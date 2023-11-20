/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

import it.csi.siac.documenti.svc._1.ElaboraAttiAmministrativi;
import it.eng.utility.client.contabilia.ricerche.ValidazioneRequestRicerche;

public class ValidazioneRequestDocumenti {
	
	private static final Logger logger = Logger.getLogger(ValidazioneRequestDocumenti.class);
	
	/*
	 * Metodo per controllare parametri di input obbligatori per elaboraAttiAmministrativi
	 */
	public static boolean checkInputElaboraAttiAmministrativi(ElaboraAttiAmministrativi input) {
		boolean result = true;
		
		// controllo annoBilancio obbligatorio
		if (input.getAnnoBilancio() == null) {
			result = false;
			
			logger.error("annoBilancio obbligatorio non può essere null");
		}
		
		// controllo codiceEnte obbligatorio
		if (input.getCodiceEnte() == null) {
			result = false;
				
			logger.error("codiceEnte obbligatorio non può essere null");
		}
		
		// controllo codiceFruitore obbligatorio
		if (input.getCodiceFruitore() == null) {
			result = false;
				
			logger.error("codiceFruitore obbligatorio non può essere null");
		}
		
		String contenutoDocumento = input.getContenutoDocumento();
		
		// controllo lunghezza stringa non minore di 1109
		if (contenutoDocumento.length() < 1109) {
			result = false;
			
			logger.error("contenutoDocumento minore di 1109 caratteri");
		}
		
		// controllo presenza codice di sblocco |0 o |1
		String codiceSblocco = contenutoDocumento.substring(contenutoDocumento.length() - 2);
		if (!codiceSblocco.equals("|0") && !codiceSblocco.equals("|1")) {
			result = false;
			
			logger.error("codice di sblocco non presente, deve essere |0 o |1");
		}
		
		return result;
	}
	
}
