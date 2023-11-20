/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.elenchiAlbi.datasource.bean.TipoElencoAlboBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.List;

@Datasource(id = "TipiElenchiAlbiDataSource")
public class TipiElenchiAlbiDataSource extends AbstractFetchDataSource<TipoElencoAlboBean> {

	@Override
	public PaginatorBean<TipoElencoAlboBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
				
		DmpkLoadComboDmfn_load_comboBean bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setTipocomboin("TIPO_ELENCO_ALBO");
		bean.setFlgsolovldin(BigDecimal.ONE);
		bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		bean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro + "|*|FLG_SOLO_GESTIBILI|*|0");
		DmpkLoadComboDmfn_load_combo store = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> result = store.execute(getLocale(), lAurigaLoginBean, bean);
		bean = result.getResultBean();
		String lStringXml = bean.getListaxmlout();
		
		List<TipoElencoAlboBean> resultList = XmlListaUtility.recuperaLista(lStringXml, TipoElencoAlboBean.class);
		PaginatorBean<TipoElencoAlboBean> toReturn = new PaginatorBean<TipoElencoAlboBean>();
		toReturn.setStartRow(0);
		toReturn.setEndRow(resultList.size());
		toReturn.setData(resultList);
		toReturn.setTotalRows(resultList.size());
		
		return toReturn;
		
	}

	
}
