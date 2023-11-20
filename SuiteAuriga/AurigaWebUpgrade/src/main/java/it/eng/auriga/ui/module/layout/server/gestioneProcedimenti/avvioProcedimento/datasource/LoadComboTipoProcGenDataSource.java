/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.avvioProcedimento.bean.TipoProcGenBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
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

@Datasource(id = "LoadComboTipoProcGenDataSource")
public class LoadComboTipoProcGenDataSource extends AbstractFetchDataSource<TipoProcGenBean> {

	@Override
	public PaginatorBean<TipoProcGenBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String flgDocFolder = StringUtils.isNotBlank(getExtraparams().get("flgDocFolder")) ? getExtraparams().get("flgDocFolder") : "";
		String tipo = StringUtils.isNotBlank(getExtraparams().get("tipo")) ? getExtraparams().get("tipo") : "";
		
		String nomeTipoProc = "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("nomeTipoProc")) {
					nomeTipoProc = (String) criterion.getValue();
				}
			}
		}

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("PROC_CON_FLUSSO_WF_X_TIPO_DOC_FOLDER");
		
		String altriParametri = "FLG_SOLO_ASSEGNABILI|*|1|*|ID_USER_LAVORO|*|" + (loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "");
		if(flgDocFolder != null && "D".equals(flgDocFolder)) {
			altriParametri = "ID_DOC_TYPE|*|" + tipo + "|*|FLG_SOLO_ASSEGNABILI|*|1|*|ID_USER_LAVORO|*|" + (loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "");
		}
		if(flgDocFolder != null && "F".equals(flgDocFolder)) {
			altriParametri = "ID_FOLDER_TYPE|*|" + tipo + "|*|FLG_SOLO_ASSEGNABILI|*|1|*|ID_USER_LAVORO|*|" + (loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "");
		}
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(null);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),	loginBean, lDmpkLoadComboDmfn_load_comboBean);

		PaginatorBean<TipoProcGenBean> lPaginatorBean = new PaginatorBean<TipoProcGenBean>();

		if (lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<TipoProcGenBean> lista = XmlListaUtility.recuperaLista(xmlLista, TipoProcGenBean.class);
			lPaginatorBean.setData(lista);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());
		}

		return lPaginatorBean;

	}

}
