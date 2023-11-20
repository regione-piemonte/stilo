/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.spring.utility.SpringAppContext;
//import it.eng.utility.authentication.module.AuthType;
//import it.eng.utility.authentication.module.Authentication;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;

@Datasource(id="GenericPropertyConfiguratorService")
public class ServiceRestGenericPropertyConfig extends AbstractServiceDataSource<LoginBean, GenericConfigBean>{
	
	@Override
	public GenericConfigBean call(LoginBean bean) throws Exception {
		
		GenericConfigBean lGenericPropertyConfigurator = (GenericConfigBean)SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		return lGenericPropertyConfigurator;
	}
	
	public static String getApplicationName(){		
		GenericConfigBean lGenericPropertyConfigurator = (GenericConfigBean)SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		String applicationName = lGenericPropertyConfigurator != null ? lGenericPropertyConfigurator.getApplicationName() : null;		
		return applicationName;
	}
	
	public static String getDisplayApplicationName(){		
		GenericConfigBean lGenericPropertyConfigurator = (GenericConfigBean)SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		String displayApplicationName = lGenericPropertyConfigurator != null ? lGenericPropertyConfigurator.getDisplayApplicationName() : null;		
		return displayApplicationName;
	}
	
	public static String getDefaultLocale(){		
		GenericConfigBean lGenericPropertyConfigurator = (GenericConfigBean)SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		String defaultLocale = lGenericPropertyConfigurator != null ? lGenericPropertyConfigurator.getDefaultLocale() : null;		
		return defaultLocale;
	}
	
	public static String getShowResetPasswordLogin(){		

		GenericConfigBean lGenericPropertyConfigurator = (GenericConfigBean)SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		//Authentication lAuthentication = (Authentication)SpringAppContext.getContext().getBean("authentication");
		
		String showResetPasswordLogin = "false";
		if (lGenericPropertyConfigurator != null && lGenericPropertyConfigurator.getShowResetPasswordLogin()!=null  && lGenericPropertyConfigurator.getShowResetPasswordLogin().equalsIgnoreCase("true"))
			showResetPasswordLogin = "true";
		
		// Se è attivo LDAP il reset password deve essere nascosto
		//if (lAuthentication != null && lAuthentication.getAuthType()!=null && lAuthentication.getAuthType().equals( AuthType.LDAP ))
		//	showResetPasswordLogin = "false";
		
		return showResetPasswordLogin;
	}
	
	public static String getFlagCifratura() {	
		String result = "false";
		GenericConfigBean lGenericPropertyConfigurator = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		if (lGenericPropertyConfigurator != null) {
			String flag = lGenericPropertyConfigurator.getFlagCifratura();
			if ( "true".equalsIgnoreCase(flag) ) {
				result = "true";
		    }
		}
		return result;
	}
	
	public static String getFlagAttivaRequestValidator() {	
		String result = "false";
		GenericConfigBean lGenericPropertyConfigurator = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		if (lGenericPropertyConfigurator != null) {
			String flag = lGenericPropertyConfigurator.getFlagAttivaRequestValidator();
			if ("true".equalsIgnoreCase(flag) ) {
				result = "true";
		    }
		}
		return result;
	}
	
	public static String getFlagRimuoviScript() {	
		String result = "false";
		GenericConfigBean lGenericPropertyConfigurator = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		if (lGenericPropertyConfigurator != null) {
			String flag = lGenericPropertyConfigurator.getFlagRimuoviScript();
			if ("true".equalsIgnoreCase(flag) ) {
				result = "true";
		    }
		}
		return result;
	}
	
	public static String getFlagEscapeHtml() {	
		String result = "false";
		GenericConfigBean lGenericPropertyConfigurator = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		if (lGenericPropertyConfigurator != null) {
			String flag = lGenericPropertyConfigurator.getFlagEscapeHtml();
			if ("true".equalsIgnoreCase(flag) ) {
				result = "true";
		    }
		}
		return result;
	}

}
