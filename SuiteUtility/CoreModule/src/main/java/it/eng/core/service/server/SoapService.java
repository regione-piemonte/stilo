package it.eng.core.service.server;

import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.core.annotation.Attachment;
import it.eng.core.business.exception.ServiceException;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.config.ClassUtil;
import it.eng.core.config.ConfigUtil;
import it.eng.core.service.bean.AttachDeSerializationException;
import it.eng.core.service.bean.AttachDescription;
import it.eng.core.service.bean.AttachDescription.FileIdAssociation;
import it.eng.core.service.bean.AttachHelper;
import it.eng.core.service.bean.IAuthenticationBean;
import it.eng.core.service.bean.IServiceInvokerInfo;
import it.eng.core.service.bean.IrisCall;
import it.eng.core.service.bean.NullBean;
import it.eng.core.service.bean.ServiceBean;
import it.eng.core.service.serialization.IServiceSerialize;
import it.eng.core.service.serialization.SerializationType;
import it.eng.core.service.serialization.SerializerRepository;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;

/**
 * WebService che espsone le operazioni di chiamata alle store procedure
 * 
 * @author Rigo Michele
 */

@WebService
@MTOM(enabled = true)
public class SoapService {

	private static final Logger log = Logger.getLogger(SoapService.class);

	@Resource
	WebServiceContext wsContext;

