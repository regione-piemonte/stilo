/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.annotation.Attachment;

@XmlRootElement
public class AurigaAttachmentMailToSendBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String filename;
	
	private byte[] content;
	
	@Attachment
	private File file;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] attachment) {
		this.content = attachment;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}