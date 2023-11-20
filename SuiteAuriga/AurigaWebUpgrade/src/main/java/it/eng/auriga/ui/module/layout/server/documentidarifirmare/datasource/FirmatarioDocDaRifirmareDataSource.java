/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.DocDaRifirmareBean;
import it.eng.client.AurigaService;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Datasource(id = "FirmatarioDocDaRifirmareDataSource")
public class FirmatarioDocDaRifirmareDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		
		DocDaRifirmareBean lFilterBean = new DocDaRifirmareBean();
		TFilterFetch<DocDaRifirmareBean> lTFilterFetch = new TFilterFetch<DocDaRifirmareBean>();
		lTFilterFetch.setFilter(lFilterBean);
		
		TPagingList<DocDaRifirmareBean> lTPagingList = AurigaService.getDaoDocDaRifirmare().search(getLocale(), lAurigaLoginBean, lTFilterFetch);
		
		if(lTPagingList != null && lTPagingList.getData() != null) {
			HashSet<String> lHashSetFirmatari = new HashSet<String>();
			for(int i = 0; i < lTPagingList.getData().size(); i++) {
				lHashSetFirmatari.add(lTPagingList.getData().get(i).getFirmatario());
			}
			List<String> lListFirmatari = Arrays.asList(lHashSetFirmatari.toArray(new String[0]));	
			Collections.sort(lListFirmatari);
			for(int j = 0; j < lListFirmatari.size(); j++) {
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lListFirmatari.get(j));
				lSimpleKeyValueBean.setValue(lListFirmatari.get(j));
				lSimpleKeyValueBean.setDisplayValue(lListFirmatari.get(j));
				lListResult.add(lSimpleKeyValueBean);
			}
		}
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}
	
}
