/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class SaveImpostazioniDocumentoWindow extends ModalWindow {
	
	protected DetailSection detailSectionProtEntrata;
	private DynamicForm protEntrataForm;
	private SelectItem mezzoTrasmissioneItem;
	private SelectItem supportoEntrataItem;
	private SelectItem idTipoDocumentoProtEntrataItem;
	private HiddenItem descTipoDocumentoProtEntrataItem;
	private HiddenItem codCategoriaAltraNumerazioneEntrataItem;
	private HiddenItem siglaAltraNumerazioneEntrataItem;
	private HiddenItem flgTipoDocConVieProtEntrataItem;	
	private HiddenItem flgOggettoNonObbligProtEntrataItem;
	private CheckboxItem skipSceltaTipologiaEntrataItem;
	
	protected DetailSection detailSectionProtUscita;
	private DynamicForm protUscitaForm;
	private SelectItem supportoUscitaItem;
	private SelectItem idTipoDocumentoProtUscitaItem;
	private HiddenItem descTipoDocumentoProtUscitaItem;
	private HiddenItem codCategoriaAltraNumerazioneUscitaItem;
	private HiddenItem siglaAltraNumerazioneUscitaItem;
	private HiddenItem flgTipoDocConVieProtUscitaItem;
	private HiddenItem flgOggettoNonObbligProtUscitaItem;	
	private CheckboxItem skipSceltaTipologiaProtUscitaItem;
	
	protected DetailSection detailSectionProtInterna;
	private DynamicForm protInternaForm;
	private SelectItem supportoInternaItem;
	private SelectItem idTipoDocumentoProtInternaItem;
	private HiddenItem descTipoDocumentoProtInternaItem;
	private HiddenItem codCategoriaAltraNumerazioneInternaItem;
	private HiddenItem siglaAltraNumerazioneInternaItem;
	private HiddenItem flgTipoDocConVieProtInternaItem;
	private HiddenItem flgOggettoNonObbligProtInternaItem;	
	private CheckboxItem skipSceltaTipologiaProtInternaItem;
	
	protected DetailSection detailSectionRepertorioEntrata;
	private DynamicForm repertorioEntrataForm;
	private SelectItem supportoRepertorioEntrataItem;
	private SelectItem repertorioEntrataItem;
	private CheckboxItem skipSceltaRepertorioEntrataItem;
	private SelectItem idTipoDocumentoRepertorioEntrataItem;
	private HiddenItem descTipoDocumentoRepertorioEntrataItem;
	private HiddenItem flgTipoDocConVieRepertorioEntrataItem;
	private HiddenItem flgOggettoNonObbligRepertorioEntrataItem;	
	private CheckboxItem skipSceltaTipologiaRepertorioEntrataItem;
	
	protected DetailSection detailSectionRepertorioInterno;
	private DynamicForm repertorioInternoForm;
	private SelectItem supportoRepertorioInternoItem;
	private SelectItem repertorioInternoItem;
	private CheckboxItem skipSceltaRepertorioInternoItem;
	private SelectItem idTipoDocumentoRepertorioInternoItem;
	private HiddenItem descTipoDocumentoRepertorioInternoItem;
	private HiddenItem flgTipoDocConVieRepertorioInternoItem;
	private HiddenItem flgOggettoNonObbligRepertorioInternoItem;	
	private CheckboxItem skipSceltaTipologiaRepertorioInternoItem;
	
	protected DetailSection detailSectionRepertorioUscita;
	private DynamicForm repertorioUscitaForm;
	private SelectItem supportoRepertorioUscitaItem;
	private SelectItem repertorioUscitaItem;
	private CheckboxItem skipSceltaRepertorioUscitaItem;
	private SelectItem idTipoDocumentoRepertorioUscitaItem;
	private HiddenItem descTipoDocumentoRepertorioUscitaItem;
	private HiddenItem flgTipoDocConVieRepertorioUscitaItem;
	private HiddenItem flgOggettoNonObbligRepertorioUscitaItem;	
	private CheckboxItem skipSceltaTipologiaRepertorioUscitaItem;
	
	protected DetailSection detailSectionRegistroFatture;
	private DynamicForm registroFattureForm;
	private SelectItem idTipoDocumentoRegistroFattureItem;
	private HiddenItem descTipoDocumentoRegistroFattureItem;
	private HiddenItem flgTipoDocConVieRegistroFattureItem;
	private HiddenItem flgOggettoNonObbligRegistroFattureItem;	
	private CheckboxItem skipSceltaTipologiaRegistroFattureItem;
	
	protected DetailSection detailSectionBozza;
	private DynamicForm bozzaForm;
	private SelectItem supportoBozzeItem;
	private SelectItem idTipoDocumentoBozzaItem;
	private HiddenItem descTipoDocumentoBozzaItem;
	private HiddenItem flgTipoDocConVieBozzaItem;
	private HiddenItem flgOggettoNonObbligBozzaItem;	
	private CheckboxItem skipSceltaTipologiaBozzaItem;
	
	protected DetailSection detailSectionProtPregresso;
	private DynamicForm protPregressoForm;
	private SelectItem idTipoDocumentoProtPregressoItem;
	private HiddenItem descTipoDocumentoProtPregressoItem;
	private HiddenItem codCategoriaAltraNumerazionePregressoItem;
	private HiddenItem siglaAltraNumerazionePregressoItem;
	private HiddenItem flgTipoDocConVieProtPregressoItem;
	private HiddenItem flgOggettoNonObbligProtPregressoItem;	
	private CheckboxItem skipSceltaTipologiaProtPregressoItem;
	
	private ValuesManager vm;
	
	public SaveImpostazioniDocumentoWindow() {
		super("config_utente_impostazioniDocumento", true);
		
		vm = new ValuesManager();
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		setTitle(I18NUtil.getMessages().configUtenteMenuImpostazioniDocumento_title());
		setIcon("archivio/flgUdFolder/U.png");

		setWidth(1000);
		setHeight(600);		
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		
		if(Layout.isPrivilegioAttivo("PRT/E")) {
			buildFormProtEntrata();
			detailSectionProtEntrata = new DetailSection("Protocollazione in entrata", true, true, false, protEntrataForm);
			layout.addMember(detailSectionProtEntrata);
		}
		
		if(Layout.isPrivilegioAttivo("PRT/U")) {
			buildFormProtUscita();
			detailSectionProtUscita = new DetailSection("Protocollazione in uscita", true, true, false, protUscitaForm);
			layout.addMember(detailSectionProtUscita);
		}
		
		if(Layout.isPrivilegioAttivo("PRT/I")) {
			buildFormProtInterna();
			detailSectionProtInterna = new DetailSection("Protocollazione interna", true, true, false, protInternaForm);
			layout.addMember(detailSectionProtInterna);
		}
		
		if(Layout.isPrivilegioAttivo("RPR")) {			
			buildFormRepertorioEntrata();
			detailSectionRepertorioEntrata = new DetailSection("A repertorio in entrata", true, true, false, repertorioEntrataForm);
			layout.addMember(detailSectionRepertorioEntrata);
		
			buildFormRepertorioInterno();
			detailSectionRepertorioInterno = new DetailSection("A repertorio interno", true, true, false, repertorioInternoForm);
			layout.addMember(detailSectionRepertorioInterno);
			
			buildFormRepertorioUscita();
			detailSectionRepertorioUscita = new DetailSection("A repertorio in uscita", true, true, false, repertorioUscitaForm);
			layout.addMember(detailSectionRepertorioUscita);
		}
		
		if(Layout.isPrivilegioAttivo("RGF")) {			
			buildFormRegistroFatture();
			detailSectionRegistroFatture = new DetailSection("Nel registro fatture", true, true, false, registroFattureForm);
			layout.addMember(detailSectionRegistroFatture);
		}
		
		if(Layout.isPrivilegioAttivo("GRD/UD/IUD")) {			
			buildFormBozza();
			detailSectionBozza = new DetailSection("Numerazione provvisoria (bozze)", true, true, false, bozzaForm);
			layout.addMember(detailSectionBozza);
		}
		
		if(Layout.isPrivilegioAttivo("GRD/UD/IPP")) {
			buildFormProtPregresso();
			detailSectionProtPregresso = new DetailSection ("Caricamento protocollo pregresso", true, true, false, protPregressoForm);
			layout.addMember(detailSectionProtPregresso);
		}
		
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		
		Button okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						vm.clearErrors(true);				
						Record record = new Record(vm.getValues());
						manageOnOkButtonClick(record);
						markForDestroy();
					}
				});				
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(okButton);
		
		portletLayout.addMember(_buttons);

		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				markForDestroy();
			}
		});

		addItem(portletLayout);
	}
	
	private void buildFormProtEntrata(){
		
		protEntrataForm = new DynamicForm();
		protEntrataForm.setKeepInParentRect(true);
		protEntrataForm.setWrapItemTitles(false);
		protEntrataForm.setNumCols(5);
		protEntrataForm.setColWidths(10, 10, 10, 10, "*");
		protEntrataForm.setPadding(5);
		protEntrataForm.setAlign(Alignment.LEFT);
		protEntrataForm.setTop(50);
		protEntrataForm.setValuesManager(vm);
		
		descTipoDocumentoProtEntrataItem = new HiddenItem("descTipoDocumentoProtEntrata");
		codCategoriaAltraNumerazioneEntrataItem = new HiddenItem("codCategoriaAltraNumerazioneEntrata");
		siglaAltraNumerazioneEntrataItem = new HiddenItem("siglaAltraNumerazioneEntrata");
		flgTipoDocConVieProtEntrataItem = new HiddenItem("flgTipoDocConVieProtEntrata");
		flgOggettoNonObbligProtEntrataItem = new HiddenItem("flgOggettoNonObbligProtEntrata");
		
		mezzoTrasmissioneItem = new SelectItem("mezzoTrasmissione", setTitleAlign("Canale da preimpostare", 200, false));
		GWTRestDataSource mezziTrasmissioneDS = new GWTRestDataSource("LoadComboCanaleRicezioneDataSource", "key", FieldType.TEXT);
		mezzoTrasmissioneItem.setOptionDataSource(mezziTrasmissioneDS);  
		mezzoTrasmissioneItem.setDisplayField("value");
		mezzoTrasmissioneItem.setValueField("key");		
		mezzoTrasmissioneItem.setWidth(200);
		mezzoTrasmissioneItem.setAllowEmptyValue(true);
		mezzoTrasmissioneItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {					
				protEntrataForm.markForRedraw();
			}
		});
		mezzoTrasmissioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ProtocollazioneUtil.isAttivoProtocolloWizard(null);
			}
		});
		
		supportoEntrataItem = new SelectItem("supportoEntrata", setTitleAlign(getTitleSupporto(), 200, false));
		supportoEntrataItem.setDisplayField("value");
		supportoEntrataItem.setValueField("key");		
		supportoEntrataItem.setWidth(200);
		supportoEntrataItem.setAllowEmptyValue(true);
		LinkedHashMap<String, String> tipoSupportoEMap = new LinkedHashMap<String, String>();
		tipoSupportoEMap.put("cartaceo", "cartaceo");
		tipoSupportoEMap.put("digitale", "digitale");
		tipoSupportoEMap.put("misto", "misto");
		supportoEntrataItem.setValueMap(tipoSupportoEMap);
		supportoEntrataItem.setStartRow(true);
		supportoEntrataItem.setAllowEmptyValue(true);
		supportoEntrataItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				supportoEntrataItem.setCanEdit(true);		
				if(mezzoTrasmissioneItem.getValue() != null) {
					if ("R".equals(mezzoTrasmissioneItem.getValueAsString()) || "L".equals(mezzoTrasmissioneItem.getValueAsString())) {
						supportoEntrataItem.setValue("cartaceo");
						supportoEntrataItem.setCanEdit(false);			
					} else if ("PEC".equals(mezzoTrasmissioneItem.getValueAsString()) || "PEO".equals(mezzoTrasmissioneItem.getValueAsString())) {
						supportoEntrataItem.setValue("digitale");
						supportoEntrataItem.setCanEdit(false);			
					}
				}
				return ProtocollazioneUtil.isAttivoProtocolloWizard(null);
			}
		});
		
		GWTRestDataSource idTipoDocumentoEntrataDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
		idTipoDocumentoEntrataDS.addParam("categoriaReg", "PG");
		idTipoDocumentoEntrataDS.addParam("siglaReg", null);

		idTipoDocumentoProtEntrataItem = new SelectItem("idTipoDocumentoProtEntrata") {

			@Override
			public void onOptionClick(Record record) {
				idTipoDocumentoProtEntrataItem.setValue(record.getAttribute("idTipoDocumento"));
				descTipoDocumentoProtEntrataItem.setValue(record.getAttribute("descTipoDocumento"));
				codCategoriaAltraNumerazioneEntrataItem.setValue(record.getAttribute("codCategoriaAltraNumerazione"));
				siglaAltraNumerazioneEntrataItem.setValue(record.getAttribute("siglaAltraNumerazione"));
				flgTipoDocConVieProtEntrataItem.setValue(record.getAttributeAsBoolean("flgTipoDocConVie"));
				flgOggettoNonObbligProtEntrataItem.setValue(record.getAttributeAsBoolean("flgOggettoNonObblig"));
			}
		};
		idTipoDocumentoProtEntrataItem.setShowTitle(true);
		idTipoDocumentoProtEntrataItem.setStartRow(true);
		idTipoDocumentoProtEntrataItem.setTitle(setTitleAlign("Tipologia documentale da preimpostare", 220, false));
		idTipoDocumentoProtEntrataItem.setWidth(300);
		idTipoDocumentoProtEntrataItem.setColSpan(2);
		idTipoDocumentoProtEntrataItem.setAlign(Alignment.CENTER);
		idTipoDocumentoProtEntrataItem.setValueField("idTipoDocumento");
		idTipoDocumentoProtEntrataItem.setDisplayField("descTipoDocumento");
		idTipoDocumentoProtEntrataItem.setOptionDataSource(idTipoDocumentoEntrataDS);
		idTipoDocumentoProtEntrataItem.setAllowEmptyValue(true);
		
		skipSceltaTipologiaEntrataItem = new CheckboxItem("skipSceltaTipologiaEntrata", setTitleAlign("Salta scelta tipologia", 200, false));
		skipSceltaTipologiaEntrataItem.setStartRow(false);
		
		protEntrataForm.setFields(
				mezzoTrasmissioneItem,
				supportoEntrataItem,
				idTipoDocumentoProtEntrataItem,
				descTipoDocumentoProtEntrataItem,
				codCategoriaAltraNumerazioneEntrataItem,
				siglaAltraNumerazioneEntrataItem,
				flgTipoDocConVieProtEntrataItem,
				flgOggettoNonObbligProtEntrataItem,
				skipSceltaTipologiaEntrataItem);
	}
	
	private void buildFormProtUscita(){
		
		protUscitaForm = new DynamicForm();
		protUscitaForm.setKeepInParentRect(true);
		protUscitaForm.setWrapItemTitles(false);
		protUscitaForm.setNumCols(5);
		protUscitaForm.setColWidths(10, 10, 10, 10, "*");
		protUscitaForm.setPadding(5);
		protUscitaForm.setAlign(Alignment.LEFT);
		protUscitaForm.setTop(50);
		protUscitaForm.setValuesManager(vm);
		
		descTipoDocumentoProtUscitaItem = new HiddenItem("descTipoDocumentoProtUscita");
		codCategoriaAltraNumerazioneUscitaItem = new HiddenItem("codCategoriaAltraNumerazioneUscita");
		siglaAltraNumerazioneUscitaItem = new HiddenItem("siglaAltraNumerazioneUscita");
		flgTipoDocConVieProtUscitaItem = new HiddenItem("flgTipoDocConVieProtUscita");
		flgOggettoNonObbligProtUscitaItem = new HiddenItem("flgOggettoNonObbligProtUscita");
			
		supportoUscitaItem = new SelectItem("supportoUscita", setTitleAlign(getTitleSupporto(), 200, false));
		supportoUscitaItem.setDisplayField("value");
		supportoUscitaItem.setValueField("key");		
		supportoUscitaItem.setWidth(200);
		supportoUscitaItem.setAllowEmptyValue(true);
		LinkedHashMap<String, String> tipoSupportoUMap = new LinkedHashMap<String, String>();
		tipoSupportoUMap.put("cartaceo", "cartaceo");
		tipoSupportoUMap.put("digitale", "digitale");
		tipoSupportoUMap.put("misto", "misto");
		supportoUscitaItem.setValueMap(tipoSupportoUMap);
		supportoUscitaItem.setStartRow(true);
		supportoUscitaItem.setAllowEmptyValue(true);
		supportoUscitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ProtocollazioneUtil.isAttivoProtocolloWizard(null);
			}
		});
		
		GWTRestDataSource idTipoDocumentoUscitaDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
		idTipoDocumentoUscitaDS.addParam("categoriaReg", "PG");
		idTipoDocumentoUscitaDS.addParam("siglaReg", null);

		idTipoDocumentoProtUscitaItem = new SelectItem("idTipoDocumentoProtUscita") {

			@Override
			public void onOptionClick(Record record) {
				idTipoDocumentoProtUscitaItem.setValue(record.getAttribute("idTipoDocumento"));
				descTipoDocumentoProtUscitaItem.setValue(record.getAttribute("descTipoDocumento"));
				codCategoriaAltraNumerazioneUscitaItem.setValue(record.getAttribute("codCategoriaAltraNumerazione"));
				siglaAltraNumerazioneUscitaItem.setValue(record.getAttribute("siglaAltraNumerazione"));
				flgTipoDocConVieProtUscitaItem.setValue(record.getAttributeAsBoolean("flgTipoDocConVie"));
				flgOggettoNonObbligProtUscitaItem.setValue(record.getAttributeAsBoolean("flgOggettoNonObblig"));
			}
		};
		idTipoDocumentoProtUscitaItem.setShowTitle(true);
		idTipoDocumentoProtUscitaItem.setTitle(setTitleAlign("Tipologia documentale da preimpostare", 220, false));
		idTipoDocumentoProtUscitaItem.setStartRow(true);
		idTipoDocumentoProtUscitaItem.setWidth(300);
		idTipoDocumentoProtUscitaItem.setColSpan(2);
		idTipoDocumentoProtUscitaItem.setAlign(Alignment.CENTER);
		idTipoDocumentoProtUscitaItem.setValueField("idTipoDocumento");
		idTipoDocumentoProtUscitaItem.setDisplayField("descTipoDocumento");
		idTipoDocumentoProtUscitaItem.setOptionDataSource(idTipoDocumentoUscitaDS);
		idTipoDocumentoProtUscitaItem.setAllowEmptyValue(true);
		
		skipSceltaTipologiaProtUscitaItem = new CheckboxItem("skipSceltaTipologiaProtUscita", setTitleAlign("Salta scelta tipologia", 200, false));
		skipSceltaTipologiaProtUscitaItem.setStartRow(false);
		
		protUscitaForm.setFields(
				supportoUscitaItem,
				idTipoDocumentoProtUscitaItem,
				descTipoDocumentoProtUscitaItem,
				codCategoriaAltraNumerazioneUscitaItem,
				siglaAltraNumerazioneUscitaItem,
				flgTipoDocConVieProtUscitaItem,
				flgOggettoNonObbligProtUscitaItem,
				skipSceltaTipologiaProtUscitaItem);
	}
	
	private void buildFormProtInterna(){
		
		protInternaForm = new DynamicForm();
		protInternaForm.setKeepInParentRect(true);
		protInternaForm.setWrapItemTitles(false);
		protInternaForm.setNumCols(5);
		protInternaForm.setColWidths(10, 10, 10, 10, "*");
		protInternaForm.setPadding(5);
		protInternaForm.setAlign(Alignment.LEFT);
		protInternaForm.setTop(50);
		protInternaForm.setValuesManager(vm);
		
		descTipoDocumentoProtInternaItem = new HiddenItem("descTipoDocumentoProtInterna");
		codCategoriaAltraNumerazioneInternaItem = new HiddenItem("codCategoriaAltraNumerazioneInterna");
		siglaAltraNumerazioneInternaItem = new HiddenItem("siglaAltraNumerazioneInterna");
		flgTipoDocConVieProtInternaItem = new HiddenItem("flgTipoDocConVieProtInterna");
		flgOggettoNonObbligProtInternaItem = new HiddenItem("flgOggettoNonObbligProtInterna");
		
		String categoriaReg = AurigaLayout.getParametroDBAsBoolean("ATTIVA_REGISTRO_PG_X_PROT_INTERNA") ? "PG" : "I";
		String siglaReg = AurigaLayout.getParametroDBAsBoolean("ATTIVA_REGISTRO_PG_X_PROT_INTERNA") ? null : "P.I.";
		GWTRestDataSource idTipoDocumentoInternaDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
		idTipoDocumentoInternaDS.addParam("categoriaReg", categoriaReg);
		idTipoDocumentoInternaDS.addParam("siglaReg", siglaReg);
		
		supportoInternaItem = new SelectItem("supportoInterna", setTitleAlign(getTitleSupporto(), 200, false));
		supportoInternaItem.setDisplayField("value");
		supportoInternaItem.setValueField("key");		
		supportoInternaItem.setWidth(200);
		supportoInternaItem.setAllowEmptyValue(true);
		LinkedHashMap<String, String> tipoSupportoIMap = new LinkedHashMap<String, String>();
		tipoSupportoIMap.put("cartaceo", "cartaceo");
		tipoSupportoIMap.put("digitale", "digitale");
		tipoSupportoIMap.put("misto", "misto");
		supportoInternaItem.setValueMap(tipoSupportoIMap);
		supportoInternaItem.setStartRow(true);
		supportoInternaItem.setAllowEmptyValue(true);
		supportoInternaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ProtocollazioneUtil.isAttivoProtocolloWizard(null);
			}
		});

		idTipoDocumentoProtInternaItem = new SelectItem("idTipoDocumentoProtInterna") {

			@Override
			public void onOptionClick(Record record) {
				idTipoDocumentoProtInternaItem.setValue(record.getAttribute("idTipoDocumento"));
				descTipoDocumentoProtInternaItem.setValue(record.getAttribute("descTipoDocumento"));
				codCategoriaAltraNumerazioneInternaItem.setValue(record.getAttribute("codCategoriaAltraNumerazione"));
				siglaAltraNumerazioneInternaItem.setValue(record.getAttribute("siglaAltraNumerazione"));
				flgTipoDocConVieProtInternaItem.setValue(record.getAttributeAsBoolean("flgTipoDocConVie"));
				flgOggettoNonObbligProtInternaItem.setValue(record.getAttributeAsBoolean("flgOggettoNonObblig"));				
			}
		};
		idTipoDocumentoProtInternaItem.setShowTitle(true);
		idTipoDocumentoProtInternaItem.setTitle(setTitleAlign("Tipologia documentale da preimpostare", 220, false));
		idTipoDocumentoProtInternaItem.setStartRow(true);
		idTipoDocumentoProtInternaItem.setWidth(300);
		idTipoDocumentoProtInternaItem.setColSpan(2);
		idTipoDocumentoProtInternaItem.setAlign(Alignment.CENTER);
		idTipoDocumentoProtInternaItem.setValueField("idTipoDocumento");
		idTipoDocumentoProtInternaItem.setDisplayField("descTipoDocumento");
		idTipoDocumentoProtInternaItem.setOptionDataSource(idTipoDocumentoInternaDS);
		idTipoDocumentoProtInternaItem.setAllowEmptyValue(true);
		
		skipSceltaTipologiaProtInternaItem = new CheckboxItem("skipSceltaTipologiaProtInterna", setTitleAlign("Salta scelta tipologia", 200, false));
		skipSceltaTipologiaProtInternaItem.setStartRow(false);
		
		protInternaForm.setFields(
				supportoInternaItem,
				idTipoDocumentoProtInternaItem,
				descTipoDocumentoProtInternaItem,
				codCategoriaAltraNumerazioneInternaItem,
				siglaAltraNumerazioneInternaItem,
				flgTipoDocConVieProtInternaItem,
				flgOggettoNonObbligProtInternaItem,
				skipSceltaTipologiaProtInternaItem);
	}
	
	private void buildFormRepertorioEntrata() {
		
		repertorioEntrataForm = new DynamicForm();
		repertorioEntrataForm.setKeepInParentRect(true);
		repertorioEntrataForm.setWrapItemTitles(false);
		repertorioEntrataForm.setNumCols(5);
		repertorioEntrataForm.setColWidths(10, 10, 10, 10, "*");
		repertorioEntrataForm.setPadding(5);
		repertorioEntrataForm.setAlign(Alignment.LEFT);
		repertorioEntrataForm.setTop(50);
		repertorioEntrataForm.setValuesManager(vm);
		
		supportoRepertorioEntrataItem = new SelectItem("supportoRepertorioEntrata", setTitleAlign(getTitleSupporto(), 200, false));
		supportoRepertorioEntrataItem.setDisplayField("value");
		supportoRepertorioEntrataItem.setValueField("key");		
		supportoRepertorioEntrataItem.setWidth(200);
		supportoRepertorioEntrataItem.setAllowEmptyValue(true);
		LinkedHashMap<String, String> tipoSupportoRMap = new LinkedHashMap<String, String>();
		tipoSupportoRMap.put("cartaceo", "cartaceo");
		tipoSupportoRMap.put("digitale", "digitale");
		tipoSupportoRMap.put("misto", "misto");
		supportoRepertorioEntrataItem.setValueMap(tipoSupportoRMap);
		supportoRepertorioEntrataItem.setStartRow(true);
		supportoRepertorioEntrataItem.setAllowEmptyValue(true);
		supportoRepertorioEntrataItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ProtocollazioneUtil.isAttivoProtocolloWizard(null);
			}
		});
		
		GWTRestDataSource gruppiRepertorioEntrataDS = new GWTRestDataSource("LoadComboGruppiRepertorioSource", "key", FieldType.TEXT);
		gruppiRepertorioEntrataDS.addParam("flgTipoProv", "E");
		
		repertorioEntrataItem = new SelectItem("repertorioEntrata", I18NUtil.getMessages().protocollazione_detail_repertorioItem_title());
		repertorioEntrataItem.setShowTitle(true);
		repertorioEntrataItem.setTitle(setTitleAlign("Repertorio da preimpostare", 200, false));
		repertorioEntrataItem.setStartRow(true);
		repertorioEntrataItem.setWidth(300);
		repertorioEntrataItem.setColSpan(2);
		repertorioEntrataItem.setAlign(Alignment.CENTER);
		repertorioEntrataItem.setValueField("key");
		repertorioEntrataItem.setDisplayField("value");
		repertorioEntrataItem.setOptionDataSource(gruppiRepertorioEntrataDS);		
		repertorioEntrataItem.setAllowEmptyValue(true);
		repertorioEntrataItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				idTipoDocumentoRepertorioEntrataItem.fetchData();
				repertorioEntrataForm.markForRedraw();
			}
		});
		
		skipSceltaRepertorioEntrataItem = new CheckboxItem("skipSceltaRepertorioEntrata", setTitleAlign("Salta scelta repertorio", 200, false));
		skipSceltaRepertorioEntrataItem.setStartRow(false);
		skipSceltaRepertorioEntrataItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if ((repertorioEntrataItem.getValueAsString() != null && !"".equals(repertorioEntrataItem.getValueAsString()))) {					
					return true;
				} else {
					if(skipSceltaRepertorioEntrataItem.getValueAsBoolean() != null && skipSceltaRepertorioEntrataItem.getValueAsBoolean()) {
						skipSceltaRepertorioEntrataItem.setValue(false);
						repertorioEntrataForm.markForRedraw();
					}
					return false;
				}
			}
		});
		skipSceltaRepertorioEntrataItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				repertorioEntrataForm.markForRedraw();
			}
		});
		
		descTipoDocumentoRepertorioEntrataItem = new HiddenItem("descTipoDocumentoRepertorioEntrata");
		flgTipoDocConVieRepertorioEntrataItem = new HiddenItem("flgTipoDocConVieRepertorioEntrata");
		flgOggettoNonObbligRepertorioEntrataItem = new HiddenItem("flgOggettoNonObbligRepertorioEntrata");
		
		GWTRestDataSource idTipoDocumentoRepertorioEntrataDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
		idTipoDocumentoRepertorioEntrataDS.addParam("categoriaReg", "R");
		
		idTipoDocumentoRepertorioEntrataItem = new SelectItem("idTipoDocumentoRepertorioEntrata") {
	
			@Override
			public void onOptionClick(Record record) {
				idTipoDocumentoRepertorioEntrataItem.setValue(record.getAttribute("idTipoDocumento"));
				descTipoDocumentoRepertorioEntrataItem.setValue(record.getAttribute("descTipoDocumento"));
				flgTipoDocConVieRepertorioEntrataItem.setValue(record.getAttributeAsBoolean("flgTipoDocConVie"));
				flgOggettoNonObbligRepertorioEntrataItem.setValue(record.getAttributeAsBoolean("flgOggettoNonObblig"));
			}
		};
		idTipoDocumentoRepertorioEntrataItem.setShowTitle(true);
		idTipoDocumentoRepertorioEntrataItem.setTitle(setTitleAlign("Tipologia documentale da preimpostare", 220, false));
		idTipoDocumentoRepertorioEntrataItem.setStartRow(true);
		idTipoDocumentoRepertorioEntrataItem.setWidth(300);
		idTipoDocumentoRepertorioEntrataItem.setColSpan(2);
		idTipoDocumentoRepertorioEntrataItem.setAlign(Alignment.CENTER);
		idTipoDocumentoRepertorioEntrataItem.setValueField("idTipoDocumento");
		idTipoDocumentoRepertorioEntrataItem.setDisplayField("descTipoDocumento");
		idTipoDocumentoRepertorioEntrataItem.setOptionDataSource(idTipoDocumentoRepertorioEntrataDS);
		idTipoDocumentoRepertorioEntrataItem.setAllowEmptyValue(true);
		idTipoDocumentoRepertorioEntrataItem.addDataArrivedHandler(new DataArrivedHandler() {
			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				if (event.getData().getLength() == 0) { 
					idTipoDocumentoRepertorioEntrataItem.setValue((String) null);
					descTipoDocumentoRepertorioEntrataItem.setValue((String) null);
					flgTipoDocConVieRepertorioEntrataItem.setValue(false);	
					flgOggettoNonObbligRepertorioEntrataItem.setValue(false);						
				} else if (event.getData().getLength() == 1) {
					idTipoDocumentoRepertorioEntrataItem.setValue(event.getData().get(0).getAttribute("idTipoDocumento"));
					descTipoDocumentoRepertorioEntrataItem.setValue(event.getData().get(0).getAttribute("descTipoDocumento"));
					flgTipoDocConVieRepertorioEntrataItem.setValue(event.getData().get(0).getAttributeAsBoolean("flgTipoDocConVie"));
					flgOggettoNonObbligRepertorioEntrataItem.setValue(event.getData().get(0).getAttributeAsBoolean("flgOggettoNonObblig"));					
				} 
			}
		});
		ListGrid idTipoDocumentoRepertorioEntrataPickListProperties = idTipoDocumentoRepertorioEntrataItem.getPickListProperties();
		idTipoDocumentoRepertorioEntrataPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				GWTRestDataSource idTipoDocumentoRepertorioEntrataDS = (GWTRestDataSource) idTipoDocumentoRepertorioEntrataItem.getOptionDataSource();
				idTipoDocumentoRepertorioEntrataDS.addParam("categoriaReg", "R");
				idTipoDocumentoRepertorioEntrataDS.addParam("siglaReg", repertorioEntrataItem.getValueAsString());		
				idTipoDocumentoRepertorioEntrataItem.setOptionDataSource(idTipoDocumentoRepertorioEntrataDS);
				idTipoDocumentoRepertorioEntrataItem.invalidateDisplayValueCache();
			}
		});
		idTipoDocumentoRepertorioEntrataItem.setPickListProperties(idTipoDocumentoRepertorioEntrataPickListProperties);
		
		skipSceltaTipologiaRepertorioEntrataItem = new CheckboxItem("skipSceltaTipologiaRepertorioEntrata", setTitleAlign("Salta scelta tipologia", 200, false));
		skipSceltaTipologiaRepertorioEntrataItem.setStartRow(false);

		repertorioEntrataForm.setFields(
				supportoRepertorioEntrataItem,
				repertorioEntrataItem,
				skipSceltaRepertorioEntrataItem,
				idTipoDocumentoRepertorioEntrataItem,
				descTipoDocumentoRepertorioEntrataItem,
				flgTipoDocConVieRepertorioEntrataItem,
				flgOggettoNonObbligRepertorioEntrataItem,
				skipSceltaTipologiaRepertorioEntrataItem);
	}
	
	private void buildFormRepertorioInterno() {
		
		repertorioInternoForm = new DynamicForm();
		repertorioInternoForm.setKeepInParentRect(true);
		repertorioInternoForm.setWrapItemTitles(false);
		repertorioInternoForm.setNumCols(5);
		repertorioInternoForm.setColWidths(10, 10, 10, 10, "*");
		repertorioInternoForm.setPadding(5);
		repertorioInternoForm.setAlign(Alignment.LEFT);
		repertorioInternoForm.setTop(50);
		repertorioInternoForm.setValuesManager(vm);
		
		supportoRepertorioInternoItem = new SelectItem("supportoRepertorioInterno", setTitleAlign(getTitleSupporto(), 200, false));
		supportoRepertorioInternoItem.setDisplayField("value");
		supportoRepertorioInternoItem.setValueField("key");		
		supportoRepertorioInternoItem.setWidth(200);
		supportoRepertorioInternoItem.setAllowEmptyValue(true);
		LinkedHashMap<String, String> tipoSupportoRMap = new LinkedHashMap<String, String>();
		tipoSupportoRMap.put("cartaceo", "cartaceo");
		tipoSupportoRMap.put("digitale", "digitale");
		tipoSupportoRMap.put("misto", "misto");
		supportoRepertorioInternoItem.setValueMap(tipoSupportoRMap);
		supportoRepertorioInternoItem.setStartRow(true);
		supportoRepertorioInternoItem.setAllowEmptyValue(true);
		supportoRepertorioInternoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ProtocollazioneUtil.isAttivoProtocolloWizard(null);
			}
		});
		
		GWTRestDataSource gruppiRepertorioInternoDS = new GWTRestDataSource("LoadComboGruppiRepertorioSource", "key", FieldType.TEXT);
		gruppiRepertorioInternoDS.addParam("flgTipoProv", "I");
		
		repertorioInternoItem = new SelectItem("repertorioInterno", I18NUtil.getMessages().protocollazione_detail_repertorioItem_title());
		repertorioInternoItem.setShowTitle(true);
		repertorioInternoItem.setTitle(setTitleAlign("Repertorio da preimpostare", 200, false));
		repertorioInternoItem.setStartRow(true);
		repertorioInternoItem.setWidth(300);
		repertorioInternoItem.setColSpan(2);
		repertorioInternoItem.setAlign(Alignment.CENTER);
		repertorioInternoItem.setValueField("key");
		repertorioInternoItem.setDisplayField("value");
		repertorioInternoItem.setOptionDataSource(gruppiRepertorioInternoDS);		
		repertorioInternoItem.setAllowEmptyValue(true);
		repertorioInternoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				idTipoDocumentoRepertorioInternoItem.fetchData();
				repertorioInternoForm.markForRedraw();
			}
		});
		
		skipSceltaRepertorioInternoItem = new CheckboxItem("skipSceltaRepertorioInterno", setTitleAlign("Salta scelta repertorio", 200, false));
		skipSceltaRepertorioInternoItem.setStartRow(false);
		skipSceltaRepertorioInternoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if ((repertorioInternoItem.getValueAsString() != null && !"".equals(repertorioInternoItem.getValueAsString()))) {					
					return true;
				} else {
					if(skipSceltaRepertorioInternoItem.getValueAsBoolean() != null && skipSceltaRepertorioInternoItem.getValueAsBoolean()) {
						skipSceltaRepertorioInternoItem.setValue(false);
						repertorioInternoForm.markForRedraw();
					}
					return false;
				}
			}
		});
		skipSceltaRepertorioInternoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				repertorioInternoForm.markForRedraw();
			}
		});
		
		descTipoDocumentoRepertorioInternoItem = new HiddenItem("descTipoDocumentoRepertorioInterno");
		flgTipoDocConVieRepertorioInternoItem = new HiddenItem("flgTipoDocConVieRepertorioInterno");
		flgOggettoNonObbligRepertorioInternoItem = new HiddenItem("flgOggettoNonObbligRepertorioInterno");
		
		GWTRestDataSource idTipoDocumentoRepertorioInternoDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
		idTipoDocumentoRepertorioInternoDS.addParam("categoriaReg", "R");
		
		idTipoDocumentoRepertorioInternoItem = new SelectItem("idTipoDocumentoRepertorioInterno") {
	
			@Override
			public void onOptionClick(Record record) {
				idTipoDocumentoRepertorioInternoItem.setValue(record.getAttribute("idTipoDocumento"));
				descTipoDocumentoRepertorioInternoItem.setValue(record.getAttribute("descTipoDocumento"));
				flgTipoDocConVieRepertorioInternoItem.setValue(record.getAttributeAsBoolean("flgTipoDocConVie"));
				flgOggettoNonObbligRepertorioInternoItem.setValue(record.getAttributeAsBoolean("flgOggettoNonObblig"));
			}
		};
		idTipoDocumentoRepertorioInternoItem.setShowTitle(true);
		idTipoDocumentoRepertorioInternoItem.setTitle(setTitleAlign("Tipologia documentale da preimpostare", 220, false));
		idTipoDocumentoRepertorioInternoItem.setStartRow(true);
		idTipoDocumentoRepertorioInternoItem.setWidth(300);
		idTipoDocumentoRepertorioInternoItem.setColSpan(2);
		idTipoDocumentoRepertorioInternoItem.setAlign(Alignment.CENTER);
		idTipoDocumentoRepertorioInternoItem.setValueField("idTipoDocumento");
		idTipoDocumentoRepertorioInternoItem.setDisplayField("descTipoDocumento");
		idTipoDocumentoRepertorioInternoItem.setOptionDataSource(idTipoDocumentoRepertorioInternoDS);
		idTipoDocumentoRepertorioInternoItem.setAllowEmptyValue(true);
		idTipoDocumentoRepertorioInternoItem.addDataArrivedHandler(new DataArrivedHandler() {
			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				if (event.getData().getLength() == 0) { 
					idTipoDocumentoRepertorioInternoItem.setValue((String) null);
					descTipoDocumentoRepertorioInternoItem.setValue((String) null);
					flgTipoDocConVieRepertorioInternoItem.setValue(false);	
					flgOggettoNonObbligRepertorioInternoItem.setValue(false);						
				} else if (event.getData().getLength() == 1) {
					idTipoDocumentoRepertorioInternoItem.setValue(event.getData().get(0).getAttribute("idTipoDocumento"));
					descTipoDocumentoRepertorioInternoItem.setValue(event.getData().get(0).getAttribute("descTipoDocumento"));
					flgTipoDocConVieRepertorioInternoItem.setValue(event.getData().get(0).getAttributeAsBoolean("flgTipoDocConVie"));
					flgOggettoNonObbligRepertorioInternoItem.setValue(event.getData().get(0).getAttributeAsBoolean("flgOggettoNonObblig"));					
				}
			}
		});
		ListGrid idTipoDocumentoRepertorioInternoPickListProperties = idTipoDocumentoRepertorioInternoItem.getPickListProperties();
		idTipoDocumentoRepertorioInternoPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				GWTRestDataSource idTipoDocumentoRepertorioInternoDS = (GWTRestDataSource) idTipoDocumentoRepertorioInternoItem.getOptionDataSource();
				idTipoDocumentoRepertorioInternoDS.addParam("categoriaReg", "R");
				idTipoDocumentoRepertorioInternoDS.addParam("siglaReg", repertorioInternoItem.getValueAsString());		
				idTipoDocumentoRepertorioInternoItem.setOptionDataSource(idTipoDocumentoRepertorioInternoDS);
				idTipoDocumentoRepertorioInternoItem.invalidateDisplayValueCache();
			}
		});
		idTipoDocumentoRepertorioInternoItem.setPickListProperties(idTipoDocumentoRepertorioInternoPickListProperties);
		
		skipSceltaTipologiaRepertorioInternoItem = new CheckboxItem("skipSceltaTipologiaRepertorioInterno", setTitleAlign("Salta scelta tipologia", 200, false));
		skipSceltaTipologiaRepertorioInternoItem.setStartRow(false);

		repertorioInternoForm.setFields(
			supportoRepertorioInternoItem,
			repertorioInternoItem,
			skipSceltaRepertorioInternoItem,
			idTipoDocumentoRepertorioInternoItem,
			descTipoDocumentoRepertorioInternoItem,
			flgTipoDocConVieRepertorioInternoItem,
			flgOggettoNonObbligRepertorioInternoItem,
			skipSceltaTipologiaRepertorioInternoItem
		);
	}
	
	private void buildFormRepertorioUscita() {
		
		repertorioUscitaForm = new DynamicForm();
		repertorioUscitaForm.setKeepInParentRect(true);
		repertorioUscitaForm.setWrapItemTitles(false);
		repertorioUscitaForm.setNumCols(5);
		repertorioUscitaForm.setColWidths(10, 10, 10, 10, "*");
		repertorioUscitaForm.setPadding(5);
		repertorioUscitaForm.setAlign(Alignment.LEFT);
		repertorioUscitaForm.setTop(50);
		repertorioUscitaForm.setValuesManager(vm);
		
		supportoRepertorioUscitaItem = new SelectItem("supportoRepertorioUscita", setTitleAlign(getTitleSupporto(), 200, false));
		supportoRepertorioUscitaItem.setDisplayField("value");
		supportoRepertorioUscitaItem.setValueField("key");		
		supportoRepertorioUscitaItem.setWidth(200);
		supportoRepertorioUscitaItem.setAllowEmptyValue(true);
		LinkedHashMap<String, String> tipoSupportoRMap = new LinkedHashMap<String, String>();
		tipoSupportoRMap.put("cartaceo", "cartaceo");
		tipoSupportoRMap.put("digitale", "digitale");
		tipoSupportoRMap.put("misto", "misto");
		supportoRepertorioUscitaItem.setValueMap(tipoSupportoRMap);
		supportoRepertorioUscitaItem.setStartRow(true);
		supportoRepertorioUscitaItem.setAllowEmptyValue(true);
		supportoRepertorioUscitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ProtocollazioneUtil.isAttivoProtocolloWizard(null);
			}
		});
		
		GWTRestDataSource gruppiRepertorioUscitaDS = new GWTRestDataSource("LoadComboGruppiRepertorioSource", "key", FieldType.TEXT);
		gruppiRepertorioUscitaDS.addParam("flgTipoProv", "U");
		
		repertorioUscitaItem = new SelectItem("repertorioUscita", I18NUtil.getMessages().protocollazione_detail_repertorioItem_title());
		repertorioUscitaItem.setShowTitle(true);
		repertorioUscitaItem.setTitle(setTitleAlign("Repertorio da preimpostare", 200, false));
		repertorioUscitaItem.setStartRow(true);
		repertorioUscitaItem.setWidth(300);
		repertorioUscitaItem.setColSpan(2);
		repertorioUscitaItem.setAlign(Alignment.CENTER);
		repertorioUscitaItem.setValueField("key");
		repertorioUscitaItem.setDisplayField("value");
		repertorioUscitaItem.setOptionDataSource(gruppiRepertorioUscitaDS);		
		repertorioUscitaItem.setAllowEmptyValue(true);
		repertorioUscitaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				idTipoDocumentoRepertorioUscitaItem.fetchData();
				repertorioUscitaForm.markForRedraw();
			}
		});
		
		skipSceltaRepertorioUscitaItem = new CheckboxItem("skipSceltaRepertorioUscita", setTitleAlign("Salta scelta repertorio", 200, false));
		skipSceltaRepertorioUscitaItem.setStartRow(false);
		skipSceltaRepertorioUscitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if ((repertorioUscitaItem.getValueAsString() != null && !"".equals(repertorioUscitaItem.getValueAsString()))) {					
					return true;
				} else {
					if(skipSceltaRepertorioUscitaItem.getValueAsBoolean() != null && skipSceltaRepertorioUscitaItem.getValueAsBoolean()) {
						skipSceltaRepertorioUscitaItem.setValue(false);
						repertorioUscitaForm.markForRedraw();
					}
					return false;
				}
			}
		});
		skipSceltaRepertorioUscitaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				repertorioUscitaForm.markForRedraw();
			}
		});
		
		descTipoDocumentoRepertorioUscitaItem = new HiddenItem("descTipoDocumentoRepertorioUscita");
		flgTipoDocConVieRepertorioUscitaItem = new HiddenItem("flgTipoDocConVieRepertorioUscita");
		flgOggettoNonObbligRepertorioUscitaItem = new HiddenItem("flgOggettoNonObbligRepertorioUscita");
		
		GWTRestDataSource idTipoDocumentoRepertorioUscitaDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
		idTipoDocumentoRepertorioUscitaDS.addParam("categoriaReg", "R");
		
		idTipoDocumentoRepertorioUscitaItem = new SelectItem("idTipoDocumentoRepertorioUscita") {
	
			@Override
			public void onOptionClick(Record record) {
				idTipoDocumentoRepertorioUscitaItem.setValue(record.getAttribute("idTipoDocumento"));
				descTipoDocumentoRepertorioUscitaItem.setValue(record.getAttribute("descTipoDocumento"));
				flgTipoDocConVieRepertorioUscitaItem.setValue(record.getAttributeAsBoolean("flgTipoDocConVie"));
				flgOggettoNonObbligRepertorioUscitaItem.setValue(record.getAttributeAsBoolean("flgOggettoNonObblig"));
			}
		};
		idTipoDocumentoRepertorioUscitaItem.setShowTitle(true);
		idTipoDocumentoRepertorioUscitaItem.setTitle(setTitleAlign("Tipologia documentale da preimpostare", 220, false));
		idTipoDocumentoRepertorioUscitaItem.setStartRow(true);
		idTipoDocumentoRepertorioUscitaItem.setWidth(300);
		idTipoDocumentoRepertorioUscitaItem.setColSpan(2);
		idTipoDocumentoRepertorioUscitaItem.setAlign(Alignment.CENTER);
		idTipoDocumentoRepertorioUscitaItem.setValueField("idTipoDocumento");
		idTipoDocumentoRepertorioUscitaItem.setDisplayField("descTipoDocumento");
		idTipoDocumentoRepertorioUscitaItem.setOptionDataSource(idTipoDocumentoRepertorioUscitaDS);
		idTipoDocumentoRepertorioUscitaItem.setAllowEmptyValue(true);
		idTipoDocumentoRepertorioUscitaItem.addDataArrivedHandler(new DataArrivedHandler() {
			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				if (event.getData().getLength() == 0) { 
					idTipoDocumentoRepertorioUscitaItem.setValue((String) null);
					descTipoDocumentoRepertorioUscitaItem.setValue((String) null);
					flgTipoDocConVieRepertorioUscitaItem.setValue(false);	
					flgOggettoNonObbligRepertorioUscitaItem.setValue(false);						
				} else if (event.getData().getLength() == 1) {
					idTipoDocumentoRepertorioUscitaItem.setValue(event.getData().get(0).getAttribute("idTipoDocumento"));
					descTipoDocumentoRepertorioUscitaItem.setValue(event.getData().get(0).getAttribute("descTipoDocumento"));
					flgTipoDocConVieRepertorioUscitaItem.setValue(event.getData().get(0).getAttributeAsBoolean("flgTipoDocConVie"));
					flgOggettoNonObbligRepertorioUscitaItem.setValue(event.getData().get(0).getAttributeAsBoolean("flgOggettoNonObblig"));					
				}
			}
		});
		ListGrid idTipoDocumentoRepertorioUscitaPickListProperties = idTipoDocumentoRepertorioUscitaItem.getPickListProperties();
		idTipoDocumentoRepertorioUscitaPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				GWTRestDataSource idTipoDocumentoRepertorioUscitaDS = (GWTRestDataSource) idTipoDocumentoRepertorioUscitaItem.getOptionDataSource();
				idTipoDocumentoRepertorioUscitaDS.addParam("categoriaReg", "R");
				idTipoDocumentoRepertorioUscitaDS.addParam("siglaReg", repertorioUscitaItem.getValueAsString());		
				idTipoDocumentoRepertorioUscitaItem.setOptionDataSource(idTipoDocumentoRepertorioUscitaDS);
				idTipoDocumentoRepertorioUscitaItem.invalidateDisplayValueCache();
			}
		});
		idTipoDocumentoRepertorioUscitaItem.setPickListProperties(idTipoDocumentoRepertorioUscitaPickListProperties);
		
		skipSceltaTipologiaRepertorioUscitaItem = new CheckboxItem("skipSceltaTipologiaRepertorioUscita", setTitleAlign("Salta scelta tipologia", 200, false));
		skipSceltaTipologiaRepertorioUscitaItem.setStartRow(false);

		repertorioUscitaForm.setFields(
			supportoRepertorioUscitaItem,
			repertorioUscitaItem,
			skipSceltaRepertorioUscitaItem,
			idTipoDocumentoRepertorioUscitaItem,
			descTipoDocumentoRepertorioUscitaItem,
			flgTipoDocConVieRepertorioUscitaItem,
			flgOggettoNonObbligRepertorioUscitaItem,
			skipSceltaTipologiaRepertorioUscitaItem
		);
	}

	private void buildFormRegistroFatture(){
		
		registroFattureForm = new DynamicForm();
		registroFattureForm.setKeepInParentRect(true);
		registroFattureForm.setWrapItemTitles(false);
		registroFattureForm.setNumCols(5);
		registroFattureForm.setColWidths(10, 10, 10, 10, "*");
		registroFattureForm.setPadding(5);
		registroFattureForm.setAlign(Alignment.LEFT);
		registroFattureForm.setTop(50);
		registroFattureForm.setValuesManager(vm);
		
		descTipoDocumentoRegistroFattureItem = new HiddenItem("descTipoDocumentoRegistroFatture");
		flgTipoDocConVieRegistroFattureItem = new HiddenItem("flgTipoDocConVieRegistroFatture");
		flgOggettoNonObbligRegistroFattureItem = new HiddenItem("flgOggettoNonObbligRegistroFatture");
		
		GWTRestDataSource idTipoDocumentoRegistroFattureDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
		idTipoDocumentoRegistroFattureDS.addParam("categoriaReg", "I");
		idTipoDocumentoRegistroFattureDS.addParam("siglaReg", "FATTURE");

		idTipoDocumentoRegistroFattureItem = new SelectItem("idTipoDocumentoRegistroFatture") {
	
			@Override
			public void onOptionClick(Record record) {
				idTipoDocumentoRegistroFattureItem.setValue(record.getAttribute("idTipoDocumento"));
				descTipoDocumentoRegistroFattureItem.setValue(record.getAttribute("descTipoDocumento"));
				flgTipoDocConVieRegistroFattureItem.setValue(record.getAttributeAsBoolean("flgTipoDocConVie"));
				flgOggettoNonObbligRegistroFattureItem.setValue(record.getAttributeAsBoolean("flgOggettoNonObblig"));
			}
		};
		idTipoDocumentoRegistroFattureItem.setShowTitle(true);
		idTipoDocumentoRegistroFattureItem.setTitle(setTitleAlign("Tipologia documentale da preimpostare", 220, false));
		idTipoDocumentoRegistroFattureItem.setStartRow(true);
		idTipoDocumentoRegistroFattureItem.setWidth(300);
		idTipoDocumentoRegistroFattureItem.setColSpan(2);
		idTipoDocumentoRegistroFattureItem.setAlign(Alignment.CENTER);
		idTipoDocumentoRegistroFattureItem.setValueField("idTipoDocumento");
		idTipoDocumentoRegistroFattureItem.setDisplayField("descTipoDocumento");
		idTipoDocumentoRegistroFattureItem.setOptionDataSource(idTipoDocumentoRegistroFattureDS);
		idTipoDocumentoRegistroFattureItem.setAllowEmptyValue(true);
		
		skipSceltaTipologiaRegistroFattureItem = new CheckboxItem("skipSceltaTipologiaRegistroFatture", setTitleAlign("Salta scelta tipologia", 200, false));
		skipSceltaTipologiaRegistroFattureItem.setStartRow(false);
		
		registroFattureForm.setFields(
				idTipoDocumentoRegistroFattureItem,
				descTipoDocumentoRegistroFattureItem,
				flgTipoDocConVieRegistroFattureItem,
				flgOggettoNonObbligRegistroFattureItem,
				skipSceltaTipologiaRegistroFattureItem);
	}
	
	private void buildFormBozza(){
		
		bozzaForm = new DynamicForm();
		bozzaForm.setKeepInParentRect(true);
		bozzaForm.setWrapItemTitles(false);
		bozzaForm.setNumCols(5);
		bozzaForm.setColWidths(10, 10, 10, 10, "*");
		bozzaForm.setPadding(5);
		bozzaForm.setAlign(Alignment.LEFT);
		bozzaForm.setTop(50);
		bozzaForm.setValuesManager(vm);
		
		descTipoDocumentoBozzaItem = new HiddenItem("descTipoDocumentoBozza");
		flgTipoDocConVieBozzaItem = new HiddenItem("flgTipoDocConVieBozza");
		flgOggettoNonObbligBozzaItem = new HiddenItem("flgOggettoNonObbligBozza");
		
		supportoBozzeItem = new SelectItem("supportoBozze", setTitleAlign(getTitleSupporto(), 200, false));
		supportoBozzeItem.setDisplayField("value");
		supportoBozzeItem.setValueField("key");		
		supportoBozzeItem.setWidth(200);
		supportoBozzeItem.setAllowEmptyValue(true);
		LinkedHashMap<String, String> tipoSupportoBMap = new LinkedHashMap<String, String>();
		tipoSupportoBMap.put("cartaceo", "cartaceo");
		tipoSupportoBMap.put("digitale", "digitale");
		tipoSupportoBMap.put("misto", "misto");
		supportoBozzeItem.setValueMap(tipoSupportoBMap);
		supportoBozzeItem.setStartRow(true);
		supportoBozzeItem.setAllowEmptyValue(true);
		supportoBozzeItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ProtocollazioneUtil.isAttivoProtocolloWizard(null);
			}
		});

		
		GWTRestDataSource idTipoDocumentoBozzaDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
		
		idTipoDocumentoBozzaItem = new SelectItem("idTipoDocumentoBozza") {

			@Override
			public void onOptionClick(Record record) {
				idTipoDocumentoBozzaItem.setValue(record.getAttribute("idTipoDocumento"));
				descTipoDocumentoBozzaItem.setValue(record.getAttribute("descTipoDocumento"));
				flgTipoDocConVieBozzaItem.setValue(record.getAttributeAsBoolean("flgTipoDocConVie"));
				flgOggettoNonObbligBozzaItem.setValue(record.getAttributeAsBoolean("flgOggettoNonObblig"));				
			}
		};
		idTipoDocumentoBozzaItem.setShowTitle(true);
		idTipoDocumentoBozzaItem.setTitle(setTitleAlign("Tipologia documentale da preimpostare", 220, false));
		idTipoDocumentoBozzaItem.setStartRow(true);
		idTipoDocumentoBozzaItem.setWidth(300);
		idTipoDocumentoBozzaItem.setColSpan(2);
		idTipoDocumentoBozzaItem.setAlign(Alignment.CENTER);
		idTipoDocumentoBozzaItem.setValueField("idTipoDocumento");
		idTipoDocumentoBozzaItem.setDisplayField("descTipoDocumento");
		idTipoDocumentoBozzaItem.setOptionDataSource(idTipoDocumentoBozzaDS);
		idTipoDocumentoBozzaItem.setAllowEmptyValue(true);
		
		skipSceltaTipologiaBozzaItem = new CheckboxItem("skipSceltaTipologiaBozza", setTitleAlign("Salta scelta tipologia", 200, false));
		skipSceltaTipologiaBozzaItem.setStartRow(false);
		
		bozzaForm.setFields(
				supportoBozzeItem,
				idTipoDocumentoBozzaItem,
				descTipoDocumentoBozzaItem,
				flgTipoDocConVieBozzaItem,
				flgOggettoNonObbligBozzaItem,
				skipSceltaTipologiaBozzaItem);
	}
	
	private void buildFormProtPregresso(){
		
		protPregressoForm = new DynamicForm();
		protPregressoForm.setKeepInParentRect(true);
		protPregressoForm.setWrapItemTitles(false);
		protPregressoForm.setNumCols(5);
		protPregressoForm.setColWidths(10, 10, 10, 10, "*");
		protPregressoForm.setPadding(5);
		protPregressoForm.setAlign(Alignment.LEFT);
		protPregressoForm.setTop(50);
		protPregressoForm.setValuesManager(vm);
		
		descTipoDocumentoProtPregressoItem = new HiddenItem("descTipoDocumentoProtPregresso");
		codCategoriaAltraNumerazionePregressoItem = new HiddenItem("codCategoriaAltraNumerazionePregresso");
		siglaAltraNumerazionePregressoItem = new HiddenItem("siglaAltraNumerazionePregresso");
		flgTipoDocConVieProtPregressoItem = new HiddenItem("flgTipoDocConVieProtPregresso");
		flgOggettoNonObbligProtPregressoItem = new HiddenItem("flgOggettoNonObbligProtPregresso");
		
		GWTRestDataSource idTipoDocumentoPregressoDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
		idTipoDocumentoPregressoDS.addParam("categoriaReg", "PG");
		idTipoDocumentoPregressoDS.addParam("siglaReg", null);

		idTipoDocumentoProtPregressoItem = new SelectItem("idTipoDocumentoProtPregresso") {

			@Override
			public void onOptionClick(Record record) {
				idTipoDocumentoProtPregressoItem.setValue(record.getAttribute("idTipoDocumento"));
				descTipoDocumentoProtPregressoItem.setValue(record.getAttribute("descTipoDocumento"));
				codCategoriaAltraNumerazionePregressoItem.setValue(record.getAttribute("codCategoriaAltraNumerazione"));
				siglaAltraNumerazionePregressoItem.setValue(record.getAttribute("siglaAltraNumerazione"));
				flgTipoDocConVieProtPregressoItem.setValue(record.getAttributeAsBoolean("flgTipoDocConVie"));				
				flgOggettoNonObbligProtPregressoItem.setValue(record.getAttributeAsBoolean("flgOggettoNonObblig"));
			}
		};
		idTipoDocumentoProtPregressoItem.setShowTitle(true);
		idTipoDocumentoProtPregressoItem.setTitle(setTitleAlign("Tipologia documentale da preimpostare", 220, false));
		idTipoDocumentoProtPregressoItem.setStartRow(true);
		idTipoDocumentoProtPregressoItem.setWidth(300);
		idTipoDocumentoProtPregressoItem.setColSpan(2);
		idTipoDocumentoProtPregressoItem.setAlign(Alignment.CENTER);
		idTipoDocumentoProtPregressoItem.setValueField("idTipoDocumento");
		idTipoDocumentoProtPregressoItem.setDisplayField("descTipoDocumento");
		idTipoDocumentoProtPregressoItem.setOptionDataSource(idTipoDocumentoPregressoDS);
		idTipoDocumentoProtPregressoItem.setAllowEmptyValue(true);
		
		skipSceltaTipologiaProtPregressoItem = new CheckboxItem("skipSceltaTipologiaProtPregresso", setTitleAlign("Salta scelta tipologia", 200, false));
		skipSceltaTipologiaProtPregressoItem.setStartRow(false);
		
		protPregressoForm.setFields(
				idTipoDocumentoProtPregressoItem,
				descTipoDocumentoProtPregressoItem,
				codCategoriaAltraNumerazionePregressoItem,
				siglaAltraNumerazionePregressoItem,
				flgTipoDocConVieProtPregressoItem,
				flgOggettoNonObbligProtPregressoItem,
				skipSceltaTipologiaProtPregressoItem);
	}
	
	public void clearValues() {
		if(protEntrataForm != null) {
			protEntrataForm.clearValues();
		}
		if(protUscitaForm != null) {
			protUscitaForm.clearValues();
		}
		if(protInternaForm != null) {
			protInternaForm.clearValues();
		}
		if(repertorioEntrataForm != null) {
			repertorioEntrataForm.clearValues();
		}	
		if(repertorioInternoForm != null) {
			repertorioInternoForm.clearValues();
		}	
		if(repertorioUscitaForm != null) {
			repertorioUscitaForm.clearValues();
		}	
		if(registroFattureForm != null) {
			registroFattureForm.clearValues();
		}
		if(bozzaForm != null) {
			bozzaForm.clearValues();
		}		
		if(protPregressoForm != null) {
			protPregressoForm.clearValues();
		}
	}

	public void setValues(Record values) {
		if (values != null) {	
			vm.editRecord(values);
			if(idTipoDocumentoRepertorioEntrataItem != null) {
				idTipoDocumentoRepertorioEntrataItem.fetchData();	
			}
			if(idTipoDocumentoRepertorioInternoItem != null) {
				idTipoDocumentoRepertorioInternoItem.fetchData();	
			}
			if(idTipoDocumentoRepertorioUscitaItem != null) {
				idTipoDocumentoRepertorioUscitaItem.fetchData();	
			}
		} else {
			vm.editNewRecord();
		}
		vm.clearErrors(true);
	}
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}

	public void manageOnOkButtonClick(Record values) {

	}
	
	private String getTitleSupporto(){
		return "Supporto orig. da preimpostare";
	}
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}

}