/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement(name = "RiferimentiEmail")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "Riferimenti all'email elaborata nel sistema.")
public class EmailReferences {

	@XmlElement(name = "destinatari")
	@ApiModelProperty(name = "destinatari", value = "Indirizzi di posta elettronica dei destinatari dell'email.")
	private RecipientsAddresses recipientsAddresses;

	@XmlElement(name = "id")
	@ApiModelProperty(example = "5fdae2e44e4f4e99a0c90df21e35a8dc", value = "Identificativo univoco dell'email nel sistema.")
	private String id;

	@XmlElement(name = "riferimento")
	@ApiModelProperty(example = "[FS@MAILARCHIVIATE2]/2020/4/16/1/20200416151851191203847814591582879", value = "Percorso al file EML nel repository.")
	private String reference;

	@XmlElement(name = "idMessaggio")
	@ApiModelProperty(example = "002201d616e7$5b290640$117b12c0$@dominio.it", value = "Identificativo del messaggio.")
	private String messageId;

	public RecipientsAddresses getRecipientsAddresses() {
		return recipientsAddresses;
	}

	public void setRecipientsAddresses(RecipientsAddresses recipientsAddresses) {
		this.recipientsAddresses = recipientsAddresses;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

}
