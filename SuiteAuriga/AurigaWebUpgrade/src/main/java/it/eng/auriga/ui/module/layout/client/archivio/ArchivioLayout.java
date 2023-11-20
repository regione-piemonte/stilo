/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.DocumentDetail;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.avvioProcedimento.SceltaTipoProcGenPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioUD.InvioUDMailWindow;
import it.eng.auriga.ui.module.layout.client.osservazioniNotifiche.OsservazioniNotificheWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.NuovoMessaggioWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.ApponiTimbroWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.DettaglioDatiPubblAttoPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.DettaglioOpereAttoPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetail;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailAtti;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailBozze;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailModelli;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailUscita;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PubblicazioneTraspAmmPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.RepertorioDetailUscita;
import it.eng.auriga.ui.module.layout.client.protocollazione.SceltaTipoDocPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.VerificaRegDuplicataWindow;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.RichiestaAccessoAttiDetail;
import it.eng.auriga.ui.module.layout.shared.invioRaccomandate.ETypePoste;
import it.eng.auriga.ui.module.layout.shared.util.AzioniRapide;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.bean.CacheLevelBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomAdvancedTreeLayout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.FrecciaDetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.GenericCallback;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

/**
 * Implementa il layout della finestra
 *
 */
public class ArchivioLayout extends CustomAdvancedTreeLayout {

	protected ToolStripButton newUdButton;
	protected ToolStripButton newFolderButton;
	protected ToolStripButton newFolderCustomButton;

	private MultiToolStripButton aggiungiAPreferitiMultiButton;
	private MultiToolStripButton rimuoviDaPreferitiMultiButton;
	private MultiToolStripButton assegnaRiservatezzaMultiButton;
	private MultiToolStripButton rimuoviRiservatezzaMultiButton;
	private MultiToolStripButton stampaEtichettaMultiButton;
	private MultiToolStripButton stampaDistintaSpedizioneMultiButton;
	private MultiToolStripButton fascicolaMultiButton;
	private MultiToolStripButton assegnaMultiButton;
	private MultiToolStripButton smistaCCMultiButton;
	private MultiToolStripButton apposizioneCommentiMultiButton;
	private MultiToolStripButton downloadZipAllegati;
	private MultiToolStripButton downloadZipFilePubblicati;
	private MultiToolStripButton modificaStatoDocMultiButton;
	private MultiToolStripButton modificaTipologiaMultiButton;
	private MultiToolStripButton chiudiFascicoloMultiButton;
	private MultiToolStripButton riapriFascicoloMultiButton;
	private MultiToolStripButton segnaComeVisionatoMultiButton;	
//	private MultiToolStripButton versaInArchivioStoricoFascicoloMultiButton;

	protected DetailToolStripButton stampaEtichettaButton;
	protected FrecciaDetailToolStripButton frecciaStampaEtichettaButton;
	protected DetailToolStripButton assegnazioneButton;
	protected FrecciaDetailToolStripButton frecciaAssegnazioneButton;
	protected DetailToolStripButton smistaButton;
	protected DetailToolStripButton smistaCCButton;	
	protected DetailToolStripButton invioAlProtocolloButton;
	protected FrecciaDetailToolStripButton frecciaInvioAlProtocolloButton;
	protected DetailToolStripButton assegnaCondividiButton;
	protected FrecciaDetailToolStripButton frecciaAssegnaCondividiButton;
	protected DetailToolStripButton stampaMenuButton;
	protected DetailToolStripButton rispondiButton;
	protected DetailToolStripButton condivisioneButton;
	protected FrecciaDetailToolStripButton frecciaCondivisioneButton;
	protected DetailToolStripButton stampaButton;
	protected DetailToolStripButton stampaRicevutaButton;
	protected DetailToolStripButton nuovaProtComeCopiaButton;
	protected DetailToolStripButton presaInCaricoButton;
	protected DetailToolStripButton restituisciButton;
	protected DetailToolStripButton segnaComeVisionatoButton;
	protected DetailToolStripButton classificazioneFascicolazioneButton;
	protected DetailToolStripButton modificaButton;
	protected FrecciaDetailToolStripButton frecciaModificaButton;
	protected DetailToolStripButton regAccessoCivicoButton;
	protected DetailToolStripButton modificaDatiRegButton;
	protected DetailToolStripButton revocaAttoButton;
	protected DetailToolStripButton protocollazioneEntrataButton;
	protected DetailToolStripButton protocollazioneUscitaButton;
	protected DetailToolStripButton protocollazioneInternaButton;
	protected DetailToolStripButton invioPECButton;
	protected DetailToolStripButton invioMailRicevutaButton;
	protected DetailToolStripButton invioPEOButton;
	protected DetailToolStripButton inviaRaccomandataButton;
	protected DetailToolStripButton inviaPostaPrioritariaButton;
	protected DetailToolStripButton verificaRegistrazioneButton;
	protected DetailToolStripButton salvaComeModelloButton;
	
	// Dettaglio Pratica pregressa
	protected DetailToolStripButton registraPrelievoButton;
	protected DetailToolStripButton modificaPrelievoButton;
	protected DetailToolStripButton eliminaPrelievoButton;
	protected DetailToolStripButton registraRestituzionePrelievoButton;

	protected DetailToolStripButton detailDownloadDocsZip;
	protected FrecciaDetailToolStripButton frecciaDownloadZipButton;
	protected DetailToolStripButton chiudiFascicoloButton;
	protected DetailToolStripButton riapriFascicoloButton;
	protected DetailToolStripButton versaInArchivioStoricoFascicoloButton;
	protected DetailToolStripButton avviaIterButton;
	protected DetailToolStripButton osservazioniNotificheButton;
	protected DetailToolStripButton apposizioneFirmaButton;
	protected DetailToolStripButton apposizioneFirmaProtocollazioneButton;
	protected DetailToolStripButton rifiutoApposizioneFirmaButton;
	protected DetailToolStripButton apposizioneVistoButton;
	protected DetailToolStripButton rifiutoApposizioneVistoButton;
	protected DetailToolStripButton pubblicazioneTraspAmmButton;	
	
	/*******************************************
	 * FLAG ABILITAZIONI MODIFICAFRECCIABUTTON *
	 *******************************************/
	
	private boolean isAbilModificaTipologia;
	private boolean isAbilModificaDatiExtraIter;
	private boolean isAbilModificaOpereAtto;
	private boolean isAbilModificaDatiPubblAtto;
	

	/*******************************
	 * ARCHIVIO CORRENTE/PREGRESSO *
	 *******************************/
	
	private ToolStrip archivioCorrentePregressoToolStrip;
	private RadioGroupItem archivioCorrentePregressoItem;
	
	public ArchivioLayout() {
		this(null, null, null, null);
	}

	public ArchivioLayout(String finalita, Boolean flgSelezioneSingola, Boolean flgSoloFolder, String idRootNode) {
		this(finalita, flgSelezioneSingola, flgSoloFolder, idRootNode, null);
	}
	
	public ArchivioLayout(String finalita, Boolean flgSelezioneSingola, Boolean flgSoloFolder, String idRootNode, Boolean showOnlyDetail) {
		this(finalita, flgSelezioneSingola, flgSoloFolder, idRootNode, showOnlyDetail, null);
	}

	public ArchivioLayout(String finalita, Boolean flgSelezioneSingola, Boolean flgSoloFolder, String idRootNode, Boolean showOnlyDetail, AdvancedCriteria initialCriteria) {

		super("archivio", "archivio", new GWTRestDataSource("ArchivioTreeDatasource", true, "idNode", FieldType.TEXT), getArchivioDataSource(),
				new ArchivioTree("archivio"), new ArchivioFilter("archivio", null), new ArchivioList("archivio"), new CustomDetail("archivio"), finalita,
				flgSelezioneSingola, flgSoloFolder, idRootNode, showOnlyDetail, initialCriteria);

		this.newButton.hide();

		if (getFinalita() != null && !"".equals(getFinalita())) {
			this.multiselectButton.hide();
		}
		
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RICERCA_PREGRESSO")) {
			buildArchivioCorrentePregressoToolStrip();
		}
	}
	
	public void buildArchivioCorrentePregressoToolStrip() {
		archivioCorrentePregressoToolStrip = new ToolStrip();		
		archivioCorrentePregressoToolStrip.setWidth100();
		archivioCorrentePregressoToolStrip.setHeight(30);
		archivioCorrentePregressoToolStrip.setBackgroundColor("transparent");
		archivioCorrentePregressoToolStrip.setBackgroundImage("blank.png");
		archivioCorrentePregressoToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		archivioCorrentePregressoToolStrip.setBorder("0px");
//		archivioCorrentePregressoToolStrip.setPadding(5);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			archivioCorrentePregressoToolStrip.setTabIndex(-1);
			archivioCorrentePregressoToolStrip.setCanFocus(false);		
		}
		
		archivioCorrentePregressoItem = new RadioGroupItem("archivioCorrentePregresso");
		archivioCorrentePregressoItem.setTitle("Archivio");
		archivioCorrentePregressoItem.setVertical(false);
		archivioCorrentePregressoItem.setStartRow(true);
		archivioCorrentePregressoItem.setWrap(false);
		String labelArchivioCorrente = AurigaLayout.getParametroDB("LABEL_ARCHIVIO_CORRENTE"); 
		String labelArchivioPregresso = AurigaLayout.getParametroDB("LABEL_ARCHIVIO_PREGRESSO"); 
		LinkedHashMap<String, String> archivioCorrentePregressoValueMap = new LinkedHashMap<String, String>();
		archivioCorrentePregressoValueMap.put("corrente", labelArchivioCorrente != null && !"".equals(labelArchivioCorrente) ? labelArchivioCorrente : "corrente");
		archivioCorrentePregressoValueMap.put("pregresso", labelArchivioPregresso != null && !"".equals(labelArchivioPregresso) ? labelArchivioPregresso : "pregresso");
		archivioCorrentePregressoItem.setValueMap(archivioCorrentePregressoValueMap);
		archivioCorrentePregressoItem.setDefaultValue("corrente");
		archivioCorrentePregressoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if(event.getValue() != null && "pregresso".equals(event.getValue())) {
					changeLayout("archivio_pregresso", getArchivioPregressoDataSource(), new ArchivioFilter("archivio_pregresso", null), new ArchivioList("archivio"), new CustomDetail("archivio"));
				} else {
					changeLayout("archivio", getArchivioDataSource(), new ArchivioFilter("archivio", null), new ArchivioList("archivio"), new CustomDetail("archivio"));				
				}				
				markForRedraw();
				reloadListAndFilter();				
			}
		});
		
		archivioCorrentePregressoToolStrip.addFormItem(archivioCorrentePregressoItem);
				
		archivioCorrentePregressoToolStrip.setHeight(5);
		archivioCorrentePregressoToolStrip.setWidth100();
		archivioCorrentePregressoToolStrip.setAlign(Alignment.CENTER);
				
		searchLayout.setMembers(bodyIntestazioneLayout, archivioCorrentePregressoToolStrip, filterLayout, listLayout);
//		rightLayout.setMembers(searchLayout, multiLookupLayout);
	}
	
	public void changeLayout(String nomeEntita, final GWTRestDataSource datasource, final ConfigurableFilter pFilter, final CustomList pList, final CustomDetail pDetail) {
			
		this.nomeEntita = nomeEntita;
		
		if (this.filter != null) {			
			this.filter.markForDestroy();
		}
		
		if (this.list != null) {			
			this.list.markForDestroy();
		}
		
		if (this.detail != null) {			
			this.detail.markForDestroy();
		}
		
		this.filter = pFilter;
		this.list = pList;
		this.detail = pDetail;	
		
		configLista = Layout.getListConfig(list != null ? list.getNomeEntita() : this.nomeEntita);
		fullScreenDetail = configLista.getFullScreenDetail();

		this.filter.setLayout(this);
		this.list.setLayout(this);
		this.detail.setLayout(this);
		
		this.datasource = datasource;
		this.list.setDataSource(datasource);
		this.detail.setDataSource(datasource);
		
		listFields = list.getAllFields();		
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
			// Questa operazione viene fatto per aggiornare il tabindex INIZIO
			Canvas[] members = this.filterButtons.getMembers();
			
			HStack filtriBottoni;
			filtriBottoni = new HStack(3);
			filtriBottoni.setPadding(5);
			
			for (Canvas singleMember : members)
			{
				filtriBottoni.addMember(singleMember);
			}
			this.filterButtons = filtriBottoni;		
 		}
		
//		filterLayout = new VLayout();        
		filterLayout.setMembers(filterToolStrip, filter, filterButtons);  	
		
		if(filter.getIsConfigured()) {
			filterLayout.setVisible(true);
			filterLayout.setShowResizeBar(true);
		} else {
			filterLayout.setVisible(false);
		}
		
		bodyListLayout.setMembers(list);	
//		listLayout.setMembers(topListToolStrip, bodyListLayout, bottomListToolStrip);
		
		detailLayout.setMembers(detail, detailToolStrip);		
		
//		searchLayout.setMembers(filterLayout, listLayout);		
//		leftLayout.setMembers(tree);		
//		rightLayout.setMembers(searchLayout, multiLookupLayout);
//		mainLayout.setMembers(leftLayout, rightLayout);
				
		filterLayout.markForRedraw();
		bodyListLayout.markForRedraw();
//		listLayout.markForRedraw();
		detailLayout.markForRedraw();	
//		searchLayout.markForRedraw();
//		leftLayout.markForRedraw();	
//		rightLayout.markForRedraw();
//		mainLayout.markForRedraw();
		
