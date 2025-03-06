package it.eng.hsm.client.bean.sign;

import it.eng.hsm.client.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;


public class SignVerifyResponseBean {
	
	private MessageBean message;
	private List<SignatureBean> signatureBean = new ArrayList<SignatureBean>();
	/*
	 * File sbustato
	 */
	private byte[] content;

	public List<SignatureBean> getSignatureBean() {
		return signatureBean;
	}

	public void setSignatureBean(List<SignatureBean> signatureBean) {
		this.signatureBean = signatureBean;
	}

	public MessageBean getMessage() {
		return message;
	}

	public void setMessage(MessageBean message) {
		this.message = message;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
}