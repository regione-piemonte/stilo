package it.eng.auriga.opentext.service.cs;

import java.util.List;
import java.util.Map;

import com.opentext.livelink.service.documentmanagement.Attachment;
import com.opentext.livelink.service.documentmanagement.AttributeGroup;
import com.opentext.livelink.service.documentmanagement.Metadata;
import com.opentext.livelink.service.documentmanagement.Node;
import com.opentext.livelink.service.documentmanagement.Version;

import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.exception.DuplicateDocumentNameException;
import it.eng.auriga.opentext.exception.InsufficientPermissionException;
import it.eng.auriga.opentext.service.cs.bean.FaseMetadataBean;
import it.eng.auriga.opentext.service.cs.bean.OTDocumentContent;
import it.eng.auriga.opentext.service.cs.bean.OTEsitoOperazione;

public interface DocumentService {

	/**
	 * 
	 * @param otToken    --> token ot
	 * @param documentId -> id del documento
	 * @param version -> id della version
	 * @return restituisco il contenuto della versione del documento, nome e mimeType
	 * @throws ContentServerException
	 */
	public OTDocumentContent getDocumentContent(String otToken, String documentIdAndversion) throws ContentServerException;
	/**
	 * 
	 * @param otToken    --> token ot
	 * @param documentId -> id del documento
	 * @param version -> id della version
	 * @return restituisco il contenuto della versione del documento, nome e mimeType
	 * @throws ContentServerException
	 */
	public OTDocumentContent getDocumentContent(String otToken, Long documentId,Long version) throws ContentServerException;
	
	/**
	 * 
	 * @param otToken    --> token ot
	 * @param documentId -> id del documento
	 * @return restituisco il contenuto del documento, nome e mimeType
	 * @throws ContentServerException
	 */
	public OTDocumentContent getDocumentContent(String otToken, Long documentId) throws ContentServerException;

	/**
	 * 
	 * @param otToken    --> token openText
	 * @param documentId --> id del documento
	 * @throws ContentServerException
	 */
	public void removeDocument(String otToken, Long documentId) throws ContentServerException;

	/**
	 * recupera l'id del version context
	 * 
	 * @param otToken         -> token del documento
	 * @param documentId      -> id del documento da cancellare
	 * @param documentVersion -> versione documento di cui recupera l'id
	 * @return -> id della versione
	 * @throws ContentServerException
	 */
	public String getVersioneContentContext(String otToken, Long documentId, Long documentVersion)
			throws ContentServerException;

	/**
	 * reupera l'info della versione del documento
	 * 
	 * @param otToken         -> otToken
	 * @param documentId      -> id del documento da cancellare
	 * @param documentVersion -> id della versione di cui su vuole l'info
	 * @return --> Versione
	 * @throws ContentServerException
	 */
	public Version getVersionInfoDocument(String otToken, Long documentId, Long documentVersion)
			throws ContentServerException;

	/**
	 * recupera il template della categoria
	 * 
	 * @param otToken     -> token opentText
	 * @param idCathegory -> id della categoria
	 * @return -> Categoria
	 * @throws ContentServerException
	 */
	AttributeGroup getCategoryTemplate(String otToken, Long idCathegory) throws ContentServerException;

	/**
	 * 
	 * @param otToken    --> token openText
	 * @param documentId --> id del documento
	 * @return
	 * @throws ContentServerException
	 */
	Node getNodeById(String otToken, Long documentId) throws ContentServerException;

	/**
	 * 
	 * @param otToken     -> token openText
	 * @param idCathegory -> id della categoria
	 * @param documentId  -> id del documento
	 * @param metadataMap -> mappa contenente i metadati da aggiornare
	 * @throws ContentServerException
	 */
	public void updateMetadataNode(String otToken, Long cathegoryId, Long documentId, Map<String, Object> metadataMap)
			throws ContentServerException, InsufficientPermissionException ;

