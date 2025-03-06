package it.eng.utility;

import java.io.File;

/**
 * helper per gestire gli attach tramite storageUri
 * 
 * @author mza
 *
 */
public interface IAttachStorage {
	
	public String storeTempFile(File file) throws Exception;
	
	public File extractTempFile(String storageUri) throws Exception;
	
}

