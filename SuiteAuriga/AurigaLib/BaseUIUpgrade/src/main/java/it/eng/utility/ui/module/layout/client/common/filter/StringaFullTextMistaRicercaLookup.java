/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.filter.item.StringaFullTextMistaRicercaLookupItem;

public class StringaFullTextMistaRicercaLookup extends CustomDataSourceField {
	
	protected ConfigurableFilter _filter;
	
	public StringaFullTextMistaRicercaLookup() {
		super(FieldType.CUSTOM);
	}

	public StringaFullTextMistaRicercaLookup(String name) {
		super(name, FieldType.CUSTOM);
	}

	public StringaFullTextMistaRicercaLookup(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public StringaFullTextMistaRicercaLookup(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.CUSTOM, title, lMap);
	}

	public StringaFullTextMistaRicercaLookup(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public StringaFullTextMistaRicercaLookup(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	public StringaFullTextMistaRicercaLookup(String name, String title, Map<String, String> lMap, ConfigurableFilter filter) {
		super(name, FieldType.CUSTOM, title, lMap);
		setFilter(filter);
		init(); // init viene chiamato nel super, ma ho bisogno del filter che viene settato dopo, quindi RICHIAMO init()
	}

	protected void init() {
		
		setAttribute("customType", "stringa_full_text_mista_ricerca_lookup");		

		StringaFullTextMistaRicercaLookupItem editorType = new StringaFullTextMistaRicercaLookupItem(getTitle(), property, this, _filter);
		setEditorType(editorType);

	}
	
	public ConfigurableFilter getFilter() {
		return _filter;
	}

	public void setFilter(ConfigurableFilter filter) {
		this._filter = filter;
	}

}
