/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.job.importdocindex.bean.HandlerResultBean;
import it.eng.job.importdocindex.exceptions.ImportDocIndexException;

/**
 * Interfaccia con i metodi delle operazioni da eseguire per l'elaborazione del file indice
 * 
 * @author Mattia Zanetti
 *
 */

public interface FileIndiceManager {

	public static final String ADDITIONAL_INFORMATION_ARCHIVIAZIONE = "infoArchiviazione";
	public static final String ADDITIONAL_INFORMATION_ID_FOGLIO = "idFoglio";

	public HandlerResultBean<Void> processaFileIndice() throws ImportDocIndexException;

}