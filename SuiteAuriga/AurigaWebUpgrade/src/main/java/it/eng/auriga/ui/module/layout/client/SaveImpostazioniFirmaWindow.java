/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class SaveImpostazioniFirmaWindow extends ModalWindow {

	private static final String TIPO_FIRMA_CLIENT = "C";
	private static final String TIPO_FIRMA_REMOTA =  "R";
	private static final String TIPO_FIRMA_REMOTA_WS =  "W";
	private static final String TIPO_FIRMA_REMOTA_AUTOMATICA = "A";
	private static final String TIPO_FIRMA_REMOTA_SOLO_PIN = "RSP";
	
	private String pwdDB = null;
//	private String pwdDBRichOtp = null;

	private HiddenItem attivaFirmaInDelegaItem;
	private HiddenItem canSendOtpViaCallItem;
	private HiddenItem canSendOtpViaSmsItem;
	
	private ValuesManager vm;
	private DynamicForm mDynamicForm;
	private SelectItem tipoFirmaSelectItem;
	private SelectItem providerFirmaRemotaItem;
	private TextItem userIdItem;
	
	private TextItem firmaInDelegaItem;
	/* Item spostati in SaveCredenzialiFirmaAutomaticaWindow
	private FormItem passwordItem;
	private FormItem confermaPasswordItem;
	private ImgButtonItem cambiaPasswordButton;
	*/
	
	private TextItem usernameRichOtpItem;
//	private FormItem passwordRichOtpItem;
//	private FormItem confermaPasswordRichOtpItem;
//	private ImgButtonItem cambiaPasswordRichOtpButton;
	
	private CheckboxItem showSendOtpViaSmsItem;
	private CheckboxItem showSendOtpViaCallItem;
	
	private Button saveButton;
	private Map providerValueMap;

	public SaveImpostazioniFirmaWindow(final Map providerValueMap) {
		
		super("config_utente_impostazioniFirma", true);

		setTitle(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_title());
		setIcon("file/mini_sign.png");

		this.vm = new ValuesManager();
		
		setAutoCenter(true);
		setWidth(600);
		setHeight(200);

		this.providerValueMap = providerValueMap;
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		mDynamicForm = new DynamicForm();
		mDynamicForm.setKeepInParentRect(true);
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setWidth100();
		mDynamicForm.setHeight100();
		mDynamicForm.setNumCols(5);
		mDynamicForm.setColWidths(10, 10, 10, 10, "*");
		mDynamicForm.setCellPadding(7);
		mDynamicForm.setCanSubmit(true);
		mDynamicForm.setAlign(Alignment.LEFT);
		mDynamicForm.setTop(50);
		mDynamicForm.setValuesManager(vm);
		
		attivaFirmaInDelegaItem = new HiddenItem("attivaFirmaInDelega");
		canSendOtpViaSmsItem = new HiddenItem("canSendOtpViaSms");
		canSendOtpViaCallItem = new HiddenItem("canSendOtpViaCall");

		LinkedHashMap<String, String> tipoFirmaMap = new LinkedHashMap<String, String>();
		tipoFirmaMap.put(TIPO_FIRMA_CLIENT, I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_client_value());
		tipoFirmaMap.put(TIPO_FIRMA_REMOTA_AUTOMATICA, I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_remotaAutomatica_value());
		tipoFirmaMap.put(TIPO_FIRMA_REMOTA, I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_remota_value());
		tipoFirmaMap.put(TIPO_FIRMA_REMOTA_SOLO_PIN, I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_remota_solo_pin_value());
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_FIRMA_ESTERNA")) {
			tipoFirmaMap.put(TIPO_FIRMA_REMOTA_WS, I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_remota_ws_value());	
		}
		
		tipoFirmaSelectItem = new SelectItem();
		tipoFirmaSelectItem.setName("tipoFirma");
		tipoFirmaSelectItem.setTitle(setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_metodoFirma_title()));
		tipoFirmaSelectItem.setShowTitle(true);
		tipoFirmaSelectItem.setColSpan(1);
		tipoFirmaSelectItem.setWidth(300);
		tipoFirmaSelectItem.setAlign(Alignment.CENTER);
		tipoFirmaSelectItem.setValueMap(tipoFirmaMap);
		tipoFirmaSelectItem.setAllowEmptyValue(false);
		tipoFirmaSelectItem.setRequired(true);
		tipoFirmaSelectItem.setDefaultValue(getDefaultFirmaValue());
		tipoFirmaSelectItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				setValues(vm.getValuesAsRecord(), false);
				mDynamicForm.markForRedraw();
			}
		});
		
		providerFirmaRemotaItem = new SelectItem("provider_firma_remota", I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_providerFirmaHsmSelect());
		providerFirmaRemotaItem.setValueMap(providerValueMap);
		providerFirmaRemotaItem.setAllowEmptyValue(false);
		providerFirmaRemotaItem.setWidth(300);
		providerFirmaRemotaItem.setStartRow(true);
		providerFirmaRemotaItem.setDefaultToFirstOption(true);
		providerFirmaRemotaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (TIPO_FIRMA_REMOTA.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString()) || TIPO_FIRMA_REMOTA_SOLO_PIN.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString())) && providerValueMap.size() > 1;
			}
		});
		
		providerFirmaRemotaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				setValues(vm.getValuesAsRecord(), false);
				mDynamicForm.markForRedraw();
			}
		});
		
		String userIdItemTitle = "";
		if (AurigaLayout.getParametroDBAsBoolean("DISATTIVA_PROPOSTA_CF_X_FIRMA_HSM")) {
			userIdItemTitle = I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_userId();
		} else {
			userIdItemTitle = I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_userIdCF();
		}
		userIdItem = new TextItem("userIdFirmatario", setTitleAlign(userIdItemTitle));
		userIdItem.setColSpan(1);
		userIdItem.setWidth(300);
		userIdItem.setStartRow(true);
		userIdItem.setAttribute("obbligatorio", true);
		userIdItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return TIPO_FIRMA_REMOTA.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString()) || TIPO_FIRMA_REMOTA_SOLO_PIN.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString());
			}
		});
		userIdItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return  TIPO_FIRMA_REMOTA.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString()) || TIPO_FIRMA_REMOTA_SOLO_PIN.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString());
			}
		}));
		userIdItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		String firmaInDelegaItemTitle = "";
		if (AurigaLayout.getParametroDBAsBoolean("DISATTIVA_PROPOSTA_CF_X_FIRMA_HSM")) {
			firmaInDelegaItemTitle = I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_firmaInDelega();
		} else {
			firmaInDelegaItemTitle = I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_firmaInDelegaCF();
		}
		firmaInDelegaItem = new TextItem("userIdDelegante",setTitleAlign(firmaInDelegaItemTitle));
		firmaInDelegaItem.setColSpan(1);
		firmaInDelegaItem.setWidth(300);
		firmaInDelegaItem.setStartRow(true);
		firmaInDelegaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return showFirmaInDelega();
			}
		});
		firmaInDelegaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		/* Item spostati in SaveCredenzialiFirmaAutomaticaWindow
		passwordItem = new PasswordItem("password", setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_password()));
		passwordItem.setAttribute("obbligatorio", true);
		passwordItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		passwordItem.setColSpan(1);
		passwordItem.setWidth(300);
		passwordItem.setStartRow(true);
		passwordItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return TIPO_FIRMA_REMOTA_AUTOMATICA.equals((String)tipoFirmaSelectItem.getValue());
			}
		});
		RequiredIfValidator reqIfValPwd = new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return TIPO_FIRMA_REMOTA_AUTOMATICA.equals((String)tipoFirmaSelectItem.getValue());
			}
		});
		passwordItem.setValidators(reqIfValPwd);
		passwordItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		confermaPasswordItem = new PasswordItem("confermaPassword",setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_confermaPassword()));
		confermaPasswordItem.setAttribute("obbligatorio", true);
		confermaPasswordItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		confermaPasswordItem.setColSpan(1);
		confermaPasswordItem.setWidth(300);
		confermaPasswordItem.setStartRow(true);
		confermaPasswordItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return TIPO_FIRMA_REMOTA_AUTOMATICA.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString()) &&
					   (pwdDB == null || "".equalsIgnoreCase(pwdDB));
			}
		});
		RequiredIfValidator reqIfValConfermaPwd = new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return TIPO_FIRMA_REMOTA_AUTOMATICA.equals((String)tipoFirmaSelectItem.getValue());
			}
		});
		CustomValidator validatorConfermaPwd = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				
				boolean isVerify = true;
				if(TIPO_FIRMA_REMOTA_AUTOMATICA.equals((String)tipoFirmaSelectItem.getValue())) {
					String pwd = passwordItem.getValue() != null ? (String)passwordItem.getValue() : "";
					String confermaPwd = confermaPasswordItem.getValue() != null ? (String) confermaPasswordItem.getValue() : "";
					if(!"".equalsIgnoreCase(confermaPwd) && !pwd.equalsIgnoreCase(confermaPwd)) {
						isVerify = false;
					}
				} 
				return isVerify;
			}
		};
		validatorConfermaPwd.setErrorMessage("Le password non coincidono!");
		confermaPasswordItem.setValidators(reqIfValConfermaPwd,validatorConfermaPwd);
		confermaPasswordItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		cambiaPasswordButton = new ImgButtonItem("cambiaPassword", "buttons/reset_pwd.png", I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_cambiaPassword());
		cambiaPasswordButton.setAlwaysEnabled(true);
		cambiaPasswordButton.setColSpan(1);
		cambiaPasswordButton.setStartRow(false);
		cambiaPasswordButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return TIPO_FIRMA_REMOTA_AUTOMATICA.equals((String)tipoFirmaSelectItem.getValue()) &&
					   (pwdDB != null && !"".equalsIgnoreCase(pwdDB));
			}
		});
		cambiaPasswordButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				
				passwordItem.setCanEdit(true);
				pwdDB = null;
			}
		});
		cambiaPasswordButton.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				
				mDynamicForm.markForRedraw();
			}
		});
		*/
		usernameRichOtpItem = new TextItem("usernameRichOtp",setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_usernameRichOtp()));
		usernameRichOtpItem.setColSpan(1);
		usernameRichOtpItem.setWidth(300);
		usernameRichOtpItem.setStartRow(true);
		usernameRichOtpItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return showCredenzialiOtp();
			}
		});
		usernameRichOtpItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		SpacerItem spacerShowSendOtpViaSmsItem = new SpacerItem();
		spacerShowSendOtpViaSmsItem.setColSpan(1);
		spacerShowSendOtpViaSmsItem.setStartRow(true);
		spacerShowSendOtpViaSmsItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return showSendOtpViaSms();	
			}
		});
		
		showSendOtpViaSmsItem = new CheckboxItem("showSendOtpViaSms", I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_showGeneraOtpViaSms());
		showSendOtpViaSmsItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showSendOtpViaSms();	
			}
		});
		
		SpacerItem spacerShowSendOtpViaCallItem = new SpacerItem();
		spacerShowSendOtpViaCallItem.setColSpan(1);
		spacerShowSendOtpViaCallItem.setStartRow(true);
		spacerShowSendOtpViaCallItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return showSendOtpViaCall();	
			}
		});
		
		showSendOtpViaCallItem = new CheckboxItem("showSendOtpViaCall", I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_showGeneraOtpViaCall());
		showSendOtpViaCallItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showSendOtpViaCall();
			}
		});
		
