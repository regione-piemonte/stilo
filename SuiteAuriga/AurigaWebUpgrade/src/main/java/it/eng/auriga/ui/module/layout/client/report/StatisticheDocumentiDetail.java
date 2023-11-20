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

public class StatisticheDocumentiDetail extends CustomDetail {
	
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
	private FilteredSelectItemWithDisplay enteAooItem;	
	private FilteredSelectItemWithDisplay utenteItem;
	
	// SelectItem
	private SelectItem applicazioniEsterneItem;
	private SelectItem registroNumerazioneItem;
	private SelectItem mezzoTrasmissioneItem;
	private SelectItem livelliRiservatezzaItem; 	
	private SelectItem raggruppaUoItem;
	
	// CheckboxItem
	private CheckboxItem raggruppaEnteAooItem; 
	
	// SpacerItem
	private SpacerItem raggruppaEnteAooSpacerItem;
	
		
	// HiddenItem 
	private HiddenItem idUtenteSelectedItem;
	
	private VLayout lVLayout;
	private ToolStrip bottoni;
	
	//Selezione e salvataggio dei modelli
	protected ToolStrip mainToolStrip;
	private SaveModelloWindow saveModelloWindow;
	protected GWTRestDataSource modelliDS;
	protected SelectItem modelliSelectItem;
	protected ListGrid modelliPickListProperties;
	protected ListGridRecord recordModelloFocused;
	
	public StatisticheDocumentiDetail() {

		super("statisticheDocumenti");
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
		
		SelectItem tipoReportItem = new SelectItem("tipoReport");
		tipoReportItem.setShowTitle(false);
		tipoReportItem.setRequired(true);
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();
		lLinkedHashMap.put("registrazioni_documenti", "Registrazioni Effettuate");
		lLinkedHashMap.put("invii_interni",           "Movimentazioni (per competenza e conoscenza)");
		lLinkedHashMap.put("assegnazioni",            "Assegnazioni per competenza");
		lLinkedHashMap.put("invii_interni_cc",        "Invii per conoscenza");
		lLinkedHashMap.put("prese_in_carico",         "Prese in carico");
		tipoReportItem.setValueMap(lLinkedHashMap);
		tipoReportItem.setWidth(770);		
		tipoReportItem.setClearable(true);
		tipoReportItem.setAllowEmptyValue(true);
		tipoReportItem.setRedrawOnChange(true);	
		
		tipoReportItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null) {
					setTitleCodRapidoItem((String) event.getValue());
					setTitleUtenteItem((String) event.getValue());
				}
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
					
		// ENTE/AOO
		enteAooItem = createFilteredSelectItemWithDisplay("idEnteAoo", setTitleAlign(I18NUtil.getMessages().statistichedocumenti_combo_enteaoo_label(), TITLE_WIDTH, false), 
                                                          "LoadComboEnteAooDataSource", 
                                                          new String[] {"descrizioneEnteAoo"}, 
                                                          new String[]{"idEnteAoo"}, 
                                                          new String[]{I18NUtil.getMessages().statistichedocumenti_combo_enteaoo_descrizioneField() },               
                                                          new Object[]{600}, 
                                                          632, 
                                                          "idEnteAoo", 
                                                          "descrizioneEnteAoo");                 
		enteAooItem.setAllowEmptyValue(true);    	
		enteAooItem.setFilterLocally(false);
        enteAooItem.setAutoFetchData(true);
        enteAooItem.setFetchMissingValues(false); 
        enteAooItem.setRedrawOnChange(true);	
        enteAooItem.setColSpan(9);
        enteAooItem.setEndRow(true);
        
        filtriForm1.setItems(lDateItemDa,lDateItemA, 
                enteAooItem);
        
        filtriForm2 = new DynamicForm();
		filtriForm2.setValuesManager(vm);
		filtriForm2.setWidth("100%");
		filtriForm2.setPadding(5);
		filtriForm2.setNumCols(10);
		filtriForm2.setColWidths(40, 100, 40, 100, 40, 100, 40, 100, 40, "*");
		filtriForm2.setWrapItemTitles(false);
		       
