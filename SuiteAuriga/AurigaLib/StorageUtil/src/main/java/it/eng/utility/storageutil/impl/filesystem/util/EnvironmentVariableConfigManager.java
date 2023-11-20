/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.impl.filesystem.FileSystemStorageConfig;
import it.eng.utility.storageutil.impl.oraclewcc.OracleWccStorageConfig;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * classe di utilita' per la gestione delle variabili d'ambiente
 * 
 * @author jravagnan
 * 
 */
public class EnvironmentVariableConfigManager {
	
	private static Logger log = Logger.getLogger(EnvironmentVariableConfigManager.class);

	public static String replaceEnvironmentVariable(String basePath) throws StorageException {
		if (basePath.startsWith("$")) {
			log.debug("Sono presenti delle variabili d'ambiente da sostituire");
			String variable = null;
			String secondaPartePath = null;
			if (basePath.contains(File.separator)) {
				variable = basePath.substring(0, basePath.indexOf(File.separator));
				secondaPartePath = basePath.substring(basePath.indexOf(File.separator), basePath.length());
			} else {
				if (basePath.contains("/")) {
					variable = basePath.substring(0, basePath.indexOf("/"));
					secondaPartePath = basePath.substring(basePath.indexOf("/"), basePath.length());
				} else {
					variable = basePath;
				}
			}
			String value = System.getenv(variable.substring(1));
			if (StringUtils.isEmpty(value)) {
				value = System.getProperty(variable.substring(1));
			}
			if (StringUtils.isEmpty(value))
				throw new StorageException("Non e' possibile associare un valore alla variabile d'ambiente o proprieta' di sistema: "
						+ variable.substring(1));
			basePath = value + (secondaPartePath != null ? secondaPartePath : "");
		}
		return basePath;
	}

	/**
	 * verifica se le configurazione contengono delle variabili d'ambiente e nel caso ne sostituisce il valore
	 * 
	 * @param config
	 * @throws StorageException
	 */
	public static void verifyPathsForVariable(FileSystemStorageConfig config) throws StorageException {
		String baseFolder = replaceEnvironmentVariable(config.getBaseFolderPath());
		String tempRepositoryPath = replaceEnvironmentVariable(config.getTempRepositoryBasePath());
		String[] repLoc = config.getRepositoryLocations();
		String[] repLocVerified = null;
		if (repLoc != null) {
			repLocVerified = new String[repLoc.length];
			for (int i = 0; i < repLoc.length; i++) {
				repLocVerified[i] = replaceEnvironmentVariable(repLoc[i]);
			}
		}
		config.setBaseFolderPath(baseFolder);
		config.setTempRepositoryBasePath(tempRepositoryPath);
		config.setRepositoryLocations(repLocVerified);
	}

	/**
	 * verifica se le configurazione contengono delle variabili d'ambiente e nel caso ne sostituisce il valore
	 * 
	 * @param config
	 * @throws StorageException
	 */
	public static void verifyPathsForVariable(OracleWccStorageConfig config) throws StorageException {
		String tempRepositoryPath = replaceEnvironmentVariable(config.getTempRepositoryBasePath());
		config.setTempRepositoryBasePath(tempRepositoryPath);
	}

}
