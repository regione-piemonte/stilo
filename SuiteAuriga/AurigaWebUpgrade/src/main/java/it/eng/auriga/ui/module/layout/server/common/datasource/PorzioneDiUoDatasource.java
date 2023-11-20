/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.AssegnatoNotificatoABean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.OrganigrammaBean;
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

import org.apache.commons.lang.StringUtils;

@Datasource(id = "PorzioneDiUoDatasource")
public class PorzioneDiUoDatasource extends OptionFetchDataSource<AssegnatoNotificatoABean> {

	@Override
	public PaginatorBean<AssegnatoNotificatoABean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro()
				: "";
		String flgUoSv = StringUtils.isNotBlank(getExtraparams().get("flgUoSv")) ? getExtraparams().get("flgUoSv") : "";
		String idUoSv = StringUtils.isNotBlank(getExtraparams().get("idUoSv")) ? getExtraparams().get("idUoSv") : "";

		String codice = "";
		String descrizione = "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("codice")) {
					codice = (String) criterion.getValue();
				} else if (criterion.getFieldName().equals("descrizione")) {
					descrizione = (String) criterion.getValue();
				}
			}
		}

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		String tipoAssegnatari = "UO";
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ORGANIGRAMMA");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro + "|*|FLG_NODO_TYPE|*|" + tipoAssegnatari
				+ "|*|NRI_LIVELLI_UO|*|" + codice + "|*|STR_IN_DES|*|" + descrizione + "|*|CI_TO_ADD|*|" + flgUoSv + idUoSv);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(null);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		PaginatorBean<AssegnatoNotificatoABean> lPaginatorBean = new PaginatorBean<AssegnatoNotificatoABean>();

		if (lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<AssegnatoNotificatoABean> lList = new ArrayList<AssegnatoNotificatoABean>();
			for (OrganigrammaBean lOrganigrammaBean : XmlListaUtility.recuperaLista(xmlLista, OrganigrammaBean.class)) {
				AssegnatoNotificatoABean lAssegnatoNotificatoABean = new AssegnatoNotificatoABean();
				lAssegnatoNotificatoABean.setChiave(lOrganigrammaBean.getTypeNodo() + lOrganigrammaBean.getIdUo());
				lAssegnatoNotificatoABean.setCodice(lOrganigrammaBean.getCodice());
				lAssegnatoNotificatoABean.setTypeNodo(lOrganigrammaBean.getTipoUo());
				lAssegnatoNotificatoABean.setIconaTypeNodo(lOrganigrammaBean.getTypeNodo());
				if ("UO".equals(lOrganigrammaBean.getTypeNodo())) {
					lAssegnatoNotificatoABean.setIconaTypeNodo("UO_" + lOrganigrammaBean.getTipoUo());
					lAssegnatoNotificatoABean.setTipoUo(lOrganigrammaBean.getTipoUo());
					lAssegnatoNotificatoABean.setDescrizione("<b>" + lOrganigrammaBean.getDescrizione() + "</b>");
				} else {
					lAssegnatoNotificatoABean.setDescrizione(lOrganigrammaBean.getDescrizione());
				}
				lAssegnatoNotificatoABean.setDisplayValue(lAssegnatoNotificatoABean.getCodice() + " ** " + lAssegnatoNotificatoABean.getDescrizione());
				lList.add(lAssegnatoNotificatoABean);
			}
			lPaginatorBean.setData(lList);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lList.size());
			lPaginatorBean.setTotalRows(lList.size());
		}
		return lPaginatorBean;
	}

}
