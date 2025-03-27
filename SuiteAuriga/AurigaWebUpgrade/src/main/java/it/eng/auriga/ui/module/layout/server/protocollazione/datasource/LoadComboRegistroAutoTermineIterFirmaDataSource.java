/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.GruppiRepertorioBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

/**
 * 
 * @author DANCRIST
 *
 */

@Datasource(id="LoadComboRegistroAutoTermineIterFirmaDataSource")
public class LoadComboRegistroAutoTermineIterFirmaDataSource extends AbstractFetchDataSource<GruppiRepertorioBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboRegistroAutoTermineIterFirmaDataSource.class);

	@Override
	public PaginatorBean<GruppiRepertorioBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		
		String tipoDocumento = getExtraparams().get("tipoDocumento") != null && !"".equals(getExtraparams().get("tipoDocumento"))	? getExtraparams().get("tipoDocumento") : "";
		String flgTipoProv = getExtraparams().get("flgTipoProv") != null && !"".equals(getExtraparams().get("flgTipoProv"))	? getExtraparams().get("flgTipoProv") : "";
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro + "|*|CATEGORIA|*|#U|*|ID_DOC_TYPE|*|" + tipoDocumento + "|*|VERSO_REG|*|" + flgTipoProv);
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("GRUPPI_REG_NUM_UD");
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		List<GruppiRepertorioBean> lListResult = new ArrayList<GruppiRepertorioBean>();
		try {
			lListResult = XmlListaUtility.recuperaLista(xmlLista, GruppiRepertorioBean.class);
		} catch (Exception e) {
			mLogger.warn(e);
		}
		
		PaginatorBean<GruppiRepertorioBean> lPaginatorBean = new PaginatorBean<GruppiRepertorioBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}
}
