/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
@Datasource(id = "CategoriaSelectDataSource")
public class CategoriaSelectDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String classifica = StringUtils.isNotBlank(getExtraparams().get("classifica")) ? getExtraparams().get("classifica") : "";		
		
		LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();

		if("standard.arrivo".equals(classifica)) {
			values.put("INTEROP_SEGN", "interoperabile (con segnatura.xml)");
			values.put("PEC", "certificata (non interoperabile)");
			values.put("ANOMALIA", "da casella ordinaria (non interoperabile)"); 
			values.put("PEC_RIC_ACC", "ricevuta PEC di accettazione");
			values.put("PEC_RIC_NO_ACC", "ricevuta PEC di mancata accettazione"); 
			values.put("PEC_RIC_CONS", "ricevuta  PEC di avvenuta consegna");
			values.put("PEC_RIC_PREAVV_NO_CONS", "preavviso di mancata consegna");
			values.put("PEC_RIC_NO_CONS", "ricevuta PEC di mancata consegna");
			values.put("DELIVERY_STATUS_NOT", "notifica di trasporto generata da mail server"); 
			values.put("ALTRO", "altra mail generata in automatico da mail server");
			values.put("INTEROP_ECC", "notifica interoperabile di eccezione");
			values.put("INTEROP_CONF", "notifica interoperabile di conferma");
			values.put("INTEROP_AGG", "notifica interoperabile di aggiornamento");
			values.put("INTEROP_ANN", "notifica interoperabile di annullamento di registrazione");
		}
		else if("standard.arrivo.ricevute_notifiche_non_associate".equals(classifica)) {		
			values.put("PEC_RIC_ACC", "ricevuta PEC di accettazione");
			values.put("PEC_RIC_NO_ACC", "ricevuta PEC di mancata accettazione");
			values.put("PEC_RIC_CONS", "ricevuta  PEC di avvenuta consegna"); 
			values.put("PEC_RIC_PREAVV_NO_CONS", "preavviso di mancata consegna");
			values.put("PEC_RIC_NO_CONS", "ricevuta PEC di mancata consegna"); 
			values.put("DELIVERY_STATUS_NOT", "notifica di trasporto generata da mail server");
			values.put("INTEROP_ECC", "notifica interoperabile di eccezione");
			values.put("INTEROP_CONF", "notifica interoperabile di conferma");
			values.put("INTEROP_AGG", "notifica interoperabile di aggiornamento");
			values.put("INTEROP_ANN", "notifica interoperabile di annullamento di registrazione");
		}

		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>(values.keySet().size());
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
