/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.database.store.dmpk_bfile.bean.DmpkBfileComposebfileuriBean;
import it.eng.database.store.dmpk_bfile.bean.DmpkBfileGetdocelettronicoBean;
import it.eng.database.store.dmpk_bfile.bean.DmpkBfileGetrelativepathBean;
import it.eng.database.store.dmpk_bfile.store.Composebfileuri;
import it.eng.database.store.dmpk_bfile.store.Getdocelettronico;
import it.eng.database.store.dmpk_bfile.store.Getrelativepath;
import it.eng.database.store.result.bean.StorageStoreResultBean;
import it.eng.utility.storageutil.Storage;
import it.eng.utility.storageutil.annotations.TipoStorage;
import it.eng.utility.storageutil.enums.StorageType;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.impl.filesystem.FileSystemStoragePolicy;
import it.eng.utility.storageutil.impl.filesystem.FlatFileSystemStoragePolicy;
import it.eng.utility.storageutil.impl.filesystem.NumericFileSystemStoragePolicy;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.storageutil.util.XMLUtil;

@TipoStorage(tipo = StorageType.BFILE)
public class BfileStorage implements Storage {

	private static Logger logger = Logger.getLogger(BfileStorage.class);
	
	protected final static int DEFAULT_NRO_ZERO_PADDING = 5;

	private BfileStorageConfig storageConfig;
	private String idStorage;
	private String utilizzatoreStorage;
	private FileSystemStoragePolicy storagePolicy;
	private int nroMaxFiles;
	private File baseDir;

	@Override
	public void configure(String xmlConfig) throws StorageException {
		try {
			storageConfig = (BfileStorageConfig) XMLUtil.newInstance().deserializeIgnoringDtd(xmlConfig, BfileStorageConfig.class);
			
			if (storageConfig != null) {
				//numero massimo di file per directory, oltre il quale ne viene creata una nuova
				nroMaxFiles = storageConfig.getNroMaxFiles() > 0 ? storageConfig.getNroMaxFiles() : 0;
				
				//numero di zeri con cui eseguire il padding per creare una nuova directory
				int numZeroPadding = storageConfig.getNroZeroPadding() > 0 ? storageConfig.getNroZeroPadding() : DEFAULT_NRO_ZERO_PADDING;
				
				//policy di creazione nuove directory 
				if (nroMaxFiles == 0) {
					storagePolicy = new FlatFileSystemStoragePolicy();
				} else {
					storagePolicy = new NumericFileSystemStoragePolicy(numZeroPadding);
				}
				
				//directory di base. 
				//Se RootFSDir è vuota, viene restituita la directory di esecuzione del processo
				//Se RootFSDir è nulla, viene lanciata un'eccezione
				baseDir = new File(storageConfig.getRootFSDir());
			}
		} catch (Exception e) {
			logger.error("Configurazione storage BFILE", e);
			throw new StorageException(e);
		}	
	}

