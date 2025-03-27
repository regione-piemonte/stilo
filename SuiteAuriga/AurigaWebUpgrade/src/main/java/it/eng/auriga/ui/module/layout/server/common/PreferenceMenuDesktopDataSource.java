/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.common;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.PreferenceBean;
import it.eng.client.AurigaService;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id="PreferenceMenuDesktopDataSource")
public class PreferenceMenuDesktopDataSource extends AbstractFetchDataSource<PreferenceBean> {
	
	private static Logger mLogger = Logger.getLogger(PreferenceMenuDesktopDataSource.class);

	@Override
	public PaginatorBean<PreferenceBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		PaginatorBean<PreferenceBean> paginatorBean = new PaginatorBean<PreferenceBean>();
		
		String userId = getExtraparams().get("userId");	
		String prefKey = getExtraparams().get("prefKey");
		String prefName = getExtraparams().get("prefName");
		
		if(StringUtils.isBlank(userId)) {
			it.eng.auriga.module.business.beans.AurigaLoginBean loginInfo = AurigaUserUtil.getLoginInfo(getSession());
			if(loginInfo != null && StringUtils.isNotBlank(loginInfo.getUseridForPrefs())) {
				userId = loginInfo.getUseridForPrefs();
			} else {
				userId = getRequest().getRemoteUser();
			}
		}	
		
		if(StringUtils.isBlank(prefName)) {
			if(criteria != null && criteria.getCriteria() != null) {
				for(Criterion criterion : criteria.getCriteria()) {											
					if(criterion.getFieldName() != null && criterion.getFieldName().equals("prefName")) {
						prefName = (String) criterion.getValue();
						break;
					}
				}			
			}
		}
		
		PreferenceBean bean = new PreferenceBean();
		bean.setUserid(userId);
		bean.setPrefName(prefName);
		bean.setPrefKey(prefKey);
		
		List<PreferenceBean> data = null;
		if(StringUtils.isNotBlank(bean.getUserid()) && StringUtils.isNotBlank(bean.getPrefName()) && StringUtils.isNotBlank(bean.getPrefKey())) {
//			PreferenceBean pref = AurigaService.getDaoTUserPreferences().get(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);			
			PreferenceBean pref = getUserPreference(bean);
			if(pref != null) {
				data = new ArrayList<PreferenceBean>();
				data.add(pref);	
			}
		}
			
		if (data == null) {
			paginatorBean.setStartRow(0);
			paginatorBean.setEndRow(0);
			paginatorBean.setTotalRows(0);
		} else {
			paginatorBean.setData(data);
			paginatorBean.setStartRow(0);
			paginatorBean.setEndRow(data.size());
			paginatorBean.setTotalRows(data.size());
		}

