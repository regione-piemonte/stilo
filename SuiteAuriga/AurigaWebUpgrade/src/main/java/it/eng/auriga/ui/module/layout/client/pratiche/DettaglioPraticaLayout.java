/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.DettaglioFirmeVistiDetail;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioUD.InvioUDMailWindow;
import it.eng.auriga.ui.module.layout.client.iterAtti.MessaggioUltimaAttivitaWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.BackDetailInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.DownloadInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.EditorHtmlDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.EditorHtmlFlussoDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.FirmaPropostaAtto2Detail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.FirmaPropostaAttoConFileFirmeDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.FirmaPropostaAttoDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.InvioCollegioSindacaleDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.LoadDetailInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.PropostaAtto2Detail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.PropostaAttoDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.PubblicazioneAlbo2Detail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.RenderingPdfDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.SaveDetailInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.SottomenuTaskBackDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.SottomenuTaskDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskDettFascicoloDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskDettFascicoloGenCompletoDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskDettFascicoloGenDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskDettFascicoloTSODetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskDettUdGenDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskFlussoDetailDinamico;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskFlussoInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskUnioneFileFascDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.UnioneFileAttoDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.UnioneRelataPubblicazioneDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.editorapplet.EditorAppletDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.AggiungiNoteDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.AllegaDocumentazioneBackDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.AllegaDocumentazioneDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.EventoLayoutDinamico;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.RiepilogoProcedimentoDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.items.CustomTaskButton;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.items.FrontendButton;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.TaskNuovaPropostaAtto2CompletaDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.TaskNuovaPropostaAtto2Detail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.TaskRevocaNuovaPropostaAtto2CompletaDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.TaskRevocaNuovaPropostaAtto2Detail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovarichaccessoatti.TaskNuovaRichiestaAccessoAttiDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.registrazioneatto.RegistrazioneAttoDetail;
import it.eng.auriga.ui.module.layout.client.proposteOrganigramma.TaskPropostaOrganigrammaDetail;
import it.eng.auriga.ui.module.layout.client.protocollazione.ConfrontaDocumentiLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public class DettaglioPraticaLayout extends HLayout {

	public static final String DEFAULT_NOME_MODELLO_PUBBLICAZIONE = "Versione per pubblicazione";
	public static final String DEFAULT_NOME_FILE_MODELLO_PUBBLICAZIONE = "Versione_per_pubblicazione.pdf";

	protected ModalWindow window;

	protected ValuesManager vm = new ValuesManager();

	protected DettaglioPraticaLayout instance;

	protected SectionStack sectionStack;
	protected SectionStackSection sectionStackSectionSX;
	protected SectionStackSection sectionStackSectionDX;

	protected VLayout layoutSX;
	protected VLayout layoutSXIntestazione;
	protected VLayout layoutSXDettaglioEvento;
	protected ToolStrip toolStripButtons;
	protected HLayout layoutDX;
	protected VLayout layoutDXHideShowMenu;
	protected VLayout layoutDXMenu;

	protected FrontendButton buttonIndietro;
	protected FrontendButton buttonConferma;
	protected FrontendButton buttonSalvaDefinitivoOK;
	protected FrontendButton buttonSalvaDefinitivoKO;
	protected FrontendButton buttonMessaggioTask;	
	protected FrontendButton buttonSalvaDefinitivo;
	protected FrontendButton buttonSalvaProvvisorio;
	protected FrontendButton buttonDatiStorici;	
	protected FrontendButton buttonModifica;
	protected FrontendButton buttonFirma;
	protected FrontendButton buttonScaricaFile;
	protected HLayout otherEventoButtonsLayout;
	protected FrontendButton buttonAnnulla;

	protected ImgButton buttonHideShowMenu;

	protected TabSet tabSetMenu;
	protected Tab tabGestisciIter;
	protected VLayout layoutGestisciIter;
	protected VStack menuGestisciIter;
	protected Label labelListaNextTask;
	protected Tab tabMonitoraProcedimento;
	protected VStack menuMonitoraProcedimento;

	protected String idProcess;
	protected String idTipoProc;
	protected Record recordAvvio;

	protected String intestazioneProgetto;
	protected String stato;
	protected String messaggioUltimaAttivita;

	protected CustomDetail currentDetail;

	protected Record detailRecord;

	protected Record first;
	protected String desUoProponente;
	protected String nomeResponsabileUoProponente;
	protected String rifAttoInOrganigramma;
	protected String listaNextTask;
	protected boolean abilitaCallApplTitoliEdilizi;
	
	protected Record currDettaglioEvento;
	protected Record precDettaglioEvento;

	protected LinkedHashMap<String, Record> mappaMenu;
	protected LinkedHashMap<String, RecordList> mappaSottomenuGruppi = new LinkedHashMap<String, RecordList>();
	protected LinkedHashMap<String, RecordList> mappaSottomenuAttributiComplessi = new LinkedHashMap<String, RecordList>();

	protected HashMap<String, VStack> mappaVStackSottomenu;
	protected HashMap<String, Label> mappaLabelSottomenu;

	protected LinkedHashMap<String, RecordList> mappaModelli;
	protected HashSet<String> tipiModelliAtti;

	protected String estremi;
	protected boolean skipReload;

	protected boolean saved = false;
	
	public DettaglioPraticaLayout(ModalWindow window) {

		this(null, window);

	}

	public DettaglioPraticaLayout(Record record, ModalWindow window) {

		instance = this;

		this.window = window;
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);

		setHeight100();
		setWidth100();
		setMembersMargin(5);
		setPadding(5);
		setBorder("0px solid grey");
		
		layoutSXIntestazione = new VLayout();
		layoutSXIntestazione.setOverflow(Overflow.VISIBLE);
		layoutSXIntestazione.setHeight(2);
		layoutSXIntestazione.setWidth100();

		layoutSXDettaglioEvento = new VLayout();

		buttonScaricaFile = new FrontendButton("Scarica file", "pratiche/task/buttons/scaricaPdf.png");
		buttonScaricaFile.setVisibility(Visibility.HIDDEN);
		buttonScaricaFile.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (currentDetail != null) {
					if (currentDetail instanceof DownloadInterface) {
						((DownloadInterface) currentDetail).download();
					}
				}
			}
		});
		
		otherEventoButtonsLayout = new HLayout();
		otherEventoButtonsLayout.setWidth(5);
		otherEventoButtonsLayout.setMembersMargin(2);
		otherEventoButtonsLayout.setOverflow(Overflow.VISIBLE);
		otherEventoButtonsLayout.setVisibility(Visibility.HIDDEN);
		
		buttonModifica = new FrontendButton("Modifica", "pratiche/task/buttons/modifica.png");
		buttonModifica.setVisibility(Visibility.HIDDEN);
		buttonModifica.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (currentDetail != null) {
					if (currentDetail instanceof EditorAppletDetail) {
						((EditorAppletDetail) currentDetail).modificaDocumento();
					}
					// else if(currentDetail instanceof PropostaAtto2Detail) {
					// ((PropostaAtto2Detail)currentDetail).compilaModello();
					// }
				}
			}
		});

		buttonFirma = new FrontendButton("Firma", "pratiche/task/buttons/firma.png");
		buttonFirma.setVisibility(Visibility.HIDDEN);
		buttonFirma.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (currentDetail != null) {
					if (currentDetail instanceof PropostaAtto2Detail) {
						((PropostaAtto2Detail) currentDetail).firma();
					}
				}
			}
		});

		buttonDatiStorici = new FrontendButton("Dati storici", "pratiche/task/buttons/datiStorici.png");
		buttonDatiStorici.setVisibility(Visibility.HIDDEN);
		buttonDatiStorici.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (currentDetail != null) {
					if (currentDetail instanceof PropostaAtto2Detail) {
						((PropostaAtto2Detail) currentDetail).visualizzaDatiStorici();
					}
				}
			}
		});

		buttonIndietro = new FrontendButton("Indietro", "pratiche/task/buttons/indietro.png");
		buttonIndietro.setVisibility(Visibility.HIDDEN);
		buttonIndietro.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (currentDetail != null) {
					if (currentDetail instanceof BackDetailInterface) {
						((BackDetailInterface) currentDetail).back();
					}
				}
			}
		});

		buttonAnnulla = new FrontendButton("Annulla", "pratiche/task/buttons/annulla.png");
		buttonAnnulla.setVisibility(Visibility.HIDDEN);
		buttonAnnulla.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (currentDetail != null) {
					if (currentDetail instanceof BackDetailInterface) {
						chiusuraDettaglioAtto();
						((BackDetailInterface) currentDetail).back();
					}
				}
			}
		});

		buttonConferma = new FrontendButton("Conferma", "pratiche/task/buttons/conferma.png");
		buttonConferma.setVisibility(Visibility.HIDDEN);
		buttonConferma.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (currentDetail != null) {
					if (currentDetail instanceof SaveDetailInterface) {
						((SaveDetailInterface) currentDetail).save();
					}
				}
			}
		});

		buttonSalvaProvvisorio = new FrontendButton("Salva", "pratiche/task/buttons/salva.png");
		buttonSalvaProvvisorio.setVisibility(Visibility.HIDDEN);
		buttonSalvaProvvisorio.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				buttonSalvaProvvisorio.focusAfterGroup();				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						salvaDatiProvvisorio();
					}
				});				
			}
		});

		buttonSalvaDefinitivoOK = new FrontendButton("Avanti", "pratiche/task/buttons/conferma.png");
		buttonSalvaDefinitivoOK.setVisibility(Visibility.HIDDEN);
		buttonSalvaDefinitivoOK.setShowDisabled(false);
		buttonSalvaDefinitivoOK.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				buttonSalvaDefinitivoOK.focusAfterGroup();				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						manageOnClickButtonSalvaDefinitivoOK();
					}
				});
			}
		});
		
		buttonSalvaDefinitivoKO = new FrontendButton("Indietro", "pratiche/task/buttons/confermaKO.png");
		buttonSalvaDefinitivoKO.setVisibility(Visibility.HIDDEN);
		buttonSalvaDefinitivoKO.setShowDisabled(false);
		buttonSalvaDefinitivoKO.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				buttonSalvaDefinitivoKO.focusAfterGroup();				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						manageOnClickButtonSalvaDefinitivoKO();
					}
				});
			}
		});
		
		buttonMessaggioTask = new FrontendButton("Aggiungi messaggio", "pratiche/task/buttons/aggiungi.png");
		buttonMessaggioTask.setVisibility(Visibility.HIDDEN);
		buttonMessaggioTask.setShowDisabled(false);
		buttonMessaggioTask.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				buttonMessaggioTask.focusAfterGroup();				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						manageOnClickButtonMessaggioTask(new ServiceCallback<String>() {
							
							@Override
							public void execute(String messaggio) {
								if(messaggio != null && !"".equals(messaggio.trim())) {
									buttonMessaggioTask.setTitle("Modifica messaggio");
									buttonMessaggioTask.setIcon("pratiche/task/note.png");
								} else {
									buttonMessaggioTask.setTitle("Aggiungi messaggio");
									buttonMessaggioTask.setIcon("pratiche/task/buttons/aggiungi.png");
								}
							}
						});
					}
				});
			}
		});
		
		buttonSalvaDefinitivo = new FrontendButton("Salva e procedi", "pratiche/task/buttons/conferma.png");
		buttonSalvaDefinitivo.setVisibility(Visibility.HIDDEN);
		buttonSalvaDefinitivo.setShowDisabled(false);
		buttonSalvaDefinitivo.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				buttonSalvaDefinitivo.focusAfterGroup();				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						manageOnClickButtonSalvaDefinitivo();
					}
				});
			}
		});
		
		toolStripButtons = new ToolStrip();
		toolStripButtons.setAlign(Alignment.CENTER);
		toolStripButtons.setHeight(30);
		toolStripButtons.setWidth100();
		toolStripButtons.setBackgroundColor("transparent");
		toolStripButtons.setBackgroundImage("blank.png");
		toolStripButtons.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		toolStripButtons.setBorder("0px");

		layoutSX = new VLayout();
		layoutSX.setWidth100();
