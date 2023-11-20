/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileExtractedIn implements Serializable{

	private String uri;

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}
	
	
}
