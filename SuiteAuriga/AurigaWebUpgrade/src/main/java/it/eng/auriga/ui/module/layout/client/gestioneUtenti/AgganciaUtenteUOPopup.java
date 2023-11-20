/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
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
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;

public abstract class AgganciaUtenteUOPopup extends ModalWindow {
	
	private AgganciaUtenteUOPopup window;
	
	protected ValuesManager vm;
	
	// DetailSection
	private DetailSection formMainSection;
	private DetailSection resocontoSpostamentoDocFascSection;
	private DetailSection uoSpostaDocFascSection;	
	
	// DynamicForm
	private DynamicForm formMain;
	private DynamicForm uoSpostaDocFascForm;
	private DynamicForm resocontoSpostamentoDocFascForm;
	
	// SelectItem
	private SelectItem tipoDiAssegnazioneSelectItem;
	private SelectItem ruoloItem;
	private SelectItem tipoItem;
	private SelectItem tipoSpostaDocFascItem;
	
	// FilteredSelectItemWithDisplay
	private FilteredSelectItemWithDisplay organigrammaItem;
	private FilteredSelectItemWithDisplay gruppoItem;
	private FilteredSelectItemWithDisplay organigrammaSpostaDocFascItem;
	
	// CheckboxItem
	private CheckboxItem flgAccessoDocLimSVItem;
	private CheckboxItem flgInclSottoUoItem;
	private CheckboxItem flgIncluseScrivanieItem;
	private CheckboxItem flgRegistrazioneEItem;
	private CheckboxItem flgRegistrazioneUIItem;
	private CheckboxItem flgGestAttiItem;
	private CheckboxItem flgVisPropAttiInIterItem;
	private CheckboxItem flgRiservatezzaRelUserUoItem;
	private CheckboxItem flgGestAttiTuttiItem;
	private CheckboxItem flgVisPropAttiInIterTuttiItem;
	

	// DateItem
	private DateItem dataDalItem;
	private DateItem dataAlItem;
	
	// TextItem
	private TextItem denominazioneScrivaniaUtenteItem;
	private TextItem descUoSpostamentoDocFascItem;
	private TextItem statoSpostamentoDocFascItem;
	
	// ExtendedTextItem
	private ExtendedTextItem codRapidoItem;
	private ExtendedTextItem codRapidoSpostaDocFascItem;
	
	// SpacerItem
	private SpacerItem spacer1Item;
	private SpacerItem spacerFlgRiservatezzaRelUserUoItem;
	
	
	// ImgButtonItem
	private ImgButtonItem lookupOrganigrammaButton;
	private ImgButtonItem uoCollegatePuntoProtocolloButton;
	private ImgButtonItem lookupOrganigrammaSpostaDocFascButton;
	private ImgButtonItem selezionaTipiGestAttiButton;
	private ImgButtonItem selezionaTipiVisPropAttiInIterButton;
	
	// NumericItem
	private NumericItem nrDocAssegnatiItem;
	private NumericItem nrFascAssegnatiItem;
	
	// DateTimeItem
	private DateTimeItem dataConteggioFascAssegnatiItem;
	private DateTimeItem dataInizioSpostamentoDocFascItem;
	private DateTimeItem dataFineSpostamentoDocFascItem;
	private DateTimeItem dataConteggioDocAssegnatiItem;

	// HiddenItem
	private HiddenItem idUoSvUtItem;
	private HiddenItem ciRelUserUoItem;
	private HiddenItem idScrivaniaItem;
	private HiddenItem idUoItem;
	private HiddenItem rowIdItem;
	private HiddenItem sostituzioneSVItem;
	private HiddenItem dataAlOrigItem;
	private HiddenItem descrizioneHiddenItem;
	private HiddenItem typeNodoHiddenItem;
	private HiddenItem descTipoRelUtenteUoItem;
	private HiddenItem idRuoloItem;
	private HiddenItem descrizioneRuoloItem;
	private HiddenItem flgUoPuntoProtocolloItem;
	
	// ottavio
	private HiddenItem flgUoAbilRegistrazioneEItem;
	private HiddenItem flgUoAbilRegistrazioneUItem;
	
	private HiddenItem listaUOPuntoProtocolloEscluseItem;
	private HiddenItem listaUOPuntoProtocolloIncluseItem;
	private HiddenItem listaUOPuntoProtocolloEreditarietaAbilitataItem;
	private HiddenItem flgPresentiDocFascItem;
	private HiddenItem typeNodoSpostaDocFascItem;
	private HiddenItem idUoSpostaDocFascItem;
	private HiddenItem descrizioneSpostaDocFascItem;
	private HiddenItem listaTipiGestAttiSelezionatiItem;
	private HiddenItem listaTipiVisPropAttiInIterSelezionatiItem;
	
	private String idUdProtocollo;
	private String tipoAssegnatari;
	private String tipoAssegnatariSpostaDocFasc;
	private String mode;

	private boolean isVersioneNestle;
	
	
	public AgganciaUtenteUOPopup(Record record, String title){
		this(record, title, false, "view");
	}
	
