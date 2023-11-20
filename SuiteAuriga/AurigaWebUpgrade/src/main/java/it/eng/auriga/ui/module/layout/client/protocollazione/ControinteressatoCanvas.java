/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;

import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class ControinteressatoCanvas extends ReplicableCanvas{

	private ReplicableCanvasForm mDynamicForm;
	private RadioGroupItem tipoSoggettoItem;
	private TextItem denominazioneItem;
	private TextItem codFiscaleItem;
	private TextItem pIvaItem;
	private TextAreaItem noteItem;
	
	public ControinteressatoCanvas(ReplicableItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		tipoSoggettoItem = new RadioGroupItem("tipoSoggetto");
		tipoSoggettoItem.setTitle("Persona fisica / giuridica");
		tipoSoggettoItem.setVertical(false);
		tipoSoggettoItem.setStartRow(true);
		Map<String, String> valueMap = new LinkedHashMap<>();
		valueMap.put("fisica", "fisica");
		valueMap.put("giuridica", "giuridica");
		tipoSoggettoItem.setValueMap(valueMap);
		tipoSoggettoItem.setDefaultValue("fisica");
		tipoSoggettoItem.setRequired(true);
		
		denominazioneItem = new TextItem("denominazione","Cognome e Nome / Ragione sociale");
		denominazioneItem.setWidth(300);
		denominazioneItem.setStartRow(true);
		denominazioneItem.setColSpan(1);
		denominazioneItem.setRequired(true);
		
		codFiscaleItem = new TextItem("codFiscale","Cod.fiscale");
		codFiscaleItem.setWidth(150);
		codFiscaleItem.setLength(16);
		codFiscaleItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value == null || "".equals(value)) {
					return true;
				}
				
				if ("fisica".equalsIgnoreCase(tipoSoggettoItem.getValueAsString())) {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
					return regExp.test((String) value);
				} else {				
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
					return regExp.test((String) value);
				}
			}
		});	
		
		codFiscaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoSoggetto = tipoSoggettoItem.getValueAsString();
				if ("fisica".equalsIgnoreCase(tipoSoggetto)) {
					codFiscaleItem.setLength(16);
				} else {
					codFiscaleItem.setLength(28);
				}
				return true;
			}
		});
		
		pIvaItem = new TextItem("pIva","P. IVA");
		pIvaItem.setWidth(150);
		pIvaItem.setLength(11);
		pIvaItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				if (value == null || "".equals(value)) {
					return true;
				}
											
				RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
				return regExp.test((String) value);
				
			}
		});	
		
		noteItem = new TextAreaItem("note","Note");
		noteItem.setStartRow(true);
		noteItem.setColSpan(7);
		noteItem.setWidth(710);
		noteItem.setHeight(40);
		noteItem.setLength(4000);
		
		mDynamicForm.setFields(tipoSoggettoItem,denominazioneItem,codFiscaleItem,pIvaItem,noteItem);	
		
		mDynamicForm.setNumCols(10);
		
		addChild(mDynamicForm);
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
	}
	
	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}
		
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
	}
	
}