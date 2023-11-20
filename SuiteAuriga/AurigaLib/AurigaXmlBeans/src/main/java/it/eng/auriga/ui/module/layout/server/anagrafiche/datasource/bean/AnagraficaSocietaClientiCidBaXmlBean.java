/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class AnagraficaSocietaClientiCidBaXmlBean {

	@NumeroColonna(numero = "1")
	private String idSoggetto;
	
	@NumeroColonna(numero = "2")
	private String idCliente;
	
	@NumeroColonna(numero = "3")
	private String denominazione;
	
	@NumeroColonna(numero = "4")
	private String cid;
	
	@NumeroColonna(numero = "5")
	private String billingAccount;
	
	@NumeroColonna(numero = "6")
	private String ipa;
	
	@NumeroColonna(numero = "7")
	private String odaCig;
	
	@NumeroColonna(numero = "8")
	private String odaCup;
	
	@NumeroColonna(numero = "9")
	private String odaNrContratto;
	
	@NumeroColonna(numero = "10")
	private String flgTipoIdFiscale;
	
	@NumeroColonna(numero = "11")
	private String codiceFiscale;
	
	@NumeroColonna(numero = "12")
	private String partitaIva;
	
	@NumeroColonna(numero = "13")
	private String codIndPa;
	
	@NumeroColonna(numero = "14")
	private String odaRifAmmInps;
	
	@NumeroColonna(numero = "15")
	private String posizioneFinanziaria;
	
	@NumeroColonna(numero = "16")
	private String annoPosizioneFinanziaria;

	@NumeroColonna(numero = "17")
	private String codRe;

	@NumeroColonna(numero = "18")
	private String rendRe;

	
	@NumeroColonna(numero = "19")
	private String esigibilitaIva;
	
	@NumeroColonna(numero = "20")
	private Boolean flgSegnoImporti;
	
	@NumeroColonna(numero = "21")
	private String societa;
	
	@NumeroColonna(numero = "22")
	private String gruppoDiRiferimento;
	
	@NumeroColonna(numero = "23")
	private String emailPecCliente;
	
	@NumeroColonna(numero = "24")
	private String odaNrItemCliente;
	
	@NumeroColonna(numero = "25")
	private String odaCCCCliente;
	
	@NumeroColonna(numero = "26")
	private String coNrContrattoCliente;
	
	@NumeroColonna(numero = "27")
	private String coNrItemCliente;
	
	@NumeroColonna(numero = "28")
	private String coCCCCliente;
	
	@NumeroColonna(numero = "29")
	private String coCUPCliente;
	
	@NumeroColonna(numero = "30")
	private String coCIGCliente;

	public String getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getBillingAccount() {
		return billingAccount;
	}

	public void setBillingAccount(String billingAccount) {
		this.billingAccount = billingAccount;
	}

	public String getIpa() {
		return ipa;
	}

	public void setIpa(String ipa) {
		this.ipa = ipa;
	}

	public String getOdaCig() {
		return odaCig;
	}

	public void setOdaCig(String odaCig) {
		this.odaCig = odaCig;
	}

	public String getOdaCup() {
		return odaCup;
	}

	public void setOdaCup(String odaCup) {
		this.odaCup = odaCup;
	}

	public String getOdaNrContratto() {
		return odaNrContratto;
	}

	public void setOdaNrContratto(String odaNrContratto) {
		this.odaNrContratto = odaNrContratto;
	}

	public String getFlgTipoIdFiscale() {
		return flgTipoIdFiscale;
	}

	public void setFlgTipoIdFiscale(String flgTipoIdFiscale) {
		this.flgTipoIdFiscale = flgTipoIdFiscale;
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

	public String getOdaRifAmmInps() {
		return odaRifAmmInps;
	}

	public void setOdaRifAmmInps(String odaRifAmmInps) {
		this.odaRifAmmInps = odaRifAmmInps;
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

	public String getSocieta() {
		return societa;
	}

	public void setSocieta(String societa) {
		this.societa = societa;
	}

	public String getGruppoDiRiferimento() {
		return gruppoDiRiferimento;
	}

	public void setGruppoDiRiferimento(String gruppoDiRiferimento) {
		this.gruppoDiRiferimento = gruppoDiRiferimento;
	}

	public String getCodIndPa() {
		return codIndPa;
	}

	public void setCodIndPa(String codIndPa) {
		this.codIndPa = codIndPa;
	}

	public String getCodRe() {
		return codRe;
	}

	public void setCodRe(String codRe) {
		this.codRe = codRe;
	}

	public String getRendRe() {
		return rendRe;
	}

	public void setRendRe(String rendRe) {
		this.rendRe = rendRe;
	}

	public String getEmailPecCliente() {
		return emailPecCliente;
	}

	public void setEmailPecCliente(String emailPecCliente) {
		this.emailPecCliente = emailPecCliente;
	}

	public String getOdaNrItemCliente() {
		return odaNrItemCliente;
	}

	public void setOdaNrItemCliente(String odaNrItemCliente) {
		this.odaNrItemCliente = odaNrItemCliente;
	}

	public String getOdaCCCCliente() {
		return odaCCCCliente;
	}

	public void setOdaCCCCliente(String odaCCCCliente) {
		this.odaCCCCliente = odaCCCCliente;
	}

	public String getCoNrContrattoCliente() {
		return coNrContrattoCliente;
	}

	public void setCoNrContrattoCliente(String coNrContrattoCliente) {
		this.coNrContrattoCliente = coNrContrattoCliente;
	}

	public String getCoNrItemCliente() {
		return coNrItemCliente;
	}

	public void setCoNrItemCliente(String coNrItemCliente) {
		this.coNrItemCliente = coNrItemCliente;
	}

	public String getCoCCCCliente() {
		return coCCCCliente;
	}

	public void setCoCCCCliente(String coCCCCliente) {
		this.coCCCCliente = coCCCCliente;
	}

	public String getCoCUPCliente() {
		return coCUPCliente;
	}

	public void setCoCUPCliente(String coCUPCliente) {
		this.coCUPCliente = coCUPCliente;
	}

	public String getCoCIGCliente() {
		return coCIGCliente;
	}

	public void setCoCIGCliente(String coCIGCliente) {
		this.coCIGCliente = coCIGCliente;
	}

}
