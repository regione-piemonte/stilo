/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;

public class ScrivaniaTreeNodeBean extends TreeNodeBean {	
	
	private String azione;
	private String parametri;
	private String criteriAvanzati;
	private Boolean flgMultiselezione;
	private String codSezione;
	private Boolean flgContenuti;
	private String nroContenuti;
	private Boolean flgIconaSpecNodo;
	private String idUtenteModPec;
	
	/*
	 * ABILITAZIONI AZIONI MASSIVE 
	 */
	
	private Boolean abilApposizioneFirma;
	private Boolean abilApposizioneFirmaProtocollazione;
	private Boolean abilRifiutoFirma;
	private Boolean abilApposizioneVisto;
	private Boolean abilRifiutoVisto;
	private Boolean abilConfermaPreassegnazione;
	private Boolean abilModificaPreassegnazione;
	private Boolean abilInserimentoInAttoAutorizzAnn;
	private Boolean abilPresaInCarico;
	private Boolean abilClassificazioneFascicolazione;
	private Boolean abilFascicolazione;
	private Boolean abilFolderizzazione;
	private Boolean abilAssegnazione;
	private Boolean abilRestituzione;
	private Boolean abilSmistamento;
	private Boolean abilSmistamentoCC;
	private Boolean abilInvioPerConoscenza;
	private Boolean abilArchiviazione;
	private Boolean abilAnnullamentoArchiviazione;
	private Boolean abilAggiuntaAiPreferiti;
	private Boolean abilRimozioneDaiPreferiti;
	private Boolean abilAssegnazioneRiservatezza;
	private Boolean abilRimozioneRiservatezza;
	private Boolean abilAnnullamentoEliminazione;
	private Boolean abilEliminazione;
	private Boolean abilEliminazioneImgScansione;
	private Boolean abilAssociazioneImgAProtocollo;
	private Boolean abilApposizioneCommenti;
	private Boolean abilStampaEtichetta;
	private Boolean abilDownloadDocZipMultiButton;
	private Boolean abilModificaStatoDocMultiButton;
	private Boolean abilModificaTipologiaMultiButton;
	private Boolean abilChiudiFascicoloMultiButton;
	private Boolean abilRiapriFascicoloMultiButton;
	private Boolean abilSegnaComeVisionatoMultiButton;
	
	private Boolean abilRichiesteAccessoAttiInvioInApprovazione;
	private Boolean abilRichiesteAccessoAttiApprovazione;
	private Boolean abilRichiesteAccessoAttiInvioEsitoVerificaArchivio;
	private Boolean abilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda;
	private Boolean abilRichiesteAccessoAttiRegistrazioneAppuntamento;
	private Boolean abilRichiesteAccessoAttiAnnullamentoAppuntamento;
	private Boolean abilRichiesteAccessoAttiRegistrazionePrelievo;
	private Boolean abilRichiesteAccessoAttiRegistrazioneRestituzione;
	private Boolean abilRichiesteAccessoAttiAnullamentoRichiesta;
	private Boolean abilRichiesteAccessoAttiStampaFoglioPrelievo;
	private Boolean abilRichiesteAccessoAttiEliminazioneRichiesta;
		
	private Boolean abilEmailChiusuraLavorazione;
	private Boolean abilEmailRiaperturaLavorazione;
	private Boolean abilEmailAssegnazione;
	private Boolean abilEmailAnnullamentoAssegnazione;
	private Boolean abilEmailInoltro;
	private Boolean abilEmailPresaInCarico;
	private Boolean abilEmailMessaInCarico;
	private Boolean abilEmailInvioInApprovazione;
	private Boolean abilEmailRilascio;
	private Boolean abilEmailEliminazione;
	private Boolean abilEmailApposizioneTagCommenti;
	

