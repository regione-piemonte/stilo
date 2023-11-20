/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.deleghe.datasource.bean.UtenteDelegabileBean;
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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

@Datasource(id = "AurigaLoadDelegheDataSource")
public class AurigaLoadDelegheDataSource extends AbstractFetchDataSource<UtenteDelegabileBean> {

	@Override
	public PaginatorBean<UtenteDelegabileBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String descrizione = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("descrizione")) {
					descrizione = (String) criterion.getValue();					
				} 
			}
		}		
		
		if (descrizione.replaceAll("\\s","").length() < 3 ) {
			descrizione = "";
		}
		
		List<UtenteDelegabileBean> lista = new ArrayList<UtenteDelegabileBean>();
		PaginatorBean<UtenteDelegabileBean> lPaginatorBean = new PaginatorBean<UtenteDelegabileBean>();		
		
		if (!"".equals(descrizione)) {
			
			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_comboBean bean = new DmpkLoadComboDmfn_load_comboBean();
			bean.setTipocomboin("DELEGHE_VS_UTENTI");
			bean.setFlgsolovldin(BigDecimal.ONE);
			bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
			
			if(!descrizione.equalsIgnoreCase(""))
			   bean.setAltriparametriin("STR_IN_DES|*|" + descrizione);	
			
			// Eseguo la store
			DmpkLoadComboDmfn_load_combo store = new DmpkLoadComboDmfn_load_combo();
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = store.execute(getLocale(), lAurigaLoginBean, bean);
			
			// leggo i risultati		
			if (StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				lista = XmlListaUtility.recuperaLista(xmlLista, UtenteDelegabileBean.class);
			}
		}
		
		lPaginatorBean.setData(lista);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lista.size());
		lPaginatorBean.setTotalRows(lista.size());	
		
		return lPaginatorBean;
	}
}