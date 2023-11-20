/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.SelectItem;

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.filter.ClassificazioneItem;

public class Classificazione extends CustomDataSourceField {
	
	public Classificazione(){
		super(FieldType.CUSTOM);
	}
	
	public Classificazione(String name) {
		super(name, FieldType.CUSTOM);
	}

	public Classificazione(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public Classificazione(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public Classificazione(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {

		setAttribute("customType", "classificazione");		

		ClassificazioneItem editorType = new ClassificazioneItem(getConfigurableFilter(), this); 
		setEditorType(editorType);
//		setFilterEditorType(ClassificazioneItem.class);

//		setValidOperators(OperatorId.EQUALS);

	}
	
	public ConfigurableFilter getConfigurableFilter() {
		return null;
	}

}
