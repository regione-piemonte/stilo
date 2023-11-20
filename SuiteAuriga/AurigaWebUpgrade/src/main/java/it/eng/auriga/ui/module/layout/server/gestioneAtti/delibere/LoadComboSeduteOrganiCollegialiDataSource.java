/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.OrganiCollegialiBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id="LoadComboSeduteOrganiCollegialiDataSource")
public class LoadComboSeduteOrganiCollegialiDataSource extends AbstractFetchDataSource<OrganiCollegialiBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboConvocazioneCommissioneSource.class);

	@Override
	public PaginatorBean<OrganiCollegialiBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		// IdUserLavoro valorizzato = Utente delegante ( se si lavora in delega ).
	    // IdUser valorizzato = Utente loggato( se non si lavora in delega )
		String idUserLavoro = loginBean.getIdUserLavoro();
		String idUser = loginBean.getIdUser() != null && !"".equals(loginBean.getIdUser().toString()) ? loginBean.getIdUser().toString() : "";
		String ID_USER_LAVORO = idUserLavoro != null ? idUserLavoro : idUser;
		
		String organo = getExtraparams().get("organo"); 	// giunta, consiglio, commissione
		String finalita = getExtraparams().get("finalita"); // CONVOCAZIONE, DISCUSSIONE, CONSULTAZIONE_STORICO
		
		List<OrganiCollegialiBean> lListResult = new ArrayList<OrganiCollegialiBean>();
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("SEDUTE_ORGANI_COLLEGIALI");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ORGANO|*|" + organo + "|*|FINALITA|*|" + finalita + "|*|ID_USER_LAVORO|*|" + ID_USER_LAVORO);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		try {
			lListResult = XmlListaUtility.recuperaLista(xmlLista, OrganiCollegialiBean.class);
		} catch (Exception e) {
			mLogger.warn(e);
		}
		
		PaginatorBean<OrganiCollegialiBean> lPaginatorBean = new PaginatorBean<OrganiCollegialiBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}
}