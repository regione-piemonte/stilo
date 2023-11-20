/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;
import it.eng.document.NumeroColonna;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "casellaEmail")
public class RigaCaselleInvRic {

	@NumeroColonna(numero = "1")
	@XmlElement(name = "idCasella")
	@ApiModelProperty(example = "bbb4b1e1-d070-41af-b0fd-1973f8cc0ffd")
	private String accountId;

	@NumeroColonna(numero = "2")
	@XmlElement(name = "indirizzoEmail")
	@ApiModelProperty(example = "nomeutente@dominio")
	private String emailAddress;

	@NumeroColonna(numero = "3")
	@ApiModelProperty(example = "PEC")
	@XmlElement(name = "tipoEmail")
	private String emailType;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

}
