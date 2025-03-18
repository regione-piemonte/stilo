/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
