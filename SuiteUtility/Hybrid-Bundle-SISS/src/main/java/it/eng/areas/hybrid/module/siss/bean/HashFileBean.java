package it.eng.areas.hybrid.module.siss.bean; 

import it.eng.areas.hybrid.module.siss.util.SignerType;

import java.io.Serializable;
import java.util.Arrays;

public class HashFileBean implements Serializable {

	private String fileName;
	private String tempFilePath;
	private String signedFileName;
	private String id;
	private String versione;
	private SignerType fileType;
	private byte[] hash;
	private String signedBean;
	private byte[] hashPdf;
	private String firmatario;
	private int status = 0;
	
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
	
	public String getSignedFileName() {
		return signedFileName;
	}
	public void setSignedFileName(String signedFileName) {
		this.signedFileName = signedFileName;
	}
	
	public String getSignedBean() {
		return signedBean;
	}
	public void setSignedBean(String signedBean) {
		this.signedBean = signedBean;
	}
	public String getFirmatario() {
		return firmatario;
	}
	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}
	public String getTempFilePath() {
		return tempFilePath;
	}
	public void setTempFilePath(String tempFilePath) {
		this.tempFilePath = tempFilePath;
	}
	public String getVersione() {
		return versione;
	}
	public void setVersione(String versione) {
		this.versione = versione;
	}
	@Override
	public String toString() {
		return "HashFileBean [fileName=" + fileName + ", tempFilePath="
				+ tempFilePath + ", signedFileName=" + signedFileName + ", id="
				+ id + ", versione=" + versione + ", fileType=" + fileType
				+ ", hash=" + Arrays.toString(hash) + ", signedBean="
				+ signedBean + ", hashPdf=" + Arrays.toString(hashPdf)
				+ ", firmatario=" + firmatario + ", status=" + status + "]";
	}
	
	
	
}