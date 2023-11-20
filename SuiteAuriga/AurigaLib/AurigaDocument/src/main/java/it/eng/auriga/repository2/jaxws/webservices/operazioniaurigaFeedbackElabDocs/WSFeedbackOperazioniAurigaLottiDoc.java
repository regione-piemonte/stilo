/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.sun.xml.ws.developer.SchemaValidation;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerGetfeedbackelabdocslistBean;
import it.eng.auriga.database.store.dmpk_bmanager.store.Getfeedbackelabdocslist;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.dao.DaoDMVConsumerWS;
import it.eng.auriga.module.business.dao.beans.DmvConsumerWSBean;
import it.eng.auriga.repository2.jaxws.jaxbBean.feedbackOperazioniaurigalottidoc.RequestFeedbackElabDocsList;
import it.eng.auriga.repository2.jaxws.jaxbBean.feedbackOperazioniaurigalottidoc.ResponseReqFeedbackElabDocsList;
import it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc.FaultActionsOnDocList;
import it.eng.auriga.repository2.jaxws.webservices.operazioniaurigalottidoc.OperazioniAurigaLottiDocFault;
import it.eng.auriga.repository2.jaxws.webservices.operazioniaurigalottidoc.OperazioniAurigaLottiDocFaultCode;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.OperazioniAurigaLottiDocConfigBean;
import it.eng.spring.utility.SpringAppContext;

@WebService(targetNamespace = "http://operazioniaurigaFeedbackElabDocs.webservices.repository2.auriga.eng.it", endpointInterface = "it.eng.auriga.repository2.jaxws.webservices.operazioniaurigaFeedbackElabDocs.WSIFeedbackOperazioniAurigaLottiDoc", name = "WSFeedbackOperazioniAurigaLottiDoc")
@SchemaValidation
@MTOM(enabled = true, threshold = 0)

public class WSFeedbackOperazioniAurigaLottiDoc implements WSIFeedbackOperazioniAurigaLottiDoc {

	@Resource
	private WebServiceContext context;

	// private Locale locale = new Locale("it");

	static Logger aLogger = Logger.getLogger(WSFeedbackOperazioniAurigaLottiDoc.class.getName());

