/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class UOCollegataItem extends CustomItem {

	public UOCollegataItem(JavaScriptObject jsObj) {
		super(jsObj);
		
	}

	public UOCollegataItem() {
		super();
	}
	
	@Override
	protected DynamicForm getFormToDraw() {
		DynamicForm lFormToDraw = new DynamicForm();
		lFormToDraw.setNumCols(12);
		lFormToDraw.setColWidths(1,1,1,1,1,1,1,1,1,1,1,1);
		lFormToDraw.setStopOnError(true);
		lFormToDraw.setMargin(-2);
		lFormToDraw.setOverflow(Overflow.VISIBLE);
		lFormToDraw.setWidth(10);
	//	lFormToDraw.setCellBorder(1);
		return lFormToDraw;
	}

	@Override
	public CustomItemFormField[] getFormFields() {
		
		CustomItemFormField idUo = new CustomItemFormField("idUo", this);
		idUo.setEditorType(new HiddenItem());
		
		CustomItemFormField codRapido = new CustomItemFormField("codRapido", this);
		codRapido.setEditorType(new HiddenItem());

		CustomItemFormField descrizione = new CustomItemFormField("descrizione", this);
		TextItem descrizioneEditorType = new TextItem();
		descrizioneEditorType.setCanEdit(false);
		descrizioneEditorType.setWidth(250);
		descrizione.setEditorType(descrizioneEditorType);
		
		CustomItemFormField clearButton = new CustomItemFormField("clear", this);
		ImgButtonItem clearButtonEditorType = new ImgButtonItem("clearButton", "buttons/clear.png", I18NUtil.getMessages().clearButton_title());
		clearButtonEditorType.setAlign(Alignment.CENTER);
		clearButtonEditorType.setAlwaysEnabled(true);
		clearButtonEditorType.setIconWidth(16);
		clearButtonEditorType.setIconHeight(16);
		clearButtonEditorType.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return _form.getValue("idUo") != null && !"".equals((String) _form.getValue("idUo"));
			}
		});
		clearButtonEditorType.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				_form.clearValue("idUo");
				_form.clearValue("codRapido");
				_form.clearValue("descrizione");
				_instance.storeValue(new Record(_form.getValues()));
				_form.markForRedraw();
			}
		});
		clearButton.setEditorType(clearButtonEditorType);		
		
		CustomItemFormField lookupOrganigramma = new CustomItemFormField("lookupOrganigramma", this);
		ImgButtonItem lookupOrganigrammaEditorType = new ImgButtonItem("lookupOrganigrammaButton", "buttons/lookup.png", I18NUtil.getMessages().selezionaButton_prompt());
		lookupOrganigrammaEditorType.setAlign(Alignment.CENTER);
		lookupOrganigrammaEditorType.setAlwaysEnabled(true);
		lookupOrganigrammaEditorType.setIconWidth(16);
		lookupOrganigrammaEditorType.setIconHeight(16);
		lookupOrganigrammaEditorType.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				OrganigrammaLookupOrganigramma lookupOrganigrammaPopup = new OrganigrammaLookupOrganigramma(null);
				lookupOrganigrammaPopup.show();				
			}
		});
		lookupOrganigramma.setEditorType(lookupOrganigrammaEditorType);
		
		CheckboxItem flgIncludiSottoUOEditorType = new CheckboxItem();
		flgIncludiSottoUOEditorType.setTitle("incluse U.O. subordinate");
		flgIncludiSottoUOEditorType.setWidth(50);
	
		CustomItemFormField flgIncludiSottoUO = new CustomItemFormField("flgIncludiSottoUO", this);
		flgIncludiSottoUO.setEditorType(flgIncludiSottoUOEditorType);

		SelectItem tipoRelazioneEditorType = new SelectItem();
		tipoRelazioneEditorType.setWidth(200);
		LinkedHashMap<String, String> tipoRelazioneValueMap = new LinkedHashMap<String, String>();
		tipoRelazioneValueMap.put("A","appartenenza gerarchica");
		tipoRelazioneValueMap.put("D", "funzionale");
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT")) {
			tipoRelazioneValueMap.put("P", "protocollista");
		}
		tipoRelazioneValueMap.put("L", "postazione ombra");
		tipoRelazioneEditorType.setValueMap(tipoRelazioneValueMap);
		tipoRelazioneEditorType.setDisplayField("value");
		tipoRelazioneEditorType.setValueField("key");
		tipoRelazioneEditorType.setWidth(250);
		tipoRelazioneEditorType.setAllowEmptyValue(false);
		tipoRelazioneEditorType.setMultiple(true);
		
		CustomItemFormField tipoRelazione = new CustomItemFormField("tipoRelazione", "da relazione di tipo", this);
		tipoRelazione.setEditorType(tipoRelazioneEditorType);
		
		// Spacer
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setColSpan(1);
		spacerItem.setWidth(10);
		CustomItemFormField spacer = new CustomItemFormField(spacerItem);
		
		return new CustomItemFormField[] {
				idUo, 
				codRapido,
				descrizione,
				clearButton,
				lookupOrganigramma, 
				flgIncludiSottoUO, 
				spacer, 
				tipoRelazione 
		};		
	}

	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		UOCollegataItem lFilterCanvasItem = new UOCollegataItem(jsObj);
		return lFilterCanvasItem;
	}

	public void setFormValuesFromRecord(Record record) {
		
		_form.clearValues();
		_form.setValue("idUo", record.getAttribute("idUoSvUt"));
		_form.setValue("codRapido", record.getAttribute("codRapidoUo"));		
		_form.setValue("descrizione", record.getAttribute("codRapidoUo") + " - " + record.getAttribute("descrUoSvUt"));
		_instance.storeValue(new Record(_form.getValues()));
		_form.markForRedraw();
	}	
	
	public class OrganigrammaLookupOrganigramma extends LookupOrganigrammaPopup {

		public OrganigrammaLookupOrganigramma(Record record) {
			super(record, true, new Integer(0));
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecord(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}

		public Boolean getFlgSoloAttive() {
			return null;
		}
	}
	
}
