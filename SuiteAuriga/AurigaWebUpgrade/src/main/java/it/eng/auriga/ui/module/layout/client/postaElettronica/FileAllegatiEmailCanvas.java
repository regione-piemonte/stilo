/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
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
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

public class FileAllegatiEmailCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;
	protected StaticTextItem titleItemNomeFile;
	protected TextItem numeroAllegatoItem;
	protected TextItem nomeFileItem;
	protected ImgButtonItem previewAllegatoItem;
	protected ImgButtonItem downloadAllegatoItem;
	protected ImgButtonItem fileFirmaValidaItem;
	protected ImgButtonItem fileFirmaNonValidaItem;
	// protected ImgButtonItem fileMarcatoTempButton;
	protected ImgButtonItem stampaFileItem;
	protected TextItem estremiProtocolloItem;
	protected ImgButtonItem dettaglioEstremiProtocolloItem;

	protected HiddenItem dimensioneFileAllegatoItem;
	protected HiddenItem idEmailItem;
	protected HiddenItem idUDItem;
	protected HiddenItem improntaItem;
	protected HiddenItem flgFirmatoItem;
	protected HiddenItem uri;
	protected HiddenItem infoFile;
	protected HiddenItem flgFirmaValidaItem;

	protected SpacerItem spacerItemFirmaFile;

	public FileAllegatiEmailCanvas(FileAllegatiEmailItem item) {
		super(item);
	}

	public FileAllegatiEmailCanvas(FileAllegatiEmailItem item, HashMap<String, String> parameters) {
		super(item, parameters);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(30);
		mDynamicForm.setColWidths("60", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
				"1", "1", "1", "*", "*");

		dimensioneFileAllegatoItem = new HiddenItem("bytes");
		idEmailItem = new HiddenItem("idEmailItem");
		idUDItem = new HiddenItem("idUD");
		improntaItem = new HiddenItem("impronta");
		flgFirmatoItem = new HiddenItem("flgFirmato");
		uri = new HiddenItem("uri");
		infoFile = new HiddenItem("infoFile");
		flgFirmaValidaItem = new HiddenItem("flgFirmaValida");

		titleItemNomeFile = new StaticTextItem();
		titleItemNomeFile.setColSpan(1);
		titleItemNomeFile.setDefaultValue("Nome" + AurigaLayout.getSuffixFormItemTitle());
		titleItemNomeFile.setShowTitle(false);
		titleItemNomeFile.setWidth(40);
		titleItemNomeFile.setAlign(Alignment.RIGHT);
		titleItemNomeFile.setTextBoxStyle(it.eng.utility.Styles.formTitle);

		numeroAllegatoItem = new TextItem("numeroProgrAllegato", "N°");
		numeroAllegatoItem.setWidth(30);
		numeroAllegatoItem.setShowTitle(true);
		numeroAllegatoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				mDynamicForm.setValue("numeroProgrAllegato", "" + getCounter());
				return getItem().getTotalMembers() > 1 && showNumeroAllegato();
			}
		});

		nomeFileItem = new TextItem("displayFileName", "Nome");
		nomeFileItem.setWidth(350);
		nomeFileItem.setShowTitle(false);

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

				if (mDynamicForm.getValueAsString("uri") != null && !mDynamicForm.getValueAsString("uri").equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
					toShowPreview = PreviewWindow.isToShowEml(lInfoFileRecord, mDynamicForm.getValueAsString("nomeFile"))
							|| PreviewWindow.canBePreviewed(lInfoFileRecord);
				}
				spacerItemPreviewAllegato.setVisible(!toShowPreview);
				return toShowPreview;
			}
		});

		downloadAllegatoItem = new ImgButtonItem("downloadAllegato", "file/download_manager.png", "Download Allegato");
		downloadAllegatoItem.setShowTitle(false);
		downloadAllegatoItem.setAlwaysEnabled(true);
		downloadAllegatoItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Download del file con dimensione: "
						+ (dimensioneFileAllegatoItem.getValue() != null && !dimensioneFileAllegatoItem.getValue().equals("")
								? dimensioneFileAllegatoItem.getValue().toString() : "");
			}
		});
		downloadAllegatoItem.addIconClickHandler(new IconClickHandler() {

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
								downloadFileAllegato();
							}
						});
						MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt(), "blank.png");
						sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								downloadFileAllegatoSbustato();
							}
						});
						showFirmatoAndSbustato.setItems(firmato, sbustato);
						showFirmatoAndSbustato.showContextMenu();
					} else {
						downloadFileAllegato();
					}
				}
			}
		});

		spacerItemFirmaFile = new SpacerItem();
		spacerItemFirmaFile.setColSpan(1);
		spacerItemFirmaFile.setWidth(20);
		spacerItemFirmaFile.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return !isFileFirmato();
			}
		});

		stampaFileItem = new ImgButtonItem("flgStampaFile", "postaElettronica/print_file.png", "Stampa");
		stampaFileItem.setShowTitle(false);
		stampaFileItem.setAlwaysEnabled(true);
		stampaFileItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Stampa";
			}
		});
		stampaFileItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				Boolean isAbilShowPrint = ((FileAllegatiEmailItem) getItem()).showStampaFileButton();
				boolean toShowPreview = false;
				if (mDynamicForm.getValueAsString("uri") != null && !mDynamicForm.getValueAsString("uri").equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
					toShowPreview = !PreviewWindow.isToShowEml(lInfoFileRecord, mDynamicForm.getValueAsString("nomeFile"))
							&& PreviewWindow.canBePreviewed(lInfoFileRecord);
				}
				return isAbilShowPrint && toShowPreview;
			}
		});
		stampaFileItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {

				clickStampaFile();
			}
		});

		fileFirmaValidaItem = new ImgButtonItem("fileFirmaValida", "firma/firma.png", "Dettaglio della firma del file allegato");
		fileFirmaValidaItem.setShowTitle(false);
		fileFirmaValidaItem.setAlwaysEnabled(true);
		fileFirmaValidaItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {

				createMenuFirma();
			}

		});
		fileFirmaValidaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return isFileFirmato() && isFirmaValida();
			}
		});
		
		fileFirmaNonValidaItem = new ImgButtonItem("fileFirmaNonValida", "firma/firmaNonValida.png", 
				"Presente firma digitale non valida o non verificabile. Clicca qui per maggiori dettagli.");
		fileFirmaNonValidaItem.setShowTitle(false);
		fileFirmaNonValidaItem.setAlwaysEnabled(true);
		fileFirmaNonValidaItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {

				createMenuFirma();
			}

		});
		fileFirmaNonValidaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return isFileFirmato() && !isFirmaValida();
			}
		});
		
