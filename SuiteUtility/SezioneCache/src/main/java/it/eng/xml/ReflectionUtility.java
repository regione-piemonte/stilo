package it.eng.xml;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public final class ReflectionUtility {
	/**
	 * <p>Recupera tutti i Fields di una classe compresi quelli ereditati dalle superclassi.
	 * <p>Eventuali campi <b>con lo stesso nome</b>, provenienti dalle superclassi, verranno scartati
	 * 
	 * @param lObjectToSerialize
	 * @return
	 * @throws SecurityException
	 * 
	 * @author denis.bragato
	 */
	public static final Field[] getFields(Object lObjectToSerialize) throws SecurityException {
		Set<String> allFieldsName = new HashSet<String>();
		Set<Field> allFields = new HashSet<Field>();
		Class<? extends Object> classToParse;
		if (lObjectToSerialize instanceof Class) {
			classToParse = (Class<? extends Object>) lObjectToSerialize;
		} else {
			classToParse = lObjectToSerialize.getClass();
		}
		while (classToParse != null && classToParse != java.lang.Object.class) {
			Field[] lFieldsToSerialize = classToParse.getDeclaredFields();
			for (int i = 0; i < lFieldsToSerialize.length; i++) {
				Field field = lFieldsToSerialize[i];
				if (allFieldsName.add(field.getName())) allFields.add(field);
			}
			
			classToParse = classToParse.getSuperclass();
		}
		Field[] lFieldsToSerialize = allFields.toArray(new Field[]{});
		return lFieldsToSerialize;
	}

}
