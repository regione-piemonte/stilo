/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;

import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;

public class NumeroProgressivoEmailItem extends CustomItem {

	public NumeroProgressivoEmailItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public NumeroProgressivoEmailItem(){
		super();
	}
		
	public NumeroProgressivoEmailItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new NumeroProgressivoEmailItem(jsObj);
		return lCustomItem;
	}

	@Override
	public CustomItemFormField[] getFormFields() {

		CustomItemFormField numero = new CustomItemFormField("numero", this);
		NumericItem numeroEditorType = new NumericItem();
		numero.setEditorType(numeroEditorType);
		numero.setShowTitle(false);
		numero.setEndRow(false);		
		numero.setColSpan(1);		

		CustomItemFormField anno = new CustomItemFormField("anno", this);
		AnnoItem annoEditorType = new AnnoItem();
		anno.setEditorType(annoEditorType);
		anno.setTitle("&nbsp;/");
		anno.setEndRow(false);		
		anno.setColSpan(1);		
		
		CustomItemFormField titleTipo = new CustomItemFormField("titleTipo", this);
		StaticTextItem titleTipoEditorType = new StaticTextItem();
		titleTipoEditorType.setWidth(25);
//		titleTipoEditorType.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleTipoEditorType.setAlign(Alignment.RIGHT);
		titleTipoEditorType.setDefaultValue("Tipo");
		titleTipo.setEditorType(titleTipoEditorType);
		titleTipo.setShowTitle(false);
		titleTipo.setCanEdit(false);
		titleTipo.setEndRow(false);		
		titleTipo.setColSpan(1);		
		titleTipo.setHidden(getName() != null && getName().equalsIgnoreCase("start"));
//		titleTipo.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {				
//				return getName() != null && !getName().equals("start");
//			}
//		});
 
		CustomItemFormField tipo = new CustomItemFormField("tipo", this);
		SelectItem tipoEditorType = new SelectItem();
		tipoEditorType.setWidth(100);
		LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
		tipoValueMap.put("E", "Entrata");
		tipoValueMap.put("U", "Uscita");
		tipoValueMap.put("N", "Notifica");
		tipoValueMap.put("B", "Bozze");
		tipoEditorType.setAllowEmptyValue(true);
		tipoEditorType.setValueMap(tipoValueMap);		
		tipo.setEditorType(tipoEditorType);
		tipo.setShowTitle(false);
		tipo.setEndRow(false);		
		tipo.setColSpan(1);		
		tipo.setHidden(getName() != null && getName().equalsIgnoreCase("start"));
//		tipo.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {				
//				return getName() != null && !getName().equals("start");
//			}
//		});

		return new CustomItemFormField[] { numero, anno, titleTipo, tipo };		
	}
	
	@Override
	protected void editRecord(Record record) {		
		_form.editRecord(record);
	}

	@Override
	protected void storeDefaultValues(final Record lRecord) {
		if (lRecord != null) {
			CanvasItem lCanvasItem = _form.getCanvasItem();
			if (lCanvasItem != null && !lCanvasItem.getName().equals("progressivo")) {
				lCanvasItem.getCanvas().addDrawHandler(new DrawHandler() {

					@Override
					public void onDraw(DrawEvent event) {
						CanvasItem lCanvasItem = _form.getCanvasItem();
						lCanvasItem.storeValue(lRecord);
					}
				});
			}
		}
	}

	@Override
	protected Record setDefaultValues(Object value) {		
		if (value == null) {
			Record lRecordDefault = new Record();
			String today = DateUtil.format(new Date());
			int annoCorrente = Integer.parseInt(today.substring(today.lastIndexOf("/") + 1));
			lRecordDefault.setAttribute("anno", "" + annoCorrente);
			lRecordDefault.setAttribute("tipo", "E");
			return lRecordDefault;
		} else {
			return new Record((JavaScriptObject) value);
		}
	}

}
