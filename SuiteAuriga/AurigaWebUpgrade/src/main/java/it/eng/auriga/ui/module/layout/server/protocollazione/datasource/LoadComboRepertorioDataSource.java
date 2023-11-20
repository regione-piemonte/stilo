/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

@Datasource(id="LoadComboRepertorioDataSource")
public class LoadComboRepertorioDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboRepertorioDataSource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		
		String flgAncheNonAssegnabili = getExtraparams().get("flgAncheNonAssegnabili");
		String tipologia = getExtraparams().get("tipologia");
		
		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("REGISTRO_REG_NUM_UD");		
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		if(StringUtils.isNotBlank(flgAncheNonAssegnabili) && "1".equals(flgAncheNonAssegnabili)) {		
			if(tipologia != null && "I".equalsIgnoreCase(tipologia)) {
				lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FLG_SOLO_ASSEGNABILI|*||*|CATEGORIA|*|I|*|ID_USER_LAVORO|*|"+idUserLavoro);
			} else {
				lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FLG_SOLO_ASSEGNABILI|*||*|CATEGORIA|*|R|*|ID_USER_LAVORO|*|"+idUserLavoro);
			}
		} else {			
			if(tipologia != null && "I".equalsIgnoreCase(tipologia)) {
				lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FLG_SOLO_ASSEGNABILI|*|1|*|CATEGORIA|*|I|*|ID_USER_LAVORO|*|"+idUserLavoro);
			} else {
				lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FLG_SOLO_ASSEGNABILI|*|1|*|CATEGORIA|*|R|*|ID_USER_LAVORO|*|"+idUserLavoro);
			}
		}
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		try {
			lListResult = XmlUtility.recuperaListaSempliceSubstring(xmlLista);
		} catch (Exception e) {
			mLogger.warn(e);
		}
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}

}