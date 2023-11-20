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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "FrazioneDataSource")
public class FrazioneDataSource extends AbstractFetchDataSource<FrazioneBean> {
	
	private static Logger mLogger = Logger.getLogger(FrazioneDataSource.class);

	@Override
	public PaginatorBean<FrazioneBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		BigDecimal flgSoloVld = StringUtils.isNotBlank(getExtraparams().get("flgSoloVld")) ? new BigDecimal(getExtraparams().get("flgSoloVld")) : null; 
		
		String codIstatComune = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("codIstatComune")) {
					codIstatComune = (String) criterion.getValue();
					break;
				} 
			}
		}	
		
		List<FrazioneBean> lListResult = new ArrayList<FrazioneBean>();

		if(!"".equals(codIstatComune)) {

			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("FRAZIONI_CAP");		;
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(flgSoloVld);
			lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("COD_ISTAT_COMUNE|*|" + codIstatComune);
			
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
			
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			
			try {
				lListResult = XmlListaUtility.recuperaLista(xmlLista, FrazioneBean.class);
			} catch (Exception e) {
				mLogger.warn(e);
			}
			
		}
		
		PaginatorBean<FrazioneBean> lPaginatorBean = new PaginatorBean<FrazioneBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}

}
