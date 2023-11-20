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
public class FatturaPassivaXmlBean {

	@NumeroColonna(numero = "1")
	private String flgUd;
	
	@NumeroColonna(numero = "2")
	private BigDecimal idUd;
	
	@NumeroColonna(numero = "8")
	private String riferimentoRegistrazione;
	
	@NumeroColonna(numero = "9")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataRegistrazione;
	
	@NumeroColonna(numero = "18")
	private String oggetto;

	@NumeroColonna(numero = "20")
	private String flgTipoProv;
	
	@NumeroColonna(numero = "31")
	private String tipoFattura;

	@NumeroColonna(numero = "32")
	private String descTipoFattura;

	@NumeroColonna(numero = "33")
	private BigDecimal idDoc;

	
	@NumeroColonna(numero = "53")
	private String descStatoFattura;
	
	@NumeroColonna(numero = "54")
	private String descStatoDettFattura;
	
	@NumeroColonna(numero = "55")
	@TipoData(tipo=Tipo.DATA_ESTESA)
	private Date dataUltimoAggStato;

	@NumeroColonna(numero = "88")
	private String nota;

	@NumeroColonna(numero = "202")
	@TipoData(tipo=Tipo.DATA)
	private Date dataConsegnaSdi;

	@NumeroColonna(numero = "203")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataFattura;

	@NumeroColonna(numero = "218")
	private String societa;

	@NumeroColonna(numero = "219")
	private String statoConservazione;
	
	@NumeroColonna(numero = "228")
	private String uriFilePrimario;
	
	@NumeroColonna(numero = "262")
	private String codiceSocieta;
	
	@NumeroColonna(numero = "101")
	private String numeroFattura;
		
	@NumeroColonna(numero = "102")
	private String valutaFattura;
		
	@NumeroColonna(numero = "103")
	private String codiceDestinatario;

	@NumeroColonna(numero = "104")
	private String denominazioneFornitore;
		
	@NumeroColonna(numero = "105")
	private String codiceFiscaleFornitore;

	@NumeroColonna(numero = "106")
	private String partitaIvaFornitore;

	@NumeroColonna(numero = "107")
	private String nrODA;
		
	@NumeroColonna(numero = "108")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataODA;

	@NumeroColonna(numero = "109")
	private String cupODA;
	
	@NumeroColonna(numero = "110")
	private String cigODA;
	
	@NumeroColonna(numero = "111")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataScadenzaPagamento;

	@NumeroColonna(numero = "112")
	private String importoFattura;

	@NumeroColonna(numero = "113")
	private String idTrasmIntermediario;

    @NumeroColonna(numero = "114")
	private String idSdI;

	@NumeroColonna(numero = "115")
	private String causaleNotaFattura;

	@NumeroColonna(numero = "116")
	private String esigibilitaIva;
	
	@NumeroColonna(numero = "117")
	private String statoRegistrazione;
	
	@NumeroColonna(numero = "118")
	private String partitaIvaIntermediario;
	
	@NumeroColonna(numero = "119")
	private String targaVeicolo;
	
	@NumeroColonna(numero = "120")
	private String telaioVeicolo;
	
	@NumeroColonna(numero = "121")	
	private String flgInsManuale;

	@NumeroColonna(numero = "122")	
	private String flgInsManualeNoXml;
	
	@NumeroColonna(numero = "123")	
	private String idAssegnatario;
	
	@NumeroColonna(numero = "124")	
	private String descAssegnatario;
	
	private String numeroRegistrazione;
	private String categoriaRegistrazione;
	private String siglaRegistrazione;
	private String annoRegistrazione;
	private Boolean flgAbilModifica;
	private Boolean flgAbilView;
	private String emailPECDestinatario;
	private String formatoTrasmissione;
	private float importoFatturaNumeric;
	private String codStatoFattura;
	private String codStatoDettFattura;
		
	
	public String getFlgUd() {
		return flgUd;
	}

	public void setFlgUd(String flgUd) {
		this.flgUd = flgUd;
	}

	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	public String getSocieta() {
		return societa;
	}

