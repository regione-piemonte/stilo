/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import java.util.List;

import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;

public class TaskFileDaFirmareBean {

	private List<FileDaFirmareBean> files;

	public List<FileDaFirmareBean> getFiles() {
		return files;
	}

	public void setFiles(List<FileDaFirmareBean> files) {
		this.files = files;
	}
}
