/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.statisticheDocumenti.datasource.bean.StatisticheDocumentiEnteAooBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import java.util.List;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;

@Datasource(id = "LoadComboEnteAooDataSource")
public class LoadComboEnteAooDataSource extends AbstractFetchDataSource<StatisticheDocumentiEnteAooBean> {
	@Override
	public PaginatorBean<StatisticheDocumentiEnteAooBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
				
		String descrizioneEnteAoo = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("descrizioneEnteAoo")) {
					descrizioneEnteAoo = (String) criterion.getValue();					
				}
			}
		}	
			
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
	    lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("AOO_SP");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("SOLO_SPAOO|*|1|*|ESTRAI_DES_BREVE|*|1|*|ESCLUDI_AOO_COLLEGATA|*|1|*|STR_IN_DES_COMPLETA|*|" + descrizioneEnteAoo);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
				
		PaginatorBean<StatisticheDocumentiEnteAooBean> lPaginatorBean = new PaginatorBean<StatisticheDocumentiEnteAooBean>();		
		
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		List<StatisticheDocumentiEnteAooBean> lista = XmlListaUtility.recuperaLista(xmlLista, StatisticheDocumentiEnteAooBean.class);
		lPaginatorBean.setData(lista);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lista.size());
		lPaginatorBean.setTotalRows(lista.size());				
		
		return lPaginatorBean;
	}
}