/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.smartgwt.client.widgets.Canvas;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.visualizzafileeml.VisualizzaFileEmlWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.BaseWindow;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.shared.bean.MimeTypeNonGestitiBean;

public class PreviewWindow extends BaseWindow implements AbstractPreviewCanvas {
	
	protected PreviewWindow window;
	
	protected PreviewWindowPageSelectionCallback previewWindowPageSelectionCallback;
	// id dell'istanza, mi serve per la callback di recupero delle pagine selezionate
	protected String pUri;
	protected String pUriConvertitoPDF;
	protected String smartId;
	protected String urlPdfPreview;
	protected String fileName;
	protected boolean isReadOnly;
	protected boolean enableOptionToSaveInOmissisForm;
	private boolean attivaViewerSVG;

	public PreviewWindow(String pUri, Boolean remoteUri, InfoFileRecord lInfoFileRecord, String recordType) {

		super ("previewWindows");
		
		window = this;
		smartId = SC.generateID();

		setShowTitle(true);
		setIsModal(isModal());
		setModalMaskOpacity(50);
		setAutoCenter(false);
		setAutoSize(false);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowMaximizeButton(true);
		if((this instanceof PreviewWindowWithCallback) && hideAnnullaButton()) {
			setShowCloseButton(false);
		} else {
			setShowCloseButton(true);
			addCloseClickHandler(new CloseClickHandler() {

				@Override
				public void onCloseClick(CloseClickEvent event) {
					manageCloseClick();
				}
			});
		}
		setAlign(Alignment.CENTER);
		setCanDragResize(true);
		setShowStatusBar(true);
		setShowResizer(true);
		setRedrawOnResize(false);
		setAutoDraw(false);

		setTitle(I18NUtil.getMessages().protocollazione_previewDocWindow_title());
		PreviewLayout lPreviewLayout = new PreviewLayout(pUri, remoteUri, lInfoFileRecord, recordType, this, this, true);

	}
	
	public PreviewWindow(String title, final String pUri, Boolean remoteUri, InfoFileRecord lInfoFileRecord, String recordType, String filename,  PreviewWindowPageSelectionCallback previewWindowPageSelectionCallback, boolean isReadOnly, boolean enableOptionToSaveInOmissisForm) {
		this("", title, pUri, remoteUri, lInfoFileRecord, recordType, filename, previewWindowPageSelectionCallback, isReadOnly, enableOptionToSaveInOmissisForm);
	}

	public PreviewWindow(String title, final String pUri, Boolean remoteUri, InfoFileRecord lInfoFileRecord, String recordType, String filename) {
		this("", title, pUri, remoteUri, lInfoFileRecord, recordType, filename);
	}

	public PreviewWindow(String pUri, Boolean remoteUri, InfoFileRecord lInfoFileRecord, String recordType, String filename, PreviewWindowPageSelectionCallback previewWindowPageSelectionCallback, boolean isReadOnly, boolean enableOptionToSaveInOmissisForm) {
		this(null, pUri, remoteUri, lInfoFileRecord, recordType, filename, previewWindowPageSelectionCallback, isReadOnly, enableOptionToSaveInOmissisForm);
	}
	
	public PreviewWindow(String pUri, Boolean remoteUri, InfoFileRecord lInfoFileRecord, String recordType, String filename) {
		this(null, pUri, remoteUri, lInfoFileRecord, recordType, filename);
	}
	
	public PreviewWindow(String keyPreview, String title, final String pUri, Boolean remoteUri, InfoFileRecord lInfoFileRecord, String recordType, String filename) {
		this(keyPreview, title, pUri, remoteUri, lInfoFileRecord, recordType, filename, null, true, false);		
	}

