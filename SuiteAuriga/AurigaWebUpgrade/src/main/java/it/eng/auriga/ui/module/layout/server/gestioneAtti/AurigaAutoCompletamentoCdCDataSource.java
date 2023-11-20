/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "AurigaAutoCompletamentoCdCDataSource")
public class AurigaAutoCompletamentoCdCDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {

	private static Logger log = Logger.getLogger(AurigaAutoCompletamentoCdCDataSource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "";		
		
		String strInDes = getExtraparams().get("strInDes") != null ? getExtraparams().get("strInDes") : "";		

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("VALORI_DIZIONARIO");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("DICTIONARY_ENTRY|*|CDC|*|ID_USER_LAVORO|*|" + idUserLavoro + "|*|STR_IN_DES|*|" + strInDes);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(null);
		
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		List<SimpleKeyValueBean> lista = new ArrayList<SimpleKeyValueBean>();
		if(!lStoreResultBean.isInError()) {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();			
			for (XmlListaSimpleBean lXmlListaSimpleBean : XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class)) {
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lXmlListaSimpleBean.getKey());
				lSimpleKeyValueBean.setValue(lXmlListaSimpleBean.getValue());
				lista.add(lSimpleKeyValueBean);
			}				
		} 
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lista);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lista.size());
		lPaginatorBean.setTotalRows(lista.size());
		
		return lPaginatorBean;
	}
}