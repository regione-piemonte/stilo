package it.eng.core.service.client.config;

import it.eng.core.service.bean.rest.RestServiceBean;
import it.eng.core.service.client.FactoryBusiness.BusinessType;


public interface RestServiceInvocationBefore {
	
	public void before(RestServiceBean pRestServiceBean);
	
	public String getUrl();
	
	public BusinessType getType();
}
