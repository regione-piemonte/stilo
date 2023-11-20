/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.StampaFileUtility;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewImageWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.VisualizzaFatturaWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class AttachmentCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;

	protected HiddenItem uriAttachItem;
	protected HiddenItem infoFileAttachItem;
	private HiddenItem fileNameAttachItem;

	protected HiddenItem fileNameAttachOrigItem;
	protected HiddenItem fileNameAttachTifItem;
	protected HiddenItem uriAttachTifItem;

	protected TextItem nomeFileTextItem;

	protected ImgButtonItem previewAllegatoItem;
	protected ImgButtonItem previewEditButton;
	protected ImgButtonItem stampaFileButtonItem;
	protected ImgButtonItem fileFirmatoDigButton;
	protected ImgButtonItem firmaNonValidaButton;
	protected ImgButtonItem fileMarcatoTempButton;
	protected ImgButtonItem trasformaInAllegatoButton;
	protected ImgButtonItem altreOpButton;

	public AttachmentCanvas(AttachmentReplicableItem item, HashMap<String, String> parameters) {
		super(item, parameters);
	}

	public AttachmentCanvas(AttachmentReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setNumCols(9);
		mDynamicForm.setColWidths(80, 1, 1, 1, 1, 1, 1, "*", "*");

		fileNameAttachItem = new HiddenItem("fileNameAttach");
		uriAttachItem = new HiddenItem("uriAttach");
		infoFileAttachItem = new HiddenItem("infoFileAttach");

		nomeFileTextItem = new TextItem();
		nomeFileTextItem.setEndRow(false);
		nomeFileTextItem.setShowTitle(false);
		nomeFileTextItem.setTextAlign(Alignment.LEFT);
		nomeFileTextItem.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		nomeFileTextItem.setWidth(510);
		nomeFileTextItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				item.setValue(fileNameAttachItem.getValue());
				return (fileNameAttachItem.getValue() != null && !fileNameAttachItem.getValue().equals(""));
			}
		});

		final SpacerItem spacerItemPreviewAllegato = new SpacerItem();
		spacerItemPreviewAllegato.setColSpan(1);
		spacerItemPreviewAllegato.setWidth(20);

		previewAllegatoItem = new ImgButtonItem("previewAllegato", "file/preview.png", "Preview");
		previewAllegatoItem.setShowTitle(false);
		previewAllegatoItem.setAlwaysEnabled(true);
		previewAllegatoItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickPreviewFile();

			}
		});
		previewAllegatoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				boolean toShowPreview = false;

				if (mDynamicForm.getValueAsString("uriAttach") != null && !mDynamicForm.getValueAsString("uriAttach").equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFileAttach"));
					toShowPreview = PreviewWindow.isToShowEml(lInfoFileRecord, mDynamicForm.getValueAsString("fileNameAttach"))
							|| PreviewWindow.canBePreviewed(lInfoFileRecord);
				}
				spacerItemPreviewAllegato.setVisible(!toShowPreview);
				return toShowPreview;
			}
		});

		stampaFileButtonItem = new ImgButtonItem("flgStampaFile", "postaElettronica/print_file.png", "");
		stampaFileButtonItem.setShowTitle(false);
		stampaFileButtonItem.setAlwaysEnabled(true);
		stampaFileButtonItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Stampa";
			}
		});
		stampaFileButtonItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
			
				Boolean isAbilShowPrint = ((AttachmentReplicableItem) getItem()).showStampaFileButton();
				boolean toShowPreview = false;
				if (mDynamicForm.getValueAsString("uriAttach") != null && !mDynamicForm.getValueAsString("uriAttach").equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFileAttach"));
					toShowPreview = !PreviewWindow.isToShowEml(lInfoFileRecord, mDynamicForm.getValueAsString("fileNameAttach"))
							&& PreviewWindow.canBePreviewed(lInfoFileRecord);
				}
				return isAbilShowPrint && toShowPreview;
			}
		});
		stampaFileButtonItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {

				clickStampaFile();
			}
		});

		fileFirmatoDigButton = new ImgButtonItem("fileFirmatoDigButton", "firma/firma.png",
				I18NUtil.getMessages().protocollazione_detail_fileFirmatoDigButton_prompt());
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
				if (uriAttachItem.getValue() != null && !uriAttachItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFileAttach"));
					return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && lInfoFileRecord.isFirmaValida();
				} else
					return false;
			}
		});
		fileFirmatoDigButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageFileFirmatoButtonClick();
			}
		});

		firmaNonValidaButton = new ImgButtonItem("firmaNonValidaButton", "firma/firmaNonValida.png",
				I18NUtil.getMessages().protocollazione_detail_firmaNonValidaButton_prompt());
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
				if (uriAttachItem.getValue() != null && !uriAttachItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFileAttach"));
					return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && !lInfoFileRecord.isFirmaValida();
				} else
					return false;
			}
		});
		firmaNonValidaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageFileFirmatoButtonClick();
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
				if (uriAttachItem.getValue() != null && !uriAttachItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFileAttach"));
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

				MenuItem downloadMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(), "file/download_manager.png");
				InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(mDynamicForm.getValue("infoFileAttach"));
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
						return (uriAttachItem.getValue() != null && !uriAttachItem.getValue().equals(""));
					}
				});

				//
				MenuItem firmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_firmaMenuItem_prompt(), "file/mini_sign.png");
				firmaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return (uriAttachItem.getValue() != null && !uriAttachItem.getValue().equals(""));
					}
				});
				firmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickFirma();
					}
				});

				final Menu altreOpMenu = new Menu();
				altreOpMenu.setOverflow(Overflow.VISIBLE);
				altreOpMenu.setShowIcons(true);
				altreOpMenu.setSelectionType(SelectionStyle.SINGLE);
				altreOpMenu.setCanDragRecordsOut(false);
				altreOpMenu.setWidth("*");
				altreOpMenu.setHeight("*");

				altreOpMenu.addItem(firmaMenuItem);
				altreOpMenu.addItem(downloadMenuItem);

				altreOpMenu.showContextMenu();

			}
		});

		mDynamicForm.setFields(infoFileAttachItem, fileNameAttachItem, uriAttachItem, nomeFileTextItem, previewAllegatoItem, stampaFileButtonItem,
				altreOpButton, fileFirmatoDigButton, firmaNonValidaButton, fileMarcatoTempButton);

		addChild(mDynamicForm);
		setHeight(26);
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		nomeFileTextItem.setCanEdit(false);
	}

	protected void manageDownloadClick() {
		String display = mDynamicForm.getValueAsString("fileNameAttach");
		String uri = mDynamicForm.getValueAsString("uriAttach");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", "false");
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

	protected void changedEventAfterUpload(final String displayFileName, final String uri) {
		ChangedEvent lChangedEventDisplay = new ChangedEvent(fileNameAttachItem.getJsObj()) {

			public DynamicForm getForm() {
				return mDynamicForm;
			};

			@Override
			public FormItem getItem() {
				return fileNameAttachItem;
			}

			@Override
			public Object getValue() {
				return displayFileName;
			}
		};
		ChangedEvent lChangedEventUri = new ChangedEvent(uriAttachItem.getJsObj()) {

			public DynamicForm getForm() {
				return mDynamicForm;
			};

			@Override
			public FormItem getItem() {
				return uriAttachItem;
			}

			@Override
			public Object getValue() {
				return uri;
			}
		};
		fileNameAttachItem.fireEvent(lChangedEventDisplay);
		uriAttachItem.fireEvent(lChangedEventUri);
	}

	// Preview del file
	public void clickPreviewFile() {
		String uri = mDynamicForm.getValueAsString("uriAttach");
		if (uri.equals("_noUri")) {
			((AttachmentReplicableItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList("listaAllegati").get(i);
					realClickPreviewFile(lRecord.getAttribute("uri"));
				}
			});
		} else {
			realClickPreviewFile(mDynamicForm.getValueAsString("uriAttach"));
		}

	}

	protected void realClickPreviewFile(final String uri) {
		final String display = mDynamicForm.getValueAsString("fileNameAttach");
		final Boolean remoteUri = true;
		final InfoFileRecord info = new InfoFileRecord(mDynamicForm.getValue("infoFileAttach"));

		if ((info != null && info.getMimetype() != null)) {
			if (info.getMimetype().equals("application/xml") && !"Segnatura.xml".equals(display) && !"segnatura.xml".equals(display)
					&& !"Conferma.xml".equals(display) && !"conferma.xml".equals(display) && !"eccezione.xml".equals(display)
					&& !"Eccezione.xml".equals(display) && !"Aggiornamento.xml".equals(display) && !"aggiornamento.xml".equals(display)
					&& !"annullamento.xml".equals(display) && !"Annullamento.xml".equals(display)) {
				Record lRecordFattura = new Record();
				lRecordFattura.setAttribute("uri", uri);
				lRecordFattura.setAttribute("uriFile", uri);
				lRecordFattura.setAttribute("remoteUri", true);
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
							preview(uri, display, remoteUri, info);
						}
					}
				});
			} else {
				preview(uri, display, remoteUri, info);
			}
		} else {
			SC.say("Non è possibile accedere alla preview del documento allegato");
		}
	}

	public void preview(final String uri, String display, Boolean remoteUri, InfoFileRecord info) {

		/**
		 * Gestione per la visualizzazione deli file di tipo image
		 */
		if (info != null && info.getMimetype() != null && info.getMimetype().startsWith("image") && !info.getMimetype().equals("image/tiff")) {
			new PreviewImageWindow(uri, remoteUri, info);
		} else {

			if (!Layout.getOpenedViewers().keySet().contains(uri)) {
				PreviewWindow lPreviewWindow = new PreviewWindow("", uri, remoteUri, info, "FileToExtractBean", display) {

					@Override
					public boolean isModal() {
						return false;
					}
				};
				lPreviewWindow.addCloseClickHandler(new CloseClickHandler() {

					@Override
					public void onCloseClick(CloseClickEvent event) {
						Layout.getOpenedViewers().remove(uri);
					}
				});
				Layout.getOpenedViewers().put(uri, lPreviewWindow);
			} else {
				Layout.getOpenedViewers().get(uri).bringToFront();
			}
		}
	}

	protected void manageFileFirmatoButtonClick() {

		final InfoFileRecord infoFileRecord = InfoFileRecord.buildInfoFileRecord(mDynamicForm.getValue("infoFileAttach"));
		final String uriFilePrimario = mDynamicForm.getValueAsString("uriAttach");

		MenuItem informazioniFirmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_dettaglioCertificazione_prompt(), "buttons/detail.png");
		informazioniFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				generaFileFirmaAndPreview(infoFileRecord, uriFilePrimario, false);
			}
		});

		MenuItem stampaFileCertificazioneMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_stampaFileCertificazione_prompt(),
				"protocollazione/stampaEtichetta.png");
		stampaFileCertificazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				generaFileFirmaAndPreview(infoFileRecord, uriFilePrimario, true);
			}
		});

		final Menu fileFirmatoMenu = new Menu();
		fileFirmatoMenu.setOverflow(Overflow.VISIBLE);
		fileFirmatoMenu.setShowIcons(true);
		fileFirmatoMenu.setSelectionType(SelectionStyle.SINGLE);
		fileFirmatoMenu.setCanDragRecordsOut(false);
		fileFirmatoMenu.setWidth("*");
		fileFirmatoMenu.setHeight("*");

		fileFirmatoMenu.addItem(informazioniFirmaMenuItem);
		fileFirmatoMenu.addItem(stampaFileCertificazioneMenuItem);

		fileFirmatoMenu.showContextMenu();
	}

	private void generaFileFirmaAndPreview(final InfoFileRecord infoFileAllegato, String uriFileAllegato, boolean aggiungiFirma) {

		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFileAllegato);
		record.setAttribute("uriAttach", uriFileAllegato);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.extraparam.put("aggiungiFirma", Boolean.toString(aggiungiFirma));
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

	public void clickFirma() {
		String display = mDynamicForm.getValueAsString("fileNameAttach");
		String uri = mDynamicForm.getValueAsString("uriAttach");
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFileAttach"));
		FirmaUtility.firmaMultipla(uri, display, lInfoFileRecord, new FirmaCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
				mDynamicForm.setValue("infoFileAttach", info);
				mDynamicForm.setValue("fileNameAttach", nomeFileFirmato);
				mDynamicForm.setValue("uriAttach", uriFileFirmato);
				mDynamicForm.markForRedraw();
				changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
			}
		});
	}

	// Download del file
	protected void clickDownloadFile() {
		String display = mDynamicForm.getValueAsString("fileNameAttach");
		String uri = mDynamicForm.getValueAsString("uriAttach");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", "false");
		if (mDynamicForm.getValue("infoFileAttach") != null) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFileAttach"));
			lRecord.setAttribute("infoFile", lInfoFileRecord);
		}
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// Download del file firmato sbustato
	protected void clickDownloadFileSbustato() {
		String display = mDynamicForm.getValueAsString("fileNameAttach");
		String uri = mDynamicForm.getValueAsString("uriAttach");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", "false");
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFileAttach"));
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		lRecord.setAttribute("infoFile", lInfoFileRecord);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// Preview del file
	public void clickStampaFile() {
		String uri = mDynamicForm.getValueAsString("uriAttach");
		if (uri.equals("_noUri")) {
			((AttachmentReplicableItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList("listaAllegati").get(i);
					realClickPreviewFile(lRecord.getAttribute("uri"));
				}
			});
		} else {
			manageStampaFile(mDynamicForm.getValueAsString("uriAttach"));
		}
	}

	protected void manageStampaFile(final String uri) {
		final String display = mDynamicForm.getValueAsString("fileNameAttach");
		final Boolean remoteUri = true;
		final InfoFileRecord info = new InfoFileRecord(mDynamicForm.getValue("infoFileAttach"));

		if ((info != null && info.getMimetype() != null)) {
			if (info.getMimetype().equals("application/xml") && !"Segnatura.xml".equals(display) && !"segnatura.xml".equals(display)
					&& !"Conferma.xml".equals(display) && !"conferma.xml".equals(display) && !"eccezione.xml".equals(display)
					&& !"Eccezione.xml".equals(display) && !"Aggiornamento.xml".equals(display) && !"aggiornamento.xml".equals(display)
					&& !"annullamento.xml".equals(display) && !"Annullamento.xml".equals(display)) {
				Record lRecordFattura = new Record();
				lRecordFattura.setAttribute("uri", uri);
				lRecordFattura.setAttribute("uriFile", uri);
				lRecordFattura.setAttribute("remoteUri", true);
				GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("VisualizzaFatturaDataSource");
				if (info != null && info.isFirmato() && info.getTipoFirma().startsWith("CAdES")) {
					lGwtRestService.addParam("sbustato", "true");
				} else {
					lGwtRestService.addParam("sbustato", "false");
				}
				lGwtRestService.call(lRecordFattura, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {

						stampaFile(uri, display, remoteUri, info);
					}
				});
			} else {
				stampaFile(uri, display, remoteUri, info);
			}
		} else {
			SC.say(I18NUtil.getMessages().posta_elettronica_replicable_canvas_errore_stampa_file());
		}
	}

	private void stampaFile(String uri, String nomeFile, Boolean remoteUri, InfoFileRecord info) {

		Record lRecord = new Record();
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("nomeFile", nomeFile);
		lRecord.setAttribute("infoFile", info);
		lRecord.setAttribute("remoteUri", remoteUri);

		RecordList listaAllegati = new RecordList();
		listaAllegati.add(lRecord);

		Record recordToPrint = new Record();
		recordToPrint.setAttribute("listaAllegati", listaAllegati);

		StampaFileUtility.stampaFile(recordToPrint, null);
	}

}
