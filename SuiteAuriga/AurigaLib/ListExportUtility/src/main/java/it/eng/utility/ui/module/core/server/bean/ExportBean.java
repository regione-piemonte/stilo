/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Map;

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

/**
 * Definisce le informazioni necessarie alla generazione dell'esportazione di
 * una lista
 *
 */
public class ExportBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeEntita;
	private String formatoExport;
	private String csvSeparator;
	private AdvancedCriteria criteria;
	private String[] fields;
	private String[] headers;
	private Map[] records;
	private String tempFileOut;
	private MimeTypeFirmaBean infoFileOut;
	private boolean overflow;
	private boolean paginazioneAttiva;

	// imposto un valore di default in maniera tale che gli header vengano
	// sempre visualizzati, se non viene impostato volontariamente un valore
	private boolean showHeaders = Boolean.TRUE;
	
	// il file temporaneo per scaricare l'export viene salvato con StorageUtil 
	// se settato a false il file temporaneo viene scritto lato GUI nella cartella temp di Java
	private boolean saveTempFileInStorage = Boolean.TRUE;

	// id del job di esportazione asincrona
	private Integer idAsyncJob;

	// titolo del report utilizzato nelle statistiche dei protocolli e documenti
	private String intestazioneReport;
	
	public String getNomeEntita() {
		return nomeEntita;
	}

	public void setNomeEntita(String nomeEntita) {
		this.nomeEntita = nomeEntita;
	}

	public String getFormatoExport() {
		return formatoExport;
	}

	public void setFormatoExport(String formatoExport) {
		this.formatoExport = formatoExport;
	}

	public String getCsvSeparator() {
		return csvSeparator;
	}

	public void setCsvSeparator(String csvSeparator) {
		this.csvSeparator = csvSeparator;
	}

	public AdvancedCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(AdvancedCriteria criteria) {
		this.criteria = criteria;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	public Map[] getRecords() {
		return records;
	}

	public void setRecords(Map[] records) {
		this.records = records;
	}

	public String getTempFileOut() {
		return tempFileOut;
	}

	public void setTempFileOut(String tempFileOut) {
		this.tempFileOut = tempFileOut;
	}

	public MimeTypeFirmaBean getInfoFileOut() {
		return infoFileOut;
	}

	public void setInfoFileOut(MimeTypeFirmaBean infoFileOut) {
		this.infoFileOut = infoFileOut;
	}

	public boolean isOverflow() {
		return overflow;
	}

	public void setOverflow(boolean overflow) {
		this.overflow = overflow;
	}
	
	public boolean isPaginazioneAttiva() {
		return paginazioneAttiva;
	}

	public void setPaginazioneAttiva(boolean paginazioneAttiva) {
		this.paginazioneAttiva = paginazioneAttiva;
	}

	public boolean showHeaders() {
		return showHeaders;
	}

	public void setShowHeaders(boolean showHeaders) {
		this.showHeaders = showHeaders;
	}

	public boolean isSaveTempFileInStorage() {
		return saveTempFileInStorage;
	}

	public void setSaveTempFileInStorage(boolean saveTempFileInStorage) {
		this.saveTempFileInStorage = saveTempFileInStorage;
	}

	public void setIdAsyncJob(Integer integer) {
		this.idAsyncJob = integer;
	}

	public Integer setIdAsyncJob() {
		return this.idAsyncJob;
	}

	public String getIntestazioneReport() {
		return intestazioneReport;
	}

	public void setIntestazioneReport(String intestazioneReport) {
		this.intestazioneReport = intestazioneReport;
	}
	
}