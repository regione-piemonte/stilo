/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "StopSearchDatasource")
public class StopSearchDatasource extends AbstractServiceDataSource<StopSearchBean, StopSearchBean> {

	@Override
	public StopSearchBean call(StopSearchBean bean)	throws Exception {
		return StopSearchUtility.stopSearch(bean, getLocale(), AurigaUserUtil.getLoginInfo(getSession()));
	}

}
