/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility.OpenEditorCallback;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.ScanUtility;
import it.eng.auriga.ui.module.layout.client.ScanUtility.ScanCallback;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.StampaEtichettaPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.VisualizzaFatturaWindow;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaEtichettaUtil;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaTimbroBean;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaTimbroWindow;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.auriga.ui.module.layout.client.timbra.TimbraWindow;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroCopertinaUtil;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class AllegatoProcCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;

	protected HiddenItem displayNameOrigItem;
	protected HiddenItem idUdHiddenItem;
	protected HiddenItem idDocHiddenItem;
	protected HiddenItem displayNameTifItem;
	protected HiddenItem uriTifItem;
	private TextItem displayNameItem;
	private HiddenItem uriItem;
	private NestedFormItem allegatoButtons;

	public NestedFormItem getAllegatoButtons() {
		return allegatoButtons;
	}

	private FileUploadItemWithFirmaAndMimeType uploadAllegatoItem;
	protected ImgButtonItem previewButton;
	protected ImgButtonItem previewEditButton;
	protected ImgButtonItem editButton;
	protected ImgButtonItem fileFirmatoDigButton;
	protected ImgButtonItem firmaNonValidaButton;
	protected ImgButtonItem fileMarcatoTempButton;
	protected ImgButtonItem altreOpButton;
	private HiddenItem infoFile;
	protected HiddenItem remoteUriItem;
	protected HiddenItem isChangedItem;

	boolean forceToShowElimina = false;

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setNumCols(9);
		mDynamicForm.setColWidths(80, 1, 1, 1, 1, 1, 1, "*", "*");

		displayNameItem = new TextItem("displayName");
		displayNameItem.setShowTitle(false);
		displayNameItem.setWidth(350);
		displayNameItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				if (displayNameItem.getValue() == null || ((String) displayNameItem.getValue()).trim().equals("")) {
					clickEliminaFile();
				} else if (mDynamicForm.getValueAsString("displayNameOrig") != null && !"".equals(mDynamicForm.getValueAsString("displayNameOrig"))
						&& !displayNameItem.getValue().equals(mDynamicForm.getValueAsString("displayNameOrig"))) {
					mDynamicForm.setValue("isChanged", true);
				}
				mDynamicForm.markForRedraw();
				allegatoButtons.markForRedraw();
			}
		});

		idUdHiddenItem = new HiddenItem("idUd");
		idDocHiddenItem = new HiddenItem("idDoc");
		uriItem = new HiddenItem("uri");
		displayNameOrigItem = new HiddenItem("displayNameOrig");
		displayNameTifItem = new HiddenItem("displayNameTif"); // NOME del Tif
		uriTifItem = new HiddenItem("uriTif"); // URI del Tif
		remoteUriItem = new HiddenItem("remoteUri");
		isChangedItem = new HiddenItem("isChanged");
		isChangedItem.setDefaultValue(false);

		uploadAllegatoItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				mDynamicForm.setValue("displayName", displayFileName);
				mDynamicForm.setValue("uri", uri);
				mDynamicForm.setValue("displayNameTif", "");
				mDynamicForm.setValue("uriTif", "");
				mDynamicForm.setValue("remoteUri", false);
				changedEventAfterUpload(displayFileName, uri);
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
				mDynamicForm.markForRedraw();
				allegatoButtons.markForRedraw();
				uploadAllegatoItem.getCanvas().redraw();
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				mDynamicForm.setValue("infoFile", info);
				String displayName = mDynamicForm.getValueAsString("displayName");
				String displayNameOrig = mDynamicForm.getValueAsString("displayNameOrig");
				if (precImpronta == null
						|| !precImpronta.equals(info.getImpronta())
						|| (displayName != null && !"".equals(displayName) && displayNameOrig != null && !"".equals(displayNameOrig) && !displayName
								.equals(displayNameOrig))) {
					mDynamicForm.setValue("isChanged", true);
				}
				if (info.isFirmato() && !info.isFirmaValida()) {
					GWTRestDataSource.printMessage(new MessageBean("Il file presenta una firma non valida alla data odierna", "", MessageType.WARNING));
				}
				mDynamicForm.markForRedraw();
				allegatoButtons.markForRedraw();
			}
		});		
		uploadAllegatoItem.setAttribute("nascosto", !showUpload());		
		uploadAllegatoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				uploadAllegatoItem.setAttribute("nascosto", !showUpload());				
				return showUpload();
			}
		});
		uploadAllegatoItem.setColSpan(1);

		allegatoButtons = new NestedFormItem("allegatoButtons");
		allegatoButtons.setWidth(10);
		allegatoButtons.setOverflow(Overflow.VISIBLE);
		allegatoButtons.setNumCols(7);
		allegatoButtons.setColWidths(16, 16, 16, 16, 16, 16, 16);

		previewButton = new ImgButtonItem("previewButton", "file/preview.png", I18NUtil.getMessages().protocollazione_detail_previewButton_prompt());
		previewButton.setAlwaysEnabled(true);
		previewButton.setColSpan(1);
		previewButton.setIconWidth(16);
		previewButton.setIconHeight(16);
		previewButton.setIconVAlign(VerticalAlignment.BOTTOM);
		previewButton.setAlign(Alignment.LEFT);
		previewButton.setWidth(16);

		previewButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				if (uriItem.getValue() != null && !uriItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
					return PreviewWindow.canBePreviewed(lInfoFileRecord);
				} else
					return false;
			}
		});

		previewButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickPreviewFile();
			}
		});

		previewEditButton = new ImgButtonItem("previewEditButton", "file/previewEdit.png", I18NUtil.getMessages()
				.protocollazione_detail_previewEditButton_prompt());
		previewEditButton.setAlwaysEnabled(true);
		previewEditButton.setColSpan(1);
		previewEditButton.setIconWidth(16);
		previewEditButton.setIconHeight(16);
		previewEditButton.setIconVAlign(VerticalAlignment.BOTTOM);
		previewEditButton.setAlign(Alignment.LEFT);
		previewEditButton.setWidth(16);
		previewEditButton.setRedrawOnChange(true);
		previewEditButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				if (uriItem.getValue() != null && !uriItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
				} else
					return false;
			}
		});
		previewEditButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickPreviewEditFile();
			}
		});

		editButton = new ImgButtonItem("editButton", "file/editDoc.png", "Modifica documento");
		editButton.setAlwaysEnabled(false);
		editButton.setColSpan(1);
		editButton.setIconWidth(16);
		editButton.setIconHeight(16);
		editButton.setIconVAlign(VerticalAlignment.BOTTOM);
		editButton.setAlign(Alignment.LEFT);
		editButton.setWidth(16);
		editButton.setRedrawOnChange(true);

		editButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (((AllegatiProcItem) getItem()).canBeEditedByApplet()) {
					if (uriItem.getValue() != null && !uriItem.getValue().equals("")) {
						if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_EDITING_FILE")) {
							InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
							String estensione = null;
							if (lInfoFileRecord.getCorrectFileName() != null && !lInfoFileRecord.getCorrectFileName().trim().equals("")) {
								estensione = lInfoFileRecord.getCorrectFileName().substring(lInfoFileRecord.getCorrectFileName().lastIndexOf(".") + 1);
							} else {
								estensione = displayNameItem.getValueAsString().substring(displayNameItem.getValueAsString().lastIndexOf(".") + 1);
							}
							for (String lString : Layout.getGenericConfig().getEditableExtension()) {
								if (lString.equals(estensione)) {
									return true;
								}
							}
							return false;
						} else
							return false;
					} else
						return false;
				} else
					return false;
			}
		});
		editButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickEditFile();
			}
		});

		fileFirmatoDigButton = new ImgButtonItem("fileFirmatoDigButton", "firma/firma.png", I18NUtil.getMessages()
				.protocollazione_detail_fileFirmatoDigButton_prompt());
		fileFirmatoDigButton.setAlwaysEnabled(true);
		fileFirmatoDigButton.setColSpan(1);
		fileFirmatoDigButton.setIconWidth(16);
		fileFirmatoDigButton.setIconHeight(16);
		fileFirmatoDigButton.setIconVAlign(VerticalAlignment.BOTTOM);
		fileFirmatoDigButton.setAlign(Alignment.LEFT);
		fileFirmatoDigButton.setWidth(16);
		fileFirmatoDigButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				if (uriItem.getValue() != null && !uriItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && lInfoFileRecord.isFirmaValida();
				} else
					return false;
			}
		});
		fileFirmatoDigButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(mDynamicForm.getValue("infoFile"));				
				generaFileFirmaAndPreview(lInfoFileRecord, uriItem.getValue().toString(), true);
			}
		});

		firmaNonValidaButton = new ImgButtonItem("firmaNonValidaButton", "firma/firmaNonValida.png", I18NUtil.getMessages()
				.protocollazione_detail_firmaNonValidaButton_prompt());
		firmaNonValidaButton.setAlwaysEnabled(true);
		firmaNonValidaButton.setColSpan(1);
		firmaNonValidaButton.setIconWidth(16);
		firmaNonValidaButton.setIconHeight(16);
		firmaNonValidaButton.setIconVAlign(VerticalAlignment.BOTTOM);
		firmaNonValidaButton.setAlign(Alignment.LEFT);
		firmaNonValidaButton.setWidth(16);
		firmaNonValidaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				if (uriItem.getValue() != null && !uriItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && !lInfoFileRecord.isFirmaValida();
				} else
					return false;
			}
		});
		firmaNonValidaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(mDynamicForm.getValue("infoFile"));				
				generaFileFirmaAndPreview(lInfoFileRecord, uriItem.getValue().toString(), true);
			}
		});
		
		fileMarcatoTempButton = new ImgButtonItem("fileMarcatoTempButton", "marca/marcaNonValida.png", I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt());
		fileMarcatoTempButton.setAlwaysEnabled(true);
		fileMarcatoTempButton.setColSpan(1);
		fileMarcatoTempButton.setIconWidth(16);
		fileMarcatoTempButton.setIconHeight(16);
		fileMarcatoTempButton.setIconVAlign(VerticalAlignment.BOTTOM);
		fileMarcatoTempButton.setAlign(Alignment.LEFT);
		fileMarcatoTempButton.setWidth(16);
		fileMarcatoTempButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriItem.getValue() != null && !uriItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
					if (lInfoFileRecord != null && lInfoFileRecord.getInfoFirmaMarca() != null && lInfoFileRecord.getInfoFirmaMarca().getDataOraMarcaTemporale() != null) {
						if (lInfoFileRecord.getInfoFirmaMarca().isMarcaTemporaleNonValida()) {
							fileMarcatoTempButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt());
							fileMarcatoTempButton.setIcon("marcaTemporale/marcaTemporaleNonValida.png");
							return true;
						} else {
							Date dataOraMarcaTemporale = lInfoFileRecord.getInfoFirmaMarca().getDataOraMarcaTemporale();
							DateTimeFormat display_datetime_format = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
							fileMarcatoTempButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaValida_prompt(display_datetime_format.format(dataOraMarcaTemporale)));
							fileMarcatoTempButton.setIcon("marcaTemporale/marcaTemporaleValida.png");
							return true;
						}
					} else {
						return false;
					}
				}
				return false;
			}
		});

		altreOpButton = new ImgButtonItem("altreOpButton", "buttons/altreOp.png", I18NUtil.getMessages().altreOpButton_prompt());
		altreOpButton.setAlwaysEnabled(true);
		altreOpButton.setColSpan(1);
		altreOpButton.setIconWidth(16);
		altreOpButton.setIconHeight(16);
		altreOpButton.setIconVAlign(VerticalAlignment.BOTTOM);
		altreOpButton.setAlign(Alignment.LEFT);
		altreOpButton.setWidth(16);
		altreOpButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				final Menu altreOpMenu = new Menu();
				altreOpMenu.setOverflow(Overflow.VISIBLE);
				altreOpMenu.setShowIcons(true);
				altreOpMenu.setSelectionType(SelectionStyle.SINGLE);
				altreOpMenu.setCanDragRecordsOut(false);
				altreOpMenu.setWidth("*");
				altreOpMenu.setHeight("*");
				
				if(allegatoButtons.isEditing()) {
					MenuItem acquisisciDaScannerMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_acquisisciDaScannerMenuItem_prompt(),
							"file/scanner.png");
					acquisisciDaScannerMenuItem.setEnableIfCondition(getShowAquisisciDaScannerCondition());
					acquisisciDaScannerMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							clickAcquisisciDaScanner();
						}
					});
					altreOpMenu.addItem(acquisisciDaScannerMenuItem);
	
					MenuItem firmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_firmaMenuItem_prompt(), "file/mini_sign.png");
					firmaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
	
						@Override
						public boolean execute(Canvas target, Menu menu, MenuItem item) {
							
							return (uriItem.getValue() != null && !uriItem.getValue().equals(""));
						}
					});
					firmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							clickFirma();
						}
					});
					altreOpMenu.addItem(firmaMenuItem);
				}

				MenuItem downloadMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(), "file/download_manager.png");
				InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(mDynamicForm.getValue("infoFile"));
				if (lInfoFileRecord != null) {
					// Se è firmato P7M
					if (lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
						Menu showFirmatoAndSbustato = new Menu();
						MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
						firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFile();
							}
						});
						MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
						sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFileSbustato();
							}
						});
						showFirmatoAndSbustato.setItems(firmato, sbustato);
						downloadMenuItem.setSubmenu(showFirmatoAndSbustato);
					} else {
						downloadMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFile();
							}
						});
					}
				}
				downloadMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						
						return (uriItem.getValue() != null && !uriItem.getValue().equals(""));
					}
				});
				
				altreOpMenu.addItem(downloadMenuItem);
				
				
				//TIMBRA
