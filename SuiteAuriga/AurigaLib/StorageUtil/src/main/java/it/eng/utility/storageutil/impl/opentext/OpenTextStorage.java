/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;


import it.eng.auriga.opentext.exception.AuthenticationException;
import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.service.cs.AuthenticationCSService;
import it.eng.auriga.opentext.service.cs.DocumentService;
import it.eng.auriga.opentext.service.cs.bean.OTAuthenticationInfo;
import it.eng.auriga.opentext.service.cs.bean.OTDocumentContent;
import it.eng.auriga.opentext.service.cs.impl.AuthenticationCSServiceImpl;
import it.eng.auriga.opentext.service.cs.impl.DocumentServiceImpl; 

import it.eng.utility.storageutil.Storage; 
import it.eng.utility.storageutil.annotations.TipoStorage;
import it.eng.utility.storageutil.enums.StorageType;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.util.XMLUtil; 

@TipoStorage(tipo = StorageType.OPENTEXT)
public class OpenTextStorage implements Storage {

	private OpenTextStorageConfig openTextStorageConfig;

	private String username;
	private String password;

	private String endpointAuth;
	private String endpointCS;
	private String endpointDM;

	private String idStorage;
	private String utilizzatoreStorage;

	private static Logger logger = Logger.getLogger(OpenTextStorage.class);

	@Override
	public void configure(String xmlConfig) throws StorageException {
		try {
			openTextStorageConfig = (OpenTextStorageConfig) XMLUtil.newInstance().deserializeIgnoringDtd(xmlConfig,
					OpenTextStorageConfig.class);
			if (openTextStorageConfig != null) {
				this.username = openTextStorageConfig.getUsername();
				this.password = openTextStorageConfig.getPassword();

				this.endpointAuth = openTextStorageConfig.getWsdlLocationAuthentication();
				this.endpointCS = openTextStorageConfig.getWsdlLocationContentService();
				this.endpointDM = openTextStorageConfig.getWsdlLocationDocumentManager();


			}
		} catch (Exception e) {
			logger.warn("Eccezione configurazione SharePointStorageConfig", e);
			throw new StorageException(e);
		}

	}

