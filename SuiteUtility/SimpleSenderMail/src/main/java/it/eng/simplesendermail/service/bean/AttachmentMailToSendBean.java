package it.eng.simplesendermail.service.bean;

public class AttachmentMailToSendBean {

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
