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
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

@Datasource(id = "LoadComboPuntiProtocolloDataSource")
public class LoadComboPuntiProtocolloDataSource extends SelectDataSource<OrganigrammaBean> {

	@Override
	public PaginatorBean<OrganigrammaBean> realFetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUser() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUser().toString()
				: "";
		// String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ?
		// AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "";

		String flgUoSv = StringUtils.isNotBlank(getExtraparams().get("flgUoSv")) ? getExtraparams().get("flgUoSv") : "";
		String idUoSv = StringUtils.isNotBlank(getExtraparams().get("idUoSv")) ? getExtraparams().get("idUoSv") : "";

		String finalita = StringUtils.isNotBlank(getExtraparams().get("finalita")) ? getExtraparams().get("finalita") : "";

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

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ORGANIGRAMMA");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FLG_NODO_TYPE|*|UO|*|SOLO_PUNTI_PROT|*|1|*|ID_USER_LAVORO|*|" + idUserLavoro
				+ "|*|NRI_LIVELLI_UO|*|" + codice + "|*|STR_IN_DES|*|" + descrizione + "|*|CI_TO_ADD|*|" + flgUoSv + idUoSv);

		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		PaginatorBean<OrganigrammaBean> lPaginatorBean = new PaginatorBean<OrganigrammaBean>();

		if (lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<OrganigrammaBean> lList = new ArrayList<OrganigrammaBean>();
			for (OrganigrammaBean lOrganigrammaBean : XmlListaUtility.recuperaLista(xmlLista, OrganigrammaBean.class)) {
				lOrganigrammaBean.setId(lOrganigrammaBean.getTypeNodo() + lOrganigrammaBean.getIdUo());
				lOrganigrammaBean.setIconaTypeNodo(lOrganigrammaBean.getTypeNodo());
				if ("UO".equals(lOrganigrammaBean.getTypeNodo())) {
					lOrganigrammaBean.setIconaTypeNodo("UO_" + lOrganigrammaBean.getTipoUo());
					lOrganigrammaBean.setDescrizione("<b>" + lOrganigrammaBean.getDescrizione() + "</b>");
					lOrganigrammaBean.setDescrizioneEstesa("<b>" + lOrganigrammaBean.getDescrizioneEstesa() + "</b>");
				}
				if (StringUtils.isBlank(finalita)) {
					lOrganigrammaBean.setFlgSelXFinalita(null);
				} else if (lOrganigrammaBean.getFlgSelXFinalita() == null || !"1".equals(lOrganigrammaBean.getFlgSelXFinalita())) {
					lOrganigrammaBean.setFlgSelXFinalita("0");
					lOrganigrammaBean.setDescrizione("<span style=\"color:#F54227\">" + lOrganigrammaBean.getDescrizione() + "</span>");
					lOrganigrammaBean.setDescrizioneEstesa("<span style=\"color:#F54227\">" + lOrganigrammaBean.getDescrizioneEstesa() + "</span>");
				} else {
					lOrganigrammaBean.setDescrizione("<span style=\"color:#1D66B2\">" + lOrganigrammaBean.getDescrizione() + "</span>");
					lOrganigrammaBean.setDescrizioneEstesa("<span style=\"color:#1D66B2\">" + lOrganigrammaBean.getDescrizioneEstesa() + "</span>");
				}
				lList.add(lOrganigrammaBean);
			}
			lPaginatorBean.setData(lList);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lList.size());
			lPaginatorBean.setTotalRows(lList.size());
		}
		return lPaginatorBean;
	}

}
