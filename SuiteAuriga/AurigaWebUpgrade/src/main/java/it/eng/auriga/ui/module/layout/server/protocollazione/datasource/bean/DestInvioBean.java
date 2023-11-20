/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class DestInvioBean {

	private String tipo;
	private String codRapido;
	private Boolean codRapidoChanged;
	private String idUo;	
	private String typeNodo;	
	private String statoFirmaResponsabile;
	private String organigramma;
	private String descrizione;
	private String descrizioneEstesa;
	private String gruppo;
	private String preferiti;
	private String gruppoSalvato;
	private String presaInCarico;
	private String messAltPresaInCarico;
	private Boolean flgDisable;
		
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getCodRapido() {
		return codRapido;
	}
	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}
	public Boolean getCodRapidoChanged() {
		return codRapidoChanged;
	}
	public void setCodRapidoChanged(Boolean codRapidoChanged) {
		this.codRapidoChanged = codRapidoChanged;
	}
	public String getIdUo() {
		return idUo;
	}
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	public String getTypeNodo() {
		return typeNodo;
	}
	public void setTypeNodo(String typeNodo) {
		this.typeNodo = typeNodo;
	}
	public String getStatoFirmaResponsabile() {
		return statoFirmaResponsabile;
	}
	public void setStatoFirmaResponsabile(String statoFirmaResponsabile) {
		this.statoFirmaResponsabile = statoFirmaResponsabile;
	}
	public String getOrganigramma() {
		return organigramma;
	}
	public void setOrganigramma(String organigramma) {
		this.organigramma = organigramma;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}
	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}
	public String getGruppo() {
		return gruppo;
	}
	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}	
	public String getPreferiti() {
		return preferiti;
	}
	public void setPreferiti(String preferiti) {
		this.preferiti = preferiti;
	}
	public String getGruppoSalvato() {
		return gruppoSalvato;
	}
	public void setGruppoSalvato(String gruppoSalvato) {
		this.gruppoSalvato = gruppoSalvato;
	}
	public String getPresaInCarico() {
		return presaInCarico;
	}
	public void setPresaInCarico(String presaInCarico) {
		this.presaInCarico = presaInCarico;
	}
	public String getMessAltPresaInCarico() {
		return messAltPresaInCarico;
	}
	public void setMessAltPresaInCarico(String messAltPresaInCarico) {
		this.messAltPresaInCarico = messAltPresaInCarico;
	}
	public Boolean getFlgDisable() {
		return flgDisable;
	}
	public void setFlgDisable(Boolean flgDisable) {
		this.flgDisable = flgDisable;
	}	
}