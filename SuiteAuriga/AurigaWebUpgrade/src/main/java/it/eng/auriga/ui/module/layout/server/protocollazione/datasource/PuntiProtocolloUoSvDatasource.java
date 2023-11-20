/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "PuntiProtocolloUoSvDatasource")
public class PuntiProtocolloUoSvDatasource extends AbstractFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		String idAssegnatario = getExtraparams().get("idAssegnatario");
		List<SimpleKeyValueBean> lista = new ArrayList<SimpleKeyValueBean>();
		if(StringUtils.isNotBlank(idAssegnatario)) {
			DmpkLoadComboDmfn_load_comboBean bean = new DmpkLoadComboDmfn_load_comboBean();
			bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
			bean.setTipocomboin("PP_ASSOCIATI_UO");			
			String flgUoSv = idAssegnatario.substring(0, 2);
			String idUoSv = idAssegnatario.substring(2);
			String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro;
			if(flgUoSv.equals("UO")) {
				altriParametri += "|*|ID_UO|*|" + idUoSv;
			} else if(flgUoSv.equals("SV")) {
				altriParametri += "|*|ID_SCRIVANIA|*|" + idUoSv;
			}			
			bean.setAltriparametriin(altriParametri);
			DmpkLoadComboDmfn_load_combo store = new DmpkLoadComboDmfn_load_combo();
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = store.execute(getLocale(), lAurigaLoginBean, bean);
			if (storeResult.isInError()) {
				throw new StoreException(storeResult);
			}
			String xmlLista = storeResult.getResultBean().getListaxmlout();
			lista = XmlUtility.recuperaListaSemplice(xmlLista);			
		}
		PaginatorBean<SimpleKeyValueBean> result = new PaginatorBean<SimpleKeyValueBean>(lista);
		return result;
	}

}
