/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.utility.MessageUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

public abstract class SimpleKeyValueDatasource<E extends Enum <E>> extends OptionFetchDataSource<SimpleKeyValueBean>{

	protected abstract E[] getValori();
	protected abstract String getPrefix();
	public AdvancedCriteria mCriteria;

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		mCriteria = criteria;
		E[] valori = getValori();
		lPaginatorBean.setTotalRows(valori.length);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(valori.length);
		
		String prefix = getPrefix();
		
		List<SimpleKeyValueBean> lList = new ArrayList<SimpleKeyValueBean>();
		Map<String, String> lMap = new HashMap<String, String>();
		String[] ordered = new String[getValori().length];
		
		String userLanguage = getLocale().getLanguage();
		
		for (int i=0; i<ordered.length; i++){
			ordered[i] = MessageUtil.getValue(userLanguage, getSession(), prefix + valori[i].name() + "_value");
			lMap.put(ordered[i], valori[i].name());
		}
		Arrays.sort(ordered);
		for (String lString : ordered){
			SimpleKeyValueBean bean = new SimpleKeyValueBean();
			bean.setKey(lMap.get(lString));
			bean.setValue(lString);
			lList.add(bean);
		}
		lPaginatorBean.setData(lList);
		return lPaginatorBean;
	}

}
