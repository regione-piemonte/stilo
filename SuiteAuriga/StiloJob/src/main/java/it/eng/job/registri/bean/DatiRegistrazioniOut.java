/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DatiRegistrazioniOut {
	
	//	1: Data e ora della registrazione
	@NumeroColonna(numero = "1")
	private String dataOraReg;
	
	//	2: N.ro della registrazione
	@NumeroColonna(numero = "2")
	private String nroReg;
	
	//	3: Tipo della registrazione: E = Entrata, U = Uscita, I = Interna
	@NumeroColonna(numero = "3")
	private String tipoReg;
	
	//	4: Utente e UO di registrazione
	@NumeroColonna(numero = "4")
	private String utenteUoReg;
	
	//	5: Estremi dell'eventuale registrazione di emergenza (registro, numero e data)
	@NumeroColonna(numero = "5")
	private String estremiRegEmergenza;
	
	//	6: Mittente/i della registrazione
	@NumeroColonna(numero = "6")
	private String mittenti;
	
	//	7: Destinatario/i della registrazione
	@NumeroColonna(numero = "7")
	private String destinatari;
	
	//	8: Oggetto
	@NumeroColonna(numero = "8")
	private String oggetto;
	
	//	9: Classifica/e o fascicolo/i
	@NumeroColonna(numero = "9")
	private String classificheFascicoli;
	
	//	10: Impronte dei file associati: per ciascun file vi sono algoritmo e encoding di calcolo impronta e impronta separati da uno spazio; i dati dei vari file sono separati da ;
	@NumeroColonna(numero = "10")
	private String fileAssociati;
	
	//	11: Assegnatario/i della registrazione
	@NumeroColonna(numero = "11")
	private String assegnatari;
	
	//	12: (flag 1/0) Indicazione di registrazione annullata
	@NumeroColonna(numero = "12")
	private String flgAnnullata;
	
	//	13: Data e ora di annullamento della registrazione
	@NumeroColonna(numero = "13")
	private String dataOraAnn;
	
	//	14: Estremi atto di annullamento della registrazione
	@NumeroColonna(numero = "14")
	private String estremiAttoAnnReg;
	
	//	15: (flag 1/0) Indicazione di registrazione con dati annullati
	@NumeroColonna(numero = "15")
	private String flgRegConDatiAnn;
	
	//	16: Estremi del protocollo ricevuto (se registrazione in entrata)
	@NumeroColonna(numero = "16")
	private String estremiProtRicevuto;
	
	//	17: Nr. allegati
	@NumeroColonna(numero = "17")
	private String nrAllegati;

	//	18: Ufficio proponente  (se registro di proposta atto/repertorio atti)
	@NumeroColonna(numero = "18")
	private String ufficioProponente;

	//	19: Stato della proposta di atto: in iter, archiviata, approvata, pubblicata (se registro di proposta atto)
	@NumeroColonna(numero = "19")
	private String statoProposta;

	//	20: Estremi di repertorio corrispondenti al n.ro di proposta (se registro di proposta atto)
	@NumeroColonna(numero = "20")
	private String estremiRepertorio;


	//	21: Estremi di proposta corrispondenti al n.ro di repertorio (se registro di repertorio atti)
	@NumeroColonna(numero = "21")
	private String estremiProposta;

	//	22: Estremi di protocollo generale corrispondenti (se registro diverso dal Prot. Gen.)
	@NumeroColonna(numero = "22")
	private String estremiProtocolloGenerale;

    //	23: Periodo/i di pubblicazione (se registro di proposta atto/repertorio atti)
	@NumeroColonna(numero = "23")
	private String periodoPubblicazione;

	
    //	24: Data di adozione (la data dell'ultima firma) (se registro di proposta atto/repertorio atti)
	@NumeroColonna(numero = "24")
	private String dataAdozione;
	
	@NumeroColonna(numero = "25")
	private String attoRitirato;
	
	@NumeroColonna(numero = "26")
	private String adottanteProTempore;
    
	@NumeroColonna(numero = "27")
	private String strutturaProponente;
    
	  /*
     * Nella stampa dei registri, sia da interfaccia che AurigaJob, 
     * dobbiamo gestire una nuova colonna che restituirà la stored procedure: 
     */
	@NumeroColonna(numero = "28")
	private String dataEsecutivita;
	
	@NumeroColonna(numero = "29")
	private String specificita;
	
	public String getDataEsecutivita() {
		return dataEsecutivita;
	}

	public void setDataEsecutivita(String dataEsecutivita) {
		this.dataEsecutivita = dataEsecutivita;
	}
	public String getDataOraReg() {
		return dataOraReg;
	}

	public void setDataOraReg(String dataOraReg) {
		this.dataOraReg = dataOraReg;
	}

	public String getNroReg() {
		return nroReg;
	}

	public void setNroReg(String nroReg) {
		this.nroReg = nroReg;
	}

	public String getTipoReg() {
		return tipoReg;
	}

	public void setTipoReg(String tipoReg) {
		this.tipoReg = tipoReg;
	}

	public String getUtenteUoReg() {
		return utenteUoReg;
	}

	public void setUtenteUoReg(String utenteUoReg) {
		this.utenteUoReg = utenteUoReg;
	}

	public String getEstremiRegEmergenza() {
		return estremiRegEmergenza;
	}

	public void setEstremiRegEmergenza(String estremiRegEmergenza) {
		this.estremiRegEmergenza = estremiRegEmergenza;
	}

	public String getMittenti() {
		return mittenti;
	}

	public void setMittenti(String mittenti) {
		this.mittenti = mittenti;
	}

	public String getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getClassificheFascicoli() {
		return classificheFascicoli;
	}

	public void setClassificheFascicoli(String classificheFascicoli) {
		this.classificheFascicoli = classificheFascicoli;
	}

	public String getFileAssociati() {
		return fileAssociati;
	}

	public void setFileAssociati(String fileAssociati) {
		this.fileAssociati = fileAssociati;
	}

	public String getAssegnatari() {
		return assegnatari;
	}

	public void setAssegnatari(String assegnatari) {
		this.assegnatari = assegnatari;
	}

	public String getFlgAnnullata() {
		return flgAnnullata;
	}

	public void setFlgAnnullata(String flgAnnullata) {
		this.flgAnnullata = flgAnnullata;
	}

	public String getDataOraAnn() {
		return dataOraAnn;
	}

	public void setDataOraAnn(String dataOraAnn) {
		this.dataOraAnn = dataOraAnn;
	}

	public String getEstremiAttoAnnReg() {
		return estremiAttoAnnReg;
	}

	public void setEstremiAttoAnnReg(String estremiAttoAnnReg) {
		this.estremiAttoAnnReg = estremiAttoAnnReg;
	}

	public String getFlgRegConDatiAnn() {
		return flgRegConDatiAnn;
	}

	public void setFlgRegConDatiAnn(String flgRegConDatiAnn) {
		this.flgRegConDatiAnn = flgRegConDatiAnn;
	}

	public String getEstremiProtRicevuto() {
		return estremiProtRicevuto;
	}

	public void setEstremiProtRicevuto(String estremiProtRicevuto) {
		this.estremiProtRicevuto = estremiProtRicevuto;
	}

	public String getNrAllegati() {
		return nrAllegati;
	}

	public void setNrAllegati(String nrAllegati) {
		this.nrAllegati = nrAllegati;
	}

	public String getUfficioProponente() {
		return ufficioProponente;
	}

	public String getStatoProposta() {
		return statoProposta;
	}

	public String getEstremiRepertorio() {
		return estremiRepertorio;
	}

	public String getEstremiProposta() {
		return estremiProposta;
	}

	public String getEstremiProtocolloGenerale() {
		return estremiProtocolloGenerale;
	}

	public String getPeriodoPubblicazione() {
		return periodoPubblicazione;
	}

	public String getDataAdozione() {
		return dataAdozione;
	}

	public void setUfficioProponente(String ufficioProponente) {
		this.ufficioProponente = ufficioProponente;
	}

	public void setStatoProposta(String statoProposta) {
		this.statoProposta = statoProposta;
	}

	public void setEstremiRepertorio(String estremiRepertorio) {
		this.estremiRepertorio = estremiRepertorio;
	}

	public void setEstremiProposta(String estremiProposta) {
		this.estremiProposta = estremiProposta;
	}

	public void setEstremiProtocolloGenerale(String estremiProtocolloGenerale) {
		this.estremiProtocolloGenerale = estremiProtocolloGenerale;
	}

	public void setPeriodoPubblicazione(String periodoPubblicazione) {
		this.periodoPubblicazione = periodoPubblicazione;
	}

	public void setDataAdozione(String dataAdozione) {
		this.dataAdozione = dataAdozione;
	}

	public String getAttoRitirato() {
		return attoRitirato;
	}

	public void setAttoRitirato(String attoRitirato) {
		this.attoRitirato = attoRitirato;
	}

	public String getAdottanteProTempore() {
		return adottanteProTempore;
	}

	public void setAdottanteProTempore(String adottanteProTempore) {
		this.adottanteProTempore = adottanteProTempore;
	}

	public String getStrutturaProponente() {
		return strutturaProponente;
	}

	public void setStrutturaProponente(String strutturaProponente) {
		this.strutturaProponente = strutturaProponente;
	}

	public String getSpecificita() {
		return specificita;
	}

	public void setSpecificita(String specificita) {
		this.specificita = specificita;
	}
	
}
