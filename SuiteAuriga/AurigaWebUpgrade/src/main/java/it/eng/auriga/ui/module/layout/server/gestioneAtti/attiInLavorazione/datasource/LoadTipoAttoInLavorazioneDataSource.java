/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.attiInLavorazione.bean.SelezionaAttoBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * Carica i tipi di atto in lavorazione da mostrare nella select di filtraggio della lista degli atti 
 * @author massimo malvestio
 *
 */
@Datasource(id="LoadTipoAttoInLavorazioneDataSource")
public class LoadTipoAttoInLavorazioneDataSource extends OptionFetchDataSource<SelezionaAttoBean> {

	@Override
	public PaginatorBean<SelezionaAttoBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro()
				: "";
		
		String nome = "";
		if (criteria.getCriterionByFieldName("nome") != null){
			nome = (String) criteria.getCriterionByFieldName("nome").getValue();
		}

		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ATTO_CON_FLUSSO_WF");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(null);
		
		String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro /*+ "|*|FLG_SOLO_ASSEGNABILI|*|1"*/;
		if(StringUtils.isNotBlank(nome)) {
			altriParametri += "|*|STR_IN_DES|*|" + nome;
		}		
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		try {
			
			List<SimpleKeyValueBean> lListResult = XmlUtility.recuperaListaSemplice(xmlLista);
			List<SelezionaAttoBean> lListResultProcedimenti = new ArrayList<SelezionaAttoBean>();
			
			SelezionaAttoBean lSelezionaAttoBeanNull = new SelezionaAttoBean();
			lSelezionaAttoBeanNull.setIdProcessType(null);
			lSelezionaAttoBeanNull.setNome("");
			lListResultProcedimenti.add(lSelezionaAttoBeanNull);
			
			for (SimpleKeyValueBean lSimpleKeyValueBean : lListResult){
			
				SelezionaAttoBean lSelezionaAttoBean = new SelezionaAttoBean();
				lSelezionaAttoBean.setIdProcessType(lSimpleKeyValueBean.getKey());
				lSelezionaAttoBean.setNome(lSimpleKeyValueBean.getValue());
				lListResultProcedimenti.add(lSelezionaAttoBean);
				
			}
			
			PaginatorBean<SelezionaAttoBean> result = new PaginatorBean<SelezionaAttoBean>(lListResultProcedimenti);
			return result;
		
		} catch (Exception e){
			return new PaginatorBean<SelezionaAttoBean>(new ArrayList<SelezionaAttoBean>());
		}
		
	}
	
}
