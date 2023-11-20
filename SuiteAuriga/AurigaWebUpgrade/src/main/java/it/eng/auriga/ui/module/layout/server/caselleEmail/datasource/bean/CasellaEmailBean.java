/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.util.Date;
import java.util.List;

public class CasellaEmailBean {
	
	@NumeroColonna(numero = "1")
	private String idCasella;
	
	@NumeroColonna(numero = "2")
	private String indirizzoEmail;
	
	@NumeroColonna(numero = "3")
	private String idSpAoo;
	
	@NumeroColonna(numero = "4")
	private String denominazione;
	
	@NumeroColonna(numero = "5")
	private String tipoAccount;
	
	@NumeroColonna(numero = "6")
	private String username;
	
	@NumeroColonna(numero = "7")
	private String password;
	
	@NumeroColonna(numero = "8")
	private String flgRicezioneAttiva;
	
	@NumeroColonna(numero = "9")
	private String flgInvioAttivo;
	
	@NumeroColonna(numero = "10")
	private String denominazioniUoAssociate;
	
	@NumeroColonna(numero = "11")
	@TipoData(tipo=Tipo.DATA)
	private Date tsUltimoAggPassword;
	
	@NumeroColonna(numero = "12")
	private String uteUltimoAggPassword;
	
	@NumeroColonna(numero = "13")
	private String nroGiorniPasswordAttuale;
	
	@NumeroColonna(numero = "14")
	private String idNodoScarico;
	
	@NumeroColonna(numero = "17")
	private String intervalloScarico;
	
	@NumeroColonna(numero = "18")
	private String nroMaxEmailScaricate;
	
	@NumeroColonna(numero = "19")
	private String nroMaxTentativiScarico;
	
	@NumeroColonna(numero = "20")
	private String nroMaxDestinatari;
	
	@NumeroColonna(numero = "21")
	private String flgCancCopiaDiScaricoAttiva;
	
	private List<ParametroCasellaBean> parametriCasella;
	
	private String nuovoIndirizzoEmail;
	private Boolean flgAttivaScarico;
	
	private List<FruitoreCasellaBean> listaFruitoriCasella;
	private List<UtenteCasellaBean> listaUtentiCasella;
	
	public String getIdCasella() {
		return idCasella;
	}

	public void setIdCasella(String idCasella) {
		this.idCasella = idCasella;
	}

	public String getIndirizzoEmail() {
		return indirizzoEmail;
	}

	public void setIndirizzoEmail(String indirizzoEmail) {
		this.indirizzoEmail = indirizzoEmail;
	}

	public String getIdSpAoo() {
		return idSpAoo;
	}

	public void setIdSpAoo(String idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getTipoAccount() {
		return tipoAccount;
	}

	public void setTipoAccount(String tipoAccount) {
		this.tipoAccount = tipoAccount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFlgRicezioneAttiva() {
		return flgRicezioneAttiva;
	}

	public void setFlgRicezioneAttiva(String flgRicezioneAttiva) {
		this.flgRicezioneAttiva = flgRicezioneAttiva;
	}

	public String getFlgInvioAttivo() {
		return flgInvioAttivo;
	}

	public void setFlgInvioAttivo(String flgInvioAttivo) {
		this.flgInvioAttivo = flgInvioAttivo;
	}

	public String getDenominazioniUoAssociate() {
		return denominazioniUoAssociate;
	}

	public void setDenominazioniUoAssociate(String denominazioniUoAssociate) {
		this.denominazioniUoAssociate = denominazioniUoAssociate;
	}

	public Date getTsUltimoAggPassword() {
		return tsUltimoAggPassword;
	}

	public void setTsUltimoAggPassword(Date tsUltimoAggPassword) {
		this.tsUltimoAggPassword = tsUltimoAggPassword;
	}

	public String getUteUltimoAggPassword() {
		return uteUltimoAggPassword;
	}

	public void setUteUltimoAggPassword(String uteUltimoAggPassword) {
		this.uteUltimoAggPassword = uteUltimoAggPassword;
	}

	public String getNroGiorniPasswordAttuale() {
		return nroGiorniPasswordAttuale;
	}

	public void setNroGiorniPasswordAttuale(String nroGiorniPasswordAttuale) {
		this.nroGiorniPasswordAttuale = nroGiorniPasswordAttuale;
	}

	public String getIdNodoScarico() {
		return idNodoScarico;
	}

	public void setIdNodoScarico(String idNodoScarico) {
		this.idNodoScarico = idNodoScarico;
	}

	public String getIntervalloScarico() {
		return intervalloScarico;
	}

	public void setIntervalloScarico(String intervalloScarico) {
		this.intervalloScarico = intervalloScarico;
	}

	public String getNroMaxEmailScaricate() {
		return nroMaxEmailScaricate;
	}

	public void setNroMaxEmailScaricate(String nroMaxEmailScaricate) {
		this.nroMaxEmailScaricate = nroMaxEmailScaricate;
	}

	public String getNroMaxTentativiScarico() {
		return nroMaxTentativiScarico;
	}

	public void setNroMaxTentativiScarico(String nroMaxTentativiScarico) {
		this.nroMaxTentativiScarico = nroMaxTentativiScarico;
	}

	public String getNroMaxDestinatari() {
		return nroMaxDestinatari;
	}

	public void setNroMaxDestinatari(String nroMaxDestinatari) {
		this.nroMaxDestinatari = nroMaxDestinatari;
	}

	public String getFlgCancCopiaDiScaricoAttiva() {
		return flgCancCopiaDiScaricoAttiva;
	}

	public void setFlgCancCopiaDiScaricoAttiva(String flgCancCopiaDiScaricoAttiva) {
		this.flgCancCopiaDiScaricoAttiva = flgCancCopiaDiScaricoAttiva;
	}

	public List<ParametroCasellaBean> getParametriCasella() {
		return parametriCasella;
	}

	public void setParametriCasella(List<ParametroCasellaBean> parametriCasella) {
		this.parametriCasella = parametriCasella;
	}

	public String getNuovoIndirizzoEmail() {
		return nuovoIndirizzoEmail;
	}

	public void setNuovoIndirizzoEmail(String nuovoIndirizzoEmail) {
		this.nuovoIndirizzoEmail = nuovoIndirizzoEmail;
	}

	public Boolean getFlgAttivaScarico() {
		return flgAttivaScarico;
	}

	public void setFlgAttivaScarico(Boolean flgAttivaScarico) {
		this.flgAttivaScarico = flgAttivaScarico;
	}

	public List<FruitoreCasellaBean> getListaFruitoriCasella() {
		return listaFruitoriCasella;
	}

	public void setListaFruitoriCasella(List<FruitoreCasellaBean> listaFruitoriCasella) {
		this.listaFruitoriCasella = listaFruitoriCasella;
	}

	public List<UtenteCasellaBean> getListaUtentiCasella() {
		return listaUtentiCasella;
	}

	public void setListaUtentiCasella(List<UtenteCasellaBean> listaUtentiCasella) {
		this.listaUtentiCasella = listaUtentiCasella;
	}

	
	
}

