/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.iterAtti.SelezionaEsitoTaskWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.NuovoMessaggioWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatoCanvas;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowWithCallback;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneAttiAttachExtensionValidator;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailBozze;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class TaskDettUdGenDetail extends ProtocollazioneDetailBozze implements TaskFlussoInterface {

	protected TaskDettUdGenDetail instance;

	protected Record recordEvento;

	protected String idProcess;
	protected String nomeFlussoWF;
	protected String processNameWF;
	protected String idEvento;
	protected String idTipoEvento;
	protected String rowId;
	protected String activityName;
	protected String instanceId;
	protected String nome;
	protected String alertConfermaSalvaDefinitivo;
	protected Record attrEsito;
	protected String messaggio;

	protected String tipoModAssDocTask;

	protected String idUd;

	protected String codTabDefault;
	protected String idTipoTaskDoc;
	protected String nomeTipoTaskDoc;

	protected Boolean flgUnioneFile;
	protected Boolean flgTimbraFile;
	protected Boolean flgFirmaFile;
	protected Boolean flgInvioPEC;
 
	protected DettaglioPraticaLayout dettaglioPraticaLayout;

	protected Set<String> esitiTaskOk;
	protected HashMap<String, Record> controlliXEsitiTaskDoc;

	public TaskDettUdGenDetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento,
			DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita);

		instance = this;

		this.recordEvento = lRecordEvento;

		this.idProcess = idProcess;
		this.nomeFlussoWF = nomeFlussoWF;
		this.processNameWF = processNameWF;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.rowId = lRecordEvento != null ? lRecordEvento.getAttribute("rowId") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.instanceId = lRecordEvento != null ? lRecordEvento.getAttribute("instanceId") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;
		this.alertConfermaSalvaDefinitivo = lRecordEvento != null ? lRecordEvento.getAttribute("alertConfermaSalvaDefinitivo") : null;

		this.tipoModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAssDocTask") : null;

		this.idUd = idUd;

		this.codTabDefault = lRecordEvento != null ? lRecordEvento.getAttribute("codTabDefault") : null;
		this.idTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoTaskDoc") : null;
		this.nomeTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("nomeTipoTaskDoc") : null;

		this.flgUnioneFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgUnioneFile") : null;
		this.flgTimbraFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgTimbraFile") : null;
		this.flgFirmaFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgFirmaFile") : null;
		this.flgInvioPEC = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgInvioPEC") : null;
	
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;

		RecordList listaEsitiTaskOk = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("esitiTaskOk") : null;
		if (listaEsitiTaskOk != null && listaEsitiTaskOk.getLength() > 0) {
			esitiTaskOk = new HashSet<String>();
			for (int i = 0; i < listaEsitiTaskOk.getLength(); i++) {
				Record esito = listaEsitiTaskOk.get(i);
				esitiTaskOk.add(esito.getAttribute("valore"));
			}
		}

		RecordList listaControlliXEsitiTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("controlliXEsitiTaskDoc") : null;
		if (listaControlliXEsitiTaskDoc != null && listaControlliXEsitiTaskDoc.getLength() > 0) {
			controlliXEsitiTaskDoc = new HashMap<String, Record>();
			for (int i = 0; i < listaControlliXEsitiTaskDoc.getLength(); i++) {
				Record recordControllo = listaControlliXEsitiTaskDoc.get(i);
				controlliXEsitiTaskDoc.put(recordControllo.getAttribute("esito"), recordControllo);
			}
		}
		
	}
	
	@Override
	public boolean isModalitaAllegatiGrid() {
		return false;
	}
	
	@Override
	public boolean isModalitaWizard() {
		return false;
	}	

	public void afterCaricaAttributiDinamiciDoc() {
		super.afterCaricaAttributiDinamiciDoc();
		try {
			if (codTabDefault != null && !"".equals(codTabDefault)) {
				tabSet.selectTab(codTabDefault);
			} else {
				tabSet.selectTab(0);
			}
		} catch (Exception e) {
		}
	}
	
	public boolean hasActionUnioneFile() {
		return flgUnioneFile != null && flgUnioneFile;
	}
	
	public boolean hasActionTimbra() {
		return flgTimbraFile != null && flgTimbraFile;
	}
	
	public boolean hasActionFirma() {
		return flgFirmaFile != null && flgFirmaFile;
	}
	
	public boolean hasActionInvioMail() {
		return flgInvioPEC != null && flgInvioPEC;
	}
	
	public boolean isEsitoTaskOk(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return (esito == null || esitiTaskOk == null || (esito != null && esitiTaskOk != null && esitiTaskOk.contains(esito)));
	}

	public String getIdTaskCorrente() {
		return nome.substring(0, nome.indexOf("|*|"));
	}

	public HashSet<String> getTipiModelliAtti() {
		return dettaglioPraticaLayout != null ? dettaglioPraticaLayout.getTipiModelliAtti() : null;
	}

	protected VLayout createLayoutTab(VLayout layout, VLayout spacerLayout) {
		VLayout layoutTab = new VLayout();
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		layoutTab.addMember(spacerLayout);
		layoutTab.setRedrawOnResize(true);
		return layoutTab;
	}

	public boolean isEseguibile() {
		boolean isEseguibile = true;
		if (recordEvento != null && recordEvento.getAttribute("flgEseguibile") != null) {
			isEseguibile = "1".equals(recordEvento.getAttribute("flgEseguibile"));
		}
		return isEseguibile;
	}

	public boolean isReadOnly() {
		boolean isReadOnly = false;
		if (recordEvento != null && recordEvento.getAttribute("flgReadOnly") != null) {
			isReadOnly = recordEvento.getAttributeAsBoolean("flgReadOnly");
		}
		return isReadOnly;
	}
	
	@Override
	public Record getRecordEvento() {
		return recordEvento;
	}

	@Override
	public void loadDati() {

		loadDettUd(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				caricaAttributiDinamiciDoc(nomeFlussoWF, processNameWF, activityName, tipoDocumento, rowidDoc);
				if (isEseguibile()) {
					editMode();
				} else {
					viewMode();
				}
				if (isEseguibile()) {
					if (isReadOnly()) {
						readOnlyMode();
					} else {
						editMode();
					}
				} else {
					viewMode();
				}
			}
		});

	}

	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
		if (!isEseguibile()) {
			// se il task non è eseguibile disabilito tutti gli attributi dinamici
			if (attributiAddDocDetails != null) {
				for (String key : attributiAddDocDetails.keySet()) {
					AttributiDinamiciDetail detail = attributiAddDocDetails.get(key);
					detail.setCanEdit(false);
					// for (DynamicForm form : detail.getForms()) {
					// setCanEdit(false, form);
					// }
				}
			}
		}
	}

	public void readOnlyMode() {
		viewMode();
		if(fileAllegatiItem != null && (fileAllegatiItem instanceof AllegatiItem)) {
			((AllegatiItem)fileAllegatiItem).readOnlyMode();
		}
	}

	@Override
	public void editRecord(Record record) {
		vm.clearErrors(true);
		clearTabErrors(tabSet);
		super.editRecord(record);
		if (isEseguibile()) {
			if (idTipoTaskDoc != null && !"".equals(idTipoTaskDoc) && nomeTipoTaskDoc != null && !"".equals(nomeTipoTaskDoc)) {
				if(fileAllegatiItem != null && (fileAllegatiItem instanceof AllegatiItem)) {
					AllegatoCanvas lAllegatoCanvas = ((AllegatiItem)fileAllegatiItem).getAllegatoCanvasFromTipo(idTipoTaskDoc);
					if (lAllegatoCanvas == null) {
						lAllegatoCanvas = (AllegatoCanvas) ((AllegatiItem)fileAllegatiItem).onClickNewButton();
						lAllegatoCanvas.setFixedTipoFileAllegato(idTipoTaskDoc, nomeTipoTaskDoc);
					}
				}
			}
		}
	}

	@Override
	public void clearTabErrors() {
		clearTabErrors(tabSet);
	}
	
	@Override
	public void showTabErrors() {
		showTabErrors(tabSet);
	}
	
	@Override
	public boolean customValidate() {
		boolean valid = super.customValidate(); // perche estendo ProtocollazioneDetailBozze	
		if (idTipoTaskDoc != null && !"".equals(idTipoTaskDoc) && nomeTipoTaskDoc != null && !"".equals(nomeTipoTaskDoc)) {
			final String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
			Record recordControllo = esito != null && !"".equals(esito) ? controlliXEsitiTaskDoc.get(esito) : null;
			if (recordControllo == null) {
				recordControllo = controlliXEsitiTaskDoc.get("#ANY");
			}
			final boolean flgObbligatorio = recordControllo != null && recordControllo.getAttribute("flgObbligatorio") != null
					&& "1".equals(recordControllo.getAttribute("flgObbligatorio"));
			final boolean flgFileObbligatorio = recordControllo != null && recordControllo.getAttribute("flgFileObbligatorio") != null
					&& "1".equals(recordControllo.getAttribute("flgFileObbligatorio"));
			final boolean flgFileFirmato = recordControllo != null && recordControllo.getAttribute("flgFileFirmato") != null
					&& "1".equals(recordControllo.getAttribute("flgFileFirmato"));
			if(fileAllegatiItem != null && (fileAllegatiItem instanceof AllegatiItem)) {				
				AllegatoCanvas lAllegatoCanvas = ((AllegatiItem)fileAllegatiItem).getAllegatoCanvasFromTipo(idTipoTaskDoc);
				if (flgObbligatorio && lAllegatoCanvas == null) {
					lAllegatoCanvas = (AllegatoCanvas) ((AllegatiItem)fileAllegatiItem).onClickNewButton();
					lAllegatoCanvas.setFixedTipoFileAllegato(idTipoTaskDoc, nomeTipoTaskDoc);
				}
				if (lAllegatoCanvas != null) {
					String uriFileAllegato = lAllegatoCanvas.getFormValuesAsRecord().getAttribute("uriFileAllegato");
					InfoFileRecord infoFileAllegato = lAllegatoCanvas.getForm()[0].getValue("infoFile") != null ? new InfoFileRecord(
							lAllegatoCanvas.getForm()[0].getValue("infoFile")) : null;
					if (flgFileObbligatorio && (uriFileAllegato == null || uriFileAllegato.equals("") || infoFileAllegato == null)) {
						lAllegatoCanvas.getForm()[0].setFieldErrors("nomeFileAllegato", "Il file è obbligatorio");
						valid = false;
					} else if (flgFileFirmato && (uriFileAllegato != null && !uriFileAllegato.equals(""))
							&& (infoFileAllegato != null && !infoFileAllegato.isFirmato())) {
						lAllegatoCanvas.getForm()[0].setFieldErrors("nomeFileAllegato", "Il file non è firmato");
						valid = false;
					} else if (flgFileFirmato && (uriFileAllegato != null && !uriFileAllegato.equals(""))
							&& (infoFileAllegato != null && !infoFileAllegato.isFirmaValida())) {
						lAllegatoCanvas.getForm()[0].setFieldErrors("nomeFileAllegato", "Il file presenta una firma non valida alla data odierna");
						valid = false;
					}
				}
			}
		}
		return valid;
	}
	
	public Boolean validateSenzaObbligatorieta() {
		clearTabErrors(tabSet);
		vm.clearErrors(true);
		boolean valid = true;
		if (attributiAddDocDetails != null) {
			for (String key : attributiAddDocDetails.keySet()) {
				boolean esitoAttributiAddDocDetail = attributiAddDocDetails.get(key).validateSenzaObbligatorieta();
				valid = valid && esitoAttributiAddDocDetail;
			}
		}
		showTabErrors(tabSet);
		if (valid) {
			setSaved(valid);
		} else {
			reopenAllSections();			
		}
		return valid;
	}

	public void loadDettUd(final DSCallback callback) {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lProtocolloDataSource.addParam("isTaskDettUdGen", "true");
		lProtocolloDataSource.addParam("idProcess", idProcess);
		lProtocolloDataSource.addParam("taskName", activityName);
		lProtocolloDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					rowidDoc = lRecord.getAttribute("rowidDoc");
					tipoDocumento = lRecord.getAttribute("tipoDocumento");
					editRecord(lRecord, recordNum);
					if (callback != null) {
						callback.execute(response, rawData, request);
					}
				}
			}
		});
	}

	public Record getRecordToSave(String motivoVarDatiReg) {
		Record lRecordToSave = super.getRecordToSave(motivoVarDatiReg);
		lRecordToSave.setAttribute("idProcess", idProcess);
		if (attributiAddDocDetails != null) {
			lRecordToSave.setAttribute("idUd", idUd);
			lRecordToSave.setAttribute("tipoDocumento", tipoDocumento);
			lRecordToSave.setAttribute("rowidDoc", rowidDoc);
			lRecordToSave.setAttribute("valori", getAttributiDinamiciDoc());
			lRecordToSave.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
		}
		return lRecordToSave;
	}

	public void salvaDati(final boolean flgIgnoreObblig, final ServiceCallback<Record> callback) {
		Record lRecordToSave = getRecordToSave(null);
		final Map<String, Object> attributiDinamiciEvent;
		final Map<String, String> tipiAttributiDinamiciEvent;
		final String esito = (!flgIgnoreObblig && attrEsito != null) ? attrEsito.getAttribute("valore") : null;
		if (!flgIgnoreObblig && attrEsito != null) {
			attributiDinamiciEvent = new HashMap<String, Object>();
			attributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("valore"));
			tipiAttributiDinamiciEvent = new HashMap<String, String>();
			tipiAttributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("tipo"));
			attrEsito = null;
		} else {
			attributiDinamiciEvent = null;
			tipiAttributiDinamiciEvent = null;
		}
		lRecordToSave.setAttribute("activityNameWF", activityName);
		lRecordToSave.setAttribute("esitoActivityWF", esito);
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");	
		saveDettDoc(lRecordToSave, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					salvaAttributiDinamiciDoc(flgIgnoreObblig, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							Record lRecordEvent = new Record();
							lRecordEvent.setAttribute("idProcess", idProcess);
							lRecordEvent.setAttribute("idEvento", idEvento);
							lRecordEvent.setAttribute("idTipoEvento", idTipoEvento);
							lRecordEvent.setAttribute("desEvento", dettaglioPraticaLayout.getDisplayNameEvento(nome));
							if (messaggio != null) {
								lRecordEvent.setAttribute("messaggio", messaggio);
							}
							lRecordEvent.setAttribute("valori", attributiDinamiciEvent);
							lRecordEvent.setAttribute("tipiValori", tipiAttributiDinamiciEvent);
							GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("CustomEventDatasource");
							if (flgIgnoreObblig) {
								lGWTRestService.addParam("flgIgnoreObblig", "1");
							} 
							lGWTRestService.addParam("skipSuccessMsg", "true");
							lGWTRestService.call(lRecordEvent, new ServiceCallback<Record>() {

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
					});
				} else {
					Layout.hideWaitPopup();
				}
			}
		});
	}

	public void saveDettDoc(Record record, final DSCallback callback) {
		// metto a null valori e tipiValori perchè sennò scatta la validazione
		record.setAttribute("valori", (Map) null);
		record.setAttribute("tipiValori", (Map) null);
		GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lProtocolloDataSource.addParam("isTaskDettUdGen", "true");
		lProtocolloDataSource.addParam("idProcess", idProcess);
		lProtocolloDataSource.addParam("taskName", activityName);
		if (isReadOnly()) {
			lProtocolloDataSource.addParam("idTaskCorrente", getIdTaskCorrente());
		}
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
		try {
			lProtocolloDataSource.updateData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						if (callback != null) {
							callback.execute(response, rawData, request);
						} else {
							Layout.hideWaitPopup();
						}
					} else {
						Layout.hideWaitPopup();
					}
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}

	@Override
	public void salvaDatiProvvisorio() {
		if (validateSenzaObbligatorieta()) {
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
	}

	@Override
	public void salvaDatiDefinitivo() {
		if (validate()) {
			final String nomeAttrCustomEsitoTask = recordEvento.getAttribute("nomeAttrCustomEsitoTask");
			if (nomeAttrCustomEsitoTask != null && !"".equals(nomeAttrCustomEsitoTask)) {
				GWTRestService<Record, Record> lAttributiDinamiciDatasource = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
				Record lAttributiDinamiciRecord = new Record();
				lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_PROCESS_HISTORY");
				lAttributiDinamiciRecord.setAttribute("rowId", rowId);
				lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoEvento);
				lAttributiDinamiciDatasource.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

					@Override
					public void execute(final Record object) {
						RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
						for (int i = 0; i < attributiAdd.getLength(); i++) {
							final Record attr = attributiAdd.get(i);
							if (attr.getAttribute("nome").equals(nomeAttrCustomEsitoTask)) {
								SelezionaEsitoTaskWindow selezionaEsitoTaskWindow = new SelezionaEsitoTaskWindow(attr, isReadOnly(), esitiTaskOk,
										new ServiceCallback<Record>() {

											@Override
											public void execute(Record object) {
												messaggio = object.getAttribute("messaggio");
												attrEsito = new Record(attr.toMap());
												attrEsito.setAttribute("valore", object.getAttribute(nomeAttrCustomEsitoTask));
												saveAndGoAlert();
											}
										});
								selezionaEsitoTaskWindow.show();
								break;
							}
						}
					}
				});
			} else {
				messaggio = null;
				attrEsito = null;
				saveAndGoAlert();
			}
		} else {
			messaggio = null;
			attrEsito = null;
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}

	public String getValidateErrorMessage() {
		return I18NUtil.getMessages().validateError_message();
	}

	public void saveAndGoAlert() {
		if (validate()) {
			if (alertConfermaSalvaDefinitivo != null && !"".equals(alertConfermaSalvaDefinitivo)) {
				SC.ask(alertConfermaSalvaDefinitivo, new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value) {
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
		if(isEsitoTaskOk(attrEsito)) {
			salvaDati(true, new ServiceCallback<Record>() {
				@Override
				public void execute(Record object) {
					idEvento = object.getAttribute("idEvento");
					Layout.hideWaitPopup();
					loadDettUd(new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if(hasActionUnioneFile()) {
								// nell'unione dei file se ho dei file firmati pades devo prendere la versione precedente (quella che usiamo per l'editor, e la convertiamo in pdf) se c'è, altrimenti quella corrente 
								// se non sono tutti i convertibili i file do errore nell'unione e blocco tutto
								unioneFileAndReturn();
							} else {
								getFileDaFirmare(hasActionTimbra(), new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Record lRecord = response.getData()[0];
											Record[] files = lRecord.getAttributeAsRecordArray("files");
											if (files != null && files.length > 0) {
												firmaFile(files, new ServiceCallback<Record>() {
													
													@Override
													public void execute(final Record recordAfterFirma) {
														Record[] files = new Record[0];
														if (recordAfterFirma.getAttributeAsRecord("fileFirmati") != null) {
															files = recordAfterFirma.getAttributeAsRecord("fileFirmati").getAttributeAsRecordArray("files");
														}
														invioMail(files, new BooleanCallback() {
															
															@Override
															public void execute(Boolean value) {														
																if(value) {
																	aggiornaFile(recordAfterFirma, new ServiceCallback<Record>() {
																		
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
															}
														});
													}
												});	
											} else if(hasActionTimbra() || hasActionFirma() || hasActionInvioMail()) {
												Layout.addMessage(new MessageBean("E' obbligatorio inserire almeno un file per procedere", "", MessageType.ERROR));										
											} else {
												salvaDati(false, new ServiceCallback<Record>() {
													
													@Override
													public void execute(Record object) {
														callbackSalvaDati(object);
													}
												});
											}
										}
									}
								});				
							}						
						}
					});					
				}
			});				
		} else {
			salvaDati(false, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					callbackSalvaDati(object);
				}
			});
		}
	}
	
	public void unioneFileAndReturn() {
		final Record lRecord = getRecordToSave(null);
		GWTRestService<Record, Record> lTaskDettUdGenDataSource = new GWTRestService<Record, Record>("TaskDettUdGenDataSource");
		lTaskDettUdGenDataSource.addParam("fileDaTimbrare", hasActionTimbra() ? "true" : "");
		lTaskDettUdGenDataSource.addParam("nomeFileUnione", recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFile") : null);
		lTaskDettUdGenDataSource.addParam("esitoTask", attrEsito != null ? attrEsito.getAttribute("valore") : null);		
		lTaskDettUdGenDataSource.executecustom("unioneFile", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordUnioneFile = response.getData()[0];
					previewFileUnioneWithFirmaAndCallback(recordUnioneFile, new ServiceCallback<Record>() {
						
						@Override
						public void execute(final Record recordUnioneFileAfterFirma) {							
							invioMail(recordUnioneFileAfterFirma, new BooleanCallback() {
								
								@Override
								public void execute(Boolean value) {									
									if(value) {
										final String uriFileFirmato = recordUnioneFileAfterFirma.getAttribute("uri");	
										final String nomeFileFirmato = recordUnioneFileAfterFirma.getAttribute("nomeFile");		
										final InfoFileRecord infoFileFirmato = InfoFileRecord.buildInfoFileString(JSON.encode(recordUnioneFileAfterFirma.getAttributeAsRecord("infoFile").getJsObj()));				
										aggiornaFilePrimario(uriFileFirmato, nomeFileFirmato, infoFileFirmato, new ServiceCallback<Record>() {
											
											@Override
											public void execute(Record record) {							
												salvaDati(false, new ServiceCallback<Record>() {

													@Override
													public void execute(Record object) {
														callbackSalvaDati(object);
													}
												});
											}
										});												
									}																				
								}
							});
						}
					});
				}
			}
		});
	}
	
	protected void aggiornaFilePrimario(String uri, String nomeFile, InfoFileRecord infoFile, final ServiceCallback<Record> callback) {				
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
			if (infoFile.isFirmato() && !infoFile.isFirmaValida()) {
				GWTRestDataSource.printMessage(new MessageBean("Il file primario presenta una firma non valida alla data odierna", "", MessageType.WARNING));
			}
			filePrimarioForm.markForRedraw();
			filePrimarioButtons.markForRedraw();
			manageChangedPrimario();					
			if(callback != null) {
				callback.execute(null);
			}
		}			
	}

	public void previewFileUnioneWithFirmaAndCallback(final Record record, final ServiceCallback<Record> callback) {		
		final String uriFileUnione = record.getAttribute("uri");	
		final String nomeFileUnione = record.getAttribute("nomeFile");		
		final InfoFileRecord infoFileUnione = InfoFileRecord.buildInfoFileString(JSON.encode(record.getAttributeAsRecord("infoFile").getJsObj()));				
		new PreviewWindowWithCallback(uriFileUnione, false, infoFileUnione, "FileToExtractBean",	nomeFileUnione, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordPreview) {
				
				if (hasActionFirma()) {
					// Leggo gli eventuali parametri per forzare il tipo d firma
					String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
					String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
					FirmaUtility.firmaMultipla(true, true, appletTipoFirmaAtti, hsmTipoFirmaAtti, uriFileUnione, nomeFileUnione, infoFileUnione, new FirmaCallback() {

						@Override
						public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord infoFileFirmato) {
							record.setAttribute("uri", uriFileFirmato);
							record.setAttribute("nomeFile", nomeFileFirmato);
							record.setAttribute("infoFile", infoFileFirmato);
							if (callback != null) {
								callback.execute(record);
							}
						}
					});						
				} else {
					if (callback != null) {
						callback.execute(record);
					}
				}
			}
		});		
	}
	
	public void getFileDaFirmare(DSCallback callback) {
		getFileDaFirmare(false, callback);
	}
	
	public void getFileDaFirmare(boolean fileDaTimbrare, DSCallback callback) {
		final Record lRecord = getRecordToSave(null);
		GWTRestService<Record, Record> lTaskDettUdGenDataSource = new GWTRestService<Record, Record>("TaskDettUdGenDataSource");
		lTaskDettUdGenDataSource.addParam("fileDaTimbrare", fileDaTimbrare ? "true" : "");		
		lTaskDettUdGenDataSource.executecustom("getFileDaFirmare", lRecord, callback);
	}
	
	protected void firmaFile(Record[] files, final ServiceCallback<Record> callback) {
		if(hasActionFirma()) {
			String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
			String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
			FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, files, new FirmaMultiplaCallback() {
	
				@Override
				public void execute(Map<String, Record> files, Record[] filesAndUd) {
					Record lRecord = new Record();
					lRecord.setAttribute("protocolloOriginale", getRecordToSave(null));
					Record lRecordFileFirmati = new Record();
					lRecordFileFirmati.setAttribute("files", files.values().toArray(new Record[] {}));
					lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
					if(callback != null) {
						callback.execute(lRecord);
					}					
				}
			});
		} else if (hasActionTimbra()) {
			Record lRecord = new Record();
			lRecord.setAttribute("protocolloOriginale", getRecordToSave(null));
			Record lRecordFileFirmati = new Record();
			lRecordFileFirmati.setAttribute("files", files);
			lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
			if(callback != null) {
				callback.execute(lRecord);
			}			
		}else {
			Record lRecord = new Record();
			lRecord.setAttribute("protocolloOriginale", getRecordToSave(null));
			Record lRecordFileFirmati = new Record();
			lRecordFileFirmati.setAttribute("files", files);
			lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
			if(callback != null) {
				callback.execute(lRecord);
			}
		}
	}
	
	protected void aggiornaFile(Record record, final ServiceCallback<Record> callback) {
		if(hasActionFirma()) {
			aggiornaFileFirmati(record,  callback);
		} else if (hasActionTimbra()) {
			aggiornaFileTimbrati(record, callback);
		} else if(callback != null) {
			callback.execute(null);			
		}		
	}

	protected void aggiornaFileFirmati(Record record, final ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TaskDettUdGenDataSource");
		lGwtRestService.addParam("idTaskCorrente", getIdTaskCorrente());
		lGwtRestService.executecustom("aggiornaFileFirmati", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					caricaDettaglio(null, lRecord);
					if(callback != null) {
						callback.execute(lRecord);
					}
				}
			}
		});
	}
	
	protected void aggiornaFileTimbrati(Record record, final ServiceCallback<Record> callback) {		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TaskDettUdGenDataSource");
		lGwtRestService.addParam("idTaskCorrente", getIdTaskCorrente());
		lGwtRestService.executecustom("aggiornaFile", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					caricaDettaglio(null, lRecord);					
					if(callback != null) {
						callback.execute(lRecord);
					}					
				}
			}
		});
	}
	
	protected void invioMail(final Record file, final BooleanCallback callback) {		
		Record[] files = new Record[1];
		files[0] = file;
		invioMail(files, callback);
	}
	
	protected void invioMail(final Record[] files, final BooleanCallback callback) {
		if(hasActionInvioMail()) {		
			if (files != null && files.length > 0) {
				DSCallback sendCallback = new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							if(callback != null) {
								callback.execute(true);
							}
						} else {
							if(callback != null) {
								callback.execute(false);
							}
						}
					}				
				};
				NuovoMessaggioWindow lNuovoMessaggioWindow = new NuovoMessaggioWindow("nuovo_messaggio","invioNuovoMessaggio", instance, sendCallback) {
					
					@Override
					public boolean isHideSalvaBozzaButton() {
						return true;
					}
					
					@Override
					public boolean getDefaultSaveSentEmail() {
						return true; // forzo il valore di default del check salvaInviati a true
					}
					
					@Override
					public Record getInitialRecordNuovoMessaggio() {
						Record lRecord = new Record();
						lRecord.setAttribute("protocolloOriginale", getRecordToSave(null));
						lRecord.setAttribute("oggetto", recordEvento != null ? recordEvento.getAttribute("invioPECSubject") : null);
						lRecord.setAttribute("bodyHtml", recordEvento != null ? recordEvento.getAttribute("invioPECBody") : null);
						lRecord.setAttribute("destinatari", recordEvento != null ? recordEvento.getAttribute("invioPECDestinatari") : null);
						lRecord.setAttribute("destinatariCC", recordEvento != null ? recordEvento.getAttribute("invioPECDestinatariCC") : null);
						lRecord.setAttribute("mittente", recordEvento != null ? recordEvento.getAttribute("invioPECIndirizzoMittente") : null);
						lRecord.setAttribute("idUD", idUd); // idUd da collegare alla mail
						RecordList attachList = new RecordList();
						for (int i = 0; i < files.length; i++) {
							Record attach = new Record();
							attach.setAttribute("fileNameAttach", files[i].getAttribute("nomeFile"));
							attach.setAttribute("infoFileAttach", files[i].getAttributeAsRecord("infoFile"));
							attach.setAttribute("uriAttach", files[i].getAttribute("uri"));
							attachList.add(attach);
						}
						lRecord.setAttribute("attach", attachList);
						return lRecord;
					};
				};	
			} else {
				if(callback != null) {
					callback.execute(false);
				}
			}
		} else {
			if(callback != null) {
				callback.execute(true);
			}
		}
	}
	
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

	public void caricaAttributiDinamiciDoc(final String nomeFlussoWF, final String processNameWF, final String activityName, final String idTipoDocumento,
			final String rowidDoc) {
		if (idTipoDocumento != null && !"".equals(idTipoDocumento)) {
			Record lRecordLoad = new Record();
			lRecordLoad.setAttribute("idTipoDocumento", idTipoDocumento);
			new GWTRestService<Record, Record>("LoadComboGruppiAttrCustomTipoDocDataSource").call(lRecordLoad, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					final boolean isReload = (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0);
					attributiAddDocTabs = (LinkedHashMap<String, String>) object.getAttributeAsMap("gruppiAttributiCustomTipoDoc");
					attributiAddDocLayouts = new HashMap<String, VLayout>();
					attributiAddDocDetails = new HashMap<String, AttributiDinamiciDetail>();
					if (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0) {
						GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
						lGwtRestService.addParam("flgNomeAttrConSuff", "true");
						lGwtRestService.addParam("nomeFlussoWF", nomeFlussoWF);
						lGwtRestService.addParam("processNameWF", processNameWF);
						lGwtRestService.addParam("activityNameWF", activityName);
						Record lAttributiDinamiciRecord = new Record();
						lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_DOCUMENTS+UD");
						lAttributiDinamiciRecord.setAttribute("rowId", rowidDoc);
						lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoDocumento);
						lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
								if (attributiAdd != null && !attributiAdd.isEmpty()) {
									boolean hasTabs = false;
									for (final String key : attributiAddDocTabs.keySet()) {
										RecordList attributiAddCategoria = new RecordList();
										for (int i = 0; i < attributiAdd.getLength(); i++) {
											Record attr = attributiAdd.get(i);
											if (attr.getAttribute("categoria") != null
													&& (attr.getAttribute("categoria").equalsIgnoreCase(key) || ("HEADER_" + attr.getAttribute("categoria"))
															.equalsIgnoreCase(key))) {
												attributiAddCategoria.add(attr);
											}
										}
										if (!attributiAddCategoria.isEmpty()) {
											if(key.equals("#HIDDEN")) {
												// Gli attributi che fanno parte di questo gruppo non li considero
											} else if (key.startsWith("HEADER_")) {
												AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
														.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
														.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null,
														tabSet, "HEADER");
												detail.setCanEdit(new Boolean(isEseguibile()));
												attributiAddDocDetails.put(key, detail);
												VLayout layoutFirstTab = (VLayout) tabSet.getTab(0).getPane();
												VLayout layout = (VLayout) layoutFirstTab.getMembers()[0];
												attributiAddDocLayouts.put(key, layout);
												int pos = 0;
												for (Canvas member : layout.getMembers()) {
													if (member instanceof HeaderDetailSection) {
														pos++;
													} else {
														break;
													}
												}
												for (DetailSection detailSection : attributiAddDocDetails.get(key).getDetailSections()) {
													if (isReload) {
														((DetailSection) layout.getMember(pos++)).setForms(detailSection.getForms());
													} else {
														layout.addMember(detailSection, pos++);
													}
												}
											} else {
												AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
														.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
														.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null,
														tabSet, key);
												detail.setCanEdit(new Boolean(isEseguibile()));
												attributiAddDocDetails.put(key, detail);
												VLayout layout = new VLayout();
												layout.setHeight100();
												layout.setWidth100();
												layout.setMembers(detail);
												attributiAddDocLayouts.put(key, layout);
												VLayout layoutTab = new VLayout();
												layoutTab.addMember(layout);
												if (tabSet.getTabWithID(key) != null) {
													tabSet.getTabWithID(key).setPane(layoutTab);
												} else {
													Tab tab = new Tab("<b>" + attributiAddDocTabs.get(key) + "</b>");
													tab.setAttribute("tabID", key);
													tab.setPrompt(attributiAddDocTabs.get(key));
													tab.setPane(layoutTab);
													tabSet.addTab(tab);
												}
												hasTabs = true;
											}
										}
									}
									if (hasTabs)
										setMembers(tabSet);
								}
							}
						});
						afterCaricaAttributiDinamiciDoc();
					}
				}
			});
		}
	}

	public void salvaAttributiDinamiciDoc(boolean flgIgnoreObblig, String rowidDoc, String activityNameWF, String esitoActivityWF,
			final ServiceCallback<Record> callback) {
		if (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0) {
			Record lRecordDoc = new Record();
			lRecordDoc.setAttribute("rowId", rowidDoc);
			lRecordDoc.setAttribute("valori", getAttributiDinamiciDoc());
			lRecordDoc.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
			lRecordDoc.setAttribute("activityNameWF", activityNameWF);
			lRecordDoc.setAttribute("esitoActivityWF", esitoActivityWF);
			GWTRestService<Record, Record> lGWTRestServiceDoc = new GWTRestService<Record, Record>("AttributiDinamiciDocDatasource");
			if (flgIgnoreObblig) {
				lGWTRestServiceDoc.addParam("flgIgnoreObblig", "1");
			}
			lGWTRestServiceDoc.call(lRecordDoc, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					if (callback != null) {
						callback.execute(object);
					}
				}
			});
		} else {
			if (callback != null) {
				callback.execute(new Record());
			}
		}
	}

	public Map<String, Object> getAttributiDinamiciDoc() {
		Map<String, Object> attributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (attributiDinamiciDoc == null) {
						attributiDinamiciDoc = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					attributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaValori(detailRecord));
				}
			}
		}
		return attributiDinamiciDoc;
	}

	public Map<String, String> getTipiAttributiDinamiciDoc() {
		Map<String, String> tipiAttributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (tipiAttributiDinamiciDoc == null) {
						tipiAttributiDinamiciDoc = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					tipiAttributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaTipiValori(detailRecord));
				}
			}
		}
		return tipiAttributiDinamiciDoc;
	}

	@Override
	public boolean showDetailToolStrip() {
		return false;
	}

	@Override
	public boolean showIterProcessoCollegatoButton() {
		return false;
	}

	@Override
	public boolean showModelliSelectItem() {
		return false;
	}

}
