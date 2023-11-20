/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.OrganigrammaBean;
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

@Datasource(id = "LoadComboUoCollegateUtenteAdminDatasource")
public class LoadComboUoCollegateUtenteAdminDatasource extends SelectDataSource<OrganigrammaBean> {

	private static Logger mLogger = Logger.getLogger(LoadComboUoCollegateUtenteAdminDatasource.class);

	@Override
	public PaginatorBean<OrganigrammaBean> realFetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro()
				: "";
		String ciToAdd = StringUtils.isNotBlank(getExtraparams().get("ciToAdd")) ? getExtraparams().get("ciToAdd") : "";

		if ("".equals(ciToAdd)) {
			String flgUoSv = StringUtils.isNotBlank(getExtraparams().get("flgUoSv")) ? getExtraparams().get("flgUoSv") : "";
			String idUoSv = StringUtils.isNotBlank(getExtraparams().get("idUoSv")) ? getExtraparams().get("idUoSv") : "";
			ciToAdd = flgUoSv + idUoSv;
		}

		String idUd = StringUtils.isNotBlank(getExtraparams().get("idUd")) ? getExtraparams().get("idUd") : "";
		String idProcedimento = StringUtils.isNotBlank(getExtraparams().get("idProcedimento")) ? getExtraparams().get("idProcedimento") : "";
		String tipoAssegnatari = StringUtils.isNotBlank(getExtraparams().get("tipoAssegnatari")) ? getExtraparams().get("tipoAssegnatari") : "UO";
		String finalita = StringUtils.isNotBlank(getExtraparams().get("finalita")) ? getExtraparams().get("finalita") : "";

		String codice = StringUtils.isNotBlank(getExtraparams().get("codice")) ? getExtraparams().get("codice") : "";
		String descrizione = StringUtils.isNotBlank(getExtraparams().get("descrizione")) ? getExtraparams().get("descrizione") : "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (!ParametriDBUtil.getParametroDBAsBoolean(getSession(), "DIM_ORGANIGRAMMA_NONSTD") && criterion.getFieldName().equals("codice")) {
					codice = (String) criterion.getValue();
				} else if (criterion.getFieldName().equals("descrizione")) {
					descrizione = (String) criterion.getValue();
				}
			}
		}

		List<OrganigrammaBean> lListResult = new ArrayList<OrganigrammaBean>();
		PaginatorBean<OrganigrammaBean> lPaginatorBean = new PaginatorBean<OrganigrammaBean>();

		if (!ParametriDBUtil.getParametroDBAsBoolean(getSession(), "DIM_ORGANIGRAMMA_NONSTD") || !"".equals(codice)/* || !"".equals(ciToAdd)*/) {

			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ORGANIGRAMMA");
			String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro + "|*|FLG_NODO_TYPE|*|" + tipoAssegnatari + "|*|NRI_LIVELLI_UO|*|" + codice
					+ "|*|STR_IN_DES|*|" + descrizione + "|*|CI_TO_ADD|*|" + ciToAdd + "|*|FINALITA|*|" + finalita;
			if (StringUtils.isNotBlank(idUd)) {
				altriParametri += "|*|TY_OBJ_TO_ASS|*|U|*|ID_OBJ_TO_ASS|*|" + idUd;
			}
			if (StringUtils.isNotBlank(idProcedimento)) {
				altriParametri += "|*|ID_PROCESS|*|" + idProcedimento;
			}
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
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
							lOrganigrammaBean.setDescrizioneEstesa("<b>" + lOrganigrammaBean.getDescrizioneEstesa() + "</b>");
						}
						if (StringUtils.isBlank(finalita)) {
							lOrganigrammaBean.setFlgSelXFinalita(null);
						} else if (lOrganigrammaBean.getFlgSelXFinalita() == null || !"1".equals(lOrganigrammaBean.getFlgSelXFinalita())) {
							lOrganigrammaBean.setFlgSelXFinalita("0");
							lOrganigrammaBean.setDescrizione("<span style=\"color:gray\">" + lOrganigrammaBean.getDescrizione() + "</span>");
							lOrganigrammaBean.setDescrizioneEstesa("<span style=\"color:gray\">" + lOrganigrammaBean.getDescrizioneEstesa() + "</span>");
						} else {
							lOrganigrammaBean.setDescrizione("<span style=\"color:#1D66B2\">" + lOrganigrammaBean.getDescrizione() + "</span>");
							lOrganigrammaBean.setDescrizioneEstesa("<span style=\"color:#1D66B2\">" + lOrganigrammaBean.getDescrizioneEstesa() + "</span>");
						}
						lListResult.add(lOrganigrammaBean);
					}
				} catch (Exception e) {
					mLogger.warn(e);
				}
			} else {
				mLogger.error(lStoreResultBean.getDefaultMessage());
			}

		}

		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;
	}

}
