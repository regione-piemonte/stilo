package it.eng.dm.sira.service.bean;

import java.util.List;

public class FilterBeanIn {
	
	private List<String> attributesIn;

	private String prov;

	private String idProcess;

	public List<String> getAttributesIn() {
		return attributesIn;
	}

	public void setAttributesIn(List<String> attributesIn) {
		this.attributesIn = attributesIn;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	

}
