/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UtenteBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

@Datasource(id = "LoadComboUtentiDatasource")
public class LoadComboUtentiDatasource extends OptionFetchDataSource<UtenteBean> {

	@Override
	public PaginatorBean<UtenteBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		
		String dominio = "";
		
		if (getExtraparams().get("dominio") != null)
			dominio = StringUtils.isNotBlank(getExtraparams().get("dominio")) ? getExtraparams().get("dominio") : "";
				
		if(dominio == null || dominio.equalsIgnoreCase("")){
			if(AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdDominio()!=null)
			   dominio = AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdDominio().toString();
		}
		
		String idUtente = StringUtils.isNotBlank(getExtraparams().get("idUtente")) ? getExtraparams().get("idUtente") : "";		
			
		String codice = "";
		String cognomeNome = "";
		String username = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("codice")) {
					codice = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("cognomeNome")) {
					cognomeNome = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("username")) {
					username = (String) criterion.getValue();					
				}
			}
		}			
		
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("UTENTI");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_DOMINIO|*|" + dominio + "|*|STR_IN_DES|*|" + cognomeNome + "|*|STR_IN_USERNAME|*|" + username + "|*|CI_TO_ADD|*|" + idUtente);	
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		PaginatorBean<UtenteBean> lPaginatorBean = new PaginatorBean<UtenteBean>();
		
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		List<UtenteBean> lList = XmlListaUtility.recuperaLista(xmlLista, UtenteBean.class);
		lPaginatorBean.setData(lList);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lList.size());
		lPaginatorBean.setTotalRows(lList.size());		
		
		return lPaginatorBean;
		
	}
}