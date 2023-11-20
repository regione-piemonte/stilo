/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "RichiesteInvioMailMassivoDaXlsSelectDataSource")
public class RichiesteInvioMailMassivoDaXlsSelectDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {
	
	private static Logger mLogger = Logger.getLogger(RichiesteInvioMailMassivoDaXlsSelectDataSource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro(): "";

		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("RICH_INVIO_MASSIVO_MAIL_DA_XLS");
		String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro;

		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(null);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		if (StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			try {
				lListResult = XmlUtility.recuperaListaSempliceSubstring(xmlLista);
			} catch (Exception e) {
				mLogger.warn(e);
			}
		}

		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;
	}

	private String preparaDescrizioneMail(String tipoMail, String descrizione) {
		Map<String, String> flagsMap = getFlag();
		if (flagsMap.containsKey(tipoMail)) {
			return "<div><img src=\"images/" + flagsMap.get(tipoMail) + "\" width=\"13\" height=\"13\" align=LEFT &nbsp/>" + " " + descrizione + "</div>";
		} else {
			return "<div><img src=\"images/" + "blank.png" + "\" width=\"13\" height=\"13\" align=LEFT &nbsp/>" + " " + descrizione + "</div>";
		}
	}

	private Map<String, String> getFlag() {
		Map<String, String> flagsMap = new HashMap<String, String>();
		flagsMap.put("PEC", "mail/PEC.png");
		flagsMap.put("PEO", "mail/PEO.png");
		return flagsMap;
	}

}
