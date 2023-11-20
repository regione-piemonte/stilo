/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.visualizzafileeml.VisualizzaFileEmlWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class ApponiTimbroWindow extends ModalWindow {

	protected ApponiTimbroWindow window;
	private ApponiTimbroForm form;
	private VLayout layout;
	protected static HTMLPane htmlPane;
	protected static Img img;

	public ApponiTimbroWindow(final Record record, final ServiceCallback<Record> afterSaveCallback) {
		super("apponi_timbro_window", true);

		window = this;

		setTitle("Opzioni timbro");

		form = new ApponiTimbroForm(record);

		Button salvaButton = new Button("Salva");
		salvaButton.setIcon("ok.png");
		salvaButton.setIconSize(16);
		salvaButton.setAutoFit(false);
		salvaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				if (form.validate()) {
					if (afterSaveCallback != null) {
						afterSaveCallback.execute(form.getValuesAsRecord());
						close();
					}
				}
			}
		});

		Button annullaButton = new Button("Annulla");
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				close();
			}
		});

		HStack buttons = new HStack(5);
		buttons.setAlign(Alignment.CENTER);
		buttons.setPadding(5);
		buttons.addMember(salvaButton);
		buttons.addMember(annullaButton);

		setAlign(Alignment.CENTER);
		setTop(50);

		layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();

		layout.addMember(form);
		layout.addMember(buttons);

		/*Devo mostrare la preview del file solo se sto scegliendo le impostazioni di timbratura per un singolo file*/
		if((!record.getAttributeAsBoolean("scaricoZip")) && (!record.getAttributeAsBoolean("skipPreview"))) {
			addPreview(record);
		}else {
			setHeight(180);
			setWidth(600);
		}

		addItem(layout);

		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				close();
			}
		});

		setHeaderIcon("file/timbra.gif");
	}

	public void close() {
		window.markForDestroy();
	}

	private void addPreview(Record record) {

		final String display = record.getAttributeAsString("nomeFile");
		final String uri = record.getAttributeAsString("uriFile");
		final Boolean remoteUri = Boolean.valueOf(record.getAttributeAsString("remote"));
		final InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));

		preview(null, null, display, uri, remoteUri, info);
	}

	private void preview(final Record detailRecord, String idUd, final String display, final String uri,
			final Boolean remoteUri, InfoFileRecord info) {
		switchPreview(uri, remoteUri, info, "FileToExtractBean", display);
	}

	private void switchPreview(String uri, Boolean remoteUri, InfoFileRecord info, String recordType, String filename) {

		if (isFileImage(info)) {
			manageImagePreview(uri, remoteUri, info);
		} else {
			managePreview("", null, uri, remoteUri, info, recordType, filename);
		}
	}

	private static boolean isFileImage(InfoFileRecord info) {
		return info != null && info.getMimetype() != null && info.getMimetype().startsWith("image")
				&& !info.getMimetype().equals("image/tiff");
	}

	private void manageImagePreview(final String uri, Boolean remoteUri, InfoFileRecord info) {
		
		Record lRecord = new Record();
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("remote", remoteUri);
		lRecord.setAttribute("recordType", "FileToExtractBean");
		lRecord.setAttribute("infoFile", info);
		// Recupero le dimensioni della window
		int width = com.google.gwt.user.client.Window.getClientWidth();
		int height = com.google.gwt.user.client.Window.getClientHeight();
		lRecord.setAttribute("width", width);
		lRecord.setAttribute("height", height);
		new GWTRestService<Record, Record>("PreviewFileDatasource").call(lRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				manageResponse(object);
			}
		});
	}

	private void managePreview(String keyPreview, String title, final String pUri, Boolean remoteUri,
			InfoFileRecord lInfoFileRecord, String recordType, String filename) {

		if (isToShowEml(lInfoFileRecord, filename)) {

			lInfoFileRecord.setAttribute("key", filename);
			lInfoFileRecord.setAttribute("value", pUri);

			VisualizzaFileEmlWindow emlWindow = new VisualizzaFileEmlWindow("visualizzaEML", lInfoFileRecord, title);
			emlWindow.addCloseClickHandler(new CloseClickHandler() {

				@Override
				public void onCloseClick(CloseClickEvent event) {
					AurigaLayout.getOpenedViewers().remove(pUri);
				}
			});
		} else {
			Record lRecord = new Record();
			lRecord.setAttribute("uri", pUri);
			lRecord.setAttribute("remote", remoteUri);
			lRecord.setAttribute("infoFile", lInfoFileRecord);
			lRecord.setAttribute("recordType", recordType);
//			if (lInfoFileRecord.getMimetype().equals("application/pdf") && (!lInfoFileRecord.isFirmato()
//					|| (lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().equalsIgnoreCase("PADES")))) {
//				lRecord.setAttribute("mimetype", lInfoFileRecord.getMimetype());
//				manageResponse(lRecord);
//			} else 
			if (lInfoFileRecord.getMimetype().equals("image/tiff") && !lInfoFileRecord.isFirmato()) {
				lRecord.setAttribute("servlet", "previewtiff");
				lRecord.setAttribute("mimetype", "application/pdf");
				manageResponse(lRecord);
			} else {
				new GWTRestService<Record, Record>("PreviewFileDatasource").call(lRecord,
						new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								manageResponse(object);
							}
						});
			}
		}
	}

	private static boolean isToShowEml(InfoFileRecord lInfoFileRecord, String filename) {
		if (lInfoFileRecord == null)
			return false;
		else {
			String mimetype = lInfoFileRecord.getMimetype();
			String correctFileName = lInfoFileRecord.getCorrectFileName();
			if (correctFileName == null || correctFileName.trim().equals("") || correctFileName.trim().length() == 0)
				correctFileName = filename;
			if (isEmlExtension(correctFileName) || isEmlMimetype(mimetype)) {
				return true;
			} else
				return false;
		}
	}

	private static boolean isEmlExtension(String correctFileName) {
		if (correctFileName == null || "".equals(correctFileName))
			return false;
		int startIndex = correctFileName.lastIndexOf(".");
		if (startIndex == -1)
			return false;
		String extension = correctFileName.substring(startIndex + 1);
		for (String lString : Layout.getGenericConfig().getEmlExtension()) {
			if (lString.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isEmlMimetype(String mimetype) {
		if (mimetype == null || "".equals(mimetype))
			return false;
		for (String lString : Layout.getGenericConfig().getEmlMimetype()) {
			if (lString.equalsIgnoreCase(mimetype)) {
				return true;
			}
		}
		return false;
	}

	private void manageResponse(Record object) {

		if (object.getAttribute("mimetype").equals("notValid")) {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().preview_window_file_not_valid(),
					I18NUtil.getMessages().preview_window_file_not_valid(), MessageType.WARNING));
			return;
		}
		try {
			String recordType = object.getAttributeAsString("recordType");
			String mimetype = object.getAttributeAsString("mimetype");
			String servlet = object.getAttributeAsString("servlet");
			servlet = (servlet == null || servlet.length() == 0) ? "preview" : servlet;
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", "");
			lRecord.setAttribute("uri", object.getAttributeAsString("uri"));
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", object.getAttributeAsBoolean("remoteUri"));

			String url = GWT.getHostPageBaseURL() + "springdispatcher/" + servlet + "?fromRecord=true&mimetype="
					+ mimetype + "&recordType=" + DownloadFile.encodeURL(recordType) + "&record="
					+ DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));

			if (object.getAttribute("mimetype").equals("application/pdf")) {
				htmlPane = new HTMLPane();
				htmlPane.setWidth100();
				htmlPane.setHeight100();
				htmlPane.setContentsURL(url);
				htmlPane.setContentsType(ContentsType.PAGE);
				
				layout.addMember(htmlPane);
			} else if (object.getAttribute("mimetype").startsWith("image")) {
				img = new Img(url);
				img.setWidth(object.getAttributeAsInt("width"));
				img.setHeight(object.getAttributeAsInt("height"));
				
				layout.addMember(img);
			}
		} catch (Exception e) {
			window.markForDestroy();
		}
	}
}