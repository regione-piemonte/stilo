/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Cristiano Daniele
 *
 */

@Datasource(id = "LoadComboStatoFogliolmportatoDS")
public class LoadComboStatoFogliolmportatoDS extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		List<SimpleKeyValueBean> lList = new ArrayList<SimpleKeyValueBean>();
		lList.add(buildSimpleKeyValueBean("da elaborare", "da elaborare"));
		lList.add(buildSimpleKeyValueBean("in elaborazione", "in elaborazione"));
		lList.add(buildSimpleKeyValueBean("elaborato senza errori",  "elaborato senza errori"));
		lList.add(buildSimpleKeyValueBean("elaborato con errori", "elaborato con errori"));

		lPaginatorBean.setData(lList);
		lPaginatorBean.setTotalRows(lList.size());
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lList.size());
		return lPaginatorBean;
	}

	private SimpleKeyValueBean buildSimpleKeyValueBean(String key, String value) {
		SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
		lSimpleKeyValueBean.setKey(key);
		lSimpleKeyValueBean.setValue(value);
		return lSimpleKeyValueBean;
	}

}