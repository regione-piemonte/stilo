/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.EmailMessage;
import it.eng.aurigamailbusiness.bean.restrepresentation.RecipientsAddresses;

@XmlRootElement(name = "richiestaSpedisciESalvaEmail")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "Richiesta per l'invio email")
public class SendAndSaveNewRequest {

	@XmlElement(name = "messaggioEmail", required = true)
	@ApiModelProperty(name = "messaggioEmail", required = true)
	private EmailMessage emailMessage;

	@XmlElement(name = "destinatari", required = true)
	@ApiModelProperty(name = "destinatari", required = true, value = "Indirizzi di posta elettronica dei destinatari dell'email.")
	private RecipientsAddresses recipientsAddresses;

	@XmlElement(name = "htmlFlag", defaultValue = "true")
	@ApiModelProperty(example = "true", value = "Se false il testo indicato è inviato come semplice testo.")
	private boolean html = true;

	@XmlElement(name = "confermaDiLetturaFlag", defaultValue = "false")
	@ApiModelProperty(example = "false", value = "Se true si richiede una conferma dell'avvenuta apertura del messaggio.")
	private boolean notificationReading;

	@XmlElement(name = "invioSeparatoFlag", defaultValue = "false")
	@ApiModelProperty(example = "false", value = "Se true l'email viene inviata a gruppi di destinatari.")
	private boolean separateMailing;

	public EmailMessage getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(EmailMessage emailMessage) {
		this.emailMessage = emailMessage;
	}

	public RecipientsAddresses getRecipientsAddresses() {
		return recipientsAddresses;
	}

	public void setRecipientsAddresses(RecipientsAddresses recipientsAddresses) {
		this.recipientsAddresses = recipientsAddresses;
	}

	public boolean isSeparateMailing() {
		return separateMailing;
	}

	public boolean getSeparateMailing() {
		return separateMailing;
	}

	public void setSeparateMailing(boolean separateMailing) {
		this.separateMailing = separateMailing;
	}

	public boolean isHtml() {
		return html;
	}

	public boolean getHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

	public boolean isNotificationReading() {
		return notificationReading;
	}

	public boolean getNotificationReading() {
		return notificationReading;
	}

	public void setNotificationReading(boolean notificationReading) {
		this.notificationReading = notificationReading;
	}

}
