/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UtenteBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.SelectDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id="LoadComboUtentiDataSource")
public class LoadComboUtentiDataSource extends SelectDataSource<UtenteBean>{

	@Override
	public PaginatorBean<UtenteBean> realFetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		String idUtente = StringUtils.isNotBlank(getExtraparams().get("idUtente")) ? getExtraparams().get("idUtente") : "";
		String idUtenteToExclude = StringUtils.isNotBlank(getExtraparams().get("idUtenteToExclude")) ? getExtraparams().get("idUtenteToExclude") : "";
		String flgSoloTributi = StringUtils.isNotBlank(getExtraparams().get("flgSoloTributi")) ? getExtraparams().get("flgSoloTributi") : "";		
		String listaIdProcessType = StringUtils.isNotBlank(getExtraparams().get("listaIdProcessType")) ? getExtraparams().get("listaIdProcessType") : "";		
		
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
		
		if (StringUtils.isBlank(codice)){
			codice = StringUtils.isNotBlank(getExtraparams().get("codiceRapido")) ? getExtraparams().get("codiceRapido") : "";
		}
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("UTENTI");		
		if (flgSoloTributi.equals("1")) {
			//aggiungere filtro per tirare fuori gli utenti dei tributi			
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FLG_SOLO_ACCRED_IN_DOMINIO|*|1|*|SOLO_ACCRED_IN_APPL|*|TRIBUTI|*|STR_IN_COD_RAPIDO|*|" + codice + "|*|STR_IN_DES|*|" + cognomeNome + "|*|STR_IN_USERNAME|*|" + username + "|*|CI_TO_ADD|*|" + idUtente + "|*|CI_TO_EXCLUDE|*|" + idUtenteToExclude);
		} else {
			//se non ho i tributi e ho la lista degli idprocessType arrivo dalla lista dei procedimenti in iter e da lavorare
			if(StringUtils.isNotBlank(listaIdProcessType)) {
				lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FLG_SOLO_ACCRED_IN_DOMINIO|*|1|*|LISTA_ID_PROCESS_TYPE|*|" + listaIdProcessType + "|*|STR_IN_DES|*|" + cognomeNome + "|*|STR_IN_USERNAME|*|" + username);
			}else {
				lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FLG_SOLO_ACCRED_IN_DOMINIO|*|1|*|STR_IN_COD_RAPIDO|*|" + codice + "|*|STR_IN_DES|*|" + cognomeNome + "|*|STR_IN_USERNAME|*|" + username + "|*|CI_TO_ADD|*|" + idUtente + "|*|CI_TO_EXCLUDE|*|" + idUtenteToExclude);
			}
		}
		
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		PaginatorBean<UtenteBean> lPaginatorBean = new PaginatorBean<UtenteBean>();		
		
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<UtenteBean> lList = XmlListaUtility.recuperaLista(xmlLista, UtenteBean.class);
			lPaginatorBean.setData(lList);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lList.size());
			lPaginatorBean.setTotalRows(lList.size());			
		} 
		return lPaginatorBean;
	}
}