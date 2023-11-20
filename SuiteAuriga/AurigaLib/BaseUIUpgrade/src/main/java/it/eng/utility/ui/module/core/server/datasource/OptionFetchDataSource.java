/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterSelectBean;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.ItemFilterBean;
import it.eng.utility.ui.module.layout.shared.bean.ItemFilterType;

public abstract class OptionFetchDataSource<T extends VisualBean> extends AbstractFetchDataSource<T> {

	@Override
	public abstract PaginatorBean<T> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception;

	public PaginatorBean<T> retrieveValuesFromFetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		PaginatorBean<T> lPaginatorBean = fetch(criteria, startRow, endRow, orderby);
		List<T> lList = lPaginatorBean.getData();
		List<T> lListToShow = new ArrayList<T>(lList.size());
		List<ItemFilterBean> fields = null;
		String datasourceName = getExtraparams().get("datasourcename");
		String[] lStrings = SpringAppContext.getContext().getBeanNamesForType(FilterSelectBean.class);
		GenericConfigBean lGenericConfigBean = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		String displayValueSeparator = lGenericConfigBean.getDisplayValueSeparator();
		String displayValueNull = lGenericConfigBean.getDisplayValueNull();
		for (String lStrId : lStrings) {
			FilterSelectBean lFilterSelectBean = (FilterSelectBean) SpringAppContext.getContext().getBean(lStrId);
			if (lFilterSelectBean.getDatasourceName() != null && lFilterSelectBean.getDatasourceName().equals(datasourceName)) {
				fields = lFilterSelectBean.getLayout().getFields();
				break;
			}
		}

		if (fields == null)
			return lPaginatorBean;
		if (lPaginatorBean.getData().size() == 0)
			return lPaginatorBean;

		BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper();		
		for (T bean : lList) {
			if (bean != null) {
				wrapperBean = BeanPropertyUtility.updateBeanWrapper(wrapperBean, bean);
				String keyFieldName = getExtraparams().get(KEY_FIELD_NAME);
				if (StringUtils.isEmpty(bean.getDisplayValue())) {
					Object propertyValue = BeanPropertyUtility.getPropertyValue(bean, wrapperBean, keyFieldName);
					// if (BeanUtilsBean2.getInstance().getProperty(bean, keyFieldName) == null
					if (propertyValue == null || propertyValue.equals(null)) {
						bean.setDisplayValue("");
					} else {
						String result = retrieveDisplayValue(fields, displayValueSeparator, displayValueNull, bean, wrapperBean);
						bean.setDisplayValue(result.substring(0, result.length() - 2));
					}
				}
				Map<String, String> lMap = BeanUtilsBean2.getInstance().describe(bean);
				for (ItemFilterBean lItemFilterBean : fields) {
					if (!lItemFilterBean.getName().equals(keyFieldName)) {
						if (lMap.get(lItemFilterBean.getName()) != null) {
							String property = lItemFilterBean.getName();
							if (lItemFilterBean.getType().equals(ItemFilterType.BOOLEAN)) {
								boolean result = (Boolean) BeanPropertyUtility.getPropertyValue(bean, wrapperBean, property);
								// boolean result = (Boolean) BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(bean, property);
								if (result) {
									String icon = (lItemFilterBean.getIcon() != null && !"".equals(lItemFilterBean.getIcon())) ? lItemFilterBean.getIcon() : lGenericConfigBean.getCheckIcon();
									String iconValue = "<img src=\"" + icon + "\" height=\"12\" width=\"12\" align=MIDDLE/>";
									BeanPropertyUtility.setPropertyValue(bean, wrapperBean, property + "_realValue", iconValue);
									// BeanUtilsBean2.getInstance().setProperty(bean, property + "_realValue", iconValue);
								}
							}
							// else if (lItemFilterBean.getType().equals(ItemFilterType.ICON)) {
							// String value = (String) BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(bean, property);
							// if (value != null && !"".equals(value)) {
							// String prefix = lItemFilterBean.getPrefix() != null ? lItemFilterBean.getPrefix() : "";
							// String suffix = lItemFilterBean.getSuffix() != null ? lItemFilterBean.getSuffix() : "";
							// String iconValue = "<img src=\"" + prefix + value + suffix + "\" height=\"12\" width=\"12\" align=MIDDLE/>";
							// BeanUtilsBean2.getInstance().setProperty(bean, property + "_realValue", iconValue);
							// }
							// }
							else {
								 Object lObject = BeanPropertyUtility.getPropertyValue(bean, wrapperBean, property);
								// Object lObject = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(bean, property);
								if (lObject instanceof String) {
									// String result = (String) BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(bean, property);
									String result = (String) lObject;
									if (result != null && result.length() > lGenericConfigBean.getMaxValueLength()) {
										String formattedValue = result.substring(0, lGenericConfigBean.getMaxValueLength()) + "...";
										BeanPropertyUtility.setPropertyValue(bean, wrapperBean, property, formattedValue);
										// BeanUtilsBean2.getInstance().setProperty(bean, property, formattedValue);
										bean.addHoverValue(property, result);
									}
								}
							}
						}
					}
				}
			}
			lListToShow.add(bean);
		}
		lPaginatorBean.setData(lListToShow);
		return lPaginatorBean;
	}

	protected String retrieveDisplayValue(List<ItemFilterBean> fields, String displayValueSeparator, String displayValueNull, T bean, BeanWrapperImpl wrapperBean)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		GenericConfigBean lGenericConfigBean = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		StringBuffer lStringBuffer = new StringBuffer();
		for (ItemFilterBean field : fields) {

			if (!field.isValue()) {
				if (field.getType().equals(ItemFilterType.BOOLEAN)) {
					// TODEL Controllare questa conversione
					Boolean value = Boolean.valueOf(BeanPropertyUtility.getPropertyValueAsString(bean, wrapperBean, field.getName()));
					// Boolean value = Boolean.valueOf(BeanUtilsBean2.getInstance().getProperty(bean, field.getName()));
					String icon = StringUtils.isNotBlank(field.getIcon()) ? field.getIcon() : lGenericConfigBean.getCheckIcon();
					if (value) {
						lStringBuffer.append("<img src=\"" + icon + "\" height=\"12\" width=\"12\" align=MIDDLE/> ");
					}
				} else if (field.getType().equals(ItemFilterType.ICON)) {
					// TODEL Controllare questa conversione
					String value = BeanPropertyUtility.getPropertyValueAsString(bean, wrapperBean, field.getName());
					// String value = (String) BeanUtilsBean2.getInstance().getProperty(bean, field.getName());
					if (value != null && !"".equals(value)) {
						String prefix = field.getPrefix() != null ? field.getPrefix() : "";
						String suffix = field.getSuffix() != null ? field.getSuffix() : "";
						String iconValue = "<img src=\"" + prefix + value + suffix + "\" height=\"12\" width=\"12\" align=MIDDLE/>";
						lStringBuffer.append(iconValue);
					}
				} else {
					try {
						String propertyValue = BeanPropertyUtility.getPropertyValueAsString(bean, wrapperBean, field.getName());
						// if (BeanUtilsBean2.getInstance().getProperty(bean, field.getName()) != null) {
						if (propertyValue != null) {
							if (field.getType() == ItemFilterType.DATE) {
								SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
								Method theMethod = bean.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), new Class[] {});
								Date lDate = (Date) theMethod.invoke(bean, new Object[] {});

								lStringBuffer.append(lSimpleDateFormat.format(lDate) + " ");
							} else
								// TODEL Controllare questa concatenazione
								lStringBuffer.append(propertyValue + " ");
						} else
							lStringBuffer.append(displayValueNull + " ");
					} catch (Exception e) {
						lStringBuffer.append(displayValueNull + " ");
					}
				}
				lStringBuffer.append(displayValueSeparator + " ");
			}

		}
		String result = lStringBuffer.toString().trim();
		return result;
	}

	protected String getIdValoreUnico() {
		return getExtraparams().get("idValoreUnico") != null ? getExtraparams().get("idValoreUnico") : "";		
	}

}
