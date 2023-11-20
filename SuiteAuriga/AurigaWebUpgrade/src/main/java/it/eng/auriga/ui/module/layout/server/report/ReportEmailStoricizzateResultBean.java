/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class ReportEmailStoricizzateResultBean implements Comparable<ReportEmailStoricizzateResultBean>{
	
	@NumeroColonna(numero = "3")
	private String codiceUO;
	
	@NumeroColonna(numero = "4")
	private String nomeUO;
	
	@NumeroColonna(numero = "10")
	private String archivio;
	
	@NumeroColonna(numero = "11")
	private String casella;
	
	@NumeroColonna(numero = "12")
	private String statoLavorazione;
	
	@NumeroColonna(numero = "16")
	private String periodo;

	@NumeroColonna(numero = "17")
	private String nrMail;
	
	@NumeroColonna(numero = "18")
	private String perc;
	
	@NumeroColonna(numero = "19")
	private String percArrotondata;

	public String getCodiceUO() {
		return codiceUO;
	}

	public void setCodiceUO(String codiceUO) {
		this.codiceUO = codiceUO;
	}

	public String getNomeUO() {
		return nomeUO;
	}

	public void setNomeUO(String nomeUO) {
		this.nomeUO = nomeUO;
	}

	public String getArchivio() {
		return archivio;
	}

	public void setArchivio(String archivio) {
		this.archivio = archivio;
	}

	public String getCasella() {
		return casella;
	}

	public void setCasella(String casella) {
		this.casella = casella;
	}

	public String getStatoLavorazione() {
		return statoLavorazione;
	}

	public void setStatoLavorazione(String statoLavorazione) {
		this.statoLavorazione = statoLavorazione;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getNrMail() {
		return nrMail;
	}

	public void setNrMail(String nrMail) {
		this.nrMail = nrMail;
	}

	public String getPerc() {
		return perc;
	}

	public void setPerc(String perc) {
		this.perc = perc;
	}

	public String getPercArrotondata() {
		return percArrotondata;
	}

	public void setPercArrotondata(String percArrotondata) {
		this.percArrotondata = percArrotondata;
	}

	@Override
	public int compareTo(ReportEmailStoricizzateResultBean o) {
		return 0;
	}
	
}