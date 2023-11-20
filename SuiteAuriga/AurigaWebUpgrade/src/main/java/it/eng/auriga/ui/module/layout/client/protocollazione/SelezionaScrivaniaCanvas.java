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
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public class SelezionaScrivaniaCanvas extends ReplicableCanvas {

	private HiddenItem idScrivaniaHiddenItem;
	private ExtendedTextItem codUoScrivaniaItem;
	private HiddenItem descrizioneHiddenItem;
	private FilteredSelectItemWithDisplay organigrammaItem;
	private HiddenItem organigrammaFromLoadDettHiddenItem;
	private ReplicableCanvasForm mDynamicForm;

	public SelezionaScrivaniaCanvas(SelezionaScrivaniaItem item) {
		super(item);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		idScrivaniaHiddenItem = new HiddenItem("idScrivania");

		codUoScrivaniaItem = new ExtendedTextItem("codUoScrivania", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codUoScrivaniaItem.setWidth(120);
		codUoScrivaniaItem.setColSpan(1);
		codUoScrivaniaItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("idScrivania", (String) null);
				mDynamicForm.setValue("descrizione", (String) null);
				mDynamicForm.setValue("organigramma", (String) null);
				mDynamicForm.clearErrors(true);
				final String value = codUoScrivaniaItem.getValueAsString();
				if (value != null && !"".equals(value)) {
					organigrammaItem.fetchData(new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							RecordList data = response.getDataAsRecordList();
							boolean trovato = false;
							if (data.getLength() > 0) {
								for (int i = 0; i < data.getLength(); i++) {
									String codice = data.get(i).getAttribute("codice");
									String flgSelXFinalita = data.get(i).getAttribute("flgSelXFinalita");
									if (value.equals(codice) && (flgSelXFinalita == null || "1".equals(flgSelXFinalita))) {
										mDynamicForm.setValue("descrizione", data.get(i).getAttribute("descrizioneOrig"));
										mDynamicForm.setValue("organigramma", data.get(i).getAttribute("id"));
										mDynamicForm.setValue("idScrivania", data.get(i).getAttribute("idUo"));
										mDynamicForm.clearErrors(true);
										trovato = true;
										break;
									}
								}
							}
							if (!trovato) {
								codUoScrivaniaItem.validate();
								codUoScrivaniaItem.blurItem();
							}
							((SelezionaScrivaniaItem)getItem()).manageChangedScrivaniaSelezionata();
							
						}
					});
				} else {
					organigrammaItem.fetchData();
					((SelezionaScrivaniaItem)getItem()).manageChangedScrivaniaSelezionata();
				}
			}
		});
		codUoScrivaniaItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (codUoScrivaniaItem.getValue() != null && !"".equals(codUoScrivaniaItem.getValueAsString().trim()) && organigrammaItem.getValue() == null) {
					return false;
				}
				return true;
			}
		});

		descrizioneHiddenItem = new HiddenItem("descrizione");

		SelectGWTRestDataSource organigrammaDS = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT,
				new String[] { "descrizione" }, true);
		organigrammaDS.addParam("tipoAssegnatari", "SV");
		organigrammaDS.addParam("finalita", ((SelezionaScrivaniaItem) getItem()).getFinalitaSelect());
		organigrammaDS.addParam("flgSoloValide", ((SelezionaScrivaniaItem) getItem()).getFlgSoloValide());
		if(((SelezionaScrivaniaItem)getItem()).getAltriParamLoadCombo() != null) {
			organigrammaDS.addParam("altriParamLoadCombo", ((SelezionaScrivaniaItem)getItem()).getAltriParamLoadCombo());
		}
		organigrammaItem = new FilteredSelectItemWithDisplay("organigramma", organigrammaDS) {
			
			@Override
			public void manageOnEnterPress(BodyKeyPressEvent event, Record record) {
				String flgSelXFinalita = record.getAttributeAsString("flgSelXFinalita");
				if (flgSelXFinalita == null || "1".equals(flgSelXFinalita)) {
					onOptionClick(record);
				} else {
					event.cancel();
					Layout.addMessage(new MessageBean("Valore non selezionabile", "", MessageType.ERROR));
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						
						@Override
						public void execute() {
							clearSelect();
						}
					});
				}
			}

			@Override
			public void manageOnCellClick(CellClickEvent event) {
				String flgSelXFinalita = event.getRecord().getAttributeAsString("flgSelXFinalita");
				if (flgSelXFinalita == null || "1".equals(flgSelXFinalita)) {
					onOptionClick(event.getRecord());
				} else {
					event.cancel();
					Layout.addMessage(new MessageBean("Valore non selezionabile", "", MessageType.ERROR));
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							clearSelect();
						}
					});
				}
			}

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
//				mDynamicForm.setValue("organigramma", record.getAttributeAsString("id"));
				mDynamicForm.setValue("codUoScrivania", record.getAttributeAsString("codice"));
				mDynamicForm.setValue("idScrivania", record.getAttributeAsString("idUo"));
				mDynamicForm.setValue("descrizione", record.getAttributeAsString("descrizioneOrig"));
				mDynamicForm.clearErrors(true);			
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaItem.fetchData();
						((SelezionaScrivaniaItem)getItem()).manageChangedScrivaniaSelezionata();
					}
				});
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("organigramma", "");
				if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
					mDynamicForm.setValue("codUoScrivania", AurigaLayout.getCodRapidoOrganigramma());
				} else {
					mDynamicForm.setValue("codUoScrivania", "");
				}
				mDynamicForm.setValue("idScrivania", "");
				mDynamicForm.setValue("descrizione", "");
				mDynamicForm.clearErrors(true);				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaItem.fetchData();
						((SelezionaScrivaniaItem)getItem()).manageChangedScrivaniaSelezionata();
					}
				});
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("organigramma", "");
					if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
						mDynamicForm.setValue("codUoScrivania", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						mDynamicForm.setValue("codUoScrivania", "");
					}
					mDynamicForm.setValue("idScrivania", "");
					mDynamicForm.setValue("descrizione", "");
					mDynamicForm.clearErrors(true);						
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							organigrammaItem.fetchData();
							((SelezionaScrivaniaItem)getItem()).manageChangedScrivaniaSelezionata();
						}
					});
				}
			}
		};
		List<ListGridField> organigrammaPickListFields = new ArrayList<ListGridField>();
		ListGridField codiceField = new ListGridField("codice", I18NUtil.getMessages().organigramma_list_codUoField_title());
		codiceField.setWidth(120);
		codiceField.setShowHover(true);
		codiceField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute("descrizioneEstesa");
			}
		});
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			codiceField.setCanFilter(false);
			// TextItem codiceFilterEditorType = new TextItem();
			// codiceFilterEditorType.setCanEdit(false);
			// codiceField.setFilterEditorType(codiceFilterEditorType);
		}
		organigrammaPickListFields.add(codiceField);
		ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().organigramma_list_descrizioneField_title());
		descrizioneField.setWidth("*");
		organigrammaPickListFields.add(descrizioneField);		
		organigrammaItem.setPickListFields(organigrammaPickListFields.toArray(new ListGridField[organigrammaPickListFields.size()]));
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			organigrammaItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimOrganigrammaNonStdMessage());			
		} else {
			organigrammaItem.setFilterLocally(true);
		}
		organigrammaItem.setAutoFetchData(false);
		organigrammaItem.setAlwaysFetchMissingValues(true);
		organigrammaItem.setFetchMissingValues(true);
		organigrammaItem.setValueField("id");
		organigrammaItem.setOptionDataSource(organigrammaDS);
		organigrammaItem.setShowTitle(false);
		organigrammaItem.setWidth(((SelezionaScrivaniaItem)getItem()).getSelectItemOrganigrammaWidth());
		organigrammaItem.setClearable(true);
		organigrammaItem.setShowIcons(true);
		organigrammaItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
			}
		});
		organigrammaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				Boolean notReplicable = getItem().getNotReplicable() != null ? getItem().getNotReplicable() : false;
				Boolean obbligatorio = getItem().getAttributeAsBoolean("obbligatorio") != null ? getItem().getAttributeAsBoolean("obbligatorio") : false;
				return !notReplicable || obbligatorio;
			}
		}));

		ListGrid pickListProperties = organigrammaItem.getPickListProperties();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String codUoScrivania = mDynamicForm.getValueAsString("codUoScrivania");
