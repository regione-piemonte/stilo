/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.util.List;

public class AttributiUdBean {

	@XmlVariabile(nome = "#@ClassFascTitolario", tipo = TipoVariabile.LISTA)
	private List<AttributiUdClassFasc> classFascTitolario;
	
	@XmlVariabile(nome = "#Append_#@ClassFascTitolario", tipo = TipoVariabile.SEMPLICE)
	private String appendClassFascTitolario;
	
	@XmlVariabile(nome = "#@FolderCustom", tipo = TipoVariabile.LISTA)
	private List<AttributiUdFolderCustom> folderCustom;
	
	@XmlVariabile(nome = "#Append_#@FolderCustom", tipo = TipoVariabile.SEMPLICE)
	private String appendFolderCustom;
	
	@XmlVariabile(nome = "#LivRiservatezza", tipo = TipoVariabile.SEMPLICE)
	private String livRiservatezza;
	
	public List<AttributiUdClassFasc> getClassFascTitolario() {
		return classFascTitolario;
	}
	public void setClassFascTitolario(List<AttributiUdClassFasc> classFascTitolario) {
		this.classFascTitolario = classFascTitolario;
	}
	public String getAppendClassFascTitolario() {
		return appendClassFascTitolario;
	}
	public void setAppendClassFascTitolario(String appendClassFascTitolario) {
		this.appendClassFascTitolario = appendClassFascTitolario;
	}
	public List<AttributiUdFolderCustom> getFolderCustom() {
		return folderCustom;
	}
	public void setFolderCustom(List<AttributiUdFolderCustom> folderCustom) {
		this.folderCustom = folderCustom;
	}
	public String getAppendFolderCustom() {
		return appendFolderCustom;
	}
	public void setAppendFolderCustom(String appendFolderCustom) {
		this.appendFolderCustom = appendFolderCustom;
	}	
	public String getLivRiservatezza() {
		return livRiservatezza;
	}
	public void setLivRiservatezza(String livRiservatezza) {
		this.livRiservatezza = livRiservatezza;
	}
	
}
