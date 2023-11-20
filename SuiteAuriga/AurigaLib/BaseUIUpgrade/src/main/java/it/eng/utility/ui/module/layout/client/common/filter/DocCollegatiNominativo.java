/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;

public class DocCollegatiNominativo extends CustomDataSourceField {
	protected ConfigurableFilter _filter;
	
	public DocCollegatiNominativo(){
		super(FieldType.CUSTOM);
	}
	
	public DocCollegatiNominativo(String name) {
		super(name, FieldType.CUSTOM);
	}

	public DocCollegatiNominativo(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public DocCollegatiNominativo(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.CUSTOM, title, lMap);
	}

	public DocCollegatiNominativo(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public DocCollegatiNominativo(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	public DocCollegatiNominativo(String name, String title, Map<String, String> lMap, ConfigurableFilter filter) {
		super(name, FieldType.CUSTOM, title, lMap);
		setFilter(filter);
		init(); // init viene chiamato nel super, ma ho bisogno del filter che viene settato dopo, quindi RICHIAMO init()
		
	}
	
	protected void init() {

		setAttribute("customType", "doc_collegati_nominativo");		

		DocCollegatiNominativoItem editorType = new DocCollegatiNominativoItem(property, this, _filter); 
		setEditorType(editorType);
	}

	public ConfigurableFilter getFilter() {
		return _filter;
	}

	public void setFilter(ConfigurableFilter filter) {
		this._filter = filter;
	}

}
