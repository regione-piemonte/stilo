/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLogoutBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoginLogout;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import org.apache.commons.lang3.StringUtils;

@Datasource(id="LogoutDataSource")
public class LogoutDataSource extends AbstractServiceDataSource<LoginBean, LoginBean>{

	@Override
	public LoginBean call(LoginBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		if (lAurigaLoginBean!=null && lAurigaLoginBean.getToken()!=null
				&& StringUtils.isNotBlank(lAurigaLoginBean.getToken()) && !lAurigaLoginBean.getToken().startsWith("#RESERVED")){
			DmpkLoginLogout lDmpkLoginLogout = new DmpkLoginLogout();
			DmpkLoginLogoutBean lDmpkLoginLogoutBean = new DmpkLoginLogoutBean();
			lDmpkLoginLogoutBean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
			lDmpkLoginLogout.execute(getLocale(), lAurigaLoginBean, lDmpkLoginLogoutBean);
			getSession().invalidate();	
		}		
		return new LoginBean();
	}

	
}
