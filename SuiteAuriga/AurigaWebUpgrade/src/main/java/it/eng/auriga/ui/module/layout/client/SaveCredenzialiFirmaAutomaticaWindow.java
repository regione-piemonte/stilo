/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client;

import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormItemType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class SaveCredenzialiFirmaAutomaticaWindow extends ModalWindow {
	
	private String pwdDB = null;
	private String pwdAsSeparatedFieldDB = null;
	private String pinDB = null;

	private HiddenItem attivaFirmaInDelegaItem;
	private HiddenItem authByPINPasswordAsSeparateFiledsItem;
	
	private ValuesManager vm;
	private DynamicForm mDynamicForm;
	private SelectItem providerFirmaRemotaItem;
	private TextItem userIdItem;
	private TextItem firmaInDelegaItem;
	private FormItem passwordItem;
	private ImgButtonItem cambiaPasswordButton;
	private FormItem confermaPasswordItem;
	private FormItem passwordAsSeparateFieldItem;
	private ImgButtonItem cambiaPasswordAsSeparatedFieldButton;
	private FormItem confermaPasswordAsSeparatedFieldItem;
	private FormItem authPINItem;
	private ImgButtonItem cambiaAuthPinAsSeparatedFieldButton;
	private FormItem confermaAuthPinAsSeparatedFieldItem;
	
	private Button saveButton;
	private Map providerValueMap;
	
	public SaveCredenzialiFirmaAutomaticaWindow(final Map providerValueMap) {
		
		super("config_utente_impostazioniFirmaAutomatica", true);

		setTitle(I18NUtil.getMessages().configUtenteMenuImpostazioniCredenzialiFirmaAutomatica_title());
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

		authByPINPasswordAsSeparateFiledsItem = new HiddenItem("authByPINPasswordAsSeparateFileds");
		
		providerFirmaRemotaItem = new SelectItem("provider_firma_remota", I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_providerFirmaHsmSelect());
		providerFirmaRemotaItem.setValueMap(providerValueMap);
		providerFirmaRemotaItem.setAllowEmptyValue(false);
		providerFirmaRemotaItem.setWidth(300);
		providerFirmaRemotaItem.setStartRow(true);
		providerFirmaRemotaItem.setDefaultToFirstOption(true);
		providerFirmaRemotaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return providerValueMap.size() > 1;
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
		
		passwordItem = new PasswordItem("password", setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_password()));
		passwordItem.setAttribute("obbligatorio", true);
		passwordItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		passwordItem.setColSpan(1);
		passwordItem.setWidth(300);
		passwordItem.setStartRow(true);

		passwordItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		passwordItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {		
				return !isAuthByPINPasswordAsSeparateFileds();
			}
		});
		
		cambiaPasswordButton = new ImgButtonItem("cambiaPassword", "buttons/reset_pwd.png", I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_cambiaPassword());
		cambiaPasswordButton.setAlwaysEnabled(true);
		cambiaPasswordButton.setColSpan(1);
		cambiaPasswordButton.setStartRow(false);
		cambiaPasswordButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (pwdDB != null && !"".equalsIgnoreCase(pwdDB) && !isAuthByPINPasswordAsSeparateFileds());
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
		
		confermaPasswordItem = new PasswordItem("confermaPassword",setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_confermaPassword()));
		confermaPasswordItem.setAttribute("obbligatorio", true);
		confermaPasswordItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		confermaPasswordItem.setColSpan(1);
		confermaPasswordItem.setWidth(300);
		confermaPasswordItem.setStartRow(true);
		confermaPasswordItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showConfermaPasswordItem();
			}
		});
		RequiredIfValidator reqIfValConfermaPwd = new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {	
				return !isAuthByPINPasswordAsSeparateFileds();
			}
		});
		CustomValidator validatorConfermaPwd = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if (showConfermaPasswordItem()) {
					boolean isVerify = true;
					String pwd = passwordItem.getValue() != null ? (String)passwordItem.getValue() : "";
					String confermaPwd = confermaPasswordItem.getValue() != null ? (String) confermaPasswordItem.getValue() : "";
					if(!"".equalsIgnoreCase(confermaPwd) && !pwd.equalsIgnoreCase(confermaPwd)) {
						isVerify = false;
					}
					return isVerify;
				} else {
					return true;
				}
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
		
		passwordAsSeparateFieldItem = new PasswordItem("passwordAsSeparateField", setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_passwordAsSeparateFiled()));
		passwordAsSeparateFieldItem.setAttribute("obbligatorio", true);
		passwordAsSeparateFieldItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		passwordAsSeparateFieldItem.setColSpan(1);
		passwordAsSeparateFieldItem.setWidth(300);
		passwordAsSeparateFieldItem.setStartRow(true);
		
		passwordAsSeparateFieldItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		passwordAsSeparateFieldItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAuthByPINPasswordAsSeparateFileds();
			}
		});
		passwordAsSeparateFieldItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				if(isAuthByPINPasswordAsSeparateFileds()) {
					return true;
				} else {
					return false;
				}
			}
		}));
		
		cambiaPasswordAsSeparatedFieldButton = new ImgButtonItem("cambiaPasswordAsSeparatedField", "buttons/reset_pwd.png", I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_cambiaPasswordAsSeparateFiled());
		cambiaPasswordAsSeparatedFieldButton.setAlwaysEnabled(true);
		cambiaPasswordAsSeparatedFieldButton.setColSpan(1);
		cambiaPasswordAsSeparatedFieldButton.setStartRow(false);
		cambiaPasswordAsSeparatedFieldButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (pwdAsSeparatedFieldDB != null && !"".equalsIgnoreCase(pwdAsSeparatedFieldDB) && isAuthByPINPasswordAsSeparateFileds());
			}
		});
		cambiaPasswordAsSeparatedFieldButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				passwordAsSeparateFieldItem.setCanEdit(true);
				pwdAsSeparatedFieldDB = null;
			}
		});
		cambiaPasswordAsSeparatedFieldButton.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		confermaPasswordAsSeparatedFieldItem = new PasswordItem("confermaPasswordAsSeparatedField",setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_confermaPasswordAsSeparateFiled()));
		confermaPasswordAsSeparatedFieldItem.setAttribute("obbligatorio", true);
		confermaPasswordAsSeparatedFieldItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		confermaPasswordAsSeparatedFieldItem.setColSpan(1);
		confermaPasswordAsSeparatedFieldItem.setWidth(300);
		confermaPasswordAsSeparatedFieldItem.setStartRow(true);
		confermaPasswordAsSeparatedFieldItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showConfermaPasswordAsSeparatedFieldItem();
			}
		});
		RequiredIfValidator reqIfValConfermaPwdAsSeparateField = new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isAuthByPINPasswordAsSeparateFileds();
			}
		});
		CustomValidator validatorConfermaPwdAsSeparateField = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if (showConfermaPasswordAsSeparatedFieldItem()) {
					boolean isVerify = true;
					String pwd = passwordAsSeparateFieldItem.getValue() != null ? (String)passwordAsSeparateFieldItem.getValue() : "";
					String confermaPwd = confermaPasswordAsSeparatedFieldItem.getValue() != null ? (String) confermaPasswordAsSeparatedFieldItem.getValue() : "";
					if(!"".equalsIgnoreCase(confermaPwd) && !pwd.equalsIgnoreCase(confermaPwd)) {
						isVerify = false;
					}
					return isVerify;
				} else {
					return true;
				}
			}
		};
		validatorConfermaPwdAsSeparateField.setErrorMessage("Le password non coincidono!");
		confermaPasswordAsSeparatedFieldItem.setValidators(reqIfValConfermaPwdAsSeparateField,validatorConfermaPwdAsSeparateField);
		confermaPasswordAsSeparatedFieldItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		authPINItem = new PasswordItem("authPIN", setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_pinAsSeparateFiled()));
		authPINItem.setAttribute("obbligatorio", true);
		authPINItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		authPINItem.setColSpan(1);
		authPINItem.setWidth(300);
		authPINItem.setStartRow(true);
		authPINItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		authPINItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAuthByPINPasswordAsSeparateFileds();
			}
		});
		authPINItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				if(isAuthByPINPasswordAsSeparateFileds()) {
					return true;
				} else {
					return false;
				}
			}
		}));
		
		cambiaAuthPinAsSeparatedFieldButton = new ImgButtonItem("cambiaPinAsSeparatedField", "buttons/reset_pwd.png", "Cambio PIN");
		cambiaAuthPinAsSeparatedFieldButton.setAlwaysEnabled(true);
		cambiaAuthPinAsSeparatedFieldButton.setColSpan(1);
		cambiaAuthPinAsSeparatedFieldButton.setStartRow(false);
		cambiaAuthPinAsSeparatedFieldButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (pinDB != null && !"".equalsIgnoreCase(pinDB) && isAuthByPINPasswordAsSeparateFileds());
			}
		});
		cambiaAuthPinAsSeparatedFieldButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				authPINItem.setCanEdit(true);
				pinDB = null;
			}
		});
		cambiaAuthPinAsSeparatedFieldButton.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		confermaAuthPinAsSeparatedFieldItem = new PasswordItem("confermaPinAsSeparatedField",setTitleAlign("Conferma PIN"));
		confermaAuthPinAsSeparatedFieldItem.setAttribute("obbligatorio", true);
		confermaAuthPinAsSeparatedFieldItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		confermaAuthPinAsSeparatedFieldItem.setColSpan(1);
		confermaAuthPinAsSeparatedFieldItem.setWidth(300);
		confermaAuthPinAsSeparatedFieldItem.setStartRow(true);
		confermaAuthPinAsSeparatedFieldItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showConfermaAuthPinAsSeparatedField();
			}
		});
		RequiredIfValidator reqIfValConfermaPinAsSeparateField = new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {	
				return isAuthByPINPasswordAsSeparateFileds();
			}
		});
		CustomValidator validatorConfermaPinAsSeparateField = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if (showConfermaAuthPinAsSeparatedField()) {
					boolean isVerify = true;
					String pin = authPINItem.getValue() != null ? (String)authPINItem.getValue() : "";
					String confermaPin = confermaAuthPinAsSeparatedFieldItem.getValue() != null ? (String) confermaAuthPinAsSeparatedFieldItem.getValue() : "";
					if(!"".equalsIgnoreCase(confermaPin) && !pin.equalsIgnoreCase(confermaPin)) {
						isVerify = false;
					}
					return isVerify;
				}else {
					return true;
				}
			}
		};
		validatorConfermaPinAsSeparateField.setErrorMessage("I PIN non coincidono!");
		confermaAuthPinAsSeparatedFieldItem.setValidators(reqIfValConfermaPinAsSeparateField,validatorConfermaPinAsSeparateField);
		confermaAuthPinAsSeparatedFieldItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		mDynamicForm.setItems(attivaFirmaInDelegaItem, authByPINPasswordAsSeparateFiledsItem, providerFirmaRemotaItem, userIdItem, firmaInDelegaItem, passwordItem, cambiaPasswordButton, confermaPasswordItem, passwordAsSeparateFieldItem, cambiaPasswordAsSeparatedFieldButton, confermaPasswordAsSeparatedFieldItem, authPINItem, cambiaAuthPinAsSeparatedFieldButton, confermaAuthPinAsSeparatedFieldItem);

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
					boolean isAttivaFirmaInDelega = attivaFirmaInDelega != null && "true".equalsIgnoreCase(attivaFirmaInDelega) ? true : false;
					if (attivaFirmaInDelega == null || "".equalsIgnoreCase(attivaFirmaInDelega) || "false".equalsIgnoreCase(attivaFirmaInDelega)) {
						mDynamicForm.setValue("firmaInDelega", "");
					}
					String authByPINPasswordAsSeparateFileds = mDynamicForm.getValueAsString("authByPINPasswordAsSeparateFileds");
					if (authByPINPasswordAsSeparateFileds == null || "".equalsIgnoreCase(authByPINPasswordAsSeparateFileds) || "false".equalsIgnoreCase(authByPINPasswordAsSeparateFileds)) {
						mDynamicForm.setValue("authByPINPasswordAsSeparateFileds", "");
					}
		
					Map mapToSave = mDynamicForm.getValuesAsRecord().toMap();
					String userIdFirmatario = mapToSave.get("userIdFirmatario") != null ? (String) mapToSave.get("userIdFirmatario") : null;
					String userIdDelegante = mapToSave.get("userIdDelegante") != null ? (String) mapToSave.get("userIdDelegante") : null;
					if (isAttivaFirmaInDelega && (userIdDelegante != null && !"".equalsIgnoreCase(userIdDelegante))) {
						mapToSave.put("userId", userIdDelegante);
						mapToSave.put("firmaInDelega", userIdFirmatario);
					} else {
						mapToSave.put("userId", userIdFirmatario);
						mapToSave.put("firmaInDelega", "");
					}
					String passwordAsSeparateField = mapToSave.get("passwordAsSeparateField") != null ? (String) mapToSave.get("passwordAsSeparateField") : null;
					if (authByPINPasswordAsSeparateFileds != null && "true".equalsIgnoreCase(authByPINPasswordAsSeparateFileds)) {
						mapToSave.remove("password");
						mapToSave.remove("passwordAsSeparateField");
						mapToSave.put("password", passwordAsSeparateField);
					}
					mapToSave.remove("userIdFirmatario");
					mapToSave.remove("userIdDelegante");
					Record prefToSave = new Record(mapToSave);
					manageOnOkButtonClick(prefToSave);
					markForDestroy();
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

	protected boolean showFirmaInDelega() {
		String attivaFirmaInDelega = mDynamicForm.getValueAsString("attivaFirmaInDelega");
		if (attivaFirmaInDelega != null && "true".equalsIgnoreCase(attivaFirmaInDelega)) {
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean isAuthByPINPasswordAsSeparateFileds() {
		String authByPINPasswordAsSeparateFileds = mDynamicForm.getValueAsString("authByPINPasswordAsSeparateFileds");
		if (authByPINPasswordAsSeparateFileds != null && "true".equalsIgnoreCase(authByPINPasswordAsSeparateFileds)) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean showConfermaPasswordItem() {
		return (!isAuthByPINPasswordAsSeparateFileds() && (pwdDB == null || "".equalsIgnoreCase(pwdDB)));
	}
	
	private boolean showConfermaPasswordAsSeparatedFieldItem() {
		return (isAuthByPINPasswordAsSeparateFileds() && (pwdAsSeparatedFieldDB == null || "".equalsIgnoreCase(pwdAsSeparatedFieldDB)));
	}
	
	private boolean showConfermaAuthPinAsSeparatedField() {
		return (isAuthByPINPasswordAsSeparateFileds() && (pinDB == null || "".equalsIgnoreCase(pinDB)));
	}
	
	public void clearValues() {
		mDynamicForm.clearValues();
	}

	public void setValues(Record values, boolean fromLoadPreference) {
		if (values != null) {
			
			Boolean attivaFirmaInDelega = false;

			Boolean authByPINPasswordAsSeparateFileds = false;
			
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

			authByPINPasswordAsSeparateFileds = FirmaUtility.getValoreVariabileHsmParamsAsBoolean("authByPINPasswordAsSeparateFileds", providerFirma);
			
			values.setAttribute("attivaFirmaInDelega", attivaFirmaInDelega);

			values.setAttribute("authByPINPasswordAsSeparateFileds", authByPINPasswordAsSeparateFileds);
			
			if(!authByPINPasswordAsSeparateFileds && (values.getAttributeAsString("password") != null && !"".equals(values.getAttributeAsString("password")))){
				pwdDB = values.getAttributeAsString("password");
				passwordItem.setCanEdit(false);
			}
			
			if(authByPINPasswordAsSeparateFileds && (values.getAttributeAsString("password") != null && !"".equals(values.getAttributeAsString("password")))){
				pwdAsSeparatedFieldDB = values.getAttributeAsString("password");
				passwordAsSeparateFieldItem.setCanEdit(false);
			}
			
			if(values.getAttributeAsString("authPIN") != null && !"".equals(values.getAttributeAsString("authPIN"))){
				pinDB = values.getAttributeAsString("authPIN");
				authPINItem.setCanEdit(false);
			}
			
			if (fromLoadPreference) {
				String userId = values.getAttributeAsString("userId");
				String firmatarioDelegato = values.getAttributeAsString("firmaInDelega");
				String password = values.getAttributeAsString("password");
				if (firmatarioDelegato != null && !"".equalsIgnoreCase(firmatarioDelegato)) {
					values.setAttribute("userIdFirmatario", firmatarioDelegato);
					values.setAttribute("userIdDelegante", userId);
				} else {
					values.setAttribute("userIdFirmatario", userId);
					values.setAttribute("userIdDelegante", "");
				}
				if(authByPINPasswordAsSeparateFileds && (password!=null && !"".equals(password))) {
					values.setAttribute("passwordAsSeparateField", password);
				}
			}
			
			mDynamicForm.editRecord(values);
		} else {
			mDynamicForm.editNewRecord();
		}
		mDynamicForm.clearErrors(true);
	}

	public void setIsInDelega() {
		
		Record valuesAsRecord = vm.getValuesAsRecord();
			
		if (valuesAsRecord != null) {
			String providerFirma = null;
			if (valuesAsRecord.getAttribute("provider_firma_remota") != null
					&& !"".equals(valuesAsRecord.getAttributeAsString("provider_firma_remota"))) {
				providerFirma = valuesAsRecord.getAttributeAsString("provider_firma_remota");
			} else if (providerValueMap.size() == 1) {
				providerFirma = (String) providerValueMap.get(providerValueMap.keySet().toArray()[0]);
			} else {
				providerFirma = null;
			}
			Boolean attivaFirmaInDelega = false;
			attivaFirmaInDelega = FirmaUtility.getValoreVariabileHsmParamsAsBoolean("attivaFirmaInDelega",
					providerFirma);
			valuesAsRecord.setAttribute("attivaFirmaInDelega", attivaFirmaInDelega);
			Boolean authByPINPasswordAsSeparateFileds = false;
			authByPINPasswordAsSeparateFileds = FirmaUtility.getValoreVariabileHsmParamsAsBoolean("authByPINPasswordAsSeparateFileds",
					providerFirma);
			valuesAsRecord.setAttribute("authByPINPasswordAsSeparateFileds", authByPINPasswordAsSeparateFileds);
			String userId = valuesAsRecord.getAttributeAsString("userId");
			String firmatarioDelegato = valuesAsRecord.getAttributeAsString("firmaInDelega");
			if (firmatarioDelegato != null && !"".equalsIgnoreCase(firmatarioDelegato)) {
				valuesAsRecord.setAttribute("userIdFirmatario", firmatarioDelegato);
				valuesAsRecord.setAttribute("userIdDelegante", userId);
			} else {
				valuesAsRecord.setAttribute("userIdFirmatario", userId);
				valuesAsRecord.setAttribute("userIdDelegante", "");
			} 
			mDynamicForm.editRecord(valuesAsRecord);
		} else {
			mDynamicForm.editNewRecord();
		}
		mDynamicForm.clearErrors(true);
		
	}
	public void manageOnOkButtonClick(Record values) {

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
