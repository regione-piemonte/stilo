/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.module.layout.shared.bean.AllFilterSelectBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterSelectBean;

import java.util.Map;

@Datasource(id="ServiceRestSelect")
public class ServiceRestSelect extends AbstractServiceDataSource<LoginBean, AllFilterSelectBean>{
	

	@Override
	public AllFilterSelectBean call(LoginBean bean) throws Exception {
		AllFilterSelectBean lAllFilterSelectBean = new AllFilterSelectBean();
		lAllFilterSelectBean.setSelects(SpringAppContext.getContext().getBeansOfType(FilterSelectBean.class));
		return lAllFilterSelectBean;
	}
}