	@Override
	public ResponseReqFeedbackElabDocsList feedbackOperazioniAurigaLottiDoc(RequestFeedbackElabDocsList parameter) throws OperazioniAurigaLottiDocFault {

		aLogger.trace(">> feedbackOperazioniAurigaLottiDoc()");
		aLogger.trace(">> feedbackOperazioniAurigaLottiDoc() -- " + parameter);

		ResponseReqFeedbackElabDocsList response = new ResponseReqFeedbackElabDocsList();
		if ((parameter == null) || (StringUtils.isBlank(parameter.getIdLottoDoc()))) {
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.INVALID_REQUEST.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.INVALID_REQUEST.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.INVALID_REQUEST.getMsg(), faultInfo);
		}
		try {
			JAXBContext lJAXBContextMarshallerReq = JAXBContext.newInstance(new Class[] { RequestFeedbackElabDocsList.class });
			Marshaller marshallerReq = lJAXBContextMarshallerReq.createMarshaller();
			marshallerReq.setProperty("jaxb.formatted.output", Boolean.valueOf(true));

			JAXBContext lJAXBContextUnmarshallerReq = JAXBContext.newInstance(new Class[] { RequestFeedbackElabDocsList.class });
			Unmarshaller unmarshallerReq = lJAXBContextUnmarshallerReq.createUnmarshaller();

			StringWriter stringWriter = new StringWriter();
			marshallerReq.marshal(parameter, stringWriter);

			aLogger.debug("Request::: " + stringWriter);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		String idLotto = parameter.getIdLottoDoc();
		aLogger.debug("IdLotto " + idLotto);
		String tipoRichiesta = parameter.getTipoRichiesta();
		aLogger.debug("tipoRichiesta " + tipoRichiesta);

		OperazioniAurigaLottiDocConfigBean lOperazioniAurigaLottiDocConfigBean = getConfig();

		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(lOperazioniAurigaLottiDocConfigBean.getDefaultSchema());

		SubjectBean subject = new SubjectBean();
		subject.setIdDominio(lSchemaBean.getSchema());
		SubjectUtil.subject.set(subject);

		DmvConsumerWSBean consumerWS = getConsumerWS(lSchemaBean);

		Getfeedbackelabdocslist getfeedbackelabdocslist = new Getfeedbackelabdocslist();

		DmpkBmanagerGetfeedbackelabdocslistBean dDmpkBmanagerGetfeedbackelabdocslistBean = new DmpkBmanagerGetfeedbackelabdocslistBean();
		dDmpkBmanagerGetfeedbackelabdocslistBean.setIdlottoin(idLotto);
		dDmpkBmanagerGetfeedbackelabdocslistBean.setTiporichiestain(tipoRichiesta);
		dDmpkBmanagerGetfeedbackelabdocslistBean.setUseridapplin(consumerWS.getUserid());
		dDmpkBmanagerGetfeedbackelabdocslistBean.setPasswordappin(consumerWS.getPassword());
		try {
			StoreResultBean<DmpkBmanagerGetfeedbackelabdocslistBean> result = getfeedbackelabdocslist.execute(lSchemaBean,
					dDmpkBmanagerGetfeedbackelabdocslistBean);

			DmpkBmanagerGetfeedbackelabdocslistBean resultBean = (DmpkBmanagerGetfeedbackelabdocslistBean) result.getResultBean();
			if (resultBean != null) {
				Integer esitoStore = resultBean.getParametro_1();
				aLogger.debug("esitoStore: " + esitoStore);

				JAXBContext lJAXBContextMarshallerRes = JAXBContext.newInstance(new Class[] { ResponseReqFeedbackElabDocsList.class });
				Marshaller marshallerRes = lJAXBContextMarshallerRes.createMarshaller();
				marshallerRes.setProperty("jaxb.formatted.output", Boolean.valueOf(true));

				JAXBContext lJAXBContextUnmarshallerRes = JAXBContext.newInstance(new Class[] { ResponseReqFeedbackElabDocsList.class });
				Unmarshaller unmarshallerRes = lJAXBContextUnmarshallerRes.createUnmarshaller();
				StringWriter stringWriter = new StringWriter();
				if ((esitoStore != null) && (!esitoStore.equals(new Integer(1)))) {
					response.setStatus("KO");
					ResponseReqFeedbackElabDocsList.RejectionMsg rejectionMsg = new ResponseReqFeedbackElabDocsList.RejectionMsg();
					aLogger.debug("::: " + resultBean.getErrcodeout());
					aLogger.debug(":::" + resultBean.getErrmsgout());

					rejectionMsg.setErrCode(BigInteger.valueOf(resultBean.getErrcodeout().intValue()));
					rejectionMsg.setValue(resultBean.getErrmsgout());
					response.setRejectionMsg(rejectionMsg);

					marshallerRes.marshal(response, stringWriter);
					aLogger.debug("Response::: " + stringWriter);
				} else {
					String xmlAggLotto = resultBean.getXmlagglottiout();
					aLogger.debug("xmlAggLotto " + xmlAggLotto);

					File zip = creaZip(xmlAggLotto);
					attachFile(zip);

					response.setStatus("OK");

					marshallerRes.marshal(response, stringWriter);

					aLogger.debug("Response::: " + stringWriter);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			aLogger.error("", e);
			response.setStatus("KO");
			aLogger.error("Errore nell'invocazione della store ", e);
			ResponseReqFeedbackElabDocsList.RejectionMsg rejectionMsg = new ResponseReqFeedbackElabDocsList.RejectionMsg();
			rejectionMsg.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getCode()));
			rejectionMsg.setValue(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg());
			response.setRejectionMsg(rejectionMsg);
		} catch (Throwable e) {
			e.printStackTrace();
			aLogger.error("", e);
		}
		return response;

	}

