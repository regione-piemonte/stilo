/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sbloccoLimiteRichiesteResponse")
public class SbloccoLimiteRichiesteResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement(required = true)
	private String presaInCarico;
	private String errorMessage;

	public String getPresaInCarico() {
		return presaInCarico;
	}

	public void setPresaInCarico(String presaInCarico) {
		this.presaInCarico = presaInCarico;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
