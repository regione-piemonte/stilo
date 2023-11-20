/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowWithCallback;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneAttiAttachExtensionValidator;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class UnioneFileAttoDetail extends PropostaAttoDetail {

	protected UnioneFileAttoDetail instance;

	protected Boolean fileUnioneDaFirmare;
	protected Boolean fileUnioneDaTimbrare;
	protected Boolean flgInvioFtpAlbo;
	
	protected Set<String> esitiTaskOk;

	public UnioneFileAttoDetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, lRecordEvento, dettaglioPraticaLayout);

		instance = this;

		fileUnioneDaFirmare = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("fileUnioneDaFirmare") : null;
		fileUnioneDaTimbrare = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("fileUnioneDaTimbrare") : null;
		flgInvioFtpAlbo = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgInvioFtpAlbo") : null;
		
		RecordList listaEsitiTaskOk = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("esitiTaskOk") : null;
		if(listaEsitiTaskOk != null && listaEsitiTaskOk.getLength() > 0) {
			esitiTaskOk = new HashSet<String>();
			for(int i = 0; i < listaEsitiTaskOk.getLength(); i++) {				
				Record esito = listaEsitiTaskOk.get(i);
				esitiTaskOk.add(esito.getAttribute("valore"));
			}			
		}
	}

	public boolean isEsitoTaskOk(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return (esito == null || esitiTaskOk == null || (esito != null && esitiTaskOk != null && esitiTaskOk.contains(esito)));		
	}

	@Override
	public void saveAndGo() {
		if(isEsitoTaskOk(attrEsito)) {
			salvaDati(true, new ServiceCallback<Record>() {
				@Override
				public void execute(Record object) {	
					Layout.hideWaitPopup();					
					dettaglioPraticaLayout.callExecAtt(nome, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							unioneFileAndReturn(object, new ServiceCallback<Record>() {
								@Override
								public void execute(Record object) {
									salvaDati(false, new ServiceCallback<Record>() {
										@Override
										public void execute(Record object) {		
											callbackSalvaDati(object);								
										}
									});
								}
							});
						}
					});					
				}
			});															
		} else {
			super.saveAndGo();
		}
	}

	@Override
	protected void callbackSalvaDati(Record object) {
		idEvento = object.getAttribute("idEvento");
		Record lRecord = new Record();
		lRecord.setAttribute("instanceId", instanceId);
		lRecord.setAttribute("activityName", activityName);
		lRecord.setAttribute("idProcess", idProcess);
		lRecord.setAttribute("idEventType", idTipoEvento);
		lRecord.setAttribute("idEvento", idEvento);
		lRecord.setAttribute("note", messaggio);
		GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
		if (flgInvioFtpAlbo != null && flgInvioFtpAlbo) {					
			lAurigaTaskDataSource.addParam("flgInvioFtpAlbo", "true");
		}
		lAurigaTaskDataSource.executecustom("salvaTask", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

						@Override
						public void execute(Record record) {
							Layout.hideWaitPopup();
							Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
							next();
						}
					});
				}
			}
		});
	}

	public void unioneFileAndReturn(Record record, final ServiceCallback<Record> callback){
		record.setAttribute("valori", getAttributiDinamiciDoc());
		record.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());		
		new GWTRestService<Record, Record>("UnioneFileAttoDatasource").call(record, new ServiceCallback<Record>() {				
			@Override
			public void execute(final Record recordFileUnione) {			

				Layout.hideWaitPopup();					
				timbraFileUnione(recordFileUnione, new ServiceCallback<Record>() {

					@Override
					public void execute(final Record recordFileTimbrato) {

						final String uriFileTimbrato = recordFileTimbrato.getAttribute("uri");	
						final String nomeFileTimbrato = recordFileTimbrato.getAttribute("nomeFile");		
						final InfoFileRecord infoFileTimbrato = InfoFileRecord.buildInfoFileString(JSON.encode(recordFileTimbrato.getAttributeAsRecord("infoFile").getJsObj()));				
						new PreviewWindowWithCallback(uriFileTimbrato, false, infoFileTimbrato, "FileToExtractBean",	nomeFileTimbrato, new ServiceCallback<Record>() {

							@Override
							public void execute(Record recordPreview) {

								if ( recordFileTimbrato != null && recordFileTimbrato.getAttribute("nomeFileVersIntegrale") != null && !"".equals(recordFileTimbrato.getAttribute("nomeFileVersIntegrale"))) {
									final String uriFileTimbratoVersIntegrale = recordFileTimbrato.getAttribute("uriVersIntegrale");	
									final String nomeFileTimbratoVersIntegrale = recordFileTimbrato.getAttribute("nomeFileVersIntegrale");		
									final InfoFileRecord infoFileTimbratoVersIntegrale = InfoFileRecord.buildInfoFileString(JSON.encode(recordFileTimbrato.getAttributeAsRecord("infoFileVersIntegrale").getJsObj()));				

									new PreviewWindowWithCallback(uriFileTimbratoVersIntegrale, false, infoFileTimbratoVersIntegrale, "FileToExtractBean", nomeFileTimbratoVersIntegrale, new ServiceCallback<Record>() {

										@Override
										public void execute(Record recordPreview) {
											firmaAggiungiFileUnione(recordFileTimbrato, callback);

										}
									});
								} else {
									firmaAggiungiFileUnione(recordFileTimbrato, callback);

								}
							}
						});
					}
				});								
			}
			
			@Override
			public void manageError() {
				Layout.hideWaitPopup();									
			}
		});	
	}


	/**
	 * @author FEBUONO
	 * 
	 * Metodo per gestire la firma e l'aggiunta dei file unione (con e senza omisses)
	 * 
	 * @param record
	 * @param callback
	 */
	public void firmaAggiungiFileUnione(Record recordFile, final ServiceCallback<Record> callback) {

		firmaFileUnione(recordFile, new ServiceCallback<Record>() {

			@Override
			public void execute(final Record recordFileFirmato) {
				invioMail(recordFileFirmato, new BooleanCallback() {
					
					@Override
					public void execute(Boolean value) {														
						if(value) {
							
							String uriFileFirmato = recordFileFirmato.getAttribute("uri");
							String nomeFileFirmato = recordFileFirmato.getAttribute("nomeFile");		
							InfoFileRecord infoFileFirmato = InfoFileRecord.buildInfoFileString(JSON.encode(recordFileFirmato.getAttributeAsRecord("infoFile").getJsObj()));
							aggiungiFilePrimario(uriFileFirmato, nomeFileFirmato, infoFileFirmato, callback);

							if ( recordFileFirmato != null && recordFileFirmato.getAttribute("nomeFileVersIntegrale") != null && !"".equals(recordFileFirmato.getAttribute("nomeFileVersIntegrale"))) {
								String uriFileFirmatoVersIntegrale = recordFileFirmato.getAttribute("uriVersIntegrale");
								String nomeFileFirmatoVersIntegrale = recordFileFirmato.getAttribute("nomeFileVersIntegrale");		
								String tipoFileAllegatoVersIntegrale = recordFileFirmato.getAttribute("tipoDocFileUnioneVersIntegrale");
								InfoFileRecord infoFileFirmatoVersIntegrale = InfoFileRecord.buildInfoFileString(JSON.encode(recordFileFirmato.getAttributeAsRecord("infoFileVersIntegrale").getJsObj()));
								aggiungiFileAllegato(uriFileFirmatoVersIntegrale, nomeFileFirmatoVersIntegrale, infoFileFirmatoVersIntegrale, tipoFileAllegatoVersIntegrale, callback);
							}	
							
							if(callback != null) {
								callback.execute(null);
							}
						}																												
					}
				});				
			}
		});

	}

	public void firmaFileUnione(final Record record, final ServiceCallback<Record> callback) {

		if (fileUnioneDaFirmare != null && fileUnioneDaFirmare) {		
			Record[] lRecordList;
			Record lRecord = new Record();
			InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(record.getAttributeAsRecord("infoFile").getJsObj()));
			lRecord.setAttribute("uri", record.getAttribute("uri"));
			lRecord.setAttribute("isFilePrincipaleAtto", "true");
			lRecord.setAttribute("nomeFile", record.getAttribute("nomeFile"));
			lRecord.setAttribute("infoFile", infoFile);
			lRecord.setAttribute("idFile", record.getAttribute("uri"));

			// Leggo gli eventuali parametri per forzare il tipo d firma
			//			String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
			//			String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			

			if ( record != null && record.getAttribute("nomeFileVersIntegrale") != null && !"".equals(record.getAttribute("nomeFileVersIntegrale"))) {
				lRecordList = new Record[2];
				Record lRecordVersIntegrale = new Record();
				InfoFileRecord infoFileVersIntegrale = InfoFileRecord.buildInfoFileString(JSON.encode(record.getAttributeAsRecord("infoFileVersIntegrale").getJsObj()));
				lRecordVersIntegrale.setAttribute("uri", record.getAttribute("uriVersIntegrale"));
				lRecordVersIntegrale.setAttribute("isFilePrincipaleAtto", "true");
				lRecordVersIntegrale.setAttribute("nomeFile", record.getAttribute("nomeFileVersIntegrale"));
				lRecordVersIntegrale.setAttribute("infoFile", infoFileVersIntegrale);
				lRecordVersIntegrale.setAttribute("idFile", record.getAttribute("uriVersIntegrale"));
				lRecordList[0] = lRecord;
				lRecordList[1] = lRecordVersIntegrale;
			} else {
				lRecordList = new Record[1];
				lRecordList[0] = lRecord;
			}

			String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
			String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
			FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, lRecordList, new FirmaMultiplaCallback() {

				@Override
				public void execute(Map<String, Record> files, Record[] filesAndUd) {
					
					Record lRecord = files.get(record.getAttribute("uri"));
					Record lRecordCallback = new Record();
					lRecordCallback.setAttribute("nomeFile", lRecord.getAttribute("nomeFile"));
					lRecordCallback.setAttribute("uri", lRecord.getAttribute("uri"));
					InfoFileRecord infoFileFirmato = InfoFileRecord.buildInfoFileString(JSON.encode(lRecord.getAttributeAsRecord("infoFile").getJsObj()));
					lRecordCallback.setAttribute("infoFile", infoFileFirmato);
					if(filesAndUd.length > 1) {
						Record lRecordVersIntegrale = files.get(record.getAttribute("uriVersIntegrale"));

						lRecordCallback.setAttribute("nomeFileVersIntegrale", lRecordVersIntegrale.getAttribute("nomeFile"));
						lRecordCallback.setAttribute("tipoDocFileUnioneVersIntegrale", record.getAttribute("tipoDocFileUnioneVersIntegrale"));
						lRecordCallback.setAttribute("uriVersIntegrale", lRecordVersIntegrale.getAttribute("uri"));
						InfoFileRecord infoFileFirmatoVersIntegrale = InfoFileRecord.buildInfoFileString(JSON.encode(lRecordVersIntegrale.getAttributeAsRecord("infoFile").getJsObj()));
						lRecordCallback.setAttribute("infoFileVersIntegrale", infoFileFirmatoVersIntegrale); 
					}
					if (callback != null) {
						callback.execute(lRecordCallback);
					}
				}
			});
		} else if(callback != null) {
			callback.execute(record);
		}
	}

	public void timbraFileUnione(Record record, final ServiceCallback<Record> callback) {
		if (fileUnioneDaTimbrare != null && fileUnioneDaTimbrare) {
			//			String uriFile = record.getAttribute("uri");
			//			String nomeFile = record.getAttribute("nomeFile");
			//			InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(record.getAttributeAsRecord("infoFile").getJsObj()));
			//			Record lRecordFileUnione = new Record();
			//			lRecordFileUnione.setAttribute("uriFilePrimario", uriFile);
			//			lRecordFileUnione.setAttribute("nomeFilePrimario", nomeFile);
			//			lRecordFileUnione.setAttribute("infoFile", infoFile);

			final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("UnioneFileAttoDatasource");
			lProtocolloDataSource.addParam("idUd", getIdUd());
			lProtocolloDataSource.executecustom("timbraFileUnione", record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record lRecordFileUnioneTimbrato = response.getData()[0];
						Record lRecordCallback = new Record();
						lRecordCallback.setAttribute("uri", lRecordFileUnioneTimbrato.getAttribute("uri"));
						lRecordCallback.setAttribute("nomeFile", lRecordFileUnioneTimbrato.getAttribute("nomeFile"));
						lRecordCallback.setAttribute("infoFile", InfoFileRecord.buildInfoFileString(JSON.encode(lRecordFileUnioneTimbrato.getAttributeAsRecord("infoFile").getJsObj())));
						if ( lRecordFileUnioneTimbrato != null && lRecordFileUnioneTimbrato.getAttribute("nomeFileVersIntegrale") != null && !"".equals(lRecordFileUnioneTimbrato.getAttribute("nomeFileVersIntegrale"))) {
							lRecordCallback.setAttribute("uriVersIntegrale", lRecordFileUnioneTimbrato.getAttribute("uriVersIntegrale"));
							lRecordCallback.setAttribute("nomeFileVersIntegrale", lRecordFileUnioneTimbrato.getAttribute("nomeFileVersIntegrale"));
							lRecordCallback.setAttribute("infoFileVersIntegrale", InfoFileRecord.buildInfoFileString(JSON.encode(lRecordFileUnioneTimbrato.getAttributeAsRecord("infoFileVersIntegrale").getJsObj())));
							lRecordCallback.setAttribute("tipoDocFileUnioneVersIntegrale", lRecordFileUnioneTimbrato.getAttribute("tipoDocFileUnioneVersIntegrale"));							
						}

						if (callback != null) {
							callback.execute(lRecordCallback);
						}
					}
				}
			});
		} else if(callback != null) {
			callback.execute(record);
		}
	}

	protected void aggiungiFilePrimario(String uri, String nomeFile, InfoFileRecord infoFile, final ServiceCallback<Record> callback) {		

		if(filePrimarioForm != null) {		

			filePrimarioForm.setValue("nomeFilePrimario", nomeFile);
			filePrimarioForm.setValue("uriFilePrimario", uri);
			filePrimarioForm.setValue("nomeFilePrimarioTif", "");
			filePrimarioForm.setValue("uriFilePrimarioTif", "");
			filePrimarioForm.setValue("remoteUriFilePrimario", false);
			filePrimarioForm.setValue("uriFileVerPreFirma", uri);
			filePrimarioForm.setValue("nomeFileVerPreFirma", nomeFile);

			changedEventAfterUpload(nomeFile, uri);

			filePrimarioForm.setValue("improntaVerPreFirma", infoFile.getImpronta());

			InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;

			String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
			filePrimarioForm.setValue("infoFile", infoFile);

			String nomeFilePrimarioOrig = filePrimarioForm.getValueAsString("nomeFilePrimarioOrig");

			if (precImpronta == null
					|| !precImpronta.equals(infoFile.getImpronta())
					|| (nomeFile != null && !"".equals(nomeFile) && nomeFilePrimarioOrig != null && !"".equals(nomeFilePrimarioOrig) && !nomeFile
					.equals(nomeFilePrimarioOrig))) {
				manageChangedFilePrimario();
			}

			if (!ProtocollazioneAttiAttachExtensionValidator.getInstance().validate(infoFile)) {
				GWTRestDataSource.printMessage(new MessageBean("Il file primario risulta in un formato non ammesso", "", MessageType.WARNING));
			}

			if (infoFile.isFirmato() && !infoFile.isFirmaValida()) {
				GWTRestDataSource.printMessage(new MessageBean("Il file primario presenta una firma non valida alla data odierna", "", MessageType.WARNING));
			}

			filePrimarioForm.markForRedraw();
			filePrimarioButtons.markForRedraw();
			manageChangedPrimario();

//			if(callback != null) {
//				callback.execute(null);
//			}
		}			
	}


	/**
	 * @author FEBUONO
	 * 
	 * Metodo per aggiungere come allegato il file timbrato e firmato in versione integrale
	 * 
	 * @param uri
	 * @param nomeFile
	 * @param infoFile
	 * @param tipoAllegato
	 * @param callback
	 * 
	 */
	protected void aggiungiFileAllegato(String uri, String nomeFile, InfoFileRecord infoFile, String idTipoAllegato, final ServiceCallback<Record> callback) {		

		if(fileAllegatiForm != null) {		
			RecordList lRecordListAllegati = fileAllegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");

			if (lRecordListAllegati == null || lRecordListAllegati.getLength() == 0) {
				lRecordListAllegati = new RecordList();
			}

			Record lRecordAllegato = new Record();
			lRecordAllegato.setAttribute("nomeFileAllegato", nomeFile);
			lRecordAllegato.setAttribute("uriFileAllegato", uri);
			lRecordAllegato.setAttribute("remoteUri", false);
			lRecordAllegato.setAttribute("infoFile", infoFile);
			lRecordAllegato.setAttribute("idTipoFileAllegato", idTipoAllegato);
			lRecordAllegato.setAttribute("listaTipiFileAllegato", idTipoAllegato);
			lRecordAllegato.setAttribute("nomeFileAllegatoTif", "");
			lRecordAllegato.setAttribute("uriFileAllegatoTif", "");
			lRecordAllegato.setAttribute("nomeFileVerPreFirma", nomeFile);
			lRecordAllegato.setAttribute("uriFileVerPreFirma", uri);
			lRecordAllegato.setAttribute("improntaVerPreFirma", infoFile.getImpronta());

			int posAllegato = getPosModelloFromTipo(idTipoAllegato, lRecordListAllegati);
			if (posAllegato == -1) {
				if (lRecordListAllegati == null || lRecordListAllegati.getLength() == 0) {
					lRecordListAllegati = new RecordList();
				}
				lRecordListAllegati.addAt(lRecordAllegato, 0);
			} else {
				lRecordListAllegati.set(posAllegato, lRecordAllegato);
			}

			Record lRecord = new Record();
			lRecord.setAttribute("listaAllegati", lRecordListAllegati);
			fileAllegatiForm.setValues(lRecord.toMap());
			if(fileAllegatiItem != null && (fileAllegatiItem instanceof AllegatiItem)) {
				((AllegatiItem)fileAllegatiItem).resetCanvasChanged();
			}

			if (!ProtocollazioneAttiAttachExtensionValidator.getInstance().validate(infoFile)) {
				GWTRestDataSource.printMessage(new MessageBean("Il file allegato risulta in un formato non ammesso", "", MessageType.WARNING));
			}

			if (infoFile.isFirmato() && !infoFile.isFirmaValida()) {
				GWTRestDataSource.printMessage(new MessageBean("Il file allegato presenta una firma non valida alla data odierna", "", MessageType.WARNING));
			}

			if (detailSectionAllegati != null) {
				detailSectionAllegati.openIfhasValue();
			}

//			if(callback != null) {
//				callback.execute(null);
//			}
		}			
	}
	
}