package it.eng.auriga.opentext.service.cs.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.LoggerFactory;

import com.opentext.livelink.service.documentmanagement.Attachment;
import com.opentext.livelink.service.documentmanagement.AttributeGroup;
import com.opentext.livelink.service.documentmanagement.CategoryInheritance;
import com.opentext.livelink.service.documentmanagement.DataValue;
import com.opentext.livelink.service.documentmanagement.DocumentManagement;
import com.opentext.livelink.service.documentmanagement.Metadata;
import com.opentext.livelink.service.documentmanagement.Node;
import com.opentext.livelink.service.documentmanagement.RowValue;
import com.opentext.livelink.service.documentmanagement.StringValue;
import com.opentext.livelink.service.documentmanagement.TableValue;
import com.opentext.livelink.service.documentmanagement.Version;
import com.opentext.livelink.service.memberservice.User;
import com.sun.xml.ws.developer.WSBindingProvider;
import com.sun.xml.ws.fault.ServerSOAPFaultException;

import it.eng.auriga.opentext.enumeration.EsitoEnum;
import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.exception.DatatypeConfigurationContentServerException;
import it.eng.auriga.opentext.exception.DuplicateDocumentNameException;
import it.eng.auriga.opentext.exception.InsufficientPermissionException;
import it.eng.auriga.opentext.service.cs.DocumentService;
import it.eng.auriga.opentext.service.cs.bean.FaseMetadataBean;
import it.eng.auriga.opentext.service.cs.bean.OTDocumentContent;
import it.eng.auriga.opentext.service.cs.bean.OTEsitoOperazione;
import it.eng.auriga.opentext.service.cs.util.IContentServerCathegoryType;

public class DocumentServiceImpl extends AbstractManageCSService implements DocumentService {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

	private String endpointCS;
	private String endpointDM;

	public DocumentServiceImpl(String endpointCS, String endpointDM) {
		super();
		this.endpointCS = endpointCS;
		this.endpointDM = endpointDM;
	}

	public DocumentServiceImpl() {
		super();
	}

	public OTDocumentContent getDocumentContent(String otToken, Long documentId) throws ContentServerException {
		return getDocumentContent(otToken, documentId, null);
	}
	// public DocumentServiceImpl() {
	// settingcs = new ContentServerSettings();
	// soapHeaderService = new SoapHeaderServiceImpl();
	// contentService = new ContentServiceImpl();
	// }

	public OTDocumentContent getDocumentContent(String otToken, Long documentId, Long versionId)
			throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");

		OTDocumentContent documentInfo = new OTDocumentContent();
		try {

			Node documentNode = this.getNodeById(otToken, documentId);

			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Recuperato documento con id "
					+ documentId);

			long documentVersion;
			if (versionId == null) {
				documentVersion = documentNode.getVersionInfo().getVersionNum();
			} else {
				documentVersion = versionId;
			}

			String documentContextId = this.getVersioneContentContext(otToken, documentId, documentVersion);

