/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.SocietaUtenteBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.List;



@Datasource(id = "LoadComboSocietaUtenteDataSource")
public class LoadComboSocietaUtenteDataSource extends AbstractFetchDataSource<SocietaUtenteBean> {
	@Override
	public PaginatorBean<SocietaUtenteBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		PaginatorBean<SocietaUtenteBean> lPaginatorBean = new PaginatorBean<SocietaUtenteBean>();
		
						
		String denominazioneSocieta = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("denominazioneSocieta")) {
					denominazioneSocieta = (String) criterion.getValue();
					break;
				}																						 
			}
		}
		
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("SOCIETA");		
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FLG_SHOW_ALL|*|1");
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
				
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} 
		else 
		{
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<SocietaUtenteBean> lista = XmlListaUtility.recuperaLista(xmlLista, SocietaUtenteBean.class);
			lPaginatorBean.setData(lista);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());			
		} 
		return lPaginatorBean;
	}
}
