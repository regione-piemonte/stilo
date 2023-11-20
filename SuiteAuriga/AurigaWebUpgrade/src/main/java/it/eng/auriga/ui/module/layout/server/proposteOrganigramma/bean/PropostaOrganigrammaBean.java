/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class PropostaOrganigrammaBean {
	
	/*******************
	 * TAB DATI SCHEDA *
	 *******************/

	/* Hidden */
	private String idUd;
	private String tipoDocumento;
	private String nomeTipoDocumento;
	private String rowidDoc;
	private String idDocPrimario;
	private String nomeFilePrimario;
	private String uriFilePrimario;
	private Boolean remoteUriFilePrimario;
	private MimeTypeFirmaBean infoFilePrimario;
	private Boolean isChangedFilePrimario;
	private String idDocPrimarioOmissis; 
	private String nomeFilePrimarioOmissis;
	private String uriFilePrimarioOmissis;
	private Boolean remoteUriFilePrimarioOmissis;
	private MimeTypeFirmaBean infoFilePrimarioOmissis;		
	private Boolean isChangedFilePrimarioOmissis;	
	private Boolean flgDatiSensibili;
			
	/* Registrazione */
	private String siglaRegistrazione;
	private String numeroRegistrazione;
	private Date dataRegistrazione;
	private String annoRegistrazione;
	private String desUserRegistrazione;
	private String desUORegistrazione;
	private String siglaRegProvvisoria;
	private String numeroRegProvvisoria;
	private Date dataRegProvvisoria;
	private String annoRegProvvisoria;
	private String desUserRegProvvisoria;
	private String desUORegProvvisoria;
		
	/* Ufficio revisione organigramma */
	private String idUoRevisioneOrganigramma;
	private String idUoPadreRevisioneOrganigramma;
	private String codRapidoUoRevisioneOrganigramma;
	private String nomeUoRevisioneOrganigramma;
	private String tipoUoRevisioneOrganigramma;
	private String livelloUoRevisioneOrganigramma;	

	/* Oggetto */
	private String oggetto;
	
	/* Testo proposta */
	private String testoProposta;
	
	/* Allegati */
	private List<AllegatoProtocolloBean> listaAllegati; // colonne: le stesse degli allegati in Protocollo
		
	/******************************
	 * TAB ATTRIBUTI DINAMICI DOC *
	 ******************************/
	
	private Map<String, Object> valori;
	private Map<String, String> tipiValori;
	private Map<String, String> colonneListe;
	
	/******************************
	 * PER GENERAZIONE DA MODELLO *
	 ******************************/
	
	private String idProcess;
	private String idModello;
	private String nomeModello;
	private String displayFilenameModello;	
	private String uriModello;
	private String tipoModello;
	private Boolean flgMostraDatiSensibili;
	
	/***********************
	 * Getters and Setters *
	 ***********************/
	
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNomeTipoDocumento() {
		return nomeTipoDocumento;
	}
	public void setNomeTipoDocumento(String nomeTipoDocumento) {
		this.nomeTipoDocumento = nomeTipoDocumento;
	}
	public String getRowidDoc() {
		return rowidDoc;
	}
	public void setRowidDoc(String rowidDoc) {
		this.rowidDoc = rowidDoc;
	}
	public String getIdDocPrimario() {
		return idDocPrimario;
	}
	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}
	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}
	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}
	public String getUriFilePrimario() {
		return uriFilePrimario;
	}
	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}
	public Boolean getRemoteUriFilePrimario() {
		return remoteUriFilePrimario;
	}
	public void setRemoteUriFilePrimario(Boolean remoteUriFilePrimario) {
		this.remoteUriFilePrimario = remoteUriFilePrimario;
	}
	public MimeTypeFirmaBean getInfoFilePrimario() {
		return infoFilePrimario;
	}
	public void setInfoFilePrimario(MimeTypeFirmaBean infoFilePrimario) {
		this.infoFilePrimario = infoFilePrimario;
	}
	public Boolean getIsChangedFilePrimario() {
		return isChangedFilePrimario;
	}
	public void setIsChangedFilePrimario(Boolean isChangedFilePrimario) {
		this.isChangedFilePrimario = isChangedFilePrimario;
	}
	public String getIdDocPrimarioOmissis() {
		return idDocPrimarioOmissis;
	}
	public void setIdDocPrimarioOmissis(String idDocPrimarioOmissis) {
		this.idDocPrimarioOmissis = idDocPrimarioOmissis;
	}
	public String getNomeFilePrimarioOmissis() {
		return nomeFilePrimarioOmissis;
	}
	public void setNomeFilePrimarioOmissis(String nomeFilePrimarioOmissis) {
		this.nomeFilePrimarioOmissis = nomeFilePrimarioOmissis;
	}
	public String getUriFilePrimarioOmissis() {
		return uriFilePrimarioOmissis;
	}
	public void setUriFilePrimarioOmissis(String uriFilePrimarioOmissis) {
		this.uriFilePrimarioOmissis = uriFilePrimarioOmissis;
	}
	public Boolean getRemoteUriFilePrimarioOmissis() {
		return remoteUriFilePrimarioOmissis;
	}
	public void setRemoteUriFilePrimarioOmissis(Boolean remoteUriFilePrimarioOmissis) {
		this.remoteUriFilePrimarioOmissis = remoteUriFilePrimarioOmissis;
	}
	public MimeTypeFirmaBean getInfoFilePrimarioOmissis() {
		return infoFilePrimarioOmissis;
	}
	public void setInfoFilePrimarioOmissis(MimeTypeFirmaBean infoFilePrimarioOmissis) {
		this.infoFilePrimarioOmissis = infoFilePrimarioOmissis;
	}
	public Boolean getIsChangedFilePrimarioOmissis() {
		return isChangedFilePrimarioOmissis;
	}
	public void setIsChangedFilePrimarioOmissis(Boolean isChangedFilePrimarioOmissis) {
		this.isChangedFilePrimarioOmissis = isChangedFilePrimarioOmissis;
	}
	public Boolean getFlgDatiSensibili() {
		return flgDatiSensibili;
	}
	public void setFlgDatiSensibili(Boolean flgDatiSensibili) {
		this.flgDatiSensibili = flgDatiSensibili;
	}
	public String getSiglaRegistrazione() {
		return siglaRegistrazione;
	}
	public void setSiglaRegistrazione(String siglaRegistrazione) {
		this.siglaRegistrazione = siglaRegistrazione;
	}
	public String getNumeroRegistrazione() {
		return numeroRegistrazione;
	}
	public void setNumeroRegistrazione(String numeroRegistrazione) {
		this.numeroRegistrazione = numeroRegistrazione;
	}
	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}
	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	public String getAnnoRegistrazione() {
		return annoRegistrazione;
	}
	public void setAnnoRegistrazione(String annoRegistrazione) {
		this.annoRegistrazione = annoRegistrazione;
	}
	public String getDesUserRegistrazione() {
		return desUserRegistrazione;
	}
	public void setDesUserRegistrazione(String desUserRegistrazione) {
		this.desUserRegistrazione = desUserRegistrazione;
	}
	public String getDesUORegistrazione() {
		return desUORegistrazione;
	}
	public void setDesUORegistrazione(String desUORegistrazione) {
		this.desUORegistrazione = desUORegistrazione;
	}
	public String getSiglaRegProvvisoria() {
		return siglaRegProvvisoria;
	}
	public void setSiglaRegProvvisoria(String siglaRegProvvisoria) {
		this.siglaRegProvvisoria = siglaRegProvvisoria;
	}
	public String getNumeroRegProvvisoria() {
		return numeroRegProvvisoria;
	}
	public void setNumeroRegProvvisoria(String numeroRegProvvisoria) {
		this.numeroRegProvvisoria = numeroRegProvvisoria;
	}
	public Date getDataRegProvvisoria() {
		return dataRegProvvisoria;
	}
	public void setDataRegProvvisoria(Date dataRegProvvisoria) {
		this.dataRegProvvisoria = dataRegProvvisoria;
	}
	public String getAnnoRegProvvisoria() {
		return annoRegProvvisoria;
	}
	public void setAnnoRegProvvisoria(String annoRegProvvisoria) {
		this.annoRegProvvisoria = annoRegProvvisoria;
	}
	public String getDesUserRegProvvisoria() {
		return desUserRegProvvisoria;
	}
	public void setDesUserRegProvvisoria(String desUserRegProvvisoria) {
		this.desUserRegProvvisoria = desUserRegProvvisoria;
	}
	public String getDesUORegProvvisoria() {
		return desUORegProvvisoria;
	}
	public void setDesUORegProvvisoria(String desUORegProvvisoria) {
		this.desUORegProvvisoria = desUORegProvvisoria;
	}
	public String getIdUoRevisioneOrganigramma() {
		return idUoRevisioneOrganigramma;
	}
	public void setIdUoRevisioneOrganigramma(String idUoRevisioneOrganigramma) {
		this.idUoRevisioneOrganigramma = idUoRevisioneOrganigramma;
	}
	public String getIdUoPadreRevisioneOrganigramma() {
		return idUoPadreRevisioneOrganigramma;
	}
	public void setIdUoPadreRevisioneOrganigramma(String idUoPadreRevisioneOrganigramma) {
		this.idUoPadreRevisioneOrganigramma = idUoPadreRevisioneOrganigramma;
	}
	public String getCodRapidoUoRevisioneOrganigramma() {
		return codRapidoUoRevisioneOrganigramma;
	}
	public void setCodRapidoUoRevisioneOrganigramma(String codRapidoUoRevisioneOrganigramma) {
		this.codRapidoUoRevisioneOrganigramma = codRapidoUoRevisioneOrganigramma;
	}
	public String getNomeUoRevisioneOrganigramma() {
		return nomeUoRevisioneOrganigramma;
	}
	public void setNomeUoRevisioneOrganigramma(String nomeUoRevisioneOrganigramma) {
		this.nomeUoRevisioneOrganigramma = nomeUoRevisioneOrganigramma;
	}
	public String getTipoUoRevisioneOrganigramma() {
		return tipoUoRevisioneOrganigramma;
	}
	public void setTipoUoRevisioneOrganigramma(String tipoUoRevisioneOrganigramma) {
		this.tipoUoRevisioneOrganigramma = tipoUoRevisioneOrganigramma;
	}
	public String getLivelloUoRevisioneOrganigramma() {
		return livelloUoRevisioneOrganigramma;
	}
	public void setLivelloUoRevisioneOrganigramma(String livelloUoRevisioneOrganigramma) {
		this.livelloUoRevisioneOrganigramma = livelloUoRevisioneOrganigramma;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getTestoProposta() {
		return testoProposta;
	}
	public void setTestoProposta(String testoProposta) {
		this.testoProposta = testoProposta;
	}
	public List<AllegatoProtocolloBean> getListaAllegati() {
		return listaAllegati;
	}
	public void setListaAllegati(List<AllegatoProtocolloBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}
	public Map<String, Object> getValori() {
		return valori;
	}
	public void setValori(Map<String, Object> valori) {
		this.valori = valori;
	}
	public Map<String, String> getTipiValori() {
		return tipiValori;
	}
	public void setTipiValori(Map<String, String> tipiValori) {
		this.tipiValori = tipiValori;
	}
	public Map<String, String> getColonneListe() {
		return colonneListe;
	}
	public void setColonneListe(Map<String, String> colonneListe) {
		this.colonneListe = colonneListe;
	}
	public String getIdProcess() {
		return idProcess;
	}
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	public String getIdModello() {
		return idModello;
	}
	public void setIdModello(String idModello) {
		this.idModello = idModello;
	}
	public String getNomeModello() {
		return nomeModello;
	}
	public void setNomeModello(String nomeModello) {
		this.nomeModello = nomeModello;
	}
	public String getDisplayFilenameModello() {
		return displayFilenameModello;
	}
	public void setDisplayFilenameModello(String displayFilenameModello) {
		this.displayFilenameModello = displayFilenameModello;
	}
	public String getUriModello() {
		return uriModello;
	}
	public void setUriModello(String uriModello) {
		this.uriModello = uriModello;
	}
	public String getTipoModello() {
		return tipoModello;
	}
	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}
	public Boolean getFlgMostraDatiSensibili() {
		return flgMostraDatiSensibili;
	}
	public void setFlgMostraDatiSensibili(Boolean flgMostraDatiSensibili) {
		this.flgMostraDatiSensibili = flgMostraDatiSensibili;
	}
	
}