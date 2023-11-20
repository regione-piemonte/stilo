/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDAO<T, ID extends Serializable> {

	T get(Class<T> cl, ID id);

	T load(Class<T> cl, ID id);

	ID save(T object);

	void update(T object);

	T merge(T object);

	void delete(T object);

	List<T> query(String hsql, Map<String, Object> params);

	Integer modify(String hsql, Map<String, Object> params);

	Boolean exists(Class<T> cl, ID id);

}
