/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "LoadComboAttributoDinamicoDataSource")
public class LoadComboAttributoDinamicoDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String nomeCombo  = StringUtils.isNotBlank(getExtraparams().get("nomeCombo"))  ? getExtraparams().get("nomeCombo")  : "";
		boolean isFiltroObbligatorio = getExtraparams().get("isFiltroObbligatorio") != null && getExtraparams().get("isFiltroObbligatorio").equalsIgnoreCase("true");
		
		String value = "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("value")) {
					value = (String) criterion.getValue();
				}
			}
		}
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ATTRIBUTO_DINAMICO");		
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
        lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("NOME|*|" + nomeCombo + "|*|STRINGA|*|" + value + "|*|");
       
        List<SimpleKeyValueBean> lista = new ArrayList<SimpleKeyValueBean>();
		if(!isFiltroObbligatorio || StringUtils.isNotBlank(value)) {
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
			if(!lStoreResultBean.isInError()) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				lista = XmlUtility.recuperaListaSemplice(xmlLista);	
			} 
		}
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();		
		lPaginatorBean.setData(lista);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lista.size());
		lPaginatorBean.setTotalRows(lista.size());
		return lPaginatorBean;
	}
}
