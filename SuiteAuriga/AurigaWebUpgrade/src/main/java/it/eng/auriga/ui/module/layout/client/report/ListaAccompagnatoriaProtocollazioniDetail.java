/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class ListaAccompagnatoriaProtocollazioniDetail extends CustomDetail {
	
	protected Map<String, String> property = new HashMap<String, String>();
	private SelectGWTRestDataSource lGwtRestDataSourceUtenti;
	private GWTRestDataSource lGwtRestDataSourceDestinatari;
	
	private DynamicForm mDynamicForm;
	private CheckboxItem flgProtSoloMieItem; 
	private CheckboxItem flgProtUtenteSelezionatoItem;
	private FilteredSelectItemWithDisplay utenteProtocollazioneItem;
	private CheckboxItem flgProtDiTuttiItem; 
	
	private DynamicForm mDynamicFormPeriodo;
	private DateItem dtInizioItem;
	private DateItem dtFineItem;
	
	private DynamicForm mDynamicFormDestinatari;
	private CheckboxItem flgDestinatariTuttiItem; 
	private CheckboxItem flgDestinatariSelezionatiItem; 
	private SelectItem destinatariItem;	
//	private HiddenItem listaOrganigrammaItem;
//	private TextItem listaDescrizioniItem;
//	private ImgButtonItem editButton;
	
	private VLayout lVLayout;
	private ToolStrip bottoniToolStrip;
	
	private DetailToolStripButton stampaButton;

	public ListaAccompagnatoriaProtocollazioniDetail(String nomeEntita) {
		super(nomeEntita);
		
		// LAYOUT MAIN
		lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(1);
		
		lGwtRestDataSourceUtenti = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT, new String[]{"cognomeNome", "username"}, true);
		
		if (AurigaLayout.getIdUOPuntoProtAttivato() != null && !"".equals(AurigaLayout.getIdUOPuntoProtAttivato())) {
			buildSceltaForm();		
			DetailSection detailSectionScelta = new DetailSection(I18NUtil.getMessages().listaAccompagnatoriaProtocollazioni_detail_detailSectionSceltaTitle(), 
					true, true, true, mDynamicForm);
			lVLayout.addMember(detailSectionScelta);	
		}
		
		buildDateForm();
		
		lGwtRestDataSourceDestinatari = new GWTRestDataSource("LoadComboAssegnatariXListaAccompRegDatasource", "idUo", FieldType.TEXT);
		
		buildDestinatariForm();
		
		DetailSection detailSectionPeriodo = new DetailSection(I18NUtil.getMessages().listaAccompagnatoriaProtocollazioni_detail_detailSectionPeriodoTitle(),
				true, true, true, mDynamicFormPeriodo);
		lVLayout.addMember(detailSectionPeriodo);	
		
		DetailSection detailSectionDestinatari = new DetailSection(I18NUtil.getMessages().listaAccompagnatoriaProtocollazioni_detail_detailSectionDestinatariTitle(),
				true, true, true, mDynamicFormDestinatari);
		lVLayout.addMember(detailSectionDestinatari);	
		
		// Bottoni Stampa e ANNULLA
		buildBottoni();
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
		addMember(bottoniToolStrip);
		
		setCanEdit(true);		
	}
	
	private void buildSceltaForm() {
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setNumCols(10);
		mDynamicForm.setColWidths(1,1,1,1,1,1,1,1,"*","*");
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setCellPadding(2);
		
		flgProtSoloMieItem = new CheckboxItem("flgProtSoloMie", I18NUtil.getMessages().listaAccompagnatoriaProtocollazioni_detail_flgProtSoloMie_title());
		flgProtSoloMieItem.setWidth(150);
		flgProtSoloMieItem.setValue(true);
		flgProtSoloMieItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				validateCheckSceltaUtenti(event.getItem().getName());
				destinatariItem.clearValue();
				destinatariItem.fetchData();
				mDynamicForm.markForRedraw();
			}
		});
		
		flgProtUtenteSelezionatoItem = new CheckboxItem("flgProtUtenteSelezionato", I18NUtil.getMessages().listaAccompagnatoriaProtocollazioni_detail_flgProtUtenteSelezionato_title());
		flgProtUtenteSelezionatoItem.setWidth(150);
		flgProtUtenteSelezionatoItem.setHeight(30);
		flgProtUtenteSelezionatoItem.setStartRow(true);
		flgProtUtenteSelezionatoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				validateCheckSceltaUtenti(event.getItem().getName());
				destinatariItem.clearValue();
				destinatariItem.fetchData();
				mDynamicForm.markForRedraw();
			}
		});
		CustomValidator lCustomProtUtenteValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				boolean isValid = true;
				if(flgProtUtenteSelezionatoItem.getValueAsBoolean() != null && flgProtUtenteSelezionatoItem.getValueAsBoolean()){
					if(utenteProtocollazioneItem.getValue() == null || "".equals(utenteProtocollazioneItem.getValue())){
						isValid = false;
					}
				}
				return isValid;
			}
		};
		lCustomProtUtenteValidator.setErrorMessage("Attenzione, occorre selezionare un utente per la scelta selezionata");
		flgProtUtenteSelezionatoItem.setValidators(lCustomProtUtenteValidator);
		
		utenteProtocollazioneItem = new FilteredSelectItemWithDisplay("utenteProtocollazione", lGwtRestDataSourceUtenti){
			@Override
			public void onOptionClick(final Record record) {
				super.onOptionClick(record);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						LinkedHashMap<String, String> utentiValueMap = new LinkedHashMap<String, String>();
						utentiValueMap.put(record.getAttributeAsString("idUtente"), record.getAttributeAsString("cognomeNome") + " ** " + record.getAttributeAsString("username"));
						utenteProtocollazioneItem.setValueMap(utentiValueMap);
					}
				});
			}			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						LinkedHashMap<String, String> utentiValueMap = new LinkedHashMap<String, String>();
						utenteProtocollazioneItem.setValueMap(utentiValueMap);
						mDynamicForm.setValue("utenteProtocollazione", "");
					}
				});			
			};		
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							LinkedHashMap<String, String> utentiValueMap = new LinkedHashMap<String, String>();
							utenteProtocollazioneItem.setValueMap(utentiValueMap);
							mDynamicForm.setValue("utenteProtocollazione", "");
						}
					});			
				}
			}
		};		
		ListGridField utentiCodiceField = new ListGridField("codice", "Cod.");
		utentiCodiceField.setWidth(80);		
		ListGridField utentiCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");	
		ListGridField utentiUsernameField = new ListGridField("username", "Username");				
		utentiUsernameField.setWidth(170);		
		utenteProtocollazioneItem.setPickListFields(utentiCodiceField, utentiCognomeNomeField, utentiUsernameField);
		utenteProtocollazioneItem.setFilterLocally(true);
		utenteProtocollazioneItem.setValueField("idUtente");  		
		utenteProtocollazioneItem.setShowTitle(false);
		utenteProtocollazioneItem.setOptionDataSource(lGwtRestDataSourceUtenti); 
		utenteProtocollazioneItem.setWidth(380);
		utenteProtocollazioneItem.setPickListWidth(600);
		utenteProtocollazioneItem.setClearable(true);
		utenteProtocollazioneItem.setShowIcons(true);
		utenteProtocollazioneItem.setAutoFetchData(false);
		utenteProtocollazioneItem.setAlwaysFetchMissingValues(true);
		utenteProtocollazioneItem.setFetchMissingValues(true);
		if(AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			utenteProtocollazioneItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		utenteProtocollazioneItem.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
			
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});
		utenteProtocollazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
			
				return flgProtUtenteSelezionatoItem.getValueAsBoolean() != null && flgProtUtenteSelezionatoItem.getValueAsBoolean();
			}
		});
