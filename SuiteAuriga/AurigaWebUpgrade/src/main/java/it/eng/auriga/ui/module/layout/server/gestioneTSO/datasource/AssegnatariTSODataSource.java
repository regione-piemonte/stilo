/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneTSO.datasource.bean.AssegnatarioTSOBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

/**
 * 
 * @author dbe4235
 *
 */


@Datasource(id="AssegnatariTSODataSource")
public class AssegnatariTSODataSource extends AbstractDataSource<AssegnatarioTSOBean, AssegnatarioTSOBean> {

	@Override
	public PaginatorBean<AssegnatarioTSOBean> fetch(AdvancedCriteria 	riteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());			
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();			
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("IstruttoriTSO");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(null);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), lAurigaLoginBean, lDmpkLoadComboDmfn_load_comboBean);
		
		List<AssegnatarioTSOBean> data = new ArrayList<AssegnatarioTSOBean>();
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		if (!lStoreResultBean.isInError()) {
			for (XmlListaSimpleBean lXmlListaSimpleBean : XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class)) {
				AssegnatarioTSOBean lAssegnatarioTSOBean = new AssegnatarioTSOBean();
				lAssegnatarioTSOBean.setIdAssegnatario(lXmlListaSimpleBean.getKey());
				lAssegnatarioTSOBean.setDescrizioneAssegnatario(lXmlListaSimpleBean.getValue());
				data.add(lAssegnatarioTSOBean);
			}	
		}
		
		PaginatorBean<AssegnatarioTSOBean> lPaginatorBean = new PaginatorBean<AssegnatarioTSOBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(data.size());
		lPaginatorBean.setTotalRows(data.size());
		
		return lPaginatorBean;		
	}

	@Override
	public AssegnatarioTSOBean add(AssegnatarioTSOBean bean)
			throws Exception {		
		return null;
	}
	
	@Override
	public AssegnatarioTSOBean get(AssegnatarioTSOBean bean)
			throws Exception {
		return null;
	}

	@Override
	public AssegnatarioTSOBean remove(AssegnatarioTSOBean bean)
			throws Exception {
		return null;
	}

	@Override
	public AssegnatarioTSOBean update(AssegnatarioTSOBean bean,
			AssegnatarioTSOBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AssegnatarioTSOBean bean) throws Exception {
		return null;
	}

}