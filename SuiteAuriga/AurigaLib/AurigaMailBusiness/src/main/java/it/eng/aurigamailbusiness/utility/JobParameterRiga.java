/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class JobParameterRiga {
	
	//-- 1: Nome parametro (SUBTYPE)
	@NumeroColonna(numero = "1")
	private String nomeParametro;
	
	//-- 2: Tipo parametro (TYPE)
	@NumeroColonna(numero = "2")
	private String tipoParametro;
	
	//-- 3: Verso IN/OUT
	@NumeroColonna(numero = "3")
	private String verso;
	
	//-- 4: Valore del parametro
	@NumeroColonna(numero = "4")
	private String valoreParametro;

	public String getNomeParametro() {
		return nomeParametro;
	}

	public String getTipoParametro() {
		return tipoParametro;
	}

	public String getVerso() {
		return verso;
	}

	public String getValoreParametro() {
		return valoreParametro;
	}

	public void setNomeParametro(String nomeParametro) {
		this.nomeParametro = nomeParametro;
	}

	public void setTipoParametro(String tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

	public void setVerso(String verso) {
		this.verso = verso;
	}

	public void setValoreParametro(String valoreParametro) {
		this.valoreParametro = valoreParametro;
	}	
	
}
