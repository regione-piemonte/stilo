/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
