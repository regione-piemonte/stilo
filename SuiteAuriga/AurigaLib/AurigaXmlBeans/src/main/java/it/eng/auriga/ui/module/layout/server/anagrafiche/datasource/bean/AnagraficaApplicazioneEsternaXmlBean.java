/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import java.util.List;

public class AnagraficaApplicazioneEsternaXmlBean {

	@NumeroColonna(numero = "1")
	private String codiceApplicazioneEsterna;	
	
	@NumeroColonna(numero = "2")
	private String codiceIstanzaApplicazioneEsterna;	
	
	@NumeroColonna(numero = "3")
	private String denominazione;
	
	@NumeroColonna(numero = "4")
	private Integer flgCredenzialiDiverse;
	
	@NumeroColonna(numero = "5")
	private Integer flgApplicazioneAnnullata;
	
	@NumeroColonna(numero = "6")
	@TipoData(tipo=Tipo.DATA)
	private Date tsPrimaConnessione;
	
	@NumeroColonna(numero = "7")
	@TipoData(tipo=Tipo.DATA)
	private Date tsUltimaConnessione;
	
	@NumeroColonna(numero = "8")
	@TipoData(tipo=Tipo.DATA)
	private Date tsIns;	
	
	@NumeroColonna(numero = "9")
	private String uteIns;
	
	@NumeroColonna(numero = "10")	
	@TipoData(tipo=Tipo.DATA)
	private Date tsLastUpd;
	
	@NumeroColonna(numero = "11")
	private String uteLastUpd;	
	
	@NumeroColonna(numero = "12")
	private Integer flgSocietaGruppo;
	
	@NumeroColonna(numero = "13")
	private String codiceFiscale;

	@NumeroColonna(numero = "14")
	private String partitaIva;	
	
	@NumeroColonna(numero = "15")
	private String emailPec;
	
	@NumeroColonna(numero = "16")
	private String email;
	
	@NumeroColonna(numero = "24")
	private String tipoTemplateFattura;
	
	//DEPOSITO FATTURE PASSIVE
	@NumeroColonna(numero = "25")
	private List<String> formatoDepositoFP;
	
	@NumeroColonna(numero = "26")
	private String modalitaDepositoFP;
	
	@NumeroColonna(numero = "18")
	private String protocolloFtpDepositoFP;
	
	@NumeroColonna(numero = "19")
	private String indirizzoFtpDepositoFP;
	
	@NumeroColonna(numero = "20")
	private String usernameFtpDepositoFP;
	
	@NumeroColonna(numero = "21")
	private String passwordFtpDepositoFP;
	
	@NumeroColonna(numero = "17")
	private String indirizzoMailDepositoFP;
	
	@NumeroColonna(numero = "22")
	private String portaFtpDepositoFP;
	
	@NumeroColonna(numero = "23")
	private String dirRemotaFtpDepositoFP;
	
	@NumeroColonna(numero = "27")
	private String proxyHostDepositoFP;
	
	@NumeroColonna(numero = "28")
	private String proxyPortDepositoFP;
	
	@NumeroColonna(numero = "29")
	private String proxyUserDepositoFP;
	
	@NumeroColonna(numero = "30")
	private String proxyPasswordDepositoFP;
	
	@NumeroColonna(numero = "81")
	private boolean flgPrefissoIdSdiFileFPItem;
	//DEPOSITO FATTURE PASSIVE
	
	
	//SCARICO FATTURE PASSIVE
	@NumeroColonna(numero="31")
	private String protocolloFtpScaricoFP;
	
	@NumeroColonna(numero = "32")
	private String indirizzoFtpScaricoFP;
	
	@NumeroColonna(numero = "33")
	private String usernameFtpScaricoFP;
	
	@NumeroColonna(numero = "34")
	private String passwordFtpScaricoFP;
	
	@NumeroColonna(numero = "35")
	private String portaFtpScaricoFP;
	
	@NumeroColonna(numero = "36")
	private String proxyHostScaricoFP;
	
	@NumeroColonna(numero = "37")
	private String proxyPortScaricoFP;
	
	@NumeroColonna(numero = "38")
	private String proxyUserScaricoFP;
	
	@NumeroColonna(numero = "39")
	private String proxyPasswordScaricoFP;
	
