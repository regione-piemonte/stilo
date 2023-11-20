/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class ParametroCasellaBean {
	
	@NumeroColonna(numero = "1")
	private String nome;
	
	@NumeroColonna(numero = "2")
	private String valore;
	
	@NumeroColonna(numero = "3")
	private String titolo;
	
	@NumeroColonna(numero = "4")
	private String tipo;
	
	@NumeroColonna(numero = "5")
	private String valueMap;
	
	@NumeroColonna(numero = "6")
	private String flgObblig;

	@NumeroColonna(numero = "7")
	private String flgShow;

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getValueMap() {
		return valueMap;
	}

	public void setValueMap(String valueMap) {
		this.valueMap = valueMap;
	}

	public String getFlgObblig() {
		return flgObblig;
	}

	public void setFlgObblig(String flgObblig) {
		this.flgObblig = flgObblig;
	}

	public String getFlgShow() {
		return flgShow;
	}

	public void setFlgShow(String flgShow) {
		this.flgShow = flgShow;
	}
	
}

