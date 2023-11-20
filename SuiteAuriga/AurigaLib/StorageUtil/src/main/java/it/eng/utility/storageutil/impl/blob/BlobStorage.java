/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.database.store.dmpk_blob.bean.DmpkBlobDeletedocelettronicoBean;
import it.eng.database.store.dmpk_blob.bean.DmpkBlobGetdocelettronicoBean;
import it.eng.database.store.dmpk_blob.bean.DmpkBlobPutdocelettronicoBean;
import it.eng.database.store.dmpk_blob.bean.DmpkBlobUpddocelettronicoBean;
import it.eng.database.store.dmpk_blob.store.Deletedocelettronico;
import it.eng.database.store.dmpk_blob.store.Getdocelettronico;
import it.eng.database.store.dmpk_blob.store.Putdocelettronico;
import it.eng.database.store.dmpk_blob.store.Upddocelettronico;
import it.eng.database.store.result.bean.StorageStoreResultBean;
import it.eng.utility.storageutil.HibernateStorageConfig;
import it.eng.utility.storageutil.Storage;
import it.eng.utility.storageutil.annotations.TipoStorage;
import it.eng.utility.storageutil.enums.StorageType;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.util.XMLUtil;

@TipoStorage(tipo = StorageType.BLOB)
public class BlobStorage implements Storage{

	private HibernateStorageConfig mBlobStorageConfig;
	
	private String idStorage;
	private String utilizzatoreStorage;
	
	private static Logger logger = Logger.getLogger(BlobStorage.class);
	
	@Override
	public void configure(String xmlConfig) throws StorageException {
		try {
			mBlobStorageConfig = (HibernateStorageConfig) XMLUtil.newInstance().deserializeIgnoringDtd(xmlConfig, HibernateStorageConfig.class);
		} catch (Exception e) {
			logger.error("Configurazione storage BLOB", e);
			throw new StorageException(e);
		}	
	}

	@Override
	public String insert(File file, String... params) throws StorageException {
		String id = eseguiPutDocElettronico(null);

		eseguiUpdateDocElettronico(file, id);
		
		return id;
	}

	private String eseguiUpdateDocElettronico(File file, String id) throws StorageException {
		Upddocelettronico lUpddocelettronico = new Upddocelettronico();
		DmpkBlobUpddocelettronicoBean lDmpkBlobUpddocelettronicoBean = new DmpkBlobUpddocelettronicoBean();
		try {
			lDmpkBlobUpddocelettronicoBean.setDocumentoin(FileUtils.readFileToByteArray(file));
		} catch (IOException e) {
			logger.error("Errore nella lettura del file " + file, e);
		}
		lDmpkBlobUpddocelettronicoBean.setIdstoragein( idStorage );
		lDmpkBlobUpddocelettronicoBean.setFlgautocommitin(1);
		lDmpkBlobUpddocelettronicoBean.setIdstoragein(idStorage);
		lDmpkBlobUpddocelettronicoBean.setIddocelettronicoin(id);
		StorageStoreResultBean<DmpkBlobUpddocelettronicoBean> resultPut = null;
		try {
			resultPut = lUpddocelettronico.execute(lDmpkBlobUpddocelettronicoBean, mBlobStorageConfig);
		} catch (Exception e) {
			logger.error("Errore nella chiamata alla stored proc di aggiornamento documento", e);
			throw new StorageException(e);
		}
			
		if (resultPut.isInError()) {
			throw new StorageException(resultPut.getDefaultMessage());
		} else if (StringUtils.isNotEmpty(resultPut.getDefaultMessage())){
			logger.error("Errore aggiornamento file documento: " + resultPut.getDefaultMessage());
		}
		
		return resultPut.getResultBean().getIddocelettronicoin();
	}
	
	private String eseguiUpdateDocElettronico(InputStream is, String id) throws StorageException {
		Upddocelettronico lUpddocelettronico = new Upddocelettronico();
		DmpkBlobUpddocelettronicoBean lDmpkBlobUpddocelettronicoBean = new DmpkBlobUpddocelettronicoBean();
		try {
			lDmpkBlobUpddocelettronicoBean.setDocumentoin(IOUtils.toByteArray(is));
		} catch (IOException e) {
			logger.error("Errore nella lettura dello stream", e);
		}
		lDmpkBlobUpddocelettronicoBean.setFlgautocommitin(1);
		lDmpkBlobUpddocelettronicoBean.setIdstoragein(idStorage);
		lDmpkBlobUpddocelettronicoBean.setIddocelettronicoin(id);
		StorageStoreResultBean<DmpkBlobUpddocelettronicoBean> resultPut = null;
		try {
			resultPut = lUpddocelettronico.execute(lDmpkBlobUpddocelettronicoBean, mBlobStorageConfig);
		} catch (Exception e) {
			logger.error("Errore nella chiamata alla stored proc di aggiornamento documento", e);
			throw new StorageException(e);
		}
			
		if (resultPut.isInError()) {
			throw new StorageException(resultPut.getDefaultMessage());
		} else if (StringUtils.isNotEmpty(resultPut.getDefaultMessage())){
			logger.error("Errore aggiornamento stream documento: " + resultPut.getDefaultMessage());
		}
		
		return resultPut.getResultBean().getIddocelettronicoin();
	}

