/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;

import it.eng.auriga.compiler.utility.TemplateStorage;
import it.eng.auriga.function.WSFileUtils;

/**
 * @author Federico Cacco
 * 
 * Imnplementazione lato AurigaBusiness di TemplateStorage.
 * Raccoglie metodi di utilità per l'accesso ai file durante la creazione del docuento a partire dal template. 
 * 
 */
public class TemplateStorageAurigaBusinessImpl implements TemplateStorage {

	private static Logger logger = Logger.getLogger(TemplateStorageAurigaBusinessImpl.class);
	
	@Override
	public File extractFile (String storageUri) throws Exception{
		WSFileUtils lWSFileUtils = new WSFileUtils();
		return lWSFileUtils.extract(null, storageUri);
	}
	
	@Override
	public InputStream extract (String storageUri) throws Exception{
		WSFileUtils lWSFileUtils = new WSFileUtils();
		File fileToExtract = lWSFileUtils.extract(null, storageUri);
		if (fileToExtract != null){
			return new FileInputStream(fileToExtract);
		}else{
			return null;
		}
	}
	
	@Override
	public File getRealFile (String storageUri) throws Exception{
		WSFileUtils lWSFileUtils = new WSFileUtils();
		return lWSFileUtils.extract(null, storageUri);
	}
	
	@Override
	public String store(File fileToStore, String... params) throws Exception{
		WSFileUtils lWSFileUtils = new WSFileUtils();
		InputStream inputStreamIn = new FileInputStream(fileToStore);
		return lWSFileUtils.saveInputStreamToStorageTmpAndGetUri(null, inputStreamIn);
	}
	
}
