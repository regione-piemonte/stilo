/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
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
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
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
import it.eng.auriga.ui.module.layout.client.anagrafiche.AssegnazioneUoForms;
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
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class StatisticheCogitoDetail extends CustomDetail {
	
	private static int TITLE_WIDTH = 142;

	// DynamicForm
	private DynamicForm tipoReportForm;
	private DynamicForm filtriForm1;
	private DynamicForm filtriForm2;
	
	private DynamicForm raggruppamentoForm;
	
	protected AssegnazioneUoForms uoReportForm;
	
	// DetailSection
	protected DetailSection uoReportSection;
	protected DetailSection tipoReportSection;
	protected DetailSection filtriSection;
	protected DetailSection raggruppamentoSection;

	// FilteredSelectItemWithDisplay
	private FilteredSelectItemWithDisplay utenteItem;
	private FilteredSelectItemWithDisplay classificazioneSuggeritaItem;
	private FilteredSelectItemWithDisplay classificazioneSceltaItem;
	
	// SelectItem 
	private SelectItem tipoReportItem;
	
	// HiddenItem 
	private HiddenItem idUtenteSelectedItem;
	private HiddenItem idClassificazioneSuggeritaSelectedItem;
	private HiddenItem idClassificazioneSceltaSelectedItem;
	
	private VLayout lVLayout;
	private ToolStrip bottoni;
	
	//Selezione e salvataggio dei modelli
	protected ToolStrip mainToolStrip;
	private SaveModelloWindow saveModelloWindow;
	protected GWTRestDataSource modelliDS;
	protected SelectItem modelliSelectItem;
	protected ListGrid modelliPickListProperties;
	protected ListGridRecord recordModelloFocused;
	
	public StatisticheCogitoDetail() {

		super("statisticheCogito");
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			setCanFocus(true);		
		}		
		//Altrimenti gli elementi risultano spostati rispetto ai bordi
		setPadding(0);
		
		// LAYOUT MAIN
		lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		
		//Creo la toolstrip superiore e la aggiungo al layout
		createMainToolstrip();
		lVLayout.addMember(mainToolStrip, 0);
		
		sezioneTipoReport();
		
		sezioneFiltri();
		
		sezioneRaggruppamento();
		
		//Creo la finestra per eseguire il salvataggio come modello
		createSaveModelloWindow(nomeEntita);
		
		// bottoni CALCOLA e SALVA COME MODELLO
		buildBottoni();
	
		addMember(lVLayout);
		addMember(bottoni);
	}

	// Sezione TIPO REPORT
	private void sezioneTipoReport(){
		
		// TIPO REPORT
		tipoReportForm = new DynamicForm();
		tipoReportForm.setValuesManager(vm);
		tipoReportForm.setPadding(5);
		tipoReportForm.setNumCols(1);
		tipoReportForm.setColWidths("*");
		tipoReportForm.setWrapItemTitles(false);
		
		tipoReportItem = new SelectItem("tipoReport");
		tipoReportItem.setShowTitle(false);
		tipoReportItem.setRequired(true);
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();
		lLinkedHashMap.put("esito",                       "Esito");
		lLinkedHashMap.put("esito_operazioni_di_registrazione_con_suggerimento", "Esito operazioni di registrazione con suggerimento");
		tipoReportItem.setValueMap(lLinkedHashMap);
		tipoReportItem.setWidth(770);		
		tipoReportItem.setClearable(true);
		tipoReportItem.setAllowEmptyValue(true);
		tipoReportItem.setRedrawOnChange(true);	
		tipoReportItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		tipoReportForm.setItems(tipoReportItem);		
		
		tipoReportSection = new DetailSection(I18NUtil.getMessages().statisticheDocumenti_tipoReport_section_title(), 3, true, true, true, tipoReportForm);
		
		lVLayout.addMember(tipoReportSection);	
	}
	
	// Sezione FILTRI
	private void sezioneFiltri(){
		
		filtriForm1 = new DynamicForm();
		filtriForm1.setValuesManager(vm);
		filtriForm1.setWidth("100%");
		filtriForm1.setPadding(5);
		filtriForm1.setNumCols(10);
		filtriForm1.setColWidths(40, 100, 40, 100, 40, 100, 40, 100, 40, "*");
		filtriForm1.setWrapItemTitles(false);
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setWidth(1);
		
		// PERIODO
		final DateItem lDateItemDa = new DateItem("da", setTitleAlign("Periodo dal", TITLE_WIDTH, true));
		lDateItemDa.setWidth(180);
		lDateItemDa.setRequired(true);
		lDateItemDa.setStartRow(true);
		lDateItemDa.setAlign(Alignment.LEFT);
		
		CustomValidator lCustomValidatorDa = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				if (value == null)
					return true;
				Date lDateA = (Date) filtriForm1.getValue("a");
				if (lDateA == null)
					return true;
				else {
					Date lDateDa = (Date) value;
					long timeDa = lDateDa.getTime();
					long timeA = lDateA.getTime();
					return timeA >= timeDa;
				}
			}
		};
		lCustomValidatorDa.setErrorMessage("La data di fine periodo deve essere maggiore di quella iniziale");
		lDateItemDa.setValidators(lCustomValidatorDa);
		lDateItemDa.setValidateOnChange(true);

		final DateItem lDateItemA = new DateItem("a", "al");
		lDateItemA.setWidth(180);
		lDateItemA.setRequired(true);
		CustomValidator lCustomValidatorA = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				if (value == null)
					return true;
				Date lDateDa = (Date) filtriForm1.getValue("da");
				if (lDateDa == null)
					return true;
				else {
					Date lDateA = (Date) value;
					long timeDa = lDateDa.getTime();
					long timeA = lDateA.getTime();
					return timeA >= timeDa;
				}
			}
		};
		lCustomValidatorA.setErrorMessage("La data di fine periodo deve essere maggiore di quella iniziale");
		lDateItemA.setValidators(lCustomValidatorA);
		lDateItemA.setValidateOnChange(true);		
		lDateItemA.setEndRow(true);
					
        filtriForm1.setItems(lDateItemDa,lDateItemA);
        
		
		// UO 
		uoReportForm = new AssegnazioneUoForms("", vm) {
			public String getFinalitaForLookupOrganigramma() {
				return "ALTRO";
			}
			public String getFinalitaForComboOrganigramma() {
				return "ALTRO";
			}
			public boolean isPartizionamentoRubricaAbilitato() {
				return true;
			}
			public boolean isAbilInserireModificareSoggInQualsiasiUo() {
				// Se l’utente HA il privilegio REP/STD si mostrano tutte le UO, se NON ha il privilegio si mostra la select con le UO collegate all’utente
				return Layout.isPrivilegioAttivo("REP/STD");
			}
			public boolean isUoCollegateShowTitle() {
				return true;
			}
			public boolean isUoCollegateObbligatorio() {
				return false;
			}		
			
			public boolean isFlgModificabileDaSottoUoVisible() {
				return false;
			}
						
			public String getTitleFlgVisibileDaSottoUoItem() {
				return I18NUtil.getMessages().statistichedocumenti_check_flgIncluseSottoUo_label();
			}
			
			public String getTitleCodRapidoItem() {
				return setTitleAlign("Registrati dalla U.O.", TITLE_WIDTH, false);
			}
			
			public int getOrganigrammaItemWidth() {
				return 502;
			}
		};	
		
		uoReportForm.refreshTitleCodRapidoItem("Richiamato da U.O.");
		
        filtriForm2 = new DynamicForm();
		filtriForm2.setValuesManager(vm);
		filtriForm2.setWidth("100%");
		filtriForm2.setPadding(5);
		filtriForm2.setNumCols(10);
		filtriForm2.setColWidths(40, 100, 40, 100, 40, 100, 40, 100, 40, "*");
		filtriForm2.setWrapItemTitles(false);
		       
		// OPERATORE	
		idUtenteSelectedItem = new HiddenItem("idUtenteSelected"); 
		utenteItem = createFilteredSelectItemWithDisplay("idUtente",
                                                          I18NUtil.getMessages().statistichecogito_combo_utente_label(), 
                                                          "LoadComboUtentiDatasource", 
                                                          new String[] {"cognomeNome", "username"}, 
                                                          new String[]{"idUtente"}, 
                                                          new String[]{I18NUtil.getMessages().statistichedocumenti_combo_utenteReport_cognomeNomeField(), 
			                                                           I18NUtil.getMessages().statistichedocumenti_combo_utenteReport_usernameField()
			                                                          },               
                                                          new Object[]{"395", "260"}, 
                                                          634, 
                                                          "idUtente", 
                                                          "cognomeNome");                 
		utenteItem.setAllowEmptyValue(true);    	
		utenteItem.setFilterLocally(false);
		utenteItem.setAutoFetchData(true);
		utenteItem.setFetchMissingValues(false);
		utenteItem.setRedrawOnChange(true);	
		utenteItem.setColSpan(9);
		utenteItem.setStartRow(true);
		
		// CLASSIFICAZIONE SUGGERITA
		idClassificazioneSuggeritaSelectedItem = new HiddenItem("idClassificazioneSuggeritaSelected"); 
		classificazioneSuggeritaItem = createFilteredSelectItemWithDisplay("idClassificazioneSuggerita",
                                                                            I18NUtil.getMessages().statistichecogito_combo_classificazione_suggerita_label(), 
                                                                            "LoadComboClassificaDataSource", 
                                                                            new String[] {"indice", "descrizione"}, 
                                                                            new String[]{"descrizioneEstesa"},
                                                                            new String[]{ I18NUtil.getMessages().statistichecogito_combo_classificazione_indiceField(), 
			                                                                              I18NUtil.getMessages().statistichecogito_combo_classificazione_descrizioneField()
			                                                                           },               
                                                                            new Object[]{"100", "500"}, 
                                                                            634, 
                                                                            "idClassifica", 
                                                                            "descrizione");                 
		
		classificazioneSuggeritaItem.setEmptyPickListMessage(I18NUtil.getMessages().protocollazione_detail_classificazioneItem_noSearchOrEmptyMessage());
		classificazioneSuggeritaItem.setClearable(true);		
		classificazioneSuggeritaItem.setAllowEmptyValue(true);    	
		classificazioneSuggeritaItem.setFilterLocally(false);
		classificazioneSuggeritaItem.setAutoFetchData(true);
		classificazioneSuggeritaItem.setFetchMissingValues(false);
		classificazioneSuggeritaItem.setRedrawOnChange(true);	
		classificazioneSuggeritaItem.setColSpan(9);
		classificazioneSuggeritaItem.setStartRow(true);

		// CLASSIFICAZIONE SCELTA
		idClassificazioneSceltaSelectedItem = new HiddenItem("idClassificazioneSceltaSelected"); 
		classificazioneSceltaItem = createFilteredSelectItemWithDisplay("idClassificazioneScelta",
                                                                        I18NUtil.getMessages().statistichecogito_combo_classificazione_scelta_label(), 
                                                                        "LoadComboClassificaDataSource", 
                                                                        new String[] {"indice", "descrizione"}, 
                                                                        new String[]{"descrizioneEstesa"},
                                                                        new String[]{ I18NUtil.getMessages().statistichecogito_combo_classificazione_indiceField(), 
			                                                                          I18NUtil.getMessages().statistichecogito_combo_classificazione_descrizioneField()
			                                                                        },               
                                                                         new Object[]{"100", "500"}, 
                                                                         634, 
                                                                         "idClassifica", 
                                                                         "descrizione");                 
		
		classificazioneSceltaItem.setEmptyPickListMessage(I18NUtil.getMessages().protocollazione_detail_classificazioneItem_noSearchOrEmptyMessage());
		classificazioneSceltaItem.setClearable(true);		
		classificazioneSceltaItem.setAllowEmptyValue(true);    	
		classificazioneSceltaItem.setFilterLocally(false);
		classificazioneSceltaItem.setAutoFetchData(true);
		classificazioneSceltaItem.setFetchMissingValues(false);
		classificazioneSceltaItem.setRedrawOnChange(true);	
		classificazioneSceltaItem.setColSpan(9);
		classificazioneSceltaItem.setStartRow(true);
		
		// ESITO		
		SelectItem esitoItem = new SelectItem("idEsito", I18NUtil.getMessages().statistichecogito_combo_esito_label());
		LinkedHashMap<String, String> lLinkedHashMapEsito = new LinkedHashMap<String, String>();
		
		lLinkedHashMapEsito.put("nessun_suggerimento",                "Nessun suggerimento");
		lLinkedHashMapEsito.put("suggerimento_fornito_non_accettato", "Suggerimento fornito ma non accettato");
		lLinkedHashMapEsito.put("suggerimento_fornito_e_accettato",   "Suggerimento fornito e accettato");
		esitoItem.setValueMap(lLinkedHashMapEsito);
		esitoItem.setWidth(270);		
		esitoItem.setAllowEmptyValue(true);
		esitoItem.setRedrawOnChange(true);	
		esitoItem.setStartRow(true);
		
		// ERRORE
		CheckboxItem erroreItem = new CheckboxItem("errore" ,I18NUtil.getMessages().statistichecogito_check_errore_label());			
		erroreItem.setWidth(200); 
		
		filtriForm2.setItems(utenteItem, 
							 idUtenteSelectedItem,
							 classificazioneSuggeritaItem, 
							 idClassificazioneSuggeritaSelectedItem,
							 classificazioneSceltaItem,    
							 idClassificazioneSceltaSelectedItem,
							 esitoItem, 
							 erroreItem
                            );
		
		DynamicForm[] listaForm = new DynamicForm[] { filtriForm1 , 
				                                      uoReportForm.getForms()[0], uoReportForm.getForms()[2],
				                                      filtriForm2 };
		filtriSection = new DetailSection(I18NUtil.getMessages().statisticheDocumenti_filtri_section_title(), 3, true, true, false,  listaForm );
		
		lVLayout.addMember(filtriSection);		
	}
	
	// Sezione RAGGRUPPAMENTO
	private void sezioneRaggruppamento(){
				
		raggruppamentoForm = new DynamicForm();
		raggruppamentoForm.setValuesManager(vm);
		raggruppamentoForm.setWidth("100%");
		raggruppamentoForm.setPadding(5);
		raggruppamentoForm.setNumCols(6);
		raggruppamentoForm.setColWidths(1, 1, 1, 1, 1, "*");
		raggruppamentoForm.setWrapItemTitles(false);
		
		// PERIODO
		SelectItem raggruppaPeriodoItem = new SelectItem("raggruppaPeriodo", I18NUtil.getMessages().statistichedocumenti_combo_raggruppaPeriodo_label());		
		LinkedHashMap<String, String> lLinkedHashMapRaggruppaPeriodo = new LinkedHashMap<String, String>();
		lLinkedHashMapRaggruppaPeriodo.put("giorno_anno",       "Giorno dell'anno");
		lLinkedHashMapRaggruppaPeriodo.put("giorno_mese",       "Giorno del mese");
		lLinkedHashMapRaggruppaPeriodo.put("giorno_settimana",  "Giorno della settimana");
		lLinkedHashMapRaggruppaPeriodo.put("settimana",         "Settimana");
		lLinkedHashMapRaggruppaPeriodo.put("mese",              "Mese");
		lLinkedHashMapRaggruppaPeriodo.put("trimestre",         "Trimestre");
		lLinkedHashMapRaggruppaPeriodo.put("quadrimestre",      "Quadrimestre");
		lLinkedHashMapRaggruppaPeriodo.put("semestre",          "Semestre");
		lLinkedHashMapRaggruppaPeriodo.put("anno",              "Anno");
		raggruppaPeriodoItem.setValueMap(lLinkedHashMapRaggruppaPeriodo);
		raggruppaPeriodoItem.setWidth(200);		
	    raggruppaPeriodoItem.setClearable(true);
	    raggruppaPeriodoItem.setAllowEmptyValue(true);

		// TIPO UO
	    SelectItem raggruppaUoItem = new SelectItem("raggruppaUo" ,I18NUtil.getMessages().statistichedocumenti_combo_raggruppaUo_label());			
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
		raggruppaUoItem.setEndRow(true);
		
		// OPERATORE
		CheckboxItem raggruppaUtenteItem = new CheckboxItem("raggruppaUtente", I18NUtil.getMessages().statistichedocumenti_check_utente_label());
		raggruppaUtenteItem.setWidth(50);
		raggruppaUtenteItem.setColSpan(2);
		
        // CLASSIFICAZIONE		
		CheckboxItem raggruppaClassificazioneItem = new CheckboxItem("raggruppaClassificazione", I18NUtil.getMessages().statistichecogito_check_classificazione_label());
		raggruppaClassificazioneItem.setWidth(50);
		raggruppaClassificazioneItem.setColSpan(2);
		
		// REGISTRAZIONE
		CheckboxItem raggruppaRegistrazioneItem = new CheckboxItem("raggruppaRegistrazione", I18NUtil.getMessages().statistichecogito_check_registrazione_label());
		raggruppaRegistrazioneItem.setWidth(50);
		raggruppaRegistrazioneItem.setColSpan(2);
		raggruppaRegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {						
				return "esito_operazioni_di_registrazione_con_suggerimento".equals(tipoReportItem.getValueAsString());
			}
		});
		
		raggruppamentoForm.setItems(raggruppaPeriodoItem, raggruppaUoItem,
				                    raggruppaUtenteItem, raggruppaClassificazioneItem, raggruppaRegistrazioneItem			                    
				                    );
					
		raggruppamentoSection = new DetailSection(I18NUtil.getMessages().statisticheDocumenti_raggruppamento_section_title(), 3, true, true, false, raggruppamentoForm );

		lVLayout.addMember(raggruppamentoSection);
	}
	
	protected void buildBottoni() {
	
		//final ToolStrip 
		bottoni = new ToolStrip();
		bottoni.setName("bottoni");
		bottoni.setWidth100();
		bottoni.setHeight(30);
		bottoni.setAlign(Alignment.CENTER);

		final DetailToolStripButton okButton        = new DetailToolStripButton("Calcola statistiche", "ok.png");

		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (vm.validate()) {
					Layout.showWaitPopup(I18NUtil.getMessages().caricamentoRubrica_caricamentoInCorso());
					Record pRecord = new Record(vm.getValues());	
					pRecord.setAttribute("flgIncluseSottoUO", pRecord.getAttributeAsBoolean("flgVisibileDaSottoUo"));
					pRecord.setAttribute("idUO", pRecord.getAttributeAsString("idUoAssociata"));
					pRecord.setAttribute("idUtente", pRecord.getAttributeAsString("idUtenteSelected"));
					pRecord.setAttribute("idClassificazioneSuggerita", pRecord.getAttributeAsString("idClassificazioneSuggeritaSelected"));
					pRecord.setAttribute("idClassificazioneScelta", pRecord.getAttributeAsString("idClassificazioneSceltaSelected"));
					
					GWTRestDataSource reporDatasource = new GWTRestDataSource("ReportCogitoLogDatasource");
					reporDatasource.executecustom("call", pRecord, new DSCallback() {
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
											   String tipoReport = tipoReportItem.getValueAsString();
											   
											   VisualizzaDatiStatisticheCogitoLogWindow lVisualizzaDatiStatisticheCogitoLogWindow = new VisualizzaDatiStatisticheCogitoLogWindow(returnedValue, tipoReport);   
										   }
										   else{
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

		final DetailToolStripButton salvaModelloButton        = new DetailToolStripButton(I18NUtil.getMessages().datiStatisticheDocumenti_salvaComeModelloButton_prompt(), "buttons/template_save.png");

		salvaModelloButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clickSalvaComeModello();
			}
				
		});

		bottoni.addButton(okButton);
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
	
    private void manageOnOptionClick(String name, Record record){	
		if (name.equals("idUtente")) {
			filtriForm2.setValue("idUtenteSelected", record.getAttributeAsString("idUtente"));	
			idUtenteSelectedItem.setValue(record.getAttribute("idUtente"));
        }
		
		if (name.equals("idClassificazioneSuggerita")) {
			filtriForm2.setValue("idClassificazioneSuggeritaSelected", record.getAttributeAsString("idClassifica"));
			idClassificazioneSuggeritaSelectedItem.setValue(record.getAttribute("idClassifica"));
		}
		
		if (name.equals("idClassificazioneScelta")) {
			filtriForm2.setValue("idClassificazioneSceltaSelected", record.getAttributeAsString("idClassifica"));
			idClassificazioneSceltaSelectedItem.setValue(record.getAttribute("idClassifica"));
		}
		
		
	}
	
	private void manageClearSelect(String name){
		if (name.equals("idUtente")){			
			utenteItem.clearValue();
			utenteItem.setValue((String)null);					
			idUtenteSelectedItem.setValue((String)null);
			filtriForm2.setValue("idUtente",      (String)null);
			filtriForm2.setValue("idUtenteSelected",(String)null);
		}
		if (name.equals("idClassificazioneSuggerita")) {
			classificazioneSuggeritaItem.clearValue();
			classificazioneSuggeritaItem.setValue((String)null);
			idClassificazioneSuggeritaSelectedItem.setValue((String)null);			
			filtriForm2.setValue("idClassificazioneSuggerita", (String)null);
			filtriForm2.setValue("idClassificazioneSuggeritaSelected", (String)null);	
		}
		
		if (name.equals("idClassificazioneScelta")) {
			classificazioneSceltaItem.clearValue();
			classificazioneSceltaItem.setValue((String)null);
			idClassificazioneSceltaSelectedItem.setValue((String)null);			
			filtriForm2.setValue("idClassificazioneScelta", (String)null);
			filtriForm2.setValue("idClassificazioneSceltaSelected", (String)null);	
		}
		
	}
	
	
    public void setVisibleAssegnazioneUoForm(boolean flg){
		uoReportForm.getForms()[0].setVisible(flg);
		uoReportForm.getForms()[2].setVisible(flg);
    }
	
	private FilteredSelectItemWithDisplay createFilteredSelectItemWithDisplay(String name,
                                                                              String title, 
                                                                              String datasourceName, 
                                                                              String[] campiVisibili,
                                                                              String[] campiHidden, 
                                                                              String[] descrizioni, 
                                                                              Object[] width,
                                                                              int widthTotale, 
                                                                              String idField, 
                                                                              String displayField) {
        SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource(datasourceName, idField, FieldType.TEXT, campiVisibili, true);
        FilteredSelectItemWithDisplay lFilteredSelectItemWithDisplay = new FilteredSelectItemWithDisplay(name, title, lGwtRestDataSource) {

            @Override
            public void onOptionClick(Record record) {
            	super.onOptionClick(record);
                manageOnOptionClick(getName(), record);
            }

            @Override
            protected void clearSelect() {
                super.clearSelect();
                manageClearSelect(getName());
            }
           
        };

        int i = 0;
        List<ListGridField> lList = new ArrayList<ListGridField>();
        for (String lString : campiVisibili) {
                ListGridField field = new ListGridField(lString, descrizioni[i]);
                if (width[i] instanceof String) {
                    field.setWidth((String) width[i]);
                } 
                else{
                	field.setWidth((Integer) width[i]);
                }
                i++;
                lList.add(field);
        }

        for (String lString : campiHidden) {
                ListGridField field = new ListGridField(lString, lString);
                field.setHidden(true);
                lList.add(field);
        }

        lFilteredSelectItemWithDisplay.setPickListFields(lList.toArray(new ListGridField[] {}));
        lFilteredSelectItemWithDisplay.setFilterLocally(true);
        lFilteredSelectItemWithDisplay.setValueField(idField);
        lFilteredSelectItemWithDisplay.setOptionDataSource(lGwtRestDataSource);
        lFilteredSelectItemWithDisplay.setWidth(widthTotale);
        lFilteredSelectItemWithDisplay.setRequired(false);
        lFilteredSelectItemWithDisplay.setClearable(true);
        lFilteredSelectItemWithDisplay.setShowIcons(true);
        lFilteredSelectItemWithDisplay.setDisplayField(displayField);
        return lFilteredSelectItemWithDisplay;
      }
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
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
	
	public boolean isAbilToRemoveModello(Record record) {
		return (record.getAttribute("key") != null && !"".equals(record.getAttributeAsString("key"))) && 
			   (record.getAttributeAsBoolean("flgAbilDel") != null && record.getAttributeAsBoolean("flgAbilDel"));
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
			public void onRecordClick(final RecordClickEvent event) {
				SC.ask(I18NUtil.getMessages().gestioneModelli_cancellazione_ask(), new BooleanCallback() {
					
					@Override
					public void execute(Boolean value) {
						if(value) {
							deleteModello(event.getRecord());	
						}
					}
				});
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
	
	private void deleteModello (ListGridRecord record) {
		if (isAbilToRemoveModello(record)) {
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
	
	protected void createModelliDatasource(String nomeEntita) {
		
		modelliDS = new GWTRestDataSource("ModelliDataSource", "key", FieldType.TEXT);
		modelliDS.addParam("prefKey", nomeEntita + ".modelli");
		modelliDS.addParam("isAbilToPublic", Layout.isPrivilegioAttivo("EML/MPB") ? "1" : "0");
	}
	
	private Map getMapValues() {
		return vm.getValues();
	}
	
	@Override
	public void editNewRecord(Map initialValues) {
		
		super.editNewRecord(initialValues);
		
		if(uoReportForm != null) {
			Record lRecordAsseganzioneUo = new Record();
			lRecordAsseganzioneUo.setAttribute("tipo", "UO");
			lRecordAsseganzioneUo.setAttribute("idUoSvUt", initialValues.get("idUoAssociata"));
			lRecordAsseganzioneUo.setAttribute("codRapidoUo", initialValues.get("codRapido"));
			lRecordAsseganzioneUo.setAttribute("flgVisibileDaSottoUo", initialValues.get("flgVisibileDaSottoUo") != null && (Boolean) initialValues.get("flgVisibileDaSottoUo"));
			lRecordAsseganzioneUo.setAttribute("flgModificabileDaSottoUo", initialValues.get("flgModificabileDaSottoUo") != null && (Boolean) initialValues.get("flgModificabileDaSottoUo"));
			
			uoReportForm.setFormValuesFromRecord(lRecordAsseganzioneUo);
		}
		
		if(utenteItem != null) {		
			SelectGWTRestDataSource utenteDS = (SelectGWTRestDataSource)utenteItem.getOptionDataSource();
			if (initialValues.get("idUtente") != null && !"".equals(initialValues.get("idUtente"))) {
				utenteDS.addParam("idUtente", (String) initialValues.get("idUtente"));
			} else {
				utenteDS.addParam("idUtente", null);
			}
			utenteItem.setOptionDataSource(utenteDS);
			utenteItem.fetchData();
		}
	}
	
	@Override
	protected void onDestroy() {
		if(saveModelloWindow != null) {
			saveModelloWindow.destroy();
		}
		if(modelliDS != null) {
			modelliDS.destroy();
		}
		super.onDestroy();
	}
}