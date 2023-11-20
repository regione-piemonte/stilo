/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class ApposizioneTagCommentiMailCanvas extends ReplicableCanvas {
	
	protected ReplicableCanvasForm annotazioniLibereForm;
	protected SelectItem tagNotaLiberaSelectItem;
	protected CheckboxItem flgInibitaModCancItem;
	protected TextAreaItem notaLiberaTextAreaItem;
	protected HiddenItem flgCommentoObbligatorioTagItem;
	private static String errorMsgValidate = "Attenzione, occorre valorizzare il campo Note ";
	
	public ApposizioneTagCommentiMailCanvas(ApposizioneTagCommentiMailItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {

		preparaItemAnnotazioniLibereForm();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {

		return new ReplicableCanvasForm[] {annotazioniLibereForm};
	}
	
	private void preparaItemAnnotazioniLibereForm() {
		
		annotazioniLibereForm = new ReplicableCanvasForm();
		annotazioniLibereForm.setWrapItemTitles(false);
		annotazioniLibereForm.setNumCols(10);
		annotazioniLibereForm.setColWidths("1","1","1","1","1","1","1","1","*","*");
		
		flgCommentoObbligatorioTagItem = new HiddenItem("flgCommentoObbligatorioTag");

		GWTRestDataSource tagDS = new GWTRestDataSource("LoadComboTagBozzeMailDataSource", "key", FieldType.TEXT);
		tagDS.addParam("ignoraVociPerFiltro", "true");
//		tagDS.addParam("flgSoloVldIn", "true");

		tagNotaLiberaSelectItem = new SelectItem("itemLavTag", I18NUtil.getMessages().item_inlavorazione_itemLavTag()){

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				annotazioniLibereForm.setValue("flgCommentoObbligatorioTag", record.getAttribute("flgNoteObbl"));
				annotazioniLibereForm.markForRedraw();
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || value.equals("")) {
					annotazioniLibereForm.setValue("flgCommentoObbligatorioTag", "");
				}
				annotazioniLibereForm.markForRedraw();
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				annotazioniLibereForm.setValue("flgCommentoObbligatorioTag", "");
				annotazioniLibereForm.markForRedraw();
			};
		};
		tagNotaLiberaSelectItem.setStartRow(true);
		tagNotaLiberaSelectItem.setOptionDataSource(tagDS);
		tagNotaLiberaSelectItem.setDisplayField("value");
		tagNotaLiberaSelectItem.setValueField("key");
		tagNotaLiberaSelectItem.setWidth(350);
		tagNotaLiberaSelectItem.setWrapTitle(false);
		tagNotaLiberaSelectItem.setStartRow(true);
		tagNotaLiberaSelectItem.setAllowEmptyValue(false);
		tagNotaLiberaSelectItem.setAutoFetchData(false);
		tagNotaLiberaSelectItem.setColSpan(1);
		ListGridField keyField = new ListGridField("key", "Nome");
		keyField.setHidden(true);
		ListGridField valueField = new ListGridField("value");
		ListGridField flgNoteObblField = new ListGridField("flgNoteObbl");
		flgNoteObblField.setHidden(true);
		tagNotaLiberaSelectItem.setPickListFields(keyField, valueField, flgNoteObblField);
		final CustomValidator lCustomValidatorNota = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {

				boolean isValid = true;
				if (annotazioniLibereForm != null && annotazioniLibereForm.getValue("flgCommentoObbligatorioTag") != null
						&& annotazioniLibereForm.getValue("flgCommentoObbligatorioTag").equals("1")) {
					if (annotazioniLibereForm.getValue("itemLavCommento") == null || annotazioniLibereForm.getValue("itemLavCommento").equals("")
							|| annotazioniLibereForm.getValue("itemLavCommento").equals(" ")) {
						isValid = false;
					}
				}
				return isValid;	
			}
		};
		lCustomValidatorNota.setErrorMessage(errorMsgValidate);
		tagNotaLiberaSelectItem.setValidators(lCustomValidatorNota);
		
		flgInibitaModCancItem = new CheckboxItem("flgInibitaModificaCancellazione","Inibita modifica/cancellazione");
		flgInibitaModCancItem.setEndRow(true);

		notaLiberaTextAreaItem = new TextAreaItem("itemLavCommento", I18NUtil.getMessages().item_inlavorazione_itemLavCommento());
		notaLiberaTextAreaItem.setLength(4000);
		notaLiberaTextAreaItem.setStartRow(true);
		notaLiberaTextAreaItem.setHeight(40);
		notaLiberaTextAreaItem.setColSpan(7);
		notaLiberaTextAreaItem.setWidth(646);
		notaLiberaTextAreaItem.setEndRow(true);

		annotazioniLibereForm.setFields(flgCommentoObbligatorioTagItem,tagNotaLiberaSelectItem, flgInibitaModCancItem, notaLiberaTextAreaItem);
		annotazioniLibereForm.setNumCols(10);
		annotazioniLibereForm.setColWidths("1","1","1","1","1","1","1","1","*","*");
		
		addChild(annotazioniLibereForm);
	}
	
}