//		fileMarcatoTempButton = new ImgButtonItem("fileMarcatoTempButton", "marca/marcaNonValida.png", I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt());
//		fileMarcatoTempButton.setAlwaysEnabled(true);
//		fileMarcatoTempButton.setColSpan(1);
//		fileMarcatoTempButton.setIconWidth(16);
//		fileMarcatoTempButton.setIconHeight(16);
//		fileMarcatoTempButton.setIconVAlign(VerticalAlignment.BOTTOM);
//		fileMarcatoTempButton.setAlign(Alignment.LEFT);
//		fileMarcatoTempButton.setWidth(16);
//		fileMarcatoTempButton.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
//					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
//					if (lInfoFileRecord != null && lInfoFileRecord.getInfoFirmaMarca() != null && lInfoFileRecord.getInfoFirmaMarca().getDataOraMarcaTemporale() != null) {
//						if (lInfoFileRecord.getInfoFirmaMarca().isMarcaTemporaleNonValida()) {
//							fileMarcatoTempButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt());
//							fileMarcatoTempButton.setIcon("marcaTemporale/marcaTemporaleNonValida.png");
//							return true;
//						} else {
//							Date dataOraMarcaTemporale = lInfoFileRecord.getInfoFirmaMarca().getDataOraMarcaTemporale();
//							DateTimeFormat display_datetime_format = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
//							fileMarcatoTempButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaValida_prompt(display_datetime_format.format(dataOraMarcaTemporale)));
//							fileMarcatoTempButton.setIcon("marcaTemporale/marcaTemporaleValida.png");
//							return true;
//						}
//					} else {
//						return false;
//					}
//				}
//				return false;
//			}
//		});

		estremiProtocolloItem = new TextItem("estremiProt", "Protocollato come");
		estremiProtocolloItem.setWidth(300);
		estremiProtocolloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrata() && isCategoriaValida();
			}
		});

		dettaglioEstremiProtocolloItem = new ImgButtonItem("apriDettaglioProtocollo", "buttons/detail.png", "Apri dettaglio protocollo");
		dettaglioEstremiProtocolloItem.setShowTitle(false);
		dettaglioEstremiProtocolloItem.setAlwaysEnabled(true);
		dettaglioEstremiProtocolloItem.setStartRow(false);
		dettaglioEstremiProtocolloItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				dettaglioProtocollo();
			}
		});
		dettaglioEstremiProtocolloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isProtocollato();
			}
		});

		mDynamicForm.setFields(numeroAllegatoItem, idUDItem, improntaItem, flgFirmatoItem, idEmailItem, dimensioneFileAllegatoItem, titleItemNomeFile,
				nomeFileItem, previewAllegatoItem, downloadAllegatoItem, fileFirmaValidaItem,fileFirmaNonValidaItem, /**fileMarcatoTempButton, */
				stampaFileItem, spacerItemPreviewAllegato, spacerItemFirmaFile,
				estremiProtocolloItem, dettaglioEstremiProtocolloItem, uri, infoFile, flgFirmaValidaItem);

		addChild(mDynamicForm);
	}

