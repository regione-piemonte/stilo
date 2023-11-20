/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement(name = "MessaggioEmail")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "Un messaggio email")
public class EmailMessage {

	@XmlElement(name = "mittente", required = true) /* mittente */
	@ApiModelProperty(example = "nomeutente@dominio.it", required = true, value = "Indirizzo di posta elettronica del mittente")
	private String addressFrom;

	@XmlElement(name = "aliasMittente")
	@ApiModelProperty(example = "alias mittente", value = "Nome logico che identifica il mittente")
	private String aliasAddressFrom;

	@XmlElement(name = "oggetto")
	@ApiModelProperty(example = "breve descrizione del contenuto del messaggio", value = "Dovrebbe aiutare il destinatario a capire il contenuto del messaggio")
	private String subject;

	@XmlElement(name = "testo")
	@ApiModelProperty(example = "contenuto del messaggio", value = "Contenuto informativo che il mittente vuol comunicare ai destinatari")
	private String body;

	public String getAddressFrom() {
		return addressFrom;
	}

	public void setAddressFrom(String addressFrom) {
		this.addressFrom = addressFrom;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAliasAddressFrom() {
		return aliasAddressFrom;
	}

	public void setAliasAddressFrom(String aliasAddressFrom) {
		this.aliasAddressFrom = aliasAddressFrom;
	}

}
