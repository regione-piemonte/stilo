package it.eng.hsm.client.bean.sign;

import it.eng.hsm.client.bean.MessageBean;

public class FileResponseBean {

	private MessageBean message;
	private byte[] fileFirmato;
	
	public MessageBean getMessage() {
		return message;
	}
	public void setMessage(MessageBean message) {
		this.message = message;
	}
	public byte[] getFileFirmato() {
		return fileFirmato;
	}
	public void setFileFirmato(byte[] fileFirmato) {
		this.fileFirmato = fileFirmato;
	}
	
}
