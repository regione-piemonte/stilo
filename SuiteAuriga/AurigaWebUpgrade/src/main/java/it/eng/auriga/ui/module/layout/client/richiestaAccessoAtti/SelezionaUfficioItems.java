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
import com.smartgwt.client.types.Alignment;
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
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;


//*********** NOTA IMPORTANTE ***********
//***************************************
// CHIAMARE afterEditRecord NELL'editRecord del del dettaglio in cui viene usato il componente
//***************************************
//***************************************

public class SelezionaUfficioItems extends ArrayList<FormItem> {
	
	private String idUoHiddenItemName;
	private String descrizioneHiddenItemName;
	private String codRapidoItemName;
	private String organigrammaItemName;
	
	private HiddenItem idUoHiddenItem;
	private ExtendedTextItem codRapidoItem;
	private HiddenItem descrizioneHiddenItem;
	private FilteredSelectItemWithDisplay organigrammaItem;
	
	private DynamicForm mDynamicForm;

	public SelezionaUfficioItems(DynamicForm pDynamicForm, final String pIdUoHiddenItemName, final String pDescrizioneHiddenItemName, final String pCodRapidoItemName, final String pOrganigrammaItemName) {		
		
		mDynamicForm = pDynamicForm;
		idUoHiddenItemName = pIdUoHiddenItemName;
		descrizioneHiddenItemName = pDescrizioneHiddenItemName;
		codRapidoItemName = pCodRapidoItemName;
		organigrammaItemName = pOrganigrammaItemName;
		
		buildItems();
	}	
	
