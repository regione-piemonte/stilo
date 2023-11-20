/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.StatoPostDiscussioneBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboStatoPostDiscussioneDataSource")
public class LoadComboStatoPostDiscussioneDataSource extends OptionFetchDataSource<StatoPostDiscussioneBean> {

	private static Logger mLogger = Logger.getLogger(LoadComboStatoPostDiscussioneDataSource.class);

	@Override
	public PaginatorBean<StatoPostDiscussioneBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		List<StatoPostDiscussioneBean> lListResult = new ArrayList<StatoPostDiscussioneBean>();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "";
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("GENERICO");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("NOME_TABELLA|*|DMT_UNITA_DOC|*|NOME_COL_TABELLA|*|COD_STATO_DETT|*|ID_USER_LAVORO|*|" + idUserLavoro + "|*|FINALITA|*|ATTI_DISCUSSI_IN_SEDUTA");
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  new DmpkLoadComboDmfn_load_combo().execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		try {
			lListResult = XmlListaUtility.recuperaLista(xmlLista, StatoPostDiscussioneBean.class);
		} catch (Exception e) {
			mLogger.warn(e);
		}
		
		PaginatorBean<StatoPostDiscussioneBean> lPaginatorBean = new PaginatorBean<StatoPostDiscussioneBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;	
	}
}