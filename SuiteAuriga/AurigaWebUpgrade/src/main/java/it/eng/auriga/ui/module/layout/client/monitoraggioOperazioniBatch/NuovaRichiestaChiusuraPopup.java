/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.filter.OrganigrammaPopup;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class NuovaRichiestaChiusuraPopup extends ModalWindow {
	
	protected NuovaRichiestaChiusuraPopup _window;
	protected DynamicForm _form;
	protected ValuesManager vm;
	
	public DynamicForm getForm() {
		return _form;
	}
	
	// SelectItem
	private SelectItem tipoOggettiDaChiudereItem;
	private SelectItem tipoPeriodoAperturaItem;
	private SelectItem tipoPeriodoSenzaOperazioniItem;
	private SelectItem caselleItem;
	
	//DIM_ORGANIGRAMMA_NONSTD = true
	private SelectItemWithDisplay struttureSTDItem;
	
	// TextItem
	//DIM_ORGANIGRAMMA_NONSTD = false
	private TextItem listaDescrizioniItem;
	
	// TextAreaItem
	private TextAreaItem motivazioneRichiestaItem;
	private TextAreaItem noteRichiestaItem;
	
	// DateItem
	private DateItem dataInvioDaItem;
	private DateItem dataInvioAItem;	
	private DateTimeItem dtOperazioneItem;
	
	// NumericItem
	private NumericItem periodoAperturaItem;
	private NumericItem periodoSenzaOperazioniItem;
	
	// ButtonItem
	private ButtonItem confermaButton;
	private ButtonItem annullaButton;
	private ImgButtonItem viewStruttureButtonItem;
	
	// HiddenItem 
	private HiddenItem struttureNONSTDItem;
	private HiddenItem listaOrganigrammaItem;
	
	// Map
	private Map<String, String> property;
	
	public NuovaRichiestaChiusuraPopup(Record pListRecord){
		
		super("nuovaRichiestaChiusura", true);
		
		_window = this;
		
		vm = new ValuesManager();
		
		setTitle("Nuova richiesta di chiusura");
		
		setAutoCenter(true);
		setHeight(440);
		setWidth(630);	
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);			
		
		_form = new DynamicForm();													
		_form.setValuesManager(vm);
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(6);
		_form.setColWidths(10,100,10,100,10,"*");		
		_form.setCellPadding(5);		
		_form.setWrapItemTitles(false);	
		// _form.setCellBorder(1);
		
		// *********************************************
		// Tipo di oggetti da chiudere		
		// *********************************************
		tipoOggettiDaChiudereItem = new SelectItem("tipoOggettiDaChiudere", "Tipo di oggetti da chiudere");
		tipoOggettiDaChiudereItem.setAttribute("obbligatorio", true);
		tipoOggettiDaChiudereItem.setWidth(130);
		LinkedHashMap<String, String> tipoOggettiDaChiudereValueMap = new LinkedHashMap<String, String>();
		if(Layout.isPrivilegioAttivo("ROM/M/CHM")){
			tipoOggettiDaChiudereValueMap.put("M", "e-mail");
		}
		if(Layout.isPrivilegioAttivo("ROM/M/CHF")){
			tipoOggettiDaChiudereValueMap.put("F", "fascicoli");
		}		
		tipoOggettiDaChiudereItem.setValueMap(tipoOggettiDaChiudereValueMap);
		
		if(Layout.isPrivilegioAttivo("ROM/M/CHF")){
			tipoOggettiDaChiudereItem.setDefaultValue("F");
		}
		if(Layout.isPrivilegioAttivo("ROM/M/CHM")){
			tipoOggettiDaChiudereItem.setDefaultValue("M");
		}
		
		tipoOggettiDaChiudereItem.setColSpan(5);
		tipoOggettiDaChiudereItem.setStartRow(true);
		tipoOggettiDaChiudereItem.setAllowEmptyValue(false);
		tipoOggettiDaChiudereItem.addChangedHandler(new ChangedHandler() {
					@Override
					public void onChanged(ChangedEvent event) {
						markForRedraw();
					}
				});
				
		
		// *********************************************
		// Caselle di ricezione o invio delle mail
		// *********************************************
		caselleItem = new SelectItem("caselle", "Caselle ricezione/invio");
		caselleItem.setDisplayField("value");
		caselleItem.setValueField("key");
		GWTRestDataSource accounts = new GWTRestDataSource("AccountInvioEmailDatasource");
		accounts.addParam("tipoAccount", "ID");
		caselleItem.setOptionDataSource(accounts);
		caselleItem.setMultiple(true);
		caselleItem.setStartRow(true);	
		caselleItem.setWidth(350);
		caselleItem.setColSpan(5);
		caselleItem.setDefaultValue((String) null);				
		caselleItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isTipoOggettoEmail();
			}
		});	
		
				
		// *********************************************
		// UO Assegnazione
		// *********************************************	
		
		// L'organigramma viene visualizzato in una POPUP (DIM_ORGANIGRAMMA_NONSTD = true)
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			property = new HashMap<String, String>();
			property.put("datasourceName", "SelectOrganigrammaDatasource");
			property.put("flgNodoType", "UO;SV");
			property.put("showCheckIncludi", "true");
			property.put("multiple", "true");
			
			struttureNONSTDItem = new HiddenItem("struttureNONSTD");
			listaOrganigrammaItem = new HiddenItem("listaOrganigramma");
			
			listaDescrizioniItem = new TextItem("listaDescrizioni","Assegnate a");
			listaDescrizioniItem.setCanEdit(false);
			listaDescrizioniItem.setWidth(350);	
			listaDescrizioniItem.setStartRow(true);	
			listaDescrizioniItem.setColSpan(4);
			
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
			viewStruttureButtonItem.setAlign(Alignment.LEFT);   
			viewStruttureButtonItem.setAlwaysEnabled(true);     
			viewStruttureButtonItem.setIconWidth(16);
			viewStruttureButtonItem.setIconHeight(16);
			viewStruttureButtonItem.addIconClickHandler(new IconClickHandler() {					
				@Override
				public void onIconClick(IconClickEvent event) {
					Record lRecord = new Record(_form.getValues());
					OrganigrammaPopup lOrganigrammaPopup = new OrganigrammaPopup("Modifica filtro su organigramma", property, lRecord) {
						@Override
						public void onClickOkButton(Record values) {						
							setFormValuesFromRecord(values);	
							markForRedraw();
						}					
					};
					lOrganigrammaPopup.show();
				}
			});
		}
		
		// L'organigramma viene visualizzato in una SELECT
		else{			
			SelectGWTRestDataSource strutture = new SelectGWTRestDataSource("SelectOrganigrammaSTDDatasource", "chiave", FieldType.TEXT, new String[] { "codice", "descrizione" }, true);
			struttureSTDItem = new SelectItemWithDisplay("struttureSTD", "Assegnate a" , strutture);		
			ListGridField chiaveField = new ListGridField("chiave");
			chiaveField.setHidden(true);
			ListGridField codiceField = new ListGridField("codice", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
			codiceField.setWidth(70);
			ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().protocollazione_detail_denominazioneItem_title());
			descrizioneField.setWidth("*");
			struttureSTDItem.setPickListFields(chiaveField, codiceField, descrizioneField);
			struttureSTDItem.setValueField("chiave");		
			struttureSTDItem.setCachePickListResults(false);
			struttureSTDItem.setMultiple(true);
			struttureSTDItem.setStartRow(true);	
			struttureSTDItem.setWidth(350);
			struttureSTDItem.setColSpan(4);
		}
		
		// *********************************************
		// Data operazione
		// *********************************************
		dtOperazioneItem = new DateTimeItem("dtOperazione", "Data e ora a cui effettuare l'operazione");
		dtOperazioneItem.setColSpan(5);
		dtOperazioneItem.setStartRow(true);
		dtOperazioneItem.setColSpan(1);
		dtOperazioneItem.setRequired(true);
		
		String data = DateTimeFormat.getFormat("dd/MM/yyyy").format(new Date()) + " 23:59";
		dtOperazioneItem.setDefaultValue(data);
		
					 
		// *********************************************
		// Range Data di invio
		// *********************************************
		dataInvioDaItem = new DateItem("dataInvioDa", "Data di invio dal");
		dataInvioDaItem.setWidth(140);
		dataInvioDaItem.setStartRow(true);
		
		dataInvioAItem = new DateItem("dataInvioA", "al");
		dataInvioAItem.setWidth(140);
		dataInvioAItem.setStartRow(false);
		dataInvioAItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isTipoOggettoEmail();
			}
		});
		
		// *********************************************
		// Periodo apertura
		// *********************************************
		periodoAperturaItem = new NumericItem("periodoApertura", "Aperte da oltre");
		periodoAperturaItem.setStartRow(true);
		periodoAperturaItem.setLength(4);
		periodoAperturaItem.setWidth(170);
		
		periodoAperturaItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isTipoOggettoEmail();
			}
		});
		
		// *********************************************
		// Tipo periodo apertura
		// *********************************************
		tipoPeriodoAperturaItem = new SelectItem("tipoPeriodoApertura");
		tipoPeriodoAperturaItem.setShowTitle(false);
		LinkedHashMap<String, String> tipoPeriodoAperturaValueMap = new LinkedHashMap<String, String>();		
		tipoPeriodoAperturaValueMap.put("M", "mesi");
		tipoPeriodoAperturaValueMap.put("G", "giorni");		
		tipoPeriodoAperturaItem.setValueMap(tipoPeriodoAperturaValueMap);
		tipoPeriodoAperturaItem.setDefaultValue("M");
		tipoPeriodoAperturaItem.setStartRow(false);
		tipoPeriodoAperturaItem.setAllowEmptyValue(false);
		tipoPeriodoAperturaItem.setColSpan(2);
		tipoPeriodoAperturaItem.setWidth(70);
		tipoPeriodoAperturaItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isTipoOggettoEmail();
			}
		});
				
		// *********************************************
		// Periodo senza operazioni
		// *********************************************
		periodoSenzaOperazioniItem = new NumericItem("periodoSenzaOperazioni", "Senza operazioni da oltre");
		periodoSenzaOperazioniItem.setStartRow(true);
		periodoSenzaOperazioniItem.setLength(4);
		periodoSenzaOperazioniItem.setWidth(170);
		periodoSenzaOperazioniItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isTipoOggettoEmail();
			}
		});
		
		// *********************************************
		// Tipo periodo senza operazioni
		// *********************************************
		tipoPeriodoSenzaOperazioniItem = new SelectItem("tipoPeriodoSenzaOperazioni");
		tipoPeriodoSenzaOperazioniItem.setShowTitle(false);
		LinkedHashMap<String, String> tipoPeriodoSenzaOperazioniValueMap = new LinkedHashMap<String, String>();		
		tipoPeriodoSenzaOperazioniValueMap.put("M", "mesi");
		tipoPeriodoSenzaOperazioniValueMap.put("G", "giorni");		
		tipoPeriodoSenzaOperazioniItem.setValueMap(tipoPeriodoSenzaOperazioniValueMap);
		tipoPeriodoSenzaOperazioniItem.setDefaultValue("M");
		tipoPeriodoSenzaOperazioniItem.setStartRow(false);
		tipoPeriodoSenzaOperazioniItem.setAllowEmptyValue(false);
		tipoPeriodoSenzaOperazioniItem.setColSpan(2);
		tipoPeriodoSenzaOperazioniItem.setWidth(70);
		tipoPeriodoSenzaOperazioniItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isTipoOggettoEmail();
			}
		});
		
		// *********************************************
		// Motivazione
		// *********************************************
		motivazioneRichiestaItem = new TextAreaItem("motivazioneRichiesta", "Motivazione richiesta");
		motivazioneRichiestaItem.setColSpan(5);
		motivazioneRichiestaItem.setStartRow(true);
		motivazioneRichiestaItem.setLength(4000);
		motivazioneRichiestaItem.setHeight(40);
		motivazioneRichiestaItem.setWidth(350);

		// *********************************************
		// Note
		// *********************************************
		noteRichiestaItem = new TextAreaItem("noteRichiesta", "Note richiesta");
		noteRichiestaItem.setColSpan(5);
		noteRichiestaItem.setStartRow(true);
		noteRichiestaItem.setLength(4000);
		noteRichiestaItem.setHeight(40);
		noteRichiestaItem.setWidth(350);
		
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			_form.setFields(new FormItem[]{tipoOggettiDaChiudereItem, 
                                           caselleItem,
                                           struttureNONSTDItem,listaOrganigrammaItem,listaDescrizioniItem, viewStruttureButtonItem,
                                           dtOperazioneItem, 
                                           dataInvioDaItem,
                                           dataInvioAItem,
                                           periodoAperturaItem,
                                           tipoPeriodoAperturaItem,
                                           periodoSenzaOperazioniItem,
                                           tipoPeriodoSenzaOperazioniItem,
                                           motivazioneRichiestaItem, 
                                           noteRichiestaItem});
		}
		else{
			_form.setFields(new FormItem[]{tipoOggettiDaChiudereItem, 
                    caselleItem,
                    struttureSTDItem,
                    dtOperazioneItem, 
                    dataInvioDaItem,
                    dataInvioAItem,
                    periodoAperturaItem,
                    tipoPeriodoAperturaItem,
                    periodoSenzaOperazioniItem,
                    tipoPeriodoSenzaOperazioniItem,
                    motivazioneRichiestaItem, 
                    noteRichiestaItem});
		}

		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(_form.validate()) {				
					final Record formRecord = new Record(_form.getValues());
					int periodoApertura        = formRecord.getAttribute("periodoApertura") != null        && !formRecord.getAttribute("periodoApertura").isEmpty() ? Integer.valueOf(formRecord.getAttribute("periodoApertura")) : 0;
					int periodoSenzaOperazioni = formRecord.getAttribute("periodoSenzaOperazioni") != null && !formRecord.getAttribute("periodoSenzaOperazioni").isEmpty() ? Integer.valueOf(formRecord.getAttribute("periodoSenzaOperazioni")) : 0;
					if(periodoApertura>=1 || periodoSenzaOperazioni>=1){
						onClickOkButton(formRecord, new DSCallback() {			
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {							
								_window.markForDestroy();
							}
						});	
					}					
					else{
						SC.say("ATTENZIONE ! Uno dei filtri 'Aperte da oltre' e 'Senza operazioni da oltre' deve essere insetito con un valore >=1");
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
				
				_window.markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		 		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(_form);
		
		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
				
		setBody(portletLayout);
		
		setIcon("lookup/nuovaRichiestaChiusura.png");
	}

	public boolean isTipoOggettoEmail() {
		String tipo = tipoOggettiDaChiudereItem.getValueAsString() != null ? tipoOggettiDaChiudereItem.getValueAsString() : "";
		return tipo.equals("M");
	}
    
    public void setFormValuesFromRecord(Record record) {
    	
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
			record.setAttribute("struttureNONSTD", listaOrganigramma);
			record.setAttribute("listaDescrizioni", listaDescrizioni);
			record.setAttribute("listaDescrizioniHtml", listaDescrizioniHtml);
			editRecord(record);
		}
		
	}
    
    private void editRecord(Record record) {
		vm.editRecord(record);
		vm.rememberValues();
		Record startRecord = new Record(vm.getValues());
		for (DynamicForm form : vm.getMembers()) {
			form.markForRedraw();
			if (form.getDetailSection() != null) {
				form.getDetailSection().redrawDetailSectionHeaderTitle();
			}
		}
	}
    
    public abstract void onClickOkButton(Record object, DSCallback callback);
  
}
