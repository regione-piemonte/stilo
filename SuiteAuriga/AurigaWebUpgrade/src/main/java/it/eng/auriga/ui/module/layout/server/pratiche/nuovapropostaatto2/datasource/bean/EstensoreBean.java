/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

/**
 * 
 * @author DANCRIST
 *
 */

public class EstensoreBean {
	
	private String estensore;
	private String estensoreFromLoadDett;
	private String codUoEstensore;
	private String desEstensore;
	
	public String getEstensore() {
		return estensore;
	}
	public void setEstensore(String estensore) {
		this.estensore = estensore;
	}
	public String getEstensoreFromLoadDett() {
		return estensoreFromLoadDett;
	}
	public void setEstensoreFromLoadDett(String estensoreFromLoadDett) {
		this.estensoreFromLoadDett = estensoreFromLoadDett;
	}
	public String getCodUoEstensore() {
		return codUoEstensore;
	}
	public void setCodUoEstensore(String codUoEstensore) {
		this.codUoEstensore = codUoEstensore;
	}
	public String getDesEstensore() {
		return desEstensore;
	}
	public void setDesEstensore(String desEstensore) {
		this.desEstensore = desEstensore;
	}

}
