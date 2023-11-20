/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.file.PrettyFileMultipleUploadInput;

/**
 * Callback utilizzata da {@link PrettyFileMultipleUploadInput}
 * in fase di inizializzazione, il quale ne chiama il metodo 
 * uploadEnd
 * @author Cristiano
 *
 */

public abstract class UploadMultipleItemCallBackHandler {
	
	/**
	 * Chiamata effettuata alla fine dell'upload multiplo del file,
	 * quando sono disponibili il nome del file ed il relativo uri
	 * @param displayFileName
	 * @param uri
	 */
	public abstract void uploadEnd(String displayFileName, String uri, String numFileCaricatiInUploadMultiplo);

	/**
	 * Gestione della situazione di errore. Il file non è stato portato correttamente
	 */
	public abstract void manageError(String error);
	
	/**
	 * Gestione della cancellazione dell'upload
	 */
	public void cancelMultipleUpload() {
		
	}

}
