/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean;

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class AttachmentBean {

	private String fileNameAttach;
	private String uriAttach;
	private MimeTypeFirmaBean infoFileAttach;

	public String getFileNameAttach() {
		return fileNameAttach;
	}

	public void setFileNameAttach(String fileNameAttach) {
		this.fileNameAttach = fileNameAttach;
	}

	public String getUriAttach() {
		return uriAttach;
	}

	public void setUriAttach(String uriAttach) {
		this.uriAttach = uriAttach;
	}

	public MimeTypeFirmaBean getInfoFileAttach() {
		return infoFileAttach;
	}

	public void setInfoFileAttach(MimeTypeFirmaBean infoFileAttach) {
		this.infoFileAttach = infoFileAttach;
	}

}