	/**
	 * Operazione che effettua la chiamata all'operazione in ingresso
	 * 
	 * @param storename
	 *            Nome della store procedure con annesso il package
	 * @param objectserialize
	 *            Oggetto serializzato
	 * @return
	 * @throws Exception
	 */
	@WebMethod
	public String serviceoperationinvoke(@WebParam(name = "serializationtype") String serializationType,
			@WebParam(name = "uuidtransaction") String uuidtransaction, @WebParam(name = "tokenid") String tokenid,
			@WebParam(name = "servicename") String servicename, @WebParam(name = "operationame") String operationname,
			@WebParam(name = "objectsserialize") List<String> objectsserialize) throws ServiceException {
		String output = "";
		try {

			// ArrayList<String> objectdeserialize = (ArrayList<String>)SerializationUtils.deserialize(IOUtils.toInputStream(objectsserialize));

			// Istanzio l'utilità di serializzazione
			IServiceSerialize serializeutil = SerializerRepository.getSerializationUtil(SerializationType.valueOf(serializationType));

			String idDominio = "DEFAULT";
			String idApplicazione = null;

			// AUTENTICAZIONE IRIS
			// cerco se in configurazione e' prevista la autenticazione
			// se si, controllo che negli header http siano stati passati i parametri
			// se si invoco il servizio con i parametri
			String loginService = null;//
			IAuthenticationBean authenticationBean = null;
			if (ConfigUtil.isAuthenticationEnabled()) {
				// se si, controllo che in configurazione sia configurato il servizio di login
				loginService = ConfigUtil.getAuthenticationServiceName();
				String authenticationBeanName = ConfigUtil.getAuthenticationBeanName();

				authenticationBean = (IAuthenticationBean) Class.forName(authenticationBeanName).newInstance();

				Set<String> beanProps = authenticationBean.listProperties();

				MessageContext context = wsContext.getMessageContext();
				Map hMap = (Map) context.get(MessageContext.HTTP_REQUEST_HEADERS);

				Map<String, String> mappaValori = new HashMap<String, String>();
				for (String key : beanProps) {
					String value = getHttpHeaderValue(context, key);
					if (value != null)
						mappaValori.put(key, value);
				}

				authenticationBean.populateFromDescription(mappaValori);
				if (authenticationBean.getApplicationName() != null) {
					org.apache.commons.configuration.Configuration configurazioneApplicazioniEnti = ConfigUtil.getMappaApplEnte();
					String tmp = (String) configurazioneApplicazioniEnti.getProperty(authenticationBean.getApplicationName());
					idDominio = tmp == null ? "DEFAULT" : tmp;
				}

			}

			// Setto i valori del token di connessione nella variabile threadlocal
			// TODO DA RIDEFINIRE
			SubjectBean subject = new SubjectBean();
			subject.setUuidtransaction(uuidtransaction);
			subject.setIdDominio(idDominio);
			SubjectUtil.subject.set(subject);

			// chiamo il servizio
			if (ConfigUtil.isAuthenticationEnabled() && !servicename.equals(loginService)) {
				idApplicazione = authenticationBean.getApplicationName();
				subject.setIdapplicazione(idApplicazione);

				IServiceInvokerInfo invokerInfo = (IServiceInvokerInfo) IrisCall.call(loginService, "login", new Serializable[] { authenticationBean });

				if (invokerInfo != null) {
					subject.setIduser(invokerInfo.getIdServiceInvoker());
					subject.setNomeapplicazione(invokerInfo.getNomeApplicazione());
				}

			}

			// MessageContext msContext = wsContext.getMessageContext();
			// SOAPMessageContext soapMsgContext = (SOAPMessageContext) msContext;
			// SOAPHeader soapHeader = soapMsgContext.getMessage().getSOAPHeader();
			// Iterator it=soapHeader.extractAllHeaderElements();
			// while (it.hasNext()) {
			// String string = (String) it.next();
			//
			// }

			ServiceBean servicebean = IrisCall.getOperationServiceBean(servicename, operationname);

			// Recupero i parametri del servizio
			Class<?>[] parameters = servicebean.getOperation().getParameterTypes();
			Type[] types = servicebean.getOperation().getGenericParameterTypes();

			if (parameters.length != objectsserialize.size()) {
				throw new Exception("Il numero dei parametri di input è errato");
			}
			log.debug(">>>>>>>> SOAP SERVICE servicename:" + servicename + " operationame:" + operationname + " >>>>>>>>>");
			// Deserializzo i paramentrio da passare al metodo
			List<Serializable> inputsObject = new ArrayList<Serializable>();
			int index = 0;
			// recupero la lista degli allegati
			Map<String, File> mapAttach = getInBoundAttach();
			log.debug(" START inizio deserializzazione parametri");
			for (String objectserialize : objectsserialize) {
				if (objectserialize == null || objectserialize.equals("")) {
					// parameter passed null
					log.debug("parametro " + index + " vuoto");
					inputsObject.add(null);
				} else {
					// verifica se ci sono file e ricostruisci gli input da inviare al servizio
					Serializable obj = serializeutil.deserialize(objectserialize, parameters[index], types[index]);
					if (obj instanceof AttachDescription) {
						log.debug("il parametro " + index + " contiene attach class:" + obj.getClass());
						// ricostruzione dell'oggetto da passsare
						Serializable recostructedObj = AttachHelper.deserialize((AttachDescription) obj, mapAttach, serializeutil, parameters[index],
								types[index]);
						log.debug("ricostruito oggetto " + recostructedObj.getClass());
						inputsObject.add(recostructedObj);
					} else {
						// Serializable objdes=serializeutil.deserialize(objectserialize,parameters[index],types[index]);
						log.debug("parametro " + index + " deserializzato " + obj);
						inputsObject.add(obj);
					}
				}
				index++;
			}

			log.debug(" END fine deserializzazione parametri");
			// Effettuo la chiamata all'api
			Serializable outputobject = IrisCall.call(servicename, operationname, inputsObject.toArray(new Serializable[0]));

			// Serializzo l'oggetto di ritorno
			// output = serializeutil.serialize(outputobject);
			if (outputobject != null) { // per i metodi void!

				List<FileIdAssociation> responseAttach = new ArrayList<FileIdAssociation>();
				AttachDescription ad = AttachHelper.serializeAsAttach(-1, outputobject, serializeutil);
				if (ad != null) {
					log.debug("parametro di ritorno serializzato come  attach class:" + ad.getClass());
					List<FileIdAssociation> assocs = ad.getContents();
					responseAttach.addAll(assocs);
					attachFiles(responseAttach);
					output = serializeutil.serialize(ad);
				} else {
					log.debug("parametro di ritorno serializzato SENZA attach");
					output = serializeutil.serialize(outputobject);
				}
			} else {
				outputobject = new NullBean();
				output = serializeutil.serialize(outputobject);
			}
			log.debug(">>>>>>>> END  SOAP SERVICE servicename:" + servicename + " operationame:" + operationname + " >>>>>>>>>");

		} catch (Exception e) {
			log.fatal("fatal serviceoperationinvoke ", e);
			throw new ServiceException(e);
		}
		return output;
	}