//		this.markForRedraw();	
		
		if(maxRecordVisualizzabiliItem != null) {
			maxRecordVisualizzabiliItem.setValue("");
			if(showMaxRecordVisualizzabiliItem()) {			
				maxRecordVisualizzabiliItem.show();
			} else {
				maxRecordVisualizzabiliItem.hide();
			}
		}
		
		if(nroRecordXPaginaItem != null) {
			nroRecordXPaginaItem.setValue("");
			if(showPaginazioneItems()) {
				nroRecordXPaginaItem.show();
			} else {
				nroRecordXPaginaItem.hide();
			}
		}
		
		if(hasMultiselectButtons()) {
			multiselectButton.show();
		} else {
			multiselectButton.hide();			
		}		

		if(nroPaginaItem != null) {
			nroPaginaItem.setValue("");
			nroPaginaItem.hide();
		}
		if(nroPaginaFirstButton != null) {
			nroPaginaFirstButton.hide();
		}
		if(nroPaginaPrevButton != null) {
			nroPaginaPrevButton.hide();
		}
		if(nroPaginaNextButton != null) {
			nroPaginaNextButton.hide();
		}
		if(nroPaginaLastButton != null) {
			nroPaginaLastButton.hide();
		}
		if(nroPaginaToolStripSeparator != null) {
			nroPaginaToolStripSeparator.hide();
		}

	}
	
	public boolean hasMultiselectButtons() {
		int n = 0;
		for(MultiToolStripButton button : getMultiselectButtons()) {
			if(((MultiToolStripButton) button).toShow()) {
				n++;
			}			
		}
		return (n > 0);
	}
	
	@Override
	public boolean showPaginazioneItems() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PAGINAZIONE_ARCHIVIO");
	}

	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {

		if (aggiungiAPreferitiMultiButton == null) {
			aggiungiAPreferitiMultiButton = new MultiToolStripButton("archivio/aggiungiAPreferiti.png", this, "Aggiungi ai preferiti", false) {

				@Override
				public boolean toShow() {
					String idFolder = navigator.getCurrentNode().getIdFolder();
					return Layout.isPrivilegioAttivo("PRF") && ((idFolder == null || !"-999".equals(idFolder))); // Se non è in Preferiti (e non è in Stampe ed
																													// esportazioni su file)
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AggiuntaAPreferitiDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Aggiunta ai \"Preferiti\" effettuata con successo",
									"Tutti i record selezionati per l'aggiunta ai \"Preferiti\" sono andati in errore!",
									"Alcuni dei record selezionati per l'aggiunta ai \"Preferiti\" sono andati in errore!", null);
						}
					});
				}
			};
		}

		if (stampaDistintaSpedizioneMultiButton == null) {
			stampaDistintaSpedizioneMultiButton = new MultiToolStripButton("protocollazione/stampaDistintaSpedizione.png", this, "Stampa distinta", false) {

				@Override
				public boolean toShow() {
					return AurigaLayout.getParametroDBAsBoolean("SHOW_STAMPA_DESTINATARI");
				}

				@Override
				public void doSomething() {
					final RecordList records = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						records.add(list.getSelectedRecords()[i]);
					}
					// Leggo i valori del filtro MEZZO DI TRASMISSIONE
					JavaScriptObject mezziTrasmissioneFilter = getMezzoTrasmissioneValueFilter();

					// Se non è stato selezioniona almeno un valore del filtro devo mostrare la popup con la combo dei mezzi di trasmissione
					if (mezziTrasmissioneFilter == null) {
						Record listMezziTrasmissioneFilter = new Record();
						final MezziTrasmissionePopup mezziTrasmissionePopup = new MezziTrasmissionePopup(listMezziTrasmissioneFilter) {

							@Override
							public void onClickOkButton(final DSCallback callback) {
								Record record = new Record();
								record.setAttribute("listaRecord", records);
								if (_form.getValue("listaMezziTrasmissione") != null) {
									record.setAttribute("listMezziTrasmissione", _form.getValueAsRecordList("listaMezziTrasmissione"));
								}
								final GWTRestService<Record, Record> lGwtRestDataSource = new GWTRestService<Record, Record>(
										"StampaDistintaSpedizioneDataSource");
								lGwtRestDataSource.executecustom("getListaDestinatari", record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveStampaDistintaSpedizioneCallback(lGwtRestDataSource, response, records, null);
									}
								});
								// else
								// Layout.addMessage(new MessageBean("E' obbligatorio selezionare almeno un mezzo di trasmissione", "", MessageType.ERROR));
							}
						};
						mezziTrasmissionePopup.show();
					}
					// Altrimenti lancio il servizio di stampa
					else {
						Record record = new Record();
						record.setAttribute("listaRecord", records);
						if ((JavaScriptObject) mezziTrasmissioneFilter != null)
							record.setAttribute("listMezziTrasmissione", new RecordList((JavaScriptObject) mezziTrasmissioneFilter));
						final GWTRestService<Record, Record> lGwtRestDataSource = new GWTRestService<Record, Record>("StampaDistintaSpedizioneDataSource");
						lGwtRestDataSource.executecustom("getListaDestinatari", record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								massiveStampaDistintaSpedizioneCallback(lGwtRestDataSource, response, records, null);
							}
						});
					}
				}
			};
		}

		if (stampaEtichettaMultiButton == null) {
			stampaEtichettaMultiButton = new MultiToolStripButton("protocollazione/barcode.png", this, "Etichetta", false) {

				@Override
				public boolean toShow() {
					return AurigaLayout.getParametroDBAsBoolean("SHOW_STAMPA_ETICHETTA_IND");
				}

				@Override
				public void doSomething() {
					final RecordList records = new RecordList();

					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						records.add(list.getSelectedRecords()[i]);
					}
					// Leggo i valori del filtro MEZZO DI TRASMISSIONE
					JavaScriptObject mezziTrasmissioneFilter = getMezzoTrasmissioneValueFilter();

					// Se non è stato selezioniona almeno un valore del filtro devo mostrare la popup con la combo dei mezzi di trasmissione
					if (mezziTrasmissioneFilter == null) {
						Record listMezziTrasmissioneFilter = new Record();
						final MezziTrasmissionePopup mezziTrasmissionePopup = new MezziTrasmissionePopup(listMezziTrasmissioneFilter) {

							@Override
							public void onClickOkButton(final DSCallback callback) {
								Record record = new Record();
								record.setAttribute("listaRecord", records);
								if (_form.getValueAsString("listaMezziTrasmissione") != null) {
									record.setAttribute("mezziTrasmissione", _form.getValueAsString("listaMezziTrasmissione"));
								}
								final GWTRestService<Record, Record> lGwtRestDataSource = new GWTRestService<Record, Record>(
										"StampaEtichettaIndirizzoDataSource");
								lGwtRestDataSource.executecustom("getListaEtichette", record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massivePrintLabelCallback(lGwtRestDataSource, response, records, null);
									}
								});
								this.markForDestroy();
							}
						};
						mezziTrasmissionePopup.show();
					} else {
						Record record = new Record();
						record.setAttribute("listaRecord", records);
						record.setAttribute("mezziTrasmissione", mezziTrasmissioneFilter.toString());
						final GWTRestService<Record, Record> lGwtRestDataSource = new GWTRestService<Record, Record>("StampaEtichettaIndirizzoDataSource");
						lGwtRestDataSource.executecustom("getListaEtichette", record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								massivePrintLabelCallback(lGwtRestDataSource, response, records, null);
							}
						});
					}
				}
			};
		}

		if (rimuoviDaPreferitiMultiButton == null) {
			rimuoviDaPreferitiMultiButton = new MultiToolStripButton("archivio/rimuoviDaPreferiti.png", this, "Rimuovi dai preferiti", false) {

				@Override
				public boolean toShow() {
					String idFolder = navigator.getCurrentNode().getIdFolder();
					return Layout.isPrivilegioAttivo("PRF") && (idFolder != null && "-999".equals(idFolder)); // Se è in Preferiti
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("RimozioneDaPreferitiDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Rimozione dai \"Preferiti\" effettuata con successo",
									"Tutti i record selezionati per la rimozione dai \"Preferiti\" sono andati in errore!",
									"Alcuni dei record selezionati per la rimozione dai \"Preferiti\" sono andati in errore!", null);
						}
					});
				}
			};
		}

		if (assegnaRiservatezzaMultiButton == null) {
			assegnaRiservatezzaMultiButton = new MultiToolStripButton("archivio/assegnaRiservatezza.png", this, "Assegna riservatezza", false) {

				@Override
				public boolean toShow() {
					return (Layout.isPrivilegioAttivo("GRD/UD/UUD") || Layout.isPrivilegioAttivo("GRD/FLD/UM"));
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("livelloRiservatezza", "1");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ModificaLivelloRiservatezzaDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Assegnazione riservatezza effettuata con successo",
									"Tutti i record selezionati per l'assegnazione riservatezza sono andati in errore!",
									"Alcuni dei record selezionati per l'assegnazione riservatezza sono andati in errore!", null);
						}
					});
				}
			};
		}
		
		if (rimuoviRiservatezzaMultiButton == null) {
			rimuoviRiservatezzaMultiButton = new MultiToolStripButton("archivio/rimuoviRiservatezza.png", this, "Rimuovi riservatezza", false) {

				@Override
				public boolean toShow() {
					return (Layout.isPrivilegioAttivo("GRD/UD/UUD") || Layout.isPrivilegioAttivo("GRD/FLD/UM"));
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("livelloRiservatezza", "");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ModificaLivelloRiservatezzaDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Rimozione riservatezza effettuata con successo",
									"Tutti i record selezionati per la rimozione riservatezza sono andati in errore!",
									"Alcuni dei record selezionati per la rimozione riservatezza sono andati in errore!", null);
						}
					});
				}
			};
		}
		
		if(chiudiFascicoloMultiButton == null) {
			chiudiFascicoloMultiButton = new MultiToolStripButton("archivio/archiviazione.png", this, "Chiusura fascicolo/aggregato", false ) {

				@Override
				public boolean toShow() {
					return Layout.isPrivilegioAttivo("GRD/FLD/C");
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					boolean isSoloFascicoli = true;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						if (list.getSelectedRecords()[i].getAttribute("flgUdFolder").equalsIgnoreCase("F")) {
							listaUdFolder.add(list.getSelectedRecords()[i]);	
						}
						else {
							isSoloFascicoli = false;
							break;
						}
					}
					if (isSoloFascicoli) {
						SC.ask("Sei sicuro di voler chiudere i fascicoli ?", new BooleanCallback() {
							@Override 
							public void execute(Boolean value) {  
								if (value) {
									Record record = new Record();
									record.setAttribute("listaRecord", listaUdFolder);
									GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
									lGwtRestDataSource.executecustom("chiudiFascicolo", record, new DSCallback() {

										@Override public void execute(DSResponse response, Object rawData, DSRequest request) { 
											massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", 
													"Tutti i fascicoli/aggregati selezionati sono stati chiusi", 
													"Tutti i fascicoli selezionati sono andati in errore!",
													"Alcuni dei fascicoli selezionati sono andati in errore!", null); 
										} 
									}); 
								} 
							} 
						}); 
					} else
						Layout.addMessage(new MessageBean("La selezione comprende anche dei documenti: operazione di chiusura non consentita", "", MessageType.ERROR));
				} 
			}; 
		}
		
		if(riapriFascicoloMultiButton == null) {
			riapriFascicoloMultiButton = new MultiToolStripButton("archivio/annullaArchiviazione.png", this, "Riapertura fascicolo/aggregato", false ) {

				@Override
				public boolean toShow() {
					return Layout.isPrivilegioAttivo("GRD/FLD/C");
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					boolean isSoloFascicoli = true;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						if (list.getSelectedRecords()[i].getAttribute("flgUdFolder").equalsIgnoreCase("F")) {
							listaUdFolder.add(list.getSelectedRecords()[i]);	
						}
						else {
							isSoloFascicoli = false;
							break;
						}
					}
					if (isSoloFascicoli) {
						SC.ask("Sei sicuro di voler riaprire i fascicoli ?", new BooleanCallback() {
							@Override 
							public void execute(Boolean value) {  
								if (value) {
									Record record = new Record();
									record.setAttribute("listaRecord", listaUdFolder);
									GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
									lGwtRestDataSource.executecustom("riapriFascicolo", record, new DSCallback() {

										@Override public void execute(DSResponse response, Object rawData, DSRequest request) { 
											massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", 
													"Tutti i fascicoli/aggregati selezionati sono stati riaperti", 
													"Tutti i fascicoli selezionati sono andati in errore!",
													"Alcuni dei fascicoli selezionati sono andati in errore!", null); 
										} 
									}); 
								} 
							} 
						}); 
					} else
						Layout.addMessage(new MessageBean("La selezione comprende anche dei documenti: operazione di riapertura non consentita", "", MessageType.ERROR));
				} 
			}; 
		}
		
		if (assegnaMultiButton == null) {
			assegnaMultiButton = new MultiToolStripButton("archivio/assegna.png", this, "Assegna", false) {

				@Override
				public boolean toShow() {
					return Layout.isPrivilegioAttivo("GRD/UD/INV") || Layout.isPrivilegioAttivo("GRD/FLD/INV");
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					// Prendo il primo flag per confrontarlo con tutti gli altri
					String flgUdFolderFirst = list.getSelectedRecords()[0].getAttribute("flgUdFolder");
					String flgTipoProvFirst = list.getSelectedRecords()[0].getAttribute("flgTipoProv");
					boolean isStessoFlgUdFolder = true;
					boolean isStessoFlgTipoProv = true;
					boolean hasFlgTipoProvEntrata = false;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						if (list.getSelectedRecords()[i].getAttribute("flgUdFolder").equalsIgnoreCase(flgUdFolderFirst)) {
							listaUdFolder.add(list.getSelectedRecords()[i]);
						} else {
							isStessoFlgUdFolder = false;
						}
						if (list.getSelectedRecords()[i].getAttribute("flgTipoProv") != null && list.getSelectedRecords()[i].getAttribute("flgTipoProv").equalsIgnoreCase("E")) {
							hasFlgTipoProvEntrata = true;
						}							
						if(list.getSelectedRecords()[i].getAttribute("flgTipoProv") != null ? !list.getSelectedRecords()[i].getAttribute("flgTipoProv").equals(flgTipoProvFirst) : flgTipoProvFirst != null) {
							isStessoFlgTipoProv = false;
						}						
					}					
					final String flgUdFolder = isStessoFlgUdFolder ? flgUdFolderFirst : null;
					final String flgTipoProv = isStessoFlgTipoProv ? flgTipoProvFirst : null;					
					final boolean isFlgTipoProvMassiva = !isStessoFlgTipoProv && hasFlgTipoProvEntrata;
					
					if (isStessoFlgUdFolder) {
						final Menu creaAssegna = new Menu(); 
						Record recordDestPref = new Record();						
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapida = new Record();						
						if(flgUdFolder.equals("U")){
							recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue());
						} else {
							recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_FOLDER.getValue());
						}
						listaAzioniRapide.add(recordAzioneRapida);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);				
						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
						lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
				
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									
									Record destinatariPreferiti = response.getData()[0];
									RecordList listaUOPreferiti = null;
									RecordList listaUtentiPreferiti = null;
									if(flgUdFolder != null && flgUdFolder.equals("U")){
										listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_DOC.getValue()));
										listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.ASSEGNA_DOC.getValue()));					
									} else if(flgUdFolder != null && flgUdFolder.equals("F")){
										listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_FOLDER.getValue()));
										listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.ASSEGNA_FOLDER.getValue()));					
									}
																
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
									
									
									// Assegna Standard 
									MenuItem assegnaMenuStandardItem = new MenuItem("Standard");
									assegnaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
										
										@Override
										public void onClick(MenuItemClickEvent event) {
											final AssegnazionePopup assegnazionePopup = new AssegnazionePopup(flgUdFolder, null) {																								
												
												@Override
												public String getFlgTipoProvDoc() {
													if(flgUdFolder.equals("U")) {
														return flgTipoProv;														
													}
													return null;
												}
												
												@Override
												public String getSuffissoFinalitaOrganigramma() {
													if(flgUdFolder.equals("U") && isFlgTipoProvMassiva) {
														return "_MASSIVA";													
													}
													return super.getSuffissoFinalitaOrganigramma();	
												}

												@Override
												public RecordList getListaPreferiti() {
													return listaPreferiti;
												}

												@Override
												public void onClickOkButton(Record record, final DSCallback callback) {
													
													record.setAttribute("flgUdFolder", flgUdFolder);
													record.setAttribute("listaRecord", listaUdFolder);
													
													Layout.showWaitPopup("Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
													GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
													try {
														lGwtRestDataSource.addData(record, new DSCallback() {

															@Override
															public void execute(DSResponse response, Object rawData, DSRequest request) {
																massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Assegnazione effettuata con successo",
																		"Tutti i record selezionati per l'assegnazione sono andati in errore!",
																		"Alcuni dei record selezionati per l'assegnazione sono andati in errore!", callback);
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
									MenuItem assegnaMenuRapidoItem = new MenuItem("Rapida");				

									Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
									
									if(success != null && success == true){
										
										Menu scelteRapide = new Menu();
										
										if(noMenuRapido){
											assegnaMenuRapidoItem.setEnabled(false);    
										} else {
											buildMenuRapidoAssegnazione(listaUdFolder, flgUdFolder, listaPreferiti,scelteRapide);
											assegnaMenuRapidoItem.setSubmenu(scelteRapide);
										}
									
									} else {
										assegnaMenuRapidoItem.setEnabled(false);
									}
									creaAssegna.addItem(assegnaMenuRapidoItem);
									
									creaAssegna.showContextMenu();
									}
								}

							
							/**
							 * @param listaUdFolder
							 * @param flgUdFolder
							 * @param listaPreferiti
							 */
							private void buildMenuRapidoAssegnazione(final RecordList listaUdFolder,
									final String flgUdFolder, RecordList listaPreferiti ,Menu scelteRapide) {
								
								for(int i=0; i < listaPreferiti.getLength();i++){
									
									Record currentRecord = listaPreferiti.get(i);
									final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
									final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
									final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
									
									MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
									currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
										
										@Override
										public void onClick(MenuItemClickEvent event) {
											
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
														massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Assegnazione effettuata con successo",
																"Tutti i record selezionati per l'assegnazione sono andati in errore!",
																"Alcuni dei record selezionati per l'assegnazione sono andati in errore!", null);
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
						
						}, new DSRequest());
						
					} else {
						Layout.addMessage(new MessageBean(
								"Non è possibile assegnare documenti e fascicoli in una sola operazione: seleziona solo documenti o solo fascicoli", "",
								MessageType.ERROR));
					}
				}
			};
		}
		
		if(smistaCCMultiButton == null) {
		   smistaCCMultiButton = new MultiToolStripButton("archivio/smistamentoCC.png", this, "Smista", false) {

				@Override
				public boolean toShow() {
					return true;
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					// Prendo il primo flag per confrontarlo con tutti gli altri
					String flgUdFolderFirst = list.getSelectedRecords()[0].getAttribute("flgUdFolder");
					boolean isStessoFlgUdFolder = true;

					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						if (list.getSelectedRecords()[i].getAttribute("flgUdFolder").equalsIgnoreCase(flgUdFolderFirst)) {
							listaUdFolder.add(list.getSelectedRecords()[i]);
						} else {
							isStessoFlgUdFolder = false;
						}					
					}					
					final String flgUdFolder = isStessoFlgUdFolder ? flgUdFolderFirst : null;
					
					if (isStessoFlgUdFolder) {
						final Menu creaSmistaCC = new Menu(); 
						Record recordDestPref = new Record();						
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapida = new Record();						
						if(flgUdFolder.equals("U")){
							recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.SMISTA_DOC.getValue());
						} else {
							recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.SMISTA_FOLDER.getValue());
						}
						listaAzioniRapide.add(recordAzioneRapida);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);				
						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
						lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
				
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									
									Record destinatariPreferiti = response.getData()[0];
									RecordList listaUOPreferiti = null;
									RecordList listaUtentiPreferiti = null;
									if(flgUdFolder != null && flgUdFolder.equals("U")){
										listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_DOC.getValue()));
										listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_DOC.getValue()));					
									} else if(flgUdFolder != null && flgUdFolder.equals("F")){
										listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_FOLDER.getValue()));
										listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_FOLDER.getValue()));					
									}
																
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
																
									// SmistaCC Standard 
									MenuItem smistaCCMenuStandardItem = new MenuItem("Standard");
									smistaCCMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
										
										@Override
										public void onClick(MenuItemClickEvent event) {
											
											String title = "Compila dati smistamento";
											final CondivisionePopup condivisionePopup = new CondivisionePopup(flgUdFolder, null, title) {																								
												
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
													
													record.setAttribute("flgUdFolder", flgUdFolder);
													record.setAttribute("listaRecord", listaUdFolder);
													record.setAttribute("motivoInvio", "#SMIST");
													
													Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
													GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
													try {
														lGwtRestDataSource.addData(record, new DSCallback() {

															@Override
															public void execute(DSResponse response, Object rawData, DSRequest request) {
																massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Smistamento effettuato con successo",
																		"Tutti i record selezionati per lo smistamento sono andati in errore!",
																		"Alcuni dei record selezionati per lo smistamento sono andati in errore!", callback);
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
									MenuItem smistaCCMenuRapidoItem = new MenuItem("Rapida");				

									Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
									
									if(success != null && success == true){
										
										Menu scelteRapide = new Menu();
										
										if(noMenuRapido){
											smistaCCMenuRapidoItem.setEnabled(false);    
										} else {
											buildMenuRapidoSmistamentoCC(listaUdFolder, flgUdFolder, listaPreferiti, scelteRapide);
											smistaCCMenuRapidoItem.setSubmenu(scelteRapide);
										}
									
									} else {
										smistaCCMenuRapidoItem.setEnabled(false);
									}
									creaSmistaCC.addItem(smistaCCMenuRapidoItem);
									
									creaSmistaCC.showContextMenu();
									}
								}

							
								private void buildMenuRapidoSmistamentoCC(final RecordList listaUdFolder,
										final String flgUdFolder, RecordList listaPreferiti ,Menu scelteRapide) {
									
									for(int i=0; i < listaPreferiti.getLength();i++){
										
										Record currentRecord = listaPreferiti.get(i);
										final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
										final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
										final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
										
										MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
										currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
											
											@Override
											public void onClick(MenuItemClickEvent event) {
												
												RecordList listaDestInvioCC = new RecordList();
												Record recordDestSmistaCC = new Record();
												recordDestSmistaCC.setAttribute("idUo", idDestinatarioPreferito);
												recordDestSmistaCC.setAttribute("typeNodo",tipoDestinatarioPreferito);
												listaDestInvioCC.add(recordDestSmistaCC);
												
												Record record = new Record();
												record.setAttribute("flgUdFolder", flgUdFolder);
												record.setAttribute("listaRecord", listaUdFolder);
												record.setAttribute("listaDestInvioCC", listaDestInvioCC);
												record.setAttribute("motivoInvio", "#SMIST");
												
												GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
												try {
													lGwtRestDataSource.addData(record, new DSCallback() {
	
														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Smistamento effettuato con successo",
																	"Tutti i record selezionati per lo smistamento sono andati in errore!",
																	"Alcuni dei record selezionati per lo smistamento sono andati in errore!", null);
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
						
						}, new DSRequest());
						
					} else {
						Layout.addMessage(new MessageBean(
								"Non è possibile smistare documenti e fascicoli in una sola operazione: seleziona solo documenti o solo fascicoli", "",
								MessageType.ERROR));
					}
				}
			};
		}

		if (fascicolaMultiButton == null) {
			fascicolaMultiButton = new MultiToolStripButton("archivio/fascicola.png", this, "Fascicola", false) {

				@Override
				public boolean toShow() {
					return (Layout.isPrivilegioAttivo("GRD/UD/UUD") || Layout.isPrivilegioAttivo("GRD/FLD/UM"));
				}

				@Override
				public void doSomething() {
					final RecordList listaUd = new RecordList();
					boolean isSoloDocumenti = true;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						if (list.getSelectedRecords()[i].getAttribute("flgUdFolder").equalsIgnoreCase("U"))
							listaUd.add(list.getSelectedRecords()[i]);
						else {
							isSoloDocumenti = false;
							break;
						}
					}
					if (isSoloDocumenti) {
						final ClassificazioneFascicolazionePopup fascicolazionePopup = new ClassificazioneFascicolazionePopup(true, null) {

							@Override
							public void onClickOkButton(final DSCallback callback) {

								Record record = new Record();
								record.setAttribute("listaRecord", listaUd);
								record.setAttribute("listaClassFasc", _form.getValueAsRecordList("listaClassFasc"));
								record.setAttribute("livelloRiservatezza", _form.getValue("livelloRiservatezza"));
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ClassificazioneFascicolazioneDataSource");
								lGwtRestDataSource.addParam("inAppend", "true");
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaUd, "idUdFolder", "segnatura",
												"Classificazione/fascicolazione effettuata con successo",
												"Tutti i record selezionati per la classificazione/fascicolazione sono andati in errore!",
												"Alcuni dei record selezionati per la classificazione/fascicolazione sono andati in errore!", callback);
									}
								});
							}
						};
					} else
						Layout.addMessage(new MessageBean("La selezione comprende anche dei fascicoli: operazione di fascicolazione non consentita", "",
								MessageType.ERROR));
				}
			};
		}
		
		if (modificaTipologiaMultiButton == null) {
			modificaTipologiaMultiButton = new MultiToolStripButton("archivio/modificaTipologia.png", this,"Modifica tipologia documentale", false) {

				@Override
				public boolean toShow() {
					return (Layout.isPrivilegioAttivo("GRD/UD/UUD") || Layout.isPrivilegioAttivo("GRD/FLD/UM"));
				}

				@Override
				public void doSomething() {
					
					Boolean isFolder = false;
					Boolean isUd = false;
					int numElement = 0;
					final RecordList listaUdFolder = new RecordList();
					
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						if("F".equals(list.getSelectedRecords()[i].getAttributeAsString("flgUdFolder"))){
							isFolder = true;
							numElement++;
						} else if("U".equals(list.getSelectedRecords()[i].getAttributeAsString("flgUdFolder"))) {
							isUd = true;
							numElement++;
						}
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					////ho selezionato solo Folder
					if(isFolder && !isUd){
						Record record = new Record();
						String descType = null;
						
						//se ho selezionato un solo elemento inizializzo la tipologia nella select
						if(numElement==1) {
							descType = listaUdFolder.get(0).getAttribute("tipo");
							record.setAttribute("idClassifica", listaUdFolder.get(0).getAttribute("idClassifica"));
						}
						final String descrizione= descType;
						
						record.setAttribute("idFolderApp", listaUdFolder.get(0).getAttribute("idFolderApp"));
						record.setAttribute("idFolderType", listaUdFolder.get(0).getAttribute("idFolderType"));

						new SceltaTipoFolderPopup(false, null, descType, record,
								new ServiceCallback<Record>() {

									@Override
									public void execute(Record lRecordTipoDoc) {

										String idFolderType = lRecordTipoDoc.getAttribute("idFolderType");
										
										Record record = new Record();
										record.setAttribute("listaRecord", listaUdFolder);

										//se la tipologia selezionata è diversa da quella attuale
										if (!descrizione.equals(idFolderType)) {
											final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(
													"ArchivioDatasource");
											lGwtRestDataSource.extraparam.put("idFolderType", idFolderType);
											lGwtRestDataSource.executecustom("modificaTipologiaMassiva", record,
													new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData,
																DSRequest request) {
															massiveOperationCallback(response, listaUdFolder,
																	"idUdFolder", "segnatura",
																	"Operazione effettuata con successo",
																	"Tutti i record selezionati per la modifica della tipologia sono andati in errore!",
																	"Alcuni dei record selezionati per la modifica della tipologia sono andati in errore!",
																	null);
														}
													});

										}
									}
								});

					//ho selezionato solo UD
					} else if(!isFolder && isUd) {
						String descType = null;
						
						//se ho selezionato un solo elemento inizializzo la tipologia nella select
						if(numElement==1) {
							descType = listaUdFolder.get(0).getAttribute("tipo");
						}
						final String descrizione= descType;
						new SceltaTipoDocPopup(false, null, descType, null, null, "|*|FINALITA|*|ASS_TIPOLOGIA", new ServiceCallback<Record>() {
			
							@Override
							public void execute(Record lRecordTipoDoc) {
			
								Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								
								String tipoDocumento = lRecordTipoDoc.getAttribute("idTipoDocumento");
								
										//se la tipologia selezionata è diversa da quella attuale
										if (!descrizione.equals(tipoDocumento)) {
											final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(
													"ArchivioDatasource");
											lGwtRestDataSource.extraparam.put("tipoDocumento", tipoDocumento);
											lGwtRestDataSource.executecustom("modificaTipologiaMassiva", record,
													new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData,
																DSRequest request) {
															massiveOperationCallback(response, listaUdFolder,
																	"idUdFolder", "segnatura",
																	"Operazione effettuata con successo",
																	"Tutti i record selezionati per la modifica della tipologia sono andati in errore!",
																	"Alcuni dei record selezionati per la modifica della tipologia sono andati in errore!",
																	null);
														}
													});
										}
							}
						});
					}else {
						Layout.addMessage(new MessageBean("Per questo tipo di azione devi selezionare solo documenti o solo fascicoli/aggregati di documenti",
								"", MessageType.WARNING));
					}
				}
			};
		}

		if (modificaStatoDocMultiButton == null) {
			modificaStatoDocMultiButton = new MultiToolStripButton("archivio/archiviaConcludi.png", this,
					I18NUtil.getMessages().archivio_layout_archiviaConludiLavorazione_title(), false) {

				@Override
				public boolean toShow() {
					return true;
				}

				@Override
				public void doSomething() {
					
					final RecordList listaUdFolder = new RecordList();
					Boolean isFolder = false;
					Boolean isUd = false;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						if("F".equals(list.getSelectedRecords()[i].getAttributeAsString("flgUdFolder"))){
							isFolder = true;
						} else if("U".equals(list.getSelectedRecords()[i].getAttributeAsString("flgUdFolder"))){
							isUd = true;
						}
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					if(!isFolder && isUd) {
						new AssegnaStatoUdPopup("U") {
	
							@Override
							public void onClickOkButton(Record object, final DSCallback callback) {
								
								Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								
								final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
								lGwtRestDataSource.extraparam.put("stato", object.getAttribute("statoUd"));
								lGwtRestDataSource.executecustom("aggiornaStatoDocumenti", record, new DSCallback() {
	
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Assegnazione stato effettuata con successo",
												"Tutti i record selezionati per l'assegnazione dello stato sono andati in errore!",
												"Alcuni dei record selezionati per l'assegnazione dello stato sono andati in errore!", null);
									}
								});
								
								this.markForDestroy();
							}
						};
					} else if(isFolder && !isUd) {
						new AssegnaStatoUdPopup("F") {
	
							@Override
							public void onClickOkButton(Record object, final DSCallback callback) {
								
								Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								
								final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
								lGwtRestDataSource.extraparam.put("stato", object.getAttribute("statoUd"));
								lGwtRestDataSource.executecustom("aggiornaStatoDocumenti", record, new DSCallback() {
	
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Assegnazione stato effettuata con successo",
												"Tutti i record selezionati per l'assegnazione dello stato sono andati in errore!",
												"Alcuni dei record selezionati per l'assegnazione dello stato sono andati in errore!", null);
									}
								});
								
								this.markForDestroy();
							}
						};
					} else {
						Layout.addMessage(new MessageBean("Per questo tipo di azione devi selezionare solo documenti o solo fascicoli/aggregati di documenti",
								"", MessageType.WARNING));
					}
				}
			};
		}

		if (apposizioneCommentiMultiButton == null) {
			apposizioneCommentiMultiButton = new MultiToolStripButton("archivio/note_commenti.png", this, I18NUtil.getMessages().archivio_layout_apposizioneCommentiMultiButton_title(), false) {

				@Override
				public boolean toShow() {
					return (Layout.isPrivilegioAttivo("GRD/UD/UUD") || Layout.isPrivilegioAttivo("GRD/FLD/UM"));
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					if (listaUdFolder.getLength() == 1) {
						((ArchivioList) getList()).manageApposizioneCommenti(listaUdFolder.get(0));
					} else {
						
						String title = I18NUtil.getMessages().archivio_layout_apposizioneCommentiPopupMassivo_title();					
						new ApposizioneCommentiPopup(title, null, null) {

							@Override
							public void onClickOkButton(Record object, final DSCallback callback) {
								RecordList listaUdFolderNoteAggiornate = new RecordList();
								for (int i = 0; i < listaUdFolder.getLength(); i++) {
									Record udFolder = listaUdFolder.get(i);
									String noteOld = udFolder.getAttribute("note");
									String noteInserita = object.getAttribute("note");
									String causaleAggNoteInserita = object.getAttribute("causaleAggNote");
									StringBuilder noteAggiornata = new StringBuilder();
									if (noteOld != null && !"".equals(noteOld)) {
										noteAggiornata.append(noteOld);
										noteAggiornata.append("\n");
									}
									noteAggiornata.append(noteInserita);
									// aggiorno aggiungendo la nota inserita alle note già presenti
									Record udFolderNoteAggiornate = new Record();
									udFolderNoteAggiornate.setAttribute("flgUdFolder", udFolder.getAttribute("flgUdFolder"));
									udFolderNoteAggiornate.setAttribute("idUdFolder", udFolder.getAttribute("idUdFolder"));
									if (noteAggiornata != null) {
										udFolderNoteAggiornate.setAttribute("note", noteAggiornata.toString());
									}
									if (causaleAggNoteInserita != null) {
										udFolderNoteAggiornate.setAttribute("causaleAggNote", causaleAggNoteInserita);
									}
									listaUdFolderNoteAggiornate.add(udFolderNoteAggiornate);
								}

								Record lRecordSelezionatiNoteAggiornate = new Record();
								lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);

								final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
								lGwtRestDataSource.executecustom("apposizioneCommenti", lRecordSelezionatiNoteAggiornate, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
												"Apposizione commenti effettuata con successo",
												"Tutti i record selezionati per l'apposizione commenti sono andati in errore!",
												"Alcuni dei record selezionati per l'apposizione commenti sono andati in errore!", callback);
									}
								});
							}
						};
					}
				}
			};
		}

		if (segnaComeVisionatoMultiButton == null) {
			segnaComeVisionatoMultiButton = new MultiToolStripButton("postaElettronica/flgRicevutaLettura.png", this, I18NUtil.getMessages().segnaComeVisionato_menu_apri_title(), false) {
				
				@Override
				public boolean toShow() {
					return (Layout.isPrivilegioAttivo("GRD/UD/UUD") || Layout.isPrivilegioAttivo("GRD/FLD/UM"))  && !AurigaLayout.isAttivoClienteADSP();
				}
				
				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					if (listaUdFolder.getLength() == 1) {
						((ArchivioList) getList()).segnaComeVisionato(listaUdFolder.get(0));
					} else {
						
						// Segna come visionato massivo
						
						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboUoDestNotificheDataSource", "key", FieldType.TEXT);
						lGwtRestDataSource.fetchData(null, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									
									RecordList listaUoDestNotifiche = response.getDataAsRecordList();
									
									String title = I18NUtil.getMessages().segnaComeVisionato_PopupMassivo_title();
									
									SegnaComeVisionatoPopup segnaComeVisionatoPopup = new SegnaComeVisionatoPopup(title, null, listaUoDestNotifiche) {

										@Override
										public void onClickOkButton(Record object, final DSCallback callback) {
											RecordList listaUdFolderNoteAggiornate = new RecordList();
											for (int i = 0; i < listaUdFolder.getLength(); i++) {
												Record udFolder = listaUdFolder.get(i);
												String noteInserita = object.getAttribute("note");
												Record udFolderNoteAggiornate = new Record();
												udFolderNoteAggiornate.setAttribute("flgUdFolder", udFolder.getAttribute("flgUdFolder"));
												udFolderNoteAggiornate.setAttribute("idUdFolder", udFolder.getAttribute("idUdFolder"));
												udFolderNoteAggiornate.setAttribute("note", noteInserita);
												listaUdFolderNoteAggiornate.add(udFolderNoteAggiornate);
											}
											Record lRecordSelezionatiNoteAggiornate = new Record();
											lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);
											lRecordSelezionatiNoteAggiornate.setAttribute("listaUoSelezionate", object.getAttributeAsRecordList("listaUoSelezionate"));	
											lRecordSelezionatiNoteAggiornate.setAttribute("flgAnchePerUtente", object.getAttributeAsRecordList("flgAnchePerUtente"));
											final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
											lGwtRestDataSource.executecustom("segnaComeVisionato", lRecordSelezionatiNoteAggiornate, new DSCallback() {

												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
															"Operazione effettuata con successo",
															"Tutti i record selezionati sono andati in errore!",
															"Alcuni dei record selezionati sono andati in errore!", callback);
												}
											});
										}
									};
									segnaComeVisionatoPopup.show();
								}
							}
						});											
					}
				}
			};
		}
		
		if (downloadZipAllegati == null) {
			downloadZipAllegati = new MultiToolStripButton("buttons/download_zip.png", this, "Scarica allegati come zip", false) {

				@Override
				public boolean toShow() {
					return true;
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					boolean isFolder = false;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						String tipoDoc = (list.getSelectedRecords()[i]).getAttribute("flgUdFolder");
						if (!tipoDoc.startsWith("F")) {
							listaUdFolder.add(list.getSelectedRecords()[i]);
						} else
							isFolder = true;
					}
					if (!listaUdFolder.isEmpty()) {
						if (isFolder)
							Layout.addMessage(
									new MessageBean(I18NUtil.getMessages().alert_archivio_massivo_downloadDocsZip(), "",
											MessageType.WARNING));

						final Menu downloadZipMenu = new Menu();
						MenuItem scaricaFileMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_originali(), "buttons/download_zip.png");
						scaricaFileMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								downloadZipDocument(listaUdFolder, "scaricaOriginali");
							}
						});
						downloadZipMenu.addItem(scaricaFileMenuItem);

						final Record detailRecord = new Record(detail.getValuesManager().getValues());
						if(showOperazioniTimbratura(detailRecord)) {
							MenuItem scaricaFileTimbratiSegnaturaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_segnatura(), "buttons/download_zip.png");
							scaricaFileTimbratiSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
								@Override
								public void onClick(MenuItemClickEvent event) {
									downloadZipDocument(listaUdFolder, "scaricaTimbratiSegnatura");
								}
							});
							downloadZipMenu.addItem(scaricaFileTimbratiSegnaturaMenuItem);
	
							if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA")) {
								MenuItem scaricaFileTimbratiConformeStampaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_stampa(), "buttons/download_zip.png");
								scaricaFileTimbratiConformeStampaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
									@Override
									public void onClick(MenuItemClickEvent event) {
										downloadZipDocument(listaUdFolder, "scaricaTimbratiConformeStampa");
									}
								});
								downloadZipMenu.addItem(scaricaFileTimbratiConformeStampaMenuItem);
	
								MenuItem scaricaFileTimbratiConformeDigitaleMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_digitale(),"buttons/download_zip.png");
								scaricaFileTimbratiConformeDigitaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
									@Override
									public void onClick(MenuItemClickEvent event) {
										downloadZipDocument(listaUdFolder, "scaricaTimbratiConformeDigitale");
									}
								});
								downloadZipMenu.addItem(scaricaFileTimbratiConformeDigitaleMenuItem);
							}
	
							if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
								MenuItem scaricaFileTimbratiConformeCartaceoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_cartaceo(),"buttons/download_zip.png");
								scaricaFileTimbratiConformeCartaceoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
									@Override
									public void onClick(MenuItemClickEvent event) {
										downloadZipDocument(listaUdFolder, "scaricaTimbratiConformeCartaceo");
									}
								});
								downloadZipMenu.addItem(scaricaFileTimbratiConformeCartaceoMenuItem);
							}
						
						}
						
						if(Layout.isPrivilegioAttivo("SCC")) {
							String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
							MenuItem scaricaFileConformitaCustomMenuItem = new MenuItem("File " + labelConformitaCustom, "buttons/download_zip.png");
							scaricaFileConformitaCustomMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
								
								@Override
								public void onClick(MenuItemClickEvent event) {
									downloadZipDocument(listaUdFolder, "scaricaConformitaCustom");
								}
							});
							downloadZipMenu.addItem(scaricaFileConformitaCustomMenuItem);
						}

						MenuItem scaricaFileSbustatiMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_sbustati(),"buttons/download_zip.png");
						scaricaFileSbustatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								downloadZipDocument(listaUdFolder, "scaricaSbustati");
							}
						});
						downloadZipMenu.addItem(scaricaFileSbustatiMenuItem);

						downloadZipMenu.showContextMenu();
					} else {
						Layout.addMessage(
								new MessageBean(I18NUtil.getMessages().alert_archivio_massivo_alldoc_downloadDocsZip(),
										"", MessageType.WARNING));
					}
				}

				public void downloadZipDocument(final RecordList listaUdFolder, String operazione) {
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					
					if(!"scaricaOriginali".equalsIgnoreCase(operazione) && !"scaricaSbustati".equalsIgnoreCase(operazione) && !"scaricaConformitaCustom".equalsIgnoreCase(operazione)) {
						if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaTimbroDocZip")) {
							showOpzioniTimbratura(operazione,record, "archivio");
						}else {
							String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro");
							String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro");
							String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina");
							
							Record opzioniTimbro = new Record();
							opzioniTimbro.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
							opzioniTimbro.setAttribute("posizioneTimbro", posizioneTimbroPref);
							opzioniTimbro.setAttribute("tipoPagina", tipoPaginaPref);
							
							manageGenerateDocZipArchivioDataSource(operazione, record, opzioniTimbro);
							
						}
					}else {
						manageGenerateDocZipArchivioDataSource(operazione, record, null);
					}
				}

				
			};
		}
			
		// Scarica il file ZIP con i file che sono stati pubblicati
		if (downloadZipFilePubblicati == null) {
			downloadZipFilePubblicati = new MultiToolStripButton("file/zip.png", this, "Scarica file pubblicati come zip", false) {

				@Override
				public boolean toShow() {
					return AurigaLayout.getParametroDBAsBoolean("ATTIVA_GESTIONE_PUBBL_FILE");
					
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					boolean isFolder = false;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						String tipoDoc = (list.getSelectedRecords()[i]).getAttribute("flgUdFolder");
						if (!tipoDoc.startsWith("F")) {
							listaUdFolder.add(list.getSelectedRecords()[i]);
						} else
							isFolder = true;
					}
					if (!listaUdFolder.isEmpty()) {
						if (isFolder)
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().alert_archivio_massivo_downloadDocsZip(), "", MessageType.WARNING));

						downloadZipDocumentPubblicati(listaUdFolder);
					} else {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().alert_archivio_massivo_alldoc_downloadDocsZip(), "", MessageType.WARNING));
					}
				}

				public void downloadZipDocumentPubblicati(final RecordList listaUdFolder) {
					Record r = new Record();
					r.setAttribute("listaRecord", listaUdFolder);

					final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
					lGwtRestDataSource.extraparam.put("messageError", I18NUtil.getMessages().alert_archivio_list_downloadDocsZip());
					lGwtRestDataSource.extraparam.put("limiteMaxZipError", I18NUtil.getMessages().alert_archivio_list_limiteMaxZipError());
					lGwtRestDataSource.executecustom("generateDocsPubblicatiZip", r, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record record = response.getData()[0];
								String message = record.getAttribute("message");
								if (message != null && !"".equals(message)) {
									Layout.addMessage(new MessageBean(message, "", MessageType.ERROR));
								} else {
									String uri = record.getAttribute("storageZipRemoteUri");
									String nomeFile = record.getAttribute("zipName");
									Record lRecord = new Record();
									lRecord.setAttribute("displayFilename", nomeFile);
									lRecord.setAttribute("uri", uri);
									lRecord.setAttribute("sbustato", "false");
									lRecord.setAttribute("remoteUri", true);
									DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
								}
							}

						}
					});
				}
			};			
		}										
		
		return new MultiToolStripButton[] { aggiungiAPreferitiMultiButton, rimuoviDaPreferitiMultiButton, assegnaRiservatezzaMultiButton,
				rimuoviRiservatezzaMultiButton, stampaEtichettaMultiButton, stampaDistintaSpedizioneMultiButton, fascicolaMultiButton, smistaCCMultiButton, assegnaMultiButton,
				apposizioneCommentiMultiButton, segnaComeVisionatoMultiButton, downloadZipAllegati, downloadZipFilePubblicati, modificaStatoDocMultiButton,
				chiudiFascicoloMultiButton, modificaTipologiaMultiButton, riapriFascicoloMultiButton
		};
	}

	public void massiveStampaDistintaSpedizioneCallback(GWTRestService<Record, Record> lGwtRestDataSource, DSResponse response, RecordList lista,
			DSCallback callback) {

		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			int numFascicoli = data.getAttributeAsInt("numFascicoli");
			boolean print = data.getAttributeAsString("uri") != null;
			boolean listaDistintaSpedizioneVuota = data.getAttributeAsBoolean("listaDistintaSpedizioneVuota");
			if (listaDistintaSpedizioneVuota) {
				Layout.addMessage(new MessageBean("Non sono presenti dati da riportare nella stampa.", "", MessageType.INFO));
			} else if (lista.getLength() == numFascicoli)
				Layout.addMessage(new MessageBean("La selezione comprende solo fascicoli. La stampa etichetta indirizzi è prevista solo per i documenti,", "",
						MessageType.ERROR));

			else if (errorMessages != null) {
				Layout.addMessage(new MessageBean(data.getAttributeAsMap("errorMessages").get("error").toString(), "", MessageType.ERROR));
			} else if (print) {
				Record record = new Record();
				record.setAttribute("formatoExport", "PDF");

				String uri = data.getAttributeAsString("uri");
				Window.Location.assign(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=stampa_distinta_spedizione.pdf&url="
						+ URL.encode(uri));
			} else
				Layout.addMessage(new MessageBean("Nessun documento da stampare", "", MessageType.INFO));
		}
	}

	public void massivePrintLabelCallback(GWTRestService<Record, Record> lGwtRestDataSource, DSResponse response, RecordList lista, DSCallback callback) {

		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			int numFascicoli = data.getAttributeAsInt("numFascicoli");
			boolean printEtichetta = data.getAttributeAsStringArray("etichette") != null && data.getAttributeAsStringArray("etichette").length > 0;
			if (lista.getLength() == numFascicoli)
				Layout.addMessage(new MessageBean("La selezione comprende solo fascicoli. La stampa etichetta indirizzi è prevista solo per i documenti,", "",
						MessageType.ERROR));

			else if (errorMessages != null) {
				Layout.addMessage(new MessageBean(data.getAttributeAsMap("errorMessages").get("error").toString(), "", MessageType.ERROR));
			} else if (printEtichetta) {
				Record record = new Record();
				record.setAttribute("formatoExport", "PDF");
				record.setAttribute("fields", data.getAttributeAsStringArray("etichette"));
				lGwtRestDataSource.call(record, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						String filename = "Results." + "PDF";
						String uri = object.getAttribute("tempFileOut");
						Window.Location.assign(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename)
								+ "&url=" + URL.encode(uri));
					}
				});
			} else
				Layout.addMessage(new MessageBean("Nessun documento da stampare", "", MessageType.INFO));
		}
	}
	
	public void singleOperationCallback(DSResponse response, Record record, String pkField, String nameField, String successMessage,
			String errorMessage, DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			if (errorMessages != null) {
				errorMsg = errorMessage != null ? errorMessage : "";
				if (errorMessages.get(record.getAttribute(pkField)) != null) {
					errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));
				}
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
		} else {
			Layout.hideWaitPopup();
		}
	}

	public void massiveOperationCallback(DSResponse response, RecordList lista, String pkField, String nameField, String successMessage,
			String completeErrorMessage, String partialErrorMessage, DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			int[] recordsToSelect = null;
			RecordList listaErrori = new RecordList();
			if (errorMessages != null && errorMessages.size() > 0) {
				recordsToSelect = new int[errorMessages.size()];
				if (lista.getLength() > errorMessages.size()) {
					errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
				} else {
					errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				}
				int rec = 0;
				for (int i = 0; i < lista.getLength(); i++) {
					Record record = lista.get(i);
					if (errorMessages.get(record.getAttribute(pkField)) != null) {
						recordsToSelect[rec++] = list.getRecordIndex(record);
						Record recordErrore = new Record();
						if ("U".equals(record.getAttribute("flgUdFolder"))) {
							recordErrore.setAttribute("idError", getEstremiUdFromLayout(record));
							errorMsg += "<br/>" + getEstremiUdFromLayout(record) + ": " + errorMessages.get(record.getAttribute(pkField));
						} else if ("F".equals(record.getAttribute("flgUdFolder"))) {
							recordErrore.setAttribute("idError", getEstremiFolderFromLayout(record));
							errorMsg += "<br/>" + getEstremiFolderFromLayout(record) + ": " + errorMessages.get(record.getAttribute(pkField));
						} else {
							recordErrore.setAttribute("idError", record.getAttribute(nameField));
							errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));							
						}
						recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
						listaErrori.add(recordErrore);
					}
				}
			}
			doSearchAndSelectRecords(recordsToSelect);
			Layout.hideWaitPopup();
			if(listaErrori != null && listaErrori.getLength() > 0) {
				ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Estremi", listaErrori, lista.getLength(), 600, 300);
				errorePopup.show();
			} else if (errorMsg != null && !"".equals(errorMsg)) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				if (successMessage != null && !"".equalsIgnoreCase(successMessage)) {
					Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				}
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		} else {
			Layout.hideWaitPopup();
		}
	}

	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		GWTRestDataSource archivioDS = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		archivioDS.addParam("interesseCessato", "I");
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RICERCA_PREGRESSO")) {
			archivioDS.addParam("flgArchivioPregresso", nomeEntita != null && "archivio_pregresso".equals(nomeEntita) ? "1" : "0");
		}
		return archivioDS;
	}
	
	private static GWTRestDataSource getArchivioDataSource() {
		return getArchivioDataSource(false);
	}
	
	private static GWTRestDataSource getArchivioPregressoDataSource() {
		return getArchivioDataSource(true);
	}
	
	private static GWTRestDataSource getArchivioDataSource(boolean flgArchivioPregresso) {
		GWTRestDataSource archivioDS = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		archivioDS.addParam("interesseCessato", "I");
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RICERCA_PREGRESSO")) {
			archivioDS.addParam("flgArchivioPregresso", flgArchivioPregresso ? "1" : "0");
		}
		return archivioDS;
	}

	@Override
	public void reload(final DSCallback callback) {
		
		if (detail instanceof ProtocollazioneDetail) {
			((ProtocollazioneDetail) detail).reload(callback);
		} else if(detail instanceof RichiestaAccessoAttiDetail) {
			((RichiestaAccessoAttiDetail) detail).reload(callback);
		} else {
			super.reload(callback);
		}
	}

	@Override
	public void reloadDetailCallback() {
		
		if (detail instanceof ProtocollazioneDetail) {
			((ProtocollazioneDetail) detail).reloadDetailCallback();
		} else {
			super.reloadDetailCallback();
		}
	}

	@Override
	public void delete(final Record record) {
		if (detail instanceof ArchivioDetail || detail instanceof FolderCustomDetail) {
			((ArchivioList) list).deleteFascicoloFromList(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					hideDetail(true);
				}
			});
		}
	}

	public void setSaveButtonTitle(String title) {
		saveButton.setTitle(title);
	}

	@Override
	public void newMode() {
		newMode(false);
	}

	public void newComeCopiaMode() {
		newMode(true);
	}

	public void newMode(boolean isNewProtComeCopia) {		
		boolean isActiveModal = AurigaLayout.getImpostazioniSceltaAccessibilitaAsBoolean("mostraModal");
		if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
			this.mode = "new";
//			showDetail();
			detail.newMode();
			if (fullScreenDetail && !showOnlyDetail) {
				backToListButton.show();
			} else {
				backToListButton.hide();
			}
			editButton.hide();
			saveButton.show();
			reloadDetailButton.hide();
			undoButton.hide();
			deleteButton.hide();
			altreOpButton.hide();
			lookupButton.hide();
		} else {
//			super.newMode();
			manageNewMode(true);			
		}
		registraPrelievoButton.hide();
		modificaPrelievoButton.hide();
		eliminaPrelievoButton.hide();
		registraRestituzionePrelievoButton.hide();				
		saveButton.setTitle(I18NUtil.getMessages().saveButton_prompt());
		if (detail instanceof ArchivioDetail) {
			((ArchivioDetail) detail).newMode();
			altreOpButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		} else if (detail instanceof FolderCustomDetail) {
			((FolderCustomDetail) detail).newMode();
			altreOpButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		} else if (detail instanceof ProtocollazioneDetail) {
			((ProtocollazioneDetail) detail).newMode(isNewProtComeCopia);
			saveButton.setTitle(I18NUtil.getMessages().protocollazione_detail_registraButton_prompt());
			altreOpButton.hide();
			deleteButton.hide();
			if (detail instanceof ProtocollazioneDetailBozze) {
				verificaRegistrazioneButton.hide();
				salvaComeModelloButton.show();
				setSaveButtonTitle(I18NUtil.getMessages().bozze_creanuovabozza_salva());
			} else {
				verificaRegistrazioneButton.show();
				if (((ProtocollazioneDetail) detail).showModelliSelectItem()) {
					salvaComeModelloButton.show();
				} else {
					salvaComeModelloButton.hide();
				}
			}
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			((RichiestaAccessoAttiDetail) detail).newMode();
			altreOpButton.hide();
			deleteButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		} else {
			altreOpButton.hide();
			deleteButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		}
		stampaEtichettaButton.hide();
		frecciaStampaEtichettaButton.hide();
		assegnazioneButton.hide();
		frecciaAssegnazioneButton.hide();
		rispondiButton.hide();
		condivisioneButton.hide();
		frecciaCondivisioneButton.hide();
		smistaButton.hide();
		smistaCCButton.hide();
		invioAlProtocolloButton.hide();
		frecciaInvioAlProtocolloButton.hide();
		assegnaCondividiButton.hide();
		frecciaAssegnaCondividiButton.hide();
		stampaMenuButton.hide();
		stampaButton.hide();
		stampaRicevutaButton.hide();
		nuovaProtComeCopiaButton.hide();
		presaInCaricoButton.hide();
		restituisciButton.hide();
		segnaComeVisionatoButton.hide();
		classificazioneFascicolazioneButton.hide();
		modificaButton.hide();
		frecciaModificaButton.hide();
		regAccessoCivicoButton.hide();
		modificaDatiRegButton.hide();
		revocaAttoButton.hide();
		protocollazioneEntrataButton.hide();
		protocollazioneUscitaButton.hide();
		protocollazioneInternaButton.hide();
		// permessiUdButton.hide();
		invioPECButton.hide();
		invioMailRicevutaButton.hide();
		invioPEOButton.hide();
		inviaRaccomandataButton.hide();
		inviaPostaPrioritariaButton.hide();		
		detailDownloadDocsZip.hide();
		frecciaDownloadZipButton.hide();
		chiudiFascicoloButton.hide();
		riapriFascicoloButton.hide();
		versaInArchivioStoricoFascicoloButton.hide();
		avviaIterButton.hide();
		osservazioniNotificheButton.hide();
		apposizioneFirmaButton.hide();
		rifiutoApposizioneFirmaButton.hide();
		apposizioneFirmaProtocollazioneButton.hide();
		apposizioneVistoButton.hide();
		rifiutoApposizioneVistoButton.hide();
		pubblicazioneTraspAmmButton.hide();		
	}

	@Override
	public void viewMode() {		
		boolean isActiveModal = AurigaLayout.getImpostazioniSceltaAccessibilitaAsBoolean("mostraModal");		
		if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
			detail.viewMode();
			if (fullScreenDetail && !showOnlyDetail) {
				backToListButton.show();
			} else {
				backToListButton.hide();
			}
			if (multiselect) {
				editButton.hide();
			} else {
				editButton.show();
			}
			saveButton.hide();
			reloadDetailButton.hide();
			undoButton.hide();
			deleteButton.show();
			altreOpButton.show();
			if (isLookup()) {
				lookupButton.show();
			} else {
				lookupButton.hide();
			}
		} else {
			super.viewMode();
		}		
		registraPrelievoButton.hide();
		modificaPrelievoButton.hide();
		eliminaPrelievoButton.hide();
		registraRestituzionePrelievoButton.hide();
		rispondiButton.hide();		
		Record record = new Record(detail.getValuesManager().getValues());
		if (detail instanceof ArchivioDetail) {
			((ArchivioDetail) detail).viewMode();
			if (record.getAttributeAsBoolean("abilModificaDati")) {
				editButton.show();
			} else {
				editButton.hide();
			}
			presaInCaricoButton.hide();
			if (record.getAttributeAsBoolean("abilRestituzione")) {
				restituisciButton.show();
			} else {
				restituisciButton.hide();
			}
			if (record.getAttributeAsBoolean("abilSetVisionato")) {
				segnaComeVisionatoButton.show();
			} else {
				segnaComeVisionatoButton.hide();
			}
			classificazioneFascicolazioneButton.hide();
			if (record.getAttributeAsBoolean("abilModificaTipologia")) {
				frecciaModificaButton.show();
			} else {
				frecciaModificaButton.hide();
			}
			if (record.getAttributeAsBoolean("abilEliminazione")) {
				deleteButton.show();
			} else {
				deleteButton.hide();
			}
			stampaEtichettaButton.hide();
			frecciaStampaEtichettaButton.hide();
			assegnazioneButton.hide();
			frecciaAssegnazioneButton.hide();
			condivisioneButton.hide();
			frecciaCondivisioneButton.hide();
			smistaButton.hide();
			smistaCCButton.hide();
			invioAlProtocolloButton.hide();
			frecciaInvioAlProtocolloButton.hide();
			assegnaCondividiButton.hide();
			frecciaAssegnaCondividiButton.hide();
			stampaMenuButton.hide();
			if (record.getAttributeAsBoolean("abilStampaRicevuta")) {
				stampaRicevutaButton.show();				
			} else {
				stampaRicevutaButton.hide();				
			}
			if (record.getAttributeAsBoolean("abilStampaCopertina") || record.getAttributeAsBoolean("abilStampaSegnatura") || record.getAttributeAsBoolean("abilStampaListaContenuti")) {				
				String titleUnicaStampaAbilitata = getTitleUnicaStampaAbilitata(record);
				stampaButton.setTitle(titleUnicaStampaAbilitata != null ? titleUnicaStampaAbilitata : "Stampa ...");
				stampaButton.show();				
			} else {
				stampaButton.hide();				
			}
			nuovaProtComeCopiaButton.hide();
			presaInCaricoButton.hide();
			if (record.getAttributeAsBoolean("abilRestituzione")) {
				restituisciButton.show();
			} else {
				restituisciButton.hide();
			}
			if (record.getAttributeAsBoolean("abilSetVisionato")) {
				segnaComeVisionatoButton.show();
			} else {
				segnaComeVisionatoButton.hide();
			}
			classificazioneFascicolazioneButton.hide();
			modificaButton.hide();
//			frecciaModificaButton.hide();
			regAccessoCivicoButton.hide();
			modificaDatiRegButton.hide();
			// permessiUdButton.hide();
			invioPECButton.hide();
			invioMailRicevutaButton.hide();
			invioPEOButton.hide();
			inviaRaccomandataButton.hide();
			inviaPostaPrioritariaButton.hide();
			salvaComeModelloButton.hide();
			if (record.getAttributeAsBoolean("abilAvvioIterWF")) {
				avviaIterButton.show();
			} else {
				avviaIterButton.hide();
			}
			if (showOsservazioniNotificheButton(record)){
				osservazioniNotificheButton.show();
			}else{
				osservazioniNotificheButton.hide();
			}
			revocaAttoButton.hide();
			protocollazioneEntrataButton.hide();
			protocollazioneUscitaButton.hide();
			protocollazioneInternaButton.hide();
			apposizioneFirmaButton.hide();
			rifiutoApposizioneFirmaButton.hide();
			apposizioneFirmaProtocollazioneButton.hide();
			apposizioneVistoButton.hide();
			rifiutoApposizioneVistoButton.hide();
			pubblicazioneTraspAmmButton.hide();
		} else if (detail instanceof FolderCustomDetail) {
			((FolderCustomDetail) detail).viewMode();
			if (record.getAttributeAsBoolean("abilModificaDati")) {
				editButton.show();
			} else {
				editButton.hide();
			}
			presaInCaricoButton.hide();
			if (record.getAttributeAsBoolean("abilRestituzione")) {
				restituisciButton.show();
			} else {
				restituisciButton.hide();
			}
			if (record.getAttributeAsBoolean("abilSetVisionato")) {
				segnaComeVisionatoButton.show();
			} else {
				segnaComeVisionatoButton.hide();
			}
			classificazioneFascicolazioneButton.hide();
			if (record.getAttributeAsBoolean("abilModificaTipologia")) {
				frecciaModificaButton.show();
			} else {
				frecciaModificaButton.hide();
			}
			if (record.getAttributeAsBoolean("abilEliminazione")) {
				deleteButton.show();
			} else {
				deleteButton.hide();
			}
			stampaEtichettaButton.hide();
			frecciaStampaEtichettaButton.hide();
			stampaButton.hide();
			stampaRicevutaButton.hide();
			assegnazioneButton.hide();
			frecciaAssegnazioneButton.hide();
			condivisioneButton.hide();
			frecciaCondivisioneButton.hide();
			smistaButton.hide();
			smistaCCButton.hide();
			invioAlProtocolloButton.hide();
			frecciaInvioAlProtocolloButton.hide();
			assegnaCondividiButton.hide();
			frecciaAssegnaCondividiButton.hide();
			stampaMenuButton.hide();
			nuovaProtComeCopiaButton.hide();
			presaInCaricoButton.hide();
			if (record.getAttributeAsBoolean("abilRestituzione")) {
				restituisciButton.show();
			} else {
				restituisciButton.hide();
			}
			if (record.getAttributeAsBoolean("abilSetVisionato")) {
				segnaComeVisionatoButton.show();
			} else {
				segnaComeVisionatoButton.hide();
			}
			classificazioneFascicolazioneButton.hide();
			modificaButton.hide();
//			frecciaModificaButton.hide();
			regAccessoCivicoButton.hide();
			modificaDatiRegButton.hide();
			// permessiUdButton.hide();
			invioPECButton.hide();
			invioMailRicevutaButton.hide();
			invioPEOButton.hide();
			inviaRaccomandataButton.hide();
			inviaPostaPrioritariaButton.hide();
			salvaComeModelloButton.hide();
			if (record.getAttributeAsBoolean("abilAvvioIterWF")) {
				avviaIterButton.show();
			} else {
				avviaIterButton.hide();
			}
			if (showOsservazioniNotificheButton(record)){
				osservazioniNotificheButton.show();
			}else{
				osservazioniNotificheButton.hide();
			}
			revocaAttoButton.hide();
			protocollazioneEntrataButton.hide();
			protocollazioneUscitaButton.hide();
			protocollazioneInternaButton.hide();
			apposizioneFirmaButton.hide();
			rifiutoApposizioneFirmaButton.hide();
			apposizioneFirmaProtocollazioneButton.hide();
			apposizioneVistoButton.hide();
			rifiutoApposizioneVistoButton.hide();
			pubblicazioneTraspAmmButton.hide();
			if ((detail instanceof PraticaPregressaDetail) && ((PraticaPregressaDetail) detail).showRegistraPrelievoButton()) {
				registraPrelievoButton.show();
			}
			if ((detail instanceof PraticaPregressaDetail) && ((PraticaPregressaDetail) detail).showModificaPrelievoButton()) {
				modificaPrelievoButton.show();
			}
			if ((detail instanceof PraticaPregressaDetail) && ((PraticaPregressaDetail) detail).showEliminaPrelievoButton()) {
				eliminaPrelievoButton.show();
			}
			if ((detail instanceof PraticaPregressaDetail) && ((PraticaPregressaDetail) detail).showRegistraRestituzionePrelievoButton()) {
				registraRestituzionePrelievoButton.show();
			}			
		} else if (detail instanceof ProtocollazioneDetail) {
			((ProtocollazioneDetail) detail).viewMode();
			if (detail instanceof ProtocollazioneDetailBozze) {
				stampaEtichettaButton.hide();
				frecciaStampaEtichettaButton.hide();
//				if(showAssegnazioneButton(record)){
//					assegnazioneButton.show();
//					frecciaAssegnazioneButton.show();
//				}else{
					assegnazioneButton.hide();
					frecciaAssegnazioneButton.hide();
//				}
//				if(showCondivisioneButton(record)){
//					condivisioneButton.show();		
//					frecciaCondivisioneButton.show();
//				}else{
					condivisioneButton.hide();
					frecciaCondivisioneButton.hide();
//				}
				if(showSmistaButton(record)) {
					smistaButton.show();
				} else {
					smistaButton.hide();
				}
				if(showSmistaCCButton(record)) {
					smistaCCButton.show();
				} else {
					smistaCCButton.hide();
				}	
				if(showInvioAlProtocolloButton(record)) {
					invioAlProtocolloButton.show();
					frecciaInvioAlProtocolloButton.show();
				} else {
					invioAlProtocolloButton.hide();
					frecciaInvioAlProtocolloButton.hide();
				}
				if(!showAssegnazioneButton(record) && !showCondivisioneButton(record) && !showModAssInviiCCButton(record)) {
					assegnaCondividiButton.hide();
					frecciaAssegnaCondividiButton.hide();
				} else {
					assegnaCondividiButton.show();
					frecciaAssegnaCondividiButton.show();
				}				
				if (record.getAttributeAsBoolean("abilStampaCopertina") && record.getAttributeAsBoolean("abilStampaRicevuta")) {
					stampaMenuButton.setTitle(I18NUtil.getMessages().protocollazione_detail_stampe());
					stampaMenuButton.show();
					stampaButton.hide();
					stampaRicevutaButton.hide();
				} else {
					stampaMenuButton.hide();
					if (record.getAttributeAsBoolean("abilStampaCopertina")) {
						stampaButton.setTitle(I18NUtil.getMessages().protocollazione_detail_stampaCopertinaButton_prompt());
						stampaButton.show();
					} else {
						stampaButton.hide();
					}
					if (record.getAttributeAsBoolean("abilStampaRicevuta")) {
						stampaRicevutaButton.setTitle(I18NUtil.getMessages().protocollazione_detail_stampaRicevutaButton_prompt());
						stampaRicevutaButton.show();
					} else {
						stampaRicevutaButton.hide();
					}
				}
				if(record.getAttributeAsBoolean("abilNuovoComeCopia")) {
					nuovaProtComeCopiaButton.show();
				} else {
					nuovaProtComeCopiaButton.hide();
				}
				if (record.getAttributeAsBoolean("abilPresaInCarico")) {
					presaInCaricoButton.show();
				} else {
					presaInCaricoButton.hide();
				}
				if (record.getAttributeAsBoolean("abilRestituzione")) {
					restituisciButton.show();
				} else {
					restituisciButton.hide();
				}
				if (record.getAttributeAsBoolean("abilSetVisionato")) {
					segnaComeVisionatoButton.show();
				} else {
					segnaComeVisionatoButton.hide();
				}
				if (record.getAttributeAsBoolean("abilClassificazioneFascicolazione") ||
						record.getAttributeAsBoolean("abilOrganizza")) {
					classificazioneFascicolazioneButton.show();
				} else {
					classificazioneFascicolazioneButton.hide();
				}
				if(record.getAttributeAsBoolean("abilModificaDatiExtraIter") || record.getAttributeAsBoolean("abilModificaDati")) {
					modificaButton.show();
				} else {
					modificaButton.hide();
				}
				if (record.getAttributeAsBoolean("abilRegAccessoCivico")) {
					regAccessoCivicoButton.show();
				} else {
					regAccessoCivicoButton.hide();
				}
				if (record.getAttributeAsBoolean("abilModificaTipologia") || record.getAttributeAsBoolean("abilModificaDatiExtraIter") || record.getAttributeAsBoolean("abilModificaOpereAtto") || record.getAttributeAsBoolean("abilModificaDatiPubblAtto")) {
					frecciaModificaButton.show();
				} else {
					frecciaModificaButton.hide();
				}				
				revocaAttoButton.hide();
				if(detail instanceof ProtocollazioneDetailModelli  && showProtocollazioneEntrataButton(record)){
					protocollazioneEntrataButton.show();
				}else{
					protocollazioneEntrataButton.hide();
				}
				if(showProtocollazioneUscitaButton(record)){
					protocollazioneUscitaButton.show();
				}else{
					protocollazioneUscitaButton.hide();
				}
				if(showProtocollazioneInternaButton(record)){
					protocollazioneInternaButton.show();
				}else{
					protocollazioneInternaButton.hide();
				}
				modificaDatiRegButton.hide();
				salvaComeModelloButton.hide();
				if (showFirmaButton(record)){
					apposizioneFirmaButton.show();
					rifiutoApposizioneFirmaButton.show();
				}else{
					apposizioneFirmaButton.hide();
					rifiutoApposizioneFirmaButton.hide();
				}
				if (showFirmaProtocollaButton(record)){
					apposizioneFirmaProtocollazioneButton.show();
				}else{
					apposizioneFirmaProtocollazioneButton.hide();
				}
				if (showVistoDocumentoButton(record)){
					apposizioneVistoButton.show();
					rifiutoApposizioneVistoButton.show();
				}else{
					apposizioneVistoButton.hide();
					rifiutoApposizioneVistoButton.hide();
				}
			} else {				
				if (((ProtocollazioneDetail) detail).showStampaEtichettaButton(record)) {
					stampaEtichettaButton.show();
					if (((ProtocollazioneDetail) detail).showFrecciaStampaEtichettaButton(record)) {
						frecciaStampaEtichettaButton.show();
					} else {
						frecciaStampaEtichettaButton.hide();
					}
				} else {
					stampaEtichettaButton.hide();
					frecciaStampaEtichettaButton.hide();
				}
//				if(showAssegnazioneButton(record)){
//					assegnazioneButton.show();
//					frecciaAssegnazioneButton.show();
//				}else{
					assegnazioneButton.hide();
					frecciaAssegnazioneButton.hide();
//				}
				if(!(detail instanceof RepertorioDetailUscita) && !(detail instanceof ProtocollazioneDetailUscita)){
					if(showRispondiButton(record)){
						rispondiButton.show();
					}	
				}
//				if(showCondivisioneButton(record)){
//					condivisioneButton.show();		
//					frecciaCondivisioneButton.show();
//				}else{
					condivisioneButton.hide();
					frecciaCondivisioneButton.hide();
//				}	
				if(showSmistaButton(record)) {
					smistaButton.show();
				} else {
					smistaButton.hide();
				}
				if(showSmistaCCButton(record)) {
					smistaCCButton.show();
				} else {
					smistaCCButton.hide();
				}
				if(showInvioAlProtocolloButton(record)) {
					invioAlProtocolloButton.show();
					frecciaInvioAlProtocolloButton.show();
				} else {
					invioAlProtocolloButton.hide();
					frecciaInvioAlProtocolloButton.hide();
				}
				if(!showAssegnazioneButton(record) && !showCondivisioneButton(record) && !showModAssInviiCCButton(record)) {
					assegnaCondividiButton.hide();
					frecciaAssegnaCondividiButton.hide();
				} else {
					assegnaCondividiButton.show();
					frecciaAssegnaCondividiButton.show();
				}
				if (record.getAttributeAsBoolean("abilStampaCopertina") && record.getAttributeAsBoolean("abilStampaRicevuta")) {
					stampaMenuButton.setTitle(I18NUtil.getMessages().protocollazione_detail_stampe());
					stampaMenuButton.show();
					stampaButton.hide();
					stampaRicevutaButton.hide();
				} else {
					stampaMenuButton.hide();
					if (record.getAttributeAsBoolean("abilStampaCopertina")) {
						stampaButton.setTitle(I18NUtil.getMessages().protocollazione_detail_stampaCopertinaButton_prompt());
						stampaButton.show();
					} else {
						stampaButton.hide();
					}
					if (record.getAttributeAsBoolean("abilStampaRicevuta")) {
						stampaRicevutaButton.setTitle(I18NUtil.getMessages().protocollazione_detail_stampaRicevutaButton_prompt());
						stampaRicevutaButton.show();
					} else {
						stampaRicevutaButton.hide();
					}
				}
				if (((ProtocollazioneDetail) detail).isAbilToProt()) {
					if(record.getAttributeAsBoolean("abilNuovoComeCopia")) {
						nuovaProtComeCopiaButton.show();
					} else {
						nuovaProtComeCopiaButton.hide();
					}
				} else {
					nuovaProtComeCopiaButton.hide();
				}
				if (record.getAttributeAsBoolean("abilPresaInCarico")) {
					presaInCaricoButton.show();
				} else {
					presaInCaricoButton.hide();
				}
				if (record.getAttributeAsBoolean("abilRestituzione")) {
					restituisciButton.show();
				} else {
					restituisciButton.hide();
				}
				if (record.getAttributeAsBoolean("abilSetVisionato")) {
					segnaComeVisionatoButton.show();
				} else {
					segnaComeVisionatoButton.hide();
				}
				if (record.getAttributeAsBoolean("abilClassificazioneFascicolazione") ||
						record.getAttributeAsBoolean("abilOrganizza")) {
					classificazioneFascicolazioneButton.show();
				} else {
					classificazioneFascicolazioneButton.hide();
				}
				if (record.getAttributeAsBoolean("abilModificaDatiExtraIter") || record.getAttributeAsBoolean("abilAggiuntaFile") || record.getAttributeAsBoolean("abilModificaDati")) {
					modificaButton.show();
				} else {
					modificaButton.hide();
				}
				if (record.getAttributeAsBoolean("abilRegAccessoCivico")) {
					regAccessoCivicoButton.show();
				} else {
					regAccessoCivicoButton.hide();
				}	
				if (record.getAttributeAsBoolean("abilModificaTipologia") || record.getAttributeAsBoolean("abilModificaDatiExtraIter") || record.getAttributeAsBoolean("abilModificaOpereAtto") || record.getAttributeAsBoolean("abilModificaDatiPubblAtto")) {
					frecciaModificaButton.show();
				} else {
					frecciaModificaButton.hide();
				}
				if (record.getAttributeAsBoolean("abilModificaDatiRegistrazione")) {
					modificaDatiRegButton.show();
				} else {
					modificaDatiRegButton.hide();
				}
				if (((ProtocollazioneDetail) detail).showModelliSelectItem()) {
					salvaComeModelloButton.show();
				} else {
					salvaComeModelloButton.hide();
				}
				if(showRevocaAttoButton(record)){
					revocaAttoButton.show();
				}else{
					revocaAttoButton.hide();
				}
				protocollazioneEntrataButton.hide();
				protocollazioneUscitaButton.hide();
				protocollazioneInternaButton.hide();
				if (showFirmaButton(record)){
					apposizioneFirmaButton.show();
					rifiutoApposizioneFirmaButton.show();
				}else{
					apposizioneFirmaButton.hide();
					rifiutoApposizioneFirmaButton.hide();
				}
				if (showFirmaProtocollaButton(record)){
					apposizioneFirmaProtocollazioneButton.show();
				}else{
					apposizioneFirmaProtocollazioneButton.hide();
				}
				if (showVistoDocumentoButton(record)){
					apposizioneVistoButton.show();
					rifiutoApposizioneVistoButton.show();
				}else{
					apposizioneVistoButton.hide();
					rifiutoApposizioneVistoButton.hide();
				}
			}
			if (showOsservazioniNotificheButton(record)){
				osservazioniNotificheButton.show();
			}else{
				osservazioniNotificheButton.hide();
			}
			if (record.getAttributeAsBoolean("abilInvioPEC")) {
				invioPECButton.show();
			} else {
				invioPECButton.hide();
			}
			if (record.getAttributeAsBoolean("abilInvioEmailRicevuta")) {
				invioMailRicevutaButton.show();
			} else {
				invioMailRicevutaButton.hide();
			}
			if (record.getAttributeAsBoolean("abilInvioPEO")) {
				invioPEOButton.show();
			} else {
				invioPEOButton.hide();
			}
			if (Layout.isPrivilegioAttivo("PRT/U") && ((ProtocollazioneDetail) detail) instanceof ProtocollazioneDetailUscita && !(record.getAttributeAsBoolean("annullata")) && AurigaLayout.getParametroDB("CLIENTE").equalsIgnoreCase("ARPA_LAZ")) {
				ProtocollazioneUtil.isPossibleToPostel(record.getAttributeAsInt("idUd"), ETypePoste.RACCOMANDATA.value(), new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record record) {
						if(record.getAttributeAsBoolean("isDaPostalizzare")){
							inviaRaccomandataButton.show();
						}else {
							inviaRaccomandataButton.hide();
						}
					}
				});
			}else {
				inviaRaccomandataButton.hide();
			}
			if (Layout.isPrivilegioAttivo("PRT/U") && ((ProtocollazioneDetail) detail) instanceof ProtocollazioneDetailUscita && !(record.getAttributeAsBoolean("annullata")) && AurigaLayout.getParametroDB("CLIENTE").equalsIgnoreCase("ARPA_LAZ")) {
				ProtocollazioneUtil.isPossibleToPostel(record.getAttributeAsInt("idUd"), ETypePoste.POSTA_PRIORITARIA.value(), new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record record) {
						if(record.getAttributeAsBoolean("isDaPostalizzare")){
							inviaPostaPrioritariaButton.show();
						}else {
							inviaPostaPrioritariaButton.hide();
						}
					}
				});
			}else {
				inviaPostaPrioritariaButton.hide();
			}
			editButton.hide();
