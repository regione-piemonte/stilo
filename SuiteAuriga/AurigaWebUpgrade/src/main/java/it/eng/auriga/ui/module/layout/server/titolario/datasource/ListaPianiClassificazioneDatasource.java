/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.titolario.datasource.bean.PianoClassifBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "ListaPianiClassificazioneDatasource")
public class ListaPianiClassificazioneDatasource extends AbstractFetchDataSource<PianoClassifBean>{

	@Override
	public PaginatorBean<PianoClassifBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		DmpkLoadComboDmfn_load_comboBean bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		bean.setTipocomboin("PIANI_CLASSIFICAZIONE");		
		DmpkLoadComboDmfn_load_combo store = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = store.execute(getLocale(), lAurigaLoginBean, bean);
		PaginatorBean<PianoClassifBean> lPaginatorBean = new PaginatorBean<PianoClassifBean>();				
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} 		
		else 
		{
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<PianoClassifBean> lista = XmlListaUtility.recuperaLista(xmlLista, PianoClassifBean.class);
			for (PianoClassifBean lPianoClassifBean : lista){				
				lPianoClassifBean.setDisplayValue(lPianoClassifBean.getDisplay());
			}		
			lPaginatorBean.setData(lista);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());			
		} 		
		return lPaginatorBean;			
	}	
}
