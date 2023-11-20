/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

public class InvioCollegioSindacaleDetail extends InvioUDMailDetail implements TaskFlussoInterface {
	
	protected InvioCollegioSindacaleDetail instance;
	
	protected Record recordEvento;
	
	protected String idProcess;
	protected String idUd;
	protected String idEvento;
	protected String idTipoEvento;
	protected String rowId;
	protected String activityName;
	protected String instanceId;
	protected String nome;	
	
	protected Record attrEsito;
	protected String assegnatario;
	
	protected DettaglioPraticaLayout dettaglioPraticaLayout;
	
	public InvioCollegioSindacaleDetail(String nomeEntita, String idProcess, String idUd, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {
		
		super(nomeEntita, "PEO");
		
		instance = this;
		
		this.recordEvento = lRecordEvento;		
		
		this.idProcess = idProcess;
		this.idUd = idUd;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.rowId = lRecordEvento != null ? lRecordEvento.getAttribute("rowId") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.instanceId = lRecordEvento != null ? lRecordEvento.getAttribute("instanceId") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;		
		
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;
		
	}

	@Override
	public Record getRecordEvento() {
		return recordEvento;
	}
	
	@Override
	public void loadDati() {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaInvioUDMailDatasource");
		lGwtRestService.extraparam.put("tipoMail", "PEO");
		lGwtRestService.extraparam.put("finalita", "INVIO_COLLEGIO_SINDACALE");
		lGwtRestService.call(lRecordToLoad, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				caricaMail(object);	
				boolean isEseguibile = true;
				if(recordEvento != null && recordEvento.getAttribute("flgEseguibile") != null) {
					isEseguibile = "1".equals(recordEvento.getAttribute("flgEseguibile"));	
				}
				setCanEdit(isEseguibile);					
			}
		});		
	}
	
	public void salvaDati(boolean flgIgnoreObblig, final ServiceCallback<Record> callback) {
		final Record detailRecord = new Record(vm.getValues());					
		detailRecord.setAttribute("idProcess", idProcess);
		detailRecord.setAttribute("idEvento", idEvento);
		detailRecord.setAttribute("idTipoEvento", idTipoEvento);
		detailRecord.setAttribute("desEvento", dettaglioPraticaLayout.getDisplayNameEvento(nome));
		GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("CustomEventDatasource");
		if(flgIgnoreObblig) {
			lGWTRestService.addParam("flgIgnoreObblig", "1");
		} else {
			lGWTRestService.addParam("skipSuccessMsg", "true");
		}
		lGWTRestService.call(detailRecord, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				if(callback != null) {
					callback.execute(object);
				}
			}
		});
	}
	
	protected void callbackSalvaDati(Record object) {
		idEvento = object.getAttribute("idEvento");								
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
					dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {			
						@Override
						public void execute(Record record) {
							Layout.hideWaitPopup();
							Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo","",MessageType.INFO));	
							next();													
						}
					});
				} 										
			}
		});
	}
	
	@Override
	public void salvaDatiProvvisorio() {
		salvaDati(true, new ServiceCallback<Record>() {			
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
		if(validate()) {	
			invio();	
		} else {
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}
	
	protected void invio() {
		final Record lRecordMail = getRecord();
		if(lRecordMail != null) {
			Layout.showWaitPopup("Invio al collegio sindacale in corso: potrebbe richiedere qualche secondo. Attendere...");	
			final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("AurigaInvioUDMailDatasource");
			try {
				lGWTRestDataSource.executecustom("invioMail", lRecordMail, new DSCallback() {			
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {					
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Layout.addMessage(new MessageBean("Inviato al collegio sindacale", "", MessageType.INFO));	
							Layout.hideWaitPopup();	
							salvaDati(false, new ServiceCallback<Record>() {
								@Override
								public void execute(Record object) {		
									callbackSalvaDati(object);											
								}
							});	
						}					
					}
				});
			} catch (Exception e) {
				Layout.addMessage(new MessageBean("Si è verificato un errore durante l'invio al collegio sindacale", "", MessageType.ERROR));			
				Layout.hideWaitPopup();
			}
		}
	}
	
	protected Boolean hasDestinatariNonSelezionatiConMailNonInviata(RecordList destinatariPec) {
		if (destinatariPec!=null){
			for (int i=0; i<destinatariPec.getLength(); i++){
				Record lRecordDestinatario = destinatariPec.get(i);
				Boolean destPrimario = lRecordDestinatario.getAttributeAsBoolean("destPrimario");
				Boolean destCC = lRecordDestinatario.getAttributeAsBoolean("destCC");
				if ((destPrimario==null || !destPrimario) && (destCC==null || !destCC)){
					//Il destinatario non è selezionato
					if(lRecordDestinatario.getAttribute("idMailInviata") == null || 
					   lRecordDestinatario.getAttribute("idMailInviata").trim().equals("")) {
						return true;
					}
				}
			}
			return false;
		}
		return null;
	}
	
	protected Boolean hasTuttiIDestinatariNonSelezionati(RecordList destinatariPec) {
		if (destinatariPec!=null){
			int nro = 0;		
			for (int i=0; i<destinatariPec.getLength(); i++){
				Record lRecordDestinatario = destinatariPec.get(i);
				Boolean destPrimario = lRecordDestinatario.getAttributeAsBoolean("destPrimario");
				Boolean destCC = lRecordDestinatario.getAttributeAsBoolean("destCC");
				if ((destPrimario==null || !destPrimario) && (destCC==null || !destCC)){
					//Il destinatario non è selezionato
					nro++;					
				}
			}
			return nro == destinatariPec.getLength();
		}
		return null;
	}
	
	public String getValidateErrorMessage() {
		return I18NUtil.getMessages().validateError_message();
	}
	
	@Override
	public String getNomeTastoSalvaProvvisorio() {
		return null;
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
