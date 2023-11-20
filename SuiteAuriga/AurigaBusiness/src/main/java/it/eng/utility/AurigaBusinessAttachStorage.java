/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import it.eng.auriga.module.config.ModuleConfig;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;

/**
 * helper per gestire gli attach tramite storageUri
 * 
 * @author mza
 *
 */
public class AurigaBusinessAttachStorage implements IAttachStorage {

	@Override
	public String storeTempFile(File file) throws Exception {
		return getStorageTemp().store(file);
	}

	@Override
	public File extractTempFile(String storageUri) throws Exception {
		return getStorageTemp().extractFile(storageUri);
	}
	
	private static StorageService getStorageTemp() {
		StorageService storageService = StorageServiceImpl.newInstance(new GenericStorageInfo() {
			public String getUtilizzatoreStorageId() {
				ModuleConfig mc = (ModuleConfig) SpringAppContext.getContext().getBean("moduleConfig");
				if (mc != null && mc.getStorages().size() > 0) {
					return mc.getStorages().get("TEMP");
				} else {
					return null;
				}
			}
		});
		return storageService;
	}
	
}

