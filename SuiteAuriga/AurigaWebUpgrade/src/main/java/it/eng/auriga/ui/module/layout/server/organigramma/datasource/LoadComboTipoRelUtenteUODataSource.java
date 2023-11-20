/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Cristiano Daniele
 *
 */
@Datasource(id = "LoadComboTipoRelUtenteUODataSource")
public class LoadComboTipoRelUtenteUODataSource extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();

		SimpleKeyValueBean lSimpleKeyValueBeanA = new SimpleKeyValueBean();
		lSimpleKeyValueBeanA.setKey("A");
		lSimpleKeyValueBeanA.setValue("Appartenenza gerarchica");
		
		SimpleKeyValueBean lSimpleKeyValueBeanD = new SimpleKeyValueBean();
		lSimpleKeyValueBeanD.setKey("D");
		lSimpleKeyValueBeanD.setValue("Funzionale/delega");

		SimpleKeyValueBean lSimpleKeyValueBeanL = new SimpleKeyValueBean();
		lSimpleKeyValueBeanL.setKey("L");
		lSimpleKeyValueBeanL.setValue("Postazione ombra");
		
		lListResult.add(lSimpleKeyValueBeanA);
		lListResult.add(lSimpleKeyValueBeanD);
		lListResult.add(lSimpleKeyValueBeanL);
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;
	}

}
