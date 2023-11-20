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

@Datasource(id = "LoadComboInvioDocStatoRichDS")
public class LoadComboInvioDocStatoRichDS extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		List<SimpleKeyValueBean> lList = new ArrayList<SimpleKeyValueBean>();
		lList.add(buildSimpleKeyValueBean("TO_PROCESS", "da elaborare"));
		lList.add(buildSimpleKeyValueBean("PROCESSING", "in elaborazione"));
		lList.add(buildSimpleKeyValueBean("COMPLETED", "elaborata senza errori"));
		lList.add(buildSimpleKeyValueBean("IN_WAIT", "da non elaborare ancora"));
		lList.add(buildSimpleKeyValueBean("DELETED", "eliminata"));
		lList.add(buildSimpleKeyValueBean("ERROR", "in errore ma da riprocessare"));
		lList.add(buildSimpleKeyValueBean("LOCKED", "in errore e non più da processare"));
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
