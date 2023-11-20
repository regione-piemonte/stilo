/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadInput;

/**
 * Callback utilizzata da {@link PrettyFileUploadInput}
 * in fase di inizializzazione, il quale ne chiama il metodo 
 * uploadEnd
 * @author Rametta
 *
 */
public abstract interface UploadItemCallBackHandler {

	/**
	 * Chiamata effettuata alla fine dell'upload del file,
	 * quando sono disponibili il nome del file ed il relativo uri
	 * @param displayFileName
	 * @param uri
	 */
	public abstract void uploadEnd(String displayFileName, String uri);

	/**
	 * Gestione della situazione di errore. Il file non è stato portato correttamente
	 */
	public abstract void manageError(String error);
	
	
}
