 package it.eng.common.bean;

import it.eng.common.type.SignerType;

import java.io.Serializable;
import java.util.Arrays;

public class HashFileBean implements Serializable {

	private String fileName;
	private String id;
	private SignerType fileType;
	private byte[] hash;
	private byte[] hashSbustato;
	private byte[] hashPdf;
	private int status = 0;
	private String idSmartCard = "";

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public byte[] getHashPdf() {
		return hashPdf;
	}
	public void setHashPdf(byte[] hashPdf) {
		this.hashPdf = hashPdf;
	}
	public SignerType getFileType() {
		return fileType;
	}
	public void setFileType(SignerType fileType) {
		this.fileType = fileType;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getHash() {
		return hash;
	}
	public void setHash(byte[] hash) {
		this.hash = hash;
	}
	public byte[] getHashSbustato() {
		return hashSbustato;
	}
	public void setHashSbustato(byte[] hashSbustato) {
		this.hashSbustato = hashSbustato;
	}
	public void setIdSmartCard(String idSmartCard) {
		this.idSmartCard = idSmartCard;
	}
	public String getIdSmartCard() {
		return idSmartCard;
	}

	@Override
	public String toString() {
		return "HashFileBean [idSmartCard =" + idSmartCard  + ", fileName=" + fileName
				+ ", id=" + id + ", fileType=" + fileType
				+ ", hash=" + Arrays.toString(hash)
				+ ", hashPdf=" + Arrays.toString(hashPdf)
				+ ", status=" + status + "]";
	}

}