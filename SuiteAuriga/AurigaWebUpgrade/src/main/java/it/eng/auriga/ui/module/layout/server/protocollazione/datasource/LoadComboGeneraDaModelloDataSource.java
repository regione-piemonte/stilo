/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ModelloXTipoDocBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboGeneraDaModelloDataSource")
public class LoadComboGeneraDaModelloDataSource extends AbstractFetchDataSource<ModelloXTipoDocBean> {
	
	@Override
	public PaginatorBean<ModelloXTipoDocBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String idTipoDocumento = StringUtils.isNotBlank(getExtraparams().get("idTipoDocumento")) ? getExtraparams().get("idTipoDocumento") : "";		
		String idProcess = StringUtils.isNotBlank(getExtraparams().get("idProcess")) ? getExtraparams().get("idProcess") : "";			
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		
		lDmpkLoadComboDmfn_load_comboBean.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("MODELLO_X_TIPO_DOC");
		
		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "";
		
		StringBuilder parametriBuffer = new StringBuilder();
		
		parametriBuffer.append("ID_DOC_TYPE|*|").append(idTipoDocumento);
		if(StringUtils.isNotBlank(idProcess)) {
			parametriBuffer.append("|*|ID_PROCESS|*|").append(idProcess);
		}
		//al momento il filtraggio sul tipo di documento è stato rimosso
//		parametriBuffer.append("|*|ESTENSIONE|*|").append("doc");
		parametriBuffer.append("|*|ID_USER_LAVORO|*|").append(idUserLavoro);
		String altriParametriIn = parametriBuffer.toString();
		
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametriIn);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		PaginatorBean<ModelloXTipoDocBean> lPaginatorBean = new PaginatorBean<ModelloXTipoDocBean>();		
		
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		}
		
		else {
			
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<ModelloXTipoDocBean> lista = XmlListaUtility.recuperaLista(xmlLista, ModelloXTipoDocBean.class);
			lPaginatorBean.setData(lista);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());			
		} 
		
		return lPaginatorBean;
		
	}
	
}

