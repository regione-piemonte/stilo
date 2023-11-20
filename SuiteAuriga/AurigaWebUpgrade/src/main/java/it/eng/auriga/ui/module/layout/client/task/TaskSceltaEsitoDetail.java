/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

public class TaskSceltaEsitoDetail extends TaskDetail {

	protected DynamicForm noteForm;
	protected TextAreaItem noteItem;

	protected DynamicForm esitoForm;
	protected SelectItem esitoItem;

	protected DetailSection detailSectionNote;
	protected DetailSection detailSectionEsito;

	public TaskSceltaEsitoDetail(String nomeEntita) {

		super(nomeEntita, null, null, null, null, null);

	}

	@Override
	public void addDetailMembers() {

		// Note
		noteForm = new DynamicForm();
		noteForm.setValuesManager(vm);
		noteForm.setWidth100();
		noteForm.setPadding(5);
		noteForm.setWrapItemTitles(false);
		noteForm.setNumCols(8);
		noteForm.setColWidths(1, 1, 1, 1, 1, 1, "*", "*");

		noteItem = new TextAreaItem("note");
		noteItem.setShowTitle(false);
		noteItem.setHeight(40);
		noteItem.setWidth(650);
		noteItem.setStartRow(true);
		noteItem.setColSpan(5);

		noteForm.setItems(noteItem);

		esitoForm = new DynamicForm();
		esitoForm.setValuesManager(vm);
		esitoForm.setWidth100();
		esitoForm.setPadding(5);
		esitoForm.setWrapItemTitles(false);
		esitoForm.setNumCols(8);
		esitoForm.setColWidths(1, 1, 1, 1, 1, 1, "*", "*");

		esitoItem = new SelectItem("esito");
		esitoItem.setShowTitle(false);
		esitoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return detailSectionEsito.isVisible();
			}
		}));

		esitoForm.setItems(esitoItem);

		detailSectionNote = new DetailSection("Messaggio", true, true, false, noteForm);
		addMember(detailSectionNote);

		detailSectionEsito = new DetailSection("Esito", true, true, false, esitoForm);
		detailSectionEsito.setVisible(false);
		addMember(detailSectionEsito);
	}

	@Override
	public void editRecord(Record record) {
		
		LinkedHashMap<String, String> esitoValueMap = new LinkedHashMap<String, String>();
		if (record.getAttributeAsMap("valoriEsito") != null) {
			final Iterator iterator = record.getAttributeAsMap("valoriEsito").keySet().iterator();
			while (iterator.hasNext()) {
				final String key = (String) iterator.next();
				esitoValueMap.put(key, (String) record.getAttributeAsMap("valoriEsito").get(key));
			}
		}
		esitoItem.setValueMap(esitoValueMap);
		super.editRecord(record);
		detailSectionNote.setVisible(false);
		detailSectionEsito.setVisible(record.getAttributeAsBoolean("flgPresenzaEsito") != null && record.getAttributeAsBoolean("flgPresenzaEsito"));
	}

	@Override
	public void editNewRecord(Map initialValues) {
		
		Record record = new Record(initialValues);
		LinkedHashMap<String, String> esitoValueMap = new LinkedHashMap<String, String>();
		if (record.getAttributeAsMap("valoriEsito") != null) {
			final Iterator iterator = record.getAttributeAsMap("valoriEsito").keySet().iterator();
			while (iterator.hasNext()) {
				final String key = (String) iterator.next();
				esitoValueMap.put(key, (String) record.getAttributeAsMap("valoriEsito").get(key));
			}
		}
		esitoItem.setValueMap(esitoValueMap);
		super.editNewRecord(initialValues);
		detailSectionNote.setVisible(true);
		detailSectionEsito.setVisible(record.getAttributeAsBoolean("flgPresenzaEsito") != null && record.getAttributeAsBoolean("flgPresenzaEsito"));
	}

}
