/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupSoggettiPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public class SelezionaUtenteItems extends ArrayList<FormItem> {
	
	private static final long serialVersionUID = 1L;
	
	private String idSoggettoHiddenItemName;
	private String usernameSoggettoHiddenItemName;
	private String codRapidoSoggettoItemName;
	private String cognomeSoggettoItemName;
	private String nomeSoggettoItemName;
	private String codiceFiscaleSoggettoItemName;
	private String emailSoggettoItemName;
	private String telefonoSoggettoItemName;
	
	private DynamicForm mDynamicForm;
		
	private HiddenItem idSoggettoHiddenItem;
	private HiddenItem usernameSoggettoHiddenItem;
	private ExtendedTextItem codRapidoSoggettoItem;
	private ExtendedTextItem cognomeSoggettoItem;
	private ExtendedTextItem nomeSoggettoItem;
	private ExtendedTextItem codiceFiscaleSoggettoItem;
	private ExtendedTextItem emailSoggettoItem;
	private ExtendedTextItem telefonoSoggettoItem;

	public SelezionaUtenteItems(DynamicForm mDynamicForm, String idSoggettoHiddenItemName, String usernameSoggettoHiddenItemName, String codRapidoSoggettoItemName,
			String cognomeSoggettoItemName, String nomeSoggettoItemName, String codiceFiscaleSoggettoItemName,
			String emailSoggettoItemName, String telefonoSoggettoItemName) {

		this.mDynamicForm = mDynamicForm;
		this.idSoggettoHiddenItemName = idSoggettoHiddenItemName;
		this.usernameSoggettoHiddenItemName = usernameSoggettoHiddenItemName;
		this.codRapidoSoggettoItemName = codRapidoSoggettoItemName;
		this.cognomeSoggettoItemName = cognomeSoggettoItemName;
		this.nomeSoggettoItemName = nomeSoggettoItemName;
		this.codiceFiscaleSoggettoItemName = codiceFiscaleSoggettoItemName;
		this.emailSoggettoItemName = emailSoggettoItemName;
		this.telefonoSoggettoItemName = telefonoSoggettoItemName;
		
		buildItems();
	}

	private void buildItems() {	
		
		clear();
				
		idSoggettoHiddenItem = new HiddenItem(idSoggettoHiddenItemName);
		usernameSoggettoHiddenItem = new HiddenItem(usernameSoggettoHiddenItemName);
		
		// Codice rapido
		
		codRapidoSoggettoItem = new ExtendedTextItem(codRapidoSoggettoItemName, I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoSoggettoItem.setWidth(120);
		codRapidoSoggettoItem.setColSpan(1);
		
		codRapidoSoggettoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return codRapidoItemShowIfCondition(item, value, form);
			}
		});
		
		codRapidoSoggettoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return codRapidotemRequiredIfValidator(formItem, value);
			}
		}));
		
		codRapidoSoggettoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				codRapidoItemOnChangedBlurHandler(event);
			}
		});
		
		// Bottone seleziona da rubrica
		
		ImgButtonItem lookupRubricaButton = new ImgButtonItem("lookupRubricaButton", "lookup/rubrica.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupRubricaButton_prompt());
		lookupRubricaButton.setColSpan(1);
		lookupRubricaButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return lookupRubricaButtonShowIfCondition(item, value, form);
			}
		});
		
		lookupRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				lookupRubricaButtonIconClick(event);
			}
		});
		
		// Bottone seleziona da organigramma
		
		ImgButtonItem lookupOrganigrammaButton = new ImgButtonItem("lookupOrganigrammaButton", "lookup/organigramma.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaButton.setColSpan(1);
		lookupOrganigrammaButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return lookupOrganigrammaButtonShowIfCondition(item, value, form);
			}
		});
		
		lookupOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				lookupOrganigrammaButtonIconClick(event);
			}
		});
		
		// Cognome
		
		cognomeSoggettoItem = new ExtendedTextItem(cognomeSoggettoItemName, I18NUtil.getMessages().protocollazione_detail_cognomeItem_title());
		cognomeSoggettoItem.setWidth(125);
		cognomeSoggettoItem.setAttribute("obbligatorio", false);
		
		cognomeSoggettoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return cognomeItemShowIfCondition(item, value, form);
			}
		});
		
		cognomeSoggettoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return cognomeItemRequiredIfValidator(formItem, value);
			}
		}));
		
		cognomeSoggettoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				cognomeItemOnChangedBlurHandler(event);
			}
		});

		// Nome
		
		nomeSoggettoItem = new ExtendedTextItem(nomeSoggettoItemName, I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeSoggettoItem.setWidth(125);
		nomeSoggettoItem.setAttribute("obbligatorio", false);
		
		nomeSoggettoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return nomeItemShowIfCondition(item, value, form);
			}
		});
		
		nomeSoggettoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return nomeItemRequiredIfValidator(formItem, value);
			}
		}));
		
		nomeSoggettoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				nomeItemOnChangedBlurHandler(event);
			}
		});
		
		// Bottone utente trovato
		
		ImgButtonItem utenteTrovatoButton = new ImgButtonItem("utenteTrovato", "ok.png", "Utente censito nel sistema");
		utenteTrovatoButton.setAlwaysEnabled(true);
		utenteTrovatoButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return utenteTrovatoButtonShowIfCondition(item, value, form);
			}
		});
		
		// Codice fiscale
		
		codiceFiscaleSoggettoItem = new ExtendedTextItem(codiceFiscaleSoggettoItemName, "C.F.");
		codiceFiscaleSoggettoItem.setWidth(125);
		codiceFiscaleSoggettoItem.setLength(16);
		codiceFiscaleSoggettoItem.setCharacterCasing(CharacterCasing.UPPER);
		codiceFiscaleSoggettoItem.setAttribute("obbligatorio", false);
		
		codiceFiscaleSoggettoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return codiceFiscaleItemShowIfCondition(item, value, form);
			}
		});
		
		codiceFiscaleSoggettoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return codiceFiscaleItemRequiredIfValidator(formItem, value);
			}
		}));
		
		codiceFiscaleSoggettoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				codiceFiscaleItemOnChangedBlurHandler(event);
			}
		});
		
		// Cerca in rubrica
		
		ImgButtonItem cercaInRubricaButton = new ImgButtonItem("cercaInRubricaButton", "lookup/rubricasearch.png", I18NUtil.getMessages()
				.protocollazione_detail_cercaInRubricaButton_prompt());
		cercaInRubricaButton.setColSpan(1);
		
		cercaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return cercaInRubricaButtonShowIfCondition(item, value, form);
			}
		});

		cercaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				cercaInRubricaButtonIconClick(event);
			}
		});
		
		// Cerca in rubrica
		
		ImgButtonItem cercaInOrganigramma = new ImgButtonItem("cercaInOrganigrammaButton", "lookup/organigrammasearch.png", I18NUtil.getMessages()
				.protocollazione_detail_cercaInOrganigrammaButton_prompt());
		cercaInOrganigramma.setColSpan(1);
		
		cercaInOrganigramma.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return cercaInOrganigrammaButtonShowIfCondition(item, value, form);
			}
		});

		cercaInOrganigramma.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				cercaInOrganigrammaButtonIconClick(event);
			}
		});
		
		// Email
		
		emailSoggettoItem = new ExtendedTextItem(emailSoggettoItemName, "e-mail");
		emailSoggettoItem.setWidth(240);
		emailSoggettoItem.setAttribute("obbligatorio", false);
		
		emailSoggettoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return emailItemShowIfCondition(item, value, form);
			}
		});
		
		emailSoggettoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return emailItemRequiredIfValidator(formItem, value);
			}
		}));
		
		emailSoggettoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				emailItemItemOnChangedBlurHandler(event);
			}
		});
				
		// Telefono
		
		telefonoSoggettoItem = new ExtendedTextItem(telefonoSoggettoItemName, "Tel.");
		telefonoSoggettoItem.setWidth(160);
		telefonoSoggettoItem.setAttribute("obbligatorio", false);
		
		telefonoSoggettoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return telefonoItemShowIfCondition(item, value, form);
			}
		});
		
		telefonoSoggettoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return telefonoItemRequiredIfValidator(formItem, value);
			}
		}));
		
		telefonoSoggettoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				telefonoItemItemOnChangedBlurHandler(event);
			}
		});
		
		// Aggiungo i bottoni
		
		add(idSoggettoHiddenItem);
		add(usernameSoggettoHiddenItem);
		add(codRapidoSoggettoItem);
		add(lookupRubricaButton);
		add(lookupOrganigrammaButton);
		add(cognomeSoggettoItem);
		add(nomeSoggettoItem);
		add(utenteTrovatoButton);
		add(codiceFiscaleSoggettoItem);
		add(cercaInRubricaButton);
		add(cercaInOrganigramma);
		add(emailSoggettoItem);
		add(telefonoSoggettoItem);
	}
	
	/************************************************************************************
	 ********************* Gestione del componente codRapidoItem ************************
	 ************************************************************************************/
	
	protected boolean codRapidoItemShowIfCondition(FormItem item, Object value, DynamicForm form){
		return true;
	}
	
	protected boolean codRapidotemRequiredIfValidator(FormItem formItem, Object value){
		return false;
	}
	
	protected void codRapidoItemOnChangedBlurHandler(ChangedEvent event){
		pulisciCampiForm();
		if (event.getValue() != null && !"".equals(event.getValue())) {
			Record lRecord = new Record();
			lRecord.setAttribute("codRapidoSoggetto", event.getValue());
			lRecord.setAttribute("codTipoSoggetto", "UP");
			cercaSoggetto(lRecord, new CercaSoggettoServiceCallback() {

				@Override
				public void executeOnError() {
					mDynamicForm.setFieldErrors(codRapidoSoggettoItem.getName(), "Soggetto non presente in rubrica, o di tipo non ammesso");
				}
			});
		}
	}
	
	/************************************************************************************
	 *********************** Gestione del componente lookupRubricaButton ****************
	 ************************************************************************************/
	
	protected boolean lookupRubricaButtonShowIfCondition(FormItem item, Object value, DynamicForm form){
		return true;
	}
	
	protected void lookupRubricaButtonIconClick(IconClickEvent event){
		MittenteLookupRubrica lookupRubricaPopup = new MittenteLookupRubrica(null, "UP");
		lookupRubricaPopup.show();
	}
	
	/************************************************************************************
	 *********************** Gestione del componente lookupOrganigrammaButton ****************
	 ************************************************************************************/
	
	protected boolean lookupOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form){
		return true;
	}
	
	protected void lookupOrganigrammaButtonIconClick(IconClickEvent event){
		SelezionaUtenteLookupOrganigramma lookupUtenteLookupOrganigramma = new SelezionaUtenteLookupOrganigramma(null);
		lookupUtenteLookupOrganigramma.show();
	}
	
	/************************************************************************************
	 ********************** Gestione del componente cognomeItem *************************
	 ************************************************************************************/
	
	protected boolean cognomeItemShowIfCondition(FormItem item, Object value, DynamicForm form){
		return true;
	}
	
	protected boolean cognomeItemRequiredIfValidator(FormItem formItem, Object value){
		String cognome = cognomeSoggettoItem.getValueAsString();
		String nome = nomeSoggettoItem.getValueAsString();
		return (((cognome != null) && (!"".equalsIgnoreCase(cognome.trim()))) || ((nome != null) && (!"".equalsIgnoreCase(nome.trim()))));
	}
	
	protected void cognomeItemOnChangedBlurHandler(ChangedEvent event){
		settaSoggettoNonTrovato();
	}
	
	
	/************************************************************************************
	 *********************** Gestione del componente nomeItem ***************************
	 ************************************************************************************/
	
	protected boolean nomeItemShowIfCondition(FormItem item, Object value, DynamicForm form){
		return true;
	}
	
	protected boolean nomeItemRequiredIfValidator(FormItem formItem, Object value){
		String cognome = cognomeSoggettoItem.getValueAsString();
		String nome = nomeSoggettoItem.getValueAsString();
		return (((cognome != null) && (!"".equalsIgnoreCase(cognome.trim()))) || ((nome != null) && (!"".equalsIgnoreCase(nome.trim()))));
	}
	
	protected void nomeItemOnChangedBlurHandler(ChangedEvent event){
		settaSoggettoNonTrovato();
	}
	
	/************************************************************************************
	 *********************** Gestione del componente utenteTrovatoButton ***************************
	 ************************************************************************************/
	
	protected boolean utenteTrovatoButtonShowIfCondition(FormItem item, Object value, DynamicForm form){
		String idUtente = mDynamicForm.getValueAsString(idSoggettoHiddenItemName);
		return (idUtente != null) && (!"".equalsIgnoreCase(idUtente.trim()));
	}
	
	/************************************************************************************
	 ********************** Gestione del componente cercaInRubricaButton*****************
	 ************************************************************************************/
	
	protected boolean cercaInRubricaButtonShowIfCondition(FormItem item, Object value, DynamicForm form){
		return true;
	}
	
	protected void cercaInRubricaButtonIconClick(IconClickEvent event){
		cercaInRubrica();
	}
	
	/************************************************************************************
	 ********************** Gestione del componente cercaInOrganigrammaButton*****************
	 ************************************************************************************/
	
	protected boolean cercaInOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form){
		return true;
	}
	
	protected void cercaInOrganigrammaButtonIconClick(IconClickEvent event){
		cercaInOrganigramma();
	}
	
	/************************************************************************************
	 ******************* Gestione del componente codiceFiscaleItem **********************
	 ************************************************************************************/
	
	protected boolean codiceFiscaleItemShowIfCondition(FormItem item, Object value, DynamicForm form){
		return true;
	}
	
	protected boolean codiceFiscaleItemRequiredIfValidator(FormItem formItem, Object value){
		return false;
	}
	
	protected void codiceFiscaleItemOnChangedBlurHandler(ChangedEvent event){
		
	}
	
	/************************************************************************************
	 ********************** Gestione del componente emailItem ***************************
	 ************************************************************************************/
	
	protected boolean emailItemShowIfCondition(FormItem item, Object value, DynamicForm form){
		return true;
	}
	
	protected boolean emailItemRequiredIfValidator(FormItem formItem, Object value){
		return false;
	}
	
	protected void emailItemItemOnChangedBlurHandler(ChangedEvent event){
		
	}
	
	/************************************************************************************
	 ********************* Gestione del componente telefonoItem *************************
	 ************************************************************************************/
	
	protected boolean telefonoItemShowIfCondition(FormItem item, Object value, DynamicForm form){
		return true;
	}
	
	protected boolean telefonoItemRequiredIfValidator(FormItem formItem, Object value){
		return false;
	}
	
	protected void telefonoItemItemOnChangedBlurHandler(ChangedEvent event){
		
	}
	
	/************************************************************************************
	 ************************************** Altro ***************************************
	 ************************************************************************************/
	
	public void setCanEdit(boolean canEdit) {
		for(FormItem item : this) {
			item.setCanEdit(canEdit);
			item.redraw();
		}
	}
	
	public FormItem getFormItem(String nameItem){
		if (mDynamicForm.getItem(nameItem) != null){
			return (FormItem) mDynamicForm.getItem(nameItem);
		} else {
			return null;
		}
	}
	
	
	protected void settaSoggettoNonTrovato(){
		mDynamicForm.setValue(idSoggettoHiddenItem.getName(), "");
		mDynamicForm.setValue(usernameSoggettoHiddenItem.getName(), "");
		mDynamicForm.setValue(codRapidoSoggettoItem.getName(), "");
	}
	
	protected void pulisciCampiForm(){
		mDynamicForm.setValue(idSoggettoHiddenItem.getName(), "");
		mDynamicForm.setValue(usernameSoggettoHiddenItem.getName(), "");
		mDynamicForm.setValue(codRapidoSoggettoItem.getName(), "");
		mDynamicForm.setValue(cognomeSoggettoItem.getName(), "");
		mDynamicForm.setValue(nomeSoggettoItem.getName(), "");
	}
	
	protected void cercaInRubrica(){
		String cognome = cognomeSoggettoItem.getValueAsString();
		String nome = nomeSoggettoItem.getValueAsString();
		if ((cognome != null) && (!"".equalsIgnoreCase(cognome.trim())) || (nome != null) && (!"".equalsIgnoreCase(nome.trim()))){
			final Record lRecord = new Record();
			lRecord.setAttribute("cognomeSoggetto", cognome);
			lRecord.setAttribute("nomeSoggetto", nome);
			lRecord.setAttribute("codTipoSoggetto", "UP");
			cercaSoggetto(lRecord, new CercaSoggettoServiceCallback() {
	
				@Override
				public void executeOnError() {
					MittenteLookupRubrica lookupRubricaPopup = new MittenteLookupRubrica(lRecord, "UP");
					lookupRubricaPopup.show();
				}
				
				@Override
				public void execute(Record object) {
					super.execute(object);
				}
			});
		}
	}
	
	protected void cercaInOrganigramma(){
		final Record lRecord = new Record();
		lRecord.setAttribute("denominazioneSoggetto", usernameSoggettoHiddenItem.isVisible() ? usernameSoggettoHiddenItem.getValue() : null);
		lRecord.setAttribute("cognomeSoggetto",cognomeSoggettoItem.isVisible() ? cognomeSoggettoItem.getValueAsString() : null);
		lRecord.setAttribute("nomeSoggetto", nomeSoggettoItem.isVisible() ? nomeSoggettoItem.getValueAsString() : null);
		lRecord.setAttribute("codfiscaleSoggetto", codiceFiscaleSoggettoItem.isVisible() ? codiceFiscaleSoggettoItem.getValue() : null);
		lRecord.setAttribute("codTipoSoggetto", "UP");
		lRecord.setAttribute("flgInOrganigramma", "UT");
		cercaSoggetto(lRecord, new CercaSoggettoInOrganigrammaServiceCallback() {

			@Override
			public void executeOnError() {
				SelezionaUtenteLookupOrganigramma lookupUtenteLookupOrganigramma = new SelezionaUtenteLookupOrganigramma(lRecord);
				lookupUtenteLookupOrganigramma.show();
			}
	
			@Override
			public void execute(Record object) {
				super.execute(object);
			}
		});
	}
	
	protected void cercaSoggetto(Record lRecord, ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindSoggettoDatasource");
		lGwtRestService.call(lRecord, callback);
	}
	
	public abstract class CercaSoggettoServiceCallback extends ServiceCallback<Record> {

		public abstract void executeOnError();
		
		@Override
		public void manageError(Exception lException) {
			super.manageError(lException);
		}

		@Override
		public void execute(Record object) {
			if ((object.getAttribute("trovatiSoggMultipliInRicerca") != null) && (object.getAttributeAsBoolean("trovatiSoggMultipliInRicerca"))){
				// Ho trovato soggetti multipli
				object.setAttribute("cognomeSoggetto", cognomeSoggettoItem.getValueAsString());
				object.setAttribute("nomeSoggetto", nomeSoggettoItem.getValueAsString());
				MittenteLookupRubrica lookupRubricaPopup = new MittenteLookupRubrica(object, "UP");
				lookupRubricaPopup.show();
			} else {
				mDynamicForm.clearErrors(true);
				if (object.getAttribute("idUserSoggetto") != null && !"".equalsIgnoreCase(object.getAttribute("idUserSoggetto"))){
					pulisciCampiForm();
					if (object.getAttribute("idUserSoggetto") != null && !object.getAttribute("idUserSoggetto").equalsIgnoreCase("")) {
						mDynamicForm.setValue(idSoggettoHiddenItem.getName(), object.getAttribute("idUserSoggetto"));
					}
					if (object.getAttribute("usernameSoggetto") != null && !object.getAttribute("usernameSoggetto").equalsIgnoreCase("")) {
						mDynamicForm.setValue(usernameSoggettoHiddenItem.getName(), object.getAttribute("usernameSoggetto"));
					}
					if (object.getAttribute("codRapidoSoggetto") != null && !object.getAttribute("codRapidoSoggetto").equalsIgnoreCase("")) {
						mDynamicForm.setValue(codRapidoSoggettoItem.getName(), object.getAttribute("codRapidoSoggetto"));
					}
					if (object.getAttribute("cognomeSoggetto") != null && !object.getAttribute("cognomeSoggetto").equalsIgnoreCase("")) {
						mDynamicForm.setValue(cognomeSoggettoItem.getName(), object.getAttribute("cognomeSoggetto"));
					}
					if (object.getAttribute("nomeSoggetto") != null && !object.getAttribute("nomeSoggetto").equalsIgnoreCase("")) {
						mDynamicForm.setValue(nomeSoggettoItem.getName(), object.getAttribute("nomeSoggetto"));
					}
				}
			}
		}
	}
	
	public void setFormValuesFromRecordRubrica(Record record) {
		SC.echo(record.getJsObj());
		// Pulisco i dati del soggetto
		pulisciCampiForm();
		mDynamicForm.clearErrors(true);
		mDynamicForm.setValue(idSoggettoHiddenItem.getName(), record.getAttribute("idUtente"));
		mDynamicForm.setValue(usernameSoggettoHiddenItem.getName(), record.getAttribute("username"));
		mDynamicForm.setValue(cognomeSoggettoItem.getName(), record.getAttribute("cognome"));
		mDynamicForm.setValue(nomeSoggettoItem.getName(), record.getAttribute("nome"));
	}
	
	public class MittenteLookupRubrica extends LookupSoggettiPopup {

		public MittenteLookupRubrica(Record record, String tipoMittente) {
			super(record, tipoMittente, true);
		}

		@Override
		public String getFinalita() {
			return "SEL_UP";
		}
		
		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordRubrica(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}

		@Override
		public String[] getTipiAmmessi() {
			return new String[] {"UP"};
		}
	}
	
	/**
	 * FUNZIONALITA' PER LA RICERCA IN ORGANIGRAMMA
	 */
			
	public class SelezionaUtenteLookupOrganigramma extends LookupOrganigrammaPopup {

		public SelezionaUtenteLookupOrganigramma(Record record) {
			super(record, true);
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordOrganigramma(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
		
		@Override
		public String getFinalita() {
			
			return "SEL_UP";
		}
		
	}	
	
	public abstract class CercaSoggettoInOrganigrammaServiceCallback extends ServiceCallback<Record> {

		public abstract void executeOnError();
		
		@Override
		public void manageError(Exception lException) {
			super.manageError(lException);
		}


		@Override
		public void execute(Record object) {
			mDynamicForm.clearErrors(true);

			if ((object.getAttribute("trovatiSoggMultipliInRicerca") != null) && (object.getAttributeAsBoolean("trovatiSoggMultipliInRicerca"))){
				// Ho trovato soggetti multipli
				object.setAttribute("cognomeSoggetto", cognomeSoggettoItem.getValueAsString());
				object.setAttribute("nomeSoggetto", nomeSoggettoItem.getValueAsString());
				SelezionaUtenteLookupOrganigramma lookupUtenteLookupOrganigramma = new SelezionaUtenteLookupOrganigramma(object);
				lookupUtenteLookupOrganigramma.show();
			} else {
				mDynamicForm.clearErrors(true);
				if (object.getAttribute("idSoggetto") != null && !"".equalsIgnoreCase(object.getAttribute("idSoggetto"))){
					pulisciCampiForm();
					if (object.getAttribute("idSoggetto") != null && !object.getAttribute("idSoggetto").equalsIgnoreCase("")) {
						mDynamicForm.setValue(idSoggettoHiddenItem.getName(), object.getAttribute("idSoggetto"));
					}
					if (object.getAttribute("usernameSogetto") != null && !object.getAttribute("usernameSogetto").equalsIgnoreCase("")) {
						mDynamicForm.setValue(usernameSoggettoHiddenItem.getName(), object.getAttribute("usernameSogetto"));
					}
					if (object.getAttribute("codRapidoSoggetto") != null && !object.getAttribute("codRapidoSoggetto").equalsIgnoreCase("")) {
						mDynamicForm.setValue(codRapidoSoggettoItem.getName(), object.getAttribute("codRapidoSoggetto"));
					}
					if (object.getAttribute("cognomeSoggetto") != null && !object.getAttribute("cognomeSoggetto").equalsIgnoreCase("")) {
						mDynamicForm.setValue(cognomeSoggettoItem.getName(), object.getAttribute("cognomeSoggetto"));
					}
					if (object.getAttribute("nomeSoggetto") != null && !object.getAttribute("nomeSoggetto").equalsIgnoreCase("")) {
						mDynamicForm.setValue(nomeSoggettoItem.getName(), object.getAttribute("nomeSoggetto"));
					}
				}
			}
		}
	}
	
	public void setFormValuesFromRecordOrganigramma(Record record) {
		SC.echo(record.getJsObj());
		// Pulisco i dati del soggetto
		clearIdSoggetto();
		clearFormSoggettoValues();
		mDynamicForm.clearErrors(true);
	
		mDynamicForm.setValue(usernameSoggettoHiddenItem.getName(),  record.getAttribute("username"));
		mDynamicForm.setValue(codiceFiscaleSoggettoItem.getName(), record.getAttribute("codFiscale"));
		mDynamicForm.setValue(idSoggettoHiddenItem.getName(), record.getAttribute("idUser"));
		
		String tipo = record.getAttribute("tipo");
		if (tipo.startsWith("UO")) {
			mDynamicForm.setValue(codRapidoSoggettoItem.getName(), record.getAttribute("codRapidoUo"));
		}else{
			mDynamicForm.setValue(codRapidoSoggettoItem.getName(), record.getAttribute("codRapidoSvUt"));
			String cognomeNome = record.getAttribute("descrUoSvUt");
			if (cognomeNome != null && !"".equals(cognomeNome)) {
				String[] tokens = new StringSplitterClient(cognomeNome, "|").getTokens();
				if (tokens.length == 2) {
					mDynamicForm.setValue(cognomeSoggettoItem.getName(),  tokens[0].trim());
					mDynamicForm.setValue(nomeSoggettoItem.getName(), tokens[1].trim());
				}
			}
		}

		mDynamicForm.markForRedraw();
	}
	
	protected void clearIdSoggetto() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("codRapidoMittente", "");
		lRecord.setAttribute("idSoggetto", "");
		lRecord.setAttribute("idUoSoggetto", "");
		lRecord.setAttribute("idUserSoggetto", "");
		lRecord.setAttribute("idScrivaniaSoggetto", "");
		mDynamicForm.setValues(lRecord.toMap());
	}
	
	protected void clearFormSoggettoValues() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("codRapidoMittente", "");
		lRecord.setAttribute("denominazioneMittente", "");
		lRecord.setAttribute("cognomeMittente", "");
		lRecord.setAttribute("nomeMittente", "");
		lRecord.setAttribute("codfiscaleMittente", "");
		mDynamicForm.setValues(lRecord.toMap());
		
	}

}
