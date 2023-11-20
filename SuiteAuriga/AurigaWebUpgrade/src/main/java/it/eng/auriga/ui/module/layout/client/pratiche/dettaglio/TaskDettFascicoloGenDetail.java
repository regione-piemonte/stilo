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
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.ProcedimentiCollegatiPopup;
import it.eng.auriga.ui.module.layout.client.iterAtti.SelezionaEsitoTaskWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.NuovoMessaggioWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatoCanvas;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowWithCallback;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetail;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedDateItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class TaskDettFascicoloGenDetail extends CustomDetail implements TaskFlussoInterface {

	protected TaskDettFascicoloGenDetail instance;

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

	protected String idFolder;
	protected String folderType;
	protected String rowidFolder;

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

	protected DetailSection detailSectionDocumentiIstruttoria;
	protected DynamicForm documentiIstruttoriaForm;
	protected AllegatiItem documentiIstruttoriaItem;
	protected HiddenItem timestampGetDataItem;

	public TaskDettFascicoloGenDetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idFolder, Record lRecordEvento,
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

		this.idFolder = idFolder;

		this.codTabDefault = lRecordEvento != null ? lRecordEvento.getAttribute("codTabDefault") : null;
		this.idTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoTaskDoc") : null;
		this.nomeTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("nomeTipoTaskDoc") : null;

		this.flgUnioneFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgUnioneFile") : null;
		this.flgTimbraFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgTimbraFile") : null;
		this.flgFirmaFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgFirmaFile") : null;
		this.flgInvioPEC = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgInvioPEC") : null;
		
		this.hasProcCollegati = false;

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
	
	public String getDetailSectionDocumentiIstruttoriaTitle() {
		return "Documenti";
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
			public void caricaModelloAllegato(String idModello, String tipoModello, String flgConvertiInPdf, final ServiceCallback<Record> callback) {

				final Record modelloRecord = new Record();
				modelloRecord.setAttribute("idModello", idModello);
				modelloRecord.setAttribute("tipoModello", tipoModello);
				modelloRecord.setAttribute("idFolder", idFolder);
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
		// Nella maschera di dettaglio autotutele/CED come in quelle del dettaglio pratica generico e TSO (parlo di quelle basate su dettaglio fascicolo) nei documenti di istruttoria, se sono registrati a protocollo o repertorio, va messo nel menu' di operazioni (quello richiamato dal tasto con le barre) l'azione di "Timbra" che deve mettere il timbro di registrazione come nella maschera di dettaglio UD
//		documentiIstruttoriaItem.setHideTimbraInAltreOperazioniButton(true);
		
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
		if (this instanceof TaskDettFascicoloGenDetail) {
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
	
	public Record getRecordToProtocollaDocumentoIstruttoriaInUscita(Record documentoIstruttoriaDaProtocollare) {
		Record lRecordToProtocolla = new Record(vm.getValues());
		Map<String, Object> recordValoriAttributiDinamici = getAttributiDinamiciFolder();
		Map<String, String> recordTipiAttributiDinamici = getTipiAttributiDinamiciFolder();
		lRecordToProtocolla.setAttribute("valori", recordValoriAttributiDinamici);
		lRecordToProtocolla.setAttribute("tipiValori", recordTipiAttributiDinamici);
		lRecordToProtocolla.setAttribute("idUdDaProtocollare", documentoIstruttoriaDaProtocollare.getAttribute("idUdAppartenenza"));
		// Valorizzo la descrizione dell'allegato
		String descrizioneFileAllegato = documentoIstruttoriaDaProtocollare.getAttribute("descrizioneFileAllegato");
		String descTipoFileAllegato = documentoIstruttoriaDaProtocollare.getAttribute("descTipoFileAllegato");
		String nomeFile = documentoIstruttoriaDaProtocollare.getAttribute("nomeFileAllegato");
		String desAllegato = descrizioneFileAllegato != null && !"".equalsIgnoreCase(descrizioneFileAllegato) ? descrizioneFileAllegato : descTipoFileAllegato != null && !"".equalsIgnoreCase(descTipoFileAllegato) ? descTipoFileAllegato : nomeFile;
		lRecordToProtocolla.setAttribute("desAllegatoDaProtocollare", desAllegato);
		lRecordToProtocolla.setAttribute("datiProtDocIstrUOProtocollante", AurigaLayout.getUoLavoro());
		return lRecordToProtocolla;
	}

	public void salvaDati(final boolean flgIgnoreObblig, final ServiceCallback<Record> callback) {
		salvaDati(flgIgnoreObblig, callback, null);
	}
	
	public void salvaDati(final boolean flgIgnoreObblig, final ServiceCallback<Record> callback, final ServiceCallback<Record> errorCallback) {
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
							} else {
								lGWTRestService.addParam("skipSuccessMsg", "true");
							}
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
				} else if (errorCallback != null) {
					errorCallback.execute(null);					
				}
			}
		});
	}

	public void saveDettFolder(Record record, final DSCallback callback) {
		// metto a null valori e tipiValori perchè sennò scatta la validazione
		record.setAttribute("valori", (Map) null);
		record.setAttribute("tipiValori", (Map) null);
		GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lArchivioDatasource.addParam("isAttivaGestioneErroriFile", "true");		
		if (this instanceof TaskDettFascicoloGenDetail) {
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
					loadDettFolder(new DSCallback() {

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
											final Record[] files = lRecord.getAttributeAsRecordArray("files");
											if (files != null && files.length > 0) {
												firmaFile(files, new ServiceCallback<Record>() {
													
													@Override
													public void execute(final Record recordAfterFirma) {
														Record[] filesFirmati = new Record[0];
														if (recordAfterFirma.getAttributeAsRecord("fileFirmati") != null) {
															filesFirmati = recordAfterFirma.getAttributeAsRecord("fileFirmati").getAttributeAsRecordArray("files");
														}
														if(filesFirmati != null && filesFirmati.length > 0) {
															for(int i = 0; i < filesFirmati.length; i++) {
																filesFirmati[i].setAttribute("idUd", files[i].getAttribute("idUd"));
																filesFirmati[i].setAttribute("idDoc", files[i].getAttribute("idDoc"));
															}
														}
														invioMail(filesFirmati, new BooleanCallback() {
															
															@Override
															public void execute(Boolean value) {														
																if(value) {
																	aggiornaFile(recordAfterFirma, new ServiceCallback<Record>() {
																		
																		@Override
																		public void execute(Record object) {
																			salvaDati(false, new ServiceCallback<Record>() {

																				@Override
																				public void execute(Record object) {
																					callbackSalvaDati(object, new ServiceCallback<Record>() {
																						
																						@Override
																						public void execute(Record object) {
																							// In caso di errore nell'avanzamento del task
																							reload();
																						}
																					});
																				}
																			}, new ServiceCallback<Record>() {
																				
																				@Override
																				public void execute(Record object) {
																					// In caso di errore nel salvataggio del fascicolo
																					reload();
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
		GWTRestService<Record, Record> lTaskDettFascicoloGenDataSource = new GWTRestService<Record, Record>("TaskDettFascicoloGenDataSource");
		lTaskDettFascicoloGenDataSource.addParam("fileDaTimbrare", hasActionTimbra() ? "true" : "");		
		lTaskDettFascicoloGenDataSource.addParam("idTipoDocFileUnione", recordEvento != null ? recordEvento.getAttribute("unioneFileIdTipoDoc") : null);
		lTaskDettFascicoloGenDataSource.addParam("nomeTipoDocFileUnione", recordEvento != null ? recordEvento.getAttribute("unioneFileNomeTipoDoc") : null);
		lTaskDettFascicoloGenDataSource.addParam("nomeFileUnione", recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFile") : null);
		lTaskDettFascicoloGenDataSource.addParam("esitoTask", attrEsito != null ? attrEsito.getAttribute("valore") : null);
		lTaskDettFascicoloGenDataSource.executecustom("creaDocumentoUnioneFile", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordUnioneFile = response.getData()[0];
					final String idUd = recordUnioneFile.getAttribute("idUd");	
					final String idDoc = recordUnioneFile.getAttribute("idDoc");	
					ServiceCallback<Record> callback = new ServiceCallback<Record>() {
						
						@Override
						public void execute(final Record recordUnioneFileAfterFirma) {							
							invioMail(recordUnioneFileAfterFirma, new BooleanCallback() {
								
								@Override
								public void execute(Boolean value) {									
									if(value) {
										final String uriFileFirmato = recordUnioneFileAfterFirma.getAttribute("uri");	
										final String nomeFileFirmato = recordUnioneFileAfterFirma.getAttribute("nomeFile");		
										final InfoFileRecord infoFileFirmato = InfoFileRecord.buildInfoFileString(JSON.encode(recordUnioneFileAfterFirma.getAttributeAsRecord("infoFile").getJsObj()));														
//											versionaFileUnione(idUd, idDoc, uriFileFirmato, nomeFileFirmato, infoFileFirmato, new ServiceCallback<Record>() {
//												
//												@Override
//												public void execute(Record record) {	
												aggiungiFileUnione(idUd, idDoc, uriFileFirmato, nomeFileFirmato, infoFileFirmato, new ServiceCallback<Record>() {
													
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
												});														
//												}
//											});												
									} else {
										cancellaFileUnione(idUd);
									}
								}
							});
						}
					};
					ServiceCallback<Record> closeCallback = new ServiceCallback<Record>() {
						
						@Override
						public void execute(final Record recordUnioneFileAfterFirma) {							
							cancellaFileUnione(idUd);								
						}
					};
					previewFileUnioneWithFirmaAndCallback(recordUnioneFile, callback, closeCallback);
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

	public void previewFileUnioneWithFirmaAndCallback(final Record record, final ServiceCallback<Record> callback, final ServiceCallback<Record> closeCallback) {		
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
		}) {
			@Override
			public void manageCloseClick() {
				super.manageCloseClick();
				if (closeCallback != null) {
					closeCallback.execute(record);
				}
			}
		};		
	}
	
	public void getFileDaFirmare(DSCallback callback) {
		getFileDaFirmare(false, callback);
	}
	
	public void getFileDaFirmare(boolean fileDaTimbrare, DSCallback callback) {
		final Record lRecord = getRecordToSave(null);
		GWTRestService<Record, Record> lTaskDettFascicoloGenDataSource = new GWTRestService<Record, Record>("TaskDettFascicoloGenDataSource");
		lTaskDettFascicoloGenDataSource.addParam("fileDaTimbrare", fileDaTimbrare ? "true" : "");		
		lTaskDettFascicoloGenDataSource.executecustom("getFileDaFirmare", lRecord, callback);
	}
	
	protected void firmaFile(Record[] files, final ServiceCallback<Record> callback) {
		if(hasActionFirma()) {
			String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
			String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
			FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, files, new FirmaMultiplaCallback() {
	
				@Override
				public void execute(Map<String, Record> files, Record[] filesAndUd) {
					Record lRecord = new Record();
					lRecord.setAttribute("fascicoloOriginale", getRecordToSave(null));
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
			lRecord.setAttribute("fascicoloOriginale", getRecordToSave(null));
			Record lRecordFileFirmati = new Record();
			lRecordFileFirmati.setAttribute("files", files);
			lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
			if(callback != null) {
				callback.execute(lRecord);
			}			
		}else {
			Record lRecord = new Record();
			lRecord.setAttribute("fascicoloOriginale", getRecordToSave(null));
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
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TaskDettFascicoloGenDataSource");
		lGwtRestService.addParam("idTaskCorrente", getIdTaskCorrente());
		lGwtRestService.executecustom("aggiornaFileFirmati", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					editRecord(lRecord);
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
						lRecord.setAttribute("fascicoloOriginale", getRecordToSave(null));
						lRecord.setAttribute("oggetto", recordEvento != null ? recordEvento.getAttribute("invioPECSubject") : null);
						lRecord.setAttribute("bodyHtml", recordEvento != null ? recordEvento.getAttribute("invioPECBody") : null);
						lRecord.setAttribute("destinatari", recordEvento != null ? recordEvento.getAttribute("invioPECDestinatari") : null);
						lRecord.setAttribute("destinatariCC", recordEvento != null ? recordEvento.getAttribute("invioPECDestinatariCC") : null);
						lRecord.setAttribute("mittente", recordEvento != null ? recordEvento.getAttribute("invioPECIndirizzoMittente") : null);
						String idUd = null;
						for (Record file : files) {
							if(idUd == null) {
								idUd = file.getAttribute("idUd");
							} else {
								idUd += ";" + file.getAttribute("idUd");
							}
						}
						lRecord.setAttribute("idUD", idUd); // TODO idUd da collegare alla mail
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
					
					@Override
					public void manageOnCloseClick() {
						super.manageOnCloseClick();
						if(callback != null) {
							callback.execute(false);
						}
					}
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
		callbackSalvaDati(object, null);
	}
	
	protected void callbackSalvaDati(Record object, final ServiceCallback<Record> errorCallback) {
		idEvento = object.getAttribute("idEvento");
		final Record lRecord = new Record();
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
				} else if(errorCallback != null){
					errorCallback.execute(lRecord);
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