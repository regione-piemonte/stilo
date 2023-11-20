/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.FolderTypeBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

@Datasource(id = "LoadComboGruppiAttrCustomTipoFolderDataSource")
public class LoadComboGruppiAttrCustomTipoFolderDataSource extends AbstractServiceDataSource<FolderTypeBean, FolderTypeBean> {	

	@Override
	public FolderTypeBean call(FolderTypeBean bean) throws Exception {

		if(StringUtils.isNotBlank(bean.getIdFolderType())) {
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
			
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();		
			lDmpkLoadComboDmfn_load_comboBean.setCodidconnectiontokenin(loginBean.getToken());
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("GRUPPI_ATTR_CUSTOM_TIPO");
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_FOLDER_TYPE|*|" + bean.getIdFolderType());
			
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
			
			if(StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {				
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				List<XmlListaSimpleBean> lista = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);		
				LinkedHashMap<String, String> gruppiAttributiCustomTipoFolder = new LinkedHashMap<String, String>();				
				for (XmlListaSimpleBean lRiga : lista){
					gruppiAttributiCustomTipoFolder.put(lRiga.getKey(), lRiga.getValue());
				}
				bean.setGruppiAttributiCustomTipoFolder(gruppiAttributiCustomTipoFolder);
			}
			
		}
		
		return bean;
	}
	
}