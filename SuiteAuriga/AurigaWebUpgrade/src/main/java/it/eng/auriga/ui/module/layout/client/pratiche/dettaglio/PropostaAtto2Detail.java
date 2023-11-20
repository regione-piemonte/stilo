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
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.iterAtti.SelezionaEsitoTaskWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatoCanvas;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowWithCallback;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailAtti;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public class PropostaAtto2Detail extends ProtocollazioneDetailAtti implements PropostaAttoInterface {

	protected PropostaAtto2Detail instance;

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
	protected String messaggio;

	protected String codTabDefault;
	protected String idTipoTaskDoc;
	protected String nomeTipoTaskDoc;
	protected Boolean flgParteDispositivoTaskDoc;

	protected DettaglioPraticaLayout dettaglioPraticaLayout;

	protected Set<String> esitiTaskOk;
	
	protected RecordList listaRecordModelli;
	protected HashMap<String, Record> controlliXEsitiTaskDoc;

	protected HashSet<String> attributiAddDocTabsDatiStorici;

	protected String uriModCopertina;
	protected String tipoModCopertina;
	protected String uriModAppendice;
	protected String tipoModAppendice;
	protected String tipoModAssDocTask;
	
	protected ImgButtonItem downloadFilePrimarioButton;

	// public PropostaAtto2Detail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento,
	// DettaglioPraticaLayout dettaglioPraticaLayout) {
	// this(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, lRecordEvento, dettaglioPraticaLayout, null, null, false);
	// }

	/**
	 * 
	 * @param nomeEntita
	 * @param idProcess
	 * @param nomeFlussoWF
	 * @param processNameWF
	 * @param idUd
	 * @param lRecordEvento
	 * @param dettaglioPraticaLayout
	 * @param modello
	 * @param esitoXCompilazioneModello
	 * @param firmaModello
	 */
	// public PropostaAtto2Detail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento,
	// DettaglioPraticaLayout dettaglioPraticaLayout, String modello, String esitoXCompilazioneModello, boolean firmaModello) {
	public PropostaAtto2Detail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento,
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

		this.uriModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("uriModCopertina") : null;
		this.tipoModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModCopertina") : null;
		this.uriModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("uriModAppendice") : null;
		this.tipoModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAppendice") : null;
		this.tipoModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAssDocTask") : null;

		this.codTabDefault = lRecordEvento != null ? lRecordEvento.getAttribute("codTabDefault") : null;
		this.idTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoTaskDoc") : null;
		this.nomeTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("nomeTipoTaskDoc") : null;
		this.flgParteDispositivoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgParteDispositivoTaskDoc") : null;
		
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

		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
	}

	public boolean isEsitoTaskOk(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return (esito == null || esitiTaskOk == null || (esito != null && esitiTaskOk != null && esitiTaskOk.contains(esito)));		
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
		// tabs.put("DATI_CONTABILI", "Dati contabili");
		// tabs.put("DATI_CONTABILI_CAPITALE", "Dati contabili conto capitale");
		// tabs.put("DATI_RICH_RIL_IMPEGNO", "Dati rich. rilascio impegno");
		// tabs.put("CIG_CUP", "CIG - CUP");
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
	 * Compila il modello con i dati attualmente salvati dall'utente ed esegue la callback specificata
	 */
	protected void compilazioneAutomaticaAnteprimaConCopertina(final DSCallback callback) {

		Record modelloRecord = new Record();
		modelloRecord.setAttribute("processId", idProcess);
		modelloRecord.setAttribute("uriModCopertina", uriModCopertina);
		modelloRecord.setAttribute("tipoModCopertina", tipoModCopertina);
		modelloRecord.setAttribute("uriModAppendice", uriModAppendice);
		modelloRecord.setAttribute("tipoModAppendice", tipoModAppendice);
		modelloRecord.setAttribute("uriModello", filePrimarioForm.getValue("uriFileVerPreFirma"));
		modelloRecord.setAttribute("tipoModello", tipoModAssDocTask);
		modelloRecord.setAttribute("nomeFile", filePrimarioForm.getValue("nomeFileVerPreFirma"));
		modelloRecord.setAttribute("oggetto", contenutiForm.getValue("oggetto"));
		modelloRecord.setAttribute("listaUoCoinvolte", altreUoCoinvolteForm.getValue("listaUoCoinvolte"));
		modelloRecord.setAttribute("valori", getAttributiDinamiciDoc());
		modelloRecord.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());

		new GWTRestService<Record, Record>("CompilaModelloDatasource").executecustom("compilaModelloAnteprimaConCopertina", modelloRecord, callback);

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
												aggiungiModelloAdAllegati(flgParteDispositivo, descrizione, idTipoModello, nomeTipoModello, uriFileFirmato, nomeFileFirmato, info, callback);
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

	public boolean getShowAttributiDinamiciDoc() {
		return nome != null && !nome.toUpperCase().startsWith("PROPOSTA_ATTO|*|");
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
	
	/**
	 * Se attivaEliminazioneUOCoinvolte = true si possono eliminare delle UO coinvolte a patto che la colonna 6 della riga corrispondente valga 0
	 */
	public boolean isAttivaEliminazioneUOCoinvolte() {
		boolean isAttivaEliminazioneUOCoinvolte = true;
		if (recordEvento != null && recordEvento.getAttribute("attivaEliminazioneUOCoinvolte") != null) {
			isAttivaEliminazioneUOCoinvolte = "true".equals(recordEvento.getAttribute("attivaEliminazioneUOCoinvolte"));			
		}
		return isAttivaEliminazioneUOCoinvolte;
	}
	
	public void readOnlyMode(boolean isAttivaEliminazioneUOCoinvolte) {
		readOnlyMode();
		altreUoCoinvolteItem.readOnlyMode(isAttivaEliminazioneUOCoinvolte);
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
						readOnlyMode(isAttivaEliminazioneUOCoinvolte());
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
						lRecord.setAttribute("uriFilePrimario", lRecord.getAttribute("uriFileVerPreFirma"));
						lRecord.setAttribute("nomeFilePrimario", lRecord.getAttribute("nomeFileVerPreFirma"));
						lRecord.setAttribute("infoFile", lRecord.getAttributeAsRecord("infoFileVerPreFirma"));
						lRecord.setAttribute("isDocPrimarioChanged", true);
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
		if (isReadOnly()) {
			lProtocolloDataSource.addParam("idTaskCorrente", getIdTaskCorrente());
		}

		final Map<String, Object> attributiDinamiciEvent = getAttributiDinamici();
		final Map<String, String> tipiAttributiDinamiciEvent = getTipiAttributiDinamici();

		final String esito = (!flgIgnoreObblig && attrEsito != null) ? attrEsito.getAttribute("valore") : null;
		if (!flgIgnoreObblig && attrEsito != null) {
			lProtocolloDataSource.addParam("nomeAttrCustomEsito", attrEsito.getAttribute("nome"));
			lProtocolloDataSource.addParam("valoreAttrCustomEsito", attrEsito.getAttribute("valore"));
			attributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("valore"));
			tipiAttributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("tipo"));
			attrEsito = null;
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

	public Map<String, Object> getAttributiDinamici() {
		return new HashMap<String, Object>();
	}

	public Map<String, String> getTipiAttributiDinamici() {
		return new HashMap<String, String>();
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
							// TODO: qui dovrei ricaricare i tab degli attributi dinamici per vedere le eventuali variazioni
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

	public void firma() {
		Record lRecord = getRecordToSave(null);
		lRecord.setAttribute("tipoModFilePrimario", tipoModAssDocTask);
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FirmaDirigenteDatasource");
		lGwtRestService.executecustom("getFileDaFirmare", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				callbackGetFileDaFirmare(response);
			}
		});
	}

	protected void callbackGetFileDaFirmare(DSResponse response) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record lRecord = response.getData()[0];
			Record[] records = lRecord.getAttributeAsRecordArray("files");

			// Leggo gli eventuali parametri per forzare il tipo d firma
			String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
			String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");
			FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, records, new FirmaMultiplaCallback() {

				@Override
				public void execute(Map<String, Record> files, Record[] filesAndUd) {
					callbackFirmaMultipla(files, filesAndUd);
				}
			});
		}

	}

	protected void callbackFirmaMultipla(Map<String, Record> files, Record[] filesAndUd) {

		Record lRecordConModifiche = new Record();
		Record lRecordProtocolloOriginale = getRecordToSave(null);
		lRecordConModifiche.setAttribute("protocolloOriginale", lRecordProtocolloOriginale);
		Record lRecordFileFirmati = new Record();
		Record[] lRecordModificati = files.values().toArray(new Record[] {});
		lRecordFileFirmati.setAttribute("files", lRecordModificati);
		lRecordConModifiche.setAttribute("fileFirmati", lRecordFileFirmati);
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FirmaDirigenteDatasource");
		lGwtRestService.executecustom("aggiornaFileFirmati", lRecordConModifiche, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				callbackAggiornaFileFirmati(response);
			}
		});

	}

	protected void callbackAggiornaFileFirmati(DSResponse response) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record lRecord = response.getData()[0];
			caricaDettaglio(null, lRecord);
			setCanEdit(true);
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

	// @Override
	// public void setCanEdit(boolean canEdit) {
	//
	// super.setCanEdit(canEdit);
	// if(mode.equals("view") || mode.equals("edit")) {
	// condivisioneItem.setCanEdit(false);
	// }
	// }

	@Override
	protected void createFilePrimarioButtons() {
 
		super.createFilePrimarioButtons();

		downloadFilePrimarioButton = new ImgButtonItem("downloadButton", "file/download_manager.png", I18NUtil.getMessages()
				.protocollazione_detail_downloadMenuItem_prompt());
		downloadFilePrimarioButton.setAlwaysEnabled(true);
		downloadFilePrimarioButton.setColSpan(1);
		downloadFilePrimarioButton.setIconWidth(16);
		downloadFilePrimarioButton.setIconHeight(16);
		downloadFilePrimarioButton.setIconVAlign(VerticalAlignment.BOTTOM);
		downloadFilePrimarioButton.setAlign(Alignment.LEFT);
		downloadFilePrimarioButton.setWidth(16);
		downloadFilePrimarioButton.setRedrawOnChange(true);
		downloadFilePrimarioButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""));
			}
		});
		downloadFilePrimarioButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String nomeFilePrimario = (String) filePrimarioForm.getValue("nomeFilePrimario");
				if (nomeFilePrimario != null) {
					if (nomeFilePrimario.toLowerCase().endsWith(".pdf.p7m")) {
						Menu showFirmatoAndSbustato = new Menu();
						MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
						firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFile();
							}
						});
						MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
						sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFileSbustato();
							}
						});
						showFirmatoAndSbustato.setItems(firmato, sbustato);
						showFirmatoAndSbustato.showContextMenu();
					} else if (nomeFilePrimario.toLowerCase().endsWith(".pdf")) {
						clickDownloadFile();
					} else {
						clickDownloadFileAggiornato();
					}
				}
			}
		});

		filePrimarioButtons.setNestedFormItems(previewButton, previewEditButton, editButton, fileFirmatoDigButton, firmaNonValidaButton,
				downloadFilePrimarioButton);
		
	}

	public void clickDownloadFileAggiornato() {
		final String nomeFilePrimario = filePrimarioForm.getValueAsString("nomeFilePrimario");
		if (nomeFilePrimario != null) {
			String idUd = new Record(vm.getValues()).getAttribute("idUd");
			String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
			addToRecent(idUd, idDocPrimario);
			compilazioneAutomaticaAnteprimaConCopertina(new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record result = response.getData()[0];
						String nomeFilePdf = result.getAttribute("nomeFile");
						String uriFilePdf = result.getAttribute("uri");
						Record lRecord = new Record();
						lRecord.setAttribute("displayFilename", nomeFilePdf);
						lRecord.setAttribute("uri", uriFilePdf);
						lRecord.setAttribute("sbustato", "false");
						lRecord.setAttribute("remoteUri", false);
						DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
					}
				}
			});
		}
		;
	}

	@Override
	public void clickPreviewFile() {
		String nomeFilePrimario = filePrimarioForm.getValueAsString("nomeFilePrimario");
		if (nomeFilePrimario != null) {
			final Record detailRecord = new Record(vm.getValues());
			final String idUd = detailRecord.getAttribute("idUd");
			String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
			addToRecent(idUd, idDocPrimario);
			compilazioneAutomaticaAnteprimaConCopertina(new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record result = response.getData()[0];
						String nomeFilePdf = result.getAttribute("nomeFile");
						String uriFilePdf = result.getAttribute("uri");
						InfoFileRecord infoFilePdf = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
						preview(detailRecord, idUd, nomeFilePdf, uriFilePdf, false, infoFilePdf);
					}
				}
			});
		}
	}

	@Override
	public boolean showFilePrimarioForm() {
		return true;
	}

	@Override
	public boolean showDesUOProtocolloItem() {
		return false;
	}

	@Override
	public boolean showGeneraDaModelloButton() {
		return false; // non ho il bottone di genera da modello nel primario ma viene creato in automatico
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