	/**
	 * leggi gli attach ricevuti
	 * 
	 * @return
	 * @throws AttachDeSerializationException
	 */
	private Map<String, File> getInBoundAttach() throws AttachDeSerializationException {
		try {
			Map<String, File> files = new HashMap<String, File>();
			MessageContext msgContext = wsContext.getMessageContext();
			Map<String, DataHandler> attachments = (Map<String, DataHandler>) wsContext.getMessageContext()
					.get(javax.xml.ws.handler.MessageContext.INBOUND_MESSAGE_ATTACHMENTS);
			for (String attachmentKey : attachments.keySet()) {
				DataHandler handler = (DataHandler) attachments.get(attachmentKey);
				log.debug("Got attachment " + attachmentKey + " of type " + attachments.get(attachmentKey));
				// TODO make extension on mimetype
				File file = File.createTempFile("temp", "bin");
				OutputStream ostream = FileUtils.openOutputStream(file);
				IOUtils.copyLarge(handler.getDataSource().getInputStream(), ostream);
				IOUtils.closeQuietly(ostream);
				files.put(attachmentKey, file);
			}
			if (attachments.isEmpty()) {
				log.debug("Got No attachments");
			}
			return files;
		} catch (Exception ex) {
			log.fatal("fatal reading attach" + ex, ex);
			throw new AttachDeSerializationException("fatal reading attach", ex);
		}
	}

	private void attachFiles(List<FileIdAssociation> files) {
		MessageContext msgContext = wsContext.getMessageContext();
		Map<String, DataHandler> attachments = new HashMap<String, DataHandler>();
		DataHandler handler = null;
		for (FileIdAssociation fileIdAssociation : files) {
			if (fileIdAssociation != null && fileIdAssociation.getContent() != null) {
				handler = new DataHandler(new FileDataSource(fileIdAssociation.getContent()));
				attachments.put(fileIdAssociation.getId(), handler);
			}
		}
		msgContext.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, attachments);
	}

	/**
	 * //TODO gestire funzionamento per altri oggetti quali stream etc.
	 * 
	 * @param outputobject
	 * @return
	 * @throws Exception
	 */
	private List<File> getAttachProp(Object outputobject) throws Exception {
		List<File> ret = new ArrayList<File>();
		log.debug("finding prop with annotation ");
		BeanWrapperImpl wrappedOutputobject = BeanPropertyUtility.getBeanWrapper(outputobject);

		Field[] fields = ClassUtil.getAnnotatedDeclaredFields(outputobject.getClass(), Attachment.class, false);
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			// il campo dovrebbe avere anche l'annotation @XmlTransient
			if (field.getType().equals(File.class)) {
				log.debug("get value for name " + field.getName());
				ret.add((File) BeanPropertyUtility.getPropertyValue(outputobject, wrappedOutputobject, field.getName()));
				// ret.add((File) BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(outputobject, field.getName()));
			} else if (field.getType().isAssignableFrom(Collection.class)) {
				Collection colls = (Collection) BeanPropertyUtility.getPropertyValue(outputobject, wrappedOutputobject, field.getName());
				// Collection colls = (Collection) BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(outputobject, field.getName());
				for (Object object : colls) {
					if (object instanceof File) {
						ret.add((File) object);
					}
				}
			} else {
				log.warn(">>>>>> field name " + field.getName() + " non gestibile come attach");
			}
		}
		return ret;
	}

	private String getHttpHeaderValue(MessageContext context, String key) {
		Map hMap = (Map) context.get(MessageContext.HTTP_REQUEST_HEADERS);
		List<String> lst = hMap.get(key) == null ? null : (List) hMap.get(key);
		return (lst == null || lst.size() == 0) ? null : lst.get(0);
	}
}