	/**
	 * crea dei nuovi metadati ex novo
	 * 
	 * @param otToken     -> token openText
	 * @param idCathegory -> id della categoria
	 * @param metadataMap -> mappa contenente i metadati da aggiornare
	 * @param elencoFasi  -> elenco delle fasi da aggiornare eventualmente. Il
	 *                    parametro può essere nullable
	 * @return i metadati creati
	 * @throws ContentServerException
	 */
	public Metadata createMetadataForNewDoc(String otToken, Long idCathegory, Map<String, Object> metadataMap,
			List<FaseMetadataBean> elencoFasi) throws ContentServerException;

	/**
	 * crea un nuovo documento e lo versiona -> agisce esclivamente col docManager
	 * 
	 * @param otToken         --> token openText
	 * @param documentName    --> nome del documento
	 * @param parentId        --> id della folder in cui salvare il documento
	 * @param metadataDoc     --> metadati del documento
	 * @param contentDocument --> conte
	 * @return nodo creato
	 * @throws ContentServerException
	 */
	public Node createDocumentAndVersion(String otToken, boolean versionable, String documentName, long parentId,
			Metadata metadataDoc, byte[] contentDocument) throws ContentServerException;

	/**
	 * crea un Folder
	 * 
	 * @param otToken    -> token openText
	 * @param folderName -> nome del folder
	 * @param parentId   -> id del parent del folder
	 * @return il Folder
	 * @throws ContentServerException
	 */
	public Node createFolder(String otToken, String folderName, long parentId) throws ContentServerException;

	/**
	 * crea la versione di un documento
	 * 
	 * @param otToken           -> token openText
	 * @param documentId        -> id del documento
	 * @param contentAttachment -> contenuto del documento
	 * @return
	 * @throws ContentServerException
	 */
	public Version versionNode(String otToken, long documentId, byte[] contentAttachment) throws ContentServerException;

	/**
	 * crea la versione di un documento
	 * 
	 * @param otToken           -> token openText
	 * @param documentId        -> id del documento
	 * @param contentAttachment -> contenuto del documento
	 * @return
	 * @throws ContentServerException
	 */
	public Version versionNode(String otToken, long documentId, byte[] contentAttachment, String name_attachemnt) throws ContentServerException;

	/**
	 * servizio che restituisce il folder Root
	 * 
	 * @param otToken -> token openText
	 * @return il nodo radice
	 * @throws ContentServerException
	 */
	public Node getRootNode(String otToken) throws ContentServerException;

	/**
	 * servizio che restituisce il folder a fronte del path in input
	 * 
	 * @param otToken -> token openText
	 * @param path    -- elenco cartelle
	 * @return
	 * @throws ContentServerException
	 */
	public Node getFolderByPath(String otToken, List<String> path) throws ContentServerException;

	/**
	 * restituisce il node a fronte del nome del nodo e del id del contenitore del
	 * nodo
	 * 
	 * @param otToken  -> token openText
	 * @param parentId -> id del parent di un Folder o di un Documento da recuperare
	 * @param name     -> nome del Folder e del documento da recuperare
	 * @return
	 * @throws ContentServerException
	 */
	public Node getNodeByName(String otToken, Long parentId, String name) throws ContentServerException;

	/**
	 * 
	 * @param otToken    --token open text
	 * @param folderPath - path della folder
	 * @return
	 * @throws ContentServerException
	 */
	public Node getNodeByPath(String otToken, List<String> folderPath) throws ContentServerException;
	
	
	public List<Node> getNodes(String otToken, List<Long> documentId) throws ContentServerException;
	
	

	/**
	 * restituisce una mappa chiave-valore. La chiave rappresenta il nome della
	 * proprietà sul ContentServer
	 * 
	 * @param otToken
	 * @param documentId
	 * @param nameCathegory -> nome della categoria di cui recuperare i metadati da aggiornare
	 * @param elencoNomiMetadatiToRetrieve
	 * @return
	 * @throws ContentServerException
	 */
	public Map<String, Object> retrieveMetadataFromCathegory(String otToken, long documentId, long idCathgory, List<String> elencoNomiMetadatiToRetrieve)
			throws ContentServerException;

