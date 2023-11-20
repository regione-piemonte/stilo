/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.ui.module.layout.shared.util.SharedConstants;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class AurigaUserUtil {
	
	public static void setLoginInfo(HttpSession session, AurigaLoginBean bean){		
		it.eng.utility.ui.module.layout.shared.bean.LoginBean loginInfo = new it.eng.utility.ui.module.layout.shared.bean.LoginBean();
		try {
			BeanUtilsBean2.getInstance().copyProperties(loginInfo, bean);
		} catch (Exception e) {} 	
		UserUtil.setLoginInfo(session, loginInfo);
		session.setAttribute("AURIGA_SPEC", bean.getSpecializzazioneBean());
	}

	public static AurigaLoginBean getLoginInfo(HttpSession session){		
		it.eng.utility.ui.module.layout.shared.bean.LoginBean loginInfo = UserUtil.getLoginInfo(session);
		if(loginInfo == null) {
			return null;
		}
		AurigaLoginBean bean = new AurigaLoginBean();
		// Inserisco la lingua di default
		bean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
		try {
			BeanUtilsBean2.getInstance().copyProperties(bean, loginInfo);
		} catch (Exception e) {} 	
		try {
			RequestAttributes lRequestAttributes = RequestContextHolder.currentRequestAttributes();
			if(lRequestAttributes.getAttribute("uuid", RequestAttributes.SCOPE_REQUEST)!=null && 
			   lRequestAttributes.getAttribute("uuid", RequestAttributes.SCOPE_REQUEST) instanceof String){
				String lStringUuid = (String) lRequestAttributes.getAttribute("uuid", RequestAttributes.SCOPE_REQUEST);
				bean.setUuid(lStringUuid);
			}
		} catch (Exception e) {}
		SpecializzazioneBean spec = (SpecializzazioneBean)session.getAttribute("AURIGA_SPEC");
		bean.setSpecializzazioneBean(spec);
		return bean;
	}
	
//	public static Locale getLocale(){		
//		return UserUtil.getLocale(getSession());
//	}
	
}
