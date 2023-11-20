/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.filter.item.StringaRicercaEstesaCaseInsensitive1WithLookupItem;


public class StringaRicercaEstesaCaseInsensitive1WithLookup extends CustomDataSourceField {

	protected ConfigurableFilter _filter;
	
	public StringaRicercaEstesaCaseInsensitive1WithLookup() {
		super(FieldType.CUSTOM);
	}

	public StringaRicercaEstesaCaseInsensitive1WithLookup(String name) {
		super(name, FieldType.CUSTOM);
	}

	public StringaRicercaEstesaCaseInsensitive1WithLookup(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public StringaRicercaEstesaCaseInsensitive1WithLookup(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.CUSTOM, title, lMap);
	}
	
	public StringaRicercaEstesaCaseInsensitive1WithLookup(String name, String title, Map<String, String> lMap, ConfigurableFilter filter) {
		super(name, FieldType.CUSTOM, title, lMap);
		setFilter(filter);
		init(); // init viene chiamato nel super, ma ho bisogno del filter che viene settato dopo, quindi RICHIAMO init()
	}

	protected void init() {
		
		setAttribute("customType", "stringa_ricerca_estesa_case_insensitive_1_with_lookup");		
		
		StringaRicercaEstesaCaseInsensitive1WithLookupItem editorType = new StringaRicercaEstesaCaseInsensitive1WithLookupItem(this, _filter, property);
		setEditorType(editorType);	
        
	}
	
	public ConfigurableFilter getFilter() {
		return _filter;
	}

	public void setFilter(ConfigurableFilter filter) {
		this._filter = filter;
	}

}
