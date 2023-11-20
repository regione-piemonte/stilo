/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ReportDocAvanzatiResultBean implements Comparable<ReportDocAvanzatiResultBean>{

	@NumeroColonna(numero = "1")
	private String idEnteAoo;
	
	@NumeroColonna(numero = "2")
	private String nomeEnteAoo;
	
	@NumeroColonna(numero = "3")
	private String codiceUO;
	
	@NumeroColonna(numero = "4")
	private String nomeUO;
	
	@NumeroColonna(numero = "5")
	private String codiceApplicazione;
	
	@NumeroColonna(numero = "6")
	private String nomeApplicazione;
	
	@NumeroColonna(numero = "7")
	private String usernameUtente;
		
	@NumeroColonna(numero = "8")
	private String denominazioneUtente;
	
	@NumeroColonna(numero = "9")
	private String tipoRegistrazione;
	
	@NumeroColonna(numero = "10")
	private String categoriaRegistrazione;
	
	@NumeroColonna(numero = "11")
	private String siglaRegistro;
	
	@NumeroColonna(numero = "12")
	private String supporto;
	
	@NumeroColonna(numero = "13")
	private String presenzaFile;
	
	@NumeroColonna(numero = "14")
	private String mezzoTrasmissione;
	
	@NumeroColonna(numero = "15")
	private String livelloRiservatezza;
	
	@NumeroColonna(numero = "16")
	private String periodo;

	@NumeroColonna(numero = "17")
	private String nrDocumenti;
	
	@NumeroColonna(numero = "18")
	private String perc;
	
	@NumeroColonna(numero = "19")
	private String percArrotondata;
	
	@NumeroColonna(numero = "20")
	private String regValideAnnullate;

	public String getIdEnteAoo() {
		return idEnteAoo;
	}

	public void setIdEnteAoo(String idEnteAoo) {
		this.idEnteAoo = idEnteAoo;
	}

	public String getNomeEnteAoo() {
		return nomeEnteAoo;
	}

	public void setNomeEnteAoo(String nomeEnteAoo) {
		this.nomeEnteAoo = nomeEnteAoo;
	}

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

	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}

	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}

	public String getNomeApplicazione() {
		return nomeApplicazione;
	}

	public void setNomeApplicazione(String nomeApplicazione) {
		this.nomeApplicazione = nomeApplicazione;
	}

	public String getUsernameUtente() {
		return usernameUtente;
	}

	public void setUsernameUtente(String usernameUtente) {
		this.usernameUtente = usernameUtente;
	}

	public String getDenominazioneUtente() {
		return denominazioneUtente;
	}

	public void setDenominazioneUtente(String denominazioneUtente) {
		this.denominazioneUtente = denominazioneUtente;
	}

	public String getTipoRegistrazione() {
		return tipoRegistrazione;
	}

	public void setTipoRegistrazione(String tipoRegistrazione) {
		this.tipoRegistrazione = tipoRegistrazione;
	}

	public String getCategoriaRegistrazione() {
		return categoriaRegistrazione;
	}

	public void setCategoriaRegistrazione(String categoriaRegistrazione) {
		this.categoriaRegistrazione = categoriaRegistrazione;
	}

	public String getSiglaRegistro() {
		return siglaRegistro;
	}

	public void setSiglaRegistro(String siglaRegistro) {
		this.siglaRegistro = siglaRegistro;
	}

	public String getSupporto() {
		return supporto;
	}

	public void setSupporto(String supporto) {
		this.supporto = supporto;
	}

	public String getPresenzaFile() {
		return presenzaFile;
	}

	public void setPresenzaFile(String presenzaFile) {
		this.presenzaFile = presenzaFile;
	}

	public String getMezzoTrasmissione() {
		return mezzoTrasmissione;
	}

	public void setMezzoTrasmissione(String mezzoTrasmissione) {
		this.mezzoTrasmissione = mezzoTrasmissione;
	}

	public String getLivelloRiservatezza() {
		return livelloRiservatezza;
	}

	public void setLivelloRiservatezza(String livelloRiservatezza) {
		this.livelloRiservatezza = livelloRiservatezza;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getNrDocumenti() {
		return nrDocumenti;
	}

	public void setNrDocumenti(String nrDocumenti) {
		this.nrDocumenti = nrDocumenti;
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
	
	public String getRegValideAnnullate() {
		return regValideAnnullate;
	}

	public void setRegValideAnnullate(String regValideAnnullate) {
		this.regValideAnnullate = regValideAnnullate;
	}

	@Override
	public int compareTo(ReportDocAvanzatiResultBean o) {
		return 0;
	}
	
}
