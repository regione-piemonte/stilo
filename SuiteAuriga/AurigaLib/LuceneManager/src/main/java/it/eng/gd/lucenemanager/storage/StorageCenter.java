/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;

import java.io.InputStream;

/**
 * classe per l'archiviazione dei file di Lucene
 * 
 * @author jravagnan
 * 
 */
public class StorageCenter {

	private static final String PROGRAM_PREFIX = "LuceneArchive";

	/**
	 * Restituisce lo storage di base da utilizzare 
	 * 
	 * @return
	 */
	public StorageService getGlobalStorage() {
		return StorageServiceImpl.newInstance(new GenericStorageInfo() {
			public String getUtilizzatoreStorageId() {
				return PROGRAM_PREFIX;
			}
		});
	}

	public InputStream extract(String urlFile) throws Throwable {
		return getGlobalStorage().extract(urlFile);
	}

}
