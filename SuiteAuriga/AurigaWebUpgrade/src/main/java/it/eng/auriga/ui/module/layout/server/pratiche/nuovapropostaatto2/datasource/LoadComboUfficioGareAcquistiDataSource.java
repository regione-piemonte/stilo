/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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

@Datasource(id = "LoadComboUfficioGareAcquistiDataSource")
public class LoadComboUfficioGareAcquistiDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro()	: "";		
		
		String altriParametri = null;
		if(StringUtils.isNotBlank(getExtraparams().get("altriParamLoadCombo"))) {
			String uoProponente = StringUtils.isNotBlank(getExtraparams().get("uoProponente")) ? getExtraparams().get("uoProponente") : "";
			if (uoProponente.startsWith("UO")) {
				uoProponente = uoProponente.substring(2);
			}
			altriParametri = getExtraparams().get("altriParamLoadCombo");			
			altriParametri = altriParametri.replace("$ID_USER_LAVORO$", idUserLavoro);
			altriParametri = altriParametri.replace("$ID_UO_PROPONENTE$", uoProponente);
		}
				
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin(getExtraparams().get("tipoLoadCombo"));
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);		
		
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), loginBean, lDmpkLoadComboDmfn_load_comboBean);
				
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