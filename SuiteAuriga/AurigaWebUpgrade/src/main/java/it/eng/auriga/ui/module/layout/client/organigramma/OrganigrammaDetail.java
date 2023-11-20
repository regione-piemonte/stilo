/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
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
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.SelezionaUOItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;


public class OrganigrammaDetail extends CustomDetail {
	
	private DetailSection formDatiPrincipaliSection;
	
	/** LIVELLO **/
	private DynamicForm formLivello;
	private HiddenItem livelloCorrente;
	private HiddenItem livelloCorrenteOrig;
	private TextItem livello;
	private SelectItem nroLivello;
	private HiddenItem flgIgnoreWarning;	
	
	/*** DATI GENERALI *****/
	private DynamicForm formDatiGenerali;
	private HiddenItem idUoSvUt;
	private SelectItem tipo;
	private SelectItem idAssessoreRif;
	private TextItem denominazioneEstesa;
	private TextItem denominazioneBreve;	
	private TextItem ciProvUO;
	private RadioGroupItem presenteAttoDefOrganigrammaItem;
	private DateItem dataIstituitaItem;
	private DateItem dataCessazioneItem;
	private CheckboxItem flgUfficioGareAppalti;
	
	
	// nuovi dati solo per modifica
	private DateItem variazioneDati;// data senza ore, obbligatorio se check spuntato
	private TextItem motiviVariazione;
	private DynamicForm estremiVariazioneDaStoricizzareForm;
	protected DetailSection estremiVariazioneDaStoricizzareDetail;
	
	/*** OPZIONI *****/
	private DynamicForm formOpzioni;
	private DetailSection formOpzioniSection;
	private CheckboxItem flgInibitaAssegnazione;
	private CheckboxItem flgInibitoInvioCC;
	private CheckboxItem flgPuntoDiProtocollo;
	private CheckboxItem inibitaPreimpDestProtEntrataItem;
	private CheckboxItem flgPuntoRaccoltaEmail;
	private CheckboxItem flgInibitaAssegnazioneEmail;
	private CheckboxItem flgPuntoRaccoltaDocumenti;
	private CheckboxItem abilitaUoProtEntrataItem;
	private CheckboxItem abilitaUoProtUscitaItem;
	private CheckboxItem abilitaUoProtUscitaFullItem;
	private CheckboxItem abilitaUoAssRiservatezzaItem;
	private CheckboxItem abilitaUoGestMulteItem;
	private CheckboxItem abilitaUoGestContrattiItem;
	private CheckboxItem abilitaUoScansioneMassivaItem;
	private CheckboxItem storicizzaDatiVariatiIn;
	
	
	
	/*** ASSEGNAZIONI ALLE UNITA' DI PERSONALE DELLA STRUTTURA  ***/
	private DynamicForm formAssegnazioniUnitaPersonaleStruttura;
	private DetailSection formAssegnazioniUnitaPersonaleStrutturaSection;
	private CheckboxItem flgPropagaAssInvioUPItem;
	private HiddenItem flgEreditaAssInvioUPItem;  
	 
	private RadioGroupItem flgAssInvioUPItem; 
	
	/*** CONTATTI *****/
	private DynamicForm contattiOrganigrammaForm;
	private ContattiOrganigrammaItem contattiOrganigrammaItem;
	protected DetailSection detailSectionContattiOrganigramma;
	
	/*** CENTRI DI COSTO *****/
	private DynamicForm centriCostoOrganigrammaForm;
	private CentriCostoOrganigrammaItem centriCostoOrganigrammaItem;
	protected DetailSection detailSectionCentriCostoOrganigramma;	
	
	/*** Tipologia UO ***/
	private DynamicForm associaPuntiProtocolloForm;
	private PuntoProtocolloItem puntoProtocolloItem;
	private DetailSection associaPuntiProtocolloDetailSection;
	
	/** UO derivata **/
	private DetailSection uoDerivataDetailSection;
	private DynamicForm uoDerivataForm;
	private SelectItem uoDerivataPerSelectItem;
	private SelezionaUOItem uoDerivataDaItem;
	
	/** UO dato luogo a **/
	private DetailSection uoDatoLuogoADetailSection;
	private DynamicForm uoDatoLuogoAForm;
	private SelectItem uoDatoLuogoAPerSelectItem;
	private SelezionaUOItem uoDatoLuogoAItem;
	

	
	/** UO per spostare doc/fasc **/
	private DetailSection uoSpostaDocFascSection;
	private DynamicForm uoSpostaDocFascForm;
	private ExtendedTextItem codRapidoSpostaDocFascItem;
	private FilteredSelectItemWithDisplay organigrammaSpostaDocFascItem;
	private ImgButtonItem lookupOrganigrammaSpostaDocFascButton;
	private SelectItem tipoSpostaDocFascItem;
	private HiddenItem flgPresentiDocFascItem;
	private HiddenItem typeNodoSpostaDocFascItem;
	private HiddenItem idUoSpostaDocFascItem;
	private HiddenItem descrizioneSpostaDocFascItem;
	
	/** UO per spostare mail **/
	private DetailSection uoSpostaMailSection;
	private DynamicForm uoSpostaMailForm;	
	private ExtendedTextItem codRapidoSpostaMailItem;
	private FilteredSelectItemWithDisplay organigrammaSpostaMailItem;
	private ImgButtonItem lookupOrganigrammaSpostaMailButton;
	private SelectItem tipoSpostaMailItem;
	private HiddenItem flgPresentiMailItem;
	private HiddenItem typeNodoSpostaMailItem;
	private HiddenItem idUoSpostaMailItem;
	private HiddenItem descrizioneSpostaMailItem;   
	
	/** Situazione documentazione e mail in competenza alla UO **/
	private DetailSection resocontoSpostamentoDocFascMailSection;
	private DynamicForm resocontoSpostamentoDocFascMailForm;	
	private NumericItem nrDocAssegnatiItem;
	private DateTimeItem dataConteggioDocAssegnatiItem;
	private NumericItem nrFascAssegnatiItem;
	private DateTimeItem dataConteggioFascAssegnatiItem;
	private NumericItem nrMailAssegnatiItem;
	private DateTimeItem dataConteggioMailAssegnatiItem;
	private TextItem descUoSpostamentoDocFascItem;
	private TextItem statoSpostamentoDocFascItem;
	private DateTimeItem dataInizioSpostamentoDocFascItem;
	private DateTimeItem dataFineSpostamentoDocFascItem;
	private TextItem descUoSpostamentoMailItem;
	private TextItem statoSpostamentoMailItem;
	private DateTimeItem dataInizioSpostamentoMailItem;
	private DateTimeItem dataFineSpostamentoMailItem;
	
	/*** COMPETENZE***/
	private DynamicForm competenzeForm;
	private DetailSection competenzeSection;
	private TextAreaItem competenzeItem;
	private String tipoAssegnatari;

	/*** Uff. liquidatore di riferimento ***/
	private DynamicForm uoLiquidatoreForm;
	private DetailSection uoLiquidatoreDetailSection;
	private FilteredSelectItemWithDisplay uoUfficioLiquidatoreItem;
	private ExtendedTextItem codRapidoUfficioLiquidatoreItem;
	private HiddenItem idUoUfficioLiquidatoreItem;
	private HiddenItem typeNodoUfficioLiquidatoreItem;
	private HiddenItem descrizioneUfficioLiquidatoreItem;
	
	public OrganigrammaDetail(String nomeEntita) {

		super(nomeEntita);

		//creaLivelloForm();
		
		creaMainForm();
		
		setTipoAssegnatari("UO");
		
		creaSelectOrganigrammaSpostaDocFasc();

		creaSelectOrganigrammaSpostaMail();
		
		creaResocontoSpostamentoSection();
		
		creaOpzioniForm();
		
		creaAssegnazioniUnitaPersonaleStrutturaForm();
		
		creaEstremiVariazioneDaStoricizzareForm();
		
		creaCentroCostoForm();
		
		creaAssociaPuntiProtocolloForm();
		
		creaUoLiquidatoreForm();
		
		creaUoDerivataForm();
				
		creaUoDatoLuogoAForm();
		
		creaCompetenzeForm();
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();
		lVLayout.addMember(formDatiPrincipaliSection);
		lVLayout.addMember(uoSpostaDocFascSection);
		lVLayout.addMember(uoSpostaMailSection);
		lVLayout.addMember(resocontoSpostamentoDocFascMailSection);
		lVLayout.addMember(formOpzioniSection);
		lVLayout.addMember(formAssegnazioniUnitaPersonaleStrutturaSection);
		lVLayout.addMember(estremiVariazioneDaStoricizzareDetail);
		lVLayout.addMember(detailSectionContattiOrganigramma);
		lVLayout.addMember(associaPuntiProtocolloDetailSection);
		lVLayout.addMember(detailSectionCentriCostoOrganigramma);
		lVLayout.addMember(uoLiquidatoreDetailSection);
		lVLayout.addMember(uoDerivataDetailSection);
		lVLayout.addMember(uoDatoLuogoADetailSection);		
		lVLayout.addMember(competenzeSection);
		
		addMember(lVLayout);
		addMember(lVLayoutSpacer);
	}
	
	@Override
	public void newMode() {
		super.newMode();
		uoDatoLuogoADetailSection.setVisible(false);
	}

	@Override
	public void viewMode() {
		super.viewMode();
		associaPuntiProtocolloDetailSection.setVisible(isAssociaPuntiProtocolloDetailSectionVisible());
		uoDatoLuogoADetailSection.setVisible(true);
	}
	
	@Override
	public void editMode() {
		super.editMode();
		associaPuntiProtocolloDetailSection.setVisible(isAssociaPuntiProtocolloDetailSectionVisible());
		uoDatoLuogoADetailSection.setVisible(true);
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		livelloCorrenteOrig.setValue(record.getAttribute("livelloCorrente"));
		reloadListaTipi(record.getAttribute("nroLivello"));				
		nroLivello.setValueMap(buildNroLivelloValueMap(record.getAttribute("nroLivello")));
		initCombo(record);
		initSpostaDocFascMailSection(record);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void editNewRecord(Map initialValues) {
		super.editNewRecord(initialValues);		
		livelloCorrenteOrig.setValue((String) initialValues.get("livelloCorrente"));
		reloadListaTipi((String) initialValues.get("nroLivello"));		
		nroLivello.setValueMap(buildNroLivelloValueMap((String) initialValues.get("nroLivello")));
	}
	
	private void reloadListaTipi(String nroLivello) {
		GWTRestDataSource listaTipiDS = (GWTRestDataSource) tipo.getOptionDataSource();
		if (nroLivello != null && !"".equals(nroLivello)) {
			listaTipiDS.addParam("nroLivello", nroLivello);			
		} else {
			listaTipiDS.addParam("nroLivello", null);
		}
		tipo.setOptionDataSource(listaTipiDS);
		tipo.fetchData(new DSCallback() {			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Map tipoValueMap = response.getDataAsRecordList().getValueMap(tipo.getValueField(), tipo.getDisplayField());
				if(tipo.getValue() != null && !"".equals(tipo.getValueAsString()) && !tipoValueMap.containsKey(tipo.getValueAsString())) {
					vm.setValue("tipo", ""); 
				}
			}
		});			
	}
	
	private String[] buildNroLivelloValueMap(String nroLivello) {
		int nroLivelloDefault = 0;
		if (nroLivello != null && !"".equals(nroLivello)) {
			nroLivelloDefault = Integer.parseInt(nroLivello);
		}
		String nroLivelliOrganigramma = ((OrganigrammaLayout)getLayout()).getNroLivelliOrganigramma();		
		if(nroLivelliOrganigramma != null && !"".equals(nroLivelliOrganigramma)) {
			int nroLivelloMax = Integer.parseInt(nroLivelliOrganigramma);
			String[] nroLivelloValueMap = new String[nroLivelloMax - nroLivelloDefault + 1];
			for(int i = 0; i <= nroLivelloMax - nroLivelloDefault; i++) {
				nroLivelloValueMap[i] = "" + (i + nroLivelloDefault);
			}
			return nroLivelloValueMap;	
		} else {
			AurigaLayout.addMessage(new MessageBean("Non è configurato il numero di livelli dell'organigramma", "", MessageType.ERROR));
			return new String[0];
		}					
	}
	
