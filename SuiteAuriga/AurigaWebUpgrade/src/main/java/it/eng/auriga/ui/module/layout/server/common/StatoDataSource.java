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

@Datasource(id = "StatoDataSource")
public class StatoDataSource extends AbstractFetchDataSource<StatoBean> {

	@Override
	public PaginatorBean<StatoBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
			
		BigDecimal flgSoloVld = StringUtils.isNotBlank(getExtraparams().get("flgSoloVld")) ? new BigDecimal(getExtraparams().get("flgSoloVld")) : null; 
		
		String codIstatStato = StringUtils.isNotBlank(getExtraparams().get("codIstatStato")) ? getExtraparams().get("codIstatStato") : "";		
		String nomeStato = "";
		String tsVld = getExtraparams().get("tsVld");
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("nomeStato")) {
					nomeStato = (String) criterion.getValue();					
				} 
//				else if(criterion.getFieldName().equals("codIstatStato")) {
//					codIstatStato = (String) criterion.getValue();
//				}
//				else if(criterion.getFieldName().equals("tsVld")) {
//					tsVld = (String) criterion.getValue();
//				}  
			}
		}	
		
		List<StatoBean> lListResult = new ArrayList<StatoBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("STATI_NAZIONALI");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(flgSoloVld);		
		
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(tsVld != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP_WITH_SEC).format(new SimpleDateFormat(FMT_STD_DATA).parse(tsVld)) : null);
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("NOME_STATO|*|" + nomeStato + "|*|CI_TO_ADD|*|" + codIstatStato);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		lListResult = XmlListaUtility.recuperaLista(xmlLista, StatoBean.class);
		
		PaginatorBean<StatoBean> lPaginatorBean = new PaginatorBean<StatoBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}

}
