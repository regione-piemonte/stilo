/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredComboBoxItem;

public class SelezionaUtenteCanvas extends ReplicableCanvas {
	
	private HiddenItem idUserSoggettoHiddenItem;
	private HiddenItem cognomeHiddenItem;
	private HiddenItem nomeHiddenItem;
	private HiddenItem denominazioneHiddenItem;
	private ExtendedTextItem codRapidoItem;
	private FilteredComboBoxItem utenteItem;	

	private ReplicableCanvasForm mDynamicForm;
	
	public SelezionaUtenteCanvas(SelezionaUtenteItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {		
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		idUserSoggettoHiddenItem = new HiddenItem("idUserSoggetto");
		cognomeHiddenItem = new HiddenItem("cognome");
		nomeHiddenItem = new HiddenItem("nome");
		denominazioneHiddenItem = new HiddenItem("denominazione");
		
		codRapidoItem = new ExtendedTextItem("codRapido", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoItem.setWidth(120);
		codRapidoItem.setColSpan(1);
		codRapidoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("idUserSoggetto", (String) null);
				mDynamicForm.setValue("denominazione", (String) null);
				mDynamicForm.clearErrors(true);
				final String value = codRapidoItem.getValueAsString();
				if (value != null && !"".equals(value)) {
					Criterion[] criterias = new Criterion[1];
					criterias[0] = new Criterion("codice", OperatorId.IEQUALS, value);

					SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT, new String[]{"cognomeNome"}, true);
					lGwtRestDataSource.addParam("finalita", null);
					lGwtRestDataSource.fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							RecordList data = response.getDataAsRecordList();
							boolean trovato = false;
							if (data.getLength() > 0) {
								for (int i = 0; i < data.getLength(); i++) {
									String codice = data.get(i).getAttribute("codice");
									if (value.equals(codice)) {
										mDynamicForm.setValue("idUserSoggetto", data.get(i).getAttribute("idUtente"));
										mDynamicForm.setValue("denominazione", data.get(i).getAttribute("cognomeNome"));
										trovato = true;
										break;
									}
								}
							}
							if (!trovato) {
								codRapidoItem.validate();
								codRapidoItem.blurItem();
							}
						}
					});
				}
			}
		});
		codRapidoItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (codRapidoItem.getValue() != null && !"".equals(codRapidoItem.getValueAsString().trim()) && utenteItem.getValue() == null) {
					return false;
				}
				return true;
			}
		});
				
		SelectGWTRestDataSource lGwtRestDataSourceUtenti = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT, new String[]{"cognomeNome"}, true);
		lGwtRestDataSourceUtenti.addParam("finalita", null);
		
		utenteItem = new FilteredComboBoxItem("denominazione", "Cognome e nome") {
					
			@Override
			public void onOptionClick(Record record) {
				mDynamicForm.setValue("idUserSoggetto", record.getAttribute("idUtente"));
				mDynamicForm.setValue("denominazione", record.getAttribute("cognomeNome"));
				mDynamicForm.setValue("codRapido", record.getAttribute("codice"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
			};
		};
		utenteItem.setAutoFetchData(false);
		utenteItem.setFetchMissingValues(true);
		utenteItem.setAddUnknownValues(true);
		
		ListGridField utentiCodiceField = new ListGridField("codice", "Cod.");
		utentiCodiceField.setWidth(80);		
		ListGridField utentiCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");//		
		utenteItem.setPickListFields(utentiCognomeNomeField);	
		utenteItem.setValueField("idUtente");
		utenteItem.setDisplayField("cognomeNome");
		utenteItem.setOptionDataSource(lGwtRestDataSourceUtenti); 
		if (getItem().getAttributeAsBoolean("obbligatorio") != null && getItem().getAttributeAsBoolean("obbligatorio")){
			utenteItem.setAttribute("obbligatorio", true);
		}
		utenteItem.setWidth(300);
		utenteItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				String denominazione = (String) utenteItem.getValue();
				if(denominazione == null || "".equals(denominazione)) {
					denominazione = (String) utenteItem.getValue();
				}
				boolean nomeECognomePresenti = denominazione != null ? denominazione.split(" ").length > 1 : false;
				Boolean obbligatorio = getItem().getAttributeAsBoolean("obbligatorio") != null ? getItem().getAttributeAsBoolean("obbligatorio") : false;
				if (obbligatorio){
					return (denominazione != null) && (!"".equalsIgnoreCase(denominazione)) && nomeECognomePresenti;
				} else {
					return (denominazione == null) || ("".equalsIgnoreCase(denominazione)) || nomeECognomePresenti;
				}
//				return true;
			}
		});
								
		mDynamicForm.setFields(idUserSoggettoHiddenItem, cognomeHiddenItem, nomeHiddenItem, denominazioneHiddenItem, codRapidoItem, utenteItem);
				
		mDynamicForm.setNumCols(16);
		mDynamicForm.setColWidths("50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100");
		
		addChild(mDynamicForm);

	}	
		
	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
}