	@Override
	public String insert(File file, String... params) throws StorageException {
		InputStream inputStream = null;
		try {
			
			inputStream = FileUtils.openInputStream(file);
			
			return insertInput(inputStream, params);
			
		} catch (Exception e) {
			logger.error("Errore nell'operazione di insert del file", e);
			throw new StorageException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioe) {
					//
				}
			}
		}
	}

	@Override
	public String writeFileItem(FileItem fileItem) throws StorageException {
		InputStream inputStream = null;
		try {
			
			inputStream = fileItem.getInputStream();
			
			return insertInput(inputStream);
			
		} catch (Exception e) {
			logger.error("Errore nell'operazione di scrittura del FileItem", e);
			throw new StorageException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioe) {
					//
				}
			}
		}
	}

	@Override
	public String insertInput(InputStream inputStream, String... params) throws StorageException {
		File primaryDocumentStoredFile = null;
		try {
			String uniqueFileName = StorageUtil.getUniqueFileName();
			File storageFolder = storagePolicy.getStorageFolder(baseDir.getAbsolutePath(), nroMaxFiles);
			logger.debug("andremo a scrivere sul file: " + uniqueFileName + " con storageFolder: " + storageFolder.getAbsolutePath());
			
			primaryDocumentStoredFile = new File(storageFolder, uniqueFileName);

			logger.debug("Inizio scrittura file nell'archivio");
			//gli stream sorgente e destinazione vengono chiusi da FileUtils
			FileUtils.copyInputStreamToFile(inputStream, primaryDocumentStoredFile);
			logger.debug("Fine scrittura file nell'archivio");

			String idFile = primaryDocumentStoredFile.getAbsolutePath()
					.replace(File.separator, "/")
					.substring(baseDir.getAbsolutePath().length());
			logger.debug("Id del file archiviato: " + idFile);
						
			return componiBfileURI(storageConfig.getRootAlias(), idFile);
			
		} catch (Exception e) {
			logger.error("Errore nell'operazione di insert dello stream", e);
			if (primaryDocumentStoredFile != null) {
				FileUtils.deleteQuietly(primaryDocumentStoredFile);
			}
			throw new StorageException(e);
		}
	}
	
	@Override
	public String printMessage(Message lMessage) throws StorageException {
		File primaryDocumentStoredFile = null;
		OutputStream outputStream = null;
		try {
			String uniqueFileName = StorageUtil.getUniqueFileName();
			File storageFolder = storagePolicy.getStorageFolder(baseDir.getAbsolutePath(), nroMaxFiles);
			logger.debug("andremo a scrivere sul file: " + uniqueFileName + " con storageFolder: " + storageFolder.getAbsolutePath());
			
			primaryDocumentStoredFile = new File(storageFolder, uniqueFileName);

			logger.debug("Inizio scrittura file nell'archivio");
			outputStream = FileUtils.openOutputStream(primaryDocumentStoredFile);
			lMessage.writeTo(outputStream);
			logger.debug("Fine scrittura file nell'archivio");

			String idFile = primaryDocumentStoredFile.getAbsolutePath()
					.replace(File.separator, "/")
					.substring(baseDir.getAbsolutePath().length());
			logger.debug("Id del file archiviato: " + idFile);
						
			return componiBfileURI(storageConfig.getRootAlias(), idFile);
			
		} catch (Exception e) {
			logger.error("Errore nell'operazione di archiviazione del mail message", e);
			if (primaryDocumentStoredFile != null) {
				FileUtils.deleteQuietly(primaryDocumentStoredFile);
			}
			throw new StorageException(e);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ioe) {
					//
				}
			}
		}
	}
	
	@Override
	public InputStream retrieve(String idFile) throws StorageException {
		logger.debug("Richesto Bfile " + idFile);
		
		Getdocelettronico store = new Getdocelettronico();
		DmpkBfileGetdocelettronicoBean storeBean = new DmpkBfileGetdocelettronicoBean();
		storeBean.setFileuriin(idFile);
		
		StorageStoreResultBean<DmpkBfileGetdocelettronicoBean> storeResult = null;
		try {
			storeResult = store.execute(storeBean, storageConfig.getHibernateConfiguration());
		} catch (Exception e) {
			logger.error("Errore nella chiamata alla stored proc di recupero documento", e);
			throw new StorageException(e);
		}
		
		if (storeResult.isInError()) {
			throw new StorageException(storeResult.getDefaultMessage());
		} else if (StringUtils.isNotEmpty(storeResult.getDefaultMessage())){
			logger.error("Errore recupero documento: " + storeResult.getDefaultMessage());
		}
		
		ByteArrayInputStream bais = null;
		try {
			if ( storeResult.getResultBean().getFilecontentout() != null ) {
				bais = new ByteArrayInputStream( storeResult.getResultBean().getFilecontentout() );
			} else {
				logger.info("errore documento out null ");
			}
		    
			return bais;
			
		} catch (Exception e) {
			logger.error("Errore nell'apertura dello stream di output", e);
			throw new StorageException(e);
		}
	}
	
	@Override
	public File retrieveFile(String idFile) throws StorageException {
		InputStream inputStream = retrieve(idFile);
		
		try {
			String archivedFilename = idFile.substring(idFile.lastIndexOf('/'));
			
			File file = File.createTempFile("bfile_" + archivedFilename + "_", "");
			
			if ( inputStream != null ) {
				logger.debug("Bfile recuperato e scritto in " + file.getPath());
				FileUtils.copyInputStreamToFile(inputStream, file);
			} else {
				logger.info("errore documento out null");
			}
			
			return file;
			
		} catch (IOException e) {
			logger.error("Errore scrittura file ricercato", e);
			throw new StorageException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioe) {
					//
				}
			}
		}
	}

	/**
	 * Recupera il vero file archiviato (il reale riferimento al file nell'archivio) dall'idFile<br/>
	 * Es: [BFILE@BFILE_DIR]BFILE_DIR_00003/2017092818015721760944643711418568
	 */
	@Override
	public File retrievRealFile(String idFile) throws StorageException {
		File file = getRealId(idFile);
		
		if (file == null || !file.exists()) {
			throw new StorageException(idFile + ": id non valido" + (file != null ? "path: " + file.getPath() + ")" : ""));
		}
		
		return file;
	}
	
	@Override
	public void delete(String idFile) throws StorageException {
		File file = getRealId(idFile);
		
		if (file != null && !FileUtils.deleteQuietly(file)) {
			logger.warn("Non è stato possibile cancellare il file con id " + idFile);
		}
	}


	private String componiBfileURI(String rootAlias, String idFile) throws StorageException {
		Composebfileuri store = new Composebfileuri();
		DmpkBfileComposebfileuriBean storeBean = new DmpkBfileComposebfileuriBean();
		storeBean.setRootdiraliasin(rootAlias);
		storeBean.setFspathfilenamein(idFile);
		
		StorageStoreResultBean<DmpkBfileComposebfileuriBean> storeResult = null;
		try {
			storeResult = store.execute(storeBean, storageConfig.getHibernateConfiguration());
		} catch (Exception e) {
			logger.error("Errore nella chiamata alla stored proc di composizione URI documento", e);
			throw new StorageException(e);
		}
		
		if (storeResult.isInError()) {
			throw new StorageException(storeResult.getDefaultMessage());
		} else if (StringUtils.isNotEmpty(storeResult.getDefaultMessage())){
			logger.error("Errore composizione URI documento: " + storeResult.getDefaultMessage());
		}
		
		return storeResult.getResultBean().getFileuriout();
	}

	private File getRealId(String idFile) throws StorageException {
		File file = null;
		Getrelativepath store = new Getrelativepath();
		DmpkBfileGetrelativepathBean storeBean = new DmpkBfileGetrelativepathBean();
		storeBean.setFileuriin(idFile);
		
		StorageStoreResultBean<DmpkBfileGetrelativepathBean> storeResult = null;
		try {
			storeResult = store.execute(storeBean, storageConfig.getHibernateConfiguration());
		} catch (Exception e) {
			logger.error("Errore nella chiamata alla stored proc di conversione URI documento", e);
			throw new StorageException(e);
		}
		
		if (storeResult.isInError()) {
			throw new StorageException(storeResult.getDefaultMessage());
		} else if (StringUtils.isNotEmpty(storeResult.getDefaultMessage())){
			logger.error("Errore conversione URI documento: " + storeResult.getDefaultMessage());
		}
		
		String relativeFilePath = storeResult.getResultBean().getRelativepathout();
		logger.debug("relativeFilePath: " + relativeFilePath);
		
		if (StringUtils.isNotBlank(relativeFilePath))
			file = new File(storageConfig.getRootFSDir(), relativeFilePath);
		
		return file;
	}
	
	public void setIdStorage(String idStorage){
		this.idStorage = idStorage;
	}
	
	public void setUtilizzatoreStorage(String idUtilizzatore){
		this.utilizzatoreStorage = idUtilizzatore;
	}
	
}
