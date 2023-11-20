/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.core.ResourceContext;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.core.service.bean.AttachDescription;
import it.eng.core.service.bean.AttachDescription.FileIdAssociation;
import it.eng.core.service.bean.AttachHelper;
import it.eng.core.service.bean.NullBean;
import it.eng.core.service.bean.rest.RestServiceBean;
import it.eng.core.service.bean.soap.ResponseSoapServiceBean;
import it.eng.core.service.client.config.Configuration;
import it.eng.core.service.serialization.IServiceSerialize;
import it.eng.core.service.serialization.SerializerRepository;
import it.eng.core.service.server.ServiceRestStore;
import it.eng.utility.RestCallSupport;

public abstract class BaseResource {

	private static final Logger log = Logger.getLogger(BaseResource.class);
	protected final Configuration cfg;
	protected final IServiceSerialize serializeUtil;
	@Context
	protected ResourceContext resourceCtx;

	public BaseResource() throws Exception {
		cfg = Configuration.getInstance();
		serializeUtil = SerializerRepository.getSerializationUtil(cfg.getSerializationtype());
	}

	protected final Response invokeService(String token, String operation, Serializable... inputs) {
		Response response = null;
		try {
			final List<FileIdAssociation> attachsend = new ArrayList<FileIdAssociation>();
			final ArrayList<String> inputstrings = new ArrayList<String>();

			RestCallSupport.fill(getServiceName(), operation, serializeUtil, attachsend, inputstrings, inputs);
			final RestServiceBean beanToSend = createBean(token, operation, inputstrings);
			final FormDataMultiPart multiPart = createMultiPart(attachsend, beanToSend);
			final ServiceRestStore serviceRestStore = resourceCtx.getResource(ServiceRestStore.class);

			response = serviceRestStore.invoke(beanToSend, multiPart);

		} catch (Exception e) {
			log.error("Errore in invokeService()", e);
			response = Response.serverError().entity(String.valueOf(e.getLocalizedMessage())).build();
		}

		return response;
	}

	protected final Response invokeService(String operation, Serializable... inputs) {
		return invokeService(null, operation, inputs);
	}

	@SuppressWarnings("unchecked")
	protected <T extends Serializable> T parseResponse(final Response response, final Class<T> clazz) {
		Serializable ser = null;
		try {
			if (response.getStatus() == Response.Status.OK.getStatusCode()) {

				final FormDataMultiPart multiPartOut = (FormDataMultiPart) response.getEntity();
				final ResponseSoapServiceBean ret = RestCallSupport.getResponse(multiPartOut.getFields());

				if (ret != null) {
					// logger.debug("xml: "+String.valueOf(ret.getXml()));
				}

				final Serializable obj = serializeUtil.deserialize(ret.getXml(), clazz, clazz);
				log.debug("obj: " + String.valueOf(obj));

				if (obj instanceof NullBean) {
					log.warn("obj è un NullBean.");
				} else if (obj instanceof AttachDescription) {
					ser = AttachHelper.deserialize((AttachDescription) obj, ret.getAttach(), serializeUtil, clazz, clazz);
				} else {
					ser = obj;
				}

			}
			// else {
			// log.fatal("response.status: "+String.valueOf(response.getStatus())+" -> lancio una WebApplicationException.");
			// throw new AurigaMailException(response);
			// }//else

			log.debug("ser: " + String.valueOf(ser));

			if (ser instanceof ResultBean) {
				ser = (Serializable) ((ResultBean<?>) ser).getResultBean();
			}

		} catch (WebApplicationException we) {
			log.error("Errore in parseResponse()", we);
			throw we;
			// final CoreException cex = RestCallSupport.buildCoreException(response);
			//
			// if (cex == null) {
			// log.error("Errore in parseResponse() -> rilancio una WebApplicationException");
			// throw we;
			// } else {
			// log.error("Errore in parseResponse() -> lancio una CoreException");
			// throw cex;
			// }
		} catch (UniformInterfaceException ufe) {
			log.error("Errore in parseResponse()", ufe);
			// log.error("Errore in parseResponse() -> lancio una CoreException", ufe);
			// throw RestCallSupport.buildCoreException(ufe);
			throw new AurigaMailException(ufe);
		} catch (Exception ex) {
			log.error("Errore in parseResponse()", ex);
			// log.error("Errore in parseResponse() -> lancio una CoreException", ex);
			// throw new CoreException(new CoreExceptionBean(ex));
			throw new AurigaMailException(ex);
		}

		if (ser == null) {
			throw new AurigaMailException(response);
		}

		return (T) ser;
	}

	protected abstract String getServiceName();

	private FormDataMultiPart createMultiPart(final List<FileIdAssociation> attachsend, final RestServiceBean beanToSend) {
		@SuppressWarnings("resource")
		final FormDataMultiPart multiPart = new FormDataMultiPart().field("restservicebean", beanToSend, MediaType.APPLICATION_XML_TYPE);
		for (FileIdAssociation fileIdPair : attachsend) {
			// è un FormDataBodyPart
			final FileDataBodyPart fileDataBodyPart = new FileDataBodyPart(fileIdPair.getId(), fileIdPair.getContent(),
					MediaType.APPLICATION_OCTET_STREAM_TYPE);
			multiPart.bodyPart(fileDataBodyPart);
		}
		return multiPart;
	}

	private RestServiceBean createBean(String token, String operation, final ArrayList<String> inputstrings) {
		final RestServiceBean beanToSend = new RestServiceBean();
		beanToSend.setInputs(inputstrings);
		beanToSend.setServicename(getServiceName());
		beanToSend.setOperationname(operation);
		beanToSend.setSerializationType(cfg.getSerializationtype().toString());
		beanToSend.setTokenid(token);
		beanToSend.setUuidtransaction("");
		beanToSend.setIdDominio("test");
		return beanToSend;
	}

}// BaseResource
