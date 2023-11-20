/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "richiestaLogin")
public class ExternalApplicationLoginRequest {

	@XmlElement(name = "applicazione", required = true)
	@ApiModelProperty(required = true, example = "FATTUBI_BAN", value = "Codice dell'applicazione")
	private String application;

	@XmlElement(name = "istanzaApplicazione")
	@ApiModelProperty(example = "PROD", allowableValues = "PROD, TEST", value = "Codice dell'istanza di applicazione")
	private String applicationInstance;

	@XmlElement(name = "username", required = true)
	@ApiModelProperty(required = true, example = "FATTUBI_BAN", value = "Username dell'utente")
	private String username;

	@XmlElement(name = "password", required = true)
	@ApiModelProperty(required = true, value = "Password dell'utente")
	private String password;

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getApplicationInstance() {
		return applicationInstance;
	}

	public void setApplicationInstance(String applicationInstance) {
		this.applicationInstance = applicationInstance;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
