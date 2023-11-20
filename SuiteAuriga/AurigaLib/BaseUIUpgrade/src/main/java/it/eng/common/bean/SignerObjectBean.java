/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.common.type.SignerType;

import java.io.Serializable;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignerObjectBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4913957259468551466L;
	
	public byte[] digestAlgs;
	public byte[] signerInfo;
	public List<X509Certificate> certificates = new ArrayList<X509Certificate>();
	public SignerType fileType;
	public String fileName;
	public String id;
	public byte[] cert;
	public byte[] crl;
	public boolean marca = false;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public byte[] getCert() {
		return cert;
	}
	public void setCert(byte[] cert) {
		this.cert = cert;
	}
	public byte[] getCrl() {
		return crl;
	}
	public void setCrl(byte[] crl) {
		this.crl = crl;
	}

	
	
	public byte[] getDigestAlgs() {
		return digestAlgs;
	}
	public void setDigestAlgs(byte[] digestAlgs) {
		this.digestAlgs = digestAlgs;
	}
	public byte[] getSignerInfo() {
		return signerInfo;
	}
	public void setSignerInfo(byte[] signerInfo) {
		this.signerInfo = signerInfo;
	}
	public List<X509Certificate> getCertificates() {
		return certificates;
	}
	public void setCertificates(List<X509Certificate> certificates) {
		this.certificates = certificates;
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
	
	public boolean isMarca() {
		return marca;
	}
	public void setMarca(boolean marca) {
		this.marca = marca;
	}
	
	@Override
	public String toString() {
		return "SignerObjectBean [digestAlgs=" + Arrays.toString(digestAlgs)
				+ ", signerInfo=" + Arrays.toString(signerInfo)
				+ ", certificates=" + certificates + ", fileName=" + fileName
				+ ", id=" + id + ", cert=" + Arrays.toString(cert) + ", crl="
				+ Arrays.toString(crl) + "]";
	}
	
	
}
