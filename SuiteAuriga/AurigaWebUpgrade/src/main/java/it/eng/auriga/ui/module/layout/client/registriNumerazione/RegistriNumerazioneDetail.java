/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.registriNumerazione;


import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.Canvas;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import java.util.HashMap;
import java.util.Map;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Side;


public class RegistriNumerazioneDetail extends CustomDetail{

	protected TabSet tabSet;
	protected Tab tabDatiPrincipali;
	protected VLayout layoutTabDatiPrincipali;

	protected String rowid;
	protected LinkedHashMap<String, String> attributiAddTabs;
	protected HashMap<String, VLayout> attributiAddLayouts;
	protected HashMap<String, AttributiDinamiciDetail> attributiAddDetails;
	
	
	// DynamicForm
	private DynamicForm datiPrincipaliRegistroNumForm;
	private DynamicForm abilitazioniForm;	
	private DynamicForm tipiDocAmmEscForm;
	
	// DetailSection
	private DetailSection abilitazioniSection;
	private DetailSection tipiDocAmmEscSection;
		
	// HiddenItem
	private HiddenItem idTipoRegNumItem;
	private HiddenItem flgAmmEscXTipiDocItem;
	
	// TextItem 
	private TextItem siglaTipoRegNumItem;
	private TextItem descrizioneItem;
	private TextItem gruppoRegistriAppItem;
	
	// DateItem
	private DateItem dtInizioVldItem;
	private DateItem dtFineVldItem;
	private DateItem dtUltimaRegItem;
	private DateItem dtUltimoCambioStatoItem;
	
	// SelectItem
	private SelectItem flgStatoRegistroItem;
	private SelectItem codCategoriaItem;
	
	// NumericItem
	private NumericItem nrAnniRinnovaNumerazioneItem;
	private NumericItem nroInizialeItem;
	private NumericItem nrUltimaRegItem;
	
	// AnnoItem
	private AnnoItem annoInizioNumItem;

	// CheckboxItem
	private CheckboxItem flgRichAbilVisItem;
	private CheckboxItem flgRichAbilXAssegnItem;
	private CheckboxItem flgInternaItem;
	private CheckboxItem flgNumerazioneSenzaContinuitaItem;
	private CheckboxItem flgRestrizioniVersoRegEItem;
	private CheckboxItem flgRestrizioniVersoRegUItem;
	private CheckboxItem flgRestrizioniVersoRegIItem;
	private CheckboxItem flgCtrAbilUOMittItem;
	
	// TitleStaticTextItem
	private TitleStaticTextItem flgRestrizioniVersoRegLabelItem;
	
	// ReplicableItem
	private TipiDocAmmEscItem tipiDocAmmEscItem;
	
	RegistriNumerazioneDetail _instance;
	
