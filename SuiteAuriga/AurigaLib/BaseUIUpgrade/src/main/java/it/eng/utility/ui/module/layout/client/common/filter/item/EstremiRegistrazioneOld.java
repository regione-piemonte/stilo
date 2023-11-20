/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;

import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class EstremiRegistrazioneOld extends CanvasItem {

	DynamicForm _form;
	private TextItem sigla;
	private CustomItemFormField anno;
	private TextItem numero;

	public static EstremiRegistrazioneOld getOrCreateRef(JavaScriptObject jsObj) {
		if(jsObj == null) return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if(obj != null) {
			obj.setJsObj(jsObj);
			return (EstremiRegistrazioneOld) obj;
		} else {
			return new EstremiRegistrazioneOld(jsObj);
		}
	}

	public EstremiRegistrazioneOld(JavaScriptObject jsObj){
		super(jsObj);
	}

	public EstremiRegistrazioneOld(){
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
            public void onInit(FormItem item) {
                //Inizializza il componente
                new EstremiRegistrazioneOld(item.getJsObj()).disegna();
                
            }
        });		
	}

	/**
	 * Questa parte disegna il mini form
	 */
	protected void disegna() {
		_form = new DynamicForm();
		_form.setNumCols(6);
		_form.setColWidths(30,30,30,50,30,50);
		sigla = new TextItem("sigla");
//		sigla.setTitle(I18NUtil.getMessages().estremiRegistrazioneItem_sigla_title());
		sigla.setEndRow(false);
		sigla.setWidth(50);
//		anno = new FilterCanvasFormItem("anno", "Anno", this);
		anno.setEditorType(new ComboBoxItem());
//		anno.setTitle(I18NUtil.getMessages().estremiRegistrazioneItem_anno_title());
		anno.setStartRow(false);
		anno.setEndRow(false);
		anno.setWidth(70);
		LinkedHashMap<String, String> lMap = new LinkedHashMap<String, String>();
		Date lDate = new Date();
		SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("yyyy");
		int lIntAnno = Integer.valueOf(lSimpleDateFormat.format(lDate));
//		int lIntAnnoPartenza = Layout.getGenericConfig().getAnnoPartenzaRicerca();
		int lIntAnnoPartenza = 1989;
		for(int i=lIntAnno; i>lIntAnnoPartenza; i--){
			lMap.put(""+i, ""+i);
		}
		anno.setValueMap(lMap);
		numero = new TextItem("numero");
//		numero.setTitle(I18NUtil.getMessages().estremiRegistrazioneItem_numero_title());
		numero.setStartRow(false);
		numero.setWidth(90);
		numero.setKeyPressFilter("[0-9]");

		sigla.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				manageChanged(event);
			}
		});
//		anno.addChangedHandler(new ChangedHandler() {
//			@Override
//			public void onChanged(ChangedEvent event) {
//				manageChanged(event);
//			}
//		});
		numero.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				manageNumeroChanged(event);
				
			}
		});	
		_form.setFields(new FormItem[]{sigla, anno, numero});
		setCanvas(_form);
		setShouldSaveValue(true);		
	}

	protected void customMethod() {
		
		
	}

	protected void manageNumeroChanged(ChangedEvent event) {
		if (numero.isDrawn()){
			String lStrValue = numero.getValueAsString();
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
			numero.setValue(lStrValue);
			updateInternalValue(numero.getName(), lStrValue);
		}	
	}

	protected void manageChanged(ChangedEvent event) {
		updateInternalValue(event.getItem().getName(), event.getValue());
	}
	
	protected void updateInternalValue(String name, Object value){
		DynamicForm lform1 = (DynamicForm)this.getCanvas();
		CanvasItem lCanvasItem = lform1.getCanvasItem();
		Record lRecord;
		if (lCanvasItem.getValue()==null){
			lRecord = new Record();
		} else lRecord = new Record((JavaScriptObject) lCanvasItem.getValue());
		
		lRecord.setAttribute(name, value);
		SC.echo(lRecord.getJsObj());
		lCanvasItem.storeValue(lRecord);
	}
}
