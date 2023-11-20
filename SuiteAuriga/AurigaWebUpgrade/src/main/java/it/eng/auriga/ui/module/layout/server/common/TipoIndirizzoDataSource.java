/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.utility.MessageUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

@Datasource(id = "TipoIndirizzoDataSource")
public class TipoIndirizzoDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String flgPersonaFisica = StringUtils.isNotBlank(getExtraparams().get("flgPersonaFisica")) ? getExtraparams().get("flgPersonaFisica") : "";
		
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){				
				if(criterion.getFieldName().equals("flgPersonaFisica")) {
					flgPersonaFisica = (String) criterion.getValue();
					break;
				}	
			}
		}
				
		// carico i valori statici
		LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
		
		String userLanguage = getLocale().getLanguage();
		
		if("1".equals(flgPersonaFisica)) {
			values.put("RS",  MessageUtil.getValue(userLanguage, getSession(), "soggetti_tipoIndirizzo_RS_value"));
			values.put("D",   MessageUtil.getValue(userLanguage, getSession(), "soggetti_tipoIndirizzo_D_value"));
			values.put("RC",  MessageUtil.getValue(userLanguage, getSession(), "soggetti_tipoIndirizzo_RC_value"));
		}
		else {
			values.put("SL",  MessageUtil.getValue(userLanguage, getSession(), "soggetti_tipoIndirizzo_SL_value"));
			values.put("SO",  MessageUtil.getValue(userLanguage, getSession(), "soggetti_tipoIndirizzo_SO_value"));
			values.put("RC",  MessageUtil.getValue(userLanguage, getSession(), "soggetti_tipoIndirizzo_RC_value"));
		}
		

		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();

		for(String key : values.keySet()) {
			SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
			lSimpleKeyValueBean.setValue(values.get(key));
			lSimpleKeyValueBean.setKey(key);
			lListResult.add(lSimpleKeyValueBean);
		}
				
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}

}
