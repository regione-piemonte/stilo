/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.OpenEditorUtility;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility.OpenEditorCallback;
import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.BackDetailInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.LoadDetailInterface;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

public abstract class EditorAppletDetail extends CustomDetail implements LoadDetailInterface, BackDetailInterface {

	protected Record recordEvento;

	protected String idProcess;
	protected String idTipoEvento;
	protected String idEvento;
	protected String activityName;
	protected String instanceId;
	protected String nome;
	private String alertConfermaSalvaDefinitivo;

	protected String idUd;

	protected String idDocAssTask;
	protected String uriModAssDocTask;
	protected String tipoModAssDocTask;
	protected String idDocTipDocTask;
	protected String uriUltimaVersDocTask;
	protected String mimetypeDocTipAssTask;
	protected String nomeFileDocTipAssTask;
	protected String nomeTipDocTask;

	private HTMLPane mHtmlPane;

	// url del documento da visualizzare/modificare
	private String uriDoc;
	private String impronta;
	private String uriPdfGenerato;
	private String filenamePdfGenerato;
	private String improntaPdf;

	protected Boolean flgFirmaFile;
	protected Boolean flgConversionePdf;

	protected DettaglioPraticaLayout dettaglioPraticaLayout;

	public EditorAppletDetail(String nomeEntita, ValuesManager vm, String idProcess, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita, vm);

		this.recordEvento = lRecordEvento;

		this.idProcess = idProcess;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.instanceId = lRecordEvento != null ? lRecordEvento.getAttribute("instanceId") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;
		this.alertConfermaSalvaDefinitivo = lRecordEvento != null ? lRecordEvento.getAttribute("alertConfermaSalvaDefinitivo") : null;

		this.dettaglioPraticaLayout = dettaglioPraticaLayout;

		this.idUd = lRecordEvento != null ? lRecordEvento.getAttribute("idUd") : null;

		this.idDocAssTask = lRecordEvento != null ? lRecordEvento.getAttribute("idDocAssTask") : null;
		this.uriModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("uriModAssDocTask") : null;
		this.tipoModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAssDocTask") : null;
		this.idDocTipDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("idDocTipDocTask") : null;
		this.uriUltimaVersDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("uriUltimaVersDocTask") : null;
		this.mimetypeDocTipAssTask = lRecordEvento != null ? lRecordEvento.getAttribute("mimetypeDocTipAssTask") : null;
		this.nomeFileDocTipAssTask = lRecordEvento != null ? lRecordEvento.getAttribute("nomeFileDocTipAssTask") : null;
		this.nomeTipDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("nomeTipDocTask") : null;
		
		this.flgFirmaFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgFirmaFile") : null;
		this.flgConversionePdf = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgConversionePdf") : null;	

