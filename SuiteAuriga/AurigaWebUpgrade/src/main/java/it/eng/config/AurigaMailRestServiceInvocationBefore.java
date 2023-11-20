/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import it.eng.core.service.bean.rest.RestServiceBean;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import it.eng.core.service.client.config.RestServiceInvocationBefore;
import it.eng.utility.ui.user.AurigaUserUtil;

public class AurigaMailRestServiceInvocationBefore implements RestServiceInvocationBefore {
	
	@Override
	public void before(RestServiceBean pRestServiceBean) {
		//Recupero la sessione
		 ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		 HttpSession lSession = attr.getRequest().getSession();
		 //Recupero l'utente
		 String lIdUser = AurigaUserUtil.getLoginInfo(lSession).getIdUser().longValue() + "";
		 //Setto l'utente
		 pRestServiceBean.getInputs().add(lIdUser);
	}

	@Override
	public String getUrl() {
		
		return AurigaMailBusinessClientConfig.getInstance().getUrl();
	}

	@Override
	public BusinessType getType() {
		
		return AurigaMailBusinessClientConfig.getInstance().getBusinesstype();
	}


}
