/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.service.client.FactoryBusiness.BusinessType;

public class WorkflowBusinessClientConfig {

	private String url;
	private BusinessType businesstype = BusinessType.REST;
	private static WorkflowBusinessClientConfig instance;
	
	public static synchronized WorkflowBusinessClientConfig getInstance() {
		if(instance==null){
			instance = new WorkflowBusinessClientConfig();
			
		}
		return instance;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	
	public BusinessType getBusinesstype() {
		return businesstype;
	}

	public void setBusinesstype(BusinessType businesstype) {
		this.businesstype = businesstype;
	}
}