		mHtmlPane = new HTMLPane();
		mHtmlPane.setContentsType(ContentsType.PAGE);
		mHtmlPane.setWidth100();
		mHtmlPane.setHeight100();
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(mHtmlPane);
		addMember(lVLayout);

	}

	public void modificaDocumento() {
		final Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		new GWTRestService<Record, Record>("EditorAppletDataSource").executecustom("lockUd", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					OpenEditorUtility.openEditor(nomeFileDocTipAssTask, uriDoc, false,
							nomeFileDocTipAssTask.substring(nomeFileDocTipAssTask.lastIndexOf(".") + 1), impronta, new OpenEditorCallback() {

								@Override
								public void execute(String nomeFileFirmato, final String uriFileFirmato, String record) {
									new GWTRestService<Record, Record>("EditorAppletDataSource").executecustom("unlockUd", lRecord, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											uriUltimaVersDocTask = uriFileFirmato;
											loadDati();
										}

									});

								}
							});
				}
			}
		});
	}

	public abstract boolean isAnteprimaDocumento();

	public void loadDati() {

		Record lRecord = new Record();
		lRecord.setAttribute("idProcess", idProcess);
		lRecord.setAttribute("idEvento", idEvento);
		lRecord.setAttribute("idTipoEvento", idTipoEvento);

		lRecord.setAttribute("idUd", idUd);
		lRecord.setAttribute("idDocAssTask", idDocAssTask);
		lRecord.setAttribute("uriModAssDocTask", uriModAssDocTask);
		lRecord.setAttribute("tipoModAssDocTask", tipoModAssDocTask);
		lRecord.setAttribute("idDocTipDocTask", idDocTipDocTask);
		lRecord.setAttribute("nomeTipDocTask", nomeTipDocTask);
		lRecord.setAttribute("uriUltimaVersDocTask", uriUltimaVersDocTask);
		lRecord.setAttribute("mimetypeDocTipAssTask", mimetypeDocTipAssTask);
		lRecord.setAttribute("nomeFileDocTipAssTask", nomeFileDocTipAssTask);
		Layout.showWaitPopup("Caricamento dati in corso...");

		new GWTRestService<Record, Record>("EditorAppletDataSource").executecustom("loadDati", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record object = response.getData()[0];
					String recordType = "FileToExtractBean";
					String mimetype = "application/pdf";
					Record lRecord = new Record();
					lRecord.setAttribute("displayFilename", object.getAttributeAsString("filenamePdfGenerato"));
					lRecord.setAttribute("uri", object.getAttributeAsString("uriPdfGenerato"));
					lRecord.setAttribute("sbustato", "false");
					lRecord.setAttribute("remoteUri", object.getAttributeAsBoolean("remoteUri"));
					String url = GWT.getHostPageBaseURL() + "springdispatcher/preview?fromRecord=true&mimetype=" + mimetype + "&recordType="
							+ DownloadFile.encodeURL(recordType) + "&record=" + DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));
					mHtmlPane.setContentsURL(url);
					mHtmlPane.markForRedraw();
					uriPdfGenerato = object.getAttributeAsString("uriPdfGenerato");
					filenamePdfGenerato = object.getAttributeAsString("filenamePdfGenerato");
					improntaPdf = object.getAttributeAsString("improntaPdf");
					uriDoc = object.getAttribute("uriDoc");
					impronta = object.getAttribute("impronta");
					uriUltimaVersDocTask = object.getAttribute("uriUltimaVersDocTask");
					nomeFileDocTipAssTask = object.getAttribute("nomeFileDocTipAssTask");
					uriModAssDocTask = object.getAttribute("uriModAssDocTask");
					tipoModAssDocTask = object.getAttribute("tipoModAssDocTask");
					nomeTipDocTask = object.getAttribute("nomeTipDocTask");
					// vm.editRecord(response.getData()[0]);
				}
			}
		});
	}

	private void salvaDati(boolean flgIgnoreObblig, final ServiceCallback<Record> callback) {
		Record lRecord = new Record(vm.getValues());
		lRecord.setAttribute("idProcess", idProcess);
		lRecord.setAttribute("idEvento", idEvento);
		lRecord.setAttribute("idTipoEvento", idTipoEvento);
		lRecord.setAttribute("idUd", idUd);
		lRecord.setAttribute("desEvento", dettaglioPraticaLayout.getDisplayNameEvento(nome));
		// lRecord.setAttribute("messaggio", messaggio);
		lRecord.setAttribute("idDocAssTask", idDocAssTask);
		lRecord.setAttribute("uriModAssDocTask", uriModAssDocTask);
		lRecord.setAttribute("tipoModAssDocTask", tipoModAssDocTask);
		lRecord.setAttribute("idDocTipDocTask", idDocTipDocTask);
		lRecord.setAttribute("nomeTipDocTask", nomeTipDocTask);
		lRecord.setAttribute("uriUltimaVersDocTask", uriUltimaVersDocTask);
		lRecord.setAttribute("mimetypeDocTipAssTask", mimetypeDocTipAssTask);
		lRecord.setAttribute("nomeFileDocTipAssTask", nomeFileDocTipAssTask);
		Layout.showWaitPopup("Salvataggio in corso...");		
		GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("EditorAppletDataSource");
		if (flgIgnoreObblig) {
			lGWTRestService.addParam("flgIgnoreObblig", "1");
		}
		lGWTRestService.call(lRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				if (callback != null) {
					callback.execute(object);
				} else {
					Layout.hideWaitPopup();
				}
			}
		});
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

	public String getValidateErrorMessage() {
		return I18NUtil.getMessages().validateError_message();
	}

	public void saveAndSendAlert() {
		if (validate()) {
			if (alertConfermaSalvaDefinitivo != null && !"".equals(alertConfermaSalvaDefinitivo)) {
				SC.ask(alertConfermaSalvaDefinitivo, new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value) {
							saveAndSend();
						}
					}
				});
			} else {
				saveAndSend();
			}
		} else {
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}

	public void saveAndSend() {
		salvaDati(false, new ServiceCallback<Record>() {

			@Override
			public void execute(final Record objectSaved) {
				Layout.hideWaitPopup();
				recordEvento.setAttribute("idEvento", idEvento);
				dettaglioPraticaLayout.callExecAtt(recordEvento, new ServiceCallback<Record>() {

					@Override
					public void execute(final Record object) {
						Layout.hideWaitPopup();
						idEvento = object.getAttribute("idEvento");
						idUd = object.getAttribute("idUd");
						idDocAssTask = object.getAttribute("idDoc");
						Record lRecord = new Record(vm.getValues());
						lRecord.setAttribute("idProcess", idProcess);
						lRecord.setAttribute("idEvento", idEvento);
						lRecord.setAttribute("idTipoEvento", idTipoEvento);
						final Record lRecordFile = new Record();
						lRecordFile.setAttribute("idFile", idDocAssTask);
						lRecordFile.setAttribute("idUd", idUd);
						if (flgConversionePdf != null && flgConversionePdf) {
							lRecordFile.setAttribute("nomeFile", filenamePdfGenerato);
							lRecordFile.setAttribute("uri", uriPdfGenerato);
							Record lInfoFileRecord = new Record();
							lInfoFileRecord.setAttribute("mimetype", "application/pdf");
							lInfoFileRecord.setAttribute("impronta", improntaPdf);
							lRecordFile.setAttribute("infoFile", lInfoFileRecord);
						} else {
							lRecordFile.setAttribute("nomeFile", nomeFileDocTipAssTask);
							lRecordFile.setAttribute("uri", uriDoc);
							Record lInfoFileRecord = new Record();
							lInfoFileRecord.setAttribute("mimetype", mimetypeDocTipAssTask);
							lInfoFileRecord.setAttribute("impronta", impronta);
							lRecordFile.setAttribute("infoFile", lInfoFileRecord);
						}
						final Record[] filesAndUd = new Record[1];
						filesAndUd[0] = lRecordFile;
						// Map<String, Record> lMap = new HashMap<String, Record>();
						// for (Record lRecordFileToSign : filesAndUd){
						// lMap.put(lRecordFile.getAttribute("idFile"), lRecordFileToSign);
						// }
						// if(lMap.size() > 0) {
						// if(flgFirmaFile != null && flgFirmaFile) {
						// FirmaMultiplaWindow lFirmaMultiplaWindow = new FirmaMultiplaWindow(lMap) {
						// @Override
						// public void firmaCallBack(Map<String, Record> files) {
						// manageFirmaCallBack(files, filesAndUd, object);
						// }
						// };
						// lFirmaMultiplaWindow.show();
						// } else invia(filesAndUd, object);
						// }
						if (activityName.equals("FIRMA_COPERTINA")) {
							// Cacco Federico 26-12-2015
							// Invece di firmare il file lo timbro

							// Ottengo il bean per il file da timbrare
							String display = lRecordFile.getAttribute("nomeFile");
							String uri = lRecordFile.getAttribute("uri");
							String mimetype = lRecordFile.getAttributeAsRecord("infoFile").getAttribute("mimetype");
							String idUd = lRecordFile.getAttribute("idUd");
							
							String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
									AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
							String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
									AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
							
							FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, false, mimetype, idUd,
									rotazioneTimbroPref,posizioneTimbroPref);
							// Ottengo i parametri di default
							GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("LoadTimbraDefault");
							lGwtRestService.call(lFileDaTimbrareBean, new ServiceCallback<Record>() {

								@Override
								public void execute(Record lRecordParametri) {
									// Cacco Federico 26-12-2015
									// Leggo i parametri di timbratura (da sovrascrivere a quelli di default) dal risultato della chiamata callExecAtt,
									// in modo da poter bypassare la finistra di dialogo che li chiede
									lRecordParametri.setAttribute("posizioneTimbro", object.getAttribute("posizioneTimbro"));
									lRecordParametri.setAttribute("tipoPagina", object.getAttribute("tipoPagina"));
									lRecordParametri.setAttribute("paginaDa", object.getAttribute("paginaDa"));
									lRecordParametri.setAttribute("paginaA", object.getAttribute("paginaA"));
									lRecordParametri.setAttribute("rotazioneTimbro", object.getAttribute("rotazioneTimbro"));
									// Eseguo la timbratura
									Layout.showWaitPopup("Timbratura in corso: potrebbe richiedere qualche secondo. Attendere...");
									GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TimbraDatasource");
									lGwtRestService.call(lRecordParametri, new ServiceCallback<Record>() {

										@Override
										public void execute(Record lRecordTimbrato) {
											Record[] filesToSend = new Record[1];
											Record lRecordToSend = new Record(lRecordFile.getJsObj());
											lRecordToSend.setAttribute("nomeFile", filenamePdfGenerato);
											lRecordToSend.setAttribute("uri", lRecordTimbrato.getAttribute("uri"));
											filesToSend[0] = lRecordToSend;
											invia(filesToSend, object);
										}
									});
								}
							});

							// Vecchio codice che chiedeva i parametri di timbratura tramite GUI
							// final TimbraWindow lTimbraWindow = new TimbraWindow("timbra", true, lFileDaTimbrareBean) {
							// @Override
							// protected void timbraCallBack(Record lRecordTimbrato) {							
							// close();
							// Record[] filesToSend = new Record[1];
							// Record lRecordToSend = new Record(lRecordFile.getJsObj());
							// lRecordToSend.setAttribute("displayFilename", filenamePdfGenerato);
							// lRecordToSend.setAttribute("uri", lRecordTimbrato.getAttribute("uri"));
							// filesToSend[0] = lRecordToSend;
							// invia(filesToSend, object);
							// }
							// };
							// lTimbraWindow.show();
						} else if (flgFirmaFile != null && flgFirmaFile) {
							String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
							String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
							FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, filesAndUd, new FirmaMultiplaCallback() {

								@Override
								public void execute(Map<String, Record> files, Record[] filesAndUd) {
									manageFirmaCallBack(files, filesAndUd, object);
								}
							});
						} else
							invia(filesAndUd, object);
					}
				});
			}
		});
	}

	public void manageFirmaCallBack(Map<String, Record> files, Record[] filesAndUd, Record object) {
		Record[] filesToSend = new Record[filesAndUd.length];
		// Gli originali sono filesAndUd
		int i = 0;
		for (Record lRecordOrig : filesAndUd) {
			Record lRecordToSend = new Record(lRecordOrig.getJsObj());
			lRecordToSend.setAttribute("uri", files.get(lRecordOrig.getAttribute("idFile")).getAttribute("uri"));
			lRecordToSend.setAttribute("nomeFile", files.get(lRecordOrig.getAttribute("idFile")).getAttribute("nomeFile"));
			lRecordToSend.setAttribute("infoFile", files.get(lRecordOrig.getAttribute("idFile")).getAttribute("infoFile"));
			filesToSend[i] = lRecordToSend;
			i++;
		}
		invia(filesToSend, object);
	}

	public void invia(Record[] filesToSend, Record object) {
		Record lRecord = new Record(object.toMap());
		lRecord.setAttribute("uriUltimaVersDocTask", filesToSend[0].getAttribute("uri"));
		lRecord.setAttribute("nomeFileDocTipAssTask", filesToSend[0].getAttribute("nomeFile"));
		lRecord.setAttribute("infoFileUltimaVersDocTask", filesToSend[0].getAttribute("infoFile"));
		lRecord.setAttribute("desEvento", dettaglioPraticaLayout.getDisplayNameEvento(nome));
		new GWTRestService<Record, Record>("EditorAppletDataSource").executecustom("salvaFirmatoAvanzaTask", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

						@Override
						public void execute(Record record) {
							afterInvia(new ServiceCallback<Record>() {

								@Override
								public void execute(Record object) {
									Layout.hideWaitPopup();
									Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
									next();
								}
							});
						}
					});
				}
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
	
	public void reload() {
		dettaglioPraticaLayout.caricaDettaglioEvento(nome);
	}

	public void back() {
		if (isAnteprimaDocumento()) {
			dettaglioPraticaLayout.caricaDettaglioEvento(nome);
		} else {
			dettaglioPraticaLayout.caricaDettaglioEventoAnnulla(nome);
		}
	}

	public void next() {
		dettaglioPraticaLayout.caricaDettaglioEventoSuccessivo(nome);
	}

}