//			frecciaModificaButton.hide();
			deleteButton.hide();
			if (record.getAttributeAsBoolean("abilAvvioIterWF")) {
				avviaIterButton.show();
			} else {
				avviaIterButton.hide();
			}
			if (record.getAttributeAsBoolean("abilPubblicazioneTraspAmm")) {
				pubblicazioneTraspAmmButton.show();
			} else {
				pubblicazioneTraspAmmButton.hide();
			}	
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			((RichiestaAccessoAttiDetail) detail).viewMode();
			if (record.getAttributeAsBoolean("abilModificaDati")) {
				editButton.show();
			} else {
				editButton.hide();
			}
			presaInCaricoButton.hide();
			restituisciButton.hide();
			segnaComeVisionatoButton.hide();
			classificazioneFascicolazioneButton.hide();
			if (record.getAttributeAsBoolean("abilModificaTipologia")) {
				frecciaModificaButton.show();
			} else {
				frecciaModificaButton.hide();
			}
			stampaEtichettaButton.hide();
			frecciaStampaEtichettaButton.hide();
			// Se il bottone di assegnazione va' mostrato anche in RichiestaAccessiAttiDetail, allora i vari metodi vanno sistemati e centralizzati in DocumentDetail
//			if(showAssegnazioneButton(record)){
//				assegnazioneButton.show();
//				frecciaAssegnazioneButton.show();
//			}else{
				assegnazioneButton.hide();
				frecciaAssegnazioneButton.hide();
