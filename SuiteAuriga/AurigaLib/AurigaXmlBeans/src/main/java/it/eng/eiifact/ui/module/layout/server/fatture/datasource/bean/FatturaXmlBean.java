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
public class FatturaXmlBean {

	@NumeroColonna(numero = "2")
	private BigDecimal idUd;

	@NumeroColonna(numero = "3")
	private String nomeUd;
	
	@NumeroColonna(numero = "14")
	private String siglaFattura;
	
	@NumeroColonna(numero = "32")
	private String tipoDocumento;
	
	@NumeroColonna(numero = "41")
	private String azioniPossibili;
	
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
	
	/*
	 *  DDT
	 */
	
	@NumeroColonna(numero = "235")
	private String uriFatturaDDT;
	
	@NumeroColonna(numero = "236")
	private String nomeFatturaDDT;
	
	
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
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
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
	private String nrFatturaDDT;
	
	@NumeroColonna(numero = "120")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataFatturaDDT;


	@NumeroColonna(numero = "277")
	private String tipoInvioDefault;

	@NumeroColonna(numero = "278")
	private String brand;
	
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

	
	/**
	 * @return the nrFatturaDDT
	 */
	public String getNrFatturaDDT() {
		return nrFatturaDDT;
	}

	
	/**
	 * @param nrFatturaDDT the nrFatturaDDT to set
	 */
	public void setNrFatturaDDT(String nrFatturaDDT) {
		this.nrFatturaDDT = nrFatturaDDT;
	}

	
	/**
	 * @return the dataFatturaDDT
	 */
	public Date getDataFatturaDDT() {
		return dataFatturaDDT;
	}

	
	/**
	 * @param dataFatturaDDT the dataFatturaDDT to set
	 */
	public void setDataFatturaDDT(Date dataFatturaDDT) {
		this.dataFatturaDDT = dataFatturaDDT;
	}

	
	/**
	 * @return the uriFatturaDDT
	 */
	public String getUriFatturaDDT() {
		return uriFatturaDDT;
	}

	
	/**
	 * @param uriFatturaDDT the uriFatturaDDT to set
	 */
	public void setUriFatturaDDT(String uriFatturaDDT) {
		this.uriFatturaDDT = uriFatturaDDT;
	}

	
	/**
	 * @return the nomeFatturaDDT
	 */
	public String getNomeFatturaDDT() {
		return nomeFatturaDDT;
	}

	
	/**
	 * @param nomeFatturaDDT the nomeFatturaDDT to set
	 */
	public void setNomeFatturaDDT(String nomeFatturaDDT) {
		this.nomeFatturaDDT = nomeFatturaDDT;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getTipoInvioDefault() {
		return tipoInvioDefault;
	}
	
	public void setTipoInvioDefault(String tipoInvioDefault) {
		this.tipoInvioDefault = tipoInvioDefault;
	}
	
	
}
