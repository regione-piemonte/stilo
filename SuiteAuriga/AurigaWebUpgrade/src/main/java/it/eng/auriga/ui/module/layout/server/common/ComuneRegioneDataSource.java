/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "ComuneRegioneDataSource")
public class ComuneRegioneDataSource extends AbstractFetchDataSource<ComuneBean> {
	
	private static Logger mLogger = Logger.getLogger(ComuneRegioneDataSource.class);

	@Override
	public PaginatorBean<ComuneBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String codIstatComune = StringUtils.isNotBlank(getExtraparams().get("codIstatComune")) ? getExtraparams().get("codIstatComune") : "";
		String nomeComune = "";
		String targaProvincia = "";
		
		String codIstatRegione = getExtraparams().get("codIstatRegione");
		
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("nomeComune")) {
					nomeComune = (String) criterion.getValue();								
				} 
				else if(criterion.getFieldName().equals("targaProvincia")) {
					targaProvincia = (String) criterion.getValue();															
				}
			}
		}			
		
		List<ComuneBean> lListResult = new ArrayList<ComuneBean>();
		PaginatorBean<ComuneBean> lPaginatorBean = new PaginatorBean<ComuneBean>();
		
		if(!"".equals(nomeComune) || !"".equals(targaProvincia)) {
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
			
			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("COMUNI_ITALIANI");		;
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(null);
			lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("INTERNAL_VALUE|*|DES|*|NOME_COMUNE|*|" + nomeComune + "|*|TARGA_PROV|*|" + targaProvincia + "|*|CI_TO_ADD|*|" + codIstatComune + "|*|ISTAT_REGIONE|*|" + codIstatRegione);
			
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
			
			if(StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();			
				try {
					lListResult = XmlListaUtility.recuperaLista(xmlLista, ComuneBean.class);
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
