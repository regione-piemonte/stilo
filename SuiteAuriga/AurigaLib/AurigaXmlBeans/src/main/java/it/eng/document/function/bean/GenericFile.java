/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GenericFile implements Serializable{

	private String displayFilename;
	private String impronta;
	private String improntaFilePreFirma;
	private Flag firmato;
	private Flag firmaNonValida;
	private String mimetype;
	private String idFormato;
	private Flag daScansione;
	private Date dataScansione;
	private String idUserScansione;
	private String algoritmo;
	private String encoding;
	private List<Firmatario> firmatari;
	private Integer nroPagineVersioneDoc;
	private Flag flgTimbroReg;
	private long bytes;
		
	// Busta crittografica
	private Flag flgBustaCrittograficaDocElFattaDaAuriga;
	private Flag flgBustaCrittograficaDocElInComplPassoIter;
	private Date tsVerificaBustaCrittograficaDocEl;
	private Flag flgBustaCrittograficaDocElNonValida;
	private String tipoBustaCrittograficaDocEl;
	private String infoVerificaBustaCrittograficaDocEl;
	
	// Marcatura temporale
	private Date tsMarcaTemporale;
	private Flag flgMarcaTemporaleAppostaDaAuriga;
	private Date tsVerificaMarcaTempDocEl;
	private Flag flgMarcaTemporaleDocElNonValida;
	private String tipoMarcaTemporaleDocEl;
	private String infoVerificaMarcaTemporaleDocEl;
	
	private Flag pdfProtetto;
	private Flag pdfEditabile;
	private Flag pdfConCommenti;
	
	private String idUdScansioneProv;
		
	private Flag convertibile;
	
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
	public Flag getFirmaNonValida() {
		return firmaNonValida;
	}
	public void setFirmaNonValida(Flag firmaNonValida) {
		this.firmaNonValida = firmaNonValida;
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
	public Flag getConvertibile() {
		return convertibile;
	}
	public void setConvertibile(Flag convertibile) {
		this.convertibile = convertibile;
	}
	public long getBytes() {
		return bytes;
	}
	public void setBytes(long bytes) {
		this.bytes = bytes;
	}
}
