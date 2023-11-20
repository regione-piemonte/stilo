/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.SoggettoAooMdgBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.SelectDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "LoadComboSoggettiAooMdgDataSource")
public class LoadComboSoggettiAooMdgDataSource extends SelectDataSource<SoggettoAooMdgBean> {	
	
		private static Logger mLogger = Logger.getLogger(LoadComboSoggettiAooMdgDataSource.class);
	
		@Override
		public PaginatorBean<SoggettoAooMdgBean> realFetch(AdvancedCriteria criteria,
				Integer startRow, Integer endRow, List<OrderByBean> orderby)
				throws Exception {
			
		String idAooMdg = StringUtils.isNotBlank(getExtraparams().get("idAooMdg")) ? getExtraparams().get("idAooMdg") : "";		
			
		String codiceRapidoAooMdg = StringUtils.isNotBlank(getExtraparams().get("codiceRapidoAooMdg")) ? getExtraparams().get("codiceRapidoAooMdg") : "";
		String descrizioneAooMdg = StringUtils.isNotBlank(getExtraparams().get("descrizioneAooMdg")) ? getExtraparams().get("descrizioneAooMdg") : "";
		
		String descrizioneEstesaAooMdg = "";
		
		boolean isCodiceRapidoFromFilter = false;		
		boolean isDescrizioneFromFilter = false;		
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("codiceRapidoAooMdg")) {
					codiceRapidoAooMdg = (String) criterion.getValue();		
					isCodiceRapidoFromFilter = true;
				} 
				else if(criterion.getFieldName().equals("descrizioneAooMdg")) {
					descrizioneAooMdg = (String) criterion.getValue();	
					isDescrizioneFromFilter = true;							
				} 
//				else if(criterion.getFieldName().equals("descrizioneEstesaAooMdg")) {
//					descrizioneEstesaAooMdg = (String) criterion.getValue();					
//				}
			}
		}			
		
		if((descrizioneAooMdg.length() < 3 && "".equals(codiceRapidoAooMdg)) || (!isDescrizioneFromFilter && !"".equals(codiceRapidoAooMdg) && isCodiceRapidoFromFilter)) {
			descrizioneAooMdg = "";
		}
		
		if(!"".equals(descrizioneAooMdg) && isDescrizioneFromFilter && !isCodiceRapidoFromFilter) {
			codiceRapidoAooMdg = "";
		}
		
		List<SoggettoAooMdgBean> lListResult = new ArrayList<SoggettoAooMdgBean>();
		PaginatorBean<SoggettoAooMdgBean> lPaginatorBean = new PaginatorBean<SoggettoAooMdgBean>();		
		
		if(!"".equals(descrizioneAooMdg) || !"".equals(codiceRapidoAooMdg)) {
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("AOO_SP");
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
			lDmpkLoadComboDmfn_load_comboBean.setPkrecin(null);
			lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		
			// Se l'utente ha digitato un filtro, il CI_TO_ADD non deve essere passato
			if (!codiceRapidoAooMdg.equalsIgnoreCase("")  ||  !descrizioneAooMdg.equalsIgnoreCase("") || !descrizioneEstesaAooMdg.equalsIgnoreCase("")){			
					lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("COD_RAPIDO|*|" + codiceRapidoAooMdg + "|*|STR_IN_DES|*|" + descrizioneAooMdg+ "|*|STR_IN_DES_COMPLETA|*|" + descrizioneEstesaAooMdg + "|*|ESCLUDI_AOO_COLLEGATA|*|1");			
			}			
			else {
			       lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("COD_RAPIDO|*|" + codiceRapidoAooMdg + "|*|STR_IN_DES|*|" + descrizioneAooMdg+ "|*|STR_IN_DES_COMPLETA|*|" + descrizioneEstesaAooMdg + "|*|ESCLUDI_AOO_COLLEGATA|*|1" + "|*|CI_TO_ADD|*|"+ idAooMdg);
			}
						
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
			if(StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {			
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				try {
					lListResult = XmlListaUtility.recuperaLista(xmlLista, SoggettoAooMdgBean.class);
				} catch (Exception e) {
					mLogger.warn(e);
				}
			}
		}
		
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;	
	}
}
