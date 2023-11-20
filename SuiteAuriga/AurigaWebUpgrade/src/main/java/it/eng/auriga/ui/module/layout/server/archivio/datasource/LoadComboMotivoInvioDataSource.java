/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.MotivoInvioBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;



import org.apache.commons.lang3.StringUtils;

@Datasource(id = "LoadComboMotivoInvioDataSource")
public class LoadComboMotivoInvioDataSource extends AbstractFetchDataSource<MotivoInvioBean> {

	@Override
	public PaginatorBean<MotivoInvioBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String tableName = getExtraparams().get("tableName");
		String idUd = getExtraparams().get("idUd");
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
				
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("GENERICO");		
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		if(StringUtils.isNotBlank(idUd)) {
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("NOME_TABELLA|*|" + tableName + "|*|ID_USER_LAVORO|*|" + idUserLavoro + "|*|NOME_COL_TABELLA|*|COD_MOTIVO|*|ID_UD|*|" + idUd);
		} else {
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("NOME_TABELLA|*|" + tableName + "|*|ID_USER_LAVORO|*|" + idUserLavoro + "|*|NOME_COL_TABELLA|*|COD_MOTIVO");
		}
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		PaginatorBean<MotivoInvioBean> lPaginatorBean = new PaginatorBean<MotivoInvioBean>();
		List<MotivoInvioBean> data = new ArrayList<MotivoInvioBean>();
		
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
					MotivoInvioBean lMotivoInvioBean = new MotivoInvioBean();
					lMotivoInvioBean.setKey(v.get(0));	// colonna 1
					String value = v.get(1); // colonna 2	
					String flgSpeciale = v.get(2); // colonna 3	
					if(flgSpeciale != null && "1".equals(flgSpeciale)) {
						lMotivoInvioBean.setValue("<b>" + value + "</b>"); // colonna 2	
					} else {
						lMotivoInvioBean.setValue(value); // colonna 2	
					}
					lMotivoInvioBean.setFlgSpeciale(flgSpeciale); 								
					data.add(lMotivoInvioBean);
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
