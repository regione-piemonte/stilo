/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.service.bean.rest.RestServiceBean;
import it.eng.core.service.client.FactoryBusiness.BusinessType;


public interface RestServiceInvocationBefore {
	
	public void before(RestServiceBean pRestServiceBean);
	
	public String getUrl();
	
	public BusinessType getType();
}
