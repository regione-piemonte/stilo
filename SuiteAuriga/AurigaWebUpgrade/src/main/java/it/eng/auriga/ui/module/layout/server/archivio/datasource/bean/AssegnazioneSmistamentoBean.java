/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class AssegnazioneSmistamentoBean extends OperazioneMassivaArchivioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String flgUdFolder;
	private String idInvio;
	private List<AssegnazioneBean> listaAssegnazioni;
	private String motivoInvio;
	private BigDecimal livelloPriorita;
	private Boolean flgInviaFascicolo;
	private Boolean flgInviaDocCollegati;
	private Boolean flgMantieniCopiaUd;
	private String messaggioInvio;
	private Boolean flgPresaInCarico;
    private Boolean flgMancataPresaInCarico;
    private Integer giorniTrascorsi;
    private Boolean flgMandaNotificaMail;
    private Boolean flgForceInvio;
    
	public String getFlgUdFolder() {
		return flgUdFolder;
	}
	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}
	public String getIdInvio() {
		return idInvio;
	}
	public void setIdInvio(String idInvio) {
		this.idInvio = idInvio;
	}
	public List<AssegnazioneBean> getListaAssegnazioni() {
		return listaAssegnazioni;
	}
	public void setListaAssegnazioni(List<AssegnazioneBean> listaAssegnazioni) {
		this.listaAssegnazioni = listaAssegnazioni;
	}
	public String getMotivoInvio() {
		return motivoInvio;
	}
	public void setMotivoInvio(String motivoInvio) {
		this.motivoInvio = motivoInvio;
	}
	public BigDecimal getLivelloPriorita() {
		return livelloPriorita;
	}
	public void setLivelloPriorita(BigDecimal livelloPriorita) {
		this.livelloPriorita = livelloPriorita;
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
	public String getMessaggioInvio() {
		return messaggioInvio;
	}
	public void setMessaggioInvio(String messaggioInvio) {
		this.messaggioInvio = messaggioInvio;
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
	public Boolean getFlgMandaNotificaMail() {
		return flgMandaNotificaMail;
	}
	public void setFlgMandaNotificaMail(Boolean flgMandaNotificaMail) {
		this.flgMandaNotificaMail = flgMandaNotificaMail;
	}
	public Boolean getFlgForceInvio() {
		return flgForceInvio;
	}
	public void setFlgForceInvio(Boolean flgForceInvio) {
		this.flgForceInvio = flgForceInvio;
	}	
}