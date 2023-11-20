/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;
import it.eng.utility.ui.module.layout.shared.bean.Navigator;

import java.util.List;

/**
 * Classe astratta che definisce i metodi da implementare per un datasource usabile da un TreeGrid
 * @author michele
 *
 * @param <T>
 */
public abstract class AbstractTreeDataSource<T extends TreeNodeBean> extends AbstractFetchDataSource<T> {
	
	public T get(T bean) throws Exception{
		return null;
	}
	
	public T update(T bean,T oldvalue) throws Exception{
		return null;
	}

	public java.util.Map<String,ErrorBean> validate(T bean) throws Exception{
		return null;
	}
	
	@Override
	public T add(T bean) throws Exception {
		return null;
	}

	@Override
	public T remove(T bean) throws Exception {
		
		return null;
	}
		
	@Override
	public abstract PaginatorBean<T> fetch(AdvancedCriteria criteria,Integer startRow,Integer endRow,List<OrderByBean> orderby) throws Exception;
			
		
	public String getNomeEntita() {
		return null;
	};	
	
}