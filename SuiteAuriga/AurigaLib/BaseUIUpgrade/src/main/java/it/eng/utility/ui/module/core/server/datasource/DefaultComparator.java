/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanWrapperImpl;

import it.eng.utility.springBeanWrapper.BeanPropertyUtility;


public class DefaultComparator<T> {

	public ComparatorResultBean compare(ComparatorBean<T> pComparatorBean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Object lObjectOld = pComparatorBean.getOldBean();
		Object lObjectNew = pComparatorBean.getNewBean();
		boolean analyzeResult = analyze(lObjectOld, lObjectNew, pComparatorBean.getClassType());
		ComparatorResultBean lComparatorResultBean = new ComparatorResultBean();
		lComparatorResultBean.setHasChanges(analyzeResult);
		return lComparatorResultBean;
	}

	private boolean analyze(Object lObjectOld, Object lObjectNew, Class classType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Field[] lFields = retrieveFields(classType);
		BeanWrapperImpl wrapperObjectOld = BeanPropertyUtility.getBeanWrapper(lObjectOld);
		BeanWrapperImpl wrapperlObjectNew = BeanPropertyUtility.getBeanWrapper(lObjectNew);
		for (Field lField : lFields){
			Object lObjectValueOld = BeanPropertyUtility.getPropertyValue(lObjectOld, wrapperObjectOld, lField.getName());
			// Object lObjectValueOld = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(lObjectOld, lField.getName());
			Object lObjectValueNew = BeanPropertyUtility.getPropertyValue(lObjectNew, wrapperlObjectNew, lField.getName());
			// Object lObjectValueNew = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(lObjectNew, lField.getName());
			if ((lObjectValueOld == null && lObjectValueNew != null) || (lObjectValueOld == null && lObjectValueNew != null)){
//				System.out.println(lField.getName() + " nullo solo per uno dei due");
				return true;
			}
			if (lObjectValueOld == null && lObjectValueNew == null){

			} else {
				//Se è un bigDecimal confrontiamo la classe
				if (BigDecimal.class.isAssignableFrom(lField.getType())){
					BigDecimal lBigDecimalOld = (BigDecimal)lObjectValueOld;
					BigDecimal lBigDecimalNew = (BigDecimal)lObjectValueNew;
					if (lBigDecimalOld.longValue()!=lBigDecimalNew.longValue()){
//						System.out.println(lField.getName() + " bigdecimal longValue diverso");
						return true;
					}
				} else if (lField.getType().isInterface()){
					ParameterizedType genericListType = (ParameterizedType) lField.getGenericType();
					Class<?> genericListClass = (Class<?>) genericListType.getActualTypeArguments()[0];
					int sizeOld = ((List)lObjectValueOld).size();
					int sizeNew = ((List)lObjectValueNew).size();
					if (sizeOld != sizeNew){
//						System.out.println(lField.getName() + " lista di dimensioni diverse");
						return true;
					} else {
						for (int i = 0; i< sizeOld; i++){
							boolean subAnalyze = analyze(((List)lObjectValueOld).get(i), ((List)lObjectValueNew).get(i), genericListClass);
							if (subAnalyze){
								return true;
							}
						}
					}
				} else if (java.util.Date.class.isAssignableFrom(lField.getType())){
					Date lDateOld = (Date)lObjectValueOld;
					Date lDateNew = (Date)lObjectValueNew;
					String lStrPattern = "dd/MM/yyyy";
					SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lStrPattern);
					if (!lSimpleDateFormat.format(lDateOld).equals(lSimpleDateFormat.format(lDateNew))){
						return true;
					}
				} else if (!lObjectValueOld.equals(lObjectValueNew)){
//						System.out.println(lField.getName() + " non coincidono");
						return true;
				}
			}
		}
		return false;
	}

	protected Field[] retrieveFields(Class lClass){
		Field[] lFieldsLista = lClass.getDeclaredFields();;
		if (lClass.getSuperclass()!=null && lClass.getSuperclass()!=java.lang.Object.class){
			Field[] inherited = lClass.getSuperclass().getDeclaredFields();
			Field[] original = lFieldsLista;
			lFieldsLista = new Field[inherited.length + original.length];
			int k = 0;
			for (Field lFieldInherited : inherited){
				lFieldsLista[k] = lFieldInherited;
				k++;
			}
			for (Field lFieldOriginal : original){
				lFieldsLista[k] = lFieldOriginal;
				k++;
			}
		}
		return lFieldsLista;
	}
}
