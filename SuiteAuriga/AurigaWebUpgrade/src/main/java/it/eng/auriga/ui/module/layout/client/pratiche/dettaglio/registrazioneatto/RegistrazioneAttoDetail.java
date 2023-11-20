/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskFlussoInterface;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.core.DataClass;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;

public class RegistrazioneAttoDetail extends CustomDetail implements TaskFlussoInterface {
	
	protected String idProcess;
	protected String idTipoEvento;
	protected String idEvento;
	protected String activityName;
	protected String instanceId;
	protected String nome;	
	private String alertConfermaSalvaDefinitivo;
	
	protected Record recordEvento;

	protected DettaglioPraticaLayout dettaglioPraticaLayout;
	private HTMLPane mHtmlPane;
	private DynamicForm mDynamicForm;

	public RegistrazioneAttoDetail(String nomeEntita, ValuesManager vm, String idProcess, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {
		super(nomeEntita,vm);
		
		this.recordEvento = lRecordEvento;

		this.idProcess = idProcess;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.instanceId = lRecordEvento != null ? lRecordEvento.getAttribute("instanceId") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;		
		this.alertConfermaSalvaDefinitivo = lRecordEvento != null ? lRecordEvento.getAttribute("alertConfermaSalvaDefinitivo") : null;
		
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;
		
		build();
	}
	
	private void build() {
		mHtmlPane = new HTMLPane();
		mHtmlPane.setContentsType(ContentsType.PAGE);
		mHtmlPane.setWidth100();
		mHtmlPane.setHeight100();
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(mHtmlPane);
		mDynamicForm = new DynamicForm();
		mDynamicForm.setValuesManager(vm);
		SelectItem lSelectItem = new SelectItem("annoRegistrazione", "Anno registrazione");
		lSelectItem.setWrapTitle(false);
		lSelectItem.setWidth(150);
		ListGrid pickListProperties = new ListGrid(); 		
		pickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		lSelectItem.setPickListProperties(pickListProperties);
		lSelectItem.setDisplayField("value");
		lSelectItem.setValueField("key");
		lSelectItem.setRequired(true);
		DataSource lDataSourceAnnoCorrente = creaAnnoCorrenteDatasource();
		lSelectItem.setOptionDataSource(lDataSourceAnnoCorrente);
		HiddenItem lHiddenItemIdUd = new HiddenItem("idUD");
		HiddenItem lHiddenItemUriPdf = new HiddenItem("uriPdf");
		HiddenItem lHiddenItemSiglaRegistroAtto = new HiddenItem("siglaRegistroAtto");
		HiddenItem lHiddenItemIdTipoDoc = new HiddenItem("idTipoDoc");
		
		mDynamicForm.setFields(lSelectItem, lHiddenItemIdUd, lHiddenItemUriPdf, lHiddenItemSiglaRegistroAtto, lHiddenItemIdTipoDoc);
		addMember(mDynamicForm);
		addMember(lVLayout);
		
	}
	protected DataSource creaAnnoCorrenteDatasource() {
		DataSource lDataSourceAnnoCorrente = new DataSource();
		lDataSourceAnnoCorrente.setClientOnly(true);
		String today = DateUtil.format(new Date());
		int annoCorrente = Integer.parseInt(today.substring(today.lastIndexOf("/") + 1));
		Record[] lRecordAnni = new Record[annoCorrente];
		int count = 0;
		for (int i = annoCorrente; i>2000; i--){
			Record lRecord = new Record();
			lRecord.setAttribute("key", i);
			lRecord.setAttribute("value", i);
			lRecordAnni[count] = lRecord;
			count++;
		}
		lDataSourceAnnoCorrente.setTestData(lRecordAnni);
		return lDataSourceAnnoCorrente;
	}
	
	@Override
	public Record getRecordEvento() {
		return recordEvento;
	}
	
	@Override
	public void loadDati() {
//		Record lRecord = new Record();
//		lRecord.setAttribute("idProcess", idProcess);
//		lRecord.setAttribute("idEvento", idEvento);
//		lRecord.setAttribute("idTipoEvento", idTipoEvento);
//		lRecord.setAttribute("fileDaUnire", recordEvento.getAttributeAsRecordArray("fileDaUnire"));
//		lRecord.setAttribute("idUd", recordEvento.getAttribute("idUd"));
		Layout.showWaitPopup("Caricamento dati in corso...");

		new GWTRestService<Record, Record>("RegistrazioneAttoDatasource").executecustom("loadDati", recordEvento, new DSCallback() {			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS){
					Record object = response.getData()[0];
					vm.setValue("uriPdf", object.getAttributeAsString("uriPdf"));
					String recordType = "FileToExtractBean";
					String mimetype = "application/pdf";
					Record lRecord = new Record();
					lRecord.setAttribute("displayFilename", "");
					lRecord.setAttribute("uri", object.getAttributeAsString("uriPdf"));
					lRecord.setAttribute("sbustato", "false");
					lRecord.setAttribute("remoteUri", object.getAttributeAsBoolean("remoteUri"));
					String url = GWT.getHostPageBaseURL() + "springdispatcher/preview?fromRecord=true&mimetype="+mimetype+"&recordType="+DownloadFile.encodeURL(recordType)+"&record=" + DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));
					mHtmlPane.setContentsURL(url);
					mHtmlPane.markForRedraw();
				}
			}
		}); 
	}

	public void salvaDati(final ServiceCallback<Record> callback) {	
		Record lRecordToSave = new Record(recordEvento.toMap());
		lRecordToSave.setAttribute("annoRegistrazione", vm.getValue("annoRegistrazione"));
		lRecordToSave.setAttribute("uriPdf", vm.getValue("uriPdf"));
		Layout.showWaitPopup("Salvataggio in corso...");
		GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("RegistrazioneAttoDatasource");		
		lGWTRestService.call(lRecordToSave, new ServiceCallback<Record>() {			
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
	public void salvaDatiProvvisorio() {
		salvaDati(new ServiceCallback<Record>() {			
			@Override
			public void execute(Record object) {
				dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {			
					@Override
					public void execute(Record record) {
						Layout.hideWaitPopup();
						reload();
					}
				});
			}
		});	
	}
	
	@Override
	public void salvaDatiDefinitivo() {
		saveAndGoAlert();
	}
	
	public String getValidateErrorMessage() {
		return I18NUtil.getMessages().validateError_message();
	}
	
	public void saveAndGoAlert() {
		if(validate()) {
			if(alertConfermaSalvaDefinitivo != null && !"".equals(alertConfermaSalvaDefinitivo)) {
				SC.ask(alertConfermaSalvaDefinitivo, new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if(value) {
							saveAndGo();
						}
					}					
				});
			} else {
				saveAndGo();
			}											
		} else {
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}	
	
	public void saveAndGo() {
		salvaDati(new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {		
				idEvento = object.getAttribute("idEvento");		
				final String estremiRegAtto = object.getAttribute("estremiRegAtto");	
				Record lRecord = new Record();								
				lRecord.setAttribute("instanceId", instanceId);
				lRecord.setAttribute("activityName", activityName);		
				lRecord.setAttribute("idProcess", idProcess);
				lRecord.setAttribute("idEventType", idTipoEvento);
				lRecord.setAttribute("idEvento", idEvento);									
				GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
				lAurigaTaskDataSource.executecustom("salvaTask", lRecord, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS){
							Layout.hideWaitPopup();
							Layout.addMessage(new MessageBean("Registrazione atto " + estremiRegAtto + " conclusa","",MessageType.INFO));	
							dettaglioPraticaLayout.managePraticaConclusa();							
						} 										
					}
				});											
			}
		});	
	}
	
	@Override
	public String getNomeTastoSalvaProvvisorio() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaProvvisorio") : null;
	}
	
	@Override
	public String getNomeTastoSalvaDefinitivo() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaDefinitivo") : null;
	}
	
	@Override
	public String getNomeTastoSalvaDefinitivo_2() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaDefinitivo_2") : null;
	}

	@Override
	public boolean hasDocumento() {
		return false;
	}	
	
	public void reload() {
		dettaglioPraticaLayout.caricaDettaglioEvento(nome);
	}
	
	@Override
	public void back() {
		dettaglioPraticaLayout.caricaDettaglioEventoAnnulla(nome);
	}
	
	public void next() {
		dettaglioPraticaLayout.caricaDettaglioEventoSuccessivo(nome);
	}
	
}
