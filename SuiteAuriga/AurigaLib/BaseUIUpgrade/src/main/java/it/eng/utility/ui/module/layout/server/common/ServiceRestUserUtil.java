/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterPrivilegiUtil;
import it.eng.utility.ui.user.UserUtil;

@Component
@Datasource(id="ServiceRestUserUtil")
public class ServiceRestUserUtil  extends AbstractServiceDataSource<LoginBean, LoginBean>{
	
	private static Logger mLogger = Logger.getLogger(ServiceRestUserUtil.class);
	
	private static UserPrivilegiUtil privilegi;
	private static FilterPrivilegiUtil filterPrivilegi;
	
	@Override
	public LoginBean call(LoginBean bean) throws Exception {
		LoginBean lLoginBean = UserUtil.getLoginInfo(getSession());
		if (lLoginBean.getUserid()!=null)
			lLoginBean.setUserid(lLoginBean.getUserid().toUpperCase().replaceAll(" ", ""));
		if (lLoginBean.getUseridForPrefs()!=null)
			lLoginBean.setUseridForPrefs(lLoginBean.getUseridForPrefs().toUpperCase().replaceAll(" ", ""));		
		if(lLoginBean.getDenominazione() == null || "".equals(lLoginBean.getDenominazione())) {
			lLoginBean.setDenominazione(lLoginBean.getUserid());		
		}
		lLoginBean.setPrivilegi(privilegi != null ? privilegi.getPrivilegi(getSession()) : null);
		return lLoginBean;
	}
	
	public SimpleKeyValueBean getRequestHeaderValue(SimpleKeyValueBean bean) throws Exception {
		HttpServletRequest request = getRequest();
		if (request != null && StringUtils.isNotBlank(bean.getKey())) {
			String value = request.getHeader(bean.getKey());
			bean.setValue(value);
		}
		return bean;
	}
	
	public SimpleKeyValueBean getSessionAttributeAsString(SimpleKeyValueBean bean) throws Exception {
		mLogger.debug("bean.getKey(): " + bean.getKey() + " session " + (getSession() != null ? "valorizzata " : "null"));
		if (getSession() != null && StringUtils.isNotBlank(bean.getKey())) {
			String value = getSession().getAttribute(bean.getKey()) != null ? getSession().getAttribute(bean.getKey()).toString() : null;
			mLogger.debug("Letto valore da session " + bean.getKey() + " = " + value);
			bean.setValue(value);
		}
		return bean;
	}
	
	public SimpleKeyValueBean setSessionAttributeAsString(SimpleKeyValueBean bean) throws Exception {
		mLogger.debug("bean.getKey(): " + bean.getKey() + " session " + (getSession() != null ? "valorizzata " : "null"));
		if (getSession() != null && StringUtils.isNotBlank(bean.getKey())) {
			getSession().setAttribute(bean.getKey(), bean.getValue());
			mLogger.debug("Scritto valore in session " + bean.getKey() + " = " + bean.getValue());
		}
		return bean;
	}
	
	public SimpleKeyValueBean getSessionId() throws Exception {
		mLogger.debug("session " + (getSession() != null ? "valorizzata " : "null"));
		SimpleKeyValueBean bean = new SimpleKeyValueBean();
		bean.setKey("sessionId");
		if (getSession() != null && getSession().getId() != null && !"".equalsIgnoreCase(getSession().getId())) {
			bean.setValue(getSession().getId());
			mLogger.debug("isSession: " + getSession().getId());
		}
		return bean;
	}

	public static UserPrivilegiUtil getPrivilegi() {
		return privilegi;
	}
	
	@Autowired(required = true)
	public void setPrivilegi(UserPrivilegiUtil privilegi) {
		ServiceRestUserUtil.privilegi = privilegi;
	}

	@Autowired(required = true)
	public void setFilterPrivilegi(FilterPrivilegiUtil filterPrivilegi) {
		ServiceRestUserUtil.filterPrivilegi = filterPrivilegi;
	}

	public static FilterPrivilegiUtil getFilterPrivilegi() {
		return filterPrivilegi;
	}

}
