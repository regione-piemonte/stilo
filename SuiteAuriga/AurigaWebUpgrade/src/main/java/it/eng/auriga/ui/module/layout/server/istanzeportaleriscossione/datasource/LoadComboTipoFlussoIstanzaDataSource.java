/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.istanzeportaleriscossione.datasource.bean.TipoFlussoIstanzaBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboTipoFlussoIstanzaDataSource")
public class LoadComboTipoFlussoIstanzaDataSource extends AbstractFetchDataSource<TipoFlussoIstanzaBean> {

	@Override
	public PaginatorBean<TipoFlussoIstanzaBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";	
		
		String tipoIstanza = StringUtils.isNotBlank(getExtraparams().get("tipoIstanza")) ? getExtraparams().get("tipoIstanza") : "";
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("FLUSSI_WF_PROC_TRIBUTI");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro + "|*|NOME_DOC_TYPE|*|" + tipoIstanza);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);

		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),	lAurigaLoginBean, lDmpkLoadComboDmfn_load_comboBean);

		List<TipoFlussoIstanzaBean> data = new ArrayList<TipoFlussoIstanzaBean>();	
		if (!lStoreResultBean.isInError()) {
			data = XmlListaUtility.recuperaLista(lStoreResultBean.getResultBean().getListaxmlout(), TipoFlussoIstanzaBean.class);			
		}

		PaginatorBean<TipoFlussoIstanzaBean> lPaginatorBean = new PaginatorBean<TipoFlussoIstanzaBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(data.size());
		lPaginatorBean.setTotalRows(data.size());
		
		return lPaginatorBean;		
	}
	
}
