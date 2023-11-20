/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.gestioneUtenti.SelezionaTipiDiAttoPopup;
import it.eng.auriga.ui.module.layout.client.gestioneUtenti.UOCollegatePuntoProtocolloPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
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
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class PostazioneDetail extends CustomDetail {

	// DetailSection
	protected DetailSection detailSectionUtenteDaSostituireCon;
	protected DetailSection detailSectionUtenteCorrente;
	private DetailSection uoSpostaDocFascSection;
	private DetailSection resocontoSpostamentoDocFascSection;
	
	// DynamicForm
	private DynamicForm formUtenteDaSostituireCon;
	private DynamicForm formUtenteCorrente;
	private DynamicForm uoSpostaDocFascForm;
	private DynamicForm resocontoSpostamentoDocFascForm;
	
	// SelectItem
	private SelectItem tipoDiAssegnazioneNewSelectItem;
	private SelectItem selezionaRuoloNewItem;
	private SelectItem tipoDiAssegnazioneSelectItem;
	private SelectItem selezionaRuoloItem;
	
	// FilteredSelectItemWithDisplay
	private FilteredSelectItemWithDisplay utentiNewItem;
	private FilteredSelectItemWithDisplay organigrammaSpostaDocFascItem;
	private FilteredSelectItemWithDisplay utentiItem;
	
	// CheckboxItem
	private CheckboxItem flgInclSottoUoItem;
	private CheckboxItem flgInclScrivVirtItem;
	private CheckboxItem flgAccessoDocLimSVItem;
	private CheckboxItem flgRegistrazioneEItem;
	private CheckboxItem flgRegistrazioneUIItem;
	private CheckboxItem flgGestAttiItem;
	private CheckboxItem flgVisPropAttiInIterItem;
	private CheckboxItem flgRiservatezzaRelUserUoItem;
	private CheckboxItem flgGestAttiTuttiItem;
	private CheckboxItem flgVisPropAttiInIterTuttiItem;
	
	private CheckboxItem flgInclSottoUoNewItem;
	private CheckboxItem flgInclScrivVirtNewItem;
	private CheckboxItem flgAccessoDocLimSVNewItem;
	private CheckboxItem flgRegistrazioneENewItem;
	private CheckboxItem flgRegistrazioneUINewItem;
	private CheckboxItem flgGestAttiNewItem;
	private CheckboxItem flgVisPropAttiInIterNewItem;
	private CheckboxItem flgRiservatezzaRelUserUoNewItem;
	private CheckboxItem flgGestAttiTuttiNewItem;
	private CheckboxItem flgVisPropAttiInIterTuttiNewItem;
	
	
	// DateItem
	private DateItem dataDalNewItem;
	private DateItem dataAlNewItem;
	private DateItem dataDalItem;
	private DateItem dataAlItem;
	private DateTimeItem dataConteggioDocAssegnatiItem;
	private DateTimeItem dataConteggioFascAssegnatiItem;
	private DateTimeItem dataInizioSpostamentoDocFascItem;
	private DateTimeItem dataFineSpostamentoDocFascItem;

	// TextItem
	private TextItem nomePostazioneItem;
	private TextItem descUoSpostamentoDocFascItem;
	private TextItem statoSpostamentoDocFascItem;
	
	// NumericItem
	private NumericItem nrDocAssegnatiItem;
	private NumericItem nrFascAssegnatiItem;
	
	// ImgButtonItem
	private ImgButtonItem uoCollegatePuntoProtocolloButtonFormUtenteCorrente;
	private ImgButtonItem uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon;
	private ImgButtonItem lookupOrganigrammaSpostaDocFascButton;
	private ImgButtonItem selezionaTipiGestAttiButton;
	private ImgButtonItem selezionaTipiVisPropAttiInIterButton;
	private ImgButtonItem selezionaTipiGestAttiNewButton;
	private ImgButtonItem selezionaTipiVisPropAttiInIterNewButton;
	
	// ExtendedTextItem
	private ExtendedTextItem codRapidoSpostaDocFascItem;

    // SpacerItem
	private SpacerItem spacer1Item;
	private SpacerItem spacer2Item;
	private SpacerItem spacerFlgRiservatezzaRelUserUoItem;
	private SpacerItem spacerFlgRiservatezzaRelUserUoNewItem;
	
	
	
	// HiddenItem
	private HiddenItem ciRelUserUoItem;
	private HiddenItem idScrivaniaItem;
	private HiddenItem idUoItem;
	private HiddenItem sostituzioneSVItem;
	private HiddenItem intestazioneItem;
	private HiddenItem flgSpostamentoItem;
	private HiddenItem flgDuplicazioneItem;
	private HiddenItem listaUOPuntoProtocolloEscluseItem;
	private HiddenItem listaUOPuntoProtocolloIncluseItem;
	private HiddenItem listaUOPuntoProtocolloEreditarietaAbilitataItem;
	private HiddenItem flgUoPuntoProtocolloItem;	
	private HiddenItem nriLivelliUoItem;
	private HiddenItem denominazioneUoItem;
	private HiddenItem flgTipoDestDocItem;
	private HiddenItem flgPresentiDocFascItem;
	private HiddenItem typeNodoSpostaDocFascItem;
	private HiddenItem idUoSpostaDocFascItem;
	private HiddenItem descrizioneSpostaDocFascItem;
	private HiddenItem dataAlOrigItem;
	
	private HiddenItem abilitaUoProtEntrataItem;
	private HiddenItem abilitaUoProtUscitaItem;
	private HiddenItem listaTipiGestAttiSelezionatiItem;
	private HiddenItem listaTipiVisPropAttiInIterSelezionatiItem;
	private HiddenItem listaTipiGestAttiSelezionatiNewItem;
	private HiddenItem listaTipiVisPropAttiInIterSelezionatiNewItem;

	private String tipoAssegnatari;
	private boolean isSostituzione;
	
	public PostazioneDetail(String nomeEntita, boolean isSostituzione) {

		super(nomeEntita);

		this.isSostituzione = isSostituzione;
		
		setTipoAssegnatari("SV");
		
		disegnaForm();
	}

	private void disegnaForm() {
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		spacer1Item = new SpacerItem();
		spacer1Item.setWidth(20);
		spacer1Item.setColSpan(1);
		spacer1Item.setStartRow(true);
		
		spacer2Item = new SpacerItem();
		spacer2Item.setWidth(20);
		spacer2Item.setColSpan(1);

		
		creaItemsUtenteCorrente();
		
		if (isSostituzione) {

			creaItemsUtenteNuovo();
						
			lVLayout.addMember(detailSectionUtenteCorrente);
			lVLayout.addMember(detailSectionUtenteDaSostituireCon);

		} else {
			lVLayout.addMember(formUtenteCorrente);
		}

		// Sezione doc/fasc assegnati
		creaSelectOrganigrammaSpostaDocFasc();
							
		// Sezione Resoconto documentazione e mail in competenza alla UO
		creaResocontoSpostamentoSection();
				
		lVLayout.addMember(uoSpostaDocFascSection);			
		lVLayout.addMember(resocontoSpostamentoDocFascSection);
		
		addMember(lVLayout);
		addMember(lVLayoutSpacer);
		
	}
	
	private void creaItemsUtenteCorrente() {

		formUtenteCorrente = new DynamicForm();
		formUtenteCorrente.setValuesManager(vm);
		formUtenteCorrente.setWrapItemTitles(false);
		formUtenteCorrente.setNumCols(20);
		formUtenteCorrente.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		dataAlOrigItem = new HiddenItem("dataAlOrig");
		ciRelUserUoItem = new HiddenItem("ciRelUserUo");
		idScrivaniaItem = new HiddenItem("idScrivania");
		idUoItem = new HiddenItem("idUo");
		abilitaUoProtEntrataItem = new HiddenItem("abilitaUoProtEntrata");
		abilitaUoProtUscitaItem = new HiddenItem("abilitaUoProtUscita");
		
		intestazioneItem = new HiddenItem("intestazione");
		flgSpostamentoItem = new HiddenItem("flgSpostamento");
		flgDuplicazioneItem = new HiddenItem("flgDuplicazione");
		flgUoPuntoProtocolloItem = new HiddenItem("flgUoPuntoProtocollo");
		listaUOPuntoProtocolloIncluseItem = new HiddenItem("listaUOPuntoProtocolloIncluse");
		listaUOPuntoProtocolloEscluseItem = new HiddenItem("listaUOPuntoProtocolloEscluse");
		listaUOPuntoProtocolloEreditarietaAbilitataItem = new HiddenItem("listaUOPuntoProtocolloEreditarietaAbilitata");
		nriLivelliUoItem = new HiddenItem("nriLivelliUo");
		denominazioneUoItem = new HiddenItem("denominazioneUo");
		sostituzioneSVItem = new HiddenItem("sostituzioneSV");
		
		
		
		if (isSostituzione) {
			sostituzioneSVItem.setDefaultValue("1");
		}
		
		creaSelectUtenti();
		
		ImgButtonItem variazioniButton = new ImgButtonItem("variazioniButton", "protocollazione/operazioniEffettuate.png", "Visualizza variazioni");
		variazioniButton.setAlwaysEnabled(true);
		variazioniButton.setColSpan(1);
		variazioniButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(getValuesManager().getValues());
				String idScrivania = record.getAttributeAsString("idScrivania") != null ? record.getAttributeAsString("idScrivania") : null;
				return idScrivania != null && !"".equals(idScrivania);
			}
		});
		variazioniButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				final Record record = new Record(getValuesManager().getValues());
				final String idScrivania = record.getAttributeAsString("idScrivania") != null ? record.getAttributeAsString("idScrivania") : null;							
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("VariazioniDatiUoSvDataSource");
				lGwtRestDataSource.addParam("idUoSv", idScrivania);			
				lGwtRestDataSource.addParam("flgUoSv", "SV");				
				lGwtRestDataSource.fetchData(null, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							if(response.getData() != null && response.getData().length > 0) {
								Record recordVariazione = response.getData()[0];
								String codiceUo = "";
								if(recordVariazione != null){
									codiceUo = recordVariazione.getAttributeAsString("codiceUo");
								}
								String estremi = record.getAttributeAsString("intestazione");
								String title = "Variazioni della postazione occupata da "  + estremi + " nella UO "+ codiceUo;
								new VariazioniDatiUoSvWindow(idScrivania, "SV", title);
							} else {
								AurigaLayout.addMessage(new MessageBean("Non c'è nessuna variazione storicizzata da visualizzare", "", MessageType.ERROR));							
							}
						}
					}
				});												
			}
		});

		// tipo di assegnazione
		GWTRestDataSource tipoRelUOUtenteDS = new GWTRestDataSource("LoadComboTipoRelUtenteUODataSource");
		tipoDiAssegnazioneSelectItem = new SelectItem("tipoRelUtenteUo", I18NUtil.getMessages().organigramma_postazione_detail_tipo_assegnazione());
		tipoDiAssegnazioneSelectItem.setOptionDataSource(tipoRelUOUtenteDS);
		tipoDiAssegnazioneSelectItem.setValueField("key");
		tipoDiAssegnazioneSelectItem.setDisplayField("value");
		tipoDiAssegnazioneSelectItem.setAttribute("obbligatorio", true);
		tipoDiAssegnazioneSelectItem.setRedrawOnChange(true);
		tipoDiAssegnazioneSelectItem.setWidth(550);
		tipoDiAssegnazioneSelectItem.setColSpan(15);
		tipoDiAssegnazioneSelectItem.setStartRow(true);
		tipoDiAssegnazioneSelectItem.setDefaultToFirstOption(true);
		tipoDiAssegnazioneSelectItem.setRequired(true);
		tipoDiAssegnazioneSelectItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				formUtenteCorrente.setValue("nomePostazione", ""); nomePostazioneItem.setValue("");
				formUtenteCorrente.setValue("ruolo", "");selezionaRuoloItem.setValue("");
				// ottavio
				//updateFlgUtenteCorrente(event.getItem().getValue());
				updateFlgUtenteCorrente();
				formUtenteCorrente.markForRedraw();
			}
		});
		
		// data validita (DA) 
		dataDalItem = new DateItem("dataDal", I18NUtil.getMessages().organigramma_postazione_detail_data_dal());
		dataDalItem.setColSpan(1);
		dataDalItem.setStartRow(true);
		dataDalItem.setRequired(true);

		// data validita (A)
		dataAlItem = new DateItem("dataAl", I18NUtil.getMessages().organigramma_postazione_detail_data_al());
		dataAlItem.setColSpan(1);
		
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
		selezionaRuoloItem = new SelectItem("ruolo", I18NUtil.getMessages().organigramma_postazione_detail_ruolo());
		selezionaRuoloItem.setShowTitle(true);
		selezionaRuoloItem.setValueField("key");
		selezionaRuoloItem.setDisplayField("value");
		selezionaRuoloItem.setOptionDataSource(selezionaRuoloDataSource);
		selezionaRuoloItem.setWidth(550);
		selezionaRuoloItem.setColSpan(15);
		selezionaRuoloItem.setAllowEmptyValue(true);
		selezionaRuoloItem.setStartRow(true);
		selezionaRuoloItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (!isTipoDiAssegnazioneL());
			}
		});
		
		// accesso limitato doc. assegnata personalmente
		flgAccessoDocLimSVItem = new CheckboxItem("flgAccessoDocLimSV", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgAccessoDocLimSVItem_title());
		flgAccessoDocLimSVItem.setValue(false);
		flgAccessoDocLimSVItem.setWidth(10);
		flgAccessoDocLimSVItem.setColSpan(6);
		
		// delega alle sotto-UO
		flgInclSottoUoItem = new CheckboxItem("flgInclSottoUo");
		flgInclSottoUoItem.setValue(false);
		flgInclSottoUoItem.setColSpan(1);
		flgInclSottoUoItem.setWidth(10);
		flgInclSottoUoItem.setStartRow(true);
		
		flgInclSottoUoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean visible = false;
				// delega/funzionale
				if (formUtenteCorrente.getValueAsString("tipoRelUtenteUo") != null && formUtenteCorrente.getValueAsString("tipoRelUtenteUo").equals("D")) {
					flgInclSottoUoItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_flg_estendi_delega_sotto_uo());
					visible = true;
				}
				// Appartenenza gerarchica
				else if (formUtenteCorrente.getValueAsString("tipoRelUtenteUo") != null && formUtenteCorrente.getValueAsString("tipoRelUtenteUo").equals("A")) {
					flgInclSottoUoItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_con_delega_alle_sotto_uo());
					visible = true;
				}
				return visible;
			}
		});

		// delega alle postazioni utente
		flgInclScrivVirtItem = new CheckboxItem("flgInclScrivVirt");
		flgInclScrivVirtItem.setValue(false);
		flgInclScrivVirtItem.setColSpan(1);
		flgInclScrivVirtItem.setWidth(10);
		flgInclScrivVirtItem.setStartRow(false);
		flgInclScrivVirtItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean visible = false;
				// delega/funzionale
				if (formUtenteCorrente.getValueAsString("tipoRelUtenteUo") != null && formUtenteCorrente.getValueAsString("tipoRelUtenteUo").equals("D")) {
					flgInclScrivVirtItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_flg_estendi_delega_postazioni_utente());
					visible = true;
				}
				// Appartenenza gerarchica
				else if (formUtenteCorrente.getValueAsString("tipoRelUtenteUo") != null && formUtenteCorrente.getValueAsString("tipoRelUtenteUo").equals("A")) {
					flgInclScrivVirtItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_flg_con_delega_postazioni_utente());
					visible = true;
				}
				return visible;
			}
		});
		
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
					formUtenteCorrente.setValue("flgVisPropAttiInIter", true);
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
				}
				else{
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
		flgGestAttiTuttiItem.setColSpan(1);
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
										formUtenteCorrente.setValue("listaTipiGestAttiSelezionati", formRecord.getAttribute("listaTipiGestAttiSelezionati"));
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
		flgVisPropAttiInIterTuttiItem.setColSpan(1);
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
										formUtenteCorrente.setValue("listaTipiVisPropAttiInIterSelezionati", formRecord.getAttribute("listaTipiVisPropAttiInIterSelezionati"));
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
		nomePostazioneItem = new TextItem("nomePostazione", "Nome postazione") {

			@Override
			public void setCanEdit(Boolean canEdit) {

				super.setCanEdit(canEdit);
				setHint(canEdit ? "Da valorizzare se diverso da cognome e nome dell'utente" : null);
				setShowHintInField(true);
			}
		};
		nomePostazioneItem.setStartRow(true);
		nomePostazioneItem.setColSpan(15);
		nomePostazioneItem.setWidth(550);
		nomePostazioneItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (!isTipoDiAssegnazioneL());
			}
		});

		//Bottone per vedere Abilitazioni vs UO collegate al punto di protocollo 
		uoCollegatePuntoProtocolloButtonFormUtenteCorrente = new ImgButtonItem("uoCollegatePuntoProtocolloFormUtenteCorrente", "buttons/uoCollegatePuntoProtocollo.png", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_uoCollegatePuntoProtocolloButton_title());
		uoCollegatePuntoProtocolloButtonFormUtenteCorrente.setAlwaysEnabled(true);
		uoCollegatePuntoProtocolloButtonFormUtenteCorrente.setColSpan(1);
		uoCollegatePuntoProtocolloButtonFormUtenteCorrente.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idUOPP = formUtenteCorrente.getValueAsString("idUo");
				String idUser = formUtenteCorrente.getValueAsString("idUser");
				String listaUOPuntoProtocolloEscluse = formUtenteCorrente.getValueAsString("listaUOPuntoProtocolloEscluse");
				String listaUOPuntoProtocolloEreditarietaAbilitata = formUtenteCorrente.getValueAsString("listaUOPuntoProtocolloEreditarietaAbilitata");
				Record record = new Record();
				record.setAttribute("idUOPP", idUOPP);
				record.setAttribute("idUser", idUser);
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
								final String denominazioneUo = formUtenteCorrente.getValueAsString("denominazioneUo");
								final String codRapido = formUtenteCorrente.getValueAsString("nriLivelliUo");								
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
		
		uoCollegatePuntoProtocolloButtonFormUtenteCorrente.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAbilToModUoCollegatePuntoProtocollo() && !isInSostituzione();
			}
		});

		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO")) {
			formUtenteCorrente.setFields( // visibili
					utentiItem, 
					variazioniButton, 
					tipoDiAssegnazioneSelectItem, 
					dataDalItem, 
					dataAlOrigItem, 
					dataAlItem, 
					selezionaRuoloItem, 
					spacer1Item,flgAccessoDocLimSVItem,
					flgInclSottoUoItem, flgInclScrivVirtItem, 
					spacer1Item,flgRegistrazioneEItem, spacer2Item, flgRegistrazioneUIItem,
					spacer1Item,flgGestAttiItem,       flgGestAttiTuttiItem, selezionaTipiGestAttiButton, listaTipiGestAttiSelezionatiItem,
		            spacer1Item, flgVisPropAttiInIterItem, flgVisPropAttiInIterTuttiItem, selezionaTipiVisPropAttiInIterButton, listaTipiVisPropAttiInIterSelezionatiItem,
					spacerFlgRiservatezzaRelUserUoItem,flgRiservatezzaRelUserUoItem,	

					nomePostazioneItem,
					spacer1Item, uoCollegatePuntoProtocolloButtonFormUtenteCorrente,

					// Hidden
					ciRelUserUoItem, 
					idScrivaniaItem, 
					idUoItem,
					abilitaUoProtEntrataItem,
					abilitaUoProtUscitaItem,
					sostituzioneSVItem, 
					intestazioneItem, 
					flgSpostamentoItem,
					flgDuplicazioneItem,
					flgUoPuntoProtocolloItem,
					listaUOPuntoProtocolloIncluseItem,
					listaUOPuntoProtocolloEscluseItem,
					listaUOPuntoProtocolloEreditarietaAbilitataItem,
					nriLivelliUoItem,
					denominazioneUoItem
					);
		} else {
			formUtenteCorrente.setFields( // visibili
					utentiItem, 
					variazioniButton, 
					tipoDiAssegnazioneSelectItem, 
					dataDalItem, 
					dataAlOrigItem, 
					dataAlItem, 
					selezionaRuoloItem, 
					spacer1Item,flgAccessoDocLimSVItem,
					flgInclSottoUoItem, flgInclScrivVirtItem, 
					spacer1Item,flgRegistrazioneEItem, spacer2Item, flgRegistrazioneUIItem,
					spacer1Item,flgGestAttiItem,       spacer2Item, flgVisPropAttiInIterItem,
					spacerFlgRiservatezzaRelUserUoItem,flgRiservatezzaRelUserUoItem,	

					nomePostazioneItem,
					spacer1Item, uoCollegatePuntoProtocolloButtonFormUtenteCorrente,

					// Hidden
					ciRelUserUoItem, 
					idScrivaniaItem, 
					idUoItem,
					abilitaUoProtEntrataItem,
					abilitaUoProtUscitaItem,
					sostituzioneSVItem, 
					intestazioneItem, 
					flgSpostamentoItem,
					flgDuplicazioneItem,
					flgUoPuntoProtocolloItem,
					listaUOPuntoProtocolloIncluseItem,
					listaUOPuntoProtocolloEscluseItem,
					listaUOPuntoProtocolloEreditarietaAbilitataItem,
					nriLivelliUoItem,
					denominazioneUoItem
					);
		}
		
	}

	private void creaItemsUtenteNuovo() {

		formUtenteDaSostituireCon = new DynamicForm();
		formUtenteDaSostituireCon.setValuesManager(vm);
		formUtenteDaSostituireCon.setWrapItemTitles(false);
		formUtenteDaSostituireCon.setNumCols(20);
		formUtenteDaSostituireCon.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		
		creaSelectUtentiNew();

		// tipo di assegnazione
		GWTRestDataSource tipoRelUtenteUoNewDS = new GWTRestDataSource("LoadComboTipoRelUtenteUODataSource");
		tipoDiAssegnazioneNewSelectItem = new SelectItem("tipoRelUtenteUoNew", I18NUtil.getMessages().organigramma_postazione_detail_tipo_assegnazione());
		tipoDiAssegnazioneNewSelectItem.setOptionDataSource(tipoRelUtenteUoNewDS);
		tipoDiAssegnazioneNewSelectItem.setValueField("key");
		tipoDiAssegnazioneNewSelectItem.setDisplayField("value");
		tipoDiAssegnazioneNewSelectItem.setAttribute("obbligatorio", true);
		tipoDiAssegnazioneNewSelectItem.setRedrawOnChange(true);
		tipoDiAssegnazioneNewSelectItem.setWidth(550);
		tipoDiAssegnazioneNewSelectItem.setColSpan(15);
		tipoDiAssegnazioneNewSelectItem.setStartRow(true);
		tipoDiAssegnazioneNewSelectItem.setDefaultToFirstOption(true);
		tipoDiAssegnazioneNewSelectItem.setRequired(true);
		tipoDiAssegnazioneNewSelectItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				formUtenteDaSostituireCon.setValue("ruoloNew", "");selezionaRuoloNewItem.setValue("");
				// ottavio
				//updateFlgUtenteCorrenteNew(event.getItem().getValue());
				updateFlgUtenteCorrenteNew();
				formUtenteDaSostituireCon.markForRedraw();
			}
		});
		
		// data validita (DA) 
		dataDalNewItem = new DateItem("dataDalNew", I18NUtil.getMessages().organigramma_postazione_detail_data_dal());
		dataDalNewItem.setColSpan(1);
		dataDalNewItem.setStartRow(true);
		dataDalNewItem.setRequired(true);
		dataDalNewItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO bisogna togliere un giorno dalla data vecchia
				if (dataAlOrigItem.getValue() == null) {
					Date dataAl = dataDalNewItem.getValueAsDate();
					CalendarUtil.addDaysToDate(dataAl, -1);
					dataAlItem.setDefaultValue(dataAl);
				}
			}
		});

		// data validita (A) 
		dataAlNewItem = new DateItem("dataAlNew", I18NUtil.getMessages().organigramma_postazione_detail_data_al());
		dataAlNewItem.setColSpan(2);
		
		// ruolo
		GWTRestDataSource selezionaRuoloNewDataSource = new GWTRestDataSource("LoadComboRuoloDataSource");
		selezionaRuoloNewItem = new SelectItem("ruoloNew", I18NUtil.getMessages().organigramma_postazione_detail_ruolo());
		selezionaRuoloNewItem.setShowTitle(true);
		selezionaRuoloNewItem.setValueField("key");
		selezionaRuoloNewItem.setDisplayField("value");
		selezionaRuoloNewItem.setOptionDataSource(selezionaRuoloNewDataSource);
		selezionaRuoloNewItem.setWidth(550);
		selezionaRuoloNewItem.setColSpan(15);
		selezionaRuoloNewItem.setAllowEmptyValue(true);
		selezionaRuoloNewItem.setStartRow(true);
		selezionaRuoloNewItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (!isTipoDiAssegnazioneNewL());
			}
		});

		// accesso limitato doc. assegnata personalmente
		flgAccessoDocLimSVNewItem = new CheckboxItem("flgAccessoDocLimSVNew", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgAccessoDocLimSVItem_title());
		flgAccessoDocLimSVNewItem.setValue(false);
		flgAccessoDocLimSVNewItem.setWidth(10);
		flgAccessoDocLimSVNewItem.setColSpan(6);
		
		// delega alle sotto-UO		
		flgInclSottoUoNewItem = new CheckboxItem("flgInclSottoUoNew");
		flgInclSottoUoNewItem.setValue(false);
		flgInclSottoUoNewItem.setColSpan(2);
		flgInclSottoUoNewItem.setWidth(10);
		flgInclSottoUoNewItem.setStartRow(true);
		flgInclSottoUoNewItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				// delega/funzionale
				if (formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew") != null && formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew").equals("D")) {
					flgInclSottoUoNewItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_flg_estendi_delega_sotto_uo());
					return true;
				}
				// Appartenenza gerarchica
				else if (formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew") != null && formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew").equals("A")) {
					flgInclSottoUoNewItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_con_delega_alle_sotto_uo());
					return true;
				}
				return false;
			}
		});

		// delega alle postazioni utente
		flgInclScrivVirtNewItem = new CheckboxItem("flgInclScrivVirtNew");
		flgInclScrivVirtNewItem.setValue(false);
		flgInclScrivVirtNewItem.setColSpan(1);
		flgInclScrivVirtNewItem.setWidth(10);
		flgInclScrivVirtNewItem.setStartRow(false);
		flgInclScrivVirtNewItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				// delega/funzionale
				if (formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew") != null && formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew").equals("D")) {
					flgInclScrivVirtNewItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_flg_estendi_delega_postazioni_utente());
					return true;
				}
				// Appartenenza gerarchica
				else if (formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew") != null && formUtenteDaSostituireCon.getValueAsString("tipoRelUtenteUoNew").equals("A")) {
					flgInclScrivVirtNewItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_flg_con_delega_postazioni_utente());
					return true;
				}
				return false;
			}
		});

		// registrazione in entrata 
		flgRegistrazioneENewItem = new CheckboxItem("flgRegistrazioneENew", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgRegistrazioneEItem_title());
		flgRegistrazioneENewItem.setValue(false);
		flgRegistrazioneENewItem.setWidth(10);
		flgRegistrazioneENewItem.setColSpan(2);
		
		// registrazione in uscita/interna
		flgRegistrazioneUINewItem = new CheckboxItem("flgRegistrazioneUINew", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgRegistrazioneUIItem_title());
		flgRegistrazioneUINewItem.setValue(false);
		flgRegistrazioneUINewItem.setWidth(10);
		flgRegistrazioneUINewItem.setColSpan(2);
		
		// avvio/gestione atti proposti
		flgGestAttiNewItem = new CheckboxItem("flgGestAttiNew", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgGestAttiItem_title());
		flgGestAttiNewItem.setValue(false);
		flgGestAttiNewItem.setWidth(10);
		flgGestAttiNewItem.setColSpan(2);
		
		flgGestAttiNewItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (flgGestAttiNewItem.getValueAsBoolean() != null && flgGestAttiNewItem.getValueAsBoolean()){
					
					// setto il flag flgVisPropAttiInIterNewItem e lo disabilito
					flgVisPropAttiInIterNewItem.setValue(true);
					formUtenteCorrente.setValue("flgVisPropAttiInIterNew", true);
					flgVisPropAttiInIterNewItem.setCanEdit(false);
					
					if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO") && flgGestAttiTuttiNewItem != null && flgVisPropAttiInIterTuttiNewItem != null) {
						flgGestAttiTuttiNewItem.show();
						flgGestAttiTuttiNewItem.setValue(true);
						flgVisPropAttiInIterTuttiNewItem.show();
						flgVisPropAttiInIterTuttiNewItem.setValue(true);
						listaTipiGestAttiSelezionatiNewItem.clearValue();
						selezionaTipiGestAttiNewButton.hide();
						listaTipiVisPropAttiInIterSelezionatiNewItem.clearValue();
						selezionaTipiVisPropAttiInIterNewButton.hide();
					}
				} else {
					// Abilito il flag flgVisPropAttiInIterNewItem
					flgVisPropAttiInIterNewItem.setCanEdit(true);
					if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO") && flgGestAttiTuttiNewItem != null && flgVisPropAttiInIterTuttiNewItem != null) {
						flgGestAttiTuttiNewItem.clearValue();
						flgGestAttiTuttiNewItem.hide();
						selezionaTipiGestAttiNewButton.hide();
						listaTipiGestAttiSelezionatiNewItem.clearValue();

					}
				}
				markForRedraw();
			}
		});
		
		flgGestAttiTuttiNewItem = new CheckboxItem("flgGestAttiTuttiNew", "tutti i tipi di atti");
		flgGestAttiTuttiNewItem.setValue(false);
		flgGestAttiTuttiNewItem.setWidth(10);
