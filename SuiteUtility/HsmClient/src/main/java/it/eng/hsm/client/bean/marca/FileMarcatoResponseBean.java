package it.eng.hsm.client.bean.marca;

import it.eng.hsm.client.bean.MessageBean;

public class FileMarcatoResponseBean {

	private MessageBean messageBean;
	private byte[] fileMarcato;
	
	public MessageBean getMessageBean() {
		return messageBean;
	}
	public void setMessageBean(MessageBean messageBean) {
		this.messageBean = messageBean;
	}
	public byte[] getFileMarcato() {
		return fileMarcato;
	}
	public void setFileMarcato(byte[] fileMarcato) {
		this.fileMarcato = fileMarcato;
	}
	
	
}
