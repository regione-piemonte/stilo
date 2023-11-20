/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "contenuto")
public class Contenuto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1419994270715501979L;

	private Header header;
	
	private List<String> contenuti;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public List<String> getContenuti() {
		return contenuti;
	}

	public void setContenuti(List<String> contenuti) {
		this.contenuti = contenuti;
	}
	
}
