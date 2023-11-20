/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.firmamultipla.FirmaMultiplaWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.ValuesManager;

public abstract class EditorHtmlFlussoDetail extends EditorHtmlDetail implements LoadDetailInterface, BackDetailInterface {
	
	protected Record recordEvento;
	
	protected String activityName;
	protected String instanceId;
	
	protected String idDoc;
	protected String alertConfermaSalvaDefinitivo;
	
	protected Boolean flgFirmaFile;
	protected Boolean flgConversionePdf;
	
	public EditorHtmlFlussoDetail(String nomeEntita, ValuesManager vm, String idProcess, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {
		
		super(nomeEntita, vm, idProcess, lRecordEvento, dettaglioPraticaLayout);		
	
		this.recordEvento = lRecordEvento;
		
		this.instanceId = lRecordEvento != null ? lRecordEvento.getAttribute("instanceId") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;
	
		this.idDoc = lRecordEvento != null ? lRecordEvento.getAttribute("idDoc") : null;
		this.alertConfermaSalvaDefinitivo = lRecordEvento != null ? lRecordEvento.getAttribute("alertConfermaSalvaDefinitivo") : null;
		
		this.flgFirmaFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgFirmaFile") : null;
		this.flgConversionePdf = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgConversionePdf") : null;		
	}
	
	public abstract boolean isAnteprimaDocumento();
	
	public void reload() {
		dettaglioPraticaLayout.caricaDettaglioEvento(nome);
	}
		
	@Override
	public void back() {
		if(isAnteprimaDocumento()) {
			dettaglioPraticaLayout.caricaDettaglioEvento(nome);
		} else {
			dettaglioPraticaLayout.caricaDettaglioEventoAnnulla(nome);
		}
	}		
	
	public void next() {
		dettaglioPraticaLayout.caricaDettaglioEventoSuccessivo(nome);		
	}
	
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
	
	public void salvaDatiDefinitivo() {
		saveAndSendAlert();
	}
	
	public void saveAndSendAlert() {
		if(editorHtmlForm.validate()) {
			if(alertConfermaSalvaDefinitivo != null && !"".equals(alertConfermaSalvaDefinitivo)) {
				SC.ask(alertConfermaSalvaDefinitivo, new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if(value) {
							saveAndSend();
						}							
					}									
				}); 
			} else {
				saveAndSend();
			}
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	public void saveAndSend() {
		salvaDati(false, new ServiceCallback<Record>() {			
			@Override
			public void execute(Record object) {
				idEvento = object.getAttribute("idEvento");
				Layout.hideWaitPopup();
				recordEvento.setAttribute("idEvento", idEvento);									
				dettaglioPraticaLayout.callExecAtt(recordEvento, new ServiceCallback<Record>() {										
					@Override
					public void execute(Record object) {
						idUd = object.getAttribute("idUd");
						idDoc = object.getAttribute("idDoc");
						Record lRecord = new Record(vm.getValues());
						lRecord.setAttribute("idProcess", idProcess);
						lRecord.setAttribute("idEvento", idEvento);
						lRecord.setAttribute("idTipoEvento", idTipoEvento);
						if(flgConversionePdf != null && flgConversionePdf) {							
							new GWTRestService<Record, Record>("EditorHtmlDataSource").executecustom("convertToPdf", lRecord, new DSCallback() {
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS){
										Record lRecordFile = new Record();
										lRecordFile.setAttribute("nomeFile", nome + ".pdf");
										lRecordFile.setAttribute("idFile", idDoc);
										lRecordFile.setAttribute("idUd", idUd);
										lRecordFile.setAttribute("uri", response.getData()[0].getAttribute("uriPdfGenerato"));
										manageFileGenerato(lRecordFile);										
									}
								}
							});			
						} else {
							new GWTRestService<Record, Record>("EditorHtmlDataSource").executecustom("generaFileHtml", lRecord, new DSCallback() {
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS){
										Record lRecordFile = new Record();
										lRecordFile.setAttribute("nomeFile", nome + ".html");
										lRecordFile.setAttribute("idFile", idDoc);
										lRecordFile.setAttribute("idUd", idUd);
										lRecordFile.setAttribute("uri", response.getData()[0].getAttribute("uriHtmlGenerato"));
										manageFileGenerato(lRecordFile);										
									}
								}
							});
						}
					}
				});									
			}
		});	
	}
	
	public void manageFileGenerato(Record lRecordFile) {
		final Record[] filesAndUd = new Record[1];
		filesAndUd[0] = lRecordFile;
//		Map<String, Record> lMap = new HashMap<String, Record>();
//		for (Record lRecordFileToSign : filesAndUd){
//			lMap.put(lRecordFile.getAttribute("idFile"), lRecordFileToSign);
//		}
//		if(lMap.size() > 0) {	
//			if(flgFirmaFile != null && flgFirmaFile) {
//				FirmaMultiplaWindow lFirmaMultiplaWindow = new FirmaMultiplaWindow(lMap) {
//					@Override
//					public void firmaCallBack(Map<String, Record> files) {					
//						manageFirmaCallBack(files, filesAndUd);
//					}
//				};
//				lFirmaMultiplaWindow.show();
//			} else invia(filesAndUd);
//		}
		if(flgFirmaFile != null && flgFirmaFile) {
			String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
			String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
			FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, filesAndUd, new FirmaMultiplaCallback() {			
				@Override
				public void execute(Map<String, Record> files, Record[] filesAndUd) {
					manageFirmaCallBack(files, filesAndUd);
				}
			});							
		} else invia(filesAndUd);
	}
	
	public void manageFirmaCallBack(Map<String, Record> files, Record[] filesAndUd) {
		Record[] filesToSend = new Record[filesAndUd.length];
		//Gli originali sono filesAndUd 
		int i = 0;
		for (Record lRecordOrig : filesAndUd){
			Record lRecordToSend = new Record(lRecordOrig.getJsObj());
			lRecordToSend.setAttribute("uri", files.get(lRecordOrig.getAttribute("idFile")).getAttribute("uri"));
			lRecordToSend.setAttribute("infoFile", files.get(lRecordOrig.getAttribute("idFile")).getAttribute("infoFile"));
			filesToSend[i] = lRecordToSend;
			i++;
		}
		invia(filesToSend);
	}
	
	public void invia(Record[] filesToSend) {
		Record lRecord = recordEvento;
		lRecord.setAttribute("idProcess", idProcess);
		lRecord.setAttribute("files", filesToSend);
		new GWTRestService<Record, Record>("EditorHtmlFlussoDatasource").call(lRecord, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo","",MessageType.INFO));	
				dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {			
					@Override
					public void execute(Record record) {
						afterInvia(new ServiceCallback<Record>() {							
							@Override
							public void execute(Record object) {
								Layout.hideWaitPopup();
								next();
							}
						});
						
					}
				});
			}
		});	
	}
	
	public void afterInvia(ServiceCallback<Record> callback) {
		if (callback != null) {
			callback.execute(null);
		}
	}
	
	public String getNomeTastoSalvaProvvisorio() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaProvvisorio") : null;
	}
	
	public String getNomeTastoSalvaProvvisorio_2() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaProvvisorio_2") : null;
	}
	
	public String getNomeTastoSalvaDefinitivo() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaDefinitivo") : null;
	}
	
	public String getNomeTastoSalvaDefinitivo_2() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaDefinitivo_2") : null;
	}
	
}
