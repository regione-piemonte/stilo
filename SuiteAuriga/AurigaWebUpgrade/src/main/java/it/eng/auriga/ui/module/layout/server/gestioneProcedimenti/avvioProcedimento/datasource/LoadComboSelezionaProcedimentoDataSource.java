/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.avvioProcedimento.bean.SelezionaProcedimentoBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
@Datasource(id = "LoadComboSelezionaProcedimentoDataSource")
public class LoadComboSelezionaProcedimentoDataSource extends AbstractFetchDataSource<SelezionaProcedimentoBean>{

	@Override
	public PaginatorBean<SelezionaProcedimentoBean> fetch(
			AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		String nomeCriteria = "";
		if (criteria.getCriterionByFieldName("nome") != null){
			nomeCriteria = (String) criteria.getCriterionByFieldName("nome").getValue();
		}

		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPO_PROCEDIMENTO");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		if (StringUtils.isEmpty(nomeCriteria)) nomeCriteria = "";
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + (StringUtils.isNotEmpty(AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro())?
				AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro():"") + 
				"|*|FLG_SOLO_ASSEGNABILI|*|1|*|STR_IN_DES|*|" + nomeCriteria);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();

		List<SimpleKeyValueBean> lListResult = XmlUtility.recuperaListaSemplice(xmlLista);
		List<SelezionaProcedimentoBean> lListResultProcedimenti = new ArrayList<SelezionaProcedimentoBean>();
		for (SimpleKeyValueBean lSimpleKeyValueBean : lListResult){
			SelezionaProcedimentoBean lSelezionaProcedimentoBean = new SelezionaProcedimentoBean();
			lSelezionaProcedimentoBean.setIdProcessType(lSimpleKeyValueBean.getKey());
			lSelezionaProcedimentoBean.setNome(lSimpleKeyValueBean.getValue());
			lListResultProcedimenti.add(lSelezionaProcedimentoBean);
		}
		PaginatorBean<SelezionaProcedimentoBean> result = new PaginatorBean<SelezionaProcedimentoBean>(lListResultProcedimenti);
		return result;
	}

}
