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
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LegendaDinamicaPanel;
import it.eng.auriga.ui.module.layout.client.protocollazione.CondivisioneItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class CondivisionePopup extends ModalWindow {

	protected CondivisionePopup _window;
	
	protected ValuesManager vm;

	protected DynamicForm formLegenda;
	protected DynamicForm formCondivisione;
	protected DynamicForm formOpzioniInvio1;
	protected DynamicForm formOpzioniInvio2;
	protected DynamicForm formMessaggio;
	protected DynamicForm formNotificheRichieste;
	
	protected CondivisioneItem condivisioneItem;
	protected List<CheckboxItem> listaCheckboxPreferiti;
	
	protected CheckboxItem flgInviaFascicoloItem;
	protected CheckboxItem flgInviaDocCollegatiItem;
	protected CheckboxItem flgMandaNotificaMailItem;
	
	protected SelectItem motivoInvioItem;
	protected SelectItem livelloPrioritaItem;
	
	protected TextAreaItem messaggioInvioItem;
	
	protected CheckboxItem flgPresaInCaricoItem;
	protected CheckboxItem flgMancataPresaInCaricoItem;
	protected NumericItem giorniTrascorsiItem;
	
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;

	protected String flgUdFolder;
	
	public CondivisionePopup(String pFlgUdFolder, Record pListRecord) {
		this(pFlgUdFolder, pListRecord, null);
	}

	public CondivisionePopup(String pFlgUdFolder, final Record pListRecord, String pTitle) {

		super("condivisione", true);

		_window = this;

		vm = new ValuesManager();

		flgUdFolder = pFlgUdFolder;
		
		if(pTitle != null && !"".equals(pTitle)) {
			setTitle(pTitle);
		} else {
			setTitle(I18NUtil.getMessages().condivisioneWindow_title());
		}

		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		formCondivisione = new DynamicForm();
		formCondivisione.setValuesManager(vm);
		formCondivisione.setKeepInParentRect(true);
		formCondivisione.setWidth100();
		formCondivisione.setHeight100();
		formCondivisione.setNumCols(7);
		formCondivisione.setColWidths(10, 10, 10, 10, 10, "*", "*");
		formCondivisione.setCellPadding(2);
		formCondivisione.setWrapItemTitles(false);
		
		condivisioneItem = new CondivisioneItem() {
			
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
			public boolean showOpzioniInvioCondivisioneButton() {
				return false;
			}
			
			@Override
			public boolean showPreferiti() {
				if(getListaPreferiti() != null && !getListaPreferiti().isEmpty()) {
					return false;
				}
				return super.showPreferiti();
			}
			
			@Override
			public String getIdUdProtocollo() {
				if ("U".equals(flgUdFolder)) {
					return pListRecord != null ? pListRecord.getAttribute("idUdFolder") : null;
				}
				return null;
			}
		};
		condivisioneItem.setName("listaDestInvioCC");
		condivisioneItem.setShowTitle(false);
		condivisioneItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		condivisioneItem.setCanEdit(true);
		condivisioneItem.setColSpan(5);
		condivisioneItem.setFlgUdFolder(flgUdFolder);
		condivisioneItem.setAttribute("obbligatorio", true);

		if (getListaPreferiti() != null && !getListaPreferiti().isEmpty()) {
			
			listaCheckboxPreferiti = new ArrayList<CheckboxItem>();
			
			List<FormItem> fields = new ArrayList<FormItem>();
			
			fields.add(condivisioneItem);
			
			// Popolamento della lista di CheckBox
			buildCheckboxListaPreferiti();
			
			for(int i = 0; i < listaCheckboxPreferiti.size(); i++){
				fields.add(listaCheckboxPreferiti.get(i));
			}
			
			formCondivisione.setFields(fields.toArray(new FormItem[fields.size()]));	
			
		} else {
			
			formCondivisione.setFields(condivisioneItem);			
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
		motivoInvioDS.addParam("tableName", "DMT_NOTIFICHE");

		motivoInvioItem = new SelectItem("motivoInvio", I18NUtil.getMessages().condivisioneWindow_motivoInvioItem_title());
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

		livelloPrioritaItem = new SelectItem("livelloPriorita", I18NUtil.getMessages().condivisioneWindow_livelloPrioritaItem_title());
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
		
		if("F".equals(flgUdFolder)) {
			formOpzioniInvio1.setFields(new FormItem[] {motivoInvioItem, livelloPrioritaItem, spacerFlgMandaNotificaMail, flgMandaNotificaMailItem});
		} else {
			formOpzioniInvio1.setFields(new FormItem[] {motivoInvioItem, livelloPrioritaItem});
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
	
			formOpzioniInvio2.setFields(new FormItem[]{spacerItem, flgInviaFascicoloItem, flgInviaDocCollegatiItem, flgMandaNotificaMailItem});
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
		
		messaggioInvioItem = new TextAreaItem("messaggioInvio", I18NUtil.getMessages().condivisioneWindow_messaggioInvioItem_title());
		messaggioInvioItem.setShowTitle(false);
		messaggioInvioItem.setStartRow(true);
		messaggioInvioItem.setLength(4000);
		messaggioInvioItem.setHeight(40);
		messaggioInvioItem.setColSpan(5);
		messaggioInvioItem.setWidth(752);

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
		
		flgPresaInCaricoItem = new CheckboxItem("flgPresaInCarico", "alla presa visione");
		flgPresaInCaricoItem.setColSpan(4);
		flgPresaInCaricoItem.setStartRow(true);
		
		flgMancataPresaInCaricoItem = new CheckboxItem("flgMancataPresaInCarico", "in caso di mancata presa visione");
		flgMancataPresaInCaricoItem.setColSpan(1);
		flgMancataPresaInCaricoItem.setWidth(1);
		flgMancataPresaInCaricoItem.setStartRow(true);
		flgMancataPresaInCaricoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				formNotificheRichieste.redraw();
			}
		});
		
		giorniTrascorsiItem = new NumericItem("giorniTrascorsi", "entro giorni");  					
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
							Record record = new Record(vm.getValues());
							record.setAttribute("listaDestInvioCC", getDestInvioCC());
							
							onClickOkButton(record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									
									_window.markForDestroy();
								}
							});
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
				layout.addMember(legendaPanel);
			} else {
				buildFormLegenda();
				layout.addMember(formLegenda);
			}
		}

		DetailSection detailSectionCondivisione = new DetailSection("Destinatario/i", true, true, true, formCondivisione);
		DetailSection detailSectionOpzioniInvio = null;
		if ("U".equals(flgUdFolder)) {
			detailSectionOpzioniInvio = new DetailSection("Opzioni invio", true, true, false, formOpzioniInvio1, formOpzioniInvio2);			
		} else {
			detailSectionOpzioniInvio = new DetailSection("Opzioni invio", true, true, false, formOpzioniInvio1);			
		}
		DetailSection detailSectionMessaggio = new DetailSection("Messaggio", true, true, false, formMessaggio);
		DetailSection detailSectionNotificheRichieste = new DetailSection("Notifiche richieste", true, true, false, formNotificheRichieste);

		layout.addMember(detailSectionCondivisione);
		if(!isSmistamentoCC()) {
			layout.addMember(detailSectionOpzioniInvio);
		}
		layout.addMember(detailSectionMessaggio);
		layout.addMember(detailSectionNotificheRichieste);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);

		setIcon("archivio/condividi.png");
	}
	
	public RecordList getDestInvioCC() {
		if (getListaPreferiti() != null && !getListaPreferiti().isEmpty()) {							
			RecordList listaDestInvioCC =  formCondivisione.getValueAsRecordList("listaDestInvioCC");
			for(int i=0; i < getListaPreferiti().getLength(); i++){
				Record currentRecord = getListaPreferiti().get(i);
				String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
				String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
				if(formCondivisione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito) != null && (Boolean) formCondivisione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito)){
					Record recordDestInvioCC = new Record();
					recordDestInvioCC.setAttribute("typeNodo",tipoDestinatarioPreferito);
					recordDestInvioCC.setAttribute("idUo", idDestinatarioPreferito);
					listaDestInvioCC.add(recordDestInvioCC);									
				}
			}	
			return listaDestInvioCC;		
		} else {
			return formCondivisione.getValueAsRecordList("listaDestInvioCC");				
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
	
	public boolean validate() {
		if(getListaPreferiti() != null) {
			for(int i=0; i < getListaPreferiti().getLength(); i++){
				Record currentRecord = getListaPreferiti().get(i);
				String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
				String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
				if(formCondivisione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito) != null && (Boolean) formCondivisione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito)){
					return true;									
				}
			}	
		}		
		return condivisioneItem.validate();
	}	

	public void editRecordPerModificaNotifica(Record record) {
		vm.editRecord(record);
		markForRedraw();
	}
	
	public String getFlgTipoProvDoc() {
		return null;
	}
	
	public boolean isSmistamentoCC() {
		return false;
	}
	
	public String getSuffissoFinalitaOrganigramma() {
		String suffissoFinalita = "";
		if(getFlgTipoProvDoc() != null && "E".equals(getFlgTipoProvDoc())) {
			suffissoFinalita = "_ENTRATA";			
		}
		return suffissoFinalita;
	}
	
	public RecordList getListaPreferiti() {
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