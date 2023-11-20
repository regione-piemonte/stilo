/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.annotation.Obbligatorio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AssegnatarioBean implements Serializable{

	private static final long serialVersionUID = 1321802042732272342L;
	@Obbligatorio
	private AssegnatarioType type;
	@Obbligatorio
	private String idAssegnatario;
	public void setIdAssegnatario(String idAssegnatario) {
		this.idAssegnatario = idAssegnatario;
	}
	public String getIdAssegnatario() {
		return idAssegnatario;
	}
	public void setType(AssegnatarioType type) {
		this.type = type;
	}
	public AssegnatarioType getType() {
		return type;
	}
}