	private String eseguiPutDocElettronico(File file) throws StorageException {
		Putdocelettronico lPutdocelettronico = new Putdocelettronico();
		DmpkBlobPutdocelettronicoBean lDmpkBlobPutdocelettronicoBean = new DmpkBlobPutdocelettronicoBean();
		if ( file != null ) {
			try {
				lDmpkBlobPutdocelettronicoBean.setDocumentoin(FileUtils.readFileToByteArray(file));
			} catch (IOException e) {
				logger.error("Errore nella lettura del file " + file, e);
			}
		}
		lDmpkBlobPutdocelettronicoBean.setCipartizionein( utilizzatoreStorage );
		lDmpkBlobPutdocelettronicoBean.setIdstoragein( idStorage );
		lDmpkBlobPutdocelettronicoBean.setFlgautocommitin(1);
		StorageStoreResultBean<DmpkBlobPutdocelettronicoBean> resultPut = null;
		try {
			resultPut = lPutdocelettronico.execute(lDmpkBlobPutdocelettronicoBean, mBlobStorageConfig);
		} catch (Exception e) {
			logger.error("Errore nella chiamata alla stored proc di inserimento documento", e);
			throw new StorageException(e);
		}

		if (resultPut.isInError()) {
			throw new StorageException(resultPut.getDefaultMessage());
		} else if (StringUtils.isNotEmpty(resultPut.getDefaultMessage())){
			logger.error("Errore aggiornamento documento: " + resultPut.getDefaultMessage());
		}
			
		return resultPut.getResultBean().getIddocelettronicoout();
	}
	
	private String eseguiPutDocElettronicoStream(InputStream is) throws StorageException {
		Putdocelettronico lPutdocelettronico = new Putdocelettronico();
		DmpkBlobPutdocelettronicoBean lDmpkBlobPutdocelettronicoBean = new DmpkBlobPutdocelettronicoBean();
		if ( is != null ) {
			try {
				lDmpkBlobPutdocelettronicoBean.setDocumentoin(IOUtils.toByteArray(is));
			} catch (IOException e) {
				logger.error("Errore nella lettura dello stream", e);
			}
		}
		lDmpkBlobPutdocelettronicoBean.setCipartizionein( utilizzatoreStorage );
		lDmpkBlobPutdocelettronicoBean.setIdstoragein( idStorage );
		lDmpkBlobPutdocelettronicoBean.setFlgautocommitin(1);
		StorageStoreResultBean<DmpkBlobPutdocelettronicoBean> resultPut = null;
		try {
			resultPut = lPutdocelettronico.execute(lDmpkBlobPutdocelettronicoBean, mBlobStorageConfig);
		} catch (Exception e) {
			logger.error("Errore nella chiamata alla stored proc di inserimento documento", e);
			throw new StorageException(e);
		}
		
		if (resultPut.isInError()) {
			throw new StorageException(resultPut.getDefaultMessage());
		} else if (StringUtils.isNotEmpty(resultPut.getDefaultMessage())){
			logger.error("Errore inserimento documento: " + resultPut.getDefaultMessage());
		}
		
		return resultPut.getResultBean().getIddocelettronicoout();
	}

	@Override
	public String insertInput(InputStream inputStream, String... params) throws StorageException {
		String id = eseguiPutDocElettronicoStream(null);

		eseguiUpdateDocElettronico(inputStream, id);
		
		return id;
	}

	@Override
	public String printMessage(Message lMessage) throws StorageException {
		return null;
	}

