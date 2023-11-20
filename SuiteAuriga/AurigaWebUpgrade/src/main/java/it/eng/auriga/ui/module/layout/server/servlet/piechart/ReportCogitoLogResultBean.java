/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ReportCogitoLogResultBean implements Comparable<ReportCogitoLogResultBean>{

	
	@NumeroColonna(numero = "1")
	private String nomeUD;
	
	@NumeroColonna(numero = "2")
	private String codiceUO;
	
	@NumeroColonna(numero = "3")
	private String nomeUO;
	
	@NumeroColonna(numero = "4")
	private String usernameUtente;
		
	@NumeroColonna(numero = "5")
	private String denominazioneUtente;
	
	@NumeroColonna(numero = "6")
	private String nriLivelliClassificazione;
		
	@NumeroColonna(numero = "7")
	private String denominazioneClassificazione;
	
	@NumeroColonna(numero = "8")
	private String periodo;

	@NumeroColonna(numero = "9")
	private String nrDocumenti;
	
	@NumeroColonna(numero = "10")
	private String perc;
	
	@NumeroColonna(numero = "11")
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

	@Override
	public int compareTo(ReportCogitoLogResultBean o) {
		return 0;
	}

	public String getNriLivelliClassificazione() {
		return nriLivelliClassificazione;
	}

	public void setNriLivelliClassificazione(String nriLivelliClassificazione) {
		this.nriLivelliClassificazione = nriLivelliClassificazione;
	}

	public String getDenominazioneClassificazione() {
		return denominazioneClassificazione;
	}

	public void setDenominazioneClassificazione(String denominazioneClassificazione) {
		this.denominazioneClassificazione = denominazioneClassificazione;
	}

	public String getNomeUD() {
		return nomeUD;
	}

	public void setNomeUD(String nomeUD) {
		this.nomeUD = nomeUD;
	}
}
