/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import org.apache.commons.io.FileUtils;

public class StorageDeleter {

	/**
	 * Metodo che esegue la cancellazione del file che viene fornito in input
	 * il cui path deve essere già stato risolto e riferirsi al file fisico.
	 * 
	 * @param uri path risolto del file che deve essere cancellato
	 * @return
	 */
	public static Boolean deleteFileResolved(String uri){
		return deleteFileResolved(new File(uri));
	}
	
	public static Boolean deleteFileResolved(File file){
		
		FileUtils.deleteQuietly(file);
		
		try {
			//In questo caso l'indirizzo del file è già stato risolto, quindi non serve farlo ulteriormente.
			return file.exists();
		} catch (Exception e) {
			return new Boolean(false);
		}
	}
}
