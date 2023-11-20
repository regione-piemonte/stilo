/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rispostaLogin")
public class ExternalApplicationLoginResponse {

	@XmlElement(name = "token", required = true)
	@ApiModelProperty(required = true, example = "A68795DF77842BA3E0530C941BA17F06260520200824003349569", value = "Il token di avvenuta autenticazione")
	private String token;

	@XmlElement(name = "descrizioneUtente")
	@ApiModelProperty(example = "Banca Popolare Di Ancona | Fatturazione", value = "Descrizione dell'utente (autenticato)")
	private String userDescription;

	@XmlElement(name = "idDominio")
	@ApiModelProperty(example = "52", value = "Identificativo del dominio di autenticazione")
	private Long domainId;

	@XmlElement(name = "descrizioneDominio")
	@ApiModelProperty(value = "Descrizione del dominio di autenticazione")
	private String domainDescription;

	/*
	 * @XmlElement(name = "tipoDominio")
	 * 
	 * @ApiModelProperty(value = "Tipo del dominio di autenticazione") private String domainType;
	 */

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public Long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	public String getDomainDescription() {
		return domainDescription;
	}

	public void setDomainDescription(String domainDescription) {
		this.domainDescription = domainDescription;
	}

}
