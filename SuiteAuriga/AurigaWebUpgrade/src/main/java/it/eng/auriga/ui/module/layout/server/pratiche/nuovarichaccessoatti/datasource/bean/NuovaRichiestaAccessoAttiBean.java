/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.SelezionaScrivaniaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.DestinatarioSUEBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.FileDaPubblicareSUEBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AltraViaProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AttiRichiestiBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MittenteProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.SoggEsternoProtBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class NuovaRichiestaAccessoAttiBean {
	
	/* Hidden */
	private String idUd;
	private String idFolder;
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
	private List<MittenteProtBean> listaMittenti;
	// *********** PROTOCOLLO MILANO X SOSTITUZIONE PG@WEB ************
	private List<SoggEsternoProtBean> listaEsibenti;
	private List<AltraViaProtBean> listaAltreVie;
	private String siglaProtocolloPregresso;
	private String nroProtocolloPregresso;
	private String annoProtocolloPregresso;
	private Date dataProtocolloPregresso;
	private Boolean flgTipoDocConVie;
	// ************* RICHIESTA ACCESSO ATTI ****************
	private Boolean flgRichiestaAccessoAtti;
	private String tipoRichiedente;
//	private List<SelezionaScrivaniaBean> listaRespIstruttoria;	
	private String idUtenteRicercatore;
	private String cognomeNomeRicercatore;
	private String idEmailArrivo;
	private String codStatoRichAccessoAtti;
	private String desStatoRichAccessoAtti;
	private String siglaPraticaSuSistUfficioRichiedente;
	private String numeroPraticaSuSistUfficioRichiedente;
	private String annoPraticaSuSistUfficioRichiedente;
	private List<MittenteProtBean> listaRichiedentiInterni;
	private Boolean flgRichAttiFabbricaVisuraSue;
	private Boolean flgRichModificheVisuraSue;
	private List<AttiRichiestiBean> listaAttiRichiesti;
	private Boolean flgAltriAttiDaRicercareVisuraSue;
	private List<SoggEsternoProtBean> listaRichiedentiDelegati;
	private String motivoRichiestaAccessoAtti;
	private String dettagliRichiestaAccessoAtti;	
	private String idIncaricatoPrelievoPerUffRichiedente;
	private String usernameIncaricatoPrelievoPerUffRichiedente;
	private String codRapidoIncaricatoPrelievoPerUffRichiedente;
	private String cognomeIncaricatoPrelievoPerUffRichiedente;
	private String nomeIncaricatoPrelievoPerUffRichiedente;
	private String telefonoIncaricatoPrelievoPerUffRichiedente;	
	private String cognomeIncaricatoPrelievoPerRichEsterno;
	private String nomeIncaricatoPrelievoPerRichEsterno;
	private String codiceFiscaleIncaricatoPrelievoPerRichEsterno;
	private String emailIncaricatoPrelievoPerRichEsterno;
	private String telefonoIncaricatoPrelievoPerRichEsterno;
	private String dataAppuntamento;
	private String orarioAppuntamento;
	private String provenienzaAppuntamento;
	private Date dataPrelievo;
	private String dataRestituzionePrelievo;
	private String restituzionePrelievoAttestataDa;
	private String idNodoDefaultRicercaAtti;
	private List<AllegatoProtocolloBean> listaDocumentiIstruttoria;
	private String mezzoTrasmissione;
	private Boolean abilVisualizzaDettStdProt;	
	private Boolean abilInvioInApprovazione;
	private Boolean abilApprovazione;
	private Boolean abilInvioEsitoVerificaArchivio;
	private Boolean abilAbilitaAppuntamentoDaAgenda;
	private Boolean abilSetAppuntamento;
	private Boolean abilAnnullamentoAppuntamento;
	private Boolean abilRegistraPrelievo;
	private Boolean abilRegistraRestituzione;
	private Boolean abilAnnullamento;
	private Boolean abilStampaFoglioPrelievo;
	private Boolean abilRichAccessoAttiChiusura;
	private Boolean abilRichAccessoAttiRiapertura;
	private Boolean abilRichAccessoAttiRipristino;	
	private Boolean prelievoEffettuato;
	
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
	private String estensioneFileDaGenerare;
	
	/******************
	 * PER EVENTO SUE *
	 ******************/
	private String nroProtocolloSUE;
	private Date dataProtocolloSUE;
	private String amministrazioneSUE;
	private String aooSUE;
	private String tipoEventoSUE;
	private String idPraticaSUE;
	private String codFiscaleSUE;
	private String giorniSospensioneSUE;
	private Boolean flgPubblicoSUE;						
	private List<DestinatarioSUEBean> destinatariSUE;
	private List<FileDaPubblicareSUEBean> fileDaPubblicareSUE;
	private String idTipoDocAllegatoSUE;
	private String idDocAllegatoSUE;
	
	/***********************
	 * PER PROTOCOLLAZIONE *
	 ***********************/
	
	private String idTipoDocToProt;
	private String idDocToProt;
	
	public String getIdUd() {
		return idUd;
	}
	
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	
	public String getIdFolder() {
		return idFolder;
	}
	
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
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
	
	public List<MittenteProtBean> getListaMittenti() {
		return listaMittenti;
	}
	
	public void setListaMittenti(List<MittenteProtBean> listaMittenti) {
		this.listaMittenti = listaMittenti;
	}
	
	public List<SoggEsternoProtBean> getListaEsibenti() {
		return listaEsibenti;
	}
	
	public void setListaEsibenti(List<SoggEsternoProtBean> listaEsibenti) {
		this.listaEsibenti = listaEsibenti;
	}
	
	public List<AltraViaProtBean> getListaAltreVie() {
		return listaAltreVie;
	}
	
	public void setListaAltreVie(List<AltraViaProtBean> listaAltreVie) {
		this.listaAltreVie = listaAltreVie;
	}
	
	public String getSiglaProtocolloPregresso() {
		return siglaProtocolloPregresso;
	}
	
	public void setSiglaProtocolloPregresso(String siglaProtocolloPregresso) {
		this.siglaProtocolloPregresso = siglaProtocolloPregresso;
	}
	
	public String getNroProtocolloPregresso() {
		return nroProtocolloPregresso;
	}
	
	public void setNroProtocolloPregresso(String nroProtocolloPregresso) {
		this.nroProtocolloPregresso = nroProtocolloPregresso;
	}
	
	public String getAnnoProtocolloPregresso() {
		return annoProtocolloPregresso;
	}
	
	public void setAnnoProtocolloPregresso(String annoProtocolloPregresso) {
		this.annoProtocolloPregresso = annoProtocolloPregresso;
	}
	
	public Date getDataProtocolloPregresso() {
		return dataProtocolloPregresso;
	}
	
	public void setDataProtocolloPregresso(Date dataProtocolloPregresso) {
		this.dataProtocolloPregresso = dataProtocolloPregresso;
	}
	
	public Boolean getFlgTipoDocConVie() {
		return flgTipoDocConVie;
	}
	
	public void setFlgTipoDocConVie(Boolean flgTipoDocConVie) {
		this.flgTipoDocConVie = flgTipoDocConVie;
	}
	
	public Boolean getFlgRichiestaAccessoAtti() {
		return flgRichiestaAccessoAtti;
	}
	
	public void setFlgRichiestaAccessoAtti(Boolean flgRichiestaAccessoAtti) {
		this.flgRichiestaAccessoAtti = flgRichiestaAccessoAtti;
	}
	
	public String getTipoRichiedente() {
		return tipoRichiedente;
	}
	
	public void setTipoRichiedente(String tipoRichiedente) {
		this.tipoRichiedente = tipoRichiedente;
	}
	
//	public List<SelezionaScrivaniaBean> getListaRespIstruttoria() {
//		return listaRespIstruttoria;
//	}
//
//	public void setListaRespIstruttoria(List<SelezionaScrivaniaBean> listaRespIstruttoria) {
//		this.listaRespIstruttoria = listaRespIstruttoria;
//	}
	
	public String getIdUtenteRicercatore() {
		return idUtenteRicercatore;
	}

	public void setIdUtenteRicercatore(String idUtenteRicercatore) {
		this.idUtenteRicercatore = idUtenteRicercatore;
	}

	public String getCognomeNomeRicercatore() {
		return cognomeNomeRicercatore;
	}

	public void setCognomeNomeRicercatore(String cognomeNomeRicercatore) {
		this.cognomeNomeRicercatore = cognomeNomeRicercatore;
	}

	public String getIdEmailArrivo() {
		return idEmailArrivo;
	}

	public void setIdEmailArrivo(String idEmailArrivo) {
		this.idEmailArrivo = idEmailArrivo;
	}

	public String getCodStatoRichAccessoAtti() {
		return codStatoRichAccessoAtti;
	}
	
	public void setCodStatoRichAccessoAtti(String codStatoRichAccessoAtti) {
		this.codStatoRichAccessoAtti = codStatoRichAccessoAtti;
	}
	
	public String getDesStatoRichAccessoAtti() {
		return desStatoRichAccessoAtti;
	}
	
	public void setDesStatoRichAccessoAtti(String desStatoRichAccessoAtti) {
		this.desStatoRichAccessoAtti = desStatoRichAccessoAtti;
	}
	
	public String getSiglaPraticaSuSistUfficioRichiedente() {
		return siglaPraticaSuSistUfficioRichiedente;
	}
	
	public void setSiglaPraticaSuSistUfficioRichiedente(String siglaPraticaSuSistUfficioRichiedente) {
		this.siglaPraticaSuSistUfficioRichiedente = siglaPraticaSuSistUfficioRichiedente;
	}
	
	public String getNumeroPraticaSuSistUfficioRichiedente() {
		return numeroPraticaSuSistUfficioRichiedente;
	}
	
	public void setNumeroPraticaSuSistUfficioRichiedente(String numeroPraticaSuSistUfficioRichiedente) {
		this.numeroPraticaSuSistUfficioRichiedente = numeroPraticaSuSistUfficioRichiedente;
	}
	
	public String getAnnoPraticaSuSistUfficioRichiedente() {
		return annoPraticaSuSistUfficioRichiedente;
	}
	
	public void setAnnoPraticaSuSistUfficioRichiedente(String annoPraticaSuSistUfficioRichiedente) {
		this.annoPraticaSuSistUfficioRichiedente = annoPraticaSuSistUfficioRichiedente;
	}
	
	public List<MittenteProtBean> getListaRichiedentiInterni() {
		return listaRichiedentiInterni;
	}
	
	public void setListaRichiedentiInterni(List<MittenteProtBean> listaRichiedentiInterni) {
		this.listaRichiedentiInterni = listaRichiedentiInterni;
	}
	
	public Boolean getFlgRichAttiFabbricaVisuraSue() {
		return flgRichAttiFabbricaVisuraSue;
	}

	public void setFlgRichAttiFabbricaVisuraSue(Boolean flgRichAttiFabbricaVisuraSue) {
		this.flgRichAttiFabbricaVisuraSue = flgRichAttiFabbricaVisuraSue;
	}
	
	public Boolean getFlgRichModificheVisuraSue() {
		return flgRichModificheVisuraSue;
	}
	
	public void setFlgRichModificheVisuraSue(Boolean flgRichModificheVisuraSue) {
		this.flgRichModificheVisuraSue = flgRichModificheVisuraSue;
	}

	public List<AttiRichiestiBean> getListaAttiRichiesti() {
		return listaAttiRichiesti;
	}
	
	public void setListaAttiRichiesti(List<AttiRichiestiBean> listaAttiRichiesti) {
		this.listaAttiRichiesti = listaAttiRichiesti;
	}
	
	public Boolean getFlgAltriAttiDaRicercareVisuraSue() {
		return flgAltriAttiDaRicercareVisuraSue;
	}
	
	public void setFlgAltriAttiDaRicercareVisuraSue(Boolean flgAltriAttiDaRicercareVisuraSue) {
		this.flgAltriAttiDaRicercareVisuraSue = flgAltriAttiDaRicercareVisuraSue;
	}

	public List<SoggEsternoProtBean> getListaRichiedentiDelegati() {
		return listaRichiedentiDelegati;
	}
	
	public void setListaRichiedentiDelegati(List<SoggEsternoProtBean> listaRichiedentiDelegati) {
		this.listaRichiedentiDelegati = listaRichiedentiDelegati;
	}
	
	public String getMotivoRichiestaAccessoAtti() {
		return motivoRichiestaAccessoAtti;
	}
	
	public void setMotivoRichiestaAccessoAtti(String motivoRichiestaAccessoAtti) {
		this.motivoRichiestaAccessoAtti = motivoRichiestaAccessoAtti;
	}
	
	public String getDettagliRichiestaAccessoAtti() {
		return dettagliRichiestaAccessoAtti;
	}
	
	public void setDettagliRichiestaAccessoAtti(String dettagliRichiestaAccessoAtti) {
		this.dettagliRichiestaAccessoAtti = dettagliRichiestaAccessoAtti;
	}
	
	public String getIdIncaricatoPrelievoPerUffRichiedente() {
		return idIncaricatoPrelievoPerUffRichiedente;
	}
	
	public void setIdIncaricatoPrelievoPerUffRichiedente(String idIncaricatoPrelievoPerUffRichiedente) {
		this.idIncaricatoPrelievoPerUffRichiedente = idIncaricatoPrelievoPerUffRichiedente;
	}
	
	public String getUsernameIncaricatoPrelievoPerUffRichiedente() {
		return usernameIncaricatoPrelievoPerUffRichiedente;
	}
	
	public void setUsernameIncaricatoPrelievoPerUffRichiedente(String usernameIncaricatoPrelievoPerUffRichiedente) {
		this.usernameIncaricatoPrelievoPerUffRichiedente = usernameIncaricatoPrelievoPerUffRichiedente;
	}
	
	public String getCodRapidoIncaricatoPrelievoPerUffRichiedente() {
		return codRapidoIncaricatoPrelievoPerUffRichiedente;
	}
	
	public void setCodRapidoIncaricatoPrelievoPerUffRichiedente(String codRapidoIncaricatoPrelievoPerUffRichiedente) {
		this.codRapidoIncaricatoPrelievoPerUffRichiedente = codRapidoIncaricatoPrelievoPerUffRichiedente;
	}
	
	public String getCognomeIncaricatoPrelievoPerUffRichiedente() {
		return cognomeIncaricatoPrelievoPerUffRichiedente;
	}
	
	public void setCognomeIncaricatoPrelievoPerUffRichiedente(String cognomeIncaricatoPrelievoPerUffRichiedente) {
		this.cognomeIncaricatoPrelievoPerUffRichiedente = cognomeIncaricatoPrelievoPerUffRichiedente;
	}
	
	public String getNomeIncaricatoPrelievoPerUffRichiedente() {
		return nomeIncaricatoPrelievoPerUffRichiedente;
	}
	
	public void setNomeIncaricatoPrelievoPerUffRichiedente(String nomeIncaricatoPrelievoPerUffRichiedente) {
		this.nomeIncaricatoPrelievoPerUffRichiedente = nomeIncaricatoPrelievoPerUffRichiedente;
	}
	
	public String getTelefonoIncaricatoPrelievoPerUffRichiedente() {
		return telefonoIncaricatoPrelievoPerUffRichiedente;
	}
	
	public void setTelefonoIncaricatoPrelievoPerUffRichiedente(String telefonoIncaricatoPrelievoPerUffRichiedente) {
		this.telefonoIncaricatoPrelievoPerUffRichiedente = telefonoIncaricatoPrelievoPerUffRichiedente;
	}
	
	public String getCognomeIncaricatoPrelievoPerRichEsterno() {
		return cognomeIncaricatoPrelievoPerRichEsterno;
	}
	
	public void setCognomeIncaricatoPrelievoPerRichEsterno(String cognomeIncaricatoPrelievoPerRichEsterno) {
		this.cognomeIncaricatoPrelievoPerRichEsterno = cognomeIncaricatoPrelievoPerRichEsterno;
	}
	
	public String getNomeIncaricatoPrelievoPerRichEsterno() {
		return nomeIncaricatoPrelievoPerRichEsterno;
	}
	
	public void setNomeIncaricatoPrelievoPerRichEsterno(String nomeIncaricatoPrelievoPerRichEsterno) {
		this.nomeIncaricatoPrelievoPerRichEsterno = nomeIncaricatoPrelievoPerRichEsterno;
	}
	
	public String getCodiceFiscaleIncaricatoPrelievoPerRichEsterno() {
		return codiceFiscaleIncaricatoPrelievoPerRichEsterno;
	}
	
	public void setCodiceFiscaleIncaricatoPrelievoPerRichEsterno(String codiceFiscaleIncaricatoPrelievoPerRichEsterno) {
		this.codiceFiscaleIncaricatoPrelievoPerRichEsterno = codiceFiscaleIncaricatoPrelievoPerRichEsterno;
	}
	
	public String getEmailIncaricatoPrelievoPerRichEsterno() {
		return emailIncaricatoPrelievoPerRichEsterno;
	}
	
	public void setEmailIncaricatoPrelievoPerRichEsterno(String emailIncaricatoPrelievoPerRichEsterno) {
		this.emailIncaricatoPrelievoPerRichEsterno = emailIncaricatoPrelievoPerRichEsterno;
	}
	
	public String getTelefonoIncaricatoPrelievoPerRichEsterno() {
		return telefonoIncaricatoPrelievoPerRichEsterno;
	}
	
	public void setTelefonoIncaricatoPrelievoPerRichEsterno(String telefonoIncaricatoPrelievoPerRichEsterno) {
		this.telefonoIncaricatoPrelievoPerRichEsterno = telefonoIncaricatoPrelievoPerRichEsterno;
	}
	
	public String getDataAppuntamento() {
		return dataAppuntamento;
	}
	
	public void setDataAppuntamento(String dataAppuntamento) {
		this.dataAppuntamento = dataAppuntamento;
	}
	
	public String getOrarioAppuntamento() {
		return orarioAppuntamento;
	}
	
	public void setOrarioAppuntamento(String orarioAppuntamento) {
		this.orarioAppuntamento = orarioAppuntamento;
	}
	
	public String getProvenienzaAppuntamento() {
		return provenienzaAppuntamento;
	}
	
	public void setProvenienzaAppuntamento(String provenienzaAppuntamento) {
		this.provenienzaAppuntamento = provenienzaAppuntamento;
	}
	
	public Date getDataPrelievo() {
		return dataPrelievo;
	}
	
	public void setDataPrelievo(Date dataPrelievo) {
		this.dataPrelievo = dataPrelievo;
	}
	
	public String getDataRestituzionePrelievo() {
		return dataRestituzionePrelievo;
	}
	
	public void setDataRestituzionePrelievo(String dataRestituzionePrelievo) {
		this.dataRestituzionePrelievo = dataRestituzionePrelievo;
	}
	
	public String getRestituzionePrelievoAttestataDa() {
		return restituzionePrelievoAttestataDa;
	}
	
	public void setRestituzionePrelievoAttestataDa(String restituzionePrelievoAttestataDa) {
		this.restituzionePrelievoAttestataDa = restituzionePrelievoAttestataDa;
	}
	
	public String getIdNodoDefaultRicercaAtti() {
		return idNodoDefaultRicercaAtti;
	}
	
	public void setIdNodoDefaultRicercaAtti(String idNodoDefaultRicercaAtti) {
		this.idNodoDefaultRicercaAtti = idNodoDefaultRicercaAtti;
	}
	
	public List<AllegatoProtocolloBean> getListaDocumentiIstruttoria() {
		return listaDocumentiIstruttoria;
	}
	
	public void setListaDocumentiIstruttoria(List<AllegatoProtocolloBean> listaDocumentiIstruttoria) {
		this.listaDocumentiIstruttoria = listaDocumentiIstruttoria;
	}
	
	public String getMezzoTrasmissione() {
		return mezzoTrasmissione;
	}

	public void setMezzoTrasmissione(String mezzoTrasmissione) {
		this.mezzoTrasmissione = mezzoTrasmissione;
	}

	public Boolean getAbilVisualizzaDettStdProt() {
		return abilVisualizzaDettStdProt;
	}
	
	public void setAbilVisualizzaDettStdProt(Boolean abilVisualizzaDettStdProt) {
		this.abilVisualizzaDettStdProt = abilVisualizzaDettStdProt;
	}
	
	public Boolean getAbilInvioInApprovazione() {
		return abilInvioInApprovazione;
	}
	
	public void setAbilInvioInApprovazione(Boolean abilInvioInApprovazione) {
		this.abilInvioInApprovazione = abilInvioInApprovazione;
	}
	
	public Boolean getAbilApprovazione() {
		return abilApprovazione;
	}
	
	public void setAbilApprovazione(Boolean abilApprovazione) {
		this.abilApprovazione = abilApprovazione;
	}
	
	public Boolean getAbilInvioEsitoVerificaArchivio() {
		return abilInvioEsitoVerificaArchivio;
	}
	
	public void setAbilInvioEsitoVerificaArchivio(Boolean abilInvioEsitoVerificaArchivio) {
		this.abilInvioEsitoVerificaArchivio = abilInvioEsitoVerificaArchivio;
	}
	
	public Boolean getAbilAbilitaAppuntamentoDaAgenda() {
		return abilAbilitaAppuntamentoDaAgenda;
	}
	
	public void setAbilAbilitaAppuntamentoDaAgenda(Boolean abilAbilitaAppuntamentoDaAgenda) {
		this.abilAbilitaAppuntamentoDaAgenda = abilAbilitaAppuntamentoDaAgenda;
	}
	
	public Boolean getAbilSetAppuntamento() {
		return abilSetAppuntamento;
	}
	
	public void setAbilSetAppuntamento(Boolean abilSetAppuntamento) {
		this.abilSetAppuntamento = abilSetAppuntamento;
	}
	
	public Boolean getAbilAnnullamentoAppuntamento() {
		return abilAnnullamentoAppuntamento;
	}
	
	public void setAbilAnnullamentoAppuntamento(Boolean abilAnnullamentoAppuntamento) {
		this.abilAnnullamentoAppuntamento = abilAnnullamentoAppuntamento;
	}
	
	public Boolean getAbilRegistraPrelievo() {
		return abilRegistraPrelievo;
	}
	
	public void setAbilRegistraPrelievo(Boolean abilRegistraPrelievo) {
		this.abilRegistraPrelievo = abilRegistraPrelievo;
	}
	
	public Boolean getAbilRegistraRestituzione() {
		return abilRegistraRestituzione;
	}
	
	public void setAbilRegistraRestituzione(Boolean abilRegistraRestituzione) {
		this.abilRegistraRestituzione = abilRegistraRestituzione;
	}
	
	public Boolean getAbilAnnullamento() {
		return abilAnnullamento;
	}
	
	public void setAbilAnnullamento(Boolean abilAnnullamento) {
		this.abilAnnullamento = abilAnnullamento;
	}
	
	public Boolean getAbilStampaFoglioPrelievo() {
		return abilStampaFoglioPrelievo;
	}
	
	public void setAbilStampaFoglioPrelievo(Boolean abilStampaFoglioPrelievo) {
		this.abilStampaFoglioPrelievo = abilStampaFoglioPrelievo;
	}
	
	public Boolean getAbilRichAccessoAttiChiusura() {
		return abilRichAccessoAttiChiusura;
	}
	
	public void setAbilRichAccessoAttiChiusura(Boolean abilRichAccessoAttiChiusura) {
		this.abilRichAccessoAttiChiusura = abilRichAccessoAttiChiusura;
	}
	
	public Boolean getAbilRichAccessoAttiRiapertura() {
		return abilRichAccessoAttiRiapertura;
	}
	
	public void setAbilRichAccessoAttiRiapertura(Boolean abilRichAccessoAttiRiapertura) {
		this.abilRichAccessoAttiRiapertura = abilRichAccessoAttiRiapertura;
	}
	
	public Boolean getAbilRichAccessoAttiRipristino() {
		return abilRichAccessoAttiRipristino;
	}
	
	public void setAbilRichAccessoAttiRipristino(Boolean abilRichAccessoAttiRipristino) {
		this.abilRichAccessoAttiRipristino = abilRichAccessoAttiRipristino;
	}
	
	public Boolean getPrelievoEffettuato() {
		return prelievoEffettuato;
	}
	
	public void setPrelievoEffettuato(Boolean prelievoEffettuato) {
		this.prelievoEffettuato = prelievoEffettuato;
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

	public String getEstensioneFileDaGenerare() {
		return estensioneFileDaGenerare;
	}

	public void setEstensioneFileDaGenerare(String estensioneFileDaGenerare) {
		this.estensioneFileDaGenerare = estensioneFileDaGenerare;
	}

	public String getNroProtocolloSUE() {
		return nroProtocolloSUE;
	}
	
	public void setNroProtocolloSUE(String nroProtocolloSUE) {
		this.nroProtocolloSUE = nroProtocolloSUE;
	}
	
	public Date getDataProtocolloSUE() {
		return dataProtocolloSUE;
	}

	public void setDataProtocolloSUE(Date dataProtocolloSUE) {
		this.dataProtocolloSUE = dataProtocolloSUE;
	}
	
	public String getAmministrazioneSUE() {
		return amministrazioneSUE;
	}
	
	public void setAmministrazioneSUE(String amministrazioneSUE) {
		this.amministrazioneSUE = amministrazioneSUE;
	}
	
	public String getAooSUE() {
		return aooSUE;
	}
	
	public void setAooSUE(String aooSUE) {
		this.aooSUE = aooSUE;
	}

	public String getTipoEventoSUE() {
		return tipoEventoSUE;
	}

	public void setTipoEventoSUE(String tipoEventoSUE) {
		this.tipoEventoSUE = tipoEventoSUE;
	}

	public String getIdPraticaSUE() {
		return idPraticaSUE;
	}

	public void setIdPraticaSUE(String idPraticaSUE) {
		this.idPraticaSUE = idPraticaSUE;
	}

	public String getCodFiscaleSUE() {
		return codFiscaleSUE;
	}

	public void setCodFiscaleSUE(String codFiscaleSUE) {
		this.codFiscaleSUE = codFiscaleSUE;
	}

	public String getGiorniSospensioneSUE() {
		return giorniSospensioneSUE;
	}

	public void setGiorniSospensioneSUE(String giorniSospensioneSUE) {
		this.giorniSospensioneSUE = giorniSospensioneSUE;
	}

	public Boolean getFlgPubblicoSUE() {
		return flgPubblicoSUE;
	}

	public void setFlgPubblicoSUE(Boolean flgPubblicoSUE) {
		this.flgPubblicoSUE = flgPubblicoSUE;
	}

	public List<DestinatarioSUEBean> getDestinatariSUE() {
		return destinatariSUE;
	}

	public void setDestinatariSUE(List<DestinatarioSUEBean> destinatariSUE) {
		this.destinatariSUE = destinatariSUE;
	}
	
	public List<FileDaPubblicareSUEBean> getFileDaPubblicareSUE() {
		return fileDaPubblicareSUE;
	}
	
	public void setFileDaPubblicareSUE(List<FileDaPubblicareSUEBean> fileDaPubblicareSUE) {
		this.fileDaPubblicareSUE = fileDaPubblicareSUE;
	}

	public String getIdTipoDocAllegatoSUE() {
		return idTipoDocAllegatoSUE;
	}

	public void setIdTipoDocAllegatoSUE(String idTipoDocAllegatoSUE) {
		this.idTipoDocAllegatoSUE = idTipoDocAllegatoSUE;
	}

	public String getIdDocAllegatoSUE() {
		return idDocAllegatoSUE;
	}

	public void setIdDocAllegatoSUE(String idDocAllegatoSUE) {
		this.idDocAllegatoSUE = idDocAllegatoSUE;
	}

	public String getIdTipoDocToProt() {
		return idTipoDocToProt;
	}

	public void setIdTipoDocToProt(String idTipoDocToProt) {
		this.idTipoDocToProt = idTipoDocToProt;
	}

	public String getIdDocToProt() {
		return idDocToProt;
	}

	public void setIdDocToProt(String idDocToProt) {
		this.idDocToProt = idDocToProt;
	}
			
}
