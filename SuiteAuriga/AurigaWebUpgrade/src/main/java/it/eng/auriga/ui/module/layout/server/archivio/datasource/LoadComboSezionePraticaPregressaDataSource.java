/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.FolderTypeBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TipoFileAllegatoBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
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

@Datasource(id = "LoadComboSezionePraticaPregressaDataSource")
public class LoadComboSezionePraticaPregressaDataSource extends AbstractFetchDataSource<TipoFileAllegatoBean> {
	
	
	@Override
	public PaginatorBean<TipoFileAllegatoBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
				
		String dictionaryEntrySezione = StringUtils.isNotBlank(getExtraparams().get("dictionaryEntrySezione")) ? getExtraparams().get("dictionaryEntrySezione") : "";		
		String idTipoFileAllegato = StringUtils.isNotBlank(getExtraparams().get("idTipoFileAllegato")) ? getExtraparams().get("idTipoFileAllegato") : "";		
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("VALORI_DIZIONARIO");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("DICTIONARY_ENTRY|*|" + dictionaryEntrySezione + "|*|ID_USER_LAVORO|*|" + idUserLavoro + "|*|CI_TO_ADD|*|" + idTipoFileAllegato);
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
			List<XmlListaSimpleBean> lListXml = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);

			for (XmlListaSimpleBean lRiga : lListXml){
				TipoFileAllegatoBean lTipoFileAllegatoBean = new TipoFileAllegatoBean();
				lTipoFileAllegatoBean.setIdTipoFileAllegato(lRiga.getKey());
				lTipoFileAllegatoBean.setDescTipoFileAllegato(lRiga.getValue());
				lPaginatorBean.addRecord(lTipoFileAllegatoBean);
			}
			
			
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lListXml.size());
			lPaginatorBean.setTotalRows(lListXml.size());			
		} 
		
		return lPaginatorBean;
		
	}

}
