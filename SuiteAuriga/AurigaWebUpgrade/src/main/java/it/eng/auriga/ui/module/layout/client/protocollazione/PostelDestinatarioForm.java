/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.i18n.client.Dictionary;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.events.HiddenValidationErrorsEvent;
import com.smartgwt.client.widgets.form.events.HiddenValidationErrorsHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;

import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class PostelDestinatarioForm extends DynamicForm{

	private PostelDestinatarioItem lDestinatarioItem;
	private ValuesManager lValuesManager;
	private Record lRecord;

	public PostelDestinatarioForm(Record recordDestinatari) {
		setOverflow(Overflow.AUTO);
		setWidth100();
		setHeight100();
		this.lRecord = recordDestinatari;
		setWrapItemTitles(false);
		setNumCols(20);
		setColWidths(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, "*", "*");
		
		createDestinatari(lRecord);
		
		lValuesManager = new ValuesManager();
		setValuesManager(lValuesManager);
		
		setFields(lDestinatarioItem);
	}
	
	private void createDestinatari(Record record) {
		// file attachment
		lDestinatarioItem = new PostelDestinatarioItem(record);
		lDestinatarioItem.setName("listaDestinatari");
		lDestinatarioItem.setTitle("Destinatari");
		lDestinatarioItem.setTitleColSpan(1);
		lDestinatarioItem.setWidth(1);
		lDestinatarioItem.setStartRow(true);
		lDestinatarioItem.setColSpan(8);
	}
	
	
	public String getApplicationName() {
		String applicationName = null;
		try {
			Dictionary dictionary = Dictionary.getDictionary("params");
			applicationName = dictionary != null && dictionary.get("applicationName") != null ? dictionary.get("applicationName") : "";
		} catch (Exception e) {
			applicationName = "";
		}
		return applicationName;
	}
	

	public Record getRecord() {
		if (validate()) {
			Record lRecord = new Record(lValuesManager.rememberValues());
			return lRecord;
		} else
			return null;
	}
	
	public ValuesManager getMapValues(){
		return lValuesManager;
	}
	
	@Override
	public void editNewRecord() {
		super.editNewRecord();
	}
	
	@Override
	public void editNewRecord(Map initialValues) {
		super.editNewRecord(initialValues);
	}
	
	@Override
	protected void onDestroy() {
		if (lValuesManager != null) {
			try {
				lValuesManager.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}
	
	boolean valid = false;
	Map<String, String> lMapErroriNascosti = null;

	public boolean validate() {
		lValuesManager.clearErrors(true);
		lValuesManager.addHiddenValidationErrorsHandler(new HiddenValidationErrorsHandler() {

			@Override
			public void onHiddenValidationErrors(HiddenValidationErrorsEvent event) {
				lMapErroriNascosti = event.getErrors();
			}
		});
		valid = true;
		lValuesManager.validate();
		Map<String, String> lMapErrori = lValuesManager.getErrors();
		if (lMapErrori != null) {
			for (String lString : lMapErrori.keySet()) {
				if (lMapErroriNascosti == null || !lMapErroriNascosti.containsKey(lString)) {
					valid = false;
				}
			}
		}
		for (DynamicForm form : lValuesManager.getMembers()) {
			for (FormItem item : form.getFields()) {
				if (item instanceof ReplicableItem) {
					ReplicableItem lReplicableItem = (ReplicableItem) item;
					if (lReplicableItem.isVisible())
						valid = lReplicableItem.validate() && valid;
				}
			}
		}
		return valid;
	}
	
	
}
