/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
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
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.anagrafiche.MembriGruppoUdWindow;
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
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class CondivisioneCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;
	
	private HiddenItem idUoHiddenItem;
	private HiddenItem typeNodoHiddenItem;
	private HiddenItem statoFirmaResponsabileItem;
	private HiddenItem codRapidoChangedHiddenItem;
	private SelectItem tipoItem;
	private ExtendedTextItem codRapidoItem;
	private HiddenItem descrizioneHiddenItem;
	private FilteredSelectItemWithDisplay organigrammaItem;
	private ImgButtonItem lookupOrganigrammaButton;
	private FilteredSelectItemWithDisplay gruppoItem;
	private SelectItem preferitiItem;
	private HiddenItem opzioniInvioHiddenItem;
	private ImgButtonItem opzioniInvioCondivisioneButton;
	private ImgButtonItem showMembriGruppoButton;
	private HiddenItem gruppoSalvatoItem;
	
	public CondivisioneCanvas(CondivisioneItem item) {

		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
//		mDynamicForm.setCellBorder(1);

		idUoHiddenItem = new HiddenItem("idUo");
		typeNodoHiddenItem = new HiddenItem("typeNodo");
		statoFirmaResponsabileItem = new HiddenItem("statoFirmaResponsabile");
		codRapidoChangedHiddenItem = new HiddenItem("codRapidoChanged");
		gruppoSalvatoItem = new HiddenItem("gruppoSalvato");

		// tipo
		tipoItem = new SelectItem("tipo", "Tipo");
		tipoItem.setDefaultValue("SV;UO");
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			tipoItem.setShowTitle(true);
		} else {
			tipoItem.setShowTitle(false);
		}
		tipoItem.setValueMap(buildTipoValueMap());
		tipoItem.setWidth(150);
		tipoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				mDynamicForm.clearErrors(true);
				Record lRecord = mDynamicForm.getValuesAsRecord();
				lRecord.setAttribute("organigramma", "");
				lRecord.setAttribute("idUo", "");
				if ("LD".equals(event.getValue())) {
					lRecord.setAttribute("typeNodo", "G");
				} else {
					lRecord.setAttribute("typeNodo", "");
				}
				lRecord.setAttribute("codRapido", "");
				mDynamicForm.setValues(lRecord.toMap());
				mDynamicForm.redraw();
			}
		});
		tipoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				final LinkedHashMap<String, String> tipoValueMap = buildTipoValueMap();
				return tipoValueMap != null && tipoValueMap.keySet().size() > 1;				
			}
		});

		codRapidoItem = new ExtendedTextItem("codRapido", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoItem.setWidth(120);
		codRapidoItem.setColSpan(1);
		codRapidoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("codRapidoChanged", true);
				mDynamicForm.setValue("idUo", (String) null);
				mDynamicForm.setValue("descrizione", (String) null);
				mDynamicForm.setValue("organigramma", (String) null);
				mDynamicForm.setValue("typeNodo", (String) null);
				mDynamicForm.setValue("gruppo", (String) null);
				mDynamicForm.clearErrors(true);
				final String value = codRapidoItem.getValueAsString();
				if (tipoItem.getValueAsString().equalsIgnoreCase("LD")) {
					cercaSoggettoLD();
				} else {
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
											mDynamicForm.setValue("idUo", data.get(i).getAttribute("idUo"));
											mDynamicForm.setValue("typeNodo", data.get(i).getAttribute("typeNodo"));
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
							}
						});
					} else {
						organigrammaItem.fetchData();
					}
				}
			}
		});
		codRapidoItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (!"PREF".equalsIgnoreCase(tipoItem.getValueAsString())) {
					if (codRapidoItem.getValue() != null && !"".equals(codRapidoItem.getValueAsString().trim()) 
							&& organigrammaItem.getValue() == null && gruppoItem.getValue() == null) {
						return false;
					}
				}
				return true;
			}
		});
		codRapidoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"PREF".equalsIgnoreCase(tipoItem.getValueAsString());
			}
		});

		descrizioneHiddenItem = new HiddenItem("descrizione");

		SelectGWTRestDataSource organigrammaDS = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT,
				new String[] { "descrizione" }, true);
		organigrammaDS.addParam("tipoAssegnatari", ((CondivisioneItem) getItem()).getFlgSoloUO() ? "UO" : null);
		organigrammaDS.addParam("finalita", ((CondivisioneItem) getItem()).getFinalitaLoadComboOrganigramma());
		
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
				mDynamicForm.setValue("codRapido", record.getAttributeAsString("codice"));
				mDynamicForm.setValue("typeNodo", record.getAttributeAsString("typeNodo"));
				mDynamicForm.setValue("idUo", record.getAttributeAsString("idUo"));
				mDynamicForm.setValue("descrizione", record.getAttributeAsString("descrizioneOrig"));
				mDynamicForm.clearErrors(true);				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaItem.fetchData();
					}
				});
			}

			@Override
			protected void clearSelect() {

				super.clearSelect();
				mDynamicForm.setValue("organigramma", "");
				if(((CondivisioneItem)getItem()).isDimOrganigrammaNonStd()) {
					mDynamicForm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
				} else {
					mDynamicForm.setValue("codRapido", "");
				}
				mDynamicForm.setValue("idUo", "");
				mDynamicForm.setValue("typeNodo", "");
				mDynamicForm.setValue("descrizione", "");
				mDynamicForm.clearErrors(true);				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaItem.fetchData();
					}
				});
			};

			@Override
			public void setValue(String value) {

				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("organigramma", "");
					if(((CondivisioneItem)getItem()).isDimOrganigrammaNonStd()) {
						mDynamicForm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						mDynamicForm.setValue("codRapido", "");
					}
					mDynamicForm.setValue("idUo", "");
					mDynamicForm.setValue("typeNodo", "");
					mDynamicForm.setValue("descrizione", "");
					mDynamicForm.clearErrors(true);				
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							organigrammaItem.fetchData();
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
				return record != null ? record.getAttribute("descrizioneEstesa") : null;
			}
		});
		if (((CondivisioneItem)getItem()).isDimOrganigrammaNonStd()) {
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

					if (record != null && record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
						return "Punto di Protocollo";
					}
					return null;
				}
			});
			organigrammaPickListFields.add(flgPuntoProtocolloField);
		}
		organigrammaItem.setPickListFields(organigrammaPickListFields.toArray(new ListGridField[organigrammaPickListFields.size()]));
		if (((CondivisioneItem)getItem()).isDimOrganigrammaNonStd()) {
			organigrammaItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimOrganigrammaNonStdMessage());
		} else {
			organigrammaItem.setFilterLocally(true);
		}
		organigrammaItem.setAutoFetchData(false);
		organigrammaItem.setAlwaysFetchMissingValues(true);
		organigrammaItem.setFetchMissingValues(true);
		organigrammaItem.setCachePickListResults(false);		
		organigrammaItem.setValueField("id");
		organigrammaItem.setOptionDataSource(organigrammaDS);
		organigrammaItem.setWidth(((CondivisioneItem) getItem()).getFilteredSelectItemWidth());
		organigrammaItem.setClearable(true);
		organigrammaItem.setShowIcons(true);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			organigrammaItem.setTitle("Lista organigramma");
			organigrammaItem.setShowTitle(true);		
		} else {
			organigrammaItem.setShowTitle(false);
		}
		organigrammaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();				
			}
		});
		organigrammaItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
			}
		});
		organigrammaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"LD".equalsIgnoreCase(tipoItem.getValueAsString()) &&
					   !"PREF".equalsIgnoreCase(tipoItem.getValueAsString());
			}
		});
		organigrammaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				Boolean notReplicable = getItem().getNotReplicable() != null ? getItem().getNotReplicable() : false;
				Boolean obbligatorio = getItem().getAttributeAsBoolean("obbligatorio") != null ? getItem().getAttributeAsBoolean("obbligatorio") : false;
				return !"LD".equalsIgnoreCase(tipoItem.getValueAsString()) && !"PREF".equalsIgnoreCase(tipoItem.getValueAsString()) && (!notReplicable || obbligatorio);
			}
		}));
		ListGrid organigrammaPickListProperties = organigrammaItem.getPickListProperties();
		organigrammaPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String codRapido = mDynamicForm.getValueAsString("codRapido");