	public void buildItems() {
		
		clear();
		
		idUoHiddenItem = new HiddenItem(idUoHiddenItemName);
		descrizioneHiddenItem = new HiddenItem(descrizioneHiddenItemName);
		
		codRapidoItem = new ExtendedTextItem(codRapidoItemName, I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoItem.setWidth(120);
		codRapidoItem.setColSpan(1);
		codRapidoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue(idUoHiddenItemName, (String) null);
				mDynamicForm.setValue(descrizioneHiddenItemName, (String) null);
				mDynamicForm.setValue(organigrammaItemName, (String) null);
				mDynamicForm.clearErrors(true);
				final String value = codRapidoItem.getValueAsString();
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
										mDynamicForm.setValue(descrizioneHiddenItemName, data.get(i).getAttribute("descrizioneOrig"));
										mDynamicForm.setValue(organigrammaItemName, data.get(i).getAttribute("id"));
										mDynamicForm.setValue(idUoHiddenItemName, data.get(i).getAttribute("idUo"));
										mDynamicForm.clearErrors(true);
										trovato = true;
										break;
									}
								}
							}
							if (!trovato) {
								codRapidoItem.validate();
								codRapidoItem.blurItem();
							}
							manageChangedUo();							
						}
					});
				} else {
					organigrammaItem.fetchData();
					manageChangedUo();
				}
			}
		});
		codRapidoItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				return codRapidoItemValidator();
			}
		});
		
		codRapidoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return codRapidoItemShowIfCondition();
			}
		});
		
		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT,
				new String[] { "descrizione" }, true);
		lGwtRestDataSource.addParam("tipoAssegnatari", getTipoAssegnatari());
		lGwtRestDataSource.addParam("finalita", getFinalitaOrganigramma());
		lGwtRestDataSource.addParam("flgSoloValide", getFlgSoloValide());
		organigrammaItem = new FilteredSelectItemWithDisplay(organigrammaItemName, lGwtRestDataSource) {

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
//				mDynamicForm.setValue(organigrammaItemName, record.getAttributeAsString("id"));
				mDynamicForm.setValue(codRapidoItemName, record.getAttributeAsString("codice"));
				mDynamicForm.setValue(idUoHiddenItemName, record.getAttributeAsString("idUo"));
				mDynamicForm.setValue(descrizioneHiddenItemName, record.getAttributeAsString("descrizioneOrig"));
				mDynamicForm.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaItem.fetchData();
						manageChangedUo();
					}
				});
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue(organigrammaItemName, "");
				mDynamicForm.setValue(codRapidoItemName, "");
				mDynamicForm.setValue(idUoHiddenItemName, "");
				mDynamicForm.setValue(descrizioneHiddenItemName, "");
				mDynamicForm.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaItem.fetchData();
						manageChangedUo();
					}
				});
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue(organigrammaItemName, "");
					mDynamicForm.setValue(codRapidoItemName, "");
					mDynamicForm.setValue(idUoHiddenItemName, "");
					mDynamicForm.setValue(descrizioneHiddenItemName, "");
					mDynamicForm.clearErrors(true);
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							organigrammaItem.fetchData();
							manageChangedUo();
						}
					});
				}
			}
		};
		List<ListGridField> organigrammaPickListFields = new ArrayList<ListGridField>();
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIS_ICONA_TIPO_UO")) {
			ListGridField typeNodoField = new ListGridField("iconaTypeNodo", "Tipo");
			typeNodoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
			typeNodoField.setAlign(Alignment.CENTER);
			typeNodoField.setWidth(30);
			typeNodoField.setShowHover(false);
			typeNodoField.setCanFilter(false);
			typeNodoField.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record.getAttribute("iconaTypeNodo") != null && !"".equals(record.getAttributeAsString("iconaTypeNodo"))) {
						return "<div align=\"center\"><img src=\"images/organigramma/tipo/" + record.getAttribute("iconaTypeNodo")
								+ ".png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
					}
					return null;
				}
			});
			organigrammaPickListFields.add(typeNodoField);
		}
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
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT")) {
			ListGridField flgPuntoProtocolloField = new ListGridField("flgPuntoProtocollo", "Punto di Protocollo");
			flgPuntoProtocolloField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
			flgPuntoProtocolloField.setAlign(Alignment.CENTER);
			flgPuntoProtocolloField.setWidth(30);
			flgPuntoProtocolloField.setShowHover(true);
			flgPuntoProtocolloField.setCanFilter(false);
			flgPuntoProtocolloField.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
						return "<div align=\"center\"><img src=\"images/organigramma/puntoProtocollo.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
					}
					return null;
				}
			});
			flgPuntoProtocolloField.setHoverCustomizer(new HoverCustomizer() {

				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record != null && record != null && record.getAttribute("flgPuntoProtocollo") != null
							&& record.getAttributeAsBoolean("flgPuntoProtocollo")) {
						return "Punto di Protocollo";
					}
					return null;
				}
			});
			organigrammaPickListFields.add(flgPuntoProtocolloField);
		}
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
		organigrammaItem.setOptionDataSource(lGwtRestDataSource);
		organigrammaItem.setShowTitle(false);
		organigrammaItem.setWidth(400);
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
				return isRequiredOrganigramma();
			}
		}));
		organigrammaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return organigrammaItemShowIfCondition();
			}
		});

		ListGrid pickListProperties = organigrammaItem.getPickListProperties();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String codRapido = mDynamicForm.getValueAsString(codRapidoItemName);
