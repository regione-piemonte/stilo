/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.SavePreferenceAction;
import it.eng.utility.ui.module.layout.client.common.SavePreferenceWindow;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public abstract class FirmeEmailHtmlPopup extends Window {

	private FirmeEmailHtmlPopup window;

	protected SavePreferenceWindow saveFirmaEmailWindow;
	
	protected ToolStrip mainToolStrip;
	protected GWTRestDataSource firmeDS;
	protected SelectItem firmeSelectItem;
	protected ListGrid firmePickListProperties;
	private FileUploadItemWithFirmaAndMimeType uploadFileItem;

	private DynamicForm form;
	private RichTextItem firmaEmailHtmlItem;
	
	private DynamicForm form2;
	private CheckboxItem flgPredefinitaItem;
	private CheckboxItem flgAutoNuovaItem;
	private CheckboxItem flgAutoRispostaItem;
	private CheckboxItem flgAutoInoltroItem;

	public FirmeEmailHtmlPopup() {

		window = this;

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

		setTitle(I18NUtil.getMessages().configUtenteMenuFirmeEmail_title());
		setHeaderIcon("menu/firma_email.png");

		setAutoCenter(true);
		setHeight(200);
		setWidth(600);
		
		firmeDS = UserInterfaceFactory.getPreferenceDataSource();
		firmeDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + "signature.email");
		
		saveFirmaEmailWindow = new SavePreferenceWindow("Salva firma in calce", firmeDS, false,
				new SavePreferenceAction() {

					@Override
					public void execute(final String value) {
						if (value != null && !value.equals("")) {														
							Record record = new Record();
							record.setAttribute("firmaEmailHtml", form.getValueAsString("firmaEmailHtml"));
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaLoadDettaglioEmailDataSource", "idEmail", FieldType.TEXT);
							lGwtRestDataSource.performCustomOperation("preparaFirmaEmailHtml", record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										saveFirmaEmail(firmeDS, value, response.getData()[0].getAttributeAsString("firmaEmailHtml"), 
												form2.getValue("flgPredefinita") != null ? (Boolean) form2.getValue("flgPredefinita") : false,
														form2.getValue("flgAutoNuova") != null ? (Boolean) form2.getValue("flgAutoNuova") : false, 
																form2.getValue("flgAutoRisposta") != null ? (Boolean) form2.getValue("flgAutoRisposta") : false, 
																		form2.getValue("flgAutoInoltro") != null ? (Boolean) form2.getValue("flgAutoInoltro") : false, 
																				new ServiceCallback<String>() {
																				
											@Override
											public void execute(String prefName) {
												firmeSelectItem.setValue(value);
											}
										});																
									}
								}
							}, new DSRequest());							
						}
					}
				});
		
		firmeSelectItem = new SelectItem("prefName");
		firmeSelectItem.setValueField("prefName");
		firmeSelectItem.setDisplayField("prefName");
		firmeSelectItem.setTitle("Firma");
		firmeSelectItem.setCanEdit(true);
		firmeSelectItem.setWidth(300);
		firmeSelectItem.setOptionDataSource(firmeDS);
		firmeSelectItem.setAllowEmptyValue(true);
		firmeSelectItem.setAutoFetchData(false);
		firmeSelectItem.setFetchMissingValues(true);
		firmeSelectItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return (String) firmeSelectItem.getValue();
			}
		});

		ListGridField firmePrefNameField = new ListGridField("prefName");
		ListGridField firmeRemoveField = new ListGridField("remove");
		firmeRemoveField.setType(ListGridFieldType.ICON);
		firmeRemoveField.setWidth(18);
		firmeRemoveField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("prefName") == null || "".equals(record.getAttributeAsString("prefName"))) {
					return null;
				} else {
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				}
			}
		});
		firmeRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(final RecordClickEvent event) {
				
				SC.ask(I18NUtil.getMessages().gestioneModelli_cancellazione_ask(), new BooleanCallback() {
					
					@Override
					public void execute(Boolean value) {
						if(value) {
							final String prefName = event.getRecord().getAttribute("prefName");
							firmeDS.removeData(event.getRecord(), new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									String value = (String) firmeSelectItem.getValue();
									if (prefName != null && value != null && prefName.equals(value)) {
										firmeSelectItem.setValue((String) null);
										firmaEmailHtmlItem.setValue((String) null);
										flgPredefinitaItem.setValue(false);
										flgAutoNuovaItem.setValue(false);
										flgAutoRispostaItem.setValue(false);
										flgAutoInoltroItem.setValue(false);
									}
									AurigaLayout.getFirmeEmailHtml().remove(prefName);
								}
							});
						}
					}
				});
			}
		});
		firmeSelectItem.setPickListFields(firmeRemoveField, firmePrefNameField);
		
		firmePickListProperties = new ListGrid();
		firmePickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		firmePickListProperties.setShowHeader(false);
		// firmePickListProperties.setCanRemoveRecords(true);
		firmePickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				final String preferenceName = event.getRecord().getAttribute("prefName");
				if (preferenceName != null && !"".equals(preferenceName)) {
					AdvancedCriteria criteria = new AdvancedCriteria();
					criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName);
					firmeDS.fetchData(criteria, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							if (data.length != 0) {
								Record record = data[0];
								firmaEmailHtmlItem.setValue(record.getAttributeAsString("value"));
								flgPredefinitaItem.setValue(AurigaLayout.getFirmaEmailPredefinita() != null && AurigaLayout.getFirmaEmailPredefinita().equals(preferenceName));
								flgAutoNuovaItem.setValue(AurigaLayout.getFirmaEmailAutoNuova() != null && AurigaLayout.getFirmaEmailAutoNuova().equals(preferenceName));
								flgAutoRispostaItem.setValue(AurigaLayout.getFirmaEmailAutoRisposta() != null && AurigaLayout.getFirmaEmailAutoRisposta().equals(preferenceName));
								flgAutoInoltroItem.setValue(AurigaLayout.getFirmaEmailAutoInoltro() != null && AurigaLayout.getFirmaEmailAutoInoltro().equals(preferenceName));											
							}
						}
					});
				}
			}
		});
		firmeSelectItem.setPickListProperties(firmePickListProperties);
		
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
		
		mainToolStrip = new ToolStrip();
		mainToolStrip.setBackgroundColor("transparent");
		mainToolStrip.setBackgroundImage("blank.png");
		mainToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		mainToolStrip.setBorder("0px");
		mainToolStrip.setWidth100();
		mainToolStrip.setHeight(20);
		mainToolStrip.setRedrawOnResize(true);
		mainToolStrip.addFormItem(firmeSelectItem);
		mainToolStrip.addSpacer(50);
		mainToolStrip.addFormItem(uploadFileTitleItem);
		mainToolStrip.addFormItem(uploadFileItem);
				
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
		firmaEmailHtmlItem.setStartRow(true);
		firmaEmailHtmlItem.setControlGroups("fontControls", "formatControls", "styleControls", "colorControls", "insertControls");

		form.setFields(firmaEmailHtmlItem);

		form2 = new DynamicForm();
		form2.setKeepInParentRect(true);
		form2.setWidth100();
		form2.setHeight(20);
		form2.setNumCols(4);
		form2.setColWidths(10, 10, 10, "*");
		form2.setMargin(5);
		form2.setTop(-5);

		flgPredefinitaItem = new CheckboxItem("flgPredefinita", "firma veloce");
		flgPredefinitaItem.setStartRow(true);
		flgPredefinitaItem.setColSpan(1);

		flgAutoNuovaItem = new CheckboxItem("flgAutoNuova", "in automatico per nuove mail");
		flgAutoNuovaItem.setStartRow(true);
		flgAutoNuovaItem.setColSpan(1);

		flgAutoRispostaItem = new CheckboxItem("flgAutoRisposta", "in automatico nelle risposte");
		flgAutoRispostaItem.setStartRow(true);
		flgAutoRispostaItem.setColSpan(1);

		flgAutoInoltroItem = new CheckboxItem("flgAutoInoltro", "in automatico negli inoltri");
		flgAutoInoltroItem.setStartRow(true);
		flgAutoInoltroItem.setColSpan(1);
		
		form2.setFields(flgPredefinitaItem, flgAutoNuovaItem, flgAutoRispostaItem, flgAutoInoltroItem);
		
		/*
		 * Inserimento di un pulsante per eseguire la cancellazione effettiva del corpo della mail.
		 * Ci sono situazioni in cui, anche se viene cancellata la firma visibile, rimangono dei tag HTML
		 * che comportano un'errata visualizzazione.
		 * Questo pulsante resetta completamente quanto presente nel text item
		 */
		Button deleteButton = new Button(I18NUtil.getMessages().cancella_firma_button_prompt());
		deleteButton.setIcon("buttons/delete.png");
		deleteButton.setIconSize(16);
		deleteButton.setAutoFit(false);
		deleteButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				firmaEmailHtmlItem.setValue("");
			}
		});

		Button confermaButton = new Button(I18NUtil.getMessages().saveButton_prompt()); 
		confermaButton.setIcon("buttons/save.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
//				if (validate()) {
					if ((!saveFirmaEmailWindow.isDrawn()) || (!saveFirmaEmailWindow.isVisible())) {
						saveFirmaEmailWindow.getForm().clearValues();
						saveFirmaEmailWindow.getForm().setValue((String) firmeSelectItem.getValue());
						saveFirmaEmailWindow.redraw();
						saveFirmaEmailWindow.show();
					}
//				}
			}
		});

		// Button annullaButton = new Button("Annulla");
		// annullaButton.setIcon("annulla.png");
		// annullaButton.setIconSize(16);
		// annullaButton.setAutoFit(false);
		// annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
		// @Override
		// public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
		// window.markForDestroy();
		// }
		// });

		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		
		_buttons.addMember(confermaButton);
		_buttons.addMember(deleteButton);
		
		// _buttons.addMember(annullaButton);
		
		_buttons.setAutoDraw(false);

		lVLayout.setMembers(mainToolStrip, form, form2, _buttons);

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
	
//	public boolean validate() {		
//		if(firmeSelectItem.getValueAsString() == null || "".equals(firmeSelectItem.getValueAsString().trim())) {
//			AurigaLayout.addMessage(new MessageBean("Selezionare o digitare il nome della firma da salvare", "", MessageType.ERROR));
//			return false;
//		}
//		return true;
//	}
	
	public abstract void saveFirmaEmail(GWTRestDataSource prefDS, String prefName, String firmaEmailHtml, Boolean flgPredefinita, Boolean flgAutoNuova, Boolean flgAutoRisposta, Boolean flgAutoInoltro, ServiceCallback<String> callback);

	@Override
	protected void onDestroy() {		
		if(saveFirmaEmailWindow != null) {
			saveFirmaEmailWindow.destroy();
		}
		if(firmeDS != null) {
			firmeDS.destroy();
		}
		super.onDestroy();
	}
	
}
