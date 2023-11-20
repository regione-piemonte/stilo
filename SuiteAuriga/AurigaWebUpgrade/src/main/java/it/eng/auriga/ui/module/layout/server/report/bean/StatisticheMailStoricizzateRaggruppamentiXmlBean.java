/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author DANCRIST
 *
 */

public class StatisticheMailStoricizzateRaggruppamentiXmlBean {
	
	@XmlVariabile(nome="StatoLavorazione", tipo=TipoVariabile.SEMPLICE)
	private String statoLavorazione;
	
	@XmlVariabile(nome="InArchivio", tipo=TipoVariabile.SEMPLICE)
	private String inArchivio;
	
	@XmlVariabile(nome="Casella", tipo=TipoVariabile.SEMPLICE)
	private String casella;
	
	@XmlVariabile(nome="TipoPeriodo", tipo=TipoVariabile.SEMPLICE)
	private String tipoPeriodo;
	
	@XmlVariabile(nome="Periodo", tipo=TipoVariabile.SEMPLICE)
	private String periodo;
	
	@XmlVariabile(nome="UO", tipo=TipoVariabile.SEMPLICE)
	private String uo;

	public String getStatoLavorazione() {
		return statoLavorazione;
	}

	public void setStatoLavorazione(String statoLavorazione) {
		this.statoLavorazione = statoLavorazione;
	}

	public String getInArchivio() {
		return inArchivio;
	}

	public void setInArchivio(String inArchivio) {
		this.inArchivio = inArchivio;
	}

	public String getCasella() {
		return casella;
	}

	public void setCasella(String casella) {
		this.casella = casella;
	}

	public String getTipoPeriodo() {
		return tipoPeriodo;
	}

	public void setTipoPeriodo(String tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getUo() {
		return uo;
	}

	public void setUo(String uo) {
		this.uo = uo;
	}

}