/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.PeriziaComboXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.PeriziaComboXmlBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

/**
 * 
 * @author dbe4235
 *
 */

@Datasource(id="LoadComboOperaFilterDataSource")
public class LoadComboOperaFilterDataSource extends OptionFetchDataSource<PeriziaComboXmlBean> {
	
	@Override
	public PaginatorBean<PeriziaComboXmlBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		String codice = "";		
		String descrizione = "";
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("codice")) {
					codice = (String) criterion.getValue();
				} else if (criterion.getFieldName().equals("descrizione")) {
					descrizione = (String) criterion.getValue();
				}
			}
		}
		
		// Inizializzo l'input
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("PERIZIA_ADSP");		
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		
		String altriParametri = "STR_IN_DES|*|"  +  descrizione + "|*|CODICE|*|" + codice;
				
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);		
		
		// Eseguo il servizio 
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		List<PeriziaComboXmlBean> lListResult = new ArrayList<PeriziaComboXmlBean>();
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		if(StringUtils.isNotBlank(xmlLista)) {			
			PeriziaComboXmlBeanDeserializationHelper helper = new PeriziaComboXmlBeanDeserializationHelper(new HashMap<String, String>());
			lListResult = XmlListaUtility.recuperaLista(xmlLista, PeriziaComboXmlBean.class, helper);
		}	
				
		PaginatorBean<PeriziaComboXmlBean> lPaginatorBean = new PaginatorBean<PeriziaComboXmlBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;	
	}
}