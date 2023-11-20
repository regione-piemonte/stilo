/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.VisualizzaFatturaWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import java.util.HashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

/**
 * 
 * @author Cristiano
 *
 */

public class AttachmentMultipleCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;

	protected HiddenItem uriAttachItem;
	protected HiddenItem fileNameAttachItem;

	protected TextItem nomeFileItem;
	protected ImgButtonItem downloadButtonItem;
	protected ImgButtonItem previewAllegatoItem;

	protected InfoFileRecord infoFileRecord;

	public AttachmentMultipleCanvas(HashMap<String, String> parameters) {
		super(null, parameters);
	}

	public AttachmentMultipleCanvas() {
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setNumCols(9);
		mDynamicForm.setColWidths(80, 1, 1, 1, 1, 1, 1, "*", "*");

		fileNameAttachItem = new HiddenItem("fileNameAttach");
		uriAttachItem = new HiddenItem("uriAttach");

		nomeFileItem = new TextItem("nome", "Nome");
		nomeFileItem.setTextAlign(Alignment.LEFT);
		nomeFileItem.setWidth(280);
		nomeFileItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				item.setValue(fileNameAttachItem.getValue());
				return (fileNameAttachItem.getValue() != null && !fileNameAttachItem.getValue().equals(""));
			}
		});

		downloadButtonItem = new ImgButtonItem("downloadButton", "file/download_manager.png", "Download");
		downloadButtonItem.setAlwaysEnabled(true);
		downloadButtonItem.setColSpan(1);
		downloadButtonItem.setIconWidth(16);
		downloadButtonItem.setIconHeight(16);
		downloadButtonItem.setIconVAlign(VerticalAlignment.BOTTOM);
		downloadButtonItem.setAlign(Alignment.LEFT);
		downloadButtonItem.setWidth(16);
		downloadButtonItem.setStartRow(false);
		downloadButtonItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageDownloadClick();
			}
		});

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
					String display = mDynamicForm.getValueAsString("fileNameAttach");
					String uri = mDynamicForm.getValueAsString("uriAttach");
					InfoFileRecord lInfoFileRecord = ((AttachmentMultipleReplicableItem) getItem()).getInfoFileRecord();
					toShowPreview = PreviewWindow.canBePreviewed(lInfoFileRecord);
				}
				return toShowPreview;
			}

		});

		mDynamicForm.setFields(fileNameAttachItem, uriAttachItem, nomeFileItem, downloadButtonItem, previewAllegatoItem);

		addChild(mDynamicForm);
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

	// Preview del file
	public void clickPreviewFile() {
		// String uri = mDynamicForm.getValueAsString("uri");
		String uri = mDynamicForm.getValueAsString("uriAttach");
		if (uri.equals("_noUri")) {
			((AttachmentMultipleReplicableItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList(((AttachmentMultipleReplicableItem) getItem()).getName()).get(i);
					realClickPreviewFile(lRecord.getAttribute("uriAttach"));
				}
			});
		} else {
			realClickPreviewFile(mDynamicForm.getValueAsString("uriAttach"));
		}

	}

	protected void realClickPreviewFile(final String uri) {
		final String display = mDynamicForm.getValueAsString("fileNameAttach");
		final String uriFile = mDynamicForm.getValueAsString("uriAttach");
		final Boolean remoteUri = true;

		final InfoFileRecord info = ((AttachmentMultipleReplicableItem) getItem()).getInfoFileRecord();

		if ((info != null)) {
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

	public void preview(String uri, String display, Boolean remoteUri, InfoFileRecord info) {
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display);
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, remoteUri, info, "FileToExtractBean");
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

	public void changedEventAfterUpload(final String displayFileName, final String uri) {
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
}
