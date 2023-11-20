/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.servlet.piechart.ReportResultBean;

import java.util.List;

public class DatasetBean {
	
	private String title;
	private List<ReportResultBean> dataset;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ReportResultBean> getDataset() {
		return dataset;
	}
	public void setDataset(List<ReportResultBean> dataset) {
		this.dataset = dataset;
	}
	
	
}
	
