/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

@Datasource(id = "LoadComboComuniDataSource")
public class LoadComboComuniDataSource extends OptionFetchDataSource<SimpleKeyValueBean>{

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(
			AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		

		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("COMUNI_ITALIANI");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ISTAT_REGIONE|*|"+"");//TODO PASSARE <cod. istat regione, parametro di DB ISTAT_REGIONE_RIF>");

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();

		List<SimpleKeyValueBean> lListResult = XmlUtility.recuperaListaSemplice(xmlLista);
		List<SimpleKeyValueBean> lListResultProcedimenti = new ArrayList<SimpleKeyValueBean>();
		for (SimpleKeyValueBean lSimpleKeyValueBean : lListResult){
			SimpleKeyValueBean sbean = new SimpleKeyValueBean();
			sbean.setKey(lSimpleKeyValueBean.getKey());
			sbean.setValue(lSimpleKeyValueBean.getValue());
			lListResultProcedimenti.add(sbean);
		}
		PaginatorBean<SimpleKeyValueBean> result = new PaginatorBean<SimpleKeyValueBean>(lListResultProcedimenti);
		return result;
	}

}
