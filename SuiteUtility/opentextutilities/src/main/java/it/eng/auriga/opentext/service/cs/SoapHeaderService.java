package it.eng.auriga.opentext.service.cs;

import javax.xml.soap.SOAPHeader;

import com.sun.xml.ws.developer.WSBindingProvider;

import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.service.cs.bean.OTDocumentContent;

public interface SoapHeaderService {
	// utilizzato per il COntentService per eseguire il downlaod del documento 
	public void setAuthAndContextSoapHeaderElementToDownload(String otToken, String contextId, WSBindingProvider provider)
			throws ContentServerException;
	
	

	// We need to manually set the SOAP header to include the authentication token
	public void setAuthSOAPHeader(String otToken, WSBindingProvider provider) throws ContentServerException;

	/**
	 * 
	 * @param creaNuovaIstanza -> indica al metodo se creare una nuova istanza
	 *                         oppure usare quella creata precedentemente
	 * @return
	 * @throws ContentServerException
	 */
	public SOAPHeader getSOAPHeader() throws ContentServerException ;
	
	
	public void setAuthAndContextSoapHeaderElementToUpload(String otToken, String contextId, OTDocumentContent otDocumentContent,
			WSBindingProvider provider) throws ContentServerException;
}
