/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class VisibEmailCaselleUoBean  {
	
	@NumeroColonna(numero="1")
	private String organigramma;

	@NumeroColonna(numero="2")
	private boolean flgIncludiSottoUO;

	@NumeroColonna(numero="3")
	private String  codRapido;
	
	@NumeroColonna(numero="4")
	private String  descrizione;
	
	

	public boolean isFlgIncludiSottoUO() {
		return flgIncludiSottoUO;
	}

	public void setFlgIncludiSottoUO(boolean flgIncludiSottoUO) {
		this.flgIncludiSottoUO = flgIncludiSottoUO;
	}

	public String getCodRapido() {
		return codRapido;
	}

	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getOrganigramma() {
		return organigramma;
	}

	public void setOrganigramma(String organigramma) {
		this.organigramma = organigramma;
	}

	

	

}