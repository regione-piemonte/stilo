/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * bean di utilità per contenere le configurazioni
 * @author Russo
 *
 */
public class FileOpConfig {
	
	private static final Logger log = LogManager.getLogger(FileOpConfig.class);

	/**
	 * dir temporanea principale all'interno della qulae saranno 
	 * costruite le sotto cartelle per memorizzare i file temporanei, 
	 * il job di delete gira in questa cartella
	 */
	private File tempDirectory;
	
	public File getTempDirectory() {
		if(tempDirectory==null || tempDirectory.equals("")){
			tempDirectory=new File(System.getProperty("java.io.tmpdir")); //se non specificata in conf usa temp
		}
		return tempDirectory;
	}

	public void setTempDirectory(File tempDirectory) {
		this.tempDirectory = tempDirectory;
	}

	public File makeTempDir(String requestKey){
		try {
			String dedicated = StringUtils.remove(UUID.randomUUID().toString(),"-");
			File dedicatedTemporyDirectory = new File(tempDirectory,dedicated);
			log.debug(requestKey + " - Creo la dir temporanea per i files " + dedicatedTemporyDirectory);
			dedicatedTemporyDirectory.mkdirs();
			return dedicatedTemporyDirectory;
		} catch (Exception e) {
			log.error("Errore ", e);
			return null;
		}
	}
	
}
