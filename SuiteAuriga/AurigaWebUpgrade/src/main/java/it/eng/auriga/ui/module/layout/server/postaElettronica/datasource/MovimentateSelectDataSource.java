/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "MovimentateSelectDataSource")
public class MovimentateSelectDataSource extends OptionFetchDataSource<UtenteBean> {

	@Override
	public PaginatorBean<UtenteBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String cognomeNome = "";
		String username = "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("cognomeNome")) {
					cognomeNome = (String) criterion.getValue();
				} else if (criterion.getFieldName().equals("username")) {
					username = (String) criterion.getValue();
				}
			}
		}

		// Input
		DmpkLoadComboDmfn_load_comboBean inputParamComboBean = new DmpkLoadComboDmfn_load_comboBean();
		inputParamComboBean.setTipocomboin("UTENTE_MAIL");
		String altriParametri = "STR_IN_DES|*|" + cognomeNome + "|*|STR_IN_USERNAME|*|" + username;
		if(StringUtils.isNotBlank(getIdValoreUnico())) {
			altriParametri += "|*|ID_VALORE_UNICO|*|" + getIdValoreUnico();
		}
		inputParamComboBean.setAltriparametriin(altriParametri);

		inputParamComboBean.setFlgsolovldin(null);

		// Output
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), inputParamComboBean);

		String xmlLista = storeResult.getResultBean().getListaxmlout();

		List<UtenteBean> resultList = new ArrayList<UtenteBean>();

		List<UtenteBean> listaXML = XmlListaUtility.recuperaLista(xmlLista, UtenteBean.class);
		resultList = new ArrayList<UtenteBean>();

		String skip = getExtraparams().get("skipAction");

		if (!"true".equals(skip)) {
			// Aggiunta del tipo azione 'ANY'
			UtenteBean lBeanAny = new UtenteBean();
			lBeanAny.setCodice("#ME");
			lBeanAny.setCognomeNome("<i>da me</i>");
			lBeanAny.setIdUtente("#ME");

			resultList.add(lBeanAny);
		}

		for (UtenteBean lXmlListaSimpleBean : listaXML) {
			UtenteBean lBean = new UtenteBean();
			lBean.setIdUtente(lXmlListaSimpleBean.getIdUtente());
			lBean.setCognomeNome(lXmlListaSimpleBean.getCognomeNome());
			lBean.setUsername(lXmlListaSimpleBean.getCodice());

			resultList.add(lBean);
		}

		PaginatorBean<UtenteBean> lPaginatorBean = new PaginatorBean<UtenteBean>();
		lPaginatorBean.setData(resultList);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(resultList.size());
		lPaginatorBean.setTotalRows(resultList.size());

		return lPaginatorBean;
	}
}
