/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

/**
 * 
 * @author dbe4235
 *
 */

public class PrefIterFirmeBozzeCanvas extends ReplicableCanvas {
	
	private ReplicableCanvasForm mDynamicForm;
	
	protected FilteredSelectItemWithDisplay utentiItem;
	protected SelectItem tipologiaDocumentaleItem;

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		SelectGWTRestDataSource utentiDS = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT,	new String[] {"cognomeNome"}, true);
		utentiItem = new FilteredSelectItemWithDisplay("idUtente", utentiDS) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("codiceRapido", record.getAttributeAsString("codice"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("idUtente", "");
				mDynamicForm.setValue("codiceRapido", "");
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("idUtente", "");
					mDynamicForm.setValue("codiceRapido", "");
				}
			}
		};
		utentiItem.setAutoFetchData(false);
		utentiItem.setAlwaysFetchMissingValues(true);
		utentiItem.setFetchMissingValues(true);

		ListGridField utentiCodiceField = new ListGridField("codice", "Cod.");
		utentiCodiceField.setWidth(90);
		ListGridField utentiCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");
		ListGridField utentiUsernameField = new ListGridField("username", "Username");
		utentiUsernameField.setWidth(90);
		utentiItem.setPickListFields(utentiCodiceField, utentiCognomeNomeField, utentiUsernameField);
		utentiItem.setFilterLocally(true);
		utentiItem.setValueField("idUtente");
		utentiItem.setOptionDataSource(utentiDS);
		utentiItem.setTitle("Nominativo");
		utentiItem.setWidth(500);
		utentiItem.setRequired(true);
		utentiItem.setClearable(true);
		utentiItem.setShowIcons(true);		
		if (AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			utentiItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		utentiItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});
		
		final GWTRestDataSource tipoDocTrattTipiDS = new GWTRestDataSource("LoadTipoDocumentaleDataSource", "key", FieldType.TEXT, true);
		tipologiaDocumentaleItem = new SelectItem("tipologiaDocAss", "Solo per la/le tipologie documentali");
		tipologiaDocumentaleItem.setColSpan(4);
		tipologiaDocumentaleItem.setWidth(500);
		tipologiaDocumentaleItem.setValueField("key");
		tipologiaDocumentaleItem.setDisplayField("value");
		tipologiaDocumentaleItem.setMultiple(true);
		tipologiaDocumentaleItem.setOptionDataSource(tipoDocTrattTipiDS);
		tipologiaDocumentaleItem.setClearable(true);
		tipologiaDocumentaleItem.setStartRow(true);
		tipologiaDocumentaleItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		mDynamicForm.setFields(utentiItem,tipologiaDocumentaleItem);		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}

}