	public void setSocieta(String societa) {
		this.societa = societa;
	}

	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}


	public String getNumeroFattura() {
		return numeroFattura;
	}

	public void setNumeroFattura(String numeroFattura) {
		this.numeroFattura = numeroFattura;
	}

	public Date getDataFattura() {
		return dataFattura;
	}

	public void setDataFattura(Date dataFattura) {
		this.dataFattura = dataFattura;
	}

	public String getTipoFattura() {
		return tipoFattura;
	}

	public void setTipoFattura(String tipoFattura) {
		this.tipoFattura = tipoFattura;
	}

	public String getImportoFattura() {
		return importoFattura;
	}

	public void setImportoFattura(String importoFattura) {
		this.importoFattura = importoFattura;
	}

	public String getValutaFattura() {
		return valutaFattura;
	}

	public void setValutaFattura(String valutaFattura) {
		this.valutaFattura = valutaFattura;
	}

	public Date getDataScadenzaPagamento() {
		return dataScadenzaPagamento;
	}

	public void setDataScadenzaPagamento(Date dataScadenzaPagamento) {
		this.dataScadenzaPagamento = dataScadenzaPagamento;
	}

	public String getEsigibilitaIva() {
		return esigibilitaIva;
	}

	public void setEsigibilitaIva(String esigibilitaIva) {
		this.esigibilitaIva = esigibilitaIva;
	}

	public String getNrODA() {
		return nrODA;
	}

	public void setNrODA(String nrODA) {
		this.nrODA = nrODA;
	}

	public Date getDataODA() {
		return dataODA;
	}

	public void setDataODA(Date dataODA) {
		this.dataODA = dataODA;
	}

	public String getCodiceDestinatario() {
		return codiceDestinatario;
	}

	public void setCodiceDestinatario(String codiceDestinatario) {
		this.codiceDestinatario = codiceDestinatario;
	}

	public Date getDataUltimoAggStato() {
		return dataUltimoAggStato;
	}

	public void setDataUltimoAggStato(Date dataUltimoAggStato) {
		this.dataUltimoAggStato = dataUltimoAggStato;
	}

	
	public String getStatoConservazione() {
		return statoConservazione;
	}

	public void setStatoConservazione(String statoConservazione) {
		this.statoConservazione = statoConservazione;
	}

	public String getCausaleNotaFattura() {
		return causaleNotaFattura;
	}

	public void setCausaleNotaFattura(String causaleNotaFattura) {
		this.causaleNotaFattura = causaleNotaFattura;
	}

	public String getIdSdI() {
		return idSdI;
	}

	public void setIdSdI(String idSdI) {
		this.idSdI = idSdI;
	}

	public String getIdTrasmIntermediario() {
		return idTrasmIntermediario;
	}

	public void setIdTrasmIntermediario(String idTrasmIntermediario) {
		this.idTrasmIntermediario = idTrasmIntermediario;
	}

	public Boolean getFlgAbilModifica() {
		return flgAbilModifica;
	}

	public void setFlgAbilModifica(Boolean flgAbilModifica) {
		this.flgAbilModifica = flgAbilModifica;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}

	public String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}

	public void setPartitaIvaFornitore(String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}

	public String getStatoRegistrazione() {
		return statoRegistrazione;
	}

	public void setStatoRegistrazione(String statoRegistrazione) {
		this.statoRegistrazione = statoRegistrazione;
	}

	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public BigDecimal getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}

	public String getPartitaIvaIntermediario() {
		return partitaIvaIntermediario;
	}

	public void setPartitaIvaIntermediario(String partitaIvaIntermediario) {
		this.partitaIvaIntermediario = partitaIvaIntermediario;
	}

	public Date getDataConsegnaSdi() {
		return dataConsegnaSdi;
	}

	public void setDataConsegnaSdi(Date dataConsegnaSdi) {
		this.dataConsegnaSdi = dataConsegnaSdi;
	}

	public String getCupODA() {
		return cupODA;
	}

	public void setCupODA(String cupODA) {
		this.cupODA = cupODA;
	}

	public String getCigODA() {
		return cigODA;
	}

	public void setCigODA(String cigODA) {
		this.cigODA = cigODA;
	}

	public String getCategoriaRegistrazione() {
		return categoriaRegistrazione;
	}

	public void setCategoriaRegistrazione(String categoriaRegistrazione) {
		this.categoriaRegistrazione = categoriaRegistrazione;
	}

	public String getSiglaRegistrazione() {
		return siglaRegistrazione;
	}

	public void setSiglaRegistrazione(String siglaRegistrazione) {
		this.siglaRegistrazione = siglaRegistrazione;
	}

	public String getAnnoRegistrazione() {
		return annoRegistrazione;
	}

	public void setAnnoRegistrazione(String annoRegistrazione) {
		this.annoRegistrazione = annoRegistrazione;
	}

	public String getRiferimentoRegistrazione() {
		return riferimentoRegistrazione;
	}

	public void setRiferimentoRegistrazione(String riferimentoRegistrazione) {
		this.riferimentoRegistrazione = riferimentoRegistrazione;
	}

	public String getNumeroRegistrazione() {
		return numeroRegistrazione;
	}

	public void setNumeroRegistrazione(String numeroRegistrazione) {
		this.numeroRegistrazione = numeroRegistrazione;
	}

	public String getCodiceSocieta() {
		return codiceSocieta;
	}

	public void setCodiceSocieta(String codiceSocieta) {
		this.codiceSocieta = codiceSocieta;
	}

	public String getFlgInsManuale() {
		return flgInsManuale;
	}

	public void setFlgInsManuale(String flgInsManuale) {
		this.flgInsManuale = flgInsManuale;
	}

	public float getImportoFatturaNumeric() {
		return importoFatturaNumeric;
	}

	public void setImportoFatturaNumeric(float importoFatturaNumeric) {
		this.importoFatturaNumeric = importoFatturaNumeric;
	}

	public String getFlgInsManualeNoXml() {
		return flgInsManualeNoXml;
	}

	public void setFlgInsManualeNoXml(String flgInsManualeNoXml) {
		this.flgInsManualeNoXml = flgInsManualeNoXml;
	}

	public Boolean getFlgAbilView() {
		return flgAbilView;
	}

	public void setFlgAbilView(Boolean flgAbilView) {
		this.flgAbilView = flgAbilView;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getUriFilePrimario() {
		return uriFilePrimario;
	}

	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}

	public String getDescTipoFattura() {
		return descTipoFattura;
	}

	public void setDescTipoFattura(String descTipoFattura) {
		this.descTipoFattura = descTipoFattura;
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

	public String getFlgTipoProv() {
		return flgTipoProv;
	}

	public void setFlgTipoProv(String flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}

	public String getDescStatoFattura() {
		return descStatoFattura;
	}

	public void setDescStatoFattura(String descStatoFattura) {
		this.descStatoFattura = descStatoFattura;
	}

	public String getDescStatoDettFattura() {
		return descStatoDettFattura;
	}

	public void setDescStatoDettFattura(String descStatoDettFattura) {
		this.descStatoDettFattura = descStatoDettFattura;
	}

	public String getCodStatoFattura() {
		return codStatoFattura;
	}

	public void setCodStatoFattura(String codStatoFattura) {
		this.codStatoFattura = codStatoFattura;
	}

	public String getCodStatoDettFattura() {
		return codStatoDettFattura;
	}

	public void setCodStatoDettFattura(String codStatoDettFattura) {
		this.codStatoDettFattura = codStatoDettFattura;
	}

	public String getTargaVeicolo() {
		return targaVeicolo;
	}

	public void setTargaVeicolo(String targaVeicolo) {
		this.targaVeicolo = targaVeicolo;
	}

	public String getTelaioVeicolo() {
		return telaioVeicolo;
	}

	public void setTelaioVeicolo(String telaioVeicolo) {
		this.telaioVeicolo = telaioVeicolo;
	}

	public String getDescAssegnatario() {
		return descAssegnatario;
	}

	public void setDescAssegnatario(String descAssegnatario) {
		this.descAssegnatario = descAssegnatario;
	}

	public String getIdAssegnatario() {
		return idAssegnatario;
	}

	public void setIdAssegnatario(String idAssegnatario) {
		this.idAssegnatario = idAssegnatario;
	}

	
	
	}
