/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class AttiRichiestiBean extends VisualBean {

	private String tipoProtocollo;
	private String idFolder;
	private String numProtocolloGenerale;
	private String annoProtocolloGenerale;
	private String siglaProtocolloSettore;
	private String numProtocolloSettore;
	private String subProtocolloSettore;
	private String annoProtocolloSettore;
	private String numPraticaWorkflow;
	private String annoPraticaWorkflow;
	private String classifica;
	private String statoScansione;
	private String stato;
	private String udc;
	// Ufficio di prelievo
	private String codRapidoUfficioPrelievo;
	private String descrizioneUfficioPrelievo;
	private String idUoUfficioPrelievo;
	private String organigrammaUfficioPrelievo;
	// Responsabile di prelievo
	private String usernameResponsabilePrelievo;
	private String idUserResponsabilePrelievo;
	private String cognomeResponsabilePrelievo;
	private String nomeResponsabilePrelievo;

	// private List<AssegnazioneBean> listaUfficiPrelievo;
	// private List<SoggEsternoProtBean> listaResponsabiliPrelievo;
	private Date dataPrelievo;
	private String noteUffRichiedente;
	private String noteCittadella;
	
	private String statoAttoDaSincronizzare;
	
	private String competenzaDiUrbanistica;
	private String cartaceoReperibile;
	private String inArchivio;
	private String desInArchivio;
	private Boolean flgRichiestaVisioneCartaceo;
	
	private String tipoFascicolo;
	private String desTipoFascicolo;	
	private String annoProtEdiliziaPrivata;
	private String numeroProtEdiliziaPrivata;
	private String annoWorkflow;
	private String numeroWorkflow;
	private String numeroDeposito;
	
	private String tipoComunicazione;
	private String desTipoComunicazione;
	private String noteSportello;
	private String visureCollegate;

	public String getTipoProtocollo() {
		return tipoProtocollo;
	}

	public void setTipoProtocollo(String tipoProtocollo) {
		this.tipoProtocollo = tipoProtocollo;
	}

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	public String getNumProtocolloGenerale() {
		return numProtocolloGenerale;
	}

	public void setNumProtocolloGenerale(String numProtocolloGenerale) {
		this.numProtocolloGenerale = numProtocolloGenerale;
	}

	public String getAnnoProtocolloGenerale() {
		return annoProtocolloGenerale;
	}

	public void setAnnoProtocolloGenerale(String annoProtocolloGenerale) {
		this.annoProtocolloGenerale = annoProtocolloGenerale;
	}

	public String getSiglaProtocolloSettore() {
		return siglaProtocolloSettore;
	}

	public void setSiglaProtocolloSettore(String siglaProtocolloSettore) {
		this.siglaProtocolloSettore = siglaProtocolloSettore;
	}

	public String getNumProtocolloSettore() {
		return numProtocolloSettore;
	}

	public void setNumProtocolloSettore(String numProtocolloSettore) {
		this.numProtocolloSettore = numProtocolloSettore;
	}

	public String getSubProtocolloSettore() {
		return subProtocolloSettore;
	}

	public void setSubProtocolloSettore(String subProtocolloSettore) {
		this.subProtocolloSettore = subProtocolloSettore;
	}

	public String getAnnoProtocolloSettore() {
		return annoProtocolloSettore;
	}

	public void setAnnoProtocolloSettore(String annoProtocolloSettore) {
		this.annoProtocolloSettore = annoProtocolloSettore;
	}
	
	public String getNumPraticaWorkflow() {
		return numPraticaWorkflow;
	}

	public void setNumPraticaWorkflow(String numPraticaWorkflow) {
		this.numPraticaWorkflow = numPraticaWorkflow;
	}
	
	public String getAnnoPraticaWorkflow() {
		return annoPraticaWorkflow;
	}
	
	public void setAnnoPraticaWorkflow(String annoPraticaWorkflow) {
		this.annoPraticaWorkflow = annoPraticaWorkflow;
	}

	public String getClassifica() {
		return classifica;
	}
	
	public void setClassifica(String classifica) {
		this.classifica = classifica;
	}

	public String getStatoScansione() {
		return statoScansione;
	}

	public void setStatoScansione(String statoScansione) {
		this.statoScansione = statoScansione;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getUdc() {
		return udc;
	}

	public void setUdc(String udc) {
		this.udc = udc;
	}

	public String getCodRapidoUfficioPrelievo() {
		return codRapidoUfficioPrelievo;
	}

	public void setCodRapidoUfficioPrelievo(String codRapidoUfficioPrelievo) {
		this.codRapidoUfficioPrelievo = codRapidoUfficioPrelievo;
	}

	public String getDescrizioneUfficioPrelievo() {
		return descrizioneUfficioPrelievo;
	}

	public void setDescrizioneUfficioPrelievo(String descrizioneUfficioPrelievo) {
		this.descrizioneUfficioPrelievo = descrizioneUfficioPrelievo;
	}

	public String getIdUoUfficioPrelievo() {
		return idUoUfficioPrelievo;
	}

	public void setIdUoUfficioPrelievo(String idUoUfficioPrelievo) {
		this.idUoUfficioPrelievo = idUoUfficioPrelievo;
	}

	public String getOrganigrammaUfficioPrelievo() {
		return organigrammaUfficioPrelievo;
	}

	public void setOrganigrammaUfficioPrelievo(String organigrammaUfficioPrelievo) {
		this.organigrammaUfficioPrelievo = organigrammaUfficioPrelievo;
	}

	public String getUsernameResponsabilePrelievo() {
		return usernameResponsabilePrelievo;
	}

	public void setUsernameResponsabilePrelievo(String usernameResponsabilePrelievo) {
		this.usernameResponsabilePrelievo = usernameResponsabilePrelievo;
	}

	public String getIdUserResponsabilePrelievo() {
		return idUserResponsabilePrelievo;
	}

	public void setIdUserResponsabilePrelievo(String idUserResponsabilePrelievo) {
		this.idUserResponsabilePrelievo = idUserResponsabilePrelievo;
	}

	public String getCognomeResponsabilePrelievo() {
		return cognomeResponsabilePrelievo;
	}

	public void setCognomeResponsabilePrelievo(String cognomeResponsabilePrelievo) {
		this.cognomeResponsabilePrelievo = cognomeResponsabilePrelievo;
	}
	
	public String getNomeResponsabilePrelievo() {
		return nomeResponsabilePrelievo;
	}

	public void setNomeResponsabilePrelievo(String nomeResponsabilePrelievo) {
		this.nomeResponsabilePrelievo = nomeResponsabilePrelievo;
	}

	public Date getDataPrelievo() {
		return dataPrelievo;
	}

	public void setDataPrelievo(Date dataPrelievo) {
		this.dataPrelievo = dataPrelievo;
	}

	public String getNoteUffRichiedente() {
		return noteUffRichiedente;
	}

	public void setNoteUffRichiedente(String noteUffRichiedente) {
		this.noteUffRichiedente = noteUffRichiedente;
	}

	public String getNoteCittadella() {
		return noteCittadella;
	}

	public void setNoteCittadella(String noteCittadella) {
		this.noteCittadella = noteCittadella;
	}

	public String getStatoAttoDaSincronizzare() {
		return statoAttoDaSincronizzare;
	}
	
	public void setStatoAttoDaSincronizzare(String statoAttoDaSincronizzare) {
		this.statoAttoDaSincronizzare = statoAttoDaSincronizzare;
	}
	
	public String getCompetenzaDiUrbanistica() {
		return competenzaDiUrbanistica;
	}
	
	public void setCompetenzaDiUrbanistica(String competenzaDiUrbanistica) {
		this.competenzaDiUrbanistica = competenzaDiUrbanistica;
	}
	
	public String getCartaceoReperibile() {
		return cartaceoReperibile;
	}
	
	public void setCartaceoReperibile(String cartaceoReperibile) {
		this.cartaceoReperibile = cartaceoReperibile;
	}
	
	public String getInArchivio() {
		return inArchivio;
	}

	public void setInArchivio(String inArchivio) {
		this.inArchivio = inArchivio;
	}

	public String getDesInArchivio() {
		return desInArchivio;
	}

	public void setDesInArchivio(String desInArchivio) {
		this.desInArchivio = desInArchivio;
	}

	public Boolean getFlgRichiestaVisioneCartaceo() {
		return flgRichiestaVisioneCartaceo;
	}

	public void setFlgRichiestaVisioneCartaceo(Boolean flgRichiestaVisioneCartaceo) {
		this.flgRichiestaVisioneCartaceo = flgRichiestaVisioneCartaceo;
	}
	
	public String getTipoFascicolo() {
		return tipoFascicolo;
	}
	
	public void setTipoFascicolo(String tipoFascicolo) {
		this.tipoFascicolo = tipoFascicolo;
	}

	public String getDesTipoFascicolo() {
		return desTipoFascicolo;
	}

	public void setDesTipoFascicolo(String desTipoFascicolo) {
		this.desTipoFascicolo = desTipoFascicolo;
	}

	public String getAnnoProtEdiliziaPrivata() {
		return annoProtEdiliziaPrivata;
	}
	
	public void setAnnoProtEdiliziaPrivata(String annoProtEdiliziaPrivata) {
		this.annoProtEdiliziaPrivata = annoProtEdiliziaPrivata;
	}
	
	public String getNumeroProtEdiliziaPrivata() {
		return numeroProtEdiliziaPrivata;
	}
	
	public void setNumeroProtEdiliziaPrivata(String numeroProtEdiliziaPrivata) {
		this.numeroProtEdiliziaPrivata = numeroProtEdiliziaPrivata;
	}
	
	public String getAnnoWorkflow() {
		return annoWorkflow;
	}

	public void setAnnoWorkflow(String annoWorkflow) {
		this.annoWorkflow = annoWorkflow;
	}
	
	public String getNumeroWorkflow() {
		return numeroWorkflow;
	}
	
	public void setNumeroWorkflow(String numeroWorkflow) {
		this.numeroWorkflow = numeroWorkflow;
	}
	
	public String getNumeroDeposito() {
		return numeroDeposito;
	}

	public void setNumeroDeposito(String numeroDeposito) {
		this.numeroDeposito = numeroDeposito;
	}
	
	public String getTipoComunicazione() {
		return tipoComunicazione;
	}
	
	public void setTipoComunicazione(String tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}
	
	public String getDesTipoComunicazione() {
		return desTipoComunicazione;
	}

	public void setDesTipoComunicazione(String desTipoComunicazione) {
		this.desTipoComunicazione = desTipoComunicazione;
	}

	public String getNoteSportello() {
		return noteSportello;
	}
	
	public void setNoteSportello(String noteSportello) {
		this.noteSportello = noteSportello;
	}
	
	public String getVisureCollegate() {
		return visureCollegate;
	}

	public void setVisureCollegate(String visureCollegate) {
		this.visureCollegate = visureCollegate;
	}

}
