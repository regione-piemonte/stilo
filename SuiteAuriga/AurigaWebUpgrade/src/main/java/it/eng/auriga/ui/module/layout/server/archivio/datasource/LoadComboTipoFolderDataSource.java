/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.FolderTypeBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TipoDocumentoBean;
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

@Datasource(id = "LoadComboTipoFolderDataSource")
public class LoadComboTipoFolderDataSource extends AbstractFetchDataSource<FolderTypeBean> {
	@Override
	public PaginatorBean<FolderTypeBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "";
		
		String idClassifica = StringUtils.isNotBlank(getExtraparams().get("idClassifica")) ? getExtraparams().get("idClassifica") : "";
		String idFolderApp  = StringUtils.isNotBlank(getExtraparams().get("idFolderApp")) ? getExtraparams().get("idFolderApp")  : "";
		String idFolderType      = StringUtils.isNotBlank(getExtraparams().get("idFolderType"))  ? getExtraparams().get("idFolderType") : "";
		boolean showErrorMsg = StringUtils.isNotBlank(getExtraparams().get("showErrorMsg")) && "true".equalsIgnoreCase(getExtraparams().get("showErrorMsg"));
		boolean isFromFilter = StringUtils.isNotBlank(getExtraparams().get("isFromFilter")) && "true".equalsIgnoreCase(getExtraparams().get("isFromFilter"));
		
		String descrizione = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("descFolderType")) {
					descrizione = (String) criterion.getValue();
					break;
				}																						 
			}
		}
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPO_FOLDER");		
		
		String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro;
		// Differenziare per i filtri dove non va passato FLG_SOLO_ASSEGNABILI a 1 in AltriParametriIn
		if (!isFromFilter) {
			altriParametri += "|*|FLG_SOLO_ASSEGNABILI|*|1";
		}
		// Se l'utente ha digitato un filtro, il CI_TO_ADD non deve essere passato
		if (StringUtils.isNotBlank(descrizione)) {
			altriParametri += "|*|STR_IN_DES|*|" + descrizione;
		} else {
			altriParametri += "|*|CI_TO_ADD|*|" + idFolderType;
		}
		if (StringUtils.isNotBlank(idClassifica)) {
			altriParametri += "|*|ID_CLASSIFICAZIONE|*|" + idClassifica;
		}
		if (StringUtils.isNotBlank(idFolderApp)) {
			altriParametri += "|*|ID_FOLDER_APP|*|" + idFolderApp;
		}
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
			
		// Differenziare per i filtri dove non va passato FlgSoloVldIn a 1
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(!isFromFilter ? new BigDecimal(1) : null);
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
       
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		PaginatorBean<FolderTypeBean> lPaginatorBean = new PaginatorBean<FolderTypeBean>();		
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);	
			if(showErrorMsg) {
				addMessage("Nessuna tipologia trovata: " + lStoreResultBean.getDefaultMessage(), "", MessageType.ERROR);
			}
		} 
		else 
		{
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<FolderTypeBean> data = XmlListaUtility.recuperaLista(xmlLista, FolderTypeBean.class);
			
			// Se provengo dai filtri, aggiungo in testa il valore "non tipizzato"
			if (isFromFilter) {
				if (data.size()>0) {
					FolderTypeBean nonTipizzatoValue = new FolderTypeBean();				
					nonTipizzatoValue.setIdFolderType("-1");
                    String icon = "warning.png";
					String valDescTipoDocumento = "<html><div>" + "<b><i>non tipizzato<i></b>" + "</div></html>";
					nonTipizzatoValue.setDescFolderType(valDescTipoDocumento);	
					data.add(0, nonTipizzatoValue);
				}				
			}
			lPaginatorBean.setData(data);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(data.size());
			lPaginatorBean.setTotalRows(data.size());			
		} 
		return lPaginatorBean;
	}
}
