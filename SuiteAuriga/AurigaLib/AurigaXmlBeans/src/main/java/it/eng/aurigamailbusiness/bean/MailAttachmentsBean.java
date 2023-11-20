/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import it.eng.core.annotation.Attachment;
import it.eng.core.business.beans.AbstractBean;

/**
 * Bean che indica l'attachments sulla mail
 * 
 * 
 */
@XmlRootElement
public class MailAttachmentsBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -1206659064167411605L;

	private String messageid;
	private String filename;
	private String mimetype;
	private String disposition;

	private Long size;

	@XmlTransient
	@Attachment
	private File file;
	private String uriFile;

	@XmlTransient
	private byte[] data;

	private String encodedHash;
	private String encoding;
	private String algoritmo;

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getUriFile() {
		return uriFile;
	}

	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public String getEncodedHash() {
		return encodedHash;
	}

	public void setEncodedHash(String encodedHash) {
		this.encodedHash = encodedHash;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

}
