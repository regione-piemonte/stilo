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
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaNonEseguitaCallback;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.ProcedimentiCollegatiPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.iterAtti.SelezionaEsitoTaskWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatoCanvas;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowWithCallback;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetail;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedDateItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class TaskDettFascicoloGenCompletoDetail extends CustomDetail implements TaskFlussoInterface {

	protected TaskDettFascicoloGenCompletoDetail instance;

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
		
	protected String idFolder;
	protected String folderType;
	protected String rowidFolder;

	protected String codTabDefault;
	protected String idTipoTaskDoc;
	protected String nomeTipoTaskDoc;

	protected Boolean flgUnioneFile;
	protected Boolean flgTimbraFile;
	protected Boolean flgFirmaFile;
	protected Boolean flgShowAnteprimaFileDaFirmare;
	protected Boolean flgFirmaAutomatica;
	
	protected HashMap<String, String> mappaWarningMsgXEsitoTask;
	
	protected String esitoTaskDaPreimpostare;
	protected String msgTaskDaPreimpostare;
	
	protected DettaglioPraticaLayout dettaglioPraticaLayout;

	protected RecordList listaRecordModelli;
	
	protected Set<String> esitiTaskOk;
	protected HashMap<String, Record> controlliXEsitiTaskDoc;
	protected HashSet<String> valoriEsito;
	
	protected Set<String> esitiTaskAzioni;
	
	protected Record attrEsito;
	protected String messaggio;
	
	protected LinkedHashMap<String, String> attributiAddFolderTabs;
	protected HashMap<String, VLayout> attributiAddFolderLayouts;
	protected HashMap<String, AttributiDinamiciDetail> attributiAddFolderDetails;

	protected VLayout mainLayout;

	protected TabSet tabSet;
	protected Tab tabDatiPrincipali;

	protected HeaderDetailSection detailSectionDatiIdentificativi;
	protected DynamicForm datiIdentificativiForm;
	protected HiddenItem datiProtDocIstrMittenteIdItem;
	protected HiddenItem datiProtDocIstrDestinatarioFlgPersonaFisicaItem;
	protected HiddenItem datiProtDocIstrDestinatarioDenomCognomeItem;
	protected HiddenItem datiProtDocIstrDestinatarioNomeItem;
	protected HiddenItem datiProtDocIstrCodFiscaleItem;
	protected TextItem nomeFolderItem;
	protected TextItem nomeFolderTypeItem;
	protected ImgButtonItem procCollegatiButtonItem;
	protected Boolean hasProcCollegati;
	protected ExtendedDateItem dataPresentazioneIstanza;

	protected DetailSection detailSectionDocumentiIniziali;
	protected DynamicForm documentiInizialiForm;
	protected AllegatiItem documentiInizialiItem;
	
	protected DetailSection detailSectionDocumentiIstruttoria;
	protected DynamicForm documentiIstruttoriaForm;
	protected AllegatiItem documentiIstruttoriaItem;
	protected HiddenItem timestampGetDataItem;
	
	protected PreviewWindowWithCallback previewWindowToCloseAfterFirma;

	public TaskDettFascicoloGenCompletoDetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idFolder, Record lRecordEvento,
			DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita);

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

		this.idFolder = idFolder;

		this.codTabDefault = lRecordEvento != null ? lRecordEvento.getAttribute("codTabDefault") : null;
		this.idTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoTaskDoc") : null;
		this.nomeTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("nomeTipoTaskDoc") : null;

		this.flgUnioneFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgUnioneFile") : null;
		this.flgTimbraFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgTimbraFile") : null;
		this.flgFirmaFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgFirmaFile") : null;
		this.flgShowAnteprimaFileDaFirmare = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgShowAnteprimaFileDaFirmare") : null;		
		this.flgFirmaAutomatica = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgDocActionsFirmaAutomatica") : null;		
		
		this.hasProcCollegati = false;

		this.dettaglioPraticaLayout = dettaglioPraticaLayout;
		
		this.listaRecordModelli = dettaglioPraticaLayout.getListaModelliAttivita(activityName);

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
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);

		createMainLayout();
		setMembers(mainLayout);

	}
		
	protected void createMainLayout() {

		createTabSet();
		
		mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		mainLayout.setMembers(tabSet);		
	}
	
	public void createTabSet() {
		
		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		tabSet.setWidth100();
		tabSet.setBorder("0px");
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		tabSet.setPaneMargin(0);

		tabDatiPrincipali = new Tab("<b>Dati principali</b>");
		tabDatiPrincipali.setAttribute("tabID", "HEADER");
		tabDatiPrincipali.setPrompt("Dati principali");

		VLayout spacerDatiPrincipali = new VLayout();
		spacerDatiPrincipali.setHeight100();
		spacerDatiPrincipali.setWidth100();

		VLayout layoutDatiPrincipali = getLayoutDatiPrincipali();

		VLayout layoutTabDatiPrincipali = createLayoutTab(layoutDatiPrincipali, spacerDatiPrincipali);

		// Aggiungo i layout ai tab
		tabDatiPrincipali.setPane(layoutTabDatiPrincipali);

		tabSet.addTab(tabDatiPrincipali);
	}
	
	public VLayout getLayoutDatiPrincipali() {

		VLayout layoutDatiDocumento = new VLayout(5);

		createDetailSectionDatiIdentificativi();
		layoutDatiDocumento.addMember(detailSectionDatiIdentificativi);		
		
		createDetailSectionDocumentiIniziali();
		layoutDatiDocumento.addMember(detailSectionDocumentiIniziali);
		
		createDetailSectionDocumentiIstruttoria();
		layoutDatiDocumento.addMember(detailSectionDocumentiIstruttoria);

		return layoutDatiDocumento;
	};
	
	public void createDetailSectionDatiIdentificativi() {
		
		datiIdentificativiForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {

				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		datiIdentificativiForm.setValuesManager(vm);
		datiIdentificativiForm.setWidth100();
		datiIdentificativiForm.setPadding(5);
		datiIdentificativiForm.setWrapItemTitles(false);
		datiIdentificativiForm.setNumCols(10);
		datiIdentificativiForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		datiIdentificativiForm.setTabSet(tabSet);
		datiIdentificativiForm.setTabID("HEADER");
		
		datiProtDocIstrMittenteIdItem = new HiddenItem("datiProtDocIstrMittenteId");
		datiProtDocIstrDestinatarioFlgPersonaFisicaItem = new HiddenItem("datiProtDocIstrDestinatarioFlgPersonaFisica");
		datiProtDocIstrDestinatarioDenomCognomeItem = new HiddenItem("datiProtDocIstrDestinatarioDenomCognome");
		datiProtDocIstrDestinatarioNomeItem = new HiddenItem("datiProtDocIstrDestinatarioNome");
		datiProtDocIstrCodFiscaleItem = new HiddenItem("datiProtDocIstrCodFiscale");
		timestampGetDataItem = new HiddenItem("timestampGetData");

		nomeFolderItem = new TextItem("nomeFolder");
		nomeFolderItem.setShowTitle(false);
		nomeFolderItem.setStartRow(true);
		nomeFolderItem.setEndRow(false);
		nomeFolderItem.setWidth(600);

		nomeFolderTypeItem = new TextItem("nomeFolderType", "Tipo procedimento");
		nomeFolderTypeItem.setStartRow(false);
		nomeFolderTypeItem.setEndRow(false);
		nomeFolderTypeItem.setWidth(300);
		
		procCollegatiButtonItem = new ImgButtonItem("procCollegatiButtonItem", "buttons/link.png", "Presenza procedimenti");
		procCollegatiButtonItem.setAlwaysEnabled(true);
		procCollegatiButtonItem.setColSpan(1);
		procCollegatiButtonItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				manageProcedimentiCollegati(idFolder);
			}
		});
		procCollegatiButtonItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return hasProcCollegati != null && hasProcCollegati;
			}
		});
		
		dataPresentazioneIstanza = new ExtendedDateItem("dataPresentazioneInstanza", "Data Presentazione") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
				setTextBoxStyle(it.eng.utility.Styles.textItemBold);
				setCanFocus(false);
			}
		};
		dataPresentazioneIstanza.setEndRow(true);
		dataPresentazioneIstanza.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return new Boolean(dataPresentazioneIstanza.getValue() != null);
			}
		});
		
		datiIdentificativiForm.setFields(datiProtDocIstrMittenteIdItem, datiProtDocIstrDestinatarioFlgPersonaFisicaItem, datiProtDocIstrDestinatarioDenomCognomeItem, datiProtDocIstrDestinatarioNomeItem, datiProtDocIstrCodFiscaleItem, nomeFolderItem, nomeFolderTypeItem, procCollegatiButtonItem, dataPresentazioneIstanza, timestampGetDataItem);
		
		detailSectionDatiIdentificativi = new HeaderDetailSection("Dati identificativi", true, true, false, datiIdentificativiForm);			
	}
	
	public void createDetailSectionDocumentiIniziali() {
		
		documentiInizialiForm = new DynamicForm();
		documentiInizialiForm.setValuesManager(vm);
		documentiInizialiForm.setWidth("100%");
		documentiInizialiForm.setPadding(5);
		documentiInizialiForm.setTabSet(tabSet);
		documentiInizialiForm.setTabID("HEADER");

		documentiInizialiItem = new AllegatiItem() {

			@Override
			public boolean showNumeroAllegato() {
				return false;
			}

			@Override
			public boolean showTipoAllegato() {
				return false;
			}

			@Override
			public boolean showNomeFileAllegato() {
				return false;
			}

			@Override
			public String getTitleDescrizioneFileAllegato() {
				return "Oggetto";
			}

			@Override
			public Integer getWidthDescrizioneFileAllegato() {
				return new Integer("500");
			}

			@Override
			public boolean isHideTimbraInAltreOperazioniButton() {
				return true;
			}

			@Override
			public boolean showVisualizzaFileUdButton() {
				return true;
			}
			
			@Override
			public boolean isDocumentiInizialiIstanza() {
				return true;
			}
			
			@Override
			public boolean getShowVersioneOmissis() {
				return false;
			}
			
		};
		documentiInizialiItem.setShowFlgParteDispositivo(false);
		documentiInizialiItem.setName("listaDocumentiIniziali");
		documentiInizialiItem.setShowTitle(false);

		documentiInizialiForm.setFields(documentiInizialiItem);
		
		detailSectionDocumentiIniziali = new DetailSection("Richiesta", true, true, false, documentiInizialiForm);		
	}
	
	public String getDetailSectionDocumentiIstruttoriaTitle() {
		return "Documenti istruttoria";
	}
	
	public boolean isDocIstruttoriaCedAutotutela() {
		return false;
	}
	
	public void createDetailSectionDocumentiIstruttoria() {
		
		documentiIstruttoriaForm = new DynamicForm();
		documentiIstruttoriaForm.setValuesManager(vm);
		documentiIstruttoriaForm.setWidth("100%");
		documentiIstruttoriaForm.setPadding(5);
		documentiIstruttoriaForm.setTabSet(tabSet);
		documentiIstruttoriaForm.setTabID("HEADER");

		documentiIstruttoriaItem = new AllegatiItem() {

			@Override
			public GWTRestDataSource getTipiFileAllegatoDataSource() {
				GWTRestDataSource lLoadComboTipoDocInProcessDataSource = new GWTRestDataSource("LoadComboTipoDocInProcessDataSource", "idTipoDoc",
						FieldType.TEXT, true);
				lLoadComboTipoDocInProcessDataSource.addParam("idProcess", idProcess);
				return lLoadComboTipoDocInProcessDataSource;
			}

			@Override
			public boolean showFilterEditorInTipiFileAllegato() {
				return false;
			}

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				AllegatoCanvas lRispostaCanvas = getRispostaCanvas();
				if (lRispostaCanvas != null) {
//					lRispostaCanvas.getForm()[0].setValue("flgParteDispositivo", true);
					if (canEdit) {
						if (lRispostaCanvas.getRemoveButton() != null) {
							lRispostaCanvas.getRemoveButton().setAlwaysDisabled(true);
						}
//						lRispostaCanvas.getForm()[0].getItem("flgParteDispositivo").setCanEdit(false);
						lRispostaCanvas.getForm()[0].getItem("listaTipiFileAllegato").setCanEdit(false);
						lRispostaCanvas.getForm()[0].getItem("descrizioneFileAllegato").setCanEdit(false);
					}
				}
			}

			@Override
			public String getIdProcess() {
				return idProcess;
			}

			@Override
			public String getTitleFlgParteDispositivo() {
				return "parte integrante";
			}

			@Override
			public boolean canBeEditedByApplet() {
				return true;
			}

			@Override
			public boolean showGeneraDaModello() {
				return true;
			}
			
			@Override
			public void caricaModelloAllegato(String idModello, String tipoModello, String flgConvertiInPdf, String idUd, final ServiceCallback<Record> callback) {
				final Record modelloRecord = new Record();
				modelloRecord.setAttribute("idModello", idModello);
				modelloRecord.setAttribute("tipoModello", tipoModello);
				modelloRecord.setAttribute("idFolder", idFolder);
				modelloRecord.setAttribute("idUd", idUd);
				if (attributiAddFolderDetails != null) {
					modelloRecord.setAttribute("valori", getAttributiDinamiciFolder());
					modelloRecord.setAttribute("tipiValori", getTipiAttributiDinamiciFolder());
				}
				GWTRestDataSource lGeneraDaModelloWithDatiFolderDataSource = new GWTRestDataSource("GeneraDaModelloWithDatiFolderDataSource");
				lGeneraDaModelloWithDatiFolderDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
				lGeneraDaModelloWithDatiFolderDataSource.executecustom("caricaModello", modelloRecord, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							if (callback != null) {
								callback.execute(response.getData()[0]);
							}
						}
					}
				});
			}
			
			@Override
			public void visualizzaVersioni(Record allegatoRecord) {
				final String nroProgr = allegatoRecord.getAttributeAsString("numeroProgrAllegato");
				final String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");						
				final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
				Record lRecordToLoad = new Record();
				lRecordToLoad.setAttribute("idUd", allegatoRecord.getAttributeAsString("idUdAppartenenza"));
				lProtocolloDataSource.getData(lRecordToLoad, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record recordProtocollo = response.getData()[0];
							ProtocollazioneDetail.visualizzaVersioni(idDoc, "DI", nroProgr, "", recordProtocollo);
						}
					}
				});
			}

			@Override
			public String getIdTaskCorrenteAllegati() {
				return getIdTaskCorrente();
			}

			@Override
			public HashSet<String> getTipiModelliAttiAllegati() {
				return getTipiModelliAtti();
			}
						
			@Override
			public boolean isDocumentiIstruttoria() {
				return true;
			}
			
			@Override
			public boolean isDocCedAutotutela() {
				return isDocIstruttoriaCedAutotutela();
			}
			
			@Override
			public boolean isDocIstruttoriaDettFascicoloGenCompleto() {
				return true;
			}
			
			@Override
			public boolean isAttivaGestSepAllegatiUD() {
				return true;
			}
			
			@Override
			public String getEmailContribuente() {
				Map<String, Object> attributiDinamiciFolder = getAttributiDinamiciFolder();
				return attributiDinamiciFolder != null ? (String) attributiDinamiciFolder.get("EMAIL_CONTRIBUENTE") : null;
			}
			
			@Override
			public boolean getShowVersioneOmissis() {
				return false;
			}
			
			@Override
			public void clickProtocolla(Record allegatoRecord, ServiceCallback<Record> callback) {
				protocollaDocumentoIstruttoriaInUscita(allegatoRecord, callback);
			}
			
			@Override
			public void afterClickProtocolla(Record allegatoRecord) {
				reload();
			}
			
			@Override
			public String getImportaFileDocumentiBtnTitle() {
				return  "Importa documenti nel fascicolo del procedimento";
			}
	
			@Override
			public boolean showOperazioniTimbraturaAllegato(Record allegatoRecord) {
				String estremiProtUd = allegatoRecord.getAttribute("estremiProtUd") != null ? allegatoRecord.getAttribute("estremiProtUd") : "";
				if(estremiProtUd != null && !"".equals(estremiProtUd)) {		
					return true;
				}
				return false;
			}
		};
