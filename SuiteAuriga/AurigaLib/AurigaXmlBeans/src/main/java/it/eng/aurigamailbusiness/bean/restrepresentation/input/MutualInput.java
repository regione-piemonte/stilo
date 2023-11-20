/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class MutualInput {
	
	@XmlElement(name="token"/*, required=true*/)
	protected String token;
	
	@XmlTransient
	@ApiModelProperty(hidden=true)
	protected String idUtenteModPec;
	
	@XmlElement(name="idUtente")
	protected Integer idUtente;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public String getIdUtenteModPec() {
		return idUtenteModPec;
	}
	public void setIdUtenteModPec(String idUtenteModPec) {
		this.idUtenteModPec = idUtenteModPec;
	}

	public Integer getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}


}//MutualInput
