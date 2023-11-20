/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

import java.util.List;


@Datasource(id = "LoadComboPrioritaInvioDataSource")
public class LoadComboPrioritaInvioDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	
	
	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
				
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();		
	
		lPaginatorBean.addRecord(createSimpleKeyValueBean("-1", "bassa", "prioritaBassa.png"));
		lPaginatorBean.addRecord(createSimpleKeyValueBean("0", "normale", "prioritaMedia.png"));
		lPaginatorBean.addRecord(createSimpleKeyValueBean("1", "alta", "prioritaAlta.png"));
		lPaginatorBean.addRecord(createSimpleKeyValueBean("2", "molto alta", "prioritaAltissima.png"));
		
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(4);
		lPaginatorBean.setTotalRows(4);			
		
		return lPaginatorBean;
		
	}
	
	private SimpleKeyValueBean createSimpleKeyValueBean(String key, String value, String icon) {
		SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
		lSimpleKeyValueBean.setKey(key);
		lSimpleKeyValueBean.setValue("<div><img src=\"images/" + icon + "\" width=\"14\" height=\"14\" style=\"vertical-align:middle;\" /> " + value + "</div>");
		return lSimpleKeyValueBean;
	}

}
