/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.LegendaOrganigrammaDinamicoBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LegendaOrganigrammaDinamicoDataSource")
public class LegendaOrganigrammaDinamicoDataSource extends OptionFetchDataSource<LegendaOrganigrammaDinamicoBean> {

	private static Logger mLogger = Logger.getLogger(LegendaOrganigrammaDinamicoDataSource.class);

	@Override
	public PaginatorBean<LegendaOrganigrammaDinamicoBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		
		List<LegendaOrganigrammaDinamicoBean> lListResult = new ArrayList<LegendaOrganigrammaDinamicoBean>();
		PaginatorBean<LegendaOrganigrammaDinamicoBean> lPaginatorBean = new PaginatorBean<LegendaOrganigrammaDinamicoBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		String altriParametri = "DICTIONARY_ENTRY|*|TIPO_UO|*|ID_USER_LAVORO|*|"  + idUserLavoro;
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("VALORI_DIZIONARIO");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		if (StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			try {
				for (LegendaOrganigrammaDinamicoBean lLegendaOrganigrammaDinamicoBean : XmlListaUtility.recuperaLista(xmlLista,
						LegendaOrganigrammaDinamicoBean.class)) {
					lLegendaOrganigrammaDinamicoBean.setDescrizione(lLegendaOrganigrammaDinamicoBean.getDescrizione());
					lListResult.add(lLegendaOrganigrammaDinamicoBean);
				}
			} catch (Exception e) {
				mLogger.error(e.getMessage(), e);
			}
		} else {
			mLogger.error(lStoreResultBean.getDefaultMessage());
		}

		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;
	}

}
