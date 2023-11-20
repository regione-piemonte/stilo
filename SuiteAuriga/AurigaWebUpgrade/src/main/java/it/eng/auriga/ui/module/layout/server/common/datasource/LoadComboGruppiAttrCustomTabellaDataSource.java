/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttrCustomTabellaBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboGruppiAttrCustomTabellaDataSource")
public class LoadComboGruppiAttrCustomTabellaDataSource extends AbstractServiceDataSource<AttrCustomTabellaBean, AttrCustomTabellaBean> {	

	@Override
	public AttrCustomTabellaBean call(AttrCustomTabellaBean bean) throws Exception {

		if(StringUtils.isNotBlank(bean.getNomeTabella())) {
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
			
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();		
			lDmpkLoadComboDmfn_load_comboBean.setCodidconnectiontokenin(loginBean.getToken());
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("GRUPPI_ATTR_CUSTOM_TIPO");
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("NOME_TABELLA|*|" + bean.getNomeTabella());
			
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
			
			if(StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {				
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				List<XmlListaSimpleBean> lista = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);		
				LinkedHashMap<String, String> gruppiAttributiCustomTabella = new LinkedHashMap<String, String>();				
				for (XmlListaSimpleBean lRiga : lista){
					gruppiAttributiCustomTabella.put(lRiga.getKey(), lRiga.getValue());
				}
				bean.setGruppiAttributiCustomTabella(gruppiAttributiCustomTabella);
			}
			
		}
		
		return bean;
	}
	
}