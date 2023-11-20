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

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.iterAtti.MessaggioTaskWindow;
import it.eng.auriga.ui.module.layout.client.iterAtti.SelezionaEsitoTaskWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.NuovoMessaggioWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatoCanvas;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowWithCallback;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailAtti;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class PropostaAttoDetail extends ProtocollazioneDetailAtti implements PropostaAttoInterface {

	protected PropostaAttoDetail instance;

	protected Record recordEvento;

	protected String idProcess;
	protected String nomeFlussoWF;
	protected String processNameWF;
	protected String idUd;
	protected String idEvento;
	protected String idTipoEvento;
	protected String rowId;
	protected String activityName;
	protected String instanceId;
	protected String nome;
	protected String alertConfermaSalvaDefinitivo;

	protected Record attrEsito;
	protected String assegnatario;
	protected String messaggio;

	protected String codTabDefault;
	protected String idTipoTaskDoc;
	protected String nomeTipoTaskDoc;
	protected Boolean flgParteDispositivoTaskDoc;
	protected Boolean flgInvioPEC;

	protected DettaglioPraticaLayout dettaglioPraticaLayout;

	protected Set<String> esitiTaskOk;
	
	protected RecordList listaRecordModelli;
	protected HashMap<String, Record> controlliXEsitiTaskDoc;

	public PropostaAttoDetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento,
			DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita, getAttributAddDocTabs(lRecordEvento));

		instance = this;

		this.recordEvento = lRecordEvento;

		this.idProcess = idProcess;
		this.nomeFlussoWF = nomeFlussoWF;
		this.processNameWF = processNameWF;
		this.idUd = idUd;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.rowId = lRecordEvento != null ? lRecordEvento.getAttribute("rowId") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.instanceId = lRecordEvento != null ? lRecordEvento.getAttribute("instanceId") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;
		this.alertConfermaSalvaDefinitivo = lRecordEvento != null ? lRecordEvento.getAttribute("alertConfermaSalvaDefinitivo") : null;

		this.codTabDefault = lRecordEvento != null ? lRecordEvento.getAttribute("codTabDefault") : null;
		this.idTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoTaskDoc") : null;
		this.nomeTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("nomeTipoTaskDoc") : null;
		this.flgParteDispositivoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgParteDispositivoTaskDoc") : null;
		this.flgInvioPEC = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgInvioPEC") : null;
		
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;
		
		RecordList listaEsitiTaskOk = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("esitiTaskOk") : null;
		if(listaEsitiTaskOk != null && listaEsitiTaskOk.getLength() > 0) {
			esitiTaskOk = new HashSet<String>();
			for(int i = 0; i < listaEsitiTaskOk.getLength(); i++) {				
				Record esito = listaEsitiTaskOk.get(i);
				esitiTaskOk.add(esito.getAttribute("valore"));
			}			
		}

		this.listaRecordModelli = dettaglioPraticaLayout.getListaModelliAttivita(activityName);

		RecordList listaControlliXEsitiTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("controlliXEsitiTaskDoc") : null;
		if (listaControlliXEsitiTaskDoc != null && listaControlliXEsitiTaskDoc.getLength() > 0) {
			controlliXEsitiTaskDoc = new HashMap<String, Record>();
			for (int i = 0; i < listaControlliXEsitiTaskDoc.getLength(); i++) {
				Record recordControllo = listaControlliXEsitiTaskDoc.get(i);
				controlliXEsitiTaskDoc.put(recordControllo.getAttribute("esito"), recordControllo);
			}
		}
	}
	
	public boolean isEsitoTaskOk(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return (esito == null || esitiTaskOk == null || (esito != null && esitiTaskOk != null && esitiTaskOk.contains(esito)));		
	}

	@Override
	public String getMessaggioTab(String tabID) {
				
		RecordList attributiAddDocTabs = recordEvento != null ? recordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				if (tab.getAttribute("codice") != null && tabID.equals(tab.getAttribute("codice"))) {
					return tab.getAttribute("messaggioTab");
				}
			}
		}
		return null;
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

	public static LinkedHashMap<String, String> getAttributAddDocTabs(Record lRecordEvento) {
		LinkedHashMap<String, String> tabs = new LinkedHashMap<String, String>();
		// tabs.put("SCELTE_ITER", "Opzioni iter");
		RecordList attributiAddDocTabs = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				tabs.put(tab.getAttribute("codice"), tab.getAttribute("titolo"));
			}
		}
		return tabs;
	}

		/**
	 * <ul>
	 * <li>Carica il modello specificato</li>
	 * <li>inietta i valori</li>
	 * <li>genera la versione pdf</li>
	 * <li>se richiesto, il file viene firmato digitalmente</li>
	 * <li>viene aggiunto agli allegati</li>
	 * </ul>
	 * 
	 * @param callback
	 */
	public void compilazioneAutomaticaModelloPdf(Record recordModello, final ServiceCallback<Record> callback) {

		if (recordModello != null) {

			final String descrizione = recordModello.getAttribute("descrizione");
			final String nomeFileModello = recordModello.getAttribute("nomeFile") + ".pdf";
			final String idTipoModello = recordModello.getAttribute("idTipoDoc");
			final String nomeTipoModello = recordModello.getAttribute("nomeTipoDoc");
			final boolean flgDaFirmare = recordModello.getAttributeAsBoolean("flgDaFirmare") != null && recordModello.getAttributeAsBoolean("flgDaFirmare");
			final boolean flgParteDispositivo = recordModello.getAttributeAsBoolean("flgParteDispositivo") != null && recordModello.getAttributeAsBoolean("flgParteDispositivo");
			
			Record lRecordCompilaModello = new Record();
			lRecordCompilaModello.setAttribute("processId", idProcess);
			lRecordCompilaModello.setAttribute("idUd", idUd);
			lRecordCompilaModello.setAttribute("idModello", recordModello.getAttribute("idModello"));
			lRecordCompilaModello.setAttribute("nomeModello", recordModello.getAttribute("nomeModello"));
			lRecordCompilaModello.setAttribute("uriModello", recordModello.getAttribute("uri"));
			lRecordCompilaModello.setAttribute("tipoModello", recordModello.getAttribute("tipoModello"));
			lRecordCompilaModello.setAttribute("nomeFile", nomeFileModello);
			lRecordCompilaModello.setAttribute("valori", getAttributiDinamiciDoc());
			lRecordCompilaModello.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());

			GWTRestService<Record, Record> compilaModelloRestService = new GWTRestService<Record, Record>("CompilaModelloDatasource");

			compilaModelloRestService.call(lRecordCompilaModello, new ServiceCallback<Record>() {

				@Override
				public void execute(Record result) {

					final String documentUri = result.getAttribute("uri");
					final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));

					new PreviewWindowWithCallback(documentUri, false, infoFile, "FileToExtractBean",
							nomeFileModello, new ServiceCallback<Record>() {

								@Override
								public void execute(Record object) {
									
									if (flgDaFirmare) {
										// Leggo gli eventuali parametri per forzare il tipo d firma
										String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
										String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");
										FirmaUtility.firmaMultipla(true, false, appletTipoFirmaAtti, hsmTipoFirmaAtti, documentUri, nomeFileModello, infoFile, new FirmaCallback() {

											@Override
											public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
												aggiungiModelloAdAllegati(flgParteDispositivo, descrizione, idTipoModello, nomeTipoModello, uriFileFirmato, nomeFileFirmato, info,
														callback);
											}
										});
									} else {
										aggiungiModelloAdAllegati(flgParteDispositivo, descrizione, idTipoModello, nomeTipoModello, documentUri, nomeFileModello, infoFile, callback);
									}
								}
							});

				}
			});

		}
	}

	
	/**
	 * Aggiunge il modello in cui sono stati iniettati i dati alla lista degli allegati
	 * 
	 * @param uriFileAllegato
	 * @param nomeFileAllegato
	 * @param infoAllegato
	 * @param callback
	 */
	public int getPosModelloFromDescr(String descrizione, RecordList listaAllegati) {
		if (listaAllegati != null) {
			for (int i = 0; i < listaAllegati.getLength(); i++) {
				Record allegato = listaAllegati.get(i);
				if (allegato.getAttribute("descrizioneFileAllegato") != null && allegato.getAttribute("descrizioneFileAllegato").equalsIgnoreCase(descrizione)) {
					return i;
				}
			}
		}
		return -1;
	}

	public int getPosModelloFromTipo(String idTipoModello, RecordList listaAllegati) {
		if (listaAllegati != null) {
			for (int i = 0; i < listaAllegati.getLength(); i++) {
				Record allegato = listaAllegati.get(i);
				if (allegato.getAttribute("listaTipiFileAllegato") != null && allegato.getAttribute("listaTipiFileAllegato").equalsIgnoreCase(idTipoModello)) {
					return i;
				}
			}
		}
		return -1;
	}

	protected void aggiungiModelloAdAllegati(boolean flgParteDispositivo, String descrizioneFileAllegato, String idTipoModello, String nomeTipoModello, String uriFileAllegato,
			String nomeFileAllegato, InfoFileRecord infoAllegato, ServiceCallback<Record> callback) {

		if (fileAllegatiForm != null) {

			RecordList listaAllegati = fileAllegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");

			int posModello = getPosModelloFromTipo(idTipoModello, listaAllegati);
			
			Record lRecordModello = new Record();
			if (posModello != -1) {
				lRecordModello = listaAllegati.get(posModello);
			}
						
			lRecordModello.setAttribute("flgParteDispositivo", flgParteDispositivo);
			if(!flgParteDispositivo) {
				lRecordModello.setAttribute("flgNoPubblAllegato", true);
				lRecordModello.setAttribute("flgPubblicaSeparato", false);
				lRecordModello.setAttribute("flgDatiSensibili", false);
			}
			
			lRecordModello.setAttribute("nomeFileAllegato", nomeFileAllegato);
			lRecordModello.setAttribute("uriFileAllegato", uriFileAllegato);
			lRecordModello.setAttribute("descrizioneFileAllegato", descrizioneFileAllegato);

			lRecordModello.setAttribute("listaTipiFileAllegato", idTipoModello);
			lRecordModello.setAttribute("idTipoFileAllegato", idTipoModello);
			lRecordModello.setAttribute("descTipoFileAllegato", nomeTipoModello);

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

			Record lRecordForm = new Record();
			lRecordForm.setAttribute("listaAllegati", listaAllegati);
			fileAllegatiForm.setValues(lRecordForm.toMap());
			if(fileAllegatiItem != null && (fileAllegatiItem instanceof AllegatiItem)) {
				((AllegatiItem)fileAllegatiItem).resetCanvasChanged();
			}

			if (detailSectionAllegati != null) {
				detailSectionAllegati.openIfhasValue();
			}
		}
		if (callback != null) {
			callback.execute(new Record());
		}
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
	
	public boolean isAttivaModificaEsclusionePubblicazione() {
		boolean isAttivaModificaEsclusionePubblicazione = false;
		if (recordEvento != null && recordEvento.getAttribute("attivaModificaEsclusionePubblicazione") != null && 
				"true".equalsIgnoreCase(recordEvento.getAttribute("attivaModificaEsclusionePubblicazione"))) { 
			isAttivaModificaEsclusionePubblicazione = true;
		}
		return isAttivaModificaEsclusionePubblicazione;
	}
	
	public void readOnlyMode(boolean isAttivaModificaEsclusionePubblicazione) {
		readOnlyMode();
		if(isAttivaModificaEsclusionePubblicazione) {
			if(fileAllegatiItem != null && (fileAllegatiItem instanceof AllegatiItem)) {
				((AllegatiItem)fileAllegatiItem).attivaModificaEsclusionePubblicazioneMode();
			}
		}
	}
	
	@Override
	public Record getRecordEvento() {
		return recordEvento;
	}

	@Override
	public void loadDati() {
		loadDettUd(new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				caricaAttributiDinamiciDoc(nomeFlussoWF, processNameWF, activityName, tipoDocumento, rowidDoc);
				caricaAttributiDinamici();
				if (isEseguibile()) {
					if (isReadOnly()) {
						readOnlyMode(isAttivaModificaEsclusionePubblicazione());
					} else {
						editMode();
					}
				} else {
					viewMode();
				}
			}
		});		
	}
	
	public void loadDettUd(final ServiceCallback<Record> callback) {
		final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lProtocolloDataSource.addParam("isPropostaAtto", "true");
		lProtocolloDataSource.addParam("idProcess", idProcess);
		lProtocolloDataSource.addParam("taskName", activityName);
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		lProtocolloDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					rowidDoc = lRecord.getAttribute("rowidDoc");
					tipoDocumento = lRecord.getAttribute("tipoDocumento");
					if (isEseguibile() && !isReadOnly()) {
						if(listaRecordModelli != null && listaRecordModelli.getLength() > 0) {
							RecordList listaAllegati = lRecord.getAttributeAsRecordList("listaAllegati");
							for (int i = 0; i < listaRecordModelli.getLength(); i++) {
								final String idTipoModello = listaRecordModelli.get(i).getAttribute("idTipoDoc");
								int posModello = getPosModelloFromTipo(idTipoModello, listaAllegati);
								if (posModello != -1) {
									listaAllegati.removeAt(posModello);
								}
							}
							lRecord.setAttribute("listaAllegati", listaAllegati);
						}
					}
					modificaRecordToLoad(lRecord);
					caricaDettaglio(null, lRecord);
					if(callback != null) {
						callback.execute(lRecord);
					}	
				}
			}
		});		
	}
	
	public void modificaRecordToLoad(Record record) {

	}

	public void caricaAttributiDinamici() {

	}

	@Override
	public String getIdTaskCorrente() {
		return nome.substring(0, nome.indexOf("|*|"));
	}

	@Override
	public HashSet<String> getTipiModelliAttiInAllegati() {
		return dettaglioPraticaLayout != null ? dettaglioPraticaLayout.getTipiModelliAtti() : null;
	}

	public void salvaDati(final boolean flgIgnoreObblig, final ServiceCallback<Record> callback) {
		Record lRecordToSave = getRecordToSave(null);

		final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lProtocolloDataSource.addParam("isPropostaAtto", "true");		
		lProtocolloDataSource.addParam("idProcess", idProcess);
		lProtocolloDataSource.addParam("taskName", activityName);
		if (assegnatario != null) {
			lProtocolloDataSource.addParam("assegnatario", assegnatario);
			assegnatario = null;
		}
		if (isReadOnly()) {
			lProtocolloDataSource.addParam("idTaskCorrente", getIdTaskCorrente());
		}

		final Map<String, Object> attributiDinamiciEvent;
		final Map<String, String> tipiAttributiDinamiciEvent;

		final String esito = (!flgIgnoreObblig && attrEsito != null) ? attrEsito.getAttribute("valore") : null;
		if (!flgIgnoreObblig && attrEsito != null) {
			lProtocolloDataSource.addParam("nomeAttrCustomEsito", attrEsito.getAttribute("nome"));
			lProtocolloDataSource.addParam("valoreAttrCustomEsito", attrEsito.getAttribute("valore"));
			attributiDinamiciEvent = new HashMap<String, Object>();
			attributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("valore"));
			tipiAttributiDinamiciEvent = new HashMap<String, String>();
			tipiAttributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("tipo"));
			attrEsito = null;
		} else {
			attributiDinamiciEvent = null;
			tipiAttributiDinamiciEvent = null;
		}

		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
		
		lProtocolloDataSource.updateData(lRecordToSave, new DSCallback() {

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
							lRecordEvent.setAttribute("idUd", idUd);
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
				}
			}
		});
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
					if(flgParteDispositivoTaskDoc != null && flgParteDispositivoTaskDoc) {
						lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", true);
					}
				}			
			}
		}
	}
	}
	
	@Override
	public void setInitialValues() {

		super.setInitialValues();	
		
		if (idTipoTaskDoc != null && !"".equals(idTipoTaskDoc) && nomeTipoTaskDoc != null && !"".equals(nomeTipoTaskDoc)) {
			if (detailSectionAllegati != null) {
				detailSectionAllegati.open();
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
		boolean valid = super.customValidate(); // perche estendo ProtocollazioneDetailAtti
		if (idTipoTaskDoc != null && !"".equals(idTipoTaskDoc) && nomeTipoTaskDoc != null && !"".equals(nomeTipoTaskDoc)) {
			String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
			Record recordControllo = esito != null && !"".equals(esito) ? controlliXEsitiTaskDoc.get(esito) : null;
			if (attrEsito != null && recordControllo == null) {
			 	final String label = attrEsito.getAttribute("label") != null ? attrEsito.getAttribute("label").toLowerCase() : null;
				String esitoCompleto = label + " " + esito;
				recordControllo = esitoCompleto != null && !"".equals(esitoCompleto) ? controlliXEsitiTaskDoc.get(esitoCompleto) : null;
			}
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
				if(flgParteDispositivoTaskDoc != null && flgParteDispositivoTaskDoc) {
					lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", true);
				}
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
			if (detailSectionAllegati != null) {
				detailSectionAllegati.open();
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
								SelezionaEsitoTaskWindow selezionaEsitoTaskWindow = new SelezionaEsitoTaskWindow(attr, true, esitiTaskOk,
									new ServiceCallback<Record>() {

										@Override
										public void execute(Record object) {
											
											messaggio = object.getAttribute("messaggio");
											attrEsito = new Record(attr.toMap());
											attrEsito.setAttribute("valore", object.getAttribute(nomeAttrCustomEsitoTask));
											assegnatario = object.getAttribute("assegnatario");
											saveAndGoAlert();
										}
									}
								);
								selezionaEsitoTaskWindow.show();
								break;
							}
						}
					}
				});
			} else if (isReadOnly()) {
				messaggio = null;
				attrEsito = null;
				assegnatario = null;
				MessaggioTaskWindow messaggioTaskWindow = new MessaggioTaskWindow(null, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						
						messaggio = object.getAttribute("messaggio");
						saveAndGoAlert();
					}
				});
				messaggioTaskWindow.show();
			} else {
				messaggio = null;
				attrEsito = null;
				assegnatario = null;
				saveAndGoAlert();
			}
		} else {
			messaggio = null;
			attrEsito = null;
			assegnatario = null;
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
	
	public Record getRecordModelloXEsito(String esito) {
		Record recordModello = null;		
		if (listaRecordModelli != null && listaRecordModelli.getLength() > 0) {			
			for (int i = 0; i < listaRecordModelli.getLength(); i++) {
				String listaEsitiXGenModello = listaRecordModelli.get(i).getAttribute("esitiXGenModello");					
				if (listaEsitiXGenModello == null || "".equals(listaEsitiXGenModello)) {
					recordModello = listaRecordModelli.get(i);
					break;						
				} 				
			}
			if(esito != null) {	
				for (int i = 0; i < listaRecordModelli.getLength(); i++) {
					String listaEsitiXGenModello = listaRecordModelli.get(i).getAttribute("esitiXGenModello");							
					if (listaEsitiXGenModello != null && !"".equals(listaEsitiXGenModello)) {
						for (String esitoXGenModello : new StringSplitterClient(listaEsitiXGenModello, "|*|").getTokens()) {
							if (esito.equalsIgnoreCase(esitoXGenModello)) {
								recordModello = listaRecordModelli.get(i);
								break;
							}
						} 
					}
				}			
			}
		}
		return recordModello;
	}

	public void saveAndGo() {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		final Record recordModello = getRecordModelloXEsito(esito);		
		if (recordModello != null) {
			salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					
					saveAndGoWithModello(recordModello, new ServiceCallback<Record>() {

						@Override
						public void execute(Record recordModello) {
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
		} else {
			salvaDati(false, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					callbackSalvaDati(object);
				}
			});
		}
	}
	
	public void saveAndGoWithModello(Record recordModello, ServiceCallback<Record> callback) {
		compilazioneAutomaticaModelloPdf(recordModello, callback);
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
	
	public boolean hasActionInvioMail() {
		return flgInvioPEC != null && flgInvioPEC;
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

	@Override
	public boolean showDetailToolStrip() {
		return false;
	}

	@Override
	public boolean showIterProcessoCollegatoButton() {
		return false;
	}

	@Override
	public boolean showDesUOProtocolloItem() {
		return false;
	}
	
	@Override
	public String getIdProcessTask() {
		return idProcess;
	}
	
	@Override
	public String getIdUd() {
		return idUd;
	}

}
