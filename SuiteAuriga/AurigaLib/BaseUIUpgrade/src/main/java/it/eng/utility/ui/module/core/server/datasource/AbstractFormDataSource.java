/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.service.ErrorBean;

import java.util.List;

/**
 * Classe astratta che definisce i metodi da implementare per un datasource da usabile da un DynamicForm
 * @author michele
 *
 * @param <T>
 */
public abstract class AbstractFormDataSource<T> extends AbstractDataSource<T,T> {

	public abstract T update(T bean,T oldvalue) throws Exception;

	public abstract java.util.Map<String,ErrorBean> validate(T bean) throws Exception;
	
	@Override
	public T get(T bean) throws Exception {
		return null;
	};
	
	@Override
	public T add(T bean) throws Exception {
		return update(bean,bean);
	}

	@Override
	public T remove(T bean) throws Exception {
		
		return null;
	}
		
	@Override
	public PaginatorBean<T> fetch(AdvancedCriteria criteria,Integer startRow,Integer endRow,List<OrderByBean> orderby) throws Exception {
		
//		System.out.println(criteria);
		return null;
	}
}