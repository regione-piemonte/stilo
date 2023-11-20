/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gwt.thirdparty.json.JSONObject;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.PreferenceBean;
import it.eng.client.AurigaService;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TOrderBy;
import it.eng.core.business.TOrderBy.OrderByType;
import it.eng.core.business.TPagingList;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean.Direction;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id="AurigaPreferenceDataSource")
public class PreferenceDataSource extends AbstractFetchDataSource<PreferenceBean>{	
	
	private static Logger mLogger = Logger.getLogger(PreferenceDataSource.class);
	
	private static ConcurrentHashMap<String, PreferenceBean> cacheUserPreference = new ConcurrentHashMap<String, PreferenceBean>();
	
	private static String getKeyFromPreferenceBean(PreferenceBean bean) {
		if(StringUtils.isNotBlank(bean.getUserid()) && StringUtils.isNotBlank(bean.getPrefKey()) && StringUtils.isNotBlank(bean.getPrefName())) {
			return "USERID|*|" + bean.getUserid() + "|*|PREF_KEY|*|" + bean.getPrefKey() + "|*|PREF_NAME|*|" + bean.getPrefName();
		}
		return null;
	}
	
	public PreferenceBean clearCacheUserPreference(PreferenceBean bean) {
		if(bean != null && StringUtils.isNotBlank(bean.getUserid()) && StringUtils.isNotBlank(bean.getPrefKey()) && StringUtils.isNotBlank(bean.getPrefName())) {
			if(cacheUserPreference.containsKey(getKeyFromPreferenceBean(bean))) {
				cacheUserPreference.remove(getKeyFromPreferenceBean(bean));
			}
		} else {
			cacheUserPreference = new ConcurrentHashMap<String, PreferenceBean>();
		}
		return bean;
	}