		return paginatorBean;
		
	}
	
	public PreferenceBean getUserPreference(PreferenceBean bean) throws Exception {
		if(StringUtils.isNotBlank(bean.getUserid()) && StringUtils.isNotBlank(bean.getPrefKey()) && StringUtils.isNotBlank(bean.getPrefName())) {				
			List<PreferenceBean> lista = getListaUserPreference(bean, 0, 1, null); 
			PreferenceBean pref = lista != null && lista.size() == 1 ? lista.get(0) : null;
			if (pref != null && pref.getUserid() != null && pref.getUserid().equals(bean.getUserid())) {
				// devo prendere solo la preference che corrisponde a quell'userid
				return pref;				
			}			
		} else {
			mLogger.error("Non è stato possibile fare la get della preference con " + getPreferenceInfo(bean));
		}
		return null;
	}
	
	public List<PreferenceBean> getListaUserPreference(PreferenceBean bean, Integer startRow, Integer endRow,
			List<OrderByBean> orderByBeanList) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		List<PreferenceBean> data = null;

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("USER_PREF");
		String altriParametri = "ID_USER_LAVORO|*|"	+ (loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "") + "|*|PREF_KEY|*|" + bean.getPrefKey();
		if (StringUtils.isNotBlank(bean.getUserid())) {
			altriParametri += "|*|USERID|*|" + bean.getUserid();
		}
		if (StringUtils.isNotBlank(bean.getPrefName())) {
			altriParametri += "|*|PREF_NAME|*|" + bean.getPrefName();
		}
		altriParametri += "|*|INCLUDE_VALUES|*|1";
		
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				loginBean, lDmpkLoadComboDmfn_load_comboBean);

		if (StringUtils.isNotBlank(lStoreResultBean.getDefaultMessage())) {
			throw new StoreException(lStoreResultBean);
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<PreferenceBean> lista = XmlListaUtility.recuperaLista(xmlLista, PreferenceBean.class);

			data = new ArrayList<PreferenceBean>();

			for (PreferenceBean prefBean : lista) {
				prefBean.setPrefKey(bean.getPrefKey());
				if (prefBean.getUserid() != null && (prefBean.getUserid().equals("PUBLIC") || prefBean.getUserid().startsWith("PUBLIC."))) {
					prefBean.setKey(prefBean.getUserid() + "|*|" + prefBean.getPrefName());					
					prefBean.setDisplayValue(prefBean.getPrefName() + "&nbsp;<img src=\"images/public.png\" height=\"12\" width=\"12\" align=MIDDLE/>");					
					if(bean.getEscludiPrefPublic() != null && bean.getEscludiPrefPublic()) {
						continue;
					}
				} else {
					prefBean.setKey(prefBean.getPrefName());
					prefBean.setDisplayValue(prefBean.getPrefName());
				}
				data.add(prefBean);
			}
			
			Collections.sort(data, new Comparator<PreferenceBean>() {

				@Override
				public int compare(PreferenceBean pref1, PreferenceBean pref2) {
					return pref1.getPrefName().toLowerCase().compareTo(pref2.getPrefName().toLowerCase());
				}
			});
		}

		return data;
	}
	
	@Override
	public PreferenceBean add(PreferenceBean bean) throws Exception {
		
		if(StringUtils.isBlank(bean.getUserid())) {
			String userId = getExtraparams().get("userId");
			if(StringUtils.isBlank(userId)) {
				it.eng.auriga.module.business.beans.AurigaLoginBean loginInfo = AurigaUserUtil.getLoginInfo(getSession());
				if(StringUtils.isNotBlank(loginInfo.getUseridForPrefs())) {
					userId = loginInfo.getUseridForPrefs();
				} else {
					userId = getRequest().getRemoteUser();
				}
			}
			bean.setUserid(userId);
		}		
		if(StringUtils.isBlank(bean.getPrefKey())) {
			bean.setPrefKey(getExtraparams().get("prefKey"));
		}
		if(StringUtils.isBlank(bean.getPrefName())) {
			String prefName = StringUtils.isNotBlank(getExtraparams().get("prefName")) ? getExtraparams().get("prefName") : "DEFAULT";			
			bean.setPrefName(prefName);
		}
//		if(bean.getSettingTime() == null) {
			bean.setSettingTime(new Date());
//		}
		try {
			AurigaService.getDaoTUserPreferences().save(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);
		} catch (Exception e){
			mLogger.error("Si è verificato un errore durante la save della preference con " + getPreferenceInfo(bean), e);
			throw new StoreException("Si è verificato un errore durante il salvataggio della preference");
		}
		return bean;

	}
	
	@Override
	public PreferenceBean update(PreferenceBean bean, PreferenceBean oldvalue) throws Exception {
		
		if(StringUtils.isBlank(bean.getUserid())) {
			String userId = getExtraparams().get("userId");
			if(StringUtils.isBlank(userId)) {
				it.eng.auriga.module.business.beans.AurigaLoginBean loginInfo = AurigaUserUtil.getLoginInfo(getSession());
				if(StringUtils.isNotBlank(loginInfo.getUseridForPrefs())) {
					userId = loginInfo.getUseridForPrefs();
				} else {
					userId = getRequest().getRemoteUser();
				}
			}
			bean.setUserid(userId);
		}
		if(StringUtils.isBlank(bean.getPrefKey())) {
			bean.setPrefKey(getExtraparams().get("prefKey"));
		}
		if(StringUtils.isBlank(bean.getPrefName())) {
			String prefName = StringUtils.isNotBlank(getExtraparams().get("prefName")) ? getExtraparams().get("prefName") : "DEFAULT";			
			bean.setPrefName(prefName);
		}
//		if(bean.getSettingTime() == null) {
			bean.setSettingTime(new Date());
//		}
		try {
			AurigaService.getDaoTUserPreferences().update(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);		
		} catch (Exception e){
			mLogger.error("Si è verificato un errore durante la save della preference con " + getPreferenceInfo(bean), e);
			throw new StoreException("Si è verificato un errore durante il salvataggio della preference");
		}
		return bean;
	}
	
	@Override
	public PreferenceBean remove(PreferenceBean bean) throws Exception {
		
		if(StringUtils.isBlank(bean.getUserid())) {
			String userId = getExtraparams().get("userId");
			if(StringUtils.isBlank(userId)) {
				it.eng.auriga.module.business.beans.AurigaLoginBean loginInfo = AurigaUserUtil.getLoginInfo(getSession());
				if(StringUtils.isNotBlank(loginInfo.getUseridForPrefs())) {
					userId = loginInfo.getUseridForPrefs();
				} else {
					userId = getRequest().getRemoteUser();
				}
			}
			bean.setUserid(userId);
		}
		if(StringUtils.isBlank(bean.getPrefKey())) {
			bean.setPrefKey(getExtraparams().get("prefKey"));
		}
		try {
			AurigaService.getDaoTUserPreferences().delete(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);		
		} catch (Exception e){
			mLogger.error("Si è verificato un errore durante la delete della preference con " + getPreferenceInfo(bean), e);
			throw new StoreException("Si è verificato un errore durante la cancellazione della preference");
		}
		return bean;
	}
	
	private String getPreferenceInfo(PreferenceBean bean) {
		String preferenceInfo = "[";
		if (StringUtils.isNotBlank(bean.getUserid())) {
			preferenceInfo += " userId: " + bean.getUserid();
		}
		if (StringUtils.isNotBlank(bean.getPrefKey())) {
			preferenceInfo += " prefKey: " + bean.getPrefKey();
		}
		if (StringUtils.isNotBlank(bean.getPrefName())) {
			preferenceInfo += " prefName: " + bean.getPrefName();
		}		
		preferenceInfo += " ]";
		return preferenceInfo;
	}
	
}
