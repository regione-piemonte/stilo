/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.report.ReportEmailStoricizzateResultBean;
import it.eng.auriga.ui.module.layout.server.servlet.piechart.ReportDocAvanzatiResultBean;

import java.util.List;

public class DatasetReportDocAvazatiBean {
	
	private String title;
	private List<ReportDocAvanzatiResultBean> dataset;
	private List<ReportEmailStoricizzateResultBean> datasetEmail;
	
	private Integer idJob;	
	private Integer nroRecord;
	private String errorContext;
	private Integer errorCode;
	private String errorMessage;
	 
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ReportDocAvanzatiResultBean> getDataset() {
		return dataset;
	}
	public void setDataset(List<ReportDocAvanzatiResultBean> dataset) {
		this.dataset = dataset;
	}
	public List<ReportEmailStoricizzateResultBean> getDatasetEmail() {
		return datasetEmail;
	}
	public void setDatasetEmail(List<ReportEmailStoricizzateResultBean> datasetEmail) {
		this.datasetEmail = datasetEmail;
	}
	public Integer getIdJob() {
		return idJob;
	}
	public void setIdJob(Integer idJob) {
		this.idJob = idJob;
	}
	public Integer getNroRecord() {
		return nroRecord;
	}
	public void setNroRecord(Integer nroRecord) {
		this.nroRecord = nroRecord;
	}
	public String getErrorContext() {
		return errorContext;
	}
	public void setErrorContext(String errorContext) {
		this.errorContext = errorContext;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
	