//				if (((CondivisioneItem)getItem()).isDimOrganigrammaNonStd() && (codRapido == null || "".equals(codRapido))) {
//					if(codRapidoItem.getCanEdit() != null && codRapidoItem.getCanEdit()) {
//						mDynamicForm.setFieldErrors("codRapido", "Filtro obbligatorio per popolare la lista di scelta");
//					}
//				}				
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
				organigrammaDS.addParam("tipoAssegnatari", ((CondivisioneItem) getItem()).getFlgSoloUO() ? "UO" : null);
				organigrammaDS.addParam("finalita", ((CondivisioneItem) getItem()).getFinalitaLoadComboOrganigramma());				
				// organigrammaDS.addParam("ciToAdd", mDynamicForm.getValueAsString("organigramma"));
				organigrammaDS.addParam("codice", codRapido);
				organigrammaItem.setOptionDataSource(organigrammaDS);
				organigrammaItem.invalidateDisplayValueCache();
			}
		});
		organigrammaItem.setPickListProperties(organigrammaPickListProperties);

		lookupOrganigrammaButton = new ImgButtonItem("lookupOrganigrammaButton", "lookup/organigramma.png",
				I18NUtil.getMessages().protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaButton.setColSpan(1);
		lookupOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				CondivisioneLookupOrganigramma lookupOrganigrammaPopup = new CondivisioneLookupOrganigramma(null);
				lookupOrganigrammaPopup.show();
			}
		});
		lookupOrganigrammaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"LD".equalsIgnoreCase(tipoItem.getValueAsString()) &&
					   !"PREF".equalsIgnoreCase(tipoItem.getValueAsString());
			}
		});
		
