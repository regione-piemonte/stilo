/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class GestioneDominioBean {
	
	private Boolean attivaGestioneMultidominio;
	private List<String> multiDomini;
	private String dominio;
	
	public Boolean isAttivaGestioneMultidominio() {
		return attivaGestioneMultidominio;
	}
	public void setAttivaGestioneMultidominio(Boolean isAttivaGestioneMultidominio) {
		this.attivaGestioneMultidominio = isAttivaGestioneMultidominio;
	}
	public List<String> getMultiDomini() {
		return multiDomini;
	}
	public void setMultiDomini(List<String> multiDomini) {
		this.multiDomini = multiDomini;
	}
	public String getDominio() {
		return dominio;
	}
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

}
