/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;


public class OrdiniXmlBean {

	@NumeroColonna(numero = "2")
	private BigDecimal idUd;

	@NumeroColonna(numero = "3")
	private String nomeUd;
	
	@NumeroColonna(numero = "8")
	private String riferimentoRegistrazione;
	
	@NumeroColonna(numero = "9")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataRegistrazione;
	
	@NumeroColonna(numero = "14")
	private String siglaFattura;
	
	@NumeroColonna(numero = "20")
	private String flgTipoProv;
	
	@NumeroColonna(numero = "32")
	private String tipoDocumento;
	
	@NumeroColonna(numero = "33")
	private BigDecimal idDoc;
	
	@NumeroColonna(numero = "41")
	private String azioniPossibili;
	
	@NumeroColonna(numero = "43")
    @TipoData(tipo=Tipo.DATA)
    private Date dataIns;
	
	@NumeroColonna(numero = "53")
	private String stato;
	
	@NumeroColonna(numero = "54")
	private String statoDett;
	
	@NumeroColonna(numero = "55")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataAggStato;
	
	@NumeroColonna(numero = "88")
	private String nota;
	
	@NumeroColonna(numero = "89")
	private BigDecimal score;
	
	private Boolean emailInviataFlgPEC;
	
	private Boolean emailInviataFlgPEO;
	
	private Boolean emailInviataFlgInterOp;
	
	@NumeroColonna(numero = "97")
	private String tipoInvioEmail;
	
	@NumeroColonna(numero = "203")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataDocumento;

	@NumeroColonna(numero = "218")
	private String societa;
	
	@NumeroColonna(numero = "219")
	private String statoConservazione;
	
	@NumeroColonna(numero = "220")
	private String denominazioneCliente;
	

	
	@NumeroColonna(numero = "221")
	private String partitaIvaCliente;
	
	@NumeroColonna(numero = "222")
	private String codiceCliente;

	@NumeroColonna(numero = "227")
	private String statoTrasmissioneMail;

	@NumeroColonna(numero = "231")
	private String canaleInvio;
	
	@NumeroColonna(numero = "235")
	private String uriOrdine;
	
	@NumeroColonna(numero = "236")
	private String nomeOrdine;
	
	/*
	 *  Ordini
	 */
	@NumeroColonna(numero = "237")
	private String ordID;
	@NumeroColonna(numero = "238")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date ordData;
	@NumeroColonna(numero = "239")
	private String ordIDTrasmSdi;
	@NumeroColonna(numero = "240")
	private String ordBuyerDes;
	@NumeroColonna(numero = "241")
	private String ordSellerDes;
	@NumeroColonna(numero = "242")
	private String ordImporto;
	@NumeroColonna(numero = "243")
	private String ordBuyerId;
	@NumeroColonna(numero = "244")
	private String ordSellerId;
	@NumeroColonna(numero = "245")
	private String ordAssegnazione;
	@NumeroColonna(numero = "246")
	private String ordClassificazione;
	@NumeroColonna(numero = "262")
	private String codApplOwner;
	@NumeroColonna(numero = "265")
	private String provincia;
	@NumeroColonna(numero = "266")
	private String intestatario;
	@NumeroColonna(numero = "267")
	private String piva;
	@NumeroColonna(numero = "268")
	private String idMitt;
	@NumeroColonna(numero = "269")
	private String destCons;
	@NumeroColonna(numero = "270")
	private String puntoCons;
	@NumeroColonna(numero = "271")
	private String descrizione_servizio_bene;
	@NumeroColonna(numero = "272")
	private String prezzo_totale_imponibile;
	@NumeroColonna(numero = "273")
	private String aic;
	@NumeroColonna(numero = "274")
	private String numero_di_reperto_del_dispositivo_medico;
	@NumeroColonna(numero = "275")
	private String note;
	@NumeroColonna(numero = "282")//"276")
	private String cig;
	@NumeroColonna(numero = "277")
	private String noteInterne;
	@NumeroColonna(numero = "278")
	private String tipoOrdine;
	@NumeroColonna(numero = "279")
	private String statoOrdine;
	@NumeroColonna(numero = "281")
	private String orderReference;
	@NumeroColonna(numero = "283")
	private String cup;
	@NumeroColonna(numero = "284")
	private String risposto;
	@NumeroColonna(numero = "285")
	private String sottotipoOrdine;
	
	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getOrderReference() {
		return orderReference;
	}

