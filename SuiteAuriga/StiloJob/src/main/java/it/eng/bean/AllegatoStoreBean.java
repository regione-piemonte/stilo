/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.FolderCustom;
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
	@XmlVariabile(nome="SEZIONE_PRATICA_Ud", tipo = TipoVariabile.SEMPLICE)
	private String sezionePratica;		
	@XmlVariabile(nome="#@URIVersPrecSuSharePoint_Ver", tipo = TipoVariabile.LISTA)
	private List<UrlVersione> uriVerPrecSuSharePoint_Ver;
	@XmlVariabile(nome="FLG_PARTE_DISPOSITIVO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgParteDispositivo;
	@XmlVariabile(nome="SAVED_IN_TASK_ID_Doc", tipo = TipoVariabile.SEMPLICE)
	private String idTask;
	@XmlVariabile(nome="FLG_NO_PUBBL_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgNoPubbl;
	@XmlVariabile(nome="FLG_PUBBLICAZIONE_SEPARATA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgPubblicaSeparato;
	@XmlVariabile(nome="FLG_DATI_SENSIBILI_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgDatiSensibili;
	@XmlVariabile(nome="FLG_ORIGINALE_CARTACEO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgOriginaleCartaceo;
	@XmlVariabile(nome="FLG_COPIA_SOSTITUTIVA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgCopiaSostitutiva;
	@XmlVariabile(nome = "#@FolderCustom", tipo = TipoVariabile.LISTA)
	private List<FolderCustom> folderCustom;
	@XmlVariabile(nome="#FlgEreditaPermessi", tipo = TipoVariabile.SEMPLICE)
	private String flgEreditaPermessi;
	@XmlVariabile(nome="#IdFolderEreditaPerm", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idFolderEreditaPerm;
	@XmlVariabile(nome="ID_UD_FROM_Doc", tipo = TipoVariabile.SEMPLICE)
	private String idUdFrom;
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
	public String getSezionePratica() {
		return sezionePratica;
	}
	public void setSezionePratica(String sezionePratica) {
		this.sezionePratica = sezionePratica;
	}
	public List<UrlVersione> getUriVerPrecSuSharePoint_Ver() {
		return uriVerPrecSuSharePoint_Ver;
	}
	public void setUriVerPrecSuSharePoint_Ver(List<UrlVersione> uriVerPrecSuSharePoint_Ver) {
		this.uriVerPrecSuSharePoint_Ver = uriVerPrecSuSharePoint_Ver;
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
	public List<FolderCustom> getFolderCustom() {
		return folderCustom;
	}
	public void setFolderCustom(List<FolderCustom> folderCustom) {
		this.folderCustom = folderCustom;
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
	public List<TipoNumerazioneBean> getTipoNumerazioni() {
		return tipoNumerazioni;
	}
	public void setTipoNumerazioni(List<TipoNumerazioneBean> tipoNumerazioni) {
		this.tipoNumerazioni = tipoNumerazioni;
	}
	
}
