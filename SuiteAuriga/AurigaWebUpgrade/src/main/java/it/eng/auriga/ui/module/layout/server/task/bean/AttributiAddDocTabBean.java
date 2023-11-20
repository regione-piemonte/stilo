/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class AttributiAddDocTabBean {

	@NumeroColonna(numero = "1")
	private String codice;

	@NumeroColonna(numero = "2")
	private String titolo;

	@NumeroColonna(numero = "3")
	private String flgDatiStorici;

	@NumeroColonna(numero = "4")
	private String messaggioTab;

	@NumeroColonna(numero = "5")
	private String messaggioTabDatiStorici;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getFlgDatiStorici() {
		return flgDatiStorici;
	}

	public void setFlgDatiStorici(String flgDatiStorici) {
		this.flgDatiStorici = flgDatiStorici;
	}

	public String getMessaggioTab() {
		return messaggioTab;
	}

	public void setMessaggioTab(String messaggioTab) {
		this.messaggioTab = messaggioTab;
	}

	public String getMessaggioTabDatiStorici() {
		return messaggioTabDatiStorici;
	}

	public void setMessaggioTabDatiStorici(String messaggioTabDatiStorici) {
		this.messaggioTabDatiStorici = messaggioTabDatiStorici;
	}

}