	@NumeroColonna(numero = "40")
	private String dirRemotaFtpScaricoFP;
	
	@NumeroColonna(numero = "41")
	private String modalitaScaricoFP;
	//SCARICO FATTURE PASSIVE
	
	//DEPOSITO FATTURE ATTIVE
	@NumeroColonna(numero = "42")
	private String modalitaDepositoFA;

	@NumeroColonna(numero = "43")
	private String protocolloFtpDepositoFA;

	@NumeroColonna(numero = "44")
	private String indirizzoFtpDepositoFA;

	@NumeroColonna(numero = "45")
	private String usernameFtpDepositoFA;

	@NumeroColonna(numero = "46")
	private String passwordFtpDepositoFA;

	@NumeroColonna(numero = "47")
	private String indirizzoMailDepositoFA;
	
	@NumeroColonna(numero = "48")
	private String formatoDepositoFA;
	
	@NumeroColonna(numero = "49")
	private String portaFtpDepositoFA;

	@NumeroColonna(numero = "50")
	private String proxyHostDepositoFA;

	@NumeroColonna(numero = "51")
	private String proxyPortDepositoFA;

	@NumeroColonna(numero = "52")
	private String proxyUserDepositoFA;

	@NumeroColonna(numero = "53")
	private String proxyPasswordDepositoFA;

	@NumeroColonna(numero = "54")
	private String dirRemotaFtpDepositoFA;	
	
	@NumeroColonna(numero = "80")
	private String prefissoFileEsitiFA;
	//DEPOSITO FATTURE ATTIVE
	
	//SCARICO FATTURE ATTIVE
	@NumeroColonna(numero="55")
	private String protocolloFtpScaricoFA;
	
	@NumeroColonna(numero = "56")
	private String indirizzoFtpScaricoFA;
	
	@NumeroColonna(numero = "57")
	private String usernameFtpScaricoFA;
	
	@NumeroColonna(numero = "58")
	private String passwordFtpScaricoFA;
	
	@NumeroColonna(numero = "59")
	private String portaFtpScaricoFA;
	
	@NumeroColonna(numero = "60")
	private String proxyHostScaricoFA;
	
	@NumeroColonna(numero = "61")
	private String proxyPortScaricoFA;
	
	@NumeroColonna(numero = "62")
	private String proxyUserScaricoFA;
	
	@NumeroColonna(numero = "63")
	private String proxyPasswordScaricoFA;
	
	@NumeroColonna(numero = "64")
	private String dirRemotaFtpScaricoFA;
	
	@NumeroColonna(numero = "65")
	private String modalitaScaricoFA;
	//SCARICO FATTURE ATTIVE
	
	//PARAMETRI FIRMA
	@NumeroColonna(numero = "66")
	private String dominioDeleganteFirmaHsm;
	
	@NumeroColonna(numero = "67")
	private boolean daFirmarePA;
	
	@NumeroColonna(numero = "68")
	private boolean daFirmarePR;
	
	@NumeroColonna(numero = "69")
	private boolean flgAttivazionePR;
	
	@NumeroColonna(numero = "70")
	private String pwdDeleganteFirmaHsm;
	
	@NumeroColonna(numero = "71")
	private String pwdFirmaHsm;
	
	@NumeroColonna(numero = "72")
	private String useridFirmaHsm;
	
	@NumeroColonna(numero = "73")
	private String useridDeleganteFirmaHsm;
	
	@NumeroColonna(numero = "74")
	private String fattStatoCaricamentoPA;
	
	@NumeroColonna(numero = "75")
	private String fattStatoCaricamentoPR;	
	//PARAMETRI FIRMA
	
	@NumeroColonna(numero = "76")
	private boolean depositoMetadatiFP;
	
	@NumeroColonna(numero = "77")
	private boolean gruppoUser;
	
	@NumeroColonna(numero = "78")
	private String destinatariEmailEsitoScaricoFA;
	
	@NumeroColonna(numero = "79")
	private boolean flgInvioEmailEsitoScaricoFA;
	
	//PARAMETRI DEPOSITO_ERRORI_CARICAMENTO
	@NumeroColonna(numero = "82")
	private String indirizzoFtpDepositoErroriCaricamento;
	
