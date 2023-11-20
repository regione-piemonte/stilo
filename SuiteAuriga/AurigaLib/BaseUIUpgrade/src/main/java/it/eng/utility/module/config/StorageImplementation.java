/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class StorageImplementation {

	private static StorageService storageService;
	private static Logger logger = Logger.getLogger(StorageImplementation.class);
	
	public static void init(){
		storageService = StorageServiceImpl.newInstance(
				new GenericStorageInfo() {			

					public String getUtilizzatoreStorageId() {
						ModuleConfig mc = (ModuleConfig)SpringAppContext.getContext().getBean("moduleConfig");
						logger.debug("Recuperato module config");
						if(mc!=null && StringUtils.isNotBlank(mc.getIdDbStorage())) {
							logger.debug("Id Storage vale " + mc.getIdDbStorage());
							return mc.getIdDbStorage();
						}
						else {
							logger.error("L'identificativo del DB di storage non è correttamente configurato, " +
									"controllare il file di configurazione del modulo.");
							return null;
						}
					}
				}
				);
	}
	
	public static StorageService getStorage(){
		return storageService;
	} 
	
}
