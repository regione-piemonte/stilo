/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class EstremiRegistrazioneItem extends CustomItem {

	DynamicForm _form;
	private ConfigurableFilter mFilter;

	public EstremiRegistrazioneItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public EstremiRegistrazioneItem(){
		super();
	}
		
	public EstremiRegistrazioneItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new EstremiRegistrazioneItem(jsObj);
		return lCustomItem;
	}

	@Override
	public CustomItemFormField[] getFormFields() {
		CustomItemFormField sigla = new CustomItemFormField("sigla", I18NUtil.getMessages().estremiRegistrazioneItem_sigla_title(), this);
		TextItem lTextItemSigla = new TextItem();
		lTextItemSigla.setCharacterCasing(CharacterCasing.UPPER);
		sigla.setEditorType(lTextItemSigla);
		sigla.setEndRow(false);
		sigla.setWidth(50);
		sigla.setValidators(new AllOrNothingValidator());
//		sigla.setRequired(true);
//		sigla.setValidateOnChange(true);
		CustomItemFormField anno = new CustomItemFormField("anno", I18NUtil.getMessages().estremiRegistrazioneItem_anno_title(), this);
		anno.setEditorType(new AnnoItem());
		anno.setValidators(new AllOrNothingValidator());
//		anno.setEditorType(new ComboBoxItem());
//		anno.setStartRow(false);
//		anno.setEndRow(false);
//		anno.setWidth(70);
//		anno.setValueMap(getMapForAnno());
		CustomItemFormField numero = new CustomItemFormField("numero", I18NUtil.getMessages().estremiRegistrazioneItem_numero_title(), this){
			@Override
			protected Object manageValue(Object value) {
				return manageNumberValue(value, this);
			}
		};
		TextItem lTextItem = new TextItem();
		lTextItem.setKeyPressFilter("[0-9]");
		numero.setEditorType(lTextItem);
//		numero.setTitle(I18NUtil.getMessages().estremiRegistrazioneItem_numero_title());
		numero.setStartRow(false);
		numero.setWidth(90);
		numero.setValidators(new AllOrNothingValidator());
		return new CustomItemFormField[]{sigla, anno, numero};
	}


	protected Object manageNumberValue(Object value, CustomItemFormField filterCanvasFormItem) {
		if (value == null){
			filterCanvasFormItem.setValue((String)null);
			return null;
		}
		String lStrValue = (String)value;
		String realValue = null;
		boolean firstNonZero = false;
		for (int k=0; k<lStrValue.length(); k++){
			if (lStrValue.charAt(k)=='0'){

			} else {
				if (firstNonZero==false){
					realValue = lStrValue.substring(k);
					firstNonZero = true;
				}
			}
		}
		lStrValue = realValue!=null?realValue:"0";
		if (lStrValue.length()<7){
			for (int i=lStrValue.length(); i<7; i++){
				lStrValue = "0" + lStrValue;
			}

		} else {
			lStrValue = lStrValue.substring(0,7);
		}
		filterCanvasFormItem.setValue(lStrValue);
		return lStrValue;
	}

	public void setFilter(ConfigurableFilter filter) {
		mFilter = filter;
	}

	//	/**
	//	 * Questa parte disegna il mini form
	//	 */
	//	protected void disegna1() {
	//		_form = new DynamicForm();
	//		_form.setNumCols(6);
	//		_form.setColWidths(30,30,30,50,30,50);
	//		sigla = new TextItem("sigla");
	////		sigla.setTitle(I18NUtil.getMessages().estremiRegistrazioneItem_sigla_title());
	//		sigla.setEndRow(false);
	//		sigla.setWidth(50);
	//		anno = new FilterCanvasFormItem("anno", "Anno", this);
	//		anno.setEditorType(new ComboBoxItem());
	////		anno.setTitle(I18NUtil.getMessages().estremiRegistrazioneItem_anno_title());
	//		anno.setStartRow(false);
	//		anno.setEndRow(false);
	//		anno.setWidth(70);
	//		LinkedHashMap<String, String> lMap = new LinkedHashMap<String, String>();
	//		Date lDate = new Date();
	//		SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("yyyy");
	//		int lIntAnno = Integer.valueOf(lSimpleDateFormat.format(lDate));
	////		int lIntAnnoPartenza = Layout.getGenericConfig().getAnnoPartenzaRicerca();
	//		int lIntAnnoPartenza = 1989;
	//		for(int i=lIntAnno; i>lIntAnnoPartenza; i--){
	//			lMap.put(""+i, ""+i);
	//		}
	//		anno.setValueMap(lMap);
	//		numero = new TextItem("numero");
	////		numero.setTitle(I18NUtil.getMessages().estremiRegistrazioneItem_numero_title());
	//		numero.setStartRow(false);
	//		numero.setWidth(90);
	//		numero.setKeyPressFilter("[0-9]");
	//
	//		sigla.addChangedHandler(new ChangedHandler() {
	//			@Override
	//			public void onChanged(ChangedEvent event) {
	//				manageChanged(event);
	//			}
	//		});
	////		anno.addChangedHandler(new ChangedHandler() {
	////			@Override
	////			public void onChanged(ChangedEvent event) {
	////				manageChanged(event);
	////			}
	////		});
	//		numero.addChangedHandler(new ChangedHandler() {
	//			@Override
	//			public void onChanged(ChangedEvent event) {
	//				manageNumeroChanged(event);
	//				
	//			}
	//		});	
	//		_form.setFields(new FormItem[]{sigla, anno, numero});
	//		setCanvas(_form);
	//		setShouldSaveValue(true);		
	//	}
	//
	//	protected void customMethod() {
	//		
	//		
	//	}
	//
	//	protected void manageNumeroChanged(ChangedEvent event) {
	//		if (numero.isDrawn()){
	//			String lStrValue = numero.getValueAsString();
	//			String realValue = null;
	//			boolean firstNonZero = false;
	//			for (int k=0; k<lStrValue.length(); k++){
	//				if (lStrValue.charAt(k)=='0'){
	//					
	//				} else {
	//					if (firstNonZero==false){
	//						realValue = lStrValue.substring(k);
	//						firstNonZero = true;
	//					}
	//				}
	//			}
	//			lStrValue = realValue!=null?realValue:"0";
	//			if (lStrValue.length()<7){
	//				for (int i=lStrValue.length(); i<7; i++){
	//					lStrValue = "0" + lStrValue;
	//				}
	//				
	//			} else {
	//				lStrValue = lStrValue.substring(0,7);
	//			}
	//			numero.setValue(lStrValue);
	//			updateInternalValue(numero.getName(), lStrValue);
	//		}	
	//	}
	//
	//	protected void manageChanged(ChangedEvent event) {
	//		updateInternalValue(event.getItem().getName(), event.getValue());
	//	}
	//	
	//	protected void updateInternalValue(String name, Object value){
	//		DynamicForm lform1 = (DynamicForm)this.getCanvas();
	//		CanvasItem lCanvasItem = lform1.getCanvasItem();
	//		Record lRecord;
	//		if (lCanvasItem.getValue()==null){
	//			lRecord = new Record();
	//		} else lRecord = new Record((JavaScriptObject) lCanvasItem.getValue());
	//		
	//		lRecord.setAttribute(name, value);
	//		SC.echo(lRecord.getJsObj());
	//		lCanvasItem.storeValue(lRecord);
	//	}
}
