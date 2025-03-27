/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.module.layout.shared.bean;

import java.util.List;

public class AppletMultiplaBean {

	private List<FileDaFirmareBean> files;
	private String provenienza;
	
	public List<FileDaFirmareBean> getFiles() {
		return files;
	}

	public void setFiles(List<FileDaFirmareBean> files) {
		this.files = files;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	
}