//		layoutSX.setBorder("1px solid rgb(167, 171, 180)");
//		layoutSX.setPadding(5);
		layoutSX.setMembers(layoutSXIntestazione, layoutSXDettaglioEvento, toolStripButtons);

		buttonHideShowMenu = new ImgButton();
		buttonHideShowMenu.setShowDown(false);
		buttonHideShowMenu.setShowRollOver(false);
		buttonHideShowMenu.setHeight(10);
		buttonHideShowMenu.setWidth(15);
		buttonHideShowMenu.setPrompt("Nascondi menu");
		buttonHideShowMenu.setSrc("buttons/hideMenu.png");
		buttonHideShowMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (layoutDXMenu.isVisible()) {
					hideMenu();
				} else {
					showMenu();
				}
			}
		});

		layoutDXHideShowMenu = new VLayout();
		layoutDXHideShowMenu.setOverflow(Overflow.VISIBLE);
		layoutDXHideShowMenu.setWidth(2);
		layoutDXHideShowMenu.setAlign(VerticalAlignment.CENTER);
		layoutDXHideShowMenu.addMember(buttonHideShowMenu);

		tabGestisciIter = new Tab("<b>FASI E PASSI ITER</b>");
		tabMonitoraProcedimento = new Tab("<b>Monitora procedimento</b>");

		tabSetMenu = new TabSet();
		tabSetMenu.setWidth(250);
		tabSetMenu.setPadding(0);
		tabSetMenu.setMargin(0);
		tabSetMenu.setPaneMargin(0);
		tabSetMenu.setShowEdges(true);
		tabSetMenu.setEdgeShowCenter(true);
		tabSetMenu.setPaneContainerClassName(it.eng.utility.Styles.tabSetMenuNoBorder);
		tabSetMenu.setCanFocus(false);
		tabSetMenu.setTabIndex(-1);
		tabSetMenu.setTabs(tabGestisciIter/* , tabMonitoraProcedimento */);
		tabSetMenu.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Layout.showWaitPopup("Caricamento dati in corso...");
				event.cancel();
				creaMenuGestisciIter(new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						Layout.hideWaitPopup();
					}
					
				});
				
			}
		});

		layoutDXMenu = new VLayout();
		layoutDXMenu.setWidth(5);
		layoutDXMenu.setOverflow(Overflow.VISIBLE);
		layoutDXMenu.setLayoutLeftMargin(5);
		layoutDXMenu.setVisible(true);
		layoutDXMenu.addMember(tabSetMenu);

		layoutDX = new HLayout();
		layoutDX.setWidth(5);
		layoutDX.setOverflow(Overflow.VISIBLE);
		layoutDX.setMembers(layoutDXHideShowMenu, layoutDXMenu);

		sectionStackSectionSX = new SectionStackSection();
		sectionStackSectionSX.setShowHeader(false);
		sectionStackSectionSX.setExpanded(true);
		sectionStackSectionSX.setItems(layoutSX);

		sectionStackSectionDX = new SectionStackSection();
		sectionStackSectionDX.setShowHeader(false);
		sectionStackSectionDX.setExpanded(true);
		sectionStackSectionDX.setItems(layoutDX);

		sectionStack = new SectionStack();
		sectionStack.setWidth100();
		sectionStack.setHeight100();
		sectionStack.setVertical(false);
		sectionStack.setBorder("0px solid grey");
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setAnimateSections(false);
		sectionStack.setOverflow(Overflow.AUTO);
		sectionStack.setMembersMargin(0);
		sectionStack.setLayoutTopMargin(5);
		sectionStack.setCanFocus(false);
		sectionStack.setTabIndex(-1);
		sectionStack.setSections(sectionStackSectionSX, sectionStackSectionDX);

		setMembers(sectionStack);

		if (record != null) {
			caricaDettaglioPratica(record);
		}
	}
	
	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	
	public void manageOnClickButtonSalvaDefinitivoOK() {
		if (currentDetail != null && currentDetail instanceof TaskNuovaPropostaAtto2CompletaDetail) {
			((TaskNuovaPropostaAtto2CompletaDetail) currentDetail).salvaDatiDefinitivoOK();
		}
	}
	
	public void manageOnClickButtonSalvaDefinitivoKO() {
		if (currentDetail != null && currentDetail instanceof TaskNuovaPropostaAtto2CompletaDetail) {
			((TaskNuovaPropostaAtto2CompletaDetail) currentDetail).salvaDatiDefinitivoKO();
		}
	}
	
	public void manageOnClickButtonMessaggioTask(ServiceCallback<String> callback) {
		if (currentDetail != null && currentDetail instanceof TaskNuovaPropostaAtto2CompletaDetail) {
			((TaskNuovaPropostaAtto2CompletaDetail) currentDetail).aggiungiMessaggioTask(false, null, callback);
		}
	}
	
	public void manageOnClickButtonSalvaDefinitivo() {
		// in avvio bisogna evitare che l'utente clicchi più volte il tasto Avanti, creando più numerazioni proposta 
		// con il timer non andava bene, vedi l'implementazione di questo metodo nella classe AvvioPraticaLayout
//		if(currentDetail != null && (currentDetail instanceof PropostaAttoInterface) && ((PropostaAttoInterface)currentDetail).isAvvioPropostaAtto()) {
//			Timer t1 = new Timer() {
//				public void run() {
//					buttonSalvaDefinitivo.setDisabled(false);
//				}
//			};
//			if(!buttonSalvaDefinitivo.isDisabled()) {
//				buttonSalvaDefinitivo.setDisabled(true);
//				t1.schedule(10000);
//				salvaDatiDefinitivo();
//			}
//		} else {
			salvaDatiDefinitivo();
//		}
	}
	
	public void salvaDatiProvvisorio() {
		if (currentDetail != null) {
			if (currentDetail instanceof EditorHtmlFlussoDetail) {
				((EditorHtmlFlussoDetail) currentDetail).salvaDatiProvvisorio();
			} else if (currentDetail instanceof EditorAppletDetail) {
				((EditorAppletDetail) currentDetail).salvaDatiProvvisorio();
			} else if (currentDetail instanceof TaskFlussoInterface) {
				((TaskFlussoInterface) currentDetail).salvaDatiProvvisorio();
			}
		}
	}

	public void salvaDatiDefinitivo() {
		if (currentDetail != null) {
			if (currentDetail instanceof EditorHtmlFlussoDetail) {
				((EditorHtmlFlussoDetail) currentDetail).salvaDatiDefinitivo();
			} else if (currentDetail instanceof EditorAppletDetail) {
				((EditorAppletDetail) currentDetail).salvaDatiDefinitivo();
			} else if (currentDetail instanceof TaskFlussoInterface) {
				((TaskFlussoInterface) currentDetail).salvaDatiDefinitivo();
			}
		}
	}

	public void setButtons(Canvas... buttons) {
		toolStripButtons = new ToolStrip();
		toolStripButtons.setAlign(Alignment.CENTER);
		toolStripButtons.setHeight(30);
		toolStripButtons.setWidth100();
		// toolStripButtons.setBackgroundColor("transparent");
		// toolStripButtons.setBackgroundImage("blank.png");
		// toolStripButtons.setBackgroundRepeat(BkgndRepeat.REPEAT_X);
		// toolStripButtons.setBorder("0px");
		toolStripButtons.addFill();
		for (Canvas button : buttons) {
			if (button != null)
				toolStripButtons.addMember(button);
		}
		toolStripButtons.addFill();

		layoutSX.setMembers(layoutSXIntestazione, layoutSXDettaglioEvento, toolStripButtons);
	}

	public void getListaTaskProc(String idProcess, String idTipoProc, String codGruppoAtt, String idTipoEventoApp, final GetListaTaskProcCallback callback) {
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(isFrontOffice() ? "GetListaAttProcFODatasource" : "GetListaAttFlussoProcDatasource");
		if (idProcess != null && !"".equals(idProcess))
			lGwtRestDataSource.addParam("idProcess", idProcess);
		if (idTipoProc != null && !"".equals(idTipoProc))
			lGwtRestDataSource.addParam("idTipoProc", idTipoProc);
		if (codGruppoAtt != null && !"".equals(codGruppoAtt))
			lGwtRestDataSource.addParam("codGruppoAtt", codGruppoAtt);
		if (idTipoEventoApp != null && !"".equals(idTipoEventoApp))
			lGwtRestDataSource.addParam("idTipoEventoApp", idTipoEventoApp);
		lGwtRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (callback != null) {
						callback.execute(response.getDataAsRecordList());
					}
				} else {
					managePraticaConclusa();
				}
			}
		});
	}

	public void creaMenuGestisciIter(final ServiceCallback<Record> callback) {
		
		final String start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());

		getListaTaskProc(idProcess, idTipoProc, null, null, new GetListaTaskProcCallback() {

			@Override
			public void execute(RecordList data) {
				
				GWT.log("getListaTaskProc() started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));
				
				layoutGestisciIter = new VLayout(); 
				layoutGestisciIter.setOverflow(Overflow.VISIBLE);
				layoutGestisciIter.setHeight(2);
				layoutGestisciIter.setWidth100();
				menuGestisciIter = creaMenu(data);
				if(listaNextTask != null && !"".equals(listaNextTask)) {
					labelListaNextTask = new Label(listaNextTask);
					labelListaNextTask.setPadding(10);
					layoutGestisciIter.setMembers(menuGestisciIter, labelListaNextTask);
				} else {
					layoutGestisciIter.setMembers(menuGestisciIter);	
				}
				tabGestisciIter.setPane(layoutGestisciIter);
				if (callback != null) {
					callback.execute(null);
				}
			}
		});
	}

	protected void creaMenuMonitoraProcedimento() {

		menuMonitoraProcedimento = new VStack();
		menuMonitoraProcedimento.setStyleName(it.eng.utility.Styles.menuTask);
		menuMonitoraProcedimento.setWidth100();
		menuMonitoraProcedimento.setMargin(0);
		menuMonitoraProcedimento.setPadding(0);
		menuMonitoraProcedimento.setHeight(15);
		menuMonitoraProcedimento.setShowEdges(false);
		menuMonitoraProcedimento.setOverflow(Overflow.VISIBLE);
//		menuMonitoraProcedimento.setBorder("1px solid rgb(167, 171, 180)");

		Record lRiepilogoProcRecord = new Record();
		lRiepilogoProcRecord.setAttribute("nome", "Riepilogo procedimento");
		lRiepilogoProcRecord.setAttribute("idGUIEvento", "RIEPILOGO_PROCEDIMENTO");
		lRiepilogoProcRecord.setAttribute("flgDatiVisibili", "1");
		menuMonitoraProcedimento.addMember(creaVoce(lRiepilogoProcRecord));

		Record lGraficoFlussoRecord = new Record();
		lGraficoFlussoRecord.setAttribute("nome", "Grafico del flusso");
		lGraficoFlussoRecord.setAttribute("idGUIEvento", "GRAFICO_FLUSSO");
		lGraficoFlussoRecord.setAttribute("flgDatiVisibili", "1");
		menuMonitoraProcedimento.addMember(creaVoce(lGraficoFlussoRecord));

		tabMonitoraProcedimento.setPane(menuMonitoraProcedimento);
	}

	private VStack creaMenu(RecordList vociMenu) {
		
		VStack menu = new VStack();
		menu.setStyleName(it.eng.utility.Styles.menuTask);
		menu.setWidth100();
		menu.setMargin(0);
		menu.setPadding(0);
		menu.setHeight(15);
		menu.setShowEdges(false);
		menu.setOverflow(Overflow.VISIBLE);
//		menu.setBorder("1px solid rgb(167, 171, 180)");

		for (int i = 0; i < vociMenu.getLength(); i++) {
			if (i == 0) {
				first = vociMenu.get(i);
				desUoProponente = first.getAttributeAsString("desUoProponente");
				nomeResponsabileUoProponente = first.getAttributeAsString("nomeResponsabileUoProponente");
				rifAttoInOrganigramma = first.getAttributeAsString("rifAttoInOrganigramma");
				listaNextTask = first.getAttributeAsString("listaNextTask");
				abilitaCallApplTitoliEdilizi = first.getAttributeAsBoolean("abilitaCallApplTitoliEdilizi");
			}
			mappaMenu.put(vociMenu.get(i).getAttributeAsString("nome"), vociMenu.get(i));
			String tipoAttivita = vociMenu.get(i).getAttributeAsString("tipoAttivita");
			if (tipoAttivita != null && "G".equals(tipoAttivita)) {
				mappaSottomenuGruppi.put(vociMenu.get(i).getAttributeAsString("nome"), null);
			} else if (tipoAttivita != null && "AC".equals(tipoAttivita)) {
				mappaSottomenuAttributiComplessi.put(vociMenu.get(i).getAttributeAsString("nome"), null);
			}
		}
		
//		if(desUoProponente == null || "".equals(desUoProponente)) {
//			if (!ProtocollazioneDetailAtti.isAbilToSelUffPropEsteso() && AurigaLayout.getSelezioneUoProponenteAttiValueMap().size() == 1) {
//				desUoProponente = AurigaLayout.getSelezioneUoProponenteAttiValueMap().values().toArray(new String[1])[0];
//			}
//		}

		for (int i = 0; i < vociMenu.getLength(); i++) {
			String nomeAttivitaApp = vociMenu.get(i).getAttributeAsString("nomeAttivitaApp");
			String nomeGruppoApp = vociMenu.get(i).getAttributeAsString("nomeGruppoApp");
			if (nomeAttivitaApp != null && !"".equals(nomeAttivitaApp)) {
				if (mappaSottomenuAttributiComplessi.keySet().contains(nomeAttivitaApp)) {
					if (mappaSottomenuAttributiComplessi.get(nomeAttivitaApp) == null) {
						mappaSottomenuAttributiComplessi.put(nomeAttivitaApp, new RecordList());
					}
					mappaSottomenuAttributiComplessi.get(nomeAttivitaApp).add(vociMenu.get(i));
				}
			} else if (nomeGruppoApp != null && !"".equals(nomeGruppoApp)) {
				if (mappaSottomenuGruppi.keySet().contains(nomeGruppoApp)) {
					if (mappaSottomenuGruppi.get(nomeGruppoApp) == null) {
						mappaSottomenuGruppi.put(nomeGruppoApp, new RecordList());
					}
					mappaSottomenuGruppi.get(nomeGruppoApp).add(vociMenu.get(i));
				}
			}
		}

		for (String key : mappaMenu.keySet()) {
			String tipoAttivita = mappaMenu.get(key).getAttributeAsString("tipoAttivita");
			String nomeGruppoApp = mappaMenu.get(key).getAttributeAsString("nomeGruppoApp");
			if (tipoAttivita != null && "G".equals(tipoAttivita)) {
				if (mappaSottomenuGruppi.get(key) != null) {
					menu.addMember(creaVoceSottomenu(mappaMenu.get(key), mappaSottomenuGruppi.get(key)));
				} else {
					menu.addMember(creaVoce(mappaMenu.get(key)));
				}
			} else if (tipoAttivita != null && "AS".equals(tipoAttivita) && (nomeGruppoApp == null || "".equals(nomeGruppoApp))) {
				menu.addMember(creaVoce(mappaMenu.get(key)));
			}
		}

		return menu;
	}

	private Button creaVoce(final Record record) {

		final String nome = record.getAttribute("nome");
		String displayName = getDisplayNameEvento(nome);
		final String flgFatta = record.getAttribute("flgFatta");
		final String flgEsito = record.getAttribute("flgEsito");
		final String flgDatiVisibili = record.getAttribute("flgDatiVisibili");
		final String flgToDo = record.getAttribute("flgToDo");
		final String motiviNonEseg = record.getAttribute("motiviNonEseg");
		final String icona = record.getAttribute("icona");

		final Button button = new Button("<div style=\"display:table;\">"
				+ "<span style=\"display:table-cell;vertical-align:middle;\">"
				+ ((icona != null && !"".equals(icona)) ? "<img src=\"images/pratiche/task/" + icona
						+ "\" height=\"32\" width=\"32\" style=\"display:table-cell;vertical-align:middle;\"/>" : "") + "</span>"
				+ "<span style=\"display:table-cell;vertical-align:middle;\">" + "<b>&nbsp;&nbsp;&nbsp;" + displayName + "</b>" + "</span>" + "</div>");

		button.setWidth100();
		button.setHeight(36);
		button.setIcon(getIconaVoceMenu(flgFatta, flgEsito, flgToDo));
		button.setIconSize(24);
		button.setIconOrientation("right");
		button.setIconAlign("right");
		button.setBaseStyle(it.eng.utility.Styles.buttonMenu);
		button.setDisabledCursor(Cursor.DEFAULT);
		button.setDisabled(flgDatiVisibili == null || !"1".equals(flgDatiVisibili));
		button.setCanHover(true);
		button.addHoverHandler(new HoverHandler() {

			@Override
			public void onHover(HoverEvent event) {
				button.setPrompt(getPromptIconaVoceMenu(flgFatta, flgEsito, flgToDo, motiviNonEseg));
			}
		});
		if (flgDatiVisibili != null && "1".equals(flgDatiVisibili)) {
			button.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					onClickVoceMenu(record);
				}
			});
		}

		return button;
	}

	private VStack creaVoceSottomenu(final Record record, RecordList listaVociSottomenu) {

		final String nome = record.getAttribute("nome");
		String displayName = getDisplayNameEvento(nome);
		final String flgFatta = record.getAttribute("flgFatta");
		final String flgEsito = record.getAttribute("flgEsito");
		final String flgDatiVisibili = record.getAttribute("flgDatiVisibili");
		final String flgToDo = record.getAttribute("flgToDo");
		final String motiviNonEseg = record.getAttribute("motiviNonEseg");
		final String icona = record.getAttribute("icona");

		VStack lVStack = new VStack();
		lVStack.setWidth100();
		lVStack.setMargin(0);
		lVStack.setPadding(0);
		lVStack.setHeight(15);
		lVStack.setShowEdges(false);
		lVStack.setOverflow(Overflow.VISIBLE);

		final Button button = new Button("<div style=\"display:table;\">"
				+ "<span style=\"display:table-cell;vertical-align:middle;\">"
				+ ((icona != null && !"".equals(icona)) ? "<img src=\"images/pratiche/task/" + icona
						+ "\" height=\"32\" width=\"32\" style=\"display:table-cell;vertical-align:middle;\"/>" : "") + "</span>"
				+ "<span style=\"display:table-cell;vertical-align:middle;\">" + "<b>&nbsp;&nbsp;&nbsp;" + displayName + "</b>" + "</span>" + "</div>");
		button.setWidth100();
		button.setHeight(36);
		button.setIcon(getIconaVoceMenu(flgFatta, flgEsito, flgToDo));
		button.setIconSize(24);
		button.setIconOrientation("right");
		button.setIconAlign("right");
		button.setBaseStyle(it.eng.utility.Styles.buttonMenu);
		button.setDisabledCursor(Cursor.DEFAULT);
		button.setDisabled(flgDatiVisibili == null || !"1".equals(flgDatiVisibili));
		button.setCanHover(true);
		button.addHoverHandler(new HoverHandler() {

			@Override
			public void onHover(HoverEvent event) {
				button.setPrompt(getPromptIconaVoceMenu(flgFatta, flgEsito, flgToDo, motiviNonEseg));
			}
		});

		lVStack.addMember(button);

		final VStack lVStackSottomenu = new VStack();
		lVStackSottomenu.setWidth100();
		lVStackSottomenu.setMargin(0);
		lVStackSottomenu.setPadding(0);
		lVStackSottomenu.setHeight(15);
		lVStackSottomenu.setShowEdges(false);
		lVStackSottomenu.setOverflow(Overflow.VISIBLE);

		mappaVStackSottomenu.put(nome, lVStackSottomenu);

		lVStack.addMember(lVStackSottomenu);

		if (listaVociSottomenu != null) {
			for (int i = 0; i < listaVociSottomenu.getLength(); i++) {

				final Record recordSottomenu = listaVociSottomenu.get(i);
				final String nomeSottomenu = recordSottomenu.getAttribute("nome");

				final Label labelSottomenu = buildLabelSottomenu(recordSottomenu);

				mappaLabelSottomenu.put(nomeSottomenu, labelSottomenu);

				lVStackSottomenu.addMember(labelSottomenu);
			}
		}

		if (flgDatiVisibili != null && "1".equals(flgDatiVisibili)) {
			button.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					onClickVoceMenu(record);
				}
			});
		}

		return lVStack;
	}

	public void showMenu() {
		layoutDXMenu.show();
		layoutDXMenu.setLayoutLeftMargin(5);
		buttonHideShowMenu.setPrompt("Nascondi menu");
		buttonHideShowMenu.setSrc("buttons/hideMenu.png");
	}

	public void hideMenu() {
		layoutDXMenu.hide();
		layoutDXMenu.setLayoutLeftMargin(0);
		buttonHideShowMenu.setPrompt("Mostra menu");
		buttonHideShowMenu.setSrc("buttons/showMenu.png");
	}

	private Label buildLabelSottomenu(final Record recordSottomenu) {

		final String nome = recordSottomenu.getAttribute("nome");
		String title = getDisplayNameEvento(nome);
		final String flgFatta = recordSottomenu.getAttribute("flgFatta");
		final String flgEsito = recordSottomenu.getAttribute("flgEsito");
		final String flgDatiVisibili = recordSottomenu.getAttribute("flgDatiVisibili");
		final String flgToDo = recordSottomenu.getAttribute("flgToDo");
		final String motiviNonEseg = recordSottomenu.getAttribute("motiviNonEseg");
		
		final Label label = new Label(title.length() > 32 ? title.substring(0, 32) + "..." : title);
		label.setPrompt(title);
		label.setHeight(36);
		label.setIcon(getIconaVoceMenu(flgFatta, flgEsito, flgToDo));
		label.setIconSize(24);
		label.setIconOrientation("right");
		label.setIconAlign("right");
		label.setAlign(Alignment.LEFT);
		label.setDisabledCursor(Cursor.DEFAULT);
		label.setDisabled(flgDatiVisibili == null || !"1".equals(flgDatiVisibili));
		label.setCanHover(true);
		label.addHoverHandler(new HoverHandler() {

			@Override
			public void onHover(HoverEvent event) {
				label.setPrompt(getPromptIconaVoceMenu(flgFatta, flgEsito, flgToDo, motiviNonEseg));
			}
		});
		if (flgDatiVisibili != null && "1".equals(flgDatiVisibili)) {
			label.setCursor(Cursor.HAND);
			label.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					onClickVoceMenu(recordSottomenu);
				}
			});
		}
		label.setBaseStyle(label.isDisabled() ? it.eng.utility.Styles.labelMenuDisabled : it.eng.utility.Styles.labelMenu);
		return label;
	}

	public void onClickVoceMenu(Record record) {
		if(currentDetail != null && currentDetail instanceof TaskFlussoInterface) {
			// anche se sono in un passo AS eseguibile devo inibire l'azione del menu dei task a dx, come ho fatto in avvio
			Record recordEvento = ((TaskFlussoInterface) currentDetail).getRecordEvento();		
			boolean isEseguibile = true;
			if (recordEvento != null && recordEvento.getAttribute("flgEseguibile") != null) {
				isEseguibile = "1".equals(recordEvento.getAttribute("flgEseguibile"));
			}
			if (isEseguibile) {
				AurigaLayout.addMessage(new MessageBean("Azione non consentita fino a salvataggio/completamento del passo aperto", "", MessageType.ERROR));
			} else {
				caricaDettaglioEvento(record);
			}
		} else {
			caricaDettaglioEvento(record);
		}
	}

	public void caricaDettaglioEvento(String nome) {
		caricaDettaglioEvento(mappaMenu.get(nome));
	}

	public void caricaDettaglioEventoApp(String nome) {
		String nomeAttivitaApp = mappaMenu.get(nome).getAttribute("nomeAttivitaApp");
		String nomeGruppoApp = mappaMenu.get(nome).getAttribute("nomeGruppoApp");
		// Quando attiva apertura automatica task iter workflow dopo completamento passo non apre automaticamente eventuale prossimo passo disponibile
		if(AurigaLayout.getParametroDBAsBoolean("APERTURA_AUTO_FIRST_TASK_ESEG_ATTI") && !(nomeAttivitaApp != null && !"".equals(nomeAttivitaApp))) {
			nomeAttivitaApp = mappaMenu.get(nome).getAttribute("nome");
		}
		if (nomeAttivitaApp != null && !"".equals(nomeAttivitaApp)) {
			caricaDettaglioEvento(nomeAttivitaApp);
		} else if (nomeGruppoApp != null && !"".equals(nomeGruppoApp)) {
			caricaDettaglioEvento(nomeGruppoApp);
		} else {
			caricaDettaglioEvento(nome);
		}
	}
	
	public void caricaDettaglioEventoAnnulla(String nome) {
		String nomeAttivitaApp = mappaMenu.get(nome).getAttribute("nomeAttivitaApp");
		String nomeGruppoApp = mappaMenu.get(nome).getAttribute("nomeGruppoApp");
		if (nomeAttivitaApp != null && !"".equals(nomeAttivitaApp)) {
			caricaDettaglioEvento(nomeAttivitaApp);
		} else if (nomeGruppoApp != null && !"".equals(nomeGruppoApp)) {
			caricaDettaglioEvento(nomeGruppoApp);
		} else {
			caricaDettaglioEvento(nome);
		}
	}

	public void caricaDettaglioEventoPrec() {
		if (precDettaglioEvento != null) {
			caricaDettaglioEvento(precDettaglioEvento.getAttributeAsString("nome"), precDettaglioEvento.getAttributeAsString("titolo"),
					precDettaglioEvento.getAttributeAsString("dettagli"), (CustomDetail) precDettaglioEvento.getAttributeAsObject("canvas"));
		}
	}

	public void caricaDettaglioEventoSuccessivo(String nome) {
		for (String key : mappaMenu.keySet()) {
			String tipoAttivita = mappaMenu.get(key).getAttributeAsString("tipoAttivita");
			String flgFatta = mappaMenu.get(key).getAttributeAsString("flgFatta");
			if (tipoAttivita != null && "AS".equals(tipoAttivita) && flgFatta != null && "-1".equals(flgFatta)) {
				caricaDettaglioEventoApp(key);
				return;
			}
		}
		caricaDettaglioEventoApp(nome);
	}

	private void showUltimaAttivita() {

		final MessaggioUltimaAttivitaWindow messaggioUltimaAttivitaWindow = new MessaggioUltimaAttivitaWindow(vm);
		messaggioUltimaAttivitaWindow.show();

	}

	public void caricaDettaglioEvento(final Record record) {
		if (record != null) {

			final String nome = record.getAttribute("nome");
			final String titolo = buildTitoloEvento(nome);
			String nomeAttivitaApp = record.getAttribute("nomeAttivitaApp");
			final String dettagli = (nomeAttivitaApp != null && !"".equals(nomeAttivitaApp)) ? null : record.getAttribute("dettagli");
			String flgCallExecAtt = record.getAttribute("flgCallExecAtt");

			// se è un task del BackOffice oppure se è un task del workflow
			if (flgCallExecAtt != null && "1".equals(flgCallExecAtt)) {

				if (record.getAttribute("idProcess") != null && !"".equals(record.getAttribute("idProcess"))) {
					callExecAtt(record, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							CustomDetail taskDetail = buildDettaglioEventoFlusso(object);
							if (taskDetail != null) {
								caricaDettaglioEvento(nome, titolo, dettagli, taskDetail);
							}
						}
					});
				} else {
					CustomDetail taskDetail = buildDettaglioEvento(record);
					if (taskDetail != null) {
						caricaDettaglioEvento(nome, titolo, dettagli, taskDetail);
					}
				}

			} else {

				CustomDetail taskDetail = buildDettaglioEvento(record);
				if (taskDetail != null) {
					caricaDettaglioEvento(nome, titolo, dettagli, taskDetail);
				}

			}
		}
	}
	
	public void callExecAtt(String nome, final ServiceCallback<Record> callback) {
		callExecAtt(mappaMenu.get(nome), callback);
	}

	public void callExecAtt(final Record record, final ServiceCallback<Record> callback) {
		
		final String start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());
		
		Layout.showWaitPopup("Caricamento dati in corso...");
		GWTRestService<Record, Record> lCallExecAttDatasource = new GWTRestService<Record, Record>("CallExecAttDatasource");
		lCallExecAttDatasource.call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(final Record output) {

				GWT.log("callExecAtt() started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));
				
				String warninMsg = output.getAttribute("warningMsg");
				if (warninMsg != null && !"".equals(warninMsg)) {
					AurigaLayout.showConfirmDialogWithWarning("Attenzione!", warninMsg, "Procedi", "Annulla", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {

							if (value != null && value) {
								if (callback != null) {
									callback.execute(output);
								}
							}
						}
					});
				} else {
					if (callback != null) {
						callback.execute(output);
					}
				}
			}
		});
	}

	public void caricaDettaglioEvento(final String nome, final String titolo, final String dettagli, final CustomDetail canvas) {

		reloadPratica(new ServiceCallback<Record>() {

			@Override
			public void execute(Record lRecordPratica) {

				if (lRecordPratica != null) {
					String start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());

					editRecord(lRecordPratica);
					
					GWT.log("editRecordPratica() started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));					
				}

				precDettaglioEvento = currDettaglioEvento;

				currDettaglioEvento = new Record();
				currDettaglioEvento.setAttribute("nome", nome);
				currDettaglioEvento.setAttribute("titolo", titolo);
				currDettaglioEvento.setAttribute("dettagli", dettagli);
				currDettaglioEvento.setAttribute("canvas", canvas);

				Record lRecordVoceMenu = nome != null ? mappaMenu.get(nome) : null;

				boolean isEseguibile = true;
				if (lRecordVoceMenu != null && lRecordVoceMenu.getAttribute("flgEseguibile") != null) {
					isEseguibile = "1".equals(lRecordVoceMenu.getAttribute("flgEseguibile"));
				}
				
				boolean isAtto = false;
				if(detailRecord != null && detailRecord.getAttribute("isAtto") != null) {
					isAtto = "1".equals(detailRecord.getAttribute("isAtto"));
				}

				String motiviNonEseg = null;
				if (lRecordVoceMenu != null && lRecordVoceMenu.getAttribute("motiviNonEseg") != null && !"".equals(lRecordVoceMenu.getAttribute("motiviNonEseg"))) {
					motiviNonEseg = "Passo non modificabile: " + lRecordVoceMenu.getAttribute("motiviNonEseg");
				}

				HLayout intestazioneLayout = new HLayout(0);
				intestazioneLayout.setStyleName(it.eng.utility.Styles.intestazioneLayout);
				intestazioneLayout.setPadding(0);
				intestazioneLayout.setMembersMargin(0);
				intestazioneLayout.setOverflow(Overflow.VISIBLE);
				intestazioneLayout.setAlign(VerticalAlignment.CENTER);

				String proponente = null;
				if (showUoProponenteResponsabileInIntestazione()) {
					if (desUoProponente != null && !"".equals(desUoProponente)) {
						proponente = "U.O. proponente: " + desUoProponente;
					}
					if (nomeResponsabileUoProponente != null && !"".equals(nomeResponsabileUoProponente)) {
						if (proponente == null) {
							proponente = "Responsabile: " + nomeResponsabileUoProponente;
						} else {
							proponente += "&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;Responsabile: " + nomeResponsabileUoProponente;
						}
					}
				}
				String intestazione = intestazioneProgetto;
				if(rifAttoInOrganigramma != null && !"".equals(rifAttoInOrganigramma)) {
					intestazione += "<br/>" + "<i>" + rifAttoInOrganigramma + "</i>";
				} else if (proponente != null && !"".equals(proponente)) {
					intestazione += "<br/>" + "<i>" + proponente + "</i>";
				}

				Label labelIntestazione = new Label("<br/><b>" + intestazione + "</b>");
				labelIntestazione.setAlign(Alignment.CENTER);
				labelIntestazione.setBaseStyle(it.eng.utility.Styles.titoloDettagliIntestazione);
				labelIntestazione.setHeight(2);
				labelIntestazione.setWidth100();
				labelIntestazione.setValign(VerticalAlignment.CENTER);
				intestazioneLayout.addMember(labelIntestazione);

				boolean isInBozza = stato != null && "In bozza".equals(stato);
				if (!isInBozza) {
					String idDefProcFlow = first.getAttribute("idDefProcFlow");
					if (idDefProcFlow != null && !"".equals(idDefProcFlow)) {
						Label btnGrafo = new Label("<div style='margin-top:8px; margin-right:5px; cursor:hand;'><img src=\"images/pratiche/task/grafico_del_flusso.png\" height=\"24\" width=\"24\"/></div>");
						btnGrafo.setBaseStyle(it.eng.utility.Styles.buttonIntestazione);
						btnGrafo.setPrompt("Grafico del flusso");
						btnGrafo.setWidth(24);
						btnGrafo.setHeight100();
						// btnGrafo.setIcon("pratiche/task/grafico_del_flusso.png");
						// btnGrafo.setIconWidth(24);
						// btnGrafo.setIconHeight(24);
						// btnGrafo.setPadding(10);
						btnGrafo.setValign(VerticalAlignment.CENTER);
						btnGrafo.setShowOverCanvas(false);
						btnGrafo.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								showGraficoFlusso();
							}
						});
						intestazioneLayout.addMember(btnGrafo);
					}
					if (idProcess != null && !"".equals(idProcess)) {
						Label btnTree = new Label("<div style='margin-top:8px; margin-right:5px; cursor:hand;'><img src=\"images/pratiche/task/iter_svolto.png\" height=\"24\" width=\"24\" /></div>");
						btnTree.setBaseStyle(it.eng.utility.Styles.buttonIntestazione);
						btnTree.setPrompt(I18NUtil.getMessages().atti_inlavorazione_iter_svolto_image_prompt());
						btnTree.setWidth(24);
						btnTree.setHeight100();
						// btnTree.setIcon("pratiche/task/iter_svolto.png");
						// btnTree.setIconWidth(24);
						// btnTree.setIconHeight(24);
						// btnTree.setPadding(10);
						btnTree.setValign(VerticalAlignment.CENTER);
						btnTree.setShowOverCanvas(false);
						btnTree.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								showIterSvolto();
							}
						});
						intestazioneLayout.addMember(btnTree);
					}
					if(abilitaCallApplTitoliEdilizi) {
						Label btnImportFromOnlyOne = new Label("<div style='margin-top:8px; margin-right:5px; cursor:hand;'><img src=\"images/pratiche/task/import.png\" height=\"24\" width=\"24\" /></div>");
						btnImportFromOnlyOne.setBaseStyle(it.eng.utility.Styles.buttonIntestazione);
						btnImportFromOnlyOne.setPrompt("Recupera dati pratica da WF");
						btnImportFromOnlyOne.setWidth(24);
						btnImportFromOnlyOne.setHeight100();
						// btnImportFromOnlyOne.setIcon("pratiche/task/import.png");
						// btnImportFromOnlyOne.setIconWidth(24);
						// btnImportFromOnlyOne.setIconHeight(24);
						// btnImportFromOnlyOne.setPadding(10);
						btnImportFromOnlyOne.setValign(VerticalAlignment.CENTER);
						btnImportFromOnlyOne.setShowOverCanvas(false);
						btnImportFromOnlyOne.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								importFromOnlyOne();
							}
						});
						intestazioneLayout.addMember(btnImportFromOnlyOne);
					}
				}

				if (isFrontOffice()) {
					Label btnNote = new Label("<div style='margin-top:8px; margin-right:5px; cursor:hand;'><img src=\"images/pratiche/task/note.png\" height=\"24\" width=\"24\" /></div>");
					btnNote.setPrompt("Note");
					btnNote.setWidth(24);
					btnNote.setHeight(24);
					// btnNote.setIcon("pratiche/task/note.png");
					// btnNote.setIconWidth(24);
					// btnNote.setIconHeight(24);
					// btnNote.setPadding(10);
					btnNote.setValign(VerticalAlignment.CENTER);
					btnNote.setShowOverCanvas(false);
					btnNote.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							caricaDettaglioEvento("Note");
						}
					});
					intestazioneLayout.addMember(btnNote);
				} else if (messaggioUltimaAttivita != null && !messaggioUltimaAttivita.equals("")) {
					Label btnUltimaAttivita = new Label("<div style='margin-top:8px; margin-right:5px; cursor:hand;'><img src=\"images/pratiche/task/note.png\" height=\"24\" width=\"24\" /></div>");
					btnUltimaAttivita.setBaseStyle(it.eng.utility.Styles.buttonIntestazione);
					btnUltimaAttivita.setPrompt("Messaggio ultima attività");
					btnUltimaAttivita.setWidth(24);
					btnUltimaAttivita.setHeight100();
					// btnUltimaAttivita.setIcon("pratiche/task/note.png");
					// btnUltimaAttivita.setIconWidth(24);
					// btnUltimaAttivita.setIconHeight(24);
					// btnUltimaAttivita.setPadding(10);
					btnUltimaAttivita.setValign(VerticalAlignment.CENTER);
					btnUltimaAttivita.setShowOverCanvas(false);
					btnUltimaAttivita.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							showUltimaAttivita();
						}
					});
					intestazioneLayout.addMember(btnUltimaAttivita);
				}
				
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_DETT_FIRME_ATTO")) {
					
					final String idUd = lRecordVoceMenu != null && lRecordVoceMenu.getAttributeAsString("idUdIstanza") != null
							&& !"".equalsIgnoreCase(lRecordVoceMenu.getAttributeAsString("idUdIstanza")) ?
							lRecordVoceMenu.getAttributeAsString("idUdIstanza") : "0";
					
					Label btnDettaglioFirmeVisti = new Label(
							"<div style='margin-top:8px; margin-right:5px; cursor:hand;'><img src=\"images/pratiche/task/dettaglio_firme_visti_pratiche.png\" height=\"24\" width=\"24\" /></div>");
					btnDettaglioFirmeVisti.setBaseStyle(it.eng.utility.Styles.buttonIntestazione);
					btnDettaglioFirmeVisti.setPrompt("Dettaglio firme/visti");
					btnDettaglioFirmeVisti.setWidth(24);
					btnDettaglioFirmeVisti.setHeight100();
					btnDettaglioFirmeVisti.setValign(VerticalAlignment.CENTER);
					btnDettaglioFirmeVisti.setShowOverCanvas(false);
					btnDettaglioFirmeVisti.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("DettaglioFirmeVisteAttoDataSource");
							lGwtRestService.extraparam.put("idUdAtto", idUd);
							lGwtRestService.call(new Record(), new ServiceCallback<Record>() {

								@Override
								public void execute(Record object) {
									
									if(object != null && object.getAttributeAsRecordList("listaFirmeVisti") != null &&
											!object.getAttributeAsRecordList("listaFirmeVisti").isEmpty()) {
										DettaglioFirmeVistiDetail lDettaglioFirmeVistiDetail = new DettaglioFirmeVistiDetail();
										lDettaglioFirmeVistiDetail.caricaDettaglio(object);
										lDettaglioFirmeVistiDetail.setCanEdit(false);
										String title = "Dettaglio firme/visti "+ object.getAttributeAsString("estremiAtto");
										String icon = "coccarda.png";
										AurigaLayout.addModalWindow("dettaglio_firme_visti", title, icon, lDettaglioFirmeVistiDetail);
									} else {
										Layout.addMessage(new MessageBean("Non sono presenti firme/visti", "", MessageType.INFO));
									}
								}

							});
						}
					});

					intestazioneLayout.addMember(btnDettaglioFirmeVisti);
				}
				
				if(isAtto && AurigaLayout.getParametroDBAsBoolean("ATTIVA_INVIO_MAIL_PROP_ATTO")) {
					Label btnInvioMailAtto = new Label("<div style='margin-top:10px; margin-right:5px; cursor:hand;'><img src=\"images/mail/PEO.png\" height=\"24\" width=\"24\" /></div>");
					btnInvioMailAtto.setBaseStyle(it.eng.utility.Styles.buttonIntestazione);
					btnInvioMailAtto.setPrompt("Invio e-mail");
					btnInvioMailAtto.setWidth(24);
					btnInvioMailAtto.setHeight100();
					btnInvioMailAtto.setValign(VerticalAlignment.CENTER);
					btnInvioMailAtto.setShowOverCanvas(false);
					btnInvioMailAtto.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							final Record recordDettaglio = ((TaskNuovaPropostaAtto2CompletaDetail) currentDetail).getRecordToSave();	
							final Record recordEvento = ((TaskFlussoInterface) currentDetail).getRecordEvento();	
							
							recordDettaglio.setAttribute("idProcess", recordEvento.getAttribute("idProcess"));						
							recordDettaglio.setAttribute("idModello", recordEvento.getAttribute("idModAssDocTask"));
							recordDettaglio.setAttribute("nomeModello", recordEvento.getAttribute("nomeModAssDocTask"));
							recordDettaglio.setAttribute("displayFilenameModello", recordEvento.getAttribute("displayFilenameModAssDocTask"));
							recordDettaglio.setAttribute("impostazioniUnioneFile", recordEvento.getAttributeAsRecord("impostazioniUnioneFile"));						
							recordDettaglio.setAttribute("unioneFileNomeFile", recordEvento.getAttribute("unioneFileNomeFile"));	
							recordDettaglio.setAttribute("unioneFileNomeFileOmissis", recordEvento.getAttribute("unioneFileNomeFileOmissis"));	
							
							GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaInvioUDMailDatasource");
							lGwtRestService.extraparam.put("tipoMail", "PEO");
							lGwtRestService.extraparam.put("finalita", "INVIO_PROPOSTA_ATTO");
							lGwtRestService.executecustom("preparaEmailAttiDaDettaglio", recordDettaglio, new DSCallback() {
								
								@Override
								public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
									if(dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
										final Record object = dsResponse.getData()[0];
										boolean isPresentiAllegatiPISep = false;
										RecordList listaAttach = object.getAttributeAsRecordList("attach");
										final RecordList listaAttachWithoutAllegatiPISep = new RecordList();
										if(listaAttach != null) {
											for(int i = 0; i < listaAttach.getLength(); i++) {
												Record attach = listaAttach.get(i);
												if(attach.getAttributeAsBoolean("flgAllegatoPISep")) {
													isPresentiAllegatiPISep = true;
												} else {
													listaAttachWithoutAllegatiPISep.add(attach);
												}
											}
										}
										if(isPresentiAllegatiPISep) {
											SC.ask("Vuoi includere gli allegati parte integrante separati?", new BooleanCallback() {
												
												@Override
												public void execute(Boolean value) {													
													if(value != null && value) {
														InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEO", true, new DSCallback() {
															
															@Override
															public void execute(DSResponse response, Object rawData, DSRequest request) {
																
															}
														});
														lInvioUdMailWindow.loadMail(object);
														lInvioUdMailWindow.show();
													} else {														
														object.setAttribute("attach", listaAttachWithoutAllegatiPISep);
														InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEO", true, new DSCallback() {
															
															@Override
															public void execute(DSResponse response, Object rawData, DSRequest request) {

															}
														});
														lInvioUdMailWindow.loadMail(object);
														lInvioUdMailWindow.show();
													}							
												}
											});				
										} else {					
											InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEO", true, new DSCallback() {
												
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													
												}
											});
											lInvioUdMailWindow.loadMail(object);
											lInvioUdMailWindow.show();
										}
									}	
								}
							}); 
						}
					});

					intestazioneLayout.addMember(btnInvioMailAtto);
				}

				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_CONFRONTO_ATTI")) {
					Label btnConfrontaDoc = new Label("<div style='margin-top:8px; margin-right:5px; cursor:hand;'><img src=\"images/pratiche/task/confronta_documenti_grande.png\" height=\"24\" width=\"24\" /></div>");
					btnConfrontaDoc.setBaseStyle(it.eng.utility.Styles.buttonIntestazione);
					btnConfrontaDoc.setPrompt("Confronta documenti");
					btnConfrontaDoc.setWidth(24);
					btnConfrontaDoc.setHeight100();
					btnConfrontaDoc.setValign(VerticalAlignment.CENTER);
					btnConfrontaDoc.setShowOverCanvas(false);
					btnConfrontaDoc.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							showConfrontaDocumenti();
							
						}
					});
					
					intestazioneLayout.addMember(btnConfrontaDoc);
				}

				layoutSXIntestazione.setMembers(intestazioneLayout);

				if (titolo != null && !"".equals(titolo) && (!(canvas instanceof SottomenuTaskDetail) || canvas instanceof SottomenuTaskBackDetail)) {

					String contents = "<div class='" + it.eng.utility.Styles.titoloTask + "'>"
							+ titolo + "</div>";
					if (dettagli != null && !"".equals(dettagli)) {
						contents += "<div class='" + it.eng.utility.Styles.dettagliTask + "'>&nbsp;&nbsp;&nbsp;<i>" + dettagli + "</i></div>";
					}
					if (!isEseguibile && motiviNonEseg != null && !"".equals(motiviNonEseg)) {
						contents += "<br/><div class='" + it.eng.utility.Styles.motiviNonEsegTask + "'>&nbsp;&nbsp;&nbsp;<img src=\"images/ko.png\" height=\"16\" width=\"16\" style=\"vertical-align:middle;padding-right:5;\"/>"
								+ motiviNonEseg + "</div>";
					}

					HLayout titoloLayout = new HLayout(0);
					titoloLayout.setPadding(5);
					titoloLayout.setMembersMargin(0);
					titoloLayout.setOverflow(Overflow.VISIBLE);

					Label labelTitolo = new Label(contents);
					labelTitolo.setAlign(Alignment.LEFT);
					labelTitolo.setBaseStyle(it.eng.utility.Styles.formTitle);
					labelTitolo.setWidth100();
					labelTitolo.setHeight(2);
					titoloLayout.addMember(labelTitolo);

					if (canvas instanceof EditorHtmlDetail) {
						Label btnEspandi = new Label();
						btnEspandi.setIcon("pratiche/espandi.png");
						btnEspandi.setIconSize(24);
						btnEspandi.setHeight(24);
						btnEspandi.setWidth(24);
						btnEspandi.setPrompt("Visualizza a tutta pagina");
						btnEspandi.setShowFocused(false);
						btnEspandi.setShowOverCanvas(false);
						btnEspandi.setShowFocusedAsOver(false);
						btnEspandi.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

							@Override
							public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

								((EditorHtmlDetail) canvas).espandi();
							}
						});
						titoloLayout.addMember(btnEspandi);
					}

					layoutSXIntestazione.addMember(titoloLayout);
				}

				if(currentDetail != null) {
					if(currentDetail.isSaved()) {
						setSaved(true);
					}
					// distruggo il dettaglio precedente o mi tiene in memoria vm e CKEditor
					currentDetail.markForDestroy();
				}
				
				layoutSXDettaglioEvento.setMembers(canvas);

				currentDetail = canvas != null ? canvas : new CustomDetail("default");
				currentDetail.setCanFocus(true);
				currentDetail.refreshTabIndex();

				buttonIndietro.setVisibility(Visibility.HIDDEN);
				buttonConferma.setVisibility(Visibility.HIDDEN);
				buttonSalvaDefinitivoOK.setVisibility(Visibility.HIDDEN);
				buttonSalvaDefinitivoKO.setVisibility(Visibility.HIDDEN);
				buttonMessaggioTask.setVisibility(Visibility.HIDDEN);
				buttonSalvaDefinitivo.setVisibility(Visibility.HIDDEN);
				buttonSalvaProvvisorio.setVisibility(Visibility.HIDDEN);
				buttonDatiStorici.setVisibility(Visibility.HIDDEN);
				buttonModifica.setVisibility(Visibility.HIDDEN);
				buttonFirma.setVisibility(Visibility.HIDDEN);
				if(getListaCustomButtons() != null) {
					for(Canvas customButton : getListaCustomButtons()) {
						customButton.setVisibility(Visibility.HIDDEN);
					}
				}
				otherEventoButtonsLayout.setMembers();
				otherEventoButtonsLayout.setVisibility(Visibility.HIDDEN);				
				buttonAnnulla.setVisibility(Visibility.HIDDEN);				
				
				if (currentDetail != null) {

					if (isEseguibile) {
						if (currentDetail instanceof EditorHtmlFlussoDetail) {
							String nomeTastoSalvaProvvisorio = "Salva";
							String nomeTastoSalvaDefinitivo = "Firma e invia";
							if (((EditorHtmlFlussoDetail) currentDetail).isAnteprimaDocumento()) {
								nomeTastoSalvaProvvisorio = ((EditorHtmlFlussoDetail) currentDetail).getNomeTastoSalvaProvvisorio_2();
								nomeTastoSalvaDefinitivo = ((EditorHtmlFlussoDetail) currentDetail).getNomeTastoSalvaDefinitivo_2();
							} else {
								nomeTastoSalvaProvvisorio = ((EditorHtmlFlussoDetail) currentDetail).getNomeTastoSalvaProvvisorio();
								nomeTastoSalvaDefinitivo = ((EditorHtmlFlussoDetail) currentDetail).getNomeTastoSalvaDefinitivo();
							}
							if (nomeTastoSalvaProvvisorio != null && !"".equals(nomeTastoSalvaProvvisorio)) {
								buttonSalvaProvvisorio.setTitle(nomeTastoSalvaProvvisorio);
								buttonSalvaProvvisorio.setVisibility(Visibility.VISIBLE);
							}
							if (nomeTastoSalvaDefinitivo != null && !"".equals(nomeTastoSalvaDefinitivo)) {
								buttonSalvaDefinitivo.setTitle(nomeTastoSalvaDefinitivo);
								buttonSalvaDefinitivo.setIcon("pratiche/task/buttons/conferma.png");
								buttonSalvaDefinitivo.setVisibility(Visibility.VISIBLE);								
							}
						} else if (currentDetail instanceof EditorAppletDetail) {
							String nomeTastoSalvaProvvisorio = "Salva";
							String nomeTastoSalvaDefinitivo = "Firma e invia";
							if (((EditorAppletDetail) currentDetail).isAnteprimaDocumento()) {
								nomeTastoSalvaProvvisorio = ((EditorAppletDetail) currentDetail).getNomeTastoSalvaProvvisorio_2();
								nomeTastoSalvaDefinitivo = ((EditorAppletDetail) currentDetail).getNomeTastoSalvaDefinitivo_2();
							} else {
								nomeTastoSalvaProvvisorio = ((EditorAppletDetail) currentDetail).getNomeTastoSalvaProvvisorio();
								nomeTastoSalvaDefinitivo = ((EditorAppletDetail) currentDetail).getNomeTastoSalvaDefinitivo();
							}
							if (nomeTastoSalvaProvvisorio != null && !"".equals(nomeTastoSalvaProvvisorio)) {
								buttonSalvaProvvisorio.setTitle(nomeTastoSalvaProvvisorio);
								buttonSalvaProvvisorio.setVisibility(Visibility.VISIBLE);
							}
							if (nomeTastoSalvaDefinitivo != null && !"".equals(nomeTastoSalvaDefinitivo)) {
								buttonSalvaDefinitivo.setTitle(nomeTastoSalvaDefinitivo);
								buttonSalvaDefinitivo.setIcon("pratiche/task/buttons/conferma.png");
								buttonSalvaDefinitivo.setVisibility(Visibility.VISIBLE);								
							}
							if (!nome.toUpperCase().startsWith("FIRMA_COPERTINA|*|")) {
								buttonModifica.setVisibility(Visibility.VISIBLE);
							}
						} else if (currentDetail instanceof SaveDetailInterface) {
							buttonConferma.setVisibility(Visibility.VISIBLE);
						} else if (currentDetail instanceof TaskFlussoInterface) {
							String nomeTastoSalvaProvvisorio = ((TaskFlussoInterface) currentDetail).getNomeTastoSalvaProvvisorio();
							String nomeTastoSalvaDefinitivo = ((TaskFlussoInterface) currentDetail).getNomeTastoSalvaDefinitivo();							
							if (nomeTastoSalvaProvvisorio != null && !"".equals(nomeTastoSalvaProvvisorio)) {
								buttonSalvaProvvisorio.setTitle(nomeTastoSalvaProvvisorio);
								buttonSalvaProvvisorio.setVisibility(Visibility.VISIBLE);
							}
//							if(currentDetail instanceof PropostaAtto2Detail) {
//								buttonFirma.setVisibility(Visibility.VISIBLE);
//								final String modello = ((PropostaAtto2Detail)currentDetail).getModello();
//								if(modello != null) {
//									buttonModifica.setTitle(AurigaLayout.getNomeModelloAtti(modello));
//									buttonModifica.setVisibility(Visibility.VISIBLE);
//								}
//							}
							if (nomeTastoSalvaDefinitivo != null && !"".equals(nomeTastoSalvaDefinitivo)) {
								if(currentDetail instanceof TaskNuovaPropostaAtto2CompletaDetail && AurigaLayout.getParametroDBAsBoolean("ATTIVA_TASTI_AVANZAMENTO_TASK_SEPARATI")) {
									String nomeTastoSalvaDefinitivoOK = "Avanti";									
									String nomeTastoSalvaDefinitivoKO = "Indietro";
									buttonSalvaDefinitivoOK.setTitle(nomeTastoSalvaDefinitivoOK);
									buttonSalvaDefinitivoOK.setIcon("pratiche/task/buttons/conferma.png");
									buttonSalvaDefinitivoOK.setVisibility(Visibility.VISIBLE);
									if((((TaskNuovaPropostaAtto2CompletaDetail) currentDetail).showButtonSalvaDefinitivoKO())) {
										buttonSalvaDefinitivoKO.setTitle(nomeTastoSalvaDefinitivoKO);
										buttonSalvaDefinitivoKO.setIcon("pratiche/task/buttons/confermaKO.png");
										buttonSalvaDefinitivoKO.setVisibility(Visibility.VISIBLE);
									}					
									String msgTaskProvvisorio = ((TaskNuovaPropostaAtto2CompletaDetail) currentDetail).getMsgTaskProvvisorio();
									if(msgTaskProvvisorio != null && !"".equals(msgTaskProvvisorio.trim())) {
										buttonMessaggioTask.setTitle("Modifica messaggio");
										buttonMessaggioTask.setIcon("pratiche/task/note.png");
									} else {
										buttonMessaggioTask.setTitle("Aggiungi messaggio");
										buttonMessaggioTask.setIcon("pratiche/task/buttons/aggiungi.png");
									}									
									buttonMessaggioTask.setVisibility(Visibility.VISIBLE);
								} else {
									buttonSalvaDefinitivo.setTitle(nomeTastoSalvaDefinitivo);
									if (((TaskFlussoInterface) currentDetail).hasDocumento()) {
										buttonSalvaDefinitivo.setIcon("pratiche/task/buttons/avanti.png");
									} else {
										buttonSalvaDefinitivo.setIcon("pratiche/task/buttons/conferma.png");
									}
									buttonSalvaDefinitivo.setVisibility(Visibility.VISIBLE);
								}
							}
							if(getListaCustomButtons() != null) {
								Record lRecordEvento = ((TaskFlussoInterface)currentDetail).getRecordEvento();
								for(CustomTaskButton customButton : getListaCustomButtons()) {
									if(lRecordEvento != null && customButton.isToShow(lRecordEvento)) {
										customButton.setVisibility(Visibility.VISIBLE);
										customButton.setTaskDetail(((TaskFlussoInterface) currentDetail));
									}
								}
							} 
						}
					}

					if (currentDetail instanceof PropostaAtto2Detail) {
						HashSet<String> attributiAddDocTabsDatiStorici = ((PropostaAtto2Detail) currentDetail).getAttributiAddDocTabsDatiStorici();
						if (attributiAddDocTabsDatiStorici != null && attributiAddDocTabsDatiStorici.size() > 0) {
							buttonDatiStorici.setVisibility(Visibility.VISIBLE);
						}
					}

					if (currentDetail instanceof LoadDetailInterface) {
						((LoadDetailInterface) currentDetail).loadDati();
					}

					currentDetail.setCanEdit(isEseguibile);
					
					if (currentDetail instanceof BackDetailInterface) {
						if (isEseguibile && (currentDetail instanceof EditorHtmlFlussoDetail)
								&& ((EditorHtmlFlussoDetail) currentDetail).isAnteprimaDocumento()) {
							buttonIndietro.setVisibility(Visibility.VISIBLE);
						} else if (isEseguibile
								&& ((currentDetail instanceof SaveDetailInterface) || (currentDetail instanceof TaskFlussoInterface)
										|| (currentDetail instanceof EditorHtmlFlussoDetail) || (currentDetail instanceof EditorAppletDetail))) {
							buttonAnnulla.setVisibility(Visibility.VISIBLE);
						} else {
							buttonIndietro.setVisibility(Visibility.VISIBLE);
						}
					}
				}
				
				List<Canvas> listaButtons = new ArrayList<Canvas>();
				listaButtons.add(buttonIndietro);
				listaButtons.add(buttonConferma);
				listaButtons.add(buttonSalvaDefinitivoOK);	
				listaButtons.add(buttonSalvaDefinitivoKO);
				listaButtons.add(buttonMessaggioTask);
				listaButtons.add(buttonSalvaDefinitivo);				
				listaButtons.add(buttonSalvaProvvisorio);
				listaButtons.add(buttonDatiStorici);				
				listaButtons.add(buttonModifica);
				listaButtons.add(buttonFirma);
				if(getListaCustomButtons() != null) {
					listaButtons.addAll(getListaCustomButtons());	
				} 				
				listaButtons.add(buttonScaricaFile);
				listaButtons.add(otherEventoButtonsLayout);
				listaButtons.add(buttonAnnulla);
				
				setButtons(listaButtons.toArray(new Canvas[0]));

				boolean showToolStripButtons = false;
				for (Canvas member : toolStripButtons.getMembers()) {
					if (member instanceof FrontendButton) {
						if (member.isVisible()) {
							showToolStripButtons = true;
						}
					}
				}
				toolStripButtons.setVisible(showToolStripButtons);

				selezionaEvento(nome);

				Layout.hideWaitPopup();
			}
		});

	}
	
	private RecordList getAllegatoFileUnione(Record record) {
		RecordList listaAllegati = new RecordList();
		for(int i=0; i < record.getAttributeAsRecordList("attach").getLength(); i++) {
			Record allegatoItem = record.getAttributeAsRecordList("attach").get(i);
			if(allegatoItem.getAttributeAsInt("nroAttach") != null && 
					allegatoItem.getAttributeAsInt("nroAttach") == 1) {
				listaAllegati.add(allegatoItem);
				break;
			}
		}
		return listaAllegati;
	}

	public boolean showUoProponenteResponsabileInIntestazione() {		
		return true;
	}

	public void reloadPratica(final ServiceCallback<Record> callback) {
		if (!skipReload && idProcess != null && !"".equals(idProcess)) {
			
			final String start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());
			
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idPratica", idProcess);
			GWTRestDataSource praticheDS = new GWTRestDataSource("PraticheDataSource", "idPratica", FieldType.TEXT);
			praticheDS.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
			
					GWT.log("loadDatiPratica() started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));
					
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record lRecordPratica = response.getData()[0];
						if (callback != null) {
							callback.execute(lRecordPratica);
						}
					}
				}
			});
		} else {
			skipReload = false;
			if (callback != null) {
				callback.execute(null);
			}
		}
	}

	public void selezionaEvento(String nome) {
		if (mappaMenu.get(nome) != null) {
			apriSottomenu(nome);
			selezionaLabelSottomenu(nome);
		}
	}

	public FrontendButton getButtonIndietro() {
		return buttonIndietro;
	}

	public FrontendButton getButtonAnnulla() {
		return buttonAnnulla;
	}

	public FrontendButton getButtonScaricaFile() {
		return buttonScaricaFile;
	}

	public FrontendButton getButtonModifica() {
		return buttonModifica;
	}

	public FrontendButton getButtonFirma() {
		return buttonFirma;
	}

	public FrontendButton getButtonDatiStorici() {
		return buttonDatiStorici;
	}

	public FrontendButton getButtonConferma() {
		return buttonConferma;
	}

	public FrontendButton getButtonSalvaProvvisorio() {
		return buttonSalvaProvvisorio;
	}
	
	public FrontendButton getButtonSalvaDefinitivoOK() {
		return buttonSalvaDefinitivoOK;
	}
	
	public FrontendButton getButtonSalvaDefinitivoKO() {
		return buttonSalvaDefinitivoKO;
	}
	
	public FrontendButton getButtonMessaggioTask() {
		return buttonMessaggioTask;
	}
	
	public FrontendButton getButtonSalvaDefinitivo() {
		return buttonSalvaDefinitivo;
	}

	public String buildTitoloEvento(String nome) {
		String titolo = getDisplayNameEvento(nome);
		if (mappaMenu.get(nome) != null) {
			String nomeAttivitaApp = mappaMenu.get(nome).getAttribute("nomeAttivitaApp");
			if (nomeAttivitaApp != null && !"".equals(nomeAttivitaApp)) {
				titolo = buildBricioleDiPane(nome);
			}
			String nomeGruppoApp = mappaMenu.get(nome).getAttribute("nomeGruppoApp");
			if (nomeGruppoApp != null && !"".equals(nomeGruppoApp)) {
				titolo = nomeGruppoApp + " / " + titolo;
			}
		}
		return titolo;
	}

	public String getDisplayNameEvento(String nome) {
		String displayName = nome;
		int pos = displayName.indexOf("|*|");
		if (pos != -1) {
			displayName = displayName.substring(pos + 3);
		}
		return displayName;
	}

	public String buildBricioleDiPane(String nome) {
		String titolo = getDisplayNameEvento(nome);
		String nomeAttivitaApp = mappaMenu.get(nome).getAttribute("nomeAttivitaApp");
		String nomeGruppoApp = mappaMenu.get(nome).getAttribute("nomeGruppoApp");
		if (nomeAttivitaApp != null && !"".equals(nomeAttivitaApp)) {
			String titoloPadre = buildBricioleDiPane(nomeAttivitaApp);
			return (titoloPadre != null && !"".equals(titoloPadre)) ? titoloPadre + " / " + titolo : titolo;
		} else if (nomeGruppoApp != null && !"".equals(nomeGruppoApp)) {
			String titoloPadre = buildBricioleDiPane(nomeGruppoApp);
			return (titoloPadre != null && !"".equals(titoloPadre)) ? titoloPadre + " / " + titolo : titolo;
		}
		return null;
	}

	public void apriSottomenu(String nome) {
		String nomeGruppoApp = mappaMenu.get(nome) != null ? mappaMenu.get(nome).getAttribute("nomeGruppoApp") : null;
		boolean trovato = false;
		for (String key : mappaVStackSottomenu.keySet()) {
			VStack lVStackSottomenu = mappaVStackSottomenu.get(key);
			if (nome.equals(key) || (nomeGruppoApp != null && !"".equals(nomeGruppoApp) && nomeGruppoApp.equals(key))) {
				lVStackSottomenu.show();
				trovato = true;
			} else {
				lVStackSottomenu.hide();
			}
		}
		if (!trovato && nomeGruppoApp != null && !"".equals(nomeGruppoApp)) {
			apriSottomenu(nomeGruppoApp);
		}
	}

	public void selezionaLabelSottomenu(String nome) {
		String nomeGruppoApp = mappaMenu.get(nome) != null ? mappaMenu.get(nome).getAttribute("nomeGruppoApp") : null;
		boolean trovato = false;
		for (String key : mappaLabelSottomenu.keySet()) {
			Label labelSottomenu = mappaLabelSottomenu.get(key);
			Record recordMenu = mappaMenu.get(key);
			if (recordMenu.getAttribute("nome").equals(nome)) {
				labelSottomenu.setBaseStyle(it.eng.utility.Styles.labelMenuSelect);
				trovato = true;
			} else {
				labelSottomenu.setBaseStyle(labelSottomenu.isDisabled() ? it.eng.utility.Styles.labelMenuDisabled : it.eng.utility.Styles.labelMenu);
			}
		}
		if (!trovato && nomeGruppoApp != null && !"".equals(nomeGruppoApp)) {
			selezionaLabelSottomenu(nomeGruppoApp);
		}
	}

	public void editRecord(Record record) {

		setDetailRecord(record);

		idProcess = record.getAttribute("idPratica");
		idTipoProc = record.getAttribute("tipoProcedimento");
		stato = record.getAttribute("stato");
		messaggioUltimaAttivita = record.getAttribute("messaggioUltimaAttivita");

		mappaModelli = new LinkedHashMap<String, RecordList>();

		tipiModelliAtti = new HashSet<String>();

		RecordList listaModelliAttivita = record.getAttributeAsRecordList("listaModelliAttivita");
		if (listaModelliAttivita != null) {
			for (int i = 0; i < listaModelliAttivita.getLength(); i++) {
				Record recordModello = listaModelliAttivita.get(i);
				RecordList listaRecordModelli = mappaModelli.get(recordModello.getAttribute("activityName"));
				if(listaRecordModelli == null) {
					listaRecordModelli = new RecordList();
				}
				listaRecordModelli.add(recordModello);
				mappaModelli.put(recordModello.getAttribute("activityName"), listaRecordModelli);				
				tipiModelliAtti.add(recordModello.getAttribute("idTipoDoc"));
			}
		}

		vm.editRecord(record);

		intestazioneProgetto = record.getAttributeAsString("nomeProgetto");

		boolean isInBozza = stato != null && "In bozza".equals(stato);
		if (!isInBozza) {
			tabSetMenu.enableTab(tabMonitoraProcedimento);
		} else {
			tabSetMenu.disableTab(tabMonitoraProcedimento);
		}
	}

	public void caricaDettaglioPratica(Record record) {
		
		String start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());

		editRecord(record);
		
		GWT.log("editRecordPratica() started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));
		
		mappaMenu = new LinkedHashMap<String, Record>();
		mappaSottomenuGruppi = new LinkedHashMap<String, RecordList>();
		mappaSottomenuAttributiComplessi = new LinkedHashMap<String, RecordList>();
		mappaVStackSottomenu = new HashMap<String, VStack>();
		mappaLabelSottomenu = new HashMap<String, Label>();

		creaMenuGestisciIter(new ServiceCallback<Record>() {

			@Override
			public void execute(Record record) {
				skipReload = true;
				if(AurigaLayout.getParametroDBAsBoolean("APERTURA_AUTO_FIRST_TASK_ESEG_ATTI")) {
					// se ci sono una o più attività semplici con flgEseguibile = 1 apro la prima 
					for (String key : mappaMenu.keySet()) {
						String tipoAttivita = mappaMenu.get(key).getAttributeAsString("tipoAttivita");
						String flgEseguibile = mappaMenu.get(key).getAttributeAsString("flgEseguibile");
						String flgAttivitaDaNonAprireInAutomatico = mappaMenu.get(key).getAttributeAsString("flgAttivitaDaNonAprireInAutomatico");
						// Carico il dettaglio solo se il tipoAttività è AS, se è eseguibile e se non ha il flag flgAttivitaDaNonAprireInAutomatico
						if (tipoAttivita != null && "AS".equals(tipoAttivita) && flgEseguibile != null && "1".equals(flgEseguibile) && !(flgAttivitaDaNonAprireInAutomatico != null && "1".equals(flgAttivitaDaNonAprireInAutomatico))) {
							caricaDettaglioEvento(key);
							return;
						}					
					}	
				}				
				// altrimenti apro il primo gruppo con flgFatta = 1
				for (String key : mappaMenu.keySet()) {
					String tipoAttivita = mappaMenu.get(key).getAttributeAsString("tipoAttivita");
					String flgFatta = mappaMenu.get(key).getAttributeAsString("flgFatta");
					if (tipoAttivita != null && "G".equals(tipoAttivita) && flgFatta != null && "1".equals(flgFatta)) {
						caricaDettaglioEvento(key);
						return;
					}
				}
				// altrimenti apro il primo task
				caricaDettaglioEvento(first);
			}
		});

		// creaMenuMonitoraProcedimento();

	}

	public CustomDetail buildDettaglioEventoFlusso(Record record) {

		final String nome = record.getAttribute("nome");
		String idGUIEvento = record.getAttribute("idGUIEvento");
		String idTipoEvento = record.getAttribute("idTipoEvento");
		String tipoAttivita = record.getAttribute("tipoAttivita");
		String nomeEntita = "evento" + idTipoEvento;
		String nomeFile = (record.getAttribute("nomeFile") != null && !"".equals(record.getAttribute("nomeFile"))) ? record.getAttribute("nomeFile") : getDisplayNameEvento(nome);
		String uriFile = record.getAttribute("uriFile");
		InfoFileRecord infoFile = record.getAttributeAsRecord("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
		String mimetype = infoFile != null ? infoFile.getMimetype() : null;
		String idFolder = record.getAttribute("idFolder");
		String idUd = record.getAttribute("idUd");
		String estremiUd = record.getAttribute("estremiUd");
		String idDefProcFlow = first.getAttribute("idDefProcFlow");
		String nomeFlussoWF = (idDefProcFlow != null && idDefProcFlow.contains(":")) ? idDefProcFlow.substring(0, idDefProcFlow.indexOf(":")) : idDefProcFlow;
		String titolo = buildTitoloEvento(nome);
		String processNameWF = (titolo != null && titolo.contains(" / ")) ? titolo.substring(0, titolo.indexOf(" / ")).trim() : titolo;
		
		final String nomeFilePrimario = record.getAttribute("nomeFile");
		final String uriFilePrimario = record.getAttribute("uriFile");
		final InfoFileRecord infoFilePrimario = record.getAttributeAsRecord("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;			
		final String nomeFilePrimarioOmissis = record.getAttribute("nomeFileOmissis");
		final String uriFilePrimarioOmissis = record.getAttribute("uriFileOmissis");
		final InfoFileRecord infoFilePrimarioOmissis = record.getAttributeAsRecord("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;
		
		final boolean flgShowAnteprimaDefinitiva = record.getAttributeAsBoolean("flgShowAnteprimaDefinitiva");
		
		boolean isEseguibile = true;
		if (record != null && record.getAttribute("flgEseguibile") != null) {
			isEseguibile = "1".equals(record.getAttribute("flgEseguibile"));
		}

		CustomDetail canvas = null;

		buttonScaricaFile.setVisibility(Visibility.HIDDEN);

		if (idGUIEvento != null && idGUIEvento.equalsIgnoreCase("FIRMA")) {
			if (mimetype != null && "text/html".equals(mimetype)) {
				idGUIEvento = "EDITOR_HTML";
			} else {
				idGUIEvento = "EDITOR_APPLET";
			}
		}

		if (tipoAttivita != null && "G".equals(tipoAttivita)) {
			canvas = new SottomenuTaskDetail(nomeEntita, idProcess, idTipoProc, idTipoEvento, instance);
		} else if (tipoAttivita != null && "AC".equals(tipoAttivita)) {
			canvas = new SottomenuTaskBackDetail(nomeEntita, idProcess, idTipoProc, idTipoEvento, instance) {

				@Override
				public void back() {

					caricaDettaglioEventoAnnulla(nome);
				}
			};
		} else if ((idGUIEvento == null || "".equals(idGUIEvento)) && idTipoEvento != null && !"".equals(idTipoEvento)) {
			canvas = new TaskFlussoDetailDinamico(nomeEntita, idProcess, record, instance) {
				
				private Record downloadRecord = null;

				@Override
				public void afterShowRenderingPdf(final Record object) {
					buttonScaricaFile.setVisibility(Visibility.VISIBLE);
					downloadRecord = object;
				}
				
				@Override
				public void download() {
					if(downloadRecord != null) {
						downloadFileTask(downloadRecord);
					}
				};
			};
		} else if (idGUIEvento.equalsIgnoreCase("DETT_UD_GEN")) {
			canvas = new TaskDettUdGenDetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("DETT_FASCICOLO_GEN")) {
			canvas = new TaskDettFascicoloGenDetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idFolder, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("DETT_FASCICOLO")) {
			canvas = new TaskDettFascicoloDetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idFolder, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("DETT_FASCICOLO_GEN_COMPLETO")) {
			canvas = new TaskDettFascicoloGenCompletoDetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idFolder, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("DETT_FASCICOLO_TSO")) {
			canvas = new TaskDettFascicoloTSODetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idFolder, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("UNIONE_FILE")) {
			canvas = new TaskUnioneFileFascDetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idFolder, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("UNIONE_FILE_ATTO")) {
			canvas = new UnioneFileAttoDetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("PROPOSTA_ATTO")) {
			canvas = new PropostaAttoDetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("FIRMA_PROPOSTA_ATTO")) {
			canvas = new FirmaPropostaAttoDetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("FIRMA_PROPOSTA_CON_FILE_FIRME")) {
			canvas = new FirmaPropostaAttoConFileFirmeDetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("UNIONE_RELATA_PUBBL")) {
			canvas = new UnioneRelataPubblicazioneDetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("PROPOSTA_ATTO_2")) {
			if(AurigaLayout.isAttivaNuovaPropostaAtto2()) {
				if(AurigaLayout.isAttivaNuovaPropostaAtto2Completa()) {
					
					canvas = new TaskNuovaPropostaAtto2CompletaDetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance) {
						
						@Override
						public void afterShow() {
							
							final String idModDispositivo = getIdModDispositivo();
							
							List<Canvas> listaOtherEventoButtons = new ArrayList<Canvas>();
														
							if(isEseguibile() && isForzaModificaAttoDaSbloccare()) {
								final FrontendButton buttonSblocca = new FrontendButton("Sblocca", "pratiche/task/buttons/modifica.png");
								buttonSblocca.addClickHandler(new ClickHandler() {
	
									@Override
									public void onClick(ClickEvent event) {
										buttonSblocca.focusAfterGroup();										
										buttonSblocca.setVisibility(Visibility.HIDDEN);
										buttonSalvaProvvisorio.setVisibility(Visibility.VISIBLE);
										sblocca();										
									}
								});
								listaOtherEventoButtons.add(buttonSblocca);
								buttonSalvaProvvisorio.setVisibility(Visibility.HIDDEN);
							}

							if(getFlgSoloPreparazioneVersPubblicazione() && isEseguibile()) {								

								final FrontendButton buttonAggiornaVersDaPubblicare = new FrontendButton("Aggiorna versione da pubblicare", "pratiche/task/buttons/modifica.png");
								buttonAggiornaVersDaPubblicare.addClickHandler(new ClickHandler() {
	
									@Override
									public void onClick(ClickEvent event) {
										buttonAggiornaVersDaPubblicare.focusAfterGroup();										
										aggiornaVersDaPubblicare();
									}
								});
								listaOtherEventoButtons.add(buttonAggiornaVersDaPubblicare);
								
								final FrontendButton buttonFrecciaAggiornaVersDaPubblicare = new FrontendButton("", "menu/menu.png");								
								buttonFrecciaAggiornaVersDaPubblicare.setBaseStyle(it.eng.utility.Styles.frecciaDetailButton);
								buttonFrecciaAggiornaVersDaPubblicare.setIconHeight(16);
								buttonFrecciaAggiornaVersDaPubblicare.setIconWidth(12);						
								buttonFrecciaAggiornaVersDaPubblicare.addClickHandler(new ClickHandler() {
	
									@Override
									public void onClick(ClickEvent event) {
										buttonFrecciaAggiornaVersDaPubblicare.focusAfterGroup();																				
										MenuItem aggiornaVersDaPubblicareMenuItem = new MenuItem("Aggiorna versione da pubblicare");
										aggiornaVersDaPubblicareMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

											@Override
											public void onClick(MenuItemClickEvent event) {
												aggiornaVersDaPubblicare();
											}
										});										
//										MenuItem riportaVersConOmissisALastVersPubblicazioneFirmataMenuItem = new MenuItem("Riporta vers. con omissis alla versione da pubblicare originale");
//										riportaVersConOmissisALastVersPubblicazioneFirmataMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
//
//											@Override
//											public void onClick(MenuItemClickEvent event) {
//												riportaVersConOmissisALastVersPubblicazioneFirmata();
//											}
//										});										
										MenuItem riportaVersConOmissisAIntegraleMenuItem = new MenuItem("Riporta vers. con omissis da pubblicare alla versione integrale");
										riportaVersConOmissisAIntegraleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

											@Override
											public void onClick(MenuItemClickEvent event) {
												riportaVersConOmissisAIntegrale();
											}
										});																				
										Menu frecciaAggiornaVersDaPubblicareMenu = new Menu();										
										frecciaAggiornaVersDaPubblicareMenu.setItems(aggiornaVersDaPubblicareMenuItem, /*riportaVersConOmissisALastVersPubblicazioneFirmataMenuItem,*/ riportaVersConOmissisAIntegraleMenuItem);
										frecciaAggiornaVersDaPubblicareMenu.showContextMenu();
									}
								});
								listaOtherEventoButtons.add(buttonFrecciaAggiornaVersDaPubblicare);									
							} else {									
								if(uriFilePrimario != null && !"".equals(uriFilePrimario) && infoFilePrimario != null && infoFilePrimario.isFirmato()) {									
									if(flgShowAnteprimaDefinitiva && idModDispositivo != null && !"".equals(idModDispositivo)) {
										final FrontendButton buttonAnteprimaProvvedimento = new FrontendButton("Anteprima", "pratiche/task/buttons/anteprima_dispositivo.png");
										buttonAnteprimaProvvedimento.addClickHandler(new ClickHandler() {

											@Override
											public void onClick(ClickEvent event) {
												buttonAnteprimaProvvedimento.focusAfterGroup();																			
												List<MenuItem> listaAnteprimaProvvedimentoMenuItems = new ArrayList<MenuItem>();												
												MenuItem nuovaVersioneItem = new MenuItem("Nuova versione");
												nuovaVersioneItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
													@Override
													public void onClick(MenuItemClickEvent event) {
														anteprimaDispositivoDaModello(true, hasPrimarioDatiSensibili());
													}
												});	
												listaAnteprimaProvvedimentoMenuItems.add(nuovaVersioneItem);																									
												MenuItem attoFirmatoItem = new MenuItem("Atto firmato");
												attoFirmatoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

													@Override
													public void onClick(MenuItemClickEvent event) {		
														anteprimaAttoFirmato(nomeFilePrimario, uriFilePrimario, infoFilePrimario, nomeFilePrimarioOmissis, uriFilePrimarioOmissis, infoFilePrimarioOmissis);
													}
												});		
												listaAnteprimaProvvedimentoMenuItems.add(attoFirmatoItem);											
												Menu contextMenu = new Menu();										
												contextMenu.setItems(listaAnteprimaProvvedimentoMenuItems.toArray(new MenuItem[listaAnteprimaProvvedimentoMenuItems.size()]));
												contextMenu.showContextMenu();
											}
										});								
										listaOtherEventoButtons.add(buttonAnteprimaProvvedimento);
									} else {
										final FrontendButton buttonAnteprimaProvvedimento = new FrontendButton("Anteprima atto firmato", "pratiche/task/buttons/anteprima_dispositivo.png");
										buttonAnteprimaProvvedimento.addClickHandler(new ClickHandler() {
			
											@Override
											public void onClick(ClickEvent event) {
												buttonAnteprimaProvvedimento.focusAfterGroup();		
												anteprimaAttoFirmato(nomeFilePrimario, uriFilePrimario, infoFilePrimario, nomeFilePrimarioOmissis, uriFilePrimarioOmissis, infoFilePrimarioOmissis);												
											}
										});								
										listaOtherEventoButtons.add(buttonAnteprimaProvvedimento);		
									}
								} else if(idModDispositivo != null && !"".equals(idModDispositivo)) {
									final FrontendButton buttonAnteprimaProvvedimento = new FrontendButton("Anteprima provvedimento", "pratiche/task/buttons/anteprima_dispositivo.png");
									buttonAnteprimaProvvedimento.addClickHandler(new ClickHandler() {
		
										@Override
										public void onClick(ClickEvent event) {
											buttonAnteprimaProvvedimento.focusAfterGroup();			
											anteprimaDispositivoDaModello(hasPrimarioDatiSensibili());
										}
									});								
									listaOtherEventoButtons.add(buttonAnteprimaProvvedimento);		
								}												
							}
		
							if(idModAppendice != null && !"".equals(idModAppendice)) {											
								String labelTastoFoglioDatiCont = AurigaLayout.getParametroDB("LABEL_TASTO_FOGLIO_DATI_CONT");
								final FrontendButton buttonAnteprimaDatiSpesa = new FrontendButton(labelTastoFoglioDatiCont != null && !"".equals(labelTastoFoglioDatiCont) ? labelTastoFoglioDatiCont : "Anteprima movimenti contabili", "pratiche/task/buttons/anteprima_dati_spesa.png");
								buttonAnteprimaDatiSpesa.addClickHandler(new ClickHandler() {
		
									@Override
									public void onClick(ClickEvent event) {
										buttonAnteprimaDatiSpesa.focusAfterGroup();
										anteprimaDatiSpesaDaModello();	
									}
								});
								listaOtherEventoButtons.add(buttonAnteprimaDatiSpesa);	
							}
							
							if((idModFoglioFirme != null && !"".equals(idModFoglioFirme)) && (idModFoglioFirme2 != null && !"".equals(idModFoglioFirme2))) {
								String labelTastoMenuFirme = AurigaLayout.getParametroDB("LABEL_TASTO_MENU_FIRME");
								final FrontendButton buttonMenuFirme = new FrontendButton(labelTastoMenuFirme != null && !"".equals(labelTastoMenuFirme) ? labelTastoMenuFirme : "Firme", "pratiche/task/buttons/attestato.png");
								buttonMenuFirme.addClickHandler(new ClickHandler() {
		
									@Override
									public void onClick(ClickEvent event) {
										buttonMenuFirme.focusAfterGroup();
										final Menu firmeMenu = new Menu();
										String labelTastoFoglioFirme = AurigaLayout.getParametroDB("LABEL_TASTO_FOGLIO_FIRME");
										MenuItem anteprimaFoglioFirmeMenuItem = new MenuItem(labelTastoFoglioFirme != null && !"".equals(labelTastoFoglioFirme) ? labelTastoFoglioFirme : "Anteprima foglio firme");
										anteprimaFoglioFirmeMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

											@Override
											public void onClick(MenuItemClickEvent event) {
												anteprimaFoglioFirmeDaModello();
											}
										});
										firmeMenu.addItem(anteprimaFoglioFirmeMenuItem);
										String labelTastoFoglioFirme2 = AurigaLayout.getParametroDB("LABEL_TASTO_FOGLIO_FIRME_2");
										MenuItem anteprimaFoglioFirme2MenuItem = new MenuItem(labelTastoFoglioFirme2 != null && !"".equals(labelTastoFoglioFirme2) ? labelTastoFoglioFirme2 : "Anteprima riepilogo firme");
										anteprimaFoglioFirme2MenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

											@Override
											public void onClick(MenuItemClickEvent event) {
												anteprimaFoglioFirme2DaModello();
											}
										});
										firmeMenu.addItem(anteprimaFoglioFirme2MenuItem);
										firmeMenu.showContextMenu();
									}
								});
								listaOtherEventoButtons.add(buttonMenuFirme);
							} else if(idModFoglioFirme != null && !"".equals(idModFoglioFirme)) {
								String labelTastoFoglioFirme = AurigaLayout.getParametroDB("LABEL_TASTO_FOGLIO_FIRME");
								final FrontendButton buttonAnteprimaFoglioFirme = new FrontendButton(labelTastoFoglioFirme != null && !"".equals(labelTastoFoglioFirme) ? labelTastoFoglioFirme : "Anteprima foglio firme", "pratiche/task/buttons/attestato.png");
								buttonAnteprimaFoglioFirme.addClickHandler(new ClickHandler() {
		
									@Override
									public void onClick(ClickEvent event) {
										buttonAnteprimaFoglioFirme.focusAfterGroup();
										anteprimaFoglioFirmeDaModello();	
									}
								});
								listaOtherEventoButtons.add(buttonAnteprimaFoglioFirme);	
							} else if(idModFoglioFirme2 != null && !"".equals(idModFoglioFirme2)) {
								String labelTastoFoglioFirme2 = AurigaLayout.getParametroDB("LABEL_TASTO_FOGLIO_FIRME_2");
								final FrontendButton buttonAnteprimaFoglioFirme2 = new FrontendButton(labelTastoFoglioFirme2 != null && !"".equals(labelTastoFoglioFirme2) ? labelTastoFoglioFirme2 : "Anteprima riepilogo firme", "pratiche/task/buttons/attestato.png");
								buttonAnteprimaFoglioFirme2.addClickHandler(new ClickHandler() {
		
									@Override
									public void onClick(ClickEvent event) {
										buttonAnteprimaFoglioFirme2.focusAfterGroup();
										anteprimaFoglioFirme2DaModello();	
									}
								});
								listaOtherEventoButtons.add(buttonAnteprimaFoglioFirme2);	
							} 
							
							if(idModSchedaTrasp != null && !"".equals(idModSchedaTrasp)) {
								String labelTastoSchedaTrasp = AurigaLayout.getParametroDB("LABEL_TASTO_SCHEDA_TRASP");
								final FrontendButton buttonAnteprimaSchedaTrasp = new FrontendButton(labelTastoSchedaTrasp != null && !"".equals(labelTastoSchedaTrasp) ? labelTastoSchedaTrasp : "Anteprima scheda trasparenza", "menu/amministrazione_trasparente.png");
								buttonAnteprimaSchedaTrasp.addClickHandler(new ClickHandler() {
		
									@Override
									public void onClick(ClickEvent event) {
										buttonAnteprimaSchedaTrasp.focusAfterGroup();
										anteprimaSchedaTraspDaModello();	
									}
								});
								listaOtherEventoButtons.add(buttonAnteprimaSchedaTrasp);	
							} 
								
							// Se nella Call_ExecAtt la variabile #IdModelloDoc è vuota le varie azioni di "Scarica" delle versioni del testo atto NON devono apparire (atto completo, versione modificabile ecc)
							if(uriFilePrimario != null && !"".equals(uriFilePrimario) && infoFilePrimario != null && infoFilePrimario.isFirmato()) {
								
								final FrontendButton buttonScaricaAttoFirmato = new FrontendButton("Scarica atto firmato", "pratiche/task/buttons/scaricaPdf.png");
								buttonScaricaAttoFirmato.addClickHandler(new ClickHandler() {

									@Override
									public void onClick(ClickEvent event) {
										buttonScaricaAttoFirmato.focusAfterGroup();														
										if (infoFilePrimario.getTipoFirma().startsWith("CAdES")) {																						
											MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
											firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

												@Override
												public void onClick(MenuItemClickEvent event) {
													Record lRecord = new Record();
													lRecord.setAttribute("displayFilename", nomeFilePrimario);
													lRecord.setAttribute("uri", uriFilePrimario);
													lRecord.setAttribute("sbustato", "false");
													lRecord.setAttribute("remoteUri", "true");
													DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");	
												}
											});											
											MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
											sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

												@Override
												public void onClick(MenuItemClickEvent event) {
													Record lRecord = new Record();
													lRecord.setAttribute("displayFilename", nomeFilePrimario);
													lRecord.setAttribute("uri", uriFilePrimario);
													lRecord.setAttribute("sbustato", "true");
													lRecord.setAttribute("remoteUri", "true");
													DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");	
												}
											});																						
											Menu scaricaAttoFirmatoMenu = new Menu();										
											scaricaAttoFirmatoMenu.setItems(firmato, sbustato);
											scaricaAttoFirmatoMenu.showContextMenu();											
										} else {											
											Record lRecord = new Record();
											lRecord.setAttribute("displayFilename", nomeFilePrimario);
											lRecord.setAttribute("uri", uriFilePrimario);
											lRecord.setAttribute("sbustato", "false");
											lRecord.setAttribute("remoteUri", "true");
											DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");	
										}	
									}									
								});	
								listaOtherEventoButtons.add(buttonScaricaAttoFirmato);
								
								if(idModDispositivo != null && !"".equals(idModDispositivo)) {
									if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_SCARICO_ATTO_VERS_MODIFICABILE")) {
										final FrontendButton buttonScaricaVerModificabile = new FrontendButton("Scarica vers modificabile", "pratiche/task/buttons/scarica_ver_modificabile.png");
										buttonScaricaVerModificabile.addClickHandler(new ClickHandler() {
										
											@Override
											public void onClick(ClickEvent event) {
												generaDispositivoDaModelloVersModificabile(new ServiceCallback<Record>() {
													
													@Override
													public void execute(final Record recordDownload) {
														Record lRecord = new Record();
														lRecord.setAttribute("displayFilename", recordDownload.getAttribute("nomeFile"));
														lRecord.setAttribute("uri", recordDownload.getAttribute("uri"));
														lRecord.setAttribute("sbustato", "false");
														lRecord.setAttribute("remoteUri", "false");
														DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
													}
												});
											}
										});
										listaOtherEventoButtons.add(buttonScaricaVerModificabile);
									}
								}
								
							} else if(idModDispositivo != null && !"".equals(idModDispositivo)) {								
								final FrontendButton buttonScaricaFile = new FrontendButton("Scarica", "pratiche/task/buttons/scaricaPdf.png");
								buttonScaricaFile.addClickHandler(new ClickHandler() {

									@Override
									public void onClick(ClickEvent event) {
										buttonScaricaFile.focusAfterGroup();																			
										List<MenuItem> listaScaricaMenuItems = new ArrayList<MenuItem>();																				
										if(hasDatiSensibili()) {											
											MenuItem scaricaFileUnioneVersIntegraleMenuItem = new MenuItem("Atto completo (vers. integrale)");
											scaricaFileUnioneVersIntegraleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

												@Override
												public void onClick(MenuItemClickEvent event) {														
													generaFileUnioneVersIntegrale(new ServiceCallback<Record>() {
														
														@Override
														public void execute(final Record recordFileUnione) {			
															final Record lFileUnione = new Record();
															lFileUnione.setAttribute("displayFilename", recordFileUnione.getAttribute("nomeFileVersIntegrale"));
															lFileUnione.setAttribute("uri", recordFileUnione.getAttribute("uriVersIntegrale"));
															lFileUnione.setAttribute("sbustato", "false");
															lFileUnione.setAttribute("remoteUri", "false");
															DownloadFile.downloadFromRecord(lFileUnione, "FileToExtractBean");
														}
													});			
												}
											});		
											listaScaricaMenuItems.add(scaricaFileUnioneVersIntegraleMenuItem);																							
											MenuItem scaricaFileUnioneVersOmissisMenuItem = new MenuItem("Atto completo (vers. per pubblicazione)");
											scaricaFileUnioneVersOmissisMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

												@Override
												public void onClick(MenuItemClickEvent event) {														
													generaFileUnioneVersXPubbl(new ServiceCallback<Record>() {
														
														@Override
														public void execute(final Record recordFileUnione) {			
															final Record lFileUnioneOmissis = new Record();
															lFileUnioneOmissis.setAttribute("displayFilename", recordFileUnione.getAttribute("nomeFile"));	
															lFileUnioneOmissis.setAttribute("uri", recordFileUnione.getAttribute("uri"));
															lFileUnioneOmissis.setAttribute("sbustato", "false");
															lFileUnioneOmissis.setAttribute("remoteUri", "false");
															DownloadFile.downloadFromRecord(lFileUnioneOmissis, "FileToExtractBean");
														}
													});			
												}
											});											
											listaScaricaMenuItems.add(scaricaFileUnioneVersOmissisMenuItem);																																
										} else {
											MenuItem scaricaFileUnioneMenuItem = new MenuItem("Atto completo");
											scaricaFileUnioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

												@Override
												public void onClick(MenuItemClickEvent event) {														
													generaFileUnioneVersIntegrale(new ServiceCallback<Record>() {
														
														@Override
														public void execute(final Record recordFileUnione) {			
															final Record lFileUnione = new Record();
															lFileUnione.setAttribute("displayFilename", recordFileUnione.getAttribute("nomeFileVersIntegrale"));
															lFileUnione.setAttribute("uri", recordFileUnione.getAttribute("uriVersIntegrale"));
															lFileUnione.setAttribute("sbustato", "false");
															lFileUnione.setAttribute("remoteUri", "false");
															DownloadFile.downloadFromRecord(lFileUnione, "FileToExtractBean");
														}
													});			
												}
											});		
											listaScaricaMenuItems.add(scaricaFileUnioneMenuItem);
										}											
										MenuItem versionePerVerificaOmissisItem = new MenuItem("Versione per verifica omissis");
										versionePerVerificaOmissisItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
											@Override
											public void onClick(MenuItemClickEvent event) {
												generaDispositivoDaModelloVersPerVerificaOmissis(new ServiceCallback<Record>() {
													
													@Override
													public void execute(final Record recordDownload) {
														Record lRecord = new Record();
														lRecord.setAttribute("displayFilename", recordDownload.getAttribute("nomeFile"));
														lRecord.setAttribute("uri", recordDownload.getAttribute("uri"));
														lRecord.setAttribute("sbustato", "false");
														lRecord.setAttribute("remoteUri", "false");
														DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
													}
												});
											}
										});	
										listaScaricaMenuItems.add(versionePerVerificaOmissisItem);	
										
										if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_SCARICO_ATTO_VERS_MODIFICABILE")) {
										
											MenuItem versioneModificabileItem = new MenuItem("Versione modificabile (doc)");
											versioneModificabileItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
												@Override
												public void onClick(MenuItemClickEvent event) {
													generaDispositivoDaModelloVersModificabile(new ServiceCallback<Record>() {
														
														@Override
														public void execute(final Record recordDownload) {
															Record lRecord = new Record();
															lRecord.setAttribute("displayFilename", recordDownload.getAttribute("nomeFile"));
															lRecord.setAttribute("uri", recordDownload.getAttribute("uri"));
															lRecord.setAttribute("sbustato", "false");
															lRecord.setAttribute("remoteUri", "false");
															DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
														}
													});
												}
											});	
											listaScaricaMenuItems.add(versioneModificabileItem);
										}
										
										Menu contextMenu = new Menu();										
										contextMenu.setItems(listaScaricaMenuItems.toArray(new MenuItem[listaScaricaMenuItems.size()]));
										contextMenu.showContextMenu();
									}
								});								
								listaOtherEventoButtons.add(buttonScaricaFile);							
							}
							
							// Aggiungo i bottoni per visualizzare gli emendamenti
							Boolean flgPresentiEmendamenti = getRecordEvento() != null ? getRecordEvento().getAttributeAsBoolean("flgPresentiEmendamenti") : null;
							if (flgPresentiEmendamenti != null && flgPresentiEmendamenti) {																
								final FrontendButton buttonListaEmendamenti = new FrontendButton("Lista emendamenti", "buttons/altreOp.png");
								buttonListaEmendamenti.addClickHandler(new ClickHandler() {
	
									@Override
									public void onClick(ClickEvent event) {
										recuperaListaEmendamenti(new ServiceCallback<Record>() {
											
											@Override
											public void execute(Record record) {
												apriListaEmendamenti();
											}
										});
									}
								});								
								listaOtherEventoButtons.add(buttonListaEmendamenti);	
								
								final FrontendButton buttonFrecciaApriEmendamentiWindow = new FrontendButton("", "menu/menu.png");								
								buttonFrecciaApriEmendamentiWindow.setBaseStyle(it.eng.utility.Styles.frecciaDetailButton);
								buttonFrecciaApriEmendamentiWindow.setIconHeight(16);
								buttonFrecciaApriEmendamentiWindow.setIconWidth(12);						
								buttonFrecciaApriEmendamentiWindow.addClickHandler(new ClickHandler() {
	
									@Override
									public void onClick(ClickEvent event) {
										buttonFrecciaApriEmendamentiWindow.focusAfterGroup();										
										Menu frecciaApriEmendamentiMenu = new Menu();										
										MenuItem apriEmendamentiMenuItem = new MenuItem("Scorri lista emendamenti");
										apriEmendamentiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
											@Override
											public void onClick(MenuItemClickEvent event) {
												recuperaListaEmendamenti(new ServiceCallback<Record>() {
													
													@Override
													public void execute(Record record) {
														apriEmendamentiWindow();
													}
												});												
											}
										});										
										MenuItem apriListaEmendamentiMenuItem = new MenuItem("Lista emendamenti");
										apriListaEmendamentiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
											@Override
											public void onClick(MenuItemClickEvent event) {
												recuperaListaEmendamenti(new ServiceCallback<Record>() {
													
													@Override
													public void execute(Record record) {
														apriListaEmendamenti();
													}
												});												
											}
										});										
										frecciaApriEmendamentiMenu.setItems(apriListaEmendamentiMenuItem, apriEmendamentiMenuItem);
										frecciaApriEmendamentiMenu.showContextMenu();
									}
								});								
								listaOtherEventoButtons.add(buttonFrecciaApriEmendamentiWindow);							
							}
							
							Record detailRecord = new Record(vm.getValues());
							if (detailRecord.getAttributeAsBoolean("abilModificaCodAttoCMTO") && (isReadOnly() || !isEseguibile())) {
								final FrontendButton buttonModificaCodAttoCMTO = new FrontendButton("Modifica cod. atto", "pratiche/task/buttons/modificaCodAtto.png");
								buttonModificaCodAttoCMTO.addClickHandler(new ClickHandler() {
		
									@Override
									public void onClick(ClickEvent event) {
										buttonModificaCodAttoCMTO.focusAfterGroup();
										modificaCodAttoCMTO(new ServiceCallback<Record>() {
											
											@Override
											public void execute(Record record) {
												reload();
											}
										});
									}
								});
								listaOtherEventoButtons.add(buttonModificaCodAttoCMTO);	
							}
							
							otherEventoButtonsLayout.setMembers(listaOtherEventoButtons.toArray(new Canvas[listaOtherEventoButtons.size()]));							
							otherEventoButtonsLayout.setVisibility(Visibility.VISIBLE);
						}	
					};		
				} else {
					canvas = new TaskNuovaPropostaAtto2Detail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance) {
						
						@Override
						public void afterShow() {
														
							List<Canvas> listaOtherEventoButtons = new ArrayList<Canvas>();
							
							final FrontendButton buttonAnteprimaProvvedimento = new FrontendButton("Anteprima provvedimento", "pratiche/task/buttons/anteprima_dispositivo.png");
							buttonAnteprimaProvvedimento.addClickHandler(new ClickHandler() {

								@Override
								public void onClick(ClickEvent event) {
									buttonAnteprimaProvvedimento.focusAfterGroup();
									anteprimaProvvedimento(nomeFilePrimario, uriFilePrimario, infoFilePrimario, nomeFilePrimarioOmissis, uriFilePrimarioOmissis, infoFilePrimarioOmissis);
								}
							});
							listaOtherEventoButtons.add(buttonAnteprimaProvvedimento);
							
							if(idModAppendice != null && !"".equals(idModAppendice)) {								
								final FrontendButton buttonAnteprimaDatiSpesa = new FrontendButton("Anteprima dati di spesa", "pratiche/task/buttons/anteprima_dati_spesa.png");
								buttonAnteprimaDatiSpesa.addClickHandler(new ClickHandler() {
		
									@Override
									public void onClick(ClickEvent event) {
										buttonAnteprimaDatiSpesa.focusAfterGroup();
										anteprimaDatiSpesaDaModello();	
									}
								});								
								listaOtherEventoButtons.add(buttonAnteprimaDatiSpesa);
							} 
							
							//TODO: da aggiungere alla completa
							String idUdPubblPost = AurigaLayout.getParametroDB("ID_UD_PUBBL_POST");
							if(idUdPubblPost != null && !"".equals(idUdPubblPost)) {
								StringSplitterClient st = new StringSplitterClient(idUdPubblPost, ";");
								Set<String> setIdUdPubblicaPost = new HashSet<String>();
								for (String token : st.getTokens()) {
									setIdUdPubblicaPost.add(token);
								}
								if(idUd != null && setIdUdPubblicaPost.contains(idUd)) {
									
									final FrontendButton buttonPubblicaPost = new FrontendButton("Pubblica", "pratiche/task/presenta_istanza.png");
									buttonPubblicaPost.addClickHandler(new ClickHandler() {
			
										@Override
										public void onClick(ClickEvent event) {
											buttonPubblicaPost.focusAfterGroup();
											pubblicaPost();	
										}
									});
										
									listaOtherEventoButtons.add(buttonPubblicaPost);
								}
							}
							
							otherEventoButtonsLayout.setMembers(listaOtherEventoButtons.toArray(new Canvas[listaOtherEventoButtons.size()]));							
							otherEventoButtonsLayout.setVisibility(Visibility.VISIBLE);
						}	
					};		
				}											
			} else {
				canvas = new PropostaAtto2Detail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance);				
			}		
		}  else if (idGUIEvento.equalsIgnoreCase("PROPOSTA_ANN_ATTO_2")) {
			if(AurigaLayout.isAttivaNuovaPropostaAtto2()) {
				if(AurigaLayout.isAttivaNuovaPropostaAtto2Completa()) {
					canvas = new TaskRevocaNuovaPropostaAtto2CompletaDetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance) {
						
						@Override
						public void afterShow() {
							final String idModDispositivo = getIdModDispositivo();
							if(idModDispositivo != null && !"".equals(idModDispositivo)) {
								final FrontendButton buttonAnteprimaProvvedimento = new FrontendButton("Anteprima provvedimento", "pratiche/task/buttons/anteprima_dispositivo.png");
								buttonAnteprimaProvvedimento.addClickHandler(new ClickHandler() {
									
									@Override
									public void onClick(ClickEvent event) {
										buttonAnteprimaProvvedimento.focusAfterGroup();
										anteprimaProvvedimento(nomeFilePrimario, uriFilePrimario, infoFilePrimario, nomeFilePrimarioOmissis, uriFilePrimarioOmissis, infoFilePrimarioOmissis);																	
									}
								});
								otherEventoButtonsLayout.setMembers(buttonAnteprimaProvvedimento);
								otherEventoButtonsLayout.setVisibility(Visibility.VISIBLE);
							}	
						}	
					};			
				} else {
					canvas = new TaskRevocaNuovaPropostaAtto2Detail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance) {
						
						@Override
						public void afterShow() {
														
							final FrontendButton buttonAnteprimaProvvedimento = new FrontendButton("Anteprima provvedimento", "pratiche/task/buttons/anteprima_dispositivo.png");
							buttonAnteprimaProvvedimento.addClickHandler(new ClickHandler() {
	
								@Override
								public void onClick(ClickEvent event) {
									buttonAnteprimaProvvedimento.focusAfterGroup();
									anteprimaProvvedimento(nomeFilePrimario, uriFilePrimario, infoFilePrimario, nomeFilePrimarioOmissis, uriFilePrimarioOmissis, infoFilePrimarioOmissis);
								}
							});
							otherEventoButtonsLayout.setMembers(buttonAnteprimaProvvedimento);							
							otherEventoButtonsLayout.setVisibility(Visibility.VISIBLE);
						}	
					};			
				}									
			}	
		} else if (idGUIEvento.equalsIgnoreCase("DETT_RICH_ACCESSO_ATTI_URB")) {
			canvas = new TaskNuovaRichiestaAccessoAttiDetail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance) {
				
				@Override
				public void afterShow() {
					Record detailRecord = new Record(vm.getValues());
					if (detailRecord.getAttributeAsBoolean("abilAbilitaAppuntamentoDaAgenda")) {
						final FrontendButton abilitaAppuntamentoDaAgendaButton = new FrontendButton("Abilita appuntamento da Agenda", "richiesteAccessoAtti/azioni/abilitaAppuntamentoDaAgenda.png");
						abilitaAppuntamentoDaAgendaButton.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								abilitaAppuntamentoDaAgendaButton.focusAfterGroup();
								manageAzioneSuRichAccessoAttiSingola("ABILITA_APPUNTAMENTO_DA_AGENDA", new ServiceCallback<Record>() {
									
									@Override
									public void execute(Record record) {
										boolean success = manageErroreSingoloAzioneRichiestaAtti(record, true);
										if(success) {
											// nascondo tutto otherEventoButtonsLayout perchè dentro c'è solo abilitaAppuntamentoDaAgendaButton
											otherEventoButtonsLayout.setVisibility(Visibility.HIDDEN);
										}
									}
								});
							}
						});
						otherEventoButtonsLayout.setMembers(abilitaAppuntamentoDaAgendaButton);
						otherEventoButtonsLayout.setVisibility(Visibility.VISIBLE);
					}
				}
			};
		} else if (idGUIEvento.equalsIgnoreCase("PUBBLICAZIONE_ALBO_2")) {
			canvas = new PubblicazioneAlbo2Detail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("FIRMA_PROPOSTA_ATTO_2")) {
			canvas = new FirmaPropostaAtto2Detail(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("INVIO_COLLEGIO_SINDACALE")) {
			canvas = new InvioCollegioSindacaleDetail(nomeEntita, idProcess, idUd, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("REGISTRAZIONE_ATTO")) {
			canvas = new RegistrazioneAttoDetail(nomeEntita, null, idProcess, record, instance);
		} else if (idGUIEvento.equalsIgnoreCase("REVISIONE_GRAFICA_ORGANIGRAMMA")) {
			canvas = new TaskPropostaOrganigrammaDetail(nomeEntita, null, idProcess, nomeFlussoWF, processNameWF, idUd, record, instance) {
				
				@Override
				public void afterShow() {
												
					final FrontendButton buttonAnteprimaProposta = new FrontendButton("Anteprima proposta", "pratiche/task/buttons/anteprima_dispositivo.png");
					buttonAnteprimaProposta.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							buttonAnteprimaProposta.focusAfterGroup();
							anteprimaPropostaDaModello(hasDatiSensibili());	
						}
					});

					if(isEseguibile()) {
						
						String titleApriEditorOrganigramma = "Visualizza organigramma";
						if(getAzione() != null) {
							if(getAzione().equals("PDF_COMPLETO")) {
								titleApriEditorOrganigramma = "Genera pdf organigramma e funzionigramma";
							} else if(getAzione().equals("PDF_GRAFO")) {
								titleApriEditorOrganigramma = "Genera pdf organigramma";
							} else if(getAzione().equals("PDF_FUNZIONIGRAMMA")) {
								titleApriEditorOrganigramma = "Genera funzionigramma";
							} 
						}
						
						final FrontendButton apriEditorOrganigrammaButton = new FrontendButton(titleApriEditorOrganigramma, "menu/organigramma.png");				
						apriEditorOrganigrammaButton.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								apriEditorOrganigrammaButton.focusAfterGroup();
								apriEditorOrganigramma();						
							}
						});
					
						otherEventoButtonsLayout.setMembers(apriEditorOrganigrammaButton, buttonAnteprimaProposta);
						
					} else {
						otherEventoButtonsLayout.setMembers(buttonAnteprimaProposta);						
					}
					
					otherEventoButtonsLayout.setVisibility(Visibility.VISIBLE);
				}	
			};
		} else if (idGUIEvento.equalsIgnoreCase("EDITOR_APPLET")) {
			if (isEseguibile) {
				canvas = new EditorAppletDetail(nomeEntita, null, idProcess, record, instance) {

					@Override
					public boolean isAnteprimaDocumento() {

						return false;
					}
				};
			} else if (uriFile != null && !"".equals(uriFile)) {
				canvas = new RenderingPdfDetail(nomeEntita, estremiUd, uriFile, nomeFile, idProcess, record) {

					private Record downloadRecord = null;

					@Override
					public void afterShow(final Record object) {
						buttonScaricaFile.setVisibility(Visibility.VISIBLE);
						downloadRecord = object;
					}
					
					@Override
					public void download() {
						if(downloadRecord != null) {
							downloadFileTask(downloadRecord);
						}
					};
					
					@Override
					public void back() {

						caricaDettaglioEventoAnnulla(nome);
					}
				};
			}
		} else if (idGUIEvento.equalsIgnoreCase("EDITOR_HTML")) {
			if (isEseguibile) {
				canvas = new EditorHtmlFlussoDetail(nomeEntita, null, idProcess, record, instance) {

					@Override
					public boolean isAnteprimaDocumento() {
						return false;
					}
				};
			} else if (uriFile != null && !"".equals(uriFile)) {
				canvas = new RenderingPdfDetail(nomeEntita, estremiUd, uriFile, nomeFile, idProcess, record) {

					private Record downloadRecord = null;

					@Override
					public void afterShow(final Record object) {
						buttonScaricaFile.setVisibility(Visibility.VISIBLE);
						downloadRecord = object;
					}
					
					@Override
					public void download() {
						if(downloadRecord != null) {
							downloadFileTask(downloadRecord);
						}
					};

					@Override
					public void back() {

						caricaDettaglioEventoAnnulla(nome);
					}
				};
			}
		} else
			canvas = buildDettaglioEvento(record);

		if (canvas == null) {
			canvas = new BackDetail(idGUIEvento) {

				@Override
				public void back() {

					caricaDettaglioEventoAnnulla(nome);
				}
			};
		}

		return canvas;
	}

	public CustomDetail buildDettaglioEvento(Record record) {

		final String nome = record.getAttribute("nome");
		final String nomeGruppoApp = record.getAttribute("nomeGruppoApp");
		final String idGUIEvento = record.getAttribute("idGUIEvento");
		final String idTipoEvento = record.getAttribute("idTipoEvento");
		final String tipoAttivita = record.getAttribute("tipoAttivita");
		final String nomeEntita = "evento" + idTipoEvento;
		final String uriModAssDocTask = record.getAttribute("uriModAssDocTask");
		final String nomeFile = (record.getAttribute("nomeFile") != null && !"".equals(record.getAttribute("nomeFile"))) ? record.getAttribute("nomeFile") : getDisplayNameEvento(nome);
		final String uriFile = record.getAttribute("uriFile");

		boolean isEseguibile = true;
		if (record != null && record.getAttribute("flgEseguibile") != null) {
			isEseguibile = "1".equals(record.getAttribute("flgEseguibile"));
		}

		CustomDetail canvas = null;

		buttonScaricaFile.setVisibility(Visibility.HIDDEN);

		if (tipoAttivita != null && "G".equals(tipoAttivita)) {
			canvas = new SottomenuTaskDetail(nomeEntita, idProcess, idTipoProc, idTipoEvento, instance);
		} else if (tipoAttivita != null && "AC".equals(tipoAttivita)) {
			canvas = new SottomenuTaskBackDetail(nomeEntita, idProcess, idTipoProc, idTipoEvento, instance) {

				@Override
				public void back() {

					caricaDettaglioEventoAnnulla(nome);
				}
			};
		} else if ((idGUIEvento == null || "".equals(idGUIEvento)) && idTipoEvento != null && !"".equals(idTipoEvento)) {
			canvas = new EventoLayoutDinamico(nomeEntita, idProcess, record, instance) {

				@Override
				public void back() {

					caricaDettaglioEventoAnnulla(nome);
				}
			};
		} else if (idGUIEvento.equalsIgnoreCase("EDITOR_HTML")) {
			if (isEseguibile) {
				canvas = new EditorHtmlDetail(nomeEntita, null, idProcess, record, instance) {

					@Override
					public void back() {

						caricaDettaglioEventoAnnulla(nome);
					}
				};
			} else if (uriFile != null && !"".equals(uriFile)) {
				canvas = new RenderingPdfDetail(nomeEntita, uriFile, nomeFile, idProcess, record) {

					private Record downloadRecord = null;

					@Override
					public void afterShow(final Record object) {
						buttonScaricaFile.setVisibility(Visibility.VISIBLE);
						downloadRecord = object;
					}
					
					@Override
					public void download() {
						if(downloadRecord != null) {
							downloadFileTask(downloadRecord);
						}
					};

					@Override
					public void back() {

						caricaDettaglioEventoAnnulla(nome);
					}
				};
			}
		} else if (idGUIEvento.equalsIgnoreCase("RENDERING_PDF") && uriModAssDocTask != null && !"".equals(uriModAssDocTask)) {
			canvas = new RenderingPdfDetail(nomeEntita, uriModAssDocTask, nomeFile, idProcess, record) {

				private Record downloadRecord = null;

				@Override
				public void afterShow(final Record object) {
					buttonScaricaFile.setVisibility(Visibility.VISIBLE);
					downloadRecord = object;
				}
				
				@Override
				public void download() {
					if(downloadRecord != null) {
						downloadFileTask(downloadRecord);
					}
				};

				@Override
				public void back() {

					caricaDettaglioEventoAnnulla(nome);
				}
			};
		} else if (idGUIEvento.equalsIgnoreCase("ALLEGA_DOCUMENTAZIONE")) {

			if (nomeGruppoApp != null && !"".equals(nomeGruppoApp)) {
				canvas = new AllegaDocumentazioneBackDetail(nomeEntita, idProcess, record, nomeEntita, instance) {

					@Override
					public void back() {

						caricaDettaglioEventoAnnulla(nome);
					}
				};
			} else {
				canvas = new AllegaDocumentazioneDetail(nomeEntita, idProcess, record, nomeEntita, instance);
			}
		} else if (idGUIEvento.equalsIgnoreCase("NOTE")) {
			canvas = new AggiungiNoteDetail(nomeEntita, idProcess, record, instance) {

				@Override
				public void back() {

					caricaDettaglioEventoPrec();
				}
			};
		} else if (idGUIEvento.equalsIgnoreCase("RIEPILOGO_PROCEDIMENTO")) {
			canvas = new RiepilogoProcedimentoDetail(nomeEntita, idProcess);
		} else if (idGUIEvento.equalsIgnoreCase("GRAFICO_FLUSSO")) {
			showGraficoFlusso();
			return null;
		}

		if (canvas == null) {
			canvas = new BackDetail(idGUIEvento) {

				@Override
				public void back() {

					caricaDettaglioEventoAnnulla(nome);
				}
			};
		}

		return canvas;
	}
	
	public void importFromOnlyOne() {
		GWTRestService<Record, Record> lOnlyOneDatasource = new GWTRestService<Record, Record>("OnlyOneDatasource");
		lOnlyOneDatasource.call(first, new ServiceCallback<Record>() {

			@Override
			public void execute(final Record output) {
				if(currentDetail != null && (currentDetail instanceof TaskFlussoInterface)) {
					AurigaLayout.addMessage(new MessageBean("Recuperati con successo i dati aggiornati della pratica WF; per visualizzare i dati aggiornati clicca nuovamente sul link del passo (previo salvataggio se hai dati variati da salvare)", "", MessageType.INFO));
				} else {
					AurigaLayout.addMessage(new MessageBean("Recuperati con successo i dati aggiornati della pratica WF", "", MessageType.INFO));
				}
			}
		});
	}

	public void showIterSvolto() {
		MostraIterSvoltoModalWindows windows = new MostraIterSvoltoModalWindows(idProcess, estremi);
		windows.show();
	}
	
	private void showConfrontaDocumenti() {
		Record rec = new Record(detailRecord.toMap());
		rec.setAttribute("idUd", detailRecord.getAttribute("idUd"));
		final RecordList listaAtti = new RecordList();
		listaAtti.add(rec);
		ConfrontaDocumentiLayout.showConfrontaDocumenti(listaAtti);
	}

	public void showGraficoFlusso() {
		String idDefProcFlow = first.getAttribute("idDefProcFlow");
		String idInstProcFlow = first.getAttribute("idInstProcFlow");
		String url = AurigaLayout.getParametroDB("URL_MODELLATORE_PROCESSI");
		String username = AurigaLayout.getParametroDB("USERID_ACTIVITI_FLOW") != null ? AurigaLayout.getParametroDB("USERID_ACTIVITI_FLOW") : "mattia";
		String password = AurigaLayout.getParametroDB("PWD_ACTIVITI_FLOW") != null ? AurigaLayout.getParametroDB("PWD_ACTIVITI_FLOW") : "zanin";
		if ((url != null && !"".equals(url)) && (username != null && !"".equals(username)) && (password != null && !"".equals(password))) {
			if ((idDefProcFlow != null && !"".equals(idDefProcFlow)) && (idInstProcFlow != null && !"".equals(idInstProcFlow))) {
				if (!url.endsWith("/"))
					url += "/";
				url += "diagram-viewer/showDiagram.html?usernameExt=" + username + "&passwordExt=" + password + "&processDefinitionId=" + idDefProcFlow
						+ "&processInstanceId=" + idInstProcFlow;
				HTMLFlow htmlFlow = new HTMLFlow();
				htmlFlow.setHeight100();
				htmlFlow.setWidth100();
				htmlFlow.setContentsURL(url);
				htmlFlow.setContentsType(ContentsType.PAGE);
				Layout.addMaximizedModalWindow("grafico_del_flusso", "Grafico del flusso", "pratiche/task/grafico_del_flusso.png", htmlFlow);
			} else {
				Layout.addMessage(new MessageBean("Nessun flusso avviato", "", MessageType.ERROR));
			}
		} else {
			Layout.addMessage(new MessageBean("Parametri in DB non configurati", "", MessageType.ERROR));
		}
	}

	public CustomDetail creaHtmlFlow(String nomeEntita, String contentsUrl) {
		final CustomDetail detail = new CustomDetail(nomeEntita);
		detail.setHeight100();
		detail.setWidth100();
		detail.setOverflow(Overflow.HIDDEN);
		HTMLFlow htmlFlow = new HTMLFlow();
		htmlFlow.setHeight100();
		htmlFlow.setWidth100();
		htmlFlow.setContentsURL(contentsUrl);
		htmlFlow.setContentsType(ContentsType.PAGE);
		detail.setMembers(htmlFlow);
		return detail;
	}

	public void downloadFileTask(final Record object) {
		if (object.getAttributeAsBoolean("firmato")) {
			Menu downloadMenu = new Menu();
			MenuItem downloadMenuItem = new MenuItem(object.getAttribute("nomeFile"));
			downloadMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					downloadFile(object.getAttribute("uriFile"), object.getAttribute("nomeFile"));
				}
			});
			downloadMenu.addItem(downloadMenuItem);
			MenuItem downloadSbustatoMenuItem = new MenuItem(object.getAttribute("nomeFilePdf"));
			downloadSbustatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					downloadFile(object.getAttribute("uriFilePdf"), object.getAttribute("nomeFilePdf"));
				}
			});
			downloadMenu.addItem(downloadSbustatoMenuItem);
			downloadMenu.showContextMenu();
		} else {
			downloadFile(object.getAttribute("uriFilePdf"), object.getAttribute("nomeFilePdf"));
		}
	}

	public void downloadFile(String uri, String display) {
		Record record = new Record();
		record.setAttribute("displayFilename", display);
		record.setAttribute("uri", uri);
		record.setAttribute("sbustato", "false");
		record.setAttribute("remoteUri", true);
		DownloadFile.downloadFromRecord(record, "FileToExtractBean");
	}

	public String getIconaVoceMenu(String flgFatta, String flgEsito, String flgToDo) {
		// if (flgFatta != null) {
		// if ("2".equals(flgFatta)) {
		// return "pratiche/task/flgFatta/fatta.png";
		// } else if ("1".equals(flgFatta)) {
		// return "pratiche/task/flgFatta/iniziata.png";
		// } else if ("-1".equals(flgFatta)) {
		// return "pratiche/task/flgFatta/non_fatta.png";
		// }
		// }
		// return "blank.png";
		if (flgFatta != null) {
			if ("2".equals(flgFatta)) {
				if (flgEsito != null) {
					if ("OK".equalsIgnoreCase(flgEsito)) {
						return "pratiche/task/icone/svolta_OK.png";
					} else if ("W".equalsIgnoreCase(flgEsito)) {
						return "pratiche/task/icone/svolta_W.png";
					} else if ("KO".equalsIgnoreCase(flgEsito)) {
						return "pratiche/task/icone/svolta_KO.png";
					}
				}
				return "pratiche/task/icone/svolta_OK.png";
			} else if ("1".equals(flgFatta)) {
				return "pratiche/task/icone/in_lavorazione.png";
			} else if ("-1".equals(flgFatta)) {
				return "pratiche/task/icone/da_svolgere.png";
			}
		}
		if (flgToDo != null && "1".equals(flgToDo)) {
			return "pratiche/task/icone/TODO.png";
		}
		return "pratiche/task/icone/non_disponibile.png";
	}

	public String getPromptIconaVoceMenu(String flgFatta, String flgEsito, String flgToDo, String motiviNonEseg) {
		if (flgFatta != null) {
			if ("2".equals(flgFatta)) {
				if (flgEsito != null) {
					if ("OK".equalsIgnoreCase(flgEsito)) {
						return "Svolta: esito positivo";
					} else if ("W".equalsIgnoreCase(flgEsito)) {
						return "Svolta: inseriti avvertimenti";
					} else if ("KO".equalsIgnoreCase(flgEsito)) {
						return "Svolta: esito negativo";
					}
				}
				return "Svolta";
			} else if ("1".equals(flgFatta)) {
				return "In lavorazione";
			} else if ("-1".equals(flgFatta)) {
				return "Da svolgere";
			}
		}
		if (flgToDo != null && "1".equals(flgToDo)) {
			return "Prossima da completare";
		}	
		if(motiviNonEseg != null && !"".equals(motiviNonEseg)) {
			return "Passo non modificabile: " + motiviNonEseg;
		}
		return "Passo non modificabile";
	}

	@Override
	public ValuesManager getValuesManager() {
		return vm;
	}

	public LinkedHashMap<String, Record> getMappaMenu() {
		return mappaMenu;
	}
	
	public LinkedHashMap<String, RecordList> getMappaSottomenuGruppi() {
		return mappaSottomenuGruppi;
	}
	
	public LinkedHashMap<String, RecordList> getMappaSottomenuAttributiComplessi() {
		return mappaSottomenuAttributiComplessi;
	}

	public Record getDetailRecord() {
		return detailRecord;
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}

	public abstract class BackDetail extends CustomDetail implements BackDetailInterface {

		public BackDetail(String nomeEntita) {
			super(nomeEntita);
		}

	}

	public List<CustomTaskButton> getListaCustomButtons() {
		return null;
	}
	
	public boolean isFrontOffice() {
		return true;
	}

	public void managePraticaConclusa() {
		window.markForDestroy();
	}

	public interface GetListaTaskProcCallback {

		void execute(RecordList data);
	}

	public RecordList getListaModelliAttivita(String activityName) {
		if(mappaModelli.get(activityName) != null) {
			return (RecordList) mappaModelli.get(activityName);
		}
		return null;
	}

	public HashSet<String> getTipiModelliAtti() {
		return tipiModelliAtti;
	}

	public static String getNomeModelloPubblicazione() {
		String nomeModelloPubblicazione = AurigaLayout.getParametroDB("NOME_MODELLO_PUBBLICAZIONE");
		if (nomeModelloPubblicazione == null || "".equals(nomeModelloPubblicazione)) {
			nomeModelloPubblicazione = DEFAULT_NOME_MODELLO_PUBBLICAZIONE;
		}
		return nomeModelloPubblicazione;
	}

	public static String getNomeFileModelloPubblicazione() {
		String nomeFileModelloPubblicazione = AurigaLayout.getParametroDB("NOME_FILE_MODELLO_PUBBLICAZIONE");
		if (nomeFileModelloPubblicazione == null || "".equals(nomeFileModelloPubblicazione)) {
			nomeFileModelloPubblicazione = DEFAULT_NOME_FILE_MODELLO_PUBBLICAZIONE;
		}
		return nomeFileModelloPubblicazione;
	}
	
	public String getIdTipoProc() {
		return idTipoProc;
	}
	
	public void chiusuraDettaglioAtto() {
		if(idProcess != null && !"".equals(idProcess)) {
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idPratica", idProcess);
			GWTRestDataSource praticheDS = new GWTRestDataSource("PraticheDataSource", "idPratica", FieldType.TEXT);		
			praticheDS.executecustom("chiusuraDettaglioAtto", lRecordToLoad, new DSCallback() {
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						
					}
				}
			});
		}
	}

	@Override
	protected void onDestroy() {
		chiusuraDettaglioAtto();
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}

}