		// APPLICAZIONI ESTERNE
        applicazioniEsterneItem = new SelectItem("applicazioniEsterne" ,I18NUtil.getMessages().statistichedocumenti_combo_applicazioniEsterne_label());			
		applicazioniEsterneItem.setName("applicazioniEsterne");
		applicazioniEsterneItem.setMultiple(true);		
		applicazioniEsterneItem.setOptionDataSource(new GWTRestDataSource("LoadComboApplicazioniEsterneDataSource", "key", FieldType.TEXT));  
		applicazioniEsterneItem.setAutoFetchData(true);
		applicazioniEsterneItem.setDisplayField("value");
		applicazioniEsterneItem.setValueField("key");			
		applicazioniEsterneItem.setWrapTitle(false);
		applicazioniEsterneItem.setAllowEmptyValue(true);
		applicazioniEsterneItem.setClearable(true);
		applicazioniEsterneItem.setAllowEmptyValue(true);
		applicazioniEsterneItem.setRedrawOnChange(true);
		applicazioniEsterneItem.setWidth(614); 
		applicazioniEsterneItem.setColSpan(9);
		applicazioniEsterneItem.setEndRow(true);		
		applicazioniEsterneItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return Layout.isPrivilegioAttivo("REP/STD");
			}
		});
								
		// OPERATORE	
		idUtenteSelectedItem = new HiddenItem("idUtenteSelected"); 
		utenteItem = createFilteredSelectItemWithDisplay("idUtente",
                                                          I18NUtil.getMessages().statistichedocumenti_combo_utente_label(), 
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
		utenteItem.setEndRow(true);
				
				
		// TIPO REGISTRAZIONE
		SelectItem tipoRegistrazioneItem = new SelectItem("tipoRegistrazione", I18NUtil.getMessages().statistichedocumenti_combo_tipoRegistrazione_label());		
		LinkedHashMap<String, String> lLinkedHashMapTipoRegistrazione = new LinkedHashMap<String, String>();
		lLinkedHashMapTipoRegistrazione.put("E", "In entrata");
		lLinkedHashMapTipoRegistrazione.put("U", "In uscita");
		lLinkedHashMapTipoRegistrazione.put("I", "Interni");
		tipoRegistrazioneItem.setValueMap(lLinkedHashMapTipoRegistrazione);
		tipoRegistrazioneItem.setWidth(180);		
		tipoRegistrazioneItem.setClearable(true);
		tipoRegistrazioneItem.setAllowEmptyValue(true);
		tipoRegistrazioneItem.setMultiple(true);
		tipoRegistrazioneItem.setEndRow(true);
		
		// CATEGORIA REGISTRAZIONE
		SelectItem categoriaRegistrazioneItem = new SelectItem("categoriaRegistrazione", I18NUtil.getMessages().statistichedocumenti_combo_categoriaRegistrazione_label());
		LinkedHashMap<String, String> lLinkedHashMapCategoriaRegistrazione = new LinkedHashMap<String, String>();
		lLinkedHashMapCategoriaRegistrazione.put("PG", "Protocollo Generale");
		lLinkedHashMapCategoriaRegistrazione.put("PP", "Protocollo Particolare");
		lLinkedHashMapCategoriaRegistrazione.put("R",  "Repertorio");
		lLinkedHashMapCategoriaRegistrazione.put("E",  "Registrazione di emergenza");
		lLinkedHashMapCategoriaRegistrazione.put("AL", "Altro");
	    categoriaRegistrazioneItem.setValueMap(lLinkedHashMapCategoriaRegistrazione);
	    categoriaRegistrazioneItem.setWidth(180);		
	    categoriaRegistrazioneItem.setClearable(true);
	    categoriaRegistrazioneItem.setAllowEmptyValue(true);
	    categoriaRegistrazioneItem.setMultiple(true);
	    
	    // REGISTRO DI NUMERAZIONE
		registroNumerazioneItem = new SelectItem("registroNumerazione" ,I18NUtil.getMessages().statistichedocumenti_combo_registroNumerazione_label());			
		registroNumerazioneItem.setName("registroNumerazione");
		registroNumerazioneItem.setMultiple(true);
		registroNumerazioneItem.setOptionDataSource(new GWTRestDataSource("LoadComboRegistroNumerazioneDataSource", "key", FieldType.TEXT));  
		registroNumerazioneItem.setAutoFetchData(true);
		registroNumerazioneItem.setDisplayField("value");
		registroNumerazioneItem.setValueField("key");			
		registroNumerazioneItem.setWrapTitle(false);
		registroNumerazioneItem.setAllowEmptyValue(true);
		registroNumerazioneItem.setClearable(true);
		registroNumerazioneItem.setAllowEmptyValue(true);
		registroNumerazioneItem.setRedrawOnChange(true);
		registroNumerazioneItem.setWidth(220);  
		registroNumerazioneItem.setEndRow(true);
		
	    			
		// SUPPORTO
	    SelectItem supportoItem = new SelectItem("supporto", I18NUtil.getMessages().statistichedocumenti_combo_supporto_label());		
		LinkedHashMap<String, String> lLinkedHashMapSupporto = new LinkedHashMap<String, String>();
		lLinkedHashMapSupporto.put("digitale",  "Digitale");
		lLinkedHashMapSupporto.put("analogico", "Cartaceo");
		lLinkedHashMapSupporto.put("misto",     "Misto");		
	    supportoItem.setValueMap(lLinkedHashMapSupporto);
	    supportoItem.setWidth(180);		
	    supportoItem.setClearable(true);
	    supportoItem.setMultiple(false);
	    supportoItem.setAllowEmptyValue(true);
		
		// MEZZO DI TRASMISSIONE
		mezzoTrasmissioneItem = new SelectItem("mezzoTrasmissione" ,I18NUtil.getMessages().statistichedocumenti_combo_mezzoTrasmissione_label());			
		mezzoTrasmissioneItem.setName("mezzoTrasmissione");
		mezzoTrasmissioneItem.setMultiple(true);
		mezzoTrasmissioneItem.setOptionDataSource(new GWTRestDataSource("LoadComboMezzoTrasmissioneDataSource", "key", FieldType.TEXT));  
		mezzoTrasmissioneItem.setAutoFetchData(true);
		mezzoTrasmissioneItem.setDisplayField("value");
		mezzoTrasmissioneItem.setValueField("key");			
		mezzoTrasmissioneItem.setWrapTitle(false);
		mezzoTrasmissioneItem.setAllowEmptyValue(true);
		mezzoTrasmissioneItem.setClearable(true);
		mezzoTrasmissioneItem.setAllowEmptyValue(true);
		mezzoTrasmissioneItem.setRedrawOnChange(true);
		mezzoTrasmissioneItem.setWidth(220);  	
		mezzoTrasmissioneItem.setEndRow(true);		
		
		// PRESENZA FILE
	    SelectItem presenzaFileItem = new SelectItem("presenzaFile", I18NUtil.getMessages().statistichedocumenti_combo_presenzaFile_label());		
		LinkedHashMap<String, String> lLinkedHashMapPresenzaFile = new LinkedHashMap<String, String>();
		lLinkedHashMapPresenzaFile.put("1", "Con file");
		lLinkedHashMapPresenzaFile.put("0", "Senza file");				
		presenzaFileItem.setValueMap(lLinkedHashMapPresenzaFile);
		presenzaFileItem.setWidth(180);		
		presenzaFileItem.setClearable(true);
		presenzaFileItem.setMultiple(false);
		presenzaFileItem.setAllowEmptyValue(true);
		
		// LIVELLI RISERVATEZZA
		livelliRiservatezzaItem = new SelectItem("livelliRiservatezza" ,I18NUtil.getMessages().statistichedocumenti_combo_livelliRiservatezza_label());			
		livelliRiservatezzaItem.setName("livelliRiservatezza");
		livelliRiservatezzaItem.setMultiple(true);		
		livelliRiservatezzaItem.setOptionDataSource(new GWTRestDataSource("LoadComboLivelliRiservatezzaStatisticheDocDataSource", "key", FieldType.TEXT));  
		livelliRiservatezzaItem.setAutoFetchData(true);
		livelliRiservatezzaItem.setDisplayField("value");
		livelliRiservatezzaItem.setValueField("key");			
		livelliRiservatezzaItem.setWrapTitle(false);
		livelliRiservatezzaItem.setAllowEmptyValue(true);
		livelliRiservatezzaItem.setClearable(true);
		livelliRiservatezzaItem.setAllowEmptyValue(true);
		livelliRiservatezzaItem.setRedrawOnChange(true);
		livelliRiservatezzaItem.setWidth(220);  
		livelliRiservatezzaItem.setEndRow(true);
		
		filtriForm2.setItems(utenteItem, 
							 applicazioniEsterneItem,
                             idUtenteSelectedItem,
                             tipoRegistrazioneItem, 
                             categoriaRegistrazioneItem, spacerItem, registroNumerazioneItem ,   
                             supportoItem,               spacerItem, mezzoTrasmissioneItem,
                             presenzaFileItem,           spacerItem, livelliRiservatezzaItem
                            
                            );
				
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
		DynamicForm[] listaForm = new DynamicForm[] { filtriForm1 , uoReportForm.getForms()[0], uoReportForm.getForms()[2], filtriForm2 };
		filtriSection = new DetailSection(I18NUtil.getMessages().statisticheDocumenti_filtri_section_title(), 3, true, true, false,  listaForm );
		
		lVLayout.addMember(filtriSection);		
	}
	
	// Sezione RAGGRUPPAMENTO
	private void sezioneRaggruppamento(){
				
		SpacerItem raggruppamentoSpacerItem = new SpacerItem();
		raggruppamentoSpacerItem.setWidth(2);
		
		raggruppaEnteAooSpacerItem = new SpacerItem();
		raggruppaEnteAooSpacerItem.setWidth(2);
		
		raggruppamentoForm = new DynamicForm();
		raggruppamentoForm.setValuesManager(vm);
		raggruppamentoForm.setWidth("100%");
		raggruppamentoForm.setPadding(5);
		raggruppamentoForm.setNumCols(14);
		raggruppamentoForm.setColWidths(60, 150, 50, 150, 30, 150, 30, 150, 30, 150, 30, 150, 30,"*");
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
	    // la select "Periodo" nella sezione dei raggruppamenti deve essere a scelta singola e NON multipla
//	    raggruppaPeriodoItem.setMultiple(true);
	    
		// TIPO UO
	    raggruppaUoItem = new SelectItem("raggruppaUo" ,I18NUtil.getMessages().statistichedocumenti_combo_raggruppaUo_label());			
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
			
		
		//*****************************************
		// RIGA 1 
		//*****************************************
		// APPLICAZIONI ESTERNE
		CheckboxItem raggruppaApplicazioniEsterneItem = new CheckboxItem("raggruppaApplicazioniEsterne", I18NUtil.getMessages().statistichedocumenti_check_applicazioniEsterne_label());
		raggruppaApplicazioniEsterneItem.setWidth(180);
		raggruppaApplicazioniEsterneItem.setColSpan(2);
		
		// OPERATORE
		CheckboxItem raggruppaUtenteItem = new CheckboxItem("raggruppaUtente", I18NUtil.getMessages().statistichedocumenti_check_utente_label());
		raggruppaUtenteItem.setWidth(50);
		raggruppaUtenteItem.setColSpan(2);
		raggruppaUtenteItem.setEndRow(true);
		
		//*****************************************
		// RIGA 2 
		//*****************************************
		// TIPO REGISTRAZIONE
		CheckboxItem raggruppaTipoRegistrazioneItem = new CheckboxItem("raggruppaTipoRegistrazione", I18NUtil.getMessages().statistichedocumenti_combo_tipoRegistrazione_label());
		raggruppaTipoRegistrazioneItem.setWidth(130);
		raggruppaTipoRegistrazioneItem.setColSpan(2);
		
		// CATEGORIA REGISTRAZIONE
//		CheckboxItem raggruppaCategoriaRegistrazioneItem = new CheckboxItem("raggruppaCategoriaRegistrazione", I18NUtil.getMessages().statistichedocumenti_combo_categoriaRegistrazione_label());
//		raggruppaCategoriaRegistrazioneItem.setWidth(50);
//		raggruppaCategoriaRegistrazioneItem.setColSpan(2);
		
		 // REGISTRO DI NUMERAZIONE
		CheckboxItem raggruppaRegistroNumerazioneItem = new CheckboxItem("raggruppaRegistroNumerazione", I18NUtil.getMessages().statistichedocumenti_combo_registroNumerazione_label());
		raggruppaRegistroNumerazioneItem.setWidth(150);
		raggruppaRegistroNumerazioneItem.setColSpan(2);
		
//		Reg. valide/annullate
		CheckboxItem raggruppaRegValideAnnullateItem = new CheckboxItem("raggruppaRegValideAnnullate", I18NUtil.getMessages().statistichedocumenti_check_raggruppaRegValideAnnullate_label());
		raggruppaRegValideAnnullateItem.setWidth(100);
		raggruppaRegValideAnnullateItem.setColSpan(2);
		raggruppaRegValideAnnullateItem.setEndRow(true);
	   
		//*****************************************
		// RIGA 3
		//*****************************************
		
		// SUPPORTO
	    CheckboxItem raggruppaSupportoItem = new CheckboxItem("raggruppaSupporto", I18NUtil.getMessages().statistichedocumenti_combo_supporto_label());
	    raggruppaSupportoItem.setWidth(80);
	    raggruppaSupportoItem.setColSpan(2);
	    
	    // MEZZO DI TRASMISSIONE
	    CheckboxItem raggruppaMezzoTrasmissioneItem = new CheckboxItem("raggruppaMezzoTrasmissione", I18NUtil.getMessages().statistichedocumenti_combo_mezzoTrasmissione_label());
	    raggruppaMezzoTrasmissioneItem.setWidth(50);
	    raggruppaMezzoTrasmissioneItem.setColSpan(2);
	    
	    // PRESENZA FILE
	    CheckboxItem raggruppaPresenzaFileItem = new CheckboxItem("raggruppaPresenzaFile", I18NUtil.getMessages().statistichedocumenti_check_raggruppaPresenzaFile_label());
	    raggruppaPresenzaFileItem.setWidth(100);
	    raggruppaPresenzaFileItem.setColSpan(2);
	    raggruppaPresenzaFileItem.setEndRow(true);
	    
		//*****************************************
		// RIGA 4
		//*****************************************
	    // LIVELLI RISERVATEZZA
	    CheckboxItem raggruppaLivelliRiservatezzaItem = new CheckboxItem("raggruppaLivelliRiservatezza", I18NUtil.getMessages().statistichedocumenti_combo_livelliRiservatezza_label());
	    raggruppaLivelliRiservatezzaItem.setWidth(150);
	    raggruppaLivelliRiservatezzaItem.setColSpan(2);
	    
	   
		// ENTE/AOO
		raggruppaEnteAooItem = new CheckboxItem("raggruppaEnteAoo", I18NUtil.getMessages().statistichedocumenti_combo_enteaoo_label());
		raggruppaEnteAooItem.setWidth(150);
		raggruppaEnteAooItem.setColSpan(2);
		
		raggruppamentoForm.setItems(raggruppaPeriodoItem,                 raggruppaUoItem,
				                    raggruppaApplicazioniEsterneItem,     raggruppaUtenteItem,                  
				                    raggruppaTipoRegistrazioneItem,       raggruppaRegistroNumerazioneItem,		   raggruppaRegValideAnnullateItem,
				                    raggruppaSupportoItem,                raggruppaMezzoTrasmissioneItem,          raggruppaPresenzaFileItem,        
				                    raggruppaLivelliRiservatezzaItem,     raggruppaEnteAooItem,                    raggruppaEnteAooSpacerItem 				            
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
					GWTRestDataSource reportDocAvanzatiDatasource = new GWTRestDataSource("ReportDocAvanzatiDatasource");
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
											   VisualizzaDatiStatisticheDocumentiWindow lVisualizzaDatiStatisticheDocumentiWindow = new VisualizzaDatiStatisticheDocumentiWindow(returnedValue);   
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
		if (name.equals("idEnteAoo")) {
			filtriForm1.setValue("idEnteAoo", record.getAttributeAsString("idEnteAoo"));	           
            enteAooItem.setValue(record.getAttribute("idEnteAoo"));
			refreshCombo((String)record.getAttribute("idEnteAoo"));
        }
		if (name.equals("idUtente")) {
			filtriForm2.setValue("idUtenteSelected", record.getAttributeAsString("idUtente"));	
			idUtenteSelectedItem.setValue(record.getAttribute("idUtente"));
        }
	}
	
	private void manageClearSelect(String name){
		if (name.equals("idEnteAoo")){			
			enteAooItem.clearValue();
			enteAooItem.setValue((String)null);						
			filtriForm1.setValue("idEnteAoo",          (String)null);
			refreshCombo("");			
		}
		if (name.equals("idUtente")){			
			utenteItem.clearValue();
			utenteItem.setValue((String)null);					
			idUtenteSelectedItem.setValue((String)null);
			filtriForm2.setValue("idUtente",      (String)null);
			filtriForm2.setValue("idUtenteSelected",(String)null);
		}
	}
	
	private void refreshCombo(String dominio){
		
		// combo APPLICAZIONI ESTERNE
		applicazioniEsterneItem.clearValue();
        applicazioniEsterneItem.setValue((String)null);	
		filtriForm2.setValue("applicazioniEsterne",(String)null);
		((GWTRestDataSource)applicazioniEsterneItem.getOptionDataSource()).addParam("dominio", dominio);
		applicazioniEsterneItem.fetchData();
				
		// combo OPERATORE
		utenteItem.clearValue();
		utenteItem.setValue((String)null);	
		filtriForm2.setValue("idUtente",(String)null);
		((SelectGWTRestDataSource)utenteItem.getOptionDataSource()).addParam("dominio", dominio);
		utenteItem.fetchData();
				
		// combo REGISTRO DI NUMERAZIONE		
		registroNumerazioneItem.clearValue();
		registroNumerazioneItem.setValue((String)null);	
		filtriForm2.setValue("registroNumerazione",(String)null);
		((GWTRestDataSource)registroNumerazioneItem.getOptionDataSource()).addParam("dominio", dominio);
		registroNumerazioneItem.fetchData();
				
		// combo MEZZO DI TRASMISSIONE
		mezzoTrasmissioneItem.clearValue();
		mezzoTrasmissioneItem.setValue((String)null);	
		filtriForm2.setValue("mezzoTrasmissione",(String)null);
		((GWTRestDataSource)mezzoTrasmissioneItem.getOptionDataSource()).addParam("dominio", dominio);
		mezzoTrasmissioneItem.fetchData();		
		
		// combo LIVELLI RISERVATEZZA
		livelliRiservatezzaItem.clearValue();
		livelliRiservatezzaItem.setValue((String)null);	
		filtriForm2.setValue("livelliRiservatezza",(String)null);
		((GWTRestDataSource)livelliRiservatezzaItem.getOptionDataSource()).addParam("dominio", dominio);
		livelliRiservatezzaItem.fetchData();
				
		// combo Raggruppa per TIPO UO
		raggruppaUoItem.clearValue();
		raggruppaUoItem.setValue((String)null);	
		raggruppamentoForm.setValue("raggruppaUo",(String)null);
		((GWTRestDataSource)raggruppaUoItem.getOptionDataSource()).addParam("dominio", dominio);
		raggruppaUoItem.fetchData();				
	}
	
	public void setVisibleEnteAooItem(boolean flg){
		if(flg)
			enteAooItem.show();
		else
			enteAooItem.hide();	
	}
	
    public void setApplicazioniEsterneItem(boolean flg){		
		if(flg)
			applicazioniEsterneItem.show();
		else
			applicazioniEsterneItem.hide();		
	}
    
    public void setVisibleRaggruppaEnteAooItem(boolean flg){		
		if(flg){
			raggruppaEnteAooItem.show();
			raggruppaEnteAooSpacerItem.hide();
		}			
		else{
			raggruppaEnteAooItem.hide();
			raggruppaEnteAooSpacerItem.show();
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
//		modelliRemoveField.setIsRemoveField(true);
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
//		modelliPickListProperties.setCanRemoveRecords(true); 		
		// apply the selected preference from the SelectItem
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
			String tipo = "";
			if (initialValues.get("tipoReport")!=null)
			   tipo = (String)initialValues.get("tipoReport");
			
			setTitleCodRapidoItem(tipo);
			setTitleUtenteItem(tipo);
			
			
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
	
	private void setTitleCodRapidoItem(String tipo){
		if(tipo.equalsIgnoreCase("registrazioni_documenti"))
			uoReportForm.refreshTitleCodRapidoItem("Registrati dalla U.O.");
		else if (tipo.equalsIgnoreCase("invii_interni"))
			uoReportForm.refreshTitleCodRapidoItem("Inviati alla U.O.");
		else if (tipo.equalsIgnoreCase("assegnazioni"))
			uoReportForm.refreshTitleCodRapidoItem("Assegnati alla U.O.");
		else if (tipo.equalsIgnoreCase("invii_interni_cc"))
			uoReportForm.refreshTitleCodRapidoItem("Inviati alla U.O.");
		else if (tipo.equalsIgnoreCase("prese_in_carico"))
			uoReportForm.refreshTitleCodRapidoItem("Presi in carico dalla U.O.");
		else
			uoReportForm.refreshTitleCodRapidoItem(uoReportForm.getTitleCodRapidoItem());
			
	}
	

	private void setTitleUtenteItem(String tipo){
		if(tipo.equalsIgnoreCase("registrazioni_documenti"))
			utenteItem.setTitle("Registrati dall’operatore");
		else if (tipo.equalsIgnoreCase("invii_interni"))
			utenteItem.setTitle("Inviati dall’operatore");
		else if (tipo.equalsIgnoreCase("assegnazioni"))
			utenteItem.setTitle("Assegnati dall’operatore");
		else if (tipo.equalsIgnoreCase("invii_interni_cc"))
			utenteItem.setTitle("Inviati dall’operatore");
		else if (tipo.equalsIgnoreCase("prese_in_carico"))
			utenteItem.setTitle("Presi in carico dall’operatore");
		else
			utenteItem.setTitle("Registrati dall’operatore");	
		
		utenteItem.redraw();
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