//		flgGestAttiTuttiNewItem.setColSpan(2);
		flgGestAttiTuttiNewItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO") && (flgGestAttiNewItem.getValueAsBoolean() != null && flgGestAttiNewItem.getValueAsBoolean());
			}
		});
		flgGestAttiTuttiNewItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (flgGestAttiTuttiNewItem.getValueAsBoolean() != null && !flgGestAttiTuttiNewItem.getValueAsBoolean()){
					selezionaTipiGestAttiNewButton.show();
				} else {
					listaTipiGestAttiSelezionatiNewItem.clearValue();
					selezionaTipiGestAttiNewButton.hide();
				}
				markForRedraw();
			}
		});
		
		selezionaTipiGestAttiNewButton = new ImgButtonItem("selezionaTipiGestAttiNew", "buttons/altriDati.png",	"Seleziona tipi di atti");
		selezionaTipiGestAttiNewButton.setAlwaysEnabled(true);
//		selezionaTipiGestAttiNewButton.setColSpan(1);
		selezionaTipiGestAttiNewButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgGestAttiTuttiNewItem != null && !flgGestAttiTuttiNewItem.getValueAsBoolean() && flgGestAttiNewItem.getValueAsBoolean();
			}
		});
		selezionaTipiGestAttiNewButton.addIconClickHandler(new IconClickHandler() {
			
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
								recordNew.setAttribute("listaTipiGestAttiSelezionati", listaTipiGestAttiSelezionatiNewItem.getValue());
								recordNew.setAttribute("tipoLista", "tipiGestAtti");
								String mode = getMode();	
								String title = "Tipi di atti abilitati";
								SelezionaTipiDiAttoPopup selezionaTipiDiAttoPopup = new SelezionaTipiDiAttoPopup(recordNew, title, mode) {
									
									@Override
									public void onClickOkButton(Record formRecord, DSCallback callback) {
										Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");				
										Layout.hideWaitPopup();
										formUtenteDaSostituireCon.setValue("listaTipiGestAttiSelezionatiNew", formRecord.getAttribute("listaTipiGestAttiSelezionati"));
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
		
		listaTipiGestAttiSelezionatiNewItem = new HiddenItem("listaTipiGestAttiSelezionatiNew");
		
		// visualizzazione atti proposti
		flgVisPropAttiInIterNewItem = new CheckboxItem("flgVisPropAttiInIterNew", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgVisPropAttiInIterItem_title());
		flgVisPropAttiInIterNewItem.setValue(false);
		flgVisPropAttiInIterNewItem.setWidth(10);
		flgVisPropAttiInIterNewItem.setColSpan(2);
		flgVisPropAttiInIterNewItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {

				if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO")) {
					if (flgVisPropAttiInIterNewItem.getValueAsBoolean() != null
							&& flgVisPropAttiInIterNewItem.getValueAsBoolean()) {
						flgVisPropAttiInIterTuttiNewItem.show();
						flgVisPropAttiInIterTuttiNewItem.setValue(true);
						listaTipiVisPropAttiInIterSelezionatiNewItem.clearValue();
						selezionaTipiVisPropAttiInIterNewButton.hide();
					}
				} else {
					flgVisPropAttiInIterTuttiNewItem.clearValue();
					flgVisPropAttiInIterTuttiNewItem.hide();
					selezionaTipiVisPropAttiInIterNewButton.hide();
				}
				markForRedraw();
			}
		});

		flgVisPropAttiInIterTuttiNewItem = new CheckboxItem("flgVisPropAttiInIterTuttiNew", "tutti i tipi di atti");
		flgVisPropAttiInIterTuttiNewItem.setValue(false);
		flgVisPropAttiInIterTuttiNewItem.setWidth(10);
//		flgVisPropAttiInIterTuttiNewItem.setColSpan(2);
		flgVisPropAttiInIterTuttiNewItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO") && (flgVisPropAttiInIterNewItem.getValueAsBoolean() != null && flgVisPropAttiInIterNewItem.getValueAsBoolean());
			}
		});
		flgVisPropAttiInIterTuttiNewItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (flgVisPropAttiInIterTuttiNewItem.getValueAsBoolean() != null && !flgVisPropAttiInIterTuttiNewItem.getValueAsBoolean()){
					selezionaTipiVisPropAttiInIterNewButton.show();
				} else {
					listaTipiVisPropAttiInIterSelezionatiNewItem.clearValue();
					selezionaTipiVisPropAttiInIterNewButton.hide();
				}
				markForRedraw();
			}
		});
		
		selezionaTipiVisPropAttiInIterNewButton = new ImgButtonItem("selezionaTipiVisPropAttiInIterNew", "buttons/altriDati.png",	"Seleziona tipi di atti");
		selezionaTipiVisPropAttiInIterNewButton.setAlwaysEnabled(true);
