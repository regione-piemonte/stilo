/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.PuntiVenditaBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;

@Datasource(id = "LoadComboPuntiVenditaDataSource")
public class LoadComboPuntiVenditaDataSource extends AbstractFetchDataSource<PuntiVenditaBean> {
	@Override
	public PaginatorBean<PuntiVenditaBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		String denominazionePuntoVendita    = "";
		String cittaPuntoVendita          = "";
		String targaProvinciaPuntoVendita = "";
		String capPuntoVendita            = "";
		String indirizzoPuntoVendita      = "";
		
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("denominazionePuntoVendita")) {
					denominazionePuntoVendita = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("cittaPuntoVendita")) {
					cittaPuntoVendita = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("targaProvinciaPuntoVendita")) {
					targaProvinciaPuntoVendita = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("capPuntoVendita")) {
					capPuntoVendita = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("indirizzoPuntoVendita")) {
					indirizzoPuntoVendita = (String) criterion.getValue();					
				}
			}
		}	
				
		String idPuntoVendita = StringUtils.isNotBlank(getExtraparams().get("idPuntoVendita")) ? getExtraparams().get("idPuntoVendita") : "";
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);		
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("PUNTI_VENDITA_EIIFACT");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("STR_IN_DES|*|" + denominazionePuntoVendita +  "|*|CITTA|*|" + cittaPuntoVendita + "|*|TARGA_PROV|*|" + targaProvinciaPuntoVendita + "|*|CAP|*|" + capPuntoVendita + "|*|INDIRIZZO|*|" + indirizzoPuntoVendita +   "|*|CI_TO_ADD|*|" +  idPuntoVendita);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
				
		PaginatorBean<PuntiVenditaBean> lPaginatorBean = new PaginatorBean<PuntiVenditaBean>();		
		
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} 
		else 
		{
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<PuntiVenditaBean> lista = XmlListaUtility.recuperaLista(xmlLista, PuntiVenditaBean.class);
			lPaginatorBean.setData(lista);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());				
		} 
		return lPaginatorBean;
	}
}