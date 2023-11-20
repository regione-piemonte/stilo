/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class OpzioniInvioCCBean {
	
	private String motivoInvio;
	private String messaggioInvio;
	private String livelloPriorita;
	private Boolean flgPresaInCarico;
    private Boolean flgMancataPresaInCarico;
    private Integer giorniTrascorsi;
	
	// SOLO PER ASSEGNAZIONE UD
    private Boolean flgInviaFascicolo;
	private Boolean flgInviaDocCollegati;
	private Boolean flgMandaNotificaMail;
	   	
    public String getMotivoInvio() {
		return motivoInvio;
	}
	public void setMotivoInvio(String motivoInvio) {
		this.motivoInvio = motivoInvio;
	}
	public String getMessaggioInvio() {
		return messaggioInvio;
	}
	public void setMessaggioInvio(String messaggioInvio) {
		this.messaggioInvio = messaggioInvio;
	}
	public String getLivelloPriorita() {
		return livelloPriorita;
	}
	public void setLivelloPriorita(String livelloPriorita) {
		this.livelloPriorita = livelloPriorita;
	}
	public Boolean getFlgPresaInCarico() {
		return flgPresaInCarico;
	}
	public void setFlgPresaInCarico(Boolean flgPresaInCarico) {
		this.flgPresaInCarico = flgPresaInCarico;
	}
	public Boolean getFlgMancataPresaInCarico() {
		return flgMancataPresaInCarico;
	}
	public void setFlgMancataPresaInCarico(Boolean flgMancataPresaInCarico) {
		this.flgMancataPresaInCarico = flgMancataPresaInCarico;
	}
	public Integer getGiorniTrascorsi() {
		return giorniTrascorsi;
	}
	public void setGiorniTrascorsi(Integer giorniTrascorsi) {
		this.giorniTrascorsi = giorniTrascorsi;
	}
	public Boolean getFlgInviaFascicolo() {
		return flgInviaFascicolo;
	}
	public void setFlgInviaFascicolo(Boolean flgInviaFascicolo) {
		this.flgInviaFascicolo = flgInviaFascicolo;
	}
	public Boolean getFlgInviaDocCollegati() {
		return flgInviaDocCollegati;
	}
	public void setFlgInviaDocCollegati(Boolean flgInviaDocCollegati) {
		this.flgInviaDocCollegati = flgInviaDocCollegati;
	}
	public Boolean getFlgMandaNotificaMail() {
		return flgMandaNotificaMail;
	}
	public void setFlgMandaNotificaMail(Boolean flgMandaNotificaMail) {
		this.flgMandaNotificaMail = flgMandaNotificaMail;
	}
}