	public PreviewWindow(String keyPreview, String title, final String pUri, Boolean remoteUri, InfoFileRecord lInfoFileRecord, String recordType, String filename,  PreviewWindowPageSelectionCallback previewWindowPageSelectionCallback, boolean isReadOnly, boolean enableOptionToSaveInOmissisForm) {
		
		super ("previewWindows");
		
		this.pUri = pUri;
		this.previewWindowPageSelectionCallback = previewWindowPageSelectionCallback;
		this.fileName = filename;
		this.isReadOnly = isReadOnly;
		this.enableOptionToSaveInOmissisForm = enableOptionToSaveInOmissisForm;
		this.attivaViewerSVG = AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIEWER_SVG");
		
		window = this;
		smartId = SC.generateID();

		if (keyPreview != null && keyPreview.length() > 0)
			portletOpened = keyPreview;

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

			setShowTitle(true);
			setIsModal(isModal());
			setModalMaskOpacity(50);
			setAutoCenter(false);
			setAutoSize(false);
			setKeepInParentRect(true);
			setShowModalMask(true);
			setShowMaximizeButton(true);
			if((this instanceof PreviewWindowWithCallback) && hideAnnullaButton()) {
				setShowCloseButton(false);
			} else {
				setShowCloseButton(true);
				addCloseClickHandler(new CloseClickHandler() {

					@Override
					public void onCloseClick(CloseClickEvent event) {
						manageCloseClick();
					}
				});
			}
			setAlign(Alignment.CENTER);
			setCanDragResize(true);
			setShowStatusBar(true);
			setShowResizer(true);
			setRedrawOnResize(false);
			setAutoDraw(false);

			setTitle(getTitleAllegato(title, filename));

			Record lRecord = new Record();
			lRecord.setAttribute("uri", pUri);
			lRecord.setAttribute("nomeFile", filename);
			lRecord.setAttribute("remote", remoteUri);
			lRecord.setAttribute("infoFile", lInfoFileRecord);
			lRecord.setAttribute("recordType", recordType);
			int width = com.google.gwt.user.client.Window.getClientWidth();
			int height = com.google.gwt.user.client.Window.getClientHeight();
			lRecord.setAttribute("width", width);
			lRecord.setAttribute("height", height);

//			if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null && lInfoFileRecord.getMimetype().equals("application/pdf")
//					&& (!lInfoFileRecord.isFirmato() || (lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().equalsIgnoreCase("PADES")))) {
//				lRecord.setAttribute("mimetype", lInfoFileRecord.getMimetype());
//				visualizzaPreviewPdfConcontrolloDimensione(lRecord);
//			} else 
			if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null && lInfoFileRecord.getMimetype().equals("image/tiff") && !lInfoFileRecord.isFirmato()) {
				lRecord.setAttribute("servlet", "previewtiff");
				lRecord.setAttribute("mimetype", "image/tiff");
				visualizzaPreviewImageConcontrolloDimensione(lRecord);
			} else {
				new GWTRestService<Record, Record>("PreviewFileDatasource").call(lRecord, new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						// Controllo se il file era troppo grande per essere convertito
						if (object.getAttribute("fileDimensionExceedToPdfConvert") != null && object.getAttributeAsBoolean("fileDimensionExceedToPdfConvert")) {
							SC.say(I18NUtil.getMessages().previewWindow_alertDimensioneFilePreviewGenerica_message(), new BooleanCallback() {								
								@Override
								public void execute(Boolean value) {			
								}
							});
						}
						// Controllo se il file era troppo grande per essere visualizzato
						else if (object.getAttribute("fileDimensionExceedToImagePreview") != null && object.getAttributeAsBoolean("fileDimensionExceedToImagePreview")) {
							SC.say(I18NUtil.getMessages().previewWindow_alertDimensioneFilePreviewGenerica_message(), new BooleanCallback() {								
								@Override
								public void execute(Boolean value) {			
								}
							});
						}
						// Controllo se il file restituito è un pdf
						else if (object.getAttribute("mimetype") != null && "application/pdf".equalsIgnoreCase(object.getAttribute("mimetype"))) {
							visualizzaPreviewPdfConcontrolloDimensione(object);
						} else {
							// Il file non è pdf e le dimensioni sono dentro le soglie
							visualizzaPreview(object);
						}
					}
				});
			}
		}
		
		if(!isModal()) {
			setTopMost();
		}
		
	}

	private String getTitleAllegato(String title, String filename) {

		String titleView = filename != null && filename.length() > 0 ? filename : "";
		if (title != null && title.length() > 0)
			titleView = titleView != null && titleView.length() > 0 ? titleView + ": " + title : title;

		titleView = titleView != null ? titleView : I18NUtil.getMessages().protocollazione_previewDocWindow_title();

		return titleView;
	}

	public static boolean isToShowEml(InfoFileRecord lInfoFileRecord, String filename) {
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
	
	protected void visualizzaPreviewPdfConcontrolloDimensione(final Record object) {
		if (object.getAttributeAsRecord("infoFile") != null && object.getAttributeAsRecord("infoFile").getAttribute("bytes") != null) {
			// Faccio il controllo della dimensione del file
			int sogliaPreviewMB = AurigaLayout.getParametroDB("DIMENSIONE_MAX_FILE_PREVIEW") != null ? Integer.parseInt(AurigaLayout.getParametroDB("DIMENSIONE_MAX_FILE_PREVIEW")) : 0;
			long dimensioneFileMB = 0;
			if (object.getAttributeAsRecord("infoFile") != null) {
				try {
					dimensioneFileMB = object.getAttributeAsRecord("infoFile") != null ? (object.getAttributeAsRecord("infoFile").getAttributeAsLong("bytes")) / 1000000 : 0;
				}catch (Exception e) {
					dimensioneFileMB = 0;
				}
			}
			// Verifico se la dimensione supera la soglia di alert
			if (sogliaPreviewMB != 0 && dimensioneFileMB > sogliaPreviewMB) {
				SC.say(I18NUtil.getMessages().previewWindow_alertDimensioneFilePreviewPdf_message(), new BooleanCallback() {								
					@Override
					public void execute(Boolean value) {
						
						if(value != null && value) {
							new GWTRestService<Record, Record>("PreviewFileDatasource").executecustom("getVersioneRidottaFile", object, new DSCallback() {
								
								@Override
								public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
									Record record = dsResponse.getData()[0];
									visualizzaPreview(record);
								}
							});
						}				
					}
				});
				
			} else {
				visualizzaPreview(object);
			}
		} else {
			visualizzaPreview(object);
		}
		
	}
	
	protected void visualizzaPreviewImageConcontrolloDimensione(final Record object) {
		if (object.getAttributeAsRecord("infoFile") != null && object.getAttributeAsRecord("infoFile").getAttribute("bytes") != null) {
			// Faccio il controllo della dimensione del file
			int sogliaPreviewMB = AurigaLayout.getParametroDB("DIMENSIONE_MAX_FILE_PREVIEW") != null ? Integer.parseInt(AurigaLayout.getParametroDB("DIMENSIONE_MAX_FILE_PREVIEW")) : 0;
			long dimensioneFileMB = 0;
			if (object.getAttributeAsRecord("infoFile") != null) {
				try {
					dimensioneFileMB = object.getAttributeAsRecord("infoFile") != null ? (object.getAttributeAsRecord("infoFile").getAttributeAsLong("bytes")) / 1000000 : 0;
				}catch (Exception e) {
					dimensioneFileMB = 0;
				}
			}
			// Verifico se la dimensione supera la soglia di alert
			if (sogliaPreviewMB != 0 && dimensioneFileMB > sogliaPreviewMB) {
				// FIXMEL sistemare messaggio
				SC.say(I18NUtil.getMessages().previewWindow_alertDimensioneFilePreviewGenerica_message(), new BooleanCallback() {								
					@Override
					public void execute(Boolean value) {			
					}
				});
				
			} else {
				visualizzaPreview(object);
			}
		} else {
			visualizzaPreview(object);
		}
		
	}
	
	protected void visualizzaPreview(Record object) {

		if (object.getAttribute("mimetype").equals("notValid")) {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().preview_window_file_not_valid(), I18NUtil.getMessages().preview_window_file_not_valid(),
					MessageType.WARNING));
			return;
		}
		try {
			String recordType = object.getAttributeAsString("recordType");
			String mimetype = object.getAttributeAsString("mimetype");
			String servlet = object.getAttributeAsString("servlet");
			servlet = (servlet == null || servlet.length() == 0) ? "preview" : servlet;
			Record lRecord = new Record();
			String nomeFileNormalize = object.getAttributeAsString("nomeFile");
			// Tolgo il carattere # dal nomeFile perchè viene riconosciuto dal browser come carattere di ancora ed impedisce la visualizzazione del file
			if(nomeFileNormalize != null && !"".equalsIgnoreCase(nomeFileNormalize)) {
				nomeFileNormalize = nomeFileNormalize.replaceAll("#", "");
			}
			lRecord.setAttribute("displayFilename", nomeFileNormalize);
			pUriConvertitoPDF = object.getAttributeAsString("uri");
			lRecord.setAttribute("uri", object.getAttributeAsString("uri"));
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", object.getAttributeAsBoolean("remoteUri"));
			// Controllo se devo utilizzare il nuovo pdf viewer
			if (AurigaLayout.getParametroDBAsBoolean("DISATTIVA_NUOVO_VIEWER_PDFJS")) {
				// Uso il vecchio pdf viewer
				if (object.getAttribute("mimetype").equals("application/pdf") || object.getAttribute("mimetype").equals("image/tiff")) {
					String url = GWT.getHostPageBaseURL() + "springdispatcher/" + servlet + "?fromRecord=true&mimetype=" + "application/pdf" + "&recordType="
							+ DownloadFile.encodeURL(recordType) + "&record=" + DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));
					HTMLPane lHtmlPane = new HTMLPane();
					lHtmlPane.setWidth100();
					lHtmlPane.setHeight100();
					markForRedraw();
					lHtmlPane.setContentsURL(url);
					lHtmlPane.setContentsType(ContentsType.PAGE);
					VLayout lVLayout = new VLayout();
					lVLayout.setWidth100();
					lVLayout.setHeight100();
					lVLayout.addMember(lHtmlPane);
					addItem(lVLayout);
				} else if (object.getAttribute("mimetype").startsWith("image")) {
					String url = GWT.getHostPageBaseURL() + "springdispatcher/" + servlet + "?fromRecord=true&mimetype=" + mimetype + "&recordType="
							+ DownloadFile.encodeURL(recordType) + "&record=" + DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));
					Img lImg = new Img(url);
					int width = object.getAttributeAsInt("width");
					int height = object.getAttributeAsInt("height");
					setWidth(width);
					setHeight(height);
					markForRedraw();
					lImg.setWidth100();
					lImg.setHeight100();
					VLayout lVLayout = new VLayout();
					lVLayout.setWidth100();
					lVLayout.setHeight100();
					lVLayout.addMember(lImg);
					addItem(lVLayout);
				}			
			} else {
				// Uso il nuovo pdf viewer
				String url = GWT.getHostPageBaseURL() + "springdispatcher/" + servlet + "?fromRecord=true&mimetype=" + mimetype + "&recordType="
						+ DownloadFile.encodeURL(recordType) + "&record=" + DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));
				
				if (enableOptionToSaveInOmissisForm){
					lRecord.setAttribute("enableToSaveInOmissis", enableOptionToSaveInOmissisForm);
					urlPdfPreview = "";
					String fileParam = GWT.getHostPageBaseURL() + "springdispatcher/preview?fromRecord=true&mimetype=" + mimetype + "&recordType=FileToExtractBean&record=" + DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));
					fileParam = encodeUriComponent(fileParam);
					if (attivaViewerSVG) {
						urlPdfPreview = GWT.getHostPageBaseURL() + "script/pdfViewerSVG/web/viewer.html?file=" + fileParam + "&selectionId=" + smartId;
					} else {
						urlPdfPreview = GWT.getHostPageBaseURL() + "script/pdfViewer_20220316/web/viewer.html?file=" + fileParam + "&selectionId=" + smartId;
					}
					
					HTMLPane lHtmlPane = new HTMLPane();
					lHtmlPane.setWidth100();
					lHtmlPane.setHeight100();
					markForRedraw();
