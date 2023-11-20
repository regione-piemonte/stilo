/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.VisualizzaFatturaWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;

public class DettaglioEmailAllegatoCanvas extends ReplicableCanvas {

	private StaticTextItem nomeFileAllegatoItem;
	private HiddenItem uriFileAllegatoItem;
	protected ImgButtonItem previewButton;
	protected ImgButtonItem previewEditButton;
	protected ImgButtonItem fileFirmatoDigButton;
	// protected ImgButtonItem firmaNonValidaButton;
	protected ImgButtonItem regAssociataButton;
	protected ImgButtonItem downloadButton;
	private HiddenItem infoFileItem;
	private HiddenItem idProvRegItem;
	private HiddenItem estremiRegItem;
	private NestedFormItem allegatoButtons;
	private ReplicableCanvasForm mDynamicForm;

	public DettaglioEmailAllegatoCanvas(DettaglioEmailAllegatoItem dettaglioEmailAllegatoItem) {
		super(dettaglioEmailAllegatoItem);
	}

	public DettaglioEmailAllegatoCanvas(DettaglioEmailAllegatoItem item, HashMap<String, String> parameters) {
		super(item, parameters);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setNumCols(4);
		mDynamicForm.setColWidths(1, 1, 1, 1);

		nomeFileAllegatoItem = new StaticTextItem("nomeFileAllegato", I18NUtil.getMessages().protocollazione_detail_nomeFileAllegatoItem_title());
		nomeFileAllegatoItem.setWidth("*");
		nomeFileAllegatoItem.setWrap(false);
		nomeFileAllegatoItem.setEndRow(false);
		nomeFileAllegatoItem.setShowTitle(false);
		nomeFileAllegatoItem.setTextAlign(Alignment.LEFT);

		uriFileAllegatoItem = new HiddenItem("uriFileAllegato");

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
				
				if (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")) {
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

		previewEditButton = new ImgButtonItem("previewEditButton", "file/previewEdit.png",
				I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt());
		previewEditButton.setAlwaysEnabled(true);
		previewEditButton.setColSpan(1);
		previewEditButton.setIconWidth(16);
		previewEditButton.setIconHeight(16);
		previewEditButton.setIconVAlign(VerticalAlignment.BOTTOM);
		previewEditButton.setAlign(Alignment.LEFT);
		previewEditButton.setWidth(16);
		previewEditButton.setRedrawOnChange(true);
		// previewEditButton.setVisible(AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")? false : true);
		previewEditButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")
						&& (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""))) {
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
				
				if (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && lInfoFileRecord.isFirmaValida();
				} else
					return false;
			}
		});
		fileFirmatoDigButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				manageFirmatoClick(event);
			}
		});

		downloadButton = new ImgButtonItem("downloadButton", "file/download_manager.png", "Scarica");
		downloadButton.setAlwaysEnabled(true);
		downloadButton.setColSpan(1);
		downloadButton.setIconWidth(16);
		downloadButton.setIconHeight(16);
		downloadButton.setIconVAlign(VerticalAlignment.BOTTOM);
		downloadButton.setAlign(Alignment.LEFT);
		downloadButton.setWidth(16);
		downloadButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(mDynamicForm.getValue("infoFile"));
				if (lInfoFileRecord != null) {
					// Se è firmato P7M
					if (lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
						Menu showFirmatoAndSbustato = new Menu();
						MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt(), "blank.png");
						firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFile();
							}
						});
						MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt(), "blank.png");
						sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFileSbustato();
							}
						});
						showFirmatoAndSbustato.setItems(firmato, sbustato);
						showFirmatoAndSbustato.showContextMenu();
					} else {
						clickDownloadFile();
					}
				}
			}
		});
		downloadButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""));
			}
		});

		regAssociataButton = new ImgButtonItem("regAssociataButton", "postaElettronica/flgStatoProt/C.png", null);
		regAssociataButton.setAlwaysEnabled(true);
		regAssociataButton.setColSpan(1);
		regAssociataButton.setIconWidth(16);
		regAssociataButton.setIconHeight(16);
		regAssociataButton.setIconVAlign(VerticalAlignment.BOTTOM);
		regAssociataButton.setAlign(Alignment.LEFT);
		regAssociataButton.setWidth(16);
		regAssociataButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				regAssociataButton.setPrompt((String) estremiRegItem.getValue());
				return (idProvRegItem.getValue() != null && !idProvRegItem.getValue().equals(""));
			}
		});
		regAssociataButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				visualizzaRegAssociata();
			}
		});

		allegatoButtons = new NestedFormItem("allegatoButtons");
		allegatoButtons.setWidth(10);
		allegatoButtons.setOverflow(Overflow.VISIBLE);
		allegatoButtons.setNumCols(5);
		allegatoButtons.setColWidths(16, 16, 16, 16, 16);
		allegatoButtons.setNestedFormItems(previewButton, previewEditButton, fileFirmatoDigButton, downloadButton, regAssociataButton);

		infoFileItem = new HiddenItem("infoFile");

		idProvRegItem = new HiddenItem("idProvReg");

		estremiRegItem = new HiddenItem("estremiReg");

		mDynamicForm.setFields(nomeFileAllegatoItem, uriFileAllegatoItem, allegatoButtons, infoFileItem, idProvRegItem, estremiRegItem);

		addChild(mDynamicForm);
	}

	protected void visualizzaRegAssociata() {
		Record record = new Record();
		record.setAttribute("idUd", mDynamicForm.getValue("idProvReg"));
		DettaglioRegProtAssociatoWindow dettaglioRegProtAssociatoWindow = new DettaglioRegProtAssociatoWindow(record,
				"Dettaglio prot. associato " + mDynamicForm.getValue("estremiReg"));
	}

	protected void manageFirmatoClick(IconClickEvent event) {
		String uri = mDynamicForm.getValueAsString("uriFileAllegato");
		if (uri.equals("_noUri")) {
			((DettaglioEmailAllegatoItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList(((DettaglioEmailAllegatoItem) getItem()).getName()).get(i);
					realManageFirmatoClick(lRecord.getAttribute("uriFileAllegato"));
				}
			});
		} else {
			realManageFirmatoClick(mDynamicForm.getValueAsString("uriFileAllegato"));
		}
	}

	protected void realManageFirmatoClick(final String uri) {
		// String lDate = (String)getItem().getForm().getValuesManager().getValue("dateRif");
		final InfoFileRecord infoFileRecord = InfoFileRecord.buildInfoFileRecord(mDynamicForm.getValue("infoFile"));

		infoFileRecord.setCorrectFileName(mDynamicForm.getValueAsString("nomeFileAllegato"));

		MenuItem informazioniFirmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_dettaglioCertificazione_prompt(), "buttons/detail.png");
		informazioniFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				generaFileFirmaAndPreview(infoFileRecord, uri, false);
			}
		});

		MenuItem stampaFileCertificazioneMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_stampaFileCertificazione_prompt(),
				"protocollazione/stampaEtichetta.png");
		stampaFileCertificazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				generaFileFirmaAndPreview(infoFileRecord, uri, true);
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

	private void generaFileFirmaAndPreview(final InfoFileRecord infoFilePrimario, String uriFilePrimario, boolean aggiungiFirma) {

		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFilePrimario);
		record.setAttribute("uriAttach", uriFilePrimario);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.extraparam.put("aggiungiFirma", Boolean.toString(aggiungiFirma));

		String dataRiferimento = (String) getItem().getForm().getValuesManager().getValue("dateRif");

		if (dataRiferimento != null) {
			lGwtRestDataSource.extraparam.put("dataRiferimento", dataRiferimento);
		}

		lGwtRestDataSource.executecustom("stampaFileCertificazione", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record data = response.getData()[0];
				InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
				String filename = infoFile.getCorrectFileName();
				String uri = data.getAttribute("tempFileOut");
				// String url = (GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename) + "&url=" +
				// URL.encode(uri));
				preview(uri, filename, false, infoFile);

			}
		});

	}

	// Download del file
	protected void clickDownloadFile() {
		String uri = mDynamicForm.getValueAsString("uriFileAllegato");
		if (uri.equals("_noUri")) {
			((DettaglioEmailAllegatoItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList(((DettaglioEmailAllegatoItem) getItem()).getName()).get(i);
					realDownload(lRecord.getAttribute("uriFileAllegato"));
				}
			});
		} else {
			realDownload(mDynamicForm.getValueAsString("uriFileAllegato"));
		}

	}

	protected void realDownload(String uri) {
		String display = mDynamicForm.getValueAsString("nomeFileAllegato");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", mDynamicForm.getValueAsString("remoteUri"));
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// Download del file firmato sbustato
	public void clickDownloadFileSbustato() {
		String uri = mDynamicForm.getValueAsString("uriFileAllegato");
		if (uri.equals("_noUri")) {
			((DettaglioEmailAllegatoItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList(((DettaglioEmailAllegatoItem) getItem()).getName()).get(i);
					realDownloadSbustato(lRecord.getAttribute("uriFileAllegato"));
				}
			});
		} else {
			realDownloadSbustato(mDynamicForm.getValueAsString("uriFileAllegato"));
		}
	}

	protected void realDownloadSbustato(String uri) {
		String display = mDynamicForm.getValueAsString("nomeFileAllegato");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", mDynamicForm.getValueAsString("remoteUri"));
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// Preview del file
	public void clickPreviewFile() {

		String uri = mDynamicForm.getValueAsString("uriFileAllegato");
		if (uri.equals("_noUri")) {
			((DettaglioEmailAllegatoItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList(((DettaglioEmailAllegatoItem) getItem()).getName()).get(i);
					realClickPreviewFile(lRecord.getAttribute("uriFileAllegato"));
				}
			});
		} else {
			realClickPreviewFile(mDynamicForm.getValueAsString("uriFileAllegato"));
		}

	}

	protected void realClickPreviewFile(final String uri) {
		final String display = mDynamicForm.getValueAsString("nomeFileAllegato");
		final Boolean remoteUri = Boolean.valueOf(mDynamicForm.getValueAsString("remoteUri"));
		final InfoFileRecord info = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml") && !"Segnatura.xml".equals(display)
				&& !"Conferma.xml".equals(display) && !"Eccezione.xml".equals(display) && !"Aggiornamento.xml".equals(display)
				&& !"Annullamento.xml".equals(display)) {
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
						preview(uri, display, remoteUri, info);
					}
				}
			});
		} else {
			preview(uri, display, remoteUri, info);
		}

	}

	public void clickPreviewEditFile() {
		String uri = mDynamicForm.getValueAsString("uriFileAllegato");
		if (uri.equals("_noUri")) {
			((DettaglioEmailAllegatoItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList(((DettaglioEmailAllegatoItem) getItem()).getName()).get(i);
					realClickPreviewEditFile(lRecord.getAttribute("uriFileAllegato"));
				}
			});
		} else {
			realClickPreviewEditFile(mDynamicForm.getValueAsString("uriFileAllegato"));
		}

	}

	protected void realClickPreviewEditFile(final String uri) {
		final String display = mDynamicForm.getValueAsString("nomeFileAllegato");
		final Boolean remoteUri = Boolean.valueOf(mDynamicForm.getValueAsString("remoteUri"));
		InfoFileRecord info = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml") && !"Segnatura.xml".equals(display)
				&& !"Conferma.xml".equals(display) && !"Eccezione.xml".equals(display) && !"Aggiornamento.xml".equals(display)
				&& !"Annullamento.xml".equals(display)) {
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
						previewWithJPedal(uri, display, remoteUri);
					}
				}
			});
		} else {
			previewWithJPedal(uri, display, remoteUri);
		}
	}

	public void previewWithJPedal(String uri, String display, Boolean remoteUri) {
		PreviewDocWindow previewDocWindow = new PreviewDocWindow(uri, display, remoteUri) {

			@Override
			public void uploadCallBack(String filePdf, String uriPdf, String record) {
				mDynamicForm.setValue("infoFile", InfoFileRecord.buildInfoFileString(record));
				mDynamicForm.setValue("nomeFileAllegato", filePdf);
				mDynamicForm.setValue("uriFileAllegato", uriPdf);
				mDynamicForm.setValue("remoteUri", false);
			}
		};
		previewDocWindow.show();
	}

	public void preview(String uri, String display, Boolean remoteUri, InfoFileRecord info) {
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display);
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, remoteUri, info, "FileToExtractBean", display);
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

}
