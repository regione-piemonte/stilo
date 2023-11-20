/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Formati di indici ammessi, relativi mimetype e estensioni
 * 
 * @author Mattia Zanetti
 *
 */

public enum IndexFileTypeEnum {

	CSV("csv", FileExtensionCostants.CSV_EXTENSION, MimeTypeCostants.CSV_MIMETYPE), XLS("xls", FileExtensionCostants.XLS_EXTENSION,
			MimeTypeCostants.XLS_MIMETYPE), XLSX("xlsx", FileExtensionCostants.XLSX_EXTENSION, MimeTypeCostants.XLSX_MIMETYPE);

	private final String nome;
	private final String estensione;
	private final String mimeType;

	private IndexFileTypeEnum(String nome, String estensione, String mimeType) {
		this.nome = nome;
		this.estensione = estensione;
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}

	public String getNome() {
		return nome;
	}

	public String getEstensione() {
		return estensione;
	}

	public static final IndexFileTypeEnum fromNome(String nome) {
		for (IndexFileTypeEnum value : values()) {
			if (value.getNome().equals(nome))
				return value;
		}
		return null;
	}

	public static final IndexFileTypeEnum fromEstensione(String estensione) {
		for (IndexFileTypeEnum value : values()) {
			if (value.getEstensione().equals(estensione))
				return value;
		}
		return null;
	}

	public static final IndexFileTypeEnum fromMimeType(String mimeType) {
		for (IndexFileTypeEnum value : values()) {
			if (value.getMimeType().equals(mimeType))
				return value;
		}
		return null;
	}

}
