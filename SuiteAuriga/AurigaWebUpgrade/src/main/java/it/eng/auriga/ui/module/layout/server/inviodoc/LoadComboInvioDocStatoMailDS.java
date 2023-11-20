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

@Datasource(id = "LoadComboInvioDocStatoMailDS")
public class LoadComboInvioDocStatoMailDS extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		List<SimpleKeyValueBean> lList = new ArrayList<SimpleKeyValueBean>();
		lList.add(buildSimpleKeyValueBean("I:OK", "inviata"));
		lList.add(buildSimpleKeyValueBean("I:KO", "invio fallito"));
		lList.add(buildSimpleKeyValueBean("I:IP", "invio in corso"));
		lList.add(buildSimpleKeyValueBean("I:W", "avvertimenti in invio"));
		lList.add(buildSimpleKeyValueBean("A:OK", "accettata"));
		lList.add(buildSimpleKeyValueBean("A:KO", "non accettata"));
		lList.add(buildSimpleKeyValueBean("A:IP", "accettazione in corso"));
		lList.add(buildSimpleKeyValueBean("A:W", "presunta mancata accettazione"));
		lList.add(buildSimpleKeyValueBean("A:ND", "accettazione non valorizzata"));
		lList.add(buildSimpleKeyValueBean("C:OK", "consegnata"));
		lList.add(buildSimpleKeyValueBean("C:KO", "consegna fallita"));
		lList.add(buildSimpleKeyValueBean("C:IP", "consegna in corso"));
		lList.add(buildSimpleKeyValueBean("C:W", "presunta mancata consegna"));
		lList.add(buildSimpleKeyValueBean("C:ND", "consegna non valorizzata"));
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
