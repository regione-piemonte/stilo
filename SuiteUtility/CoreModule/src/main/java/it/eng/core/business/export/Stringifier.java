package it.eng.core.business.export;

import java.util.Calendar;
import java.util.Collection;

import org.apache.commons.beanutils.BeanUtilsBean2;

import it.eng.core.business.HibernateUtil;

//
public class Stringifier {

	public static String stringify(Object value) throws Exception {
		String ret = null;
		if (value == null) {
			ret = "";
		} else if (Calendar.class.isAssignableFrom(value.getClass())) {
			ret = "" + ((Calendar) value).getTimeInMillis();
		} else if ((HibernateUtil.getListEntityPackage() != null && !HibernateUtil.getListEntityPackage().isEmpty()
				&& HibernateUtil.getListEntityPackage().contains(value.getClass().getPackage().getName()))
				|| value.getClass().getPackage().getName().equals(HibernateUtil.getEntitypackage())) {
			// TODO non funziona con le chiavi composte
			String primaryKey = HibernateUtil.getPrimaryKey(value.getClass());
			Object valueKey = BeanUtilsBean2.getInstance().getProperty(value, primaryKey);
			if (valueKey != null && valueKey.getClass().equals(String.class)) {
				ret = valueKey.toString();
			} else {
				// TODO chiave composta
			}
		} else if (value instanceof Collection) {
			;// ignore relation
		} else if (value.getClass().equals(String.class)) {
			ret = value.toString();
		} else if (value.getClass().isAssignableFrom(Number.class)) {
			ret = value.toString();
		}

		return ret;
	}
}
