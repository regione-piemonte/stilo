/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class InoltroRagioneriaItem extends CustomItem {

	public InoltroRagioneriaItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public InoltroRagioneriaItem(){
		super();
	}
	
	public InoltroRagioneriaItem(String datasourceName){
		super(buildPropertyWithDatasourceName(datasourceName));
	}
	
	public static Map<String, String> buildPropertyWithDatasourceName(String datasourceName) {
		
		Map<String, String> property = new HashMap<String, String>();
		property.put("datasourceName", datasourceName);
		return property;
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new InoltroRagioneriaItem(jsObj);
		return lCustomItem;
	}

	@Override
	public CustomItemFormField[] getFormFields() {

		String datasourceName = property.get("datasourceName");
		
		CustomItemFormField idProcessType = new CustomItemFormField("idProcessType", this);
		idProcessType.setEditorType(new HiddenItem());
		
		CustomItemFormField assegnatario = new CustomItemFormField("assegnatario", this);
		assegnatario.setEditorType(new HiddenItem());
		
		CustomItemFormField dtInoltroRagioneria = new CustomItemFormField("dtInoltroRagioneria", this);
		dtInoltroRagioneria.setEditorType(new HiddenItem());
		
		CustomItemFormField nome = new CustomItemFormField("nome", this);
		SelectTreeInoltroRagioneriaItem nomeEditorType = new SelectTreeInoltroRagioneriaItem(datasourceName) {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				_form.setValue("idProcessType", record.getAttribute("idProcessType"));
				_form.setValue("assegnatario", record.getAttribute("assegnatario"));
				_form.setValue("dtInoltroRagioneria", record.getAttribute("dtInoltroRagioneria"));
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));			
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					_form.clearValue("nome");
					_form.clearValue("idProcessType");
					_form.clearValue("assegnatario");
					_form.clearValue("dtInoltroRagioneria");		
					_form.markForRedraw();
				}
				_instance.storeValue(new Record(_form.getValues()));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				_form.clearValue("nome");
				_form.clearValue("idProcessType");
				_form.clearValue("assegnatario");
				_form.clearValue("dtInoltroRagioneria");	
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));
			};
		};
		nomeEditorType.setWidth(350);		
		nome.setEditorType(nomeEditorType);
		nome.setEndRow(false);	
		
		return new CustomItemFormField[] { idProcessType, assegnatario, dtInoltroRagioneria, nome };
	}
	
	@Override
	protected void editRecord(Record record) {
		super.editRecord(record);
		_instance.storeValue(record);
	}

}
