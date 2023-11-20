/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.chiusuraMassivaEmail.bean.ChiusuraMassivaEmailBean;

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.util.List;
import java.util.Map;


@Datasource(id = "ChiusuraMassivaEmailDatasource")
public class ChiusuraMassivaEmailDatasource extends AbstractDataSource<ChiusuraMassivaEmailBean, ChiusuraMassivaEmailBean>{	

	@Override
	public ChiusuraMassivaEmailBean add(ChiusuraMassivaEmailBean bean) throws Exception {		
			
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		return bean;
	}
		
	@Override
	public ChiusuraMassivaEmailBean get(ChiusuraMassivaEmailBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public ChiusuraMassivaEmailBean remove(ChiusuraMassivaEmailBean bean) throws Exception {
		
		return null;
	}

	@Override
	public ChiusuraMassivaEmailBean update(ChiusuraMassivaEmailBean bean, ChiusuraMassivaEmailBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<ChiusuraMassivaEmailBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(ChiusuraMassivaEmailBean bean) throws Exception {
		
		return null;
	}
		
}
