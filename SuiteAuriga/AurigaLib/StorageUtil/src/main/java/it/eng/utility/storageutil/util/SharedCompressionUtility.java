/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

public class SharedCompressionUtility {

	/**
	 * Metodo che, dato il file di input, ritorna il path completo (comprensivo
	 * del nuovo nome) con anche la nuova estenzione che si deve assegnare
	 * all'archivio compresso
	 * 
	 * @return path completo del file di output comprensivo del nome e
	 *         del tipoCompressione 
	 */
	public static String getFileOutputPath(File fileIn, String tipoCompressione) {

		String fileName = "";
		if (fileIn != null) {
			String pathWithoutName = fileIn.getPath().replace(fileIn.getName(), "");
			if (fileIn.getName() != null && fileIn.getName().lastIndexOf(".") != -1) {
				fileName = fileIn.getName().substring(0, fileIn.getName().lastIndexOf("."));
			} else {
				/*
				 * Vuol dire che ci si trova nella situazione in cui il file ha
				 * nome senza tipoCompressione
				 */
				fileName = fileIn.getName();
			}

			return pathWithoutName + fileName + "." + tipoCompressione;
		}
		return "";
	}

}
