package it.eng.common.bean;

import it.eng.common.type.SignerType;


public class SignerFileObject {

	private byte[] signerdata;
	private SignerType type;
	
	public byte[] getSignerdata() {
		return signerdata;
	}
	public void setSignerdata(byte[] signerdata) {
		this.signerdata = signerdata;
	}
	public SignerType getType() {
		return type;
	}
	public void setType(SignerType type) {
		this.type = type;
	}
}