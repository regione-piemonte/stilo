/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class EntitaAssociataModelloDocItem extends CustomItem {
	
	private SelectItem tipoEntitaAssociataItem;
	
	private HiddenItem idEntitaAssociataItem;
	private HiddenItem nomeEntitaAssociataItem;

	private SelectItem tipoDocumentoItem;
	private SelectItem tipoFolderItem;
	private SelectItem tipoProcedimentoItem;
	
	public EntitaAssociataModelloDocItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public EntitaAssociataModelloDocItem(){
		super();
	}
	
	public EntitaAssociataModelloDocItem(final Map<String, String> property){
		super(property);
	}
	
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new EntitaAssociataModelloDocItem(jsObj);
		return lCustomItem;
	}
	
	@Override
	public CustomItemFormField[] getFormFields() {
		
		ArrayList<CustomItemFormField> formFieldsList = new ArrayList<CustomItemFormField>();
		
		CustomItemFormField tipoEntitaAssociata = new CustomItemFormField("tipoEntitaAssociata", "", this);
		tipoEntitaAssociataItem = new SelectItem("tipoEntitaAssociata", "Tipo"){
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));					
			}
			
			@Override
			public void clearSelect() {
				super.clearSelect();
				tipoEntitaAssociataItem.setValue("");
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));
			}
			
			@Override
			public void setValue(String value) {
				super.clearSelect();
				if (value == null || "".equals(value)) {
					tipoEntitaAssociataItem.setValue("");
					_form.markForRedraw();
					_instance.storeValue(new Record(_form.getValues()));
				}
			}
		};
		tipoEntitaAssociataItem.setShowTitle(false);
		tipoEntitaAssociataItem.setWidth(400);
		LinkedHashMap<String, String> tipoEntitaAssociataValueMap = new LinkedHashMap<String, String>();
		tipoEntitaAssociataValueMap.put("TD", "Tipo documento");
		tipoEntitaAssociataValueMap.put("TF", "Tipo fascicolo");
