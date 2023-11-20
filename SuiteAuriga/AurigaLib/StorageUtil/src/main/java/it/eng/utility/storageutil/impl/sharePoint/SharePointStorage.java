/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import it.eng.sharepointclient.util.SharePointFileUtils;
import it.eng.sharepointclient.util.SharePointFolderUtils;
import it.eng.sharepointclient.util.SharePointUploadFileUtils;
import it.eng.utility.storageutil.Storage;
import it.eng.utility.storageutil.annotations.TipoStorage;
import it.eng.utility.storageutil.enums.StorageType;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.util.XMLUtil;

@TipoStorage(tipo = StorageType.SHAREPOINT)
public class SharePointStorage implements Storage {

	private SharePointStorageConfig mSharePointConfig;

	private String username;
	private String password;
	private String rootPath;
	private String libraryName;

	private String idStorage;
	private String utilizzatoreStorage;

	private static Logger logger = Logger.getLogger(SharePointStorage.class);

	@Override
	public void configure(String xmlConfig) throws StorageException {
		try {
			mSharePointConfig = (SharePointStorageConfig) XMLUtil.newInstance().deserializeIgnoringDtd(xmlConfig, SharePointStorageConfig.class);
			if (mSharePointConfig != null) {
				this.username = mSharePointConfig.getUsername();
				this.password = mSharePointConfig.getPassword();
				this.rootPath = mSharePointConfig.getRootPath();
				this.libraryName = mSharePointConfig.getLibraryName();
			}
		} catch (Exception e) {
			logger.warn("Eccezione configurazione SharePointStorageConfig", e);
			throw new StorageException(e);
		}

	}

	@Override
	public String insert(File file, String... params) throws StorageException {
		logger.debug("Metodo insert");

		String wsdlLocationCopy = mSharePointConfig.getWsdlLocationCopy();
		String serviceNameCopy = mSharePointConfig.getServiceNameCopy();
		String serviceNamespaceCopy = mSharePointConfig.getServiceNamespaceCopy();
		SharePointUploadFileUtils shUtils = new SharePointUploadFileUtils(wsdlLocationCopy, serviceNameCopy, serviceNamespaceCopy, username, password);

		String destinationUrl = null;
		if (params != null && params.length > 0) {
			String versionPath = getVersioningPath(params);
			logger.info("versionPath " + versionPath);

			if (versionPath == null && getNomeFile(params) == null) {
				throw new StorageException("E' obbligatorio specificare il nome del file");
			}

			try {
				if (versionPath == null) {
					destinationUrl = createFolder(params);
					destinationUrl += "/" + getNomeFile(params);
					logger.info("destinationUrl " + destinationUrl);

					shUtils.uploadFile(file, destinationUrl);

					destinationUrl = destinationUrl.substring(rootPath.length());
					logger.info("destinationUrl " + destinationUrl);
				} else {
					/**
					 * ERRATO
					 */
					versionPath = rootPath + libraryName + versionPath;
					/**
					 * CORRETTO
					 */
					if( versionPath.contains("/CLE") ) {
						Integer index =  versionPath.indexOf("/CLE");
						versionPath = rootPath + libraryName + versionPath.substring(index, versionPath.length());
					} else if( versionPath.contains("/SEC") ) {
						Integer index =  versionPath.indexOf("/SEC");
						versionPath = rootPath + libraryName + versionPath.substring(index, versionPath.length());
					} else if( versionPath.contains("/TEL") ) {
						Integer index =  versionPath.indexOf("/TEL");
						versionPath = rootPath + libraryName + versionPath.substring(index, versionPath.length());
					} else if( versionPath.contains("/MOB") ) {
						Integer index =  versionPath.indexOf("/MOB");
						versionPath = rootPath + libraryName + versionPath.substring(index, versionPath.length());
					} else if( versionPath.contains("/ASS") ) {
						Integer index =  versionPath.indexOf("/ASS");
						versionPath = rootPath + libraryName + versionPath.substring(index, versionPath.length());
					} else if( versionPath.contains("/ENG") ) {
						Integer index =  versionPath.indexOf("/ENG");
						versionPath = rootPath + libraryName + versionPath.substring(index, versionPath.length());
					} else if( versionPath.contains("/FIU") ) {
						Integer index =  versionPath.indexOf("/FIU");
						versionPath = rootPath + libraryName + versionPath.substring(index, versionPath.length());
					} else if( versionPath.contains("/LEO") ) {
						Integer index =  versionPath.indexOf("/LEO");
						versionPath = rootPath + libraryName + versionPath.substring(index, versionPath.length());
					} else if ( versionPath.contains("/ING") ){
						Integer index =  versionPath.indexOf("/ING");
						versionPath = rootPath + libraryName + versionPath.substring(index, versionPath.length());
					} else if ( versionPath.contains("/INF") ){
						Integer index =  versionPath.indexOf("/INF");
						versionPath = rootPath + libraryName + versionPath.substring(index, versionPath.length());
					} else if ( versionPath.contains("/URB") ){
						Integer index =  versionPath.indexOf("/URB");
						versionPath = rootPath + libraryName + versionPath.substring(index, versionPath.length());
					} else {
						Integer index =  versionPath.indexOf("/ADR");
						versionPath = rootPath + libraryName + versionPath.substring(index, versionPath.length());
					}
					logger.error("************ VERSION PATH: "+ versionPath);
					byte[] datiFileVersione = shUtils.getItem(versionPath);
					if (datiFileVersione == null) {
						throw new StorageException("Il documento da versionare non è presente su SharePoint");
					} else {
						logger.info("creo una nuova versione per il file salvato in " + versionPath);
						shUtils.uploadFile(file, versionPath);

						destinationUrl = versionPath.substring(rootPath.length());
						logger.info("destinationUrl " + destinationUrl);
					}
				}
			} catch (Exception e) {
				logger.error("Errore nell'upload del file", e);
				throw new StorageException("Errore nell'upload del file");
			}

		} else {
			throw new StorageException("E' obbligatorio passare dei parametri");
		}
		return destinationUrl;
	}

