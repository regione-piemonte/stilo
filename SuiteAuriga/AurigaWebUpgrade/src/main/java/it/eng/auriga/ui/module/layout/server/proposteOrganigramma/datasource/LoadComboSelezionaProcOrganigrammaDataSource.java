/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.proposteOrganigramma.bean.SelezionaProcOrganigrammaBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
@Datasource(id = "LoadComboSelezionaProcOrganigrammaDataSource")
public class LoadComboSelezionaProcOrganigrammaDataSource extends AbstractFetchDataSource<SelezionaProcOrganigrammaBean>{

	private static Logger mLogger = Logger.getLogger(LoadComboSelezionaProcOrganigrammaDataSource.class);

	@Override
	public PaginatorBean<SelezionaProcOrganigrammaBean> fetch(
			AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "";
		
		String nomeCriteria = "";
		if (criteria.getCriterionByFieldName("nome") != null) {
			nomeCriteria = (String) criteria.getCriterionByFieldName("nome").getValue();
		}
		if (StringUtils.isBlank(nomeCriteria))  {
			nomeCriteria = "";
		}
		
		List<SelezionaProcOrganigrammaBean> lListResult = new ArrayList<SelezionaProcOrganigrammaBean>();
		PaginatorBean<SelezionaProcOrganigrammaBean> lPaginatorBean = new PaginatorBean<SelezionaProcOrganigrammaBean>();

		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPO_PROCEDIMENTO");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro + "|*|FLG_SOLO_ASSEGNABILI|*|1|*|STR_IN_DES|*|" + nomeCriteria + "|*|CATEGORIA|*|REVISIONE_ORGANIGRAMMA");

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		if(!lStoreResultBean.isInError()) {
			try {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				lListResult = XmlListaUtility.recuperaLista(xmlLista, SelezionaProcOrganigrammaBean.class);				
			} catch(Exception e) {
				mLogger.error(e.getMessage(), e);
			}
		} else if (StringUtils.isNotBlank(lStoreResultBean.getDefaultMessage())) {
			mLogger.error(lStoreResultBean.getDefaultMessage());
		} 

		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		return lPaginatorBean;
	}

}