	public void setOrderReference(String orderReference) {
		this.orderReference = orderReference;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getIntestatario() {
		return intestatario;
	}

	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	public String getIdMitt() {
		return idMitt;
	}

	public void setIdMitt(String idMitt) {
		this.idMitt = idMitt;
	}

	public String getDestCons() {
		return destCons;
	}

	public void setDestCons(String destCons) {
		this.destCons = destCons;
	}

	public String getPuntoCons() {
		return puntoCons;
	}

	public void setPuntoCons(String puntoCons) {
		this.puntoCons = puntoCons;
	}

	public String getDescrizione_servizio_bene() {
		return descrizione_servizio_bene;
	}

	public void setDescrizione_servizio_bene(String descrizione_servizio_bene) {
		this.descrizione_servizio_bene = descrizione_servizio_bene;
	}

	public String getPrezzo_totale_imponibile() {
		return prezzo_totale_imponibile;
	}

	public void setPrezzo_totale_imponibile(String prezzo_totale_imponibile) {
		this.prezzo_totale_imponibile = prezzo_totale_imponibile;
	}

	public String getAic() {
		return aic;
	}

	public void setAic(String aic) {
		this.aic = aic;
	}

	public String getNumero_di_reperto_del_dispositivo_medico() {
		return numero_di_reperto_del_dispositivo_medico;
	}

	public void setNumero_di_reperto_del_dispositivo_medico(String numero_di_reperto_del_dispositivo_medico) {
		this.numero_di_reperto_del_dispositivo_medico = numero_di_reperto_del_dispositivo_medico;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public String getNoteInterne() {
		return noteInterne;
	}

	public void setNoteInterne(String noteInterne) {
		this.noteInterne = noteInterne;
	}

	/*
	 * ATTRIBUTI CUSTOM ( partono dal 100)
	 */
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
	
	
	@NumeroColonna(numero = "280")//110")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataScadenzaPagamento;
	
	@NumeroColonna(numero = "111")
	private String importo;
	
	@NumeroColonna(numero = "112")
	private String idTrasmissioneSdI;
	
	@NumeroColonna(numero = "113")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataInvioSdI;
	
	@NumeroColonna(numero = "114")
	private String erroreInvioSdI;
	
	@NumeroColonna(numero = "115")
	private String idSdI;
	
	@NumeroColonna(numero = "117")
	private String statoRegistrazione;
	
	@NumeroColonna(numero = "118")
	private String causaleNotaFattura;
		
	private String descSiglaFattura;
	
	private String abilViewFilePrimario;
	
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
		
		if (emailInviataFlgPEC == null)
			return false;
		else
			return emailInviataFlgPEC;
		
	}

	public void setEmailInviataFlgPEC(Boolean emailInviataFlgPEC) {
		   this.emailInviataFlgPEC = emailInviataFlgPEC;
	}

	public Boolean getEmailInviataFlgPEO() {
		if (emailInviataFlgPEO == null)
			return false;
		else
		   return emailInviataFlgPEO;
	}

	public void setEmailInviataFlgPEO(Boolean emailInviataFlgPEO) {		
		   this.emailInviataFlgPEO = emailInviataFlgPEO;
	}

	public Boolean getEmailInviataFlgInterOp() {
		
		if(emailInviataFlgInterOp == null)
			return false;
		else
			return emailInviataFlgInterOp;
	}

	public void setEmailInviataFlgInterOp(Boolean emailInviataFlgInterOp) {
		this.emailInviataFlgInterOp = emailInviataFlgInterOp;
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

	public String getTipoInvioEmail() {
		
		if(getEmailInviataFlgPEC()) {
			return "PEC;";
		} else if(getEmailInviataFlgPEO()) {
			return "PEO;";
		} else if(getEmailInviataFlgInterOp()) {
			return "INTEROP;";
		} else {
			return "";
		}
	}

	public void setTipoInvioEmail(String tipoInvioEmail) {
		
		if (tipoInvioEmail == null) {
			tipoInvioEmail = "";
		}
		
		this.tipoInvioEmail = tipoInvioEmail;
		
		if (tipoInvioEmail!=null && !tipoInvioEmail.equalsIgnoreCase("")){
			setEmailInviataFlgPEC(Boolean.TRUE);
			setEmailInviataFlgPEO(Boolean.TRUE);
			setEmailInviataFlgInterOp(Boolean.TRUE);
		}
		else{
			setEmailInviataFlgPEC(Boolean.FALSE);
			setEmailInviataFlgPEO(Boolean.FALSE);
			setEmailInviataFlgInterOp(Boolean.FALSE);
		}
		
			
		/*
		if(tipoInvioEmail.contains("PEC")) {
			
			setEmailInviataFlgPEC(Boolean.TRUE);
			setEmailInviataFlgPEO(Boolean.FALSE);
			setEmailInviataFlgInterOp(Boolean.FALSE);
			
		} else if(tipoInvioEmail.contains("PEO")){
			
			setEmailInviataFlgPEC(Boolean.FALSE);
			setEmailInviataFlgPEO(Boolean.TRUE);
			setEmailInviataFlgInterOp(Boolean.FALSE);
			
		} else if(tipoInvioEmail.contains("INTEROP")){
			setEmailInviataFlgPEC(Boolean.FALSE);
			setEmailInviataFlgPEO(Boolean.FALSE);
			setEmailInviataFlgInterOp(Boolean.TRUE);
		}	
		else {
				setEmailInviataFlgPEC(Boolean.FALSE);
				setEmailInviataFlgPEO(Boolean.FALSE);
				setEmailInviataFlgInterOp(Boolean.FALSE);
		}
		
		*/
	}

	

	public String getPartitaIvaCliente() {
		return partitaIvaCliente;
	}

	public void setPartitaIvaCliente(String partitaIvaCliente) {
		this.partitaIvaCliente = partitaIvaCliente;
	}

	public String getCodiceCliente() {
		return codiceCliente;
	}

	public void setCodiceCliente(String codiceCliente) {
		this.codiceCliente = codiceCliente;
	}
	
	public String getDescSiglaFattura() {
		return descSiglaFattura;
	}

	public void setDescSiglaFattura(String descSiglaFattura) {
		this.descSiglaFattura = descSiglaFattura;
	}

	public String getAzioniPossibili() {
		return azioniPossibili;
	}

	public void setAzioniPossibili(String azioniPossibili) {
		
		if (azioniPossibili == null) {
			azioniPossibili = "";
		}
		
		this.azioniPossibili = azioniPossibili;
		
        if(!azioniPossibili.equalsIgnoreCase("")) {
        	
        	if(azioniPossibili.charAt(18) == '1')                                                                                 //        -- flag 19: Visualizzazione file primario
        		setAbilViewFilePrimario("1");
				else
					setAbilViewFilePrimario("0");
        	
			
		} 

	}

	public String getStatoTrasmissioneMail() {
		return statoTrasmissioneMail;
	}

	public void setStatoTrasmissioneMail(String statoTrasmissioneMail) {
		this.statoTrasmissioneMail = statoTrasmissioneMail;
	}

	public String getStatoDett() {
		return statoDett;
	}

	public void setStatoDett(String statoDett) {
		this.statoDett = statoDett;
	}

	public String getCanaleInvio() {
		return canaleInvio;
	}

	public void setCanaleInvio(String canaleInvio) {
		this.canaleInvio = canaleInvio;
	}

	public String getUriOrdine() {
		return uriOrdine;
	}

	public void setUriOrdine(String uriOrdine) {
		this.uriOrdine = uriOrdine;
	}

	public String getNomeOrdine() {
		return nomeOrdine;
	}

	public void setNomeOrdine(String nomeOrdine) {
		this.nomeOrdine = nomeOrdine;
	}

	public String getOrdID() {
		return ordID;
	}

	public void setOrdID(String ordID) {
		this.ordID = ordID;
	}

	public Date getOrdData() {
		return ordData;
	}

	public void setOrdData(Date ordData) {
		this.ordData = ordData;
	}

	public String getOrdIDTrasmSdi() {
		return ordIDTrasmSdi;
	}

	public void setOrdIDTrasmSdi(String ordIDTrasmSdi) {
		this.ordIDTrasmSdi = ordIDTrasmSdi;
	}

	public String getOrdBuyerDes() {
		return ordBuyerDes;
	}

	public void setOrdBuyerDes(String ordBuyerDes) {
		this.ordBuyerDes = ordBuyerDes;
	}

	public String getOrdSellerDes() {
		return ordSellerDes;
	}

	public void setOrdSellerDes(String ordSellerDes) {
		this.ordSellerDes = ordSellerDes;
	}

	public String getOrdImporto() {
		return ordImporto;
	}

	public void setOrdImporto(String ordImporto) {
		this.ordImporto = ordImporto;
	}

	public String getOrdBuyerId() {
		return ordBuyerId;
	}

	public void setOrdBuyerId(String ordBuyerId) {
		this.ordBuyerId = ordBuyerId;
	}

	public String getOrdSellerId() {
		return ordSellerId;
	}

	public void setOrdSellerId(String ordSellerId) {
		this.ordSellerId = ordSellerId;
	}

	public String getRiferimentoRegistrazione() {
		return riferimentoRegistrazione;
	}

	public void setRiferimentoRegistrazione(String riferimentoRegistrazione) {
		this.riferimentoRegistrazione = riferimentoRegistrazione;
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

	public String getOrdAssegnazione() {
		return ordAssegnazione;
	}

	public void setOrdAssegnazione(String ordAssegnazione) {
		this.ordAssegnazione = ordAssegnazione;
	}

	public String getOrdClassificazione() {
		return ordClassificazione;
	}

	public void setOrdClassificazione(String ordClassificazione) {
		this.ordClassificazione = ordClassificazione;
	}

	public String getStatoRegistrazione() {
		return statoRegistrazione;
	}

	public void setStatoRegistrazione(String statoRegistrazione) {
		this.statoRegistrazione = statoRegistrazione;
	}

	public String getFlgTipoProv() {
		return flgTipoProv;
	}

	public void setFlgTipoProv(String flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}

	public Date getDataIns() {
		return dataIns;
	}

	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}

	public String getTipoOrdine() {
		return tipoOrdine;
	}

	public void setTipoOrdine(String tipoOrdine) {
		this.tipoOrdine = tipoOrdine;
	}
    
	public String getStatoOrdine() {
		return statoOrdine;
	}
	
	public void setStatoOrdine(String statoOrdine) {
		this.statoOrdine = statoOrdine;
	}

	public String getCodApplOwner() {
		return codApplOwner;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

	public String getRisposto() {
		return risposto;
	}

	public void setRisposto(String risposto) {
		this.risposto = risposto;
	}

	public String getSottotipoOrdine() {
		return sottotipoOrdine;
	}

	public void setSottotipoOrdine(String sottotipoOrdine) {
		this.sottotipoOrdine = sottotipoOrdine;
	}
	
}
