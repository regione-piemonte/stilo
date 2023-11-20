/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;

import java.io.File;
import java.util.Locale;

public class RecuperoFile {

	public FileExtractedOut extractfile(Locale locale, AurigaLoginBean mLoginBean, FileExtractedIn lFileExtractedIn) throws StorageException {
		
		FileExtractedOut lFileExtractedOut = new FileExtractedOut();
		
		// Recupero file da Storage Esterno e non da FS - DOCER
		if(lFileExtractedIn != null && lFileExtractedIn.getUri() != null && lFileExtractedIn.getUri().toUpperCase().startsWith("[DOCER@")) {
			File fileDocer = StorageImplementation.getStorage().extractFile(lFileExtractedIn.getUri());
			String uriTemp = StorageImplementation.getStorage().store(fileDocer);
			File fileTemp = StorageImplementation.getStorage().extractFile(uriTemp);
			lFileExtractedOut.setExtracted(fileTemp);
		} else {
			lFileExtractedOut.setExtracted(StorageImplementation.getStorage().extractFile(lFileExtractedIn.getUri()));
		}
		
		return lFileExtractedOut;
	}

}