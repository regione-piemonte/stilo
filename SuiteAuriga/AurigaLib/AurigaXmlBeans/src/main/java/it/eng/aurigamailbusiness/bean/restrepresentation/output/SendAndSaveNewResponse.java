/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.EmailReferences;
import it.eng.aurigamailbusiness.bean.restrepresentation.FileInfo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rispostaSpedisciESalvaEmail")
@ApiModel(description = "Risposta all'invio email")
public class SendAndSaveNewResponse {

	@XmlElement(name = "emailElaborata")
	@ApiModelProperty(value = "Email processate nel sistema.")
	private List<EmailReferences> list = new ArrayList<EmailReferences>(0);

	@XmlElement(name = "allegato")
	@ApiModelProperty(value = "File allegati all'email.")
	private List<FileInfo> attachments = new ArrayList<FileInfo>(0);

	@XmlElement(name = "dimensioneAllegati")
	@ApiModelProperty(example = "821,77 KB", value = "Dimensione totale di tutti i file allegati all'email.")
	private String attachmentsSize;

	@XmlElement(name = "idUtenteModPec")
	private String idUtenteModPec;

	@XmlElement(name = "mittentePECFlag")
	@ApiModelProperty(example = "true", value = "Se true la casella di posta del mittente è PEC.")
	private Boolean mittentePEC;

	@XmlElement(name = "confermaDiLetturaFlag")
	@ApiModelProperty(example = "true", value = "Se true è stata richiesta una conferma dell'avvenuta apertura del messaggio.")
	private Boolean notificationReading;

	public List<EmailReferences> getList() {
		if (list == null) {
			list = new ArrayList<EmailReferences>();
		}
		return list;
	}

	public void setList(List<EmailReferences> list) {
		this.list = list;
	}

	public String getIdUtenteModPec() {
		return idUtenteModPec;
	}

	public void setIdUtenteModPec(String idUtenteModPec) {
		this.idUtenteModPec = idUtenteModPec;
	}

	public Boolean isMittentePEC() {
		return mittentePEC;
	}

	public Boolean getMittentePEC() {
		return mittentePEC;
	}

	public void setMittentePEC(Boolean mittentePEC) {
		this.mittentePEC = mittentePEC;
	}

	public Boolean isNotificationReading() {
		return notificationReading;
	}

	public Boolean getNotificationReading() {
		return notificationReading;
	}

	public void setNotificationReading(Boolean notificationReading) {
		this.notificationReading = notificationReading;
	}

	public String getAttachmentsSize() {
		return attachmentsSize;
	}

	public void setAttachmentsSize(String attachmentsSize) {
		this.attachmentsSize = attachmentsSize;
	}

	public List<FileInfo> getAttachments() {
		if (attachments == null) {
			attachments = new ArrayList<FileInfo>();
		}
		return attachments;
	}

	public void setAttachments(List<FileInfo> attachments) {
		this.attachments = attachments;
	}

}