	@Override
	public String insert(File file, String... params) throws StorageException {
		logger.debug("Metodo insert");

//		AuthenticationCSService authenticationCSService = new AuthenticationCSServiceImpl(endpointAuth);
//		OTAuthenticationInfo otAuthenticationInfo;
//
//		try {
//			otAuthenticationInfo = authenticationCSService.executeAuthentication(username, password);
//
//
//			String otToken = otAuthenticationInfo.getToken();
//
//			List<String> pathFolder = FeatureUtility.buildAnnoMese();
//			logger.debug("pathFolder " + pathFolder);
//
//			DocumentService documentService = new DocumentServiceImpl(endpointCS, endpointDM);
//			Node rootNode = documentService.getRootNode(otToken);
//			logger.debug("e" + rootNode.getName());
//			
//			Node parentNode = rootNode;
//			//
//			Node folderToCreate = null;
//			// crea path anno mese giorno; controlla prima esistenza
//			for (String nameFolderToCreate : pathFolder) {
//				folderToCreate = documentService.getNodeByName(otToken, parentNode.getID(), nameFolderToCreate);
//				if (folderToCreate == null) {
//					folderToCreate = documentService.createFolder(otToken, nameFolderToCreate, parentNode.getID());
//				}
//				parentNode = folderToCreate;
//			}
//			
//			Map<String, Object> metadataAurigaMap = new HashMap<String, Object>();
//			metadataAurigaMap.put("tipo_protocollo", "entrata");
//			metadataAurigaMap.put("nro_protocollo", "1231");
//
//			List<FaseMetadataBean> elencoFasi = new ArrayList<FaseMetadataBean>();
//			Metadata metadataDoc = documentService.createMetadataForNewDoc(otToken, new Long(params[0]), metadataAurigaMap,
//					elencoFasi);
//
//			// inserimento in ot
//			// System.out.println("Inserimento documento per idDoc: [" + idDoc + "]");
//
//			byte[] contentDocument = FileUtils.readFileToByteArray(file);
//			// // controllo e creazione path secodno convenzione
//			// /PROTOCOLLO/ANNO/MESE/GIORNO
//			// long idParentDirectory = getParentNodeDirectory(token, documentService);
//
//			Node node = documentService.createDocumentAndVersion(otToken, true, params[1], parentNode.getID(),
//					metadataDoc, contentDocument);
//			long idOT = node.getID();
//			logger.debug("Inserito documento con id [" + idOT + "]");// per idDoc: [" + idDoc + "]");
//
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (AuthenticationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ContentServerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return null;
	}

	@Override
	public String insertInput(InputStream inputStream, String... params) throws StorageException {
		logger.debug("Metodo insertInput");


		return null;
	}

	@Override
	public String printMessage(Message lMessage) throws StorageException {
		return null;
	}

	@Override
	public void delete(String id) throws StorageException {
		logger.debug("Metodo delete");
	}

	@Override
	public InputStream retrieve(String id) throws StorageException {
		logger.debug("Start retrieve File Stream");
		logger.info("Cerco il documento con id " + id);
		
		InputStream contentStream = null;
		try {
			AuthenticationCSService authenticationCSService = new AuthenticationCSServiceImpl(endpointAuth);
			OTAuthenticationInfo otAuthenticationInfo;

			otAuthenticationInfo = authenticationCSService.executeAuthentication(username, password);

			String otToken = otAuthenticationInfo.getToken();
			DocumentService documentService = new DocumentServiceImpl(endpointCS, endpointDM);
			OTDocumentContent otDocumentContent = documentService.getDocumentContent(otToken, id);
			otDocumentContent.getContent();
			contentStream = new ByteArrayInputStream(otDocumentContent.getContent());

			return contentStream;
		} catch (ContentServerException e) {
			logger.error(e);
			throw new StorageException(e);
		} catch (MalformedURLException e) {
			logger.error(e);
			throw new StorageException(e);
		} catch (AuthenticationException e) {
			logger.error(e);
			throw new StorageException(e);
		}
	}

	@Override
	public File retrieveFile(String id) throws StorageException {
		logger.debug("Start retrieve File");
		logger.info("Cerco il documento con id " + id);
		File content = null;
		try {
			AuthenticationCSService authenticationCSService = new AuthenticationCSServiceImpl(endpointAuth);
			OTAuthenticationInfo otAuthenticationInfo;

			otAuthenticationInfo = authenticationCSService.executeAuthentication(username, password);

			String otToken = otAuthenticationInfo.getToken();
			DocumentService documentService = new DocumentServiceImpl(endpointCS, endpointDM);
			OTDocumentContent otDocumentContent = documentService.getDocumentContent(otToken, id);
			byte[] datiFile = otDocumentContent.getContent();

			if (datiFile != null) {
				try {
					content = getFile(datiFile, otDocumentContent.getName());
				} catch (Exception e) {
					logger.error("Errore ", e);
					throw new StorageException("Errore nel recupero del file");
				}
			} else {
				throw new StorageException("File non trovato");
			}
		} catch (ContentServerException e) {
			logger.error(e);
			throw new StorageException(e);
		} catch (MalformedURLException e) {
			logger.error(e);
			throw new StorageException(e);
		} catch (AuthenticationException e) {
			logger.error(e);
			throw new StorageException(e);
		}
		return content;
	}

	@Override
	public File retrievRealFile(String id) throws StorageException {
		return retrieveFile(id);
	}

	@Override
	public String writeFileItem(FileItem fileItem) throws StorageException {
		return null;
	}

	public void setIdStorage(String idStorage) {
		this.idStorage = idStorage;
	}

	public void setUtilizzatoreStorage(String idUtilizzatore) {
		this.utilizzatoreStorage = idUtilizzatore;
	}

	private File getFile(byte[] datiFile, String extension) throws IOException {
		File temp = File.createTempFile("temp", extension);
		FileOutputStream out = new FileOutputStream(temp);
		out.write(datiFile);
		out.flush();
		out.close();
		return temp;
	}
}
