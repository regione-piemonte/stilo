/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.lang3.StringUtils;


public enum FileTypeFilterEnum {
	txt("txt"),
	TXT("TXT"),
	CSV("csv"),
	csv("CSV"),
	XML("xml"),
	P7M("p7m"),
	XLS("xls"),
	XLSX("xlsx"),
	PDF("PDF"),
	pdf("pdf"),
	TAR("tar"),
	GZIP("tar.gz"),
	GZIP_CRIPTATO("tar.gz.gpg"),
	RAR("rar"),
	SEVEN_ZIP("7z"),
	zip("zip"),
	ZIP("ZIP"),
	BOL("bol"),
	LD("LD"),
	LDD("LDD"),
	ALL(""),
	BIM("BIM"),
	CUSTOM_CREDEMTEL("xml_meta")
	;
	
	private String estensione;
	private String filtro;
	
	private FileTypeFilterEnum(String estensione) {
		this.estensione = estensione;
		if (StringUtils.isNotBlank(estensione)) {
			this.filtro = "*." + estensione;
		} else {
			this.filtro = "*.*";
		} 
	}
	
	public String getEstensione() {
		return this.estensione;
	}

	public String getFiltro() {
		return this.filtro;
	}

	public static FileTypeFilterEnum fromValue(String estensione) {
		for (FileTypeFilterEnum tipoFiltro : FileTypeFilterEnum.values()) {
			if (tipoFiltro.estensione.equalsIgnoreCase(estensione)) {
				return tipoFiltro;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return String.format("%s [%s:%s]", this.name(), this.estensione, this.filtro);
	}
}
