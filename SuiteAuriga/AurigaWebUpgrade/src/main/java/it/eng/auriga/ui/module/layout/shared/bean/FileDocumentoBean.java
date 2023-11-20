/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class FileDocumentoBean {
	
	private String idDoc;
	private String displayFilename;
	private String uri;
	private MimeTypeFirmaBean info;
	
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}	
	public String getDisplayFilename() {
		return displayFilename;
	}
	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public MimeTypeFirmaBean getInfo() {
		return info;
	}
	public void setInfo(MimeTypeFirmaBean info) {
		this.info = info;
	}
	
}