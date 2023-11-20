/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityDprofiloBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.FolderTypeBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.DettaglioProfiloBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.ProfiloUtenteBean;
import it.eng.client.DmpkDefSecurityDprofilo;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "LoadComboProfiliUtenteDataSource")
public class LoadComboProfiliUtenteDataSource extends AbstractFetchDataSource<ProfiloUtenteBean> {
	@Override
	public PaginatorBean<ProfiloUtenteBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String idProfilo = StringUtils.isNotBlank(getExtraparams().get("idProfilo")) ? getExtraparams().get("idProfilo") : "";
				
		String nome = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("nome")) {
					nome = (String) criterion.getValue();
					break;
				}																						 
			}
		}
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("PROFILO");		
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("TARGET|*|UT|*|STR_IN_NOME|*|" + nome + "|*|CI_TO_ADD|*|" + idProfilo);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		PaginatorBean<ProfiloUtenteBean> lPaginatorBean = new PaginatorBean<ProfiloUtenteBean>();		
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} 
		else 
		{
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<ProfiloUtenteBean> lista = XmlListaUtility.recuperaLista(xmlLista, ProfiloUtenteBean.class);
			lPaginatorBean.setData(lista);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());			
		} 
		return lPaginatorBean;
	}
	
}
