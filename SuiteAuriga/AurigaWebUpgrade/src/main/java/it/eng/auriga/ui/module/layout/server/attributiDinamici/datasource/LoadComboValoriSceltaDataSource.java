/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "LoadComboValoriSceltaDataSource")
public class LoadComboValoriSceltaDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String valoriScelta  = StringUtils.isNotBlank(getExtraparams().get("valoriScelta"))  ? getExtraparams().get("valoriScelta")  : "";
		
		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		
		if (valoriScelta!=null && !valoriScelta.equalsIgnoreCase(""))  {
		
			String[] listaValoriString = valoriScelta.split("\\|\\*\\|");
			List<String> listaValoriList  = Arrays.asList(listaValoriString);
			
			if (listaValoriList.size() > 0 ){
				for(int j = 0; j < listaValoriList.size(); j++) {
					SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
					lSimpleKeyValueBean.setKey(listaValoriList.get(j));
					lSimpleKeyValueBean.setValue(listaValoriList.get(j));
					lSimpleKeyValueBean.setDisplayValue(listaValoriList.get(j));
					lListResult.add(lSimpleKeyValueBean);	
				}
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