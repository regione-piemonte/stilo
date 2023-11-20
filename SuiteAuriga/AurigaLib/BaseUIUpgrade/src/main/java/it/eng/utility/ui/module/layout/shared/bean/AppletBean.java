/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class AppletBean {

	private String uri;
	private String fileName;
	private String uriTif;
	private String fileNameTif;
	private String provenienza;
	private MimeTypeFirmaBean mimeTypeFirmaBean;
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setMimeTypeFirmaBean(MimeTypeFirmaBean mimeTypeFirmaBean) {
		this.mimeTypeFirmaBean = mimeTypeFirmaBean;
	}
	public MimeTypeFirmaBean getMimeTypeFirmaBean() {
		return mimeTypeFirmaBean;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	public String getProvenienza() {
		return provenienza;
	}
	public String getUriTif() {
		return uriTif;
	}
	public void setUriTif(String uriTif) {
		this.uriTif = uriTif;
	}
	public String getFileNameTif() {
		return fileNameTif;
	}
	public void setFileNameTif(String fileNameTif) {
		this.fileNameTif = fileNameTif;
	}
	
	
}
