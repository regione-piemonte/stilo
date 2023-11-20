/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;

import java.util.List;

@Datasource(id="TestListDatasource")
public class TestListDatasource extends AbstractFetchDataSource<String> {

	@Override
	public String add(String bean) throws Exception {
		
		return null;
	}

	@Override
	public String remove(String bean) throws Exception {
		
		return null;
	}

	@Override
	public String update(String bean, String oldvalue)
			throws Exception {
		
		return null;
	}


	@Override
	public PaginatorBean<String> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		//Chiamata 
		
		//TPagingList<LuceneDataSourceField> utenti = SecurityManagerService.getDaoUtenti().search(new TFilterFetch<LuceneDataSourceField>());
		
		//System.out.println(utenti);
		
		
//		
//		ConfigBean bean = server.call("ModuleConfig", "test", bean1, bean2);
//		
		PaginatorBean<String> paginator = new PaginatorBean<String>();
//		
//		List<ConfigBean> data = new ArrayList<ConfigBean>();
//		data.add(bean);
//		paginator.setData(data);
//		paginator.setStartRow(0);
//		paginator.setEndRow(1);
//		paginator.setTotalRows(1);
		
		return paginator;

	}





}