//					lHtmlPane.setContentsURL(urlPdfPreview);
					lHtmlPane.setContents("<iframe id=\"" + smartId + "_VIEWERPDF\" frameborder=\"0\" width=\"100%\" height=\"100%\" marginheight=\"0\" marginwidth=\"0\" align=\"left\" scrolling=\"yes\" vspace=\"0\" hspace=\"0\" src=\"" + urlPdfPreview + "\"></iframe>");
					lHtmlPane.setContentsType(ContentsType.PAGE);
					lHtmlPane.setRedrawOnResize(false);
					VLayout lVLayout = new VLayout();
					lVLayout.setWidth100();
					lVLayout.setHeight100();
					lVLayout.addMember(lHtmlPane);
					addItem(lVLayout);
				} else if (object.getAttribute("mimetype").equals("application/pdf") || object.getAttribute("mimetype").equals("image/tiff") ) {
					if (object.getAttribute("mimetype").equals("application/pdf")) {
						// Utilizzo l'editor pdf.js
						String fileParam = GWT.getHostPageBaseURL() + "springdispatcher/" + servlet + "?fromRecord=true&mimetype=" + mimetype + "&recordType=FileToExtractBean&record=" + DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));
						fileParam = encodeUriComponent(fileParam);
						if (attivaViewerSVG) {
							urlPdfPreview = GWT.getHostPageBaseURL() + "script/pdfViewerSVG/web/viewer.html?file=" + fileParam + "&selectionId=" + smartId;
						} else {
							urlPdfPreview = GWT.getHostPageBaseURL() + "script/pdfViewer_20220316/web/viewer.html?file=" + fileParam + "&selectionId=" + smartId;
						}
						
					} else {
						// Utilizzo l'editor standard
						// Devo rimettere pdf come mimeType
						object.setAttribute("mimetype", "application/pdf");
						mimetype = object.getAttributeAsString("mimetype");
						urlPdfPreview = GWT.getHostPageBaseURL() + "springdispatcher/" + servlet + "?fromRecord=true&mimetype=" + mimetype + "&recordType="
								+ DownloadFile.encodeURL(recordType) + "&record=" + DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));
					}
					HTMLPane lHtmlPane = new HTMLPane();
					lHtmlPane.setWidth100();
					lHtmlPane.setHeight100();
					markForRedraw();
