/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.SavePreferenceAction;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public class FirmaEmailHtmlPopup extends Window {

	private FirmaEmailHtmlPopup window;

	private SavePreferenceAction savePreferenceAction;

	private DynamicForm form;
	private RichTextItem firmaEmailHtmlItem;

	private DynamicForm formUpload;
	private FileUploadItemWithFirmaAndMimeType uploadFileItem;

	public FirmaEmailHtmlPopup(String value, SavePreferenceAction action) {

		window = this;

		this.savePreferenceAction = action;

		setIsModal(true);
		setModalMaskOpacity(50);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);

		setTitle(I18NUtil.getMessages().configUtenteMenuFirmaEmail_title());
		setHeaderIcon("menu/firma_email.png");

		setAutoCenter(true);
		setHeight(200);
		setWidth(600);

		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(2);
		form.setColWidths(10, "*");
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
		form.setBorder("1px solid grey");
		form.setMargin(5);

		firmaEmailHtmlItem = new RichTextItem();
		firmaEmailHtmlItem.setName("firmaEmailHtml");
		firmaEmailHtmlItem.setShowTitle(false);
		firmaEmailHtmlItem.setColSpan(2);
		firmaEmailHtmlItem.setWidth(580);
		firmaEmailHtmlItem.setHeight(180);
		firmaEmailHtmlItem.setAlign(Alignment.CENTER);
		firmaEmailHtmlItem.setValue(value);
		firmaEmailHtmlItem.setStartRow(true);
		firmaEmailHtmlItem.setControlGroups("fontControls", "formatControls", "styleControls", "colorControls", "insertControls");

		form.setFields(firmaEmailHtmlItem);

		formUpload = new DynamicForm();
		formUpload.setKeepInParentRect(true);
		formUpload.setWidth100();
		formUpload.setHeight(20);
		formUpload.setNumCols(4);
		formUpload.setColWidths(10, 10, 10, "*");
		formUpload.setMargin(5);
		formUpload.setTop(-5);

		TitleItem uploadFileTitleItem = new TitleItem("Inserisci un' immagine");
		uploadFileTitleItem.setColSpan(1);

		uploadFileItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				afterUploadImmagineFirmaEmailHtml(uri, displayFileName);
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
				uploadFileItem.getCanvas().redraw();
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				if (info.isFirmato() || info.getMimetype() == null || !info.getMimetype().toLowerCase().startsWith("image/")) {
					GWTRestDataSource.printMessage(new MessageBean("Il file non è un' immagine", "", MessageType.ERROR));
				}
			}
		});
		uploadFileItem.setColSpan(1);
		uploadFileItem.setWidth(16);

		formUpload.setFields(uploadFileTitleItem, uploadFileItem);

		Button confermaButton = new Button(I18NUtil.getMessages().saveButton_prompt());
		confermaButton.setIcon("buttons/save.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (form.validate()) {
					savePreferenceAction();
					window.markForDestroy();
				}
			}
		});

		// Button annullaButton = new Button("Annulla");
		// annullaButton.setIcon("annulla.png");
		// annullaButton.setIconSize(16);
		// annullaButton.setAutoFit(false);
		// annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
		// @Override
		// public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
		// 
		// window.markForDestroy();
		// }
		// });

		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);
		// _buttons.addMember(annullaButton);
		_buttons.setAutoDraw(false);

		lVLayout.setMembers(form, formUpload, _buttons);

		addItem(lVLayout);

		setShowTitle(true);
		setHeaderIcon("menu/firma_email.png");

		draw();
	}

	public void afterUploadImmagineFirmaEmailHtml(String uri, String display) {
		String firmaEmailHtml = "<img src=\"" + display + ":" + uri + "\" />" + form.getValueAsString("firmaEmailHtml");
		Record record = new Record();
		record.setAttribute("firmaEmailHtml", firmaEmailHtml);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaLoadDettaglioEmailDataSource", "idEmail", FieldType.TEXT);
		lGwtRestDataSource.performCustomOperation("caricaLogoFirmaEmailHtml", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					form.setValue("firmaEmailHtml", response.getData()[0].getAttributeAsString("firmaEmailHtml"));
				}
			}
		}, new DSRequest());
	}

	public void savePreferenceAction() {
		Record record = new Record();
		record.setAttribute("firmaEmailHtml", form.getValueAsString("firmaEmailHtml"));
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaLoadDettaglioEmailDataSource", "idEmail", FieldType.TEXT);
		lGwtRestDataSource.performCustomOperation("preparaFirmaEmailHtml", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					savePreferenceAction.execute(response.getData()[0].getAttributeAsString("firmaEmailHtml"));
					hide();
				}
			}
		}, new DSRequest());
	}

}
