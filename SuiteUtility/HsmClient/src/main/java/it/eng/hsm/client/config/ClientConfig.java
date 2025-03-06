package it.eng.hsm.client.config;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class ClientConfig {

	@XmlVariabile(nome = "hsmType", tipo = TipoVariabile.SEMPLICE)
	private String hsmType;
	@XmlVariabile(nome = "attivaFirmaInDelega", tipo = TipoVariabile.SEMPLICE)
	private String attivaFirmaInDelega;
	@XmlVariabile(nome = "canSendOtpViaSms", tipo = TipoVariabile.SEMPLICE)
	private String canSendOtpViaSms;
	@XmlVariabile(nome = "canSendOtpViaCall", tipo = TipoVariabile.SEMPLICE)
	private String canSendOtpViaCall;
	@XmlVariabile(nome = "requireOtp", tipo = TipoVariabile.SEMPLICE)
	private boolean requireOtp;
	@XmlVariabile(nome = "useDifferentCredentialForOtpRequest", tipo = TipoVariabile.SEMPLICE)
	private String useDifferentCredentialForOtpRequest;
	@XmlVariabile(nome = "requireSignatureInSession", tipo = TipoVariabile.SEMPLICE)
	private boolean requireSignatureInSession;
	private String idSession;
	@XmlVariabile(nome = "proxyConfig", tipo = TipoVariabile.NESTED)
	private ProxyConfig proxyConfig;
	@XmlVariabile(nome = "marcaConfig", tipo = TipoVariabile.NESTED)
	private MarcaConfig marcaConfig;
	@XmlVariabile(nome = "firmaRemotaConPassaggioFile", tipo = TipoVariabile.SEMPLICE)
	private String firmaRemotaConPassaggioFile;
	@XmlVariabile(nome = "authByPINPasswordAsSeparateFileds", tipo = TipoVariabile.SEMPLICE)
	private String authByPINPasswordAsSeparateFileds;
	@XmlVariabile(nome = "canSavePasswordFirmaNonAutomatica", tipo = TipoVariabile.SEMPLICE)
	private String canSavePasswordFirmaNonAutomatica;
	@XmlVariabile(nome = "canSavePinFirmaNonAutomatica", tipo = TipoVariabile.SEMPLICE)
	private String canSavePinFirmaNonAutomatica;
	@XmlVariabile(nome = "canSavePasswordRichOtp", tipo = TipoVariabile.SEMPLICE)
	private String canSavePasswordRichOtp;

	public String getHsmType() {
		return hsmType;
	}
	
	public void setHsmType(String hsmType) {
		this.hsmType = hsmType;
	}
	
	public String getAttivaFirmaInDelega() {
		return attivaFirmaInDelega;
	}
	
	public void setAttivaFirmaInDelega(String attivaFirmaInDelega) {
		this.attivaFirmaInDelega = attivaFirmaInDelega;
	}

	public String getCanSendOtpViaSms() {
		return canSendOtpViaSms;
	}

	public void setCanSendOtpViaSms(String canSendOtpViaSms) {
		this.canSendOtpViaSms = canSendOtpViaSms;
	}

	public String getCanSendOtpViaCall() {
		return canSendOtpViaCall;
	}

	public void setCanSendOtpViaCall(String canSendOtpViaCall) {
		this.canSendOtpViaCall = canSendOtpViaCall;
	}

	public boolean isRequireOtp() {
		return requireOtp;
	}

	public void setRequireOtp(boolean requireOtp) {
		this.requireOtp = requireOtp;
	}
	
	public String getUseDifferentCredentialForOtpRequest() {
		return useDifferentCredentialForOtpRequest;
	}

	
	public void setUseDifferentCredentialForOtpRequest(String useDifferentCredentialForOtpRequest) {
		this.useDifferentCredentialForOtpRequest = useDifferentCredentialForOtpRequest;
	}

	public boolean isRequireSignatureInSession() {
		return requireSignatureInSession;
	}

	public void setRequireSignatureInSession(boolean requireSignatureInSession) {
		this.requireSignatureInSession = requireSignatureInSession;
	}

	public ProxyConfig getProxyConfig() {
		return proxyConfig;
	}

	public void setProxyConfig(ProxyConfig proxyConfig) {
		this.proxyConfig = proxyConfig;
	}

	public MarcaConfig getMarcaConfig() {
		return marcaConfig;
	}

	public void setMarcaConfig(MarcaConfig marcaConfig) {
		this.marcaConfig = marcaConfig;
	}

	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public String getFirmaRemotaConPassaggioFile() {
		return firmaRemotaConPassaggioFile;
	}
	
	public void setFirmaRemotaConPassaggioFile(String firmaRemotaConPassaggioFile) {
		this.firmaRemotaConPassaggioFile = firmaRemotaConPassaggioFile;
	}
	
	public String getAuthByPINPasswordAsSeparateFileds() {
		return authByPINPasswordAsSeparateFileds;
	}

	public void setAuthByPINPasswordAsSeparateFileds(String authByPINPasswordAsSeparateFileds) {
		this.authByPINPasswordAsSeparateFileds = authByPINPasswordAsSeparateFileds;
	}

	public String getCanSavePasswordFirmaNonAutomatica() {
		return canSavePasswordFirmaNonAutomatica;
	}
	
	public void setCanSavePasswordFirmaNonAutomatica(String canSavePasswordFirmaNonAutomatica) {
		this.canSavePasswordFirmaNonAutomatica = canSavePasswordFirmaNonAutomatica;
	}
	
	public String getCanSavePinFirmaNonAutomatica() {
		return canSavePinFirmaNonAutomatica;
	}

	public void setCanSavePinFirmaNonAutomatica(String canSavePinFirmaNonAutomatica) {
		this.canSavePinFirmaNonAutomatica = canSavePinFirmaNonAutomatica;
	}
	
	public String getCanSavePasswordRichOtp() {
		return canSavePasswordRichOtp;
	}

	public void setCanSavePasswordRichOtp(String canSavePasswordRichOtp) {
		this.canSavePasswordRichOtp = canSavePasswordRichOtp;
	}

}
