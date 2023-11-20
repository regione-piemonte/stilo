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
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
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
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class AgganciaDocumentiFascicoliStrutturaUOPopup extends ModalWindow {
	
	private AgganciaDocumentiFascicoliStrutturaUOPopup window;
	
	// DetailSection
	private DetailSection formMainSection;
	
	// DynamicForm
	private DynamicForm formMain;
	
	// FilteredSelectItemWithDisplay
	private FilteredSelectItemWithDisplay organigrammaItem;
	
	// CheckboxItem
	private CheckboxItem flgInclSottoUoItem;
	private CheckboxItem flgInclScrivVirtItem;
	private CheckboxItem flgVisDocFascRisItem;
	
	// HiddenItem
	private HiddenItem idUoItem;
	private HiddenItem denominazioneUoItem;
	
	// ExtendedTextItem
	private ExtendedTextItem codRapidoItem;
	
	// SpacerItem
	private SpacerItem spacer1Item;
	private SpacerItem spacer2Item;
	
	// ImgButtonItem
	private ImgButtonItem lookupOrganigrammaButton;
	
	private String idUdProtocollo;
	private String tipoAssegnatari;
	
	private String mode;
	private String title;

	public AgganciaDocumentiFascicoliStrutturaUOPopup(Record record, String title){
		this(record, title, "view");
	}
	
	public AgganciaDocumentiFascicoliStrutturaUOPopup(Record record, String title, String mode){
		
		super("aggancia_documenti_fascicoli_struttura_uo", true);
		
		window = this;
		
		this.title = title;
		
		setHeight(535);
		setWidth(900);	
		setTitle(title);
        settingsMenu.removeItem(separatorMenuItem);
        settingsMenu.removeItem(autoSearchMenuItem);
		
        setMode(mode);
        
    	setIdUdProtocollo(null);	
		
		setTipoAssegnatari("UO");
		
		disegnaForm();
		
		initForm(record);
			
		final Button okButton = new Button("Ok");   
		okButton.setIcon("ok.png");
		okButton.setIconSize(16); 
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(formMain.validate()) {
					
					final Record formRecord = getRecordToSave();
					okButton.focusAfterGroup();
					onClickOkButton(formRecord, new DSCallback() {	
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							window.markForDestroy();
						}
					});							
				}
			}
		});
		
		Button annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {	
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				window.markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(okButton);
		_buttons.addMember(annullaButton);	
		_buttons.setAutoDraw(true);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		layout.addMember(formMainSection);

		layout.addMember(lVLayoutSpacer);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
		setBody(portletLayout);
	}
	
	private void initForm(Record record) {
			
		// CODICE RAPIDO
		codRapidoItem.setValue(record.getAttribute("codRapido"));
		formMain.setValue("codRapido",      record.getAttribute("codRapido")); 
		
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && (codRapidoItem.getValueAsString() == null || "".equalsIgnoreCase(codRapidoItem.getValueAsString()))) {	
			codRapidoItem.setValue(AurigaLayout.getCodRapidoOrganigramma());
		}
		
		// combo ORGANIGRAMMA
		if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma")) && record.getAttribute("descrizione") != null &&  !record.getAttribute("descrizione").equalsIgnoreCase("")  ) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("organigramma"), record.getAttribute("descrizione"));
			organigrammaItem.setValueMap(valueMap);
			organigrammaItem.setValue(record.getAttribute("organigramma"));
		}
		
		denominazioneUoItem.setValue(record.getAttribute("descrizione"));
		formMain.setValue("denominazioneUo", record.getAttribute("descrizione")); 
		
		// ID UO
		idUoItem.setValue(record.getAttribute("idUo"));
		formMain.setValue("idUo",      record.getAttribute("idUo"));  
			
		// CHECK SOTTO UO
		flgInclSottoUoItem.setValue(record.getAttributeAsBoolean("flgIncluseSottoUo"));
		formMain.setValue("flgIncluseSottoUo",      record.getAttributeAsBoolean("flgIncluseSottoUo"));  
		
		// CHECK INCLUSE SCRIVANIE
		flgInclScrivVirtItem.setValue(record.getAttributeAsBoolean("flgIncluseScrivanie"));
		formMain.setValue("flgIncluseScrivanie",      record.getAttributeAsBoolean("flgIncluseScrivanie"));  
		
		// CHECK anche doc. e fasc. riservati
		flgVisDocFascRisItem.setValue(record.getAttributeAsBoolean("flgVisDocFascRis"));
		formMain.setValue("flgVisDocFascRis",      record.getAttributeAsBoolean("flgVisDocFascRis"));  
	}
	
	private void disegnaForm() {

		formMain = new DynamicForm();
		formMain.setKeepInParentRect(true);
		formMain.setWidth100();
		formMain.setHeight100();
		formMain.setCellPadding(5);
		formMain.setAlign(Alignment.CENTER);
		formMain.setOverflow(Overflow.VISIBLE);
		formMain.setTop(50);
		formMain.setWrapItemTitles(false);
		formMain.setNumCols(7);
		formMain.setColWidths(1, 1, 1, 1, 1, 1, "*");
		
		// HIDDEN
		idUoItem                          = new HiddenItem("idUo");
		denominazioneUoItem				  = new HiddenItem("denominazioneUo");
		
		spacer1Item = new SpacerItem();
		spacer1Item.setWidth(20);
		spacer1Item.setColSpan(1);
		spacer1Item.setStartRow(true);
		
		spacer2Item = new SpacerItem();
		spacer2Item.setWidth(20);
		spacer2Item.setColSpan(1);
		spacer2Item.setStartRow(true);
		
		// CHECK SOTTO UO
		flgInclSottoUoItem = new CheckboxItem("flgIncluseSottoUo", "anche sotto-U.O.");
		flgInclSottoUoItem.setColSpan(1);
		flgInclSottoUoItem.setWidth(10);
		
		// CHECK INCLUSE SCRIVANIE
		flgInclScrivVirtItem = new CheckboxItem("flgIncluseScrivanie", "anche alle postazioni utente");
		flgInclScrivVirtItem.setColSpan(1);
		flgInclScrivVirtItem.setWidth(10);
		
		// CHECK anche doc. e fasc. riservati
		flgVisDocFascRisItem = new CheckboxItem("flgVisDocFascRis", "anche doc. e fasc. riservati");
		flgVisDocFascRisItem.setColSpan(1);
		flgVisDocFascRisItem.setWidth(10);
		
		creaSelectOrganigramma();
				
		formMain.setFields(       
            codRapidoItem,  
            lookupOrganigrammaButton, 
            organigrammaItem,
            spacer1Item,
            flgInclSottoUoItem,
            spacer1Item,
            flgInclScrivVirtItem,
            spacer1Item,
            flgVisDocFascRisItem,			            
		    idUoItem, 
	        denominazioneUoItem
		);	
		
		formMainSection = new DetailSection(title, true, true, false, formMain);
	}

	private void creaSelectOrganigramma() {
		
		String tipoAssegnatari = "UO";
		
		codRapidoItem = new ExtendedTextItem("codRapido", I18NUtil.getMessages().protocollazione_detail_uoItem_title());
		codRapidoItem.setWidth(120);
		codRapidoItem.setColSpan(1);
		codRapidoItem.setRequired(true);		
		codRapidoItem.setStartRow(true);
		codRapidoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				formMain.setValue("idUo", (String) null);
				formMain.setValue("denominazioneUo", (String) null);
				formMain.setValue("organigramma", (String) null);
				formMain.clearErrors(true);
				final String value = codRapidoItem.getValueAsString();
				
					GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
					
						organigrammaDS.addParam("flgSoloValide", "0");
						organigrammaDS.addParam("isTipoAssL", "true");				
					
					organigrammaItem.setOptionDataSource(organigrammaDS);
					
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
											formMain.setValue("denominazioneUo", data.get(i).getAttribute("descrizioneOrig"));
											formMain.setValue("organigramma", data.get(i).getAttribute("id"));
											formMain.setValue("idUo", data.get(i).getAttribute("idUo"));
											formMain.setValue("typeNodo", data.get(i).getAttribute("typeNodo"));
											formMain.setValue("flgUoPuntoProtocollo", data.get(i).getAttribute("flgPuntoProtocollo"));
											formMain.clearErrors(true);
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
		
		SelectGWTRestDataSource lGwtRestDataSourceOrganigramma = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT, new String[] { "descrizione" }, true);
		lGwtRestDataSourceOrganigramma.addParam("tipoAssegnatari", tipoAssegnatari);
		lGwtRestDataSourceOrganigramma.addParam("finalita", "ALTRO");
		lGwtRestDataSourceOrganigramma.addParam("idUd", getIdUdProtocollo());

		organigrammaItem = new FilteredSelectItemWithDisplay("organigramma", lGwtRestDataSourceOrganigramma) {

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
				formMain.setValue("codRapido", record.getAttributeAsString("codice"));
				formMain.setValue("idUo", record.getAttributeAsString("idUo"));
				formMain.setValue("denominazioneUo", record.getAttributeAsString("descrizioneOrig"));
				formMain.clearErrors(true);
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
				formMain.setValue("organigramma", "");
				formMain.setValue("codRapido", "");
				if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
					formMain.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
				} else {
					formMain.setValue("codRapido", "");
				}
				formMain.setValue("idUo", "");
				formMain.setValue("denominazioneUo", "");
				formMain.clearErrors(true);
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
					formMain.setValue("organigramma", "");
					formMain.setValue("codRapido", "");
					if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
						formMain.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						formMain.setValue("codRapido", "");
					}
					formMain.setValue("idUo", "");
					formMain.setValue("denominazioneUo", "");
					formMain.clearErrors(true);
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
				return record.getAttribute("descrizioneEstesa");
			}
		});
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			codiceField.setCanFilter(false);
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
		organigrammaItem.setOptionDataSource(lGwtRestDataSourceOrganigramma);
		organigrammaItem.setShowTitle(false);
		organigrammaItem.setWidth(480);
		organigrammaItem.setClearable(true);
		organigrammaItem.setShowIcons(true);
		organigrammaItem.setRequired(true);
		organigrammaItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
			}
		});
		organigrammaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return true;
			}
		});
		organigrammaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return true;
			}
		}));
		ListGrid pickListProperties = organigrammaItem.getPickListProperties();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String codRapido = formMain.getValueAsString("codRapido");
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
				organigrammaDS.addParam("codice", codRapido);
				organigrammaDS.addParam("finalita", "ALTRO");
				organigrammaDS.addParam("idUd", getIdUdProtocollo());				
				organigrammaDS.addParam("flgSoloValide", "0");
				organigrammaDS.addParam("isTipoAssL", "true");
				
				organigrammaItem.setOptionDataSource(organigrammaDS);
				organigrammaItem.invalidateDisplayValueCache();
			}
		});
		organigrammaItem.setPickListProperties(pickListProperties);

		lookupOrganigrammaButton = new ImgButtonItem("lookupOrganigrammaButton", "lookup/organigramma.png", I18NUtil.getMessages().protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaButton.setColSpan(1);
		lookupOrganigrammaButton.setWidth(10);
		lookupOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				AssegnazioneLookupOrganigramma lookupOrganigrammaPopup = new AssegnazioneLookupOrganigramma(null);
				lookupOrganigrammaPopup.show();
			}
		});
		lookupOrganigrammaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return true;
			}
		});

		ListGridField codiceRapidoGruppoField = new ListGridField("codiceRapidoGruppo", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codiceRapidoGruppoField.setWidth(70);
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

	}

	public String getIdUdProtocollo() {
		return idUdProtocollo;
	}

	public void setIdUdProtocollo(String idUdProtocollo) {
		this.idUdProtocollo = idUdProtocollo;
	}
		
	public class AssegnazioneLookupOrganigramma extends LookupOrganigrammaPopup {

		public AssegnazioneLookupOrganigramma(Record record) {
			super(record, true, getFlgIncludiUtenti());
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
			return "ALTRO";
		}

		@Override
		public String getIdUd() {
			return getIdUdProtocollo();
		}
		
		@Override
		public Boolean getFlgSoloAttive() {
			// Se tipo =  L allora FlgSoloAttive = 0.
			// Se tipo <> L allora FlgSoloAttive = 1.
			return true;
		}
	}
		
	public void setFormValuesFromRecord(Record record) {
		String idOrganigramma = record.getAttribute("idUoSvUt");
		String tipo = record.getAttribute("tipo");
		int pos = tipo.indexOf("_");
		if (pos != -1) {
			tipo = tipo.substring(0, pos);
		}
		formMain.setValue("organigramma", tipo + idOrganigramma);
		formMain.setValue("idUo", idOrganigramma);
		formMain.setValue("codRapido", record.getAttribute("codRapidoUo"));
		formMain.setValue("denominazioneUo", record.getAttribute("nome"));
		formMain.setValue("flgIncluseSottoUo", record.getAttributeAsBoolean("flgIncluseSottoUo"));
		formMain.setValue("flgIncluseScrivanie", record.getAttributeAsBoolean("flgIncluseScrivanie"));
		formMain.setValue("flgVisDocFascRis", record.getAttributeAsBoolean("flgVisDocFascRis"));
		
		formMain.clearErrors(true);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {
				organigrammaItem.fetchData();
			}
		});
	}

	public Record getFormValuesAsRecord() {
		return formMain.getValuesAsRecord();
	}
		
	public Integer getFlgIncludiUtenti() {
		Integer flgIncludiUtenti = new Integer(1);
		String tipoAssegnatari = getTipoAssegnatari();
		if(tipoAssegnatari != null) {
			if("UO".equals(tipoAssegnatari)) {
				flgIncludiUtenti = new Integer(0);
			} else if("SV".equals(tipoAssegnatari)) {
				flgIncludiUtenti = new Integer(2);				 
			} 
		}
		return flgIncludiUtenti;
	}

	public String getTipoAssegnatari() {
		return tipoAssegnatari;
	}

	public void setTipoAssegnatari(String tipoAssegnatari) {
		this.tipoAssegnatari = tipoAssegnatari;
	}
		
	public abstract void onClickOkButton(Record formRecord, DSCallback callback);

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
		
	public Record getRecordToSave() {
		Record lRecordToSave = new Record();
		// Salvo i form
		addFormValues(lRecordToSave, formMain);
		return lRecordToSave;
	}
		
	protected static void addFormValues(Record record, DynamicForm form) {
		if (form != null) {
			try {
				JSOHelper.addProperties(record.getJsObj(), form.getValuesAsRecord().getJsObj());
			} catch (Exception e) {
			}
		}
	}
		
	public void setCanEdit(boolean canEdit, DynamicForm form) {
		if (form != null) {
			form.setEditing(canEdit);
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
					if (item instanceof DateItem || item instanceof DateTimeItem) {
						TextItem textItem = new TextItem();
						textItem.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
						if (item instanceof DateItem) {
							((DateItem) item).setTextFieldProperties(textItem);
						} else if (item instanceof DateTimeItem) {
							((DateTimeItem) item).setTextFieldProperties(textItem);
						}
					} else {
						item.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
					}
					item.setCanEdit(canEdit);
					item.redraw();
				}
				if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
			}
		}
	}		
}