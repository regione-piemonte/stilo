/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.vocabolario.datasource.bean.DictionaryEntryBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


@Datasource(id="DictionaryEntryDataSource")
public class DictionaryEntryDataSource extends OptionFetchDataSource<DictionaryEntryBean>{
	
	private static Logger mLogger = Logger.getLogger(DictionaryEntryDataSource.class);

	@Override
	public PaginatorBean<DictionaryEntryBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
					throws Exception {

		List<DictionaryEntryBean> lListResult = new ArrayList<DictionaryEntryBean>();
		boolean isFromFilter = StringUtils.isNotBlank(getExtraparams().get("isFromFilter")) && "true".equalsIgnoreCase(getExtraparams().get("isFromFilter"));

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("DICTIONARY_ENTRY");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(isFromFilter ? null : new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(null);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();

		try {
			if(StringUtils.isNotBlank(xmlLista)) {
				lListResult = XmlListaUtility.recuperaLista(xmlLista, DictionaryEntryBean.class);				
			}			
		} catch (Exception e) {
			mLogger.warn(e);
		}

		PaginatorBean<DictionaryEntryBean> lPaginatorBean = new PaginatorBean<DictionaryEntryBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;
	}

}
