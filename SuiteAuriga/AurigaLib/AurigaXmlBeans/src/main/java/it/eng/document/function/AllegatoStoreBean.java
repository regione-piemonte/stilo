/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.FolderCustom;
import it.eng.document.function.bean.MittentiDocumentoBean;
import it.eng.document.function.bean.RegistroEmergenza;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.UrlVersione;

public class AllegatoStoreBean {
	
	@XmlVariabile(nome="#IdUD", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idUd;
	@XmlVariabile(nome="#CodNaturaRelVsUD", tipo = TipoVariabile.SEMPLICE)
	private String natura = "ALL";
	@XmlVariabile(nome="#DesOgg", tipo = TipoVariabile.SEMPLICE)
	private String descrizione;
	@XmlVariabile(nome="#IdDocType", tipo = TipoVariabile.SEMPLICE)
	private Integer idDocType;	
	@XmlVariabile(nome="#NomeDocType", tipo = TipoVariabile.SEMPLICE)
	private String nomeDocType;	
	@XmlVariabile(nome="SEZIONE_PRATICA_Ud", tipo = TipoVariabile.SEMPLICE)
	private String sezionePratica;
	@XmlVariabile(nome="#@RegistrazioniDate", tipo = TipoVariabile.LISTA)
	private List<RegistroEmergenza> registrazioniDate;
	@XmlVariabile(nome="#@MittentiEsterni", tipo = TipoVariabile.LISTA)
	private List<MittentiDocumentoBean> mittentiEsibenti;
	@XmlVariabile(nome="NRO_DEPOSITO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String nroDeposito;
	@XmlVariabile(nome="ANNO_DEPOSITO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String annoDeposito;	
	@XmlVariabile(nome="FLG_UD_DA_DATA_ENTRY_SCANSIONI_Ud", tipo = TipoVariabile.SEMPLICE)
	private String flgUdDaDataEntryScansioni;
	@XmlVariabile(nome="#@URIVersPrecSuSharePoint_Ver", tipo = TipoVariabile.LISTA)
	private List<UrlVersione> uriVerPrecSuSharePoint_Ver;
	@XmlVariabile(nome="FLG_ALLEG_PROV_ESTERNA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgProvEsterna;
	@XmlVariabile(nome="FLG_PARTE_DISPOSITIVO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgParteDispositivo;
	@XmlVariabile(nome="SAVED_IN_TASK_ID_Doc", tipo = TipoVariabile.SEMPLICE)
	private String idTask;
	@XmlVariabile(nome="FLG_NO_PUBBL_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgNoPubbl;
	@XmlVariabile(nome="FLG_PUBBLICAZIONE_SEPARATA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgPubblicaSeparato;
	@XmlVariabile(nome="UNIONE_DOPO_PAG_NRO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String nroPagFileUnione;
	@XmlVariabile(nome="UNIONE_VER_OMISSIS_DOPO_PAG_NRO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String nroPagFileUnioneOmissis;
	@XmlVariabile(nome="FLG_DATI_PROTETTI_TIPO_1_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgDatiProtettiTipo1;
	@XmlVariabile(nome="FLG_DATI_PROTETTI_TIPO_2_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgDatiProtettiTipo2;
	@XmlVariabile(nome="FLG_DATI_PROTETTI_TIPO_3_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgDatiProtettiTipo3;
	@XmlVariabile(nome="FLG_DATI_PROTETTI_TIPO_4_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgDatiProtettiTipo4;
	@XmlVariabile(nome="FLG_DATI_SENSIBILI_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgDatiSensibili;
	@XmlVariabile(nome="FLG_ORIGINALE_CARTACEO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgOriginaleCartaceo;
	@XmlVariabile(nome="FLG_COPIA_SOSTITUTIVA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgCopiaSostitutiva;
	@XmlVariabile(nome="FLG_GEN_AUTOMATICO_DA_MODELLO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgGenAutoDaModello;
	@XmlVariabile(nome = "#@FolderCustom", tipo = TipoVariabile.LISTA)
	private List<FolderCustom> folderCustom;
	@XmlVariabile(nome="#Append_#@FolderCustom", tipo = TipoVariabile.SEMPLICE)
	private String flgAppendFolderCustom;	
	@XmlVariabile(nome="#FlgEreditaPermessi", tipo = TipoVariabile.SEMPLICE)
	private String flgEreditaPermessi;
	@XmlVariabile(nome="#IdFolderEreditaPerm", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idFolderEreditaPerm;
	@XmlVariabile(nome="ID_UD_FROM_Doc", tipo = TipoVariabile.SEMPLICE)
	private String idUdFrom;
	@XmlVariabile(nome="ID_UD_ALLEGATO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String idUdAllegato;
	@XmlVariabile(nome = "#@NumerazioniDaDare", tipo = TipoVariabile.LISTA)
	private List<TipoNumerazioneBean> tipoNumerazioni;

	public BigDecimal getIdUd() {
		return idUd;
	}
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	public String getNatura() {
		return natura;
	}
	public void setNatura(String natura) {
		this.natura = natura;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Integer getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(Integer idDocType) {
		this.idDocType = idDocType;
	}	
	public String getNomeDocType() {
		return nomeDocType;
	}
	public void setNomeDocType(String nomeDocType) {
		this.nomeDocType = nomeDocType;
	}
	public String getSezionePratica() {
		return sezionePratica;
	}
	public void setSezionePratica(String sezionePratica) {
		this.sezionePratica = sezionePratica;
	}		
	public List<RegistroEmergenza> getRegistrazioniDate() {
		return registrazioniDate;
	}
	public void setRegistrazioniDate(List<RegistroEmergenza> registrazioniDate) {
		this.registrazioniDate = registrazioniDate;
	}
	public List<MittentiDocumentoBean> getMittentiEsibenti() {
		return mittentiEsibenti;
	}
	public void setMittentiEsibenti(List<MittentiDocumentoBean> mittentiEsibenti) {
		this.mittentiEsibenti = mittentiEsibenti;
	}	
	public String getNroDeposito() {
		return nroDeposito;
	}
	public void setNroDeposito(String nroDeposito) {
		this.nroDeposito = nroDeposito;
	}
	public String getAnnoDeposito() {
		return annoDeposito;
	}
	public void setAnnoDeposito(String annoDeposito) {
		this.annoDeposito = annoDeposito;
	}	
	public String getFlgUdDaDataEntryScansioni() {
		return flgUdDaDataEntryScansioni;
	}
	public void setFlgUdDaDataEntryScansioni(String flgUdDaDataEntryScansioni) {
		this.flgUdDaDataEntryScansioni = flgUdDaDataEntryScansioni;
	}
	public List<UrlVersione> getUriVerPrecSuSharePoint_Ver() {
		return uriVerPrecSuSharePoint_Ver;
	}
	public void setUriVerPrecSuSharePoint_Ver(List<UrlVersione> uriVerPrecSuSharePoint_Ver) {
		this.uriVerPrecSuSharePoint_Ver = uriVerPrecSuSharePoint_Ver;
	}
	public String getFlgProvEsterna() {
		return flgProvEsterna;
	}
	public void setFlgProvEsterna(String flgProvEsterna) {
		this.flgProvEsterna = flgProvEsterna;
	}
	public String getFlgParteDispositivo() {
		return flgParteDispositivo;
	}
	public void setFlgParteDispositivo(String flgParteDispositivo) {
		this.flgParteDispositivo = flgParteDispositivo;
	}
	public String getIdTask() {
		return idTask;
	}
	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}
	public String getFlgNoPubbl() {
		return flgNoPubbl;
	}
	public void setFlgNoPubbl(String flgNoPubbl) {
		this.flgNoPubbl = flgNoPubbl;
	}
	public String getFlgPubblicaSeparato() {
		return flgPubblicaSeparato;
	}
	public void setFlgPubblicaSeparato(String flgPubblicaSeparato) {
		this.flgPubblicaSeparato = flgPubblicaSeparato;
	}	
	public String getNroPagFileUnione() {
		return nroPagFileUnione;
	}
	public void setNroPagFileUnione(String nroPagFileUnione) {
		this.nroPagFileUnione = nroPagFileUnione;
	}
	public String getNroPagFileUnioneOmissis() {
		return nroPagFileUnioneOmissis;
	}
	public void setNroPagFileUnioneOmissis(String nroPagFileUnioneOmissis) {
		this.nroPagFileUnioneOmissis = nroPagFileUnioneOmissis;
	}
	public String getFlgDatiProtettiTipo1() {
		return flgDatiProtettiTipo1;
	}
	public void setFlgDatiProtettiTipo1(String flgDatiProtettiTipo1) {
		this.flgDatiProtettiTipo1 = flgDatiProtettiTipo1;
	}
	public String getFlgDatiProtettiTipo2() {
		return flgDatiProtettiTipo2;
	}
	public void setFlgDatiProtettiTipo2(String flgDatiProtettiTipo2) {
		this.flgDatiProtettiTipo2 = flgDatiProtettiTipo2;
	}
	public String getFlgDatiProtettiTipo3() {
		return flgDatiProtettiTipo3;
	}
	public void setFlgDatiProtettiTipo3(String flgDatiProtettiTipo3) {
		this.flgDatiProtettiTipo3 = flgDatiProtettiTipo3;
	}
	public String getFlgDatiProtettiTipo4() {
		return flgDatiProtettiTipo4;
	}
	public void setFlgDatiProtettiTipo4(String flgDatiProtettiTipo4) {
		this.flgDatiProtettiTipo4 = flgDatiProtettiTipo4;
	}
	public String getFlgDatiSensibili() {
		return flgDatiSensibili;
	}
	public void setFlgDatiSensibili(String flgDatiSensibili) {
		this.flgDatiSensibili = flgDatiSensibili;
	}
	public String getFlgOriginaleCartaceo() {
		return flgOriginaleCartaceo;
	}
	public void setFlgOriginaleCartaceo(String flgOriginaleCartaceo) {
		this.flgOriginaleCartaceo = flgOriginaleCartaceo;
	}
	public String getFlgCopiaSostitutiva() {
		return flgCopiaSostitutiva;
	}
	public void setFlgCopiaSostitutiva(String flgCopiaSostitutiva) {
		this.flgCopiaSostitutiva = flgCopiaSostitutiva;
	}
	public String getFlgGenAutoDaModello() {
		return flgGenAutoDaModello;
	}
	public void setFlgGenAutoDaModello(String flgGenAutoDaModello) {
		this.flgGenAutoDaModello = flgGenAutoDaModello;
	}
	public List<FolderCustom> getFolderCustom() {
		return folderCustom;
	}
	public void setFolderCustom(List<FolderCustom> folderCustom) {
		this.folderCustom = folderCustom;
	}	
	public String getFlgAppendFolderCustom() {
		return flgAppendFolderCustom;
	}
	public void setFlgAppendFolderCustom(String flgAppendFolderCustom) {
		this.flgAppendFolderCustom = flgAppendFolderCustom;
	}
	public String getFlgEreditaPermessi() {
		return flgEreditaPermessi;
	}
	public void setFlgEreditaPermessi(String flgEreditaPermessi) {
		this.flgEreditaPermessi = flgEreditaPermessi;
	}
	public BigDecimal getIdFolderEreditaPerm() {
		return idFolderEreditaPerm;
	}
	public void setIdFolderEreditaPerm(BigDecimal idFolderEreditaPerm) {
		this.idFolderEreditaPerm = idFolderEreditaPerm;
	}
	public String getIdUdFrom() {
		return idUdFrom;
	}
	public void setIdUdFrom(String idUdFrom) {
		this.idUdFrom = idUdFrom;
	}
	public String getIdUdAllegato() {
		return idUdAllegato;
	}
	public void setIdUdAllegato(String idUdAllegato) {
		this.idUdAllegato = idUdAllegato;
	}
	public List<TipoNumerazioneBean> getTipoNumerazioni() {
		return tipoNumerazioni;
	}
	public void setTipoNumerazioni(List<TipoNumerazioneBean> tipoNumerazioni) {
		this.tipoNumerazioni = tipoNumerazioni;
	}
	
}
