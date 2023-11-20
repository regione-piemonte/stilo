/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class LinguaBean {
	

	@NumeroColonna(numero="1")
	private String idLingua;
	
	@NumeroColonna(numero="2")
	private String descrizioneLingua;
	
	

	public String getIdLingua() {
		return idLingua;
	}


	public void setIdLingua(String idLingua) {
		this.idLingua = idLingua;
	}


	public String getDescrizioneLingua() {
		return descrizioneLingua;
	}


	public void setDescrizioneLingua(String descrizioneLingua) {
		this.descrizioneLingua = descrizioneLingua;
	}




}


