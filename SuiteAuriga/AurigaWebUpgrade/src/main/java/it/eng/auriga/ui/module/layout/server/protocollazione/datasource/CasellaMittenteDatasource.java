/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.InfoCasellaBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.SimpleBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.CasellaMittenteBean;
import it.eng.aurigamailbusiness.bean.MailboxAccountBean;
import it.eng.aurigamailbusiness.bean.TProfiliFruitoriMgoBean;
import it.eng.aurigamailbusiness.bean.TProfiliUtentiMgoBean;
import it.eng.aurigamailbusiness.database.utility.bean.listaprofilituentesucasella.ListaProfiliUtenteSuCasellaIn;
import it.eng.aurigamailbusiness.database.utility.bean.listaprofilituentesucasella.ListaProfiliUtenteSuCasellaOut;
import it.eng.client.AurigaMailService;
import it.eng.client.CasellaUtility;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.core.business.TFilterFetch;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "CasellaMittenteDatasource")
public class CasellaMittenteDatasource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	
	private static Logger mLogger = Logger.getLogger(CasellaMittenteDatasource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "";
	
		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("CASELLE_INV_RIC");
		String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro + "|*|FINALITA|*|INVIO|*|TIPO_CASELLA|*|";

		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		if (StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			try {
				for (InfoCasellaBean lInfoCasellaBean : XmlListaUtility.recuperaLista(xmlLista, InfoCasellaBean.class)) {
					SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
					lSimpleKeyValueBean.setKey(lInfoCasellaBean.getIdAccount());
					lSimpleKeyValueBean.setValue(lInfoCasellaBean.getAccount());
					lSimpleKeyValueBean.setDisplayValue(preparaDescrizioneMail(lInfoCasellaBean.getTipoMail(), lInfoCasellaBean.getAccount()));
					lListResult.add(lSimpleKeyValueBean);
				}
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
			return "<div><img src=\"images/" + flagsMap.get(tipoMail) + "\" width=\"14\" height=\"14\" style=\"vertical-align:middle;\" /> " + descrizione + "</div>";
		} else {
			return "<div><img src=\"images/" + "blank.png" + "\" width=\"14\" height=\"14\" style=\"vertical-align:middle;\" /> " + descrizione + "</div>";
		}
	}

	private Map<String, String> getFlag() {
		Map<String, String> flagsMap = new HashMap<String, String>();
		flagsMap.put("PEC", "mail/PEC.png");
		flagsMap.put("PEO", "mail/PEO.png");
		return flagsMap;
	}

}
