package it.eng.utility.oomanager.config;

import java.util.List;

public class OpenOfficeInstance {
	
	private String host;
	private List<String> portList;
	private String servicename;
	private String officeServiceHome;
		
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	public List<String> getPortList() {
		return portList;
	}
	public void setPortList(List<String> portList) {
		this.portList = portList;
	}
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	public String getOfficeServiceHome() {
		return officeServiceHome;
	}
	public void setOfficeServiceHome(String officeServiceHome) {
		this.officeServiceHome = officeServiceHome;
	}
		
}
