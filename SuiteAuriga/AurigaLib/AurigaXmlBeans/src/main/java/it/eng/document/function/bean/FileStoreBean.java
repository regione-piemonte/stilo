/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FileStoreBean {
	@XmlVariabile(nome="#URI_Ver", tipo = TipoVariabile.SEMPLICE)
	private String uri;
	@XmlVariabile(nome="#Dimensione_Ver", tipo = TipoVariabile.SEMPLICE)
	private Long dimensione;
	@XmlVariabile(nome="#DisplayFilename_Ver", tipo = TipoVariabile.SEMPLICE)
	private String displayFilename;
	@XmlVariabile(nome="#Impronta_Ver", tipo = TipoVariabile.SEMPLICE)
	private String impronta;
	@XmlVariabile(nome="#ImprontaFilePreFirma_Ver", tipo = TipoVariabile.SEMPLICE)
	private String improntaFilePreFirma;
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
	
	// Busta crittografica
	@XmlVariabile(nome = "FLG_BUSTA_CRITTOGRAFICA_DOC_EL_FATTA_DA_AURIGA_Ver", tipo = TipoVariabile.SEMPLICE)
	private Flag flgBustaCrittograficaDocElFattaDaAuriga;
	@XmlVariabile(nome = "FLG_BUSTA_CRITTOGRAFICA_DOC_EL_IN_COMPL_PASSO_ITER_Ver", tipo = TipoVariabile.SEMPLICE)
	private Flag flgBustaCrittograficaDocElInComplPassoIter;
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "TS_VERIFICA_BUSTA_CRITTOGRAFICA_DOC_EL_Ver", tipo = TipoVariabile.SEMPLICE)
	private Date tsVerificaBustaCrittograficaDocEl;
	@XmlVariabile(nome = "FLG_BUSTA_CRITTOGRAFICA_DOC_EL_NON_VALIDA_Ver", tipo = TipoVariabile.SEMPLICE)
	private Flag flgBustaCrittograficaDocElNonValida;
	@XmlVariabile(nome = "TIPO_BUSTA_CRITTOGRAFICA_DOC_EL_Ver", tipo = TipoVariabile.SEMPLICE)
	private String tipoBustaCrittograficaDocEl;
	@XmlVariabile(nome = "INFO_VERIFICA_BUSTA_CRITTOGRAFICA_DOC_EL_Ver", tipo = TipoVariabile.SEMPLICE)
	private String infoVerificaBustaCrittograficaDocEl;
	
	// Marcatura temporale
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "TS_MARCA_TEMPORALE_Ver", tipo = TipoVariabile.SEMPLICE)
	private Date tsMarcaTemporale;
	@XmlVariabile(nome = "FLG_MARCA_TEMPORALE_APPOSTA_DA_AURIGA_Ver", tipo = TipoVariabile.SEMPLICE)
	private Flag flgMarcaTemporaleAppostaDaAuriga;
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "TS_VERIFICA_MARCA_TEMP_DOC_EL_Ver", tipo = TipoVariabile.SEMPLICE)
	private Date tsVerificaMarcaTempDocEl;
	@XmlVariabile(nome = "FLG_MARCA_TEMPORALE_DOC_EL_NON_VALIDA_Ver", tipo = TipoVariabile.SEMPLICE)
	private Flag flgMarcaTemporaleDocElNonValida;
	@XmlVariabile(nome = "TIPO_MARCA_TEMPORALE_DOC_EL_Ver", tipo = TipoVariabile.SEMPLICE)
	private String tipoMarcaTemporaleDocEl;
	@XmlVariabile(nome = "INFO_VERIFICA_MARCA_TEMPORALE_DOC_EL_Ver", tipo = TipoVariabile.SEMPLICE)
	private String infoVerificaMarcaTemporaleDocEl;
	
	@XmlVariabile(nome="FLG_PDF_PROTETTO_Ver", tipo = TipoVariabile.SEMPLICE)
	private Flag pdfProtetto;
	
	@XmlVariabile(nome="FLG_PDF_EDITABILE_Ver", tipo = TipoVariabile.SEMPLICE)
	private Flag pdfEditabile;
	@XmlVariabile(nome="FLG_PDF_CON_COMMENTI_Ver", tipo = TipoVariabile.SEMPLICE)
	private Flag pdfConCommenti;
	
	@XmlVariabile(nome="ID_UD_SCANSIONE_PROV_Ver", tipo = TipoVariabile.SEMPLICE)
	private String idUdScansioneProv;
	
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
	public String getImprontaFilePreFirma() {
		return improntaFilePreFirma;
	}
	public void setImprontaFilePreFirma(String improntaFilePreFirma) {
		this.improntaFilePreFirma = improntaFilePreFirma;
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
	public void setUriVerPrecSuSharePoint_Ver(List<UrlVersione> uriVerPrecSuSharePoint_Ver) {
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
	public Flag getFlgBustaCrittograficaDocElFattaDaAuriga() {
		return flgBustaCrittograficaDocElFattaDaAuriga;
	}
	public void setFlgBustaCrittograficaDocElFattaDaAuriga(Flag flgBustaCrittograficaDocElFattaDaAuriga) {
		this.flgBustaCrittograficaDocElFattaDaAuriga = flgBustaCrittograficaDocElFattaDaAuriga;
	}
	public Flag getFlgBustaCrittograficaDocElInComplPassoIter() {
		return flgBustaCrittograficaDocElInComplPassoIter;
	}
	public void setFlgBustaCrittograficaDocElInComplPassoIter(Flag flgBustaCrittograficaDocElInComplPassoIter) {
		this.flgBustaCrittograficaDocElInComplPassoIter = flgBustaCrittograficaDocElInComplPassoIter;
	}
	public Date getTsVerificaBustaCrittograficaDocEl() {
		return tsVerificaBustaCrittograficaDocEl;
	}
	public void setTsVerificaBustaCrittograficaDocEl(Date tsVerificaBustaCrittograficaDocEl) {
		this.tsVerificaBustaCrittograficaDocEl = tsVerificaBustaCrittograficaDocEl;
	}
	public Flag getFlgBustaCrittograficaDocElNonValida() {
		return flgBustaCrittograficaDocElNonValida;
	}
	public void setFlgBustaCrittograficaDocElNonValida(Flag flgBustaCrittograficaDocElNonValida) {
		this.flgBustaCrittograficaDocElNonValida = flgBustaCrittograficaDocElNonValida;
	}
	public String getTipoBustaCrittograficaDocEl() {
		return tipoBustaCrittograficaDocEl;
	}
	public void setTipoBustaCrittograficaDocEl(String tipoBustaCrittograficaDocEl) {
		this.tipoBustaCrittograficaDocEl = tipoBustaCrittograficaDocEl;
	}
	public String getInfoVerificaBustaCrittograficaDocEl() {
		return infoVerificaBustaCrittograficaDocEl;
	}
	public void setInfoVerificaBustaCrittograficaDocEl(String infoVerificaBustaCrittograficaDocEl) {
		this.infoVerificaBustaCrittograficaDocEl = infoVerificaBustaCrittograficaDocEl;
	}
	public Date getTsMarcaTemporale() {
		return tsMarcaTemporale;
	}
	public void setTsMarcaTemporale(Date tsMarcaTemporale) {
		this.tsMarcaTemporale = tsMarcaTemporale;
	}
	public Flag getFlgMarcaTemporaleAppostaDaAuriga() {
		return flgMarcaTemporaleAppostaDaAuriga;
	}
	public void setFlgMarcaTemporaleAppostaDaAuriga(Flag flgMarcaTemporaleAppostaDaAuriga) {
		this.flgMarcaTemporaleAppostaDaAuriga = flgMarcaTemporaleAppostaDaAuriga;
	}
	public Date getTsVerificaMarcaTempDocEl() {
		return tsVerificaMarcaTempDocEl;
	}
	public void setTsVerificaMarcaTempDocEl(Date tsVerificaMarcaTempDocEl) {
		this.tsVerificaMarcaTempDocEl = tsVerificaMarcaTempDocEl;
	}
	public Flag getFlgMarcaTemporaleDocElNonValida() {
		return flgMarcaTemporaleDocElNonValida;
	}
	public void setFlgMarcaTemporaleDocElNonValida(Flag flgMarcaTemporaleDocElNonValida) {
		this.flgMarcaTemporaleDocElNonValida = flgMarcaTemporaleDocElNonValida;
	}
	public String getTipoMarcaTemporaleDocEl() {
		return tipoMarcaTemporaleDocEl;
	}
	public void setTipoMarcaTemporaleDocEl(String tipoMarcaTemporaleDocEl) {
		this.tipoMarcaTemporaleDocEl = tipoMarcaTemporaleDocEl;
	}
	public String getInfoVerificaMarcaTemporaleDocEl() {
		return infoVerificaMarcaTemporaleDocEl;
	}	
	public void setInfoVerificaMarcaTemporaleDocEl(String infoVerificaMarcaTemporaleDocEl) {
		this.infoVerificaMarcaTemporaleDocEl = infoVerificaMarcaTemporaleDocEl;
	}
	public Flag getPdfProtetto() {
		return pdfProtetto;
	}
	public void setPdfProtetto(Flag pdfProtetto) {
		this.pdfProtetto = pdfProtetto;
	}
	public Flag getPdfEditabile() {
		return pdfEditabile;
	}
	public Flag getPdfConCommenti() {
		return pdfConCommenti;
	}
	public void setPdfEditabile(Flag pdfEditabile) {
		this.pdfEditabile = pdfEditabile;
	}
	public void setPdfConCommenti(Flag pdfConCommenti) {
		this.pdfConCommenti = pdfConCommenti;
	}
	public String getIdUdScansioneProv() {
		return idUdScansioneProv;
	}
	public void setIdUdScansioneProv(String idUdScansioneProv) {
		this.idUdScansioneProv = idUdScansioneProv;
	}
	
}
