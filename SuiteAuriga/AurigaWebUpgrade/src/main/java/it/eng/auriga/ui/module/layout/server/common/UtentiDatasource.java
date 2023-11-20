/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UtenteBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "UtentiDatasource")
public class UtentiDatasource extends OptionFetchDataSource<UtenteBean> {

	@Override
	public PaginatorBean<UtenteBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		String codice = "";
		String cognomeNome = "";
		String username = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("codice")) {
					codice = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("cognomeNome")) {
					cognomeNome = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("username")) {
					username = (String) criterion.getValue();					
				}
			}
		}			
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("UTENTI");
		String altriParametri = "FLG_SOLO_ACCRED_IN_DOMINIO|*|1|*|STR_IN_COD_RAPIDO|*|" + codice + "|*|STR_IN_DES|*|" + cognomeNome + "|*|STR_IN_USERNAME|*|" + username;
		if(StringUtils.isNotBlank(getIdValoreUnico())) {
			altriParametri += "|*|ID_VALORE_UNICO|*|" + getIdValoreUnico();
		}
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);	
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(null);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		PaginatorBean<UtenteBean> lPaginatorBean = new PaginatorBean<UtenteBean>();		
		
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<UtenteBean> lList = XmlListaUtility.recuperaLista(xmlLista, UtenteBean.class);
			lPaginatorBean.setData(lList);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lList.size());
			lPaginatorBean.setTotalRows(lList.size());			
		} 
		
		return lPaginatorBean;		
	}
	
}