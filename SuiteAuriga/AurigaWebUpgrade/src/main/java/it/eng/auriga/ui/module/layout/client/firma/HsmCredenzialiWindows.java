/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormItemType;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public abstract class HsmCredenzialiWindows extends Window {

	private DynamicForm formUsernameHsm;
	private DynamicForm formListaCertificatiHsm;
	private DynamicForm formPasswordHsm;
	private DynamicForm formCodiceOtpHsm;
	
	private SelectItem listaCertificatiSelect;
	private PasswordItem passwordTextItem;
	private TextItem otpCodeTextItem;
	private ImgButtonItem generazioneRemotaOtpSmsImgButton;
	private ImgButtonItem generazioneRemotaOtpCallImgButton;
	private boolean isFirmaRemotaWithWS;
	
	
	private final int TITLE_WIDTH = 220;

	public HsmCredenzialiWindows(Record preimpostazioni, boolean isFirmaRemotaWithWS) {
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		if (!isFirmaRemotaWithWS) {
			setWidth(550);
			setHeight(270);
		} else {
			setWidth(450);
			setHeight(170);
		}
		setKeepInParentRect(true);
		if (!isFirmaRemotaWithWS) {
			setTitle(I18NUtil.getMessages().hsmCredenzialiWindow_title());
		} else {
			setTitle("INSERIMENTO OTP");
		}
		setShowModalMask(true);
		setShowCloseButton(false);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout vSpacerLayout = new VLayout();
		vSpacerLayout.setHeight(10);
		
		HLayout codiceFiscaleLayout = createUsernameLayout();
		HLayout listaCertificatiLayout = createListaCertificatiLayout();
		HLayout optLayout = null;
		if (!isFirmaRemotaWithWS) {
			optLayout = createOtpLayout();
		} else {
			optLayout = createOtpLayoutWS();
		}
		HLayout passwordLayout = createPasswordLayout();
		VLayout confermaLayout = createConfermaLayout();
		if (!isFirmaRemotaWithWS) {
			addItem(vSpacerLayout);
			addItem(codiceFiscaleLayout);
			if(nascondiListaCertificati()){
				passwordTextItem.setCanEdit(true);
				passwordTextItem.setTextBoxStyle(it.eng.utility.Styles.textItem);
				otpCodeTextItem.setCanEdit(true);
			} else {
				addItem(listaCertificatiLayout);
			}
		
			addItem(passwordLayout);
		}
		VLayout vFillLayout = new VLayout();
		vFillLayout.setHeight("100%");
		
		addItem(optLayout);
		addItem(vSpacerLayout);
		addItem(confermaLayout);
		addItem(vFillLayout);
		
		setShowTitle(true);
		
		String username = preimpostazioni.getAttribute("username");
		String codFiscaleLoggato = AurigaLayout.getUtenteLoggato().getCodFiscale(); 
		if (username != null && !"".equals(username)) {
			formUsernameHsm.setValue("username", username);
			if (!nascondiListaCertificati() && !isFirmaRemotaWithWS) {
				caricaListaCertificati();
			}
		} else if (codFiscaleLoggato != null && !"".equals(codFiscaleLoggato) && !AurigaLayout.getParametroDBAsBoolean("DISATTIVA_PROPOSTA_CF_X_FIRMA_HSM")) {
			formUsernameHsm.setValue("username", codFiscaleLoggato);
			if (!nascondiListaCertificati() && !isFirmaRemotaWithWS) {
				caricaListaCertificati();
			}
		}
		
		String usernameDelegante = preimpostazioni.getAttribute("usernameDelegante");
		if (usernameDelegante != null && !"".equals(usernameDelegante) && mostraUsernameDelegante()) {
			formUsernameHsm.setValue("usernameDelegante", usernameDelegante);
		}
		
		show();
	}

//	public HsmCredenzialiWindows(Record preimpostazioni) {
//		
//		setIsModal(true);
//		setModalMaskOpacity(50);
//		setAutoCenter(true);
//		setWidth(550);
//		setHeight(270);
//		setKeepInParentRect(true);
//		setTitle(I18NUtil.getMessages().hsmCredenzialiWindow_title());
//		setShowModalMask(true);
//		setShowCloseButton(false);
//		setShowMaximizeButton(false);
//		setShowMinimizeButton(false);
//		setAlign(Alignment.CENTER);
//		setTop(50);
//
//		VLayout vSpacerLayout = new VLayout();
//		vSpacerLayout.setHeight(10);
//		
//		HLayout codiceFiscaleLayout = createUsernameLayout();
//		HLayout listaCertificatiLayout = createListaCertificatiLayout();
//		HLayout optLayout = createOtpLayout();
//		HLayout passwordLayout = createPasswordLayout();
//		VLayout confermaLayout = createConfermaLayout();
//		if(!isFirmaRemotaWithWS) {
//			addItem(vSpacerLayout);
//			addItem(codiceFiscaleLayout);
//			if(nascondiListaCertificati()){
//				passwordTextItem.setCanEdit(true);
//				passwordTextItem.setTextBoxStyle(it.eng.utility.Styles.textItem);
//				otpCodeTextItem.setCanEdit(true);
//			} else {
//				addItem(listaCertificatiLayout);
//			}
//				
//				addItem(passwordLayout);
//		}
//		
//		VLayout vFillLayout = new VLayout();
//		vFillLayout.setHeight("100%");
//		
//		addItem(optLayout);
//		addItem(vSpacerLayout);
//		addItem(confermaLayout);
//		addItem(vFillLayout);
//		
//		setShowTitle(true);
//		
//		String username = preimpostazioni.getAttribute("username");
//		String codFiscaleLoggato = AurigaLayout.getUtenteLoggato().getCodFiscale(); 
//		if (username != null && !"".equals(username)) {
//			formUsernameHsm.setValue("username", username);
//			if (!nascondiListaCertificati()) {
//				caricaListaCertificati();
//			}
//		} else if (codFiscaleLoggato != null && !"".equals(codFiscaleLoggato)) {
//			formUsernameHsm.setValue("username", codFiscaleLoggato);
//			if (!nascondiListaCertificati()) {
//				caricaListaCertificati();
//			}
//		}
//		
//		String usernameDelegante = preimpostazioni.getAttribute("usernameDelegante");
//		if (usernameDelegante != null && !"".equals(usernameDelegante) && mostraUsernameDelegante()) {
//			formUsernameHsm.setValue("usernameDelegante", usernameDelegante);
//		}
//		
//		show();
//	}

	protected HLayout createUsernameLayout() {

		VLayout hSpacerLayout = new VLayout();
		hSpacerLayout.setWidth(20);

		formUsernameHsm = new DynamicForm();
		formUsernameHsm.setNumCols(10);
		formUsernameHsm.setCellPadding(5);
		formUsernameHsm.setWrapItemTitles(false);
		formUsernameHsm.setColWidths("20", "*");
		formUsernameHsm.setAlign(Alignment.CENTER);

		TextItem usernameTextItem = new TextItem();
		usernameTextItem.setName("username");
		if (AurigaLayout.getParametroDBAsBoolean("DISATTIVA_PROPOSTA_CF_X_FIRMA_HSM")) {
			usernameTextItem.setTitle(setTitleAlign(I18NUtil.getMessages().hsmCredenzialiWindow_usernameTextItem(), TITLE_WIDTH, false));
		} else {
			usernameTextItem.setTitle(setTitleAlign(I18NUtil.getMessages().hsmCredenzialiWindow_usernameCFTextItem(), TITLE_WIDTH, false));
		}
		usernameTextItem.setWidth(200);
		usernameTextItem.setRequired(true);
		usernameTextItem.setStartRow(true);
		
		ImgButtonItem caricaCertificatiImgButton = new ImgButtonItem("caricaCertificati", "firmaHsm/listaCertificati.png",I18NUtil.getMessages().hsmCredenzialiWindow_caricaCertificatiImgButton());
		
		caricaCertificatiImgButton.setIconHeight(30);
		caricaCertificatiImgButton.setIconWidth(36);
		caricaCertificatiImgButton.setColSpan(1);
		caricaCertificatiImgButton.setStartRow(false);
		caricaCertificatiImgButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				caricaListaCertificati();
			}
		});
		
		caricaCertificatiImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent arg0) {
				caricaListaCertificati();
			}
		});

		caricaCertificatiImgButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return !nascondiListaCertificati();
			}
		});
		
		TextItem deleganteTextItem = new TextItem();
		deleganteTextItem.setName("usernameDelegante");
		if (AurigaLayout.getParametroDBAsBoolean("DISATTIVA_PROPOSTA_CF_X_FIRMA_HSM")) {
			deleganteTextItem.setTitle(setTitleAlign(I18NUtil.getMessages().hsmCredenzialiWindow_usernameDeleganteTextItem(), TITLE_WIDTH, false));
		} else {
			deleganteTextItem.setTitle(setTitleAlign(I18NUtil.getMessages().hsmCredenzialiWindow_usernameCFDeleganteTextItem(), TITLE_WIDTH, false));
		}
		deleganteTextItem.setWidth(200);
		deleganteTextItem.setStartRow(true);
		
		deleganteTextItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return mostraUsernameDelegante();
			}
		});
		
		formUsernameHsm.setItems(usernameTextItem, caricaCertificatiImgButton, deleganteTextItem);

		HLayout hLayout = new HLayout();
		hLayout.addMember(formUsernameHsm);

		return hLayout;
	}
	
	protected HLayout createListaCertificatiLayout() {

		VLayout hSpacerLayout = new VLayout();
		hSpacerLayout.setWidth(20);

		formListaCertificatiHsm = new DynamicForm();
		formListaCertificatiHsm.setNumCols(10);
		formListaCertificatiHsm.setCellPadding(5);
		formListaCertificatiHsm.setWrapItemTitles(false);
		formListaCertificatiHsm.setColWidths("20", "*");
		formListaCertificatiHsm.setAlign(Alignment.CENTER);
		
		listaCertificatiSelect = new SelectItem("listaCertificati", setTitleAlign(I18NUtil.getMessages().hsmCredenzialiWindow_listaCertificatiSelect(), TITLE_WIDTH, false));
		listaCertificatiSelect.setAllowEmptyValue(false);
		listaCertificatiSelect.setWidth(300);
		listaCertificatiSelect.setStartRow(true);
		listaCertificatiSelect.setCanEdit(false);
		listaCertificatiSelect.setDefaultToFirstOption(true);

		formListaCertificatiHsm.setItems(listaCertificatiSelect);

		HLayout hLayout = new HLayout();
		hLayout.addMember(formListaCertificatiHsm);

		return hLayout;
	}
	
	protected HLayout createOtpLayout() {
		
		VLayout hSpacerLayout = new VLayout();
		hSpacerLayout.setWidth(20);

		formCodiceOtpHsm = new DynamicForm();
		formCodiceOtpHsm.setNumCols(10);
		formCodiceOtpHsm.setCellPadding(5);
		formCodiceOtpHsm.setWrapItemTitles(false);
		formCodiceOtpHsm.setColWidths("20", "*");
		formCodiceOtpHsm.setAlign(Alignment.CENTER);
		
		otpCodeTextItem = new TextItem();
		otpCodeTextItem.setName("codiceOtp");
		otpCodeTextItem.setTitle(setTitleAlign(I18NUtil.getMessages().hsmCredenzialiWindow_otpCodeTextItem(), TITLE_WIDTH, false));
		otpCodeTextItem.setWidth(200);
		otpCodeTextItem.setStartRow(true);
		otpCodeTextItem.setCanEdit(false);
		otpCodeTextItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return mostraTextItemCodiceOtp();
			}
		});
		
		generazioneRemotaOtpSmsImgButton = new ImgButtonItem("generazioneRemotaOtpSms", "firmaHsm/hsmOtpSms.png", I18NUtil.getMessages().hsmCredenzialiWindow_generazioneRemotaOtpSmsImgButton());
		generazioneRemotaOtpSmsImgButton.setIconWidth(35);
		generazioneRemotaOtpSmsImgButton.setIconHeight(35);
		generazioneRemotaOtpSmsImgButton.setColSpan(1);
		generazioneRemotaOtpSmsImgButton.setStartRow(false);
		generazioneRemotaOtpSmsImgButton.setCanEdit(mostraGenerazioneRemotaOtpSms(true));
		generazioneRemotaOtpSmsImgButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				
				generazioneRemotaOtpButton("SMS");
			}
		});
			
		generazioneRemotaOtpCallImgButton = new ImgButtonItem("generazioneRemotaOtpSms", "firmaHsm/hsmOtpCall.png", I18NUtil.getMessages().hsmCredenzialiWindow_generazioneRemotaOtpCallImgButton());
		generazioneRemotaOtpCallImgButton.setIconWidth(35);
		generazioneRemotaOtpCallImgButton.setIconHeight(35);
		generazioneRemotaOtpCallImgButton.setColSpan(1);
		generazioneRemotaOtpCallImgButton.setStartRow(false);
		generazioneRemotaOtpCallImgButton.setCanEdit(mostraGenerazioneRemotaOtpCall(true));
		generazioneRemotaOtpCallImgButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				
				generazioneRemotaOtpButton("ARUBACALL");
			}
		});
		
		formCodiceOtpHsm.setItems(otpCodeTextItem, generazioneRemotaOtpSmsImgButton/*, generazioneRemotaOtpCallImgButton*/);

		HLayout hLayout = new HLayout();
		hLayout.addMember(formCodiceOtpHsm);

		return hLayout;
		
	}
	
	protected HLayout createOtpLayoutWS() {
		
		VLayout hSpacerLayout = new VLayout();
		hSpacerLayout.setWidth(20);

		formCodiceOtpHsm = new DynamicForm();
		formCodiceOtpHsm.setNumCols(10);
		formCodiceOtpHsm.setCellPadding(5);
		formCodiceOtpHsm.setWrapItemTitles(false);
		formCodiceOtpHsm.setColWidths("20", "*");
		formCodiceOtpHsm.setAlign(Alignment.LEFT);
		
		otpCodeTextItem = new TextItem();
		otpCodeTextItem.setName("codiceOtp");
		otpCodeTextItem.setTitle(setTitleAlign(I18NUtil.getMessages().hsmCredenzialiWindow_otpCodeTextItem(), TITLE_WIDTH, true));
		otpCodeTextItem.setWidth(200);
		otpCodeTextItem.setStartRow(true);
		otpCodeTextItem.setCanEdit(true);
		
		formCodiceOtpHsm.setItems(otpCodeTextItem);

		HLayout hLayout = new HLayout();
		hLayout.addMember(formCodiceOtpHsm);

		return hLayout;
		
	}
	
	protected HLayout createPasswordLayout() {

		VLayout hSpacerLayout = new VLayout();
		hSpacerLayout.setWidth(20);

		formPasswordHsm = new DynamicForm();
		formPasswordHsm.setNumCols(10);
		formPasswordHsm.setCellPadding(5);
		formPasswordHsm.setWrapItemTitles(false);
		formPasswordHsm.setColWidths("20", "*");
		formPasswordHsm.setAlign(Alignment.CENTER);

		passwordTextItem = new PasswordItem();
		passwordTextItem.setName("password");
		passwordTextItem.setTitle(setTitleAlign(I18NUtil.getMessages().hsmCredenzialiWindow_passwordTextItem(), TITLE_WIDTH, false));
		passwordTextItem.setWidth(200);
		passwordTextItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		passwordTextItem.setStartRow(true);
		passwordTextItem.setCanEdit(false);
		passwordTextItem.setTextBoxStyle(it.eng.utility.Styles.textItemReadonly);

		formPasswordHsm.setItems(passwordTextItem);

		HLayout hLayout = new HLayout();
		
		hLayout.addMember(formPasswordHsm);

		return hLayout;
	}

	protected VLayout createConfermaLayout() {

		HLayout buttonConfermaLayout = new HLayout();
		buttonConfermaLayout.setWidth100();
		buttonConfermaLayout.setHeight100();
		buttonConfermaLayout.setAlign(Alignment.CENTER);
		buttonConfermaLayout.setLayoutAlign(Alignment.CENTER);
		buttonConfermaLayout.setHoverAlign(Alignment.CENTER);

		Button okButton = new Button();
		okButton.setTitle(I18NUtil.getMessages().hsmCredenzialiWindow_okButton());
		okButton.setWidth(100);
		okButton.setTop(20);
		okButton.setAlign(Alignment.CENTER);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				onClickOkButton();
			}
		});

		VLayout hSpacerLayout = new VLayout();
		hSpacerLayout.setWidth(30);

		Button annullaButton = new Button();
		annullaButton.setTitle(I18NUtil.getMessages().hsmCredenzialiWindow_annullaButton());
		annullaButton.setWidth(100);
		annullaButton.setTop(20);
		annullaButton.setAlign(Alignment.CENTER);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent arg0) {
				onClickAnnullaButton();
			}

		});

		VLayout vSpacerLayout = new VLayout();
		vSpacerLayout.setHeight(10);

		buttonConfermaLayout.addMember(okButton);
		// buttonConfermaLayout.addMember(hSpacerLayout);
		// buttonConfermaLayout.addMember(annullaButton);

		VLayout layout = new VLayout();
		layout.setAlign(Alignment.CENTER);
		layout.setLayoutAlign(Alignment.CENTER);
		layout.setHoverAlign(Alignment.CENTER);
		layout.addMember(vSpacerLayout);
		layout.addMember(buttonConfermaLayout);

		return layout;
	}

	public String getUsername() {
		return formUsernameHsm.getValueAsString("username");
	}
	
	public String getUsernameDelegante() {
		return formUsernameHsm.getValueAsString("usernameDelegante");
	}

	public String getPassword() {
		return formPasswordHsm.getValueAsString("password");
	}

	public String getCodiceOtp() {
		return formCodiceOtpHsm.getValueAsString("codiceOtp");
	}
	
	public String getCertId() {
		String selectedCertificate = listaCertificatiSelect.getValueAsString();
		if ((selectedCertificate != null) && (!"".equalsIgnoreCase(selectedCertificate))){
			String[] split = selectedCertificate.split("#");
			if (split.length >= 1){
				return selectedCertificate.split("#")[0];
			}
		}
		return "";
		
	}

	public String getPotereDiFirma() {
		String selectedCertificate = listaCertificatiSelect.getValueAsString();
		if ((selectedCertificate != null) && (!"".equalsIgnoreCase(selectedCertificate))){
			String[] split = selectedCertificate.split("#");
			if (split.length >= 2){
				return selectedCertificate.split("#")[1];
			}
		}
		return "";
	}
	
	public void caricaListaCertificati(){
		formUsernameHsm.clearErrors(true);
		if(formUsernameHsm.validate()) {
			Record recordDaPassare = new Record();
			recordDaPassare.setAttribute("username", getUsername());
			recordDaPassare.setAttribute("providerHsmFromPreference", AurigaLayout.getImpostazioneFirma("provider_firma_remota"));
			GWTRestDataSource dataSource = new GWTRestDataSource("FirmaHsmDataSource");
			dataSource.executecustom("richiediListaCertificati", recordDaPassare, new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
	
					Layout.hideWaitPopup();
	
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record data = response.getData()[0];
						if (data.getAttributeAsBoolean("esitoOk")) {										
							Record[] listaRecordCertificati = data.getAttributeAsRecordArray("listaCertificati");
							if ((listaRecordCertificati != null) && (listaRecordCertificati.length > 0)){
								LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
	
								for (Record certificato : listaRecordCertificati) {
									String certId = certificato.getAttribute("certId");
									String potereDiFirma = certificato.getAttribute("potereDiFirma");
									String descrizione = certificato.getAttribute("descrizione");
									valueMap.put(certId + "#" + potereDiFirma, descrizione);
								} 
								listaCertificatiSelect.setValueMap(valueMap);
								listaCertificatiSelect.setCanEdit(true);
								listaCertificatiSelect.redraw();
								passwordTextItem.setCanEdit(true);
								passwordTextItem.setTextBoxStyle(it.eng.utility.Styles.textItem);
								otpCodeTextItem.setCanEdit(true);
								generazioneRemotaOtpSmsImgButton.setCanEdit(mostraGenerazioneRemotaOtpSms(false));
							}else{
								Layout.addMessage(new MessageBean("Non è stato trovato nessun certificato associato all'utente", "", MessageType.ERROR));
							}
						} else {
							String messaggio = data.getAttribute("errorMessage");
							Layout.addMessage(new MessageBean(messaggio, "", MessageType.ERROR));
						}
					}
				}
			});
		}
	}

	public abstract void onClickOkButton();

	public abstract void onClickAnnullaButton();

	public abstract void generazioneRemotaOtpButton(String typeOtp);
	
	public abstract boolean nascondiListaCertificati();
	
	public abstract boolean mostraUsernameDelegante();
	
	public abstract boolean mostraTextItemCodiceOtp();
	
	public abstract boolean mostraGenerazioneRemotaOtpSms(boolean creazioneWindow);
	
	public abstract boolean mostraGenerazioneRemotaOtpCall(boolean creazioneWindow);
		
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}

}