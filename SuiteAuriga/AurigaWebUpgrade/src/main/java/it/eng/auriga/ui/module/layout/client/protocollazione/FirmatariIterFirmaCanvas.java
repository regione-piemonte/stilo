/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.protocollazione;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class FirmatariIterFirmaCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;
	
	private NumericItem nroOrdineItem;

	private FilteredSelectItemWithDisplay idUtenteItem;
	private HiddenItem desUtenteItem;
	
	private RadioGroupItem tipoFirmaItem;
	
	private TextItem ruoloItem;
	
	private CheckboxItem flgAppostaItem;
	
	private DateTimeItem dataFirmaItem;
	
	public FirmatariIterFirmaCanvas(ReplicableItem item) {
		super(item);		
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		nroOrdineItem = new NumericItem("nroOrdine", "N° ordine di firma");
		nroOrdineItem.setRequired(true);
		nroOrdineItem.setKeyPressFilter("[0-9]");
		CustomValidator nroOrdineMaggioreDiZeroValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(value != null && !"".equals(value)) {
					Integer intValue = Integer.parseInt((String) value);
					return intValue > 0;
				}
				return true;
			}
		};
		nroOrdineMaggioreDiZeroValidator.setErrorMessage("Valore non valido: il N° ordine deve essere maggiore di zero");
		nroOrdineItem.setValidators(nroOrdineMaggioreDiZeroValidator);
		nroOrdineItem.setDefaultValue(getDefaultValueNroOrdine());
		
		SelectGWTRestDataSource utentiDS = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT,	new String[] {"cognomeNome"}, true);

		idUtenteItem = new FilteredSelectItemWithDisplay("idUtente", "Nominativo", utentiDS) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("desUtente", record.getAttributeAsString("cognomeNome"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("idUtente", "");
				mDynamicForm.setValue("desUtente", "");
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("idUtente", "");
					mDynamicForm.setValue("desUtente", "");
				}
			}
		};
		idUtenteItem.setAutoFetchData(false);
		idUtenteItem.setAlwaysFetchMissingValues(true);
		idUtenteItem.setFetchMissingValues(true);
		ListGridField utentiCodiceField = new ListGridField("codice", "Cod.");
		utentiCodiceField.setWidth(90);
		ListGridField utentiCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");//
		ListGridField utentiUsernameField = new ListGridField("username", "Username");
		utentiUsernameField.setWidth(90);
		idUtenteItem.setPickListFields(utentiCodiceField, utentiCognomeNomeField, utentiUsernameField);
		idUtenteItem.setPickListWidth(400);
		idUtenteItem.setFilterLocally(true);
		idUtenteItem.setValueField("idUtente");
		idUtenteItem.setOptionDataSource(utentiDS);
		idUtenteItem.setRequired(true);
		idUtenteItem.setClearable(true);
		idUtenteItem.setShowIcons(true);		
		if (AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			idUtenteItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		idUtenteItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});		
		
		desUtenteItem = new HiddenItem("desUtente");
		
		tipoFirmaItem = new RadioGroupItem("tipoFirma", "Tipo firma");
		tipoFirmaItem.setVertical(false);
		LinkedHashMap<String, String> tipoFirmaValueMap = new LinkedHashMap<String, String>();
		tipoFirmaValueMap.put("D", "digitale");
		tipoFirmaValueMap.put("E", "elettronica&nbsp;(visto)");
		tipoFirmaItem.setValueMap(tipoFirmaValueMap);
		tipoFirmaItem.setDefaultValue("D");
		tipoFirmaItem.setRequired(true);
		
		ruoloItem = new TextItem("ruolo", "Ruolo");
//		ruoloItem.setRequired(true);
		
		flgAppostaItem = new CheckboxItem("flgApposta", "apposta&nbsp;") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		flgAppostaItem.setWidth("*");
		
		dataFirmaItem = new DateTimeItem("dataFirma", "Data firma") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};

		mDynamicForm.setFields(
			nroOrdineItem,
			idUtenteItem, desUtenteItem,
			tipoFirmaItem,
			ruoloItem,
			flgAppostaItem,
			dataFirmaItem
		);

		mDynamicForm.setNumCols(15);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50");

		addChild(mDynamicForm);
	}
	
	private String getDefaultValueNroOrdine() {
		if(getItem() != null && getItem().getLastCanvas() != null) {
			String lastValue = (String) getItem().getLastCanvas().getForm()[0].getValue("nroOrdine");
			if(lastValue != null && !"".equals(lastValue)) {
				return "" + (Integer.parseInt((String) lastValue)+1);			
			}
		}
		return "1";
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

	@Override
	public void editRecord(Record record) {
		manageLoadSelectUtenteInEditRecord(record);
		super.editRecord(record);
	}
	
	public void manageLoadSelectUtenteInEditRecord(Record record) {
		if (record.getAttribute("idUtente") != null && !"".equals(record.getAttributeAsString("idUtente")) &&
				record.getAttribute("desUtente") != null && !"".equals(record.getAttributeAsString("desUtente")) ) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("idUtente"), record.getAttribute("desUtente"));
				idUtenteItem.setValueMap(valueMap);
			}

			SelectGWTRestDataSource utentiDS = (SelectGWTRestDataSource) idUtenteItem.getOptionDataSource();
			if (record.getAttribute("idUtente") != null && !"".equals(record.getAttributeAsString("idUtente"))) {
				utentiDS.addParam("idUtente", record.getAttributeAsString("idUtente"));
				utentiDS.addParam("desUtente", record.getAttributeAsString("desUtente"));
			} else {
				utentiDS.addParam("idUtente", null);
				utentiDS.addParam("desUtente", null);
			}
			idUtenteItem.setOptionDataSource(utentiDS);
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {

		super.setCanEdit(canEdit);
		
		boolean isFirmaApposta = flgAppostaItem.getValueAsBoolean() != null && flgAppostaItem.getValueAsBoolean();
		if(isFirmaApposta) {
			if (getRemoveButton() != null) {
				getRemoveButton().setAlwaysDisabled(true);
			}
			nroOrdineItem.setCanEdit(false);
			idUtenteItem.setCanEdit(false);
			tipoFirmaItem.setCanEdit(false);
			ruoloItem.setCanEdit(false);
		}
		flgAppostaItem.setCanEdit(false);
		dataFirmaItem.setCanEdit(false);
	}

}