//			}
			if(showCondivisioneButton(record)){
				condivisioneButton.show();		
				frecciaCondivisioneButton.show();
			}else{
				condivisioneButton.hide();
				frecciaCondivisioneButton.hide();
			}
			smistaButton.hide();
			smistaCCButton.hide();
			invioAlProtocolloButton.hide();
			frecciaInvioAlProtocolloButton.hide();
			assegnaCondividiButton.hide();
			frecciaAssegnaCondividiButton.hide();
			stampaMenuButton.hide();
			stampaButton.hide();
			stampaRicevutaButton.hide();
			nuovaProtComeCopiaButton.hide();
			presaInCaricoButton.hide();
			restituisciButton.hide();
			segnaComeVisionatoButton.hide();
			classificazioneFascicolazioneButton.hide();
			modificaButton.hide();
//			frecciaModificaButton.hide();
			regAccessoCivicoButton.hide();
			modificaDatiRegButton.hide();
			// permessiUdButton.hide();
			invioPECButton.hide();
			invioMailRicevutaButton.hide();
			invioPEOButton.hide();
			inviaRaccomandataButton.hide();
			inviaPostaPrioritariaButton.hide();
			deleteButton.hide();
			salvaComeModelloButton.hide();
			avviaIterButton.hide();
			if (showOsservazioniNotificheButton(record)){
				osservazioniNotificheButton.show();
			}else{
				osservazioniNotificheButton.hide();
			}
			revocaAttoButton.hide();
			protocollazioneEntrataButton.hide();
			protocollazioneUscitaButton.hide();
			protocollazioneInternaButton.hide();
			apposizioneFirmaButton.hide();
			rifiutoApposizioneFirmaButton.hide();
			apposizioneFirmaProtocollazioneButton.hide();
			apposizioneVistoButton.hide();
			rifiutoApposizioneVistoButton.hide();			
			pubblicazioneTraspAmmButton.hide();
		} else {
			stampaEtichettaButton.hide();
			frecciaStampaEtichettaButton.hide();
			assegnazioneButton.hide();
			frecciaAssegnazioneButton.hide();
			condivisioneButton.hide();
			frecciaCondivisioneButton.hide();
			smistaButton.hide();
			smistaCCButton.hide();
			invioAlProtocolloButton.hide();
			frecciaInvioAlProtocolloButton.hide();
			assegnaCondividiButton.hide();
			frecciaAssegnaCondividiButton.hide();
			stampaMenuButton.hide();
			stampaButton.hide();	
			stampaRicevutaButton.hide();
			nuovaProtComeCopiaButton.hide();
			editButton.hide();
			presaInCaricoButton.hide();
			restituisciButton.hide();
			segnaComeVisionatoButton.hide();
			classificazioneFascicolazioneButton.hide();
			modificaButton.hide();
			frecciaModificaButton.hide();
			regAccessoCivicoButton.hide();
			modificaDatiRegButton.hide();
			// permessiUdButton.hide();
			invioPECButton.hide();
			invioMailRicevutaButton.hide();
			invioPEOButton.hide();
			inviaRaccomandataButton.hide();
			inviaPostaPrioritariaButton.hide();
			deleteButton.hide();
			salvaComeModelloButton.hide();
			avviaIterButton.hide();
//			if ((detail instanceof RepertorioDetailEntrata || detail instanceof RepertorioDetailEntrata) && showRispondiButton(record)){
//				rispondiButton.show();
//			}else{
//				rispondiButton.hide();
//			}
			if (showOsservazioniNotificheButton(record)){
				osservazioniNotificheButton.show();
			}else{
				osservazioniNotificheButton.hide();
			}			
			revocaAttoButton.hide();
			protocollazioneEntrataButton.hide();
			protocollazioneUscitaButton.hide();
			protocollazioneInternaButton.hide();
			apposizioneFirmaButton.hide();
			rifiutoApposizioneFirmaButton.hide();
			apposizioneFirmaProtocollazioneButton.hide();
			apposizioneVistoButton.hide();
			rifiutoApposizioneVistoButton.hide();	
			pubblicazioneTraspAmmButton.hide();
		}
		if (isLookup() && record.getAttributeAsBoolean("flgSelXFinalita") != null && record.getAttributeAsBoolean("flgSelXFinalita")) {
			lookupButton.show();
		} else {
			lookupButton.hide();
		}
		altreOpButton.hide();
		verificaRegistrazioneButton.hide();
		if ((detail instanceof ProtocollazioneDetail) && ((ProtocollazioneDetail) detail).showDownloadDocZipButton(record)) {
			detailDownloadDocsZip.show();
			frecciaDownloadZipButton.show();
		} else {
			detailDownloadDocsZip.hide();
			frecciaDownloadZipButton.hide();
		}
		if (detail instanceof ArchivioDetail) {
			if (record.getAttributeAsBoolean("abilChiudiFascicolo")) {
				chiudiFascicoloButton.show();
			} else {
				chiudiFascicoloButton.hide();
			}
			if (record.getAttributeAsBoolean("abilRiapriFascicolo")) {
				riapriFascicoloButton.show();
			} else {
				riapriFascicoloButton.hide();
			}
			if (record.getAttributeAsBoolean("flgFascTitolario")) {
				if (record.getAttributeAsBoolean("abilVersaInArchivioStoricoFascicolo")) {
					versaInArchivioStoricoFascicoloButton.show();
				} else {
					versaInArchivioStoricoFascicoloButton.hide();
				}
			} else {
				versaInArchivioStoricoFascicoloButton.hide();
			}
		} else {
			chiudiFascicoloButton.hide();
			versaInArchivioStoricoFascicoloButton.hide();
			riapriFascicoloButton.hide();
		}
	}
	
	@Override
	public void editMode(boolean fromViewMode) {		
		boolean isActiveModal = AurigaLayout.getImpostazioniSceltaAccessibilitaAsBoolean("mostraModal");		
		if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
			this.mode = "edit";
			this.fromViewMode = fromViewMode;
			detail.editMode();
			if (fullScreenDetail && !showOnlyDetail) {
				backToListButton.show();
			} else {
				backToListButton.hide();
			}
			editButton.hide();
			saveButton.show();
			reloadDetailButton.show();
			undoButton.show();
			deleteButton.hide();
			altreOpButton.hide();
			lookupButton.hide();
		} else {
			super.editMode(fromViewMode);
		}		
		Record record = new Record(detail.getValuesManager().getValues());		
		registraPrelievoButton.hide();
		modificaPrelievoButton.hide();
		eliminaPrelievoButton.hide();
		registraRestituzionePrelievoButton.hide();		
		if (detail instanceof ArchivioDetail) {
			((ArchivioDetail) detail).editMode();
			altreOpButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		} else if (detail instanceof FolderCustomDetail) {
			((FolderCustomDetail) detail).editMode();
			altreOpButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		} else if (detail instanceof ProtocollazioneDetail) {
			((ProtocollazioneDetail) detail).editMode();
			saveButton.setTitle(I18NUtil.getMessages().saveButton_prompt());
			altreOpButton.hide();
			deleteButton.hide();
			if (detail instanceof ProtocollazioneDetailBozze) {
				verificaRegistrazioneButton.hide();
				salvaComeModelloButton.show();
				setSaveButtonTitle(I18NUtil.getMessages().bozze_creanuovabozza_salva());
			} else {
				verificaRegistrazioneButton.show();
				if (((ProtocollazioneDetail) detail).showModelliSelectItem()) {
					salvaComeModelloButton.show();
				} else {
					salvaComeModelloButton.hide();
				}
			}			
			if (((ProtocollazioneDetail) detail).isProtocollazioneBozza()) {
				reloadDetailButton.hide();
				undoButton.hide();
				salvaComeModelloButton.hide();
			}
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			((RichiestaAccessoAttiDetail) detail).editMode();
			altreOpButton.hide();
			deleteButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		} else {
			altreOpButton.hide();
			deleteButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		}
		stampaEtichettaButton.hide();
		frecciaStampaEtichettaButton.hide();
		assegnazioneButton.hide();
		frecciaAssegnazioneButton.hide();
		rispondiButton.hide();
		condivisioneButton.hide();
		frecciaCondivisioneButton.hide();
		smistaButton.hide();
		smistaCCButton.hide();
		invioAlProtocolloButton.hide();
		frecciaInvioAlProtocolloButton.hide();
		assegnaCondividiButton.hide();
		frecciaAssegnaCondividiButton.hide();
		stampaMenuButton.hide();
		stampaButton.hide();
		stampaRicevutaButton.hide();
		nuovaProtComeCopiaButton.hide();
		presaInCaricoButton.hide();
		restituisciButton.hide();
		segnaComeVisionatoButton.hide();
		classificazioneFascicolazioneButton.hide();
		modificaButton.hide();
		frecciaModificaButton.hide();
		regAccessoCivicoButton.hide();
		modificaDatiRegButton.hide();
		// permessiUdButton.hide();
		invioPECButton.hide();
		invioMailRicevutaButton.hide();
		invioPEOButton.hide();
		inviaRaccomandataButton.hide();
		inviaPostaPrioritariaButton.hide();
		detailDownloadDocsZip.hide();
		frecciaDownloadZipButton.hide();
		chiudiFascicoloButton.hide();
		riapriFascicoloButton.hide();
		versaInArchivioStoricoFascicoloButton.hide();
		avviaIterButton.hide();
		revocaAttoButton.hide();
		protocollazioneEntrataButton.hide();
		protocollazioneUscitaButton.hide();
		protocollazioneInternaButton.hide();
		apposizioneFirmaButton.hide();		
		rifiutoApposizioneFirmaButton.hide();
		apposizioneFirmaProtocollazioneButton.hide();		
		apposizioneVistoButton.hide();
		rifiutoApposizioneVistoButton.hide();	
	}

	private String getTitleUnicaStampaAbilitata(Record record) {
		String title = null;
		int nroStampeAbilitate = 0;
		if (record.getAttributeAsBoolean("abilStampaCopertina")) {				
			nroStampeAbilitate++;			
			title = "Copertina" ;
		}
		if (record.getAttributeAsBoolean("abilStampaSegnatura")) {
			nroStampeAbilitate++;	
			title = "Segnatura";
		}
		if (record.getAttributeAsBoolean("abilStampaListaContenuti")) {				
			nroStampeAbilitate++;
			title = "Lista contenuti";
		}
		if(nroStampeAbilitate > 1) {
			return null;
		}
		return title;
	}
	
	private String getNomeModelloUnicaStampaAbilitata(Record record) {
		String nomeModello = null;
		int nroStampeAbilitate = 0;
		if (record.getAttributeAsBoolean("abilStampaCopertina")) {				
			nroStampeAbilitate++;			
			nomeModello = "COPERTINA_FASC" ;
		}
		if (record.getAttributeAsBoolean("abilStampaSegnatura")) {
			nroStampeAbilitate++;	
			nomeModello = "SEGNATURA_FASC";
		}
		if (record.getAttributeAsBoolean("abilStampaListaContenuti")) {				
			nroStampeAbilitate++;
			nomeModello = "LISTA_CONTENUTI_FASC";
		}
		if(nroStampeAbilitate > 1) {
			return null;
		}
		return nomeModello;
	}
	
	@Override
	public List<ToolStripButton> getDetailButtons() {
		
		List<ToolStripButton> detailButtons = new ArrayList<ToolStripButton>();

		if(stampaEtichettaButton == null) {
			stampaEtichettaButton = new DetailToolStripButton(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_CARTACEO") ? "Timbra" :
					I18NUtil.getMessages().protocollazione_detail_stampaEtichettaButton_prompt(),
					"protocollazione/barcode.png");
			stampaEtichettaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					((ProtocollazioneDetail) detail).clickStampaEtichetta();
				}
			});
		}
		
		if(stampaRicevutaButton == null) {
			stampaRicevutaButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_stampaRicevutaButton_prompt(),
					"stampa.png");
			stampaRicevutaButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					((ProtocollazioneDetail) detail).clickStampaRicevuta();
				}
			});
		}
		
		
		if(smistaButton == null) {
			smistaButton = new DetailToolStripButton("Smista","archivio/smistamento.png");
			smistaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();																		
					if(detail instanceof ProtocollazioneDetail){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaSmista = new Record();
						recordAzioneRapidaSmista.setAttribute("azioneRapida", AzioniRapide.SMISTA_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaSmista);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);			
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaSmista = new Record();
						recordAzioneRapidaSmista.setAttribute("azioneRapida", AzioniRapide.SMISTA_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaSmista);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);									
					} 
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof ProtocollazioneDetail) {
											((ProtocollazioneDetail) detail).clickSmista(destinatariPreferiti); 											
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 
									}
								}
							}, new DSRequest());
					
				}
			});
		}
		
		if(smistaCCButton == null) {
			smistaCCButton = new DetailToolStripButton("Smista","archivio/smistamentoCC.png");
			smistaCCButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();																		
					if(detail instanceof ProtocollazioneDetail){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaSmistaCC = new Record();
						recordAzioneRapidaSmistaCC.setAttribute("azioneRapida", AzioniRapide.SMISTA_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaSmistaCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);			
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaSmistaCC = new Record();
						recordAzioneRapidaSmistaCC.setAttribute("azioneRapida", AzioniRapide.SMISTA_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaSmistaCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);									
					} 
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof ProtocollazioneDetail) {
											((ProtocollazioneDetail) detail).clickSmistaCC(destinatariPreferiti); 											
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 
									}
								}
							}, new DSRequest());
					
				}
			});
		}
		
		if(assegnaCondividiButton == null) {
			assegnaCondividiButton = new DetailToolStripButton("Assegnazioni/invii conosc","archivio/assegna.png");
			assegnaCondividiButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();																		
					if(detail instanceof ProtocollazioneDetail){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);			
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);									
					} 
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof ProtocollazioneDetail) {
											((ProtocollazioneDetail) detail).clickAssegnaCondividi(destinatariPreferiti); 											
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 
									}
								}
							}, new DSRequest());
					
				}
			});
		}
		
		if(frecciaAssegnaCondividiButton == null) {			
			frecciaAssegnaCondividiButton = new FrecciaDetailToolStripButton();
			frecciaAssegnaCondividiButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();						
					if(detail instanceof ProtocollazioneDetail){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);			
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);									
					} 					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof ProtocollazioneDetail) {
											((ProtocollazioneDetail) detail).clickAssegnaCondividi(destinatariPreferiti); 											
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 
									}
								}
							}, new DSRequest());
					
				}
			});
		}
		
		if(invioAlProtocolloButton == null) {
			invioAlProtocolloButton = new DetailToolStripButton("Invio al protocollo","archivio/invio_al_protocollo.png");
			invioAlProtocolloButton.addClickHandler(new ClickHandler()  {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickInvioAlProtocolloMenu(); 											
					}
				}
			});
		}
		
		if(frecciaInvioAlProtocolloButton == null) {
			frecciaInvioAlProtocolloButton = new FrecciaDetailToolStripButton();
			frecciaInvioAlProtocolloButton.addClickHandler(new ClickHandler()  {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickFrecciaInvioAlProtocolloMenu(); 											
					}
				}
			});
		}

		if(frecciaStampaEtichettaButton == null) {			
			frecciaStampaEtichettaButton = new FrecciaDetailToolStripButton();
			frecciaStampaEtichettaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					((ProtocollazioneDetail) detail).clickFrecciaStampaEtichetta();
				}
			});
		}
		
		if(stampaMenuButton == null) {			
			stampaMenuButton = new DetailToolStripButton("Stampe", "stampa.png");
			stampaMenuButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					Menu menu = new Menu();
					MenuItem stampaCopertina = new MenuItem(I18NUtil.getMessages().protocollazione_detail_stampaCopertinaButton_prompt());
					stampaCopertina.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							if(detail instanceof ProtocollazioneDetail) {
								((ProtocollazioneDetail) detail).clickStampaCopertina();
							} else if(detail instanceof ArchivioDetail) {
								Record record = new Record(detail.getValuesManager().getValues());
								String nomeModelloUnicaStampaAbilitata = getNomeModelloUnicaStampaAbilitata(record);
								if(nomeModelloUnicaStampaAbilitata != null) {
									((ArchivioDetail) detail).clickStampa(nomeModelloUnicaStampaAbilitata);
								} else {
									Menu stampaMenu = new Menu();
									if (record.getAttributeAsBoolean("abilStampaCopertina")) {										
										MenuItem stampaCopertinaMenuItem = new MenuItem("Copertina");
										stampaCopertinaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
											
											@Override
											public void onClick(MenuItemClickEvent event) {
												((ArchivioDetail) detail).clickStampa("COPERTINA_FASC");
											}
										});
										stampaMenu.addItem(stampaCopertinaMenuItem);
									}
									if (record.getAttributeAsBoolean("abilStampaSegnatura")) {																	
										MenuItem stampaSegnaturaMenuItem = new MenuItem("Segnatura");
										stampaSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
											
											@Override
											public void onClick(MenuItemClickEvent event) {
												((ArchivioDetail) detail).clickStampa("SEGNATURA_FASC");
											}
										});						
										stampaMenu.addItem(stampaSegnaturaMenuItem);
									}
									if (record.getAttributeAsBoolean("abilStampaListaContenuti")) {																	
										MenuItem stampaListaContenutiMenuItem = new MenuItem("Lista contenuti");
										stampaListaContenutiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
											
											@Override
											public void onClick(MenuItemClickEvent event) {
												((ArchivioDetail) detail).clickStampa("LISTA_CONTENUTI_FASC");
											}
										});						
										stampaMenu.addItem(stampaListaContenutiMenuItem);
									}
									stampaMenu.showContextMenu();	
								}
							}
						}
					});		
					menu.addItem(stampaCopertina);
					
					MenuItem stampaRicevuta = new MenuItem(I18NUtil.getMessages().protocollazione_detail_stampaRicevutaButton_prompt());
					stampaRicevuta.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							((ProtocollazioneDetail) detail).clickStampaRicevuta();
						}
					});		
					menu.addItem(stampaRicevuta);
					
					menu.showContextMenu();
				}
					
					
			});
		}
		
		if(assegnazioneButton == null) {
			assegnazioneButton = new DetailToolStripButton("Assegna","archivio/assegna.png");
			assegnazioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();						
					if(detail instanceof ProtocollazioneDetail){ 
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} 
					else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);	
					} 					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof ProtocollazioneDetail) {
											((ProtocollazioneDetail) detail).clickAssegnazione(destinatariPreferiti); 											
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 
									}
								}
							}, new DSRequest());
					
				}
			});
		}
		
		if(rispondiButton == null) {
			rispondiButton = new DetailToolStripButton("Rispondi","archivio/rispondi.png");
			rispondiButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).manageOnClickRispondi();