	@Override
	public PaginatorBean<PreferenceBean> fetch(AdvancedCriteria criteria,Integer startRow, Integer endRow, List<OrderByBean> orderByBeanList)throws Exception {
		PaginatorBean<PreferenceBean> paginatorBean = new PaginatorBean<PreferenceBean>();

		String userId = getExtraparams().get("userId");		
		if(StringUtils.isBlank(userId)) {
			it.eng.auriga.module.business.beans.AurigaLoginBean loginInfo = AurigaUserUtil.getLoginInfo(getSession());
			if(loginInfo != null && StringUtils.isNotBlank(loginInfo.getUseridForPrefs())) {
				userId = loginInfo.getUseridForPrefs();
			} else {
				userId = getRequest().getRemoteUser();
			}
		}	

		String prefKey = getExtraparams().get("prefKey");

		String prefName = getExtraparams().get("prefName");
		
		boolean escludiPrefPublic = getExtraparams().get("escludiPrefPublic") != null && "true".equalsIgnoreCase(getExtraparams().get("escludiPrefPublic"));
		
		if(criteria != null && criteria.getCriteria() != null) {
			for(Criterion criterion : criteria.getCriteria()) {
				if(criterion.getFieldName() != null) {
					if(criterion.getFieldName().equals("userId")) {
						userId = (String) criterion.getValue();
					} else if(criterion.getFieldName().equals("prefName")) {
						if(StringUtils.isBlank(prefName)) {
							prefName = (String) criterion.getValue();
						}
					}
				}
			}
		}

		PreferenceBean bean = new PreferenceBean();
		bean.setUserid(userId);
		bean.setPrefKey(prefKey);
		bean.setPrefName(prefName);
		bean.setEscludiPrefPublic(escludiPrefPublic);
		
		if(StringUtils.isNotBlank(bean.getUserid()) && StringUtils.isNotBlank(bean.getPrefKey())) {
			if(StringUtils.isNotBlank(bean.getPrefName())) {
				PreferenceBean pref = null;
				if(!bean.getPrefKey().equals("skipNewsAlert") && cacheUserPreference.containsKey(getKeyFromPreferenceBean(bean))) {
					pref = cacheUserPreference.get(getKeyFromPreferenceBean(bean));
					if(pref != null && pref instanceof NullPreferenceBean) {
						pref = null;
					}
					if (pref != null && pref.getUserid() != null && pref.getUserid().startsWith("PUBLIC.")) {
						if(bean.getEscludiPrefPublic() != null && bean.getEscludiPrefPublic()) {
							pref = null;
						}					
					}
				} else {
					pref = getUserPreference(bean, startRow, endRow, orderByBeanList);
//					pref = AurigaService.getDaoTUserPreferences().get(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);
					if (pref != null && pref.getUserid() != null && pref.getUserid().startsWith("PUBLIC.")) {
						if(bean.getEscludiPrefPublic() != null && bean.getEscludiPrefPublic()) {
							pref = null;
						}					
					}
					if(pref != null) {
						if(pref.getValue() != null && (pref.getValue().contains("{") || pref.getValue().contains("}"))) {
							try {
								new JSONObject(pref.getValue());
							} catch (Exception e) {
								mLogger.error(pref.getUserid() + " Preference: " + pref.getPrefKey() + "-" + pref.getPrefName() + " contiene json malformato");
							}
						}
						cacheUserPreference.put(getKeyFromPreferenceBean(bean), pref);
					} else {
						NullPreferenceBean nullbean = new NullPreferenceBean();
						nullbean.setUserid(bean.getUserid());
						nullbean.setPrefKey(bean.getPrefKey());
						nullbean.setPrefName(bean.getPrefName());
						cacheUserPreference.put(getKeyFromPreferenceBean(bean), nullbean);
					}
				}
				if(pref != null) {
					List<PreferenceBean> data = new ArrayList<PreferenceBean>();
					data.add(pref);
					paginatorBean.setData(data);
					paginatorBean.setStartRow(0);
					paginatorBean.setEndRow(1);
					paginatorBean.setTotalRows(1);
				} else {
					paginatorBean.setData(new ArrayList<PreferenceBean>());
					paginatorBean.setStartRow(0);
					paginatorBean.setEndRow(0);
					paginatorBean.setTotalRows(0);
				}			
				return paginatorBean;
			}
		} else {
			mLogger.error("Non è stato possibile fare la search delle preference con " + getPreferenceInfo(bean));
			paginatorBean.setData(new ArrayList<PreferenceBean>());
			paginatorBean.setStartRow(0);
			paginatorBean.setEndRow(0);
			paginatorBean.setTotalRows(0);
			return paginatorBean;
		}	
		
		List<PreferenceBean> data = null;
		try {
			data = getListaUserPreference(bean, startRow, endRow, orderByBeanList);
		} catch (Exception e) {
			mLogger.error(e.getMessage(), e);
		}
		
		if (data == null) {
			paginatorBean.setStartRow(0);
			paginatorBean.setEndRow(0);
			paginatorBean.setTotalRows(0);
		} else {
			Collections.sort(data, new Comparator<PreferenceBean>() {

				@Override
				public int compare(PreferenceBean pref1, PreferenceBean pref2) {
					if(pref1.getPrefName().equalsIgnoreCase("DEFAULT")) {
						return Integer.MIN_VALUE;
					} else if(pref2.getPrefName().equalsIgnoreCase("DEFAULT")) {
						return Integer.MAX_VALUE;
					}
					return pref1.getPrefName().toLowerCase().compareTo(pref2.getPrefName().toLowerCase());
				}
			});
			
			paginatorBean.setData(data);
			paginatorBean.setStartRow(0);
			paginatorBean.setEndRow(data.size());
			paginatorBean.setTotalRows(data.size());
		}

		return paginatorBean;
	}
	
