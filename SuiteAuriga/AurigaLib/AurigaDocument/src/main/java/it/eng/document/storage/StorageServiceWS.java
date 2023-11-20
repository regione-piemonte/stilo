/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import org.apache.log4j.Logger;

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.impl.StorageServiceImpl;
import it.eng.utility.storageutil.manager.dao.DaoStorages;
import it.eng.utility.storageutil.manager.dao.DaoUtilizzatoriStorage;
import it.eng.utility.storageutil.manager.entity.Storages;
import it.eng.utility.storageutil.manager.entity.UtilizzatoriStorage;

/**
 * 
 * @author DANCRIST
 *
 * Classe di Beckend per l'implementazione di servizi rest relativi allo storage.
 */

@Service(name="StorageServiceWS")
public class StorageServiceWS {
	
	private static Logger mLogger = Logger.getLogger(StorageServiceWS.class);
	
	@Operation(name = "store")
	public String store(String idUtilizzatoreStorage,File file) throws StorageException{
		StorageService storageService = getStorage(idUtilizzatoreStorage);
		return storageService.store(file);
	}
	
	@Operation(name = "delete")
	public void delete(String idUtilizzatoreStorage,String uri) throws StorageException{
		StorageService storageService = getStorage(idUtilizzatoreStorage);
		storageService.delete(uri);
	}
	
	@Operation(name = "extractFile")
	public File extractFile(String idUtilizzatoreStorage,String uri) throws StorageException{
		StorageService storageService = getStorage(idUtilizzatoreStorage);
		return storageService.extractFile(uri);
	}
	
	@Operation(name = "getRealFile")
	public File getRealFile(String idUtilizzatoreStorage,String uri) throws StorageException{
		StorageService storageService = getStorage(idUtilizzatoreStorage);
		return storageService.getRealFile(uri);
	}
	
	@Operation(name = "getTipoStorage")
	public String getTipoStorage(String idUtilizzatoreStorage) throws Exception{
		mLogger.debug("idStorage: "+idUtilizzatoreStorage);
		Storages storages = null;
		UtilizzatoriStorage utilizzatoriStorage = (idUtilizzatoreStorage != null) ? DaoUtilizzatoriStorage.newInstance().get(idUtilizzatoreStorage) : null;
		if(utilizzatoriStorage != null && utilizzatoriStorage.getStorages() != null &&
				utilizzatoriStorage.getStorages().getIdStorage() != null){
			storages = (utilizzatoriStorage.getStorages().getIdStorage() != null) ?
					DaoStorages.newInstance().get(utilizzatoriStorage.getStorages().getIdStorage()) : null;	
		}
		mLogger.debug("tipo storage: "+storages.getTipoStorage());			
		return storages != null ? storages.getTipoStorage() : null;	
	}
	
	@Operation(name = "getConfigurazioniStorage")
	public String getConfigurazioniStorage(String idUtilizzatoreStorage) throws Exception {
		mLogger.debug("idStorage: "+idUtilizzatoreStorage); 
		Storages storages = null;
		UtilizzatoriStorage utilizzatoriStorage = (idUtilizzatoreStorage != null) ? DaoUtilizzatoriStorage.newInstance().get(idUtilizzatoreStorage) : null;
		if(utilizzatoriStorage != null && utilizzatoriStorage.getStorages() != null &&
				utilizzatoriStorage.getStorages().getIdStorage() != null){
			storages = (utilizzatoriStorage.getStorages().getIdStorage() != null) ?
					DaoStorages.newInstance().get(utilizzatoriStorage.getStorages().getIdStorage()) : null;	
		}
		mLogger.debug("tipo storage: "+storages.getTipoStorage());			
		return storages != null ? storages.getXmlConfig() : null;		
	}
	
	private static StorageService getStorage(final String storage) {
		StorageService storageService = StorageServiceImpl.newInstance(new GenericStorageInfo() {
			public String getUtilizzatoreStorageId() {
				return storage;
			}
		});
		return storageService;
	}
}