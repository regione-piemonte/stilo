/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class FirmatariInterniBean implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String nroOrdine;
	
	@NumeroColonna(numero = "2")
	private String idUtente;

	@NumeroColonna(numero = "3")
	private String desUtente;
	
	@NumeroColonna(numero = "4")
	private String tipoFirma;	
	
	@NumeroColonna(numero = "5")
	private String flgApposta;
	
	@NumeroColonna(numero = "6")
	private String ruolo;
	
	@NumeroColonna(numero = "7")
	@TipoData(tipo = Tipo.DATA)
	private Date dataFirma;

	public String getNroOrdine() {
		return nroOrdine;
	}

	public void setNroOrdine(String nroOrdine) {
		this.nroOrdine = nroOrdine;
	}

	public String getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	public String getDesUtente() {
		return desUtente;
	}

	public void setDesUtente(String desUtente) {
		this.desUtente = desUtente;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public String getFlgApposta() {
		return flgApposta;
	}

	public void setFlgApposta(String flgApposta) {
		this.flgApposta = flgApposta;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Date getDataFirma() {
		return dataFirma;
	}

	public void setDataFirma(Date dataFirma) {
		this.dataFirma = dataFirma;
	}
	
}
