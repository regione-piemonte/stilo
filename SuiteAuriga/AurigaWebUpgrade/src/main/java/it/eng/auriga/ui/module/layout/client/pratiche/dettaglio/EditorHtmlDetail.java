/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public abstract class EditorHtmlDetail extends CustomDetail implements BackDetailInterface,SaveDetailInterface{

	protected String idProcess;
	protected String idTipoEvento;
	protected String idEvento;
	
	protected Record recordEvento;
	
	protected DettaglioPraticaLayout dettaglioPraticaLayout;

	protected String nome;

	protected String idUd;
	
	protected String idDocAssTask;
	protected String uriModAssDocTask;
	protected String idDocTipDocTask;
	protected String uriUltimaVersDocTask;
	protected String mimetypeDocTipAssTask;
	protected String nomeFileDocTipAssTask;
	protected String nomeTipDocTask;

	protected DynamicForm  editorHtmlForm;
	protected RichTextItem descrizioneHtmlItem;
	protected String smartId;

	public EditorHtmlDetail(String nomeEntita, ValuesManager vm, String idProcess, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {
		
		super(nomeEntita,vm);
		
		smartId = SC.generateID();
		initPageContexMenu(this, "pageContextMenu" + smartId, Layout.isExternalPortlet);
		
		this.idProcess = idProcess;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		
		this.recordEvento = lRecordEvento;
		
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;

		nome = lRecordEvento.getAttribute("nome");

		idUd = lRecordEvento.getAttribute("idUd");	
		
		idDocAssTask = lRecordEvento.getAttribute("idDocAssTask");	
		uriModAssDocTask = lRecordEvento.getAttribute("uriModAssDocTask");
		idDocTipDocTask = lRecordEvento.getAttribute("idDocTipDocTask");
		uriUltimaVersDocTask = lRecordEvento.getAttribute("uriUltimaVersDocTask");
		mimetypeDocTipAssTask = lRecordEvento.getAttribute("mimetypeDocTipAssTask");
		nomeFileDocTipAssTask = lRecordEvento.getAttribute("nomeFileDocTipAssTask");
		nomeTipDocTask = lRecordEvento.getAttribute("nomeTipDocTask");
		
		build();	

	}
	
	public String getTitleSection() {
		return null;
	}	

	private native void initPageContexMenu(EditorHtmlDetail editorHtmlDetail,
			String functionName, boolean isExternalPortlet) /*-{
	   	if (isExternalPortlet){	
	   		$doc[functionName] = function (value, event) {
	       		editorHtmlDetail.@it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.EditorHtmlDetail::pageContextMenu(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(value, event);
	   		};
		} else {
		   $wnd[functionName] = function (value, event) {
		       editorHtmlDetail.@it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.EditorHtmlDetail::pageContextMenu(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(value, event);
		   };
		}
	}-*/;

	public void pageContextMenu(final String idPage, JavaScriptObject mouseEvent){
		Menu contextMenu = new Menu();


		MenuItem aggiungiPaginaItem = new MenuItem("Aggiungi Pagina", "buttons/add.png");
		aggiungiPaginaItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			@Override
			public void onClick(MenuItemClickEvent event) {
				aggiungiPagina(idPage);
			}});
		MenuItem eliminaPaginaItem = new MenuItem("Elimina Pagina", "buttons/delete.png");
		eliminaPaginaItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			@Override
			public void onClick(MenuItemClickEvent event) {
				eliminaPagina(idPage);
			}});
		contextMenu.addItem(aggiungiPaginaItem);
		if (!idPage.equals("0"))
			contextMenu.addItem(eliminaPaginaItem);
		MenuItem downloadPdfItem = new MenuItem("Download Pdf", "export/pdf.png");
		downloadPdfItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			@Override
			public void onClick(MenuItemClickEvent event) {
				downloadPdf();
			}});
		contextMenu.addItem(downloadPdfItem);
		int x;
		int y;
		if (Layout.isFirefoxBrowser()) {
			x = JSOHelper.getAttributeAsInt(mouseEvent, "clientX");
			y = JSOHelper.getAttributeAsInt(mouseEvent, "clientY");
		} else if (Layout.isIEBrowser()) {
			x = JSOHelper.getAttributeAsInt(mouseEvent, "offsetX");
			y = JSOHelper.getAttributeAsInt(mouseEvent, "offsetY");
		} else {
			x = JSOHelper.getAttributeAsInt(mouseEvent, "x");
			y = JSOHelper.getAttributeAsInt(mouseEvent, "y");
		}
		x = x + descrizioneHtmlItem.getPageLeft();
		y = y + descrizioneHtmlItem.getPageTop() + descrizioneHtmlItem.getCanvas().getChildren()[0].getHeight();
		contextMenu.setRect(x, y, 10, 10);
		if (editing)
		contextMenu.show();
	}

	protected void downloadPdf() {
		Record lRecord = new Record(vm.getValues());
		new GWTRestService<Record, Record>("EditorHtmlDataSource").executecustom("convertToPdf", lRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS)
					DownloadFile.downloadFromUrl(response.getData()[0].getAttribute("uriPdfGenerato"), "generato.pdf");
			}
		});
	}

	protected void aggiungiPagina(String idPage) {
		Record lRecord = new Record(vm.getValues());
		lRecord.setAttribute("smartId", smartId);
		new GWTRestService<Record, Record>("EditorHtmlDataSource").executecustom("aggiungiPagina", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS)
					vm.editRecord(response.getData()[0]);
			}
		});
	}

	protected void eliminaPagina(String idPage) {
		Record lRecord = new Record(vm.getValues());
		lRecord.setAttribute("smartId", smartId);
		GWTRestService<Record, Record> lService = new GWTRestService<Record, Record>("EditorHtmlDataSource");
		lService.addParam("pageNumber", idPage);
		lService.executecustom("eliminaPagina", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS)
					vm.editRecord(response.getData()[0]);
			}
		});
	}

	protected void build() {	
		
		editorHtmlForm = new DynamicForm();
		editorHtmlForm.setWidth100();
		editorHtmlForm.setHeight100();	
		editorHtmlForm.setPadding(5);
		editorHtmlForm.setValuesManager(vm);
		
		descrizioneHtmlItem = new RichTextItem("descrizione");  
		descrizioneHtmlItem.setHeight("100%");
		descrizioneHtmlItem.setWidth("100%");		
		descrizioneHtmlItem.setEndRow(true);
		descrizioneHtmlItem.setColSpan(3);
		descrizioneHtmlItem.setControlGroups("fontControls", "formatControls", "styleControls", "colorControls", "insertControls");

		editorHtmlForm.setFields(descrizioneHtmlItem);			
		
		String titleSection = getTitleSection();
		if(titleSection != null && !"".equals(titleSection)) {
			DetailSection descrizioneHtmlSection = new DetailSection(titleSection, true, true, true, editorHtmlForm);
			addMember(descrizioneHtmlSection);
		} else {
			addMember(editorHtmlForm);
		}				
	}
	
	public void espandi() {
		EditorHtmlMaximizedWindow editorHtmlMaximizedWindow = new EditorHtmlMaximizedWindow(dettaglioPraticaLayout.getDisplayNameEvento(nome), editorHtmlForm.getValuesAsRecord().getAttribute("descrizione"), new ServiceCallback<Record>() {					
			@Override
			public void execute(Record object) {
				
				descrizioneHtmlItem.setValue(object.getAttribute("html"));				
			}
		});
		editorHtmlMaximizedWindow.show();
	}
	
	@Override
	public void save() {
		if(editorHtmlForm.validate()) {
			salvaDati(false, new ServiceCallback<Record>() {
				@Override
				public void execute(Record object) {
					dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {			
						@Override
						public void execute(Record record) {
							Layout.hideWaitPopup();
							back();
						}
					});
				}
			});
		}
	}
	
	public void salvaDati(boolean flgIgnoreObblig, final ServiceCallback<Record> callback) {
		Record lRecord = new Record(vm.getValues());
		lRecord.setAttribute("idProcess", idProcess);
		lRecord.setAttribute("idEvento", idEvento);
		lRecord.setAttribute("idTipoEvento", idTipoEvento);
		lRecord.setAttribute("idUd", idUd);
		lRecord.setAttribute("desEvento", dettaglioPraticaLayout.getDisplayNameEvento(nome));			
		lRecord.setAttribute("idDocAssTask", idDocAssTask);
		lRecord.setAttribute("uriModAssDocTask", uriModAssDocTask);		
		lRecord.setAttribute("idDocTipDocTask", idDocTipDocTask);	
		lRecord.setAttribute("uriUltimaVersDocTask", uriUltimaVersDocTask);		
		lRecord.setAttribute("mimetypeDocTipAssTask", mimetypeDocTipAssTask);		
		lRecord.setAttribute("nomeFileDocTipAssTask", nomeFileDocTipAssTask != null && !"".equals(nomeFileDocTipAssTask) ? nomeFileDocTipAssTask : nome + ".html");
		Layout.showWaitPopup("Salvataggio in corso...");
		GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("EditorHtmlDataSource");
		if(flgIgnoreObblig) {
			lGWTRestService.addParam("flgIgnoreObblig", "1");
		}
		lGWTRestService.call(lRecord, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				if(callback != null) {
					callback.execute(object);
				} else {
					Layout.hideWaitPopup();							
				}
			}
		});				
	}

	@Override
	public void loadDati() {
		Record lRecord = new Record();
		lRecord.setAttribute("idProcess", idProcess);
		lRecord.setAttribute("idEvento", idEvento);
		lRecord.setAttribute("idTipoEvento", idTipoEvento);
		lRecord.setAttribute("idUd", idUd);			
		lRecord.setAttribute("idDocAssTask", idDocAssTask);
		lRecord.setAttribute("uriModAssDocTask", uriModAssDocTask);		
		lRecord.setAttribute("idDocTipDocTask", idDocTipDocTask);	
		lRecord.setAttribute("uriUltimaVersDocTask", uriUltimaVersDocTask);		
		lRecord.setAttribute("mimetypeDocTipAssTask", mimetypeDocTipAssTask);		
		lRecord.setAttribute("nomeFileDocTipAssTask", nomeFileDocTipAssTask);
		lRecord.setAttribute("smartId", smartId);
		Layout.showWaitPopup("Caricamento dati in corso...");
		new GWTRestService<Record, Record>("EditorHtmlDataSource").executecustom("loadDati", lRecord, new DSCallback() {			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS){
					vm.editRecord(response.getData()[0]);
				}
			}
		}); 
	}

}