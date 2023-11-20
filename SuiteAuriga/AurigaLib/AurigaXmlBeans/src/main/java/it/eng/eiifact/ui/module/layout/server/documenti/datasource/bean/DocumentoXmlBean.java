/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;
import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * Rappresentazione java dei dati estratti dalla store sottoforma di xml
 * @author ottavio passalacqua
 *
 */
public class DocumentoXmlBean {

    @NumeroColonna(numero = "1")
	private String flgUdFolder;
	
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
		
	@NumeroColonna(numero = "88")
	private String nota;
	
	@NumeroColonna(numero = "89")
	private BigDecimal score;

	@NumeroColonna(numero = "96")
	private String tipoRicevutaEmail;
	
	@NumeroColonna(numero = "97")
	private String tipoInvioEmail;


	// Attributi custom

	@NumeroColonna(numero = "101")
	private String numeroFattura;

	@NumeroColonna(numero = "102")
	private String numeroOrdineAcquisto;

	@NumeroColonna(numero = "103")
	private String causaleNotaFattura;
	
	@NumeroColonna(numero = "118")
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
	
    // Altri valori	
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

    @NumeroColonna(numero = "231")
	private String canaleInvio;
    
    @NumeroColonna(numero = "234")
	private String flgStatoLock;
    
	private Boolean emailInviataFlgPEC;
	private Boolean emailInviataFlgPEO;
	private Boolean emailInviataFlgInterOp;
	private String descSiglaFattura;
	private String abilViewFilePrimario;
	
	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
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

	public String getFlgUdFolder() {
		return flgUdFolder;
	}

	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
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

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getStatoDett() {
		return statoDett;
	}

	public void setStatoDett(String statoDett) {
		this.statoDett = statoDett;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getTipoRicevutaEmail() {
		return tipoRicevutaEmail;
	}

	public void setTipoRicevutaEmail(String tipoRicevutaEmail) {
		this.tipoRicevutaEmail = tipoRicevutaEmail;
	}

	public String getNumeroOrdineAcquisto() {
		return numeroOrdineAcquisto;
	}

	public void setNumeroOrdineAcquisto(String numeroOrdineAcquisto) {
		this.numeroOrdineAcquisto = numeroOrdineAcquisto;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
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

	public String getCanaleInvio() {
		return canaleInvio;
	}

	public void setCanaleInvio(String canaleInvio) {
		this.canaleInvio = canaleInvio;
	}

	public String getDescSiglaFattura() {
		return descSiglaFattura;
	}

	public void setDescSiglaFattura(String descSiglaFattura) {
		this.descSiglaFattura = descSiglaFattura;
	}

	public String getNumeroFattura() {
		return numeroFattura;
	}

	public void setNumeroFattura(String numeroFattura) {
		this.numeroFattura = numeroFattura;
	}

	public String getSocieta() {
		return societa;
	}

	public void setSocieta(String societa) {
		this.societa = societa;
	}

	public String getFlgStatoLock() {
		return flgStatoLock;
	}

	public void setFlgStatoLock(String flgStatoLock) {
		this.flgStatoLock = flgStatoLock;
	}
}