//					lHtmlPane.setContentsURL(urlPdfPreview);
					lHtmlPane.setContents("<iframe id=\"" + smartId + "_VIEWERPDF\" frameborder=\"0\" width=\"100%\" height=\"100%\" marginheight=\"0\" marginwidth=\"0\" align=\"left\" scrolling=\"yes\" vspace=\"0\" hspace=\"0\" src=\"" + urlPdfPreview + "\"></iframe>");
					lHtmlPane.setContentsType(ContentsType.PAGE);
					lHtmlPane.setRedrawOnResize(false);
					VLayout lVLayout = new VLayout();
					lVLayout.setWidth100();
					lVLayout.setHeight100();
					lVLayout.addMember(lHtmlPane);
					addItem(lVLayout);
				} else if (object.getAttribute("mimetype").startsWith("image")) {
					Img lImg = new Img(url);
					int width = object.getAttributeAsInt("width");
					int height = object.getAttributeAsInt("height");
					setWidth(width);
					setHeight(height);
					markForRedraw();
					lImg.setWidth100();
					lImg.setHeight100();
					VLayout lVLayout = new VLayout();
					lVLayout.setWidth100();
					lVLayout.setHeight100();
					lVLayout.addMember(lImg);
					addItem(lVLayout);
				}
			}

			if (this instanceof PreviewWindowWithCallback) {
				Button okButton = new Button();
				okButton.setTitle("Ok");
				okButton.setIcon("ok.png");
				okButton.setIconHeight(16);
				okButton.setIconWidth(16);
				okButton.setWidth(100);
				okButton.setTop(20);
				okButton.setAlign(Alignment.CENTER);
				okButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						manageOkClickAndDestroy();						
					}
				});

				HStack _buttons = new HStack(5);
				_buttons.setHeight(30);
				_buttons.setAlign(Alignment.CENTER);
				_buttons.setPadding(5);
				_buttons.addMember(okButton);
				
				if(!hideAnnullaButton()) {
					
					Button annullaButtonItem = new Button();
					annullaButtonItem.setTitle("Annulla");
					annullaButtonItem.setIcon("annulla.png");
					annullaButtonItem.setIconHeight(16);
					annullaButtonItem.setIconWidth(16);
					annullaButtonItem.setWidth(100);
					annullaButtonItem.setTop(20);
					annullaButtonItem.setAlign(Alignment.CENTER);
					annullaButtonItem.addClickHandler(new ClickHandler() {
	
						@Override
						public void onClick(ClickEvent event) {
							manageCloseClick();
						};
					});
					
					_buttons.addMember(annullaButtonItem);
				}
				
				addItem(_buttons);
			}

			show();
		} catch (Exception e) {
			markForDestroy();
		}
	}

	public static boolean isPdfConvertibile(InfoFileRecord lInfoFileRecord) {
		if (lInfoFileRecord == null) {
			return false;
		} else {
			String mimetype = lInfoFileRecord.getMimetype() != null ? lInfoFileRecord.getMimetype() : "";
			if (mimetype != null) {
				if (mimetype.equals("application/pdf") || lInfoFileRecord.isConvertibile()) {
					return true;
				}
			} 
			return false;
		}
	}

	public static boolean canBePreviewed(InfoFileRecord lInfoFileRecord) {
		if (lInfoFileRecord == null) {
			return false;
		} else {
			MimeTypeNonGestitiBean mimeTypeNonGestitiMap = Layout.getMimeTypeNonGestitiBean();
			String mimetype = lInfoFileRecord.getMimetype() != null ? lInfoFileRecord.getMimetype() : "";
			String correctFileName = lInfoFileRecord.getCorrectFileName();
			if (mimetype != null && !isMimeTypeNonGestito(mimeTypeNonGestitiMap, mimetype)) {
				if (mimetype.equals("application/pdf") || mimetype.startsWith("image") || lInfoFileRecord.isConvertibile() || isEmlExtension(correctFileName) || isEmlMimetype(mimetype)) {
					return true;
				} 
			} 
				return false;
		}
	}
	
	private static boolean isMimeTypeNonGestito(MimeTypeNonGestitiBean mimeTypeNonGestitiMap, String mimetype) {
		if(mimeTypeNonGestitiMap!=null && mimeTypeNonGestitiMap.getMimeTypeMap() != null && mimeTypeNonGestitiMap.getMimeTypeMap().containsKey("mimeTypeNonGestiti")) {
			for (String mimeTypeNonGestito : mimeTypeNonGestitiMap.getMimeTypeMap().get("mimeTypeNonGestiti")) {
				if(mimeTypeNonGestito.equals(mimetype)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isEmlMimetype(String mimetype) {
		if (mimetype == null || "".equals(mimetype)) {
			return false;
		}
		for (String lString : Layout.getGenericConfig().getEmlMimetype()) {
			if (lString.equalsIgnoreCase(mimetype)) {
				return true;
			}
		}
		return false;
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

	@Override
	public void finishLoad(VLayout lVLayout) {
		addItem(lVLayout);
		show();
	}

	
	public static native String getRects(String iframeID, boolean attivaViewerSVG) /*-{ 
		
	var iframeElem = $wnd.document.getElementById(iframeID + "_VIEWERPDF");
	if (iframeElem) {
		var pages = Array.prototype.slice.call(iframeElem.contentDocument.getElementsByClassName("page"));
        var pagesMap = [];
        for (var index = 0; index < pages.length; index++) { 
            var page = pages[index];
            var rects = Array.prototype.slice.call(page.getElementsByClassName("svgRect"));
            var pageNum = page.getAttribute("data-page-number");
            var pageRects = [];
            
            for (var index2 = 0; index2 < rects.length; index2++) {
               
                var rect = rects[index2];
                var h, w, x, y;
                
                if (attivaViewerSVG) {
	                h = rect.height.baseVal.value;
	                w = rect.width.baseVal.value;
	                x = rect.x.baseVal.value;
	                
	                if (rect.parentElement) {
	                	y = rect.parentElement.parentElement.height.baseVal.value - rect.y.baseVal.value;
	                } else {
	                   	y = parseInt(rect.parentNode.parentNode.getAttribute("height")) - rect.y.baseVal.value;
	               	}
	                
                } else {
	                // prendo il valore dell'attribute "scale" dal contenitore delle peccette
	                // per capire di quanto bisogna scalare tutte le dimensioni per arrivare 
	                // alla dimensione effettiva
	      
	                var scale = rect.parentElement.attributes.scale.value;
	                
	                h = rect.height.baseVal.value / scale;
	                w = rect.width.baseVal.value / scale;
	                x = rect.x.baseVal.value / scale;
	                y = (rect.ownerSVGElement.height.baseVal.value - rect.y.baseVal.value) / scale;
	               	
                }
                
                var pageRect = {
	                    "llx": x,
	                    "lly": y - h,
	                    "urx": x + w,
	                    "ury": y,
	                };
	                pageRects.push(pageRect);
            };
            
            if (pageRects && pageRects.length > 0) {
                var page = {
                    "numeroPagina": pageNum,
                    "ritaglioBean": pageRects,
                };

                pagesMap.push(page);
            }

        };

        return JSON.stringify(pagesMap);
		
	}
	}-*/;
	
	public static native void destroyViewer (String iframeID) /*-{
		var iframeElem = $wnd.document.getElementById(iframeID + "_VIEWERPDF");
		if (iframeElem) {
			iframeElem.contentWindow.localStorage.clear();
		}
	}-*/;
	
	public void manageCloseClick() {
		Layout.hideWaitPopup();							
		final String jsonRitagli = getRects(smartId, attivaViewerSVG);
		destroyViewer(smartId);
		markForDestroy();
		
		try {
			
			String url = GWT.getHostPageBaseURL() + "springdispatcher/preview/getSelection?selectionId=" + smartId;
			RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
			requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");
			
			requestBuilder.sendRequest(requestBuilder.toString(), new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					String textResponse = response.getText() != null ? response.getText() : "";
					String selection = "";
					String rotationDegree = "";
					if (textResponse != null && !"".equalsIgnoreCase(textResponse)) {
						String[] split = textResponse.split("\\|\\*\\|");
						selection = split[0].equalsIgnoreCase("null") ? null : split[0];
						rotationDegree = split[1].equalsIgnoreCase("null") ? null : split[1];
					}
					if ((selection != null && !"".equalsIgnoreCase(selection)) || (jsonRitagli != null && !"[]".equalsIgnoreCase(jsonRitagli)) || (rotationDegree != null && !"0".equalsIgnoreCase(rotationDegree))) {
						new PreviewWindowAfterPageSelectionPopup(pUriConvertitoPDF, fileName, selection, jsonRitagli, isReadOnly, enableOptionToSaveInOmissisForm, previewWindowPageSelectionCallback, rotationDegree).show();
					}
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					previewWindowPageSelectionCallback.executeOnError();
				}
			});
		} catch (RequestException e) {
		}
	}
		
	public void manageOkClickAndDestroy() {
		manageOkClick();
		// non devo chiamare il manageCloseClick ma direttamente il markforDestroy
		window.markForDestroy();		
	}

	public void manageOkClick() {

	}
	
	public boolean hideAnnullaButton() {
		return false;
	}

	@Override
	public boolean isModal() {
		return true;
	}
	
	protected String encodeUriComponent(String component) {
		String result = null;      

		result = URL.encodeQueryString(component)   
				.replaceAll("\\%28", "(")                          
				.replaceAll("\\%29", ")")   		
				.replaceAll("\\+", "%20")                          
				.replaceAll("\\%27", "'")   			   
				.replaceAll("\\%21", "!")
				.replaceAll("\\%7E", "~");          

		return result;   

	}

	private void setTopMost(){
		final NodeList<Element> allWindowsWithModalMask = findAllWindowsWithModalMask();
		for(int i =0; i<allWindowsWithModalMask.getLength(); i++) {
			Element el = allWindowsWithModalMask.getItem(i);
            String id = el.getAttribute("eventproxy");
            if(id.contains("PreviewControl") || id.contains("NuovaPropostaAtto2CompletaDetail")) {
            	if(Canvas.getById(id) != null) {
            		setMaxZIndex(Canvas.getById(id).getOrCreateJsObj());
                }
            } else if(id.contains("AurigaLayout") || id.contains("PostaElettronicaList") || id.contains("DettaglioPostaElettronica")) {
            	if(Canvas.getById(id) != null) {
            		Canvas.getById(id).addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							final NodeList<Element> allWindowsWithModalMask = findAllWindowsWithModalMask();
							for(int i =0; i<allWindowsWithModalMask.getLength(); i++) {
								Element el = allWindowsWithModalMask.getItem(i);
					            String id = el.getAttribute("eventproxy");
					            if(id.contains("PreviewControl") || id.contains("NuovaPropostaAtto2CompletaDetail")) {
					            	if(Canvas.getById(id) != null) {
					            		setMaxZIndex(Canvas.getById(id).getOrCreateJsObj());
					                }
					            }
							}
						}
					});
                }
            }
		}
	}
	
	protected native NodeList<Element> findAllWindowsWithModalMask() /*-{
	 	return $wnd.document.querySelectorAll("[class='windowBackground']");
	}-*/;
	  
	protected native void setMaxZIndex(JavaScriptObject windowCanvas) /*-{
		windowCanvas.setZIndex(largestZIndex()+1);
		
	   	function largestZIndex() {
		    var z = 0;
		    var elementList = $wnd.document.querySelectorAll('*');
			for (var i = 0; i < elementList.length; i++) {
			  var s = getComputedStyle(elementList[i]);
		        if (s.zIndex !== 'auto' && +s.zIndex > z) {
		            z = +s.zIndex;
		        }
		    }
		    return z;
		}
	}-*/;
	
}
