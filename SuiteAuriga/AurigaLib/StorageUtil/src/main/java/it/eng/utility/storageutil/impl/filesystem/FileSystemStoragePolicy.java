/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.storageutil.impl.filesystem;

import it.eng.utility.storageutil.exception.StorageException;

import java.io.File;

public interface FileSystemStoragePolicy {

	public File getStorageFolder(String basePath, int nroMaxFiles) throws StorageException;
	
}