	public AgganciaUtenteUOPopup(Record record, String title, boolean isVersioneNestle, String mode){
		
		super("aggancia_utente_uo", true);
		
		window = this;
		
		vm = new ValuesManager();	
		
		this.isVersioneNestle = isVersioneNestle;
		
		setHeight(535);
		setWidth(900);	
		setTitle(title);
        settingsMenu.removeItem(separatorMenuItem);
        settingsMenu.removeItem(autoSearchMenuItem);
		
        setMode(mode);
        
    	setIdUdProtocollo(null);	
		
		setTipoAssegnatari("UO");
		setTipoAssegnatariSpostaDocFasc("SV");
		
		disegnaForm();
		
		initForm(record);
			
		final Button okButton = new Button("Ok");   
		okButton.setIcon("ok.png");
		okButton.setIconSize(16); 
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				okButton.focusAfterGroup();
				if(formMain.validate()) {
					if (validate()){						
						final Record formRecord = getRecordToSave();
						onClickOkButton(formRecord, new DSCallback() {	
							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								window.markForDestroy();
							}
						});
					}
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
		layout.addMember(uoSpostaDocFascSection);	
		layout.addMember(resocontoSpostamentoDocFascSection);
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
		
		// combo TIPO ASSEGNAZIONE
		if (record.getAttribute("tipoRelazione") != null && !"".equals(record.getAttributeAsString("tipoRelazione")) && record.getAttribute("descTipoRelazione") != null &&  !record.getAttribute("descTipoRelazione").equalsIgnoreCase("")  ) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("tipoRelazione"), record.getAttribute("descTipoRelazione"));
			tipoDiAssegnazioneSelectItem.setValueMap(valueMap);
			tipoDiAssegnazioneSelectItem.setValue(record.getAttribute("tipoRelazione"));
			formMain.setValue("descTipoRelUtenteUo", record.getAttribute("descTipoRelazione"));
		}	
		
		// DATA INIZIO
		if (record.getAttribute("dtInizioVld")!=null && !"".equals(record.getAttributeAsString("dtInizioVld"))){
			formMain.setValue("dataDal",      record.getAttributeAsString("dtInizioVld"));
		}
		
		// DATA FINE
		if (record.getAttribute("dtFineVld")!=null && !"".equals(record.getAttributeAsString("dtFineVld"))){
			formMain.setValue("dataAl",      record.getAttributeAsString("dtFineVld"));			
		}
		
		// combo RUOLO
		if (record.getAttribute("idRuolo") != null && !"".equals(record.getAttributeAsString("idRuolo")) && record.getAttribute("descrizioneRuolo") != null &&  !record.getAttribute("descrizioneRuolo").equalsIgnoreCase("")  ) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idRuolo"), record.getAttribute("descrizioneRuolo"));
			ruoloItem.setValueMap(valueMap);
			ruoloItem.setValue(record.getAttribute("idRuolo"));
			formMain.setValue("idRuolo", record.getAttribute("idRuolo"));
			formMain.setValue("descrizioneRuolo", record.getAttribute("descrizioneRuolo"));
		}
		
		// NOME POSTAZIONE 
		denominazioneScrivaniaUtenteItem.setValue(record.getAttribute("denominazioneScrivaniaUtente"));
		formMain.setValue("denominazioneScrivaniaUtente",      record.getAttribute("denominazioneScrivaniaUtente"));   
		
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
		
		// ID UO
		idUoItem.setValue(record.getAttribute("idUo"));
		formMain.setValue("idUo",      record.getAttribute("idUo"));  
		
		// ID UO
		rowIdItem.setValue(record.getAttribute("rowId"));
		formMain.setValue("rowId",      record.getAttribute("rowId"));
		
		// DESCRIZIONE
		descrizioneHiddenItem.setValue(record.getAttribute("descrizione"));
		formMain.setValue("descrizione",      record.getAttribute("descrizione"));  
		
		// TYPE NODO
		typeNodoHiddenItem.setValue(record.getAttribute("typeNodo"));
		formMain.setValue("typeNodo",      record.getAttribute("typeNodo"));  
					
		// FLAG accesso limitato doc. assegnata personalmente"
		flgAccessoDocLimSVItem.setValue(record.getAttributeAsBoolean("flgAccessoDocLimSV"));
		formMain.setValue("flgAccessoDocLimSV",      record.getAttributeAsBoolean("flgAccessoDocLimSV"));  
		
		// FLAG delega alle sotto-UO
		flgInclSottoUoItem.setValue(record.getAttributeAsBoolean("flgIncluseSottoUo"));
		formMain.setValue("flgInclSottoUo",      record.getAttributeAsBoolean("flgIncluseSottoUo"));  
		
		// FLAG delega alle postazioni utente
		flgIncluseScrivanieItem.setValue(record.getAttributeAsBoolean("flgIncluseScrivanie"));
		formMain.setValue("flgIncluseScrivanie",      record.getAttributeAsBoolean("flgIncluseScrivanie"));  
						
		// FLAG registrazione in entrata
		flgRegistrazioneEItem.setValue(record.getAttributeAsBoolean("flgRegistrazioneE"));
		formMain.setValue("flgRegistrazioneE",      record.getAttributeAsBoolean("flgRegistrazioneE"));  
				
		// FLAG registrazione in uscita/interna
		flgRegistrazioneUIItem.setValue(record.getAttributeAsBoolean("flgRegistrazioneUI"));
		formMain.setValue("flgRegistrazioneUI",      record.getAttributeAsBoolean("flgRegistrazioneUI"));  
		
		// FLAG avvio/gestione atti proposti
		flgGestAttiItem.setValue(record.getAttributeAsBoolean("flgGestAtti"));
		formMain.setValue("flgGestAtti",      record.getAttributeAsBoolean("flgGestAtti"));  

		// FLAG tuttti i tipi avvio/gestione atti proposti
		flgGestAttiTuttiItem.setValue(record.getAttributeAsBoolean("flgGestAttiTutti"));
		formMain.setValue("flgGestAttiTutti",      record.getAttributeAsBoolean("flgGestAttiTutti"));
		
		// Lista tipi avvio/gestione atti proposti
		listaTipiGestAttiSelezionatiItem.setValue(record.getAttributeAsBoolean("listaTipiGestAttiSelezionati"));
		formMain.setValue("listaTipiGestAttiSelezionati",      record.getAttribute("listaTipiGestAttiSelezionati"));
		
		// FLAG visualizzazione atti proposti
		flgVisPropAttiInIterItem.setValue(record.getAttributeAsBoolean("flgVisPropAttiInIter"));
		formMain.setValue("flgVisPropAttiInIter",      record.getAttributeAsBoolean("flgVisPropAttiInIter"));  
		
		// FLAG tutti i tipi visualizzazione atti proposti
		flgVisPropAttiInIterTuttiItem.setValue(record.getAttributeAsBoolean("flgVisPropAttiInIterTutti"));
		formMain.setValue("flgVisPropAttiInIterTutti",      record.getAttributeAsBoolean("flgVisPropAttiInIterTutti"));
		
		// Lista tipi visualizzazione atti proposti
		listaTipiVisPropAttiInIterSelezionatiItem.setValue(record.getAttributeAsBoolean("listaTipiVisPropAttiInIterSelezionati"));
		formMain.setValue("listaTipiVisPropAttiInIterSelezionati",      record.getAttribute("listaTipiVisPropAttiInIterSelezionati"));
		
		// FLAG Abilitazione alla documentazione riservata assegnata alla struttura 
		flgRiservatezzaRelUserUoItem.setValue(record.getAttribute("flgRiservatezzaRelUserUo"));
		formMain.setValue("flgRiservatezzaRelUserUo",      record.getAttributeAsBoolean("flgRiservatezzaRelUserUo")); 
				
		// FLAG UO PUNTO PROTOCOLLO
		flgUoPuntoProtocolloItem.setValue(record.getAttribute("flgUoPuntoProtocollo"));
		formMain.setValue("flgUoPuntoProtocollo",      record.getAttribute("flgUoPuntoProtocollo")); 
		
		// LISTA UO PUNTO PROTOCOLLO INCLUSE
		listaUOPuntoProtocolloIncluseItem.setValue(record.getAttribute("listaUOPuntoProtocolloIncluse"));
		formMain.setValue("listaUOPuntoProtocolloIncluse",      record.getAttribute("listaUOPuntoProtocolloIncluse"));
		
		// LISTA UO PUNTO PROTOCOLLO ESCLUSE
		listaUOPuntoProtocolloEscluseItem.setValue(record.getAttribute("listaUOPuntoProtocolloEscluse"));
		formMain.setValue("listaUOPuntoProtocolloEscluse",      record.getAttribute("listaUOPuntoProtocolloEscluse"));
		
		// LISTA UO PUNTO PROTOCOLLO CON EREDITARIETA ABILITATA
		listaUOPuntoProtocolloEreditarietaAbilitataItem.setValue(record.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata"));
		formMain.setValue("listaUOPuntoProtocolloEreditarietaAbilitata", record.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata"));
		
		/** UO per spostare doc/fasc **/
		flgPresentiDocFascItem.setValue(record.getAttribute("flgPresentiDocFasc"));
		uoSpostaDocFascForm.setValue("flgPresentiDocFasc",      record.getAttribute("flgPresentiDocFasc"));
		
		// Combo Organigramma Sposta Doc/Fasc
		if (record.getAttributeAsString("idUODestDocfasc") != null && !"".equals(record.getAttributeAsString("idUODestDocfasc"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttributeAsString("organigrammaSpostaDocFasc"), record.getAttributeAsString("desUODestDocFasc"));
			organigrammaSpostaDocFascItem.setValueMap(valueMap);
			organigrammaSpostaDocFascItem.setValue(record.getAttributeAsString("organigrammaSpostaDocFasc"));
			uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", record.getAttributeAsString("organigrammaSpostaDocFasc") );
			uoSpostaDocFascForm.setValue("idUoSpostaDocFasc", record.getAttributeAsString("idUODestDocfasc"));
			uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", record.getAttribute("desUODestDocFasc"));	
			uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", record.getAttribute("livelliUODestDocFasc"));
			uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", "UO");
		} 
				
		/** Situazione documentazione in competenza alla UO **/
		statoSpostamentoDocFascItem.setValue(record.getAttribute("statoSpostamentoDocFasc"));
		resocontoSpostamentoDocFascForm.setValue("statoSpostamentoDocFasc",      record.getAttribute("statoSpostamentoDocFasc"));
		
		dataInizioSpostamentoDocFascItem.setValue(record.getAttribute("dataInizioSpostamentoDocFasc"));
		resocontoSpostamentoDocFascForm.setValue("dataInizioSpostamentoDocFasc",      record.getAttribute("dataInizioSpostamentoDocFasc"));
		
		dataFineSpostamentoDocFascItem.setValue(record.getAttribute("dataFineSpostamentoDocFasc"));
		resocontoSpostamentoDocFascForm.setValue("dataFineSpostamentoDocFasc",      record.getAttribute("dataFineSpostamentoDocFasc"));
		
		nrDocAssegnatiItem.setValue(record.getAttribute("nrDocAssegnati"));
		resocontoSpostamentoDocFascForm.setValue("nrDocAssegnati",      record.getAttribute("nrDocAssegnati"));
		
		nrDocAssegnatiItem.setValue(record.getAttribute("dataConteggioDocAssegnati"));
		resocontoSpostamentoDocFascForm.setValue("dataConteggioDocAssegnati",      record.getAttribute("dataConteggioDocAssegnati"));
		
		nrFascAssegnatiItem.setValue(record.getAttribute("nrFascAssegnati"));
		resocontoSpostamentoDocFascForm.setValue("nrFascAssegnati",      record.getAttribute("nrFascAssegnati"));
		
		dataConteggioFascAssegnatiItem.setValue(record.getAttribute("dataConteggioFascAssegnati"));
		resocontoSpostamentoDocFascForm.setValue("dataConteggioFascAssegnati",      record.getAttribute("dataConteggioFascAssegnati"));
		
		// Mostra/Nasconde le sezioni spostamento e resoconto 
		Date dataCessazione = record.getAttribute("dtFineVld") != null ? convertStringToDate(record.getAttribute("dtFineVld")) : null;
		
		 // Se la data non e' presente, nascondo le sezioni
		 if (dataCessazione!=null){
			 // Se la data >= data odierna, mostro le sezioni per lo spostamento e nascondo quella del resoconto
			 if (isDataCessazioneValida(dataCessazione)){
				 uoSpostaDocFascSection.setVisible(true);	
				 codRapidoSpostaDocFascItem.setRequired(true);
				 organigrammaSpostaDocFascItem.setRequired(true);
				 resocontoSpostamentoDocFascSection.setVisible(false);
			 }
			 // Se la data < data odierna, nascondo le sezioni per lo spostamento e mostro quella del resoconto
			 if (isDataCessazioneScaduta(dataCessazione)){
				 uoSpostaDocFascSection.setVisible(false);	
				 codRapidoSpostaDocFascItem.setRequired(false);
				 organigrammaSpostaDocFascItem.setRequired(false);
				 resocontoSpostamentoDocFascSection.setVisible(true);
			 }
		 } else {
		     uoSpostaDocFascSection.setVisible(false);	
			 codRapidoSpostaDocFascItem.setRequired(false);
			 organigrammaSpostaDocFascItem.setRequired(false);
			 resocontoSpostamentoDocFascSection.setVisible(false);
		 }
		 
		 setCanEdit(!(mode != null && "view".equalsIgnoreCase(mode)));
		 
     	 // i campi della sezione RESOCONTO devono essere sempre read-only
		 setCanEdit(false, resocontoSpostamentoDocFascForm);	
		 
		 if (flgGestAttiItem.getValueAsBoolean() != null && flgGestAttiItem.getValueAsBoolean()){
			 if(flgVisPropAttiInIterItem.getValueAsBoolean() != null && flgVisPropAttiInIterItem.getValueAsBoolean()){
				 flgVisPropAttiInIterItem.setCanEdit(false);	 
			 }
		 }
	}
	
	private void disegnaForm() {

		formMain = new DynamicForm();
		formMain.setValuesManager(vm);
		formMain.setKeepInParentRect(true);
		formMain.setWidth100();
		formMain.setHeight100();
		formMain.setCellPadding(5);
		formMain.setAlign(Alignment.CENTER);
		formMain.setOverflow(Overflow.VISIBLE);
		formMain.setTop(50);
		formMain.setWrapItemTitles(false);
		formMain.setNumCols(9);
		formMain.setColWidths(180, 1, 1, 1, 1, 1, 1, 1, "*");
		
		// HIDDEN
		idUoSvUtItem                      = new HiddenItem("idUoSvUt");
		ciRelUserUoItem                   = new HiddenItem("ciRelUserUo");
		idScrivaniaItem                   = new HiddenItem("idScrivania");
		idUoItem                          = new HiddenItem("idUo");
		rowIdItem                         = new HiddenItem("idRow");
		typeNodoHiddenItem                = new HiddenItem("typeNodo");
		sostituzioneSVItem                = new HiddenItem("sostituzioneSV");
		dataAlOrigItem                    = new HiddenItem("dataAlOrig");
		descrizioneHiddenItem             = new HiddenItem("descrizione");
		descTipoRelUtenteUoItem           = new HiddenItem("descTipoRelUtenteUo");
		idRuoloItem                       = new HiddenItem("idRuolo");
		descrizioneRuoloItem              = new HiddenItem("descrizioneRuolo");
		flgUoPuntoProtocolloItem          = new HiddenItem("flgUoPuntoProtocollo");
		
		// ottavio
		flgUoAbilRegistrazioneEItem       = new HiddenItem("flgUoAbilRegistrazioneE");
		flgUoAbilRegistrazioneUItem       = new HiddenItem("flgUoAbilRegistrazioneU");
		
		listaUOPuntoProtocolloIncluseItem = new HiddenItem("listaUOPuntoProtocolloIncluse");
		listaUOPuntoProtocolloEscluseItem = new HiddenItem("listaUOPuntoProtocolloEscluse");
		listaUOPuntoProtocolloEreditarietaAbilitataItem = new HiddenItem("listaUOPuntoProtocolloEreditarietaAbilitata");

		// tipo di assegnazione
		GWTRestDataSource tipoRelUOUtenteDS = new GWTRestDataSource("LoadComboTipoRelUtenteUODataSource");
		tipoDiAssegnazioneSelectItem = new SelectItem("tipoRelUtenteUo", I18NUtil.getMessages().organigramma_postazione_detail_tipo_assegnazione()){
			@Override
			public void onOptionClick(Record record) {
				formMain.setValue("descTipoRelUtenteUo", record.getAttributeAsString("value"));
			}
		};
		
		tipoDiAssegnazioneSelectItem.setOptionDataSource(tipoRelUOUtenteDS);
		tipoDiAssegnazioneSelectItem.setValueField("key");
		tipoDiAssegnazioneSelectItem.setDisplayField("value");
		tipoDiAssegnazioneSelectItem.setAttribute("obbligatorio", true);
		tipoDiAssegnazioneSelectItem.setRedrawOnChange(true);
		tipoDiAssegnazioneSelectItem.setWidth(667);
		tipoDiAssegnazioneSelectItem.setColSpan(6);
		tipoDiAssegnazioneSelectItem.setStartRow(true);
		tipoDiAssegnazioneSelectItem.setRequired(true);		
		
		if (isVersioneNestle) 
			tipoDiAssegnazioneSelectItem.setDefaultValue((String) "D");
		
		tipoDiAssegnazioneSelectItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				formMain.setValue("denominazioneScrivaniaUtente", "");denominazioneScrivaniaUtenteItem.setValue("");
				ruoloItem.setValue("");formMain.setValue("ruolo", "");
				formMain.setValue("descrizioneRuolo", "");
				formMain.setValue("idRuolo", "");
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
				
				if(organigrammaDS.extraparam.get("isTipoAssL")!=null) {
					if((organigrammaDS.extraparam.get("isTipoAssL").equals("false") && isTipoDiAssegnazioneL()) || 
							   (organigrammaDS.extraparam.get("isTipoAssL").equals("true") && !isTipoDiAssegnazioneL())
							  ) {
								organigrammaItem.setValue("");
					}	
				}
				
				// ottavio
				//updateFlg(event.getItem().getValue());
				updateFlg();
				
				formMain.markForRedraw();
			}
		});
		tipoDiAssegnazioneSelectItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (!isVersioneNestle);
			}
		});
				
		// UO
		creaSelectOrganigramma();
				
		// data validita (DA) 
		dataDalItem = new DateItem("dataDal", I18NUtil.getMessages().organigramma_postazione_detail_data_dal());
		dataDalItem.setColSpan(1);
		dataDalItem.setWidth(120);
		dataDalItem.setStartRow(true);
		dataDalItem.setRequired(true);

		// data validita (A)
		dataAlItem = new DateItem("dataAl", I18NUtil.getMessages().organigramma_postazione_detail_data_al());
		dataAlItem.setColSpan(1);
		dataAlItem.setWidth(120);
		
		dataAlItem.addChangedHandler(new ChangedHandler() {			
			
			@Override
			public void onChanged(ChangedEvent event) {
				updateSpostaDocFascSection(event.getItem().getValue());
			}
		});	
		
		dataAlItem.addBlurHandler(new BlurHandler() {	
			
			@Override
			public void onBlur(BlurEvent event) {	
				updateSpostaDocFascSection(event.getItem().getValue());
			}
		});
		
		// ruolo
		GWTRestDataSource selezionaRuoloDataSource = new GWTRestDataSource("LoadComboRuoloDataSource");
		ruoloItem = new SelectItem("ruolo", I18NUtil.getMessages().organigramma_postazione_detail_ruolo()){
			
			@Override
			public void onOptionClick(Record record) {
				formMain.setValue("idRuolo", record.getAttributeAsString("key"));
				formMain.setValue("descrizioneRuolo", record.getAttributeAsString("value"));
			}
		};
		ruoloItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (!isTipoDiAssegnazioneL());
			}
		});
		
		ruoloItem.setShowTitle(true);
		ruoloItem.setValueField("key");
		ruoloItem.setDisplayField("value");
		ruoloItem.setOptionDataSource(selezionaRuoloDataSource);
		ruoloItem.setWidth(667);
		ruoloItem.setColSpan(6);
		ruoloItem.setAllowEmptyValue(true);
		ruoloItem.setStartRow(true);
		
		spacer1Item = new SpacerItem();
		spacer1Item.setWidth(20);
		spacer1Item.setColSpan(1);
		spacer1Item.setStartRow(true);
		
		
		
		
		// accesso limitato doc. assegnata personalmente
		flgAccessoDocLimSVItem = new CheckboxItem("flgAccessoDocLimSV", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgAccessoDocLimSVItem_title());
		flgAccessoDocLimSVItem.setValue(false);
		flgAccessoDocLimSVItem.setWidth(10);
		flgAccessoDocLimSVItem.setColSpan(6);

		// delega alle sotto-UO
		flgInclSottoUoItem = new CheckboxItem("flgInclSottoUo", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgIncluseSottoUoItem_title());
		flgInclSottoUoItem.setValue(false);
		flgInclSottoUoItem.setWidth(10);
		flgInclSottoUoItem.setColSpan(1);

		// delega alle postazioni utente
		flgIncluseScrivanieItem = new CheckboxItem("flgIncluseScrivanie", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgIncluseScrivanieItem_title());
		flgIncluseScrivanieItem.setValue(false);
		flgIncluseScrivanieItem.setWidth(10);
		flgIncluseScrivanieItem.setColSpan(1);
		
		// registrazione in entrata 
		flgRegistrazioneEItem = new CheckboxItem("flgRegistrazioneE", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgRegistrazioneEItem_title());
		flgRegistrazioneEItem.setValue(false);
		flgRegistrazioneEItem.setWidth(10);
		flgRegistrazioneEItem.setColSpan(1);
		
		// registrazione in uscita/interna
		flgRegistrazioneUIItem = new CheckboxItem("flgRegistrazioneUI", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgRegistrazioneUIItem_title());
		flgRegistrazioneUIItem.setValue(false);
		flgRegistrazioneUIItem.setWidth(10);
		flgRegistrazioneUIItem.setColSpan(1);
		
		// avvio/gestione atti proposti
		flgGestAttiItem = new CheckboxItem("flgGestAtti", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgGestAttiItem_title());
		flgGestAttiItem.setValue(false);
		flgGestAttiItem.setWidth(10);
		flgGestAttiItem.setColSpan(1);
		
		flgGestAttiItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (flgGestAttiItem.getValueAsBoolean() != null && flgGestAttiItem.getValueAsBoolean()){
					
					// setto il flag flgVisPropAttiInIterItem e lo disabilito
					flgVisPropAttiInIterItem.setValue(true);
					formMain.setValue("flgVisPropAttiInIter", true);
					flgVisPropAttiInIterItem.setCanEdit(false);
					
					if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO") && flgGestAttiTuttiItem != null && flgVisPropAttiInIterTuttiItem != null) {
						flgGestAttiTuttiItem.show();
						flgGestAttiTuttiItem.setValue(true);
						flgVisPropAttiInIterTuttiItem.show();
						flgVisPropAttiInIterTuttiItem.setValue(true);
						listaTipiGestAttiSelezionatiItem.clearValue();
						selezionaTipiGestAttiButton.hide();
						listaTipiVisPropAttiInIterSelezionatiItem.clearValue();
						selezionaTipiVisPropAttiInIterButton.hide();
					}
					
				} else {
					// Abilito il flag flgVisPropAttiInIterItem
					flgVisPropAttiInIterItem.setCanEdit(true);
					if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO") && flgGestAttiTuttiItem != null && flgVisPropAttiInIterTuttiItem != null) {
						flgGestAttiTuttiItem.clearValue();
						flgGestAttiTuttiItem.hide();
						selezionaTipiGestAttiButton.hide();
						listaTipiGestAttiSelezionatiItem.clearValue();
						
					}
					
				}
				markForRedraw();
			}
		});
		
		flgGestAttiTuttiItem = new CheckboxItem("flgGestAttiTutti", "tutti i tipi di atti");
		flgGestAttiTuttiItem.setValue(false);
		flgGestAttiTuttiItem.setWidth(10);