//		SpacerItem spacerLookupOrganigrammaButton = new SpacerItem();
//		spacerLookupOrganigrammaButton.setWidth(20);
//		spacerLookupOrganigrammaButton.setColSpan(1);
//		spacerLookupOrganigrammaButton.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {				
//				return lookupOrganigrammaButton != null && !lookupOrganigrammaButton.getVisible();
//			}
//		});

//		SpacerItem spacer = new SpacerItem();
//		spacer.setWidth(10);
//		spacer.setColSpan(1);
		
		opzioniInvioHiddenItem = new HiddenItem("opzioniInvio");
		
		opzioniInvioCondivisioneButton = new ImgButtonItem("opzioniInvioCondivisioneButton", "buttons/altriDati.png",
				I18NUtil.getMessages().protocollazione_detail_opzioniInvioAssegnazioneButton_prompt());
//		opzioniInvioCondivisioneButton.setAlwaysEnabled(true);
		opzioniInvioCondivisioneButton.setColSpan(1);
		opzioniInvioCondivisioneButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record recordOpzioniInvio = new Record(mDynamicForm.getValues()).getAttributeAsRecord("opzioniInvio"); 	
				String tipoAssegnatario = new Record(mDynamicForm.getValues()).getAttribute("tipo");
				OpzioniInvioCondivisionePopup opzioniInvioCondivisionePopup = new OpzioniInvioCondivisionePopup(recordOpzioniInvio, tipoAssegnatario, getEditing()) {
					
					@Override
					public String getFlgUdFolder() {
						return ((CondivisioneItem) getItem()).getFlgUdFolder();
					}
					
					@Override
					public void onClickOkButton(Record record) {
						mDynamicForm.setValue("opzioniInvio", record);	
					}
				};
				opzioniInvioCondivisionePopup.show();
			}
		});
		opzioniInvioCondivisioneButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((CondivisioneItem) getItem()).showOpzioniInvioCondivisioneButton()/* && 
						!"LD".equalsIgnoreCase(tipoItem.getValueAsString())*/;
			}
		});

		// gruppi
		SelectGWTRestDataSource gruppoDS = new SelectGWTRestDataSource("LoadComboGruppoSoggettiDataSource", "idGruppo", FieldType.TEXT,
				new String[] { "nomeGruppo" }, true);
		gruppoDS.extraparam.put("usaFlagSoggettiInterni", "S");
		gruppoDS.extraparam.put("flgSoloGruppiSoggInt", "2");
		gruppoDS.extraparam.put("flgSoloAssegnabili", "1");
		
		gruppoItem = new FilteredSelectItemWithDisplay("gruppo", gruppoDS) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("codRapido", record.getAttributeAsString("codiceRapidoGruppo"));
				mDynamicForm.setValue("descrizione", record.getAttributeAsString("nomeGruppo"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("gruppo", "");
				mDynamicForm.setValue("codRapido", "");
				mDynamicForm.setValue("descrizione", "");
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("gruppo", "");
					mDynamicForm.setValue("codRapido", "");
					mDynamicForm.setValue("descrizione", "");
				}
            }
		};
		gruppoItem.setAutoFetchData(false);
		gruppoItem.setFetchMissingValues(true);
		gruppoItem.setAlwaysFetchMissingValues(true);
		gruppoItem.setCachePickListResults(false);
		
		ListGridField codiceRapidoGruppoField = new ListGridField("codiceRapidoGruppo", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codiceRapidoGruppoField.setWidth(80);
		ListGridField nomeGruppoField = new ListGridField("nomeGruppo", I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeGruppoField.setWidth("*");

		ListGridField flagSoggettiGruppoField = new ListGridField("flagSoggettiGruppo", I18NUtil.getMessages().protocollazione_detail_tipoItem_title());
		flagSoggettiGruppoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flagSoggettiGruppoField.setType(ListGridFieldType.ICON);
		flagSoggettiGruppoField.setWidth(30);
		flagSoggettiGruppoField.setIconWidth(16);
		flagSoggettiGruppoField.setIconHeight(16);
		Map<String, String> flagSoggettiGruppoValueIcons = new HashMap<String, String>();
		flagSoggettiGruppoValueIcons.put("E", "protocollazione/flagSoggettiGruppo/E.png");
		flagSoggettiGruppoValueIcons.put("I", "protocollazione/flagSoggettiGruppo/I.png");
		flagSoggettiGruppoValueIcons.put("M", "protocollazione/flagSoggettiGruppo/M.png");
		flagSoggettiGruppoValueIcons.put("O", "protocollazione/flagSoggettiGruppo/O.png");
		flagSoggettiGruppoValueIcons.put("", "blank.png");
		flagSoggettiGruppoField.setValueIcons(flagSoggettiGruppoValueIcons);
		flagSoggettiGruppoField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if (record.getAttribute("flagSoggettiGruppo") != null) {
					if ("E".equals(record.getAttribute("flagSoggettiGruppo"))) {
						return I18NUtil.getMessages().protocollazione_detail_flagSoggettiGruppo_E_value();
					} else if ("I".equals(record.getAttribute("flagSoggettiGruppo"))) {
						return I18NUtil.getMessages().protocollazione_detail_flagSoggettiGruppo_I_value();
					} else if ("M".equals(record.getAttribute("flagSoggettiGruppo"))) {
						return I18NUtil.getMessages().protocollazione_detail_flagSoggettiGruppo_M_value();
					} else if ("O".equals(record.getAttribute("flagSoggettiGruppo"))) {
						return I18NUtil.getMessages().protocollazione_detail_flagSoggettiGruppo_O_value();
					}
				}
				return null;
			}
		});

		gruppoItem.setPickListFields(codiceRapidoGruppoField, nomeGruppoField, flagSoggettiGruppoField);
		gruppoItem.setFilterLocally(true);
		gruppoItem.setValueField("idGruppo");
		gruppoItem.setOptionDataSource(gruppoDS);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			gruppoItem.setTitle("Gruppo");
			gruppoItem.setShowTitle(true);
		} else {
			gruppoItem.setShowTitle(false);
		}
		gruppoItem.setWidth(((CondivisioneItem) getItem()).getFilteredSelectItemWidth());
		gruppoItem.setClearable(true);
		gruppoItem.setShowIcons(true);
		gruppoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();				
			}
		});
		gruppoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "LD".equalsIgnoreCase(tipoItem.getValueAsString());
			}
		});
		gruppoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				Boolean notReplicable = getItem().getNotReplicable() != null ? getItem().getNotReplicable() : false;
				Boolean obbligatorio = getItem().getAttributeAsBoolean("obbligatorio") != null ? getItem().getAttributeAsBoolean("obbligatorio") : false;
				return "LD".equalsIgnoreCase(tipoItem.getValueAsString()) && (!notReplicable || obbligatorio);
			}
		}));
		
		String flgUdFolder = ((CondivisioneItem) getItem()).getFlgUdFolder();
		GWTRestDataSource preferitiDS = new GWTRestDataSource("LoadComboPreferitiAssegnazioneCondivisioneDataSource");
		preferitiDS.addParam("tipoAssegnatari", ((CondivisioneItem) getItem()).getFlgSoloUO() ? "UO" : null);
		if(flgUdFolder != null) {
			if("U".equals(flgUdFolder)) {
				preferitiDS.addParam("finalita", "INVIO_CC_DOC");
			} else if("F".equals(flgUdFolder)) {
				preferitiDS.addParam("finalita", "INVIO_CC_FOLDER");
			} 
		}
		preferitiDS.addParam("idEmail", ((CondivisioneItem) getItem()).getIdEmailArrivoProtocollo());		
		
		preferitiItem = new SelectItem("preferiti", "Preferiti") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				if(record.getAttribute("key") != null && !"".equals(record.getAttribute("key"))){
					mDynamicForm.setValue("typeNodo", record.getAttribute("key").substring(0, 2));
					mDynamicForm.setValue("idUo", record.getAttribute("key").substring(2));
				}
				markForRedraw();
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("typeNodo", "");
				mDynamicForm.setValue("idUo", "");	
				markForRedraw();
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("typeNodo", "");
					mDynamicForm.setValue("idUo", "");
				}
				markForRedraw();
            }
			
		};
		
		preferitiItem.setAutoFetchData(false);
		preferitiItem.setFetchMissingValues(true);
		preferitiItem.setAlwaysFetchMissingValues(true);
		preferitiItem.setCachePickListResults(false);
		
		preferitiItem.setWidth(450);
		preferitiItem.setColSpan(1);
		preferitiItem.setShowTitle(false);
		preferitiItem.setAllowEmptyValue(true);
		preferitiItem.setDisplayField("value");
		preferitiItem.setValueField("key");
		preferitiItem.setOptionDataSource(preferitiDS);
		preferitiItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		preferitiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return "PREF".equalsIgnoreCase(tipoItem.getValueAsString());
			}
		});
		preferitiItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				Boolean notReplicable = getItem().getNotReplicable() != null ? getItem().getNotReplicable() : false;
				Boolean obbligatorio = getItem().getAttributeAsBoolean("obbligatorio") != null ? getItem().getAttributeAsBoolean("obbligatorio") : false;
				return "PREF".equalsIgnoreCase(tipoItem.getValueAsString()) && (!notReplicable || obbligatorio);
			}
		}));
		
		
		ListGrid preferitiPickListProperties = preferitiItem.getPickListProperties();
		preferitiPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {		
				String flgUdFolder = ((CondivisioneItem) getItem()).getFlgUdFolder();
				GWTRestDataSource preferitiDS = (GWTRestDataSource) preferitiItem.getOptionDataSource();
				preferitiDS.addParam("tipoAssegnatari", ((CondivisioneItem) getItem()).getFlgSoloUO() ? "UO" : null);
				if(flgUdFolder != null) {
					if("U".equals(flgUdFolder)) {
						preferitiDS.addParam("finalita", "INVIO_CC_DOC");
					} else if("F".equals(flgUdFolder)) {
						preferitiDS.addParam("finalita", "INVIO_CC_FOLDER");
					} 
				}
				preferitiDS.addParam("idEmail", ((CondivisioneItem) getItem()).getIdEmailArrivoProtocollo());						
				preferitiItem.setOptionDataSource(preferitiDS);
				preferitiItem.invalidateDisplayValueCache();
			}
		});
		preferitiItem.setPickListProperties(preferitiPickListProperties);
		
		showMembriGruppoButton = new ImgButtonItem("showMembriGruppoButton", "menu/gruppi_soggetti.png", "Visualizza membri gruppo");
		showMembriGruppoButton.setAlwaysEnabled(true);
		showMembriGruppoButton.setColSpan(1);
		showMembriGruppoButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return showMembriGruppo();
			}
		});
		showMembriGruppoButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				
				Record record = new Record();
				if(mDynamicForm.getValueAsString("gruppoSalvato") != null && gruppoItem.getValueAsString() != null && 
				   mDynamicForm.getValueAsString("gruppoSalvato").equals(gruppoItem.getValueAsString())) {
						record.setAttribute("idUd", ((CondivisioneItem) getItem()).getIdUdProtocollo() != null ?
								((CondivisioneItem) getItem()).getIdUdProtocollo() : "");
				}
				record.setAttribute("gruppo", gruppoItem.getValueAsString());
				record.setAttribute("nomeGruppo", gruppoItem.getDisplayValue());
				record.setAttribute("flgAssCondDest", "C");
				record.setAttribute("tipologiaMembro", "DIC");
				new MembriGruppoUdWindow(record);
			}
		});
		
		mDynamicForm.setFields(
			idUoHiddenItem, 
			typeNodoHiddenItem, 
			statoFirmaResponsabileItem, 
			codRapidoChangedHiddenItem,
			tipoItem, 
			gruppoSalvatoItem,
			preferitiItem,
			codRapidoItem, descrizioneHiddenItem, 
			gruppoItem, 
			showMembriGruppoButton,
			lookupOrganigrammaButton, 
			/*spacerLookupOrganigrammaButton, 
			spacer,*/
			organigrammaItem, 
			opzioniInvioHiddenItem, 
			opzioniInvioCondivisioneButton
		);

		mDynamicForm.setNumCols(16);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");

		addChild(mDynamicForm);

	}

	protected void cercaSoggettoLD() {
		mDynamicForm.setValue("gruppo", (String) null);
		mDynamicForm.clearErrors(true);
		final String value = codRapidoItem.getValueAsString() != null ? codRapidoItem.getValueAsString().toUpperCase() : null;
		if (value != null && !"".equals(value)) {
			SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboGruppoSoggettiDataSource", "idGruppo", FieldType.TEXT,
					new String[] { "codiceRapidoGruppo", "nomeGruppo" }, true);
			lGwtRestDataSource.extraparam.put("usaFlagSoggettiInterni", "S");
			lGwtRestDataSource.extraparam.put("flgSoloGruppiSoggInt", "2");
			lGwtRestDataSource.extraparam.put("flgSoloAssegnabili", "1");
			lGwtRestDataSource.extraparam.put("codiceRapidoGruppo", value);
			lGwtRestDataSource.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					RecordList data = response.getDataAsRecordList();
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String codiceRapido = data.get(i).getAttribute("codiceRapidoGruppo");
							if (codiceRapido != null && value.equalsIgnoreCase(codiceRapido)) {
								mDynamicForm.setValue("codRapido", data.get(i).getAttribute("codiceRapidoGruppo"));
								mDynamicForm.setValue("gruppo", data.get(i).getAttribute("idGruppo"));
								mDynamicForm.setValue("descrizione", data.get(i).getAttribute("nomeGruppo"));
								mDynamicForm.setValue("typeNodo", "G");
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						mDynamicForm.setFieldErrors("codRapido", "Gruppo inesistente");
					}
				}
			});
		}
	}

	@Override
	public void editRecord(Record record) {
		mDynamicForm.clearErrors(true);
		if ("LD".equalsIgnoreCase(record.getAttribute("tipo"))) {
			if ( record.getAttribute("gruppo") != null && !"".equals(record.getAttributeAsString("gruppo")) &&
				 record.getAttribute("descrizione") != null && !"".equals(record.getAttributeAsString("descrizione")) ) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("gruppo"), record.getAttribute("descrizione"));
				gruppoItem.setValueMap(valueMap);
			}

			GWTRestDataSource gruppoDS = (GWTRestDataSource) gruppoItem.getOptionDataSource();
			if (record.getAttribute("gruppo") != null && !"".equals(record.getAttributeAsString("gruppo"))) {
				gruppoDS.addParam("idGruppo", record.getAttributeAsString("gruppo"));
			} else {
				gruppoDS.addParam("idGruppo", null);
			}
			gruppoItem.setOptionDataSource(gruppoDS);
		} else {
			if ( record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma")) &&
				 record.getAttribute("descrizione") != null && !"".equals(record.getAttributeAsString("descrizione")) ) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				if (record.getAttribute("typeNodo") != null && "UO".equals(record.getAttribute("typeNodo"))) {
					valueMap.put(record.getAttribute("organigramma"), "<b>" + record.getAttribute("descrizione") + "</b>");
				} else {
					valueMap.put(record.getAttribute("organigramma"), record.getAttribute("descrizione"));
				}
				organigrammaItem.setValueMap(valueMap);
			}
			GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
			if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma"))
					&& record.getAttribute("typeNodo") != null
					&& ("UO".equals(record.getAttributeAsString("typeNodo")) || "SV".equals(record.getAttributeAsString("typeNodo")))) {
				organigrammaDS.addParam("idUoSv", record.getAttributeAsString("idUo"));
				organigrammaDS.addParam("flgUoSv", record.getAttributeAsString("typeNodo"));
			} else {
				organigrammaDS.addParam("idUoSv", null);
				organigrammaDS.addParam("flgUoSv", null);
			}
			organigrammaItem.setOptionDataSource(organigrammaDS);
		}
		super.editRecord(record);
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
		mDynamicForm.setValue("organigramma", tipo + idOrganigramma);
		mDynamicForm.setValue("idUo", idOrganigramma);
		mDynamicForm.setValue("typeNodo", tipo);
		// mDynamicForm.setValue("descrizione", ""); // da settare
		mDynamicForm.setValue("codRapido", record.getAttribute("codRapidoUo"));
		mDynamicForm.clearErrors(true);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				organigrammaItem.fetchData();
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

	public class CondivisioneLookupOrganigramma extends LookupOrganigrammaPopup {

		public CondivisioneLookupOrganigramma(Record record) {
			super(record, true, ((CondivisioneItem) getItem()).getFlgSoloUO() ? new Integer(0) : new Integer(1));
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
		public boolean getFlgMostraSVDefaultValue() {
			return !AurigaLayout.getImpostazioniSceltaOrganigrammaAsBoolean("flgNascondiSVInviiCC");
		}
		
		@Override
		public String getFinalita() {
			return ((CondivisioneItem) getItem()).getFinalitaOrganigrammaLookup();
		}
	}
	
	public void readOnlyMode(Boolean isAttivaEliminazioneUOCoinvolte) {
		setCanEdit(false);		
		if(isAttivaEliminazioneUOCoinvolte){
			if (getRemoveButton() != null) {
				getRemoveButton().show();	
				if (!canRemove()) {				
					getRemoveButton().setAlwaysDisabled(true);
				}			
			}
		}
	}
	
	public boolean canRemove() {
		boolean canRemove = false;
		String statoFirmaResponsabile = mDynamicForm.getValueAsString("statoFirmaResponsabile");
		if(statoFirmaResponsabile == null || (statoFirmaResponsabile != null && "0".equals(statoFirmaResponsabile))){
			canRemove = true;
		}
		return canRemove;
	}
	
	public LinkedHashMap<String, String> buildTipoValueMap() {
		final boolean flgSoloUO = ((CondivisioneItem) getItem()).getFlgSoloUO();
		final LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
		if (flgSoloUO) {
			tipoValueMap.put("SV;UO", "U.O.");
		} else {
			tipoValueMap.put("SV;UO", "Unità di personale/U.O.");
			tipoValueMap.put("LD", "Liste di distribuzione");
		}
		if (((CondivisioneItem) getItem()).showPreferiti()) {
			tipoValueMap.put("PREF", "Preferiti");
		}
		return tipoValueMap;
	}
	
	/**
	 * Metodo per la visualizzazione del bottone Visualizza membri gruppo che si abilita se si è selezionata una 
	 * Lista di distribuzione valorizzata correttamente
	 */
	private boolean showMembriGruppo() {
		return ((tipoItem != null && tipoItem.getValueAsString() != null &&
				"LD".equals(tipoItem.getValueAsString())) && 
				(gruppoItem != null && gruppoItem.getValueAsString() != null
				 && !"".equals(gruppoItem.getValueAsString())));			
	}
	
}