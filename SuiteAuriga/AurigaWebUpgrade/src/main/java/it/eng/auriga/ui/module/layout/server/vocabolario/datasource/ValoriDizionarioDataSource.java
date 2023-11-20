/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


@Datasource(id="ValoriDizionarioDataSource")
public class ValoriDizionarioDataSource extends AbstractFetchDataSource<SimpleKeyValueBean>{
	
	private static Logger mLogger = Logger.getLogger(ValoriDizionarioDataSource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
					throws Exception {

		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";

		String dictionaryEntryVincolo =  StringUtils.isNotBlank(getExtraparams().get("dictionaryEntryVincolo")) ? getExtraparams().get("dictionaryEntryVincolo") : "";
		String valueGenVincolo =  StringUtils.isNotBlank(getExtraparams().get("valueGenVincolo")) ? getExtraparams().get("valueGenVincolo") : "";

		if (criteria!=null && criteria.getCriteria()!=null) {			
			for (Criterion criterion : criteria.getCriteria()) {
				if(criterion.getFieldName().equals("dictionaryEntryVincolo")) {
					String value = (String) criterion.getValue();
					if(StringUtils.isNotBlank(value)) {
						dictionaryEntryVincolo = value;
					}
					break;
				} 
			}
		}	
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("VALORI_DIZIONARIO");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("DICTIONARY_ENTRY|*|" + dictionaryEntryVincolo + "|*|INTERNAL_VALUE|*|DES|*|CI_TO_ADD|*|" + valueGenVincolo + "|*|ID_USER_LAVORO|*|" + idUserLavoro);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();

		try {
			lListResult = XmlUtility.recuperaListaSempliceSubstring(xmlLista);
		} catch (Exception e) {
			mLogger.warn(e);
		}

		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;
	}

}
