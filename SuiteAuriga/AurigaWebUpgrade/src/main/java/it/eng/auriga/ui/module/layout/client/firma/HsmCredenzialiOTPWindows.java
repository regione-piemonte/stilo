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

public abstract class HsmCredenzialiOTPWindows extends Window {

	private Window _this = this;
	
	private DynamicForm formCredenzialiOTP;
	
	private TextItem usernameCredenzialiOTPItem;
	private PasswordItem passwordCredenzialiOTPItem;
	;
	
//	private boolean firmaHsmRequireRemoteGenerationOtp;
//	private boolean firmaHsmRequireCaricamentoCertificati;
	
	private final int TITLE_WIDTH = 120;

	//public HsmCredenzialiWindowNuova(boolean firmaHsmRequireRemoteGenerationOtp, boolean firmaHsmRequireCaricamentoCertificati) {
	public HsmCredenzialiOTPWindows(Record preimpostazioni) {
		
//		this.firmaHsmRequireRemoteGenerationOtp = firmaHsmRequireRemoteGenerationOtp;
//		this.firmaHsmRequireCaricamentoCertificati = firmaHsmRequireCaricamentoCertificati;
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);
		setHeight(200);
		setKeepInParentRect(true);
		setTitle(I18NUtil.getMessages().hsmCredenzialiOTPWindow_title());
		setShowModalMask(true);
		setShowCloseButton(false);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout vSpacerLayout = new VLayout();
		vSpacerLayout.setHeight(10);
		
		HLayout credenzialiLayout = createCredenzialiLayout();
		VLayout confermaLayout = createConfermaLayout();
		
		VLayout vFillLayout = new VLayout();
		vFillLayout.setHeight("100%");
		
		addItem(credenzialiLayout);
		addItem(vSpacerLayout);
		addItem(confermaLayout);
		addItem(vFillLayout);
		
		setShowTitle(true);
		
		// Setto i valori a maschera
		String otpUsername = preimpostazioni.getAttribute("otpUsername");
		if (otpUsername != null && !"".equals(otpUsername)) {
			formCredenzialiOTP.setValue("otpUsername", otpUsername);
		}
		
		show();
	}

	protected HLayout createCredenzialiLayout() {

		VLayout hSpacerLayout = new VLayout();
		hSpacerLayout.setWidth(20);

		formCredenzialiOTP = new DynamicForm();
		formCredenzialiOTP.setNumCols(10);
		formCredenzialiOTP.setCellPadding(5);
		formCredenzialiOTP.setWrapItemTitles(false);
		formCredenzialiOTP.setColWidths("20", "*");
		formCredenzialiOTP.setAlign(Alignment.CENTER);

		TextItem otpUsernameTextItem = new TextItem();
		otpUsernameTextItem.setName("otpUsername");
		otpUsernameTextItem.setTitle(setTitleAlign(I18NUtil.getMessages().hsmCredenzialiOTPWindow_usernameTextItem(), TITLE_WIDTH, false));
		otpUsernameTextItem.setWidth(200);
		otpUsernameTextItem.setStartRow(true);
		
		PasswordItem otpPasswordTextItem = new PasswordItem();
		otpPasswordTextItem.setName("otpPassword");
		otpPasswordTextItem.setTitle(setTitleAlign(I18NUtil.getMessages().hsmCredenzialiOTPWindow_passwordTextItem(), TITLE_WIDTH, false));
		otpPasswordTextItem.setWidth(200);
		otpPasswordTextItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		otpPasswordTextItem.setStartRow(true);
		
		formCredenzialiOTP.setItems(otpUsernameTextItem, otpPasswordTextItem);

		HLayout hLayout = new HLayout();
		hLayout.addMember(formCredenzialiOTP);

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
		okButton.setTitle(I18NUtil.getMessages().hsmCredenzialiOTPWindow_okButton());
		okButton.setWidth(100);
		okButton.setTop(20);
		okButton.setAlign(Alignment.CENTER);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				generazioneRemotaOtpButton("SMS");
			}
		});

		VLayout hSpacerLayout = new VLayout();
		hSpacerLayout.setWidth(30);

		Button annullaButton = new Button();
		annullaButton.setTitle(I18NUtil.getMessages().hsmCredenzialiOTPWindow_annullaButton());
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
		buttonConfermaLayout.addMember(hSpacerLayout);
		buttonConfermaLayout.addMember(annullaButton);

		VLayout layout = new VLayout();
		layout.setAlign(Alignment.CENTER);
		layout.setLayoutAlign(Alignment.CENTER);
		layout.setHoverAlign(Alignment.CENTER);
		layout.addMember(vSpacerLayout);
		layout.addMember(buttonConfermaLayout);

		return layout;
	}
	
	public String getOtpUsername() {
		return formCredenzialiOTP.getValueAsString("otpUsername");
	}
	
	public String getOtpPassword() {
		return formCredenzialiOTP.getValueAsString("otpPassword");
	}

	public abstract void generazioneRemotaOtpButton(String typeOpt);

	public abstract void onClickAnnullaButton();	
		
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}

}