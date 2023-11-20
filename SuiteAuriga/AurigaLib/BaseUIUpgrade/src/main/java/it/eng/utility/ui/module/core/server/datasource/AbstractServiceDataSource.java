/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.service.ErrorBean;

import java.util.List;

/**
 * Classe astratta che definisce i metodi da implementare per un datasource di tipo call
 * @author michele
 *
 * @param <T>
 */
public abstract class AbstractServiceDataSource<IN,OUT> extends AbstractDataSource<IN,OUT> {

	public abstract OUT call(IN pInBean) throws Exception;
	
	public OUT get(IN pInBean) throws Exception {
		return null;
	};

	public java.util.Map<String,ErrorBean> validate(IN pInBean) throws Exception {
		return null;
	}
	
	public OUT update(IN pInBean, IN oldvalue) throws Exception {
		return add(pInBean);
	};
	
	@Override
	public OUT add(IN pInBean) throws Exception {
		return call(pInBean);
	}

	@Override
	public OUT remove(IN pInBean) throws Exception {
		
		return null;
	}
		
	@Override
	public PaginatorBean<OUT> fetch(AdvancedCriteria criteria,Integer startRow,Integer endRow,List<OrderByBean> orderby) throws Exception {
		
//		System.out.println(criteria);
		return null;
	}
}