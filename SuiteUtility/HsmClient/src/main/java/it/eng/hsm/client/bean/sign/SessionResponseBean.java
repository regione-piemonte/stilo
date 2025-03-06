package it.eng.hsm.client.bean.sign;

import it.eng.hsm.client.bean.MessageBean;

public class SessionResponseBean {

	private MessageBean message;
	private String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public MessageBean getMessage() {
		return message;
	}

	public void setMessage(MessageBean message) {
		this.message = message;
	}
	
	
}
