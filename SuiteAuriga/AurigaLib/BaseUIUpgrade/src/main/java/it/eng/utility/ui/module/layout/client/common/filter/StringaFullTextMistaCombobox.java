/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.filter.item.StringaFullTextMistaComboboxItem;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;

public class StringaFullTextMistaCombobox extends CustomDataSourceField {
	protected ConfigurableFilter _filter;
	protected FilterFieldBean _filterBean;
	
	public StringaFullTextMistaCombobox() {
		super(FieldType.CUSTOM);
	}

	public StringaFullTextMistaCombobox(String name) {
		super(name, FieldType.CUSTOM);
	}

	public StringaFullTextMistaCombobox(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public StringaFullTextMistaCombobox(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.CUSTOM, title, lMap);
	}

	public StringaFullTextMistaCombobox(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public StringaFullTextMistaCombobox(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	public StringaFullTextMistaCombobox(String name, String title, FilterFieldBean filterBean, ConfigurableFilter filter) {
		super(name, FieldType.CUSTOM, title);
		setFilter(filter);
		setFilterBean(filterBean);
		init(); // init viene chiamato nel super, ma ho bisogno del filter che viene settato dopo, quindi RICHIAMO init()
	}

	protected void init() {
		
		setAttribute("customType", "combo_box");
		
		StringaFullTextMistaComboboxItem editorType = new StringaFullTextMistaComboboxItem(getFilter(), getFilterBean(), getTitle(), this);
		setEditorType(editorType);

	}
	
	public ConfigurableFilter getFilter() {
		return _filter;
	}

	public void setFilter(ConfigurableFilter filter) {
		this._filter = filter;
	}

	public FilterFieldBean getFilterBean() {
		return _filterBean;
	}

	public void setFilterBean(FilterFieldBean filterBean) {
		this._filterBean = filterBean;
	}

}
