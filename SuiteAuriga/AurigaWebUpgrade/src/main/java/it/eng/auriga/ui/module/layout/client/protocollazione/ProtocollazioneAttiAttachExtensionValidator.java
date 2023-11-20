/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

/**
 * Valida il nome del file specificato, verificando che l'estensione sia una di
 * quelle consentite
 * 
 * @author massimo malvestio
 *
 */
public class ProtocollazioneAttiAttachExtensionValidator {

	private static ProtocollazioneAttiAttachExtensionValidator instance;

	private ProtocollazioneAttiAttachExtensionValidator() {
	};

	public static ProtocollazioneAttiAttachExtensionValidator getInstance() {

		if (instance == null) {
			instance = new ProtocollazioneAttiAttachExtensionValidator();
		}

		return instance;
	}

	/**
	 * Verifica che il tipo del file caricato sia tra quelli consentiti.
	 * <ul>
	 * <li>doc</li>
	 * <li>doc.p7m</li>
	 * <li>docx</li>
	 * <li>docx.p7m</li>
	 * <li>pdf</li>
	 * <li>pdf.p7m</li>
	 * <li>odt</li>
	 * <li>odt.p7m</li>
	 * <li>xls</li>
	 * <li>xls.p7m</li>
	 * </ul>
	 * 
	 * @param filename
	 *            : nome del file caricato
	 * @return true se il file è di tipo consentito, false altrimenti
	 */
	public boolean validate(InfoFileRecord infoFile) {

		// String correctName = infoFile != null &&
		// infoFile.getCorrectFileName() != null ?
		// infoFile.getCorrectFileName().toLowerCase() : "";
		//
		// return correctName != null &&
		// (correctName.toLowerCase().endsWith(".doc") ||
		// correctName.toLowerCase().endsWith(".docx") ||
		// correctName.toLowerCase().endsWith(".xls") ||
		// correctName.toLowerCase().endsWith(".xlsx") ||
		// correctName.toLowerCase().endsWith(".odt") ||
		// correctName.toLowerCase().endsWith(".pdf") ||
		// correctName.toLowerCase().endsWith(".doc.p7m") ||
		// correctName.toLowerCase().endsWith(".docx.p7m") ||
		// correctName.toLowerCase().endsWith(".xls.p7m") ||
		// correctName.toLowerCase().endsWith(".xlsx.p7m") ||
		// correctName.toLowerCase().endsWith(".odt.p7m") ||
		// correctName.toLowerCase().endsWith(".pdf.p7m"));

		return infoFile == null || infoFile.getMimetype() == null || (infoFile.getMimetype().equals("")
				|| infoFile.getMimetype().equals("application/pdf") || infoFile.getMimetype().startsWith("image"))
				|| infoFile.isConvertibile();

	}

}