	public RegistriNumerazioneDetail(String nomeEntita) {
		
		super(nomeEntita);
		
		_instance = this;		
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		createTabSet();

		setMembers(tabSet);
	}
	
   
	// SEZIONE DATI PRINCIPALI
	private void buildDatiPrincipaliSection(){
		
		// FORM MAIN
		datiPrincipaliRegistroNumForm = new DynamicForm();
		datiPrincipaliRegistroNumForm.setValuesManager(vm);
		datiPrincipaliRegistroNumForm.setWrapItemTitles(false);
		datiPrincipaliRegistroNumForm.setHeight("5");
		datiPrincipaliRegistroNumForm.setPadding(5);
		datiPrincipaliRegistroNumForm.setNumCols(10);
		datiPrincipaliRegistroNumForm.setColWidths(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
			
		// nascosti
		idTipoRegNumItem       = new HiddenItem("idTipoRegNum");
		flgAmmEscXTipiDocItem  = new HiddenItem("flgAmmEscXTipiDoc");
		
		// categoria
		codCategoriaItem = new SelectItem("codCategoria", I18NUtil.getMessages().registri_numerazione_detail_codCategoriaItem());
		codCategoriaItem.setClearable(false);
		codCategoriaItem.setAllowEmptyValue(false);
		codCategoriaItem.setRequired(true);
		codCategoriaItem.setWidth(160);
		codCategoriaItem.setColSpan(9);
		codCategoriaItem.setDefaultValue("R");
		codCategoriaItem.setStartRow(true);
		codCategoriaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				
				setFlgNumerazioneSenzaContinuitaReadOnly(editing);
				
				boolean isRepertorio = (codCategoriaItem.getValueAsString() != null && "R".equals(codCategoriaItem.getValueAsString()));
				
				// Se e' un repertorio (R) il check flgNumerazioneSenzaContinuitaItem è spuntato e NON editabile.
				if(isRepertorio){
					flgNumerazioneSenzaContinuitaItem.setValue(true);
				}
				else{
					flgNumerazioneSenzaContinuitaItem.clearValue();
				}				
				markForRedraw();
			}
		});

	    // sigla
		siglaTipoRegNumItem = new TextItem("siglaTipoRegNum", I18NUtil.getMessages().registri_numerazione_detail_siglaTipoRegNumItem());
		siglaTipoRegNumItem.setWidth(160);
		siglaTipoRegNumItem.setLength(30);
		siglaTipoRegNumItem.setColSpan(9);
		siglaTipoRegNumItem.setRequired(true);
		
		// descrizione
		descrizioneItem = new TextItem("descrizione", I18NUtil.getMessages().registri_numerazione_detail_descrizioneItem());
		descrizioneItem.setWidth(600);
		descrizioneItem.setColSpan(9);
		descrizioneItem.setRequired(true);
		descrizioneItem.setStartRow(true);
		
		SpacerItem spacer1 = new SpacerItem();
		spacer1.setWidth(20);
		spacer1.setStartRow(true);

		SpacerItem spacer2 = new SpacerItem();
		spacer2.setWidth(30);

		// Numerazione data in automatico a sistema
		flgInternaItem = new CheckboxItem("flgInterna", I18NUtil.getMessages().registri_numerazione_detail_flgInternaItem());		
		flgInternaItem.setColSpan(9);
		flgInternaItem.setTitleOrientation(TitleOrientation.RIGHT);
		flgInternaItem.setDefaultValue(true);
		flgInternaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		// Numerazione senza soluzione di continuità
		flgNumerazioneSenzaContinuitaItem = new CheckboxItem("flgNumerazioneSenzaContinuita", I18NUtil.getMessages().registri_numerazione_detail_flgNumerazioneSenzaContinuitaItem());
		flgNumerazioneSenzaContinuitaItem.setColSpan(9);
		flgNumerazioneSenzaContinuitaItem.setTitleOrientation(TitleOrientation.RIGHT);
		flgNumerazioneSenzaContinuitaItem.setDefaultValue(true);
		
		// Rinnovo numerazione (ogni quanti anni)
		nrAnniRinnovaNumerazioneItem = new NumericItem("nrAnniRinnovaNumerazione", I18NUtil.getMessages().registri_numerazione_detail_nrAnniRinnovaNumerazioneItem());
		nrAnniRinnovaNumerazioneItem.setWidth(70);
		nrAnniRinnovaNumerazioneItem.setLength(3);
		nrAnniRinnovaNumerazioneItem.setDefaultValue(1);
		nrAnniRinnovaNumerazioneItem.setColSpan(9);
		nrAnniRinnovaNumerazioneItem.setWrapTitle(true);
		nrAnniRinnovaNumerazioneItem.setRequired(true);
		nrAnniRinnovaNumerazioneItem.setStartRow(true);
		
		// Anno inizio numerazione
		annoInizioNumItem = new AnnoItem("annoInizioNum", I18NUtil.getMessages().registri_numerazione_detail_annoInizioNumerazioneItem());
		annoInizioNumItem.setWidth(70);
		annoInizioNumItem.setRequired(true);
		annoInizioNumItem.setColSpan(9);	
		Integer anno = Integer.parseInt(DateTimeFormat.getFormat("yyyy").format(new Date()));
		annoInizioNumItem.setDefaultValue(anno);
		
		// 1° numero da dare
		nroInizialeItem = new NumericItem("nroIniziale", I18NUtil.getMessages().registri_numerazione_detail_nroInizialeItem());
		nroInizialeItem.setWidth(70);
		nroInizialeItem.setColSpan(9);
		nroInizialeItem.setDefaultValue(1);
		nroInizialeItem.setStartRow(true);		
		nroInizialeItem.setAttribute("obbligatorio", true);
		nroInizialeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				// E' obbligatorio quando si e' in NEW ed e' visibile
				return isNewMode() && isFlgInternaChecked();
			}
		}));
		
		// Si vede solo se “Numerazione data in automatico a sistema” è spuntato
		nroInizialeItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isFlgInternaChecked();
			}
		});

		// Ultimo numero
		nrUltimaRegItem = new NumericItem("nrUltimaReg", I18NUtil.getMessages().registri_numerazione_detail_nrUltimaRegItem());
		nrUltimaRegItem.setWidth(70);
		nrUltimaRegItem.setColSpan(9);
		nrUltimaRegItem.setStartRow(true);
		nrUltimaRegItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
                // Si vede solo quando si modifica/visualizza registro già inserito
				return !isNewMode();
			}
		});

		// Data ultimo numero
		dtUltimaRegItem = new DateItem("dtUltimaReg", I18NUtil.getMessages().registri_numerazione_detail_dtUltimaRegItem());		
		dtUltimaRegItem.setWidth(10);
		dtUltimaRegItem.setColSpan(9);
		dtUltimaRegItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
                // Si vede solo quando si modifica/visualizza registro già inserito
				return !isNewMode();
			}
		});

		
		// "Per registrazioni"		
		flgRestrizioniVersoRegLabelItem = new TitleStaticTextItem("Per registrazioni :", 100);
		flgRestrizioniVersoRegLabelItem.setName("flgRestrizioniVersoRegLabel");
		flgRestrizioniVersoRegLabelItem.setStartRow(true);
		
		// Per registrazioni (entrata) 
		flgRestrizioniVersoRegEItem = new CheckboxItem("flgRestrizioniVersoRegE", I18NUtil.getMessages().registri_numerazione_detail_flgRestrizioniVersoRegEItem());
		flgRestrizioniVersoRegEItem.setWidth(10);
		flgRestrizioniVersoRegEItem.setDefaultValue(true);
		flgRestrizioniVersoRegEItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		// Per registrazioni (uscita) 
		flgRestrizioniVersoRegUItem = new CheckboxItem("flgRestrizioniVersoRegU", I18NUtil.getMessages().registri_numerazione_detail_flgRestrizioniVersoRegUItem());
		flgRestrizioniVersoRegUItem.setWidth(10);
		flgRestrizioniVersoRegUItem.setDefaultValue(true);
		flgRestrizioniVersoRegUItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		// Per registrazioni (interna) 
		flgRestrizioniVersoRegIItem = new CheckboxItem("flgRestrizioniVersoRegI", I18NUtil.getMessages().registri_numerazione_detail_flgRestrizioniVersoRegIItem());
		flgRestrizioniVersoRegIItem.setWidth(10);
		flgRestrizioniVersoRegIItem.setDefaultValue(true);
		flgRestrizioniVersoRegIItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		// Selezionato in base alla U.O. mittente della registrazione
		flgCtrAbilUOMittItem = new CheckboxItem("flgCtrAbilUOMitt", I18NUtil.getMessages().registri_numerazione_detail_flgCtrAbilUOMittItem());	
		flgCtrAbilUOMittItem.setColSpan(9);
		flgCtrAbilUOMittItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		flgCtrAbilUOMittItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				// Il campo deve essere visibile SOLO se almeno uno dei check “in Uscita” e “Interne” è spuntato
				boolean visibile = isFlgRestrizioniVersoRegUChecked() || isFlgRestrizioniVersoRegIChecked();
				// Se non e' visibile tolgo il check 
				if(!visibile){
					flgCtrAbilUOMittItem.setValue(false);
					gruppoRegistriAppItem.redraw();
				}
				return visibile;
			}
		});

		// Gruppo di registri
		gruppoRegistriAppItem = new TextItem("gruppoRegistriApp", I18NUtil.getMessages().registri_numerazione_detail_gruppoRegistriAppItem());
		gruppoRegistriAppItem.setWidth(600);
		gruppoRegistriAppItem.setColSpan(9);
		gruppoRegistriAppItem.setStartRow(true);
		gruppoRegistriAppItem.setAttribute("obbligatorio", true);
		gruppoRegistriAppItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isFlgCtrAbilUOMittChecked();
			}
		}));
		gruppoRegistriAppItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isFlgCtrAbilUOMittChecked();
			}
		});
		
		// Stato
		flgStatoRegistroItem = new SelectItem("flgStatoRegistro", I18NUtil.getMessages().registri_numerazione_detail_flgStatoRegistroItem());
		LinkedHashMap<String, String> statoValueMap = new LinkedHashMap<String, String>();
		statoValueMap.put("A", I18NUtil.getMessages().registri_numerazione_detail_flgStatoRegistroItem_aperto());
		statoValueMap.put("C", I18NUtil.getMessages().registri_numerazione_detail_flgStatoRegistroItem_chiuso());
		flgStatoRegistroItem.setValueMap(statoValueMap);
		flgStatoRegistroItem.setAllowEmptyValue(false);
		flgStatoRegistroItem.setWidth(80);
		flgStatoRegistroItem.setRequired(true);
		flgStatoRegistroItem.setDefaultValue("A");
		flgStatoRegistroItem.setStartRow(true);

		// Data ultimo cambio stato
		dtUltimoCambioStatoItem = new DateItem("dtUltimoCambioStato", I18NUtil.getMessages().registri_numerazione_detail_dtUltimoCambioStatoItem());		
		dtUltimoCambioStatoItem.setWidth(10);
		dtUltimoCambioStatoItem.setTitleColSpan(2);
				
		// Validità dal
		dtInizioVldItem = new DateItem("dtInizioVld", I18NUtil.getMessages().registri_numerazione_detail_dtInizioVldItem());		
		dtInizioVldItem.setWidth(10);
		dtInizioVldItem.setColSpan(2);
		dtInizioVldItem.setRequired(true);
		dtInizioVldItem.setStartRow(true);
		
		//Integer dataodierna = Integer.parseInt(DateTimeFormat.getFormat("yyyy").format(new Date()));
		dtInizioVldItem.setDefaultValue(new Date());
		
		// Validità al
		dtFineVldItem = new DateItem("dtFineVld", I18NUtil.getMessages().registri_numerazione_detail_dtFineVldItem());		
		dtFineVldItem.setWidth(10);
		dtFineVldItem.setColSpan(2);
				
		datiPrincipaliRegistroNumForm.setItems(
                        					   // nascosti
                		   					   idTipoRegNumItem,
                		   					   flgAmmEscXTipiDocItem,                		   				
             		   
						             		   // visibili
						             		   codCategoriaItem,
						             		   siglaTipoRegNumItem,
						             		   descrizioneItem,
						             		   spacer1, flgInternaItem,
						             		   spacer1, flgNumerazioneSenzaContinuitaItem,
						             		   nrAnniRinnovaNumerazioneItem,
						             		   annoInizioNumItem,				                    		   
						             		   nroInizialeItem,
						             		   nrUltimaRegItem,
						             		   dtUltimaRegItem,						             		   
						             		   flgRestrizioniVersoRegLabelItem, flgRestrizioniVersoRegEItem , flgRestrizioniVersoRegUItem , flgRestrizioniVersoRegIItem, spacer2,spacer2,spacer2,spacer2,spacer2, spacer2,						             		  
						             		   spacer1, flgCtrAbilUOMittItem,
						             		   gruppoRegistriAppItem,
						             		   flgStatoRegistroItem ,						             		   
						             		   dtUltimoCambioStatoItem,
						             		   dtInizioVldItem ,
						             		   dtFineVldItem						             		   
                        					);				
	}
	
	// SEZIONE TIPI DOCUMENTI AMMESSI
	private void buildTipiDocAmmEscSection(){
	
		// Lista con i tipi di documenti ammessi
		tipiDocAmmEscForm = new DynamicForm();
		tipiDocAmmEscForm.setValuesManager(vm);
		tipiDocAmmEscForm.setWidth("100%");
		tipiDocAmmEscForm.setHeight("5");
		tipiDocAmmEscForm.setPadding(5);
		tipiDocAmmEscForm.setWrapItemTitles(false);
		tipiDocAmmEscForm.setNumCols(2);
		tipiDocAmmEscForm.setColWidths(10,"*");
		
		tipiDocAmmEscItem = new TipiDocAmmEscItem();
		tipiDocAmmEscItem.setName("listaTipiDocAmmEsc");
		tipiDocAmmEscItem.setStartRow(true);
		tipiDocAmmEscItem.setColSpan(2);
		tipiDocAmmEscItem.setCanEdit(true);
		tipiDocAmmEscItem.setShowTitle(false);
		tipiDocAmmEscItem.setAttribute("obbligatorio", false);
		
		tipiDocAmmEscForm.setFields(tipiDocAmmEscItem);
		
		tipiDocAmmEscSection = new DetailSection(I18NUtil.getMessages().registri_numerazione_detail_tipiDocAmmEscSection_title(), true, true, false, tipiDocAmmEscForm);
	}

	// SEZIONE ABILITAZIONI
	private void buildAbilitazioniSection(){
		
		// FORM ABILITAZIONI
		abilitazioniForm = new DynamicForm();
		abilitazioniForm.setValuesManager(vm);
		abilitazioniForm.setHeight("5");
		abilitazioniForm.setPadding(5);
		abilitazioniForm.setNumCols(2);
		abilitazioniForm.setColWidths(10, 10);

		flgRichAbilVisItem = new CheckboxItem("flgRichAbilVis", I18NUtil.getMessages().registri_numerazione_detail_flgRichAbilXVisualizzItem());
		flgRichAbilVisItem.setStartRow(true);
		flgRichAbilVisItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("CTRL_TP_REG_X_VIS");
			}
		});
		
		flgRichAbilXAssegnItem = new CheckboxItem("flgRichAbilXAssegn", I18NUtil.getMessages().registri_numerazione_detail_flgRichAbilXNumerareItem());
		flgRichAbilXAssegnItem.setStartRow(true);

		abilitazioniForm.setItems(flgRichAbilVisItem, flgRichAbilXAssegnItem);
		
		abilitazioniSection = new DetailSection(I18NUtil.getMessages().registri_numerazione_detail_Abilitazioni(),true, true, false, abilitazioniForm);
	}
	
		
	
	private void setFlgNumerazioneSenzaContinuitaReadOnly(boolean canEdit){	
		// Se sono in EDIT 
		if (canEdit){
			// Se la categoria = R
			boolean isRepertorio = (codCategoriaItem.getValueAsString() != null && "R".equals(codCategoriaItem.getValueAsString()));
			if (isRepertorio) {
			    //  è solo read-only	
				flgNumerazioneSenzaContinuitaItem.setCanEdit(false);
			}
			else{
				// altrimenti e' in edit-mode
				flgNumerazioneSenzaContinuitaItem.setCanEdit(true);
			}
		}
	}
	
	private void setNroInizialeReadOnly(boolean canEdit){			
		// Se sono in EDIT 
		if (canEdit){			
			// Se il registro ha già valorizzato UltimoNroGenearatoOut 
			if (nrUltimaRegItem.getValue()!=null && !nrUltimaRegItem.getValue().toString().equalsIgnoreCase("")){
				//  è solo read-only
				nroInizialeItem.setCanEdit(false);	
			}			
			else{
				// altrimenti e' in edit-mode
				nroInizialeItem.setCanEdit(true);
			}
		}		
	}
	
	protected void setCodCategoriaValueMap(String modeIn) {
		LinkedHashMap<String, String> codCategoriaMap = new LinkedHashMap<String, String>();
		codCategoriaMap.put("R",  "Repertorio");
		codCategoriaMap.put("I",  "Altra numerazione");		
		if (modeIn.equalsIgnoreCase("view") || modeIn.equalsIgnoreCase("edit")) {
			codCategoriaMap.put("PG", "Protocollo Generale");
			codCategoriaMap.put("PP", "Protocollo particolare / di settore");
		}
		codCategoriaItem.setValueMap(codCategoriaMap);
		if (datiPrincipaliRegistroNumForm != null) {
			datiPrincipaliRegistroNumForm.markForRedraw();
		}
	}
	
	private boolean isFlgRestrizioniVersoRegEChecked(){
		return  (flgRestrizioniVersoRegEItem.getValue() != null && !flgRestrizioniVersoRegEItem.getValue().toString().equalsIgnoreCase("") &&  flgRestrizioniVersoRegEItem.getValue().toString().equalsIgnoreCase("true"));
	}

	private boolean isFlgRestrizioniVersoRegUChecked(){
		return  (flgRestrizioniVersoRegUItem.getValue() != null && !flgRestrizioniVersoRegUItem.getValue().toString().equalsIgnoreCase("") &&  flgRestrizioniVersoRegUItem.getValue().toString().equalsIgnoreCase("true"));
	}

	private boolean isFlgRestrizioniVersoRegIChecked(){
		return  (flgRestrizioniVersoRegIItem.getValue() != null && !flgRestrizioniVersoRegIItem.getValue().toString().equalsIgnoreCase("") &&  flgRestrizioniVersoRegIItem.getValue().toString().equalsIgnoreCase("true"));
	}

	private boolean isFlgCtrAbilUOMittChecked(){
		return  (flgCtrAbilUOMittItem.getValue() != null && !flgCtrAbilUOMittItem.getValue().toString().equalsIgnoreCase("") &&  flgCtrAbilUOMittItem.getValue().toString().equalsIgnoreCase("true"));
	}

	private boolean isFlgInternaChecked(){
		return  (flgInternaItem.getValue() != null && !flgInternaItem.getValue().toString().equalsIgnoreCase("") &&  flgInternaItem.getValue().toString().equalsIgnoreCase("true"));
	}
	
	private boolean isNewMode(){
		return (mode != null && mode.equals("new"));		
	}
	
	@Override
	public boolean customValidate() {
		boolean valid = super.customValidate();
		
		boolean flgRestrizioniVersoRegE = flgRestrizioniVersoRegEItem.getValueAsBoolean() != null && flgRestrizioniVersoRegEItem.getValueAsBoolean();		
		boolean flgRestrizioniVersoRegU = flgRestrizioniVersoRegUItem.getValueAsBoolean() != null && flgRestrizioniVersoRegUItem.getValueAsBoolean();
		boolean flgRestrizioniVersoRegI = flgRestrizioniVersoRegIItem.getValueAsBoolean() != null && flgRestrizioniVersoRegIItem.getValueAsBoolean();
		
		if(!flgRestrizioniVersoRegE && !flgRestrizioniVersoRegU && !flgRestrizioniVersoRegI) {
			vm.setFieldErrors("flgRestrizioniVersoRegE", "Obbligatorio selezionare almeno una opzione", true);		
			valid = false;
		}
		
		// intero da 1 in su
		if ((nrAnniRinnovaNumerazioneItem.getValueAsString() != null && !"".equals(nrAnniRinnovaNumerazioneItem.getValueAsString().trim()))){
			int nrAnniRinnovaNumerazione = Integer.valueOf(nrAnniRinnovaNumerazioneItem.getValueAsString());
			if (nrAnniRinnovaNumerazione < 1){
				vm.setFieldErrors("nrAnniRinnovaNumerazione", "Valore non valido. Deve essere maggiore di 0", true);
				valid = false;
			}
		}
		else{
			vm.setFieldErrors("nrAnniRinnovaNumerazione", "Valore non valido. Deve essere maggiore di 1", true);
			valid = false;
		}
			
		return valid;
	}
	
	public class TitleStaticTextItem extends StaticTextItem {

		public TitleStaticTextItem(String title, int width) {
			setTitle(title);
			setColSpan(1);
			setDefaultValue(title + AurigaLayout.getSuffixFormItemTitle());
			setWidth(width);
			setShowTitle(false);
			setAlign(Alignment.RIGHT);
			setTextBoxStyle(it.eng.utility.Styles.formTitle);
			setWrap(false);
		}

		@Override
		public void setCanEdit(Boolean canEdit) {
			setTextBoxStyle(it.eng.utility.Styles.formTitle);
		}
	}
	
	protected void createTabSet() {
			
			tabSet = new TabSet();
			tabSet.setTabBarPosition(Side.TOP);
			tabSet.setTabBarAlign(Side.LEFT);
			tabSet.setWidth100();
			tabSet.setBorder("0px");
			tabSet.setCanFocus(false);
			tabSet.setTabIndex(-1);
			tabSet.setPaneMargin(0);

			/**
			 *  Tab Dati principali
			 */
			tabDatiPrincipali = new Tab("<b>" + getTitleTabDatiPrincipali() + "</b>");
			tabDatiPrincipali.setAttribute("tabID", "HEADER");
			tabDatiPrincipali.setPrompt(getTitleTabDatiPrincipali());

			VLayout lVLayoutSpacer = new VLayout();
			lVLayoutSpacer.setWidth100();
			lVLayoutSpacer.setHeight(10);

			layoutTabDatiPrincipali = createLayoutTab(getLayoutTabDatiPrincipali(), lVLayoutSpacer);

			// Aggiungo i layout ai tab
			tabDatiPrincipali.setPane(layoutTabDatiPrincipali);

			tabSet.addTab(tabDatiPrincipali);
	}
		
	public VLayout getLayoutTabDatiPrincipali() {
			
	        setInitValues();
			
			// LAYOUT MAIN
			VLayout lVLayout = new VLayout();
			lVLayout.setWidth100();
			lVLayout.setHeight(100);
					
			VLayout lVLayoutSpacer = new VLayout();
			lVLayoutSpacer.setWidth100();
			lVLayoutSpacer.setHeight(10);

			lVLayout.addMember(datiPrincipaliRegistroNumForm);
			lVLayout.addMember(tipiDocAmmEscSection);	
			lVLayout.addMember(abilitazioniSection);	
			
			return lVLayout;
	}
		
	public String getTitleTabDatiPrincipali() {
			return "Dati principali";
	}
		
	protected VLayout createLayoutTab(VLayout layout, VLayout spacerLayout) {
			
			VLayout layoutTab = new VLayout();
			layoutTab.setWidth100();
			layoutTab.setHeight100();
			layoutTab.addMember(layout);
			layoutTab.addMember(spacerLayout);
			layoutTab.setRedrawOnResize(true);
			return layoutTab;
	}
		
	private void setInitValues() {
		   
		buildDatiPrincipaliSection();
			
		buildTipiDocAmmEscSection();
			
		buildAbilitazioniSection();
	}

	
	@Override
	public void clearTabErrors() {
		   clearTabErrors(tabSet);
	}
	
	@Override
	public void showTabErrors() {
		   showTabErrors(tabSet);
	}
	
	@Override
	public void showTabErrors(TabSet tabSet) {
		
		super.showTabErrors(tabSet);
		
		if (attributiAddTabs != null) {
			for (String key : attributiAddTabs.keySet()) {
				if (attributiAddDetails.get(key) != null) {
					attributiAddDetails.get(key).showTabErrors(tabSet);
				}
			}
		}
	}	
	
	@Override
	public void editNewRecord() {
		vm.clearErrors(true);
		clearTabErrors(tabSet);
		super.editNewRecord();
		caricaAttributiDinamici(null);
	}
	
	@Override
	public void editNewRecord(Map initialValues) {
    	vm.clearErrors(true);
		clearTabErrors(tabSet);
		super.editNewRecord(initialValues);
		reloadComboFromRecord(new Record(initialValues));
		caricaAttributiDinamici(null);
	}
	
	@Override
	public void editRecord(Record record) {
		vm.clearErrors(true);
		clearTabErrors(tabSet);
		super.editRecord(record);
		reloadComboFromRecord(record);
		this.rowid = record.getAttribute("rowid");
		caricaAttributiDinamici(rowid);
	}
	
	
	@Override
	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		super.setCanEdit(canEdit);

		if (attributiAddDetails != null) {
			for (String key : attributiAddDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddDetails.get(key);
				detail.setCanEdit(canEdit);
			}
		}
		
		setCodCategoriaValueMap(this.mode);
		
		// Quando si è in modifica e il registro ha già valorizzato UltimoNroGenearatoOut l'item nroInizialeItem è solo read-only
		setNroInizialeReadOnly(canEdit);
		
		// Quando si è in modifica e la categoria = R il check è solo read-only
		setFlgNumerazioneSenzaContinuitaReadOnly(canEdit);
		
		// item sempre read-only
		nrUltimaRegItem.setCanEdit(false);
		dtUltimaRegItem.setCanEdit(false);
		dtUltimoCambioStatoItem.setCanEdit(false);
	}

	public void caricaAttributiDinamici(final String rowid) {
		Record lRecordLoad = new Record();
		lRecordLoad.setAttribute("nomeTabella", "DMT_TIPI_REGISTRAZIONE");
		new GWTRestService<Record, Record>("LoadComboGruppiAttrCustomTabellaDataSource").call(lRecordLoad, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				final boolean isReload = (attributiAddTabs != null && attributiAddTabs.size() > 0);
				if(attributiAddLayouts != null) {
					for (String key : attributiAddLayouts.keySet()) {
						// se inizia con HEADER_ non devo cancellare il layout perchè è quello del tab principale
						if(key != null && !key.startsWith("HEADER_")) {
							try { attributiAddLayouts.get(key).destroy(); } catch(Exception e) {}
						}
					}
				}
				if(attributiAddDetails != null) {
					for (String key : attributiAddDetails.keySet()) {
						try { attributiAddDetails.get(key).destroy(); } catch(Exception e) {}				
					}
				}
				attributiAddTabs = (LinkedHashMap<String, String>) object.getAttributeAsMap("gruppiAttributiCustomTabella");
				attributiAddLayouts = new HashMap<String, VLayout>();
				attributiAddDetails = new HashMap<String, AttributiDinamiciDetail>();
				if (attributiAddTabs != null && attributiAddTabs.size() > 0) {
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");					
					Record lAttributiDinamiciRecord = new Record();
					lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_TIPI_REGISTRAZIONE");
					lAttributiDinamiciRecord.setAttribute("rowId", rowid);
					lAttributiDinamiciRecord.setAttribute("tipoEntita", (String) null);
					lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
							if (attributiAdd != null && !attributiAdd.isEmpty()) {
								for (final String key : attributiAddTabs.keySet()) {
									RecordList attributiAddCategoria = new RecordList();
									for (int i = 0; i < attributiAdd.getLength(); i++) {
										Record attr = attributiAdd.get(i);
										if (attr.getAttribute("categoria") != null
												&& (attr.getAttribute("categoria").equalsIgnoreCase(key) || ("HEADER_" + attr.getAttribute("categoria"))
														.equalsIgnoreCase(key))) {
											attributiAddCategoria.add(attr);
										}
									}
									if (!attributiAddCategoria.isEmpty()) {
										if(key.equals("#HIDDEN")) {
											// Gli attributi che fanno parte di questo gruppo non li considero
										} else if (key.startsWith("HEADER_")) {
											AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
													.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
													.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null,
													tabSet, "HEADER");
											detail.setCanEdit(new Boolean(editing));
											attributiAddDetails.put(key, detail);
											VLayout layout = (VLayout) layoutTabDatiPrincipali.getMembers()[0];
											attributiAddLayouts.put(key, layout);
											int pos = 0;
											for (Canvas member : layout.getMembers()) {
												if (member instanceof HeaderDetailSection) {
													pos++;
												} else {
													break;
												}
											}												
											for (DetailSection detailSection : attributiAddDetails.get(key).getDetailSections()) {
												if (isReload) {
													((DetailSection) layout.getMember(pos++)).setForms(detailSection.getForms());
												} else {
													layout.addMember(detailSection, pos++);
												}
											}
										} else {
											AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
													.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
													.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null,
													tabSet, key);
											detail.setCanEdit(new Boolean(editing));
											attributiAddDetails.put(key, detail);
											VLayout layout = new VLayout();
											layout.setHeight100();
											layout.setWidth100();
											layout.setMembers(detail);
											attributiAddLayouts.put(key, layout);
											VLayout layoutTab = new VLayout();
											layoutTab.addMember(layout);
											if (tabSet.getTabWithID(key) != null) {
												tabSet.getTabWithID(key).setPane(layoutTab);
											} else {
												Tab tab = new Tab("<b>" + attributiAddTabs.get(key) + "</b>");
												tab.setAttribute("tabID", key);
												tab.setPrompt(attributiAddTabs.get(key));
												tab.setPane(layoutTab);
												tabSet.addTab(tab);
											}
										}
									}
								}
							}
						}
					});
				}
			}
		});
	}
	
	public Record getRecordToSave() {
		
		final Record lRecordToSave = new Record(vm.getValues());
		
		if (attributiAddDetails != null) {
			lRecordToSave.setAttribute("rowid", rowid);
			lRecordToSave.setAttribute("valori", getAttributiDinamici());
			lRecordToSave.setAttribute("tipiValori", getTipiAttributiDinamici());
		}
		return lRecordToSave;
	}

	public Map<String, Object> getAttributiDinamici() {
		
		Map<String, Object> attributiDinamici = null;
		
		if (attributiAddTabs != null) {
			for (String key : attributiAddTabs.keySet()) {
				if (attributiAddDetails.get(key) != null) {
					if (attributiDinamici == null) {
						attributiDinamici = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDetails.get(key).getRecordToSave();
					attributiDinamici.putAll(attributiAddDetails.get(key).getMappaValori(detailRecord));
				}
			}
		}
		return attributiDinamici;
	}

	public Map<String, String> getTipiAttributiDinamici() {
		
		Map<String, String> tipiAttributiDinamici = null;
		
		if (attributiAddTabs != null) {
			for (String key : attributiAddTabs.keySet()) {
				if (attributiAddDetails.get(key) != null) {
					if (tipiAttributiDinamici == null) {
						tipiAttributiDinamici = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDetails.get(key).getRecordToSave();
					tipiAttributiDinamici.putAll(attributiAddDetails.get(key).getMappaTipiValori(detailRecord));
				}
			}
		}
		return tipiAttributiDinamici;
	}
	
	private void reloadComboFromRecord(Record record) {
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();		
		if(attributiAddLayouts != null) {
			for (String key : attributiAddLayouts.keySet()) {
				// se inizia con HEADER_ non devo cancellare il layout perchè è quello del tab principale
				if(key != null && !key.startsWith("HEADER_")) {
					try { attributiAddLayouts.get(key).destroy(); } catch(Exception e) {}
				}
			}
		}
		if(attributiAddDetails != null) {
			for (String key : attributiAddDetails.keySet()) {
				try { attributiAddDetails.get(key).destroy(); } catch(Exception e) {}				
			}
		}
		attributiAddTabs = null;
		attributiAddLayouts = null;		
		attributiAddDetails = null;
	}
	
}
