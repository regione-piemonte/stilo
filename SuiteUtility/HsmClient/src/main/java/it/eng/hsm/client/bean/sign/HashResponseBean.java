package it.eng.hsm.client.bean.sign;

import it.eng.hsm.client.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;

public class HashResponseBean {
	
	private byte[] hashFirmata;
	private MessageBean message;
	
	public byte[] getHashFirmata() {
		return hashFirmata;
	}
	public void setHashFirmata(byte[] hashFirmata) {
		this.hashFirmata = hashFirmata;
	}
	public MessageBean getMessage() {
		return message;
	}
	public void setMessage(MessageBean message) {
		this.message = message;
	}
	
}