	private String setTitleAligned(String title){
		return "<span style=\"width: 150px; display: inline-block;\">"+title+"</span>";
	}
	
	private boolean isAssociaPuntiProtocolloDetailSectionVisible(){
		if (flgPuntoDiProtocollo != null){
			return OrganigrammaLayout.isAttivoGestionePuntiProtocollo() && !flgPuntoDiProtocollo.getValueAsBoolean();
		}else{
			return false;
		}
	}
	
	private boolean isUoLiquidatoreDetailSectionVisible(){
		return AurigaLayout.getParametroDBAsBoolean("GESTIONE_ATTI_COMPLETA");
	}
	
	private boolean isUOSpostaDocFascSectionRequired(){
		return !AurigaLayout.getParametroDBAsBoolean("DISATTIVA_OBBL_SPOST_DOC_DA_UO_SV_CESSATE");
	}
	
    private boolean isUOSpostaMailSectionRequired(){
    	return !AurigaLayout.getParametroDBAsBoolean("DISATTIVA_OBBL_SPOST_DOC_DA_UO_SV_CESSATE");
	}
	
	private boolean isUOSpostaDocFascSectionVisible(){
		
		Date dataCessazione      = dataCessazioneItem.getValueAsDate();
		
		// Se la data di cessazione < data odierna   
		if (isDataCessazioneScaduta(dataCessazione)){
			
		    // Verifico se sono presenti doc/fasc
			String flgPresentiDocFasc = vm.getValueAsString("flgPresentiDocFasc");
			
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
	
	private boolean isUOSpostaMailSectionVisible(){
		Date dataCessazione      = dataCessazioneItem.getValueAsDate();
		// Se la data di cessazione < data odierna   
		if (isDataCessazioneScaduta(dataCessazione)){
		    // Verifico se sono presenti doc/fasc
			String flgPresentiMail = vm.getValueAsString("flgPresentiMail");
			if (flgPresentiMail != null){
				if (flgPresentiMail.equalsIgnoreCase("1")){
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
	
	public enum UODerivataDatoLuogoEnum {
	    ACCORPAMENTO("accorpamento", "accorpamento"),
	    SPLIT("split", "split");

	    private String valore;
	    private String descrizione;

	    UODerivataDatoLuogoEnum(String valore, String descrizione) {
	        this.valore = valore;
	        this.descrizione = descrizione;
	    }

	    public String valore() {
	        return valore;
	    }
	    
	    public String descrizione() {
	        return descrizione;
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
	
	private void creaSelectOrganigrammaSpostaDocFasc() {

		String tipoAssegnatari = "UO";
		uoSpostaDocFascForm = new DynamicForm();
		uoSpostaDocFascForm.setValuesManager(vm);
		uoSpostaDocFascForm.setWidth("100%");
		uoSpostaDocFascForm.setHeight("5");
		uoSpostaDocFascForm.setPadding(5);
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
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						@Override
						public void execute() {
							organigrammaSpostaDocFascItem.fetchData();
						}
					});
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
				                       typeNodoSpostaDocFascItem             ,
				                       idUoSpostaDocFascItem                 ,
				                       descrizioneSpostaDocFascItem          
	                                 );	
		
		uoSpostaDocFascSection = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_uoSpostaDocFascSection_title(), true, true, false, uoSpostaDocFascForm);
		uoSpostaDocFascSection.setVisible(isUOSpostaDocFascSectionVisible());		
	}
	
	private void creaSelectOrganigrammaSpostaMail() {
		
		uoSpostaMailForm = new DynamicForm();
		uoSpostaMailForm.setValuesManager(vm);
		uoSpostaMailForm.setWidth("100%");
		uoSpostaMailForm.setHeight("5");
		uoSpostaMailForm.setPadding(5);
		uoSpostaMailForm.setWrapItemTitles(false);		
		uoSpostaMailForm.setNumCols(6);
		uoSpostaMailForm.setColWidths(1, 1, 1, 1, "*", "*");
		
		flgPresentiMailItem                = new HiddenItem("flgPresentiMail");
		typeNodoSpostaMailItem             = new HiddenItem("typeNodoSpostaMail");
		idUoSpostaMailItem                 = new HiddenItem("idUoSpostaMail");
		descrizioneSpostaMailItem          = new HiddenItem("descrizioneSpostaMail");
		
		// tipo
		tipoSpostaMailItem = new SelectItem("tipoSpostaMail");
		LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
		styleMap.put("SV;UO", "Unita'  di personale/U.O.");
		tipoSpostaMailItem.setDefaultValue("SV;UO");
		tipoSpostaMailItem.setShowTitle(false);
		tipoSpostaMailItem.setValueMap(styleMap);
		tipoSpostaMailItem.setWidth(150);
		tipoSpostaMailItem.setStartRow(true);
		tipoSpostaMailItem.setVisible(false);
		
		tipoSpostaMailItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				uoSpostaMailForm.clearErrors(true);
				Record lRecord = uoSpostaMailForm.getValuesAsRecord();
				lRecord.setAttribute("organigrammaSpostaMail", "");
				lRecord.setAttribute("idUoSpostaMail", "");
				lRecord.setAttribute("typeNodoSpostaMail", "");
				lRecord.setAttribute("codRapidoSpostaMail", "");
				uoSpostaMailForm.setValues(lRecord.toMap());
			}
		});
		
		codRapidoSpostaMailItem = new ExtendedTextItem("codRapidoSpostaMail", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoSpostaMailItem.setWidth(120);
		codRapidoSpostaMailItem.setColSpan(1);
		codRapidoSpostaMailItem.setStartRow(true);
		codRapidoSpostaMailItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				uoSpostaMailForm.setValue("idUoSpostaMail", (String) null);
				uoSpostaMailForm.setValue("descrizioneSpostaMail", (String) null);
				uoSpostaMailForm.setValue("organigrammaSpostaMail", (String) null);
				uoSpostaMailForm.setValue("typeNodoSpostaMail", (String) null);
				uoSpostaMailForm.setValue("gruppoSpostaMail", (String) null);
				uoSpostaMailForm.clearErrors(true);
				final String value = codRapidoSpostaMailItem.getValueAsString();
				{					
					GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaSpostaMailItem.getOptionDataSource();
					organigrammaDS.addParam("flgSoloValide", "1");
					
					organigrammaSpostaMailItem.setOptionDataSource(organigrammaDS);
					
					if (value != null && !"".equals(value)) {
						organigrammaSpostaMailItem.fetchData(new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								RecordList data = response.getDataAsRecordList();
								boolean trovato = false;
								if (data.getLength() > 0) {
									for (int i = 0; i < data.getLength(); i++) {
										String codice = data.get(i).getAttribute("codice");
										String flgSelXFinalita = data.get(i).getAttribute("flgSelXFinalita");
										if (value.equals(codice) && (flgSelXFinalita == null || "1".equals(flgSelXFinalita))) {
											uoSpostaMailForm.setValue("descrizioneSpostaMail", data.get(i).getAttribute("descrizioneOrig"));
											uoSpostaMailForm.setValue("organigrammaSpostaMail", data.get(i).getAttribute("id"));
											uoSpostaMailForm.setValue("idUoSpostaMail", data.get(i).getAttribute("idUo"));
											uoSpostaMailForm.setValue("typeNodoSpostaMail", data.get(i).getAttribute("typeNodo"));
											uoSpostaMailForm.clearErrors(true);
											trovato = true;
											break;
										}
									}
								}
								if (!trovato) {
									codRapidoSpostaMailItem.validate();
									codRapidoSpostaMailItem.blurItem();
								}
							}
						});
					} else {
						organigrammaSpostaMailItem.fetchData();
					}
				}
			}
		});
		codRapidoSpostaMailItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (codRapidoSpostaMailItem.getValue() != null && 
					!"".equals(codRapidoSpostaMailItem.getValueAsString().trim()) && 
					organigrammaSpostaMailItem.getValue() == null						
					) {
					return false;
				}
				return true;
			}
		});
		
		SelectGWTRestDataSource lGwtRestDataSourceOrganigramma = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT, new String[] { "descrizione" }, true);
		lGwtRestDataSourceOrganigramma.addParam("tipoAssegnatari", getTipoAssegnatari());
		lGwtRestDataSourceOrganigramma.addParam("finalita", "ALTRO");

		organigrammaSpostaMailItem = new FilteredSelectItemWithDisplay("organigrammaSpostaMail", lGwtRestDataSourceOrganigramma) {

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
				uoSpostaMailForm.setValue("codRapidoSpostaMail",            record.getAttributeAsString("codice"));
				uoSpostaMailForm.setValue("typeNodoSpostaMail",             record.getAttributeAsString("typeNodo"));
				uoSpostaMailForm.setValue("idUoSpostaMail",                 record.getAttributeAsString("idUo"));
				uoSpostaMailForm.setValue("descrizioneSpostaMail",          record.getAttributeAsString("descrizioneOrig"));		
				uoSpostaMailForm.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						organigrammaSpostaMailItem.fetchData();
					}
				});
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				uoSpostaMailForm.setValue("organigrammaSpostaMail", "");
				uoSpostaMailForm.setValue("codRapidoSpostaMail", "");
				if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
					uoSpostaMailForm.setValue("codRapidoSpostaMail", AurigaLayout.getCodRapidoOrganigramma());
				} else {
					uoSpostaMailForm.setValue("codRapidoSpostaMail", "");
				}
				uoSpostaMailForm.setValue("idUoSpostaMail", "");
				uoSpostaMailForm.setValue("typeNodoSpostaMail", "");
				uoSpostaMailForm.setValue("descrizioneSpostaMail", "");
			
				uoSpostaMailForm.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						organigrammaSpostaMailItem.fetchData();
					}
				});
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					uoSpostaMailForm.setValue("organigrammaSpostaMail", "");
					uoSpostaMailForm.setValue("codRapidoSpostaMail", "");
					if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
						uoSpostaMailForm.setValue("codRapidoSpostaMail", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						uoSpostaMailForm.setValue("codRapidoSpostaMail", "");
					}
					uoSpostaMailForm.setValue("idUoSpostaMail", "");
					uoSpostaMailForm.setValue("typeNodoSpostaMail", "");
					uoSpostaMailForm.setValue("descrizioneSpostaMail", "");
					
					uoSpostaMailForm.clearErrors(true);
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						@Override
						public void execute() {
							organigrammaSpostaMailItem.fetchData();
						}
					});
				}
			}
		};
		List<ListGridField> organigrammaSpostaMailPickListFields = new ArrayList<ListGridField>();
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
			organigrammaSpostaMailPickListFields.add(typeNodoField);
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
		organigrammaSpostaMailPickListFields.add(codiceField);
		ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().organigramma_list_descrizioneField_title());
		descrizioneField.setWidth("*");
		organigrammaSpostaMailPickListFields.add(descrizioneField);
		
		organigrammaSpostaMailItem.setPickListFields(organigrammaSpostaMailPickListFields.toArray(new ListGridField[organigrammaSpostaMailPickListFields.size()]));
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			organigrammaSpostaMailItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimOrganigrammaNonStdMessage());			
		} else {
			organigrammaSpostaMailItem.setFilterLocally(true);
		}
		organigrammaSpostaMailItem.setAutoFetchData(false);
		organigrammaSpostaMailItem.setAlwaysFetchMissingValues(true);
		organigrammaSpostaMailItem.setFetchMissingValues(true);
		organigrammaSpostaMailItem.setValueField("id");
		organigrammaSpostaMailItem.setOptionDataSource(lGwtRestDataSourceOrganigramma);
		organigrammaSpostaMailItem.setShowTitle(false);
		organigrammaSpostaMailItem.setWidth(400);
		organigrammaSpostaMailItem.setClearable(true);
		organigrammaSpostaMailItem.setShowIcons(true);
		organigrammaSpostaMailItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
			}
		});
		
		ListGrid pickListProperties = organigrammaSpostaMailItem.getPickListProperties();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String codRapido = uoSpostaMailForm.getValueAsString("codRapidoSpostaMail");
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaSpostaMailItem.getOptionDataSource();
				organigrammaDS.addParam("codice", codRapido);
				organigrammaDS.addParam("finalita", "ALTRO");
				organigrammaDS.addParam("flgSoloValide", "1");
				organigrammaSpostaMailItem.setOptionDataSource(organigrammaDS);
				organigrammaSpostaMailItem.invalidateDisplayValueCache();
			}
		});
		organigrammaSpostaMailItem.setPickListProperties(pickListProperties);

		lookupOrganigrammaSpostaMailButton = new ImgButtonItem("lookupOrganigrammaSpostaMailButton", "lookup/organigramma.png", I18NUtil.getMessages().protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaSpostaMailButton.setColSpan(1);
		lookupOrganigrammaSpostaMailButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				AssegnazioneLookupOrganigrammaSpostaMail lookupOrganigrammaPopup = new AssegnazioneLookupOrganigrammaSpostaMail(null);
				lookupOrganigrammaPopup.show();
			}
		});
				
		uoSpostaMailForm.setFields( // visibili
				                       tipoSpostaMailItem,
				                       codRapidoSpostaMailItem,
				                       lookupOrganigrammaSpostaMailButton,
				                       organigrammaSpostaMailItem,
				                       
	                                   // Hidden
				                       flgPresentiMailItem,
				                       typeNodoSpostaMailItem             ,
				                       idUoSpostaMailItem                 ,
				                       descrizioneSpostaMailItem          
	                                 );	
		
		uoSpostaMailSection = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_uoSpostaMailSection_title(), true, true, false, uoSpostaMailForm);
		uoSpostaMailSection.setVisible(isUOSpostaMailSectionVisible());
	}
	
	public class AssegnazioneLookupOrganigrammaSpostaDocFasc extends LookupOrganigrammaPopup {

		public AssegnazioneLookupOrganigrammaSpostaDocFasc(Record record) {
			super(record, true, getFlgIncludiUtenti());
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
	
	public class AssegnazioneLookupOrganigrammaSpostaMail extends LookupOrganigrammaPopup {

		public AssegnazioneLookupOrganigrammaSpostaMail(Record record) {
			super(record, true, getFlgIncludiUtenti());
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordSpostaMail(record);
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
	
	public void setFormValuesFromRecordSpostaMail(Record record) {
		String idOrganigramma = record.getAttribute("idUoSvUt");
		String tipo = record.getAttribute("tipo");
		int pos = tipo.indexOf("_");
		if (pos != -1) {
			tipo = tipo.substring(0, pos);
		}
		uoSpostaMailForm.setValue("organigrammaSpostaMail", tipo + idOrganigramma);
		uoSpostaMailForm.setValue("idUoSpostaMail", idOrganigramma);
		uoSpostaMailForm.setValue("typeNodoSpostaMail", tipo);
		uoSpostaMailForm.setValue("codRapidoSpostaMail", record.getAttribute("codRapidoUo"));
		uoSpostaMailForm.setValue("descrizioneSpostaMail", record.getAttribute("nome"));
		
		uoSpostaMailForm.clearErrors(true);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				organigrammaSpostaMailItem.fetchData();
			}
		});
	}
	
	private void creaResocontoSpostamentoSection() {
		resocontoSpostamentoDocFascMailForm = new DynamicForm();
		resocontoSpostamentoDocFascMailForm.setValuesManager(vm);
		resocontoSpostamentoDocFascMailForm.setWidth("100%");
		resocontoSpostamentoDocFascMailForm.setHeight("5");
		resocontoSpostamentoDocFascMailForm.setPadding(5);
		resocontoSpostamentoDocFascMailForm.setWrapItemTitles(false);		
		resocontoSpostamentoDocFascMailForm.setNumCols(16);
		resocontoSpostamentoDocFascMailForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		
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
		descUoSpostamentoDocFascItem = new TextItem("descUoSpostamentoDocFasc", I18NUtil.getMessages().organigramma_uo_detail_descUoSpostamentoDocFasc_title());		
		descUoSpostamentoDocFascItem.setStartRow(true);
		descUoSpostamentoDocFascItem.setColSpan(3);
		descUoSpostamentoDocFascItem.setWidth(340);

		statoSpostamentoDocFascItem = new TextItem("statoSpostamentoDocFasc", I18NUtil.getMessages().organigramma_uo_detail_statoSpostamentoDocFasc_title());		
		statoSpostamentoDocFascItem.setWidth(120);
		
		dataInizioSpostamentoDocFascItem = new DateTimeItem("dataInizioSpostamentoDocFasc", I18NUtil.getMessages().organigramma_uo_detail_dataInizioSpostamentoDocFasc_title());
		dataInizioSpostamentoDocFascItem.setWidth(120);
		
		dataFineSpostamentoDocFascItem = new DateTimeItem("dataFineSpostamentoDocFasc", I18NUtil.getMessages().organigramma_uo_detail_dataFineSpostamentoDocFasc_title());
		dataFineSpostamentoDocFascItem.setWidth(120);

		// Conteggio mail
		nrMailAssegnatiItem = new NumericItem("nrMailAssegnati", I18NUtil.getMessages().organigramma_uo_detail_nrMailAssegnati_title());
		nrMailAssegnatiItem.setStartRow(true);
		nrMailAssegnatiItem.setWidth(100);
		
		dataConteggioMailAssegnatiItem = new DateTimeItem("dataConteggioMailAssegnati", I18NUtil.getMessages().organigramma_uo_detail_dataConteggioMailAssegnati_title());
		dataConteggioMailAssegnatiItem.setWidth(120);

		// Stato spostamento mail
		descUoSpostamentoMailItem = new TextItem("descUoSpostamentoMail", I18NUtil.getMessages().organigramma_uo_detail_descUoSpostamentoMail_title());		
		descUoSpostamentoMailItem.setStartRow(true);
		descUoSpostamentoMailItem.setColSpan(3);
		descUoSpostamentoMailItem.setWidth(340);
		
		statoSpostamentoMailItem = new TextItem("statoSpostamentoMail", I18NUtil.getMessages().organigramma_uo_detail_statoSpostamentoMail_title());		
		statoSpostamentoMailItem.setWidth(120);
		statoSpostamentoMailItem.setWrapTitle(true);
		
		dataInizioSpostamentoMailItem = new DateTimeItem("dataInizioSpostamentoMail", I18NUtil.getMessages().organigramma_uo_detail_dataInizioSpostamentoMail_title());
		dataInizioSpostamentoMailItem.setWidth(120);
		
		dataFineSpostamentoMailItem = new DateTimeItem("dataFineSpostamentoMail", I18NUtil.getMessages().organigramma_uo_detail_dataFineSpostamentoMail_title());
		dataFineSpostamentoMailItem.setWidth(120);
					
		resocontoSpostamentoDocFascMailForm.setFields( // visibili
				                                       nrDocAssegnatiItem,
				                                       dataConteggioDocAssegnatiItem,
				                                       nrFascAssegnatiItem,
				                                       dataConteggioFascAssegnatiItem,
				                                       descUoSpostamentoDocFascItem,
				                                       statoSpostamentoDocFascItem,
				                                       dataInizioSpostamentoDocFascItem,
				                                       dataFineSpostamentoDocFascItem,
				                                       nrMailAssegnatiItem,
				                                       dataConteggioMailAssegnatiItem,
				                                       descUoSpostamentoMailItem,
				                                       statoSpostamentoMailItem,
				                                       dataInizioSpostamentoMailItem,
				                                       dataFineSpostamentoMailItem				                                     
                                                      );	

		resocontoSpostamentoDocFascMailSection = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_resocontoSpostamentoDocFascMailSection_title(), true, true, false, resocontoSpostamentoDocFascMailForm);
		resocontoSpostamentoDocFascMailSection.setVisible(false);		
	}
	
    private boolean isResocontoSpostamentoDocFascMailSectionVisible(){
		// Se la data di cessazione < data odierna
		Date dataCessazione      = dataCessazioneItem.getValueAsDate();
		if (isDataCessazioneScaduta(dataCessazione))
		    return true;
		else
			return false;
	}
    
	@Override  
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
		// i campi della sezione RESOCONTO devono essere sempre read-only
		setCanEdit(false, resocontoSpostamentoDocFascMailForm);		
		centriCostoOrganigrammaItem.setCanEdit(canEdit);
		
		// I campi nel form formAssegnazioniUnitaPersonaleStruttura sono disattivi se FlgEreditaAssInvioUPOut = 1
		if (flgEreditaAssInvioUPItem.getValue()!=null && !flgEreditaAssInvioUPItem.getValue().toString().equalsIgnoreCase("") && flgEreditaAssInvioUPItem.getValue().toString().equalsIgnoreCase("1")){
			setCanEdit(false, formAssegnazioniUnitaPersonaleStruttura);
		}		    	
	}
	
	private void updateSpostaDocFascMailSection(Object dataCessazione){

		 organigrammaSpostaDocFascItem.setValue("");  uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", (String) null);
		 descrizioneSpostaDocFascItem.setValue("");   uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc",  (String) null);
		 codRapidoSpostaDocFascItem.setValue("");     uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc",    (String) null);
		 idUoSpostaDocFascItem.setValue("");          uoSpostaDocFascForm.setValue("idUoSpostaDocFasc",         (String) null);
		 typeNodoSpostaDocFascItem.setValue("UO");    uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc",     "UO");

		 organigrammaSpostaMailItem.setValue("");  uoSpostaMailForm.setValue("organigrammaSpostaMail", (String) null);
		 descrizioneSpostaMailItem.setValue("");   uoSpostaMailForm.setValue("descrizioneSpostaMail",  (String) null);
		 codRapidoSpostaMailItem.setValue("");     uoSpostaMailForm.setValue("codRapidoSpostaMail",    (String) null);
		 idUoSpostaMailItem.setValue("");          uoSpostaMailForm.setValue("idUoSpostaMail",         (String) null);
		 typeNodoSpostaMailItem.setValue("UO");    uoSpostaMailForm.setValue("typeNodoSpostav",        "UO");

		if (dataCessazione != null){
			uoSpostaDocFascSection.setVisible(isUOSpostaDocFascSectionVisible());	
			codRapidoSpostaDocFascItem.setRequired(isUOSpostaDocFascSectionVisible()    && isUOSpostaDocFascSectionRequired());
			organigrammaSpostaDocFascItem.setRequired(isUOSpostaDocFascSectionVisible() && isUOSpostaDocFascSectionRequired());
			
			uoSpostaMailSection.setVisible(isUOSpostaMailSectionVisible());
			codRapidoSpostaMailItem.setRequired(isUOSpostaMailSectionVisible()    && isUOSpostaMailSectionRequired());
			organigrammaSpostaMailItem.setRequired(isUOSpostaMailSectionVisible() && isUOSpostaMailSectionRequired());
			
			resocontoSpostamentoDocFascMailSection.setVisible(false);
		}
		else{
			uoSpostaDocFascSection.setVisible(false);	
			codRapidoSpostaDocFascItem.setRequired(false);
			organigrammaSpostaDocFascItem.setRequired(false);
			uoSpostaMailSection.setVisible(false);
			codRapidoSpostaMailItem.setRequired(false);
			organigrammaSpostaMailItem.setRequired(false);
			resocontoSpostamentoDocFascMailSection.setVisible(false);
		}
		uoSpostaDocFascSection.markForRedraw();	
		uoSpostaMailSection.markForRedraw();
		resocontoSpostamentoDocFascMailSection.markForRedraw();
	}
	
	 private void initSpostaDocFascMailSection(Record record) {
		 Date dataCessazione = record.getAttribute("dataSoppressa") != null ? DateUtil.parseInput(record.getAttribute("dataSoppressa")) : null;
		 // Se la data non e' presente, nascondo le sezioni
		 if (dataCessazione!=null){
			 // Se la data >= data odierna, mostro le sezioni per lo spostamento e nascondo quella del resoconto
			 if (isDataCessazioneValida(dataCessazione)){				
				 uoSpostaDocFascSection.setVisible(true);	
				 codRapidoSpostaDocFascItem.setRequired(isUOSpostaDocFascSectionRequired());
				 organigrammaSpostaDocFascItem.setRequired(isUOSpostaDocFascSectionRequired());
				 uoSpostaMailSection.setVisible(true);
				 codRapidoSpostaMailItem.setRequired(isUOSpostaMailSectionRequired());
				 organigrammaSpostaMailItem.setRequired(isUOSpostaMailSectionRequired());				 
				 resocontoSpostamentoDocFascMailSection.setVisible(false);
			 }
			 // Se la data < data odierna, nascondo le sezioni per lo spostamento e mostro quella del resoconto
			 if (isDataCessazioneScaduta(dataCessazione)){
				 uoSpostaDocFascSection.setVisible(false);	
				 codRapidoSpostaDocFascItem.setRequired(false);
				 organigrammaSpostaDocFascItem.setRequired(false);
				 uoSpostaMailSection.setVisible(false);
				 codRapidoSpostaMailItem.setRequired(false);
				 organigrammaSpostaMailItem.setRequired(false);
				 resocontoSpostamentoDocFascMailSection.setVisible(true);
			 }
		 }
         else
            {
		     uoSpostaDocFascSection.setVisible(false);	
			 codRapidoSpostaDocFascItem.setRequired(false);
			 organigrammaSpostaDocFascItem.setRequired(false);
			 uoSpostaMailSection.setVisible(false);
			 codRapidoSpostaMailItem.setRequired(false);
			 organigrammaSpostaMailItem.setRequired(false);
			 resocontoSpostamentoDocFascMailSection.setVisible(false);
			}
	}
	
	 private void initCombo(Record record) {
		 // Combo Organigramma Sposta Doc/Fasc
		 if (record.getAttributeAsString("idUODestDocfasc") != null && !"".equals(record.getAttributeAsString("idUODestDocfasc"))) {
			 LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			 valueMap.put(record.getAttributeAsString("idUODestDocfasc"), record.getAttributeAsString("desUODestDocFasc"));
			 organigrammaSpostaDocFascItem.setValueMap(valueMap);
			 organigrammaSpostaDocFascItem.setValue(record.getAttribute("idUODestDocfasc"));
			 uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", record.getAttribute("desUODestDocFasc"));	
			 uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", record.getAttribute("livelliUODestDocFasc"));
			 uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", "UO");
		 } 
		 // Combo Organigramma Sposta Mail
		 if (record.getAttributeAsString("idUODestMail") != null && !"".equals(record.getAttributeAsString("idUODestMail"))) {
			 LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			 valueMap.put(record.getAttributeAsString("idUODestMail"), record.getAttributeAsString("desUODestMail"));
			 organigrammaSpostaMailItem.setValueMap(valueMap);
			 organigrammaSpostaMailItem.setValue(record.getAttribute("idUODestMail"));
			 uoSpostaMailForm.setValue("descrizioneSpostaMail", record.getAttribute("desUODestMail"));	
			 uoSpostaMailForm.setValue("codRapidoSpostaMail", record.getAttribute("livelliUODestMail"));
			 uoSpostaMailForm.setValue("typeNodoSpostaMail", "UO");
		} 
		 
		// Combo Ufficio Liquidatore
		if (record.getAttributeAsString("idUoUfficioLiquidatore") != null && !"".equals(record.getAttributeAsString("idUoUfficioLiquidatore"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			String uoUfficioLiquidatore = record.getAttributeAsString("typeNodoUfficioLiquidatore") + record.getAttributeAsString("idUoUfficioLiquidatore");
			valueMap.put(uoUfficioLiquidatore, "<b>" + record.getAttributeAsString("descrizioneUfficioLiquidatore") + "</b>");
			uoUfficioLiquidatoreItem.setValueMap(valueMap);
			uoUfficioLiquidatoreItem.setValue(uoUfficioLiquidatore);									
		 }
	 }
	 
	 public String getTipoAssegnatari() {
			return tipoAssegnatari;
	}

	public void setTipoAssegnatari(String tipoAssegnatari) {
			this.tipoAssegnatari = tipoAssegnatari;
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
	
	private void creaCompetenzeForm() {
		
		competenzeForm = new DynamicForm();
		competenzeForm.setValuesManager(vm);
		competenzeForm.setWidth("100%");
		competenzeForm.setHeight("5");
		competenzeForm.setNumCols(2);
		competenzeForm.setColWidths("50","*");
		
		competenzeItem = new TextAreaItem("competenze", I18NUtil.getMessages().organigramma_uo_detail_competenzeItem_title());
		competenzeItem.setShowTitle(false);
		competenzeItem.setStartRow(true);
		competenzeItem.setLength(4000);
		competenzeItem.setHeight(60);
		competenzeItem.setWidth(650);

		competenzeForm.setItems(competenzeItem);
		competenzeSection = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_competenzeSection_title(), true, true, false, competenzeForm);
	}
	
	private void creaUoDatoLuogoAForm() {
		
		        // Sezione UO che ha dato luogo
				uoDatoLuogoAForm = new DynamicForm();
				uoDatoLuogoAForm.setValuesManager(vm);
				uoDatoLuogoAForm.setWidth("100%");
				uoDatoLuogoAForm.setHeight("5");
				uoDatoLuogoAForm.setNumCols(10);
				uoDatoLuogoAForm.setColWidths("50","1","1","1","1","1","1","1","*");

				uoDatoLuogoAPerSelectItem = new SelectItem("uoDatoLuogoAPer", I18NUtil.getMessages().organigramma_uo_detail_uoDatoLuogoAPerSelect_title());
				LinkedHashMap<String, String> uoDatoLuogoAPerValueMap = new LinkedHashMap<String, String>();
				uoDatoLuogoAPerValueMap.put(UODerivataDatoLuogoEnum.ACCORPAMENTO.valore, UODerivataDatoLuogoEnum.ACCORPAMENTO.descrizione);
				uoDatoLuogoAPerValueMap.put(UODerivataDatoLuogoEnum.SPLIT.valore, UODerivataDatoLuogoEnum.SPLIT.descrizione);
				uoDatoLuogoAPerSelectItem.setValueMap(uoDatoLuogoAPerValueMap);
				uoDatoLuogoAPerSelectItem.setAllowEmptyValue(true);
				uoDatoLuogoAPerSelectItem.setStartRow(true);
				uoDatoLuogoAPerSelectItem.addChangedHandler(new ChangedHandler() {
					
					@Override
					public void onChanged(ChangedEvent event) {
						uoDatoLuogoAForm.markForRedraw();
					}
				});
				
				uoDatoLuogoAItem = new SelezionaUOItem() {
					@Override
					public boolean showLookupOrganigrammaButton() {
						return false;
					}
					@Override
					public String getFlgSoloValide() {
						return "false";
					}
				};;
				
				uoDatoLuogoAItem.setName("listaUODatoLuogoA");
				uoDatoLuogoAItem.setTitle(I18NUtil.getMessages().organigramma_uo_detail_uoDatoLuogoAItem_title());
				uoDatoLuogoAItem.setStartRow(true);
				uoDatoLuogoAItem.setShowIfCondition(new FormItemIfFunction() {

					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {
						return !("".equals(uoDatoLuogoAPerSelectItem.getValueAsString()) || uoDatoLuogoAPerSelectItem.getValueAsString() == null);
					}
				});
				
				uoDatoLuogoAForm.setItems(uoDatoLuogoAPerSelectItem, uoDatoLuogoAItem);
				uoDatoLuogoADetailSection = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_uoDatoLuogoADetailSection_title(), true, true, false, uoDatoLuogoAForm){
					
					@Override
					public boolean showFirstCanvasWhenEmptyAfterOpen() {
						return true;
					}
				};
	}
	
	private void creaCentroCostoForm() {
		centriCostoOrganigrammaForm = new DynamicForm();
		centriCostoOrganigrammaForm.setValuesManager(vm);
		centriCostoOrganigrammaForm.setWidth("100%");
		centriCostoOrganigrammaForm.setHeight("5");
		centriCostoOrganigrammaForm.setPadding(5);

		centriCostoOrganigrammaItem = new CentriCostoOrganigrammaItem() {

			@Override
			public boolean isNewMode() {
				return (getLayout().getMode() != null && "new".equals(getLayout().getMode()));
			}
		};
		centriCostoOrganigrammaItem.setName("listaCentriCosto");
		centriCostoOrganigrammaItem.setShowTitle(false);

		centriCostoOrganigrammaForm.setFields(centriCostoOrganigrammaItem);
		
		detailSectionCentriCostoOrganigramma = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_centriCostoOrganigrammaDetailSection_title(), true, true, false, centriCostoOrganigrammaForm){
			
			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return true;
			}
		};
	}
	
	private void creaUoDerivataForm() {
		
			    // Sezione UO derivata
				uoDerivataForm = new DynamicForm();
				uoDerivataForm.setValuesManager(vm);
				uoDerivataForm.setWidth("100%");
				uoDerivataForm.setHeight("5");
				uoDerivataForm.setNumCols(10);
				uoDerivataForm.setColWidths("50","1","1","1","1","1","1","1","*");

				uoDerivataPerSelectItem = new SelectItem("uoDerivataPer", I18NUtil.getMessages().organigramma_uo_detail_uoDerivataPerSelect_title());
				LinkedHashMap<String, String> uoDerivataPerValueMap = new LinkedHashMap<String, String>();
				uoDerivataPerValueMap.put(UODerivataDatoLuogoEnum.ACCORPAMENTO.valore, UODerivataDatoLuogoEnum.ACCORPAMENTO.descrizione);
				uoDerivataPerValueMap.put(UODerivataDatoLuogoEnum.SPLIT.valore, UODerivataDatoLuogoEnum.SPLIT.descrizione);
				uoDerivataPerSelectItem.setValueMap(uoDerivataPerValueMap);
				uoDerivataPerSelectItem.setAllowEmptyValue(true);
				uoDerivataPerSelectItem.setStartRow(true);
				uoDerivataPerSelectItem.addChangedHandler(new ChangedHandler() {
						
						@Override
						public void onChanged(ChangedEvent event) {
							uoDerivataForm.markForRedraw();
						}
					});
				
				uoDerivataDaItem = new SelezionaUOItem() {
					
					@Override
					public boolean showLookupOrganigrammaButton() {
						return false;
					}
					@Override
					public String getFlgSoloValide() {
						return "false";
					}
				};
				uoDerivataDaItem.setName("listaUODerivataDa");
				uoDerivataDaItem.setTitle(I18NUtil.getMessages().organigramma_uo_detail_uoDerivataDaItem_title());
				uoDerivataDaItem.setStartRow(true);
				uoDerivataDaItem.setShowIfCondition(new FormItemIfFunction() {

					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {
						return !("".equals(uoDerivataPerSelectItem.getValueAsString()) || uoDerivataPerSelectItem.getValueAsString() == null);
					}
				});
			
				uoDerivataForm.setItems(uoDerivataPerSelectItem, uoDerivataDaItem);
				uoDerivataDetailSection = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_uoDerivataDetailSection_title(), true, true, false, uoDerivataForm){
					
					@Override
					public boolean showFirstCanvasWhenEmptyAfterOpen() {
						return true;
					}
				};
	}
	
	private void creaAssociaPuntiProtocolloForm() {
		
		puntoProtocolloItem = new PuntoProtocolloItem();
		puntoProtocolloItem.setStartRow(true);
		puntoProtocolloItem.setName("uoPuntoProtocolloCollegate");
		puntoProtocolloItem.setShowTitle(false);

		associaPuntiProtocolloForm = new DynamicForm();
		associaPuntiProtocolloForm.setValuesManager(vm);
		associaPuntiProtocolloForm.setWidth("100%");
		associaPuntiProtocolloForm.setHeight("5");
		associaPuntiProtocolloForm.setPadding(5);
		associaPuntiProtocolloForm.setItems(puntoProtocolloItem);
		
		associaPuntiProtocolloDetailSection = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_associaPuntiProtocolloDetailSection_title(), true, true, false, associaPuntiProtocolloForm){
			
			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return true;
			}
		};
		associaPuntiProtocolloDetailSection.setVisible(isAssociaPuntiProtocolloDetailSectionVisible());
	}
	
	private void creaUoLiquidatoreForm() {
		
		//uoLiquidatoreForm = new DynamicForm();
		
		//uoLiquidatoreForm.setCellBorder(1);
		
		
		// *********************************************
		// Organigramma UO 
		// *********************************************
		/*
		uoLiquidatoreForm = new DynamicForm() {

			@Override
			public void editRecord(Record record) {
				uoLiquidatoreForm.clearErrors(true);
				if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma"))) {
					LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
					if (record.getAttribute("typeNodo") != null && "UO".equals(record.getAttribute("typeNodo"))) {
						valueMap.put(record.getAttribute("organigramma"), "<b>" + record.getAttribute("descrizione") + "</b>");
					} else {
						valueMap.put(record.getAttribute("organigramma"), record.getAttribute("descrizione"));
					}
					uoUfficioLiquidatoreItem.setValueMap(valueMap);
				}
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) uoUfficioLiquidatoreItem.getOptionDataSource();
				if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma"))
						&& record.getAttribute("typeNodo") != null
						&& ("UO".equals(record.getAttributeAsString("typeNodo")) || "SV".equals(record.getAttributeAsString("typeNodo")))) {
					organigrammaDS.addParam("idUoSv", record.getAttributeAsString("idUo"));
					organigrammaDS.addParam("flgUoSv", record.getAttributeAsString("typeNodo"));
				} else {
					organigrammaDS.addParam("idUoSv", null);
					organigrammaDS.addParam("flgUoSv", null);
				}
				uoUfficioLiquidatoreItem.setOptionDataSource(organigrammaDS);
				super.editRecord(record);
			}
		};
		
		*/
		uoLiquidatoreForm = new DynamicForm();
		uoLiquidatoreForm.setValuesManager(vm);
		uoLiquidatoreForm.setHeight("5");
		uoLiquidatoreForm.setPadding(5);
		uoLiquidatoreForm.setNumCols(16);
		uoLiquidatoreForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");
		uoLiquidatoreForm.setWidth100();
		uoLiquidatoreForm.setHeight100();
		uoLiquidatoreForm.setWrapItemTitles(false);
		
		idUoUfficioLiquidatoreItem        = new HiddenItem("idUoUfficioLiquidatore");
		typeNodoUfficioLiquidatoreItem    = new HiddenItem("typeNodoUfficioLiquidatore");
		descrizioneUfficioLiquidatoreItem = new HiddenItem("descrizioneUfficioLiquidatore");

		// ********************************************************************
		// CODICE RAPIDO 
		// ********************************************************************
		codRapidoUfficioLiquidatoreItem = new ExtendedTextItem("codRapidoUfficioLiquidatore", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoUfficioLiquidatoreItem.setWidth(120);
		codRapidoUfficioLiquidatoreItem.setColSpan(1);		
		codRapidoUfficioLiquidatoreItem.addChangedBlurHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					uoLiquidatoreForm.clearErrors(true);
					uoLiquidatoreForm.setValue("descrizioneUfficioLiquidatore", (String) null);
					uoLiquidatoreForm.setValue("uoUfficioLiquidatore", (String) null);	
					uoLiquidatoreForm.setValue("idUoUfficioLiquidatore", (String) null);
					uoLiquidatoreForm.setValue("typeNodoUfficioLiquidatore", (String) null);					
					final String value = codRapidoUfficioLiquidatoreItem.getValueAsString();					
					if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
						if (value != null && !"".equals(value)) {
							uoUfficioLiquidatoreItem.fetchData(new DSCallback() {
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									RecordList data = response.getDataAsRecordList();
									boolean trovato = false;
									if (data.getLength() > 0) {
										for (int i = 0; i < data.getLength(); i++) {
											String codice = data.get(i).getAttribute("codice");
											String flgSelXFinalita = data.get(i).getAttribute("flgSelXFinalita");
											if (value.equals(codice) && (flgSelXFinalita == null || "1".equals(flgSelXFinalita))) {
												uoLiquidatoreForm.setValue("descrizioneUfficioLiquidatore", data.get(i).getAttribute("descrizioneOrig"));
												uoLiquidatoreForm.setValue("uoUfficioLiquidatore", data.get(i).getAttribute("id"));
												uoLiquidatoreForm.setValue("idUoUfficioLiquidatore", data.get(i).getAttribute("idUo"));
												uoLiquidatoreForm.setValue("typeNodoUfficioLiquidatore", data.get(i).getAttribute("typeNodo"));
												uoLiquidatoreForm.clearErrors(true);
												trovato = true;
												break;
											}
										}
									}
									if (!trovato) {
										codRapidoUfficioLiquidatoreItem.validate();
										codRapidoUfficioLiquidatoreItem.blurItem();
									}
								}
							});
						} else {
							uoUfficioLiquidatoreItem.fetchData();
						}
					}
					else{
						if (value != null && !"".equals(value)) {
							Criterion[] criterias = new Criterion[1];
							criterias[0] = new Criterion("codice", OperatorId.IEQUALS, value);
							SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT, new String[] { "codice", "descrizione" }, true);
							lGwtRestDataSource.addParam("finalita", null);
							lGwtRestDataSource.addParam("idUd", null);		
							lGwtRestDataSource.addParam("tipoAssegnatari", null);
							lGwtRestDataSource.fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									RecordList data = response.getDataAsRecordList();
									boolean trovato = false;
									if (data.getLength() > 0) {
										for (int i = 0; i < data.getLength(); i++) {
											String codice = data.get(i).getAttribute("codice");
											String flgSelXFinalita = data.get(i).getAttribute("flgSelXFinalita");
											if (value.equals(codice) && (flgSelXFinalita == null || "1".equals(flgSelXFinalita))) {
												uoLiquidatoreForm.setValue("descrizioneUfficioLiquidatore", data.get(i).getAttribute("descrizione"));
												uoLiquidatoreForm.setValue("uoUfficioLiquidatore", data.get(i).getAttribute("id"));
												uoLiquidatoreForm.setValue("idUoUfficioLiquidatore", data.get(i).getAttribute("idUo"));
												uoLiquidatoreForm.setValue("typeNodoUfficioLiquidatore", data.get(i).getAttribute("typeNodo"));
												uoLiquidatoreForm.clearErrors(true);
												trovato = true;
												break;
											}
										}
									}
									if (!trovato) {
										codRapidoUfficioLiquidatoreItem.validate();
										codRapidoUfficioLiquidatoreItem.blurItem();
									}
								}
							});
						}
					}
				}
			});
			
			
		// ********************************************************************
		// BOTTONE LOOKUP
		// ********************************************************************
		ImgButtonItem lookupOrganigrammaUfficioLiquidatoreButton = new ImgButtonItem("lookupOrganigrammaUfficioLiquidatoreButton", "lookup/organigramma.png",I18NUtil.getMessages().protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaUfficioLiquidatoreButton.setColSpan(1);
		lookupOrganigrammaUfficioLiquidatoreButton.addIconClickHandler(new IconClickHandler() {
				@Override
				public void onIconClick(IconClickEvent event) {
					UfficioLiquidatoreLookupOrganigramma ufficioLiquidatoreLookupOrganigrammaPopup = new UfficioLiquidatoreLookupOrganigramma(null);
					ufficioLiquidatoreLookupOrganigrammaPopup.show();
				}
		});
		lookupOrganigrammaUfficioLiquidatoreButton.setShowIfCondition(new FormItemIfFunction() {
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return showOrganigrammaItem();
				}
		});

		// ********************************************************************
		// COMBO ORGANIGRAMMA
		// ********************************************************************
		SelectGWTRestDataSource lUoUfficioLiquidatoreDataSource = new SelectGWTRestDataSource("LoadComboUoCollegateUtenteAdminDatasource", "id", FieldType.TEXT, new String[] { "descrizione" }, true);
		lUoUfficioLiquidatoreDataSource.addParam("finalita", null);
		lUoUfficioLiquidatoreDataSource.addParam("idUd", null);
		lUoUfficioLiquidatoreDataSource.addParam("tipoAssegnatari", null);

		uoUfficioLiquidatoreItem = new FilteredSelectItemWithDisplay("uoUfficioLiquidatore", lUoUfficioLiquidatoreDataSource) {
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
				uoLiquidatoreForm.setValue("codRapidoUfficioLiquidatore",    record.getAttributeAsString("codice"));
				uoLiquidatoreForm.setValue("idUoUfficioLiquidatore",         record.getAttributeAsString("idUo"));
				uoLiquidatoreForm.setValue("uoUfficioLiquidatore",           record.getAttributeAsString("id"));
				uoLiquidatoreForm.setValue("descrizioneUfficioLiquidatore",  record.getAttributeAsString("descrizioneOrig"));
				uoLiquidatoreForm.clearErrors(true);					
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						uoUfficioLiquidatoreItem.fetchData();
					}
				});
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				uoLiquidatoreForm.setValue("uoUfficioLiquidatore", "");
				uoLiquidatoreForm.setValue("codRapidoUfficioLiquidatore", "");
				uoLiquidatoreForm.setValue("idUoUfficioLiquidatore", "");
				uoLiquidatoreForm.setValue("typeNodoUfficioLiquidatore", "");
				uoLiquidatoreForm.setValue("descrizioneUfficioLiquidatore", "");
				uoLiquidatoreForm.clearErrors(true);					
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						uoUfficioLiquidatoreItem.fetchData();
					}
				});
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					uoLiquidatoreForm.setValue("uoUfficioLiquidatore", "");
					uoLiquidatoreForm.setValue("idUoUfficioLiquidatore", "");
					uoLiquidatoreForm.setValue("typeNodoUfficioLiquidatore", "");
					uoLiquidatoreForm.setValue("descrizioneUfficioLiquidatore", "");
					if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
						uoLiquidatoreForm.setValue("codRapidoUfficioLiquidatore", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						uoLiquidatoreForm.setValue("codRapidoUfficioLiquidatore", "");
					}					
					uoLiquidatoreForm.clearErrors(true);					
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						@Override
						public void execute() {
							uoUfficioLiquidatoreItem.fetchData();
						}
					});
				}
			}
		};
		uoUfficioLiquidatoreItem.setAutoFetchData(true);
		uoUfficioLiquidatoreItem.setFetchMissingValues(true);
		
		// Definizione colonne
		List<ListGridField> uoUfficioLiquidatoreOrganigrammaPickListFields = new ArrayList<ListGridField>();
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
			uoUfficioLiquidatoreOrganigrammaPickListFields.add(typeNodoField);
		}
		
		// Codice
		ListGridField codiceField = new ListGridField("codice", I18NUtil.getMessages().organigramma_list_codUoField_title());
		codiceField.setWidth(100);		
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			codiceField.setCanFilter(false);
		}
		uoUfficioLiquidatoreOrganigrammaPickListFields.add(codiceField);
		
		// Descrizione
		ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().organigramma_list_descrizioneField_title());
		uoUfficioLiquidatoreOrganigrammaPickListFields.add(descrizioneField);
		
		// Descrizione estesa
		ListGridField descrizioneEstesaField = new ListGridField("descrizioneEstesa", I18NUtil.getMessages().organigramma_list_descrizioneEstesaField_title());
		descrizioneEstesaField.setHidden(true);
		uoUfficioLiquidatoreOrganigrammaPickListFields.add(descrizioneEstesaField);
		
		// Icona punto di protocollo
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
						if (record != null && record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
							return "Punto di Protocollo";
						}
						return null;
					}
				});
				uoUfficioLiquidatoreOrganigrammaPickListFields.add(flgPuntoProtocolloField);
		}
		uoUfficioLiquidatoreItem.setPickListFields(uoUfficioLiquidatoreOrganigrammaPickListFields.toArray(new ListGridField[uoUfficioLiquidatoreOrganigrammaPickListFields.size()]));

		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			    uoUfficioLiquidatoreItem.setFilterLocally(false);
				uoUfficioLiquidatoreItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimOrganigrammaNonStdMessage());
		} else {
				uoUfficioLiquidatoreItem.setFilterLocally(true);
		}
		
		uoUfficioLiquidatoreItem.setAutoFetchData(true);
		uoUfficioLiquidatoreItem.setAlwaysFetchMissingValues(true);
		uoUfficioLiquidatoreItem.setFetchMissingValues(true);
		uoUfficioLiquidatoreItem.setValueField("id");
		uoUfficioLiquidatoreItem.setOptionDataSource(lUoUfficioLiquidatoreDataSource);
		uoUfficioLiquidatoreItem.setShowTitle(false);
		uoUfficioLiquidatoreItem.setWidth(500);
		uoUfficioLiquidatoreItem.setClearable(true);
		uoUfficioLiquidatoreItem.setShowIcons(true);
		uoUfficioLiquidatoreItem.setItemHoverFormatter(new FormItemHoverFormatter() {
				@Override
				public String getHoverHTML(FormItem item, DynamicForm form) {
					return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
				}
		});
				
		ListGrid uoUfficioLiquidatoreOrganigrammaPickListProperties = uoUfficioLiquidatoreItem.getPickListProperties();
		uoUfficioLiquidatoreOrganigrammaPickListProperties.addFetchDataHandler(new FetchDataHandler() {
				@Override
				public void onFilterData(FetchDataEvent event) {
					String codRapido = uoLiquidatoreForm.getValueAsString("codRapidoUfficioLiquidatore");
					GWTRestDataSource organigrammaDS = (GWTRestDataSource) uoUfficioLiquidatoreItem.getOptionDataSource();
					organigrammaDS.addParam("codice", codRapido);
					organigrammaDS.addParam("finalita", null);
					organigrammaDS.addParam("idUd", null);
					uoUfficioLiquidatoreItem.setOptionDataSource(organigrammaDS);
					uoUfficioLiquidatoreItem.invalidateDisplayValueCache();
				}
		});
		uoUfficioLiquidatoreItem.setPickListProperties(uoUfficioLiquidatoreOrganigrammaPickListProperties);

		SpacerItem spacer = new SpacerItem();
		spacer.setWidth(20);
		spacer.setColSpan(1);
			
		uoLiquidatoreForm.setFields( // hidden
				                     idUoUfficioLiquidatoreItem, typeNodoUfficioLiquidatoreItem, descrizioneUfficioLiquidatoreItem,
				                     // visibili
				                    codRapidoUfficioLiquidatoreItem, lookupOrganigrammaUfficioLiquidatoreButton, uoUfficioLiquidatoreItem);
		
		uoLiquidatoreDetailSection = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_uoLiquidatoreDetailSection_title(), true, true, false, uoLiquidatoreForm);
			
		uoLiquidatoreDetailSection.setVisible(isUoLiquidatoreDetailSectionVisible());
		
	}
	
	private void creaEstremiVariazioneDaStoricizzareForm() {
		
		estremiVariazioneDaStoricizzareForm = new DynamicForm();
		estremiVariazioneDaStoricizzareForm.setValuesManager(vm);
		estremiVariazioneDaStoricizzareForm.setWidth("100%");
		estremiVariazioneDaStoricizzareForm.setHeight("5");
		estremiVariazioneDaStoricizzareForm.setPadding(5);

		variazioneDati = new DateItem("variazioneDatiIn", I18NUtil.getMessages().organigramma_uo_detail_variazioniDatiIn_title());
		variazioneDati.setStartRow(true);
		variazioneDati.setRequired(true);
		variazioneDati.setWidth(100);
		variazioneDati.setDefaultValue(new Date());

		motiviVariazione = new TextItem("motiviVariazioneIn", I18NUtil.getMessages().organigramma_uo_detail_motiviVariazioneIn_title());
		motiviVariazione.setStartRow(true);
		motiviVariazione.setEndRow(false);
		motiviVariazione.setWidth(300);
		motiviVariazione.setColSpan(4);

		estremiVariazioneDaStoricizzareForm.setFields(variazioneDati, motiviVariazione);
		
		estremiVariazioneDaStoricizzareDetail = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_estremiVariazioneDaStoricizzareDetailSection_title(), true, true, false, estremiVariazioneDaStoricizzareForm);
		
		contattiOrganigrammaForm = new DynamicForm();
		contattiOrganigrammaForm.setValuesManager(vm);
		contattiOrganigrammaForm.setWidth("100%");
		contattiOrganigrammaForm.setHeight("5");
		contattiOrganigrammaForm.setPadding(5);

		contattiOrganigrammaItem = new ContattiOrganigrammaItem() {

			@Override
			public boolean isNewMode() {
				return (getLayout().getMode() != null && "new".equals(getLayout().getMode()));
			}
		};
		contattiOrganigrammaItem.setName("listaContatti");
		contattiOrganigrammaItem.setShowTitle(false);

		contattiOrganigrammaForm.setFields(contattiOrganigrammaItem);
		
		detailSectionContattiOrganigramma = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_contattiOrganigrammaDetailSection_title(), true, true, false, contattiOrganigrammaForm){
			
			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return true;
			}
		};
	}
	
	private void creaOpzioniForm() {
	
		formOpzioni = new DynamicForm();
		formOpzioni.setWidth(1);
		formOpzioni.setValuesManager(vm);
		formOpzioni.setWrapItemTitles(false);
		formOpzioni.setOverflow(Overflow.VISIBLE);
		formOpzioni.setNumCols(6);
		formOpzioni.setColWidths(1, 1, 1, 1, 1, "*");
		
		// Riga 1
		flgPuntoDiProtocollo = new CheckboxItem("flgPuntoProtocollo", I18NUtil.getMessages().organigramma_uo_detail_flgPuntoProtocolloItem_title());
		flgPuntoDiProtocollo.setColSpan(1);
		flgPuntoDiProtocollo.setWidth(50);
		flgPuntoDiProtocollo.setStartRow(false);
		flgPuntoDiProtocollo.setShowIfCondition(new FormItemIfFunction() {
					@Override
					public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
						return OrganigrammaLayout.isAttivoGestionePuntiProtocollo();
					}
				});
		flgPuntoDiProtocollo.addChangedHandler(new ChangedHandler() {			
					@Override
					public void onChanged(ChangedEvent event) {
						associaPuntiProtocolloDetailSection.setVisible(flgPuntoDiProtocollo != null ? !flgPuntoDiProtocollo.getValueAsBoolean() : false);
						associaPuntiProtocolloDetailSection.markForRedraw();
					}
				});	
			
		flgPuntoRaccoltaEmail = new CheckboxItem("flgPuntoRaccoltaEmail", I18NUtil.getMessages().organigramma_uo_detail_flgPuntoRaccoltaEmail_title());
		flgPuntoRaccoltaEmail.setValue(false);
		flgPuntoRaccoltaEmail.setColSpan(1);
		flgPuntoRaccoltaEmail.setWidth(10);
		flgPuntoRaccoltaEmail.setStartRow(true);

		flgPuntoRaccoltaDocumenti = new CheckboxItem("flgPuntoRaccoltaDocumenti", I18NUtil.getMessages().organigramma_uo_detail_flgPuntoRaccoltaDocumenti_title());
		flgPuntoRaccoltaDocumenti.setValue(false);
		flgPuntoRaccoltaDocumenti.setColSpan(1);
		flgPuntoRaccoltaDocumenti.setWidth(10);
		flgPuntoRaccoltaDocumenti.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return !OrganigrammaLayout.isAttivoGestionePuntiProtocollo();
			}
		});
				
		// Riga 2
		flgInibitaAssegnazione = new CheckboxItem("flgInibitaAssegnazione", I18NUtil.getMessages().organigramma_uo_detail_flgInibitaAssegnazione_title());
		flgInibitaAssegnazione.setValue(false);
		flgInibitaAssegnazione.setColSpan(1);
		flgInibitaAssegnazione.setWidth(10);
		flgInibitaAssegnazione.setStartRow(true);

		flgInibitoInvioCC = new CheckboxItem("flgInibitoInvioCC", I18NUtil.getMessages().organigramma_uo_detail_flgInibitoInvioCC_title());
		flgInibitoInvioCC.setValue(false);
		flgInibitoInvioCC.setWidth(10);
		flgInibitoInvioCC.setColSpan(1);
		
		flgInibitaAssegnazioneEmail = new CheckboxItem("flgInibitaAssegnazioneEmail", I18NUtil.getMessages().organigramma_uo_detail_flgInibitaAssegnazioneEmail_title());
		flgInibitaAssegnazioneEmail.setValue(false);
		flgInibitaAssegnazioneEmail.setWidth(10);
		flgInibitaAssegnazioneEmail.setColSpan(1);

		// Riga 3
		inibitaPreimpDestProtEntrataItem = new CheckboxItem("inibitaPreimpDestProtEntrata", I18NUtil.getMessages().organigramma_uo_detail_inibitaPreimpDestProtEntrata_title());
		inibitaPreimpDestProtEntrataItem.setColSpan(1);
		inibitaPreimpDestProtEntrataItem.setWidth(50);
		inibitaPreimpDestProtEntrataItem.setStartRow(true);
		inibitaPreimpDestProtEntrataItem.setColSpan(6);
		inibitaPreimpDestProtEntrataItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return OrganigrammaLayout.isInibitaPreimpDestProtEntrata();
			}
		});
		inibitaPreimpDestProtEntrataItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
					
		// Riga 4
		abilitaUoProtEntrataItem = new CheckboxItem("abilitaUoProtEntrata", I18NUtil.getMessages().organigramma_uo_detail_abilitaUoProtEntrata_title());
		abilitaUoProtEntrataItem.setColSpan(1);
		abilitaUoProtEntrataItem.setWidth(50);
		abilitaUoProtEntrataItem.setStartRow(true);
		abilitaUoProtEntrataItem.setColSpan(6);
		abilitaUoProtEntrataItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return AurigaLayout.isAttivoClienteA2A();
			}
		});
		abilitaUoProtEntrataItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		// Riga 5
		abilitaUoProtUscitaItem = new CheckboxItem("abilitaUoProtUscita", I18NUtil.getMessages().organigramma_uo_detail_abilitaUoProtUscita_title());
		abilitaUoProtUscitaItem.setColSpan(1);
		abilitaUoProtUscitaItem.setWidth(50);
		abilitaUoProtUscitaItem.setStartRow(true);
		abilitaUoProtUscitaItem.setColSpan(6);
		abilitaUoProtUscitaItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return AurigaLayout.isAttivoClienteA2A();
			}
		});
		abilitaUoProtUscitaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
				
		// Riga 6
		abilitaUoProtUscitaFullItem = new CheckboxItem("abilitaUoProtUscitaFull", I18NUtil.getMessages().organigramma_uo_detail_abilitaUoProtUscitaFull_title());
		abilitaUoProtUscitaFullItem.setColSpan(1);
		abilitaUoProtUscitaFullItem.setWidth(50);
		abilitaUoProtUscitaFullItem.setStartRow(true);
		abilitaUoProtUscitaFullItem.setColSpan(6);
		abilitaUoProtUscitaFullItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return OrganigrammaLayout.isAbilitaUoProtUscitaFull();
			}
		});
		abilitaUoProtUscitaFullItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
				
		// Riga 7
		abilitaUoAssRiservatezzaItem = new CheckboxItem("abilitaUoAssRiservatezza", I18NUtil.getMessages().organigramma_uo_detail_abilitaUoAssRiservatezza_title());
		abilitaUoAssRiservatezzaItem.setColSpan(1);
		abilitaUoAssRiservatezzaItem.setWidth(50);
		abilitaUoAssRiservatezzaItem.setStartRow(true);
		abilitaUoAssRiservatezzaItem.setColSpan(6);
		abilitaUoAssRiservatezzaItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return AurigaLayout.isAttivoClienteA2A();
			}
		});
		abilitaUoAssRiservatezzaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		// Riga 8
		abilitaUoGestMulteItem = new CheckboxItem("abilitaUoGestMulte", I18NUtil.getMessages().organigramma_uo_detail_abilitaUoGestMulte_title());
		abilitaUoGestMulteItem.setColSpan(1);
		abilitaUoGestMulteItem.setWidth(50);
		abilitaUoGestMulteItem.setStartRow(true);
		abilitaUoGestMulteItem.setColSpan(6);
		abilitaUoGestMulteItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return AurigaLayout.isAttivoClienteA2A();
			}
		});
		abilitaUoGestMulteItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		// Riga 9
		abilitaUoGestContrattiItem = new CheckboxItem("abilitaUoGestContratti", I18NUtil.getMessages().organigramma_uo_detail_abilitaUoGestContratti_title());
		abilitaUoGestContrattiItem.setColSpan(1);
		abilitaUoGestContrattiItem.setWidth(50);
		abilitaUoGestContrattiItem.setStartRow(true);
		abilitaUoGestContrattiItem.setColSpan(6);
		abilitaUoGestContrattiItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return AurigaLayout.isAttivoClienteA2A();
			}
		});
		abilitaUoGestContrattiItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		
		// Riga 10
		abilitaUoScansioneMassivaItem = new CheckboxItem("abilitaUoScansioneMassiva", I18NUtil.getMessages().organigramma_uo_detail_abilitaUoScansioneMassiva_title());
		abilitaUoScansioneMassivaItem.setColSpan(1);
		abilitaUoScansioneMassivaItem.setWidth(50);
		abilitaUoScansioneMassivaItem.setStartRow(true);
		abilitaUoScansioneMassivaItem.setColSpan(6);
		abilitaUoScansioneMassivaItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return OrganigrammaLayout.isAbilitaUoScansioneMassiva();
			}
		});
		abilitaUoScansioneMassivaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		// Riga 11
		storicizzaDatiVariatiIn = new CheckboxItem("storicizzaDatiVariati", I18NUtil.getMessages().organigramma_uo_detail_flgStoricizzaDatiVariati_title());
		storicizzaDatiVariatiIn.setWidth(10);
		storicizzaDatiVariatiIn.setStartRow(true);
		storicizzaDatiVariatiIn.setColSpan(6);
		storicizzaDatiVariatiIn.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (storicizzaDatiVariatiIn.getValueAsBoolean() != null && storicizzaDatiVariatiIn.getValueAsBoolean())
					estremiVariazioneDaStoricizzareDetail.show();
				else
					estremiVariazioneDaStoricizzareDetail.hide();
				return (getLayout().getMode() != null ? getLayout().getMode().equals("edit") : false);
			}
		});
		storicizzaDatiVariatiIn.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (storicizzaDatiVariatiIn.getValueAsBoolean() != null && storicizzaDatiVariatiIn.getValueAsBoolean())
					estremiVariazioneDaStoricizzareDetail.show();
				else
					estremiVariazioneDaStoricizzareDetail.hide();
				
				formDatiGenerali.redraw();
			}
		});
								
		formOpzioni.setFields(flgPuntoDiProtocollo,flgPuntoRaccoltaEmail,flgPuntoRaccoltaDocumenti,
				              flgInibitaAssegnazione,flgInibitoInvioCC,flgInibitaAssegnazioneEmail,
				              inibitaPreimpDestProtEntrataItem,				              
				              abilitaUoProtEntrataItem,
				              abilitaUoProtUscitaItem,
				              abilitaUoProtUscitaFullItem,
				              abilitaUoAssRiservatezzaItem,
				              abilitaUoGestMulteItem,
				              abilitaUoGestContrattiItem,
				              abilitaUoScansioneMassivaItem,
				              storicizzaDatiVariatiIn
							 );
		
		formOpzioniSection = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_formOpzioniSection_title(), true, true, false, formOpzioni);
	}
		
	private void creaAssegnazioniUnitaPersonaleStrutturaForm(){
		
		formAssegnazioniUnitaPersonaleStruttura = new DynamicForm();
		formAssegnazioniUnitaPersonaleStruttura.setValuesManager(vm);
		formAssegnazioniUnitaPersonaleStruttura.setWrapItemTitles(false);
		formAssegnazioniUnitaPersonaleStruttura.setNumCols(15);
		formAssegnazioniUnitaPersonaleStruttura.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		flgEreditaAssInvioUPItem  = new HiddenItem("flgEreditaAssInvioUP");
		
		LinkedHashMap<String, String> flgAssInvioUPMap = new LinkedHashMap<String, String>();  
		flgAssInvioUPMap.put("0", "mai");  
		flgAssInvioUPMap.put("1", "solo da parte di persone della struttura");  
		flgAssInvioUPMap.put("2", "solo da parte di persone della struttura o delle strutture sovraordinate");  
		flgAssInvioUPMap.put("3", "sempre");
		
		flgAssInvioUPItem = new RadioGroupItem("flgAssInvioUP");
		flgAssInvioUPItem.setTitle(I18NUtil.getMessages().organigramma_uo_detail_flgAssInvioUP_title());
		flgAssInvioUPItem.setShowTitle(false);
		flgAssInvioUPItem.setVertical(false);
		flgAssInvioUPItem.setStartRow(true);
		
		flgAssInvioUPItem.setValueMap(flgAssInvioUPMap);
		flgAssInvioUPItem.setDefaultValue(3);
		flgAssInvioUPItem.setColSpan(15);
		
		flgPropagaAssInvioUPItem = new CheckboxItem("flgPropagaAssInvioUP", I18NUtil.getMessages().organigramma_uo_detail_flgPropagaAssInvioUP_title());
		flgPropagaAssInvioUPItem.setValue(false);
		flgPropagaAssInvioUPItem.setWidth(10);
		flgPropagaAssInvioUPItem.setColSpan(1);
		flgPropagaAssInvioUPItem.setStartRow(true);
				
		formAssegnazioniUnitaPersonaleStruttura.setFields(flgAssInvioUPItem, flgPropagaAssInvioUPItem, flgEreditaAssInvioUPItem);
		
		formAssegnazioniUnitaPersonaleStrutturaSection = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_formAssegnazioniUnitaPersonaleStrutturaSection_title(), true, true, false, formAssegnazioniUnitaPersonaleStruttura);
	}
	
	private void creaMainForm() {
		
		// LIVELLO
		formLivello = new DynamicForm();
		formLivello.setWidth(1);
		formLivello.setValuesManager(vm);
		formLivello.setWrapItemTitles(false);
		formLivello.setOverflow(Overflow.VISIBLE);
		formLivello.setNumCols(6);
		formLivello.setColWidths(1, 1, 1, 1, 1, 1);
		
		livelloCorrente = new HiddenItem("livelloCorrente");				
		livelloCorrenteOrig = new HiddenItem("livelloCorrenteOrig");	
		
		flgIgnoreWarning = new HiddenItem("flgIgnoreWarning");
		flgIgnoreWarning.setDefaultValue(0);
				
		final StaticTextItem livelloCorrenteLabel = new StaticTextItem("livelloCorrenteLabel", setTitleAligned(I18NUtil.getMessages().organigramma_uo_detail_livello_title()));
		livelloCorrenteLabel.setStartRow(true);
		livelloCorrenteLabel.setAttribute("obbligatorio", true);
		livelloCorrenteLabel.setAlign(Alignment.RIGHT);
		livelloCorrenteLabel.setWidth(1);
		livelloCorrenteLabel.setWrap(false);		
		livelloCorrenteLabel.setValueFormatter(new FormItemValueFormatter() {
			@Override
			public String formatValue(Object value, Record record, DynamicForm form,
					FormItem item) {
				String label = null;
				String livelloCorrente = vm.getValueAsString("livelloCorrente");
				if(livelloCorrente != null && !"".equals(livelloCorrente)) {
					label = livelloCorrente.replace(".", "&nbsp;.&nbsp;") + " .";
					StringSplitterClient st = new StringSplitterClient(livelloCorrente, ".");									
				} else {
					label = "";					
				}				
				return label;
			}
		});		
		livelloCorrenteLabel.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				String livelloCorrente = vm.getValueAsString("livelloCorrente");
				if (livelloCorrente != null && !"".equals(livelloCorrente)) {
					livello.setShowTitle(false);
					livello.setColSpan(1);	
					return true;
				} else {
					livello.setShowTitle(true);
					livello.setColSpan(2);	
					return false;
				}
			}
		});
		
		//LIVELLO CORRENTE DA CONCATENARE AI PRECEDENTI
		livello = new TextItem("livello", setTitleAligned(I18NUtil.getMessages().organigramma_uo_detail_livello_title()));		
		livello.setRequired(true);
		livello.setWidth(100);
		
		nroLivello = new SelectItem("nroLivello", I18NUtil.getMessages().organigramma_uo_detail_nroLivello_title());		
		nroLivello.setValueMap(new String[0]);
		nroLivello.setWidth(100);
		nroLivello.setRequired(true);
		nroLivello.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {

				String livelloCorrente = vm.getValueAsString("livelloCorrenteOrig");
				int nroLivelloDefault = 1;
				if(livelloCorrente != null && !"".equals(livelloCorrente)) {
					StringSplitterClient st = new StringSplitterClient(livelloCorrente, ".");
					nroLivelloDefault = st.getTokens().length + 1;					
				}
				if(vm.getValueAsString("nroLivello") != null && !"".equals(vm.getValueAsString("nroLivello"))) {
					int nroLivello = Integer.parseInt(vm.getValueAsString("nroLivello"));
					if(nroLivello > nroLivelloDefault) {
						for(int i = 0; i < nroLivello-nroLivelloDefault; i++) {
							if(livelloCorrente != null && !"".equals(livelloCorrente)) {
								livelloCorrente += ".--";
							} else {
								livelloCorrente = "--";
							}
						}
					}
				}
				vm.setValue("livelloCorrente", livelloCorrente);		
				reloadListaTipi(vm.getValueAsString("nroLivello"));	
				formLivello.markForRedraw();
			}
		});		
		
		ImgButtonItem variazioniButton = new ImgButtonItem("variazioniButton", "protocollazione/operazioniEffettuate.png", "Visualizza variazioni");
		variazioniButton.setAlwaysEnabled(true);
		variazioniButton.setColSpan(1);
		variazioniButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(getValuesManager().getValues());
				String idUo = record.getAttributeAsString("idUoSvUt") != null ? record.getAttributeAsString("idUoSvUt") : null;
				return idUo != null && !"".equals(idUo);
			}
		});
		variazioniButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {				
				final Record record = new Record(getValuesManager().getValues());
				final String idUo = record.getAttributeAsString("idUoSvUt") != null ? record.getAttributeAsString("idUoSvUt") : null;							
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("VariazioniDatiUoSvDataSource");
				lGwtRestDataSource.addParam("idUoSv", idUo);			
				lGwtRestDataSource.addParam("flgUoSv", "UO");				
				lGwtRestDataSource.fetchData(null, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							if(response.getData() != null && response.getData().length > 0) {
								String estremi = "";
								if (record.getAttributeAsString("livelloCorrente") != null && !"".equals(record.getAttributeAsString("livelloCorrente"))) {
									estremi = record.getAttributeAsString("livelloCorrente") + "." + record.getAttributeAsString("livello") + " - "
											+ record.getAttributeAsString("denominazioneEstesa");
								} else {
									estremi = record.getAttributeAsString("livello") + " - " + record.getAttributeAsString("denominazioneEstesa");
								}	
								new VariazioniDatiUoSvWindow(idUo, "UO", estremi);
							} else {
								AurigaLayout.addMessage(new MessageBean("Non c'è nessuna variazione storicizzata da visualizzare", "", MessageType.ERROR));							
							}
						}
					}
				});								
			}
		});
		
		formLivello.setFields(
			livelloCorrente, livelloCorrenteOrig, flgIgnoreWarning,
			livelloCorrenteLabel, livello, nroLivello, variazioniButton		
		);

		// DATI GENERALI
		formDatiGenerali = new DynamicForm();
		formDatiGenerali.setValuesManager(vm);
		formDatiGenerali.setWrapItemTitles(false);
		formDatiGenerali.setNumCols(10);
		formDatiGenerali.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		
		idUoSvUt = new HiddenItem("idUoSvUt");
		
		final GWTRestDataSource listaTipiDS = new GWTRestDataSource("LoadComboTipoOrganigrammaDataSource", "key", FieldType.TEXT);
		// SELEZIONA TIPO ORGANIGRAMMA
		tipo = new SelectItem("tipo", setTitleAligned(I18NUtil.getMessages().organigramma_uo_detail_tipo_title()));
		tipo.setValueField("key");
		tipo.setDisplayField("value");
		tipo.setOptionDataSource(listaTipiDS);
		tipo.setWidth(370);
		tipo.setRequired(true);
		tipo.setCachePickListResults(false);
		tipo.setDefaultToFirstOption(true);

		
		// ufficio gare/appalti
		flgUfficioGareAppalti = new CheckboxItem("flgUfficioGareAppalti", I18NUtil.getMessages().organigramma_uo_detail_flgUfficioGareAppalti_title());
		flgUfficioGareAppalti.setValue(false);
		flgUfficioGareAppalti.setColSpan(8);
		flgUfficioGareAppalti.setWidth(10);
		flgUfficioGareAppalti.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return OrganigrammaLayout.isAttivaGestioneUfficioGare();
			}
		});
		
		
		// denominazione
		denominazioneEstesa = new TextItem("denominazioneEstesa", setTitleAligned(I18NUtil.getMessages().organigramma_uo_detail_denominzazione_title()));
		denominazioneEstesa.setRequired(true);
		denominazioneEstesa.setStartRow(true);
		denominazioneEstesa.setEndRow(false);
		denominazioneEstesa.setWidth(680);
		denominazioneEstesa.setColSpan(10);
		
		denominazioneBreve = new TextItem("denominazioneBreve", setTitleAligned(I18NUtil.getMessages().organigramma_uo_detail_denominzazione_title_breve()));
		denominazioneBreve.setRequired(false);
		denominazioneBreve.setStartRow(true);
		denominazioneBreve.setWidth(370);
		
		ciProvUO = new TextItem("ciProvUO", setTitleAligned(I18NUtil.getMessages().organigramma_uo_detail_ciProvUO_title()));
		ciProvUO.setWidth(155);
		
		// Assessore di riferimento
		final GWTRestDataSource listaAssessoriDS = new GWTRestDataSource("LoadComboAssessoriDataSource", "key", FieldType.TEXT);
		idAssessoreRif = new SelectItem("idAssessoreRif", setTitleAligned(I18NUtil.getMessages().organigramma_uo_detail_assessoreRif_title()));
		idAssessoreRif.setValueField("key");
		idAssessoreRif.setDisplayField("value");
		idAssessoreRif.setOptionDataSource(listaAssessoriDS);
		idAssessoreRif.setWidth(700);
		idAssessoreRif.setRequired(false);
		idAssessoreRif.setCachePickListResults(false);
		idAssessoreRif.setColSpan(10);
		idAssessoreRif.setStartRow(true);
		idAssessoreRif.setMultiple(true);
		idAssessoreRif.setClearable(true);
		idAssessoreRif.setAllowEmptyValue(true);
		idAssessoreRif.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("GESTIONE_ATTI_COMPLETA");
			}
		});
				
		presenteAttoDefOrganigrammaItem = new RadioGroupItem("presenteAttoDefOrganigramma");
		presenteAttoDefOrganigrammaItem.setTitle(I18NUtil.getMessages().organigramma_uo_detail_presenteAttoDefOrganigramma_title());
		presenteAttoDefOrganigrammaItem.setVertical(false);
		presenteAttoDefOrganigrammaItem.setStartRow(true);
		presenteAttoDefOrganigrammaItem.setValueMap("Si", "No");
		presenteAttoDefOrganigrammaItem.setAttribute("obbligatorio", true);
		presenteAttoDefOrganigrammaItem.setColSpan(10);
		presenteAttoDefOrganigrammaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("SHOW_FLG_UO_ATTO_ORGANIGRAMMA");
			}
		});
		presenteAttoDefOrganigrammaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		presenteAttoDefOrganigrammaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return AurigaLayout.getParametroDBAsBoolean("SHOW_FLG_UO_ATTO_ORGANIGRAMMA");
			}
		}));
		
		dataIstituitaItem = new DateItem("dataIstituita", setTitleAligned(I18NUtil.getMessages().organigramma_uo_detail_dataIstituita_title()));
		dataIstituitaItem.setStartRow(true);
		dataIstituitaItem.setColSpan(4);
		dataIstituitaItem.setRequired(true);
		dataIstituitaItem.setColSpan(10);

		dataCessazioneItem = new DateItem("dataSoppressa", setTitleAligned(I18NUtil.getMessages().organigramma_uo_detail_dataSoppressa_title()));
		dataCessazioneItem.setStartRow(true);
		dataCessazioneItem.setColSpan(4);
		dataCessazioneItem.setColSpan(10);
		
		dataCessazioneItem.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				updateSpostaDocFascMailSection(event.getItem().getValue());
			}
		});	
		
		dataCessazioneItem.addBlurHandler(new BlurHandler() {			
			@Override
			public void onBlur(BlurEvent event) {	
				updateSpostaDocFascMailSection(event.getItem().getValue());
			}
		});	
		
		SpacerItem spacer = new SpacerItem();
		spacer.setWidth(20);
		spacer.setColSpan(1);
		
		formDatiGenerali.setFields( // hidden 
				                      idUoSvUt,
				                      // visibili
									  tipo, flgUfficioGareAppalti,
				                      denominazioneEstesa, 
				                      denominazioneBreve, 
				                      ciProvUO, 
				                      idAssessoreRif, 
				                      presenteAttoDefOrganigrammaItem,
				                      dataIstituitaItem, 
				                      dataCessazioneItem);
		
		formDatiPrincipaliSection = new DetailSection(I18NUtil.getMessages().organigramma_uo_detail_formDatiPrincipaliSection_title(), true, true, false, formLivello,formDatiGenerali );		
	}
	
	/*
	private void creaLivelloForm() {
		
		formLivello = new DynamicForm();
		formLivello.setWidth(1);
		formLivello.setValuesManager(vm);
		formLivello.setWrapItemTitles(false);
		formLivello.setOverflow(Overflow.VISIBLE);
		formLivello.setNumCols(6);
		
		livelloCorrente = new HiddenItem("livelloCorrente");				
		livelloCorrenteOrig = new HiddenItem("livelloCorrenteOrig");	
		
		flgIgnoreWarning = new HiddenItem("flgIgnoreWarning");
		flgIgnoreWarning.setDefaultValue(0);
				
		final StaticTextItem livelloCorrenteLabel = new StaticTextItem("livelloCorrenteLabel", setTitleAligned(I18NUtil.getMessages().organigramma_uo_detail_livello_title()));
		livelloCorrenteLabel.setStartRow(true);
		livelloCorrenteLabel.setAttribute("obbligatorio", true);
		livelloCorrenteLabel.setAlign(Alignment.RIGHT);
		livelloCorrenteLabel.setWidth(1);
		livelloCorrenteLabel.setWrap(false);		
		livelloCorrenteLabel.setValueFormatter(new FormItemValueFormatter() {
			@Override
			public String formatValue(Object value, Record record, DynamicForm form,
					FormItem item) {
				String label = null;
				String livelloCorrente = vm.getValueAsString("livelloCorrente");
				if(livelloCorrente != null && !"".equals(livelloCorrente)) {
					label = livelloCorrente.replace(".", "&nbsp;.&nbsp;") + " .";
					StringSplitterClient st = new StringSplitterClient(livelloCorrente, ".");									
				} else {
					label = "";					
				}				
				return label;
			}
		});		
		livelloCorrenteLabel.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				String livelloCorrente = vm.getValueAsString("livelloCorrente");
				if (livelloCorrente != null && !"".equals(livelloCorrente)) {
					livello.setShowTitle(false);
					livello.setColSpan(1);	
					return true;
				} else {
					livello.setShowTitle(true);
					livello.setColSpan(2);	
					return false;
				}
			}
		});
		
		//LIVELLO CORRENTE DA CONCATENARE AI PRECEDENTI
		livello = new TextItem("livello", setTitleAligned(I18NUtil.getMessages().organigramma_uo_detail_livello_title()));		
		livello.setRequired(true);
		livello.setWidth(100);
		
		nroLivello = new SelectItem("nroLivello", I18NUtil.getMessages().organigramma_uo_detail_nroLivello_title());		
		nroLivello.setValueMap(new String[0]);
		nroLivello.setWidth(100);
		nroLivello.setRequired(true);
		nroLivello.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {

				String livelloCorrente = vm.getValueAsString("livelloCorrenteOrig");
				int nroLivelloDefault = 1;
				if(livelloCorrente != null && !"".equals(livelloCorrente)) {
					StringSplitterClient st = new StringSplitterClient(livelloCorrente, ".");
					nroLivelloDefault = st.getTokens().length + 1;					
				}
				if(vm.getValueAsString("nroLivello") != null && !"".equals(vm.getValueAsString("nroLivello"))) {
					int nroLivello = Integer.parseInt(vm.getValueAsString("nroLivello"));
					if(nroLivello > nroLivelloDefault) {
						for(int i = 0; i < nroLivello-nroLivelloDefault; i++) {
							if(livelloCorrente != null && !"".equals(livelloCorrente)) {
								livelloCorrente += ".--";
							} else {
								livelloCorrente = "--";
							}
						}
					}
				}
				vm.setValue("livelloCorrente", livelloCorrente);		
				reloadListaTipi(vm.getValueAsString("nroLivello"));	
				formLivello.markForRedraw();
			}
		});		
		
		ImgButtonItem variazioniButton = new ImgButtonItem("variazioniButton", "protocollazione/operazioniEffettuate.png", "Visualizza variazioni");
		variazioniButton.setAlwaysEnabled(true);
		variazioniButton.setColSpan(1);
		variazioniButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(getValuesManager().getValues());
				String idUo = record.getAttributeAsString("idUoSvUt") != null ? record.getAttributeAsString("idUoSvUt") : null;
				return idUo != null && !"".equals(idUo);
			}
		});
		variazioniButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {				
				final Record record = new Record(getValuesManager().getValues());
				final String idUo = record.getAttributeAsString("idUoSvUt") != null ? record.getAttributeAsString("idUoSvUt") : null;							
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("VariazioniDatiUoSvDataSource");
				lGwtRestDataSource.addParam("idUoSv", idUo);			
				lGwtRestDataSource.addParam("flgUoSv", "UO");				
				lGwtRestDataSource.fetchData(null, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							if(response.getData() != null && response.getData().length > 0) {
								String estremi = "";
								if (record.getAttributeAsString("livelloCorrente") != null && !"".equals(record.getAttributeAsString("livelloCorrente"))) {
									estremi = record.getAttributeAsString("livelloCorrente") + "." + record.getAttributeAsString("livello") + " - "
											+ record.getAttributeAsString("denominazioneEstesa");
								} else {
									estremi = record.getAttributeAsString("livello") + " - " + record.getAttributeAsString("denominazioneEstesa");
								}	
								new VariazioniDatiUoSvWindow(idUo, "UO", estremi);
							} else {
								AurigaLayout.addMessage(new MessageBean("Non c'è nessuna variazione storicizzata da visualizzare", "", MessageType.ERROR));							
							}
						}
					}
				});								
			}
		});
		
		formLivello.setFields(
			livelloCorrente, livelloCorrenteOrig, flgIgnoreWarning,
			livelloCorrenteLabel, livello, nroLivello, variazioniButton		
		);
		
		addSubForm(formLivello);
	}
	
	*/
	
	private boolean showOrganigrammaItem() {
		return (mode != null && ("new".equals(mode) || "edit".equals(mode))) && isAbilInserireModificareSoggInQualsiasiUo();
	}
	
	public boolean isAbilInserireModificareSoggInQualsiasiUo() {
		// Se l’utente HA il privilegio REP/STD si mostrano tutte le UO, se NON ha il privilegio si mostra la select con le UO collegate all’utente
		return Layout.isPrivilegioAttivo("REP/STD");
	}
	
	public class UfficioLiquidatoreLookupOrganigramma extends LookupOrganigrammaPopup {
		public UfficioLiquidatoreLookupOrganigramma(Record record) {
			super(record, true, 0);
		}
		@Override
		public void manageLookupBack(Record record) {
			setUoLiquidatoreFormValuesFromRecord(record);
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
		@Override
		public String getIdUd() {
			return null;
		}
	}

	public void setUoLiquidatoreFormValuesFromRecord(Record record) {
		String idOrganigramma = record.getAttribute("idUoSvUt");
		String tipo = record.getAttribute("tipo");
		int pos = tipo.indexOf("_");
		if (pos != -1) {
			tipo = tipo.substring(0, pos);
		}
		uoLiquidatoreForm.setValue("uoUfficioLiquidatore", tipo + idOrganigramma);
		uoLiquidatoreForm.setValue("idUoUfficioLiquidatore", idOrganigramma);
		uoLiquidatoreForm.setValue("typeNodoUfficioLiquidatore", tipo);
		uoLiquidatoreForm.setValue("descrizioneUfficioLiquidatore", ""); 
		uoLiquidatoreForm.setValue("codRapidoUfficioLiquidatore", record.getAttribute("codRapidoUo"));
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			if(record.getAttribute("codRapidoUo") == null || "".equalsIgnoreCase(record.getAttribute("codRapidoUo"))) {			
				uoLiquidatoreForm.setValue("codRapidoUfficioLiquidatore", AurigaLayout.getCodRapidoOrganigramma());						
			}
		}	
		uoLiquidatoreForm.clearErrors(true);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				uoUfficioLiquidatoreItem.fetchData();
			}
		});
	}
}