/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.job.aggiungiMarca.bean;

public class MarcaConfigBean {
	
	private Boolean marcaHsm=false;
	private String marcaServiceUrl;
	private String marcaServiceUid;
	private String marcaServicePwd;
	
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
	public Boolean getMarcaHsm() {
		return marcaHsm;
	}
	public void setMarcaHsm(Boolean marcaHsm) {
		this.marcaHsm = marcaHsm;
	}
	
	
}
