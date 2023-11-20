/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class WSEndPointArubaHsmBean {
	
	private String wsdlArubaHsm;
	private String serviceNS;
	private String serviceName;
	private String marcaServiceUrl;
	private String marcaServiceUid;
	private String marcaServicePwd;
	private String tipoMarcaForzataPerApplet;
	
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
	public String getMarcaServiceUrl() {
		return marcaServiceUrl;
	}
	public void setMarcaServiceUrl(String marcaServiceUrl) {
		this.marcaServiceUrl = marcaServiceUrl;
	}
	public String getMarcaServiceUid() {
		return marcaServiceUid;
	}
	public void setMarcaServiceUid(String marcaServiceUid) {
		this.marcaServiceUid = marcaServiceUid;
	}
	public String getMarcaServicePwd() {
		return marcaServicePwd;
	}
	public void setMarcaServicePwd(String marcaServicePwd) {
		this.marcaServicePwd = marcaServicePwd;
	}
	public String getTipoMarcaForzataPerApplet() {
		return tipoMarcaForzataPerApplet;
	}
	public void setTipoMarcaForzataPerApplet(String tipoMarcaForzataPerApplet) {
		this.tipoMarcaForzataPerApplet = tipoMarcaForzataPerApplet;
	}
	
}