//				if() {     TODO: timbro
					buildTimbraMenuItem(altreOpMenu, lInfoFileRecord);	
//				}				
					
					
				if (lInfoFileRecord != null && Layout.isPrivilegioAttivo("SCC")) {
					String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
					MenuItem timbroConformitaCustomMenuItem = new MenuItem(labelConformitaCustom, "file/copiaConformeCustom.png");
					timbroConformitaCustomMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
					timbroConformitaCustomMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									clickTimbra("COPIA_CONFORME_CUSTOM");
								}
							});

					altreOpMenu.addItem(timbroConformitaCustomMenuItem);

				}
				
				//Copertina con segnatura di registrazione
				MenuItem copertinaConSegnaturaRegistrazioneMenuItem = new MenuItem(AurigaLayout.getParametroDBAsBoolean("TIMBRO_PROFILO_DATI_SPEC_TIPO")
						? I18NUtil.getMessages().protocollazione_detail_copertinaConSegnatura()
						: I18NUtil.getMessages().protocollazione_detail_copertinaConTimbro(), "blank.png");
				copertinaConSegnaturaRegistrazioneMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						
						clickCopertinaConSegnaturaReg();
					}
				});
				copertinaConSegnaturaRegistrazioneMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
					
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						
						Record detailRecord = ((AllegatiProcItem) getItem()).getDetailRecord();
						String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
						if (idUd != null && !"".equals(idUd)) {
							return true;
						}
						return false;
					}
				});
				
				altreOpMenu.addItem(copertinaConSegnaturaRegistrazioneMenuItem);
				
				//Copertina con etichetta
				MenuItem copertinaConEtichettaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_copertinaConEtichetta(),
						"protocollazione/stampaEtichetta.png");
				copertinaConEtichettaMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						
						clickCopertinaConEtichetta();
					}
				});
				copertinaConEtichettaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
					
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						
						Record detailRecord = ((AllegatiProcItem) getItem()).getDetailRecord();
						String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
						if (idUd != null && !"".equals(idUd)) {
							return true;
						}
						return false;
					}
				});
				
				altreOpMenu.addItem(copertinaConEtichettaMenuItem);
				
				//Copertina con etichetta multipla
				MenuItem copertinaConEtichettaMultiplaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_copertinaConEtichettaMultipla(),
						"protocollazione/stampaEtichetta.png");
				copertinaConEtichettaMultiplaMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						//TODO
						clickCopertinaConEtichettaMultipla();
					}
				});
				copertinaConEtichettaMultiplaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
					
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						
						Record detailRecord = ((AllegatiProcItem) getItem()).getDetailRecord();
						String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
						if (idUd != null && !"".equals(idUd)) {
							return true;
						}
						return false;
					}
				});
				
				altreOpMenu.addItem(copertinaConEtichettaMultiplaMenuItem);
			
				// altreOpMenu.addItem(visualizzaInfoFirma);
				if (allegatoButtons.isEditing() || forceToShowElimina) {
					MenuItem eliminaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_eliminaMenuItem_prompt(), "file/editdelete.png");
					eliminaMenuItem.setEnableIfCondition(getShowElimina());
					eliminaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {

							clickEliminaFile();
						}
					});
					altreOpMenu.addItem(eliminaMenuItem);
				}
				
				
				altreOpMenu.showContextMenu();
			}
		});

		allegatoButtons.setNestedFormItems(previewButton, previewEditButton, editButton, fileFirmatoDigButton, firmaNonValidaButton, fileMarcatoTempButton, altreOpButton);

		infoFile = new HiddenItem("infoFile");
		infoFile.setVisible(false);

		mDynamicForm.setFields(idUdHiddenItem, idDocHiddenItem, displayNameOrigItem, displayNameItem, uploadAllegatoItem, uriItem, allegatoButtons, infoFile,
				remoteUriItem, isChangedItem);

		addChild(mDynamicForm);
	}
	
	
	protected void buildTimbraMenuItem(Menu altreOpMenu, InfoFileRecord lInfoFileRecord) {
		
		boolean flgAddSubMenu = false;
		
		MenuItem timbraMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraMenuItem(), "file/timbra.gif");
		Menu timbraSubMenu = new Menu();	
			
		MenuItem timbraDatiSegnaturaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraDatiSegnaturaMenuItem(), "file/timbra.gif");
		timbraDatiSegnaturaMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickTimbra("");
			}
		});
		timbraDatiSegnaturaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				
				if (uriItem.getValue() != null && !uriItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
				}
				return false;
			}
		});
		
		timbraSubMenu.addItem(timbraDatiSegnaturaMenuItem);
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
			if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null && 
					(lInfoFileRecord.getMimetype().equals("application/pdf") || lInfoFileRecord.getMimetype().startsWith("image"))) {
				MenuItem timbroConformitaOriginaleMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroConformitaOriginaleMenuItem(), "file/timbra.gif");
				timbroConformitaOriginaleMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						
						if (uriItem.getValue() != null && !uriItem.getValue().equals("")) {
							InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
							return lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
						}
						return false;
					}
				});
				timbroConformitaOriginaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickTimbra("CONFORMITA_ORIG_CARTACEO");
					}
				});
				
				flgAddSubMenu=true;
				timbraSubMenu.addItem(timbroConformitaOriginaleMenuItem);
			}
		}
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA") && lInfoFileRecord != null) {
			flgAddSubMenu=true;
			
			MenuItem timbroCopiaConformeMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem(), "file/timbra.gif");
			timbroCopiaConformeMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					
					if (uriItem.getValue() != null && !uriItem.getValue().equals("")) {
						InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
						return lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
					}
					return false;
				}
			});
			
			Menu sottoMenuCopiaConformeItem = new Menu();
			
			MenuItem timbroCopiaConformeStampaItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_stampa(), "file/timbra.gif");	
			timbroCopiaConformeStampaItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				@Override
				public void onClick(MenuItemClickEvent event) {
					clickTimbra("CONFORMITA_ORIG_DIGITALE_STAMPA");
				}
			});
			sottoMenuCopiaConformeItem.addItem(timbroCopiaConformeStampaItem);
			
			MenuItem timbroCopiaConformeDigitaleItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_suppDigitale(), "file/timbra.gif");	
			timbroCopiaConformeDigitaleItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				@Override
				public void onClick(MenuItemClickEvent event) {
					clickTimbra("CONFORMITA_ORIG_DIGITALE_SUPP_DIG");
				}
			});
			sottoMenuCopiaConformeItem.addItem(timbroCopiaConformeDigitaleItem);
			
			timbroCopiaConformeMenuItem.setSubmenu(sottoMenuCopiaConformeItem);			
			timbraSubMenu.addItem(timbroCopiaConformeMenuItem);
		}
		
		timbraMenuItem.setSubmenu(timbraSubMenu);
		
		//Se ho piu voci aggiungo il sottoMenu Timbra
		if(flgAddSubMenu) {
			altreOpMenu.addItem(timbraMenuItem);
			//Se ho un unica voce la aggiungo ad altreOperazioni con voce "Timbra"
		}else {
			timbraDatiSegnaturaMenuItem.setTitle("Timbra");
			altreOpMenu.addItem(timbraDatiSegnaturaMenuItem);
		}
	}

	protected MenuItemIfFunction getShowElimina() {
		
		return new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				
				// Se è valorizzato il file
				if (uriItem.getValue() != null) {
					// Se il file è vuoto
					if (uriItem.getValue().equals("")) {
						// Non mostro elimina
						return false;
					} else {
						return true;
					}
				} else
					return false;
			}
		};
	}

	protected MenuItemIfFunction getShowAquisisciDaScannerCondition() {
		return new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				
				return getShowAcquisisciDaScanner();
			}
		};
	}

	protected boolean getShowAcquisisciDaScanner() {
		return ((AllegatiProcItem) getItem()).showAcquisisciDaScanner();
	}

	protected boolean showUpload() {
		return ((AllegatiProcItem) getItem()).showUpload();
	}

	@Override
	public boolean validate() {
		mDynamicForm.setShowInlineErrors(true);
		Map<String, String> lMap = new HashMap<String, String>();
		if ((uriItem.getValue() == null || uriItem.getValue().equals(""))) {
			lMap.put("displayName", "File non valorizzato");
			mDynamicForm.setErrors(lMap);
			return false;
		}
		return true;
	}

	public void clickAcquisisciDaScanner() {
		ScanUtility.openScan(new ScanCallback() {

			@Override
			public void execute(String filePdf, String uriPdf, String fileTif, String uriTif, String record) {
				InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				mDynamicForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					mDynamicForm.setValue("isChanged", true);
				}
				mDynamicForm.setValue("displayName", filePdf);
				mDynamicForm.setValue("uri", uriPdf);
				mDynamicForm.setValue("displayNameTif", fileTif);
				mDynamicForm.setValue("uriTif", uriTif);
				mDynamicForm.setValue("remoteUri", false);
				mDynamicForm.markForRedraw();
				allegatoButtons.markForRedraw();
				changedEventAfterUpload(filePdf, uriPdf);
			}
		});
	}

	public void clickFirma() {
		String display = mDynamicForm.getValueAsString("displayName");
		String uri = mDynamicForm.getValueAsString("uri");
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
		// FirmaWindow firmaWindow = new FirmaWindow(uri, display, lInfoFileRecord) {
		// @Override
		// public void firmaCallBack(String nomeFileFirmato, String uriFileFirmato, String record) {
		// InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
		// String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
		// InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
		// mDynamicForm.setValue("infoFile", info);
		// if(precImpronta == null || !precImpronta.equals(info.getImpronta())) {
		// mDynamicForm.setValue("isChanged", true);
		// }
		// mDynamicForm.setValue("displayName", nomeFileFirmato);
		// mDynamicForm.setValue("uri", uriFileFirmato);
		// mDynamicForm.setValue("displayNameTif", "");
		// mDynamicForm.setValue("uriTif", "");
		// mDynamicForm.setValue("remoteUri", false);
		// mDynamicForm.markForRedraw();
		// allegatoButtons.markForRedraw();
		// changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
		// }
		// };
		// firmaWindow.show();
		FirmaUtility.firmaMultipla(uri, display, lInfoFileRecord, new FirmaCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
				InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				mDynamicForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					mDynamicForm.setValue("isChanged", true);
				}
				mDynamicForm.setValue("displayName", nomeFileFirmato);
				mDynamicForm.setValue("uri", uriFileFirmato);
				mDynamicForm.setValue("displayNameTif", "");
				mDynamicForm.setValue("uriTif", "");
				mDynamicForm.setValue("remoteUri", false);
				mDynamicForm.markForRedraw();
				allegatoButtons.markForRedraw();
				changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
			}

		});
	}

	public void clickTimbra(String finalita) {
		String display = mDynamicForm.getValueAsString("displayName");
		String uri = mDynamicForm.getValueAsString("uri");
		String remote = mDynamicForm.getValueAsString("remoteUri");
		InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
		Record detailRecord = ((AllegatiProcItem) getItem()).getDetailRecord();
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDoc = mDynamicForm != null ? mDynamicForm.getValueAsString("idDoc") : null;
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina") != null ?
				AurigaLayout.getImpostazioneTimbro("tipoPagina") : "";
				
		/*Controllo introdotto per la nuova modalità di timbratura per i file firmati che devono saltare la scelta della preferenza*/
		boolean skipSceltaTimbratura = AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniTimbroSegnatura");
		boolean flgBustaPdfTimbratura = false;
		
		if(precInfo.isFirmato() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_BUSTA_PDF_FILE_FIRMATO") && !finalita.equalsIgnoreCase("CONFORMITA_ORIG_DIGITALE_STAMPA")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = true;
		}
		
		if(finalita.equalsIgnoreCase("COPIA_CONFORME_CUSTOM")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = false;
		}
		
		if(skipSceltaTimbratura){
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			record.setAttribute("nomeFile", display);
			record.setAttribute("uri", uri);
			record.setAttribute("remote", remote);
			record.setAttribute("mimetype", precInfo.getMimetype());
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			if (finalita.equals("CONFORMITA_ORIG_CARTACEO")) {
				record.setAttribute("tipoPagina", "tutte");
			}else {
				record.setAttribute("tipoPagina", tipoPaginaPref);
			}
			record.setAttribute("finalita", finalita);
					
			if(flgBustaPdfTimbratura) {
				TimbroUtil.callStoreLoadFilePerTimbroConBusta(record);
			}else {
				TimbroUtil.buildDatiSegnatura(record);
			}
		}else{
					
			FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remote), 
					precInfo.getMimetype(), idUd, idDoc, rotazioneTimbroPref,posizioneTimbroPref);
			lFileDaTimbrareBean.setAttribute("finalita", finalita);
			lFileDaTimbrareBean.setAttribute("tipoPagina", tipoPaginaPref);
			lFileDaTimbrareBean.setAttribute("skipScelteOpzioniCopertina", "false");
			TimbraWindow lTimbraWindow = new TimbraWindow("timbra", true, lFileDaTimbrareBean);
			lTimbraWindow.show();
		}
	}
	
	public void clickCopertinaConSegnaturaReg() {
		
		Record detailRecord = ((AllegatiProcItem) getItem()).getDetailRecord();
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String numeroAllegato = mDynamicForm.getValueAsString("numeroProgrAllegato");//TODO MANCA IL NUMERO ALLEGATO
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		
		if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
			
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("numeroAllegato", numeroAllegato);
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			
			TimbroCopertinaUtil.buildDatiSegnatura(record);
			
		}else{
			
			Record copertinaTimbroRecord = new Record();
			copertinaTimbroRecord.setAttribute("idUd", idUd);
			copertinaTimbroRecord.setAttribute("numeroAllegato", numeroAllegato);
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "");
			copertinaTimbroRecord.setAttribute("flgDocFolder", "D");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
			
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean);
			copertinaTimbroWindow.show();
		}

	}

	public void clickPreviewFile() {
		final Record detailRecord = ((AllegatiProcItem) getItem()).getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDoc = mDynamicForm.getValueAsString("idDoc");
		addToRecent(idUd, idDoc);
		final String display = mDynamicForm.getValueAsString("displayName");
		final String uri = mDynamicForm.getValueAsString("uri");
		final Boolean remoteUri = Boolean.valueOf(mDynamicForm.getValueAsString("remoteUri"));
		final InfoFileRecord info = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute("remoteUri", remoteUri);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("VisualizzaFatturaDataSource");
			if (info != null && info.isFirmato() && info.getTipoFirma().startsWith("CAdES")) {
				lGwtRestService.addParam("sbustato", "true");
			} else {
				lGwtRestService.addParam("sbustato", "false");
			}
			lGwtRestService.call(lRecordFattura, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					
					if (object.getAttribute("html") != null && !"".equals(object.getAttribute("html"))) {
						VisualizzaFatturaWindow lVisualizzaFatturaWindow = new VisualizzaFatturaWindow(display, object);
						lVisualizzaFatturaWindow.show();
					} else {
						preview(detailRecord, idUd, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			preview(detailRecord, idUd, display, uri, remoteUri, info);
		}
	}

	public void preview(final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display);
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, remoteUri, info, "FileToExtractBean");
	}

	public void clickPreviewEditFile() {
		final Record detailRecord = ((AllegatiProcItem) getItem()).getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDoc = mDynamicForm.getValueAsString("idDoc");
		addToRecent(idUd, idDoc);
		final String display = mDynamicForm.getValueAsString("displayName");
		final String uri = mDynamicForm.getValueAsString("uri");
		final Boolean remoteUri = Boolean.valueOf(mDynamicForm.getValueAsString("remoteUri"));
		final InfoFileRecord info = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute("remoteUri", remoteUri);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("VisualizzaFatturaDataSource");
			if (info != null && info.isFirmato() && info.getTipoFirma().startsWith("CAdES")) {
				lGwtRestService.addParam("sbustato", "true");
			} else {
				lGwtRestService.addParam("sbustato", "false");
			}
			lGwtRestService.call(lRecordFattura, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					
					if (object.getAttribute("html") != null && !"".equals(object.getAttribute("html"))) {
						VisualizzaFatturaWindow lVisualizzaFatturaWindow = new VisualizzaFatturaWindow(display, object);
						lVisualizzaFatturaWindow.show();
					} else {
						previewWithJPedal(detailRecord, idUd, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			previewWithJPedal(detailRecord, idUd, display, uri, remoteUri, info);
		}
	}

	protected void clickEditFile() {
		final Record detailRecord = ((AllegatiProcItem) getItem()).getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDoc = mDynamicForm.getValueAsString("idDoc");
		addToRecent(idUd, idDoc);
		final String display = mDynamicForm.getValueAsString("displayName");
		final String uri = mDynamicForm.getValueAsString("uri");
		final Boolean remoteUri = Boolean.valueOf(mDynamicForm.getValueAsString("remoteUri"));
		final InfoFileRecord info = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
		String estensione = null;
		if (info.getCorrectFileName() != null && !info.getCorrectFileName().trim().equals("")) {
			estensione = info.getCorrectFileName().substring(info.getCorrectFileName().lastIndexOf(".") + 1);
		} else {
			estensione = display.substring(display.lastIndexOf(".") + 1);
		}
		String impronta = info.getImpronta();
		OpenEditorUtility.openEditor(display, uri, remoteUri, estensione, impronta, new OpenEditorCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, String record) {
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				mDynamicForm.setValue("infoFile", info);
				mDynamicForm.setValue("isChanged", true);
				mDynamicForm.setValue("displayName", nomeFileFirmato);
				mDynamicForm.setValue("uri", uriFileFirmato);
				mDynamicForm.setValue("displayNameTif", "");
				mDynamicForm.setValue("uriTif", "");
				mDynamicForm.setValue("remoteUri", false);
				mDynamicForm.markForRedraw();
				allegatoButtons.markForRedraw();
				changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
			}
		});

	}

	public void previewWithJPedal(final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), info.getMimetype(), idUd,
				rotazioneTimbroPref,posizioneTimbroPref);
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("LoadTimbraDefault");
		lGwtRestService.call(lFileDaTimbrareBean, new ServiceCallback<Record>() {

			@Override
			public void execute(Record lOpzioniTimbro) {
				boolean timbroEnabled = detailRecord != null && detailRecord.getAttribute("flgTipoProv") != null
						&& !"".equals(detailRecord.getAttribute("flgTipoProv")) && detailRecord.getAttribute("idUd") != null
						&& !"".equals(detailRecord.getAttribute("idUd"));
				PreviewDocWindow previewDocWindow = new PreviewDocWindow(uri, display, remoteUri, timbroEnabled, lOpzioniTimbro) {

					@Override
					public void uploadCallBack(String filePdf, String uriPdf, String record) {
						InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
						String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
						InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
						mDynamicForm.setValue("infoFile", info);
						if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
							mDynamicForm.setValue("isChanged", true);
						}
						mDynamicForm.setValue("displayName", filePdf);
						mDynamicForm.setValue("uri", uriPdf);
						mDynamicForm.setValue("displayNameTif", "");
						mDynamicForm.setValue("uriTif", "");
						mDynamicForm.setValue("remoteUri", false);
						mDynamicForm.markForRedraw();
						allegatoButtons.markForRedraw();
						changedEventAfterUpload(filePdf, uriPdf);
					}
				};
				previewDocWindow.show();
			}
		});
	}

	// Download del file
	protected void clickDownloadFile() {
		Record detailRecord = ((AllegatiProcItem) getItem()).getDetailRecord();
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDoc = mDynamicForm.getValueAsString("idDoc");
		addToRecent(idUd, idDoc);
		String display = mDynamicForm.getValueAsString("displayName");
		String uri = mDynamicForm.getValueAsString("uri");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", mDynamicForm.getValueAsString("remoteUri"));
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// Download del file firmato sbustato
	public void clickDownloadFileSbustato() {
		Record detailRecord = ((AllegatiProcItem) getItem()).getDetailRecord();
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDoc = mDynamicForm.getValueAsString("idDoc");
		addToRecent(idUd, idDoc);
		String display = mDynamicForm.getValueAsString("displayName");
		String uri = mDynamicForm.getValueAsString("uri");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", mDynamicForm.getValueAsString("remoteUri"));
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// Cancellazione del file
	protected void clickEliminaFile() {
		mDynamicForm.setValue("displayName", "");
		mDynamicForm.setValue("uri", "");
		mDynamicForm.setValue("displayNameTif", "");
		mDynamicForm.setValue("uriTif", "");
		mDynamicForm.clearValue("infoFile");
		mDynamicForm.markForRedraw();
		allegatoButtons.markForRedraw();
		uploadAllegatoItem.getCanvas().redraw();
	}

	public void addToRecent(String idUd, String idDoc) {
		if (idUd != null && !"".equals(idUd) && idDoc != null && !"".equals(idDoc)) {
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AddToRecentDataSource");
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

				}
			});
		}
	}
	
	public void clickCopertinaConEtichetta() {
		
		Record detailRecord = ((AllegatiProcItem) getItem()).getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		final String numeroAllegato = mDynamicForm.getValueAsString("numeroProgrAllegato");//TODO MANCA IL NUMERO ALLEGATO
		
		final Record recordToPrint = new Record();
		recordToPrint.setAttribute("idUd", idUd);
		recordToPrint.setAttribute("numeroAllegato", numeroAllegato);
		recordToPrint.setAttribute("nomeStampantePred", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
	
		/**
		 * Se non è presente la stampante per le etichette predefinita, allora propone la scelta
		 */
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null
				&& !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
			CopertinaEtichettaUtil.stampaEtichettaDatiSegnatura(recordToPrint);
		}else{
			PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {
				
				@Override
				public void execute(String nomeStampante) {
				
					CopertinaEtichettaUtil.stampaEtichettaDatiSegnatura(recordToPrint);
				}
			}, null);
		}
	}
	
	//Copertina con etichetta
	public void clickCopertinaConEtichettaMultipla() {
				
		Record detailRecord = ((AllegatiProcItem) getItem()).getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
			
			
		final Record record = new Record();
		record.setAttribute("idUd", idUd);
		record.setAttribute("isMultiple", "true");
		/**
			* Se non è presente la stampante per le etichette predefinita, allora propone la scelta
		*/
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null
				&& !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
				
			record.setAttribute("nomeStampante", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
				
			StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(record);
			stampaEtichettaPopup.show();
		}else{
			PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {
						
				@Override
				public void execute(String nomeStampante) {
				
					record.setAttribute("nomeStampante", nomeStampante);
						
					StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(record);
					stampaEtichettaPopup.show();
				}
			}, null);
		}
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

	public void changedEventAfterUpload(final String displayFileName, final String uri) {
		ChangedEvent lChangedEventDisplay = new ChangedEvent(displayNameItem.getJsObj()) {

			public DynamicForm getForm() {
				return mDynamicForm;
			};

			@Override
			public FormItem getItem() {
				return displayNameItem;
			}

			@Override
			public Object getValue() {
				
				return displayFileName;
			}
		};
		ChangedEvent lChangedEventUri = new ChangedEvent(uriItem.getJsObj()) {

			public DynamicForm getForm() {
				return mDynamicForm;
			};

			@Override
			public FormItem getItem() {
				return uriItem;
			}

			@Override
			public Object getValue() {
				return uri;
			}
		};
		displayNameItem.fireEvent(lChangedEventDisplay);
		uriItem.fireEvent(lChangedEventUri);
	}

	@Override
	public void editRecord(Record record) {
		
		record.setAttribute("displayNameOrig", record.getAttribute("displayName"));
		super.editRecord(record);
	}
	
	public void generaFileFirmaAndPreview(final InfoFileRecord infoFile, String uriFile, boolean aggiungiFirma) {
		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFile);
		record.setAttribute("uriAttach", uriFile);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.extraparam.put("aggiungiFirma", Boolean.toString(aggiungiFirma));
		if(((AllegatiProcItem) getItem()).getDataRifValiditaFirma() != null) {
			String dataRiferimento = DateUtil.format(((AllegatiProcItem) getItem()).getDataRifValiditaFirma());
			lGwtRestDataSource.extraparam.put("dataRiferimento", dataRiferimento);
		}
		lGwtRestDataSource.executecustom("stampaFileCertificazione", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record data = response.getData()[0];
				InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
				String filename = infoFile.getCorrectFileName();
				String uri = data.getAttribute("tempFileOut");
				PreviewControl.switchPreview(uri, false, infoFile, "FileToExtractBean", filename);				
			}
		});
	}

}
