/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class OpzioniInvioAssegnazionePopup extends ModalWindow {
		
	protected OpzioniInvioAssegnazionePopup _window;
	
	protected ValuesManager vm;	
	
	protected DynamicForm formOpzioniInvio1;
	protected DynamicForm formOpzioniInvio2;
	protected DynamicForm formMessaggio;
	protected DynamicForm formNotificheRichieste;		
	
	protected SelectItem motivoInvioItem;
	protected SelectItem livelloPrioritaItem;
	
	protected CheckboxItem flgInviaFascicoloItem;
	protected CheckboxItem flgInviaDocCollegatiItem;
	//protected CheckboxItem flgMantieniCopiaUdItem;
	protected CheckboxItem flgMandaNotificaMailItem;

	protected TextAreaItem messaggioInvioItem;
	
	protected CheckboxItem flgPresaInCaricoItem;
	protected CheckboxItem flgMancataPresaInCaricoItem;
	protected NumericItem giorniTrascorsiItem;
	
	protected HStack _buttons;
	
	protected String idUd;	
	protected String flgUdFolder;	
	
	public OpzioniInvioAssegnazionePopup(final boolean flgUo, Record record, Boolean canEdit){
		this(flgUo, null, record, canEdit);
	}
	
	public OpzioniInvioAssegnazionePopup(final boolean flgUo, final String tipoDestinatario, Record record, Boolean canEdit){
		
		super("opzioniInvioAssegnazione", true);

		_window = this;
		
		vm = new ValuesManager();
			
		idUd = getIdUd();
		flgUdFolder = getFlgUdFolder();
		
		setTitle(I18NUtil.getMessages().protocollazione_opzioniInvioAssegnazionePopup_title());
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		formOpzioniInvio1 = new DynamicForm();
		formOpzioniInvio1.setValuesManager(vm);
		formOpzioniInvio1.setKeepInParentRect(true);
		formOpzioniInvio1.setWidth100();
		formOpzioniInvio1.setHeight100();
		formOpzioniInvio1.setNumCols(8);
		formOpzioniInvio1.setColWidths(60, 1, 1, 1, 1, 1, "*", "*");
		formOpzioniInvio1.setCellPadding(5);
		formOpzioniInvio1.setWrapItemTitles(false);
		
		CustomValidator lMotivoInvioPAFValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if (value != null && "PAF".equals(value) && flgUo) {
					return false;					
				}
				return true;
			}
		};
		lMotivoInvioPAFValidator.setErrorMessage("L'assegnazione per firma/approvazione deve essere fatta a persona/e e non ad una U.O.");
		
		CustomValidator lMotivoInvioPAVValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if (value != null && "PAV".equals(value) && flgUo) {
					return false;					
				}
				return true;
			}
		};
		lMotivoInvioPAVValidator.setErrorMessage("L'assegnazione per apposizione visto elettronico deve essere fatta a persona/e e non ad una U.O.");
		
		
		GWTRestDataSource motivoInvioDS = new GWTRestDataSource("LoadComboMotivoInvioDataSource", "key", FieldType.TEXT);
		if ("U".equals(flgUdFolder)) {
			motivoInvioDS.addParam("tableName", "DMT_INVII_UD");
			motivoInvioDS.addParam("idUd", idUd);
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
		motivoInvioItem.setValidators(lMotivoInvioPAFValidator, lMotivoInvioPAVValidator);

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
	
//			flgMantieniCopiaUdItem = new CheckboxItem("flgMantieniCopiaUd", "mantieni copia");
//			flgMantieniCopiaUdItem.setColSpan(1);
//			flgMantieniCopiaUdItem.setWidth(1);
//			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_DEFAULT_MANTIENI_COPIA_IN_ASS_DOC")) {
//				flgMantieniCopiaUdItem.setDefaultValue(true);			
//			}	
			
			if(tipoDestinatario != null && ("LD".equalsIgnoreCase(tipoDestinatario)
					|| "G".equalsIgnoreCase(tipoDestinatario))) {
				formOpzioniInvio2.setFields(new FormItem[]{spacerItem, flgMandaNotificaMailItem});
			} else {
				formOpzioniInvio2.setFields(new FormItem[]{spacerItem, flgInviaFascicoloItem, flgInviaDocCollegatiItem, /*flgMantieniCopiaUdItem,*/ flgMandaNotificaMailItem});
			}
			
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
		formNotificheRichieste.setColWidths(1,1,1,1,"*");
		formNotificheRichieste.setCellPadding(5);
		formNotificheRichieste.setWrapItemTitles(false);
		
		flgPresaInCaricoItem = new CheckboxItem("flgPresaInCarico", I18NUtil.getMessages().protocollazione_opzioniInvioAssegnazionePopup_flgPresaInCaricoItem_title());
		flgPresaInCaricoItem.setColSpan(4);
		flgPresaInCaricoItem.setStartRow(true);
		
		flgMancataPresaInCaricoItem = new CheckboxItem("flgMancataPresaInCarico",   I18NUtil.getMessages().protocollazione_opzioniInvioAssegnazionePopup_flgMancataPresaInCaricoItem_title());
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
		
		formNotificheRichieste.setFields(new FormItem[]{
        	flgPresaInCaricoItem, 
        	flgMancataPresaInCaricoItem,
        	giorniTrascorsiItem
		});

		Button okButton = new Button(I18NUtil.getMessages().protocollazione_detail_okButton_title());
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {	
				if(vm.validate()) {
					final Record record = new Record(vm.getValues());
					if(record != null && record.getAttributeAsBoolean("flgInviaFascicolo") != null &&
							record.getAttributeAsBoolean("flgInviaFascicolo")) {
						SC.ask("Sei sicuro di voler assegnare tutta la documentazione del fascicolo ?", new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								
								if(value) {
									onClickOkButton(new Record(vm.getValues()));
									_window.markForDestroy();
								}
							}
						});
					} else {				
						onClickOkButton(new Record(vm.getValues()));
						_window.markForDestroy();
					}
				}
			}
		});
		
		_buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(okButton);
		
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
		
		DetailSection detailSectionOpzioniInvio = null;
		if (flgUdFolder != null && "U".equals(flgUdFolder)) {
			detailSectionOpzioniInvio = new DetailSection("Opzioni invio", true, true, false, formOpzioniInvio1, formOpzioniInvio2);			
		} else {
			detailSectionOpzioniInvio = new DetailSection("Opzioni invio", true, true, false, formOpzioniInvio1);			
		}
		DetailSection detailSectionMessaggio = new DetailSection("Messaggio", true, true, false, formMessaggio);
		DetailSection detailSectionNotificheRichieste = new DetailSection("Notifiche richieste", true, true, false, formNotificheRichieste);		
		
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

		setIcon("buttons/altriDati.png");
		
		if(record != null) {
			vm.editRecord(record);
		} else {
			vm.editNewRecord();
		}
		setCanEdit(canEdit);
	}
	
	public void setCanEdit(Boolean canEdit) {
		if(canEdit) {
			_buttons.show();
		} else {
			_buttons.hide();
		}
		for(DynamicForm form : vm.getMembers()) {
			setCanEdit(canEdit, form);
		}
	}
	
	public void setCanEdit(boolean canEdit, DynamicForm form) {
		if (form != null) {
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
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

	public abstract String getIdUd();
	public abstract String getFlgUdFolder();
	public abstract void onClickOkButton(Record record);
	
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
