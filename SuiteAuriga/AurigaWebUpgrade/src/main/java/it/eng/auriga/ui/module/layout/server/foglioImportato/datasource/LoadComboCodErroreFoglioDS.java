/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


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

/**
 * 
 * @author OPASSALACQUA
 *
 */

@Datasource(id = "LoadComboCodErroreFoglioDS")
public class LoadComboCodErroreFoglioDS extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
	
		String listaTipi = "";	
		
		if (criteria.getCriterionByFieldName("criteria")!=null){
			List<Map<String,Object>> lList = (ArrayList<Map<String,Object>>)criteria.getCriterionByFieldName("criteria").getValue();
			if (lList!=null){
				for (Map<String, Object> lMap : lList){
					if (lMap!=null){
						if (lMap.get("fieldName") instanceof String){
							if (lMap.get("fieldName").equals("tipiContenuto")){																
								if( lMap.get("value")!=null && !lMap.get("value").equals("")){
									ArrayList<String> arrayListaTipi = new ArrayList<String>();
									arrayListaTipi = (ArrayList<String>) lMap.get("value");								
									listaTipi = StringUtils.join(arrayListaTipi, ";");								
								}
							}
						}
					}
				}
			}
		}
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("VALORI_DIZIONARIO");
		
		if(listaTipi !=null && !listaTipi.equalsIgnoreCase("")){
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("DICTIONARY_ENTRY|*|COD_ERRORE_IMP_CSV|*|TIPI_TRACCIATI|*|" + listaTipi);	
		}
		else{
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("DICTIONARY_ENTRY|*|COD_ERRORE_IMP_CSV|*|TIPI_TRACCIATI|*|");	
		}
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		if (lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);
		} else {
			List<SimpleKeyValueBean> lista = XmlUtility.recuperaListaSemplice(lStoreResultBean.getResultBean().getListaxmlout());
			
			lPaginatorBean.setData(lista);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());
		}
		return lPaginatorBean;
	}
}