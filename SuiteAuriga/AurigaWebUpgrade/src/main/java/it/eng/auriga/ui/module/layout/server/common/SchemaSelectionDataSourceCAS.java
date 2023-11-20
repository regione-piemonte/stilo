/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.common;

import it.eng.auriga.ui.module.layout.shared.bean.SchemaBean;
import it.eng.auriga.ui.module.layout.shared.bean.SchemaSelector;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.GsonBuilderFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Datasource(id="SchemaSelectionDataSourceCAS")
public class SchemaSelectionDataSourceCAS extends AbstractServiceDataSource<SchemaSelector, SchemaSelector>{
	
	private static GsonBuilder builder = GsonBuilderFactory.getIstance();
	
	@Override
	public SchemaSelector call(SchemaSelector bean) throws Exception {
		SchemaSelector result = new SchemaSelector();		
		for (int i=0; i<4; i++){
			SchemaBean lSchemaBean = new SchemaBean();
			lSchemaBean.setAlias("Alias" + i);
			lSchemaBean.setName("Name" + i);
			result.addSchema(lSchemaBean);
		}
		
		return result;
	}
	
	public static String getSchemas(){		
		SchemaSelector result = (SchemaSelector) SpringAppContext.getContext().getBean("SchemaConfigurator");
		if (result.getSchemi().size()==1) result.setShowSelection(false);
		
		Gson gson = builder.create();
		return gson.toJson(result);
	}

}