//						manageOnClickRispondiProtocollo();
					}					
				}
			});
		}
		
		if(condivisioneButton == null) {
			condivisioneButton = new DetailToolStripButton("Invia per conoscenza","archivio/condividi.png");
			condivisioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();
					if(detail instanceof DocumentDetail){ // che è come fare ((detail instanceof ProtocollazioneDetail) || (detail instanceof RichiestaAccessoAttiDetail)) 
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);	
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} 
					else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);	
					} 
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof DocumentDetail) {
											((DocumentDetail) detail).clickCondivisione(destinatariPreferiti);
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 
									}
								}
							}, new DSRequest());
				}
			});
		}
		
		if(frecciaStampaEtichettaButton == null) {			
			frecciaStampaEtichettaButton = new FrecciaDetailToolStripButton();
			frecciaStampaEtichettaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					((ProtocollazioneDetail) detail).clickFrecciaStampaEtichetta();
				}
			});
		}
		
		if(frecciaAssegnazioneButton == null) {			
			frecciaAssegnazioneButton = new FrecciaDetailToolStripButton();
			frecciaAssegnazioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();						
					if(detail instanceof ProtocollazioneDetail){ 
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue());
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} 
					else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);	
					} 
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof ProtocollazioneDetail) {
											((ProtocollazioneDetail) detail).clickFrecciaAssegnazione(destinatariPreferiti); 											
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 
									}
								}
							}, new DSRequest());
					
				}
			});
		}
		
		if(frecciaCondivisioneButton == null) {			
			frecciaCondivisioneButton = new FrecciaDetailToolStripButton();
			frecciaCondivisioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();						
					if(detail instanceof DocumentDetail){ // // che è come fare ((detail instanceof ProtocollazioneDetail) || (detail instanceof RichiestaAccessoAttiDetail))
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);						
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} 
					else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);	
					} 				
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof DocumentDetail) {
											((DocumentDetail) detail).clickFrecciaCondivisione(destinatariPreferiti);
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 
									}
								}
							}, new DSRequest());
				}
			});
		}
		
		if(stampaButton == null) {
			stampaButton = new DetailToolStripButton("Stampa", "stampa.png");
			stampaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickStampaCopertina();
					} else if(detail instanceof ArchivioDetail) {
						Record record = new Record(detail.getValuesManager().getValues());
						String nomeModelloUnicaStampaAbilitata = getNomeModelloUnicaStampaAbilitata(record);
						if(nomeModelloUnicaStampaAbilitata != null) {
							((ArchivioDetail) detail).clickStampa(nomeModelloUnicaStampaAbilitata);
						} else {
							Menu stampaMenu = new Menu();
							if (record.getAttributeAsBoolean("abilStampaCopertina")) {										
								MenuItem stampaCopertinaMenuItem = new MenuItem("Copertina");
								stampaCopertinaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										((ArchivioDetail) detail).clickStampa("COPERTINA_FASC");
									}
								});
								stampaMenu.addItem(stampaCopertinaMenuItem);
							}
							if (record.getAttributeAsBoolean("abilStampaSegnatura")) {																	
								MenuItem stampaSegnaturaMenuItem = new MenuItem("Segnatura");
								stampaSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										((ArchivioDetail) detail).clickStampa("SEGNATURA_FASC");
									}
								});						
								stampaMenu.addItem(stampaSegnaturaMenuItem);
							}
							if (record.getAttributeAsBoolean("abilStampaListaContenuti")) {																	
								MenuItem stampaListaContenutiMenuItem = new MenuItem("Lista contenuti");
								stampaListaContenutiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										((ArchivioDetail) detail).clickStampa("LISTA_CONTENUTI_FASC");
									}
								});						
								stampaMenu.addItem(stampaListaContenutiMenuItem);
							}
							stampaMenu.showContextMenu();	
						}
					}
				}
			});
		}

		if(nuovaProtComeCopiaButton == null) {
			nuovaProtComeCopiaButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_nuovaProtComeCopiaButton_prompt(),
					"protocollazione/nuovaProtComeCopia.png");
			nuovaProtComeCopiaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickNuovaProtComeCopia(new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								newComeCopiaMode();
							}
						});
					}
				}
			});
		}

		if(presaInCaricoButton == null) {	
			presaInCaricoButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_presaIncarico_title(), 
					"archivio/prendiInCarico.png");
			presaInCaricoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickPresaInCarico();
					}
				}
			});
		}
		
		if(restituisciButton == null) {	
			restituisciButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_restituisci_title(), 
					"archivio/restituzione.png");
			restituisciButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickRestituisci();
					} else if (detail instanceof ArchivioDetail) {
						((ArchivioDetail) detail).clickRestituisci();
					}
				}
			});
		}
		
		if(segnaComeVisionatoButton == null) {	
			segnaComeVisionatoButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_segnaComeVisionato_title(), 
					"postaElettronica/flgRicevutaLettura.png");
			segnaComeVisionatoButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickSegnaComeVisionato();
					} else if (detail instanceof ArchivioDetail) {
						((ArchivioDetail) detail).clickSegnaComeVisionato();
					}
				}
			});
		}
		
		if(classificazioneFascicolazioneButton == null) {	
			classificazioneFascicolazioneButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_fascicolazione_title(), 
					"archivio/fascicola.png");
			classificazioneFascicolazioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickClassificazioneFascicolazione();
					}
				}
			});
		}
		
		if(modificaButton == null) {	
			modificaButton = new DetailToolStripButton("Modifica", "buttons/modify.png");
			modificaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					editMode(true);
					Record record = new Record(detail.getValuesManager().getValues());
					if (detail instanceof ProtocollazioneDetail) {
						if (!(detail instanceof ProtocollazioneDetailBozze)) {
							if (record.getAttributeAsBoolean("abilModificaDati")) {
								((ProtocollazioneDetail) detail).modificaDatiMode();
							} else if (record.getAttributeAsBoolean("abilAggiuntaFile")) {
								((ProtocollazioneDetail) detail).aggiuntaFileMode();
							} else if (record.getAttributeAsBoolean("abilModificaDatiExtraIter")) {
								((ProtocollazioneDetail) detail).modificaDatiExtraIterMode();
							} else {
								viewMode();
							}
						}
					}
				}
			});
		}
		
		if(frecciaModificaButton == null) {			
			frecciaModificaButton = new FrecciaDetailToolStripButton();
			frecciaModificaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					clickFrecciaModifica();
				}
			});
		}
		
		if(regAccessoCivicoButton == null) {	
			regAccessoCivicoButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_regAccessoCivicoButton_title(), "buttons/modify.png");
			regAccessoCivicoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					editMode(true);
					Record record = new Record(detail.getValuesManager().getValues());
					if (detail instanceof ProtocollazioneDetail) {
						if (!(detail instanceof ProtocollazioneDetailBozze)) {
							if (record.getAttributeAsBoolean("abilRegAccessoCivico")) {
								((ProtocollazioneDetail) detail).modificaRegAccessoCivicoMode();
								setSaveButtonTitle("Registra");
							} else {
								viewMode();
							}
						}
					}
				}
			});
		}
		
		if(modificaDatiRegButton == null) {	
			modificaDatiRegButton = new DetailToolStripButton("Var. registrazione", "buttons/modificaDatiReg.png");
			modificaDatiRegButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					editMode(true);
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).modificaDatiRegMode();
					}
				}
			});
		}
		
		if(revocaAttoButton == null) {	
			revocaAttoButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_revoca_atto_button(),
					"buttons/revoca_atto.png");
			revocaAttoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickRevocaAtto(instance);
					}
				}
			});
		}
		
		if(protocollazioneEntrataButton == null) {	
			protocollazioneEntrataButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_protocollazione_entrata_button(),
					"menu/protocollazione_entrata.png");
			protocollazioneEntrataButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickProtocollazioneEntrata(instance);
					}
				}
			});
		}
		
		if(protocollazioneUscitaButton == null) {	
			protocollazioneUscitaButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_protocollazione_uscita_button(),
					"menu/protocollazione_uscita.png");
			protocollazioneUscitaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickProtocollazioneUscita(instance);
					}
				}
			});
		}
		
		if(protocollazioneInternaButton == null) {	
			protocollazioneInternaButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_protocollazione_interna_button(), 
					"menu/protocollazione_interna.png");
			protocollazioneInternaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickProtocollazioneInterna(instance);
					}
				}
			});
		}
		
		if(invioPECButton == null) {	
			invioPECButton = new DetailToolStripButton(I18NUtil.getMessages().invioudmail_detail_mailinterop_title(),
					"anagrafiche/soggetti/flgEmailPecPeo/INTEROP.png");
			invioPECButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					Record record = new Record(detail.getValuesManager().getValues());
					
					final Boolean flgInvioPECMulti = record.getAttributeAsBoolean("flgInvioPECMulti") != null &&
							record.getAttributeAsBoolean("flgInvioPECMulti") ? true : false;					
					
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaInvioUDMailDatasource");
					if(flgInvioPECMulti) {
						lGwtRestService.extraparam.put("PEC_MULTI", "1");
					} 
					lGwtRestService.extraparam.put("tipoMail", "PEC");
					lGwtRestService.call(record, new ServiceCallback<Record>() {
	
						@Override
						public void execute(Record object) {
							
							if(flgInvioPECMulti) {
								object.setAttribute("tipoMail", "PEO");
								InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEO", new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										
										reload(new DSCallback() {
		
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												
												viewMode();
											}
										});
									}
								});
								lInvioUdMailWindow.loadMail(object);
								lInvioUdMailWindow.show();
								
							} else {
								InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEC", new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										
										reload(new DSCallback() {
		
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												
												viewMode();
											}
										});
									}
								});
								lInvioUdMailWindow.loadMail(object);
								lInvioUdMailWindow.show();
							}

						}
					});
				}
			});
		}
		
		if(invioMailRicevutaButton == null) {	
			invioMailRicevutaButton = new DetailToolStripButton(I18NUtil.getMessages().invioudmail_detail_mailricevuta_title(),
					"anagrafiche/soggetti/flgEmailPecPeo/INTEROP.png");
			invioMailRicevutaButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					((ProtocollazioneDetail) detail).getStampaRicevuta(new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object data, DSRequest dsRequest) {
							// TODO Auto-generated method stub
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record result = response.getData()[0];
								final String nomeFilePdf = result.getAttribute("nomeFile");
								final String uriFilePdf = result.getAttribute("uri");
								final InfoFileRecord infoFilePdf = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
								Record record = new Record(detail.getValuesManager().getValues());
								GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaInvioUDMailDatasource");
								lGwtRestService.extraparam.put("tipoMail", "PEC");
								lGwtRestService.executecustom("callInvioRicevuta", record, new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										final Record object = response.getData()[0];
											new NuovoMessaggioWindow("nuovo_messaggio","invioNuovoMessaggio", instance,new DSCallback() {
												
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
												}
											}) 
											{
												@Override
												public Record getInitialRecordNuovoMessaggio() {				
													Record lRecord = new Record();
													
													lRecord.setAttribute("oggetto", object.getAttribute("oggetto"));
													lRecord.setAttribute("bodyHtml", object.getAttribute("bodyHtml"));
													lRecord.setAttribute("destinatari", object.getAttribute("destinatari"));
													lRecord.setAttribute("destinatariCC", object.getAttribute("destinatariCC"));
													lRecord.setAttribute("mittente", object.getAttribute("mittente"));				
													RecordList attachRecordList = new RecordList();
													Record attach = new Record();
													attach.setAttribute("fileNameAttach", nomeFilePdf);
													attach.setAttribute("infoFileAttach", infoFilePdf);
													attach.setAttribute("uriAttach", uriFilePdf);
													attachRecordList.add(attach);
													lRecord.setAttribute("attach", attachRecordList);				
													return lRecord;
												}
												

												@Override
												public String getTitleWindow() {
													return I18NUtil.getMessages().invioudmail_detail_mailricevuta_Windowtitle();
												}
											};
									}
								});
							}
							
						}
					});
				}
			});
		}
		
		if(invioPEOButton == null) {	
			invioPEOButton = new DetailToolStripButton(I18NUtil.getMessages().invioudmail_detail_mailpeo_title(), "anagrafiche/soggetti/flgEmailPecPeo/PEO.png");
			invioPEOButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					Record record = new Record(detail.getValuesManager().getValues());
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaInvioUDMailDatasource");
					lGwtRestService.extraparam.put("tipoMail", "PEO");
					lGwtRestService.call(record, new ServiceCallback<Record>() {
	
						@Override
						public void execute(Record object) {
							InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEO", new DSCallback() {
	
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									
									reload(new DSCallback() {
	
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											
											viewMode();
										}
									});
								}
							});
							lInvioUdMailWindow.loadMail(object);
							lInvioUdMailWindow.show();
						}
					});
				}
			});
		}
		
		if (inviaRaccomandataButton == null) {
			inviaRaccomandataButton = new DetailToolStripButton("Invio raccomandata", "postaElettronica/inoltro.png");
			inviaRaccomandataButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					final Record lRecord = new Record(detail.getValuesManager().getValues());
					ProtocollazioneUtil.isPossibleToPostel(lRecord.getAttributeAsInt("idUd"), ETypePoste.RACCOMANDATA.value(), new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record record) {
							if(record.getAttributeAsBoolean("isDaPostalizzare") && (detail instanceof ProtocollazioneDetail )){
								((ProtocollazioneDetail) detail).setModalitaInvio("raccomandata");
								((ProtocollazioneDetail) detail).clickPostalizza(lRecord);
							}else {
								Layout.addMessage(new MessageBean("Il protocollo risulta già stato inviato tramite raccomandata", "",
										MessageType.ERROR));
							}
						}
					});
				}
			});
		}
		
		if (inviaPostaPrioritariaButton == null) {
			inviaPostaPrioritariaButton = new DetailToolStripButton("Invio posta prioritaria", "postaElettronica/inoltro.png");
			inviaPostaPrioritariaButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					final Record lRecord = new Record(detail.getValuesManager().getValues());
					ProtocollazioneUtil.isPossibleToPostel(lRecord.getAttributeAsInt("idUd"), ETypePoste.POSTA_PRIORITARIA.value(), new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record record) {
							if(record.getAttributeAsBoolean("isDaPostalizzare") && (detail instanceof ProtocollazioneDetail )){
								((ProtocollazioneDetail) detail).setModalitaInvio("posta prioritaria");
								((ProtocollazioneDetail) detail).clickPostalizza(lRecord);
							}else {
								Layout.addMessage(new MessageBean("Il protocollo risulta già stato inviato tramite posta prioritaria", "",
										MessageType.ERROR));
							}
						}
					});
				}
			});
		}
		
		if(verificaRegistrazioneButton == null) {	
			verificaRegistrazioneButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_verificaRegistrazioneButton_prompt(),
					"buttons/verificaDati.png");
			verificaRegistrazioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					Record lInputRecord = new Record();
					lInputRecord.setAttribute("datiReg", new Record(detail.getValuesManager().getValues()));
					final GWTRestDataSource lVerificaRegDuplicataDataSource = new GWTRestDataSource("VerificaRegDuplicataDataSource");
					lVerificaRegDuplicataDataSource.addData(lInputRecord, new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record lOutputRecord = response.getData()[0];
								if ("N".equals(lOutputRecord.getAttribute("statoDuplicazione"))) {
									Layout.addMessage(new MessageBean("Non risultano possibili registrazioni duplicate nel periodo considerato", "",
											MessageType.INFO));
								} else {
									if (lOutputRecord.getAttribute("warningMessage") != null && !"".equals(lOutputRecord.getAttribute("warningMessage"))) {
										Layout.addMessage(new MessageBean(lOutputRecord.getAttribute("warningMessage"), "", MessageType.WARNING));
									}
									GWTRestDataSource lRegistrazioniDuplicataDataSource = new GWTRestDataSource("RegistrazioniDuplicataDataSource", "idUd",
											FieldType.TEXT);
									lRegistrazioniDuplicataDataSource.addParam("datiRegXml", lOutputRecord.getAttribute("datiRegXml"));
									VerificaRegDuplicataWindow lVerificaRegDuplicataWindow = new VerificaRegDuplicataWindow(lRegistrazioniDuplicataDataSource);
									lVerificaRegDuplicataWindow.show();
								}
							}
						}
					});
				}
			});
		}
		
		if(salvaComeModelloButton == null) {	
			salvaComeModelloButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_salvaComeModelloButton_prompt(),
					"buttons/template_save.png");
			salvaComeModelloButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickSalvaComeModello();
					}
				}
			});
		}
		
		if(registraPrelievoButton == null) {	
			registraPrelievoButton = new DetailToolStripButton("Registra prelievo", "richiesteAccessoAtti/azioni/registraPrelievo.png");
			registraPrelievoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof PraticaPregressaDetail) {
						((PraticaPregressaDetail) detail).onClickRegistraPrelievoButton();
					}
				}
			});
		}
		
		if(modificaPrelievoButton == null) {	
			modificaPrelievoButton = new DetailToolStripButton("Modifica prelievo", "richiesteAccessoAtti/azioni/modificaPrelievo.png");
			modificaPrelievoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof PraticaPregressaDetail) {
						((PraticaPregressaDetail) detail).onClickModificaPrelievoButton();
					}
				}
			});
		}
		
		if(eliminaPrelievoButton == null) {	
			eliminaPrelievoButton = new DetailToolStripButton("Elimina prelievo", "richiesteAccessoAtti/azioni/eliminaPrelievo.png");
			eliminaPrelievoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof PraticaPregressaDetail) {
						((PraticaPregressaDetail) detail).onClickEliminaPrelievoButton();
					}
				}
			});
		}
		
		if(registraRestituzionePrelievoButton == null) {	
			registraRestituzionePrelievoButton = new DetailToolStripButton("Registra restituzione prelievo", "richiesteAccessoAtti/azioni/registraRestituzione.png");
			registraRestituzionePrelievoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof PraticaPregressaDetail) {
						((PraticaPregressaDetail) detail).onClickRegistraRestituzionePrelievoButton();
					}
				}
			});
		}
		
		if(detailDownloadDocsZip == null) {	
			detailDownloadDocsZip = new DetailToolStripButton(I18NUtil.getMessages().archivio_list_downloadDocsZip(), "buttons/download_zip.png");
			detailDownloadDocsZip.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					clickDownloadDocZip("scaricaOriginali");
				}

			});
		}
		
		if(frecciaDownloadZipButton == null) {
			frecciaDownloadZipButton = new FrecciaDetailToolStripButton();
			frecciaDownloadZipButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					clickFrecciaDownload();
				}
			});
		}
		
		if(chiudiFascicoloButton == null) {	
			chiudiFascicoloButton = new DetailToolStripButton(I18NUtil.getMessages().archivio_chiudiFascicoloButton_prompt(), "archivio/save_key.png");
			chiudiFascicoloButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					SC.ask("Sei sicuro di voler procedere alla chiusura del fascicolo ?", new BooleanCallback() {
	
						@Override
						public void execute(Boolean value) {
							if (value) {
								final Record recordSelected = new Record(detail.getValuesManager().getValues());
								final RecordList listaUdFolder = new RecordList();
								listaUdFolder.add(recordSelected);
								final Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
								lGwtRestDataSource.executecustom("chiudiFascicolo", record, new DSCallback() {
	
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										singleOperationCallback(response, recordSelected, "idUdFolder", "segnatura", "Operazione effettuata con successo",
												"Il record selezionato e' andato in errore!", null);
										reload(new DSCallback() {
	
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												
												detail.setSaved(true);
												viewMode();
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
		
		if(riapriFascicoloButton == null) {	
			riapriFascicoloButton = new DetailToolStripButton(I18NUtil.getMessages().archivio_riapriFascicoloButton_prompt(), "archivio/annullaArchiviazione.png");
			riapriFascicoloButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					SC.ask("Sei sicuro di voler procedere alla riapertura del fascicolo ?", new BooleanCallback() {
	
						@Override
						public void execute(Boolean value) {
							if (value) {
								final Record recordSelected = new Record(detail.getValuesManager().getValues());
								final RecordList listaUdFolder = new RecordList();
								listaUdFolder.add(recordSelected);
								final Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
								lGwtRestDataSource.executecustom("riapriFascicolo", record, new DSCallback() {
	
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										singleOperationCallback(response, recordSelected, "idUdFolder", "segnatura", "Operazione effettuata con successo",
												"Il record selezionato e' andato in errore!", null);
										reload(new DSCallback() {
	
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												
												detail.setSaved(true);
												viewMode();
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
		
		if(versaInArchivioStoricoFascicoloButton == null) {	
			versaInArchivioStoricoFascicoloButton = new DetailToolStripButton(I18NUtil.getMessages().archivio_versaInArchivioStoricoFascicoloButton_prompt(),
					"archivio/cassaforte.png");
			versaInArchivioStoricoFascicoloButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					SC.ask("Sei sicuro di voler procedere al versamento del fascicolo nell'archivio storico ?", new BooleanCallback() {
	
						@Override
						public void execute(Boolean value) {
							if (value) {
								final Record recordSelected = new Record(detail.getValuesManager().getValues());
								final RecordList listaUdFolder = new RecordList();
								listaUdFolder.add(recordSelected);
								final Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
								lGwtRestDataSource.executecustom("versaInArchivioStoricoFascicolo", record, new DSCallback() {
	
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										singleOperationCallback(response, recordSelected, "idUdFolder", "segnatura", "Operazione effettuata con successo",
												"Il record selezionato e' andato in errore!", null);
										reload(new DSCallback() {
	
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												
												detail.setSaved(true);
												viewMode();
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
		
		if(avviaIterButton == null) {	
			avviaIterButton = new DetailToolStripButton("Avvia iter guidato", "buttons/gear.png");
			avviaIterButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					if (detail instanceof ProtocollazioneDetail) {
						final String idUd = detailRecord.getAttribute("idUd");
						if(detailRecord.getAttribute("flgTipoProv") != null && "E".equalsIgnoreCase(detailRecord.getAttribute("flgTipoProv"))) {
							Record lRecordAvvio = new Record();
							lRecordAvvio.setAttribute("idTipoProc", detailRecord.getAttribute("idProcessTypeIterWFRisposta"));
							lRecordAvvio.setAttribute("nomeTipoProc", detailRecord.getAttribute("nomeProcessTypeIterWFRisposta"));
							lRecordAvvio.setAttribute("idTipoFlussoActiviti", detailRecord.getAttribute("nomeTipoFlussoIterWFRisposta"));
							lRecordAvvio.setAttribute("idTipoDocProposta", detailRecord.getAttribute("idDocTypeIterWFRisposta"));
							lRecordAvvio.setAttribute("nomeTipoDocProposta", detailRecord.getAttribute("nomeDocTypeIterWFRisposta"));
							lRecordAvvio.setAttribute("categoriaProposta", detailRecord.getAttribute("codCategoriaNumIniIterWFRisposta"));
							lRecordAvvio.setAttribute("siglaProposta", detailRecord.getAttribute("siglaNumIniIterWFRisposta"));			
							lRecordAvvio.setAttribute("idUdRichiesta", idUd);	
							AurigaLayout.avviaPratica(lRecordAvvio, new BooleanCallback() {

								@Override
								public void execute(Boolean value) {
									reload(new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {													
											viewMode();
											detail.setSaved(true); // per ricaricare la lista quando chiudo il dettaglio											
										}
									});
								}								
							});	
						} else {
							apriSceltaTipoProcGenPopup("D", detailRecord.getAttribute("tipoDocumento"), new ServiceCallback<Record>() {
		
								@Override
								public void execute(Record record) {
									
									record.setAttribute("idUd", idUd);
									Layout.showWaitPopup("Avvio procedimento in corso...");
									GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AvvioProcedimentoGenericoDatasource");
									lGwtRestService.call(record, new ServiceCallback<Record>() {
		
										@Override
										public void execute(Record object) {
											Layout.hideWaitPopup();
											Layout.addMessage(new MessageBean("Procedimento avviato con successo", "", MessageType.INFO));
											reload(new DSCallback() {
		
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {													
													viewMode();
													detail.setSaved(true); // per ricaricare la lista quando chiudo il dettaglio
													apriDettProcedimentoFromUd(idUd);
												}
											});
										}
									});
								}
							});
						}
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)) {
						final String idFolder = detailRecord.getAttribute("idUdFolder");
						apriSceltaTipoProcGenPopup("F", detailRecord.getAttribute("idFolderType"), new ServiceCallback<Record>() {
	
							@Override
							public void execute(Record record) {
								
								record.setAttribute("idFolder", idFolder);
								Layout.showWaitPopup("Avvio procedimento in corso...");
								GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AvvioProcedimentoGenericoDatasource");
								lGwtRestService.call(record, new ServiceCallback<Record>() {
	
									@Override
									public void execute(Record object) {
										Layout.hideWaitPopup();
										Layout.addMessage(new MessageBean("Procedimento avviato con successo", "", MessageType.INFO));
										reload(new DSCallback() {
	
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {												
												viewMode();
												detail.setSaved(true); // per ricaricare la lista quando chiudo il dettaglio
												apriDettProcedimentoFromFolder(idFolder);
											}
										});
									}
								});
							}
						});
					}
				}
			});
		}
		
		if(osservazioniNotificheButton == null) {	
			osservazioniNotificheButton = new DetailToolStripButton("Notifiche", "osservazioni_notifiche.png");
			osservazioniNotificheButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					String title = null;
					if (detail instanceof ProtocollazioneDetail) {
						title = "Documento "+ getEstremiUdFromLayout(detailRecord) + " - Osservazioni e notifiche";
						new OsservazioniNotificheWindow(detailRecord.getAttribute("idUd"),"U", title);
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)) {
						title = "Fascicolo "+ getEstremiFolderFromLayout(detailRecord) + " - Osservazioni e notifiche";
						new OsservazioniNotificheWindow(detailRecord.getAttribute("idUdFolder"),"F", title);
					}	
				}
			});
		}
		
		if(apposizioneFirmaButton == null) {
			apposizioneFirmaButton = new DetailToolStripButton(I18NUtil.getMessages().apposizioneFirma_button_title(),"file/mini_sign.png");
			apposizioneFirmaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail)detail).manageFirmaComplessiva(true, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								
								/*
								 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
								 * una volta che viene chiuso il dettaglio
								 */
								((ProtocollazioneDetail)detail).setSaved(true);
							}
						});
					}
				}
			});
		}
		
		if(apposizioneFirmaProtocollazioneButton == null) {
			apposizioneFirmaProtocollazioneButton = new DetailToolStripButton(I18NUtil.getMessages().apposizioneFirmaProtocollazione_button_title(),"buttons/firmaEProtocolla.png");
			apposizioneFirmaProtocollazioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail)detail).manageProtocollaTimbraEFirma(new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								
								/*
								 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
								 * una volta che viene chiuso il dettaglio
								 */
								((ProtocollazioneDetail)detail).setSaved(true);
							}
						});
					}
				}
			});
		}
		
		if(rifiutoApposizioneFirmaButton == null) {
			rifiutoApposizioneFirmaButton = new DetailToolStripButton(I18NUtil.getMessages().rifiutoApposizioneFirma_button_title(),"file/rifiuto_apposizione.png");
			rifiutoApposizioneFirmaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail)detail).manageFirmaComplessiva(false, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								
								/*
								 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
								 * una volta che viene chiuso il dettaglio
								 */
								((ProtocollazioneDetail)detail).setSaved(true);
							}
						});
					}
				}
			});
		}
		
		if(apposizioneVistoButton == null) {	
			apposizioneVistoButton = new DetailToolStripButton(I18NUtil.getMessages().apposizioneVisto_button_title(),"ok.png");
			apposizioneVistoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail)detail).manageVistoDocumento(true, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								/*
								 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
								 * una volta che viene chiuso il dettaglio
								 */
								((ProtocollazioneDetail)detail).setSaved(true);
							}
						});
					} 
				}
			});
		} 		

		if(rifiutoApposizioneVistoButton == null) {	
			rifiutoApposizioneVistoButton = new DetailToolStripButton(I18NUtil.getMessages().rifiutoApposizioneVisto_button_title(),"ko.png");
			rifiutoApposizioneVistoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail)detail).manageVistoDocumento(false, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								/*
								 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
								 * una volta che viene chiuso il dettaglio
								 */
								((ProtocollazioneDetail)detail).setSaved(true);
							}
						});
					} 
				}
			});
		} 
		
		if(pubblicazioneTraspAmmButton == null) {	
			pubblicazioneTraspAmmButton = new DetailToolStripButton("Pubbl. Trasp. Amm.", "buttons/richiesta_pubblicazione.png");
			pubblicazioneTraspAmmButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					new PubblicazioneTraspAmmPopup(detailRecord.getAttribute("idUd"));
				}
			});
		}
		
		detailButtons.add(apposizioneFirmaButton);
		detailButtons.add(apposizioneFirmaProtocollazioneButton);
		detailButtons.add(rifiutoApposizioneFirmaButton);
		detailButtons.add(apposizioneVistoButton);
		detailButtons.add(rifiutoApposizioneVistoButton);
		detailButtons.add(stampaEtichettaButton);
		detailButtons.add(frecciaStampaEtichettaButton);
		detailButtons.add(stampaMenuButton);
		detailButtons.add(stampaRicevutaButton);
		detailButtons.add(stampaButton);
		detailButtons.add(invioMailRicevutaButton);
		detailButtons.add(nuovaProtComeCopiaButton);
		detailButtons.add(saveButton);
		detailButtons.add(editButton);
		detailButtons.add(presaInCaricoButton);
		detailButtons.add(restituisciButton);
		detailButtons.add(segnaComeVisionatoButton);
		detailButtons.add(classificazioneFascicolazioneButton);
		detailButtons.add(modificaButton);
		detailButtons.add(frecciaModificaButton);
		detailButtons.add(regAccessoCivicoButton);
		detailButtons.add(modificaDatiRegButton);		
		detailButtons.add(revocaAttoButton);
		detailButtons.add(protocollazioneEntrataButton);
		detailButtons.add(protocollazioneUscitaButton);
		detailButtons.add(protocollazioneInternaButton);
		detailButtons.add(invioPECButton);
		detailButtons.add(invioPEOButton);
		detailButtons.add(inviaRaccomandataButton);
		detailButtons.add(inviaPostaPrioritariaButton);
		detailButtons.add(verificaRegistrazioneButton);
		detailButtons.add(reloadDetailButton);
		detailButtons.add(undoButton);
		detailButtons.add(deleteButton);
		detailButtons.add(smistaButton);
		detailButtons.add(smistaCCButton);
		detailButtons.add(invioAlProtocolloButton);
		detailButtons.add(frecciaInvioAlProtocolloButton);
		detailButtons.add(assegnaCondividiButton);
		detailButtons.add(frecciaAssegnaCondividiButton);
		detailButtons.add(assegnazioneButton);
		detailButtons.add(frecciaAssegnazioneButton);
		detailButtons.add(rispondiButton);
		detailButtons.add(condivisioneButton);
		detailButtons.add(frecciaCondivisioneButton);
		detailButtons.add(osservazioniNotificheButton);
		detailButtons.add(registraPrelievoButton);
		detailButtons.add(modificaPrelievoButton);
		detailButtons.add(eliminaPrelievoButton);
		detailButtons.add(registraRestituzionePrelievoButton);
		detailButtons.add(salvaComeModelloButton);
		detailButtons.add(altreOpButton);
		detailButtons.add(lookupButton);
		detailButtons.add(detailDownloadDocsZip);
		detailButtons.add(frecciaDownloadZipButton);
		detailButtons.add(chiudiFascicoloButton);
		detailButtons.add(riapriFascicoloButton);
		detailButtons.add(versaInArchivioStoricoFascicoloButton);
		detailButtons.add(avviaIterButton);
		detailButtons.add(pubblicazioneTraspAmmButton);
		
		if ((detail instanceof RichiestaAccessoAttiDetail)) {
			detailButtons.addAll(((RichiestaAccessoAttiDetail)detail).getAzioniButtons());
		}

		return detailButtons;
	}

	protected void clickFrecciaDownload() {
		final Menu downloadZipMenu = new Menu();
		MenuItem scaricaFileMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_originali(), "buttons/download_zip.png");
		scaricaFileMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickDownloadDocZip("scaricaOriginali");
			}
		});
		downloadZipMenu.addItem(scaricaFileMenuItem);
		
		final Record detailRecord = new Record(detail.getValuesManager().getValues());
		if(showOperazioniTimbratura(detailRecord)) {
			
			MenuItem scaricaFileTimbratiSegnaturaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_segnatura(), "buttons/download_zip.png");
			scaricaFileTimbratiSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					clickDownloadDocZip("scaricaTimbratiSegnatura");
				}
			});
			downloadZipMenu.addItem(scaricaFileTimbratiSegnaturaMenuItem);
	
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA")) {
				MenuItem scaricaFileTimbratiConformeStampaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_stampa(), "buttons/download_zip.png");
				scaricaFileTimbratiConformeStampaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadDocZip("scaricaTimbratiConformeStampa");
					}
				});
				downloadZipMenu.addItem(scaricaFileTimbratiConformeStampaMenuItem);
	
				MenuItem scaricaFileTimbratiConformeDigitaleMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_digitale(),"buttons/download_zip.png");
				scaricaFileTimbratiConformeDigitaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadDocZip("scaricaTimbratiConformeDigitale");
					}
				});
				downloadZipMenu.addItem(scaricaFileTimbratiConformeDigitaleMenuItem);
			}
	
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
				MenuItem scaricaFileTimbratiConformeCartaceoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_cartaceo(),"buttons/download_zip.png");
				scaricaFileTimbratiConformeCartaceoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadDocZip("scaricaTimbratiConformeCartaceo");
					}
				});
				downloadZipMenu.addItem(scaricaFileTimbratiConformeCartaceoMenuItem);
			}
		
		}
		
		if(Layout.isPrivilegioAttivo("SCC")) {
			String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
			MenuItem scaricaFileConformitaCustomMenuItem = new MenuItem("File " + labelConformitaCustom, "buttons/download_zip.png");
			scaricaFileConformitaCustomMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				@Override
				public void onClick(MenuItemClickEvent event) {
					clickDownloadDocZip("scaricaConformitaCustom");
				}
			});
			downloadZipMenu.addItem(scaricaFileConformitaCustomMenuItem);
		}

		MenuItem scaricaFileSbustatiMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_sbustati(),"buttons/download_zip.png");
		scaricaFileSbustatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickDownloadDocZip("scaricaSbustati");
			}
		});
		downloadZipMenu.addItem(scaricaFileSbustatiMenuItem);

		downloadZipMenu.showContextMenu();
	
	}

	protected void clickDownloadDocZip(String operazione) {
		if(!"scaricaOriginali".equalsIgnoreCase(operazione) && !"scaricaSbustati".equalsIgnoreCase(operazione) && !"scaricaConformitaCustom".equalsIgnoreCase(operazione)) {
			if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaTimbroDocZip")) {
				showOpzioniTimbratura(operazione, null, "protocollo");
			}else {
				String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro");
				String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro");
				String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina");
				
				Record opzioniTimbro = new Record();
				opzioniTimbro.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
				opzioniTimbro.setAttribute("posizioneTimbro", posizioneTimbroPref);
				opzioniTimbro.setAttribute("tipoPagina", tipoPaginaPref);
				
				manageGenerateDocZipProtocolloDataSource(operazione, opzioniTimbro);
				
			}
		}else {
			manageGenerateDocZipProtocolloDataSource(operazione, null);
		}
	}

	

	public void apriSceltaTipoProcGenPopup(final String flgDocFolder, final String tipo, final ServiceCallback<Record> callback) {
		
		GWTRestDataSource idTipoProcDS = new GWTRestDataSource("LoadComboTipoProcGenDataSource", "idTipoProc", FieldType.TEXT, true);
		idTipoProcDS.addParam("flgDocFolder", flgDocFolder);
		idTipoProcDS.addParam("tipo", tipo);
		idTipoProcDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				if (response.getData().length <= 1) {
					Record lRecord = new Record();
					if (response.getData().length == 1) {
						lRecord.setAttribute("idProcessType", response.getData()[0].getAttribute("idTipoProc"));
						lRecord.setAttribute("desProcessType", response.getData()[0].getAttribute("nomeTipoProc"));
						lRecord.setAttribute("flowTypeId", response.getData()[0].getAttribute("flowTypeId"));
					}
					if (callback != null) {
						callback.execute(lRecord);
					}
				} else {
					new SceltaTipoProcGenPopup(flgDocFolder, tipo, callback);
				}
			}
		});
	}
	
	public void getAbilitazioniFrecciaButton() {
		Record record = new Record(detail.getValuesManager().getValues());
		isAbilModificaTipologia = record.getAttributeAsBoolean("abilModificaTipologia"); 
		isAbilModificaDatiExtraIter = record.getAttributeAsBoolean("abilModificaDatiExtraIter");
		isAbilModificaOpereAtto = record.getAttributeAsBoolean("abilModificaOpereAtto");
		isAbilModificaDatiPubblAtto = record.getAttributeAsBoolean("abilModificaDatiPubblAtto");
	}
	
	private void clickFrecciaModifica() {

		getAbilitazioniFrecciaButton();

		Menu menu = new Menu();

		MenuItem standardItem = new MenuItem("Modifica");
		standardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				editMode(true);
				Record record = new Record(detail.getValuesManager().getValues());
				if (detail instanceof ProtocollazioneDetail) {
					if (!(detail instanceof ProtocollazioneDetailBozze)) {
						if (record.getAttributeAsBoolean("abilModificaDati")) {
							((ProtocollazioneDetail) detail).modificaDatiMode();
						} else if (record.getAttributeAsBoolean("abilAggiuntaFile")) {
							((ProtocollazioneDetail) detail).aggiuntaFileMode();
						} else if (record.getAttributeAsBoolean("abilModificaDatiExtraIter")) {
							((ProtocollazioneDetail) detail).modificaDatiExtraIterMode();
						} else {
							viewMode();
						}
					}
				}
			}
		});
		menu.addItem(standardItem);

		if (isAbilModificaTipologia) {
			MenuItem modificaTipologiaMenuItem = new MenuItem("Assegna/modifica tipologia");
			modificaTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					//se il record è un unità documentale
					if (detail instanceof ProtocollazioneDetail || detail instanceof RichiestaAccessoAttiDetail) {

						final Record recordDetail = new Record(detail.getValuesManager().getValues());
						final String tipoDocumento = recordDetail.getAttribute("tipoDocumento");

						new SceltaTipoDocPopup(false, tipoDocumento, null,
								null, null, "|*|FINALITA|*|ASS_TIPOLOGIA", new ServiceCallback<Record>() {

									@Override
									public void execute(Record lRecordTipoDoc) {

										String idTipoDocumento = lRecordTipoDoc.getAttribute("idTipoDocumento");

										//se la tipologia selezionata è diversa da quella attuale
										if (!idTipoDocumento.equals(tipoDocumento) && idTipoDocumento != null) {
											final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(
													"ProtocolloDataSource");
											lGwtRestDataSource.extraparam.put("tipoDocumento", idTipoDocumento);
											lGwtRestDataSource.executecustom("modificaTipologiaUD", recordDetail,
													new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData,
																DSRequest request) {
															singleOperationCallback(response, recordDetail,
																	"idUdFolder", "segnatura",
																	"Operazione effettuata con successo",
																	"Il record selezionato e' andato in errore!", null);
															reload(new DSCallback() {

																@Override
																public void execute(DSResponse response, Object rawData,
																		DSRequest request) {

																	detail.setSaved(true);
																	viewMode();
																}
															});
														}
													});
										}
									}
								});

						//se il record è una folder
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)) {

						final Record recordDetail = new Record(detail.getValuesManager().getValues());
						final String folderType = recordDetail.getAttribute("idFolderType");

						Record record = new Record();
						record.setAttribute("idClassifica", recordDetail.getAttribute("idClassifica"));
						record.setAttribute("idFolderApp", recordDetail.getAttribute("idFolderApp"));
						record.setAttribute("idFolderType", recordDetail.getAttribute("idFolderType"));

						SceltaTipoFolderPopup lSceltaTipoFolderPopup = new SceltaTipoFolderPopup(false, folderType,
								null, record, new ServiceCallback<Record>() {

									@Override
									public void execute(Record lRecordTipoDoc) {

										String idFolderType = lRecordTipoDoc.getAttribute("idFolderType");

										//se la tipologia selezionata è diversa da quella attuale
										if (!folderType.equals(idFolderType) && idFolderType != null) {
											final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(
													"ArchivioDatasource");
											lGwtRestDataSource.extraparam.put("idFolderType", idFolderType);
											lGwtRestDataSource.executecustom("modificaTipologia", recordDetail,
													new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData,
																DSRequest request) {
															singleOperationCallback(response, recordDetail,
																	"idUdFolder", "segnatura",
																	"Operazione effettuata con successo",
																	"Il record selezionato e' andato in errore!", null);
															reload(new DSCallback() {

																@Override
																public void execute(DSResponse response, Object rawData,
																		DSRequest request) {

																	detail.setSaved(true);
																	viewMode();
																}
															});
														}
													});
										}
									}
								});
					}
				}

			});
			menu.addItem(modificaTipologiaMenuItem);
		}

		if (isAbilModificaDatiExtraIter) {
			MenuItem modificaDatiExtraIterMenuItem = new MenuItem(I18NUtil.getMessages().modificaDatiExtraIter_menu_apri_title(), "pratiche/task/buttons/modifica_dati_extra_iter.png");
			modificaDatiExtraIterMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					editMode(true);
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).modificaDatiExtraIterMode();						
					}
				}

			});
			menu.addItem(modificaDatiExtraIterMenuItem);
		}
		
		if (isAbilModificaOpereAtto) {
			MenuItem modificaOpereMenuItem = new MenuItem(I18NUtil.getMessages().modificaOpereAtto_menu_apri_title(), "pratiche/task/buttons/modifica_opere_atto.png");
			modificaOpereMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final Record recordDetail = new Record(detail.getValuesManager().getValues());
					final String idUd      = recordDetail.getAttributeAsString("idUd");
					final String idProcess = recordDetail.getAttributeAsString("idProcess");
					final String activityName = "#AGG_EXTRA_ITER";
					Record lRecord = new Record();
					lRecord.setAttribute("idUd", idUd);
					lRecord.setAttribute("idProcess", idProcess);
					lRecord.setAttribute("activityName", activityName);
					String title = I18NUtil.getMessages().modificaOpereAtto_Popup_Ud_title(recordDetail.getAttribute("segnatura"));
					new DettaglioOpereAttoPopup(lRecord, title);
				}

			});
			menu.addItem(modificaOpereMenuItem);
		}
			
		if (isAbilModificaDatiPubblAtto) {
			MenuItem modificaDatiPubblAttoMenuItem = new MenuItem(I18NUtil.getMessages().modificaDatiPubblAtto_menu_apri_title(), "pratiche/task/buttons/modifica_dati_pubbl_atto.png");
			modificaDatiPubblAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final Record recordDetail = new Record(detail.getValuesManager().getValues());
					final String idUd      = recordDetail.getAttributeAsString("idUd");
					final String idProcess = recordDetail.getAttributeAsString("idProcess");
					final String activityName = "#AGG_EXTRA_ITER";
					Record lRecord = new Record();
					lRecord.setAttribute("idUd", idUd);
					lRecord.setAttribute("idProcess", idProcess);
					lRecord.setAttribute("activityName", activityName);
					String title = I18NUtil.getMessages().modificaDatiPubblAtto_Popup_Ud_title(recordDetail.getAttribute("segnatura"));
					new DettaglioDatiPubblAttoPopup(lRecord, title);
				}

			});
			menu.addItem(modificaDatiPubblAttoMenuItem);
		}
			
		menu.showContextMenu();
	}
	

	public void apriDettProcedimentoFromUd(String idUd) {
		Record lRecordDetail = new Record();
		lRecordDetail.setAttribute("idUd", idUd);
		final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lProtocolloDataSource.getData(lRecordDetail, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordDoc = response.getData()[0];
					AurigaLayout.apriDettaglioPratica(lRecordDoc.getAttribute("idProcess"), lRecordDoc.getAttribute("estremiProcess"));
				}
			}
		});
	}

	public void apriDettProcedimentoFromFolder(String idFolder) {
		Record lRecordDetail = new Record();
		lRecordDetail.setAttribute("idUdFolder", idFolder);
		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lArchivioDatasource.getData(lRecordDetail, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordFolder = response.getData()[0];
					AurigaLayout.apriDettaglioPratica(lRecordFolder.getAttribute("idProcess"), lRecordFolder.getAttribute("estremiProcess"));
				}
			}
		});
	}

	public void scaricaFile(String display, String uri, String remoteUri, Record infoFile) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri);
		lRecord.setAttribute("infoFile", infoFile);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	@Override
	public String getNewDetailTitle() {
		if (detail instanceof ArchivioDetail) {
			Record record = new Record(detail.getValuesManager().getValues());
			String nroFascicolo = "";
			
			if(record.getAttributeAsString("nroFascicolo")!=null)
				nroFascicolo =record.getAttributeAsString("nroFascicolo");
					
			String nroSottofascicolo = "";
			if (record.getAttribute("nroSottofascicolo")!=null)
				nroSottofascicolo = record.getAttribute("nroSottofascicolo");
			
			if (nroFascicolo != null && !"".equals(nroFascicolo)) {				
				if(nroSottofascicolo != null && !"".equals(nroSottofascicolo)) {
					return "Nuovo inserto";
				}else{
					return "Nuovo sotto-fascicolo";
				}
			} else {
				return "Nuovo fascicolo";
			}
		} else if (detail instanceof FolderCustomDetail) {
			return "Nuova cartella";
		} else if (detail instanceof ProtocollazioneDetail) {
			Record record = new Record(detail.getValuesManager().getValues());
			String tipoDocumento = record.getAttributeAsString("tipoDocumento");
			if (tipoDocumento != null && !"".equals(tipoDocumento)) {
				return "Nuovo documento";
			} else {
				return "Nuovo documento bozza";
			}
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			return "Nuova richiesta accesso atti";
		}
		return null;
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		if (detail instanceof ArchivioDetail) {
			String nroSottofascicolo = record.getAttributeAsString("nroSottofascicolo");
			String nroInserto = record.getAttributeAsString("nroInserto");
			if (nroInserto != null && !"".equals(nroInserto)) {
				return I18NUtil.getMessages().editDetail_titlePrefix() + " inserto " + getTipoEstremiRecord(record);
			}else if (nroSottofascicolo != null && !"".equals(nroSottofascicolo)) {
				return I18NUtil.getMessages().editDetail_titlePrefix() + " sotto-fascicolo " + getTipoEstremiRecord(record);
			} else {
				return I18NUtil.getMessages().editDetail_titlePrefix() + " fascicolo " + getTipoEstremiRecord(record);
			}
		} else if (detail instanceof FolderCustomDetail) {
			return I18NUtil.getMessages().editDetail_titlePrefix() + " cartella " + getTipoEstremiRecord(record);
		} else if (detail instanceof ProtocollazioneDetail) {
			if (detail instanceof ProtocollazioneDetailAtti) {
				String nomeTipoDocumento = record.getAttributeAsString("nomeTipoDocumento");
				return I18NUtil.getMessages().editDetail_titlePrefix() + " documento " + nomeTipoDocumento + " " + getTipoEstremiRecord(record);
			} else {
				if(((ProtocollazioneDetail)detail).isProtocollazioneBozza()) {
					if(((ProtocollazioneDetail)detail).getFlgTipoProv() != null) {
						if("U".equals(((ProtocollazioneDetail)detail).getFlgTipoProv())) {
							return "Protocollazione in uscita documento " + getTipoEstremiRecord(record);
						} else if("I".equals(((ProtocollazioneDetail)detail).getFlgTipoProv())) {
							return "Protocollazione interna documento " + getTipoEstremiRecord(record);
						}
					}
					return "Registrazione documento " + getTipoEstremiRecord(record);
				} else {
					return I18NUtil.getMessages().editDetail_titlePrefix() + " documento " + getTipoEstremiRecord(record);
				}
			}			
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			return I18NUtil.getMessages().editDetail_titlePrefix() + " richiesta accesso atti " + getTipoEstremiRecord(record);
		}
		return null;
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		if (detail instanceof ArchivioDetail) {
			String nroSottofascicolo = record.getAttributeAsString("nroSottofascicolo");
			String nroInserto = record.getAttributeAsString("nroInserto");
			if (nroInserto != null && !"".equals(nroInserto)) {
				return I18NUtil.getMessages().editDetail_titlePrefix() + " inserto " + getTipoEstremiRecord(record);
			}else if (nroSottofascicolo != null && !"".equals(nroSottofascicolo)) {
				return I18NUtil.getMessages().viewDetail_titlePrefix() + " sotto-fascicolo " + getTipoEstremiRecord(record);
			} else {
				return I18NUtil.getMessages().viewDetail_titlePrefix() + " fascicolo " + getTipoEstremiRecord(record);
			}
		} else if (detail instanceof FolderCustomDetail) {
			return I18NUtil.getMessages().viewDetail_titlePrefix() + " cartella " + getTipoEstremiRecord(record);
		} else if (detail instanceof ProtocollazioneDetail) {
			if (detail instanceof ProtocollazioneDetailAtti) {
				String nomeTipoDocumento = record.getAttributeAsString("nomeTipoDocumento");
				return I18NUtil.getMessages().viewDetail_titlePrefix() + " documento " + nomeTipoDocumento + " " + getTipoEstremiRecord(record);
			} else
				return I18NUtil.getMessages().viewDetail_titlePrefix() + " documento " + getTipoEstremiRecord(record);
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			return I18NUtil.getMessages().viewDetail_titlePrefix() + " richiesta accesso atti " + getTipoEstremiRecord(record); 
		}
		return null;
	}

	@Override
	public String getTipoEstremiRecord(Record record) {
		if (detail instanceof ArchivioDetail) {
			String estremi = "";
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
				if (record.getAttributeAsString("nroInserto") != null && !"".equals(record.getAttributeAsString("nroInserto"))) {
					estremi += "/" + record.getAttributeAsString("nroInserto");
				}
				estremi += " ";
			}
			if (record.getAttributeAsString("nome") != null && !"".equals(record.getAttributeAsString("nome"))) {
				estremi += record.getAttributeAsString("nome");
			}
			return estremi;
		} else if (detail instanceof FolderCustomDetail) {
			String estremi = "";
			if (record.getAttributeAsString("percorsoFolderApp") != null && !"".equals(record.getAttributeAsString("percorsoFolderApp"))) {
				estremi += record.getAttributeAsString("percorsoFolderApp") + "/";
			}
			if (record.getAttributeAsString("nome") != null && !"".equals(record.getAttributeAsString("nome"))) {
				estremi += record.getAttributeAsString("nome");
			}
			return estremi;
		} else if (detail instanceof ProtocollazioneDetail) {
			return getTipoEstremiRecordProtocollazione(record);
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			String subProtocolloPregressoPrefix = record.getAttribute("subProtocolloPregresso") != null && !"".equals(record.getAttribute("subProtocolloPregresso")) ? " sub " + record.getAttribute("subProtocolloPregresso") : "";
			if ((record.getAttribute("tipoRichiedente") == null) || (!"I".equalsIgnoreCase(record.getAttribute("tipoRichiedente")))){
				return "Prot. N° " + record.getAttribute("nroProtocolloPregresso") + subProtocolloPregressoPrefix + " anno " +  record.getAttribute("annoProtocolloPregresso");
			}else {
				return "Rich. N° " + record.getAttribute("nroProtocolloPregresso") + subProtocolloPregressoPrefix + " anno " +  record.getAttribute("annoProtocolloPregresso");
			}
		}
		return null;
	}

	protected String getTipoEstremiRecordProtocollazione(Record record) {
		String estremi = "";
		if (detail instanceof ProtocollazioneDetailAtti) {
			if (record.getAttributeAsString("siglaProtocollo") != null && !"".equals(record.getAttributeAsString("siglaProtocollo"))) {
				estremi += record.getAttributeAsString("siglaProtocollo") + " ";
			}
			if (record.getAttributeAsString("nroProtocollo") != null && !"".equals(record.getAttributeAsString("nroProtocollo"))) {
				estremi += record.getAttributeAsString("nroProtocollo") + " ";
			}
			if (record.getAttributeAsString("subProtocollo") != null && !"".equals(record.getAttributeAsString("subProtocollo"))) {
				estremi += "sub " + record.getAttributeAsString("subProtocollo") + " ";
			}
			if (record.getAttributeAsDate("dataProtocollo") != null) {
				estremi += "del " + DateUtil.format((Date) record.getAttributeAsDate("dataProtocollo"));
			}
		} else {
			if (record.getAttributeAsString("tipoProtocollo") != null && !"".equals(record.getAttributeAsString("tipoProtocollo"))) {
				if ("NI".equals(record.getAttributeAsString("tipoProtocollo"))) {
					estremi += "bozza ";
				} else if ("PP".equals(record.getAttributeAsString("tipoProtocollo"))) {
					estremi += "Prot. ";
				} else {
					estremi += record.getAttributeAsString("tipoProtocollo") + " ";
				}
			}
			if (record.getAttributeAsString("siglaProtocollo") != null && !"".equals(record.getAttributeAsString("siglaProtocollo"))) {
				estremi += record.getAttributeAsString("siglaProtocollo") + " ";
			}
			if (record.getAttributeAsString("nroProtocollo") != null && !"".equals(record.getAttributeAsString("nroProtocollo"))) {
				estremi += record.getAttributeAsString("nroProtocollo") + " ";
			}
			if (record.getAttributeAsString("subProtocollo") != null && !"".equals(record.getAttributeAsString("subProtocollo"))) {
				estremi += "sub " + record.getAttributeAsString("subProtocollo") + " ";
			}
			if (record.getAttributeAsDate("dataProtocollo") != null) {
				estremi += "del " + DateUtil.format((Date) record.getAttributeAsDate("dataProtocollo"));
			}
		}
		return estremi;
	}

	public void onSaveButtonClick() {
		if (detail instanceof ProtocollazioneDetail) {
			((ProtocollazioneDetail) detail).clickSalvaRegistra();
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			((RichiestaAccessoAttiDetail) detail).onSaveButtonClick();
		} else if (detail instanceof PraticaPregressaDetail) {
			((PraticaPregressaDetail) detail).onSaveButtonClick();
		} else {
			super.onSaveButtonClick();
		}
	}

	@Override
	public String getDetailPrimaryKeyFieldName() {
		if (detail instanceof ProtocollazioneDetail) {
			return "idUd";
		} else {
			return super.getDetailPrimaryKeyFieldName();
		}
	}

	@Override
	protected Record createMultiLookupRecord(Record record) {
		
		final Record newRecord = new Record();
		if (record.getAttribute("idUdFolder") != null && !"".equals(record.getAttributeAsString("idUdFolder"))) {
			String flgUdFolder = record.getAttributeAsString("flgUdFolder");
			newRecord.setAttribute("id", record.getAttributeAsString("idUdFolder"));
			if ("F".equals(flgUdFolder)) {
				String nroFascicolo = record.getAttributeAsString("nroFascicolo");
				String nroSottofascicolo = record.getAttributeAsString("nroSottofascicolo");
				String nroInserto = record.getAttributeAsString("nroInserto");
				if (nroInserto != null && !"".equals(nroInserto)) {
					newRecord.setAttribute("icona", nomeEntita + "/flgUdFolder/inserto.png");
				} else if (nroSottofascicolo != null && !"".equals(nroSottofascicolo)) {
					newRecord.setAttribute("icona", nomeEntita + "/flgUdFolder/sottofascicolo.png");
				} else if (nroFascicolo != null && !"".equals(nroFascicolo)) {
					newRecord.setAttribute("icona", nomeEntita + "/flgUdFolder/fascicolo.png");
				} else {
					newRecord.setAttribute("icona", nomeEntita + "/flgUdFolder/F.png");
				}
			} else {
				newRecord.setAttribute("icona", nomeEntita + "/flgUdFolder/U.png");
			}
			String segnatura = record.getAttributeAsString("segnatura");
			String nome = record.getAttributeAsString("nome");
			newRecord.setAttribute("nome", segnatura != null && !"".equals(segnatura) ? segnatura + " - " + nome : nome);

		} else if (record.getAttribute("idFolder") != null && !"".equals(record.getAttributeAsString("idFolder"))) {
			newRecord.setAttribute("id", record.getAttributeAsString("idFolder"));
			String nroFascicolo = record.getAttributeAsString("nroFascicolo");
			String nroSottofascicolo = record.getAttributeAsString("nroSottofascicolo");
			String nroInserto = record.getAttributeAsString("nroInserto");
			if (nroInserto != null && !"".equals(nroInserto)) {
				newRecord.setAttribute("icona", nomeEntita + "/flgUdFolder/inserto.png");
			} else if (nroSottofascicolo != null && !"".equals(nroSottofascicolo)) {
				newRecord.setAttribute("icona", nomeEntita + "/flgUdFolder/sottofascicolo.png");
			} else if (nroFascicolo != null && !"".equals(nroFascicolo)) {
				newRecord.setAttribute("icona", nomeEntita + "/flgUdFolder/fascicolo.png");
			} else {
				newRecord.setAttribute("icona", nomeEntita + "/flgUdFolder/F.png");
			}
			newRecord.setAttribute("nome", record.getAttributeAsString("nome"));
		}
		return newRecord;
	}

	@Override
	protected ToolStripButton[] getCustomNewButtons() {
		
		newUdButton = new ToolStripButton();
		newUdButton.setIcon("archivio/newUd.png");
		newUdButton.setIconSize(16);
		newUdButton.setPrompt(I18NUtil.getMessages().archivio_list_newUdButton_prompt());
		newUdButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				list.deselectAllRecords();
				if(AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaBozza")) {
					recuperaDatiClassificaFascicolo(new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							
							String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoBozza");
							String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoBozza");
							Boolean flgTipoDocConVie = AurigaLayout.getImpostazioniDocumentoAsBoolean("flgTipoDocConVieBozza");
							Boolean flgOggettoNonObblig = AurigaLayout.getImpostazioniDocumentoAsBoolean("flgOggettoNonObbligBozza");													
							Map<String, Object> initialValues = object.toMap();
							if (idTipoDoc != null && !"".equals(idTipoDoc)) {
								initialValues.put("tipoDocumento", idTipoDoc);
								initialValues.put("nomeTipoDocumento", nomeTipoDoc);		
								initialValues.put("flgTipoDocConVie", flgTipoDocConVie);				
								initialValues.put("flgOggettoNonObblig", flgOggettoNonObblig);
							}
							ProtocollazioneDetailBozze protocollazioneDetailBozze = ProtocollazioneUtil.buildProtocollazioneDetailBozze(null, initialValues, null);
							protocollazioneDetailBozze.nuovoDettaglio(instance, initialValues);
							if (AurigaLayout.getIsAttivaAccessibilita()) {
								protocollazioneDetailBozze.setTabIndex(-1);
								protocollazioneDetailBozze.setCanFocus(false);
								ProtocollazioneWindow window = new ProtocollazioneWindow(protocollazioneDetailBozze, "menu/archivio.png", protocollazioneDetailBozze.getNomeEntita(),
										getNewDetailTitle());
								window.show();
							} else {
								changeDetail(new GWTRestDataSource("ProtocolloDataSource"), protocollazioneDetailBozze);
								newMode();
							}
						}
					});								
				} else {
					String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoBozza");
//					String descTipoDocDefault = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoBozza");				
					AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, null, null, new ServiceCallback<Record>() {
		
						@Override
						public void execute(final Record lRecordTipoDoc) {
		
							recuperaDatiClassificaFascicolo(new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									
									String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
									String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
									Boolean flgTipoDocConVie = lRecordTipoDoc != null ? lRecordTipoDoc.getAttributeAsBoolean("flgTipoDocConVie") : null;
									Boolean flgOggettoNonObblig = lRecordTipoDoc != null ? lRecordTipoDoc.getAttributeAsBoolean("flgOggettoNonObblig") : null;																				
									Map<String, Object> initialValues = object.toMap();
									if (idTipoDoc != null && !"".equals(idTipoDoc)) {
										initialValues.put("tipoDocumento", idTipoDoc);
										initialValues.put("nomeTipoDocumento", nomeTipoDoc);	
										initialValues.put("flgTipoDocConVie", flgTipoDocConVie);				
										initialValues.put("flgOggettoNonObblig", flgOggettoNonObblig);
									}
									ProtocollazioneDetailBozze protocollazioneDetailBozze = ProtocollazioneUtil.buildProtocollazioneDetailBozze(null, initialValues, null);
									protocollazioneDetailBozze.nuovoDettaglio(instance, initialValues);
									changeDetail(new GWTRestDataSource("ProtocolloDataSource"), protocollazioneDetailBozze);
									if (AurigaLayout.getIsAttivaAccessibilita()) {
										protocollazioneDetailBozze.setTabIndex(-1);
										protocollazioneDetailBozze.setCanFocus(false);
										ProtocollazioneWindow window = new ProtocollazioneWindow(protocollazioneDetailBozze, "menu/archivio.png", protocollazioneDetailBozze.getNomeEntita(),
												getNewDetailTitle());
										window.show();
									} else {
										newMode();
									}
								}
							});							
						}
					});
				}						
			}
		});

		newFolderButton = new ToolStripButton();
		newFolderButton.setIcon("archivio/newFolder.png");
		newFolderButton.setIconSize(16);
		newFolderButton.setPrompt(I18NUtil.getMessages().archivio_list_newFolderButton_prompt());
		newFolderButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				list.deselectAllRecords();
				changeDetail((GWTRestDataSource) list.getDataSource(), new ArchivioDetail("archivio"));
				Record livelloCorrente = navigator.getPercorso().get(navigator.getPercorso().getLength() - 1);
				Record record = new Record();
				record.setAttribute("idUdFolder", livelloCorrente.getAttributeAsString("idFolder"));
				GWTRestDataSource lGwtRestDataSourceArchivio = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
				lGwtRestDataSourceArchivio.addParam("flgFascInCreazione", "1");
				lGwtRestDataSourceArchivio.getData(record, new DSCallback() {

					@Override
					public void execute(final DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							final Record lRecord = response.getData()[0];
							Scheduler.get().scheduleDeferred(new ScheduledCommand() {

								@Override
								public void execute() {
									if(AurigaLayout.getImpostazioniFascicoloAsBoolean("skipSceltaTipologiaFascicolo")) {
										String idFolderType = AurigaLayout.getImpostazioniFascicolo("idFolderTypeFascicolo");
										String descFolderType = AurigaLayout.getImpostazioniFascicolo("descFolderTypeFascicolo");
										String templateNomeFolder = AurigaLayout.getImpostazioniFascicolo("templateNomeFolderFascicolo");
										Map<String, Object> initialValues = lRecord.toMap();
										if (idFolderType != null && !"".equals(idFolderType)) {
											initialValues.put("idFolderType", idFolderType);
											initialValues.put("descFolderType", descFolderType);
											initialValues.put("templateNomeFolder", templateNomeFolder);																						
											initialValues.put("flgUdFolder", "F");
											initialValues.put("flgFascTitolario", true);
										}
										detail.editNewRecord(initialValues);
										setIdNodeToOpen(getNavigator().getCurrentNode().getIdNode());
										newMode();	
									} else {
										String idFolderTypeDefault = AurigaLayout.getImpostazioniFascicolo("idFolderTypeFascicolo");	
										lRecord.setAttribute("flgUdFolder", "F");
										lRecord.setAttribute("flgFascTitolario", true);
										AurigaLayout.apriSceltaTipoFolderPopup(false, idFolderTypeDefault, lRecord, new ServiceCallback<Record>() {

											@Override
											public void execute(Record lRecordTipoFolder) {
												
												String idFolderType = lRecordTipoFolder != null ? lRecordTipoFolder.getAttribute("idFolderType") : null;
												String descFolderType = lRecordTipoFolder != null ? lRecordTipoFolder.getAttribute("descFolderType") : null;
												String templateNomeFolder = lRecordTipoFolder != null ? lRecordTipoFolder.getAttribute("templateNomeFolder") : null;
												Map<String, Object> initialValues = lRecordTipoFolder.toMap();
												if (idFolderType != null && !"".equals(idFolderType)) {
													initialValues.put("idFolderType", idFolderType);
													initialValues.put("descFolderType", descFolderType);
													initialValues.put("templateNomeFolder", templateNomeFolder);
												}
												detail.editNewRecord(initialValues);
												setIdNodeToOpen(getNavigator().getCurrentNode().getIdNode());
												newMode();
											}
										});	
									}									
								}
							});
						}
					}
				});
			}
		});

		newFolderCustomButton = new ToolStripButton();
		newFolderCustomButton.setIcon("archivio/newFolder.png");
		newFolderCustomButton.setIconSize(16);
		newFolderCustomButton.setPrompt(I18NUtil.getMessages().archivio_list_newFolderCustomButton_prompt());
		newFolderCustomButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				list.deselectAllRecords();
				changeDetail((GWTRestDataSource) list.getDataSource(), new FolderCustomDetail("archivio"));
				Record livelloCorrente = navigator.getPercorso().get(navigator.getPercorso().getLength() - 1);
				Record record = new Record();
				record.setAttribute("idUdFolder", livelloCorrente.getAttributeAsString("idFolder"));
				GWTRestDataSource lGwtRestDataSourceArchivio = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
				lGwtRestDataSourceArchivio.addParam("flgFascInCreazione", "1");
				lGwtRestDataSourceArchivio.getData(record, new DSCallback() {

					@Override
					public void execute(final DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							final Record lRecord = response.getData()[0];
							Scheduler.get().scheduleDeferred(new ScheduledCommand() {

								@Override
								public void execute() {									
									if(AurigaLayout.getImpostazioniFascicoloAsBoolean("skipSceltaTipologiaCartella")) {
										String idFolderType = AurigaLayout.getImpostazioniFascicolo("idFolderTypeCartella");
										String descFolderType = AurigaLayout.getImpostazioniFascicolo("descFolderTypeCartella");
										String templateNomeFolder = AurigaLayout.getImpostazioniFascicolo("templateNomeFolderCartella");
										Map<String, Object> initialValues = lRecord.toMap();
										if (idFolderType != null && !"".equals(idFolderType)) {
											initialValues.put("idFolderType", idFolderType);
											initialValues.put("descFolderType", descFolderType);
											initialValues.put("templateNomeFolder", templateNomeFolder);
											initialValues.put("flgUdFolder", "F");
											initialValues.put("flgFascTitolario", false);
										}
										detail.editNewRecord(initialValues);
										setIdNodeToOpen(getNavigator().getCurrentNode().getIdNode());
										newMode();	
										if (AurigaLayout.getIsAttivaAccessibilita()) {
											GenericWindow window = new GenericWindow(detail, detail.getNomeEntita(), getNewDetailTitle(), "menu/archivio.png");
											window.show();
										} 
									} else {
										String idFolderTypeDefault = AurigaLayout.getImpostazioniFascicolo("idFolderTypeCartella");	
										lRecord.setAttribute("flgUdFolder", "F");
										lRecord.setAttribute("flgFascTitolario", false);
										AurigaLayout.apriSceltaTipoFolderPopup(false, idFolderTypeDefault, lRecord, new ServiceCallback<Record>() {

											@Override
											public void execute(Record lRecordTipoFolder) {
												
												String idFolderType = lRecordTipoFolder != null ? lRecordTipoFolder.getAttribute("idFolderType") : null;
												String descFolderType = lRecordTipoFolder != null ? lRecordTipoFolder.getAttribute("descFolderType") : null;
												String templateNomeFolder = lRecordTipoFolder != null ? lRecordTipoFolder.getAttribute("templateNomeFolder") : null;
												Map<String, Object> initialValues = lRecordTipoFolder.toMap();
												if (idFolderType != null && !"".equals(idFolderType)) {
													initialValues.put("idFolderType", idFolderType);
													initialValues.put("descFolderType", descFolderType);
													initialValues.put("templateNomeFolder", templateNomeFolder);
												}
												detail.editNewRecord(initialValues);
												setIdNodeToOpen(getNavigator().getCurrentNode().getIdNode());
												newMode();	
												if (AurigaLayout.getIsAttivaAccessibilita()) {
													GenericWindow window = new GenericWindow(detail, detail.getNomeEntita(), getNewDetailTitle(), "menu/archivio.png");
													window.show();
												} 
											}
										});	
									}
								}
							});
						}
					}
				});
			}
		});

		ToolStripButton[] customNewButtons = { newUdButton, newFolderButton, newFolderCustomButton };
		return customNewButtons;
	}
	
	public void recuperaDatiClassificaFascicolo(final ServiceCallback<Record> callback) {
		Record livelloCorrente = navigator.getPercorso().get(navigator.getPercorso().getLength() - 1);
		Record record = new Record();
		record.setAttribute("idUdFolder", livelloCorrente.getAttributeAsString("idFolder"));
		GWTRestDataSource lGwtRestDataSourceArchivio = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lGwtRestDataSourceArchivio.getData(record, new DSCallback() {

			@Override
			public void execute(final DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record lRecord = response.getData()[0];
					Record lNewRecord = new Record();
					if (lRecord.getAttributeAsBoolean("flgFascTitolario")) {
						RecordList listaClassFasc = new RecordList();
						Record classFasc = new Record();
						classFasc.setAttribute("indice", lRecord.getAttribute("indiceClassifica"));
						classFasc.setAttribute("nomeFascicolo", lRecord.getAttribute("nomeFascicolo"));
						classFasc.setAttribute("annoFascicolo", lRecord.getAttribute("annoFascicolo"));
						classFasc.setAttribute("nroFascicolo", lRecord.getAttribute("nroFascicolo"));
						classFasc.setAttribute("nroSottofascicolo", lRecord.getAttribute("nroSottofascicolo"));
						classFasc.setAttribute("nroInserto", lRecord.getAttribute("nroInserto"));
						classFasc.setAttribute("idClassifica", lRecord.getAttribute("idClassifica"));
						classFasc.setAttribute("idFolderFascicolo", lRecord.getAttribute("idUdFolder"));
						classFasc.setAttribute("classifiche", lRecord.getAttribute("idClassifica"));
						classFasc.setAttribute("descrizioneClassifica", lRecord.getAttribute("descClassifica"));
						listaClassFasc.add(classFasc);
						lNewRecord.setAttribute("listaClassFasc", listaClassFasc);
					} else {
						RecordList listaFolderCustom = new RecordList();
						Record folderCustom = new Record();
						folderCustom.setAttribute("id", lRecord.getAttribute("idUdFolder"));
						folderCustom.setAttribute("path", lRecord.getAttribute("percorsoFolderApp") + "/" + lRecord.getAttribute("nome"));
						listaFolderCustom.add(folderCustom);
						lNewRecord.setAttribute("listaFolderCustom", listaFolderCustom);
					}									
					if(callback != null) {
						callback.execute(lNewRecord);
					}
				}
			}
		});
	}

	public void setMultiselect(Boolean multiselect) {
		super.setMultiselect(multiselect);
		newButton.hide();
		Record livelloCorrente = navigator.getPercorso().get(navigator.getPercorso().getLength() - 1);
		Map altriAttributi = livelloCorrente != null ? livelloCorrente.getAttributeAsMap("altriAttributi") : null;
		if (altriAttributi != null && "1".equals(altriAttributi.get("showNewUdButton"))
				&& (getFinalita() == null || (!"FASCICOLA_UD".equals(getFinalita()) && !"FASCICOLA".equals(getFinalita())))) {
			newUdButton.show();
		} else {
			newUdButton.hide();
		}
		if (altriAttributi != null && "1".equals(altriAttributi.get("showNewFolderButton"))) {
			newFolderButton.show();
		} else {
			newFolderButton.hide();
		}
		if (altriAttributi != null && "1".equals(altriAttributi.get("showNewFolderCustomButton"))) {
			newFolderCustomButton.show();
		} else {
			newFolderCustomButton.hide();
		}
	}

	@Override
	public void cercaMode() {
		
		super.cercaMode();
		Record livelloCorrente = navigator.getPercorso().get(navigator.getPercorso().getLength() - 1);
		Map altriAttributi = livelloCorrente != null ? livelloCorrente.getAttributeAsMap("altriAttributi") : null;
		if (altriAttributi != null && "1".equals(altriAttributi.get("showNewUdButton"))
				&& (getFinalita() == null || (!"FASCICOLA_UD".equals(getFinalita()) && !"FASCICOLA".equals(getFinalita())))) {
			newUdButton.show();
		} else {
			newUdButton.hide();
		}
		if (altriAttributi != null && "1".equals(altriAttributi.get("showNewFolderButton"))) {
			newFolderButton.show();
		} else {
			newFolderButton.hide();
		}
		if (altriAttributi != null && "1".equals(altriAttributi.get("showNewFolderCustomButton"))) {
			newFolderCustomButton.show();
		} else {
			newFolderCustomButton.hide();
		}
	}

	@Override
	public void esploraMode(CacheLevelBean cacheLevel) {
		
		super.esploraMode(cacheLevel);
		Record livelloCorrente = navigator.getPercorso().get(navigator.getPercorso().getLength() - 1);
		Map altriAttributi = livelloCorrente != null ? livelloCorrente.getAttributeAsMap("altriAttributi") : null;
		if (altriAttributi != null && "1".equals(altriAttributi.get("showNewUdButton"))
				&& (getFinalita() == null || (!"FASCICOLA_UD".equals(getFinalita()) && !"FASCICOLA".equals(getFinalita())))) {
			newUdButton.show();
		} else {
			newUdButton.hide();
		}
		if (altriAttributi != null && "1".equals(altriAttributi.get("showNewFolderButton"))) {
			newFolderButton.show();
		} else {
			newFolderButton.hide();
		}
		if (altriAttributi != null && "1".equals(altriAttributi.get("showNewFolderCustomButton"))) {
			newFolderCustomButton.show();
		} else {
			newFolderCustomButton.hide();
		}
	}

	@Override
	public void doLookup(Record record) {
		
		if (record.getAttributeAsBoolean("flgSelXFinalita")) {
			super.doLookup(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().recordNonSelezionabileXFinalita_message(), "", MessageType.WARNING));
		}
	}

	public void setIdNodeToOpenByIdFolder(String idFolder, final GenericCallback callback) {
		try {
			if (idFolder != null && !"".equals(idFolder)) {			
				Record record = new Record();
				record.setAttribute("idFolder", idFolder);
				tree.getDataSource().performCustomOperation("getIdNodeByIdFolder", record, new DSCallback() {
	
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record data = response.getData()[0];
							setIdNodeToOpen(data.getAttributeAsString("idNode"));
							if(callback != null) callback.execute();
						}
					}
				}, new DSRequest());
			} else {
				if(getIdNodeToOpen() == null) {
					setIdNodeToOpen("/");
				}
				if(callback != null) callback.execute();
			}
		} catch(Exception e) {
			setIdNodeToOpen(null);
			if(callback != null) callback.execute();
		}
	}

	// Restituisce i valori del filtro MEZZO DI TRASMISSIONE
	private JavaScriptObject getMezzoTrasmissioneValueFilter() {
		JavaScriptObject mezziTrasmissioneFilter = null;
		for (int i = 0; i < list.getLayout().getFilter().getClauseStack().getMembers().length - 1; i++) {
			FormItem lClauseFieldNameItem = list.getLayout().getFilter().getClauseFieldNameItem(i);
			if (lClauseFieldNameItem.getValue() != null && lClauseFieldNameItem.getValue().equals("mezzoTrasmissione")) {
				FormItem lFormItemValueItem = list.getLayout().getFilter().getClauseValueItem(i);
				if (lFormItemValueItem != null) {
					Object lObject = lFormItemValueItem.getValue();
					if (lObject instanceof JavaScriptObject) {
						mezziTrasmissioneFilter = (JavaScriptObject) lObject;
					} else if(lObject instanceof ArrayList) {
						mezziTrasmissioneFilter = JSOHelper.arrayConvert(((ArrayList) lObject).toArray());
					}
				}
			}
		}
		return mezziTrasmissioneFilter;
	}

	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}

	@Override
	public void changeDetail(final GWTRestDataSource datasource, final CustomDetail pDetail) {

		super.changeDetail(datasource, pDetail);
			
		// Per ricaricare i bottoni di dettaglio nel caso delle richieste di accesso agli atti
		createDetailToolStrip();		
		detailLayout.setMembers(detail, detailToolStrip);		
		detailLayout.markForRedraw();
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE OSSERVAZIONI NOTIFICHE
	 */
	public boolean showOsservazioniNotificheButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilOsservazioniNotifiche");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE FIRMA
	 */
	public boolean showFirmaButton(Record record) {
		return record != null && record.getAttributeAsBoolean("abilFirma");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE FIRMA E PROTOCOLLA
	 */
	public boolean showFirmaProtocollaButton(Record record) {
		return record != null && record.getAttributeAsBoolean("abilFirmaProtocolla");
	}
			
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE REVOCA ATTO
	 */
	public boolean showRevocaAttoButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilRevocaAtto");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE PROTOCOLLAZIONE ENTRATA
	 */
	public boolean showProtocollazioneEntrataButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilProtocollazioneEntrata");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE PROTOCOLLAZIONE USCITA
	 */
	public boolean showProtocollazioneUscitaButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilProtocollazioneUscita");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE PROTOCOLLAZIONE INTERNA
	 */
	public boolean showProtocollazioneInternaButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilProtocollazioneInterna");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE VISTO
	 */
	public boolean showVistoDocumentoButton(Record record) {
		return record != null && record.getAttributeAsBoolean("abilVistoElettronico");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE ASSEGNAZIONE
	 */
	public boolean showAssegnazioneButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilAssegnazioneSmistamento");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE INVIO AL PROTOCOLLO
	 */
	public boolean showInvioAlProtocolloButton(Record record) {
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
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE RISPONDI
	 */
	public boolean showRispondiButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilRispondi");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE CONDIVISIONE
	 */
	public boolean showCondivisioneButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilCondivisione");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE SMISTA
	 */
	public boolean showSmistaButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilSmista");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE SMISTACC
	 */
	public boolean showSmistaCCButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilSmistaCC");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE Assegnazioni/invii cononsc
	 */
	public boolean showModAssInviiCCButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilModAssInviiCC");
	}
	
	/**
	 * METODI PER IL CALCOLO DEL TITOLO PER IL BOTTONE DI OSSERVAZIONI E NOTIFICHE
	 */
	private String getEstremiFolderFromLayout(Record record){
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
	
	private String getEstremiUdFromLayout(Record record){
		return record.getAttribute("segnatura");
	}
	
	public void showOpzioniTimbratura(final String operazione, final Record record, final String nomeDatasoruce) {

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
				
				if("archivio".equalsIgnoreCase(nomeDatasoruce)) {
					manageGenerateDocZipArchivioDataSource(operazione,record, impostazioniTimbratura);
				}else {
					manageGenerateDocZipProtocolloDataSource(operazione, impostazioniTimbratura);
				}
				
			}

			
		});
		apponiTimbroWindow.show();

	}
	
	/**
	 * @param operazione
	 * @param record
	 * @param impostazioniTimbratura 
	 */
	public void manageGenerateDocZipArchivioDataSource(String operazione, Record record, Record impostazioniTimbratura) {
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
		lGwtRestDataSource.extraparam.put("messageError", I18NUtil.getMessages().alert_archivio_list_downloadDocsZip());
		lGwtRestDataSource.extraparam.put("limiteMaxZipError", I18NUtil.getMessages().alert_archivio_list_limiteMaxZipError());
		lGwtRestDataSource.extraparam.put("operazione", operazione);
		
		if(impostazioniTimbratura!=null) {
			lGwtRestDataSource.extraparam.put("posizioneTimbro", impostazioniTimbratura.getAttribute("posizioneTimbro"));
			lGwtRestDataSource.extraparam.put("rotazioneTimbro", impostazioniTimbratura.getAttribute("rotazioneTimbro"));
			lGwtRestDataSource.extraparam.put("tipoPagina", impostazioniTimbratura.getAttribute("tipoPagina"));
		}
		
		lGwtRestDataSource.setForceToShowPrompt(false);	
		
		Layout.addMessage(new MessageBean(I18NUtil.getMessages().alert_archivio_list_downloadDocsZip_wait(), "", MessageType.WARNING));
		lGwtRestDataSource.executecustom("generateDocsZip", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					String message = record.getAttribute("message");
					String warningTimbro = record.getAttribute("warningTimbro");
					String warningSbusta = record.getAttribute("warningSbusta");
					if (message != null && !"".equals(message)) {
						Layout.addMessage(new MessageBean(message, "", MessageType.ERROR));
					} else {
						String uri = record.getAttribute("storageZipRemoteUri");
						String nomeFile = record.getAttribute("zipName");
						Record infoFileRecord = record.getAttributeAsRecord("infoFile");
						Record lRecord = new Record();
						lRecord.setAttribute("displayFilename", nomeFile);
						lRecord.setAttribute("uri", uri);
						lRecord.setAttribute("sbustato", "false");
						lRecord.setAttribute("remoteUri", true);
						lRecord.setAttribute("infoFile", infoFileRecord);
						
						DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
						
						if (warningTimbro != null && !"".equals(warningTimbro)) {
							Layout.addMessage(new MessageBean(warningTimbro, "", MessageType.WARNING));
						}else if (warningSbusta != null && !"".equals(warningSbusta)) {
							Layout.addMessage(new MessageBean(warningSbusta, "", MessageType.WARNING));
						}
					}
				}

			}
		});
	}
	
	/**
	 * @param operazione
	 * @param impostazioniTimbratura 
	 */
	public void manageGenerateDocZipProtocolloDataSource(String operazione, Record impostazioniTimbratura) {
		Record record = new Record(((ProtocollazioneDetail) detail).getValuesManager().getValues());
		String segnatura = record.getAttribute("segnatura") != null ? record.getAttribute("segnatura") : getTipoEstremiRecordProtocollazione(record);
		GWTRestDataSource datasource = new GWTRestDataSource("ProtocolloDataSource");
		datasource.extraparam.put("segnatura", segnatura);
		datasource.extraparam.put("messageError", I18NUtil.getMessages().alert_archivio_list_downloadDocsZip());
		datasource.extraparam.put("limiteMaxZipError", I18NUtil.getMessages().alert_archivio_list_limiteMaxZipError());
		datasource.extraparam.put("operazione", operazione);
		
		if(impostazioniTimbratura!=null) {
			datasource.extraparam.put("posizioneTimbro", impostazioniTimbratura.getAttribute("posizioneTimbro"));
			datasource.extraparam.put("rotazioneTimbro", impostazioniTimbratura.getAttribute("rotazioneTimbro"));
			datasource.extraparam.put("tipoPagina", impostazioniTimbratura.getAttribute("tipoPagina"));
		}		
		
		datasource.setForceToShowPrompt(false);
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
					Record infoFileRecord = response.getData()[0].getAttributeAsRecord("infoFile");
					
					scaricaFile(zipName, zipUri, zipUri, infoFileRecord);
					
					if (warningTimbro != null && !"".equals(warningTimbro)) {
						Layout.addMessage(new MessageBean(warningTimbro, "", MessageType.WARNING));
					}else if (warningSbusta != null && !"".equals(warningSbusta)) {
						Layout.addMessage(new MessageBean(warningSbusta, "", MessageType.WARNING));
					}
				}

			}
		});
	}
	
	private boolean showOperazioniTimbratura(Record detailRecord) {
		return detailRecord != null && detailRecord.getAttribute("codCategoriaProtocollo") != null && !"".equalsIgnoreCase(detailRecord.getAttribute("codCategoriaProtocollo"))
				&& ("PG".equalsIgnoreCase(detailRecord.getAttribute("codCategoriaProtocollo")) ||
						"R".equalsIgnoreCase(detailRecord.getAttribute("codCategoriaProtocollo")) ||
						"PP".equalsIgnoreCase(detailRecord.getAttribute("codCategoriaProtocollo")));
	}
	
}