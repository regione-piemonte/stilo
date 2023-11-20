/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ClassificaBean;
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

@Datasource(id="LoadComboClassificaDataSource")
public class LoadComboClassificaDataSource extends SelectDataSource<ClassificaBean>{
	
	private static Logger mLogger = Logger.getLogger(LoadComboClassificaDataSource.class);

	@Override
	public PaginatorBean<ClassificaBean> realFetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "";
		String idClassifica = StringUtils.isNotBlank(getExtraparams().get("idClassifica")) ? getExtraparams().get("idClassifica") : "";		
		
		String indice = StringUtils.isNotBlank(getExtraparams().get("indice")) ? getExtraparams().get("indice") : "";
		String descrizione = StringUtils.isNotBlank(getExtraparams().get("descrizione")) ? getExtraparams().get("descrizione") : "";
		
		boolean isIndiceFromFilter = false;		
		boolean isDescrizioneFromFilter = false;		
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("indice")) {
					indice = (String) criterion.getValue();		
					isIndiceFromFilter = true;
				} else if(criterion.getFieldName().equals("descrizione")) {
					descrizione = (String) criterion.getValue();		
					isDescrizioneFromFilter = true;
				}
			}
		}			
		
		if((descrizione.length() < 3 && "".equals(indice)) || (!isDescrizioneFromFilter && !"".equals(indice) && isIndiceFromFilter)) {
			descrizione = "";
		}
		
		if(!"".equals(descrizione) && isDescrizioneFromFilter && !isIndiceFromFilter) {
			indice = "";
		}
		
		List<ClassificaBean> lListResult = new ArrayList<ClassificaBean>();
		PaginatorBean<ClassificaBean> lPaginatorBean = new PaginatorBean<ClassificaBean>();		
		
		if(!"".equals(descrizione) || !"".equals(indice)) {
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
			
			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("CLASSIFICAZIONE");
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro + "|*|FLG_SOLO_ASSEGNABILI|*|1|*|INDICE|*|" + indice + "|*|STR_IN_DES|*|" + descrizione + "|*|CI_TO_ADD|*|" + idClassifica);
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
			
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
						
			if(StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				try {
					lListResult = XmlListaUtility.recuperaLista(xmlLista, ClassificaBean.class);
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
