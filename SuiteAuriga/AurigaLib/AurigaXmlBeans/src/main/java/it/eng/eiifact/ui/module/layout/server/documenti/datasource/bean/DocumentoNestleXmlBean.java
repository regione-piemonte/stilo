/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
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
public class DocumentoNestleXmlBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NumeroColonna(numero = "1")
	private String flgUdFolder;
	
	@NumeroColonna(numero = "2")
	private BigDecimal idUd;

	@NumeroColonna(numero = "3")
	private String nomeUd;
	
	@NumeroColonna(numero = "14")
	private String siglaDocumento;
	
	@NumeroColonna(numero = "32")
	private String tipoDocumento;
	
	@NumeroColonna(numero = "33")
	private BigDecimal idDocPrimario;
	
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
	private String vettore;

	@NumeroColonna(numero = "102")
	private String nroPagine;

	@NumeroColonna(numero = "103")
	private String ldv;
	
	@NumeroColonna(numero = "104")
	private String nroOda;
	
	@NumeroColonna(numero = "105")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataEvasione;
	
	@NumeroColonna(numero = "106")
	private String nazione;
	
	
    // Altri valori	
	@NumeroColonna(numero = "203")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataDocumento;

	
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
    
    @NumeroColonna(numero = "253")
   	private String salesOrganization;
    
    @NumeroColonna(numero = "254")
   	private String distributionChannel;
    
    @NumeroColonna(numero = "255")
   	private String lotto;
    
    @NumeroColonna(numero = "256")
   	private String dimensione;
    
    @NumeroColonna(numero = "257")
	@TipoData(tipo=Tipo.DATA)
   	private Date tsUltimoEvento;
    
    
    private String numeroDocumento;
    
	private Boolean emailInviataFlgPEC;
	private Boolean emailInviataFlgPEO;
	private Boolean emailInviataFlgInterOp;
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

	

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
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

	
	public String getFlgStatoLock() {
		return flgStatoLock;
	}

	public void setFlgStatoLock(String flgStatoLock) {
		this.flgStatoLock = flgStatoLock;
	}

	
	
	
	public String getSalesOrganization() {
		return salesOrganization;
	}

	
	public void setSalesOrganization(String salesOrganization) {
		this.salesOrganization = salesOrganization;
	}

	
	public String getDistributionChannel() {
		return distributionChannel;
	}

	
	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	
	public String getLotto() {
		return lotto;
	}

	
	public void setLotto(String lotto) {
		this.lotto = lotto;
	}

	
	public String getDimensione() {
		return dimensione;
	}

	
	public void setDimensione(String dimensione) {
		this.dimensione = dimensione;
	}


	public String getVettore() {
		return vettore;
	}

	public void setVettore(String vettore) {
		this.vettore = vettore;
	}

	public String getNroPagine() {
		return nroPagine;
	}

	public void setNroPagine(String nroPagine) {
		this.nroPagine = nroPagine;
	}

	public String getLdv() {
		return ldv;
	}

	public void setLdv(String ldv) {
		this.ldv = ldv;
	}

	public String getNroOda() {
		return nroOda;
	}

	public void setNroOda(String nroOda) {
		this.nroOda = nroOda;
	}



	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getSiglaDocumento() {
		return siglaDocumento;
	}

	public void setSiglaDocumento(String siglaDocumento) {
		this.siglaDocumento = siglaDocumento;
	}

	public Date getTsUltimoEvento() {
		return tsUltimoEvento;
	}

	public void setTsUltimoEvento(Date tsUltimoEvento) {
		this.tsUltimoEvento = tsUltimoEvento;
	}

	public Date getDataEvasione() {
		return dataEvasione;
	}

	public void setDataEvasione(Date dataEvasione) {
		this.dataEvasione = dataEvasione;
	}

	public BigDecimal getIdDocPrimario() {
		return idDocPrimario;
	}

	public void setIdDocPrimario(BigDecimal idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}
}