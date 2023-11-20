/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.Flag;

public class XmlOpzioniStampaBean implements Serializable {
	
	@XmlVariabile(nome = "RicevutaXMittente", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRicevutaXMittente;
	
	@XmlVariabile(nome = "Primario", tipo = TipoVariabile.SEMPLICE)
	private Flag flgPrimario;
	
	@XmlVariabile(nome = "Allegati", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAllegati;

	@XmlVariabile(nome = "NotazioneCopiaOriginale", tipo = TipoVariabile.SEMPLICE)
	private String notazioneCopiaOriginale;

	@XmlVariabile(nome = "HideBarcode", tipo = TipoVariabile.SEMPLICE)
	private Flag flgHideBarcode;
	
	public Flag getFlgRicevutaXMittente() {
		return flgRicevutaXMittente;
	}

	public void setFlgRicevutaXMittente(Flag flgRicevutaXMittente) {
		this.flgRicevutaXMittente = flgRicevutaXMittente;
	}

	public Flag getFlgPrimario() {
		return flgPrimario;
	}

	public void setFlgPrimario(Flag flgPrimario) {
		this.flgPrimario = flgPrimario;
	}

	public Flag getFlgAllegati() {
		return flgAllegati;
	}

	public void setFlgAllegati(Flag flgAllegati) {
		this.flgAllegati = flgAllegati;
	}

	public String getNotazioneCopiaOriginale() {
		return notazioneCopiaOriginale;
	}

	public void setNotazioneCopiaOriginale(String notazioneCopiaOriginale) {
		this.notazioneCopiaOriginale = notazioneCopiaOriginale;
	}

	public Flag getFlgHideBarcode() {
		return flgHideBarcode;
	}

	public void setFlgHideBarcode(Flag flgHideBarcode) {
		this.flgHideBarcode = flgHideBarcode;
	}
}