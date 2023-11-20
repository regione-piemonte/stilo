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
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class ProponentiCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;

	private HiddenItem idUoHiddenItem;
	private HiddenItem codRapidoHiddenItem;
	private HiddenItem descrizioneHiddenItem;
	private HiddenItem flgUfficioGareHiddenItem;
	private SelectItem ufficioProponenteItem;
	private ExtendedTextItem codRapidoItem;
	private FilteredSelectItemWithDisplay organigrammaItem;
	private HiddenItem organigrammaFromLoadDettHiddenItem;
	private ScrivaniaProponentiItem listaRdPItem;
	private ScrivaniaProponentiItem listaDirigentiItem;
	private ScrivaniaProponentiItem listaDirettoriItem;
//	private SelectItem idScrivaniaDirettoreItem;	
//	private HiddenItem idScrivaniaDirettoreFromLoadDettHiddenItem;
//	private HiddenItem codUoScrivaniaDirettoreHiddenItem;
//	private HiddenItem desScrivaniaDirettoreHiddenItem;
//	private TextItem tipoVistoScrivaniaDirettoreItem;
//	private CheckboxItem flgForzaDispFirmaScrivaniaDirettoreItem;
	
	public ProponentiCanvas(ProponentiItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		if(!((ProponentiItem)getItem()).getNotReplicable()) {
			mDynamicForm.setBorder("1px solid #a7abb4");
			mDynamicForm.setMargin(2);
			mDynamicForm.setPadding(2);
		}
				
		List<FormItem> items = new ArrayList<FormItem>();		
		
		if(((ProponentiItem)getItem()).isAbilSelezioneProponentiEstesa()) {
			
			idUoHiddenItem = new HiddenItem("idUo");
			
			codRapidoItem = new ExtendedTextItem("codRapido", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
			codRapidoItem.setWidth(120);
			codRapidoItem.setColSpan(1);
			codRapidoItem.addChangedBlurHandler(new ChangedHandler() {
	
				@Override
				public void onChanged(ChangedEvent event) {
					mDynamicForm.setValue("idUo", (String) null);
					mDynamicForm.setValue("descrizione", (String) null);
					mDynamicForm.setValue("flgUfficioGare", (String) null);				
					mDynamicForm.setValue("organigramma", (String) null);
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
											mDynamicForm.setValue("descrizione", data.get(i).getAttribute("descrizioneOrig"));
											mDynamicForm.setValue("flgUfficioGare", data.get(i).getAttribute("flgUfficioGare"));													
											mDynamicForm.setValue("organigramma", data.get(i).getAttribute("id"));
											mDynamicForm.setValue("idUo", data.get(i).getAttribute("idUo"));
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
								manageChangedUoSelezionata();
								
							}
						});
					} else {
						organigrammaItem.fetchData();
						manageChangedUoSelezionata();
					}
				}
			});
			codRapidoItem.setValidators(new CustomValidator() {
	
				@Override
				protected boolean condition(Object value) {
					if (codRapidoItem.getValue() != null && !"".equals(codRapidoItem.getValueAsString().trim()) && organigrammaItem.getValue() == null) {
						return false;
					}
					return true;
				}
			});
			
			descrizioneHiddenItem = new HiddenItem("descrizione");
			flgUfficioGareHiddenItem = new HiddenItem("flgUfficioGare");		
			
			SelectGWTRestDataSource organigrammaDS = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT, new String[] { "descrizione" }, true);
			if(((ProponentiItem)getItem()).getAltriParamLoadComboIdUo() != null) {
				organigrammaDS.addParam("altriParamLoadCombo", ((ProponentiItem)getItem()).getAltriParamLoadComboIdUo());
			}			
			organigrammaDS.addParam("idTipoDoc", ((ProponentiItem)getItem()).getIdTipoDocProposta()); 
			organigrammaDS.addParam("tipoAssegnatari", "UO");
			organigrammaDS.addParam("finalita", null);
			organigrammaDS.addParam("flgSoloValide", ((ProponentiItem) getItem()).getFlgSoloValide());
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
//					mDynamicForm.setValue("organigramma", record.getAttributeAsString("id"));
					mDynamicForm.setValue("codRapido", record.getAttributeAsString("codice"));
					mDynamicForm.setValue("idUo", record.getAttributeAsString("idUo"));
					mDynamicForm.setValue("descrizione", record.getAttributeAsString("descrizioneOrig"));
					mDynamicForm.setValue("flgUfficioGare", record.getAttributeAsString("flgUfficioGare"));
					mDynamicForm.clearErrors(true);			
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							organigrammaItem.fetchData();
							manageChangedUoSelezionata();
						}
					});
				}

				@Override
				protected void clearSelect() {
					super.clearSelect();
					mDynamicForm.setValue("organigramma", "");
					if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
						mDynamicForm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						mDynamicForm.setValue("codRapido", "");
					}
					mDynamicForm.setValue("idUo", "");
					mDynamicForm.setValue("descrizione", "");
					mDynamicForm.setValue("flgUfficioGare", "");				
					mDynamicForm.clearErrors(true);				
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							organigrammaItem.fetchData();
							manageChangedUoSelezionata();
						}
					});
				};

				@Override
				public void setValue(String value) {
					super.setValue(value);
					if (value == null || "".equals(value)) {
						mDynamicForm.setValue("organigramma", "");
						if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
							mDynamicForm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
						} else {
							mDynamicForm.setValue("codRapido", "");
						}
						mDynamicForm.setValue("idUo", "");
						mDynamicForm.setValue("descrizione", "");
						mDynamicForm.setValue("flgUfficioGare", "");									
						mDynamicForm.clearErrors(true);						
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {

							@Override
							public void execute() {
								organigrammaItem.fetchData();
								manageChangedUoSelezionata();
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
			organigrammaItem.setOptionDataSource(organigrammaDS);
			organigrammaItem.setShowTitle(false);
			organigrammaItem.setWidth(((ProponentiItem)getItem()).getSelectItemOrganigrammaWidth());
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
					String codRapido = mDynamicForm.getValueAsString("codRapido");
//					if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && (codRapido == null || "".equals(codRapido))) {
//						if(codRapidoItem.getCanEdit() != null && codRapidoItem.getCanEdit()) {
//							mDynamicForm.setFieldErrors("codRapido", "Filtro obbligatorio per popolare la lista di scelta");
//						}
//					}				
					GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
					organigrammaDS.addParam("codice", codRapido);
//					organigrammaDS.addParam("ciToAdd", (String) organigrammaFromLoadDettHiddenItem.getValue());
					organigrammaDS.addParam("finalita", null);
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
					ProponentiLookupOrganigramma lookupOrganigrammaPopup = new ProponentiLookupOrganigramma(null);
					lookupOrganigrammaPopup.show();
				}
			});		
			lookupOrganigrammaButton.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return ((ProponentiItem)getItem()).showLookupOrganigrammaButton();				
				}
			});

			items.add(idUoHiddenItem);
			items.add(descrizioneHiddenItem);
			items.add(flgUfficioGareHiddenItem);
			items.add(codRapidoItem);
			items.add(lookupOrganigrammaButton);
			items.add(organigrammaItem);
			items.add(organigrammaFromLoadDettHiddenItem);			

		} else {
			
			idUoHiddenItem = new HiddenItem("idUo");
			descrizioneHiddenItem = new HiddenItem("descrizione");
			flgUfficioGareHiddenItem = new HiddenItem("flgUfficioGare");					
			codRapidoHiddenItem = new HiddenItem("codRapido");
			
			ufficioProponenteItem = new SelectItem("ufficioProponente", ((ProponentiItem)getItem()).getTitleIdUo()) {
				
				@Override
				public void onOptionClick(Record record) {
					super.onOptionClick(record);
					String idUo = record.getAttribute("idUo");
					mDynamicForm.setValue("idUo", idUo);
					String descrizione = record.getAttribute("descrizione");
					if(descrizione != null && !"".equals(descrizione)) {
						mDynamicForm.setValue("codRapido", descrizione.substring(0, descrizione.indexOf(" - ")));
						mDynamicForm.setValue("descrizione", descrizione.substring(descrizione.indexOf(" - ") + 3));						
					} else {
						mDynamicForm.setValue("codRapido", "");
						mDynamicForm.setValue("descrizione", "");						
					}
					if(idUo != null && !"".equals(idUo)) {
						mDynamicForm.setValue("flgUfficioGare", ((ProponentiItem)getItem()).getFlgUfficioGareProponentiMap().get(idUo));
					} else {
						mDynamicForm.setValue("flgUfficioGare", "");	
					}
				}
				
				@Override
				protected void clearSelect() {
					super.clearSelect();
					mDynamicForm.setValue("idUo", "");
					mDynamicForm.setValue("codRapido", "");
					mDynamicForm.setValue("descrizione", "");
					mDynamicForm.setValue("flgUfficioGare", "");	
				}
				
				@Override
				public void setValue(String value) {
					super.setValue(value);
					if (value == null || "".equals(value)) {
						mDynamicForm.setValue("idUo", "");
						mDynamicForm.setValue("codRapido", "");
						mDynamicForm.setValue("descrizione", "");
						mDynamicForm.setValue("flgUfficioGare", "");	
					}
	            }
			};
			ufficioProponenteItem.setWidth(((ProponentiItem)getItem()).getSelectItemOrganigrammaWidth());
			ufficioProponenteItem.setColSpan(4);
			ufficioProponenteItem.setDisplayField("descrizione");
			ufficioProponenteItem.setValueField("idUo");
//			ufficioProponenteItem.setOptionDataSource(ufficioProponenteDS);
//			ufficioProponenteItem.setAutoFetchData(true);
//			ufficioProponenteItem.addDataArrivedHandler(new DataArrivedHandler() {			
//				@Override
//				public void onDataArrived(DataArrivedEvent event) {					
//					RecordList data = event.getData();
//					if(data != null && data.getLength() > 0) {
//						ufficioProponenteValueMap = new LinkedHashMap<String, String>();
//						for (int i = 0; i < data.getLength(); i++) {	
//							ufficioProponenteValueMap.put(data.get(i).getAttribute("idUo"), data.get(i).getAttribute("descrizione"));			
//						}
//						if(data.getLength() == 1) {
//							Record record = data.get(0);
//							String key = record.getAttribute("idUo");
//							mDynamicForm.setValue("idUo", key);
//							String value = record.getAttribute("descrizione");
//							mDynamicForm.setValue("idUo", (key != null && key.startsWith("UO")) ? key.substring(2) : key);
//							mDynamicForm.setValue("ufficioProponente", (key != null && key.startsWith("UO")) ? key.substring(2) : key);
//							if(value != null && !"".equals(value)) {
//								mDynamicForm.setValue("codRapido", value.substring(0, value.indexOf(" - ")));
//								mDynamicForm.setValue("descrizione", value.substring(value.indexOf(" - ") + 3));			
//							}
//							if(key != null && !"".equals(key)) {
//								mDynamicForm.setValue("flgUfficioGare", ((ProponentiItem)getItem()).getFlgUfficioGareProponentiMap().get(key));
//							}
//							afterSelezioneUoProponente();
//						}
//					}				
//				};
//			});
			
			// devo usare il dataSource clientOnly altrimenti con il valueMap non mi mantiene l'ordine della store
//			ufficioProponenteItem.setValueMap(((ProponentiItem)getItem()).getProponentiValueMap());
			ufficioProponenteItem.setOptionDataSource(((ProponentiItem)getItem()).getProponentiDS());
			ufficioProponenteItem.setAttribute("obbligatorio", true);
			ufficioProponenteItem.setAllowEmptyValue(false);			
			ufficioProponenteItem.addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {					
					manageChangedUoSelezionata();
				}
			});	
			
			items.add(idUoHiddenItem);
			items.add(descrizioneHiddenItem);
			items.add(flgUfficioGareHiddenItem);
			items.add(codRapidoHiddenItem);
			items.add(ufficioProponenteItem);
						
		}
		
		// Responsabile di Procedimento
		
		listaRdPItem = new ScrivaniaProponentiItem() {
			
			@Override
			public String getIdUoProponenteIdScrivania() {
				return getIdUoProponente();
			}	
			
			@Override
			public String getAltriParamLoadComboIdScrivania() {
				return ((ProponentiItem)getItem()).getAltriParamLoadComboIdScrivaniaRdP();
			}		
			
			@Override
			public int getSelectItemOrganigrammaWidth() {
				return ((ProponentiItem)getItem()).getSelectItemOrganigrammaWidth();
			}
			
			@Override
			public boolean skipValidation() {
				if(((ProponentiItem)getItem()).showIdScrivaniaRdP()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				if(((ProponentiItem)getItem()).isFromProponentiDetailInGridItem()) {					
					lVLayout.setWidth100();
					lVLayout.setPadding(11);
					lVLayout.setMargin(4);
					lVLayout.setIsGroup(true);
					lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
					if(((ProponentiItem)getItem()).isRequiredIdScrivaniaRdP()) {
						lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(((ProponentiItem)getItem()).getTitleIdScrivaniaRdP()) + "</span>");					
					} else {
						lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + ((ProponentiItem)getItem()).getTitleIdScrivaniaRdP() + "</span>");
					}
				}
				return lVLayout;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return ((ProponentiItem)getItem()).isFromProponentiDetailInGridItem();
			}						
		};
		listaRdPItem.setName("listaRdP");
		if(((ProponentiItem)getItem()).isFromProponentiDetailInGridItem()) {
			listaRdPItem.setShowTitle(false);	
		} else {
			listaRdPItem.setTitle(((ProponentiItem)getItem()).getTitleIdScrivaniaRdP());								
		}		
		listaRdPItem.setColSpan(20);
		if(!((ProponentiItem)getItem()).isFromProponentiDetailInGridItem()) {
			listaRdPItem.setNotReplicable(true);
		}
		if(((ProponentiItem)getItem()).isRequiredIdScrivaniaRdP()) {
			listaRdPItem.setAttribute("obbligatorio", true);
		}		
		listaRdPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((ProponentiItem)getItem()).showIdScrivaniaRdP();
			}
		});
		
		items.add(listaRdPItem);
		
		// Dirigente
		
		listaDirigentiItem = new ScrivaniaProponentiItem() {
			
			@Override
			public String getIdUoProponenteIdScrivania() {
				return getIdUoProponente();
			}	
			
			@Override
			public String getAltriParamLoadComboIdScrivania() {
				return ((ProponentiItem)getItem()).getAltriParamLoadComboIdScrivaniaDirigente();
			}		
			
			@Override
			public int getSelectItemOrganigrammaWidth() {
				return ((ProponentiItem)getItem()).getSelectItemOrganigrammaWidth();
			}
			
			@Override
			public boolean showTipoVistoScrivania() {
				return ((ProponentiItem)getItem()).showTipoVistoScrivaniaDirigente();
			}
			
			@Override
			public boolean isRequiredTipoVistoScrivania() {
				return ((ProponentiItem)getItem()).isRequiredTipoVistoScrivaniaDirigente();
			}
			
			@Override
			public boolean showFlgForzaDispFirmaScrivania() {
				return true;
			}
			
			@Override
			public boolean skipValidation() {
				if(((ProponentiItem)getItem()).showIdScrivaniaDirigente()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				if(((ProponentiItem)getItem()).isFromProponentiDetailInGridItem()) {									
					lVLayout.setWidth100();
					lVLayout.setPadding(11);
					lVLayout.setMargin(4);
					lVLayout.setIsGroup(true);
					lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
					if(((ProponentiItem)getItem()).isRequiredIdScrivaniaDirigente()) {
						lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(((ProponentiItem)getItem()).getTitleIdScrivaniaDirigente()) + "</span>");					
					} else {
						lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + ((ProponentiItem)getItem()).getTitleIdScrivaniaDirigente() + "</span>");
					}
				}
				return lVLayout;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return ((ProponentiItem)getItem()).isFromProponentiDetailInGridItem();
			}						
		};
		listaDirigentiItem.setName("listaDirigenti");
		if(((ProponentiItem)getItem()).isFromProponentiDetailInGridItem()) {
			listaDirigentiItem.setShowTitle(false);	
		} else {
			listaDirigentiItem.setTitle(((ProponentiItem)getItem()).getTitleIdScrivaniaDirigente());								
		}
		listaDirigentiItem.setColSpan(20);
		if(!((ProponentiItem)getItem()).isFromProponentiDetailInGridItem()) {
			listaDirigentiItem.setNotReplicable(true);
		}
		if(((ProponentiItem)getItem()).isRequiredIdScrivaniaDirigente()) {
			listaDirigentiItem.setAttribute("obbligatorio", true);
		}
		listaDirigentiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((ProponentiItem)getItem()).showIdScrivaniaDirigente();
			}
		});
		
		items.add(listaDirigentiItem);
				
		// Direttore
		
		listaDirettoriItem = new ScrivaniaProponentiItem() {
			
			@Override
			public String getIdUoProponenteIdScrivania() {
				return getIdUoProponente();
			}	
			
			@Override
			public String getAltriParamLoadComboIdScrivania() {
				return ((ProponentiItem)getItem()).getAltriParamLoadComboIdScrivaniaDirettore();
			}		
			
			@Override
			public int getSelectItemOrganigrammaWidth() {
				return ((ProponentiItem)getItem()).getSelectItemOrganigrammaWidth();
			}
			
			@Override
			public boolean showTipoVistoScrivania() {
				return ((ProponentiItem)getItem()).showTipoVistoScrivaniaDirettore();
			}
			
			@Override
			public boolean isRequiredTipoVistoScrivania() {
				return ((ProponentiItem)getItem()).isRequiredTipoVistoScrivaniaDirettore();
			}
			
			@Override
			public boolean showFlgForzaDispFirmaScrivania() {
				return true;
			}
			
			@Override
			public boolean skipValidation() {
				if(((ProponentiItem)getItem()).showIdScrivaniaDirettore()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				if(((ProponentiItem)getItem()).isFromProponentiDetailInGridItem()) {									
					lVLayout.setWidth100();
					lVLayout.setPadding(11);
					lVLayout.setMargin(4);
					lVLayout.setIsGroup(true);
					lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
					if(((ProponentiItem)getItem()).isRequiredIdScrivaniaDirettore()) {
						lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(((ProponentiItem)getItem()).getTitleIdScrivaniaDirettore()) + "</span>");					
					} else {
						lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + ((ProponentiItem)getItem()).getTitleIdScrivaniaDirettore() + "</span>");
					}
				}
				return lVLayout;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return ((ProponentiItem)getItem()).isFromProponentiDetailInGridItem();
			}						
		};
		listaDirettoriItem.setName("listaDirettori");
		if(((ProponentiItem)getItem()).isFromProponentiDetailInGridItem()) {
			listaDirettoriItem.setShowTitle(false);	
		} else {
			listaDirettoriItem.setTitle(((ProponentiItem)getItem()).getTitleIdScrivaniaDirettore());								
		}
		listaDirettoriItem.setColSpan(20);
		listaDirettoriItem.setNotReplicable(true);
		if(((ProponentiItem)getItem()).isRequiredIdScrivaniaDirettore()) {
			listaDirettoriItem.setAttribute("obbligatorio", true);
		}
		listaDirettoriItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((ProponentiItem)getItem()).showIdScrivaniaDirettore();
			}
		});
		
		items.add(listaDirettoriItem);

		/*
		SelectGWTRestDataSource idScrivaniaDirettoreDS = new SelectGWTRestDataSource("LoadComboScrivanieOrganigrammaDataSource", "idSv", FieldType.TEXT, new String[]{"codUo", "descrizione"}, true);
		if(((ProponentiItem)getItem()).getAltriParamLoadComboIdScrivaniaDirettore() != null) {
			idScrivaniaDirettoreDS.addParam("altriParamLoadCombo", ((ProponentiItem)getItem()).getAltriParamLoadComboIdScrivaniaDirettore());			
		}	
		idScrivaniaDirettoreItem = new FilteredSelectItemWithDisplay("idScrivaniaDirettore", idScrivaniaDirettoreDS) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid idScrivaniaDirettorePickListProperties = super.builPickListProperties();
				if(idScrivaniaDirettorePickListProperties == null) {
					idScrivaniaDirettorePickListProperties = new ListGrid();
				}
				if(!isCodUoDirettoreFieldWithFilter() && !isDescrizioneDirettoreFieldWithFilter()) {
					idScrivaniaDirettorePickListProperties.setShowFilterEditor(false); 
				}
				idScrivaniaDirettorePickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						String idUoProponente = getIdUoProponente();						
						GWTRestDataSource idScrivaniaDirettoreDS = (GWTRestDataSource) idScrivaniaDirettoreItem.getOptionDataSource();
						idScrivaniaDirettoreDS.addParam("uoProponente", idUoProponente);
//						idScrivaniaDirettoreDS.addParam("idSv", (String) idScrivaniaDirettoreFromLoadDettHiddenItem.getValue());
						idScrivaniaDirettoreItem.setOptionDataSource(idScrivaniaDirettoreDS);
						idScrivaniaDirettoreItem.invalidateDisplayValueCache();
					}
				});
				return idScrivaniaDirettorePickListProperties;				
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);				
				mDynamicForm.clearErrors(true);				
				mDynamicForm.setValue("codUoScrivaniaDirettore", record.getAttributeAsString("codUo"));
				mDynamicForm.setValue("desScrivaniaDirettore", record.getAttributeAsString("descrizione"));				
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.clearErrors(true);				
				mDynamicForm.setValue("idScrivaniaDirettore", "");
				mDynamicForm.setValue("codUoScrivaniaDirettore", "");
				mDynamicForm.setValue("desScrivaniaDirettore", "");				
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.clearErrors(true);				
					mDynamicForm.setValue("idScrivaniaDirettore", "");
					mDynamicForm.setValue("codUoScrivaniaDirettore", "");
					mDynamicForm.setValue("desScrivaniaDirettore", "");					
				}
			}
		};
		idScrivaniaDirettoreItem.setTitle(((ProponentiItem)getItem()).getTitleIdScrivaniaDirettore());
//		idScrivaniaDirettoreItem.setTitleOrientation(TitleOrientation.TOP);		
		idScrivaniaDirettoreItem.setWidth(((ProponentiItem)getItem()).getSelectItemOrganigrammaWidth());
		idScrivaniaDirettoreItem.setColSpan(4);
		idScrivaniaDirettoreItem.setStartRow(true);
		idScrivaniaDirettoreItem.setValueField("idSv");
		ListGridField descrizioneDirettoreField = new ListGridField("descrizione", "Descrizione");
		descrizioneDirettoreField.setWidth("*");
		descrizioneDirettoreField.setCanFilter(isDescrizioneDirettoreFieldWithFilter());
		ListGridField codUoDirettoreField = new ListGridField("codUo", "Cod. U.O.");
		codUoDirettoreField.setWidth(120);
		codUoDirettoreField.setCanFilter(isCodUoDirettoreFieldWithFilter());
		idScrivaniaDirettoreItem.setPickListFields(codUoDirettoreField, descrizioneDirettoreField);			
		idScrivaniaDirettoreItem.setAllowEmptyValue(false);
		idScrivaniaDirettoreItem.setAutoFetchData(false);
		idScrivaniaDirettoreItem.setAlwaysFetchMissingValues(true);
		idScrivaniaDirettoreItem.setFetchMissingValues(true);
//		if(((ProponentiItem)getItem()).isRequiredIdScrivaniaDirettore()) {
//			idScrivaniaDirettoreItem.setAttribute("obbligatorio", true);
//		} else {
//			idScrivaniaDirettoreItem.setAllowEmptyValue(true);
//		}
		idScrivaniaDirettoreItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((ProponentiItem)getItem()).isRequiredIdScrivaniaDirettore();
			}
		}));
		idScrivaniaDirettoreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((ProponentiItem)getItem()).isRequiredIdScrivaniaDirettore()) {
					idScrivaniaDirettoreItem.setAttribute("obbligatorio", true);
					idScrivaniaDirettoreItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((ProponentiItem)getItem()).getTitleIdScrivaniaDirettore()));
					idScrivaniaDirettoreItem.setAllowEmptyValue(false);
				} else {
					idScrivaniaDirettoreItem.setAttribute("obbligatorio", false);
					idScrivaniaDirettoreItem.setTitle(((ProponentiItem)getItem()).getTitleIdScrivaniaDirettore());
					idScrivaniaDirettoreItem.setAllowEmptyValue(true);
				}											
				return ((ProponentiItem)getItem()).showIdScrivaniaDirettore();
			}
		});	
		
		items.add(idScrivaniaDirettoreItem);
				
		idScrivaniaDirettoreFromLoadDettHiddenItem = new HiddenItem("idScrivaniaDirettoreFromLoadDett");
		items.add(idScrivaniaDirettoreFromLoadDettHiddenItem);
		
		codUoScrivaniaDirettoreHiddenItem = new HiddenItem("codUoScrivaniaDirettore");
		items.add(codUoScrivaniaDirettoreHiddenItem);
				
		desScrivaniaDirettoreHiddenItem = new HiddenItem("desScrivaniaDirettore");
		items.add(desScrivaniaDirettoreHiddenItem);
		
		tipoVistoScrivaniaDirettoreItem = new TextItem("tipoVistoScrivaniaDirettore", "Tipo visto");
		tipoVistoScrivaniaDirettoreItem.setWidth(220);
		tipoVistoScrivaniaDirettoreItem.setColSpan(1);
		if(((ProponentiItem)getItem()).isRequiredTipoVistoScrivaniaDirettore()) {
			tipoVistoScrivaniaDirettoreItem.setAttribute("obbligatorio", true);
		}
		tipoVistoScrivaniaDirettoreItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((ProponentiItem)getItem()).isRequiredTipoVistoScrivaniaDirettore();
			}
		}));
		tipoVistoScrivaniaDirettoreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((ProponentiItem)getItem()).showTipoVistoScrivaniaDirettore();
			}
		});
		items.add(tipoVistoScrivaniaDirettoreItem);
		
		flgForzaDispFirmaScrivaniaDirettoreItem = new CheckboxItem("flgForzaDispFirmaScrivaniaDirettore", "forza disponibilità firma");
		flgForzaDispFirmaScrivaniaDirettoreItem.setDefaultValue(false);
		flgForzaDispFirmaScrivaniaDirettoreItem.setColSpan(1);
		flgForzaDispFirmaScrivaniaDirettoreItem.setWidth("*");
		flgForzaDispFirmaScrivaniaDirettoreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((ProponentiItem)getItem()).showIdScrivaniaDirettore();
			}
		});
		items.add(flgForzaDispFirmaScrivaniaDirettoreItem);	
		*/
		
		mDynamicForm.setFields(items.toArray(new FormItem[items.size()]));
		
		mDynamicForm.setNumCols(20);
		mDynamicForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		addChild(mDynamicForm);

	}
	
	public String getIdUoProponente() {
		return mDynamicForm.getValue("idUo") != null ? mDynamicForm.getValueAsString("idUo") : null;
	}
	
	public boolean isCodUoDirettoreFieldWithFilter() {
		return isAltriParamWithNriLivelliUo(((ProponentiItem)getItem()).getAltriParamLoadComboIdScrivaniaDirettore());
	}
	
	public boolean isDescrizioneDirettoreFieldWithFilter() {
		return isAltriParamWithStrInDes(((ProponentiItem)getItem()).getAltriParamLoadComboIdScrivaniaDirettore());
	}
	
	public boolean isAltriParamWithNriLivelliUo(String altriParamLoadCombo) {
		return altriParamLoadCombo != null && altriParamLoadCombo.indexOf("NRI_LIVELLI_UO|*|") != -1;
	}
	
	public boolean isAltriParamWithStrInDes(String altriParamLoadCombo) {
		return altriParamLoadCombo != null && altriParamLoadCombo.indexOf("STR_IN_DES|*|") != -1;
	}
		
	public boolean isAltriParamWithIdUo(String altriParamLoadCombo) {
		return altriParamLoadCombo != null && altriParamLoadCombo.indexOf("ID_UO|*|") != -1;
	}
	
	@Override
	public boolean validate() {
		mDynamicForm.clearErrors(true);
		Boolean valid = super.validate();
		for (FormItem item : mDynamicForm.getFields()) {
			if (item instanceof ReplicableItem) {
				ReplicableItem lReplicableItem = (ReplicableItem) item;
				boolean itemValid = lReplicableItem.validate();
				valid = itemValid && valid;
				if(!itemValid) {
					if(lReplicableItem != null && lReplicableItem.getForm() != null && lReplicableItem.getForm().getDetailSection() != null) {
						lReplicableItem.getForm().getDetailSection().open();
					}
				}
			}
		}	
		if(mDynamicForm.getErrors() != null && !mDynamicForm.getErrors().isEmpty()) {
			for(Object key : mDynamicForm.getErrors().keySet()) {
				FormItem item = mDynamicForm.getItem((String) key);
				if(item != null && item.getForm() != null && item.getForm().getDetailSection() != null) {
					item.getForm().getDetailSection().open();
				}
			}
		}
		return valid;
	}	

	@Override
	public void editRecord(Record record) {
		mDynamicForm.clearErrors(true);
		if(organigrammaItem != null) {
			if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma"))) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("organigramma"), record.getAttribute("descrizione"));
				organigrammaItem.setValueMap(valueMap);
			}		
			GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
			if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma"))) {
				organigrammaDS.addParam("idUoSv", record.getAttributeAsString("idUo"));
				organigrammaDS.addParam("flgUoSv", "UO");
			} else {
				organigrammaDS.addParam("idUoSv", null);
				organigrammaDS.addParam("flgUoSv", null);
			}
			organigrammaItem.setOptionDataSource(organigrammaDS);					 
		}	
		
		boolean isChangedUoSelezionata = false;
		if(ufficioProponenteItem != null) {
			if(record.getAttribute("ufficioProponente") == null || "".equalsIgnoreCase(record.getAttribute("ufficioProponente"))) {
				if (((ProponentiItem)getItem()).getProponentiValueMap().size() == 1) {
					String key = ((ProponentiItem)getItem()).getProponentiValueMap().keySet().toArray(new String[1])[0];
					String value = ((ProponentiItem)getItem()).getProponentiValueMap().get(key);
					record.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());					
					record.setAttribute("idUo", (key != null && key.startsWith("UO")) ? key.substring(2) : key);
					record.setAttribute("ufficioProponente", (key != null && key.startsWith("UO")) ? key.substring(2) : key);
					if(value != null && !"".equals(value)) {
						record.setAttribute("codRapido", value.substring(0, value.indexOf(" - ")));
						record.setAttribute("descrizione", value.substring(value.indexOf(" - ") + 3));				
					}
					record.setAttribute("flgUfficioGare", ((ProponentiItem)getItem()).getFlgUfficioGareProponentiMap().get(key));
					isChangedUoSelezionata = true;
				} else if (((ProponentiItem)getItem()).getSelezioneProponentiValueMap().size() == 1) {
					String key = ((ProponentiItem)getItem()).getSelezioneProponentiValueMap().keySet().toArray(new String[1])[0];
					String value = ((ProponentiItem)getItem()).getSelezioneProponentiValueMap().get(key);
					record.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
					record.setAttribute("idUo", (key != null && key.startsWith("UO")) ? key.substring(2) : key);
					record.setAttribute("ufficioProponente", (key != null && key.startsWith("UO")) ? key.substring(2) : key);
					if(value != null && !"".equals(value)) {
						record.setAttribute("codRapido", value.substring(0, value.indexOf(" - ")));
						record.setAttribute("descrizione", value.substring(value.indexOf(" - ") + 3));				
					}
					record.setAttribute("flgUfficioGare", ((ProponentiItem)getItem()).getFlgUfficioGareProponentiMap().get(key));
					isChangedUoSelezionata = true;
				}
			}
		}
		
