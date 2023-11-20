/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.config.FilterConfigurator;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;

@Datasource(id="FilterConfiguratorService")
public class ServiceRestFilterConfig extends AbstractServiceDataSource<LoginBean, FilterConfigurator>{

	@Override
	public FilterConfigurator call(LoginBean bean) throws Exception {
		// Federico Cacco 14-10-2015
		// Centralizzata traduzione dei filtri xml
		return getXmlFiltriTradotto();
	}

}
