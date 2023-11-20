/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import java.util.List;

/**
 * 
 * @author Alberto Calvelli
 *
 */

@Datasource(id = "LoadAssocProcDataSource")
public class LoadAssocProcDataSource extends OptionFetchDataSource<SimpleKeyValueBean>{

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,Integer startRow, Integer endRow, List<OrderByBean> orderby)throws Exception {
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		String nomeProcedimento = "";		
		if (criteria.getCriteria() != null) {
			for (Criterion lCriterion : criteria.getCriteria()) {
				if (lCriterion != null) {
					if (lCriterion.getFieldName().equals("value")) {
						nomeProcedimento = (String) lCriterion.getValue();
					}
				}
			}
		}	
			
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPO_PROCEDIMENTO");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("STR_IN_DES|*|"  +  nomeProcedimento);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(null);
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		lDmpkLoadComboDmfn_load_comboBean.setPkrecin(null);		

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();		
		 
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} 		
		else 
		{
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<XmlListaSimpleBean> lista = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);
			for (XmlListaSimpleBean lRiga : lista){
				SimpleKeyValueBean lAssociaProcedimentoBean = new SimpleKeyValueBean();
				lAssociaProcedimentoBean.setKey(lRiga.getKey()+"&-&"+lRiga.getValue());
				lAssociaProcedimentoBean.setValue(lRiga.getValue());
				lPaginatorBean.addRecord(lAssociaProcedimentoBean);
			}
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());			
		} 
		
		return lPaginatorBean;		
	}
}