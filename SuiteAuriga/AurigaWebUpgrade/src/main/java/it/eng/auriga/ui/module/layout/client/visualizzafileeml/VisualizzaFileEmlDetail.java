/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.FileAllegatiEmailItem;
import it.eng.auriga.ui.module.layout.client.postaElettronica.VisualizzaCorpoHTMLMail;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class VisualizzaFileEmlDetail extends CustomDetail {

	protected VisualizzaFileEmlWindow window;
	protected VisualizzaFileEmlDetail instance;

	protected HiddenItem idEmailItem;
	protected HiddenItem categoriaItem;

	protected DynamicForm mDynamicFormAttori;
	protected TextItem mittenteItem;
	protected TextItem tsInvioItem;
	protected TextItem destinatarioItem;
	protected TextItem destinatariccItem;

	protected DynamicForm mDynamicFormContenuti;
	protected TextItem oggettoDatiProtocolloContenutiItem;
	protected StaticTextItem titleItemCorpoMail;
	protected TextAreaItem corpoMailContenutiItem;
	protected ImgButtonItem viewerContenutiItem;

	private HiddenItem body;
	private HiddenItem uriFileEml;

	protected DynamicForm mDynamicFormFileAllegati;
	protected FileAllegatiEmailItem fileAllegatiItem;

	/*
	 * DETAIL SECTION TabSet tabDatiPrincipali
	 */

	protected DetailSection detailSectionMittentiDestinatari;
	protected DetailSection detailSectionContenuti;
	protected DetailSection detailSectionFileAllegati;

	/*
	 * DETAIL section tabSet tabEmailCollegate
	 */
	protected DetailSection detailSectionListaEmaiInviate;
	protected DetailSection detailSectionListaEmailRicevute;

	protected DynamicForm mDynamicFormListaMailEntrata;
	protected DynamicForm mDynamicFormListMailInviata;

	protected DynamicForm hiddenForm;

	public boolean ricaricaPagina = true;

	public VisualizzaFileEmlDetail(String nomeEntita, VisualizzaFileEmlWindow pWindow) {

		super(nomeEntita);

		instance = this;

		window = pWindow;

		buildFormMittentiDestinatari();
		buildFormContenuti();
		buildFormFileAllegati();
		setPaddingAsLayoutMargin(false);

		// Creo i due Spacer
		VLayout spacer1 = new VLayout();
		spacer1.setHeight100();
		spacer1.setWidth100();

		VLayout layoutDatiPrincipaliDetailSection = createDetailSectionTabSetDatiPrincipali();

		VLayout layoutTab1 = new VLayout();
		layoutTab1.addMember(layoutDatiPrincipaliDetailSection);
		layoutTab1.addMember(spacer1);
		addMember(layoutTab1);
	}

	private VLayout createDetailSectionTabSetDatiPrincipali() {

		VLayout layoutDatiPrincipali = new VLayout();
		layoutDatiPrincipali.setHeight(5);
		layoutDatiPrincipali.setOverflow(Overflow.VISIBLE);
		detailSectionMittentiDestinatari = new DetailSection("Mittente e Destinatari", true, true, false, mDynamicFormAttori);
		detailSectionMittentiDestinatari.setVisible(true);
		layoutDatiPrincipali.addMember(detailSectionMittentiDestinatari);

		detailSectionContenuti = new DetailSection("Contenuti", true, true, false, mDynamicFormContenuti);
		detailSectionContenuti.setVisible(true);
		layoutDatiPrincipali.addMember(detailSectionContenuti);

		detailSectionFileAllegati = new DetailSection("File allegati", true, true, false, mDynamicFormFileAllegati);
		detailSectionFileAllegati.setVisible(true);
		layoutDatiPrincipali.addMember(detailSectionFileAllegati);

		return layoutDatiPrincipali;
	}

	protected void buildFormMittentiDestinatari() {
		mDynamicFormAttori = new DynamicForm();
		mDynamicFormAttori.setValuesManager(vm);
		mDynamicFormAttori.setWidth100();
		mDynamicFormAttori.setHeight100();
		mDynamicFormAttori.setWrapItemTitles(false);
		mDynamicFormAttori.setNumCols(10);
		mDynamicFormAttori.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		// MITTENTE
		mittenteItem = new TextItem("mittente", "Da");
		mittenteItem.setWidth(750);
		mittenteItem.setShowTitle(true);
		mittenteItem.setCanEdit(false);

		tsInvioItem = new TextItem("date", "Inviato il");
		tsInvioItem.setShowTitle(true);
		tsInvioItem.setWidth(120);
		tsInvioItem.setStartRow(true);
		tsInvioItem.setCanEdit(false);

		// DESTINATARIO
		destinatarioItem = new TextItem("destinatari", "A");
		destinatarioItem.setWidth(750);
		destinatarioItem.setCanEdit(false);
		destinatarioItem.setShowTitle(true);
		destinatarioItem.setStartRow(true);

		destinatariccItem = new TextItem("destinataricc", "CC");
		destinatariccItem.setWidth(750);
		destinatariccItem.setCanEdit(false);
		destinatariccItem.setShowTitle(true);
		destinatariccItem.setStartRow(true);

		mDynamicFormAttori.setFields(mittenteItem, tsInvioItem, destinatarioItem, destinatariccItem);

	}

	protected void buildFormContenuti() {
		mDynamicFormContenuti = new DynamicForm();
		mDynamicFormContenuti.setValuesManager(vm);
		mDynamicFormContenuti.setWidth100();
		mDynamicFormContenuti.setHeight100();
		mDynamicFormContenuti.setWrapItemTitles(false);
		mDynamicFormContenuti.setNumCols(10);
		mDynamicFormContenuti.setColWidths("60", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		oggettoDatiProtocolloContenutiItem = new TextItem("oggetto", "Oggetto");
		oggettoDatiProtocolloContenutiItem.setWidth(750);
		oggettoDatiProtocolloContenutiItem.setShowTitle(true);
		oggettoDatiProtocolloContenutiItem.setCanEdit(false);

		// CORPO MAIL
		corpoMailContenutiItem = new TextAreaItem("messaggio", "Corpo") {
			
			@Override
			public boolean showViewer() {
				return false;
			};
		};
		corpoMailContenutiItem.setWidth(750);
		corpoMailContenutiItem.setShowTitle(true);
		corpoMailContenutiItem.setCanEdit(false);
		corpoMailContenutiItem.setStartRow(true);

		body = new HiddenItem("body");

		uriFileEml = new HiddenItem("uriFileEml");

		viewerContenutiItem = new ImgButtonItem("viewerCorpo", "buttons/view.png", "Visualizza contenuti");
		viewerContenutiItem.setShowTitle(false);
		viewerContenutiItem.setAlwaysEnabled(true);
		viewerContenutiItem.setWidth(16);
		viewerContenutiItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				final Record lRecord = new Record(vm.getValues());
				previewShowBody(lRecord);
			}
		});

		mDynamicFormContenuti.setFields(oggettoDatiProtocolloContenutiItem, corpoMailContenutiItem, body, viewerContenutiItem);

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
				
				return Layout.isPrivilegioAttivo("EML/PR");
			}
		};

		fileAllegatiItem.setName("allegati");
		fileAllegatiItem.setAttribute("closeViewer", nomeEntita);
		fileAllegatiItem.setShowTitle(false);

		mDynamicFormFileAllegati.setFields(fileAllegatiItem);

	}

	private String iconHtml(String src) {
		return "<div style=\"cursor:pointer\" align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";
	}

	protected Canvas createExpansionComponentDestinatari(ListGridRecord record) {

		/*
		 * LISTA EMAIL SUCCESSIVE COLLEGATE
		 */

		HLayout orizzontaleSinistro = new HLayout();
		HLayout orizzontaleDestro = new HLayout();

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
		// listaDestinatari.setCanSort(false);
		listaDestinatari.setShowHeaderContextMenu(false);

		// ID EMAIL
		ListGridField idEmail = new ListGridField("idEmail", "Id.");
		idEmail.setHidden(true);
		// TIPO DESTINATARIO
		final ListGridField tipoDest = new ListGridField("tipoDest", "Tipo");
		tipoDest.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
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
		// DESTINATARIO
		ListGridField indirizzoDest = new ListGridField("indirizzoDest", "Destinatario");
		indirizzoDest.setIcon("public.png");
		// STATO
		ListGridField statoConsolidamentoDest = new ListGridField("statoConsolidamentoDest", "Stato");
		statoConsolidamentoDest.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoConsolidamentoDest.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoConsolidamentoDest = record.getAttribute("statoConsolidamentoDest");
				if (statoConsolidamentoDest != null && statoConsolidamentoDest.equalsIgnoreCase("consegnata")) {
					return iconHtml("postaElettronica/statoConsolidamento/consegnata.png");
				} else if (statoConsolidamentoDest != null && statoConsolidamentoDest.equalsIgnoreCase("errori in consegna")) {
					return iconHtml("postaElettronica/statoConsolidamento/errori_in_consegna.png");
				} else if (statoConsolidamentoDest != null && statoConsolidamentoDest.equalsIgnoreCase("in invio")) {
					return iconHtml("postaElettronica/statoConsolidamento/in_invio.png");
				}
				return null;
			}
		});
		statoConsolidamentoDest.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute("statoConsolidamentoDest");
			}
		});
		statoConsolidamentoDest.setWidth(200);
		// PERVENUTA RICEVUTA DI AVVENUTA LETTURA
		ListGridField flgRicevutaLettura = new ListGridField("flgRicevutaLettura", "Ricevuta lettura");
		flgRicevutaLettura.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
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
		listaDestinatari.setFields(idEmail, tipoDest, indirizzoDest, statoConsolidamentoDest, flgRicevutaLettura);

		Record detailRecord = new Record(vm.getValues());
		RecordList listaEmailSuccessiveCollegate = detailRecord.getAttributeAsRecordList("listaEmailSuccessiveCollegate");
		if (listaEmailSuccessiveCollegate != null) {
			RecordList listaEmailSuccessiveCollegate2Liv = new RecordList();
			for (int i = 0; i < listaEmailSuccessiveCollegate.getLength(); i++) {
				if (listaEmailSuccessiveCollegate.get(i).getAttribute("flgRigaEmailVsDest").equals("2")
						&& listaEmailSuccessiveCollegate.get(i).getAttribute("idEmail").equalsIgnoreCase(record.getAttribute("idEmail"))) {
					listaEmailSuccessiveCollegate2Liv.add(listaEmailSuccessiveCollegate.get(i));
				}
			}
			listaDestinatari.setData(listaEmailSuccessiveCollegate2Liv);
		}

		orizzontaleSinistro.addMember(listaDestinatari);
		layout.addMember(orizzontaleSinistro);
		layout.addMember(orizzontaleDestro);
		return layout;
	}

	@Override
	public void editRecord(Record record) {
		editRecord(record, false);
	}

	public void editRecord(Record record, boolean fromLivello) {
		super.editRecord(record);
		fileAllegatiItem.setDetailRecord(record);

		detailSectionContenuti.openIfhasValue();

		detailSectionFileAllegati.hide();
		detailSectionFileAllegati.show();
		detailSectionFileAllegati.openIfhasValue();

		setCanEdit(false);
		markForRedraw();

	}

	protected void realEditRecord(Record lRecord) {
		super.editRecord(lRecord);
		setCanEdit(false);
		markForRedraw();
	}

	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(false);
	}

	public VisualizzaFileEmlWindow getWindow() {
		return window;
	}

	public void setWindow(VisualizzaFileEmlWindow window) {
		this.window = window;
	}

	protected String buildIconHtml(String src) {
		return "<div align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";
	}

	private void previewShowBody(final Record lRecord) {

		lRecord.setAttribute("completa", "false");
		String uriFileEml = lRecord.getAttribute("uriFileEml");

		Record lRecord1 = new Record();
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

	public static String encodeURL(String str) {
		if (str != null) {
			return URL.encode(str.replaceAll("&", "%26"));
		}
		return null;
	}

	/*
	 * public void closeViewer() {
	 * 
	 * for (Map.Entry<String, PreviewWindow> entry : AurigaLayout.getOpenedViewers().entrySet()) { PreviewWindow pw = entry.getValue();
	 * 
	 * if(nomeEntita !=null && nomeEntita.equals(pw.getPortletOpened())) { pw.markForDestroy(); AurigaLayout.getOpenedViewers().remove(entry.getKey()); } } }
	 */
}