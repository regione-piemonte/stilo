/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBAttoError implements Serializable {

	private static final long serialVersionUID = 1L;

	private java.lang.String code;

	private java.lang.String description;

	private java.lang.String idAtto;

	public AlboAVBAttoError() {
	}

	public AlboAVBAttoError(String code, String description, String idAtto) {
		this.code = code;
		this.description = description;
		this.idAtto = idAtto;
	}

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getIdAtto() {
		return idAtto;
	}

	public void setIdAtto(java.lang.String idAtto) {
		this.idAtto = idAtto;
	}
}
