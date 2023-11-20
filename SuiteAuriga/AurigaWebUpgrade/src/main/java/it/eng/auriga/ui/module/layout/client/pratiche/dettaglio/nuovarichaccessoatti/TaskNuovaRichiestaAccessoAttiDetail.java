/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.iterAtti.SelezionaEsitoTaskWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.NuovoMessaggioWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskFlussoInterface;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatoCanvas;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowWithCallback;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class TaskNuovaRichiestaAccessoAttiDetail extends NuovaRichiestaAccessoAttiDetail implements TaskFlussoInterface {
	
	// TODO FARE COME IN PROPOSTA_ATTO 1 E FIRMA_PROPOSTA_ATTO 1 (AOSTA) DOVE IN ALCUNI TASK GENERO UN DOCUMENTO, LO FIRMO O LO RITORNO NELLO STESSO TASK IN REVISIONE
	// LISTA DI FILE DA FIRMARE ME LO DA LA VALE
	// NO UNIONE FILE E NO PUBBLICAZIONE AL MOMENTO
	// LISTA ALLEGATI IN REALTA E' UNA LISTA DI DOCUMENTI (UD) DEL FASCICOLO

	protected TaskNuovaRichiestaAccessoAttiDetail instance;
	
	protected Record recordEvento;

	protected String idProcess;
	protected String nomeFlussoWF;
	protected String processNameWF;
	protected String idTipoEvento;
	protected String idEvento;
	protected String rowId;
	protected String activityName;
	protected String instanceId;
	protected String nome;	
	protected String alertConfermaSalvaDefinitivo;

	protected String idUd;
	protected String idFolder;
	protected String codTabDefault;
	protected String idTipoTaskDoc;
	protected String nomeTipoTaskDoc;
	protected Boolean flgParteDispositivoTaskDoc;
	
	protected String idModAssDocTask;
	protected String tipoModAssDocTask;
	protected String displayFilenameModAssDocTask;
	
	protected Boolean flgFirmaFile;
	protected Boolean flgProtocolla;	
	protected String tipoEventoSUE;
	protected Boolean flgInvioNotEmail;
		
	protected HashMap<String, String> mappaWarningMsgXEsitoTask;
	
	protected String esitoTaskDaPreimpostare;
	protected String msgTaskDaPreimpostare;
	
	protected DettaglioPraticaLayout dettaglioPraticaLayout;
	
	protected RecordList listaRecordModelli;
	
	protected Set<String> esitiTaskOk;	
	protected HashMap<String, Record> controlliXEsitiTaskDoc;
	protected HashSet<String> valoriEsito;
	
	protected Set<String> esitiTaskAzioni;

	protected HashSet<String> attributiAddDocTabsDatiStorici;

	protected Record attrEsito;
	protected Record attrEsitoNotEmail;
	protected String messaggio;
	
	public TaskNuovaRichiestaAccessoAttiDetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita, getAttributAddDocTabs(lRecordEvento));

		instance = this;

		this.recordEvento = lRecordEvento;

		this.idProcess = idProcess;
		this.nomeFlussoWF = nomeFlussoWF;
		this.processNameWF = processNameWF;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.rowId = lRecordEvento != null ? lRecordEvento.getAttribute("rowId") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;
		this.instanceId = lRecordEvento != null ? lRecordEvento.getAttribute("instanceId") : null;
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;
		this.alertConfermaSalvaDefinitivo = lRecordEvento != null ? lRecordEvento.getAttribute("alertConfermaSalvaDefinitivo") : null;

		this.idUd = idUd;		
		this.idFolder = lRecordEvento != null ? lRecordEvento.getAttribute("idFolder") : null;
		this.codTabDefault = lRecordEvento != null ? lRecordEvento.getAttribute("codTabDefault") : null;
		this.idTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoTaskDoc") : null;
		this.nomeTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("nomeTipoTaskDoc") : null;
		this.flgParteDispositivoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgParteDispositivoTaskDoc") : null;
		
		this.idModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("idModAssDocTask") : null;		
		this.tipoModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAssDocTask") : null;		
		this.displayFilenameModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("displayFilenameModAssDocTask") : null;
		
		this.flgFirmaFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgFirmaFile") : null;
		this.flgProtocolla = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgProtocolla") : null;	
		this.tipoEventoSUE = lRecordEvento != null ? lRecordEvento.getAttribute("eventoSUETipo") : null;
		this.flgInvioNotEmail = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgInvioNotEmail") : null;
		
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;
		
		this.listaRecordModelli = dettaglioPraticaLayout.getListaModelliAttivita(activityName);

		RecordList listaEsitiTaskOk = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("esitiTaskOk") : null;
		if(listaEsitiTaskOk != null && listaEsitiTaskOk.getLength() > 0) {
			esitiTaskOk = new HashSet<String>();
			for(int i = 0; i < listaEsitiTaskOk.getLength(); i++) {				
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
		
		RecordList listaValoriEsito = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("valoriEsito") : null;
		if (listaValoriEsito != null && listaValoriEsito.getLength() > 0) {
			valoriEsito = new HashSet<String>();
			for (int i = 0; i < listaValoriEsito.getLength(); i++) {
				valoriEsito.add(listaValoriEsito.get(i).getAttribute("valore"));
			}
		}
		
		RecordList listaEsitiTaskAzioni = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("esitiTaskAzioni") : null;
		if(listaEsitiTaskAzioni != null && listaEsitiTaskAzioni.getLength() > 0) {
			esitiTaskAzioni = new HashSet<String>();
			for(int i = 0; i < listaEsitiTaskAzioni.getLength(); i++) {				
				Record esito = listaEsitiTaskAzioni.get(i);
				esitiTaskAzioni.add(esito.getAttribute("valore"));
			}			
		}
		
		RecordList listaWarningMsgXEsitoTask = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("warningMsgXEsitoTask") : null;
		if(listaWarningMsgXEsitoTask != null && listaWarningMsgXEsitoTask.getLength() > 0) {
			mappaWarningMsgXEsitoTask = new HashMap<String, String>();
			for(int i = 0; i < listaWarningMsgXEsitoTask.getLength(); i++) {				
				Record warningMsgXEsito = listaWarningMsgXEsitoTask.get(i);
				mappaWarningMsgXEsitoTask.put(warningMsgXEsito.getAttribute("esito"), warningMsgXEsito.getAttribute("warningMsg"));
			}			
		}
				
		this.esitoTaskDaPreimpostare = lRecordEvento != null ? lRecordEvento.getAttribute("esitoTaskDaPreimpostare") : null;		
		this.msgTaskDaPreimpostare = lRecordEvento != null ? lRecordEvento.getAttribute("msgTaskDaPreimpostare") : null;		
		
		// lista dei tab di attributi dinamici che gestiscono i dati storici
		RecordList attributiAddDocTabs = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			attributiAddDocTabsDatiStorici = new HashSet<String>();
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				if (tab.getAttribute("flgDatiStorici") != null && "1".equals(tab.getAttribute("flgDatiStorici"))) {
					attributiAddDocTabsDatiStorici.add(tab.getAttribute("codice"));
				}
			}
		}
		
		build();
	}
	
	@Override
	public boolean skipSuperBuild() {
		return true; // evito di fare la build nel costruttore della superclasse, in modo da farla poi alla fine, dopo aver inizializzato recordEvento e le altre variabili che mi servono nella build
	}
	
	@Override
	public boolean showDetailToolStrip() {
		return false;
	}
		
	public static LinkedHashMap<String, String> getAttributAddDocTabs(Record lRecordEvento) {
		
		LinkedHashMap<String, String> tabs = new LinkedHashMap<String, String>();
		RecordList attributiAddDocTabs = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				tabs.put(tab.getAttribute("codice"), tab.getAttribute("titolo"));
			}
		}
		return tabs;
	}
	
	public HashSet<String> getAttributiAddDocTabsDatiStorici() {
		return attributiAddDocTabsDatiStorici;
	}
	
	public void visualizzaDatiStorici() {

		if (attributiAddDocTabsDatiStorici != null && attributiAddDocTabsDatiStorici.size() > 0) {

			final TabSet tabSetDatiStorici = new TabSet();

			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
			// lGwtRestService.addParam("suffisso", "_CMMI");
			lGwtRestService.addParam("nomeFlussoWF", nomeFlussoWF);
			lGwtRestService.addParam("processNameWF", processNameWF);
			lGwtRestService.addParam("activityNameWF", activityName);
			lGwtRestService.addParam("flgDatiStorici", "true");
			Record lAttributiDinamiciRecord = new Record();
			lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_DOCUMENTS");
			lAttributiDinamiciRecord.setAttribute("rowId", rowidDoc);
			lAttributiDinamiciRecord.setAttribute("tipoEntita", tipoDocumento);
			lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
					if (attributiAdd != null && !attributiAdd.isEmpty()) {
						for (final String key : attributiAddDocTabsDatiStorici) {
							RecordList attributiAddCategoria = new RecordList();
							for (int i = 0; i < attributiAdd.getLength(); i++) {
								Record attr = attributiAdd.get(i);
								if (attr.getAttribute("categoria") != null && attr.getAttribute("categoria").equalsIgnoreCase(key)) {
									attributiAddCategoria.add(attr);
								}
							}
							if (!attributiAddCategoria.isEmpty()) {
								AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
										.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
										.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null, null, null);
								detail.setCanEdit(false);
								String messaggioTabDatiStorici = getMessaggioTabDatiStorici(key);
								if (messaggioTabDatiStorici != null && !"".equals(messaggioTabDatiStorici)) {
									Label labelMessaggioTabDatiStorici = new Label(messaggioTabDatiStorici);
									labelMessaggioTabDatiStorici.setAlign(Alignment.LEFT);
									labelMessaggioTabDatiStorici.setWidth100();
									labelMessaggioTabDatiStorici.setHeight(2);
									labelMessaggioTabDatiStorici.setPadding(5);
									detail.addMember(labelMessaggioTabDatiStorici, 0);
								}

								VLayout layout = new VLayout();
								layout.setHeight100();
								layout.setWidth100();
								layout.setMembers(detail);

								VLayout layoutTab = new VLayout();
								layoutTab.addMember(layout);

								Tab tab = new Tab("<b>" + attributiAddDocTabs.get(key) + "</b>");
								tab.setPrompt(attributiAddDocTabs.get(key));
								tab.setPane(layoutTab);

								tabSetDatiStorici.addTab(tab);
							}
						}
						AurigaLayout.addModalWindow("datiStorici", "Dati storici", "protocollazione/variazioni.png", tabSetDatiStorici);
					}
				}
			});
		}
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

	public String getMessaggioTabDatiStorici(String tabID) {
		
		RecordList attributiAddDocTabs = recordEvento != null ? recordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				if (tab.getAttribute("codice") != null && tabID.equals(tab.getAttribute("codice"))) {
					return tab.getAttribute("messaggioTabDatiStorici");
				}
			}
		}
		return null;
	}
	
	@Override
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
		afterShow();
	}
	
	public boolean hasActionFirma() {
		return flgFirmaFile != null && flgFirmaFile;
	}
	
	public boolean hasActionProtocolla() {
		return flgProtocolla != null && flgProtocolla;
	}
	
	public boolean hasActionInvioNotEmail() {
		return flgInvioNotEmail != null && flgInvioNotEmail;
	}
		
	public boolean isEsitoTaskOk(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return (esito == null || esitiTaskOk == null || (esito != null && esitiTaskOk != null && esitiTaskOk.contains(esito)));		
	}
	
	public boolean isEsitoTaskAzioni(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return (esito == null || esitiTaskAzioni == null || (esito != null && esitiTaskAzioni != null && esitiTaskAzioni.contains(esito)));		
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
	
	protected String getIdUd() {
		return idUd;
	}
	
	protected String getIdFolder() {
		return idFolder;
	}
	
	public String getIdProcessTask() {
		return idProcess;
	}
	
	public String getIdTaskCorrente() {
		return nome.substring(0, nome.indexOf("|*|"));
	}
	
	public HashSet<String> getTipiModelliAttiInDocumentiIstruttoria() {
		return dettaglioPraticaLayout != null ? dettaglioPraticaLayout.getTipiModelliAtti() : null;
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

	public void afterShow() {
		
	}

	public void readOnlyMode() {
		viewMode();		
		if(listaDocumentiIstruttoriaItem != null) {
			listaDocumentiIstruttoriaItem.readOnlyMode();
		}
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

	public Map<String, Object> getAttributiDinamici() {
		return new HashMap<String, Object>();
	}

	public Map<String, String> getTipiAttributiDinamici() {
		return new HashMap<String, String>();
	}
	
	@Override
	public Record getRecordEvento() {
		return recordEvento;
	}
	
	@Override
	public void loadDati() {		
		loadDettRichAccessoAtti(new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				caricaAttributiDinamiciDoc(nomeFlussoWF, processNameWF, activityName, tipoDocumento, rowidDoc);					
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
	
	public void loadDettRichAccessoAtti(final ServiceCallback<Record> callback) {
		final GWTRestDataSource lNuovaRichiestaAccessoAttiDatasource = new GWTRestDataSource("NuovaRichiestaAccessoAttiDatasource");
		lNuovaRichiestaAccessoAttiDatasource.addParam("idProcess", idProcess);
		lNuovaRichiestaAccessoAttiDatasource.addParam("taskName", activityName);
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		lNuovaRichiestaAccessoAttiDatasource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record lRecord = response.getData()[0];
					recordFromLoadDett = new Record(lRecord.getJsObj());
					rowidDoc = lRecord.getAttribute("rowidDoc");
					tipoDocumento = lRecord.getAttribute("tipoDocumento");
//					if (isEseguibile() && !isReadOnly()) {
//						if(listaRecordModelli != null && listaRecordModelli.getLength() > 0) {
//							RecordList listaDocumentiIstruttoria = lRecord.getAttributeAsRecordList("listaDocumentiIstruttoria");
//							for (int i = 0; i < listaRecordModelli.getLength(); i++) {
//								final String idTipoModello = listaRecordModelli.get(i).getAttribute("idTipoDoc");
//								int posModello = getPosModelloFromTipo(idTipoModello, listaDocumentiIstruttoria);
//								if (posModello != -1) {
//									String estremiProtUd = listaDocumentiIstruttoria.get(posModello).getAttribute("estremiProtUd");
//									boolean isUdProtocollata = estremiProtUd != null && !"".equals(estremiProtUd);
//									if(!isUdProtocollata) {
//										listaDocumentiIstruttoria.removeAt(posModello);
//									}
//								}
//							}
//							lRecord.setAttribute("listaDocumentiIstruttoria", listaDocumentiIstruttoria);
//						}
//					}
					editRecord(lRecord);
					// IMPORTANTE: quando ricarico i dati da DB devo finire la renderizzazione a maschera prima di chiamare la getRecordToSave(),
					// altrimenti l'html dei CKEditor risulta indentato male e questo crea problemi durante l'iniezione nel modello
					//TODO DA PORTARE ANCHE NELLE ALTRE MASCHERE DEI TASK		
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {		
							if(callback != null) {
								callback.execute(lRecord);
							}
						}
					});
				}
			}
		});
	}
	
	public void salvaDati(boolean flgIgnoreObblig, ServiceCallback<Record> callback) {
		salvaDati(null, flgIgnoreObblig, null, callback);
	}
	
	public void salvaDati(Record recordToSave, boolean flgIgnoreObblig, ServiceCallback<Record> callback) {
		salvaDati(recordToSave, flgIgnoreObblig, null, callback);
	}
	
	public void salvaDati(boolean flgIgnoreObblig, Map<String, String> otherExtraparam, ServiceCallback<Record> callback) {
		salvaDati(null, flgIgnoreObblig, otherExtraparam, callback);	
	}
	
	public void salvaDati(final Record recordToSave, final boolean flgIgnoreObblig, final Map<String, String> otherExtraparam, final ServiceCallback<Record> callback) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {						
				final Record lRecordToSave = recordToSave != null ? recordToSave : getRecordToSave();
				final GWTRestDataSource lNuovaRichiestaAccessoAttiDatasource = new GWTRestDataSource("NuovaRichiestaAccessoAttiDatasource");
				lNuovaRichiestaAccessoAttiDatasource.setTimeout(Integer.MAX_VALUE); // setto al massimo il timeout in salvataggio 
				if(otherExtraparam != null) {
					for(String key : otherExtraparam.keySet()) {
						lNuovaRichiestaAccessoAttiDatasource.addParam(key, otherExtraparam.get(key));
					}
				}
				lNuovaRichiestaAccessoAttiDatasource.addParam("idProcess", idProcess);
				lNuovaRichiestaAccessoAttiDatasource.addParam("taskName", activityName);
				lNuovaRichiestaAccessoAttiDatasource.addParam("esitoTask", attrEsito != null ? attrEsito.getAttribute("valore") : null);
				lNuovaRichiestaAccessoAttiDatasource.addParam("msgEsitoTask", messaggio);
				if (isReadOnly()) {
					lNuovaRichiestaAccessoAttiDatasource.addParam("idTaskCorrente", getIdTaskCorrente());
				}
				final Map<String, Object> attributiDinamiciEvent = getAttributiDinamici();
				final Map<String, String> tipiAttributiDinamiciEvent = getTipiAttributiDinamici();
				final String esito = (!flgIgnoreObblig && attrEsito != null) ? attrEsito.getAttribute("valore") : null;
				if (!flgIgnoreObblig) { 
					lNuovaRichiestaAccessoAttiDatasource.addParam("flgSalvataggioDefinitivoPreCompleteTask", "true");
					if(attrEsito != null) {
						lNuovaRichiestaAccessoAttiDatasource.addParam("nomeAttrCustomEsito", attrEsito.getAttribute("nome"));
						lNuovaRichiestaAccessoAttiDatasource.addParam("valoreAttrCustomEsito", attrEsito.getAttribute("valore"));
						attributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("valore"));
						tipiAttributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("tipo"));			
						attrEsitoNotEmail = new Record(attrEsito.toMap());
						attrEsito = null;
					}
				}
				Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());	
				lNuovaRichiestaAccessoAttiDatasource.updateData(lRecordToSave, new DSCallback() {

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
		});
	}

	@Override
	public boolean customValidate() {
		
		boolean valid = super.customValidate(); // perche estendo RichiestaAccessoAttiDetail
		
		if (idTipoTaskDoc != null && !"".equals(idTipoTaskDoc) && nomeTipoTaskDoc != null && !"".equals(nomeTipoTaskDoc)) {
			final String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
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
			
			if(listaDocumentiIstruttoriaItem != null) {
				AllegatoCanvas lAllegatoCanvas = listaDocumentiIstruttoriaItem.getAllegatoCanvasFromTipo(idTipoTaskDoc);
				if (flgObbligatorio && lAllegatoCanvas == null) {
					lAllegatoCanvas = (AllegatoCanvas) listaDocumentiIstruttoriaItem.onClickNewButton();
					lAllegatoCanvas.setFixedTipoFileAllegato(idTipoTaskDoc, nomeTipoTaskDoc);
					if(flgParteDispositivoTaskDoc != null && flgParteDispositivoTaskDoc) {
						lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", true);
					}				
				}				
				if (lAllegatoCanvas != null) {
					lAllegatoCanvas.setForceToShowGeneraDaModelloForTipoTaskDoc(true);
					lAllegatoCanvas.redrawButtons();	
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

//				if (documentiIstruttoriaSection != null) {
//					documentiIstruttoriaSection.open();
//				}

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
	public void salvaDatiProvvisorio() {
		
		if (validateSenzaObbligatorieta()) {
			Map<String, String> otherExtraparam = new HashMap<String, String>();									
			otherExtraparam.put("flgSalvataggioProvvisorioInBozza", "true");			
			salvaDati(true, otherExtraparam, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

						@Override
						public void execute(Record record) {
							Layout.hideWaitPopup();
							// TODO: qui dovrei ricaricare i tab degli attributi dinamici per vedere le eventuali variazioni
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
								SelezionaEsitoTaskWindow selezionaEsitoTaskWindow = new SelezionaEsitoTaskWindow(attr, true, esitiTaskOk, valoriEsito, mappaWarningMsgXEsitoTask, esitoTaskDaPreimpostare, msgTaskDaPreimpostare,
									new ServiceCallback<Record>() {

										@Override
										public void execute(Record object) {												
											messaggio = object.getAttribute("messaggio");
											attrEsito = new Record(attr.toMap());
											attrEsito.setAttribute("valore", object.getAttribute(nomeAttrCustomEsitoTask));
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
						if (value != null && value) {
							saveAndGo();
						} else {
							messaggio = null;
							attrEsito = null;	
						}
					}
				});
			} else {
				saveAndGo();
			}
		} else {
			messaggio = null;
			attrEsito = null;
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
		
	@Override
	public Record getRecordToSave() {
		Record lRecordToSave = super.getRecordToSave();
		lRecordToSave.setAttribute("idFolder", getIdFolder());
		lRecordToSave.setAttribute("idProcess", getIdProcessTask());
		lRecordToSave.setAttribute("idModello", idModAssDocTask != null ? idModAssDocTask : "");
		lRecordToSave.setAttribute("tipoModello", tipoModAssDocTask != null ? tipoModAssDocTask : "");
		lRecordToSave.setAttribute("displayFilenameModello", displayFilenameModAssDocTask != null ? displayFilenameModAssDocTask : "");
		return lRecordToSave;
	}
	     
	public void saveAndGo() {
		Map<String, String> otherExtraparam = new HashMap<String, String>();									
		otherExtraparam.put("flgSalvataggioProvvisorioPreCompleteTask", "true");
		salvaDati(true, otherExtraparam, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				idEvento = object.getAttribute("idEvento");
				Layout.hideWaitPopup();
				loadDettRichAccessoAtti(new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record dett) {
						final String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
						final Record recordModello = getRecordModelloXEsito(esito);
						firma(new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());	
								protocolla(null, new ServiceCallback<Record>() {
									
									@Override
									public void execute(Record object) {
										eventoSUE(null, new ServiceCallback<Record>() {

											@Override
											public void execute(Record object) {						
												if (recordModello != null) {
													salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

														@Override
														public void execute(Record object) {					
															saveAndGoWithModello(recordModello, esito, new ServiceCallback<Record>() {

																@Override
																public void execute(final Record recordModello) {	
																	Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
																	protocolla(recordModello, new ServiceCallback<Record>() {
																		
																		@Override
																		public void execute(Record object) {
																			eventoSUE(recordModello, new ServiceCallback<Record>() {

																				@Override
																				public void execute(Record object) {						
																					salvaDati(false, new ServiceCallback<Record>() {
																						
																						@Override
																						public void execute(Record object) {
																							callbackSalvaDati(object);
																						}
																					});
																				};							
																			});		
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
											};							
										});		
									}
								});										
							}
						});			
					}
				});
			}
		});					
	}
		
	public void firma(final ServiceCallback<Record> callback) {
		boolean flgDocPerAzionePresente = (recordEvento.getAttribute("idDocAzioni") != null && !"".equals(recordEvento.getAttribute("idDocAzioni")));
		if(flgDocPerAzionePresente && hasActionFirma() && isEsitoTaskAzioni(attrEsito)) {
			RecordList listaDocumentiIstruttoria = documentiIstruttoriaForm.getValuesAsRecord().getAttributeAsRecordList("listaDocumentiIstruttoria");
			String idDocDaFirmare = recordEvento != null ? recordEvento.getAttribute("idDocAzioni") : null;
			int posDocDaFirmare = getPosModelloFromIdDoc(idDocDaFirmare, listaDocumentiIstruttoria);
			
			if (posDocDaFirmare != -1) {
				Record lRecord = listaDocumentiIstruttoria.get(posDocDaFirmare);
				String documentUri = lRecord.getAttribute("uriFileAllegato");
				String nomeFileModello = lRecord.getAttribute("nomeFileAllegato");
				InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(lRecord.getAttributeAsRecord("infoFile").getJsObj()));
				String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
				String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");				
				FirmaUtility.firmaMultipla(true, false, appletTipoFirmaAtti, hsmTipoFirmaAtti, documentUri, nomeFileModello, infoFile, new FirmaCallback() {
		
					@Override
					public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord infoFileFirmato) {
						aggiornaFileFirmato(nomeFileFirmato, uriFileFirmato, infoFileFirmato, callback);
					}
				});
			} else {
				if(callback != null) {
					callback.execute(null);
				}
			}
			//TODO firma e poi chiama aggiornaFileFirmato(callback);
		} else {
			if(callback != null) {
				callback.execute(null);
			}
		}
	}
		
	public void aggiornaFileFirmato(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord infoFile, ServiceCallback<Record> callback) {
		RecordList listaDocumentiIstruttoria = documentiIstruttoriaForm.getValuesAsRecord().getAttributeAsRecordList("listaDocumentiIstruttoria");
		String idDocDaFirmare = recordEvento != null ? recordEvento.getAttribute("idDocAzioni") : null;	
		int posModello = getPosModelloFromIdDoc(idDocDaFirmare, listaDocumentiIstruttoria);
		if (posModello != -1) {
			Record lRecord = listaDocumentiIstruttoria.get(posModello);
			lRecord.setAttribute("uriFileAllegato", uriFileFirmato);
			lRecord.setAttribute("nomeFileAllegato", nomeFileFirmato);
			lRecord.setAttribute("infoFile", infoFile); 
			lRecord.setAttribute("isChanged", true);
			listaDocumentiIstruttoria.set(posModello, lRecord);
		}
		Record lRecordForm = new Record();
		lRecordForm.setAttribute("listaDocumentiIstruttoria", listaDocumentiIstruttoria);
		documentiIstruttoriaForm.setValues(lRecordForm.toMap());
		// dopo l'editRecord devo risettare il mode del dettaglio, perchè altrimenti sulle replicableItem compaiono i bottoni di remove delle righe anche quando non dovrebbero
		if (isEseguibile()) {
			if (isReadOnly()) {
				if(listaDocumentiIstruttoriaItem != null) {
					listaDocumentiIstruttoriaItem.readOnlyMode();
				}
			} else {
				if(listaDocumentiIstruttoriaItem != null) {
					listaDocumentiIstruttoriaItem.setCanEdit(true);
				}
			}
		} else {
			if(listaDocumentiIstruttoriaItem != null) {
				listaDocumentiIstruttoriaItem.setCanEdit(false);
			}
		}
		
		if(callback != null) {
			callback.execute(null);
		}
	}		
	
	private int getPosDocFromIdDoc(String idDoc, RecordList listaDocumentiIstruttoria) {
		if (listaDocumentiIstruttoria != null) {
			for (int i = 0; i < listaDocumentiIstruttoria.getLength(); i++) {
				Record doc = listaDocumentiIstruttoria.get(i);
				if (doc.getAttribute("listaTipiFileAllegato") != null && doc.getAttribute("listaTipiFileAllegato").equalsIgnoreCase(idDoc)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public void protocolla(final Record recordModello, final ServiceCallback<Record> callback) {
		boolean flgDocPerAzionePresente = ((recordEvento.getAttribute("idDocAzioni") != null && !"".equals(recordEvento.getAttribute("idDocAzioni"))) || (recordModello != null && recordModello.getAttribute("idTipoDoc") != null && !"".equals(recordModello.getAttribute("idTipoDoc"))));
		if(flgDocPerAzionePresente && hasActionProtocolla() && isEsitoTaskAzioni(attrEsito)) {
			Record lRecordToSave = getRecordToSave();
			if(recordModello != null) {				
				lRecordToSave.setAttribute("idTipoDocToProt", recordModello.getAttribute("idTipoDoc"));
			} else {
				lRecordToSave.setAttribute("idDocToProt", recordEvento != null ? recordEvento.getAttribute("idDocAzioni") : null);
			}
			salvaDati(lRecordToSave, false, new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record object) {
					loadDettRichAccessoAtti(callback);
				}
			});	
		} else {
			if(callback != null) {
				callback.execute(null);
			}
		}
	}
	
	public void eventoSUE(Record recordModello, final ServiceCallback<Record> callback){
		boolean flgDocPerAzionePresente = ((recordEvento.getAttribute("idDocAzioni") != null && !"".equals(recordEvento.getAttribute("idDocAzioni"))) || (recordModello != null && recordModello.getAttribute("idTipoDoc") != null && !"".equals(recordModello.getAttribute("idTipoDoc"))));
		if(flgDocPerAzionePresente && tipoEventoSUE != null && !"".equals(tipoEventoSUE) && isEsitoTaskAzioni(attrEsito)) {
			Record lRecordEventoSUE = getRecordToSave();
			if(recordModello != null) {				
				lRecordEventoSUE.setAttribute("idTipoDocAllegatoSUE", recordModello.getAttribute("idTipoDoc"));
			} else {
				lRecordEventoSUE.setAttribute("idDocAllegatoSUE", recordEvento != null ? recordEvento.getAttribute("idDocAzioni") : null);
			}			
			lRecordEventoSUE.setAttribute("tipoEventoSUE", tipoEventoSUE);
			lRecordEventoSUE.setAttribute("idPraticaSUE", recordEvento != null ? recordEvento.getAttribute("eventoSUEIdPratica") : null);
			lRecordEventoSUE.setAttribute("codFiscaleSUE", recordEvento != null ? recordEvento.getAttribute("eventoSUECodFiscale") : null);
			lRecordEventoSUE.setAttribute("giorniSospensioneSUE", recordEvento != null ? recordEvento.getAttribute("eventoSUEGiorniSospensione") : null);
			lRecordEventoSUE.setAttribute("flgPubblicoSUE", recordEvento != null ? recordEvento.getAttributeAsBoolean("eventoSUEFlgPubblico") : null);						
			lRecordEventoSUE.setAttribute("destinatariSUE", recordEvento != null ? recordEvento.getAttributeAsRecordList("eventoSUEDestinatari") : null);
			lRecordEventoSUE.setAttribute("fileDaPubblicareSUE", recordEvento != null ? recordEvento.getAttributeAsRecordList("eventoSUEFileDaPubblicare") : null);
			
			// FIXME Sistemare questi valori
			lRecordEventoSUE.setAttribute("nroProtocolloSUE", lRecordEventoSUE != null ? lRecordEventoSUE.getAttributeAsRecordList("nroProtocolloPregresso") : null);
			lRecordEventoSUE.setAttribute("dataProtocolloSUE", lRecordEventoSUE != null ? lRecordEventoSUE.getAttributeAsDate("dataProtocolloPregresso") : null);
			lRecordEventoSUE.setAttribute("amministrazioneSUE", recordEvento != null && recordEvento.getAttribute("amministrazioneSUE") != null && !"".equalsIgnoreCase(recordEvento.getAttribute("amministrazioneSUE")) ? recordEvento.getAttribute("amministrazioneSUE") : "Comune di Milano");
			lRecordEventoSUE.setAttribute("aooSUE", recordEvento != null && recordEvento.getAttribute("aooSUE") != null && !"".equalsIgnoreCase(recordEvento.getAttribute("aooSUE")) ? recordEvento.getAttribute("aooSUE") : "c_f205");
			
			new GWTRestDataSource("NuovaRichiestaAccessoAttiDatasource").executecustom("eseguiEventoSUE", lRecordEventoSUE, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record result = response.getData()[0];
						if (callback != null) {
							callback.execute(result);
						}
					} else {
						reload();
					}
				}
			});			
		} else {
			if(callback != null) {
				callback.execute(null);
			}
		}		
	}
	
	protected void callbackSalvaDati(Record object) {
		
		idEvento = object.getAttribute("idEvento");
		
		final Record lRecordSalvaTask = new Record();
		lRecordSalvaTask.setAttribute("instanceId", instanceId);
		lRecordSalvaTask.setAttribute("activityName", activityName);
		lRecordSalvaTask.setAttribute("idProcess", idProcess);
		lRecordSalvaTask.setAttribute("idEventType", idTipoEvento);
		lRecordSalvaTask.setAttribute("idEvento", idEvento);
		lRecordSalvaTask.setAttribute("idUd", idUd);
		lRecordSalvaTask.setAttribute("note", messaggio);		
		
		boolean invioNotEmailFlgConfermaInvio = recordEvento != null && recordEvento.getAttributeAsBoolean("invioNotEmailFlgConfermaInvio");		
		boolean flgCallXDettagliMail = recordEvento != null && recordEvento.getAttributeAsBoolean("invioNotEmailFlgCallXDettagliMail");
				
		if (hasActionInvioNotEmail() && flgCallXDettagliMail) {
			//chiamo la store del dettaglio mail, poi se ho il flgConfermaInvio apro la popup e infine chiamo salvatask
			getDatiXInvioNotEmail(new DSCallback() {
				
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
						recordEvento = dsResponse.getData()[0];
						if (recordEvento != null && recordEvento.getAttributeAsBoolean("flgInvioNotEmail")) {
							attrEsitoNotEmail = null;

							if ( recordEvento != null && recordEvento.getAttributeAsBoolean("invioNotEmailFlgConfermaInvio")) {

								invioNotEmail(new BooleanCallback() {

									@Override
									public void execute(Boolean value) {

										if(value) {
											GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
											lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

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
													} else {
														reload();
													}
												}
											});
										} 
//										else {
//											reload();
//										}
									}				
								});
							} else {
								lRecordSalvaTask.setAttribute("invioNotEmailSubject", recordEvento != null ? recordEvento.getAttribute("invioNotEmailSubject") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailBody", recordEvento != null ? recordEvento.getAttribute("invioNotEmailBody") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailDestinatari", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatari") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailDestinatariCC", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCC") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailDestinatariCCN", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCCN") : null);						
								lRecordSalvaTask.setAttribute("invioNotEmailIdCasellaMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIdCasellaMittente") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIndirizzoMittente") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailAliasIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailAliasIndirizzoMittente") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailFlgPEC", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgPEC") : null);				
								lRecordSalvaTask.setAttribute("invioNotEmailFlgInvioMailXComplTask", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgInvioMailXComplTask") : null);				
//								lRecordSalvaTask.setAttribute("invioNotEmailFlgCallXDettagliMail", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgCallXDettagliMail") : null);				
								lRecordSalvaTask.setAttribute("invioNotEmailAttachment", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailAttachment") : null);				
								
								GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");		
								lAurigaTaskDataSource.addParam("flgInvioNotEmail", "true");
												
								lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

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
										} else {
											reload();
										}
									}
								});
							}
						} else {
							
							GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");													
							lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

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
									} else {
										reload();
									}
								}
							});
						}
					}
				}
			}, false);
			
		} else if (hasActionInvioNotEmail() && invioNotEmailFlgConfermaInvio) {
			// se ho azione invio notifica e ho il flgconferma (ma non flgCallXDettagliMail) apro la popup
			// con i dati della call execatt
			
			attrEsitoNotEmail = null;
			getDatiXInvioNotEmail(new DSCallback() {

				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
						invioNotEmail(new BooleanCallback() {

							@Override
							public void execute(Boolean value) {

								if(value) {
									GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
									lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

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
											} else {
												reload();
											}
										}
									});
								}
//								else {
//									reload();
//								}
							}
						});
					}
				}
			}, true);
		
		} else {
		
			lRecordSalvaTask.setAttribute("invioNotEmailSubject", recordEvento != null ? recordEvento.getAttribute("invioNotEmailSubject") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailBody", recordEvento != null ? recordEvento.getAttribute("invioNotEmailBody") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailDestinatari", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatari") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailDestinatariCC", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCC") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailDestinatariCCN", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCCN") : null);						
			lRecordSalvaTask.setAttribute("invioNotEmailIdCasellaMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIdCasellaMittente") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIndirizzoMittente") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailAliasIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailAliasIndirizzoMittente") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailFlgPEC", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgPEC") : null);		
			lRecordSalvaTask.setAttribute("invioNotEmailFlgInvioMailXComplTask", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgInvioMailXComplTask") : null);				
	//		lRecordSalvaTask.setAttribute("invioNotEmailFlgCallXDettagliMail", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgCallXDettagliMail") : null);				
			lRecordSalvaTask.setAttribute("invioNotEmailAttachment", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailAttachment") : null);				
			
			GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
			if(hasActionInvioNotEmail() /*&& isEsitoTaskAzioni(attrEsitoNotEmail)*/) {			
				lAurigaTaskDataSource.addParam("flgInvioNotEmail", "true");
				attrEsitoNotEmail = null;
			}
			lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {
	
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
					} else {
						reload();
					}
				}
			});
		}
		
	}
	
	protected void invioNotEmail(final BooleanCallback callback) {

		final boolean invioNotEmailFlgInvioMailXComplTask = recordEvento != null && recordEvento.getAttributeAsBoolean("invioNotEmailFlgInvioMailXComplTask");		

		DSCallback sendCallback = new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if(callback != null) {
						callback.execute(true);
					}
				} else {
					if(callback != null) {
						callback.execute(invioNotEmailFlgInvioMailXComplTask ? false : true);
					}
				}
			}				
		};
		final NuovoMessaggioWindow lNuovoMessaggioWindow = new NuovoMessaggioWindow("nuovo_messaggio","invioNuovoMessaggio", instance, sendCallback) {
			
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
				lRecord.setAttribute("oggetto", recordEvento != null ? recordEvento.getAttribute("invioNotEmailSubject") : null);
				lRecord.setAttribute("bodyHtml", recordEvento != null ? recordEvento.getAttribute("invioNotEmailBody") : null);				
				RecordList invioNotEmailDestinatari = recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatari") : null;
				if(invioNotEmailDestinatari != null && invioNotEmailDestinatari.getLength() > 0) {
					String destinatari = null;
					for(int i = 0; i < invioNotEmailDestinatari.getLength(); i++) {				
						if(destinatari == null) {
							destinatari = invioNotEmailDestinatari.get(i).getAttribute("value");
						} else {
							destinatari += "; " + invioNotEmailDestinatari.get(i).getAttribute("value");
						}
					}	
					lRecord.setAttribute("destinatari", destinatari);
				}				
				RecordList invioNotEmailDestinatariCC = recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCC") : null;
				if(invioNotEmailDestinatariCC != null && invioNotEmailDestinatariCC.getLength() > 0) {
					String destinatariCC = null;
					for(int i = 0; i < invioNotEmailDestinatariCC.getLength(); i++) {				
						if(destinatariCC == null) {
							destinatariCC = invioNotEmailDestinatariCC.get(i).getAttribute("value");
						} else {
							destinatariCC += "; " + invioNotEmailDestinatariCC.get(i).getAttribute("value");
						}
					}	
					lRecord.setAttribute("destinatariCC", destinatariCC);
				}				
				RecordList invioNotEmailDestinatariCCN = recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCCN") : null;
				if(invioNotEmailDestinatariCCN != null && invioNotEmailDestinatariCCN.getLength() > 0) {
					String destinatariCCN = null;
					for(int i = 0; i < invioNotEmailDestinatariCCN.getLength(); i++) {				
						if(destinatariCCN == null) {
							destinatariCCN = invioNotEmailDestinatariCCN.get(i).getAttribute("value");
						} else {
							destinatariCCN += "; " + invioNotEmailDestinatariCCN.get(i).getAttribute("value");
						}
					}	
					lRecord.setAttribute("destinatariCCN", destinatariCCN);
				}				
				lRecord.setAttribute("mittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIndirizzoMittente") : null);
				
				//TODO FEDERICA BUONO invioNotEmailAttachment
				RecordList files = recordEvento.getAttributeAsRecordList("invioNotEmailAttachment") != null ? recordEvento.getAttributeAsRecordList("invioNotEmailAttachment") : new RecordList();