//		mDynamicForm.setItems(attivaFirmaInDelegaItem, canSendOtpViaSmsItem, canSendOtpViaCallItem, tipoFirmaSelectItem, providerFirmaRemotaItem, userIdItem, firmaInDelegaItem, passwordItem, cambiaPasswordButton, confermaPasswordItem, usernameRichOtpItem, passwordRichOtpItem, confermaPasswordRichOtpItem, cambiaPasswordRichOtpButton, spacerShowSendOtpViaSmsItem, showSendOtpViaSmsItem, spacerShowSendOtpViaCallItem, showSendOtpViaCallItem);
//		mDynamicForm.setItems(attivaFirmaInDelegaItem, canSendOtpViaSmsItem, canSendOtpViaCallItem, tipoFirmaSelectItem, providerFirmaRemotaItem, userIdItem, firmaInDelegaItem, passwordItem, cambiaPasswordButton, confermaPasswordItem, usernameRichOtpItem, spacerShowSendOtpViaSmsItem, showSendOtpViaSmsItem, spacerShowSendOtpViaCallItem, showSendOtpViaCallItem);
		mDynamicForm.setItems(attivaFirmaInDelegaItem, canSendOtpViaSmsItem, canSendOtpViaCallItem, tipoFirmaSelectItem, providerFirmaRemotaItem, userIdItem, firmaInDelegaItem, usernameRichOtpItem, spacerShowSendOtpViaSmsItem, showSendOtpViaSmsItem, spacerShowSendOtpViaCallItem, showSendOtpViaCallItem);

		saveButton = new Button("Salva");
		saveButton.setIcon("ok.png");
		saveButton.setIconSize(16);
		saveButton.setAutoFit(false);
		saveButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (vm.validate()) {
					// Devo azzerare i valori delle impostazioni non abilitate
					String attivaFirmaInDelega = mDynamicForm.getValueAsString("attivaFirmaInDelega");
					if (attivaFirmaInDelega == null || "".equalsIgnoreCase(attivaFirmaInDelega) || "false".equalsIgnoreCase(attivaFirmaInDelega)) {
						mDynamicForm.setValue("firmaInDelega", "");
					}
					String canSendOtpViaSms = mDynamicForm.getValueAsString("canSendOtpViaSms");
					if (canSendOtpViaSms == null || "".equalsIgnoreCase(canSendOtpViaSms) || "false".equalsIgnoreCase(canSendOtpViaSms)) {
						mDynamicForm.setValue("showSendOtpViaSms", false);
					}
					String canSendOtpViaCall = mDynamicForm.getValueAsString("canSendOtpViaCall");
					if (canSendOtpViaCall == null || "".equalsIgnoreCase(canSendOtpViaCall) || "false".equalsIgnoreCase(canSendOtpViaCall)) {
						mDynamicForm.setValue("showSendOtpViaCall", false);
					}
		
					Map mapToSave = mDynamicForm.getValuesAsRecord().toMap();
					mapToSave.remove("canSendOtpViaSms");
					mapToSave.remove("canSendOtpViaCall");
					String userIdFirmatario = mapToSave.get("userIdFirmatario") != null ? (String) mapToSave.get("userIdFirmatario") : null;
					String userIdDelegante = mapToSave.get("userIdDelegante") != null ? (String) mapToSave.get("userIdDelegante") : null;
					if (userIdDelegante != null && !"".equalsIgnoreCase(userIdDelegante)) {
						mapToSave.put("userId", userIdDelegante);
						mapToSave.put("firmaInDelega", userIdFirmatario);
					} else {
						mapToSave.put("userId", userIdFirmatario);
						mapToSave.put("firmaInDelega", "");
					}
					mapToSave.remove("userIdFirmatario");
					mapToSave.remove("userIdDelegante");
					Record prefToSave = new Record(mapToSave);
					boolean manageOnOkButtonClick = manageOnOkButtonClick(prefToSave);
					if(manageOnOkButtonClick) {
						markForDestroy();
					}
//					markForDestroy();
				}
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(saveButton);
		
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
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();

		layout.addMember(mDynamicForm);
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);
	}

	public void closeModal() {
		markForDestroy();
	}
	
	protected boolean showFirmaInDelega() {
		String attivaFirmaInDelega = mDynamicForm.getValueAsString("attivaFirmaInDelega");
		if (attivaFirmaInDelega != null && "true".equalsIgnoreCase(attivaFirmaInDelega) && (TIPO_FIRMA_REMOTA.equals(mDynamicForm.getValueAsString("tipoFirma")) || TIPO_FIRMA_REMOTA_SOLO_PIN.equalsIgnoreCase(mDynamicForm.getValueAsString("tipoFirma")))) {
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean showCredenzialiOtp() {
		String providerFirma = mDynamicForm.getValueAsString("provider_firma_remota");
		boolean mostraCredenzialiOtp = FirmaUtility.getValoreVariabileHsmParamsAsBoolean("useDifferentCredentialForOtpRequest", providerFirma);
		if (mostraCredenzialiOtp && TIPO_FIRMA_REMOTA.equals(mDynamicForm.getValueAsString("tipoFirma"))) {
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean showSendOtpViaSms() {
		String canSendOtpViaSms = mDynamicForm.getValueAsString("canSendOtpViaSms");
		if (canSendOtpViaSms != null && "true".equalsIgnoreCase(canSendOtpViaSms) && TIPO_FIRMA_REMOTA.equals(mDynamicForm.getValueAsString("tipoFirma"))) {
			return true;
		} else {
			return false;
		}
	}

	protected boolean showSendOtpViaCall() {
		String canSendOtpViaCall = mDynamicForm.getValueAsString("canSendOtpViaCall");
		if (canSendOtpViaCall != null && "true".equalsIgnoreCase(canSendOtpViaCall) && TIPO_FIRMA_REMOTA.equals(mDynamicForm.getValueAsString("tipoFirma"))) {
			return true;
		} else {
			return false;
		}
	}

	public void clearValues() {
		mDynamicForm.clearValues();
	}

	public void setValues(Record values, boolean fromLoadPreference) {
		if (values != null) {
//			if(values.getAttributeAsString("password") != null && !"".equals(values.getAttributeAsString("password"))){
//				pwdDB = values.getAttributeAsString("password");
//				passwordItem.setCanEdit(false);
//			}
			
//			if(values.getAttributeAsString("passwordRichOtp") != null && !"".equals(values.getAttributeAsString("passwordRichOt"))){
//				pwdDBRichOtp = values.getAttributeAsString("passwordRichOtp");
//				passwordRichOtpItem.setCanEdit(false);
//			}
			
			Boolean attivaFirmaInDelega = false;
			Boolean canSendOtpViaSms = false;
			Boolean canSendOtpViaCall = false;

			if (values.getAttribute("tipoFirma") != null && (TIPO_FIRMA_REMOTA.equals(values.getAttributeAsString("tipoFirma")) || TIPO_FIRMA_REMOTA_AUTOMATICA.equals(values.getAttributeAsString("tipoFirma")) || TIPO_FIRMA_REMOTA_SOLO_PIN.equals(values.getAttributeAsString("tipoFirma")))) {
				String providerFirma = null;
				if (values.getAttribute("provider_firma_remota") != null && !"".equals(values.getAttributeAsString("provider_firma_remota"))) {
					providerFirma = values.getAttributeAsString("provider_firma_remota");
				} else if (providerValueMap.size() == 1){
					providerFirma = (String) providerValueMap.get(providerValueMap.keySet().toArray()[0]);
				} else {
					providerFirma = null;
				}
				
				Map providerFirmaRemotaItemValueMap = providerFirmaRemotaItem.getValueMap();
				if (providerFirmaRemotaItemValueMap != null && providerFirmaRemotaItemValueMap.size() > 0 && !providerFirmaRemotaItemValueMap.containsKey(providerFirma)) {
					String firstValue = (String) providerValueMap.get(providerValueMap.keySet().toArray()[0]);
					providerFirma = firstValue;
					values.setAttribute("provider_firma_remota", firstValue);
				}
							
				attivaFirmaInDelega = FirmaUtility.getValoreVariabileHsmParamsAsBoolean("attivaFirmaInDelega", providerFirma);
				canSendOtpViaCall = FirmaUtility.getValoreVariabileHsmParamsAsBoolean("canSendOtpViaCall", providerFirma);
				canSendOtpViaSms =  FirmaUtility.getValoreVariabileHsmParamsAsBoolean("canSendOtpViaSms", providerFirma);
			}
			
			values.setAttribute("attivaFirmaInDelega", attivaFirmaInDelega);
			values.setAttribute("canSendOtpViaCall", canSendOtpViaCall);
			values.setAttribute("canSendOtpViaSms", canSendOtpViaSms);
			
			if (fromLoadPreference) {
				String userId = values.getAttributeAsString("userId");
				String firmatarioDelegato = values.getAttributeAsString("firmaInDelega");
				if (firmatarioDelegato != null && !"".equalsIgnoreCase(firmatarioDelegato)) {
					values.setAttribute("userIdFirmatario", firmatarioDelegato);
					values.setAttribute("userIdDelegante", userId);
				} else {
					values.setAttribute("userIdFirmatario", userId);
					values.setAttribute("userIdDelegante", "");
				}
			}
			
			mDynamicForm.editRecord(values);
		} else {
			mDynamicForm.editNewRecord();
		}
		mDynamicForm.clearErrors(true);
	}

	public boolean manageOnOkButtonClick(Record values) {
		return false;
	}
	
	public static String getDefaultFirmaValue() {

		if("JNLP".equals(AurigaLayout.getParametroDB("MODALITA_FIRMA"))
			|| "APPLET".equals(AurigaLayout.getParametroDB("MODALITA_FIRMA"))) {
				return TIPO_FIRMA_CLIENT;
		} else if("HSM".equals(AurigaLayout.getParametroDB("MODALITA_FIRMA")) &&
				!AurigaLayout.getParametroDBAsBoolean("FIRMA_AUTOMATICA")) {
				return TIPO_FIRMA_REMOTA;
		} else if("HSM".equals(AurigaLayout.getParametroDB("MODALITA_FIRMA")) &&
				AurigaLayout.getParametroDBAsBoolean("FIRMA_AUTOMATICA")) {
				return TIPO_FIRMA_REMOTA_AUTOMATICA;
		} else 
		      return "";
	}

	private String setTitleAlign(String title) {
		return "<span style=\"width: 220px; display: inline-block;\">" + title + "</span>";
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