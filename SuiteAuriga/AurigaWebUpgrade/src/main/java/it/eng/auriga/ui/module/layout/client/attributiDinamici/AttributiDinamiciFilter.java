/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.FieldFetchDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.SelectItemFiltrabile;
import it.eng.utility.ui.module.layout.client.common.filter.ListaScelta;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;

import java.util.Map;

import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.SelectItem;

public class AttributiDinamiciFilter extends ConfigurableFilter {

	public AttributiDinamiciFilter(String lista, Map<String, String> extraparam) {	
		super(lista, extraparam);	
	}
	
	protected FieldFetchDataSource getFieldFetchDataSource() {
		FieldFetchDataSource lFieldFetchDataSource = new FieldFetchDataSource("AttributiDinamiciListFieldDataSource", "name", nomeEntita);
		lFieldFetchDataSource.addParam("nomeTabella", getExtraParam().get("nomeTabella"));
		lFieldFetchDataSource.addParam("tipoEntita", getExtraParam().get("tipoEntita"));
		lFieldFetchDataSource.addParam("token", "");
		lFieldFetchDataSource.setCacheAllData(false);
		lFieldFetchDataSource.setPreventHTTPCaching(false);
		lFieldFetchDataSource.setFilter(filter);
		return lFieldFetchDataSource;
	}
	
	public ListaScelta buildListaScelta(String title, FilterFieldBean lFilterFieldBean) {
		ListaScelta lDataSourceField = null;
		if (lFilterFieldBean.getSelect() != null){							
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), title);										
			lDataSourceField.setMultiple(lFilterFieldBean.getSelect().getMultiple());    
			if(lFilterFieldBean.getSelect().getValueMap() != null) {
				lDataSourceField.setValueMap(lFilterFieldBean.getSelect().getValueMap());
			} else {					
				createFilteredSelectItem(lFilterFieldBean, lDataSourceField);
			}
			mappaMultiple.put(lFilterFieldBean.getName(), lFilterFieldBean.getSelect().getMultiple());
		} else {
			GWTRestDataSource datasource = new GWTRestDataSource("LoadComboAttributoDinamicoDataSource", "key", FieldType.TEXT);
			datasource.addParam("nomeCombo", lFilterFieldBean.getName());
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), title);			
			SelectItem lSelectItem = new SelectItem();		
			lSelectItem.setOptionDataSource(datasource);
			lDataSourceField.setEditorType(lSelectItem);
		}
		return lDataSourceField;
	}
	
}