	@NumeroColonna(numero = "83")
	private String usernameFtpDepositoErroriCaricamento;
	
	@NumeroColonna(numero = "84")
	private String passwordFtpDepositoErroriCaricamento;
	
	@NumeroColonna(numero = "85")
	private String portaFtpDepositoErroriCaricamento;
	
	@NumeroColonna(numero = "86")
	private String proxyHostDepositoErroriCaricamento;
	
	@NumeroColonna(numero = "87")
	private String proxyPortDepositoErroriCaricamento;
	
	@NumeroColonna(numero = "88")
	private String proxyUserDepositoErroriCaricamento;
	
	@NumeroColonna(numero = "89")
	private String proxyPasswordDepositoErroriCaricamento;
	
	@NumeroColonna(numero = "90")
	private String dirRemotaFtpDepositoErroriCaricamento;
	
	@NumeroColonna(numero = "91")
	private String modalitaDepositoErroriCaricamento;
	
	@NumeroColonna(numero = "92")
	private String protocolloFtpDepositoErroriCaricamento;
	
	@NumeroColonna(numero = "93")
	private String destinatariEmailDepositoErroriCaricamento;
	//PARAMETRI DEPOSITO_ERRORI_CARICAMENTO
	
	
	public String getCodiceApplicazioneEsterna() {
		return codiceApplicazioneEsterna;
	}

	public void setCodiceApplicazioneEsterna(String codiceApplicazioneEsterna) {
		this.codiceApplicazioneEsterna = codiceApplicazioneEsterna;
	}

	public String getCodiceIstanzaApplicazioneEsterna() {
		return codiceIstanzaApplicazioneEsterna;
	}

