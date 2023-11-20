/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class ACLBean {
	
	private String tipoDestinatario;
	private String codiceUo;
	private String denominazione;
	private String denominazioneEstesa;
	private String tipoOrganigramma;		
	private String organigramma;		
	private String idOrganigramma;		
	private String codiceRapido;
	private String idUtente;		
	private String idGruppo;
	private String idGruppoInterno;
	private String idGruppoEsterno;
	private String opzioniAccesso;
	private Boolean flgEreditata;
	private Boolean flgAssegnatario;
	private Boolean flgInvioPerConoscenza;
	
	public String getTipoDestinatario() {
		return tipoDestinatario;
	}
	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	public String getCodiceRapido() {
		return codiceRapido;
	}
	public void setCodiceRapido(String codiceRapido) {
		this.codiceRapido = codiceRapido;
	}
	public String getIdOrganigramma() {
		return idOrganigramma;
	}
	public void setIdOrganigramma(String idOrganigramma) {
		this.idOrganigramma = idOrganigramma;
	}
	public String getIdGruppoInterno() {
		return idGruppoInterno;
	}
	public void setIdGruppoInterno(String idGruppoInterno) {
		this.idGruppoInterno = idGruppoInterno;
	}
	public Boolean getFlgEreditata() {
		return flgEreditata;
	}
	public void setFlgEreditata(Boolean flgEreditata) {
		this.flgEreditata = flgEreditata;
	}
	public String getOpzioniAccesso() {
		return opzioniAccesso;
	}
	public void setOpzioniAccesso(String opzioniAccesso) {
		this.opzioniAccesso = opzioniAccesso;
	}	
	public String getIdGruppo() {
		return idGruppo;
	}
	public void setIdGruppo(String idGruppo) {
		this.idGruppo = idGruppo;
	}
	public String getIdGruppoEsterno() {
		return idGruppoEsterno;
	}
	public void setIdGruppoEsterno(String idGruppoEsterno) {
		this.idGruppoEsterno = idGruppoEsterno;
	}
	public String getTipoOrganigramma() {
		return tipoOrganigramma;
	}
	public void setTipoOrganigramma(String tipoOrganigramma) {
		this.tipoOrganigramma = tipoOrganigramma;
	}
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public String getCodiceUo() {
		return codiceUo;
	}
	public void setCodiceUo(String codiceUo) {
		this.codiceUo = codiceUo;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getDenominazioneEstesa() {
		return denominazioneEstesa;
	}
	public void setDenominazioneEstesa(String denominazioneEstesa) {
		this.denominazioneEstesa = denominazioneEstesa;
	}
	public Boolean getFlgAssegnatario() {
		return flgAssegnatario;
	}
	public void setFlgAssegnatario(Boolean flgAssegnatario) {
		this.flgAssegnatario = flgAssegnatario;
	}
	public Boolean getFlgInvioPerConoscenza() {
		return flgInvioPerConoscenza;
	}
	public void setFlgInvioPerConoscenza(Boolean flgInvioPerConoscenza) {
		this.flgInvioPerConoscenza = flgInvioPerConoscenza;
	}
	public String getOrganigramma() {
		return organigramma;
	}
	public void setOrganigramma(String organigramma) {
		this.organigramma = organigramma;
	}
	
}
