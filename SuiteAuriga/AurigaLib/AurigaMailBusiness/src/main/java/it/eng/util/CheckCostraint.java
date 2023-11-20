/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.aurigamailbusiness.annotation.Obbligatorio;
import it.eng.aurigamailbusiness.exception.ConstraintException;

public class CheckCostraint {

	private CheckCostraint() {
		throw new IllegalStateException("Classe di utilità");
	}

	private static final Logger log = Logger.getLogger(CheckCostraint.class);

	public static void check(Object lObject) throws ConstraintException {
		if (lObject == null)
			return;
		Field[] lFields = lObject.getClass().getDeclaredFields();
		for (Field lField : lFields) {
			Obbligatorio lObbligatorio = lField.getAnnotation(Obbligatorio.class);
			if (lObbligatorio != null) {
				try {
					if (BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(lObject, lField.getName()) == null) {
						throw new ConstraintException(lField.getName(), lObject.getClass());
					} else {
						if (lField.getType().equals(java.lang.String.class)) {
							String lString = BeanUtilsBean2.getInstance().getProperty(lObject, lField.getName());
							if (StringUtils.isEmpty(lString)) {
								throw new ConstraintException(lField.getName(), lObject.getClass());
							}
						}
						if (lField.getType().isInterface() && lField.getType().getInterfaces()[0].equals(java.util.Collection.class)) {
							List<Object> lList = (List<Object>) BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(lObject, lField.getName());
							if (lList.isEmpty()) {
								throw new ConstraintException(lField.getName(), lObject.getClass());
							}
						}
					}
				} catch (IllegalAccessException e) {
					log.error("IllegalAccessException", e);
				} catch (InvocationTargetException e) {
					log.error("InvocationTargetException", e);
				} catch (NoSuchMethodException e) {
					log.error("NoSuchMethodException", e);
				}
			}
		}
	}
}
