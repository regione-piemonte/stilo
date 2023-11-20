/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author DANCRIST
 *
 */

@Datasource(id="LoadComboProponenteAttoConsiglioDataSource")
public class LoadComboProponenteAttoConsiglioDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	
	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "";
		
		String key = StringUtils.isNotBlank(getExtraparams().get("key")) ? getExtraparams().get("key") : "";
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("PROPONENTE_ATTO_CONSIGLIO");
		
		if(StringUtils.isNotBlank(getExtraparams().get("altriParamLoadCombo"))) {		
			String altriParametri = getExtraparams().get("altriParamLoadCombo");
			altriParametri = altriParametri.replace("$ID_USER_LAVORO$", idUserLavoro);
			altriParametri = altriParametri.replace("$STR_IN_DES$", "");
			altriParametri = altriParametri.replace("$CI_TO_ADD$", key);
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);	
		} else {
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(null);							
		}
		
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				loginBean, lDmpkLoadComboDmfn_load_comboBean);

		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();

		if (lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<SimpleKeyValueBean> lListResult = XmlUtility.recuperaListaSempliceSubstring(xmlLista);
			lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>(lListResult);			
		}

		return lPaginatorBean;
	}
}