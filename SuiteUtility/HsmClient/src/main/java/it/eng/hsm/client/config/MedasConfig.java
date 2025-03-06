package it.eng.hsm.client.config;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class MedasConfig extends ClientConfig {

	@XmlVariabile(nome = "wsSignConfig", tipo = TipoVariabile.NESTED)
	private WSConfig wsSignConfig;
	@XmlVariabile(nome = "wsOtpConfig", tipo = TipoVariabile.NESTED)
	private WSConfig wsOtpConfig;
	@XmlVariabile(nome = "wsCertConfig", tipo = TipoVariabile.NESTED)
	private WSConfig wsCertConfig;
	@XmlVariabile(nome = "user", tipo = TipoVariabile.SEMPLICE)
	private String user;
	@XmlVariabile(nome = "codiceFiscale", tipo = TipoVariabile.SEMPLICE)
	private String codiceFiscale;
	@XmlVariabile(nome = "password", tipo = TipoVariabile.SEMPLICE)
	private String password;
	@XmlVariabile(nome = "otp", tipo = TipoVariabile.SEMPLICE)
	private String otp;
	@XmlVariabile(nome = "padesConfig", tipo = TipoVariabile.SEMPLICE)
	private PadesConfig padesConfig;
	// Questa variabile non deve essere messa nella configurazione xml
	private TypeOtp typeOtp;
	@XmlVariabile(nome = "padesArss", tipo = TipoVariabile.SEMPLICE)
	private boolean padesArss;
	@XmlVariabile(nome = "padesDs", tipo = TipoVariabile.SEMPLICE)
	private boolean padesDs;
	@XmlVariabile(nome = "processId", tipo = TipoVariabile.SEMPLICE)
	private String processId;
	@XmlVariabile(nome = "certificateSerialNumber", tipo = TipoVariabile.SEMPLICE)
	private String certificateSerialNumber;
	@XmlVariabile(nome = "certificateId", tipo = TipoVariabile.SEMPLICE)
	private String certificateId;
	@XmlVariabile(nome = "signaturePower", tipo = TipoVariabile.SEMPLICE)
	private String signaturePower;
	@XmlVariabile(nome = "certificateDocType", tipo = TipoVariabile.SEMPLICE)
	private String certificateDocType;
	
	public WSConfig getWsSignConfig() {
		return wsSignConfig;
	}

	public void setWsSignConfig(WSConfig wsSignConfig) {
		this.wsSignConfig = wsSignConfig;
	}

	public WSConfig getWsOtpConfig() {
		return wsOtpConfig;
	}

	public void setWsOtpConfig(WSConfig wsOtpConfig) {
		this.wsOtpConfig = wsOtpConfig;
	}

	public WSConfig getWsCertConfig() {
		return wsCertConfig;
	}

	public void setWsCertConfig(WSConfig wsCertConfig) {
		this.wsCertConfig = wsCertConfig;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public PadesConfig getPadesConfig() {
		return padesConfig;
	}

	public void setPadesConfig(PadesConfig padesConfig) {
		this.padesConfig = padesConfig;
	}

	public TypeOtp getTypeOtp() {
		return typeOtp;
	}

	public void setTypeOtp(TypeOtp typeOtp) {
		this.typeOtp = typeOtp;
	}

	public boolean isPadesArss() {
		return padesArss;
	}

	public void setPadesArss(boolean padesArss) {
		this.padesArss = padesArss;
	}

	public boolean isPadesDs() {
		return padesDs;
	}

	public void setPadesDs(boolean padesDs) {
		this.padesDs = padesDs;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getCertificateSerialNumber() {
		return certificateSerialNumber;
	}

	public void setCertificateSerialNumber(String certificateSerialNumber) {
		this.certificateSerialNumber = certificateSerialNumber;
	}

	public String getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getSignaturePower() {
		return signaturePower;
	}

	public void setSignaturePower(String signaturePower) {
		this.signaturePower = signaturePower;
	}

	public String getCertificateDocType() {
		return certificateDocType;
	}

	public void setCertificateDocType(String certificateDocType) {
		this.certificateDocType = certificateDocType;
	}

	
}
