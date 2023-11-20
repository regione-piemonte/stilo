/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean.SocietaStatoCaricamentoRubricheComboFilterBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "SocietaStatoCaricamentoRubricheComboFilterDataSource")
public class SocietaStatoCaricamentoRubricheComboFilterDataSource
		extends OptionFetchDataSource<SocietaStatoCaricamentoRubricheComboFilterBean> {

	@Override
	public PaginatorBean<SocietaStatoCaricamentoRubricheComboFilterBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		BigDecimal flgSoloVld = StringUtils.isNotBlank(getExtraparams().get("flgSoloVld"))
				? new BigDecimal(getExtraparams().get("flgSoloVld")) : null;

		List<SocietaStatoCaricamentoRubricheComboFilterBean> lListResult = new ArrayList<SocietaStatoCaricamentoRubricheComboFilterBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("SOCIETA");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(
				"ID_USER_LAVORO|*|" + (AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null
						? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "") + "");

		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(flgSoloVld);
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo
				.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();

		if (StringUtils.isNotBlank(xmlLista)) {
			lListResult = XmlListaUtility.recuperaLista(xmlLista, SocietaStatoCaricamentoRubricheComboFilterBean.class);
		}
		PaginatorBean<SocietaStatoCaricamentoRubricheComboFilterBean> lPaginatorBean = new PaginatorBean<SocietaStatoCaricamentoRubricheComboFilterBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;
	}

}
