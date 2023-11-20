/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBDocumentoWSDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private java.lang.String titolo;

	private java.lang.String url;

	public AlboAVBDocumentoWSDTO() {

	}

	public AlboAVBDocumentoWSDTO(String titolo, String url) {

		this.titolo = titolo;
		this.url = url;
	}

	public java.lang.String getTitolo() {
		return titolo;
	}

	public void setTitolo(java.lang.String titolo) {
		this.titolo = titolo;
	}

	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}


}
