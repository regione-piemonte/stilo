/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

@Datasource(id = "LoadComboTipoOrganigrammaDataSource")
public class LoadComboTipoOrganigrammaDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		int nroLivello = StringUtils.isNotBlank(getExtraparams().get("nroLivello")) ? Integer.parseInt(getExtraparams().get("nroLivello")) : 1;

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPO_UO");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("INTERNAL_VALUE|*|CID|*|LIV_GERARCHICO|*|" + nroLivello);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();

		if (lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);
		}

		else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<XmlListaSimpleBean> lista = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);
			for (XmlListaSimpleBean lRiga : lista) {
				SimpleKeyValueBean lTipoOrganigrammaBean = new SimpleKeyValueBean();
				lTipoOrganigrammaBean.setKey(lRiga.getKey());
				lTipoOrganigrammaBean.setValue(lRiga.getValue());
				lPaginatorBean.addRecord(lTipoOrganigrammaBean);
			}
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());
		}
		return lPaginatorBean;
	}
}