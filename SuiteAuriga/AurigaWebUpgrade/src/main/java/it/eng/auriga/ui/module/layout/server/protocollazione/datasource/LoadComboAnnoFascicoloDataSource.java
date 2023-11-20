/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Datasource(id = "LoadComboAnnoFascicoloDataSource")
public class LoadComboAnnoFascicoloDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {
	
	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String prefix = (String) criteria.getValue(); 
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("displayValue")) {
					prefix = (String) criterion.getValue();
				}
			}
		}
		
		LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
		
		Date lDate = new Date();
		SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("yyyy");		
		int lIntAnno = Integer.valueOf(lSimpleDateFormat.format(lDate));	
		
		int lIntAnnoPartenza = ((GenericConfigBean)SpringAppContext.getContext().getBean("GenericPropertyConfigurator")).getAnnoPartenza();
		
		for(int i=lIntAnno; i>lIntAnnoPartenza; i--){
			values.put(""+i, ""+i);
		}
		
		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>(values.keySet().size());
		for(String key : values.keySet()) {
			SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
			lSimpleKeyValueBean.setValue(values.get(key));
			lSimpleKeyValueBean.setKey(key);
			if(prefix == null || "".equals(prefix) || key.startsWith(prefix)) {
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
