/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridFieldIfFunction;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressHandler;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.DocumentDetail;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility.StampaEtichettaCallback;
import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.EventoAMCPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioMail.InvioNotificaInteropWindow;
import it.eng.auriga.ui.module.layout.client.invioUD.InvioUDMailWindow;
import it.eng.auriga.ui.module.layout.client.osservazioniNotifiche.OsservazioniNotificheWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRispostaProtWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.ModificaRegProtAssociatoImgWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.items.CustomTaskButton;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.ModificaCodAttoCMTOPopup;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.TaskNuovaPropostaAtto2CompletaDetail;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.ApponiTimbroWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.DestUOProtocollazioneWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.DettaglioDatiPubblAttoPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.DettaglioOpereAttoPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetail;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailEntrata;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailInterna;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailUscita;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.SceltaTipoDocPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.StampaEtichettaPopup;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.RichiestaAccessoAttiDetail;
import it.eng.auriga.ui.module.layout.client.scrivania.CercaUDPopup;
import it.eng.auriga.ui.module.layout.client.scrivania.ScrivaniaLayout;
import it.eng.auriga.ui.module.layout.client.scrivania.ShowInMenuFunctionFromScrivania;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.auriga.ui.module.layout.client.timbra.TimbraWindow;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroUtil;
import it.eng.auriga.ui.module.layout.shared.invioRaccomandate.ETypePoste;
import it.eng.auriga.ui.module.layout.shared.util.AzioniRapide;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomAdvancedTreeLayout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.GenericCallback;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu.NavigationContextMenuFrom;
import it.eng.utility.ui.module.layout.client.common.ReloadListCallback;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.menu.MenuUtil;
import it.eng.utility.ui.module.layout.client.menu.ShowInMenuFunction;

public class ArchivioList extends CustomList {

	private ListGridField folderUp;
	private ListGridField icona;
	private ListGridField idUdFolder;
	private ListGridField flgUdFolder;
	private ListGridField nome;
	private ListGridField segnatura;
	private ListGridField nroSecondario;
	private ListGridField segnaturaXOrd;
	private ListGridField numero;
	private ListGridField anno;
	private ListGridField tsApertura;
	private ListGridField tsChiusura;
	private ListGridField tsRegistrazione;
	private ListGridField tsDocumento;
	private ListGridField fascicoliApp;
	private ListGridField livelloRiservatezza;
//	private ListGridField responsabileFascicolo;
	private ListGridField priorita;
	private ListGridField idTipo; //idTipoDoc
	private ListGridField tipo; //nomeTipoDoc
	private ListGridField score;
	private ListGridField idFolderApp;
	private ListGridField percorsoFolderApp;
	private ListGridField flgSelXFinalita;
	private ListGridField oggetto;
	private ListGridField descContenutiFascicolo;
	private ListGridField stato;
	private ListGridField flgTipoProv;
	private ListGridField assegnatari;
	private ListGridField mittenti;
	private ListGridField destinatari;
	private ListGridField flgRicevutaViaEmail;
	private ListGridField flgInviataViaEmail;
	private ListGridField flgAnnReg;
	private ListGridField flgAssegnatoAMe;
	private ListGridField flgNotificatoAMe;
	private ListGridField attoAutAnnullamento;
	private ListGridField tsPresaInCarico;
	private ListGridField annotazioniApposte;
	private ListGridField societa;
	private ListGridField inConoscenzaA;

	// Solo per DOCUMENTI
	private ListGridField utenteProtocollante;
	private ListGridField uoProtocollante;
	private ListGridField repertorio;
	private ListGridField estremiUDTrasmessoInUscitaCon;
	private ListGridField motivoAnnullamento;

	// Solo per RICEVUTI PER COMPETENZA
	private ListGridField flgPresaInCarico;

	// Solo per le NEWS
	private ListGridField tsRicezione;
	private ListGridField prioritaInvioNotifiche;
	private ListGridField estremiInvioNotifiche;
	// Solo per gli INVIATI
	private ListGridField tsInvio;
	private ListGridField destinatariInvio;
	// Solo per gli ELIMINATI
	private ListGridField tsEliminazione;
	private ListGridField eliminatoDa;
	
	// Modulo ATTI
	private ListGridField nroProvvisorioAtto;
//	private ListGridField dataAvvioIterAtto;
	private ListGridField pubblicazione;
	private ListGridField uoProponente;
	private ListGridField programmazioneAcquisti;
	private ListGridField codiceCIG;
	private ListGridField cui;
	
	private ListGridField statoTrasmissioneMail;

	private boolean fromScrivania = false;

	private String idNode;
	private String tipoNodo;
	private String idFolder;
	private ListGridField idDocPrimario;
	
	private ReloadListCallback mReloadListCallback;
	
	private ListGridField dtEsecutivita;
	private ListGridField flgImmediatamenteEseg;
	
	private ListGridField perizie;
	
	
	private boolean isActiveModal;

	private ListGridField centroDiCosto;
	private ListGridField dataScadenza;
	
	private ListGridField flgSottopostoControlloRegAmm;
	private ListGridField nrLiquidazioneContestuale;
	private ListGridField idProcessoControlloRegAmm;
	
//	protected RecordList recordSelezionatiPerFirma;
	
	private ListGridField flgDeterminaContrarreTramiteGara;
	private ListGridField flgDeterminaAggiudicaGara;
	private ListGridField flgDeterminaRimodulazioneSpesaPostAggiudica;
	private ListGridField flgLiquidazioneContestualeImpegni;
	private ListGridField flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi;
	private ListGridField tipoAffidamento;
	private ListGridField materiaTipoAtto;
	private ListGridField progettoLLPP;
	private ListGridField beniServizi;
	private ListGridField ordinativi;

	private ListGridField flgDatiCompletiPerProtocollazione;
	private ListGridField msgDiInvio;
	private ListGridField flgPresenzaContratti;
	

	// Solo per il nodo "Immagini non associate ai protocolli"
	private ListGridField tsScansione; 
	private ListGridField datiBarcode;
	private ListGridField sedeScansione;
	private ListGridField timbroProtRiconosciuto;
	private ListGridField nroProtRiconosciuto;
	private ListGridField tsRicezioneDoc;
	private ListGridField motivoScarto;
	private ListGridField statoTrasferimentoBloomfleet;
	
	
	public ArchivioList(String nomeEntita) {
		this(nomeEntita, false);
	}

	public ArchivioList(String nomeEntita, boolean fromScrivania) {
		this(nomeEntita, fromScrivania, null, null, null);
	}

	public ArchivioList(String nomeEntita, boolean pFromScrivania, String pIdNode, String pTipoNodo, final String pIdFolder) {

		super(nomeEntita);

		initReloadListCallback();

		this.fromScrivania = pFromScrivania;
		isActiveModal = AurigaLayout.getImpostazioniSceltaAccessibilitaAsBoolean("mostraModal");

		idNode = pIdNode;
		tipoNodo = pTipoNodo != null && !"".equals(pTipoNodo) ? pTipoNodo : "FD";
		idFolder = pIdFolder;

		setAutoFetchData(false);
		setDataFetchMode(FetchMode.BASIC);
		setSelectionType(SelectionStyle.NONE);

		idDocPrimario = new ListGridField("idDocPrimario");
		idDocPrimario.setHidden(true);
		idDocPrimario.setCanHide(false);

		idUdFolder = new ListGridField("idUdFolder");
		idUdFolder.setHidden(true);
		idUdFolder.setCanHide(false);

		flgUdFolder = new ListGridField("flgUdFolder");
		flgUdFolder.setHidden(true);
		flgUdFolder.setCanHide(false);

		idFolderApp = new ListGridField("idFolderApp");
		idFolderApp.setHidden(true);
		idFolderApp.setCanHide(false);

		percorsoFolderApp = new ListGridField("percorsoFolderApp");
		percorsoFolderApp.setHidden(true);
		percorsoFolderApp.setCanHide(false);
		
		motivoAnnullamento = new ListGridField("motivoAnnullamento", I18NUtil.getMessages().archivio_list_motivoAnnullamentoField_title());
		motivoAnnullamento.setHidden(true);
		
		flgSelXFinalita = new ListGridField("flgSelXFinalita");
		flgSelXFinalita.setHidden(true);
		flgSelXFinalita.setCanHide(false);

		segnatura = new ListGridField("segnatura", I18NUtil.getMessages().archivio_list_segnaturaField_title());
		segnatura.setHidden(true);
		segnatura.setCanHide(false);

		if (AurigaLayout.getImpostazioniAperturaDettaglioPerRicercaPuntualeAsBoolean("apriDettaglioRicercaPuntuale"))
			addDataArrivedHandler(new DataArrivedHandler() {
				
				@Override
				public void onDataArrived(DataArrivedEvent event) {
					// TODO Auto-generated method stub
					int numberRecords = getRecords().length;
					if (numberRecords == 1) {
						ListGridRecord listGridRecord = getRecords()[0];
						manageDetailButtonClick(listGridRecord);
					}
				}
		});		
		folderUp = new ControlListGridField("folderUp");
		folderUp.setAttribute("custom", true);
		folderUp.setShowHover(true);
		folderUp.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("idFolderApp") != null && !"".equals(record.getAttribute("idFolderApp"))
						&& record.getAttribute("percorsoFolderApp") != null && !"".equals(record.getAttribute("percorsoFolderApp"))) {
					return buildImgButtonHtml("buttons/folderUp.png","Apri fascicoli/cartelle superiori");
				}
				return null;
			}
		});
		folderUp.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("idFolderApp") != null && !"".equals(record.getAttribute("idFolderApp"))
						&& record.getAttribute("percorsoFolderApp") != null && !"".equals(record.getAttribute("percorsoFolderApp"))) {
					return "Apri fascicoli/cartelle superiori";
				}
				return null;
			}
		});
		folderUp.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				Record record = event.getRecord();
				if (record.getAttribute("idFolderApp") != null && !"".equals(record.getAttribute("idFolderApp"))
						&& record.getAttribute("percorsoFolderApp") != null && !"".equals(record.getAttribute("percorsoFolderApp"))) {
					if (layout instanceof ArchivioLayout) {
						Menu contextMenu = new Menu();
						String[] idFolderAppArray = new StringSplitterClient(record.getAttribute("idFolderApp"), "|*|").getTokens();
						String[] percorsoFolderAppArray = new StringSplitterClient(record.getAttribute("percorsoFolderApp"), "|*|").getTokens();
						for (int i = 0; i < idFolderAppArray.length; i++) {
							MenuItem menuItem = new MenuItem(percorsoFolderAppArray[i]);
							final String idFolderApp = idFolderAppArray[i];
							menuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									((ArchivioLayout) layout).esploraFromList(idFolderApp);
								}
							});
							contextMenu.addItem(menuItem);
						}
						contextMenu.showContextMenu();
					}
				}
			}
		});

		icona = new ControlListGridField("icona");
		icona.setAttribute("custom", true);
		icona.setShowHover(true);
		icona.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("F".equals(record.getAttributeAsString("flgUdFolder"))) {
					String nroFascicolo = record.getAttributeAsString("nroFascicolo");
					String nroSottofascicolo = record.getAttributeAsString("nroSottofascicolo");
					String nroInserto = record.getAttributeAsString("nroInserto");
					
					if (fromScrivania) {
						if (nroInserto != null && !"".equals(nroInserto)) {
							return buildIconHtml("archivio/flgUdFolder/inserto.png", "inserto");
						} else if (nroSottofascicolo != null && !"".equals(nroSottofascicolo)) {
							return buildIconHtml("archivio/flgUdFolder/sottofascicolo.png", "sotto-fascicolo");
						} else if (nroFascicolo != null && !"".equals(nroFascicolo)) {
							return buildIconHtml("archivio/flgUdFolder/fascicolo.png", "fascicolo");
						} else {
							return buildIconHtml("archivio/flgUdFolder/F.png", "cartella");
						}
					} else {
						if (nroInserto != null && !"".equals(nroInserto)) {
							return buildIconHtml("archivio/flgUdFolder/inserto.png", I18NUtil.getMessages().archivio_list_iconaFolderButton_prompt() + " inserto");
						} else if (nroSottofascicolo != null && !"".equals(nroSottofascicolo)) {
							return buildImgButtonHtml("archivio/flgUdFolder/sottofascicolo.png", I18NUtil.getMessages().archivio_list_iconaFolderButton_prompt() + " sotto-fascicolo");
						} else if (nroFascicolo != null && !"".equals(nroFascicolo)) {
							return buildImgButtonHtml("archivio/flgUdFolder/fascicolo.png", I18NUtil.getMessages().archivio_list_iconaFolderButton_prompt() + " fascicolo");
						} else {
							return buildImgButtonHtml("archivio/flgUdFolder/F.png", I18NUtil.getMessages().archivio_list_iconaFolderButton_prompt() + " cartella");
						}
					}
				} else {
					Integer nroDocConFile = (record.getAttributeAsString("nroDocConFile") != null && !"".equals(record.getAttributeAsString("nroDocConFile"))) ? new Integer(
							record.getAttributeAsString("nroDocConFile")) : 0;
					Integer nroDocConFileFirmati = (record.getAttributeAsString("nroDocConFileFirmati") != null && !"".equals(record
							.getAttributeAsString("nroDocConFileFirmati"))) ? new Integer(record.getAttributeAsString("nroDocConFileFirmati")) : 0;
					Integer nroDocConFileDaScanner = (record.getAttributeAsString("nroDocConFileDaScanner") != null && !"".equals(record
							.getAttributeAsString("nroDocConFileDaScanner"))) ? new Integer(record.getAttributeAsString("nroDocConFileDaScanner")) : 0;
					if (nroDocConFile > 0) {
						return buildIconHtml("archivio/flgUdFolder/docConFile.png", "documento con " + nroDocConFile + " file associati di cui: " + nroDocConFileFirmati + " firmati digitalmente; "
								+ nroDocConFileDaScanner + " acquisiti da scanner");
					} else {
						return buildIconHtml("archivio/flgUdFolder/U.png", "documento senza file associati");
					}
				}
			}
		});
		icona.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("F".equals(record.getAttributeAsString("flgUdFolder"))) {
					String nroFascicolo = record.getAttributeAsString("nroFascicolo");
					String nroSottofascicolo = record.getAttributeAsString("nroSottofascicolo");
					String nroInserto = record.getAttributeAsString("nroInserto");
					
					if (fromScrivania) {
						if (nroInserto != null && !"".equals(nroInserto)) {
							return "inserto";
						} else if (nroSottofascicolo != null && !"".equals(nroSottofascicolo)) {
							return "sotto-fascicolo";
						} else if (nroFascicolo != null && !"".equals(nroFascicolo)) {
							return "fascicolo";
						}
					} else {
						if (nroInserto != null && !"".equals(nroInserto)) {
							return I18NUtil.getMessages().archivio_list_iconaFolderButton_prompt() + " inserto";
						} else if (nroSottofascicolo != null && !"".equals(nroSottofascicolo)) {
							return I18NUtil.getMessages().archivio_list_iconaFolderButton_prompt() + " sotto-fascicolo";
						} else if (nroFascicolo != null && !"".equals(nroFascicolo)) {
							return I18NUtil.getMessages().archivio_list_iconaFolderButton_prompt() + " fascicolo";
						} else {
							return I18NUtil.getMessages().archivio_list_iconaFolderButton_prompt() + " cartella";
						}
					}
				} else {
					Integer nroDocConFile = (record.getAttributeAsString("nroDocConFile") != null && !"".equals(record.getAttributeAsString("nroDocConFile"))) ? new Integer(
							record.getAttributeAsString("nroDocConFile")) : 0;
					Integer nroDocConFileFirmati = (record.getAttributeAsString("nroDocConFileFirmati") != null && !"".equals(record
							.getAttributeAsString("nroDocConFileFirmati"))) ? new Integer(record.getAttributeAsString("nroDocConFileFirmati")) : 0;
					Integer nroDocConFileDaScanner = (record.getAttributeAsString("nroDocConFileDaScanner") != null && !"".equals(record
							.getAttributeAsString("nroDocConFileDaScanner"))) ? new Integer(record.getAttributeAsString("nroDocConFileDaScanner")) : 0;
					if (nroDocConFile > 0) {
						return "documento con " + nroDocConFile + " file associati di cui: " + nroDocConFileFirmati + " firmati digitalmente; "
								+ nroDocConFileDaScanner + " acquisiti da scanner";
					} else {
						return "documento senza file associati";
					}
				}
				return null;
			}
		});
		icona.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				Record record = event.getRecord();
				if ("F".equals(record.getAttributeAsString("flgUdFolder")) && !fromScrivania) {
					if (layout instanceof ArchivioLayout) {
						((ArchivioLayout) layout).esploraFromList((record.getAttribute("idUdFolder")));
					}
				}
			}
		});

		annotazioniApposte = new ListGridField("note", I18NUtil.getMessages().archivio_list_annotazioniApposteField_title());
		annotazioniApposte.setType(ListGridFieldType.ICON);
		annotazioniApposte.setWidth(30);
		annotazioniApposte.setIconWidth(16);
		annotazioniApposte.setIconHeight(16);
		annotazioniApposte.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("note") != null) {
					return buildImgButtonHtml("file/icon_postit.png", "Annotazioni apposte");
				}
				return null;
			}
		});
		
		annotazioniApposte.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("note") != null) {
					return "Annotazioni apposte";
				}
				return null;
			}
		});
		
		annotazioniApposte.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final Record record = event.getRecord();
				if (record.getAttribute("flgUdFolder").equals("U")) {
					Record lRecordToLoad = new Record();
					lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
					getAbilitazioniProtocolloDataSource().getData(lRecordToLoad,new DSCallback() {
						
						@Override
						public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
							// TODO Auto-generated method stub
							if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
								final Record detailRecord = dsResponse.getData()[0];
								manageAnnotazioniApposte(record, detailRecord.getAttributeAsBoolean("abilModificaDati"));
							}
						}
					});
				} else if (record.getAttribute("flgUdFolder").equals("F")) {
						getAbilitazioniArchivioDataSource().performCustomOperation("get", record, new DSCallback() {
							
							@Override
							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
								if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
									final Record detailRecord = dsResponse.getData()[0];
									manageAnnotazioniApposte(record, detailRecord.getAttributeAsBoolean("abilModificaDati"));
								}
							}
						});
				}
			}
		});
		
		societa = new ListGridField("societa", I18NUtil.getMessages().archivio_list_societaField_title());
		// Se il PARAMETRO_DB ATTIVA_MULTISOCIETA non è attivo lo nascondo
		if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_MULTISOCIETA")) {
			societa.setHidden(true);
			societa.setCanHide(false);
		}
		
		flgTipoProv = new ListGridField("flgTipoProv", I18NUtil.getMessages().archivio_list_tipoProtocolloField_title());
		flgTipoProv.setType(ListGridFieldType.ICON);
		flgTipoProv.setWidth(30);
		flgTipoProv.setIconWidth(16);
		flgTipoProv.setIconHeight(16);
		Map<String, String> flgTipoProvValueIcons = new HashMap<String, String>();
		flgTipoProvValueIcons.put("E", "menu/protocollazione_entrata.png");
		flgTipoProvValueIcons.put("U", "menu/protocollazione_uscita.png");
		flgTipoProvValueIcons.put("I", "menu/protocollazione_interna.png");
		flgTipoProvValueIcons.put("", "blank.png");
		flgTipoProv.setValueIcons(flgTipoProvValueIcons);
		Map<String, String> flgTipoProvValueHovers = new HashMap<String, String>();
		flgTipoProvValueHovers.put("E", I18NUtil.getMessages().archivio_list_tipoVersoInEntrataAlt_value());
		flgTipoProvValueHovers.put("U", I18NUtil.getMessages().archivio_list_tipoVersoInUscitaAlt_value());
		flgTipoProvValueHovers.put("I", I18NUtil.getMessages().archivio_list_tipoVersoInternoAlt_value());
		flgTipoProvValueHovers.put("", "");
		flgTipoProv.setAttribute("valueHovers", flgTipoProvValueHovers);

		statoTrasmissioneMail = new ListGridField("statoTrasmissioneEmail", I18NUtil.getMessages().stato_trasmissione_mail_title());
		statoTrasmissioneMail.setType(ListGridFieldType.ICON);
		statoTrasmissioneMail.setWidth(30);
		statoTrasmissioneMail.setIconWidth(16);
		statoTrasmissioneMail.setIconHeight(16);
		Map<String, String> statoInvioMailIcons = new HashMap<String, String>();
		statoInvioMailIcons.put("ERROR", "postaElettronica/statoConsolidamento/ko-red.png");
		statoInvioMailIcons.put("WARNING", "postaElettronica/statoConsolidamento/ko-arancione.png");
		statoInvioMailIcons.put("IN_PROGRESS", "postaElettronica/statoConsolidamento/presunto_ok.png");
		statoInvioMailIcons.put("CONSEGNA", "postaElettronica/statoConsolidamento/consegnata.png");
		statoTrasmissioneMail.setValueIcons(statoInvioMailIcons);
		Map<String, String> statoInvioMailValueHovers = new HashMap<String, String>();
		statoInvioMailValueHovers.put("ERROR", I18NUtil.getMessages().stato_trasmissione_mail_error());
		statoInvioMailValueHovers.put("WARNING", I18NUtil.getMessages().stato_trasmissione_mail_warning());
		statoInvioMailValueHovers.put("IN_PROGRESS", I18NUtil.getMessages().stato_trasmissione_mail_inprogress());
		statoInvioMailValueHovers.put("CONSEGNA", I18NUtil.getMessages().stato_trasmissione_mail_consegna());
		statoTrasmissioneMail.setAttribute("valueHovers", statoInvioMailValueHovers);

		oggetto = new ListGridField("oggetto", I18NUtil.getMessages().archivio_list_oggettoField_title());
		oggetto.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String oggetto = (String) record.getAttribute("oggetto");
				if (oggetto == null) return null;
				if (oggetto.length()>Layout.getGenericConfig().getMaxValueLength()){
					return oggetto.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
				} else return oggetto;				
			}
		});

		descContenutiFascicolo = new ListGridField("descContenutiFascicolo", I18NUtil.getMessages().archivio_list_descContenutiField_title());

		stato = new ListGridField("stato", I18NUtil.getMessages().archivio_list_statoField_title());
	
		nroSecondario = new ListGridField("nroSecondario", I18NUtil.getMessages().archivio_list_nroSecondarioField_title());
		
		segnaturaXOrd = new ListGridField("segnaturaXOrd", I18NUtil.getMessages().archivio_list_segnaturaField_title());
		segnaturaXOrd.setDisplayField("segnatura");
		segnaturaXOrd.setSortByDisplayField(false);
		segnaturaXOrd.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {

				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				manageDetailButtonClick(record);
			}
		});

		numero = new ListGridField("numero", I18NUtil.getMessages().archivio_list_numeroField_title());
		numero.setType(ListGridFieldType.INTEGER);
		
		anno = new ListGridField("anno", I18NUtil.getMessages().archivio_list_annoField_title());
		anno.setType(ListGridFieldType.INTEGER);
				
		idTipo = new ListGridField("idTipo");
		idTipo.setHidden(true);
		idTipo.setCanHide(false);

		tipo = new ListGridField("tipo", I18NUtil.getMessages().archivio_list_tipoField_title());
		
		nome = new ListGridField("nome", I18NUtil.getMessages().archivio_list_nomeField_title());
		
		assegnatari = new ListGridField("assegnatari", I18NUtil.getMessages().archivio_list_assegnatariField_title());
		assegnatari.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String assegnatari = (String) record.getAttribute("assegnatari");
				if (assegnatari == null) return null;
				if (assegnatari.length()>Layout.getGenericConfig().getMaxValueLength()){
					return assegnatari.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
				} else return assegnatari;				
			}
		});
		
		mittenti = new ListGridField("mittenti", I18NUtil.getMessages().archivio_list_mittentiField_title());
		mittenti.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String mittenti = (String) record.getAttribute("mittenti");
				if (mittenti == null) return null;
				if (mittenti.length()>Layout.getGenericConfig().getMaxValueLength()){
					return mittenti.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
				} else return mittenti;				
			}
		});
		
		destinatari = new ListGridField("destinatari", I18NUtil.getMessages().archivio_list_destinatariField_title());
		destinatari.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String destinatari = (String) record.getAttribute("destinatari");
				if (destinatari == null) return null;
				if (destinatari.length()>Layout.getGenericConfig().getMaxValueLength()){
					return destinatari.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
				} else return destinatari;				
			}
		});

		flgRicevutaViaEmail = new ListGridField("iconaFlgRicevutaViaEmail", I18NUtil.getMessages().archivio_list_flgRicevutaViaEmailField_title());
		flgRicevutaViaEmail.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgRicevutaViaEmail.setAlign(Alignment.CENTER);
		flgRicevutaViaEmail.setWrap(false);
		flgRicevutaViaEmail.setWidth(30);
		flgRicevutaViaEmail.setAttribute("custom", true);
		flgRicevutaViaEmail.setShowHover(true);
		flgRicevutaViaEmail.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgRicevutaViaEmail = (String) record.getAttribute("flgRicevutaViaEmail");
				if (flgRicevutaViaEmail != null && !"".equals(flgRicevutaViaEmail)) {
					if (flgRicevutaViaEmail.equals("INTEROP")) {
						return buildIconHtml("mail/" + flgRicevutaViaEmail + ".png","Ricevuta tramite e-mail interoperabile");
					} else if (flgRicevutaViaEmail.equals("PEC")) {
						return buildIconHtml("mail/" + flgRicevutaViaEmail + ".png","Ricevuta tramite PEC (non interoperabile)");
					} else if (flgRicevutaViaEmail.equals("PEO")) {
						return buildIconHtml("mail/" + flgRicevutaViaEmail + ".png","Ricevuta tramite posta elettronica ordinaria");
					}
				}
				return null;
			}
		});
		flgRicevutaViaEmail.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgRicevutaViaEmail = (String) record.getAttribute("flgRicevutaViaEmail");
				if (flgRicevutaViaEmail != null && !"".equals(flgRicevutaViaEmail)) {
					if (flgRicevutaViaEmail.equals("INTEROP")) {
						return "Ricevuta tramite e-mail interoperabile";
					} else if (flgRicevutaViaEmail.equals("PEC")) {
						return "Ricevuta tramite PEC (non interoperabile)";
					} else if (flgRicevutaViaEmail.equals("PEO")) {
						return "Ricevuta tramite posta elettronica ordinaria";
					}
				}
				return null;
			}
		});

		flgInviataViaEmail = new ListGridField("iconaFlgInviataViaEmail", I18NUtil.getMessages().archivio_list_flgInviataViaEmailField_title());
		flgInviataViaEmail.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgInviataViaEmail.setAlign(Alignment.CENTER);
		flgInviataViaEmail.setWrap(false);
		flgInviataViaEmail.setWidth(30);
		flgInviataViaEmail.setAttribute("custom", true);
		flgInviataViaEmail.setShowHover(true);
		flgInviataViaEmail.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgInviataViaEmail = (String) record.getAttribute("flgInviataViaEmail");
				if (flgInviataViaEmail != null && !"".equals(flgInviataViaEmail)) {
					String[] tokens = new StringSplitterClient(flgInviataViaEmail, ";").getTokens();
					if (tokens.length > 0) {
						String res = "Inviata tramite ";
						for (int i = 0; i < tokens.length; i++) {
							if (i > 0) {
								if (i == (tokens.length - 1)) {
									res += " e ";
								} else {
									res += ", ";
								}
							}
							if (tokens[i].equals("INTEROP")) {
								res += "e-mail interoperabile";
							} else if (tokens[i].equals("PEC")) {
								res += "PEC (non interoperabile)";
							} else if (tokens[i].equals("PEO")) {
								res += "posta elettronica ordinaria";
							}
						}
						return buildIconHtml("mail/inviata.png", res);
					}
				}
				return null;
			}
		});
		flgInviataViaEmail.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgInviataViaEmail = (String) record.getAttribute("flgInviataViaEmail");
				if (flgInviataViaEmail != null && !"".equals(flgInviataViaEmail)) {
					String[] tokens = new StringSplitterClient(flgInviataViaEmail, ";").getTokens();
					if (tokens.length > 0) {
						String res = "Inviata tramite ";
						for (int i = 0; i < tokens.length; i++) {
							if (i > 0) {
								if (i == (tokens.length - 1)) {
									res += " e ";
								} else {
									res += ", ";
								}
							}
							if (tokens[i].equals("INTEROP")) {
								res += "e-mail interoperabile";
							} else if (tokens[i].equals("PEC")) {
								res += "PEC (non interoperabile)";
							} else if (tokens[i].equals("PEO")) {
								res += "posta elettronica ordinaria";
							}
						}
						return res;
					}
				}
				return null;
			}
		});

		flgAnnReg = new ListGridField("flgAnnReg", "Annullamento registrazione");
		flgAnnReg.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgAnnReg.setType(ListGridFieldType.ICON);
		flgAnnReg.setWidth(30);
		flgAnnReg.setIconWidth(16);
		flgAnnReg.setIconHeight(16);
		Map<String, String> flgAnnRegValueIcons = new HashMap<String, String>();
		flgAnnRegValueIcons.put("A", "protocollazione/annullata.png");
		flgAnnRegValueIcons.put("R", "protocollazione/richAnnullamento.png");
		flgAnnRegValueIcons.put("D", "protocollazione/annullamentoAutorizzato.png");
		flgAnnRegValueIcons.put("", "blank.png");
		flgAnnReg.setValueIcons(flgAnnRegValueIcons);
		flgAnnReg.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute("datiAnnReg");
			}
		});

		flgAssegnatoAMe = new ListGridField("flgAssegnatoAMe", "Assegnato a me");
		flgAssegnatoAMe.setType(ListGridFieldType.INTEGER);
		flgAssegnatoAMe.setAlign(Alignment.CENTER);
		flgAssegnatoAMe.setWrap(false);
		flgAssegnatoAMe.setWidth(30);
		flgAssegnatoAMe.setIconWidth(16);
		flgAssegnatoAMe.setIconHeight(16);
		flgAssegnatoAMe.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgAssegnatoAMe = record.getAttribute("flgAssegnatoAMe");
				if (flgAssegnatoAMe != null && "1".equals(flgAssegnatoAMe)) {
					return buildIconHtml("ok.png", "Assegnato a me");
				}
				return null;
			}
		});

		final String flgNotificatoAMeTitle = (fromScrivania && idNode != null && (idNode.startsWith("FD.2NA") || idNode.startsWith("D.2NA") || idNode.startsWith("F.2NA"))) ? "Notificato a me" : "Inviato a me";

		flgNotificatoAMe = new ListGridField("flgNotificatoAMe", flgNotificatoAMeTitle);
		flgNotificatoAMe.setType(ListGridFieldType.INTEGER);
		flgNotificatoAMe.setAlign(Alignment.CENTER);
		flgNotificatoAMe.setWrap(false);
		flgNotificatoAMe.setWidth(30);
		flgNotificatoAMe.setIconWidth(16);
		flgNotificatoAMe.setIconHeight(16);
		flgNotificatoAMe.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNotificatoAMe = record.getAttribute("flgNotificatoAMe");
				if (flgNotificatoAMe != null && "1".equals(flgNotificatoAMe)) {
					return buildIconHtml("ok.png", flgNotificatoAMeTitle);
				}
				return null;
			}
		});

		tsApertura = new ListGridField("tsApertura", I18NUtil.getMessages().archivio_list_tsAperturaField_title());
		tsApertura.setType(ListGridFieldType.DATE);
		tsApertura.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		tsApertura.setWrap(false);

		tsChiusura = new ListGridField("tsChiusura", I18NUtil.getMessages().archivio_list_tsChiusuraField_title());
		tsChiusura.setType(ListGridFieldType.DATE);
		tsChiusura.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		tsChiusura.setWrap(false);

		tsRegistrazione = new ListGridField("tsRegistrazione", I18NUtil.getMessages().archivio_list_tsRegistrazioneField_title());
		tsRegistrazione.setType(ListGridFieldType.DATE);
		tsRegistrazione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsRegistrazione.setWrap(false);
		
		tsPresaInCarico = new ListGridField("tsPresaInCarico", I18NUtil.getMessages().auriga_filter_scrivania_dtPresaInCarico_title());
		tsPresaInCarico.setType(ListGridFieldType.DATE);
		tsPresaInCarico.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsPresaInCarico.setWrap(false);

		tsDocumento = new ListGridField("tsDocumento", I18NUtil.getMessages().archivio_list_tsDocumentoField_title());
		tsDocumento.setType(ListGridFieldType.DATE);
		tsDocumento.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		tsDocumento.setWrap(false);

		fascicoliApp = new ListGridField("fascicoliApp", "Fascicolo/i appartenenza");

		livelloRiservatezza = new ListGridField("livelloRiservatezza", I18NUtil.getMessages().archivio_list_livelloRiservatezzaField_title());
		livelloRiservatezza.setType(ListGridFieldType.INTEGER);
		livelloRiservatezza.setAlign(Alignment.CENTER);
		livelloRiservatezza.setWrap(false);
		livelloRiservatezza.setWidth(30);
		livelloRiservatezza.setIconWidth(16);
		livelloRiservatezza.setIconHeight(16);
		livelloRiservatezza.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String valueLivelloRiservatezza = record.getAttribute("livelloRiservatezza");
				Integer livelloRiservatezza = valueLivelloRiservatezza != null && !"".equals(String.valueOf(valueLivelloRiservatezza)) ? new Integer(String
						.valueOf(valueLivelloRiservatezza)) : null;
				if (livelloRiservatezza != null) {
					return buildIconHtml("lock.png", "Riservato");
				}
				return null;
			}
		});
		livelloRiservatezza.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String valueLivelloRiservatezza = record.getAttribute("livelloRiservatezza");
				Integer livelloRiservatezza = valueLivelloRiservatezza != null && !"".equals(String.valueOf(valueLivelloRiservatezza)) ? new Integer(String
						.valueOf(valueLivelloRiservatezza)) : null;
				if (livelloRiservatezza != null) {
					return "Riservato";
				}
				return null;
			}
		});

//		responsabileFascicolo = new ListGridField("responsabileFascicolo", I18NUtil.getMessages().archivio_list_responsabileFascicoloField_title());

		priorita = new ListGridField("priorita", I18NUtil.getMessages().archivio_list_prioritaField_title());
		priorita.setType(ListGridFieldType.INTEGER);
		priorita.setAlign(Alignment.CENTER);
		priorita.setShowHover(false);
		priorita.setWrap(false);
		priorita.setWidth(30);
		priorita.setIconWidth(16);
		priorita.setIconHeight(16);
		priorita.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Integer priorita = value != null && !"".equals(String.valueOf(value)) ? new Integer(String.valueOf(value)) : null;
				if (priorita != null) {
					return buildIconHtml("protocollazione/riservatezza/" + priorita.toString() + ".png", I18NUtil.getMessages().archivio_list_prioritaField_title());
				}
				return null;
			}
		});

		String tsRicezioneTitle = I18NUtil.getMessages().archivio_list_tsRicezioneField_title();
		if (fromScrivania && idNode != null && (idNode.startsWith("FD.2NA") || idNode.startsWith("D.2NA") || idNode.startsWith("F.2NA"))) {
			tsRicezioneTitle = "Notifica del";
		}
		
		tsRicezione = new ListGridField("tsRicezione", tsRicezioneTitle);
		tsRicezione.setType(ListGridFieldType.DATE);
		tsRicezione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsRicezione.setWrap(false);

		prioritaInvioNotifiche = new ListGridField("prioritaInvioNotifiche", I18NUtil.getMessages().archivio_list_prioritaInvioNotificheField_title());
		prioritaInvioNotifiche.setType(ListGridFieldType.ICON);
		prioritaInvioNotifiche.setWidth(30);
		prioritaInvioNotifiche.setIconWidth(16);
		prioritaInvioNotifiche.setIconHeight(16);
		Map<String, String> prioritaInvioNotificheValueIcons = new HashMap<String, String>();
		prioritaInvioNotificheValueIcons.put("-1", "prioritaBassa.png");
		prioritaInvioNotificheValueIcons.put("0", "prioritaMedia.png");
		prioritaInvioNotificheValueIcons.put("1", "prioritaAlta.png");
		prioritaInvioNotificheValueIcons.put("2", "prioritaAltissima.png");		
		prioritaInvioNotificheValueIcons.put("", "blank.png");
		prioritaInvioNotifiche.setValueIcons(prioritaInvioNotificheValueIcons);
		prioritaInvioNotifiche.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String valuePriorita = record.getAttribute("prioritaInvioNotifiche");
				Integer priorita = valuePriorita != null && !"".equals(String.valueOf(valuePriorita)) ? new Integer(String.valueOf(valuePriorita)) : null;
				if (priorita != null) {
					String res = "";
					switch (priorita) {
					case 1: // priorita' bassa
						res = I18NUtil.getMessages().prioritaBassa_Alt_value();
						break;
					case 2: // priorita' media
						res = I18NUtil.getMessages().prioritaMedia_Alt_value();
						break;
					case 3: // priorita' alta
						res = I18NUtil.getMessages().prioritaAlta_Alt_value();
						break;
					case 4: // priorita' altissima
						res = I18NUtil.getMessages().prioritaAltissima_Alt_value();
						break;
					}
					return res;
				}
				return null;
			}
		});

		estremiInvioNotifiche = new ListGridField("estremiInvioNotifiche", I18NUtil.getMessages().archivio_list_estremiInvioNotificheField_title());
		estremiInvioNotifiche.setAttribute("custom", true);
		estremiInvioNotifiche.setCellAlign(Alignment.LEFT);
		estremiInvioNotifiche.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String estremiInvioNotifiche = (String) record.getAttribute("estremiInvioNotifiche");
				if (estremiInvioNotifiche != null && !"".equals(msgDiInvio)){
					estremiInvioNotifiche = estremiInvioNotifiche.replaceAll("\n", " ").trim();
				}
				if (estremiInvioNotifiche == null) return null;
				if (estremiInvioNotifiche.length()>Layout.getGenericConfig().getMaxValueLength()){
					return estremiInvioNotifiche.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
				} else return estremiInvioNotifiche;				
			}
		});
		estremiInvioNotifiche.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,int colNum) {
				return (String) record.getAttribute("estremiInvioNotifiche");
			}
		});	
		
		
		tsInvio = new ListGridField("tsInvio", I18NUtil.getMessages().archivio_list_tsInvioField_title());
		tsInvio.setType(ListGridFieldType.DATE);
		tsInvio.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsInvio.setWrap(false);

		destinatariInvio = new ListGridField("destinatariInvio", I18NUtil.getMessages().archivio_list_destinatariInvioField_title());

		tsEliminazione = new ListGridField("tsEliminazione", I18NUtil.getMessages().archivio_list_tsEliminazioneField_title());
		tsEliminazione.setType(ListGridFieldType.DATE);
		tsEliminazione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsEliminazione.setWrap(false);

		eliminatoDa = new ListGridField("eliminatoDa", I18NUtil.getMessages().archivio_list_eliminatoDaField_title());
		LinkedHashMap<String, String> eliminatoDaValueMap = new LinkedHashMap<String, String>();
		eliminatoDaValueMap.put("B", I18NUtil.getMessages().archivio_list_eliminatoDaField_B_value());
		eliminatoDaValueMap.put("N", I18NUtil.getMessages().archivio_list_eliminatoDaField_N_value());
		eliminatoDaValueMap.put("I", I18NUtil.getMessages().archivio_list_eliminatoDaField_I_value());
		eliminatoDaValueMap.put("C", I18NUtil.getMessages().archivio_list_eliminatoDaField_C_value());
		eliminatoDaValueMap.put("W", I18NUtil.getMessages().archivio_list_eliminatoDaField_W_value());
		eliminatoDaValueMap.put("P", I18NUtil.getMessages().archivio_list_eliminatoDaField_P_value());
		eliminatoDaValueMap.put("NA", I18NUtil.getMessages().archivio_list_eliminatoDaField_NA_value());
		eliminatoDaValueMap.put("NCC", I18NUtil.getMessages().archivio_list_eliminatoDaField_NCC_value());
		eliminatoDaValueMap.put("NNA", I18NUtil.getMessages().archivio_list_eliminatoDaField_NNA_value());
		eliminatoDaValueMap.put("DA", I18NUtil.getMessages().archivio_list_eliminatoDaField_DA_value());
		eliminatoDaValueMap.put("DFA", I18NUtil.getMessages().archivio_list_eliminatoDaField_DFA_value());
		eliminatoDaValueMap.put("DFI", I18NUtil.getMessages().archivio_list_eliminatoDaField_DFI_value());
		eliminatoDaValueMap.put("DPR", I18NUtil.getMessages().archivio_list_eliminatoDaField_DPR_value());
		eliminatoDaValueMap.put("ADA", I18NUtil.getMessages().archivio_list_eliminatoDaField_ADA_value());
		eliminatoDaValueMap.put("S", I18NUtil.getMessages().archivio_list_eliminatoDaField_S_value());
		eliminatoDaValueMap.put("IEML", I18NUtil.getMessages().archivio_list_eliminatoDaField_IEML_value());
		eliminatoDaValueMap.put("DIA", I18NUtil.getMessages().archivio_list_eliminatoDaField_DIA_value());
		eliminatoDaValueMap.put("DAV", I18NUtil.getMessages().archivio_list_eliminatoDaField_DAV_value());
		eliminatoDaValueMap.put("", "");
		eliminatoDa.setValueMap(eliminatoDaValueMap);

		utenteProtocollante = new ListGridField("utenteProtocollante", I18NUtil.getMessages().archivio_list_utenteProtocollanteField_title());
		
		uoProtocollante = new ListGridField("uoProtocollante", I18NUtil.getMessages().archivio_list_uoProtocollanteField_title());
		
		repertorio = new ListGridField("repertorio",I18NUtil.getMessages().archivio_list_repertorio_title());
		
		estremiUDTrasmessoInUscitaCon = new ListGridField("estremiUDTrasmessoInUscitaCon", "Uscito con");
		estremiUDTrasmessoInUscitaCon.setAttribute("custom", true);		
		estremiUDTrasmessoInUscitaCon.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {

				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				final String idUD = record != null && record.getAttributeAsString("idUDTrasmessoInUscitaCon") != null &&
						!"".equals(record.getAttributeAsString("idUDTrasmessoInUscitaCon")) ? record.getAttributeAsString("idUDTrasmessoInUscitaCon") : null;
				if(idUD != null && !"".equals(idUD)){
					String estremiUDTrasmessoInUscitaCon = record.getAttributeAsString("estremiUDTrasmessoInUscitaCon");
					Record recordToShow = new Record();
					recordToShow.setAttribute("idUd", idUD);
					new DettaglioRegProtAssociatoWindow(recordToShow, "Dettaglio "+estremiUDTrasmessoInUscitaCon);
				}
			}
		});
		estremiUDTrasmessoInUscitaCon.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				final String idUD = record != null && record.getAttributeAsString("idUDTrasmessoInUscitaCon") != null &&
						!"".equals(record.getAttributeAsString("idUDTrasmessoInUscitaCon")) ? record.getAttributeAsString("idUDTrasmessoInUscitaCon") : null;
				if(idUD != null){
					return buildLinkText(record.getAttributeAsString("estremiUDTrasmessoInUscitaCon"));
				}
				return null;
			}
		});
		

		flgPresaInCarico = new ListGridField("flgPresaInCarico", I18NUtil.getMessages().archivio_list_flgPresaInCaricoField_title());
		flgPresaInCarico.setType(ListGridFieldType.ICON);
		flgPresaInCarico.setIconWidth(16);
		flgPresaInCarico.setIconHeight(16);
		Map<String, String> flgPresaInCaricoValueIcons = new HashMap<String, String>();
		flgPresaInCaricoValueIcons.put("1", "archivio/flgPresaInCarico/da_fare.png");
		flgPresaInCaricoValueIcons.put("0", "archivio/flgPresaInCarico/fatta.png");
		flgPresaInCarico.setValueIcons(flgPresaInCaricoValueIcons);
		Map<String, String> flgPresaInCaricoValueHovers = new HashMap<String, String>();
		flgPresaInCaricoValueHovers.put("1", I18NUtil.getMessages().archivio_list_flgPresaInCaricoField_1_value());
		flgPresaInCaricoValueHovers.put("0", I18NUtil.getMessages().archivio_list_flgPresaInCaricoField_0_value());
		flgPresaInCarico.setAttribute("valueHovers", flgPresaInCaricoValueHovers);

		nroProvvisorioAtto = new ListGridField("nroProvvisorioAtto", "N.ro provvisorio");

//		dataAvvioIterAtto = new ListGridField("dataAvvioIterAtto", "Data avvio iter atto");
//		dataAvvioIterAtto.setType(ListGridFieldType.DATE);
//		dataAvvioIterAtto.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
//		dataAvvioIterAtto.setWrap(false);

		pubblicazione = new ListGridField("pubblicazione", "Pubblicazione");

		uoProponente = new ListGridField("uoProponente", "U.O. registrazione/proponente");
		
		programmazioneAcquisti = new ListGridField("programmazioneAcquisti", I18NUtil.getMessages().archivio_list_programmazioneAcquisti_title());
		
		codiceCIG = new ListGridField("codiceCIG", I18NUtil.getMessages().archivio_list_codiceCIG_title());
		
		cui = new ListGridField("cui", I18NUtil.getMessages().archivio_list_cui_title());
		
		inConoscenzaA = new ListGridField("inConoscenzaA", I18NUtil.getMessages().archivio_list_inConoscenzaA_title());
		inConoscenzaA.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String inConoscenzaA = (String) record.getAttribute("inConoscenzaA");
				if (inConoscenzaA == null) return null;
				if (inConoscenzaA.length()>Layout.getGenericConfig().getMaxValueLength()){
					return inConoscenzaA.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
				} else return inConoscenzaA;				
			}
		});

		score = new ListGridField("score", I18NUtil.getMessages().archivio_list_scoreField_title());
		score.setType(ListGridFieldType.INTEGER);
		score.setSortByDisplayField(false);
		score.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Integer score = value != null && !"".equals(String.valueOf(value)) ? new Integer(String.valueOf(value)) : null;
				if (score != null) {
					String res = "";
					for (int i = 0; i < score; i++) {
						res += "<img src=\"images/score.png\" size=\"10\"/>";
					}
					return res;
				}
				return null;
			}
		});

		attoAutAnnullamento = new ListGridField("attoAutAnnullamento", I18NUtil.getMessages().archivio_list_attoAutAnnullamento_title());
		
		dtEsecutivita = new ListGridField("dtEsecutivita", I18NUtil.getMessages().archivio_list_dtEsecutivitaField_title());
		dtEsecutivita.setType(ListGridFieldType.DATE);
		
		flgImmediatamenteEseg = new ListGridField("flgImmediatamenteEseg", I18NUtil.getMessages().archivio_list_flgImmediatamenteEsegField());
		flgImmediatamenteEseg.setType(ListGridFieldType.ICON);
		flgImmediatamenteEseg.setIconWidth(16);
		flgImmediatamenteEseg.setIconHeight(16);
		Map<String, String> flgImmediatamenteEsegValueIcons = new HashMap<String, String>();
		flgImmediatamenteEsegValueIcons.put("1", "attiInLavorazione/IE.png");
		flgImmediatamenteEsegValueIcons.put("0", "blank.png");
		flgImmediatamenteEseg.setValueIcons(flgImmediatamenteEsegValueIcons);
	
		perizie = new ListGridField("perizie", I18NUtil.getMessages().archivio_list_perizie_title());
		
		centroDiCosto = new ListGridField("centroDiCosto", I18NUtil.getMessages().archivio_list_centroDiCosto_title());		
		if(!AurigaLayout.isAttivoClienteCOTO()) {
			centroDiCosto.setHidden(true);
			centroDiCosto.setCanHide(false);
		}
		
		dataScadenza = new ListGridField("dataScadenza", I18NUtil.getMessages().archivio_list_dataScadenza_title());
		dataScadenza.setType(ListGridFieldType.DATE);
		dataScadenza.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataScadenza.setWrap(false);
		dataScadenza.setWidth(100);		
		if(!AurigaLayout.getParametroDBAsBoolean("ATTIVO_ITER_LIQUIDAZIONI")) {
			dataScadenza.setHidden(true);
			dataScadenza.setCanHide(false);
		}		
		
		flgSottopostoControlloRegAmm = new ListGridField("flgSottopostoControlloRegAmm", I18NUtil.getMessages().archivio_list_flgSottopostoControlloRegAmmField_title());
		flgSottopostoControlloRegAmm.setType(ListGridFieldType.ICON);
		flgSottopostoControlloRegAmm.setWidth(30);
		flgSottopostoControlloRegAmm.setIconWidth(16);
		flgSottopostoControlloRegAmm.setIconHeight(16);
		flgSottopostoControlloRegAmm.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flgSottopostoControlloRegAmm") != null && record.getAttribute("flgSottopostoControlloRegAmm").equalsIgnoreCase("1")) {
					return buildImgButtonHtml("buttons/giallo.png", I18NUtil.getMessages().archivio_list_flgSottopostoControlloRegAmmField_1_value());
				}
				return null;
			}
		});		
		flgSottopostoControlloRegAmm.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flgSottopostoControlloRegAmm") != null && record.getAttribute("flgSottopostoControlloRegAmm").equalsIgnoreCase("1")) {
					return I18NUtil.getMessages().archivio_list_flgSottopostoControlloRegAmmField_1_value();
				}
				return null;
			}
		});	
		flgSottopostoControlloRegAmm.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				Record record = event.getRecord();
				if (record.getAttribute("flgSottopostoControlloRegAmm") != null && record.getAttribute("flgSottopostoControlloRegAmm").equalsIgnoreCase("1")) {
					apriDettaglioProcesso(record.getAttributeAsString("idProcessoControlloRegAmm") , 
                      null,
                      record.getAttributeAsString("idUd"),
                      null,
                      false
                    );	
				}
			}
		});
		
		// Se il cliente NON e' CMTO nascondo la colonna
		if(!AurigaLayout.isAttivoClienteCMTO()) {
			flgSottopostoControlloRegAmm.setHidden(true);
			flgSottopostoControlloRegAmm.setCanHide(false);
		}
					
		nrLiquidazioneContestuale = new ListGridField("nrLiquidazioneContestuale", I18NUtil.getMessages().archivio_list_nrLiquidazioneContestualeField_title());
		
		// Se il cliente NON e' CMTO nascondo la colonna
		if(!AurigaLayout.isAttivoClienteCMTO()) {
			nrLiquidazioneContestuale.setHidden(true);
			nrLiquidazioneContestuale.setCanHide(false);
		}
				
		idProcessoControlloRegAmm = new ListGridField("idProcessoControlloRegAmm");
		idProcessoControlloRegAmm.setHidden(true);
		idProcessoControlloRegAmm.setCanHide(false);
		
		ListGridField detailButtonAccessibleField = new ListGridField("detail_button", "Dettaglio");  
		detailButtonAccessibleField.setAttribute("custom", true);	
		detailButtonAccessibleField.setShowHover(true);	
		detailButtonAccessibleField.setCanReorder(false);	
		detailButtonAccessibleField.setCanSort(false);	
		detailButtonAccessibleField.setWidth(50);
		detailButtonAccessibleField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {					
				return buildButtonActionJsByRowNumHtml("buttons/detail.png","Dettaglio", rowNum, "openDetailByRowNum", "detail");			
			}
		});
		detailButtonAccessibleField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				return getDetailButtonPrompt();
			}
		});	
		detailButtonAccessibleField.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				// TODO Auto-generated method stub
				return AurigaLayout.getIsAttivaAccessibilita();
			}
		});
		ListGridField selectRowAccessibleField = new ListGridField("selectRowAccessibleField", "Seleziona riga");  
		selectRowAccessibleField.setAttribute("custom", true);	
		selectRowAccessibleField.setShowHover(true);	
		selectRowAccessibleField.setCanReorder(false);	
		selectRowAccessibleField.setCanSort(false);	
		selectRowAccessibleField.setWidth(50);
		selectRowAccessibleField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {	
				if (isSelected(record)) {
					return buildButtonSelectJsByRowNumHtml("buttons/checkboxField.png","Deseleziona riga", rowNum, "selectRowByRowNum", "checked");
				} else {
					return buildButtonSelectJsByRowNumHtml("buttons/checkboxField.png","Seleziona riga", rowNum, "selectRowByRowNum", "");			
				}
			}
		});
		selectRowAccessibleField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				return isSelected(record) ? "Deselziona riga" : "Seleziona riga";
			}
		});	
		selectRowAccessibleField.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				// TODO Auto-generated method stub
				return AurigaLayout.getIsAttivaAccessibilita();
			}
		});
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			setArrowKeyAction("focus");
			setCanFocus(true);
			addBodyKeyPressHandler(new BodyKeyPressHandler() {
	            
	            @Override
	            public void onBodyKeyPress(BodyKeyPressEvent event) {
	                if (EventHandler.getKey().equalsIgnoreCase("Space")) {
						Integer focusRow2 = getFocusRow();
						ListGridRecord record = getRecord(focusRow2);
						manageDetailButtonClick(record);
	                }
	            }
	        });		
			setShowSortArrow(SortArrow.NONE);
			setShowAllRecords(AurigaLayout.getParametroDBAsBoolean("ATTIVA_PAGINAZIONE_ARCHIVIO"));
		}
		
		flgDeterminaContrarreTramiteGara = new ListGridField("flgDeterminaContrarreTramiteGara", I18NUtil.getMessages().archivio_list_flgDeterminaContrarreTramiteGara_title());
		flgDeterminaContrarreTramiteGara.setAttribute("custom", true);
		flgDeterminaContrarreTramiteGara.setAlign(Alignment.CENTER);
		
		flgDeterminaAggiudicaGara = new ListGridField("flgDeterminaAggiudicaGara", I18NUtil.getMessages().archivio_list_flgDeterminaAggiudicaGara_title());
		flgDeterminaAggiudicaGara.setAttribute("custom", true);
		flgDeterminaAggiudicaGara.setAlign(Alignment.CENTER);
		
		flgDeterminaRimodulazioneSpesaPostAggiudica = new ListGridField("flgDeterminaRimodulazioneSpesaPostAggiudica", I18NUtil.getMessages().archivio_list_flgDeterminaRimodulazioneSpesaPostAggiudica_title());
		flgDeterminaRimodulazioneSpesaPostAggiudica.setAttribute("custom", true);
		flgDeterminaRimodulazioneSpesaPostAggiudica.setAlign(Alignment.CENTER);
		
		flgLiquidazioneContestualeImpegni = new ListGridField("flgLiquidazioneContestualeImpegni", I18NUtil.getMessages().archivio_list_flgLiquidazioneContestualeImpegni_title());
		flgLiquidazioneContestualeImpegni.setAttribute("custom", true);
		flgLiquidazioneContestualeImpegni.setAlign(Alignment.CENTER);
		
		flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi = new ListGridField("flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi", I18NUtil.getMessages().archivio_list_flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi_title());
		flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi.setAttribute("custom", true);
		flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi.setAlign(Alignment.CENTER);
		
		tipoAffidamento = new ListGridField("tipoAffidamento", I18NUtil.getMessages().archivio_list_tipoAffidamento_title());
		tipoAffidamento.setAttribute("custom", true);
		tipoAffidamento.setAlign(Alignment.CENTER);
		
		materiaTipoAtto = new ListGridField("materiaTipoAtto", I18NUtil.getMessages().archivio_list_materiaTipoAtto_title());
		materiaTipoAtto.setAttribute("custom", true);
		materiaTipoAtto.setAlign(Alignment.CENTER);
		
		progettoLLPP = new ListGridField("progettoLLPP", I18NUtil.getMessages().archivio_list_progettoLLPP_title());
		progettoLLPP.setAttribute("custom", true);
		progettoLLPP.setAlign(Alignment.CENTER);
		
		beniServizi = new ListGridField("beniServizi", I18NUtil.getMessages().archivio_list_beniServizi_title());
		beniServizi.setAttribute("custom", true);
		beniServizi.setAlign(Alignment.CENTER);
		
		ordinativi = new ListGridField("ordinativi", I18NUtil.getMessages().archivio_list_ordinativi_title());
		ordinativi.setAttribute("custom", true);
		ordinativi.setAlign(Alignment.CENTER);
				
		flgDatiCompletiPerProtocollazione = new ListGridField("iconaFlgDatiCompletiPerProtocollazione", "Dati completi per protocollazione");
		flgDatiCompletiPerProtocollazione.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgDatiCompletiPerProtocollazione.setAlign(Alignment.CENTER);
		flgDatiCompletiPerProtocollazione.setWrap(false);
		flgDatiCompletiPerProtocollazione.setWidth(30);
		flgDatiCompletiPerProtocollazione.setAttribute("custom", true);
		flgDatiCompletiPerProtocollazione.setShowHover(true);
		flgDatiCompletiPerProtocollazione.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgDatiCompletiPerProtocollazione = (String) record.getAttribute("flgDatiCompletiPerProtocollazione");
				if (flgDatiCompletiPerProtocollazione != null && "1".equalsIgnoreCase(flgDatiCompletiPerProtocollazione)) {
					return buildIconHtml("protocollazione/datiCompletiProtocollazione.png");
				}
				return null;
			}
		});
		flgDatiCompletiPerProtocollazione.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgDatiCompletiPerProtocollazione = (String) record.getAttribute("flgDatiCompletiPerProtocollazione");
				if (flgDatiCompletiPerProtocollazione != null && "1".equalsIgnoreCase(flgDatiCompletiPerProtocollazione)) {
					return "Dati completi per protocollazione";
				}
				return null;
			}
		});
		
		msgDiInvio = new ListGridField("msgDiInvio", I18NUtil.getMessages().archivio_list_msgDiInvioField_title());
		msgDiInvio.setAttribute("custom", true);
		msgDiInvio.setCellAlign(Alignment.LEFT);
		msgDiInvio.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String msgDiInvio = (String) record.getAttribute("msgDiInvio");
				if (msgDiInvio != null && !"".equals(msgDiInvio)){
					msgDiInvio = msgDiInvio.replaceAll("\n", " ").trim();
				}
				if (msgDiInvio == null) return null;
				if (msgDiInvio.length()>Layout.getGenericConfig().getMaxValueLength()){
					return msgDiInvio.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
				} else return msgDiInvio;				
			}
		});
		msgDiInvio.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,int colNum) {
				return (String) record.getAttribute("msgDiInvio");
			}
		});	
		
		
		// Solo per il nodo "Immagini non associate ai protocolli"
		tsRicezioneDoc = new ListGridField("tsRicezioneDoc", "Data e ora ricezione");
		tsRicezioneDoc.setType(ListGridFieldType.DATE);
		tsRicezioneDoc.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsRicezioneDoc.setWrap(false);
		
		tsScansione = new ListGridField("tsScansione", "Data e ora scansione");
		tsScansione.setType(ListGridFieldType.DATE);
		tsScansione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsScansione.setWrap(false);

		datiBarcode = new ListGridField("datiBarcode", "Dati barcode");
		datiBarcode.setAttribute("custom", true);
		datiBarcode.setAlign(Alignment.LEFT);

		
		sedeScansione = new ListGridField("sedeScansione", "Sede scansione");
		sedeScansione.setAttribute("custom", true);
		sedeScansione.setAlign(Alignment.LEFT);

		timbroProtRiconosciuto = new ListGridField("timbroProtRiconosciuto", "Timbro prot. riconosciuto");
		timbroProtRiconosciuto.setAttribute("custom", true);
		timbroProtRiconosciuto.setAlign(Alignment.LEFT);

		nroProtRiconosciuto = new ListGridField("nroProtRiconosciuto", "N° prot. riconosciuto");
		nroProtRiconosciuto.setAttribute("custom", true);
		nroProtRiconosciuto.setAlign(Alignment.LEFT);

		motivoScarto = new ListGridField("motivoScarto", "Motivo scarto");
		motivoScarto.setAttribute("custom", true);
		motivoScarto.setAlign(Alignment.LEFT);
		motivoScarto.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String motivoScarto = (String) record.getAttribute("motivoScarto");
				if (motivoScarto != null && !"".equals(motivoScarto)){
					motivoScarto = motivoScarto.replaceAll("\n", " ").trim();
				}
				if (motivoScarto == null) return null;
				if (motivoScarto.length()>Layout.getGenericConfig().getMaxValueLength()){
					return motivoScarto.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
				} else return motivoScarto;				
			}
		});
		
		// Icona Stato trasferimento a Bloomfleet
		statoTrasferimentoBloomfleet = new ListGridField("statoTrasferimentoBloomfleet", I18NUtil.getMessages().archivio_list_statoTrasferimentoBloomfleetField_title());
		statoTrasferimentoBloomfleet.setType(ListGridFieldType.ICON);
		statoTrasferimentoBloomfleet.setWidth(30);
		statoTrasferimentoBloomfleet.setIconWidth(16);
		statoTrasferimentoBloomfleet.setIconHeight(16);
		Map<String, String> statoTrasferimentoBloomfleetValueIcons = new HashMap<String, String>();
		statoTrasferimentoBloomfleetValueIcons.put("da_trasferire", "protocollazione/B_bianca.png");
		statoTrasferimentoBloomfleetValueIcons.put("trasferito",    "protocollazione/B_verde.png");
		statoTrasferimentoBloomfleetValueIcons.put("in_errore",     "protocollazione/B_rossa.png");
		statoTrasferimentoBloomfleetValueIcons.put("",              "blank.png");
		statoTrasferimentoBloomfleet.setValueIcons(statoTrasferimentoBloomfleetValueIcons);
		Map<String, String> statoTrasferimentoBloomfleetValueHovers = new HashMap<String, String>();
		statoTrasferimentoBloomfleetValueHovers.put("da_trasferire", I18NUtil.getMessages().archivio_list_statoTrasferimentoBloomfleet_da_trasferire_Alt_title());
		statoTrasferimentoBloomfleetValueHovers.put("trasferito", I18NUtil.getMessages().archivio_list_statoTrasferimentoBloomfleet_trasferito_Alt_title());
		statoTrasferimentoBloomfleetValueHovers.put("in_errore", I18NUtil.getMessages().archivio_list_statoTrasferimentoBloomfleet_in_errore_Alt_title());
		statoTrasferimentoBloomfleetValueHovers.put("", "");
		statoTrasferimentoBloomfleet.setAttribute("valueHovers", statoTrasferimentoBloomfleetValueHovers);
		List<ListGridField> fieldsToSet = new ArrayList<ListGridField>();
		
		
		flgPresenzaContratti = new ListGridField("flgPresenzaContratti", I18NUtil.getMessages().archivio_list_flgPresenzaContrattiField());
		flgPresenzaContratti.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgPresenzaContratti.setType(ListGridFieldType.ICON);
		flgPresenzaContratti.setIconWidth(16);
		flgPresenzaContratti.setIconHeight(16);
		Map<String, String> flgPresenzaContrattiValueIcons = new HashMap<String, String>();
		flgPresenzaContrattiValueIcons.put("1", "lettere/lettera_C_verde.png");
		flgPresenzaContrattiValueIcons.put("0", "blank.png");
		flgPresenzaContratti.setValueIcons(flgPresenzaContrattiValueIcons);
		
		Map<String, String> flgPresenzaContrattiValueHovers = new HashMap<String, String>();
		flgPresenzaContrattiValueHovers.put("1", I18NUtil.getMessages().archivio_list_flgPresenzaContratti_1_Alt_title());
		flgPresenzaContrattiValueHovers.put("", "");
		flgPresenzaContratti.setAttribute("valueHovers", flgPresenzaContrattiValueHovers);
		
		
		fieldsToSet.addAll(Arrays.asList(selectRowAccessibleField)); 		
		if (fromScrivania) {
			folderUp.setHidden(true);
			if (AurigaLayout.isAttivoModuloProt()) {
				
				// ottavio - AURIGA-520 : Se sono in D.BOZZE.DAPROT vanno rinominate le colonne:
				// Inviato il -> Data di invio al protocollo 
				// Assegnatario/i -> Protocollo assegnatario 
				if (idNode!= null && (idNode.equals("D.BOZZE.DAPROT"))) {
					tsInvio.setTitle("Data di invio al protocollo");
					assegnatari.setTitle("Protocollo assegnatario");
				}
				
				fieldsToSet.addAll(Arrays.asList(folderUp, 
						                        icona, 
						                        flgTipoProv, 
						                        nome, 
						                        oggetto, 
						                        descContenutiFascicolo, 
						                        segnaturaXOrd, 
						                        nroSecondario, 
						                        numero, 
						                        anno, 
						                        priorita, 
						                        idTipo, 
						                        tipo, 
						                        tsApertura, 
						                        tsChiusura,
						                        tsRegistrazione, 
						                        tsDocumento, 
						                        fascicoliApp, 
						                        livelloRiservatezza, 
						                        assegnatari, 
						                        mittenti, 
						                        destinatari,
						                        tsInvio, 
						                        destinatariInvio, 
						                        tsEliminazione, 
						                        eliminatoDa, 
						                        flgPresaInCarico, 
						                        score, 
						                        idUdFolder, 
						                        flgUdFolder, 
						                        idFolderApp, 
						                        percorsoFolderApp, 
						                        flgSelXFinalita,
						                        segnatura,
						                        attoAutAnnullamento, 
						                        tsPresaInCarico, 
						                        societa,
						                        stato,
												flgRicevutaViaEmail,  
												flgAnnReg,  
						                        utenteProtocollante,
						                        motivoAnnullamento, 
						                        uoProtocollante, 
												repertorio,
												estremiUDTrasmessoInUscitaCon,
						                        nroProvvisorioAtto,
												pubblicazione,
												uoProponente, 
												tsRicezione,
												prioritaInvioNotifiche,
												estremiInvioNotifiche, 
												msgDiInvio,
												flgNotificatoAMe, 
						                        flgAssegnatoAMe,
						                        flgDatiCompletiPerProtocollazione,
						                        inConoscenzaA
                        						)
									);
				
				if (AurigaLayout.isAttivoClienteADSP()) {
					fieldsToSet.addAll(Arrays.asList(perizie));
				}
				fieldsToSet.addAll(Arrays.asList(centroDiCosto, dataScadenza, flgSottopostoControlloRegAmm, nrLiquidazioneContestuale, idProcessoControlloRegAmm));
				
				// Icona Stato trasferimento a Bloomfleet (solo per A2A)
				if (AurigaLayout.isAttivoClienteA2A()) {
					fieldsToSet.addAll(Arrays.asList(statoTrasferimentoBloomfleet));	
				}
								
				// ottavio - AURIGA-517 : la colonna "Presente documentazione contratti" deve apparire in tutte e solo le sezioni con ci_nodo = D.2A.DP.R
				if (idNode!= null && idNode.equals("D.2A.DP.R")) {
					fieldsToSet.addAll(Arrays.asList(flgPresenzaContratti));
				}
				
				// ottavio - AURIGA-520 : Se NON sono in D.BOZZE.DAPROT e D.BOZZE e D.2A.DAPROT aggiungo queste colonne : flgInviataViaEmail,statoTrasmissioneMail 
				if (idNode!= null && !idNode.equals("D.BOZZE.DAPROT") && !idNode.equals("D.BOZZE") && !idNode.equals("D.2A.DAPROT")) {
					fieldsToSet.addAll(Arrays.asList(flgInviataViaEmail,statoTrasmissioneMail));
				}
			} else {
				fieldsToSet.addAll(Arrays.asList(folderUp, 
												 icona, 
												 flgTipoProv, 
												 nome, 
												 oggetto, 
												 descContenutiFascicolo, 
												 stato, 
												 segnaturaXOrd, 
												 numero, 
												 anno, 
												 priorita, 
												 idTipo, 
												 tipo, 
												 tsApertura, 
												 tsChiusura,
												 tsRegistrazione, 
												 tsPresaInCarico,
												 flgNotificatoAMe, 
												 tsRicezione, 
												 prioritaInvioNotifiche, 
												 estremiInvioNotifiche, 
												 tsInvio, 
												 destinatariInvio, 
												 tsEliminazione, 
												 eliminatoDa,
												 flgPresaInCarico, 
												 repertorio, 
												 nroProvvisorioAtto, 
												 pubblicazione, 
												 uoProponente, 
												 score, 
												 idUdFolder, 
												 flgUdFolder, 
												 idFolderApp, 
												 percorsoFolderApp, 
												 flgSelXFinalita, 
												 segnatura, 
												 attoAutAnnullamento, 
												 centroDiCosto, 
												 dataScadenza, 
												 flgSottopostoControlloRegAmm, 
												 nrLiquidazioneContestuale, 
												 idProcessoControlloRegAmm, 
												 msgDiInvio, 
												 societa)
											   );
			}
		} else {
			folderUp.setHidden(false);
			if (AurigaLayout.isAttivoClienteCMMI()) {
				if (AurigaLayout.isAttivoModuloProt()) {
					fieldsToSet.addAll(Arrays.asList(folderUp, 
													 icona, 
													 flgTipoProv, 
													 nome, 
													 oggetto, 
													 motivoAnnullamento, 
													 descContenutiFascicolo, 
													 stato, 
													 segnaturaXOrd, 
													 nroSecondario,
													 numero, 
													 anno, 
													 priorita, 
													 idTipo, 
													 tipo, 
													 tsApertura, 
													 tsChiusura,
													 tsRegistrazione, 
													 tsPresaInCarico,
													 tsDocumento, 
													 fascicoliApp, 
													 livelloRiservatezza, 													  
													 assegnatari, 
													 mittenti, 
													 destinatari,
													 flgRicevutaViaEmail, 
													 flgInviataViaEmail, 
													 flgAnnReg, 
													 flgAssegnatoAMe, 
													 flgNotificatoAMe, 
													 tsRicezione, 
													 prioritaInvioNotifiche,
													 estremiInvioNotifiche, 
													 tsInvio, 
													 destinatariInvio, 
													 tsEliminazione, 
													 eliminatoDa, 
													 utenteProtocollante, 
													 uoProtocollante, 
													 estremiUDTrasmessoInUscitaCon,
													 flgPresaInCarico, 
													 repertorio, 
													 nroProvvisorioAtto, 
													 pubblicazione, 
													 uoProponente, 
													 score, 
													 idUdFolder, 
													 flgUdFolder, 
													 idFolderApp, 
													 percorsoFolderApp, 
													 flgSelXFinalita, 
													 segnatura, 
													 statoTrasmissioneMail, 
													 dtEsecutivita, 
													 flgImmediatamenteEseg, 
													 flgSottopostoControlloRegAmm, 
													 nrLiquidazioneContestuale, 
													 idProcessoControlloRegAmm, 
													 societa,
													 inConoscenzaA)
							);
					
				} else {
					fieldsToSet.addAll(Arrays.asList(folderUp, 
													 icona, 
													 flgTipoProv, 
													 nome, 
													 oggetto, 
													 descContenutiFascicolo, 
													 stato, 
													 segnaturaXOrd, 
													 numero, 
													 anno, 
													 priorita, 
													 idTipo, 
													 tipo, 
													 tsApertura, 
													 tsChiusura,
													 tsRegistrazione,  
													 tsPresaInCarico,
													 flgNotificatoAMe, 
													 tsRicezione, 
													 prioritaInvioNotifiche, 
													 estremiInvioNotifiche, 
													 tsInvio, 
													 destinatariInvio, 
													 tsEliminazione, 
													 eliminatoDa,
													 flgPresaInCarico, 
													 repertorio, 
													 nroProvvisorioAtto, 
													 pubblicazione, 
													 uoProponente, 
													 score, 
													 idUdFolder, 
													 flgUdFolder, 
													 idFolderApp, 
													 percorsoFolderApp, 
													 flgSelXFinalita, 
													 segnatura, 
													 dtEsecutivita, 
													 flgImmediatamenteEseg, 
													 flgSottopostoControlloRegAmm, 
													 nrLiquidazioneContestuale, 
													 idProcessoControlloRegAmm, 
													 societa)
							);
				}
			} else {
				if (AurigaLayout.isAttivoModuloProt()) {
					fieldsToSet.addAll(Arrays.asList(folderUp, 
													 icona, 
													 flgTipoProv, 
													 nome, 
													 oggetto, 
													 motivoAnnullamento, 
													 descContenutiFascicolo, 
													 stato, 
													 segnaturaXOrd, 
													 nroSecondario, 
													 numero, 
													 anno, 
													 priorita, 
													 idTipo, 
													 tipo, 
													 tsApertura, 
													 tsChiusura,
													 tsRegistrazione, 
													 tsDocumento, 
													 fascicoliApp, 
													 livelloRiservatezza, 
													 assegnatari, 
													 mittenti, 
													 destinatari,
													 flgRicevutaViaEmail, 
													 flgInviataViaEmail, 
													 flgAnnReg, 
													 flgAssegnatoAMe, 
													 flgNotificatoAMe, 
													 tsRicezione, 
													 prioritaInvioNotifiche,
													 estremiInvioNotifiche, 
													 tsInvio, 
													 destinatariInvio, 
													 tsEliminazione, 
													 eliminatoDa, 
													 utenteProtocollante, 
													 uoProtocollante, 
													 estremiUDTrasmessoInUscitaCon,
													 flgPresaInCarico, 
													 repertorio, 
													 nroProvvisorioAtto, 
													 pubblicazione, 
													 uoProponente, 
													 score, 
													 idUdFolder, 
													 flgUdFolder, 
													 idFolderApp, 
													 percorsoFolderApp, 
													 flgSelXFinalita, 
													 segnatura, 
													 statoTrasmissioneMail, 
													 dtEsecutivita, 
													 flgImmediatamenteEseg, 
													 societa,
													 inConoscenzaA)
							);
					if (AurigaLayout.isAttivoClienteADSP()) {
						fieldsToSet.addAll(Arrays.asList(perizie));
					}
					fieldsToSet.addAll(Arrays.asList(centroDiCosto, dataScadenza, annotazioniApposte, flgSottopostoControlloRegAmm, nrLiquidazioneContestuale, idProcessoControlloRegAmm));
					
					// Icona Stato trasferimento a Bloomfleet (solo per A2A)
					if (AurigaLayout.isAttivoClienteA2A()) {
						fieldsToSet.addAll(Arrays.asList(statoTrasferimentoBloomfleet));	
					}
					
				} else {
					fieldsToSet.addAll(Arrays.asList(folderUp, 
													 icona, 
													 flgTipoProv, 
													 nome, 
													 oggetto, 
													 descContenutiFascicolo, 
													 stato, 
													 segnaturaXOrd, 
													 numero, 
													 anno, 
													 priorita, 
													 idTipo, 
													 tipo, 
													 tsApertura, 
													 tsChiusura,
													 tsRegistrazione, 
													 flgNotificatoAMe, 
													 tsRicezione, 
													 prioritaInvioNotifiche, 
													 estremiInvioNotifiche, 
													 tsInvio, 
													 destinatariInvio, 
													 tsEliminazione, 
													 eliminatoDa,
													 flgPresaInCarico, 
													 repertorio, 
													 nroProvvisorioAtto, 
													 pubblicazione, 
													 uoProponente, 
													 score, 
													 idUdFolder, 
													 flgUdFolder, 
													 idFolderApp, 
													 percorsoFolderApp, 
													 flgSelXFinalita, 
													 segnatura, 
													 dtEsecutivita, 
													 flgImmediatamenteEseg, 
													 centroDiCosto, 
													 dataScadenza, 
													 annotazioniApposte, 
													 flgSottopostoControlloRegAmm, 
													 nrLiquidazioneContestuale, 
													 idProcessoControlloRegAmm, 
													 societa,
													 inConoscenzaA)
							);
				}
			}
		}
		
		if (AurigaLayout.isAttivoClienteCMTO()) {
			fieldsToSet.addAll(Arrays.asList(flgDeterminaContrarreTramiteGara, flgDeterminaAggiudicaGara, flgDeterminaRimodulazioneSpesaPostAggiudica, flgLiquidazioneContestualeImpegni, flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi, tipoAffidamento, materiaTipoAtto, progettoLLPP, beniServizi, ordinativi)); 
		}

		if (AurigaLayout.isAttivoClienteCOTO()) {
			fieldsToSet.addAll(Arrays.asList(programmazioneAcquisti, codiceCIG, cui));
		}
		
		// ottavio - AURIGA-357 : la colonna "Dati completi per protocollazione"  deve apparire nella sezione con ci_nodo che inizia con D.2A.DAFIRM
		if (fromScrivania && idNode!= null &&  idNode.startsWith("D.2A.DAFIRM")) {
			fieldsToSet.addAll(Arrays.asList(flgDatiCompletiPerProtocollazione));			
		}
		
		
		// Solo per il nodo "Immagini non associate ai protocolli"
		if (fromScrivania && idNode!= null && idNode.equals("D.SCANNOASS")) {
			fieldsToSet.clear();
			fieldsToSet.addAll(Arrays.asList(flgUdFolder,
					                         idUdFolder,
					                         anno,
					                         numero,
					                         tsRicezioneDoc,
					                         tsScansione, 
					                         datiBarcode,
					                         sedeScansione,
					                         timbroProtRiconosciuto,
					                         nroProtRiconosciuto,
					                         motivoScarto
					                         ));			
		}
		
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			fieldsToSet.addAll(Arrays.asList(detailButtonAccessibleField));
		}
		setFields(fieldsToSet.toArray(new ListGridField[fieldsToSet.size()]));
	}

	private void initReloadListCallback() {
		mReloadListCallback = new ReloadListCallback();
	}

	@Override
	protected int getButtonsFieldWidth() {
		return 50;
	}

	@Override
	protected void manageAltreOpButtonClick(ListGridRecord record) {
		showRowContextMenu(record, null);
	}

	@Override
	public void reloadFieldsFromCriteria(AdvancedCriteria criteria) {
		boolean isFulltextSearch = false;
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("searchFulltext".equals(crit.getFieldName())) {
					Map value = JSOHelper.getAttributeAsMap(crit.getJsObj(), "value");
					String parole = (String) value.get("parole");
					if (parole != null && !"".equals(parole)) {
						isFulltextSearch = true;
					}
				}
			}
		}
		if (isFulltextSearch) {
			score.setHidden(false);
		} else {
			score.setHidden(true);
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				try {
					refreshFields();
				} catch (Exception e) {
				}
				markForRedraw();
			}
		});
	}

	@Override
	public void setFields(ListGridField... fields) {
		for (final ListGridField field : fields) {
			String fieldName = field.getName();
			if (fromScrivania && tipoNodo.equals("F")) {
				
				if ("flgTipoProv".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("mittenti".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("destinatari".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("oggetto".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("tsRegistrazione".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("flgAnnReg".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("fascicoliApp".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("utenteProtocollante".equals(fieldName)) {
					field.setHidden(true);
				}				
				if ("motivoAnnullamento".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("uoProtocollante".equals(fieldName)) {
					field.setHidden(true);
				}
				if("repertorio".equals(fieldName)){
					field.setHidden(true);
				}
				if("estremiUDTrasmessoInUscitaCon".equals(fieldName)){
					field.setHidden(true);
				}
			}
			if (fromScrivania && tipoNodo.equals("D")) {
				if ("flgAnnReg".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("priorita".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("iconaFlgRicevutaViaEmail".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("uoProtocollante".equals(fieldName)) {
					field.setHidden(true);
				}
				if("repertorio".equals(fieldName)){
					field.setHidden(true);
				}
				if ("utenteProtocollante".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("motivoAnnullamento".equals(fieldName)) {
					field.setHidden(true);
				}
				if("estremiUDTrasmessoInUscitaCon".equals(fieldName)){
					field.setHidden(true);
				}
				if ("nroProvvisorioAtto".equals(fieldName)) {
					field.setHidden(true);
				}
//				if ("dataAvvioIterAtto".equals(fieldName)) {
//					field.setHidden(true);
//				}
				if ("pubblicazione".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("uoProponente".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("tsApertura".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("tsChiusura".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("descContenutiFascicolo".equals(fieldName)) {
					field.setHidden(true);
				}
				if ("nome".equals(fieldName)) {
					field.setHidden(true);
				}
//				if ("responsabileFascicolo".equals(fieldName)) {
//					field.setHidden(true);
//				}
				if("nroSecondario".equals(fieldName)){
					field.setHidden(true);
				}
			}
			
			if ((idNode != null) && (idNode.startsWith("D.2NA.DL") || idNode.startsWith("D.2NA"))){
				if ("estremiInvioNotifiche".equals(fieldName)){
					field.setTitle(I18NUtil.getMessages().archivio_list_estremiInvioNotificheField_notifiche_title());
				}
			}

			if (!fromScrivania || idNode == null || (!idNode.startsWith("FD.2A") && !idNode.startsWith("F.2A") && !idNode.startsWith("D.2A"))
					|| idNode.startsWith("D.2A.DP") || idNode.startsWith("F.2A.DP")) {
				if ("flgAssegnatoAMe".equals(fieldName)) {
					field.setHidden(true);
				}
			}

			// ottavio - AURIGA-357 : la colonna "Inviato a me"    deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2A  o F.2A  o DF.2A o D.2CC o F.2CC o DF.2CC			
			//                        la colonna "Notificato a me" deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2NA o F.2NA o DF.2NA 			
			if ("flgNotificatoAMe".equals(fieldName)) {
				if (!fromScrivania  || idNode == null ) {
					field.setHidden(true);					
				}
				else{
					if (idNode != null) {
						if (idNode.startsWith("D.2A")   ||  
							idNode.startsWith("F.2A")   || 
							idNode.startsWith("DF.2A")  || 
							idNode.startsWith("D.2CC")  || 
							idNode.startsWith("F.2CC")  || 
							idNode.startsWith("DF.2CC") ||
							idNode.startsWith("D.2NA")  ||
							idNode.startsWith("F.2NA")  ||
							idNode.startsWith("DF.2NA")
						   ){
							  field.setHidden(false);	   // mostro la colonna
						}else{ 
							  field.setHidden(true);       // nascondo la colonna
						} 
					}
				}
			}
			
			// ottavio - AURIGA-357 : la colonna "Ricevuto il"  deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2A  o F.2A  o DF.2A o D.2CC o F.2CC o DF.2CC
			//                        la colonna "Notifica del" deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2NA o F.2NA o DF.2NA 
			if ("tsRicezione".equals(fieldName)) {
				if (!fromScrivania  || idNode == null ) {
					field.setHidden(true);					
				}
				else{
					if (idNode != null) {
						if (idNode.startsWith("D.2A")   ||  
							idNode.startsWith("F.2A")   || 
							idNode.startsWith("DF.2A")  || 
							idNode.startsWith("D.2CC")  || 
							idNode.startsWith("F.2CC")  || 
							idNode.startsWith("DF.2CC") ||
							idNode.startsWith("D.2NA")  ||
							idNode.startsWith("F.2NA")  ||
							idNode.startsWith("DF.2NA")  
						   ){
							    field.setHidden(false);	   // mostro la colonna	
								
						}else{ 
							  field.setHidden(true);       // nascondo la colonna
						}  
					}
				}	
			}
			
			// ottavio - AURIGA-357 : la colonna "Dati invio"    deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2A  o F.2A  o DF.2A o D.2CC o F.2CC o DF.2CC
			//                        la colonna "Dati notifica" deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2NA o F.2NA o DF.2NA 
			if ("estremiInvioNotifiche".equals(fieldName)) {
				if (!fromScrivania  || idNode == null ) {
					field.setHidden(true);					
				} 
				else{
					if (idNode != null) {
						if (idNode.startsWith("D.2A")   ||  
							idNode.startsWith("F.2A")   ||  
							idNode.startsWith("DF.2A")  || 
							idNode.startsWith("D.2CC")  || 
							idNode.startsWith("F.2CC")  || 
							idNode.startsWith("DF.2CC") ||
							idNode.startsWith("D.2NA")  ||
							idNode.startsWith("F.2NA")  ||
							idNode.startsWith("DF.2NA")
						   ){
							  field.setHidden(false);	   // mostro la colonna
						}else{ 
							  field.setHidden(true);       // nascondo la colonna
						}  
					}
				}
			}
			
			// ottavio - AURIGA-357 : la colonna "Priorità invio" deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2A o F.2A o DF.2A o D.2CC o F.2CC o DF.2CC o D.2NA o F.2NA o DF.2NA
			if ("prioritaInvioNotifiche".equals(fieldName)) {
				if (!fromScrivania  || idNode == null ) {
					field.setHidden(true);					
				} 
				else{
					if (idNode != null) {
						if (idNode.startsWith("D.2A")   ||               
					        idNode.startsWith("F.2A")   || 
					        idNode.startsWith("DF.2A")  || 
					        idNode.startsWith("D.2CC")  || 
					        idNode.startsWith("F.2CC")  || 
					        idNode.startsWith("DF.2CC") ||
					        idNode.startsWith("D.2NA")  ||
					        idNode.startsWith("F.2NA")  ||
					        idNode.startsWith("DF.2NA") 
					       ){
							  field.setHidden(false);	   // mostro la colonna
						}else{ 
							  field.setHidden(true);       // nascondo la colonna
						}
					}
				}
					
			}
			
			if (fromScrivania && idNode != null && idNode.equals("D.23")) {
				if (!"tsRegistrazione".equals(fieldName) && !"segnaturaXOrd".equals(fieldName) && !"oggetto".equals(fieldName) && !"tipo".equals(fieldName)) {
					field.setHidden(true);
				}
			}

			// Se sto cercando nelle NEWS
			if (!fromScrivania || idFolder == null || !idFolder.equals("-9")) {
				if ("tsRicezione".equals(fieldName) || "prioritaInvioNotifiche".equals(fieldName) || "estremiInvioNotifiche".equals(fieldName) || "msgDiInvio".equals(fieldName)  ) {
					field.setHidden(true);
				}
			}
			
			// Se sto cercando tra gli ELIMINATI
			if (!fromScrivania || idFolder == null || !idFolder.equals("-99999")) {
				if ("tsEliminazione".equals(fieldName) || "eliminatoDa".equals(fieldName)) {
					field.setHidden(true);
				}
			}
			
			// Se sto cercando tra gli INVIATI
			if ("destinatariInvio".equals(fieldName)){
				if (!fromScrivania || idFolder == null || !idFolder.equals("-9999") && !idFolder.equals("-99991")) {
						field.setHidden(true);
					
				}
			}
			
			// Se sto cercando tra gli INVIATI
			if ("tsInvio".equals(fieldName)){
				if (!fromScrivania || idFolder == null || !idFolder.equals("-9999") && !idFolder.equals("-99991")) {
						field.setHidden(true);
				}
				
				if (idNode != null) {
					if (idNode.equalsIgnoreCase("D.BOZZE.DAPROT")){
						  field.setHidden(false);	   // mostro la colonna
					}else{ 
						  field.setHidden(true);       // nascondo la colonna
					}
				}
			}			
			
			// Se sto cercando nella sezione Stampe ed esportazioni su file;
			if (fromScrivania && idNode != null && idNode.equals("D.23")) {
				if ("altreOpButton".equals(fieldName)) {
					field.setHidden(true);
				}
			}

			if (!AurigaLayout.getParametroDBAsBoolean("ATTIVATO_MODULO_ATTI")) {
				if ("nroProvvisorioAtto".equals(fieldName) /*|| "dataAvvioIterAtto".equals(fieldName)*/ || "pubblicazione".equals(fieldName)
						|| "uoProponente".equals(fieldName)) {
					field.setHidden(true);
				}
			}
			
			if (!fromScrivania || idNode == null || (!idNode.equals("D.13") && !idNode.equals("D.14"))) {
				if ("attoAutAnnullamento".equals(fieldName)) {
					field.setHidden(true);
				}
			}
			
			// ottavio - AURIGA-357 : la colonna "Data presa in carico" deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2A o F.2A o DF.2A AND che NON contiene la stringa ".DP"			
			if ("tsPresaInCarico".equals(fieldName)) {
				if (!fromScrivania  || idNode == null ) {
					field.setHidden(true);					
				} 
				else {
					  if (idNode != null) {
						  if ( (idNode.startsWith("D.2A")  || 
							    idNode.startsWith("F.2A")  || 
							    idNode.startsWith("DF.2A")
							   ) 
								&& !idNode.contains(".DP")    
							 ){
								field.setHidden(false);       // mostro la colonna
							 }
						else {
							  field.setHidden(true);          // nascondo la colonna
						}
					 }
			    }
			}
			
			
			// ottavio - AURIGA-357 : la colonna "Dati completi per protocollazione"  deve apparire nella sezione con ci_nodo che inizia con D.2A.DAFIRM
			if ("flgDatiCompletiPerProtocollazione".equals(fieldName)) {
				if (!fromScrivania  || idNode == null ) {
					field.setHidden(true);					
				} 
				else{
					if (idNode != null) {
						if (idNode.equalsIgnoreCase("D.2A.DAFIRM")){
							  field.setHidden(false);	   // mostro la colonna
						}else{ 
							  field.setHidden(true);       // nascondo la colonna
						}
					}
				}
			}
			
			
			
			// ottavio - AURIGA-357 : l'icona "Presa in carico" deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2A o F.2A o DF.2A AND che NON contiene la stringa ".DP"
			if ("flgPresaInCarico".equals(fieldName)) {
				if (!fromScrivania  || idNode == null ) {
					field.setHidden(true);					
				} 
				else {
					  if (idNode != null) {
						  if ( (idNode.startsWith("D.2A")  || 
							    idNode.startsWith("F.2A")  || 
							    idNode.startsWith("DF.2A")
							   ) 
								&& !idNode.contains(".DP")    
							 ){
								field.setHidden(false);       // mostro la colonna
							 }
						else {
							  field.setHidden(true);          // nascondo la colonna
						}
					 }
			    }
			}

			
			// Se il cliente NON e' COTO nascondo la colonna centroDiCosto
			if (!AurigaLayout.isAttivoClienteCOTO()){
				if ("centroDiCosto".equals(fieldName)) {
					field.setHidden(true);
				}
			}
			
			// Se il cliente NON e' CMTO nascondo la colonna flgSottopostoControlloRegAmm
			if(!AurigaLayout.isAttivoClienteCMTO()) {
				if ("flgSottopostoControlloRegAmm".equals(fieldName)) {
					field.setHidden(true);
				}
			}
			

			// Se il cliente NON e' CMTO nascondo la colonna nrLiquidazioneContestuale
			if(!AurigaLayout.isAttivoClienteCMTO()) {
				if ("nrLiquidazioneContestuale".equals(fieldName)) {
					field.setHidden(true);
				}
			}
			
			// Se il parametro ATTIVO_ITER_LIQUIDAZIONI non è true nascondo la colonna dataScadenza
			if(!AurigaLayout.getParametroDBAsBoolean("ATTIVO_ITER_LIQUIDAZIONI")) {				
				if ("dataScadenza".equals(fieldName)) {
					field.setHidden(true);
				}
			}
			
			
			// ottavio - AURIGA-389 : la colonna "Messaggio di invio" deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2A o F.2A o DF.2A o D.2CC o F.2CC o DF.2CC o D.2NA o F.2NA o DF.2NA
			if ("msgDiInvio".equals(fieldName)) {
				if (!fromScrivania  || idNode == null ) {
					field.setHidden(true);					
				} 
				else{
					if (idNode != null) {
						if (idNode.startsWith("D.2A")   ||               
					        idNode.startsWith("F.2A")   || 
					        idNode.startsWith("DF.2A")  || 
					        idNode.startsWith("D.2CC")  || 
					        idNode.startsWith("F.2CC")  || 
					        idNode.startsWith("DF.2CC") ||
					        idNode.startsWith("D.2NA")  ||
					        idNode.startsWith("F.2NA")  ||
					        idNode.startsWith("DF.2NA") 
					       ){
							  field.setHidden(false);	   // mostro la colonna
						}else{ 
							  field.setHidden(true);       // nascondo la colonna
						}
					}
				}
			}
			
			
			// ottavio - AURIGA-517 : la colonna "Presente documentazione contratti" deve apparire in tutte e solo le sezioni con ci_nodo = D.2A.DP.R
			if ("flgPresenzaContratti".equals(fieldName)) {
				if (!fromScrivania  || idNode == null ) {
					field.setHidden(true);					
				} 
				else{
					if (idNode != null) {
						if (idNode.equalsIgnoreCase("D.2A.DP.R")){
							  field.setHidden(false);	   // mostro la colonna
						}else{ 
							  field.setHidden(true);       // nascondo la colonna
						}
					}
				}
			}
						
						
			// Se e' stato selezionato il nodo "Immagini non associate ai protocolli", mostro solo le colonne :  "Anno",  "N° immagine", "Data e ora ricezione" , "Data e ora scansione", "Dati barcode", "Sede scansione", "Timbro prot. riconosciuto", "N° prot. riconosciuto" , "Motivo scarto"
			if (fromScrivania && idNode != null && idNode.equals("D.SCANNOASS")) {
				if (!"anno".equals(fieldName)                   &&
					!"numero".equals(fieldName)                 &&
					!"tsRicezioneDoc".equals(fieldName)         &&
					!"tsScansione".equals(fieldName)            && 
					!"datiBarcode".equals(fieldName)            &&
					!"sedeScansione".equals(fieldName)          &&
					!"timbroProtRiconosciuto".equals(fieldName) &&
					!"nroProtRiconosciuto".equals(fieldName)    &&
					!"motivoScarto".equals(fieldName)
				   ) 
				{
					field.setHidden(true);
				}
			}
			
			
			// ottavio - AURIGA-520 : le seguenti colonne devono apparire in tutte e solo le sezioni con ci_nodo = D.BOZZE.DAPROT o D.BOZZE o D.2A.DAPROT :
			// "Verso"                                 (flgTipoProv)
			// "Segnatura"                             (segnatura)
			// "Grado di riservatezza"                 (livelloRiservatezza)
			// "N.ro"                                  (numero)
			// "Anno"                                  (anno)
			// "Data"                                  (tsRegistrazione)
			// "Oggetto"                               (oggetto)
			// "Priorità"                              (priorita)
			// "Tipologia documentale"                 (tipo)
			// "Data documento"                        (tsDocumento)
			// "Fascicolo/i appartenenza"              (fascicoliApp)
			// "Mittente/i"                            (mittenti)
			// "Destinatario/i"                        (destinatari)
			// "Punteggio ricerca"                     (score)
			// "Dati completi per la protocollazione"  (flgDatiCompletiPerProtocollazione)
			// "Data di invio al protocollo"           (tsInvio) 
			// "Altre operazioni"                      (altreOpButtonField)
						
			
			if ("flgTipoProv".equals(fieldName) || 
				"segnatura".equals(fieldName) ||
				"livelloRiservatezza".equals(fieldName) ||
				"numero".equals(fieldName) ||
				"anno".equals(fieldName) ||
				"tsRegistrazione".equals(fieldName) ||
				"oggetto".equals(fieldName) ||
				"priorita".equals(fieldName) ||
				"tipo".equals(fieldName) ||
				"tsDocumento".equals(fieldName) ||
				"fascicoliApp".equals(fieldName) ||
				"mittenti".equals(fieldName) ||
				"destinatari".equals(fieldName) ||
				"score".equals(fieldName) ||
				"flgDatiCompletiPerProtocollazione".equals(fieldName) ||
				"tsInvio".equals(fieldName)
					) {
				if (!fromScrivania  || idNode == null ) {
					field.setHidden(true);					
				} 
				else{
					if (idNode != null) {
						if (idNode.equalsIgnoreCase("D.BOZZE.DAPROT")  ||  idNode.equalsIgnoreCase("D.BOZZE")  || idNode.equalsIgnoreCase("D.2A.DAPROT")){
							  field.setHidden(false);	   // mostro la colonna
						}else{ 
							  field.setHidden(true);       // nascondo la colonna
						}
					}
				}
			}
			
		
		}
		
		
		super.setFields(fields);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				try {
					refreshFields();
				} catch (Exception e) {
				}
				markForRedraw();
			}
		});
	}

	@Override
	protected ShowInMenuFunction[] getShowInMenuFunction() {
		ScrivaniaLayout scrivaniaLayout = fromScrivania ? (ScrivaniaLayout) getLayout() : null;
		
		ShowInMenuFunctionFromScrivania lShowInMenuFunctionNEWS = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
				                                                                                      new ListGridField[] { tsRicezione, prioritaInvioNotifiche, estremiInvioNotifiche, msgDiInvio }, 
				                                                                                      this, 
				                                                                                      "idFolder", 
				                                                                                      "equals", new String[] { "-9" }
																									);
		
		ShowInMenuFunctionFromScrivania lShowInMenuFunctionELIMINATI = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
				                                                                                           new ListGridField[] {tsEliminazione, eliminatoDa }, 
				                                                                                           this, 
				                                                                                           "idFolder", 
				                                                                                           "equals", new String[] { "-99999" });
		
		ShowInMenuFunctionFromScrivania lShowInMenuFunctionINVIATI = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 		
				                                                                                         new ListGridField[] { tsInvio }, 
				                                                                                         this, 
				                                                                                         "idFolder", 
				                                                                                         "equals", new String[] { "-9999", "-99991" }
		                                                                                                );
		
		ShowInMenuFunctionFromScrivania lShowInMenuFunctionDESTINATARI_INVIO = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
																						                           new ListGridField[] { destinatariInvio }, 
																						                           this, 
																						                           "idFolder", 
																						                           "equals", new String[] { "-9999", "-99991" }
		                                                                                                          );

		
		// ottavio - AURIGA-357 : la colonna "Inviato a me"    deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2A  o F.2A  o DF.2A o D.2CC o F.2CC o DF.2CC			
	    //                        la colonna "Notificato a me" deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2NA o F.2NA o DF.2NA 						
		ShowInMenuFunctionFromScrivania lShowInMenuFunctionFLG_NOTIFICATO_A_ME = new ShowInMenuFunctionFromScrivania(scrivaniaLayout,
				                                                                                                     new ListGridField[] { flgNotificatoAMe }, 
				                                                                                                     this, 
				                                                                                                     "idNode", 
				                                                                                                     "startsWith", new String[] { "D.2A", "F.2A", "DF.2A", "D.2CC", "F.2CC", "DF.2CC","D.2NA", "F.2NA", "DF.2NA" }				                                                                                                     
																													);		
		if (fromScrivania) {

			ShowInMenuFunctionFromScrivania lShowInMenuFunctionDOCUMENTI = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
					                                                                                           new ListGridField[] { mittenti,destinatari, oggetto, tsRegistrazione, fascicoliApp }, 
					                                                                                           this, 
					                                                                                           "tipoNodo", 
					                                                                                           "equals", new String[] { "D", "FD" });
			
			ShowInMenuFunctionFromScrivania lShowInMenuFunction_BOZZE = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
					                                                                                               new ListGridField[] {flgRicevutaViaEmail, flgAnnReg, utenteProtocollante, motivoAnnullamento, uoProtocollante, repertorio, estremiUDTrasmessoInUscitaCon }, 
					                                                                                               this, 
					                                                                                               "idNode", 
					                                                                                               "startsWith", new String[] { "D.1", "D.2", "D.7", "D.5" }
																												);
			
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionFASCICOLI_E_DOC = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
					                                                                                                 new ListGridField[] {flgRicevutaViaEmail, nroProvvisorioAtto, pubblicazione, uoProponente }, 
					                                                                                                 this, 
					                                                                                                 "idNode", 
					                                                                                                 "startsWith", new String[] { "D.1", "D.2", "D.7", "D.5", "F" }					                                                                                                 
																													);
			
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionFOLDER = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
					                                                                                        new ListGridField[] { tsApertura, tsChiusura, descContenutiFascicolo, nome, nroSecondario}, 
					                                                                                        this, 
					                                                                                        "tipoNodo", 
					                                                                                        "equals", new String[] { "F", "FD" }
																										  );
			
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionALTRE_OP = new ShowInMenuFunctionFromScrivania(scrivaniaLayout,
                                                                                                              new ListGridField[] { altreOpButtonField }, 
                                                                                                              this, 
                                                                                                              "idNode", 
                                                                                                              "notEquals", new String[] { "D.23" }
																											 );
			
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionFLG_ASSEGNATO_A_ME = new ShowInMenuFunctionFromScrivania(scrivaniaLayout,
					                                                                                                    new ListGridField[] { flgAssegnatoAMe }, 
					                                                                                                    this, 
					                                                                                                    "idNode", 
					                                                                                                    "startsWith", new String[] { "FD.2A", "F.2A", "D.2A" }
																														);
			
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionANNULLAMENTI = new ShowInMenuFunctionFromScrivania(scrivaniaLayout,
					                                                                                              new ListGridField[] { attoAutAnnullamento }, 
					                                                                                              this, 
					                                                                                              "idNode", 
					                                                                                              "startsWith", new String[] { "D.13", "D.14" }
																												 );
			
			// ottavio - AURIGA-357 : la colonna "Data presa in carico" deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2A o F.2A o DF.2A AND che NON contiene la stringa ".DP"
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionDATA_PRESA_IN_CARICO = new ShowInMenuFunctionFromScrivania(scrivaniaLayout,
					                                                                                                      new ListGridField[] { tsPresaInCarico }, 
					                                                                                                      this, 
					                                                                                                      "idNode", 
					                                                                                                      "startsWith",  new String[] { "D.2A", "F.2A", "DF.2A" }  ,
					                                                                                                      "notContains", new String[] { ".DP" } 
																														  );
			
			// ottavio - AURIGA-357 : l'icona "Presa in carico" deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2A o F.2A o DF.2A AND che NON contiene la stringa ".DP"
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionFLG_PRESA_IN_CARICO = new ShowInMenuFunctionFromScrivania(scrivaniaLayout,
					                                                                                                     new ListGridField[] { flgPresaInCarico }, 
					                                                                                                     this, 
					                                                                                                     "idNode", 
					                                                                                                     "startsWith",  new String[] { "D.2A", "F.2A", "DF.2A" } ,
					                                                                                                     "notContains", new String[] { ".DP" }
                                                                                                                         );
			
			// ottavio - AURIGA-357 : la colonna "Priorità" deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.BOZZE, D.1, D.2, D.7, D.5
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionPRIORITA = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
					                                                                                          new ListGridField[] { priorita }, 
					                                                                                          this, 
					                                                                                          "idNode", 
					                                                                                          "startsWith", new String[] { "D.BOZZE", "D.1", "D.2", "D.7", "D.5"}
			                                                                                                 );
							
			// ottavio - AURIGA-517 : la colonna "Presente documentazione contratti" deve apparire in tutte e solo le sezioni con ci_nodo = D.2A.DP.R
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionPRESENTEDOCCONTRATTI = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
                                                                                                                          new ListGridField[] { flgPresenzaContratti }, 
                                                                                                                          this, 
                                                                                                                          "idNode", 
                                                                                                                          "equals", new String[] { "D.2A.DP.R" }
			                          																					 );
			
			// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.BOZZE.DAPROT, aggiungo la colonna : tsInvio
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionTS_INVIO = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
                                                                                                              new ListGridField[] {  tsInvio }, 
                                                                                                              this, 
                                                                                                              "idNode", 
                                                                                                              "equals", new String[] { "D.BOZZE.DAPROT"}
			               																					 );

			// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.2A.DAPROT, escudo le colonne
			ShowInMenuFunctionFromScrivania lShowInMenuFunction_ESCLUDI_NEWS = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
																								              	   new ListGridField[] {tsRicezione, prioritaInvioNotifiche, estremiInvioNotifiche, msgDiInvio }, 
																								                   this, 
																								                   "idNode", 
																								                   "startsWith", new String[] { "D.2"},
																								                   "notEquals", new String[] { "D.2A.DAPROT" }
																												  );
			
			// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.BOZZE o D.BOZZE.DAPROT o D.2A.DAPROT, aggiungo la colonne : flgDatiCompletiPerProtocollazione
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionDATI_COMPLETI_PROT = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
                    																									new ListGridField[] {  flgDatiCompletiPerProtocollazione }, 
																									                    this, 
																									                    "idNode", 
																									                    "equals", new String[] { "D.2A.DAFIRM", "D.BOZZE","D.BOZZE.DAPROT", "D.2A.DAPROT"}
																														);
			
			// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.BOZZE.DAPROT escludo le colonne
			ShowInMenuFunctionFromScrivania lShowInMenuFunction_ESCLUDI_STATO = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
           		 																										   new ListGridField[] {stato}, 
           		 																										   this, 
           		 																										   "idNode", 
           		 																										   "notEquals", new String[] { "D.BOZZE.DAPROT" }
				    																									  );
			
			// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.2A.DAPROT, escudo le colonna
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionESCLUDI_FLG_NOTIFICATO_A_ME = new ShowInMenuFunctionFromScrivania(scrivaniaLayout,
																											             	     new ListGridField[] { flgNotificatoAMe }, 
																											                     this, 
																											                     "idNode", 
																											                     "startsWith", new String[] { "D.2A", "F.2A", "DF.2A", "D.2CC", "F.2CC", "DF.2CC","D.2NA", "F.2NA", "DF.2NA" },
																											             		 "notEquals", new String[] { "D.2A.DAPROT" }
																															);
			
			// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.2A.DAPROT, escudo le colonna
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionESCLUDI_FLG_ASSEGNATO_A_ME = new ShowInMenuFunctionFromScrivania(scrivaniaLayout,
																											                    new ListGridField[] { flgAssegnatoAMe }, 
																											                    this, 
																											                    "idNode", 
																											                    "startsWith", new String[] { "FD.2A", "F.2A", "D.2A" },
																											                    "notEquals", new String[] { "D.2A.DAPROT" }
																																);

			// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.2A.DAPROT, escudo le colonna
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionESCLUDI_BOZZE = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
																											       new ListGridField[] {flgRicevutaViaEmail, flgAnnReg, utenteProtocollante, motivoAnnullamento, uoProtocollante, repertorio, estremiUDTrasmessoInUscitaCon }, 
																											       this, 
																											       "idNode", 
																											       "startsWith", new String[] { "D.1", "D.2", "D.7", "D.5" },
																											       "notEquals", new String[] { "D.2A.DAPROT" }
																												  );
						
			// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.2A.DAPROT, escudo le colonne
			ShowInMenuFunctionFromScrivania lShowInMenuFunctionESCLUDI_FASCICOLI_E_DOC = new ShowInMenuFunctionFromScrivania(scrivaniaLayout, 
																										                     new ListGridField[] {flgRicevutaViaEmail, nroProvvisorioAtto, pubblicazione, uoProponente }, 
																										                     this, 
																										                     "idNode", 
																										                     "startsWith", new String[] { "D.1", "D.2", "D.7", "D.5", "F" },
																										                     "notEquals", new String[] { "D.2A.DAPROT" }
																															 );
	
			// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.BOZZE.DAPROT o D.2A.DAPROT
						if (idNode!= null && (idNode.equals("D.BOZZE.DAPROT") || idNode.equals("D.2A.DAPROT"))) {
							return new ShowInMenuFunction[] { 	lShowInMenuFunctionDOCUMENTI, 
																lShowInMenuFunctionESCLUDI_FASCICOLI_E_DOC,  
										                        lShowInMenuFunctionESCLUDI_BOZZE,
										                        lShowInMenuFunctionFOLDER, 
										                        lShowInMenuFunctionALTRE_OP, 
										                        lShowInMenuFunction_ESCLUDI_NEWS,
										                        lShowInMenuFunctionELIMINATI, 
										                        lShowInMenuFunctionDESTINATARI_INVIO,
										                        lShowInMenuFunctionESCLUDI_FLG_ASSEGNATO_A_ME, 
										                        lShowInMenuFunctionFLG_PRESA_IN_CARICO, 
										                        lShowInMenuFunctionESCLUDI_FLG_NOTIFICATO_A_ME, 
										                        lShowInMenuFunctionANNULLAMENTI , 
										                        lShowInMenuFunctionDATA_PRESA_IN_CARICO,
										                        lShowInMenuFunctionPRIORITA,
										                        lShowInMenuFunctionPRESENTEDOCCONTRATTI,
										                        lShowInMenuFunctionDATI_COMPLETI_PROT,
										                        lShowInMenuFunctionTS_INVIO,
										                        lShowInMenuFunction_ESCLUDI_STATO							                        
															};
						}
						else{
							return new ShowInMenuFunction[] { 	lShowInMenuFunctionDOCUMENTI, 
										                        lShowInMenuFunctionFASCICOLI_E_DOC, 
										                        lShowInMenuFunction_BOZZE,
										                        lShowInMenuFunctionFOLDER, 
										                        lShowInMenuFunctionALTRE_OP, 
										                        lShowInMenuFunctionNEWS, 
										                        lShowInMenuFunctionELIMINATI, 
										                        lShowInMenuFunctionDESTINATARI_INVIO,
										                        lShowInMenuFunctionFLG_ASSEGNATO_A_ME, 
										                        lShowInMenuFunctionFLG_PRESA_IN_CARICO, 
										                        lShowInMenuFunctionFLG_NOTIFICATO_A_ME, 
										                        lShowInMenuFunctionANNULLAMENTI , 
										                        lShowInMenuFunctionDATA_PRESA_IN_CARICO,
										                        lShowInMenuFunctionPRIORITA,
										                        lShowInMenuFunctionPRESENTEDOCCONTRATTI,
										                        lShowInMenuFunctionDATI_COMPLETI_PROT,
										                        lShowInMenuFunction_ESCLUDI_STATO,
										                        lShowInMenuFunctionINVIATI
															};
						}
					}
					return new ShowInMenuFunction[] { lShowInMenuFunctionNEWS, 
			                lShowInMenuFunctionELIMINATI, 
			                lShowInMenuFunctionINVIATI,
			                lShowInMenuFunctionDESTINATARI_INVIO,
			                lShowInMenuFunctionFLG_NOTIFICATO_A_ME };
				}

	protected MenuItem[] getHeaderContextMenuItems(Integer fieldNum) {
		if (fromScrivania && idNode != null && (idNode.equals("D.23"))   ) {
			MenuItem[] lMenuItems = super.getHeaderContextMenuItems(fieldNum);
			if (lMenuItems == null)
				return null;
			// Recupero i reali menuItem[]
			MenuItem[] lMenuItemsRetrieved = MenuUtil.retrieveMenuFromJavascriptMenu(lMenuItems);
			MenuItem lMenuItemColonne = retrieveColonne(lMenuItemsRetrieved);
			if (lMenuItemColonne != null) {
				// Recupero la posizione del menu Colonne
				int posColonne = lMenuItemColonne.getAttributeAsInt("position");
				// Recupero il submenu di Colonne
				MenuItem[] lSubmenuItemsColonne = MenuUtil.retrieveSubMenuFromJavascriptMenu(lMenuItemColonne);
				List<MenuItem> lSubmenuItemsColonneList = new ArrayList<MenuItem>(lSubmenuItemsColonne.length);
				int toShow = 0;
				for (MenuItem lSubmenuItem : lSubmenuItemsColonne) {
					String name = lSubmenuItem.getTitle();
					if (name.equals(tsRegistrazione.getTitle()) || name.equals(segnaturaXOrd.getTitle()) || name.equals(oggetto.getTitle())
							|| name.equals(tipo.getTitle())) {
						lSubmenuItemsColonneList.add(lSubmenuItem);
						toShow++;
					}
				}
				MenuItem[] lSubmenuItemsColonneNew = new MenuItem[toShow];
				for (int count = 0; count < toShow; count++) {
					lSubmenuItemsColonneNew[count] = lSubmenuItemsColonneList.get(count);
				}
				Menu lSubmenuColonneNew = new Menu();
				lSubmenuColonneNew.setItems(lSubmenuItemsColonneNew);
				lMenuItems[posColonne].setSubmenu(lSubmenuColonneNew);
			}
			final ListGridField field = super.getField(fieldNum);
			if (field.getGroupingModes() != null && field.getGroupingModes().keySet().size() > 0) {
				// Recupero il menu Raggruppa per
				MenuItem lMenuItemRaggruppaPer = retrieveRaggruppaPer(lMenuItemsRetrieved);
				if (lMenuItemRaggruppaPer != null) {
					// Recupero la posizione del menu Raggruppa per
					int posRaggruppaPer = lMenuItemRaggruppaPer.getAttributeAsInt("position");
					MenuItem[] lSubmenuItemsRaggruppaPer = new MenuItem[field.getGroupingModes().keySet().size()];
					int cont = 0;
					for (Object key : field.getGroupingModes().keySet()) {
						final String groupingMode = (String) key;
						lSubmenuItemsRaggruppaPer[cont] = new MenuItem((String) field.getGroupingModes().get(key));
						lSubmenuItemsRaggruppaPer[cont].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								field.setGroupingMode(groupingMode);
								instance.groupBy(field.getName());
							}
						});
						cont++;
					}
					Menu lSubmenuRaggruppaPer = new Menu();
					lSubmenuRaggruppaPer.setItems(lSubmenuItemsRaggruppaPer);
					lMenuItems[posRaggruppaPer].setSubmenu(lSubmenuRaggruppaPer);
				}
			}
			return lMenuItems;
		}

		else
			return super.getHeaderContextMenuItems(fieldNum);
	}

	// Metodo per visualizzare il DETTAGLIO ( eseguito quando si clicca sul
	// bottone )
	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			manageLoadDetailAcc(record, recordNum, callback);
		} else {
			manageLoadDetailNoAcc(record, recordNum, callback);
		}
	}

	// Definisco i bottoni DETTAGLIO/MODIFICA/CANCELLA/SELEZIONA
	@Override
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {
		Canvas lCanvasReturn = null;

		if (fieldName.equals("buttons")) {

			ImgButton detailButton = buildDetailButton(record);

			ImgButton scaricaFileButton = new ImgButton();
			scaricaFileButton.setShowDown(false);
			scaricaFileButton.setShowRollOver(false);
			scaricaFileButton.setSrc("file/download_manager.png");
			scaricaFileButton.setPrompt("Scarica file");
			scaricaFileButton.setSize(16);
			scaricaFileButton.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					scaricaFilePrimarioOnClick(record);
				}
			});
			
			ImgButton visualizzaFileButton = new ImgButton();
			visualizzaFileButton.setShowDown(false);
			visualizzaFileButton.setShowRollOver(false);
			visualizzaFileButton.setSrc("file/preview.png");
			visualizzaFileButton.setPrompt("Visualizza file");
			visualizzaFileButton.setSize(16);
			visualizzaFileButton.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					visualizzaFilePrimarioOnClick(record);
				}
			});

			ImgButton lookupButton = buildLookupButton(record);

			HLayout recordCanvas = new HLayout(4);
			recordCanvas.setHeight(22);
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);
			recordCanvas.setMembersMargin(7);
			
			if (fromScrivania && idNode != null && idNode.equals("D.23")) {
				recordCanvas.addMember(scaricaFileButton);
			} else if (fromScrivania && idNode != null && (idNode.equals("D.PROTNOSCAN") || idNode.equals("D.SCANNOASS"))) {
				//TODO ha senso per la sezione D.PROTNOSCAN ?
				recordCanvas.addMember(visualizzaFileButton);
			} else if (isRecordAbilToView(record)) {
				recordCanvas.addMember(detailButton);
			}

			if (layout.isLookup()) {
				if (!isRecordSelezionabileForLookup(record)) {
					lookupButton.disable();
				}
				recordCanvas.addMember(lookupButton); // aggiungo il bottone SELEZIONA
			}
			lCanvasReturn = recordCanvas;
		}
		return lCanvasReturn;
	}

	@Override
	protected boolean isRecordSelezionabileForLookup(ListGridRecord record) {
		return record.getAttributeAsBoolean("flgSelXFinalita");
	}

	public void showRowContextMenu(final ListGridRecord record, final Menu navigationContextMenu) {
		if (record.getAttribute("flgUdFolder").equals("U")) {
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
			getAbilitazioniProtocolloDataSource().getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record detailRecord = response.getData()[0];
						Record recordDestPref = new Record();						
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						Record recordAzioneRapidaSmista = new Record();
						recordAzioneRapidaSmista.setAttribute("azioneRapida", AzioniRapide.SMISTA_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaSmista);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
						recordDestPref.setAttribute("idUd", record.getAttribute("idUdFolder"));
						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
						lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
								new DSCallback() {

									@Override
									public void execute(DSResponse responseDestPref, Object rawDataDestPref,
											DSRequest requestDestPref) {
										if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
											if (AurigaLayout.getIsAttivaAccessibilita()) {
												Record destinatariPreferiti = responseDestPref.getData()[0];
	
												final Menu contextMenu = new Menu();
												contextMenu.setCanFocus(true);
												final Menu altreOpMenu = createUdAltreOpMenu(record, detailRecord,
														destinatariPreferiti);
												for (int i = 0; i < altreOpMenu.getItems().length; i++) {
													MenuItem item = altreOpMenu.getItems()[i];
													item.setAttribute("aria-label", item.getTitle());
													contextMenu.addItem(item, i);
												}
												if (navigationContextMenu != null) {
													for (int i = 0; i < navigationContextMenu.getItems().length; i++) {
														MenuItem item = navigationContextMenu.getItems()[i];
														item.setAttribute("aria-label", item.getTitle());
														contextMenu.addItem(item, i);
													}
													MenuItem separatorMenuItem = new MenuItem();
													separatorMenuItem.setIsSeparator(true);
													contextMenu.addItem(separatorMenuItem,
															navigationContextMenu.getItems().length);
												}
												contextMenu.showContextMenu();												
											} else {
												Record destinatariPreferiti = responseDestPref.getData()[0];
	
												final Menu contextMenu = new Menu();
												final Menu altreOpMenu = createUdAltreOpMenu(record, detailRecord,
														destinatariPreferiti);
												for (int i = 0; i < altreOpMenu.getItems().length; i++) {
													contextMenu.addItem(altreOpMenu.getItems()[i], i);
												}
												if (navigationContextMenu != null) {
													for (int i = 0; i < navigationContextMenu.getItems().length; i++) {
														contextMenu.addItem(navigationContextMenu.getItems()[i], i);
													}
													MenuItem separatorMenuItem = new MenuItem();
													separatorMenuItem.setIsSeparator(true);
													contextMenu.addItem(separatorMenuItem,
															navigationContextMenu.getItems().length);
												}
												contextMenu.showContextMenu();
											}
										}
									}
								}, new DSRequest());
					}
				}
			});
		} else if (record.getAttribute("flgUdFolder").equals("F")) {
			if ((record.getAttribute("nroFascicolo") != null && !"".equals(record.getAttribute("nroFascicolo")))
					|| (record.getAttribute("percorsoNome") != null && !"".equals(record.getAttribute("percorsoNome")))) {
				// se è un fascicolo o un sotto-fascicolo
				getAbilitazioniArchivioDataSource().performCustomOperation("get", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							
							final Record detailRecord = response.getData()[0];
							Record recordDestPref = new Record();						
							RecordList listaAzioniRapide = new RecordList();
							Record recordAzioneRapidaAssegna = new Record();
							recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_FOLDER.getValue()); 
							listaAzioniRapide.add(recordAzioneRapidaAssegna);
							Record recordAzioneRapidaInvioCC = new Record();
							recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_FOLDER.getValue()); 
							listaAzioniRapide.add(recordAzioneRapidaInvioCC);
							Record recordAzioneRapidaSmista = new Record();
							recordAzioneRapidaSmista.setAttribute("azioneRapida", AzioniRapide.SMISTA_DOC.getValue()); 
							listaAzioniRapide.add(recordAzioneRapidaSmista);
							recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
							lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
									new DSCallback() {

										@Override
										public void execute(DSResponse responseDestPref, Object rawDataDestPref,
												DSRequest requestDestPref) {
											if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
												Record destinatariPreferiti = responseDestPref.getData()[0];
												
												final Menu contextMenu = new Menu();
												final Menu altreOpMenu = createFolderAltreOpMenu(record, detailRecord, destinatariPreferiti);
												for (int i = 0; i < altreOpMenu.getItems().length; i++) {
													contextMenu.addItem(altreOpMenu.getItems()[i], i);
												}
												if (navigationContextMenu != null) {
													for (int i = 0; i < navigationContextMenu.getItems().length; i++) {
														contextMenu.addItem(navigationContextMenu.getItems()[i], i);
													}
													MenuItem separatorMenuItem = new MenuItem();
													separatorMenuItem.setIsSeparator(true);
													contextMenu.addItem(separatorMenuItem, navigationContextMenu.getItems().length);
												}
												if (AurigaLayout.getIsAttivaAccessibilita()) {
													contextMenu.setCanFocus(true);
												}
												contextMenu.showContextMenu();
											}
										}
									}, new DSRequest());
						}
					}
				}, new DSRequest());
			} else {
				// se è una classifica
				if (navigationContextMenu != null) {
					final Menu contextMenu = new Menu();
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							for (int i = 0; i < navigationContextMenu.getItems().length; i++) {
								contextMenu.addItem(navigationContextMenu.getItems()[i], i);
							}
							if (contextMenu.getItems().length > 0) {
								if (AurigaLayout.getIsAttivaAccessibilita()) {
									contextMenu.setCanFocus(true);
								}
								contextMenu.showContextMenu();
							}
						}
					});
				}
			}
		}
	}
	
	protected void manageEliminaImgScansione(final ListGridRecord record) {
		
		final Record lRecordToDelete = new Record();
		lRecordToDelete.setAttribute("idUd", record.getAttribute("idUdFolder"));
		
		SC.ask("Sei sicuro di voler cancellare l'immagine ?", new BooleanCallback() {
			@Override 
			public void execute(Boolean value) {  
				if (value) {
					getProtocolloDataSource().executecustom("cancellazioneFisica", lRecordToDelete, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout.getTipoEstremiRecord(record)), "", MessageType.INFO));
								layout.doSearch();
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(layout.getTipoEstremiRecord(record)), "", MessageType.ERROR));
							}
						}
					});
				}
			} 
		});
	}
	
	protected void manageAssociazioneImgAProtocollo(ListGridRecord record) {
		
		final String listaIdUDScansioniFinal = record.getAttribute("idUdFolder");
		
		final CercaUDPopup lCercaUDPopup = new CercaUDPopup() {
			
			@Override
			public void onClickButton() {
				
				final String idUd = getIdUd();	
				final String nroProt = getAnnoProt();
				final String annoProt = getNumProt();
				
				if(idUd!=null && !"".equalsIgnoreCase(idUd)) {
					apriProtocolloAssociatoImg(listaIdUDScansioniFinal, idUd, nroProt, annoProt, _window);
				}else if(nroProt!=null && !"".equalsIgnoreCase(nroProt) && annoProt!=null && !"".equalsIgnoreCase(annoProt)) {
					cercaProtocolloEvent(new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							if(object.getAttribute("idUd")!= null && !"".equalsIgnoreCase(object.getAttribute("idUd"))) {
								apriProtocolloAssociatoImg(listaIdUDScansioniFinal, object.getAttribute("idUd"), nroProt, annoProt, _window);
							}else {
								SC.warn("Protocollo inesistente");
							}							
						}
					});
				}else {
					SC.warn("Protocollo inesistente");
				}								
			}
		};
		lCercaUDPopup.show();
		
	}
	
	
	public void scaricaFilePrimarioOnClick(ListGridRecord record) {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
		getProtocolloDataSource().getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					scaricaFilePrimario(response.getData()[0]);
				}
			}
		});
	}
	
	
	
	public void visualizzaFilePrimarioOnClick(ListGridRecord record) {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
		getProtocolloDataSource().getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					visualizzaFilePrimario(response.getData()[0]);
				}
			}
		});
	}
	
	

	public void visualizzaFilePrimario(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		Object infoFile = detailRecord.getAttributeAsObject("infoFile");
		visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaEditFilePrimario(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		Object infoFile = detailRecord.getAttributeAsObject("infoFile");
		visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaFileAllegato(Record detailRecord, int nroAllegato) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
		String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
		String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
		String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
		visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaEditFileAllegato(Record detailRecord, int nroAllegato) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
		String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
		String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
		String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
		visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaFile(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		addToRecent(idUd, idDoc);
		InfoFileRecord info = new InfoFileRecord(infoFile);
		PreviewControl.switchPreview(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display);
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display);
	}

	public void visualizzaEditFile(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		addToRecent(idUd, idDoc);
		InfoFileRecord info = new InfoFileRecord(infoFile);
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), info.getMimetype(), idUd
				,rotazioneTimbroPref,posizioneTimbroPref);
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("LoadTimbraDefault");
		lGwtRestService.call(lFileDaTimbrareBean, new ServiceCallback<Record>() {

			@Override
			public void execute(Record lOpzioniTimbro) {
				boolean timbroEnabled = detailRecord != null && detailRecord.getAttribute("flgTipoProv") != null
						&& !"".equals(detailRecord.getAttribute("flgTipoProv")) && detailRecord.getAttribute("idUd") != null
						&& !"".equals(detailRecord.getAttribute("idUd"));
				PreviewDocWindow previewDocWindow = new PreviewDocWindow(uri, display, Boolean.valueOf(remoteUri), timbroEnabled, lOpzioniTimbro) {

					@Override
					public void uploadCallBack(String filePdf, String uriPdf, String record) {

					}
				};
				previewDocWindow.show();
			}
		});
	}

	public void scaricaFilePrimario(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		scaricaFile(idUd, idDoc, display, uri, remoteUri);
	}


	public void scaricaFile(String idUd, String idDoc, String display, String uri, String remoteUri) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}


	public void scaricaFileSbustato(String idUd, String idDoc, String display, String uri, String remoteUri, Object infoFile) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", remoteUri);
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(infoFile);
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	public void timbraFilePrimario(Record detailRecord, boolean fileIntegrale) {
		
		String idUd = detailRecord.getAttributeAsString("idUd");
		
		String display = null;
		String uri = null;
		String remoteUri = null;
		Object infoFile = null;
		String idDoc = null;
		Integer nroProgAllegato = null;
		
		String tipoTimbro = detailRecord.getAttributeAsString("tipoTimbro");
		String finalita = detailRecord.getAttributeAsString("finalita") !=null ? detailRecord.getAttributeAsString("finalita") : "";
		
		if (fileIntegrale) {
			idUd = detailRecord.getAttributeAsString("idUd");
			display = detailRecord.getAttributeAsString("nomeFilePrimario");
			uri = detailRecord.getAttributeAsString("uriFilePrimario");
			remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
			infoFile = detailRecord.getAttributeAsObject("infoFile");
			idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		} else {
			Record filePrimarioOmissis=detailRecord.getAttributeAsRecord("filePrimarioOmissis");
			display = filePrimarioOmissis.getAttributeAsString("nomeFile");
			uri = filePrimarioOmissis.getAttributeAsString("uriFile");
			remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
			infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
			idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
		}
		
		idDoc = idDoc != null ? idDoc : "";
		tipoTimbro = tipoTimbro != null ? tipoTimbro : "";
		timbraFile(idDoc, tipoTimbro, idUd, display, uri, remoteUri, infoFile, finalita, nroProgAllegato);
	}

	public void timbraFileAllegato(Record detailRecord, int nroAllegato, boolean fileIntegrale) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		
		String display = null;
		String uri = null;
		String remoteUri = null;
		Object infoFile = null;
		String idDoc = null;
		Integer nroProgAllegato = null;
		
		String tipoTimbro = detailRecord.getAttributeAsString("tipoTimbro");
		String finalita = detailRecord.getAttributeAsString("finalita") !=null ? detailRecord.getAttributeAsString("finalita") : "";
		
		if (fileIntegrale) {
			idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
			display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
			uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
			remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
			infoFile = allegatoRecord.getAttributeAsObject("infoFile");
			nroProgAllegato = allegatoRecord.getAttributeAsInt("numeroProgrAllegato");
		} else {
			idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
			display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
			uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
			remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
			infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
			nroProgAllegato = allegatoRecord.getAttributeAsInt("numeroProgrAllegato");
		}

		idDoc = idDoc != null ? idDoc : "";
		tipoTimbro = tipoTimbro != null ? tipoTimbro : "";

		timbraFile(idDoc, tipoTimbro, idUd, display, uri, remoteUri, infoFile, finalita, nroProgAllegato);
	}

	public void timbraFile(String idDoc, String tipoTimbro, String idUd, String display, String uri, String remoteUri, Object infoFile, String finalita, Integer nroProgAllegato) {
		InfoFileRecord precInfo = new InfoFileRecord(infoFile);
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina") != null ?
				AurigaLayout.getImpostazioneTimbro("tipoPagina") : "";
				
		/*Controllo introdotto per la nuova modalità di timbratura per i file firmati che devono saltare la scelta della preferenza*/
		boolean skipSceltaTimbratura = AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniTimbroSegnatura");
		boolean flgBustaPdfTimbratura = false;
		
		if(precInfo.isFirmato() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_BUSTA_PDF_FILE_FIRMATO") && !finalita.equalsIgnoreCase("CONFORMITA_ORIG_DIGITALE_STAMPA")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = true;
		}
		
		if(finalita.equalsIgnoreCase("COPIA_CONFORME_CUSTOM")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = false;
		}
		
		if(skipSceltaTimbratura){
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			record.setAttribute("nomeFile", display);
			record.setAttribute("uri", uri);
			record.setAttribute("remote", remoteUri);
			record.setAttribute("mimetype", precInfo.getMimetype());
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			if(nroProgAllegato!=null) {
				record.setAttribute("nroProgAllegato", nroProgAllegato);
			}
			if (finalita.equals("CONFORMITA_ORIG_CARTACEO")) {
				record.setAttribute("tipoPagina", "tutte");
			}else {
				record.setAttribute("tipoPagina", tipoPaginaPref);
			}
			record.setAttribute("finalita", finalita);
			
			if(flgBustaPdfTimbratura) {
				TimbroUtil.callStoreLoadFilePerTimbroConBusta(record);
			}else {
				TimbroUtil.buildDatiSegnatura(record);
			}				
		}else{
		
			FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), precInfo.getMimetype(), idUd, idDoc,
					tipoTimbro,"D",rotazioneTimbroPref,posizioneTimbroPref);
			lFileDaTimbrareBean.setAttribute("tipoPagina", tipoPaginaPref);
			lFileDaTimbrareBean.setAttribute("finalita", finalita);
			lFileDaTimbrareBean.setAttribute("skipScelteOpzioniCopertina", "false");
			if(nroProgAllegato!=null) {
				lFileDaTimbrareBean.setAttribute("nroProgAllegato", nroProgAllegato);
			}
			TimbraWindow lTimbraWindow = new TimbraWindow("timbra", true, lFileDaTimbrareBean);
			lTimbraWindow.show();
		}
	}

	public void addToRecent(String idUd, String idDoc) {
		if (idUd != null && !"".equals(idUd) && idDoc != null && !"".equals(idDoc)) {
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AddToRecentDataSource");
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
				}
			});
		}
	}

	public Menu createUdAltreOpMenu(final ListGridRecord listRecord, final Record detailRecord, final Record destinatariPreferiti) {
		
		mReloadListCallback.setRecord(detailRecord);
		mReloadListCallback.setLayout(layout);
		final Menu altreOpMenu = new Menu();
		if (isDownloadZipVisible(detailRecord)) {
			MenuItem docZipMenu = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip(), "buttons/download_zip.png");
			Menu docZipSubMenu = new Menu();	
			
			MenuItem scaricaFileMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_originali(), "buttons/download_zip.png");
			scaricaFileMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					manageOnClickScaricaFile(listRecord, detailRecord, "scaricaOriginali");
				}
			});
			docZipSubMenu.addItem(scaricaFileMenuItem);
			
			if(showOperazioniTimbratura(detailRecord)) {
						
				MenuItem scaricaFileTimbratiSegnaturaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_segnatura(), "buttons/download_zip.png");
				scaricaFileTimbratiSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						manageOnClickScaricaFile(listRecord, detailRecord, "scaricaTimbratiSegnatura");
					}
				});
				docZipSubMenu.addItem(scaricaFileTimbratiSegnaturaMenuItem);
				
				
				if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA")) {
					MenuItem scaricaFileTimbratiConformeStampaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_stampa(), "buttons/download_zip.png");
					scaricaFileTimbratiConformeStampaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							manageOnClickScaricaFile(listRecord, detailRecord, "scaricaTimbratiConformeStampa");
						}
					});
					docZipSubMenu.addItem(scaricaFileTimbratiConformeStampaMenuItem);
				
					MenuItem scaricaFileTimbratiConformeDigitaleMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_digitale(), "buttons/download_zip.png");
					scaricaFileTimbratiConformeDigitaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							manageOnClickScaricaFile(listRecord, detailRecord, "scaricaTimbratiConformeDigitale");
						}
					});
					docZipSubMenu.addItem(scaricaFileTimbratiConformeDigitaleMenuItem);
				}
				
				if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
					MenuItem scaricaFileTimbratiConformeCartaceoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_cartaceo(), "buttons/download_zip.png");
					scaricaFileTimbratiConformeCartaceoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							manageOnClickScaricaFile(listRecord, detailRecord, "scaricaTimbratiConformeCartaceo");
						}
					});
					docZipSubMenu.addItem(scaricaFileTimbratiConformeCartaceoMenuItem);
				}
			}
			
			MenuItem scaricaFileSbustatiMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_sbustati(), "buttons/download_zip.png");
			scaricaFileSbustatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageOnClickScaricaFile(listRecord, detailRecord, "scaricaSbustati");
				}
			});
			docZipSubMenu.addItem(scaricaFileSbustatiMenuItem);
			
			if(Layout.isPrivilegioAttivo("SCC")) {
				String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
				MenuItem scaricaFileConformitaCustomMenuItem = new MenuItem("File " + labelConformitaCustom, "buttons/download_zip.png");
				scaricaFileConformitaCustomMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						manageOnClickScaricaFile(listRecord, detailRecord, "scaricaConformitaCustom");
					}
				});
				docZipSubMenu.addItem(scaricaFileConformitaCustomMenuItem);
			}
			
			docZipMenu.setSubmenu(docZipSubMenu);
			altreOpMenu.addItem(docZipMenu);
		}
		
		// Se sono nella sezione "Documenti protocollati in attesa immagini" o  "Immagini non associate ai protocolli"
		if (fromScrivania && idNode != null && (idNode.equals("D.PROTNOSCAN") || idNode.equals("D.SCANNOASS"))) {
			//TODO hanno senso per la sezione D.PROTNOSCAN ?
			// Associa immagine/i a protocollo
			MenuItem associazioneImgAProtocolloMenuItem = new MenuItem("Associa immagine/i a protocollo", "postaElettronica/associaProtocolloPratica.png");
			associazioneImgAProtocolloMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					manageAssociazioneImgAProtocollo(listRecord);
				}
			});
			altreOpMenu.addItem(associazioneImgAProtocolloMenuItem);
				
			// Elimina
			MenuItem eliminaMenuItem = new MenuItem("Elimina", "buttons/delete.png");
			eliminaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					manageEliminaImgScansione(listRecord);
				}
			});
			altreOpMenu.addItem(eliminaMenuItem);
			
			// Scarica file primario
			MenuItem scaricaFilePrimarioMenuItem = new MenuItem("Scarica file", "file/download_manager.png");
			scaricaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					scaricaFilePrimarioOnClick(listRecord);
				}
			});
			altreOpMenu.addItem(scaricaFilePrimarioMenuItem);
			
			// Visualizza file primario
			MenuItem visualizzaFilePrimarioMenuItem = new MenuItem("Visualizza file", "file/preview.png");
			visualizzaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					visualizzaFilePrimarioOnClick(listRecord);
				}
			});
			altreOpMenu.addItem(visualizzaFilePrimarioMenuItem);	
						
		}
		// Se sono nella sezione Stampe ed esportazioni su file
		else if (fromScrivania && idNode != null && idNode.equals("D.23")) {
			MenuItem scaricaMenuItem = new MenuItem("Scarica file", "file/download_manager.png");
			scaricaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					scaricaFilePrimarioOnClick(listRecord);
				}
			});
			altreOpMenu.addItem(scaricaMenuItem);

			if (Layout.isPrivilegioAttivo("GRD/UD/DUD")) {
				MenuItem eliminaMenuItem = new MenuItem("Elimina", "buttons/delete.png");
				eliminaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final Record lRecordToDelete = new Record();
						lRecordToDelete.setAttribute("idUd", listRecord.getAttribute("idUdFolder"));
						SC.ask("Sei sicuro di voler procedere all'eliminazione fisica del documento?", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									getProtocolloDataSource().executecustom("cancellazioneFisica", lRecordToDelete, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(
														layout.getTipoEstremiRecord(listRecord)), "", MessageType.INFO));
												layout.doSearch();
											} else {
												Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(
														layout.getTipoEstremiRecord(listRecord)), "", MessageType.ERROR));
											}
										}
									});
								}
							}
						});
					}
				});
				altreOpMenu.addItem(eliminaMenuItem);
			}
		} else {
			MenuItem visualizzaMenuItem = new MenuItem("Visualizza", "buttons/detail.png");
			visualizzaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					manageDetailButtonClick(listRecord);
				}
			});
			altreOpMenu.addItem(visualizzaMenuItem);

			int nroDocConFile = listRecord.getAttribute("nroDocConFile") != null && !listRecord.getAttribute("nroDocConFile").isEmpty() ? Integer
					.valueOf(listRecord.getAttribute("nroDocConFile")) : 0;

			if (nroDocConFile > 0) {
				Record filePrimarioOmissis=detailRecord.getAttributeAsRecord("filePrimarioOmissis");
				
				MenuItem visualizzaFileMenuItem = new MenuItem("File", "archivio/file.png");
				Menu fileAssociatiSubMenu = new Menu();
				fileAssociatiSubMenu.setEmptyMessage("Nessun file su cui<br/>hai diritti di accesso");
				
				//File primario senza omissis
				if ((detailRecord.getAttributeAsString("uriFilePrimario") != null
						&& !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
						((filePrimarioOmissis.getAttributeAsString("uriFile") == null) || "".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
					
					MenuItem filePrimarioIntegraleMenuItem = new MenuItem("File primario - " + detailRecord.getAttributeAsString("nomeFilePrimario"));
					buildFilePrimarioMenuItem(listRecord,detailRecord,filePrimarioIntegraleMenuItem, true);
					fileAssociatiSubMenu.addItem(filePrimarioIntegraleMenuItem);
					
				}
				
				//File primario con versione omissis
				else if((detailRecord.getAttributeAsString("uriFilePrimario") != null
						&& !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
						((filePrimarioOmissis.getAttributeAsString("uriFile") != null) && !"".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
					
					//File primario integrale
					MenuItem filePrimarioIntegraleMenuItem = new MenuItem("File primario (vers. integrale) - " + detailRecord.getAttributeAsString("nomeFilePrimario"));
					buildFilePrimarioMenuItem(listRecord,detailRecord,filePrimarioIntegraleMenuItem, true);
					fileAssociatiSubMenu.addItem(filePrimarioIntegraleMenuItem);
					
					//File primario omissis
					MenuItem filePrimarioOmissisMenuItem = new MenuItem("File primario (vers. con omissis) - " +filePrimarioOmissis.getAttributeAsString("nomeFile"));
					buildFilePrimarioMenuItem(listRecord,detailRecord,filePrimarioOmissisMenuItem, false);
					fileAssociatiSubMenu.addItem(filePrimarioOmissisMenuItem);
				}
				
				//File primario solo omissis
				else if((detailRecord.getAttributeAsString("uriFilePrimario") == null
						|| "".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
						((filePrimarioOmissis.getAttributeAsString("uriFile") != null) && !"".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
					
					MenuItem filePrimarioOmissisMenuItem = new MenuItem("File primario (vers. con omissis) - " + filePrimarioOmissis.getAttributeAsString("nomeFile"));
					buildFilePrimarioMenuItem(listRecord,detailRecord,filePrimarioOmissisMenuItem, false);
					fileAssociatiSubMenu.addItem(filePrimarioOmissisMenuItem);
				}
				
				
				//Aggiungo al menu gli allegati
				RecordList listaAllegati = detailRecord.getAttributeAsRecordList("listaAllegati");
				
				if (listaAllegati != null) {
					for (int n = 0; n < listaAllegati.getLength(); n++) {
						final int nroAllegato = n;
						final Record allegatoRecord = listaAllegati.get(n);
						
						//Allegato senza omissis
						if((allegatoRecord.getAttributeAsString("uriFileAllegato")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")==null || "".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
							
							MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Allegato N°"+allegatoRecord.getAttributeAsString("numeroProgrAllegato") +" - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
							buildAllegatiMenuItem(listRecord,detailRecord,allegatoRecord, fileAllegatoIntegraleMenuItem, nroAllegato, true);
							fileAssociatiSubMenu.addItem(fileAllegatoIntegraleMenuItem);
						}
						
						//Entrambi versioni di allegati
						else if((allegatoRecord.getAttributeAsString("uriFileAllegato")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
							
							//Allegato integrale
							MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Allegato N°"+allegatoRecord.getAttributeAsString("numeroProgrAllegato") +" (vers. integrale) - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
							buildAllegatiMenuItem(listRecord,detailRecord,allegatoRecord, fileAllegatoIntegraleMenuItem, nroAllegato,true);
							fileAssociatiSubMenu.addItem(fileAllegatoIntegraleMenuItem);
							
							//Alegato omissis
							MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Allegato N°"+allegatoRecord.getAttributeAsString("numeroProgrAllegato") +" (vers. con omissis) - " + allegatoRecord.getAttributeAsString("nomeFileOmissis"));
							buildAllegatiMenuItem(listRecord,detailRecord,allegatoRecord, fileAllegatoOmissisMenuItem, nroAllegato,false);
							fileAssociatiSubMenu.addItem(fileAllegatoOmissisMenuItem);
						}
						
						//Allegato solo omissis
						else if((allegatoRecord.getAttributeAsString("uriFileAllegato")==null || "".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
							
							MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Allegato N°"+allegatoRecord.getAttributeAsString("numeroProgrAllegato") +" (vers. con omissis) - " + allegatoRecord.getAttributeAsString("nomeFileOmissis"));
							buildAllegatiMenuItem(listRecord,detailRecord,allegatoRecord, fileAllegatoOmissisMenuItem, nroAllegato,false);
							fileAssociatiSubMenu.addItem(fileAllegatoOmissisMenuItem);
						}
					}
				}
			
				visualizzaFileMenuItem.setSubmenu(fileAssociatiSubMenu);
				altreOpMenu.addItem(visualizzaFileMenuItem);
			}
			
			if (detailRecord.getAttributeAsBoolean("abilRevocaAtto")) {
				MenuItem revocaAttoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_revoca_atto_button(),
						"buttons/revoca_atto.png");
				revocaAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						manageRevocaAttoButtonClick(listRecord);
					}
				});
				altreOpMenu.addItem(revocaAttoMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilProtocollazioneEntrata")) {
				MenuItem protocollazioneEntrataMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_protocollazione_entrata_button(),
						"menu/protocollazione_entrata.png");
				protocollazioneEntrataMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						manageProtocollazioneEntrataButtonClick(listRecord);
					}
				});
				altreOpMenu.addItem(protocollazioneEntrataMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilProtocollazioneUscita")) {
				MenuItem protocollazioneUscitaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_protocollazione_uscita_button(),
						"menu/protocollazione_uscita.png");
				protocollazioneUscitaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						manageProtocollazioneUscitaButtonClick(listRecord);
					}
				});
				altreOpMenu.addItem(protocollazioneUscitaMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilProtocollazioneInterna")) {
				MenuItem protocollazioneInternaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_protocollazione_interna_button(), 
						"menu/protocollazione_interna.png");
				protocollazioneInternaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						manageProtocollazioneInternaButtonClick(listRecord);
					}
				});
				altreOpMenu.addItem(protocollazioneInternaMenuItem);
			}
			
			/*** TODO: FEDERICO TROTTA GESTIRE ABILITAZIONE E IMMAGINE ***/
			if (Layout.isPrivilegioAttivo("PRT/U") && "U".equalsIgnoreCase(detailRecord.getAttribute("flgTipoProv")) && AurigaLayout.getParametroDB("CLIENTE").equalsIgnoreCase("ARPA_LAZ")) {

				MenuItem inviaRaccomandataMenuItem = new MenuItem("Invio raccomandata", "postaElettronica/inoltro.png");
				inviaRaccomandataMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						
						ProtocollazioneUtil.isPossibleToPostel(detailRecord.getAttributeAsInt("idUd"), ETypePoste.RACCOMANDATA.value(), new ServiceCallback<Record>() {
							@Override
							public void execute(Record record) {
								if(record.getAttributeAsBoolean("isDaPostalizzare")){
									final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
									Record lRecordToLoad = new Record();
									lRecordToLoad.setAttribute("idUd", detailRecord.getAttributeAsInt("idUd"));
									lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												ProtocollazioneDetail  protocollazioneDetail = ProtocollazioneDetail.getInstance(detailRecord);
												protocollazioneDetail.setModalitaInvio("raccomandata");
												protocollazioneDetail.clickPostalizza(response.getData()[0]);
											}
										}
									});
								}else {
									Layout.addMessage(new MessageBean("L'unità documentaria risulta avere già una raccomandata spedita ad essa associata", "",	MessageType.ERROR));
								}
							}

						});
					}
				});
				altreOpMenu.addItem(inviaRaccomandataMenuItem);
			}
			
			/*** TODO: FEDERICO TROTTA GESTIRE ABILITAZIONE E IMMAGINE ***/
			if (Layout.isPrivilegioAttivo("PRT/U") && "U".equalsIgnoreCase(detailRecord.getAttribute("flgTipoProv")) && AurigaLayout.getParametroDB("CLIENTE").equalsIgnoreCase("ARPA_LAZ")) {

				MenuItem inviaPostaPrioritariaMenuItem = new MenuItem("Invio posta prioritaria", "postaElettronica/inoltro.png");
				inviaPostaPrioritariaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						
						ProtocollazioneUtil.isPossibleToPostel(detailRecord.getAttributeAsInt("idUd"), ETypePoste.POSTA_PRIORITARIA.value(), new ServiceCallback<Record>() {
							@Override
							public void execute(Record record) {
								if(record.getAttributeAsBoolean("isDaPostalizzare")){
									final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
									Record lRecordToLoad = new Record();
									lRecordToLoad.setAttribute("idUd", detailRecord.getAttributeAsInt("idUd"));
									lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												ProtocollazioneDetail  protocollazioneDetail = ProtocollazioneDetail.getInstance(detailRecord);
												protocollazioneDetail.setModalitaInvio("posta prioritaria");
												protocollazioneDetail.clickPostalizza(response.getData()[0]);
											}
										}
									});
								}else {
									Layout.addMessage(new MessageBean("L'unità documentaria risulta avere già una lettera spedita ad essa associata", "",	MessageType.ERROR));
								}
							}

						});
					}
				});
				altreOpMenu.addItem(inviaPostaPrioritariaMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilPresaInCarico")) {
				MenuItem prendiInCaricoMenuItem = new MenuItem("Prendi in carico", "archivio/prendiInCarico.png");
				prendiInCaricoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final RecordList listaUdFolder = new RecordList();
						listaUdFolder.add(listRecord);
						Record record = new Record();
						record.setAttribute("listaRecord", listaUdFolder);
						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PresaInCaricoDataSource");
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								operationCallback(response, listRecord, "idUdFolder", "Presa in carico effettuata con successo",
										"Si è verificato un errore durante la presa in carico!", null);
							}
						});
					}
				});
				altreOpMenu.addItem(prendiInCaricoMenuItem);
			}
			
			if (detailRecord.getAttributeAsBoolean("abilRispondi") && !("U".equals(listRecord.getAttribute("flgTipoProv")))) {
				MenuItem rispondiMenuItem = new MenuItem("Rispondi", "archivio/rispondi.png");
				rispondiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final Record record = new Record();
						record.setAttribute("idUd", listRecord.getAttribute("idUdFolder"));
						//TODO da mettere solo se ha come numerazione principale repertorio
						if(detailRecord.getAttribute("codCategoriaProtocollo") != null && "R".equals(detailRecord.getAttribute("codCategoriaProtocollo"))) {
							record.setAttribute("repertorio", detailRecord.getAttribute("siglaProtocollo"));
						}
						if("E".equals(listRecord.getAttribute("flgTipoProv"))) {
							record.setAttribute("tipoProt", "E");
							new DettaglioRispostaProtWindow(record);							
						}
						else if("I".equals(listRecord.getAttribute("flgTipoProv"))) {
							
							//controllo se il protocollo interno è abilitato alla risposta
							if (detailRecord.getAttributeAsBoolean("abilRispondiUscita")) {
								SC.ask("Sono presenti destinatari esterni nella risposta?", new BooleanCallback() {
	
									@Override
									public void execute(Boolean value) {
										if (value) {
											record.setAttribute("tipoProt", "E");
										}
										else {
											record.setAttribute("tipoProt", "I");
										}
										new DettaglioRispostaProtWindow(record);
									}
								});
							}else {
								record.setAttribute("tipoProt", "I");
								new DettaglioRispostaProtWindow(record);
							}
						}
					}
				});
				altreOpMenu.addItem(rispondiMenuItem);
			}
			
			if(isAbilInvioAlProtocollo(detailRecord)) {
				if(detailRecord != null && detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione") != null) {
					if(detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").getLength() == 1 ) {
						
						final String idUo = detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").get(0).getAttributeAsString("idUo");
						
						MenuItem invioAlProtocolloItem = new MenuItem("Invio al protocollo", "archivio/invio_al_protocollo.png");					
						final Menu scelteRapide = new Menu();
						MenuItem invioAlProtocolloRapidoItem = new MenuItem("Rapido");
						invioAlProtocolloRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {								
								clickInvioAlProtocolloRapido(listRecord, idUo, "U", null, null);
							}
						});
						MenuItem invioAlProtocolloConMessaggioItem = new MenuItem("Con messaggio");
						invioAlProtocolloConMessaggioItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {
								
								final DestUOProtocollazioneWindow destUOProtocollazioneWindow = new DestUOProtocollazioneWindow() {
									
									@Override
									public void onClickOkButton(Record record, DSCallback callback) {
										String messaggioInvio = record != null && record.getAttributeAsString("messaggioInvio") != null
												? record.getAttributeAsString("messaggioInvio") : null;
										clickInvioAlProtocolloConMessaggio(listRecord, idUo, "U", messaggioInvio, callback);
									};
								};
								destUOProtocollazioneWindow.show();
							}
						});
						
						scelteRapide.addItem(invioAlProtocolloRapidoItem);
						scelteRapide.addItem(invioAlProtocolloConMessaggioItem);
						invioAlProtocolloItem.setSubmenu(scelteRapide);
						altreOpMenu.addItem(invioAlProtocolloItem);	
						
					} else if(detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").getLength() > 1 ) {
						MenuItem invioAlProtocolloItem = new MenuItem("Invio al protocollo", "archivio/invio_al_protocollo.png");
						final Menu creaInvioAlProtocollo = new Menu();
						List<MenuItem> listaMenuItem = new ArrayList<MenuItem>();
						for(int i=0; i < detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").getLength(); i++) {
							Record currentRecord = detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").get(i);
							final String idUo = currentRecord.getAttributeAsString("idUo");
							MenuItem item = new MenuItem(currentRecord.getAttributeAsString("descrizione"));
							final Menu scelteRapide = new Menu();
							MenuItem invioAlProtocolloRapidoItem = new MenuItem("Rapido");
							invioAlProtocolloRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
								
								@Override
								public void onClick(MenuItemClickEvent event) {
									clickInvioAlProtocolloRapido(listRecord, idUo, "U", null, null);				
								}
							});
							
							MenuItem invioAlProtocolloConMessaggioItem = new MenuItem("Con messaggio");
							invioAlProtocolloConMessaggioItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
								@Override
								public void onClick(MenuItemClickEvent event) {
									
									final DestUOProtocollazioneWindow destUOProtocollazioneWindow = new DestUOProtocollazioneWindow() {
										
										@Override
										public void onClickOkButton(Record record, DSCallback callback) {
											String messaggioInvio = record != null && record.getAttributeAsString("messaggioInvio") != null
													? record.getAttributeAsString("messaggioInvio") : null;
											clickInvioAlProtocolloConMessaggio(listRecord, idUo, "U", messaggioInvio, callback);
										};
									};
									destUOProtocollazioneWindow.show();
								}
							});
							
							scelteRapide.addItem(invioAlProtocolloRapidoItem);
							scelteRapide.addItem(invioAlProtocolloConMessaggioItem);
							
							item.setSubmenu(scelteRapide);
							listaMenuItem.add(item);
						}
						
						for(MenuItem menuItem : listaMenuItem) {
							creaInvioAlProtocollo.addItem(menuItem);
						}
						
						invioAlProtocolloItem.setSubmenu(creaInvioAlProtocollo);
						altreOpMenu.addItem(invioAlProtocolloItem);	
					}
				}
			}

			if (detailRecord.getAttributeAsBoolean("abilAssegnazioneSmistamento")) {

				final Menu creaAssegna = new Menu(); 
				
				RecordList listaUOPreferiti = null;
				RecordList listaUtentiPreferiti = null;
				
				if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite")!=null)
					listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_DOC.getValue()));	
				
				if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti")!=null)
					listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.ASSEGNA_DOC.getValue()));	

				MenuItem assegnazioneMenuItem = new MenuItem("Assegna", "archivio/assegna.png");
				
				boolean noMenuRapido = true; // identifica la presenza o menu di valori da visualizzare nel menu rapido
				final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare
				
				boolean flgSoloUo = false;
				final String codSupportoOrig = detailRecord != null ? detailRecord.getAttributeAsString("codSupportoOrig") : null;
				if(codSupportoOrig != null) {
					if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
					   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
					   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
						flgSoloUo = true;
					}
				}
				
				if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
					listaPreferiti.addList(listaUOPreferiti.toArray());
					noMenuRapido = false;
				}
				
				if(!flgSoloUo) {
					if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
						listaPreferiti.addList(listaUtentiPreferiti.toArray());
						noMenuRapido = false;
					}
				}
				
				final RecordList listaAssPreselMitt = detailRecord != null ? detailRecord.getAttributeAsRecordList("listaAssPreselMitt") : null;
				
				// Assegnazione Standard 
				MenuItem assegnaMenuStandardItem = new MenuItem("Standard");
				assegnaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						
						String title = "Compila dati assegnazione del documento " + getEstremiUdFromList(listRecord);
						
						final AssegnazionePopup assegnazionePopup = new AssegnazionePopup("U", listRecord, title) {
							
							@Override
							public String getFlgTipoProvDoc() {
								return listRecord.getAttribute("flgTipoProv");																						
							}

							@Override
							public String getCodSupportoOrig() {
								return codSupportoOrig;
							}
							
							@Override
							public RecordList getListaPreferiti() {
								return listaPreferiti;
							}
							
							@Override
							public RecordList getListaAssegnatariMitt() {
								return listaAssPreselMitt;
							}
							
							@Override
							public void onClickOkButton(Record record, final DSCallback callback) {
								
								final RecordList listaUdFolder = new RecordList();
								listaUdFolder.add(listRecord);
								
								record.setAttribute("flgUdFolder", "U");
								record.setAttribute("listaRecord", listaUdFolder);
								
								Layout.showWaitPopup("Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
								try {
									lGwtRestDataSource.addData(record, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											operationCallback(response, listRecord, 
													"idUdFolder", 
													"Assegnazione effettuata con successo",
													"Si è verificato un errore durante l'assegnazione!", new DSCallback() {
														
														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {														
															if(callback != null){
																callback.execute(response, rawData, request);
															}
															Record lRecordToLoad = new Record();
															lRecordToLoad.setAttribute("idUd", listRecord.getAttribute("idUdFolder"));
															getProtocolloDataSource().getData(lRecordToLoad, new DSCallback() {

																@Override
																public void execute(DSResponse response, Object rawData, DSRequest request) {
																	if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
																		manageStampaEtichettaPostAssegnazione(listRecord, response.getData()[0]);
																	}
																}
															});
														}
													});
										}
									});
								} catch (Exception e) {
									Layout.hideWaitPopup();
								}
							}
						};
						assegnazionePopup.show();
					}
				});
				
				creaAssegna.addItem(assegnaMenuStandardItem);
				
				// Assegna Rapido 
				MenuItem assegnaMenuRapidoItem = new MenuItem("Rapido");
				
				Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
				if(success != null && success == true){
						
					Menu scelteRapide = new Menu();
					
					if(noMenuRapido){
						assegnaMenuRapidoItem.setEnabled(false);
					} else {
						buildMenuRapidoAssegnazione(listRecord, "U", listaPreferiti, scelteRapide);
						assegnaMenuRapidoItem.setSubmenu(scelteRapide);
					}
					
				} else {
					assegnaMenuRapidoItem.setEnabled(false);
				}
				creaAssegna.addItem(assegnaMenuRapidoItem);
				assegnazioneMenuItem.setSubmenu(creaAssegna);
				
				altreOpMenu.addItem(assegnazioneMenuItem);
			}

			// SMISTAMENTO
			if (detailRecord.getAttributeAsBoolean("abilSmista")) {
				final Menu creaSmista = new Menu(); 

				RecordList listaUOPreferiti = null;
				RecordList listaUtentiPreferiti = null;
				
				if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite")!=null)
					listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_DOC.getValue()));
				
				if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti")!=null)
					listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_DOC.getValue()));
				
				MenuItem smistamentoMenuItem = new MenuItem("Smista", "archivio/smistamento.png");
				
				boolean noMenuRapido = true; // identifica la presenza o menu di valori da visualizzare nel menu rapido
				final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare
				
				boolean flgSoloUo = false;
				final String codSupportoOrig = detailRecord != null ? detailRecord.getAttributeAsString("codSupportoOrig") : null;
				if(codSupportoOrig != null) {
					if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
					   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
					   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
						flgSoloUo = true;
					}
				}
				if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
					listaPreferiti.addList(listaUOPreferiti.toArray());
					noMenuRapido = false;
				}
				
				if(!flgSoloUo) {
					if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
						listaPreferiti.addList(listaUtentiPreferiti.toArray());
						noMenuRapido = false;
					}
				}
				
				// Smistamento Standard 
				MenuItem smistaMenuStandardItem = new MenuItem("Standard");
				smistaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						
						final SmistamentoPopup smistamentoPopup = new SmistamentoPopup("U", listRecord) {
							
							@Override
							public RecordList getListaPreferiti() {
								return listaPreferiti;
							}			
							
							@Override
							public void onClickOkButton(Record record, final DSCallback callback) {
								
								final RecordList listaUdFolder = new RecordList();
								listaUdFolder.add(listRecord);
								
								record.setAttribute("flgUdFolder", "U");
								record.setAttribute("listaRecord", listaUdFolder);
								record.setAttribute("motivoInvio", "#SMIST");
								
								Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
								try {
									lGwtRestDataSource.addData(record, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											operationCallback(response, listRecord, "idUdFolder", "Smistamento effettuato con successo",
													"Si è verificato un errore durante lo smistamento!", callback);
										}
									});
								} catch (Exception e) {
									Layout.hideWaitPopup();
								}
							}
						};
						smistamentoPopup.show();
					}
				});
				creaSmista.addItem(smistaMenuStandardItem);
				
				// Smista Rapido 
				MenuItem smistaMenuRapidoItem = new MenuItem("Rapido");
				
				Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
				if(success != null && success == true){					
					Menu scelteRapide = new Menu();			
					if(noMenuRapido){
						smistaMenuRapidoItem.setEnabled(false);
					} else {
						buildMenuRapidoSmistamento(listRecord, "U", listaPreferiti, scelteRapide);
						smistaMenuRapidoItem.setSubmenu(scelteRapide);
					}			
				} else {
					smistaMenuRapidoItem.setEnabled(false);
				}
				creaSmista.addItem(smistaMenuRapidoItem);
				smistamentoMenuItem.setSubmenu(creaSmista);
				
				altreOpMenu.addItem(smistamentoMenuItem);
			}
			
			// SMISTAMENTO CC
			if (detailRecord.getAttributeAsBoolean("abilSmistaCC")) {
				
				final Menu creaSmistaCC = new Menu(); 

				RecordList listaUOPreferiti = null;
				RecordList listaUtentiPreferiti = null;
				
				if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite")!=null)
					listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_DOC.getValue()));
				
				if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti")!=null)
					listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_DOC.getValue()));
				
				MenuItem smistamentoCCMenuItem = new MenuItem("Smista", "archivio/smistamentoCC.png");
				
				boolean noMenuRapido = true; // identifica la presenza o menu di valori da visualizzare nel menu rapido
				final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare
				
				boolean flgSoloUo = false;
				final String codSupportoOrig = detailRecord != null ? detailRecord.getAttributeAsString("codSupportoOrig") : null;
				if(codSupportoOrig != null) {
					if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
					   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
					   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
						flgSoloUo = true;
					}
				}
				if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
					listaPreferiti.addList(listaUOPreferiti.toArray());
					noMenuRapido = false;
				}
				
				if(!flgSoloUo) {
					if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
						listaPreferiti.addList(listaUtentiPreferiti.toArray());
						noMenuRapido = false;
					}
				}
				
				// SmistamentoCC Standard 
				MenuItem smistaCCMenuStandardItem = new MenuItem("Standard");
				smistaCCMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						
						String title = "Compila dati smistamento " + getEstremiUdFromList(listRecord);
						final CondivisionePopup condivisionePopup = new CondivisionePopup("U", listRecord, title) {
							
							@Override
							public boolean isSmistamentoCC() {
								return true;
							}
							
							@Override
							public RecordList getListaPreferiti() {
								return listaPreferiti;
							}
							
							@Override
							public void onClickOkButton(Record record, final DSCallback callback) {
								final RecordList listaUdFolder = new RecordList();
								listaUdFolder.add(listRecord);
								
								record.setAttribute("flgUdFolder", "U");
								record.setAttribute("motivoInvio", "#SMIST");
								record.setAttribute("listaRecord", listaUdFolder);
								
								Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
								try {
									lGwtRestDataSource.addData(record, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											operationCallback(response, listRecord, "idUdFolder", "Smistamento effettuato con successo",
													"Si è verificato un errore durante lo smistamento!", callback);
										}
									});
								} catch (Exception e) {
									Layout.hideWaitPopup();
								}
							}
						};
						condivisionePopup.show();
					}
				});
				creaSmistaCC.addItem(smistaCCMenuStandardItem);
				
				// SmistaCC Rapido 
				MenuItem smistaCCMenuRapidoItem = new MenuItem("Rapido");
				
				Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
				if(success != null && success == true){					
					Menu scelteRapide = new Menu();			
					if(noMenuRapido){
						smistaCCMenuRapidoItem.setEnabled(false);
					} else {
						buildMenuRapidoSmistamentoCC(listRecord, "U", listaPreferiti, scelteRapide);
						smistaCCMenuRapidoItem.setSubmenu(scelteRapide);
					}			
				} else {
					smistaCCMenuRapidoItem.setEnabled(false);
				}
				creaSmistaCC.addItem(smistaCCMenuRapidoItem);
				smistamentoCCMenuItem.setSubmenu(creaSmistaCC);
				
				altreOpMenu.addItem(smistamentoCCMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilCondivisione")) {
				final Menu creaCondivisione = new Menu(); 

				RecordList listaUOPreferiti = null;
				RecordList listaUtentiPreferiti = null;
				
				if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite")!=null)
					listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.INVIO_CC_DOC.getValue()));
				
				if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti")!=null)
					listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.INVIO_CC_DOC.getValue()));

				MenuItem condivisioneMenuItem = new MenuItem("Invia per conoscenza", "archivio/condividi.png");
				
				boolean noMenuRapido = true; // identifica la presenza o menu di valori da visualizzare nel menu rapido
				final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare
				
				if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
					listaPreferiti.addList(listaUOPreferiti.toArray());
					noMenuRapido = false;
				}
				
				if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
					listaPreferiti.addList(listaUtentiPreferiti.toArray());
					noMenuRapido = false;
				}
				
				// Invio per conoscenza Standard
				MenuItem condivisioneMenuStandardItem = new MenuItem("Standard");			
				condivisioneMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						
						String title = I18NUtil.getMessages().condivisioneWindow_title() + " del documento " + getEstremiUdFromList(listRecord);
						
						final CondivisionePopup condivisionePopup = new CondivisionePopup("U", listRecord, title) {

							@Override
							public String getFlgTipoProvDoc() {
								return listRecord.getAttribute("flgTipoProv");																						
							}
							
							@Override
							public RecordList getListaPreferiti() {
								return listaPreferiti;
							}
							
							@Override
							public void onClickOkButton(Record record, final DSCallback callback) {
								final RecordList listaUdFolder = new RecordList();
								listaUdFolder.add(listRecord);
								
								record.setAttribute("flgUdFolder", "U");
								record.setAttribute("listaRecord", listaUdFolder);
								
								Layout.showWaitPopup("Invio per conoscenza in corso: potrebbe richiedere qualche secondo. Attendere…");
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
								try {
									lGwtRestDataSource.addData(record, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											operationCallback(response, listRecord, "idUdFolder", "Invio per conoscenza effettuato con successo",
													"Si è verificato un errore durante l'invio per conoscenza!", callback);
										}
									});
								} catch (Exception e) {
									Layout.hideWaitPopup();
								}
							}
						};
						condivisionePopup.show();
					}
				});
				creaCondivisione.addItem(condivisioneMenuStandardItem);
				
				// Invio per conoscenza Rapida
				MenuItem condivisioneMenuRapidoItem = new MenuItem("Rapida");
				
				Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
				
				if(success != null && success == true) {
					
					Menu scelteRapide = new Menu();					
					
					if(noMenuRapido){
						condivisioneMenuRapidoItem.setEnabled(false);
					} else {
						buildMenuRapidoCondivisione(listRecord, "U", listaPreferiti, scelteRapide);
						condivisioneMenuRapidoItem.setSubmenu(scelteRapide);
					}
					
				} else {
					condivisioneMenuRapidoItem.setEnabled(false);
				}
				creaCondivisione.addItem(condivisioneMenuRapidoItem);
				
				condivisioneMenuItem.setSubmenu(creaCondivisione);
				altreOpMenu.addItem(condivisioneMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilClassificazioneFascicolazione")) {
				if (!AurigaLayout.isAttivoClassifSenzaFasc(detailRecord.getAttribute("flgTipoProv"))
						|| (fromScrivania && ((ScrivaniaLayout) layout).getIdNode() != null && "D.10".equals(((ScrivaniaLayout) layout).getIdNode()))) {
					MenuItem fascicolaMenuItem = new MenuItem("Fascicola", "archivio/fascicola.png");
					fascicolaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							new ClassificazioneFascicolazionePopup(true, listRecord, detailRecord) {

								@Override
								public void onClickOkButton(final DSCallback callback) {
									final RecordList listaUd = new RecordList();
									listaUd.add(listRecord);
									Record record = new Record();
									record.setAttribute("listaRecord", listaUd);
									record.setAttribute("listaClassFasc", _form.getValueAsRecordList("listaClassFasc"));
									record.setAttribute("livelloRiservatezza", _form.getValue("livelloRiservatezza"));
									GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ClassificazioneFascicolazioneDataSource");
									lGwtRestDataSource.addData(record, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											operationCallback(response, listRecord, "idUdFolder", "Fascicolazione effettuata con successo",
													"Si è verificato un errore durante la fascicolazione!", callback);
										}
									});
								}
							};
						}
					});
					altreOpMenu.addItem(fascicolaMenuItem);
				} else {
					MenuItem classificaFascicolaMenuItem = new MenuItem("Classifica/fascicola", "archivio/classfasc.png");
					classificaFascicolaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							new ClassificazioneFascicolazionePopup(false, 
									listRecord, detailRecord) {

								@Override
								public void onClickOkButton(final DSCallback callback) {
									final RecordList listaUd = new RecordList();
									listaUd.add(listRecord);
									Record record = new Record();
									record.setAttribute("listaRecord", listaUd);
									record.setAttribute("listaClassFasc", _form.getValueAsRecordList("listaClassFasc"));
									record.setAttribute("livelloRiservatezza", _form.getValue("livelloRiservatezza"));
									GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ClassificazioneFascicolazioneDataSource");
									lGwtRestDataSource.addData(record, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											operationCallback(response, listRecord, "idUdFolder", "Classificazione/fascicolazione effettuata con successo",
													"Si è verificato un errore durante la classificazione/fascicolazione!", callback);
										}
									});
								}
							};
						}
					});
					altreOpMenu.addItem(classificaFascicolaMenuItem);
				}
			}
			
			if (detailRecord.getAttributeAsBoolean("abilOrganizza")) {
				MenuItem organizzaMenuItem = new MenuItem("Organizza", "archivio/organizza.png");
				organizzaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						new OrganizzaPopup(listRecord) {

							@Override
							public void onClickOkButton(final DSCallback callback) {
								final RecordList listaUd = new RecordList();
								listaUd.add(listRecord);
								Record record = new Record();
								record.setAttribute("listaRecord", listaUd);
								record.setAttribute("listaFolderCustom", _form.getValueAsRecordList("listaFolderCustom"));
								record.setAttribute("livelloRiservatezza", _form.getValue("livelloRiservatezza"));
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OrganizzaDataSource");
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										operationCallback(response, listRecord, "idUdFolder", "Organizzazione nella cartella/e effettuata con successo",
												"Si è verificato un errore durante l'organizzazione nella cartella/e!", callback);
									}
								});
							}
						};
					}
				});
				altreOpMenu.addItem(organizzaMenuItem);
			}

			if (detailRecord.getAttribute("flgTipoProv") != null && !"".equals(detailRecord.getAttribute("flgTipoProv"))) {
				if (detailRecord.getAttributeAsBoolean("abilModificaDatiExtraIter") || detailRecord.getAttributeAsBoolean("abilAggiuntaFile") || detailRecord.getAttributeAsBoolean("abilModificaDati")) {
					MenuItem modificaMenuItem = new MenuItem("Modifica", "buttons/modify.png");
					modificaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							if (detailRecord.getAttributeAsBoolean("abilModificaDati")) {
								if (detailRecord.getAttributeAsBoolean("abilAggiuntaFile")) {
									manageModificaDatiConAggiuntaFileButtonClick(listRecord);
									listRecord.setAttribute("rowClickEventSource", "modificaDatiConAggiuntaFileButton");
								} else {
									manageModificaDatiButtonClick(listRecord);
								}
							} else if (detailRecord.getAttributeAsBoolean("abilAggiuntaFile")) {
								manageAggiuntaFileButtonClick(listRecord);
							} else if (detailRecord.getAttributeAsBoolean("abilModificaDatiExtraIter")) {
								manageModificaDatiExtraIterButtonClick(listRecord);
							}
						}
					});
					altreOpMenu.addItem(modificaMenuItem);
					
				}
				if (detailRecord.getAttributeAsBoolean("abilModificaDatiRegistrazione")) {
					MenuItem modificaDatiRegMenuItem = new MenuItem("Variazione dati registrazione", "buttons/modificaDatiReg.png");
					modificaDatiRegMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							manageModificaDatiRegButtonClick(listRecord);
						}
					});
					altreOpMenu.addItem(modificaDatiRegMenuItem);
				}
			} else {
				if (detailRecord.getAttributeAsBoolean("abilModificaDati")) {
					MenuItem modificaMenuItem = new MenuItem("Modifica", "buttons/modify.png");
					modificaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							manageModifyButtonClick(listRecord);
							layout.setReloadListCallback(mReloadListCallback);
						}
					});
					altreOpMenu.addItem(modificaMenuItem);
				}
			}
			
			if(detailRecord.getAttributeAsBoolean("abilModificaTipologia")) {
				MenuItem modificaTipologiaMenuItem = new MenuItem("Modifica/Assegna tipologia", "archivio/modificaTipologia.png");
				modificaTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickModificaTipologia(listRecord,detailRecord);
					}
				});
				altreOpMenu.addItem(modificaTipologiaMenuItem);
			}
			
			if (detailRecord.getAttributeAsBoolean("abilModificaCodAttoCMTO")) {
				MenuItem modificaCodAttoCMTOMenuItem = new MenuItem("Modifica cod. atto", "pratiche/task/buttons/modificaCodAtto.png");
				modificaCodAttoCMTOMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
						Record lRecordToLoad = new Record();
						lRecordToLoad.setAttribute("idUd", listRecord.getAttribute("idUdFolder"));
						lNuovaPropostaAtto2CompletaDataSource.getData(lRecordToLoad, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {		
									Record lRecordAtto = response.getData()[0];
									Record lRecord = new Record();
									lRecord.setAttribute("idDocPrimario", lRecordAtto.getAttributeAsString("idDocPrimario"));
									lRecord.setAttribute("tipoDocumento", lRecordAtto.getAttributeAsString("tipoDocumento"));
									lRecord.setAttribute("tipoAffidamento", lRecordAtto.getAttributeAsString("tipoAffidamento"));
									lRecord.setAttribute("materiaTipoAtto", lRecordAtto.getAttributeAsString("materiaTipoAtto"));
									lRecord.setAttribute("desMateriaTipoAtto", lRecordAtto.getAttributeAsString("desMateriaTipoAtto"));
									lRecord.setAttribute("flgLLPP", lRecordAtto.getAttributeAsString("flgLLPP"));
									lRecord.setAttribute("annoProgettoLLPP", lRecordAtto.getAttributeAsString("annoProgettoLLPP"));
									lRecord.setAttribute("numProgettoLLPP", lRecordAtto.getAttributeAsString("numProgettoLLPP"));
									lRecord.setAttribute("flgBeniServizi", lRecordAtto.getAttributeAsString("flgBeniServizi"));
									lRecord.setAttribute("annoProgettoBeniServizi", lRecordAtto.getAttributeAsString("annoProgettoBeniServizi"));
									lRecord.setAttribute("numProgettoBeniServizi", lRecordAtto.getAttributeAsString("numProgettoBeniServizi"));
									new ModificaCodAttoCMTOPopup(lRecord, new ServiceCallback<Record>() {
										
										@Override
										public void execute(Record record) {
											layout.reloadListAndSetCurrentRecord(listRecord);							
										}
									});
								}
							}
						});						
					}
				});
				altreOpMenu.addItem(modificaCodAttoCMTOMenuItem);	
			}

			if (detailRecord.getAttributeAsBoolean("abilInvioPEC")) {
				MenuItem invioPECMenuItem = new MenuItem(I18NUtil.getMessages().invioudmail_detail_mailinterop_title(),
						"anagrafiche/soggetti/flgEmailPecPeo/INTEROP.png");
				invioPECMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						
						final Boolean flgInvioPECMulti = detailRecord.getAttributeAsBoolean("flgInvioPECMulti") != null &&
								detailRecord.getAttributeAsBoolean("flgInvioPECMulti") ? true : false;
						GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaInvioUDMailDatasource");
						if(flgInvioPECMulti) {
							lGwtRestService.extraparam.put("PEC_MULTI", "1");
						} 
						lGwtRestService.extraparam.put("tipoMail", "PEC");
						lGwtRestService.call(detailRecord, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								
								if(flgInvioPECMulti) {
									object.setAttribute("tipoMail", "PEO");
									InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEO", new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											mReloadListCallback.executeCallback();
										}
									});
									lInvioUdMailWindow.loadMail(object);
									lInvioUdMailWindow.show();
									
								} else {
								
									InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEC", new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											mReloadListCallback.executeCallback();
										}
									});
									lInvioUdMailWindow.loadMail(object);
									lInvioUdMailWindow.show();
								}
							}
						});
		
					}
				});
				altreOpMenu.addItem(invioPECMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilInvioPEO")) {
				MenuItem invioPEOMenuItem = new MenuItem(I18NUtil.getMessages().invioudmail_detail_mailpeo_title(),
						"anagrafiche/soggetti/flgEmailPecPeo/PEO.png");
				invioPEOMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaInvioUDMailDatasource");
						lGwtRestService.extraparam.put("tipoMail", "PEO");
						lGwtRestService.call(detailRecord, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEO", new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										mReloadListCallback.executeCallback();
									}
								});
								lInvioUdMailWindow.loadMail(object);
								lInvioUdMailWindow.show();
							}
						});
					}
				});
				altreOpMenu.addItem(invioPEOMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilModAssInviiCC")) {
				MenuItem inviiEffettuatiMenuItem = new MenuItem("Modifica/annulla ass./invii cc", "archivio/inviiEffettuati.png");
				inviiEffettuatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("InviiEffettuatiDataSource", "idInvioNotifica", FieldType.TEXT);
						lGwtRestDataSource.addParam("flgUdFolder", listRecord.getAttribute("flgUdFolder"));
						lGwtRestDataSource.addParam("idUdFolder", listRecord.getAttribute("idUdFolder"));
						lGwtRestDataSource.fetchData(null, new DSCallback() {
	
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									RecordList data = response.getDataAsRecordList();
									if (data.getLength() == 0) {
										Layout.addMessage(new MessageBean("Non risultano invii effettuati da te o dalla/e UO cui sei associato", "",
												MessageType.INFO));
									} else if (data.getLength() > 0) {
										
										Record lRecordToLoad = new Record();
										lRecordToLoad.setAttribute("idUd", listRecord.getAttribute("idUdFolder"));
										getProtocolloDataSource().getData(lRecordToLoad, new DSCallback() {
	
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
													
													Record detailRecord = response.getData()[0];	
													
													String codSupportoOrig = detailRecord != null && detailRecord.getAttributeAsString("codSupportoOrig") != null ? 
															detailRecord.getAttributeAsString("codSupportoOrig") : null;
													String segnaturaXOrd = listRecord != null && listRecord.getAttributeAsString("segnaturaXOrd") != null ? 
															listRecord.getAttributeAsString("segnaturaXOrd") : null;
													
													Record recordToPrint = new Record();
													recordToPrint.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
													recordToPrint.setAttribute("flgUdFolder", "U");
													recordToPrint.setAttribute("estremiUdFolder", getEstremiUdFromList(listRecord));														
													recordToPrint.setAttribute("codSupportoOrig", codSupportoOrig);
													recordToPrint.setAttribute("segnaturaXOrd",   segnaturaXOrd);
													recordToPrint.setAttribute("listaAllegati",   detailRecord.getAttributeAsRecordList("listaAllegati"));
													recordToPrint.setAttribute("idDoc", detailRecord.getAttribute("idDocPrimario"));									
													
													String title = "Invii effettutati sul documento " + getEstremiUdFromList(listRecord);
													 
													InviiEffettuatiWindow inviiEffettuatiPopup = new InviiEffettuatiWindow(lGwtRestDataSource, recordToPrint, title) {
	
														@Override
														public void manageOnCloseClick() {
															super.manageOnCloseClick();
															layout.doSearch();
														}
													};
													inviiEffettuatiPopup.show();
												}
											}
										});
									}
								}
							}
						});
					}
				});
				altreOpMenu.addItem(inviiEffettuatiMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilRestituzione")) {
				MenuItem restituzioneMenuItem = new MenuItem("Restituisci", "archivio/restituzione.png");
				restituzioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						RestituzionePopup restituzionePopup = new RestituzionePopup() {

							@Override
							public void onClickOkButton(final DSCallback callback) {
								
								final RecordList listaUdFolder = new RecordList();
								listaUdFolder.add(listRecord);
								final Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								record.setAttribute("flgIgnoreWarning", _form.getValue("flgIgnoreWarning"));
								record.setAttribute("messaggio", _form.getValue("messaggio"));
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("RestituzioneDataSource");
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										operationCallbackRestituisci(response, listRecord, _form, new DSCallback() {
											
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {														
												if(callback != null){
													callback.execute(response, rawData, request);
												}
												if(listRecord.getAttribute("flgUdFolder") != null &&
														"U".equalsIgnoreCase(listRecord.getAttribute("flgUdFolder"))){
												Record lRecordToLoad = new Record();
												lRecordToLoad.setAttribute("idUd", listRecord.getAttribute("idUdFolder"));
												getProtocolloDataSource().getData(lRecordToLoad, new DSCallback() {

													@Override
													public void execute(DSResponse response, Object rawData, DSRequest request) {
														if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
															manageStampaEtichettaPostAssegnazione(listRecord, response.getData()[0]);
														}
													}
												});
											}
											}
										});
									}
								});
							}
						};
						restituzionePopup.show();
					}
				});
				altreOpMenu.addItem(restituzioneMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilInvioConferma")) {
				MenuItem invioConfermaMenuItem = creaInvioNotificaInteropMenuItem(detailRecord, "conferma");
				altreOpMenu.addItem(invioConfermaMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilInvioAggiornamento")) {
				MenuItem invioAggiornamentoMenuItem = creaInvioNotificaInteropMenuItem(detailRecord, "aggiornamento");
				altreOpMenu.addItem(invioAggiornamentoMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilInvioAnnullamento")) {
				MenuItem invioAnnullamentoMenuItem = creaInvioNotificaInteropMenuItem(detailRecord, "annullamento");
				altreOpMenu.addItem(invioAnnullamentoMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilRichAnnullamentoReg")) {
				MenuItem richAnnullamentoRegItem = new MenuItem("Richiedi annullamento registrazione", "protocollazione/richAnnullamento.png");
				richAnnullamentoRegItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						Record lRecord = new Record();
						lRecord.setAttribute("codCategoria", detailRecord.getAttribute("codCategoriaProtocollo"));
						lRecord.setAttribute("sigla", detailRecord.getAttribute("siglaProtocollo"));
						lRecord.setAttribute("anno", detailRecord.getAttribute("annoProtocollo"));
						lRecord.setAttribute("nro", detailRecord.getAttribute("nroProtocollo"));
						new RichAnnullamentoRegPopup(listRecord.getAttribute("segnatura"), lRecord) {

							@Override
							public void onClickOkButton(final DSCallback callback) {
								
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaRegistrazioneDocDataSource");
								lGwtRestDataSource.performCustomOperation("richAnnullamentoReg", new Record(_form.getValues()), new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										Record data = response.getData()[0];
										String errorMessage = data.getAttribute("errorMessage");
										if (errorMessage != null) {
											Layout.addMessage(new MessageBean(errorMessage, "", MessageType.ERROR));
										} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Layout.addMessage(new MessageBean("La richiesta di annullamento della registrazione è stata acquisita", "",
													MessageType.INFO));
											layout.reloadListAndSetCurrentRecord(listRecord);
											if (callback != null) {
												callback.execute(new DSResponse(), null, new DSRequest());
											}
										}
									}
								}, new DSRequest());
							}
						};
					}
				});
				altreOpMenu.addItem(richAnnullamentoRegItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilModificaRichAnnullamentoReg")) {
				MenuItem modificaRichAnnullamentoRegItem = new MenuItem("Modifica richiesta annullamento registrazione",
						"protocollazione/modificaRichAnnullamento.png");
				modificaRichAnnullamentoRegItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						Record lRecord = new Record();
						lRecord.setAttribute("codCategoria", detailRecord.getAttribute("codCategoriaProtocollo"));
						lRecord.setAttribute("sigla", detailRecord.getAttribute("siglaProtocollo"));
						lRecord.setAttribute("anno", detailRecord.getAttribute("annoProtocollo"));
						lRecord.setAttribute("nro", detailRecord.getAttribute("nroProtocollo"));
						lRecord.setAttribute("motiviRichAnnullamento", detailRecord.getAttribute("motiviRichAnnullamento"));
						new RichAnnullamentoRegPopup(listRecord.getAttribute("segnatura"), lRecord) {

							@Override
							public void onClickOkButton(final DSCallback callback) {
								
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaRegistrazioneDocDataSource");
								lGwtRestDataSource.performCustomOperation("modificaRichAnnullamentoReg", new Record(_form.getValues()), new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										Record data = response.getData()[0];
										String errorMessage = data.getAttribute("errorMessage");
										if (errorMessage != null) {
											Layout.addMessage(new MessageBean(errorMessage, "", MessageType.ERROR));
										} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Layout.addMessage(new MessageBean(
													"La richiesta di annullamento della registrazione è stata modificata con successo", "", MessageType.INFO));
											layout.reloadListAndSetCurrentRecord(listRecord);
											if (callback != null) {
												callback.execute(new DSResponse(), null, new DSRequest());
											}
										}
									}
								}, new DSRequest());
							}
						};
					}
				});
				altreOpMenu.addItem(modificaRichAnnullamentoRegItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilEliminaRichAnnullamentoReg")) {
				MenuItem eliminaRichAnnullamentoRegItem = new MenuItem("Elimina richiesta annullamento registrazione",
						"protocollazione/eliminaRichAnnullamento.png");
				eliminaRichAnnullamentoRegItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						SC.ask("Sei sicuro di voler eliminare la richiesta di annullamento della registrazione?", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								
								if (value) {
									GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaRegistrazioneDocDataSource");
									Record lRecord = new Record();
									lRecord.setAttribute("codCategoria", detailRecord.getAttribute("codCategoriaProtocollo"));
									lRecord.setAttribute("sigla", detailRecord.getAttribute("siglaProtocollo"));
									lRecord.setAttribute("anno", detailRecord.getAttribute("annoProtocollo"));
									lRecord.setAttribute("nro", detailRecord.getAttribute("nroProtocollo"));
									lGwtRestDataSource.performCustomOperation("eliminaRichAnnullamentoReg", lRecord, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											Record data = response.getData()[0];
											String errorMessage = data.getAttribute("errorMessage");
											if (errorMessage != null) {
												Layout.addMessage(new MessageBean(errorMessage, "", MessageType.ERROR));
											} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												Layout.addMessage(new MessageBean("La richiesta di annullamento della registrazione è stata eliminata", "",
														MessageType.INFO));
												layout.reloadListAndSetCurrentRecord(listRecord);
											}
										}
									}, new DSRequest());
								}
							}
						});
					}
				});
				altreOpMenu.addItem(eliminaRichAnnullamentoRegItem);
			}
			// Vai iter
			if (detailRecord.getAttributeAsString("idProcess") != null && !detailRecord.getAttributeAsString("idProcess").equals("")) {
				MenuItem vaiIterMenuItem = new MenuItem("Vai all’iter/processo collegato", "buttons/gear.png");
				vaiIterMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						apriDettaglioProcesso(detailRecord.getAttributeAsString("idProcess") , 
								              detailRecord.getAttributeAsString("ruoloSmistamento"),
								              detailRecord.getAttributeAsString("idUd"),
								              detailRecord.getAttributeAsString("estremiProcess"),
								              true
								              );						
					}
				});
				altreOpMenu.addItem(vaiIterMenuItem);
			}

			if (detailRecord.getAttributeAsBoolean("abilAnnullamentoReg")) {
				MenuItem annullamentoRegItem = new MenuItem("Annulla registrazione", "protocollazione/annullata.png");
				annullamentoRegItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						SC.ask("L'annullamento è stato autorizzato. Sei sicuro di voler annullare la registrazione?", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaRegistrazioneDocDataSource");
									Record lRecord = new Record();
									lRecord.setAttribute("codCategoria", detailRecord.getAttribute("codCategoriaProtocollo"));
									lRecord.setAttribute("sigla", detailRecord.getAttribute("siglaProtocollo"));
									lRecord.setAttribute("anno", detailRecord.getAttribute("annoProtocollo"));
									lRecord.setAttribute("nro", detailRecord.getAttribute("nroProtocollo"));
									lGwtRestDataSource.performCustomOperation("annullamentoReg", lRecord, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											Record data = response.getData()[0];
											String errorMessage = data.getAttribute("errorMessage");
											if (errorMessage != null) {
												Layout.addMessage(new MessageBean(errorMessage, "", MessageType.ERROR));
											} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												Layout.addMessage(new MessageBean("Registrazione annullata", "", MessageType.INFO));
												String invioAutoNotInteropAnn = AurigaLayout.getParametroDB("INVIO_AUTO_NOT_INTEROP_ANN");
												Boolean emailArrivoInteroperabile = detailRecord.getAttributeAsBoolean("emailArrivoInteroperabile") != null
														&& detailRecord.getAttributeAsBoolean("emailArrivoInteroperabile");
												if (emailArrivoInteroperabile && (invioAutoNotInteropAnn != null && invioAutoNotInteropAnn.equals("true"))) {
													final GWTRestService<Record, Record> lGwtRestServiceInvioNotifica = new GWTRestService<Record, Record>(
															"AurigaInvioNotificaInteropUdDatasource");
													lGwtRestServiceInvioNotifica.extraparam.put("tipoNotifica", "annullamento");
													lGwtRestServiceInvioNotifica.call(detailRecord, new ServiceCallback<Record>() {

														@Override
														public void execute(Record object) {
															if (object != null) {
																Layout.showWaitPopup("Invio automatico notifica di annullamento in corso: potrebbe richiedere qualche secondo. Attendere…");
																try {
																	lGwtRestServiceInvioNotifica.executecustom("invioMail", object, new DSCallback() {

																		@Override
																		public void execute(DSResponse response, Object rawData, DSRequest request) {
																			Layout.hideWaitPopup();
																			if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
																				Layout.addMessage(new MessageBean(
																						"Invio automatico notifica di annullamento effettuato con successo",
																						"", MessageType.INFO));
																			} else {
																				Layout.addMessage(new MessageBean(
																						"Si è verificato un errore durante l'invio automatico della notifica di annullamento",
																						"", MessageType.ERROR));
																			}
																		}
																	});
																} catch (Exception e) {
																	Layout.hideWaitPopup();
																	Layout.addMessage(new MessageBean(
																			"Si è verificato un errore durante l'invio automatico della notifica di annullamento",
																			"", MessageType.ERROR));
																}
															}
														}
													});
												}
												layout.reloadListAndSetCurrentRecord(listRecord);
											}
										}
									}, new DSRequest());
								}
							}
						});
					}
				});
				altreOpMenu.addItem(annullamentoRegItem);
			}
//			if (detailRecord.getAttributeAsBoolean("abilArchiviazione")) {
//				MenuItem archiviazioneMenuItem = new MenuItem(AurigaLayout.getParametroDB("LABEL_SET_INTERESSE_CESSATO_UD"), "archivio/archiviazione.png");
//				archiviazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
//
//					@Override
//					public void onClick(MenuItemClickEvent event) {
//						final RecordList listaUdFolder = new RecordList();
//						listaUdFolder.add(listRecord);
//						Record record = new Record();
//						record.setAttribute("listaRecord", listaUdFolder);
//						record.setAttribute("flgInteresseCessato", "1");
//						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchiviazioneDataSource");
//						lGwtRestDataSource.addData(record, new DSCallback() {
//
//							@Override
//							public void execute(DSResponse response, Object rawData, DSRequest request) {
//								
//								if (layout instanceof ScrivaniaLayout)
//									((ScrivaniaLayout) layout).massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
//											"Archiviazione effettuata con successo", "Tutti i record selezionati per l'archiviazione sono andati in errore!",
//											"Alcuni dei record selezionati per l'archiviazione sono andati in errore!", null);
//								else if (layout instanceof ArchivioLayout)
//									((ArchivioLayout) layout).massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
//											"Archiviazione effettuata con successo", "Tutti i record selezionati per l'archiviazione sono andati in errore!",
//											"Alcuni dei record selezionati per l'archiviazione sono andati in errore!", null);
//							}
//						});
//					}
//				});
//				altreOpMenu.addItem(archiviazioneMenuItem);
//			}
			if (layout instanceof ScrivaniaLayout) {
				MenuItem spostaInEliminatiMenuItem = new MenuItem("Sposta in Eliminati", "buttons/cestino.png");
				spostaInEliminatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final EliminazionePopup eliminazionePopup = new EliminazionePopup() {

							@Override
							public void onClickOkButton(final DSCallback callback) {
								final RecordList listaUdFolder = new RecordList();
								Record rec = new Record();
								rec.setAttribute("flgUdFolder", listRecord.getAttribute("flgUdFolder"));
								rec.setAttribute("idUdFolder", listRecord.getAttribute("idUdFolder"));
								listaUdFolder.add(rec);
								Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								record.setAttribute("motivo", _form.getValue("motivo"));
								record.setAttribute("sezioneAreaLav", ((ScrivaniaLayout) layout).getCodSezione());
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("EliminazioneDaAreaLavoroDataSource");
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (idFolder != null && "-99999".equals(idFolder)) {
											((ScrivaniaLayout) layout).massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
													"Eliminazione dalla Scrivania effettuata con successo",
													"Tutti i record selezionati per l'eliminazione dalla Scrivania sono andati in errore!",
													"Alcuni dei record selezionati per l'eliminazione dalla Scrivania sono andati in errore!", callback);
										} else {
											((ScrivaniaLayout) layout).massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
													"Spostamento in Eliminati effettuato con successo",
													"Tutti i record selezionati per lo spostamento in Eliminati sono andati in errore!",
													"Alcuni dei record selezionati per lo spostamento in Eliminati sono andati in errore!", callback);
										}
									}
								});
							}
						};
						eliminazionePopup.show();
					}
				});
				if(showSpostaEliminatiButton()){
					altreOpMenu.addItem(spostaInEliminatiMenuItem);
				}
				
				// Segna come archiviato 
				if (detailRecord.getAttributeAsBoolean("abilArchiviazione")) {
					MenuItem segnaComeArchiviatoMenuItem = new MenuItem(I18NUtil.getMessages().segnaComeArchiviato_menu_apri_title(), "archivio/archiviazione.png");
					segnaComeArchiviatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						public void onClick(MenuItemClickEvent event) {
							segnaComeArchiviato(listRecord);
						}
					});
					altreOpMenu.addItem(segnaComeArchiviatoMenuItem);
				}
			}
			if (detailRecord.getAttributeAsBoolean("abilModificaDati")) {
				MenuItem apposizioneCommentiMenuItem = new MenuItem(I18NUtil.getMessages().apposizioneCommenti_menu_apri_title(), "archivio/note_commenti.png");
				apposizioneCommentiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					public void onClick(MenuItemClickEvent event) {
						manageApposizioneCommenti(listRecord);
					}
				});
				altreOpMenu.addItem(apposizioneCommentiMenuItem);
			}
			if (detailRecord.getAttributeAsBoolean("abilOsservazioniNotifiche")) {
				MenuItem aggiungiOsservazioneNotifica = new MenuItem(I18NUtil.getMessages().osservazioniNotifiche_menu_apri_title(), "osservazioni_notifiche.png");
				aggiungiOsservazioneNotifica.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					public void onClick(MenuItemClickEvent event) {
						manageOsservazioniNotifiche(listRecord);
					}
				});
				altreOpMenu.addItem(aggiungiOsservazioneNotifica);
			}
			
			if (detailRecord.getAttributeAsBoolean("abilFirma")) {
				MenuItem ApposizioneFirmaMenuItem = new MenuItem(I18NUtil.getMessages().apposizioneFirma_button_title(), "file/mini_sign.png");
				ApposizioneFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					public void onClick(MenuItemClickEvent event) {
						manageFirma(listRecord, true);
					}
				});
				altreOpMenu.addItem(ApposizioneFirmaMenuItem);
			}
			
			if (detailRecord.getAttributeAsBoolean("abilFirmaProtocolla")) {
				MenuItem apposizioneFirmaEProtocollaMenuItem = new MenuItem(I18NUtil.getMessages().apposizioneFirmaProtocollazione_button_title(),"buttons/firmaEProtocolla.png");
				apposizioneFirmaEProtocollaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					public void onClick(MenuItemClickEvent event) {
						manageProtocollaTimbraEFirma(listRecord, true);
					}
				});
				altreOpMenu.addItem(apposizioneFirmaEProtocollaMenuItem);
			}
			
			if (detailRecord.getAttributeAsBoolean("abilFirma")) {
				MenuItem RifiutoApposizioneFirmaMenuItem = new MenuItem(I18NUtil.getMessages().rifiutoApposizioneFirma_button_title(), "file/rifiuto_apposizione.png");
				RifiutoApposizioneFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					public void onClick(MenuItemClickEvent event) {
						manageFirma(listRecord, false);
					}
				});
				altreOpMenu.addItem(RifiutoApposizioneFirmaMenuItem);
			}
			
			//Inserimento del pulsante per l'apposizione del visto elettronico
			if (detailRecord.getAttributeAsBoolean("abilVistoElettronico")) {
				MenuItem apposizioneVistoMenuItem = new MenuItem(I18NUtil.getMessages().apposizioneVisto_button_title(), "ok.png");
				apposizioneVistoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					public void onClick(MenuItemClickEvent event) {
						final RecordList listaUd = new RecordList();
						listaUd.add(listRecord);
						manageApponiVisto(listaUd, true);
					}
				});
				altreOpMenu.addItem(apposizioneVistoMenuItem);
				
				MenuItem rifiutoVistoMenuItem = new MenuItem(I18NUtil.getMessages().rifiutoApposizioneVisto_button_title(), "ko.png");
				rifiutoVistoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					public void onClick(MenuItemClickEvent event) {
						final RecordList listaUd = new RecordList();
						listaUd.add(listRecord);
						manageApponiVisto(listaUd, false);
					}
				});
				altreOpMenu.addItem(rifiutoVistoMenuItem);
			}
			
			// TAGLIA
			MenuItem tagliaMenuItem = getTagliaMenuItem(detailRecord);
			//altreOpMenu.addItem(tagliaMenuItem);
			// INCOLLA
			MenuItem incollaMenuItem = getIncollaMenuItem(detailRecord);
			//altreOpMenu.addItem(incollaMenuItem);
			
			// STAMPA ETICHETTA
			if (detailRecord.getAttributeAsBoolean("abilStampaEtichetta")) {
				MenuItem stampaEtichettaMenuItem = new MenuItem(
						AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_CARTACEO") ? "Timbra" : "Stampa etichetta",
						"protocollazione/stampaEtichetta.png");
				stampaEtichettaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					@Override
					public void onClick(MenuItemClickEvent event) {
						manageStampaEtichettaClick(detailRecord);
					}
				});				
				altreOpMenu.addItem(stampaEtichettaMenuItem);
			}	
			
			// Segna come visionato
			if (detailRecord.getAttributeAsBoolean("abilSetVisionato")) {
				MenuItem segnaComeVisionatoMenuItem = new MenuItem(I18NUtil.getMessages().segnaComeVisionato_menu_apri_title(), "postaElettronica/flgRicevutaLettura.png");
				segnaComeVisionatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					public void onClick(MenuItemClickEvent event) {
						segnaComeVisionato(listRecord);
					}
				});
				altreOpMenu.addItem(segnaComeVisionatoMenuItem);
			}
					
			// Modifica Dati Extra Iter
			if (detailRecord.getAttributeAsBoolean("abilModificaDatiExtraIter")) {
				MenuItem modificaDatiExtraIterMenuItem = new MenuItem(I18NUtil.getMessages().modificaDatiExtraIter_menu_apri_title(), "pratiche/task/buttons/modifica_dati_extra_iter.png");
				modificaDatiExtraIterMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						manageModificaDatiExtraIterButtonClick(listRecord);
					}

				});
				altreOpMenu.addItem(modificaDatiExtraIterMenuItem);
			}
			
			// Modifica Opere Atto
			if (detailRecord.getAttributeAsBoolean("abilModificaOpereAtto")) {
				MenuItem modificaOpereAttoMenuItem = new MenuItem(I18NUtil.getMessages().modificaOpereAtto_menu_apri_title(), "pratiche/task/buttons/modifica_opere_atto.png");
				modificaOpereAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					public void onClick(MenuItemClickEvent event) {
						final String idUd      = detailRecord.getAttributeAsString("idUd");
						final String idProcess = detailRecord.getAttributeAsString("idProcess");
						final String activityName = "#AGG_EXTRA_ITER";
						Record lRecord = new Record();
						lRecord.setAttribute("idUd", idUd);
						lRecord.setAttribute("idProcess", idProcess);
						lRecord.setAttribute("activityName", activityName);
						String title = I18NUtil.getMessages().modificaOpereAtto_Popup_Ud_title(getEstremiUdFromList(listRecord));
						new DettaglioOpereAttoPopup(lRecord, title);
					}
				});
				altreOpMenu.addItem(modificaOpereAttoMenuItem);
			}
						
			// Modifica Dati Pubblicazione Atto
			if (detailRecord.getAttributeAsBoolean("abilModificaDatiPubblAtto") && 
				detailRecord.getAttributeAsString("idProcess") != null && !detailRecord.getAttributeAsString("idProcess").equals("")) {
				MenuItem modificaDatiPubblAttoMenuItem = new MenuItem(I18NUtil.getMessages().modificaDatiPubblAtto_menu_apri_title(), "pratiche/task/buttons/modifica_dati_pubbl_atto.png");
				modificaDatiPubblAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					public void onClick(MenuItemClickEvent event) {
						final String idUd      = detailRecord.getAttributeAsString("idUd");
						final String idProcess = detailRecord.getAttributeAsString("idProcess");
						final String activityName = "#AGG_EXTRA_ITER";
						Record lRecord = new Record();
						lRecord.setAttribute("idUd", idUd);
						lRecord.setAttribute("idProcess", idProcess);
						lRecord.setAttribute("activityName", activityName);
						String title = I18NUtil.getMessages().modificaDatiPubblAtto_Popup_Ud_title(getEstremiUdFromList(listRecord));
						new DettaglioDatiPubblAttoPopup(lRecord, title);
					}
				});
				altreOpMenu.addItem(modificaDatiPubblAttoMenuItem);
			}
			
			if (detailRecord.getAttributeAsString("idProcess") != null && !detailRecord.getAttributeAsString("idProcess").equals("")) {
				final String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");
				if(lSistAMC != null && !"".equals(lSistAMC) && AurigaLayout.isPrivilegioAttivo("ATT/AMC")) {
					MenuItem eventoAMCMenuItem = new MenuItem("Evento " + lSistAMC, "buttons/gear.png");
					eventoAMCMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							
							final RecordList listaRecord = new RecordList();
							final Record recordAtto = new Record();
							recordAtto.setAttribute("unitaDocumentariaId", listRecord.getAttribute("idUdFolder"));
							recordAtto.setAttribute("flgRilevanzaContabile", "1"); // lo setto sempre a 1 altrimenti poi non mi esegue l'evento (tanto poi carica il dettaglio e riverifica se ha rilevanza contabile oppure no)									
							listaRecord.add(recordAtto);		
							final Record recordToSave = new Record();
							recordToSave.setAttribute("listaRecord", listaRecord);
							recordToSave.setAttribute("azione", AttiLayout._EVENTO_AMC);									
							EventoAMCPopup lEventoAMCPopup = new EventoAMCPopup("Evento " + lSistAMC, recordToSave, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									Layout.showWaitPopup("Evento " + lSistAMC + " in corso: potrebbe richiedere qualche secondo. Attendere…");
									GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "AzioneSuListaDocAttiCompletiDataSource" : "AzioneSuListaDocAttiDataSource");
									try {
										lGwtRestDataSource.addData(object, new DSCallback() {

											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {	
												Layout.hideWaitPopup();
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {							
													Record data = response.getData()[0];
													Map errorMessages = data.getAttributeAsMap("errorMessages");
													if (errorMessages != null && errorMessages.size() == 1) {
														String errorMsg = (String) errorMessages.get(recordAtto.getAttribute("unitaDocumentariaId"));
														Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
													} else {
														if(layout != null) {
															layout.doSearch();
														}
														Layout.addMessage(new MessageBean("Evento " + lSistAMC + " effettuato con successo", "", MessageType.INFO));							
													}
												} 									
											}
										});
									} catch (Exception e) {
										Layout.hideWaitPopup();
									}			
								}
							});
							lEventoAMCPopup.show();			
						}
					});
					altreOpMenu.addItem(eventoAMCMenuItem);
					
					if(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() && ("CONTABILIA".equalsIgnoreCase(lSistAMC) || "CONTABILIA2".equalsIgnoreCase(lSistAMC))) {
						MenuItem recuperaMovimentiContabiliaMenuItem = new MenuItem("Recupera movimenti " + lSistAMC, "attiInLavorazione/rilevanzaContabile.png");
						recuperaMovimentiContabiliaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
								lNuovaPropostaAtto2CompletaDataSource.addParam("isRecuperoMovimentiContabilia", "true");
								Record lRecordToLoad = new Record();
								lRecordToLoad.setAttribute("idUd", listRecord.getAttribute("idUdFolder"));
								Layout.showWaitPopup("Recupero movimenti " + lSistAMC +  " in corso: potrebbe richiedere qualche secondo. Attendere…");
								lNuovaPropostaAtto2CompletaDataSource.getData(lRecordToLoad, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										Layout.hideWaitPopup();
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {					
											Layout.addMessage(new MessageBean("Recupero movimenti " + lSistAMC +  " effettuato con successo", "", MessageType.INFO));
										}
									}
								});
							}
						});
						altreOpMenu.addItem(recuperaMovimentiContabiliaMenuItem);
					}
				}
			}
			
		}

		altreOpMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));

		return altreOpMenu;
	}

	protected void manageStampaEtichettaClick(final Record detailRecord) {
		ProtocollazioneDetail  protocollazioneDetail = ProtocollazioneDetail.getInstance(detailRecord);
		protocollazioneDetail.clickStampaEtichetta(detailRecord);
	}
		
	private void buildMenuRapidoCondivisione(final ListGridRecord listRecord, final String flgUdFolder, RecordList listaPreferiti, Menu scelteRapide) {
		
		for(int i=0; i < listaPreferiti.getLength();i++){
				
			Record currentRecord = listaPreferiti.get(i);
			final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
			final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
			final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
			
			MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
			currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
				@Override
				public void onClick(MenuItemClickEvent event) {
					
					final RecordList listaUdFolder = new RecordList();
					listaUdFolder.add(listRecord);
					RecordList listaDestInvioCC = new RecordList();
					Record recordAssegnazioni = new Record();
					recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
					recordAssegnazioni.setAttribute("typeNodo", tipoDestinatarioPreferito);
					listaDestInvioCC.add(recordAssegnazioni);
					
					Record record = new Record();
					record.setAttribute("flgUdFolder", flgUdFolder);
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("listaDestInvioCC", listaDestInvioCC);
					
					Layout.showWaitPopup("Invio per conoscenza in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
					try {
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								operationCallback(response, listRecord, "idUdFolder", "Invio per conoscenza effettuato con successo",
										"Si è verificato un errore durante l'invio per conoscenza!", null);
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			});
			scelteRapide.addItem(currentRapidoItem);			
		}
	}

	private void buildMenuRapidoAssegnazione(final ListGridRecord listRecord, final String flgUdFolder, RecordList listaPreferiti, Menu scelteRapide) {
		
		for(int i=0; i < listaPreferiti.getLength(); i++){
				
			Record currentRecord = listaPreferiti.get(i);
			final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
			final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
			final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");

			MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
			currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
				@Override
				public void onClick(MenuItemClickEvent event) {
					
					final RecordList listaUdFolder = new RecordList();
					listaUdFolder.add(listRecord);
					RecordList listaAssegnazioni = new RecordList();
					Record recordAssegnazioni = new Record();
					recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
					recordAssegnazioni.setAttribute("typeNodo",tipoDestinatarioPreferito);
					listaAssegnazioni.add(recordAssegnazioni);
					
					Record record = new Record();
					record.setAttribute("flgUdFolder", flgUdFolder);
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("listaAssegnazioni", listaAssegnazioni);
					if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_DEFAULT_MANTIENI_COPIA_IN_ASS_DOC")) {
						record.setAttribute("flgMantieniCopiaUd", true);
					}	
					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
					try {
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								operationCallback(response, listRecord,
										"idUdFolder", "Assegnazione effettuata con successo",
										"Si è verificato un errore durante l'assegnazione!", new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {														
										
										Record lRecordToLoad = new Record();
										lRecordToLoad.setAttribute("idUd", listRecord.getAttribute("idUdFolder"));
										getProtocolloDataSource().getData(lRecordToLoad, new DSCallback() {

											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
													manageStampaEtichettaPostAssegnazione(listRecord, response.getData()[0]);
												}
											}
										});
									}
								});
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			});
			scelteRapide.addItem(currentRapidoItem);				
		}
	}
	
	private void buildMenuRapidoSmistamento(final ListGridRecord listRecord, final String flgUdFolder, RecordList listaPreferiti, Menu scelteRapide) {
		
		for(int i=0; i < listaPreferiti.getLength(); i++){
				
			Record currentRecord = listaPreferiti.get(i);
			final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
			final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
			final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");

			MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
			currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
				@Override
				public void onClick(MenuItemClickEvent event) {
					
					final RecordList listaUdFolder = new RecordList();
					listaUdFolder.add(listRecord);
					RecordList listaAssegnazioni = new RecordList();
					Record recordAssegnazioni = new Record();
					recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
					recordAssegnazioni.setAttribute("typeNodo",tipoDestinatarioPreferito);
					listaAssegnazioni.add(recordAssegnazioni);
					
					Record record = new Record();
					record.setAttribute("flgUdFolder", flgUdFolder);
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("listaAssegnazioni", listaAssegnazioni);
					record.setAttribute("motivoInvio", "#SMIST");
					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
					try {
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								operationCallback(response, listRecord, "idUdFolder", "Smistamento effettuato con successo",
										"Si è verificato un errore durante lo smistamento!", null);
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			});
			scelteRapide.addItem(currentRapidoItem);				
		}
	}
	
	private void buildMenuRapidoSmistamentoCC(final ListGridRecord listRecord, final String flgUdFolder, RecordList listaPreferiti, Menu scelteRapide) {
		
		for(int i=0; i < listaPreferiti.getLength();i++){
				
			Record currentRecord = listaPreferiti.get(i);
			final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
			final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
			final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
			
			MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
			currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
				@Override
				public void onClick(MenuItemClickEvent event) {
					
					final RecordList listaUdFolder = new RecordList();
					listaUdFolder.add(listRecord);
					RecordList listaDestInvioCC = new RecordList();
					Record recordAssegnazioni = new Record();
					recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
					recordAssegnazioni.setAttribute("typeNodo", tipoDestinatarioPreferito);
					listaDestInvioCC.add(recordAssegnazioni);
					
					Record record = new Record();
					record.setAttribute("flgUdFolder", flgUdFolder);
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("motivoInvio", "#SMIST");
					record.setAttribute("listaDestInvioCC", listaDestInvioCC);
					
					Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
					try {
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								operationCallback(response, listRecord, "idUdFolder", "Smistamento effettuato con successo",
										"Si è verificato un errore durante lo smistamento!", null);
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			});
			scelteRapide.addItem(currentRapidoItem);			
		}
	}
	
	private void buildTimbraAllegato(final ListGridRecord listRecord, final Record detailRecord, final int nroAllegato,
			Menu operazioniFileAllegatoSubmenu, InfoFileRecord lInfoFileRecord, final boolean fileIntegrale) {

		boolean flgAddSubMenuTimbra = false;

		MenuItem timbraMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraMenuItem(), "file/timbra.gif");
		Menu timbraSubMenu = new Menu();	
		
		MenuItem apponiSegnaturaRegistrazioneFileAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_file_timbra_datiSegnatura(), "file/timbra.gif");
		apponiSegnaturaRegistrazioneFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						timbraFileAllegato(detailRecord, nroAllegato, fileIntegrale);
					}
				});
		apponiSegnaturaRegistrazioneFileAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
		timbraSubMenu.addItem(apponiSegnaturaRegistrazioneFileAllegatoMenuItem);

		if (detailRecord != null && detailRecord.getAttributeAsBoolean("attivaTimbroTipologia") != null
				&& detailRecord.getAttributeAsBoolean("attivaTimbroTipologia")) {

			MenuItem timbraFileAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_file_timbra_datiTipologia(), "file/timbra.gif");
			timbraFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String idDoc = listRecord.getAttribute("idDocPrimario");
					detailRecord.setAttribute("idDoc", idDoc);
					detailRecord.setAttribute("tipoTimbra", "T");
					timbraFileAllegato(detailRecord, nroAllegato, fileIntegrale);
				}
			});
			timbraFileAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			flgAddSubMenuTimbra=true;
			timbraSubMenu.addItem(timbraFileAllegatoMenuItem);

		}

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
			if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null
					&& (lInfoFileRecord.getMimetype().equals("application/pdf")
							|| lInfoFileRecord.getMimetype().startsWith("image"))) {

				MenuItem timbroConformitaOriginaleAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroConformitaOriginaleMenuItem(),"file/timbra.gif");
				timbroConformitaOriginaleAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
				timbroConformitaOriginaleAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_CARTACEO");
								timbraFileAllegato(detailRecord, nroAllegato, fileIntegrale);
							}
						});

				flgAddSubMenuTimbra=true;
				timbraSubMenu.addItem(timbroConformitaOriginaleAllegatoMenuItem);
			}
		}

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA") && lInfoFileRecord != null) {
			
			flgAddSubMenuTimbra=true;

			MenuItem timbroCopiaConformeAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem(), "file/timbra.gif");
			timbroCopiaConformeAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			Menu sottoMenuAllegatoTimbroCopiaConformeItem = new Menu();
			
			MenuItem timbroCopiaConformeStampaAllegatoItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_stampa(), "file/timbra.gif");	
			timbroCopiaConformeStampaAllegatoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_DIGITALE_STAMPA");
							timbraFileAllegato(detailRecord, nroAllegato, fileIntegrale);
						}
					});
			
			sottoMenuAllegatoTimbroCopiaConformeItem.addItem(timbroCopiaConformeStampaAllegatoItem);
			
			MenuItem timbroCopiaConformeDigitaleAllegatoItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_suppDigitale(), "file/timbra.gif");			
			timbroCopiaConformeDigitaleAllegatoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_DIGITALE_SUPP_DIG");
							timbraFileAllegato(detailRecord, nroAllegato, fileIntegrale);
						}
					});
			
			sottoMenuAllegatoTimbroCopiaConformeItem.addItem(timbroCopiaConformeDigitaleAllegatoItem);

			timbroCopiaConformeAllegatoMenuItem.setSubmenu(sottoMenuAllegatoTimbroCopiaConformeItem);
			timbraSubMenu.addItem(timbroCopiaConformeAllegatoMenuItem);
		}
		
		//Se ho piu voci aggiungo il sottoMenu Timbra
		if(flgAddSubMenuTimbra) {
			timbraMenuItem.setSubmenu(timbraSubMenu);
			operazioniFileAllegatoSubmenu.addItem(timbraMenuItem);
			//Se ho un unica voce la aggiungo ad altreOperazioni con voce "Timbra"
		}else {
			apponiSegnaturaRegistrazioneFileAllegatoMenuItem.setTitle("Timbra");
			operazioniFileAllegatoSubmenu.addItem(apponiSegnaturaRegistrazioneFileAllegatoMenuItem);
		}
	
	}

	private void buildTimbraButtons(final ListGridRecord listRecord, final Record detailRecord, InfoFileRecord lInfoFileRecord,
			Menu operazioniFilePrimarioSubmenu, final boolean fileIntegrale) {
		
		boolean flgAddSubMenuTimbra = false;

		MenuItem timbraMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraMenuItem(), "file/timbra.gif");
		Menu timbraSubMenu = new Menu();
		
		MenuItem apponiSegnaturaRegistrazioneFilePrimarioMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_file_timbra_datiSegnatura(), "file/timbra.gif");
		apponiSegnaturaRegistrazioneFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						timbraFilePrimario(detailRecord, fileIntegrale);
					}
				});
		apponiSegnaturaRegistrazioneFilePrimarioMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
		timbraSubMenu.addItem(apponiSegnaturaRegistrazioneFilePrimarioMenuItem);

		if (detailRecord != null && detailRecord.getAttributeAsBoolean("attivaTimbroTipologia") != null
				&& detailRecord.getAttributeAsBoolean("attivaTimbroTipologia")) {
			
			MenuItem timbraFilePrimarioMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_file_timbra_datiTipologia(), "file/timbra.gif");
			timbraFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String idDoc = listRecord.getAttribute("idDocPrimario");
					detailRecord.setAttribute("idDoc", idDoc);
					detailRecord.setAttribute("tipoTimbro", "T");
					timbraFilePrimario(detailRecord, fileIntegrale);
				}
			});
			timbraFilePrimarioMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			flgAddSubMenuTimbra=true;
			timbraSubMenu.addItem(timbraFilePrimarioMenuItem);
		}

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
			if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null
					&& (lInfoFileRecord.getMimetype().equals("application/pdf")
							|| lInfoFileRecord.getMimetype().startsWith("image"))) {

				MenuItem timbroConformitaOriginalePrimarioMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroConformitaOriginaleMenuItem(),"file/timbra.gif");
				timbroConformitaOriginalePrimarioMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
				timbroConformitaOriginalePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_CARTACEO");
								timbraFilePrimario(detailRecord,fileIntegrale);
							}
						});

				flgAddSubMenuTimbra=true;
				timbraSubMenu.addItem(timbroConformitaOriginalePrimarioMenuItem);
			}
		}

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA") && lInfoFileRecord != null) {
			
			flgAddSubMenuTimbra=true;

			MenuItem timbroCopiaConformePrimarioMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem(), "file/timbra.gif");
			timbroCopiaConformePrimarioMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			Menu sottoMenuTimbroCopiaConformeItem = new Menu();
			
			MenuItem timbroCopiaConformeStampaItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_stampa(), "file/timbra.gif");	
			timbroCopiaConformeStampaItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_DIGITALE_STAMPA");
							timbraFilePrimario(detailRecord, fileIntegrale);
						}
					});

			sottoMenuTimbroCopiaConformeItem.addItem(timbroCopiaConformeStampaItem);
			
			MenuItem timbroCopiaConformeDigitaleItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_suppDigitale(), "file/timbra.gif");	
			timbroCopiaConformeDigitaleItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_DIGITALE_SUPP_DIG");
							timbraFilePrimario(detailRecord, fileIntegrale);
						}
					});

			sottoMenuTimbroCopiaConformeItem.addItem(timbroCopiaConformeDigitaleItem);
			
			timbroCopiaConformePrimarioMenuItem.setSubmenu(sottoMenuTimbroCopiaConformeItem);
			timbraSubMenu.addItem(timbroCopiaConformePrimarioMenuItem);
		}
		
		//Se ho piu voci aggiungo il sottoMenu Timbra
		if(flgAddSubMenuTimbra) {
			timbraMenuItem.setSubmenu(timbraSubMenu);
			operazioniFilePrimarioSubmenu.addItem(timbraMenuItem);
			//Se ho un unica voce la aggiungo ad altreOperazioni con voce "Timbra"
		}else {
			apponiSegnaturaRegistrazioneFilePrimarioMenuItem.setTitle("Timbra");
			operazioniFilePrimarioSubmenu.addItem(apponiSegnaturaRegistrazioneFilePrimarioMenuItem);
		}

	}

	public boolean isDownloadZipVisible(Record record) {
		boolean filePrimarioExists = record.getAttribute("uriFilePrimario") != null && !record.getAttribute("uriFilePrimario").isEmpty();
		boolean allegatiExist = record.getAttributeAsRecordList("listaAllegati") != null && record.getAttributeAsRecordList("listaAllegati").getLength() > 0;
		if (allegatiExist) {
			for (int i = 0; i < record.getAttributeAsRecordList("listaAllegati").getLength(); i++) {
				Record currentAllegato = record.getAttributeAsRecordList("listaAllegati").get(i);
				String currentUriFileAllegato = currentAllegato.getAttribute("uriFileAllegato");
				if (currentUriFileAllegato == null || currentUriFileAllegato.isEmpty()) {
					allegatiExist = Boolean.FALSE;
					break;
				}
			}
		}
		return filePrimarioExists || allegatiExist;
	}

	/**
	 * Crea l'azione di download dello zip contenente tutti gli allegati ed il file primario dell'unità documentale
	 * 
	 * @param listRecord
	 * @return
	 */
	private void manageOnClickScaricaFile(final ListGridRecord listRecord, final Record record, String operazione) {
		if(!"scaricaOriginali".equalsIgnoreCase(operazione) && !"scaricaSbustati".equalsIgnoreCase(operazione) && !"scaricaConformitaCustom".equalsIgnoreCase(operazione)) {
			if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaTimbroDocZip")) {
				showOpzioniTimbratura(listRecord, record, operazione);
			}else {
				String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro");
				String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro");
				String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina");
				
				Record opzioniTimbro = new Record();
				opzioniTimbro.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
				opzioniTimbro.setAttribute("posizioneTimbro", posizioneTimbroPref);
				opzioniTimbro.setAttribute("tipoPagina", tipoPaginaPref);
				
				manageGenerateDocZip(listRecord, record, operazione, opzioniTimbro);
				
			}
		}else {
			manageGenerateDocZip(listRecord, record, operazione, null);
		}	
	}
	
	public void showOpzioniTimbratura(final ListGridRecord listRecord, final Record record, final String operazione) {

		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro");
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro");
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina");

		Record opzioniDefaultTimbro = new Record();
		opzioniDefaultTimbro.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
		opzioniDefaultTimbro.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
		opzioniDefaultTimbro.setAttribute("tipoPaginaPref", tipoPaginaPref);
		opzioniDefaultTimbro.setAttribute("scaricoZip", true);

		ApponiTimbroWindow apponiTimbroWindow = new ApponiTimbroWindow(opzioniDefaultTimbro, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {	
				
				Record impostazioniTimbratura = new Record();
				impostazioniTimbratura.setAttribute("posizioneTimbro", object.getAttribute("posizioneTimbro"));
				impostazioniTimbratura.setAttribute("rotazioneTimbro", object.getAttribute("rotazioneTimbro"));
				impostazioniTimbratura.setAttribute("tipoPagina", object.getAttribute("tipoPaginaTimbro"));
				
				manageGenerateDocZip(listRecord, record, operazione, impostazioniTimbratura);
				
			}

			
		});
		apponiTimbroWindow.show();
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE FUNZIONALITA' INVIO AL PROTOCOLLO
	 */
	public boolean isAbilInvioAlProtocollo(Record record) {
		if(record != null && record.getAttributeAsRecordList("listaDestinatariUoProtocollazione") != null &&
				record.getAttributeAsRecordList("listaDestinatariUoProtocollazione").getLength() > 0) {
			for(int i=0; i < record.getAttributeAsRecordList("listaDestinatariUoProtocollazione").getLength(); i++) {
				Record item = record.getAttributeAsRecordList("listaDestinatariUoProtocollazione").get(i);
				if(item.getAttributeAsString("idUo") != null && !"".equalsIgnoreCase(item.getAttributeAsString("idUo"))){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param listRecord
	 * @param record
	 * @param operazione
	 * @param impostazioniTimbratura 
	 */
	public void manageGenerateDocZip(final ListGridRecord listRecord, final Record record, String operazione, Record impostazioniTimbratura) {
		GWTRestDataSource datasource = getProtocolloDataSource();
		datasource.extraparam.put("segnatura", listRecord.getAttribute("segnatura"));
		datasource.extraparam.put("limiteMaxZipError", I18NUtil.getMessages().alert_archivio_list_limiteMaxZipError());
		datasource.extraparam.put("operazione", operazione);
		datasource.setForceToShowPrompt(false);
		
		if(impostazioniTimbratura!=null) {
			datasource.extraparam.put("posizioneTimbro", impostazioniTimbratura.getAttribute("posizioneTimbro"));
			datasource.extraparam.put("rotazioneTimbro", impostazioniTimbratura.getAttribute("rotazioneTimbro"));
			datasource.extraparam.put("tipoPagina", impostazioniTimbratura.getAttribute("tipoPagina"));
		}		
				
		Layout.addMessage(new MessageBean(I18NUtil.getMessages().alert_archivio_list_downloadDocsZip_wait(), "", MessageType.WARNING));
		datasource.executecustom("generateDocsZip", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record result = response.getData()[0];
				String message = result.getAttribute("message");
				String warningTimbro = result.getAttribute("warningTimbro");
				String warningSbusta = result.getAttribute("warningSbusta");
				if (message != null) {
					Layout.addMessage(new MessageBean(message, "", MessageType.ERROR));
				} else if (result.getAttribute("errorCode") == null || result.getAttribute("errorCode").isEmpty()) {
					String zipUri = response.getData()[0].getAttribute("storageZipRemoteUri");
					String zipName = response.getData()[0].getAttribute("zipName");
					
					scaricaFile(listRecord.getAttribute("idUdFolder"), "", zipName, zipUri, zipUri);
					
					if (warningTimbro != null && !"".equals(warningTimbro)) {
						Layout.addMessage(new MessageBean(warningTimbro, "", MessageType.WARNING));
					}else if (warningSbusta != null && !"".equals(warningSbusta)) {
						Layout.addMessage(new MessageBean(warningSbusta, "", MessageType.WARNING));
					}
				}
			}
		});
	}

	protected MenuItem creaInvioNotificaInteropMenuItem(final Record record, final String tipoNotifica) {
		String icona = "postaElettronica/notifiche.png";
		if (tipoNotifica.equals("conferma")) {
			icona = "postaElettronica/notifInteropConf.png";
		} else if (tipoNotifica.equals("eccezione")) {
			icona = "postaElettronica/notifInteropEcc.png";
		} else if (tipoNotifica.equals("aggiornamento")) {
			icona = "postaElettronica/notifInteropAgg.png";
		} else if (tipoNotifica.equals("annullamento")) {
			icona = "postaElettronica/notifInteropAnn.png";
		}
		MenuItem invioNotificaInteropMenuItem = new MenuItem("Invia notifica di " + tipoNotifica, icona);
		invioNotificaInteropMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageInvioNotificaInteropClick(record, tipoNotifica);
			}
		});
		return invioNotificaInteropMenuItem;
	}

	protected void manageInvioNotificaInteropClick(Record record, String tipoNotifica) {
		final GWTRestService<Record, Record> lGwtRestServiceInvioNotifica = new GWTRestService<Record, Record>("AurigaInvioNotificaInteropUdDatasource");
		lGwtRestServiceInvioNotifica.extraparam.put("tipoNotifica", tipoNotifica);
		lGwtRestServiceInvioNotifica.call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				managerInvioNotificaInteropResponse(lGwtRestServiceInvioNotifica, object);
			}
		});
	}

	protected void managerInvioNotificaInteropResponse(GWTRestService<Record, Record> lGwtRestServiceInvioNotifica, Record record) {
		//record.setAttribute("fromUD", "1");
		InvioNotificaInteropWindow lInvioNotificaInteropWindow = new InvioNotificaInteropWindow(lGwtRestServiceInvioNotifica, record, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				mReloadListCallback.executeCallback();
			}
		});
		lInvioNotificaInteropWindow.show();
	}

	public Menu createFolderAltreOpMenu(final ListGridRecord listRecord, final Record detailRecord, final Record destinatariPreferiti) {
		mReloadListCallback.setRecord(detailRecord);
		mReloadListCallback.setLayout(layout);
		Menu altreOpMenu = new Menu();

		MenuItem visualizzaMenuItem = new MenuItem("Visualizza", "buttons/detail.png");
		visualizzaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageDetailButtonClick(listRecord);
			}
		});
		altreOpMenu.addItem(visualizzaMenuItem);

		if (fromScrivania) {
			MenuItem visualizzaContenutiMenuItem = new MenuItem("Visualizza contenuti", "buttons/visDocFasc.png");
			visualizzaContenutiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					ContenutiFascicoloPopup contenutiFascicoliPopup = new ContenutiFascicoloPopup(listRecord);
					contenutiFascicoliPopup.show();
				}
			});
			altreOpMenu.addItem(visualizzaContenutiMenuItem);
		}

		if (detailRecord.getAttributeAsBoolean("abilPresaInCarico")) {
			MenuItem prendiInCaricoMenuItem = new MenuItem("Prendi in carico", "archivio/prendiInCarico.png");
			prendiInCaricoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final RecordList listaUdFolder = new RecordList();
					listaUdFolder.add(listRecord);
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PresaInCaricoDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							operationCallback(response, listRecord, "idUdFolder", "Presa in carico effettuata con successo",
									"Si è verificato un errore durante la presa in carico!", null);
						}
					});
				}
			});
			altreOpMenu.addItem(prendiInCaricoMenuItem);
		}

		if (detailRecord.getAttributeAsBoolean("abilAssegnazioneSmistamento")) {
			final Menu creaAssegna = new Menu();			
			RecordList listaUOPreferiti = null;
			RecordList listaUtentiPreferiti = null;
						
			if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite")!=null)
				listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_FOLDER.getValue()));
			
			if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti")!=null)
				listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.ASSEGNA_FOLDER.getValue()));

			MenuItem assegnazioneMenuItem = new MenuItem("Assegna", "archivio/assegna.png");
			
			boolean noMenuRapido = true; // identifica la presenza o menu di valori da visualizzare nel menu rapido
			final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare
			
			if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
				listaPreferiti.addList(listaUOPreferiti.toArray());
				noMenuRapido = false;
			}
			
			if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
				listaPreferiti.addList(listaUtentiPreferiti.toArray());
				noMenuRapido = false;
			}
			
			// Assegnazione Standard 
			MenuItem assegnaMenuStandardItem = new MenuItem("Standard");
			assegnaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					String title = "Compila dati assegnazione del fascicolo " + getEstremiFolderFromList(listRecord);
					
					final AssegnazionePopup assegnazionePopup = new AssegnazionePopup("F", listRecord, title) {

						@Override
						public RecordList getListaPreferiti() {
							return listaPreferiti;
						}
												
						@Override
						public void onClickOkButton(Record record, final DSCallback callback) {
							
							final RecordList listaUdFolder = new RecordList();
							listaUdFolder.add(listRecord);
							
							record.setAttribute("flgUdFolder", "F");
							record.setAttribute("listaRecord", listaUdFolder);
							
							Layout.showWaitPopup("Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
							try {
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										operationCallback(response, listRecord, 
												"idUdFolder", 
												"Assegnazione effettuata con successo",
												"Si è verificato un errore durante l'assegnazione!", new DSCallback() {
													
													@Override
													public void execute(DSResponse response, Object rawData, DSRequest request) {														
														if(callback != null){
															callback.execute(response, rawData, request);
														}
														Record lRecordToLoad = new Record();
														lRecordToLoad.setAttribute("idUd", listRecord.getAttribute("idUdFolder"));
														getProtocolloDataSource().getData(lRecordToLoad, new DSCallback() {

															@Override
															public void execute(DSResponse response, Object rawData, DSRequest request) {
																if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
																	manageStampaEtichettaPostAssegnazione(listRecord, response.getData()[0]);
																}
															}
														});
													}
												});
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						}
					};
					assegnazionePopup.show();
				}
			});
			creaAssegna.addItem(assegnaMenuStandardItem);
			
			// Assegnazione Rapido 
			MenuItem assegnaMenuRapidoItem = new MenuItem("Rapido");
			
			Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
			
			if(success != null && success == true){
				
				Menu scelteRapide = new Menu();
				
				if(noMenuRapido){
					assegnaMenuRapidoItem.setEnabled(false);
				} else {
					buildMenuRapidoAssegnazione(listRecord, "F", listaPreferiti, scelteRapide);
					assegnaMenuRapidoItem.setSubmenu(scelteRapide);
				}
				
			} else {
				assegnaMenuRapidoItem.setEnabled(false);
			}
			creaAssegna.addItem(assegnaMenuRapidoItem);
			assegnazioneMenuItem.setSubmenu(creaAssegna);
			
			altreOpMenu.addItem(assegnazioneMenuItem);
		}

		// SMISTAMENTO
		if (detailRecord.getAttributeAsBoolean("abilSmista")) {

			final Menu creaSmista = new Menu(); 
			
			RecordList listaUOPreferiti = null;
			RecordList listaUtentiPreferiti = null;
			
			if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite")!=null)
				listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_DOC.getValue()));
			
			if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti")!=null)
				listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_DOC.getValue()));
			
			MenuItem smistamentoMenuItem = new MenuItem("Smista", "archivio/smista.png");
			
			boolean noMenuRapido = true; // identifica la presenza o menu di valori da visualizzare nel menu rapido
			final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare
			
			boolean flgSoloUo = false;
			final String codSupportoOrig = detailRecord != null ? detailRecord.getAttributeAsString("codSupportoOrig") : null;
			if(codSupportoOrig != null) {
				if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
				   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
				   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
					flgSoloUo = true;
				}
			}
			if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
				listaPreferiti.addList(listaUOPreferiti.toArray());
				noMenuRapido = false;
			}
			
			if(!flgSoloUo) {
				if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
					listaPreferiti.addList(listaUtentiPreferiti.toArray());
					noMenuRapido = false;
				}
			}
			
			// Smistamento Standard 
			MenuItem smistaMenuStandardItem = new MenuItem("Standard");
			smistaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					final SmistamentoPopup smistamentoPopup = new SmistamentoPopup("F", listRecord) {
						
						@Override
						public RecordList getListaPreferiti() {
							return listaPreferiti;
						}			
						
						@Override
						public void onClickOkButton(Record record, final DSCallback callback) {
							
							final RecordList listaUdFolder = new RecordList();
							listaUdFolder.add(listRecord);
							
							record.setAttribute("flgUdFolder", "F");
							record.setAttribute("motivoInvio", "#SMIST");
							record.setAttribute("listaRecord", listaUdFolder);
							
							Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
							try {
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										operationCallback(response, listRecord, "idUdFolder", "Smistamento effettuato con successo",
												"Si è verificato un errore durante lo smistamento!", callback);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						}
					};
					smistamentoPopup.show();
				}
			});
			creaSmista.addItem(smistaMenuStandardItem);
			
			// Smista Rapido 
			MenuItem smistaMenuRapidoItem = new MenuItem("Rapido");
			
			Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
			if(success != null && success == true){					
				Menu scelteRapide = new Menu();			
				if(noMenuRapido){
					smistaMenuRapidoItem.setEnabled(false);
				} else {
					buildMenuRapidoSmistamento(listRecord, "F", listaPreferiti, scelteRapide);
					smistaMenuRapidoItem.setSubmenu(scelteRapide);
				}			
			} else {
				smistaMenuRapidoItem.setEnabled(false);
			}
			creaSmista.addItem(smistaMenuRapidoItem);
			smistamentoMenuItem.setSubmenu(creaSmista);
			
			altreOpMenu.addItem(smistamentoMenuItem);
		}
		
		// SMISTAMENTO CC
		if (detailRecord.getAttributeAsBoolean("abilSmistaCC")) {
			
			final Menu creaSmistaCC = new Menu(); 

			RecordList listaUOPreferiti = null;
			RecordList listaUtentiPreferiti = null;
			
			if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite")!=null)
				listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_DOC.getValue()));
			
			if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti")!=null)
				listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_DOC.getValue()));
			
			MenuItem smistamentoCCMenuItem = new MenuItem("Smista", "archivio/smista.png");
			
			boolean noMenuRapido = true; // identifica la presenza o menu di valori da visualizzare nel menu rapido
			final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare
			
			boolean flgSoloUo = false;
			final String codSupportoOrig = detailRecord != null ? detailRecord.getAttributeAsString("codSupportoOrig") : null;
			if(codSupportoOrig != null) {
				if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
				   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
				   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
					flgSoloUo = true;
				}
			}
			if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
				listaPreferiti.addList(listaUOPreferiti.toArray());
				noMenuRapido = false;
			}
			
			if(!flgSoloUo) {
				if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
					listaPreferiti.addList(listaUtentiPreferiti.toArray());
					noMenuRapido = false;
				}
			}
			
			// SmistamentoCC Standard 
			MenuItem smistaCCMenuStandardItem = new MenuItem("Standard");
			smistaCCMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					String title = "Compila dati smistamento " + getEstremiFolderFromList(listRecord);
					final CondivisionePopup condivisionePopup = new CondivisionePopup("F", listRecord, title) {
						
						@Override
						public boolean isSmistamentoCC() {
							return true;
						}
						
						@Override
						public RecordList getListaPreferiti() {
							return listaPreferiti;
						}
						
						@Override
						public void onClickOkButton(Record record, final DSCallback callback) {
							final RecordList listaUdFolder = new RecordList();
							listaUdFolder.add(listRecord);
							
							record.setAttribute("flgUdFolder", "F");
							record.setAttribute("motivoInvio", "#SMIST");
							record.setAttribute("listaRecord", listaUdFolder);
							
							Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
							try {
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										operationCallback(response, listRecord, "idUdFolder", "Smistamento effettuato con successo",
												"Si è verificato un errore durante lo smistamento!", callback);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						}
					};
					condivisionePopup.show();
				}
			});
			creaSmistaCC.addItem(smistaCCMenuStandardItem);
			
			// SmistaCC Rapido 
			MenuItem smistaCCMenuRapidoItem = new MenuItem("Rapido");
			
			Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
			if(success != null && success == true){					
				Menu scelteRapide = new Menu();			
				if(noMenuRapido){
					smistaCCMenuRapidoItem.setEnabled(false);
				} else {
					buildMenuRapidoSmistamentoCC(listRecord, "F", listaPreferiti, scelteRapide);
					smistaCCMenuRapidoItem.setSubmenu(scelteRapide);
				}			
			} else {
				smistaCCMenuRapidoItem.setEnabled(false);
			}
			creaSmistaCC.addItem(smistaCCMenuRapidoItem);
			smistamentoCCMenuItem.setSubmenu(creaSmistaCC);
			
			altreOpMenu.addItem(smistamentoCCMenuItem);
		}

		if (detailRecord.getAttributeAsBoolean("abilCondivisione")) {
			
			final Menu creaCondivisione = new Menu();
			
			RecordList listaUOPreferiti = null;
			RecordList listaUtentiPreferiti = null;
			
			if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite")!=null)
				listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.INVIO_CC_FOLDER.getValue()));
			
			if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti")!=null)
				listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.INVIO_CC_FOLDER.getValue()));

			MenuItem condivisioneMenuItem = new MenuItem("Invia per conoscenza", "archivio/condividi.png");
			
			boolean noMenuRapido = true; // identifica la presenza o menu di valori da visualizzare nel menu rapido
			final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare
			
			if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
				listaPreferiti.addList(listaUOPreferiti.toArray());
				noMenuRapido = false;
			}
			
			if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
				listaPreferiti.addList(listaUtentiPreferiti.toArray());
				noMenuRapido = false;
			}	
			
			// Invio per conoscenza Standard
			MenuItem condivisioneMenuStandardItem = new MenuItem("Standard");
			condivisioneMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					String title = I18NUtil.getMessages().condivisioneWindow_title() + " del fascicolo " + getEstremiFolderFromList(listRecord);
					
					final CondivisionePopup condivisionePopup = new CondivisionePopup("F", listRecord, title) {
						
						@Override
						public RecordList getListaPreferiti() {
							return listaPreferiti;
						}

						@Override
						public void onClickOkButton(Record record, final DSCallback callback) {
							final RecordList listaUdFolder = new RecordList();
							listaUdFolder.add(listRecord);
							
							record.setAttribute("flgUdFolder", "F");
							record.setAttribute("listaRecord", listaUdFolder);
							
							Layout.showWaitPopup("Invio per conoscenza in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
							try {
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										operationCallback(response, listRecord, "idUdFolder", "Invio per conoscenza effettuato con successo",
												"Si è verificato un errore durante l'invio per conoscenza!", callback);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}

						}
					};
					condivisionePopup.show();
				}
			});
			creaCondivisione.addItem(condivisioneMenuStandardItem);
			
			// Invio per conoscenza Rapida
			MenuItem condivisioneMenuRapidoItem = new MenuItem("Rapida");
			
			Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
			
			if(success != null && success == true) {
				
				Menu scelteRapide = new Menu();				
				
				if(noMenuRapido){
					condivisioneMenuRapidoItem.setEnabled(false);
				} else {
					buildMenuRapidoCondivisione(listRecord, "F", listaPreferiti, scelteRapide);
					condivisioneMenuRapidoItem.setSubmenu(scelteRapide);
				}
				
			} else {
				condivisioneMenuRapidoItem.setEnabled(false);
			}
			creaCondivisione.addItem(condivisioneMenuRapidoItem);
			
			condivisioneMenuItem.setSubmenu(creaCondivisione);
			altreOpMenu.addItem(condivisioneMenuItem);
		}

		if (detailRecord.getAttributeAsBoolean("abilEliminazione")) {
			MenuItem eliminazioneMenuItem = new MenuItem("Elimina", "buttons/delete.png");
			eliminazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					deleteFascicoloFromList(listRecord, null);
				}
			});
			altreOpMenu.addItem(eliminazioneMenuItem);
		}

		if (detailRecord.getAttributeAsBoolean("abilModificaDati")) {
			MenuItem modificaMenuItem = new MenuItem("Modifica", "buttons/modify.png");
			modificaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					manageModifyButtonClick(listRecord);
					layout.setReloadListCallback(mReloadListCallback);
				}
			});
			altreOpMenu.addItem(modificaMenuItem);
		}
		
		if (detailRecord.getAttributeAsBoolean("abilModificaTipologia")) {
			MenuItem modificaTipologiaMenuItem = new MenuItem("Modifica/Assegna tipologia", "archivio/modificaTipologia.png");
			modificaTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					clickModificaTipologia(listRecord,detailRecord);
				}
			});
			altreOpMenu.addItem(modificaTipologiaMenuItem);
		}

		if (detailRecord.getAttributeAsBoolean("abilModAssInviiCC")) {
			MenuItem inviiEffettuatiMenuItem = new MenuItem("Modifica/annulla ass./invii cc", "archivio/inviiEffettuati.png");
			inviiEffettuatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
				final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("InviiEffettuatiDataSource", "idInvioNotifica", FieldType.TEXT);
				lGwtRestDataSource.addParam("flgUdFolder", listRecord.getAttribute("flgUdFolder"));
				lGwtRestDataSource.addParam("idUdFolder", listRecord.getAttribute("idUdFolder"));
				lGwtRestDataSource.fetchData(null, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								RecordList data = response.getDataAsRecordList();
								if (data.getLength() == 0) {
									Layout.addMessage(new MessageBean("Non risultano invii effettuati da te o dalla/e UO cui sei associato", "", MessageType.INFO));
								} else if (data.getLength() > 0) {
									Record lRecordToLoad = new Record();
									lRecordToLoad.setAttribute("idUdFolder", listRecord.getAttribute("idUdFolder"));
								    getArchivioDataSource().getData(lRecordToLoad, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												
												Record detailRecord = response.getData()[0];	
												
												Record recordToPrint = new Record();
												recordToPrint.setAttribute("idUdFolder", detailRecord.getAttribute("idUdFolder"));
												recordToPrint.setAttribute("flgUdFolder", "F");
												recordToPrint.setAttribute("estremiUdFolder", getEstremiFolderFromList(listRecord));
												
												String title = "Invii effettutati sul fascicolo " + getEstremiFolderFromList(listRecord);
												
											    InviiEffettuatiWindow inviiEffettuatiPopup = new InviiEffettuatiWindow(lGwtRestDataSource, recordToPrint, title) {

													@Override
													public void manageOnCloseClick() {
														super.manageOnCloseClick();
														layout.doSearch();
													}
												};
												inviiEffettuatiPopup.show();
											}
										}
									});
								}
							}
						}
					});
				}
			});
			altreOpMenu.addItem(inviiEffettuatiMenuItem);
		}

		if (detailRecord.getAttributeAsBoolean("abilRestituzione")) {
			MenuItem restituzioneMenuItem = new MenuItem("Restituisci", "archivio/restituzione.png");
			restituzioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					RestituzionePopup restituzionePopup = new RestituzionePopup() {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							final RecordList listaUdFolder = new RecordList();
							listaUdFolder.add(listRecord);
							final Record record = new Record();
							record.setAttribute("listaRecord", listaUdFolder);
							record.setAttribute("flgIgnoreWarning", _form.getValue("flgIgnoreWarning"));
							record.setAttribute("messaggio", _form.getValue("messaggio"));
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("RestituzioneDataSource");
							lGwtRestDataSource.addData(record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									operationCallbackRestituisci(response, listRecord, _form, new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {														
											if(callback != null){
												callback.execute(response, rawData, request);
											}
											if(listRecord.getAttribute("flgUdFolder") != null &&
													"U".equalsIgnoreCase(listRecord.getAttribute("flgUdFolder"))){												
											Record lRecordToLoad = new Record();
											lRecordToLoad.setAttribute("idUd", listRecord.getAttribute("idUdFolder"));
											getProtocolloDataSource().getData(lRecordToLoad, new DSCallback() {

												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
														manageStampaEtichettaPostAssegnazione(listRecord, response.getData()[0]);
													}
												}
											});
										}
										}
									});
								}
							});
						}
					};
					restituzionePopup.show();
				}
			});
			altreOpMenu.addItem(restituzioneMenuItem);

		}
//		if (detailRecord.getAttributeAsBoolean("abilArchiviazione")) {
//			MenuItem archiviazioneMenuItem = new MenuItem(AurigaLayout.getParametroDB("LABEL_SET_INTERESSE_CESSATO_FASC"), "archivio/archiviazione.png");
//			archiviazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
//
//				@Override
//				public void onClick(MenuItemClickEvent event) {
//					final RecordList listaUdFolder = new RecordList();
//					listaUdFolder.add(listRecord);
//					Record record = new Record();
//					record.setAttribute("listaRecord", listaUdFolder);
//					record.setAttribute("flgInteresseCessato", "1");
//					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchiviazioneDataSource");
//					lGwtRestDataSource.addData(record, new DSCallback() {
//
//						@Override
//						public void execute(DSResponse response, Object rawData, DSRequest request) {
//							if (layout instanceof ScrivaniaLayout)
//								((ScrivaniaLayout) layout).massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
//										"Archiviazione effettuata con successo", "Tutti i record selezionati per l'archiviazione sono andati in errore!",
//										"Alcuni dei record selezionati per l'archiviazione sono andati in errore!", null);
//							else if (layout instanceof ArchivioLayout)
//								((ArchivioLayout) layout).massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
//										"Archiviazione effettuata con successo", "Tutti i record selezionati per l'archiviazione sono andati in errore!",
//										"Alcuni dei record selezionati per l'archiviazione sono andati in errore!", null);
//
//						}
//					});
//				}
//			});
//			altreOpMenu.addItem(archiviazioneMenuItem);
//		}
		MenuItem spostaInEliminatiMenuItem = new MenuItem("Sposta in Eliminati", "buttons/cestino.png");
		spostaInEliminatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				final EliminazionePopup eliminazionePopup = new EliminazionePopup() {

					@Override
					public void onClickOkButton(final DSCallback callback) {
						final RecordList listaUdFolder = new RecordList();
						Record rec = new Record();
						rec.setAttribute("flgUdFolder", listRecord.getAttribute("flgUdFolder"));
						rec.setAttribute("idUdFolder", listRecord.getAttribute("idUdFolder"));
						listaUdFolder.add(rec);
						Record record = new Record();
						record.setAttribute("listaRecord", listaUdFolder);
						record.setAttribute("motivo", _form.getValue("motivo"));
						record.setAttribute("sezioneAreaLav", ((ScrivaniaLayout) layout).getCodSezione());
						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("EliminazioneDaAreaLavoroDataSource");
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (idFolder != null && "-99999".equals(idFolder)) {
									if (layout instanceof ScrivaniaLayout) {
										((ScrivaniaLayout) layout).massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
												"Eliminazione dalla Scrivania effettuata con successo",
												"Tutti i record selezionati per l'eliminazione dalla Scrivania sono andati in errore!",
												"Alcuni dei record selezionati per l'eliminazione dalla Scrivania sono andati in errore!", callback);
									} else if (layout instanceof ArchivioLayout) {
										((ArchivioLayout) layout).massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
												"Eliminazione dalla Scrivania effettuata con successo",
												"Tutti i record selezionati per l'eliminazione dalla Scrivania sono andati in errore!",
												"Alcuni dei record selezionati per l'eliminazione dalla Scrivania sono andati in errore!", callback);
									}
								} else {
									if (layout instanceof ScrivaniaLayout) {
										((ScrivaniaLayout) layout).massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
												"Spostamento in Eliminati effettuato con successo",
												"Tutti i record selezionati per lo spostamento in Eliminati sono andati in errore!",
												"Alcuni dei record selezionati per lo spostamento in Eliminati sono andati in errore!", callback);
									} else if (layout instanceof ArchivioLayout) {
										((ArchivioLayout) layout).massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
												"Spostamento in Eliminati effettuato con successo",
												"Tutti i record selezionati per lo spostamento in Eliminati sono andati in errore!",
												"Alcuni dei record selezionati per lo spostamento in Eliminati sono andati in errore!", callback);
									}
								}
							}
						});
					}
				};
				eliminazionePopup.show();
			}
		});
		if(showSpostaEliminatiButton()){
			altreOpMenu.addItem(spostaInEliminatiMenuItem);
		}
		if (detailRecord.getAttributeAsBoolean("abilModificaDati")) {
			MenuItem apposizioneCommentiMenuItem = new MenuItem(I18NUtil.getMessages().apposizioneCommenti_menu_apri_title(), "archivio/note_commenti.png");
			apposizioneCommentiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				public void onClick(MenuItemClickEvent event) {
					manageApposizioneCommenti(listRecord);
				}
			});
			altreOpMenu.addItem(apposizioneCommentiMenuItem);
		}

		if (detailRecord.getAttributeAsBoolean("abilChiudiFascicolo")) {
			MenuItem chiudiFascicoloMenuItem = new MenuItem(I18NUtil.getMessages().archivio_chiudiFascicoloButton_prompt(), "archivio/save_key.png");
			chiudiFascicoloMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					chiudiFascicoloFromList(listRecord, null);
				}
			});
			altreOpMenu.addItem(chiudiFascicoloMenuItem);
		}
		
		if (detailRecord.getAttributeAsBoolean("abilRiapriFascicolo")) {
			MenuItem riapriFascicoloMenuItem = new MenuItem(I18NUtil.getMessages().archivio_riapriFascicoloButton_prompt(), "archivio/annullaArchiviazione.png");
			riapriFascicoloMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					riapriFascicoloFromList(listRecord, null);
				}
			});
			altreOpMenu.addItem(riapriFascicoloMenuItem);
		}

		if (detailRecord.getAttributeAsBoolean("abilVersaInArchivioStoricoFascicolo")) {
			if (detailRecord.getAttributeAsBoolean("flgFascTitolario")) {
				MenuItem abilVersaInArchivioStoricoFascicoloMenuItem = new MenuItem(I18NUtil.getMessages()
						.archivio_versaInArchivioStoricoFascicoloButton_prompt(), "archivio/cassaforte.png");
				abilVersaInArchivioStoricoFascicoloMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						versaInArchivioStoricoFascicoloFromList(listRecord, null);
					}
				});
				altreOpMenu.addItem(abilVersaInArchivioStoricoFascicoloMenuItem);
			}
		}
		
		if (detailRecord.getAttributeAsBoolean("abilOsservazioniNotifiche")) {
			MenuItem aggiungiOsservazioneNotifica = new MenuItem(I18NUtil.getMessages().osservazioniNotifiche_menu_apri_title(), "osservazioni_notifiche.png");
			aggiungiOsservazioneNotifica.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				public void onClick(MenuItemClickEvent event) {
					manageOsservazioniNotifiche(listRecord);
				}
			});
			altreOpMenu.addItem(aggiungiOsservazioneNotifica);
		}

		// TAGLIA
		MenuItem tagliaMenuItem = getTagliaMenuItem(detailRecord);
		//altreOpMenu.addItem(tagliaMenuItem);
		// INCOLLA
		MenuItem incollaMenuItem = getIncollaMenuItem(detailRecord);
		//altreOpMenu.addItem(incollaMenuItem);
		
		// Segna come visionato
		if (detailRecord.getAttributeAsBoolean("abilSetVisionato")) {
			MenuItem segnaComeVisionatoMenuItem = new MenuItem(I18NUtil.getMessages().segnaComeVisionato_menu_apri_title(), "postaElettronica/flgRicevutaLettura.png");
			segnaComeVisionatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					public void onClick(MenuItemClickEvent event) {
							segnaComeVisionato(listRecord);
					}
			});
			altreOpMenu.addItem(segnaComeVisionatoMenuItem);
		}
		
		if (layout instanceof ScrivaniaLayout) {
			// Segna come Archiviato
			if (detailRecord.getAttributeAsBoolean("abilArchiviazione")) {
				MenuItem segnaComeArchiviatoMenuItem = new MenuItem(I18NUtil.getMessages().segnaComeArchiviato_menu_apri_title(), "archivio/archiviazione.png");
				segnaComeArchiviatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					public void onClick(MenuItemClickEvent event) {
						segnaComeArchiviato(listRecord);
					}
				});
				altreOpMenu.addItem(segnaComeArchiviatoMenuItem);
			}
			
		}
		altreOpMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));

		return altreOpMenu;
	}

	public void manageApposizioneCommenti(final Record listRecord) {
		String noteOld = listRecord.getAttribute("note");
		String causaleAggNoteOld = listRecord.getAttribute("causaleAggNote");
		String title = null;
		if(listRecord.getAttribute("flgUdFolder").equals("U")){
			//Documento
			title = I18NUtil.getMessages().archivio_list_apposizioneCommentiPopupUd_title(getEstremiUdFromList(listRecord));
		}else if(listRecord.getAttribute("flgUdFolder").equals("F")){
			//Fascicolo
			title = I18NUtil.getMessages().archivio_list_apposizioneCommentiPopupFolder_title(getEstremiFolderFromList(listRecord));
		}
		new ApposizioneCommentiPopup(title, noteOld, causaleAggNoteOld) {

			@Override
			public void onClickOkButton(Record object, final DSCallback callback) {
				RecordList listaUdFolderNoteAggiornate = new RecordList();
				String noteInserita = object.getAttribute("note");
				String causaleAggNoteInserita = object.getAttribute("causaleAggNote");
				Record listRecordNoteAggiornate = new Record();
				listRecordNoteAggiornate.setAttribute("flgUdFolder", listRecord.getAttribute("flgUdFolder"));
				listRecordNoteAggiornate.setAttribute("idUdFolder", listRecord.getAttribute("idUdFolder"));
				listRecordNoteAggiornate.setAttribute("note", noteInserita);
				listRecordNoteAggiornate.setAttribute("causaleAggNote", causaleAggNoteInserita);
				listaUdFolderNoteAggiornate.add(listRecordNoteAggiornate);
				Record lRecordSelezionatiNoteAggiornate = new Record();
				lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);
				getArchivioDataSource().executecustom("apposizioneCommenti", lRecordSelezionatiNoteAggiornate, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						operationCallback(response, listRecord, "idUdFolder", "Apposizione commenti effettuata con successo",
								"Si è verificato un errore durante l'apposizione commenti!", callback);
					}
				});
			}
		};
	}

	public void manageAnnotazioniApposte(final Record listRecord, boolean isEditable) {
		String noteOld = listRecord.getAttribute("note");
		String causaleAggNoteOld = listRecord.getAttribute("causaleAggNote");
		String title = null;
		title = I18NUtil.getMessages().archivio_list_annotazioniAppostePopupFolder_title();
		AnnotazioniAppostePopup annotazioniAppostePopup = new AnnotazioniAppostePopup(title, noteOld, causaleAggNoteOld, isEditable) {
			
			@Override
			public void onClickOkButton(Record object, final DSCallback callback) {
				RecordList listaUdFolderNoteAggiornate = new RecordList();
				String noteInserita = object.getAttribute("note");
				String causaleAggNoteInserita = object.getAttribute("causaleAggNote");
				Record listRecordNoteAggiornate = new Record();
				listRecordNoteAggiornate.setAttribute("flgUdFolder", listRecord.getAttribute("flgUdFolder"));
				listRecordNoteAggiornate.setAttribute("idUdFolder", listRecord.getAttribute("idUdFolder"));
				listRecordNoteAggiornate.setAttribute("note", noteInserita);
				listRecordNoteAggiornate.setAttribute("causaleAggNote", causaleAggNoteInserita);
				listaUdFolderNoteAggiornate.add(listRecordNoteAggiornate);
				Record lRecordSelezionatiNoteAggiornate = new Record();
				lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);
				getArchivioDataSource().executecustom("apposizioneCommenti", lRecordSelezionatiNoteAggiornate, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						operationCallback(response, listRecord, "idUdFolder", "Apposizione commenti effettuata con successo",
								"Si è verificato un errore durante l'apposizione commenti!", callback);
					}
				});
			}
		};
		annotazioniAppostePopup.setCanEdit(false);
	}

	public void segnaComeVisionato(final Record listRecord) {
		
		// Segna come visionato puntuale
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboUoDestNotificheDataSource", "key", FieldType.TEXT);		
		lGwtRestDataSource.addParam("flgUdFolder", listRecord.getAttribute("flgUdFolder"));
		lGwtRestDataSource.addParam("idUdFolder", listRecord.getAttribute("idUdFolder"));
		lGwtRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					
					RecordList listaUoDestNotifiche = response.getDataAsRecordList();
					
					String title = null;
					
					if(listRecord.getAttribute("flgUdFolder").equals("U")){
						//Documento
						title = I18NUtil.getMessages().segnaComeVisionato_Popup_Ud_title(getEstremiUdFromList(listRecord));
					}else if(listRecord.getAttribute("flgUdFolder").equals("F")){
						//Fascicolo
						title = I18NUtil.getMessages().segnaComeVisionato_Popup_Folder_title(getEstremiFolderFromList(listRecord));
					}
					SegnaComeVisionatoPopup segnaComeVisionatoPopup = new SegnaComeVisionatoPopup(title, null, listaUoDestNotifiche) {

						@Override
						public void onClickOkButton(Record object, final DSCallback callback) {
							RecordList listaUdFolderNoteAggiornate = new RecordList();
							String noteInserita = object.getAttribute("note"); 
							Record udFolderNoteAggiornate = new Record();
							udFolderNoteAggiornate.setAttribute("flgUdFolder", listRecord.getAttribute("flgUdFolder"));
							udFolderNoteAggiornate.setAttribute("idUdFolder", listRecord.getAttribute("idUdFolder"));
							udFolderNoteAggiornate.setAttribute("note", noteInserita);
							listaUdFolderNoteAggiornate.add(udFolderNoteAggiornate);
							Record lRecordSelezionatiNoteAggiornate = new Record();
							lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);
							lRecordSelezionatiNoteAggiornate.setAttribute("listaUoSelezionate", object.getAttributeAsRecordList("listaUoSelezionate"));											
							lRecordSelezionatiNoteAggiornate.setAttribute("flgAnchePerUtente", object.getAttributeAsRecordList("flgAnchePerUtente"));											
							getArchivioDataSource().executecustom("segnaComeVisionato", lRecordSelezionatiNoteAggiornate, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									operationCallback(response, listRecord, "idUdFolder", "Operazione effettuata con successo",
											"Si è verificato un errore durante l'aggiornamento !", callback);
								}
							});
						}
					};
					segnaComeVisionatoPopup.show();
				}
			}
		});
	}
	
	public void segnaComeArchiviato(final Record listRecord) {
		
		// Segna come archiviato puntuale
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboUoDestNotificheDataSource", "key", FieldType.TEXT);		
		lGwtRestDataSource.addParam("flgUdFolder", listRecord.getAttribute("flgUdFolder"));
		lGwtRestDataSource.addParam("idUdFolder", listRecord.getAttribute("idUdFolder"));
		final String idNodo = ((ScrivaniaLayout) layout).getIdNode();
		lGwtRestDataSource.addParam("altriParametriIn", "TIPO_AZIONE|*|A|*|CI_NODO_SCRIVANIA|*|" + idNodo);
		lGwtRestDataSource.fetchData(null, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					
					RecordList listaUoDestNotifiche = response.getDataAsRecordList();
					
					String title = null;
					
					if(listRecord.getAttribute("flgUdFolder").equals("U")){
						//Documento
						title = I18NUtil.getMessages().segnaComeArchiviato_Popup_Ud_title(getEstremiUdFromList(listRecord));
					}else if(listRecord.getAttribute("flgUdFolder").equals("F")){
						//Fascicolo
						title = I18NUtil.getMessages().segnaComeArchiviato_Popup_Folder_title(getEstremiFolderFromList(listRecord));
					}
					SegnaComeArchiviatoPopup segnaComeArchiviatoPopup = new SegnaComeArchiviatoPopup(title, null, listaUoDestNotifiche) {
						
						@Override
						public void onClickOkButton(Record object, final DSCallback callback) {
							RecordList listaUdFolderNoteAggiornate = new RecordList();
							String noteInserita = object.getAttribute("note"); 
							Record udFolderNoteAggiornate = new Record();
							udFolderNoteAggiornate.setAttribute("flgUdFolder", listRecord.getAttribute("flgUdFolder"));
							udFolderNoteAggiornate.setAttribute("idUdFolder", listRecord.getAttribute("idUdFolder"));
							udFolderNoteAggiornate.setAttribute("note", noteInserita);
							listaUdFolderNoteAggiornate.add(udFolderNoteAggiornate);
							Record lRecordSelezionatiNoteAggiornate = new Record();
							lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);
							lRecordSelezionatiNoteAggiornate.setAttribute("listaUoSelezionate", object.getAttributeAsRecordList("listaUoSelezionate"));											
							lRecordSelezionatiNoteAggiornate.setAttribute("idNodo", idNodo);	
							lRecordSelezionatiNoteAggiornate.setAttribute("flgAnchePerUtente", object.getAttributeAsRecordList("flgAnchePerUtente"));	
							getArchivioDataSource().executecustom("segnaComeArchiviato", lRecordSelezionatiNoteAggiornate, new DSCallback() {
								
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									operationCallback(response, listRecord, "idUdFolder", "Operazione effettuata con successo",
											"Si è verificato un errore durante l'aggiornamento !", callback);
								}
							});
						}
					};
					segnaComeArchiviatoPopup.show();
				}
			}
		});
	}
	
	public void operationCallback(DSResponse response, Record record, String pkField, String successMessage, String errorMessage, DSCallback callback) {
		
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			if (errorMessages != null) {
				if (errorMessages.get(record.getAttribute(pkField)) != null) {
					errorMsg = (String) errorMessages.get(record.getAttribute(pkField));
				} else {
					errorMsg = errorMessage != null ? errorMessage : "Si è verificato un errore durante l'operazione!";
				}
			}
			if (layout instanceof ArchivioLayout && (((ArchivioLayout)layout).getIdNodeToOpen() != null && !"".equals(((ArchivioLayout)layout).getIdNodeToOpen()))) {				
				layout.hideDetailAfterSave();
			} else {			
				layout.reloadListAndSetCurrentRecord(record);
			}
			Layout.hideWaitPopup();
			if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		}
	}
	
	private void operationCallbackRestituisci(DSResponse response, final Record record, DynamicForm _form, final DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			if (errorMessages != null) {
				if (errorMessages.get(record.getAttribute("idUdFolder")) != null) {
					errorMsg = (String) errorMessages.get(record.getAttribute("idUdFolder"));
				} else {
					errorMsg = "Si è verificato un errore durante la restituzione!";
				}
			}
			if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				if (data.getAttributeAsInt("flgIgnoreWarning") != 1) {
					Layout.addMessage(new MessageBean("Restituzione effettuata con successo", "", MessageType.INFO));
					layout.reloadListAndSetCurrentRecord(record);
					if (callback != null) {
						callback.execute(new DSResponse(), null, new DSRequest());
					}
				} else {
					_form.setValue("flgIgnoreWarning", "1");
				}
			}
		}
	}

	protected void manageAggiuntaFileButtonClick(ListGridRecord record) {
		layout.getDetail().markForRedraw();
		manageLoadDetail(record, getRecordIndex(record), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
					if (layout.getDetail() instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) layout.getDetail()).aggiuntaFileMode();
						layout.setReloadListCallback(mReloadListCallback);					
					}
					List<ToolStripButton> detailButtons = layout.getDetailButtons();
					if (dettaglioWindow != null && dettaglioWindow.isOpen()) {
						dettaglioWindow.updateContentDocumentWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}else {
						dettaglioWindow = new DettaglioWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}
				} else {
					layout.editMode();
					if (layout.getDetail() instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) layout.getDetail()).aggiuntaFileMode();
						layout.setReloadListCallback(mReloadListCallback);					
					}
				}
			}
		});
	}

	protected void manageModificaDatiConAggiuntaFileButtonClick(ListGridRecord record) {
		layout.getDetail().markForRedraw();
		manageLoadDetail(record, getRecordIndex(record), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
					if (layout.getDetail() instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) layout.getDetail()).modificaDatiMode(true);
					}
					List<ToolStripButton> detailButtons = layout.getDetailButtons();
					if (dettaglioWindow != null && dettaglioWindow.isOpen()) {
						dettaglioWindow.updateContentDocumentWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}else {
						dettaglioWindow = new DettaglioWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}
				} else {
					layout.editMode();
					if (layout.getDetail() instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) layout.getDetail()).modificaDatiMode(true);
					}
				}
			}
		});
	}

	protected void manageModificaDatiButtonClick(ListGridRecord record) {
		layout.getDetail().markForRedraw();
		manageLoadDetail(record, getRecordIndex(record), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
					if (layout.getDetail() instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) layout.getDetail()).modificaDatiMode(false);
						layout.setReloadListCallback(mReloadListCallback);					
					}
					List<ToolStripButton> detailButtons = layout.getDetailButtons();
					if (dettaglioWindow != null && dettaglioWindow.isOpen()) {
						dettaglioWindow.updateContentDocumentWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}else {
						dettaglioWindow = new DettaglioWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}
				} else {
					layout.editMode();
					if (layout.getDetail() instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) layout.getDetail()).modificaDatiMode(false);
						layout.setReloadListCallback(mReloadListCallback);					
					}
				}
			}
		});
	}
	
	protected void manageModificaDatiExtraIterButtonClick(ListGridRecord record) {
		layout.getDetail().markForRedraw();
		manageLoadDetail(record, getRecordIndex(record), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
					if (layout.getDetail() instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) layout.getDetail()).modificaDatiExtraIterMode();
						layout.setReloadListCallback(mReloadListCallback);					
					}
					List<ToolStripButton> detailButtons = layout.getDetailButtons();
					if (dettaglioWindow != null && dettaglioWindow.isOpen()) {
						dettaglioWindow.updateContentDocumentWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}else {
						dettaglioWindow = new DettaglioWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}
				} else {
					layout.editMode();
					if (layout.getDetail() instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) layout.getDetail()).modificaDatiExtraIterMode();
						layout.setReloadListCallback(mReloadListCallback);					
					}
				}
			}
		});
	}

	protected void manageModificaDatiRegButtonClick(ListGridRecord record) {
		layout.getDetail().markForRedraw();
		manageLoadDetail(record, getRecordIndex(record), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
					if (layout.getDetail() instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) layout.getDetail()).modificaDatiRegMode();
						layout.setReloadListCallback(mReloadListCallback);
					}
					List<ToolStripButton> detailButtons = layout.getDetailButtons();
					if (dettaglioWindow != null && dettaglioWindow.isOpen()) {
						dettaglioWindow.updateContentDocumentWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}else {
						dettaglioWindow = new DettaglioWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}
				} else {
					layout.editMode();
					if (layout.getDetail() instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) layout.getDetail()).modificaDatiRegMode();
						layout.setReloadListCallback(mReloadListCallback);
					}
				}
			}
		});
	}
		
	protected void manageRevocaAttoButtonClick(final ListGridRecord record) {
		if(AurigaLayout.isAttivaNuovaPropostaAtto2()) {
			layout.getDetail().markForRedraw();
			final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "NuovaPropostaAtto2CompletaDataSource" : "NuovaPropostaAtto2DataSource");
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
			lNuovaPropostaAtto2DataSource.getData(lRecordToLoad, new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record lRecordAtto = response.getData()[0];
						AurigaLayout.avviaRevocaAtto(lRecordAtto);
					}
				}
			});
		}
	}
	
	protected void manageProtocollazioneEntrataButtonClick(ListGridRecord record) {
		layout.getDetail().markForRedraw();
		final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSourceProtocollo.addParam("isProtocollazioneBozza", "true");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					ProtocollazioneDetailEntrata protocollazioneDetailEntrata = ProtocollazioneUtil.buildProtocollazioneDetailEntrata(lRecord);
					protocollazioneDetailEntrata.caricaDettaglioXProtBozza(layout, lRecord);
					layout.changeDetail(lGwtRestDataSourceProtocollo, protocollazioneDetailEntrata);
					layout.editMode();
					if (layout instanceof ScrivaniaLayout) {
						((ScrivaniaLayout) layout).setSaveButtonTitle("Registra");
					} else if (layout instanceof ArchivioLayout) {
						((ArchivioLayout) layout).setSaveButtonTitle("Registra");
					}
					layout.setReloadListCallback(mReloadListCallback);
				}
			}
		});
	}

	protected void manageProtocollazioneUscitaButtonClick(ListGridRecord record) {
		layout.getDetail().markForRedraw();
		final GWTRestDataSource lGwtRestDataSourceProtocollo = getProtocolloDataSource();
		lGwtRestDataSourceProtocollo.addParam("isProtocollazioneBozza", "true");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					ProtocollazioneDetailUscita protocollazioneDetailUscita = ProtocollazioneUtil.buildProtocollazioneDetailUscita(lRecord);
					protocollazioneDetailUscita.caricaDettaglioXProtBozza(layout, lRecord);
					layout.changeDetail(lGwtRestDataSourceProtocollo, protocollazioneDetailUscita);
					layout.editMode();
					if (layout instanceof ScrivaniaLayout) {
						((ScrivaniaLayout) layout).setSaveButtonTitle("Registra");
					} else if (layout instanceof ArchivioLayout) {
						((ArchivioLayout) layout).setSaveButtonTitle("Registra");
					}
					layout.setReloadListCallback(mReloadListCallback);
				}
			}
		});
	}

	protected void manageProtocollazioneInternaButtonClick(ListGridRecord record) {
		layout.getDetail().markForRedraw();
		final GWTRestDataSource lGwtRestDataSourceProtocollo = getProtocolloDataSource();
		lGwtRestDataSourceProtocollo.addParam("isProtocollazioneBozza", "true");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					ProtocollazioneDetailInterna protocollazioneDetailInterna = ProtocollazioneUtil.buildProtocollazioneDetailInterna(lRecord);
					protocollazioneDetailInterna.caricaDettaglioXProtBozza(layout, lRecord);
					layout.changeDetail(lGwtRestDataSourceProtocollo, protocollazioneDetailInterna);
					layout.editMode();
					if (layout instanceof ScrivaniaLayout) {
						((ScrivaniaLayout) layout).setSaveButtonTitle("Registra");
					} else if (layout instanceof ArchivioLayout) {
						((ArchivioLayout) layout).setSaveButtonTitle("Registra");
					}
					layout.setReloadListCallback(mReloadListCallback);
				}
			}
		});
	}
	
	protected void manageOsservazioniNotifiche(ListGridRecord record) {

		String title = null;
		if(record.getAttribute("flgUdFolder").equals("U")){
			//Documento
			title = "Osservazioni e notifiche sul documento " + getEstremiUdFromList(record);
		}else if(record.getAttribute("flgUdFolder").equals("F")){
			//Fascicolo
			title = "Osservazioni e notifiche sul fascicolo " + getEstremiFolderFromList(record);
		}
		new OsservazioniNotificheWindow(record.getAttribute("idUdFolder"), record.getAttribute("flgUdFolder"), title);
	}
	
	public void manageFirma(ListGridRecord listRecord, boolean apposizioneForzata) {
		
		final RecordList listaUd = new RecordList();
		listaUd.add(listRecord);
		manageFirmaMassiva(listaUd, apposizioneForzata, null);
	}
	
	public void manageFirmaMassiva(final RecordList listRecord, boolean apposizioneForzata, String prefTipoFirma) {

		/*
		 * listRecord è un recordList contenente tutti gli ud che sono stati selezionati 
		 * nella lista.
		 */
		if(listRecord != null){
			
			//Apro il popup per scegliere l'azione di apposizione 
			AzioneApposizionePopup popup = new AzioneApposizionePopup(listRecord, null, TipologiaApposizione.FIRMA, true,
					apposizioneForzata, prefTipoFirma, new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record response) {
					if(layout != null) {
						//Aggiorno la grafica relativa
						if (layout instanceof ScrivaniaLayout){
							((ScrivaniaLayout)layout).doSearchAfterFirma();
						}else {
							layout.doSearch();
						}
					}
				}
			});
			//Devo visualizzare il popup per inserire la motivazione nel caso in cui si rifiuti l'apposizione
			if(!apposizioneForzata){
				popup.show();
			}
		}
	}
	
	public void manageProtocollaTimbraEFirma(ListGridRecord listRecord, boolean apposizioneForzata) {
		
		final RecordList listaRecordSelezionati = new RecordList();
		listRecord.setAttribute("flgDatiCompletiPerProtocollazione", "1");
		listaRecordSelezionati.add(listRecord);
		manageProtocollaTimbraEFirmaMassivi(listaRecordSelezionati, apposizioneForzata, null);
	}
	
	public void manageProtocollaTimbraEFirmaMassivi(final RecordList listaRecordSelezionati, boolean apposizioneForzata, String prefTipoFirma) {
		
		// Mi porto dietro la mappa di tutti gli eventuali errori
		Map<String, String> mappaErrori = new HashMap<String, String>();
		// Lista dei record effettivamente da lavorare
		RecordList listaRecordDaLavorare = new RecordList();
		// Tolgo le bozze che non hanno i dati completi per la protocollazione
		for (int i = 0; i < listaRecordSelezionati.getLength(); i++) {
			Record lRecord = listaRecordSelezionati.get(i);
			String flgDatiCompletiPerProtocollazione = lRecord.getAttribute("flgDatiCompletiPerProtocollazione");
			if (flgDatiCompletiPerProtocollazione != null && "1".equalsIgnoreCase(flgDatiCompletiPerProtocollazione)) {
				listaRecordDaLavorare.add(lRecord);
			} else {
				mappaErrori.put(lRecord.getAttribute("idUdFolder"), "Dati non completi per la protocollazione: operazione di firma e protocollazione contestuale non consentita");
			}
		}
		
		// Controllo se ho record da lavorare
		if (listaRecordDaLavorare.getLength() > 0) {
			final Record recordDaProtocollare = new Record();
			recordDaProtocollare.setAttribute("listaRecord", listaRecordDaLavorare);
			DocumentDetail.avviaProtocollaTimbraEFirma(null, recordDaProtocollare, mappaErrori, new ServiceCallback<DSResponse>() {
				
				@Override
				public void execute(DSResponse response) {
					manageProtocollaTimbraEFirmaMassiviCallback(listaRecordSelezionati, response);
				}
			});
		} else {
			// Creo un DSResponse per la compatibilità delle callback
			DSResponse response = new DSResponse();
			response.setStatus(DSResponse.STATUS_SUCCESS);
			Record[] data = new Record[1];
			Record errori = new Record();
			errori.setAttribute("errorMessages", mappaErrori);
			data[0] = errori;
			response.setData(data);
			manageProtocollaTimbraEFirmaMassiviCallback(listaRecordSelezionati, response);
		}
	}
	
	private void manageProtocollaTimbraEFirmaMassiviCallback(RecordList listaRecordSelezionati, DSResponse response) {

		if (layout instanceof ScrivaniaLayout) {
			((ScrivaniaLayout) layout).massiveOperationCallback(response, listaRecordSelezionati, "idUdFolder", "segnatura",
					"Firma con segnatura di protocollo effettuata con successo", "Tutti i record selezionati per la firma con segnatura di protocollo sono andati in errore!",
					"Alcuni dei record selezionati per la firma con segnatura di protocollo sono andati in errore!", null);
		} else if (layout instanceof ArchivioLayout) {
			((ArchivioLayout) layout).massiveOperationCallback(response, listaRecordSelezionati, "idUdFolder", "segnatura",
					"Firma con segnatura di protocollo effettuata con successo", "Tutti i record selezionati per la firma con segnatura di protocollo sono andati in errore!",
					"Alcuni dei record selezionati per la firma con segnatura di protocollo sono andati in errore!", null);
		}
	}
	
	public void versaInArchivioStoricoFascicoloFromList(final Record record, final DSCallback callback) {
		versaInArchivioStoricoFascicoloFromList(getRecord(getRecordIndex(record)), callback);
	}

	public void versaInArchivioStoricoFascicoloFromList(final ListGridRecord listRecord, final DSCallback callback) {
		SC.ask("Sei sicuro di voler versare in archivio il fascicolo?", new BooleanCallback() {

			@Override
			public void execute(Boolean value) {
				if (value) {
					final RecordList listaUdFolder = new RecordList();
					listaUdFolder.add(listRecord);
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
					lGwtRestDataSource.executecustom("versaInArchivioStoricoFascicolo", record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							operationCallback(response, listRecord, "idUdFolder", "Operazione effettuata con successo", "Si è verificato un errore !", callback);
						}
					});
				}
			}
		});

	}

	public void chiudiFascicoloFromList(final Record record, final DSCallback callback) {
		chiudiFascicoloFromList(getRecord(getRecordIndex(record)), callback);
	}

	public void chiudiFascicoloFromList(final ListGridRecord listRecord, final DSCallback callback) {
		SC.ask("Sei sicuro di voler chiudere il fascicolo?", new BooleanCallback() {

			@Override
			public void execute(Boolean value) {
				if (value) {
					final RecordList listaUdFolder = new RecordList();
					listaUdFolder.add(listRecord);
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
					lGwtRestDataSource.executecustom("chiudiFascicolo", record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							operationCallback(response, listRecord, "idUdFolder", "Operazione effettuata con successo", "Si è verificato un errore !", callback);
						}
					});
				}
			}
		});

	}
	
	public void riapriFascicoloFromList(final ListGridRecord listRecord, final DSCallback callback) {
		SC.ask("Sei sicuro di voler riaprire il fascicolo?", new BooleanCallback() {

			@Override
			public void execute(Boolean value) {
				if (value) {
					final RecordList listaUdFolder = new RecordList();
					listaUdFolder.add(listRecord);
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
					lGwtRestDataSource.executecustom("riapriFascicolo", record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							operationCallback(response, listRecord, "idUdFolder", "Operazione effettuata con successo", "Si è verificato un errore !", callback);
						}
					});
				}
			}
		});

	}

	public void deleteFascicoloFromList(final Record record, final DSCallback callback) {
		deleteFascicoloFromList(getRecord(getRecordIndex(record)), callback);
	}

	public void deleteFascicoloFromList(final ListGridRecord listRecord, final DSCallback callback) {
		if (listRecord.getAttribute("nroFascicolo") != null && !"".equals(listRecord.getAttribute("nroFascicolo"))) {
			SC.ask("Sei sicuro di voler eliminare il fascicolo?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {
					if (value) {
						final RecordList listaUdFolder = new RecordList();
						listaUdFolder.add(listRecord);
						Record record = new Record();
						record.setAttribute("listaRecord", listaUdFolder);
						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("EliminazioneFascicoloDataSource");						
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(final DSResponse response, Object rawData, DSRequest request) {
								if(layout instanceof ArchivioLayout) {
									((ArchivioLayout) layout).setIdNodeToOpenByIdFolder(listRecord.getAttribute("idFolderApp"), new GenericCallback() {
										
										@Override
										public void execute() {
											operationCallback(response, listRecord, "idUdFolder", "Eliminazione del fascicolo effettuata con successo",
													"Si è verificato un errore durante l'eliminazione del fascicolo!", callback);
										}
									});
								} else {
									operationCallback(response, listRecord, "idUdFolder", "Eliminazione del fascicolo effettuata con successo",
											"Si è verificato un errore durante l'eliminazione del fascicolo!", callback);
								}
								
							}
						});
					}
				}
			});
		} else if (listRecord.getAttribute("percorsoNome") != null && !"".equals(listRecord.getAttribute("percorsoNome"))) {
			SC.ask("Sei sicuro di voler eliminare la cartella?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {
					if (value) {
						final RecordList listaUdFolder = new RecordList();
						listaUdFolder.add(listRecord);
						Record record = new Record();
						record.setAttribute("listaRecord", listaUdFolder);
						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("EliminazioneFascicoloDataSource");						
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(final DSResponse response, Object rawData, DSRequest request) {
								if(layout instanceof ArchivioLayout) {
									((ArchivioLayout) layout).setIdNodeToOpenByIdFolder(listRecord.getAttribute("idFolderApp"), new GenericCallback() {
										
										@Override
										public void execute() {
											operationCallback(response, listRecord, "idUdFolder", "Eliminazione della cartella effettuata con successo",
													"Si è verificato un errore durante l'eliminazione della cartella!", callback);
										}
									});
								} else {
									operationCallback(response, listRecord, "idUdFolder", "Eliminazione della cartella effettuata con successo",
											"Si è verificato un errore durante l'eliminazione della cartella!", callback);	
								}																
							}
						});
					}
				}
			});
		}
	}

	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
		if (layout.isLookup() && record != null) {
			if (record.getAttributeAsBoolean("flgSelXFinalita")) {
				return "font-weight:bold; color:#1D66B2";
			} else {
				return "font-weight:normal; color:gray";
			}
		}
		return super.getCellCSSText(record, rowNum, colNum);
	}

	@Override
	public void manageContextClick(final Record record) {
		if (record != null) {
			if (layout instanceof CustomAdvancedTreeLayout) {
				if ("F".equals(record.getAttributeAsString("flgUdFolder"))) {
					Record data = new Record();
					data.setAttribute("idFolder", record.getAttributeAsString("idUdFolder"));
					data.setAttribute("idNode", ((CustomAdvancedTreeLayout) layout).getNavigator().getCurrentNode().getIdNode());
					((CustomAdvancedTreeLayout) layout).getTree().getDataSource().performCustomOperation("calcolaPercorsoFromList", data, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record data = response.getData()[0];
								RecordList percorso = data.getAttributeAsRecordList("percorso");
								Record currentNode = percorso.get(percorso.getLength() - 1);
								TreeNodeBean node = new TreeNodeBean();
								node.setIdNode(currentNode.getAttributeAsString("idNode"));
								node.setNome(currentNode.getAttributeAsString("nome"));
								node.setParentId(currentNode.getAttributeAsString("parentId"));
								node.setFlgEsplodiNodo(currentNode.getAttributeAsString("flgEsplodiNodo"));
								node.setIdFolder(currentNode.getAttributeAsString("idFolder"));
								node.setIdLibreria(currentNode.getAttributeAsString("idLibreria"));
								node.setAltriAttributi(currentNode.getAttributeAsMap("altriAttributi"));
								final Menu navigationContextMenu = new NavigationContextMenu((CustomAdvancedTreeLayout) layout, node,
										NavigationContextMenuFrom.LIST_RECORD);
								Scheduler.get().scheduleDeferred(new ScheduledCommand() {

									@Override
									public void execute() {
										navigationContextMenu.showContextMenu();
										navigationContextMenu.hide();
										showRowContextMenu(getRecord(getRecordIndex(record)), navigationContextMenu);
									}
								});
							}
						}
					}, new DSRequest());
				} else {
					showRowContextMenu(getRecord(getRecordIndex(record)), null);
				}
			} else {
				showRowContextMenu(getRecord(getRecordIndex(record)), null);
			}
		}
	}

	/********************************
	 * NUOVA GESTIONE CONTROLLI BOTTONI
	 ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};

	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsFields = super.getButtonsFields();
		if (!AurigaLayout.getIsAttivaAccessibilita()) {
		if (altreOpButtonField == null) {
			altreOpButtonField = buildAltreOpButtonField();			
		}
			
		buttonsFields.add(0, altreOpButtonField);
		}
		return buttonsFields;
	}
	
	@Override
	public boolean getCanHideAltreOpButtonField() {
		// altreOpButtonField viene gestito negli showIf della scrivania quindi il canHide va impostato a true
		return true;
	}

	@Override
	protected boolean showDetailButtonField() {
		return true;
	}

	@Override
	protected boolean isRecordAbilToView(ListGridRecord record) {
		return record.getAttribute("flgUdFolder").equals("U")
				|| (record.getAttribute("nroFascicolo") != null && !"".equals(record.getAttribute("nroFascicolo")))
				|| (record.getAttribute("percorsoNome") != null && !"".equals(record.getAttribute("percorsoNome")));
	}

	@Override
	protected boolean isRecordAbilToAltreOp(ListGridRecord record) {
		return record.getAttribute("flgUdFolder").equals("U")
				|| (record.getAttribute("nroFascicolo") != null && !"".equals(record.getAttribute("nroFascicolo")))
				|| (record.getAttribute("percorsoNome") != null && !"".equals(record.getAttribute("percorsoNome")));
	}

	@Override
	protected ControlListGridField buildDetailButtonField() {
		if (fromScrivania && idNode != null && idNode.equals("D.23")) {
			ControlListGridField scaricaFileButton = new ControlListGridField("scaricaFileButton");
			scaricaFileButton.setAttribute("custom", true);
			scaricaFileButton.setShowHover(true);
			scaricaFileButton.setCanReorder(false);
			scaricaFileButton.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					return buildImgButtonHtml("file/download_manager.png", "Scarica file");
				}
			});
			scaricaFileButton.setHoverCustomizer(new HoverCustomizer() {

				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					return "Scarica file";
				}
			});
			scaricaFileButton.addRecordClickHandler(new RecordClickHandler() {

				@Override
				public void onRecordClick(RecordClickEvent event) {
					event.cancel();
					ListGridRecord record = getRecord(event.getRecordNum());
					scaricaFilePrimarioOnClick(record);
				}
			});
			return scaricaFileButton;
		} else if (fromScrivania && idNode != null && (idNode.equals("D.PROTNOSCAN") || idNode.equals("D.SCANNOASS"))) {
			//TODO ha senso per la sezione D.PROTNOSCAN ?
			ControlListGridField visualizzaFileButton = new ControlListGridField("visualizzaFileButton");
			visualizzaFileButton.setAttribute("custom", true);
			visualizzaFileButton.setShowHover(true);
			visualizzaFileButton.setCanReorder(false);
			visualizzaFileButton.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					return buildImgButtonHtml("file/preview.png");
				}
			});
			visualizzaFileButton.setHoverCustomizer(new HoverCustomizer() {

				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					return "Visualizza file";
				}
			});
			visualizzaFileButton.addRecordClickHandler(new RecordClickHandler() {

				@Override
				public void onRecordClick(RecordClickEvent event) {
					event.cancel();
					ListGridRecord record = getRecord(event.getRecordNum());
					visualizzaFilePrimarioOnClick(record);
				}
			});
			return visualizzaFileButton;
		} else {
			return super.buildDetailButtonField();
		}
	}

	/********************************
	 * FINE NUOVA GESTIONE CONTROLLI BOTTONI
	 ********************************/

	protected void creaAttestato(final String idUd, final String idDoc, ListGridRecord listRecord, final InfoFileRecord infoFileAllegato, final String uriFileAllegato, final boolean attestatoFirmato) {

		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", listRecord.getAttribute("idUdFolder"));
		getProtocolloDataSource().getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Record record = response.getData()[0];
				record.setAttribute("infoFileAttach", infoFileAllegato);
				record.setAttribute("uriAttach", uriFileAllegato);
				record.setAttribute("idUd", idUd);
				record.setAttribute("idDoc", idDoc);
				record.setAttribute("attestatoFirmatoDigitalmente", attestatoFirmato);

				GWTRestDataSource lGwtRestDataSource = getArchivioDataSource();
				lGwtRestDataSource.extraparam.put("attestatoFirmato", Boolean.toString(attestatoFirmato));
				lGwtRestDataSource.extraparam.put("urlContext", GWT.getHostPageBaseURL());
				lGwtRestDataSource.executecustom("stampaAttestato", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record data = response.getData()[0];
						final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
						final String filename = infoFile.getCorrectFileName();
						final String uri = data.getAttribute("tempFileOut");
						if (!attestatoFirmato) {
							Record lRecord = new Record();
							lRecord.setAttribute("displayFilename", filename);
							lRecord.setAttribute("uri", uri);
							lRecord.setAttribute("sbustato", "false");
							lRecord.setAttribute("remoteUri", false);

							DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
						} else {
							FirmaUtility.firmaMultipla(uri, filename, infoFile, new FirmaCallback() {

								@Override
								public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
									Record lRecord = new Record();
									lRecord.setAttribute("displayFilename", nomeFileFirmato);
									lRecord.setAttribute("uri", uriFileFirmato);
									lRecord.setAttribute("sbustato", "false");
									lRecord.setAttribute("remoteUri", false);
									DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
								}
							});
						}
					}
				});
			}
		});
	}
	
	private String getEstremiFolderFromList(Record record){
		String estremi = "";
		if(record.getAttribute("percorsoNome") != null && !"".equals(record.getAttribute("percorsoNome"))){
			estremi = record.getAttribute("percorsoNome");
		}else{
			if (record.getAttributeAsString("annoFascicolo") != null && !"".equals(record.getAttributeAsString("annoFascicolo"))) {
				estremi += record.getAttributeAsString("annoFascicolo") + " ";
			}
			if (record.getAttributeAsString("indiceClassifica") != null && !"".equals(record.getAttributeAsString("indiceClassifica"))) {
				estremi += record.getAttributeAsString("indiceClassifica") + " ";
			}
			if (record.getAttributeAsString("nroFascicolo") != null && !"".equals(record.getAttributeAsString("nroFascicolo"))) {
				estremi += "N° " + record.getAttributeAsString("nroFascicolo");
				if (record.getAttributeAsString("nroSottofascicolo") != null && !"".equals(record.getAttributeAsString("nroSottofascicolo"))) {
					estremi += "/" + record.getAttributeAsString("nroSottofascicolo");
				}
				estremi += " ";
			}
			if (record.getAttributeAsString("nome") != null && !"".equals(record.getAttributeAsString("nome"))) {
				estremi += record.getAttributeAsString("nome");
			}
		}
		return estremi;
	}
	
	private String getEstremiUdFromList(Record record){
		return record.getAttribute("segnatura");
	}
	
	
	/**
	 * Metodo per gestire l'apposizione del visto da lista
	 * Richiamato quando si fa l'operazione massiva utilizzando il pulsante
	 * nella toolstrip ma anche quando si clicca con il destro su un record
	 * @param listRecord
	 */
	public void manageApponiVisto(RecordList listRecord, boolean apposizioneForzata) {
		/*
		 * listRecord è un recordList contenente tutti gli ud che sono stati selezionati 
		 * nella lista.
		 */
		if(listRecord != null){
			AzioneApposizionePopup popup = new AzioneApposizionePopup(listRecord, null, TipologiaApposizione.VISTO, true, apposizioneForzata,
					null, new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record response) {
					if(layout != null) {
						
						RecordList listaRecordUd = response.getAttributeAsRecordList("listaRecordUd");
								
						//Aggiorno la grafica relativa
						if (layout instanceof ScrivaniaLayout){
							
							((ScrivaniaLayout)layout).doSearchAfterApposizioneVisto();
							
						}else {
							layout.doSearch();
						}
					}
				}
			});
			//Devo visualizzare il popup per inserire la motivazione nel caso in cui si rifiuti l'apposizione
			if(!apposizioneForzata){
				popup.show();
			}
		}
	}
	
	/**
	 * Metodo che verifica se visualizzare il bottone di Sposta in Eliminati, utilizzabile in lista da Menù contestuale
	 * oppure bottone AltreOperazioni. Il comportamento di visualizzazione è speculare a quello gestito nell'azione massiva
	 * dello stesso.
	 */
	private Boolean showSpostaEliminatiButton(){
		boolean isValid = false;
		if(layout != null && layout instanceof ScrivaniaLayout && 
				((ScrivaniaLayout) layout).getCodSezione() != null && !"".equals(((ScrivaniaLayout) layout).getCodSezione())){
			isValid = Layout.isPrivilegioAttivo("ELS");
		}
		
		return isValid;
	}
	
	/**
	 * Gestione funzionalità Taglia & Incolla
	 */
	
	private MenuItem getTagliaMenuItem(final Record cutRecord) {
		MenuItem tagliaMenuItem = new MenuItem("NEW Taglia", "buttons/cut.png");
		tagliaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				if (layout != null && layout instanceof ScrivaniaLayout) {
					((ScrivaniaLayout) layout).setCutNode(cutRecord);
				}
			}
		});
		return tagliaMenuItem;
	}
	
	private MenuItem getIncollaMenuItem(final Record pasteRecord) {
		MenuItem incollaMenuItem = new MenuItem("NEW Incolla", "incolla.png");
		incollaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				if (layout != null && layout instanceof ScrivaniaLayout) {
					((ScrivaniaLayout) layout).paste(currentRecord,true);
				}
			}
		});
		return incollaMenuItem;
	}
	
	/**
	 * Metodo per la stampa delle etichette in fase di post-assegnazione UD
	 */
	private void manageStampaEtichettaPostAssegnazione(Record listRecord, Record detailRecord) {
		
		String codSupportoOrig = detailRecord != null && detailRecord.getAttributeAsString("codSupportoOrig") != null ? 
				detailRecord.getAttributeAsString("codSupportoOrig") : null;
		String segnaturaXOrd = listRecord != null && listRecord.getAttributeAsString("segnaturaXOrd") != null ? 
				listRecord.getAttributeAsString("segnaturaXOrd") : null;
		
		if( (codSupportoOrig != null && "C".equals(codSupportoOrig)) &&
			(segnaturaXOrd != null && (segnaturaXOrd.startsWith("1-") || segnaturaXOrd.startsWith("2-"))) &&
			AurigaLayout.getParametroDBAsBoolean("ATTIVA_STAMPA_AUTO_ETICH_POST_ASS") &&
			AurigaLayout.getImpostazioneStampaAsBoolean("stampaEtichettaAutoReg") ){
				
			final Record recordToPrint = new Record();
			recordToPrint.setAttribute("idUd", detailRecord.getAttribute("idUd"));
			recordToPrint.setAttribute("listaAllegati", detailRecord.getAttributeAsRecordList("listaAllegati"));
			recordToPrint.setAttribute("idDoc", detailRecord.getAttribute("idDocPrimario"));
			if(AurigaLayout.getImpostazioneStampaAsBoolean("skipSceltaOpzStampa")){
								
				/**
				 * Viene verificato che sia stata selezionata una stampante in precedenza
				 */
				if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null && 
						!"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
					buildStampaEtichettaAutoPostAss(recordToPrint, null);
				} else {
					PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {

						@Override
						public void execute(String nomeStampante) {
							recordToPrint.setAttribute("nomeStampante", nomeStampante);
							buildStampaEtichettaAutoPostAss(recordToPrint,nomeStampante);
						}
					}, new PrinterScannerCallback() {
						
						@Override
						public void execute(String nomeStampante) {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().protocollazione_detail_stampaEtichettaPostRegSenzaOpzStampa_errorMessage(),
									"", MessageType.ERROR));
						}
					});
				}	
			} else {
				recordToPrint.setAttribute("flgHideBarcode", true);
				StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(recordToPrint);
				stampaEtichettaPopup.show();
			}
		}
	}
	
	/**
	 * Stampa dell'etichetta post-assegnazione
	 */
	private void buildStampaEtichettaAutoPostAss(Record record, String nomeStampante) {
		
		Layout.showWaitPopup("Stampa etichetta in corso...");
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttribute("idUd"));
		lRecord.setAttribute("listaAllegati", record.getAttributeAsRecordList("listaAllegati"));
		if(nomeStampante == null || "".equals(nomeStampante)) {
			nomeStampante = AurigaLayout.getImpostazioneStampa("stampanteEtichette");
		}
		lRecord.setAttribute("nomeStampante", nomeStampante);
		lRecord.setAttribute("nroEtichette",  "1");		
		lRecord.setAttribute("flgPrimario", AurigaLayout.getImpostazioneStampaAsBoolean("flgPrimario"));
		lRecord.setAttribute("flgAllegati", AurigaLayout.getImpostazioneStampaAsBoolean("flgAllegati"));
		lRecord.setAttribute("flgRicevutaXMittente", AurigaLayout.getImpostazioneStampaAsBoolean("flgRicevutaXMittente"));
		lRecord.setAttribute("flgHideBarcode", true);
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ETICHETTA_ORIG_COPIA")){			
			lRecord.setAttribute("notazioneCopiaOriginale",  AurigaLayout.getImpostazioneStampa("notazioneCopiaOriginale"));
		}
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PreparaValoriEtichettaDatasource");
		lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				Layout.hideWaitPopup();
				final String nomeStampante = object.getAttribute("nomeStampante");
				final Record[] etichette = object.getAttributeAsRecordArray("etichette");
				final String numCopie = object.getAttribute("nroEtichette");
				StampaEtichettaUtility.stampaEtichetta("", "", nomeStampante, "", etichette, numCopie, new StampaEtichettaCallback() {

					@Override
					public void execute() {
					
					}
				});
			}

			@Override
			public void manageError() {
				Layout.hideWaitPopup();
				Layout.addMessage(new MessageBean("Impossibile stampare l'etichetta", "", MessageType.ERROR));
			}
		});
	}
	
	
	
	private void buildFilePrimarioMenuItem(final ListGridRecord listRecord, final Record detailRecord, MenuItem filePrimarioMenuItem, final boolean fileIntegrale) {
		Menu operazioniFilePrimarioSubmenu = new Menu();
		InfoFileRecord lInfoFileRecord;

		//file primario integrale
		if(fileIntegrale){
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(detailRecord.getAttributeAsObject("infoFile"));
		}
		//file primario omissis
		else {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(detailRecord.getAttributeAsRecord("filePrimarioOmissis").getAttributeAsObject("infoFile"));
		}
		
		MenuItem visualizzaFilePrimarioMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(), "file/preview.png");
		visualizzaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				if(fileIntegrale) {
					String idUd = detailRecord.getAttributeAsString("idUd");
					// String idDoc = (detailRecord.getAttributeAsString("idDocPrimario") != null && !"".equalsIgnoreCase(detailRecord.getAttributeAsString("idDocPrimario")) ? detailRecord.getAttributeAsString("idDocPrimario") : listRecord.getAttributeAsString("idDocPrimario"));
					String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
					String display = detailRecord.getAttributeAsString("nomeFilePrimario");
					String uri = detailRecord.getAttributeAsString("uriFilePrimario");
					String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
					Object infoFile = detailRecord.getAttributeAsObject("infoFile");
					visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
				}
				else {
					String idUd = detailRecord.getAttributeAsString("idUd");
					final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
					String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
					String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
					String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
					String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
					Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
					visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
				}
			}
		});
		visualizzaFilePrimarioMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
		operazioniFilePrimarioSubmenu.addItem(visualizzaFilePrimarioMenuItem);
		if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")) {
			MenuItem visualizzaEditFilePrimarioMenuItem = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(),
					"file/previewEdit.png");
			visualizzaEditFilePrimarioMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							if(fileIntegrale) {
								String idUd = detailRecord.getAttributeAsString("idUd");
								String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
								// String idDoc = (detailRecord.getAttributeAsString("idDocPrimario") != null && !"".equalsIgnoreCase(detailRecord.getAttributeAsString("idDocPrimario")) ? detailRecord.getAttributeAsString("idDocPrimario") : listRecord.getAttributeAsString("idDocPrimario"));
								String display = detailRecord.getAttributeAsString("nomeFilePrimario");
								String uri = detailRecord.getAttributeAsString("uriFilePrimario");
								String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
								Object infoFile = detailRecord.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
							else {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
								String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
								String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
								String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
								String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
								Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
						}
					});
			visualizzaEditFilePrimarioMenuItem
					.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			operazioniFilePrimarioSubmenu.addItem(visualizzaEditFilePrimarioMenuItem);
		}
		MenuItem scaricaFilePrimarioMenuItem = new MenuItem("Scarica", "file/download_manager.png");
		// Se è firmato P7M
		if (lInfoFileRecord != null && lInfoFileRecord.isFirmato()
				&& lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
			Menu scaricaFilePrimarioSubMenu = new Menu();
			MenuItem firmato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
			firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						// String idDoc = (detailRecord.getAttributeAsString("idDocPrimario") != null && !"".equalsIgnoreCase(detailRecord.getAttributeAsString("idDocPrimario")) ? detailRecord.getAttributeAsString("idDocPrimario") : listRecord.getAttributeAsString("idDocPrimario"));
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
				}
			});
			MenuItem sbustato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
			sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						// String idDoc = (detailRecord.getAttributeAsString("idDocPrimario") != null && !"".equalsIgnoreCase(detailRecord.getAttributeAsString("idDocPrimario")) ? detailRecord.getAttributeAsString("idDocPrimario") : listRecord.getAttributeAsString("idDocPrimario"));
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						Object infoFile = detailRecord.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
				}
			});
			scaricaFilePrimarioSubMenu.setItems(firmato, sbustato);
			scaricaFilePrimarioMenuItem.setSubmenu(scaricaFilePrimarioSubMenu);
		} else {
			scaricaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						// String idDoc = (detailRecord.getAttributeAsString("idDocPrimario") != null && !"".equalsIgnoreCase(detailRecord.getAttributeAsString("idDocPrimario")) ? detailRecord.getAttributeAsString("idDocPrimario") : listRecord.getAttributeAsString("idDocPrimario"));
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
				}
			});
		}
		operazioniFilePrimarioSubmenu.addItem(scaricaFilePrimarioMenuItem);

//		if(listRecord != null && listRecord.getAttributeAsString("segnaturaXOrd") != null && !"".equalsIgnoreCase(listRecord.getAttributeAsString("segnaturaXOrd"))
//				&& ("PG".equalsIgnoreCase(listRecord.getAttributeAsString("segnaturaXOrd")) ||
//					"R".equalsIgnoreCase(listRecord.getAttributeAsString("segnaturaXOrd")) ||
//					"PP".equalsIgnoreCase(listRecord.getAttributeAsString("segnaturaXOrd")))) {
			if(showOperazioniTimbratura(detailRecord)) {
				buildTimbraButtons(listRecord, detailRecord, lInfoFileRecord, operazioniFilePrimarioSubmenu, fileIntegrale);
		}	
			
		if (lInfoFileRecord != null && Layout.isPrivilegioAttivo("SCC")) {
				String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
				MenuItem timbroConformitaCustomAllegatoMenuItem = new MenuItem(labelConformitaCustom, "file/copiaConformeCustom.png");
				timbroConformitaCustomAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
				timbroConformitaCustomAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								detailRecord.setAttribute("finalita", "COPIA_CONFORME_CUSTOM");
								timbraFilePrimario(detailRecord, fileIntegrale);
							}
						});

				operazioniFilePrimarioSubmenu.addItem(timbroConformitaCustomAllegatoMenuItem);

			}	

		// Attestato conformità all’originale
		MenuItem attestatoConformitaOriginaleMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_attestatoConformitaMenuItem(), "file/attestato.png");
		attestatoConformitaOriginaleMenuItem
				.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final InfoFileRecord file;
						final String uri;
						final String idDoc;
						final String idUd = detailRecord.getAttributeAsString("idUd");

						if (fileIntegrale) {
							file = InfoFileRecord
									.buildInfoFileRecord(detailRecord.getAttributeAsObject("infoFile"));
							uri = detailRecord.getAttributeAsString("uriFilePrimario");
							idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						} else {
							final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
							file = InfoFileRecord
									.buildInfoFileRecord(filePrimarioOmissis.getAttributeAsObject("infoFile"));
							uri = filePrimarioOmissis.getAttributeAsString("uriFile");
							idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						}
						SC.ask("Vuoi firmare digitalmente l'attestato?", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									creaAttestato(idUd, idDoc, listRecord, file, uri, true);
								} else {
									creaAttestato(idUd, idDoc, listRecord, file, uri, false);
								}
							}
						});

					}
				});
		attestatoConformitaOriginaleMenuItem
				.setEnabled(lInfoFileRecord != null);
		operazioniFilePrimarioSubmenu.addItem(attestatoConformitaOriginaleMenuItem);

		filePrimarioMenuItem.setSubmenu(operazioniFilePrimarioSubmenu);
	}
		
	
	
	private void buildAllegatiMenuItem(final ListGridRecord listRecord, final Record detailRecord, final Record allegatoRecord, MenuItem fileAllegatoMenuItem,final int nroAllegato, final boolean allegatoIntegrale) {
		Menu operazioniFileAllegatoSubmenu = new Menu();
		InfoFileRecord lInfoFileRecord;
		
		//se è un allegato integrale
		if(allegatoIntegrale) {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));
		}
		//versione con omissis
		else {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFileOmissis"));
		}
		
		MenuItem visualizzaFileAllegatoMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(), "file/preview.png");
		visualizzaFileAllegatoMenuItem
				.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						//se è un allegato integrale
						if(allegatoIntegrale) {
							String idUd = detailRecord.getAttributeAsString("idUd");
							final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
							String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
							String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
							String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
							String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
							Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
							visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
						}
						//versione con omissis
						else{
							String idUd = detailRecord.getAttributeAsString("idUd");
							final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
							String idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
							String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
							String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
							String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
							Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
							visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
						}
					}
				});
		visualizzaFileAllegatoMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
		operazioniFileAllegatoSubmenu.addItem(visualizzaFileAllegatoMenuItem);
		if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")) {
			MenuItem visualizzaEditFileAllegatoMenuItem = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(), "file/previewEdit.png");
			visualizzaEditFileAllegatoMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							// se è un allegato integrale
							if (allegatoIntegrale) {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati")
										.get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
								String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
								Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
							// versione con omissis
							else {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati")
										.get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
								String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
								String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
								Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
						}
					});
			visualizzaEditFileAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			operazioniFileAllegatoSubmenu.addItem(visualizzaEditFileAllegatoMenuItem);
		}
		MenuItem scaricaFileAllegatoMenuItem = new MenuItem("Scarica", "file/download_manager.png");
		
		// Se è firmato P7M
		if (lInfoFileRecord != null && lInfoFileRecord.isFirmato()
				&& lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
			Menu scaricaFileAllegatoSubMenu = new Menu();
			MenuItem firmato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
			firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					//se è un allegato integrale
					if(allegatoIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
						String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					//versione con omissis
					else{
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
						String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
						String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					
				}
			});
			MenuItem sbustato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
			sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					//se è un allegato integrale
					if(allegatoIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
						String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
						Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
					//versione con omissis
					else{
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
						String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
						String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
						Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
				}
			});
			scaricaFileAllegatoSubMenu.setItems(firmato, sbustato);
			scaricaFileAllegatoMenuItem.setSubmenu(scaricaFileAllegatoSubMenu);
		} else {
			scaricaFileAllegatoMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							if(allegatoIntegrale) {
								//se è un allegato integrale
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
								String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
								scaricaFile(idUd, idDoc, display, uri, remoteUri);
							}
							//versione con omissis
							else{
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
								String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
								String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
								scaricaFile(idUd, idDoc, display, uri, remoteUri);
							}
						}
					});
		}

		operazioniFileAllegatoSubmenu.addItem(scaricaFileAllegatoMenuItem);

		if(showOperazioniTimbratura(detailRecord)) {
			buildTimbraAllegato(listRecord, detailRecord, nroAllegato, operazioniFileAllegatoSubmenu, lInfoFileRecord, allegatoIntegrale);
		}
		
		if (lInfoFileRecord != null && Layout.isPrivilegioAttivo("SCC")) {
			String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
			MenuItem timbroConformitaCustomAllegatoMenuItem = new MenuItem(labelConformitaCustom, "file/copiaConformeCustom.png");
			timbroConformitaCustomAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			timbroConformitaCustomAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "COPIA_CONFORME_CUSTOM");
							timbraFileAllegato(detailRecord, nroAllegato, allegatoIntegrale);
						}
					});

			operazioniFileAllegatoSubmenu.addItem(timbroConformitaCustomAllegatoMenuItem);

		}

		// Attestato conformità all’originale
		MenuItem attestatoConformitaOriginaleMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_attestatoConformitaMenuItem(),
				"file/attestato.png");
		attestatoConformitaOriginaleMenuItem
				.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final InfoFileRecord fileAllegato;
						final String uri;
						final String idUd = detailRecord.getAttributeAsString("idUd");;
						final String idDoc;
						
						//se è un allegato integrale
						if(allegatoIntegrale) {
						
						fileAllegato = InfoFileRecord
								.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));
						uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
							idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						}
						//versione con omissis
						else {
							fileAllegato = InfoFileRecord
									.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFileOmissis"));
							uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
							idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
						}
						SC.ask("Vuoi firmare digitalmente l'attestato?", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									creaAttestato(idUd, idDoc, listRecord, fileAllegato, uri, true);
								} else {
									creaAttestato(idUd, idDoc, listRecord, fileAllegato, uri, false);
								}
							}
						});
					}
				});
		attestatoConformitaOriginaleMenuItem.setEnabled(lInfoFileRecord != null);
		operazioniFileAllegatoSubmenu.addItem(attestatoConformitaOriginaleMenuItem);
		fileAllegatoMenuItem.setSubmenu(operazioniFileAllegatoSubmenu);
	}
	
	private void clickModificaTipologia(final Record listRecord,Record detailRecord) {

		if(listRecord.getAttribute("flgUdFolder").equalsIgnoreCase("F")) {

			final String descTipo = listRecord.getAttribute("tipo");

			Record record = new Record();
			record.setAttribute("idClassifica", listRecord.getAttribute("idClassifica"));
			record.setAttribute("idFolderApp", listRecord.getAttribute("idFolderApp"));
			record.setAttribute("idFolderType", listRecord.getAttribute("idFolderType"));

			new SceltaTipoFolderPopup(false, null, descTipo, record,
				new ServiceCallback<Record>() {

					@Override
					public void execute(Record lRecordTipoDoc) {

						String idFolderType = lRecordTipoDoc.getAttribute("idFolderType");

						if (!descTipo.equals(idFolderType)) {
							final GWTRestDataSource lGwtRestDataSource = getArchivioDataSource();
							lGwtRestDataSource.extraparam.put("idFolderType", idFolderType);
							lGwtRestDataSource.executecustom("modificaTipologia", listRecord, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									operationCallback(response, listRecord, "idUdFolder",
											"Operazione effettuata con successo",
											"Il record selezionato e' andato in errore!", null);
								}
							});
						}
					}
				});
		} else {

			final String descTipoDocumento = listRecord.getAttribute("tipo");
			new SceltaTipoDocPopup(false, null, descTipoDocumento, null, null, "|*|FINALITA|*|ASS_TIPOLOGIA", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecordTipoDoc) {

					String tipoDocumento = lRecordTipoDoc.getAttribute("idTipoDocumento");

					if (!descTipoDocumento.equals(tipoDocumento)) {

						final GWTRestDataSource lGwtRestDataSource = getArchivioDataSource();
						lGwtRestDataSource.extraparam.put("tipoDocumento", tipoDocumento);
						lGwtRestDataSource.executecustom("modificaTipologia", listRecord, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								operationCallback(response, listRecord, "idUdFolder",
										"Operazione effettuata con successo",
										"Il record selezionato e' andato in errore!", null);
							}
						});

					}
				}
			});
		}
		
		layout.setReloadListCallback(mReloadListCallback);
	}
	
	private void clickInvioAlProtocolloRapido(final Record listRecord, final String idUo,
			final String flgUdFolder, final String messaggioInvio, final DSCallback callback) {
	
		final RecordList listaUdFolder = new RecordList();
		
		if(listRecord.getAttributeAsString("idUdFolder") == null ||
				"".equals(listRecord.getAttributeAsString("idUdFolder"))){
			listRecord.setAttribute("idUdFolder", listRecord.getAttribute("idUdFolder"));
			listRecord.setAttribute("flgUdFolder", flgUdFolder);
		}
		listaUdFolder.add(listRecord);
		RecordList listaAssegnazioni = new RecordList();
		Record recordAssegnazioni = new Record();
		recordAssegnazioni.setAttribute("idUo", idUo);
		recordAssegnazioni.setAttribute("typeNodo","UO");
		listaAssegnazioni.add(recordAssegnazioni);
		
		final Record record = new Record();
		record.setAttribute("flgUdFolder", flgUdFolder);
		record.setAttribute("messaggioInvio", messaggioInvio);
		record.setAttribute("motivoInvio", "PP");
		record.setAttribute("listaRecord", listaUdFolder);
		record.setAttribute("listaAssegnazioni", listaAssegnazioni);
		
		Layout.showWaitPopup("Invio al protocollo in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
		try {
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					operationCallback(response, listRecord,
							"idUdFolder", "Invio al protocollo effettuato con successo",
							"Si è verificato un errore durante l'invio al protocollo!", new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {														
							
							if(callback != null){
								callback.execute(response, rawData, request);
							}
							layout.doSearch();
						}
					});
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}				
	}
	
	private void clickInvioAlProtocolloConMessaggio(final Record listRecord, final String idUo, 
			final String flgUdFolder, final String messaggioInvio, final DSCallback callback) {
	
		final RecordList listaUdFolder = new RecordList();
		
		if(listRecord.getAttributeAsString("idUdFolder") == null ||
				"".equals(listRecord.getAttributeAsString("idUdFolder"))){
			listRecord.setAttribute("idUdFolder", listRecord.getAttribute("idUdFolder"));
			listRecord.setAttribute("flgUdFolder", listRecord);
		}
		listaUdFolder.add(listRecord);
		RecordList listaAssegnazioni = new RecordList();
		Record recordAssegnazioni = new Record();
		recordAssegnazioni.setAttribute("idUo", idUo);
		recordAssegnazioni.setAttribute("typeNodo","UO");
		listaAssegnazioni.add(recordAssegnazioni);
		 
		final Record record = new Record();
		record.setAttribute("flgUdFolder", flgUdFolder);
		record.setAttribute("messaggioInvio", messaggioInvio);
		record.setAttribute("motivoInvio", "PP");
		record.setAttribute("listaRecord", listaUdFolder);
		record.setAttribute("listaAssegnazioni", listaAssegnazioni);
		
		Layout.showWaitPopup("Invio al protocollo in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
		try {
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					operationCallback(response, listRecord,
							"idUdFolder", "Invio al protocollo effettuato con successo",
							"Si è verificato un errore durante l'invio al protocollo!", new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {														
							
							if(callback != null){
								callback.execute(response, rawData, request);
							}
							layout.doSearch();
						}
					});
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}				
	}
	
	protected String buildLinkText(String text) {
		return "<div style=\"cursor:pointer\"><b><u>" + text + "</u></b></div>";		
	}
	
	@Override
	protected void manageDetailButtonClick(ListGridRecord record) {
		if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
			detailClick(record, getRecordIndex(record)); 
		} else {
			super.detailClick(record, getRecordIndex(record));
		}
	}
	
	protected void detailClick(final Record record, final int recordNum) {
		if(layout != null) {
			layout.getDetail().markForRedraw();
			manageLoadDetail(record, recordNum, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					layout.viewMode();	
					List<ToolStripButton> detailButtons = layout.getDetailButtons();
					if (dettaglioWindow != null && dettaglioWindow.isOpen()) {
						dettaglioWindow.updateContentDocumentWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}else {
						dettaglioWindow = new DettaglioWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}
					
				}
			}); 
		}
	}
	
	private void manageLoadDetailAcc (final Record record, final int recordNum, final DSCallback callback) {
		if (isActiveModal) {
			Layout.showWaitPopup(I18NUtil.getMessages().archivio_detail_caricamento_dettaglio_documento());
		}
		if (record.getAttribute("flgUdFolder").equals("U")) {
			final GWTRestDataSource lGwtRestDataSource = getProtocolloDataSource();
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
			lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record lRecord = response.getData()[0];	
						boolean flgRichiestaAccessoAtti = lRecord.getAttributeAsBoolean("flgRichiestaAccessoAtti") != null && lRecord.getAttributeAsBoolean("flgRichiestaAccessoAtti");
						String idProcess = lRecord.getAttribute("idProcess") != null && !"".equalsIgnoreCase(lRecord.getAttribute("idProcess")) ? lRecord.getAttribute("idProcess") : null;
						if (flgRichiestaAccessoAtti && idProcess == null) {
							RichiestaAccessoAttiDetail richiestaAccessoAttiDetail = new RichiestaAccessoAttiDetail("richiesta_accesso_atti") {
								@Override
								public boolean showDetailToolStrip() {
									return false;
								}
							};							
							richiestaAccessoAttiDetail.editRecord(lRecord);
							layout.changeDetail(lGwtRestDataSource, richiestaAccessoAttiDetail);
						} else {
							ProtocollazioneDetail  protocollazioneDetail = ProtocollazioneDetail.getInstance(lRecord);
							protocollazioneDetail.caricaDettaglio(layout, lRecord);
							layout.changeDetail(lGwtRestDataSource, protocollazioneDetail);
						}						
						callback.execute(response, null, new DSRequest());
					}
				}
			});

		} else {			
			final GWTRestDataSource lGwtRestDataSource = getArchivioDataSource();
			lGwtRestDataSource.getData(record, new DSCallback() {

				@Override
				public void execute(final DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record detailRecord = response.getData()[0];						
						if (detailRecord.getAttributeAsBoolean("flgFascTitolario") != null && !detailRecord.getAttributeAsBoolean("flgFascTitolario")) {
							boolean flgCaricamentoPregressoDaGUI = detailRecord.getAttributeAsBoolean("flgCaricamentoPregressoDaGUI") != null && detailRecord.getAttributeAsBoolean("flgCaricamentoPregressoDaGUI");
							// Pratica pregressa caricato da GUI
							if(flgCaricamentoPregressoDaGUI) {
								layout.changeDetail(lGwtRestDataSource, new PraticaPregressaDetail("archivio"));
							} else {
								layout.changeDetail(lGwtRestDataSource, new FolderCustomDetail("archivio"));
							}
						} else {
							layout.changeDetail(lGwtRestDataSource, new ArchivioDetail("archivio"));
						}						
						layout.getDetail().editRecord(detailRecord, recordNum);
						layout.getDetail().getValuesManager().clearErrors(true);
						if (layout instanceof ArchivioLayout) {
							((ArchivioLayout) layout).setIdNodeToOpenByIdFolder(record.getAttribute("idFolderApp"), new GenericCallback() {
								
								@Override
								public void execute() {									
									callback.execute(response, null, new DSRequest());
								}
							});
						} else {
							callback.execute(response, null, new DSRequest());
						}
					}
				}
			});
		}
	}
	
	private void manageLoadDetailNoAcc (final Record record, final int recordNum, final DSCallback callback) {
		if (record.getAttribute("flgUdFolder").equals("U")) {
			final GWTRestDataSource lGwtRestDataSource = getProtocolloDataSource();
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
			// lRecordToLoad.setAttribute("segnatura", record.getAttribute("segnatura"));
			lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record lRecord = response.getData()[0];	
						boolean flgRichiestaAccessoAtti = lRecord.getAttributeAsBoolean("flgRichiestaAccessoAtti") != null && lRecord.getAttributeAsBoolean("flgRichiestaAccessoAtti");
						String idProcess = lRecord.getAttribute("idProcess") != null && !"".equalsIgnoreCase(lRecord.getAttribute("idProcess")) ? lRecord.getAttribute("idProcess") : null;
						if (flgRichiestaAccessoAtti && idProcess == null) {
							RichiestaAccessoAttiDetail richiestaAccessoAttiDetail = new RichiestaAccessoAttiDetail("richiesta_accesso_atti") {
								@Override
								public boolean showDetailToolStrip() {
									return false;
								}
							};							
							richiestaAccessoAttiDetail.editRecord(lRecord);
							layout.changeDetail(lGwtRestDataSource, richiestaAccessoAttiDetail);
						} else {
							ProtocollazioneDetail  protocollazioneDetail = ProtocollazioneDetail.getInstance(lRecord);
							protocollazioneDetail.caricaDettaglio(layout, lRecord);
							layout.changeDetail(lGwtRestDataSource, protocollazioneDetail);
						}						
						callback.execute(response, null, new DSRequest());
					}
				}
			});

		} else {			
			final GWTRestDataSource lGwtRestDataSource = getArchivioDataSource();
			lGwtRestDataSource.getData(record, new DSCallback() {

				@Override
				public void execute(final DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record detailRecord = response.getData()[0];						
						if (detailRecord.getAttributeAsBoolean("flgFascTitolario") != null && !detailRecord.getAttributeAsBoolean("flgFascTitolario")) {
							boolean flgCaricamentoPregressoDaGUI = detailRecord.getAttributeAsBoolean("flgCaricamentoPregressoDaGUI") != null && detailRecord.getAttributeAsBoolean("flgCaricamentoPregressoDaGUI");
							// Pratica pregressa caricato da GUI
							if(flgCaricamentoPregressoDaGUI) {
								layout.changeDetail(lGwtRestDataSource, new PraticaPregressaDetail("archivio"));
							} else {
								layout.changeDetail(lGwtRestDataSource, new FolderCustomDetail("archivio"));
							}
						} else {
							layout.changeDetail(lGwtRestDataSource, new ArchivioDetail("archivio"));
						}						
						layout.getDetail().editRecord(detailRecord, recordNum);
						layout.getDetail().getValuesManager().clearErrors(true);
						if (layout instanceof ArchivioLayout) {
							((ArchivioLayout) layout).setIdNodeToOpenByIdFolder(record.getAttribute("idFolderApp"), new GenericCallback() {
								
								@Override
								public void execute() {									
									callback.execute(response, null, new DSRequest());
								}
							});
						} else {
							callback.execute(response, null, new DSRequest());
						}
					}
				}
			});
		}
	}

	public void apriDettaglioProcesso(final String idProcessIn, final String ruoloSmistamentoIn, final String idUdIn , final String estremiProcessIn, final boolean abilitaSmistamentoIn) {		
		if (idProcessIn != null) {			
			List<CustomTaskButton> customButtons = new ArrayList<CustomTaskButton>();			
			if(abilitaSmistamentoIn && AttiLayout.isAttivoSmistamentoAtti()) {		
				final CustomTaskButton buttonSmistaAtto = new CustomTaskButton("Smista", "pratiche/task/buttons/smista_atto.png") {

					public boolean isToShow(Record recordEvento) {
						Boolean flgAttivaSmistamento = recordEvento != null ? recordEvento.getAttributeAsBoolean("flgAttivaSmistamento") : null;
						return flgAttivaSmistamento != null && flgAttivaSmistamento;
					}
				};
				buttonSmistaAtto.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						final Record recordAtto = new Record();
						recordAtto.setAttribute("idProcedimento", idProcessIn);
						recordAtto.setAttribute("ruoloSmistamento", ruoloSmistamentoIn);
						recordAtto.setAttribute("unitaDocumentariaId", idUdIn);
						if(buttonSmistaAtto.getTaskDetail() != null && buttonSmistaAtto.getTaskDetail() instanceof TaskNuovaPropostaAtto2CompletaDetail) {
							((TaskNuovaPropostaAtto2CompletaDetail) buttonSmistaAtto.getTaskDetail()).salvaBeforeSmistaAtto(new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									AurigaLayout.smistaAtto(layout, recordAtto, new ServiceCallback<Record>() {

										@Override
										public void execute(Record object) {
											((TaskNuovaPropostaAtto2CompletaDetail) buttonSmistaAtto.getTaskDetail()).chiudiAfterSmistaAtto();
										}										
									});								
								}
							});
						} else {
							AurigaLayout.smistaAtto(layout, recordAtto, null);
						}
					}
				});
				customButtons.add(buttonSmistaAtto);
			}
			AurigaLayout.apriDettaglioPratica(idProcessIn, estremiProcessIn, customButtons, null);					
		}
	}	
	
	@Override
	protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		if (getFieldName(colNum).equals("segnaturaXOrd")) {
			return it.eng.utility.Styles.cellTextBlueClickable;
		}
		return super.getBaseStyle(record, rowNum, colNum);
	}
	
	public GWTRestDataSource getProtocolloDataSource() {
		GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource", "idUd", FieldType.TEXT);
		lGwtRestDataSourceProtocollo.addParam("idNode", idNode);
		return lGwtRestDataSourceProtocollo;
	}
	
	public GWTRestDataSource getAbilitazioniProtocolloDataSource() {
		GWTRestDataSource lGwtRestDataSourceProtocollo = getProtocolloDataSource();
		lGwtRestDataSourceProtocollo.addParam("flgSoloAbilAzioni", "1");		
		return lGwtRestDataSourceProtocollo;
	}
	
	public GWTRestDataSource getArchivioDataSource() {
		GWTRestDataSource lGwtRestDataSourceArchivio = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lGwtRestDataSourceArchivio.addParam("idNode", idNode);
		return lGwtRestDataSourceArchivio;
	}	
	
	public GWTRestDataSource getAbilitazioniArchivioDataSource() {
		GWTRestDataSource lGwtRestDataSourceArchivio = getArchivioDataSource();
		lGwtRestDataSourceArchivio.addParam("flgSoloAbilAzioni", "1");
		return lGwtRestDataSourceArchivio;
	}

	public void apriProtocolloAssociatoImg(final String listaIdUDScansioniFinal, String idUd, String nroProt,
			String annoProt, CercaUDPopup _window) {
		_window.markForDestroy();
		
		Layout.showWaitPopup("Caricamento in corso..");

		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		lRecord.setAttribute("listaIdUdScansioni", listaIdUDScansioniFinal);
		new ModificaRegProtAssociatoImgWindow(lRecord, "Dettaglio prot. N° " + nroProt + "/" + annoProt, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				if (layout != null) {
					try {
						layout.doSearch();
					} catch (Exception e) {
					}
				}
			}
		});
		
		Layout.hideWaitPopup();
	}
	
	private boolean showOperazioniTimbratura(Record detailRecord) {
		return detailRecord != null && detailRecord.getAttribute("codCategoriaProtocollo") != null && !"".equalsIgnoreCase(detailRecord.getAttribute("codCategoriaProtocollo"))
				&& ("PG".equalsIgnoreCase(detailRecord.getAttribute("codCategoriaProtocollo")) ||
						"R".equalsIgnoreCase(detailRecord.getAttribute("codCategoriaProtocollo")) ||
						"PP".equalsIgnoreCase(detailRecord.getAttribute("codCategoriaProtocollo")));
	}
}