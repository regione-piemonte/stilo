/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BF5DocumentoIn extends CreaModDocumentoInBean {
	
	private static final long serialVersionUID = -4011655723485405230L;
	
	@XmlVariabile(nome="LOTTO_Ud", tipo = TipoVariabile.SEMPLICE)
	private String lotto;
	
	@XmlVariabile(nome="SCATOLA_Ud", tipo = TipoVariabile.SEMPLICE)
	private String scatola;

	@XmlVariabile(nome="UTENTESCANSIONE_Ud", tipo = TipoVariabile.SEMPLICE)
	private String utenteScansione;

	public String getLotto() {
		return lotto;
	}

	public void setLotto(String lotto) {
		this.lotto = lotto;
	}

	public String getScatola() {
		return scatola;
	}

	public void setScatola(String scatola) {
		this.scatola = scatola;
	}

	public String getUtenteScansione() {
		return utenteScansione;
	}

	public void setUtenteScansione(String utenteScansione) {
		this.utenteScansione = utenteScansione;
	}
	
}

