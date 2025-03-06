package it.eng.auriga.opentext.service.cs.impl;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;

import org.springframework.remoting.soap.SoapFaultException;

import com.opentext.livelink.service.contentservice.FileAtts;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;

import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.service.cs.SoapHeaderService;
import it.eng.auriga.opentext.service.cs.bean.OTDocumentContent;

public class SoapHeaderServiceImpl implements SoapHeaderService {

	public void setAuthAndContextSoapHeaderElementToDownload(String otToken, String contextId,
			WSBindingProvider provider) throws ContentServerException {
		try {
			SOAPHeader header = this.getSOAPHeader();
			// The namespace of the OTAuthentication object
			final String ECM_API_NAMESPACE = "urn:api.ecm.opentext.com";

			// Add the OTAuthentication SOAP header element
			SOAPHeaderElement otAuthElement = header.addHeaderElement(new QName(ECM_API_NAMESPACE, "OTAuthentication"));

			// Add the AuthenticationToken SOAP element
			SOAPElement authTokenElement = otAuthElement
					.addChildElement(new QName(ECM_API_NAMESPACE, "AuthenticationToken"));
			authTokenElement.addTextNode(otToken);

			final String SERVICE_API_NAMESPACE = "urn:Core.service.livelink.opentext.com";

			SOAPHeaderElement contextElement = header.addHeaderElement(new QName(SERVICE_API_NAMESPACE, "contextID"));
			if (contextId != null)
				contextElement.addTextNode(contextId);

			// creo gli headers da aggiungere all'Outbound
			List<Header> headers = new ArrayList<Header>();
			headers.add(Headers.create(otAuthElement));
			headers.add(Headers.create(contextElement));

			provider.setOutboundHeaders(headers);

		} catch (Exception e) {

			throw new ContentServerException("Eccezione nella modifica del ContextSoapHeader " + e.getMessage());
		}

	}

	// We need to manually set the SOAP header to include the authentication token
	public void setAuthSOAPHeader(String otToken, WSBindingProvider provider) throws ContentServerException {

		try {
			SOAPHeader header = this.getSOAPHeader();
			// The namespace of the OTAuthentication object
			final String ECM_API_NAMESPACE = "urn:api.ecm.opentext.com";

			// Add the OTAuthentication SOAP header element
			SOAPHeaderElement otAuthElement = header.addHeaderElement(new QName(ECM_API_NAMESPACE, "OTAuthentication"));

			// Add the AuthenticationToken SOAP element
			SOAPElement authTokenElement = otAuthElement
					.addChildElement(new QName(ECM_API_NAMESPACE, "AuthenticationToken"));
			authTokenElement.addTextNode(otToken);
			provider.setOutboundHeaders(Headers.create(otAuthElement));
		} catch (SoapFaultException e) {

			throw new ContentServerException("Eccezione nella creazione del SOAPHeader " + e.getMessage());
		} catch (SOAPException e) {

			throw new ContentServerException("Eccezione nella creazione del SOAPHeader " + e.getMessage());
		}

	}

	public SOAPHeader getSOAPHeader() throws ContentServerException {
		SOAPHeader header = null;
		try {
			// Create a SOAP header
			header = MessageFactory.newInstance().createMessage().getSOAPPart().getEnvelope().getHeader();
		}

		catch (Exception e) {

			throw new ContentServerException("Eccezione nel recupero del SOAPHeader " + e.getMessage());
		}
		return header;
	}

	public void setAuthAndContextSoapHeaderElementToUpload(String otToken, String contextId,
			OTDocumentContent otDocumentContent, WSBindingProvider provider) throws ContentServerException {
		try {
			SOAPHeader header = this.getSOAPHeader();
			// The namespace of the OTAuthentication object
			final String ECM_API_NAMESPACE = "urn:api.ecm.opentext.com";
			final String CORE_NAMESPACE = "urn:Core.service.livelink.opentext.com";

			// Add the OTAuthentication SOAP header element
			SOAPHeaderElement otAuthElement = header.addHeaderElement(new QName(ECM_API_NAMESPACE, "OTAuthentication"));

			// Add the AuthenticationToken SOAP element
			SOAPElement authTokenElement = otAuthElement
					.addChildElement(new QName(ECM_API_NAMESPACE, "AuthenticationToken"));
			authTokenElement.addTextNode(otToken);

			final String SERVICE_API_NAMESPACE = "urn:Core.service.livelink.opentext.com";

			SOAPHeaderElement contextElement = header.addHeaderElement(new QName(SERVICE_API_NAMESPACE, "contextID"));
			if (contextId != null)
				contextElement.addTextNode(contextId);

			FileAtts fileAtts = new FileAtts();

			fileAtts.setCreatedDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
			fileAtts.setFileName(otDocumentContent.getName());
			fileAtts.setFileSize(Long.valueOf(otDocumentContent.getContent().length));
			fileAtts.setModifiedDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));

			// Add the FileAtts SOAP header element
			SOAPHeaderElement fileAttsElement = header.addHeaderElement(new QName(CORE_NAMESPACE, "fileAtts"));

			// Add the CreatedDate element
			SOAPElement createdDateElement = fileAttsElement.addChildElement(new QName(CORE_NAMESPACE, "CreatedDate"));
			createdDateElement.addTextNode(fileAtts.getCreatedDate().toString());

			// Add the ModifiedDate element
			SOAPElement modifiedDateElement = fileAttsElement
					.addChildElement(new QName(CORE_NAMESPACE, "ModifiedDate"));
			modifiedDateElement.addTextNode(fileAtts.getModifiedDate().toString());

			// Add the FileSize element
			SOAPElement fileSizeElement = fileAttsElement.addChildElement(new QName(CORE_NAMESPACE, "FileSize"));
			fileSizeElement.addTextNode(fileAtts.getFileSize().toString());

			// Add the FileName element
			SOAPElement fileNameElement = fileAttsElement.addChildElement(new QName(CORE_NAMESPACE, "FileName"));
			fileNameElement.addTextNode(fileAtts.getFileName());

			// creo gli headers da aggiungere all'Outbound
			List<Header> headers = new ArrayList<Header>();
			headers.add(Headers.create(otAuthElement));
			headers.add(Headers.create(contextElement));
			headers.add(Headers.create(fileAttsElement));

			provider.setOutboundHeaders(headers);

		} catch (Exception e) {

			throw new ContentServerException("Eccezione nella modifica del ContextSoapHeader " + e.getMessage());
		}

	}

}
