/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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