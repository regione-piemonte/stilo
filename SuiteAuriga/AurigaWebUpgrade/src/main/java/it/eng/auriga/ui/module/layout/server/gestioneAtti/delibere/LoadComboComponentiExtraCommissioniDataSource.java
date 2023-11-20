/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.DichiarazioneVotiSelectOdgXmlBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id="LoadComboComponentiExtraCommissioniDataSource")
public class LoadComboComponentiExtraCommissioniDataSource extends AbstractFetchDataSource<DichiarazioneVotiSelectOdgXmlBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboDichiarazioneVotiDataSource.class);

	@Override
	public PaginatorBean<DichiarazioneVotiSelectOdgXmlBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		List<DichiarazioneVotiSelectOdgXmlBean> lListResult = new ArrayList<DichiarazioneVotiSelectOdgXmlBean>();
		String idSeduta = StringUtils.isNotBlank(getExtraparams().get("idSeduta")) ? getExtraparams().get("idSeduta") : "";
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("PRESENTI_AGG_SEDUTA_COMMISSIONE");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_SEDUTA|*|"+idSeduta);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		try {
			// DA CAPIRE COME FARE PER LE 3 COLONNE. PROVARE A VEDERE lListResult = XmlUtility.recuperaListaSempliceSubstring(xmlLista);
			lListResult = XmlListaUtility.recuperaLista(xmlLista, DichiarazioneVotiSelectOdgXmlBean.class);
		} catch (Exception e) {
			mLogger.warn(e);
		}
		
		PaginatorBean<DichiarazioneVotiSelectOdgXmlBean> lPaginatorBean = new PaginatorBean<DichiarazioneVotiSelectOdgXmlBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}
}