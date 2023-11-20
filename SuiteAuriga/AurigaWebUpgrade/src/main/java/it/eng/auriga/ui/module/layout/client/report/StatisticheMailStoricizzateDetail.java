/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressHandler;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloAction;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloWindow;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.filter.OrganigrammaPopup;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class StatisticheMailStoricizzateDetail extends CustomDetail {
	
	private VLayout lVLayout;
	private ToolStrip bottoni;
	
	protected DetailSection filtriSection;
	protected DetailSection raggruppamentoSection;
		
	private DynamicForm filtriForm;
	private DateItem dtStoricizzazioneDaItem;
	private DateItem dtStoricizzazioneAItem;
	private DateItem dtChiusuraDaItem;
	private DateItem dtChiusuraAItem;
	private DateItem dtUltimoAggiornamentoDaItem;
	private DateItem dtUltimoAggiornamentoAItem;
	private DateItem dtInvioDaItem;
	private DateItem dtInvioAItem;
	private SelectItem caselleItem;
	
	//DIM_ORGANIGRAMMA_NONSTD = true
	private HiddenItem struttureItem;
	private HiddenItem listaOrganigrammaItem;
	private TextItem listaDescrizioniItem;
	private ImgButtonItem viewStruttureButtonItem;
	private Map<String, String> property;
	
	//DIM_ORGANIGRAMMA_NONSTD = false
	private SelectItemWithDisplay struttureNONSTDItem;
	
	private SelectItem statoLavorazioneItem;
	private NumericItem nrMesiItem;

	private DynamicForm raggruppamentoForm;
	private DynamicForm raggruppamentoForm2;
	private SelectItem raggruppaTipologiaPeriodoItem;
	private SelectItem raggruppaPeriodoItem;
	private SelectItem raggruppaUoItem;
	private CheckboxItem flgRaggruppaStatoLavorazioneEmailItem;
	private CheckboxItem flgRaggruppaArchivioItem;
	private CheckboxItem flgRaggruppaCaselleItem;
	
	//Selezione e salvataggio dei modelli
	protected ToolStrip mainToolStrip;
	private SaveModelloWindow saveModelloWindow;
	protected GWTRestDataSource modelliDS;
	protected SelectItem modelliSelectItem;
	protected ListGrid modelliPickListProperties;
	protected ListGridRecord recordModelloFocused;
	
	public StatisticheMailStoricizzateDetail() {

		super("statisticheMailStoricizzate");
		
		//Altrimenti gli elementi risultano spostati rispetto ai bordi
		setPadding(0);
		
		// LAYOUT MAIN
		lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		
		//Creo la toolstrip superiore e la aggiungo al layout
		createMainToolstrip();
		lVLayout.addMember(mainToolStrip, 0);
				
		sezioneFiltri();
		
		sezioneRaggruppamento();
		
		//final ToolStrip 
		bottoni = new ToolStrip();
		bottoni.setWidth100();
		bottoni.setHeight(30);
		bottoni.setAlign(Alignment.CENTER);
		
		buildBottoni();
	
		addMember(lVLayout);
		addMember(bottoni);
	}
	
	private void createMainToolstrip() {

		// Creo la select relativa ai modelli
		createModelliSelectItem();

		// Creo la mainToolstrip e aggiungo le select impostate
		mainToolStrip = new ToolStrip();
		mainToolStrip.setBackgroundColor("transparent");
		mainToolStrip.setBackgroundImage("blank.png");
		mainToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);		
		mainToolStrip.setBorder("1px solid #878E96"); // La stessa colorazione dei bordi dei componenti
		mainToolStrip.setWidth100();
		mainToolStrip.setHeight(30);

		// Aggiungo le due select di interesse
		mainToolStrip.addFormItem(modelliSelectItem);
	}
	
	public void createModelliSelectItem() {

		createModelliDatasource(nomeEntita);

		modelliSelectItem = new SelectItem("modelli");
		modelliSelectItem.setValueField("key");
		modelliSelectItem.setDisplayField("displayValue");
		modelliSelectItem.setTitle("<b>" + I18NUtil.getMessages().protocollazione_detail_modelliSelectItem_title() + "</b>");
		modelliSelectItem.setCachePickListResults(false);
		modelliSelectItem.setRedrawOnChange(true);
		modelliSelectItem.setShowOptionsFromDataSource(true);
		modelliSelectItem.setOptionDataSource(modelliDS);
		modelliSelectItem.setAllowEmptyValue(true);
		
		ListGridField modelliRemoveField = new ListGridField("remove");
		modelliRemoveField.setType(ListGridFieldType.ICON);
		modelliRemoveField.setWidth(18);
		// Per accessibilità. se modelliRemoveField.setIgnoreKeyboardClicks(false);, alla pressione della freccetta giù, parte automaticamente l'action ( cancella il record )
		modelliRemoveField.setIgnoreKeyboardClicks(true);
		modelliRemoveField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if (isAbilToRemoveModello(record)) {
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				}
				return null;
			}
		}); 
		modelliRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				deleteModello(event.getRecord());
			}   
		});
		
		ListGridField modelliKeyField = new ListGridField("key");
		modelliKeyField.setHidden(true);

		ListGridField modelliDisplayValueField = new ListGridField("displayValue");
		modelliDisplayValueField.setWidth("100%");

		modelliSelectItem.setPickListFields(modelliRemoveField, modelliKeyField, modelliDisplayValueField);


		modelliPickListProperties = new ListGrid();
		modelliPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		modelliPickListProperties.setShowHeader(false);
		modelliPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				loadModello(event.getRecord(), event.getColNum());
			}
		});

		if (AurigaLayout.getIsAttivaAccessibilita()) {
			modelliPickListProperties.addSelectionChangedHandler(new SelectionChangedHandler() {
				
				@Override
				public void onSelectionChanged(SelectionEvent event) {
					// TODO Auto-generated method stub
					recordModelloFocused = event.getRecord();
				}
			});
			
			modelliPickListProperties.addBodyKeyPressHandler(new BodyKeyPressHandler() {
				
				@Override
				public void onBodyKeyPress(BodyKeyPressEvent event) {
					if (EventHandler.getKey().equalsIgnoreCase("Enter")) {
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
							@Override
							public void execute() {
								ListGridRecord selectedRecord = modelliSelectItem.getSelectedRecord();
								loadModello(selectedRecord, 1);
						}
						});
					} else if (EventHandler.getKey().equalsIgnoreCase("Delete")) {
						Layout.showConfirmDialogWithWarning("Attenzione!", I18NUtil.getMessages().gestioneModelli_cancellazione_ask(), "Ok", "Annulla", new BooleanCallback() {
	
							@Override
							public void execute(Boolean value) {
								if (value != null && value) {
									if (recordModelloFocused != null) {
										deleteModello(recordModelloFocused);
									}
								} else {
									Layout.addMessage(
											new MessageBean("Cancellazione interrotta dall'utente", "", MessageType.INFO));
								}
							}
						});
					}
				}				
			});		
			}

		modelliSelectItem.setPickListProperties(modelliPickListProperties);
		createSaveModelloWindow(nomeEntita);
	}
	
	private void deleteModello (final ListGridRecord record) {
		if (isAbilToRemoveModello(record)) {
			SC.ask(I18NUtil.getMessages().gestioneModelli_cancellazione_ask(), new BooleanCallback() {
				
				@Override
				public void execute(Boolean value) {
					if(value) {
						final String key = record.getAttribute("key");
						modelliDS.removeData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								String value = (String) modelliSelectItem.getValue();
								if (key != null && value != null && key.equals(value)) {
									modelliSelectItem.setValue((String) null);
								}
							} 
						});
					}
				}
			});
		}			
	}   
		
	private void loadModello (ListGridRecord record, int numCol) {
		boolean isRemoveField = isAbilToRemoveModello(record) && numCol == 0;
		if(!isRemoveField) {					
			String userid = (String) record.getAttribute("userid");
			String prefName = (String) record.getAttribute("prefName");
			if (prefName != null && !"".equals(prefName)) {
				AdvancedCriteria criteria = new AdvancedCriteria();
				criteria.addCriteria("prefName", OperatorId.EQUALS, prefName);
				criteria.addCriteria("flgIncludiPubbl", userid.startsWith("PUBLIC.") ? "1" : "0");
				if (userid.startsWith("UO.")) {
					String idUo = (String) record.getAttribute("idUo");
					criteria.addCriteria("idUo", idUo);
				}
				modelliDS.fetchData(criteria, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record[] data = response.getData();
						if (data.length != 0) {
							Record record = data[0];
							Record values = new Record(JSON.decode(record.getAttribute("value")));
							editNewRecord(values.toMap());
						}
					}
				});
			}
		}			
	}
	
	public boolean isAbilToRemoveModello(Record record) {
		return (record.getAttribute("key") != null && !"".equals(record.getAttributeAsString("key"))) && 
			   (record.getAttributeAsBoolean("flgAbilDel") != null && record.getAttributeAsBoolean("flgAbilDel"));
	}
	
	protected void createModelliDatasource(String nomeEntita) {
		
		modelliDS = new GWTRestDataSource("ModelliDataSource", "key", FieldType.TEXT);
		modelliDS.addParam("prefKey", nomeEntita + ".modelli");
		modelliDS.addParam("isAbilToPublic", Layout.isPrivilegioAttivo("EML/MPB") ? "1" : "0");
	}
	
	protected void createSaveModelloWindow(String nomeEntita) {
		saveModelloWindow = new SaveModelloWindow(I18NUtil.getMessages().protocollazione_detail_salvaComeModelloButton_prompt(), nomeEntita,
				new SaveModelloAction(modelliDS, modelliSelectItem) {

					@Override
					public Map getMapValuesForAdd() {
						Map values = getMapValues();
						return values;
					}

					@Override
					public Map getMapValuesForUpdate() {
						Map values = getMapValues();
						return values;
					}
				}) {

			@Override
			public boolean isAbilToSavePublic() {
				return Layout.isPrivilegioAttivo("EML/MPB");
			}
			
			@Override
			public boolean isTrasmissioneAtti() {
				return false;
			}
		};
	}
	
	private Map getMapValues() {
		return vm.getValues();
	}
	
	// Sezione FILTRI
	private void sezioneFiltri(){
			
		filtriForm = new DynamicForm();
		filtriForm.setValuesManager(vm);
		filtriForm.setWidth("100%");
		filtriForm.setPadding(5);
		filtriForm.setNumCols(10);
		filtriForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		filtriForm.setWrapItemTitles(false);
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setWidth(1);
		
		CustomValidator dtVerificaValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				
				Boolean dtStoricizzazioneDa = dtStoricizzazioneDaItem != null && dtStoricizzazioneDaItem.getValueAsDate() != null ?
						true : false;
				Boolean dtChiusuraDa = dtChiusuraDaItem != null && dtChiusuraDaItem.getValueAsDate() != null ?
						true : false;
				Boolean dtUltimoAggiornamentoDa = dtUltimoAggiornamentoDaItem != null && dtUltimoAggiornamentoDaItem.getValueAsDate() != null ?
						true : false;
				Boolean dtInvioDa = dtInvioDaItem != null && dtInvioDaItem.getValueAsDate() != null ?
						true : false;
				
				return (dtStoricizzazioneDa || dtChiusuraDa || dtUltimoAggiornamentoDa || dtInvioDa);
			}
		};
		dtVerificaValidator.setErrorMessage("Attenzione, occorre valorizzare almeno una di queste date");
		
		
		dtStoricizzazioneDaItem = new DateItem("dtStoricizzazioneDa", "Data di storicizzazione da");
		dtStoricizzazioneDaItem.setWidth(120);
		dtStoricizzazioneDaItem.setStartRow(true);
		dtStoricizzazioneDaItem.setValidators(dtVerificaValidator);
		
		dtStoricizzazioneAItem = new DateItem("dtStoricizzazioneA", "a");
		dtStoricizzazioneAItem.setWidth(120);
		dtStoricizzazioneAItem.setEndRow(false);
		dtStoricizzazioneAItem.setColSpan(1);

		dtChiusuraDaItem = new DateItem("dtChiusuraDa", "Data di chiusura da");
		dtChiusuraDaItem.setWidth(120);	
		dtChiusuraDaItem.setStartRow(true);
		dtChiusuraDaItem.setValidators(dtVerificaValidator);
		
		dtChiusuraAItem = new DateItem("dtChiusuraA", "a");
		dtChiusuraAItem.setWidth(120);	
		dtChiusuraAItem.setEndRow(false);
		dtChiusuraAItem.setColSpan(1);
		
		dtUltimoAggiornamentoDaItem = new DateItem("dtUltimoAggiornamentoDa", "Data di ultimo aggiornamento da");
		dtUltimoAggiornamentoDaItem.setWidth(120);
		dtUltimoAggiornamentoDaItem.setStartRow(true);
		dtUltimoAggiornamentoDaItem.setValidators(dtVerificaValidator);
		
		dtUltimoAggiornamentoAItem = new DateItem("dtUltimoAggiornamentoA", "a");
		dtUltimoAggiornamentoAItem.setWidth(120);
		dtUltimoAggiornamentoAItem.setEndRow(true);
		dtUltimoAggiornamentoAItem.setColSpan(1);
		
		dtInvioDaItem = new DateItem("dtInvioDa", "Data di invio da");
		dtInvioDaItem.setWidth(120);		
		dtInvioDaItem.setStartRow(true);
		dtInvioDaItem.setValidators(dtVerificaValidator);
		
		dtInvioAItem = new DateItem("dtInvioA", "a");
		dtInvioAItem.setWidth(120);		
		dtInvioAItem.setEndRow(true);
		dtInvioAItem.setColSpan(1);
		
		caselleItem = new SelectItem("caselle", "Casella");
		caselleItem.setDisplayField("value");
		caselleItem.setValueField("key");
		GWTRestDataSource caselleDS = new GWTRestDataSource("LoadComboCaselleMailStoricizzateDataSource");
		caselleItem.setOptionDataSource(caselleDS);
		caselleItem.setMultiple(true);
		caselleItem.setStartRow(true);	
		caselleItem.setWidth(400);
		caselleItem.setColSpan(8);	
		caselleItem.setWrapTitle(false);
		caselleItem.setCachePickListResults(false);
		caselleItem.setRedrawOnChange(true);
		
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			
			property = new HashMap<String, String>();
			property.put("datasourceName", "SelectOrganigrammaDatasource");
			property.put("flgNodoTypeParamDB", "TIPO_ASSEGNATARI_EMAIL");
			property.put("flgNodoType", "UO;SV");
			property.put("showCheckIncludi", "true");
			property.put("multiple", "true");
		
			struttureItem = new HiddenItem("strutture");
			listaOrganigrammaItem = new HiddenItem("listaOrganigramma");
			
			listaDescrizioniItem = new TextItem("listaDescrizioni","U.O. Competente");
			listaDescrizioniItem.setCanEdit(false);
			listaDescrizioniItem.setWidth(400);	
			listaDescrizioniItem.setStartRow(true);	
			listaDescrizioniItem.setColSpan(7);
			listaDescrizioniItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {
				
				@Override
				public String getHoverHTML(FormItem item, DynamicForm form) {
					
					return (String) form.getValue("listaDescrizioniHtml");
				}
			});	
			listaDescrizioniItem.setHoverWidth(600);
			listaDescrizioniItem.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					markForRedraw();
				}
			});
			
			viewStruttureButtonItem = new ImgButtonItem("editButton", "buttons/modify.png", I18NUtil.getMessages().modifyButton_prompt());   
			viewStruttureButtonItem.setAlign(Alignment.CENTER);   
			viewStruttureButtonItem.setAlwaysEnabled(true);     
			viewStruttureButtonItem.setIconWidth(16);
			viewStruttureButtonItem.setIconHeight(16);	
			viewStruttureButtonItem.setColSpan(1);
			viewStruttureButtonItem.addIconClickHandler(new IconClickHandler() {		
				
				@Override
				public void onIconClick(IconClickEvent event) {
					Record lRecord = new Record(filtriForm.getValues());
					OrganigrammaPopup lOrganigrammaPopup = new OrganigrammaPopup("Modifica filtro su organigramma", property, lRecord) {
						@Override
						public void onClickOkButton(Record values) {
							
							editRecord(values);						
						}					
					};
					lOrganigrammaPopup.show();
				}
			});
		
		} else {
			
			SelectGWTRestDataSource struttureDS = new SelectGWTRestDataSource("LoadComboStruttureMailStoricizzateDataSource", "chiave", FieldType.TEXT, new String[] { "codice", "descrizione" }, true);
			struttureNONSTDItem = new SelectItemWithDisplay("struttureNONSTD", "U.O. Competente" , struttureDS);		
			ListGridField chiaveField = new ListGridField("chiave");
			chiaveField.setHidden(true);
			ListGridField codiceField = new ListGridField("codice", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
			codiceField.setWidth(70);
			ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().protocollazione_detail_denominazioneItem_title());
			descrizioneField.setWidth("*");
			struttureNONSTDItem.setPickListFields(chiaveField, codiceField, descrizioneField);
			struttureNONSTDItem.setValueField("chiave");		
			struttureNONSTDItem.setCachePickListResults(false);
			struttureNONSTDItem.setMultiple(true);
			struttureNONSTDItem.setStartRow(true);	
			struttureNONSTDItem.setWidth(400);
			struttureNONSTDItem.setColSpan(4);
		}
		
		statoLavorazioneItem = new SelectItem("statoLavorazione", "Stato lavorazione");
		LinkedHashMap<String, String> statiValueMap = new LinkedHashMap<String, String>();
		statiValueMap.put("lavorazione_aperta", "aperta");
		statiValueMap.put("lavorazione_conclusa", "chiusa");
		statoLavorazioneItem.setValueMap(statiValueMap);
		statoLavorazioneItem.setStartRow(true);	
		statoLavorazioneItem.setWidth(400);
		statoLavorazioneItem.setColSpan(8);
		statoLavorazioneItem.setAllowEmptyValue(true);
		
		nrMesiItem = new NumericItem("nrMesi", "Senza nessun operazione da almeno mesi");
		nrMesiItem.setStartRow(true);	
		nrMesiItem.setColSpan(8);
		
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			
			filtriForm.setItems(
					spacerItem,
					dtStoricizzazioneDaItem,dtStoricizzazioneAItem,
					dtChiusuraDaItem,dtChiusuraAItem,
					dtUltimoAggiornamentoDaItem,dtUltimoAggiornamentoAItem,
					dtInvioDaItem,dtInvioAItem,
					caselleItem,
					struttureItem,listaOrganigrammaItem,listaDescrizioniItem,viewStruttureButtonItem,
					statoLavorazioneItem,nrMesiItem
			);  
			
		} else {
			
			filtriForm.setItems(
					spacerItem,
					dtStoricizzazioneDaItem,dtStoricizzazioneAItem,
					dtChiusuraDaItem,dtChiusuraAItem,
					dtUltimoAggiornamentoDaItem,dtUltimoAggiornamentoAItem,
					dtInvioDaItem,dtInvioAItem,
					caselleItem,
					struttureNONSTDItem,
					statoLavorazioneItem,nrMesiItem
			);  
		}
					
		filtriSection = new DetailSection("Filtri", 3, true, true, false,  filtriForm );
		lVLayout.addMember(filtriSection);
	}
	
	// Sezione RAGGRUPPAMENTO
	private void sezioneRaggruppamento() {
		
		SpacerItem raggruppamentoSpacerItem = new SpacerItem();
		raggruppamentoSpacerItem.setWidth(1);
		
		raggruppamentoForm = new DynamicForm();
		raggruppamentoForm.setValuesManager(vm);
		raggruppamentoForm.setWidth("100%");
		raggruppamentoForm.setPadding(5);
		raggruppamentoForm.setNumCols(10);
		raggruppamentoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		raggruppamentoForm.setWrapItemTitles(false);
		
		LinkedHashMap<String, String> lLinkedHashMapTipologiaPeriodo = new LinkedHashMap<String, String>();
		lLinkedHashMapTipologiaPeriodo.put("chiusura",			    "Chiusura");
		lLinkedHashMapTipologiaPeriodo.put("ultimo_aggiornamento",  "Ultimo aggiornamento");
		lLinkedHashMapTipologiaPeriodo.put("storicizzazione",  	    "Storicizzazione");
		lLinkedHashMapTipologiaPeriodo.put("invio",         		"Invio");
		
		raggruppaTipologiaPeriodoItem = new SelectItem("raggruppaTipologiaPeriodo", "Periodo");		
		raggruppaTipologiaPeriodoItem.setValueMap(lLinkedHashMapTipologiaPeriodo);
		raggruppaTipologiaPeriodoItem.setWidth(200);		
		raggruppaTipologiaPeriodoItem.setClearable(true);
		raggruppaTipologiaPeriodoItem.setAllowEmptyValue(false);
		raggruppaTipologiaPeriodoItem.setStartRow(true);
		raggruppaTipologiaPeriodoItem.setAllowEmptyValue(true);
		
		LinkedHashMap<String, String> lLinkedHashMapPeriodo = new LinkedHashMap<String, String>();
		lLinkedHashMapPeriodo.put("giorno_anno",       "Giorno dell'anno");
		lLinkedHashMapPeriodo.put("giorno_mese",       "Giorno del mese");
		lLinkedHashMapPeriodo.put("giorno_settimana",  "Giorno della settimana");
		lLinkedHashMapPeriodo.put("settimana",         "Settimana");
		lLinkedHashMapPeriodo.put("mese",              "Mese");
		lLinkedHashMapPeriodo.put("trimestre",         "Trimestre");
		lLinkedHashMapPeriodo.put("quadrimestre",      "Quadrimestre");
		lLinkedHashMapPeriodo.put("semestre",          "Semestre");
		lLinkedHashMapPeriodo.put("anno",              "Anno");
		
		raggruppaPeriodoItem = new SelectItem("raggruppaPeriodo","");	
		raggruppaPeriodoItem.setShowTitle(false);
		raggruppaPeriodoItem.setValueMap(lLinkedHashMapPeriodo);
		raggruppaPeriodoItem.setWidth(200);		
		raggruppaPeriodoItem.setClearable(true);
		raggruppaPeriodoItem.setAllowEmptyValue(false);
		raggruppaPeriodoItem.setStartRow(false);
		raggruppaPeriodoItem.setAllowEmptyValue(true);
		raggruppaPeriodoItem.setColSpan(8);
		
		raggruppaUoItem = new SelectItem("raggruppaUo" , "U.O. competente");			
		raggruppaUoItem.setName("raggruppaUo");
		raggruppaUoItem.setMultiple(false);
		raggruppaUoItem.setOptionDataSource(new GWTRestDataSource("LoadComboRaggruppaUoDataSource", "key", FieldType.TEXT));  
		raggruppaUoItem.setAutoFetchData(true);
		raggruppaUoItem.setDisplayField("value");
		raggruppaUoItem.setValueField("key");			
		raggruppaUoItem.setWrapTitle(false);
		raggruppaUoItem.setAllowEmptyValue(true);
		raggruppaUoItem.setClearable(true);
		raggruppaUoItem.setAllowEmptyValue(true);
		raggruppaUoItem.setRedrawOnChange(true);
		raggruppaUoItem.setWidth(200); 
		raggruppaUoItem.setStartRow(true);
		raggruppaUoItem.setColSpan(8);
		raggruppaUoItem.setAllowEmptyValue(true);
		
		
		raggruppamentoForm2 = new DynamicForm();
		raggruppamentoForm2.setValuesManager(vm);
		raggruppamentoForm2.setWidth("100%");
		raggruppamentoForm2.setPadding(5);
		raggruppamentoForm2.setNumCols(10);
		raggruppamentoForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		raggruppamentoForm2.setWrapItemTitles(false);
		
		SpacerItem spacer1 = new SpacerItem();
		spacer1.setColSpan(1);
		spacer1.setWidth(80);
		spacer1.setStartRow(true);
		
		flgRaggruppaStatoLavorazioneEmailItem = new CheckboxItem("flgRaggruppaStatoLavorazioneEmail", "Stato lavorazione");
		flgRaggruppaStatoLavorazioneEmailItem.setStartRow(false);	
		flgRaggruppaStatoLavorazioneEmailItem.setWidth(100);
		flgRaggruppaStatoLavorazioneEmailItem.setColSpan(1);

		flgRaggruppaCaselleItem = new CheckboxItem("flgRaggruppaCaselle", "Casella");
		flgRaggruppaCaselleItem.setColSpan(1);
		flgRaggruppaCaselleItem.setStartRow(false);	
		flgRaggruppaCaselleItem.setWidth(10);
		
		flgRaggruppaArchivioItem = new CheckboxItem("flgRaggruppaArchivio", "Archivio in cui si trovano le mail: corrente o storico");
		flgRaggruppaArchivioItem.setColSpan(1);
		flgRaggruppaArchivioItem.setStartRow(false);
		flgRaggruppaArchivioItem.setWidth(10);
		
		raggruppamentoForm.setItems(
				raggruppamentoSpacerItem,
				raggruppaTipologiaPeriodoItem,raggruppaPeriodoItem,
				raggruppaUoItem
		);
		
		raggruppamentoForm2.setItems(
				spacer1,flgRaggruppaCaselleItem,flgRaggruppaStatoLavorazioneEmailItem,flgRaggruppaArchivioItem
		);
					
		DynamicForm[] listaForm = new DynamicForm[] { raggruppamentoForm , raggruppamentoForm2};
		raggruppamentoSection = new DetailSection("Raggruppamenti", 3, true, true, false, listaForm);

		lVLayout.addMember(raggruppamentoSection);
	}
	
	protected void buildBottoni() {

		final DetailToolStripButton calcolaStatistiche = new DetailToolStripButton("Calcola statistiche", "ok.png");
		calcolaStatistiche.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (vm.validate()) {
					Layout.showWaitPopup(I18NUtil.getMessages().caricamentoRubrica_caricamentoInCorso());
					Record pRecord = new Record(vm.getValues());						
					GWTRestDataSource reportDocAvanzatiDatasource = new GWTRestDataSource("StatisticheMailStoricizzateDataSource");
					reportDocAvanzatiDatasource.executecustom("call", pRecord, new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								  Record returnedValue = response.getData()[0];								
								  int idJob = 0;								
							      // Leggo IdJobIO 		
							      if (returnedValue.getAttribute("idJob") != null && !returnedValue.getAttribute("idJob").equalsIgnoreCase("")){
							    	  idJob = returnedValue.getAttributeAsInt("idJob");
							      }												   
							      // Se la store restituisce IdJobIO valorizzato, si mostra un messaggio di info "La statistica richiesta è stata schedulata: assegnato N° richiesta " + IdJobIO;
							      if(idJob >0){
							    	  Layout.addMessage(new MessageBean("La statistica richiesta è stata schedulata: assegnato N° richiesta " + returnedValue.getAttributeAsString("idJob"), "", MessageType.INFO));
							      }
							      // Altrimenti visualizzo i dati della lista xml ReportContentsXMLOut
								  else{
									   int nroRecord = 0;										   
									   if (returnedValue.getAttribute("nroRecord") != null && !returnedValue.getAttribute("nroRecord").equalsIgnoreCase("")){
										   nroRecord = returnedValue.getAttributeAsInt("nroRecord");
									   }										   
									   if (nroRecord > 0){
										   new VisualizzaDatiEmailStoricizzateWindow(returnedValue);   
									   } else{
										   Layout.addMessage(new MessageBean("Non ci sono dati da visualizzare", "", MessageType.ERROR));
									   }										   									   
								   }								
							}
							Layout.hideWaitPopup();
						}
					});
				}
			}
		});
		bottoni.addButton(calcolaStatistiche);
		
		final DetailToolStripButton salvaModelloButton = new DetailToolStripButton(I18NUtil.getMessages().datiStatisticheDocumenti_salvaComeModelloButton_prompt(), "buttons/template_save.png");
		salvaModelloButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				clickSalvaComeModello();
			}
				
		});
		bottoni.addButton(salvaModelloButton);
	}
	
	public void clickSalvaComeModello() {
		if ((!saveModelloWindow.isDrawn()) || (!saveModelloWindow.isVisible())) {
			saveModelloWindow.clearValues();
			saveModelloWindow.selezionaModello(modelliSelectItem.getSelectedRecord());
			saveModelloWindow.redrawForms();
			saveModelloWindow.redraw();
			saveModelloWindow.show();
		}
	}
	
	@Override
	public void editRecord(Record record) {
	
		if (record.getAttribute("listaOrganigramma") !=null ){
			RecordList listaOrganigramma = new RecordList();
			String listaDescrizioni = null;
			String listaDescrizioniHtml = null;
			RecordList lRecordListOrganigramma = new RecordList(record.getAttributeAsJavaScriptObject("listaOrganigramma"));
			for (int i = 0; i < lRecordListOrganigramma.getLength(); i++){	
				Record lRecordOrganigramma = lRecordListOrganigramma.get(i);
				if(lRecordOrganigramma.getAttribute("organigramma") != null && !"".equals(lRecordOrganigramma.getAttribute("organigramma"))) {
					listaOrganigramma.add(lRecordOrganigramma);
					String descrizione = lRecordOrganigramma.getAttribute("codRapido") + " ** " + lRecordOrganigramma.getAttribute("descrizione");
					boolean flgIncludiSottoUO = lRecordOrganigramma.getAttributeAsBoolean("flgIncludiSottoUO") != null && lRecordOrganigramma.getAttributeAsBoolean("flgIncludiSottoUO");
					boolean flgIncludiScrivanie = lRecordOrganigramma.getAttributeAsBoolean("flgIncludiScrivanie") != null && lRecordOrganigramma.getAttributeAsBoolean("flgIncludiScrivanie");
					if(flgIncludiSottoUO && flgIncludiScrivanie) {
						descrizione += " (sotto-UO e scrivanie)";
					} else if(flgIncludiSottoUO) {
						descrizione += " (sotto-UO)";
					} else if(flgIncludiScrivanie) {
						descrizione += " (scrivanie)";
					}
					if(listaDescrizioni == null) {
						listaDescrizioni = descrizione;
					} else {
						listaDescrizioni += " , " + descrizione;
					}			
					String descrizioneHtml = lRecordOrganigramma.getAttribute("descrizione");			
					if(lRecordOrganigramma.getAttribute("typeNodo") != null && "UO".equals(lRecordOrganigramma.getAttribute("typeNodo"))) {
						descrizioneHtml = "<b>" + descrizioneHtml + "</b>";
					}
					descrizioneHtml = lRecordOrganigramma.getAttribute("codRapido") + " ** " + descrizioneHtml;
					if(flgIncludiSottoUO && flgIncludiScrivanie) {
						descrizioneHtml += " (sotto-UO e scrivanie)";
					} else if(flgIncludiSottoUO) {
						descrizioneHtml += " (sotto-UO)";
					} else if(flgIncludiScrivanie) {
						descrizioneHtml += " (scrivanie)";
					}
					if(listaDescrizioniHtml == null) {
						listaDescrizioniHtml = descrizioneHtml;
					} else {
						listaDescrizioniHtml += "<br/>" + descrizioneHtml;
					}	
				}				
			}
			record.setAttribute("strutture", listaOrganigramma);
			record.setAttribute("listaDescrizioni", listaDescrizioni);
			record.setAttribute("listaDescrizioniHtml", listaDescrizioniHtml);
		}
		super.editRecord(record);
	}

}