//		selezionaTipiVisPropAttiInIterNewButton.setColSpan(1);
		selezionaTipiVisPropAttiInIterNewButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgVisPropAttiInIterTuttiNewItem != null && !flgVisPropAttiInIterTuttiNewItem.getValueAsBoolean() && flgVisPropAttiInIterNewItem.getValueAsBoolean();
			}
		});
		selezionaTipiVisPropAttiInIterNewButton.addIconClickHandler(new IconClickHandler() {
			
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
								recordNew.setAttribute("listaTipiVisPropAttiInIterSelezionati", listaTipiVisPropAttiInIterSelezionatiNewItem.getValue());
								recordNew.setAttribute("tipoLista", "tipiVisPropAttiInIter");
								String mode = getMode();	
								String title = "Tipi di atti abilitati";
								SelezionaTipiDiAttoPopup selezionaTipiDiAttoPopup = new SelezionaTipiDiAttoPopup(recordNew, title, mode) {
									
									@Override
									public void onClickOkButton(Record formRecord, DSCallback callback) {
										Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");				
										Layout.hideWaitPopup();
										formUtenteDaSostituireCon.setValue("listaTipiVisPropAttiInIterSelezionatiNew", formRecord.getAttribute("listaTipiVisPropAttiInIterSelezionati"));
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
		
		listaTipiVisPropAttiInIterSelezionatiNewItem = new HiddenItem("listaTipiVisPropAttiInIterSelezionatiNew");

		// Abilitazione alla documentazione riservata assegnata alla struttura
		flgRiservatezzaRelUserUoNewItem = new CheckboxItem("flgRiservatezzaRelUserUoNew", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgRiservatezzaRelUserUoItem_title());
		flgRiservatezzaRelUserUoNewItem.setValue(false);
		flgRiservatezzaRelUserUoNewItem.setWidth(10);
		flgRiservatezzaRelUserUoNewItem.setColSpan(6);
		
		// Se il parametro DB ATTIVA_GEST_RISERVATEZZA_REL_USER_UO = true
		flgRiservatezzaRelUserUoNewItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_RISERVATEZZA_REL_USER_UO"));
			}
		});

		// Se il parametro DB ATTIVA_GEST_RISERVATEZZA_REL_USER_UO = true
		spacerFlgRiservatezzaRelUserUoNewItem = new SpacerItem();
		spacerFlgRiservatezzaRelUserUoNewItem.setWidth(20);
		spacerFlgRiservatezzaRelUserUoNewItem.setColSpan(1);
		spacerFlgRiservatezzaRelUserUoNewItem.setStartRow(true);
		spacerFlgRiservatezzaRelUserUoNewItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_RISERVATEZZA_REL_USER_UO"));
			}
		});

		
		
		//Bottone per vedere Abilitazioni vs UO collegate al punto di protocollo 
		uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon = new ImgButtonItem("uoCollegatePuntoProtocolloFormUtenteDaSostituireCon", "buttons/uoCollegatePuntoProtocollo.png", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_uoCollegatePuntoProtocolloButton_title());
		uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon.setAlwaysEnabled(true);
		uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon.setColSpan(1);
		uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon.addIconClickHandler(new IconClickHandler() {

					@Override
					public void onIconClick(IconClickEvent event) {
						String idUOPP = formUtenteCorrente.getValueAsString("idUo");
						String idUser = formUtenteCorrente.getValueAsString("idUser");
						String listaUOPuntoProtocolloEscluse = formUtenteCorrente.getValueAsString("listaUOPuntoProtocolloEscluse");
						String listaUOPuntoProtocolloEreditarietaAbilitata = formUtenteCorrente.getValueAsString("listaUOPuntoProtocolloEreditarietaAbilitata");
						Record record = new Record();
						record.setAttribute("idUOPP", idUOPP);
						record.setAttribute("idUser", idUser);
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
										final String denominazioneUo = formUtenteCorrente.getValueAsString("denominazioneUo");
										final String codRapido = formUtenteCorrente.getValueAsString("nriLivelliUo");								
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
				
		uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon.setShowIfCondition(new FormItemIfFunction() {
					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {
						return isAbilToModUoCollegatePuntoProtocollo() && isInSostituzione();
					}
		});

		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO")) {
			formUtenteDaSostituireCon.setFields(utentiNewItem, 
					tipoDiAssegnazioneNewSelectItem, 
					dataDalNewItem, 
					dataAlNewItem, 
					selezionaRuoloNewItem,				                            
					spacer1Item,flgAccessoDocLimSVNewItem,				                            
					flgInclSottoUoNewItem, flgInclScrivVirtNewItem,
					spacer1Item,flgRegistrazioneENewItem, spacer2Item, flgRegistrazioneUINewItem,
					spacer1Item,flgGestAttiNewItem,       flgGestAttiTuttiNewItem, selezionaTipiGestAttiNewButton, listaTipiGestAttiSelezionatiNewItem,
		            spacer1Item, flgVisPropAttiInIterNewItem, flgVisPropAttiInIterTuttiNewItem, selezionaTipiVisPropAttiInIterNewButton, listaTipiVisPropAttiInIterSelezionatiNewItem,
					spacerFlgRiservatezzaRelUserUoNewItem, flgRiservatezzaRelUserUoNewItem,

					spacer1Item,uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon
					);
		} else {
			formUtenteDaSostituireCon.setFields(utentiNewItem, 
					tipoDiAssegnazioneNewSelectItem, 
					dataDalNewItem, 
					dataAlNewItem, 
					selezionaRuoloNewItem,				                            
					spacer1Item,flgAccessoDocLimSVNewItem,				                            
					flgInclSottoUoNewItem, flgInclScrivVirtNewItem,
					spacer1Item,flgRegistrazioneENewItem, spacer2Item, flgRegistrazioneUINewItem,
					spacer1Item,flgGestAttiNewItem,       spacer2Item, flgVisPropAttiInIterNewItem,		
					spacerFlgRiservatezzaRelUserUoNewItem, flgRiservatezzaRelUserUoNewItem,

					spacer1Item,uoCollegatePuntoProtocolloButtonFormUtenteDaSostituireCon
					);
		}
		
		
		detailSectionUtenteCorrente        = new DetailSection(I18NUtil.getMessages().organigramma_postazione_detail_section_utente_corrente(), false, false, false, formUtenteCorrente);
		detailSectionUtenteDaSostituireCon = new DetailSection(I18NUtil.getMessages().organigramma_postazione_detail_section_utente_nuovo(), false, false, false, formUtenteDaSostituireCon);
	}

	protected void creaSelectUtenti() {
		
		SelectGWTRestDataSource lGwtRestDataSourceUtenti = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT, new String[] {"cognomeNome", "username" }, true);
		lGwtRestDataSourceUtenti.addParam("idUtenteToExclude", String.valueOf(Layout.getUtenteLoggato().getIdUser()));
		utentiItem = new FilteredSelectItemWithDisplay("idUser", lGwtRestDataSourceUtenti) {

			@Override
			public void onOptionClick(Record record) {
				String idUser = record.getAttribute("idUtente");
				recuperaTipoAssegnazioneXUtente(idUser, formUtenteCorrente.getValueAsString("idUo"), formUtenteCorrente.getValueAsString("ciRelUserUo"),
						new ServiceCallback<String>() {

							@Override
							public void execute(String tipoDiAssegnazioneDefault) {

								formUtenteCorrente.setValue("tipoRelUtenteUo", tipoDiAssegnazioneDefault);
								formUtenteCorrente.markForRedraw();
							}
						});
			}
		};
		ListGridField utentiCodiceField      = new ListGridField("codice", "Cod."); utentiCodiceField.setWidth(100);
		ListGridField utentiCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");
		ListGridField utentiUsernameField    = new ListGridField("username", "Username");utentiUsernameField.setWidth(190);
		
		utentiItem.setPickListFields(utentiCodiceField, utentiCognomeNomeField, utentiUsernameField);
		utentiItem.setFilterLocally(true);
		utentiItem.setValueField("idUtente");
		utentiItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_utenti());
		utentiItem.setOptionDataSource(lGwtRestDataSourceUtenti);
		utentiItem.setWidth(550);
		utentiItem.setRequired(true);
		utentiItem.setAllowEmptyValue(false);
		utentiItem.setClearable(false);
		utentiItem.setShowIcons(true);		
		utentiItem.setAutoFetchData(false);
		utentiItem.setFetchMissingValues(false);
		
		if (AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			utentiItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		utentiItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});
		
		utentiItem.setColSpan(15);
	}

	protected void creaSelectUtentiNew() {
		SelectGWTRestDataSource lGwtRestDataSourceUtentiNew = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT, new String[] { "cognomeNome", "username" }, true);
		lGwtRestDataSourceUtentiNew.addParam("idUtenteToExclude", String.valueOf(Layout.getUtenteLoggato().getIdUser()));
		utentiNewItem = new FilteredSelectItemWithDisplay("idUserNew", lGwtRestDataSourceUtentiNew) {

			@Override
			public void onOptionClick(Record record) {
				String idUserNew = record.getAttribute("idUtente");
				recuperaTipoAssegnazioneXUtente(idUserNew, formUtenteCorrente.getValueAsString("idUo"), formUtenteCorrente.getValueAsString("ciRelUserUo"),
						new ServiceCallback<String>() {

							@Override
							public void execute(String tipoDiAssegnazioneDefault) {

								formUtenteDaSostituireCon.setValue("tipoRelUtenteUoNew", tipoDiAssegnazioneDefault);
								formUtenteDaSostituireCon.markForRedraw();
							}
						});
			}
		};
		
		ListGridField utentiNewCodiceField      = new ListGridField("codice", "Cod.");utentiNewCodiceField.setWidth(100);
		ListGridField utentiNewCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");
		ListGridField utentiNewUsernameField    = new ListGridField("username", "Username");utentiNewUsernameField.setWidth(190);
		
		utentiNewItem.setPickListFields(utentiNewCodiceField, utentiNewCognomeNomeField, utentiNewUsernameField);
		utentiNewItem.setFilterLocally(true);
		utentiNewItem.setValueField("idUtente");
		utentiNewItem.setTitle(I18NUtil.getMessages().organigramma_postazione_detail_utenti());
		utentiNewItem.setOptionDataSource(lGwtRestDataSourceUtentiNew);
		utentiNewItem.setWidth(550);
		utentiNewItem.setRequired(true);
		utentiNewItem.setAllowEmptyValue(false);
		utentiNewItem.setClearable(false);
		utentiNewItem.setShowIcons(true);
		if (AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			utentiNewItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		utentiNewItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});
		utentiNewItem.setColSpan(15);
	}

	@Override
	public void editRecord(Record record) {
		record.setAttribute("dataAlOrig", record.getAttribute("dataAl"));
		super.editRecord(record);
		
		initCombo(record);
		
		initSpostaDocFascSection(record);
		
		if (isSostituzione) {
			if (tipoDiAssegnazioneNewSelectItem!=null){
				if (tipoDiAssegnazioneNewSelectItem !=null) {
					// ottavio
					//updateFlgUtenteCorrenteNew(tipoDiAssegnazioneNewSelectItem.getValue());
					updateFlgUtenteCorrenteNew();
				}
			}
		}
		
		markForRedraw();
	}
	
	@Override
	public void editNewRecord(Map initialValues) {
		
		super.editNewRecord(initialValues);
		
		if (tipoDiAssegnazioneSelectItem !=null) {
			tipoDiAssegnazioneSelectItem.fetchData(new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					RecordList data = response.getDataAsRecordList();
					if (data.getLength() > 0) {
						Record record = data.get(0);
						// ottavio
						//updateFlgUtenteCorrente(record.getAttribute("key"));
						updateFlgUtenteCorrente();
					}
				}
			});
		}
	}
	
	private void initCombo(Record record) {
		 
		// Inizializzo la combo degli UTENTI con il nome della postazione
		GWTRestDataSource utentiDS = (GWTRestDataSource) utentiItem.getOptionDataSource();
		if (record.getAttribute("idUser") != null && !"".equals(record.getAttributeAsString("idUser"))) {
			utentiDS.addParam("idUtente", record.getAttributeAsString("idUser"));
					
			if (record.getAttribute("descrizioneUser") != null && !"".equals(record.getAttributeAsString("descrizioneUser"))) {
				String descrizione = record.getAttribute("descrizioneUser");
				if (record.getAttribute("username") != null && !"".equals(record.getAttributeAsString("username"))) {
							descrizione += " ** " + record.getAttribute("username");
				}
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("idUser"), descrizione);
				utentiItem.setValueMap(valueMap);
				utentiItem.setValue(record.getAttribute("idUser"));
			}
		} 
		else 
		{
			utentiDS.addParam("idUtente", null);
		}
		utentiItem.setOptionDataSource(utentiDS);

				
		// Inizializzo la combo del RUOLO
		GWTRestDataSource selezionaRuoloDS = (GWTRestDataSource) selezionaRuoloItem.getOptionDataSource();
		if (record.getAttribute("ruolo") != null && !"".equals(record.getAttributeAsString("ruolo"))) {
			selezionaRuoloDS.addParam("ruoloAssegnato", record.getAttributeAsString("ruolo"));
		} 
		else 
		{
			selezionaRuoloDS.addParam("ruoloAssegnato", null);
		}
		selezionaRuoloItem.setOptionDataSource(selezionaRuoloDS);
				
			
		 // Combo Organigramma Sposta Doc/Fasc
		 if (record.getAttributeAsString("idUODestDocfasc") != null && !"".equals(record.getAttributeAsString("idUODestDocfasc"))) {
			 LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			 valueMap.put(record.getAttributeAsString("idUODestDocfasc"), record.getAttributeAsString("desUODestDocFasc"));
			 organigrammaSpostaDocFascItem.setValueMap(valueMap);
			 organigrammaSpostaDocFascItem.setValue(record.getAttribute("idUODestDocfasc"));
			 uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc", record.getAttribute("desUODestDocFasc"));	
			 uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc", record.getAttribute("livelliUODestDocFasc"));
			 uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc", record.getAttribute("flgTipoDestDoc"));
		 } 
	}
	
	
	private void initSpostaDocFascSection(Record record) {
		 Date dataCessazione = record.getAttribute("dataAl") != null ? DateUtil.parseInput(record.getAttribute("dataAl")) : null;
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
		 }
        else
           {
		     uoSpostaDocFascSection.setVisible(false);	
			 codRapidoSpostaDocFascItem.setRequired(false);
			 organigrammaSpostaDocFascItem.setRequired(false);
			 resocontoSpostamentoDocFascSection.setVisible(false);
			}
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
		if (mode.equals("new")) {
			utentiItem.setCanEdit(true);
		} else {
			utentiItem.setCanEdit(false);
		}
		if (isInSostituzione()) {
			setCanEdit(false, formUtenteCorrente);
		}
		
		// i campi della sezione RESOCONTO devono essere sempre read-only
		setCanEdit(false, resocontoSpostamentoDocFascForm);						

	}

	private void recuperaTipoAssegnazioneXUtente(String idUser, String idUo, String ciRelUserUo, final ServiceCallback<String> callback) {
		Record record = new Record();
		record.setAttribute("idUo", idUo);
		record.setAttribute("ciRelUserUo", ciRelUserUo);
		record.setAttribute("idUser", idUser);
		new GWTRestDataSource("PostazioneDatasource").executecustom("recuperaTipoAssegnazioneXUtente", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				callback.execute(response.getData()[0].getAttribute("tipoRelUtenteUo"));
			}
		});
	}

	public boolean isInSostituzione() {
		Record record = formUtenteCorrente.getValuesAsRecord();
		return (record.getAttribute("sostituzioneSV") != null && record.getAttribute("sostituzioneSV").equals("1"));
	}
	
	private boolean isTipoDiAssegnazioneL(){
		return (tipoDiAssegnazioneSelectItem.getValue()!=null && tipoDiAssegnazioneSelectItem.getValueAsString().equals("L"));
	}
	
	private boolean isTipoDiAssegnazioneNewL(){
		return (tipoDiAssegnazioneNewSelectItem.getValue()!=null && tipoDiAssegnazioneNewSelectItem.getValueAsString().equals("L"));
	}

	public boolean isAbilToModUoCollegatePuntoProtocollo() {
		// TODO Auto-generated method stub
		return (flgUoPuntoProtocolloItem.getValue()!=null && flgUoPuntoProtocolloItem.getValue().toString().equals("true"));
	}

	public String getMode() {
		return getLayout().getMode();
	}

	public void setFormValuesFromRecordAfterAbilUOPuntoProtocollo(Record record) {
		formUtenteCorrente.setValue("listaUOPuntoProtocolloIncluse", record.getAttribute("listaUOPuntoProtocolloIncluse"));
		formUtenteCorrente.setValue("listaUOPuntoProtocolloEscluse", record.getAttribute("listaUOPuntoProtocolloEscluse"));
		formUtenteCorrente.setValue("listaUOPuntoProtocolloEreditarietaAbilitata", record.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata"));
	}
	
	
	private void creaSelectOrganigrammaSpostaDocFasc() {

		String tipoAssegnatari = "UO;SV";
		
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
		flgTipoDestDocItem                    = new HiddenItem("flgTipoDestDoc");
		
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
		organigrammaSpostaDocFascItem.setWidth(550);
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
				                       codRapidoSpostaDocFascItem,
				                       lookupOrganigrammaSpostaDocFascButton,
				                       organigrammaSpostaDocFascItem,				                       
	                                   // Hidden
				                       flgTipoDestDocItem,
				                       flgPresentiDocFascItem,
				                       typeNodoSpostaDocFascItem             ,
				                       idUoSpostaDocFascItem                 ,
				                       descrizioneSpostaDocFascItem          
	                                 );	
		
		uoSpostaDocFascSection = new DetailSection(I18NUtil.getMessages().organigramma_postazione_detail_uoSpostaDocFascSection_title(), true, true, false, uoSpostaDocFascForm);
		uoSpostaDocFascSection.setVisible(isUOSpostaDocFascSectionVisible());		
	}

	
	private void creaResocontoSpostamentoSection() {
		resocontoSpostamentoDocFascForm = new DynamicForm();
		resocontoSpostamentoDocFascForm.setValuesManager(vm);
		resocontoSpostamentoDocFascForm.setWidth("100%");
		resocontoSpostamentoDocFascForm.setHeight("5");
		resocontoSpostamentoDocFascForm.setPadding(5);
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
		descUoSpostamentoDocFascItem.setWidth(340);

		statoSpostamentoDocFascItem = new TextItem("statoSpostamentoDocFasc", I18NUtil.getMessages().organigramma_uo_detail_statoSpostamentoDocFasc_title());		
		statoSpostamentoDocFascItem.setWidth(120);
		
		dataInizioSpostamentoDocFascItem = new DateTimeItem("dataInizioSpostamentoDocFasc", I18NUtil.getMessages().organigramma_uo_detail_dataInizioSpostamentoDocFasc_title());
		dataInizioSpostamentoDocFascItem.setWidth(120);
		
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
	
	private boolean isUOSpostaDocFascSectionVisible(){
		
		Date dataCessazione      = dataAlItem.getValueAsDate();
		
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

	private void updateSpostaDocFascSection(Object dataCessazione){

		 organigrammaSpostaDocFascItem.setValue("");  uoSpostaDocFascForm.setValue("organigrammaSpostaDocFasc", (String) null);
		 descrizioneSpostaDocFascItem.setValue("");   uoSpostaDocFascForm.setValue("descrizioneSpostaDocFasc",  (String) null);
		 codRapidoSpostaDocFascItem.setValue("");     uoSpostaDocFascForm.setValue("codRapidoSpostaDocFasc",    (String) null);
		 idUoSpostaDocFascItem.setValue("");          uoSpostaDocFascForm.setValue("idUoSpostaDocFasc",         (String) null);
		 typeNodoSpostaDocFascItem.setValue("");      uoSpostaDocFascForm.setValue("typeNodoSpostaDocFasc",     (String) null);


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
	
	/*
	private void updateFlgUtenteCorrenteNew(Object tipoDiAssegnazione){
		
		if (tipoDiAssegnazione != null && !tipoDiAssegnazione.equals("")){
			
			// Se "A" (Appartenenza gerarchica) o "D" (Funzionale/delega)
			if (tipoDiAssegnazione.equals("A") || tipoDiAssegnazione.equals("D")){
				
				// Se il parametro DB DEFAULT_REL_UO_USER_REGE = true
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGE")) {
					// Il check "registrazione in entrata" va preimpostato a spuntato
					flgRegistrazioneENewItem.setValue(true);
					formUtenteDaSostituireCon.setValue("flgRegistrazioneENew",      "true");  
				}
				
				// Se il parametro DB DEFAULT_REL_UO_USER_REGE = true
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGIU")) {
					// Il check "registrazione in uscita/interna" va preimpostato a spuntato
					flgRegistrazioneUINewItem.setValue(true);
					formUtenteDaSostituireCon.setValue("flgRegistrazioneUINew", true);
				}
				
				// Se il parametro DB DEFAULT_REL_UO_USER_GEST_ATTI = true
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_GEST_ATTI")) {
					// Il check "avvio/gestione atti proposti" va preimpostato a spuntato
					flgGestAttiNewItem.setValue(true);
					formUtenteDaSostituireCon.setValue("flgGestAttiNew", true);
					
					// setto il flag flgVisPropAttiInIterItem e lo disabilito
					flgVisPropAttiInIterNewItem.setValue(true);
					formUtenteDaSostituireCon.setValue("flgVisPropAttiInIterNew", true);
					
					flgVisPropAttiInIterItem.setCanEdit(false);
				}

				// Se il parametro DB DEFAULT_REL_UO_USER_GEST_ATTI = true
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_GEST_ATTI")) {
					// Il check "visualizzazione atti proposti" va preimpostato a spuntato
					flgVisPropAttiInIterNewItem.setValue(true);
					formUtenteDaSostituireCon.setValue("flgVisPropAttiInIterNew", true);
				}
			}
			
			// Se "L" (postazione ombra)
			if (tipoDiAssegnazione.equals("L")){
				
				// Se il parametro DB DEFAULT_POST_OMBRA_ACC_LIM_DOC_ASS_PERS = true
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_POST_OMBRA_ACC_LIM_DOC_ASS_PERS")) {
					// Il check "accesso limitato doc. assegnata personalmente" va preimpostato a spuntato
					flgAccessoDocLimSVNewItem.setValue(true);
					formUtenteDaSostituireCon.setValue("flgAccessoDocLimSVNew", true);
				}
			}
		}
	}
	*/
	
	// ottavio
	private void updateFlgUtenteCorrenteNew(){
		
		// Se il cliente e' A2A
		if (AurigaLayout.isAttivoClienteA2A()) {
			// Il check "registrazione in entrata" va preimpostato prendendo il valore dalla UO
			if (isUoAbilRegistrazioneE()){
				flgRegistrazioneENewItem.setValue(true);
				formUtenteDaSostituireCon.setValue("flgRegistrazioneENew", true);
					
			}
			else{
				flgRegistrazioneENewItem.setValue(false);
				formUtenteDaSostituireCon.setValue("flgRegistrazioneENew", false);
			}
			
			// Il check "registrazione in uscita" va preimpostato prendendo il valore dalla UO solo se quest'ultimo è 1, 
			// altrimenti si considera il parametro che indica il default (ovvero se la colonna della UO è 0 ma parametro DEFAULT_REL_UO_USER_REGIU = true il  flag "registrazione in uscita/interna" si spunta comunque)
			if (isUoAbilRegistrazioneU()){
				flgRegistrazioneUINewItem.setValue(true);
				formUtenteDaSostituireCon.setValue("flgRegistrazioneUINew", true);	
			}
			else{
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGIU")) {
					flgRegistrazioneUINewItem.setValue(true);
					formUtenteDaSostituireCon.setValue("flgRegistrazioneUINew", true);
				}
				else{
					flgRegistrazioneUINewItem.setValue(false);
					formUtenteDaSostituireCon.setValue("flgRegistrazioneUINew", false);
				}
			}
		}
		else{
			// Se il parametro DB DEFAULT_REL_UO_USER_REGE = true
			if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGE")) {
				// Il check "registrazione in entrata" va preimpostato a spuntato
				flgRegistrazioneENewItem.setValue(true);
				formUtenteDaSostituireCon.setValue("flgRegistrazioneENew", true);  
			}
			
			// Se il parametro DB DEFAULT_REL_UO_USER_REGE = true
			if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGIU")) {
				// Il check "registrazione in uscita/interna" va preimpostato a spuntato
				flgRegistrazioneUINewItem.setValue(true);
				formUtenteDaSostituireCon.setValue("flgRegistrazioneUINew", true);
			}
		}
			
				
		// Se il parametro DB DEFAULT_REL_UO_USER_GEST_ATTI = true
		if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_GEST_ATTI")) {
			// Il check "avvio/gestione atti proposti" va preimpostato a spuntato
			flgGestAttiNewItem.setValue(true);
			formUtenteDaSostituireCon.setValue("flgGestAttiNew", true);
			
			// setto il flag flgVisPropAttiInIterItem e lo disabilito
			flgVisPropAttiInIterNewItem.setValue(true);
			formUtenteDaSostituireCon.setValue("flgVisPropAttiInIterNew", true);
			
			flgVisPropAttiInIterNewItem.setCanEdit(false);
			
		}

		// Se il parametro DB DEFAULT_REL_UO_USER_GEST_ATTI = true
		if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_GEST_ATTI")) {
			// Il check "visualizzazione atti proposti" va preimpostato a spuntato
			flgVisPropAttiInIterNewItem.setValue(true);
			formUtenteDaSostituireCon.setValue("flgVisPropAttiInIterNew", true);
			
		}
	
		// Se il parametro DB DEFAULT_POST_OMBRA_ACC_LIM_DOC_ASS_PERS = true
		if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_POST_OMBRA_ACC_LIM_DOC_ASS_PERS")) {
			// Il check "accesso limitato doc. assegnata personalmente" va preimpostato a spuntato
			flgAccessoDocLimSVNewItem.setValue(true);
			formUtenteDaSostituireCon.setValue("flgAccessoDocLimSVNew", true);
		}
			
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO")) {
			if(flgGestAttiTuttiItem.getValueAsBoolean() && (flgGestAttiTuttiNewItem.getValueAsBoolean() == null || !flgGestAttiTuttiNewItem.getValueAsBoolean())) {
				flgGestAttiTuttiNewItem.setValue(true);
				formUtenteDaSostituireCon.setValue("flgGestAttiTuttiNew", true);
				listaTipiGestAttiSelezionatiNewItem.clearValue();
				selezionaTipiGestAttiNewButton.hide();
			} else if(flgGestAttiTuttiNewItem.getValueAsBoolean() != null && flgGestAttiTuttiNewItem.getValueAsBoolean()) {
				flgGestAttiTuttiNewItem.setValue(true);
				formUtenteDaSostituireCon.setValue("flgGestAttiTuttiNew", true);
				listaTipiGestAttiSelezionatiNewItem.clearValue();
				selezionaTipiGestAttiNewButton.hide();
			} else {
				flgGestAttiTuttiNewItem.setValue(false);
				formUtenteDaSostituireCon.setValue("flgGestAttiTuttiNew", false);
				listaTipiGestAttiSelezionatiNewItem.setValue(formUtenteDaSostituireCon.getValueAsString("listaTipiGestAttiSelezionatiNew") != null 
						&& !"".equals(formUtenteDaSostituireCon.getValueAsString("listaTipiGestAttiSelezionatiNew")) ? formUtenteDaSostituireCon.getValueAsString("listaTipiGestAttiSelezionatiNew") : formUtenteCorrente.getValueAsString("listaTipiGestAttiSelezionati"));
				formUtenteDaSostituireCon.setValue("listaTipiGestAttiSelezionatiNew", formUtenteDaSostituireCon.getValueAsString("listaTipiGestAttiSelezionatiNew") != null 
						&& !"".equals(formUtenteDaSostituireCon.getValueAsString("listaTipiGestAttiSelezionatiNew")) ? formUtenteDaSostituireCon.getValueAsString("listaTipiGestAttiSelezionatiNew") : formUtenteCorrente.getValueAsString("listaTipiGestAttiSelezionati"));
			}
			if(flgVisPropAttiInIterTuttiItem.getValueAsBoolean() && (flgVisPropAttiInIterTuttiNewItem.getValueAsBoolean() == null || !flgVisPropAttiInIterTuttiNewItem.getValueAsBoolean())) {
				flgVisPropAttiInIterTuttiNewItem.setValue(true);
				formUtenteDaSostituireCon.setValue("flgVisPropAttiInIterTuttiNew", true);
				listaTipiVisPropAttiInIterSelezionatiNewItem.clearValue();
				selezionaTipiVisPropAttiInIterNewButton.hide();
			} else if (flgVisPropAttiInIterTuttiNewItem.getValueAsBoolean() != null && flgVisPropAttiInIterTuttiNewItem.getValueAsBoolean()) {
				flgVisPropAttiInIterTuttiNewItem.setValue(true);
				formUtenteDaSostituireCon.setValue("flgVisPropAttiInIterTuttiNew", true);
				listaTipiVisPropAttiInIterSelezionatiNewItem.clearValue();
				selezionaTipiVisPropAttiInIterNewButton.hide();
			} else {
				flgVisPropAttiInIterTuttiNewItem.setValue(false);
				formUtenteDaSostituireCon.setValue("flgVisPropAttiInIterTuttiNew", false);
				listaTipiVisPropAttiInIterSelezionatiNewItem.setValue(formUtenteDaSostituireCon.getValueAsString("listaTipiVisPropAttiInIterSelezionatiNew") != null 
						&& !"".equals(formUtenteDaSostituireCon.getValueAsString("listaTipiVisPropAttiInIterSelezionatiNew")) ? formUtenteDaSostituireCon.getValueAsString("listaTipiVisPropAttiInIterSelezionatiNew") : formUtenteCorrente.getValueAsString("listaTipiVisPropAttiInIterSelezionati"));
				formUtenteDaSostituireCon.setValue("listaTipiVisPropAttiInIterSelezionatiNew", formUtenteDaSostituireCon.getValueAsString("listaTipiVisPropAttiInIterSelezionatiNew") != null 
						&& !"".equals(formUtenteDaSostituireCon.getValueAsString("listaTipiVisPropAttiInIterSelezionatiNew")) ? formUtenteDaSostituireCon.getValueAsString("listaTipiVisPropAttiInIterSelezionatiNew") : formUtenteCorrente.getValueAsString("listaTipiVisPropAttiInIterSelezionati"));
			}
		}
		
	}

	/*
	private void updateFlgUtenteCorrente(Object tipoDiAssegnazione){
	
		if (tipoDiAssegnazione != null && !tipoDiAssegnazione.equals("")){
		
			// Se "A" (Appartenenza gerarchica) o "D" (Funzionale/delega)
			if (tipoDiAssegnazione.equals("A") || tipoDiAssegnazione.equals("D")){
			
				// Se il parametro DB DEFAULT_REL_UO_USER_REGE = true
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGE")) {
					// Il check "registrazione in entrata" va preimpostato a spuntato
					flgRegistrazioneEItem.setValue(true);
					formUtenteCorrente.setValue("flgRegistrazioneE",      "true");  
				}
			
				// Se il parametro DB DEFAULT_REL_UO_USER_REGIU = true
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGIU")) {
					// Il check "registrazione in uscita/interna" va preimpostato a spuntato
					flgRegistrazioneUIItem.setValue(true);
					formUtenteCorrente.setValue("flgRegistrazioneUI", true);
				}
		
				// Se il parametro DB DEFAULT_REL_UO_USER_GEST_ATTI = true
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_GEST_ATTI")) {
					// Il check "avvio/gestione atti proposti" va preimpostato a spuntato
					flgGestAttiItem.setValue(true);
					formUtenteCorrente.setValue("flgGestAtti", true);
				
					// setto il flag flgVisPropAttiInIterItem e lo disabilito
					flgVisPropAttiInIterItem.setValue(true);
					formUtenteCorrente.setValue("flgVisPropAttiInIter", true);
				
					flgVisPropAttiInIterItem.setCanEdit(false);
				}

				// Se il parametro DB DEFAULT_REL_UO_USER_GEST_ATTI = true
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_GEST_ATTI")) {
					// Il check "visualizzazione atti proposti" va preimpostato a spuntato
					flgVisPropAttiInIterItem.setValue(true);
					formUtenteCorrente.setValue("flgVisPropAttiInIter", true);
				}
			}
		
			// Se "L" (postazione ombra)
			if (tipoDiAssegnazione.equals("L")){
				
				// Se il parametro DB DEFAULT_POST_OMBRA_ACC_LIM_DOC_ASS_PERS = true
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_POST_OMBRA_ACC_LIM_DOC_ASS_PERS")) {
					// Il check "accesso limitato doc. assegnata personalmente" va preimpostato a spuntato
					flgAccessoDocLimSVItem.setValue(true);
					formUtenteCorrente.setValue("flgAccessoDocLimSV", true);
				}
			}
		}
	}
	
	*/
	
	// ottavio
	private void updateFlgUtenteCorrente(){
		
		// Se il cliente e' A2A
		if (AurigaLayout.isAttivoClienteA2A()) {
			// Il check "registrazione in entrata" va preimpostato prendendo il valore dalla UO
			if (isUoAbilRegistrazioneE()){
				flgRegistrazioneEItem.setValue(true);
				formUtenteCorrente.setValue("flgRegistrazioneE", true);
			}
			else{
				flgRegistrazioneEItem.setValue(false);
				formUtenteCorrente.setValue("flgRegistrazioneE", false);
			}
							
			// Il check "registrazione in uscita" va preimpostato prendendo il valore dalla UO solo se quest'ultimo è 1, 
			// altrimenti si considera il parametro che indica il default (ovvero se la colonna della UO è 0 ma parametro DEFAULT_REL_UO_USER_REGIU = true il  flag "registrazione in uscita/interna" si spunta comunque)
			if (isUoAbilRegistrazioneU()){
				flgRegistrazioneUIItem.setValue(true);
				formUtenteCorrente.setValue("flgRegistrazioneUI", true);	
			}
			else{
				if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGIU")) {
					flgRegistrazioneUIItem.setValue(true);
					formUtenteCorrente.setValue("flgRegistrazioneUI", true);
				}
				else{
					flgRegistrazioneUIItem.setValue(false);
					formUtenteCorrente.setValue("flgRegistrazioneUI", false);
				}
			}
		}
		else{
			// Se il parametro DB DEFAULT_REL_UO_USER_REGE = true
			if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGE")) {
				// Il check "registrazione in entrata" va preimpostato a spuntato
				flgRegistrazioneEItem.setValue(true);
				formUtenteCorrente.setValue("flgRegistrazioneE",      "true");  
			}
			
			// Se il parametro DB DEFAULT_REL_UO_USER_REGIU = true
			if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_REGIU")) {
				// Il check "registrazione in uscita/interna" va preimpostato a spuntato
				flgRegistrazioneUIItem.setValue(true);
				formUtenteCorrente.setValue("flgRegistrazioneUI", true);
			}
		}

		
		// Se il parametro DB DEFAULT_REL_UO_USER_GEST_ATTI = true
		if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_GEST_ATTI")) {
			// Il check "avvio/gestione atti proposti" va preimpostato a spuntato
			flgGestAttiItem.setValue(true);
			formUtenteCorrente.setValue("flgGestAtti", true);
			
			// setto il flag flgVisPropAttiInIterItem e lo disabilito
			flgVisPropAttiInIterItem.setValue(true);
			formUtenteCorrente.setValue("flgVisPropAttiInIter", true);
			
			flgVisPropAttiInIterItem.setCanEdit(false);
			
		}

		// Se il parametro DB DEFAULT_REL_UO_USER_GEST_ATTI = true
		if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_REL_UO_USER_GEST_ATTI")) {
			// Il check "visualizzazione atti proposti" va preimpostato a spuntato
			flgVisPropAttiInIterItem.setValue(true);
			formUtenteCorrente.setValue("flgVisPropAttiInIter", true);
			
		}
	
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ABIL_TIPI_ATTI_IN_REL_USER_UO")) {
			if(flgGestAttiItem.getValueAsBoolean()) {
				flgGestAttiTuttiItem.setValue(true);
				flgGestAttiTuttiItem.show();
				formUtenteCorrente.setValue("flgGestAttiTutti", true);
				listaTipiGestAttiSelezionatiItem.clearValue();
				selezionaTipiGestAttiButton.hide();
			} else {
				flgGestAttiTuttiItem.setValue(false);
				flgGestAttiTuttiItem.hide();
				formUtenteCorrente.setValue("flgGestAttiTutti", false);
				listaTipiGestAttiSelezionatiItem.setValue(formUtenteCorrente.getValueAsString("listaTipiGestAttiSelezionati"));
				formUtenteCorrente.setValue("listaTipiGestAttiSelezionati", formUtenteCorrente.getValueAsString("listaTipiGestAttiSelezionati"));
			}
			if(flgVisPropAttiInIterItem.getValueAsBoolean()) {
				flgVisPropAttiInIterTuttiItem.setValue(true);
				flgVisPropAttiInIterTuttiItem.show();
				formUtenteCorrente.setValue("flgVisPropAttiInIterTutti", true);
				listaTipiVisPropAttiInIterSelezionatiItem.clearValue();
				selezionaTipiVisPropAttiInIterButton.hide();
			} else {
				flgVisPropAttiInIterTuttiItem.setValue(false);
				flgVisPropAttiInIterTuttiItem.hide();
				formUtenteCorrente.setValue("flgVisPropAttiInIterTutti", false);
				listaTipiVisPropAttiInIterSelezionatiItem.setValue(formUtenteCorrente.getValueAsString("listaTipiVisPropAttiInIterSelezionati"));
				formUtenteCorrente.setValue("listaTipiVisPropAttiInIterSelezionati", formUtenteCorrente.getValueAsString("listaTipiVisPropAttiInIterSelezionati"));
			}
		}
	
		// Se il parametro DB DEFAULT_POST_OMBRA_ACC_LIM_DOC_ASS_PERS = true
		if (AurigaLayout.getParametroDBAsBoolean("DEFAULT_POST_OMBRA_ACC_LIM_DOC_ASS_PERS")) {
			// Il check "accesso limitato doc. assegnata personalmente" va preimpostato a spuntato
			flgAccessoDocLimSVItem.setValue(true);
			formUtenteCorrente.setValue("flgAccessoDocLimSV", true);
		}
	}
	
	@Override
	public boolean customValidate() {
		boolean valid = super.customValidate();
		
		// Se ho messo "postazione ombra" e ho spuntato "accesso limitato doc. assegnata personalmente" e nessuno dei check 
		//   "registrazione in entrata"      
		//   "registrazione in uscita/interna" 
		//   "avvio/gestione atti proposti"   
		// e' spuntato diamo errore
		
		// Se non e' attiva la sezione per la sostituizione controllo i flag della sezione corrente 
		if (!isSostituzione) {
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
						vm.setFieldErrors("flgRegistrazioneE", "ATTENZIONE ! Almeno una delle opzioni 'registrazione in entrata','registrazione in uscita/interna','avvio/gestione atti proposti' deve essere selezionata.", true);
						valid = false;
					}
				}
			}
		}
		
		// Se e' attiva la sezione detailSectionUtenteDaSostituireCon allora controllo i flag della sezione di sostituzione
		if (isSostituzione) {
			if (tipoDiAssegnazioneNewSelectItem.getValue()!=null && tipoDiAssegnazioneNewSelectItem.getValueAsString().equals("L")){
				if (flgAccessoDocLimSVNewItem.getValue()!=null && flgAccessoDocLimSVNewItem.getValueAsBoolean()==true){
					boolean flgRegistrazioneENew = false;
					if (flgRegistrazioneENewItem.getValue()!=null && flgRegistrazioneENewItem.getValueAsBoolean()==true){
						flgRegistrazioneENew = true;
					}
					
					boolean flgRegistrazioneUINew = false;
					if (flgRegistrazioneUINewItem.getValue()!=null && flgRegistrazioneUINewItem.getValueAsBoolean()==true){
						flgRegistrazioneUINew = true;
					}
					
					boolean flgGestAttiNew = false;
					if (flgGestAttiNewItem.getValue()!=null && flgGestAttiNewItem.getValueAsBoolean()==true){
						flgGestAttiNew = true;
					}
					if (flgRegistrazioneENew==false && flgRegistrazioneUINew==false && flgGestAttiNew==false){
						vm.setFieldErrors("flgRegistrazioneENew", "ATTENZIONE ! Almeno una delle opzioni 'registrazione in entrata','registrazione in uscita/interna','avvio/gestione atti proposti' deve essere selezionata.", true);
						valid = false;
					}
				}
			}
		}
		return valid;
	}
	
	// ottavio
	private boolean isUoAbilRegistrazioneE() {
		return (abilitaUoProtEntrataItem.getValue()!=null && abilitaUoProtEntrataItem.getValue().equals("true"));
	}
	
	private boolean isUoAbilRegistrazioneU() {
		return (abilitaUoProtUscitaItem.getValue()!=null && abilitaUoProtUscitaItem.getValue().equals("true"));
	}
	
}