/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.UrlVersione;

import java.util.Date;
import java.util.List;


import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RERFileStoreBean {
	
	@XmlVariabile(nome="#IdUD", tipo = TipoVariabile.SEMPLICE)
	private String idUd;
	@XmlVariabile(nome="#DesNaturaRelVsUD", tipo = TipoVariabile.SEMPLICE)
	private String desNaturaRelVsUD;
	@XmlVariabile(nome="#DesOgg", tipo = TipoVariabile.SEMPLICE)
	private String descrizione;
	@XmlVariabile(nome="#URI_Ver", tipo = TipoVariabile.SEMPLICE)
	private String uri;
	@XmlVariabile(nome="#Dimensione_Ver", tipo = TipoVariabile.SEMPLICE)
	private Long dimensione;
	@XmlVariabile(nome="#DisplayFilename_Ver", tipo = TipoVariabile.SEMPLICE)
	private String displayFilename;
	@XmlVariabile(nome="#Impronta_Ver", tipo = TipoVariabile.SEMPLICE)
	private String impronta;
	@XmlVariabile(nome="#FlgFirmata_Ver", tipo = TipoVariabile.SEMPLICE)
	private Flag firmato;
	@XmlVariabile(nome="#Mimetype_Ver", tipo = TipoVariabile.SEMPLICE)
	private String mimetype;
	@XmlVariabile(nome="#IdFormatoEl_Ver", tipo = TipoVariabile.SEMPLICE)
	private String idFormato;
	@XmlVariabile(nome="#FlgDaScansione_Ver", tipo = TipoVariabile.SEMPLICE)
	private Flag daScansione;
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="#DtScansione_Ver", tipo = TipoVariabile.SEMPLICE)
	private Date dataScansione;
	@XmlVariabile(nome="#IdUserScansione_Ver", tipo = TipoVariabile.SEMPLICE)
	private String idUserScansione;
	@XmlVariabile(nome="#Algoritmo_Impronta_Ver", tipo = TipoVariabile.SEMPLICE)
	private String algoritmo;
	@XmlVariabile(nome="#Encoding_Impronta_Ver", tipo = TipoVariabile.SEMPLICE)
	private String encoding;
	@XmlVariabile(nome="FIRMATARIO_DOC_EL_Ver", tipo = TipoVariabile.LISTA)
	private List<Firmatario> firmatari;
	@XmlVariabile(nome="#@URIVersPrecSuSharePoint_Ver", tipo = TipoVariabile.LISTA)
	private List<UrlVersione> uriVerPrecSuSharePoint_Ver;
	@XmlVariabile(nome = "NRO_PAGINE_Ver", tipo = TipoVariabile.SEMPLICE)
	private Integer nroPagineVersioneDoc;
	@XmlVariabile(nome = "FLG_TIMBRO_REG_Ver", tipo = TipoVariabile.SEMPLICE)
	private Flag flgTimbroReg;
	@XmlVariabile(nome="SAVED_IN_TASK_ID_Ver", tipo = TipoVariabile.SEMPLICE)
	private String idTask;
	@XmlVariabile(nome="#IdDocType", tipo = TipoVariabile.SEMPLICE)
	private String idDocType;
	@XmlVariabile(nome="#NomeDocType", tipo = TipoVariabile.SEMPLICE)
	private String nomeDocType;
	@XmlVariabile(nome="#ProvCIDocType", tipo = TipoVariabile.SEMPLICE)
	private String provCIDocType;   
	@XmlVariabile(nome="#FlgEreditaPermessi", tipo = TipoVariabile.SEMPLICE)
	private String flgEreditaPermessi;
	
	public String getFlgEreditaPermessi() {
		return flgEreditaPermessi;
	}
	public void setFlgEreditaPermessi(String flgEreditaPermessi) {
		this.flgEreditaPermessi = flgEreditaPermessi;
	}
	public String getDisplayFilename() {
		return displayFilename;
	}
	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}
	public String getImpronta() {
		return impronta;
	}
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}
	public Flag getFirmato() {
		return firmato;
	}
	public void setFirmato(Flag firmato) {
		this.firmato = firmato;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getIdFormato() {
		return idFormato;
	}
	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}
	public Flag getDaScansione() {
		return daScansione;
	}
	public void setDaScansione(Flag daScansione) {
		this.daScansione = daScansione;
	}
	public Date getDataScansione() {
		return dataScansione;
	}
	public void setDataScansione(Date dataScansione) {
		this.dataScansione = dataScansione;
	}
	public String getIdUserScansione() {
		return idUserScansione;
	}
	public void setIdUserScansione(String idUserScansione) {
		this.idUserScansione = idUserScansione;
	}
	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}
	public String getAlgoritmo() {
		return algoritmo;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setFirmatari(List<Firmatario> firmatari) {
		this.firmatari = firmatari;
	}
	public List<Firmatario> getFirmatari() {
		return firmatari;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getUri() {
		return uri;
	}
	public void setDimensione(Long dimensione) {
		this.dimensione = dimensione;
	}
	public Long getDimensione() {
		return dimensione;
	}
	public List<UrlVersione> getUriVerPrecSuSharePoint_Ver() {
		return uriVerPrecSuSharePoint_Ver;
	}
	public void setUriVerPrecSuSharePoint_Ver(
			List<UrlVersione> uriVerPrecSuSharePoint_Ver) {
		this.uriVerPrecSuSharePoint_Ver = uriVerPrecSuSharePoint_Ver;
	}
	public Integer getNroPagineVersioneDoc() {
		return nroPagineVersioneDoc;
	}
	public void setNroPagineVersioneDoc(Integer nroPagineVersioneDoc) {
		this.nroPagineVersioneDoc = nroPagineVersioneDoc;
	}
	public Flag getFlgTimbroReg() {
		return flgTimbroReg;
	}
	public void setFlgTimbroReg(Flag flgTimbroReg) {
		this.flgTimbroReg = flgTimbroReg;
	}
	public String getIdTask() {
		return idTask;
	}
	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}
	
	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getDesNaturaRelVsUD() {
		return desNaturaRelVsUD;
	}

	public void setDesNaturaRelVsUD(String desNaturaRelVsUD) {
		this.desNaturaRelVsUD = desNaturaRelVsUD;
	}
	public String getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}
	public String getNomeDocType() {
		return nomeDocType;
	}
	public void setNomeDocType(String nomeDocType) {
		this.nomeDocType = nomeDocType;
	}
	public String getProvCIDocType() {
		return provCIDocType;
	}
	public void setProvCIDocType(String provCIDocType) {
		this.provCIDocType = provCIDocType;
	}

	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
