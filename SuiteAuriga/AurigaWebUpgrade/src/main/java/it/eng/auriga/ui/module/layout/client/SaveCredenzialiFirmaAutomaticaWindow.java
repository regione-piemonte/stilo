/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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

	private HiddenItem attivaFirmaInDelegaItem;
	
	private ValuesManager vm;
	private DynamicForm mDynamicForm;
	private SelectItem providerFirmaRemotaItem;
	private TextItem userIdItem;
	private TextItem firmaInDelegaItem;
	private FormItem passwordItem;
	private FormItem confermaPasswordItem;
	private ImgButtonItem cambiaPasswordButton;
	
	
	private Button saveButton;
	private Map providerValueMap;

	public SaveCredenzialiFirmaAutomaticaWindow(final Map providerValueMap) {
		
		super("config_utente_impostazioniFirma", true);

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
		
		confermaPasswordItem = new PasswordItem("confermaPassword",setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_confermaPassword()));
		confermaPasswordItem.setAttribute("obbligatorio", true);
		confermaPasswordItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		confermaPasswordItem.setColSpan(1);
		confermaPasswordItem.setWidth(300);
		confermaPasswordItem.setStartRow(true);
		confermaPasswordItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return (pwdDB == null || "".equalsIgnoreCase(pwdDB));
			}
		});

		RequiredIfValidator reqIfValConfermaPwd = new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return true;
			}
		});
		CustomValidator validatorConfermaPwd = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				
				boolean isVerify = true;
				
					String pwd = passwordItem.getValue() != null ? (String)passwordItem.getValue() : "";
					String confermaPwd = confermaPasswordItem.getValue() != null ? (String) confermaPasswordItem.getValue() : "";
					if(!"".equalsIgnoreCase(confermaPwd) && !pwd.equalsIgnoreCase(confermaPwd)) {
						isVerify = false;
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
				
				return (pwdDB != null && !"".equalsIgnoreCase(pwdDB));
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
		
		mDynamicForm.setItems(attivaFirmaInDelegaItem, providerFirmaRemotaItem, userIdItem, firmaInDelegaItem, passwordItem, cambiaPasswordButton, confermaPasswordItem);

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
		
					Map mapToSave = mDynamicForm.getValuesAsRecord().toMap();
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
	
	public void clearValues() {
		mDynamicForm.clearValues();
	}

	public void setValues(Record values, boolean fromLoadPreference) {
		if (values != null) {
			
			Boolean attivaFirmaInDelega = false;
			
			if(values.getAttributeAsString("password") != null && !"".equals(values.getAttributeAsString("password"))){
				pwdDB = values.getAttributeAsString("password");
				passwordItem.setCanEdit(false);
			}
			
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
			
			values.setAttribute("attivaFirmaInDelega", attivaFirmaInDelega);
			
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