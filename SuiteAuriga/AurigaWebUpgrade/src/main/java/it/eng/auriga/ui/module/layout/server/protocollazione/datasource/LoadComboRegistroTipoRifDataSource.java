/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "LoadComboRegistroTipoRifDataSource")
public class LoadComboRegistroTipoRifDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboRegistroTipoRifDataSource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		
		String registroTipoRif = StringUtils.isNotBlank(getExtraparams().get("registroTipoRif")) ? getExtraparams().get("registroTipoRif") : "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("registroTipoRif")) {
					registroTipoRif = (String) criterion.getValue();
					break;
				}
			}
		}

		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("VALORI_DIZIONARIO");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("DICTIONARY_ENTRY|*|REGISTRO_TIPO_RIF_UD|*|CI_TO_ADD|*|" + registroTipoRif + "|*|ID_USER_LAVORO|*|"  + idUserLavoro);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();

		try {
			List<XmlListaSimpleBean> lista = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);

			for (XmlListaSimpleBean lRiga : lista) {
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lRiga.getKey());
				lSimpleKeyValueBean.setValue(lRiga.getValue());
				lListResult.add(lSimpleKeyValueBean);
			}
		} catch (Exception e) {
			mLogger.warn(e);
		}

		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;
	}

}
