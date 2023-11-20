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

@Datasource(id = "LoadComboRegistroNumerazioneDataSource")
public class LoadComboRegistroNumerazioneDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String dominio = ""; 
		if (getExtraparams().get("dominio") != null)
			dominio = StringUtils.isNotBlank(getExtraparams().get("dominio")) ? getExtraparams().get("dominio") : "";
				
		if(dominio == null || dominio.equalsIgnoreCase("")){
				if(AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdDominio()!=null)
				   dominio = AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdDominio().toString();
		}
		
	    List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
	    List<SimpleKeyValueBean> lListResultNew = new ArrayList<SimpleKeyValueBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("SIGLA_REG_NUM_UD");		
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("INCLUDI_ALTRE_SPECIALI|*|1|*|ID_DOMINIO|*|" + dominio);		
				
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		lListResult = XmlUtility.recuperaListaSemplice(xmlLista);
		
		if(lListResult!=null && lListResult.size()>0){
			for (SimpleKeyValueBean rec : lListResult){	
				String lkey  = rec.getKey();
				String lvalue = rec.getValue() + "  (" + lkey + ")";
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lkey);
				lSimpleKeyValueBean.setValue(lvalue);
				lListResultNew.add(lSimpleKeyValueBean);
			}
		}
			
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResultNew);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResultNew.size());
		lPaginatorBean.setTotalRows(lListResultNew.size());
		
		return lPaginatorBean;
	}
}