/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;

import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.StampaFileBean;

public class StampaListaAccompagnatoriaProtocollazioniBean extends StampaFileBean {
	
	private Boolean flgProtSoloMie;
	private Boolean flgProtUtenteSelezionato;
	private String utenteProtocollazione;
	private Boolean flgProtDiTutti;
	private Date dtInizio;
	private Date dtFine;
	private Boolean flgDestinatariTutti;
	private Boolean flgDestinatariSelezionati;
	private ArrayList<String>listaDestinatari;
//	private String listaIdUO;
	
	private Boolean inError;
	private Boolean foglioGenerato;
	private String errorMessage;
	
	public Boolean getFlgProtSoloMie() {
		return flgProtSoloMie;
	}
	public void setFlgProtSoloMie(Boolean flgProtSoloMie) {
		this.flgProtSoloMie = flgProtSoloMie;
	}
	public Boolean getFlgProtUtenteSelezionato() {
		return flgProtUtenteSelezionato;
	}
	public void setFlgProtUtenteSelezionato(Boolean flgProtUtenteSelezionato) {
		this.flgProtUtenteSelezionato = flgProtUtenteSelezionato;
	}
	public String getUtenteProtocollazione() {
		return utenteProtocollazione;
	}
	public void setUtenteProtocollazione(String utenteProtocollazione) {
		this.utenteProtocollazione = utenteProtocollazione;
	}
	public Boolean getFlgProtDiTutti() {
		return flgProtDiTutti;
	}
	public void setFlgProtDiTutti(Boolean flgProtDiTutti) {
		this.flgProtDiTutti = flgProtDiTutti;
	}
	public Date getDtInizio() {
		return dtInizio;
	}
	public void setDtInizio(Date dtInizio) {
		this.dtInizio = dtInizio;
	}
	public Date getDtFine() {
		return dtFine;
	}
	public void setDtFine(Date dtFine) {
		this.dtFine = dtFine;
	}
	public Boolean getFlgDestinatariTutti() {
		return flgDestinatariTutti;
	}
	public void setFlgDestinatariTutti(Boolean flgDestinatariTutti) {
		this.flgDestinatariTutti = flgDestinatariTutti;
	}
	public Boolean getFlgDestinatariSelezionati() {
		return flgDestinatariSelezionati;
	}
	public void setFlgDestinatariSelezionati(Boolean flgDestinatariSelezionati) {
		this.flgDestinatariSelezionati = flgDestinatariSelezionati;
	}
	public ArrayList<String> getListaDestinatari() {
		return listaDestinatari;
	}
	public void setListaDestinatari(ArrayList<String> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}
//	public String getListaIdUO() {
//		return listaIdUO;
//	}
//	public void setListaIdUO(String listaIdUO) {
//		this.listaIdUO = listaIdUO;
//	}
	public Boolean getInError() {
		return inError;
	}
	public void setInError(Boolean inError) {
		this.inError = inError;
	}
	public Boolean getFoglioGenerato() {
		return foglioGenerato;
	}
	public void setFoglioGenerato(Boolean foglioGenerato) {
		this.foglioGenerato = foglioGenerato;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
