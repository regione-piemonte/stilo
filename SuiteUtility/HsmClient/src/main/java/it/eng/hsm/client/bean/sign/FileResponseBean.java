/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