//				if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && (codRapido == null || "".equals(codRapido))) {
//					if(codRapidoItem.getCanEdit() != null && codRapidoItem.getCanEdit()) {
//						mDynamicForm.setFieldErrors(codRapidoItemName, "Filtro obbligatorio per popolare la lista di scelta");						
//					}
//				}
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
				// organigrammaDS.addParam("ciToAdd", mDynamicForm.getValueAsString(organigrammaItemName));
				organigrammaDS.addParam("codice", codRapido);
				organigrammaDS.addParam("finalita", getFinalitaOrganigramma());
				organigrammaItem.setOptionDataSource(organigrammaDS);
				organigrammaItem.invalidateDisplayValueCache();
			}
		});
		organigrammaItem.setPickListProperties(pickListProperties);

		ImgButtonItem lookupOrganigrammaButton = new ImgButtonItem("lookupOrganigrammaButton", "lookup/organigramma.png",
				I18NUtil.getMessages().protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaButton.setColSpan(1);
		lookupOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				SelezionaUfficioLookupOrganigramma lookupOrganigrammaPopup = new SelezionaUfficioLookupOrganigramma(null);
				lookupOrganigrammaPopup.show();
			}
		});
		
		lookupOrganigrammaButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return lookupOrganigrammaButtonShowIfCondition();
			}
		});
		
		add(idUoHiddenItem);
		add(descrizioneHiddenItem);
		add(codRapidoItem);
		add(lookupOrganigrammaButton);
		add(organigrammaItem);
	}

	protected boolean organigrammaItemShowIfCondition(){
		return true;
	}
	
	protected boolean codRapidoItemValidator(){
		if (codRapidoItem.getValue() != null && !"".equals(codRapidoItem.getValueAsString().trim()) && organigrammaItem.getValue() == null) {
			return false;
		}
		return true;
	}
	
	protected boolean codRapidoItemShowIfCondition(){
		return true;
	}
	
	protected boolean lookupOrganigrammaButtonShowIfCondition(){
		return true;
	}
	
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
		mDynamicForm.setValue(organigrammaItemName, tipo + idOrganigramma);
		mDynamicForm.setValue(idUoHiddenItemName, idOrganigramma); 
		// mDynamicForm.setValue(descrizioneHiddenItemName, ""); // da settare
		mDynamicForm.setValue(codRapidoItemName, record.getAttribute("codRapidoUo"));
		mDynamicForm.clearErrors(true);

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				organigrammaItem.fetchData();
				manageChangedUo();
			}
		});
	}
	
	/**
	 * IMPORTANTE: QUESTO METODO DEVE ESSERE CHIAMATO OGNI VOLTA CHE SI AGGIORNA IL FORM. SERVE PER AGGIORNARE IL VALORE
	 * RIPORTATO NELLA SELECT. SE NON VIENE INVOCATO RIMENE VISUALIZZATO IL CODICE DALLA UO, E NON LA SUA DESCRIZIONE
	 */
	public void afterEditRecord(Record record) {
		if (record.getAttribute(organigrammaItemName) != null && !"".equals(record.getAttributeAsString(organigrammaItemName))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute(organigrammaItemName), record.getAttribute(descrizioneHiddenItemName));
			organigrammaItem.setValueMap(valueMap);
		}
		GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
		if (record.getAttribute(organigrammaItemName) != null && !"".equals(record.getAttributeAsString(organigrammaItemName))) {
			organigrammaDS.addParam("idUoSv", record.getAttributeAsString(idUoHiddenItemName));
			organigrammaDS.addParam("flgUoSv", "UO");
		} else {
			organigrammaDS.addParam("idUoSv", null);
			organigrammaDS.addParam("flgUoSv", null);
		}
		organigrammaItem.setOptionDataSource(organigrammaDS);		
	}
	
	public void setCanEdit(boolean canEdit) {
		for(FormItem item : this) {
			item.setCanEdit(canEdit);
			item.redraw();
		}
	}
		
	public class SelezionaUfficioLookupOrganigramma extends LookupOrganigrammaPopup {

		public SelezionaUfficioLookupOrganigramma(Record record) {
			super(record, true, 0);
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
			return getFinalitaOrganigramma();
		}

	}
	
	public FormItem getFormItem(String name) {
		return mDynamicForm.getItem(name);
	}
	
	public boolean isRequiredOrganigramma() {
		return false;
	}
	
	public String getFinalitaOrganigramma() {
		return null;
	}
	
	public String getFlgSoloValide() {
		return null;
	}
	
	public String getTipoAssegnatari() {
		return "UO";
	}
	
	public void manageChangedUo() {
		
	}
	
}