	@Override
	public String insertInput(InputStream inputStream, String... params) throws StorageException {
		logger.debug("Metodo insert");

		String wsdlLocationCopy = mSharePointConfig.getWsdlLocationCopy();
		String serviceNameCopy = mSharePointConfig.getServiceNameCopy();
		String serviceNamespaceCopy = mSharePointConfig.getServiceNamespaceCopy();
		SharePointUploadFileUtils shUtils = new SharePointUploadFileUtils(wsdlLocationCopy, serviceNameCopy, serviceNamespaceCopy, username, password);

		String destinationUrl = null;
		if (params != null && params.length > 0) {
			String versionPath = getVersioningPath(params);
			logger.info("versionPath " + versionPath);

			if (versionPath == null && getNomeFile(params) == null) {
				throw new StorageException("E' obbligatorio specificare il nome del file");
			}

			File file;
			try {
				file = File.createTempFile("temp", "");

				file.deleteOnExit();
				FileOutputStream out = new FileOutputStream(file);
				IOUtils.copy(inputStream, out);

				if (versionPath == null) {
					destinationUrl = createFolder(params);
					destinationUrl += "/" + getNomeFile(params);
					logger.info("destinationUrl " + destinationUrl);
					shUtils.uploadFile(file, destinationUrl);

					destinationUrl = destinationUrl.substring(rootPath.length());
					logger.info("destinationUrl " + destinationUrl);
				} else {
					/**
					 * ERRATO
					 */
					versionPath = rootPath + libraryName + versionPath;
					/**
					 * CORRETTO
					 */
					Integer index =  versionPath.indexOf("/ADR");
					versionPath = rootPath + libraryName + versionPath.substring(index, versionPath.length());
					logger.error("************ VERSION PATH: "+ versionPath);
					byte[] datiFileVersione = shUtils.getItem(versionPath);
					if (datiFileVersione == null) {
						throw new StorageException("Il documento da versionare non è presente su SharePoint");
					} else {
						logger.info("creo una nuova versione per il file salvato in " + versionPath);
						shUtils.uploadFile(file, versionPath);

						destinationUrl = versionPath.substring(rootPath.length());
						logger.info("destinationUrl " + destinationUrl);
					}
				}
			} catch (IOException e) {
				logger.error("Errore nella creazione del file temporaneo", e);
				throw new StorageException("Errore nella creazione del file temporaneo");
			} catch (Exception e) {
				logger.error("Errore nell'upload del file", e);
				throw new StorageException("Errore nell'upload del file");
			}
		} else {
			throw new StorageException("E' obbligatorio passare dei parametri");
		}
		return destinationUrl;

	}

