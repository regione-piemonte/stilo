/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "ComuneDataSource")
public class ComuneDataSource extends OptionFetchDataSource<ComuneBean> {
	
	private static Logger mLogger = Logger.getLogger(ComuneDataSource.class);

	@Override
	public PaginatorBean<ComuneBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		BigDecimal flgSoloVld = StringUtils.isNotBlank(getExtraparams().get("flgSoloVld")) ? new BigDecimal(getExtraparams().get("flgSoloVld")) : null; 
		
		String codIstatComune = "";
		String nomeComune = StringUtils.isNotBlank(getExtraparams().get("nomeComune")) ? getExtraparams().get("nomeComune") : "";
		String targaProvincia = "";
		String tsVld = getExtraparams().get("tsVld");
		
		boolean isNomeComuneFromFilter = false;		
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("nomeComune")) {
					nomeComune = (String) criterion.getValue();			
					isNomeComuneFromFilter = true;
				} 
				else if(criterion.getFieldName().equals("targaProvincia")) {
					targaProvincia = (String) criterion.getValue();															
				}
				else if(criterion.getFieldName().equals("codIstatComune")) {
					codIstatComune = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("tsVld")) {
					tsVld = (String) criterion.getValue();
				}  
			}
		}	
		
		if(targaProvincia.length() != 2) targaProvincia = "";
		if((nomeComune.length() < 2 && "".equals(targaProvincia)) || (!isNomeComuneFromFilter && !"".equals(targaProvincia))) {
			nomeComune = "";
		}
		
		if(!"".equals(nomeComune) || !"".equals(targaProvincia)) {
			codIstatComune = "";
		}
		
		List<ComuneBean> lListResult = new ArrayList<ComuneBean>();
		PaginatorBean<ComuneBean> lPaginatorBean = new PaginatorBean<ComuneBean>();
		
		if(!"".equals(nomeComune) || !"".equals(targaProvincia) || !"".equals(codIstatComune)) {
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
			
			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("COMUNI_ITALIANI");		;
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(flgSoloVld);
			lDmpkLoadComboDmfn_load_comboBean.setTsvldin(tsVld != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP_WITH_SEC).format(new SimpleDateFormat(FMT_STD_DATA).parse(tsVld)) : null);
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("NOME_COMUNE|*|" + nomeComune + "|*|TARGA_PROV|*|" + targaProvincia + "|*|ISTAT_PROVINCIA|*|" + codIstatComune + "|*|CI_TO_ADD|*|" + codIstatComune);
			
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
