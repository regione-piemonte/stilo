/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.job.aggiungiMarca.bean;

public class HsmConfigBean {
	
	private String wsAddress;
	private String wsServiceNS;
	private String wsServiceName;
	private String environment;
	
	public String getWsAddress() {
		return wsAddress;
	}
	public void setWsAddress(String wsAddress) {
		this.wsAddress = wsAddress;
	}
	
	public String getWsServiceNS() {
		return wsServiceNS;
	}
	public void setWsServiceNS(String wsServiceNS) {
		this.wsServiceNS = wsServiceNS;
	}
	public String getWsServiceName() {
		return wsServiceName;
	}
	public void setWsServiceName(String wsServiceName) {
		this.wsServiceName = wsServiceName;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	
	
	
}