//		flgGestAttiTuttiItem.setColSpan(2);
		flgGestAttiTuttiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO") && (flgGestAttiItem.getValueAsBoolean() != null && flgGestAttiItem.getValueAsBoolean());
			}
		});
		flgGestAttiTuttiItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (flgGestAttiTuttiItem.getValueAsBoolean() != null && !flgGestAttiTuttiItem.getValueAsBoolean()){
					selezionaTipiGestAttiButton.show();
				} else {
					listaTipiGestAttiSelezionatiItem.clearValue();
					selezionaTipiGestAttiButton.hide();
				}
				markForRedraw();
			}
		});
		
		selezionaTipiGestAttiButton = new ImgButtonItem("selezionaTipiGestAtti", "buttons/altriDati.png",	"Seleziona tipi di atti");
		selezionaTipiGestAttiButton.setAlwaysEnabled(true);
//		selezionaGestAttiButton.setColSpan(1);
		selezionaTipiGestAttiButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgGestAttiTuttiItem != null && !flgGestAttiTuttiItem.getValueAsBoolean() && flgGestAttiItem.getValueAsBoolean();
			}
		});
		selezionaTipiGestAttiButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = new Record();
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGestioneUtentiDataSource");
				lGwtRestDataSource.executecustom("leggiTipologieAtti", record, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS){
							if (response.getData() != null) {
								Record lRecordDb    = response.getData()[0];
								RecordList listaSelezionaTipiAtto  = lRecordDb.getAttributeAsRecordList("listaSelezionaTipiAtto");
								Record recordNew = new Record();
								recordNew.setAttribute("listaSelezionaTipiAtto", listaSelezionaTipiAtto);
								recordNew.setAttribute("listaTipiGestAttiSelezionati", listaTipiGestAttiSelezionatiItem.getValue());
								recordNew.setAttribute("tipoLista", "tipiGestAtti");
								String mode = getMode();	
								String title = "Tipi di atti abilitati";
								SelezionaTipiDiAttoPopup selezionaTipiDiAttoPopup = new SelezionaTipiDiAttoPopup(recordNew, title, mode) {
									
									@Override
									public void onClickOkButton(Record formRecord, DSCallback callback) {
										Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");				
										Layout.hideWaitPopup();
										formMain.setValue("listaTipiGestAttiSelezionati", formRecord.getAttribute("listaTipiGestAttiSelezionati"));
										markForDestroy();
									}
								};
								selezionaTipiDiAttoPopup.show();						
							}
						}
					}						
				});
			}
		});
		
		listaTipiGestAttiSelezionatiItem = new HiddenItem("listaTipiGestAttiSelezionati");
		
		// visualizzazione atti proposti
		flgVisPropAttiInIterItem = new CheckboxItem("flgVisPropAttiInIter", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgVisPropAttiInIterItem_title());
		flgVisPropAttiInIterItem.setValue(false);
		flgVisPropAttiInIterItem.setWidth(10);
		flgVisPropAttiInIterItem.setColSpan(1);
		flgVisPropAttiInIterItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {

				if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO")) {
					if (flgVisPropAttiInIterItem.getValueAsBoolean() != null && flgVisPropAttiInIterItem.getValueAsBoolean()) {
						flgVisPropAttiInIterTuttiItem.show();
						flgVisPropAttiInIterTuttiItem.setValue(true);
						listaTipiVisPropAttiInIterSelezionatiItem.clearValue();
						selezionaTipiVisPropAttiInIterButton.hide();
					} else {
						flgVisPropAttiInIterTuttiItem.clearValue();
						flgVisPropAttiInIterTuttiItem.hide();
						selezionaTipiVisPropAttiInIterButton.hide();
					}
				}
				markForRedraw();
			}
		});
		
		flgVisPropAttiInIterTuttiItem = new CheckboxItem("flgVisPropAttiInIterTutti", "tutti i tipi di atti");
		flgVisPropAttiInIterTuttiItem.setValue(false);
		flgVisPropAttiInIterTuttiItem.setWidth(10);
//		flgVisPropAttiInIterTuttiItem.setColSpan(2);
		flgVisPropAttiInIterTuttiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO") && (flgVisPropAttiInIterItem.getValueAsBoolean() != null && flgVisPropAttiInIterItem.getValueAsBoolean());
			}
		});
		flgVisPropAttiInIterTuttiItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (flgVisPropAttiInIterTuttiItem.getValueAsBoolean() != null && !flgVisPropAttiInIterTuttiItem.getValueAsBoolean()){
					selezionaTipiVisPropAttiInIterButton.show();
				} else {
					listaTipiVisPropAttiInIterSelezionatiItem.clearValue();
					selezionaTipiVisPropAttiInIterButton.hide();
				}
				markForRedraw();
			}
		});
		
		selezionaTipiVisPropAttiInIterButton = new ImgButtonItem("selezionaTipiVisPropAttiInIter", "buttons/altriDati.png",	"Seleziona tipi di atti");
		selezionaTipiVisPropAttiInIterButton.setAlwaysEnabled(true);