	@Override
	public String printMessage(Message lMessage) throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) throws StorageException {
		logger.debug("Metodo delete");

		String wsdlLocationList = mSharePointConfig.getWsdlLocationList();
		String serviceNameList = mSharePointConfig.getServiceNameList();
		String serviceNamespaceList = mSharePointConfig.getServiceNamespaceList();
		SharePointFileUtils shUtils = new SharePointFileUtils(wsdlLocationList, serviceNameList, serviceNamespaceList, username, password);
		String fileRef = id;// id.substring(rootPath.length());
		logger.info("fileRef " + fileRef);

		String idDocument = shUtils.getIdDocument(libraryName, id);
		logger.info("IdDocument " + idDocument);
		if (idDocument != null) {
			try {
				shUtils.deleteFile(libraryName, idDocument, id);
			} catch (Exception e) {
				logger.warn("Eccezione SharePointStorageConfig delete", e);
			}
		}

	}

	@Override
	public InputStream retrieve(String id) throws StorageException {
		logger.debug("Start retrieve File");
		logger.info("Cerco il documento con id " + id);

		String wsdlLocationCopy = mSharePointConfig.getWsdlLocationCopy();
		String serviceNameCopy = mSharePointConfig.getServiceNameCopy();
		String serviceNamespaceCopy = mSharePointConfig.getServiceNamespaceCopy();
		SharePointUploadFileUtils shUtils = new SharePointUploadFileUtils(wsdlLocationCopy, serviceNameCopy, serviceNamespaceCopy, username, password);

		byte[] datiFile = null;

		// se l'id inizio con _vti_history significa che si sta richiedendo una precedente versione di un file
		if (id.startsWith("_vti_history")) {
			try {
				id = rootPath + id;
				datiFile = shUtils.getItemVersion(id);
			} catch (IOException e) {
				logger.error("Errore ", e);
				throw new StorageException("si è verificato un errore nel recupero del file");
			}
		} else {
			id = rootPath + id;
			datiFile = shUtils.getItem(id);
		}

		if (datiFile != null) {
			try {
				return new ByteArrayInputStream(datiFile);
			} catch (Exception e) {
				logger.error("Errore ", e);
				throw new StorageException("si è verificato un errore nel recupero del file");
			}
		} else
			throw new StorageException("il file non esiste");
	}

	@Override
	public File retrieveFile(String id) throws StorageException {
		logger.debug("Start retrieve File");
		logger.info("Cerco il documento con id " + id);

		String wsdlLocationCopy = mSharePointConfig.getWsdlLocationCopy();
		String serviceNameCopy = mSharePointConfig.getServiceNameCopy();
		String serviceNamespaceCopy = mSharePointConfig.getServiceNamespaceCopy();
		SharePointUploadFileUtils shUtils = new SharePointUploadFileUtils(wsdlLocationCopy, serviceNameCopy, serviceNamespaceCopy, username, password);

		byte[] datiFile = null;
		String extension = "";
		int i = id.lastIndexOf('.');
		if (i > 0) {
			extension = id.substring(i);
		}

		// se l'id inizio con _vti_history significa che si sta richiedendo una precedente versione di un file
		if (id.startsWith("_vti_history")) {
			try {
				id = rootPath + id;
				datiFile = shUtils.getItemVersion(id);
			} catch (IOException e) {
				logger.error("Errore ", e);
				throw new StorageException("si è verificato un errore nel recupero del file");
			}
		} else {
			id = rootPath + id;
			datiFile = shUtils.getItem(id);
		}

		if (datiFile != null) {
			try {
				return getFile(datiFile, extension);
			} catch (Exception e) {
				logger.error("Errore ", e);
				throw new StorageException("si è verificato un errore nel recupero del file");
			}
		} else
			throw new StorageException("il file non esiste");
	}

	@Override
	public File retrievRealFile(String id) throws StorageException {
		return retrieveFile(id);
	}

	@Override
	public String writeFileItem(FileItem fileItem) throws StorageException {
		try {
			return insertInput(fileItem.getInputStream());
		} catch (IOException e) {
			logger.warn("Eccezione SharePointStorageConfig writeFileItem", e);
			return null;
		}
	}

	public void setIdStorage(String idStorage) {
		this.idStorage = idStorage;
	}

	public void setUtilizzatoreStorage(String idUtilizzatore) {
		this.utilizzatoreStorage = idUtilizzatore;
	}

	private String getVersioningPath(String... params) {
		String path = null;
		for (String param : params) {
			logger.info("param " + param);
			// se viene passato un parametro che inizia con DOC_PATH# stiamo versionando
			// se non viene passato un parametro che inizia con DOC_PATH# stiamo facendo un upload di un nuovo documento (prima versione)
			if (param.startsWith("DOC_PATH#")) {
				path = param.substring("DOC_PATH#".length());
			}
		}
		return path;
	}

	private String createFolder(String... params) {
		String wsdlLocationDws = mSharePointConfig.getWsdlLocationDws();
		String serviceNameDws = mSharePointConfig.getServiceNameDws();
		String serviceNamespaceDws = mSharePointConfig.getServiceNamespaceDws();
		SharePointFolderUtils shFolderUtils = new SharePointFolderUtils(wsdlLocationDws, serviceNameDws, serviceNamespaceDws, username, password);

		String pathCartella = libraryName;
		String destinationUrl = rootPath + libraryName;

		for (String param : params) {
			if (param.startsWith("Folder#")) {
				String folders = param.substring("Folder#".length());
				String[] folderList = folders.split("/");
				for (String folder : folderList) {
					logger.info("folder " + folder);
					if (folder != null && !folder.equalsIgnoreCase("")) {
						pathCartella += "/" + folder;
						logger.info("pathCartella " + pathCartella);
						shFolderUtils.createFolder(pathCartella);
						destinationUrl += "/" + folder;
						logger.info("destinationUrl " + destinationUrl);
					}
				}
			}
		}
		return destinationUrl;
	}

	private String getNomeFile(String... params) {
		String nomeFile = null;
		for (String param : params) {
			if (param.startsWith("FileName#")) {
				nomeFile = param.substring("FileName#".length());
			}
		}
		return nomeFile;
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
