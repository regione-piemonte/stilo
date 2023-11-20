/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.portal.Portlet;
public class ConfrontaDocumentiLayout extends VLayout {
		
	protected ValuesManager vm;
	private boolean enableOptionToSaveInOmissisForm = false;
	private boolean attivaViewerSVG;
	private VLayout layoutDocSx;
	private VLayout layoutDocDx;
	private String idFileSx;
	private String idFileDx;
	private ElencoDocumentiDaConfrontareItem lElencoDocumentiDaConfrontareItem;
	private ImgButtonItem importaFileDocumentiBtn;
	private ImgButtonItem addButton;
	
	public ConfrontaDocumentiLayout() {
		this.vm = new ValuesManager();
		this.attivaViewerSVG = AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIEWER_SVG");
		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);
		setCanHover(false);
		setPadding(5);
		setMembersMargin(5);
		setRedrawOnResize(false);
		setAutoDraw(false);

		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		addMember(getMasterLayout());
	}
	
	private VLayout getMasterLayout() {
		VLayout masterLayout = new VLayout();
		masterLayout.setWidth100();
		masterLayout.setHeight100();	
		masterLayout.addMember(getGestioneDocumentiLayout());
		masterLayout.addMember(getPreviewDocumentiLayout());
		return masterLayout;
	}
	
	private HLayout getGestioneDocumentiLayout() {
		
		HLayout gestioneDocumentiLayout = new HLayout();
		gestioneDocumentiLayout.setWidth100();
		gestioneDocumentiLayout.setHeight(150);
		gestioneDocumentiLayout.setShowResizeBar(true);
		gestioneDocumentiLayout.setBorder("1px solid grey");
		
		VLayout listaDocumentiLayout = new VLayout();
		listaDocumentiLayout.setWidth(550);
		listaDocumentiLayout.setHeight100();
		listaDocumentiLayout.setOverflow(Overflow.AUTO);
		
		DynamicForm listaDocumentiForm = new DynamicForm();
		listaDocumentiForm.setWidth100();
		listaDocumentiForm.setHeight100();
		listaDocumentiForm.setNumCols(16);
		listaDocumentiForm.setColWidths("1","1","1","1","1","1","1","1","1","1","1","1","1","1","*","*");
		listaDocumentiForm.setValuesManager(vm);
		
		createAddButtons();

		lElencoDocumentiDaConfrontareItem = new ElencoDocumentiDaConfrontareItem() {
			
			@Override
			public void visualizzaDocumento(Record recordDocumento, String schermata) {
				visualizzaPreview(recordDocumento, schermata);
			}
		};
		lElencoDocumentiDaConfrontareItem.setName("elencoDocumentiDaConfrontare");
		lElencoDocumentiDaConfrontareItem.setTitle("Elenco documenti");
		lElencoDocumentiDaConfrontareItem.setShowTitle(false);
		lElencoDocumentiDaConfrontareItem.setStartRow(false);
				
		SpacerItem spacer = new SpacerItem();
		spacer.setWidth("100%");
		
		listaDocumentiForm.setItems(lElencoDocumentiDaConfrontareItem);
		
		VLayout bottoniLayout = new VLayout();
		bottoniLayout.setWidth(5);
		bottoniLayout.setLayoutTopMargin(5);
		bottoniLayout.setHeight(5);
		bottoniLayout.setRedrawOnResize(false);
		
		DynamicForm buttonListaForm = new DynamicForm();
		buttonListaForm.setWidth100();
		buttonListaForm.setHeight(5);
		buttonListaForm.setNumCols(16);
		buttonListaForm.setColWidths("1","1","1","1","1","1","1","1","1","1","1","1","1","1","*","*");
		buttonListaForm.setValuesManager(vm);
		
		createAddButtons();
		
		buttonListaForm.setItems(addButton, importaFileDocumentiBtn);
		
		bottoniLayout.addMember(buttonListaForm);
		
		listaDocumentiLayout.setMembers(listaDocumentiForm);
		
		gestioneDocumentiLayout.setMembers(bottoniLayout, listaDocumentiLayout);
		
		return gestioneDocumentiLayout;
	}	
	
	private HLayout getPreviewDocumentiLayout() {
		
		HLayout previewDocumentiLayout = new HLayout();
		previewDocumentiLayout.setWidth100();
		previewDocumentiLayout.setHeight100();
		previewDocumentiLayout.setRedrawOnResize(false);
		
		Record object = new Record ();
		object.setAttribute("mimetype", "application/pdf");
		object.setAttribute("nomeFile", "DatiIstanzaDCR_NCLDTR78B17H224W");
		object.setAttribute("recordType", "FileToExtractBean");
		object.setAttribute("uri", "[FS@AURIGAREP2]/2022/2/17/1/2022021709135051111975296669790729");
		object.setAttribute("idfile", "123456789");
		
		// DOC SX
		layoutDocSx = new VLayout();
		layoutDocSx.setWidth("50%");
		layoutDocSx.setHeight100();
		layoutDocSx.setRedrawOnResize(false);
		layoutDocSx.setAutoDraw(false);
		layoutDocSx.setName("preview_sinistra");
		
		// DOC DX
		layoutDocDx = new VLayout();
		layoutDocDx.setWidth("50%");
		layoutDocDx.setHeight100();
		layoutDocDx.setRedrawOnResize(false);
		layoutDocDx.setAutoDraw(false);
		layoutDocDx.setName("preview_destra");
				
		previewDocumentiLayout.addMember(layoutDocSx);
		previewDocumentiLayout.addMember(layoutDocDx);
				
		return previewDocumentiLayout;
	}
	
	private void createAddButtons() {	
		addButton = new ImgButtonItem("addButton", "[SKIN]actions/add.png", I18NUtil.getMessages().newButton_prompt());
		addButton.setStartRow(true);
		
		addButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				lElencoDocumentiDaConfrontareItem.onClickHandlerNewButton();
			}
		});
		
		importaFileDocumentiBtn = new ImgButtonItem("importaFileDocumentiBtn", "buttons/importaAtti.png", "Aggiungi documenti da confrontare");
		

		importaFileDocumentiBtn.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				ImportaDocumentiDaConfrontareMultiLookupArchivio lookupArchivioPopup = new ImportaDocumentiDaConfrontareMultiLookupArchivio(null);
				lookupArchivioPopup.show();
			}
		});	
	}
	
	private void checkVisualizzaPreview(Record lRecord, final VLayout layout) {
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
				} else {
					// Il file non è pdf e le dimensioni sono dentro le soglie
					visualizzaPreview(object, layout);
				}
			}
		});
	}
	
	protected void visualizzaPreview(Record object, String layoutSxDx) {
		checkVisualizzaPreview(object, layoutSxDx.equalsIgnoreCase("sinistra") ? layoutDocSx : layoutDocDx);
	}
	
	protected void visualizzaPreview(Record object, VLayout layout) {

		if (object.getAttribute("mimetype").equals("notValid")) {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().preview_window_file_not_valid(), I18NUtil.getMessages().preview_window_file_not_valid(),
					MessageType.WARNING));
			return;
		}
		try {
			String urlPdfPreview = "";
			String smartId = SC.generateID();
			String recordType = object.getAttributeAsString("recordType");
			String mimetype = object.getAttributeAsString("mimetype");
			String servlet = object.getAttributeAsString("servlet");
			servlet = (servlet == null || servlet.length() == 0) ? "preview" : servlet;
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", object.getAttributeAsString("nomeFile"));
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
//					layout.markForRedraw();
					lHtmlPane.setContentsURL(url);
					lHtmlPane.setContentsType(ContentsType.PAGE);
					manageAddMemberOnLayout(layout, lHtmlPane, object.getAttributeAsString("descrizioneFile"));
				} else if (object.getAttribute("mimetype").startsWith("image")) {
					String url = GWT.getHostPageBaseURL() + "springdispatcher/" + servlet + "?fromRecord=true&mimetype=" + mimetype + "&recordType="
							+ DownloadFile.encodeURL(recordType) + "&record=" + DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));
					Img lImg = new Img(url);
					int width = object.getAttributeAsInt("width");
					int height = object.getAttributeAsInt("height");
					setWidth(width);
					setHeight(height);