//		if(idScrivaniaDirettoreItem != null) {
//			manageLoadSelectInEditRecord(record, idScrivaniaDirettoreItem, "idScrivaniaDirettore", new String[]{"codUoScrivaniaDirettore", "desScrivaniaDirettore"}, "idSv");
//		}
		
		super.editRecord(record);
		
		if(isChangedUoSelezionata) {
			manageChangedUoSelezionata();
		}
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
//		if(ufficioProponenteItem != null) {
//			ufficioProponenteItem.setCanEdit(((ProponentiItem)getItem()).isEditableIdUo() ? canEdit : false);
//		}
//		if(codRapidoItem != null) {
//			codRapidoItem.setCanEdit(((ProponentiItem)getItem()).isEditableIdUo() ? canEdit : false);
//		}
//		if(organigrammaItem != null) {
//			organigrammaItem.setCanEdit(((ProponentiItem)getItem()).isEditableIdUo() ? canEdit : false);
//		}
		if(listaRdPItem != null) {
			if(((ProponentiItem)getItem()).isEditableIdScrivaniaRdP()) {
				listaRdPItem.setCanEdit(canEdit);
				if (canEdit && listaRdPItem.getTotalMembers() == 0) {
					listaRdPItem.onClickNewButton();						
				}
			} else {
				listaRdPItem.setCanEdit(false);
			}			
		}
		if(listaDirigentiItem != null) {
			if(((ProponentiItem)getItem()).isEditableIdScrivaniaDirigente()) {
				listaDirigentiItem.setCanEdit(canEdit);
				if (canEdit && listaDirigentiItem.getTotalMembers() == 0) {
					listaDirigentiItem.onClickNewButton();						
				}
			} else {
				listaDirigentiItem.setCanEdit(false);
			}					
		}
		if(listaDirettoriItem != null) {
			if(((ProponentiItem)getItem()).isEditableIdScrivaniaDirettore()) {
				listaDirettoriItem.setCanEdit(canEdit);
				if (canEdit && listaDirettoriItem.getTotalMembers() == 0) {
					listaDirettoriItem.onClickNewButton();						
				}
			} else {
				listaDirettoriItem.setCanEdit(false);
			}					
		}		
