/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UoAssociatoUtenteBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.util.List;

@Datasource(id = "UoAssociaUtenteDatasource")
public class UoAssociaUtenteDatasource extends OptionFetchDataSource<UoAssociatoUtenteBean>{

	@Override
	public PaginatorBean<UoAssociatoUtenteBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		DmpkLoadComboDmfn_load_comboBean bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		bean.setTipocomboin("SOGG_INT_COLLEGATI_UTENTE");
		bean.setAltriparametriin("TIPI_SOGG_INT|*|UO|*|FLG_ANCHE_CON_DELEGA|*|1|*|ID_USER_LAVORO|*|" + 
				(lAurigaLoginBean.getIdUserLavoro()!=null?lAurigaLoginBean.getIdUserLavoro():""));
		DmpkLoadComboDmfn_load_combo store = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = store.execute(getLocale(), lAurigaLoginBean, bean);
		if (storeResult.isInError()){
			throw new StoreException(storeResult);
		}
		bean = storeResult.getResultBean();
		String lStrXml = bean.getListaxmlout();
		List<UoAssociatoUtenteBean> lListResult = XmlListaUtility.recuperaLista(lStrXml, UoAssociatoUtenteBean.class);
		
		for (UoAssociatoUtenteBean uoAssociatoUtenteBean : lListResult) 
			uoAssociatoUtenteBean.setDisplayValue(uoAssociatoUtenteBean.getDescrizione());
		
		
		PaginatorBean<UoAssociatoUtenteBean> result = new PaginatorBean<UoAssociatoUtenteBean>(lListResult);
		return result;
	}
	
	
}