	public String getAzione() {
		return azione;
	}
	public void setAzione(String azione) {
		this.azione = azione;
	}
	public String getParametri() {
		return parametri;
	}
	public void setParametri(String parametri) {
		this.parametri = parametri;
	}
	public String getCriteriAvanzati() {
		return criteriAvanzati;
	}
	public void setCriteriAvanzati(String criteriAvanzati) {
		this.criteriAvanzati = criteriAvanzati;
	}
	public Boolean getFlgMultiselezione() {
		return flgMultiselezione;
	}
	public void setFlgMultiselezione(Boolean flgMultiselezione) {
		this.flgMultiselezione = flgMultiselezione;
	}
	public String getCodSezione() {
		return codSezione;
	}
	public void setCodSezione(String codSezione) {
		this.codSezione = codSezione;
	}
	public Boolean getFlgContenuti() {
		return flgContenuti;
	}
	public void setFlgContenuti(Boolean flgContenuti) {
		this.flgContenuti = flgContenuti;
	}
	public String getNroContenuti() {
		return nroContenuti;
	}
	public void setNroContenuti(String nroContenuti) {
		this.nroContenuti = nroContenuti;
	}
	public Boolean getFlgIconaSpecNodo() {
		return flgIconaSpecNodo;
	}
	public void setFlgIconaSpecNodo(Boolean flgIconaSpecNodo) {
		this.flgIconaSpecNodo = flgIconaSpecNodo;
	}
	public String getIdUtenteModPec() {
		return idUtenteModPec;
	}
	public void setIdUtenteModPec(String idUtenteModPec) {
		this.idUtenteModPec = idUtenteModPec;
	}	
	public Boolean getAbilApposizioneFirma() {
		return abilApposizioneFirma;
	}
	public void setAbilApposizioneFirma(Boolean abilApposizioneFirma) {
		this.abilApposizioneFirma = abilApposizioneFirma;
	}
	
	public Boolean getAbilApposizioneFirmaProtocollazione() {
		return abilApposizioneFirmaProtocollazione;
	}
	
