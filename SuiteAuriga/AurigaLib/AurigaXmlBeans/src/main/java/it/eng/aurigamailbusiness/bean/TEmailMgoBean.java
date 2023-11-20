/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class TEmailMgoBean extends AbstractBean implements Serializable {

	@Override
	public String toString() {
		return "TEmailMgoBean [accountMittente=" + accountMittente + ", categoria=" + categoria + ", idCasella=" + idCasella + ", idEmail=" + idEmail
				+ ", messageId=" + messageId + ", oggetto=" + oggetto + ", uriEmail=" + uriEmail + ", flgIo=" + flgIo + ", tsIns=" + tsIns + "]";
	}

	private static final long serialVersionUID = -7341833444762147789L;

	private static final String PEC = "C";

	private static final String PEO = "O";

	private String accountMittente;

	private String categoria;

	private String corpo;

	private long dimensione;

	private boolean flgEmailFirmata;

	private boolean flgInoltrata;

	private boolean flgInviataRisposta;

	private String flgIo;

	private boolean flgNoAssocAuto;

	private boolean flgNotifInteropAgg;

	private boolean flgNotifInteropAnn;

	private boolean flgNotifInteropConf;

	private boolean flgNotifInteropEcc;

	private String flgRicevutaCbs;

	private boolean flgRichConferma;

	private boolean flgSpam;

	private String flgStatoProt;

	private String flgStatoSpam;

	private String idCasella;

	private String idEmail;

	private String idRecDizContrassegno;

	private String idRecDizOperLock;

	private String idUoAssegn;

	private String idUteIns;

	private String idUtenteAssegn;

	private String idUtenteAggStatoLav;

	private String idUtenteLock;

	private String idUteUltimoAgg;

	private Long livPriorita;

	private String messageId;

	private short nroAllegati;

	private short nroAllegatiFirmati;

	private String oggetto;

	private String statoConsolidamento;

	private Date tsAssegn;

	private Date tsIns;

	private Date tsInvio;

	private Date tsInvioClient;

	private Date tsLettura;

	private Date tsLock;

	private Date tsRicezione;

	private Date tsUltimoAgg;

	private String uriEmail;

	private String avvertimenti;

	private String descrizioneUtenteAssegn;

	private String descrizioneUoAssegn;

	private String accountMittenteCtx;

	private String oggettoCtx;

	private String corpoCtx;

	private String siglaRegistroMitt;

	private Integer numRegMitt;

	private Short annoRegMitt;

	private Date tsRegMitt;

	private String oggettoRegMitt;

	private String oggettoRegMittCtx;

	private String statoLavorazione;

	private boolean flgRichConfLettura;

	private String messageIdTrasporto;

	private String codAzioneDaFare;

	private String dettAzioneDaFare;

	private BigDecimal progrMsg;

	private BigDecimal annoProgrMsg;

	private String registroProgrMsg;

	private String statoInvio;

	private String statoAccettazione;

	private String statoConsegna;

	private Date tsAggStatoLavorazione;

	private String motivoEccezione;

	private BigDecimal progrBozza;

	private Short annoProgrBozza;

	private String messageIdRif;

	private String errMessageRicPec;

	private boolean flgUriRicevuta;

	private String nroRichiestaMassivaInvio;
		
	/*
	AURIGA-581
	Gestire tag reply-to nell'operazione di risposta delle mail 
	inviate da mailui + salvarlo in campo REPLY_TO della T_EMAIL_MGO
	*/
	private String replyTo;

	/*
	AURIGA-600
	Salvataggio corpo mail come file in mailui
	*/
	private String uriCorpo;
	private String errArchUriCorpo;
	private String scCorpoMail;

	public String getAccountMittente() {
		return accountMittente;
	}

	public void setAccountMittente(String accountMittente) {
		this.accountMittente = accountMittente;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public long getDimensione() {
		return dimensione;
	}

	public void setDimensione(long dimensione) {
		this.dimensione = dimensione;
	}

	public boolean getFlgEmailFirmata() {
		return flgEmailFirmata;
	}

	public void setFlgEmailFirmata(boolean flgEmailFirmata) {
		this.flgEmailFirmata = flgEmailFirmata;
	}

	public boolean getFlgInoltrata() {
		return flgInoltrata;
	}

	public void setFlgInoltrata(boolean flgInoltrata) {
		this.flgInoltrata = flgInoltrata;
	}

	public boolean getFlgInviataRisposta() {
		return flgInviataRisposta;
	}

	public void setFlgInviataRisposta(boolean flgInviataRisposta) {
		this.flgInviataRisposta = flgInviataRisposta;
	}

	public String getFlgIo() {
		return flgIo;
	}

	public void setFlgIo(String flgIo) {
		this.flgIo = flgIo;
	}

	public boolean getFlgNoAssocAuto() {
		return flgNoAssocAuto;
	}

	public void setFlgNoAssocAuto(boolean flgNoAssocAuto) {
		this.flgNoAssocAuto = flgNoAssocAuto;
	}

	public boolean getFlgNotifInteropAgg() {
		return flgNotifInteropAgg;
	}

	public void setFlgNotifInteropAgg(boolean flgNotifInteropAgg) {
		this.flgNotifInteropAgg = flgNotifInteropAgg;
	}

	public boolean getFlgNotifInteropAnn() {
		return flgNotifInteropAnn;
	}

	public void setFlgNotifInteropAnn(boolean flgNotifInteropAnn) {
		this.flgNotifInteropAnn = flgNotifInteropAnn;
	}

	public boolean getFlgNotifInteropConf() {
		return flgNotifInteropConf;
	}

	public void setFlgNotifInteropConf(boolean flgNotifInteropConf) {
		this.flgNotifInteropConf = flgNotifInteropConf;
	}

	public boolean getFlgNotifInteropEcc() {
		return flgNotifInteropEcc;
	}

	public void setFlgNotifInteropEcc(boolean flgNotifInteropEcc) {
		this.flgNotifInteropEcc = flgNotifInteropEcc;
	}

	public String getFlgRicevutaCbs() {
		return flgRicevutaCbs;
	}

	public void setFlgRicevutaCbs(String flgRicevutaCbs) {
		this.flgRicevutaCbs = flgRicevutaCbs;
	}

	public boolean getFlgRichConferma() {
		return flgRichConferma;
	}

	public void setFlgRichConferma(boolean flgRichConferma) {
		this.flgRichConferma = flgRichConferma;
	}

	public boolean getFlgSpam() {
		return flgSpam;
	}

	public void setFlgSpam(boolean flgSpam) {
		this.flgSpam = flgSpam;
	}

	public String getFlgStatoProt() {
		return flgStatoProt;
	}

	public void setFlgStatoProt(String flgStatoProt) {
		this.flgStatoProt = flgStatoProt;
	}

	public String getFlgStatoSpam() {
		return flgStatoSpam;
	}

	public void setFlgStatoSpam(String flgStatoSpam) {
		this.flgStatoSpam = flgStatoSpam;
	}

	public String getIdCasella() {
		return idCasella;
	}

	public void setIdCasella(String idCasella) {
		this.idCasella = idCasella;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getIdUteIns() {
		return idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}

	public String getIdUteUltimoAgg() {
		return idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	public Long getLivPriorita() {
		return livPriorita;
	}

	public void setLivPriorita(Long livPriorita) {
		this.livPriorita = livPriorita;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public short getNroAllegati() {
		return nroAllegati;
	}

	public void setNroAllegati(short nroAllegati) {
		this.nroAllegati = nroAllegati;
	}

	public short getNroAllegatiFirmati() {
		return nroAllegatiFirmati;
	}

	public void setNroAllegatiFirmati(short nroAllegatiFirmati) {
		this.nroAllegatiFirmati = nroAllegatiFirmati;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getStatoConsolidamento() {
		return statoConsolidamento;
	}

	public void setStatoConsolidamento(String statoConsolidamento) {
		this.statoConsolidamento = statoConsolidamento;
	}

	public Date getTsAssegn() {
		return tsAssegn;
	}

	public void setTsAssegn(Date tsAssegn) {
		this.tsAssegn = tsAssegn;
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public Date getTsInvio() {
		return tsInvio;
	}

	public void setTsInvio(Date tsInvio) {
		this.tsInvio = tsInvio;
	}

	public Date getTsInvioClient() {
		return tsInvioClient;
	}

	public void setTsInvioClient(Date tsInvioClient) {
		this.tsInvioClient = tsInvioClient;
	}

	public Date getTsLettura() {
		return tsLettura;
	}

	public void setTsLettura(Date tsLettura) {
		this.tsLettura = tsLettura;
	}

	public Date getTsLock() {
		return tsLock;
	}

	public void setTsLock(Date tsLock) {
		this.tsLock = tsLock;
	}

	public Date getTsRicezione() {
		return tsRicezione;
	}

	public void setTsRicezione(Date tsRicezione) {
		this.tsRicezione = tsRicezione;
	}

	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public String getUriEmail() {
		return uriEmail;
	}

	public void setUriEmail(String uriEmail) {
		this.uriEmail = uriEmail;
	}

	public String getIdRecDizContrassegno() {
		return idRecDizContrassegno;
	}

	public void setIdRecDizContrassegno(String idRecDizContrassegno) {
		this.idRecDizContrassegno = idRecDizContrassegno;
	}

	public String getIdRecDizOperLock() {
		return idRecDizOperLock;
	}

	public void setIdRecDizOperLock(String idRecDizOperLock) {
		this.idRecDizOperLock = idRecDizOperLock;
	}

	public String getIdUoAssegn() {
		return idUoAssegn;
	}

	public void setIdUoAssegn(String idUoAssegn) {
		this.idUoAssegn = idUoAssegn;
	}

	public String getIdUtenteAssegn() {
		return idUtenteAssegn;
	}

	public void setIdUtenteAssegn(String idUtenteAssegn) {
		this.idUtenteAssegn = idUtenteAssegn;
	}

	public String getIdUtenteLock() {
		return idUtenteLock;
	}

	public void setIdUtenteLock(String idUtenteLock) {
		this.idUtenteLock = idUtenteLock;
	}

	public String getAvvertimenti() {
		return avvertimenti;
	}

	public void setAvvertimenti(String avvertimenti) {
		// Tronco gli avvertimenti a 4000
		if (StringUtils.isNotEmpty(avvertimenti) && avvertimenti.length() > 4000) {
			avvertimenti = avvertimenti.substring(0, 4000);
		}
		this.avvertimenti = avvertimenti;
	}

	public String tipoCasellaMittente() {
		if (categoria.equals(Categoria.ANOMALIA.getValue()) || categoria.equals(Categoria.ALTRO.getValue())
				|| categoria.equals(Categoria.DELIVERY_STATUS.getValue()))
			return PEO;
		return PEC;
	}

	public String getDescrizioneUtenteAssegn() {
		return descrizioneUtenteAssegn;
	}

	public void setDescrizioneUtenteAssegn(String descrizioneUtenteAssegn) {
		this.descrizioneUtenteAssegn = descrizioneUtenteAssegn;
	}

	public String getDescrizioneUoAssegn() {
		return descrizioneUoAssegn;
	}

	public void setDescrizioneUoAssegn(String descrizioneUoAssegn) {
		this.descrizioneUoAssegn = descrizioneUoAssegn;
	}

	public String getAccountMittenteCtx() {
		return accountMittenteCtx;
	}

	public void setAccountMittenteCtx(String accountMittenteCtx) {
		this.accountMittenteCtx = accountMittenteCtx;
	}

	public String getOggettoCtx() {
		return oggettoCtx;
	}

	public void setOggettoCtx(String oggettoCtx) {
		this.oggettoCtx = oggettoCtx;
	}

	public String getCorpoCtx() {
		return corpoCtx;
	}

	public void setCorpoCtx(String corpoCtx) {
		this.corpoCtx = corpoCtx;
	}

	public String getSiglaRegistroMitt() {
		return siglaRegistroMitt;
	}

	public void setSiglaRegistroMitt(String siglaRegistroMitt) {
		this.siglaRegistroMitt = siglaRegistroMitt;
	}

	public Integer getNumRegMitt() {
		return numRegMitt;
	}

	public void setNumRegMitt(Integer numRegMitt) {
		this.numRegMitt = numRegMitt;
	}

	public Short getAnnoRegMitt() {
		return annoRegMitt;
	}

	public void setAnnoRegMitt(Short annoRegMitt) {
		this.annoRegMitt = annoRegMitt;
	}

	public Date getTsRegMitt() {
		return tsRegMitt;
	}

	public void setTsRegMitt(Date tsRegMitt) {
		this.tsRegMitt = tsRegMitt;
	}

	public String getOggettoRegMitt() {
		return oggettoRegMitt;
	}

	public void setOggettoRegMitt(String oggettoRegMitt) {
		this.oggettoRegMitt = oggettoRegMitt;
	}

	public String getOggettoRegMittCtx() {
		return oggettoRegMittCtx;
	}

	public void setOggettoRegMittCtx(String oggettoRegMittCtx) {
		this.oggettoRegMittCtx = oggettoRegMittCtx;
	}

	public boolean isFlgRichConfLettura() {
		return flgRichConfLettura;
	}

	public void setFlgRichConfLettura(boolean flgRichConfLettura) {
		this.flgRichConfLettura = flgRichConfLettura;
	}

	public String getMessageIdTrasporto() {
		return messageIdTrasporto;
	}

	public void setMessageIdTrasporto(String messageIdTrasporto) {
		this.messageIdTrasporto = messageIdTrasporto;
	}

	public String getStatoLavorazione() {
		return statoLavorazione;
	}

	public void setStatoLavorazione(String statoLavorazione) {
		this.statoLavorazione = statoLavorazione;
	}

	public String getCodAzioneDaFare() {
		return codAzioneDaFare;
	}

	public void setCodAzioneDaFare(String codAzioneDaFare) {
		this.codAzioneDaFare = codAzioneDaFare;
	}

	public String getDettAzioneDaFare() {
		return dettAzioneDaFare;
	}

	public void setDettAzioneDaFare(String dettAzioneDaFare) {
		this.dettAzioneDaFare = dettAzioneDaFare;
	}

	public BigDecimal getProgrMsg() {
		return progrMsg;
	}

	public void setProgrMsg(BigDecimal progrMsg) {
		this.progrMsg = progrMsg;
	}

	public BigDecimal getAnnoProgrMsg() {
		return annoProgrMsg;
	}

	public void setAnnoProgrMsg(BigDecimal annoProgrMsg) {
		this.annoProgrMsg = annoProgrMsg;
	}

	public String getRegistroProgrMsg() {
		return registroProgrMsg;
	}

	public void setRegistroProgrMsg(String registroProgrMsg) {
		this.registroProgrMsg = registroProgrMsg;
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

	public Date getTsAggStatoLavorazione() {
		return tsAggStatoLavorazione;
	}

	public void setTsAggStatoLavorazione(Date tsAggStatoLavorazione) {
		this.tsAggStatoLavorazione = tsAggStatoLavorazione;
	}

	public BigDecimal getProgrBozza() {
		return progrBozza;
	}

	public void setProgrBozza(BigDecimal progrBozza) {
		this.progrBozza = progrBozza;
	}

	public Short getAnnoProgrBozza() {
		return annoProgrBozza;
	}

	public void setAnnoProgrBozza(Short annoProgrBozza) {
		this.annoProgrBozza = annoProgrBozza;
	}

	public String getIdUtenteAggStatoLav() {
		return idUtenteAggStatoLav;
	}

	public void setIdUtenteAggStatoLav(String idUtenteAggStatoLav) {
		this.idUtenteAggStatoLav = idUtenteAggStatoLav;
	}

	public String getMessageIdRif() {
		return messageIdRif;
	}

	public void setMessageIdRif(String messageIdRif) {
		this.messageIdRif = messageIdRif;
	}

	public String getErrMessageRicPec() {
		return errMessageRicPec;
	}

	public void setErrMessageRicPec(String errMessageRicPec) {
		this.errMessageRicPec = errMessageRicPec;
	}

	public String getMotivoEccezione() {
		return motivoEccezione;
	}

	public void setMotivoEccezione(String motivoEccezione) {
		this.motivoEccezione = motivoEccezione;
	}

	public boolean getFlgUriRicevuta() {
		return flgUriRicevuta;
	}

	public void setFlgUriRicevuta(boolean flgUriRicevuta) {
		this.flgUriRicevuta = flgUriRicevuta;
	}

	public String getNroRichiestaMassivaInvio() {
		return nroRichiestaMassivaInvio;
	}

	public void setNroRichiestaMassivaInvio(String nroRichiestaMassivaInvio) {
		this.nroRichiestaMassivaInvio = nroRichiestaMassivaInvio;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	
	public String getUriCorpo() {
		return uriCorpo;
	}

	public void setUriCorpo(String uriCorpo) {
		this.uriCorpo = uriCorpo;
	}

	public String getErrArchUriCorpo() {
		return errArchUriCorpo;
	}

	public void setErrArchUriCorpo(String errArchUriCorpo) {
		this.errArchUriCorpo = errArchUriCorpo;
	}

	public String getScCorpoMail() {
		return scCorpoMail;
	}

	public void setScCorpoMail(String scCorpoMail) {
		this.scCorpoMail = scCorpoMail;
	}

}