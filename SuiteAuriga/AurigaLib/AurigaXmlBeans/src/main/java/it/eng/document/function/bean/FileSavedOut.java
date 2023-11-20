/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileSavedOut implements Serializable {

	private String uri;
	private String errorInSaved;
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getErrorInSaved() {
		return errorInSaved;
	}

	public void setErrorInSaved(String errorInSaved) {
		this.errorInSaved = errorInSaved;
	}
	
}
