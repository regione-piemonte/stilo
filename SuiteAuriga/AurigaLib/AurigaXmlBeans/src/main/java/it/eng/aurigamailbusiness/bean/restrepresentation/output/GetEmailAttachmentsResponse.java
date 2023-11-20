/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.EmailAttachment;
import it.eng.core.annotation.Attachment;

/*
 * NON togliere assolutamente 'implements Serializable'
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rispostaRecuperaAllegatiEmail")
public class GetEmailAttachmentsResponse implements Serializable {

	private static final long serialVersionUID = -7041884749108500700L;
	
	@XmlElement(name="allegatoEmail")
	private List<EmailAttachment> mailAttachments = new ArrayList<>(0);
	
	@Attachment
	@ApiModelProperty(hidden=false)
	@XmlElement(name="file")
	private List<File> files = new ArrayList<>(0);

	public List<EmailAttachment> getMailAttachments() {
		return mailAttachments;
	}
	public void setMailAttachments(List<EmailAttachment> mailAttachments) {
		this.mailAttachments = mailAttachments;
	}
	
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	
}
