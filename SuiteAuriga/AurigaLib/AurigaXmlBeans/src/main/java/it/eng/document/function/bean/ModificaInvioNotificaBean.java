/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModificaInvioNotificaBean implements Serializable{

	private String flgInvioNotifica;
	private String idInvioNotifica;
	private String flgUdFolder;
	private String idUdFolder;		
	private String motivoAnnullamento;	
	private String xmlDestinatari;	
	private String motivoInvio;
	private String messaggioInvio;
	private BigDecimal livelloPriorita;
	private Date tsDecorrenzaAssegnaz;
	private Boolean flgInviaFascicolo;
	private Boolean flgInviaDocCollegati;
	private Boolean flgMantieniCopiaUd;
	
	public String getFlgUdFolder() {
		return flgUdFolder;
	}
	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}
	public String getIdUdFolder() {
		return idUdFolder;
	}
	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
	}
	public String getFlgInvioNotifica() {
		return flgInvioNotifica;
	}
	public void setFlgInvioNotifica(String flgInvioNotifica) {
		this.flgInvioNotifica = flgInvioNotifica;
	}
	public String getIdInvioNotifica() {
		return idInvioNotifica;
	}
	public void setIdInvioNotifica(String idInvioNotifica) {
		this.idInvioNotifica = idInvioNotifica;
	}
	public String getMotivoAnnullamento() {
		return motivoAnnullamento;
	}
	public void setMotivoAnnullamento(String motivoAnnullamento) {
		this.motivoAnnullamento = motivoAnnullamento;
	}
	public String getXmlDestinatari() {
		return xmlDestinatari;
	}
	public void setXmlDestinatari(String xmlDestinatari) {
		this.xmlDestinatari = xmlDestinatari;
	}
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
	public BigDecimal getLivelloPriorita() {
		return livelloPriorita;
	}
	public void setLivelloPriorita(BigDecimal livelloPriorita) {
		this.livelloPriorita = livelloPriorita;
	}
	public Date getTsDecorrenzaAssegnaz() {
		return tsDecorrenzaAssegnaz;
	}
	public void setTsDecorrenzaAssegnaz(Date tsDecorrenzaAssegnaz) {
		this.tsDecorrenzaAssegnaz = tsDecorrenzaAssegnaz;
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
	public Boolean getFlgMantieniCopiaUd() {
		return flgMantieniCopiaUd;
	}
	public void setFlgMantieniCopiaUd(Boolean flgMantieniCopiaUd) {
		this.flgMantieniCopiaUd = flgMantieniCopiaUd;
	}		
	
}