//		tipoEntitaAssociataValueMap.put("TP", "Tipo di processo/procedimento");
		tipoEntitaAssociataItem.setValueMap(tipoEntitaAssociataValueMap);
		tipoEntitaAssociataItem.setDisplayField("value");
		tipoEntitaAssociataItem.setValueField("key");
		tipoEntitaAssociataItem.setWidth(300);
		tipoEntitaAssociataItem.setAllowEmptyValue(false);
		tipoEntitaAssociataItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				_form.markForRedraw();
			}
		});		
		tipoEntitaAssociata.setEditorType(tipoEntitaAssociataItem);
		formFieldsList.add(tipoEntitaAssociata);
		
		CustomItemFormField idEntitaAssociata = new CustomItemFormField("idEntitaAssociata", "", this);
		idEntitaAssociataItem = new HiddenItem("idEntitaAssociata");
		idEntitaAssociata.setEditorType(idEntitaAssociataItem);
		formFieldsList.add(idEntitaAssociata);
		
		CustomItemFormField nomeEntitaAssociata = new CustomItemFormField("nomeEntitaAssociata", "", this);
		nomeEntitaAssociataItem = new HiddenItem("nomeEntitaAssociata");
		nomeEntitaAssociata.setEditorType(nomeEntitaAssociataItem);
		formFieldsList.add(nomeEntitaAssociata);
		
		CustomItemFormField tipoDocumento = new CustomItemFormField("tipoDocumento", "", this);
		GWTRestDataSource tipoDocumentoDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
		tipoDocumentoDS.addParam("isFromFilter", "true");
		tipoDocumentoItem = new SelectItem("tipoDocumento") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				idEntitaAssociataItem.setValue(record.getAttribute("idTipoDocumento"));
				nomeEntitaAssociataItem.setValue(record.getAttribute("descTipoDocumento"));
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				tipoDocumentoItem.setValue("");
				idEntitaAssociataItem.setValue("");
				nomeEntitaAssociataItem.setValue("");
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					tipoDocumentoItem.setValue("");
					idEntitaAssociataItem.setValue("");
					nomeEntitaAssociataItem.setValue("");
				}
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));
            }
			
		};
		tipoDocumentoItem.setWidth(300);
		tipoDocumentoItem.setEndRow(true);
		tipoDocumentoItem.setShowTitle(false);
		tipoDocumentoItem.setPickListWidth(450);
		tipoDocumentoItem.setValueField("idTipoDocumento");
		tipoDocumentoItem.setDisplayField("descTipoDocumento");
		tipoDocumentoItem.setOptionDataSource(tipoDocumentoDS);
		tipoDocumentoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return _form.getValueAsString("tipoEntitaAssociata") != null && "TD".equals(_form.getValueAsString("tipoEntitaAssociata"));
			}
		});		
		tipoDocumento.setEditorType(tipoDocumentoItem);
		formFieldsList.add(tipoDocumento);
		
		CustomItemFormField tipoFolder = new CustomItemFormField("tipoFolder", "", this);
		GWTRestDataSource tipoFolderDS = new GWTRestDataSource("LoadComboTipoFolderDataSource", "idFolderType", FieldType.TEXT);
		tipoFolderDS.addParam("isFromFilter", "true");
		tipoFolderItem = new SelectItem("tipoFolder") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				idEntitaAssociataItem.setValue(record.getAttribute("idFolderType"));
				nomeEntitaAssociataItem.setValue(record.getAttribute("descFolderType"));	
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				tipoFolderItem.setValue("");
				idEntitaAssociataItem.setValue("");
				nomeEntitaAssociataItem.setValue("");
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					tipoFolderItem.setValue("");
					idEntitaAssociataItem.setValue("");
					nomeEntitaAssociataItem.setValue("");
				}
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));
            }
			
		};
		tipoFolderItem.setWidth(300);
		tipoFolderItem.setEndRow(true);
		tipoFolderItem.setShowTitle(false);
		tipoFolderItem.setPickListWidth(450);
		tipoFolderItem.setValueField("idFolderType");
		tipoFolderItem.setDisplayField("descFolderType");
		tipoFolderItem.setOptionDataSource(tipoFolderDS);
		tipoFolderItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return _form.getValueAsString("tipoEntitaAssociata") != null && "TF".equals(_form.getValueAsString("tipoEntitaAssociata"));
			}
		});
		tipoFolder.setEditorType(tipoFolderItem);
		formFieldsList.add(tipoFolder);
		
		CustomItemFormField tipoProcedimento = new CustomItemFormField("tipoProcedimento", "", this);
		GWTRestDataSource tipoProcedimentoDS = new GWTRestDataSource("LoadComboTipiProcDataSource", "key", FieldType.TEXT, true);
		tipoProcedimentoItem = new SelectItem("tipoProcedimento") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				idEntitaAssociataItem.setValue(record.getAttribute("key"));
				nomeEntitaAssociataItem.setValue(record.getAttribute("value"));	
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				tipoProcedimentoItem.setValue("");
				idEntitaAssociataItem.setValue("");
				nomeEntitaAssociataItem.setValue("");
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					tipoProcedimentoItem.setValue("");
					idEntitaAssociataItem.setValue("");
					nomeEntitaAssociataItem.setValue("");
				}
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));
            }
			
		};
		tipoProcedimentoItem.setWidth(300);
		tipoProcedimentoItem.setEndRow(true);
		tipoProcedimentoItem.setShowTitle(false);
		tipoProcedimentoItem.setPickListWidth(450);
		tipoProcedimentoItem.setValueField("key");
		tipoProcedimentoItem.setDisplayField("value");
		tipoProcedimentoItem.setOptionDataSource(tipoProcedimentoDS);		
		tipoProcedimentoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return _form.getValueAsString("tipoEntitaAssociata") != null && "TP".equals(_form.getValueAsString("tipoEntitaAssociata"));
			}
		});
		tipoProcedimento.setEditorType(tipoProcedimentoItem);
		formFieldsList.add(tipoProcedimento);
		
		return formFieldsList.toArray(new CustomItemFormField[formFieldsList.size()]);
	}
	
}