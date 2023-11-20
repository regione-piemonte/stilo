/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Formati di indici ammessi, relativi mimetype e estensioni
 * 
 * @author Mattia Zanetti
 *
 */

public enum ArchiveTypeEnum {

	TAR("tar", FileExtensionCostants.TAR_EXTENSION, MimeTypeCostants.TAR_MIMETYPE), ZIP("zip", FileExtensionCostants.ZIP_EXTENSION,
			MimeTypeCostants.ZIP_MIMETYPE), SEVENZIP("7z", FileExtensionCostants.SEVENZIP_EXTENSION, MimeTypeCostants.SEVENZIP_MIMETYPE), RAR("rar",
					FileExtensionCostants.RAR_EXTENSION,
					MimeTypeCostants.RAR_MIMETYPE), GZIP("gzip", FileExtensionCostants.GZIP_EXTENSION, MimeTypeCostants.GZIP_MIMETYPE);

	private final String nome;
	private final String estensione;
	private final String mimeType;

	private ArchiveTypeEnum(String nome, String estensione, String mimeType) {
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

	public static ArchiveTypeEnum fromNome(String nome) {
		for (ArchiveTypeEnum value : values()) {
			if (value.getNome().equals(nome))
				return value;
		}
		return null;
	}

	public static ArchiveTypeEnum fromExstension(String estensione) {
		for (ArchiveTypeEnum value : values()) {
			if (value.getEstensione().equalsIgnoreCase(estensione))
				return value;
		}
		return null;
	}

	public static ArchiveTypeEnum fromMimeType(String mimeType) {
		for (ArchiveTypeEnum value : values()) {
			if (value.getMimeType().equalsIgnoreCase(mimeType))
				return value;
		}
		return null;
	}

}
