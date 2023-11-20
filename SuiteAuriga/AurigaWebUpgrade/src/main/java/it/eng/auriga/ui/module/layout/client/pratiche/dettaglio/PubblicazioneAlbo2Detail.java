/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class PubblicazioneAlbo2Detail extends CustomDetail implements TaskFlussoInterface {

	protected PubblicazioneAlbo2Detail instance;

	protected VLayout layoutAnteprima;
	protected HTMLPane mHtmlPane;

	protected Record recordEvento;

	protected String idProcess;
	protected String idTipoEvento;
	protected String idEvento;
	protected String activityName;
	protected String instanceId;
	protected String nome;
	protected String idUd;

	protected String uriModCopertina;
	protected String tipoModCopertina;
	protected String uriModCopertinaFinale;
	protected String tipoModCopertinaFinale;
	protected String uriModAppendice;
	protected String tipoModAppendice;
	protected String tipoModAssDocTask;

	protected Boolean fileUnioneDaFirmare;
	protected Boolean fileUnioneDaTimbrare;

	protected DettaglioPraticaLayout dettaglioPraticaLayout;

	protected Record recordProtocollo;

	public boolean isModelloPubblicazioneAllegato = false;

	public PubblicazioneAlbo2Detail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento,
			DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita);

		instance = this;

		this.recordEvento = lRecordEvento;

		this.idProcess = idProcess;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.instanceId = lRecordEvento != null ? lRecordEvento.getAttribute("instanceId") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;

		this.idUd = lRecordEvento != null ? lRecordEvento.getAttribute("idUd") : null;

		this.dettaglioPraticaLayout = dettaglioPraticaLayout;

		this.uriModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("uriModCopertina") : null;
		this.tipoModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModCopertina") : null;
		this.uriModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("uriModCopertinaFinale") : null;
		this.tipoModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModCopertinaFinale") : null;
		this.uriModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("uriModAppendice") : null;
		this.tipoModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAppendice") : null;
		this.tipoModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAssDocTask") : null;

		this.fileUnioneDaFirmare = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("fileUnioneDaFirmare") : null;
		this.fileUnioneDaTimbrare = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("fileUnioneDaTimbrare") : null;

		layoutAnteprima = new VLayout();
		layoutAnteprima.setHeight100();
		layoutAnteprima.setWidth100();

		mHtmlPane = new HTMLPane();
		mHtmlPane.setContentsType(ContentsType.PAGE);
		mHtmlPane.setWidth100();
		mHtmlPane.setHeight100();

		layoutAnteprima.addMember(mHtmlPane);

		addMember(layoutAnteprima);

	}

	@Override
	public Record getRecordEvento() {
		return recordEvento;
	}
	
	@Override
	public void loadDati() {
		final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lProtocolloDataSource.addParam("idProcess", idProcess);
		lProtocolloDataSource.addParam("taskName", activityName);
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		lProtocolloDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

					recordProtocollo = response.getData()[0];

					RecordList listaAllegati = recordProtocollo.getAttributeAsRecordList("listaAllegati");

					int posModello = getPosModello(DettaglioPraticaLayout.getNomeModelloPubblicazione(), listaAllegati);

					if (posModello != -1) {

						Record allegato = listaAllegati.get(posModello);
						showVersionePerPubblicazione(allegato.getAttribute("uriFileAllegato"), DettaglioPraticaLayout.getNomeFileModelloPubblicazione(),
								new InfoFileRecord(allegato.getAttributeAsRecord("infoFile")));
						isModelloPubblicazioneAllegato = true;

					} else {

						compilazioneAutomaticaVersionePerPubblicazione(new ServiceCallback<Record>() {

							@Override
							public void execute(Record result) {
								String uriFile = result.getAttribute("uri");
								InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
								showVersionePerPubblicazione(uriFile, DettaglioPraticaLayout.getNomeFileModelloPubblicazione(), infoFile);
							}
						});

					}
				}
			}
		});
	}

	public void compilazioneAutomaticaVersionePerPubblicazione(final ServiceCallback<Record> callback) {
		Record modelloRecord = new Record();
		modelloRecord.setAttribute("processId", idProcess);
		modelloRecord.setAttribute("uriModCopertina", uriModCopertina);
		modelloRecord.setAttribute("tipoModCopertina", tipoModCopertina);
		modelloRecord.setAttribute("uriModAppendice", uriModAppendice);
		modelloRecord.setAttribute("tipoModAppendice", tipoModAppendice);
		modelloRecord.setAttribute("uriModello", recordProtocollo.getAttribute("uriFileVerPreFirma"));
		modelloRecord.setAttribute("tipoModello", tipoModAssDocTask);
		modelloRecord.setAttribute("nomeFile", recordProtocollo.getAttribute("nomeFileVerPreFirma"));

		new GWTRestService<Record, Record>("CompilaModelloDatasource").executecustom("compilaModelloAnteprimaConCopertina", modelloRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

					Record result = response.getData()[0];

					String uriFilePdf = result.getAttribute("uri");

					Record modelloRecord = new Record();
					modelloRecord.setAttribute("processId", idProcess);
					modelloRecord.setAttribute("uriModCopertinaFinale", uriModCopertinaFinale);
					modelloRecord.setAttribute("tipoModCopertinaFinale", tipoModCopertinaFinale);
					modelloRecord.setAttribute("uri", uriFilePdf);
					modelloRecord.setAttribute("nomeFile", DettaglioPraticaLayout.getNomeFileModelloPubblicazione());
					modelloRecord.setAttribute("uriModCopertina", uriModCopertina);
					modelloRecord.setAttribute("tipoModCopertina", tipoModCopertina);
					modelloRecord.setAttribute("uriModAppendice", uriModAppendice);
					modelloRecord.setAttribute("tipoModAppendice", tipoModAppendice);
					modelloRecord.setAttribute("flgNoPubblPrimario", recordProtocollo.getAttribute("flgNoPubblPrimario"));
					try {
						modelloRecord.setAttribute("filePrimarioVerPubbl", recordProtocollo.getAttributeAsRecordList("listaFilePrimarioVerPubbl").get(0));
					} catch (Exception e) {
					}
					modelloRecord.setAttribute("flgDatiSensibili", recordProtocollo.getAttribute("flgDatiSensibili"));	
					modelloRecord.setAttribute("filePrimarioOmissis", recordProtocollo.getAttributeAsRecord("filePrimarioOmissis"));
					modelloRecord.setAttribute("listaAllegati", recordProtocollo.getAttributeAsRecordList("listaAllegati"));

					new GWTRestService<Record, Record>("CompilaModelloDatasource").executecustom("compilaVersionePerPubblicazione", modelloRecord,
							new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record result = response.getData()[0];
										if (callback != null) {
											callback.execute(result);
										}
									}
								}
							});

				}

			}
		});
	}

	public int getPosModello(String descrizioneFileAllegato, RecordList listaAllegati) {
		if (listaAllegati != null) {
			for (int i = 0; i < listaAllegati.getLength(); i++) {
				Record allegato = listaAllegati.get(i);
				if (allegato.getAttribute("descrizioneFileAllegato") != null
						&& allegato.getAttribute("descrizioneFileAllegato").equalsIgnoreCase(descrizioneFileAllegato)) {
					return i;
				}
			}
		}
		return -1;
	}

	protected void aggiungiModelloAdAllegati(String descrizioneFileAllegato, String uriFileAllegato, String nomeFileAllegato, InfoFileRecord infoAllegato) {

		if (recordProtocollo != null) {

			RecordList listaAllegati = recordProtocollo.getAttributeAsRecordList("listaAllegati");

			int posModello = getPosModello(descrizioneFileAllegato, listaAllegati);
			
			Record lRecordModello = new Record();
			if (posModello != -1) {
				lRecordModello = listaAllegati.get(posModello);
			}
			
			lRecordModello.setAttribute("nomeFileAllegato", nomeFileAllegato);
			lRecordModello.setAttribute("uriFileAllegato", uriFileAllegato);
			lRecordModello.setAttribute("descrizioneFileAllegato", descrizioneFileAllegato);
			lRecordModello.setAttribute("nomeFileAllegatoTif", "");
			lRecordModello.setAttribute("uriFileAllegatoTif", "");
			lRecordModello.setAttribute("remoteUri", false);
			lRecordModello.setAttribute("isChanged", true);
			lRecordModello.setAttribute("nomeFileVerPreFirma", nomeFileAllegato);
			lRecordModello.setAttribute("uriFileVerPreFirma", uriFileAllegato);
			lRecordModello.setAttribute("infoFileVerPreFirma", infoAllegato);
			lRecordModello.setAttribute("improntaVerPreFirma", infoAllegato.getImpronta());
			lRecordModello.setAttribute("infoFile", infoAllegato);
			
			if (posModello == -1) {
				if (listaAllegati == null || listaAllegati.getLength() == 0) {
					listaAllegati = new RecordList();
				}
				listaAllegati.addAt(lRecordModello, 0);
			} else {
				listaAllegati.set(posModello, lRecordModello);
			}

			recordProtocollo.setAttribute("listaAllegati", listaAllegati);

		}

	}

	public void showVersionePerPubblicazione(String uri, String nomeFile, InfoFileRecord info) {
		String recordType = "FileToExtractBean";
		String mimetype = "application/pdf";
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", nomeFile);
		lRecord.setAttribute("uri", uri);
		if (info != null && info.isFirmato() && info.getTipoFirma().startsWith("CAdES")) {
			lRecord.setAttribute("sbustato", "true");
		} else {
			lRecord.setAttribute("sbustato", "false");
		}
		lRecord.setAttribute("remoteUri", true);
		String url = GWT.getHostPageBaseURL() + "springdispatcher/preview?fromRecord=true&mimetype=" + mimetype + "&recordType="
				+ DownloadFile.encodeURL(recordType) + "&record=" + DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));
		mHtmlPane.setContentsURL(url);
		mHtmlPane.markForRedraw();
	}

	@Override
	public void salvaDatiProvvisorio() {
		
	}

	public void salvaDatiProtocollo(final ServiceCallback<Record> callback) {
		if (!isModelloPubblicazioneAllegato) {
			compilazioneAutomaticaVersionePerPubblicazione(new ServiceCallback<Record>() {

				@Override
				public void execute(Record result) {

					if (fileUnioneDaFirmare != null && fileUnioneDaFirmare && fileUnioneDaTimbrare != null && fileUnioneDaTimbrare) {
						timbraFileUnione(result, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								firmaFileUnione(object, new FirmaCallback() {

									@Override
									public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord infoFileFirmato) {
										aggiungiModelloAdAllegatiAndUpdate(uriFileFirmato, nomeFileFirmato, infoFileFirmato, callback);
									}
								});
							}
						});
					} else if (fileUnioneDaFirmare != null && fileUnioneDaFirmare) {
						firmaFileUnione(result, new FirmaCallback() {

							@Override
							public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord infoFileFirmato) {
								aggiungiModelloAdAllegatiAndUpdate(uriFileFirmato, nomeFileFirmato, infoFileFirmato, callback);
							}
						});
					} else if (fileUnioneDaTimbrare != null && fileUnioneDaTimbrare) {
						timbraFileUnione(result, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								String uriFile = object.getAttribute("uri");
								String nomeFile = DettaglioPraticaLayout.getNomeFileModelloPubblicazione();
								InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(object.getAttributeAsRecord("infoFile").getJsObj()));
								aggiungiModelloAdAllegatiAndUpdate(uriFile, nomeFile, infoFile, callback);
							}
						});
					} else {
						String uriFile = result.getAttribute("uri");
						String nomeFile = DettaglioPraticaLayout.getNomeFileModelloPubblicazione();
						InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
						aggiungiModelloAdAllegatiAndUpdate(uriFile, nomeFile, infoFile, callback);
					}
				}
			});
		} else {
			if (callback != null) {
				callback.execute(recordProtocollo);
			}
		}
	}

	public void firmaFileUnione(Record record, FirmaCallback callback) {
		String uriFile = record.getAttribute("uri");
		String nomeFile = DettaglioPraticaLayout.getNomeFileModelloPubblicazione();
		InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(record.getAttributeAsRecord("infoFile").getJsObj()));
		// Leggo gli eventuali parametri per forzare il tipo d firma
		String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
		String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");
		FirmaUtility.firmaMultipla(true, true, appletTipoFirmaAtti, hsmTipoFirmaAtti, uriFile, nomeFile, infoFile, callback);
	}

	public void timbraFileUnione(Record record, final ServiceCallback<Record> callback) {
		String uriFile = record.getAttribute("uri");
		String nomeFile = DettaglioPraticaLayout.getNomeFileModelloPubblicazione();
		InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(record.getAttributeAsRecord("infoFile").getJsObj()));
		Record lRecordFileUnione = new Record();
		lRecordFileUnione.setAttribute("uriFileAllegato", uriFile);
		lRecordFileUnione.setAttribute("nomeFileAllegato", nomeFile);
		lRecordFileUnione.setAttribute("infoFile", infoFile);
		final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lProtocolloDataSource.addParam("idUd", recordProtocollo.getAttribute("idUd"));
		lProtocolloDataSource.executecustom("timbraFileAllegato", lRecordFileUnione, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordFileUnioneTimbrato = response.getData()[0];
					Record lRecordCallback = new Record();
					lRecordCallback.setAttribute("uri", lRecordFileUnioneTimbrato.getAttribute("uriFileAllegato"));
					lRecordCallback.setAttribute("nomeFile", lRecordFileUnioneTimbrato.getAttribute("nomeFileAllegato"));
					InfoFileRecord infoFileAllegato = InfoFileRecord.buildInfoFileString(JSON.encode(lRecordFileUnioneTimbrato.getAttributeAsRecord("infoFile")
							.getJsObj()));
					lRecordCallback.setAttribute("infoFile", infoFileAllegato);
					if (callback != null) {
						callback.execute(lRecordCallback);
					}
				}
			}
		});
	}

	public void aggiungiModelloAdAllegatiAndUpdate(String uriFile, String nomeFile, InfoFileRecord infoFile, final ServiceCallback<Record> callback) {

		aggiungiModelloAdAllegati(DettaglioPraticaLayout.getNomeModelloPubblicazione(), uriFile, nomeFile, infoFile);

		Record lRecordToSave = new Record(recordProtocollo.toMap());
		final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lProtocolloDataSource.addParam("isPropostaAtto", "true");
		lProtocolloDataSource.addParam("idProcess", idProcess);
		lProtocolloDataSource.addParam("taskName", activityName);		
		lProtocolloDataSource.addParam("idTaskCorrente", getIdTaskCorrente());
		lProtocolloDataSource.updateData(lRecordToSave, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					isModelloPubblicazioneAllegato = true;
					if (callback != null) {
						callback.execute(recordProtocollo);
					}
				}
			}
		});
	}

	public String getIdTaskCorrente() {
		return nome.substring(0, nome.indexOf("|*|"));
	}

	@Override
	public void salvaDatiDefinitivo() {
		salvaDatiProtocollo(new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PubblicazioneAlbo2Datasource");
				lGwtRestService.call(object, new ServiceCallback<Record>() {

					@Override
					public void execute(Record record) {
						Record lRecordEvent = new Record();
						lRecordEvent.setAttribute("idProcess", idProcess);
						lRecordEvent.setAttribute("idEvento", idEvento);
						lRecordEvent.setAttribute("idTipoEvento", idTipoEvento);
						lRecordEvent.setAttribute("idUd", idUd);
						lRecordEvent.setAttribute("desEvento", dettaglioPraticaLayout.getDisplayNameEvento(nome));
						// lRecordEvent.setAttribute("messaggio", messaggio);
						GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("CustomEventDatasource");
						lGWTRestService.addParam("skipSuccessMsg", "true");
						lGWTRestService.call(lRecordEvent, new ServiceCallback<Record>() {

							@Override
							public void execute(final Record object) {
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
						});
					}
				});
			}
		});
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

	@Override
	public void back() {
		dettaglioPraticaLayout.caricaDettaglioEventoAnnulla(nome);
	}

	public void next() {
		dettaglioPraticaLayout.caricaDettaglioEventoSuccessivo(nome);
	}

}
