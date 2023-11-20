/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;

import org.apache.log4j.Logger;

import it.eng.auriga.compiler.utility.TemplateStorage;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;

/**
 * @author Federico Cacco
 * 
 * Imnplementazione lato AurigaWeb di TemplateStorage.
 * Raccoglie metodi di utilità per l'accesso ai file durante la creazione del docuento a partire dal template. 
 *
 */
public class TemplateStorageAurigaWebImpl implements TemplateStorage{

	private static Logger logger = Logger.getLogger(TemplateStorageAurigaWebImpl.class);
	
	@Override
	public File extractFile (String storageTemplateUri) throws StorageException{
		return StorageImplementation.getStorage().extractFile(storageTemplateUri);
	}
	
	@Override
	public InputStream extract (String storageTemplateUri) throws StorageException{
		return StorageImplementation.getStorage().extract(storageTemplateUri);
	}
	
	@Override
	public File getRealFile (String uri) throws StorageException{
		return StorageImplementation.getStorage().getRealFile(uri);
	}
	
	@Override
	public String store(File fileToStore, String... params) throws StorageException{
		return StorageImplementation.getStorage().store(fileToStore, params);
	}
	
}


