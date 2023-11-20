/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
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
import it.eng.auriga.ui.module.layout.client.archivio.DocPraticaPregressaCanvas;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatoCanvas;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowPageSelectionCallback;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetail;
import it.eng.auriga.ui.module.layout.client.protocollazione.SelezionaGeneraDaModelloWindow;
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
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas.ReplicableCanvasForm;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public abstract class FileButtonsItem extends NestedFormItem {

	private FileButtonsItem fileButtons;
	
	private ImgButtonItem previewButton;
	private ImgButtonItem previewEditButton;
	private ImgButtonItem editButton;
	private ImgButtonItem fileFirmatoDigButton;
	private ImgButtonItem firmaNonValidaButton;
	protected ImgButtonItem fileMarcatoTempButton;
	private ImgButtonItem dimensioneSignificativaIcon;
	private ImgButtonItem downloadOutsideAltreOpMenuButton;
	private ImgButtonItem altreOpButton;
	private ImgButtonItem generaDaModelloButton;
	
	private DynamicForm form;
	private FileUploadItemWithFirmaAndMimeType uploadItem;
	
	public FileButtonsItem(String name, DynamicForm pDynamicForm) {
		this(name, pDynamicForm, null);
	}
	
	public FileButtonsItem(String name, DynamicForm pDynamicForm, FileUploadItemWithFirmaAndMimeType pFileUploadItem) {
		
		super(name);		
		
		setOverflow(Overflow.VISIBLE);
		setWidth(10);
		setColSpan(1);
		
		fileButtons = this;
		
		form = pDynamicForm;
		
		if(pFileUploadItem != null) {
			uploadItem = pFileUploadItem;
		} else {
			uploadItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

				@Override
				public void uploadEnd(final String displayFileName, final String uri) {
					String warningMessage = getWarningMessageOnUpload();
					if(warningMessage != null && !"".equals(warningMessage)) {
						Layout.addMessage(new MessageBean(warningMessage, "", MessageType.WARNING));
					}
					form.setValue(nomeFileFieldName(), displayFileName);
					form.setValue(uriFileFieldName(), uri);
					form.setValue(nomeFileTifFieldName(), "");
					form.setValue(uriFileTifFieldName(), "");
					form.setValue(remoteUriFieldName(), false);
					form.setValue(nomeFileVerPreFirmaFieldName(), displayFileName);
					form.setValue(uriFileVerPreFirmaFieldName(), uri);
					changedEventAfterUpload(displayFileName, uri);
				}

				@Override
				public void manageError(String error) {
					String errorMessage = "Errore nel caricamento del file";
					if (error != null && !"".equals(error))
						errorMessage += ": " + error;
					Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
					form.markForRedraw();
					fileButtons.markForRedraw();
					manageOnChanged();
					uploadItem.getCanvas().redraw();
				}
			}, new ManageInfoCallbackHandler() {

				@Override
				public void manageInfo(InfoFileRecord info) {
					InfoFileRecord precInfo = form.getValue(infoFileFieldName()) != null ? new InfoFileRecord(form.getValue(infoFileFieldName())) : null;
					String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
					form.setValue(infoFileFieldName(), info);
					form.setValue(improntaVerPreFirmaFieldName(), info.getImpronta());
					form.setValue(infoFileVerPreFirmaFieldName(), info);				
					String nomeFile = form.getValueAsString(nomeFileFieldName());
					String nomeFileOrig = form.getValueAsString(nomeFileOrigFieldName());
					if (precImpronta == null || !precImpronta.equals(info.getImpronta()) || (nomeFile != null && !"".equals(nomeFile)
							&& nomeFileOrig != null && !"".equals(nomeFileOrig) && !nomeFile.equals(nomeFileOrig))) {
						form.setValue(isChangedFieldName(), true);
						manageOnChangedFile();
					}
					controlAfterUpload(info);					
					form.markForRedraw();
					fileButtons.markForRedraw();
					manageOnChanged();
				}
			});
			uploadItem.setAttribute("nascosto", !showUploadItem());		
			uploadItem.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {				
					uploadItem.setAttribute("nascosto", !showUploadItem());				
					return showUploadItem();
				}
			});			
			uploadItem.setColSpan(1);
		}
				
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
				return showPreviewButton();				
			}
		});
		previewButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickPreviewButton();
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
				return showPreviewEditButton();				
			}
		});
		previewEditButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickPreviewEditButton();;
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
				return showEditButton();
			}
		});
		editButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickEditButton();
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
				return showFileFirmatoDigButton();				
			}
		});
		fileFirmatoDigButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickFileFirmatoButton();
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
				return showFirmaNonValidaButton();				
			}
		});
		firmaNonValidaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickFirmaNonValidaButton();
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
				if (isFileMarcatoTemp()) {
					if (isMarcaTempFileValida()) {
						Date dataOraMarcaTemporale = getDataMarcaTempFile();
						DateTimeFormat display_datetime_format = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
						fileMarcatoTempButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaValida_prompt(display_datetime_format.format(dataOraMarcaTemporale)));
						fileMarcatoTempButton.setIcon("marcaTemporale/marcaTemporaleValida.png");
					} else {
						fileMarcatoTempButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt());
						fileMarcatoTempButton.setIcon("marcaTemporale/marcaTemporaleNonValida.png");
					}
					return true;
				} else {
					return false;
				}
			}
		});
		
		dimensioneSignificativaIcon = new ImgButtonItem("dimensioneSignificativaIcon", "warning.png", "Dimensione significativa");
		dimensioneSignificativaIcon.setCellStyle(it.eng.utility.Styles.formCell);
		dimensioneSignificativaIcon.setAlwaysEnabled(true);
		dimensioneSignificativaIcon.setColSpan(1);
		dimensioneSignificativaIcon.setIconWidth(16);
		dimensioneSignificativaIcon.setIconHeight(16);
		dimensioneSignificativaIcon.setIconVAlign(VerticalAlignment.BOTTOM);
		dimensioneSignificativaIcon.setAlign(Alignment.LEFT);
		dimensioneSignificativaIcon.setWidth(16);
		dimensioneSignificativaIcon.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDimensioneSignificativaIcon();				
			}
		});		
				
		downloadOutsideAltreOpMenuButton = new ImgButtonItem("downloadOutsideAltreOpMenuButton", "file/download_manager.png", I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt());
		downloadOutsideAltreOpMenuButton.setCellStyle(it.eng.utility.Styles.formCell);
		downloadOutsideAltreOpMenuButton.setAlwaysEnabled(true);
		downloadOutsideAltreOpMenuButton.setColSpan(1);
		downloadOutsideAltreOpMenuButton.setIconWidth(16);
		downloadOutsideAltreOpMenuButton.setIconHeight(16);
		downloadOutsideAltreOpMenuButton.setIconVAlign(VerticalAlignment.BOTTOM);
		downloadOutsideAltreOpMenuButton.setAlign(Alignment.LEFT);
		downloadOutsideAltreOpMenuButton.setWidth(16);
		downloadOutsideAltreOpMenuButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				clickDownloadOutsideAltreOpMenuButton();
			}
		});
		downloadOutsideAltreOpMenuButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return enableDownloadMenuItem() && showDownloadButtomOutsideAltreOpMenu();				
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
				clickAltreOpButton();
			}
		});
		
		altreOpButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showAltreOperazioniIfEditing()) {
					return fileButtons.isEditing();
				}
				return showAltreOperazioni();
			}
		});
		
		generaDaModelloButton = new ImgButtonItem("generaDaModelloButton", "protocollazione/generaDaModello.png",
				I18NUtil.getMessages().protocollazione_detail_generaDaModelloButton_prompt());
		generaDaModelloButton.setAlwaysEnabled(false);
		generaDaModelloButton.setColSpan(1);
		generaDaModelloButton.setIconWidth(16);
		generaDaModelloButton.setIconHeight(16);
		generaDaModelloButton.setIconVAlign(VerticalAlignment.BOTTOM);
		generaDaModelloButton.setAlign(Alignment.LEFT);
		generaDaModelloButton.setWidth(16);
		generaDaModelloButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showGeneraDaModelloButton();				
			}
		});
		generaDaModelloButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickGeneraDaModelloButton();
			}
		});
		
		if(pFileUploadItem == null) {
			
			setNumCols(8);
			setColWidths(16,16,16,16,16,16,16,16);
			
			setNestedFormItems(
				uploadItem,
				previewButton, 
				previewEditButton, 
				editButton, 
				fileFirmatoDigButton, 
				firmaNonValidaButton, 
				fileMarcatoTempButton,
				dimensioneSignificativaIcon,
				downloadOutsideAltreOpMenuButton,
				altreOpButton, 
				generaDaModelloButton
			);
			
		} else {
		
			setNumCols(7);
			setColWidths(16,16,16,16,16,16,16);
			
			setNestedFormItems(
				previewButton, 
				previewEditButton, 
				editButton, 
				fileFirmatoDigButton, 
				firmaNonValidaButton, 
				fileMarcatoTempButton,
				dimensioneSignificativaIcon,
				downloadOutsideAltreOpMenuButton,
				altreOpButton, 
				generaDaModelloButton
			);
			
		}
		
	}
	
	public boolean showAltreOperazioni() {
		return true;
	}
	
	public boolean showAltreOperazioniIfEditing() {
		return false;
	}

	public void updateAfterUpload(final String displayFileName, final String uri, Record infoFile) {
		
		InfoFileRecord info = new InfoFileRecord(infoFile);
		InfoFileRecord precInfo = form.getValue(infoFileFieldName()) != null ? new InfoFileRecord(form.getValue(infoFileFieldName())) : null;
		String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
		form.setValue(infoFileFieldName(), info);
		if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
			form.setValue(isChangedFieldName(), true);
			manageOnChangedFile();
		}
		form.setValue(nomeFileFieldName(), displayFileName);
		form.setValue(uriFileFieldName(), uri);
		form.setValue(nomeFileTifFieldName(), "");
		form.setValue(uriFileTifFieldName(), "");
		form.setValue(remoteUriFieldName(), false);
		form.setValue(nomeFileVerPreFirmaFieldName(), displayFileName);
		form.setValue(uriFileVerPreFirmaFieldName(), uri);
		form.setValue(improntaVerPreFirmaFieldName(), info.getImpronta());
		form.setValue(infoFileVerPreFirmaFieldName(), info);			
		controlAfterUpload(info);
		form.markForRedraw();
		fileButtons.markForRedraw();
		manageOnChanged();
		changedEventAfterUpload(displayFileName, uri);
		
	}
	
	public boolean showUploadItem() {
		return true;
	}
	
	public boolean showPreviewButton() {
		if (form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(form.getValue(infoFileFieldName()));
			return PreviewWindow.canBePreviewed(lInfoFileRecord);
		} else {
			return false;
		}
	}
	
	public void clickPreviewButton() {
		final String idUd = getIdUd();
		String idDoc = getIdDoc();
		addToRecent(idUd, idDoc);
		final String display = form.getValueAsString(nomeFileFieldName());
		final String uri = form.getValueAsString(uriFileFieldName());
		final Boolean remoteUri = Boolean.valueOf(form.getValueAsString(remoteUriFieldName()));
		final InfoFileRecord info = new InfoFileRecord(form.getValue(infoFileFieldName()));
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute(remoteUriFieldName(), remoteUri);
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
						preview(idUd, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			preview(idUd, display, uri, remoteUri, info);
		}
	}
	
	public void preview(String idUd, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		// Callback per la gestione della versione con omisses
		PreviewWindowPageSelectionCallback lWindowPageSelectionCallback = new PreviewWindowPageSelectionCallback() {
			
			@Override
			public void executeSalvaVersConOmissis(Record record) {			
				if(showSalvaVersConOmissisInPreview()) {
					executeSalvaVersConOmissisInPreview(record);
				}
			}
			
			@Override
			public void executeSalva(Record record) {
				executeSalvaInPreview(record);
			}
			
			@Override
			public void executeOnError() {				
			}
		};
		boolean isViewOnly = !fileButtons.isEditing();
		boolean enableOptionToSaveInOmissisForm = showSalvaVersConOmissisInPreview();
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display, lWindowPageSelectionCallback, isViewOnly, enableOptionToSaveInOmissisForm);		
	}
	
	public void executeSalvaInPreview(Record record) {
		String uri = record.getAttribute("uri");
		String displayFileName = record.getAttribute("fileName");
		Record infoFile = record.getAttributeAsRecord("infoFile");
		InfoFileRecord info = new InfoFileRecord(infoFile);

		InfoFileRecord precInfo = form.getValue(infoFileFieldName()) != null ? new InfoFileRecord(form.getValue(infoFileFieldName())) : null;
		String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
		form.setValue(infoFileFieldName(), info);
		if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
			form.setValue(isChangedFieldName(), true);
			manageOnChangedFile();
		}
		form.setValue(nomeFileFieldName(), displayFileName);
		form.setValue(uriFileFieldName(), uri);
		form.setValue(nomeFileTifFieldName(), "");
		form.setValue(uriFileTifFieldName(), "");
		form.setValue(remoteUriFieldName(), false);
		form.setValue(nomeFileVerPreFirmaFieldName(), displayFileName);
		form.setValue(uriFileVerPreFirmaFieldName(), uri);
		form.setValue(improntaVerPreFirmaFieldName(), info.getImpronta());
		form.setValue(infoFileVerPreFirmaFieldName(), info);				
		controlAfterUpload(info);
		form.markForRedraw();
		fileButtons.markForRedraw();
		manageOnChanged();
		changedEventAfterUpload(displayFileName, uri);		
	}
	
	public boolean showSalvaVersConOmissisInPreview() {
		return false;
	}
	
	public void executeSalvaVersConOmissisInPreview(Record record) {
		
	}
	
	public boolean showPreviewEditButton() {
		if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET") 
				&& (form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals(""))) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(form.getValue(infoFileFieldName()));
			return lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
		} else {
			return false;
		}
	}
	
	public void clickPreviewEditButton() {
		final String idUd = getIdUd();
		String idDoc = getIdDoc();
		addToRecent(idUd, idDoc);
		final String display = form.getValueAsString(nomeFileFieldName());
		final String uri = form.getValueAsString(uriFileFieldName());
		final Boolean remoteUri = Boolean.valueOf(form.getValueAsString(remoteUriFieldName()));
		final InfoFileRecord info = new InfoFileRecord(form.getValue(infoFileFieldName()));
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute(remoteUriFieldName(), remoteUri);
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
						previewWithJPedal(idUd, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			previewWithJPedal(idUd, display, uri, remoteUri, info);
		}
	}
	
	public void previewWithJPedal(String idUd, final String display, final String uri, final Boolean remoteUri,
			InfoFileRecord info) {
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
				boolean timbroEnabled = getFlgTipoProv() != null && !"".equals(getFlgTipoProv()) 
										&& getIdUd() != null && !"".equals(getIdUd());
				PreviewDocWindow previewDocWindow = new PreviewDocWindow(uri, display, remoteUri, timbroEnabled, lOpzioniTimbro) {

					@Override
					public void uploadCallBack(String filePdf, String uriPdf, String record) {
						InfoFileRecord precInfo = new InfoFileRecord(form.getValue(infoFileFieldName()));
						String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
						InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
						form.setValue(infoFileFieldName(), info);
						if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
							form.setValue(isChangedFieldName(), true);
							manageOnChangedFile();
						}
						form.setValue(nomeFileFieldName(), filePdf);
						form.setValue(uriFileFieldName(), uriPdf);
						form.setValue(nomeFileTifFieldName(), "");
						form.setValue(uriFileTifFieldName(), "");
						form.setValue(remoteUriFieldName(), false);
						form.setValue(nomeFileVerPreFirmaFieldName(), filePdf);
						form.setValue(uriFileVerPreFirmaFieldName(), uriPdf);
						form.setValue(improntaVerPreFirmaFieldName(), info.getImpronta());
						form.setValue(infoFileVerPreFirmaFieldName(), info);	
						controlAfterUpload(info);
						form.markForRedraw();
						fileButtons.markForRedraw();
						manageOnChanged();
						changedEventAfterUpload(filePdf, uriPdf);
					}
				};
				previewDocWindow.show();
			}
		});
	}

	public boolean showEditButton() {
		final String display = form.getValueAsString(nomeFileVerPreFirmaFieldName());
		final String uri = form.getValueAsString(uriFileVerPreFirmaFieldName());
		if (canBeEditedByApplet()) {
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_EDITING_FILE")) {
				if (uri != null && !uri.equals("")) {
					String estensione = null;
					/**
					 * File corrente non firmato
					 */
					Object infoFile = form.getValue(infoFileFieldName());
					if (infoFile != null) {
						InfoFileRecord lInfoFileRecord = new InfoFileRecord(infoFile);
						if (lInfoFileRecord.getCorrectFileName() != null && !lInfoFileRecord.getCorrectFileName().trim().equals("")) {
							estensione = lInfoFileRecord.getCorrectFileName().substring(lInfoFileRecord.getCorrectFileName().lastIndexOf(".") + 1);
						}
						if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
							estensione = display.substring(display.lastIndexOf(".") + 1);
						}
						if (estensione.equalsIgnoreCase("p7m")) {
							String displaySbustato = display.substring(0, display.lastIndexOf("."));
							estensione = displaySbustato.substring(displaySbustato.lastIndexOf(".") + 1);
						}
						for (String lString : Layout.getGenericConfig().getEditableExtension()) {
							if (lString.equals(estensione)) {
								return true;
							}
						}
					}
					/**
					 * File pre versione di quello firmato
					 */
					Object infoFilePreVer = form.getValue(infoFileVerPreFirmaFieldName());
					if(infoFilePreVer != null) {
						InfoFileRecord lInfoFilePreVerRecord = new InfoFileRecord(infoFilePreVer);
						if (lInfoFilePreVerRecord.getCorrectFileName() != null && !lInfoFilePreVerRecord.getCorrectFileName().trim().equals("")) {
							estensione = lInfoFilePreVerRecord.getCorrectFileName().substring(lInfoFilePreVerRecord.getCorrectFileName().lastIndexOf(".") + 1);
						}
						if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
							estensione = display.substring(display.lastIndexOf(".") + 1);
						}
						if (estensione.equalsIgnoreCase("p7m")) {
							String displaySbustato = display.substring(0, display.lastIndexOf("."));
							estensione = displaySbustato.substring(displaySbustato.lastIndexOf(".") + 1);
						}
						for (String lString : Layout.getGenericConfig().getEditableExtension()) {
							if (lString.equals(estensione)) {
								return true;
							}
						}
					} 
					return false;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void clickEditButton() {
		String idUd = getIdUd();
		String idDoc = getIdDoc();
		addToRecent(idUd, idDoc);
		final String display = form.getValueAsString(nomeFileVerPreFirmaFieldName());
		final String uri = form.getValueAsString(uriFileVerPreFirmaFieldName());
		final Boolean remoteUri = Boolean.valueOf(form.getValueAsString(remoteUriFieldName()));
		final InfoFileRecord info = new InfoFileRecord(form.getValue(infoFileFieldName()));
		if (info.isFirmato()) {
			SC.ask("Modificando il file perderai la/le firme già apposte. Vuoi comunque procedere?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {

					if (value){
						InfoFileRecord infoPreFirma = new InfoFileRecord(form.getValue(infoFileVerPreFirmaFieldName()));
						editFile(infoPreFirma, display, uri, remoteUri);
					}
				}
			});
		} else {
			editFile(info, display, uri, remoteUri);
		}
	}

	public void editFile(InfoFileRecord info, String display, String uri, Boolean remoteUri) {
		String estensione = null;
		if (info.getCorrectFileName() != null && !info.getCorrectFileName().trim().equals("")) {
			estensione = info.getCorrectFileName().substring(info.getCorrectFileName().lastIndexOf(".") + 1);
		}
		if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
			estensione = display.substring(display.lastIndexOf(".") + 1);
		}
		String impronta = form.getValueAsString(improntaVerPreFirmaFieldName());
		if (impronta == null || "".equals(impronta)) {
			impronta = info.getImpronta();
		}
		if (estensione.equalsIgnoreCase("p7m")) {
			Record lRecordDaSbustare = new Record();
			lRecordDaSbustare.setAttribute("displayFilename", display);
			lRecordDaSbustare.setAttribute("uri", uri);
			lRecordDaSbustare.setAttribute("remoteUri", remoteUri);
			new GWTRestService<Record, Record>("SbustaFileService").call(lRecordDaSbustare, new ServiceCallback<Record>() {

				@Override
				public void execute(final Record lRecordSbustato) {
					String displaySbustato = lRecordSbustato.getAttribute("displayFilename");
					String uriSbustato = lRecordSbustato.getAttribute("uri");
					String estensioneSbustato = displaySbustato.substring(displaySbustato.lastIndexOf(".") + 1);
					String improntaSbustato = lRecordSbustato.getAttribute("impronta");
					openEditFileWindow(displaySbustato, uriSbustato, false, estensioneSbustato, improntaSbustato);
				}
			});
		} else {
			openEditFileWindow(display, uri, remoteUri, estensione, impronta);
		}
	}

	public void openEditFileWindow(String display, String uri, Boolean remoteUri, String estensione, String impronta) {
		OpenEditorUtility.openEditor(display, uri, remoteUri, estensione, impronta, new OpenEditorCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, String record) {
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				InfoFileRecord precInfo = form.getValue(infoFileFieldName()) != null ? new InfoFileRecord(form.getValue(infoFileFieldName())) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				form.setValue(infoFileFieldName(), info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					form.setValue(isChangedFieldName(), true);
					manageOnChangedFile();
				}
				form.setValue(nomeFileFieldName(), nomeFileFirmato);
				form.setValue(uriFileFieldName(), uriFileFirmato);
				form.setValue(nomeFileTifFieldName(), "");
				form.setValue(uriFileTifFieldName(), "");
				form.setValue(remoteUriFieldName(), false);
				form.setValue(nomeFileVerPreFirmaFieldName(), nomeFileFirmato);
				form.setValue(uriFileVerPreFirmaFieldName(), uriFileFirmato);
				form.setValue(improntaVerPreFirmaFieldName(), info.getImpronta());
				form.setValue(infoFileVerPreFirmaFieldName(), info);	
				controlAfterUpload(info);
				form.markForRedraw();
				fileButtons.markForRedraw();
				manageOnChanged();
				changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
			}
		});
	}
	
	public long getDimAlertAllegato() {
		return -1;
	}
	
	public long getDimMaxAllegatoXPubbl() {
		return -1;
	}
	
	public boolean showDimensioneSignificativaIcon() {
		if (form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(form.getValue(infoFileFieldName()));
			long dimAlertAllegato = getDimAlertAllegato();
			if(dimAlertAllegato > 0 && lInfoFileRecord != null && lInfoFileRecord.getBytes() > dimAlertAllegato) {						
				final float MEGABYTE = 1024L * 1024L;
				float dimensioneInMB = lInfoFileRecord.getBytes() / MEGABYTE;						
				dimensioneSignificativaIcon.setPrompt("Dimensione significativa: " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB");
				return true;
			} 		
		} 
		return false;	
	}
	
	public boolean showFileFirmatoDigButton() {
		if (form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(form.getValue(infoFileFieldName()));
			return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && lInfoFileRecord.isFirmaValida();
		}
		return false;		
	}
	
	public void clickFileFirmatoButton() {
		Menu fileFirmatoMenu = createFileFirmatoMenu();
		fileFirmatoMenu.showContextMenu();
	}
	
	public boolean showFirmaNonValidaButton() {
		if (form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(form.getValue(infoFileFieldName()));
			return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && !lInfoFileRecord.isFirmaValida();
		}
		return false;
	}
	
	public boolean isFileMarcatoTemp() {
		if (form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(form.getValue(infoFileFieldName()));
			return lInfoFileRecord != null && lInfoFileRecord.getInfoFirmaMarca() != null && lInfoFileRecord.getInfoFirmaMarca().getDataOraMarcaTemporale() != null;
		}
		return false;
	}
	
	public boolean isMarcaTempFileValida() {
		if (form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(form.getValue(infoFileFieldName()));
			return lInfoFileRecord != null && lInfoFileRecord.getInfoFirmaMarca() != null && !lInfoFileRecord.getInfoFirmaMarca().isMarcaTemporaleNonValida();
		}
		return false;
	}
	
	public Date getDataMarcaTempFile() {
		if (form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(form.getValue(infoFileFieldName()));
			return (lInfoFileRecord != null && lInfoFileRecord.getInfoFirmaMarca() != null ) ? lInfoFileRecord.getInfoFirmaMarca().getDataOraMarcaTemporale() : null;
		}
		return null;
	}
	
	public void clickFirmaNonValidaButton() {
		Menu fileFirmatoMenu = createFileFirmatoMenu();
		fileFirmatoMenu.showContextMenu();
	}
	
	public Menu createFileFirmatoMenu() {
		final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(form.getValue(infoFileFieldName()));
		final String uriFile = form.getValueAsString(uriFileFieldName());
		MenuItem informazioniFirmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_dettaglioCertificazione_prompt(), "buttons/detail.png");
		informazioniFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				generaFileFirmaAndPreview(infoFile, uriFile, false);				
			}
		});
		MenuItem stampaFileCertificazioneMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_stampaFileCertificazione_prompt(),
				"protocollazione/stampaEtichetta.png");
		stampaFileCertificazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				generaFileFirmaAndPreview(infoFile, uriFile, true);				
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
		return fileFirmatoMenu;
	}
	
	public void generaFileFirmaAndPreview(final InfoFileRecord infoFile, String uriFile, boolean aggiungiFirma) {
		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFile);
		record.setAttribute("uriAttach", uriFile);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.extraparam.put("aggiungiFirma", Boolean.toString(aggiungiFirma));
		if(getDataRifValiditaFirma() != null) {
			String dataRiferimento = DateUtil.format(getDataRifValiditaFirma());
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
	
	public void clickAltreOpButton() {
		final Menu altreOpMenu = createAltreOpMenu();
		altreOpMenu.showContextMenu();
	}
	
	public Menu createAltreOpMenu() {		
		
		Menu altreOpMenu = new Menu();
		altreOpMenu.setOverflow(Overflow.VISIBLE);
		altreOpMenu.setShowIcons(true);
		altreOpMenu.setSelectionType(SelectionStyle.SINGLE);
		altreOpMenu.setCanDragRecordsOut(false);
		altreOpMenu.setWidth("*");
		altreOpMenu.setHeight("*");
		
		if(showVisualizzaVersioniMenuItem()) {
			MenuItem visualizzaVersioniMenuItem = new MenuItem("Visualizza versioni", "file/version.png");
			visualizzaVersioniMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
	
				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return enableVisualizzaVersioniMenuItem();				
				}
			});
			visualizzaVersioniMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					clickVisualizzaVersioniMenuItem();
				}
			});
			altreOpMenu.addItem(visualizzaVersioniMenuItem);
		}
		
		if(showDownloadMenuItem() && !showDownloadButtomOutsideAltreOpMenu()) {
			MenuItem downloadMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(), "file/download_manager.png");
			downloadMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
	
				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return enableDownloadMenuItem();
				}
			});
			InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(form.getValue(infoFileFieldName()));
			if (lInfoFileRecord != null) {
				// Se è firmato P7M
				if (lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
					Menu showFirmatoAndSbustato = new Menu();
					MenuItem firmato = new MenuItem(
							I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
					firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							clickDownloadFileMenuItem();
						}
					});
					MenuItem sbustato = new MenuItem(
							I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
					sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							clickDownloadFileSbustatoMenuItem();
						}
					});
					showFirmatoAndSbustato.setItems(firmato, sbustato);
					downloadMenuItem.setSubmenu(showFirmatoAndSbustato);
				} else {
					downloadMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							clickDownloadFileMenuItem();
						}
					});
				}
			}
			altreOpMenu.addItem(downloadMenuItem);
		}
		
		if(showAcquisisciDaScannerMenuItem() && fileButtons.isEditing()) {			
			MenuItem acquisisciDaScannerMenuItem = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_acquisisciDaScannerMenuItem_prompt(), "file/scanner.png");
			acquisisciDaScannerMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return enableAcquisisciDaScannerMenuItem();
				}
			});
			acquisisciDaScannerMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickAcquisisciDaScannerMenuItem();
				}
			});
			altreOpMenu.addItem(acquisisciDaScannerMenuItem);			
		}
		
		if(showFirmaMenuItem() && fileButtons.isEditing()) {				
			MenuItem firmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_firmaMenuItem_prompt(),
					"file/mini_sign.png");
			firmaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return enableFirmaMenuItem();
				}
			});
			firmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickFirmaMenuItem();
				}
			});			
			altreOpMenu.addItem(firmaMenuItem);			
		}
		
		if(showTimbraBarcodeMenuItems()) {
			
			// Attestato di conformità originale
			MenuItem attestatoConformitaOriginaleMenuItem = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_attestatoConformitaMenuItem(), "file/attestato.png");
			attestatoConformitaOriginaleMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return enableAttestatoConformitaOriginaleMenuItem();
				}
			});
			attestatoConformitaOriginaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickAttestatoConformitaOriginaleMenuItem();
				}
			});			
			altreOpMenu.addItem(attestatoConformitaOriginaleMenuItem);		
			
			if (getIdUd() != null && !"".equals(getIdUd())) {
				if (showAzioniTimbratura()) {								
					addTimbraBarcodeMenuItems(altreOpMenu);
				}
			}
		}
		
		final InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(form.getValue(infoFileFieldName()));
		if (lInfoFileRecord != null && Layout.isPrivilegioAttivo("SCC")) {
			String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
			MenuItem timbroConformitaCustomMenuItem = new MenuItem(labelConformitaCustom, "file/copiaConformeCustom.png");
			timbroConformitaCustomMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			timbroConformitaCustomMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							clickTimbraDatiSegnatura("COPIA_CONFORME_CUSTOM");
						}
					});

			altreOpMenu.addItem(timbroConformitaCustomMenuItem);

		}
		
		if(showEliminaMenuItem() && fileButtons.isEditing()) {				
			MenuItem eliminaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_eliminaMenuItem_prompt(),
					"file/editdelete.png");
			eliminaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return enableEliminaMenuItem();
				}
			});
			eliminaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickEliminaMenuItem();
				}
			});			
			altreOpMenu.addItem(eliminaMenuItem);
		}
		
		return altreOpMenu;
	}
	
	public void clickDownloadOutsideAltreOpMenuButton() {
		
		InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(form.getValue(infoFileFieldName()));
		if (lInfoFileRecord != null) {
			// Se è firmato P7M
			if (lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
				Menu showFirmatoAndSbustato = new Menu();
				MenuItem firmato = new MenuItem(
						I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
				firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadFileMenuItem();
					}
				});
				MenuItem sbustato = new MenuItem(
						I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
				sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadFileSbustatoMenuItem();
					}
				});
				showFirmatoAndSbustato.setItems(firmato, sbustato);
				showFirmatoAndSbustato.showContextMenu();
			} else {
				clickDownloadFileMenuItem();
			}
		}
		
	}
	
	public boolean showVisualizzaVersioniMenuItem() {
		return true;
	}
	
	public boolean enableVisualizzaVersioniMenuItem() {
		boolean enabled = false;
		if(getNroUltimaVersione() != null	&& !"".equals(getNroUltimaVersione())) {
			Integer nroUltimaVersiones = new Integer(getNroUltimaVersione());
			if (form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")) {
				if (nroUltimaVersiones != null && nroUltimaVersiones > 1) {
					enabled = true;
				}
			}
		}
		return enabled;
	}
	
	public void clickVisualizzaVersioniMenuItem() {		
		ProtocollazioneDetail.visualizzaVersioni(getIdDoc(), getTipoFile(), getNroProgr(), null, getRecordProtocollo());
	}
	
	public boolean showDownloadMenuItem() {
		return true;
	}
	
	public boolean showDownloadButtomOutsideAltreOpMenu() {
		return false;
	}
	
	public boolean enableDownloadMenuItem() {		
		return form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("");
	}
	
	public void clickDownloadFileMenuItem() {		
		String idUd = getIdUd();
		String idDoc = getIdDoc();
		addToRecent(idUd, idDoc);
		String display = form.getValueAsString(nomeFileFieldName());
		String uri = form.getValueAsString(uriFileFieldName());
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", form.getValueAsString(remoteUriFieldName()));
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
	public void clickDownloadFileSbustatoMenuItem() {		
		String idUd = getIdUd();
		String idDoc = getIdDoc();
		addToRecent(idUd, idDoc);
		String display = form.getValueAsString(nomeFileFieldName());
		String uri = form.getValueAsString(uriFileFieldName());
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", form.getValueAsString(remoteUriFieldName()));
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(form.getValue(infoFileFieldName()));
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
	public boolean showAcquisisciDaScannerMenuItem() {
		return true;
	}
	
	public boolean enableAcquisisciDaScannerMenuItem() {		
		return true;
	}
	
	public void clickAcquisisciDaScannerMenuItem() {		
		ScanUtility.openScan(new ScanCallback() {

			@Override
			public void execute(String filePdf, String uriPdf, String fileTif, String uriTif, String record) {
				InfoFileRecord precInfo = form.getValue(infoFileFieldName()) != null ? new InfoFileRecord(form.getValue(infoFileFieldName())) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				form.setValue(infoFileFieldName(), info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					form.setValue(isChangedFieldName(), true);
					manageOnChangedFile();
				}
				form.setValue(nomeFileFieldName(), filePdf);
				form.setValue(uriFileFieldName(), uriPdf);
				form.setValue(nomeFileTifFieldName(), fileTif);
				form.setValue(uriFileTifFieldName(), uriTif);
				form.setValue(remoteUriFieldName(), false);
				form.setValue(nomeFileVerPreFirmaFieldName(), filePdf);
				form.setValue(uriFileVerPreFirmaFieldName(), uriPdf);
				form.setValue(improntaVerPreFirmaFieldName(), info.getImpronta());
				form.setValue(infoFileVerPreFirmaFieldName(), info);				
				controlAfterUpload(info);	
				form.markForRedraw();
				fileButtons.markForRedraw();
				manageOnChanged();
				changedEventAfterUpload(filePdf, uriPdf);
			}
		});
	}
	
	public boolean showFirmaMenuItem() {
		return true;
	}
	
	public boolean enableFirmaMenuItem() {		
		return form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("");
	}
	
	public void clickFirmaMenuItem() {		
		firma(null);
	}
	
	public void firma(final ServiceCallback<Record> callback) {
		String display = form.getValueAsString(nomeFileFieldName());
		String uri = form.getValueAsString(uriFileFieldName());
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(form.getValue(infoFileFieldName()));		
		manageOnClickFirma(uri, display, lInfoFileRecord, new FirmaCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
				InfoFileRecord precInfo = form.getValue(infoFileFieldName()) != null ? new InfoFileRecord(form.getValue(infoFileFieldName())) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				form.setValue(infoFileFieldName(), info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					form.setValue(isChangedFieldName(), true);
					manageOnChangedFile();
				}
				form.setValue(nomeFileFieldName(), nomeFileFirmato);
				form.setValue(uriFileFieldName(), uriFileFirmato);
				form.setValue(nomeFileTifFieldName(), "");
				form.setValue(uriFileTifFieldName(), "");
				form.setValue(remoteUriFieldName(), false);	
				controlAfterUpload(info);		
				form.markForRedraw();
				fileButtons.markForRedraw();
				manageOnChanged();
				changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
				if (callback != null) {
					callback.execute(new Record(form.getValues()));
				}
			}
		});
	}
	
	public void manageOnClickFirma(String uri, String display, InfoFileRecord info, FirmaCallback firmaCallback) {
		FirmaUtility.firmaMultipla(uri, display, info, firmaCallback);
	}
	
	public boolean showTimbraBarcodeMenuItems() {
		return true;
	}
	
	public boolean hideTimbraMenuItems() {
		return false;
	}
	
	public void addTimbraBarcodeMenuItems(Menu altreOpMenu) {
		
		/**
		 * TIMBRA 
		 * Visibile solo se è presente l'uri del file
		 */
	
		//Variabile che gestisce l'aggiunta della voce unica o del sottomenu "Timbra"
		boolean flgAddSubMenuTimbra = false;		
		
		MenuItem timbraMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraMenuItem(), "file/timbra.gif");
		MenuItem timbraDatiSegnaturaMenuItem = null;
		MenuItem timbroConformitaOriginaleMenuItem = null;
		MenuItem timbroCopiaConformeMenuItem = null;
		final InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(form.getValue(infoFileFieldName()));
		if (lInfoFileRecord != null) {
			Menu timbraSubMenu = new Menu();
			timbraDatiSegnaturaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraDatiSegnaturaMenuItem(), "file/timbra.gif");
			timbraDatiSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {					
					clickTimbraDatiSegnatura("");
				}
			});
			timbraDatiSegnaturaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
				
				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
				}
			});
			timbraSubMenu.addItem(timbraDatiSegnaturaMenuItem);
			
			if (isAttivaTimbroTipologia()) {
				MenuItem timbraDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraDatiTipologiaMenuItem());
				timbraDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						// Per la funzionalità di Dati tipologia viene utilizzato l'idDoc del file
						// primario e non quello dell'allegato
						if (getIdDocPrimario() != null && !"".equals(getIdDocPrimario())) {
							clickTimbraDatiTipologia();
						} else {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().allegato_canvas_messageDocumentoNonTipizzato(), "",MessageType.WARNING));
						}
					}
				});
				timbraDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return isAttivaTimbroTipologia() && form.getValueAsString(uriFileFieldName()) != null
								&& !form.getValueAsString(uriFileFieldName()).equals("") && getIdDocPrimario() != null
								&& !getIdDocPrimario().equals("") && lInfoFileRecord != null
								&& lInfoFileRecord.isConvertibile();
					}
				});
				
				flgAddSubMenuTimbra=true;
				timbraSubMenu.addItem(timbraDatiTipologiaMenuItem);
			}
			
			
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
				if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null && 
						(lInfoFileRecord.getMimetype().equals("application/pdf") || lInfoFileRecord.getMimetype().startsWith("image"))) {
					timbroConformitaOriginaleMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroConformitaOriginaleMenuItem(), "file/timbra.gif");
					timbroConformitaOriginaleMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
						
						@Override
						public boolean execute(Canvas target, Menu menu, MenuItem item) {
							return form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
						}
					});
					timbroConformitaOriginaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							clickTimbraDatiSegnatura("CONFORMITA_ORIG_CARTACEO");
						}
					});
