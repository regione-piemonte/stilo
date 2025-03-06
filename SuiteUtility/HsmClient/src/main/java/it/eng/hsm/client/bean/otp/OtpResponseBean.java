package it.eng.hsm.client.bean.otp;

import it.eng.hsm.client.bean.MessageBean;


public class OtpResponseBean {
	
	private MessageBean message;
	
	public MessageBean getMessage() {
		return message;
	}

	public void setMessage(MessageBean message) {
		this.message = message;
	}
	
}