//				String idUd = null;
//				for (Record file : files) {
//					if(idUd == null) {
//						idUd = file.getAttribute("idUd");
//					} else {
//						idUd += ";" + file.getAttribute("idUd");
//					}
//				}
//				lRecord.setAttribute("idUD", idUd); // TODO idUd da collegare alla mail
				RecordList attachList = new RecordList();
				for (int i = 0; i < files.getLength(); i++) {
					Record attach = new Record();
					attach.setAttribute("fileNameAttach", files.get(i).getAttribute("nomeFile"));
					attach.setAttribute("infoFileAttach", files.get(i).getAttributeAsRecord("infoFile"));
					attach.setAttribute("uriAttach", files.get(i).getAttribute("uri"));
					attachList.add(attach);
				}
				lRecord.setAttribute("attach", attachList);

				return lRecord;
			};
			
			@Override
			public void manageOnCloseClick() {
				if(invioNotEmailFlgInvioMailXComplTask) {
					SC.warn("Se chiudi la finestra di invio mail senza effettuare l'invio il passo dell'iter non verrà  completato. Confermi di voler chiudere?", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {
								_window.markForDestroy();
								if(callback != null) {
									callback.execute(false);
								}
							}
						}
					});
				} else {
					SC.warn("Se chiudi la finestra di invio mail senza effettuare l'invio il passo dell'iter verrà  completato ugualmente. Confermi di voler chiudere?", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {
								_window.markForDestroy();
								if(callback != null) {
									callback.execute(true);
								}
							}
						}
					});
				}			
			}
		};

	}

	protected void getDatiXInvioNotEmail(final DSCallback callback, boolean prepareAttach) {
		
		GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");	
		lAurigaTaskDataSource.addParam("prepareAttach", String.valueOf(prepareAttach));
		lAurigaTaskDataSource.executecustom("getDatiXInvioNotifMail", recordEvento, callback);
	}
	
	@Override
	public void caricaModelloInDocumentiIstruttoria(String idModello, String tipoModello, final String flgConvertiInPdf, final ServiceCallback<Record> callback) {
		
		Record recordCaricamentoModello = new Record();
		recordCaricamentoModello.setAttribute("idModello", idModello);
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ModelliDocDatasource", "idModello", FieldType.TEXT);
		lGwtRestDataSource.getData(recordCaricamentoModello, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = dsResponse.getData()[0];					
					Record lRecordCompilaModello = getRecordToSave();					
					lRecordCompilaModello.setAttribute("idProcess", idProcess);
					lRecordCompilaModello.setAttribute("idModello", lRecord.getAttribute("idModello"));
					lRecordCompilaModello.setAttribute("tipoModello", lRecord.getAttribute("tipoModello"));
					lRecordCompilaModello.setAttribute("nomeModello", lRecord.getAttribute("nomeModello"));
					String estensioneFileDaGenerare = flgConvertiInPdf != null && "1".equalsIgnoreCase(flgConvertiInPdf) ?  "pdf" : "doc";
					lRecordCompilaModello.setAttribute("estensioneFileDaGenerare", estensioneFileDaGenerare);
					final String nomeFileModello = lRecord.getAttribute("nomeModello") + "." + estensioneFileDaGenerare;
					lRecordCompilaModello.setAttribute("displayFilenameModello", nomeFileModello);
					
					// Attributi dinamici doc
					if (attributiAddDocDetails != null) {
						lRecordCompilaModello.setAttribute("rowidDoc", rowidDoc);
						lRecordCompilaModello.setAttribute("valori", getAttributiDinamiciDoc());
						lRecordCompilaModello.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
						lRecordCompilaModello.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
					}
					
					new GWTRestDataSource("NuovaRichiestaAccessoAttiDatasource").executecustom("compilaModello", lRecordCompilaModello, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record responseRecord = response.getData()[0];
							Record fileToUpload = new Record();
							
							fileToUpload.setAttribute("nomeFilePrimario", responseRecord.getAttribute("nomeFile"));
							fileToUpload.setAttribute("uriFilePrimario", responseRecord.getAttribute("uri"));
							fileToUpload.setAttribute("infoFile", responseRecord.getAttributeAsRecord("infoFile"));
							callback.execute(fileToUpload);
						}
					});
				}
			}
		});
	}
	
	@Override
	public boolean isModelloModificabileInAllegati(String idModello) {
		if(listaRecordModelli != null && listaRecordModelli.getLength() > 0) {
			for (int i = 0; i < listaRecordModelli.getLength(); i++) {
				if(idModello != null && idModello.equals(listaRecordModelli.get(i).getAttribute("idModello"))) {
					return !listaRecordModelli.get(i).getAttributeAsBoolean("flgLocked");
				}
			}			
		}
		return true;
	}

	public void saveAndGoWithModello(Record recordModello, String esito, ServiceCallback<Record> callback) {
		boolean flgGeneraDaModello = true;		
		RecordList listaDocumentiIstruttoria = documentiIstruttoriaForm.getValuesAsRecord().getAttributeAsRecordList("listaDocumentiIstruttoria");
		final String idTipoModello = recordModello.getAttribute("idTipoDoc");
		int posModello = getPosModelloFromTipo(idTipoModello, listaDocumentiIstruttoria);
		if (posModello != -1) {
			String estremiProtUd = listaDocumentiIstruttoria.get(posModello).getAttribute("estremiProtUd");
			boolean isUdProtocollata = estremiProtUd != null && !"".equals(estremiProtUd);
			if(isUdProtocollata) {
				flgGeneraDaModello = false;
			}
		}		
		if(flgGeneraDaModello) {			
			compilazioneAutomaticaModelloPdf(recordModello, esito, callback);
		} else {
			if (callback != null) {
				Record recordToPass = new Record();
				recordToPass.setAttribute("idTipoDoc", idTipoModello);
				callback.execute(recordToPass);
			}
		}
	}
	
	/**
	 * <ul>
	 * <li>Carica il modello specificato, inietta i valori, genera la versione pdf 
	 * (se non viene specificato un formato diverso) e, se richiesto,</li> 
	 * <li>il file viene firmato digitalmente e viene aggiunto ai documenti istruttoria</li>
	 * </ul>
	 * 
	 * @param callback
	 */
	public void compilazioneAutomaticaModelloPdf(Record recordModello, String esito, final ServiceCallback<Record> callback) {

		if (recordModello != null) {
						
			final String descrizione = recordModello.getAttribute("descrizione");
			final String idTipoModello = recordModello.getAttribute("idTipoDoc");
			final String nomeTipoModello = recordModello.getAttribute("nomeTipoDoc");
			final String nomeModello = recordModello.getAttribute("nomeModello");
			final boolean flgDaFirmare = recordModello.getAttributeAsBoolean("flgDaFirmare") != null && recordModello.getAttributeAsBoolean("flgDaFirmare");
			final boolean flgParteDispositivo = recordModello.getAttributeAsBoolean("flgParteDispositivo") != null && recordModello.getAttributeAsBoolean("flgParteDispositivo");
			
			Record lRecordCompilaModello = getRecordToSave();
			
			lRecordCompilaModello.setAttribute("idProcess", idProcess);
			lRecordCompilaModello.setAttribute("tipoModello", recordModello.getAttribute("tipoModello"));
			lRecordCompilaModello.setAttribute("idModello", recordModello.getAttribute("idModello"));
			lRecordCompilaModello.setAttribute("nomeModello", nomeModello);
			String estensioneFileDaGenerare = recordModello.getAttribute("formato") != null && !"".equalsIgnoreCase(recordModello.getAttribute("formato")) ? recordModello.getAttribute("formato") : "doc";
			estensioneFileDaGenerare = "docx".equalsIgnoreCase(estensioneFileDaGenerare) ? "doc" : estensioneFileDaGenerare;
			final String nomeFileModello = recordModello.getAttribute("nomeFile") + "." + estensioneFileDaGenerare;
			lRecordCompilaModello.setAttribute("displayFilenameModello", nomeFileModello);
			lRecordCompilaModello.setAttribute("estensioneFileDaGenerare", estensioneFileDaGenerare);
			
			// Attributi dinamici doc
			if (attributiAddDocDetails != null) {
				lRecordCompilaModello.setAttribute("rowidDoc", rowidDoc);
				lRecordCompilaModello.setAttribute("valori", getAttributiDinamiciDoc());
				lRecordCompilaModello.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
				lRecordCompilaModello.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
			}
			
			Layout.showWaitPopup(I18NUtil.getMessages().generazioneDocumentoWaitPopup_message());	

			final GWTRestDataSource lNuovaRichiestaAccessoAttiDatasource = new GWTRestDataSource("NuovaRichiestaAccessoAttiDatasource");
			lNuovaRichiestaAccessoAttiDatasource.addParam("esitoTask", esito);
			lNuovaRichiestaAccessoAttiDatasource.executecustom("compilaModello", lRecordCompilaModello, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						
						Record result = response.getData()[0];
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
													aggiungiModelloADocumentiIstruttoria(flgParteDispositivo, descrizione, idTipoModello, nomeTipoModello, uriFileFirmato, nomeFileFirmato, info, callback);
												}
											});
										} else {
											aggiungiModelloADocumentiIstruttoria(flgParteDispositivo, descrizione, idTipoModello, nomeTipoModello, documentUri, nomeFileModello, infoFile, callback);
										}
									}
								});
					}
				}
			});
		}
	}
	
	protected void aggiungiModelloADocumentiIstruttoria(boolean flgParteDispositivo, String descrizione, String idTipoModello, String nomeTipoModello, String uriFile,
			String nomeFile, InfoFileRecord infoFile, ServiceCallback<Record> callback) {

		if (documentiIstruttoriaForm != null) {
			
			RecordList listaDocumentiIstruttoria = documentiIstruttoriaForm.getValuesAsRecord().getAttributeAsRecordList("listaDocumentiIstruttoria");

			int posModello = getPosModelloFromTipo(idTipoModello, listaDocumentiIstruttoria);
			
			Record lRecordModello = new Record();		
			if (posModello != -1) {
				lRecordModello = listaDocumentiIstruttoria.get(posModello);
			}
			
			lRecordModello.setAttribute("flgProtocolla", hasActionProtocolla() && isEsitoTaskAzioni(attrEsito));
			
			lRecordModello.setAttribute("flgParteDispositivo", flgParteDispositivo);
			if(!flgParteDispositivo) {
				lRecordModello.setAttribute("flgNoPubblAllegato", true);
				lRecordModello.setAttribute("flgPubblicaSeparato", false);
				lRecordModello.setAttribute("flgDatiSensibili", false);
			}
			
			lRecordModello.setAttribute("nomeFileAllegato", nomeFile);
			lRecordModello.setAttribute("uriFileAllegato", uriFile);
			lRecordModello.setAttribute("descrizioneFileAllegato", descrizione);

			lRecordModello.setAttribute("listaTipiFileAllegato", idTipoModello);
			lRecordModello.setAttribute("idTipoFileAllegato", idTipoModello);
			lRecordModello.setAttribute("descTipoFileAllegato", nomeTipoModello);

			lRecordModello.setAttribute("nomeFileAllegatoTif", "");
			lRecordModello.setAttribute("uriFileAllegatoTif", "");
			lRecordModello.setAttribute("remoteUri", false);
			
			// Metto isChanged a true solo se l'impronta del nuovo file generato è diversa da quello generato in precedenza, in modo da versionare solamente se il file è variato
			// FIXME Verificare questa porzione di codice			
			Record precInfoFile = lRecordModello.getAttribute("infoFile") != null ? lRecordModello.getAttributeAsRecord("infoFile") : null;
			String precImpronta = precInfoFile != null ? precInfoFile.getAttribute("impronta") : null;
			boolean isChanged = false;
			if (precImpronta == null || !precImpronta.equals(infoFile.getImpronta())) {
				isChanged = true;
			}
			lRecordModello.setAttribute("isChanged", isChanged);
			
			lRecordModello.setAttribute("nomeFileVerPreFirma", nomeFile);
			lRecordModello.setAttribute("uriFileVerPreFirma", uriFile);
			lRecordModello.setAttribute("infoFileVerPreFirma", infoFile);
			lRecordModello.setAttribute("improntaVerPreFirma", infoFile.getImpronta());
			lRecordModello.setAttribute("infoFile", infoFile);
			
			if (posModello == -1) {
				if (listaDocumentiIstruttoria == null || listaDocumentiIstruttoria.getLength() == 0) {
					listaDocumentiIstruttoria = new RecordList();
				}
				listaDocumentiIstruttoria.addAt(lRecordModello, 0);
			} else {
				listaDocumentiIstruttoria.set(posModello, lRecordModello);
			}

			Record lRecordForm = new Record();
			lRecordForm.setAttribute("listaDocumentiIstruttoria", listaDocumentiIstruttoria);
			documentiIstruttoriaForm.setValues(lRecordForm.toMap());
			
			if(listaDocumentiIstruttoriaItem != null) {
				listaDocumentiIstruttoriaItem.resetCanvasChanged();
			}

//			if (documentiIstruttoriaSection != null) {
//				documentiIstruttoriaSection.openIfhasValue();
//			}

		}
		
		if (callback != null) {
			Record recordToPass = new Record();
			recordToPass.setAttribute("idTipoDoc", idTipoModello);
			callback.execute(recordToPass);
		}
	}
	
	public int getPosModelloFromTipo(String idTipoModello, RecordList listaDocumentiIstruttoria) {
		if (listaDocumentiIstruttoria != null) {
			for (int i = 0; i < listaDocumentiIstruttoria.getLength(); i++) {
				Record doc = listaDocumentiIstruttoria.get(i);
				if (doc.getAttribute("listaTipiFileAllegato") != null && doc.getAttribute("listaTipiFileAllegato").equalsIgnoreCase(idTipoModello)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public int getPosModelloFromIdDoc(String idDoc, RecordList listaDocumentiIstruttoria) {
		if (listaDocumentiIstruttoria != null) {
			for (int i = 0; i < listaDocumentiIstruttoria.getLength(); i++) {
				Record doc = listaDocumentiIstruttoria.get(i);
				if (doc.getAttribute("idDocAllegato") != null && doc.getAttribute("idDocAllegato").equalsIgnoreCase(idDoc)) {
					return i;
				}
			}
		}
		return -1;
	}
	
//	public int getPosModelloFromIdDoc(String idDoc, RecordList listaDocumentiIstruttoria) {
//		if (listaDocumentiIstruttoria != null) {
//			for (int i = 0; i < listaDocumentiIstruttoria.getLength(); i++) {
//				Record doc = listaDocumentiIstruttoria.get(i);
//				if (doc.getAttribute("listaTipiFileAllegato") != null && doc.getAttribute("listaTipiFileAllegato").equalsIgnoreCase(idDoc)) {
//					return i;
//				}
//			}
//		}
//		return -1;
//	}
	
	@Override
	public boolean isTaskArchivio() {
		return recordEvento.getAttribute("taskArchivio") != null && recordEvento.getAttributeAsBoolean("taskArchivio");
	}
	
	@Override
	public void editRecord(Record record) {
				
		super.editRecord(record);

		if (isEseguibile()) {
			if (idTipoTaskDoc != null && !"".equals(idTipoTaskDoc) && nomeTipoTaskDoc != null && !"".equals(nomeTipoTaskDoc)) {
				AllegatoCanvas lAllegatoCanvas = listaDocumentiIstruttoriaItem.getAllegatoCanvasFromTipo(idTipoTaskDoc);
				if (lAllegatoCanvas == null) {
					lAllegatoCanvas = (AllegatoCanvas) listaDocumentiIstruttoriaItem.onClickNewButton();
					lAllegatoCanvas.setFixedTipoFileAllegato(idTipoTaskDoc, nomeTipoTaskDoc);
					if(flgParteDispositivoTaskDoc != null && flgParteDispositivoTaskDoc) {
						lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", true);
					}					
				}
				lAllegatoCanvas.setForceToShowGeneraDaModelloForTipoTaskDoc(true);
				lAllegatoCanvas.redrawButtons();
			}
		}	
	}
	
	@Override
	protected void openSectionsIfHasValue() {
		super.openSectionsIfHasValue();
		if (idTipoTaskDoc != null && !"".equals(idTipoTaskDoc) && nomeTipoTaskDoc != null && !"".equals(nomeTipoTaskDoc)) {
			if (documentiIstruttoriaSection != null) {
				documentiIstruttoriaSection.open();
			}	
		}
	}

}
