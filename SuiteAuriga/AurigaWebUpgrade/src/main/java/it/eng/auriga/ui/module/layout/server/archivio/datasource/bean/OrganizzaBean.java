/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FolderCustomBean;

import java.io.Serializable;
import java.util.List;

public class OrganizzaBean extends OperazioneMassivaArchivioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String livelloRiservatezza;
	private List<FolderCustomBean> listaFolderCustom;
		
	
	public String getLivelloRiservatezza() {
		return livelloRiservatezza;
	}
	public void setLivelloRiservatezza(String livelloRiservatezza) {
		this.livelloRiservatezza = livelloRiservatezza;
	}
	public List<FolderCustomBean> getListaFolderCustom() {
		return listaFolderCustom;
	}
	public void setListaFolderCustom(List<FolderCustomBean> listaFolderCustom) {
		this.listaFolderCustom = listaFolderCustom;
	}
		
}
