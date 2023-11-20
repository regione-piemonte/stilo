/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
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

/**
 * 
 * @author DANCRIST
 *
 */

@Datasource(id = "LoadComboDestinatariOsservazioniDataSource")
public class LoadComboDestinatariOsservazioniDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	
	
	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "";
		String idUdFolder = getExtraparams().get("idUdFolder");
		String flgUdFolder = getExtraparams().get("flgUdFolder");
		
		List<SimpleKeyValueBean> data = new ArrayList<SimpleKeyValueBean>();
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("DEST_NOT_OSS_SU_UD_FLD");
		if(flgUdFolder.equals("U")){
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro + "|*|ID_UD|*|" + idUdFolder);
		}else{
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro + "|*|ID_FOLDER|*|" + idUdFolder);
		}

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();		
		
		if(lStoreResultBean.getDefaultMessage() != null) {		
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<XmlListaSimpleBean> lListXml = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);
			
			for (XmlListaSimpleBean lRiga : lListXml){
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lRiga.getKey());
				lSimpleKeyValueBean.setValue(lRiga.getValue());
				data.add(lSimpleKeyValueBean);
			}	
		} 
		
		SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
		lSimpleKeyValueBean.setKey("UT|*|1");
		lSimpleKeyValueBean.setValue("Daniele");
		//data.add(lSimpleKeyValueBean);
	
		SimpleKeyValueBean lSimpleKeyValueBean2 = new SimpleKeyValueBean();
		lSimpleKeyValueBean2.setKey("G|*|2");
		lSimpleKeyValueBean2.setValue("Cristiano");
		//data.add(lSimpleKeyValueBean2);
		
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(data.size());
		lPaginatorBean.setTotalRows(data.size());	
		
		return lPaginatorBean;
	}

}
