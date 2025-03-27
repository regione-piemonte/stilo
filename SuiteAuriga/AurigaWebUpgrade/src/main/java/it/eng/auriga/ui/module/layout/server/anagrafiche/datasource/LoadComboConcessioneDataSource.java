/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.anagrafiche.datasource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ConcessioneComboXmlBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.SelectDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboConcessioneDataSource")
public class LoadComboConcessioneDataSource extends SelectDataSource<ConcessioneComboXmlBean> {

	private static Logger mLogger = Logger.getLogger(LoadComboConcessioneDataSource.class);

	@Override
	public PaginatorBean<ConcessioneComboXmlBean> realFetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		boolean flgAncheNonVld = StringUtils.isNotBlank(getExtraparams().get("flgAncheNonVld")) && "true".equals(getExtraparams().get("flgAncheNonVld"));
		String codice = StringUtils.isNotBlank(getExtraparams().get("codice")) ? getExtraparams().get("codice") : "";
		String descrizione = StringUtils.isNotBlank(getExtraparams().get("descrizione")) ? getExtraparams().get("descrizione") : "";
		
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
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("VALORI_DIZIONARIO");		
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(flgAncheNonVld ? null : new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		
		String altriParametri = "DICTIONARY_ENTRY|*|CONCESSIONI|*|STR_IN_DES|*|"  +  descrizione + "|*|STR_IN_CODICE|*|" + codice;
		
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);		
		
		// Eseguo il servizio 
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		List<ConcessioneComboXmlBean> lListResult = new ArrayList<ConcessioneComboXmlBean>();
			
		if (StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				
				try {
					lListResult =  XmlListaUtility.recuperaLista(xmlLista, ConcessioneComboXmlBean.class);
				} catch (Exception e) {
					mLogger.warn(e);
				}
		} else {
				mLogger.error(lStoreResultBean.getDefaultMessage());
		}

		PaginatorBean<ConcessioneComboXmlBean> lPaginatorBean = new PaginatorBean<ConcessioneComboXmlBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;
	}
}
