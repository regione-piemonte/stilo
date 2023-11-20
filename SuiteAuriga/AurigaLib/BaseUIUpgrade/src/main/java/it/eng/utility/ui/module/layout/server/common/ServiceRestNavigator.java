/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.module.layout.shared.bean.Navigator;
import it.eng.utility.ui.module.layout.shared.bean.NavigatorBean;

@Datasource(id="NavigatorService")
public class ServiceRestNavigator extends AbstractServiceDataSource<LoginBean, Navigator>{
	
	@Override
	public  Navigator call(LoginBean bean) throws Exception {
		
			
		Navigator navigator = new Navigator();		
		return navigator;
	}

}