/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LegendaDinamicaPanel;
import it.eng.auriga.ui.module.layout.client.protocollazione.AssegnazioneItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class AssegnazionePopup extends ModalWindow {

	protected AssegnazionePopup _window;
	
	protected ValuesManager vm;

	protected DynamicForm formLegenda;
	protected DynamicForm formAssegnazione;
	protected DynamicForm formOpzioniInvio1;
	protected DynamicForm formOpzioniInvio2;
	protected DynamicForm formMessaggio;
	protected DynamicForm formNotificheRichieste;
	
	protected AssegnazioneItem assegnazioneItem;
	protected CheckboxItem flgSelezionaItem;
	protected List<CheckboxItem> listaCheckbox;
	protected List<CheckboxItem> listaCheckboxPreferiti;
	
	protected SelectItem motivoInvioItem;
	protected SelectItem livelloPrioritaItem;
	
	protected CheckboxItem flgInviaFascicoloItem;
	protected CheckboxItem flgInviaDocCollegatiItem;
	protected CheckboxItem flgMantieniCopiaUdItem;
	protected CheckboxItem flgMandaNotificaMailItem;

	protected TextAreaItem messaggioInvioItem;
	
	protected CheckboxItem flgPresaInCaricoItem;
	protected CheckboxItem flgMancataPresaInCaricoItem;
	protected NumericItem giorniTrascorsiItem;
	
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;

	protected String flgUdFolder;
	
	public AssegnazionePopup(String pFlgUdFolder, final Record pListRecord) {
		this(pFlgUdFolder, pListRecord, null, "");
	}
	
	public AssegnazionePopup(String pFlgUdFolder, final Record pListRecord, String pTitle) {
		this(pFlgUdFolder, pListRecord, pTitle, "");
	}
	
	public AssegnazionePopup(String pFlgUdFolder, final Record pListRecord, String pTitle, String callFrom) {

		super("assegnazione", true);

		_window = this;
		
		vm = new ValuesManager();

		flgUdFolder = pFlgUdFolder != null ? pFlgUdFolder : "";
		
		if(pTitle != null && !"".equals(pTitle)) {
			setTitle(pTitle);
		} else {
			setTitle("Compila dati assegnazione");
		}
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		formAssegnazione = new DynamicForm();
		formAssegnazione.setValuesManager(vm);
		formAssegnazione.setKeepInParentRect(true);
		formAssegnazione.setWidth100();
		formAssegnazione.setHeight100();
		formAssegnazione.setNumCols(7);
		formAssegnazione.setColWidths(10, 10, 10, 10, 10, "*", "*");
		formAssegnazione.setCellPadding(2);
		formAssegnazione.setWrapItemTitles(false);
		
		assegnazioneItem = new AssegnazioneItem() {
			
			@Override
			public String getFinalitaOrganigrammaLookup() {
				String suffissoFinalita = getSuffissoFinalitaOrganigramma() != null ? getSuffissoFinalitaOrganigramma() : "";
				return super.getFinalitaOrganigrammaLookup() + suffissoFinalita;
			}
		
			@Override
			public String getFinalitaLoadComboOrganigramma() {				
				String suffissoFinalita = getSuffissoFinalitaOrganigramma() != null ? getSuffissoFinalitaOrganigramma() : "";
				return super.getFinalitaLoadComboOrganigramma() + suffissoFinalita;
			}
			
			@Override
			public boolean isDimOrganigrammaNonStd() {
				if(getFlgTipoProvDoc() != null && "E".equals(getFlgTipoProvDoc())) {
					return AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && AurigaLayout.isPrivilegioAttivo("SEL/DEST_E/ES");
				}
				return super.isDimOrganigrammaNonStd();
			}
			
			@Override
			public int getFilteredSelectItemWidth() {
				if ("F".equals(flgUdFolder)) {
					return 550;
				} 
				return super.getFilteredSelectItemWidth();
			}
			
			@Override
			public boolean showOpzioniInvioAssegnazioneButton() {
				return false;
			}
			
			@Override
			public String getIdUdProtocollo() {
				if ("U".equals(flgUdFolder)) {
					return pListRecord != null ? pListRecord.getAttribute("idUdFolder") : null;
				}
				return null;
			}
			
			@Override
			public String getSupportoOriginaleProt() {
				if(getCodSupportoOrig() != null) {
					if("C".equals(getCodSupportoOrig())) return "cartaceo";
					if("D".equals(getCodSupportoOrig())) return "digitale";
					if("M".equals(getCodSupportoOrig())) return "misto";
				}
				return null;
			}
			
			@Override
			public boolean showPreferiti() {
				if(getListaPreferiti() != null && !getListaPreferiti().isEmpty()) {
					return false;
				}
				return super.showPreferiti();
			}
			
			@Override
			public boolean showAssegnatariMitt() {
				if(getListaAssegnatariMitt() != null && !getListaAssegnatariMitt().isEmpty()) {
					return false;
				}
				return super.showAssegnatariMitt();
			}
			
		};		
		assegnazioneItem.setName("listaAssegnazioni");
		assegnazioneItem.setShowTitle(false);
		assegnazioneItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		assegnazioneItem.setCanEdit(true);
		assegnazioneItem.setColSpan(5);
		assegnazioneItem.setFlgUdFolder(flgUdFolder);
		if (isAssegnazioneUnica()) {
			assegnazioneItem.setNotReplicable(true);
			assegnazioneItem.setFlgSenzaLD(true);
		}
		assegnazioneItem.setAttribute("obbligatorio", true);
				
		if ((getListaPreferiti() != null && !getListaPreferiti().isEmpty())) {
			
			listaCheckbox = new ArrayList<CheckboxItem>();				
			listaCheckboxPreferiti = new ArrayList<CheckboxItem>();					
						
			if (isAssegnazioneUnica()) {
				
				List<FormItem> fields = new ArrayList<FormItem>();
				
				flgSelezionaItem = new CheckboxItem("seleziona", "<i>Seleziona...</i>");
				flgSelezionaItem.setDefaultValue(true);
				flgSelezionaItem.setStartRow(false);
				flgSelezionaItem.setEndRow(false);			
				flgSelezionaItem.setColSpan(1);
				flgSelezionaItem.setWidth(100);
				flgSelezionaItem.setHeight(30);			
				
				fields.add(flgSelezionaItem);
				listaCheckbox.add(flgSelezionaItem);
				
				fields.add(assegnazioneItem);
				
				if (getListaPreferiti() != null && !getListaPreferiti().isEmpty()) {
		
					buildCheckboxListaPreferiti();	
					
					for(int i = 0; i < listaCheckboxPreferiti.size(); i++){
						fields.add(listaCheckboxPreferiti.get(i));
						listaCheckbox.add(listaCheckboxPreferiti.get(i));					
					}				
				}				
				manageClickCheckboxEsclusive();				
				formAssegnazione.setFields(fields.toArray(new FormItem[fields.size()]));
				
			} else {
				
				List<FormItem> fields = new ArrayList<FormItem>();
				fields.add(assegnazioneItem);
								
				if (getListaPreferiti() != null && !getListaPreferiti().isEmpty()) {
					
					buildCheckboxListaPreferiti();
				
					for(int i = 0; i < listaCheckboxPreferiti.size(); i++){
						fields.add(listaCheckboxPreferiti.get(i));
					}
				}
				formAssegnazione.setFields(fields.toArray(new FormItem[fields.size()]));	
			}
		} else {		
			formAssegnazione.setFields(assegnazioneItem);	
		}

		formOpzioniInvio1 = new DynamicForm();
		formOpzioniInvio1.setValuesManager(vm);
		formOpzioniInvio1.setKeepInParentRect(true);
		formOpzioniInvio1.setWidth100();
		formOpzioniInvio1.setHeight100();
		formOpzioniInvio1.setNumCols(8);
		formOpzioniInvio1.setColWidths(60, 1, 1, 1, 1, 1, "*", "*");
		formOpzioniInvio1.setCellPadding(5);
		formOpzioniInvio1.setWrapItemTitles(false);
		
		GWTRestDataSource motivoInvioDS = new GWTRestDataSource("LoadComboMotivoInvioDataSource", "key", FieldType.TEXT);
		if ("U".equals(flgUdFolder)) {
			motivoInvioDS.addParam("tableName", "DMT_INVII_UD");
			motivoInvioDS.addParam("idUd", pListRecord != null ? pListRecord.getAttribute("idUdFolder") : null);
		} else if ("F".equals(flgUdFolder)) {
			motivoInvioDS.addParam("tableName", "DMT_MOVIMENTI_FOLDER");
		}

		motivoInvioItem = new SelectItem("motivoInvio", "Motivo");
		motivoInvioItem.setStartRow(true);
		motivoInvioItem.setOptionDataSource(motivoInvioDS);
		motivoInvioItem.setAutoFetchData(true);
		motivoInvioItem.setDisplayField("value");
		motivoInvioItem.setValueField("key");
		motivoInvioItem.setWidth(280);
		motivoInvioItem.setWrapTitle(false);
		motivoInvioItem.setColSpan(1);
		motivoInvioItem.setAllowEmptyValue(true);

		GWTRestDataSource livelloPrioritaDS = new GWTRestDataSource("LoadComboPrioritaInvioDataSource", "key", FieldType.TEXT);

		livelloPrioritaItem = new SelectItem("livelloPriorita", "Priorità");
		livelloPrioritaItem.setStartRow(true);
		livelloPrioritaItem.setOptionDataSource(livelloPrioritaDS);
		livelloPrioritaItem.setAutoFetchData(true);
		livelloPrioritaItem.setDisplayField("value");
		livelloPrioritaItem.setValueField("key");
		livelloPrioritaItem.setWidth(150);
		livelloPrioritaItem.setWrapTitle(false);
		livelloPrioritaItem.setColSpan(1);
		livelloPrioritaItem.setAllowEmptyValue(true);
		
		SpacerItem spacerFlgMandaNotificaMail = new SpacerItem();
		spacerFlgMandaNotificaMail.setStartRow(true);
		spacerFlgMandaNotificaMail.setColSpan(1);
		spacerFlgMandaNotificaMail.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				boolean isVisible = false;
				if ("U".equals(flgUdFolder)) {
					if("on-demand".equals(AurigaLayout.getParametroDB("ATTIVA_NOT_EMAIL_INVIO_CC_UD"))) {
						isVisible = true;
					}
				} else if ("F".equals(flgUdFolder)) {
					if("on-demand".equals(AurigaLayout.getParametroDB("ATTIVA_NOT_EMAIL_INVIO_CC_FLD"))) {
						isVisible = true;
					}
				}
				return isVisible;
			}
		});	
		
		flgMandaNotificaMailItem = new CheckboxItem("flgMandaNotificaMail", "manda notifica mail");
		flgMandaNotificaMailItem.setColSpan(1);
		flgMandaNotificaMailItem.setWidth(1);
		flgMandaNotificaMailItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isVisible = false;
				if ("U".equals(flgUdFolder)) {
					if("on-demand".equals(AurigaLayout.getParametroDB("ATTIVA_NOT_EMAIL_ASSEGN_UD"))) {
						isVisible = true;
					}
				} else if ("F".equals(flgUdFolder)) {
					if("on-demand".equals(AurigaLayout.getParametroDB("ATTIVA_NOT_EMAIL_INVIO_CC_FLD"))) {
						isVisible = true;
					}
				}
				return isVisible;
			}
		});	
		
		if ("F".equals(flgUdFolder)) {
			formOpzioniInvio1.setFields(new FormItem[]{motivoInvioItem, livelloPrioritaItem, spacerFlgMandaNotificaMail, flgMandaNotificaMailItem});
		} else {
			formOpzioniInvio1.setFields(new FormItem[]{motivoInvioItem, livelloPrioritaItem});
		}
		
		if ("U".equals(flgUdFolder)) {
			
			formOpzioniInvio2 = new DynamicForm();
			formOpzioniInvio2.setValuesManager(vm);
			formOpzioniInvio2.setKeepInParentRect(true);
			formOpzioniInvio2.setWidth100();
			formOpzioniInvio2.setHeight100();
			formOpzioniInvio2.setNumCols(8);
			formOpzioniInvio2.setColWidths(60, 1, 1, 1, 1, 1, "*", "*");
			formOpzioniInvio2.setCellPadding(5);
			formOpzioniInvio2.setWrapItemTitles(false);
			
			SpacerItem spacerItem = new SpacerItem();
			spacerItem.setColSpan(1);
			
			flgInviaFascicoloItem = new CheckboxItem("flgInviaFascicolo", "invia fascicolo");
			flgInviaFascicoloItem.setColSpan(1);
			flgInviaFascicoloItem.setWidth(1);
	
			flgInviaDocCollegatiItem = new CheckboxItem("flgInviaDocCollegati", "invia doc. collegati");
			flgInviaDocCollegatiItem.setColSpan(1);
			flgInviaDocCollegatiItem.setWidth(1);
	
			flgMantieniCopiaUdItem = new CheckboxItem("flgMantieniCopiaUd", "mantieni copia");
			flgMantieniCopiaUdItem.setColSpan(1);
			flgMantieniCopiaUdItem.setWidth(1);
			if(!callFrom.equalsIgnoreCase("InviiEffettuatiList") ){
				if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_DEFAULT_MANTIENI_COPIA_IN_ASS_DOC")) {
					flgMantieniCopiaUdItem.setDefaultValue(true);			
				}
			}
			
			formOpzioniInvio2.setFields(new FormItem[]{spacerItem, flgInviaFascicoloItem, flgInviaDocCollegatiItem, flgMantieniCopiaUdItem, flgMandaNotificaMailItem});
		} 
			
		formMessaggio = new DynamicForm();
		formMessaggio.setValuesManager(vm);
		formMessaggio.setKeepInParentRect(true);
		formMessaggio.setWidth100();
		formMessaggio.setHeight100();
		formMessaggio.setNumCols(5);
		formMessaggio.setColWidths(10, "*", "*", "*", "*");
		formMessaggio.setCellPadding(5);
		formMessaggio.setWrapItemTitles(false);
		
		messaggioInvioItem = new TextAreaItem("messaggioInvio", "Messaggio");
		messaggioInvioItem.setShowTitle(false);
		messaggioInvioItem.setStartRow(true);
		messaggioInvioItem.setLength(4000);
		messaggioInvioItem.setHeight(40);
		messaggioInvioItem.setColSpan(5);		
		messaggioInvioItem.setWidth(780);
		
		formMessaggio.setFields(messaggioInvioItem);
		
		formNotificheRichieste = new DynamicForm();
		formNotificheRichieste.setValuesManager(vm);
		formNotificheRichieste.setKeepInParentRect(true);
		formNotificheRichieste.setWidth100();
		formNotificheRichieste.setHeight100();
		formNotificheRichieste.setNumCols(5);
		formNotificheRichieste.setColWidths(1, 1, 1, 1, "*");
		formNotificheRichieste.setCellPadding(5);
		formNotificheRichieste.setWrapItemTitles(false);
		
		flgPresaInCaricoItem = new CheckboxItem("flgPresaInCarico", I18NUtil.getMessages().protocollazione_opzioniInvioAssegnazionePopup_flgPresaInCaricoItem_title());
		flgPresaInCaricoItem.setColSpan(4);
		flgPresaInCaricoItem.setStartRow(true);
		
		flgMancataPresaInCaricoItem = new CheckboxItem("flgMancataPresaInCarico", I18NUtil.getMessages().protocollazione_opzioniInvioAssegnazionePopup_flgMancataPresaInCaricoItem_title());
		flgMancataPresaInCaricoItem.setColSpan(1);
		flgMancataPresaInCaricoItem.setWidth(1);
		flgMancataPresaInCaricoItem.setStartRow(true);
		flgMancataPresaInCaricoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				formNotificheRichieste.redraw();
			}
		});
		
		giorniTrascorsiItem = new NumericItem("giorniTrascorsi", I18NUtil.getMessages().protocollazione_opzioniInvioAssegnazionePopup_giorniTrascorsiItem_title());  					
		giorniTrascorsiItem.setColSpan(1);
		giorniTrascorsiItem.setShowIfCondition(new FormItemIfFunction() {	
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgMancataPresaInCaricoItem.getValueAsBoolean();
			}
		});
		
		formNotificheRichieste.setFields(flgPresaInCaricoItem, flgMancataPresaInCaricoItem, giorniTrascorsiItem);
				
		Button confermaButton = new Button("Ok");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
					@Override
					public void execute() {
						
						if (validate()) {
							final Record record = new Record(vm.getValues());
							record.setAttribute("listaAssegnazioni", getAssegnazioni());						
							boolean verifyCheckLD = manageVerifyCheckLD(record);
							if(verifyCheckLD) {
								SC.ask(I18NUtil.getMessages().protocollazione_opzioniInvioAssegnazionePopup_verifycheckLD(), new BooleanCallback() {
									
									@Override
									public void execute(Boolean value) {
										
										if(value) {
											onClickOkButton(record, new DSCallback() {
												
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													
													_window.markForDestroy();
												}
											});
										}
									}
								});							
							} else {
								if(record != null && record.getAttributeAsBoolean("flgInviaFascicolo") != null &&
										record.getAttributeAsBoolean("flgInviaFascicolo")) {
									
									SC.ask("Sei sicuro di voler assegnare tutta la documentazione del fascicolo ?", new BooleanCallback() {
										
										@Override
										public void execute(Boolean value) {
											
											if(value) {
												onClickOkButton(record, new DSCallback() {
													
													@Override
													public void execute(DSResponse response, Object rawData, DSRequest request) {
														
														_window.markForDestroy();
													}
												});
											}
										}
									});
								} else {
									onClickOkButton(record, new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											
											_window.markForDestroy();
										}
									});
								}
							}
						}
					}
				});					
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
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIS_ICONA_TIPO_UO")) {
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_LEGENDA_DIN_TIPO_UO")) {
				LegendaDinamicaPanel legendaPanel = new LegendaDinamicaPanel();
				if (AurigaLayout.getIsAttivaAccessibilita()) {
					legendaPanel.setTabIndex(-1);
					legendaPanel.setCanFocus(false);		
				}
				layout.addMember(legendaPanel);
			} else {
				buildFormLegenda();
				if (AurigaLayout.getIsAttivaAccessibilita()) {
					formLegenda.setTabIndex(-1);
					formLegenda.setCanFocus(false);		
				}
				layout.addMember(formLegenda);
			}
		}

		DetailSection detailSectionAssegnazione = new DetailSection("F".equals(flgUdFolder) ? "Destinatario" : "Destinatario/i", true, true, true, formAssegnazione);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			detailSectionAssegnazione.setCanFocus(false);
			detailSectionAssegnazione.setTabIndex(-1);		
		}
		DetailSection detailSectionOpzioniInvio = null;
		if ("U".equals(flgUdFolder)) {
			detailSectionOpzioniInvio = new DetailSection("Opzioni invio", true, true, false, formOpzioniInvio1, formOpzioniInvio2);			
		} else {
			detailSectionOpzioniInvio = new DetailSection("Opzioni invio", true, true, false, formOpzioniInvio1);			
		}
		DetailSection detailSectionMessaggio = new DetailSection("Messaggio", true, true, false, formMessaggio);
		DetailSection detailSectionNotificheRichieste = new DetailSection("Notifiche richieste", true, true, false, formNotificheRichieste);		
		
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			detailSectionOpzioniInvio.setCanFocus(false);
			detailSectionMessaggio.setCanFocus(false);
			detailSectionNotificheRichieste.setCanFocus(false);					
		}
		layout.addMember(detailSectionAssegnazione);
		layout.addMember(detailSectionOpzioniInvio);
		layout.addMember(detailSectionMessaggio);
		layout.addMember(detailSectionNotificheRichieste);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);

		setIcon("archivio/assegna.png");
	}
	
	private Boolean manageVerifyCheckLD(Record record) {
		boolean verifyCheckLD = false;
		final boolean flgInviaFascicolo = record.getAttributeAsBoolean("flgInviaFascicolo") != null && record.getAttributeAsBoolean("flgInviaFascicolo");
		final boolean flgInviaDocCollegati = record.getAttributeAsBoolean("flgInviaDocCollegati") != null && record.getAttributeAsBoolean("flgInviaDocCollegati");
		if(record != null) {	
			if(record.getAttributeAsRecordList("listaAssegnazioni") != null &&
					!record.getAttributeAsRecordList("listaAssegnazioni").isEmpty()) {
				for(int i=0; i < record.getAttributeAsRecordList("listaAssegnazioni").getLength(); i++) {
					Record recItem = record.getAttributeAsRecordList("listaAssegnazioni").get(i);
					if(recItem != null && recItem.getAttributeAsString("tipo") != null &&
							"LD".equalsIgnoreCase(recItem.getAttributeAsString("tipo"))) {
						verifyCheckLD = true;
						break;
					}
				}
			}
		}
		return (flgInviaFascicolo || flgInviaDocCollegati) && verifyCheckLD;
	}
	
	public RecordList getAssegnazioni() {
		if (getListaPreferiti() != null && !getListaPreferiti().isEmpty()) {		
			if(isAssegnazioneUnica()) {
				if(formAssegnazione.getValue("seleziona") != null && (Boolean) formAssegnazione.getValue("seleziona")) {
					return formAssegnazione.getValueAsRecordList("listaAssegnazioni");					
				} else { 
					RecordList listaAssegnazioni = new RecordList();
//					for(int i=0; i < getListaAssegnatariMitt().getLength(); i++){
//						Record currentRecord = getListaAssegnatariMitt().get(i);
//						String tipoAssegnatarioMitt = currentRecord.getAttribute("flgUoSv");
//						String idAssegnatarioMitt = currentRecord.getAttribute("idUoSv");
//						if(formAssegnazione.getValue(tipoAssegnatarioMitt + idAssegnatarioMitt) != null && (Boolean) formAssegnazione.getValue(tipoAssegnatarioMitt + idAssegnatarioMitt)){
//							Record recordAssegnazioni = new Record();
//							recordAssegnazioni.setAttribute("typeNodo",tipoAssegnatarioMitt);
//							recordAssegnazioni.setAttribute("idUo", idAssegnatarioMitt);
//							listaAssegnazioni.add(recordAssegnazioni);									
//						}
//					}	
					for(int i=0; i < getListaPreferiti().getLength(); i++){
						Record currentRecord = getListaPreferiti().get(i);
						String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
						String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
						if(formAssegnazione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito) != null && (Boolean) formAssegnazione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito)){
							Record recordAssegnazioni = new Record();
							recordAssegnazioni.setAttribute("typeNodo",tipoDestinatarioPreferito);
							recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
							listaAssegnazioni.add(recordAssegnazioni);									
						}
					}	
					return listaAssegnazioni;		
				}
			} else {
				RecordList listaAssegnazioni =  formAssegnazione.getValueAsRecordList("listaAssegnazioni");
//				for(int i=0; i < getListaAssegnatariMitt().getLength(); i++){
//					Record currentRecord = getListaAssegnatariMitt().get(i);
//					String tipoAssegnatarioMitt = currentRecord.getAttribute("flgUoSv");
//					String idAssegnatarioMitt = currentRecord.getAttribute("idUoSv");
//					if(formAssegnazione.getValue(tipoAssegnatarioMitt + idAssegnatarioMitt) != null && (Boolean) formAssegnazione.getValue(tipoAssegnatarioMitt + idAssegnatarioMitt)){
//						Record recordAssegnazioni = new Record();
//						recordAssegnazioni.setAttribute("typeNodo",tipoAssegnatarioMitt);
//						recordAssegnazioni.setAttribute("idUo", idAssegnatarioMitt);
//						listaAssegnazioni.add(recordAssegnazioni);									
//					}
//				}	
				for(int i=0; i < getListaPreferiti().getLength(); i++){
					Record currentRecord = getListaPreferiti().get(i);
					String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
					String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
					if(formAssegnazione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito) != null && (Boolean) formAssegnazione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito)){
						Record recordAssegnazioni = new Record();
						recordAssegnazioni.setAttribute("typeNodo",tipoDestinatarioPreferito);
						recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
						listaAssegnazioni.add(recordAssegnazioni);									
					}
				}	
				return listaAssegnazioni;		
			}			
		} else {
			return formAssegnazione.getValueAsRecordList("listaAssegnazioni");				
		}	
	}

	private void buildFormLegenda() {
		
		formLegenda = new DynamicForm();
		formLegenda.setKeepInParentRect(true);
		formLegenda.setCellPadding(5);
		formLegenda.setWrapItemTitles(false);

		StaticTextItem tipoUOImage = new StaticTextItem("iconaStatoConsolidamento");
		tipoUOImage.setShowValueIconOnly(true);
		tipoUOImage.setShowTitle(false);
		tipoUOImage.setValueIconWidth(600);
		tipoUOImage.setValueIconHeight(60);
		tipoUOImage.setAlign(Alignment.LEFT);
		Map<String, String> valueIcons = new HashMap<String, String>();
		valueIcons.put("1", "organigramma/legenda_uo.png");
		tipoUOImage.setValueIcons(valueIcons);
		tipoUOImage.setDefaultValue("1");
		tipoUOImage.setDefaultIconSrc("organigramma/legenda_uo.png");

		formLegenda.setItems(tipoUOImage);
	}
	
