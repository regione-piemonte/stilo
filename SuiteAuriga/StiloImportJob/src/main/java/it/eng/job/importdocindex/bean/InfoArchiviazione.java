/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Classe con le informazioni relative all'archiviazione (URI, impronta)
 * 
 * @author Mattia Zanetti
 *
 */

public class InfoArchiviazione {

	/**
	 * Uri del file salvato con storageUtil
	 */

	private String uri;

	/**
	 * Percorso del file relativo alla directory radice
	 */

	private String percorsoRelativoRispettoARadice;

	/**
	 * Impronta del file
	 */

	private String impronta;

	/**
	 * Algorirmo impronta del file
	 */

	private String algoritmo;

	/**
	 * Codifica impronta del file
	 */

	private String encoding;

	/**
	 * Dimensione in byte del file
	 */

	private Long dimensione;

	/**
	 * Mime type del file
	 */

	private String mimeType;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPercorsoRelativoRispettoARadice() {
		return percorsoRelativoRispettoARadice;
	}

	public void setPercorsoRelativoRispettoARadice(String percorsoRelativoRispettoARadice) {
		this.percorsoRelativoRispettoARadice = percorsoRelativoRispettoARadice;
	}

	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Long getDimensione() {
		return dimensione;
	}

	public void setDimensione(Long dimensione) {
		this.dimensione = dimensione;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}
