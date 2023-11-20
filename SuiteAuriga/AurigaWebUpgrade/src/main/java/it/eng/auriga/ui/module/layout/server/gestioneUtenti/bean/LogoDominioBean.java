/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class LogoDominioBean {

	@NumeroColonna(numero="1")
	private String idLogo;
	
	@NumeroColonna(numero="2")
	private String nomeLogo;
	
	
	@NumeroColonna(numero="3")
	private String descrizioneLogo;


	public String getIdLogo() {
		return idLogo;
	}


	public String getNomeLogo() {
		return nomeLogo;
	}


	public String getDescrizioneLogo() {
		return descrizioneLogo;
	}


	public void setIdLogo(String idLogo) {
		this.idLogo = idLogo;
	}


	public void setNomeLogo(String nomeLogo) {
		this.nomeLogo = nomeLogo;
	}


	public void setDescrizioneLogo(String descrizioneLogo) {
		this.descrizioneLogo = descrizioneLogo;
	}
}
