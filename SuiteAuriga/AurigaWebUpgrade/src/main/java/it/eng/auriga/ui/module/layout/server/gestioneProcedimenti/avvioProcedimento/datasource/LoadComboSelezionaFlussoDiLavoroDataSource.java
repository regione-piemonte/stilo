/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.UnmarshalException;
@Datasource(id = "LoadComboSelezionaFlussoDiLavoroDataSource")
public class LoadComboSelezionaFlussoDiLavoroDataSource extends AbstractFetchDataSource<SimpleKeyValueBean>{

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(
			AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		String idProcessType = getExtraparams().get("idProcessType");

		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("FLUSSI_WF");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("INTERNAL_VALUE|*|CID|*|ID_PROCESS_TYPE|*|" + idProcessType);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();

		try {
			List<SimpleKeyValueBean> lListResult = XmlUtility.recuperaListaSemplice(xmlLista);
			PaginatorBean<SimpleKeyValueBean> result = new PaginatorBean<SimpleKeyValueBean>(lListResult);
			return result;
		} catch (UnmarshalException e){
			SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
			lSimpleKeyValueBean.setKey("GPA_PROCESSO_VIA");
			lSimpleKeyValueBean.setValue("GPA_PROCESSO_VIA");
			SimpleKeyValueBean lSimpleKeyValueBean1 = new SimpleKeyValueBean();
			lSimpleKeyValueBean1.setKey("test");
			lSimpleKeyValueBean1.setValue("value");
			List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
			lListResult.add(lSimpleKeyValueBean);
			lListResult.add(lSimpleKeyValueBean1);
			PaginatorBean<SimpleKeyValueBean> result = new PaginatorBean<SimpleKeyValueBean>(lListResult);
			return result;
		}
	}
}	