/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;

public class PeriziaCanvas extends ReplicableCanvas {

	private FilteredSelectItemWithDisplay periziaItem;
	private ExtendedTextItem codiceRapidoItem;
	private HiddenItem codiceHiddenItem;
	private HiddenItem descrizioneHiddenItem;
	
	private ReplicableCanvasForm mDynamicForm;

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		codiceHiddenItem       = new HiddenItem("codice");
		descrizioneHiddenItem  = new HiddenItem("descrizione");

		// Codice rapido
		codiceRapidoItem = new ExtendedTextItem("codiceRapido", I18NUtil.getMessages().perizia_codiceRapitoItem_title());
		codiceRapidoItem.setRequired(false);
		codiceRapidoItem.setWidth(120);
		codiceRapidoItem.setColSpan(1);
		codiceRapidoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("codice", (String) null);
				mDynamicForm.setValue("descrizione", (String) null);
				mDynamicForm.setValue("perizia", (String) null);
				mDynamicForm.clearErrors(true);
				final String value = codiceRapidoItem.getValueAsString();
				if (value != null && !"".equals(value)) {
					periziaItem.fetchData(new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							RecordList data = response.getDataAsRecordList();
							boolean trovato = false;
							if (data.getLength() > 0) {
								for (int i = 0; i < data.getLength(); i++) {
									String codice = data.get(i).getAttribute("codice");
									if (value.equals(codice)) {
										mDynamicForm.setValue("codice", data.get(i).getAttribute("codice"));
										mDynamicForm.setValue("descrizione", data.get(i).getAttribute("descrizione"));
										mDynamicForm.setValue("perizia", data.get(i).getAttribute("codice"));
										mDynamicForm.clearErrors(true);
										trovato = true;
										break;
									}
								}
							}
							if (!trovato) {
								codiceRapidoItem.validate();
								codiceRapidoItem.blurItem();
							}
						}
					});
				} else {
					periziaItem.fetchData();
				}
			}
		});
		
		codiceRapidoItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (codiceRapidoItem.getValue() != null && !"".equals(codiceRapidoItem.getValueAsString().trim()) && periziaItem.getValue() == null) {
					return false;
				}
				return true;
			}
		});
		
		// combo perizie
		SelectGWTRestDataSource perizieDS = new SelectGWTRestDataSource("LoadComboPerizieDataSource", "codice", FieldType.TEXT, new String[] { "descrizione" }, true);
		
		periziaItem = new FilteredSelectItemWithDisplay("perizia", perizieDS) {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.clearErrors(true);
				mDynamicForm.setValue("codiceRapido",  record.getAttribute("codice"));
				mDynamicForm.setValue("codice",        record.getAttribute("codice"));
				mDynamicForm.setValue("descrizione",   record.getAttribute("descrizione"));
				//markForRedraw();
				mDynamicForm.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					
					@Override
					public void execute() {
						periziaItem.fetchData();
					}
				});
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("codiceRapido", "");
				mDynamicForm.setValue("codice", "");
				mDynamicForm.setValue("descrizione",  "");
				//markForRedraw();
				mDynamicForm.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					
					@Override
					public void execute() {
						periziaItem.fetchData();
					}
				});
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("codiceRapido", "");
					mDynamicForm.setValue("codice", "");
					mDynamicForm.setValue("descrizione",  "");
					
					mDynamicForm.clearErrors(true);
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						
						@Override
						public void execute() {
							periziaItem.fetchData();
						}
					});
				}
            }
		};
		
		periziaItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		periziaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return true;
			}
		});
		periziaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return true;
			}
		}));
		
		// Definizione delle colonne
		
		// CODICE
		ListGridField codiceField = new ListGridField("codice", I18NUtil.getMessages().perizia_combo_codiceField_title());
		codiceField.setWidth(120);
		codiceField.setShowHover(true);
		
		// DESCRIZIONE
		ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().perizia_combo_descrizioneField_title());
		descrizioneField.setWidth("*");
		
		List<ListGridField> periziaPickListFields = new ArrayList<ListGridField>();
		periziaPickListFields.add(codiceField);
		periziaPickListFields.add(descrizioneField);
		
		// Aggiungo le colonne
		periziaItem.setPickListFields(periziaPickListFields.toArray(new ListGridField[periziaPickListFields.size()]));
		
		ListGrid pickListProperties = periziaItem.getPickListProperties();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {
			@Override
			public void onFilterData(FetchDataEvent event) {
				String codiceRapido = mDynamicForm.getValueAsString("codiceRapido");
				GWTRestDataSource periziaDS = (GWTRestDataSource) periziaItem.getOptionDataSource();
				periziaDS.addParam("codice", codiceRapido);				
				periziaItem.setOptionDataSource(periziaDS);
				periziaItem.invalidateDisplayValueCache();
			}
		});
		periziaItem.setPickListProperties(pickListProperties);
		
		periziaItem.setFilterLocally(true);
		periziaItem.setAutoFetchData(false);
		periziaItem.setAlwaysFetchMissingValues(true);
		periziaItem.setFetchMissingValues(true);
		periziaItem.setValueField("codice");
		periziaItem.setOptionDataSource(perizieDS);
		periziaItem.setShowTitle(false);
		periziaItem.setWidth(480);
		periziaItem.setClearable(true);
		periziaItem.setShowIcons(true);
		periziaItem.setRequired(false);

		mDynamicForm.setFields(codiceRapidoItem, periziaItem , codiceHiddenItem, descrizioneHiddenItem);

		mDynamicForm.setNumCols(6);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100");

		addChild(mDynamicForm);
	}

	@Override
	public void editRecord(Record record) {
		GWTRestDataSource perizieDS = (GWTRestDataSource) periziaItem.getOptionDataSource();
		if (record.getAttribute("perizia") != null && !"".equals(record.getAttributeAsString("perizia"))) {
			perizieDS.addParam("perizia", record.getAttributeAsString("perizia"));
		} else {
			perizieDS.addParam("perizia", null);
		}
		periziaItem.setOptionDataSource(perizieDS);
		
		
		if (record.getAttribute("perizia") != null && !"".equals(record.getAttributeAsString("perizia")) &&
				record.getAttribute("descrizione") != null && !"".equals(record.getAttributeAsString("descrizione"))) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("perizia"), record.getAttribute("descrizione"));
				periziaItem.setValueMap(valueMap);
			}

		super.editRecord(record);
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}
}