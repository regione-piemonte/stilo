/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.PreferenceBean;
import it.eng.client.AurigaService;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.util.ArrayList;
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
		
//		if(StringUtils.isNotBlank(bean.getUserid()) && StringUtils.isNotBlank(bean.getPrefName()) && StringUtils.isNotBlank(bean.getPrefKey())) {
			List<PreferenceBean> data = new ArrayList<PreferenceBean>();
			PreferenceBean pref = AurigaService.getDaoTUserPreferences().get(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);			
			if(pref != null) {
				data.add(pref);
				paginatorBean.setData(data);
				paginatorBean.setStartRow(0);
				paginatorBean.setEndRow(1);
				paginatorBean.setTotalRows(1);
			} else {
				paginatorBean.setData(data);
				paginatorBean.setStartRow(0);
				paginatorBean.setEndRow(0);
				paginatorBean.setTotalRows(0);
			}			
			return paginatorBean;
//		}
		
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
		if(bean.getSettingTime() == null) {
			bean.setSettingTime(new Date());
		}
		AurigaService.getDaoTUserPreferences().save(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);		
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
		if(bean.getSettingTime() == null) {
			bean.setSettingTime(new Date());
		}
		AurigaService.getDaoTUserPreferences().update(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);		
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
			mLogger.warn(e);
		}
		return bean;
	}
	
}
