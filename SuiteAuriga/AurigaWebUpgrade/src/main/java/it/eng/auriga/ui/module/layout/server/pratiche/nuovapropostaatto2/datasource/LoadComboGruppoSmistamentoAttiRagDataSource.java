/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.GruppoSoggettiBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.SelectDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboGruppoSmistamentoAttiRagDataSource")
public class LoadComboGruppoSmistamentoAttiRagDataSource extends SelectDataSource<GruppoSoggettiBean> {
		@Override	
		public PaginatorBean<GruppoSoggettiBean> realFetch(AdvancedCriteria criteria,
				Integer startRow, Integer endRow, List<OrderByBean> orderby)
				throws Exception {		
			
		String codiceRapidoGruppo = StringUtils.isNotBlank(getExtraparams().get("codiceRapidoGruppo")) ? getExtraparams().get("codiceRapidoGruppo") : "";
		
		String nomeGruppo = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("codiceRapidoGruppo")) {
					codiceRapidoGruppo = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("nomeGruppo")) {
					nomeGruppo = (String) criterion.getValue();					
				}
			}
		}			
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("GRUPPO_SOGG_RUBRICA");		
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("COD_RAPIDO|*|" + codiceRapidoGruppo + "|*|STR_IN_NOME|*|" + nomeGruppo + "|*|FLG_SOLO_ASS_RAGIONERIA|*|1");	
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
		lDmpkLoadComboDmfn_load_comboBean.setPkrecin(null);
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);		
		
		PaginatorBean<GruppoSoggettiBean> lPaginatorBean = new PaginatorBean<GruppoSoggettiBean>();				
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<GruppoSoggettiBean> lista = new ArrayList<GruppoSoggettiBean>();
			int size = 0;
			if(StringUtils.isNotBlank(xmlLista)) {
				lista = XmlListaUtility.recuperaLista(xmlLista, GruppoSoggettiBean.class);
				if(lista != null) {
					for (GruppoSoggettiBean lRiga : lista){
						GruppoSoggettiBean lGruppoSoggettiBean = new GruppoSoggettiBean();		
						lGruppoSoggettiBean.setIdGruppo(lRiga.getIdSoggettiInterni());
						lGruppoSoggettiBean.setCodiceRapidoGruppo(lRiga.getCodiceRapidoGruppo());				
						lGruppoSoggettiBean.setNomeGruppo(lRiga.getNomeGruppo());
						lPaginatorBean.addRecord(lGruppoSoggettiBean);
						size ++;
					}
				}
			}
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(size);
			lPaginatorBean.setTotalRows(size);			
		} 		
		return lPaginatorBean;		
	}
}