//		documentiIstruttoriaItem.setShowFlgParteDispositivo(true);
		documentiIstruttoriaItem.setName("listaDocumentiIstruttoria");
		documentiIstruttoriaItem.setShowTitle(false);
		documentiIstruttoriaItem.setShowCollegaDocumentiImportati(false);
		// Nella maschera di dettaglio autotutele/CED come in quelle del dettaglio pratica generico e TSO (parlo di quelle basate su dettaglio fascicolo) nei documenti di istruttoria, se sono registrati a protocollo o repertorio, va messo nel menu' di operazioni (quello richiamato dal tasto con le barre) l'azione di "Timbra" che deve mettere il timbro di registrazione come nella maschera di dettaglio UD
//		documentiIstruttoriaItem.setHideTimbraInAltreOperazioniButton(true);
		documentiIstruttoriaItem.setShowDownloadOutsideAltreOperazioniButton(true);

		documentiIstruttoriaForm.setFields(documentiIstruttoriaItem);
		
		detailSectionDocumentiIstruttoria = new DetailSection(getDetailSectionDocumentiIstruttoriaTitle(), true, true, false, documentiIstruttoriaForm);			
	}
	
	public void afterCaricaAttributiDinamiciFolder() {
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
	
	public boolean isShowAnteprimaFileDaFirmare() {
		return flgShowAnteprimaFileDaFirmare != null && flgShowAnteprimaFileDaFirmare;
	}
	
	public boolean hasActionFirmaAutomatica() {
		return flgFirmaAutomatica != null && flgFirmaAutomatica;
	}
	
	public boolean isEsitoTaskOk(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return (esito == null || esitiTaskOk == null || (esito != null && esitiTaskOk != null && esitiTaskOk.contains(esito)));
	}
	
	public boolean isEsitoTaskAzioniValorizzato() {
		return esitiTaskAzioni != null; 		
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
	
	protected String getIdFolder() {
		return idFolder;
	}
	
	public String getIdProcessTask() {
		return idProcess;
	}
	
	public String getIdTaskCorrente() {
		return nome.substring(0, nome.indexOf("|*|"));
	}
	
	@Override
	public Record getRecordEvento() {
		return recordEvento;
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

	@Override
	public void loadDati() {

		loadDettFolder(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				caricaAttributiDinamiciFolder(nomeFlussoWF, processNameWF, activityName, folderType, rowidFolder);
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
		setCanEdit(false, datiIdentificativiForm);
		setCanEdit(false, documentiInizialiForm);		
		nomeFolderItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		nomeFolderTypeItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		if (!isEseguibile()) {
			// se il task non è eseguibile disabilito tutti gli attributi dinamici
			if (attributiAddFolderDetails != null) {
				for (String key : attributiAddFolderDetails.keySet()) {
					AttributiDinamiciDetail detail = attributiAddFolderDetails.get(key);
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
		documentiIstruttoriaItem.readOnlyMode();
	}

	@Override
	public void editRecord(Record record) {
		vm.clearErrors(true);
		clearTabErrors(tabSet);
		record.setAttribute("nomeFolder", record.getAttribute("nomeFascicolo"));
		if(record.getAttributeAsRecordList("listaProcCollegati") != null &&
		  !record.getAttributeAsRecordList("listaProcCollegati").isEmpty()){
			hasProcCollegati = true;
		} else {
			hasProcCollegati = false;
		}
		super.editRecord(record);		
		RecordList listaDocumentiIniziali = record.getAttributeAsRecordList("listaDocumentiIniziali");
		if (listaDocumentiIniziali == null || listaDocumentiIniziali.getLength() == 0) {
			detailSectionDocumentiIniziali.setVisible(false);
		} else {
			detailSectionDocumentiIniziali.setVisible(true);
		}		
		if (isEseguibile()) {
			if (idTipoTaskDoc != null && !"".equals(idTipoTaskDoc) && nomeTipoTaskDoc != null && !"".equals(nomeTipoTaskDoc)) {
				AllegatoCanvas lAllegatoCanvas = documentiIstruttoriaItem.getAllegatoCanvasFromTipo(idTipoTaskDoc);
				if (lAllegatoCanvas == null) {
					lAllegatoCanvas = (AllegatoCanvas) documentiIstruttoriaItem.onClickNewButton();
					lAllegatoCanvas.setFixedTipoFileAllegato(idTipoTaskDoc, nomeTipoTaskDoc);
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
	public void showTabErrors(TabSet tabSet) {
		super.showTabErrors(tabSet);
		if (attributiAddFolderTabs != null) {
			for (String key : attributiAddFolderTabs.keySet()) {
				if (attributiAddFolderDetails != null && attributiAddFolderDetails.get(key) != null) {
					attributiAddFolderDetails.get(key).showTabErrors(tabSet);
				}
			}
		}
	}	
	
	@Override
	public boolean customValidate() {
		boolean valid = super.customValidate();
		if (attributiAddFolderDetails != null) {
			for (String key : attributiAddFolderDetails.keySet()) {
				boolean esitoAttributiAddFolderDetail = attributiAddFolderDetails.get(key).validate();
				valid = valid && esitoAttributiAddFolderDetail;
			}
		}
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
			AllegatoCanvas lAllegatoCanvas = documentiIstruttoriaItem.getAllegatoCanvasFromTipo(idTipoTaskDoc);
			if (flgObbligatorio && lAllegatoCanvas == null) {
				lAllegatoCanvas = (AllegatoCanvas) documentiIstruttoriaItem.onClickNewButton();
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
		return valid;
	}
	
	public Boolean validateSenzaObbligatorieta() {
		clearTabErrors(tabSet);
		vm.clearErrors(true);
		boolean valid = true;
		if (attributiAddFolderDetails != null) {
			for (String key : attributiAddFolderDetails.keySet()) {
				boolean esitoAttributiAddFolderDetail = attributiAddFolderDetails.get(key).validateSenzaObbligatorieta();
				valid = valid && esitoAttributiAddFolderDetail;
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

	public void loadDettFolder(final DSCallback callback) {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUdFolder", idFolder);
		GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		if (this instanceof TaskDettFascicoloGenCompletoDetail) {
			lArchivioDatasource.addParam("isTaskDettFascGen", "true");
		} else {
			lArchivioDatasource.addParam("isTaskDettFasc", "true");
		}
		lArchivioDatasource.addParam("idProcess", idProcess);
		lArchivioDatasource.addParam("taskName", activityName);
		lArchivioDatasource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					rowidFolder = lRecord.getAttribute("rowidFolder");
					folderType = lRecord.getAttribute("folderType");
					editRecord(lRecord, recordNum);
					if (callback != null) {
						callback.execute(response, rawData, request);
					}
				}
			}
		});
	}

	public Record getRecordToSave(String motivoVarDatiReg) {
		Record lRecordToSave = new Record(vm.getValues());
		lRecordToSave.setAttribute("idProcess", idProcess);
		if (attributiAddFolderDetails != null) {
			lRecordToSave.setAttribute("idFolder", idFolder);
			lRecordToSave.setAttribute("folderType", folderType);
			lRecordToSave.setAttribute("rowidFolder", rowidFolder);
			lRecordToSave.setAttribute("valori", getAttributiDinamiciFolder());
			lRecordToSave.setAttribute("tipiValori", getTipiAttributiDinamiciFolder());
		}
		return lRecordToSave;
	}
	
	public Record getRecordToProtocollaDocumentoIstruttoriaInUscita(Record lRecordDocIstruttoriaDaProtocollare) {
		Record lRecordToProtocolla = new Record(vm.getValues());
		Map<String, Object> recordValoriAttributiDinamici = getAttributiDinamiciFolder();
		Map<String, String> recordTipiAttributiDinamici = getTipiAttributiDinamiciFolder();
		lRecordToProtocolla.setAttribute("valori", recordValoriAttributiDinamici);
		lRecordToProtocolla.setAttribute("tipiValori", recordTipiAttributiDinamici);
		lRecordToProtocolla.setAttribute("idUdDaProtocollare", lRecordDocIstruttoriaDaProtocollare.getAttribute("idUdAppartenenza"));
		// Valorizzo la descrizione dell'allegato
		String descrizioneFileAllegato = lRecordDocIstruttoriaDaProtocollare.getAttribute("descrizioneFileAllegato");
		String descTipoFileAllegato = lRecordDocIstruttoriaDaProtocollare.getAttribute("descTipoFileAllegato");
		String nomeFile = lRecordDocIstruttoriaDaProtocollare.getAttribute("nomeFileAllegato");
		String desAllegato = descrizioneFileAllegato != null && !"".equalsIgnoreCase(descrizioneFileAllegato) ? descrizioneFileAllegato : descTipoFileAllegato != null && !"".equalsIgnoreCase(descTipoFileAllegato) ? descTipoFileAllegato : nomeFile;
		lRecordToProtocolla.setAttribute("desAllegatoDaProtocollare", desAllegato);
		lRecordToProtocolla.setAttribute("datiProtDocIstrUOProtocollante", AurigaLayout.getUoLavoro());
		return lRecordToProtocolla;
	}

	public void salvaDati(final boolean flgIgnoreObblig, final ServiceCallback<Record> callback) {
		salvaDati(flgIgnoreObblig, callback, null);
	}
	
	public void salvaDati(final boolean flgIgnoreObblig, final ServiceCallback<Record> callback, final ServiceCallback<Record> errorCallback) {
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {				
				setSaved(true);
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
				saveDettFolder(lRecordToSave, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							salvaAttributiDinamiciFolder(flgIgnoreObblig, rowidFolder, activityName, esito, new ServiceCallback<Record>() {

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
											object.setAttribute("esito", esito);
											if (callback != null) {
												callback.execute(object);
											} else {
												Layout.hideWaitPopup();
											}
										}
									});
								}
							});
						} else if (errorCallback != null) {
							errorCallback.execute(null);					
						}
					}
				});
			}			
		});
	}

	public void saveDettFolder(Record record, final DSCallback callback) {
		// metto a null valori e tipiValori perchè sennò scatta la validazione
		record.setAttribute("valori", (Map) null);
		record.setAttribute("tipiValori", (Map) null);
		GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lArchivioDatasource.addParam("isAttivaGestioneErroriFile", "true");		
		if (this instanceof TaskDettFascicoloGenCompletoDetail) {
			lArchivioDatasource.addParam("isTaskDettFascGen", "true");
		} else {
			lArchivioDatasource.addParam("isTaskDettFasc", "true");
		}
		lArchivioDatasource.addParam("idProcess", idProcess);
		lArchivioDatasource.addParam("taskName", activityName);
		if (isReadOnly()) {
			lArchivioDatasource.addParam("idTaskCorrente", getIdTaskCorrente());
		}
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
		try {
			lArchivioDatasource.updateData(record, new DSCallback() {

				@Override
				public void execute(final DSResponse response, final Object rawData, final DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						manageErroriFile(response.getData()[0], new ServiceCallback<Record>() {
							
							@Override
							public void execute(final Record lRecord) {
								if (callback != null) {
									callback.execute(response, rawData, request);
								}
							}
						});
					} else {
						Layout.hideWaitPopup();
					}
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	public void manageErroriFile(Record lRecord, ServiceCallback<Record> callback) {
		if(lRecord.getAttributeAsMap("erroriFile") != null && !lRecord.getAttributeAsMap("erroriFile").isEmpty()) {
			Layout.hideWaitPopup();
			HashMap<String, String> erroriFile = (HashMap<String, String>) lRecord.getAttributeAsMap("erroriFile");
			// lo devo fare due volte altrimenti ogni tanto al primo giro non da l'evidenza grafica degli errori sui file allegati appena inseriti
			if(documentiIstruttoriaItem != null) {
				documentiIstruttoriaForm.clearErrors(true);
				documentiIstruttoriaItem.manageErroriFile(erroriFile);				
			}
			if(documentiIstruttoriaItem != null) {
				documentiIstruttoriaForm.clearErrors(true);
				documentiIstruttoriaItem.manageErroriFile(erroriFile);				
			}
			showTabErrors(tabSet);
		} else {
			if(callback != null) {
				callback.execute(lRecord);
			}
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
		} else {
			messaggio = null; // qui e in tutti gli altri punti non devo sbiancare messaggio
			attrEsito = null;
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}

	@Override
	public void salvaDatiDefinitivo() {
		// tolgo queste due righe perchè se l'utente preme il bottone "Salva e procedi" mentre sta aspettando che si carichi il modulo hybrid della firma poi si sbianca l'esito
//		messaggio = null;
//		attrEsito = null;
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
							SelezionaEsitoTaskWindow selezionaEsitoTaskWindow = new SelezionaEsitoTaskWindow(attr, isReadOnly(), esitiTaskOk, valoriEsito, mappaWarningMsgXEsitoTask, esitoTaskDaPreimpostare, msgTaskDaPreimpostare,
								new ServiceCallback<Record>() {
	
									@Override
									public void execute(Record object) {
										messaggio = object.getAttribute("messaggio");
										attrEsito = new Record(attr.toMap());
										attrEsito.setAttribute("valore", object.getAttribute(nomeAttrCustomEsitoTask));
										continuaSalvaDatiDefinitivoWithValidate();												
									}
								}
							);
							selezionaEsitoTaskWindow.show();
							break;
						} else {
							messaggio = null;
							attrEsito = null;
						}
					}
				}
			});
		} else {
			messaggio = null;
			attrEsito = null;
			continuaSalvaDatiDefinitivoWithValidate();				
		}
	}
	
	private void continuaSalvaDatiDefinitivoWithValidate() {
		if(isEsitoTaskOk(attrEsito)) {
			if (validate()) {		
				saveAndGoAlert();				
			} else {
				messaggio = null;
				attrEsito = null;
				Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
			}
		} else {
			if (validateSenzaObbligatorieta()) {
				saveAndGoAlert();
			} else {
				messaggio = null;
				attrEsito = null;
				Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
			}
		}
	}

	public String getValidateErrorMessage() {
		return I18NUtil.getMessages().validateError_message();
	}

	public void saveAndGoAlert() {
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
	}
	
	public RecordList getListaRecordModelliXEsitoPreAvanzamentoFlusso(String esito) {
		if (listaRecordModelli != null && listaRecordModelli.getLength() > 0) {	
			RecordList listaRecordModelliConEsitoUguale = new RecordList();		
			RecordList listaRecordModelliSenzaEsito = new RecordList();		
			for (int i = 0; i < listaRecordModelli.getLength(); i++) {
				String listaEsitiXGenModello = listaRecordModelli.get(i).getAttribute("esitiXGenModello");							
				if (listaEsitiXGenModello != null && !"".equals(listaEsitiXGenModello)) {
					for (String esitoXGenModello : new StringSplitterClient(listaEsitiXGenModello, "|*|").getTokens()) {
						if (esito != null && esito.equalsIgnoreCase(esitoXGenModello)) {
							if(!listaRecordModelli.get(i).getAttributeAsBoolean("flgPostAvanzamentoFlusso")) {
								listaRecordModelliConEsitoUguale.add(listaRecordModelli.get(i));
							}
						}
					} 
				} else if(!listaRecordModelli.get(i).getAttributeAsBoolean("flgPostAvanzamentoFlusso")) {
					listaRecordModelliSenzaEsito.add(listaRecordModelli.get(i));
				}
			}	
			if(listaRecordModelliConEsitoUguale != null && listaRecordModelliConEsitoUguale.getLength() > 0) {
				return listaRecordModelliConEsitoUguale;
			} else if(listaRecordModelliSenzaEsito != null && listaRecordModelliSenzaEsito.getLength() > 0) {
				return listaRecordModelliSenzaEsito;
			} 
		}
		return null;
	}
	
	public RecordList getListaRecordModelliXEsitoPostAvanzamentoFlusso(String esito) {
		if (listaRecordModelli != null && listaRecordModelli.getLength() > 0) {	
			RecordList listaRecordModelliConEsitoUguale = new RecordList();		
			RecordList listaRecordModelliSenzaEsito = new RecordList();		
			for (int i = 0; i < listaRecordModelli.getLength(); i++) {
				String listaEsitiXGenModello = listaRecordModelli.get(i).getAttribute("esitiXGenModello");							
				if (listaEsitiXGenModello != null && !"".equals(listaEsitiXGenModello)) {
					for (String esitoXGenModello : new StringSplitterClient(listaEsitiXGenModello, "|*|").getTokens()) {
						if (esito != null && esito.equalsIgnoreCase(esitoXGenModello)) {
							if(listaRecordModelli.get(i).getAttributeAsBoolean("flgPostAvanzamentoFlusso")) {
								listaRecordModelliConEsitoUguale.add(listaRecordModelli.get(i));
							}
						}
					} 
				} else if(listaRecordModelli.get(i).getAttributeAsBoolean("flgPostAvanzamentoFlusso")) {					
					listaRecordModelliSenzaEsito.add(listaRecordModelli.get(i));
				}
			}	
			if(listaRecordModelliConEsitoUguale != null && listaRecordModelliConEsitoUguale.getLength() > 0) {
				return listaRecordModelliConEsitoUguale;
			} else if(listaRecordModelliSenzaEsito != null && listaRecordModelliSenzaEsito.getLength() > 0) {
				return listaRecordModelliSenzaEsito;
			} 
		}
		return null;
	}

	public void saveAndGo() {		
		final String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;	
		final RecordList listaRecordModelliXEsitoPreAvanzamentoFlusso = getListaRecordModelliXEsitoPreAvanzamentoFlusso(esito);
		boolean eseguiAzioni = false;
		if(isEsitoTaskAzioniValorizzato()) {
			if(isEsitoTaskAzioni(attrEsito)) {
				eseguiAzioni = true;
			}
		} else if(isEsitoTaskOk(attrEsito)) {
			eseguiAzioni = true;
		}
		if(eseguiAzioni) {
			salvaDati(true, new ServiceCallback<Record>() {
				@Override
				public void execute(Record object) {
					idEvento = object.getAttribute("idEvento");
					Layout.hideWaitPopup();
					loadDettFolder(new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							saveAndGoWithListaModelliGenAutomatica(true, listaRecordModelliXEsitoPreAvanzamentoFlusso, esito, new ServiceCallback<RecordList>() {
								
								@Override
								public void execute(final RecordList listaRecordModelliGeneratiDaFirmare) {
									if(hasActionUnioneFile()) {
										// nell'unione dei file se ho dei file firmati pades devo prendere la versione precedente (quella che usiamo per l'editor, e la convertiamo in pdf) se c'è, altrimenti quella corrente 
										// se non sono tutti i convertibili i file do errore nell'unione e blocco tutto
										unioneFileAndReturn(hasActionTimbra(), listaRecordModelliGeneratiDaFirmare);
									} else if(hasActionFirma()) {
										firmaFile(hasActionTimbra(), listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {
											
											@Override
											public void execute(final Record recordAfterFirma) {
												if(previewWindowToCloseAfterFirma != null) {
													previewWindowToCloseAfterFirma.markForDestroy();
													previewWindowToCloseAfterFirma = null;
												}
												salvaDati(false, new ServiceCallback<Record>() {

													@Override
													public void execute(Record object) {
														callbackSalvaDati(object);																
													}
												});
											}
										});																			
									} else if(hasActionTimbra()) {
										timbraFile(listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {
											
											@Override
											public void execute(final Record recordAfterFirma) {
												if(previewWindowToCloseAfterFirma != null) {
													previewWindowToCloseAfterFirma.markForDestroy();
													previewWindowToCloseAfterFirma = null;
												}
												salvaDati(false, new ServiceCallback<Record>() {

													@Override
													public void execute(Record object) {								
														callbackSalvaDati(object);														
													}
												});		
											}
										});			
									} else {
										firmaAggiornaFileGenerati(listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {

											@Override
											public void execute(Record recordFirma) {
												if(previewWindowToCloseAfterFirma != null) {
													previewWindowToCloseAfterFirma.markForDestroy();
													previewWindowToCloseAfterFirma = null;
												}
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
			});				
		} else {
			saveAndGoWithListaModelliGenAutomatica(false, listaRecordModelliXEsitoPreAvanzamentoFlusso, esito, new ServiceCallback<RecordList>() {

				@Override
				public void execute(RecordList listaRecordModelliGeneratiDaFirmare) {
					firmaAggiornaFileGenerati(listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {

						@Override
						public void execute(Record recordFirma) {
							if(previewWindowToCloseAfterFirma != null) {
								previewWindowToCloseAfterFirma.markForDestroy();
								previewWindowToCloseAfterFirma = null;
							}
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
	}
	
	public void getFileDaFirmare(boolean fileDaTimbrare, DSCallback callback) {
		final Record lRecord = getRecordToSave(null);
		GWTRestService<Record, Record> lTaskDettFascicoloGenDataSource = new GWTRestService<Record, Record>("TaskDettFascicoloGenDataSource");
		lTaskDettFascicoloGenDataSource.addParam("fileDaTimbrare", fileDaTimbrare ? "true" : "");		
		lTaskDettFascicoloGenDataSource.executecustom("getFileDaFirmare", lRecord, callback);
	}
	
	// recupera i file allegati da firmare assieme al file unione nel task di Firma di adozione
	public void getFileAllegatiDaFirmareWithFileUnione(DSCallback callback) {
		final Record lRecord = getRecordToSave();
		GWTRestService<Record, Record> lTaskDettFascicoloGenDataSource = new GWTRestService<Record, Record>("TaskDettFascicoloGenDataSource");
		lTaskDettFascicoloGenDataSource.executecustom("getFileAllegatiDaFirmareWithFileUnione", lRecord, callback);
	}
	
	public void showAnteprimaFileDaFirmare(Record[] filesDaFirmare, final RecordList listaRecordModelliGeneratiDaFirmare, final ServiceCallback<RecordList> callback) {				
		if(isShowAnteprimaFileDaFirmare() && filesDaFirmare != null && filesDaFirmare.length == 1) {
			String uriFile = filesDaFirmare[0].getAttribute("uri");
			String nomeFile = filesDaFirmare[0].getAttribute("nomeFile");
			InfoFileRecord infoFile = filesDaFirmare[0].getAttributeAsRecord("infoFile") != null ? new InfoFileRecord(filesDaFirmare[0].getAttributeAsRecord("infoFile")) : null;							
			if(uriFile != null && !"".equals(uriFile)) {		
				new PreviewWindowWithCallback(uriFile, true, infoFile, "FileToExtractBean", nomeFile, new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record recordPreview) {	
						if(callback != null) {
							callback.execute(listaRecordModelliGeneratiDaFirmare);
						}
					}
				});	
			} else if(callback != null) {
				callback.execute(listaRecordModelliGeneratiDaFirmare);
			}
		} else if(callback != null) {
			callback.execute(listaRecordModelliGeneratiDaFirmare);
		}
	}
	
	protected void firmaFile(boolean fileDaTimbrare, final RecordList listaRecordModelliGeneratiDaFirmare, final ServiceCallback<Record> callback) {
		getFileDaFirmare(fileDaTimbrare, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordFileDaFirmare = response.getData()[0];
					final Record[] filesDaFirmare = lRecordFileDaFirmare.getAttributeAsRecordArray("files");					
					showAnteprimaFileDaFirmare(filesDaFirmare, listaRecordModelliGeneratiDaFirmare, new ServiceCallback<RecordList>() {
						
						@Override
						public void execute(RecordList listaRecordModelliGeneratiDaFirmare) {
							RecordList listaFilesDaFirmare = new RecordList();
							if(filesDaFirmare != null) {
								listaFilesDaFirmare.addList(filesDaFirmare);
							}
							if(listaRecordModelliGeneratiDaFirmare != null) {						
								for(int i = 0; i < listaRecordModelliGeneratiDaFirmare.getLength(); i++) {							
									String uriFileGenerato = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("uriFileGenerato");
									InfoFileRecord infoFileGenerato = InfoFileRecord.buildInfoFileString(JSON.encode(listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsRecord("infoFileGenerato").getJsObj()));							
									String nomeFileModello = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nomeFile") + ".pdf";
									boolean flgDaFirmare = listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare");							
									if (flgDaFirmare) {								
										Record record = new Record();
										record.setAttribute("idFile", "fileGenerato" + uriFileGenerato); //ATTENZIONE, la parola "fileGenerato" viene usata nella successiva action FirmaAutomatica per discriminare i file da non rifirmare 
										record.setAttribute("uri", uriFileGenerato);
										record.setAttribute("nomeFile", nomeFileModello);
										record.setAttribute("infoFile", infoFileGenerato);
										listaFilesDaFirmare.add(record);				
									}		
								}
							}					
							if(listaFilesDaFirmare.getLength() > 0) {
								// Leggo gli eventuali parametri per forzare il tipo di firma
								String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
								String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
								FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, listaFilesDaFirmare.toArray(), new FirmaMultiplaCallback() {
						
									@Override
									public void execute(final Map<String, Record> signedFiles, Record[] filesAndUd) {
										final Record lRecord = new Record();
										lRecord.setAttribute("fascicoloOriginale", getRecordToSave());
										if (hasActionFirmaAutomatica()) {
											firmaConFirmaAutomatica(signedFiles, filesAndUd, new FirmaMultiplaCallback() {
												
												@Override
												public void execute(Map<String, Record> filesFirmatiFirmaAutomatica, Record[] filesAndUd) {
													// Aggiorno i file che erano stati firmati firmati nel precedente passo di firma
													Set<String> filesFirmatiFirmaAutomaticaKeySet = filesFirmatiFirmaAutomatica.keySet();
													for (String idFileFirmatoFirmaAutomatica : filesFirmatiFirmaAutomaticaKeySet) {
														signedFiles.put(idFileFirmatoFirmaAutomatica, filesFirmatiFirmaAutomatica.get(idFileFirmatoFirmaAutomatica));										
													}
													Record lRecordFileFirmati = new Record();
													lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
													lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
													aggiornaFileFirmati(lRecord, callback);
												}
											}, null);
										} else {
											Record lRecordFileFirmati = new Record();
											lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
											lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
											aggiornaFileFirmati(lRecord, callback);
										}
									}
								});
							} else {
								if(callback != null) {
									callback.execute(getRecordToSave());			
								}
							}
						}
					});					
				}
			}
		});		
	}
	
	protected void timbraFile(final RecordList listaRecordModelliGeneratiDaFirmare, final ServiceCallback<Record> callback) {
		getFileDaFirmare(true, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordFileDaFirmare = response.getData()[0];					
					Record[] filesDaFirmare = lRecordFileDaFirmare.getAttributeAsRecordArray("files");									
					Record lRecord = new Record();
					lRecord.setAttribute("fascicoloOriginale", getRecordToSave());
					Record lRecordFileTimbrati = new Record();
					lRecordFileTimbrati.setAttribute("files", filesDaFirmare);
					lRecord.setAttribute("fileFirmati", lRecordFileTimbrati);					
					aggiornaFileTimbrati(lRecord, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record recordTimbraFile) {	
							RecordList listaFilesDaFirmare = new RecordList();
							if(listaRecordModelliGeneratiDaFirmare != null) {						
								for(int i = 0; i < listaRecordModelliGeneratiDaFirmare.getLength(); i++) {							
									String uriFileGenerato = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("uriFileGenerato");
									InfoFileRecord infoFileGenerato = InfoFileRecord.buildInfoFileString(JSON.encode(listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsRecord("infoFileGenerato").getJsObj()));							
									String nomeFileModello = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nomeFile") + ".pdf";
									boolean flgDaFirmare = listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare");							
									if (flgDaFirmare) {								
										Record record = new Record();
										record.setAttribute("idFile", "fileGenerato" + uriFileGenerato); //ATTENZIONE, la parola "fileGenerato" viene usata nella successiva action FirmaAutomatica per discriminare i file da non rifirmare 
										record.setAttribute("uri", uriFileGenerato);
										record.setAttribute("nomeFile", nomeFileModello);
										record.setAttribute("infoFile", infoFileGenerato);
										listaFilesDaFirmare.add(record);				
									}		
								}
							}					
							if(listaFilesDaFirmare.getLength() > 0) {
								// Leggo gli eventuali parametri per forzare il tipo di firma
								String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
								String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
								FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, listaFilesDaFirmare.toArray(), new FirmaMultiplaCallback() {
						
									@Override
									public void execute(final Map<String, Record> signedFiles, Record[] filesAndUd) {
										final Record lRecord = new Record();
										lRecord.setAttribute("fascicoloOriginale", getRecordToSave());
										if (hasActionFirmaAutomatica()) {
											firmaConFirmaAutomatica(signedFiles, filesAndUd, new FirmaMultiplaCallback() {
												
												@Override
												public void execute(Map<String, Record> filesFirmatiFirmaAutomatica, Record[] filesAndUd) {
													// Aggiorno i file che erano stati firmati firmati nel precedente passo di firma
													Set<String> filesFirmatiFirmaAutomaticaKeySet = filesFirmatiFirmaAutomatica.keySet();
													for (String idFileFirmatoFirmaAutomatica : filesFirmatiFirmaAutomaticaKeySet) {
														signedFiles.put(idFileFirmatoFirmaAutomatica, filesFirmatiFirmaAutomatica.get(idFileFirmatoFirmaAutomatica));										
													}
													Record lRecordFileFirmati = new Record();
													lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
													lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
													aggiornaFileFirmati(lRecord, callback);
												}
											}, null);
										} else {
											Record lRecordFileFirmati = new Record();
											lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
											lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
											aggiornaFileFirmati(lRecord, callback);
										}
									}
								});
							} else {
								if(callback != null) {
									callback.execute(getRecordToSave());			
								}
							}			
						}
					});					
				}
			}
		});		
	}

	protected void firmaConFirmaAutomatica(Map<String, Record> signedFiles, final Record[] filesAndUd, final FirmaMultiplaCallback callbackFirmaEseguita, final FirmaMultiplaNonEseguitaCallback callbackFirmaNonEseguita) {
		// Devo rifirmare tutti i file firmati al passo di firma, tranne quelli generati da modello
		Set<String> signedFileKeySet = signedFiles.keySet();
		RecordList recordList = new RecordList();
		for (String idFile : signedFileKeySet) {
			if (idFile != null && !idFile.startsWith("fileGenerato")) {
				recordList.add(signedFiles.get(idFile));
			}
		}
		// Verifico se ho file da firmare con la firma automatica
		if (!recordList.isEmpty()) {
			String userIdFirmatario = recordEvento != null ? recordEvento.getAttribute("docActionsFirmaAutomaticaUseridFirmatario") : null;
			String firmaInDelega = recordEvento != null ? recordEvento.getAttribute("docActionsFirmaAutomaticaFirmaInDelega") : null;
			String password = recordEvento != null ? recordEvento.getAttribute("docActionsFirmaAutomaticaPassword") : null;
			String providerFirma = recordEvento != null ? recordEvento.getAttribute("docActionsFirmaAutomaticaProvider") : null;
			String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");
			String username;
			String usernameDelegante;
			if (firmaInDelega != null && !"".equalsIgnoreCase(firmaInDelega)) {
				username =  firmaInDelega;
				usernameDelegante = userIdFirmatario;
			} else {
				username = userIdFirmatario;
				usernameDelegante = "";
			}
			FirmaUtility.firmaMultiplaHsmAutomatica(true, recordList.toArray(), username, usernameDelegante, password, providerFirma, hsmTipoFirmaAtti, callbackFirmaEseguita, callbackFirmaNonEseguita);
		 } else {
			 // Proseguo normalmente
			 callbackFirmaEseguita.execute(signedFiles, filesAndUd);
		 }
	}
	
	protected void aggiornaFileFirmati(Record record, final ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TaskDettFascicoloGenDataSource");
		lGwtRestService.addParam("idTaskCorrente", getIdTaskCorrente());
		lGwtRestService.executecustom("aggiornaFileFirmati", record, new DSCallback() {
		
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];					
					editRecord(lRecord);
					// dopo l'editRecord devo risettare il mode del dettaglio, perchè altrimenti sulle replicableItem compaiono i bottoni di remove delle righe anche quando non dovrebbero
					if (isEseguibile()) {
						if (isReadOnly()) {
							readOnlyMode();
						} else {
							editMode();
						}
					} else {
						viewMode();
					}
					if(callback != null) {
						callback.execute(lRecord);
					}
				}
			}
		});
	}
	
	protected void aggiornaFileTimbrati(Record record, final ServiceCallback<Record> callback) {		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TaskDettFascicoloGenDataSource");
		lGwtRestService.addParam("idTaskCorrente", getIdTaskCorrente());
		lGwtRestService.executecustom("aggiornaFile", record, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {				
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					editRecord(lRecord);
					// dopo l'editRecord devo risettare il mode del dettaglio, perchè altrimenti sulle replicableItem compaiono i bottoni di remove delle righe anche quando non dovrebbero
					if (isEseguibile()) {
						if (isReadOnly()) {
							readOnlyMode();
						} else {
							editMode();
						}
					} else {
						viewMode();
					}
					if(callback != null) {
						callback.execute(lRecord);
					}				
				}
			}
		});
	}
		
	public void unioneFileAndReturn(boolean fileDaTimbrare, final RecordList listaRecordModelliGeneratiDaFirmare) {
		final Record lRecord = getRecordToSave(null);
		GWTRestService<Record, Record> lTaskDettFascicoloGenDataSource = new GWTRestService<Record, Record>("TaskDettFascicoloGenDataSource");
		lTaskDettFascicoloGenDataSource.addParam("fileDaTimbrare", fileDaTimbrare ? "true" : "");		
		lTaskDettFascicoloGenDataSource.addParam("idTipoDocFileUnione", recordEvento != null ? recordEvento.getAttribute("unioneFileIdTipoDoc") : null);
		lTaskDettFascicoloGenDataSource.addParam("nomeTipoDocFileUnione", recordEvento != null ? recordEvento.getAttribute("unioneFileNomeTipoDoc") : null);
		lTaskDettFascicoloGenDataSource.addParam("nomeFileUnione", recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFile") : null);
		lTaskDettFascicoloGenDataSource.addParam("esitoTask", attrEsito != null ? attrEsito.getAttribute("valore") : null);
		lTaskDettFascicoloGenDataSource.executecustom("creaDocumentoUnioneFile", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordUnioneFile = response.getData()[0];
					previewFileUnioneWithFirmaAndCallback(recordUnioneFile, listaRecordModelliGeneratiDaFirmare);
				}
			}
		});
	}
	
	protected void aggiungiFileUnione(String idUd, String idDoc, String uri, String nomeFile, InfoFileRecord infoFile, final ServiceCallback<Record> callback) {				
		if(documentiIstruttoriaForm != null) {		
			RecordList listaDocumentiIstruttoria = documentiIstruttoriaForm.getValuesAsRecord().getAttributeAsRecordList("listaDocumentiIstruttoria");
			if(listaDocumentiIstruttoria == null || listaDocumentiIstruttoria.getLength() == 0) { 
				listaDocumentiIstruttoria = new RecordList();	
			}			
			Record lRecordFileUnione = new Record();
			lRecordFileUnione.setAttribute("idUdAppartenenza", idUd);
			lRecordFileUnione.setAttribute("idDocAllegato", idDoc);			
			lRecordFileUnione.setAttribute("isChanged", true);
			lRecordFileUnione.setAttribute("nomeFileAllegato", nomeFile);
			lRecordFileUnione.setAttribute("uriFileAllegato", uri);								
			// TODO quali valori bisogna mettere?
			lRecordFileUnione.setAttribute("idTipoFileAllegato", recordEvento != null ? recordEvento.getAttribute("unioneFileIdTipoDoc") : null);
			lRecordFileUnione.setAttribute("descTipoFileAllegato", recordEvento != null ? recordEvento.getAttribute("unioneFileNomeTipoDoc") : null);
			lRecordFileUnione.setAttribute("listaTipiFileAllegato", recordEvento != null ? recordEvento.getAttribute("unioneFileIdTipoDoc") : null);
			lRecordFileUnione.setAttribute("descrizioneFileAllegato", recordEvento != null ? recordEvento.getAttribute("unioneFileDescrizione") : null);
			lRecordFileUnione.setAttribute("nomeFileAllegatoTif", "");
			lRecordFileUnione.setAttribute("uriFileAllegatoTif", "");				
			lRecordFileUnione.setAttribute("remoteUri", false);				
			lRecordFileUnione.setAttribute("nomeFileVerPreFirma", nomeFile);
			lRecordFileUnione.setAttribute("uriFileVerPreFirma", uri);				
			lRecordFileUnione.setAttribute("infoFile", infoFile);
			lRecordFileUnione.setAttribute("improntaVerPreFirma",  infoFile.getImpronta());
			lRecordFileUnione.setAttribute("flgParteDispositivo",  false);
			listaDocumentiIstruttoria.addAt(lRecordFileUnione, 0);
			Record lRecordForm = new Record();
			lRecordForm.setAttribute("listaDocumentiIstruttoria", listaDocumentiIstruttoria);
			documentiIstruttoriaForm.setValues(lRecordForm.toMap());
			documentiIstruttoriaItem.resetCanvasChanged();	
			if(detailSectionDocumentiIstruttoria != null) {
				detailSectionDocumentiIstruttoria.openIfhasValue();
			}								
		}	
		if(callback != null) {
			callback.execute(null);
		}
	}
	
	public void versionaFileUnione(String idUd, String idDoc, String uri, String nomeFile, InfoFileRecord infoFile, final ServiceCallback<Record> callback) {
		Record lRecordFileUnione = new Record();
		lRecordFileUnione.setAttribute("nomeFile", nomeFile);
		lRecordFileUnione.setAttribute("uri", uri);												
		lRecordFileUnione.setAttribute("infoFile", infoFile);
		lRecordFileUnione.setAttribute("idUd", idUd);
		lRecordFileUnione.setAttribute("idDoc", idDoc);		
		GWTRestService<Record, Record> lTaskDettFascicoloGenDataSource = new GWTRestService<Record, Record>("TaskDettFascicoloGenDataSource");
		lTaskDettFascicoloGenDataSource.executecustom("versionaDocumentoUnioneFile", lRecordFileUnione, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if(callback != null) {
						callback.execute(null);
					}
				}
			}
		});
	}
	
	public void cancellaFileUnione(String idUd) {
		Record lRecordFileUnione = new Record();
		lRecordFileUnione.setAttribute("idUd", idUd);
		GWTRestService<Record, Record> lTaskDettFascicoloGenDataSource = new GWTRestService<Record, Record>("TaskDettFascicoloGenDataSource");
		lTaskDettFascicoloGenDataSource.executecustom("cancellaDocumentoUnioneFile", lRecordFileUnione, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				reload();
			}
		});
	}
	
	public void previewFileUnioneWithFirmaAndCallback(final Record record, final RecordList listaRecordModelliGeneratiDaFirmare) {		
		final String idUd = record.getAttribute("idUd");	
		final String idDoc = record.getAttribute("idDoc");	
		final String uriFileUnione = record.getAttribute("uri");	
		final String nomeFileUnione = record.getAttribute("nomeFile");		
		final InfoFileRecord infoFileUnione = InfoFileRecord.buildInfoFileString(JSON.encode(record.getAttributeAsRecord("infoFile").getJsObj()));				
		new PreviewWindowWithCallback(uriFileUnione, false, infoFileUnione, "FileToExtractBean", nomeFileUnione, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordPreview) {				
				final ServiceCallback<Record> callback = new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						salvaDati(false, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								callbackSalvaDati(object, new ServiceCallback<Record>() {
									
									@Override
									public void execute(Record object) {
										// In caso di errore nell'avanzamento del task
										cancellaFileUnione(idUd);
									}
								});
							}
						}, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								// In caso di errore nel salvataggio del fascicolo
								cancellaFileUnione(idUd);
							}
						});
					}
				};				
				if (hasActionFirma()) {
					aggiungiFileUnione(idUd, idDoc, uriFileUnione, nomeFileUnione, infoFileUnione, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							RecordList listaFilesDaFirmare = new RecordList();						
							Record lFileUnione = new Record();
							lFileUnione.setAttribute("idFile", "fileUnione" + uriFileUnione);
							lFileUnione.setAttribute("uri", uriFileUnione);
							lFileUnione.setAttribute("nomeFile", nomeFileUnione);
							lFileUnione.setAttribute("infoFile", infoFileUnione);
							lFileUnione.setAttribute("isFilePrincipaleAtto", true);
							listaFilesDaFirmare.add(lFileUnione);			
							if(listaRecordModelliGeneratiDaFirmare != null) {
								for(int i = 0; i < listaRecordModelliGeneratiDaFirmare.getLength(); i++) {							
									String uriFileGenerato = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("uriFileGenerato");
									InfoFileRecord infoFileGenerato = InfoFileRecord.buildInfoFileString(JSON.encode(listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsRecord("infoFileGenerato").getJsObj()));							
									String nomeFileModello = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nomeFile") + ".pdf";
									boolean flgDaFirmare = listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare");							
									if (flgDaFirmare) {								
										Record record = new Record();
										record.setAttribute("idFile", "fileGenerato" + uriFileGenerato); //ATTENZIONE, la parola "fileGenerato" viene usata nella successiva action FirmaAutomatica per discriminare i file da non rifirmare
										record.setAttribute("uri", uriFileGenerato);
										record.setAttribute("nomeFile", nomeFileModello);
										record.setAttribute("infoFile", infoFileGenerato);
										listaFilesDaFirmare.add(record);				
									}		
								}		
							}
							// Leggo gli eventuali parametri per forzare il tipo d firma
							String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
							String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
							FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, listaFilesDaFirmare.toArray(), new FirmaMultiplaCallback() {
								@Override
								public void execute(final Map<String, Record> signedFiles, Record[] filesAndUd) {
									final Record lRecord = new Record();
									lRecord.setAttribute("fascicoloOriginale", getRecordToSave());
									if (hasActionFirmaAutomatica()) {
										firmaConFirmaAutomatica(signedFiles, filesAndUd, new FirmaMultiplaCallback() {
											
											@Override
											public void execute(Map<String, Record> filesFirmatiFirmaAutomatica, Record[] filesAndUd) {
												// Aggiorno i file che erano stati firmati firmati nel precedente passo di firma
												Set<String> filesFirmatiFirmaAutomaticaKeySet = filesFirmatiFirmaAutomatica.keySet();
												for (String idFileFirmatoFirmaAutomatica : filesFirmatiFirmaAutomaticaKeySet) {
													signedFiles.put(idFileFirmatoFirmaAutomatica, filesFirmatiFirmaAutomatica.get(idFileFirmatoFirmaAutomatica));										
												}
												Record lRecordFileFirmati = new Record();
												lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
												lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
												aggiornaFileFirmati(lRecord, callback);														
											}
										}, null);
									} else {
										Record lRecordFileFirmati = new Record();
										lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[]{}));
										lRecord.setAttribute("fileFirmati", lRecordFileFirmati);								
										aggiornaFileFirmati(lRecord, callback);													
									}
								}
							});
						}
					});
				} else {
					firmaAggiornaFileGenerati(listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							aggiungiFileUnione(idUd, idDoc, uriFileUnione, nomeFileUnione, infoFileUnione, callback);
						}
					});			
				}
			}
		}) {
			
			@Override
			public void manageCloseClick() {
				super.manageCloseClick();
				cancellaFileUnione(idUd);
			}
		};		
	}
	
	public void saveAndGoWithListaModelliGenAutomatica(boolean flgAfterSalva, final RecordList listaRecordModelli, final String esito, final ServiceCallback<RecordList> callback) {
		if (listaRecordModelli != null) {
			if(!flgAfterSalva) {
				// qui la chiamata per salvare i valori dei tab dinamici la devo lasciare perchè non c'è un salvataggio provvisorio che me li salva prima
				salvaAttributiDinamiciFolder(false, rowidFolder, activityName, esito, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						compilazioneAutomaticaListaModelliPdf(listaRecordModelli, esito, callback);
					}
				});				
			} else {
				salvaAttributiDinamiciFolder(false, rowidFolder, activityName, esito, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						compilazioneAutomaticaListaModelliPdf(listaRecordModelli, esito, callback);
					}
				});
			}
		} else if(callback != null) {
			callback.execute(null);
		}
	}
	
	@Override
	public Record getRecordToSave() {
		
		final Record lRecordToSave = super.getRecordToSave();		
		
		// Attributi dinamici folder
		if (attributiAddFolderDetails != null) {
			lRecordToSave.setAttribute("rowidFolder", rowidFolder);
			lRecordToSave.setAttribute("valori", getAttributiDinamiciFolder());
			lRecordToSave.setAttribute("tipiValori", getTipiAttributiDinamiciFolder());
//			lRecordToSave.setAttribute("colonneListe", getColonneListeAttributiDinamiciFolder());
		}
		
		return lRecordToSave;
	}
	
	public void compilazioneAutomaticaListaModelliPdf(final RecordList listaRecordModelli, final String esito, final ServiceCallback<RecordList> callback) {

		if (listaRecordModelli != null && listaRecordModelli.getLength() > 0) {

			Record lRecordCompilaModello = new Record();
			lRecordCompilaModello.setAttribute("processId", idProcess);
			lRecordCompilaModello.setAttribute("idFolder", idFolder);
			lRecordCompilaModello.setAttribute("listaRecordModelli", listaRecordModelli);
			lRecordCompilaModello.setAttribute("dettaglioBean", getRecordToSave());
			
			GWTRestService<Record, Record> lTaskDettFascicoloGenDataSource = new GWTRestService<Record, Record>("TaskDettFascicoloGenDataSource");
			lTaskDettFascicoloGenDataSource.addParam("esitoTask", esito);
			Layout.showWaitPopup("Generazione automatica file in corso...");	
			lTaskDettFascicoloGenDataSource.executecustom("compilazioneAutomaticaListaModelliPdf", lRecordCompilaModello, new DSCallback() {
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {						
						RecordList listaRecordModelliGenerati = response.getData()[0].getAttributeAsRecordList("listaRecordModelli");
						Record dettaglioBean = response.getData()[0].getAttributeAsRecord("dettaglioBean");		
						// Aggiorno i documenti istruttoria
						if(dettaglioBean != null) {
							Record lRecordForm = new Record();
							lRecordForm.setAttribute("listaDocumentiIstruttoria", dettaglioBean.getAttributeAsRecordList("listaDocumentiIstruttoria"));
							documentiIstruttoriaForm.setValues(lRecordForm.toMap());
							if(documentiIstruttoriaItem != null) {
								documentiIstruttoriaItem.resetCanvasChanged();
							}
						}
						if(listaRecordModelliGenerati != null && listaRecordModelliGenerati.getLength() > 0) {
							previewFileGenerati(0, listaRecordModelliGenerati, new ServiceCallback<RecordList>() {
								
								@Override
								public void execute(final RecordList listaRecordModelliGenerati) {
									if(listaRecordModelliGenerati != null) {										
										boolean hasFilesDaFirmare = false;
										for(int i = 0; i < listaRecordModelliGenerati.getLength(); i++) {											
											boolean flgDaFirmare = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare");											
											if (flgDaFirmare) {
												hasFilesDaFirmare = true;			
											}		
										}										
										if(hasFilesDaFirmare) {
											aggiungiListaModelliADocumentiIstruttoria(listaRecordModelliGenerati, null, new ServiceCallback<Record>() {
												
												@Override
												public void execute(Record object) {
													if(callback != null) {
														callback.execute(listaRecordModelliGenerati);
													}
												}
											});											
										} else {
											aggiungiListaModelliADocumentiIstruttoria(listaRecordModelliGenerati, null, new ServiceCallback<Record>() {
												
												@Override
												public void execute(Record object) {
													if(callback != null) {
														callback.execute(null);
													}
												}
											});		
										}									
									} else {
										if(callback != null) {
											callback.execute(null);
										}
									}									
								}
							});
						}						
					}
				}
			});
		} else {
			if(callback != null) {
				callback.execute(null);
			}
		}
	}
	
	protected void previewFileGenerati(final int i, final RecordList listaRecordModelliGenerati, final ServiceCallback<RecordList> callback) {		
		
		if(i >= 0 && listaRecordModelliGenerati != null && listaRecordModelliGenerati.getLength() > 0 && i < listaRecordModelliGenerati.getLength()) {				
		
			final Record recordModello = listaRecordModelliGenerati.get(i);
			
			final String uriFileGenerato = recordModello.getAttribute("uriFileGenerato");
			final InfoFileRecord infoFileGenerato = InfoFileRecord.buildInfoFileString(JSON.encode(recordModello.getAttributeAsRecord("infoFileGenerato").getJsObj()));
			final String nomeFileModello = recordModello.getAttribute("nomeFile") + ".pdf";
			
			boolean flgSkipAnteprima = recordModello.getAttributeAsBoolean("flgSkipAnteprima") != null && recordModello.getAttributeAsBoolean("flgSkipAnteprima");						
			if(!flgSkipAnteprima) {
				new PreviewWindowWithCallback(uriFileGenerato, false, infoFileGenerato, "FileToExtractBean",	nomeFileModello, new ServiceCallback<Record>() {
	
					@Override
					public void execute(Record object) {									
						previewFileGenerati(i + 1, listaRecordModelliGenerati, callback);
					}
				}) {
					
					@Override
					public boolean hideAnnullaButton() {
						// se è prevista la numerazione del documento per l'esito scelto e la rollback NON è attiva per tutti i documenti fascicolo o per lo specifico registro del documento da generare allora devo nascondere il bottone Annulla della preview
						if(recordModello.getAttribute("siglaNumDaDare") != null && !"".equals(recordModello.getAttribute("siglaNumDaDare")) && !AurigaLayout.isAttivaRollbackNumeerazioneDefDocFascicolo(recordModello.getAttribute("siglaNumDaDare"))) {
							return true;
						} else {
							return false;
						}
					}
					
					@Override
					public void manageCloseClick() {
						super.manageCloseClick();
						if(AurigaLayout.isAttivaRollbackNumeerazioneDefDocFascicolo(recordModello.getAttribute("siglaNumDaDare"))) {
							rollbackNumerazioneDefAtti(recordModello);
						}
					}
					
					@Override
					public void manageOkClickAndDestroy() {
						// se ho solo una preview da mostrare (quindi ho un solo file da generare e non ho l'unione file)
						if(listaRecordModelliGenerati.getLength() == 1 && !hasActionUnioneFile()) {
							if(recordModello.getAttribute("siglaNumDaDare") != null && !"".equals(recordModello.getAttribute("siglaNumDaDare")) && !AurigaLayout.isAttivaRollbackNumeerazioneDefDocFascicolo(recordModello.getAttribute("siglaNumDaDare"))) {
								super.manageOkClickAndDestroy();
							} else {
								previewWindowToCloseAfterFirma = this;
								super.manageOkClick(); //non chiudo subito la preview ma solo dopo che è stata effettuata la firma con successo, così se va in errore posso fare Annulla con la rollback
							}
						} else {
							super.manageOkClickAndDestroy();
						}
					}
				};
			} else {
				previewFileGenerati(i + 1, listaRecordModelliGenerati, callback);
			}
		
		} else if(callback != null) {
			callback.execute(listaRecordModelliGenerati);
		}
	}
	
	public void rollbackNumerazioneDefAtti(Record record) {
		final GWTRestService<Record, Record> lTaskDettFascicoloGenDataSource = new GWTRestService<Record, Record>("TaskDettFascicoloGenDataSource");
		lTaskDettFascicoloGenDataSource.executecustom("rollbackNumerazioneDefAtti", record, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = dsResponse.getData()[0];
					if(lRecord.getAttribute("esitoRollbackNumDefAtti") != null && "OK".equalsIgnoreCase(lRecord.getAttribute("esitoRollbackNumDefAtti"))) {
						reload();
					} else {
						// se va in errore la rollback della numerazione definitiva atto
						SC.say("Si è verificato un errore durante l'annullamento della numerazione assegnata al documento appena generato", new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								reload();
							}
						});
					}
				}
			}
		});		
	}
	
	protected void firmaAggiornaFileGenerati(final RecordList listaRecordModelliGenerati, final ServiceCallback<Record> callback) {		
		if(listaRecordModelliGenerati != null) {		
			RecordList listaFilesDaFirmare = new RecordList();
			for(int i = 0; i < listaRecordModelliGenerati.getLength(); i++) {				
				String uriFileGenerato = listaRecordModelliGenerati.get(i).getAttribute("uriFileGenerato");
				InfoFileRecord infoFileGenerato = InfoFileRecord.buildInfoFileString(JSON.encode(listaRecordModelliGenerati.get(i).getAttributeAsRecord("infoFileGenerato").getJsObj()));				
				String nomeFileModello = listaRecordModelliGenerati.get(i).getAttribute("nomeFile") + ".pdf";
				String idTipoModello = listaRecordModelliGenerati.get(i).getAttribute("idTipoDoc");
				boolean flgDaFirmare = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare");				
				if (flgDaFirmare) {
					Record record = new Record();
					record.setAttribute("idFile", idTipoModello); // uso questo come campo identificativo del record per poi riaggiornare l'altra lista
					record.setAttribute("uri", uriFileGenerato);
					record.setAttribute("nomeFile", nomeFileModello);
					record.setAttribute("infoFile", infoFileGenerato);
					listaFilesDaFirmare.add(record);				
				}		
			}
			if(listaFilesDaFirmare.getLength() > 0) {
				// Leggo gli eventuali parametri per forzare il tipo d firma
				String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
				String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");
				FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, listaFilesDaFirmare.toArray(), new FirmaMultiplaCallback() {
	
					@Override
					public void execute(final Map<String, Record> signedFiles, Record[] filesAndUd) {		
						if (hasActionFirmaAutomatica()) {
							firmaConFirmaAutomatica(signedFiles, filesAndUd, new FirmaMultiplaCallback() {
								
								@Override
								public void execute(Map<String, Record> filesFirmatiFirmaAutomatica, Record[] filesAndUd) {
									// Aggiorno i file che erano stati firmati firmati nel precedente passo di firma
									Set<String> filesFirmatiFirmaAutomaticaKeySet = filesFirmatiFirmaAutomatica.keySet();
									for (String idFileFirmatoFirmaAutomatica : filesFirmatiFirmaAutomaticaKeySet) {
										signedFiles.put(idFileFirmatoFirmaAutomatica, filesFirmatiFirmaAutomatica.get(idFileFirmatoFirmaAutomatica));										
									}
									aggiungiListaModelliADocumentiIstruttoria(listaRecordModelliGenerati, signedFiles, callback);
								}
							}, null);
						} else {
							aggiungiListaModelliADocumentiIstruttoria(listaRecordModelliGenerati, signedFiles, callback);
						}
					}
				});	
			} else {
				aggiungiListaModelliADocumentiIstruttoria(listaRecordModelliGenerati, null, callback);		
			}		
		} else {
			if(callback != null) {
				callback.execute(null);
			}
		}		
	}
	
	protected void aggiungiListaModelliADocumentiIstruttoria(RecordList listaRecordModelliGenerati, Map<String, Record> signedFiles, ServiceCallback<Record> callback) {
		if (documentiIstruttoriaForm != null) {
			
			RecordList listaDocumentiIstruttoria = documentiIstruttoriaForm.getValuesAsRecord().getAttributeAsRecordList("listaDocumentiIstruttoria");
		
			for(int i = 0; i < listaRecordModelliGenerati.getLength(); i++) {
				
				String estremiProtUd = listaRecordModelliGenerati.get(i).getAttribute("estremiProtUd");
				String descrizioneFileAllegato = listaRecordModelliGenerati.get(i).getAttribute("descrizione");
				String nomeFileAllegato = listaRecordModelliGenerati.get(i).getAttribute("nomeFile") + ".pdf";
				String uriFileAllegato = listaRecordModelliGenerati.get(i).getAttribute("uriFileGenerato");
				String infoFileAllegato = JSON.encode(listaRecordModelliGenerati.get(i).getAttributeAsRecord("infoFileGenerato").getJsObj());						
				InfoFileRecord infoAllegato = InfoFileRecord.buildInfoFileString(infoFileAllegato);						
				
				String idTipoModello = listaRecordModelliGenerati.get(i).getAttribute("idTipoDoc");
				String nomeTipoModello = listaRecordModelliGenerati.get(i).getAttribute("nomeTipoDoc");
				boolean flgDaFirmare = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare");
						
				if (flgDaFirmare && signedFiles != null) {							
					Record lRecordFileFirmato = signedFiles.get(idTipoModello);
					if(lRecordFileFirmato != null) {
						nomeFileAllegato = lRecordFileFirmato.getAttribute("nomeFile");
						uriFileAllegato = lRecordFileFirmato.getAttribute("uri");
						infoAllegato = InfoFileRecord.buildInfoFileString(JSON.encode(lRecordFileFirmato.getAttributeAsRecord("infoFile").getJsObj()));
					}
				}
				
				int posModello = getPosDocumentoFromTipoSenzaFileOConFileGenDaModelloDaFirmareNonFirmato(idTipoModello, listaDocumentiIstruttoria);
				
				Record lRecordModello = new Record();		
				if (posModello != -1) {
					lRecordModello = listaDocumentiIstruttoria.get(posModello);
				}
				
				// mi salvo l'informazione che è un file generato da modello
				lRecordModello.setAttribute("flgGenAutoDaModello", true);
				
				// mi salvo l'informazione che è un file generato da modello da firmare
				lRecordModello.setAttribute("flgGenDaModelloDaFirmare", flgDaFirmare);
				
//				boolean flgParere = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgParere") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgParere");									
//				boolean flgParteDispositivo = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgParteDispositivo") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgParteDispositivo");									
//				boolean flgNoPubbl = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgNoPubbl") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgNoPubbl");									
//				boolean flgPubblicaSeparato = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgPubblicaSeparato") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgPubblicaSeparato");									
//				lRecordModello.setAttribute("flgParere", flgParere);
//				if(flgParere) {
//					lRecordModello.setAttribute("flgParteDispositivo", false);
//					lRecordModello.setAttribute("flgNoPubblAllegato", flgNoPubbl);
//					lRecordModello.setAttribute("flgPubblicaSeparato", true);
//				} else {
//					lRecordModello.setAttribute("flgParteDispositivo", flgParteDispositivo);
//					if(!flgParteDispositivo) {
//						lRecordModello.setAttribute("flgNoPubblAllegato", true);
//						lRecordModello.setAttribute("flgPubblicaSeparato", false);
//						lRecordModello.setAttribute("flgDatiSensibili", false);
//					} else {
//						lRecordModello.setAttribute("flgNoPubblAllegato", flgNoPubbl);	
//						lRecordModello.setAttribute("flgPubblicaSeparato", flgPubblicaSeparato);
//					}
//				}
				
				lRecordModello.setAttribute("estremiProtUd", estremiProtUd);
				
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
					if (listaDocumentiIstruttoria == null || listaDocumentiIstruttoria.getLength() == 0) {
						listaDocumentiIstruttoria = new RecordList();
					}
					listaDocumentiIstruttoria.addAt(lRecordModello, 0);
				} else {
					listaDocumentiIstruttoria.set(posModello, lRecordModello);
				}
			}
				
			Record lRecordForm = new Record();
			lRecordForm.setAttribute("listaDocumentiIstruttoria", listaDocumentiIstruttoria);
			documentiIstruttoriaForm.setValues(lRecordForm.toMap());
								
			if(documentiIstruttoriaItem != null) {
				documentiIstruttoriaItem.resetCanvasChanged();
			}

//			if (detailSectionDocumentiIstruttoria != null) {
//				detailSectionDocumentiIstruttoria.openIfhasValue();
//			}	
			
		}
		
		if(callback != null) {
			callback.execute(new Record());
		}	
	}
	
	// Recupero la posizione del documento di quel tipo
	public int getPosDocumentoFromTipo(String idTipoModello, RecordList listaDocumenti) {
		if (listaDocumenti != null) {
			for (int i = 0; i < listaDocumenti.getLength(); i++) {
				Record allegato = listaDocumenti.get(i);
				if (allegato.getAttribute("listaTipiFileAllegato") != null && allegato.getAttribute("listaTipiFileAllegato").equalsIgnoreCase(idTipoModello)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public int getPosDocumentoFromTipoSenzaFileOConFileGenDaModelloDaFirmareNonFirmato(String idTipoModello, RecordList listaDocumenti) {
		if (listaDocumenti != null) {
			for (int i = 0; i < listaDocumenti.getLength(); i++) {
				Record allegato = listaDocumenti.get(i);
				if (allegato.getAttribute("listaTipiFileAllegato") != null && allegato.getAttribute("listaTipiFileAllegato").equalsIgnoreCase(idTipoModello)) {
					InfoFileRecord infoFile = allegato.getAttributeAsRecord("infoFile") != null ? new InfoFileRecord(allegato.getAttributeAsRecord("infoFile")) : null;					
					boolean flgGenDaModelloDaFirmare = allegato.getAttributeAsBoolean("flgGenDaModelloDaFirmare") != null && allegato.getAttributeAsBoolean("flgGenDaModelloDaFirmare");
					boolean flgGenDaModelloFirmato = infoFile != null && infoFile.isFirmato();
					if (allegato.getAttribute("uriFileAllegato") == null || "".equals(allegato.getAttribute("uriFileAllegato"))) {
						return i;					
					} else if (flgGenDaModelloDaFirmare && !flgGenDaModelloFirmato) {
						return i;
					}
				}
			}
		}
		return -1;
	}		
			
	protected void callbackSalvaDati(Record object) {
		callbackSalvaDati(object, null);
	}
	
	protected void callbackSalvaDati(Record object, final ServiceCallback<Record> errorCallback) {
		
		idEvento = object.getAttribute("idEvento");
		
		final RecordList listaRecordModelliXEsitoPostAvanzamentoFlusso = getListaRecordModelliXEsitoPostAvanzamentoFlusso(object.getAttribute("esito"));		
		
		final Record lRecordSalvaTask = new Record();
		lRecordSalvaTask.setAttribute("instanceId", instanceId);
		lRecordSalvaTask.setAttribute("activityName", activityName);
		lRecordSalvaTask.setAttribute("idProcess", idProcess);
		lRecordSalvaTask.setAttribute("idEventType", idTipoEvento);
		lRecordSalvaTask.setAttribute("idEvento", idEvento);
		lRecordSalvaTask.setAttribute("idFolder", idFolder);		
		lRecordSalvaTask.setAttribute("note", messaggio);
		lRecordSalvaTask.setAttribute("dettaglioBean", getRecordToSave());
		lRecordSalvaTask.setAttribute("listaRecordModelli", listaRecordModelliXEsitoPostAvanzamentoFlusso);
		
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
				} else if(errorCallback != null){
					errorCallback.execute(lRecordSalvaTask);
				} else {
					reload();
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

	public void caricaAttributiDinamiciFolder(final String nomeFlussoWF, final String processNameWF, final String activityName, final String folderType,
			final String rowidFolder) {
		if (folderType != null && !"".equals(folderType)) {
			Record lRecordLoad = new Record();
			lRecordLoad.setAttribute("idFolderType", folderType);
			new GWTRestService<Record, Record>("LoadComboGruppiAttrCustomTipoFolderDataSource").call(lRecordLoad, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					final boolean isReload = (attributiAddFolderTabs != null && attributiAddFolderTabs.size() > 0);
					attributiAddFolderTabs = (LinkedHashMap<String, String>) object.getAttributeAsMap("gruppiAttributiCustomTipoFolder");
					attributiAddFolderLayouts = new HashMap<String, VLayout>();
					attributiAddFolderDetails = new HashMap<String, AttributiDinamiciDetail>();
					if (attributiAddFolderTabs != null && attributiAddFolderTabs.size() > 0) {
						GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
						lGwtRestService.addParam("flgNomeAttrConSuff", "true");
						lGwtRestService.addParam("nomeFlussoWF", nomeFlussoWF);
						lGwtRestService.addParam("processNameWF", processNameWF);
						lGwtRestService.addParam("activityNameWF", activityName);
						Record lAttributiDinamiciRecord = new Record();
						lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_FOLDER");
						lAttributiDinamiciRecord.setAttribute("rowId", rowidFolder);
						lAttributiDinamiciRecord.setAttribute("tipoEntita", folderType);
						lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
								if (attributiAdd != null && !attributiAdd.isEmpty()) {
									boolean hasTabs = false;
									for (final String key : attributiAddFolderTabs.keySet()) {
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
												attributiAddFolderDetails.put(key, detail);

												VLayout layoutTabDatiPrincipali = (VLayout) tabDatiPrincipali.getPane();
												VLayout layout = (VLayout) layoutTabDatiPrincipali.getMembers()[0];

												attributiAddFolderLayouts.put(key, layout);
												int pos = 0;
												for (Canvas member : layout.getMembers()) {
													if (member instanceof HeaderDetailSection) {
														pos++;
													} else {
														break;
													}
												}
												for (DetailSection detailSection : attributiAddFolderDetails.get(key).getDetailSections()) {
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
												attributiAddFolderDetails.put(key, detail);
												VLayout layout = new VLayout();
												layout.setHeight100();
												layout.setWidth100();
												layout.setMembers(detail);
												attributiAddFolderLayouts.put(key, layout);
												VLayout layoutTab = new VLayout();
												layoutTab.addMember(layout);
												if (tabSet.getTabWithID(key) != null) {
													tabSet.getTabWithID(key).setPane(layoutTab);
												} else {
													Tab tab = new Tab("<b>" + attributiAddFolderTabs.get(key) + "</b>");
													tab.setAttribute("tabID", key);
													tab.setPrompt(attributiAddFolderTabs.get(key));
													tab.setPane(layoutTab);
													tabSet.addTab(tab);
												}
												hasTabs = true;
											}
										}
									}
									if (hasTabs) {
										setMembers(tabSet);
									}
									afterCaricaAttributiDinamiciFolder();
								}
							}
						});
					}
				}
			});
		}
	}

	public void salvaAttributiDinamiciFolder(boolean flgIgnoreObblig, String rowidDoc, String activityNameWF, String esitoActivityWF,
			final ServiceCallback<Record> callback) {
		if (attributiAddFolderTabs != null && attributiAddFolderTabs.size() > 0) {
			Record lRecordFolder = new Record();
			lRecordFolder.setAttribute("rowId", rowidFolder);
			lRecordFolder.setAttribute("valori", getAttributiDinamiciFolder());
			lRecordFolder.setAttribute("tipiValori", getTipiAttributiDinamiciFolder());
			lRecordFolder.setAttribute("activityNameWF", activityNameWF);
			lRecordFolder.setAttribute("esitoActivityWF", esitoActivityWF);
			GWTRestService<Record, Record> lGWTRestServiceFolder = new GWTRestService<Record, Record>("AttributiDinamiciFolderDatasource");
			if (flgIgnoreObblig) {
				lGWTRestServiceFolder.addParam("flgIgnoreObblig", "1");
			}
			lGWTRestServiceFolder.call(lRecordFolder, new ServiceCallback<Record>() {

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

	public Map<String, Object> getAttributiDinamiciFolder() {
		Map<String, Object> attributiDinamiciFolder = null;
		if (attributiAddFolderTabs != null) {
			for (String key : attributiAddFolderTabs.keySet()) {
				if (attributiAddFolderDetails != null && attributiAddFolderDetails.get(key) != null) {
					if (attributiDinamiciFolder == null) {
						attributiDinamiciFolder = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddFolderDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddFolderDetails.get(key).getRecordToSave();
					attributiDinamiciFolder.putAll(attributiAddFolderDetails.get(key).getMappaValori(detailRecord));
				}
			}
		}
		return attributiDinamiciFolder;
	}

	public Map<String, String> getTipiAttributiDinamiciFolder() {
		Map<String, String> tipiAttributiDinamiciFolder = null;
		if (attributiAddFolderTabs != null) {
			for (String key : attributiAddFolderTabs.keySet()) {
				if (attributiAddFolderDetails != null && attributiAddFolderDetails.get(key) != null) {
					if (tipiAttributiDinamiciFolder == null) {
						tipiAttributiDinamiciFolder = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddFolderDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddFolderDetails.get(key).getRecordToSave();
					tipiAttributiDinamiciFolder.putAll(attributiAddFolderDetails.get(key).getMappaTipiValori(detailRecord));
				}
			}
		}
		return tipiAttributiDinamiciFolder;
	}

	public void protocollaDocumentoIstruttoriaInUscita(final Record documentoIstruttoriaDaProtocollare, final ServiceCallback<Record> callback) {
		salvaDati(true, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				Layout.showWaitPopup("Protocollazione in corso: potrebbe richiedere qualche secondo. Attendere...");
				Record record = getRecordToProtocollaDocumentoIstruttoriaInUscita(documentoIstruttoriaDaProtocollare);
				// FIXME Finire protocollazione del documento di istruttoria
				GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource");
				lArchivioDatasource.executecustom("protocollazioneDocumentiIstruttoria", record, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object data, DSRequest dsRequest) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							// FIXME Chiamo la callback per aggiornare i dati nell'allegato
							// Gestire bene i popup
							if (callback != null) {
								Layout.hideWaitPopup();
								callback.execute(response.getData().length > 0 ? response.getData()[0] : new Record());
							} else {
								Layout.hideWaitPopup();
							}
						} else {
							Layout.hideWaitPopup();
						}
					}
				});
			}
		});
//			salvaDati(true, new ServiceCallback<Record>() {
//				
//				@Override
//				public void execute(Record object) {
//					Layout.showWaitPopup("Protocollazione in corso: potrebbe richiedere qualche secondo. Attendere...");
//					Record record = getRecordToProtocollaDocumentoIstruttoriaInUscita(documentoIstruttoriaDaProtocollare);
//					// FIXME Finire protocollazione
//					GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource");
//					lArchivioDatasource.addParam("idUdDaProtocollare", documentoIstruttoriaDaProtocollare.getAttribute("idUdAppartenenza"));
//					lArchivioDatasource.executecustom("protocollazioneDocumentiIstruttoria", record, new DSCallback() {
//						
//						@Override
//						public void execute(DSResponse response, Object data, DSRequest dsRequest) {
//							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
//								// FIXME Chiamo la callback per aggiornare i dati nell'allegato
//								if (callback != null) {
//									callback.execute(response.getData()[0]);
//								} else {
//									Layout.hideWaitPopup();
//								}
//							} else {
//								Layout.hideWaitPopup();
//							}
//						}
//					});
//					
//				}
//			});
	}
	
	public void manageProcedimentiCollegati(String idFolder) {
		
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUdFolder", idFolder);
		GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lArchivioDatasource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					if(lRecord.getAttributeAsRecordList("listaProcCollegati") != null &&
					   lRecord.getAttributeAsRecordList("listaProcCollegati").getLength() > 0) {
						
						RecordList listaProcessi = new RecordList();
						for(int i=0; i < lRecord.getAttributeAsRecordList("listaProcCollegati").getLength(); i++) {
							Record item = lRecord.getAttributeAsRecordList("listaProcCollegati").get(i);
							Record processo = new Record();
							processo.setAttribute("idProcess", item.getAttribute("idProcess"));
							processo.setAttribute("descrizione", item.getAttribute("descrizione"));
							
							listaProcessi.add(processo);
						}
						
						ProcedimentiCollegatiPopup procedimentiCollegatiPopup = new ProcedimentiCollegatiPopup("procedimenti_collegati", listaProcessi) ;
						procedimentiCollegatiPopup.show();
					}
				}
			}
		});
	}

}