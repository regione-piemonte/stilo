/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;


@Datasource(id = "AzioneDaFareSelectDataSource")
public class AzioneDaFareSelectDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		boolean isFromFilter = StringUtils.isNotBlank(getExtraparams().get("isFromFilter")) && "true".equalsIgnoreCase(getExtraparams().get("isFromFilter"));

		// Input
		DmpkLoadComboDmfn_load_comboBean inputParamComboBean = new DmpkLoadComboDmfn_load_comboBean();
		inputParamComboBean.setTipocomboin("GENERICO");
		inputParamComboBean.setAltriparametriin("NOME_TABELLA|*|T_EMAIL_MGO|*|NOME_COL_TABELLA|*|COD_AZIONE_DA_FARE|*|ID_USER_LAVORO|*|"  + idUserLavoro);
		inputParamComboBean.setFlgsolovldin(isFromFilter ? null : new BigDecimal(1));

		// Output
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), inputParamComboBean);

		String xmlLista = storeResult.getResultBean().getListaxmlout();

		List<SimpleKeyValueBean> resultList = new ArrayList<SimpleKeyValueBean>();

		List<XmlListaSimpleBean> listaXML = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);
		resultList = new ArrayList<SimpleKeyValueBean>();

		String skip = getExtraparams().get("skipAction");

		if (!"true".equals(skip)) {
			// Aggiunta del tipo azione 'NESSUNA AZIONE'
			SimpleKeyValueBean lBeanNone = new SimpleKeyValueBean();
			lBeanNone.setKey("NONE");
			lBeanNone.setValue("<i>nessuna azione</i>");
			resultList.add(lBeanNone);

			// Aggiunta del tipo azione 'ANY'
			SimpleKeyValueBean lBeanAny = new SimpleKeyValueBean();
			lBeanAny.setKey("ANY");
			lBeanAny.setValue("<i>qualsiasi azione</i>");
			resultList.add(lBeanAny);
		}

		for (XmlListaSimpleBean lXmlListaSimpleBean : listaXML) {
			SimpleKeyValueBean lBean = new SimpleKeyValueBean();
			lBean.setKey(lXmlListaSimpleBean.getKey());
			lBean.setValue(lXmlListaSimpleBean.getValue());
			resultList.add(lBean);
		}

		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(resultList);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(resultList.size());
		lPaginatorBean.setTotalRows(resultList.size());

		return lPaginatorBean;
	}
}
