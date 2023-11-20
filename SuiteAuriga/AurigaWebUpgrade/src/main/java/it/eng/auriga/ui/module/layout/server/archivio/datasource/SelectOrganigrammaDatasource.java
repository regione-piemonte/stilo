/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "SelectOrganigrammaDatasource")
public class SelectOrganigrammaDatasource extends SelectDataSource<OrganigrammaBean> {
	
	private static Logger mLogger = Logger.getLogger(SelectOrganigrammaDatasource.class);

	@Override
	public PaginatorBean<OrganigrammaBean> realFetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro()
				: "";
		String ciToAdd = StringUtils.isNotBlank(getExtraparams().get("ciToAdd")) ? getExtraparams().get("ciToAdd") : "";

		String codice = StringUtils.isNotBlank(getExtraparams().get("codice")) ? getExtraparams().get("codice") : "";
		String descrizione = "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("descrizione")) {
					descrizione = (String) criterion.getValue();
				}
			}
		}

		List<OrganigrammaBean> lListResult = new ArrayList<OrganigrammaBean>();
		PaginatorBean<OrganigrammaBean> lPaginatorBean = new PaginatorBean<OrganigrammaBean>();

		if (!"".equals(codice) || !"".equals(ciToAdd)) {

			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

			String flgNodoType = null;
			if (StringUtils.isNotBlank(getExtraparams().get("flgNodoTypeParamDB"))) {
				flgNodoType = ParametriDBUtil.getParametroDB(getSession(), getExtraparams().get("flgNodoTypeParamDB"));
			}
			if (StringUtils.isBlank(flgNodoType)) {
				flgNodoType = StringUtils.isNotBlank(getExtraparams().get("flgNodoType")) ? getExtraparams().get("flgNodoType") : "UO;SV";
			}

			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ORGANIGRAMMA");
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro + "|*|FLG_NODO_TYPE|*|" + flgNodoType
					+ "|*|NRI_LIVELLI_UO|*|" + codice + "|*|STR_IN_DES|*|" + descrizione + "|*|CI_TO_ADD|*|" + ciToAdd);
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(null);

			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
					AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

			if (StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				try {
					for (OrganigrammaBean lOrganigrammaBean : XmlListaUtility.recuperaLista(xmlLista, OrganigrammaBean.class)) {
						lOrganigrammaBean.setId(lOrganigrammaBean.getTypeNodo() + lOrganigrammaBean.getIdUo());
						lOrganigrammaBean.setDescrizioneOrig(lOrganigrammaBean.getDescrizione());
						lOrganigrammaBean.setIconaTypeNodo(lOrganigrammaBean.getTypeNodo());
						if ("UO".equals(lOrganigrammaBean.getTypeNodo())) {
							lOrganigrammaBean.setIconaTypeNodo("UO_" + lOrganigrammaBean.getTipoUo());
							lOrganigrammaBean.setDescrizione("<b>" + lOrganigrammaBean.getDescrizione() + "</b>");
						}
						lOrganigrammaBean.setDisplayValue(lOrganigrammaBean.getCodice() + " ** " + lOrganigrammaBean.getDescrizione());
						lListResult.add(lOrganigrammaBean);
					}
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