//		utenteProtocollazioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem formItem, Object value) {
//				return flgProtUtenteSelezionatoItem.getValueAsBoolean() != null && flgProtUtenteSelezionatoItem.getValueAsBoolean();
//			}
//		}));
		utenteProtocollazioneItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				destinatariItem.clearValue();
				destinatariItem.fetchData();
			}
		});
		
		flgProtDiTuttiItem = new CheckboxItem("flgProtDiTutti",I18NUtil.getMessages().listaAccompagnatoriaProtocollazioni_detail_flgProtDiTutti_title());
		flgProtDiTuttiItem.setWidth(150);
		flgProtDiTuttiItem.setStartRow(true);
		flgProtDiTuttiItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				validateCheckSceltaUtenti(event.getItem().getName());
				destinatariItem.clearValue();
				destinatariItem.fetchData();		
				mDynamicForm.markForRedraw();
			}
		});
		
		mDynamicForm.setItems(
				flgProtSoloMieItem,flgProtUtenteSelezionatoItem,utenteProtocollazioneItem,flgProtDiTuttiItem
		);
		
	}
	
	private void buildDateForm() {
		
		mDynamicFormPeriodo = new DynamicForm();
		mDynamicFormPeriodo.setValuesManager(vm);
		mDynamicFormPeriodo.setNumCols(5);
		mDynamicFormPeriodo.setColWidths(1,1,1,"*","*");
		mDynamicFormPeriodo.setWrapItemTitles(false);
		
		dtInizioItem = new DateItem("dtInizio", I18NUtil.getMessages().listaAccompagnatoriaProtocollazioni_detail_dtInizio_title());
		dtInizioItem.setRequired(true);
		dtInizioItem.setValue(new Date());
		dtInizioItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				destinatariItem.clearValue();
				destinatariItem.fetchData();							
			}
		});
		
		dtFineItem = new DateItem("dtFine", I18NUtil.getMessages().listaAccompagnatoriaProtocollazioni_detail_dtFine_title());
		dtFineItem.setValue(new Date());
		dtFineItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				destinatariItem.clearValue();
				destinatariItem.fetchData();
			}
		});
		
		mDynamicFormPeriodo.setItems(dtInizioItem,dtFineItem);
	}
	
	private void buildDestinatariForm() {
		
		mDynamicFormDestinatari = new DynamicForm();
		mDynamicFormDestinatari.setValuesManager(vm);
		mDynamicFormDestinatari.setNumCols(10);
		mDynamicFormDestinatari.setColWidths(1,1,1,1,1,1,1,1,"*","*");
		mDynamicFormDestinatari.setWrapItemTitles(false);
		
		flgDestinatariTuttiItem = new CheckboxItem("flgDestinatariTutti", I18NUtil.getMessages().listaAccompagnatoriaProtocollazioni_detail_flgDestinatariTutti_title());
		flgDestinatariTuttiItem.setWidth(150);
		flgDestinatariTuttiItem.setValue(true);
		flgDestinatariTuttiItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				validateCheckSceltaDestinatari(event.getItem().getName());
			}
		});
		
		flgDestinatariSelezionatiItem = new CheckboxItem("flgDestinatariSelezionati", I18NUtil.getMessages().listaAccompagnatoriaProtocollazioni_detail_flgDestinatariSelezionati_title());
		flgDestinatariSelezionatiItem.setWidth(150);
		flgDestinatariSelezionatiItem.setHeight(30);
		flgDestinatariSelezionatiItem.setStartRow(true);
		flgDestinatariSelezionatiItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				validateCheckSceltaDestinatari(event.getItem().getName());
			}
		});
		CustomValidator lCustomDestinatariValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				boolean isValid = true;
				if(flgDestinatariSelezionatiItem.getValueAsBoolean() != null && flgDestinatariSelezionatiItem.getValueAsBoolean()){
					if(destinatariItem.getValue() == null || "".equals(destinatariItem.getValue())){
						isValid = false;
					}
				}
				return isValid;
			}
		};
		lCustomDestinatariValidator.setErrorMessage("Attenzione, occorre selezionare almeno un destinatario per la scelta selezionata");
		flgDestinatariSelezionatiItem.setValidators(lCustomDestinatariValidator);
		
		destinatariItem = new SelectItem("listaDestinatari");
		destinatariItem.setDisplayField("denominazione");
		destinatariItem.setValueField("idUo"); 
		destinatariItem.setShowTitle(false);
		ListGridField idUoField = new ListGridField("idUo");
		idUoField.setHidden(true);
		ListGridField codiceField = new ListGridField("codice", I18NUtil.getMessages().organigramma_list_codUoField_title());
		codiceField.setWidth(80);
		codiceField.setShowHover(true);
		codiceField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute("denominazioneEstesa");
			}
		});
		codiceField.setCanFilter(false);
		ListGridField denominazioneField = new ListGridField("denominazione", I18NUtil.getMessages().organigramma_list_descrizioneField_title());
		denominazioneField.setWidth("*");
		denominazioneField.setCanFilter(false);
		destinatariItem.setPickListFields(idUoField, codiceField, denominazioneField);
		destinatariItem.setOptionDataSource(lGwtRestDataSourceDestinatari); 
		destinatariItem.setWidth(380);
		destinatariItem.setPickListWidth(600);
		destinatariItem.setShowIcons(true);
		destinatariItem.setClearable(false);
		destinatariItem.setMultiple(true);
		destinatariItem.setAutoFetchData(false);
		destinatariItem.setAlwaysFetchMissingValues(true);
		destinatariItem.setFetchMissingValues(true);
		destinatariItem.setColSpan(3);
		destinatariItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("denominazioneEstesa") : null;
			}
		});
		destinatariItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {			
				return flgDestinatariSelezionatiItem.getValueAsBoolean() != null && flgDestinatariSelezionatiItem.getValueAsBoolean();
			}
		});
