/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
public class AbilitazioniFascicoloOut implements Serializable {
	
	@XmlVariabile(nome="AssegnazioneSmistamento", tipo=TipoVariabile.SEMPLICE)
	private Boolean assegnazioneSmistamento;
	@XmlVariabile(nome="Condivisione", tipo=TipoVariabile.SEMPLICE)
	private Boolean condivisione;
	@XmlVariabile(nome="ModificaDati", tipo=TipoVariabile.SEMPLICE)
	private Boolean modificaDati;
	@XmlVariabile(nome="AvvioIterWF", tipo=TipoVariabile.SEMPLICE)
	private Boolean avvioIterWF;
	@XmlVariabile(nome="AggiuntaDoc", tipo=TipoVariabile.SEMPLICE)
	private Boolean aggiuntaDoc;
	@XmlVariabile(nome="Eliminazione", tipo=TipoVariabile.SEMPLICE)
	private Boolean eliminazione;
	@XmlVariabile(nome="PresaInCarico", tipo=TipoVariabile.SEMPLICE)
	private Boolean presaInCarico;
	@XmlVariabile(nome="Restituzione", tipo=TipoVariabile.SEMPLICE)
	private Boolean restituzione;
	@XmlVariabile(nome="Archiviazione", tipo=TipoVariabile.SEMPLICE)
	private Boolean archiviazione;	
	@XmlVariabile(nome="Chiusura", tipo=TipoVariabile.SEMPLICE)
	private Boolean chiusura;
	@XmlVariabile(nome="Riapertura", tipo=TipoVariabile.SEMPLICE)
	private Boolean riapertura;
	@XmlVariabile(nome="VersamentoStorico", tipo=TipoVariabile.SEMPLICE)
	private Boolean abilVersaInArchivioStoricoFascicolo;
	@XmlVariabile(nome="OsservazioniNotifiche", tipo=TipoVariabile.SEMPLICE)
	private Boolean abilOsservazioniNotifiche; 
	@XmlVariabile(nome="RegistrazionePrelievo", tipo=TipoVariabile.SEMPLICE)
	private Boolean abilRegistrazionePrelievo; 	
	@XmlVariabile(nome="ModificaPrelievo", tipo=TipoVariabile.SEMPLICE)
	private Boolean abilModificaPrelievo; 
	@XmlVariabile(nome="EliminazionePrelievo", tipo=TipoVariabile.SEMPLICE)
	private Boolean abilEliminazionePrelievo; 
	@XmlVariabile(nome="RestituzionePrelievo", tipo=TipoVariabile.SEMPLICE)
	private Boolean abilRestituzionePrelievo; 	
	@XmlVariabile(nome="StampaCopertina", tipo=TipoVariabile.SEMPLICE)
	private Boolean abilStampaCopertina;
	@XmlVariabile(nome="StampaSegnatura", tipo=TipoVariabile.SEMPLICE)
	private Boolean abilStampaSegnatura;
	@XmlVariabile(nome="StampaListaContenuti", tipo=TipoVariabile.SEMPLICE)
	private Boolean abilStampaListaContenuti;	
	@XmlVariabile(nome="ModificaTipologia", tipo=TipoVariabile.SEMPLICE)
	private Boolean modificaTipologia;
	@XmlVariabile(nome="Smista", tipo=TipoVariabile.SEMPLICE)
	private Boolean smista;
	@XmlVariabile(nome="SmistaCC", tipo=TipoVariabile.SEMPLICE)
	private Boolean smistaCC;
	@XmlVariabile(nome="SetVisionato", tipo=TipoVariabile.SEMPLICE)
	private Boolean setVisionato;
	@XmlVariabile(nome="ModAssInviiCC", tipo=TipoVariabile.SEMPLICE)
	private Boolean modAssInviiCC;
	@XmlVariabile(nome="GestioneCollegamentiFolder", tipo=TipoVariabile.SEMPLICE)
	private Boolean gestioneCollegamentiFolder;

	
	public Boolean getModificaTipologia() {
		return modificaTipologia;
	}
	public void setModificaTipologia(Boolean modificaTipologia) {
		this.modificaTipologia = modificaTipologia;
	}
	public Boolean getArchiviazione() {
		return archiviazione;
	}
	public void setArchiviazione(Boolean archiviazione) {
		this.archiviazione = archiviazione;
	}
	public Boolean getAssegnazioneSmistamento() {
		return assegnazioneSmistamento;
	}
	public void setAssegnazioneSmistamento(Boolean assegnazioneSmistamento) {
		this.assegnazioneSmistamento = assegnazioneSmistamento;
	}
	public Boolean getCondivisione() {
		return condivisione;
	}
	public void setCondivisione(Boolean condivisione) {
		this.condivisione = condivisione;
	}
	public Boolean getModificaDati() {
		return modificaDati;
	}
	public void setModificaDati(Boolean modificaDati) {
		this.modificaDati = modificaDati;
	}
	public Boolean getAvvioIterWF() {
		return avvioIterWF;
	}
	public void setAvvioIterWF(Boolean avvioIterWF) {
		this.avvioIterWF = avvioIterWF;
	}
	public Boolean getAggiuntaDoc() {
		return aggiuntaDoc;
	}
	public void setAggiuntaDoc(Boolean aggiuntaDoc) {
		this.aggiuntaDoc = aggiuntaDoc;
	}
	public Boolean getEliminazione() {
		return eliminazione;
	}
	public void setEliminazione(Boolean eliminazione) {
		this.eliminazione = eliminazione;
	}
	public Boolean getPresaInCarico() {
		return presaInCarico;
	}
	public void setPresaInCarico(Boolean presaInCarico) {
		this.presaInCarico = presaInCarico;
	}
	public Boolean getRestituzione() {
		return restituzione;
	}
	public void setRestituzione(Boolean restituzione) {
		this.restituzione = restituzione;
	}
	public Boolean getChiusura() {
		return chiusura;
	}
	public void setChiusura(Boolean chiusura) {
		this.chiusura = chiusura;
	}
	public Boolean getRiapertura() {
		return riapertura;
	}
	public void setRiapertura(Boolean riapertura) {
		this.riapertura = riapertura;
	}
	public Boolean getAbilVersaInArchivioStoricoFascicolo() {
		return abilVersaInArchivioStoricoFascicolo;
	}
	public void setAbilVersaInArchivioStoricoFascicolo(
			Boolean abilVersaInArchivioStoricoFascicolo) {
		this.abilVersaInArchivioStoricoFascicolo = abilVersaInArchivioStoricoFascicolo;
	}
	public Boolean getAbilOsservazioniNotifiche() {
		return abilOsservazioniNotifiche;
	}
	public void setAbilOsservazioniNotifiche(Boolean abilOsservazioniNotifiche) {
		this.abilOsservazioniNotifiche = abilOsservazioniNotifiche;
	}
	
