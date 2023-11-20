/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class BillingAccountBean {
	
	//Usato come chiave per il listgrid
	private int progressivo;
	
	@NumeroColonna(numero = "1")
	private String billingAccount;  
	
	@NumeroColonna(numero = "2")
	private String ipa;               
	
	@NumeroColonna(numero = "3")
	private String odaNumeroContratto;            
	
	@NumeroColonna(numero = "4")
	private String odaCig;                        
	
	@NumeroColonna(numero = "5")
	private String odaCup;                     
	
	@NumeroColonna(numero = "6")
	private String rifAmministrativo;          
	
	@NumeroColonna(numero = "7")
	private String posizioneFinanziaria;       
	
	@NumeroColonna(numero = "8")
	private String annoPosizioneFinanziaria;   
	
	@NumeroColonna(numero = "9")
	private String odaNumeroItem;                 
	
	@NumeroColonna(numero = "10")
	private String odaCCC;                 
	
	@NumeroColonna(numero = "11")
	private String coNumeroContratto;
	
	@NumeroColonna(numero = "12")
	private String coNumeroItem;
	
	@NumeroColonna(numero = "13")
	private String coCCC;
	
	@NumeroColonna(numero = "14")
	private String coCup;
	
	@NumeroColonna(numero = "15")
	private String coCig;

	@NumeroColonna(numero = "16")
	private String emailPecDestinatario;

	
	public int getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
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

	public String getOdaNumeroContratto() {
		return odaNumeroContratto;
	}

	public void setOdaNumeroContratto(String odaNumeroContratto) {
		this.odaNumeroContratto = odaNumeroContratto;
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

	public String getRifAmministrativo() {
		return rifAmministrativo;
	}

	public void setRifAmministrativo(String rifAmministrativo) {
		this.rifAmministrativo = rifAmministrativo;
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

	public String getOdaNumeroItem() {
		return odaNumeroItem;
	}

	public void setOdaNumeroItem(String odaNumeroItem) {
		this.odaNumeroItem = odaNumeroItem;
	}

	public String getOdaCCC() {
		return odaCCC;
	}

	public void setOdaCCC(String odaCCC) {
		this.odaCCC = odaCCC;
	}

	public String getCoNumeroContratto() {
		return coNumeroContratto;
	}

	public void setCoNumeroContratto(String coNumeroContratto) {
		this.coNumeroContratto = coNumeroContratto;
	}

	public String getCoNumeroItem() {
		return coNumeroItem;
	}

	public void setCoNumeroItem(String coNumeroItem) {
		this.coNumeroItem = coNumeroItem;
	}

	public String getCoCCC() {
		return coCCC;
	}

	public void setCoCCC(String coCCC) {
		this.coCCC = coCCC;
	}

	public String getCoCup() {
		return coCup;
	}

	public void setCoCup(String coCup) {
		this.coCup = coCup;
	}

	public String getCoCig() {
		return coCig;
	}

	public void setCoCig(String coCig) {
		this.coCig = coCig;
	}

	public String getEmailPecDestinatario() {
		return emailPecDestinatario;
	}

	public void setEmailPecDestinatario(String emailPecDestinatario) {
		this.emailPecDestinatario = emailPecDestinatario;
	}

	
	
	
	
	
}
