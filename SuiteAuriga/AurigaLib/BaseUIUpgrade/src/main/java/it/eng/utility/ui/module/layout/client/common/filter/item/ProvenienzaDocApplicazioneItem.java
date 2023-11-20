/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class ProvenienzaDocApplicazioneItem extends CustomItem {

	DynamicForm _form;
	private ConfigurableFilter mFilter;

	public ProvenienzaDocApplicazioneItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public ProvenienzaDocApplicazioneItem(){
		super();
	}
		
	public ProvenienzaDocApplicazioneItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new ProvenienzaDocApplicazioneItem(jsObj);
		return lCustomItem;
	}

	@Override
	public CustomItemFormField[] getFormFields() {
//		CustomItemFormField idDocProv = new CustomItemFormField("id_doc_prov", I18NUtil.getMessages().provenienzaDocApplicazioneItem_id_doc_prov_title(), this);
		CustomItemFormField idDocProv = new CustomItemFormField("id_doc_prov", "ID DOC", this);
		TextItem lTextItemSigla = new TextItem();
		lTextItemSigla.setCharacterCasing(CharacterCasing.UPPER);
		idDocProv.setEditorType(lTextItemSigla);
		idDocProv.setEndRow(false);
		idDocProv.setWidth(50);
		idDocProv.setValidators(new AllOrNothingValidator());
		idDocProv.setRequired(true);

//		CustomItemFormField appl = new CustomItemFormField("appl", I18NUtil.getMessages().provenienzaDocApplicazioneItem_appl_title(), this);
		CustomItemFormField appl = new CustomItemFormField("appl", "APPLICAZIONE", this);
		TextItem lTextItemAppl = new TextItem();
		lTextItemAppl.setCharacterCasing(CharacterCasing.UPPER);
		appl.setEditorType(lTextItemAppl);
		appl.setEndRow(false);
		appl.setWidth(50);
		appl.setValidators(new AllOrNothingValidator());
		appl.setRequired(true);
		
		return new CustomItemFormField[]{idDocProv, appl};
	}

	public void setFilter(ConfigurableFilter filter) {
		mFilter = filter;
	}

}
