/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.eng.auriga.ui.module.layout.shared.bean.SchemaBean;
import it.eng.auriga.ui.module.layout.shared.bean.SchemaSelector;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.GsonBuilderFactory;

@Datasource(id = "SchemaSelectionDataSource")
public class SchemaSelectionDataSource extends AbstractServiceDataSource<SchemaSelector, SchemaSelector> {

	private static Logger mLogger = Logger.getLogger(SchemaSelectionDataSource.class);
	private static GsonBuilder builder = GsonBuilderFactory.getIstance();

	@Override
	public SchemaSelector call(SchemaSelector bean) throws Exception {
		SchemaSelector result = new SchemaSelector();
		for (int i = 0; i < 4; i++) {
			SchemaBean lSchemaBean = new SchemaBean();
			lSchemaBean.setAlias("Alias" + i);
			lSchemaBean.setName("Name" + i);
			result.addSchema(lSchemaBean);
		}

		return result;
	}

	public static String getSchemas() {
		return getSchemas(null);
	}
	
	public static String getSchemas(String schemaSelezionato) {
		// mLogger.debug("getSchemas() static CALLED");
		SchemaSelector result = (SchemaSelector) SpringAppContext.getContext().getBean("SchemaConfigurator");
		SchemaBean schemaBeanSel = null;
		if (schemaSelezionato != null && schemaSelezionato.length() > 0) {
			for (SchemaBean schemaBean : result.getSchemi()) {
				if (schemaBean.getName().equals(schemaSelezionato)) {
					schemaBeanSel = getSchemaBean(schemaBean);
					break;
				}
			}
		} else {
			schemaBeanSel = getSchemaBean(result.getDefaultSchema());
		}
		if (schemaBeanSel != null) {
			final ArrayList<SchemaBean> schemiNew = new ArrayList<SchemaBean>();
			schemiNew.add(schemaBeanSel);
			final SchemaSelector schemaSelectorNew = new SchemaSelector();
			schemaSelectorNew.setSchemi(schemiNew);
			schemaSelectorNew.setDefaultSchema(getSchemaBean(result.getDefaultSchema()));
			schemaSelectorNew.setShowSelection(result.getShowSelection() != null ? new Boolean(result.getShowSelection()) : null);
			result = schemaSelectorNew;
		}
		final int n = result.getSchemi().size();
		if (n == 1) {
			result.setShowSelection(false);
		}
		if (n > 0 && result.getDefaultSchema() == null) {
			result.setDefaultSchema(result.getSchemi().get(0));
		}
		Gson gson = builder.create();
		String json = gson.toJson(result);
		return json;
	}

	private static SchemaBean getSchemaBean(SchemaBean schemaBean) {
		if (schemaBean == null)
			return null;
		SchemaBean schemaBeanSel = new SchemaBean();
		schemaBeanSel.setName(schemaBean.getName() != null ? new String(schemaBean.getName()) : null);
		schemaBeanSel.setAlias(schemaBean.getAlias() != null ? new String(schemaBean.getAlias()) : null);
		return schemaBeanSel;
	}

}
