/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.NumeroColonna;

public class DatiSegnaturaRegoleProtAutoBean implements Serializable {

	private static final long serialVersionUID = -6841571236859541928L;

	
	@NumeroColonna(numero = "1")
	private String codAmministrazione;
	
	@NumeroColonna(numero = "2")
	private String codAoo;
	
	@NumeroColonna(numero = "3")
	private String siglaRegistro;

	public String getCodAmministrazione() {
		return codAmministrazione;
	}

	public void setCodAmministrazione(String codAmministrazione) {
		this.codAmministrazione = codAmministrazione;
	}

	public String getCodAoo() {
		return codAoo;
	}

	public void setCodAoo(String codAoo) {
		this.codAoo = codAoo;
	}

	public String getSiglaRegistro() {
		return siglaRegistro;
	}

	public void setSiglaRegistro(String siglaRegistro) {
		this.siglaRegistro = siglaRegistro;
	}
	
}