	private DmvConsumerWSBean getConsumerWS(SchemaBean pSchemaBean) throws OperazioniAurigaLottiDocFault {
		aLogger.trace("Verifica delle credenziali");

		MessageContext lMessageContext = this.context.getMessageContext();
		String username = (String) lMessageContext.get("username");
		String password = (String) lMessageContext.get("password");
		if (StringUtils.isBlank(username)) {
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.INVALID_CREDENTIAL.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.INVALID_CREDENTIAL.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.INVALID_CREDENTIAL.getMsg(), faultInfo);
		}
		DaoDMVConsumerWS dao = new DaoDMVConsumerWS();
		try {
			return dao.getDmvConsumerWs(pSchemaBean, username, password);
		} catch (Exception e) {
			aLogger.error("Errore recupero consumer WS", e);
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.INVALID_CREDENTIAL.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.INVALID_CREDENTIAL.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.INVALID_CREDENTIAL.getMsg(), faultInfo);
		}
	}

	private OperazioniAurigaLottiDocConfigBean getConfig() throws OperazioniAurigaLottiDocFault {
		OperazioniAurigaLottiDocConfigBean lOperazioniAurigaLottiDocConfigBean = null;
		try {
			lOperazioniAurigaLottiDocConfigBean = (OperazioniAurigaLottiDocConfigBean) SpringAppContext.getContext()
					.getBean("FeedbackOperazioniAurigaLottiDocConfigBean");
		} catch (Exception e) {
			aLogger.fatal("Eccezione nel recupero del bean OperazioniAurigaLottiDocConfigBean");
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg(), faultInfo);
		}
		if (lOperazioniAurigaLottiDocConfigBean == null) {
			aLogger.fatal("Bean OperazioniAurigaLottiDocConfigBean non configurato");
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg(), faultInfo);
		}
		if (StringUtils.isBlank(lOperazioniAurigaLottiDocConfigBean.getDefaultSchema())) {
			aLogger.fatal("Schema non valorizzato");
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg(), faultInfo);
		}
		return lOperazioniAurigaLottiDocConfigBean;
	}

	public boolean attachFile(File mioFile) throws Exception {
		try {
			MessageContext msgContext = this.context.getMessageContext();

			FileDataSource fileDS = new FileDataSource(mioFile);
			DataHandler handler = new DataHandler(fileDS);

			Map<String, DataHandler> mapDataHandlers = (Map) msgContext.get("javax.xml.ws.binding.attachments.inbound");
			mapDataHandlers.put(handler.getName(), handler);

			msgContext.put("javax.xml.ws.binding.attachments.outbound", mapDataHandlers);
			return true;
		} catch (Exception e) {
			aLogger.error("Errore in attachFile(File file = " + mioFile + ")" + e.getMessage(), e);
		}
		return false;
	}

	private static File creaZip(String xmlString) throws FileNotFoundException, IOException {
		File temp = File.createTempFile("response", ".xml");

		temp.deleteOnExit();

		BufferedWriter out = new BufferedWriter(new FileWriter(temp));
		out.write(xmlString);
		out.close();

		File zipFile = File.createTempFile("response", ".zip");
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zos = new ZipOutputStream(fos);

		FileInputStream fis = new FileInputStream(temp);
		ZipEntry zipEntry = new ZipEntry("response.xml");
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte['?'];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			// int length;
			zos.write(bytes, 0, length);
		}
		zos.closeEntry();
		fis.close();

		zos.close();

		return zipFile;
	}

	public static void main(String[] args) throws Exception {
		JAXBContext lJAXBContextMarshaller = JAXBContext.newInstance(new Class[] { ResponseReqFeedbackElabDocsList.class });
		Marshaller marshaller = lJAXBContextMarshaller.createMarshaller();

		JAXBContext lJAXBContextUnmarshaller = JAXBContext.newInstance(new Class[] { ResponseReqFeedbackElabDocsList.class });
		Unmarshaller unmarshaller = lJAXBContextUnmarshaller.createUnmarshaller();

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><ns2:ResponseReqFeedbackElabDocsList xmlns:ns2=\"http://operazioniaurigaFeedbackElabDocs.webservices.repository2.auriga.eng.it\" Status=\"OK\"/>";

		StringReader sr = new StringReader(xml);

		ResponseReqFeedbackElabDocsList response = new ResponseReqFeedbackElabDocsList();
		response.setStatus("KO");

		ResponseReqFeedbackElabDocsList.RejectionMsg rejMsg = new ResponseReqFeedbackElabDocsList.RejectionMsg();
		rejMsg.setValue("erer");
		rejMsg.setErrCode(new BigInteger("3"));
		response.setRejectionMsg(rejMsg);
		System.out.println(response);

		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(response, stringWriter);

		System.out.println(stringWriter);
	}

}
