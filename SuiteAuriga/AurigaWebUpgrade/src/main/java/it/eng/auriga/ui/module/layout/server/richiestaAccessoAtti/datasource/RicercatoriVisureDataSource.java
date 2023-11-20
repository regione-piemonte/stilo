/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.RicercatoriVisureBean;
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

@Datasource(id="RicercatoriVisureDataSource")
public class RicercatoriVisureDataSource extends AbstractDataSource<RicercatoriVisureBean, RicercatoriVisureBean> {

	@Override
	public PaginatorBean<RicercatoriVisureBean> fetch(AdvancedCriteria 	riteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		//String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";				
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();			
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("RICERCATORI_VISURE_SUE");
		//lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro);
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(null);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), lAurigaLoginBean, lDmpkLoadComboDmfn_load_comboBean);
		
		List<RicercatoriVisureBean> data = new ArrayList<RicercatoriVisureBean>();
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		if (!lStoreResultBean.isInError()) {
			for (XmlListaSimpleBean lXmlListaSimpleBean : XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class)) {
				RicercatoriVisureBean lRicercatoriVisureBean = new RicercatoriVisureBean();
				lRicercatoriVisureBean.setIdUtenteRicercatore(lXmlListaSimpleBean.getKey());
				lRicercatoriVisureBean.setCognomeNomeRicercatore(lXmlListaSimpleBean.getValue());
				data.add(lRicercatoriVisureBean);
			}	
		}
		
		PaginatorBean<RicercatoriVisureBean> lPaginatorBean = new PaginatorBean<RicercatoriVisureBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(data.size());
		lPaginatorBean.setTotalRows(data.size());
		
		return lPaginatorBean;		
	}

	@Override
	public RicercatoriVisureBean add(RicercatoriVisureBean bean)
			throws Exception {		
		return null;
	}
	
	@Override
	public RicercatoriVisureBean get(RicercatoriVisureBean bean)
			throws Exception {
		return null;
	}

	@Override
	public RicercatoriVisureBean remove(RicercatoriVisureBean bean)
			throws Exception {
		return null;
	}

	@Override
	public RicercatoriVisureBean update(RicercatoriVisureBean bean,
			RicercatoriVisureBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(RicercatoriVisureBean bean) throws Exception {
		return null;
	}

}