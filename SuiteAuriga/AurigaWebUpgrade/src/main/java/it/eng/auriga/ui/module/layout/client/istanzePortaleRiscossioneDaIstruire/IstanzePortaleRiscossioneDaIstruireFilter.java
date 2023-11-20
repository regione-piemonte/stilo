/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;


public class IstanzePortaleRiscossioneDaIstruireFilter extends ConfigurableFilter {

	public IstanzePortaleRiscossioneDaIstruireFilter(String lista) {
		super(lista);
	}
	
	public void createSelectItem(FilterFieldBean pFilterFieldBean, DataSourceField pDataSourceField, LinkedHashMap<String, String> valueMap) {
		it.eng.utility.ui.module.layout.client.common.items.SelectItem lSelectItem = new it.eng.utility.ui.module.layout.client.common.items.SelectItem(pFilterFieldBean.getName(), pFilterFieldBean.getTitle());
		lSelectItem.setValueMap(valueMap);
		if("tipoIstanza".equals(pDataSourceField.getName())) {
			lSelectItem.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					
					//Se cambio il filtro di tipoIstanza se la lista era già caricata la ricarico
					try {
						if(layout.getList().getDataAsRecordList().getLength() > 0) {
							layout.doSearch();
						}
					} catch(Exception e) {}
				}
			});
		}
		pDataSourceField.setEditorType(lSelectItem);
	}
	
}