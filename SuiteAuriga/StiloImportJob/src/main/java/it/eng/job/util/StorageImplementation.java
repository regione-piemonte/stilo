/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.invoke.MethodHandles;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.job.SpringHelper;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;

public class StorageImplementation {

	private static StorageService storageService;
	final static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public static void init() {
		logger.debug(">>> Inizializzo lo storage");
		storageService = StorageServiceImpl.newInstance(new GenericStorageInfo() {

			public String getUtilizzatoreStorageId() {
				// ModuleConfig mc = (ModuleConfig) SpringHelper.getMainApplicationContext().getBeansOfType(ModuleConfig.class).get("moduleConfig");
				ModuleConfig mc = (ModuleConfig) SpringHelper.getMainApplicationContext().getBean("storageConfig", ModuleConfig.class);
				logger.debug("Recuperato module config");
				if (mc != null && StringUtils.isNotBlank(mc.getIdDbStorage())) {
					logger.debug("Id Storage vale " + mc.getIdDbStorage());
					return mc.getIdDbStorage();
				} else {
					logger.error("L'identificativo del DB di storage non è correttamente configurato");
					return null;
				}
			}
		});
	}

	public static StorageService getStorage() {
		if (storageService == null) {
			init();
		}
		return storageService;
	}

	/**
	 * Ritorna il servizio di storage associato al parametro idUtilizzatoreStorage
	 * 
	 * @param idUtilizzatoreStorage
	 * @return StorageService
	 */
	public static StorageService getStorageDaUtilizzatore(final String idUtilizzatoreStorage) {

		StorageService storage = null;

		storage = StorageServiceImpl.newInstance(new GenericStorageInfo() {

			public String getUtilizzatoreStorageId() {

				logger.debug("Id Utilizzatore Storage: " + idUtilizzatoreStorage);

				if (StringUtils.isNotBlank(idUtilizzatoreStorage)) {
					return idUtilizzatoreStorage;
				} else {
					logger.error("Id Utilizzatore Storage non corretto");
					return null;
				}
			}
		});

		return storage;
	}

}
