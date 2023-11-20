/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * Rappresentazione java dei dati estratti dalla store sottoforma di xml
 * @author massimo malvestio
 *
 */
public class FatturaPAXmlBean {

	@NumeroColonna(numero = "2")
	private BigDecimal idUd;

	@NumeroColonna(numero = "3")
	private String nomeUd;
	
	@NumeroColonna(numero = "14")
	private String siglaFattura;
	
	@NumeroColonna(numero = "43")
    @TipoData(tipo=Tipo.DATA)
    private Date dataIns;

	@NumeroColonna(numero = "32")
	private String tipoDocumento;
	
	@NumeroColonna(numero = "41")
	private String abilViewFilePrimario;
	
	@NumeroColonna(numero = "54")
	private String stato;
	
	@NumeroColonna(numero = "55")
	@TipoData(tipo=Tipo.DATA_ESTESA)
	private Date dataAggStato;
	
	@NumeroColonna(numero = "88")
	private String nota;
	
	@NumeroColonna(numero = "89")
	private BigDecimal score;
	
	@NumeroColonna(numero = "97")
	private String tipoInvioEmail;		
	
	@NumeroColonna(numero = "101")
	private String numeroFattura;
	
	@NumeroColonna(numero = "102")
	private String valuta;
	
	@NumeroColonna(numero = "103")
	private String codiceDestinatario;
	
	@NumeroColonna(numero = "104")
	private String denominazioneDestinatario;
	
	@NumeroColonna(numero = "105")
	private String idFiscaleDestinatario;
	
	@NumeroColonna(numero = "106")
	private String numeroOrdineAcquisto;
	
	@NumeroColonna(numero = "107")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataOrdineAcquisto;
	
	@NumeroColonna(numero = "108")
	private String cupOrdineAcquisto;
	
	@NumeroColonna(numero = "109")
	private String cigOrdineAcquisto;
	
	@NumeroColonna(numero = "110")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataScadenzaPagamento;
	
	@NumeroColonna(numero = "111")
	private String importo;
	
	@NumeroColonna(numero = "112")
	private String idTrasmissioneSdI;
	
	@NumeroColonna(numero = "113")
	@TipoData(tipo=Tipo.DATA_ESTESA)
	private Date dataInvioSdI;
	
	@NumeroColonna(numero = "114")
	private String erroreInvioSdI;
	
	
	@NumeroColonna(numero = "115")
	private String idSdI;
	
	
	@NumeroColonna(numero = "116")
	private String cidOrdineAcquisto;
	
	@NumeroColonna(numero = "117")
	private String billingAccountOrdineAcquisto;	
	
	@NumeroColonna(numero = "118")
	private String causaleNotaFattura;
	
	@NumeroColonna(numero = "119")
	private String gruppoDiRiferimento;
	
	@NumeroColonna(numero = "120")
	private String flgPivaCf;
	
	@NumeroColonna(numero = "121")
	private String esigibilitaIva;
	
	@NumeroColonna(numero = "122")
	private Boolean flgSegnoImporti;
	
	@NumeroColonna(numero = "123")
	private String rifAmministrativoInps;
	
	@NumeroColonna(numero = "124")
	private String posizioneFinanziaria;
	
	@NumeroColonna(numero = "125")
	private String annoPosizioneFinanziaria;
	
	@NumeroColonna(numero = "126")	
	private String flgInsManuale;
	
	@NumeroColonna(numero = "127")
	private String canaleProvenienzaDoc;

	@NumeroColonna(numero = "128")
	private String agency;

	@NumeroColonna(numero = "129")
	private String groupNumber;

	@NumeroColonna(numero = "130")
	private String bookingNumber;
     
	@NumeroColonna(numero = "203")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataDocumento;

	@NumeroColonna(numero = "218")
	private String societa;
	
	@NumeroColonna(numero = "219")
	private String statoConservazione;
	
	@NumeroColonna(numero = "220")
	private String denominazioneCliente;
	
	@NumeroColonna(numero = "231")
	private String canaleInvio;
	
	@NumeroColonna(numero = "265")
	private String motivoEsitoSdI;
	
	
	private Boolean emailInviataFlgPEC;
	private Boolean emailInviataFlgPEO;
	private Boolean emailInviataFlgInterop;
	private Boolean flgAbilModifica;
	private String emailPECDestinatario;
	private String formatoTrasmissione;
	private float importoNumeric;
	private String numeroFatturaFormatted;
	
	
	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	
	public String getNumeroFattura() {
		return numeroFattura;
	}

