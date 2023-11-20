/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TipoDocumentoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TipoFileAllegatoBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;


@Datasource(id = "LoadComboTipoFileAllegatoDataSource")
public class LoadComboTipoFileAllegatoDataSource extends AbstractFetchDataSource<TipoFileAllegatoBean> {
	
	
	@Override
	public PaginatorBean<TipoFileAllegatoBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
				
		String idTipoFileAllegato = StringUtils.isNotBlank(getExtraparams().get("idTipoFileAllegato")) ? getExtraparams().get("idTipoFileAllegato") : "";		
		String idDocTypePrimario = StringUtils.isNotBlank(getExtraparams().get("idDocTypePrimario")) ? getExtraparams().get("idDocTypePrimario") : "";	
		
		String descrizione = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("descTipoFileAllegato")) {
					descrizione = (String) criterion.getValue();					
				}
			}
		}			
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPO_DOC");
		
				
		// Se l'utente ha digitato un filtro, il CI_TO_ADD non deve essere passato
		if (descrizione!=null && !descrizione.equalsIgnoreCase("")) {
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + (AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "") + "|*|FLG_SOLO_ASSEGNABILI|*|1|*|FLG_ALLEGATO_PRIMARIO|*|A|*|ID_DOC_TYPE_PRIMARIO|*|" + idDocTypePrimario + "|*|STR_IN_DES|*|" + descrizione);
		} else {
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + (AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "") + "|*|FLG_SOLO_ASSEGNABILI|*|1|*|FLG_ALLEGATO_PRIMARIO|*|A|*|ID_DOC_TYPE_PRIMARIO|*|" + idDocTypePrimario + "|*|CI_TO_ADD|*|" + idTipoFileAllegato);
		}

		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		PaginatorBean<TipoFileAllegatoBean> lPaginatorBean = new PaginatorBean<TipoFileAllegatoBean>();		
		
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} 
		
		else 
		{
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<TipoDocumentoBean> lListXml = XmlListaUtility.recuperaLista(xmlLista, TipoDocumentoBean.class);

			for (TipoDocumentoBean lRiga : lListXml){
				TipoFileAllegatoBean lTipoFileAllegatoBean = new TipoFileAllegatoBean();
				lTipoFileAllegatoBean.setIdTipoFileAllegato(lRiga.getIdTipoDocumento());
				lTipoFileAllegatoBean.setDescTipoFileAllegato(lRiga.getDescTipoDocumento());
				lTipoFileAllegatoBean.setFlgRichiestaFirmaDigitale(lRiga.getFlgRichiestaFirmaDigitale());
				lPaginatorBean.addRecord(lTipoFileAllegatoBean);
			}
			
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lListXml.size());
			lPaginatorBean.setTotalRows(lListXml.size());			
		} 
		
		return lPaginatorBean;
		
	}

}
