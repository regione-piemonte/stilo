/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import java.math.BigDecimal;

@Datasource(id = "LoadComboApplicazioniEsterneDataSource")
public class LoadComboApplicazioniEsterneDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		String dominio = ""; 
		
		if (getExtraparams().get("dominio") != null)
			dominio = StringUtils.isNotBlank(getExtraparams().get("dominio")) ? getExtraparams().get("dominio") : "";
		
		//if(dominio == null || dominio.equalsIgnoreCase(""))
			//dominio = AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdDominio().toString();
			
	    List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("APPLICAZIONI_ESTERNE");		
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FLG_SHOW_ALL|*|1|*|ID_DOMINIO|*|" + dominio);
				
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		lListResult = XmlUtility.recuperaListaSemplice(xmlLista);
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}
}