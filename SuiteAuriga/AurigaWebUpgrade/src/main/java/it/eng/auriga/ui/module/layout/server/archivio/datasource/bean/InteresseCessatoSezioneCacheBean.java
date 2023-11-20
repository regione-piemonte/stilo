/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;

public class InteresseCessatoSezioneCacheBean {
	
	 @XmlVariabile(nome="#FlgInteresseCessato", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String flgInteresseCessato;

	public String getFlgInteresseCessato() {
		return flgInteresseCessato;
	}

	public void setFlgInteresseCessato(String flgInteresseCessato) {
		this.flgInteresseCessato = flgInteresseCessato;
	}
}
