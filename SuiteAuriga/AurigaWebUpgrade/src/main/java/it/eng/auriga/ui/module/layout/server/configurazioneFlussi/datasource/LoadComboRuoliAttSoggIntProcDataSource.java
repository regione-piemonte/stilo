/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboRuoliAttSoggIntProcDataSource")
public class LoadComboRuoliAttSoggIntProcDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboRuoliAttSoggIntProcDataSource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		
		String codTipoFlusso = StringUtils.isNotBlank(getExtraparams().get("codTipoFlusso")) ? getExtraparams().get("codTipoFlusso") : null;

		// Input
		DmpkLoadComboDmfn_load_comboBean inputParamComboBean = new DmpkLoadComboDmfn_load_comboBean();
		inputParamComboBean.setTipocomboin("RUOLI_ATT_SOGG_INT_PROC");
		inputParamComboBean.setAltriparametriin("CI_TYPE_FLUSSO_WF|*|" + codTipoFlusso);
				
		// Output
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), inputParamComboBean);
		if(storeResult.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);	
			mLogger.error(storeResult.getDefaultMessage());
		} else {
			
			String xmlLista = storeResult.getResultBean().getListaxmlout();
			List<SimpleKeyValueBean> resultList = new ArrayList<SimpleKeyValueBean>();
			List<XmlListaSimpleBean> listaXML = null;
			try {			
				listaXML = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);
				for (XmlListaSimpleBean lXmlListaSimpleBean : listaXML) {
					SimpleKeyValueBean lBean = new SimpleKeyValueBean();
					lBean.setKey(lXmlListaSimpleBean.getKey());
					lBean.setValue(lXmlListaSimpleBean.getValue());
					resultList.add(lBean);
				}
				
				lPaginatorBean.setData(resultList);
				lPaginatorBean.setStartRow(0);
				lPaginatorBean.setEndRow(resultList.size());
				lPaginatorBean.setTotalRows(resultList.size());
			} catch (Exception e) {
				mLogger.error(e);
			}	
		}

		return lPaginatorBean;
	}

}