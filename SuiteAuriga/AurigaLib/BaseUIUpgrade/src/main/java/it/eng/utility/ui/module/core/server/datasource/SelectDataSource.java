/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;

public abstract class SelectDataSource<T extends VisualBean> extends AbstractFetchDataSource<T>{

	public abstract PaginatorBean<T> realFetch(AdvancedCriteria criteria,Integer startRow,Integer endRow,List<OrderByBean> orderby) throws Exception;

	boolean showAll = false;
	String[] fieldsToShow;
	@Override
	public PaginatorBean<T> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		PaginatorBean<T> lPaginatorBean = realFetch(criteria, startRow, endRow, orderby);
		List<T> lListToShow = new ArrayList<T>(lPaginatorBean.getData().size());
		GenericConfigBean lGenericConfigBean = (GenericConfigBean)SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		String displayValueSeparator = lGenericConfigBean.getDisplayValueSeparator();
		String displayValueNull = lGenericConfigBean.getDisplayValueNull();
		String lStringShowAll = getExtraparams().get("showAll");
		if (lStringShowAll!=null && lStringShowAll.equals("true")){
			showAll = true;
		} else {
			String lString = getExtraparams().get("fieldToShow");
			fieldsToShow = lString.split(";");
		}
		
		BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper();		
		for (T bean : lPaginatorBean.getData()){
			if (bean!=null) {
				String lStrId = getExtraparams().get(KEY_FIELD_NAME);
				wrapperBean = BeanPropertyUtility.updateBeanWrapper(wrapperBean, bean);
				Object value = BeanPropertyUtility.getPropertyValue(bean, wrapperBean, lStrId);
				// if (BeanUtilsBean2.getInstance().getProperty(bean, lStrId)==null ||	BeanUtilsBean2.getInstance().getProperty(bean, lStrId).equals(null)){
				if (value==null || value.equals(null)){
					bean.setDisplayValue("");
				} else {
					String result = retrieveDisplayValue(displayValueSeparator, displayValueNull, bean, wrapperBean, lGenericConfigBean);
					bean.setDisplayValue(result.substring(0, result.length()-2));
				}
				
//				if (BeanUtilsBean2.getInstance().getProperty(bean, lStrId)==null ||
//						BeanUtilsBean2.getInstance().getProperty(bean, lStrId).equals(null)){
//					bean.setDisplayValue("");
//				} else {
//					String result = retrieveDisplayValue(displayValueSeparator, displayValueNull, bean);
//					bean.setDisplayValue(result.substring(0, result.length()-2));
//				}
			}
			lListToShow.add(bean);
		}
		lPaginatorBean.setData(lListToShow);
		return lPaginatorBean;
	}

	protected String retrieveDisplayValue(String displayValueSeparator, String displayValueNull, T bean, BeanWrapperImpl wrapperBean, GenericConfigBean lGenericConfigBean)
	throws IllegalAccessException, InvocationTargetException,
	NoSuchMethodException {
		StringBuffer lStringBuffer = new StringBuffer();
		Map<String, String> lMap = BeanUtilsBean2.getInstance().describe(bean);
		if (showAll) {
			Set<String> lSetProps = lMap.keySet();
			lSetProps.remove("class");
			fieldsToShow = lSetProps.toArray(new String[]{});
		}
		for (String lString : fieldsToShow){
			
			Object lObject =BeanPropertyUtility.getPropertyValue(bean, wrapperBean, lString);
			// Object lObject = BeanUtilsBean2.getInstance().getProperty(bean, lString);
			
			if (lObject == null) {
				lStringBuffer.append(displayValueNull + " ");
			} else if (lObject instanceof Boolean){
				Boolean value = (Boolean) lObject;
				// Boolean value = Boolean.valueOf(BeanUtilsBean2.getInstance().getProperty(bean, lString));
				String icon = lGenericConfigBean.getCheckIcon();
				if (value) lStringBuffer.append("<img src=\"" + icon + "\" height=\"12\" width=\"12\" align=MIDDLE/> ");
			} else {
				try{
					if (lObject instanceof Date){
						SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
						Date lDate = (Date)lObject;

						lStringBuffer.append(lSimpleDateFormat.format(lDate) + " ");
					} else 
						lStringBuffer.append(lObject + " ");
				} catch (Exception e){
					lStringBuffer.append(displayValueNull + " ");
				}	
			} 
			lStringBuffer.append(displayValueSeparator + " ");
		}
		String result = lStringBuffer.toString().trim();
		return result;
	}
}