//		destinatariItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem formItem, Object value) {
//				return flgDestinatariSelezionatiItem.getValueAsBoolean() != null && flgDestinatariSelezionatiItem.getValueAsBoolean();
//			}
//		}));		
		ListGrid pickListProperties = new ListGrid();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String dtInizioVld = DateUtil.formatAsShortDate(dtInizioItem.getValueAsDate());
				String dtFineVld = DateUtil.formatAsShortDate(dtFineItem.getValueAsDate());
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) destinatariItem.getOptionDataSource();
				organigrammaDS.addParam("dtInizioVld", dtInizioVld);
				organigrammaDS.addParam("dtFineVld", dtFineVld);
				if(flgProtUtenteSelezionatoItem.getValueAsBoolean() != null && flgProtUtenteSelezionatoItem.getValueAsBoolean()) {
					organigrammaDS.addParam("idUserAss", utenteProtocollazioneItem.getValueAsString());		
				} else if(flgProtSoloMieItem.getValueAsBoolean() != null && flgProtSoloMieItem.getValueAsBoolean()) {
					String idUser = Layout.getUtenteLoggato().getIdUser() != null ? String.valueOf(Layout.getUtenteLoggato().getIdUser()) : "";
					organigrammaDS.addParam("idUserAss", Layout.getUtenteLoggato().getIdUserLavoro() != null && !"".equals(Layout.getUtenteLoggato().getIdUserLavoro()) ? Layout.getUtenteLoggato().getIdUserLavoro() : idUser);		
				} else if(flgProtDiTuttiItem.getValueAsBoolean() != null && flgProtDiTuttiItem.getValueAsBoolean()) {
					organigrammaDS.addParam("idUserAss", "");		
				}					
				destinatariItem.setOptionDataSource(organigrammaDS);
				destinatariItem.invalidateDisplayValueCache();
			}
		});		
		destinatariItem.setPickListProperties(pickListProperties);
		
