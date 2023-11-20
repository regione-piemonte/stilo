/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ResponsabileProcBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.util.List;

import org.apache.commons.lang.StringUtils;

@Datasource(id = "LoadComboResponsabileProcDataSource")
public class LoadComboResponsabileProcDataSource extends AbstractFetchDataSource<ResponsabileProcBean> {

	@Override
	public PaginatorBean<ResponsabileProcBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String idUserRespProc = StringUtils.isNotBlank(getExtraparams().get("idUserRespProc")) ? getExtraparams().get("idUserRespProc") : "";
		String uoProponente = StringUtils.isNotBlank(getExtraparams().get("uoProponente")) ? getExtraparams().get("uoProponente") : "";
		if (uoProponente.startsWith("UO")) {
			uoProponente = uoProponente.substring(2);
		}

		String desUserRespProc = "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("desUserRespProc")) {
					desUserRespProc = (String) criterion.getValue();
				}
			}
		}

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("RESP_PROC");

		String altriParametri = "ID_UO|*|" + uoProponente;
		// Se l'utente ha digitato un filtro, il CI_TO_ADD non deve essere passato
		// if (StringUtils.isNotBlank(desUserRespProc)) {
		// altriParametri += "|*|STR_IN_DES|*|" + desUserRespProc;
		// } else {
		// altriParametri += "|*|CI_TO_ADD|*|" + idUserRespProc;
		// }

		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		PaginatorBean<ResponsabileProcBean> lPaginatorBean = new PaginatorBean<ResponsabileProcBean>();

		if (lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<XmlListaSimpleBean> lista = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);

			for (XmlListaSimpleBean lRiga : lista) {
				ResponsabileProcBean lResponsabileProcBean = new ResponsabileProcBean();
				lResponsabileProcBean.setIdUserRespProc(lRiga.getKey());
				lResponsabileProcBean.setDesUserRespProc(lRiga.getValue());
				lPaginatorBean.addRecord(lResponsabileProcBean);
			}

			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());
		}

		return lPaginatorBean;

	}

}
