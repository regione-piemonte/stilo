package it.eng.utility.oomanager.config;

import java.util.List;


public class OpenOfficeConfiguration {

	private List<OpenOfficeInstance> instances;
	private Integer maxTrytoconvert;
		
	public List<OpenOfficeInstance> getInstances() {
		return instances;
	}

	public void setInstances(List<OpenOfficeInstance> instances) {
		this.instances = instances;
	}

	public Integer getMaxTrytoconvert() {
		return maxTrytoconvert;
	}

	public void setMaxTrytoconvert(Integer maxTrytoconvert) {
		this.maxTrytoconvert = maxTrytoconvert;
	}


	
	
	
}