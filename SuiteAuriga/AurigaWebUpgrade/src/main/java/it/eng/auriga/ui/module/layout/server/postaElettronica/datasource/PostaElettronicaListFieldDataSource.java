/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.bean.FieldBean;
import it.eng.utility.ui.module.layout.server.common.ListFieldDataSource;

import java.util.List;

@Datasource(id="PostaElettronicaListFieldDataSource")
public class PostaElettronicaListFieldDataSource extends ListFieldDataSource{

	@Override
	public PaginatorBean<FieldBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		PaginatorBean<FieldBean> lPaginatorBean = super.fetch(criteria, startRow, endRow, orderby);		
		for(int i = 0; i < lPaginatorBean.getData().size(); i++) {
			if("casellaRicezione".equals(lPaginatorBean.getData().get(i).getName())) {
				lPaginatorBean.getData().get(i).setTitle(getCasellaFilterTitle());
			}
		}		
		return lPaginatorBean;
	}
	
	private String getCasellaFilterTitle() {
		String classifica = getExtraparams().get("classifica");	
		if(classifica != null) {
			if(classifica.startsWith("standard.arrivo") || classifica.startsWith("standard.archiviata.arrivo")) {
				return "Caselle di ricezione";		
			} else if(classifica.startsWith("standard.invio") || classifica.startsWith("standard.archiviata.invio") || classifica.startsWith("standard.bozze")) {
				return "Caselle di invio";					
			}
		}	
		return "Caselle";
	}

}
