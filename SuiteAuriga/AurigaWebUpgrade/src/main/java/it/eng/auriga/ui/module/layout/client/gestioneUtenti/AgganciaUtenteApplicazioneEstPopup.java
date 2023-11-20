/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.FormItemType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class AgganciaUtenteApplicazioneEstPopup extends ModalWindow {
	
	private AgganciaUtenteApplicazioneEstPopup window;
	
	// DynamicForm
	private DynamicForm formMain;
	
	// SelectItem
	private SelectItemWithDisplay applicazioniEsterneItem;
	private SelectItem uoCollegateUtenteItem;
	
	// TextItem
	private TextItem idUtenteApplEstItem;
	private TextItem usernameUtenteApplEstItem;
	
	// PasswordItem
	private PasswordItem passwordUtenteApplEstItem;
	private PasswordItem confermaPasswordItem;
	
	// ImgButtonItem
	private ImgButtonItem resetPasswordButton;
	
	//HiddenItem 
	private HiddenItem denominazioneApplEstItem;
	private HiddenItem idUoCollegataUtenteItem;
	private HiddenItem descrizioneUoCollegataUtenteItem;
	private HiddenItem flgUsaCredenzialiDiverseAurigaItem;
	
	
	private String mode;
	
	public AgganciaUtenteApplicazioneEstPopup(Record record, String title, String mode){
		
		super("aggancia_utente_applicazione", true);
		
		window = this;
		
		setHeight(300);
		setWidth(800);	
		
		setTitle(title);
		settingsMenu.removeItem(separatorMenuItem);
	    settingsMenu.removeItem(autoSearchMenuItem);
	    
	    setMode(mode);
		
	    disegnaForm();
		
		initForm(record);
				
		Button okButton = new Button("Ok");   
		okButton.setIcon("ok.png");
		okButton.setIconSize(16); 
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				if(formMain.validate()) {
					final Record formRecord = new Record(formMain.getValues());
					
					String password = "";
					String confermaPassword = "";
					if (formRecord.getAttribute("passwordUtenteApplEst")!=null && !formRecord.getAttribute("passwordUtenteApplEst").equalsIgnoreCase(""))
						password = formRecord.getAttributeAsString("passwordUtenteApplEst");
					
					if (formRecord.getAttribute("confermaPassword")!=null && !formRecord.getAttribute("confermaPassword").equalsIgnoreCase(""))
						confermaPassword = formRecord.getAttributeAsString("confermaPassword");
					
					if(password.equalsIgnoreCase(confermaPassword)){
						onClickOkButton(formRecord, new DSCallback() {			
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								
								window.markForDestroy();
							}
						});	
					}
					else{
						SC.warn("La password non corrisponde a quella confermata !");
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
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		layout.addMember(formMain);
		addMember(layout);
		addMember(_buttons);
	}
	
	private void initForm(Record record) {
		
		// id utente applicazione 
		idUtenteApplEstItem.setValue(record.getAttribute("idUtenteApplEst"));
		formMain.setValue("idUtenteApplEst",      record.getAttribute("idUtenteApplEst"));   	
		
		// username
		usernameUtenteApplEstItem.setValue(record.getAttribute("usernameUtenteApplEst"));
		formMain.setValue("usernameUtenteApplEst",      record.getAttribute("usernameUtenteApplEst"));   
		
		// password
		passwordUtenteApplEstItem.setValue(record.getAttribute("passwordUtenteApplEst"));
		formMain.setValue("passwordUtenteApplEst",      record.getAttribute("passwordUtenteApplEst"));   
		
		confermaPasswordItem.setValue(record.getAttribute("passwordUtenteApplEst"));
		formMain.setValue("confermaPassword",      record.getAttribute("passwordUtenteApplEst"));   
		
		// combo APPLICAZIONI ESTERNE
		if (record.getAttribute("codiceApplIstEst") != null && !"".equals(record.getAttributeAsString("codiceApplIstEst")) && record.getAttribute("denominazioneApplEst") != null &&  !record.getAttribute("denominazioneApplEst").equalsIgnoreCase("")  ) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("codiceApplIstEst"), record.getAttribute("denominazioneApplEst"));
			applicazioniEsterneItem.setValueMap(valueMap);
			applicazioniEsterneItem.setValue(record.getAttribute("codiceApplIstEst"));
			formMain.setValue("denominazioneApplEst", record.getAttribute("denominazioneApplEst"));
		}	
		
		if(record.getAttribute("flgUsaCredenzialiDiverseAuriga") != null){
			flgUsaCredenzialiDiverseAurigaItem.setValue(record.getAttribute("flgUsaCredenzialiDiverseAuriga"));
			formMain.setValue("flgUsaCredenzialiDiverseAuriga",      record.getAttribute("flgUsaCredenzialiDiverseAuriga"));   			
		}
			
		// combo UO COLLEGATA ALL'UTENTE
		if (record.getAttribute("idUoCollegataUtente") != null && !"".equals(record.getAttributeAsString("idUoCollegataUtente")) && record.getAttribute("descrizioneUoCollegataUtente") != null &&  !record.getAttribute("descrizioneUoCollegataUtente").equalsIgnoreCase("")  ) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			String idUoCollegataUtente = record.getAttribute("idUoCollegataUtente");
			
			if(!idUoCollegataUtente.startsWith("UO"))
				idUoCollegataUtente = "UO" + idUoCollegataUtente;
			
			valueMap.put(idUoCollegataUtente, record.getAttribute("descrizioneUoCollegataUtente"));
			
			uoCollegateUtenteItem.setValueMap(valueMap);
			uoCollegateUtenteItem.setValue(idUoCollegataUtente);
			formMain.setValue("idUoCollegataUtente", idUoCollegataUtente);
			formMain.setValue("descrizioneUoCollegataUtente", record.getAttribute("descrizioneUoCollegataUtente"));
		}
		
		GWTRestDataSource listaDataDS = (GWTRestDataSource) uoCollegateUtenteItem.getOptionDataSource();
		listaDataDS.addParam("idUser", record.getAttributeAsString("idUser"));
		uoCollegateUtenteItem.setOptionDataSource(listaDataDS);
		uoCollegateUtenteItem.fetchData();
		
		boolean isEditMode = (getMode()!=null && !getMode().equalsIgnoreCase("") && getMode().equalsIgnoreCase("edit"));
		
		if(isEditMode){
			applicazioniEsterneItem.setCanEdit(false);
			passwordUtenteApplEstItem.setCanEdit(false);
			confermaPasswordItem.setVisible(false);
		} 
		formMain.markForRedraw();
	}
	
	private void disegnaForm() {

		formMain = new DynamicForm();
		formMain.setKeepInParentRect(true);
		formMain.setWidth100();
		formMain.setHeight100();
		formMain.setCellPadding(5);
		formMain.setAlign(Alignment.CENTER);
		formMain.setOverflow(Overflow.VISIBLE);
		formMain.setTop(50);
		formMain.setWrapItemTitles(false);
		formMain.setNumCols(7);
		formMain.setColWidths(180, 1, 1, 1, 1, 1, "*");
		formMain.setMostraIconaRequired(true);
		
		// HIDDEN
		denominazioneApplEstItem             = new HiddenItem("denominazioneApplEst");
		idUoCollegataUtenteItem              = new HiddenItem("idUoCollegataUtente");
		descrizioneUoCollegataUtenteItem     = new HiddenItem("descrizioneUoCollegataUtente");
		flgUsaCredenzialiDiverseAurigaItem   = new HiddenItem("flgUsaCredenzialiDiverseAuriga");
		
		// APPLICAZIONI ESTERNE
		applicazioniEsterneItem = createSelectItem("applicazioniEsterne", 
				                                   I18NUtil.getMessages().agganciautenteapplicazioneestpopup_combo_applicazioniEsterne_label(), 
				                                   "LoadComboApplicazioniEsterneUtentiDataSource",
				                                   new String[] { "denominazioneApplEst" }, 
				                                   new String[] { "codiceApplIstEst" , "flgUsaCredenzialiDiverseAuriga"}, 
				                                   new String[] {  "Denominazione" }, 
				                                   new Object[] { 250 }, 
				                                   400, 
				                                   "codiceApplIstEst",
				                                   "denominazioneApplEst", 
				                                   true);
		
		applicazioniEsterneItem.setFilterLocally(false);
		applicazioniEsterneItem.setMultiple(false);		
		applicazioniEsterneItem.setAutoFetchData(true);
		applicazioniEsterneItem.setWrapTitle(false);
		applicazioniEsterneItem.setAllowEmptyValue(true);
		applicazioniEsterneItem.setRedrawOnChange(true);
		applicazioniEsterneItem.setColSpan(6);
		applicazioniEsterneItem.setRequired(true);				
		applicazioniEsterneItem.setStartRow(true);
		
		// ID UTENTE 		
		idUtenteApplEstItem = new TextItem("idUtenteApplEst", I18NUtil.getMessages().agganciautenteapplicazioneestpopup_utenteItem_title());
		idUtenteApplEstItem.setStartRow(true);
		idUtenteApplEstItem.setColSpan(6);
		idUtenteApplEstItem.setWidth(400);
		
		// USERNAME
		usernameUtenteApplEstItem = new TextItem("usernameUtenteApplEst", I18NUtil.getMessages().agganciautenteapplicazioneestpopup_usernameItem_title());
		usernameUtenteApplEstItem.setStartRow(true);
		usernameUtenteApplEstItem.setColSpan(6);
		usernameUtenteApplEstItem.setWidth(400);
		usernameUtenteApplEstItem.setAttribute("obbligatorio", false);		
		usernameUtenteApplEstItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isApplicazioneUsaCredenzialiDiverseDaAuriga();
			}
		}));
		
		// PASSWORD
		passwordUtenteApplEstItem = new PasswordItem("passwordUtenteApplEst", I18NUtil.getMessages().agganciautenteapplicazioneestpopup_passwordItem_title());
		passwordUtenteApplEstItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		passwordUtenteApplEstItem.setColSpan(5);
		passwordUtenteApplEstItem.setWidth(400);
		passwordUtenteApplEstItem.setStartRow(true);		
		passwordUtenteApplEstItem.setAttribute("obbligatorio", false);		
		passwordUtenteApplEstItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isApplicazioneUsaCredenzialiDiverseDaAuriga();
			}
		}));

		
		// Reset password
		resetPasswordButton = new ImgButtonItem("resetPassword", "buttons/reset_pwd.png", "Reset Password");
		resetPasswordButton.setAlwaysEnabled(true);
		resetPasswordButton.setColSpan(1);
		resetPasswordButton.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {
				resetPassword();
			}
		});
		
		resetPasswordButton.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isEditMode = (getMode()!=null && !getMode().equalsIgnoreCase("") && getMode().equalsIgnoreCase("edit"));
				return (isEditMode);
			}
		});
		
		// CONFERMA PASSWORD
		confermaPasswordItem = new PasswordItem("confermaPassword", I18NUtil.getMessages().agganciautenteapplicazioneestpopup_confermaPasswordItem_title());
		confermaPasswordItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		confermaPasswordItem.setColSpan(6);
		confermaPasswordItem.setWidth(400);
		confermaPasswordItem.setStartRow(true);		
		confermaPasswordItem.setAttribute("obbligatorio", false);		
		confermaPasswordItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isApplicazioneUsaCredenzialiDiverseDaAuriga();
			}
		}));
		
		// UO COLLEGATE ALL'UTENTE
		uoCollegateUtenteItem = new SelectItem("uoCollegateUtente" ,I18NUtil.getMessages().agganciautenteapplicazioneestpopup_combo_uoCollegateUtente_label()){
			@Override
			public void onOptionClick(Record record) {
				
				formMain.setValue("idUoCollegataUtente", record.getAttributeAsString("idUo"));
				formMain.setValue("descrizioneUoCollegataUtente", record.getAttributeAsString("descrizione"));
			}
        };
        uoCollegateUtenteItem.setMultiple(false);		
        uoCollegateUtenteItem.setOptionDataSource(new GWTRestDataSource("UoCollegateUtenteApplicazioneEstDatasource", "idUo", FieldType.TEXT));  
        uoCollegateUtenteItem.setAutoFetchData(false);
        uoCollegateUtenteItem.setDisplayField("descrizione");
        uoCollegateUtenteItem.setValueField("idUo");			
        uoCollegateUtenteItem.setWrapTitle(false);
        uoCollegateUtenteItem.setAllowEmptyValue(true);
        uoCollegateUtenteItem.setClearable(true);
        uoCollegateUtenteItem.setAllowEmptyValue(true);
        uoCollegateUtenteItem.setRedrawOnChange(true);
        uoCollegateUtenteItem.setWidth(400); 
        uoCollegateUtenteItem.setColSpan(6);				
        uoCollegateUtenteItem.setStartRow(true);
		
		formMain.setFields( applicazioniEsterneItem,
				            idUtenteApplEstItem, 
							usernameUtenteApplEstItem,
							passwordUtenteApplEstItem, resetPasswordButton,
							confermaPasswordItem,
							uoCollegateUtenteItem,
							denominazioneApplEstItem,
							idUoCollegataUtenteItem,
							descrizioneUoCollegataUtenteItem,
							flgUsaCredenzialiDiverseAurigaItem
				           );
	}
	
	private void resetPassword() {
		passwordUtenteApplEstItem.setCanEdit(true);
		passwordUtenteApplEstItem.redraw();
		confermaPasswordItem.setVisible(true);
		confermaPasswordItem.redraw();
	}
	
	public abstract void onClickOkButton(Record formRecord, DSCallback callback);

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}		
	
	private SelectItemWithDisplay createSelectItem(String name, 
			                                       String title, 
			                                       String datasourceName, 
			                                       String[] campiVisibili, 
			                                       String[] campiHidden,
			                                       String[] descrizioni, 
			                                       Object[] width, 
			                                       int widthTotale, 
			                                       String idField, 
			                                       String displayField, 
			                                       boolean isClearable) {
		
		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource(datasourceName, idField, FieldType.TEXT, campiVisibili, true);
		SelectItemWithDisplay lSelectItemWithDisplay = new SelectItemWithDisplay(name, title, lGwtRestDataSource) {

			@Override
			public void onOptionClick(Record record) {
				manageOnOptionClick(getName(), record);
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				manageClearSelect(getName());
			}
		};
		int i = 0;
		List<ListGridField> lList = new ArrayList<ListGridField>();
		for (String lString : campiVisibili) {
			ListGridField field = new ListGridField(lString, descrizioni[i]);
			if (width[i] instanceof String) {
				field.setWidth((String) width[i]);
			} else
				field.setWidth((Integer) width[i]);

			i++;
			lList.add(field);
		}
		for (String lString : campiHidden) {
			ListGridField field = new ListGridField(lString, lString);
			field.setHidden(true);
			lList.add(field);
		}
		lSelectItemWithDisplay.setPickListFields(lList.toArray(new ListGridField[] {}));
		lSelectItemWithDisplay.setFilterLocally(true);
		lSelectItemWithDisplay.setValueField(idField);
		lSelectItemWithDisplay.setOptionDataSource(lGwtRestDataSource);
		lSelectItemWithDisplay.setWidth(widthTotale);
		lSelectItemWithDisplay.setRequired(false);
		lSelectItemWithDisplay.setClearable(isClearable);
		lSelectItemWithDisplay.setShowIcons(isClearable);
		lSelectItemWithDisplay.setCachePickListResults(false);
		lSelectItemWithDisplay.setDisplayField(displayField);
		return lSelectItemWithDisplay;
	}

	private void manageOnOptionClick(String name, Record record) {
		if (name.equals("applicazioniEsterne")) {
			String codiceApplIstEst = record.getAttributeAsString("codiceApplIstEst");
			applicazioniEsterneItem.setValue(record.getAttribute("codiceApplIstEst"));
			formMain.setValue("applicazioniEsterne", codiceApplIstEst);		
			flgUsaCredenzialiDiverseAurigaItem.setValue(record.getAttribute("flgUsaCredenzialiDiverseAuriga"));
			formMain.setValue("flgUsaCredenzialiDiverseAuriga",      record.getAttributeAsBoolean("flgUsaCredenzialiDiverseAuriga"));
			formMain.setValue("denominazioneApplEst", record.getAttributeAsString("denominazioneApplEst"));
			formMain.markForRedraw();
		}
	}
	
	private void manageClearSelect(String name) {
		if (name.equals("applicazioniEsterne")) {
			formMain.setValue("applicazioniEsterne", (String)null);
			formMain.setValue("flgUsaCredenzialiDiverseAuriga", (String)null);
			formMain.setValue("denominazioneApplEst", (String)null);
			applicazioniEsterneItem.setValue((String)null);
			flgUsaCredenzialiDiverseAurigaItem.setValue(false);
			denominazioneApplEstItem.setValue((String)null);
			applicazioniEsterneItem.clearValue();
			formMain.markForRedraw();
		}
	}
		
	public boolean isApplicazioneUsaCredenzialiDiverseDaAuriga(){
		if (formMain.getValue("flgUsaCredenzialiDiverseAuriga")!=null && !formMain.getValueAsString("flgUsaCredenzialiDiverseAuriga").equalsIgnoreCase("")){
			if (formMain.getValueAsString("flgUsaCredenzialiDiverseAuriga").equalsIgnoreCase("true")) 
				return true;
		}
		return false;
	}
}