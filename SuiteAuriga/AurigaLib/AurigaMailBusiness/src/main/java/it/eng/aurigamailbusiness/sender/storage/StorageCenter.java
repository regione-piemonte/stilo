/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;

import java.io.InputStream;

/**
 * classe per l'archiviazione delle email
 * 
 * @author jravagnan
 * 
 */
public class StorageCenter {

	private static final String PROGRAM_PREFIX = "MailArchive.";

	/**
	 * Restituisce lo storage di base da utilizzare per ogni singola mailBox. Il nome dell'utilizzatore è ottenuto concatenando
	 * "MailArchive." all'id della mailBox
	 * 
	 * @param idMailBox
	 *            L'id che individua ogni singola mailBox
	 * @return
	 */
	public StorageService getGlobalStorage(final String idMailBox) {
		return StorageServiceImpl.newInstance(new GenericStorageInfo() {
			public String getUtilizzatoreStorageId() {
				return PROGRAM_PREFIX + idMailBox;
			}
		});
	}

	/**
	 * Restituisce lo storage da utilizzare per la data operazione di ogni singola mailBox. Il nome dell'utilizzatore è ottenuto
	 * concatenando "MailArchive.", l'id della mailBox, ".", nome dell'operazione
	 * 
	 * @param idMailBox
	 *            L'id che individua ogni singola mailBox
	 * @param operationName
	 *            Il nome dell'operazione
	 * @return
	 */
	public StorageService getOperationStorage(final String idMailBox, final String operationName) {
		return StorageServiceImpl.newInstance(new GenericStorageInfo() {
			public String getUtilizzatoreStorageId() {
				return PROGRAM_PREFIX + idMailBox + "." + operationName;
			}
		});
	}

	public InputStream extract(String idMailBox, String urlFile) throws Throwable {
		return getGlobalStorage(idMailBox).extract(urlFile);
	}

}
