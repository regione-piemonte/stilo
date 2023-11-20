/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attiTrasparenza.datasource.bean.TrasparenzaAllegatiBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

@Datasource(id = "LoadComboTrasparenzaAllegatiDataSource")
public class LoadComboTrasparenzaAllegatiDataSource extends AbstractFetchDataSource<TrasparenzaAllegatiBean> {	
	
	@Override
	public PaginatorBean<TrasparenzaAllegatiBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		String tabellaValori = null;
		if (getExtraparams().get("nomeCombo") != null) {
			tabellaValori = StringUtils.isNotBlank(getExtraparams().get("nomeCombo")) ? getExtraparams().get("nomeCombo") : null;
		}
		String valoreAttribChiave = null;
		if (getExtraparams().get("valoreChiave") != null) {
			valoreAttribChiave = StringUtils.isNotBlank(getExtraparams().get("valoreChiave")) ? getExtraparams().get("valoreChiave") : null;
		}
		String nomeAttribChiave = null;
		
		switch (tabellaValori) {
	        case "VLR03": {
	        	nomeAttribChiave = "TIPOPROV_NORMA_TITOLOATTRIB";
	            break;
	        }
	        case "VLR04": {
	        	nomeAttribChiave = "TIPOLOGIA_NORMA";
	            break;
	        }
	        case "VLR05": {
	        	nomeAttribChiave = "PROVVEDIMENTO";
	            break;
	        }
		}
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin(tabellaValori);
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(nomeAttribChiave + "|*|" + valoreAttribChiave);
        lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		lDmpkLoadComboDmfn_load_comboBean.setPkrecin(null);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		PaginatorBean<TrasparenzaAllegatiBean> lPaginatorBean = new PaginatorBean<TrasparenzaAllegatiBean>();		
		
		if(lStoreResultBean.getDefaultMessage() != null) {		
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<SimpleKeyValueBean> lista = XmlUtility.recuperaListaSemplice(xmlLista);
			

			List<TrasparenzaAllegatiBean> listaAllegati = new ArrayList<TrasparenzaAllegatiBean>();
			TrasparenzaAllegatiBean allegatiBean = new TrasparenzaAllegatiBean();
			
			String listaAllegatiObbligatori = "";
			String listaAllegatiNonObbligatori = "";
			String listaIdAllegatiObbligatori = "";
			String listaIdAllegatiNonObbligatori = "";
			String avviso = "";
			
			for (SimpleKeyValueBean keyValue : lista) {
				// colonna allegati
				if (StringUtils.isNotBlank(keyValue.getKey())) {
					 String[] allegato = StringUtils.split(keyValue.getKey(), "|$|");
					 String idDocType = allegato[0];
					 String descrizione = allegato[1];
					 String flgObbl = allegato[2];
					 if (flgObbl != null && flgObbl.equals("1")) {
						 listaAllegatiObbligatori += descrizione + "|";
						 listaIdAllegatiObbligatori += idDocType + "|";
					 } else {
						 listaAllegatiNonObbligatori += descrizione + "|";
						 listaIdAllegatiNonObbligatori += idDocType + "|";;
					 }
					 
				}
				
				if (StringUtils.isNotBlank(keyValue.getValue())) {
					// colonna avvisi
					avviso += keyValue.getValue();
				}
				
			}
			
			allegatiBean.setListaAllegatiNonObbligatoriTrasparenza(listaAllegatiNonObbligatori);
			allegatiBean.setListaIdAllegatiNonObbligatoriTrasparenza(listaIdAllegatiNonObbligatori);
			
			allegatiBean.setListaAllegatiObbligatoriTrasparenza(listaAllegatiObbligatori);
			allegatiBean.setListaIdAllegatiObbligatoriTrasparenza(listaIdAllegatiObbligatori);
			
			allegatiBean.setAvviso(avviso);
			
			listaAllegati.add(allegatiBean);

			lPaginatorBean.setData(listaAllegati);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());			
		} 
		
		return lPaginatorBean;
		
	}
	
}