//		if(idScrivaniaDirettoreItem != null) {
//			idScrivaniaDirettoreItem.setCanEdit(((ProponentiItem)getItem()).isEditableIdScrivaniaDirettore() ? canEdit : false);
//		}
//		if(tipoVistoScrivaniaDirettoreItem != null) {
//			tipoVistoScrivaniaDirettoreItem.setCanEdit(((ProponentiItem)getItem()).isEditableIdScrivaniaDirettore() ? canEdit : false);			
//		}
//		if(flgForzaDispFirmaScrivaniaDirettoreItem != null) {
//			flgForzaDispFirmaScrivaniaDirettoreItem.setCanEdit(((ProponentiItem)getItem()).isEditableIdScrivaniaDirettore() ? canEdit : false);			
//		}
		mDynamicForm.markForRedraw();
	}
	
	public void manageChangedUoSelezionata() {
		if(listaRdPItem != null && isAltriParamWithIdUo(((ProponentiItem)getItem()).getAltriParamLoadComboIdScrivaniaRdP())) {
			listaRdPItem.manageChangedUoSelezionata();
		}
		if(listaDirigentiItem != null && isAltriParamWithIdUo(((ProponentiItem)getItem()).getAltriParamLoadComboIdScrivaniaDirigente())) {
			listaDirigentiItem.manageChangedUoSelezionata();
		}
		if(listaDirettoriItem != null && isAltriParamWithIdUo(((ProponentiItem)getItem()).getAltriParamLoadComboIdScrivaniaDirettore())) {
			listaDirettoriItem.manageChangedUoSelezionata();
		}
//		if(idScrivaniaDirettoreItem != null && isAltriParamWithIdUo(((ProponentiItem)getItem()).getAltriParamLoadComboIdScrivaniaDirettore())) {
//			resetSelectItemAfterChangedIdUoProponente(idScrivaniaDirettoreItem);
//		}
		((ProponentiItem)getItem()).manageChangedUoSelezionata();
	}
	
	public void resetSelectItemAfterChangedIdUoProponente(final SelectItem item) {
		if(item != null) {
			final String value = item.getValueAsString();
			item.fetchData(new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					RecordList data = response.getDataAsRecordList();
					boolean isObbligatorio = item.getAttributeAsBoolean("obbligatorio") != null && item.getAttributeAsBoolean("obbligatorio");
					if (isObbligatorio && data.getLength() == 1) {
						item.setValue(data.get(0).getAttribute("key"));						
						item.fireEvent(new ChangedEvent(item.getJsObj()));
					} else if(value != null && !"".equals(value)) {
						boolean trovato = false;
						if (data.getLength() > 0) {						
							for (int i = 0; i < data.getLength(); i++) {
								String key = data.get(i).getAttribute("key");
								if (value.equals(key)) {
									trovato = true;
									break;
								}
							}
						}
						if (!trovato) {
							item.setValue("");
							item.fireEvent(new ChangedEvent(item.getJsObj()));
						}
					}
				}
			});
		}
	}
	
	/* DA TOGLIERE PERCHE' CREA PROBLEMI QUANDO RICARICO IL DETTAGLIO (NON MOSTRA IL VALORE SALVATO)
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		if(getEditing() && (codRapidoItem.getValue() == null || "".equals(codRapidoItem.getValue()))) {
			if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
				mDynamicForm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
			} else {
				mDynamicForm.setValue("codRapido", "");
			}			
		}
	}
	*/

	public void setFormValuesFromRecord(Record record) {
		mDynamicForm.clearErrors(true);
		final String idOrganigramma = record.getAttribute("idUoSvUt");
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
		// mDynamicForm.setValue("descrizione", ""); // da settare		
		mDynamicForm.setValue("codRapido", record.getAttribute("codRapidoUo"));
		mDynamicForm.clearErrors(true);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				organigrammaItem.fetchData(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						RecordList data = response.getDataAsRecordList();
						if(idOrganigramma != null && !"".equals(idOrganigramma)) {
							if (data.getLength() > 0) {
								for (int i = 0; i < data.getLength(); i++) {
									String idUo = data.get(i).getAttribute("idUo");
									if (idUo != null && idUo.equals(idOrganigramma)) {
										mDynamicForm.setValue("descrizione", data.get(i).getAttribute("descrizioneOrig"));
										mDynamicForm.setValue("flgUfficioGare", data.get(i).getAttribute("flgUfficioGare"));														
										break;
									}
								}
							}
						}
						manageChangedUoSelezionata();						
					}
				});				
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

	public class ProponentiLookupOrganigramma extends LookupOrganigrammaPopup {

		public ProponentiLookupOrganigramma(Record record) {
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
			return null;
		}

	}
}