//					layout.markForRedraw();
					lImg.setWidth100();
					lImg.setHeight100();
					layout.addMember(lImg);
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
//					layout.markForRedraw();
//					lHtmlPane.setContentsURL(urlPdfPreview);
					lHtmlPane.setContents("<iframe id=\"" + smartId + "_VIEWERPDF\" frameborder=\"0\" width=\"100%\" height=\"100%\" marginheight=\"0\" marginwidth=\"0\" align=\"left\" scrolling=\"yes\" vspace=\"0\" hspace=\"0\" src=\"" + urlPdfPreview + "\"></iframe>");
					lHtmlPane.setContentsType(ContentsType.PAGE);
					lHtmlPane.setRedrawOnResize(false);
					manageAddMemberOnLayout(layout, lHtmlPane, object.getAttributeAsString("nomeFile"));
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
//					layout.markForRedraw();
//					lHtmlPane.setContentsURL(urlPdfPreview);
					lHtmlPane.setContents("<iframe id=\"" + smartId + "_VIEWERPDF\" frameborder=\"0\" width=\"100%\" height=\"100%\" marginheight=\"0\" marginwidth=\"0\" align=\"left\" scrolling=\"yes\" vspace=\"0\" hspace=\"0\" src=\"" + urlPdfPreview + "\"></iframe>");
					lHtmlPane.setContentsType(ContentsType.PAGE);
					lHtmlPane.setRedrawOnResize(false);
					manageAddMemberOnLayout(layout, lHtmlPane, object.getAttributeAsString("nomeFile"));
				} else if (object.getAttribute("mimetype").startsWith("image")) {
					Img lImg = new Img(url);
					int width = object.getAttributeAsInt("width");
					int height = object.getAttributeAsInt("height");
					setWidth(width);
					setHeight(height);
//					layout.markForRedraw();
					lImg.setWidth100();
					lImg.setHeight100();
					layout.addMember(lImg);
				}
			}

		} catch (Exception e) {
			markForDestroy();
		}
	}
	
	public void rimuoviPreview(String idFile) {
		if (idFile != null) {
			VLayout layoutWithFile = null;
			if (idFile.equalsIgnoreCase(idFileSx)) {
				layoutWithFile = layoutDocSx;
			} else if (idFile.equalsIgnoreCase(idFileDx)) {
				layoutWithFile = layoutDocDx;
			}
			if (layoutWithFile != null) {
				manageRemoveMemberOnLayout(layoutWithFile);
			}
		}
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
	
	private void manageAddMemberOnLayout (VLayout layout, HTMLPane htmlPane, String titolo) {
		boolean foundPreview = false;
		Canvas elementFound = new Canvas();
		Canvas[] members = layout.getMembers();
		for (Canvas member : members) {
			if (member instanceof HTMLPane) {
				elementFound = member;
				foundPreview = true;
				break;
			}
		}
		if (foundPreview) {
			layout.removeMember(elementFound);
			DynamicForm form = (DynamicForm) layout.getMember("form_titolo_" + layout.getName());
			StaticTextItem item = (StaticTextItem) form.getItem("titolo_" + layout.getName());
			item.setValue(titolo);
		} else {
			StaticTextItem label = new StaticTextItem("titolo_" + layout.getName());
			label.setShowTitle(false);
			label.setWidth(200);
			label.setColSpan(2);
			label.setAlign(Alignment.LEFT);
			label.setValue(titolo);
			DynamicForm form = new DynamicForm();
			form.setName("form_titolo_" + layout.getName());
			form.setItems(label);
			layout.addMember(form);
		}
		layout.addMember(htmlPane);
	}
	
	private void manageRemoveMemberOnLayout (VLayout layout) {
		Canvas[] members = layout.getMembers();
		for (Canvas member : members) {
			if (member instanceof HTMLPane) {
				layout.removeMember(member);
				break;
			}
		}
		DynamicForm form = (DynamicForm) layout.getMember("form_titolo_" + layout.getName());
		StaticTextItem item = (StaticTextItem) form.getItem("titolo_" + layout.getName());
		item.setValue("");
	}

	public ElencoDocumentiDaConfrontareItem getlElencoDocumentiDaConfrontareItem() {
		return lElencoDocumentiDaConfrontareItem;
	}
	
//	public void aggiungiDocumentiInElenco(RecordList resultRecordList) {
//		for (int i = 0; i < resultRecordList.getLength(); i++) {
//			Record resultRecord = resultRecordList.get(i);
//			lElencoDocumentiDaConfrontareItem.aggiungiDocumentiInLista(resultRecord);								
//		}
//	}
	
	public void aggiungiDocumentiInElenco(RecordList  recordToAdd, final ServiceCallback<Record> callback) {
		if ((recordToAdd != null) && (recordToAdd.getLength() > 0)) {
			Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere...");
			Record recordMassivo = new Record();
			recordMassivo.setAttribute("listaRecord", recordToAdd);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
			lGwtRestDataSource.performCustomOperation("getEstremiUnitaDocumentarie", recordMassivo, new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						RecordList resultRecordList = response.getData()[0].getAttributeAsRecordList("listaRecord");
						for (int i = 0; i < resultRecordList.getLength(); i++) {
							Record resultRecord = resultRecordList.get(i);
							lElencoDocumentiDaConfrontareItem.aggiungiDocumentiInLista(resultRecord);								
						}
					}
					Layout.hideWaitPopup();
					if (callback != null) {
						callback.execute(null);
					}
				}
			}, new DSRequest());
		}
	}
	
	public static void showConfrontaDocumenti (RecordList listaAttiDaAggiungere) {
		Portlet confrontaAtti = Layout.getOpenedPortlet("confronta_atti");
		final ConfrontaDocumentiLayout lConfrontaDocumentiLayout;
		if (confrontaAtti != null) {
			Canvas body = confrontaAtti.getBody();
			if (body != null && body instanceof ConfrontaDocumentiLayout) {
				lConfrontaDocumentiLayout = (ConfrontaDocumentiLayout) confrontaAtti.getBody();
			} else {
				lConfrontaDocumentiLayout = new ConfrontaDocumentiLayout();
			}
		} else {
			lConfrontaDocumentiLayout = new ConfrontaDocumentiLayout();
		}
		lConfrontaDocumentiLayout.aggiungiDocumentiInElenco(listaAttiDaAggiungere, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				Layout.addPortlet("confronta_atti", "Confronta Documenti", "menu/oggettario.png", lConfrontaDocumentiLayout, false);
			}
		});		
	}
	
	public class ImportaDocumentiDaConfrontareMultiLookupArchivio extends LookupArchivioPopup {

		private List<Record> multiLookupList = new ArrayList<Record>();

		public ImportaDocumentiDaConfrontareMultiLookupArchivio(Record record) {
			super(record, null, false);
		}
		
		@Override
		public String getWindowTitle() {
			return "Seleziona i documenti da confrontare";
		}
		
		@Override
		public String getFinalita() {
			return getFinalitaAggiungiDocumentoDaConfrontare();
		}

		@Override
		public void manageOnCloseClick() {
			super.manageOnCloseClick();
			if ((multiLookupList != null) && (multiLookupList.size() > 0)) {
				Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere...");
				RecordList lRecordListDocumentToImport = new RecordList();
				for (Record record : multiLookupList) {
					Record lRecordToLoad = new Record();
					lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
					lRecordListDocumentToImport.add(lRecordToLoad);
				}
				Record recordMassivo = new Record();
				recordMassivo.setAttribute("listaRecord", lRecordListDocumentToImport);
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
				Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere...");
				lGwtRestDataSource.performCustomOperation("getEstremiUnitaDocumentarie", recordMassivo, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							RecordList resultRecordList = response.getData()[0].getAttributeAsRecordList("listaRecord");
							for (int i = 0; i < resultRecordList.getLength(); i++) {
								Record resultRecord = resultRecordList.get(i);
								lElencoDocumentiDaConfrontareItem.aggiungiDocumentiInLista(resultRecord);								
							}
						}
						Layout.hideWaitPopup();
					}
				}, new DSRequest());
			}

		}

		@Override
		public void manageMultiLookupBack(Record record) {
			multiLookupList.add(record);
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			if (multiLookupList != null) {
				for (int i = 0; i < multiLookupList.size(); i++) {
					Record values = multiLookupList.get(i);
					if (values.getAttribute("idUdFolder").equals(record.getAttribute("id"))) {
						multiLookupList.remove(i);
						break;
					}
				}
			}
		}

		@Override
		public void manageLookupBack(Record record) {
		}

	}
	
	public String getFinalitaAggiungiDocumentoDaConfrontare() {
		return "SEL_ATTI";
	}
	
}