	public Boolean getAbilRegistrazionePrelievo() {
		return abilRegistrazionePrelievo;
	}
	public void setAbilRegistrazionePrelievo(Boolean abilRegistrazionePrelievo) {
		this.abilRegistrazionePrelievo = abilRegistrazionePrelievo;
	}
	public Boolean getAbilModificaPrelievo() {
		return abilModificaPrelievo;
	}
	public void setAbilModificaPrelievo(Boolean abilModificaPrelievo) {
		this.abilModificaPrelievo = abilModificaPrelievo;
	}
	public Boolean getAbilEliminazionePrelievo() {
		return abilEliminazionePrelievo;
	}
	public void setAbilEliminazionePrelievo(Boolean abilEliminazionePrelievo) {
		this.abilEliminazionePrelievo = abilEliminazionePrelievo;
	}
	public Boolean getAbilRestituzionePrelievo() {
		return abilRestituzionePrelievo;
	}
	public void setAbilRestituzionePrelievo(Boolean abilRestituzionePrelievo) {
		this.abilRestituzionePrelievo = abilRestituzionePrelievo;
	}
	public Boolean getAbilStampaCopertina() {
		return abilStampaCopertina;
	}
	public void setAbilStampaCopertina(Boolean abilStampaCopertina) {
		this.abilStampaCopertina = abilStampaCopertina;
	}
	public Boolean getAbilStampaSegnatura() {
		return abilStampaSegnatura;
	}
	public void setAbilStampaSegnatura(Boolean abilStampaSegnatura) {
		this.abilStampaSegnatura = abilStampaSegnatura;
	}
	public Boolean getAbilStampaListaContenuti() {
		return abilStampaListaContenuti;
	}
	public void setAbilStampaListaContenuti(Boolean abilStampaListaContenuti) {
		this.abilStampaListaContenuti = abilStampaListaContenuti;
	}
	public Boolean getSmista() {
		return smista;
	}
	public void setSmista(Boolean smista) {
		this.smista = smista;
	}
	public Boolean getSmistaCC() {
		return smistaCC;
	}
	public void setSmistaCC(Boolean smistaCC) {
		this.smistaCC = smistaCC;
	}
	public Boolean getSetVisionato() {
		return setVisionato;
	}
	public void setSetVisionato(Boolean setVisionato) {
		this.setVisionato = setVisionato;
	}
	public Boolean getModAssInviiCC() {
		return modAssInviiCC;
	}
	public void setModAssInviiCC(Boolean modAssInviiCC) {
		this.modAssInviiCC = modAssInviiCC;
	}
	public Boolean getGestioneCollegamentiFolder() {
		return gestioneCollegamentiFolder;
	}
	public void setGestioneCollegamentiFolder(Boolean gestioneCollegamentiFolder) {
		this.gestioneCollegamentiFolder = gestioneCollegamentiFolder;
	}
}