//		selezionaVisPropAttiInIterButton.setColSpan(1);
		selezionaTipiVisPropAttiInIterButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgVisPropAttiInIterTuttiItem != null && !flgVisPropAttiInIterTuttiItem.getValueAsBoolean() && flgVisPropAttiInIterItem.getValueAsBoolean();
			}
		});
		selezionaTipiVisPropAttiInIterButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = new Record();
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGestioneUtentiDataSource");
				lGwtRestDataSource.executecustom("leggiTipologieAtti", record, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS){
							if (response.getData() != null) {
								Record lRecordDb    = response.getData()[0];
								RecordList listaSelezionaTipiAtto  = lRecordDb.getAttributeAsRecordList("listaSelezionaTipiAtto");
								Record recordNew = new Record();
								recordNew.setAttribute("listaSelezionaTipiAtto", listaSelezionaTipiAtto);
								recordNew.setAttribute("listaTipiVisPropAttiInIterSelezionati", listaTipiVisPropAttiInIterSelezionatiItem.getValue());
								recordNew.setAttribute("tipoLista", "tipiVisPropAttiInIter");
								String mode = getMode();	
								String title = "Tipi di atti abilitati";
								SelezionaTipiDiAttoPopup selezionaTipiDiAttoPopup = new SelezionaTipiDiAttoPopup(recordNew, title, mode) {
									
									@Override
									public void onClickOkButton(Record formRecord, DSCallback callback) {
										Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");				
										Layout.hideWaitPopup();
										formMain.setValue("listaTipiVisPropAttiInIterSelezionati", formRecord.getAttribute("listaTipiVisPropAttiInIterSelezionati"));
										markForDestroy();
									}
								};
								selezionaTipiDiAttoPopup.show();						
							}
						}
					}						
				});
			}
		});
		
		listaTipiVisPropAttiInIterSelezionatiItem = new HiddenItem("listaTipiVisPropAttiInIterSelezionati");
		
		// Abilitazione alla documentazione riservata assegnata alla struttura
		flgRiservatezzaRelUserUoItem = new CheckboxItem("flgRiservatezzaRelUserUo", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgRiservatezzaRelUserUoItem_title());
		flgRiservatezzaRelUserUoItem.setValue(false);
		flgRiservatezzaRelUserUoItem.setWidth(10);
		flgRiservatezzaRelUserUoItem.setColSpan(6);
		
		// Se il parametro DB ATTIVA_GEST_RISERVATEZZA_REL_USER_UO = true
		flgRiservatezzaRelUserUoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_RISERVATEZZA_REL_USER_UO"));
			}
		});

		// Se il parametro DB ATTIVA_GEST_RISERVATEZZA_REL_USER_UO = true
		spacerFlgRiservatezzaRelUserUoItem = new SpacerItem();
		spacerFlgRiservatezzaRelUserUoItem.setWidth(20);
		spacerFlgRiservatezzaRelUserUoItem.setColSpan(1);
		spacerFlgRiservatezzaRelUserUoItem.setStartRow(true);
		spacerFlgRiservatezzaRelUserUoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_RISERVATEZZA_REL_USER_UO"));
			}
		});

		
		// nome postazione
		denominazioneScrivaniaUtenteItem = new TextItem("denominazioneScrivaniaUtente", "Nome postazione") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setHint(canEdit ? "Da valorizzare se diverso da cognome e nome dell'utente" : null);
				setShowHintInField(true);
			}
		};
		denominazioneScrivaniaUtenteItem.setStartRow(true);
		denominazioneScrivaniaUtenteItem.setColSpan(6);
		denominazioneScrivaniaUtenteItem.setWidth(667);
		denominazioneScrivaniaUtenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (!isTipoDiAssegnazioneL());
			}
		});
		
		//Bottone per vedere Abilitazioni vs UO collegate al punto di protocollo 
		uoCollegatePuntoProtocolloButton = new ImgButtonItem("uoCollegatePuntoProtocollo", "buttons/uoCollegatePuntoProtocollo.png", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_uoCollegatePuntoProtocolloButton_title());
		uoCollegatePuntoProtocolloButton.setAlwaysEnabled(true);
		uoCollegatePuntoProtocolloButton.setColSpan(1);
		uoCollegatePuntoProtocolloButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idUOPP = formMain.getValueAsString("idUo");
				String listaUOPuntoProtocolloEscluse = formMain.getValueAsString("listaUOPuntoProtocolloEscluse");
				String listaUOPuntoProtocolloEreditarietaAbilitata = formMain.getValueAsString("listaUOPuntoProtocolloEreditarietaAbilitata");
				Record record = new Record();
				record.setAttribute("idUOPP", idUOPP);
				record.setAttribute("listaUOPuntoProtocolloEscluse", listaUOPuntoProtocolloEscluse);
				record.setAttribute("listaUOPuntoProtocolloEreditarietaAbilitata", listaUOPuntoProtocolloEreditarietaAbilitata);
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGestioneUtentiDataSource");
				lGwtRestDataSource.executecustom("leggiUOCollegatePuntoProtocollo", record, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS){
							if (response.getData() != null) {
								Record lRecordDb    = response.getData()[0];
								RecordList listaUOCollegatePuntoProtocollo  = lRecordDb.getAttributeAsRecordList("listaUOCollegatePuntoProtocollo");
								Record recordNew = new Record();
								recordNew.setAttribute("listaUOCollegatePuntoProtocollo", listaUOCollegatePuntoProtocollo);
								final String denominazioneUo = formMain.getValueAsString("organigramma");
								final String codRapido = formMain.getValueAsString("codRapido");								
								String title = "Abilitazioni vs UO collegate al punto di protocollo " + codRapido + "-" + denominazioneUo;								
								String mode = getMode();								
								UOCollegatePuntoProtocolloPopup uoCollegatePuntoProtocolloPopup = new UOCollegatePuntoProtocolloPopup(recordNew, title, mode) {
									
									@Override
									public void onClickOkButton(Record formRecord, DSCallback callback) {
										Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");				
										Layout.hideWaitPopup();
										setFormValuesFromRecordAfterAbilUOPuntoProtocollo(formRecord);
										markForDestroy();
									}
								};
								uoCollegatePuntoProtocolloPopup.show();						
							}
						}
					}						
				});				
			}
		});
		
		uoCollegatePuntoProtocolloButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAbilToModUoCollegatePuntoProtocollo();
			}
		});
		
		// Sezione doc/fasc assegnati
		creaSelectOrganigrammaSpostaDocFasc();

		// Sezione Resoconto documentazione in competenza alla UO
		creaResocontoSpostamentoSection();
		
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO")) {
			formMain.setFields( // visibili
		            tipoDiAssegnazioneSelectItem,            
		            tipoItem, 
		            codRapidoItem, 
		            gruppoItem, 
		            lookupOrganigrammaButton, 
		            organigrammaItem,				       
		            dataDalItem, 
		            dataAlItem, 
		            ruoloItem, 				            
		            spacer1Item,flgAccessoDocLimSVItem,
		            spacer1Item,flgInclSottoUoItem,    flgIncluseScrivanieItem,
		            spacer1Item,flgRegistrazioneEItem, flgRegistrazioneUIItem,
		            spacer1Item,flgGestAttiItem,       flgGestAttiTuttiItem, selezionaTipiGestAttiButton, listaTipiGestAttiSelezionatiItem,
		            spacer1Item, flgVisPropAttiInIterItem, flgVisPropAttiInIterTuttiItem, selezionaTipiVisPropAttiInIterButton, listaTipiVisPropAttiInIterSelezionatiItem,
		            spacerFlgRiservatezzaRelUserUoItem,flgRiservatezzaRelUserUoItem,				            
		            denominazioneScrivaniaUtenteItem,	
		            spacer1Item, uoCollegatePuntoProtocolloButton,				            
		            
		            // Hidden
				    idUoSvUtItem, 
				    ciRelUserUoItem, 
				    idScrivaniaItem, 
				    idUoItem, 
				    rowIdItem,
				    sostituzioneSVItem, 
				    dataAlOrigItem,
				    descrizioneHiddenItem,
					typeNodoHiddenItem,
					descTipoRelUtenteUoItem,
					idRuoloItem,
			        descrizioneRuoloItem,
			        flgUoPuntoProtocolloItem,
			        
			        // ottavio
			        flgUoAbilRegistrazioneEItem,
			        flgUoAbilRegistrazioneUItem,
			        listaUOPuntoProtocolloIncluseItem,
		            listaUOPuntoProtocolloEscluseItem,
		            listaUOPuntoProtocolloEreditarietaAbilitataItem
		           );	
		} else {
			formMain.setFields( // visibili
		            tipoDiAssegnazioneSelectItem,            
		            tipoItem, 
		            codRapidoItem, 
		            gruppoItem, 
		            lookupOrganigrammaButton, 
		            organigrammaItem,				       
		            dataDalItem, 
		            dataAlItem, 
		            ruoloItem, 				            
		            spacer1Item,flgAccessoDocLimSVItem,
		            spacer1Item,flgInclSottoUoItem,    flgIncluseScrivanieItem,
		            spacer1Item,flgRegistrazioneEItem, flgRegistrazioneUIItem,
		            spacer1Item,flgGestAttiItem,       flgVisPropAttiInIterItem,
		            spacerFlgRiservatezzaRelUserUoItem,flgRiservatezzaRelUserUoItem,				            
		            denominazioneScrivaniaUtenteItem,	
		            spacer1Item, uoCollegatePuntoProtocolloButton,				            
		            
		            // Hidden
				    idUoSvUtItem, 
				    ciRelUserUoItem, 
				    idScrivaniaItem, 
				    idUoItem, 
				    rowIdItem,
				    sostituzioneSVItem, 
				    dataAlOrigItem,
				    descrizioneHiddenItem,
					typeNodoHiddenItem,
					descTipoRelUtenteUoItem,
					idRuoloItem,
			        descrizioneRuoloItem,
			        flgUoPuntoProtocolloItem,
			        
			        // ottavio
			        flgUoAbilRegistrazioneEItem,
			        flgUoAbilRegistrazioneUItem,
			        listaUOPuntoProtocolloIncluseItem,
		            listaUOPuntoProtocolloEscluseItem,
		            listaUOPuntoProtocolloEreditarietaAbilitataItem
		           );	
		}
		
		
		formMainSection = new DetailSection(I18NUtil.getMessages().agganciautenteuopopup_uoAssociateUtenteSection_title(), true, true, false, formMain);
	}

	private void creaSelectOrganigramma() {
		
			boolean flgSenzaLD = true;
			String tipoAssegnatari = "UO";
			
			// tipo
			tipoItem = new SelectItem("tipo");
			LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
			if (tipoAssegnatari != null && "SV".equals(tipoAssegnatari)) {
				styleMap.put("SV;UO", "Unita'  di personale");
			} else if (tipoAssegnatari != null && "UO".equals(tipoAssegnatari)) {
				styleMap.put("SV;UO", "U.O.");
			} else {
				styleMap.put("SV;UO", "Unita'  di personale/U.O.");
			}
			if (!flgSenzaLD) {
				styleMap.put("LD", "Liste di distribuzione");
			}
			
			tipoItem.setDefaultValue("SV;UO");
			tipoItem.setShowTitle(false);
			tipoItem.setValueMap(styleMap);
			tipoItem.setWidth(150);
			tipoItem.setStartRow(true);
			tipoItem.setVisible(false);
			
			tipoItem.addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					formMain.clearErrors(true);
					Record lRecord = formMain.getValuesAsRecord();
					lRecord.setAttribute("organigramma", "");
					lRecord.setAttribute("idUo", "");
					if ("LD".equals(event.getValue())) {
						lRecord.setAttribute("typeNodo", "G");
					} else {
						lRecord.setAttribute("typeNodo", "");
					}
					lRecord.setAttribute("codRapido", "");
					formMain.setValues(lRecord.toMap());
				}
			});
			
			codRapidoItem = new ExtendedTextItem("codRapido", I18NUtil.getMessages().protocollazione_detail_uoItem_title());
			codRapidoItem.setWidth(120);
			codRapidoItem.setColSpan(1);
			codRapidoItem.setRequired(true);		
			codRapidoItem.setStartRow(true);
			codRapidoItem.addChangedBlurHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					formMain.setValue("idUo", (String) null);
					formMain.setValue("descrizione", (String) null);
					formMain.setValue("organigramma", (String) null);
					formMain.setValue("typeNodo", (String) null);
					formMain.setValue("gruppo", (String) null);
					formMain.setValue("listaUOPuntoProtocolloEscluse", (String) null);
					formMain.setValue("listaUOPuntoProtocolloIncluse", (String) null);
					formMain.setValue("listaUOPuntoProtocolloEreditarietaAbilitata", (String) null);
					formMain.clearErrors(true);
					final String value = codRapidoItem.getValueAsString();
					if (tipoItem.getValueAsString().equalsIgnoreCase("LD")) {
						cercaSoggettoLD();
					} else {
						
						GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
						if(isTipoDiAssegnazioneL()) {
							organigrammaDS.addParam("flgSoloValide", "0");
							organigrammaDS.addParam("isTipoAssL", "true");
						} else {
							organigrammaDS.addParam("flgSoloValide", "1");
							organigrammaDS.addParam("isTipoAssL", "false");
						}
						
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
												formMain.setValue("descrizione", data.get(i).getAttribute("descrizioneOrig"));
												formMain.setValue("organigramma", data.get(i).getAttribute("id"));
												formMain.setValue("idUo", data.get(i).getAttribute("idUo"));
												formMain.setValue("typeNodo", data.get(i).getAttribute("typeNodo"));
												formMain.setValue("flgUoPuntoProtocollo", data.get(i).getAttribute("flgPuntoProtocollo"));
												
												
												// ottavio
												formMain.setValue("flgUoAbilRegistrazioneE", data.get(i).getAttribute("flgUoAbilRegistrazioneE"));
												formMain.setValue("flgUoAbilRegistrazioneU", data.get(i).getAttribute("flgUoAbilRegistrazioneU"));
												
												// ottavio
												//updateFlg(tipoDiAssegnazioneSelectItem.getValueAsString());
												updateFlg();
												formMain.markForRedraw();
												
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
				}
			});
			codRapidoItem.setValidators(new CustomValidator() {

				@Override
				protected boolean condition(Object value) {
					if (codRapidoItem.getValue() != null && !"".equals(codRapidoItem.getValueAsString().trim()) && organigrammaItem.getValue() == null
							&& gruppoItem.getValue() == null) {
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
					formMain.setValue("typeNodo", record.getAttributeAsString("typeNodo"));
					formMain.setValue("idUo", record.getAttributeAsString("idUo"));
					formMain.setValue("descrizione", record.getAttributeAsString("descrizioneOrig"));
					formMain.setValue("flgUoPuntoProtocollo", record.getAttributeAsString("flgPuntoProtocollo"));
					
					// ottavio
					formMain.setValue("flgUoAbilRegistrazioneE", record.getAttributeAsString("flgUoAbilRegistrazioneE"));
					formMain.setValue("flgUoAbilRegistrazioneU", record.getAttributeAsString("flgUoAbilRegistrazioneU"));
					
					formMain.setValue("listaUOPuntoProtocolloEscluse", "");
					formMain.setValue("listaUOPuntoProtocolloIncluse", "");
					formMain.setValue("listaUOPuntoProtocolloEreditarietaAbilitata", "");
					formMain.clearErrors(true);
					
					// ottavio
					updateFlg();
					formMain.markForRedraw();

					
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
					formMain.setValue("typeNodo", "");
					formMain.setValue("descrizione", "");
					formMain.setValue("flgUoPuntoProtocollo", "");
					formMain.setValue("listaUOPuntoProtocolloEscluse", "");
					formMain.setValue("listaUOPuntoProtocolloIncluse", "");
					formMain.setValue("listaUOPuntoProtocolloEreditarietaAbilitata", "");
					
					// ottavio
					formMain.setValue("flgUoAbilRegistrazioneE", "");
					formMain.setValue("flgUoAbilRegistrazioneU", "");
					
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
						formMain.setValue("typeNodo", "");
						formMain.setValue("descrizione", "");
						formMain.setValue("flgUoPuntoProtocollo", "");
						formMain.setValue("listaUOPuntoProtocolloEscluse", "");
						formMain.setValue("listaUOPuntoProtocolloIncluse", "");
						formMain.setValue("listaUOPuntoProtocolloEreditarietaAbilitata", "");
						
						// ottavio
						formMain.setValue("flgUoAbilRegistrazioneE", "");
						formMain.setValue("flgUoAbilRegistrazioneU", "");
						
						
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
			organigrammaItem.setWidth(450);
			organigrammaItem.setClearable(true);
			organigrammaItem.setShowIcons(true);
			organigrammaItem.setRequired(true);
			organigrammaItem.setColSpan(5);
			organigrammaItem.setItemHoverFormatter(new FormItemHoverFormatter() {

				@Override
				public String getHoverHTML(FormItem item, DynamicForm form) {
					return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
				}
			});
			organigrammaItem.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return !"LD".equalsIgnoreCase(tipoItem.getValueAsString());
				}
			});
			organigrammaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

				@Override
				public boolean execute(FormItem formItem, Object value) {
					return !"LD".equalsIgnoreCase(tipoItem.getValueAsString());
				}
			}));
			ListGrid pickListProperties = organigrammaItem.getPickListProperties();
			pickListProperties.addFetchDataHandler(new FetchDataHandler() {

				@Override
				public void onFilterData(FetchDataEvent event) {
					String codRapido = formMain.getValueAsString("codRapido");
					GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
					// organigrammaDS.addParam("ciToAdd", mDynamicForm.getValueAsString("organigramma"));
					organigrammaDS.addParam("codice", codRapido);
					organigrammaDS.addParam("finalita", "ALTRO");
					organigrammaDS.addParam("idUd", getIdUdProtocollo());
					
					if(isTipoDiAssegnazioneL()) {
						organigrammaDS.addParam("flgSoloValide", "0");
						organigrammaDS.addParam("isTipoAssL", "true");
					} else {
						organigrammaDS.addParam("flgSoloValide", "1");
						organigrammaDS.addParam("isTipoAssL", "false");
					}
					
					organigrammaItem.setOptionDataSource(organigrammaDS);
					organigrammaItem.invalidateDisplayValueCache();
				}
			});
			organigrammaItem.setPickListProperties(pickListProperties);

			lookupOrganigrammaButton = new ImgButtonItem("lookupOrganigrammaButton", "lookup/organigramma.png", I18NUtil.getMessages().protocollazione_detail_lookupOrganigrammaButton_prompt());
			lookupOrganigrammaButton.setColSpan(1);
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
					return !"LD".equalsIgnoreCase(tipoItem.getValueAsString());
				}
			});

			// gruppi
			SelectGWTRestDataSource lGwtRestDataSourceGruppo = new SelectGWTRestDataSource("LoadComboGruppoSoggettiDataSource", "idGruppo", FieldType.TEXT, new String[] { "nomeGruppo" }, true);
			lGwtRestDataSourceGruppo.extraparam.put("usaFlagSoggettiInterni", "S");
			lGwtRestDataSourceGruppo.extraparam.put("flgSoloGruppiSoggInt", "2");
			gruppoItem = new FilteredSelectItemWithDisplay("gruppo", lGwtRestDataSourceGruppo) {

				@Override
				public void onOptionClick(Record record) {
					super.onOptionClick(record);
					formMain.setValue("codRapido", record.getAttributeAsString("codiceRapidoGruppo"));
					formMain.setValue("descrizione", record.getAttributeAsString("nomeGruppo"));
				}

				@Override
				protected void clearSelect() {
					super.clearSelect();
					formMain.setValue("gruppo", "");
					formMain.setValue("codRapido", "");
					formMain.setValue("descrizione", "");
				}		
				
			};

			gruppoItem.setAutoFetchData(false);
			gruppoItem.setFetchMissingValues(true);

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

			gruppoItem.setPickListFields(codiceRapidoGruppoField, nomeGruppoField, flagSoggettiGruppoField);
			gruppoItem.setFilterLocally(true);
			gruppoItem.setValueField("idGruppo");
			gruppoItem.setOptionDataSource(lGwtRestDataSourceGruppo);
			gruppoItem.setShowTitle(false);
			gruppoItem.setWidth(400);
			gruppoItem.setClearable(true);
			gruppoItem.setShowIcons(true);
			gruppoItem.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return "LD".equalsIgnoreCase(tipoItem.getValueAsString());
				}
			});
			gruppoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

				@Override
				public boolean execute(FormItem formItem, Object value) {
					return "LD".equalsIgnoreCase(tipoItem.getValueAsString());
				}
			}));
		}
		
		protected void cercaSoggettoLD() {
			formMain.setValue("gruppo", (String) null);
			formMain.clearErrors(true);
			final String value = codRapidoItem.getValueAsString().toUpperCase();
			if (value != null && !"".equals(value)) {
				SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboGruppoSoggettiDataSource", "idGruppo", FieldType.TEXT,
						new String[] { "codiceRapidoGruppo", "nomeGruppo" }, true);
				lGwtRestDataSource.extraparam.put("usaFlagSoggettiInterni", "S");
				lGwtRestDataSource.extraparam.put("flgSoloGruppiSoggInt", "2");
				lGwtRestDataSource.extraparam.put("codiceRapidoGruppo", value);
				lGwtRestDataSource.fetchData(null, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						RecordList data = response.getDataAsRecordList();
						boolean trovato = false;
						if (data.getLength() > 0) {
							for (int i = 0; i < data.getLength(); i++) {
								String codiceRapido = data.get(i).getAttribute("codiceRapidoGruppo");
								if (value.equalsIgnoreCase(codiceRapido)) {
									formMain.setValue("codRapido", data.get(i).getAttribute("codiceRapidoGruppo"));
									formMain.setValue("gruppo", data.get(i).getAttribute("idGruppo"));
									formMain.setValue("descrizione", data.get(i).getAttribute("nomeGruppo"));
									trovato = true;
									break;
								}
							}
						}
						if (!trovato) {
							formMain.setFieldErrors("codRapido", "Gruppo inesistente");
						}
					}
				});
			}
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
				return !isTipoDiAssegnazioneL();
			}
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
			formMain.setValue("organigramma", tipo + idOrganigramma);
			formMain.setValue("idUo", idOrganigramma);
			formMain.setValue("typeNodo", tipo);
			// mDynamicForm.setValue("descrizione", ""); // da settare
			formMain.setValue("codRapido", record.getAttribute("codRapidoUo"));
			formMain.setValue("descrizione", record.getAttribute("nome"));
			formMain.setValue("descrizioneEstesa", record.getAttribute("denominazioneEstesa"));
						
			// ottavio
			formMain.setValue("flgUoAbilRegistrazioneE", record.getAttributeAsString("abilitaUoProtEntrata"));
			formMain.setValue("flgUoAbilRegistrazioneU", record.getAttributeAsString("abilitaUoProtUscita"));
			
			formMain.clearErrors(true);
			
			// ottavio
			updateFlg();
			formMain.markForRedraw();					
			
			
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
		
		public Integer getFlgIncludiUtentiSpostaDocFasc() {
			Integer flgIncludiUtenti = new Integer(1);
			String tipoAssegnatariSpostaDocFasc = getTipoAssegnatariSpostaDocFasc();
			if(tipoAssegnatariSpostaDocFasc != null) {
				if("UO".equals(tipoAssegnatariSpostaDocFasc)) {
					flgIncludiUtenti = new Integer(0);
				} else if("SV".equals(tipoAssegnatariSpostaDocFasc)) {
					flgIncludiUtenti = new Integer(2);				 
				} 
			}
			return flgIncludiUtenti;
		}

		public void setTipoAssegnatari(String tipoAssegnatari) {
			this.tipoAssegnatari = tipoAssegnatari;
		}
		
		public String getTipoAssegnatari() {
			return tipoAssegnatari;
		}
	
		public void setTipoAssegnatariSpostaDocFasc(String tipoAssegnatariSpostaDocFasc) {
			this.tipoAssegnatariSpostaDocFasc = tipoAssegnatariSpostaDocFasc;
		}
		
		public String getTipoAssegnatariSpostaDocFasc() {
			return tipoAssegnatariSpostaDocFasc;
		}
		
		public abstract void onClickOkButton(Record formRecord, DSCallback callback);
		
		public void setFormValuesFromRecordAfterAbilUOPuntoProtocollo(Record record) {
			formMain.setValue("listaUOPuntoProtocolloIncluse", record.getAttribute("listaUOPuntoProtocolloIncluse"));
			formMain.setValue("listaUOPuntoProtocolloEscluse", record.getAttribute("listaUOPuntoProtocolloEscluse"));
			formMain.setValue("listaUOPuntoProtocolloEreditarietaAbilitata", record.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata"));
		}

		public boolean isAbilToModUoCollegatePuntoProtocollo() {
			
			return (flgUoPuntoProtocolloItem.getValue()!=null && flgUoPuntoProtocolloItem.getValue().equals("true"));
		}

		
		
		
		public String getMode() {
			return mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}
				
		private boolean isTipoDiAssegnazioneL(){
			return (tipoDiAssegnazioneSelectItem.getValue()!=null && tipoDiAssegnazioneSelectItem.getValueAsString().equals("L"));
		}

		private void creaSelectOrganigrammaSpostaDocFasc() {

			String tipoAssegnatari = "UO;SV";
			
			uoSpostaDocFascForm = new DynamicForm();
			uoSpostaDocFascForm.setValuesManager(vm);
			uoSpostaDocFascForm.setKeepInParentRect(true);
			uoSpostaDocFascForm.setWidth100();
			uoSpostaDocFascForm.setHeight100();
			uoSpostaDocFascForm.setCellPadding(5);
			uoSpostaDocFascForm.setAlign(Alignment.CENTER);
			uoSpostaDocFascForm.setOverflow(Overflow.VISIBLE);
			uoSpostaDocFascForm.setTop(50);
			uoSpostaDocFascForm.setWrapItemTitles(false);		
			uoSpostaDocFascForm.setNumCols(6);
			uoSpostaDocFascForm.setColWidths(1, 1, 1, 1, "*", "*");
		
			
			
			flgPresentiDocFascItem                = new HiddenItem("flgPresentiDocFasc");
			typeNodoSpostaDocFascItem             = new HiddenItem("typeNodoSpostaDocFasc");
			idUoSpostaDocFascItem                 = new HiddenItem("idUoSpostaDocFasc");
			descrizioneSpostaDocFascItem          = new HiddenItem("descrizioneSpostaDocFasc");
			
			// tipo
			tipoSpostaDocFascItem = new SelectItem("tipoSpostaDocFasc");
			LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
			styleMap.put("SV;UO", "Unita'  di personale/U.O.");
			tipoSpostaDocFascItem.setDefaultValue("SV;UO");
			tipoSpostaDocFascItem.setShowTitle(false);
			tipoSpostaDocFascItem.setValueMap(styleMap);
			tipoSpostaDocFascItem.setWidth(150);
			tipoSpostaDocFascItem.setStartRow(true);
			tipoSpostaDocFascItem.setVisible(false);
			
			tipoSpostaDocFascItem.addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					uoSpostaDocFascForm.clearErrors(true);
					Record lRecord = uoSpostaDocFascForm.getValuesAsRecord();
					lRecord.setAttribute("organigrammaSpostaDocFasc", "");
					lRecord.setAttribute("idUoSpostaDocFasc", "");
					lRecord.setAttribute("typeNodoSpostaDocFasc", "");
					lRecord.setAttribute("codRapidoSpostaDocFasc", "");
					uoSpostaDocFascForm.setValues(lRecord.toMap());
				}
			});
			
			codRapidoSpostaDocFascItem = new ExtendedTextItem("codRapidoSpostaDocFasc", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
			codRapidoSpostaDocFascItem.setWidth(120);
			codRapidoSpostaDocFascItem.setColSpan(1);
			codRapidoSpostaDocFascItem.setStartRow(true);
			codRapidoSpostaDocFascItem.addChangedBlurHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					uoSpostaDocFascForm.setValue("idUoSpostaDocFasc", (String) null);
					uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", (String) null);
					uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", (String) null);
					uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", (String) null);
					uoSpostaDocFascForm.setValue("gruppoSpostaDocFasc", (String) null);
					uoSpostaDocFascForm.clearErrors(true);
					final String value = codRapidoSpostaDocFascItem.getValueAsString();
					{
						GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaSpostaDocFascItem.getOptionDataSource();
						organigrammaDS.addParam("flgSoloValide", "1");
						organigrammaSpostaDocFascItem.setOptionDataSource(organigrammaDS);
						if (value != null && !"".equals(value)) {
							organigrammaSpostaDocFascItem.fetchData(new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									RecordList data = response.getDataAsRecordList();
									boolean trovato = false;
									if (data.getLength() > 0) {
										for (int i = 0; i < data.getLength(); i++) {
											String codice = data.get(i).getAttribute("codice");
											String flgSelXFinalita = data.get(i).getAttribute("flgSelXFinalita");
											if (value.equals(codice) && (flgSelXFinalita == null || "1".equals(flgSelXFinalita))) {
												uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", data.get(i).getAttribute("descrizioneOrig"));
												uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", data.get(i).getAttribute("id"));
												uoSpostaDocFascForm.setValue("idUoSpostaDocFasc", data.get(i).getAttribute("idUo"));
												uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", data.get(i).getAttribute("typeNodo"));
												uoSpostaDocFascForm.clearErrors(true);
												trovato = true;
												break;
											}
										}
									}
									if (!trovato) {
										codRapidoSpostaDocFascItem.validate();
										codRapidoSpostaDocFascItem.blurItem();
									}
								}
							});
						} else {
							organigrammaSpostaDocFascItem.fetchData();
						}
					}
				}
			});
			codRapidoSpostaDocFascItem.setValidators(new CustomValidator() {

				@Override
				protected boolean condition(Object value) {
					if (codRapidoSpostaDocFascItem.getValue() != null && 
						!"".equals(codRapidoSpostaDocFascItem.getValueAsString().trim()) && 
						organigrammaSpostaDocFascItem.getValue() == null						
						) {
						return false;
					}
					return true;
				}
			});
			
			SelectGWTRestDataSource lGwtRestDataSourceOrganigramma = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT, new String[] { "descrizione" }, true);
			lGwtRestDataSourceOrganigramma.addParam("tipoAssegnatari", tipoAssegnatari);
			lGwtRestDataSourceOrganigramma.addParam("finalita", "ALTRO");

			organigrammaSpostaDocFascItem = new FilteredSelectItemWithDisplay("organigrammaSpostaDocFasc", lGwtRestDataSourceOrganigramma) {

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
					uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc",            record.getAttributeAsString("codice"));
					uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc",             record.getAttributeAsString("typeNodo"));
					uoSpostaDocFascForm.setValue("idUoSpostaDocFasc",                 record.getAttributeAsString("idUo"));
					uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc",          record.getAttributeAsString("descrizioneOrig"));		
					uoSpostaDocFascForm.clearErrors(true);
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						@Override
						public void execute() {
							organigrammaSpostaDocFascItem.fetchData();
						}
					});
				}

				@Override
				protected void clearSelect() {
					super.clearSelect();
					uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", "");
					uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", "");
					if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
						uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", "");
					}
					uoSpostaDocFascForm.setValue("idUoSpostaDocFasc", "");
					uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", "");
					uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", "");
					uoSpostaDocFascForm.clearErrors(true);
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						
						@Override
						public void execute() {
							organigrammaSpostaDocFascItem.fetchData();
						}
					});
				};

				@Override
				public void setValue(String value) {
					super.setValue(value);
					
					if (value == null || "".equals(value)) {
						uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", "");
						uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", "");
						if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
							uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", AurigaLayout.getCodRapidoOrganigramma());
						} else {
							uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", "");
						}
						uoSpostaDocFascForm.setValue("idUoSpostaDocFasc", "");
						uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", "");
						uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", "");					
						uoSpostaDocFascForm.clearErrors(true);
						
						/*
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
							@Override
							public void execute() {
								organigrammaSpostaDocFascItem.fetchData();
							}
						});
						
						*/
					}
				}
			};
			List<ListGridField> organigrammaSpostaDocFascPickListFields = new ArrayList<ListGridField>();
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
				organigrammaSpostaDocFascPickListFields.add(typeNodoField);
			}
			ListGridField codiceField = new ListGridField("codice", I18NUtil.getMessages().organigramma_list_codUoField_title());
			codiceField.setWidth(80);
			codiceField.setShowHover(true);
			codiceField.setHoverCustomizer(new HoverCustomizer() {
				
				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					return (record != null ? record.getAttribute("descrizioneEstesa") : null);
				}
			});
			if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
				codiceField.setCanFilter(false);
			}
			organigrammaSpostaDocFascPickListFields.add(codiceField);
			ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().organigramma_list_descrizioneField_title());
			descrizioneField.setWidth("*");
			organigrammaSpostaDocFascPickListFields.add(descrizioneField);
			
			organigrammaSpostaDocFascItem.setPickListFields(organigrammaSpostaDocFascPickListFields.toArray(new ListGridField[organigrammaSpostaDocFascPickListFields.size()]));
			if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
				organigrammaSpostaDocFascItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimOrganigrammaNonStdMessage());			
			} else {
				organigrammaSpostaDocFascItem.setFilterLocally(true);
			}
			organigrammaSpostaDocFascItem.setAutoFetchData(false);
			organigrammaSpostaDocFascItem.setAlwaysFetchMissingValues(true);
			organigrammaSpostaDocFascItem.setFetchMissingValues(true);
			organigrammaSpostaDocFascItem.setValueField("id");
			organigrammaSpostaDocFascItem.setOptionDataSource(lGwtRestDataSourceOrganigramma);
			organigrammaSpostaDocFascItem.setShowTitle(false);
			organigrammaSpostaDocFascItem.setWidth(400);
			organigrammaSpostaDocFascItem.setClearable(true);
			organigrammaSpostaDocFascItem.setShowIcons(true);
			organigrammaSpostaDocFascItem.setItemHoverFormatter(new FormItemHoverFormatter() {

				@Override
				public String getHoverHTML(FormItem item, DynamicForm form) {
					return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
				}
			});
			
			ListGrid pickListProperties = organigrammaSpostaDocFascItem.getPickListProperties();
			pickListProperties.addFetchDataHandler(new FetchDataHandler() {

				@Override
				public void onFilterData(FetchDataEvent event) {
					String codRapido = uoSpostaDocFascForm.getValueAsString("codRapidoSpostaDocFasc");
					GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaSpostaDocFascItem.getOptionDataSource();
					organigrammaDS.addParam("codice", codRapido);
					organigrammaDS.addParam("finalita", "ALTRO");
					organigrammaDS.addParam("flgSoloValide", "1");
					organigrammaSpostaDocFascItem.setOptionDataSource(organigrammaDS);
					organigrammaSpostaDocFascItem.invalidateDisplayValueCache();
				}
			});
			organigrammaSpostaDocFascItem.setPickListProperties(pickListProperties);

			lookupOrganigrammaSpostaDocFascButton = new ImgButtonItem("lookupOrganigrammaSpostaDocFascButton", "lookup/organigramma.png", I18NUtil.getMessages().protocollazione_detail_lookupOrganigrammaButton_prompt());
			lookupOrganigrammaSpostaDocFascButton.setColSpan(1);
			lookupOrganigrammaSpostaDocFascButton.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					AssegnazioneLookupOrganigrammaSpostaDocFasc lookupOrganigrammaPopup = new AssegnazioneLookupOrganigrammaSpostaDocFasc(null);
					lookupOrganigrammaPopup.show();
				}
			});
			
			uoSpostaDocFascForm.setFields( // visibili
					                       tipoSpostaDocFascItem,
					                       codRapidoSpostaDocFascItem,
					                       lookupOrganigrammaSpostaDocFascButton,
					                       organigrammaSpostaDocFascItem,				                       
		                                   // Hidden
					                       flgPresentiDocFascItem,
					                       typeNodoSpostaDocFascItem,
					                       idUoSpostaDocFascItem,
					                       descrizioneSpostaDocFascItem          
		                                 );	
			
			uoSpostaDocFascSection = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_uoSpostaDocFascSection_title(), true, true, false, uoSpostaDocFascForm);
			uoSpostaDocFascSection.setVisible(isUOSpostaDocFascSectionVisible());		
		}
		

		private boolean isUOSpostaDocFascSectionVisible(){
			
			Date dataCessazione      = dataAlItem.getValueAsDate();
			
			// Se la data di cessazione < data odierna   
			if (isDataCessazioneScaduta(dataCessazione)){
				
			    // Verifico se sono presenti doc/fasc
				String flgPresentiDocFasc = uoSpostaDocFascForm.getValueAsString("flgPresentiDocFasc");
				if (flgPresentiDocFasc != null){
					if (flgPresentiDocFasc.equalsIgnoreCase("1")){
						return true;
					}
					else{
						return false;
					}
				}else{
					return false;
				}	
			}
			
			// Se la data di cessazione >= data odierna
			if (isDataCessazioneValida(dataCessazione)){
				return true;
			}
			else{
				return false;
			}		
		}
		
		public boolean isDataCessazioneValida(Date dataCessazione){
			Date today = new Date();					
			if ( dataCessazione == null)
				return false;
			
			if ( dataCessazione.after(today) || CalendarUtil.isSameDate(dataCessazione, today) )
				return true;
			else
				return false;
			
		}
		public boolean isDataCessazioneScaduta(Date dataCessazione){
			Date today = new Date();

			if ( dataCessazione == null)
				return false;

			if ( !CalendarUtil.isSameDate(dataCessazione, today) && dataCessazione.before(today) )
				return true;
			else
				return false;
		}
		
		public class AssegnazioneLookupOrganigrammaSpostaDocFasc extends LookupOrganigrammaPopup {

			public AssegnazioneLookupOrganigrammaSpostaDocFasc(Record record) {
				super(record, true, getFlgIncludiUtentiSpostaDocFasc());
			}
			
			@Override
			public void manageLookupBack(Record record) {
				setFormValuesFromRecordSpostaDocFasc(record);
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
			public Boolean getFlgSoloAttive() {
				return true;
			}
		}		

		public void setFormValuesFromRecordSpostaDocFasc(Record record) {
			String idOrganigramma = record.getAttribute("idUoSvUt");
			String tipo = record.getAttribute("tipo");
			int pos = tipo.indexOf("_");
			if (pos != -1) {
				tipo = tipo.substring(0, pos);
			}
			uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", tipo + idOrganigramma);
			uoSpostaDocFascForm.setValue("idUoSpostaDocFasc", idOrganigramma);
			uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", tipo);
			uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", record.getAttribute("codRapidoUo"));
			uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", record.getAttribute("nome"));		
			uoSpostaDocFascForm.clearErrors(true);
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				
				@Override
				public void execute() {
					organigrammaSpostaDocFascItem.fetchData();
				}
			});
		}
		

		
		private void updateSpostaDocFascSection(Object dataCessazione){

			 organigrammaSpostaDocFascItem.setValue("");  uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", (String) null);
			 descrizioneSpostaDocFascItem.setValue("");   uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc",  (String) null);
			 codRapidoSpostaDocFascItem.setValue("");     uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc",    (String) null);
			 idUoSpostaDocFascItem.setValue("");          uoSpostaDocFascForm.setValue("idUoSpostaDocFasc",         (String) null);
			 typeNodoSpostaDocFascItem.setValue("UO");    uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc",     "UO");

			if (dataCessazione != null){
				uoSpostaDocFascSection.setVisible(isUOSpostaDocFascSectionVisible());	
				codRapidoSpostaDocFascItem.setRequired(isUOSpostaDocFascSectionVisible());
				organigrammaSpostaDocFascItem.setRequired(isUOSpostaDocFascSectionVisible());
				resocontoSpostamentoDocFascSection.setVisible(false);
			}
			else{
				uoSpostaDocFascSection.setVisible(false);	
				codRapidoSpostaDocFascItem.setRequired(false);
				organigrammaSpostaDocFascItem.setRequired(false);
				resocontoSpostamentoDocFascSection.setVisible(false);
			}
			uoSpostaDocFascSection.markForRedraw();	
			resocontoSpostamentoDocFascSection.markForRedraw();
		}

		
		private void creaResocontoSpostamentoSection() {
			resocontoSpostamentoDocFascForm = new DynamicForm();
			resocontoSpostamentoDocFascForm.setValuesManager(vm);
			resocontoSpostamentoDocFascForm.setKeepInParentRect(true);
			resocontoSpostamentoDocFascForm.setWidth100();
			resocontoSpostamentoDocFascForm.setHeight100();
			resocontoSpostamentoDocFascForm.setCellPadding(5);
			resocontoSpostamentoDocFascForm.setAlign(Alignment.CENTER);
			resocontoSpostamentoDocFascForm.setOverflow(Overflow.VISIBLE);
			resocontoSpostamentoDocFascForm.setTop(50);
			resocontoSpostamentoDocFascForm.setWrapItemTitles(false);		
			resocontoSpostamentoDocFascForm.setNumCols(16);
			resocontoSpostamentoDocFascForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
			
			// Conteggio documenti
			nrDocAssegnatiItem = new NumericItem("nrDocAssegnati", I18NUtil.getMessages().organigramma_uo_detail_nrDocAssegnati_title());
			nrDocAssegnatiItem.setStartRow(true);
			nrDocAssegnatiItem.setWidth(100);

			dataConteggioDocAssegnatiItem = new DateTimeItem("dataConteggioDocAssegnati", I18NUtil.getMessages().organigramma_uo_detail_dataConteggioDocAssegnati_title());
			dataConteggioDocAssegnatiItem.setWidth(120);

			// Conteggio fascicoli
			nrFascAssegnatiItem = new NumericItem("nrFascAssegnati", I18NUtil.getMessages().organigramma_uo_detail_nrFascAssegnati_title());
			nrFascAssegnatiItem.setStartRow(true);
			nrFascAssegnatiItem.setWidth(100);

			dataConteggioFascAssegnatiItem = new DateTimeItem("dataConteggioFascAssegnati", I18NUtil.getMessages().organigramma_uo_detail_dataConteggioFascAssegnati_title());
			dataConteggioFascAssegnatiItem.setWidth(120);
			
			// Stato spostamento doc/fasc
			descUoSpostamentoDocFascItem = new TextItem("descUoSpostamentoDocFasc", I18NUtil.getMessages().organigramma_postazione_detail_descUoSpostamentoDocFasc_title());		
			descUoSpostamentoDocFascItem.setStartRow(true);
			descUoSpostamentoDocFascItem.setColSpan(3);
			descUoSpostamentoDocFascItem.setWidth(360);

			statoSpostamentoDocFascItem = new TextItem("statoSpostamentoDocFasc", I18NUtil.getMessages().organigramma_uo_detail_statoSpostamentoDocFasc_title());		
			statoSpostamentoDocFascItem.setWidth(120);
			statoSpostamentoDocFascItem.setStartRow(true);
			
			dataInizioSpostamentoDocFascItem = new DateTimeItem("dataInizioSpostamentoDocFasc", I18NUtil.getMessages().organigramma_uo_detail_dataInizioSpostamentoDocFasc_title());
			dataInizioSpostamentoDocFascItem.setWidth(120);
			dataInizioSpostamentoDocFascItem.setStartRow(true);
			
			dataFineSpostamentoDocFascItem = new DateTimeItem("dataFineSpostamentoDocFasc", I18NUtil.getMessages().organigramma_uo_detail_dataFineSpostamentoDocFasc_title());
			dataFineSpostamentoDocFascItem.setWidth(120);

			resocontoSpostamentoDocFascForm.setFields( // visibili
					                                       nrDocAssegnatiItem,
					                                       dataConteggioDocAssegnatiItem,
					                                       nrFascAssegnatiItem,
					                                       dataConteggioFascAssegnatiItem,
					                                       descUoSpostamentoDocFascItem,
					                                       statoSpostamentoDocFascItem,
					                                       dataInizioSpostamentoDocFascItem,
					                                       dataFineSpostamentoDocFascItem
	                                                      );	

			resocontoSpostamentoDocFascSection = new DetailSection(I18NUtil.getMessages().organigramma_postazione_detail_resocontoSpostamentoDocFascSection_title(), true, true, false, resocontoSpostamentoDocFascForm);
			resocontoSpostamentoDocFascSection.setVisible(false);		
		}
		
		private boolean isResocontoSpostamentoDocFascSectionVisible(){
				// Se la data di cessazione < data odierna
				Date dataCessazione      = dataAlItem.getValueAsDate();
				if (isDataCessazioneScaduta(dataCessazione))
				    return true;
				else
					return false;
		}
		  
		public Record getRecordToSave() {
			Record lRecordToSave = new Record();
			// Salvo i form
			addFormValues(lRecordToSave, formMain);
			addFormValues(lRecordToSave, uoSpostaDocFascForm);
			addFormValues(lRecordToSave, resocontoSpostamentoDocFascForm);
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
		
		public void setCanEdit(boolean canEdit) {
			for (DynamicForm form : vm.getMembers()) {
				setCanEdit(canEdit, form);
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
		
		private Date convertStringToDate(String dateIn){
	    	
	    	DateTimeFormat display_datetime_format = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
	    	DateTimeFormat upload_datetime_format = DateTimeFormat.getFormat("MMM dd, yyyy hh:mm:ss a");
	    	DateTimeFormat date_format = DateTimeFormat.getFormat("yyyy-MM-dd");
	    	DateTimeFormat time_format = DateTimeFormat.getFormat("HH:mm:ss");
	    	DateTimeFormat datetime_format = DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ss");
	    	DateTimeFormat datetime_format_toda = DateTimeFormat.getFormat("yyyyMMdd'T'HHmmss'Z'");
	    	DateTimeFormat display_date_format = DateTimeFormat.getFormat("dd/MM/yyyy");
	    	
	    	
	    	if (dateIn == null){
				return null;
			} else {
				
				try {
					return display_datetime_format.parse(dateIn);
				} catch (Exception e) {}	
				try {
					return upload_datetime_format.parse(dateIn);
				} catch (Exception e) {}	
				try {
					return datetime_format.parse(dateIn);
				} catch (Exception e) {}	
				try {
					return date_format.parse(dateIn);
				} catch (Exception e) {}		
				try {
					return display_date_format.parse(dateIn);
				} catch (Exception e) {}	
				return null;
			}
	    }
		
		/*
		private void updateFlg(Object tipoDiAssegnazione){
			
			if (tipoDiAssegnazione != null && !tipoDiAssegnazione.equals("")){
				
				// Se "A" (Appartenenza gerarchica) o "D" (Funzionale/delega)
				if (tipoDiAssegnazione.equals("A") || tipoDiAssegnazione.equals("D")){
					
					// Se il parametro DB DEFAULT_REL_UO_USER_REGE = true
					if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGE")) {
						// Il check "registrazione in entrata" va preimpostato a spuntato
						flgRegistrazioneEItem.setValue(true);
						formMain.setValue("flgRegistrazioneE", true);  
					}
					
					// Se il parametro DB DEFAULT_REL_UO_USER_REGU = true
					if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGIU")) {
						// Il check "registrazione in uscita/interna" va preimpostato a spuntato
						flgRegistrazioneUIItem.setValue(true);
						formMain.setValue("flgRegistrazioneUI", true);
					}
					
					// Se il parametro DB DEFAULT_REL_UO_USER_GEST_ATTI = true
					if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_GEST_ATTI")) {
						// Il check "avvio/gestione atti proposti" va preimpostato a spuntato
						flgGestAttiItem.setValue(true);
						formMain.setValue("flgGestAtti", true);
						
						// in queesto caso forzo il flag flgVisPropAttiInIterItem e lo disabilito
						flgVisPropAttiInIterItem.setValue(true);
						formMain.setValue("flgVisPropAttiInIter", true);
						
						flgVisPropAttiInIterItem.setCanEdit(false);
					}

					// Se il parametro DB DEFAULT_REL_UO_USER_VIS_ATTI = true
					if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_VIS_ATTI")) {
						// Il check "visualizzazione atti proposti" va preimpostato a spuntato
						flgVisPropAttiInIterItem.setValue(true);
						formMain.setValue("flgVisPropAttiInIter", true);
					}
				}
				
				// Se "L" (postazione ombra)
				if (tipoDiAssegnazione.equals("L")){
					
					// Se il parametro DB DEFAULT_POST_OMBRA_ACC_LIM_DOC_ASS_PERS = true
					if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_POST_OMBRA_ACC_LIM_DOC_ASS_PERS")) {
						// Il check "accesso limitato doc. assegnata personalmente" va preimpostato a spuntato
						flgAccessoDocLimSVItem.setValue(true);
						formMain.setValue("flgAccessoDocLimSV", true);
					}
				}
			}
		}
      */
		
		// ottavio
		private void updateFlg(){			
					
			// Se il cliente e' A2A
			if (AurigaLayout.isAttivoClienteA2A()) {
				// Il check "registrazione in entrata" va preimpostato prendendo il valore dalla UO
				if (isUoAbilRegistrazioneE()){
					flgRegistrazioneEItem.setValue(true);
					formMain.setValue("flgRegistrazioneE", true);	
				}
				else{
					flgRegistrazioneEItem.setValue(false);
					formMain.setValue("flgRegistrazioneE", false);	
				}
				
				// Il check "registrazione in uscita" va preimpostato prendendo il valore dalla UO solo se quest'ultimo è 1, 
				// altrimenti si considera il parametro che indica il default (ovvero se la colonna della UO è 0 ma parametro DEFAULT_REL_UO_USER_REGIU = true il  flag "registrazione in uscita/interna" si spunta comunque)
				if (isUoAbilRegistrazioneU()){
					flgRegistrazioneUIItem.setValue(true);
					formMain.setValue("flgRegistrazioneUI", true);	
				}
				else{
					if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGIU")) {
						flgRegistrazioneUIItem.setValue(true);
						formMain.setValue("flgRegistrazioneUI", true);
					}
					else{
						flgRegistrazioneUIItem.setValue(false);
						formMain.setValue("flgRegistrazioneUI", false);
					}
				}
			}
			else{
				// Se il parametro DB DEFAULT_REL_UO_USER_REGE = true
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGE")) {
					// Il check "registrazione in entrata" va preimpostato a spuntato
					flgRegistrazioneEItem.setValue(true);
					formMain.setValue("flgRegistrazioneE", true);  
				}
				
				// Se il parametro DB DEFAULT_REL_UO_USER_REGIU = true
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGIU")) {
					// Il check "registrazione in uscita/interna" va preimpostato a spuntato
					flgRegistrazioneUIItem.setValue(true);
					formMain.setValue("flgRegistrazioneUI", true);
				}
			}
				
			// Se il parametro DB DEFAULT_REL_UO_USER_GEST_ATTI = true
			if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_GEST_ATTI")) {
				// Il check "avvio/gestione atti proposti" va preimpostato a spuntato
				flgGestAttiItem.setValue(true);
				formMain.setValue("flgGestAtti", true);
				
				// in queesto caso forzo il flag flgVisPropAttiInIterItem e lo disabilito
				flgVisPropAttiInIterItem.setValue(true);
				formMain.setValue("flgVisPropAttiInIter", true);
				
				flgVisPropAttiInIterItem.setCanEdit(false);
				
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO")) {
					if(flgGestAttiItem.getValueAsBoolean() != null && flgGestAttiItem.getValueAsBoolean()) {
						flgGestAttiTuttiItem.setValue(true);
						formMain.setValue("flgGestAttiTutti", true);
						listaTipiGestAttiSelezionatiItem.clearValue();
						selezionaTipiGestAttiButton.hide();
					} else {
						flgGestAttiTuttiItem.setValue(false);
						formMain.setValue("flgGestAttiTutti", false);
					}
					if(flgVisPropAttiInIterItem.getValueAsBoolean() != null && flgVisPropAttiInIterItem.getValueAsBoolean()) {
						flgVisPropAttiInIterTuttiItem.setValue(true);
						formMain.setValue("flgVisPropAttiInIterTutti", true);
						listaTipiVisPropAttiInIterSelezionatiItem.clearValue();
						selezionaTipiVisPropAttiInIterButton.hide();
					} else {
						flgVisPropAttiInIterTuttiItem.setValue(false);
						formMain.setValue("flgVisPropAttiInIterTutti", false);
					}
				}
			}

			// Se il parametro DB DEFAULT_REL_UO_USER_VIS_ATTI = true
			if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_VIS_ATTI")) {
				// Il check "visualizzazione atti proposti" va preimpostato a spuntato
				flgVisPropAttiInIterItem.setValue(true);
				formMain.setValue("flgVisPropAttiInIter", true);

				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO")) {
					if(flgVisPropAttiInIterItem.getValueAsBoolean() != null && flgVisPropAttiInIterItem.getValueAsBoolean()) {
						flgVisPropAttiInIterTuttiItem.setValue(true);
						formMain.setValue("flgVisPropAttiInIterTutti", true);
					}
				}
			}
		
			// Se il parametro DB DEFAULT_POST_OMBRA_ACC_LIM_DOC_ASS_PERS = true
			if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_POST_OMBRA_ACC_LIM_DOC_ASS_PERS")) {
				// Il check "accesso limitato doc. assegnata personalmente" va preimpostato a spuntato
				flgAccessoDocLimSVItem.setValue(true);
				formMain.setValue("flgAccessoDocLimSV", true);
			}
		}

		
		@Override
		public void manageOnCloseClick() {		
			if(getIsModal()) {
				markForDestroy();
			} else {
				Layout.removePortlet(getNomeEntita());
			}	
		}
		
		private boolean validate(){
			
			// Se ho messo "postazione ombra" e ho spuntato "accesso limitato doc. assegnata personalmente" e nessuno dei check 
			//   "registrazione in entrata"      
			//   "registrazione in uscita/interna" 
			//   "avvio/gestione atti proposti"   
			// e' spuntato diamo errore
			if (tipoDiAssegnazioneSelectItem.getValue()!=null && tipoDiAssegnazioneSelectItem.getValueAsString().equals("L")){
				if (flgAccessoDocLimSVItem.getValue()!=null && flgAccessoDocLimSVItem.getValueAsBoolean()==true){
					boolean flgRegistrazioneE = false;
					if (flgRegistrazioneEItem.getValue()!=null && flgRegistrazioneEItem.getValueAsBoolean()==true){
						flgRegistrazioneE = true;
					}
					
					boolean flgRegistrazioneUI = false;
					if (flgRegistrazioneUIItem.getValue()!=null && flgRegistrazioneUIItem.getValueAsBoolean()==true){
						flgRegistrazioneUI = true;
					}
					
					boolean flgGestAtti = false;
					if (flgGestAttiItem.getValue()!=null && flgGestAttiItem.getValueAsBoolean()==true){
						flgGestAtti = true;
					}
					if (flgRegistrazioneE==false && flgRegistrazioneUI==false && flgGestAtti==false){
						SC.warn("Almeno una delle opzioni 'registrazione in entrata','registrazione in uscita/interna','avvio/gestione atti proposti' deve essere selezionata.");
						return false;
					}
				}
			}
			return true;
		}
		
		// ottavio
		private boolean isUoAbilRegistrazioneE() {
			return (flgUoAbilRegistrazioneEItem.getValue()!=null && flgUoAbilRegistrazioneEItem.getValue().equals("true"));
		}
		
		private boolean isUoAbilRegistrazioneU() {
			return (flgUoAbilRegistrazioneUItem.getValue()!=null && flgUoAbilRegistrazioneUItem.getValue().equals("true"));
		}
		
}