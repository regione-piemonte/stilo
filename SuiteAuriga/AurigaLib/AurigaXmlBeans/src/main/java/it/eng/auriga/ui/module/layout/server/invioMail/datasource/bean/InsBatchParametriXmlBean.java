/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class InsBatchParametriXmlBean {

	@NumeroColonna(numero = "1")
	private String nomeParametro;

	@NumeroColonna(numero = "2")
	private String tipoParametro;

	@NumeroColonna(numero = "3")
	private String verso;

	@NumeroColonna(numero = "4")
	private String valoreParametro;

	public String getNomeParametro() {
		return nomeParametro;
	}

	public void setNomeParametro(String nomeParametro) {
		this.nomeParametro = nomeParametro;
	}

	public String getTipoParametro() {
		return tipoParametro;
	}

	public void setTipoParametro(String tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

	public String getVerso() {
		return verso;
	}

	public void setVerso(String verso) {
		this.verso = verso;
	}

	public String getValoreParametro() {
		return valoreParametro;
	}

	public void setValoreParametro(String valoreParametro) {
		this.valoreParametro = valoreParametro;
	}

}