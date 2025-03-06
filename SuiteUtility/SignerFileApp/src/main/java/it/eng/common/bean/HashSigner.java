package it.eng.common.bean;

import it.eng.client.applet.operation.jsignpdf.HashAlgorithm;

import it.eng.common.type.SignerType;

public class HashSigner {

	private SignerType type;
	private HashAlgorithm digest;
	private byte[] hash;
	
	public SignerType getType() {
		return type;
	}
	public void setType(SignerType type) {
		this.type = type;
	}
	public HashAlgorithm getDigest() {
		return digest;
	}
	public void setDigest(HashAlgorithm digest) {
		this.digest = digest;
	}
	public byte[] getHash() {
		return hash;
	}
	public void setHash(byte[] hash) {
		this.hash = hash;
	}
	
	
}
