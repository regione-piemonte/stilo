/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public class GetListaDefPrefsBean {
	
	private String userId;
	private String prefKeyPrefix;
	private String idNode;
	private String finalita;
	
	private String autosearch;
	private String layoutLista;
	private String flgLayoutListaDefault;
	private String ricercaPreferita;
	private String flgRicercaPreferitaDefault;
	private String layoutFiltro;
	private String flgLayoutFiltroDefault;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPrefKeyPrefix() {
		return prefKeyPrefix;
	}
	public void setPrefKeyPrefix(String prefKeyPrefix) {
		this.prefKeyPrefix = prefKeyPrefix;
	}
	public String getIdNode() {
		return idNode;
	}
	public void setIdNode(String idNode) {
		this.idNode = idNode;
	}
	public String getFinalita() {
		return finalita;
	}
	public void setFinalita(String finalita) {
		this.finalita = finalita;
	}	
	public String getAutosearch() {
		return autosearch;
	}
	public void setAutosearch(String autosearch) {
		this.autosearch = autosearch;
	}
	public String getLayoutLista() {
		return layoutLista;
	}
	public void setLayoutLista(String layoutLista) {
		this.layoutLista = layoutLista;
	}
	public String getFlgLayoutListaDefault() {
		return flgLayoutListaDefault;
	}
	public void setFlgLayoutListaDefault(String flgLayoutListaDefault) {
		this.flgLayoutListaDefault = flgLayoutListaDefault;
	}
	public String getRicercaPreferita() {
		return ricercaPreferita;
	}
	public void setRicercaPreferita(String ricercaPreferita) {
		this.ricercaPreferita = ricercaPreferita;
	}
	public String getFlgRicercaPreferitaDefault() {
		return flgRicercaPreferitaDefault;
	}
	public void setFlgRicercaPreferitaDefault(String flgRicercaPreferitaDefault) {
		this.flgRicercaPreferitaDefault = flgRicercaPreferitaDefault;
	}
	public String getLayoutFiltro() {
		return layoutFiltro;
	}
	public void setLayoutFiltro(String layoutFiltro) {
		this.layoutFiltro = layoutFiltro;
	}
	public String getFlgLayoutFiltroDefault() {
		return flgLayoutFiltroDefault;
	}
	public void setFlgLayoutFiltroDefault(String flgLayoutFiltroDefault) {
		this.flgLayoutFiltroDefault = flgLayoutFiltroDefault;
	}	
		
}
