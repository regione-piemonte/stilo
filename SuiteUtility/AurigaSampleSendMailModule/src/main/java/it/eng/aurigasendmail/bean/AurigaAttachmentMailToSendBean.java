package it.eng.aurigasendmail.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AurigaAttachmentMailToSendBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String filename;
	private byte[] content;
	/**
	 * Nome del file da inviare
	 * @return nome del file da inviare
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * Nome del file da inviare
	 * @param filename nome del file da inviare
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * Content del file
	 * @return content del file
	 */
	public byte[] getContent() {
		return content;
	}
	/**
	 * Content del file
	 * @param attachment content del file
	 */
	public void setContent(byte[] attachment) {
		this.content = attachment;
	}
}