	public PreferenceBean getUserPreference(PreferenceBean bean, Integer startRow, Integer endRow,
			List<OrderByBean> orderByBeanList) throws Exception {
		if(StringUtils.isNotBlank(bean.getUserid()) && StringUtils.isNotBlank(bean.getPrefKey()) && StringUtils.isNotBlank(bean.getPrefName())) {				
			List<PreferenceBean> lista = getListaUserPreference(bean, startRow, endRow, orderByBeanList); 
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
		
		boolean includeValues = getExtraparams().get("includeValues") != null && "true".equalsIgnoreCase(getExtraparams().get("includeValues"));		
		
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
		if(includeValues) {
			altriParametri += "|*|INCLUDE_VALUES|*|1";
		}
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
				if (prefBean.getUserid() != null && prefBean.getUserid().startsWith("PUBLIC.")) {
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
	
	@Deprecated
	public List<PreferenceBean> getListaUserPreferenceWithDao(PreferenceBean bean, Integer startRow, Integer endRow,
			List<OrderByBean> orderByBeanList) throws Exception {

		TFilterFetch<PreferenceBean> filter = new TFilterFetch<PreferenceBean>();
		filter.setStartRow(startRow);
		filter.setEndRow(endRow);
		//Setto gli ordinamenti
		List<TOrderBy> orderByList = new ArrayList<TOrderBy>();		
		if(orderByBeanList != null) {		
			for(OrderByBean orderByBean : orderByBeanList){			
				TOrderBy orderBy = new TOrderBy();
				orderBy.setPropname(orderByBean.getColumnname());
				if(orderByBean.getDirection()==Direction.ASC){
					orderBy.setType(OrderByType.ASCENDING);
				}else{
					orderBy.setType(OrderByType.DESCENDING);
				}			
				orderByList.add(orderBy);				
			}
		} 
		filter.setFilter(bean);
		filter.setOrders(orderByList);		

		TPagingList<PreferenceBean> pagingList = AurigaService.getDaoTUserPreferences().search(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), filter);
		
//		if (prefKey.contains(".filter") && pagingList.getData()!=null && pagingList.getData().size()>0){
//			
//			for(int i = 0; i < pagingList.getData().size(); i++) {
//				
//				GsonBuilder builder =  GsonBuilderFactory.getIstance();
//				Gson gson = builder.create();
//				
//				String jsonmap = pagingList.getData().get(i).getValue();
//				
//				AdvancedCriteria lAdvancedCriteria = null;
//				String lStringValue = jsonmap; 
//				try {
//					lAdvancedCriteria = gson.fromJson(jsonmap, AdvancedCriteria.class);
//				} catch (Exception e){				
//					int index = -1;
//					StringBuffer lStringBuffer = new StringBuffer();
//					while ((index = jsonmap.indexOf("\"value\":")) != -1){
//						if (jsonmap.charAt(index+8)=='"' || jsonmap.charAt(index+8)=='{' || jsonmap.charAt(index+8)=='['){
//							lStringBuffer.append(jsonmap.substring(0, index+8));
//							jsonmap = jsonmap.substring(index+8);
//						} else {
//							lStringBuffer.append(jsonmap.substring(0, index+8));
//							int start = index+8;
//							if(jsonmap.substring(start).startsWith("null")) {
//								lStringBuffer.append("null");
//								start += 4;
//							} else if(jsonmap.substring(start).startsWith("true")) {
//								lStringBuffer.append("true");
//								start += 4;
//							} else if(jsonmap.substring(start).startsWith("false")) {
//								lStringBuffer.append("false");
//								start += 5;
//							} else {
//								lStringBuffer.append("\"");
//								while (jsonmap.charAt(start)!=')'){
//									lStringBuffer.append(jsonmap.charAt(start));
//									start++;
//								}
//								lStringBuffer.append(jsonmap.charAt(start));
//								lStringBuffer.append("\"");
//							}							
//							jsonmap = jsonmap.substring(start+1);
//						} 
//					}
//					lStringBuffer.append(jsonmap);
//					
//					jsonmap = lStringBuffer.toString();
//					lAdvancedCriteria = gson.fromJson(jsonmap, AdvancedCriteria.class);
//				}
//				
//				FilterConfigurator lFilterConfigurator = (FilterConfigurator)SpringAppContext.getContext().getBean("FilterConfigurator");
//				Map<String,FilterBean> liste = lFilterConfigurator.getListe();	
//				for (Criterion lCriterion : lAdvancedCriteria.getCriteria()){
//					boolean foundcheck = false;
//					String fieldName = null;
//					for (String lString : liste.keySet()){
//						FilterBean lFilterBean = liste.get(lString);
//						List<FilterFieldBean> fields = lFilterBean.getFields();
//						for (FilterFieldBean lFieldBean : fields){
//							if (lFieldBean.getName().equals(lCriterion.getFieldName()) && lFieldBean.getType()==FilterType.check &&
//									lCriterion.getValue()==Boolean.FALSE){
//								foundcheck = true; fieldName = lCriterion.getFieldName();
//							}
//						}
//					}
//					if (foundcheck){
//						lStringValue = lStringValue.replaceAll("\"fieldName\":\"" + fieldName + "\",             \"operator\":\"equals\",             \"value\":false", 
//								"\"fieldName\":\"" + fieldName + "\",             \"operator\":\"equals\",             \"value\":\"specialFalse\"");
//					} 
//				};	
//	
//				pagingList.getData().get(i).setValue(lStringValue);				
//			}
//		}

		return pagingList.getData();
	}
	
	@Override
	public PreferenceBean get(PreferenceBean bean) throws Exception {
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
		if(StringUtils.isNotBlank(bean.getUserid()) && StringUtils.isNotBlank(bean.getPrefKey()) && StringUtils.isNotBlank(bean.getPrefName())) {
			PreferenceBean pref = null;
			if(!bean.getPrefKey().equals("skipNewsAlert") && cacheUserPreference.containsKey(getKeyFromPreferenceBean(bean))) {
				pref = cacheUserPreference.get(getKeyFromPreferenceBean(bean));
				if(pref != null && pref instanceof NullPreferenceBean) {
					pref = null;
				}
			} else {
				pref = AurigaService.getDaoTUserPreferences().get(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);
				if(pref != null) {
					if(pref.getValue() != null && (pref.getValue().contains("{") || pref.getValue().contains("}"))) {
						try {
							new JSONObject(pref.getValue());
						} catch (Exception e) {
							mLogger.error(pref.getUserid() + " Preference: " + pref.getPrefKey() + "-" + pref.getPrefName() + " contiene json malformato");
						}
					}
					cacheUserPreference.put(getKeyFromPreferenceBean(bean), pref);
				} else {
					NullPreferenceBean nullbean = new NullPreferenceBean();
					nullbean.setUserid(bean.getUserid());
					nullbean.setPrefKey(bean.getPrefKey());
					nullbean.setPrefName(bean.getPrefName());
					cacheUserPreference.put(getKeyFromPreferenceBean(bean), nullbean);
				}
			}
			return pref;
		} else {
			mLogger.error("Non è stato possibile fare la get della preference con " + getPreferenceInfo(bean));
		}		
		return null;
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
		if(StringUtils.isBlank(bean.getPrefName()) && StringUtils.isNotBlank(bean.getKey())) {
			if(bean.getKey().contains("|*|")) {
				bean.setUserid(bean.getKey().substring(0, bean.getKey().indexOf("|*|")));
				bean.setPrefName(bean.getKey().substring(bean.getKey().indexOf("|*|") + 3));
			} else {
				bean.setPrefName(bean.getKey());
			}
		}
		try {
			AurigaService.getDaoTUserPreferences().save(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);
			cacheUserPreference.put(getKeyFromPreferenceBean(bean), bean);
		} catch (Exception e){
			mLogger.error("Si è verificato un errore durante la save della preference con " + getPreferenceInfo(bean), e);
		}
		if (bean.getUserid() != null && bean.getUserid().startsWith("PUBLIC.")) {
			bean.setKey(bean.getUserid() + "|*|" + bean.getPrefName());
			bean.setDisplayValue(bean.getPrefName() + "&nbsp;<img src=\"images/public.png\" height=\"12\" width=\"12\" align=MIDDLE/>");
		} else {
			bean.setKey(bean.getPrefName());
			bean.setDisplayValue(bean.getPrefName());
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
		if(StringUtils.isBlank(bean.getPrefName()) && StringUtils.isNotBlank(bean.getKey())) {
			if(bean.getKey().contains("|*|")) {
				bean.setUserid(bean.getKey().substring(0, bean.getKey().indexOf("|*|")));
				bean.setPrefName(bean.getKey().substring(bean.getKey().indexOf("|*|") + 3));
			} else {
				bean.setPrefName(bean.getKey());
			}
		}
		try {
			AurigaService.getDaoTUserPreferences().update(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);
			cacheUserPreference.put(getKeyFromPreferenceBean(bean), bean);
		} catch (Exception e){
			mLogger.error("Si è verificato un errore durante l'update della preference con " + getPreferenceInfo(bean), e);
		}
		if (bean.getUserid() != null && bean.getUserid().startsWith("PUBLIC.")) {
			bean.setKey(bean.getUserid() + "|*|" + bean.getPrefName());
			bean.setDisplayValue(bean.getPrefName() + "&nbsp;<img src=\"images/public.png\" height=\"12\" width=\"12\" align=MIDDLE/>");
		} else {
			bean.setKey(bean.getPrefName());
			bean.setDisplayValue(bean.getPrefName());
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
		if(StringUtils.isBlank(bean.getPrefName()) && StringUtils.isNotBlank(bean.getKey())) {
			if(bean.getKey().contains("|*|")) {
				bean.setUserid(bean.getKey().substring(0, bean.getKey().indexOf("|*|")));
				bean.setPrefName(bean.getKey().substring(bean.getKey().indexOf("|*|") + 3));
			} else {
				bean.setPrefName(bean.getKey());
			}
		}
		try {					
			AurigaService.getDaoTUserPreferences().delete(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);
			cacheUserPreference.remove(getKeyFromPreferenceBean(bean));
		} catch (Exception e){
			mLogger.error("Si è verificato un errore durante la delete della preference con " + getPreferenceInfo(bean), e);
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
	
	/*
	 * Bean per rappresentare le preference già lette da DB ma vuote in cacheUserPreference 
	 */
	public class NullPreferenceBean extends PreferenceBean {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}

}
