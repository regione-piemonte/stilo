package it.eng.core.service.client.soap;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import it.eng.core.service.bean.AttachDescription.FileIdAssociation;
import it.eng.core.service.bean.soap.RequestSoapServiceBean;
import it.eng.core.service.bean.soap.ResponseSoapServiceBean;

/**
 * classe per fare la chiamata al servizio soap in ascolto per chiaamre i servizi, è alternativa alla classe {@link SoapService}
 * 
 * @author Russo
 *
 */
public class GenericSoapService {

	private static final Logger log = Logger.getLogger(GenericSoapService.class);

	public ResponseSoapServiceBean invoke(RequestSoapServiceBean soapBean, Map<String, String> headersToAdd) throws Exception {
		String endpointUrl = soapBean.getUrl().toExternalForm();
		QName serviceName = new QName("http://server.service.core.eng.it/", "SoapServiceService");
		QName portName = new QName("http://server.service.core.eng.it/", "SoapServicePort");
		log.debug("calling:" + endpointUrl);

		/** Creare un servizio ed aggiungere almeno una porta ad esso. **/
		URL url = new URL(endpointUrl);

		Service service = Service.create(url, serviceName);

		/** Creare una istanza di distribuzione da un servizio. **/
		Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

		if (headersToAdd != null) {

			Map<String, Object> ctxt = ((BindingProvider) dispatch).getRequestContext();

			Map<String, List<String>> headers = (Map<String, List<String>>) ctxt.get(MessageContext.HTTP_REQUEST_HEADERS);
			if (null == headers) {
				headers = new HashMap<String, List<String>>();
			}
			for (String s : headersToAdd.keySet())
				headers.put(s, Collections.singletonList(headersToAdd.get(s)));
			ctxt.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

		}

		/** Creare la richiesta SOAPMessage. **/
		// comporre un messaggio di richiesta
		MessageFactory mf = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);

		// Creare un messaggio. Questo esempio funziona con SOAPPART.
		SOAPMessage request = mf.createMessage();
		SOAPPart part = request.getSOAPPart();

		// Ottenere il SOAPEnvelope e gli elementi intestazione e corpo.
		SOAPEnvelope env = part.getEnvelope();
		SOAPHeader header = env.getHeader();
		SOAPBody body = env.getBody();

		// Creo il payload di messaggio.
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc = dbf.newDocumentBuilder().newDocument();
		JAXBContext jc = JAXBContext.newInstance(soapBean.getClass());
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(soapBean, doc);

		// aggiungo il payload al body
		log.debug("aggiungo il payload al body");
		body.addDocument(doc);
		// aggiungo gli allegati
		List<FileIdAssociation> attachemnts = soapBean.getAttachments();
		for (FileIdAssociation fileIdAssociation : attachemnts) {
			log.debug("aggiungo l'allegato:" + fileIdAssociation.getContent());
			DataHandler handler = new DataHandler(new FileDataSource(fileIdAssociation.getContent()));
			AttachmentPart attachPart = request.createAttachmentPart(handler);
			attachPart.setContentId(fileIdAssociation.getId());
			request.addAttachmentPart(attachPart);
		}

		request.saveChanges();
		// stampo il messaggio soap pre test

		Document document = env.getOwnerDocument();
		DOMImplementationLS domImplLS = (DOMImplementationLS) document.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		String xml = serializer.writeToString(env);
		log.debug("xml:" + xml);
		/** Richiamare l'endpoint del servizio. **/
		SOAPMessage soapres = dispatch.invoke(request);

		SOAPBody responseBody = soapres.getSOAPBody();
		// MySoapMsgUtil util= new MySoapMsgUtil();
		// String xml=util.parseSOAPBodyToString(soapres.getSOAPBody());
		// accedo all'elemento return della response che è fatta cosi
		/**
		 * <ns2:serviceoperationinvokeResponse xmlns:ns2="http://server.service.core.eng.it/"> <return>xml
		 * risposta </return> </ns2:serviceoperationinvokeResponse>
		 */
		// TODO make a xpath
		Node node = responseBody.getFirstChild().getFirstChild().getFirstChild();
		// perndo l'xml del contenuto di response
		// Document document = node.getOwnerDocument();
		// DOMImplementationLS domImplLS = (DOMImplementationLS) document
		// .getImplementation();
		// LSSerializer serializer = domImplLS.createLSSerializer();
		// String xml = serializer.writeToString(node);
		ResponseSoapServiceBean ret = new ResponseSoapServiceBean();
		ret.setXml(node.getNodeValue());
		// add allegati di ritorno
		// ora non può capitare di avere n allegati di ritorno al max 1!!
		Iterator iterator = soapres.getAttachments();
		Map<String, File> responseAttach = new HashMap<String, File>();
		while (iterator.hasNext()) {
			AttachmentPart ap = (AttachmentPart) iterator.next();
			File file = File.createTempFile("attachRespo", ".bin");
			IOUtils.copyLarge(ap.getDataHandler().getInputStream(), FileUtils.openOutputStream(file));
			// rimuovo le parentesi angolari
			String key = ap.getContentId();
			if (ap.getContentId().startsWith("<")) {
				log.debug("removing angular braket");
				key = ap.getContentId().substring(1, ap.getContentId().length() - 1);
			}
			responseAttach.put(key, file);
		}
		ret.setAttach(responseAttach);

		log.debug("executing soap business call completed");
		return ret;
	}

	public ResponseSoapServiceBean invoke(RequestSoapServiceBean soapBean) throws Exception {
		return invoke(soapBean, null);
	}
}
