/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

public class EntityUtils {
	
	public static final <T> String getEntityName(Class<T> entityClass) {
		String entityname = entityClass.getSimpleName();

		Entity entity = entityClass.getAnnotation(Entity.class);
		if (entity != null && entity.name() != null && !"".equals(entity.name())) {
			entityname = entity.name();
		}
		Table table = entityClass.getAnnotation(Table.class);
		if (table != null && table.name() != null && !"".equals(table.name())) {
			entityname = table.name();
		}

		return entityname;
	}
	
	public static final <T> String getEntityFieldName(Class<T> entityClass, String methodBaseName) throws SecurityException, NoSuchMethodException {
		Column column = entityClass.getMethod("get" + methodBaseName).getAnnotation(Column.class);
		return column.name();
	}

}