//					
					flgAddSubMenuTimbra=true;
					timbraSubMenu.addItem(timbroConformitaOriginaleMenuItem);
				}
			}
			
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA") && lInfoFileRecord != null) {
				flgAddSubMenuTimbra=true;
				
				timbroCopiaConformeMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem(), "file/timbra.gif");
				timbroCopiaConformeMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
					
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
					}
				});
				
				Menu sottoMenuCopiaConformeItem = new Menu();
				
				MenuItem timbroCopiaConformeStampaItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_stampa(), "file/timbra.gif");	
				timbroCopiaConformeStampaItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickTimbraDatiSegnatura("CONFORMITA_ORIG_DIGITALE_STAMPA");
					}
				});
				sottoMenuCopiaConformeItem.addItem(timbroCopiaConformeStampaItem);
				
				MenuItem timbroCopiaConformeDigitaleItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_suppDigitale(), "file/timbra.gif");	
				timbroCopiaConformeDigitaleItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickTimbraDatiSegnatura("CONFORMITA_ORIG_DIGITALE_SUPP_DIG");
					}
				});
				sottoMenuCopiaConformeItem.addItem(timbroCopiaConformeDigitaleItem);
				
				timbroCopiaConformeMenuItem.setSubmenu(sottoMenuCopiaConformeItem);				
				timbraSubMenu.addItem(timbroCopiaConformeMenuItem);
			}
			
			timbraMenuItem.setSubmenu(timbraSubMenu);
			
		}
		timbraMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile();				
			}
		});
		
		
		
		/**
		 * BARCODE SU A4
		 */
		MenuItem barcodeA4MenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4MenuItem() , "protocollazione/barcode.png");	
		Menu barcodeA4SubMenu = new Menu();
		//Dati segnatura 
		MenuItem barcodeA4NrDocumentoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4NrDocumentoMenuItem() ,"protocollazione/nr_documento.png");
		barcodeA4NrDocumentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {				
				clickBarcodeDatiSegnatura("","");
			}
		});
		MenuItem barcodeA4NrDocumentoPoisizioneMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4NrDocumentoPoisizioneMenuItem() ,"protocollazione/nr_documento_posizione.png");
		barcodeA4NrDocumentoPoisizioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {				
				clickBarcodeDatiSegnatura("","P");
			}
		});
		MenuItem barcodeA4DatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4DatiTipologiaMenuItem());
		barcodeA4DatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {				
				clickBarcodeDatiTipologia("T");
			}
		});
		barcodeA4DatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
			
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return isAttivaTimbroTipologia() /*&& form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")*/
					   && getIdDocPrimario() != null && !getIdDocPrimario().equals("");	
			}
		});		
		barcodeA4SubMenu.setItems(barcodeA4NrDocumentoMenuItem, barcodeA4NrDocumentoPoisizioneMenuItem, barcodeA4DatiTipologiaMenuItem);
		barcodeA4MenuItem.setSubmenu(barcodeA4SubMenu);
		
		/**
		 * BARCODE SU A4 MULTPIPLI
		 */
		MenuItem barcodeA4MultipliMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4MultipliMenuItem(), "blank.png");
		Menu barcodeMultipliA4SubMenu = new Menu();		
		//Dati segnatura
		MenuItem barcodeMultipliA4NrDocumentoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliA4NrDocumentoMenuItem(),"protocollazione/nr_documento.png");
		barcodeMultipliA4NrDocumentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickBarcodeMultipli("","");
			}
		});
		MenuItem barcodeMultipliA4NrDocPosMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliA4NrDocPosMenuItem() ,"protocollazione/nr_documento_posizione.png");
		barcodeMultipliA4NrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickBarcodeMultipli("","P");
			}
		});
		MenuItem barcodeMultipliA4DatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliA4DatiTipologiaMenuItem());
		barcodeMultipliA4DatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickBarcodeMultipli("T","");
			}
		});
		barcodeMultipliA4DatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
			
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return isAttivaTimbroTipologia() /*&& form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")*/
					   && getIdDocPrimario() != null && !getIdDocPrimario().equals("");	
			}
		});		
		barcodeMultipliA4SubMenu.setItems(barcodeMultipliA4NrDocumentoMenuItem, barcodeMultipliA4NrDocPosMenuItem, barcodeMultipliA4DatiTipologiaMenuItem);
		barcodeA4MultipliMenuItem.setSubmenu(barcodeMultipliA4SubMenu);
		
		/**
		 * BARCODE SU ETICHETTA
		 */
		MenuItem barcodeEtichettaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaMenuItem(), "protocollazione/stampaEtichetta.png");		
		Menu barcodeEtichettaSubMenu = new Menu();
		MenuItem barcodeEtichettaNrDocMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaNrDocMenuItem(),"protocollazione/nr_documento.png");
		barcodeEtichettaNrDocMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {				
				clickBarcodeDatiSegnatura("E","");
			}
		});
		MenuItem barcodeEtichettaNrDocPosMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaNrDocPosMenuItem(),"protocollazione/nr_documento_posizione.png");
		barcodeEtichettaNrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {				
				clickBarcodeDatiSegnatura("E","P");
			}
		});		
		MenuItem barcodeEtichettaDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaDatiTipologiaMenuItem());
		barcodeEtichettaDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {				
				clickBarcodeDatiTipologia("E");
			}
		});
		barcodeEtichettaDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
			
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return isAttivaTimbroTipologia() /*&& form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")*/
					   && getIdDocPrimario() != null && !getIdDocPrimario().equals("");	
			}
		});		
		barcodeEtichettaSubMenu.setItems(barcodeEtichettaNrDocMenuItem, barcodeEtichettaNrDocPosMenuItem,barcodeEtichettaDatiTipologiaMenuItem);
		barcodeEtichettaMenuItem.setSubmenu(barcodeEtichettaSubMenu);
		
		/**
		 * BARCODE SU ETICHETTA MULTIPLI
		 */		
		MenuItem barcodeEtichettaMultiploMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaMultiploMenuItem());
		Menu barcodeMultipliEtichettaSubMenu = new Menu();		
		MenuItem barcodeMultipliEtichettaNrDocMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliEtichettaNrDocMenuItem(),"protocollazione/nr_documento.png");
		barcodeMultipliEtichettaNrDocMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {				
				clickBarcodeSuEtichettaMultipli("","");
			}
		});
		MenuItem barcodeMultipliEtichettaNrDocPosMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliEtichettaNrDocPosMenuItem(),"protocollazione/nr_documento_posizione.png");
		barcodeMultipliEtichettaNrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {				
				clickBarcodeSuEtichettaMultipli("","P");
			}
		});
		MenuItem barcodeMultipliEtichettaDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliEtichettaDatiTipologiaMenuItem());
		barcodeMultipliEtichettaDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {				
				clickBarcodeSuEtichettaMultipli("T","");
			}
		});
		barcodeMultipliEtichettaDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
			
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return isAttivaTimbroTipologia() /*&& form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")*/
					   && getIdDocPrimario() != null && !getIdDocPrimario().equals("");	
			}
		});		
		barcodeMultipliEtichettaSubMenu.setItems(barcodeMultipliEtichettaNrDocMenuItem, barcodeMultipliEtichettaNrDocPosMenuItem,barcodeMultipliEtichettaDatiTipologiaMenuItem);
		barcodeEtichettaMultiploMenuItem.setSubmenu(barcodeMultipliEtichettaSubMenu);
		
		String idUd = getIdUd();
		if (idUd != null && !"".equals(idUd)) {
			
			if(AurigaLayout.getParametroDBAsBoolean("SHOW_BARCODE_MENU") && lInfoFileRecord != null){
				altreOpMenu.addItem(barcodeA4MenuItem);
				altreOpMenu.addItem(barcodeA4MultipliMenuItem);
				altreOpMenu.addItem(barcodeEtichettaMenuItem);
				altreOpMenu.addItem(barcodeEtichettaMultiploMenuItem);
			}	
			
			//Se devo mostrare il Menu timbra
			if(!hideTimbraMenuItems()){
				//Se ho piu voci aggiungo il sottoMenu Timbra
				if(flgAddSubMenuTimbra) {
					if (timbraMenuItem != null) {
						altreOpMenu.addItem(timbraMenuItem);
					}
					//Se ho un unica voce la aggiungo ad altreOperazioni con voce "Timbra"
				}else { 
					if (timbraDatiSegnaturaMenuItem != null) {
						altreOpMenu.addItem(timbraDatiSegnaturaMenuItem);
					}
				}
			}
		}
	}
	
	/**
	 * TIMBRA DATI SEGNATURA - DmpkRegistrazionedocGettimbrodigreg
	 */
	public void clickTimbraDatiSegnatura(String finalita) {
		String idDoc = form.getValueAsString(idDocFieldName());
		String nomeFile = form.getValueAsString(nomeFileFieldName());
		String uri = form.getValueAsString(uriFileFieldName());
		String remote = form.getValueAsString(remoteUriFieldName());		
		InfoFileRecord precInfo = form.getValue(infoFileFieldName()) != null ? new InfoFileRecord(form.getValue(infoFileFieldName())) : null;
		String idUd = getIdUd();
			
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
			record.setAttribute("nomeFile", nomeFile);
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
				
			FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, nomeFile, Boolean.valueOf(remote), 
					precInfo.getMimetype(), idUd, idDoc, rotazioneTimbroPref, posizioneTimbroPref);
			lFileDaTimbrareBean.setAttribute("finalita", finalita);
			lFileDaTimbrareBean.setAttribute("tipoPagina", tipoPaginaPref);
			lFileDaTimbrareBean.setAttribute("skipScelteOpzioniCopertina", "false");
			TimbraWindow lTimbraWindow = new TimbraWindow("timbra", true, lFileDaTimbrareBean);
			lTimbraWindow.show();
		}
	}
	
	/**
	 * TIMBRA DATI SEGNATURA - DmpkRegistrazionedocGettimbrospecxtipo
	 */
	public void clickTimbraDatiTipologia() {
		
		String idDocPrimario = getIdDocPrimario();
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
			
			Record record = new Record();
			record.setAttribute("idDoc", idDocPrimario);
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			
			TimbroCopertinaUtil.buildDatiTipologia(record);
			
		}else{
			
			Record copertinaTimbroRecord = new Record();
			copertinaTimbroRecord.setAttribute("idDoc", idDocPrimario);
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "T");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
		
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,false,"","T","");
			copertinaTimbroWindow.show();
		}
	}
	
	
	//*************** OPERAZIONE DA DETTAGLIO UD - BARCODE SU A4 & BARCODE SU ETICHETTA ( Dati segnatura & Dati tipologia ) ***************
	
	/**
	 *  DATI SEGNATURA - DmpkRegistrazionedocGettimbrodigreg
	 *  @param provenienza = B: barcode & E: etichetta
	 */
	public void clickBarcodeDatiSegnatura(String tipologia,String posizione) {
		
		if(tipologia != null && !"".equals(tipologia) && "E".equals(tipologia)){
			
			clickBarcodeEtichettaDatiSegnatura(posizione);
		}else{
		
			String idUd = getIdUd();
			
			String numeroAllegato = null;
			if(posizione != null && "P".equals(posizione)){
				numeroAllegato = getNroProgr();
			}
			
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
				copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
				copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
				copertinaTimbroRecord.setAttribute("provenienza", "A");
				copertinaTimbroRecord.setAttribute("posizionale", posizione);
				
				CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
				CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean);
				copertinaTimbroWindow.show();
			}
		}
	}
	
	/**
	 *  DATI TIPOLOGIA - DmpkRegistrazionedocGettimbrospecxtipo
	 */
	public void clickBarcodeDatiTipologia(String tipo){
		
		if(tipo != null && !"".equals(tipo) && "E".equals(tipo)){
			
			clickBarcodeEtichettaDatiTipologia();
		}else{
		
			String idDocPrimario = getIdDocPrimario();
			String numeroAllegato = "";
			
			String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
			String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
			
			
			if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
				
				Record record = new Record();
				record.setAttribute("idDoc", idDocPrimario);
				record.setAttribute("numeroAllegato", numeroAllegato);
				record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
				record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
				record.setAttribute("skipScelteOpzioniCopertina", "true");
				
				TimbroCopertinaUtil.buildDatiTipologia(record);
				
			}else{
				
				Record copertinaTimbroRecord = new Record();
				copertinaTimbroRecord.setAttribute("idDoc", idDocPrimario);
				copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "T");
				copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
				copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
				
				CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
				CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,false,"","T","");
				copertinaTimbroWindow.show();
			}
		}
	}
	
	/**
	 * Barcode multipli su A4 - Dati segnatura & tipologia 
	 */
	public void clickBarcodeMultipli(String tipo,String posizionale){
		
		String idUd = getIdUd();
		String idDocPrimario = getIdDocPrimario();
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
				
		String numeroAllegato = null;
		if(posizionale != null && "P".equals(posizionale)){
			numeroAllegato = getNroProgr();
		}
				
		if(tipo != null && !"".equals(tipo) && "T".equals(tipo)){
			
			Record copertinaTimbroRecord = new Record();
			copertinaTimbroRecord.setAttribute("idDoc", idDocPrimario);
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "T");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
			
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,true,"","T","");
			copertinaTimbroWindow.show();
		}else{
			
			Record copertinaTimbroRecord = new Record();
			copertinaTimbroRecord.setAttribute("idUd", idUd);
			copertinaTimbroRecord.setAttribute("numeroAllegato", numeroAllegato);
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
			copertinaTimbroRecord.setAttribute("provenienza", "P");
			copertinaTimbroRecord.setAttribute("posizionale", posizionale);
			
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,true,"","",posizionale);
			copertinaTimbroWindow.show();
		}
	}
	
	/**
	 * Barcode multipli su etichetta
	 */
	public void clickBarcodeSuEtichettaMultipli(String tipo,String posizione) {
			
		String idUd = getIdUd();
		String idDocPrimario = getIdDocPrimario();
		
		final Record record = new Record();
		record.setAttribute("idUd", idUd);
		record.setAttribute("isMultiple", "true");
		if(tipo != null && "T".equals(tipo)){
			record.setAttribute("tipologia", "T");
			record.setAttribute("idDoc", idDocPrimario);
		}
		
		if(posizione != null && "P".equals(posizione)){
			record.setAttribute("nrAllegato", getNroProgr());
		}
		record.setAttribute("posizione", posizione);
		
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
	
	/**
	 * Barcode su etichetta - Dati segnatura
	 */
	public void clickBarcodeEtichettaDatiSegnatura(String posizione) {
		
		final String idUd = getIdUd();
		
		final String nrAllegato = posizione != null && "P".equals(posizione) ? getNroProgr() : null;
		
		final Record recordToPrint = new Record();
		recordToPrint.setAttribute("idUd", idUd);
		recordToPrint.setAttribute("numeroAllegato", nrAllegato);
		recordToPrint.setAttribute("nomeStampantePred", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
	
		/**
		 * Se non è presente la stampante per i barcode su etichette predefinita, allora propone la scelta
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
	
	/**
	 * Barcode su etichetta - Dati tipologia
	 */
	public void clickBarcodeEtichettaDatiTipologia() {
		
		String idDocPrimario = getIdDocPrimario();
		
		final Record recordToPrint = new Record();
		recordToPrint.setAttribute("idDoc", idDocPrimario);
		recordToPrint.setAttribute("numeroAllegato", "");
		recordToPrint.setAttribute("nomeStampantePred", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
	
		/**
		 * Se non è presente la stampante per i barcode su etichette predefinita, allora propone la scelta
		 */
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null	&& !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
			CopertinaEtichettaUtil.stampaEtichettaDatiTipologia(recordToPrint);
		} else {
			PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {
				
				@Override
				public void execute(String nomeStampante) {
					CopertinaEtichettaUtil.stampaEtichettaDatiTipologia(recordToPrint);
				}
			}, null);
		}
	}
	
	public boolean enableAttestatoConformitaOriginaleMenuItem() {		
		String nroProtocollo = getRecordProtocollo() != null ? getRecordProtocollo().getAttribute("nroProtocollo") : null;
		if(nroProtocollo != null && !"".equals(nroProtocollo) && (form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals(""))) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(form.getValue(infoFileFieldName()));
			return lInfoFileRecord != null;
		}
		return false; 
	}
	
	public void clickAttestatoConformitaOriginaleMenuItem() {		
		final InfoFileRecord info = form.getValue(infoFileFieldName()) != null
				? new InfoFileRecord(form.getValue(infoFileFieldName())) : null;
		final String uri = form.getValueAsString(uriFileFieldName());
		SC.ask("Vuoi firmare digitalmente l'attestato ?", new BooleanCallback() {

			@Override
			public void execute(Boolean value) {
				if (value) {
					creaAttestato(info, uri, true);
				} else {
					creaAttestato(info, uri, false);
				}
			}
		});
	}
	
	public void creaAttestato(final InfoFileRecord infoFile, String uriFile, final boolean attestatoFirmato) {
		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFile);
		record.setAttribute("uriAttach", uriFile);
		if(getRecordProtocollo() != null) {
			record.setAttribute("siglaProtocollo", getRecordProtocollo().getAttribute("siglaProtocollo"));
			record.setAttribute("nroProtocollo", getRecordProtocollo().getAttribute("nroProtocollo"));
			record.setAttribute("desUserProtocollo", getRecordProtocollo().getAttribute("desUserProtocollo"));
			record.setAttribute("desUOProtocollo", getRecordProtocollo().getAttribute("desUOProtocollo"));
			record.setAttribute("idUd", getIdUd());
			record.setAttribute("idDoc", getIdDoc());
			record.setAttribute("attestatoFirmatoDigitalmente", attestatoFirmato);
			try {
				record.setAttribute("dataProtocollo", DateUtil.formatAsShortDatetime((getRecordProtocollo().getAttributeAsDate("dataProtocollo"))));
			} catch (Exception e) {
			}
		}
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
		lGwtRestDataSource.extraparam.put("urlContext", GWT.getHostPageBaseURL());
		lGwtRestDataSource.executecustom("stampaAttestato", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Record data = response.getData()[0];
				final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
				final String filename = infoFile.getCorrectFileName();
				final String uri = data.getAttribute("tempFileOut");
				if (!attestatoFirmato) {
					Record lRecord = new Record();
					lRecord.setAttribute("displayFilename", filename);
					lRecord.setAttribute("uri", uri);
					lRecord.setAttribute("sbustato", "false");
					lRecord.setAttribute("remoteUri", false);
					DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
				} else {
					FirmaUtility.firmaMultipla(uri, filename, infoFile, new FirmaCallback() {

						@Override
						public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
							Record lRecord = new Record();
							lRecord.setAttribute("displayFilename", nomeFileFirmato);
							lRecord.setAttribute("uri", uriFileFirmato);
							lRecord.setAttribute("sbustato", "false");
							lRecord.setAttribute("remoteUri", false);
							DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
						}
					});
				}
			}
		});
	}
	
	public boolean showEliminaMenuItem() {
		return true;
	}
	
	public boolean enableEliminaMenuItem() {
		return form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("");
	}
	
	public void clickEliminaMenuItem() {		
		clickEliminaFile();
	}
	
	public void clickEliminaFile() {
		form.setValue(nomeFileFieldName(), "");
		form.setValue(uriFileFieldName(), "");
		form.clearValue(infoFileFieldName());	
		form.setValue(nomeFileTifFieldName(), "");
		form.setValue(uriFileTifFieldName(), "");
		form.setValue(remoteUriFieldName(), false);	
		form.setValue(nomeFileVerPreFirmaFieldName(), "");
		form.setValue(uriFileVerPreFirmaFieldName(), "");
		form.setValue(improntaVerPreFirmaFieldName(), "");
		form.clearValue(infoFileVerPreFirmaFieldName());			
		form.markForRedraw();
		fileButtons.markForRedraw();
		manageOnChanged();
		uploadItem.getCanvas().redraw();		
	}
	
	public boolean showGeneraDaModelloButton() {
		return getTipoDoc() != null && !"".equals(getTipoDoc());
	}
	
	public boolean isModelloModificabile(String idModello) {
		return true;
	}
	
	public void clickGeneraDaModelloButton() {
//		if (form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")) {
//			SC.ask("E' gia presente un file. Vuoi procedere alla generazione sovrascrivendo il file esistente?", new BooleanCallback() {
//
//				@Override
//				public void execute(Boolean value) {
//
//					if (value != null && value) {
//						generaDaModello();
//					}
//				}
//			});
//		} else {
//			generaDaModello();
//		}
		generaDaModello();
	}
	
	public void generaDaModello() {		
		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboGeneraDaModelloDataSource", new String[] { "codice", "descrizione" }, true);
		lGwtRestDataSource.addParam("idTipoDocumento", getTipoDoc());
		lGwtRestDataSource.addParam("idProcess", getIdProcGeneraDaModello());
		lGwtRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				RecordList data = response.getDataAsRecordList();
				if (data.getLength() > 1) {
					SelezionaGeneraDaModelloWindow generaDaModelloWindow = new SelezionaGeneraDaModelloWindow(data) {

						@Override
						public void caricaModelloSelezionato(String idModello, String tipoModello, String flgConvertiInPdf) {
							afterSelezioneModello(idModello, tipoModello, flgConvertiInPdf);
						}
					};
					generaDaModelloWindow.show();
				} else if (data.getLength() == 1) {
					String idModello = data.get(0).getAttribute("idModello");
					if (idModello != null && !"".equals(idModello)) {
						afterSelezioneModello(idModello, data.get(0).getAttribute("tipoModello"), data.get(0).getAttribute("flgConvertiInPdf"));
					}
				} else {
					Layout.addMessage(new MessageBean("Nessun modello da caricare!", "", MessageType.INFO));
				}
			}
		});
	}
	
	private void afterSelezioneModello(final String idModello, final String tipoModello, final String flgConvertiInPdf) {
		if (isModelloModificabile(idModello) && form.getValueAsString(uriFileFieldName()) != null && !form.getValueAsString(uriFileFieldName()).equals("")) {
			SC.ask("E' gia presente un file. Vuoi procedere alla generazione sovrascrivendo il file esistente?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {

					if (value != null && value) {
						caricaModello(idModello, tipoModello, flgConvertiInPdf);
					}
				}
			});
		} else {
			caricaModello(idModello, tipoModello, flgConvertiInPdf);
		}
	}
	
	public void caricaModello(String idModello, String tipoModello, String flgConvertiInPdf) {
		caricaModello(idModello, tipoModello, flgConvertiInPdf, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				afterCaricaModello(object);
			}
		});
	}
	
	public Record getRecordCaricaModello(String idModello, String tipoModello) {
		final Record modelloRecord = new Record();
		modelloRecord.setAttribute("idModello", idModello);
		modelloRecord.setAttribute("tipoModello", tipoModello);
		modelloRecord.setAttribute("idUd", getIdUd());
		return modelloRecord;
	}
	
	public GWTRestDataSource getGeneraDaModelloDataSource(String idModello, String tipoModello, String flgConvertiInPdf) {
		final GWTRestDataSource lGeneraDaModelloDataSource = new GWTRestDataSource("GeneraDaModelloWithDatiDocDataSource");
		lGeneraDaModelloDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
		return lGeneraDaModelloDataSource;
	}
	
	public void caricaModello(String idModello, String tipoModello, String flgConvertiInPdf, final ServiceCallback<Record> afterCallback) {
		GWTRestDataSource lGeneraDaModelloDataSource = getGeneraDaModelloDataSource(idModello, tipoModello, flgConvertiInPdf);
		if(lGeneraDaModelloDataSource != null) {
			lGeneraDaModelloDataSource.executecustom("caricaModello", getRecordCaricaModello(idModello, tipoModello), new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						if (afterCallback != null) {
							afterCallback.execute(response.getData()[0]);
						}
					}
				}
			});
		}
	}

	public void afterCaricaModello(Record record) {
		String nomeFile = record.getAttribute("nomeFilePrimario");
		String uriFile = record.getAttribute("uriFilePrimario");
		form.setValue(nomeFileFieldName(), nomeFile);
		form.setValue(uriFileFieldName(), uriFile);
		form.setValue(nomeFileTifFieldName(), "");
		form.setValue(uriFileTifFieldName(), "");
		form.setValue(remoteUriFieldName(), false);
		form.setValue(nomeFileVerPreFirmaFieldName(), nomeFile);
		form.setValue(uriFileVerPreFirmaFieldName(), uriFile);
		changedEventAfterUpload(nomeFile, uriFile);
		InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
		form.setValue(improntaVerPreFirmaFieldName(), info.getImpronta());
		form.setValue(infoFileVerPreFirmaFieldName(), info);
		InfoFileRecord precInfo = form.getValue(infoFileFieldName()) != null ? new InfoFileRecord(form.getValue(infoFileFieldName())) : null;
		String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
		form.setValue("infoFile", info);
		String nomeFileOrig = form.getValueAsString(nomeFileOrigFieldName());
		if (precImpronta == null || !precImpronta.equals(info.getImpronta()) 
			|| (nomeFile != null && !"".equals(nomeFile) && nomeFileOrig != null && !"".equals(nomeFileOrig) && !nomeFile.equals(nomeFileOrig))) {
			form.setValue(isChangedFieldName(), true);
			manageOnChangedFile();
		}
		controlAfterUpload(info);		
		form.markForRedraw();
		fileButtons.markForRedraw();
		manageOnChanged();
		if (showEditButton()) {
			clickEditButton();
		} else {
			clickPreviewButton();
		}
	}
	
	public String getWarningMessageOnUpload() {
		return null;
	}
	
	public void controlAfterUpload(InfoFileRecord info) {
		AllegatoCanvas lAllegatoCanvas = getAllegatoCanvas();
		ReplicableCanvasForm lAllegatoCanvasFileForm = getAllegatoCanvasFileForm(lAllegatoCanvas);
		boolean flgParteDispositivo = lAllegatoCanvasFileForm != null && lAllegatoCanvasFileForm.getValue("flgParteDispositivo") != null && new Boolean(lAllegatoCanvasFileForm.getValueAsString("flgParteDispositivo"));
		boolean flgNoPubblAllegato = lAllegatoCanvasFileForm != null && lAllegatoCanvasFileForm.getValue("flgNoPubblAllegato") != null	&& new Boolean(lAllegatoCanvasFileForm.getValueAsString("flgNoPubblAllegato"));
		boolean flgDatiSensibili = lAllegatoCanvasFileForm != null && lAllegatoCanvasFileForm.getValue("flgDatiSensibili") != null	&& new Boolean(lAllegatoCanvasFileForm.getValueAsString("flgDatiSensibili"));
		boolean showFileOmissis = lAllegatoCanvas !=null && (AllegatiItem)lAllegatoCanvas.getItem() !=null && ((AllegatiItem)lAllegatoCanvas.getItem()).getShowVersioneOmissis() && flgDatiSensibili;				
//		boolean flgPubblicaSeparato = false;
//		if(((AllegatiItem)lAllegatoCanvas.getItem()).isShowFlgPubblicaSeparato()) {
//			if(((AllegatiItem)lAllegatoCanvas.getItem()).isFlgPubblicazioneAllegatiUguale()) {
//				flgPubblicaSeparato = ((AllegatiItem)lAllegatoCanvas.getItem()).isFlgPubblicaAllegatiSeparati();
//			} else {
//				flgPubblicaSeparato = lAllegatoCanvas != null && lAllegatoCanvasFileForm.getValue("flgPubblicaSeparato") != null && new Boolean(lAllegatoCanvasFileForm.getValueAsString("flgPubblicaSeparato"));	
//			}
//		}
//		if (((AllegatiItem) getItem()).isProtInModalitaWizard() && ((AllegatiItem) getItem()).isCanaleSupportoCartaceo() &&
//			(info == null || info.getMimetype() == null || (!info.getMimetype().equals("application/pdf") && !info.getMimetype().startsWith("image")))) {
//			GWTRestDataSource.printMessage(new MessageBean(
//					"Il file non è un'immagine come atteso: poiché il canale/supporto originale specificato indica che il documento è cartaceo puoi allegare solo la/le immagini - scansioni o foto - che ne rappresentano la copia digitale",
//					"", MessageType.ERROR));		
//			/*
//		 	* Visto l'errore dovuto al fatto che il file non è nel formato richiesto
//		 	* rimuovo le informazioni associate al file primario di cui si è tentato l'inserimento.
//		 	* In questo modo all'aggiornamento della grafica non viene riempita la text e non vengono
//		 	* abilitati i pulsanti di firma, etc. (normalmente abilitati nel caso di file in 
//		 	* formato corretto) 
//		 	*/
//			clickEliminaFile();			
//		}		
		if (!isFormatoAmmesso(info)) {
			GWTRestDataSource.printMessage(new MessageBean("Il file risulta in un formato non ammesso", "", MessageType.WARNING));
		} else if(lAllegatoCanvas != null && lAllegatoCanvasFileForm.getValue("flgParteDispositivo") != null && new Boolean(lAllegatoCanvasFileForm.getValueAsString("flgParteDispositivo")) && !isFormatoAmmessoParteIntegrante(info)) {
			GWTRestDataSource.printMessage(new MessageBean("Il file risulta in un formato non ammesso per un allegato parte integrante", "", MessageType.WARNING));
//			clickEliminaFile();
		} 
		if(flgParteDispositivo && !PreviewWindow.isPdfConvertibile(info) /*&& !flgPubblicaSeparato*/) {
			if(lAllegatoCanvas != null) {
				if(((AllegatiItem)lAllegatoCanvas.getItem()).isShowFlgPubblicaSeparato()) {
					if(((AllegatiItem)lAllegatoCanvas.getItem()).isFlgPubblicazioneAllegatiUguale()) {
						((AllegatiItem)lAllegatoCanvas.getItem()).setFlgPubblicaAllegatiSeparati(true);
					} else {
						lAllegatoCanvasFileForm.setValue("flgPubblicaSeparato", true);
					}
				}
				GWTRestDataSource.printMessage(new MessageBean("File non convertibile in formato pdf: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING));				
			}
		}
		if (info.isFirmato()) {
			String rifiutoAllegatiConFirmeNonValide = getRifiutoAllegatiConFirmeNonValide();
			if(!info.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null && !"".equals(rifiutoAllegatiConFirmeNonValide)) { 
				if(lAllegatoCanvas != null) {
					if("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
						flgParteDispositivo = false;
						lAllegatoCanvasFileForm.setValue("flgParteDispositivo", false);
						flgNoPubblAllegato = true;
						lAllegatoCanvasFileForm.setValue("flgNoPubblAllegato", true);
//						flgPubblicaSeparato = false;
						lAllegatoCanvasFileForm.setValue("flgPubblicaSeparato", false);
						if(showFileOmissis) {
							flgDatiSensibili = false;
							showFileOmissis = false;
							lAllegatoCanvasFileForm.setValue("flgDatiSensibili", false);
							lAllegatoCanvas.manageOnChangedFlgDatiSensibili();
						}						
						GWTRestDataSource.printMessage(new MessageBean("Il file presenta firme non valide alla data odierna e non può essere caricato come allegato parte integrante: è stato automaticamente inserito come allegato NON parte integrante", "", MessageType.WARNING));
					} else {
						GWTRestDataSource.printMessage(new MessageBean("Il file presenta firme non valide alla data odierna", "", MessageType.WARNING));
					}
				} else {
					GWTRestDataSource.printMessage(new MessageBean("Il file presenta firme non valide alla data odierna", "", MessageType.WARNING));
				}
			} else if(isDisattivaUnioneAllegatiFirmati()) {
				if(lAllegatoCanvas != null) {
					if(flgParteDispositivo && !flgNoPubblAllegato) {
						if(((AllegatiItem)lAllegatoCanvas.getItem()).isShowFlgPubblicaSeparato()) {
							if(((AllegatiItem)lAllegatoCanvas.getItem()).isFlgPubblicazioneAllegatiUguale()) {
								((AllegatiItem)lAllegatoCanvas.getItem()).setFlgPubblicaAllegatiSeparati(true);
							} else {
								lAllegatoCanvasFileForm.setValue("flgPubblicaSeparato", true);														
							}
						}
//						GWTRestDataSource.printMessage(new MessageBean("Il file è firmato. Si consiglia pubblicazione separata.", "", MessageType.WARNING));
					}
				}
			}
		}
		boolean pubblicazioneSeparataPdfProtetti = isPubblicazioneSeparataPdfProtetti();
		if(flgParteDispositivo && pubblicazioneSeparataPdfProtetti && info.isPdfProtetto() /*&& !flgPubblicaSeparato*/) {
			if(lAllegatoCanvas != null) {
				if(((AllegatiItem)lAllegatoCanvas.getItem()).isShowFlgPubblicaSeparato()) {
					if(((AllegatiItem)lAllegatoCanvas.getItem()).isFlgPubblicazioneAllegatiUguale()) {
						((AllegatiItem)lAllegatoCanvas.getItem()).setFlgPubblicaAllegatiSeparati(true);
					} else {
						lAllegatoCanvasFileForm.setValue("flgPubblicaSeparato", true);
					}
				}
				GWTRestDataSource.printMessage(new MessageBean("File protetto da qualsiasi modifica: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING));				
			}
		}
		final float MEGABYTE = 1024L * 1024L;
		long dimAlertAllegato = getDimAlertAllegato(); // questo è in bytes
		long dimMaxAllegatoXPubblInMB = getDimMaxAllegatoXPubbl(); // questa è in MB
		if(dimMaxAllegatoXPubblInMB > 0 && info != null && info.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE)) {						
			if(lAllegatoCanvas != null) {
				if(flgParteDispositivo) {
					if(((AllegatiItem)lAllegatoCanvas.getItem()).isShowFlgNoPubblAllegato()) {
						lAllegatoCanvasFileForm.setValue("flgNoPubblAllegato", true);																				
					}	
					if(((AllegatiItem)lAllegatoCanvas.getItem()).isShowFlgPubblicaSeparato()) {
						if(((AllegatiItem)lAllegatoCanvas.getItem()).isFlgPubblicazioneAllegatiUguale()) {
							((AllegatiItem)lAllegatoCanvas.getItem()).setFlgPubblicaAllegatiSeparati(true);
						} else {
							lAllegatoCanvasFileForm.setValue("flgPubblicaSeparato", true);														
						}
					}
					GWTRestDataSource.printMessage(new MessageBean("La dimensione del file è superiore alla soglia di " + dimMaxAllegatoXPubblInMB + " MB consentita per la pubblicazione", "", MessageType.WARNING));
				}
			}	
		} else if(dimAlertAllegato > 0 && info != null && info.getBytes() > dimAlertAllegato) {
			float dimensioneInMB = info.getBytes() / MEGABYTE;						
			if(lAllegatoCanvas != null) {
				if(flgParteDispositivo && !flgNoPubblAllegato) {
					if(((AllegatiItem)lAllegatoCanvas.getItem()).isShowFlgPubblicaSeparato()) {
						if(((AllegatiItem)lAllegatoCanvas.getItem()).isFlgPubblicazioneAllegatiUguale()) {
							((AllegatiItem)lAllegatoCanvas.getItem()).setFlgPubblicaAllegatiSeparati(true);
						} else {
							lAllegatoCanvasFileForm.setValue("flgPubblicaSeparato", true);														
						}
					}						
					GWTRestDataSource.printMessage(new MessageBean("Attenzione: la dimensione del file è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB. Si consiglia pubblicazione separata.", "", MessageType.WARNING));
				} else {						
					GWTRestDataSource.printMessage(new MessageBean("Attenzione: la dimensione del file è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB", "", MessageType.WARNING));
				}
				lAllegatoCanvasFileForm.markForRedraw();
			} else {
				GWTRestDataSource.printMessage(new MessageBean("Attenzione: la dimensione del file è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB", "", MessageType.WARNING));						
			}						
		}		
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
	
	public void changedEventAfterUpload(final String displayFileName, final String uri) {
		ChangedEvent lChangedEventDisplay = new ChangedEvent(form.getItem(nomeFileFieldName()).getJsObj()) {

			public DynamicForm getForm() {
				return form;
			};

			@Override
			public FormItem getItem() {
				return form.getItem(nomeFileFieldName());
			}

			@Override
			public Object getValue() {
				return displayFileName;
			}
		};
		ChangedEvent lChangedEventUri = new ChangedEvent(form.getItem(uriFileFieldName()).getJsObj()) {

			public DynamicForm getForm() {
				return form;
			};

			@Override
			public FormItem getItem() {
				return form.getItem(uriFileFieldName());
			}

			@Override
			public Object getValue() {
				return uri;
			}
		};
		form.getItem(nomeFileFieldName()).fireEvent(lChangedEventDisplay);
		form.getItem(uriFileFieldName()).fireEvent(lChangedEventUri);
	}
		
	// field names
	public String idUdFieldName() { return "idUd" + getSuffixFieldName(); };	
	public String idDocFieldName() { return "idDoc" + getSuffixFieldName(); };	
	public String nomeFileFieldName() { return "nomeFile" + getSuffixFieldName(); };	
	public String uriFileFieldName() { return "uriFile" + getSuffixFieldName(); };
	public String infoFileFieldName() { return "infoFile" + getSuffixFieldName(); };
	public String remoteUriFieldName() { return "remoteUri" + getSuffixFieldName(); };
	public String isChangedFieldName() { return "isChanged" + getSuffixFieldName(); };
	public String nomeFileOrigFieldName() { return "nomeFileOrig" + getSuffixFieldName(); };
	public String nomeFileTifFieldName() { return "nomeFileTif" + getSuffixFieldName(); };
	public String uriFileTifFieldName() { return "uriFileTif" + getSuffixFieldName(); };	
	public String nomeFileVerPreFirmaFieldName() { return "nomeFileVerPreFirma" + getSuffixFieldName(); };
	public String uriFileVerPreFirmaFieldName() { return "uriFileVerPreFirma" + getSuffixFieldName(); };
	public String improntaVerPreFirmaFieldName() { return "improntaVerPreFirma" + getSuffixFieldName(); };
	public String infoFileVerPreFirmaFieldName() { return "infoFileVerPreFirma" + getSuffixFieldName(); };
	public String nroUltimaVersioneFieldName() { return "nroUltimaVersione" + getSuffixFieldName(); };	
	
	public String getSuffixFieldName() {
		return "";
	}
	
	// da utilizzare solo quando FileButtonsItem è utilizzato negli allegati, per i bottoni della versione con omissis
	public AllegatoCanvas getAllegatoCanvas() {
		return null;
	}
	
	public ReplicableCanvasForm getAllegatoCanvasFileForm(AllegatoCanvas lAllegatoCanvas) {
		if(lAllegatoCanvas != null) {
			return (lAllegatoCanvas instanceof DocPraticaPregressaCanvas) ? lAllegatoCanvas.getForm()[1] : lAllegatoCanvas.getForm()[0];
		}
		return null;
	}
	
	// abstract methods		
	public abstract void manageOnChanged();
	public abstract void manageOnChangedFile();
	
	public String getIdUd() {
		return form.getValueAsString(idUdFieldName());
	}
	public String getIdDoc() {
		return form.getValueAsString(idDocFieldName());
	}	
	public String getNroUltimaVersione() {
		return form.getValueAsString(nroUltimaVersioneFieldName());
	}		
	// per la funzionalità di Dati tipologia viene utilizzato l'idDoc del file primario e non quello dell'allegato
	public String getIdDocPrimario() {
		return null;
	}
	public String getTipoDoc() {
		return null;
	}
	public String getFlgTipoProv() {
		return null;
	}
	public String getIdProcGeneraDaModello() {
		return null;
	}
	public boolean canBeEditedByApplet() {
		return false;
	}
	public Date getDataRifValiditaFirma() {
		return null;
	}
	public Record getRecordProtocollo() {
		return null;
	}
	public String getEstremiProtocollo() {
		return "";
	}
	public boolean isAttivaTimbroTipologia() {
		return true;
	}
	// (FP: File Primario; A: Allegato; AO: Allegato con omissis; DI: Documento Istruttoria)
	public String getTipoFile() {
		return null;
	}
	// N.ro allegato o del documento istruttoria. In caso di file primario è 0.
	public String getNroProgr() {
		return "0";
	}	
	public boolean isFormatoAmmesso(InfoFileRecord info) {	
		return true;
	}
	public boolean isFormatoAmmessoParteIntegrante(InfoFileRecord info) {	
		return true;
	}
	public String getRifiutoAllegatiConFirmeNonValide() {
		return null;
	}
	public boolean isDisattivaUnioneAllegatiFirmati() {
		return false;
	}
	public boolean isPubblicazioneSeparataPdfProtetti() {
		return false;
	}
	public boolean showAzioniTimbratura() {
		return true;
	}

}
