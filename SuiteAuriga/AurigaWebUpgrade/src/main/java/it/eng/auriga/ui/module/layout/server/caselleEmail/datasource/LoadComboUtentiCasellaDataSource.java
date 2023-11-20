/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.caselleEmail.datasource.bean.LoadComboUtentiCasellaBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.SelectDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id="LoadComboUtentiCasellaDataSource")
public class LoadComboUtentiCasellaDataSource extends SelectDataSource<LoadComboUtentiCasellaBean>{

	@Override
	public PaginatorBean<LoadComboUtentiCasellaBean> realFetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		String idUser = StringUtils.isNotBlank(getExtraparams().get("idUser")) ? getExtraparams().get("idUser") : "";
		String dominio = StringUtils.isNotBlank(getExtraparams().get("dominio")) ? getExtraparams().get("dominio") : "";
		
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
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_DOMINIO|*|" + dominio + "|*|STR_IN_COD_RAPIDO|*|" + codice + "|*|STR_IN_DES|*|" + cognomeNome + "|*|STR_IN_USERNAME|*|" + username + "|*|CI_TO_ADD|*|" + idUser);	
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		PaginatorBean<LoadComboUtentiCasellaBean> lPaginatorBean = new PaginatorBean<LoadComboUtentiCasellaBean>();		
		
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<LoadComboUtentiCasellaBean> lList = XmlListaUtility.recuperaLista(xmlLista, LoadComboUtentiCasellaBean.class);
			lPaginatorBean.setData(lList);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lList.size());
			lPaginatorBean.setTotalRows(lList.size());			
		} 
		
		return lPaginatorBean;
	}
	
}
