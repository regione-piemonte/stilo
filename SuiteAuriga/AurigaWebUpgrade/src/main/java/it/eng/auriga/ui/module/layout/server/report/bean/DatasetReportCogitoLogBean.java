/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.servlet.piechart.ReportCogitoLogResultBean;
import java.util.List;

public class DatasetReportCogitoLogBean {
	
	private String title;
	private List<ReportCogitoLogResultBean> dataset;
		
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
	public List<ReportCogitoLogResultBean> getDataset() {
		return dataset;
	}
	public void setDataset(List<ReportCogitoLogResultBean> dataset) {
		this.dataset = dataset;
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
	
