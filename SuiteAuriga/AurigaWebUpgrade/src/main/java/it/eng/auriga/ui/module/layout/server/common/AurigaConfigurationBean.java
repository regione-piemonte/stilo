/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Map;
import java.util.Set;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UtenteBean;

public class AurigaConfigurationBean {
	
	private Map<String,String> parametriDB;		
	private String urlEditorOrganigramma;
	private Map<String,String> nomiModelliAtti;
	private Map<Integer,Set<String>> hiddenFieldsAtti;
	private List<UtenteBean> utentiAbilCPAList;
	
	public Map<String, String> getParametriDB() {
		return parametriDB;
	}
	public void setParametriDB(Map<String, String> parametriDB) {
		this.parametriDB = parametriDB;
	}
	public String getUrlEditorOrganigramma() {
		return urlEditorOrganigramma;
	}
	public void setUrlEditorOrganigramma(String urlEditorOrganigramma) {
		this.urlEditorOrganigramma = urlEditorOrganigramma;
	}
	public Map<String, String> getNomiModelliAtti() {
		return nomiModelliAtti;
	}
	public void setNomiModelliAtti(Map<String, String> nomiModelliAtti) {
		this.nomiModelliAtti = nomiModelliAtti;
	}
	public Map<Integer, Set<String>> getHiddenFieldsAtti() {
		return hiddenFieldsAtti;
	}
	public void setHiddenFieldsAtti(Map<Integer, Set<String>> hiddenFieldsAtti) {
		this.hiddenFieldsAtti = hiddenFieldsAtti;
	}
	public List<UtenteBean> getUtentiAbilCPAList() {
		return utentiAbilCPAList;
	}
	public void setUtentiAbilCPAList(List<UtenteBean> utentiAbilCPAList) {
		this.utentiAbilCPAList = utentiAbilCPAList;
	}

}
