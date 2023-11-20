/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioMail.InoltroMailWindow;
import it.eng.auriga.ui.module.layout.client.invioMail.InvioMailPopup;
import it.eng.auriga.ui.module.layout.client.invioMail.InvioNotificaInteropWindow;
import it.eng.auriga.ui.module.layout.client.invioMail.NuovoInvioMailWindow;
import it.eng.auriga.ui.module.layout.client.invioMail.RispostaMailWindow;
import it.eng.auriga.ui.module.layout.client.istanze.EditaIstanzaWindowFromMail;
import it.eng.auriga.ui.module.layout.client.postainuscita.RicevutePostaInUscitaWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.EditaProtocolloWindowFromMail;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewImageWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.auriga.ui.module.layout.shared.util.AzioniRapide;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class PostaElettronicaList extends CustomList {

	private ListGridField id;
	private ListGridField idEmailField;
	private ListGridField flgIoField;
	private ListGridField messageIdField;
	private ListGridField idCasellaField;
	private ListGridField casellaRicezioneField;
	private ListGridField azioneDaFareField;
	private ListGridField dettaglioAzioneDaFareField;
	private ListGridField codAzioneDaFareField;
	private ListGridField tsSalvataggioEmailField;
	private ListGridField tsRicezioneField;
	private ListGridField tsInvioClientField;
	private ListGridField tsInvioField;
	private ListGridField dimensioneField;
	private ListGridField uriEmailField;
	private ListGridField iconaFlgSpamField;
	private ListGridField statoConsolidamentoField;
	private ListGridField allegatiField;
	private ListGridField iconaFlgNoAssocAutoField;
	private ListGridField accountMittenteField;
	private ListGridField oggettoField;
	private ListGridField corpoField;
	private ListGridField destinatariField;
	private ListGridField destinatariPrimariField;
	private ListGridField destinatariCCField;
	private ListGridField destinatariCCNField;
	private ListGridField avvertimentiField;
	private ListGridField iconaFlgRichConfermaField;
	private ListGridField tsAssegnField;
	private ListGridField assegnatarioField;
	private ListGridField messaggioAssegnazioneField;
	private ListGridField iconaFlgInviataRispostaField;
	private ListGridField iconaFlgInoltrataField;
	private ListGridField iconaFlgStatoProtField;
	private ListGridField iconaFlgNotifInteropEccField;
	private ListGridField iconaFlgNotifInteropConfField;
	private ListGridField iconaFlgNotifInteropAggField;
	private ListGridField iconaFlgNotifInteropAnnField;
	private ListGridField livPrioritaField;
	private ListGridField estremiProtCollegatiField;
	private ListGridField estremiDocInviatoField;
	private ListGridField emailCollegataField;
	private ListGridField protocolliDestinatariField;

	private ListGridField tipoEmailField;
	private ListGridField sottotipoEmailField;
	private ListGridField flgEmailCertificataField;
	private ListGridField flgInteroperabileField;
	private ListGridField protocolloMittenteField;
	private ListGridField oggettoProtocolloMittenteField;
	private ListGridField statoLavorazioneField;
	private ListGridField contrassegnoField;

	private ListGridField statoInvioField;
	private ListGridField statoAccettazioneField;
	private ListGridField statoConsegnaField;
	private ListGridField nroGiorniApertaField;
	private ListGridField inCaricoAField;
	private ListGridField inCaricoDalField;

	private ListGridField annotazioniField;
	private ListGridField tagAppostoField;

	private ListGridField tsChiusuraField;
	private ListGridField chiusuraEffettuataDaField;

	private String idNode;
	
	public PostaElettronicaList(String nomeEntita) {
		this(nomeEntita, null);
	}

	@Override
	protected Object formatDateForSorting(ListGridRecord record, String fieldName) {
		String value = record != null ? record.getAttributeAsString(fieldName) : null;
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
		return value != null && !"".equals(value) ? dateFormat.parse(value) : null;
	}

	@Override
	public Boolean sort() {
		return super.sort();
	}

	public PostaElettronicaList(String nomeEntita, String pIdNode) {

		super(nomeEntita);

		idNode = pIdNode;
//		setAriaRole("table");
		setCanFocus(AurigaLayout.getIsAttivaAccessibilita());
		

		String classifica = idNode != null ? idNode.substring(2) : null;

		setAutoFetchData(false);
		setDataFetchMode(FetchMode.BASIC);
		setSelectionType(SelectionStyle.NONE);

		idEmailField = new ListGridField("idEmail");
		idEmailField.setHidden(true);
		idEmailField.setCanHide(false);

		flgIoField = new ListGridField("flgIo");
		flgIoField.setHidden(true);
		flgIoField.setCanHide(false);

		buildIdField();

		messageIdField = new ListGridField("messageId", I18NUtil.getMessages().postaElettronica_list_messageIdField_title());

		idCasellaField = new ListGridField("idCasella");
		idCasellaField.setHidden(true);
		idCasellaField.setCanHide(false);

		casellaRicezioneField = new ListGridField("casellaRicezione", I18NUtil.getMessages().postaElettronica_list_casellaRicezioneField_title());

		tsSalvataggioEmailField = new ListGridField("tsSalvataggioEmail", I18NUtil.getMessages().postaElettronica_list_tsSalvataggioEmailField_title());
		tsSalvataggioEmailField.setType(ListGridFieldType.DATE);
		tsSalvataggioEmailField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsSalvataggioEmailField.setWrap(false);

		tsRicezioneField = new ListGridField("tsRicezione", I18NUtil.getMessages().postaElettronica_list_tsRicezioneField_title());
		tsRicezioneField.setType(ListGridFieldType.DATE);
		tsRicezioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsRicezioneField.setWrap(false);

		tsInvioClientField = new ListGridField("tsInvioClient", I18NUtil.getMessages().postaElettronica_list_tsInvioClientField_title());
		tsInvioClientField.setType(ListGridFieldType.DATE);
		tsInvioClientField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsInvioClientField.setWrap(false);
		tsInvioField = new ListGridField("tsInvio", I18NUtil.getMessages().postaElettronica_list_tsInvioField_title());
		tsInvioField.setType(ListGridFieldType.DATE);
		tsInvioField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsInvioField.setWrap(false);

		dimensioneField = new ListGridField("dimensione", I18NUtil.getMessages().postaElettronica_list_dimensioneField_title());
		dimensioneField.setType(ListGridFieldType.INTEGER);
		dimensioneField.setWrap(false);

		uriEmailField = new ListGridField("uriEmail");
		uriEmailField.setHidden(true);
		uriEmailField.setCanHide(false);

		iconaFlgSpamField = new ListGridField("flgSpam", I18NUtil.getMessages().postaElettronica_list_iconaFlgSpamField_title());
		iconaFlgSpamField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaFlgSpamField.setType(ListGridFieldType.ICON);
		iconaFlgSpamField.setAlign(Alignment.CENTER);
		iconaFlgSpamField.setWrap(false);
		iconaFlgSpamField.setWidth(30);
		iconaFlgSpamField.setIconWidth(16);
		iconaFlgSpamField.setIconHeight(16);
		iconaFlgSpamField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgSpam = (String) record.getAttribute("flgSpam");
				if (flgSpam != null && "1".equals(flgSpam)) {
					String labelSpam = "";
					String flgStatoSpam = (String) record.getAttribute("flgStatoSpam");
					if (flgStatoSpam != null) {
						if ("B".equals(flgStatoSpam)) {
							labelSpam =  "Spam (bloccata)";
						} else if ("RS".equals(flgStatoSpam)) {
							labelSpam =  "Spam (richiesta sblocco)";
						} else if ("S".equals(flgStatoSpam)) {
							labelSpam =  "Spam (sbloccata)";
						} else if ("R".equals(flgStatoSpam)) {
							labelSpam =  "Spam (rigettata a seguito dello sblocco)";
						}
					} else {
						labelSpam =  "Spam";
					}
					return buildIconHtml("postaElettronica/spam.png",labelSpam);
				}
				return null;
			}
		});
		iconaFlgSpamField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgSpam = (String) record.getAttribute("flgSpam");
				if (flgSpam != null && "1".equals(flgSpam)) {
					String flgStatoSpam = (String) record.getAttribute("flgStatoSpam");
					if (flgStatoSpam != null) {
						if ("B".equals(flgStatoSpam)) {
							return "Spam (bloccata)";
						} else if ("RS".equals(flgStatoSpam)) {
							return "Spam (richiesta sblocco)";
						} else if ("S".equals(flgStatoSpam)) {
							return "Spam (sbloccata)";
						} else if ("R".equals(flgStatoSpam)) {
							return "Spam (rigettata a seguito dello sblocco)";
						}
					}
					return "Spam";
				}
				return null;
			}
		});

		statoConsolidamentoField = new ListGridField("statoConsolidamento", I18NUtil.getMessages().postaElettronica_list_statoConsolidamentoField_title());
		statoConsolidamentoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoConsolidamentoField.setType(ListGridFieldType.ICON);
		statoConsolidamentoField.setAlign(Alignment.CENTER);
		statoConsolidamentoField.setWrap(false);
		statoConsolidamentoField.setWidth(30);
		statoConsolidamentoField.setIconWidth(16);
		statoConsolidamentoField.setIconHeight(16);
		statoConsolidamentoField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				String statoConsolidamento = (String) value;
				if (statoConsolidamento != null && !"".equals(statoConsolidamento)) {
					return buildImgButtonHtml("postaElettronica/statoConsolidamento/" + statoConsolidamento.replaceAll(" ", "_") + ".png");
				}
				return null;
			}
		});
		statoConsolidamentoField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return (String) record.getAttribute("statoConsolidamento");
			}
		});
		statoConsolidamentoField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				String statoConsolidamento = (String) event.getRecord().getAttribute("statoConsolidamento");
				if (statoConsolidamento != null && !"".equals(statoConsolidamento)) {
					manageRicevutePostaInUscitaClick(event.getRecord());
				}
			}
		});

		allegatiField = new ListGridField("allegatiEmail", I18NUtil.getMessages().postaElettronica_list_allegatiField_title());
		allegatiField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		allegatiField.setWrap(false);
		allegatiField.setWidth(30);
		allegatiField.setIconWidth(16);
		allegatiField.setIconHeight(16);
		allegatiField.setAttribute("custom", true);
		allegatiField.setShowHover(true);
		allegatiField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String allegatiEmail = (String) record.getAttribute("allegatiEmail");
				String altIcona = (String) record.getAttribute("altIcona");
				if (allegatiEmail != null && !"".equals(allegatiEmail)) {
					if (allegatiEmail.equals("A")) {
						return buildImgButtonHtml("allegati.png",altIcona);
					} else if (allegatiEmail.equals("AF")) {
						return buildImgButtonHtml("allegatiFirmati.png",altIcona);
					}
				}
				return null;
			}
		});
		allegatiField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String altIcona = (String) record.getAttribute("altIcona");
				String allegati = (String) record.getAttribute("allegatiEmail");
				if (allegati != null && !"".equals(allegati)) {
					return altIcona;
				}
				return null;
			}
		});
		allegatiField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				Record recordOriginale = event.getRecord();
				Record record = new Record();
				record.setAttribute("idEmail", recordOriginale.getAttribute("idEmail"));
				record.setAttribute("id", recordOriginale.getAttribute("id"));
				String allegatiEmail = (String) recordOriginale.getAttribute("allegatiEmail");
				if (allegatiEmail != null && !"".equals(allegatiEmail)) {
					retrieveAllegati(record, new RetrieveInfoFileCallback() {

						@Override
						public void manageInfoFile(final Record detailRecord, RecordList listaAllegati) {
							Menu allegatiMenu = new Menu();
							if (listaAllegati != null && !listaAllegati.isEmpty()) {
								for (int n = 0; n < listaAllegati.getLength(); n++) {

									final int nroAllegato = n;
									final Record allegatoRecord = listaAllegati.get(n);

									String numeroFileAllegato = allegatoRecord.getAttributeAsString("numeroProgrAllegato");
									String titoloFileAllegato = allegatoRecord.getAttributeAsString("displayFileName");
									if ((numeroFileAllegato != null) && (numeroFileAllegato.length() > 0)) {
										titoloFileAllegato = numeroFileAllegato + "- " + titoloFileAllegato;
									}

									MenuItem fileAllegatoMenuItem = new MenuItem(titoloFileAllegato);
									Menu operazioniFileAllegatoSubmenu = new Menu();

									InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));

									MenuItem visualizzaFileAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(),
											"file/preview.png");
									visualizzaFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

										@Override
										public void onClick(MenuItemClickEvent event) {

											visualizzaFileAllegato(detailRecord, nroAllegato);
										}
									});
									visualizzaFileAllegatoMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
									operazioniFileAllegatoSubmenu.addItem(visualizzaFileAllegatoMenuItem);

									if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")) {
										MenuItem visualizzaEditFileAllegatoMenuItem = new MenuItem(
												I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(), "file/previewEdit.png");
										visualizzaEditFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

											@Override
											public void onClick(MenuItemClickEvent event) {
												visualizzaEditFileAllegato(detailRecord, nroAllegato);
											}
										});
										visualizzaEditFileAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
										operazioniFileAllegatoSubmenu.addItem(visualizzaEditFileAllegatoMenuItem);
									}

									MenuItem scaricaFileAllegatoMenuItem = new MenuItem("Scarica", "file/download_manager.png");
									// Se è firmato P7M
									if (lInfoFileRecord != null && lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
										Menu scaricaFileAllegatoSubMenu = new Menu();
										MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
										firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

											@Override
											public void onClick(MenuItemClickEvent event) {
												scaricaFileAllegato(detailRecord, nroAllegato);
											}
										});
										MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
										sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

											@Override
											public void onClick(MenuItemClickEvent event) {
												scaricaFileAllegatoSbustato(detailRecord, nroAllegato);
											}
										});
										scaricaFileAllegatoSubMenu.setItems(firmato, sbustato);
										scaricaFileAllegatoMenuItem.setSubmenu(scaricaFileAllegatoSubMenu);
									} else {
										scaricaFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

											@Override
											public void onClick(MenuItemClickEvent event) {
												scaricaFileAllegato(detailRecord, nroAllegato);
											}
										});
									}
									operazioniFileAllegatoSubmenu.addItem(scaricaFileAllegatoMenuItem);

									fileAllegatoMenuItem.setSubmenu(operazioniFileAllegatoSubmenu);
									allegatiMenu.addItem(fileAllegatoMenuItem);
								}
							}
							allegatiMenu.showContextMenu();
						};
					});
				}
			}
		});

		iconaFlgNoAssocAutoField = new ListGridField("flgNoAssocAuto", I18NUtil.getMessages().postaElettronica_list_iconaFlgNoAssocAutoField_title());
		iconaFlgNoAssocAutoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaFlgNoAssocAutoField.setType(ListGridFieldType.ICON);
		iconaFlgNoAssocAutoField.setAlign(Alignment.CENTER);
		iconaFlgNoAssocAutoField.setWrap(false);
		iconaFlgNoAssocAutoField.setWidth(30);
		iconaFlgNoAssocAutoField.setIconWidth(16);
		iconaFlgNoAssocAutoField.setIconHeight(16);
		iconaFlgNoAssocAutoField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNoAssocAuto = (String) record.getAttribute("flgNoAssocAuto");
				if (flgNoAssocAuto != null && "1".equals(flgNoAssocAuto)) {
					return buildIconHtml("warning.png","Il sistema non è stato in grado di associarla in automatico all'email inviata cui si riferisce");
				}
				return null;
			}
		});
		iconaFlgNoAssocAutoField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNoAssocAuto = (String) record.getAttribute("flgNoAssocAuto");
				if (flgNoAssocAuto != null && "1".equals(flgNoAssocAuto)) {
					return "Il sistema non è stato in grado di associarla in automatico all'email inviata cui si riferisce";
				}
				return null;
			}
		});

		accountMittenteField = new ListGridField("accountMittente", I18NUtil.getMessages().postaElettronica_list_accountMittenteField_title());
		accountMittenteField.setWrap(false);

		oggettoField = new ListGridField("oggetto", I18NUtil.getMessages().postaElettronica_list_oggettoField_title());
		oggettoField.setWrap(false);
		oggettoField.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				String body = event.getRecord().getAttribute("oggetto");
				Record record = new Record();
				record.setAttribute("message", body);
				String idMail = event.getRecord().getAttribute("id") != null ? event.getRecord().getAttribute("id") : "";
				String tipologia = event.getRecord().getAttribute("tipoEmail") != null ? event.getRecord().getAttribute("tipoEmail")
						: "";
				String estremiEmail = "Dettaglio oggetto mail: "+idMail +" "+tipologia;
				record.setAttribute("estremiEmail", estremiEmail);
				new VisualizzaContenutoHeaderWindow("visualizza_contenuto_campi_email",record);
			}
		});

		corpoField = new ListGridField("corpo", I18NUtil.getMessages().postaElettronica_list_corpoField_title());
		corpoField.setAttribute("custom", true);
		corpoField.setCellAlign(Alignment.LEFT);
		corpoField.setWrap(false);
		corpoField.setEscapeHTML(true);
		corpoField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String corpo = (String) record.getAttribute("corpo");
				if (corpo != null && !"".equals(corpo)) {
					corpo = corpo.replaceAll("\n", " ").trim();
				}
				if (corpo == null)
					return null;
				if (corpo.length() > Layout.getGenericConfig().getMaxValueLength()) {
					return corpo.substring(0, Layout.getGenericConfig().getMaxValueLength()) + "...";
				} else
					return corpo;
			}
		});
		corpoField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return (String) record.getAttribute("corpo");
			}
		});
		corpoField.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				String body = event.getRecord().getAttribute("corpo");
				Record record = new Record();
				record.setAttribute("message", body);
				String idMail = event.getRecord().getAttribute("id") != null ? event.getRecord().getAttribute("id") : "";
				String tipologia = event.getRecord().getAttribute("tipoEmail") != null ? event.getRecord().getAttribute("tipoEmail")
						: "";
				String estremiEmail = "Dettaglio corpo mail: "+idMail +" "+tipologia;
				record.setAttribute("estremiEmail", estremiEmail);
				new VisualizzaContenutoHeaderWindow("visualizza_contenuto_campi_email", record);
			}
		});

		destinatariField = new ListGridField("destinatari", I18NUtil.getMessages().postaElettronica_list_destinatariField_title());
		destinatariField.setAttribute("custom", true);
		destinatariField.setCellAlign(Alignment.LEFT);
		destinatariField.setWrap(false);

		destinatariPrimariField = new ListGridField("destinatariPrimari", I18NUtil.getMessages().postaElettronica_list_destinatariPrimariField_title());
		destinatariPrimariField.setWrap(false);

		destinatariCCField = new ListGridField("destinatariCC", I18NUtil.getMessages().postaElettronica_list_destinatariCCField_title());
		destinatariCCField.setWrap(false);

		destinatariCCNField = new ListGridField("destinatariCCN", I18NUtil.getMessages().postaElettronica_list_destinatariCCNField_title());
		destinatariCCNField.setWrap(false);

		avvertimentiField = new ListGridField("avvertimenti", I18NUtil.getMessages().postaElettronica_list_avvertimentiField_title());
		avvertimentiField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		avvertimentiField.setType(ListGridFieldType.ICON);
		avvertimentiField.setAlign(Alignment.CENTER);
		avvertimentiField.setWrap(false);
		avvertimentiField.setWidth(30);
		avvertimentiField.setIconWidth(16);
		avvertimentiField.setIconHeight(16);
		avvertimentiField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String avvertimenti = (String) value;
				if (avvertimenti != null && !"".equals(avvertimenti)) {
					return buildIconHtml("warning.png","Attenzione: il sistema ha riscontrato degli errori nei dati. Per visualizzarli apri il dettaglio e-mail");
				}
				return null;
			}
		});
		avvertimentiField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String avvertimenti = (String) value;
				if (avvertimenti != null && !"".equals(avvertimenti)) {
					return "Attenzione: il sistema ha riscontrato degli errori nei dati. Per visualizzarli apri il dettaglio e-mail";
				}
				return null;
			}
		});

		// codAzione
		codAzioneDaFareField = new ListGridField("codAzioneDaFare", I18NUtil.getMessages().posta_elettronica_list_codAzioneDaFareField());
		codAzioneDaFareField.setAttribute("custom", true);
		codAzioneDaFareField.setHidden(true);
		codAzioneDaFareField.setCanHide(false);
		codAzioneDaFareField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				Record recordTmp = new Record();
				recordTmp.setAttribute("codAzioneDaFare", new RecordList((JavaScriptObject) record.getAttributeAsObject("azioneDaFareBean")));
				return (String) ((Map) recordTmp.toMap().get("codAzioneDaFare")).get("codAzioneDaFare");
			}
		});

		azioneDaFareField = new ListGridField("azioneDaFare", I18NUtil.getMessages().postaElettronica_list_azioneDaFare_title());
		azioneDaFareField.setAttribute("custom", true);
		azioneDaFareField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				Record recordTmp = new Record();
				recordTmp.setAttribute("azioneDaFare", new RecordList((JavaScriptObject) record.getAttributeAsObject("azioneDaFareBean")));
				return (String) ((Map) recordTmp.toMap().get("azioneDaFare")).get("azioneDaFare");
			}
		});

		azioneDaFareField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String azioneDaFareToolTip = (String) value;
				return azioneDaFareToolTip;
			}
		});

		// Dettaglio azione da fare
		dettaglioAzioneDaFareField = new ListGridField("dettaglioAzioneDaFare", I18NUtil.getMessages().posta_elettronica_list_dettaglioAzioneDaFareField());
		dettaglioAzioneDaFareField.setHidden(true);
		dettaglioAzioneDaFareField.setCanHide(false);
		dettaglioAzioneDaFareField.setAttribute("custom", true);

		dettaglioAzioneDaFareField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				Record recordTmp = new Record();

				recordTmp.setAttribute("dettaglioAzioneDaFare", new RecordList((JavaScriptObject) record.getAttributeAsObject("azioneDaFareBean")));
				return (String) ((Map) recordTmp.toMap().get("dettaglioAzioneDaFare")).get("dettaglioAzioneDaFare");
			}
		});

		iconaFlgRichConfermaField = new ListGridField("flgRichConferma", I18NUtil.getMessages().postaElettronica_list_iconaFlgRichConfermaField_title());
		iconaFlgRichConfermaField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaFlgRichConfermaField.setType(ListGridFieldType.ICON);
		iconaFlgRichConfermaField.setAlign(Alignment.CENTER);
		iconaFlgRichConfermaField.setWrap(false);
		iconaFlgRichConfermaField.setWidth(30);
		iconaFlgRichConfermaField.setIconWidth(16);
		iconaFlgRichConfermaField.setIconHeight(16);
		iconaFlgRichConfermaField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgRichConferma = (String) record.getAttribute("flgRichConferma");
				if (flgRichConferma != null && "1".equals(flgRichConferma)) {
					return buildIconHtml("postaElettronica/richConferma.png","Richiesta di conferma");
				}
				return null;
			}
		});
		iconaFlgRichConfermaField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgRichConferma = (String) record.getAttribute("flgRichConferma");
				if (flgRichConferma != null && "1".equals(flgRichConferma)) {
					return "Richiesta di conferma";
				}
				return null;
			}
		});

		tsAssegnField = new ListGridField("tsAssegn", I18NUtil.getMessages().postaElettronica_list_tsAssegnField_title());
		tsAssegnField.setType(ListGridFieldType.DATE);
		tsAssegnField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsAssegnField.setWrap(false);

		messaggioAssegnazioneField = new ListGridField("messaggioAssegnazione",
				I18NUtil.getMessages().postaElettronica_list_messaggioAssegnazioneField_title());
		messaggioAssegnazioneField.setWrap(false);

		// U.O. competente
		assegnatarioField = new ListGridField("assegnatario", I18NUtil.getMessages().posta_elettronica_list_assegnatarioField());
		assegnatarioField.setWrap(false);

		// Inviata risposta
		iconaFlgInviataRispostaField = new ListGridField("flgInviataRisposta", I18NUtil.getMessages().posta_elettronica_list_iconaFlgInviataRispostaField());
		iconaFlgInviataRispostaField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaFlgInviataRispostaField.setType(ListGridFieldType.ICON);
		iconaFlgInviataRispostaField.setAlign(Alignment.CENTER);
		iconaFlgInviataRispostaField.setWrap(false);
		iconaFlgInviataRispostaField.setWidth(30);
		iconaFlgInviataRispostaField.setIconWidth(16);
		iconaFlgInviataRispostaField.setIconHeight(16);
		iconaFlgInviataRispostaField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgInviataRisposta = (String) record.getAttribute("flgInviataRisposta");
				if (flgInviataRisposta != null && "1".equals(flgInviataRisposta)) {
					return buildIconHtml("postaElettronica/iconMilano/risposta_mail.png","Inviata risposta");

				}
				return null;
			}
		});
		iconaFlgInviataRispostaField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgInviataRisposta = (String) record.getAttribute("flgInviataRisposta");
				if (flgInviataRisposta != null && "1".equals(flgInviataRisposta)) {
					return "Inviata risposta";
				}
				return null;
			}
		});
		iconaFlgInviataRispostaField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				Record record = event.getRecord();
				String flgInviataRisposta = (String) record.getAttribute("flgInviataRisposta");
				if (flgInviataRisposta != null && "1".equals(flgInviataRisposta)) {
					final String idEmail = (String) record.getAttribute("idEmail");
					GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("EmailCollegateDataSource", "idEmail", FieldType.TEXT);
					lGWTRestDataSource.addParam("idEmail", idEmail);
					lGWTRestDataSource.addParam("tipoRel", "risposta");
					lGWTRestDataSource.fetchData(null, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							RecordList data = response.getDataAsRecordList();
							EmailCollegateWindow emailRispostaCollegateWindow = null;
							if (data.getLength() == 1) {
								emailRispostaCollegateWindow = new EmailCollegateWindow("risposta", idEmail, "1", data.get(0)) {

									public void manageOnCloseClick() {
										destroy();
										layout.doSearch();
									};
								};
								emailRispostaCollegateWindow.show();
							} else if (data.getLength() > 0) {
								emailRispostaCollegateWindow = new EmailCollegateWindow("risposta", idEmail, null, null) {

									public void manageOnCloseClick() {
										destroy();
										layout.doSearch();
									};
								};
								emailRispostaCollegateWindow.show();
							}
						}
					});
				}
			}
		});

		// Inoltrata
		iconaFlgInoltrataField = new ListGridField("flgInoltrata", I18NUtil.getMessages().posta_elettronica_list_iconaFlgInoltrataField());
		iconaFlgInoltrataField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaFlgInoltrataField.setType(ListGridFieldType.ICON);
		iconaFlgInoltrataField.setAlign(Alignment.CENTER);
		iconaFlgInoltrataField.setWrap(false);
		iconaFlgInoltrataField.setWidth(30);
		iconaFlgInoltrataField.setIconWidth(16);
		iconaFlgInoltrataField.setIconHeight(16);
		iconaFlgInoltrataField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgInviataInoltro = (String) record.getAttribute("flgInoltrata");
				if (flgInviataInoltro != null && "1".equals(flgInviataInoltro)) {
					// return buildImgButtonHtml("postaElettronica/inoltro.png");
					return buildIconHtml("postaElettronica/iconMilano/inoltro_mail.png","Inoltrata");
				}
				return null;
			}
		});
		iconaFlgInoltrataField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgInviataInoltro = (String) record.getAttribute("flgInoltrata");
				if (flgInviataInoltro != null && "1".equals(flgInviataInoltro)) {
					return "Inoltrata";
				}
				return null;
			}
		});
		iconaFlgInoltrataField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				Record record = event.getRecord();
				String flgInviataInoltro = (String) record.getAttribute("flgInoltrata");
				if (flgInviataInoltro != null && "1".equals(flgInviataInoltro)) {
					final String idEmail = (String) record.getAttribute("idEmail");
					GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("EmailCollegateDataSource", "idEmail", FieldType.TEXT);
					lGWTRestDataSource.addParam("idEmail", idEmail);
					lGWTRestDataSource.addParam("tipoRel", "inoltro");
					lGWTRestDataSource.fetchData(null, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							RecordList data = response.getDataAsRecordList();
							EmailCollegateWindow emailInoltroCollegateWindow = null;
							if (data.getLength() == 1) {
								emailInoltroCollegateWindow = new EmailCollegateWindow("inoltro", idEmail, "1", data.get(0)) {

									public void manageOnCloseClick() {
										destroy();
										layout.doSearch();
									};
								};
								emailInoltroCollegateWindow.show();
							} else if (data.getLength() > 0) {
								emailInoltroCollegateWindow = new EmailCollegateWindow("inoltro", idEmail, null, null) {

									public void manageOnCloseClick() {
										destroy();
										layout.doSearch();
									};
								};
								emailInoltroCollegateWindow.show();
							}
						}
					});
				}
			}
		});

		iconaFlgStatoProtField = new ListGridField("flgStatoProt", I18NUtil.getMessages().postaElettronica_list_iconaFlgStatoProtField_title());
		iconaFlgStatoProtField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaFlgStatoProtField.setType(ListGridFieldType.ICON);
		iconaFlgStatoProtField.setAlign(Alignment.CENTER);
		iconaFlgStatoProtField.setWrap(false);
		iconaFlgStatoProtField.setWidth(30);
		iconaFlgStatoProtField.setIconWidth(16);
		iconaFlgStatoProtField.setIconHeight(16);
		iconaFlgStatoProtField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgStatoProt = (String) record.getAttribute("flgStatoProt");
				if (flgStatoProt != null && !"".equals(flgStatoProt)) {
					String label = "";
					if ("P".equals(flgStatoProt)) {
						label =  "Sono stati protocollati solo alcuni degli allegati dell'e-mail";
					} else if ("C".equals(flgStatoProt)) {
						label =  "Tutti gli allegati/parti dell'e-mail sono stati protocollati";
					}
					return buildIconHtml("postaElettronica/flgStatoProt/" + flgStatoProt + ".png",label);
				}
				return null;
			}
		});
		iconaFlgStatoProtField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgStatoProt = (String) record.getAttribute("flgStatoProt");
				if (flgStatoProt != null) {
					if ("P".equals(flgStatoProt)) {
						return "Sono stati protocollati solo alcuni degli allegati dell'e-mail";
					} else if ("C".equals(flgStatoProt)) {
						return "Tutti gli allegati/parti dell'e-mail sono stati protocollati";
					}
				}
				return null;
			}
		});

		iconaFlgNotifInteropEccField = new ListGridField("flgNotifInteropEcc",
				I18NUtil.getMessages().postaElettronica_list_iconaFlgNotifInteropEccField_title());
		iconaFlgNotifInteropEccField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaFlgNotifInteropEccField.setType(ListGridFieldType.ICON);
		iconaFlgNotifInteropEccField.setAlign(Alignment.CENTER);
		iconaFlgNotifInteropEccField.setWrap(false);
		iconaFlgNotifInteropEccField.setWidth(30);
		iconaFlgNotifInteropEccField.setIconWidth(16);
		iconaFlgNotifInteropEccField.setIconHeight(16);
		iconaFlgNotifInteropEccField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNotifInteropEcc = (String) record.getAttribute("flgNotifInteropEcc");
				if (flgNotifInteropEcc != null && "1".equals(flgNotifInteropEcc)) {
					return buildIconHtml("postaElettronica/notifInteropEcc.png","Pervenute notifiche di eccezione");
				}
				return null;
			}
		});
		iconaFlgNotifInteropEccField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNotifInteropEcc = (String) record.getAttribute("flgNotifInteropEcc");
				if (flgNotifInteropEcc != null && "1".equals(flgNotifInteropEcc)) {
					return "Pervenute notifiche di eccezione";
				}
				return null;
			}
		});

		iconaFlgNotifInteropConfField = new ListGridField("flgNotifInteropConf",
				I18NUtil.getMessages().postaElettronica_list_iconaFlgNotifInteropConfField_title());
		iconaFlgNotifInteropConfField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaFlgNotifInteropConfField.setType(ListGridFieldType.ICON);
		iconaFlgNotifInteropConfField.setAlign(Alignment.CENTER);
		iconaFlgNotifInteropConfField.setWrap(false);
		iconaFlgNotifInteropConfField.setWidth(30);
		iconaFlgNotifInteropConfField.setIconWidth(16);
		iconaFlgNotifInteropConfField.setIconHeight(16);
		iconaFlgNotifInteropConfField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNotifInteropConf = (String) record.getAttribute("flgNotifInteropConf");
				if (flgNotifInteropConf != null && "1".equals(flgNotifInteropConf)) {
					return buildIconHtml("postaElettronica/notifInteropConf.png","Pervenute notifiche di conferma");
				}
				return null;
			}
		});
		iconaFlgNotifInteropConfField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNotifInteropConf = (String) record.getAttribute("flgNotifInteropConf");
				if (flgNotifInteropConf != null && "1".equals(flgNotifInteropConf)) {
					return "Pervenute notifiche di conferma";
				}
				return null;
			}
		});

		iconaFlgNotifInteropAggField = new ListGridField("flgNotifInteropAgg",
				I18NUtil.getMessages().postaElettronica_list_iconaFlgNotifInteropAggField_title());
		iconaFlgNotifInteropAggField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaFlgNotifInteropAggField.setType(ListGridFieldType.ICON);
		iconaFlgNotifInteropAggField.setAlign(Alignment.CENTER);
		iconaFlgNotifInteropAggField.setWrap(false);
		iconaFlgNotifInteropAggField.setWidth(30);
		iconaFlgNotifInteropAggField.setIconWidth(16);
		iconaFlgNotifInteropAggField.setIconHeight(16);
		iconaFlgNotifInteropAggField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNotifInteropAgg = (String) record.getAttribute("flgNotifInteropAgg");
				if (flgNotifInteropAgg != null && "1".equals(flgNotifInteropAgg)) {
					return buildIconHtml("postaElettronica/notifInteropAgg.png","Pervenute notifiche di aggiornamento");
				}
				return null;
			}
		});
		iconaFlgNotifInteropAggField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNotifInteropAgg = (String) record.getAttribute("flgNotifInteropAgg");
				if (flgNotifInteropAgg != null && "1".equals(flgNotifInteropAgg)) {
					return "Pervenute notifiche di aggiornamento";
				}
				return null;
			}
		});

		iconaFlgNotifInteropAnnField = new ListGridField("flgNotifInteropAnn",
				I18NUtil.getMessages().postaElettronica_list_iconaFlgNotifInteropAnnField_title());
		iconaFlgNotifInteropAnnField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaFlgNotifInteropAnnField.setType(ListGridFieldType.ICON);
		iconaFlgNotifInteropAnnField.setAlign(Alignment.CENTER);
		iconaFlgNotifInteropAnnField.setWrap(false);
		iconaFlgNotifInteropAnnField.setWidth(30);
		iconaFlgNotifInteropAnnField.setIconWidth(16);
		iconaFlgNotifInteropAnnField.setIconHeight(16);
		iconaFlgNotifInteropAnnField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNotifInteropAnn = (String) record.getAttribute("flgNotifInteropAnn");
				if (flgNotifInteropAnn != null && "1".equals(flgNotifInteropAnn)) {
					return buildIconHtml("postaElettronica/notifInteropAnn.png","Pervenute notifiche di annullamento");
				}
				return null;
			}
		});
		iconaFlgNotifInteropAnnField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNotifInteropAnn = (String) record.getAttribute("flgNotifInteropAnn");
				if (flgNotifInteropAnn != null && "1".equals(flgNotifInteropAnn)) {
					return "Pervenute notifiche di annullamento";
				}
				return null;
			}
		});

		livPrioritaField = new ListGridField("livPriorita", I18NUtil.getMessages().postaElettronica_list_livPrioritaField_title());
		livPrioritaField.setWrap(false);

		estremiProtCollegatiField = new ListGridField("estremiProtCollegati", I18NUtil.getMessages().postaElettronica_list_estremiProtCollegatiField_title());
		estremiProtCollegatiField.setAttribute("custom", true);
		estremiProtCollegatiField.setWrap(false);

		estremiDocInviatoField = new ListGridField("estremiDocInviato", I18NUtil.getMessages().postaElettronica_list_estremiDocInviatoField_title());
		estremiDocInviatoField.setAttribute("custom", true);
		estremiDocInviatoField.setWrap(false);
		estremiDocInviatoField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				Map lMap = event.getRecord().getAttributeAsMap("estremiRegProtAssociati");
				if (lMap != null && lMap.size() == 1) {
					Record record = new Record();
					record.setAttribute("idUd", lMap.keySet().iterator().next());
					DettaglioRegProtAssociatoWindow dettaglioRegProtAssociatoWindow = new DettaglioRegProtAssociatoWindow(record, "Dettaglio doc. inviato");
				}
			}
		});

		emailCollegataField = new ListGridField("idEmailCollegata", I18NUtil.getMessages().postaElettronica_list_emailCollegataField_title());
		emailCollegataField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		emailCollegataField.setAlign(Alignment.CENTER);
		emailCollegataField.setWrap(false);
		emailCollegataField.setWidth(30);
		emailCollegataField.setAttribute("custom", true);
		emailCollegataField.setShowHover(true);
		emailCollegataField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("idEmailCollegata") != null && !"".equals(record.getAttribute("idEmailCollegata"))) {
					return buildImgButtonHtml("postaElettronica/emailCollegata.png");
				}
				return null;
			}
		});
		emailCollegataField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("idEmailCollegata") != null && !"".equals(record.getAttribute("idEmailCollegata"))) {
					return "Vai a e-mail collegata";
				}
				return null;
			}
		});
		emailCollegataField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				Record record = event.getRecord();
				if (record.getAttribute("idEmailCollegata") != null && !"".equals(record.getAttribute("idEmailCollegata"))) {
					new DettaglioEmailWindow(record.getAttribute("idEmailCollegata"), null, getLayout());
				}
			}
		});

		protocolliDestinatariField = new ListGridField("protocolliDestinatari",
				I18NUtil.getMessages().postaElettronica_list_protocolliDestinatariField_title());

		// Tipo
		tipoEmailField = new ListGridField("tipoEmail", I18NUtil.getMessages().posta_elettronica_list_tipoEmailField());

		// Sotto-tipo
		sottotipoEmailField = new ListGridField("sottotipoEmail", I18NUtil.getMessages().posta_elettronica_list_sottotipoEmailField());

		// Mail certificata
		flgEmailCertificataField = new ListGridField("flgEmailCertificata", I18NUtil.getMessages().posta_elettronica_list_flgEmailCertificataField());
		flgEmailCertificataField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgEmailCertificataField.setType(ListGridFieldType.ICON);
		flgEmailCertificataField.setAlign(Alignment.CENTER);
		flgEmailCertificataField.setWrap(false);
		flgEmailCertificataField.setWidth(30);
		flgEmailCertificataField.setIconWidth(16);
		flgEmailCertificataField.setIconHeight(16);
		flgEmailCertificataField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgEmailCertificata = (String) record.getAttribute("flgEmailCertificata");
				if (flgEmailCertificata != null && "1".equals(flgEmailCertificata)) {
					return buildIconHtml("coccarda.png","Mitt. certificato");
				}
				return null;
			}
		});
		flgEmailCertificataField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return "Mitt. certificato";
			}
		});

		// Interoperabile
		flgInteroperabileField = new ListGridField("flgInteroperabile", I18NUtil.getMessages().posta_elettronica_list_flgInteroperabileField());
		flgInteroperabileField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgInteroperabileField.setType(ListGridFieldType.ICON);
		flgInteroperabileField.setAlign(Alignment.CENTER);
		flgInteroperabileField.setWrap(false);
		flgInteroperabileField.setWidth(30);
		flgInteroperabileField.setIconWidth(16);
		flgInteroperabileField.setIconHeight(16);
		flgInteroperabileField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgInteroperabile = (String) record.getAttribute("flgInteroperabile");
				if (flgInteroperabile != null && "1".equals(flgInteroperabile)) {
					return buildIconHtml("postaElettronica/interoperabile.png","Interoperabile");
				}
				return null;
			}
		});
		flgInteroperabileField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return "Interoperabile";
			}
		});

		// Stato lavorazione
		statoLavorazioneField = new ListGridField("statoLavorazione", I18NUtil.getMessages().posta_elettronica_list_statoLavorazioneField());
		statoLavorazioneField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoLavorazioneField.setType(ListGridFieldType.ICON);
		statoLavorazioneField.setAlign(Alignment.CENTER);
		statoLavorazioneField.setWrap(false);
		statoLavorazioneField.setWidth(30);
		statoLavorazioneField.setIconWidth(16);
		statoLavorazioneField.setIconHeight(16);
		statoLavorazioneField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				String statoLavorazione = (String) value;

				if ("lavorazione aperta".equals(statoLavorazione))
					statoLavorazione = "aperta";
				if ("lavorazione conclusa".equals(statoLavorazione))
					statoLavorazione = "chiusa";

				if (statoLavorazione != null && !"".equals(statoLavorazione)) {
					return buildIconHtml("postaElettronica/statoLavorazione/" + statoLavorazione + ".png",statoLavorazione);
				}
				return null;
			}
		});
		statoLavorazioneField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				String statoLavorazione = (String) record.getAttribute("statoLavorazione");

				if ("lavorazione aperta".equals(statoLavorazione))
					statoLavorazione = "aperta";
				if ("lavorazione conclusa".equals(statoLavorazione))
					statoLavorazione = "chiusa";

				return statoLavorazione;
			}
		});

		SortNormalizer contrassegnoSortNormalizer = new SortNormalizer() {

			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				return record.getAttribute("gradoUrgenzaContrassegno");
			}
		};

		/* Da togliere */
		contrassegnoField = new ListGridField("idRecDizContrassegno", I18NUtil.getMessages().postaElettronica_list_contrassegnoField_title());
		contrassegnoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		contrassegnoField.setType(ListGridFieldType.ICON);
		contrassegnoField.setAlign(Alignment.CENTER);
		contrassegnoField.setWrap(false);
		contrassegnoField.setWidth(30);
		contrassegnoField.setIconWidth(16);
		contrassegnoField.setIconHeight(16);
		contrassegnoField.setSortNormalizer(contrassegnoSortNormalizer);
		contrassegnoField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String idRecDizContrassegno = (String) value;
				if (idRecDizContrassegno != null && !"".equals(idRecDizContrassegno)) {
					return buildIconHtml("postaElettronica/contrassegno/" + idRecDizContrassegno.substring(6).toLowerCase() + ".png",(String) record.getAttribute("contrassegno"));
				}
				return null;
			}
		});
		contrassegnoField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return (String) record.getAttribute("contrassegno");
			}
		});

		// Prot. mitt
		protocolloMittenteField = new ListGridField("protocolloMittente", I18NUtil.getMessages().posta_elettronica_list_protocolloMittenteField());

		// Oggetto prot. mitt
		oggettoProtocolloMittenteField = new ListGridField("oggettoProtocolloMittente",
				I18NUtil.getMessages().posta_elettronica_list_oggettoProtocolloMittenteField());

		// Stato Invio
		statoInvioField = new ListGridField("statoInvio", I18NUtil.getMessages().posta_elettronica_list_statoInvioField());
		statoInvioField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoInvioField.setType(ListGridFieldType.ICON);
		statoInvioField.setAlign(Alignment.CENTER);
		statoInvioField.setWrap(false);
		statoInvioField.setWidth(30);
		statoInvioField.setIconWidth(16);
		statoInvioField.setIconHeight(16);
		statoInvioField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoInvio = (String) record.getAttribute("statoInvio");

				return getIconaStatoConsolidamento(statoInvio,(String) record.getAttribute("descrizioneStatoInvio"));

			}
		});
		statoInvioField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return (String) record.getAttribute("descrizioneStatoInvio");
			}
		});

		// Stato Accettazione
		statoAccettazioneField = new ListGridField("statoAccettazione", I18NUtil.getMessages().posta_elettronica_list_statoAccettazioneField());
		statoAccettazioneField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoAccettazioneField.setType(ListGridFieldType.ICON);
		statoAccettazioneField.setAlign(Alignment.CENTER);
		statoAccettazioneField.setWrap(false);
		statoAccettazioneField.setWidth(30);
		statoAccettazioneField.setIconWidth(16);
		statoAccettazioneField.setIconHeight(16);
		statoAccettazioneField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoAccettazione = (String) record.getAttribute("statoAccettazione");

				return getIconaStatoConsolidamento(statoAccettazione,(String) record.getAttribute("descrizioneStatoAccettazione"));
			}
		});
		statoAccettazioneField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return (String) record.getAttribute("descrizioneStatoAccettazione");
			}
		});

		// Stato Consegna
		statoConsegnaField = new ListGridField("statoConsegna", I18NUtil.getMessages().posta_elettronica_list_statoConsegnaField());
		statoConsegnaField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoConsegnaField.setType(ListGridFieldType.ICON);
		statoConsegnaField.setAlign(Alignment.CENTER);
		statoConsegnaField.setWrap(false);
		statoConsegnaField.setWidth(30);
		statoConsegnaField.setIconWidth(16);
		statoConsegnaField.setIconHeight(16);
		statoConsegnaField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoConsegna = (String) record.getAttribute("statoConsegna");

				return getIconaStatoConsolidamento(statoConsegna,(String) record.getAttribute("descrizioneStatoConsegna"));
			}
		});

		statoConsegnaField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return (String) record.getAttribute("descrizioneStatoConsegna");
			}
		});

		// Aperta da giorni
		nroGiorniApertaField = new ListGridField("nroGiorniAperta", I18NUtil.getMessages().posta_elettronica_list_nroGiorniApertaField());
		nroGiorniApertaField.setAttribute("custom", true);
		nroGiorniApertaField.setCellAlign(Alignment.RIGHT);
		nroGiorniApertaField.setSortByDisplayField(false);
		nroGiorniApertaField.setDisplayField("nroGiorniAperta");
		nroGiorniApertaField.setCanGroupBy(true);
		nroGiorniApertaField.setCanReorder(true);
		nroGiorniApertaField.setCanFreeze(true);
		nroGiorniApertaField.setShowDefaultContextMenu(true);
		nroGiorniApertaField.setCanDragResize(true);

		// inCaricoA
		inCaricoAField = new ListGridField("inCaricoA", I18NUtil.getMessages().posta_elettronica_list_inCaricoAField());
		inCaricoAField.setAttribute("custom", true);
		inCaricoAField.setCellAlign(Alignment.RIGHT);
		inCaricoAField.setSortByDisplayField(false);
		inCaricoAField.setDisplayField("inCaricoA");
		inCaricoAField.setCanGroupBy(true);
		inCaricoAField.setCanReorder(true);
		inCaricoAField.setCanFreeze(true);
		inCaricoAField.setShowDefaultContextMenu(true);
		inCaricoAField.setCanDragResize(true);

		// inCaricoDal
		inCaricoDalField = new ListGridField("inCaricoDal", I18NUtil.getMessages().posta_elettronica_list_inCaricoDalField());
		inCaricoDalField.setAttribute("custom", true);
		inCaricoDalField.setCellAlign(Alignment.RIGHT);
		inCaricoDalField.setSortByDisplayField(false);
		inCaricoDalField.setDisplayField("inCaricoDal");
		inCaricoDalField.setCanGroupBy(true);
		inCaricoDalField.setCanReorder(true);
		inCaricoDalField.setCanFreeze(true);
		inCaricoDalField.setShowDefaultContextMenu(true);
		inCaricoDalField.setCanDragResize(true);

		annotazioniField = new ListGridField("annotazioni", I18NUtil.getMessages().posta_elettronica_list_appuntiNote());

		// Tag Apposto
		tagAppostoField = new ListGridField("tagApposto", I18NUtil.getMessages().posta_elettronica_list_tagAppostoField()); 
		
		
		
		tsChiusuraField = new ListGridField("tsChiusura", I18NUtil.getMessages().postaElettronica_list_tsChiusuraField_title());
		tsChiusuraField.setType(ListGridFieldType.DATE);
		tsChiusuraField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsChiusuraField.setWrap(false);

		chiusuraEffettuataDaField = new ListGridField("chiusuraEffettuataDa", I18NUtil.getMessages().postaElettronica_list_chiusuraEffettuataDaField_title());
		chiusuraEffettuataDaField.setWrap(false);

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
//		selectRowAccessibleField.addRecordClickHandler(new RecordClickHandler() {
//			
//			@Override
//			public void onRecordClick(RecordClickEvent event) {
//				// TODO Auto-generated method stub
//				ListGridRecord record = getRecord(event.getRecordNum());
//				if (isSelected(record)) {
//					deselectRowByRowNum(event.getRecordNum()+"", "");
//				} else {
//					selectRowByRowNum(event.getRecordNum()+"", "");			
//				}
//			}
//		});

		
		List<ListGridField> fieldsToSet = new ArrayList<ListGridField>();
		if (classifica != null) {
			if (classifica.startsWith("standard.arrivo") || classifica.startsWith("standard.archiviata.arrivo")) {
				fieldsToSet.add(id);
				fieldsToSet.add(idEmailField);
				fieldsToSet.add(flgIoField);
				fieldsToSet.add(messageIdField);
				fieldsToSet.add(idCasellaField);
				fieldsToSet.add(casellaRicezioneField);
				fieldsToSet.add(tsSalvataggioEmailField);
				fieldsToSet.add(tsRicezioneField);
				fieldsToSet.add(tsInvioClientField);
				fieldsToSet.add(dimensioneField);
				fieldsToSet.add(iconaFlgSpamField);
				fieldsToSet.add(allegatiField);
				fieldsToSet.add(iconaFlgNoAssocAutoField);
				fieldsToSet.add(accountMittenteField);
				fieldsToSet.add(oggettoField);
				fieldsToSet.add(corpoField);
				fieldsToSet.add(destinatariField);
				fieldsToSet.add(destinatariPrimariField);
				fieldsToSet.add(destinatariCCField);
				fieldsToSet.add(destinatariCCNField);
				fieldsToSet.add(codAzioneDaFareField);
				fieldsToSet.add(azioneDaFareField);
				fieldsToSet.add(dettaglioAzioneDaFareField);
				fieldsToSet.add(avvertimentiField);
				fieldsToSet.add(tsAssegnField);
				fieldsToSet.add(assegnatarioField);
				fieldsToSet.add(messaggioAssegnazioneField);
				fieldsToSet.add(iconaFlgInviataRispostaField);
				fieldsToSet.add(iconaFlgInoltrataField);
				fieldsToSet.add(iconaFlgStatoProtField);
				fieldsToSet.add(livPrioritaField);
				fieldsToSet.add(estremiProtCollegatiField);
				fieldsToSet.add(emailCollegataField);
				fieldsToSet.add(tipoEmailField);
				fieldsToSet.add(sottotipoEmailField);
				fieldsToSet.add(flgEmailCertificataField);
				fieldsToSet.add(flgInteroperabileField);

				fieldsToSet.add(inCaricoAField);
				fieldsToSet.add(inCaricoDalField);
				fieldsToSet.add(annotazioniField);
				fieldsToSet.add(tagAppostoField);
				fieldsToSet.add(tsChiusuraField);
				fieldsToSet.add(chiusuraEffettuataDaField);

				if (classifica.startsWith("standard.arrivo.interoperabili") || classifica.startsWith("standard.archiviata.arrivo")) {
					fieldsToSet.add(protocolloMittenteField);
					fieldsToSet.add(oggettoProtocolloMittenteField);
				}
			} else if (classifica.startsWith("standard.invio") || classifica.startsWith("standard.archiviata.invio")
					|| classifica.startsWith("standard.uscita")) {
				fieldsToSet.add(id);
				fieldsToSet.add(idEmailField);
				fieldsToSet.add(flgIoField);
				fieldsToSet.add(idCasellaField);
				fieldsToSet.add(casellaRicezioneField);
				fieldsToSet.add(tsInvioClientField);
				fieldsToSet.add(dimensioneField);
				fieldsToSet.add(uriEmailField);
				fieldsToSet.add(allegatiField);
				fieldsToSet.add(oggettoField);
				fieldsToSet.add(corpoField);
				fieldsToSet.add(destinatariField);
				fieldsToSet.add(destinatariPrimariField);
				fieldsToSet.add(destinatariCCField);
				fieldsToSet.add(destinatariCCNField);
				fieldsToSet.add(codAzioneDaFareField);
				fieldsToSet.add(azioneDaFareField);
				fieldsToSet.add(dettaglioAzioneDaFareField);
				fieldsToSet.add(iconaFlgRichConfermaField);
				fieldsToSet.add(iconaFlgNotifInteropEccField);
				fieldsToSet.add(iconaFlgNotifInteropConfField);
				fieldsToSet.add(iconaFlgNotifInteropAggField);
				fieldsToSet.add(iconaFlgNotifInteropAnnField);
				fieldsToSet.add(livPrioritaField);
				fieldsToSet.add(estremiDocInviatoField);
				fieldsToSet.add(protocolliDestinatariField);
				fieldsToSet.add(tipoEmailField);
				fieldsToSet.add(sottotipoEmailField);
				fieldsToSet.add(flgEmailCertificataField);
				fieldsToSet.add(flgInteroperabileField);
				fieldsToSet.add(inCaricoAField);
				fieldsToSet.add(inCaricoDalField);
				fieldsToSet.add(annotazioniField);
				fieldsToSet.add(tagAppostoField);
				fieldsToSet.add(tsChiusuraField);
				fieldsToSet.add(chiusuraEffettuataDaField);

				if (classifica.startsWith("standard.invio") || classifica.startsWith("standard.archiviata.invio")) {
					fieldsToSet.add(nroGiorniApertaField);
					fieldsToSet.add(statoInvioField);
					fieldsToSet.add(statoAccettazioneField);
					fieldsToSet.add(statoConsegnaField);
				}
			} else if (classifica.startsWith("standard.eliminata") || classifica.startsWith("standard.bozze") || classifica.startsWith("custom.")) {
				fieldsToSet.add(id);
				fieldsToSet.add(idEmailField);
				fieldsToSet.add(flgIoField);
				fieldsToSet.add(idCasellaField);
				fieldsToSet.add(casellaRicezioneField);
				fieldsToSet.add(tsInvioClientField);
				fieldsToSet.add(dimensioneField);
				fieldsToSet.add(uriEmailField);
				fieldsToSet.add(oggettoField);
				fieldsToSet.add(corpoField);
				fieldsToSet.add(destinatariField);
				fieldsToSet.add(destinatariPrimariField);
				fieldsToSet.add(destinatariCCField);
				fieldsToSet.add(destinatariCCNField);
				fieldsToSet.add(codAzioneDaFareField);
				fieldsToSet.add(azioneDaFareField);
				fieldsToSet.add(dettaglioAzioneDaFareField);
				fieldsToSet.add(livPrioritaField);
				fieldsToSet.add(inCaricoAField);
				fieldsToSet.add(inCaricoDalField);
				fieldsToSet.add(annotazioniField);
				fieldsToSet.add(tagAppostoField);
				fieldsToSet.add(tsChiusuraField);
				fieldsToSet.add(chiusuraEffettuataDaField);
			}
		} else {
			fieldsToSet.add(id);
			fieldsToSet.add(idEmailField);
			fieldsToSet.add(flgIoField);
			fieldsToSet.add(messageIdField);
			fieldsToSet.add(idCasellaField);
			fieldsToSet.add(casellaRicezioneField);
			fieldsToSet.add(tsSalvataggioEmailField);
			fieldsToSet.add(tsRicezioneField);
			fieldsToSet.add(tsInvioClientField);
			fieldsToSet.add(dimensioneField);
			fieldsToSet.add(iconaFlgSpamField);
			fieldsToSet.add(allegatiField);
			fieldsToSet.add(iconaFlgNoAssocAutoField);
			fieldsToSet.add(accountMittenteField);
			fieldsToSet.add(oggettoField);
			fieldsToSet.add(corpoField);
			fieldsToSet.add(destinatariField);
			fieldsToSet.add(destinatariPrimariField);
			fieldsToSet.add(destinatariCCField);
			fieldsToSet.add(destinatariCCNField);
			fieldsToSet.add(codAzioneDaFareField);
			fieldsToSet.add(azioneDaFareField);
			fieldsToSet.add(dettaglioAzioneDaFareField);
			fieldsToSet.add(avvertimentiField);
			fieldsToSet.add(tsAssegnField);
			fieldsToSet.add(assegnatarioField);
			fieldsToSet.add(messaggioAssegnazioneField);
			fieldsToSet.add(iconaFlgInviataRispostaField);
			fieldsToSet.add(iconaFlgInoltrataField);
			fieldsToSet.add(iconaFlgStatoProtField);
			fieldsToSet.add(livPrioritaField);
			fieldsToSet.add(estremiProtCollegatiField);
			fieldsToSet.add(emailCollegataField);
			fieldsToSet.add(tipoEmailField);
			fieldsToSet.add(sottotipoEmailField);
			fieldsToSet.add(flgEmailCertificataField);
			fieldsToSet.add(flgInteroperabileField);
			fieldsToSet.add(statoLavorazioneField);

			fieldsToSet.add(statoInvioField);
			fieldsToSet.add(statoAccettazioneField);
			fieldsToSet.add(statoConsegnaField);
			fieldsToSet.add(nroGiorniApertaField);
			fieldsToSet.add(inCaricoAField);
			fieldsToSet.add(inCaricoDalField);
			fieldsToSet.add(annotazioniField);
			fieldsToSet.add(tagAppostoField);
			fieldsToSet.add(tsChiusuraField);
			fieldsToSet.add(chiusuraEffettuataDaField);
		}
		if (AurigaLayout.getIsAttivaAccessibilita()) {
//			fieldsToSet.add(checkboxFieldAcc);
			fieldsToSet.add(detailButtonAccessibleField);
			fieldsToSet.add(0,selectRowAccessibleField);
			setShowAllRecords(AurigaLayout.getParametroDBAsBoolean("ATTIVA_PAGINAZIONE_CRUSCOTTO_MAIL"));
//			setArrowKeyAction("none");
			setShowSortArrow(SortArrow.NONE);
			setArrowKeyAction("focus");
			
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
	
		}
		setFields(fieldsToSet.toArray(new ListGridField[fieldsToSet.size()]));
	}

	private String getIconaStatoConsolidamento(String stato, String label) {
		if (stato != null && "OK".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/consegnata.png",label);
		}

		if (stato != null && "IP".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/presunto_ok.png",label);
		}

		if (stato != null && "W".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/ko-arancione.png",label);
		}

		if (stato != null && "KO".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/ko-red.png",label);
		}

		if (stato != null && "ND".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/stato_consegna.png",label);
		}

		return null;
	}

	/**
	 * Inizializza la colonna contenente l'id della mail
	 */
	protected void buildIdField() {

		id = new ListGridField("progrOrdinamento", I18NUtil.getMessages().postaElettronica_list_messageProgressivoField_title());
		id.setAttribute("custom", true);
		id.setCellAlign(Alignment.RIGHT);
		id.setSortByDisplayField(false);
		id.setDisplayField("id");
		id.setCanGroupBy(false);
		id.setCanReorder(false);
		id.setCanFreeze(false);
		id.setShowDefaultContextMenu(false);
		id.setCanDragResize(true);
		id.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {

				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				manageDetailButtonClick(record);
			}
		});
	}

	@Override
	protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		try {
			if (getFieldName(colNum).equals("progrOrdinamento")) {
				if (record.getAttribute("id") != null && record.getAttribute("id").toUpperCase().endsWith(".B")) {
					return it.eng.utility.Styles.cellTextOrangeClickable;
				} else if (record.getAttribute("flgIo") != null) {
					if (record.getAttribute("flgIo").equalsIgnoreCase("I")) {
						if (record.getAttribute("categoria") != null && (record.getAttribute("categoria").equalsIgnoreCase("INTEROP_SEGN")
								|| record.getAttribute("categoria").equalsIgnoreCase("PEC") || record.getAttribute("categoria").equalsIgnoreCase("ANOMALIA")
								|| record.getAttribute("categoria").equalsIgnoreCase("ALTRO"))) {
							return it.eng.utility.Styles.cellTextGreenClickable;
						} else {
							return it.eng.utility.Styles.cellTextGreyClickable;
						}
					} else if (record.getAttribute("flgIo").equalsIgnoreCase("O")) {
						if (record.getAttribute("categoria") != null && (record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ECC")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_CONF")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_AGG")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ANN"))) {
							return it.eng.utility.Styles.cellTextGreyClickable;
						} else
							return it.eng.utility.Styles.cellTextBlueClickable;
					}
				}
			} else {
				if (record.getAttribute("id") != null && record.getAttribute("id").toUpperCase().endsWith(".B")) {
					return it.eng.utility.Styles.cellOrange;
				} else if (record.getAttribute("flgIo") != null) {
					if (record.getAttribute("flgIo").equalsIgnoreCase("I")) {
						if (record.getAttribute("categoria") != null && (record.getAttribute("categoria").equalsIgnoreCase("INTEROP_SEGN")
								|| record.getAttribute("categoria").equalsIgnoreCase("PEC") || record.getAttribute("categoria").equalsIgnoreCase("ANOMALIA")
								|| record.getAttribute("categoria").equalsIgnoreCase("ALTRO"))) {
							return it.eng.utility.Styles.cellGreen;
						} else {
							return it.eng.utility.Styles.cellGrey;
						}
					} else if (record.getAttribute("flgIo").equalsIgnoreCase("O")) {
						if (record.getAttribute("categoria") != null && (record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ECC")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_CONF")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_AGG")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ANN"))) {
							return it.eng.utility.Styles.cellGrey;
						}
						return it.eng.utility.Styles.cellBlue;
					}
				}
			}
			return super.getBaseStyle(record, rowNum, colNum);
		} catch (Exception e) {
			return super.getBaseStyle(record, rowNum, colNum);
		}
	}

	@Override
	protected int getButtonsFieldWidth() {
		return 50;
	}

	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {

		if (getFieldName(colNum).equals("estremiDocInviato")) {

			Map lMap = record.getAttributeAsMap("estremiRegProtAssociati");
			if (lMap != null && lMap.size() == 1) {
				return "cursor:hand; color:blue; text-decoration:underline";
			}
		}
		return super.getCellCSSText(record, rowNum, colNum);
	}

	@Override
	protected void manageAltreOpButtonClick(ListGridRecord record) {
		if (record != null) {
			showRowContextMenu(record);
		}
	}

	// Definisco i bottoni DETTAGLIO/MODIFICA/CANCELLA/SELEZIONA
	@Override
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {

		Canvas lCanvasReturn = null;

		if (fieldName.equals("buttons")) {

			ImgButton detailButton = buildDetailButton(record);
			ImgButton lookupButton = buildLookupButton(record);

			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);
			recordCanvas.setMembersMargin(7);

			if(!AurigaLayout.getIsAttivaAccessibilita()) {
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

	@Override
	public void manageContextClick(final Record record) {
		if (record != null) {
			showRowContextMenu(record);
		}
	}

	public void visualizzaFileAllegato(final Record detailRecord, int nroAllegato) {
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		final String display = allegatoRecord.getAttributeAsString("displayFileName");
		final String numeroProgrAllegato = allegatoRecord.getAttributeAsString("numeroProgrAllegato");
		String uri = allegatoRecord.getAttributeAsString("uri");
		final String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		final Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
		InfoFileRecord infoFileRecord = new InfoFileRecord(infoFile);
		if (allegatoRecord.getAttribute("bytes") != null) {
			infoFileRecord.setBytes(Long.parseLong(allegatoRecord.getAttribute("bytes")));
		}

		final String title;
		if ((numeroProgrAllegato != null) && (numeroProgrAllegato.length() > 0)) {
			title = "Allegato N°" + numeroProgrAllegato + " - " + display;
		} else {
			title = display;
		}

		visualizzaFile(detailRecord, title, uri, remoteUri, infoFileRecord);
	}

	public void visualizzaEditFileAllegato(final Record detailRecord, int nroAllegato) {
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		final String display = allegatoRecord.getAttributeAsString("displayFileName");
		final String numeroProgrAllegato = allegatoRecord.getAttributeAsString("numeroProgrAllegato");
		final String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		final Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
		final String uri = allegatoRecord.getAttributeAsString("uri");
		final String title;
		
		if ((numeroProgrAllegato != null) && (numeroProgrAllegato.length() > 0)) {
			title = "Allegato N°" + numeroProgrAllegato + " - " + display;
		} else {
			title = display;
		}
		
		visualizzaEditFile(detailRecord, title, uri, remoteUri, infoFile);
	}

	protected void visualizzaFile(final Record detailRecord, final String display, final String uri, final String remoteUri, InfoFileRecord info) {
		String progressivo = detailRecord.getAttribute("id");

		String display1 = progressivo != null ? progressivo + " - " + display : display;
		if (info != null && info.getMimetype() != null && info.getMimetype().startsWith("image") && !info.getMimetype().equals("image/tiff")) {
			new PreviewImageWindow(uri, Boolean.valueOf(remoteUri), info);
		} else {
			if (!Layout.getOpenedViewers().keySet().contains(uri)) {
				PreviewWindow lPreviewWindow = new PreviewWindow(nomeEntita, "", uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display1) {

					@Override
					public boolean isModal() {
						return false;
					}
				};
				lPreviewWindow.addCloseClickHandler(new CloseClickHandler() {

					@Override
					public void onCloseClick(CloseClickEvent event) {
						Layout.getOpenedViewers().remove(uri);
					}
				});
				Layout.getOpenedViewers().put(uri, lPreviewWindow);
			} else {
				Layout.getOpenedViewers().get(uri).bringToFront();
			}
		}
	}

	public void visualizzaEditFile(final Record detailRecord, final String display, final String uri, final String remoteUri, Object infoFile) {
		InfoFileRecord info = new InfoFileRecord(infoFile);
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
				
		FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), info.getMimetype(), 
				null,rotazioneTimbroPref,posizioneTimbroPref);
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

	private Map<String, Map<Integer, String>> lMapEmailUriAllegati = new HashMap<String, Map<Integer, String>>();

	private Map<String, RecordList> lMapEmailInfoFileAllegati = new HashMap<String, RecordList>();

	private Map<String, Record> lMapEmailDetailRecord = new HashMap<String, Record>();

	/*
	 * 02/08/2018
	 * Metodo che viene richiamato al click sull'icona degli allegati. Richiama il datasource AurigaAbilitazioniEmailDataSource 
	 * che oltre a ritornare i flag delle abilitazioni, torna la lista degli allegati e l'uri della mail.
	 * Una volta ottenuta la lista degli allegati viene richiamato il metodo "retrieveAttach" del datasource 
	 * AurigaGetDettaglioPostaElettronicaDataSource per recuperare gli uri dei file allegati. Le informazioni così ottenute vengono
	 * gestite ed elaborate poi in "manageInfoFile" come prima.
	 * 
	 */
	
	private void retrieveAllegati(Record record, final RetrieveInfoFileCallback pRetrieveInfoFileCallback) {
		final String idEmail = record.getAttribute("idEmail");
		if (lMapEmailDetailRecord.containsKey(idEmail)) {
			// Prelievo del valore dalla cache
			pRetrieveInfoFileCallback.manageInfoFile(lMapEmailDetailRecord.get(idEmail), lMapEmailInfoFileAllegati.get(idEmail));
		} else {
			getAurigaAbilitazioniEmailDataSource().call(record, new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record detailRecord) {

					if (detailRecord != null){
						
						getAurigaGetDettaglioPostaElettronicaDataSource().executecustom("retrieveAttach", detailRecord, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									RecordList listaAllegati = response.getData()[0].getAttributeAsRecordList("listaAllegati");
									lMapEmailDetailRecord.put(idEmail, response.getData()[0]);
									lMapEmailInfoFileAllegati.put(idEmail, listaAllegati);
									pRetrieveInfoFileCallback.manageInfoFile(response.getData()[0], listaAllegati);
								}
							}
						});
					}
				}
			});
		}
	}

	private void retrieveUri(Record detailRecord, final int nroAllegato, final RetrieveUriAttachCallback lRetrieveUriAttachCallback) {
		final String idEmail = detailRecord.getAttribute("idEmail");
		if (lMapEmailUriAllegati.containsKey(idEmail)) {
			String uri = lMapEmailUriAllegati.get(idEmail).get(nroAllegato);
			lRetrieveUriAttachCallback.manageUri(uri);
		} else {
			getAurigaGetDettaglioPostaElettronicaDataSource().executecustom("retrieveAttach", detailRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Map<Integer, String> lMap = new HashMap<Integer, String>();
						RecordList lRecordList = response.getData()[0].getAttributeAsRecordList("listaAllegati");
						for (int i = 0; i < lRecordList.getLength(); i++) {
							lMap.put(i, lRecordList.get(i).getAttributeAsString("uri"));
						}
						lMapEmailUriAllegati.put(idEmail, lMap);
						String uri = lMapEmailUriAllegati.get(idEmail).get(nroAllegato);
						lRetrieveUriAttachCallback.manageUri(uri);
					}
				}
			});
		}
	}

	protected void scaricaFileAllegato(Record detailRecord, final int nroAllegato) {
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		Record lInfoFileRecord = new Record();
		if (allegatoRecord.getAttributeAsObject("infoFile") != null) {
			lInfoFileRecord = new InfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));
		}
		lInfoFileRecord.setAttribute("bytes", new Long(allegatoRecord.getAttribute("bytes")));
		final String display = allegatoRecord.getAttributeAsString("nomeFile");
		final String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		final String uri = allegatoRecord.getAttributeAsString("uri");
		scaricaFile(display, uri, remoteUri, lInfoFileRecord);
	}

	protected void scaricaFile(String display, String uri, String remoteUri, Record lInfoFileRecord) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri);
		lRecord.setAttribute("infoFile", lInfoFileRecord);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	public void scaricaFileAllegatoSbustato(Record detailRecord, int nroAllegato) {
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		Record lInfoFileRecord = new Record();
		if (allegatoRecord.getAttributeAsObject("infoFile") != null) {
			lInfoFileRecord = new InfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));
		}
		lInfoFileRecord.setAttribute("bytes", new Long(allegatoRecord.getAttribute("bytes")));
		final String display = allegatoRecord.getAttributeAsString("nomeFile");
		final String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		final String uri = allegatoRecord.getAttributeAsString("uri");
		scaricaFileSbustato(display, uri, remoteUri, lInfoFileRecord);
	}

	private void scaricaFileSbustato(String display, String uri, String remoteUri, Record lInfoFileRecord) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", remoteUri);
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getAttribute("correctFileName"));
		lRecord.setAttribute("infoFile", lInfoFileRecord);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	protected void showRowContextMenu(final Record pRecord) {
		getAurigaAbilitazioniEmailDataSource().call(pRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(final Record object) {
				
				Record recordDestPref = new Record();						
				RecordList listaAzioniRapide = new RecordList();
				Record recordAzioneRapidaMessaInCarico = new Record();
				recordAzioneRapidaMessaInCarico.setAttribute("azioneRapida", AzioniRapide.METTI_IN_CARICO.getValue()); 
				listaAzioniRapide.add(recordAzioneRapidaMessaInCarico);
				Record recordAzioneRapidaMandaInApprovazione = new Record();
				recordAzioneRapidaMandaInApprovazione.setAttribute("azioneRapida", AzioniRapide.MANDA_IN_APPROVAZIONE.getValue()); 
				listaAzioniRapide.add(recordAzioneRapidaMandaInApprovazione);
				Record recordAzioneRapidaAssegnaUOCompetente = new Record();
				recordAzioneRapidaAssegnaUOCompetente.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_UO_COMPETENTE.getValue()); 
				listaAzioniRapide.add(recordAzioneRapidaAssegnaUOCompetente);
				recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);					
				
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
				lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
		
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							
								Record destinatariPreferiti = response.getData()[0];
								buildMenu(object, pRecord, destinatariPreferiti);						 
							}
						}
					}, new DSRequest());
			}
		});
	}

	protected void buildMenu(final Record abilitazioni, final Record pRecordMail, final Record destinatariPreferiti) {

		String classifica = getLayout().getFilter().getExtraParam().get("classifica");
		final String destinatariEAccount = pRecordMail.getAttributeAsString("casellaRicezione");

		final Menu altreOpMenu = new Menu();
		altreOpMenu.setOverflow(Overflow.VISIBLE);
		altreOpMenu.setShowIcons(true);
		altreOpMenu.setSelectionType(SelectionStyle.SINGLE);
		altreOpMenu.setCanDragRecordsOut(false);
		altreOpMenu.setWidth("*");
		altreOpMenu.setHeight("*");

		// Visualizza
		MenuItem visualizzaMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_visualizzaMenuItem(), "buttons/detail.png");
		visualizzaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageDetailButtonClick(getRecord(getRecordIndex(pRecordMail)));
			}
		});
		altreOpMenu.addItem(visualizzaMenuItem);

		boolean abilitaProtocolla = abilitazioni.getAttribute("abilitaProtocolla") != null && abilitazioni.getAttribute("abilitaProtocolla").equals("true");
		boolean abilitaProtocollaInteraEmail = Layout.isPrivilegioAttivo("EML/RIE/PG");
		boolean abilitaRepertoria = abilitazioni.getAttribute("abilitaRepertoria") != null && abilitazioni.getAttribute("abilitaRepertoria").equals("true"); 
		boolean abilitaRepertoriaInteraEmail = Layout.isPrivilegioAttivo("EML/RIE/R"); 
		boolean abilitaProtocollaAccessoAttiSUE = abilitazioni.getAttribute("abilitaProtocollaAccessoAttiSUE") != null && abilitazioni.getAttribute("abilitaProtocollaAccessoAttiSUE").equals("true");
		
		// Da qua gestisco repertorio, protocollazione e registrazione su richiesta accesso atti
		// Se ho solo una di queste abilitazione il tasto esegue direttamente la funzione, altrimenti viene aperto un menu di scelta
		int numeroAbilitazioni = 0;
		if (abilitaProtocolla) {
			numeroAbilitazioni ++;
		}
		if(abilitaProtocollaInteraEmail) {
			numeroAbilitazioni ++;
		}
		if (abilitaRepertoria) {
			numeroAbilitazioni ++;
			if(abilitaRepertoriaInteraEmail) {
				numeroAbilitazioni ++;
			}
		}
		if (abilitaProtocollaAccessoAttiSUE) {
			numeroAbilitazioni ++;
		}
		
		if (numeroAbilitazioni > 1) {
			
			MenuItem registraMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_registraMenuItem(), "buttons/protocollazione.png");
			Menu protocollaRegistraMenu = new Menu();
			
			if (abilitaProtocolla) {
				// Menu Protocolla
				MenuItem protocollaMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_protocollaMenuItem());
				protocollaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						manageProtocollaClick(pRecordMail, destinatariEAccount, false, false);
					}
				});
				protocollaRegistraMenu.addItem(protocollaMenuItem);
			}
			
			if(abilitaProtocollaInteraEmail) {
				// Menu Protocolla intera email
				MenuItem protocollaInteraEmailMenuItem = new MenuItem("Protocollazione intera mail");
				protocollaInteraEmailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						manageProtocollaClick(pRecordMail, destinatariEAccount, false, true);
					}
				});
				protocollaRegistraMenu.addItem(protocollaInteraEmailMenuItem);
			}
			
			if (abilitaRepertoria) {
				//Registra a Repertorio
				MenuItem repertorioMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_repertorioMenuItem());
				repertorioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						manageRegistraRepertorio(pRecordMail, destinatariEAccount, false);
					}
				});
				protocollaRegistraMenu.addItem(repertorioMenuItem);
				
				if(abilitaRepertoriaInteraEmail) {
					//Registra a Repertorio intera email
					MenuItem repertorioInteraEmailMenuItem = new MenuItem("Repertoriazione intera mail");
					repertorioInteraEmailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							manageRegistraRepertorio(pRecordMail, destinatariEAccount, true);
						}
					});
					protocollaRegistraMenu.addItem(repertorioInteraEmailMenuItem);
				}
			}
			
			if (abilitaProtocollaAccessoAttiSUE) {
				//Registra protocollo accesso atti sue
				MenuItem protocollaAccessoAttiSUEMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_protocollaAccessoAttiSUE());
				protocollaAccessoAttiSUEMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
	
						manageProtocollaClick(pRecordMail, destinatariEAccount, true, false);
					}
				});
				protocollaRegistraMenu.addItem(protocollaAccessoAttiSUEMenuItem);
			}
			
			registraMenuItem.setSubmenu(protocollaRegistraMenu);
			altreOpMenu.addItem(registraMenuItem);
			
		} else if (abilitaProtocolla) {
			
			// Protocolla
			MenuItem protocollaMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_registraMenuItem(), "buttons/protocollazione.png");
			protocollaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageProtocollaClick(pRecordMail, destinatariEAccount, false, false);
				}
			});
			altreOpMenu.addItem(protocollaMenuItem);
			
			if(abilitaProtocollaInteraEmail) {
				// Menu Protocolla intera email
				MenuItem protocollaInteraEmailMenuItem = new MenuItem("Protocollazione intera mail");
				protocollaInteraEmailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						manageProtocollaClick(pRecordMail, destinatariEAccount, false, true);
					}
				});
				altreOpMenu.addItem(protocollaInteraEmailMenuItem);
			}
		} else if (abilitaRepertoria) {
			// Registra a repertorio
			MenuItem repertorioMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_registraMenuItem(), "buttons/protocollazione.png");
			repertorioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageRegistraRepertorio(pRecordMail, destinatariEAccount, false);
				}
			});
			altreOpMenu.addItem(repertorioMenuItem);
			
			if(abilitaRepertoriaInteraEmail) {
				//Registra a Repertorio intera email
				MenuItem repertorioInteraEmailMenuItem = new MenuItem("Repertoriazione intera mail");
				repertorioInteraEmailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						manageRegistraRepertorio(pRecordMail, destinatariEAccount, true);
					}
				});
				altreOpMenu.addItem(repertorioInteraEmailMenuItem);
			}
		} else if (abilitaProtocollaAccessoAttiSUE) {
			// Protocolla accesso atti SUE
			MenuItem protocollaAccessoAttiSUEMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_protocollaAccessoAttiSUE(), "buttons/protocollazione.png");
			protocollaAccessoAttiSUEMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageProtocollaClick(pRecordMail, destinatariEAccount, true, false);
				}
			});
			altreOpMenu.addItem(protocollaAccessoAttiSUEMenuItem);
		}	

		// REGISTRA ISTANZE
		if ((abilitazioni.getAttribute("abilitaRegIstanzaAutotutela") != null && abilitazioni.getAttribute("abilitaRegIstanzaAutotutela").equals("true"))
				|| (abilitazioni.getAttribute("abilitaRegIstanzaCED") != null && abilitazioni.getAttribute("abilitaRegIstanzaCED").equals("true"))) {
			MenuItem abilRegIstanzaMenuItem = buildAbilitaRegIstanzaMenuItem(getRecord(getRecordIndex(pRecordMail)), abilitazioni);
			altreOpMenu.addItem(abilRegIstanzaMenuItem);
		}

		if (abilitazioni.getAttribute("abilitaRispondi") != null && abilitazioni.getAttribute("abilitaRispondi").equals("true")) {
			
			MenuItem rispondiGeneraleMenuItem = new MenuItem("Rispondi", "postaElettronica/risposta.png");
			Menu rispondiSubMenuItem = new Menu();
			
			MenuItem rispondiStandardMenuItem = buildRispondiMenuItem(pRecordMail,false);
			MenuItem rispondiConAllegatiMenuItem = buildRispondiMenuItem(pRecordMail,true);
			
			// Presenza di allegati nella mail
			if (pRecordMail.getAttribute("allegatiEmail") != null && !"".equals(pRecordMail.getAttribute("allegatiEmail"))
					&& (pRecordMail.getAttribute("allegatiEmail").equals("A") || pRecordMail.getAttribute("allegatiEmail").equals("AF")) ) {
				
				rispondiSubMenuItem.addItem(rispondiStandardMenuItem);
				rispondiSubMenuItem.addItem(rispondiConAllegatiMenuItem);
				rispondiGeneraleMenuItem.setSubmenu(rispondiSubMenuItem);
				
				altreOpMenu.addItem(rispondiGeneraleMenuItem);
			} else {
				rispondiStandardMenuItem.setTitle(I18NUtil.getMessages().posta_elettronica_list_rispondiMenuItem());
				rispondiStandardMenuItem.setIcon("postaElettronica/risposta.png");
				altreOpMenu.addItem(rispondiStandardMenuItem);
			}
		}

		if (abilitazioni.getAttribute("abilitaRispondiATutti") != null && abilitazioni.getAttribute("abilitaRispondiATutti").equals("true")) {
			
			MenuItem rispondiATuttiGeneraleMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_rispondiATuttiMenuItem(), "postaElettronica/risposta.png");
			Menu rispondiSubMenuItem = new Menu();
			
			MenuItem rispondiATuttiStandardMenuItem = buildRispondiATuttiMenuItem(pRecordMail,false);
			MenuItem rispondiATuttiConAllegatiMenuItem = buildRispondiATuttiMenuItem(pRecordMail,true);
			
			// Presenza di allegati nella mail
			if (pRecordMail.getAttribute("allegatiEmail") != null && !"".equals(pRecordMail.getAttribute("allegatiEmail"))
					&& (pRecordMail.getAttribute("allegatiEmail").equals("A") || pRecordMail.getAttribute("allegatiEmail").equals("AF")) ) {
				
				rispondiSubMenuItem.addItem(rispondiATuttiStandardMenuItem);
				rispondiSubMenuItem.addItem(rispondiATuttiConAllegatiMenuItem);
				rispondiATuttiGeneraleMenuItem.setSubmenu(rispondiSubMenuItem);
				
				altreOpMenu.addItem(rispondiATuttiGeneraleMenuItem);
			} else {
				rispondiATuttiStandardMenuItem.setTitle(I18NUtil.getMessages().posta_elettronica_list_rispondiATuttiMenuItem());
				rispondiATuttiStandardMenuItem.setIcon("postaElettronica/risposta.png");
				altreOpMenu.addItem(rispondiATuttiStandardMenuItem);
			}
		}

		if ((abilitazioni.getAttribute("abilitaInoltraEmail") != null && abilitazioni.getAttribute("abilitaInoltraEmail").equals("true"))
				|| (abilitazioni.getAttribute("abilitaInoltraContenuti") != null && abilitazioni.getAttribute("abilitaInoltraContenuti").equals("true"))) {
			MenuItem inoltraMenuItem = buildInoltraMenuItem(abilitazioni, pRecordMail);
			altreOpMenu.addItem(inoltraMenuItem);
		}

		if (abilitazioni.getAttribute("abilitaNotifInteropConferma") != null && abilitazioni.getAttribute("abilitaNotifInteropConferma").equals("true")) {
			MenuItem invioConfermaMenuItem = creaInvioNotificaInteropMenuItem(pRecordMail, "conferma");
			altreOpMenu.addItem(invioConfermaMenuItem);
		}

		if (abilitazioni.getAttribute("abilitaNotifInteropEccezione") != null && abilitazioni.getAttribute("abilitaNotifInteropEccezione").equals("true")) {
			MenuItem invioEccezioneMenuItem = creaInvioNotificaInteropMenuItem(pRecordMail, "eccezione");
			altreOpMenu.addItem(invioEccezioneMenuItem);
		}

		if (abilitazioni.getAttribute("abilitaNotifInteropAggiornamento") != null
				&& abilitazioni.getAttribute("abilitaNotifInteropAggiornamento").equals("true")) {
			MenuItem invioAggiornamentoMenuItem = creaInvioNotificaInteropMenuItem(pRecordMail, "aggiornamento");
			altreOpMenu.addItem(invioAggiornamentoMenuItem);
		}

		if (abilitazioni.getAttribute("abilitaNotifInteropAnnullamento") != null
				&& abilitazioni.getAttribute("abilitaNotifInteropAnnullamento").equals("true")) {
			MenuItem invioAnnullamentoMenuItem = creaInvioNotificaInteropMenuItem(pRecordMail, "annullamento");
			altreOpMenu.addItem(invioAnnullamentoMenuItem);
		}

		if (classifica != null && classifica.startsWith("standard.invio")) {
			MenuItem ricevutePostaInUscitaMenuItem = creaRicevutePostaInUscitaMenuItem(pRecordMail);
			altreOpMenu.addItem(ricevutePostaInUscitaMenuItem);
		}

		if (abilitazioni.getAttribute("abilitaArchivia") != null && abilitazioni.getAttribute("abilitaArchivia").equals("true")) {
			MenuItem archiviaMenuItem = creaArchiviaMenuItem(pRecordMail);
			altreOpMenu.addItem(archiviaMenuItem);
		}

		if (abilitazioni.getAttribute("abilitaRiapri") != null && abilitazioni.getAttribute("abilitaRiapri").equals("true")) {
			MenuItem annullaArchiaviazioneMenuItem = riapriEmailMenuItem(pRecordMail);
			altreOpMenu.addItem(annullaArchiaviazioneMenuItem);
		}

		String flgStatoProt = (String) pRecordMail.getAttribute("flgStatoProt");
		if (flgStatoProt != null && !"".equals(flgStatoProt)) {
			MenuItem visualizzaProtAssociatiMenuItem = creaVisualizzaProtAssociatiMenuItem(pRecordMail);
			altreOpMenu.addItem(visualizzaProtAssociatiMenuItem);
		}

		if (abilitazioni.getAttribute("abilitaAssegna") != null && abilitazioni.getAttribute("abilitaAssegna").equals("true")) {
			MenuItem assegnaMenuItem = creaAssegnaMenuItem(pRecordMail, destinatariPreferiti);
			altreOpMenu.addItem(assegnaMenuItem);
		}

		if (abilitazioni.getAttribute("abilitaAnnullamentoInvio") != null && abilitazioni.getAttribute("abilitaAnnullamentoInvio").equals("true")) {
			MenuItem restituisciAnnullaUOMenuItem = annullaSceltaUOCompetente(pRecordMail);
			altreOpMenu.addItem(restituisciAnnullaUOMenuItem);
		}

		if ((abilitazioni.getAttribute("abilitaScaricaEmail") != null && abilitazioni.getAttribute("abilitaScaricaEmail").equals("true"))
				|| (abilitazioni.getAttribute("abilitaScaricaEmailSenzaBustaTrasporto") != null
						&& abilitazioni.getAttribute("abilitaScaricaEmailSenzaBustaTrasporto").equals("true"))) {
			MenuItem scaricaMenuItem = buildScaricaMenuItem(abilitazioni, pRecordMail);
			altreOpMenu.addItem(scaricaMenuItem);
		}

		String allegatiEmail = pRecordMail.getAttribute("allegatiEmail");

		if (allegatiEmail != null && !"".equals(allegatiEmail) && (allegatiEmail.equals("A") || allegatiEmail.equals("AF"))) {
			MenuItem scaricaZipAllegati = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_scaricaZipAllegati(), "buttons/download_zip.png");
			scaricaZipAllegati.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					scaricaZipAllegati(pRecordMail);
				}
			});

			altreOpMenu.addItem(scaricaZipAllegati);

		}
		if (abilitazioni.getAttribute("abilitaAzioneDaFare") != null && abilitazioni.getAttribute("abilitaAzioneDaFare").equals("true")) {
			MenuItem azioneDaFareMenuItem = buildAzioneDaFareMenuItem(pRecordMail);
			altreOpMenu.addItem(azioneDaFareMenuItem);
		}
		if (abilitazioni.getAttribute("abilitaAssociaAInvio") != null && abilitazioni.getAttribute("abilitaAssociaAInvio").equals("true")) {
			MenuItem associaAInvioMenuItem = buildAssociaAInvioMenuItem(pRecordMail);
			altreOpMenu.addItem(associaAInvioMenuItem);
		}

		MenuItem azioneStampaPDF = buildStampaEmailMenuItem(pRecordMail);
		altreOpMenu.addItem(azioneStampaPDF);

		// ABILITAZIONE PRESA IN CARICO
		if (abilitazioni.getAttribute("abilitaPresaInCarico") != null && abilitazioni.getAttribute("abilitaPresaInCarico").equals("true")) {
			MenuItem azionePrendiInCarico = buildPresaInCaricoMenuItem(pRecordMail);
			altreOpMenu.addItem(azionePrendiInCarico);
		}

		// ABILITAZIONE MESSA IN CARICO & MANDA IN APPROVAZIONE
		if (abilitazioni.getAttribute("abilitaMessaInCarico") != null && abilitazioni.getAttribute("abilitaMessaInCarico").equals("true")) {

			// MESSA IN CARICO
			MenuItem azioneMettiInCarico = buildMessaInCaricoMenuItem(pRecordMail, destinatariPreferiti);
			altreOpMenu.addItem(azioneMettiInCarico);

			// MANDA IN APPROVAZIONE
			MenuItem azioneMettiInApprovazione = buildMessaInApprovazioneMenuItem(pRecordMail, destinatariPreferiti);
			altreOpMenu.addItem(azioneMettiInApprovazione);
		}

		// ABILITAZIONE RILASCIO
		if (abilitazioni.getAttribute("abilitaRilascio") != null && abilitazioni.getAttribute("abilitaRilascio").equals("true")) {
			MenuItem azioneRilascio = buildRilascioMenuItem(pRecordMail);
			altreOpMenu.addItem(azioneRilascio);
		}

		// Invia
		MenuItem reiviaMenu = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_reinviaMenu(), "postaElettronica/inoltro.png");
		reiviaMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				manageReinviaClick(pRecordMail);
			}
		});
		Menu creaReiviaMenu = new Menu();
		int n = 0;

		if (abilitazioni.getAttribute("abilitaInvia") != null && abilitazioni.getAttribute("abilitaInvia").equals("true") && !isBozza(pRecordMail)) {
			MenuItem reiviaMenuItem = new MenuItem("Re-Invia");
			reiviaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					manageReinviaClick(pRecordMail);
				}
			});

			reiviaMenu.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					manageReinviaClick(pRecordMail);
				}
			});
			creaReiviaMenu.addItem(reiviaMenuItem);
			n += 1;
		}

		if (abilitazioni.getAttribute("abilitaInvioCopia") != null && abilitazioni.getAttribute("abilitaInvioCopia").equals("true") && !isBozza(pRecordMail)) {
			// Nuovo invio come copia
			MenuItem nuovoInvioCopiaMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_nuovoInvioCopiaMenuItem());
			nuovoInvioCopiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					Record record = new Record();
					final RecordList listaEmail = new RecordList();
					pRecordMail.setAttribute("allegaEmlSbustato", "false");
					pRecordMail.setAttribute("idEmail", pRecordMail.getAttributeAsString("idEmail"));
					listaEmail.add(pRecordMail);
					record.setAttribute("listaRecord", listaEmail);

					final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
					lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							manageNuovoInvioCopiaClick(pRecordMail, "Nuovo invio e-mail", "NIC");
						}
					});
				}
			});
			creaReiviaMenu.addItem(nuovoInvioCopiaMenuItem);
			n += 1;
		}
		if (n > 0) {
			reiviaMenu.setSubmenu(creaReiviaMenu);
			altreOpMenu.addItem(reiviaMenu);
		}
		
		if (abilitazioni.getAttribute("abilitaStampaFile") != null && abilitazioni.getAttribute("abilitaStampaFile").equalsIgnoreCase("true")) {
			
			MenuItem stampaTuttiFileMenuItem = new MenuItem("Stampa allegati", "postaElettronica/print_file.png");
			stampaTuttiFileMenuItem.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					manageStampaAllegati(pRecordMail);
				}
			});
			altreOpMenu.addItem(stampaTuttiFileMenuItem);
		}

		if (abilitazioni.getAttribute("abilitaInvia") != null && abilitazioni.getAttribute("abilitaInvia").equals("true") && isBozza(pRecordMail)) {
			// Per la chiusura della bozza devo copiare degli attributi nel record dalle abilitazioni
			pRecordMail.setAttribute("emailPredecessoreIdEmail", abilitazioni.getAttribute("emailPredecessoreIdEmail"));
			pRecordMail.setAttribute("idEmailInoltrate", abilitazioni.getAttributeAsRecordArray("idEmailInoltrate"));
			pRecordMail.setAttribute("flgMailPredecessoreStatoLavAperta", abilitazioni.getAttribute("flgMailPredecessoreStatoLavAperta"));
			
			MenuItem invioBozzaItem = new MenuItem("Invia", "buttons/send.png");
			invioBozzaItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					if (isMessaggioAskChiusuraMailProvenienzaToShow(abilitazioni)) {
						// Apro la popup per chiedere se chiudere o no le mail di provenienza
						// Verifico se ho azioni da fare sulle mail da chiudere
						Map<String, String> mappaParametriPopupChiusuraMailProvenienza = generaParametriPopupChiusuraMailProvenienza(abilitazioni);
						// Genero il messaggio da mostrare nella popup di chiudura
														
						InvioMailPopup invioMailPopup = new InvioMailPopup(mappaParametriPopupChiusuraMailProvenienza) {

							@Override
							public void onClickChiudiMailECompletaAzioneButton(Record formRecord) {
								//Il terzo parametro è true perchè in questo caso dobbiamo chiudere la mail
								manageInvioBozzaClick(pRecordMail, true, "COMPL");
								markForDestroy();
							}

							@Override
							public void onClickChiudiMailEAnnullaAzioneButton(Record formRecord) {
								//Il terzo parametro è false perchè in questo caso NON dobbiamo chiudere la mail
								manageInvioBozzaClick(pRecordMail, true, "ANN");
								markForDestroy();
							}

							@Override
							public void onClickChiudiMailButton(Record formRecord) {
								//Il terzo parametro è true perchè in questo caso dobbiamo chiudere la mail
								manageInvioBozzaClick(pRecordMail, true, null);
								markForDestroy();
							}

							@Override
							public void onClickNonChiudereMailButton(Record formRecord) {
								//Il terzo parametro è true perchè in questo caso dobbiamo chiudere la mail
								manageInvioBozzaClick(pRecordMail, false, null);
								markForDestroy();
							}
							
						};
						invioMailPopup.show();
						
					} else {
						manageInvioBozzaClick(pRecordMail, false, null);
					}
				}
			});
			altreOpMenu.addItem(invioBozzaItem);
		}

		altreOpMenu.showContextMenu();
	}

	private MenuItem buildRispondiATuttiMenuItem(final Record pRecordMail,final Boolean richiediAllegati) {
		
		String titleRispondi = richiediAllegati != null && richiediAllegati ? 
				I18NUtil.getMessages().posta_elettronica_list_rispondiATuttiConAllegatiMenuItem() :	
				I18NUtil.getMessages().posta_elettronica_list_rispondiATuttiStandardMenuItem();
		
		MenuItem rispondiATuttiMenuItem = new MenuItem(titleRispondi, "");
		rispondiATuttiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Record record = new Record();
				final RecordList listaEmail = new RecordList();
				listaEmail.add(pRecordMail);
				record.setAttribute("listaRecord", listaEmail);
				final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
				lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						manageRispondiATuttiClick(pRecordMail,richiediAllegati);
					}
				});
			}
		});
		return rispondiATuttiMenuItem;
	}

	private MenuItem buildRispondiMenuItem(final Record pRecordMail,final Boolean richiediAllegati) {
		
		String titleRispondi = richiediAllegati != null && richiediAllegati ? 
			I18NUtil.getMessages().posta_elettronica_list_rispondiConAllegatiMenuItem() :	
			I18NUtil.getMessages().posta_elettronica_list_rispondiStandardMenuItem();
		
		MenuItem rispondiMenuItem = new MenuItem(titleRispondi, "");
		rispondiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				final Record record = new Record();
				final RecordList listaEmail = new RecordList();
				listaEmail.add(pRecordMail);

				checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {

						Record esitoCheck = object.getAttributeAsRecord("esito");

						boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
						boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

						if (isLock) {
							if (isForzaLock) {
								String messaggio = esitoCheck.getAttributeAsString("errorMessage");
								Layout.showConfirmDialogWithWarning("Attenzione", messaggio, null, null, new BooleanCallback() {

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
														record.setAttribute("listaRecord", listaEmail);
														final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>(
																"AurigaInvioMailDatasource");
														lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

															@Override
															public void execute(Record object) {
																manageRispondiClick(pRecordMail,richiediAllegati);
															}
														});
													}
												}
											});
										}
									}
								});
							}
							// No force lock
							else {
								Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
							}
						} else {
							// Mail noLock
							record.setAttribute("listaRecord", listaEmail);
							final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
							lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

								@Override
								public void execute(Record object) {
									manageRispondiClick(pRecordMail,richiediAllegati);
								}
							});
						}
					}
				});
			}
		});
		return rispondiMenuItem;
	}

	protected void manageReinviaClick(final Record pRecordMail) {
		
		final RecordList listaEmail = new RecordList();
		listaEmail.add(pRecordMail);
		final Record record = new Record();
		record.setAttribute("listaRecord", listaEmail);
		checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				Record esitoCheck = object.getAttributeAsRecord("esito");
				boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
				boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");
				if (isLock) {
					if (isForzaLock) {
						String messaggio = esitoCheck.getAttributeAsString("errorMessage");
						Layout.showConfirmDialogWithWarning("Attenzione", messaggio, null, null, new BooleanCallback() {

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
											}
											// mail sbloccata
											else {
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
																layout.doSearch();
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
					}
					// No force lock
					else {
						Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
					}
				}
				// Mail noLock
				else {
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
											layout.doSearch();
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

	private void manageInvioBozzaClick(final Record pRecordMail, final boolean chiudiMailDiProvenienza, final String gestisciAzioniDaFare) {
		final RecordList listaEmail = new RecordList();
		listaEmail.add(pRecordMail);
		final Record record = new Record();
		record.setAttribute("listaRecord", listaEmail);
		checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {

				Record esitoCheck = object.getAttributeAsRecord("esito");
				boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
				boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

				if (isLock) {
					if (isForzaLock) {
						String messaggio = esitoCheck.getAttributeAsString("errorMessage");
						Layout.showConfirmDialogWithWarning("Attenzione", messaggio, null, null, new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {

										@Override
										public void execute(Record data) {
											Map errorMessages = data.getAttributeAsMap("errorMessages");
											if (errorMessages != null && errorMessages.size() > 0) {
												String error = data.getAttribute("idMail") + ": " + errorMessages.get(data.getAttribute("oggetto"));
												Layout.addMessage(new MessageBean(error, "", MessageType.ERROR));
											}
											// mail sbloccata
											else {
												lock(listaEmail, new ServiceCallback<Record>() {

													@Override
													public void execute(Record data) {

														GWTRestService<Record, Record> lGwtRestServiceAurigaInvioMailDatasource = new GWTRestService<Record, Record>(
																"AurigaInvioMailDatasource");
														Record recordInvioBozzaMail = getInvioBozzaMailBeanToRecord(pRecordMail);
														lGwtRestServiceAurigaInvioMailDatasource.extraparam.put("posizione", "L");
														lGwtRestServiceAurigaInvioMailDatasource.executecustom("invioBozzaMail", recordInvioBozzaMail,
																new DSCallback() {

																	@Override
																	public void execute(DSResponse response, Object rawData, DSRequest request) {
																		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
																			Layout.addMessage(new MessageBean(I18NUtil.getMessages().invionotificainterop_esitoInvio_OK_value(),
																					"", MessageType.INFO));
																		}
																		chiudiMailDiProvenienza(pRecordMail, chiudiMailDiProvenienza, gestisciAzioniDaFare);
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
					}
					// No force lock
					else {
						Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
						layout.doSearch();
					}
				}
				// Mail noLock
				else {
					lock(listaEmail, new ServiceCallback<Record>() {

						@Override
						public void execute(Record data) {

							GWTRestService<Record, Record> lGwtRestServiceAurigaInvioMailDatasource = new GWTRestService<Record, Record>(
									"AurigaInvioMailDatasource");
							Record recordInvioBozzaMail = getInvioBozzaMailBeanToRecord(pRecordMail);
							lGwtRestServiceAurigaInvioMailDatasource.extraparam.put("posizione", "L");
							lGwtRestServiceAurigaInvioMailDatasource.executecustom("invioBozzaMail", recordInvioBozzaMail, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Layout.addMessage(new MessageBean(I18NUtil.getMessages().invionotificainterop_esitoInvio_OK_value(),
												"", MessageType.INFO));
									}
									sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {

										@Override
										public void execute(Record data) {
											chiudiMailDiProvenienza(pRecordMail, chiudiMailDiProvenienza, gestisciAzioniDaFare);
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

	protected void manageProtocollaClick(final Record recordList, final String destinatariEAccount, final boolean richiestaAcessoAttiSue,
			final boolean protocollaInteraEmail) {
		
		if(richiestaAcessoAttiSue) {
			Record mittente = new Record();
			mittente.setAttribute("emailMittente", recordList.getAttribute("accountMittente"));
			mittente.setAttribute("tipoMittente", "PF");
			final RecordList listaMittenti = new RecordList();
			listaMittenti.add(mittente);
			recordList.setAttribute("listaEsibenti", listaMittenti);
		}
			
		getAurigaGetDettaglioPostaElettronicaDataSource().performCustomOperation("get", recordList, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					// recupero i dettagli relativi alla mail per ottenere la lista dei file nella sezione Appunti e note.
					final Record lRecordLoad = response.getData()[0];
					RecordList itemLavorazione = lRecordLoad.getAttributeAsRecordList("listaItemInLavorazione");
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
									lRecordLoad.setAttribute("fileDaAppunti",fileDaAppunti); //Aggiunta della lista dei file recuperati alla protocollazione.
									if (richiestaAcessoAttiSue) {
										lGwtRestServiceProtocolla.extraparam.put("isRichiestaAccessoAtti", "true");
									}
									if(protocollaInteraEmail) {
										lGwtRestServiceProtocolla.extraparam.put("isProtocollaInteraEmail", "true");
									}
									lGwtRestServiceProtocolla.call(lRecordLoad, new ServiceCallback<Record>() {

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
//														manageModificaDatiButtonClick(object.getAttribute("idUd"), destinatariEAccount, recordList);
														if (richiestaAcessoAttiSue){
															object.setAttribute("azioneDaFareBean", recordList.getAttributeAsObject("azioneDaFareBean"));
														}
														manageProtocollaPostaElettronicaCallback(object, destinatariEAccount, false, null, richiestaAcessoAttiSue, true);
													}

													@Override
													public void onClickAnnullaChiudiMailButton(Record objectPopup, DSCallback callback) {
														markForDestroy();
														actionArchiviaMail(lRecordLoad);
													}
												};
												lWarningMsgDoppiaRegPopup.show();	
												
											} else if (object.getAttribute("idUd") != null && !object.getAttribute("idUd").trim().equals("")) {
												SC.ask(I18NUtil.getMessages().postaElettronica__list_copiaMailGiaProtocollataField(), new BooleanCallback() {
													
													@Override
													public void execute(Boolean value) {
														if (value) {
															manageModificaDatiButtonClick(object.getAttribute("idUd"), destinatariEAccount, recordList);
														} else {
															SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
																
																@Override
																public void execute(Boolean value) {
																	if (value) {
																		actionArchiviaMail(lRecordLoad);
																	}
																}
															});
														}
													}
												});
											} else {
												if (richiestaAcessoAttiSue){
													object.setAttribute("azioneDaFareBean", recordList.getAttributeAsObject("azioneDaFareBean"));
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
									}
									lGwtRestServiceProtocolla.call(lRecordLoad, new ServiceCallback<Record>() {

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
//														manageModificaDatiButtonClick(object.getAttribute("idUd"), destinatariEAccount, recordList);
														if (richiestaAcessoAttiSue){
															object.setAttribute("azioneDaFareBean", recordList.getAttributeAsObject("azioneDaFareBean"));
														}
														manageProtocollaPostaElettronicaCallback(object, destinatariEAccount, false, null, richiestaAcessoAttiSue, true);
													}

													@Override
													public void onClickAnnullaChiudiMailButton(Record objectPopup, DSCallback callback) {
														markForDestroy();
														actionArchiviaMail(lRecordLoad);
													}
												};
												lWarningMsgDoppiaRegPopup.show();	
											} else if (object.getAttribute("idUd") != null && !object.getAttribute("idUd").trim().equals("")) {
												SC.ask(I18NUtil.getMessages().postaElettronica__list_copiaMailGiaProtocollataField(), new BooleanCallback() {
													
													@Override
													public void execute(Boolean value) {
														if (value) {
															manageModificaDatiButtonClick(object.getAttribute("idUd"), destinatariEAccount, recordList);
														} else {
															SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
																
																@Override
																public void execute(Boolean value) {
																	if (value) {
																		actionArchiviaMail(lRecordLoad);
																	}
																}
															});
														}
													}
												});
											} else {
												if (richiestaAcessoAttiSue){
													object.setAttribute("azioneDaFareBean", recordList.getAttributeAsObject("azioneDaFareBean"));
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
						}
						lGwtRestServiceProtocolla.call(lRecordLoad, new ServiceCallback<Record>() {

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
//											manageModificaDatiButtonClick(object.getAttribute("idUd"), destinatariEAccount, recordList);
											if (richiestaAcessoAttiSue){
												object.setAttribute("azioneDaFareBean", recordList.getAttributeAsObject("azioneDaFareBean"));
											}
											manageProtocollaPostaElettronicaCallback(object, destinatariEAccount, false, null, richiestaAcessoAttiSue, true);
										}

										@Override
										public void onClickAnnullaChiudiMailButton(Record objectPopup, DSCallback callback) {
											markForDestroy();
											actionArchiviaMail(lRecordLoad);
										}
									};
									lWarningMsgDoppiaRegPopup.show();	
								} else if (object.getAttribute("idUd") != null && !object.getAttribute("idUd").trim().equals("")) {
									SC.ask(I18NUtil.getMessages().postaElettronica__list_copiaMailGiaProtocollataField(), new BooleanCallback() {
										
										@Override
										public void execute(Boolean value) {
											if (value) {
												manageModificaDatiButtonClick(object.getAttribute("idUd"), destinatariEAccount, recordList);
											} else {
												SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
													
													@Override
													public void execute(Boolean value) {
														if (value) {
															actionArchiviaMail(lRecordLoad);
														}
													}
												});
											}
										}
									});
								} else {
									if (richiestaAcessoAttiSue){
										object.setAttribute("azioneDaFareBean", recordList.getAttributeAsObject("azioneDaFareBean"));
									}
									manageProtocollaPostaElettronicaCallback(object, destinatariEAccount, false, null, richiestaAcessoAttiSue, true);
								}
							}
						});
					}
				}
			}
		}, new DSRequest());
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
		String title = "";
		CustomLayout externalLayout = null;
		if(isThereIdUdMail) {
			title = "protocollazione_entrata";
			externalLayout = layout;
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
			externalLayout = layout;
		}
		EditaProtocolloWindowFromMail lEditaProtocolloWindowFromMail = new EditaProtocolloWindowFromMail(title, lRecord, flgTipoProv, isThereIdUdMail, recordMail, null, externalLayout) {

			@Override
			public void manageAfterCloseWindow() {
				if (record != null && record.getAttributeAsBoolean("protocolloAccessoAttiSueDaEmail")) {
					layout.doSearch();
				} else {
					layout.doSearch();
				}
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
		layout.getDetail().markForRedraw();
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
	
	private void manageLoadDetailIdUdMail ( String idUd, final DSCallback callback) {
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", idUd);
			getProtocolloDataSource().getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(final DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						callback.execute(response, null, new DSRequest());
					}
				}
			});
	}

	protected void manageRispondiClick(Record pRecord,Boolean richiediAllegati) {
		pRecord.setAttribute("richiediAllegati", richiediAllegati);
		new RispostaMailWindow("risposta", true, null, pRecord, layout, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				layout.doSearch();
			}
		});
	}

	protected void manageRispondiATuttiClick(final Record pRecord,Boolean richiediAllegati) {
		pRecord.setAttribute("richiediAllegati", richiediAllegati);
		new RispostaMailWindow("risposta", false, pRecord.getAttribute("casellaRicezione"), 
			pRecord, layout, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				layout.doSearch();
			}
		});
	}

	protected void manageInoltraClick(Record pRecord, boolean allegaMailOrig) {
		new InoltroMailWindow(allegaMailOrig ? "inoltroAllegaMailOrig" : "inoltro", pRecord, layout, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				layout.doSearch();
			}
		});
	}
	
	/**
	 * @author DANCRIST Metodo relativo alla stampa di tutti gli allegati della mail
	 */
	protected void manageStampaAllegati(final Record record) {
	
		final String idEmail = record.getAttribute("idEmail");
		Record recordToLoad = new Record();
		recordToLoad.setAttribute("idEmail", idEmail);
		recordToLoad.setAttribute("uri", record.getAttribute("uriEmail"));
		getAurigaGetDettaglioPostaElettronicaDataSource().performCustomOperation("retrieveAttach", recordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					
					final Record detailRecord = response.getData()[0];
					RecordList listaAllegati = detailRecord.getAttributeAsRecordList("listaAllegati");
					
					if (listaAllegati != null && listaAllegati.getLength() > 0 && !listaAllegati.isEmpty()) {
					
						Integer countShowPreview = 0;
						Integer countNotShowPreview = 0;
						final RecordList listaAllegatiDaStampare = new RecordList();
						final Set<String> setFileNonStampabili = new HashSet<String>();
		
						for (int i = 0; i < listaAllegati.getLength(); i++) {
							Record item = listaAllegati.get(i);
							if (item != null) {
								if (item.getAttributeAsString("uri") != null && !item.getAttributeAsString("uri").equals("")) {
									InfoFileRecord lInfoFileRecord = new InfoFileRecord(item.getAttributeAsJavaScriptObject("infoFile"));
									if (!PreviewWindow.isToShowEml(lInfoFileRecord, item.getAttributeAsString("displayFileName"))
											&& PreviewWindow.canBePreviewed(lInfoFileRecord)) {
										countShowPreview += 1;
										listaAllegatiDaStampare.add(item);
									} else {
										countNotShowPreview += 1;
										setFileNonStampabili.add(item.getAttributeAsString("displayFileName"));
									}
								}
							}
						}
						
						// Il numero degli allegati non stampabili è uguale alla dimensione della lista degli allegati
						if (countNotShowPreview == listaAllegati.getLength()) {
							Layout.addMessage(new MessageBean("Nessun allegato è in formato stampabile", "", MessageType.WARNING));
						}
						// Ci sono sia file stampabili che non
						else if (countNotShowPreview > 0 && countShowPreview > 0) {
							String value = "";
							for (String item : setFileNonStampabili) {
								value += value.concat(item).concat(";");
							}
							Layout.showConfirmDialogWithWarning("Attenzione", "Alcuni degli allegati da stampare: " + value + " hanno formato non stampabile, Procedi ?", null, null, new BooleanCallback() {

								@Override
								public void execute(Boolean value) {
									if (value) {
										stampaFileAllegati(record.getAttribute("uriEmail"), listaAllegatiDaStampare, setFileNonStampabili);
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
							stampaFileAllegati(record.getAttribute("uriEmail"),listaAllegatiDaStampare, setFileNonStampabili);
						}
						// Non ci sono file
						else if (countNotShowPreview == 0 && countShowPreview == 0) {
							Layout.addMessage(new MessageBean("Nessun allegato è in formato stampabile", "", MessageType.WARNING));
						}

					} else {
						Layout.addMessage(new MessageBean("Non sono presenti allegati", "", MessageType.WARNING));
					}
				}
			}
		}, new DSRequest());
	}
	
	private void stampaFileAllegati(String uriEmail, RecordList recordList, final Set<String> setFilesNonStampabili) {

//		Record lRecord = new Record();
//		lRecord.setAttribute("uri", uriEmail);
//		
//		RecordList listaAllegati = new RecordList();
//		for(int i=0; i < recordList.getLength(); i++){
//			Record item = recordList.get(i);
//			Record record = new Record();
//			record.setAttribute("nomeFile", item.getAttributeAsString("nomeFileAllegato"));
//			record.setAttribute("displayFileName", item.getAttributeAsString("displayFileNameAllegato"));
//			record.setAttribute("infoFile", item.getAttributeAsObject("infoFile"));
//			InfoFileRecord infoFileRecord =  new InfoFileRecord(item.getAttributeAsObject("infoFile"));
//			record.setAttribute("bytes",  infoFileRecord.getAttribute("bytes"));
//			listaAllegati.add(record);
//		}
//		
//		lRecord.setAttribute("listaAllegati", listaAllegati);
//		
//		getAurigaGetDettaglioPostaElettronicaDataSource().executecustom("retrieveAttach", lRecord, new DSCallback() {
//
//			@Override
//			public void execute(DSResponse response, Object rawData, DSRequest request) {
//				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
//
					Record recordToPrint = new Record();
					RecordList recordListToPrint = new RecordList();
//					RecordList recordList = response.getData()[0].getAttributeAsRecordList("listaAllegati");
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
//				}
//			}
//		});
	}

	protected void manageNuovoInvioCopiaClick(Record pRecord, final String title, final String codice) {

		getAurigaGetDettaglioPostaElettronicaDataSource().performCustomOperation("get", pRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];

					new NuovoInvioMailWindow("InvioNuovoMessaggioCopia", lRecord, title, codice, layout, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {

							layout.doSearch();
						}
					});
				}
			}
		}, new DSRequest());
	}

	// Voce menu' contestuale : Invia notifica
	protected MenuItem creaInvioNotificaInteropMenuItem(final Record pRecordMail, final String tipoNotifica) {
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

				checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {

						Record esitoCheck = object.getAttributeAsRecord("esito");

						boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
						boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

						if (isLock) {
							if (isForzaLock) {
								String messaggio = esitoCheck.getAttributeAsString("errorMessage");

								Layout.showConfirmDialogWithWarning("Attenzione", messaggio, null, null, new BooleanCallback() {

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
													}
													// mail sbloccata
													else {
														manageInvioNotificaInteropClick(pRecordMail, tipoNotifica);
													}
												}
											});
										}
									}
								});
							}
							// No force lock
							else {
								Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
							}
						}
						// Mail noLock
						else {
							manageInvioNotificaInteropClick(pRecordMail, tipoNotifica);
						}
					}
				});

			}
		});
		return invioNotificaInteropMenuItem;
	}

	protected void manageInvioNotificaInteropClick(Record pRecord, String tipoNotifica) {
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

				layout.doSearch();
			}
		});
		lInvioNotificaInteropWindow.show();
	}

	// Voce menu' contestuale : Ricevute PEC/notifiche collegate
	protected MenuItem creaRicevutePostaInUscitaMenuItem(final Record pRecordMail) {
		// Ricevute PEC/notifiche collegate
		MenuItem ricevutePostaInUscitaMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_ricevutePostaInUscitaMenuItem(),
				"mail/mail-reply2.png");
		ricevutePostaInUscitaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageRicevutePostaInUscitaClick(pRecordMail);
			}
		});
		return ricevutePostaInUscitaMenuItem;
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

	// Voce menu' contestuale : Chiudi email
	protected MenuItem creaArchiviaMenuItem(final Record pRecordMail) {
		MenuItem archiviaMenuItem = new MenuItem(AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL"), "archivio/archiviazione.png");
		archiviaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				actionArchiviaMail(pRecordMail);
			}
		});
		return archiviaMenuItem;
	}
	
	public void actionArchiviaMail (final Record pRecordMail) {
		final RecordList records = new RecordList();
		records.add(pRecordMail);

		checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {

				Record esitoCheck = object.getAttributeAsRecord("esito");

				boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
				boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

				if (isLock) {
					if (isForzaLock) {
						String messaggio = esitoCheck.getAttributeAsString("errorMessage");

						Layout.showConfirmDialogWithWarning("Attenzione", messaggio, null, null, new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {

									sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {

										@Override
										public void execute(Record data) {

											// Record esitoSblocco = object.getAttributeAsRecord("esito");
											Map mapErrorMessages = data.getAttributeAsMap("errorMessages");

											if (mapErrorMessages != null && mapErrorMessages.size() > 0) {
												if (data.getAttribute("id") == null || data.getAttribute("id").equalsIgnoreCase("")) {
													String id = mapErrorMessages.keySet().toString().replace("[", "").replace("]", "");
													String value = "Errore: " + mapErrorMessages.get(id).toString();

													Layout.addMessage(new MessageBean(value, "", MessageType.ERROR));
												} else {
													Layout.addMessage(new MessageBean(mapErrorMessages.get(data.getAttribute("id")).toString(), "",
															MessageType.ERROR));
												}
											}
											// mail sbloccata
											else
												archiviaMail(pRecordMail);
										}
									});
								}
							}
						});
					}
					// No force lock
					else {
						Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
					}
				}
				// Mail noLock
				else
					archiviaMail(pRecordMail);
			}
		});
	}

	// Voce menu' contestuale : Riapertura mail
	protected MenuItem riapriEmailMenuItem(final Record pRecordMail) {
		MenuItem archiviaMenuItem = new MenuItem(AurigaLayout.getParametroDB("LABEL_RIAPERTURA_EMAIL"), "archivio/annullaArchiviazione.png");
		archiviaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				final RecordList records = new RecordList();
				records.add(pRecordMail);

				checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {

						Record esitoCheck = object.getAttributeAsRecord("esito");

						boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
						boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

						if (isLock) {
							if (isForzaLock) {
								String messaggio = esitoCheck.getAttributeAsString("errorMessage");

								Layout.showConfirmDialogWithWarning("Attenzione", messaggio, null, null, new BooleanCallback() {

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
															Layout.addMessage(new MessageBean(mapErrorMessages.get(data.getAttribute("id")).toString(), "",
																	MessageType.ERROR));
														}
													}
													// mail sbloccata
													else {
														DatiOperazioneRichiestaWindow operazioneRichiestaWindow = new DatiOperazioneRichiestaWindow(layout,
																records, false, false);
														operazioneRichiestaWindow.show();
													}
												}
											});
										}
									}
								});
							}
							// No force lock
							else {
								Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
							}
						}
						// Mail noLock
						else {
							DatiOperazioneRichiestaWindow operazioneRichiestaWindow = new DatiOperazioneRichiestaWindow(layout, records, false, false);
							operazioneRichiestaWindow.show();
						}
					}
				});
			}
		});
		return archiviaMenuItem;
	}

	// Voce menu' contestuale : Visualizza prot. associato/i
	protected MenuItem creaVisualizzaProtAssociatiMenuItem(final Record pRecordMail) {
		// Visualizza prot. associato/i
		MenuItem visualizzaProtAssociatiMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_visualizzaProtAssociatiMenuItem(),
				"postaElettronica/visProtAssociati.png");
		final Map lMap = pRecordMail.getAttributeAsMap("estremiRegProtAssociati");
		if (lMap != null && lMap.size() > 0) {
			final Iterator iterator = lMap.keySet().iterator();
			if (lMap.size() == 1) {
				final String idUd = (String) iterator.next();
				visualizzaProtAssociatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						Record record = new Record();
						record.setAttribute("idUd", idUd);
						DettaglioRegProtAssociatoWindow dettaglioRegProtAssociatoWindow = new DettaglioRegProtAssociatoWindow(record,
								"Dettaglio prot. associato " + (String) lMap.get(idUd));
					}
				});
			} else {
				Menu submenu = new Menu();
				while (iterator.hasNext()) {
					final String idUd = (String) iterator.next();
					MenuItem submenuItem = new MenuItem((String) lMap.get(idUd));
					submenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							Record record = new Record();
							record.setAttribute("idUd", idUd);
							DettaglioRegProtAssociatoWindow dettaglioRegProtAssociatoWindow = new DettaglioRegProtAssociatoWindow(record,
									"Dettaglio prot. associato " + (String) lMap.get(idUd));
						}
					});
					submenu.addItem(submenuItem);
				}
				visualizzaProtAssociatiMenuItem.setSubmenu(submenu);
			}
		}
		return visualizzaProtAssociatiMenuItem;
	}

	protected MenuItem annullaSceltaUOCompetente(final Record pRecordMail) {

		MenuItem annullaUoCompetenteMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_annulla_uo_competente(),
				"archivio/annulla_uo_competente.png");
		annullaUoCompetenteMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				final RecordList listaEmail = new RecordList();
				Record tempRecord = new Record();
				tempRecord.setAttribute("idEmail", pRecordMail.getAttributeAsString("idEmail"));
				listaEmail.add(tempRecord);
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
		return annullaUoCompetenteMenuItem;
	}

	// Voce menu' contestuale : Assegna
	protected MenuItem creaAssegnaMenuItem(final Record pRecordMail, final Record destinatariPreferiti) {
		
		final Menu creaAssegnaUO = new Menu(); 
		
//		final RecordList listaDestinatariPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUOPreferiteMail");
		final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_UO_COMPETENTE.getValue()));							
		
		// Set U.O. competente
		MenuItem assegnaMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_set_uo_competente(), "archivio/assegna.png");
		
		// Set U.O. Standard 
		MenuItem assegnaMenuStandardItem = new MenuItem("Standard");
		assegnaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {

						Record esitoCheck = object.getAttributeAsRecord("esito");

						boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
						boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

						if (isLock) {
							if (isForzaLock) {
								String messaggio = esitoCheck.getAttributeAsString("errorMessage");

								Layout.showConfirmDialogWithWarning("Attenzione", messaggio, null, null, new BooleanCallback() {

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
															Layout.addMessage(new MessageBean(mapErrorMessages.get(data.getAttribute("id")).toString(), "",
																	MessageType.ERROR));
														}
													} else {
														manageAssegnaClick(pRecordMail, listaDestinatariPreferiti);
													}
												}
											});
										}
									}
								});
							}
							// No force lock
							else {
								Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
							}
						}
						// Mail noLock
						else {
							manageAssegnaClick(pRecordMail, listaDestinatariPreferiti);
						}
					}
				});
			}
		});
		creaAssegnaUO.addItem(assegnaMenuStandardItem);
		
		// Set U.O. Rapido 
		MenuItem assegnaMenuRapidoItem = new MenuItem("Rapido");
		
		Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
		
		if(success != null && success == true
				&& listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
			

			Menu scelteRapide = new Menu();

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
						listRecordAssegna.setAttribute("idEmail", pRecordMail.getAttribute("idEmail"));
						listRecordAssegna.setAttribute("flgIo", pRecordMail.getAttribute("flgIO"));
							
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
					
			assegnaMenuRapidoItem.setSubmenu(scelteRapide);
		} else {
			assegnaMenuRapidoItem.setEnabled(false);
		}
		
		
		creaAssegnaUO.addItem(assegnaMenuRapidoItem);
		
		assegnaMenuItem.setSubmenu(creaAssegnaUO);
		
		return assegnaMenuItem;
	}
	
	
	
	

	// Voce menu' contestuale : Azione da fare
	protected MenuItem buildAzioneDaFareMenuItem(final Record pRecordMail) {
		// Azione da fare
		MenuItem assegnaMenuItem = new MenuItem(I18NUtil.getMessages().postaElettronica_list_azioneDaFare_title(), "postaElettronica/todo.png");
		assegnaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				final RecordList listaEmail = new RecordList();

				pRecordMail.setAttribute("codAzioneDaFare", ((Map) pRecordMail.toMap().get("azioneDaFareBean")).get("codAzioneDaFare"));
				pRecordMail.setAttribute("azioneDaFare", ((Map) pRecordMail.toMap().get("azioneDaFareBean")).get("azioneDaFare"));
				pRecordMail.setAttribute("dettaglioAzioneDaFare", ((Map) pRecordMail.toMap().get("azioneDaFareBean")).get("dettaglioAzioneDaFare"));

				listaEmail.add(pRecordMail);

				checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {

						Record esitoCheck = object.getAttributeAsRecord("esito");

						boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
						boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

						if (isLock) {
							if (isForzaLock) {
								String messaggio = esitoCheck.getAttributeAsString("errorMessage");

								Layout.showConfirmDialogWithWarning("Attenzione", messaggio, null, null, new BooleanCallback() {

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
													}
													// mail sbloccata
													else {
														SelezionaAzioneDaFareWindow azioniDaFareWindow = new SelezionaAzioneDaFareWindow(false, listaEmail,
																instance.getLayout(), null);
														azioniDaFareWindow.show();
													}
												}
											});
										}
									}
								});
							}
							// No force lock
							else {
								Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
							}
						}
						// Mail noLock
						else {
							SelezionaAzioneDaFareWindow azioniDaFareWindow = new SelezionaAzioneDaFareWindow(false, listaEmail, instance.getLayout(), null);
							azioniDaFareWindow.show();
						}
					}
				});
			}
		});
		return assegnaMenuItem;
	}

	// Voce menu' contestuale : Stampa mail
	protected MenuItem buildStampaEmailMenuItem(final Record lRecord) {

		Menu creaPDFMenu = new Menu();

		// Stampa
		MenuItem stampaMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_stampaMenuItem(), "protocollazione/stampaEtichetta.png");

		// E-mail completa
		MenuItem stampaPdfMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_stampaPdfMenuItem());
		stampaPdfMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				lRecord.setAttribute("tipo", lRecord.getAttribute("tipoEmail"));
				lRecord.setAttribute("sottotipo", lRecord.getAttribute("sottotipoEmail"));
				lRecord.setAttribute("destinatariPrincipali", lRecord.getAttribute("destinatariPrimari"));
				lRecord.setAttribute("desUOAssegnataria", lRecord.getAttribute("assegnatario"));
				lRecord.setAttribute("statoInvio", lRecord.getAttribute("descrStatoInvio"));
				lRecord.setAttribute("statoConsegna", lRecord.getAttribute("descrStatoConsegna"));
				lRecord.setAttribute("statoAccettazione", lRecord.getAttribute("descrStatoAccettazione"));
				lRecord.setAttribute("flgIO", lRecord.getAttribute("flgIo"));
				lRecord.setAttribute("completa", "true");

				getAurigaGetDettaglioPostaElettronicaDataSource().performCustomOperation("get", lRecord, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record lRecord = response.getData()[0];
							lRecord.setAttribute("completa", "true");
							lRecord.setAttribute("escapedHtmlBody", lRecord.getAttribute("escapedHtmlBody"));
							PreviewWindowEmailPdf visualizzaMail = new PreviewWindowEmailPdf(lRecord);
							visualizzaMail.show();
						}
					}
				}, new DSRequest());
			}
		});
		creaPDFMenu.addItem(stampaPdfMenuItem);

		// E-mail completa Html
		MenuItem stampaHtmlMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_stampaHtmlMenuItem());
		stampaHtmlMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				lRecord.setAttribute("tipo", lRecord.getAttribute("tipoEmail"));
				lRecord.setAttribute("sottotipo", lRecord.getAttribute("sottotipoEmail"));
				lRecord.setAttribute("destinatariPrincipali", lRecord.getAttribute("destinatariPrimari"));
				lRecord.setAttribute("desUOAssegnataria", lRecord.getAttribute("assegnatario"));
				lRecord.setAttribute("statoInvio", lRecord.getAttribute("descrStatoInvio"));
				lRecord.setAttribute("statoConsegna", lRecord.getAttribute("descrStatoConsegna"));
				lRecord.setAttribute("statoAccettazione", lRecord.getAttribute("descrStatoAccettazione"));
				lRecord.setAttribute("flgIO", lRecord.getAttribute("flgIo"));
				lRecord.setAttribute("completa", "true");

				getAurigaGetDettaglioPostaElettronicaDataSource().performCustomOperation("get", lRecord, new DSCallback() {

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
					}
				}, new DSRequest());
			}
		});
		creaPDFMenu.addItem(stampaHtmlMenuItem);

		// Solo testo messaggio
		MenuItem stampaTestoMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_stampaTestoMenuItem());
		stampaTestoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				lRecord.setAttribute("tipo", lRecord.getAttribute("tipoEmail"));
				lRecord.setAttribute("sottotipo", lRecord.getAttribute("sottotipoEmail"));
				lRecord.setAttribute("completa", "false");

				getAurigaGetDettaglioPostaElettronicaDataSource().performCustomOperation("get", lRecord, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record lRecord = response.getData()[0];
							lRecord.setAttribute("completa", "false");
							lRecord.setAttribute("escapedHtmlBody", lRecord.getAttribute("escapedHtmlBody"));
							PreviewWindowEmailPdf visualizzaMail = new PreviewWindowEmailPdf(lRecord);
							visualizzaMail.show();
						}
					}
				}, new DSRequest());
			}
		});
		creaPDFMenu.addItem(stampaTestoMenuItem);

		// Solo testo messaggio Html
		MenuItem stampaTestoHtmlMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_stampaTestoHtmlMenuItem());
		stampaTestoHtmlMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

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
							lRecord.setAttribute("inputHtml",lRecord.getAttribute("body"));
							VisualizzaCorpoHTMLMail visualizzaCorpoMail = new VisualizzaCorpoHTMLMail(lRecord);
							visualizzaCorpoMail.show();
						}
					});
				} catch (Exception e) {
				}
			}
		});

		creaPDFMenu.addItem(stampaTestoHtmlMenuItem);

		stampaMenuItem.setSubmenu(creaPDFMenu);
		return stampaMenuItem;
	}

	protected MenuItem buildAssociaAInvioMenuItem(final Record record) {

		MenuItem associaAInvioMenuItem = new MenuItem("Associa a invio", "buttons/addlink.png");
		associaAInvioMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				AssociaInvioPopup associaPopup = new AssociaInvioPopup(instance.getLayout(), null, record);
				associaPopup.show();
			}
		});
		return associaAInvioMenuItem;
	}

	// Voce menu contestuale : Prendi in carico
	protected MenuItem buildPresaInCaricoMenuItem(final Record pRecordMail) {
		// Prendi in carico
		MenuItem prendiInCaricoMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_prendiInCaricoMenuItem(),
				"postaElettronica/prendiInCarico.png");
		prendiInCaricoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				final OperazioniPerEmailPopup presaInCaricoEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.PRESA_IN_CARICO.getValue(), null) {

					@Override
					public void onClickOkButton(final DSCallback callback) {
						final RecordList listaEmail = new RecordList();
						listaEmail.add(pRecordMail);
						Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_presa_in_carico_in_corso());
						buildTipoOperazione(listaEmail, null, getMotivo(), false, TipoOperazioneMail.PRESA_IN_CARICO.getValue());
						markForDestroy();
					}
				};
				presaInCaricoEmailPopup.show();
			}
		});
		return prendiInCaricoMenuItem;
	}

	// Voce menu contestuale : Messa in carico
	protected MenuItem buildMessaInCaricoMenuItem(final Record pRecordMail, final Record destinatariPreferiti) {
		
		Menu creaInCarico = new Menu();
		
//		final RecordList listaDestinatariPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUtentiPreferitiMail");
		final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.METTI_IN_CARICO.getValue()));								
		
		// Metti in carico
		MenuItem messaInCaricoMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_messaInCaricoMenuItem(),
				"postaElettronica/mettiInCarico.png");
		
		// Metti in carico Standard
		MenuItem messaInCaricoMenuStandardItem = new MenuItem("Standard");
		messaInCaricoMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				final OperazioniPerEmailPopup presaInCaricoEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.MESSA_IN_CARICO.getValue(), listaDestinatariPreferiti) {

					@Override
					public void onClickOkButton(final DSCallback callback) {
						final RecordList listaEmail = new RecordList();
						listaEmail.add(pRecordMail);
						Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_carico_in_corso());
						buildTipoOperazione(listaEmail, getUtente(), getMotivo(), false, TipoOperazioneMail.MESSA_IN_CARICO.getValue());
						markForDestroy();
					}
				};
				presaInCaricoEmailPopup.show();
			}
		});
		creaInCarico.addItem(messaInCaricoMenuStandardItem);
			
		// Metti in carico Rapido 
		MenuItem messaInCaricoRapidaItem = new MenuItem("Rapido");

		Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
		
		if(success != null && success == true
				&& listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
			
			Menu scelteRapide = new Menu();

			for(int i=0; i < listaDestinatariPreferiti.getLength();i++){
				
				Record currentRecord = listaDestinatariPreferiti.get(i);
				final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
//				final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
				final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
				 
				MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
				currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						
						final RecordList listaEmail = new RecordList();
						listaEmail.add(pRecordMail);
						String motivo = null;
						String userLockFor = idDestinatarioPreferito;
						Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_carico_in_corso());
						buildTipoOperazione(listaEmail, userLockFor, motivo, false, TipoOperazioneMail.MESSA_IN_CARICO.getValue());
						
						
					}
				});
				scelteRapide.addItem(currentRapidoItem);
				
			}
			
			messaInCaricoRapidaItem.setSubmenu(scelteRapide);
			
		} else {
			messaInCaricoRapidaItem.setEnabled(false);
		}
		
		
		messaInCaricoMenuItem.setSubmenu(creaInCarico);
		creaInCarico.addItem(messaInCaricoRapidaItem);
		
		
		
		return messaInCaricoMenuItem;
	}

	
	
	// Voce menu contestuale : Manda in approvazione
	protected MenuItem buildMessaInApprovazioneMenuItem(final Record pRecordMail, final Record destinatariPreferiti) {
		
		Menu creaInApprovazioneMenu = new Menu();
		
//		final RecordList listaDestinatariPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUtentiPreferitiMail");
		final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.MANDA_IN_APPROVAZIONE.getValue()));								
		
		// Manda in approvazione
		MenuItem mandaInApprovazioneMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_mandaInApprovazioneMenuItem(),
				"postaElettronica/manda_in_approvazione.png");
		
		// Manda in approvazione Standard
		MenuItem mandaInApprovazioneStandardItem = new MenuItem("Standard");
		
		mandaInApprovazioneStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				final OperazioniPerEmailPopup messaInApprovazioneEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue(), listaDestinatariPreferiti) {

					@Override
					public void onClickOkButton(final DSCallback callback) {
						
						final RecordList listaEmail = new RecordList();
						listaEmail.add(pRecordMail);
						String motivo = getMotivo();
						if (motivo != null && !"".equalsIgnoreCase(motivo)) {
							motivo = "[APPROVAZIONE]" + motivo;
						} else {
							motivo = "[APPROVAZIONE]";
						}
						Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_approvazione_in_corso());
						buildTipoOperazione(listaEmail, getUtente(), motivo, false, TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue());
						markForDestroy();
					}
				};
				messaInApprovazioneEmailPopup.show();
			}
		});
		creaInApprovazioneMenu.addItem(mandaInApprovazioneStandardItem);
		
		// Manda in approvazione Rapida
		MenuItem mandaInApprovazioneRapidaItem = new MenuItem("Rapida");
		
		Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
				
		if(success != null && success == true
				&&  listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
			
			Menu scelteRapide = new Menu();

			for(int i=0; i < listaDestinatariPreferiti.getLength();i++){
				
				Record currentRecord = listaDestinatariPreferiti.get(i);
				final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
//				final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
				final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
				 
				MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
				currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						
						final RecordList listaEmail = new RecordList();
						listaEmail.add(pRecordMail);
						String motivo = "[APPROVAZIONE]";
						String userLockFor = idDestinatarioPreferito;
						Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_approvazione_in_corso());
						buildTipoOperazione(listaEmail, userLockFor, motivo, false, TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue());
						
					}
				});
				scelteRapide.addItem(currentRapidoItem);
				
			}
			
			mandaInApprovazioneRapidaItem.setSubmenu(scelteRapide);
		} else {
			mandaInApprovazioneRapidaItem.setEnabled(false);
		}
		
		creaInApprovazioneMenu.addItem(mandaInApprovazioneRapidaItem);
		
		mandaInApprovazioneMenuItem.setSubmenu(creaInApprovazioneMenu);
		return mandaInApprovazioneMenuItem;
	}

	// Voce menu contestuale : RILASCIA
	protected MenuItem buildRilascioMenuItem(final Record pRecordMail) {
		// Rilascia
		MenuItem rilasciaMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_rilasciaMenuItem(), "postaElettronica/rilascia.png");
		rilasciaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				final OperazioniPerEmailPopup rilasciaEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.RILASCIA.getValue(), null) {

					@Override
					public void onClickOkButton(final DSCallback callback) {
						final RecordList listaEmail = new RecordList();
						listaEmail.add(pRecordMail);
						Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_rilascio_in_corso());
						rilascio(listaEmail, null, getMotivo(), false);
						markForDestroy();
					}
				};
				rilasciaEmailPopup.show();
			}
		});
		return rilasciaMenuItem;
	}

	protected void manageAssegnaClick(final Record pRecord, final RecordList listaPreferiti) {
		final AssegnazioneEmailPopup assegnazioneEmailPopup = new AssegnazioneEmailPopup(pRecord, listaPreferiti) {

			@Override
			public void onClickOkButton(Record record, final DSCallback callback) {
				
				final RecordList listaEmail = new RecordList();
				listaEmail.add(pRecord);
				record.setAttribute("listaRecord", listaEmail);
				
				Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_assegnazione_in_corso());
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneEmailDataSource");
				try {
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							operationCallback(response, pRecord, "idEmail", I18NUtil.getMessages().posta_elettronica_assegnazione_successo(),
									I18NUtil.getMessages().posta_elettronica_assegnazione_errore(), callback);
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}
			}
		};
		assegnazioneEmailPopup.show();
	}

	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {

		Record lRecord = new Record();
		final String idEmail = record.getAttribute("idEmail");
		lRecord.setAttribute("idEmail", idEmail);

		if (!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaLoadDettaglioEmailDataSource", "idEmail", FieldType.TEXT);
			layout.changeDetail(lGwtRestDataSource, new PostaElettronicaDetail("posta_elettronica"));
			lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						layout.getDetail().editRecord(record, recordNum);
						layout.getDetail().getValuesManager().clearErrors(true);
						layout.getDetail().markForRedraw();
						callback.execute(response, null, new DSRequest());
					}
				}
			}, new DSRequest());
		} else {
			GWTRestDataSource lGwtRestDataSource = getAurigaGetDettaglioPostaElettronicaDataSource();
			layout.changeDetail(lGwtRestDataSource, new DettaglioPostaElettronica("posta_elettronica"));
			lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						layout.getDetail().editRecord(record, recordNum);
						layout.getDetail().getValuesManager().clearErrors(true);
						layout.getDetail().markForRedraw();
						callback.execute(response, null, new DSRequest());
					}
				}
			}, new DSRequest());
		}
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
			layout.reloadListAndSetCurrentRecord(record);
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

	private MenuItem buildInoltraMenuItem(Record abilitazioni, final Record pRecordMail) {

		if ((abilitazioni.getAttribute("abilitaInoltraEmail") != null && abilitazioni.getAttribute("abilitaInoltraEmail").equals("true"))
				&& (abilitazioni.getAttribute("abilitaInoltraContenuti") != null && abilitazioni.getAttribute("abilitaInoltraContenuti").equals("true"))) {

			Menu inoltraSubmenu = new Menu();
			
			// Allega contenuti
			MenuItem allegaFileEmailMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_allegaFileEmailMenuItem(), "blank.png");
			allegaFileEmailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final Record record = new Record();
					final RecordList listaEmail = new RecordList();
					pRecordMail.setAttribute("allegaEmlSbustato", "false");
					listaEmail.add(pRecordMail);
					record.setAttribute("listaRecord", listaEmail);

					checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {

							Record esitoCheck = object.getAttributeAsRecord("esito");

							boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
							boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

							if (isLock) {
								if (isForzaLock) {
									String messaggio = esitoCheck.getAttributeAsString("errorMessage");

									Layout.showConfirmDialogWithWarning("Attenzione", messaggio, null, null, new BooleanCallback() {

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
														}
														// mail sbloccata
														else {
															final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>(
																	"AurigaInvioMailDatasource");
															lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

																@Override
																public void execute(Record object) {
																	manageInoltraClick(pRecordMail, false);
																}
															});
														}
													}
												});
											}
										}
									});
								}
								// No force lock
								else {
									Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
								}
							}
							// Mail noLock
							else {
								final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
								lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {
										manageInoltraClick(pRecordMail, false);
									}
								});
							}
						}
					});
				}
			});
			inoltraSubmenu.addItem(allegaFileEmailMenuItem);

			MenuItem allegaMailOriginaleMenuItem = new MenuItem("Allega e-mail originale", "blank.png");
			allegaMailOriginaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final Record record = new Record();
					final RecordList listaEmail = new RecordList();
					pRecordMail.setAttribute("allegaEmlSbustato", "false");
					listaEmail.add(pRecordMail);
					record.setAttribute("listaRecord", listaEmail);

					checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {

							Record esitoCheck = object.getAttributeAsRecord("esito");

							boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
							boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

							if (isLock) {
								if (isForzaLock) {
									String messaggio = esitoCheck.getAttributeAsString("errorMessage");

									Layout.showConfirmDialogWithWarning("Attenzione", messaggio, null, null, new BooleanCallback() {

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
														}
														// mail sbloccata
														else {
															final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>(
																	"AurigaInvioMailDatasource");
															lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

																@Override
																public void execute(Record object) {
																	manageInoltraClick(pRecordMail, true);
																}
															});
														}
													}
												});
											}
										}
									});
								}
								// No force lock
								else {
									Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
								}
							}
							// Mail noLock
							else {
								final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
								lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {
										manageInoltraClick(pRecordMail, true);
									}
								});
							}
						}
					});
				}
			});
			inoltraSubmenu.addItem(allegaMailOriginaleMenuItem);
			// Allega e-mail senza busta di trasporto
			MenuItem inoltraMenuItemSbustato = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_inoltraMenuItemSbustato());
			inoltraMenuItemSbustato.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final Record record = new Record();
					pRecordMail.setAttribute("allegaEmlSbustato", "true");
					final RecordList listaEmail = new RecordList();
					listaEmail.add(pRecordMail);
					record.setAttribute("listaRecord", listaEmail);

					checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {

							Record esitoCheck = object.getAttributeAsRecord("esito");

							boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
							boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

							if (isLock) {
								if (isForzaLock) {
									String messaggio = esitoCheck.getAttributeAsString("errorMessage");

									Layout.showConfirmDialogWithWarning("Attenzione", messaggio, null, null, new BooleanCallback() {

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
														}
														// mail sbloccata
														else {
															final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>(
																	"AurigaInvioMailDatasource");
															lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

																@Override
																public void execute(Record object) {
																	manageInoltraClick(pRecordMail, true);
																}
															});
														}
													}
												});
											}
										}
									});
								}
								// No force lock
								else {
									Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
								}
							}
							// Mail noLock
							else {
								final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
								lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {
										manageInoltraClick(pRecordMail, true);
									}
								});
							}
						}
					});
				}
			});
			String abilitaScaricaEmailSenzaBustaTrasporto = abilitazioni.getAttribute("abilitaScaricaEmailSenzaBustaTrasporto");
			if ("true".equals(abilitaScaricaEmailSenzaBustaTrasporto))
				inoltraSubmenu.addItem(inoltraMenuItemSbustato);
			
			// Inoltra
			MenuItem inoltraMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_inoltraMenuItem(), "postaElettronica/inoltro.png");
			inoltraMenuItem.setSubmenu(inoltraSubmenu);
			return inoltraMenuItem;
		} else if (abilitazioni.getAttribute("abilitaInoltraEmail") != null && abilitazioni.getAttribute("abilitaInoltraEmail").equals("true")) {
			// Inoltra
			MenuItem inoltraMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_inoltraMenuItem(), "postaElettronica/inoltro.png");
			inoltraMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final Record record = new Record();
					final RecordList listaEmail = new RecordList();
					listaEmail.add(pRecordMail);
					record.setAttribute("listaRecord", listaEmail);

					checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {

							Record esitoCheck = object.getAttributeAsRecord("esito");

							boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
							boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

							if (isLock) {
								if (isForzaLock) {
									String messaggio = esitoCheck.getAttributeAsString("errorMessage");

									Layout.showConfirmDialogWithWarning("Attenzione", messaggio, null, null, new BooleanCallback() {

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
														}
														// mail sbloccata
														else {
															final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>(
																	"AurigaInvioMailDatasource");
															lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

																@Override
																public void execute(Record object) {
																	manageInoltraClick(pRecordMail, true);
																}
															});
														}
													}
												});
											}
										}
									});
								}
								// No force lock
								else {
									Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
								}
							}
							// Mail noLock
							else {
								final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
								lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {
										manageInoltraClick(pRecordMail, true);
									}
								});
							}
						}
					});
				}
			});
			return inoltraMenuItem;
		} else if (abilitazioni.getAttribute("abilitaInoltraContenuti") != null && abilitazioni.getAttribute("abilitaInoltraContenuti").equals("true")) {
			MenuItem inoltraContenutiMenuItem = new MenuItem("Inoltra contenuti", "postaElettronica/inoltro.png");
			inoltraContenutiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final Record record = new Record();
					final RecordList listaEmail = new RecordList();
					listaEmail.add(pRecordMail);
					record.setAttribute("listaRecord", listaEmail);

					checkLockEmail(pRecordMail, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {

							Record esitoCheck = object.getAttributeAsRecord("esito");

							boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
							boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");

							if (isLock) {
								if (isForzaLock) {
									String messaggio = esitoCheck.getAttributeAsString("errorMessage");

									Layout.showConfirmDialogWithWarning("Attenzione", messaggio, null, null, new BooleanCallback() {

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
														}
														// mail sbloccata
														else {
															final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>(
																	"AurigaInvioMailDatasource");
															lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

																@Override
																public void execute(Record object) {
																	manageInoltraClick(pRecordMail, false);
																}
															});
														}
													}
												});
											}
										}
									});
								}
								// No force lock
								else {
									Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
								}
							}
							// Mail noLock
							else {
								final GWTRestService<Record, Record> lGwtRestServiceInvio = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
								lGwtRestServiceInvio.call(record, new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {
										manageInoltraClick(pRecordMail, false);
									}
								});
							}
						}
					});
				}
			});
			return inoltraContenutiMenuItem;
		}
		return null;
	}

	private MenuItem buildScaricaMenuItem(Record abilitazioni, final Record pRecordMail) {

		if ((abilitazioni.getAttribute("abilitaScaricaEmail") != null && abilitazioni.getAttribute("abilitaScaricaEmail").equals("true"))
				&& (abilitazioni.getAttribute("abilitaScaricaEmailSenzaBustaTrasporto") != null
						&& abilitazioni.getAttribute("abilitaScaricaEmailSenzaBustaTrasporto").equals("true"))) {

			Menu scaricaSubmenu = new Menu();
			// Scarica e-mail
			MenuItem scaricaEmailMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_scaricaEmailMenuItem(), "blank.png");
			scaricaEmailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					scaricaMail(pRecordMail);
				}
			});
			scaricaSubmenu.addItem(scaricaEmailMenuItem);
			// Scarica e-mail senza busta di trasporto
			MenuItem scaricaEmailSenzaBustaPECTrasportoMenuItem = new MenuItem(
					I18NUtil.getMessages().posta_elettronica_list_scaricaEmailSenzaBustaPECTrasportoMenuItem(), "blank.png");
			scaricaEmailSenzaBustaPECTrasportoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					scaricaMailSenzaBustaTrasporto(pRecordMail);
				}
			});
			scaricaSubmenu.addItem(scaricaEmailSenzaBustaPECTrasportoMenuItem);

			// Scarica
			MenuItem scaricaMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_scaricaMenuItem(), "file/download_manager.png");
			scaricaMenuItem.setSubmenu(scaricaSubmenu);
			return scaricaMenuItem;

		} else if (abilitazioni.getAttribute("abilitaScaricaEmail") != null && abilitazioni.getAttribute("abilitaScaricaEmail").equals("true")) {
			// Scarica e-mail
			MenuItem scaricaEmailMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_list_scaricaEmailMenuItem(), "file/download_manager.png");
			scaricaEmailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					scaricaMail(pRecordMail);
				}
			});

			return scaricaEmailMenuItem;

		} else if (abilitazioni.getAttribute("abilitaScaricaEmailSenzaBustaTrasporto") != null
				&& abilitazioni.getAttribute("abilitaScaricaEmailSenzaBustaTrasporto").equals("true")) {
			// Scarica e-mail senza busta di trasporto
			MenuItem scaricaEmailSenzaBustaPECTrasportoMenuItem = new MenuItem(
					I18NUtil.getMessages().posta_elettronica_list_scaricaEmailSenzaBustaPECTrasportoMenuItem(), "file/download_manager.png");
			scaricaEmailSenzaBustaPECTrasportoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					scaricaMailSenzaBustaTrasporto(pRecordMail);
				}
			});
			return scaricaEmailSenzaBustaPECTrasportoMenuItem;
		}
		return null;
	}

	private void scaricaMail(final Record listRecord) {

		if (listRecord.getAttribute("uriMail") == null || listRecord.getAttribute("uriMail").equalsIgnoreCase("")) {
			Record input = new Record();
			input.setAttribute("idEmail", listRecord.getAttribute("idEmail"));
			getAurigaGetDettaglioPostaElettronicaDataSource().executecustom("getUriFromIdEmail", input, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					Record lRecord = new Record();
					lRecord.setAttribute("displayFilename", listRecord.getAttribute("progrDownloadStampa") + ".eml");
					lRecord.setAttribute("uri", response.getData()[0].getAttribute("uriFile"));
					lRecord.setAttribute("sbustato", "false");
					lRecord.setAttribute("remoteUri", "false");

					DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
				}
			});
		} else {
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", listRecord.getAttribute("progrDownloadStampa") + ".eml");
			lRecord.setAttribute("uri", listRecord.getAttribute("uriEmail"));
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", "false");
			DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
		}
	}

	private void scaricaMailSenzaBustaTrasporto(final Record listRecord) {
		
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", listRecord.getAttribute("progrDownloadStampa") + ".eml");
		lRecord.setAttribute("idEmail", listRecord.getAttribute("idEmail"));
		lRecord.setAttribute("uri", listRecord.getAttribute("uriEmail"));
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", "false");

		getAurigaGetDettaglioPostaElettronicaDataSource().performCustomOperation("recuperaDatiEmlSbustato", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					String uri = (String) record.getAttribute("uri");

					Record lRecord = new Record();

					lRecord.setAttribute("displayFilename", listRecord.getAttribute("progrDownloadStampa") + ".eml");
					lRecord.setAttribute("uri", uri);
					lRecord.setAttribute("sbustato", "false");
					lRecord.setAttribute("remoteUri", "false");
					DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
				}
			}
		}, new DSRequest());
	}

	private void scaricaZipAllegati(final Record pRecord) {
		Record lRecord = new Record();
		lRecord.setAttribute("idEmail", pRecord.getAttribute("idEmail"));
		lRecord.setAttribute("uri", pRecord.getAttribute("uriEmail"));
		lRecord.setAttribute("progrDownloadStampa", pRecord.getAttribute("progrDownloadStampa"));

		GWTRestDataSource lGwtRestDataSource = getAurigaGetDettaglioPostaElettronicaDataSource();
		lGwtRestDataSource.extraparam.put("limiteMaxZipError", I18NUtil.getMessages().alert_archivio_list_limiteMaxZipError());
		lGwtRestDataSource.performCustomOperation("getZipAllegati", lRecord, new DSCallback() {

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

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
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
	protected void manageDetailButtonClick(final ListGridRecord record) {
		final String classifica = getLayout().getFilter().getExtraParam().get("classifica");
		String flgIo = record.getAttributeAsString("flgIo");
		final String idEmail = record.getAttributeAsString("idEmail");
		final String tipoRel = record.getAttributeAsString("tipoRel");
		final Record lRecord = new Record();
		lRecord.setAttribute("idEmail", idEmail);
		lRecord.setAttribute("flgIo", flgIo);
		Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_caricamento_dettaglio_mail());

		if (!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {
			getAurigaAbilitazioniEmailDataSource().call(lRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					DettaglioEmailWindow lDettaglioEmailWindow = new DettaglioEmailWindow(idEmail, tipoRel, classifica, record, object, getLayout());
				}
			});
		} else {
			DettaglioEmailWindow lDettaglioEmailWindow = new DettaglioEmailWindow(idEmail, tipoRel, classifica, record, null, getLayout());
		}
	}

	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/

	/**
	 * 
	 * @param listaMail
	 * @param idUserLockFor
	 * @param motivi
	 * @param isMassiva
	 * @param sceltaAzione
	 *            = PRESA_IN_CARICO("PIC"), MESSA_IN_CARICO("MIC"), MANDA_IN_APPROVAZIONE("MIA"), RILASCIA("R");
	 */
	public void buildTipoOperazione(final RecordList listaMail, String idUserLockFor, String motivi, final boolean isMassiva, final String sceltaAzione) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);
		record.setAttribute("iduserlockfor", idUserLockFor);
		record.setAttribute("motivi", motivi);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LockEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = caricoErrorMessage(response, listaMail, "idEmail", "id", isMassiva, sceltaAzione);
				if (!listaMail.isEmpty()) {
					Record record = listaMail.get(0);
					layout.reloadListAndSetCurrentRecord(record);
				}
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
				if (!listaMail.isEmpty()) {
					Record record = listaMail.get(0);
					layout.reloadListAndSetCurrentRecord(record);
				}
				Layout.hideWaitPopup();
				if (errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
				} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_rilascio_successo(), "", MessageType.INFO));
				}
			}
		});
	}

	private String caricoErrorMessage(DSResponse response, RecordList listaMail, String pkField, String nameField, boolean isMassiva, String sceltaAzione) {
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
				errorMsg = I18NUtil.getMessages().posta_elettronica_rilascio_errore();
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

				Record rec = new Record();

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					Map mapErrorMessages = data.getAttributeAsMap("errorMessages");
					if (mapErrorMessages != null && mapErrorMessages.size() > 0) {
						rec.setAttribute("esitoLock", false);
					} else
						rec.setAttribute("esitoLock", true);
				}
				callback.execute(rec);
			}
		});
	}

	private void archiviaMail(Record recorMail) {

		final RecordList records = new RecordList();
		records.add(recorMail);

		DatiOperazioneRichiestaWindow operazioneRichiestaWindow = new DatiOperazioneRichiestaWindow(layout, records, false, true);
		operazioneRichiestaWindow.show();
	}

	private void manageAnnullamentoAssegnazioneUOCompetente(Record object) {
		Map errorMessages = object.getAttributeAsMap("errorMessages");
		if (errorMessages != null && errorMessages.size() > 0) {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_annulla_uo_competente_error(), "", MessageType.ERROR));
		} else {
			getLayout().doSearch();
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_annulla_uo_competente_success(), "", MessageType.INFO));
		}
	}

	public static String encodeURL(String str) {
		if (str != null) {
			return URL.encode(str.replaceAll("&", "%26"));
		}
		return null;
	}

	private boolean isBozza(Record record) {
		String id = record != null ? record.getAttribute("id") : "";
		return id != null && id.toUpperCase().endsWith(".B");
	}

	private Record getInvioBozzaMailBeanToRecord(Record inputRecord) {
		
		Record recordMailBean = new Record();

		recordMailBean.setAttribute("idEmail", inputRecord.getAttribute("idEmail"));
		recordMailBean.setAttribute("idEmailPrincipale", inputRecord.getAttribute("idEmail"));
		recordMailBean.setAttribute("aliasAccountMittente", inputRecord.getAttribute("accountMittente"));
		recordMailBean.setAttribute("mittente", inputRecord.getAttribute("accountMittente"));
		recordMailBean.setAttribute("destinatari", inputRecord.getAttribute("destinatariPrimari"));
		recordMailBean.setAttribute("oggetto", inputRecord.getAttribute("oggetto"));
		recordMailBean.setAttribute("destinatariCC", inputRecord.getAttribute("destinatariCC"));
		recordMailBean.setAttribute("destinatariCCN", inputRecord.getAttribute("destinatariCCN"));
		recordMailBean.setAttribute("uriMail", inputRecord.getAttribute("uriEmail"));
		recordMailBean.setAttribute("dimensioneMail", inputRecord.getAttribute("dimensione"));
		recordMailBean.setAttribute("interoperabile", inputRecord.getAttribute("flgInteroperabile"));
		recordMailBean.setAttribute("confermaLettura",
				inputRecord.getAttribute("flgRichConfermaLettura") != null && !"".equals(inputRecord.getAttribute("flgRichConfermaLettura"))
						&& "1".equals(inputRecord.getAttribute("flgRichConfermaLettura")) ? true : false);
		if (inputRecord.getAttributeAsString("corpo") != null && !"".equals(inputRecord.getAttributeAsString("corpo"))) {
			String body = inputRecord.getAttributeAsString("corpo");
			if (body.startsWith("<html>")) {
				recordMailBean.setAttribute("bodyHtml", body);
				recordMailBean.setAttribute("textHtml", "html");
			} else {
				recordMailBean.setAttribute("bodyText", body);
				recordMailBean.setAttribute("textHtml", "text");
			}
		}
		
		if(inputRecord.getAttribute("categoria") != null && "PEC".equals(inputRecord.getAttribute("categoria"))){
			recordMailBean.setAttribute("casellaIsPec", "true");
		} else {
			recordMailBean.setAttribute("casellaIsPec", "false");
		}

		return recordMailBean;
	}

	private MenuItem buildAbilitaRegIstanzaMenuItem(final ListGridRecord record, Record abilitazioni) {

		MenuItem autotutelaMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_button_registra_istanza_autotutela(),
				"menu/istanze_autotutela.png");
		autotutelaMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				final Record lRecord = new Record();
				lRecord.setAttribute("idEmail", record.getAttributeAsString("idEmail"));
				lRecord.setAttribute("flgIo", record.getAttributeAsString("flgIo"));
				lRecord.setAttribute("body", record.getAttributeAsString("corpo"));
				lRecord.setAttribute("tsInvio", record.getAttributeAsString("tsInvio"));
				lRecord.setAttribute("categoria", record.getAttributeAsString("categoria"));
				lRecord.setAttribute("azioneDaFareBean", new RecordList((JavaScriptObject) record.getAttributeAsObject("azioneDaFareBean")));
				lRecord.setAttribute("id", record.getAttribute("id"));
				lRecord.setAttribute("flgStatoProt", record.getAttribute("flgStatoProtocollazione"));
				manageRegistraIstanzaClick(lRecord, "istanze_autotutela");
			}
		});

		MenuItem cedMenuItem = new MenuItem(I18NUtil.getMessages().posta_elettronica_button_registra_istanza_ced(), "menu/istanze_ced.png");
		cedMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				final Record lRecord = new Record();
				lRecord.setAttribute("idEmail", record.getAttributeAsString("idEmail"));
				lRecord.setAttribute("flgIo", record.getAttributeAsString("flgIo"));
				lRecord.setAttribute("body", record.getAttributeAsString("corpo"));
				lRecord.setAttribute("tsInvio", record.getAttributeAsString("tsInvio"));
				lRecord.setAttribute("categoria", record.getAttributeAsString("categoria"));
				lRecord.setAttribute("azioneDaFareBean", new RecordList((JavaScriptObject) record.getAttributeAsObject("azioneDaFareBean")));
				lRecord.setAttribute("id", record.getAttribute("id"));
				lRecord.setAttribute("flgStatoProt", record.getAttribute("flgStatoProtocollazione"));
				
				manageRegistraIstanzaClick(lRecord, "istanze_ced");
			}
		});

		if ((abilitazioni.getAttribute("abilitaRegIstanzaAutotutela") != null && abilitazioni.getAttribute("abilitaRegIstanzaAutotutela").equals("true"))
				&& (abilitazioni.getAttribute("abilitaRegIstanzaCED") != null && abilitazioni.getAttribute("abilitaRegIstanzaCED").equals("true"))) {

			MenuItem abilRegIstaMenu = new MenuItem(I18NUtil.getMessages().posta_elettronica_button_registra_istanza(), "menu/istanze.png");
			Menu abilRegIstaSubMenu = new Menu();
			abilRegIstaSubMenu.addItem(autotutelaMenuItem);
			abilRegIstaSubMenu.addItem(cedMenuItem);
			abilRegIstaMenu.setSubmenu(abilRegIstaSubMenu);
			return abilRegIstaMenu;
		} else if (abilitazioni.getAttribute("abilitaRegIstanzaAutotutela") != null
				&& abilitazioni.getAttribute("abilitaRegIstanzaAutotutela").equals("true")) {
			autotutelaMenuItem.setTitle(I18NUtil.getMessages().posta_elettronica_button_registra_istanza() + " "
					+ I18NUtil.getMessages().posta_elettronica_button_registra_istanza_autotutela());
			return autotutelaMenuItem;
		} else if (abilitazioni.getAttribute("abilitaRegIstanzaCED") != null && abilitazioni.getAttribute("abilitaRegIstanzaCED").equals("true")) {
			cedMenuItem.setTitle(I18NUtil.getMessages().posta_elettronica_button_registra_istanza() + " "
					+ I18NUtil.getMessages().posta_elettronica_button_registra_istanza_ced());
			return cedMenuItem;
		}

		return null;
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
							actionArchiviaMail(pRecord);
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
											actionArchiviaMail(pRecord);
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
				layout.doSearch();
			}
		};
		lEditaIstanzaWindowFromMail.show();
	}

	/**
	 * Questo metodo viene utilizzato per rimuovere dalla cache l'id della mail che viene fornito come input. La cache viene utilizzata per memorizzare le
	 * informazioni delle mail che sono state in precedenza visualizzate. Ad una successiva richiesta non viene eseguito quindi il fetch delle informazioni ma
	 * si utilizzano quelle già presenti. Questo comporta che, in caso di modifica delle bozze, vengano visualizzate delle informazioni obsolete. Grazie a
	 * questo metodo si può rimuovere dalla cache l'id della mail per cui si vuole eseguire un nuovo fetch alla prossima richiesta.
	 * 
	 * @param idEmail
	 *            id della mail da rimuovere dalla cache
	 */
	public void deleteFromAllegatiCache(String idEmail) {
		if (idEmail != null && lMapEmailDetailRecord != null) {
			lMapEmailDetailRecord.remove(idEmail);
		}
	}
	
	// Se richiesto chiude le mail da cui hanno origine l'inoltro o la risposta
	protected void chiudiMailDiProvenienza(Record pRecordMail, boolean chiudiMailProvenienza, String gestisciAzioniDaFare) {
		boolean isChiusuraMultipla = false;
		final RecordList listaMailDaChiudere = new RecordList();
		if (pRecordMail != null) {
			if (chiudiMailProvenienza) {
				if (pRecordMail.getAttribute("emailPredecessoreIdEmail") != null
						&& !"".equalsIgnoreCase(pRecordMail.getAttribute("emailPredecessoreIdEmail"))) {
					// Ho una mail precedessore (può essere la bozza di una risposta)
					// Verifico se è aperta
					if ("true".equalsIgnoreCase(pRecordMail.getAttribute("flgMailPredecessoreStatoLavAperta"))){
						Record recordDaAggiungere = new Record();
						recordDaAggiungere.setAttribute("idEmail", pRecordMail.getAttribute("emailPredecessoreIdEmail"));
						listaMailDaChiudere.add(recordDaAggiungere);
					}
				} else if (pRecordMail.getAttributeAsRecordArray("idEmailInoltrate") != null) {
					// Ricavo tutte le mail inoltrate (può essere la bozza di un inoltro)
					Record[] elencoEmailInoltrate = pRecordMail.getAttributeAsRecordArray("idEmailInoltrate");
					for (Record emailInoltrata : elencoEmailInoltrate) {
						if ("1".equalsIgnoreCase(emailInoltrata.getAttribute("flgMailStatoLavAperta"))){
							Record recordDaAggiungere = new Record();
							recordDaAggiungere.setAttribute("idEmail", emailInoltrata.getAttribute("idMailInoltrata"));
							listaMailDaChiudere.add(recordDaAggiungere);
						}
					}
					if (elencoEmailInoltrate.length > 1) {
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
					layout.doSearch();
				}
			});
		} else {
			layout.doSearch();
		}
	}
		
	protected boolean isMessaggioAskChiusuraMailProvenienzaToShow(Record pRecordMail) {
		if (pRecordMail != null){
			return Boolean.valueOf(pRecordMail.getAttribute("flgMailPredecessoreStatoLavAperta"));
		}else{
			return false;
		}
	}
	
	protected Map<String, String> generaParametriPopupChiusuraMailProvenienza(Record pRecordMail) {
		
		boolean isOperazioneMassiva = false;
		String nomeOperazione = "";

		int nroMailPredecessore = pRecordMail.getAttributeAsInt("nroMailPredecessore");
		String ruoloVsPredecessore = pRecordMail.getAttribute("ruoloVsPredecessore");
		if (nroMailPredecessore == 1){
			// Sono in una risposta o inoltro singolo (bozza)
			isOperazioneMassiva = false;
			nomeOperazione = ruoloVsPredecessore;
		} else {
			// Sono in un inoltro multiplo (bozza)
			isOperazioneMassiva = true;
			nomeOperazione = ruoloVsPredecessore;
		}
		
		Map<String, String> mappaParametri = new HashMap<String, String>();
		mappaParametri.put("isOperazioneMassiva", isOperazioneMassiva + "");
		mappaParametri.put("nomeOperazione", nomeOperazione);

		mappaParametri = settaAzioniDaFareParam(pRecordMail, mappaParametri);
		
		return mappaParametri;
	}
	
	protected Map<String, String> settaAzioniDaFareParam(Record pRecordMail, Map<String, String> mappaParametri){
		boolean flgMailPredecessoreConAzioneDaFare = Boolean.valueOf(pRecordMail.getAttribute("flgMailPredecessoreConAzioneDaFare"));
		String mailPredecessoriUnicaAzioneDaFare = pRecordMail.getAttribute("mailPredecessoriUnicaAzioneDaFare");
		
		mappaParametri.put("isAzioniDaFarePresenti", flgMailPredecessoreConAzioneDaFare + "");
		mappaParametri.put("nomeAzioneDaFare", mailPredecessoriUnicaAzioneDaFare);
		return mappaParametri;
	}
		
	private void manageRegistraRepertorio(final Record recordMail, final String destinatariEAccount, final boolean protocollaInteraEmail){

		getAurigaGetDettaglioPostaElettronicaDataSource().performCustomOperation("get", recordMail, new DSCallback() {

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
						Layout.showConfirmDialogWithWarning("Attenzione", "Sono stati trovati dei file nella sezione Appunti & Note, vuoi aggiungerli come allegati alla repertoriazione?", null, null, new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								
								if(value) {
									lRecord.setAttribute("fileDaAppunti",fileDaAppunti); //Aggiunta della lista dei file recuperati alla protocollazione.
									GWTRestService<Record, Record> lGwtRestServiceProtocolla = new GWTRestService<Record, Record>("AurigaProtocollaPostaElettronicaDataSource");
									if(protocollaInteraEmail) {
										lGwtRestServiceProtocolla.extraparam.put("isProtocollaInteraEmail", "true");
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
														actionArchiviaMail(lRecord);
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
																		actionArchiviaMail(lRecord);
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
														actionArchiviaMail(lRecord);
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
																		actionArchiviaMail(lRecord);
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
//											manageModificaDatiRepertorioButtonClick(object.getAttribute("idUd"), destinatariEAccount,  lRecord);
											manageRegistraRepertorioCallback(object, destinatariEAccount, false, null, true);
										}

										@Override
										public void onClickAnnullaChiudiMailButton(Record objectPopup, DSCallback callback) {
											markForDestroy();
											actionArchiviaMail(lRecord);
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
															actionArchiviaMail(lRecord);
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
	
	private void manageRegistraRepertorioCallback(final Record lRecord, final String destinatariEAccount,
			final boolean isThereIdUdMail, final Record recordMail, final boolean isNewReg){
		
		if(!isNewReg) {
			editaRepertorioWindowFromEmail(lRecord,destinatariEAccount,null,null,null,isThereIdUdMail,recordMail);
		} else {
			if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaRepertorioEntrata")) {
				final String repertorioEntrata = AurigaLayout.getImpostazioniDocumento("repertorioEntrata");
				if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaRepertorioEntrata")) {
					String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioEntrata");
					String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoRepertorioEntrata");
					editaRepertorioWindowFromEmail(lRecord,destinatariEAccount,idTipoDoc,nomeTipoDoc,repertorioEntrata,isThereIdUdMail,recordMail);
				} else {
					String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioEntrata");
					AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "R", repertorioEntrata, new ServiceCallback<Record>() {
	
						@Override
						public void execute(Record lRecordTipoDoc) {
							String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
							String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
							editaRepertorioWindowFromEmail(lRecord,destinatariEAccount,idTipoDoc,nomeTipoDoc,repertorioEntrata,isThereIdUdMail,recordMail);
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
								editaRepertorioWindowFromEmail(lRecord,destinatariEAccount,idTipoDoc,nomeTipoDoc,repertorioEntrata,isThereIdUdMail,recordMail);
							} else {
								String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioEntrata");
								AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "R", repertorioEntrata, new ServiceCallback<Record>() {
	
									@Override
									public void execute(Record lRecordTipoDoc) {
										String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
										String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
										editaRepertorioWindowFromEmail(lRecord,destinatariEAccount,idTipoDoc,nomeTipoDoc,repertorioEntrata,isThereIdUdMail,recordMail);
									}
								});
							}
						}
					}
				});
			}
		}
	}
	
	protected void manageModificaDatiRepertorioButtonClick(String idUd, final String destinatariEAccount, final Record recordMail) {
		layout.getDetail().markForRedraw();
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
	
	private void editaRepertorioWindowFromEmail(Record lRecord, String destinatariEAccount,
			String idTipoDoc, String nomeTipoDoc, String repertorio, boolean isThereIdUdMail, Record recordMail) {
		
		if (destinatariEAccount != null) {
			lRecord.setAttribute("casellaEmailDestinatario", destinatariEAccount); // viene settato il campo casellaEmailDestinatario del bean
		}
		String title = "";
		CustomLayout externalLayout = null;
		if(isThereIdUdMail) {
			title = "repertorio_entrata";
			externalLayout = layout;
		} else {
			title = "repertorio_mail";
		}
		lRecord.setAttribute("tipoDocumento", idTipoDoc);
		lRecord.setAttribute("nomeTipoDocumento", nomeTipoDoc);
		lRecord.setAttribute("repertorio", repertorio);
		
		RegistraRepertorioEmailWindow lRegistraRepertorioEmailWindow = new RegistraRepertorioEmailWindow(title, lRecord, "E", isThereIdUdMail, recordMail, null, externalLayout) {
	
			@Override
			public void manageAfterCloseWindow() {
				layout.doSearch();
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
	
	private void ricaricaLista() {
		if(getLayout() != null) {
			getLayout().doSearch();	
		}
	}
	
	public GWTRestDataSource getAurigaGetDettaglioPostaElettronicaDataSource() {
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
		lGwtRestDataSource.addParam("idNode", idNode);
		return lGwtRestDataSource;
	}
	
	public GWTRestService<Record, Record> getAurigaAbilitazioniEmailDataSource() {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaAbilitazioniEmailDataSource");
		lGwtRestService.addParam("idNode", idNode);
		return lGwtRestService;
	}
	
	public GWTRestDataSource getProtocolloDataSource() {
		GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource", "idUd", FieldType.TEXT);
		lGwtRestDataSourceProtocollo.addParam("idNode", idNode);
		return lGwtRestDataSourceProtocollo;
	}
	
}