			Version documentInformation = this.getVersionInfoDocument(otToken, documentId, documentVersion);
			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
					+ " >> Recuperata versione documento con id " + documentId);
			String documentName = documentInformation != null ? documentInformation.getFilename() : null;
			String documentType = documentInformation != null ? documentInformation.getMimeType() : null;

			if (contentService == null) {
				contentService = new ContentServiceImpl(endpointCS, endpointDM);
			}
			byte[] documentContent = contentService.downloadContent(otToken, documentContextId);
			documentInfo.setContent(documentContent);

			documentInfo.setMimeType(documentType);

			documentInfo.setName(documentName);

		}

		catch (Exception e) {
			logger.error("Eccezione nel recupero del documento per id " + documentId + " " + e.getMessage(), e);
			throw new ContentServerException(EsitoEnum.ESITO_KO_1.getDescrizioneEsito());
		}

		return documentInfo;
	}

	private DocumentManagement getDocumentManagement(String otToken, String endpointDM) throws ContentServerException {
		if (managementCSClient == null)
			managementCSClient = getManagementCSClient(endpointCS, endpointDM);
		logger.info("managementCSClient " + managementCSClient);
		DocumentManagement documentMan = managementCSClient.getDocumentManagement(endpointDM);
		managementCSClient.updateClientWithAuthorization(otToken, (WSBindingProvider) documentMan);
		return documentMan;
	}

	public void removeDocument(String otToken, Long documentId) throws ContentServerException {

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
		docMan.deleteNode(documentId);

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> Cancellazione avvenuta con successo per documento con id " + documentId);

	}

	public void updateDocument(String otToken, Node node)
			throws ContentServerException, InsufficientPermissionException {

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);

		try {
			docMan.updateNode(node);

			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
					+ " >> Aggiornamento avvenuto con successo per documento con id " + node.getID());
		} catch (Exception e) {
			if (e instanceof ServerSOAPFaultException
					&& e.getLocalizedMessage().contains("Insufficient permission for modify operation"))
				throw new InsufficientPermissionException();

		}

	}

	public Node getNodeById(String otToken, Long documentId) throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		DocumentManagement docMan = getDocumentManagement(otToken, endpointDM);
		Node node = docMan.getNode(documentId);

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> Recupero avvenuto con successo per documento con id " + documentId);

		return node;
	}

	public Node getNodeByPath(String otToken, List<String> folderPath) throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
		Node rootNode = getRootNode(otToken);
		return docMan.getNodeByPath(rootNode.getID(), folderPath);

	}

	public String getVersioneContentContext(String otToken, Long documentId, Long documentVersion)
			throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		DocumentManagement docMan = getDocumentManagement(otToken, endpointDM);
		String versioneContesto = docMan.getVersionContentsContext(documentId, documentVersion);

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> Recupero versione contesto avvenuto con successo per documento con id " + documentId);

		return versioneContesto;
	}

	public Version getVersionInfoDocument(String otToken, Long documentId, Long documentVersion)
			throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		DocumentManagement docMan = getDocumentManagement(otToken, endpointDM);
		Version documentInformation = docMan.getVersion(documentId, documentVersion);

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> Recupero info versione avvenuto con successo per documento con id " + documentId);

		return documentInformation;
	}

	public AttributeGroup getCategoryTemplate(String otToken, Long idCathegory) throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
		AttributeGroup templateCategoria = docMan.getCategoryTemplate(idCathegory);

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> Recupero tempalte categoria  avvenuto con successo per  id " + idCathegory);

		return templateCategoria;
	}

	public void updateMetadataNode(String otToken, Long cathegoryId, Long documentId, Map<String, Object> metadataMap)
			throws ContentServerException, InsufficientPermissionException {

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> *********Id del documento da aggiornare : " + documentId);

		Node node = this.getNodeById(otToken, documentId);
		List<AttributeGroup> attributeGropups = node.getMetadata().getAttributeGroups();
		AttributeGroup categoryToEdit = null;

		AttributeGroup cathegory = getCategoryTemplate(otToken, cathegoryId);

		for (AttributeGroup atG : attributeGropups) {

			if (atG.getType().equals("Category") && atG.getDisplayName().equals(cathegory.getDisplayName())) {
				categoryToEdit = atG;
				logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
						+ " >> *********Categoria del del documento da aggiornare >>>>>> TROVATA");
				break;
			}

		}

		Map<String, IContentServerCathegoryType> mapCathegoryType = this.buildMapCathegoryType();

		for (DataValue propertyp : categoryToEdit.getValues()) {
			try {
				IContentServerCathegoryType contentServerCathegoryType = mapCathegoryType
						.get(propertyp.getClass().getName());

				if (metadataMap.get(propertyp.getDescription()) != null) {
					contentServerCathegoryType.setDataValue(propertyp, metadataMap.get(propertyp.getDescription()));
					logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Proprietà "
							+ propertyp.getDescription() + " aggiornata correttamente");

				} else
					logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Proprietà "
							+ propertyp.getDescription() + " non aggiornata perchè non presente in input");
				// **************************************");
			} catch (DatatypeConfigurationContentServerException de) {
				logger.error(de.getMessage(), de);
				throw new ContentServerException(de.getMessage());

			}

		}

		this.updateDocument(otToken, node);

	}

	public Metadata createMetadataForNewDoc(String otToken, Long idCathegory, Map<String, Object> metadataMap,
			List<FaseMetadataBean> elencoFasi) throws ContentServerException {

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");

		AttributeGroup templateCategoria = this.getCategoryTemplate(otToken, idCathegory);

		Metadata metadata = new Metadata();

		Map<String, IContentServerCathegoryType> mapCathegoryType = this.buildMapCathegoryType();
		for (DataValue propertyp : templateCategoria.getValues()) {

			if (propertyp.getClass().getName().contains("com.opentext.livelink.service.documentmanagement.TableValue")
					&& elencoFasi != null) {

				// creo la mappa che contiene le key da associare alle
				// proprietà presenti nel
				// Row
				Map<String, String> rowValueKey = new HashMap<String, String>();
				List<RowValue> rows = ((TableValue) propertyp).getValues();

				for (RowValue row : rows) {
					for (DataValue data : row.getValues()) {
						rowValueKey.put(data.getDescription(), data.getKey());
					}
				}

				((TableValue) propertyp).getValues().clear();

				// Verificare se è obbligatorio aggiungere una FASE anche se non presente
				// if (elencoFasi.size() == 0)
				// elencoFasi.add(new FaseMetadataBean());

				for (FaseMetadataBean bean : elencoFasi) {
					RowValue row = new RowValue();

					try {
						for (Field field0 : bean.getClass().getDeclaredFields()) {
							Method methodName = bean.getClass().getMethod("get" + field0.getName());
							StringValue s = new StringValue();
							s.getValues().add((String) methodName.invoke(bean));
							s.setDescription(field0.getName());
							s.setKey(rowValueKey.get(field0.getName()));
							row.getValues().add(s);
						}
						((TableValue) propertyp).getValues().add(row);

					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw new ContentServerException();
					}

				}

			}

			else {

				try {

					IContentServerCathegoryType contentServerCathegoryType = mapCathegoryType
							.get(propertyp.getClass().getName());
					if (metadataMap.get(propertyp.getDescription()) != null) {
						contentServerCathegoryType.setDataValue(propertyp, metadataMap.get(propertyp.getDescription()));
						logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Proprietà "
								+ propertyp.getDescription() + " aggiornata correttamente");

					} else
						logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Proprietà "
								+ propertyp.getDescription() + " non aggiornata perchè non presente in input");
					// **************************************");
				} catch (DatatypeConfigurationContentServerException de) {
					logger.error(de.getMessage(), de);
					throw new ContentServerException();

				}
			}

		}
		metadata.getAttributeGroups().add(templateCategoria);
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> Metadati creati correttamente per categoria " + templateCategoria.getDisplayName());
		return metadata;

	}

	public AttributeGroup addParentMetadataToDocument(String otToken, long docId, String confidentialityLevel)
			throws ContentServerException {
		DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
		List<CategoryInheritance> e = docMan.getCategoryInheritance(docId);
		;
		AttributeGroup cathegory = docMan.getCategoryTemplate(e.get(0).getCategoryID());

		((StringValue) cathegory.getValues().get(0)).getValues().add(confidentialityLevel);
		return cathegory;

	}

	public Node createDocumentAndVersion(String otToken, boolean versionable, String documentName, long parentId,
			Metadata metadataDoc, byte[] contentDocument) throws ContentServerException {

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");

		Node docNode = new Node();
		try {
			docNode.setName(documentName);
			docNode.setIsVersionable(versionable);
			docNode.setType("Document");
			docNode.setParentID(parentId);
			docNode.setMetadata(metadataDoc);
			Attachment attachment = this.buildAttachment(documentName, contentDocument);
			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
			DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
			docNode = docMan.createNodeAndVersion(docNode, attachment);

			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Creazione documento "
					+ documentName + " e contenuto avvenuto con successo");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ContentServerException(e.getMessage());
		}
		return docNode;

	}

	private Attachment buildAttachment(String nameAttachment, byte[] contentAttachment) {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		// creo contenutomar
		Attachment attachment = new Attachment();
		attachment.setContents(contentAttachment);
		GregorianCalendar gcNow = new GregorianCalendar();
		gcNow.setTime(new Date());

		try {
			XMLGregorianCalendar xmlGcNow = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcNow);
			attachment.setCreatedDate(xmlGcNow);
			attachment.setModifiedDate(xmlGcNow);
		} catch (DatatypeConfigurationException d) {

			logger.error("Errore nel settaggio della data sul documento " + d.getMessage(), d);

		}

		attachment.setFileName(nameAttachment);
		attachment.setFileSize(contentAttachment.length);
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Creazione contenuto documento "
				+ nameAttachment + " avvenuta con successo");
		return attachment;

	}

	public Node createFolder(String otToken, String folderName, long parentId) throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		Node docFolder = new Node();
		docFolder.setName(folderName);

		docFolder.setType("Folder");
		docFolder.setParentID(parentId);
		DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
		docFolder = docMan.createNode(docFolder);
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Creazione folder con nome "
				+ folderName + " avvenuta con successo");
		return docFolder;
	}

	public Version versionNode(String otToken, long documentId, byte[] contentAttachment, String name_attachemnt)
			throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		Node node = this.getNodeById(otToken, documentId);
		Attachment attachment = null;
		if (name_attachemnt == null || name_attachemnt.equals("")) {
			attachment = this.buildAttachment(node.getName(), contentAttachment);
		} else {
			attachment = this.buildAttachment(name_attachemnt, contentAttachment);
		}
		DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
		Version version = docMan.addVersion(documentId, node.getMetadata(), attachment);
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> Creazione nuova versione per documento " + documentId + " avvenuta con successo");
		return version;
	}

	/**
	 * servizio che restituisce il folder Root
	 * 
	 * @param docMan
	 * @return
	 * @throws ContentServerException
	 */
	public Node getRootNode(String otToken) throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
		Node rootNode = docMan.getRootNode("EnterpriseWS");
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> Recupero root node avvenuto con successo");
		return rootNode;
	}

	/**
	 * servizio che restituisce il folder a fronte del path in input
	 * 
	 * @param docMan
	 * @param path
	 *            -- elenco cartelle
	 * @return
	 * @throws ContentServerException
	 */
	public Node getFolderByPath(String otToken, List<String> path) throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		Node rootNode = this.getRootNode(otToken);
		String fullPath = "";

		for (String currentFolder : path) {
			fullPath += "\\" + currentFolder;
		}
		DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
		Node node = docMan.getNodeByPath(rootNode.getID(), path);
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Recupero documento al path "
				+ fullPath + " avvenuto con successo");
		return node;
	}

	/**
	 * restituisce il node a fronte del nome del nodo e del id del contenitore del
	 * nodo
	 * 
	 * @param docMan
	 * @param parentId
	 * @param name
	 * @return
	 * @throws ContentServerException
	 */
	public Node getNodeByName(String otToken, Long parentId, String name) throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
		Node node = docMan.getNodeByName(parentId, name);
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Recupero documento  " + name
				+ " avvenuto con successo");
		return node;
	}

	public Map<String, Object> retrieveMetadataFromCathegory(String otToken, long documentId, long idCathegory,
			List<String> elencoNomiMetadatiToRetrieve) throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		Node node = this.getNodeById(otToken, documentId);
		Metadata elencoMetadati = node.getMetadata();
		Map<String, Object> elencoMetadatiRecuperati = new HashMap<String, Object>();
		Map<String, IContentServerCathegoryType> mapCathegoryType = this.buildMapCathegoryType();
		AttributeGroup attg = this.getCategoryTemplate(otToken, idCathegory);
		for (AttributeGroup attCath : elencoMetadati.getAttributeGroups()) {
			if (attCath.getType().equals("Category") && attCath.getDisplayName().equals(attg.getDisplayName())) {
				for (DataValue attribute : attCath.getValues()) {
					if (elencoNomiMetadatiToRetrieve.contains(attribute.getDescription())) {

						IContentServerCathegoryType contentServerCathegoryType = mapCathegoryType
								.get(attribute.getClass().getName());
						elencoMetadatiRecuperati.put(attribute.getDescription(),
								contentServerCathegoryType.getDataValue(attribute));

					}
				}

			}

		}
		return elencoMetadatiRecuperati;
	}

	public Version addVersion(String otToken, long documentId, Metadata metadata, Attachment contenuto)
			throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
		Version versione = docMan.addVersion(documentId, metadata, contenuto);
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Versione aggiornata  "
				+ versione.getID() + "  con successo");

		return versione;
	}

	public OTEsitoOperazione createNodeAndUploadContent(String otToken, Metadata metadata,
			OTDocumentContent documentContent, long idParentFolder)
			throws ContentServerException, DuplicateDocumentNameException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");

		String contextId = this.createNodeAndVersionContext(otToken, metadata, documentContent.getName(),
				idParentFolder);

		return contentService.uploadContent(otToken, contextId, documentContent);
	}

	public Node createNode(String otToken, String fileName, long idParentFolder) throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		Node documentNode = new Node();
		documentNode.setName(fileName);

		documentNode.setType(settingcs.getDocumentTypeNode());
		documentNode.setParentID(idParentFolder);
		DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);

		documentNode = docMan.createNode(documentNode);
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Creazione documento con nome "
				+ fileName + " avvenuta con successo");
		return documentNode;

	}

	public String createNodeAndVersionContext(String otToken, Metadata metadata, String fileName, long idParentFolder)
			throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");

		Node documentNode = new Node();
		documentNode.setName(fileName);
		documentNode.setIsVersionable(true);
		documentNode.setType(settingcs.getDocumentTypeNode());
		documentNode.setParentID(idParentFolder);
		documentNode.setMetadata(metadata);

		DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
		String versionContextId = docMan.createNodeAndVersionContext(documentNode);
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> Creazione versione documento con nome " + fileName + " avvenuta con successo");
		return versionContextId;

	}

	public OTEsitoOperazione checkoutNode(String otToken, String user, Long documentId) {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		logger.info("Checkout eseguito ad opera dell'utente " + user);
		OTEsitoOperazione otEsitoOperazione = new OTEsitoOperazione();
		try {
			User utenteLoggato = memberServiceClient.retrieveLoggedUser(otToken);
			DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
			docMan.reserveNode(documentId, utenteLoggato.getID());

		} catch (Exception e) {
			otEsitoOperazione.setCodiceEsito(EsitoEnum.ESITO_KO_2.getCodiceEsito());
			otEsitoOperazione.setDescrizioneEsito(EsitoEnum.ESITO_KO_2.getDescrizioneEsito());
		}

		return otEsitoOperazione;
	}

	public OTEsitoOperazione checkinNode(String otToken, String user, Long documentId, Metadata metadata,
			Attachment attachment) {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		logger.info("Checkin eseguito ad opera dell'utente " + user);
		OTEsitoOperazione otEsitoOperazione = new OTEsitoOperazione();
		try {

			this.addVersion(otToken, documentId, metadata, attachment);
			DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);

			docMan.unreserveNode(documentId);

		} catch (Exception e) {
			otEsitoOperazione.setCodiceEsito(EsitoEnum.ESITO_KO_2.getCodiceEsito());
			otEsitoOperazione.setDescrizioneEsito(EsitoEnum.ESITO_KO_2.getDescrizioneEsito());
		}

		return otEsitoOperazione;
	}

	public OTEsitoOperazione checkinOnlyContent(String otToken, String user, Long documentId, byte[] attachment) {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		logger.info("Checkin eseguito ad opera dell'utente " + user);
		OTEsitoOperazione otEsitoOperazione = new OTEsitoOperazione();
		try {

			this.versionNode(otToken, documentId, attachment);
			DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);

			docMan.unreserveNode(documentId);

		} catch (Exception e) {
			otEsitoOperazione.setCodiceEsito(EsitoEnum.ESITO_KO_2.getCodiceEsito());
			otEsitoOperazione.setDescrizioneEsito(EsitoEnum.ESITO_KO_2.getDescrizioneEsito());
		}

		return otEsitoOperazione;
	}

	public OTEsitoOperazione undoCheckoutNode(String otToken, String user, Long documentId) {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		logger.info("Undo Checkout eseguito ad opera dell'utente " + user);
		OTEsitoOperazione otEsitoOperazione = new OTEsitoOperazione();
		try {

			DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
			docMan.unreserveNode(documentId);

		} catch (Exception e) {
			otEsitoOperazione.setCodiceEsito(EsitoEnum.ESITO_KO_2.getCodiceEsito());
			otEsitoOperazione.setDescrizioneEsito(EsitoEnum.ESITO_KO_2.getDescrizioneEsito());
		}

		return otEsitoOperazione;
	}

	public OTEsitoOperazione renameDocument(String otToken, Long documentId, String newName)
			throws DuplicateDocumentNameException {

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		logger.info("Rename del work order con id " + documentId);
		OTEsitoOperazione otEsitoOperazione = new OTEsitoOperazione();
		try {

			DocumentManagement docMan = this.getDocumentManagement(otToken, endpointDM);
			docMan.renameNode(documentId, newName);

		} catch (Exception e) {
			otEsitoOperazione.setCodiceEsito(EsitoEnum.ESITO_KO_2.getCodiceEsito());
			otEsitoOperazione.setDescrizioneEsito(EsitoEnum.ESITO_KO_2.getDescrizioneEsito());
			if (e instanceof ServerSOAPFaultException && e.getLocalizedMessage().contains("DocMan.DuplicateName"))
				throw new DuplicateDocumentNameException();
		}

		return otEsitoOperazione;

	}

	public List<Node> getNodes(String otToken, List<Long> documentIds) throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		DocumentManagement docMan = getDocumentManagement(otToken, endpointDM);
		return docMan.getNodes(documentIds);

	}

	public String getEndpointCS() {
		return endpointCS;
	}

	public void setEndpointCS(String endpointCS) {
		this.endpointCS = endpointCS;
	}

	public String getEndpointDM() {
		return endpointDM;
	}

	public void setEndpointDM(String endpointDM) {
		this.endpointDM = endpointDM;
	}

	@Override
	public Version versionNode(String otToken, long documentId, byte[] contentAttachment)
			throws ContentServerException {
		// TODO Auto-generated method stub
		return versionNode(otToken, documentId, contentAttachment, null);
	}

	@Override
	public OTDocumentContent getDocumentContent(String otToken, String documentIdAndversion)
			throws ContentServerException {
		if (documentIdAndversion != null && documentIdAndversion.contains("_v")) {
			String tokenVer[] = documentIdAndversion.split("_v");
			return getDocumentContent(otToken, Long.decode(tokenVer[0]), Long.decode(tokenVer[1]));
		} else {
			throw new ContentServerException("id non valido(non è presente separatore _v) o nullo");
		}
	}

}