//		listaOrganigrammaItem = new HiddenItem("listaIdUO");
//		
//		listaDescrizioniItem = new TextItem("listaDescrizioni","");
//		listaDescrizioniItem.setCanEdit(false);
//		listaDescrizioniItem.setWidth(500);	
//		listaDescrizioniItem.setShowTitle(false);
//		listaDescrizioniItem.setAttribute("obbligatorio", true);
//		listaDescrizioniItem.addChangeHandler(new ChangeHandler() {
//			
//			@Override
//			public void onChange(ChangeEvent event) {
//				
//				mDynamicFormDestinatari.markForRedraw();
//			}
//		});
//		listaDescrizioniItem.setShowIfCondition(new FormItemIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//			
//				return flgDestinatariSelezionatiItem.getValueAsBoolean();
//			}
//		});
//		
//		editButton = new ImgButtonItem("editButton", "buttons/modify.png", I18NUtil.getMessages().modifyButton_prompt());   
//		editButton.setAlign(Alignment.CENTER);   
//		editButton.setAlwaysEnabled(true);     
//		editButton.setIconWidth(16);
//		editButton.setIconHeight(16);	
//		editButton.addIconClickHandler(new IconClickHandler() {				
//			@Override
//			public void onIconClick(IconClickEvent event) {
//				
//				Record lRecord = new Record(mDynamicFormDestinatari.getValues());
//				property.put("datasourceName", "SelectOrganigrammaDatasource");
//				property.put("flgNodoType", "UO");
//				property.put("showCheckIncludi", "false");
//				property.put("multiple", "true");
//				property.put("dtInizioVld", DateUtil.formatAsShortDate(dtInizioItem.getValueAsDate()));
//				property.put("dtFineVld", DateUtil.formatAsShortDate(dtFineItem.getValueAsDate()));				
//				OrganigrammaPopup lOrganigrammaPopup = new OrganigrammaPopup("Scelta UO", property, lRecord) {
//					
//					@Override
//					public void onClickOkButton(Record values) {
//					
//						if (values.getAttribute("listaOrganigramma") != null){
//							String listaIdUO = new String();
//							String listaDescrizioni = null;
//							RecordList lRecordListOrganigramma = new RecordList(values.getAttributeAsJavaScriptObject("listaOrganigramma"));
//							for (int i = 0; i < lRecordListOrganigramma.getLength(); i++) {	
//								Record lRecordOrganigramma = lRecordListOrganigramma.get(i);
//								if(lRecordOrganigramma.getAttribute("organigramma") != null && !"".equals(lRecordOrganigramma.getAttribute("organigramma"))) {
//								
//									String idUO = lRecordOrganigramma.getAttributeAsString("idUo") != null && !"".equals(lRecordOrganigramma.getAttributeAsString("idUo")) ?
//											lRecordOrganigramma.getAttributeAsString("idUo") : "";
//								    listaIdUO += idUO + ";";
//											
//									String descrizione = lRecordOrganigramma.getAttribute("codRapido") + " ** " + lRecordOrganigramma.getAttribute("descrizione");
//									if(listaDescrizioni == null) {
//										listaDescrizioni = descrizione;
//									} else {
//										listaDescrizioni += " , " + descrizione;
//									}	
//									
//									boolean flgIncludiSottoUO = lRecordOrganigramma.getAttributeAsBoolean("flgIncludiSottoUO") != null && lRecordOrganigramma.getAttributeAsBoolean("flgIncludiSottoUO");
//									boolean flgIncludiScrivanie = lRecordOrganigramma.getAttributeAsBoolean("flgIncludiScrivanie") != null && lRecordOrganigramma.getAttributeAsBoolean("flgIncludiScrivanie");
//									if(flgIncludiSottoUO && flgIncludiScrivanie) {
//										descrizione += " (sotto-UO e scrivanie)";
//									} else if(flgIncludiSottoUO) {
//										descrizione += " (sotto-UO)";
//									} else if(flgIncludiScrivanie) {
//										descrizione += " (scrivanie)";
//									}
//				
//								}				
//							}
//							listaDescrizioniItem.setValue(listaDescrizioni);
//							listaOrganigrammaItem.setValue(listaIdUO);
//							markForRedraw();
//						}	
//					}					
//				};
//				lOrganigrammaPopup.show();
//			}
//		});
//		editButton.addChangeHandler(new ChangeHandler() {
//			
//			@Override
//			public void onChange(ChangeEvent event) {
//				
//				mDynamicFormDestinatari.markForRedraw();
//			}
//		});
//		editButton.setShowIfCondition(new FormItemIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//			
//				return flgDestinatariSelezionatiItem.getValueAsBoolean();
//			}
//		});
//		
//		CustomValidator lCustomValidator = new CustomValidator() {
//
//			@Override
//			protected boolean condition(Object value) {
//				
//				boolean isValid = true;
//				if(flgDestinatariSelezionatiItem.getValueAsBoolean() != null && flgDestinatariSelezionatiItem.getValueAsBoolean()){
//					if(listaDescrizioniItem.getValue() == null || "".equals(listaDescrizioniItem.getValue())){
//						isValid = false;
//					}
//				}
//				return isValid;
//			}
//		};
//		lCustomValidator.setErrorMessage("Attenzione, occorre selezionare un utente per la scelta selezionata");
//		flgDestinatariSelezionatiItem.setValidators(lCustomValidator);
		
		mDynamicFormDestinatari.setItems(
				flgDestinatariTuttiItem,
				flgDestinatariSelezionatiItem,
				destinatariItem
				/*
				listaOrganigrammaItem,
				listaDescrizioniItem,
				editButton
				*/
		);
	}
	
	protected void buildBottoni() {
		
		bottoniToolStrip = new ToolStrip();
		bottoniToolStrip.setName("bottoni");
		bottoniToolStrip.setWidth100();
		bottoniToolStrip.setHeight(30);
		bottoniToolStrip.setAlign(Alignment.CENTER);

		stampaButton  = new DetailToolStripButton("Stampa", "ok.png");
		stampaButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (vm.validate()) {
					
					Layout.showWaitPopup(I18NUtil.getMessages().caricamentoRubrica_caricamentoInCorso());
					
					Record pRecord = new Record(vm.getValues());	
					
					GWTRestService<Record, Record> reportDS = new GWTRestService<Record, Record>("ReportDatasource");
					reportDS.executecustom("stampaListaAccompagnatoriaProtocollazioni", pRecord , new DSCallback() {
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								final Record savedRecord = response.getData()[0];
								if(savedRecord != null){
									Boolean foglioGenerato = savedRecord.getAttributeAsBoolean("foglioGenerato") != null ?
											savedRecord.getAttributeAsBoolean("foglioGenerato") : false;
									String errorMessage = null;
											
									if(foglioGenerato){
										Boolean inError = savedRecord.getAttributeAsBoolean("inError") != null ?
												savedRecord.getAttributeAsBoolean("inError") : false;
												
										if(inError){
											errorMessage = savedRecord.getAttributeAsString("errorMessage") != null ?
													savedRecord.getAttributeAsString("errorMessage") : "Non è stato possibile generare la lista accompagnatoria protocollazioni per tutte le UO";
											Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
										}
										
										String nomeFilePdf = savedRecord.getAttribute("nomeFile");
										String uriFilePdf = savedRecord.getAttribute("uri");
										if (uriFilePdf != null && !"".equalsIgnoreCase(uriFilePdf)){
											InfoFileRecord infoFilePdf = InfoFileRecord.buildInfoFileString(JSON.encode(savedRecord.getAttributeAsRecord("infoFile").getJsObj()));
											PreviewControl.switchPreview(uriFilePdf, false, infoFilePdf, "FileToExtractBean", nomeFilePdf);
										}
										
									} else {
										errorMessage = savedRecord.getAttributeAsString("errorMessage") != null ?
												savedRecord.getAttributeAsString("errorMessage") : "Errore nella generazione della lista accompagnatoria protocollazioni per tutte le UO";
										Layout.addMessage(new MessageBean(errorMessage, "", MessageType.ERROR));
									}
								}
							}
							Layout.hideWaitPopup();
						}
					});
				}
			}
		});

		bottoniToolStrip.addButton(stampaButton);
	}
	
	private void validateCheckSceltaUtenti(String nameCheckboxClicked){
		
		flgProtSoloMieItem.setValue(flgProtSoloMieItem.getName().equals(nameCheckboxClicked) ? true : false);
		flgProtUtenteSelezionatoItem.setValue(flgProtUtenteSelezionatoItem.getName().equals(nameCheckboxClicked) ? true : false);
		flgProtDiTuttiItem.setValue(flgProtDiTuttiItem.getName().equals(nameCheckboxClicked) ? true : false);

		if(flgProtUtenteSelezionatoItem.getValueAsBoolean() != null && flgProtUtenteSelezionatoItem.getValueAsBoolean()){
			utenteProtocollazioneItem.validate();
		}
		
		markForRedraw();
	}
	
	private void validateCheckSceltaDestinatari(String nameCheckboxClicked){
		
		flgDestinatariTuttiItem.setValue(flgDestinatariTuttiItem.getName().equals(nameCheckboxClicked) ? true : false);
		flgDestinatariSelezionatiItem.setValue(flgDestinatariSelezionatiItem.getName().equals(nameCheckboxClicked) ? true : false);
		
		if(flgDestinatariSelezionatiItem.getValueAsBoolean() != null && flgDestinatariSelezionatiItem.getValueAsBoolean()){
			destinatariItem.validate();
		}
		
		markForRedraw();
	}
	
}