package it.eng.hsm.client.bean.sign;

/**
 * 
 * @author DANCRIST
 *
 */

public class CertificateVerifyBean {
	
    protected byte[] certificate;
    protected String checkTime;
    protected String invalidCertificateMessage;
    protected String invalidCertificateCode;
    protected Boolean trustedIdentity;
    protected String untrustedIdentityMessage;
    protected String untrustedIdentityCode;
    protected Boolean validCertificate;
    
	public byte[] getCertificate() {
		return certificate;
	}
	public void setCertificate(byte[] certificate) {
		this.certificate = certificate;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getInvalidCertificateMessage() {
		return invalidCertificateMessage;
	}
	public void setInvalidCertificateMessage(String invalidCertificateMessage) {
		this.invalidCertificateMessage = invalidCertificateMessage;
	}
	public String getInvalidCertificateCode() {
		return invalidCertificateCode;
	}
	public void setInvalidCertificateCode(String invalidCertificateCode) {
		this.invalidCertificateCode = invalidCertificateCode;
	}
	public Boolean getTrustedIdentity() {
		return trustedIdentity;
	}
	public void setTrustedIdentity(Boolean trustedIdentity) {
		this.trustedIdentity = trustedIdentity;
	}
	public String getUntrustedIdentityMessage() {
		return untrustedIdentityMessage;
	}
	public void setUntrustedIdentityMessage(String untrustedIdentityMessage) {
		this.untrustedIdentityMessage = untrustedIdentityMessage;
	}
	public String getUntrustedIdentityCode() {
		return untrustedIdentityCode;
	}
	public void setUntrustedIdentityCode(String untrustedIdentityCode) {
		this.untrustedIdentityCode = untrustedIdentityCode;
	}
	public Boolean getValidCertificate() {
		return validCertificate;
	}
	public void setValidCertificate(Boolean validCertificate) {
		this.validCertificate = validCertificate;
	}

}
