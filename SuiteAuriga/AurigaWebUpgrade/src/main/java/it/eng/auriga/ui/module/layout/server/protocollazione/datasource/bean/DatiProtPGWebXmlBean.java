/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.AltreUbicazioniBean;

public class DatiProtPGWebXmlBean {

	@XmlVariabile(nome="#DesOgg", tipo=TipoVariabile.SEMPLICE)
	private String oggetto;

	@XmlVariabile(nome = "#@AltreUbicazioni", tipo = TipoVariabile.LISTA)
	private List<AltreUbicazioniBean> altreUbicazioni;
	
	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public List<AltreUbicazioniBean> getAltreUbicazioni() {
		return altreUbicazioni;
	}

	public void setAltreUbicazioni(List<AltreUbicazioniBean> altreUbicazioni) {
		this.altreUbicazioni = altreUbicazioni;
	}

}
