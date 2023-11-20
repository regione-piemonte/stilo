/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "LoadComboStatoDettUdDataSource")
public class LoadComboStatoDettUdDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {

	private static Logger mLogger = Logger.getLogger(LoadComboStatoDettUdDataSource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "";
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("GENERICO");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		String altriParametri = "NOME_TABELLA|*|DMT_UNITA_DOC|*|NOME_COL_TABELLA|*|COD_STATO_DETT|*|ID_USER_LAVORO|*|" + idUserLavoro;
		if(StringUtils.isNotBlank(getExtraparams().get("finalita"))) {
			altriParametri += "|*|FINALITA|*|" + getExtraparams().get("finalita");
		}
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  new DmpkLoadComboDmfn_load_combo().execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		try {
			lListResult = XmlUtility.recuperaListaSempliceSubstring(xmlLista);
			if(lListResult.size() > 0) {
				SimpleKeyValueBean nessunTipoKeyValueBean = new SimpleKeyValueBean();
				nessunTipoKeyValueBean.setKey("NESSUN_TIPO");
				nessunTipoKeyValueBean.setValue("<html><div>" + "<b><i>nessuno stato<i></b>" + "</div></html>");
				lListResult.add(0, nessunTipoKeyValueBean);
			}
		} catch (Exception e) {
			mLogger.warn(e);
		}
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;	
	}
}