/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class EmailDetailToExtractBean
{
	String idEmail;
	String uriFileEml;
	String progressivo;
	String tipo;
	String sottoTipo;
	String messageId;
	String casellaAccount;
	String casellaIsPEC;
	String accountMittente;
	String subject;
	String body;
	String tsInvio;
	String tsRicezione;
	String listaDestinatariPrincipali;
	String destinatariCC;
	String desUOAssegnataria;
	String allegati;
	String completa;
	String progrDownloadStampa;
	String statoLavorazione;
	String [] files;
	String flgIO;
	String statoConsolidamento;
	String tsStatoLavorazioneDal;
	String tsAssegnazioneDal;
	String inCaricoA;
	String tsInCaricoDal;
	String codAzioneDaFare;
	String descrizioneAzioneDaFare;
	String estremiDocDerivati;
	
	String dataAggStatoLavorazione;
	String orarioAggStatoLavorazione;
	String dataUltimaAssegnazione;
	String orarioUltimaAssegnazione;
	String desUtenteLock;
	String dataLock;
	String orarioLock;
	String statoInvio;
	String statoAccettazione;
	String statoConsegna;
	
	String abilitaPresaInCarico;
	String abilitaMessaInCarico; 
	String abilitaRilascio;
	
	String abilitaInvia;
	String abilitaInvioCopia;
	

	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	public String getUriFileEml() {
		return uriFileEml;
	}
	public void setUriFileEml(String uriFileEml) {
		this.uriFileEml = uriFileEml;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSottoTipo() {
		return sottoTipo;
	}
	public void setSottoTipo(String sottoTipo) {
		this.sottoTipo = sottoTipo;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getCasellaAccount() {
		return casellaAccount;
	}
	public void setCasellaAccount(String casellaAccount) {
		this.casellaAccount = casellaAccount;
	}
	public String getCasellaIsPEC() {
		return casellaIsPEC;
	}
	public void setCasellaIsPEC(String casellaIsPEC) {
		this.casellaIsPEC = casellaIsPEC;
	}
	public String getAccountMittente() {
		return accountMittente;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTsInvio() {
		return tsInvio;
	}
	public void setTsInvio(String tsInvio) {
		this.tsInvio = tsInvio;
	}
	public String getTsRicezione() {
		return tsRicezione;
	}
	public void setTsRicezione(String tsRicezione) {
		this.tsRicezione = tsRicezione;
	}
	public String getListaDestinatariPrincipali() {
		return listaDestinatariPrincipali;
	}
	public void setListaDestinatariPrincipali(String listaDestinatariPrincipali) {
		this.listaDestinatariPrincipali = listaDestinatariPrincipali;
	}
	public String getDesUOAssegnataria() {
		return desUOAssegnataria;
	}
	public void setDesUOAssegnataria(String desUOAssegnataria) {
		this.desUOAssegnataria = desUOAssegnataria;
	}
	
	
	public String getAllegati() {
		return allegati;
	}
	public void setAllegati(String allegati) {
		this.allegati = allegati;
	}
	public void setAccountMittente(String accountMittente) {
		this.accountMittente = accountMittente;
	}
	public void setFiles(String[] files) {
		this.files = files;
	}
	public String [] getFiles()
	{
		
		String [] files = allegati.split(";");
		
		return files;
	}
	public String getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(String progressivo) {
		this.progressivo = progressivo;
	}
	public String getCompleta() {
		return completa;
	}
	public void setCompleta(String completa) {
		this.completa = completa;
	}
	public String getProgrDownloadStampa() {
		return progrDownloadStampa;
	}
	public void setProgrDownloadStampa(String progrDownloadStampa) {
		this.progrDownloadStampa = progrDownloadStampa;
	}
	public String getStatoLavorazione() {
		return statoLavorazione;
	}
	public void setStatoLavorazione(String statoLavorazione) {
		this.statoLavorazione = statoLavorazione;
	}
	public String getFlgIO() {
		return flgIO;
	}
	public void setFlgIO(String flgIO) {
		this.flgIO = flgIO;
	}
	public String getStatoConsolidamento() {
		return statoConsolidamento;
	}
	public void setStatoConsolidamento(String statoConsolidamento) {
		this.statoConsolidamento = statoConsolidamento;
	}
	public String getTsStatoLavorazioneDal() {
		return tsStatoLavorazioneDal;
	}
	public void setTsStatoLavorazioneDal(String tsStatoLavorazioneDal) {
		this.tsStatoLavorazioneDal = tsStatoLavorazioneDal;
	}
	public String getTsAssegnazioneDal() {
		return tsAssegnazioneDal;
	}
	public void setTsAssegnazioneDal(String tsAssegnazioneDal) {
		this.tsAssegnazioneDal = tsAssegnazioneDal;
	}
	public String getInCaricoA() {
		return inCaricoA;
	}
	public void setInCaricoA(String inCaricoA) {
		this.inCaricoA = inCaricoA;
	}
	public String getTsInCaricoDal() {
		return tsInCaricoDal;
	}
	public void setTsInCaricoDal(String tsInCaricoDal) {
		this.tsInCaricoDal = tsInCaricoDal;
	}
	public String getCodAzioneDaFare() {
		return codAzioneDaFare;
	}
	public void setCodAzioneDaFare(String codAzioneDaFare) {
		this.codAzioneDaFare = codAzioneDaFare;
	}
	public String getDescrizioneAzioneDaFare() {
		return descrizioneAzioneDaFare;
	}
	public void setDescrizioneAzioneDaFare(String descrizioneAzioneDaFare) {
		this.descrizioneAzioneDaFare = descrizioneAzioneDaFare;
	}
	public String getEstremiDocDerivati() {
		return estremiDocDerivati;
	}
	public void setEstremiDocDerivati(String estremiDocDerivati) {
		this.estremiDocDerivati = estremiDocDerivati;
	}
	public String getDataAggStatoLavorazione() {
		return dataAggStatoLavorazione;
	}
	public void setDataAggStatoLavorazione(String dataAggStatoLavorazione) {
		this.dataAggStatoLavorazione = dataAggStatoLavorazione;
	}
	public String getOrarioAggStatoLavorazione() {
		return orarioAggStatoLavorazione;
	}
	public void setOrarioAggStatoLavorazione(String orarioAggStatoLavorazione) {
		this.orarioAggStatoLavorazione = orarioAggStatoLavorazione;
	}
	public String getDataUltimaAssegnazione() {
		return dataUltimaAssegnazione;
	}
	public void setDataUltimaAssegnazione(String dataUltimaAssegnazione) {
		this.dataUltimaAssegnazione = dataUltimaAssegnazione;
	}
	public String getOrarioUltimaAssegnazione() {
		return orarioUltimaAssegnazione;
	}
	public void setOrarioUltimaAssegnazione(String orarioUltimaAssegnazione) {
		this.orarioUltimaAssegnazione = orarioUltimaAssegnazione;
	}
	public String getDesUtenteLock() {
		return desUtenteLock;
	}
	public void setDesUtenteLock(String desUtenteLock) {
		this.desUtenteLock = desUtenteLock;
	}
	public String getDataLock() {
		return dataLock;
	}
	public void setDataLock(String dataLock) {
		this.dataLock = dataLock;
	}
	public String getOrarioLock() {
		return orarioLock;
	}
	public void setOrarioLock(String orarioLock) {
		this.orarioLock = orarioLock;
	}
	public String getStatoInvio() {
		return statoInvio;
	}
	public void setStatoInvio(String statoInvio) {
		this.statoInvio = statoInvio;
	}
	public String getStatoAccettazione() {
		return statoAccettazione;
	}
	public void setStatoAccettazione(String statoAccettazione) {
		this.statoAccettazione = statoAccettazione;
	}
	public String getStatoConsegna() {
		return statoConsegna;
	}
	public void setStatoConsegna(String statoConsegna) {
		this.statoConsegna = statoConsegna;
	}
	public String getAbilitaPresaInCarico() {
		return abilitaPresaInCarico;
	}
	public void setAbilitaPresaInCarico(String abilitaPresaInCarico) {
		this.abilitaPresaInCarico = abilitaPresaInCarico;
	}
	public String getAbilitaMessaInCarico() {
		return abilitaMessaInCarico;
	}
	public void setAbilitaMessaInCarico(String abilitaMessaInCarico) {
		this.abilitaMessaInCarico = abilitaMessaInCarico;
	}
	public String getAbilitaRilascio() {
		return abilitaRilascio;
	}
	public void setAbilitaRilascio(String abilitaRilascio) {
		this.abilitaRilascio = abilitaRilascio;
	}
	public String getDestinatariCC() {
		return destinatariCC;
	}
	public void setDestinatariCC(String destinatariCC) {
		this.destinatariCC = destinatariCC;
	}
	public String getAbilitaInvia() {
		return abilitaInvia;
	}
	public void setAbilitaInvia(String abilitaInvia) {
		this.abilitaInvia = abilitaInvia;
	}
	public String getAbilitaInvioCopia() {
		return abilitaInvioCopia;
	}
	public void setAbilitaInvioCopia(String abilitaInvioCopia) {
		this.abilitaInvioCopia = abilitaInvioCopia;
	}	
}
