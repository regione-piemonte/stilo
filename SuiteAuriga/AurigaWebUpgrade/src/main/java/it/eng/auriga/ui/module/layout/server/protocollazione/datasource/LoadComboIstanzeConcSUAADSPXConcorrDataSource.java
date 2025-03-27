/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.IstanzaConcSUAADSPXConcorrBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.SelectDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboIstanzeConcSUAADSPXConcorrDataSource")
public class LoadComboIstanzeConcSUAADSPXConcorrDataSource extends SelectDataSource<IstanzaConcSUAADSPXConcorrBean> {

	@Override
	public PaginatorBean<IstanzaConcSUAADSPXConcorrBean> realFetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		String idUdDaCollegare = StringUtils.isNotBlank(getExtraparams().get("idUdDaCollegare")) ? getExtraparams().get("idUdDaCollegare") : "";
		
		String protocolloIstanza = "";		
		String codPratica = "";
		String nroPubblicazione = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("protocolloIstanza")) {
					protocolloIstanza = criterion.getValue() != null ? (String) criterion.getValue() : "";	
				} else if(criterion.getFieldName().equals("codPratica")) {
					codPratica = criterion.getValue() != null ? (String) criterion.getValue() : "";		
				} else if(criterion.getFieldName().equals("nroPubblicazione")) {
					nroPubblicazione = criterion.getValue() != null ? (String) criterion.getValue() : "";		
				}
			}
		}
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ISTANZE_CONC_SUA_ADSP_X_CONCORR");		
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_UD_DA_COLLEGARE|*|" + idUdDaCollegare + "|*|PROTOCOLLO_ISTANZA|*|" + protocolloIstanza + "|*|COD_PRATICA|*|" + codPratica + "|*|NRO_PUBBLICAZIONE|*|" + nroPubblicazione);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		List<IstanzaConcSUAADSPXConcorrBean> lista = new ArrayList<IstanzaConcSUAADSPXConcorrBean>();
		
		if(!lStoreResultBean.isInError()) {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			lista = XmlListaUtility.recuperaLista(xmlLista, IstanzaConcSUAADSPXConcorrBean.class);	
		}
		
		PaginatorBean<IstanzaConcSUAADSPXConcorrBean> lPaginatorBean = new PaginatorBean<IstanzaConcSUAADSPXConcorrBean>();		
		lPaginatorBean.setData(lista);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lista.size());
		lPaginatorBean.setTotalRows(lista.size());
		
		return lPaginatorBean;
	}
	
}