	public void setCodiceIstanzaApplicazioneEsterna(
			String codiceIstanzaApplicazioneEsterna) {
		this.codiceIstanzaApplicazioneEsterna = codiceIstanzaApplicazioneEsterna;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Integer getFlgCredenzialiDiverse() {
		return flgCredenzialiDiverse;
	}

	public void setFlgCredenzialiDiverse(Integer flgCredenzialiDiverse) {
		this.flgCredenzialiDiverse = flgCredenzialiDiverse;
	}

	public Integer getFlgApplicazioneAnnullata() {
		return flgApplicazioneAnnullata;
	}

	public void setFlgApplicazioneAnnullata(Integer flgApplicazioneAnnullata) {
		this.flgApplicazioneAnnullata = flgApplicazioneAnnullata;
	}

	public Date getTsPrimaConnessione() {
		return tsPrimaConnessione;
	}

	public void setTsPrimaConnessione(Date tsPrimaConnessione) {
		this.tsPrimaConnessione = tsPrimaConnessione;
	}

	public Date getTsUltimaConnessione() {
		return tsUltimaConnessione;
	}

	public void setTsUltimaConnessione(Date tsUltimaConnessione) {
		this.tsUltimaConnessione = tsUltimaConnessione;
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public String getUteIns() {
		return uteIns;
	}

	public void setUteIns(String uteIns) {
		this.uteIns = uteIns;
	}

	public Date getTsLastUpd() {
		return tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}

	public String getUteLastUpd() {
		return uteLastUpd;
	}

	public void setUteLastUpd(String uteLastUpd) {
		this.uteLastUpd = uteLastUpd;
	}

	public Integer getFlgSocietaGruppo() {
		return flgSocietaGruppo;
	}

	public void setFlgSocietaGruppo(Integer flgSocietaGruppo) {
		this.flgSocietaGruppo = flgSocietaGruppo;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getEmailPec() {
		return emailPec;
	}

	public void setEmailPec(String emailPec) {
		this.emailPec = emailPec;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTipoTemplateFattura() {
		return tipoTemplateFattura;
	}

	public void setTipoTemplateFattura(String tipoTemplateFattura) {
		this.tipoTemplateFattura = tipoTemplateFattura;
	}

	public String getModalitaDepositoFP() {
		return modalitaDepositoFP;
	}

	public void setModalitaDepositoFP(String modalitaDepositoFP) {
		this.modalitaDepositoFP = modalitaDepositoFP;
	}

	public String getProtocolloFtpDepositoFP() {
		return protocolloFtpDepositoFP;
	}

	public void setProtocolloFtpDepositoFP(String protocolloFtpDepositoFP) {
		this.protocolloFtpDepositoFP = protocolloFtpDepositoFP;
	}

	public String getIndirizzoFtpDepositoFP() {
		return indirizzoFtpDepositoFP;
	}

	public void setIndirizzoFtpDepositoFP(String indirizzoFtpDepositoFP) {
		this.indirizzoFtpDepositoFP = indirizzoFtpDepositoFP;
	}

	public String getUsernameFtpDepositoFP() {
		return usernameFtpDepositoFP;
	}

	public void setUsernameFtpDepositoFP(String usernameFtpDepositoFP) {
		this.usernameFtpDepositoFP = usernameFtpDepositoFP;
	}

	public String getPasswordFtpDepositoFP() {
		return passwordFtpDepositoFP;
	}

	public void setPasswordFtpDepositoFP(String passwordFtpDepositoFP) {
		this.passwordFtpDepositoFP = passwordFtpDepositoFP;
	}

	public String getIndirizzoMailDepositoFP() {
		return indirizzoMailDepositoFP;
	}

	public void setIndirizzoMailDepositoFP(String indirizzoMailDepositoFP) {
		this.indirizzoMailDepositoFP = indirizzoMailDepositoFP;
	}

	public List<String> getFormatoDepositoFP() {
		return formatoDepositoFP;
	}

	public void setFormatoDepositoFP(List<String> formatoDepositoFP) {
		this.formatoDepositoFP = formatoDepositoFP;
	}

	public String getPortaFtpDepositoFP() {
		return portaFtpDepositoFP;
	}

	public void setPortaFtpDepositoFP(String portaFtpDepositoFP) {
		this.portaFtpDepositoFP = portaFtpDepositoFP;
	}

	public String getDirRemotaFtpDepositoFP() {
		return dirRemotaFtpDepositoFP;
	}

	public void setDirRemotaFtpDepositoFP(String dirRemotaFtpDepositoFP) {
		this.dirRemotaFtpDepositoFP = dirRemotaFtpDepositoFP;
	}

	public String getProxyHostDepositoFP() {
		return proxyHostDepositoFP;
	}

	public void setProxyHostDepositoFP(String proxyHostDepositoFP) {
		this.proxyHostDepositoFP = proxyHostDepositoFP;
	}

	public String getProxyPortDepositoFP() {
		return proxyPortDepositoFP;
	}

	public void setProxyPortDepositoFP(String proxyPortDepositoFP) {
		this.proxyPortDepositoFP = proxyPortDepositoFP;
	}

	public String getProxyUserDepositoFP() {
		return proxyUserDepositoFP;
	}

	public void setProxyUserDepositoFP(String proxyUserDepositoFP) {
		this.proxyUserDepositoFP = proxyUserDepositoFP;
	}

	public String getProxyPasswordDepositoFP() {
		return proxyPasswordDepositoFP;
	}

	public void setProxyPasswordDepositoFP(String proxyPasswordDepositoFP) {
		this.proxyPasswordDepositoFP = proxyPasswordDepositoFP;
	}

	public String getProtocolloFtpScaricoFP() {
		return protocolloFtpScaricoFP;
	}

	public void setProtocolloFtpScaricoFP(String protocolloFtpScaricoFP) {
		this.protocolloFtpScaricoFP = protocolloFtpScaricoFP;
	}

	public String getIndirizzoFtpScaricoFP() {
		return indirizzoFtpScaricoFP;
	}

	public void setIndirizzoFtpScaricoFP(String indirizzoFtpScaricoFP) {
		this.indirizzoFtpScaricoFP = indirizzoFtpScaricoFP;
	}

	public String getUsernameFtpScaricoFP() {
		return usernameFtpScaricoFP;
	}

	public void setUsernameFtpScaricoFP(String usernameFtpScaricoFP) {
		this.usernameFtpScaricoFP = usernameFtpScaricoFP;
	}

	public String getPasswordFtpScaricoFP() {
		return passwordFtpScaricoFP;
	}

	public void setPasswordFtpScaricoFP(String passwordFtpScaricoFP) {
		this.passwordFtpScaricoFP = passwordFtpScaricoFP;
	}

	public String getPortaFtpScaricoFP() {
		return portaFtpScaricoFP;
	}

	public void setPortaFtpScaricoFP(String portaFtpScaricoFP) {
		this.portaFtpScaricoFP = portaFtpScaricoFP;
	}

	public String getProxyHostScaricoFP() {
		return proxyHostScaricoFP;
	}

	public void setProxyHostScaricoFP(String proxyHostScaricoFP) {
		this.proxyHostScaricoFP = proxyHostScaricoFP;
	}

	public String getProxyPortScaricoFP() {
		return proxyPortScaricoFP;
	}

	public void setProxyPortScaricoFP(String proxyPortScaricoFP) {
		this.proxyPortScaricoFP = proxyPortScaricoFP;
	}

	public String getProxyUserScaricoFP() {
		return proxyUserScaricoFP;
	}

	public void setProxyUserScaricoFP(String proxyUserScaricoFP) {
		this.proxyUserScaricoFP = proxyUserScaricoFP;
	}

	public String getProxyPasswordScaricoFP() {
		return proxyPasswordScaricoFP;
	}

	public void setProxyPasswordScaricoFP(String proxyPasswordScaricoFP) {
		this.proxyPasswordScaricoFP = proxyPasswordScaricoFP;
	}

	public String getDirRemotaFtpScaricoFP() {
		return dirRemotaFtpScaricoFP;
	}

	public void setDirRemotaFtpScaricoFP(String dirRemotaFtpScaricoFP) {
		this.dirRemotaFtpScaricoFP = dirRemotaFtpScaricoFP;
	}

	public String getModalitaScaricoFP() {
		return modalitaScaricoFP;
	}

	public void setModalitaScaricoFP(String modalitaScaricoFP) {
		this.modalitaScaricoFP = modalitaScaricoFP;
	}

	public String getModalitaDepositoFA() {
		return modalitaDepositoFA;
	}

	public void setModalitaDepositoFA(String modalitaDepositoFA) {
		this.modalitaDepositoFA = modalitaDepositoFA;
	}

	public String getProtocolloFtpDepositoFA() {
		return protocolloFtpDepositoFA;
	}

	public void setProtocolloFtpDepositoFA(String protocolloFtpDepositoFA) {
		this.protocolloFtpDepositoFA = protocolloFtpDepositoFA;
	}

	public String getIndirizzoFtpDepositoFA() {
		return indirizzoFtpDepositoFA;
	}

	public void setIndirizzoFtpDepositoFA(String indirizzoFtpDepositoFA) {
		this.indirizzoFtpDepositoFA = indirizzoFtpDepositoFA;
	}

	public String getUsernameFtpDepositoFA() {
		return usernameFtpDepositoFA;
	}

	public void setUsernameFtpDepositoFA(String usernameFtpDepositoFA) {
		this.usernameFtpDepositoFA = usernameFtpDepositoFA;
	}

	public String getPasswordFtpDepositoFA() {
		return passwordFtpDepositoFA;
	}

	public void setPasswordFtpDepositoFA(String passwordFtpDepositoFA) {
		this.passwordFtpDepositoFA = passwordFtpDepositoFA;
	}

	public String getIndirizzoMailDepositoFA() {
		return indirizzoMailDepositoFA;
	}

	public void setIndirizzoMailDepositoFA(String indirizzoMailDepositoFA) {
		this.indirizzoMailDepositoFA = indirizzoMailDepositoFA;
	}

	public String getFormatoDepositoFA() {
		return formatoDepositoFA;
	}

	public void setFormatoDepositoFA(String formatoDepositoFA) {
		this.formatoDepositoFA = formatoDepositoFA;
	}

	public String getPortaFtpDepositoFA() {
		return portaFtpDepositoFA;
	}

	public void setPortaFtpDepositoFA(String portaFtpDepositoFA) {
		this.portaFtpDepositoFA = portaFtpDepositoFA;
	}

	public String getProxyHostDepositoFA() {
		return proxyHostDepositoFA;
	}

	public void setProxyHostDepositoFA(String proxyHostDepositoFA) {
		this.proxyHostDepositoFA = proxyHostDepositoFA;
	}

	public String getProxyPortDepositoFA() {
		return proxyPortDepositoFA;
	}

	public void setProxyPortDepositoFA(String proxyPortDepositoFA) {
		this.proxyPortDepositoFA = proxyPortDepositoFA;
	}

	public String getProxyUserDepositoFA() {
		return proxyUserDepositoFA;
	}

	public void setProxyUserDepositoFA(String proxyUserDepositoFA) {
		this.proxyUserDepositoFA = proxyUserDepositoFA;
	}

	public String getProxyPasswordDepositoFA() {
		return proxyPasswordDepositoFA;
	}

	public void setProxyPasswordDepositoFA(String proxyPasswordDepositoFA) {
		this.proxyPasswordDepositoFA = proxyPasswordDepositoFA;
	}

	public String getDirRemotaFtpDepositoFA() {
		return dirRemotaFtpDepositoFA;
	}

	public void setDirRemotaFtpDepositoFA(String dirRemotaFtpDepositoFA) {
		this.dirRemotaFtpDepositoFA = dirRemotaFtpDepositoFA;
	}

	public String getProtocolloFtpScaricoFA() {
		return protocolloFtpScaricoFA;
	}

	public void setProtocolloFtpScaricoFA(String protocolloFtpScaricoFA) {
		this.protocolloFtpScaricoFA = protocolloFtpScaricoFA;
	}

	public String getIndirizzoFtpScaricoFA() {
		return indirizzoFtpScaricoFA;
	}

	public void setIndirizzoFtpScaricoFA(String indirizzoFtpScaricoFA) {
		this.indirizzoFtpScaricoFA = indirizzoFtpScaricoFA;
	}

	public String getUsernameFtpScaricoFA() {
		return usernameFtpScaricoFA;
	}

	public void setUsernameFtpScaricoFA(String usernameFtpScaricoFA) {
		this.usernameFtpScaricoFA = usernameFtpScaricoFA;
	}

	public String getPasswordFtpScaricoFA() {
		return passwordFtpScaricoFA;
	}

	public void setPasswordFtpScaricoFA(String passwordFtpScaricoFA) {
		this.passwordFtpScaricoFA = passwordFtpScaricoFA;
	}

	public String getPortaFtpScaricoFA() {
		return portaFtpScaricoFA;
	}

	public void setPortaFtpScaricoFA(String portaFtpScaricoFA) {
		this.portaFtpScaricoFA = portaFtpScaricoFA;
	}

	public String getProxyHostScaricoFA() {
		return proxyHostScaricoFA;
	}

	public void setProxyHostScaricoFA(String proxyHostScaricoFA) {
		this.proxyHostScaricoFA = proxyHostScaricoFA;
	}

	public String getProxyPortScaricoFA() {
		return proxyPortScaricoFA;
	}

	public void setProxyPortScaricoFA(String proxyPortScaricoFA) {
		this.proxyPortScaricoFA = proxyPortScaricoFA;
	}

	public String getProxyUserScaricoFA() {
		return proxyUserScaricoFA;
	}

	public void setProxyUserScaricoFA(String proxyUserScaricoFA) {
		this.proxyUserScaricoFA = proxyUserScaricoFA;
	}

	public String getProxyPasswordScaricoFA() {
		return proxyPasswordScaricoFA;
	}

	public void setProxyPasswordScaricoFA(String proxyPasswordScaricoFA) {
		this.proxyPasswordScaricoFA = proxyPasswordScaricoFA;
	}

	public String getDirRemotaFtpScaricoFA() {
		return dirRemotaFtpScaricoFA;
	}

	public void setDirRemotaFtpScaricoFA(String dirRemotaFtpScaricoFA) {
		this.dirRemotaFtpScaricoFA = dirRemotaFtpScaricoFA;
	}

	public String getModalitaScaricoFA() {
		return modalitaScaricoFA;
	}

	public void setModalitaScaricoFA(String modalitaScaricoFA) {
		this.modalitaScaricoFA = modalitaScaricoFA;
	}

	public String getDominioDeleganteFirmaHsm() {
		return dominioDeleganteFirmaHsm;
	}

	public void setDominioDeleganteFirmaHsm(String dominioDeleganteFirmaHsm) {
		this.dominioDeleganteFirmaHsm = dominioDeleganteFirmaHsm;
	}

	public String getPwdDeleganteFirmaHsm() {
		return pwdDeleganteFirmaHsm;
	}

	public void setPwdDeleganteFirmaHsm(String pwdDeleganteFirmaHsm) {
		this.pwdDeleganteFirmaHsm = pwdDeleganteFirmaHsm;
	}

	public String getPwdFirmaHsm() {
		return pwdFirmaHsm;
	}

	public void setPwdFirmaHsm(String pwdFirmaHsm) {
		this.pwdFirmaHsm = pwdFirmaHsm;
	}

	public String getUseridFirmaHsm() {
		return useridFirmaHsm;
	}

	public void setUseridFirmaHsm(String useridFirmaHsm) {
		this.useridFirmaHsm = useridFirmaHsm;
	}

	public String getUseridDeleganteFirmaHsm() {
		return useridDeleganteFirmaHsm;
	}

	public void setUseridDeleganteFirmaHsm(String useridDeleganteFirmaHsm) {
		this.useridDeleganteFirmaHsm = useridDeleganteFirmaHsm;
	}

	public String getFattStatoCaricamentoPA() {
		return fattStatoCaricamentoPA;
	}

	public void setFattStatoCaricamentoPA(String fattStatoCaricamentoPA) {
		this.fattStatoCaricamentoPA = fattStatoCaricamentoPA;
	}

	public String getFattStatoCaricamentoPR() {
		return fattStatoCaricamentoPR;
	}

	public void setFattStatoCaricamentoPR(String fattStatoCaricamentoPR) {
		this.fattStatoCaricamentoPR = fattStatoCaricamentoPR;
	}

	public boolean isDaFirmarePA() {
		return daFirmarePA;
	}

	public void setDaFirmarePA(boolean daFirmarePA) {
		this.daFirmarePA = daFirmarePA;
	}

	public boolean isDaFirmarePR() {
		return daFirmarePR;
	}

	public void setDaFirmarePR(boolean daFirmarePR) {
		this.daFirmarePR = daFirmarePR;
	}

	public boolean isFlgAttivazionePR() {
		return flgAttivazionePR;
	}

	public void setFlgAttivazionePR(boolean flgAttivazionePR) {
		this.flgAttivazionePR = flgAttivazionePR;
	}

	public boolean isDepositoMetadatiFP() {
		return depositoMetadatiFP;
	}

	public void setDepositoMetadatiFP(boolean depositoMetadatiFP) {
		this.depositoMetadatiFP = depositoMetadatiFP;
	}

	public boolean isGruppoUser() {
		return gruppoUser;
	}

	public void setGruppoUser(boolean gruppoUser) {
		this.gruppoUser = gruppoUser;
	}

	public String getDestinatariEmailEsitoScaricoFA() {
		return destinatariEmailEsitoScaricoFA;
	}

	public void setDestinatariEmailEsitoScaricoFA(
			String destinatariEmailEsitoScaricoFA) {
		this.destinatariEmailEsitoScaricoFA = destinatariEmailEsitoScaricoFA;
	}

	public boolean isFlgInvioEmailEsitoScaricoFA() {
		return flgInvioEmailEsitoScaricoFA;
	}

	public void setFlgInvioEmailEsitoScaricoFA(boolean flgInvioEmailEsitoScaricoFA) {
		this.flgInvioEmailEsitoScaricoFA = flgInvioEmailEsitoScaricoFA;
	}

	public String getPrefissoFileEsitiFA() {
		return prefissoFileEsitiFA;
	}

	public void setPrefissoFileEsitiFA(String prefissoFileEsitiFA) {
		this.prefissoFileEsitiFA = prefissoFileEsitiFA;
	}

	public boolean isFlgPrefissoIdSdiFileFPItem() {
		return flgPrefissoIdSdiFileFPItem;
	}

	public void setFlgPrefissoIdSdiFileFPItem(boolean flgPrefissoIdSdiFileFPItem) {
		this.flgPrefissoIdSdiFileFPItem = flgPrefissoIdSdiFileFPItem;
	}

	public String getIndirizzoFtpDepositoErroriCaricamento() {
		return indirizzoFtpDepositoErroriCaricamento;
	}

	public void setIndirizzoFtpDepositoErroriCaricamento(
			String indirizzoFtpDepositoErroriCaricamento) {
		this.indirizzoFtpDepositoErroriCaricamento = indirizzoFtpDepositoErroriCaricamento;
	}

	public String getUsernameFtpDepositoErroriCaricamento() {
		return usernameFtpDepositoErroriCaricamento;
	}

	public void setUsernameFtpDepositoErroriCaricamento(
			String usernameFtpDepositoErroriCaricamento) {
		this.usernameFtpDepositoErroriCaricamento = usernameFtpDepositoErroriCaricamento;
	}

	public String getPasswordFtpDepositoErroriCaricamento() {
		return passwordFtpDepositoErroriCaricamento;
	}

	public void setPasswordFtpDepositoErroriCaricamento(
			String passwordFtpDepositoErroriCaricamento) {
		this.passwordFtpDepositoErroriCaricamento = passwordFtpDepositoErroriCaricamento;
	}

	public String getPortaFtpDepositoErroriCaricamento() {
		return portaFtpDepositoErroriCaricamento;
	}

	public void setPortaFtpDepositoErroriCaricamento(
			String portaFtpDepositoErroriCaricamento) {
		this.portaFtpDepositoErroriCaricamento = portaFtpDepositoErroriCaricamento;
	}

	public String getProxyHostDepositoErroriCaricamento() {
		return proxyHostDepositoErroriCaricamento;
	}

	public void setProxyHostDepositoErroriCaricamento(
			String proxyHostDepositoErroriCaricamento) {
		this.proxyHostDepositoErroriCaricamento = proxyHostDepositoErroriCaricamento;
	}

	public String getProxyPortDepositoErroriCaricamento() {
		return proxyPortDepositoErroriCaricamento;
	}

	public void setProxyPortDepositoErroriCaricamento(
			String proxyPortDepositoErroriCaricamento) {
		this.proxyPortDepositoErroriCaricamento = proxyPortDepositoErroriCaricamento;
	}

	public String getProxyUserDepositoErroriCaricamento() {
		return proxyUserDepositoErroriCaricamento;
	}

	public void setProxyUserDepositoErroriCaricamento(
			String proxyUserDepositoErroriCaricamento) {
		this.proxyUserDepositoErroriCaricamento = proxyUserDepositoErroriCaricamento;
	}

	public String getProxyPasswordDepositoErroriCaricamento() {
		return proxyPasswordDepositoErroriCaricamento;
	}

	public void setProxyPasswordDepositoErroriCaricamento(
			String proxyPasswordDepositoErroriCaricamento) {
		this.proxyPasswordDepositoErroriCaricamento = proxyPasswordDepositoErroriCaricamento;
	}

	public String getDirRemotaFtpDepositoErroriCaricamento() {
		return dirRemotaFtpDepositoErroriCaricamento;
	}

	public void setDirRemotaFtpDepositoErroriCaricamento(
			String dirRemotaFtpDepositoErroriCaricamento) {
		this.dirRemotaFtpDepositoErroriCaricamento = dirRemotaFtpDepositoErroriCaricamento;
	}

	public String getModalitaDepositoErroriCaricamento() {
		return modalitaDepositoErroriCaricamento;
	}

	public void setModalitaDepositoErroriCaricamento(
			String modalitaDepositoErroriCaricamento) {
		this.modalitaDepositoErroriCaricamento = modalitaDepositoErroriCaricamento;
	}

	public String getProtocolloFtpDepositoErroriCaricamento() {
		return protocolloFtpDepositoErroriCaricamento;
	}

	public void setProtocolloFtpDepositoErroriCaricamento(
			String protocolloFtpDepositoErroriCaricamento) {
		this.protocolloFtpDepositoErroriCaricamento = protocolloFtpDepositoErroriCaricamento;
	}

	public String getDestinatariEmailDepositoErroriCaricamento() {
		return destinatariEmailDepositoErroriCaricamento;
	}

	public void setDestinatariEmailDepositoErroriCaricamento(
			String destinatariEmailDepositoErroriCaricamento) {
		this.destinatariEmailDepositoErroriCaricamento = destinatariEmailDepositoErroriCaricamento;
	}
}
