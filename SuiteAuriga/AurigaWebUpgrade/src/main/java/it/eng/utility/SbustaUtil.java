/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.exception.StoreException;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;

import java.io.InputStream;

public class SbustaUtil {

	public static InputStream sbusta(String uriFile, String displayFilename) throws StoreException {
		try {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(uriFile).toURI().toString();
			return lInfoFileUtility.sbusta(fileUrl, displayFilename);		
		}catch(Exception e) {
			throw new StoreException("Impossibile sbustare il file");
		}
	}
	
}
