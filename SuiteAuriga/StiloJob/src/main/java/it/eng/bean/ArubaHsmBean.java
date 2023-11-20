/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class ArubaHsmBean {
	
	private String wsdlArubaHsm;
	private String serviceNS;
	private String serviceName;
	
	
	private String 	user;
	private String  delegatedUser;
	private String  delegatedPassword;
	
	private String delegatedDomain;
	
	private String  otpPwd;
	private String  typeOtpAuth;
	
	
	
	public String getWsdlArubaHsm() {
		return wsdlArubaHsm;
	}
	public void setWsdlArubaHsm(String wsdlArubaHsm) {
		this.wsdlArubaHsm = wsdlArubaHsm;
	}
	public String getServiceNS() {
		return serviceNS;
	}
	public void setServiceNS(String serviceNS) {
		this.serviceNS = serviceNS;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDelegatedUser() {
		return delegatedUser;
	}
	public void setDelegatedUser(String delegatedUser) {
		this.delegatedUser = delegatedUser;
	}
	public String getDelegatedPassword() {
		return delegatedPassword;
	}
	public void setDelegatedPassword(String delegatedPassword) {
		this.delegatedPassword = delegatedPassword;
	}
	public String getDelegatedDomain() {
		return delegatedDomain;
	}
	public void setDelegatedDomain(String delegatedDomain) {
		this.delegatedDomain = delegatedDomain;
	}
	public String getOtpPwd() {
		return otpPwd;
	}
	public void setOtpPwd(String otpPwd) {
		this.otpPwd = otpPwd;
	}
	public String getTypeOtpAuth() {
		return typeOtpAuth;
	}
	public void setTypeOtpAuth(String typeOtpAuth) {
		this.typeOtpAuth = typeOtpAuth;
	}
	
	
}
