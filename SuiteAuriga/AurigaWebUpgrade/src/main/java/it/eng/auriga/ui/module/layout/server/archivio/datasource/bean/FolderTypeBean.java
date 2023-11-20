/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import it.eng.document.NumeroColonna;


public class FolderTypeBean {

	@NumeroColonna(numero="1")
	private String idFolderType;
	@NumeroColonna(numero="2")
	private String descFolderType;
	@NumeroColonna(numero="3")
	private String templateNomeFolder;
	@NumeroColonna(numero="4")
	private String dictionaryEntrySezione;
	@NumeroColonna(numero = "7")
	private Boolean flgTipoFolderConVie;
	
	private LinkedHashMap<String, String> gruppiAttributiCustomTipoFolder;		
	
	public String getIdFolderType() {
		return idFolderType;
	}
	public void setIdFolderType(String idFolderType) {
		this.idFolderType = idFolderType;
	}
	public String getDescFolderType() {
		return descFolderType;
	}
	public void setDescFolderType(String descFolderType) {
		this.descFolderType = descFolderType;
	}
	public String getTemplateNomeFolder() {
		return templateNomeFolder;
	}
	public void setTemplateNomeFolder(String templateNomeFolder) {
		this.templateNomeFolder = templateNomeFolder;
	}
	public String getDictionaryEntrySezione() {
		return dictionaryEntrySezione;
	}
	public void setDictionaryEntrySezione(String dictionaryEntrySezione) {
		this.dictionaryEntrySezione = dictionaryEntrySezione;
	}
	public Boolean getFlgTipoFolderConVie() {
		return flgTipoFolderConVie;
	}
	public void setFlgTipoFolderConVie(Boolean flgTipoFolderConVie) {
		this.flgTipoFolderConVie = flgTipoFolderConVie;
	}
	public LinkedHashMap<String, String> getGruppiAttributiCustomTipoFolder() {
		return gruppiAttributiCustomTipoFolder;
	}
	public void setGruppiAttributiCustomTipoFolder(
			LinkedHashMap<String, String> gruppiAttributiCustomTipoFolder) {
		this.gruppiAttributiCustomTipoFolder = gruppiAttributiCustomTipoFolder;
	}
	
}
