/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
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
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.AurigaIndex;
import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.HtmlEditorOrganigrammaFlowWindow;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.iterAtti.SelezionaEsitoTaskWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskFlussoInterface;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatoCanvas;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowWithCallback;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.IDocumentItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class TaskPropostaOrganigrammaDetail extends CustomDetail implements TaskFlussoInterface {
	
	protected static String _TAB_DATI_PRINCIPALI_ID = "HEADER";
	
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
	private String alertConfermaSalvaDefinitivo;
	
	protected String idTipoTaskDoc;
	protected String nomeTipoTaskDoc;
	protected Boolean flgParteDispositivoTaskDoc;
	
	protected String idModAssDocTask;
	protected String nomeModAssDocTask;
	protected String displayFilenameModAssDocTask;

	protected Boolean flgUnioneFile;
	protected Boolean flgFirmaFile;
	
	protected RecordList listaRecordModelli;
	
	protected Set<String> esitiTaskOk;	
	protected HashMap<String, Record> controlliXEsitiTaskDoc;
	protected HashSet<String> valoriEsito;
	
	protected String idUd;
	protected String idDocPrimario;	
	protected String uriFilePrimario;
	protected String tipoDocumento;
	protected String rowidDoc;
	protected String idUo;
	protected String idUoPadre;
	protected String codRapidoUo;
	protected String nomeUo;
	protected String tipoUo;
	protected String level;
	protected String nomeVersConfronto;
	protected String nroVersConfronto;
	protected String nroVersLavoro;
	protected String azione;  
	protected String idDocOrganigramma;  
	protected String idDocArchiviazionePdf;	  	
	protected String livelloRevisione;
	protected String livelloMaxRevisione;
	protected String tipiUORevisione;
	
	private TabSet tabSet;
	private Tab tabDatiPrincipali;
	
	private DynamicForm hiddenForm;
	private HiddenItem idUdHiddenItem; 
	private HiddenItem tipoDocumentoHiddenItem; 
	private HiddenItem nomeTipoDocumentoHiddenItem; 
	private HiddenItem oggettoItem;
	private HiddenItem rowidDocHiddenItem;
	private HiddenItem idDocPrimarioHiddenItem; 
	private HiddenItem nomeFilePrimarioHiddenItem;
	private HiddenItem uriFilePrimarioHiddenItem;
	private HiddenItem remoteUriFilePrimarioHiddenItem;
	private HiddenItem infoFilePrimarioHiddenItem;
	private HiddenItem isChangedFilePrimarioHiddenItem;
	private HiddenItem idDocPrimarioOmissisHiddenItem; 
	private HiddenItem nomeFilePrimarioOmissisHiddenItem;
	private HiddenItem uriFilePrimarioOmissisHiddenItem;
	private HiddenItem remoteUriFilePrimarioOmissisHiddenItem;
	private HiddenItem infoFilePrimarioOmissisHiddenItem;		
	private HiddenItem isChangedFilePrimarioOmissisHiddenItem;
	
	private DetailSection detailSectionUfficioRevisioneOrganigramma;
	private DynamicForm ufficioRevisioneOrganigrammaForm;
	private HiddenItem idUoRevisioneOrganigrammaItem;
	private HiddenItem idUoPadreRevisioneOrganigrammaItem;
	private HiddenItem tipoUoRevisioneOrganigrammaItem;
	private HiddenItem livelloUoRevisioneOrganigrammaItem;	
	private TextItem codRapidoUoRevisioneOrganigrammaItem;
	private TextItem nomeUoRevisioneOrganigrammaItem;
	
	protected DetailSection detailSectionTestoProposta;
	protected DynamicForm testoPropostaForm;
	protected CKEditorItem testoPropostaItem;
	
	protected DetailSection detailSectionAllegati;	
	protected DynamicForm allegatiForm;
	protected AllegatiItem listaAllegatiItem;
	
	/******************************
	 * TAB ATTRIBUTI DINAMICI DOC *
	 ******************************/

	protected LinkedHashMap<String, String> attributiAddDocTabs;
	protected HashMap<String, VLayout> attributiAddDocLayouts;
	protected HashMap<String, AttributiDinamiciDetail> attributiAddDocDetails;
	protected Map<String, Object> attributiDinamiciDocDaCopiare;	

	protected DettaglioPraticaLayout dettaglioPraticaLayout;

	protected Record attrEsito;
	protected String messaggio;
	
	public TaskPropostaOrganigrammaDetail(String nomeEntita, ValuesManager vm, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita, vm);

		this.recordEvento = lRecordEvento;

		this.idUd = idUd;
		
		this.idProcess = idProcess;
		this.nomeFlussoWF = nomeFlussoWF;
		this.processNameWF = processNameWF;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.rowId = lRecordEvento != null ? lRecordEvento.getAttribute("rowId") : null;
		this.instanceId = lRecordEvento != null ? lRecordEvento.getAttribute("instanceId") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;
		this.alertConfermaSalvaDefinitivo = lRecordEvento != null ? lRecordEvento.getAttribute("alertConfermaSalvaDefinitivo") : null;

		this.idTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoTaskDoc") : null;
		this.nomeTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("nomeTipoTaskDoc") : null;
		this.flgParteDispositivoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgParteDispositivoTaskDoc") : null;
		
		this.idModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("idModAssDocTask") : null;		
		this.nomeModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModAssDocTask") : null;
		this.displayFilenameModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("displayFilenameModAssDocTask") : null;		
		
		this.flgUnioneFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgUnioneFile") : null;
		this.flgFirmaFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgFirmaFile") : null;
		
		this.nomeVersConfronto = lRecordEvento != null ? lRecordEvento.getAttribute("nomeVersConfronto") : null;
		this.nroVersConfronto = lRecordEvento != null ? lRecordEvento.getAttribute("nroVersConfronto") : null;		
		this.nroVersLavoro = lRecordEvento != null ? lRecordEvento.getAttribute("nroVersLavoro") : null;		
		this.azione = lRecordEvento != null ? lRecordEvento.getAttribute("azione") : null;	  
		this.idDocOrganigramma = lRecordEvento != null ? lRecordEvento.getAttribute("idDocOrganigramma") : null;	
		this.idDocArchiviazionePdf = lRecordEvento != null ? lRecordEvento.getAttribute("idDocArchiviazionePdf") : null;
		this.livelloRevisione = lRecordEvento != null ? lRecordEvento.getAttribute("livelloRevisione") : null;
		this.livelloMaxRevisione = lRecordEvento != null ? lRecordEvento.getAttribute("livelloMaxRevisione") : null;;
		this.tipiUORevisione = lRecordEvento != null ? lRecordEvento.getAttribute("tipiUORevisione") : null;;
		
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
		
		this.attributiAddDocTabs = getAttributAddDocTabs(lRecordEvento);
		
		setPaddingAsLayoutMargin(false);
		
		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		tabSet.setWidth100();
		tabSet.setBorder("0px");
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		tabSet.setPaneMargin(0);
		
		createTabDatiPrincipali();
		tabSet.setTabs(tabDatiPrincipali);
		
		setMembers(tabSet);

		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);		
	}
	
	protected VLayout createTabPane(VLayout layout) {

		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		
		VLayout layoutTab = new VLayout();
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		layoutTab.addMember(spacerLayout);
		layoutTab.setRedrawOnResize(true);
		
		return layoutTab;
	}
	
	protected void createTabDatiPrincipali() {

		tabDatiPrincipali = new Tab("<b>Dati principali</b>");
		tabDatiPrincipali.setAttribute("tabID", _TAB_DATI_PRINCIPALI_ID);
		tabDatiPrincipali.setPrompt("Dati principali");
		tabDatiPrincipali.setPane(createTabPane(getLayoutDatiPrincipali()));
	}

	public VLayout getLayoutDatiPrincipali() {

		VLayout layoutDatiPrincipali = new VLayout(5);
		layoutDatiPrincipali.setWidth100();
		layoutDatiPrincipali.setHeight100();
		
		hiddenForm = new DynamicForm();
		hiddenForm.setValuesManager(vm);
//		hiddenForm.setHeight(0);
		hiddenForm.setOverflow(Overflow.HIDDEN);
		hiddenForm.setTabSet(tabSet);
		hiddenForm.setTabID(_TAB_DATI_PRINCIPALI_ID);
		
		idUdHiddenItem = new HiddenItem("idUd");
		tipoDocumentoHiddenItem = new HiddenItem("tipoDocumento");
		nomeTipoDocumentoHiddenItem = new HiddenItem("nomeTipoDocumento");
		oggettoItem = new HiddenItem("oggetto");
		rowidDocHiddenItem = new HiddenItem("rowidDoc");
		idDocPrimarioHiddenItem = new HiddenItem("idDocPrimario");		
		nomeFilePrimarioHiddenItem = new HiddenItem("nomeFilePrimario");
		uriFilePrimarioHiddenItem = new HiddenItem("uriFilePrimario");
		remoteUriFilePrimarioHiddenItem = new HiddenItem("remoteUriFilePrimario");
		infoFilePrimarioHiddenItem = new HiddenItem("infoFilePrimario");
		isChangedFilePrimarioHiddenItem = new HiddenItem("isChangedFilePrimario");
		idDocPrimarioOmissisHiddenItem = new HiddenItem("idDocPrimarioOmissis");
		nomeFilePrimarioOmissisHiddenItem = new HiddenItem("nomeFilePrimarioOmissis");
		uriFilePrimarioOmissisHiddenItem = new HiddenItem("uriFilePrimarioOmissis");
		remoteUriFilePrimarioOmissisHiddenItem = new HiddenItem("remoteUriFilePrimarioOmissis");
		infoFilePrimarioOmissisHiddenItem = new HiddenItem("infoFilePrimarioOmissis");
		isChangedFilePrimarioOmissisHiddenItem = new HiddenItem("isChangedFilePrimarioOmissis");
		
		hiddenForm.setFields(
			idUdHiddenItem, 
			tipoDocumentoHiddenItem, 
			nomeTipoDocumentoHiddenItem, 
			oggettoItem,
			rowidDocHiddenItem,
			idDocPrimarioHiddenItem, 
			nomeFilePrimarioHiddenItem,
			uriFilePrimarioHiddenItem,
			remoteUriFilePrimarioHiddenItem,
			infoFilePrimarioHiddenItem,
			isChangedFilePrimarioHiddenItem,
			idDocPrimarioOmissisHiddenItem, 
			nomeFilePrimarioOmissisHiddenItem,
			uriFilePrimarioOmissisHiddenItem,
			remoteUriFilePrimarioOmissisHiddenItem,
			infoFilePrimarioOmissisHiddenItem,		
			isChangedFilePrimarioOmissisHiddenItem
		);
		
		ufficioRevisioneOrganigrammaForm = new DynamicForm();
		ufficioRevisioneOrganigrammaForm.setNumCols(20);
		ufficioRevisioneOrganigrammaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ufficioRevisioneOrganigrammaForm.setValuesManager(vm);
		ufficioRevisioneOrganigrammaForm.setTabSet(tabSet);
		ufficioRevisioneOrganigrammaForm.setTabID(_TAB_DATI_PRINCIPALI_ID);
			
		idUoRevisioneOrganigrammaItem = new HiddenItem("idUoRevisioneOrganigramma");
		idUoPadreRevisioneOrganigrammaItem = new HiddenItem("idUoPadreRevisioneOrganigramma");
		tipoUoRevisioneOrganigrammaItem = new HiddenItem("tipoUoRevisioneOrganigramma");
		livelloUoRevisioneOrganigrammaItem = new HiddenItem("livelloUoRevisioneOrganigramma");
		
		codRapidoUoRevisioneOrganigrammaItem = new TextItem("codRapidoUoRevisioneOrganigramma", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title()) {
			
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
				setTabIndex(-1);
			}
		};
		codRapidoUoRevisioneOrganigrammaItem.setWrapTitle(false);
		codRapidoUoRevisioneOrganigrammaItem.setWidth(120);
		codRapidoUoRevisioneOrganigrammaItem.setColSpan(1);
		
		nomeUoRevisioneOrganigrammaItem = new TextItem("nomeUoRevisioneOrganigramma") {
			
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
				setTabIndex(-1);
			}
		};
		nomeUoRevisioneOrganigrammaItem.setShowTitle(false);
		nomeUoRevisioneOrganigrammaItem.setWidth(500);
		
		ufficioRevisioneOrganigrammaForm.setFields(idUoRevisioneOrganigrammaItem, idUoPadreRevisioneOrganigrammaItem, tipoUoRevisioneOrganigrammaItem, livelloUoRevisioneOrganigrammaItem, codRapidoUoRevisioneOrganigrammaItem, nomeUoRevisioneOrganigrammaItem);
		
		testoPropostaForm = new DynamicForm();
		testoPropostaForm.setValuesManager(vm);
		testoPropostaForm.setWidth100();
		testoPropostaForm.setPadding(5);
		testoPropostaForm.setWrapItemTitles(false);
		testoPropostaForm.setNumCols(20);
		testoPropostaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		testoPropostaForm.setTabSet(tabSet);
		testoPropostaForm.setTabID(_TAB_DATI_PRINCIPALI_ID);
		
		testoPropostaItem = new CKEditorItem("testoProposta");
		testoPropostaItem.setShowTitle(false);
		testoPropostaItem.setColSpan(20);
		testoPropostaItem.setWidth("100%");
		testoPropostaItem.setRequired(true);
		
		testoPropostaForm.setFields(testoPropostaItem);			
		
		allegatiForm = new DynamicForm();
		allegatiForm.setValuesManager(vm);
		allegatiForm.setWidth100();
		allegatiForm.setPadding(5);
		allegatiForm.setWrapItemTitles(false);
		allegatiForm.setNumCols(20);
		allegatiForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		allegatiForm.setTabSet(tabSet);
		allegatiForm.setTabID(_TAB_DATI_PRINCIPALI_ID);
		
		listaAllegatiItem = new AllegatiItem() {

			@Override
			public String getIdProcess() {
				return getIdProcessTask();
			}

			@Override
			public String getIdTaskCorrenteAllegati() {
				return getIdTaskCorrente();
			}

			@Override
			public HashSet<String> getTipiModelliAttiAllegati() {
				return getTipiModelliAttiInAllegati();
			}
			
			@Override
			public boolean isObbligatorioFile() {
				return isObbligatorioFileInAllegati();
			}

			@Override
			public boolean showGeneraDaModello() {
				return true;
			}

			@Override
			public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
				return getRecordCaricaModelloInAllegati(idModello, tipoModello);
			}
			
			@Override
			public String getTitleFlgParteDispositivo() {
				return I18NUtil.getMessages().nuovaPropostaAtto2_detail_allegati_flgParteIntegrante_title();
			}
			
			@Override
			public Integer getWidthDescrizioneFileAllegato() {
				return 250;
			}
			
			@Override
			public Integer getWidthNomeFileAllegato() {
				return 250;
			}
			
			@Override
			public boolean getShowVersioneOmissis() {
				return true;
			}				
		};
		listaAllegatiItem.setName("listaAllegati");
		listaAllegatiItem.setShowTitle(false);
		listaAllegatiItem.setShowFlgParteDispositivo(true);
		listaAllegatiItem.setShowFlgNoPubblAllegato(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ESCL_PUBBL_FILE_IN_ATTI"));
		listaAllegatiItem.setColSpan(20);
		
		allegatiForm.setFields(listaAllegatiItem);		
		
		detailSectionUfficioRevisioneOrganigramma = new DetailSection("Revisione organigramma di", true, true, true, ufficioRevisioneOrganigrammaForm);		
		detailSectionTestoProposta = new DetailSection("Testo proposta revisione organigramma", true, true, true, testoPropostaForm);		
		detailSectionAllegati = new DetailSection("Allegati", true, false, false, allegatiForm);
			
		layoutDatiPrincipali.setMembers(hiddenForm, detailSectionUfficioRevisioneOrganigramma, detailSectionTestoProposta, detailSectionAllegati);
	
		return layoutDatiPrincipali;
	}
	
	public void apriEditorOrganigramma() {
		final boolean hasVersLavoro = nroVersLavoro != null && !"".equals(nroVersLavoro);		
		final HtmlEditorOrganigrammaFlowWindow lHtmlEditorOrganigrammaFlowWindow = new HtmlEditorOrganigrammaFlowWindow("Editor organigramma", "menu/organigramma.png", null) {
			
			@Override
			public void manageOnCloseClick() {
				// lascio questo metodo vuoto in modo da inibire la chiusura implicita della window, in modo da gestirla esternamente nel closeClickHandler 
			};

			@Override
			public void closeWindowAfterAzione(String esitoAzione) {
				markForDestroy();
				if(esitoAzione != null && "1".equals(esitoAzione)) {
					// se ricarico tutto perdo tutte le eventuali modifiche che hanno fatto, quindi ricarico solo gli allegati
//					reload(); 
					if(idDocArchiviazionePdf != null && !"".equals(idDocArchiviazionePdf)) {
						final GWTRestDataSource lPropostaOrganigrammaDataSource = new GWTRestDataSource("PropostaOrganigrammaDataSource");
						lPropostaOrganigrammaDataSource.addParam("idProcess", idProcess);
						lPropostaOrganigrammaDataSource.addParam("taskName", activityName);
						Record lRecordToLoad = new Record();
						lRecordToLoad.setAttribute("idUd", idUd);
						lPropostaOrganigrammaDataSource.getData(lRecordToLoad, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									final Record lRecord = response.getData()[0];
									Record allegatoArchiviazionePdf = null;
									RecordList listaAllegatiSalvati = lRecord.getAttributeAsRecordList("listaAllegati");
									for(int i = 0; i < listaAllegatiSalvati.getLength(); i++) {
										if(listaAllegatiSalvati.get(i).getAttribute("idDocAllegato").equals(idDocArchiviazionePdf)) {
											allegatoArchiviazionePdf = listaAllegatiSalvati.get(i);
											break;
										}
									}
									if(allegatoArchiviazionePdf != null) {
										RecordList listaAllegati = allegatiForm.getValueAsRecordList("listaAllegati");
										boolean trovato = false;
										for(int i = 0; i < listaAllegati.getLength(); i++) {
											if(listaAllegati.get(i).getAttribute("idDocAllegato").equals(idDocArchiviazionePdf)) {
												listaAllegati.get(i).setAttribute("uriFileAllegato", allegatoArchiviazionePdf.getAttribute("uriFileAllegato"));
												listaAllegati.get(i).setAttribute("nomeFileAllegato", allegatoArchiviazionePdf.getAttribute("nomeFileAllegato"));
												listaAllegati.get(i).setAttribute("infoFile", allegatoArchiviazionePdf.getAttributeAsRecord("infoFile"));
												trovato = true;
												break;
											}
										}
										if(!trovato) {
											listaAllegati.add(allegatoArchiviazionePdf);
										}
										Record lRecordForm = new Record();
										lRecordForm.setAttribute("listaAllegati", listaAllegati);
										allegatiForm.setValues(lRecordForm.toMap());
										if(listaAllegatiItem != null) {
											listaAllegatiItem.resetCanvasChanged();
										}
										if (detailSectionAllegati != null) {
											detailSectionAllegati.openIfhasValue();
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
								}
							}
						});
					}
				}				
			}
			
			@Override
			public void closeWindow(String nroVersLavoroSalvato) {
				markForDestroy();
				if(nroVersLavoroSalvato != null && !"".equals(nroVersLavoroSalvato)) {
					nroVersLavoro = nroVersLavoroSalvato;
				}				
			}
		};
		String urlEditorOrganigramma = AurigaLayout.getUrlEditorOrganigramma();
		if(urlEditorOrganigramma == null || "".equals(urlEditorOrganigramma)) {
			urlEditorOrganigramma = AurigaIndex.getOrigin() + "/EditorOrganigramma";
		}
		String contentsUrl = urlEditorOrganigramma + "?idDoc=" + idDocOrganigramma;
		if(AurigaLayout.utenteLoggato.getSchema() != null && !"".equals(AurigaLayout.utenteLoggato.getSchema())) {
			contentsUrl += "&schema=" + AurigaLayout.utenteLoggato.getSchema();
		}
		if(AurigaLayout.utenteLoggato.getToken() != null && !"".equals(AurigaLayout.utenteLoggato.getToken())) {
			contentsUrl += "&token=" + AurigaLayout.utenteLoggato.getToken();
		}
		if(AurigaLayout.utenteLoggato.getIdUserLavoro() != null && !"".equals(AurigaLayout.utenteLoggato.getIdUserLavoro())) {
			contentsUrl += "&idUserLavoro=" + AurigaLayout.utenteLoggato.getIdUserLavoro();
		}
		if(!hasVersLavoro) {			
			if(idUo != null && !"".equals(idUo)) {
				contentsUrl += "&idUo=" + idUo;						
			}
			if(idUoPadre != null && !"".equals(idUoPadre)) {
				contentsUrl += "&idUoPadre=" + idUoPadre;						
			}	
			if(codRapidoUo != null && !"".equals(codRapidoUo)) {
				contentsUrl += "&codRapidoUo=" + codRapidoUo;						
			}					
			if(nomeUo != null && !"".equals(nomeUo)) {
				contentsUrl += "&nome=" + nomeUo;											
			}
			if(tipoUo != null && !"".equals(tipoUo)) {
				contentsUrl += "&tipo=" + tipoUo;						
			}
			if(level != null && !"".equals(level)) {
				contentsUrl += "&level=" + level;						
			}					
		}	
		if(livelloRevisione != null && !"".equals(livelloRevisione)) {
			contentsUrl += "&livelloRevisione=" + livelloRevisione;						
		}	
		if(nroVersLavoro != null && !"".equals(nroVersLavoro)) {
			contentsUrl += "&nroVersLavoro=" + nroVersLavoro;						
		}
		if(nomeVersConfronto != null && !"".equals(nomeVersConfronto)) {
			contentsUrl += "&nomeVersConfronto=" + nomeVersConfronto;						
		}
		if(nroVersConfronto != null && !"".equals(nroVersConfronto)) {
			contentsUrl += "&nroVersConfronto=" + nroVersConfronto;						
		}
		if(azione != null && !"".equals(azione)) {
			contentsUrl += "&azione=" + azione;						
		}
		if(idDocArchiviazionePdf != null && !"".equals(idDocArchiviazionePdf)) {
			contentsUrl += "&idDocArchiviazionePdf=" + idDocArchiviazionePdf;						
		}
		if(livelloMaxRevisione != null && !"".equals(livelloMaxRevisione)) {
			contentsUrl += "&livelloMaxRevisione=" + livelloMaxRevisione;						
		}
		if(tipiUORevisione != null && !"".equals(tipiUORevisione)) {
			contentsUrl += "&tipiUORevisione=" + tipiUORevisione;						
		}
		
		String closeWindowFunctionName = lHtmlEditorOrganigrammaFlowWindow.getCloseWindowFunctionName();
		if(closeWindowFunctionName != null && !"".equals(closeWindowFunctionName)) {
			contentsUrl += "&closeWindowFunctionName=" + closeWindowFunctionName;						
		}
