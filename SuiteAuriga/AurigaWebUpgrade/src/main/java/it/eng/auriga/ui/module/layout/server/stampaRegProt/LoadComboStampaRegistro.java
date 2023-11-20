/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.stampaRegProt.bean.StampaRegistroBean;
import it.eng.auriga.ui.module.layout.server.stampaRegProt.bean.XmlLIstaStampaPropostaAttoBean;
import it.eng.auriga.ui.module.layout.server.stampaRegProt.bean.XmlLIstaStampaRegistroRepertorioBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.util.List;

@Datasource(id = "LoadComboStampaRegistro")
public class LoadComboStampaRegistro extends AbstractFetchDataSource<StampaRegistroBean>{

	@Override
	public PaginatorBean<StampaRegistroBean> fetch(AdvancedCriteria criteria, Integer startRow,
			Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String tipoStampa = getExtraparams().get("tipoStampa");		
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		
		lDmpkLoadComboDmfn_load_comboBean.setCodidconnectiontokenin(loginBean.getToken());
		
		String altriParametriIn = "";
		
		if(tipoStampa.equalsIgnoreCase("stampa_reg_repertorio")){
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("SIGLA_REG_NUM_UD");
			altriParametriIn = "COD_CATEGORIA|*|R|*|";
		}
		else if(tipoStampa.equalsIgnoreCase("stampa_reg_prot")){
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("SIGLA_REG_NUM_UD");
			altriParametriIn = "COD_CATEGORIA|*|PROT|*|";
		}
		else 
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ATTO_CON_FLUSSO_WF");
		
		altriParametriIn = altriParametriIn+"FLG_SOLO_GESTIBILI|*|1|*|ID_USER_LAVORO|*|" + (loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "");
		
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametriIn);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		PaginatorBean<StampaRegistroBean> paginatorBean = new PaginatorBean<StampaRegistroBean>();		
		
		if(lStoreResultBean.getDefaultMessage() != null) {
			paginatorBean.setStartRow(0);
			paginatorBean.setEndRow(0);
			paginatorBean.setTotalRows(0);			
		}
		
		else {
			
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			
			if(tipoStampa.equalsIgnoreCase("stampa_reg_repertorio") || tipoStampa.equalsIgnoreCase("stampa_reg_prot")){
				List<XmlLIstaStampaRegistroRepertorioBean> lista = XmlListaUtility.recuperaLista(xmlLista, XmlLIstaStampaRegistroRepertorioBean.class);

				StampaRegistroBean stampaRegistroBean = null;
				
				for (XmlLIstaStampaRegistroRepertorioBean lRiga : lista){
					stampaRegistroBean = new StampaRegistroBean();
					stampaRegistroBean.setIdRegistro(lRiga.getSigla());
					stampaRegistroBean.setVoceRegistro(lRiga.getDescrizione());
					paginatorBean.addRecord(stampaRegistroBean);
				}
				
				paginatorBean.setStartRow(0);
				paginatorBean.setEndRow(lista.size());
				paginatorBean.setTotalRows(lista.size());		
			}
			else{
				
				List<XmlLIstaStampaPropostaAttoBean> lista = XmlListaUtility.recuperaLista(xmlLista, XmlLIstaStampaPropostaAttoBean.class);

				StampaRegistroBean stampaRegistroBean = null;
				
				for (XmlLIstaStampaPropostaAttoBean lRiga : lista){
					stampaRegistroBean = new StampaRegistroBean();
					stampaRegistroBean.setIdRegistro(lRiga.getSigla());
					stampaRegistroBean.setVoceRegistro(lRiga.getDescrizione());
					paginatorBean.addRecord(stampaRegistroBean);
				}
				
				paginatorBean.setStartRow(0);
				paginatorBean.setEndRow(lista.size());
				paginatorBean.setTotalRows(lista.size());		
				
			}
			
				
		} 
		
		return paginatorBean;
		
	}
	
}