//	public boolean isFileFirmato() {
//		boolean flgFirmato = mDynamicForm.getValue("flgFirmato") != null
//				&& ("1".equals(mDynamicForm.getValueAsString("flgFirmato")) || "true".equalsIgnoreCase(mDynamicForm.getValueAsString("flgFirmato")));
//		boolean flgFirmaValida = mDynamicForm.getValue("flgFirmaValida") != null
//				&& ("1".equals(mDynamicForm.getValueAsString("flgFirmaValida")) || "true".equalsIgnoreCase(mDynamicForm.getValueAsString("flgFirmaValida")));
//		spacerItemFirmaFile.setVisible(!flgFirmato);
//		return flgFirmato && flgFirmaValida;
//	}
	
	public boolean isFileFirmato() {
		boolean flgFirmato = mDynamicForm.getValue("flgFirmato") != null
				&& ("1".equals(mDynamicForm.getValueAsString("flgFirmato")) || "true".equalsIgnoreCase(mDynamicForm.getValueAsString("flgFirmato")));
		return flgFirmato;
	}
	
	public boolean isFirmaValida() {
		boolean flgFirmaValida = mDynamicForm.getValue("flgFirmaValida") != null
				&& ("1".equals(mDynamicForm.getValueAsString("flgFirmaValida")) || "true".equalsIgnoreCase(mDynamicForm.getValueAsString("flgFirmaValida")));
		return flgFirmaValida;
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		numeroAllegatoItem.setCanEdit(false);
		numeroAllegatoItem.setTextBoxStyle(it.eng.utility.Styles.staticTextItemBold);
		titleItemNomeFile.setTextBoxStyle(it.eng.utility.Styles.formTitle);
	}

	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
	}

	private boolean isMailEntrata() {
		final Record detailRecord = ((FileAllegatiEmailItem) getItem()).getDetailRecord();
		return detailRecord != null && detailRecord.getAttribute("flgIO") != null && detailRecord.getAttribute("flgIO").equals("I");
	}

	private boolean isCategoriaValida() {
		final Record detailRecord = ((FileAllegatiEmailItem) getItem()).getDetailRecord();
		return detailRecord != null && (detailRecord.getAttribute("categoria") != null && detailRecord.getAttribute("categoria").equals("PEC")
				|| detailRecord.getAttribute("categoria") != null && detailRecord.getAttribute("categoria").equals("ANOMALIA")
				|| detailRecord.getAttribute("categoria") != null && detailRecord.getAttribute("categoria").equals("INTEROP_SEGN"));
	}

	private boolean isProtocollato() {
		return mDynamicForm.getValue("estremiProt") != null && !mDynamicForm.getValue("estremiProt").equals("");
	}

	private void downloadFileAllegato() {
		String uri = mDynamicForm.getValueAsString("uri");
		if (uri.equals("_noUri")) {
			((FileAllegatiEmailItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList(((FileAllegatiEmailItem) getItem()).getName()).get(i);
					realDownload(lRecord.getAttribute("uri"));
				}
			});
		} else {
			realDownload(mDynamicForm.getValueAsString("uri"));
		}
	}

	private void downloadFileAllegatoSbustato() {
		String uri = mDynamicForm.getValueAsString("uri");
		if (uri.equals("_noUri")) {
			((FileAllegatiEmailItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList(((FileAllegatiEmailItem) getItem()).getName()).get(i);
					realDownloadSbustato(lRecord.getAttribute("uri"));
				}
			});
		} else {
			realDownloadSbustato(mDynamicForm.getValueAsString("uri"));
		}
	}

	private void realDownloadSbustato(String uri) {
		String display = mDynamicForm.getValueAsString("displayFileName");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", true);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	private void realDownload(String uri) {
		String display = mDynamicForm.getValueAsString("displayFileName");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", true);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	private void dettaglioProtocollo() {
		final Record detailRecord = ((FileAllegatiEmailItem) getItem()).getDetailRecord();
		if (detailRecord != null && ((detailRecord.getAttribute("idEmail") != null && !"".equals(detailRecord.getAttribute("idEmail")))
				&& (mDynamicForm.getValueAsString("idUD") != null && !"".equals(mDynamicForm.getValueAsString("idUD"))))) {
			Record lRecord = new Record();
			lRecord.setAttribute("idUd", mDynamicForm.getValueAsString("idUD"));
			String title = (mDynamicForm.getValue("estremiProt") != null && !mDynamicForm.getValue("estremiProt").equals("")
					? "Dettaglio prot. " + mDynamicForm.getValue("estremiProt") : "");
			DettaglioRegProtAssociatoWindow dettaglioProtWindow = new DettaglioRegProtAssociatoWindow(lRecord, title);
		} else {
			SC.say("Non e' possibile accedere ai dettagli perche' il riferimento del protocollo di apertura e' inesistente.");
		}
	}

	// Preview del file
	public void clickPreviewFile() {
		String uri = mDynamicForm.getValueAsString("uri");
		if (uri.equals("_noUri")) {
			((FileAllegatiEmailItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList(((FileAllegatiEmailItem) getItem()).getName()).get(i);
					realClickPreviewFile(lRecord.getAttribute("uri"));
				}
			});
		} else {
			realClickPreviewFile(mDynamicForm.getValueAsString("uri"));
		}
	}

	protected void realClickPreviewFile(final String uri) {
		
		final String display = mDynamicForm.getValueAsString("displayFileName");
		final String numeroProgrAllegato = mDynamicForm.getValueAsString("numeroProgrAllegato");
		final String progressivo = ((FileAllegatiEmailItem) getItem()).getDetailRecord().getAttribute("id");
		final Boolean remoteUri = true;
		final String flgConvertibileInPdf = mDynamicForm.getValueAsString("flgConvertibileInPdf");
		final InfoFileRecord info = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
		final String title;
		if ((numeroProgrAllegato != null) && (numeroProgrAllegato.length() > 0)) {
			if (progressivo != null && !"".equals(progressivo)) {
				title = progressivo + " - " + "Allegato N°" + numeroProgrAllegato + " - " + display;
			} else {
				title = "Allegato N°" + numeroProgrAllegato + " - " + display;
			}
		} else {
			title = display;
		}
		if (info != null) {
			if (info.getMimetype() != null && info.getMimetype().equals("application/xml") && !"Segnatura.xml".equals(display)
					&& !"segnatura.xml".equals(display) && !"Conferma.xml".equals(display) && !"conferma.xml".equals(display)
					&& !"eccezione.xml".equals(display) && !"Eccezione.xml".equals(display) && !"Aggiornamento.xml".equals(display)
					&& !"aggiornamento.xml".equals(display) && !"annullamento.xml".equals(display) && !"Annullamento.xml".equals(display)) {
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
							VisualizzaFatturaWindow lVisualizzaFatturaWindow = new VisualizzaFatturaWindow(title, object);
							lVisualizzaFatturaWindow.show();
						} else {
							preview(uri, title, remoteUri, info);
						}
					}
				});
			} else {
				preview(uri, title, remoteUri, info);
			}
		} else {
			SC.say("Non è possibile accedere alla preview del documento allegato");
		}
	}

	public void preview(final String uri, String display, Boolean remoteUri, InfoFileRecord info) {
		String view = ((FileAllegatiEmailItem) getItem()).getAttribute("closeViewer");
		/**
		 * Gestione per la visualizzazione deli file di tipo image
		 */
		if (info != null && info.getMimetype() != null && info.getMimetype().startsWith("image") && !info.getMimetype().equals("image/tiff")) {
			 new PreviewImageWindow(uri, remoteUri, info);
		} else {

			if (!Layout.getOpenedViewers().keySet().contains(uri)) {
				PreviewWindow lPreviewWindow = new PreviewWindow(view, "", uri, remoteUri, info, "FileToExtractBean", display) {

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

	private void generaFileFirmaAndPreview(final InfoFileRecord infoFileAllegato, String uriFileAllegato, boolean aggiungiFirma) {

		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFileAllegato);
		record.setAttribute("uriAttach", uriFileAllegato);

		String tsInvio = ((FileAllegatiEmailItem) getItem()).getDetailRecord().getAttribute("tsInvio");

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.extraparam.put("aggiungiFirma", Boolean.toString(aggiungiFirma));
		if (tsInvio != null) {
			lGwtRestDataSource.extraparam.put("dataRiferimento", tsInvio);
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

	protected boolean showNumeroAllegato() {
		return ((FileAllegatiEmailItem) getItem()).showNumeroAllegato();
	}

	private void clickStampaFile() {
		String uri = mDynamicForm.getValueAsString("uri");
		if (uri.equals("_noUri")) {
			((FileAllegatiEmailItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList(((FileAllegatiEmailItem) getItem()).getName()).get(i);
					manageStampaFile(lRecord.getAttribute("uri"));
				}
			});
		} else {
			manageStampaFile(mDynamicForm.getValueAsString("uri"));
		}
	}

	protected void manageStampaFile(final String uri) {
		final String display = mDynamicForm.getValueAsString("displayFileName");
		final Boolean remoteUri = true;
		final InfoFileRecord info = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
		if (info != null) {
			if (info.getMimetype() != null && info.getMimetype().equals("application/xml") && !"Segnatura.xml".equals(display)
					&& !"segnatura.xml".equals(display) && !"Conferma.xml".equals(display) && !"conferma.xml".equals(display)
					&& !"eccezione.xml".equals(display) && !"Eccezione.xml".equals(display) && !"Aggiornamento.xml".equals(display)
					&& !"aggiornamento.xml".equals(display) && !"annullamento.xml".equals(display) && !"Annullamento.xml".equals(display)) {
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
	
	private void createMenuFirma() {
		
		final InfoFileRecord infoFileAllegato = InfoFileRecord.buildInfoFileRecord(mDynamicForm.getValue("infoFile"));
		final String uriFileAllegato = mDynamicForm.getValueAsString("uri");

		MenuItem informazioniFirmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_dettaglioCertificazione_prompt(),
				"buttons/detail.png");
		informazioniFirmaMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				final boolean remoteUri = true;
				if (uriFileAllegato.equals("_noUri")) {
					((FileAllegatiEmailItem) getItem()).downloadFile(new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							int i = getCounter() - 1;
							Record lRecord = object.getAttributeAsRecordList(((FileAllegatiEmailItem) getItem()).getName()).get(i);
							generaFileFirmaAndPreview(infoFileAllegato, lRecord.getAttribute("uri"), false);
						}
					});
				} else {
					generaFileFirmaAndPreview(infoFileAllegato, uriFileAllegato, false);
				}
			}
		});

		MenuItem stampaFileCertificazioneMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_stampaFileCertificazione_prompt(),
				"protocollazione/stampaEtichetta.png");
		stampaFileCertificazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				if (uriFileAllegato.equals("_noUri")) {

					((FileAllegatiEmailItem) getItem()).downloadFile(new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							int i = getCounter() - 1;
							Record lRecord = object.getAttributeAsRecordList(((FileAllegatiEmailItem) getItem()).getName()).get(i);
							generaFileFirmaAndPreview(infoFileAllegato, lRecord.getAttribute("uri"), true);
						}
					});
				} else
					generaFileFirmaAndPreview(infoFileAllegato, uriFileAllegato, true);
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

}