	@Override
	public void delete(String id) throws StorageException {
		Deletedocelettronico lDeletedocelettronico = new Deletedocelettronico();
		DmpkBlobDeletedocelettronicoBean lDmpkBlobDeletedocelettronicoBean = new DmpkBlobDeletedocelettronicoBean();
		lDmpkBlobDeletedocelettronicoBean.setIddocelettronicoin(id);
		
		StorageStoreResultBean<DmpkBlobDeletedocelettronicoBean> resultDelete;
		try {
			resultDelete = lDeletedocelettronico.execute(lDmpkBlobDeletedocelettronicoBean, mBlobStorageConfig);
		} catch (Exception e) {
			logger.error("Errore nella chiamata alla stored proc di cancellazione documento", e);
			throw new StorageException(e);
		}
		
		if (resultDelete.isInError()){
			throw new StorageException(resultDelete.getDefaultMessage());
		} else if (StringUtils.isNotEmpty(resultDelete.getDefaultMessage())){
			logger.error("Errore cancellazione documento: " + resultDelete.getDefaultMessage());
		}
	}

	@Override
	public InputStream retrieve(String id) throws StorageException {
		logger.info("Cerco il documento con id " + id);
		StorageStoreResultBean<DmpkBlobGetdocelettronicoBean> resultGet = null;
		try {
			Getdocelettronico lGetdocelettronico = new Getdocelettronico();
			DmpkBlobGetdocelettronicoBean lDmpkBlobGetdocelettronicoBean = new DmpkBlobGetdocelettronicoBean();
			lDmpkBlobGetdocelettronicoBean.setIddocelettronicoin(id);
			lDmpkBlobGetdocelettronicoBean.setIdstoragein( idStorage );
			
			resultGet = lGetdocelettronico.execute(lDmpkBlobGetdocelettronicoBean, mBlobStorageConfig);
			
		} catch (Exception e) {
			logger.error("Errore nella chiamata alla stored proc di ricerca documento", e);
			throw new StorageException(e);
		}

		if (resultGet.isInError()) {
			throw new StorageException(resultGet.getDefaultMessage());
		}
		if (StringUtils.isNotEmpty(resultGet.getDefaultMessage())) {
			logger.error("Errore ricerca documento: " + resultGet.getDefaultMessage());
		}
		
		ByteArrayInputStream bais = null;
		try {
			if ( resultGet.getResultBean().getDocumentoout() != null ) {
				bais = new ByteArrayInputStream( resultGet.getResultBean().getDocumentoout() );
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
	public File retrieveFile(String id) throws StorageException {
		StorageStoreResultBean<DmpkBlobGetdocelettronicoBean> resultGet = null;
		try {			
			Getdocelettronico lGetdocelettronico = new Getdocelettronico();
			DmpkBlobGetdocelettronicoBean lDmpkBlobGetdocelettronicoBean = new DmpkBlobGetdocelettronicoBean();
			lDmpkBlobGetdocelettronicoBean.setIddocelettronicoin(id);
			lDmpkBlobGetdocelettronicoBean.setIdstoragein( idStorage );
			
			resultGet = lGetdocelettronico.execute(lDmpkBlobGetdocelettronicoBean, mBlobStorageConfig);
		} catch (Exception e) {
			logger.error("Errore ", e);
			throw new StorageException(e);
		}
		
		if (resultGet.isInError()){
			throw new StorageException(resultGet.getDefaultMessage());
		}
		if (StringUtils.isNotEmpty(resultGet.getDefaultMessage())){
			logger.error("Errore: " + resultGet.getDefaultMessage());
		}
		
		String idBlob = java.util.UUID.randomUUID().toString();	
		
		try {
			File file = File.createTempFile("blob_"+idBlob+"_", "");
			
			if ( resultGet.getResultBean().getDocumentoout() != null ) {
				logger.debug("Blob recuperato e scritto in " + file.getPath());
				FileUtils.writeByteArrayToFile( file, resultGet.getResultBean().getDocumentoout() );
			} else {
				logger.info("errore documento out null");
			}
			
			return file;
			
		} catch (IOException e) {
			logger.error("Errore scrittura file ricercato", e);
			throw new StorageException(e);
		}
	}

	@Override
	public File retrievRealFile(String id) throws StorageException {
		return retrieveFile(id);
	}

	@Override
	public String writeFileItem(FileItem fileItem) throws StorageException {
		String result = null;
		try {
			result = insertInput( fileItem.getInputStream() );
		} catch (IOException e) {
			logger.error("Errore scrittura FileItem", e);
		}
		return result;
	}

	public void setIdStorage(String idStorage){
		this.idStorage = idStorage;
	}
	
	public void setUtilizzatoreStorage(String idUtilizzatore) {
		this.utilizzatoreStorage = idUtilizzatore;
	}
	
}
