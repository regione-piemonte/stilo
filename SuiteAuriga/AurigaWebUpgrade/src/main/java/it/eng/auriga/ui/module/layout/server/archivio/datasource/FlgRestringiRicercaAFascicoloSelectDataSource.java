/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
@Datasource(id = "FlgRestringiRicercaAFascicoloSelectDataSource")
public class FlgRestringiRicercaAFascicoloSelectDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String tipoNodo = StringUtils.isNotBlank(getExtraparams().get("tipoNodo")) ? getExtraparams().get("tipoNodo") : "";		
		
		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		SimpleKeyValueBean lSimpleKeyValueBeanFS = new SimpleKeyValueBean();
		lSimpleKeyValueBeanFS.setKey("FS");
		lSimpleKeyValueBeanFS.setValue("fascicoli e sotto-fascicoli");
		
		SimpleKeyValueBean lSimpleKeyValueBeanF = new SimpleKeyValueBean();
		lSimpleKeyValueBeanF.setKey("F");
		lSimpleKeyValueBeanF.setValue("fascicoli");
		
		SimpleKeyValueBean lSimpleKeyValueBeanS = new SimpleKeyValueBean();
		lSimpleKeyValueBeanS.setKey("S");
		lSimpleKeyValueBeanS.setValue("sotto-fascicoli");
		
		
		
		if(tipoNodo == null || !"F".equals(tipoNodo)) {
			lListResult.add(lSimpleKeyValueBeanFS);
		}
		lListResult.add(lSimpleKeyValueBeanF);
		lListResult.add(lSimpleKeyValueBeanS);
			
				
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}

}
