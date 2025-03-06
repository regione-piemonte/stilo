package it.eng.auriga.opentext.service.cs;

import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.exception.DuplicateDocumentNameException;
import it.eng.auriga.opentext.service.cs.bean.OTDocumentContent;
import it.eng.auriga.opentext.service.cs.bean.OTEsitoOperazione;

public interface ContentServiceIface {
	
	/**
	 * 
	 * @param otToken --> token verso OpentText
	 * @param documentContextId -> id contesto del documento
	 * @return restituisce il contenuto del documento in byte[]
	 * @throws ContentServerException
	 */
	public byte[] downloadContent(String otToken, String documentContextId) throws ContentServerException;
	
	/**
	 * esegue l'upload del contenuto del documento
	 * @param otToken --> token openText
	 * @param contextId -> id contesto del documento
	 * @param otDocumentContent -> contenuto del documento
	 * @return -> esito dell'operazione
	 * @throws ContentServerException
	 */
	public OTEsitoOperazione uploadContent(String otToken, String contextId, OTDocumentContent otDocumentContent)
			throws ContentServerException, DuplicateDocumentNameException ;

}