//				if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && (codUoScrivania == null || "".equals(codUoScrivania))) {
//					if(codUoScrivaniaItem.getCanEdit() != null && codUoScrivaniaItem.getCanEdit()) {
//						mDynamicForm.setFieldErrors("codUoScrivania", "Filtro obbligatorio per popolare la lista di scelta");
//					}
//				}				
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
				organigrammaDS.addParam("uoProponente", ((SelezionaScrivaniaItem)getItem()).getUoProponenteCorrente());
				organigrammaDS.addParam("codice", codUoScrivania);
//				organigrammaDS.addParam("ciToAdd", (String) organigrammaFromLoadDettHiddenItem.getValue());
				organigrammaItem.setOptionDataSource(organigrammaDS);
				organigrammaItem.invalidateDisplayValueCache();				
			}
		});
		organigrammaItem.setPickListProperties(pickListProperties);
		
		organigrammaFromLoadDettHiddenItem = new HiddenItem("organigrammaFromLoadDett");

		ImgButtonItem lookupOrganigrammaButton = new ImgButtonItem("lookupOrganigrammaButton", "lookup/organigramma.png",
				I18NUtil.getMessages().protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaButton.setColSpan(1);
		lookupOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				SelezionaScrivaniaLookupOrganigramma lookupOrganigrammaPopup = new SelezionaScrivaniaLookupOrganigramma(null);
				lookupOrganigrammaPopup.show();
			}
		});
		
		lookupOrganigrammaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((SelezionaScrivaniaItem)getItem()).showLookupOrganigrammaButton();
			}
		});
		
		List<FormItem> items = new ArrayList<FormItem>();
		
		items.add(idScrivaniaHiddenItem);
		items.add(codUoScrivaniaItem);
		items.add(descrizioneHiddenItem);
		items.add(lookupOrganigrammaButton);
		items.add(organigrammaItem);
		items.add(organigrammaFromLoadDettHiddenItem);
		items.addAll(((SelezionaScrivaniaItem)getItem()).getCustomItems());

		mDynamicForm.setFields(items.toArray(new FormItem[items.size()]));

		mDynamicForm.setNumCols(16);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");

		addChild(mDynamicForm);

	}
	
	public void resetAfterChangedParams() {
		final String value = organigrammaItem.getValueAsString();
		organigrammaItem.fetchData(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				RecordList data = response.getDataAsRecordList();
				if(value != null && !"".equals(value)) {					
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String idSv = data.get(i).getAttribute("id");
							if (value.equals(idSv)) {
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						mDynamicForm.setValue("organigramma", "");
						if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
							mDynamicForm.setValue("codUoScrivania", AurigaLayout.getCodRapidoOrganigramma());
						} else {
							mDynamicForm.setValue("codUoScrivania", "");
						}
						mDynamicForm.setValue("idScrivania", "");
						mDynamicForm.setValue("descrizione", "");
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
							@Override
							public void execute() {
								organigrammaItem.fetchData();
								((SelezionaScrivaniaItem)getItem()).manageChangedScrivaniaSelezionata();
							}
						});
					}
				}
			}
		});
	}
	
	@Override
	public void editRecord(Record record) {
		mDynamicForm.clearErrors(true);
		GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
		if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma"))) {
			organigrammaDS.addParam("idUoSv", record.getAttributeAsString("idScrivania"));
			organigrammaDS.addParam("flgUoSv", "SV");
		} else {
			organigrammaDS.addParam("idUoSv", null);
			organigrammaDS.addParam("flgUoSv", null);
		}
		organigrammaItem.setOptionDataSource(organigrammaDS);
		if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma"))) {
			if (record.getAttribute("descrizione") != null && !"".equals(record.getAttributeAsString("descrizione"))) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("organigramma"), record.getAttribute("descrizione"));
				organigrammaItem.setValueMap(valueMap);
			}
			organigrammaItem.setValue(record.getAttribute("organigramma"));
		}
		super.editRecord(record);
	}
	
	/* DA TOGLIERE PERCHE' CREA PROBLEMI QUANDO RICARICO IL DETTAGLIO (NON MOSTRA IL VALORE SALVATO)
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		if(getEditing() && (codUoScrivaniaItem.getValue() == null || "".equals(codUoScrivaniaItem.getValue()))) {
			if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
				mDynamicForm.setValue("codUoScrivania", AurigaLayout.getCodRapidoOrganigramma());
			} else {
				mDynamicForm.setValue("codUoScrivania", "");
			}			
		}
	}
	*/

	public void setFormValuesFromRecord(Record record) {
		String idOrganigramma = record.getAttribute("idUoSvUt");
		// if(idOrganigramma == null || "".equals(idOrganigramma)) {
		// idOrganigramma = record.getAttribute("idFolder");
		// }
		String tipo = record.getAttribute("tipo");
		int pos = tipo.indexOf("_");
		if (pos != -1) {
			tipo = tipo.substring(0, pos);
		}
		mDynamicForm.setValue("organigramma", tipo + idOrganigramma);
		mDynamicForm.setValue("idScrivania", idOrganigramma);
		// mDynamicForm.setValue("descrizione", ""); // da settare
		mDynamicForm.setValue("codUoScrivania", record.getAttribute("codRapidoUo"));
		mDynamicForm.clearErrors(true);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				organigrammaItem.fetchData();
				((SelezionaScrivaniaItem)getItem()).manageChangedScrivaniaSelezionata();
			}
		});

	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}
	
	public class SelezionaScrivaniaLookupOrganigramma extends LookupOrganigrammaPopup {

		public SelezionaScrivaniaLookupOrganigramma(Record record) {
			super(record, true, 2);
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

		@Override
		public String getFinalita() {
			return ((SelezionaScrivaniaItem) getItem()).getFinalitaLookup();
		}

	}
}