/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.hsm.client.bean.MessageBean;


public class MarcaResponseBean {
	
	private FileMarcatoResponseBean fileMarcatoResponseBean = new FileMarcatoResponseBean();
	
	private MessageBean message;
	
	public FileMarcatoResponseBean getFileMarcatoResponseBean() {
		return fileMarcatoResponseBean;
	}

	public void setFileMarcatoResponseBean(FileMarcatoResponseBean fileMarcatoResponseBean) {
		this.fileMarcatoResponseBean = fileMarcatoResponseBean;
	}

	public MessageBean getMessage() {
		return message;
	}

	public void setMessage(MessageBean message) {
		this.message = message;
	}
	
}
