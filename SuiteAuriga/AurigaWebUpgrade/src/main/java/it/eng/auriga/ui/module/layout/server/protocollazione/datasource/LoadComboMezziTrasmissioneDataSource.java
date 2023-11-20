/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


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

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

@Datasource(id = "LoadComboMezziTrasmissioneDataSource")
public class LoadComboMezziTrasmissioneDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {	
	
	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String idRegistrazione = null;
		if (getExtraparams().get("idRegistrazione") != null)
			idRegistrazione = StringUtils.isNotBlank(getExtraparams().get("idRegistrazione")) ? getExtraparams().get("idRegistrazione") : null;
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("GENERICO");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("NOME_TABELLA|*|DMT_UNITA_DOC|*|NOME_COL_TABELLA|*|COD_MEZZO_TRASM|*|ID_USER_LAVORO|*|"  + idUserLavoro);
        lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		lDmpkLoadComboDmfn_load_comboBean.setPkrecin(idRegistrazione);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();		
		
		if(lStoreResultBean.getDefaultMessage() != null) {		
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<SimpleKeyValueBean> lista = XmlUtility.recuperaListaSemplice(xmlLista);
			lPaginatorBean.setData(lista);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());			
		} 
		
		return lPaginatorBean;
		
	}

}