	public void setAbilApposizioneFirmaProtocollazione(Boolean abilApposizioneFirmaProtocollazione) {
		this.abilApposizioneFirmaProtocollazione = abilApposizioneFirmaProtocollazione;
	}
	public Boolean getAbilRifiutoFirma() {
		return abilRifiutoFirma;
	}
	public void setAbilRifiutoFirma(Boolean abilRifiutoFirma) {
		this.abilRifiutoFirma = abilRifiutoFirma;
	}
	public Boolean getAbilApposizioneVisto() {
		return abilApposizioneVisto;
	}
	public void setAbilApposizioneVisto(Boolean abilApposizioneVisto) {
		this.abilApposizioneVisto = abilApposizioneVisto;
	}
	public Boolean getAbilRifiutoVisto() {
		return abilRifiutoVisto;
	}
	public void setAbilRifiutoVisto(Boolean abilRifiutoVisto) {
		this.abilRifiutoVisto = abilRifiutoVisto;
	}
	public Boolean getAbilConfermaPreassegnazione() {
		return abilConfermaPreassegnazione;
	}
	public void setAbilConfermaPreassegnazione(Boolean abilConfermaPreassegnazione) {
		this.abilConfermaPreassegnazione = abilConfermaPreassegnazione;
	}
	public Boolean getAbilModificaPreassegnazione() {
		return abilModificaPreassegnazione;
	}
	public void setAbilModificaPreassegnazione(Boolean abilModificaPreassegnazione) {
		this.abilModificaPreassegnazione = abilModificaPreassegnazione;
	}
	public Boolean getAbilInserimentoInAttoAutorizzAnn() {
		return abilInserimentoInAttoAutorizzAnn;
	}
	public void setAbilInserimentoInAttoAutorizzAnn(Boolean abilInserimentoInAttoAutorizzAnn) {
		this.abilInserimentoInAttoAutorizzAnn = abilInserimentoInAttoAutorizzAnn;
	}
	public Boolean getAbilPresaInCarico() {
		return abilPresaInCarico;
	}
	public void setAbilPresaInCarico(Boolean abilPresaInCarico) {
		this.abilPresaInCarico = abilPresaInCarico;
	}
	public Boolean getAbilClassificazioneFascicolazione() {
		return abilClassificazioneFascicolazione;
	}
	public void setAbilClassificazioneFascicolazione(Boolean abilClassificazioneFascicolazione) {
		this.abilClassificazioneFascicolazione = abilClassificazioneFascicolazione;
	}
	public Boolean getAbilFascicolazione() {
		return abilFascicolazione;
	}
	public void setAbilFascicolazione(Boolean abilFascicolazione) {
		this.abilFascicolazione = abilFascicolazione;
	}
	public Boolean getAbilFolderizzazione() {
		return abilFolderizzazione;
	}
	public void setAbilFolderizzazione(Boolean abilFolderizzazione) {
		this.abilFolderizzazione = abilFolderizzazione;
	}
	public Boolean getAbilAssegnazione() {
		return abilAssegnazione;
	}
	public void setAbilAssegnazione(Boolean abilAssegnazione) {
		this.abilAssegnazione = abilAssegnazione;
	}
	public Boolean getAbilRestituzione() {
		return abilRestituzione;
	}
	public void setAbilRestituzione(Boolean abilRestituzione) {
		this.abilRestituzione = abilRestituzione;
	}
	public Boolean getAbilSmistamento() {
		return abilSmistamento;
	}
	public void setAbilSmistamento(Boolean abilSmistamento) {
		this.abilSmistamento = abilSmistamento;
	}
	public Boolean getAbilSmistamentoCC() {
		return abilSmistamentoCC;
	}
	public void setAbilSmistamentoCC(Boolean abilSmistamentoCC) {
		this.abilSmistamentoCC = abilSmistamentoCC;
	}
	public Boolean getAbilInvioPerConoscenza() {
		return abilInvioPerConoscenza;
	}
	public void setAbilInvioPerConoscenza(Boolean abilInvioPerConoscenza) {
		this.abilInvioPerConoscenza = abilInvioPerConoscenza;
	}
	public Boolean getAbilArchiviazione() {
		return abilArchiviazione;
	}
	public void setAbilArchiviazione(Boolean abilArchiviazione) {
		this.abilArchiviazione = abilArchiviazione;
	}
	public Boolean getAbilAnnullamentoArchiviazione() {
		return abilAnnullamentoArchiviazione;
	}
	public void setAbilAnnullamentoArchiviazione(Boolean abilAnnullamentoArchiviazione) {
		this.abilAnnullamentoArchiviazione = abilAnnullamentoArchiviazione;
	}
	public Boolean getAbilAggiuntaAiPreferiti() {
		return abilAggiuntaAiPreferiti;
	}
	public void setAbilAggiuntaAiPreferiti(Boolean abilAggiuntaAiPreferiti) {
		this.abilAggiuntaAiPreferiti = abilAggiuntaAiPreferiti;
	}
	public Boolean getAbilRimozioneDaiPreferiti() {
		return abilRimozioneDaiPreferiti;
	}
	public void setAbilRimozioneDaiPreferiti(Boolean abilRimozioneDaiPreferiti) {
		this.abilRimozioneDaiPreferiti = abilRimozioneDaiPreferiti;
	}
	public Boolean getAbilAssegnazioneRiservatezza() {
		return abilAssegnazioneRiservatezza;
	}
	public void setAbilAssegnazioneRiservatezza(Boolean abilAssegnazioneRiservatezza) {
		this.abilAssegnazioneRiservatezza = abilAssegnazioneRiservatezza;
	}
	public Boolean getAbilRimozioneRiservatezza() {
		return abilRimozioneRiservatezza;
	}
	public void setAbilRimozioneRiservatezza(Boolean abilRimozioneRiservatezza) {
		this.abilRimozioneRiservatezza = abilRimozioneRiservatezza;
	}
	public Boolean getAbilAnnullamentoEliminazione() {
		return abilAnnullamentoEliminazione;
	}
	public void setAbilAnnullamentoEliminazione(Boolean abilAnnullamentoEliminazione) {
		this.abilAnnullamentoEliminazione = abilAnnullamentoEliminazione;
	}
	public Boolean getAbilEliminazione() {
		return abilEliminazione;
	}
	public void setAbilEliminazione(Boolean abilEliminazione) {
		this.abilEliminazione = abilEliminazione;
	}
	public Boolean getAbilEliminazioneImgScansione() {
		return abilEliminazioneImgScansione;
	}
	public Boolean getAbilAssociazioneImgAProtocollo() {
		return abilAssociazioneImgAProtocollo;
	}
	public void setAbilEliminazioneImgScansione(Boolean abilEliminazioneImgScansione) {
		this.abilEliminazioneImgScansione = abilEliminazioneImgScansione;
	}
	public void setAbilAssociazioneImgAProtocollo(Boolean abilAssociazioneImgAProtocollo) {
		this.abilAssociazioneImgAProtocollo = abilAssociazioneImgAProtocollo;
	}
	public Boolean getAbilApposizioneCommenti() {
		return abilApposizioneCommenti;
	}
	public void setAbilApposizioneCommenti(Boolean abilApposizioneCommenti) {
		this.abilApposizioneCommenti = abilApposizioneCommenti;
	}
	public Boolean getAbilStampaEtichetta() {
		return abilStampaEtichetta;
	}
	public void setAbilStampaEtichetta(Boolean abilStampaEtichetta) {
		this.abilStampaEtichetta = abilStampaEtichetta;
	}
	public Boolean getAbilDownloadDocZipMultiButton() {
		return abilDownloadDocZipMultiButton;
	}
	public void setAbilDownloadDocZipMultiButton(Boolean abilDownloadDocZipMultiButton) {
		this.abilDownloadDocZipMultiButton = abilDownloadDocZipMultiButton;
	}
	public Boolean getAbilModificaStatoDocMultiButton() {
		return abilModificaStatoDocMultiButton;
	}
	public void setAbilModificaStatoDocMultiButton(Boolean abilModificaStatoDocMultiButton) {
		this.abilModificaStatoDocMultiButton = abilModificaStatoDocMultiButton;
	}
	public Boolean getAbilModificaTipologiaMultiButton() {
		return abilModificaTipologiaMultiButton;
	}
	public void setAbilModificaTipologiaMultiButton(Boolean abilModificaTipologiaMultiButton) {
		this.abilModificaTipologiaMultiButton = abilModificaTipologiaMultiButton;
	}
	public Boolean getAbilChiudiFascicoloMultiButton() {
		return abilChiudiFascicoloMultiButton;
	}
	public void setAbilChiudiFascicoloMultiButton(Boolean abilChiudiFascicoloMultiButton) {
		this.abilChiudiFascicoloMultiButton = abilChiudiFascicoloMultiButton;
	}
	public Boolean getAbilRiapriFascicoloMultiButton() {
		return abilRiapriFascicoloMultiButton;
	}
	public void setAbilRiapriFascicoloMultiButton(Boolean abilRiapriFascicoloMultiButton) {
		this.abilRiapriFascicoloMultiButton = abilRiapriFascicoloMultiButton;
	}
	public Boolean getAbilSegnaComeVisionatoMultiButton() {
		return abilSegnaComeVisionatoMultiButton;
	}
	public void setAbilSegnaComeVisionatoMultiButton(Boolean abilSegnaComeVisionatoMultiButton) {
		this.abilSegnaComeVisionatoMultiButton = abilSegnaComeVisionatoMultiButton;
	}
	public Boolean getAbilRichiesteAccessoAttiInvioInApprovazione() {
		return abilRichiesteAccessoAttiInvioInApprovazione;
	}
	public void setAbilRichiesteAccessoAttiInvioInApprovazione(Boolean abilRichiesteAccessoAttiInvioInApprovazione) {
		this.abilRichiesteAccessoAttiInvioInApprovazione = abilRichiesteAccessoAttiInvioInApprovazione;
	}
	public Boolean getAbilRichiesteAccessoAttiApprovazione() {
		return abilRichiesteAccessoAttiApprovazione;
	}
	public void setAbilRichiesteAccessoAttiApprovazione(Boolean abilRichiesteAccessoAttiApprovazione) {
		this.abilRichiesteAccessoAttiApprovazione = abilRichiesteAccessoAttiApprovazione;
	}
	public Boolean getAbilRichiesteAccessoAttiInvioEsitoVerificaArchivio() {
		return abilRichiesteAccessoAttiInvioEsitoVerificaArchivio;
	}
	public void setAbilRichiesteAccessoAttiInvioEsitoVerificaArchivio(
			Boolean abilRichiesteAccessoAttiInvioEsitoVerificaArchivio) {
		this.abilRichiesteAccessoAttiInvioEsitoVerificaArchivio = abilRichiesteAccessoAttiInvioEsitoVerificaArchivio;
	}
	public Boolean getAbilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda() {
		return abilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda;
	}
	public void setAbilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda(
			Boolean abilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda) {
		this.abilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda = abilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda;
	}
	public Boolean getAbilRichiesteAccessoAttiRegistrazioneAppuntamento() {
		return abilRichiesteAccessoAttiRegistrazioneAppuntamento;
	}
	public void setAbilRichiesteAccessoAttiRegistrazioneAppuntamento(
			Boolean abilRichiesteAccessoAttiRegistrazioneAppuntamento) {
		this.abilRichiesteAccessoAttiRegistrazioneAppuntamento = abilRichiesteAccessoAttiRegistrazioneAppuntamento;
	}
	public Boolean getAbilRichiesteAccessoAttiAnnullamentoAppuntamento() {
		return abilRichiesteAccessoAttiAnnullamentoAppuntamento;
	}
	public void setAbilRichiesteAccessoAttiAnnullamentoAppuntamento(
			Boolean abilRichiesteAccessoAttiAnnullamentoAppuntamento) {
		this.abilRichiesteAccessoAttiAnnullamentoAppuntamento = abilRichiesteAccessoAttiAnnullamentoAppuntamento;
	}
	public Boolean getAbilRichiesteAccessoAttiRegistrazionePrelievo() {
		return abilRichiesteAccessoAttiRegistrazionePrelievo;
	}
	public void setAbilRichiesteAccessoAttiRegistrazionePrelievo(Boolean abilRichiesteAccessoAttiRegistrazionePrelievo) {
		this.abilRichiesteAccessoAttiRegistrazionePrelievo = abilRichiesteAccessoAttiRegistrazionePrelievo;
	}
	public Boolean getAbilRichiesteAccessoAttiRegistrazioneRestituzione() {
		return abilRichiesteAccessoAttiRegistrazioneRestituzione;
	}
	public void setAbilRichiesteAccessoAttiRegistrazioneRestituzione(
			Boolean abilRichiesteAccessoAttiRegistrazioneRestituzione) {
		this.abilRichiesteAccessoAttiRegistrazioneRestituzione = abilRichiesteAccessoAttiRegistrazioneRestituzione;
	}
	public Boolean getAbilRichiesteAccessoAttiAnullamentoRichiesta() {
		return abilRichiesteAccessoAttiAnullamentoRichiesta;
	}
	public void setAbilRichiesteAccessoAttiAnullamentoRichiesta(Boolean abilRichiesteAccessoAttiAnullamentoRichiesta) {
		this.abilRichiesteAccessoAttiAnullamentoRichiesta = abilRichiesteAccessoAttiAnullamentoRichiesta;
	}
	public Boolean getAbilRichiesteAccessoAttiStampaFoglioPrelievo() {
		return abilRichiesteAccessoAttiStampaFoglioPrelievo;
	}
	public void setAbilRichiesteAccessoAttiStampaFoglioPrelievo(Boolean abilRichiesteAccessoAttiStampaFoglioPrelievo) {
		this.abilRichiesteAccessoAttiStampaFoglioPrelievo = abilRichiesteAccessoAttiStampaFoglioPrelievo;
	}
	public Boolean getAbilRichiesteAccessoAttiEliminazioneRichiesta() {
		return abilRichiesteAccessoAttiEliminazioneRichiesta;
	}
	public void setAbilRichiesteAccessoAttiEliminazioneRichiesta(Boolean abilRichiesteAccessoAttiEliminazioneRichiesta) {
		this.abilRichiesteAccessoAttiEliminazioneRichiesta = abilRichiesteAccessoAttiEliminazioneRichiesta;
	}
	public Boolean getAbilEmailChiusuraLavorazione() {
		return abilEmailChiusuraLavorazione;
	}
	public void setAbilEmailChiusuraLavorazione(Boolean abilEmailChiusuraLavorazione) {
		this.abilEmailChiusuraLavorazione = abilEmailChiusuraLavorazione;
	}
	public Boolean getAbilEmailRiaperturaLavorazione() {
		return abilEmailRiaperturaLavorazione;
	}
	public void setAbilEmailRiaperturaLavorazione(Boolean abilEmailRiaperturaLavorazione) {
		this.abilEmailRiaperturaLavorazione = abilEmailRiaperturaLavorazione;
	}
	public Boolean getAbilEmailAssegnazione() {
		return abilEmailAssegnazione;
	}
	public void setAbilEmailAssegnazione(Boolean abilEmailAssegnazione) {
		this.abilEmailAssegnazione = abilEmailAssegnazione;
	}
	public Boolean getAbilEmailAnnullamentoAssegnazione() {
		return abilEmailAnnullamentoAssegnazione;
	}
	public void setAbilEmailAnnullamentoAssegnazione(Boolean abilEmailAnnullamentoAssegnazione) {
		this.abilEmailAnnullamentoAssegnazione = abilEmailAnnullamentoAssegnazione;
	}
	public Boolean getAbilEmailInoltro() {
		return abilEmailInoltro;
	}
	public void setAbilEmailInoltro(Boolean abilEmailInoltro) {
		this.abilEmailInoltro = abilEmailInoltro;
	}
	public Boolean getAbilEmailPresaInCarico() {
		return abilEmailPresaInCarico;
	}
	public void setAbilEmailPresaInCarico(Boolean abilEmailPresaInCarico) {
		this.abilEmailPresaInCarico = abilEmailPresaInCarico;
	}
	public Boolean getAbilEmailMessaInCarico() {
		return abilEmailMessaInCarico;
	}
	public void setAbilEmailMessaInCarico(Boolean abilEmailMessaInCarico) {
		this.abilEmailMessaInCarico = abilEmailMessaInCarico;
	}
	public Boolean getAbilEmailInvioInApprovazione() {
		return abilEmailInvioInApprovazione;
	}
	public void setAbilEmailInvioInApprovazione(Boolean abilEmailInvioInApprovazione) {
		this.abilEmailInvioInApprovazione = abilEmailInvioInApprovazione;
	}
	public Boolean getAbilEmailRilascio() {
		return abilEmailRilascio;
	}
	public void setAbilEmailRilascio(Boolean abilEmailRilascio) {
		this.abilEmailRilascio = abilEmailRilascio;
	}
	public Boolean getAbilEmailEliminazione() {
		return abilEmailEliminazione;
	}
	public void setAbilEmailEliminazione(Boolean abilEmailEliminazione) {
		this.abilEmailEliminazione = abilEmailEliminazione;
	}
	public Boolean getAbilEmailApposizioneTagCommenti() {
		return abilEmailApposizioneTagCommenti;
	}
	public void setAbilEmailApposizioneTagCommenti(Boolean abilEmailApposizioneTagCommenti) {
		this.abilEmailApposizioneTagCommenti = abilEmailApposizioneTagCommenti;
	}
}