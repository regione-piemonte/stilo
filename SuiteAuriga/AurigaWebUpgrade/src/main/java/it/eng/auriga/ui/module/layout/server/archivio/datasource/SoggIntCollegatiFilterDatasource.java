/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UoProtocollanteBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "SoggIntCollegatiUoSvFilterDatasource")
public class SoggIntCollegatiFilterDatasource extends AbstractFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = lAurigaLoginBean.getToken();
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		DmpkLoadComboDmfn_load_comboBean bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setCodidconnectiontokenin(token);
		bean.setTipocomboin("SOGG_INT_COLLEGATI_UTENTE");
		bean.setFlgsolovldin(new BigDecimal(1));
		String altriParametri = "TIPI_SOGG_INT|*|UO;SV|*|FLG_ANCHE_CON_DELEGA|*|1|*|ID_USER_LAVORO|*|" + idUserLavoro;
		String idNode = getExtraparams().get("idNode");
		// nelle sezioni della scrivania "Su tutta l'area"
		if (idNode != null && !idNode.endsWith(".R")) {
			altriParametri += "|*|FLG_INCL_SOTTOUO_SV|*|1";
		}
		bean.setAltriparametriin(altriParametri);
		DmpkLoadComboDmfn_load_combo store = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = store.execute(getLocale(), lAurigaLoginBean, bean);
		if (lStoreResultBean.isInError()) {
			throw new StoreException(lStoreResultBean);
		}
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();		
		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		if(StringUtils.isNotBlank(xmlLista)) {
			lListResult = XmlUtility.recuperaListaSemplice(xmlLista);
		}
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());		
		return lPaginatorBean;
	}

}
