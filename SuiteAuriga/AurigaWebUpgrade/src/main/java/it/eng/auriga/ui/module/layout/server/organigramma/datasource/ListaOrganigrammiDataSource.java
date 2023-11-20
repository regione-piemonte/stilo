/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.LoadComboOrganigrammiBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "ListaOrganigrammiDataSource")
public class ListaOrganigrammiDataSource extends AbstractFetchDataSource<LoadComboOrganigrammiBean>{

	@Override
	public PaginatorBean<LoadComboOrganigrammiBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		DmpkLoadComboDmfn_load_comboBean bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		bean.setTipocomboin("ORGANIGRAMMI_SP_AOO");		
		DmpkLoadComboDmfn_load_combo store = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = store.execute(getLocale(), lAurigaLoginBean, bean);
		PaginatorBean<LoadComboOrganigrammiBean> lPaginatorBean = new PaginatorBean<LoadComboOrganigrammiBean>();				
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} 		
		else 
		{
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<LoadComboOrganigrammiBean> lista = XmlListaUtility.recuperaLista(xmlLista, LoadComboOrganigrammiBean.class);
			for (LoadComboOrganigrammiBean lLoadComboOrganigrammiBean : lista){				
				lLoadComboOrganigrammiBean.setDisplayValue(StringUtils.isNotBlank(lLoadComboOrganigrammiBean.getDataFine()) ? 
						"Dal " + lLoadComboOrganigrammiBean.getDataInizio() + " al " + lLoadComboOrganigrammiBean.getDataFine() : "Dal " + lLoadComboOrganigrammiBean.getDataInizio());								
				lPaginatorBean.addRecord(lLoadComboOrganigrammiBean);
			}					
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());			
		} 		
		return lPaginatorBean;			
	}	
}
