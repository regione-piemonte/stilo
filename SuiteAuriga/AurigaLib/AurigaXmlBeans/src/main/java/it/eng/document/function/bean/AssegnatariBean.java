/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class AssegnatariBean implements Serializable{

	@NumeroColonna(numero = "1")
	private TipoAssegnatario tipo;
	@NumeroColonna(numero = "2")
	private String idSettato;
	@NumeroColonna(numero = "5")
	private String codRapido;
	@NumeroColonna(numero = "17")
	private String ruolo;	
	@NumeroColonna(numero = "19")
	private String permessiAccesso;
	@NumeroColonna(numero = "20")
	private Flag feedback;
	@NumeroColonna(numero = "21")
	private Integer numeroGiorniFeedback;
	@NumeroColonna(numero = "22")
	private String motivoInvio;
	@NumeroColonna(numero = "23")
	private String livelloPriorita;
	@NumeroColonna(numero = "24")
	private Flag flgInviaFascicolo;
	@NumeroColonna(numero = "25")
	private Flag flgInviaDocCollegati;
	@NumeroColonna(numero = "26")
	private Flag flgMantieniCopiaUd;
	@NumeroColonna(numero = "27")
	private String messaggioInvio;
	@NumeroColonna(numero = "28")
	private String puntoProtocollo;
	@NumeroColonna(numero = "29")
    private Flag flgMandaNotificaMail;
	
	public TipoAssegnatario getTipo() {
		return tipo;
	}
	public void setTipo(TipoAssegnatario tipo) {
		this.tipo = tipo;
	}
	public String getIdSettato() {
		return idSettato;
	}
	public void setIdSettato(String idSettato) {
		this.idSettato = idSettato;
	}
	public String getCodRapido() {
		return codRapido;
	}
	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getPermessiAccesso() {
		return permessiAccesso;
	}
	public void setPermessiAccesso(String permessiAccesso) {
		this.permessiAccesso = permessiAccesso;
	}
	public Flag getFeedback() {
		return feedback;
	}
	public void setFeedback(Flag feedback) {
		this.feedback = feedback;
	}
	public Integer getNumeroGiorniFeedback() {
		return numeroGiorniFeedback;
	}
	public void setNumeroGiorniFeedback(Integer numeroGiorniFeedback) {
		this.numeroGiorniFeedback = numeroGiorniFeedback;
	}
	public String getMotivoInvio() {
		return motivoInvio;
	}
	public void setMotivoInvio(String motivoInvio) {
		this.motivoInvio = motivoInvio;
	}
	public String getLivelloPriorita() {
		return livelloPriorita;
	}
	public void setLivelloPriorita(String livelloPriorita) {
		this.livelloPriorita = livelloPriorita;
	}
	public Flag getFlgInviaFascicolo() {
		return flgInviaFascicolo;
	}
	public void setFlgInviaFascicolo(Flag flgInviaFascicolo) {
		this.flgInviaFascicolo = flgInviaFascicolo;
	}
	public Flag getFlgInviaDocCollegati() {
		return flgInviaDocCollegati;
	}
	public void setFlgInviaDocCollegati(Flag flgInviaDocCollegati) {
		this.flgInviaDocCollegati = flgInviaDocCollegati;
	}
	public Flag getFlgMantieniCopiaUd() {
		return flgMantieniCopiaUd;
	}
	public void setFlgMantieniCopiaUd(Flag flgMantieniCopiaUd) {
		this.flgMantieniCopiaUd = flgMantieniCopiaUd;
	}
	public String getMessaggioInvio() {
		return messaggioInvio;
	}
	public void setMessaggioInvio(String messaggioInvio) {
		this.messaggioInvio = messaggioInvio;
	}
	public String getPuntoProtocollo() {
		return puntoProtocollo;
	}
	public void setPuntoProtocollo(String puntoProtocollo) {
		this.puntoProtocollo = puntoProtocollo;
	}
	public Flag getFlgMandaNotificaMail() {
		return flgMandaNotificaMail;
	}
	public void setFlgMandaNotificaMail(Flag flgMandaNotificaMail) {
		this.flgMandaNotificaMail = flgMandaNotificaMail;
	}
	
}