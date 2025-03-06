package it.eng.hsm.client.bean.cert;

import java.util.List;

public class CertBean {

	private String certId;
	private String serialNumber;
	private byte[] certValue;
	private String signaturePower;
	private String descrizione;
	List<String> otps;
	
	public CertBean() {
		super();
	}

	public CertBean(String certId, String serialNumber, byte[] certValue) {
		super();
		this.certId = certId;
		this.serialNumber = serialNumber;
		this.certValue = certValue;
	}

	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public byte[] getCertValue() {
		return certValue;
	}

	public void setCertValue(byte[] certValue) {
		this.certValue = certValue;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSignaturePower() {
		return signaturePower;
	}

	public void setSignaturePower(String signaturePower) {
		this.signaturePower = signaturePower;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<String> getOtps() {
		return otps;
	}

	public void setOtps(List<String> otps) {
		this.otps = otps;
	}
	
	
}