//	private void buildCheckboxListaAssegnatariMitt(){
//		for(int i = 0; i < getListaAssegnatariMitt().getLength();i++){
//			Record currentRecord = getListaAssegnatariMitt().get(i);
//			String tipoAssegnatarioMitt = currentRecord.getAttribute("flgUoSv");
//			String idAssegnatarioMitt = currentRecord.getAttribute("idUoSv");
//			String descrizioneAssegnatarioMitt = currentRecord.getAttribute("descrizione");
//			CheckboxItem flgAssegnatarioMittItem = new CheckboxItem(tipoAssegnatarioMitt + idAssegnatarioMitt, descrizioneAssegnatarioMitt);
//			flgAssegnatarioMittItem.setStartRow(true);
//			flgAssegnatarioMittItem.setColSpan(6);
//			flgAssegnatarioMittItem.setWrapTitle(false);		
//			listaCheckboxAssegnatariMitt.add(flgAssegnatarioMittItem);
//		}
//	}
	
	private void buildCheckboxListaPreferiti(){
		for(int i = 0; i < getListaPreferiti().getLength();i++){
			Record currentRecord = getListaPreferiti().get(i);
			String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
			String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
			String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
			CheckboxItem flgPreferitoItem = new CheckboxItem(tipoDestinatarioPreferito + idDestinatarioPreferito, descrizioneDestinatarioPreferito);
			flgPreferitoItem.setStartRow(true);
			flgPreferitoItem.setColSpan(6);
			listaCheckboxPreferiti.add(flgPreferitoItem);
		}
	}

	private void manageClickCheckboxEsclusive(){
		for(CheckboxItem check : listaCheckbox){
			check.addChangeHandler(new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent event) {
					
					String nameCheckboxClicked = event.getItem().getName();
					for(CheckboxItem check : listaCheckbox){
						if(nameCheckboxClicked.equals(check.getName())){
							check.setValue(true);
						} else {
							check.setValue(false);
						}	
						formAssegnazione.clearErrors(true);
						if(nameCheckboxClicked.equals("seleziona")){
							assegnazioneItem.show();
							assegnazioneItem.setAttribute("obbligatorio", true);
						} else {
							assegnazioneItem.hide();
							assegnazioneItem.setAttribute("obbligatorio", false);
						}
					}				
				}
			});
		}
	}
	
	public boolean isAssegnazioneUnica() {
		if("U".equals(flgUdFolder) && getFlgTipoProvDoc() != null && "E".equals(getFlgTipoProvDoc())) {
			return AurigaLayout.getParametroDBAsBoolean("ASSEGNATARIO_UNICO_ENTRATA");
		}
		return ("F".equals(flgUdFolder) || isDocCartaceo());		
	}
	
	public boolean validate() {
//		if(getListaAssegnatariMitt() != null) {
//			for(int i=0; i < getListaAssegnatariMitt().getLength(); i++){
//				Record currentRecord = getListaAssegnatariMitt().get(i);
//				String tipoAssegnatarioMitt = currentRecord.getAttribute("flgUoSv");
//				String idAssegnatarioMitt = currentRecord.getAttribute("idUoSv");
//				if(formAssegnazione.getValue(tipoAssegnatarioMitt + idAssegnatarioMitt) != null && (Boolean) formAssegnazione.getValue(tipoAssegnatarioMitt + idAssegnatarioMitt)){
//					return true;									
//				}
//			}	
//		}
		if(getListaPreferiti() != null) {
			for(int i=0; i < getListaPreferiti().getLength(); i++){
				Record currentRecord = getListaPreferiti().get(i);
				String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
				String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
				if(formAssegnazione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito) != null && (Boolean) formAssegnazione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito)){
					return true;									
				}
			}	
		}
		return assegnazioneItem.validate();
	}	

	public void editRecordPerModificaInvio(Record record) {
		vm.editRecord(record);
		markForRedraw();
	}
		
	public String getFlgTipoProvDoc() {
		return null;
	}
	
	public String getSuffissoFinalitaOrganigramma() {
		String suffissoFinalita = "";
		if(getFlgTipoProvDoc() != null && "E".equals(getFlgTipoProvDoc())) {
			suffissoFinalita = "_ENTRATA";			
		}
		return suffissoFinalita;
	}
	
	public boolean isDocCartaceo() {
		return "U".equals(flgUdFolder) && "C".equals(getCodSupportoOrig());		
	}
	
	public String getCodSupportoOrig() {
		return null;
	}
	
	public RecordList getListaPreferiti() {
		return null;
	}
	
	public RecordList getListaAssegnatariMitt() {
		return null;
	}
	
	public abstract void onClickOkButton(Record record, DSCallback callback);
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {}
		}
		super.onDestroy();
	}
	
}