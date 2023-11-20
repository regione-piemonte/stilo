/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import it.eng.utility.storageutil.exception.StorageException;

public class FlatFileSystemStoragePolicy implements FileSystemStoragePolicy {

	@Override
	public File getStorageFolder(String basePath, int nroMaxFiles) throws StorageException {
		File folder = new File(basePath);
		folder.mkdirs();
		return folder;
	}

}

