/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.exception.StoreException;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;

import java.io.InputStream;

import org.apache.log4j.Logger;

/**
 * Converte il documento di cui è stato specificato l'uri in storage in un file pdf
 *
 */
public class ConvertToPdfUtil {
	
	private static Logger logger = Logger.getLogger(ConvertToPdfUtil.class);

	/**
	 * 
	 * @param uri posizione del documento in storage 
	 * @param isOdt indica se è true o meno, workaround per evitare una errata interpretazione del mime type da parte di FileOperation
	 * @return input stream legato al pdf generato
	 * @throws StoreException
	 */
	public static InputStream convertToPdf(String uri, boolean isOdt, boolean pdfa) throws StoreException{		
		try {			
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(uri).toURI().toString();
			return lInfoFileUtility.converti(fileUrl, "", isOdt, pdfa);			
		} catch (Exception e) {
			logger.error("Errore: " + e.getMessage(), e);
			throw new StoreException("Non è stato possibile convertire il file");
		}
	}
}