	/**
	 * aggiunge una nuova versione sul Content Server
	 * 
	 * @param otToken    -- token openText
	 * @param documentId -- id del documento
	 * @param metadata   --> metadata da aggiungere al documento
	 * @param contenuto  --> contenuto del documento da versionare
	 * @return la versione creata
	 * @throws ContentServerException
	 */
	public Version addVersion(String otToken, long documentId, Metadata metadata, Attachment contenuto)
			throws ContentServerException;

	/**
	 * 
	 * @param otToken
	 * @param fileName --> nome documento
	 * @param idParentFolder -> id della cartella su cui salvare il documento
	 * @return
	 * @throws ContentServerException
	 */
	public Node createNode(String otToken, String fileName, long idParentFolder) throws ContentServerException;

	/**
	 * NOTA BENE: Questo servizio passa per il ContentService e quindi usa l'MTOM
	 * @param otToken
	 * @param idCathegory --> id categoria del documento
	 * @param metadataMap --> mappa metadati
	 * @param elencoFasi --> elenco fasi nullable
	 * @param documentContent -> contenuto documento
	 * @param idParentFolder -> cartella contenitore
	 * @return
	 * @throws ContentServerException
	 */
	public OTEsitoOperazione createNodeAndUploadContent(String otToken, Metadata metadata,
			OTDocumentContent documentContent, long idParentFolder) throws ContentServerException, DuplicateDocumentNameException;

	/**
	 * crea il nodo e restituisce il contesto
	 * @param otToken
	 * @param metadata --> metadati 
	 * @param fileName -> nome file
	 * @param idParentFolder -> cartella padrea
	 * @return
	 * @throws ContentServerException
	 */
	public String createNodeAndVersionContext(String otToken, Metadata metadata, String fileName, long idParentFolder)
			throws ContentServerException;
	
	/**
	 * consente di aggiungere la categoria trasversale
	 * @param otToken --> token content server
	 * @param docId --> id del documento da cui recuperare le categorie ereditate
	 * @param confidentialityLevel --> il valore dell'attributo
	 * @return
	 * @throws ContentServerException
	 */
	public AttributeGroup addParentMetadataToDocument(String otToken, long docId, String confidentialityLevel) throws ContentServerException;
	
	/**
	 * 
	 * @param otToken --> token content server
	 * @param user -> utente che esegue il checkout
	 * @param documentId -> id del documento da prendere in checkout
	 * @return -> esito operazione
	 */
	public OTEsitoOperazione checkoutNode(String otToken, String user, Long documentId);
	
	/**
	 * 
	 * @param otToken --> token content server
	 * @param user -> utente che esegue il checkin
	 * @param documentId -> id del documento da prendere in checkin
	 * @param metadata --> metadata del documento
	 * param attachment --> contentuto del documento
	 * @return -> esito operazione
	 */
	public OTEsitoOperazione checkinNode(String otToken, String user, Long documentId, Metadata metadata, Attachment attachment) ;
	
	/**
	 * 
	 * @param otToken --> token content server
	 * @param user -> utente che esegue l'undo del checkout
	 * @param documentId -> id del documento per cui annullare il checkout
	 * @return -> esito operazione
	 */
	public OTEsitoOperazione undoCheckoutNode(String otToken, String user, Long documentId);

	/**
	 * 
	 * @param otToken --> token del content server
	 * @param user --> utente che esegue il checkin
	 * @param documentId --> id del documento
	 * @param attachment --> contenuto del documento
	 * @return
	 */
	OTEsitoOperazione checkinOnlyContent(String otToken, String user, Long documentId, byte[] attachment);
	
	public OTEsitoOperazione renameDocument(String otToken, Long documentId, String newName) throws DuplicateDocumentNameException;

	



}
