/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioMail.AttachmentReplicableItem;
import it.eng.auriga.ui.module.layout.client.invioMail.InoltroMailWindow;
import it.eng.auriga.ui.module.layout.client.invioMail.InvioMailForm;
import it.eng.auriga.ui.module.layout.client.invioMail.InvioMailPopup;
import it.eng.auriga.ui.module.layout.client.invioMail.InvioNotificaInteropWindow;
import it.eng.auriga.ui.module.layout.client.invioMail.NuovoInvioMailWindow;
import it.eng.auriga.ui.module.layout.client.invioMail.RispostaMailWindow;
import it.eng.auriga.ui.module.layout.client.istanze.EditaIstanzaWindowFromMail;
import it.eng.auriga.ui.module.layout.client.protocollazione.EditaProtocolloWindowFromMail;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.shared.util.AzioniRapide;
import it.eng.auriga.ui.module.layout.shared.util.IndirizziEmailSplitter;
import it.eng.utility.Styles;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.FrecciaDetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

public class DettaglioPostaElettronica extends CustomDetail {

	protected DettaglioEmailWindow window;
	protected DettaglioPostaElettronica instance;

	protected BricioleDiPaneToolStrip bricioleDiPaneToolStrip;

	private FormItemIcon visualizzaCronologiaIcon;

	protected ToolStrip detailToolStrip;
	protected DettaglioPostaElettronicaToolStripButton caricoToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton messaInCaricoToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton rilasciaToolStripButton;
	protected FrecciaDettaglioPostaElettronicaToolStripButton frecciaCaricoToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton inoltraToolStripButton;
	protected FrecciaDettaglioPostaElettronicaToolStripButton frecciaInoltraToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton rispondiToolStripButton;
	protected FrecciaDettaglioPostaElettronicaToolStripButton frecciaRispondiToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton reinviaEmailFaultToolStripButton;
	protected FrecciaDettaglioPostaElettronicaToolStripButton frecciaReinviaToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton downloadZipAllegatiToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton registraToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton registraRepertorioToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton registraIstanzaToolStripButton;
	protected FrecciaDettaglioPostaElettronicaToolStripButton frecciaRegistraIstanzaToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton azioneDaFareToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton nuovoInvioCopiaToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton assegnaToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton annullaAssegnaToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton reloadToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton associaProtocolloToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton archivioToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton annullaArchiviazioneToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton scaricaMailProtocolloToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton stampaMailToolStripButton;
	protected FrecciaDettaglioPostaElettronicaToolStripButton frecciaStampaToolStripButton;
	protected FrecciaDettaglioPostaElettronicaToolStripButton frecciaScaricaMailProtocolloToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton inviaNotificaDetailButton;
	protected DettaglioPostaElettronicaToolStripButton inviaNotificaConfermaDetailButton;
	protected DettaglioPostaElettronicaToolStripButton inviaNotificaEccezioneDetailButton;
	protected DettaglioPostaElettronicaToolStripButton inviaNotificaAggiornamentoDetailButton;
	protected DettaglioPostaElettronicaToolStripButton inviaNotificaAnnullamentoDetailButton;
	protected DettaglioPostaElettronicaToolStripButton reinviaToolStripButton;
	protected DettaglioPostaElettronicaToolStripButton associaInvioDetailButton;
	protected DettaglioPostaElettronicaToolStripButton salvaItemLavorazioneButton;
	protected DettaglioPostaElettronicaToolStripButton salvaBozzaButton;

	private ItemLavorazioneMailLayout portletLayout;

	protected TabSet tabSetGenerale;
	protected Tab tabDatiPrincipali;
	protected Tab tabEmailSuccessiveCollegate;
	protected Tab tabIterBozza;

	protected HiddenItem idEmailItem;
	protected HiddenItem aliasAccountMittenteItem;
	protected HiddenItem categoriaItem;

	protected DynamicForm mDynamicFormEstremi;
	protected StaticTextItem titleItemProgressivo;
	protected StaticTextItem titleItemCasellaAccount;
	protected StaticTextItem titleItemEstremiDocTrasmessi;
	protected StaticTextItem titleItemStatoConsolidamento;
	protected StaticTextItem titleItemStatoLavorazione;
	protected StaticTextItem titleItemDesUOAssegnataria;
	protected StaticTextItem titleItemProtocollataCome;
	protected StaticTextItem titleItemTsRicezione;
	protected StaticTextItem titleItemTsInvio;
	protected StaticTextItem titleTsStatoLavorazioneAPartireDal;
	protected StaticTextItem titleOrarioStatoLavorazione;
	protected StaticTextItem iconaMicroCategoriaItem;
	protected StaticTextItem iconaStatoConsolidamentoItem;

	protected StaticTextItem statoInvioItem;
	protected StaticTextItem statoAccettazioneItem;
	protected StaticTextItem statoConsegnaItem;

	protected TextItem messageIdItem;

	protected StaticTextItem tipoItem;
	protected StaticTextItem messageProgressivoItem;
	protected TextItem statoLavorazioneItem;
	protected TextItem statoConsolidamentoItem;
	protected TextItem tsInvioItem;
	protected TextItem dataStatoLavorazioneAPartireDal;
	protected TextItem orarioStatoLavorazione;

	protected EstremiStaticTextItem desUOAssegnatariaItem;

	protected ImgButtonItem viewerMessaggioAssegnazioneItem;
	protected NestedFormItem nestedFormNotificheInteropItem;
	protected ImgButtonItem flgRicevutaConfermaInteropItem;
	protected ImgButtonItem flgRicevutaEccezioneInteropItem;
	protected ImgButtonItem flgRicevutoAggiornamentoInteropItem;
	protected ImgButtonItem flgRicevutoAnnRegInteropItem;
	protected TextItem tsRicezioneItem;
	protected TextItem casellaAccountItem;

	protected EstremiStaticTextItem estremiDocTrasmessiItem;
	protected EstremiStaticTextItem protocollataComeItem;

	protected DynamicForm mDynamicFormEmailPredecessore;
	protected StaticTextItem emailPredecessoreMessageProgressivoItem;
	protected StaticTextItem emailPredecessoreIconaMicroCategoriaItem;
	protected StaticTextItem emailPredecessoreTipoItem;
	protected TextItem emailPredecessoreTsInvioItem;
	protected TextItem emailPredecessoreTsRicezioneItem;
	protected TextItem emailPredecessoreCasellaAccountItem;
	protected TextItem emailPredecessoreAccountMittenteItem;
	protected TextItem emailPredecessoreDestinatariPrincipaliItem;
	protected TextItem emailPredecessoreDestinatariCCItem;
	protected TextAreaItem emailPredecessoreSubjectItem;

	protected DynamicForm mDynamicFormAttori;

	/* BOZZE */
	private SelectItem lSelectItemMittenteBozza;
	private ExtendedTextItem lTextItemDestinatariBozza;
	private ExtendedTextItem lTextItemDestinatariCCBozza;

	protected StaticTextItem titleItemMittenteEmailInviata;
	protected TextItem mittenteEmailInviataAttoriItem;
	protected StaticTextItem titleItemIndirizzoDestinatariOriginali;
	protected TextAreaItem indirizzoDestinatariOriginaliAttoriItem;
	protected StaticTextItem titleItemIndirizzoDestinatariCopia;
	protected TextAreaItem indirizzoDestinatariCopiaAttoriItem;
//	protected DestinatariPrincipaliItem destinatariPrincipaliAttoriItem;// ReplicableItem
//	protected DestinatariPrincipaliItem destinatariCopiaAttoriItem;// ReplicableItem
//	protected DestinatariPrincipaliItem destinatariCCNAttoriItem;// ReplicableItem
	protected ListaDestinatariPrincipaliItem destinatariPrincipaliAttoriItem;// GridItem
	protected ListaDestinatariPrincipaliItem destinatariCopiaAttoriItem;// GridItem
	protected ListaDestinatariPrincipaliItem destinatariCCNAttoriItem;// GridItem
	protected TextItem numeroTotaleDestinatariPrincipaliAttoriItem;
	protected TextItem numeroTotaleDestinatariCopiaAttoriItem;
	protected TextItem numeroTotaleDestinatariCCNAttoriItem;

	// Azione da fare
	protected DynamicForm dynamicFormAzioneDaFare;
	protected TextItem azioneDaFareItem;
	protected StaticTextItem codAzioneDaFareItem;
	protected StaticTextAreaItem dettaglioAzioneDaFareItem;
	protected StaticTextItem titleAzioneDaFare;
	protected StaticTextItem titleDettaglioAzioneDaFare;

	protected EstremiTitleStaticTextItem titleTsAssegnazioneDal;
	protected TextItem tsUOAssegnazioneDal;

	private EstremiTitleStaticTextItem titleOrarioAssegnazione;
	private TextItem orarioUOAssegnazione;

	protected StaticTextItem titleItemInCaricoA;
	protected TextItem inCaricoAItem;

	protected EstremiTitleStaticTextItem titleTsInCaricoDal;
	protected EstremiTitleStaticTextItem titleOrarioInCaricoDal;
	protected TextItem tsInCaricoDal;
	protected TextItem orarioInCaricoDal;

	protected DynamicForm mDynamicFormContenuti;
	protected StaticTextItem titleItemOggettoDatiProtocollo;
	protected TextItem oggettoDatiProtocolloContenutiItem;
	protected TextAreaItem msgErrRicevutaPECItem;
	protected StaticTextItem titleItemCorpoMail;
	protected TextAreaItem corpoMailContenutiItem;

	private HiddenItem body;
	private HiddenItem azioneItem;

	protected DynamicForm mDynamicFormProtocolloMitt;
	protected StaticTextItem titleEnteProtocollo;
	protected TextItem enteProtocolloItem;
	protected TextItem numeroProtocolloItem;
	protected TextItem annoProtocolloItem;
	protected TextItem dataRegMittProtocolloItem;
	protected StaticTextItem titleOggettoMailProtocollo;
	protected TextAreaItem oggettoMailProtocolloItem;
	protected TextItem registroProtocolloItem;

	protected DynamicForm mDynamicFormFileAllegati;
	protected StaticTextItem labelPregressoItem;
	protected FileAllegatiEmailItem fileAllegatiItem;

	/*
	 * DETAIL SECTION TabSet tabDatiPrincipali
	 */
	protected DetailSection detailSectionEstremi;
	protected DetailSection detailSectionAttori;
	protected DetailSection detailSectionContenuti;
	protected DetailSection detailSectionProtocolloMitt;
	protected DetailSection detailSectionFileAllegati;
	protected DetailSection detailAzioneDaFare;

	protected DetailSection detailSectionBozza;
	protected InvioMailForm mDynamicFormBozza;

	protected CustomList listaEmailCollegateMailEntrata;

	protected CustomList listaEmailCollegateMailUscita;

	/*
	 * DETAIL section tabSet tabEmailCollegate
	 */
	protected DetailSection detailSectionListaEmaiInviate;
	protected DetailSection detailSectionListaEmailRicevute;

	protected DynamicForm mDynamicFormListaMailEntrata;
	protected DynamicForm mDynamicFormListMailInviata;

	protected DynamicForm hiddenForm;

	private String classifica;
	public boolean ricaricaPagina = false;

	public int indexTab = 0;
	private TextAreaItem motivoEccezioneContenutiItem;
	private StaticTextItem titleItemMotivoEccezione;

	private String tipoRel;

	protected FormItemIcon flgRichConfermaIcon;
	protected FormItemIcon flgRichConfermaLetturaIcon;

	private HiddenItem flgRichConfermaItem;
	private HiddenItem flgRichConfermaLetturaItem;
	
	private HiddenItem flgURIRicevutaItem;

	// MainToolStrip
	protected ToolStrip mainToolStrip;
	protected SelectItem firmaInCalceSelectItem;
	protected ListGrid firmaInCalcePickListProperties;
	protected ToolStripButton firmaPredefinitaButton;
	protected GWTRestDataSource firmeDS;
	
	private Record recordList;
	
	private static final int NUMERO_CAMPI = 10;

	public DettaglioPostaElettronica(String nomeEntita) {
		this(nomeEntita, null, null, null, null);
	}

	public DettaglioPostaElettronica(String nomeEntita, String pTipoRel) {
		this(nomeEntita, pTipoRel, null, null, null);
	}

	public DettaglioPostaElettronica(String nomeEntita, String tipoRel, final String classifica, Record listRecord, Record abilInterop) {
		super(nomeEntita);

		instance = this;

		this.classifica = classifica;
		this.tipoRel = tipoRel;
		this.recordList = listRecord;

		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);

		// Hidden item & form
		hiddenForm = new DynamicForm();
		hiddenForm.setValuesManager(vm);

		idEmailItem = new HiddenItem("idEmail");

		aliasAccountMittenteItem = new HiddenItem("aliasAccountMittente");

		categoriaItem = new HiddenItem("categoria");

		azioneItem = new HiddenItem("azioneDaFareBean");
		
		flgURIRicevutaItem = new HiddenItem("flgURIRicevuta");

		hiddenForm.setItems(idEmailItem, categoriaItem, azioneItem, flgURIRicevutaItem, aliasAccountMittenteItem);

		buildFormEstremi();

		buildFormBozza();

		buildFormEmailPredecessore();
		buildFormContenuti();
		buildFormProtocolloMitt();
		buildFormFileAllegati();
		buildFormAzioneDaFare();

		buildListaEmailCollegateMailEntrata();
		buildListaEmailCollegateMailUscita();

		tabSetGenerale = new TabSet();
		tabSetGenerale.setTabBarPosition(Side.TOP);
		tabSetGenerale.setTabBarAlign(Side.LEFT);
		tabSetGenerale.setWidth100();
		tabSetGenerale.setBorder("0px");
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			tabSetGenerale.setTabIndex(null);
			tabSetGenerale.setCanFocus(true);
			tabDatiPrincipali = new Tab("<h5>" + I18NUtil.getMessages().posta_elettronica_detail_section_tab_datiPrincipali() + "</h5>");
			tabEmailSuccessiveCollegate = new Tab("<h5><b>" + I18NUtil.getMessages().posta_elettronica_detail_section_tab_emailSucc() + "</b></h5>");
			tabIterBozza = new Tab("<h5><b>" + I18NUtil.getMessages().posta_elettronica_detail_section_note_appunti() + "</b></h5>");					
		} else {
			tabSetGenerale.setCanFocus(false);
			tabSetGenerale.setTabIndex(-1);
			tabDatiPrincipali = new Tab("<b>" + I18NUtil.getMessages().posta_elettronica_detail_section_tab_datiPrincipali() + "</b>");
			tabEmailSuccessiveCollegate = new Tab("<b>" + I18NUtil.getMessages().posta_elettronica_detail_section_tab_emailSucc() + "</b>");
			tabIterBozza = new Tab("<b>" + I18NUtil.getMessages().posta_elettronica_detail_section_note_appunti() + "</b>");			
		}		

		setPaddingAsLayoutMargin(false);

		// Creo i due TAB
		tabDatiPrincipali.setPrompt("Dati Principali");

		tabEmailSuccessiveCollegate.setPrompt("E-mail successive");

		// Tab di appunti & note
		tabIterBozza.setPrompt(I18NUtil.getMessages().posta_elettronica_detail_section_note_appunti());
		tabIterBozza.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent arg0) {
				// Apri il primo item, nel caso mancasse
				if ((portletLayout != null) && (portletLayout.getItemLavorazioneMailItem() != null)
						&& (portletLayout.getItemLavorazioneMailItem().getEditing())) {
					int count = portletLayout.getItemLavorazioneMailItem().getTotalMembers();
					if (count == 0) {
						portletLayout.getItemLavorazioneMailItem().onClickNewButton();
					}
				}
			}
		});

		// Creo i due Spacer
		VLayout spacer1 = new VLayout();
		spacer1.setHeight100();
		spacer1.setWidth100();

		VLayout spacer2 = new VLayout();
		spacer2.setHeight100();
		spacer2.setWidth100();

		VLayout layoutDatiPrincipaliDetailSection = createDetailSectionTabSetDatiPrincipali(listRecord);

		VLayout layoutTab1 = new VLayout();
		layoutTab1.addMember(layoutDatiPrincipaliDetailSection);
		layoutTab1.addMember(spacer1);

		VLayout layoutTab2 = new VLayout();
		layoutTab2.setMembersMargin(10);
		layoutTab2.setPadding(5);
		layoutTab2.setAlign(Alignment.CENTER);
		layoutTab2.addMember(listaEmailCollegateMailEntrata);
		layoutTab2.addMember(listaEmailCollegateMailUscita);

		VLayout layoutDatiIterBozza = createLayoutIterBozza();
		VLayout layoutTab3 = new VLayout();
		layoutTab3.addMember(layoutDatiIterBozza);

		// Aggiungo i layout ai tab
		tabDatiPrincipali.setPane(layoutTab1);
		tabEmailSuccessiveCollegate.setPane(layoutTab2);
		tabIterBozza.setPane(layoutTab3);

		tabSetGenerale.setPaneMargin(0);

		// Aggiungo i tab al TabSetGenerale
		tabSetGenerale.addTab(tabDatiPrincipali);
		tabSetGenerale.addTab(tabEmailSuccessiveCollegate);
		// Il tab degli item in lavorazione si vede solamente se è attibo
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ITEM_LAV_EMAIL")) {
			tabSetGenerale.addTab(tabIterBozza);
		}

		tabSetGenerale.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {
				indexTab = event.getTabNum();
				vm.setAttribute("indexTab", indexTab, true);
			}
		});

		// Creo la TOOLSTRIP e aggiungo i bottoni
		List<DetailToolStripButton> lDetailButtons = getDetailsButton(listRecord);
		detailToolStrip = new ToolStrip();
		detailToolStrip.setName("bottoni");
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		if (!AurigaLayout.getParametroDBAsBoolean("HIDE_LABEL_BUTTON_DETT_EMAIL")) {
			detailToolStrip.setMembersMargin(2);
			if (!AurigaLayout.getIsAttivaAccessibilita()) {
				detailToolStrip.addFill();
			}
		}
		for (DetailToolStripButton button : lDetailButtons) {
			detailToolStrip.addButton(button);
		}

		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout vLayout = new VLayout();
		vLayout.setHeight100();
		vLayout.setWidth100();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			vLayout.setName("main_layout");		
		}
		
		bricioleDiPaneToolStrip = new BricioleDiPaneToolStrip() {

			@Override
			public void caricaLivello(Record record) {

				/* Nuova gestione Briciole di Pane */
				caricaDettaglioEmailCollegata(record.getAttribute("idEmail"));
			}

			@Override
			public String getTitleField() {
				return "descrizione";
			}
		};

		// Creo la toolstrip superiore
		createMainToolstrip();

		vLayout.addMember(bricioleDiPaneToolStrip);

		if (AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")) {
			vLayout.addMember(mainToolStrip, 0);
		}

		vLayout.addMember(tabSetGenerale);
		vLayout.addMember(detailToolStrip);
		addMember(vLayout);
	}

	private List<DetailToolStripButton> getDetailsButton(final Record listRecord) {

		List<DetailToolStripButton> detailButtons = new ArrayList<DetailToolStripButton>();

		// CARICO
		caricoToolStripButton = new DettaglioPostaElettronicaToolStripButton("Carico", "postaElettronica/prendiInCarico.png");
		caricoToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (isAttivaAbilitazione("abilitaPresaInCarico")) {
					final OperazioniPerEmailPopup caricoEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.PRESA_IN_CARICO.getValue(),null) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							ricaricaPagina = true;
							final RecordList listaEmail = new RecordList();
							Record mailRecord = new Record(vm.getValues());
							listaEmail.add(mailRecord);
							Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_presa_in_carico_in_corso());
							buildTipoOperazione(listaEmail, null, getMotivo(), false, TipoOperazioneMail.PRESA_IN_CARICO.getValue());
							markForDestroy();
						}
					};
					caricoEmailPopup.show();
				} else {

					Record recordDestPref = new Record();						
					RecordList listaAzioniRapide = new RecordList();
					Record recordAzioneRapida = new Record();						
					recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.METTI_IN_CARICO.getValue());
					listaAzioniRapide.add(recordAzioneRapida);
					recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);					
					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
					
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record destinatariPreferiti = response.getData()[0];
//								final RecordList listaDestinatariPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUtentiPreferitiMail");
								final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.METTI_IN_CARICO.getValue()));								
								final OperazioniPerEmailPopup caricoEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.MESSA_IN_CARICO.getValue(),listaDestinatariPreferiti) {

									@Override
									public void onClickOkButton(final DSCallback callback) {
										ricaricaPagina = true;
										final RecordList listaEmail = new RecordList();
										Record mailRecord = new Record(vm.getValues());
										listaEmail.add(mailRecord);
										Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_carico_in_corso());
										buildTipoOperazione(listaEmail, getUtente(), getMotivo(), false, TipoOperazioneMail.MESSA_IN_CARICO.getValue());
										markForDestroy();
									}
								};
								caricoEmailPopup.show();
							}
						}
					}, new DSRequest());
				}
				
			}
		});

		frecciaCaricoToolStripButton = new FrecciaDettaglioPostaElettronicaToolStripButton();
		frecciaCaricoToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				final Menu menu = new Menu();
				
				Record recordDestPref = new Record();						
				RecordList listaAzioniRapide = new RecordList();
				Record recordAzioneRapidaMessaInCarico = new Record();
				recordAzioneRapidaMessaInCarico.setAttribute("azioneRapida", AzioniRapide.METTI_IN_CARICO.getValue()); 
				listaAzioniRapide.add(recordAzioneRapidaMessaInCarico);
				Record recordAzioneRapidaMandaInApprovazione = new Record();
				recordAzioneRapidaMandaInApprovazione.setAttribute("azioneRapida", AzioniRapide.MANDA_IN_APPROVAZIONE.getValue()); 
				listaAzioniRapide.add(recordAzioneRapidaMandaInApprovazione);
				recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);					
				
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
				lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
		
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record destinatariPreferiti = response.getData()[0];
							
							MenuItem messaInCaricoMenuItem = buildSezioneInCarico(destinatariPreferiti);
							MenuItem presaInCaricoMenuItem = buildSezionePresaInCarico();
							MenuItem mandaInApprovazioneMenuItem = buildSezioneMandaInApp(destinatariPreferiti);
							
							if (isAttivaAbilitazione("abilitaMessaInCarico")) {
								menu.addItem(messaInCaricoMenuItem);
								menu.addItem(mandaInApprovazioneMenuItem);
							}
							if (isAttivaAbilitazione("abilitaPresaInCarico")) {
								menu.addItem(presaInCaricoMenuItem);
							}
							menu.showContextMenu();							
						}
					}
				}, new DSRequest());
			}



			/**
			 * @return La voce menu 'Manda in approvazione' con le sottovoci relative Standard e Rapida
			 */
			private MenuItem  buildSezioneMandaInApp(Record destinatariPreferiti) {
				// -- Sezione Manda in approvazione -- 
				
				Menu creaInApprovazioneMenu = new Menu();
				final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.MANDA_IN_APPROVAZIONE.getValue()));
				
				// Manda in approvazione Main
				MenuItem mandaInApprovazioneMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_mandaInApprovazioneMenuItem());
				
				// Manda in approvazione Standard
				MenuItem mandaInApprovazioneStandardItem = new MenuItem("Standard");
				
				mandaInApprovazioneStandardItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final OperazioniPerEmailPopup mandaInApprovazioneEmailPopup = new OperazioniPerEmailPopup(
								TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue(), listaDestinatariPreferiti) {

							@Override
							public void onClickOkButton(final DSCallback callback) {
								
								ricaricaPagina = true;
								final RecordList listaEmail = new RecordList();
								Record mailRecord = new Record(vm.getValues());
								listaEmail.add(mailRecord);
								String motivo = getMotivo();
								if (motivo != null && !"".equalsIgnoreCase(motivo)) {
									motivo = "[APPROVAZIONE]" + motivo;
								} else {
									motivo = "[APPROVAZIONE]";
								}
								buildTipoOperazione(listaEmail, getUtente(), motivo, false, TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue());
								markForDestroy();
							}
						};
						mandaInApprovazioneEmailPopup.show();
					}
				});
				creaInApprovazioneMenu.addItem(mandaInApprovazioneStandardItem);
				
				// Manda in approvazione Rapida
				MenuItem mandaInApprovazioneRapidaItem = new MenuItem("Rapida");
				
				if(listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
					Menu scelteRapide = buildScelteRapideInAppr(destinatariPreferiti);
					mandaInApprovazioneRapidaItem.setSubmenu(scelteRapide);
				} else {
					mandaInApprovazioneRapidaItem.setEnabled(false);
				}
				
				
				creaInApprovazioneMenu.addItem(mandaInApprovazioneRapidaItem);
				
				
				mandaInApprovazioneMenuItem.setSubmenu(creaInApprovazioneMenu);
				
				return mandaInApprovazioneMenuItem;
			}



			/**
			 * @return  La voce menu 'Prendi in carico' 
			 */
			private MenuItem buildSezionePresaInCarico() {
				// Prendi in carico
				MenuItem presaInCaricoMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_presa_in_carico_menu_item());
				presaInCaricoMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final OperazioniPerEmailPopup presaInCaricoEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.PRESA_IN_CARICO.getValue(), null) {

							@Override
							public void onClickOkButton(final DSCallback callback) {
								
								ricaricaPagina = true;
								final RecordList listaEmail = new RecordList();
								Record mailRecord = new Record(vm.getValues());
								listaEmail.add(mailRecord);
								Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_presa_in_carico_in_corso());
								buildTipoOperazione(listaEmail, getUtente(), getMotivo(), false, TipoOperazioneMail.PRESA_IN_CARICO.getValue());
								markForDestroy();
							}
						};
						presaInCaricoEmailPopup.show();
					}
				});
				return presaInCaricoMenuItem;
			}

			
			/**
			 * Voce menu 'Metti in carico' con le sottovoci relative Standard e Rapida
			 *  
			 */
			private MenuItem buildSezioneInCarico(Record destinatariPreferiti) {
				
				//  -- Sezione Metti in carico -- 
				
				Menu creaInCarico = new Menu();
				final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.METTI_IN_CARICO.getValue()));
				
				// Metti in carico Main
				MenuItem messaInCaricoMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_messa_in_carico_menu_item());
				
				// Metti in carico Standard 
				MenuItem messaInCaricoStandardItem = new MenuItem("Standard"); 
				messaInCaricoStandardItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final OperazioniPerEmailPopup mettiInCaricoEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.MESSA_IN_CARICO.getValue(), listaDestinatariPreferiti) {

							@Override
							public void onClickOkButton(final DSCallback callback) {
								
								ricaricaPagina = true;
								final RecordList listaEmail = new RecordList();
								Record mailRecord = new Record(vm.getValues());
								listaEmail.add(mailRecord);
								Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_carico_in_corso());
								buildTipoOperazione(listaEmail, getUtente(), getMotivo(), false, TipoOperazioneMail.MESSA_IN_CARICO.getValue());
								markForDestroy();
							}
						};
						mettiInCaricoEmailPopup.show();
					}
				});
				creaInCarico.addItem(messaInCaricoStandardItem);

				// Metti in carico Rapida
				MenuItem messaInCaricoRapidaItem = new MenuItem("Rapida");				
				
				if(listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
					Menu scelteRapide = buildScelteRapideInCarico(destinatariPreferiti);
					messaInCaricoRapidaItem.setSubmenu(scelteRapide);
				} else {
					messaInCaricoRapidaItem.setEnabled(false);
				}

				creaInCarico.addItem(messaInCaricoRapidaItem);

				messaInCaricoMenuItem.setSubmenu(creaInCarico);
				return messaInCaricoMenuItem;
				
			}

			/**
			 * 
			 * @param record contiene la lista dei destinatari Preferiti
			 * @return un menu contenente la lista dei preferiti da visualizzare in modalità Rapida
			 */
			private Menu buildScelteRapideInCarico(Record destinatariPreferiti){
				Menu scelteRapide = new Menu();
				Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
				RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.METTI_IN_CARICO.getValue()));								
				
				if (success != null && success == true
						&& listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
					for(int i=0; i < listaDestinatariPreferiti.getLength();i++){
						
						Record currentRecord = listaDestinatariPreferiti.get(i);
						final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
						final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
						 
						MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
						currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {

								final RecordList listaMail = new RecordList();
								Record mailRecord = new Record(vm.getValues());
								listaMail.add(mailRecord);
								Layout.showWaitPopup(
										I18NUtil.getMessages().posta_elettronica_messa_in_carico_in_corso());

								Record record = new Record();
								record.setAttribute("listaRecord", listaMail);
								record.setAttribute("iduserlockfor", idDestinatarioPreferito);
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LockEmailDataSource");
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										
										ricaricaPagina = true;
										String errorMsg = tipoOperazioneErrorMessage(response, listaMail, "idEmail",
												"id", false, TipoOperazioneMail.MESSA_IN_CARICO.getValue());
										reloadDetail();
										Layout.hideWaitPopup();
										if (errorMsg != null) {
											Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
										} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Layout.addMessage(new MessageBean(
													I18NUtil.getMessages()
															.posta_elettronica_messa_in_carico_successo(),
													"", MessageType.INFO));
										}
									}
								});
							}
						});
						scelteRapide.addItem(currentRapidoItem);
					}
				}
				return scelteRapide;
			}

			/**
			 * 
			 * @param record contiene la lista dei destinatari Preferiti
			 * @return un menu contenente la lista dei preferiti da visualizzare in modalità Rapida
			 */
			private Menu buildScelteRapideInAppr(Record destinatariPreferiti){
				
				Menu scelteRapide = new Menu();
				Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
				RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.MANDA_IN_APPROVAZIONE.getValue()));								
							
				if (success != null && success == true
						&& listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
					for(int i=0; i < listaDestinatariPreferiti.getLength();i++){
						
						Record currentRecord = listaDestinatariPreferiti.get(i);
						final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
						final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
						 
						MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
						currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {
								
								final RecordList listaMail = new RecordList();
								Record mailRecord = new Record(vm.getValues());
								listaMail.add(mailRecord);
								Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_approvazione_in_corso());

								Record record = new Record();
								record.setAttribute("listaRecord", listaMail);
								record.setAttribute("iduserlockfor", idDestinatarioPreferito);
								record.setAttribute("motivi", "[APPROVAZIONE]");
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LockEmailDataSource");
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										
										ricaricaPagina = true;
										String errorMsg = tipoOperazioneErrorMessage(response, listaMail, "idEmail", "id", false, TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue());
										reloadDetail();
										Layout.hideWaitPopup();
										if (errorMsg != null) {
											Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
										} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_manda_in_approvazione_successo(), "", MessageType.INFO));
											
										}
									}
								});
							}
						});
						scelteRapide.addItem(currentRapidoItem);
					}
				}
				return scelteRapide;
			}	
		});
		
		// Rilascia
		rilasciaToolStripButton = new DettaglioPostaElettronicaToolStripButton(I18NUtil.getMessages().posta_elettronica_rilascia_tool_strip_button(),
				"postaElettronica/rilascia.png");
		rilasciaToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				onClickRilasciaContenutiMenu();
			}
		});

		// Inoltro mail
		inoltraToolStripButton = new DettaglioPostaElettronicaToolStripButton(I18NUtil.getMessages().posta_elettronica_inoltra_tool_strip_button(),
				"postaElettronica/iconMilano/inoltro_mail.png");
		inoltraToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				onClickInoltraContenutiMenu();
			}
		});
		// Freccia Inoltro mail
		frecciaInoltraToolStripButton = new FrecciaDettaglioPostaElettronicaToolStripButton();
		frecciaInoltraToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				Menu menu = new Menu();

				// Allega e-mail originale
				MenuItem inoltraMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_inoltra_menu_item());
				inoltraMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						onClickInoltraAllegandoMailOriginale();
					}
				});

				// Allega e-mail senza busta di trasporto
				MenuItem inoltraMenuItemSbustato = new MenuItem(I18NUtil.getMessages().posta_elettronica_inoltra_menu_item_sbustato());
				inoltraMenuItemSbustato.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						onClickInoltraAllegandoMailSenzaBusta();
					}
				});

				// Allega contenuti - testo e file - dell'email
				MenuItem inoltraContenutiMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_inoltra_contenuti_menu_item());
				inoltraContenutiMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						onClickInoltraContenutiMenu();
					}
				});

				
				menu.addItem(inoltraContenutiMenuItem);
				
				menu.addItem(inoltraMenuItem);
				if (isAttivaAbilitazione("abilitaScaricaEmailSenzaBustaTrasporto")) {
					menu.addItem(inoltraMenuItemSbustato);
				}
				

				menu.showContextMenu();
			}
		});

		// Rispondi mail
		rispondiToolStripButton = new DettaglioPostaElettronicaToolStripButton(I18NUtil.getMessages().posta_elettronica_rispondi_tool_strip_button(),
				"postaElettronica/iconMilano/risposta_mail.png");
		rispondiToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				final Record recordAllegati = new Record(vm.getValues());
				if(recordAllegati != null && recordAllegati.getAttributeAsRecordList("listaAllegati") != null
						&& !recordAllegati.getAttributeAsRecordList("listaAllegati").isEmpty()) {
					
					Menu subRispondi = new Menu();
					
					MenuItem rispondiStandardMenuItem = new MenuItem("Standard");
					rispondiStandardMenuItem.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							
							buildRispondiToolStripButton(false);
						}
					});

					MenuItem rispondiConAllegatiMenuItem = new MenuItem("Con allegati");
					rispondiConAllegatiMenuItem.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							
							buildRispondiToolStripButton(true);
						}
					});
					
					subRispondi.setItems(rispondiStandardMenuItem,rispondiConAllegatiMenuItem);
					subRispondi.showContextMenu();
					
				} else {
					buildRispondiToolStripButton(false);
				}
			}
		});
		// Freccia rispondi mail
		frecciaRispondiToolStripButton = new FrecciaDettaglioPostaElettronicaToolStripButton();
		frecciaRispondiToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				final Record recordAllegati = new Record(vm.getValues());
					
				Menu menuSenzaAllegati = new Menu();
				Menu menuConAllegati = new Menu();

				if(recordAllegati != null && recordAllegati.getAttributeAsRecordList("listaAllegati") != null
						&& !recordAllegati.getAttributeAsRecordList("listaAllegati").isEmpty()) {
					
					final MenuItem rispondiConAllegatiMenuItem = buildRispondiConAllegati();
					final MenuItem rispondiATtuttiConAllegatiMenuItem = buildRispondiATuttiConAllegati();
					
					menuConAllegati.addItem(rispondiConAllegatiMenuItem);
					menuConAllegati.addItem(rispondiATtuttiConAllegatiMenuItem);
					
					menuConAllegati.showContextMenu();
					
				} else {
					// Rispondi
					MenuItem rispondiMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_rispondi_menu_item());
					rispondiMenuItem.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							
						
							buildRispondiToolStripButton(false);
						}
					});
					// Rispondi a tutti
					MenuItem rispondiATuttiMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_rispondi_a_tutti_menu_item());
					rispondiATuttiMenuItem.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							
									
							buildRispondiATuttiToolStripButton(false);
						}
					});
					
					menuSenzaAllegati.setItems(rispondiMenuItem, rispondiATuttiMenuItem);
					menuSenzaAllegati.showContextMenu();
				}
			}
		});

		// Registra istanza
		registraIstanzaToolStripButton = new DettaglioPostaElettronicaToolStripButton(I18NUtil.getMessages().posta_elettronica_button_registra_istanza(),
				"menu/istanze.png");
		registraIstanzaToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (isAttivaAbilitazione("abilitaRegIstanzaAutotutela")) {
					caricaIstanza("istanze_autotutela");
				} else if (isAttivaAbilitazione("abilitaRegIstanzaCED")) {
					caricaIstanza("istanze_ced");
				}
			}
		});
		// Freccia registra istanza
		frecciaRegistraIstanzaToolStripButton = new FrecciaDettaglioPostaElettronicaToolStripButton();
		frecciaRegistraIstanzaToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				Menu menu = new Menu();
				MenuItem istanzaAutotutela = new MenuItem(I18NUtil.getMessages().posta_elettronica_button_registra_istanza_autotutela(),
						"menu/istanze_autotutela.png");
				istanzaAutotutela.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						caricaIstanza("istanze_autotutela");
					}
				});
				MenuItem istanzaCed = new MenuItem(I18NUtil.getMessages().posta_elettronica_button_registra_istanza_ced(), "menu/istanze_ced.png");
				istanzaCed.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						caricaIstanza("istanze_ced");
					}
				});

				if (isAttivaAbilitazione("abilitaRegIstanzaAutotutela")) {
					menu.addItem(istanzaAutotutela);
				}
				if (isAttivaAbilitazione("abilitaRegIstanzaCED")) {
					menu.addItem(istanzaCed);
				}

				menu.showContextMenu();
			}
		});

		// Nuovo invio come copia
		nuovoInvioCopiaToolStripButton = new DettaglioPostaElettronicaToolStripButton(
				I18NUtil.getMessages().posta_elettronica_nuovo_invio_copia_tool_strip_button(), "postaElettronica/inoltro.png");
		nuovoInvioCopiaToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				final Record recordAzioneDaFare = new Record(vm.getValues());

				manageNuovoInvioCopiaClick(recordAzioneDaFare, true);
			}
		});

		// Re-Invia
		reinviaEmailFaultToolStripButton = new DettaglioPostaElettronicaToolStripButton(
				I18NUtil.getMessages().posta_elettronica_reinvia_email_fault_tool_strip_button(), "postaElettronica/inoltro.png");
		reinviaEmailFaultToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				ricaricaPagina = true;
				reinviaEmailFaultToolStripButton.focusAfterGroup();
				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						if (isBozza(listRecord)) {
							boolean validate = true;
							vm.clearErrors(true);
							if (!vm.getItem("destinatari").validate()) {
								validate = false;
							}
							if (vm.getItem("listaItemInLavorazione") != null && !vm.getItem("listaItemInLavorazione").validate()) {
								validate = false;
							}
							if (validate) {
								final Record recordInvia = getSalvaInBozzaMailBeanToRecord();
								if (isMessaggioAskChiusuraMailProvenienzaToShow()) {
									// Apro la popup per chiedere se chiudere o no le mail di provenienza
									// Verifico se ho azioni da fare sulle mail da chiudere
									Map<String, String> mappaParametriPopupChiusuraMailProvenienza = generaParametriPopupChiusuraMailProvenienza();
									// Genero il messaggio da mostrare nella popup di chiudura
																	
									InvioMailPopup invioMailPopup = new InvioMailPopup(mappaParametriPopupChiusuraMailProvenienza) {
			
										@Override
										public void onClickChiudiMailECompletaAzioneButton(Record formRecord) {
											//Il terzo parametro è true perchè in questo caso dobbiamo chiudere la mail
											manageInviaBozzaClick(recordInvia, true, "COMPL");
											markForDestroy();
										}
			
										@Override
										public void onClickChiudiMailEAnnullaAzioneButton(Record formRecord) {
											//Il terzo parametro è false perchè in questo caso NON dobbiamo chiudere la mail
											manageInviaBozzaClick(recordInvia, true, "ANN");
											markForDestroy();
										}
			
										@Override
										public void onClickChiudiMailButton(Record formRecord) {
											//Il terzo parametro è true perchè in questo caso dobbiamo chiudere la mail
											manageInviaBozzaClick(recordInvia, true, null);
											markForDestroy();
										}
			
										@Override
										public void onClickNonChiudereMailButton(Record formRecord) {
											//Il terzo parametro è true perchè in questo caso dobbiamo chiudere la mail
											manageInviaBozzaClick(recordInvia, false, null);
											markForDestroy();
										}
										
									};
									invioMailPopup.show();
									
								} else {
									manageInviaBozzaClick(recordInvia, false, null);
								}
							}
						} else {
							final Record recordReinvia = new Record();
							recordReinvia.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
							manageReinviaClick(recordReinvia);
						}
					}
				});
			}
		});

		// Freccia re-invia
		frecciaReinviaToolStripButton = new FrecciaDettaglioPostaElettronicaToolStripButton();
		frecciaReinviaToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				Menu menu = new Menu();
				MenuItem rispondiMenuItem = new MenuItem("Re-Invia");

				rispondiMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						final Record recordReinvia = new Record();
						recordReinvia.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
						manageReinviaClick(recordReinvia);
					}
				});

				MenuItem rispondiATuttiMenuItem = new MenuItem("Nuovo invio come copia");
				rispondiATuttiMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						final Record recordAzioneDaFare = new Record(vm.getValues());

						manageNuovoInvioCopiaClick(recordAzioneDaFare, true);
					}
				});
				menu.setItems(rispondiMenuItem, rispondiATuttiMenuItem);
				menu.showContextMenu();
			}
		});

		// Azione da fare mail
		azioneDaFareToolStripButton = new DettaglioPostaElettronicaToolStripButton(I18NUtil.getMessages().posta_elettronica_azione_da_fare_tool_strip_button(),
				"postaElettronica/todo.png");
		azioneDaFareToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				final Record recordAzioneDaFare = new Record(vm.getValues());

				manageAzioneDaFareClick(recordAzioneDaFare);
			}
		});

		// Assegna mail
		
		assegnaToolStripButton = new DettaglioPostaElettronicaToolStripButton(I18NUtil.getMessages().posta_elettronica_set_uo_competente(),
		"archivio/assegna.png");
		assegnaToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				final Menu creaAssegnaUO = new Menu(); 
				
				Record recordDestPref = new Record();						
				RecordList listaAzioniRapide = new RecordList();
				Record recordAzioneRapida = new Record();						
				recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_UO_COMPETENTE.getValue());
				listaAzioniRapide.add(recordAzioneRapida);
				recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);					
				
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
				lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
		
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							
							Record destinatariPreferiti = response.getData()[0];
							final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_UO_COMPETENTE.getValue()));							
							
							MenuItem assegnaMenuStandardItem = new MenuItem("Standard");
							
							assegnaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									final Record listRecordAssegna = new Record();
									listRecordAssegna.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
									listRecordAssegna.setAttribute("flgIo", new Record(vm.getValues()).getAttribute("flgIO"));
									
									manageAssegnaClick(listRecordAssegna, listaDestinatariPreferiti);
								}
							});
							creaAssegnaUO.addItem(assegnaMenuStandardItem);

							MenuItem assegnaMenuRapidoItem = new MenuItem("Rapido");

							if(listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
								Menu scelteRapide = buildScelteRapideAssegnaUO(destinatariPreferiti);
								assegnaMenuRapidoItem.setSubmenu(scelteRapide);
							} else {
								assegnaMenuRapidoItem.setEnabled(false);
							}

							creaAssegnaUO.addItem(assegnaMenuRapidoItem);

							creaAssegnaUO.showContextMenu();							     
						}
					}
				}, new DSRequest());
			}
			
			
			/**
			 * 
			 * @param record contiene la lista dei destinatari Preferiti
			 * @return un menu contenente la lista dei preferiti da visualizzare in modalità Rapida
			 */
			private Menu buildScelteRapideAssegnaUO(Record destinatariPreferiti){

				Menu scelteRapide = new Menu();
				Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
				RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_UO_COMPETENTE.getValue()));							
				
				if (success != null && success == true
						&& listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
					for(int i=0; i < listaDestinatariPreferiti.getLength();i++){
						
						Record currentRecord = listaDestinatariPreferiti.get(i);
						final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
						final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
						final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
						 
						MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
						currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {
								
								final Record listRecordAssegna = new Record();
								listRecordAssegna.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
								listRecordAssegna.setAttribute("flgIo", new Record(vm.getValues()).getAttribute("flgIO"));
								
								RecordList listaAssegnazioni = new RecordList();
								Record recordAssegnazioni = new Record();
								recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
								recordAssegnazioni.setAttribute("typeNodo",tipoDestinatarioPreferito);
								listaAssegnazioni.add(recordAssegnazioni);

								Record record = new Record();
								final RecordList listaEmail = new RecordList();
								listaEmail.add(listRecordAssegna);
								record.setAttribute("listaRecord", listaEmail);
								record.setAttribute("listaAssegnazioni", listaAssegnazioni);

								Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_assegnazione_mail());

								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneEmailDataSource");
								try {
									lGwtRestDataSource.addData(record, new DSCallback()   {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											ricaricaPagina = true;
											operationCallback(response, listRecordAssegna, "idEmail", "Assegnazione effettuata con successo",
													"Si è verificato un errore durante l'assegnazione!", null);

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
				return scelteRapide;
			}
		});
		
		registraToolStripButton = new DettaglioPostaElettronicaToolStripButton(I18NUtil.getMessages().posta_elettronica_list_registraMenuItem(),
				"buttons/protocollazione.png");
		registraToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				boolean isProtocollaInteraEmail = Layout.isPrivilegioAttivo("EML/RIE/PG");
				boolean isRepertoriaInteraEmail = Layout.isPrivilegioAttivo("EML/RIE/R"); 
				
				// Da qua gestisco repertorio, protocollazione e registrazione su richiesta accesso atti
				// Se ho solo una di queste abilitazione il tasto esegue direttamente la funzione, altrimenti viene aperto un menu di scelta
				int numeroAbilitazioni = 0;
				if (isAttivaAbilitazione("abilitaRepertoria")) {
					numeroAbilitazioni ++;
					if(isRepertoriaInteraEmail) {
						numeroAbilitazioni ++;
					}
				}
				if (isAttivaAbilitazione("abilitaProtocolla")) {
					numeroAbilitazioni ++;
				}
				if(isProtocollaInteraEmail) {
					numeroAbilitazioni ++;
				}
				if (isAttivaAbilitazione("abilitaProtocollaAccessoAttiSUE")) {
					numeroAbilitazioni ++;
				}
				if (numeroAbilitazioni > 1) {
					
					final Menu registraMenu = new Menu(); 
					
					if (isAttivaAbilitazione("abilitaProtocolla")) {
						
						MenuItem protocollaMenuItem = new MenuItem("Protocolla");
						protocollaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {
								
								manageProtocollaClick(false, false);
							}
						});
						registraMenu.addItem(protocollaMenuItem);
					}
					
					if(isProtocollaInteraEmail) {
						// Menu Protocolla intera email
						MenuItem protocollaInteraEmailMenuItem = new MenuItem("Protocollazione intera mail");
						protocollaInteraEmailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {
								manageProtocollaClick(false,true);
							}
						});
						registraMenu.addItem(protocollaInteraEmailMenuItem);
					}
					
					if (isAttivaAbilitazione("abilitaRepertoria")) {
						
						MenuItem repertorioMenuItem = new MenuItem("Registra a repertorio");
						repertorioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {							
								manageRegistraRepertorio(false);
							}
						});
						registraMenu.addItem(repertorioMenuItem);
						
						if(isRepertoriaInteraEmail) {
							//Registra a Repertorio intera email
							MenuItem repertorioInteraEmailMenuItem = new MenuItem("Repertoriazione intera mail");
							repertorioInteraEmailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
								
								@Override
								public void onClick(MenuItemClickEvent event) {
									manageRegistraRepertorio(true);
								}
							});
							registraMenu.addItem(repertorioInteraEmailMenuItem);
						}
					}
					
					if (isAttivaAbilitazione("abilitaProtocollaAccessoAttiSUE")) {
						
						MenuItem protocollaAccessoAttiSUEMenuItem = new MenuItem("Protocolla accesso atti SUE");
						protocollaAccessoAttiSUEMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {
								
								manageProtocollaClick(true, false);
							}
						});
						registraMenu.addItem(protocollaAccessoAttiSUEMenuItem);
					}
					
					registraMenu.showContextMenu();	
					
				} else if (isAttivaAbilitazione("abilitaProtocolla")) {
					// se è abilitato alla registrazione del repertorio, registra a repertorio
					manageProtocollaClick(false, false);
				} else if(isProtocollaInteraEmail) {
					// se è abilitato alla registrazione dell'intera email
					manageProtocollaClick(false, true);
				} else if (!isAttivaAbilitazione("abilitaRepertoria")) {
					// se è abilitato alla registrazione del repertorio, registra a repertorio
					manageRegistraRepertorio(false);
				} else if(isRepertoriaInteraEmail) {
					manageRegistraRepertorio(true);
				} else if (!isAttivaAbilitazione("abilitaRepertoria")) {
					// se è abilitato alla registrazione accesso atti SUE, registro su accesso atti SUE
					manageProtocollaClick(true, false);
				}
			}
		});

		// Annulla
		annullaAssegnaToolStripButton = new DettaglioPostaElettronicaToolStripButton(I18NUtil.getMessages().posta_elettronica_annulla_uo_competente(),
				"archivio/annulla_uo_competente.png");
		annullaAssegnaToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				final RecordList listaEmail = new RecordList();
				final Record recordAnnullaAssegnazione = new Record();
				recordAnnullaAssegnazione.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
				listaEmail.add(recordAnnullaAssegnazione);
				Record annullamentoRecord = new Record();
				annullamentoRecord.setAttribute("listaAnnullamenti", listaEmail);
				
				MotiviOperazionePopup motiviPopup = new MotiviOperazionePopup(annullamentoRecord, new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						manageAnnullamentoAssegnazioneUOCompetente(object);
						
					}
				});
				motiviPopup.show();
			}
		});

		// Ricarica
		reloadToolStripButton = new DettaglioPostaElettronicaToolStripButton(I18NUtil.getMessages().posta_elettronica_reload_tool_strip_button(),
				"buttons/refreshList.png");
		reloadToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				reloadDetail();
			}
		});

		// Associa
		associaProtocolloToolStripButton = new DettaglioPostaElettronicaToolStripButton(
				I18NUtil.getMessages().posta_elettronica_associa_protocollo_tool_trip_button(), "postaElettronica/associaProtocolloPratica.png");
		associaProtocolloToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				visualizzaRegAssociata();
			}
		});

		// Archivia
		archivioToolStripButton = new DettaglioPostaElettronicaToolStripButton(AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL"),
				"archivio/archiviazione.png");
		archivioToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				actionArchiviaMail();
			}
		});

		// Associa invio
		if (associaInvioDetailButton == null) {
			associaInvioDetailButton = new DettaglioPostaElettronicaToolStripButton(I18NUtil.getMessages().posta_elettronica_associa_invio_detail_button(),
					"buttons/addlink.png");
			associaInvioDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

				@Override
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

					Record record = new Record();
					record.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
					record.setAttribute("id", new Record(vm.getValues()).getAttribute("id"));

					AssociaInvioPopup associaPopup = new AssociaInvioPopup(null, instance, record);
					associaPopup.show();
					
					ricaricaPagina = true;
				}
			});
		}

		// Annulla archiviazione
		annullaArchiviazioneToolStripButton = new DettaglioPostaElettronicaToolStripButton(AurigaLayout.getParametroDB("LABEL_RIAPERTURA_EMAIL"),
				"archivio/annullaArchiviazione.png");
		annullaArchiviazioneToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				final RecordList listaRiapri = new RecordList();

				final Record record = new Record();
				record.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
				record.setAttribute("id", new Record(vm.getValues()).getAttribute("id"));

				listaRiapri.add(record);

				record.setAttribute("listaRecord", listaRiapri);

				openDatiOperazione(listaRiapri, false);

				if (isBozza(record)) {
					mDynamicFormBozza.setCanEdit(true);
				}
				
				ricaricaPagina = true;
			}
		});

		// Scarica
		scaricaMailProtocolloToolStripButton = new DettaglioPostaElettronicaToolStripButton(
				I18NUtil.getMessages().posta_elettronica_scarica_mail_protocollo_tool_strip_button(), "file/download_manager.png");
		scaricaMailProtocolloToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				scaricaMail();
			}
		});

		// Download
		downloadZipAllegatiToolStripButton = new DettaglioPostaElettronicaToolStripButton(I18NUtil.getMessages().posta_elettronica_list_scaricaZipAllegati(),
				"file/zip.png");
		downloadZipAllegatiToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				Record record = new Record(vm.getValues());

				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
				lGwtRestDataSource.extraparam.put("limiteMaxZipError", I18NUtil.getMessages().alert_archivio_list_limiteMaxZipError());

				lGwtRestDataSource.performCustomOperation("getZipAllegati", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record result = response.getData()[0];

							if (result.getAttribute("message") != null) {
								Layout.addMessage(new MessageBean(result.getAttribute("message"), "", MessageType.ERROR));
							} else if (result.getAttribute("errorCode") == null || result.getAttribute("errorCode").isEmpty()) {
								String uri = result.getAttribute("storageZipRemoteUri");
								String nomeFile = result.getAttribute("zipName");

								Record lRecord = new Record();
								lRecord.setAttribute("displayFilename", nomeFile);
								lRecord.setAttribute("uri", uri);
								lRecord.setAttribute("sbustato", "false");
								lRecord.setAttribute("remoteUri", true);
								DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
							}
						}
					}
				}, new DSRequest());
			}
		});

		// Salva item lavorazione
		salvaItemLavorazioneButton = new DettaglioPostaElettronicaToolStripButton("Salva", "buttons/save.png");
		salvaItemLavorazioneButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						if ((portletLayout != null) && (portletLayout.getItemLavorazioneMailItem() != null)
								&& (portletLayout.getItemLavorazioneMailItem().validate()) && (vm.getValues() != null)) {
							Record record = new Record(vm.getValues());
							// Converto in InvioMailBean
							Record inputRecord = new Record();

							if (isBozza(record)) {
								inputRecord.setAttribute("idEmail", record.getAttribute("idMail"));
							}
							inputRecord.setAttribute("aliasAccountMittente", record.getAttribute("casellaAccount"));
							inputRecord.setAttribute("idEmailPrincipale", record.getAttribute("idEmail"));
							inputRecord.setAttribute("listaItemInLavorazione", record.getAttributeAsRecordList("listaItemInLavorazione"));

							GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaSalvaIterBozzaMailDataSource");
							lGwtRestService.extraparam.put("tipoRel", tipoRel);
							lGwtRestService.extraparam.put("updateBozza", "true");
							lGwtRestService.performCustomOperation("saveItemLavorazione", inputRecord, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										ricaricaPagina = true;
										Layout.addMessage(new MessageBean("Salvataggio avvenuto con successo", "", MessageType.INFO));
										reloadDetail();
									}
								}
							}, new DSRequest());
						}
					}
				});
			}
		});

		salvaBozzaButton = new DettaglioPostaElettronicaToolStripButton("Salva", "buttons/save.png");
		salvaBozzaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				salvaBozzaButton.focusAfterGroup();
				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						saveBozza(null, "");
					}
				});
			}
		});

		// Stampa pdf email
		stampaMailToolStripButton = new DettaglioPostaElettronicaToolStripButton(I18NUtil.getMessages().posta_elettronica_stampa_pdf_mail_tool_strip_button(),
				"postaElettronica/print_file.png");
		stampaMailToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				final Record lRecord = new Record(vm.getValues());
				lRecord.setAttribute("completa", "false");
				PreviewWindowEmailPdf visualizzaMail = new PreviewWindowEmailPdf(lRecord);
				visualizzaMail.show();
			}
		});

		// FRECCIA STAMPA
		frecciaStampaToolStripButton = new FrecciaDettaglioPostaElettronicaToolStripButton();
		frecciaStampaToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				Menu menuStampa = new Menu();

				// Email completa
				MenuItem stampaPdfMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_stampaPdfMenuItem());
				stampaPdfMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						final Record lRecord = new Record(vm.getValues());
						lRecord.setAttribute("completa", "true");
						PreviewWindowEmailPdf visualizzaMail = new PreviewWindowEmailPdf(lRecord);
						visualizzaMail.show();

					}
				});
				menuStampa.addItem(stampaPdfMenuItem);

				// Email completa html
				MenuItem stampaHtmlMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_stampaHtmlMenuItem());
				stampaHtmlMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						final Record lRecord = new Record(vm.getValues());

						lRecord.setAttribute("tipo", lRecord.getAttribute("tipoEmail"));
						lRecord.setAttribute("sottotipo", lRecord.getAttribute("sottotipoEmail"));
						lRecord.setAttribute("escapedHtmlBody", lRecord.getAttribute("corpo"));
						lRecord.setAttribute("destinatariPrincipali", lRecord.getAttribute("destinatariPrimari"));
						lRecord.setAttribute("desUOAssegnataria", lRecord.getAttribute("assegnatario"));
						lRecord.setAttribute("statoInvio", lRecord.getAttribute("descrStatoInvio"));
						lRecord.setAttribute("statoConsegna", lRecord.getAttribute("descrStatoConsegna"));
						lRecord.setAttribute("statoAccettazione", lRecord.getAttribute("descrStatoAccettazione"));
						lRecord.setAttribute("flgIO", lRecord.getAttribute("flgIo"));
						lRecord.setAttribute("completa", "true");
						
						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
						lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									final Record lRecord = response.getData()[0];
									lRecord.setAttribute("completa", "true");

									String progressivo = lRecord.getAttribute("id");
									String idEmail = lRecord.getAttribute("idEmail");
									String uriFileEml = lRecord.getAttribute("uriFileEml");

									Record lRecord1 = new Record();
									lRecord1.setAttribute("progressivo", progressivo);
									lRecord1.setAttribute("idEmail", idEmail);
									lRecord1.setAttribute("uriFileEml", uriFileEml);

									String url = GWT.getHostPageBaseURL() + "springdispatcher/createpdf/getHtml";
									RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, url);
									requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");

									StringBuilder stringBuilder = new StringBuilder();
									stringBuilder.append("mimetype=text/html&")
											.append("record=" + encodeURL(JSON.encode(lRecord1.getJsObj(), new JSONEncoder())));
									try {
										requestBuilder.sendRequest(stringBuilder.toString(), new RequestCallback() {

											@Override
											public void onResponseReceived(Request request, Response response) {

												String u = URL.encode(response.getText());
												String html = GWT.getHostPageBaseURL() + "springdispatcher/stream?uri=" + u;
												
												lRecord.setAttribute("inputHtml", html);

												VisualizzaCorpoHTMLMail visualizzaCorpoMail = new VisualizzaCorpoHTMLMail(lRecord);
												visualizzaCorpoMail.show();
											}

											@Override
											public void onError(Request request, Throwable exception) {
												
											}
										});
									} catch (Exception e) {

									}
								}
							}
						}, new DSRequest());
					}
				});
				menuStampa.addItem(stampaHtmlMenuItem);

				// Solo testo messaggio
				MenuItem eccezioneMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_stampaTestoMenuItem());
				eccezioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						final Record lRecord = new Record(vm.getValues());
						lRecord.setAttribute("completa", "false");
						PreviewWindowEmailPdf visualizzaMail = new PreviewWindowEmailPdf(lRecord);
						visualizzaMail.show();
					}
				});
				menuStampa.addItem(eccezioneMenuItem);

				// Solo testo messaggio Html
				MenuItem stampaTestoHtmlMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_stampaTestoHtmlMenuItem());
				stampaTestoHtmlMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						final Record lRecord = new Record(vm.getValues());

						lRecord.setAttribute("completa", "false");

						String progressivo = lRecord.getAttribute("id");
						String idEmail = lRecord.getAttribute("idEmail");
						String uriFileEml = lRecord.getAttribute("uriFileEml");

						Record lRecord1 = new Record();
						lRecord1.setAttribute("progressivo", progressivo);
						lRecord1.setAttribute("idEmail", idEmail);
						lRecord1.setAttribute("uriFileEml", uriFileEml);

						String url = GWT.getHostPageBaseURL() + "springdispatcher/createpdf/getHtml";
						RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, url);
						requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");

						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append("mimetype=text/html&").append("record=" + encodeURL(JSON.encode(lRecord1.getJsObj(), new JSONEncoder())));
						try {
							requestBuilder.sendRequest(stringBuilder.toString(), new RequestCallback() {

								@Override
								public void onResponseReceived(Request request, Response response) {

									String u = URL.encode(response.getText());
									String html = GWT.getHostPageBaseURL() + "springdispatcher/stream?uri=" + u;

									lRecord.setAttribute("inputHtml", html);
									
									VisualizzaCorpoHTMLMail visualizzaCorpoMail = new VisualizzaCorpoHTMLMail(lRecord);
									visualizzaCorpoMail.show();
								}

								@Override
								public void onError(Request request, Throwable exception) {
						
									lRecord.setAttribute("inputHtml", lRecord.getAttribute("body"));
									VisualizzaCorpoHTMLMail visualizzaCorpoMail = new VisualizzaCorpoHTMLMail(lRecord);
									visualizzaCorpoMail.show();
								}
							});
						} catch (Exception e) {
						}
					}
				});
				menuStampa.addItem(stampaTestoHtmlMenuItem);

				// STAMPA FILE
				// Stampa tutti i file
				MenuItem stampaTuttiFileMenuItem = new MenuItem(I18NUtil.getMessages().dettaglio_posta_elettronica_stampaTuttiIFile());
				stampaTuttiFileMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						manageStampaTuttiIFile();
					}
				});

				// Stampa tutti gli allegati
				MenuItem stampaTuttiAllegatiMenuItem = new MenuItem(I18NUtil.getMessages().dettaglio_posta_elettronica_stampaTuttiAllegati());
				stampaTuttiAllegatiMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						manageStampaAllegati();
					}
				});

				// Stampa tutti i file di Appunti&Note
				MenuItem stampaFileAppuntiNoteMenuItem = new MenuItem(I18NUtil.getMessages().dettaglio_posta_elettronica_stampaTuttiFileNoteAppunti());
				stampaFileAppuntiNoteMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						manageStampaFileAppuntiNote();
					}
				});

				if (isAttivaAbilitazione("abilitaStampaFile")) {
					menuStampa.addItem(stampaTuttiFileMenuItem);
					menuStampa.addItem(stampaTuttiAllegatiMenuItem);
					menuStampa.addItem(stampaFileAppuntiNoteMenuItem);
				}

				MenuItem esportaElencoDestinatariMenuItem = new MenuItem(I18NUtil.getMessages().dettaglio_posta_elettronica_esportaDestinatari());
				esportaElencoDestinatariMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						manageEsportaElencoDestinatari();
					}
				});
				
				if(isMailUscitaO()) {
					menuStampa.addItem(esportaElencoDestinatariMenuItem);
				}
				
				menuStampa.showContextMenu();
			}
		});

		// Freccia Scarica mail
		frecciaScaricaMailProtocolloToolStripButton = new FrecciaDettaglioPostaElettronicaToolStripButton();
		frecciaScaricaMailProtocolloToolStripButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				Menu menu = new Menu();

				// Scarica e-mail
				MenuItem scaricaMailMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_scarica_mail_menu_item());
				scaricaMailMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						scaricaMail();
					}
				});
				// Scarica e-mail senza busta di trasporto
				MenuItem scaricaMailSenzaBustaPECTrasportoMenuItem = new MenuItem(
						I18NUtil.getMessages().posta_elettronica_scarica_mail_senzabusta_pec_trasporto_menu_item());
				scaricaMailSenzaBustaPECTrasportoMenuItem.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
	
						Record lRecord = new Record();
						lRecord.setAttribute("displayFilename", vm.getValue("progrDownloadStampa") + ".eml");
						lRecord.setAttribute("uri", vm.getValue("uri"));
						lRecord.setAttribute("sbustato", "false");
						lRecord.setAttribute("remoteUri", "false");
						lRecord.setAttribute("idEmail", vm.getValue("idEmail"));

						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
						lGwtRestDataSource.performCustomOperation("recuperaDatiEmlSbustato", lRecord, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Record record = response.getData()[0];
									String uri = (String) record.getAttribute("uri");

									Record lRecord = new Record();

									lRecord.setAttribute("displayFilename", vm.getValue("progrDownloadStampa") + ".eml");
									lRecord.setAttribute("uri", uri);
									lRecord.setAttribute("sbustato", "false");
									lRecord.setAttribute("remoteUri", "false");
									DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
								}
							}
						}, new DSRequest());
					}
				});
				menu.setItems(scaricaMailMenuItem, scaricaMailSenzaBustaPECTrasportoMenuItem);
				menu.showContextMenu();
			}
		});

		// BUTTONS INVIO NOTIFICA - START

		if (inviaNotificaDetailButton == null) {
			// Invia notifica
			inviaNotificaDetailButton = new DettaglioPostaElettronicaToolStripButton(I18NUtil.getMessages().posta_elettronica_invia_notifica_detail_button(),
					"postaElettronica/notifiche.png");
			inviaNotificaDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

				@Override
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

					Menu invioNotificaMenu = new Menu();

					if (isAttivaAbilitazione("abilitaNotifInteropConferma")) {
						// Abil di conferma
						MenuItem confermaMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_diconferma_menu_item(),
								"postaElettronica/notifInteropConf.png");
						confermaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								final Record listRecordInviaNotificaConfermaDetail = new Record();
								listRecordInviaNotificaConfermaDetail.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
								listRecordInviaNotificaConfermaDetail.setAttribute("uriEmail", new Record(vm.getValues()).getAttribute("uri"));
								manageInvioNotificaInteropClick(listRecordInviaNotificaConfermaDetail, "conferma");
							}
						});
						invioNotificaMenu.addItem(confermaMenuItem);
					}

					if (isAttivaAbilitazione("abilitaNotifInteropEccezione")) {
						// Abil di eccezione
						MenuItem eccezioneMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_dieccezione_menu_item(),
								"postaElettronica/notifInteropEcc.png");
						eccezioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								final Record listRecordInviaNotificaEccezioneDetail = new Record();
								listRecordInviaNotificaEccezioneDetail.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
								listRecordInviaNotificaEccezioneDetail.setAttribute("uriEmail", new Record(vm.getValues()).getAttribute("uri"));
								manageInvioNotificaInteropClick(listRecordInviaNotificaEccezioneDetail, "eccezione");
							}
						});
						invioNotificaMenu.addItem(eccezioneMenuItem);
					}

					if (isAttivaAbilitazione("abilitaNotifInteropAggiornamento")) {
						// Abil di aggiornamento
						MenuItem aggiornamentoMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_diaggiornamento_menu_item(),
								"postaElettronica/notifInteropAgg.png");
						aggiornamentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								final Record listRecordInviaNotificaAggiornamentoDetail = new Record();
								listRecordInviaNotificaAggiornamentoDetail.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
								listRecordInviaNotificaAggiornamentoDetail.setAttribute("uriEmail", new Record(vm.getValues()).getAttribute("uri"));
								manageInvioNotificaInteropClick(listRecordInviaNotificaAggiornamentoDetail, "aggiornamento");
							}
						});
						invioNotificaMenu.addItem(aggiornamentoMenuItem);
					}

					if (isAttivaAbilitazione("abilitaNotifInteropAnnullamento")) {
						// Abil di annullamento
						MenuItem annullamentoMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_diannullamento_menu_item(),
								"postaElettronica/notifInteropAnn.png");
						annullamentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								final Record listRecordInviaNotificaAnnullamentoDetail = new Record();
								listRecordInviaNotificaAnnullamentoDetail.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
								listRecordInviaNotificaAnnullamentoDetail.setAttribute("uriEmail", new Record(vm.getValues()).getAttribute("uri"));
								manageInvioNotificaInteropClick(listRecordInviaNotificaAnnullamentoDetail, "annullamento");
							}
						});
						invioNotificaMenu.addItem(annullamentoMenuItem);
					}

					invioNotificaMenu.showContextMenu();
				}
			});
		}

		// Invia notifica di conferma
		if (inviaNotificaConfermaDetailButton == null) {
			inviaNotificaConfermaDetailButton = new DettaglioPostaElettronicaToolStripButton(
					I18NUtil.getMessages().posta_elettronica_invia_notifica_conferma_detail_button(), "postaElettronica/notifInteropConf.png");
			inviaNotificaConfermaDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

				@Override
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					final Record listRecordInviaNotificaConfermaDetail = new Record();
					listRecordInviaNotificaConfermaDetail.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
					listRecordInviaNotificaConfermaDetail.setAttribute("uriEmail", new Record(vm.getValues()).getAttribute("uri"));
					manageInvioNotificaInteropClick(listRecordInviaNotificaConfermaDetail, "conferma");
				}
			});
		}

		// Invia notifica di eccezione
		if (inviaNotificaEccezioneDetailButton == null) {
			inviaNotificaEccezioneDetailButton = new DettaglioPostaElettronicaToolStripButton(
					I18NUtil.getMessages().posta_elettronica_invia_notifica_eccezione_detail_button(), "postaElettronica/notifInteropEcc.png");
			inviaNotificaEccezioneDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

				@Override
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					final Record listRecordInviaNotificaEccezioneDetail = new Record();
					listRecordInviaNotificaEccezioneDetail.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
					listRecordInviaNotificaEccezioneDetail.setAttribute("uriEmail", new Record(vm.getValues()).getAttribute("uri"));
					manageInvioNotificaInteropClick(listRecordInviaNotificaEccezioneDetail, "eccezione");
				}
			});
		}

		// Invia notifica di aggiornamento
		if (inviaNotificaAggiornamentoDetailButton == null)

		{
			inviaNotificaAggiornamentoDetailButton = new DettaglioPostaElettronicaToolStripButton(
					I18NUtil.getMessages().posta_elettronica_invia_notifica_aggiornamento_detail_button(), "postaElettronica/notifInteropAgg.png");
			inviaNotificaAggiornamentoDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

				@Override
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					final Record listRecordInviaNotificaAggiornamentoDetail = new Record();
					listRecordInviaNotificaAggiornamentoDetail.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
					listRecordInviaNotificaAggiornamentoDetail.setAttribute("uriEmail", new Record(vm.getValues()).getAttribute("uri"));
					manageInvioNotificaInteropClick(listRecordInviaNotificaAggiornamentoDetail, "aggiornamento");
				}
			});
		}

		// //Invia notifica di annullamento
		if (inviaNotificaAnnullamentoDetailButton == null) {
			inviaNotificaAnnullamentoDetailButton = new DettaglioPostaElettronicaToolStripButton(
					I18NUtil.getMessages().posta_elettronica_invia_notifica_annullamento_detail_button(), "postaElettronica/notifInteropAnn.png");
			inviaNotificaAnnullamentoDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

				@Override
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					final Record listRecordInviaNotificaAnnullamentoDetail = new Record();
					listRecordInviaNotificaAnnullamentoDetail.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
					listRecordInviaNotificaAnnullamentoDetail.setAttribute("uriEmail", new Record(vm.getValues()).getAttribute("uri"));
					manageInvioNotificaInteropClick(listRecordInviaNotificaAnnullamentoDetail, "annullamento");
				}
			});
		}

		populateDetailButtons(detailButtons, listRecord);

		return detailButtons;
	}
	
	private void onClickRilasciaContenutiMenu() {
		
		final OperazioniPerEmailPopup rilascioEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.RILASCIA.getValue(), null) {

			@Override
			public void onClickOkButton(final DSCallback callback) {
				
				ricaricaPagina = true;
				final RecordList listaEmail = new RecordList();
				Record mailRecord = new Record(vm.getValues());
				listaEmail.add(mailRecord);
				Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_rilascio_in_corso());
				rilascio(listaEmail, null, getMotivo(), false);
				markForDestroy();
			}
		};
		rilascioEmailPopup.show();
	}
	
	private void onClickInoltraContenutiMenu() {
		final Record listRecordInoltra = new Record();
		listRecordInoltra.setAttribute("allegaEmlSbustato", "false");
		listRecordInoltra.setAttribute("statoLavorazione", new Record(vm.getValues()).getAttribute("statoLavorazione"));
	
		onClickInoltraCommmon(listRecordInoltra, false);
	}
	
	private void onClickInoltraAllegandoMailSenzaBusta() {
		final Record listRecordInoltra = new Record();
		listRecordInoltra.setAttribute("allegaEmlSbustato", "true");
		
		onClickInoltraCommmon(listRecordInoltra, true);
	}
	
	private void onClickInoltraAllegandoMailOriginale() {
		final Record listRecordInoltra = new Record();
		listRecordInoltra.setAttribute("id", new Record(vm.getValues()).getAttribute("id"));
		listRecordInoltra.setAttribute("allegaEmlSbustato", "false");
		
		onClickInoltraCommmon(listRecordInoltra, true);
	}
	
	private void onClickInoltraCommmon(final Record listRecordInoltra , final boolean allegaMailOrig) {
		listRecordInoltra.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
		listRecordInoltra.setAttribute("oggetto", new Record(vm.getValues()).getAttribute("subject"));
		listRecordInoltra.setAttribute("progrDownloadStampa", new Record(vm.getValues()).getAttribute("progrDownloadStampa"));
		listRecordInoltra.setAttribute("casellaRicezione", new Record(vm.getValues()).getAttribute("casellaAccount"));
		listRecordInoltra.setAttribute("statoLavorazione", new Record(vm.getValues()).getAttribute("statoLavorazione"));
		listRecordInoltra.setAttribute("azioneDaFareBean", new Record(vm.getValues()).getAttributeAsRecord("azioneDaFareBean"));
		
		final RecordList listaEmail = new RecordList();
		listaEmail.add(listRecordInoltra);
		
		final Record record = new Record();
		record.setAttribute("listaRecord", listaEmail);
		
		final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
		lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				manageInoltraClick(listRecordInoltra, allegaMailOrig);
			}
		});
	}

	public void caricaIstanza(String nomeEntita) {

		final Record lRecord = new Record();
		lRecord.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
		lRecord.setAttribute("flgIo", new Record(vm.getValues()).getAttribute("flgIO"));
		lRecord.setAttribute("tsInvio", new Record(vm.getValues()).getAttribute("tsInvio"));
		lRecord.setAttribute("categoria", new Record(vm.getValues()).getAttribute("categoria"));
		lRecord.setAttribute("body", new Record(vm.getValues()).getAttribute("body"));
		lRecord.setAttribute("azioneDaFareBean",new Record((JavaScriptObject) vm.getValue("azioneDaFareBean")));
		lRecord.setAttribute("id", new Record(vm.getValues()).getAttribute("id"));
		lRecord.setAttribute("flgStatoProt", new Record(vm.getValues()).getAttribute("flgStatoProt"));
		
		manageRegistraIstanzaClick(lRecord, nomeEntita);
	}

	private void populateDetailButtons(List<DetailToolStripButton> detailButtons, Record record) {
		if (isBozza(record)) {
			detailButtons.add(salvaBozzaButton);
			detailButtons.add(reinviaEmailFaultToolStripButton);
			detailButtons.add(caricoToolStripButton);
			detailButtons.add(frecciaCaricoToolStripButton);
			detailButtons.add(rilasciaToolStripButton);
			// BUTTONS INVIO NOTIFICA - END

			/*
			 * E' disponibile per tutti i tipi di e-mail (sia in entrata che inviate).
			 */
			detailButtons.add(inoltraToolStripButton);

			/*
			 * In arrivo interoperabile In arrivo (se non interoperabile) Notifica interoperabile di conferma registrazione Notifica interoperabile di eccezione
			 * Notifica interoperabile di aggiornamento Notifica interoperabile di annullamento registrazione Inoltro Risposta Trasmissione documento a mezzo
			 * PEC Trasmissione documento a mezzo PEO
			 */
			detailButtons.add(frecciaInoltraToolStripButton);
			/*
			 * In arrivo interoperabile In arrivo (se non interoperabile)
			 */
			detailButtons.add(rispondiToolStripButton);
			/*
			 * In arrivo interoperabile In arrivo (se non interoperabile)
			 */
			detailButtons.add(frecciaRispondiToolStripButton);

			detailButtons.add(registraToolStripButton);

			/**
			 * Registra istanza che permette di creare, partendo dai dati della mail, una nuova istanza di autotutela o CED dei tributi.
			 */
			detailButtons.add(registraIstanzaToolStripButton);
			detailButtons.add(frecciaRegistraIstanzaToolStripButton);
			/*
			 * In arrivo interoperabile In arrivo (se non interoperabile) Notifiche e ricevute, se non ancora associate - in automatico o manualmente - alla
			 * mail “predecessore”
			 */
			detailButtons.add(assegnaToolStripButton);
			detailButtons.add(annullaAssegnaToolStripButton);
			/*
			 * Solo in caso di email in uscita
			 */
			detailButtons.add(nuovoInvioCopiaToolStripButton);
			detailButtons.add(azioneDaFareToolStripButton);
			detailButtons.add(associaProtocolloToolStripButton);

			/*
			 * 1. In arrivo interoperabile 2. In arrivo (non interoperabile) 3. Notifica automatica mail server 4. Ricevuta PEC (con dettaglio del sotto-tipo:
			 * accettazione, mancata accettazione, consegna, mancata consegna) 5. Notifica errore trasmissione/consegna (ovvero una delivery status
			 * notification) 6. Notifica interoperabile di conferma registrazione 7. Notifica interoperabile di eccezione 8. Notifica interoperabile di
			 * aggiornamento 9. Notifica interoperabile di annullamento registrazione 10. Ricevuta di avvenuta lettura
			 */
			detailButtons.add(archivioToolStripButton);
			detailButtons.add(annullaArchiviazioneToolStripButton);
			/*
			 * nel caso di mail in entrata scaricata da casella PEC
			 */
			detailButtons.add(scaricaMailProtocolloToolStripButton);
			/*
			 * E' presente solo se si tratta di mail in entrata scaricata da casella PEC
			 */
			detailButtons.add(frecciaScaricaMailProtocolloToolStripButton);
			/*
			 * Scarica lo zip degli allegati; presente se ci sono allegati
			 */
			detailButtons.add(downloadZipAllegatiToolStripButton);

			/*
			 * Genara la stampa della email in PDF (con body solotesto o strutturato secondo HTML)
			 */
			detailButtons.add(stampaMailToolStripButton);
			detailButtons.add(frecciaStampaToolStripButton);
			detailButtons.add(inviaNotificaDetailButton);
			detailButtons.add(inviaNotificaConfermaDetailButton);
			detailButtons.add(inviaNotificaEccezioneDetailButton);
			detailButtons.add(inviaNotificaAggiornamentoDetailButton);
			detailButtons.add(inviaNotificaAnnullamentoDetailButton);
			detailButtons.add(reloadToolStripButton);
			detailButtons.add(associaInvioDetailButton);

		} else {
			detailButtons.add(salvaItemLavorazioneButton);
			detailButtons.add(caricoToolStripButton);
			detailButtons.add(frecciaCaricoToolStripButton);
			detailButtons.add(rilasciaToolStripButton);
			// BUTTONS INVIO NOTIFICA - END

			/*
			 * E' disponibile per tutti i tipi di e-mail (sia in entrata che inviate).
			 */
			detailButtons.add(inoltraToolStripButton);

			/*
			 * In arrivo interoperabile In arrivo (se non interoperabile) Notifica interoperabile di conferma registrazione Notifica interoperabile di eccezione
			 * Notifica interoperabile di aggiornamento Notifica interoperabile di annullamento registrazione Inoltro Risposta Trasmissione documento a mezzo
			 * PEC Trasmissione documento a mezzo PEO
			 */
			detailButtons.add(frecciaInoltraToolStripButton);
			/*
			 * In arrivo interoperabile In arrivo (se non interoperabile)
			 */
			detailButtons.add(rispondiToolStripButton);
			/*
			 * In arrivo interoperabile In arrivo (se non interoperabile)
			 */
			detailButtons.add(frecciaRispondiToolStripButton);
			
			detailButtons.add(registraToolStripButton);
			/**
			 * Registra istanza che permette di creare, partendo dai dati della mail, una nuova istanza di autotutela o CED dei tributi.
			 */
			detailButtons.add(registraIstanzaToolStripButton);
			detailButtons.add(frecciaRegistraIstanzaToolStripButton);
			/*
			 * In arrivo interoperabile In arrivo (se non interoperabile) Notifiche e ricevute, se non ancora associate - in automatico o manualmente - alla
			 * mail “predecessore”
			 */
			detailButtons.add(assegnaToolStripButton);
			detailButtons.add(annullaAssegnaToolStripButton);
			/*
			 * Solo in caso di email in uscita
			 */
			detailButtons.add(nuovoInvioCopiaToolStripButton);
			detailButtons.add(reinviaEmailFaultToolStripButton);
			detailButtons.add(frecciaReinviaToolStripButton);
			detailButtons.add(azioneDaFareToolStripButton);
			detailButtons.add(associaProtocolloToolStripButton);

			/*
			 * 1. In arrivo interoperabile 2. In arrivo (non interoperabile) 3. Notifica automatica mail server 4. Ricevuta PEC (con dettaglio del sotto-tipo:
			 * accettazione, mancata accettazione, consegna, mancata consegna) 5. Notifica errore trasmissione/consegna (ovvero una delivery status
			 * notification) 6. Notifica interoperabile di conferma registrazione 7. Notifica interoperabile di eccezione 8. Notifica interoperabile di
			 * aggiornamento 9. Notifica interoperabile di annullamento registrazione 10. Ricevuta di avvenuta lettura
			 */
			detailButtons.add(archivioToolStripButton);
			detailButtons.add(annullaArchiviazioneToolStripButton);
			/*
			 * nel caso di mail in entrata scaricata da casella PEC
			 */
			detailButtons.add(scaricaMailProtocolloToolStripButton);
			/*
			 * E' presente solo se si tratta di mail in entrata scaricata da casella PEC
			 */
			detailButtons.add(frecciaScaricaMailProtocolloToolStripButton);
			/*
			 * Scarica lo zip degli allegati; presente se ci sono allegati
			 */
			detailButtons.add(downloadZipAllegatiToolStripButton);

			/*
			 * Genara la stampa della email in PDF (con body solotesto o strutturato secondo HTML)
			 */
			detailButtons.add(stampaMailToolStripButton);
			detailButtons.add(frecciaStampaToolStripButton);
			detailButtons.add(inviaNotificaDetailButton);
			detailButtons.add(inviaNotificaConfermaDetailButton);
			detailButtons.add(inviaNotificaEccezioneDetailButton);
			detailButtons.add(inviaNotificaAggiornamentoDetailButton);
			detailButtons.add(inviaNotificaAnnullamentoDetailButton);
			detailButtons.add(reloadToolStripButton);
			detailButtons.add(associaInvioDetailButton);
		}
	}

	protected void annullaArchiviazioneEmail(final Record record) {

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AnnullaArchiviazioneEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				annullaArchiviazione(response, record, "idEmail", "id", I18NUtil.getMessages().posta_elettronica_annulla_archiviazione_successo(),
						I18NUtil.getMessages().posta_elettronica_annulla_archiviazione_errore(), null);
			}
		});
	}
	
	public void actionArchiviaMail () {
		Record record = new Record();
		record.setAttribute("azioneDaFareBean", new Record((JavaScriptObject) vm.getValue("azioneDaFareBean")));
		record.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
		record.setAttribute("id", new Record(vm.getValues()).getAttribute("id"));
		record.setAttribute("flgIo", new Record(vm.getValues()).getAttribute("flgIO"));
		record.setAttribute("flgStatoProt", new Record(vm.getValues()).getAttribute("flgStatoProtocollazione"));

		final RecordList records = new RecordList();
		records.add(record);

		openDatiOperazione(records, true);
		
		ricaricaPagina = true;
	}

	public void openDatiOperazione(final RecordList records, final boolean isArchiviazione) {
		final Record pRecordMail = records.get(0);

		checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {

				Record esitoCheck = object.getAttributeAsRecord("esito");

				boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
				boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

				if (isLock) {
					if (isForzaLock) {
						String messaggio = esitoCheck.getAttributeAsString("errorMessage");
						Layout.showConfirmDialogWithWarning("Attenzione",messaggio, null, null, new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {

										@Override
										public void execute(Record data) {
											Map mapErrorMessages = data.getAttributeAsMap("errorMessages");
											if (mapErrorMessages != null && mapErrorMessages.size() > 0) {
												if (data.getAttribute("id") == null || data.getAttribute("id").equalsIgnoreCase("")) {
													String id = mapErrorMessages.keySet().toString().replace("[", "").replace("]", "");
													String value = "Errore: " + mapErrorMessages.get(id).toString();

													Layout.addMessage(new MessageBean(value, "", MessageType.ERROR));
												} else {
													Layout.addMessage(
															new MessageBean(mapErrorMessages.get(data.getAttribute("id")).toString(), "", MessageType.ERROR));
												}
											} else {// mail sbloccata
												DatiOperazioneRichiestaWindow operazioneRichiestaWindow = new DatiOperazioneRichiestaWindow(instance, records,
														isArchiviazione);
												operazioneRichiestaWindow.show();
											}
										}
									});
								}
							}
						});

					} // No force lock
					else {
						Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
					}
				} else {
					DatiOperazioneRichiestaWindow operazioneRichiestaWindow = new DatiOperazioneRichiestaWindow(instance, records, isArchiviazione);
					operazioneRichiestaWindow.show();
				}
			}
		});
	}

	protected void archiviaMail(final Record record) {

		record.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
		record.setAttribute("flgIo", new Record(vm.getValues()).getAttribute("flgIO"));
		record.setAttribute("flgStatoProt", new Record(vm.getValues()).getAttribute("flgStatoProtocollazione"));
		record.setAttribute("classifica", classifica);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchiviazioneEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				archiviaEmail(response, record, "idEmail", "id", I18NUtil.getMessages().posta_elettronica_archiviazione_successo(),
						I18NUtil.getMessages().posta_elettronica_archiviazione_errore(), null);
			}
		});
	}

	private VLayout createDetailSectionTabSetDatiPrincipali(Record record) {

		VLayout layoutDatiPrincipali = new VLayout();
		layoutDatiPrincipali.setHeight(5);
		layoutDatiPrincipali.setOverflow(Overflow.VISIBLE);

		detailSectionEstremi = new DetailSection(I18NUtil.getMessages().posta_elettronica_detail_section_estremi(), true, true, false, mDynamicFormEstremi);
		detailSectionEstremi.setVisible(true);
		layoutDatiPrincipali.addMember(detailSectionEstremi);

		detailAzioneDaFare = new DetailSection(I18NUtil.getMessages().posta_elettronica_detail_azione_da_fare(), true, true, false, dynamicFormAzioneDaFare);
		detailAzioneDaFare.setVisible(true);
		layoutDatiPrincipali.addMember(detailAzioneDaFare);

		detailSectionContenuti = new DetailSection(I18NUtil.getMessages().posta_elettronica_detail_section_contenuti(), true, true, false,
				mDynamicFormContenuti);
		detailSectionContenuti.setVisible(true);
		layoutDatiPrincipali.addMember(detailSectionContenuti);

		detailSectionProtocolloMitt = new DetailSection(I18NUtil.getMessages().posta_elettronica_detail_section_protocollo_mitt(), true, true, false,
				mDynamicFormProtocolloMitt);
		detailSectionProtocolloMitt.setVisible(true);
		layoutDatiPrincipali.addMember(detailSectionProtocolloMitt);

		detailSectionFileAllegati = new DetailSection(I18NUtil.getMessages().posta_elettronica_detail_section_file_allegati(), true, true, false,
				mDynamicFormFileAllegati);

		detailSectionFileAllegati.setVisible(true);
		layoutDatiPrincipali.addMember(detailSectionFileAllegati);

		detailSectionBozza = new DetailSection(I18NUtil.getMessages().posta_elettronica_detail_section_contenuti_bozza(), true, true, false, mDynamicFormBozza);

		detailSectionBozza.setVisible(true);
		layoutDatiPrincipali.addMember(detailSectionBozza);

		return layoutDatiPrincipali;
	}

	private void buildFormBozza() {
		mDynamicFormBozza = new InvioMailForm("dettaglio_email");
		mDynamicFormBozza.setValuesManager(vm);
	}

	private void buildFormEstremi() {
		mDynamicFormEstremi = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		mDynamicFormEstremi.setValuesManager(vm);
		mDynamicFormEstremi.setWidth100();
		mDynamicFormEstremi.setHeight100();
		mDynamicFormEstremi.setWrapItemTitles(false);
		mDynamicFormEstremi.setNumCols(12);
		mDynamicFormEstremi.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*");

		visualizzaCronologiaIcon = new FormItemIcon();
		visualizzaCronologiaIcon.setHeight(16);
		visualizzaCronologiaIcon.setWidth(16);
		visualizzaCronologiaIcon.setNeverDisable(true);
		visualizzaCronologiaIcon.setSrc("protocollazione/operazioniEffettuate.png");
		visualizzaCronologiaIcon.setPrompt(I18NUtil.getMessages().posta_elettronica_visualizza_cronologia_icon());
		visualizzaCronologiaIcon.setCursor(Cursor.POINTER);
		visualizzaCronologiaIcon.setAttribute("cellStyle", Styles.formCellClickable);
		visualizzaCronologiaIcon.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				String idEmail = new Record(vm.getValues()).getAttribute("idEmail");
				String title = new Record(vm.getValues()).getAttribute("titoloGUIDettaglioEmail");

				new CronologiaOperazioniEffettuateDettEmailWindow(idEmail,
						I18NUtil.getMessages().cronologiaoperazionieffettuatedettaglioEmail_window_title(title));
			}
		});

		titleItemProgressivo = new EstremiTitleStaticTextItem("N&#176;", 20);

		flgRichConfermaItem = new HiddenItem("flgRichConferma");

		flgRichConfermaLetturaItem = new HiddenItem("flgRichConfermaLettura");

		flgRichConfermaIcon = new FormItemIcon();
		flgRichConfermaIcon.setHeight(16);
		flgRichConfermaIcon.setWidth(16);
		flgRichConfermaIcon.setNeverDisable(true);
		flgRichConfermaIcon.setSrc("postaElettronica/flgConfermaProto.png");
		flgRichConfermaIcon.setPrompt("Richiesta conferma di protocollazione");

		flgRichConfermaLetturaIcon = new FormItemIcon();
		flgRichConfermaLetturaIcon.setHeight(16);
		flgRichConfermaLetturaIcon.setWidth(16);
		flgRichConfermaLetturaIcon.setNeverDisable(true);
		flgRichConfermaLetturaIcon.setSrc("postaElettronica/richConferma.png");
		flgRichConfermaLetturaIcon.setPrompt("Richiesta conferma di lettura");

		messageProgressivoItem = new EstremiStaticTextItem("id", "", visualizzaCronologiaIcon, flgRichConfermaIcon, flgRichConfermaLetturaIcon);
		messageProgressivoItem.setShowTitle(false);
		messageProgressivoItem.setWidth(1);
		messageProgressivoItem.setColSpan(3);
		messageProgressivoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				Record record = new Record(vm.getValues());
				if (record.getAttribute("id") != null && record.getAttribute("id").toUpperCase().endsWith(".B")) {
					item.setTextBoxStyle(it.eng.utility.Styles.textItemBoldOrange);
				} else if (record.getAttribute("flgIO") != null) {
					if (record.getAttribute("flgIO").equalsIgnoreCase("I")) {
						if (record.getAttribute("categoria") != null && (record.getAttribute("categoria").equalsIgnoreCase("INTEROP_SEGN")
								|| record.getAttribute("categoria").equalsIgnoreCase("PEC") || record.getAttribute("categoria").equalsIgnoreCase("ANOMALIA")
								|| record.getAttribute("categoria").equalsIgnoreCase("ALTRO"))) {
							item.setTextBoxStyle(it.eng.utility.Styles.textItemBoldGreen);
						} else {
							item.setTextBoxStyle(it.eng.utility.Styles.textItemBoldGrey);
						}
					} else if (record.getAttribute("flgIO").equalsIgnoreCase("O")) {
						if (record.getAttribute("categoria") != null && (record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ECC")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_CONF")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_AGG")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ANN"))) {
							item.setTextBoxStyle(it.eng.utility.Styles.textItemBoldGrey);
						} else {
							item.setTextBoxStyle(it.eng.utility.Styles.textItemBoldBlue);
						}
					}
				}

				List<FormItemIcon> icons = new ArrayList<FormItemIcon>();
				icons.add(visualizzaCronologiaIcon);
				if (!isBozza(record) && record.getAttributeAsString("flgRichConferma") != null && "1".equals(record.getAttributeAsString("flgRichConferma"))) {
					icons.add(flgRichConfermaIcon);
				}
				if (record.getAttributeAsString("flgRichConfermaLettura") != null && "1".equals(record.getAttributeAsString("flgRichConfermaLettura"))) {
					icons.add(flgRichConfermaLetturaIcon);
				}
				messageProgressivoItem.setIcons(icons.toArray(new FormItemIcon[0]));
				return true;
			}
		});

		SpacerItem spacerRowRiga0 = new SpacerItem();
		spacerRowRiga0.setEndRow(true);
		spacerRowRiga0.setRowSpan(1);

		SpacerItem spacer1Colon = new SpacerItem();
		spacer1Colon.setColSpan(1);
		spacer1Colon.setEndRow(true);

		/* RIGA 2 */

		iconaMicroCategoriaItem = new StaticTextItem("iconaMicroCategoria");
		iconaMicroCategoriaItem.setShowValueIconOnly(true);
		iconaMicroCategoriaItem.setShowTitle(false);
		iconaMicroCategoriaItem.setWidth(32);
		iconaMicroCategoriaItem.setHeight(32);
		iconaMicroCategoriaItem.setValueIconSize(32);
		iconaMicroCategoriaItem.setVAlign(VerticalAlignment.CENTER);
		iconaMicroCategoriaItem.setCellStyle(it.eng.utility.Styles.staticTextItem);
		iconaMicroCategoriaItem.setStartRow(true);
		iconaMicroCategoriaItem.setAlign(Alignment.LEFT);

		Map<String, String> valueIcons = new HashMap<String, String>();
		valueIcons.put("ricezione_documento", "postaElettronica/iconMilano/ricezione_documento.png");
		valueIcons.put("inoltro_mail", "postaElettronica/iconMilano/inoltro_mail.png");
		valueIcons.put("risposta_mail", "postaElettronica/iconMilano/risposta_mail.png");
		valueIcons.put("invio_documento", "postaElettronica/iconMilano/invio_documento.png");
		valueIcons.put("messaggio_di_sistema", "postaElettronica/iconMilano/messaggio_di_sistema.png");
		valueIcons.put("notifica_trasmissione", "postaElettronica/iconMilano/notifica_trasmissione.png");
		valueIcons.put("notifica_lettura", "postaElettronica/iconMilano/notifica_lettura.png");
		valueIcons.put("notifica_protocollo_in_entrata", "postaElettronica/iconMilano/notifica_protocollo_in_entrata.png");
		valueIcons.put("notifica_protocollo_in_uscita", "postaElettronica/iconMilano/notifica_protocollo_in_uscita.png");
		valueIcons.put("", "postaElettronica/iconMilano/mail.png");
		iconaMicroCategoriaItem.setValueIcons(valueIcons);
		iconaMicroCategoriaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				Record record = new Record(vm.getValues());

				String flgInteroperabile = record.getAttribute("flgInteroperabile");
				String flgPEC = record.getAttribute("flgPEC");
				String flgRicNotSenzaPredecessore = record.getAttribute("flgRicNotSenzaPredecessore");

				// Flag relativo alla posta Inoltrata
				String flgInoltrata = record.getAttribute("flgInoltrata");
				// Flag relativo alla risposta inviata
				String flgInviataRisposta = record.getAttribute("flgInviataRisposta");

				String hint = "";
				if (flgInteroperabile != null && "1".equals(flgInteroperabile)) {
					if (!"".equals(hint))
						hint += "&nbsp;";
					hint += "<img src=\"images/postaElettronica/interoperabile.png\" height=\"16\" width=\"16\" />";
				}
				if (flgPEC != null && "1".equals(flgPEC)) {
					if (!"".equals(hint))
						hint += "&nbsp;";
					hint += "<img src=\"images/coccarda.png\" height=\"16\" width=\"16\" />";

				}
				if (flgRicNotSenzaPredecessore != null && "1".equals(flgRicNotSenzaPredecessore)) {
					if (!"".equals(hint))
						hint += "&nbsp;";
					hint += "<img src=\"images/postaElettronica/flgRicNotSenzaPredecessore.png\" height=\"16\" width=\"16\" />";
				}

				/*
				 * Per l'inserimento dell'icona relativa allo stato di inoltro della mail
				 */
				if (flgInoltrata != null && "1".equals(flgInoltrata)) {
					if (!"".equals(hint))
						hint += "&nbsp;";
					hint += "<img src=\"images/postaElettronica/iconMilano/inoltro_mail.png\" height=\"16\" width=\"16\" />";
				}

				/*
				 * Per l'inserimento dell'icona relativa allo stato di risposta della mail
				 */
				if (flgInviataRisposta != null && "1".equals(flgInviataRisposta)) {
					if (!"".equals(hint))
						hint += "&nbsp;";
					hint += "<img src=\"images/postaElettronica/iconMilano/risposta_mail.png\" height=\"16\" width=\"16\" />";
				}

				iconaMicroCategoriaItem.setHint(hint);
				iconaMicroCategoriaItem.setWrapHintText(false);
				return true;
			}
		});
		iconaMicroCategoriaItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				Record record = new Record(vm.getValues());

				String iconaMicroCategoria = record.getAttribute("iconaMicroCategoria");
				String flgInteroperabile = record.getAttribute("flgInteroperabile");
				String flgPEC = record.getAttribute("flgPEC");
				String flgRicNotSenzaPredecessore = record.getAttribute("flgRicNotSenzaPredecessore");
				// Flag relativo alla posta Inoltrata
				String flgInoltrata = record.getAttribute("flgInoltrata");
				// Flag relativo alla risposta inviata
				String flgInviataRisposta = record.getAttribute("flgInviataRisposta");

				String hover = iconaMicroCategoria != null ? iconaMicroCategoria.replaceAll("_", " ") : "Mail";

				if (flgInteroperabile != null && "1".equals(flgInteroperabile)) {
					if (!"".equals(hover))
						hover += " ";

					hover += "interoperabile";
				}
				if (flgPEC != null && "1".equals(flgPEC)) {
					if (!"".equals(hover))
						hover += " ";

					hover += "certificata";
				}
				if (flgRicNotSenzaPredecessore != null && "1".equals(flgRicNotSenzaPredecessore)) {
					if (!"".equals(hover))
						hover += " ";

					hover += "orfana";
				}

				if (flgInoltrata != null && "1".equals(flgInoltrata)) {
					if (!"".equals(hover))
						hover += " ";

					hover += "Inoltrata";
				}
				if (flgInviataRisposta != null && "1".equals(flgInviataRisposta)) {
					if (!"".equals(hover))
						hover += "<br/>";

					hover += "Risposta Inviata";
				}
				return hover;
			}
		});

		tipoItem = new StaticTextItem("tipo");
		tipoItem.setShowTitle(false);
		tipoItem.setWidth(1);
		tipoItem.setRowSpan(1);
		tipoItem.setColSpan(2);
		tipoItem.setVAlign(VerticalAlignment.CENTER);
		tipoItem.setWrap(false);
		tipoItem.setValueFormatter(new FormItemValueFormatter() {

			@Override
			public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {
				String tipo = vm.getValueAsString("tipo");
				String sottotipo = vm.getValueAsString("sottotipo");
				if (sottotipo != null && !"".equals(sottotipo))
					return tipo + "<br/>" + sottotipo;
				else
					return tipo;
			}
		});

		titleItemTsInvio = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_item_ts_invio(), 150);

		tsInvioItem = new TextItem("tsInvio", I18NUtil.getMessages().posta_elettronica_ts_invio_item());
		tsInvioItem.setShowTitle(false);
		tsInvioItem.setWidth(120);

		SpacerItem spacerStatoConsolidamento = new SpacerItem();
		spacerStatoConsolidamento.setColSpan(1);
		spacerStatoConsolidamento.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrataI();
			}
		});
		spacerStatoConsolidamento.setEndRow(true);

		titleItemStatoConsolidamento = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_item_stato_consolidamento(), 100);
		titleItemStatoConsolidamento.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailUscitaO();
			}
		});

		Map<String, String> valueIconsStatiTrasm = new HashMap<String, String>();

		valueIconsStatiTrasm.put("IP", "postaElettronica/statoConsolidamento/presunto_ok.png");
		valueIconsStatiTrasm.put("OK", "postaElettronica/statoConsolidamento/consegnata.png");
		valueIconsStatiTrasm.put("W", "postaElettronica/statoConsolidamento/ko-arancione.png");
		valueIconsStatiTrasm.put("KO", "postaElettronica/statoConsolidamento/ko.png");
		valueIconsStatiTrasm.put("ND", "postaElettronica/statoConsolidamento/stato_consegna.png");

		statoInvioItem = new StaticTextItem("statoInvio");
		statoInvioItem.setShowValueIconOnly(true);
		statoInvioItem.setShowTitle(false);
		statoInvioItem.setWidth(10);
		statoInvioItem.setHeight(32);
		statoInvioItem.setValueIconSize(32);
		statoInvioItem.setVAlign(VerticalAlignment.CENTER);
		statoInvioItem.setCellStyle(it.eng.utility.Styles.staticTextItem);
		statoInvioItem.setAlign(Alignment.LEFT);
		statoInvioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record statoInvioRecord = new Record(vm.getValues());
				String stato = statoInvioRecord.getAttribute("iconaStatoInvio");
				String hint = getIconaStatoConsolidamento(stato);
				statoInvioItem.setHint(hint);
				return true;
			}
		});
		statoInvioItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String result = "";
				Record statoInvioRecord = new Record(vm.getValues());
				String stato = statoInvioRecord.getAttribute("iconaStatoInvio");
				String statoInvio = statoInvioRecord.getAttribute("statoInvio");
				String avvertimenti = statoInvioRecord.getAttributeAsString("avvertimenti") != null
						&& !"".equals(statoInvioRecord.getAttributeAsString("avvertimenti")) ? statoInvioRecord.getAttributeAsString("avvertimenti") : "";
				if ("IP".equals(stato) || "KO".equals(stato)) {
					if (!"".equals(avvertimenti)) {
						result = statoInvio + " - " + avvertimenti;
					} else
						result = statoInvio;
				} else {
					result = statoInvio;
				}
				return result;
			}
		});

		statoAccettazioneItem = new StaticTextItem("statoAccettazione");
		statoAccettazioneItem.setShowValueIconOnly(true);
		statoAccettazioneItem.setShowTitle(false);
		statoAccettazioneItem.setWidth(10);
		statoAccettazioneItem.setHeight(32);
		statoAccettazioneItem.setValueIconSize(32);
		statoAccettazioneItem.setVAlign(VerticalAlignment.CENTER);
		statoAccettazioneItem.setCellStyle(it.eng.utility.Styles.staticTextItem);
		statoAccettazioneItem.setAlign(Alignment.LEFT);
		statoAccettazioneItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String result = "";
				Record statoAccetazioneRecord = new Record(vm.getValues());
				String stato = statoAccetazioneRecord.getAttribute("iconaStatoAccettazione");
				String statoInvio = statoAccetazioneRecord.getAttribute("statoAccettazione");
				if(statoAccetazioneRecord.getAttributeAsString("msgErrMancataAccettazione") != null
						&& !"".equals(statoAccetazioneRecord.getAttributeAsString("msgErrMancataAccettazione")) && "KO".equals(stato)) {
					result = statoInvio + " - " + statoAccetazioneRecord.getAttributeAsString("msgErrMancataAccettazione");
				} else {
					result = statoInvio;
				}
				
				return result;
			}
		});
		statoAccettazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String stato = new Record(vm.getValues()).getAttribute("iconaStatoAccettazione");
				String hint = getIconaStatoConsolidamento(stato);
				statoAccettazioneItem.setHint(hint);
				return true;
			}
		});

		statoConsegnaItem = new StaticTextItem("statoConsegna");
		statoConsegnaItem.setShowValueIconOnly(true);
		statoConsegnaItem.setShowTitle(false);
		statoConsegnaItem.setWidth(10);
		statoConsegnaItem.setHeight(32);
		statoConsegnaItem.setValueIconSize(32);
		statoConsegnaItem.setVAlign(VerticalAlignment.CENTER);
		statoConsegnaItem.setCellStyle(it.eng.utility.Styles.staticTextItem);
		statoConsegnaItem.setAlign(Alignment.LEFT);
		statoConsegnaItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String result = "";
				Record statoConsegnaRecord = new Record(vm.getValues());
				String stato = statoConsegnaRecord.getAttribute("iconaStatoConsegna");
				String statoInvio = statoConsegnaRecord.getAttribute("statoConsegna");
				if(statoConsegnaRecord.getAttributeAsString("msgErrMancataConsegna") != null
						&& !"".equals(statoConsegnaRecord.getAttributeAsString("msgErrMancataConsegna")) && "KO".equals(stato)) {
					result = statoInvio + " - " + statoConsegnaRecord.getAttributeAsString("msgErrMancataConsegna");
				} else {
					result = statoInvio;
				}
				
				return result;
			}
		});
		statoConsegnaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String stato = new Record(vm.getValues()).getAttribute("iconaStatoConsegna");

				String hint = getIconaStatoConsolidamento(stato);

				statoConsegnaItem.setHint(hint);
				return true;
			}
		});

		NestedFormItem statiItem = new NestedFormItem("stati");
		statiItem.setWidth(60);
		statiItem.setNumCols(3);
		statiItem.setColWidths(16, 16, 16);
		statiItem.setColSpan(1);
		statiItem.setNestedFormItems(statoInvioItem, statoAccettazioneItem, statoConsegnaItem);

		messageIdItem = new TextItem("messageId", I18NUtil.getMessages().posta_elettronica_detail_messageIdField());
		messageIdItem.setWidth(400);
		messageIdItem.setColSpan(1);

		/* FINE STATI */

		FormItemIcon visualizzaDettDocTrasmessoIcon = new FormItemIcon();
		visualizzaDettDocTrasmessoIcon.setHeight(16);
		visualizzaDettDocTrasmessoIcon.setWidth(16);
		visualizzaDettDocTrasmessoIcon.setNeverDisable(true);
		visualizzaDettDocTrasmessoIcon.setSrc("buttons/detail.png");
		visualizzaDettDocTrasmessoIcon.setPrompt("Visualizza dettaglio documento");
		visualizzaDettDocTrasmessoIcon.setCursor(Cursor.POINTER);
		visualizzaDettDocTrasmessoIcon.setAttribute("cellStyle", Styles.formCellClickable);
		visualizzaDettDocTrasmessoIcon.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				Record detailRecord = new Record(vm.getValues());
				HashMap<String, String> mappaEstremiDocDerivati = (HashMap<String, String>) detailRecord.getAttributeAsMap("mappaEstremiDocTrasmessi");
				if (mappaEstremiDocDerivati != null && mappaEstremiDocDerivati.size() > 0) {
					if (mappaEstremiDocDerivati.size() == 1) {
						String idUd = (String) mappaEstremiDocDerivati.keySet().iterator().next();
						String estremi = (String) mappaEstremiDocDerivati.get(idUd);
						dettaglioProtocollo(idUd, estremi);
					} else {
						Menu menu = new Menu();
						for (final Object key : mappaEstremiDocDerivati.keySet()) {
							final String idUd = (String) key;
							final String estremi = (String) mappaEstremiDocDerivati.get(key);
							MenuItem menuItem = new MenuItem(estremi);
							menuItem.addClickHandler(new ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									dettaglioProtocollo(idUd, estremi);
								}
							});
							menu.addItem(menuItem);
						}
						menu.showContextMenu();
					}
				}
			}
		});

		titleItemEstremiDocTrasmessi = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_item_estremi_doc_trasmessi(), 100);
		titleItemEstremiDocTrasmessi.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailUscitaO();
			}
		});

		estremiDocTrasmessiItem = new EstremiStaticTextItem("estremiDocTrasmessi", I18NUtil.getMessages().posta_elettronica_estremi_doc_trasmessi_item(),
				visualizzaDettDocTrasmessoIcon);
		estremiDocTrasmessiItem.setShowTitle(false);
		estremiDocTrasmessiItem.setWidth(300);
		estremiDocTrasmessiItem.setColSpan(3);
		estremiDocTrasmessiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailUscitaO();
			}
		});

		// RIGA 3
		SpacerItem spacerRiga = new SpacerItem();
		spacerRiga.setWidth(15);
		spacerRiga.setColSpan(1);

		titleItemTsRicezione = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_item_ts_ricezione(), 150);
		titleItemTsRicezione.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrataI();
			}
		});

		tsRicezioneItem = new TextItem("tsInsRegistrazione", I18NUtil.getMessages().posta_elettronica_ts_ricezione_item());
		tsRicezioneItem.setShowTitle(false);
		tsRicezioneItem.setWidth(120);
		tsRicezioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrataI();
			}
		});

		titleItemCasellaAccount = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_item_casella_account(), 60);
		titleItemCasellaAccount.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrataI();
			}
		});

		casellaAccountItem = new TextItem("casellaAccount", I18NUtil.getMessages().posta_elettronica_casella_account_item());
		casellaAccountItem.setShowTitle(false);
		casellaAccountItem.setWidth(250);

		casellaAccountItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrataI();
			}
		});

		FormItemIcon visualizzaDettProtIcon = new FormItemIcon();
		visualizzaDettProtIcon.setHeight(16);
		visualizzaDettProtIcon.setWidth(16);
		visualizzaDettProtIcon.setNeverDisable(true);
		visualizzaDettProtIcon.setSrc("buttons/detail.png");
		visualizzaDettProtIcon.setPrompt(I18NUtil.getMessages().posta_elettronica_visualizza_dett_prot_icon());
		visualizzaDettProtIcon.setCursor(Cursor.POINTER);
		visualizzaDettProtIcon.setAttribute("cellStyle", Styles.formCellClickable);
		visualizzaDettProtIcon.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				Record detailRecord = new Record(vm.getValues());
				HashMap<String, String> mappaEstremiDocDerivati = (HashMap<String, String>) detailRecord.getAttributeAsMap("mappaEstremiDocDerivati");
				if (mappaEstremiDocDerivati != null && mappaEstremiDocDerivati.size() > 0) {
					if (mappaEstremiDocDerivati.size() == 1) {
						String idUd = (String) mappaEstremiDocDerivati.keySet().iterator().next();
						String estremi = (String) mappaEstremiDocDerivati.get(idUd);
						dettaglioProtocollo(idUd, estremi);
					} else {
						Menu menu = new Menu();
						for (final Object key : mappaEstremiDocDerivati.keySet()) {
							final String idUd = (String) key;
							final String estremi = (String) mappaEstremiDocDerivati.get(key);
							MenuItem menuItem = new MenuItem(estremi);
							menuItem.addClickHandler(new ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {

									dettaglioProtocollo(idUd, estremi);
								}
							});
							menu.addItem(menuItem);
						}
						menu.showContextMenu();
					}
				}
			}
		});

		SpacerItem spacerProtocollataCome = new SpacerItem();
		spacerProtocollataCome.setColSpan(4);
		spacerProtocollataCome.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrataI() && isMailProtocollata();
			}
		});
		spacerProtocollataCome.setStartRow(true);

		titleItemProtocollataCome = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_item_protocollata_come(), 100);
		titleItemProtocollataCome.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String statoProtocollazione = new Record(vm.getValues()).getAttribute("statoProtocollazione");
				if (statoProtocollazione != null && "protocollata".equals(statoProtocollazione)) {
					titleItemProtocollataCome.setDefaultValue(
							"<div><img src=\"images/postaElettronica/flgStatoProt/C.png\" size =\"16\" style=\"vertical-align: middle;\"/>&nbsp;Registrata come :</div>");
				} else if (statoProtocollazione != null && "protocollata parzialmente".equals(statoProtocollazione)) {
					titleItemProtocollataCome.setDefaultValue(
							"<div><img src=\"images/postaElettronica/flgStatoProt/P.png\" size =\"16\" style=\"vertical-align: middle;\"/>&nbsp;Registrata come :</div>");
				} else {
					titleItemProtocollataCome.setDefaultValue("Registrata come" + AurigaLayout.getSuffixFormItemTitle());
				}
				return isMailEntrataI() && isMailProtocollata();
			}
		});

		protocollataComeItem = new EstremiStaticTextItem("estremiDocDerivati", I18NUtil.getMessages().posta_elettronica_protocollata_come_item(),
				visualizzaDettProtIcon);
		protocollataComeItem.setShowTitle(false);
		protocollataComeItem.setWidth(300);
		protocollataComeItem.setColSpan(3);
		protocollataComeItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrataI() && isMailProtocollata();
			}
		});

		titleItemTsInvio.setWidth(60);
		tsInvioItem.setWidth(120);
		titleItemStatoConsolidamento.setWidth(160);

		titleItemEstremiDocTrasmessi.setWidth(120);
		estremiDocTrasmessiItem.setWidth(300);
		estremiDocTrasmessiItem.setClipValue(true);

		titleItemTsRicezione.setWidth(60);
		tsRicezioneItem.setWidth(120);
		casellaAccountItem.setWidth(240);
		titleItemProtocollataCome.setWidth(120);
		protocollataComeItem.setWidth(300);
		protocollataComeItem.setClipValue(true);

		/*
		 * Questo spacer è stato inserito per distanziare adeguatamente le label (Inviata il, Registrata il) dagli elementi alla sinistra Senza questo spacer,
		 * che è stato successivamente inserito nel form relativo, con uno zoom inferiore (<=90%) al normale gli elementi grafici vanno a sovrapporsi
		 */
		SpacerItem spacerLabel = new SpacerItem();
		spacerLabel.setWidth(45);

		mDynamicFormEstremi.setFields(flgRichConfermaItem, flgRichConfermaLetturaItem,
				/* 1a riga */
				titleItemProgressivo, messageProgressivoItem, spacerLabel, titleItemTsInvio, tsInvioItem, titleItemStatoConsolidamento, statiItem,
				messageIdItem, spacer1Colon,
				/* 2a riga */
				iconaMicroCategoriaItem, tipoItem, spacerRiga, spacerLabel, titleItemTsRicezione, tsRicezioneItem, titleItemCasellaAccount, casellaAccountItem,
				spacerStatoConsolidamento, titleItemEstremiDocTrasmessi, estremiDocTrasmessiItem,
				/* 3a riga */
				spacerProtocollataCome, spacerLabel, titleItemProtocollataCome, protocollataComeItem);

	}

	public String getIconaStatoConsolidamento(String stato) {

		String hint = "";

		if (stato != null && "OK".equals(stato)) {
			if (!"".equals(hint))
				hint += "&nbsp;";
			hint += "<img src=\"images/postaElettronica/statoConsolidamento/consegnata.png\" height=\"16\" width=\"16\" />";
		}

		if (stato != null && "IP".equals(stato)) {
			if (!"".equals(hint))
				hint += "&nbsp;";
			hint += "<img src=\"images/postaElettronica/statoConsolidamento/presunto_ok.png\" height=\"16\" width=\"16\" />";
		}
		if (stato != null && "KO".equals(stato)) {
			if (!"".equals(hint))
				hint += "&nbsp;";
			hint += "<img src=\"images/postaElettronica/statoConsolidamento/ko-red.png\" height=\"16\" width=\"16\" />";
		}

		if (stato != null && "ND".equals(stato)) {
			if (!"".equals(hint))
				hint += "&nbsp;";
			hint += "<img src=\"images/postaElettronica/statoConsolidamento/stato_consegna.png\" height=\"16\" width=\"16\" />";
		}

		if (stato != null && "W".equals(stato)) {
			if (!"".equals(hint))
				hint += "&nbsp;";
			hint += "<img src=\"images/postaElettronica/statoConsolidamento/ko-arancione.png\" height=\"16\" width=\"16\" />";
		}
		return hint;
	}

	private void buildNestedFormNotificheInteropItem() {
		nestedFormNotificheInteropItem = new NestedFormItem("nestedFormNotificheInterop");
		nestedFormNotificheInteropItem.setShowTitle(false);
		nestedFormNotificheInteropItem.setColSpan(1);
		nestedFormNotificheInteropItem.setWidth(100);
		nestedFormNotificheInteropItem.setNumCols(4);
		nestedFormNotificheInteropItem.setColWidths(25, 25, 25, 25);

		flgRicevutaConfermaInteropItem = new ImgButtonItem("flgRicevutaConfermaInterop", "postaElettronica/notifInteropConf.png",
				I18NUtil.getMessages().posta_elettronica_flg_ricevuta_conferma_interop_item());
		flgRicevutaConfermaInteropItem.setAlwaysEnabled(true);
		flgRicevutaConfermaInteropItem.setColSpan(1);
		flgRicevutaConfermaInteropItem.setIconWidth(16);
		flgRicevutaConfermaInteropItem.setIconHeight(16);
		flgRicevutaConfermaInteropItem.setIconVAlign(VerticalAlignment.BOTTOM);
		flgRicevutaConfermaInteropItem.setAlign(Alignment.LEFT);
		flgRicevutaConfermaInteropItem.setWidth(16);
		flgRicevutaConfermaInteropItem.setRedrawOnChange(true);
		flgRicevutaConfermaInteropItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("flgRicevutaConfermaInterop") != null && vm.getValueAsString("flgRicevutaConfermaInterop").equals("1");
			}
		});

		flgRicevutaEccezioneInteropItem = new ImgButtonItem("flgRicevutaEccezioneInterop", "postaElettronica/notifInteropEcc.png",
				I18NUtil.getMessages().posta_elettronica_flg_ricevuta_eccezione_interop_item());
		flgRicevutaEccezioneInteropItem.setAlwaysEnabled(true);
		flgRicevutaEccezioneInteropItem.setColSpan(1);
		flgRicevutaEccezioneInteropItem.setIconWidth(16);
		flgRicevutaEccezioneInteropItem.setIconHeight(16);
		flgRicevutaEccezioneInteropItem.setIconVAlign(VerticalAlignment.BOTTOM);
		flgRicevutaEccezioneInteropItem.setAlign(Alignment.LEFT);
		flgRicevutaEccezioneInteropItem.setWidth(16);
		flgRicevutaEccezioneInteropItem.setRedrawOnChange(true);
		flgRicevutaEccezioneInteropItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("flgRicevutaEccezioneInterop") != null && vm.getValueAsString("flgRicevutaEccezioneInterop").equals("1");
			}
		});

		flgRicevutoAggiornamentoInteropItem = new ImgButtonItem("flgRicevutoAggiornamentoInterop", "postaElettronica/notifInteropAgg.png",
				I18NUtil.getMessages().posta_elettronica_flg_ricevuto_aggiornamento_interop());
		flgRicevutoAggiornamentoInteropItem.setAlwaysEnabled(true);
		flgRicevutoAggiornamentoInteropItem.setColSpan(1);
		flgRicevutoAggiornamentoInteropItem.setIconWidth(16);
		flgRicevutoAggiornamentoInteropItem.setIconHeight(16);
		flgRicevutoAggiornamentoInteropItem.setIconVAlign(VerticalAlignment.BOTTOM);
		flgRicevutoAggiornamentoInteropItem.setAlign(Alignment.LEFT);
		flgRicevutoAggiornamentoInteropItem.setWidth(16);
		flgRicevutoAggiornamentoInteropItem.setRedrawOnChange(true);
		flgRicevutoAggiornamentoInteropItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("flgRicevutoAggiornamentoInterop") != null && vm.getValueAsString("flgRicevutoAggiornamentoInterop").equals("1");
			}
		});

		flgRicevutoAnnRegInteropItem = new ImgButtonItem("flgRicevutoAnnRegInterop", "postaElettronica/notifInteropAnn.png",
				I18NUtil.getMessages().posta_elettronica_flg_ricevuto_anno_reg_interop());
		flgRicevutoAnnRegInteropItem.setAlwaysEnabled(true);
		flgRicevutoAnnRegInteropItem.setColSpan(1);
		flgRicevutoAnnRegInteropItem.setIconWidth(16);
		flgRicevutoAnnRegInteropItem.setIconHeight(16);
		flgRicevutoAnnRegInteropItem.setIconVAlign(VerticalAlignment.BOTTOM);
		flgRicevutoAnnRegInteropItem.setAlign(Alignment.LEFT);
		flgRicevutoAnnRegInteropItem.setWidth(16);
		flgRicevutoAnnRegInteropItem.setRedrawOnChange(true);
		flgRicevutoAnnRegInteropItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("flgRicevutoAnnRegInterop") != null && vm.getValueAsString("flgRicevutoAnnRegInterop").equals("1");
			}
		});

		nestedFormNotificheInteropItem.setNestedFormItems(flgRicevutaConfermaInteropItem, flgRicevutaEccezioneInteropItem, flgRicevutoAggiornamentoInteropItem,
				flgRicevutoAnnRegInteropItem);
	}

	private void buildFormEmailPredecessore() {
		mDynamicFormEmailPredecessore = new DynamicForm();
		mDynamicFormEmailPredecessore.setValuesManager(vm);
		mDynamicFormEmailPredecessore.setWidth100();
		mDynamicFormEmailPredecessore.setHeight100();
		mDynamicFormEmailPredecessore.setWrapItemTitles(false);
		mDynamicFormEmailPredecessore.setNumCols(10);
		mDynamicFormEmailPredecessore.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		FormItemIcon visualizzaDettaglioEmailPredecessoreIcon = new FormItemIcon();
		visualizzaDettaglioEmailPredecessoreIcon.setHeight(16);
		visualizzaDettaglioEmailPredecessoreIcon.setWidth(16);
		visualizzaDettaglioEmailPredecessoreIcon.setNeverDisable(true);
		visualizzaDettaglioEmailPredecessoreIcon.setSrc("blank.png"); //TODO mettere icona? viene utilizzato?
		visualizzaDettaglioEmailPredecessoreIcon.setPrompt(I18NUtil.getMessages().posta_elettronica_visualizza_dettaglio_email_predecessore_icon());
		visualizzaDettaglioEmailPredecessoreIcon.setCursor(Cursor.POINTER);	
		visualizzaDettaglioEmailPredecessoreIcon.setAttribute("cellStyle", Styles.formCellClickable);
		visualizzaDettaglioEmailPredecessoreIcon.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				final String idEmailPredecessore = vm.getValueAsString("emailPredecessoreIdEmail");
				caricaDettaglioEmailCollegata(idEmailPredecessore);
			}
		});

		titleItemProgressivo = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_item_progressivo(), 10);

		emailPredecessoreMessageProgressivoItem = new EstremiStaticTextItem("emailPredecessoreId",
				I18NUtil.getMessages().posta_elettronica_email_predecessore_message_progressivo_item(), visualizzaDettaglioEmailPredecessoreIcon);
		emailPredecessoreMessageProgressivoItem.setShowTitle(false);
		emailPredecessoreMessageProgressivoItem.setColSpan(1);
		emailPredecessoreMessageProgressivoItem.setWidth(1);
		emailPredecessoreMessageProgressivoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				Record record = new Record(vm.getValues());
				if (record.getAttribute("emailPredecessoreId") != null && record.getAttribute("emailPredecessoreId").toUpperCase().endsWith(".B")) {
					item.setTextBoxStyle(it.eng.utility.Styles.cellTextOrangeClickable);
				} else if (record.getAttribute("emailPredecessoreFlgIO") != null) {
					if (record.getAttribute("emailPredecessoreFlgIO").equalsIgnoreCase("I")) {
						if (record.getAttribute("emailPredecessoreCategoria") != null
								&& (record.getAttribute("emailPredecessoreCategoria").equalsIgnoreCase("INTEROP_SEGN")
										|| record.getAttribute("emailPredecessoreCategoria").equalsIgnoreCase("PEC")
										|| record.getAttribute("emailPredecessoreCategoria").equalsIgnoreCase("ANOMALIA")
										|| record.getAttribute("emailPredecessoreCategoria").equalsIgnoreCase("ALTRO"))) {
							item.setTextBoxStyle(it.eng.utility.Styles.cellTextGreenClickable);
						} else {
							item.setTextBoxStyle(it.eng.utility.Styles.cellTextGreyClickable);
						}
					} else if (record.getAttribute("emailPredecessoreFlgIO").equalsIgnoreCase("O")) {
						if (record.getAttribute("emailPredecessoreCategoria") != null
								&& (record.getAttribute("emailPredecessoreCategoria").equalsIgnoreCase("INTEROP_ECC")
										|| record.getAttribute("emailPredecessoreCategoria").equalsIgnoreCase("INTEROP_CONF")
										|| record.getAttribute("emailPredecessoreCategoria").equalsIgnoreCase("INTEROP_AGG")
										|| record.getAttribute("emailPredecessoreCategoria").equalsIgnoreCase("INTEROP_ANN"))) {
							item.setTextBoxStyle(it.eng.utility.Styles.cellTextGreyClickable);
						} else {
							item.setTextBoxStyle(it.eng.utility.Styles.cellTextBlueClickable);
						}
					}
				}
				return true;
			}
		});
		emailPredecessoreMessageProgressivoItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final String idEmailPredecessore = vm.getValueAsString("emailPredecessoreIdEmail");
				caricaDettaglioEmailCollegata(idEmailPredecessore);

			}
		});

		emailPredecessoreIconaMicroCategoriaItem = new StaticTextItem("emailPredecessoreIconaMicroCategoria");
		emailPredecessoreIconaMicroCategoriaItem.setShowValueIconOnly(true);
		emailPredecessoreIconaMicroCategoriaItem.setStartRow(true);
		emailPredecessoreIconaMicroCategoriaItem.setShowTitle(false);
		emailPredecessoreIconaMicroCategoriaItem.setWidth(32);
		emailPredecessoreIconaMicroCategoriaItem.setHeight(32);
		emailPredecessoreIconaMicroCategoriaItem.setValueIconSize(32);
		emailPredecessoreIconaMicroCategoriaItem.setVAlign(VerticalAlignment.BOTTOM);
		emailPredecessoreIconaMicroCategoriaItem.setCellStyle(it.eng.utility.Styles.staticTextItem);
		emailPredecessoreIconaMicroCategoriaItem.setRowSpan(1);
		emailPredecessoreIconaMicroCategoriaItem.setColSpan(1);
		Map<String, String> valueIcons = new HashMap<String, String>();
		valueIcons.put("ricezione_documento", "postaElettronica/iconMilano/ricezione_documento.png");
		valueIcons.put("invio_documento", "postaElettronica/iconMilano/invio_documento.png");
		valueIcons.put("messaggio_di_sistema", "postaElettronica/iconMilano/messaggio_di_sistema.png");
		valueIcons.put("inoltro_mail", "postaElettronica/iconMilano/inoltro_mail.png");
		valueIcons.put("risposta_mail", "postaElettronica/iconMilano/risposta_mail.png");
		valueIcons.put("notifica_trasmissione", "postaElettronica/iconMilano/notifica_trasmissione.png");
		valueIcons.put("notifica_lettura", "postaElettronica/iconMilano/notifica_lettura.png");
		valueIcons.put("notifica_protocollo_in_entrata", "postaElettronica/iconMilano/notifica_protocollo_in_entrata.png");
		valueIcons.put("notifica_protocollo_in_uscita", "postaElettronica/iconMilano/notifica_protocollo_in_uscita.png");
		valueIcons.put("", "postaElettronica/iconMilano/mail.png");
		emailPredecessoreIconaMicroCategoriaItem.setValueIcons(valueIcons);
		emailPredecessoreIconaMicroCategoriaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String emailPredecessoreFlgInteroperabile = new Record(vm.getValues()).getAttribute("emailPredecessoreFlgInteroperabile");
				String emailPredecessoreFlgPEC = new Record(vm.getValues()).getAttribute("emailPredecessoreFlgPEC");
				String hint = "";
				if (emailPredecessoreFlgInteroperabile != null && "1".equals(emailPredecessoreFlgInteroperabile)) {
					if (!"".equals(hint))
						hint += "&nbsp;";
					hint += "<img src=\"images/postaElettronica/interoperabile.png\" height=\"16\" width=\"16\" />";
				}
				if (emailPredecessoreFlgPEC != null && "1".equals(emailPredecessoreFlgPEC)) {
					if (!"".equals(hint))
						hint += "&nbsp;";
					hint += "<img src=\"images/coccarda.png\" height=\"16\" width=\"16\" />";
				}
				emailPredecessoreIconaMicroCategoriaItem.setHint(hint);
				return true;
			}
		});
		emailPredecessoreIconaMicroCategoriaItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String emailPredecessoreIconaMicroCategoria = new Record(vm.getValues()).getAttribute("emailPredecessoreIconaMicroCategoria");
				String emailPredecessoreFlgInteroperabile = new Record(vm.getValues()).getAttribute("emailPredecessoreFlgInteroperabile");
				String emailPredecessoreFlgPEC = new Record(vm.getValues()).getAttribute("emailPredecessoreFlgPEC");
				String hover = emailPredecessoreIconaMicroCategoria != null ? emailPredecessoreIconaMicroCategoria.replaceAll("_", " ") : "Mail";
				if (emailPredecessoreFlgInteroperabile != null && "1".equals(emailPredecessoreFlgInteroperabile)) {
					if (!"".equals(hover))
						hover += " ";
					hover += "interoperabile";
				}
				if (emailPredecessoreFlgPEC != null && "1".equals(emailPredecessoreFlgPEC)) {
					if (!"".equals(hover))
						hover += " ";
					hover += "certificata";
				}
				return hover;
			}
		});

		emailPredecessoreTipoItem = new StaticTextItem("emailPredecessoreTipo");
		emailPredecessoreTipoItem.setShowTitle(false);
		emailPredecessoreTipoItem.setRowSpan(1);
		emailPredecessoreTipoItem.setColSpan(1);
		emailPredecessoreTipoItem.setVAlign(VerticalAlignment.BOTTOM);
		emailPredecessoreTipoItem.setWidth(1);
		emailPredecessoreTipoItem.setWrap(false);
		emailPredecessoreTipoItem.setValueFormatter(new FormItemValueFormatter() {

			@Override
			public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {

				String emailPredecessoreTipo = vm.getValueAsString("emailPredecessoreTipo");
				String emailPredecessoreSottotipo = vm.getValueAsString("emailPredecessoreSottotipo");
				if (emailPredecessoreSottotipo != null && !"".equals(emailPredecessoreSottotipo))
					return emailPredecessoreTipo + "<br/>" + emailPredecessoreSottotipo;
				else
					return emailPredecessoreTipo;
			}
		});

		emailPredecessoreTsInvioItem = new TextItem("emailPredecessoreTsInvio", I18NUtil.getMessages().posta_elettronica_email_predecessore_ts_invio_item());
		emailPredecessoreTsInvioItem.setWidth(100);
		emailPredecessoreTsInvioItem.setVAlign(VerticalAlignment.CENTER);

		emailPredecessoreTsRicezioneItem = new TextItem("emailPredecessoreTsRicezione",
				I18NUtil.getMessages().posta_elettronica_email_predecessore_ts_ricezione_item());
		emailPredecessoreTsRicezioneItem.setWidth(100);
		emailPredecessoreTsRicezioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				return record.getAttribute("emailPredecessoreFlgIO") != null && record.getAttribute("emailPredecessoreFlgIO").equalsIgnoreCase("I");
			}
		});

		emailPredecessoreCasellaAccountItem = new TextItem("emailPredecessoreCasellaAccount",
				I18NUtil.getMessages().posta_elettronica_email_predecessore_casella_account_item());
		emailPredecessoreCasellaAccountItem.setWidth(250);
		emailPredecessoreCasellaAccountItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				return record.getAttribute("emailPredecessoreFlgIO") != null && record.getAttribute("emailPredecessoreFlgIO").equalsIgnoreCase("I");
			}
		});

		emailPredecessoreAccountMittenteItem = new TextItem("emailPredecessoreAccountMittente",
				I18NUtil.getMessages().posta_elettronica_email_predecessore_account_mittente_item());
		emailPredecessoreAccountMittenteItem.setWidth(750);
		emailPredecessoreAccountMittenteItem.setColSpan(7);

		emailPredecessoreDestinatariPrincipaliItem = new TextItem("emailPredecessoreDestinatariPrincipali",
				I18NUtil.getMessages().posta_elettronica_email_predecessore_destinatari_principali_item());
		emailPredecessoreDestinatariPrincipaliItem.setWidth(750);
		emailPredecessoreDestinatariPrincipaliItem.setColSpan(7);

		emailPredecessoreDestinatariCCItem = new TextItem("emailPredecessoreDestinatariCC",
				I18NUtil.getMessages().posta_elettronica_email_predecessore_destinatari_cc_item());
		emailPredecessoreDestinatariCCItem.setWidth(750);
		emailPredecessoreDestinatariCCItem.setColSpan(7);

		emailPredecessoreSubjectItem = new TextAreaItem("emailPredecessoreSubject", I18NUtil.getMessages().posta_elettronica_email_predecessore_subject_item());
		emailPredecessoreSubjectItem.setWidth(750);
		emailPredecessoreSubjectItem.setHeight(30);
		emailPredecessoreSubjectItem.setColSpan(7);

		mDynamicFormEmailPredecessore.setFields(titleItemProgressivo, emailPredecessoreMessageProgressivoItem, emailPredecessoreTsInvioItem,

				emailPredecessoreIconaMicroCategoriaItem, emailPredecessoreTipoItem, emailPredecessoreTsRicezioneItem, emailPredecessoreCasellaAccountItem,
				buildSpacerItem(2, true, new FormItemIfFunction() {

					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {
						Record record = new Record(vm.getValues());
						return record.getAttribute("emailPredecessoreFlgIO") != null && record.getAttribute("emailPredecessoreFlgIO").equalsIgnoreCase("I");
					}
				}), emailPredecessoreAccountMittenteItem,

				buildSpacerItem(2, true, null), emailPredecessoreDestinatariPrincipaliItem,

				buildSpacerItem(2, true, null), emailPredecessoreDestinatariCCItem,

				buildSpacerItem(2, true, null), emailPredecessoreSubjectItem);
	}

	public SpacerItem buildSpacerItem(int colSpan, boolean startRow, FormItemIfFunction showIf) {
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setColSpan(colSpan);
		spacerItem.setStartRow(startRow);
		if (showIf != null) {
			spacerItem.setShowIfCondition(showIf);
		}
		return spacerItem;
	}

	protected void buildFormAzioneDaFare() {

		dynamicFormAzioneDaFare = new DynamicForm();
		dynamicFormAzioneDaFare.setValuesManager(vm);
		dynamicFormAzioneDaFare.setWidth100();
		dynamicFormAzioneDaFare.setHeight100();
		dynamicFormAzioneDaFare.setWrapItemTitles(false);
		dynamicFormAzioneDaFare.setNumCols(10);
		dynamicFormAzioneDaFare.setColWidths("60", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		int WHDTH = 250;

		// Stato lavorazione
		titleItemStatoLavorazione = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_item_stato_lavorazione(), 100);
		titleItemStatoLavorazione.setColSpan(1);

		// Stato lavorazione
		statoLavorazioneItem = new TextItem("statoLavorazione", I18NUtil.getMessages().posta_elettronica_stato_lavorazione_item());
		statoLavorazioneItem.setShowTitle(false);
		statoLavorazioneItem.setVAlign(VerticalAlignment.CENTER);

		statoLavorazioneItem.setColSpan(1);
		statoLavorazioneItem.setWidth(WHDTH);

		// a partire dal
		titleTsStatoLavorazioneAPartireDal = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_ts_stato_lavorazione_apartiredal(),
				10);

		// a partire dal
		dataStatoLavorazioneAPartireDal = new TextItem("dataAggStatoLavorazione",
				I18NUtil.getMessages().posta_elettronica_data_stato_lavorazione_apartiredal());
		dataStatoLavorazioneAPartireDal.setShowTitle(false);
		dataStatoLavorazioneAPartireDal.setWidth(100);

		titleOrarioStatoLavorazione = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_orario_stato_lavorazione(), 10);
		orarioStatoLavorazione = new TextItem("orarioAggStatoLavorazione", "Data stato lavorazione");
		orarioStatoLavorazione.setShowTitle(false);
		orarioStatoLavorazione.setWidth(100);

		SpacerItem spacer1Colon = new SpacerItem();
		spacer1Colon.setColSpan(1);
		spacer1Colon.setEndRow(true);

		// U.O. competente
		titleItemDesUOAssegnataria = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_item_des_uo_assegnataria(), 100);

		final FormItemIcon visualizzaMsgAssegnazioneIcon = new FormItemIcon();
		visualizzaMsgAssegnazioneIcon.setHeight(16);
		visualizzaMsgAssegnazioneIcon.setWidth(16);
		visualizzaMsgAssegnazioneIcon.setNeverDisable(true);
		visualizzaMsgAssegnazioneIcon.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				String messaggioAssegnazione = new Record(vm.getValues()).getAttribute("msgUltimaAssegnazione");
				if (messaggioAssegnazione != null && !"".equals(messaggioAssegnazione)) {
					clickPreviewMessaggioDiAssegnazioneItem();
				}
			}
		});

		// U.O competente
		desUOAssegnatariaItem = new EstremiStaticTextItem("desUOAssegnataria", I18NUtil.getMessages().posta_elettronica_des_uo_assegnataria_item(),
				visualizzaMsgAssegnazioneIcon);
		desUOAssegnatariaItem.setShowTitle(false);
		desUOAssegnatariaItem.setWidth(WHDTH);
		desUOAssegnatariaItem.setColSpan(1);
		desUOAssegnatariaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				String messaggioAssegnazione = new Record(vm.getValues()).getAttribute("msgUltimaAssegnazione");
				if (messaggioAssegnazione == null || "".equals(messaggioAssegnazione)) {
					visualizzaMsgAssegnazioneIcon.setPrompt(null);
					visualizzaMsgAssegnazioneIcon.setSrc("blank.png");
					visualizzaMsgAssegnazioneIcon.setCursor(Cursor.DEFAULT);					
					visualizzaMsgAssegnazioneIcon.setAttribute("cellStyle", Styles.formCell);
				} else if (messaggioAssegnazione != null && !"".equals(messaggioAssegnazione)) {
					visualizzaMsgAssegnazioneIcon.setPrompt(I18NUtil.getMessages().posta_elettronica_visualizza_msg_assegnazione_icon());
					visualizzaMsgAssegnazioneIcon.setSrc("buttons/view.png");
					visualizzaMsgAssegnazioneIcon.setCursor(Cursor.POINTER);					
					visualizzaMsgAssegnazioneIcon.setAttribute("cellStyle", Styles.formCell);
				}

				return true;// isMailEntrataI();
			}
		});

		// dal
		titleTsAssegnazioneDal = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_ts_assegnazione_dal(), 10);

		// Data Assegnazione dal
		tsUOAssegnazioneDal = new TextItem("dataUltimaAssegnazione", I18NUtil.getMessages().posta_elettronica_ts_uo_assegnazione_dal());
		tsUOAssegnazioneDal.setShowTitle(false);
		tsUOAssegnazioneDal.setWidth(100);

		// alle
		titleOrarioAssegnazione = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_orario_assegnazione(), 10);

		// Orario Assegnazione dal
		orarioUOAssegnazione = new TextItem("orarioUltimaAssegnazione", I18NUtil.getMessages().posta_elettronica_orario_uo_assegnazione());
		orarioUOAssegnazione.setShowTitle(false);
		orarioUOAssegnazione.setWidth(100);

		SpacerItem spacer2Colon = new SpacerItem();
		spacer2Colon.setColSpan(1);
		spacer2Colon.setWidth(120);
		spacer2Colon.setEndRow(true);

		// In carico a
		titleItemInCaricoA = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_item_incaricoa(), 110);

		// Assegnata a
		inCaricoAItem = new TextItem("desUtenteLock", I18NUtil.getMessages().posta_elettronica_incaricoa_item());
		inCaricoAItem.setShowTitle(false);
		inCaricoAItem.setWidth(WHDTH);

		// dal
		titleTsInCaricoDal = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_ts_incaricodal(), 10);

		SpacerItem spacer3Colon = new SpacerItem();
		spacer3Colon.setColSpan(1);
		spacer3Colon.setWidth(120);
		spacer3Colon.setEndRow(true);

		// Data in carico dal
		tsInCaricoDal = new TextItem("dataLock", I18NUtil.getMessages().posta_elettronica_ts_incaricodal());
		tsInCaricoDal.setShowTitle(false);
		tsInCaricoDal.setWidth(100);

		// alle
		titleOrarioInCaricoDal = new EstremiTitleStaticTextItem(I18NUtil.getMessages().posta_elettronica_title_orario_incaricodal(), 10);
		orarioInCaricoDal = new TextItem("orarioLock", I18NUtil.getMessages().posta_elettronica_orario_incaricodal());
		orarioInCaricoDal.setShowTitle(false);
		orarioInCaricoDal.setWidth(100);

		titleAzioneDaFare = new StaticTextItem();
		titleAzioneDaFare.setColSpan(1);
		titleAzioneDaFare.setWidth(60);
		titleAzioneDaFare.setDefaultValue("Azione da fare:");
		titleAzioneDaFare.setShowTitle(false);
		titleAzioneDaFare.setAlign(Alignment.RIGHT);
		titleAzioneDaFare.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleAzioneDaFare.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String valore = vm.getValueAsString("descrizioneAzioneDaFare");

				return valore != null && valore.length() > 0;
			}
		});

		codAzioneDaFareItem = new StaticTextItem("codAzioneDaFare");
		codAzioneDaFareItem.setVisible(false);
		codAzioneDaFareItem.setValueFormatter(new FormItemValueFormatter() {

			@Override
			public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {

				if (vm.getValue("azioneDaFareBean") != null)

					return (String) new Record((JavaScriptObject) vm.getValue("azioneDaFareBean")).toMap().get("codAzioneDaFare");
				else
					return null;

			}
		});

		azioneDaFareItem = new TextItem("descrizioneAzioneDaFare");
		azioneDaFareItem.setColSpan(1);
		azioneDaFareItem.setShowTitle(false);
		azioneDaFareItem.setWidth(WHDTH);
		azioneDaFareItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String valore = vm.getValueAsString("descrizioneAzioneDaFare");

				return valore != null && valore.length() > 0;
			}
		});

		titleDettaglioAzioneDaFare = new StaticTextItem();
		titleDettaglioAzioneDaFare.setColSpan(1);
		titleDettaglioAzioneDaFare.setStartRow(true);
		titleDettaglioAzioneDaFare.setWidth(60);
		titleDettaglioAzioneDaFare.setDefaultValue("Dettagli azione:");
		titleDettaglioAzioneDaFare.setShowTitle(false);
		titleDettaglioAzioneDaFare.setAlign(Alignment.RIGHT);
		titleDettaglioAzioneDaFare.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleDettaglioAzioneDaFare.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String valore = vm.getValueAsString("dettaglioAzioneDaFare");

				return valore != null && valore.length() > 0;
			}
		});

		dettaglioAzioneDaFareItem = new StaticTextAreaItem("dettaglioAzioneDaFare");
		dettaglioAzioneDaFareItem.setWidth(700);
		dettaglioAzioneDaFareItem.setColSpan(5);
		dettaglioAzioneDaFareItem.setHeight(30);
		dettaglioAzioneDaFareItem.setShowTitle(false);

		dettaglioAzioneDaFareItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String valore = vm.getValueAsString("dettaglioAzioneDaFare");

				return valore != null && valore.length() > 0;
			}
		});

		body = new HiddenItem("body");

		dynamicFormAzioneDaFare.setFields(
				/* Riga 1 */
				titleItemStatoLavorazione, statoLavorazioneItem, titleTsStatoLavorazioneAPartireDal, dataStatoLavorazioneAPartireDal,
				titleOrarioStatoLavorazione, orarioStatoLavorazione, spacer1Colon,
				/* Riga 2 */
				titleItemDesUOAssegnataria, desUOAssegnatariaItem, titleTsAssegnazioneDal, tsUOAssegnazioneDal, titleOrarioAssegnazione, orarioUOAssegnazione,
				spacer2Colon, titleItemInCaricoA, inCaricoAItem, titleTsInCaricoDal, tsInCaricoDal, titleOrarioInCaricoDal, orarioInCaricoDal, spacer3Colon,
				titleAzioneDaFare, azioneDaFareItem, titleDettaglioAzioneDaFare, dettaglioAzioneDaFareItem);

	}

//	/**
//	 * @deprecated Non più usato,accorpato in buildFormContenuti
//	 */
//	protected void buildFormAttori() {
//		mDynamicFormAttori = new DynamicForm();
//		mDynamicFormAttori.setValuesManager(vm);
//		mDynamicFormAttori.setWidth100();
//		mDynamicFormAttori.setHeight100();
//		mDynamicFormAttori.setWrapItemTitles(false);
//		mDynamicFormAttori.setNumCols(10);
//		mDynamicFormAttori.setColWidths("60", "1", "1", "1", "1", "1", "1", "1", "*", "*");
//
//		titleItemMittenteEmailInviata = new StaticTextItem();
//		titleItemMittenteEmailInviata.setColSpan(1);
//		titleItemMittenteEmailInviata.setDefaultValue("Da" + AurigaLayout.getSuffixFormItemTitle());
//		titleItemMittenteEmailInviata.setWidth(60);
//		titleItemMittenteEmailInviata.setShowTitle(false);
//		titleItemMittenteEmailInviata.setAlign(Alignment.RIGHT);
//		titleItemMittenteEmailInviata.setTextBoxStyle(it.eng.utility.Styles.formTitle);
//
//		// MITTENTE
//		mittenteEmailInviataAttoriItem = new TextItem("accountMittente", "Da");
//		mittenteEmailInviataAttoriItem.setWidth(750);
//		mittenteEmailInviataAttoriItem.setShowTitle(false);
//
//		titleItemIndirizzoDestinatariOriginali = new StaticTextItem();
//		titleItemIndirizzoDestinatariOriginali.setColSpan(1);
//		titleItemIndirizzoDestinatariOriginali.setDefaultValue("A" + AurigaLayout.getSuffixFormItemTitle());
//		titleItemIndirizzoDestinatariOriginali.setWidth(60);
//		titleItemIndirizzoDestinatariOriginali.setStartRow(true);
//		titleItemIndirizzoDestinatariOriginali.setShowTitle(false);
//		titleItemIndirizzoDestinatariOriginali.setAlign(Alignment.RIGHT);
//		titleItemIndirizzoDestinatariOriginali.setTextBoxStyle(it.eng.utility.Styles.formTitle);
//		titleItemIndirizzoDestinatariOriginali.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				return isMailEntrataI();
//			}
//		});
//
//		// DESTINATARIO
//		indirizzoDestinatariOriginaliAttoriItem = new TextAreaItem("destinatariPrincipali", "A");
//		indirizzoDestinatariOriginaliAttoriItem.setWidth(750);
//		indirizzoDestinatariOriginaliAttoriItem.setHeight(30);
//		indirizzoDestinatariOriginaliAttoriItem.setShowTitle(false);
//		indirizzoDestinatariOriginaliAttoriItem.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				return isMailEntrataI();
//			}
//		});
//
//		titleItemIndirizzoDestinatariCopia = new StaticTextItem();
//		titleItemIndirizzoDestinatariCopia.setColSpan(1);
//		titleItemIndirizzoDestinatariCopia.setDefaultValue("CC" + AurigaLayout.getSuffixFormItemTitle());
//		titleItemIndirizzoDestinatariCopia.setShowTitle(false);
//		titleItemIndirizzoDestinatariCopia.setWidth(60);
//		titleItemIndirizzoDestinatariCopia.setStartRow(true);
//		titleItemIndirizzoDestinatariCopia.setAlign(Alignment.RIGHT);
//		titleItemIndirizzoDestinatariCopia.setTextBoxStyle(it.eng.utility.Styles.formTitle);
//		titleItemIndirizzoDestinatariCopia.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				return isMailEntrataI();
//			}
//		});
//
//		// CC
//		indirizzoDestinatariCopiaAttoriItem = new TextAreaItem("destinatariCC",
//				I18NUtil.getMessages().posta_elettronica_indirizzo_destinatari_copia_attori_item());
//		indirizzoDestinatariCopiaAttoriItem.setWidth(750);
//		indirizzoDestinatariCopiaAttoriItem.setHeight(30);
//		indirizzoDestinatariCopiaAttoriItem.setShowTitle(false);
//		indirizzoDestinatariCopiaAttoriItem.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				return isMailEntrataI();
//			}
//		});
//
//		// DESTINATARI PRINCIPALI RepicableItem - A
//		destinatariPrincipaliAttoriItem = new DestinatariPrincipaliItem();
//		destinatariPrincipaliAttoriItem.setName("listaDestinatariPrincipali");
//		destinatariPrincipaliAttoriItem.setTitle(I18NUtil.getMessages().posta_elettronica_destinatari_principali_attori_item());
//		destinatariPrincipaliAttoriItem.setColSpan(7);
//		destinatariPrincipaliAttoriItem.setStartRow(true);
//		destinatariPrincipaliAttoriItem.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				RecordList listaDestinatariPrincipali = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariPrincipali");
//				return ((isMailUscitaO()) && ((listaDestinatariPrincipali != null && listaDestinatariPrincipali.getLength() > 0)));
//			}
//		});
//
//		// DESTINATARI IN COPIA RepicableItem - CC
//		destinatariCopiaAttoriItem = new DestinatariPrincipaliItem();
//		destinatariCopiaAttoriItem.setName("listaDestinatariCC");
//		destinatariCopiaAttoriItem.setTitle(I18NUtil.getMessages().posta_destinatari_copia_attori_item());
//		destinatariCopiaAttoriItem.setColSpan(7);
//		destinatariCopiaAttoriItem.setStartRow(true);
//		destinatariCopiaAttoriItem.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				RecordList listaDestinatariCC = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariCC");
//				return ((isMailUscitaO()) && ((listaDestinatariCC != null && listaDestinatariCC.getLength() > 0)));
//			}
//		});
//
//		mDynamicFormAttori.setFields(titleItemMittenteEmailInviata, mittenteEmailInviataAttoriItem, titleItemIndirizzoDestinatariOriginali,
//				indirizzoDestinatariOriginaliAttoriItem, titleItemIndirizzoDestinatariCopia, indirizzoDestinatariCopiaAttoriItem,
//				destinatariPrincipaliAttoriItem, destinatariCopiaAttoriItem);
//	}

	protected void buildFormContenuti() {

		mDynamicFormContenuti = new DynamicForm();
		mDynamicFormContenuti.setValuesManager(vm);
		mDynamicFormContenuti.setWidth100();
		mDynamicFormContenuti.setHeight100();
		mDynamicFormContenuti.setWrapItemTitles(false);
		mDynamicFormContenuti.setNumCols(10);
		mDynamicFormContenuti.setColWidths("60", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		titleItemMittenteEmailInviata = new StaticTextItem();
		titleItemMittenteEmailInviata.setColSpan(1);
		titleItemMittenteEmailInviata.setDefaultValue("Da" + AurigaLayout.getSuffixFormItemTitle());
		titleItemMittenteEmailInviata.setWidth(60);
		titleItemMittenteEmailInviata.setShowTitle(false);
		titleItemMittenteEmailInviata.setAlign(Alignment.RIGHT);
		titleItemMittenteEmailInviata.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemMittenteEmailInviata.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				return !isBozza(record);
			}
		});

		// MITTENTE - Da
		mittenteEmailInviataAttoriItem = new TextItem("accountMittente", I18NUtil.getMessages().posta_mittente_email_inviata_attori_item());
		mittenteEmailInviataAttoriItem.setWidth(750);
		mittenteEmailInviataAttoriItem.setShowTitle(false);
		mittenteEmailInviataAttoriItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				return !isBozza(record);
			}
		});

		lSelectItemMittenteBozza = new SelectItem("mittenteBozza", I18NUtil.getMessages().invionotificainteropform_mittenteItem_title());
		lSelectItemMittenteBozza.setDisplayField("value");
		lSelectItemMittenteBozza.setValueField("key");
		lSelectItemMittenteBozza.setRequired(true);
		GWTRestDataSource accounts = new GWTRestDataSource("AccountInvioEmailDatasource");
		accounts.addParam("finalita", "INVIO");
		lSelectItemMittenteBozza.setOptionDataSource(accounts);
		lSelectItemMittenteBozza.setEndRow(true);
		lSelectItemMittenteBozza.setTitleColSpan(1);
		lSelectItemMittenteBozza.setColSpan(1);
		lSelectItemMittenteBozza.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				return isBozza(record);
			}
		});
		lSelectItemMittenteBozza.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				manageAddDataArrivedSelectMittente(event);
			}
		});

		// DESTINATARIO - A
		titleItemIndirizzoDestinatariOriginali = new StaticTextItem();
		titleItemIndirizzoDestinatariOriginali.setColSpan(1);
		titleItemIndirizzoDestinatariOriginali.setDefaultValue("A" + AurigaLayout.getSuffixFormItemTitle());
		titleItemIndirizzoDestinatariOriginali.setWidth(60);
		titleItemIndirizzoDestinatariOriginali.setStartRow(true);
		titleItemIndirizzoDestinatariOriginali.setShowTitle(false);
		titleItemIndirizzoDestinatariOriginali.setAlign(Alignment.RIGHT);
		titleItemIndirizzoDestinatariOriginali.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemIndirizzoDestinatariOriginali.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				return isMailEntrataI() && !isBozza(record);
			}
		});

		indirizzoDestinatariOriginaliAttoriItem = new TextAreaItem("destinatariPrincipali",
				I18NUtil.getMessages().posta_elettronica_indirizzo_destinatari_originali_attori_item());
		indirizzoDestinatariOriginaliAttoriItem.setWidth(750);
		indirizzoDestinatariOriginaliAttoriItem.setHeight(30);
		indirizzoDestinatariOriginaliAttoriItem.setShowTitle(false);
		indirizzoDestinatariOriginaliAttoriItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				return isMailEntrataI() && !isBozza(record);
			}
		});
		

		lTextItemDestinatariBozza = new ExtendedTextItem("destinatariPrincipaliBozza", I18NUtil.getMessages().invionotificainteropform_destinatariItem_title());
		lTextItemDestinatariBozza.setColSpan(1);
		lTextItemDestinatariBozza.setWidth(480);
		lTextItemDestinatariBozza.setEndRow(true);
		lTextItemDestinatariBozza.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				return isBozza(record);
			}
		});
		CustomValidator lEmailRegExpValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				Record record = new Record(vm.getValues());
				if(isBozza(record)) {
					if (value == null || value.equals(""))
						return true;
					RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
					String lString = (String) value;
					String[] list = IndirizziEmailSplitter.split(lString);
					boolean res = true;
					for(int i=0; i < list.length; i++){
						if (list[i] == null || list[i].equals(""))
							res = res && true;
						else
							res = res && regExp.test(list[i].trim());
					}
					return res;
				}
				return true;
			}
		};
		lEmailRegExpValidator.setErrorMessage(I18NUtil.getMessages().invionotificainteropform_destinatariValidatorErrorMessage());
		lTextItemDestinatariBozza.setValidators(lEmailRegExpValidator);
		lTextItemDestinatariBozza.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				lTextItemDestinatariBozza.validate();
			}
		});
		
		
		// Destinatari CC
		
		titleItemIndirizzoDestinatariCopia = new StaticTextItem();
		titleItemIndirizzoDestinatariCopia.setColSpan(1);
		titleItemIndirizzoDestinatariCopia.setDefaultValue("CC" + AurigaLayout.getSuffixFormItemTitle());
		titleItemIndirizzoDestinatariCopia.setShowTitle(false);
		titleItemIndirizzoDestinatariCopia.setWidth(60);
		titleItemIndirizzoDestinatariCopia.setStartRow(true);
		titleItemIndirizzoDestinatariCopia.setAlign(Alignment.RIGHT);
		titleItemIndirizzoDestinatariCopia.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemIndirizzoDestinatariCopia.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				return isMailEntrataI() && !isBozza(record);
			}
		});
		
		indirizzoDestinatariCopiaAttoriItem = new TextAreaItem("destinatariCC",
				I18NUtil.getMessages().posta_elettronica_indirizzo_destinatari_coppia_attori_item());
		indirizzoDestinatariCopiaAttoriItem.setWidth(750);
		indirizzoDestinatariCopiaAttoriItem.setHeight(30);
		indirizzoDestinatariCopiaAttoriItem.setShowTitle(false);
		indirizzoDestinatariCopiaAttoriItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				return isMailEntrataI() && !isBozza(record);
			}
		});

		// DESTINATARI PRINCIPALI - A RepicableItem
//		destinatariPrincipaliAttoriItem = new DestinatariPrincipaliItem();
//		destinatariPrincipaliAttoriItem.setName("listaDestinatariPrincipali");
//		destinatariPrincipaliAttoriItem.setTitle(I18NUtil.getMessages().posta_elettronica_destinatari_principali_attori_item());
//		destinatariPrincipaliAttoriItem.setColSpan(7);
//		destinatariPrincipaliAttoriItem.setStartRow(true);
//		destinatariPrincipaliAttoriItem.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				Record record = new Record(vm.getValues());
//				RecordList listaDestinatariPrincipali = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariPrincipali");
//				return ((isMailUscitaO()) && !isBozza(record) && ((listaDestinatariPrincipali != null && listaDestinatariPrincipali.getLength() > 0)));
//			}
//		});
		destinatariPrincipaliAttoriItem = new ListaDestinatariPrincipaliItem("listaDestinatariPrincipali", I18NUtil.getMessages().posta_elettronica_destinatari_principali_attori_item());
		destinatariPrincipaliAttoriItem.setColSpan(1);
		destinatariPrincipaliAttoriItem.setWidth(748);
//		destinatariPrincipaliAttoriItem.setMaxHeight(60);
		destinatariPrincipaliAttoriItem.setStartRow(true);
		destinatariPrincipaliAttoriItem.setEndRow(false);
		destinatariPrincipaliAttoriItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				RecordList listaDestinatariPrincipali = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariPrincipali");
				return ((isMailUscitaO()) && !isBozza(record) && ((listaDestinatariPrincipali != null && listaDestinatariPrincipali.getLength() > 0)));
			}
		});

		numeroTotaleDestinatariPrincipaliAttoriItem = new TextItem("numeroTotaleDestinatariPrincipali", I18NUtil.getMessages().posta_elettronica_totale_destinatari_principali_attori_item());
		numeroTotaleDestinatariPrincipaliAttoriItem.setColSpan(1);
		numeroTotaleDestinatariPrincipaliAttoriItem.setWidth(50);
		numeroTotaleDestinatariPrincipaliAttoriItem.setVAlign(VerticalAlignment.BOTTOM);
		numeroTotaleDestinatariPrincipaliAttoriItem.setTitleVAlign(VerticalAlignment.BOTTOM);
		numeroTotaleDestinatariPrincipaliAttoriItem.setTitleStyle("formTitle labelNroTotDestinatariEmail");
		numeroTotaleDestinatariPrincipaliAttoriItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				RecordList listaDestinatariPrincipali = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariPrincipali");
				return isMailUscitaO() && ((listaDestinatariPrincipali != null && listaDestinatariPrincipali.getLength() > 0));
			}
		});
		numeroTotaleDestinatariPrincipaliAttoriItem.setCanEdit(false);
		
		// DESTINATARI IN CC RepicableItem
//		destinatariCopiaAttoriItem = new DestinatariPrincipaliItem();
//		destinatariCopiaAttoriItem.setName("listaDestinatariCC");
//		destinatariCopiaAttoriItem.setTitle(I18NUtil.getMessages().posta_destinatari_copia_attori_item());
//		destinatariCopiaAttoriItem.setColSpan(7);
//		destinatariCopiaAttoriItem.setStartRow(true);
//		destinatariCopiaAttoriItem.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				Record record = new Record(vm.getValues());
//				RecordList listaDestinatariCC = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariCC");
//				return ((isMailUscitaO()) && !isBozza(record) && ((listaDestinatariCC != null && listaDestinatariCC.getLength() > 0)));
//			}
//		});
		destinatariCopiaAttoriItem = new ListaDestinatariPrincipaliItem("listaDestinatariCC", I18NUtil.getMessages().posta_destinatari_copia_attori_item());
		destinatariCopiaAttoriItem.setColSpan(1);
		destinatariCopiaAttoriItem.setWidth(748);
//		destinatariCopiaAttoriItem.setMaxHeight(60);
		destinatariCopiaAttoriItem.setStartRow(true);
		destinatariCopiaAttoriItem.setEndRow(false);
		destinatariCopiaAttoriItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				RecordList listaDestinatariCC = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariCC");
				return ((isMailUscitaO()) && !isBozza(record) && ((listaDestinatariCC != null && listaDestinatariCC.getLength() > 0)));
			}
		});
		
		numeroTotaleDestinatariCopiaAttoriItem = new TextItem("numeroTotaleDestinatariCopia", I18NUtil.getMessages().posta_destinatari_totale_copia_attori_item());
		numeroTotaleDestinatariCopiaAttoriItem.setColSpan(1);
		numeroTotaleDestinatariCopiaAttoriItem.setWidth(50);
		numeroTotaleDestinatariCopiaAttoriItem.setVAlign(VerticalAlignment.BOTTOM);
		numeroTotaleDestinatariCopiaAttoriItem.setTitleVAlign(VerticalAlignment.BOTTOM);
		numeroTotaleDestinatariCopiaAttoriItem.setTitleStyle("formTitle labelNroTotDestinatariEmail");
		numeroTotaleDestinatariCopiaAttoriItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				RecordList listaDestinatariCC = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariCC");
				return isMailUscitaO() && ((listaDestinatariCC != null && listaDestinatariCC.getLength() > 0));
			}
		});
		numeroTotaleDestinatariCopiaAttoriItem.setCanEdit(false);
		
		// DESTINATARI IN CCN RepicableItem
//		destinatariCCNAttoriItem = new DestinatariPrincipaliItem();
//		destinatariCCNAttoriItem.setTitle(I18NUtil.getMessages().posta_destinatari_copia_ccn_attori_item());
//		destinatariCCNAttoriItem.setName("listaDestinatariCCN");
//		destinatariCCNAttoriItem.setColSpan(7);
//		destinatariCCNAttoriItem.setStartRow(true);
//		destinatariCCNAttoriItem.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				Record record = new Record(vm.getValues());
//				RecordList listaDestinatariCCN = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariCCN");
//				return ((isMailUscitaO()) && !isBozza(record) && ((listaDestinatariCCN != null && listaDestinatariCCN.getLength() > 0)))
//						&& (!isMittentePec());
//			}
//		});
		destinatariCCNAttoriItem = new ListaDestinatariPrincipaliItem("listaDestinatariCCN", I18NUtil.getMessages().posta_destinatari_copia_ccn_attori_item());
		destinatariCCNAttoriItem.setColSpan(1);
		destinatariCCNAttoriItem.setWidth(748);
//		destinatariCCNAttoriItem.setMaxHeight(60);
		destinatariCCNAttoriItem.setStartRow(true);
		destinatariCCNAttoriItem.setEndRow(false);
		destinatariCCNAttoriItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				RecordList listaDestinatariCCN = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariCCN");
				return ((isMailUscitaO()) && !isBozza(record) && ((listaDestinatariCCN != null && listaDestinatariCCN.getLength() > 0)))
						&& (!isMittentePec());
			}
		});

		numeroTotaleDestinatariCCNAttoriItem = new TextItem("numeroTotaleDestinatariCCNAttori", I18NUtil.getMessages().posta_destinatari_totale_copia_ccn_attori_item());
		numeroTotaleDestinatariCCNAttoriItem.setColSpan(1);
		numeroTotaleDestinatariCCNAttoriItem.setWidth(50);
		numeroTotaleDestinatariCCNAttoriItem.setVAlign(VerticalAlignment.BOTTOM);
		numeroTotaleDestinatariCCNAttoriItem.setTitleVAlign(VerticalAlignment.BOTTOM);
		numeroTotaleDestinatariCCNAttoriItem.setTitleStyle("formTitle labelNroTotDestinatariEmail");
		numeroTotaleDestinatariCCNAttoriItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				RecordList listaDestinatariCCN = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariCCN");
				return isMailUscitaO() && ((listaDestinatariCCN != null && listaDestinatariCCN.getLength() > 0));
			}
		});
		numeroTotaleDestinatariCCNAttoriItem.setCanEdit(false);
		
		// Destinatari CC Bozza
		lTextItemDestinatariCCBozza = new ExtendedTextItem("destinatariCCBozza", I18NUtil.getMessages().invionotificainteropform_destinatariCCItem_title());
		lTextItemDestinatariCCBozza.setColSpan(1);
		lTextItemDestinatariCCBozza.setWidth(480);
		lTextItemDestinatariCCBozza.setEndRow(true);
		lTextItemDestinatariCCBozza.setShowIfCondition(new FormItemIfFunction() {
		
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				Record record = new Record(vm.getValues());
				return isBozza(record);
			}
		});

		titleItemOggettoDatiProtocollo = new StaticTextItem();
		titleItemOggettoDatiProtocollo.setColSpan(1);
		titleItemOggettoDatiProtocollo.setStartRow(true);
		titleItemOggettoDatiProtocollo.setWidth(60);
		titleItemOggettoDatiProtocollo.setDefaultValue("Oggetto" + AurigaLayout.getSuffixFormItemTitle());
		titleItemOggettoDatiProtocollo.setShowTitle(false);
		titleItemOggettoDatiProtocollo.setAlign(Alignment.RIGHT);
		titleItemOggettoDatiProtocollo.setTextBoxStyle(it.eng.utility.Styles.formTitle);

		// Oggetto
		oggettoDatiProtocolloContenutiItem = new TextItem("subject", I18NUtil.getMessages().posta_elettronica_oggetto_dati_protocollo_contenuti_item());
		oggettoDatiProtocolloContenutiItem.setWidth(750);
		oggettoDatiProtocolloContenutiItem.setShowTitle(false);
		
		
		// Box di messaggio errore Ricevuta PEC
		msgErrRicevutaPECItem = new TextAreaItem("msgErrRicevutaPEC",I18NUtil.getMessages().posta_elettronica_messaggio_errore_ricevuta_pec_item());
		msgErrRicevutaPECItem.setColSpan(1);
		msgErrRicevutaPECItem.setWidth(750);
		msgErrRicevutaPECItem.setHeight(30);
		msgErrRicevutaPECItem.setStartRow(true);
		msgErrRicevutaPECItem.setEndRow(true);
		msgErrRicevutaPECItem.setShowTitle(true);
		msgErrRicevutaPECItem.setShowIfCondition(new FormItemIfFunction() {
		
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				Record record = new Record(vm.getValues());
				String msgErrRicevutaPEC = record.getAttribute("msgErrRicevutaPEC");
				if(msgErrRicevutaPEC != null && !"".equals(msgErrRicevutaPEC)) {
					return true;
				} else {
					return false;
				}
			}
		});

		// CORPO MAIL - Corpo
		titleItemCorpoMail = new StaticTextItem();
		titleItemCorpoMail.setColSpan(1);
		titleItemCorpoMail.setStartRow(true);
		titleItemCorpoMail.setWidth(60);
		titleItemCorpoMail.setDefaultValue("Corpo" + AurigaLayout.getSuffixFormItemTitle());
		titleItemCorpoMail.setShowTitle(false);
		titleItemCorpoMail.setAlign(Alignment.RIGHT);
		titleItemCorpoMail.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemCorpoMail.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());

				String body = record.getAttribute("escapedHtmlBody");
				String motivoEccezione = record.getAttribute("motivoEccezione");

				if (motivoEccezione != null && motivoEccezione.length() > 0) {
					return !(body == null || (body != null && body.length() == 0));
				} else
					return true;

			}
		});
		
		corpoMailContenutiItem = new TextAreaItem("escapedHtmlBody", I18NUtil.getMessages().posta_elettronica_corpo_mail_contenuti_item()) {
			
			@Override
			public void manageOnViewerClick(TextAreaItem item) {
				
				manageOnViewerClickCorpoMailContenuti();
			}
		};
		corpoMailContenutiItem.setWidth(750);
		corpoMailContenutiItem.setShowTitle(false);
		corpoMailContenutiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());

				String body = record.getAttribute("escapedHtmlBody");
				String me = record.getAttribute("motivoEccezione");

				if (me != null && me.length() > 0) {
					return !(body == null || (body != null && body.length() == 0));
				} else
					return true;
			}
		});

		body = new HiddenItem("body");

		titleItemMotivoEccezione = new StaticTextItem();
		titleItemMotivoEccezione.setColSpan(1);
		titleItemMotivoEccezione.setStartRow(true);
		titleItemMotivoEccezione.setWidth(60);
		titleItemMotivoEccezione.setDefaultValue("Motivo Eccezione:");
		titleItemMotivoEccezione.setShowTitle(false);
		titleItemMotivoEccezione.setAlign(Alignment.RIGHT);
		titleItemMotivoEccezione.setTextBoxStyle(it.eng.utility.Styles.formTitle);

		titleItemMotivoEccezione.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());

				String me = record.getAttribute("motivoEccezione");

				return me != null && me.length() > 0;
			}
		});

		motivoEccezioneContenutiItem = new TextAreaItem("motivoEccezione", I18NUtil.getMessages().posta_elettronica_corpo_mail_contenuti_item());
		motivoEccezioneContenutiItem.setWidth(750);
		motivoEccezioneContenutiItem.setShowTitle(false);
		motivoEccezioneContenutiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());

				String me = record.getAttribute("motivoEccezione");

				return me != null && me.length() > 0;
			}
		});		

		mDynamicFormContenuti.setFields(titleItemMittenteEmailInviata, mittenteEmailInviataAttoriItem, lSelectItemMittenteBozza,

				titleItemIndirizzoDestinatariOriginali, indirizzoDestinatariOriginaliAttoriItem, lTextItemDestinatariBozza,

				titleItemIndirizzoDestinatariCopia, indirizzoDestinatariCopiaAttoriItem,
				
				lTextItemDestinatariCCBozza,

				destinatariPrincipaliAttoriItem, numeroTotaleDestinatariPrincipaliAttoriItem, destinatariCopiaAttoriItem, numeroTotaleDestinatariCopiaAttoriItem,
				
				destinatariCCNAttoriItem, numeroTotaleDestinatariCCNAttoriItem, 
				
				msgErrRicevutaPECItem, 
				
				titleItemOggettoDatiProtocollo, oggettoDatiProtocolloContenutiItem,
				
				titleItemCorpoMail, corpoMailContenutiItem, body, titleItemMotivoEccezione, motivoEccezioneContenutiItem
		);
	}
	
	public void manageOnViewerClickCorpoMailContenuti() {
		
		final Record lRecord = new Record(vm.getValues());
	
		lRecord.setAttribute("completa", "false");
	
		String progressivo = lRecord.getAttribute("id");
		String idEmail = lRecord.getAttribute("idEmail");
		String uriFileEml = lRecord.getAttribute("uriFileEml");
	
		Record lRecord1 = new Record();
		lRecord1.setAttribute("progressivo", progressivo);
		lRecord1.setAttribute("idEmail", idEmail);
		lRecord1.setAttribute("uriFileEml", uriFileEml);
	
		String url = GWT.getHostPageBaseURL() + "springdispatcher/createpdf/getHtml";
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, url);
		requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");
	
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("mimetype=text/html&").append("record=" + encodeURL(JSON.encode(lRecord1.getJsObj(), new JSONEncoder())));
		try {
			requestBuilder.sendRequest(stringBuilder.toString(), new RequestCallback() {
	
				@Override
				public void onResponseReceived(Request request, Response response) {
					
					String u = URL.encode(response.getText());
					String html = GWT.getHostPageBaseURL() + "springdispatcher/stream?uri=" + u;
					lRecord.setAttribute("inputHtml", html);
					VisualizzaCorpoHTMLMail visualizzaCorpoMail = new VisualizzaCorpoHTMLMail(lRecord);
					visualizzaCorpoMail.show();
				}
	
				@Override
				public void onError(Request request, Throwable exception) {
					
					lRecord.setAttribute("inputHtml", lRecord.getAttribute("body"));
					VisualizzaCorpoHTMLMail visualizzaCorpoMail = new VisualizzaCorpoHTMLMail(lRecord);
					visualizzaCorpoMail.show();
				}
			});
		} catch (Exception e) {
		}
	}

	protected void buildFormProtocolloMitt() {

		mDynamicFormProtocolloMitt = new DynamicForm();
		mDynamicFormProtocolloMitt.setValuesManager(vm);
		mDynamicFormProtocolloMitt.setWidth100();
		mDynamicFormProtocolloMitt.setHeight100();
		mDynamicFormProtocolloMitt.setWrapItemTitles(false);
		mDynamicFormProtocolloMitt.setNumCols(20);
		mDynamicFormProtocolloMitt.setColWidths("60", "50", "1", "50", "1", "50", "1", "50", "1", "50", "1", "50", "1", "50", "1", "50", "1", "50", "*", "*");

		titleEnteProtocollo = new StaticTextItem();
		titleEnteProtocollo.setColSpan(1);
		titleEnteProtocollo.setStartRow(true);
		titleEnteProtocollo.setWidth(60);
		titleEnteProtocollo.setDefaultValue("Da" + AurigaLayout.getSuffixFormItemTitle());
		titleEnteProtocollo.setShowTitle(false);
		titleEnteProtocollo.setAlign(Alignment.RIGHT);
		titleEnteProtocollo.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleEnteProtocollo.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrataI() && isCategoriaInteropSegn();
			}
		});

		// Da
		enteProtocolloItem = new TextItem("enteRegMitt", I18NUtil.getMessages().posta_elettronica_ente_protocollo_item());
		enteProtocolloItem.setWidth(280);
		enteProtocolloItem.setColSpan(1);
		enteProtocolloItem.setShowTitle(false);
		enteProtocolloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrataI() && isCategoriaInteropSegn();
			}
		});

		// Prot. mittente
		registroProtocolloItem = new TextItem("siglaRegistroRegMitt", I18NUtil.getMessages().posta_elettronica_registro_protocollo_item());
		registroProtocolloItem.setWidth(120);
		registroProtocolloItem.setColSpan(1);
		registroProtocolloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrataI() && isCategoriaInteropSegn();
			}
		});

		// N°
		numeroProtocolloItem = new TextItem("numRegMitt", I18NUtil.getMessages().posta_elettronica_numero_protocollo_item());
		numeroProtocolloItem.setWidth(100);
		numeroProtocolloItem.setColSpan(1);
		numeroProtocolloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrataI() && isCategoriaInteropSegn();
			}
		});

		// del
		dataRegMittProtocolloItem = new TextItem("dtRegMitt", I18NUtil.getMessages().posta_elettronica_data_reg_mitt_protocollo_item());
		dataRegMittProtocolloItem.setWidth(80);
		dataRegMittProtocolloItem.setColSpan(1);
		dataRegMittProtocolloItem.setValueFormatter(new FormItemValueFormatter() {

			@Override
			public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {

				String stringValue = (String) value;
				return stringValue != null && !"".equals(stringValue) ? stringValue.substring(0, 10) : "";
			}
		});
		dataRegMittProtocolloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrataI() && isCategoriaInteropSegn();
			}
		});

		titleOggettoMailProtocollo = new StaticTextItem();
		titleOggettoMailProtocollo.setColSpan(1);
		titleOggettoMailProtocollo.setStartRow(true);
		titleOggettoMailProtocollo.setWidth(60);
		titleOggettoMailProtocollo.setDefaultValue("Oggetto" + AurigaLayout.getSuffixFormItemTitle());
		titleOggettoMailProtocollo.setShowTitle(false);
		titleOggettoMailProtocollo.setAlign(Alignment.RIGHT);
		titleOggettoMailProtocollo.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleOggettoMailProtocollo.setStartRow(true);

		// Oggetto
		oggettoMailProtocolloItem = new TextAreaItem("oggettoRegMitt", I18NUtil.getMessages().posta_elettronica_oggetto_mail_protocollo_item());
		oggettoMailProtocolloItem.setWidth(750);
		oggettoMailProtocolloItem.setHeight(30);
		oggettoMailProtocolloItem.setColSpan(16);
		oggettoMailProtocolloItem.setShowTitle(false);
		oggettoMailProtocolloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isMailEntrataI() && isCategoriaInteropSegn();
			}
		});

		mDynamicFormProtocolloMitt.setFields(titleEnteProtocollo, enteProtocolloItem, registroProtocolloItem, numeroProtocolloItem, dataRegMittProtocolloItem,
				titleOggettoMailProtocollo, oggettoMailProtocolloItem);
	}

	protected void buildFormFileAllegati() {
		mDynamicFormFileAllegati = new DynamicForm();
		mDynamicFormFileAllegati.setValuesManager(vm);
		mDynamicFormFileAllegati.setWrapItemTitles(false);

		fileAllegatiItem = new FileAllegatiEmailItem() {

			public void downloadFile(final ServiceCallback<Record> lDsCallback) {

				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource");
				lGwtRestDataSource.executecustom("retrieveAttach", getDetailRecord(), new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS)
							realEditRecord(response.getData()[0]);

						lDsCallback.execute(response.getData()[0]);
					}
				});
			};

			@Override
			public boolean showStampaFileButton() {
				return isAttivaAbilitazione("abilitaStampaFile");
			}
		};
		fileAllegatiItem.setName("listaAllegati");
		fileAllegatiItem.setShowTitle(false);
		fileAllegatiItem.setAttribute("closeViewer", nomeEntita);
		
		labelPregressoItem = new StaticTextItem();
		labelPregressoItem.setName("messagePregresso");
		labelPregressoItem.setShowTitle(false);
		labelPregressoItem.setStartRow(true);
		labelPregressoItem.setWrap(false);
		labelPregressoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equalsIgnoreCase((String)value);
			}
		});

		mDynamicFormFileAllegati.setFields(fileAllegatiItem,labelPregressoItem);
	}

	/**
	 * Costruisce la custom list contenente le email correlate quando l'email di partenza è di tipo "in entrata"
	 */
	protected void buildListaEmailCollegateMailEntrata() {

		listaEmailCollegateMailEntrata = new MailEntrataMailCorrelateList(this);
	}

	protected String iconHtml(String src) {
		return "<div style=\"cursor:pointer\" align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";
	}

	protected Canvas createExpansionComponentDestinatari(ListGridRecord record) {

		/*
		 * LISTA EMAIL SUCCESSIVE COLLEGATE
		 */
		HLayout orizzontaleSinistro = new HLayout();

		VLayout layout = new VLayout(5);
		layout.setPadding(5);
		layout.setHeight(2);
		layout.setOverflow(Overflow.VISIBLE);
		final CustomList listaDestinatari = new CustomList("listaDestinatari") {

			@Override
			public boolean isDisableRecordComponent() {
				return true;
			};
		};
		orizzontaleSinistro.setWidth100();

		listaDestinatari.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		listaDestinatari.setWidth100();
		listaDestinatari.setBorder("0px");
		listaDestinatari.setHeight(1);
		listaDestinatari.setShowAllRecords(true);
		listaDestinatari.setBodyOverflow(Overflow.VISIBLE);
		listaDestinatari.setOverflow(Overflow.VISIBLE);
		listaDestinatari.setLeaveScrollbarGap(false);
		listaDestinatari.setCanGroupBy(false);
		listaDestinatari.setShowHeaderContextMenu(false);

		// ID EMAIL Id
		ListGridField idEmail = new ListGridField("idEmail", I18NUtil.getMessages().posta_elettronica_id_email_list());
		idEmail.setHidden(true);

		// TIPO DESTINATARIO Tipo
		final ListGridField tipoDest = new ListGridField("tipoDest", I18NUtil.getMessages().posta_elettronica_tipo_dest());
		tipoDest.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		tipoDest.setType(ListGridFieldType.ICON);
		tipoDest.setAlign(Alignment.CENTER);
		tipoDest.setWrap(false);

		tipoDest.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String tipoDest = record.getAttribute("tipoDest");
				if (tipoDest != null && tipoDest.equalsIgnoreCase("P")) {
					return iconHtml("anagrafiche/rubrica_email/tipoIndirizzo/S.png");
				} else if (tipoDest != null && tipoDest.equalsIgnoreCase("CC")) {
					return iconHtml("anagrafiche/rubrica_email/tipoIndirizzo/G.png");
				} else if (tipoDest != null && tipoDest.equalsIgnoreCase("CCN")) {
					return iconHtml("anagrafiche/rubrica_email/tipoIndirizzo/A.png");
				}
				return null;
			}
		});
		tipoDest.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String tipoDest = record.getAttribute("tipoDest");
				if (tipoDest != null && tipoDest.equalsIgnoreCase("P")) {
					return "Destinatario principale";
				} else if (tipoDest != null && tipoDest.equalsIgnoreCase("CC")) {
					return "Destinatario copia conoscenza";
				} else if (tipoDest != null && tipoDest.equalsIgnoreCase("CCN")) {
					return "Destinatario copia nascosta";
				}
				return null;
			}
		});
		tipoDest.setWidth(200);
		// DESTINATARIO - Destinatario
		ListGridField indirizzoDest = new ListGridField("indirizzoDest", I18NUtil.getMessages().posta_elettronica_indirizzo_dest());
		indirizzoDest.setIcon("public.png");

		// STATO - Stato
		ListGridField statoConsolidamentoDest = new ListGridField("iconaStatoConsegnaDestinatario",
				I18NUtil.getMessages().posta_elettronica_stato_consolidamento_dest());
		statoConsolidamentoDest.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoConsolidamentoDest.setType(ListGridFieldType.ICON);
		statoConsolidamentoDest.setAlign(Alignment.CENTER);
		statoConsolidamentoDest.setWrap(false);

		statoConsolidamentoDest.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String stato = record.getAttribute("iconaStatoConsegnaDestinatario");

				if (stato != null && "OK".equals(stato)) {
					return iconHtml("postaElettronica/statoConsolidamento/consegnata.png");
				}

				if (stato != null && "IP".equals(stato)) {
					return iconHtml("postaElettronica/statoConsolidamento/presunto_ok.png");
				}

				if (stato != null && "KO".equals(stato)) {
					return iconHtml("postaElettronica/statoConsolidamento/ko-red.png");
				}

				if (stato != null && "ND".equals(stato)) {
					return iconHtml("postaElettronica/statoConsolidamento/stato_consegna.png");
				}

				if (stato != null && "W".equals(stato)) {
					return iconHtml("postaElettronica/statoConsolidamento/ko-arancione.png");
				}

				return null;
			}
		});
		statoConsolidamentoDest.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute("statoConsegnaDestinatario");
			}
		});

		statoConsolidamentoDest.setWidth(200);
		// PERVENUTA RICEVUTA DI AVVENUTA LETTURA
		// ListGridField flgRicevutaLettura = new ListGridField("flgRicevutaLettura", "Ricevuta lettura");
		ListGridField flgRicevutaLettura = new ListGridField("flgRicevutaLettura", I18NUtil.getMessages().posta_elettronica_flg_ricevuta_lettura());
		flgRicevutaLettura.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgRicevutaLettura.setType(ListGridFieldType.ICON);
		flgRicevutaLettura.setAlign(Alignment.CENTER);
		flgRicevutaLettura.setWrap(false);

		flgRicevutaLettura.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flgRicevutaLettura") != null && record.getAttribute("flgRicevutaLettura").equals("1")) {
					// return iconHtml("anagrafiche/mail/pervenuta _ricevuta_avvenuta_lettura.png");
					return iconHtml("anagrafiche/postaElettronica/iconMilano/notifica_lettura.png");
				}
				return null;
			}
		});
		flgRicevutaLettura.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flgRicevutaLettura") != null && record.getAttribute("flgRicevutaLettura").equals("1")) {
					return "Pervenuta ricevuta di avvenuta lettura";
				}
				return null;
			}
		});
		flgRicevutaLettura.setWidth(200);

		ListGridField buttonsField = new ListGridField("buttons");
		buttonsField.setAlign(Alignment.CENTER);
		buttonsField.setWidth(20);
		buttonsField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		listaDestinatari.setFields(tipoDest, indirizzoDest, statoConsolidamentoDest, flgRicevutaLettura);

		Record detailRecord = new Record(vm.getValues());
		RecordList listaEmailSuccessiveCollegate = detailRecord.getAttributeAsRecordList("listaEmailSuccessiveCollegate");
		RecordList listaEmailSuccessiveCollegate2Liv = new RecordList();
		if (listaEmailSuccessiveCollegate != null) {

			for (int i = 0; i < listaEmailSuccessiveCollegate.getLength(); i++) {
				if (listaEmailSuccessiveCollegate.get(i).getAttribute("flgRigaEmailVsDest").equals("2")
						&& listaEmailSuccessiveCollegate.get(i).getAttribute("idEmail").equalsIgnoreCase(record.getAttribute("idEmail"))) {
					listaEmailSuccessiveCollegate2Liv.add(listaEmailSuccessiveCollegate.get(i));
				}
			}
			listaDestinatari.setData(listaEmailSuccessiveCollegate2Liv);
		}
		if (listaEmailSuccessiveCollegate2Liv.getLength() > 0) {
			orizzontaleSinistro.addMember(listaDestinatari);
			layout.addMember(orizzontaleSinistro);
		} else {
			// Se non ci sono destinatario nn espando
			layout = null;
		}
		return layout;
	}

	/**
	 * Costruisce la lista delle email correlate quando l'email di partenza è di tipo in uscita
	 */
	private void buildListaEmailCollegateMailUscita() {

		listaEmailCollegateMailUscita = new MailUscitaMailCorrelateList(this);
	}

	protected void manageInoltraClick(final Record pRecordMail, final boolean allegaMailOrig) {

		final String utentelock = pRecordMail.getAttribute("desUtenteLock");

		checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {

				Record esitoCheck = object.getAttributeAsRecord("esito");

				boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
				boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

				if (isLock) {
					if (isForzaLock) {
						String messaggio = esitoCheck.getAttributeAsString("errorMessage");
						Layout.showConfirmDialogWithWarning("Attenzione",messaggio, null, null, new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {

										@Override
										public void execute(Record data) {
											Map errorMessages = data.getAttributeAsMap("errorMessages");

											if (errorMessages != null && errorMessages.size() > 0) {

												String error = data.getAttribute("id") + ": " + errorMessages.get(data.getAttribute("idEmail"));

												Layout.addMessage(new MessageBean(error, "", MessageType.ERROR));
											} else {

												InoltroMailWindow lInoltroEmailWindow = new InoltroMailWindow(
														allegaMailOrig ? "inoltroAllegaMailOrig" : "inoltro", pRecordMail, instance, new DSCallback() {

															@Override
															public void execute(DSResponse response, Object rawData, DSRequest request) {
																ricaricaPagina = true;
																reloadDetail();
															}
														});
											}
										}
									});
								}
							}
						});
					} // No force lock
					else {
						Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
					}
				} else {
					InoltroMailWindow lInoltroEmailWindow = new InoltroMailWindow(allegaMailOrig ? "inoltroAllegaMailOrig" : "inoltro", pRecordMail, instance,
							new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									ricaricaPagina = true;
									reloadDetail();
								}
							});
				}

			}
		});
	}

	protected void manageNuovoInvioCopiaClick(Record pRecord, boolean allegaMailOrig) {
		new NuovoInvioMailWindow("InvioNuovoMessaggioCopia", pRecord, "Nuovo invio e-mail", "NIC", instance, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				ricaricaPagina = true;
				reloadDetail();
			}
		});
	}

	protected void manageReinvioEmailClick(Record pRecord, boolean allegaMailOrig) {
		new NuovoInvioMailWindow("InvioNuovoMessaggioCopia", pRecord, "Re-invio e-mail", "RI", instance, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				ricaricaPagina = true;
				reloadDetail();
			}
		});
	}

	protected void manageAzioneDaFareClick(final Record pRecord) {

		checkLockEmail(pRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {

				Record esitoCheck = object.getAttributeAsRecord("esito");

				boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
				boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

				if (isLock) {
					if (isForzaLock) {
						String messaggio = esitoCheck.getAttributeAsString("errorMessage");
						Layout.showConfirmDialogWithWarning("Attenzione",messaggio, null, null, new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									sbloccaEmail(pRecord, new ServiceCallback<Record>() {

										@Override
										public void execute(Record data) {
											Map errorMessages = data.getAttributeAsMap("errorMessages");

											if (errorMessages != null && errorMessages.size() > 0) {

												String error = data.getAttribute("id") + ": " + errorMessages.get(data.getAttribute("idEmail"));

												Layout.addMessage(new MessageBean(error, "", MessageType.ERROR));
											} else {

												final RecordList recordList = new RecordList();

												pRecord.setAttribute("codAzioneDaFare", ((Map) pRecord.toMap().get("azioneDaFareBean")).get("codAzioneDaFare"));
												pRecord.setAttribute("azioneDaFare", ((Map) pRecord.toMap().get("azioneDaFareBean")).get("azioneDaFare"));
												pRecord.setAttribute("dettaglioAzioneDaFare",
														((Map) pRecord.toMap().get("azioneDaFareBean")).get("dettaglioAzioneDaFare"));

												recordList.add(pRecord);

												SelezionaAzioneDaFareWindow azioneDaFareWindow = new SelezionaAzioneDaFareWindow(false, recordList, null,
														instance) {
												};
												azioneDaFareWindow.show();
												ricaricaPagina = true;
											}
										}
									});
								}
							}
						});
					} // No force lock
					else {
						Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
					}
				} else {
					final RecordList recordList = new RecordList();

					pRecord.setAttribute("codAzioneDaFare", ((Map) pRecord.toMap().get("azioneDaFareBean")).get("codAzioneDaFare"));
					pRecord.setAttribute("azioneDaFare", ((Map) pRecord.toMap().get("azioneDaFareBean")).get("azioneDaFare"));
					pRecord.setAttribute("dettaglioAzioneDaFare", ((Map) pRecord.toMap().get("azioneDaFareBean")).get("dettaglioAzioneDaFare"));

					recordList.add(pRecord);

					SelezionaAzioneDaFareWindow azioneDaFareWindow = new SelezionaAzioneDaFareWindow(false, recordList, null, instance) {
					};
					azioneDaFareWindow.show();
					ricaricaPagina = true;
				}
			}
		});

	}
	
	private MenuItem buildRispondiConAllegati() {
		
		Menu rispondiMenu = new Menu();
		MenuItem rispondiMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_rispondi_menu_item());
		
		MenuItem standard = new MenuItem(I18NUtil.getMessages().posta_elettronica_rispondi_standard_submenu_item());
		standard.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
			
				buildRispondiToolStripButton(false);
			}
		});
		
		MenuItem conAllegati = new MenuItem(I18NUtil.getMessages().posta_elettronica_rispondi_allegati_submenu_item());
		conAllegati.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				
				buildRispondiToolStripButton(true);
			}
		});
		
		rispondiMenu.setItems(standard,conAllegati);
		rispondiMenuItem.setSubmenu(rispondiMenu);
		
		return rispondiMenuItem;
	}
	
	private MenuItem buildRispondiATuttiConAllegati() {
		
		Menu rispondiATuttiMenu = new Menu();
		MenuItem rispondiATuttiMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_rispondi_a_tutti_menu_item());
		
		MenuItem standard = new MenuItem(I18NUtil.getMessages().posta_elettronica_rispondi_a_tutti_standard_submenu_item());
		standard.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
			
				buildRispondiATuttiToolStripButton(false);
			}
		});
		
		MenuItem conAllegati = new MenuItem(I18NUtil.getMessages().posta_elettronica_rispondi_a_tutti_allegati_submenu_item());
		conAllegati.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				
				buildRispondiATuttiToolStripButton(true);
			}
		});
		
		rispondiATuttiMenu.setItems(standard,conAllegati);
		rispondiATuttiMenuItem.setSubmenu(rispondiATuttiMenu);
		
		return rispondiATuttiMenuItem;
		
	}
	
	private void buildRispondiToolStripButton(final Boolean richiediAllegati) {
		
		Record record = new Record();
		final RecordList listaEmail = new RecordList();

		final Record listRecordRispondi = new Record();
		listRecordRispondi.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
		listRecordRispondi.setAttribute("oggetto", new Record(vm.getValues()).getAttribute("subject"));
		listRecordRispondi.setAttribute("accountMittente", new Record(vm.getValues()).getAttribute("accountMittente"));
		listRecordRispondi.setAttribute("destinatariPrimari", new Record(vm.getValues()).getAttribute("destinatariPrincipali"));
		listRecordRispondi.setAttribute("destinatariCC", new Record(vm.getValues()).getAttribute("destinatariCC"));
		listRecordRispondi.setAttribute("corpo", new Record(vm.getValues()).getAttribute("body"));
		listRecordRispondi.setAttribute("tsInvio", new Record(vm.getValues()).getAttribute("tsInvio"));
		listRecordRispondi.setAttribute("casellaRicezione", new Record(vm.getValues()).getAttribute("casellaAccount"));
		listRecordRispondi.setAttribute("statoLavorazione", new Record(vm.getValues()).getAttribute("statoLavorazione"));
		listRecordRispondi.setAttribute("azioneDaFareBean", new Record(vm.getValues()).getAttributeAsRecord("azioneDaFareBean"));
		listRecordRispondi.setAttribute("statoLavorazione", new Record(vm.getValues()).getAttribute("statoLavorazione"));

		listaEmail.add(listRecordRispondi);
		record.setAttribute("listaRecord", listaEmail);
		final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
		lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				manageRispondiClick(listRecordRispondi, richiediAllegati);
			}
		});
	}
	
	private void buildRispondiATuttiToolStripButton(final Boolean richiediAllegati) {
		
		Record record = new Record();
		final RecordList listaEmail = new RecordList();

		final Record listRecordRispondi = new Record();
		listRecordRispondi.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
		listRecordRispondi.setAttribute("oggetto", new Record(vm.getValues()).getAttribute("subject"));
		listRecordRispondi.setAttribute("accountMittente", new Record(vm.getValues()).getAttribute("accountMittente"));
		listRecordRispondi.setAttribute("destinatariPrimari", new Record(vm.getValues()).getAttribute("destinatariPrincipali"));
		listRecordRispondi.setAttribute("destinatariCC", new Record(vm.getValues()).getAttribute("destinatariCC"));
		listRecordRispondi.setAttribute("corpo", new Record(vm.getValues()).getAttribute("body"));
		listRecordRispondi.setAttribute("tsInvio", new Record(vm.getValues()).getAttribute("tsInvio"));
		listRecordRispondi.setAttribute("casellaRicezione", new Record(vm.getValues()).getAttribute("casellaAccount"));
		listRecordRispondi.setAttribute("azioneDaFareBean", new Record(vm.getValues()).getAttributeAsRecord("azioneDaFareBean"));
		listRecordRispondi.setAttribute("statoLavorazione", new Record(vm.getValues()).getAttribute("statoLavorazione"));

		listaEmail.add(listRecordRispondi);
		record.setAttribute("listaRecord", listaEmail);
		final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
		lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				manageRispondiATuttiClick(listRecordRispondi, richiediAllegati);
			}
		});
	}

	protected void manageRispondiClick(final Record pRecordMail,final Boolean richiediAllegati) {

		checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {

				Record esitoCheck = object.getAttributeAsRecord("esito");

				boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
				boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

				if (isLock) {
					if (isForzaLock) {
						String messaggio = esitoCheck.getAttributeAsString("errorMessage");
						Layout.showConfirmDialogWithWarning("Attenzione",messaggio, null, null, new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {

										@Override
										public void execute(Record data) {
											Map errorMessages = data.getAttributeAsMap("errorMessages");

											if (errorMessages != null && errorMessages.size() > 0) {

												String error = data.getAttribute("id") + ": " + errorMessages.get(data.getAttribute("idEmail"));

												Layout.addMessage(new MessageBean(error, "", MessageType.ERROR));
											} else {// mail sbloccata
												pRecordMail.setAttribute("richiediAllegati", richiediAllegati);
												RispostaMailWindow lRispostaEmailWindow = new RispostaMailWindow("risposta", true, null, pRecordMail, instance,
														new DSCallback() {

															@Override
															public void execute(DSResponse response, Object rawData, DSRequest request) {
																ricaricaPagina = true;
																reloadDetail();
															}
														});
											}
										}
									});
								}
							}
						});
					} // No force lock
					else {
						Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
					}
				} else {
					pRecordMail.setAttribute("richiediAllegati", richiediAllegati);
					RispostaMailWindow lRispostaEmailWindow = new RispostaMailWindow("risposta", true, null, pRecordMail, instance, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							ricaricaPagina = true;
							reloadDetail();
						}
					});
				}
			}
		});
	}

	protected void manageRispondiATuttiClick(final Record pRecordMail, final Boolean richiediAllegati) {
		checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {

				Record esitoCheck = object.getAttributeAsRecord("esito");

				boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
				boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

				if (isLock) {
					if (isForzaLock) {
						String messaggio = esitoCheck.getAttributeAsString("errorMessage");
						Layout.showConfirmDialogWithWarning("Attenzione",messaggio, null, null, new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {

										@Override
										public void execute(Record data) {
											Map errorMessages = data.getAttributeAsMap("errorMessages");

											if (errorMessages != null && errorMessages.size() > 0) {

												String error = data.getAttribute("id") + ": " + errorMessages.get(data.getAttribute("idEmail"));

												Layout.addMessage(new MessageBean(error, "", MessageType.ERROR));
											} else {// mail sbloccata
												pRecordMail.setAttribute("richiediAllegati", richiediAllegati);
												RispostaMailWindow lRispostaEmailWindow = new RispostaMailWindow("risposta", false,
														pRecordMail.getAttribute("casellaRicezione"), pRecordMail, instance, new DSCallback() {

															@Override
															public void execute(DSResponse response, Object rawData, DSRequest request) {

																reloadDetail();
															}
														});
											}
										}
									});
								}
							}
						});
					} // No force lock
					else {
						Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
					}
				} else {
					pRecordMail.setAttribute("richiediAllegati", richiediAllegati);
					RispostaMailWindow lRispostaEmailWindow = new RispostaMailWindow("risposta", false, pRecordMail.getAttribute("casellaRicezione"),
							pRecordMail, instance, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {

									reloadDetail();
								}
							});
				}
			}
		});
	}

	public void reloadDetail() {

		Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_caricamento_dettaglio_mail());
		Record lRecord = new Record(vm.getValues());
		final String idEmail = vm.getValueAsString("idEmail");
		lRecord.setAttribute("idEmail", idEmail);
		lRecord.setAttribute("indexTab", indexTab);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
		lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];

					String i = record.getAttribute("indexTab");
					if (i != null)
						indexTab = Integer.parseInt(i);

					editRecordFromLivello(record);

					tabSetGenerale.selectTab(record.getAttribute("indexTab"));
				}

				Layout.hideWaitPopup();
			}
		}, new DSRequest());
	}

	protected void manageAssegnaClick(final Record pRecord, final RecordList listaDestinatariPreferiti) {
		final AssegnazioneEmailPopup assegnazioneEmailPopup = new AssegnazioneEmailPopup(pRecord,listaDestinatariPreferiti) {

			@Override
			public void onClickOkButton(Record record, final DSCallback callback) {
				
				final RecordList listaEmail = new RecordList();
				listaEmail.add(pRecord);
				record.setAttribute("listaRecord", listaEmail);
				
				Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_assegnazione_mail());

				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneEmailDataSource");
				try {
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							ricaricaPagina = true;
							operationCallback(response, pRecord, "idEmail", "Assegnazione effettuata con successo",
									"Si è verificato un errore durante l'assegnazione!", callback);

						}

					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}
			}
		};
		assegnazioneEmailPopup.show();
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
			reloadDetail();
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
	
	@Override
	public void editRecord(Record record) {
		editRecord(record, false);
	}

	public void editRecordFromLivello(Record record) {
		editRecord(record, false);
	}

	private Record populateFormBozza(Record record) {

		Record lRecord = new Record();
		Record obj = record.getAttributeAsRecord("invioMailBean");

		lRecord.setAttribute("idEmail", obj.getAttribute("idEmail"));
		lRecord.setAttribute("mittente", obj.getAttribute("mittente"));
		lRecord.setAttribute("aliasAccountMittente", obj.getAttribute("mittente"));
		lRecord.setAttribute("destinatari", obj.getAttribute("destinatari"));
		lRecord.setAttribute("destinatariCC", obj.getAttribute("destinatariCC"));
		lRecord.setAttribute("destinatariCCN", obj.getAttribute("destinatariCCN"));
		lRecord.setAttribute("oggetto", obj.getAttribute("oggetto"));
		if (obj.getAttribute("bodyHtml") != null && !"".equals(obj.getAttribute("bodyHtml"))) {
			lRecord.setAttribute("bodyHtml", obj.getAttribute("bodyHtml"));
			lRecord.setAttribute("textHtml", "html");
		} else {
			lRecord.setAttribute("bodyText", obj.getAttribute("bodyText"));
			lRecord.setAttribute("textHtml", "text");
		}
		lRecord.setAttribute("confermaLettura", obj.getAttribute("confermaLettura"));
		lRecord.setAttribute("uriMail", obj.getAttribute("uriMail"));
		lRecord.setAttribute("dimensioneMail", obj.getAttribute("dimensioneMail"));
		lRecord.setAttribute("interoperabile", obj.getAttribute("interoperabile"));
		lRecord.setAttribute("attach", obj.getAttributeAsRecordList("attach"));
		lRecord.setAttribute("confermaLettura", obj.getAttribute("confermaLettura"));
		lRecord.setAttribute("casellaIsPec", obj.getAttribute("casellaIsPec"));

		return lRecord;
	}

	public void editRecord(Record record, boolean fromLivello) {
		super.editRecord(record);

		RecordList livelliPredecessori = record.getAttributeAsRecordList("listaEmailPrecedenti");

		bricioleDiPaneToolStrip.setLivelli(livelliPredecessori, record.getAttribute("idEmail"));

		fileAllegatiItem.setDetailRecord(record);
		if ((portletLayout != null) && (portletLayout.getItemLavorazioneMailItem() != null)) {
			portletLayout.getItemLavorazioneMailItem().setDetailRecord(record);
		}
		destinatariPrincipaliAttoriItem.setDetailRecord(record);
		destinatariCopiaAttoriItem.setDetailRecord(record);
		destinatariCCNAttoriItem.setDetailRecord(record);

		detailSectionEstremi.openIfhasValue();
		detailSectionContenuti.openIfhasValue();
		detailAzioneDaFare.openIfhasValue();

		detailSectionFileAllegati.hide();
		
		if(record.getAttributeAsString("messagePregresso") != null && !"".equalsIgnoreCase(record.getAttributeAsString("messagePregresso"))) {
			detailSectionFileAllegati.show();
			detailSectionFileAllegati.open();			
		} else {		
			if (openDetailSectionFileAllegati()) {
				detailSectionFileAllegati.show();
				detailSectionFileAllegati.openIfhasValue();
			}
		}

		detailSectionProtocolloMitt.hide();
		if (openDetailSectionProtocollo()) {
			detailSectionProtocolloMitt.show();
			detailSectionProtocolloMitt.openIfhasValue();
		}

		// l'email di partenza è in entrata, quindi mostro la tabella con il relativo layout
		if (record.getAttribute("flgIO") != null && record.getAttribute("flgIO").equals("I")) {

			// tabella delle email correlate da usare quando l'email di partenza è di tipo in ingresso
			listaEmailCollegateMailEntrata.show();
			listaEmailCollegateMailUscita.hide();

			// lista contenente tutte le email correlate
			RecordList listaEmailSuccessiveCollegate = record.getAttributeAsRecordList("listaEmailSuccessiveCollegate");

			if (listaEmailSuccessiveCollegate != null) {
				RecordList listaEmailSuccessiveCollegate1Liv = new RecordList();
				for (int i = 0; i < listaEmailSuccessiveCollegate.getLength(); i++) {

					// lista di secondo livello espandibile
					if (listaEmailSuccessiveCollegate.get(i).getAttribute("flgRigaEmailVsDest").equals("1")) {
						listaEmailSuccessiveCollegate1Liv.add(listaEmailSuccessiveCollegate.get(i));
					}
				}
				listaEmailCollegateMailEntrata.setData(listaEmailSuccessiveCollegate1Liv);
			} else {
				listaEmailCollegateMailEntrata.setData(new RecordList());
			}
		} else if (record.getAttribute("flgIO") != null && record.getAttribute("flgIO").equals("O")) {
			listaEmailCollegateMailEntrata.hide();
			listaEmailCollegateMailUscita.show();

			RecordList listaEmailSuccessiveCollegate = record.getAttributeAsRecordList("listaEmailSuccessiveCollegate");

			if (listaEmailSuccessiveCollegate != null) {

				RecordList listaEmailSuccessiveCollegate1Liv = new RecordList();
				for (int i = 0; i < listaEmailSuccessiveCollegate.getLength(); i++) {

					// lista di secondo livello espandibile
					if (listaEmailSuccessiveCollegate.get(i).getAttribute("flgRigaEmailVsDest").equals("1")) {
						listaEmailSuccessiveCollegate1Liv.add(listaEmailSuccessiveCollegate.get(i));
					}
				}
				listaEmailCollegateMailUscita.setData(listaEmailSuccessiveCollegate1Liv);
			} else {
				listaEmailCollegateMailUscita.setData(new RecordList());
			}
		}

		if (isAttivaAbilitazione("abilitaInoltraEmail", record)) {
			inoltraToolStripButton.show();
			if (isAttivaAbilitazione("abilitaInoltraContenuti", record)) {
				frecciaInoltraToolStripButton.show();
			} else {
				frecciaInoltraToolStripButton.hide();
			}
		} else {
			inoltraToolStripButton.hide();
			frecciaInoltraToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaRispondi", record)) {
			rispondiToolStripButton.show();
			if (isAttivaAbilitazione("abilitaRispondiATutti", record)) {
				frecciaRispondiToolStripButton.show();
			} else {
				frecciaRispondiToolStripButton.hide();
			}
		} else {
			rispondiToolStripButton.hide();
			frecciaRispondiToolStripButton.hide();
		}

		//Se è abilitato alla protocollazione e/o alla registrazione a repertorio mostra il Bottone Registra
		if(isAttivaAbilitazione("abilitaRepertoria", record) || isAttivaAbilitazione("abilitaProtocolla", record)) {
			registraToolStripButton.show();
		} else {
			registraToolStripButton.hide();
		}

		boolean isAbilitaRegIstanzaAutotutela = isAttivaAbilitazione("abilitaRegIstanzaAutotutela", record);
		boolean isAbilitaRegIstanzaCED = isAttivaAbilitazione("abilitaRegIstanzaCED", record);
		if (isAbilitaRegIstanzaAutotutela || isAbilitaRegIstanzaCED) {
			registraIstanzaToolStripButton.show();
			registraIstanzaToolStripButton.setTitle(I18NUtil.getMessages().posta_elettronica_button_registra_istanza());
			registraIstanzaToolStripButton.setIcon("menu/istanze.png");
			if (isAbilitaRegIstanzaAutotutela && isAbilitaRegIstanzaCED) {
				frecciaRegistraIstanzaToolStripButton.show();
			} else {
				frecciaRegistraIstanzaToolStripButton.hide();
				if (isAbilitaRegIstanzaAutotutela) {
					registraIstanzaToolStripButton.setTitle(I18NUtil.getMessages().posta_elettronica_button_registra_istanza() + " "
							+ I18NUtil.getMessages().posta_elettronica_button_registra_istanza_autotutela());
					registraIstanzaToolStripButton.setIcon("menu/istanze_autotutela.png");
				} else if (isAbilitaRegIstanzaCED) {
					registraIstanzaToolStripButton.setTitle(I18NUtil.getMessages().posta_elettronica_button_registra_istanza() + " "
							+ I18NUtil.getMessages().posta_elettronica_button_registra_istanza_ced());
					registraIstanzaToolStripButton.setIcon("menu/istanze_ced.png");
				}
			}
		} else {
			registraIstanzaToolStripButton.hide();
			frecciaRegistraIstanzaToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaAzioneDaFare", record)) {
			azioneDaFareToolStripButton.show();
		} else {
			azioneDaFareToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaInvioCopia", record)) {
			nuovoInvioCopiaToolStripButton.show();
		} else {
			nuovoInvioCopiaToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaInvia", record)) {
			if (isBozza(record)) {
				reinviaEmailFaultToolStripButton.setTitle("Invia");
				frecciaReinviaToolStripButton.hide();
			} else {
				frecciaReinviaToolStripButton.show();
			}

			reinviaEmailFaultToolStripButton.show();
		} else {
			frecciaReinviaToolStripButton.hide();
			reinviaEmailFaultToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaAssegna", record)) {
			assegnaToolStripButton.show();
		} else {
			assegnaToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaAnnullamentoInvio", record)) {
			annullaAssegnaToolStripButton.show();
		} else {
			annullaAssegnaToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaAssociaProtocollo", record)) {
			associaProtocolloToolStripButton.show();
		} else {
			associaProtocolloToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaArchivia", record)) {
			archivioToolStripButton.show();
		} else {
			archivioToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaRiapri", record)) {
			annullaArchiviazioneToolStripButton.show();
		} else {
			annullaArchiviazioneToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaScaricaEmail", record)) {
			scaricaMailProtocolloToolStripButton.show();
			if (isAttivaAbilitazione("abilitaScaricaEmailSenzaBustaTrasporto", record)) {
				frecciaScaricaMailProtocolloToolStripButton.show();
			} else {
				frecciaScaricaMailProtocolloToolStripButton.hide();
			}
		} else {
			scaricaMailProtocolloToolStripButton.hide();
			frecciaScaricaMailProtocolloToolStripButton.hide();
		}

		RecordList listaAllegati = record.getAttributeAsRecordList("listaAllegati");
		if ((listaAllegati != null ? listaAllegati.getLength() : 0) > 0) {
			downloadZipAllegatiToolStripButton.show();
		} else {
			downloadZipAllegatiToolStripButton.hide();
		}

		/*
		 * ABILITAZIONI
		 */
		
		//Se è abilitato alla protocollazione e al registrazione a repertorio mostra il menu registra
		if(isAttivaAbilitazione("abilitaRepertoria", record) || isAttivaAbilitazione("abilitaProtocolla", record)) {
			registraToolStripButton.show();
		} else {
			registraToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaRispondi", record)) {
			rispondiToolStripButton.show();
		} else {
			rispondiToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaRispondiATutti", record)) {
			frecciaRispondiToolStripButton.show();
		} else {
			frecciaRispondiToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaInoltraEmail", record)) {
			inoltraToolStripButton.show();
		} else {
			inoltraToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaInoltraContenuti", record)) {
			frecciaInoltraToolStripButton.show();
		} else {
			frecciaInoltraToolStripButton.hide();
		}

		List<String> tipiNotificheInterop = new ArrayList<String>();
		if (isAttivaAbilitazione("abilitaNotifInteropConferma", record)) {
			tipiNotificheInterop.add("conferma");
		}
		if (isAttivaAbilitazione("abilitaNotifInteropEccezione", record)
				&& (record.getAttribute("statoProtocollazione") != null && record.getAttribute("statoProtocollazione").equalsIgnoreCase("non protocollata"))) {
			tipiNotificheInterop.add("eccezione");
		}
		if (isAttivaAbilitazione("abilitaNotifInteropAggiornamento", record)) {
			tipiNotificheInterop.add("aggiornamento");
		}
		if (isAttivaAbilitazione("abilitaNotifInteropAnnullamento", record)) {
			tipiNotificheInterop.add("annullamento");
		}

		inviaNotificaDetailButton.hide();
		inviaNotificaConfermaDetailButton.hide();
		inviaNotificaEccezioneDetailButton.hide();
		inviaNotificaAggiornamentoDetailButton.hide();
		inviaNotificaAnnullamentoDetailButton.hide();
		if (tipiNotificheInterop.size() > 0) {
			if (tipiNotificheInterop.size() == 1) {
				inviaNotificaDetailButton.hide();
				if (tipiNotificheInterop.get(0).equals("conferma")) {
					inviaNotificaConfermaDetailButton.show();
				} else {
					inviaNotificaConfermaDetailButton.hide();
				}
				if (tipiNotificheInterop.get(0).equals("eccezione")) {
					inviaNotificaEccezioneDetailButton.show();
				} else {
					inviaNotificaEccezioneDetailButton.hide();
				}
				if (tipiNotificheInterop.get(0).equals("aggiornamento")) {
					inviaNotificaAggiornamentoDetailButton.show();
				} else {
					inviaNotificaAggiornamentoDetailButton.hide();
				}
				if (tipiNotificheInterop.get(0).equals("annullamento")) {
					inviaNotificaAnnullamentoDetailButton.show();
				} else {
					inviaNotificaAnnullamentoDetailButton.hide();
				}
			} else {
				inviaNotificaDetailButton.show();
				inviaNotificaConfermaDetailButton.hide();
				inviaNotificaEccezioneDetailButton.hide();
				inviaNotificaAggiornamentoDetailButton.hide();
				inviaNotificaAnnullamentoDetailButton.hide();
			}
		}

		// Abilitazioni carico e rilascio
		if (!isAttivaAbilitazione("abilitaPresaInCarico", record) && !isAttivaAbilitazione("abilitaMessaInCarico", record)) {
			caricoToolStripButton.hide();
			frecciaCaricoToolStripButton.hide();
		} else {
			caricoToolStripButton.show();
			frecciaCaricoToolStripButton.show();
		}
		if (isAttivaAbilitazione("abilitaRilascio", record)) {
			rilasciaToolStripButton.show();
		} else {
			rilasciaToolStripButton.hide();
		}

		if (isAttivaAbilitazione("abilitaAssociaAInvio", record))
			associaInvioDetailButton.show();
		else {
			associaInvioDetailButton.hide();
		}

		tabSetGenerale.selectTab(indexTab);

		// Federico Cacco
		// Se non ho item in lavorazione il tab deve essere aperto in modalità edit,
		// altrimenti lo apro in modalità view.
		// RecordList listaItemLavorazione = record.getAttributeAsRecordList("listaItemInLavorazione");
		// if ((listaItemLavorazione != null) && (listaItemLavorazione.getLength() > 0)) {
		// // Ho degli item. Il tab relativo deve essere aperto in modalità di lettura
		// viewMode();
		// } else {
		// // Ho degli item. Il tab relativo deve essere aperto in modalità di scrittura
		// editMode();
		// }

		// Se sono in bozza compare il bottone per il salvataggio bozza e invio, altrimenti
		// posso salvare solamente gli item in lavorazione
		salvaBozzaButton.setVisible(isBozza(record) && isAttivaAbilitazione("abilitaSalvaBozza", record));
		salvaItemLavorazioneButton.setVisible(!isBozza(record) && isAttivaAbilitazione("abilitaSalvaItemLav", record));

		if (isBozza(record)) {
			Record recordToSave = populateFormBozza(record);
			mDynamicFormBozza.editRecord(recordToSave);
			mDynamicFormBozza.markForRedraw();
			detailSectionContenuti.hide();
			detailSectionFileAllegati.hide();
			detailSectionBozza.show();
		} else {
			detailSectionContenuti.show();
			detailSectionFileAllegati.show();
			detailSectionBozza.hide();
		}

		setTitleDetailSectionAppuntiNote(record);
		
		setTitleDetailSectionEmailSuccessive(record);

		// Se sono in bozza devo poter editare tutto il dettaglio, altrimenti solamente gli item in lavorazione
		setCanEdit(isBozza(record));

		aggiornaWindowTitle(record);

		markForRedraw();
	}

	/**
	 * Aggiunge un icona al tab Appunti & note se gli item in lavorazione contengono elementi
	 * 
	 * @param record
	 */
	private void setTitleDetailSectionAppuntiNote(Record record) {
		boolean isAppuntiNote = false;
		RecordList recordListAppuntiNote = record != null && record.getAttributeAsRecordList("listaItemInLavorazione") != null
				&& record.getAttributeAsRecordList("listaItemInLavorazione").getLength() > 0 ? record.getAttributeAsRecordList("listaItemInLavorazione") : null;
		if (recordListAppuntiNote != null) {
			for (int i = 0; i < recordListAppuntiNote.getLength(); i++) {
				Record item = recordListAppuntiNote.get(i);
				if (item != null) {
					if (item.getAttributeAsString("itemLavUriFile") != null && !"".equals(item.getAttributeAsString("itemLavUriFile"))) {
						isAppuntiNote = true;
						break;
					}
					if ((item.getAttributeAsString("itemLavTag") != null && !"".equals(item.getAttributeAsString("itemLavTag")))
							|| (item.getAttributeAsString("itemLavCommento") != null && !"".equals(item.getAttributeAsString("itemLavCommento")))) {
						isAppuntiNote = true;
						break;
					}
				}
			}
		}
		if (tabIterBozza != null) {
			if (AurigaLayout.getIsAttivaAccessibilita()) {
				if (isAppuntiNote) {
					tabIterBozza.setTitle("<h5><img src=\"images/postaElettronica/flg_appunti_note.png\" height=\"16\" width=\"16\" /> <b>" + I18NUtil.getMessages().posta_elettronica_detail_section_note_appunti() + "</b></h5>");
				} else {
					tabIterBozza.setTitle("<h5><b>" + I18NUtil.getMessages().posta_elettronica_detail_section_note_appunti() + "</b></h5>");
				}	
			} else {
			if (isAppuntiNote) {
				tabIterBozza.setTitle("<img src=\"images/postaElettronica/flg_appunti_note.png\" height=\"16\" width=\"16\" /> <b>" + I18NUtil.getMessages().posta_elettronica_detail_section_note_appunti() + "</b>");
			} else {
				tabIterBozza.setTitle("<b>" + I18NUtil.getMessages().posta_elettronica_detail_section_note_appunti() + "</b>");
			}
		}
	}
	}
	
	private void setTitleDetailSectionEmailSuccessive(Record record) {
		boolean isAppuntiEmailSuccessive = false;
		
		RecordList listaEmailSuccessiveCollegate = record.getAttributeAsRecordList("listaEmailSuccessiveCollegate");
		if(listaEmailSuccessiveCollegate != null && listaEmailSuccessiveCollegate.getLength() > 0 &&
				!listaEmailSuccessiveCollegate.isEmpty()) {
			isAppuntiEmailSuccessive = true;
		}
		
		if (tabEmailSuccessiveCollegate != null) {
			if (AurigaLayout.getIsAttivaAccessibilita()) {
				if (isAppuntiEmailSuccessive) {
					tabEmailSuccessiveCollegate.setTitle("<h5><img src=\"images/postaElettronica/emailSuccessive.png\" height=\"16\" width=\"16\" /> &nbsp; <b>" + I18NUtil.getMessages().posta_elettronica_detail_section_tab_emailSucc() + "</b></h5>");
				} else {
					tabEmailSuccessiveCollegate.setTitle("<h5><b>" + I18NUtil.getMessages().posta_elettronica_detail_section_tab_emailSucc() + "</b></h5>");
				}		
			} else {
			if (isAppuntiEmailSuccessive) {
				tabEmailSuccessiveCollegate.setTitle("<img src=\"images/postaElettronica/emailSuccessive.png\" height=\"16\" width=\"16\" /> &nbsp; <b>" + I18NUtil.getMessages().posta_elettronica_detail_section_tab_emailSucc() + "</b>");
			} else {
				tabEmailSuccessiveCollegate.setTitle("<b>" + I18NUtil.getMessages().posta_elettronica_detail_section_tab_emailSucc() + "</b>");
			}
		}
	}
	}

	protected boolean isBozza(Record record) {
		String id = record != null ? record.getAttribute("id") : "";
		return id != null && id.toUpperCase().endsWith(".B");
	}

	protected boolean isAttivaAbilitazione(String name) {
		return isAttivaAbilitazione(name, new Record(vm.getValues()));
	}

	protected boolean isAttivaAbilitazione(String name, Record record) {
		return record != null && record.getAttribute(name) != null && record.getAttribute(name).equalsIgnoreCase("true");
	}

	protected void realEditRecord(Record lRecord) {
		super.editRecord(lRecord);
		setCanEdit(false);
		markForRedraw();
	}

	private boolean openDetailSectionFileAllegati() {
		Record record = new Record(vm.getValues());
		return ((record.getAttribute("flgIO") != null && (record.getAttribute("flgIO").equals("I") || record.getAttribute("flgIO").equals("O")))
				&& (record.getAttribute("categoria") != null && (record.getAttribute("categoria").equals("PEC")
						|| record.getAttribute("categoria").equals("ANOMALIA") || record.getAttribute("categoria").equals("INTEROP_SEGN")
						|| record.getAttribute("categoria").equals("INTEROP_CONF") || record.getAttribute("categoria").equals("INTEROP_AGG")
						|| record.getAttribute("categoria").equals("INTEROP_ECC") || record.getAttribute("categoria").equals("ALTRO"))));
	}

	private boolean openDetailSectionProtocollo() {
		Record record = new Record(vm.getValues());
		return ((record.getAttribute("flgIO") != null && record.getAttribute("flgIO").equals("I"))
				&& (record.getAttribute("categoria") != null && record.getAttribute("categoria").equals("INTEROP_SEGN")));
	}

	private boolean openDetailAzioneDaFare() {
		String azioneDaFare = (String) new Record((JavaScriptObject) vm.getValue("azioneDaFareBean")).toMap().get("azioneDaFare");
		return ((azioneDaFare != null && !azioneDaFare.equals("")));
	}

	private boolean isMailEntrataI() {
		Record record = new Record(vm.getValues());
		return record.getAttribute("flgIO") != null
				&& (record.getAttribute("flgIO").equalsIgnoreCase("I")/* || record.getAttribute("flgIO").equalsIgnoreCase("O") */);
	}

	private boolean isMailUscitaO() {
		Record record = new Record(vm.getValues());
		return record.getAttribute("flgIO") != null && record.getAttribute("flgIO").equalsIgnoreCase("O");
	}
	
	private boolean isMittentePec(){
		Record record = new Record(vm.getValues());
		return record.getAttribute("categoria") != null && record.getAttribute("categoria").equalsIgnoreCase("PEC");
	}

	private boolean isMailProtocollata() {
		Record record = new Record(vm.getValues());
		return record.getAttribute("statoProtocollazione") != null && !record.getAttribute("statoProtocollazione").equals("")
				&& !record.getAttribute("statoProtocollazione").equalsIgnoreCase("non protocollata");
	}

	private boolean isCategoriaInteropSegn() {
		Record record = new Record(vm.getValues());
		return record.getAttribute("categoria") != null && record.getAttribute("categoria").equalsIgnoreCase("INTEROP_SEGN");
	}

	private void scaricaMail() {
		
		if (vm.getValue("flgURIRicevuta") != null && "1".equalsIgnoreCase((String)vm.getValue("flgURIRicevuta"))) {
			Record input = new Record();
			input.setAttribute("idEmail", vm.getValue("idEmail"));
			GWTRestDataSource downloadFromIdDS = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource");
			downloadFromIdDS.executecustom("getUriFromIdEmail", input, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					Record lRecord = new Record();
					lRecord.setAttribute("displayFilename", vm.getValue("progrDownloadStampa") + ".eml");
					lRecord.setAttribute("uri", response.getData()[0].getAttributeAsString("uriFile"));
					lRecord.setAttribute("sbustato", "false");
					lRecord.setAttribute("remoteUri", "false");
					DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
				}
			});
		} else {
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", vm.getValue("progrDownloadStampa") + ".eml");
			lRecord.setAttribute("uri", vm.getValue("uri"));
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", "false");
			DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
		}
	}

	// Preview del file
	public void clickPreviewCorpoItem() {
		Record lRecord = new Record(vm.getValues());
		final String body = vm.getValue("body").toString();
		final String messageId = vm.getValue("messageId").toString();
		new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource").executecustom("recuperaInfoHtml", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					boolean isHtml = response.getData()[0].getAttributeAsBoolean("value");
					VisualizzaCorpoMail visualizzaCorpoMail = new VisualizzaCorpoMail(body, messageId, isHtml);
					visualizzaCorpoMail.show();
				}
			}
		});
	}

	/**
	 * Visualizza le informazioni della email complessivamente
	 */
	public void clickPreviewInfoMailItem() {
		final Record lRecord = new Record(vm.getValues());
		PreviewWindowEmailPdf visualizzaMail = new PreviewWindowEmailPdf(lRecord);
		visualizzaMail.show();
	}

	@Override
	public void setCanEdit(boolean canEdit) {
		Record record = new Record(vm.getValues());
		super.setCanEdit(isBozza(record) && isAttivaAbilitazione("abilitaSalvaBozza", record));
		if (!isBozza(record) || !isAttivaAbilitazione("abilitaSalvaBozza", record)) {
			mDynamicFormBozza.setCanEdit(false);

			//Se è readOnly non visualizzo la toolstrip
			mainToolStrip.setVisible(false);
			
			if (AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")) {
				/*
				 * Siamo nel caso in cui si tratta di una bozza non editabile
				 * Rendo non visibile il pulsante per la firma predefinita 
				 */
				firmaPredefinitaButton.setVisible(false);
			}
		} else {
			mDynamicFormBozza.setCanEdit(true);
			
			//Se NON è readOnly visualizzo la toolstrip
			mainToolStrip.setVisible(true);
			
			if (AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")) {
				/*
				 * Siamo nel caso in cui si tratta di una bozza editabile
				 * Rendo visibile il pulsante per la firma predefinita SE il flag sul database indica che lo si vuole visualizzare
				 */
				firmaPredefinitaButton.setVisible(AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE"));
			}
		}

		tipoItem.setTextBoxStyle(it.eng.utility.Styles.formTitleBold);
		emailPredecessoreTipoItem.setTextBoxStyle(it.eng.utility.Styles.formTitleBold);

		statoLavorazioneItem.setTextBoxStyle(it.eng.utility.Styles.staticTextItemBold);
		tsInvioItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);

		desUOAssegnatariaItem.setTextBoxStyle(it.eng.utility.Styles.staticTextItemBold);
		estremiDocTrasmessiItem.setTextBoxStyle(it.eng.utility.Styles.staticTextItemBold);
		tsRicezioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		tsUOAssegnazioneDal.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		orarioUOAssegnazione.setTextBoxStyle(it.eng.utility.Styles.textItemBold);

		casellaAccountItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		messageIdItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		inCaricoAItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		azioneDaFareItem.setTextBoxStyle(it.eng.utility.Styles.staticTextItemBold);
		protocollataComeItem.setTextBoxStyle(it.eng.utility.Styles.staticTextItemBold);
		tsInCaricoDal.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		orarioInCaricoDal.setTextBoxStyle(it.eng.utility.Styles.textItemBold);

		dataStatoLavorazioneAPartireDal.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		orarioStatoLavorazione.setTextBoxStyle(it.eng.utility.Styles.textItemBold);

		emailPredecessoreTsInvioItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		emailPredecessoreTsRicezioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		emailPredecessoreCasellaAccountItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);

		titleItemCasellaAccount.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemInCaricoA.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemEstremiDocTrasmessi.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemStatoConsolidamento.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemStatoLavorazione.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemDesUOAssegnataria.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleTsStatoLavorazioneAPartireDal.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemProtocollataCome.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemTsRicezione.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemTsInvio.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemMittenteEmailInviata.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemIndirizzoDestinatariOriginali.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemIndirizzoDestinatariCopia.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemOggettoDatiProtocollo.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleAzioneDaFare.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemInCaricoA.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleDettaglioAzioneDaFare.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemCorpoMail.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleEnteProtocollo.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleOggettoMailProtocollo.setTextBoxStyle(it.eng.utility.Styles.formTitle);
		titleItemMotivoEccezione.setTextBoxStyle(it.eng.utility.Styles.formTitle);

		if ((portletLayout != null) && (portletLayout.getItemLavorazioneMailItem() != null)) {
			if (isBozza(record)) {
				portletLayout.getItemLavorazioneMailItem().setCanEdit(isAttivaAbilitazione("abilitaSalvaBozza", record));
			} else {
				portletLayout.getItemLavorazioneMailItem().setCanEdit(isAttivaAbilitazione("abilitaSalvaItemLav", record));
			}
			portletLayout.getItemLavorazioneMailItem().redraw();
		}
	}

	public void aggiornaWindowTitle(Record record) {
		if (window != null) {
			String title = record.getAttribute("titoloGUIDettaglioEmail");
			if (record.getAttribute("flgIO") != null) {
				if (record.getAttribute("flgIO").equalsIgnoreCase("I")) {
					title = "<img src=\"images/postaElettronica/iconMilano/mail_entrata.png\" height=\"16\" width=\"16\" align=\"bottom\"/>&nbsp;&nbsp;"
							+ title;
				} else if (record.getAttribute("flgIO").equalsIgnoreCase("O")) {
					title = "<img src=\"images/postaElettronica/iconMilano/mail_uscita.png\" height=\"16\" width=\"16\" align=\"bottom\"/>&nbsp;&nbsp;" + title;
				}
			}
			window.setTitle(title);
		}
	}

	protected void caricaDettaglioEmailCollegata(String idEmailCollegata) {

		indexTab = 0;
		Record rr = new Record(vm.getValues());
		String idEmail = rr.getAttribute("idEmail");

		Record record = new Record();
		record.setAttribute("idEmail", idEmailCollegata);
		record.setAttribute("idEmailPrecIn", idEmail);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
		lGwtRestDataSource.performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record detailRecord = response.getData()[0];

					editRecordFromLivello(detailRecord);
				}
			}
		}, new DSRequest());
	}

	private void dettaglioProtocollo(String idUd, String estremi) {
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		String title = (estremi != null && !"".equals(estremi) ? "Dettaglio " + estremi : "");
		DettaglioRegProtAssociatoWindow dettaglioProtWindow = new DettaglioRegProtAssociatoWindow(lRecord, title);
	}

	public DettaglioEmailWindow getWindow() {
		return window;
	}

	public void setWindow(DettaglioEmailWindow window) {
		this.window = window;
	}

	public class EstremiTitleStaticTextItem extends StaticTextItem {

		public EstremiTitleStaticTextItem(String title, int width) {
			setTitle(title);
			setColSpan(1);
			setDefaultValue(title + AurigaLayout.getSuffixFormItemTitle());
			setWidth(width);
			setShowTitle(false);
			setAlign(Alignment.RIGHT);
			setTextBoxStyle(it.eng.utility.Styles.formTitle);
			setWrap(false);
		}

		@Override
		public void setCanEdit(Boolean canEdit) {
			setTextBoxStyle(it.eng.utility.Styles.formTitle);
		}
	}

	public class EstremiStaticTextItem extends StaticTextItem {

		private int widthOrig;

		public EstremiStaticTextItem(String name, String title, FormItemIcon... icons) {
			super(name, title);
			if (AurigaLayout.getIsAttivaAccessibilita()) {
				setCanFocus(true);			
			}
			setHeight(22);
			setIcons(icons);
			setWrap(false);
		}

		@Override
		public void setWidth(int width) {
			super.setWidth(width + 19);
			widthOrig = width;
		}

		@Override
		public void setShowIcons(Boolean showIcons) {
			super.setShowIcons(showIcons);
			if (showIcons) {
				setAttribute("width", widthOrig + 19);
			} else {
				setAttribute("width", widthOrig);
			}
		}

		@Override
		public void setCanEdit(Boolean canEdit) {
			super.setCanEdit(true);
			setTextBoxStyle(it.eng.utility.Styles.staticTextItemBold);
		}
	}

	protected void visualizzaRegAssociata() {
		final Record listRecordAssocia = new Record(vm.getValues());

		HashMap<String, String> mappaEstremiDocDerivati = (HashMap<String, String>) listRecordAssocia.getAttributeAsMap("mappaEstremiDocTrasmessi");
		if (mappaEstremiDocDerivati != null && mappaEstremiDocDerivati.size() > 0) {
			if (mappaEstremiDocDerivati.size() == 1) {
				String idUd = (String) mappaEstremiDocDerivati.keySet().iterator().next();
				String estremi = (String) mappaEstremiDocDerivati.get(idUd);
				listRecordAssocia.setAttribute("idUd", idUd);
				DettaglioRegProtAssociatoWindow dettaglioRegProtAssociatoWindow = new DettaglioRegProtAssociatoWindow(listRecordAssocia,
						"Dettaglio prot. associato " + estremi);
			} else {
				Menu menu = new Menu();
				for (final Object key : mappaEstremiDocDerivati.keySet()) {
					final String idUd = (String) key;
					final String estremi = (String) mappaEstremiDocDerivati.get(key);
					MenuItem menuItem = new MenuItem(estremi);
					menuItem.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							listRecordAssocia.setAttribute("idUd", idUd);
							DettaglioRegProtAssociatoWindow dettaglioRegProtAssociatoWindow = new DettaglioRegProtAssociatoWindow(listRecordAssocia,
									"Dettaglio prot. associato " + estremi);
						}
					});
					menu.addItem(menuItem);
				}
				menu.showContextMenu();
			}
		}
	}

	protected void manageInvioNotificaInteropClick(final Record pRecordMail, final String tipoNotifica) {

		checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {

				Record esitoCheck = object.getAttributeAsRecord("esito");

				boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
				boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

				if (isLock) {
					if (isForzaLock) {
						String messaggio = esitoCheck.getAttributeAsString("errorMessage");
						Layout.showConfirmDialogWithWarning("Attenzione",messaggio, null, null, new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {

										@Override
										public void execute(Record data) {
											Map errorMessages = data.getAttributeAsMap("errorMessages");

											if (errorMessages != null && errorMessages.size() > 0) {

												String error = data.getAttribute("id") + ": " + errorMessages.get(data.getAttribute("idEmail"));

												Layout.addMessage(new MessageBean(error, "", MessageType.ERROR));
											} else {// mail sbloccata

												final GWTRestService<Record, Record> lGwtRestServiceInvioNotifica = new GWTRestService<Record, Record>(
														"AurigaInvioNotificaInteropDatasource");
												lGwtRestServiceInvioNotifica.extraparam.put("tipoNotifica", tipoNotifica);
												lGwtRestServiceInvioNotifica.call(pRecordMail, new ServiceCallback<Record>() {

													@Override
													public void execute(Record object) {
														managerInvioNotificaInteropResponse(lGwtRestServiceInvioNotifica, object);
													}
												});
											}
										}
									});
								}
							}
						});

					} // No force lock
					else {
						Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
					}
				} else {
					final GWTRestService<Record, Record> lGwtRestServiceInvioNotifica = new GWTRestService<Record, Record>(
							"AurigaInvioNotificaInteropDatasource");
					lGwtRestServiceInvioNotifica.extraparam.put("tipoNotifica", tipoNotifica);
					lGwtRestServiceInvioNotifica.call(pRecordMail, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							managerInvioNotificaInteropResponse(lGwtRestServiceInvioNotifica, object);
						}
					});
				}
			}
		});
	}

	protected void managerInvioNotificaInteropResponse(final GWTRestService<Record, Record> lGwtRestServiceInvioNotifica, final Record pRecordMail) {

		InvioNotificaInteropWindow lInvioNotificaInteropWindow = new InvioNotificaInteropWindow(lGwtRestServiceInvioNotifica, pRecordMail, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				ricaricaPagina = true;
				reloadDetail();
			}
		});
		lInvioNotificaInteropWindow.show();
	}

	protected String buildIconHtml(String src) {
		return "<div align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";
	}

	public void clickPreviewMessaggioDiAssegnazioneItem() {
		String messaggioAssegnazione = vm.getValue("msgUltimaAssegnazione").toString();
		VisualizzaMessaggioAssegnazione visualizzaMessaggioAssegnazione = new VisualizzaMessaggioAssegnazione(messaggioAssegnazione);
		visualizzaMessaggioAssegnazione.show();
	}

	protected void manageReinviaClick(final Record pRecordMail) {

		checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {

				Record esitoCheck = object.getAttributeAsRecord("esito");

				boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
				boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

				if (isLock) {
					if (isForzaLock) {
						String messaggio = esitoCheck.getAttributeAsString("errorMessage");
						Layout.showConfirmDialogWithWarning("Attenzione",messaggio, null, null, new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {

										@Override
										public void execute(Record data) {
											Map errorMessages = data.getAttributeAsMap("errorMessages");

											if (errorMessages != null && errorMessages.size() > 0) {

												String error = data.getAttribute("id") + ": " + errorMessages.get(data.getAttribute("idEmail"));

												Layout.addMessage(new MessageBean(error, "", MessageType.ERROR));
											} else {
												RecordList listaEmail = new RecordList();
												listaEmail.add(pRecordMail);

												lock(listaEmail, new ServiceCallback<Record>() {

													@Override
													public void execute(Record data) {

														GWTRestService<Record, Record> lGwtRestServiceAurigaInvioMailDatasource = new GWTRestService<Record, Record>(
																"AurigaInvioMailDatasource");
														lGwtRestServiceAurigaInvioMailDatasource.executecustom("reinvia", pRecordMail, new DSCallback() {

															@Override
															public void execute(DSResponse response, Object rawData, DSRequest request) {
																if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
																	Layout.addMessage(new MessageBean(I18NUtil.getMessages().invionotificainterop_esitoInvio_OK_value(),
																			"", MessageType.INFO));
																}
																ricaricaPagina = true;
																reloadDetail();
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
					} // No
						// force
						// lock
					else {
						Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
					}
				} else {
					RecordList listaEmail = new RecordList();
					listaEmail.add(pRecordMail);

					lock(listaEmail, new ServiceCallback<Record>() {

						@Override
						public void execute(Record data) {

							GWTRestService<Record, Record> lGwtRestServiceAurigaInvioMailDatasource = new GWTRestService<Record, Record>(
									"AurigaInvioMailDatasource");
							lGwtRestServiceAurigaInvioMailDatasource.executecustom("reinvia", pRecordMail, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Layout.addMessage(new MessageBean(I18NUtil.getMessages().invionotificainterop_esitoInvio_OK_value(),
												"", MessageType.INFO));
									}

									sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {

										@Override
										public void execute(Record data) {
											ricaricaPagina = true;
											reloadDetail();
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

	protected void manageInviaBozzaClick(final Record pRecordMail, final boolean chiudiMailDiProvenienza, final String gestisciAzioniDaFare) {
		
		if(verificaInvioSeparato(pRecordMail)){
	
			checkLockEmail(pRecordMail, new ServiceCallback<Record>() {
	
				@Override
				public void execute(Record object) {
	
					Record esitoCheck = object.getAttributeAsRecord("esito");
	
					boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
					boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");
	
					if (isLock) {
						if (isForzaLock) {
							String messaggio = esitoCheck.getAttributeAsString("errorMessage");
							Layout.showConfirmDialogWithWarning("Attenzione",messaggio, null, null, new BooleanCallback() {
	
								@Override
								public void execute(Boolean value) {
									if (value) {
										sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {
	
											@Override
											public void execute(Record data) {
												Map errorMessages = data.getAttributeAsMap("errorMessages");
												if (errorMessages != null && errorMessages.size() > 0) {
	
													String error = data.getAttribute("id") + ": " + errorMessages.get(data.getAttribute("idEmail"));
													Layout.addMessage(new MessageBean(error, "", MessageType.ERROR));
												} else {
													RecordList listaEmail = new RecordList();
													listaEmail.add(pRecordMail);
	
													lock(listaEmail, new ServiceCallback<Record>() {
	
														@Override
														public void execute(Record data) {
	
															if (isNotObbligatoria(pRecordMail)) {
																GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>(
																		"AurigaInvioMailDatasource");
																lGwtRestService.extraparam.put("tipoRel", tipoRel);
																lGwtRestService.executecustom("invioBozzaMail", pRecordMail, new DSCallback() {
	
																	@Override
																	public void execute(DSResponse response, Object rawData, DSRequest request) {
																		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
																			Layout.addMessage(new MessageBean(I18NUtil.getMessages().invionotificainterop_esitoInvio_OK_value(),
																					"", MessageType.INFO));
																			chiudiMailDiProvenienza(chiudiMailDiProvenienza, gestisciAzioniDaFare);
																		}
																	}
																});
															} else {
																Layout.addMessage(
																		new MessageBean("Attenzione, occorre valorizzare il campo Note per il Tag scelto " + "", "",
																				MessageType.WARNING));
															}
														}
													});
												}
											}
										});
									}
								}
							});
						} // No
							// force
							// lock
						else {
							Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
							window.manageOnCloseClick();
						}
					} else {
						RecordList listaEmail = new RecordList();
						listaEmail.add(pRecordMail);
	
						lock(listaEmail, new ServiceCallback<Record>() {
	
							@Override
							public void execute(Record data) {
	
								if (isNotObbligatoria(pRecordMail)) {
	
									GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
									lGwtRestService.extraparam.put("tipoRel", tipoRel);
									lGwtRestService.executecustom("invioBozzaMail", pRecordMail, new DSCallback() {
	
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												Layout.addMessage(new MessageBean(I18NUtil.getMessages().invionotificainterop_esitoInvio_OK_value(),
														"", MessageType.INFO));
												sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {
	
													@Override
													public void execute(Record data) {
														chiudiMailDiProvenienza(chiudiMailDiProvenienza, gestisciAzioniDaFare);
													}
												});
											}
										}
									});
								} else {
									Layout.addMessage(
											new MessageBean("Attenzione, occorre valorizzare il campo Note per il Tag scelto " + "", "", MessageType.WARNING));
								}
							}
						});
					}
				}
			});
		}else{
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().inviomailform_flgInvioSeparato_errormessages() + "", "", MessageType.WARNING));
		}
	}

	public void archiviaEmail(DSResponse response, Record record, String pkField, String nameField, String successMessage, String completeErrorMessage,
			DSCallback callback) {

		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;

			if (errorMessages != null && errorMessages.size() > 0) {

				errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));

			}
			reloadDetail();
			Layout.hideWaitPopup();
			if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
			}
		}
	}

	public void annullaArchiviazione(DSResponse response, Record record, String pkField, String nameField, String successMessage, String completeErrorMessage,
			DSCallback callback) {

		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;

			if (errorMessages != null && errorMessages.size() > 0) {

				RecordList listaRecord = record.getAttributeAsRecordList("listaRecord");
				errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				Record recordErrore = listaRecord.get(0);
				errorMsg += "<br/>" + recordErrore.getAttribute(nameField) + ": " + errorMessages.get(recordErrore.getAttribute(pkField));
			}

			reloadDetail();
			Layout.hideWaitPopup();

			if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
			}
		}
	}

	public void buildTipoOperazione(final RecordList listaMail, String idUserLockFor, String motivi, final boolean isMassiva, final String sceltaAzione) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);
		record.setAttribute("iduserlockfor", idUserLockFor);
		record.setAttribute("motivi", motivi);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LockEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = tipoOperazioneErrorMessage(response, listaMail, "idEmail", "id", isMassiva, sceltaAzione);
				reloadDetail();
				Layout.hideWaitPopup();
				if (errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
				} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (TipoOperazioneMail.PRESA_IN_CARICO.getValue().equals(sceltaAzione)) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_presa_in_carico_successo(), "", MessageType.INFO));
					} else if (TipoOperazioneMail.MESSA_IN_CARICO.getValue().equals(sceltaAzione)) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_messa_in_carico_successo(), "", MessageType.INFO));
					} else if (TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue().equals(sceltaAzione)) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_manda_in_approvazione_successo(), "", MessageType.INFO));
					}
				}
			}
		});
	}

	public void rilascio(final RecordList listaMail, String usreLockFor, String motivo, final boolean isMassiva) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);
		record.setAttribute("motivi", motivo);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("UnlockEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = rilascioErrorMessage(response, listaMail, "idEmail", "id", isMassiva);
				reloadDetail();
				Layout.hideWaitPopup();
				if (errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
				} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_rilascio_successo(), "", MessageType.INFO));

				}
			}
		});
	}

	private String tipoOperazioneErrorMessage(DSResponse response, RecordList listaMail, String pkField, String nameField, boolean isMassiva,
			String sceltaAzione) {
		String errorMsg = null;
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			if (errorMessages != null && errorMessages.size() > 0) {
				if (TipoOperazioneMail.PRESA_IN_CARICO.getValue().equals(sceltaAzione)) {
					errorMsg = I18NUtil.getMessages().posta_elettronica_presa_in_carico_errore();
				} else if (TipoOperazioneMail.MESSA_IN_CARICO.getValue().equals(sceltaAzione)) {
					errorMsg = I18NUtil.getMessages().posta_elettronica_messa_in_carico_errore();
				} else if (TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue().equals(sceltaAzione)) {
					errorMsg = I18NUtil.getMessages().posta_elettronica_manda_in_approvazione_errore();
				}
				errorMsg += "<br/>" + errorMessages.get(listaMail.get(0).getAttribute(pkField));
			}
		}
		return errorMsg;
	}

	private String rilascioErrorMessage(DSResponse response, RecordList listaMail, String pkField, String nameField, boolean isMassiva) {
		String errorMsg = null;
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			if (errorMessages != null && errorMessages.size() > 0) {
				errorMsg = "Il rilascio è andato in errore!";
				errorMsg += "<br/>" + errorMessages.get(listaMail.get(0).getAttribute(pkField));
			}
		}
		return errorMsg;
	}

	// Controllo se la singola mail è stata presa gia in carico
	private void checkLockEmail(Record record, final ServiceCallback<Record> callback) {

		final GWTRestDataSource lockEmailDataSource = new GWTRestDataSource("LockEmailDataSource");

		lockEmailDataSource.executecustom("checkLockEmail", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Record rec = new Record();
				rec.setAttribute("esito", response.getData()[0]);
				callback.execute(rec);
			}
		});
	}

	// sblocco singola mail
	private void sbloccaEmail(Record record, final ServiceCallback<Record> callback) {
		final RecordList listaEmail = new RecordList();
		listaEmail.add(record);

		Record lRecord = new Record();
		lRecord.setAttribute("listaRecord", listaEmail);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("UnlockEmailDataSource");
		lGwtRestDataSource.addData(lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					Map errorMessages = data.getAttributeAsMap("errorMessages");
					callback.execute(data);
				}
			}
		});
	}

	private void lock(final RecordList listaMail, final ServiceCallback<Record> callback) {

		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LockEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = null;

				Record rec = new Record();

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					Map errorMessages = data.getAttributeAsMap("errorMessages");

					if (errorMessages != null && errorMessages.size() > 0) {
						errorMsg = "La messa in carico è andata in errore!";
						errorMsg += "<br/>" + data.getAttribute("id") + ": " + errorMessages.get(data.getAttribute("idEmail"));
						rec.setAttribute("esitoLock", false);
					} else
						rec.setAttribute("esitoLock", true);
				}
				callback.execute(rec);
			}
		});
	}

	private void manageAnnullamentoAssegnazioneUOCompetente(Record object) {
		
		ricaricaPagina = true;
		Map errorMessages = object.getAttributeAsMap("errorMessages");
		reloadDetail();
		Layout.hideWaitPopup();
		if (errorMessages != null && errorMessages.size() > 0) {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_annulla_uo_competente_error(), "", MessageType.ERROR));
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_annulla_uo_competente_success(), "", MessageType.INFO));
		}
	}

	public static String encodeURL(String str) {
		if (str != null) {
			return URL.encode(str.replaceAll("&", "%26"));
		}
		return null;
	}

	private VLayout createLayoutIterBozza() {

		portletLayout = new ItemLavorazioneMailLayout("dettaglio_email", vm) {

			@Override
			public void trasformaInAllegatoEmailFromItemLavorazione(Record record) {
				AttachmentReplicableItem item = (AttachmentReplicableItem) mDynamicFormBozza.getAttachmentReplicableItem();
				item.setFileAsAllegatoFromWindow(record);
				mDynamicFormBozza.markForRedraw();
			};

			@Override
			public Boolean showAbilToPrint() {
				return isAttivaAbilitazione("abilitaStampaFile");
			}
		};
		portletLayout.setHeight100();
		portletLayout.setWidth100();

		VLayout layoutDatiIterBozza = new VLayout(5);
		layoutDatiIterBozza.addMember(portletLayout);

		return layoutDatiIterBozza;
	}

	@Override
	public void newMode() {
		super.newMode();
	}

	@Override
	public void editMode() {
		super.editMode();
	}

	private void manageAddDataArrivedSelectMittente(DataArrivedEvent event) {
		String mittenteDefault = lSelectItemMittenteBozza.getValueAsString();
		if (mittenteDefault != null && !"".equals(mittenteDefault) && event != null && event.getData() != null && event.getData().getLength() > 0) {
			boolean trovato = false;
			for (int i = 0; i < event.getData().getLength(); i++) {
				if (event.getData().get(i).getAttributeAsString("key").equals(mittenteDefault)) {
					trovato = true;
					break;
				}
			}
			if (!trovato) {
				lSelectItemMittenteBozza.setValue(" ");				
				if (getApplicationName().equalsIgnoreCase("EIIFact"))
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().postaElettronica_mittente_default_warning_digidoc(), "", MessageType.WARNING));
				else					
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().postaElettronica_mittente_default_warning(), "", MessageType.WARNING));
			}
		}
	}

	private void saveBozza(final DSCallback callback, final String invio_finale) {
		if (vm != null) {
			Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_salvataggio_mail());

			/*
			 * Voglio ottenere l'istanza di PostaElettronicaList da cui sono partito in modo tale da poter eliminare dalla cache la bozza che è appena stata
			 * modificata. Questo per fare in modo che, al prossimo caricamento della bozza modificata, venga eseguito un fetch delle informazioni senza
			 * utilizzare quelle precedentemente memorizzate (e ormai obsolete)
			 */
			if (getWindow() != null && getWindow().get_layoutPostaElettronica() != null && getWindow().get_layoutPostaElettronica().getList() != null
					&& getWindow().get_layoutPostaElettronica().getList() instanceof PostaElettronicaList) {
				String idEmail = vm.getValueAsString("idEmail"); // l'id della mail da cancellare dalla cache
				((PostaElettronicaList) getWindow().get_layoutPostaElettronica().getList()).deleteFromAllegatiCache(idEmail);
			}

			vm.clearErrors(true);
			if ((vm.getItem("listaItemInLavorazione") == null)
					|| (vm.getItem("listaItemInLavorazione") != null && vm.getItem("listaItemInLavorazione").validate())) {
				Record recordDetailMail = new Record(vm.getValues());
				if (isNotObbligatoria(recordDetailMail)) {

					final Record invioMailRecord = new Record(mDynamicFormBozza.getValues());
					invioMailRecord.setAttribute("aliasAccountMittente", invioMailRecord.getAttribute("mittente"));
					invioMailRecord.setAttribute("idEmailPrincipale", recordDetailMail.getAttributeAsRecord("invioMailBean").getAttribute("idEmail"));

					Record itemInLavorazioneRecord = new Record();
					if (recordDetailMail.getAttributeAsRecordList("listaItemInLavorazione") != null
							&& recordDetailMail.getAttributeAsRecordList("listaItemInLavorazione").getLength() > 0
							&& !recordDetailMail.getAttributeAsRecordList("listaItemInLavorazione").isEmpty()) {
						itemInLavorazioneRecord.setAttribute("listaItemInLavorazione", recordDetailMail.getAttributeAsRecordList("listaItemInLavorazione"));
						invioMailRecord.setAttribute("listaItemInLavorazione", itemInLavorazioneRecord.getAttributeAsRecordList("listaItemInLavorazione"));
					}

					salvaMail(callback, invio_finale, invioMailRecord);
				} else {
					// Nascondo la schermata di popup
					Layout.hideWaitPopup();
					Layout.addMessage(new MessageBean("Attenzione, occorre valorizzare il campo Note per il Tag scelto " + "", "", MessageType.WARNING));
				}
			} else {
				// Nascondo la schermata di popup
				Layout.hideWaitPopup();
			}
		}
	}

	private void salvaMail(final DSCallback callback, String invio_finale, Record invioMailRecord) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaSalvaIterBozzaMailDataSource");
		lGwtRestService.extraparam.put("tipoRel", tipoRel);
		lGwtRestService.extraparam.put("invio_finale", invio_finale);
		lGwtRestService.extraparam.put("updateBozza", "true");
		lGwtRestService.performCustomOperation("saveBozza", invioMailRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				// Nascondo la schermata di popup
				Layout.hideWaitPopup();

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					ricaricaPagina = true;
					Layout.addMessage(new MessageBean("Bozza salvata con successo", "", MessageType.INFO));
					if (callback == null) {
						reloadDetailBozza();
					} else {
						callback.execute(response, rawData, request);
					}
				}
			}
		}, new DSRequest());
	}

	public void reloadDetailBozza() {

		/*
		 * Aggiungo un popup per indicare il fatto che la schermata è bloccata perchè sto ricaricando il dettaglio della mail
		 */
		Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_caricamento_dettaglio_mail());

		Record lRecord = new Record(vm.getValues());
		final String idEmail = lRecord.getAttributeAsRecord("invioMailBean").getAttribute("idEmail");
		lRecord.setAttribute("idEmail", idEmail);
		lRecord.setAttribute("indexTab", indexTab);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
		lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];

					String i = record.getAttribute("indexTab");
					if (i != null)
						indexTab = Integer.parseInt(i);

					editRecordFromLivello(record);

					tabSetGenerale.selectTab(record.getAttribute("indexTab"));
				}
				// Nascondo la schermata di popup
				Layout.hideWaitPopup();
			}
		}, new DSRequest());
	}

	private Record getSalvaInBozzaMailBeanToRecord() {

		Record recordDetailMail = new Record(vm.getValues());

		Record invioMailRecord = new Record(mDynamicFormBozza.getValues());
		invioMailRecord.setAttribute("aliasAccountMittente", invioMailRecord.getAttribute("mittente"));
		invioMailRecord.setAttribute("idEmailPrincipale", recordDetailMail.getAttributeAsRecord("invioMailBean").getAttribute("idEmail"));
		invioMailRecord.setAttribute("idEmail", recordDetailMail.getAttributeAsRecord("invioMailBean").getAttribute("idEmail"));
		invioMailRecord.setAttribute("flgMailPredecessoreStatoLavAperta", recordDetailMail.getAttribute("flgMailPredecessoreStatoLavAperta"));
		Record itemInLavorazioneRecord = new Record();
		if (recordDetailMail.getAttributeAsRecordList("listaItemInLavorazione") != null
				&& recordDetailMail.getAttributeAsRecordList("listaItemInLavorazione").getLength() > 0
				&& !recordDetailMail.getAttributeAsRecordList("listaItemInLavorazione").isEmpty()) {
			itemInLavorazioneRecord.setAttribute("listaItemInLavorazione", recordDetailMail.getAttributeAsRecordList("listaItemInLavorazione"));
			invioMailRecord.setAttribute("listaItemInLavorazione", itemInLavorazioneRecord.getAttributeAsRecordList("listaItemInLavorazione"));
		}

		return invioMailRecord;
	}

	private Boolean isVisibleRegistraIstanzaButton(Record record) {
		return (isAttivaAbilitazione("abilitaRegIstanzaAutotutela") || isAttivaAbilitazione("abilitaRegIstanzaCED"));
	}

	protected void manageRegistraIstanzaClick(final Record pRecord, final String nomeEntita) {
		GWTRestService<Record, Record> lGwtRestServiceRegistraIstanza = new GWTRestService<Record, Record>("AurigaProtocollaPostaElettronicaDataSource");
		lGwtRestServiceRegistraIstanza.extraparam.put("isRegistroIstanza", "true");
		lGwtRestServiceRegistraIstanza.call(pRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(final Record object) {
				if (object.getAttribute("warningMsgDoppiaReg") != null && !object.getAttribute("warningMsgDoppiaReg").trim().equals("")) {
					// Popup con 3 bottoni 
					// a) Procedi => si procede aprendo la maschera di registrazione
					// b) Annulla e chiudi e-mail => si richiama stored di archiviazione mail e si ricarica lista
					// c) Annulla => si chiude alert senza far nulla nè ricaricare lista
					WarningMsgDoppiaRegPopup lWarningMsgDoppiaRegPopup = new WarningMsgDoppiaRegPopup(object.getAttribute("warningMsgDoppiaReg")) {

						@Override
						public void onClickOkButton(Record objectPopup, DSCallback callback) {		
							markForDestroy();
//							manageModificaDatiButtonClick(object.getAttribute("idUd"), null, pRecord);
							manageRegistaIstanzaPostaElettronicaCallback(object, nomeEntita);
						}

						@Override
						public void onClickAnnullaChiudiMailButton(Record objectPopup, DSCallback callback) {
							markForDestroy();
							actionArchiviaMail();
						}
					};
					lWarningMsgDoppiaRegPopup.show();	
				} else if (object.getAttribute("idUd") != null && !object.getAttribute("idUd").trim().equals("")) {
					SC.ask(I18NUtil.getMessages().postaElettronica__list_copiaMailGiaProtocollataField(), new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if (value) {
								manageModificaDatiButtonClick(object.getAttribute("idUd"), null, pRecord);
							} else {
								SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
									
									@Override
									public void execute(Boolean value) {
										if (value) {
											actionArchiviaMail();
										}
									}
								});
							}
						}
					});
				} else {
					manageRegistaIstanzaPostaElettronicaCallback(object, nomeEntita);
				}
			}
		});
	}

	protected void manageRegistaIstanzaPostaElettronicaCallback(final Record lRecord, String nomeEntita) {
		EditaIstanzaWindowFromMail lEditaIstanzaWindowFromMail = new EditaIstanzaWindowFromMail(nomeEntita, lRecord) {

			@Override
			public void manageAfterCloseWindow() {
				ricaricaPagina = true;
				reloadDetail();
			}
		};
		lEditaIstanzaWindowFromMail.show();
	}

	protected Boolean isNotObbligatoria(Record record) {
		boolean verify = true;

		if (record.getAttributeAsRecordList("listaItemInLavorazione") != null && record.getAttributeAsRecordList("listaItemInLavorazione").getLength() > 0
				&& !record.getAttributeAsRecordList("listaItemInLavorazione").isEmpty()) {
			RecordList recordListItemInLavorazione = record.getAttributeAsRecordList("listaItemInLavorazione");
			for (int i = 0; i < recordListItemInLavorazione.getLength(); i++) {
				Record item = recordListItemInLavorazione.get(i);
				if (item != null) {
					if (item != null && item.getAttribute("flgCommentoObbligatorioTag") != null
							&& !item.getAttribute("flgCommentoObbligatorioTag").equals("")) {
						if (item.getAttribute("flgCommentoObbligatorioTag").equals("1")) {
							if (item.getAttributeAsString("itemLavCommento") == null || item.getAttributeAsString("itemLavCommento").equals("")
									|| item.getAttributeAsString("itemLavCommento").equals(" ")) {
								verify = false;
								break;
							}
						}
					}
				}
			}
		}
		return verify;
	}

	/**
	 * @author DANCRIST Metodo relativo alla stampa di tutti i file della mail,allegati e file nel tab Note&Appunti
	 */
	private void manageStampaTuttiIFile() {
		Integer countShowPreview = 0;
		Integer countNotShowPreview = 0;
		Record record = new Record(vm.getValues());

		final RecordList listaFileDaStampare = new RecordList();
		final Set<String> setFileNonStampabiliMsgWrng = new HashSet<String>();
		final Set<String> setFileNonStampabili = new HashSet<String>();

		// Allegati della mail
		final RecordList recordListAllegati = record.getAttributeAsRecordList("listaAllegati");
		Integer sizeListaAllegati = recordListAllegati != null ? recordListAllegati.getLength() : 0;
		if (recordListAllegati != null && recordListAllegati.getLength() > 0 && !recordListAllegati.isEmpty()) {
			for (int i = 0; i < recordListAllegati.getLength(); i++) {
				Record item = recordListAllegati.get(i);
				if (item != null) {
					if (item.getAttributeAsString("uri") != null && !item.getAttributeAsString("uri").equals("")) {
						InfoFileRecord lInfoFileRecord = new InfoFileRecord(item.getAttributeAsJavaScriptObject("infoFile"));
						if (!PreviewWindow.isToShowEml(lInfoFileRecord, item.getAttributeAsString("displayFileName"))
								&& PreviewWindow.canBePreviewed(lInfoFileRecord)) {
							countShowPreview += 1;
							listaFileDaStampare.add(item);
						} else {
							countNotShowPreview += 1;
							setFileNonStampabiliMsgWrng.add(item.getAttributeAsString("nomeFile"));
							setFileNonStampabili.add(item.getAttributeAsString("nomeFile"));
						}
					}
				}
			}
		}

		// File nel tab Note & Appunti
		final RecordList recordListAppuntiNote = record != null && record.getAttributeAsRecordList("listaItemInLavorazione") != null
				&& record.getAttributeAsRecordList("listaItemInLavorazione").getLength() > 0 ? record.getAttributeAsRecordList("listaItemInLavorazione") : null;
		Integer sizeListaFileNoteAppunti = recordListAppuntiNote != null ? recordListAppuntiNote.getLength() : 0;
		if (recordListAppuntiNote != null && recordListAppuntiNote.getLength() > 0 && !recordListAppuntiNote.isEmpty()) {
			for (int i = 0; i < recordListAppuntiNote.getLength(); i++) {
				Record item = recordListAppuntiNote.get(i);
				if (item != null) {
					if (item.getAttributeAsJavaScriptObject("itemLavInfoFile") != null && item.getAttributeAsString("itemLavNomeFile") != null) {
						InfoFileRecord lInfoFileRecord = new InfoFileRecord(item.getAttributeAsJavaScriptObject("itemLavInfoFile"));
						if (!PreviewWindow.isToShowEml(lInfoFileRecord, item.getAttributeAsString("itemLavNomeFile"))
								&& PreviewWindow.canBePreviewed(lInfoFileRecord)) {
							countShowPreview += 1;
							listaFileDaStampare.add(item);
						} else {
							countNotShowPreview += 1;
							setFileNonStampabiliMsgWrng.add(item.getAttributeAsString("itemLavNomeFile"));
							setFileNonStampabili.add(item.getAttributeAsString("itemLavNomeFile"));
						}
					}
				}
			}
		}

		// La Dimensione degli allegati è uguale alla Dimensione dei file del tab di note & appunti
		if (sizeListaAllegati == sizeListaFileNoteAppunti) {

			// Se la Dimensione dei file non stampabili è uguale alla somma delle dimensioni dei file allegati & file di note&appunti genero
			// un messaggio di warning
			if (countNotShowPreview == (sizeListaAllegati + sizeListaFileNoteAppunti)) {
				Layout.addMessage(new MessageBean("Nessun file è in formato stampabile", "", MessageType.WARNING));
			}
			// Ci sono file da stampare e non
			else if (countNotShowPreview > 0 && countShowPreview > 0) {
				String value = "";
				for (String item : setFileNonStampabiliMsgWrng) {
					value += value.concat(item).concat(";");
				}
				Layout.showConfirmDialogWithWarning("Attenzione","Alcuni dei file da stampare: " + value + " hanno formato non stampabile", null, null, new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value) {
							stampaTuttiIFile(recordListAllegati, recordListAppuntiNote, setFileNonStampabili);
						}
					}
				});
			}
			// Non ci sono file da stampare
			else if (countNotShowPreview > 0 && countShowPreview == 0) {
				Layout.addMessage(new MessageBean("Nessun file è in formato stampabile", "", MessageType.WARNING));
			}
			// Ci sono file da stampare
			else if (countNotShowPreview == 0 && countShowPreview > 0) {
				stampaTuttiIFile(recordListAllegati, recordListAppuntiNote, setFileNonStampabili);
			}
			// Non ci sono file da stampare
			else if (countNotShowPreview == 0 && countShowPreview == 0) {
				Layout.addMessage(new MessageBean("Nessun file è in formato stampabile", "", MessageType.WARNING));
			}
		}
		// La Dimensione degli allegati supera quella dei file presenti in note&appunti
		else if (sizeListaAllegati > sizeListaFileNoteAppunti) {
			// Non ci sono file da stampare
			if (countNotShowPreview == sizeListaAllegati) {
				Layout.addMessage(new MessageBean("Nessun file è in formato stampabile", "", MessageType.WARNING));
			}
			// Ci sono file da stampare e non
			else if (countNotShowPreview > 0 && countShowPreview > 0) {
				String value = "";
				for (String item : setFileNonStampabiliMsgWrng) {
					value += value.concat(item).concat(";");
				}
				Layout.showConfirmDialogWithWarning("Attenzione","Alcuni dei file da stampare: " + value + " hanno formato non stampabile", null, null, new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value) {
							stampaTuttiIFile(recordListAllegati, recordListAppuntiNote, setFileNonStampabili);
						}
					}
				});
			}
			// Non ci sono file da stampare
			else if (countNotShowPreview > 0 && countShowPreview == 0) {
				Layout.addMessage(new MessageBean("Nessun file è in formato stampabile", "", MessageType.WARNING));
			}
			// Ci sono file da stampare
			else if (countNotShowPreview == 0 && countShowPreview > 0) {
				stampaTuttiIFile(recordListAllegati, recordListAppuntiNote, setFileNonStampabili);
			}
			// Non ci sono file da stampare
			else if (countNotShowPreview == 0 && countShowPreview == 0) {
				Layout.addMessage(new MessageBean("Nessun file è in formato stampabile", "", MessageType.WARNING));
			}
		}
		// La dimensione dei file presenti in note&appunti supera quella degli allegati
		else if (sizeListaAllegati < sizeListaFileNoteAppunti) {

			// Non ci sono file da stampare
			if (countNotShowPreview == sizeListaFileNoteAppunti) {
				Layout.addMessage(new MessageBean("Nessun file è in formato stampabile", "", MessageType.WARNING));
			}
			// Ci sono sia file da stampare e non
			else if (countNotShowPreview > 0 && countShowPreview > 0) {

				String value = "";
				for (String item : setFileNonStampabiliMsgWrng) {
					value += value.concat(item).concat(";");
				}
				Layout.showConfirmDialogWithWarning("Attenzione","Alcuni dei file da stampare: " + value + " hanno formato non stampabile", null, null, new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value) {
							stampaTuttiIFile(recordListAllegati, recordListAppuntiNote, setFileNonStampabili);
						}
					}
				});
			}
			// Non ci sono file da stampare
			else if (countNotShowPreview > 0 && countShowPreview == 0) {
				Layout.addMessage(new MessageBean("Nessun file è in formato stampabile", "", MessageType.WARNING));
			}
			// Ci sono file da stampare
			else if (countNotShowPreview == 0 && countShowPreview > 0) {
				stampaTuttiIFile(recordListAllegati, recordListAppuntiNote, setFileNonStampabili);
			}
			// Non ci sono file
			else if (countNotShowPreview == 0 && countShowPreview == 0) {
				Layout.addMessage(new MessageBean("Nessun file è in formato stampabile", "", MessageType.WARNING));
			}
		}
	}

	/**
	 * @author DANCRIST Metodo relativo alla stampa di tutti gli allegati della mail( file allegati e file presenti nel tab Note&Appunti )
	 */
	private void manageStampaAllegati() {
		Integer countShowPreview = 0;
		Integer countNotShowPreview = 0;
		Record record = new Record(vm.getValues());

		final RecordList listaAllegatiDaStampare = new RecordList();
		final Set<String> setFileNonStampabili = new HashSet<String>();

		RecordList recordListAllegati = record.getAttributeAsRecordList("listaAllegati");
		if (recordListAllegati != null && recordListAllegati.getLength() > 0 && !recordListAllegati.isEmpty()) {
			Integer sizeLista = recordListAllegati.getLength();
			for (int i = 0; i < recordListAllegati.getLength(); i++) {
				Record item = recordListAllegati.get(i);
				if (item != null) {
					if (item.getAttributeAsString("uri") != null && !item.getAttributeAsString("uri").equals("")) {
						InfoFileRecord lInfoFileRecord = new InfoFileRecord(item.getAttributeAsJavaScriptObject("infoFile"));
						if (!PreviewWindow.isToShowEml(lInfoFileRecord, item.getAttributeAsString("displayFileName"))
								&& PreviewWindow.canBePreviewed(lInfoFileRecord)) {
							countShowPreview += 1;
							listaAllegatiDaStampare.add(item);
						} else {
							countNotShowPreview += 1;
							setFileNonStampabili.add(item.getAttributeAsString("nomeFile"));
						}
					}
				}
			}

			// Il numero degli allegati non stampabili è uguale alla dimensione della lista degli allegati
			if (countNotShowPreview == sizeLista) {
				Layout.addMessage(new MessageBean("Nessun allegato è in formato stampabile", "", MessageType.WARNING));
			}
			// Ci sono sia file stampabili che non
			else if (countNotShowPreview > 0 && countShowPreview > 0) {
				String value = "";
				for (String item : setFileNonStampabili) {
					value += value.concat(item).concat(";");
				}
				Layout.showConfirmDialogWithWarning("Attenzione","Alcuni degli allegati da stampare: " + value + " hanno formato non stampabile, Procedi ?", null, null, new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value) {
							stampaFileAllegati(listaAllegatiDaStampare, setFileNonStampabili);
						}
					}
				});
			}
			// Ci sono solo file non stampabili
			else if (countNotShowPreview > 0 && countShowPreview == 0) {
				Layout.addMessage(new MessageBean("Nessun allegato è in formato stampabile", "", MessageType.WARNING));
			}
			// Ci sono solo file stampabili
			else if (countNotShowPreview == 0 && countShowPreview > 0) {
				stampaFileAllegati(listaAllegatiDaStampare, setFileNonStampabili);
			}
			// Non ci sono file
			else if (countNotShowPreview == 0 && countShowPreview == 0) {
				Layout.addMessage(new MessageBean("Nessun allegato è in formato stampabile", "", MessageType.WARNING));
			}
		} else {
			Layout.addMessage(new MessageBean("Non sono presenti allegati", "", MessageType.WARNING));
		}
	}

	/**
	 * 
	 * @author DANCRIST Metodo relativo alla stampa di tutti i file presenti nel tab Note&Appunti
	 */
	private void manageStampaFileAppuntiNote() {
		Integer countShowPreview = 0;
		Integer countNotShowPreview = 0;
		Record record = new Record(vm.getValues());

		final RecordList listaFileDaStampare = new RecordList();
		Set<String> setFileNonStampabili = new HashSet<String>();

		RecordList recordListAppuntiNote = record != null && record.getAttributeAsRecordList("listaItemInLavorazione") != null
				&& record.getAttributeAsRecordList("listaItemInLavorazione").getLength() > 0 ? record.getAttributeAsRecordList("listaItemInLavorazione") : null;
		if (recordListAppuntiNote != null && recordListAppuntiNote.getLength() > 0 && !recordListAppuntiNote.isEmpty()) {
			Integer sizeLista = recordListAppuntiNote.getLength();
			for (int i = 0; i < recordListAppuntiNote.getLength(); i++) {
				Record item = recordListAppuntiNote.get(i);
				if (item != null) {
					if (item.getAttributeAsJavaScriptObject("itemLavInfoFile") != null && item.getAttributeAsString("itemLavNomeFile") != null) {
						InfoFileRecord lInfoFileRecord = new InfoFileRecord(item.getAttributeAsJavaScriptObject("itemLavInfoFile"));
						if (!PreviewWindow.isToShowEml(lInfoFileRecord, item.getAttributeAsString("itemLavNomeFile"))
								&& PreviewWindow.canBePreviewed(lInfoFileRecord)) {
							countShowPreview += 1;
							listaFileDaStampare.add(item);
						} else {
							countNotShowPreview += 1;
							setFileNonStampabili.add(item.getAttributeAsString("itemLavNomeFile"));
						}
					}
				}
			}
			// Il numero dei file non stampabili è uguale alla dimensione della lista dei file
			if (countNotShowPreview == sizeLista) {
				Layout.addMessage(new MessageBean("Nessun file è in formato stampabile", "", MessageType.WARNING));
			}
			// Ci sono sia file stampabili che non
			else if (countNotShowPreview > 0 && countShowPreview > 0) {
				String value = "";
				for (String item : setFileNonStampabili) {
					value += value.concat(item).concat(";");
				}
				Layout.showConfirmDialogWithWarning("Attenzione","Alcuni degli allegati da stampare: " + value + " hanno formato non stampabile, Procedi ?", null, null, new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value) {
							stampaFileAppuntiNote(listaFileDaStampare);
						}
					}
				});
			}
			// Ci sono solo file non stampabili
			else if (countNotShowPreview > 0 && countShowPreview == 0) {
				Layout.addMessage(new MessageBean("Nessun file è in formato stampabile", "", MessageType.WARNING));
			}
			// Ci sono solo file stampabili
			else if (countNotShowPreview == 0 && countShowPreview > 0) {
				stampaFileAppuntiNote(listaFileDaStampare);
			}
			// Non ci sono file
			else if (countNotShowPreview == 0 && countShowPreview == 0) {
				Layout.addMessage(new MessageBean("Nessun file è in formato stampabile", "", MessageType.WARNING));
			}
		} else {
			Layout.addMessage(new MessageBean("Nessun file è in formato stampabile", "", MessageType.WARNING));
		}
	}

	private void stampaTuttiIFile(RecordList recordListAllegati, final RecordList recordListFileNoteAppunti, final Set<String> setFilesNonStampabili) {

		/**
		 * Verifico se sono presenti file solo nel tab Note&Appunti
		 */
		if (recordListAllegati != null && recordListAllegati.getLength() > 0 && !recordListAllegati.isEmpty()) {
			Record lRecord = new Record(vm.getValues());
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource");
			lGwtRestDataSource.executecustom("retrieveAttach", lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

						Record recordToPrint = new Record();
						RecordList recordListAllegatiToPrint = new RecordList();
						RecordList recordList = response.getData()[0].getAttributeAsRecordList("listaAllegati");
						if (recordList != null && recordList.getLength() > 0 && !recordList.isEmpty()) {
							for (int i = 0; i < recordList.getLength(); i++) {
								Record item = recordList.get(i);
								if (item != null) {
									if (!setFilesNonStampabili.contains(item.getAttributeAsString("nomeFile"))) {
										item.setAttribute("remoteUri", true);
										recordListAllegatiToPrint.add(item);
									}
								}
							}
						}

						RecordList listaFileNA = new RecordList();
						if (recordListFileNoteAppunti != null && recordListFileNoteAppunti.getLength() > 0 && !recordListFileNoteAppunti.isEmpty()) {
							for (int i = 0; i < recordListFileNoteAppunti.getLength(); i++) {
								Record item = recordListFileNoteAppunti.get(i);
								if (item != null) {

									if (!setFilesNonStampabili.contains(item.getAttributeAsString("itemLavNomeFile"))) {
										Record lRecord = new Record();

										lRecord.setAttribute("uri", item.getAttributeAsString("itemLavUriFile"));
										lRecord.setAttribute("remoteUri", true);
										lRecord.setAttribute("nomeFile", item.getAttributeAsString("itemLavNomeFile"));
										lRecord.setAttribute("infoFile", item.getAttributeAsJavaScriptObject("itemLavInfoFile"));

										listaFileNA.add(lRecord);
									}
								}
							}
						}
						recordToPrint.setAttribute("listaAllegati", getListaTuttiFile(recordListAllegatiToPrint, listaFileNA));
						StampaFileUtility.stampaFile(recordToPrint, null);
					}
				}
			});
		} else {
			
			RecordList listaFileNA = new RecordList();
			if (recordListFileNoteAppunti != null && recordListFileNoteAppunti.getLength() > 0 && !recordListFileNoteAppunti.isEmpty()) {
				for (int i = 0; i < recordListFileNoteAppunti.getLength(); i++) {
					Record item = recordListFileNoteAppunti.get(i);
					if (item != null) {

						Record lRecord = new Record();
						lRecord.setAttribute("uri", item.getAttributeAsString("itemLavUriFile"));
						lRecord.setAttribute("remoteUri", true);
						lRecord.setAttribute("nomeFile", item.getAttributeAsString("itemLavNomeFile"));
						lRecord.setAttribute("infoFile", item.getAttributeAsJavaScriptObject("itemLavInfoFile"));

						listaFileNA.add(lRecord);
					}
				}
			}
			Record recordToPrint = new Record();
			recordToPrint.setAttribute("listaAllegati", listaFileNA);
			StampaFileUtility.stampaFile(recordToPrint, null);
		}
	}

	// metodo per export in xls di tutti i destinatari di una e-mail
	public void manageEsportaElencoDestinatari() {
		
		String[] fields = createFieldsToExport();
		String[] headers = new String[NUMERO_CAMPI];
		
		for (String field : fields) {	
			if(field.equalsIgnoreCase("tipoDestinatario")) {
				fields[0] = field;
				headers[0] = "Tipo destinatario";
			}else if(field.equalsIgnoreCase("indirizzo")) {
				fields[1] = field;
				headers[1] = "Indirizzo";
			}else if(field.equalsIgnoreCase("statoConsolidamento")) {
				fields[2] = field;
				headers[2] = "Stato Consolidamento";
			}else if(field.equalsIgnoreCase("flgNotifInteropEcc")) {
				fields[3] = field;
				headers[3] = "Arrivata notifica interoperabile di eccezione";
			}else if(field.equalsIgnoreCase("flgNotifInteropConf")) {
				fields[4] = field;
				headers[4] = "Arrivata notifica interoperabile di conferma";
			}else if(field.equalsIgnoreCase("flgNotifInteropAgg")) {
				fields[5] = field;
				headers[5] = "Arrivata notifica interoperabile di aggiornamento";
			}else if(field.equalsIgnoreCase("flgNotifInteropAnn")) {
				fields[6] = field;
				headers[6] = "Arrivata notifica interoperabile di annullamento";
			}else if(field.equalsIgnoreCase("flgRicevutaLettura")) {
				fields[7] = field;
				headers[7] = "Arrivata la ricevuta di avvenuta lettura";
			}else if(field.equalsIgnoreCase("statoConsegna")) {
				fields[8] = field;
				headers[8] = "Stato di consegna";
			}else if(field.equalsIgnoreCase("msgErrMancataConsegnaDestinatario")) {
				fields[9] = field;
				headers[9] = "Messaggio di mancata consegna";
			}
		}
		
		final Record record = new Record();
		record.setAttribute("nomeEntita", "elencoDestinatari");
		record.setAttribute("formatoExport", "xls");
		record.setAttribute("criteria", (String) null);
		record.setAttribute("fields", fields);
		record.setAttribute("headers", headers);
		record.setAttribute("records", extractRecords(fields));
		record.setAttribute("overflow", false);
		
		final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource");
		lGWTRestDataSource.performCustomOperation("export", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					String filename = "Results." + record.getAttribute("formatoExport");
					String url = response.getData()[0].getAttribute("tempFileOut");
					// se l'esportazione ha restituito un uri allora lancio il download del documento generato, altrimenti
					// vuol dire che è abilitato per questo datasource l'esportazione asincrona e quindi la generazione è stata schedulata
					if (url != null) {
						Window.Location.assign(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename)
								+ "&url=" + URL.encode(url));
					}
				}
				/*
				 * else { Layout.addMessage(new MessageBean("Si è verificato un errore durante l'esportazione della lista", "", MessageType.ERROR)); }
				 */
			}
		}, new DSRequest());
	}
	
	protected String[] createFieldsToExport() {
		String[] fields = new String[NUMERO_CAMPI];
		
		fields[0] = "tipoDestinatario";
		fields[1] = "indirizzo";
		fields[2] = "statoConsolidamento";
		fields[3] = "flgNotifInteropEcc";
		fields[4] = "flgNotifInteropConf";
		fields[5] = "flgNotifInteropAgg";
		fields[6] = "flgNotifInteropAnn";
		fields[7] = "flgRicevutaLettura";
		fields[8] = "statoConsegna";
		fields[9] = "msgErrMancataConsegnaDestinatario";
		
		return fields;
	}
	
	protected Record[] extractRecords(String[] fields) {
		int listaDestinatariPrincipaliLength = 0;
		RecordList listaDestinatariPrincipali = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariPrincipali");
		if (listaDestinatariPrincipali !=null && !listaDestinatariPrincipali.isEmpty())
		   listaDestinatariPrincipaliLength = listaDestinatariPrincipali.getLength();
		
		int listaDestinatariCCLength = 0;
		RecordList listaDestinatariCC = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariCC");
		if(listaDestinatariCC!=null && !listaDestinatariCC.isEmpty())
		  listaDestinatariCCLength = listaDestinatariCC.getLength();
		
		int listaDestinatariCCNLength = 0;
		RecordList listaDestinatariCCN = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatariCCN");
		if(listaDestinatariCCN!=null && !listaDestinatariCCN.isEmpty())
		   listaDestinatariCCNLength = listaDestinatariCCN.getLength();
		
		Record[] records = new Record[listaDestinatariPrincipaliLength+listaDestinatariCCLength+listaDestinatariCCNLength];
		
		for (int i = 0; i < listaDestinatariPrincipaliLength; i++) {
			Record rec = new Record();
			for (String fieldName : fields) {
				if(fieldName.equals("tipoDestinatario")) {
					rec.setAttribute(fieldName, "P");
				} else {
					rec.setAttribute(fieldName, listaDestinatariPrincipali.get(i).getAttribute(fieldName));
				}
			}	
			records[i] = rec;
		}
		
		for (int i = 0; i < listaDestinatariCCLength; i++) {
			Record rec = new Record();
			for (String fieldName : fields) {
				if(fieldName.equals("tipoDestinatario")) {
					rec.setAttribute(fieldName, "CC");
				} else {
					rec.setAttribute(fieldName, listaDestinatariCC.get(i).getAttribute(fieldName));
				}
			}	
			records[listaDestinatariPrincipaliLength+i] = rec;
		}
		
		for (int i = 0; i < listaDestinatariCCNLength; i++) {
			Record rec = new Record();
			for (String fieldName : fields) {
				if(fieldName.equals("tipoDestinatario")) {
					rec.setAttribute(fieldName, "CCN");
				} else {
					rec.setAttribute(fieldName, listaDestinatariCCN.get(i).getAttribute(fieldName));
				}
			}	
			records[listaDestinatariPrincipaliLength+listaDestinatariCCLength+i] = rec;
		}
		
		return records;
	}
	
	/**
	 * 
	 * @param listaAllegatiDaStampare
	 * @param modalita
	 * @author DANCRIST stampa file presenti nel tab Note&Appunti
	 */
	private void stampaFileAppuntiNote(RecordList recordList) {

		RecordList listaAllegati = new RecordList();
		if (recordList != null && recordList.getLength() > 0 && !recordList.isEmpty()) {
			for (int i = 0; i < recordList.getLength(); i++) {
				Record item = recordList.get(i);
				if (item != null) {

					Record lRecord = new Record();
					lRecord.setAttribute("uri", item.getAttributeAsString("itemLavUriFile"));
					lRecord.setAttribute("remoteUri", true);
					lRecord.setAttribute("nomeFile", item.getAttributeAsString("itemLavNomeFile"));
					lRecord.setAttribute("infoFile", item.getAttributeAsJavaScriptObject("itemLavInfoFile"));

					listaAllegati.add(lRecord);
				}
			}
		}
		Record recordToPrint = new Record();
		recordToPrint.setAttribute("listaAllegati", listaAllegati);
		StampaFileUtility.stampaFile(recordToPrint, null);
	}

	/**
	 * 
	 * @param RecordList
	 *            recordList,Set<String> setFileNonStampabili
	 * @param modalita
	 * @author DANCRIST stampa allegati presenti nel dettaglio della mail
	 */
	private void stampaFileAllegati(RecordList recordList, final Set<String> setFilesNonStampabili) {

		Record lRecord = new Record(vm.getValues());
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource");
		lGwtRestDataSource.executecustom("retrieveAttach", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

					Record recordToPrint = new Record();
					RecordList recordListToPrint = new RecordList();
					RecordList recordList = response.getData()[0].getAttributeAsRecordList("listaAllegati");
					if (recordList != null && recordList.getLength() > 0 && !recordList.isEmpty()) {
						for (int i = 0; i < recordList.getLength(); i++) {
							Record item = recordList.get(i);
							if (item != null) {
								if (!setFilesNonStampabili.contains(item.getAttributeAsString("nomeFile"))) {
									item.setAttribute("remoteUri", true);
									recordListToPrint.add(item);
								}
							}
						}
						// NomeFile - uri
						recordToPrint.setAttribute("listaAllegati", recordListToPrint);
						StampaFileUtility.stampaFile(recordToPrint, null);
					}
				}
			}
		});
	}

	private RecordList getListaTuttiFile(RecordList allegati, RecordList files) {
		RecordList result = new RecordList();
		for (int i = 0; i < allegati.getLength(); i++) {
			Record item = allegati.get(i);
			if (item != null) {
				result.add(item);
			}
		}
		for (int j = 0; j < files.getLength(); j++) {
			Record item2 = files.get(j);
			if (item2 != null) {
				result.add(item2);
			}
		}

		return result;
	}

	/**
	 * 
	 * @author DANCRIST Classe per la gestione dei bottoni nella barra inferiore, con il parametro di DB HIDE_LABEL_BUTTON_DETT_EMAIL sarà possibile
	 *         visualizzare o meno il titolo delle stesse
	 */
	public class DettaglioPostaElettronicaToolStripButton extends DetailToolStripButton {

		public DettaglioPostaElettronicaToolStripButton(String title, String icon) {
			super(title, icon, !AurigaLayout.getParametroDBAsBoolean("HIDE_LABEL_BUTTON_DETT_EMAIL"));
		}
	}
	
	public class FrecciaDettaglioPostaElettronicaToolStripButton extends FrecciaDetailToolStripButton {

		public FrecciaDettaglioPostaElettronicaToolStripButton() {
			super(!AurigaLayout.getParametroDBAsBoolean("HIDE_LABEL_BUTTON_DETT_EMAIL"));
		}
	}

	// Se richiesto chiude le mail da cui hanno origine l'inoltro o la risposta
	protected void chiudiMailDiProvenienza(boolean chiudiMailProvenienza, String gestisciAzioniDaFare) {
		boolean isChiusuraMultipla = false;
		final RecordList listaMailDaChiudere = new RecordList();
		if (vm != null) {
			Record dettaglioEmail = new Record(vm.getValues());
			if (chiudiMailProvenienza) {
				if (dettaglioEmail.getAttribute("emailPredecessoreIdEmail") != null
						&& !"".equalsIgnoreCase(dettaglioEmail.getAttribute("emailPredecessoreIdEmail"))) {
					// Ho una mail precedessore (può essere la bozza di una risposta)
					// Verifico se è aperta
					if ("true".equalsIgnoreCase(dettaglioEmail.getAttribute("flgMailPredecessoreStatoLavAperta"))){
						Record recordDaAggiungere = new Record();
						recordDaAggiungere.setAttribute("idEmail", dettaglioEmail.getAttribute("emailPredecessoreIdEmail"));
						listaMailDaChiudere.add(recordDaAggiungere);
					}
				} else if (dettaglioEmail.getAttributeAsRecordArray("idEmailInoltrate") != null) {
					// Ricavo tutte le mail inoltrate (può essere la bozza di un inoltro)
					Record[] elencoEmailInoltrate = dettaglioEmail.getAttributeAsRecordArray("idEmailInoltrate");
					for (Record emailInoltrata : elencoEmailInoltrate) {
						if ("1".equalsIgnoreCase(emailInoltrata.getAttribute("flgMailStatoLavAperta"))){
							Record recordDaAggiungere = new Record();
							recordDaAggiungere.setAttribute("idEmail", emailInoltrata.getAttribute("idMailInoltrata"));
							listaMailDaChiudere.add(recordDaAggiungere);
						}
					}
					if (elencoEmailInoltrate.length <= 1) {
					} else {
						isChiusuraMultipla = true;
					}
				}
			}
		}

		final boolean isChiusuraMultiplaFinal = isChiusuraMultipla;

		if (listaMailDaChiudere.getLength() > 0) {

			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchiviazioneEmailDataSource");
			Record record = new Record();
			record.setAttribute("listaRecord", listaMailDaChiudere);
			if (gestisciAzioniDaFare != null){
				record.setAttribute("operazioneRichiesta", gestisciAzioniDaFare);
			}
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						ricaricaPagina = true;
						// Verifico se ho avuto errori nella chiusura delle mail di provenienza
						if (response.getData()[0] != null && response.getData()[0].getAttributeAsMap("errorMessages") != null
								&& !response.getData()[0].getAttributeAsMap("errorMessages").isEmpty()) {
							String errorMessage = "L'azione \"" + ((AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") != null
									&& !"".equalsIgnoreCase(AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL")))
											? AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") : "Chiudi")
									+ "\" non è andata a buon fine";
							if (isChiusuraMultiplaFinal) {
								// Verifico se sono andati in errore tutti i record o solo alcuni
								if (listaMailDaChiudere.getLength() == response.getData()[0].getAttributeAsMap("errorMessages").size()) {
									// Sono andati in errore tutti i record
									errorMessage = "L'azione \""
											+ ((AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") != null
													&& !"".equalsIgnoreCase(AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL")))
															? AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") : "Chiudi")
											+ "\" non è andata a buon fine su tutte le e-mail inoltrate";
								} else {
									// Sono andati in errore solamente alcuni record
									errorMessage = "L'azione \""
											+ ((AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") != null
													&& !"".equalsIgnoreCase(AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL")))
															? AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") : "Chiudi")
											+ "\" non è andata a buon fine su alcune delle e-mail inoltrate";
								}
							}
							Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));

						}

					}
					window.manageOnCloseClick();
				}
			});
		} else {
			window.manageOnCloseClick();
		}
	}

	private void createMainToolstrip() {

		if (AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")) {
			// Creo la select relativa alla firma in calce
			createSelectFirmaInCalce();
		}

		// Creo la mainToolstrip e aggiungo le select impostate
		mainToolStrip = new ToolStrip();
		mainToolStrip.setBackgroundColor("transparent");
		mainToolStrip.setBackgroundImage("blank.png");
		mainToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		mainToolStrip.setBorder("0px");
		mainToolStrip.setWidth100();
		mainToolStrip.setHeight(30);

		//mainToolStrip.addFill(); // Per spostare a destra la nuova select

		if (AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")) {
			mainToolStrip.addFormItem(firmaInCalceSelectItem);
			mainToolStrip.addButton(firmaPredefinitaButton);
		}

	}

	private void createSelectFirmaInCalce() {
		createFirmeDatasource();

		//Creo il pulsante per selezionare la firma predefinita
		firmaPredefinitaButton = new ToolStripButton("", "menu/firma_email.png");
		firmaPredefinitaButton.setPrompt(I18NUtil.getMessages().firme_in_calce_firmaPredefinita_title());	
		/*
		 * L'immagine di background normalmente è già impostata.
		 * Ne setto una vuota così da non avere lo stile precedente ma per vedere in trasparenza 
		 * lo sfondo della finestra
		 */
//		firmaPredefinitaButton.setBackgroundImage("");
		firmaPredefinitaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				replaceSignature();
			}
			
			private void replaceSignature() {

				if(mDynamicFormBozza != null){
					if(mDynamicFormBozza.getStyleText().equals("text")){
						//Si sta considerando la modalità text						
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_modificaInTestoSemplice_warningMessage(), "", MessageType.WARNING));
					}else{
						// Ottengo la firma impostata come predefinita
						final String nomeFirmaPredefinita = AurigaLayout.getFirmaEmailPredefinita();
						String firmaPredefinita = nomeFirmaPredefinita != null && !nomeFirmaPredefinita.equals("") ? AurigaLayout.getFirmaEmailHtml(nomeFirmaPredefinita) : null;
		
						if (firmaPredefinita != null && !firmaPredefinita.equals("")) {
							// Prelevo i valori del form
							final Map formValues = getMapValues();
		
							GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
							// Pongo un extraparam per il valore della nuova firma
							corpoMailDataSource.extraparam.put("newSignature", firmaPredefinita);
							corpoMailDataSource.performCustomOperation("replaceSignature", new Record(formValues), new DSCallback() {
		
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record record = response.getData()[0];
										
										//Setto il nominativo della firma nella select relativa
										firmaInCalceSelectItem.setValue(nomeFirmaPredefinita);
										
										formValues.put("bodyHtml", record.getAttribute("bodyHtml"));
		
										editNewRecord(formValues);
									}
								}
							}, new DSRequest());
						}
						else{
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_firmaPredefinitaNonPresente_warningMessage(), "", MessageType.WARNING));
						}
					}
				}
			}
		});
		
		
		// Creo la select
		firmaInCalceSelectItem = new SelectItem("firmaInCalce");
		firmaInCalceSelectItem.setValueField("prefName");
		firmaInCalceSelectItem.setDisplayField("prefName");

		firmaInCalceSelectItem.setTitle("<b>" + I18NUtil.getMessages().firme_in_calce_modelliSelectItem_title() + "</b>");
		firmaInCalceSelectItem.setWrapTitle(false); // In questo modo il titolo non viene mandato a capo
		firmaInCalceSelectItem.setCachePickListResults(false);
		firmaInCalceSelectItem.setRedrawOnChange(true);
		firmaInCalceSelectItem.setShowOptionsFromDataSource(true);
		firmaInCalceSelectItem.setOptionDataSource(firmeDS);
		firmaInCalceSelectItem.setAllowEmptyValue(true);

		/*
		 * Visualizzo la select solamente nel caso in cui sia una bozza
		 */
		firmaInCalceSelectItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				Record record = new Record(getMapValues());
				return isBozza(record);
			}
		});

		firmaInCalcePickListProperties = new ListGrid();
		firmaInCalcePickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		firmaInCalcePickListProperties.setShowHeader(false);
		// apply the selected preference from the SelectItem
		firmaInCalcePickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {

				if(mDynamicFormBozza != null){
					if(mDynamicFormBozza.getStyleText().equals("text")){
						//Si sta considerando la modalità text
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_detail_firmaInCalcePickListProperties_warningmessage(), "", MessageType.WARNING));
					}else{
						final String preferenceName = event.getRecord().getAttribute("prefName");
						if (preferenceName != null && !"".equals(preferenceName)) {
							AdvancedCriteria criteria = new AdvancedCriteria();
							criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName);
							firmeDS.fetchData(criteria, new DSCallback() {
		
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									Record[] data = response.getData();
									if (data.length != 0) {
										Record record = data[0];
		
										// Prelevo i valori del form
										final Map formValues = getMapValues();
		
										GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
										// Pongo un extraparam per il valore della nuova firma
										corpoMailDataSource.extraparam.put("newSignature", record.getAttributeAsString("value"));
										corpoMailDataSource.performCustomOperation("replaceSignature", new Record(formValues), new DSCallback() {
		
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
													Record record = response.getData()[0];
		
													formValues.put("bodyHtml", record.getAttribute("bodyHtml"));
		
													editNewRecord(formValues);
												}
											}
										}, new DSRequest());
		
									}
								}
							});
						}
					}
				}
			}
		});

		// Inserisco la picklist
		firmaInCalceSelectItem.setPickListProperties(firmaInCalcePickListProperties);
	}

	private void createFirmeDatasource() {
		firmeDS = UserInterfaceFactory.getPreferenceDataSource();
		firmeDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + "signature.email");
	}

	private Map getMapValues() {
		return vm.getValues();
	}
	
	protected boolean isMessaggioAskChiusuraMailProvenienzaToShow() {
		if (vm != null){
			Record recordPrincipale = new Record(vm.getValues());
			return Boolean.valueOf(recordPrincipale.getAttribute("flgMailPredecessoreStatoLavAperta"));
		}else{
			return false;
		}
	}
	
	protected Map<String, String> generaParametriPopupChiusuraMailProvenienza() {
		
		boolean isOperazioneMassiva = false;
		String nomeOperazione = "";

		if (getValuesManager() != null) {
			Record dettaglioEmail = new Record(getValuesManager().getValues());
			int nroMailPredecessore = dettaglioEmail.getAttributeAsInt("nroMailPredecessore");
			String ruoloVsPredecessore = dettaglioEmail.getAttribute("ruoloVsPredecessore");
			if (nroMailPredecessore == 1){
				// Sono in una risposta o inoltro singolo (bozza)
				isOperazioneMassiva = false;
				nomeOperazione = ruoloVsPredecessore;
			} else {
				// Sono in un inoltro multiplo (bozza)
				isOperazioneMassiva = true;
				nomeOperazione = ruoloVsPredecessore;
			}
		}
		Map<String, String> mappaParametri = new HashMap<String, String>();
		mappaParametri.put("isOperazioneMassiva", isOperazioneMassiva + "");
		mappaParametri.put("nomeOperazione", nomeOperazione);

		mappaParametri = settaAzioniDaFareParam(mappaParametri);
		
		return mappaParametri;
	}
	
	protected Map<String, String> settaAzioniDaFareParam(Map<String, String> mappaParametri){
		Record recordPrincipale = new Record(vm.getValues());
		boolean flgMailPredecessoreConAzioneDaFare = Boolean.valueOf(recordPrincipale.getAttribute("flgMailPredecessoreConAzioneDaFare"));
		String mailPredecessoriUnicaAzioneDaFare = recordPrincipale.getAttribute("mailPredecessoriUnicaAzioneDaFare");
		
		mappaParametri.put("isAzioniDaFarePresenti", flgMailPredecessoreConAzioneDaFare + "");
		mappaParametri.put("nomeAzioneDaFare", mailPredecessoriUnicaAzioneDaFare);
		return mappaParametri;
	}
	
	/**
	 * Viene verificato l'invio separato della mail, se il chek flgInvioSeparato è spuntato e sono presenti
	 * destinatari in CC, l'invio della stessa non è consentito.
	 */
	public Boolean verificaInvioSeparato(Record record) {
		
		boolean verify = true;
		if(record != null){
			if(record.getAttributeAsBoolean("flgInvioSeparato") != null && record.getAttributeAsBoolean("flgInvioSeparato")){
				if(record.getAttributeAsString("destinatariCC") != null && !"".equalsIgnoreCase(record.getAttributeAsString("destinatariCC"))){
					verify = false;
				}
			}
		}
		return verify;
	}

	public String getApplicationName() {
		String applicationName = null;
		try {
			Dictionary dictionary = Dictionary.getDictionary("params");
			applicationName = dictionary != null && dictionary.get("applicationName") != null ? dictionary.get("applicationName") : "";
		} catch (Exception e) {
			applicationName = "";
		}
		return applicationName;
	}
	
	protected void manageProtocollaClick(final boolean richiestaAcessoAttiSue, final boolean protocollaInteraEmail) {

		final Record pRecord = new Record();
		pRecord.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
		pRecord.setAttribute("flgIo", new Record(vm.getValues()).getAttribute("flgIO"));
		pRecord.setAttribute("tsInvio", new Record(vm.getValues()).getAttribute("tsInvio"));
		pRecord.setAttribute("body", new Record(vm.getValues()).getAttribute("body"));
		pRecord.setAttribute("categoria", new Record(vm.getValues()).getAttribute("categoria"));
		final String destinatariEAccount = new Record(vm.getValues()).getAttributeAsString("casellaAccount");
		
		if(richiestaAcessoAttiSue) {
			Record mittente = new Record();
			mittente.setAttribute("emailMittente", pRecord.getAttribute("accountMittente"));
			mittente.setAttribute("tipoMittente", "PF");
			final RecordList listaMittenti = new RecordList();
			listaMittenti.add(mittente);
			pRecord.setAttribute("listaEsibenti", listaMittenti);
		}
		
		RecordList itemLavorazione = new Record(vm.getValues()).getAttributeAsRecordList("listaItemInLavorazione");
		final RecordList fileDaAppunti = new RecordList();
		if(itemLavorazione != null && !itemLavorazione.isEmpty()) {
			// Viene effettuato un check per vedere se c'è almeno una nota di tipologia "file".
			for(int i = 0; i < itemLavorazione.getLength(); i++) {
				Record curRecord = itemLavorazione.get(i);
				if("F".equals(curRecord.getAttributeAsString("itemLavTipo"))) {
					fileDaAppunti.add(curRecord);
				}
			}
		}
		if(!fileDaAppunti.isEmpty()) {
			// A video popup richiesta utente se vuole aggiungere o meno i file. Inserire la lista come extraparam.
			Layout.showConfirmDialogWithWarning("Attenzione", "Sono stati trovati dei file nella sezione Appunti & Note, vuoi aggiungerli come allegati alla protocollazione?", null, null, new BooleanCallback() {
				@Override
				public void execute(Boolean value) {
					
					if(value) {
						GWTRestService<Record, Record> lGwtRestServiceProtocolla = new GWTRestService<Record, Record>("AurigaProtocollaPostaElettronicaDataSource");
						pRecord.setAttribute("fileDaAppunti",fileDaAppunti);
						if (richiestaAcessoAttiSue) {
							lGwtRestServiceProtocolla.extraparam.put("isRichiestaAccessoAtti", "true");
						}
						if(protocollaInteraEmail) {
							lGwtRestServiceProtocolla.extraparam.put("isProtocollaInteraEmail", "true");
							pRecord.setAttribute("uri", new Record(vm.getValues()).getAttribute("uri"));
						}
						lGwtRestServiceProtocolla.call(pRecord, new ServiceCallback<Record>() {

							@Override
							public void execute(final Record object) {
								if (object.getAttribute("warningMsgDoppiaReg") != null && !object.getAttribute("warningMsgDoppiaReg").trim().equals("")) {									
									// Popup con 3 bottoni 
									// a) Procedi => si procede aprendo la maschera di registrazione
									// b) Annulla e chiudi e-mail => si richiama stored di archiviazione mail e si ricarica lista
									// c) Annulla => si chiude alert senza far nulla nè ricaricare lista
									WarningMsgDoppiaRegPopup lWarningMsgDoppiaRegPopup = new WarningMsgDoppiaRegPopup(object.getAttribute("warningMsgDoppiaReg")) {

										@Override
										public void onClickOkButton(Record objectPopup, DSCallback callback) {		
											markForDestroy();
//											manageModificaDatiButtonClick(object.getAttribute("idUd"), destinatariEAccount, pRecord);
											if (richiestaAcessoAttiSue){
												object.setAttribute("azioneDaFareBean", pRecord.getAttributeAsObject("azioneDaFareBean"));
											}
											manageProtocollaPostaElettronicaCallback(object, destinatariEAccount, false, null, richiestaAcessoAttiSue, true);
										}

										@Override
										public void onClickAnnullaChiudiMailButton(Record objectPopup, DSCallback callback) {
											markForDestroy();
											actionArchiviaMail();
										}
									};
									lWarningMsgDoppiaRegPopup.show();	
								} else if (object.getAttribute("idUd") != null && !object.getAttribute("idUd").trim().equals("")) {
									SC.ask(I18NUtil.getMessages().postaElettronica__list_copiaMailGiaProtocollataField(), new BooleanCallback() {
										
										@Override
										public void execute(Boolean value) {
											if (value) {
												manageModificaDatiButtonClick(object.getAttribute("idUd"), destinatariEAccount, pRecord);
											} else {
												SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
													
													@Override
													public void execute(Boolean value) {
														if (value) {
															actionArchiviaMail();
														}
													}
												});
											}
										}
									});
								} else {
									if (richiestaAcessoAttiSue){
										object.setAttribute("azioneDaFareBean", pRecord.getAttributeAsObject("azioneDaFareBean"));
									}
									manageProtocollaPostaElettronicaCallback(object, destinatariEAccount, false, null, richiestaAcessoAttiSue, true);
								}
							}
						});
					} else {
						GWTRestService<Record, Record> lGwtRestServiceProtocolla = new GWTRestService<Record, Record>("AurigaProtocollaPostaElettronicaDataSource");
						if (richiestaAcessoAttiSue) {
							lGwtRestServiceProtocolla.extraparam.put("isRichiestaAccessoAtti", "true");
						}
						if(protocollaInteraEmail) {
							lGwtRestServiceProtocolla.extraparam.put("isProtocollaInteraEmail", "true");
							pRecord.setAttribute("uri", new Record(vm.getValues()).getAttribute("uri"));
						}
						lGwtRestServiceProtocolla.call(pRecord, new ServiceCallback<Record>() {

							@Override
							public void execute(final Record object) {
								if (object.getAttribute("warningMsgDoppiaReg") != null && !object.getAttribute("warningMsgDoppiaReg").trim().equals("")) {									
									// Popup con 3 bottoni 
									// a) Procedi => si procede aprendo la maschera di registrazione
									// b) Annulla e chiudi e-mail => si richiama stored di archiviazione mail e si ricarica lista
									// c) Annulla => si chiude alert senza far nulla nè ricaricare lista
									WarningMsgDoppiaRegPopup lWarningMsgDoppiaRegPopup = new WarningMsgDoppiaRegPopup(object.getAttribute("warningMsgDoppiaReg")) {

										@Override
										public void onClickOkButton(Record objectPopup, DSCallback callback) {		
											markForDestroy();
//											manageModificaDatiButtonClick(object.getAttribute("idUd"), destinatariEAccount, pRecord);
											if (richiestaAcessoAttiSue){
												object.setAttribute("azioneDaFareBean", pRecord.getAttributeAsObject("azioneDaFareBean"));
											}
											manageProtocollaPostaElettronicaCallback(object, destinatariEAccount, false, null, richiestaAcessoAttiSue, true);
										}

										@Override
										public void onClickAnnullaChiudiMailButton(Record objectPopup, DSCallback callback) {
											markForDestroy();
											actionArchiviaMail();
										}
									};
									lWarningMsgDoppiaRegPopup.show();
								} else if (object.getAttribute("idUd") != null && !object.getAttribute("idUd").trim().equals("")) {
									SC.ask(I18NUtil.getMessages().postaElettronica__list_copiaMailGiaProtocollataField(), new BooleanCallback() {
										
										@Override
										public void execute(Boolean value) {
											if (value) {
												manageModificaDatiButtonClick(object.getAttribute("idUd"), destinatariEAccount, pRecord);
											} else {
												SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
													
													@Override
													public void execute(Boolean value) {
														if (value) {
															actionArchiviaMail();
														}
													}
												});
											}
										}
									});
								} else {
									if (richiestaAcessoAttiSue){
										object.setAttribute("azioneDaFareBean", pRecord.getAttributeAsObject("azioneDaFareBean"));
									}
									manageProtocollaPostaElettronicaCallback(object, destinatariEAccount, false, null, richiestaAcessoAttiSue, true);
								}
							}
						});
					}
				}
			});		
		} else {
			GWTRestService<Record, Record> lGwtRestServiceProtocolla = new GWTRestService<Record, Record>("AurigaProtocollaPostaElettronicaDataSource");
			if (richiestaAcessoAttiSue) {
				lGwtRestServiceProtocolla.extraparam.put("isRichiestaAccessoAtti", "true");
			}
			if(protocollaInteraEmail) {
				lGwtRestServiceProtocolla.extraparam.put("isProtocollaInteraEmail", "true");
				pRecord.setAttribute("uri", new Record(vm.getValues()).getAttribute("uri"));
			}
			lGwtRestServiceProtocolla.call(pRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(final Record object) {
					if (object.getAttribute("warningMsgDoppiaReg") != null && !object.getAttribute("warningMsgDoppiaReg").trim().equals("")) {
						// Popup con 3 bottoni 
						// a) Procedi => si procede aprendo la maschera di registrazione
						// b) Annulla e chiudi e-mail => si richiama stored di archiviazione mail e si ricarica lista
						// c) Annulla => si chiude alert senza far nulla nè ricaricare lista
						WarningMsgDoppiaRegPopup lWarningMsgDoppiaRegPopup = new WarningMsgDoppiaRegPopup(object.getAttribute("warningMsgDoppiaReg")) {

							@Override
							public void onClickOkButton(Record objectPopup, DSCallback callback) {		
								markForDestroy();
//								manageModificaDatiButtonClick(object.getAttribute("idUd"), destinatariEAccount, pRecord);
								if (richiestaAcessoAttiSue){
									object.setAttribute("azioneDaFareBean", pRecord.getAttributeAsObject("azioneDaFareBean"));
								}
								manageProtocollaPostaElettronicaCallback(object, destinatariEAccount, false, null, richiestaAcessoAttiSue, true);
							}

							@Override
							public void onClickAnnullaChiudiMailButton(Record objectPopup, DSCallback callback) {
								markForDestroy();
								actionArchiviaMail();
							}
						};
						lWarningMsgDoppiaRegPopup.show();	
					} else if (object.getAttribute("idUd") != null && !object.getAttribute("idUd").trim().equals("")) {
						SC.ask(I18NUtil.getMessages().postaElettronica__list_copiaMailGiaProtocollataField(), new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								if (value) {
									manageModificaDatiButtonClick(object.getAttribute("idUd"), destinatariEAccount, pRecord);
								} else {
									SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
										
										@Override
										public void execute(Boolean value) {
											if (value) {
												actionArchiviaMail();
											}
										}
									});
								}
							}
						});
					} else {
						if (richiestaAcessoAttiSue){
							object.setAttribute("azioneDaFareBean", pRecord.getAttributeAsObject("azioneDaFareBean"));
						}
						manageProtocollaPostaElettronicaCallback(object, destinatariEAccount, false, null, richiestaAcessoAttiSue, true);
					}
				}
			});
		}
	}
	
	protected void manageProtocollaPostaElettronicaCallback(final Record lRecord, final String destinatariEAccount, final boolean isThereIdUdMail, 
			final Record recordMail, final boolean richiestaAcessoAttiSue, final boolean isNewReg) {
		
		if(richiestaAcessoAttiSue) {
			if(!isNewReg) {
				editaProtocolloWindowFromMail(lRecord,destinatariEAccount,isThereIdUdMail,recordMail,richiestaAcessoAttiSue);
			} else {
			AurigaLayout.apriSceltaTipoRichiestaAccessoAttiPopup(null, new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecordTipoDoc) {
					
					String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
					String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
					
					lRecord.setAttribute("tipoDocumento", idTipoDoc);
					lRecord.setAttribute("nomeTipoDocumento", nomeTipoDoc);
					editaProtocolloWindowFromMail(lRecord,destinatariEAccount,isThereIdUdMail,recordMail,richiestaAcessoAttiSue);
				}
			});
			}
		} else {
			if(!isNewReg) {
				editaProtocolloWindowFromMail(lRecord,destinatariEAccount,isThereIdUdMail,recordMail,richiestaAcessoAttiSue);
			} else {
				if(!AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaEntrata")) {
					String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoProtEntrata");
					AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "PG", null, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record lRecordTipoDoc) {
							
							String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
							String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;	
							
							lRecord.setAttribute("tipoDocumento", idTipoDoc);
							lRecord.setAttribute("nomeTipoDocumento", nomeTipoDoc);
							editaProtocolloWindowFromMail(lRecord,destinatariEAccount,isThereIdUdMail,recordMail,richiestaAcessoAttiSue);
						}
					});
				} else {
					String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoProtEntrata");
					String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoProtEntrata");
					
					lRecord.setAttribute("tipoDocumento", idTipoDoc);
					lRecord.setAttribute("nomeTipoDocumento", nomeTipoDoc);
					editaProtocolloWindowFromMail(lRecord,destinatariEAccount,isThereIdUdMail,recordMail,richiestaAcessoAttiSue);
				}
			}
		}
	}
	
	private void editaProtocolloWindowFromMail(Record lRecord, String destinatariEAccount, boolean isThereIdUdMail, Record recordMail, boolean richiestaAcessoAttiSue) {
		String flgTipoProv = lRecord.getAttribute("flgTipoProv");
		if (destinatariEAccount != null) {
			lRecord.setAttribute("casellaEmailDestinatario", destinatariEAccount); // viene settato il campo casellaEmailDestinatario del bean
		}
		if (recordMail != null) {
			recordMail = new Record ();
			recordMail.setAttribute("azioneDaFareBean", new Record((JavaScriptObject) vm.getValue("azioneDaFareBean")));
			recordMail.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
			recordMail.setAttribute("id", new Record(vm.getValues()).getAttribute("id"));
			recordMail.setAttribute("flgIo", new Record(vm.getValues()).getAttribute("flgIO"));
			recordMail.setAttribute("flgStatoProt", new Record(vm.getValues()).getAttribute("flgStatoProtocollazione"));
		}
		String title = "";
		if(isThereIdUdMail) {
			title = "protocollazione_entrata";
		} else {
			title = "protocollazione_mail";
		}
		if (richiestaAcessoAttiSue) {
			lRecord.setAttribute("protocolloAccessoAttiSueDaEmail", true);
			Record esibente = new Record();
			esibente.setAttribute("email", lRecord.getAttribute("indirizzo"));
			esibente.setAttribute("flgAncheMittente", true);
			final RecordList listaEsibenti = new RecordList();
			listaEsibenti.add(esibente);
			lRecord.setAttribute("listaEsibenti", listaEsibenti);
			lRecord.setAttribute("listaMittenti", (RecordList) null);
		}
		EditaProtocolloWindowFromMail lEditaProtocolloWindowFromMail = new EditaProtocolloWindowFromMail(title, lRecord, flgTipoProv, isThereIdUdMail, recordMail, instance, null) {

			@Override
			public void manageAfterCloseWindow() {
				ricaricaPagina = true;
				reloadDetail();
			}
		};
		lEditaProtocolloWindowFromMail.show();
		//Controllo se ci sono errori di decompressione da visualizzare
		if(lRecord.getAttribute("erroriArchivi") != null) {
			for(String err : lRecord.getAttributeAsStringArray("erroriArchivi")) {
				AurigaLayout.addMessage(new MessageBean(err, "", MessageType.WARNING));
			}
		}
		//Controllo se ci sono estensioni non riconosciute.
		if(lRecord.getAttribute("erroriEstensioniFile") != null) {
			for(String err : lRecord.getAttributeAsStringArray("erroriEstensioniFile")) {
				AurigaLayout.addMessage(new MessageBean(err, "", MessageType.WARNING));
			}
		}
	}

	protected void manageModificaDatiButtonClick(String idUd, final String destinatariEAccount,final Record recordMail) {
		markForRedraw();
		manageLoadDetailIdUdMail(idUd, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record detailRecord = response.getData()[0];
						manageProtocollaPostaElettronicaCallback(detailRecord, destinatariEAccount, true, recordMail, false, false);
				}
			}

		});
	}
	
	private void manageLoadDetailIdUdMail(String idUd,final DSCallback callback) {
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		// lRecordToLoad.setAttribute("segnatura", record.getAttribute("segnatura"));
		lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(final DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					callback.execute(response, null, new DSRequest());
				}
			}
		});
	}

	private void manageRegistraRepertorio(final boolean protocollaInteraEmail) {

		Record lRecordRepertorio = new Record();
		lRecordRepertorio.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
		lRecordRepertorio.setAttribute("flgIo", new Record(vm.getValues()).getAttribute("flgIO"));
		final String destinatariEAccount = new Record(vm.getValues()).getAttributeAsString("casellaAccount");
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
		lGwtRestDataSource.performCustomOperation("get", lRecordRepertorio, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					// recupero i dettagli relativi alla mail per ottenere la lista dei file nella sezione Appunti e note.
					final Record lRecord = response.getData()[0];
					RecordList itemLavorazione = lRecord.getAttributeAsRecordList("listaItemInLavorazione");
					final RecordList fileDaAppunti = new RecordList();
					if(itemLavorazione != null && !itemLavorazione.isEmpty()) {
						// Viene effettuato un check per vedere se c'è almeno una nota di tipologia "file".
						for(int i = 0; i < itemLavorazione.getLength(); i++) {
							Record curRecord = itemLavorazione.get(i);
							if("F".equals(curRecord.getAttributeAsString("itemLavTipo"))) {
								fileDaAppunti.add(curRecord);
							}
						}
					}
					if(!fileDaAppunti.isEmpty()) {
						// A video popup richiesta utente se vuole aggiungere o meno i file. Inserire la lista come extraparam.
						Layout.showConfirmDialogWithWarning("Attenzione","Sono stati trovati dei file nella sezione Appunti & Note, vuoi aggiungerli come allegati alla repertoriazione?", null, null, new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								
								if(value) {
									lRecord.setAttribute("fileDaAppunti",fileDaAppunti); //Aggiunta della lista dei file recuperati alla protocollazione.
									GWTRestService<Record, Record> lGwtRestServiceProtocolla = new GWTRestService<Record, Record>("AurigaProtocollaPostaElettronicaDataSource");
									if(protocollaInteraEmail) {
										lGwtRestServiceProtocolla.extraparam.put("isProtocollaInteraEmail", "true");
										lRecord.setAttribute("uri", new Record(vm.getValues()).getAttribute("uri"));
									}
									lGwtRestServiceProtocolla.call(lRecord, new ServiceCallback<Record>() {

										@Override
										public void execute(final Record object) {
											if (object.getAttribute("warningMsgDoppiaReg") != null && !object.getAttribute("warningMsgDoppiaReg").trim().equals("")) {												
												// Popup con 3 bottoni 
												// a) Procedi => si procede aprendo la maschera di registrazione
												// b) Annulla e chiudi e-mail => si richiama stored di archiviazione mail e si ricarica lista
												// c) Annulla => si chiude alert senza far nulla nè ricaricare lista
												WarningMsgDoppiaRegPopup lWarningMsgDoppiaRegPopup = new WarningMsgDoppiaRegPopup(object.getAttribute("warningMsgDoppiaReg")) {

													@Override
													public void onClickOkButton(Record objectPopup, DSCallback callback) {		
														markForDestroy();
//														manageModificaDatiRepertorioButtonClick(object.getAttribute("idUd"), destinatariEAccount, lRecord);
														manageRegistraRepertorioCallback(object, destinatariEAccount, false, null, true);
													}

													@Override
													public void onClickAnnullaChiudiMailButton(Record objectPopup, DSCallback callback) {
														markForDestroy();
														actionArchiviaMail();
													}
												};
												lWarningMsgDoppiaRegPopup.show();
											} else if (object.getAttribute("idUd") != null && !object.getAttribute("idUd").trim().equals("")) {
												SC.ask(I18NUtil.getMessages().postaElettronica__list_copiaMailGiaProtocollataField(), new BooleanCallback() {
													
													@Override
													public void execute(Boolean value) {
														if (value) {
															manageModificaDatiRepertorioButtonClick(object.getAttribute("idUd"), destinatariEAccount, lRecord);
														} else {
															SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
																
																@Override
																public void execute(Boolean value) {
																	if (value) {
																		actionArchiviaMail();
																	}
																}
															});
														}
													}
												});
											} else {
												manageRegistraRepertorioCallback(object, destinatariEAccount, false, null, true);
											}
										}
									});
								} else {
									GWTRestService<Record, Record> lGwtRestServiceProtocolla = new GWTRestService<Record, Record>("AurigaProtocollaPostaElettronicaDataSource");
									if(protocollaInteraEmail) {
										lGwtRestServiceProtocolla.extraparam.put("isProtocollaInteraEmail", "true");
										lRecord.setAttribute("uri", new Record(vm.getValues()).getAttribute("uri"));
									}
									lGwtRestServiceProtocolla.call(lRecord, new ServiceCallback<Record>() {

										@Override
										public void execute(final Record object) {
											if (object.getAttribute("warningMsgDoppiaReg") != null && !object.getAttribute("warningMsgDoppiaReg").trim().equals("")) {
												// Popup con 3 bottoni 
												// a) Procedi => si procede aprendo la maschera di registrazione
												// b) Annulla e chiudi e-mail => si richiama stored di archiviazione mail e si ricarica lista
												// c) Annulla => si chiude alert senza far nulla nè ricaricare lista
												WarningMsgDoppiaRegPopup lWarningMsgDoppiaRegPopup = new WarningMsgDoppiaRegPopup(object.getAttribute("warningMsgDoppiaReg")) {

													@Override
													public void onClickOkButton(Record objectPopup, DSCallback callback) {		
														markForDestroy();
//														manageModificaDatiRepertorioButtonClick(object.getAttribute("idUd"), destinatariEAccount, lRecord);
														manageRegistraRepertorioCallback(object, destinatariEAccount, false, null, true);
													}

													@Override
													public void onClickAnnullaChiudiMailButton(Record objectPopup, DSCallback callback) {
														markForDestroy();
														actionArchiviaMail();
													}
												};
												lWarningMsgDoppiaRegPopup.show();
											} else if (object.getAttribute("idUd") != null && !object.getAttribute("idUd").trim().equals("")) {
												SC.ask(I18NUtil.getMessages().postaElettronica__list_copiaMailGiaProtocollataField(), new BooleanCallback() {
													
													@Override
													public void execute(Boolean value) {
														if (value) {
															manageModificaDatiRepertorioButtonClick(object.getAttribute("idUd"), destinatariEAccount, lRecord);
														} else {
															SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
																
																@Override
																public void execute(Boolean value) {
																	if (value) {
																		actionArchiviaMail();
																	}
																}
															});
														}
													}
												});
											} else {
												manageRegistraRepertorioCallback(object, destinatariEAccount, false, null, true);
											}
										}
									});
								}
							}
						});		
					} else {
						GWTRestService<Record, Record> lGwtRestServiceProtocolla = new GWTRestService<Record, Record>("AurigaProtocollaPostaElettronicaDataSource");
						if(protocollaInteraEmail) {
							lGwtRestServiceProtocolla.extraparam.put("isProtocollaInteraEmail", "true");
							lRecord.setAttribute("uri", new Record(vm.getValues()).getAttribute("uri"));
						}
						lGwtRestServiceProtocolla.call(lRecord, new ServiceCallback<Record>() {

							@Override
							public void execute(final Record object) {
								if (object.getAttribute("warningMsgDoppiaReg") != null && !object.getAttribute("warningMsgDoppiaReg").trim().equals("")) {									
									// Popup con 3 bottoni 
									// a) Procedi => si procede aprendo la maschera di registrazione
									// b) Annulla e chiudi e-mail => si richiama stored di archiviazione mail e si ricarica lista
									// c) Annulla => si chiude alert senza far nulla nè ricaricare lista
									WarningMsgDoppiaRegPopup lWarningMsgDoppiaRegPopup = new WarningMsgDoppiaRegPopup(object.getAttribute("warningMsgDoppiaReg")) {

										@Override
										public void onClickOkButton(Record objectPopup, DSCallback callback) {		
											markForDestroy();
//											manageModificaDatiRepertorioButtonClick(object.getAttribute("idUd"), destinatariEAccount, lRecord);
											manageRegistraRepertorioCallback(object, destinatariEAccount, false, null, true);
										}

										@Override
										public void onClickAnnullaChiudiMailButton(Record objectPopup, DSCallback callback) {
											markForDestroy();
											actionArchiviaMail();
										}
									};
									lWarningMsgDoppiaRegPopup.show();
								} else if (object.getAttribute("idUd") != null && !object.getAttribute("idUd").trim().equals("")) {
									SC.ask(I18NUtil.getMessages().postaElettronica__list_copiaMailGiaProtocollataField(), new BooleanCallback() {
										
										@Override
										public void execute(Boolean value) {
											if (value) {
												manageModificaDatiRepertorioButtonClick(object.getAttribute("idUd"), destinatariEAccount, lRecord);
											} else {
												SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
													
													@Override
													public void execute(Boolean value) {
														if (value) {
															actionArchiviaMail();
														}
													}
												});
											}
										}
									});
								} else {
									manageRegistraRepertorioCallback(object, destinatariEAccount, false, null, true);
								}
							}
						});
					}
				}
			}
		}, new DSRequest());
	}
	
	protected void manageModificaDatiRepertorioButtonClick(String idUd, final String destinatariEAccount, final Record recordMail) {
		markForRedraw();
		manageLoadDetailIdUdMail(idUd, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record detailRecord = response.getData()[0];
					manageRegistraRepertorioCallback(detailRecord, destinatariEAccount, true, recordMail, false);
				}
			}
		});
	}

	private void manageRegistraRepertorioCallback(final Record lRecord, final String destinatariEAccount,
			final boolean isThereIdUdMail, final Record recordMail, final boolean isNewReg){
		
		if(!isNewReg) {
			editaRepertorioWindowFromEmail(lRecord, destinatariEAccount, null, isThereIdUdMail, recordMail, null, null);
		} else {
			if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaRepertorioEntrata")) {
				final String repertorioEntrata = AurigaLayout.getImpostazioniDocumento("repertorioEntrata");
				if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaRepertorioEntrata")) {
					String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioEntrata");
					String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoRepertorioEntrata");			
					editaRepertorioWindowFromEmail(lRecord, destinatariEAccount, repertorioEntrata, isThereIdUdMail, recordMail, idTipoDoc, nomeTipoDoc);
				} else {
					String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioEntrata");
					AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "R", repertorioEntrata, new ServiceCallback<Record>() {
	
						@Override
						public void execute(Record lRecordTipoDoc) {
							String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
							String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
							editaRepertorioWindowFromEmail(lRecord, destinatariEAccount, repertorioEntrata, isThereIdUdMail, recordMail, idTipoDoc, nomeTipoDoc);					
						}
					});
				}
			} else {
				final String repertorioEntrataDefault = AurigaLayout.getImpostazioniDocumento("repertorioEntrata");	
				AurigaLayout.apriSceltaRepertorioPopup("E", repertorioEntrataDefault, new ServiceCallback<Record>() {
	
					@Override
					public void execute(Record lRecordRepertorio) {
	
						final String repertorioEntrata = lRecordRepertorio != null ? lRecordRepertorio.getAttribute("repertorio") : null;
						if (repertorioEntrata != null && !"".equals(repertorioEntrata)) {
							if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaRepertorioEntrata")) {
								String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioEntrata");
								String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoRepertorioEntrata");
								editaRepertorioWindowFromEmail(lRecord, destinatariEAccount, repertorioEntrata, isThereIdUdMail, recordMail, idTipoDoc, nomeTipoDoc);						
							} else {
								String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioEntrata");
								AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "R", repertorioEntrata, new ServiceCallback<Record>() {
	
									@Override
									public void execute(Record lRecordTipoDoc) {
										String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
										String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
										editaRepertorioWindowFromEmail(lRecord, destinatariEAccount, repertorioEntrata, isThereIdUdMail, recordMail, idTipoDoc, nomeTipoDoc);								
									}
								});
							}
						}
					}
				});
			}
		}
	}
	
	private void editaRepertorioWindowFromEmail(Record lRecord, String destinatariEAccount,
			String repertorio, boolean isThereIdUdMail, Record recordMail,String idTipoDoc, String nomeTipoDoc) {
		
		if (destinatariEAccount != null) {
			lRecord.setAttribute("casellaEmailDestinatario", destinatariEAccount); // viene settato il campo casellaEmailDestinatario del bean
		}
		if (recordMail != null) {
			recordMail = new Record ();
			recordMail.setAttribute("azioneDaFareBean", new Record((JavaScriptObject) vm.getValue("azioneDaFareBean")));
			recordMail.setAttribute("idEmail", new Record(vm.getValues()).getAttribute("idEmail"));
			recordMail.setAttribute("id", new Record(vm.getValues()).getAttribute("id"));
			recordMail.setAttribute("flgIo", new Record(vm.getValues()).getAttribute("flgIO"));
			recordMail.setAttribute("flgStatoProt", new Record(vm.getValues()).getAttribute("flgStatoProtocollazione"));
		}
		String title = "";
		if(isThereIdUdMail) {
			title = "repertorio_entrata";
		} else {
			title = "repertorio_mail";
		}
		lRecord.setAttribute("tipoDocumento", idTipoDoc);
		lRecord.setAttribute("nomeTipoDocumento", nomeTipoDoc);
		lRecord.setAttribute("repertorio", repertorio);

		RegistraRepertorioEmailWindow lRegistraRepertorioEmailWindow = new RegistraRepertorioEmailWindow(title, lRecord, "E", isThereIdUdMail, recordMail, instance, null) {

			@Override
			public void manageAfterCloseWindow() {
				ricaricaPagina = true;
				reloadDetail();
			}
		};
		lRegistraRepertorioEmailWindow.show();
		//Controllo se ci sono errori di decompressione da visualizzare
		if(lRecord.getAttribute("erroriArchivi") != null) {
			for(String err : lRecord.getAttributeAsStringArray("erroriArchivi")) {
				AurigaLayout.addMessage(new MessageBean(err, "", MessageType.WARNING));
			}
		}
		//Controllo se ci sono estensioni non riconosciute.
		if(lRecord.getAttribute("erroriEstensioniFile") != null) {
			for(String err : lRecord.getAttributeAsStringArray("erroriEstensioniFile")) {
				AurigaLayout.addMessage(new MessageBean(err, "", MessageType.WARNING));
			}
		}
	}
}