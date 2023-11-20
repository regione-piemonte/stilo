/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.caselleEmail.datasource.bean.LoadComboFruitoriCasellaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.OrganigrammaBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.SelectDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id="LoadComboFruitoriCasellaDataSource")
public class LoadComboFruitoriCasellaDataSource extends SelectDataSource<LoadComboFruitoriCasellaBean>{
	
	private static Logger mLogger = Logger.getLogger(LoadComboFruitoriCasellaDataSource.class);

	@Override
	public PaginatorBean<LoadComboFruitoriCasellaBean> realFetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		String idFruitoreCasella = StringUtils.isNotBlank(getExtraparams().get("idFruitoreCasella")) ? getExtraparams().get("idFruitoreCasella") : "";
		String dominio = StringUtils.isNotBlank(getExtraparams().get("dominio")) ? getExtraparams().get("dominio") : "";
		
		String codice = StringUtils.isNotBlank(getExtraparams().get("codice")) ? getExtraparams().get("codice") : "";
		String denominazione = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if (!ParametriDBUtil.getParametroDBAsBoolean(getSession(), "DIM_ORGANIGRAMMA_NONSTD") && criterion.getFieldName().equals("codice")) {
					codice = (String) criterion.getValue();				
				} else if(criterion.getFieldName().equals("denominazione")) {
					denominazione = (String) criterion.getValue();					
				}
			}
		}		
		
		List<LoadComboFruitoriCasellaBean> lListResult = new ArrayList<LoadComboFruitoriCasellaBean>();
		PaginatorBean<LoadComboFruitoriCasellaBean> lPaginatorBean = new PaginatorBean<LoadComboFruitoriCasellaBean>();		
		
		if( !ParametriDBUtil.getParametroDBAsBoolean(getSession(), "DIM_ORGANIGRAMMA_NONSTD") || !"".equals(codice)/* || !"".equals(idFruitoreCasella)*/) {
			
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
			
			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("FRUITORI_CASELLE");
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("SOLO_UO_SOTTO_DOMINIO|*|" + dominio + "|*|NRI_LIVELLI_UO|*|" + codice + "|*|STR_IN_DES|*|" + denominazione + "|*|CI_TO_ADD|*|" + idFruitoreCasella);	
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
			
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
			
			if(StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				try {
					lListResult = XmlListaUtility.recuperaLista(xmlLista, LoadComboFruitoriCasellaBean.class);					
				} catch (Exception e) {
					mLogger.warn(e);
				}					
			} 
		}
			
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());		
		
		
		return lPaginatorBean;
	}
	
}
