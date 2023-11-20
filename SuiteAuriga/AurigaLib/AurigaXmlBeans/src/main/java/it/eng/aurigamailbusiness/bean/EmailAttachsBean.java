/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.annotation.Attachment;
import it.eng.core.business.beans.AbstractBean;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * riferimento degli attachments
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class EmailAttachsBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -6938539831907003794L;

	private List<MailAttachmentsInfoBean> mailAttachments;
	
	@Attachment
	private List<File> files;

	public List<MailAttachmentsInfoBean> getMailAttachments() {
		return mailAttachments;
	}

	public void setMailAttachments(List<MailAttachmentsInfoBean> mailAttachments) {
		this.mailAttachments = mailAttachments;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<File> getFiles() {
		return files;
	}


}