//		lHtmlEditorOrganigrammaFlowWindow.setHtmlFlowContentsURL(contentsUrl);		
		final String iframeID = lHtmlEditorOrganigrammaFlowWindow.getiframeID();
		lHtmlEditorOrganigrammaFlowWindow.setHtmlFlowContents("<iframe id=\"" + iframeID + "\" frameborder=\"0\" width=\"100%\" height=\"100%\" marginheight=\"0\" marginwidth=\"0\" align=\"left\" scrolling=\"yes\" vspace=\"0\" hspace=\"0\" src=\"" + contentsUrl + "\"></iframe>");
		lHtmlEditorOrganigrammaFlowWindow.addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				event.cancel();
				iframePostMessage(iframeID, "saveOrganigrammaAndClose", AurigaIndex.getOrigin());
			}
		});
		lHtmlEditorOrganigrammaFlowWindow.maximize();
		lHtmlEditorOrganigrammaFlowWindow.show();
	}		
	
	public static native void iframePostMessage(String iframeID, String message, String origin) /*-{ 
		iframeElem = $wnd.document.getElementById(iframeID);
		if (iframeElem) {
			iframeContent = (iframeElem.contentWindow || iframeElem.contentDocument);
			if (iframeContent) {
				iframeContent.postMessage(message, origin); 								
			}
		}
	}-*/;

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
	
	@Override
	public void loadDati() {
		loadDettPropostaOrganigramma(new ServiceCallback<Record>() {
			
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
	
	public void loadDettPropostaOrganigramma(final ServiceCallback<Record> callback) {
		final GWTRestDataSource lPropostaOrganigrammaDataSource = new GWTRestDataSource("PropostaOrganigrammaDataSource");
		lPropostaOrganigrammaDataSource.addParam("idProcess", idProcess);
		lPropostaOrganigrammaDataSource.addParam("taskName", activityName);
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		lPropostaOrganigrammaDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record lRecord = response.getData()[0];
					rowidDoc = lRecord.getAttribute("rowidDoc");
					tipoDocumento = lRecord.getAttribute("tipoDocumento");		
					idDocPrimario = lRecord.getAttribute("idDocPrimario");
					uriFilePrimario = lRecord.getAttribute("uriFilePrimario");
					idUo = lRecord.getAttribute("idUoRevisioneOrganigramma");
					idUoPadre = lRecord.getAttribute("idUoPadreRevisioneOrganigramma");
					codRapidoUo = lRecord.getAttribute("codRapidoUoRevisioneOrganigramma");
					nomeUo = lRecord.getAttribute("nomeUoRevisioneOrganigramma");
					tipoUo = lRecord.getAttribute("tipoUoRevisioneOrganigramma");
					level = lRecord.getAttribute("livelloUoRevisioneOrganigramma");			
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
	public void editRecord(Record record) {
		
		vm.editRecord(record);
		
		// Devo settare manualmente i valori dei CKEditor		
		if(testoPropostaItem != null) {
			testoPropostaItem.setValue(record.getAttribute("testoProposta"));
		}
		
		if(detailSectionAllegati != null) {
			detailSectionAllegati.openIfhasValue();
		}		
	}
	
	public DynamicForm[] getTabForms(String tabID) {
		
		ArrayList<DynamicForm> listaTabForms = new ArrayList<DynamicForm>();
		for(DynamicForm form : vm.getMembers()) {
			if(form.getTabID() != null && form.getTabID().equalsIgnoreCase(tabID)) {
				listaTabForms.add(form);
			}
		}
		return listaTabForms.toArray(new DynamicForm[listaTabForms.size()]);
	}
	
	public void addTabValues(Record record, String tabID) {
		
		for(DynamicForm form : getTabForms(tabID)) {
			addFormValues(record, form);
		}
	}
	
	public Record getRecordToSave() {
		
		final Record lRecordToSave = new Record();
		
		// Dati scheda
		addTabValues(lRecordToSave, _TAB_DATI_PRINCIPALI_ID);
		
		
		lRecordToSave.setAttribute("idProcess", idProcess);
		lRecordToSave.setAttribute("idModello", idModAssDocTask != null ? idModAssDocTask : "");
		lRecordToSave.setAttribute("nomeModello", nomeModAssDocTask != null ? nomeModAssDocTask : "");
		lRecordToSave.setAttribute("displayFilenameModello", displayFilenameModAssDocTask != null ? displayFilenameModAssDocTask : "");
		
		// Setto i valori dei CKEditor		
		lRecordToSave.setAttribute("testoProposta", testoPropostaItem != null ? testoPropostaItem.getValue() : null);		
		
		// Attributi dinamici doc
		if (attributiAddDocDetails != null) {
			lRecordToSave.setAttribute("rowidDoc", rowidDoc);
			lRecordToSave.setAttribute("valori", getAttributiDinamiciDoc());
			lRecordToSave.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
			lRecordToSave.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
		}
		
		lRecordToSave.setAttribute("flgDatiSensibili", hasDatiSensibili());
		
		return lRecordToSave;
	}

	private void salvaDati(final boolean flgIgnoreObblig, final ServiceCallback<Record> callback) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {				
				Record lRecordToSave = getRecordToSave();
				final GWTRestDataSource lPropostaOrganigrammaDataSource = new GWTRestDataSource("PropostaOrganigrammaDataSource");
				lPropostaOrganigrammaDataSource.addParam("idProcess", idProcess);
				lPropostaOrganigrammaDataSource.addParam("taskName", activityName);
				if (isReadOnly()) {
					lPropostaOrganigrammaDataSource.addParam("idTaskCorrente", getIdTaskCorrente());
				}
				final Map<String, Object> attributiDinamiciEvent = new HashMap<String, Object>();
				final Map<String, String> tipiAttributiDinamiciEvent = new HashMap<String, String>();
				final String esito = (!flgIgnoreObblig && attrEsito != null) ? attrEsito.getAttribute("valore") : null;
				// se è il salvataggio finale
				if (!flgIgnoreObblig) {					
					if(attrEsito != null) {
						lPropostaOrganigrammaDataSource.addParam("nomeAttrCustomEsito", attrEsito.getAttribute("nome"));
						lPropostaOrganigrammaDataSource.addParam("valoreAttrCustomEsito", attrEsito.getAttribute("valore"));
						attributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("valore"));
						tipiAttributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("tipo"));
						attrEsito = null;
					}
				}
				Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
				lPropostaOrganigrammaDataSource.updateData(lRecordToSave, new DSCallback() {

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
								SelezionaEsitoTaskWindow selezionaEsitoTaskWindow = new SelezionaEsitoTaskWindow(attr, true, esitiTaskOk, valoriEsito,
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
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
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
					if (value) {
						saveAndGo();
					}
				}
			});
		} else {
			saveAndGo();
		}
	}

	public void saveAndGo() {
		final String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;	
		final Record recordModello = getRecordModelloXEsito(esito);		
		if(isEsitoTaskOk(attrEsito)) {						
			salvaDati(true, new ServiceCallback<Record>() {
				@Override
				public void execute(Record object) {
					idEvento = object.getAttribute("idEvento");
					Layout.hideWaitPopup();
					loadDettPropostaOrganigramma(new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record dett) {
							if(hasActionUnioneFile()) {
								// nell'unione dei file se ho dei file firmati pades devo prendere la versione precedente (quella che usiamo per l'editor, e la convertiamo in pdf) se c'è, altrimenti quella corrente 
								// se non sono tutti i convertibili i file do errore nell'unione e blocco tutto				
								unioneFileAndReturn();			
							} else if(hasActionFirma()) {
								getFileDaFirmare(new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Record lRecord = response.getData()[0];
											Record[] files = lRecord.getAttributeAsRecordArray("files");
											if (files != null && files.length > 0) {
												firmaFile(files, new ServiceCallback<Record>() {
													
													@Override
													public void execute(final Record recordAfterFirma) {
														aggiornaFile(recordAfterFirma, new ServiceCallback<Record>() {
															
															@Override
															public void execute(Record object) {
																if (recordModello != null) {
																	salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

																		@Override
																		public void execute(Record object) {
																			saveAndGoWithModello(recordModello, esito, new ServiceCallback<Record>() {

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
														});	
													}
												});	
											} else {
												if (recordModello != null) {
													salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

														@Override
														public void execute(Record object) {
															saveAndGoWithModello(recordModello, esito, new ServiceCallback<Record>() {

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
										}
									}
								});				
							} else {
								if (recordModello != null) {
									salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

										@Override
										public void execute(Record object) {
											saveAndGoWithModello(recordModello, esito, new ServiceCallback<Record>() {

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
						}
					});					
				}
			});	
		} else {
			if (recordModello != null) {
				salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						saveAndGoWithModello(recordModello, esito, new ServiceCallback<Record>() {

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
	}
	
	public void unioneFileAndReturn() {
		final Record lRecord = getRecordToSave();		
		final GWTRestDataSource lPropostaOrganigrammaDataSource = new GWTRestDataSource("PropostaOrganigrammaDataSource");		
		lPropostaOrganigrammaDataSource.addParam("nomeFileUnione", recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFile") : null);
		lPropostaOrganigrammaDataSource.addParam("nomeFileUnioneOmissis", recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFileOmissis") : null);
		lPropostaOrganigrammaDataSource.addParam("esitoTask", attrEsito != null ? attrEsito.getAttribute("valore") : null);
		Layout.showWaitPopup("Generazione del file unione in corso...");				
		lPropostaOrganigrammaDataSource.executecustom("unioneFile", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordUnioneFile = response.getData()[0];
					previewFileUnioneWithFirmaAndCallback(recordUnioneFile, new ServiceCallback<Record>() {
						
						@Override
						public void execute(final Record recordUnioneFileAfterFirma) {							
							final String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;	
							final Record recordModello = getRecordModelloXEsito(esito);		
							if (recordModello != null) {
								salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {
										saveAndGoWithModello(recordModello, esito, new ServiceCallback<Record>() {

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
					});
				}
			}
		});
	}
	
	public void previewFileUnioneWithFirmaAndCallback(final Record record, final ServiceCallback<Record> callback) {			
		final String uriFileUnione = record.getAttribute("uriVersIntegrale");	
		final String nomeFileUnione = record.getAttribute("nomeFileVersIntegrale");		
		final InfoFileRecord infoFileUnione = record.getAttributeAsRecord("infoFileVersIntegrale") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(record.getAttributeAsRecord("infoFileVersIntegrale").getJsObj())) : null;
		final Record lFileUnione = (uriFileUnione != null && !"".equals(uriFileUnione)) ? new Record() : null;
		if(lFileUnione != null) {			
			lFileUnione.setAttribute("idFile", "primario" + uriFileUnione);
			lFileUnione.setAttribute("uri", uriFileUnione);
			lFileUnione.setAttribute("isFilePrincipaleAtto", true);
			lFileUnione.setAttribute("nomeFile", nomeFileUnione);
			lFileUnione.setAttribute("infoFile", infoFileUnione);
		}
		final String uriFileUnioneOmissis = record.getAttribute("uri");	
		final String nomeFileUnioneOmissis = record.getAttribute("nomeFile");		
		final InfoFileRecord infoFileUnioneOmissis = record.getAttributeAsRecord("infoFile") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(record.getAttributeAsRecord("infoFile").getJsObj())) : null;				
		final Record lFileUnioneOmissis = (uriFileUnioneOmissis != null && !"".equals(uriFileUnioneOmissis)) ? new Record() : null;
		if(lFileUnioneOmissis != null) {
			lFileUnioneOmissis.setAttribute("idFile", "primarioOmissis" + uriFileUnioneOmissis);
			lFileUnioneOmissis.setAttribute("uri", uriFileUnioneOmissis);
			lFileUnioneOmissis.setAttribute("isFilePrincipaleAtto", true);
			lFileUnioneOmissis.setAttribute("nomeFile", nomeFileUnioneOmissis);
			lFileUnioneOmissis.setAttribute("infoFile", infoFileUnioneOmissis);
		}		
		new PreviewWindowWithCallback(uriFileUnione, false, infoFileUnione, "FileToExtractBean", nomeFileUnione, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordPreview) {		
				if(lFileUnioneOmissis != null) {
					new PreviewWindowWithCallback(uriFileUnioneOmissis, false, infoFileUnioneOmissis, "FileToExtractBean", nomeFileUnioneOmissis, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record recordPreview) {		
							afterPreviewFileUnioneWithFirma(lFileUnione, lFileUnioneOmissis, callback);							
						}
					}) {
						
						@Override
						public boolean hideAnnullaButton() {
							return true;
						}
					};		
				} else {
					afterPreviewFileUnioneWithFirma(lFileUnione, null, callback);
				}
			}
		}) {
			
			@Override
			public boolean hideAnnullaButton() {
				return true;
			}
		};		
	}
	
	public void afterPreviewFileUnioneWithFirma(Record lFileUnione, Record lFileUnioneOmissis, final ServiceCallback<Record> callback) {
		if (hasActionFirma()) {	
			final List<Record> listaFiles = new ArrayList<Record>();
			if(lFileUnione != null) {
				listaFiles.add(lFileUnione);
			}
			if(lFileUnioneOmissis != null) {
				listaFiles.add(lFileUnioneOmissis);
			}	
			getFileAllegatiDaFirmare(new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record lRecordFileAllegati = response.getData()[0];
						Record[] filesAllegati = lRecordFileAllegati.getAttributeAsRecordArray("files");
						for(int i = 0; i < filesAllegati.length; i++) {
							listaFiles.add(filesAllegati[i]);	
						}
						// Leggo gli eventuali parametri per forzare il tipo d firma
						String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
						String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
						Record[] files = listaFiles.toArray(new Record[listaFiles.size()]);
						FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, files, new FirmaMultiplaCallback() {
							@Override
							public void execute(Map<String, Record> signedFiles, Record[] filesAndUd) {
								Record lRecord = new Record();
								lRecord.setAttribute("protocolloOriginale", getRecordToSave());
								Record lRecordFileFirmati = new Record();
								lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[]{}));
								lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
								aggiornaFileFirmati(lRecord,  callback);						
							}
						});
					}
				}
			});	
		} else {
			if(lFileUnione != null) {				
				InfoFileRecord infoFileUnione = lFileUnione.getAttributeAsRecord("infoFile") != null ? new InfoFileRecord(lFileUnione.getAttributeAsRecord("infoFile")) : null;
				hiddenForm.setValue("nomeFilePrimario", lFileUnione.getAttribute("nomeFile"));
				hiddenForm.setValue("uriFilePrimario", lFileUnione.getAttribute("uri"));
				hiddenForm.setValue("remoteUriFilePrimario", false);
				InfoFileRecord precInfoFile = hiddenForm.getValue("infoFilePrimario") != null ? new InfoFileRecord(hiddenForm.getValue("infoFilePrimario")) : null;
				String precImpronta = precInfoFile != null ? precInfoFile.getImpronta() : null;
				hiddenForm.setValue("infoFilePrimario", infoFileUnione);
				if (precImpronta == null || !precImpronta.equals(infoFileUnione.getImpronta())) {
					hiddenForm.setValue("isChangedFilePrimario", true);
				}
				if (infoFileUnione.isFirmato() && !infoFileUnione.isFirmaValida()) {
					GWTRestDataSource.printMessage(new MessageBean("Il file primario presenta una firma non valida alla data odierna", "", MessageType.WARNING));
				}
			}
			if(lFileUnioneOmissis != null) {
				InfoFileRecord infoFileUnioneOmissis = lFileUnioneOmissis.getAttributeAsRecord("infoFile") != null ? new InfoFileRecord(lFileUnioneOmissis.getAttributeAsRecord("infoFile")) : null;
				hiddenForm.setValue("nomeFilePrimarioOmissis", lFileUnioneOmissis.getAttribute("nomeFile"));
				hiddenForm.setValue("uriFilePrimarioOmissis", lFileUnioneOmissis.getAttribute("uri"));
				hiddenForm.setValue("remoteUriFilePrimarioOmissis", false);
				InfoFileRecord precInfoFileOmissis = hiddenForm.getValue("infoFilePrimarioOmissis") != null ? new InfoFileRecord(hiddenForm.getValue("infoFilePrimarioOmissis")) : null;
				String precImprontaOmissis = precInfoFileOmissis != null ? precInfoFileOmissis.getImpronta() : null;
				hiddenForm.setValue("infoFilePrimarioOmissis", infoFileUnioneOmissis);
				if (precImprontaOmissis == null || !precImprontaOmissis.equals(infoFileUnioneOmissis.getImpronta())) {
					hiddenForm.setValue("isChangedFilePrimarioOmissis", true);
				}
				if (infoFileUnioneOmissis.isFirmato() && !infoFileUnioneOmissis.isFirmaValida()) {
					GWTRestDataSource.printMessage(new MessageBean("Il file primario (vers. con omissis) presenta una firma non valida alla data odierna", "", MessageType.WARNING));
				}
			}
			if(callback != null) {
				callback.execute(getRecordToSave());
			}
		}
	}
	
	public void getFileDaFirmare(DSCallback callback) {
		final Record lRecord = getRecordToSave();
		final GWTRestDataSource lPropostaOrganigrammaDataSource = new GWTRestDataSource("PropostaOrganigrammaDataSource");
		lPropostaOrganigrammaDataSource.executecustom("getFileDaFirmare", lRecord, callback);
	}
	
	public void getFileAllegatiDaFirmare(DSCallback callback) {
		final Record lRecord = getRecordToSave();
		final GWTRestDataSource lPropostaOrganigrammaDataSource = new GWTRestDataSource("PropostaOrganigrammaDataSource");
		lPropostaOrganigrammaDataSource.executecustom("getFileAllegatiDaFirmare", lRecord, callback);
	}
	
	protected void firmaFile(Record[] files, final ServiceCallback<Record> callback) {
		if(hasActionFirma()) {
			String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
			String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
			FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, files, new FirmaMultiplaCallback() {
	
				@Override
				public void execute(Map<String, Record> signedFiles, Record[] filesAndUd) {
					Record lRecord = new Record();
					lRecord.setAttribute("protocolloOriginale", getRecordToSave());
					Record lRecordFileFirmati = new Record();
					lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
					lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
					if(callback != null) {
						callback.execute(lRecord);
					}					
				}
			});
		} else {
			Record lRecord = new Record();
			lRecord.setAttribute("protocolloOriginale", getRecordToSave());
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
		} else if(callback != null) {
			callback.execute(getRecordToSave());			
		}		
	}

	protected void aggiornaFileFirmati(Record record, final ServiceCallback<Record> callback) {
		final GWTRestDataSource lPropostaOrganigrammaDataSource = new GWTRestDataSource("PropostaOrganigrammaDataSource");
		lPropostaOrganigrammaDataSource.addParam("idTaskCorrente", getIdTaskCorrente());
		lPropostaOrganigrammaDataSource.executecustom("aggiornaFileFirmati", record, new DSCallback() {
		
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
				} else {
					reload();
				}
			}
		});
	}
	
	public void saveAndGoWithModello(Record recordModello, String esito, ServiceCallback<Record> callback) {
		compilazioneAutomaticaModelloPdf(recordModello, esito, callback);
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
	public void compilazioneAutomaticaModelloPdf(Record recordModello, String esito, final ServiceCallback<Record> callback) {

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
			lRecordCompilaModello.setAttribute("dettaglioBean", getRecordToSave());
			
			final GWTRestDataSource lPropostaOrganigrammaDataSource = new GWTRestDataSource("PropostaOrganigrammaDataSource");
			lPropostaOrganigrammaDataSource.addParam("esitoTask", esito);
			lPropostaOrganigrammaDataSource.executecustom("compilazioneAutomaticaModelloPdf", lRecordCompilaModello, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record result = response.getData()[0];
						final String documentUri = result.getAttribute("uri");
						final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));

						new PreviewWindowWithCallback(documentUri, false, infoFile, "FileToExtractBean", nomeFileModello, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								
								if (flgDaFirmare) {
									// Leggo gli eventuali parametri per forzare il tipo di firma
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
				}
			});
		}
	}
	
	protected void aggiungiModelloAdAllegati(boolean flgParteDispositivo, String descrizioneFileAllegato, String idTipoModello, String nomeTipoModello, String uriFileAllegato,
			String nomeFileAllegato, InfoFileRecord infoAllegato, ServiceCallback<Record> callback) {

		if (allegatiForm != null) {
			
			RecordList listaAllegati = allegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");

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
			allegatiForm.setValues(lRecordForm.toMap());
			
			if(listaAllegatiItem != null) {
				listaAllegatiItem.resetCanvasChanged();
			}

			if (detailSectionAllegati != null) {
				detailSectionAllegati.openIfhasValue();
			}
		}
		if (callback != null) {
			callback.execute(new Record());
		}
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
	
	@Override
	public boolean customValidate() {
		
		boolean valid = super.customValidate();
		// Faccio la validazione dei CKEditor obbligatori
		if(testoPropostaItem != null) {
			valid = testoPropostaItem.validate() && valid;
		}				
		if (attributiAddDocDetails != null) {
			for (String key : attributiAddDocDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddDocDetails.get(key);
				if(!detail.customValidate()) {
					valid = false;
				}
				for (DynamicForm form : detail.getForms()) {
					form.clearErrors(true);
					valid = form.validate() && valid;
					for (FormItem item : form.getFields()) {
						if (item instanceof ReplicableItem) {
							ReplicableItem lReplicableItem = (ReplicableItem) item;
							boolean itemValid = lReplicableItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lReplicableItem != null && lReplicableItem.getForm() != null && lReplicableItem.getForm().getDetailSection() != null) {
									lReplicableItem.getForm().getDetailSection().open();
								}
							}
						} else if (item instanceof IDocumentItem) {
							IDocumentItem lIDocumentItem = (IDocumentItem) item;
							boolean itemValid = lIDocumentItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lIDocumentItem != null && lIDocumentItem.getForm() != null && lIDocumentItem.getForm().getDetailSection() != null) {
									lIDocumentItem.getForm().getDetailSection().open();
								}
							}
						} else if (item instanceof CKEditorItem) {
							CKEditorItem lCKEditorItem = (CKEditorItem) item;
							boolean itemValid = lCKEditorItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lCKEditorItem != null && lCKEditorItem.getForm() != null && lCKEditorItem.getForm().getDetailSection() != null) {
									lCKEditorItem.getForm().getDetailSection().open();
								}
							}
						} else {
							boolean itemValid = item.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(item != null && item.getForm() != null && item.getForm().getDetailSection() != null) {
									item.getForm().getDetailSection().open();
								}
							}
						}
					}
				}
			}
		}
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
			
			if(listaAllegatiItem != null) {
				AllegatoCanvas lAllegatoCanvas = listaAllegatiItem.getAllegatoCanvasFromTipo(idTipoTaskDoc);
				if (flgObbligatorio && lAllegatoCanvas == null) {
					lAllegatoCanvas = (AllegatoCanvas) listaAllegatiItem.onClickNewButton();
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
				if (detailSectionAllegati != null) {
					detailSectionAllegati.open();
				}			
			}
		}		
		return valid;
	}
	
	/**
	 * Metodo per il recupero da maschera dei valori degli attributi dinamici
	 * associati al documento, nella modalità per il salvataggio dei dati da maschera:
	 * gli attributi LISTA hanno i valori interni mappati con i NUMERI delle colonne
	 * 
	 */
	public Map<String, Object> getAttributiDinamiciDoc() {
		Map<String, Object> attributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (attributiDinamiciDoc == null) {
						attributiDinamiciDoc = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, 
					// i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					attributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaValori(detailRecord));
				}
			}
		}
		return attributiDinamiciDoc;
	}

	/**
	 * Metodo per il recupero da maschera dei tipi degli attributi dinamici
	 * associati al documento
	 * 
	 */
	public Map<String, String> getTipiAttributiDinamiciDoc() {
		Map<String, String> tipiAttributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (tipiAttributiDinamiciDoc == null) {
						tipiAttributiDinamiciDoc = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal
					// vm, i valori degli attributi lista non li prende
					// correttamente
					// final Record detailRecord = new
					// Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					tipiAttributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaTipiValori(detailRecord));
				}
			}
		}
		return tipiAttributiDinamiciDoc;
	}
	
	/**
	 * Metodo per il recupero da maschera delle mappe delle colonne degli attributi dinamici
	 * di tipo LISTA associati al documento
	 * 
	 */
	public Map<String, String> getColonneListeAttributiDinamiciDoc() {
		Map<String, String> mappaColonneListaAttributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (mappaColonneListaAttributiDinamiciDoc == null) {
						mappaColonneListaAttributiDinamiciDoc = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal
					// vm, i valori degli attributi lista non li prende
					// correttamente
					// final Record detailRecord = new
					// Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					mappaColonneListaAttributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaColonneListe(detailRecord));
				}
			}
		}
		return mappaColonneListaAttributiDinamiciDoc;
	}
	
	public void salvaAttributiDinamiciDoc(boolean flgIgnoreObblig, String rowidDoc, String activityNameWF, String esitoActivityWF, final ServiceCallback<Record> callback) {
		
		if (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0) {
			Record lRecordDoc = new Record();
			lRecordDoc.setAttribute("rowId", rowidDoc);
			lRecordDoc.setAttribute("valori", getAttributiDinamiciDoc());
			lRecordDoc.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
//			lRecordDoc.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
			lRecordDoc.setAttribute("activityNameWF", activityNameWF);
			lRecordDoc.setAttribute("esitoActivityWF", esitoActivityWF);
			GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("AttributiDinamiciDocDatasource");
			if (flgIgnoreObblig) {
				lGWTRestService.addParam("flgIgnoreObblig", "1");
			}
			lGWTRestService.call(lRecordDoc, new ServiceCallback<Record>() {

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
	
	public void caricaAttributiDinamiciDoc(String nomeFlussoWF, String processNameWF, String activityName, String idTipoDoc, String rowidDoc) {
		
		attributiAddDocLayouts = new HashMap<String, VLayout>();
		attributiAddDocDetails = new HashMap<String, AttributiDinamiciDetail>();
		if (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0) {
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
			lGwtRestService.addParam("nomeFlussoWF", nomeFlussoWF);
			lGwtRestService.addParam("processNameWF", processNameWF);
			lGwtRestService.addParam("activityNameWF", activityName);
			Record lAttributiDinamiciRecord = new Record();
			lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_DOCUMENTS");
			lAttributiDinamiciRecord.setAttribute("rowId", rowidDoc);
			lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoDoc);
			lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
					if (attributiAdd != null && !attributiAdd.isEmpty()) {						
						HashMap<String, RecordList> mappaAttributiAddCategoria = new HashMap<String, RecordList>();						
						for (int i = 0; i < attributiAdd.getLength(); i++) {
							Record attr = attributiAdd.get(i);
							if (attr.getAttribute("categoria") != null && !"".equals(attr.getAttribute("categoria"))) {
								if(!mappaAttributiAddCategoria.containsKey(attr.getAttribute("categoria"))) {
									mappaAttributiAddCategoria.put(attr.getAttribute("categoria"), new RecordList());
								} 
								mappaAttributiAddCategoria.get(attr.getAttribute("categoria")).add(attr);
							}
						}						
						for (final String key : attributiAddDocTabs.keySet()) {							
							RecordList attributiAddCategoria = mappaAttributiAddCategoria.get(key) != null ? mappaAttributiAddCategoria.get(key) : new RecordList();							
							if (!attributiAddCategoria.isEmpty()) {
								AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
										.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
										.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null, tabSet, key);
								detail.setCanEdit(new Boolean(isEseguibile()));
								String messaggioTab = getMessaggioTab(key);
								if (messaggioTab != null && !"".equals(messaggioTab)) {
									Label labelMessaggioTab = new Label(messaggioTab);
									labelMessaggioTab.setAlign(Alignment.LEFT);
									labelMessaggioTab.setWidth100();
									labelMessaggioTab.setHeight(2);
									labelMessaggioTab.setPadding(5);
									detail.addMember(labelMessaggioTab, 0);
								}
								attributiAddDocDetails.put(key, detail);
								VLayout layout = new VLayout();
								layout.setHeight100();
								layout.setWidth100();
								layout.setMembers(detail);
								attributiAddDocLayouts.put(key, layout);
								VLayout layoutTab = new VLayout();
								layoutTab.addMember(layout);
								Tab tab = new Tab("<b>" + attributiAddDocTabs.get(key) + "</b>");
								tab.setAttribute("tabID", key);
								tab.setPrompt(attributiAddDocTabs.get(key));
								tab.setPane(layoutTab);
								tabSet.addTab(tab);
							}
						}						
					}
					afterCaricaAttributiDinamiciDoc();
				}
			});
		} else {
			afterCaricaAttributiDinamiciDoc();
		}
	}
	
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
	
	/**
	 * Metodo che viene richiamato alla fine del caricamento degli attributi
	 * dinamici del documento
	 * 
	 */
	protected void afterCaricaAttributiDinamiciDoc() {
		afterShow();
	}
	
	public void afterShow() {
		
	}
	
	public void readOnlyMode() {
		viewMode();		
		if(listaAllegatiItem != null) {
			listaAllegatiItem.readOnlyMode();
		}
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		
		super.setCanEdit(canEdit);
		
		setCanEdit(false, ufficioRevisioneOrganigrammaForm);
	}
	
	public boolean hasActionUnioneFile() {
		return flgUnioneFile != null && flgUnioneFile;
	}
	
	public boolean hasActionFirma() {
		return flgFirmaFile != null && flgFirmaFile;
	}
	
	public boolean isEsitoTaskOk(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return (esito == null || esitiTaskOk == null || (esito != null && esitiTaskOk != null && esitiTaskOk.contains(esito)));		
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
	
	public String getAzione() {
		return azione;
	}
	
	public void anteprimaPropostaDaModello(boolean hasDatiSensibili) {
		if(hasDatiSensibili) {
			generaPropostaDaModelloVersIntegrale(new ServiceCallback<Record>() {
				
				@Override
				public void execute(final Record recordPreview) {
					generaPropostaDaModelloVersConOmissis(new ServiceCallback<Record>() {
						
						@Override
						public void execute(final Record recordPreviewOmissis) {
							previewWithCallback(recordPreview, new ServiceCallback<Record>() {
						
								@Override
								public void execute(Record object) {
									preview(recordPreviewOmissis);
								}
							});
						}
					});
				}
			});
		} else {
			generaPropostaDaModelloVersIntegrale(new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record recordPreview) {
					preview(recordPreview);
				}
			});
		}
	}
	
	public void generaPropostaDaModelloVersIntegrale() {
		generaPropostaDaModelloVersIntegrale(null);
	}
	
	public void generaPropostaDaModelloVersIntegrale(final ServiceCallback<Record> callback) {
		generaPropostaDaModello(true, callback);
	}
	
	public void generaPropostaDaModelloVersConOmissis() {
		generaPropostaDaModelloVersConOmissis(null);
	}
	
	public void generaPropostaDaModelloVersConOmissis(final ServiceCallback<Record> callback) {
		generaPropostaDaModello(false, callback);
	}
	
	public void generaPropostaDaModello(boolean flgMostraDatiSensibili, final ServiceCallback<Record> callback) {
		
		Record record = getRecordToSave();
		record.setAttribute("idModello", idModAssDocTask != null ? idModAssDocTask : "");
		record.setAttribute("nomeModello", nomeModAssDocTask != null ? nomeModAssDocTask : "");
		record.setAttribute("displayFilenameModello", displayFilenameModAssDocTask != null ? displayFilenameModAssDocTask : "");
		record.setAttribute("flgMostraDatiSensibili", flgMostraDatiSensibili);
		
		Layout.showWaitPopup("Generazione anteprima proposta in corso...");				
		final GWTRestDataSource lPropostaOrganigrammaDataSource = new GWTRestDataSource("PropostaOrganigrammaDataSource");
		lPropostaOrganigrammaDataSource.executecustom("generaPropostaDaModello", record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordPreview = response.getData()[0];
					if(callback != null) {
						callback.execute(recordPreview);
					}
				}				
			}		
		});
	}
	
	public void preview(final Record recordPreview) {
		previewWithCallback(recordPreview, null);
	}
	
	public void previewWithCallback(final Record recordPreview, final ServiceCallback<Record> closeCallback) {
		if (recordPreview.getAttribute("nomeFile") != null && recordPreview.getAttribute("nomeFile").endsWith(".pdf")) {
			new PreviewWindow(recordPreview.getAttribute("uri"), false, new InfoFileRecord(recordPreview.getAttributeAsRecord("infoFile")), "FileToExtractBean", recordPreview.getAttribute("nomeFile")) {
			
				@Override
				public void manageCloseClick() {
					super.manageCloseClick();
					if(closeCallback != null) {
						closeCallback.execute(recordPreview);
					}
				};
			};
		} else {
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", recordPreview.getAttribute("nomeFile"));
			lRecord.setAttribute("uri", recordPreview.getAttribute("uri"));
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", "false");
			DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
			if(closeCallback != null) {
				closeCallback.execute(recordPreview);
			}
		}
	}
	
	protected String getIdUd() {
		return idUd;
	}
	
	public String getIdProcessTask() {
		return idProcess;
	}
	
	public String getIdTaskCorrente() {
		return nome.substring(0, nome.indexOf("|*|"));
	}
		
	/**
	 * Metodo che restituisce la mappa dei modelli relativi agli atti associati
	 * al task, da passare alla sezione degli allegati
	 * 
	 */
	public HashSet<String> getTipiModelliAttiInAllegati() {
		return dettaglioPraticaLayout != null ? dettaglioPraticaLayout.getTipiModelliAtti() : null;
	}
	
	public boolean isObbligatorioFileInAllegati() {
		return true;
	}
	
	/**
	 * Metodo che costruisce il record per la chiamata al servizio che genera
	 * l'allegato da modello iniettando i valori presenti in maschera
	 * 
	 */
	public Record getRecordCaricaModelloInAllegati(String idModello, String tipoModello) {
		
		final Record modelloRecord = new Record();
		modelloRecord.setAttribute("idModello", idModello);
		modelloRecord.setAttribute("tipoModello", tipoModello);
		modelloRecord.setAttribute("idUd", getIdUd());
		if (attributiAddDocDetails != null) {
			modelloRecord.setAttribute("valori", getAttributiDinamiciDoc());
			modelloRecord.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
			modelloRecord.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
		}		
		return modelloRecord;
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
	public Record getRecordEvento() {
		return recordEvento;
	}

	@Override
	public boolean hasDocumento() {
		return false;
	}

}
