/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.IndirizziComboXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.LoadComboIndirizzoSoggettoBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "LoadComboIndirizziDataSource")
public class LoadComboIndirizziDataSource extends AbstractFetchDataSource<LoadComboIndirizzoSoggettoBean> {	
	
	private static Logger mLogger = Logger.getLogger(LoadComboIndirizziDataSource.class);
	
	public PaginatorBean<LoadComboIndirizzoSoggettoBean> fetch(AdvancedCriteria criteria,Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {	
		
		List<LoadComboIndirizzoSoggettoBean> lListResult = new ArrayList<LoadComboIndirizzoSoggettoBean>();
		PaginatorBean<LoadComboIndirizzoSoggettoBean> lPaginatorBean = new PaginatorBean<LoadComboIndirizzoSoggettoBean>();	

		
		String idSoggetto = null;
		if (getExtraparams().get("idSoggetto") != null)
			idSoggetto = StringUtils.isNotBlank(getExtraparams().get("idSoggetto")) ? getExtraparams().get("idSoggetto") : null;
		
		if (idSoggetto == null){
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);	
			return lPaginatorBean;
		}
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("INDIRIZZI_SOGG");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_SOGG_RUBRICA|*|"+idSoggetto);
        lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		//lDmpkLoadComboDmfn_load_comboBean.setPkrecin(idRegistrazione);
		
		// eseguo il servizio		
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		if(StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {			
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			IndirizziComboXmlBeanDeserializationHelper helper = new IndirizziComboXmlBeanDeserializationHelper(new HashMap<String, String>());
			try {
					lListResult = XmlListaUtility.recuperaLista(xmlLista, LoadComboIndirizzoSoggettoBean.class, helper);
				} catch (Exception e) {
					mLogger.warn(e);
				}
		}
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}
}
