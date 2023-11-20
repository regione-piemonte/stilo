/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioMail.InoltroMailWindow;
import it.eng.auriga.ui.module.layout.client.invioMail.InvioNotificaInteropWindow;
import it.eng.auriga.ui.module.layout.client.invioMail.RispostaMailWindow;
import it.eng.auriga.ui.module.layout.client.postainuscita.RicevutePostaInUscitaWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.EditaProtocolloWindowFromMail;
import it.eng.auriga.ui.module.layout.shared.util.AzioniRapide;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.HtmlItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class PostaElettronicaDetail extends CustomDetail {

	protected PostaElettronicaDetail _instance;
	protected DynamicForm _form;

	protected HiddenItem flgIo;
	protected HiddenItem idEmail;
	protected StaticTextItem statoConsolidamento;
	protected NestedFormItem infoButtons;
	protected ImgButtonItem inviataRisposta;
	protected ImgButtonItem inoltrata;
	protected NestedFormItem notificheInteropButtons;
	protected ImgButtonItem notificaEccezione;
	protected ImgButtonItem notificaConferma;
	protected ImgButtonItem notificaAggiornamento;
	protected ImgButtonItem notificaAnnullamento;
	protected ImgButtonItem notificheCollegate;
	protected DateTimeItem tsInvio;
	protected TextItem accountMittente;
	protected DettaglioEmailDestinatarioItem destinatariPrincipali;
	protected DettaglioEmailDestinatarioItem destinatariCC;
	protected DettaglioEmailDestinatarioItem destinatariCCn;
	protected DettaglioEmailAllegatoItem allegati;
	protected TextItem oggetto;
	protected HtmlItem corpo;
	protected ImgButtonItem download;
	protected TextAreaItem avvertimenti;

	protected HiddenItem uriEmail;
	protected HiddenItem dateRif;

	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton protocollaDetailButton;
	protected DetailToolStripButton rispondiDetailButton;
	protected DetailToolStripButton rispondiATuttiDetailButton;
	protected DetailToolStripButton inoltraDetailButton;
	protected DetailToolStripButton inviaNotificaDetailButton;
	protected DetailToolStripButton inviaNotificaConfermaDetailButton;
	protected DetailToolStripButton inviaNotificaEccezioneDetailButton;
	protected DetailToolStripButton inviaNotificaAggiornamentoDetailButton;
	protected DetailToolStripButton inviaNotificaAnnullamentoDetailButton;
	protected DetailToolStripButton archiviazioneDetailButton;
	protected DetailToolStripButton assegnaDetailButton;
	protected DetailToolStripButton ricevutePostaInUscitaDetailButton;

	public boolean verificaProtocollazione = false;

	protected boolean editing;
	private String tipoRel;

	private String classifica;

	public PostaElettronicaDetail(String nomeEntita) {
		this(nomeEntita, null, null, null, null);
	}

	public PostaElettronicaDetail(String nomeEntita, String pTipoRel) {
		this(nomeEntita, pTipoRel, null, null, null);
	}

	public PostaElettronicaDetail(String pNomeEntita, String pTipoRel, final String pClassifica, Record listRecord, Record abilitazioni) {

		super(pNomeEntita);

		_instance = this;

		tipoRel = pTipoRel;

		classifica = pClassifica;
		setName("main_layout");

		// ******************************************************************************************
		// Sezione DATI PRINCIPALI
		// ******************************************************************************************

		// Creo il form
		_form = new DynamicForm();
		_form.setValuesManager(vm);
		_form.setWidth100();
		_form.setHeight100();
		_form.setPadding(5);
		_form.setWrapItemTitles(false);
		_form.setNumCols(2);
		_form.setColWidths("150", "*");

		flgIo = new HiddenItem("flgIo");
		idEmail = new HiddenItem("idEmail");

		buildStatoConsolidamento();

		// inviata risposta
		buildInviataRisposta();
		// inoltrata
		buildInoltrata();

		SpacerItem spacerRispostaInoltrataItem = new SpacerItem();
		spacerRispostaInoltrataItem.setColSpan(1);
		spacerRispostaInoltrataItem.setStartRow(true);
		spacerRispostaInoltrataItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean inviataRispostaPresente = vm.getValueAsString("inviataRispostaPresente") != null
						&& vm.getValueAsString("inviataRispostaPresente").equals("true");
				boolean inoltrataPresente = vm.getValueAsString("inoltrataPresente") != null && vm.getValueAsString("inoltrataPresente").equals("true");
				return inviataRispostaPresente || inoltrataPresente;
			}
		});

		infoButtons = new NestedFormItem("infoButtons");
		infoButtons.setShowTitle(false);
		infoButtons.setColSpan(1);
		infoButtons.setEndRow(true);
		infoButtons.setWidth(48);
		infoButtons.setNumCols(3);
		infoButtons.setColWidths(16, 16, 16);
		infoButtons.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean inviataRispostaPresente = vm.getValueAsString("inviataRispostaPresente") != null
						&& vm.getValueAsString("inviataRispostaPresente").equals("true");
				boolean inoltrataPresente = vm.getValueAsString("inoltrataPresente") != null && vm.getValueAsString("inoltrataPresente").equals("true");
				return inviataRispostaPresente || inoltrataPresente;
			}
		});
		infoButtons.setNestedFormItems(inviataRisposta, inoltrata);

		// eccezione
		buildEccezione();
		// conferme
		buildConferme();
		// aggiornamento
		buildAggiornamento();
		// annullamento
		buildAnnullamento();

		SpacerItem spacerNotificheInteropItem = new SpacerItem();
		spacerNotificheInteropItem.setColSpan(1);
		spacerNotificheInteropItem.setStartRow(true);
		spacerNotificheInteropItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean notificaEccezionePresente = vm.getValueAsString("notificaEccezionePresente") != null
						&& vm.getValueAsString("notificaEccezionePresente").equals("true");
				boolean notificaConfermaPresente = vm.getValueAsString("notificaConfermaPresente") != null
						&& vm.getValueAsString("notificaConfermaPresente").equals("true");
				boolean notificaAggiornamentoPresente = vm.getValueAsString("notificaAggiornamentoPresente") != null
						&& vm.getValueAsString("notificaAggiornamentoPresente").equals("true");
				boolean notificaAnnullamentoPresente = vm.getValueAsString("notificaAnnullamentoPresente") != null
						&& vm.getValueAsString("notificaAnnullamentoPresente").equals("true");
				return vm.getValueAsString("flgIo") != null && "O".equals(vm.getValueAsString("flgIo"))
						&& (notificaEccezionePresente || notificaConfermaPresente || notificaAggiornamentoPresente || notificaAnnullamentoPresente);
			}
		});

		notificheInteropButtons = new NestedFormItem("notificheInteropButtons");
		notificheInteropButtons.setShowTitle(false);
		notificheInteropButtons.setColSpan(1);
		notificheInteropButtons.setEndRow(true);
		notificheInteropButtons.setWidth(64);
		notificheInteropButtons.setNumCols(4);
		notificheInteropButtons.setColWidths(16, 16, 16, 16);
		notificheInteropButtons.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean notificaEccezionePresente = vm.getValueAsString("notificaEccezionePresente") != null
						&& vm.getValueAsString("notificaEccezionePresente").equals("true");
				boolean notificaConfermaPresente = vm.getValueAsString("notificaConfermaPresente") != null
						&& vm.getValueAsString("notificaConfermaPresente").equals("true");
				boolean notificaAggiornamentoPresente = vm.getValueAsString("notificaAggiornamentoPresente") != null
						&& vm.getValueAsString("notificaAggiornamentoPresente").equals("true");
				boolean notificaAnnullamentoPresente = vm.getValueAsString("notificaAnnullamentoPresente") != null
						&& vm.getValueAsString("notificaAnnullamentoPresente").equals("true");
				return vm.getValueAsString("flgIo") != null && "O".equals(vm.getValueAsString("flgIo"))
						&& (notificaEccezionePresente || notificaConfermaPresente || notificaAggiornamentoPresente || notificaAnnullamentoPresente);
			}
		});
		notificheInteropButtons.setNestedFormItems(notificaEccezione, notificaConferma, notificaAggiornamento, notificaAnnullamento);

		notificheCollegate = new ImgButtonItem("notificheCollegate", "buttons/altriDati.png", "Notifiche collegate");
		notificheCollegate.setShowTitle(true);
		notificheCollegate.setAlwaysEnabled(true);
		notificheCollegate.setTitle("Notifiche collegate");
		notificheCollegate.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				final String idEmail = vm.getValueAsString("idEmail");
				GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("EmailCollegateDataSource", "idEmail", FieldType.TEXT);
				lGWTRestDataSource.addParam("idEmail", idEmail);
				lGWTRestDataSource.addParam("tipoRel", "notifica");
				lGWTRestDataSource.fetchData(null, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						RecordList data = response.getDataAsRecordList();
						EmailCollegateWindow emailRispostaCollegateWindow = null;
						if (data.getLength() == 0) {
							Layout.addMessage(new MessageBean("Non ci sono notifiche legate alla e-mail", "", MessageType.INFO));
						} else if (data.getLength() == 1) {
							emailRispostaCollegateWindow = new EmailCollegateWindow("notifica", idEmail, "1", data.get(0));
							emailRispostaCollegateWindow.show();
						} else if (data.getLength() > 0) {
							emailRispostaCollegateWindow = new EmailCollegateWindow("notifica", idEmail, null, null);
							emailRispostaCollegateWindow.show();
						}
					}
				});
			}
		});
		// MARINA MODIFICA
		// tsInvio = new DateTimeItem("tsInvio", "Inviata il");
		tsInvio = new DateTimeItem("tsInvio", I18NUtil.getMessages().posta_elettronica_detail_tsInvio());
		tsInvio.setStartRow(true);
		tsInvio.setEndRow(true);
		tsInvio.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("tsInvio") != null;
			}
		});
		// MARINA MODIFICA
		// accountMittente = new TextItem("accountMittente", "Mittente");
		accountMittente = new TextItem("accountMittente", I18NUtil.getMessages().posta_elettronica_detail_accountMittente());
		accountMittente.setWidth(480);
		accountMittente.setStartRow(true);
		accountMittente.setEndRow(true);

		destinatariPrincipali = new DettaglioEmailDestinatarioItem();
		destinatariPrincipali.setName("destinatariPrincipali");
		destinatariPrincipali.setTitle("Destinatari principali");
		destinatariPrincipali.setCanEdit(false);

		destinatariCC = new DettaglioEmailDestinatarioItem();
		destinatariCC.setName("destinatariCC");
		// MARINA MODIFICA
		// destinatariCC.setTitle("Destinatari cc");
		destinatariCC.setTitle(I18NUtil.getMessages().posta_elettronica_detail_destinatariCC());
		destinatariCC.setCanEdit(false);
		destinatariCC.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("destinatariCC") != null;
			}
		});

		destinatariCCn = new DettaglioEmailDestinatarioItem();
		destinatariCCn.setName("destinatariCCn");
		// MARINA MODIFICA
		// destinatariCCn.setTitle("Destinatari ccn");
		destinatariCCn.setTitle(I18NUtil.getMessages().posta_elettronica_detail_destinatariCCn());
		destinatariCCn.setCanEdit(false);
		destinatariCCn.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("destinatariCCn") != null;
			}
		});

		allegati = new DettaglioEmailAllegatoItem() {

			public void downloadFile(final ServiceCallback<Record> lDsCallback) {

				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaLoadDettaglioEmailDataSource");
				lGwtRestDataSource.executecustom("retrieveAttach", getDetailRecord(), new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS)
							realEditRecord(response.getData()[0]);
						lDsCallback.execute(response.getData()[0]);
					}
				});
			};
		};
		allegati.setName("allegati");
		// MARINA MODIFICA
		// allegati.setTitle("Allegati");
		allegati.setTitle(I18NUtil.getMessages().posta_elettronica_detail_allegati());
		allegati.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("allegati") != null;
			}
		});
		// MARINA MODIFICA
		// oggetto = new TextItem("oggetto", "Oggetto");
		oggetto = new TextItem("oggetto", I18NUtil.getMessages().posta_elettronica_detail_oggetto());
		oggetto.setWidth(480);
		oggetto.setStartRow(true);
		oggetto.setEndRow(true);

		corpo = new HtmlItem();
		// MARINA MODIFICA
		// corpo.setTitle("Corpo");
		corpo.setTitle(I18NUtil.getMessages().posta_elettronica_detail_corpo());
		corpo.setName("corpo");
		corpo.setWidth(650);
		corpo.setHeight(100);
		corpo.setStartRow(true);
		corpo.setEndRow(true);
		// MARINA MODIFICA
		// download = new ImgButtonItem("download", "file/download_manager.png", "Scarica eml");
		download = new ImgButtonItem("download", "file/download_manager.png", I18NUtil.getMessages().posta_elettronica_detail_download());
		download.setAlwaysEnabled(true);
		download.setShowTitle(true);
		// MARINA MODIFICA
		// download.setTitle("Scarica eml");
		download.setTitle(I18NUtil.getMessages().posta_elettronica_detail_download());
		download.setColSpan(2);
		// download.setWidth(150);
		download.setIconWidth(16);
		download.setTextAlign(Alignment.LEFT);
		download.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				if (vm.getAttribute("uriMail") == null || vm.getAttribute("uriMail").equals("")) {
					Record input = new Record();
					input.setAttribute("idEmail", vm.getAttribute("idEmail"));
					GWTRestDataSource downloadFromIdDS = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource");
					downloadFromIdDS.executecustom("getUriFromIdEmail", input, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {

							Record lRecord = new Record();
							lRecord.setAttribute("displayFilename", vm.getValue("progrDownloadStampa") + ".eml");
							lRecord.setAttribute("uri", vm.getValue("uri"));
							lRecord.setAttribute("sbustato", "false");
							lRecord.setAttribute("remoteUri", "true");
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
		});

		// avvertimenti
		avvertimenti = new TextAreaItem("avvertimenti",
				"&nbsp;&nbsp;&nbsp;<img src=\"images/warning.png\" style=\"vertical-align:bottom\"/>&nbsp;<font color=\"red\"><b>"
						+ I18NUtil.getMessages().posta_elettronica_detail_avvertimenti() + "<b/></font>");
		avvertimenti.setEndRow(true);
		avvertimenti.setColSpan(4);
		avvertimenti.setHeight(60);
		avvertimenti.setWidth(650);
		avvertimenti.setCanEdit(false);
		avvertimenti.setVisible(true);
		avvertimenti.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("flgIo") != null && "I".equals(vm.getValueAsString("flgIo")) && vm.getValueAsString("avvertimenti") != null
						&& !"".equals(vm.getValueAsString("avvertimenti"));
			}
		});

		uriEmail = new HiddenItem("uriEmail");

		dateRif = new HiddenItem("dateRif");

		if (tipoRel != null && "notifica".equals(tipoRel)) {
			_form.setFields(flgIo, idEmail, spacerRispostaInoltrataItem, infoButtons, tsInvio, accountMittente, destinatariPrincipali, destinatariCC,
					destinatariCCn, allegati, oggetto, corpo, download, avvertimenti, uriEmail, dateRif);
		} else {
			_form.setFields(flgIo, idEmail, statoConsolidamento, spacerRispostaInoltrataItem, infoButtons, spacerNotificheInteropItem, notificheInteropButtons,
					notificheCollegate, tsInvio, accountMittente, destinatariPrincipali, destinatariCC, destinatariCCn, allegati, oggetto, corpo, download,
					avvertimenti, uriEmail, dateRif);
		}

		// Creo il VLAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.addMember(_form);

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		addMember(lVLayout);
		addMember(lVLayoutSpacer);

		List<DetailToolStripButton> detailButtons = getDetailButtons(classifica, listRecord, abilitazioni);

		if (detailButtons != null && detailButtons.size() > 0) {

			// Creo la TOOLSTRIP e aggiungo i bottoni
			detailToolStrip = new ToolStrip();
			detailToolStrip.setName("bottoni");
			detailToolStrip.setWidth100();
			detailToolStrip.setHeight(30);
			detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
			// detailToolStrip.addButton(backButton);
			detailToolStrip.addFill(); // push all buttons to the right

			int n = 0;
			for (DetailToolStripButton button : getDetailButtons(classifica, listRecord, abilitazioni)) {
				detailToolStrip.addButton(button);
			}

			addMember(detailToolStrip);
		}

		setLayoutMargin(0);

		setCanEdit(true);
	}

	private List<DetailToolStripButton> getDetailButtons(final String classifica, final Record listRecord, final Record abilitazioni) {

		List<DetailToolStripButton> detailButtons = new ArrayList<DetailToolStripButton>();
		listRecord.setAttribute("casellaRicezione", new Record(vm.getValues()).getAttribute("casellaAccount"));

		if (listRecord != null) {

			if (protocollaDetailButton == null) {
				// MARINA MODIFICA
				// protocollaDetailButton = new DetailToolStripButton("Protocolla", "menu/protocollazione.png");
				protocollaDetailButton = new DetailToolStripButton(I18NUtil.getMessages().posta_elettronica_detail_protocollaDetailButton(),
						"menu/protocollazione.png");
				protocollaDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						verificaProtocollazione = true;
						manageProtocollaClick(listRecord);
					}
				});
			}

			if (rispondiDetailButton == null) {
				// MARINA MODIFICA
				// rispondiDetailButton = new DetailToolStripButton("Rispondi", "postaElettronica/risposta.png");
				rispondiDetailButton = new DetailToolStripButton(I18NUtil.getMessages().posta_elettronica_detail_rispondiDetailButton(),
						"postaElettronica/risposta.png");
				rispondiDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						Record record = new Record();
						final RecordList listaEmail = new RecordList();
						listaEmail.add(listRecord);
						record.setAttribute("listaRecord", listaEmail);
						final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
						lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								manageRispondiClick(listRecord);
							}
						});
					}
				});
			}

			if (rispondiATuttiDetailButton == null) {
				// MARINA MODIFICA
				// rispondiATuttiDetailButton = new DetailToolStripButton("Rispondi a tutti", "postaElettronica/risposta.png");
				rispondiATuttiDetailButton = new DetailToolStripButton(I18NUtil.getMessages().posta_elettronica_detail_rispondiATuttiDetailButton(),
						"postaElettronica/risposta.png");
				rispondiATuttiDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						Record record = new Record();
						final RecordList listaEmail = new RecordList();
						listaEmail.add(listRecord);
						record.setAttribute("listaRecord", listaEmail);
						final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
						lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								manageRispondiATuttiClick(listRecord);
							}
						});
					}
				});
			}

			if (inoltraDetailButton == null) {
				// MARINA MODIFICA
				// inoltraDetailButton = new DetailToolStripButton("Inoltra", "postaElettronica/inoltro.png");
				inoltraDetailButton = new DetailToolStripButton(I18NUtil.getMessages().posta_elettronica_detail_inoltraDetailButton(),
						"postaElettronica/inoltro.png");
				inoltraDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						Menu inoltraMenu = new Menu();
						if (abilitazioni.getAttribute("abilitaInoltraEmail") != null && abilitazioni.getAttribute("abilitaInoltraEmail").equals("true")) {
							// MARINA MODIFICA
							// MenuItem allegaMailOriginaleMenuItem = new MenuItem("Allega e-mail originale", "blank.png");
							MenuItem allegaMailOriginaleMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_detail_allegaMailOriginaleMenuItem(),
									"blank.png");
							allegaMailOriginaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									final Record record = new Record();
									final RecordList listaEmail = new RecordList();
									listaEmail.add(listRecord);
									record.setAttribute("listaRecord", listaEmail);
									final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
									lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

										@Override
										public void execute(Record object) {
											manageInoltraClick(listRecord, true);
										}
									});
								}
							});
							inoltraMenu.addItem(allegaMailOriginaleMenuItem);
						}
						if (abilitazioni.getAttribute("abilitaInoltraContenuti") != null && abilitazioni.getAttribute("abilitaInoltraContenuti").equals("true")) {
							// MARINA MODIFICA
							// MenuItem allegaFileMailMenuItem = new MenuItem("Allega contenuti - testo e file - dell'email", "blank.png");
							MenuItem allegaFileMailMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_detail_allegaFileMenuItem(), "blank.png");
							allegaFileMailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									Record record = new Record();
									final RecordList listaEmail = new RecordList();
									listaEmail.add(listRecord);
									record.setAttribute("listaRecord", listaEmail);
									final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
									lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

										@Override
										public void execute(Record object) {
											manageInoltraClick(listRecord, false);
										}
									});
								}
							});
							inoltraMenu.addItem(allegaFileMailMenuItem);
						}
						inoltraMenu.showContextMenu();
					}
				});
			}

			if (inviaNotificaDetailButton == null) {
				// MARINA MODIFICA
				// inviaNotificaDetailButton = new DetailToolStripButton("Invia notifica" , "postaElettronica/notifiche.png");
				inviaNotificaDetailButton = new DetailToolStripButton(I18NUtil.getMessages().posta_elettronica_detail_invia_notifica_detail_button(),
						"postaElettronica/notifiche.png");
				inviaNotificaDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						Menu invioNotificaMenu = new Menu();
						if (abilitazioni != null) {
							if (abilitazioni.getAttribute("abilitaNotifInteropConferma").equals("true")) {
								// MARINA MODIFICA
								// MenuItem confermaMenuItem = new MenuItem("di conferma", "postaElettronica/notifInteropConf.png");
								MenuItem confermaMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_detail_confermaMenuItem(),
										"postaElettronica/notifInteropConf.png");
								confermaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

									@Override
									public void onClick(MenuItemClickEvent event) {
										manageInvioNotificaInteropClick(listRecord, "conferma");
									}
								});
								invioNotificaMenu.addItem(confermaMenuItem);
							}
							if (abilitazioni.getAttribute("abilitaNotifInteropEccezione").equals("true")) {
								// MARINA MODIFICA
								// MenuItem eccezioneMenuItem = new MenuItem("di eccezione", "postaElettronica/notifInteropEcc.png");
								MenuItem eccezioneMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_detail_eccezioneMenuItem(),
										"postaElettronica/notifInteropEcc.png");
								eccezioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

									@Override
									public void onClick(MenuItemClickEvent event) {
										manageInvioNotificaInteropClick(listRecord, "eccezione");
									}
								});
								invioNotificaMenu.addItem(eccezioneMenuItem);
							}
							if (abilitazioni.getAttribute("abilitaNotifInteropAggiornamento").equals("true")) {
								// MARINA MODIFICA
								// MenuItem aggiornamentoMenuItem = new MenuItem("di aggiornamento", "postaElettronica/notifInteropAgg.png");
								MenuItem aggiornamentoMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_detail_aggiornamento_MenuItem(),
										"postaElettronica/notifInteropAgg.png");
								aggiornamentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

									@Override
									public void onClick(MenuItemClickEvent event) {
										manageInvioNotificaInteropClick(listRecord, "aggiornamento");
									}
								});
								invioNotificaMenu.addItem(aggiornamentoMenuItem);
							}
							if (abilitazioni.getAttribute("abilitaNotifInteropAnnullamento").equals("true")) {
								// MARINA MODIFICA
								// MenuItem annullamentoMenuItem = new MenuItem("di annullamento", "postaElettronica/notifInteropAnn.png");
								MenuItem annullamentoMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_detail_annullamentoMenuItem(),
										"postaElettronica/notifInteropAnn.png");
								annullamentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

									@Override
									public void onClick(MenuItemClickEvent event) {
										manageInvioNotificaInteropClick(listRecord, "annullamento");
									}
								});
								invioNotificaMenu.addItem(annullamentoMenuItem);
							}
						}
						invioNotificaMenu.showContextMenu();
					}
				});
			}

			if (inviaNotificaConfermaDetailButton == null) {
				// MARINA MODIFICA
				// inviaNotificaConfermaDetailButton = new DetailToolStripButton("Invia notifica di conferma" , "postaElettronica/notifInteropConf.png");
				inviaNotificaConfermaDetailButton = new DetailToolStripButton(I18NUtil.getMessages()
						.posta_elettronica_detail_inviaNotificaConfermaDetailButton(), "postaElettronica/notifInteropConf.png");
				inviaNotificaConfermaDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						
						manageInvioNotificaInteropClick(listRecord, "conferma");
					}
				});
			}

			if (inviaNotificaEccezioneDetailButton == null) {
				// MARINA MODIFICA
				// inviaNotificaEccezioneDetailButton = new DetailToolStripButton("Invia notifica di eccezione" , "postaElettronica/notifInteropEcc.png");
				inviaNotificaEccezioneDetailButton = new DetailToolStripButton(I18NUtil.getMessages()
						.posta_elettronica_detail_inviaNotificaEccezioneDetailButton(), "postaElettronica/notifInteropEcc.png");
				inviaNotificaEccezioneDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						manageInvioNotificaInteropClick(listRecord, "eccezione");
					}
				});
			}

			if (inviaNotificaAggiornamentoDetailButton == null) {
				// MARINA MODIFICA
				// inviaNotificaAggiornamentoDetailButton = new DetailToolStripButton("Invia notifica di aggiornamento" ,
				// "postaElettronica/notifInteropAgg.png");
				inviaNotificaAggiornamentoDetailButton = new DetailToolStripButton(I18NUtil.getMessages()
						.posta_elettronica_detail_inviaNotificaAggiornamentoDetailButton(), "postaElettronica/notifInteropAgg.png");
				inviaNotificaAggiornamentoDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						manageInvioNotificaInteropClick(listRecord, "aggiornamento");
					}
				});
			}

			if (inviaNotificaAnnullamentoDetailButton == null) {
				// MARINA MODIFICA
				// inviaNotificaAnnullamentoDetailButton = new DetailToolStripButton("Invia notifica di annullamento" , "postaElettronica/notifInteropAnn.png");
				inviaNotificaAnnullamentoDetailButton = new DetailToolStripButton(I18NUtil.getMessages()
						.posta_elettronica_detail_inviaNotificaAnnullamentoDetailButton(), "postaElettronica/notifInteropAnn.png");
				inviaNotificaAnnullamentoDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						manageInvioNotificaInteropClick(listRecord, "annullamento");
					}
				});
			}

			if (ricevutePostaInUscitaDetailButton == null) {
				// MARINA MODIFICA
				// ricevutePostaInUscitaDetailButton = new DetailToolStripButton("Ricevute PEC/notifiche collegate", "mail/mail-reply2.png");
				ricevutePostaInUscitaDetailButton = new DetailToolStripButton(I18NUtil.getMessages()
						.posta_elettronica_detail_ricevutePostaInUscitaDetailButton(), "mail/mail-reply2.png");
				ricevutePostaInUscitaDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						
						manageRicevutePostaInUscitaClick(listRecord);
					}
				});
			}

			if (archiviazioneDetailButton == null) {
				archiviazioneDetailButton = new DetailToolStripButton(AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL"), "archivio/archiviazione.png");
				archiviazioneDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						manageArchiviaClick(listRecord);
					}
				});
			}

			if (assegnaDetailButton == null) {
				// MARINA MODIFICA
				// assegnaDetailButton = new DetailToolStripButton("Assegna", "archivio/assegna.png");
				assegnaDetailButton = new DetailToolStripButton(I18NUtil.getMessages().posta_elettronica_detail_assegnaDetailButton(), "archivio/assegna.png");
				assegnaDetailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						
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
//									final RecordList listaDestinatariPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUOPreferiteMail");
									final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_UO_COMPETENTE.getValue()));							
									manageAssegnaClick(listRecord, listaDestinatariPreferiti);
								}
							}
						}, new DSRequest());
					}
				});
			}

			if (abilitazioni.getAttribute("abilitaProtocolla") != null && abilitazioni.getAttribute("abilitaProtocolla").equals("true")) {
				detailButtons.add(protocollaDetailButton);
			}

			if (abilitazioni.getAttribute("abilitaRispondi") != null && abilitazioni.getAttribute("abilitaRispondi").equals("true")) {
				detailButtons.add(rispondiDetailButton);
			}

			if (abilitazioni.getAttribute("abilitaRispondiATutti") != null && abilitazioni.getAttribute("abilitaRispondiATutti").equals("true")) {
				detailButtons.add(rispondiATuttiDetailButton);
			}

			if ((abilitazioni.getAttribute("abilitaInoltraEmail") != null && abilitazioni.getAttribute("abilitaInoltraEmail").equals("true"))
					|| (abilitazioni.getAttribute("abilitaInoltraContenuti") != null && abilitazioni.getAttribute("abilitaInoltraContenuti").equals("true"))) {
				detailButtons.add(inoltraDetailButton);
			}

			List<String> tipiNotificheInterop = new ArrayList<String>();
			if (abilitazioni.getAttribute("abilitaNotifInteropConferma") != null && abilitazioni.getAttribute("abilitaNotifInteropConferma").equals("true")) {
				tipiNotificheInterop.add("conferma");
			}
			if (abilitazioni.getAttribute("abilitaNotifInteropEccezione") != null && abilitazioni.getAttribute("abilitaNotifInteropEccezione").equals("true")) {
				tipiNotificheInterop.add("eccezione");
			}
			if (abilitazioni.getAttribute("abilitaNotifInteropAggiornamento") != null
					&& abilitazioni.getAttribute("abilitaNotifInteropAggiornamento").equals("true")) {
				tipiNotificheInterop.add("aggiornamento");
			}
			if (abilitazioni.getAttribute("abilitaNotifInteropAnnullamento") != null
					&& abilitazioni.getAttribute("abilitaNotifInteropAnnullamento").equals("true")) {
				tipiNotificheInterop.add("annullamento");
			}

			if (tipiNotificheInterop.size() > 0) {
				if (tipiNotificheInterop.size() == 1) {
					if (tipiNotificheInterop.get(0).equals("conferma")) {
						detailButtons.add(inviaNotificaConfermaDetailButton);
					}
					if (tipiNotificheInterop.get(0).equals("eccezione")) {
						detailButtons.add(inviaNotificaEccezioneDetailButton);
					}
					if (tipiNotificheInterop.get(0).equals("aggiornamento")) {
						detailButtons.add(inviaNotificaAggiornamentoDetailButton);
					}
					if (tipiNotificheInterop.get(0).equals("annullamento")) {
						detailButtons.add(inviaNotificaAnnullamentoDetailButton);
					}
				} else {
					detailButtons.add(inviaNotificaDetailButton);
				}
			}

			if (classifica != null && classifica.startsWith("standard.invio")) {
				detailButtons.add(ricevutePostaInUscitaDetailButton);
			}

			if (abilitazioni.getAttribute("abilitaArchivia") != null && abilitazioni.getAttribute("abilitaArchivia").equals("true")) {
				detailButtons.add(archiviazioneDetailButton);
			}

			if (abilitazioni.getAttribute("abilitaAssegna") != null && abilitazioni.getAttribute("abilitaAssegna").equals("true")) {
				detailButtons.add(assegnaDetailButton);
			}

		}

		return detailButtons;
	}

	protected void buildEccezione() {
		// MARINA MODIFICA
		// notificaEccezione = new ImgButtonItem("notificaEccezione", "postaElettronica/notifInteropEcc.png", "Pervenute eccezioni");
		notificaEccezione = new ImgButtonItem("notificaEccezione", "postaElettronica/notifInteropEcc.png", I18NUtil.getMessages()
				.posta_elettornica_detail_notificaEccezione());
		notificaEccezione.setAlwaysEnabled(true);
		notificaEccezione.setColSpan(1);
		notificaEccezione.setIconWidth(16);
		notificaEccezione.setIconHeight(16);
		notificaEccezione.setIconVAlign(VerticalAlignment.BOTTOM);
		notificaEccezione.setAlign(Alignment.LEFT);
		notificaEccezione.setWidth(16);
		notificaEccezione.setRedrawOnChange(true);
		notificaEccezione.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("notificaEccezionePresente") != null && vm.getValueAsString("notificaEccezionePresente").equals("true");
			}
		});
	}

	protected void buildConferme() {
		// MARINA MODIFICA
		// notificaConferma = new ImgButtonItem("notificaConferma", "postaElettronica/notifInteropConf.png", "Pervenute conferme");
		notificaConferma = new ImgButtonItem("notificaConferma", "postaElettronica/notifInteropConf.png", I18NUtil.getMessages()
				.posta_elettronica_detail_notificaConferma());
		notificaConferma.setAlwaysEnabled(true);
		notificaConferma.setColSpan(1);
		notificaConferma.setIconWidth(16);
		notificaConferma.setIconHeight(16);
		notificaConferma.setIconVAlign(VerticalAlignment.BOTTOM);
		notificaConferma.setAlign(Alignment.LEFT);
		notificaConferma.setWidth(16);
		notificaConferma.setRedrawOnChange(true);
		notificaConferma.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("notificaConfermaPresente") != null && vm.getValueAsString("notificaConfermaPresente").equals("true");
			}
		});
	}

	protected void buildAggiornamento() {
		// MARINA MODIFICA
		// notificaAggiornamento = new ImgButtonItem("notificaAggiornamento", "postaElettronica/notifInteropAgg.png", "Pervenute notifiche di aggiornamento");
		notificaAggiornamento = new ImgButtonItem("notificaAggiornamento", "postaElettronica/notifInteropAgg.png", I18NUtil.getMessages()
				.posta_elettronica_detail_notificaAggiornamento());
		notificaAggiornamento.setAlwaysEnabled(true);
		notificaAggiornamento.setColSpan(1);
		notificaAggiornamento.setIconWidth(16);
		notificaAggiornamento.setIconHeight(16);
		notificaAggiornamento.setIconVAlign(VerticalAlignment.BOTTOM);
		notificaAggiornamento.setAlign(Alignment.LEFT);
		notificaAggiornamento.setWidth(16);
		notificaAggiornamento.setRedrawOnChange(true);
		notificaAggiornamento.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("notificaAggiornamentoPresente") != null && vm.getValueAsString("notificaAggiornamentoPresente").equals("true");
			}
		});
	}

	protected void buildAnnullamento() {
		// MARINA MODIFICA
		// notificaAnnullamento = new ImgButtonItem("notificaAnnullamento", "postaElettronica/notifInteropAnn.png",
		// "Pervenute notifiche di annullamento registrazione");
		notificaAnnullamento = new ImgButtonItem("notificaAnnullamento", "postaElettronica/notifInteropAnn.png", I18NUtil.getMessages()
				.posta_elettronica_detail_notificaAnnullamento());
		notificaAnnullamento.setAlwaysEnabled(true);
		notificaAnnullamento.setColSpan(1);
		notificaAnnullamento.setIconWidth(16);
		notificaAnnullamento.setIconHeight(16);
		notificaAnnullamento.setIconVAlign(VerticalAlignment.BOTTOM);
		notificaAnnullamento.setAlign(Alignment.LEFT);
		notificaAnnullamento.setWidth(16);
		notificaAnnullamento.setRedrawOnChange(true);
		notificaAnnullamento.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("notificaAnnullamentoPresente") != null && vm.getValueAsString("notificaAnnullamentoPresente").equals("true");
			}
		});
	}

	protected void buildInviataRisposta() {
		// MARINA MODIFICA
		// inviataRisposta = new ImgButtonItem("inviataRisposta", "postaElettronica/risposta.png", "Inviata Risposta");
		inviataRisposta = new ImgButtonItem("inviataRisposta", "postaElettronica/risposta.png", I18NUtil.getMessages().posta_elettronica_inviataRisposta());
		inviataRisposta.setAlwaysEnabled(true);
		inviataRisposta.setColSpan(1);
		inviataRisposta.setIconWidth(16);
		inviataRisposta.setIconHeight(16);
		inviataRisposta.setIconVAlign(VerticalAlignment.BOTTOM);
		inviataRisposta.setAlign(Alignment.LEFT);
		inviataRisposta.setWidth(16);
		inviataRisposta.setRedrawOnChange(true);
		inviataRisposta.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("inviataRispostaPresente") != null && vm.getValueAsString("inviataRispostaPresente").equals("true");
			}
		});
		inviataRisposta.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				final String idEmail = vm.getValueAsString("idEmail");
				GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("EmailCollegateDataSource", "idEmail", FieldType.TEXT);
				lGWTRestDataSource.addParam("idEmail", idEmail);
				lGWTRestDataSource.addParam("tipoRel", "risposta");
				lGWTRestDataSource.fetchData(null, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						RecordList data = response.getDataAsRecordList();
						EmailCollegateWindow emailRispostaCollegateWindow = null;
						if (data.getLength() == 1) {
							emailRispostaCollegateWindow = new EmailCollegateWindow("risposta", idEmail, "1", data.get(0));
							emailRispostaCollegateWindow.show();
						} else if (data.getLength() > 0) {
							emailRispostaCollegateWindow = new EmailCollegateWindow("risposta", idEmail, null, null);
							emailRispostaCollegateWindow.show();
						}
					}
				});
			}
		});
	}

	protected void buildInoltrata() {
		// MARINA MODIFICA
		// inoltrata = new ImgButtonItem("inoltrata", "postaElettronica/inoltro.png", "Inoltrata");
		inoltrata = new ImgButtonItem("inoltrata", "postaElettronica/inoltro.png", I18NUtil.getMessages().posta_elettronica_detail_inoltrata());
		inoltrata.setAlwaysEnabled(true);
		inoltrata.setColSpan(1);
		inoltrata.setIconWidth(16);
		inoltrata.setIconHeight(16);
		inoltrata.setIconVAlign(VerticalAlignment.BOTTOM);
		inoltrata.setAlign(Alignment.LEFT);
		inoltrata.setWidth(16);
		inoltrata.setRedrawOnChange(true);
		inoltrata.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("inoltrataPresente") != null && vm.getValueAsString("inoltrataPresente").equals("true");
			}
		});
		inoltrata.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				final String idEmail = vm.getValueAsString("idEmail");
				GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("EmailCollegateDataSource", "idEmail", FieldType.TEXT);
				lGWTRestDataSource.addParam("idEmail", idEmail);
				lGWTRestDataSource.addParam("tipoRel", "inoltro");
				lGWTRestDataSource.fetchData(null, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						RecordList data = response.getDataAsRecordList();
						EmailCollegateWindow emailInoltroCollegateWindow = null;
						if (data.getLength() == 1) {
							emailInoltroCollegateWindow = new EmailCollegateWindow("inoltro", idEmail, "1", data.get(0));
							emailInoltroCollegateWindow.show();
						} else if (data.getLength() > 0) {
							emailInoltroCollegateWindow = new EmailCollegateWindow("inoltro", idEmail, null, null);
							emailInoltroCollegateWindow.show();
						}
					}
				});
			}
		});
	}

	protected void buildStatoConsolidamento() {
		// MARINA MODIFICA
		// statoConsolidamento = new StaticTextItem("statoConsolidamento", "Stato consolidamento");
		statoConsolidamento = new StaticTextItem("statoConsolidamento", I18NUtil.getMessages().posta_elettronica_detail_stato_consolidamento());
		statoConsolidamento.setShowValueIconOnly(true);
		statoConsolidamento.setShowTitle(true);
		Map<String, String> lMap = new HashMap<String, String>();
		lMap.put("accettata", "postaElettronica/statoConsolidamento/accettata.png");
		lMap.put("avvertimenti_in_consegna", "postaElettronica/statoConsolidamento/avvertimenti_in_consegna.png");
		lMap.put("consegnata", "postaElettronica/statoConsolidamento/consegnata.png");
		lMap.put("consegnata_parzialmente", "postaElettronica/statoConsolidamento/consegnata_parzialmente.png");
		lMap.put("errori_in_consegna", "postaElettronica/statoConsolidamento/errori_in_consegna.png");
		lMap.put("non_accettata", "postaElettronica/statoConsolidamento/non_accettata.png");
		lMap.put("in_invio", "postaElettronica/statoConsolidamento/in_invio.png");
		statoConsolidamento.setValueIcons(lMap);
		statoConsolidamento.setColSpan(1);
		statoConsolidamento.setWidth(16);
		statoConsolidamento.setIconWidth(16);
		statoConsolidamento.setIconHeight(16);
		statoConsolidamento.setIconVAlign(VerticalAlignment.BOTTOM);
		statoConsolidamento.setCellStyle(it.eng.utility.Styles.staticTextItem);
		statoConsolidamento.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				// Solo se in uscita
				return vm.getValueAsString("flgIo") != null && "O".equals(vm.getValueAsString("flgIo")) && vm.getValueAsString("statoConsolidamento") != null
						&& !"".equals(vm.getValueAsString("statoConsolidamento"));
			}
		});
		statoConsolidamento.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final String idEmailRif = vm.getValueAsString("idEmail");
				GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("RicevutePostaInUscitaDataSource", "idEmail", FieldType.TEXT);
				lGWTRestDataSource.addParam("idEmailRif", idEmailRif);
				lGWTRestDataSource.fetchData(null, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						RecordList data = response.getDataAsRecordList();
						if (data.getLength() == 0) {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().ricevutePostaInUscitaWindow_empty_message(), "", MessageType.INFO));
						} else if (data.getLength() == 1) {
							RicevutePostaInUscitaWindow ricevutePostaInUscitaWindow = new RicevutePostaInUscitaWindow(data.get(0));
						} else if (data.getLength() > 0) {
							RicevutePostaInUscitaWindow ricevutePostaInUscitaWindow = new RicevutePostaInUscitaWindow(idEmailRif);
						}
					}
				});
			}
		});
		statoConsolidamento.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				String statoConsolidamento = (String) vm.getValueAsString("statoConsolidamento");
				if (statoConsolidamento != null && !"".equals(statoConsolidamento)) {
					statoConsolidamento = statoConsolidamento.toString().replaceAll("_", " ");
					return statoConsolidamento;
				}
				return null;
			}
		});
	}

	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		destinatariPrincipali.setIdEmail(record.getAttributeAsString("idEmail"));
		destinatariCC.setIdEmail(record.getAttributeAsString("idEmail"));
		destinatariCCn.setIdEmail(record.getAttributeAsString("idEmail"));
		statoConsolidamento.setPrompt(record.getAttributeAsString("statoConsolidamento"));
		tsInvio.setCanEdit(false);
		accountMittente.setCanEdit(false);
		destinatariPrincipali.setCanEdit(false);
		destinatariCC.setCanEdit(false);
		destinatariCCn.setCanEdit(false);
		allegati.setCanEdit(false);
		oggetto.setCanEdit(false);
		corpo.setCanEdit(false);
		avvertimenti.setCanEdit(false);
		allegati.setDetailRecord(record);
		markForRedraw();
	}

	private void realEditRecord(Record record) {
		editRecord(record);

	}

	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		super.setCanEdit(false);
		statoConsolidamento.setCanEdit(true);
	}

	protected void manageRicevutePostaInUscitaClick(Record pRecord) {
		final String idEmailRif = pRecord.getAttribute("idEmail");
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("RicevutePostaInUscitaDataSource", "idEmail", FieldType.TEXT);
		lGWTRestDataSource.addParam("idEmailRif", idEmailRif);
		lGWTRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				RecordList data = response.getDataAsRecordList();
				if (data.getLength() == 0) {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().ricevutePostaInUscitaWindow_empty_message(), "", MessageType.INFO));
				} else if (data.getLength() == 1) {
					RicevutePostaInUscitaWindow ricevutePostaInUscitaWindow = new RicevutePostaInUscitaWindow(data.get(0));
				} else if (data.getLength() > 0) {
					RicevutePostaInUscitaWindow ricevutePostaInUscitaWindow = new RicevutePostaInUscitaWindow(idEmailRif);
				}
			}
		});
	}

	protected void manageProtocollaClick(Record pRecord) {
		GWTRestService<Record, Record> lGwtRestServiceProtocolla = new GWTRestService<Record, Record>("AurigaProtocollaPostaElettronicaDataSource");
		// lGwtRestServiceProtocolla.addParam("classifica", classifica);
		lGwtRestServiceProtocolla.call(pRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				manageProtocollaPostaElettronicaCallback(object);
			}
		});
	}

	protected void manageProtocollaPostaElettronicaCallback(Record lRecord) {
		String flgTipoProv = lRecord.getAttribute("flgTipoProv");
		EditaProtocolloWindowFromMail lEditaProtocolloWindowFromMail = new EditaProtocolloWindowFromMail("protocollazione_mail", lRecord, flgTipoProv) {

			@Override
			public void manageAfterCloseWindow() {
				
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

	protected void manageRispondiClick(Record pRecord) {
		RispostaMailWindow lRispostaEmailWindow = new RispostaMailWindow("risposta", true, null, pRecord, layout, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				reloadDetail();
			}
		});
	}

	protected void manageRispondiATuttiClick(final Record pRecord) {
		RispostaMailWindow lRispostaEmailWindow = new RispostaMailWindow("risposta", false, pRecord.getAttribute("casellaRicezione"), pRecord, layout,
				new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {

						reloadDetail();
					}
				});
	}

	protected void manageInoltraClick(Record pRecord, boolean allegaMailOrig) {
		InoltroMailWindow lInoltroEmailWindow = new InoltroMailWindow(allegaMailOrig ? "inoltroAllegaMailOrig" : "inoltro", pRecord, layout, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				reloadDetail();
			}
		});
	}

	protected void manageArchiviaClick(final Record pRecord) {
		final RecordList listaEmail = new RecordList();
		listaEmail.add(pRecord);
		Record record = new Record();
		record.setAttribute("listaRecord", listaEmail);
		record.setAttribute("classifica", classifica);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchiviazioneEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				operationCallback(response, pRecord, "idEmail", "Archiviazione effettuata con successo", "Si è verificato un errore durante l'archiviazione!",
						null);
			}
		});
	}

	protected void manageAssegnaClick(final Record pRecord, final RecordList listaDestinatariPreferiti) {
		final AssegnazioneEmailPopup assegnazioneEmailPopup = new AssegnazioneEmailPopup(pRecord, listaDestinatariPreferiti) {

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

	protected void manageInvioNotificaInteropClick(Record pRecord, String tipoNotifica) {
		pRecord.setAttribute("uriEmail", vm.getValue("uriEmail"));
		final GWTRestService<Record, Record> lGwtRestServiceInvioNotifica = new GWTRestService<Record, Record>("AurigaInvioNotificaInteropDatasource");
		lGwtRestServiceInvioNotifica.extraparam.put("tipoNotifica", tipoNotifica);
		lGwtRestServiceInvioNotifica.call(pRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				managerInvioNotificaInteropResponse(lGwtRestServiceInvioNotifica, object);
			}
		});
	}

	protected void managerInvioNotificaInteropResponse(GWTRestService<Record, Record> lGwtRestServiceInvioNotifica, Record record) {
		InvioNotificaInteropWindow lInvioNotificaInteropWindow = new InvioNotificaInteropWindow(lGwtRestServiceInvioNotifica, record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				reloadDetail();
			}
		});
		lInvioNotificaInteropWindow.show();
	}

	public void reloadDetail() {

		Record lRecord = new Record();
		final String idEmail = vm.getValueAsString("idEmail");
		lRecord.setAttribute("idEmail", idEmail);

		if (!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {

			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaLoadDettaglioEmailDataSource", "idEmail", FieldType.TEXT);
			lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						editRecord(record);
						getValuesManager().clearErrors(true);
					}
				}
			}, new DSRequest());

		} else {
			layout.changeDetail((GWTRestDataSource) getDataSource(), new DettaglioPostaElettronica("posta_elettronica"));
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
			lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						editRecord(record, recordNum);
						getValuesManager().clearErrors(true);
					}
				}
			}, new DSRequest());
		}

	}
}
