/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.utility.ui.module.core.server.datasource.AbstractFormDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.login.server.bean.LoginBean;

@Datasource(id="LoginFormDataSource")
public class LoginFormDataSource extends AbstractFormDataSource<LoginBean> {

	@Override
	public LoginBean update(LoginBean bean, LoginBean oldvalue)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(LoginBean bean) throws Exception {
		
		return null;
	}

}
