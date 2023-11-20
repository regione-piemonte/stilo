/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.job.importdocindex.bean.HandlerResultBean;
import it.eng.job.importdocindex.exceptions.ImportDocIndexException;

public interface ScansioneDirectoryManager {

	public static final String ADDITIONAL_INFORMATION_LISTA_FILE_COMPRESSI = "listaFileCompressi";

	public static final String ADDITIONAL_INFORMATION_LISTA_FILE_INDICE = "listaFileIndice";

	public static final String ADDITIONAL_INFORMATION_LISTA_DIRECTORY = "listaDirectory";

	public static final String ADDITIONAL_INFORMATION_DIRECTORY_ESTRAZIONE = "directoryEstrazione";

	/**
	 * Date le configurazioni in input produce una lista di file indice
	 * 
	 * @param config
	 * @return
	 * @throws ImportDocIndexException
	 */

	public HandlerResultBean<Void> trovaFileIndice() throws ImportDocIndexException;

}
