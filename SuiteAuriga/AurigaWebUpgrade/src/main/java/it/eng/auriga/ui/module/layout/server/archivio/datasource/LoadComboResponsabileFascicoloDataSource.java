/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ResponsabileFascicoloBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.SelectDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;



import org.apache.commons.lang3.StringUtils;

@Datasource(id="LoadComboResponsabileFascicoloDataSource")
public class LoadComboResponsabileFascicoloDataSource extends SelectDataSource<ResponsabileFascicoloBean>{
	
	@Override
	public PaginatorBean<ResponsabileFascicoloBean> realFetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {		
		
		String idClassifica = StringUtils.isNotBlank(getExtraparams().get("idClassifica")) ? getExtraparams().get("idClassifica") : "";
		String idFolderApp  = StringUtils.isNotBlank(getExtraparams().get("idFolderApp"))  ? getExtraparams().get("idFolderApp")  : "";
		String idUOScrivResponsabile = StringUtils.isNotBlank(getExtraparams().get("idUOScrivResponsabile")) ? getExtraparams().get("idUOScrivResponsabile") : "";
		
//		String codice = "";		
//		String descrizione = "";				
//		
//		if (criteria!=null && criteria.getCriteria()!=null){			
//			for (Criterion criterion : criteria.getCriteria()){
//				if(criterion.getFieldName().equals("codice")) {
//					codice = (String) criterion.getValue();					
//				} else if(criterion.getFieldName().equals("descrizione")) {
//					descrizione = (String) criterion.getValue();					
//				}				
//			}
//		}		
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();			
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("RESP_FASCICOLO");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);		
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + (AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "") + "|*|" +  "ID_CLASSIFICAZIONE|*|" + idClassifica +  "|*|" + "ID_FOLDER_APP|*|" + idFolderApp + "|*|"  + "CI_TO_ADD|*|" + idUOScrivResponsabile +"|*|");
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		PaginatorBean<ResponsabileFascicoloBean> lPaginatorBean = new PaginatorBean<ResponsabileFascicoloBean>();		
		List<ResponsabileFascicoloBean> data = new ArrayList<ResponsabileFascicoloBean>();
		
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} else {
			// Conversione ListaRisultati ==> EngResultSet 
			StringReader sr = new StringReader(lStoreResultBean.getResultBean().getListaxmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																
	        		ResponsabileFascicoloBean lResponsabileFascicoloBean = new ResponsabileFascicoloBean();
					lResponsabileFascicoloBean.setTypeNodo(v.get(0));				
					lResponsabileFascicoloBean.setIdUOResponsabile(v.get(1) != null ? new BigDecimal(v.get(1)) : null);										
					lResponsabileFascicoloBean.setCodice(v.get(2));
					lResponsabileFascicoloBean.setDescrizione(v.get(3));
					lResponsabileFascicoloBean.setDescrizioneEstesa(v.get(4));					
					lResponsabileFascicoloBean.setFlgDefault(v.get(7) != null ? v.get(7) : "0");					
					data.add(lResponsabileFascicoloBean);
	    		}
			}			
			lPaginatorBean.setData(data);			
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
			lPaginatorBean.setTotalRows(data.size());
		} 
		
		return lPaginatorBean;
	}	
	
}