	public void setNumeroFattura(String numeroFattura) {
		this.numeroFattura = numeroFattura;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getCodiceDestinatario() {
		return codiceDestinatario;
	}

	public void setCodiceDestinatario(String codiceDestinatario) {
		this.codiceDestinatario = codiceDestinatario;
	}

	public String getDenominazioneDestinatario() {
		return denominazioneDestinatario;
	}

	public void setDenominazioneDestinatario(String denominazioneDestinatario) {
		this.denominazioneDestinatario = denominazioneDestinatario;
	}

	public String getIdFiscaleDestinatario() {
		return idFiscaleDestinatario;
	}

	public void setIdFiscaleDestinatario(String idFiscaleDestinatario) {
		this.idFiscaleDestinatario = idFiscaleDestinatario;
	}

	public String getNumeroOrdineAcquisto() {
		return numeroOrdineAcquisto;
	}

	public void setNumeroOrdineAcquisto(String numeroOrdineAcquisto) {
		this.numeroOrdineAcquisto = numeroOrdineAcquisto;
	}

	public Date getDataOrdineAcquisto() {
		return dataOrdineAcquisto;
	}

	public void setDataOrdineAcquisto(Date dataOrdineAcquisto) {
		this.dataOrdineAcquisto = dataOrdineAcquisto;
	}

	public String getCupOrdineAcquisto() {
		return cupOrdineAcquisto;
	}

	public void setCupOrdineAcquisto(String cupOrdineAcquisto) {
		this.cupOrdineAcquisto = cupOrdineAcquisto;
	}

	public String getCigOrdineAcquisto() {
		return cigOrdineAcquisto;
	}

	public void setCigOrdineAcquisto(String cigOrdineAcquisto) {
		this.cigOrdineAcquisto = cigOrdineAcquisto;
	}

	public Date getDataScadenzaPagamento() {
		return dataScadenzaPagamento;
	}

	public void setDataScadenzaPagamento(Date dataScadenzaPagamento) {
		this.dataScadenzaPagamento = dataScadenzaPagamento;
	}

	public String getValuta() {
		return valuta;
	}

	public void setValuta(String valuta) {
		this.valuta = valuta;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getIdTrasmissioneSdI() {
		return idTrasmissioneSdI;
	}

	public void setIdTrasmissioneSdI(String idTrasmissioneSdI) {
		this.idTrasmissioneSdI = idTrasmissioneSdI;
	}

	public Date getDataInvioSdI() {
		return dataInvioSdI;
	}

	public void setDataInvioSdI(Date dataInvioSdI) {
		this.dataInvioSdI = dataInvioSdI;
	}

	public String getErroreInvioSdI() {
		return erroreInvioSdI;
	}

	public void setErroreInvioSdI(String erroreInvioSdI) {
		this.erroreInvioSdI = erroreInvioSdI;
	}

	public String getIdSdI() {
		return idSdI;
	}

	public void setIdSdI(String idSdI) {
		this.idSdI = idSdI;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Date getDataAggStato() {
		return dataAggStato;
	}

	public void setDataAggStato(Date dataAggStato) {
		this.dataAggStato = dataAggStato;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}
	
	public String getSocieta() {
		return societa;
	}

	public void setSocieta(String societa) {
		this.societa = societa;
	}

	public String getStatoConservazione() {
		return statoConservazione;
	}

	public void setStatoConservazione(String statoConservazione) {
		this.statoConservazione = statoConservazione;
	}

	public String getCidOrdineAcquisto() {
		return cidOrdineAcquisto;
	}

	public String getBillingAccountOrdineAcquisto() {
		return billingAccountOrdineAcquisto;
	}

	public void setCidOrdineAcquisto(String cidOrdineAcquisto) {
		this.cidOrdineAcquisto = cidOrdineAcquisto;
	}

	public void setBillingAccountOrdineAcquisto(String billingAccountOrdineAcquisto) {
		this.billingAccountOrdineAcquisto = billingAccountOrdineAcquisto;
	}
	
	public String getDenominazioneCliente() {
		return denominazioneCliente;
	}
	
	public void setDenominazioneCliente(String denominazioneCliente) {
		this.denominazioneCliente = denominazioneCliente;
	}
	
	public String getNomeUd() {
		return nomeUd;
	}

	public void setNomeUd(String nomeUd) {
		this.nomeUd = nomeUd;
	}

	public String getSiglaFattura() {
		return siglaFattura;
	}

	public void setSiglaFattura(String siglaFattura) {
		this.siglaFattura = siglaFattura;
	}
	
	public Boolean getEmailInviataFlgPEC() {
		return emailInviataFlgPEC;
	}

	public void setEmailInviataFlgPEC(Boolean emailInviataFlgPEC) {
		this.emailInviataFlgPEC = emailInviataFlgPEC;
	}

	public Boolean getEmailInviataFlgPEO() {
		return emailInviataFlgPEO;
	}

	public void setEmailInviataFlgPEO(Boolean emailInviataFlgPEO) {
		this.emailInviataFlgPEO = emailInviataFlgPEO;
	}
	
	public Boolean getEmailInviataFlgInterop() {
		return emailInviataFlgInterop;
	}

	public void setEmailInviataFlgInterop(Boolean emailInviataFlgInterop) {
		this.emailInviataFlgInterop = emailInviataFlgInterop;
	}

	public String getTipoInvioEmail() {
		return tipoInvioEmail;
	}

	public void setTipoInvioEmail(String tipoInvioEmail) {
		this.tipoInvioEmail = tipoInvioEmail;
	}

	public String getAbilViewFilePrimario() {
		return abilViewFilePrimario;
	}

	public void setAbilViewFilePrimario(String abilViewFilePrimario) {
		this.abilViewFilePrimario = abilViewFilePrimario;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
	
	public String getCausaleNotaFattura() {
		return causaleNotaFattura;
	}

	public void setCausaleNotaFattura(String causaleNotaFattura) {
		this.causaleNotaFattura = causaleNotaFattura;
	}

	public String getGruppoDiRiferimento() {
		return gruppoDiRiferimento;
	}

	public void setGruppoDiRiferimento(String gruppoDiRiferimento) {
		this.gruppoDiRiferimento = gruppoDiRiferimento;
	}

	public String getEsigibilitaIva() {
		return esigibilitaIva;
	}

	public void setEsigibilitaIva(String esigibilitaIva) {
		this.esigibilitaIva = esigibilitaIva;
	}

	public Boolean getFlgSegnoImporti() {
		return flgSegnoImporti;
	}

	public void setFlgSegnoImporti(Boolean flgSegnoImporti) {
		this.flgSegnoImporti = flgSegnoImporti;
	}

	public String getFlgPivaCf() {
		return flgPivaCf;
	}

	public void setFlgPivaCf(String flgPivaCf) {
		this.flgPivaCf = flgPivaCf;
	}

	public String getRifAmministrativoInps() {
		return rifAmministrativoInps;
	}

	public void setRifAmministrativoInps(String rifAmministrativoInps) {
		this.rifAmministrativoInps = rifAmministrativoInps;
	}

	public String getPosizioneFinanziaria() {
		return posizioneFinanziaria;
	}

	public void setPosizioneFinanziaria(String posizioneFinanziaria) {
		this.posizioneFinanziaria = posizioneFinanziaria;
	}
	
	public String getAnnoPosizioneFinanziaria() {
		return annoPosizioneFinanziaria;
	}

	public void setAnnoPosizioneFinanziaria(String annoPosizioneFinanziaria) {
		this.annoPosizioneFinanziaria = annoPosizioneFinanziaria;
	}

	public String getFlgInsManuale() {
		return flgInsManuale;
	}
	
	public void setFlgInsManuale(String flgInsManuale) {
		this.flgInsManuale = flgInsManuale;
	}

	public Boolean getFlgAbilModifica() {
		return flgAbilModifica;
	}

	public void setFlgAbilModifica(Boolean flgAbilModifica) {
		this.flgAbilModifica = flgAbilModifica;
	}

	public String getCanaleInvio() {
		return canaleInvio;
	}

	public void setCanaleInvio(String canaleInvio) {
		this.canaleInvio = canaleInvio;
	}

	public String getCanaleProvenienzaDoc() {
		return canaleProvenienzaDoc;
	}

	public void setCanaleProvenienzaDoc(String canaleProvenienzaDoc) {
		this.canaleProvenienzaDoc = canaleProvenienzaDoc;
	}

	public String getEmailPECDestinatario() {
		return emailPECDestinatario;
	}

	public void setEmailPECDestinatario(String emailPECDestinatario) {
		this.emailPECDestinatario = emailPECDestinatario;
	}

	public String getFormatoTrasmissione() {
		return formatoTrasmissione;
	}

	public void setFormatoTrasmissione(String formatoTrasmissione) {
		this.formatoTrasmissione = formatoTrasmissione;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public String getBookingNumber() {
		return bookingNumber;
	}

	public void setBookingNumber(String bookingNumber) {
		this.bookingNumber = bookingNumber;
	}

	public float getImportoNumeric() {
		return importoNumeric;
	}

	public void setImportoNumeric(float importoNumeric) {
		this.importoNumeric = importoNumeric;
	}

	

	public Date getDataIns() {
		return dataIns;
	}

	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}

	public String getMotivoEsitoSdI() {
		return motivoEsitoSdI;
	}

	public void setMotivoEsitoSdI(String motivoEsitoSdI) {
		this.motivoEsitoSdI = motivoEsitoSdI;
	}

	public String getNumeroFatturaFormatted() {
		return numeroFatturaFormatted;
	}

	public void setNumeroFatturaFormatted(String numeroFatturaFormatted) {
		this.numeroFatturaFormatted = numeroFatturaFormatted;
	}

}
