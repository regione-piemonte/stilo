/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.shared.bean.ParametriDBBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id="ParametriDBDataSource")
public class ParametriDBDataSource extends AbstractServiceDataSource<LoginBean, ParametriDBBean>{
	
	@Override
	public ParametriDBBean call(LoginBean bean) throws Exception {
		
		return ParametriDBUtil.getParametriDB(getSession());
	}

}