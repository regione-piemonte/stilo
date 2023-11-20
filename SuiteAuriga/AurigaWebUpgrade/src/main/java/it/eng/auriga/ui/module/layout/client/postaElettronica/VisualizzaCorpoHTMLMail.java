/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.PrintProperties;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author Lorenzo Mereu
 *
 */
public class VisualizzaCorpoHTMLMail extends ModalWindow {

	protected RadioGroupItem style;
	protected FormItem lTextAreaItemBody;
	protected DynamicForm formEstremi;
	protected ImgButtonItem viewerContenutiItem1;
	private StaticTextItem itemValue;
	protected StaticTextItem titleItemDesUOAssegnataria;
	private EstremiTitleStaticTextItem labelId;
	private FormItem[] itemEstremi;
	private Label labelLavorazione;
	private Label label;
	private FormItem[] itemLavorazione;
	private DynamicForm formLavorazione;
	private Label labelContenuti;
	private DynamicForm formContenuti;
	private FormItem[] itemContenuti;
	private Label labelAllegati;
	private Label labelValueAllegati;

	public VisualizzaCorpoHTMLMail(Record pRecord){
		super("visualizza_corpo_html_mail", true);
	
		String html = pRecord.getAttribute("inputHtml") != null ? pRecord.getAttribute("inputHtml") : "";
		
		String title = pRecord.getAttribute("titoloGUIDettaglioEmail");
		String progressivo = pRecord.getAttribute("id");
		String tipo = pRecord.getAttribute("tipo");
		String sottoTipo = pRecord.getAttribute("sottotipo");
		String tsInvio = pRecord.getAttributeAsString("tsInvio");
		Boolean isBozzaEditabile = pRecord != null && pRecord.getAttributeAsString("isBozzaEditabile") != null
				&& "1".equals(pRecord.getAttributeAsString("isBozzaEditabile")) ? true : false;

		String titolo = "";
		if (title == null) {
			titolo = "Dettaglio corpo e-mail - Anteprima di stampa";
		} else {
			titolo = (title != null && title.length() > 0) ? title + " - Anteprima di stampa " : progressivo + " - " + tipo + " - " + sottoTipo + " del "
					+ tsInvio + " - Anteprima di stampa";
		}

		String c = pRecord.getAttribute("completa");

		boolean completa = "true".equals(c) ? true : false;

		setTitle(titolo);
		setWidth(850);
		setHeight(650);
		setAutoCenter(true);

		final VLayout layout = new VLayout();

		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		layout.setLayoutLeftMargin(5);
		layout.setLayoutRightMargin(5);
		layout.setLayoutTopMargin(5);
		layout.setLayoutBottomMargin(5);

		try {
			String allegati = "";

			if (completa) {
				pRecord.setAttribute("tipoEmail", pRecord.getAttribute("tipo") + " - " + pRecord.getAttribute("sottotipo"));

				boolean casellaIsPEC = "true".equals(pRecord.getAttribute("casellaIsPEC")) ? true : false;
				String pec = casellaIsPEC ? "SI" : "NO";
				pRecord.setAttribute("isPec", pec);

				String dest = pRecord.getAttribute("destinatariPrincipali");
				String destCC = pRecord.getAttribute("destinatariCC");

				if (dest == null) {
					dest = "";

					RecordList listaDestinatari = pRecord.getAttributeAsRecordList("listaDestinatariPrincipali");
					if (listaDestinatari != null) {
						for (int n = 0; n < listaDestinatari.getLength(); n++) {
							Record destin = listaDestinatari.get(n);

							String indirizzo = destin.getAttributeAsString("indirizzo");
							dest += "" + indirizzo + ";";
						}
					}
				}
				if (destCC == null) {
					destCC = "";
					RecordList listaDestinatariCC = pRecord.getAttributeAsRecordList("listaDestinatariCC");

					if (listaDestinatariCC != null) {
						for (int n = 0; n < listaDestinatariCC.getLength(); n++) {
							Record destin = listaDestinatariCC.get(n);

							String indirizzo = destin.getAttributeAsString("indirizzo");
							destCC += "" + indirizzo + ";";
						}
					}
				}

				pRecord.setAttribute("destinatari", dest + " " + destCC);

				Map azioniDaFareMap = new Record(pRecord.getAttributeAsJavaScriptObject("azioneDaFareBean")).toMap();
				String codAzioneDaFare = (String) azioniDaFareMap.get("azioneDaFare");

				String descrizioneAzioneDaFare = (String) azioniDaFareMap.get("dettaglioAzioneDaFare");

				pRecord.setAttribute("codAzioneDaFare", codAzioneDaFare);
				pRecord.setAttribute("dettaglioAzioneDaFare", descrizioneAzioneDaFare);

				RecordList listaAllegati = pRecord.getAttributeAsRecordList("listaAllegati");

				if (listaAllegati != null) {
					for (int n = 0; listaAllegati != null && n < listaAllegati.getLength(); n++) {
						Record allegatoRecord = listaAllegati.get(n);

						String file = allegatoRecord.getAttributeAsString("nomeFile");
						String numeroProgrAllegato = allegatoRecord.getAttributeAsString("numeroProgrAllegato");
						if ((numeroProgrAllegato != null) && (numeroProgrAllegato.length() > 0)) {
							allegati += "Allegato n° " + numeroProgrAllegato + ": " + file + "<BR>";
						} else {
							allegati += " " + file + "/n";
						}
					}
				}

				pRecord.setAttribute("allegati", allegati);

				addEstremi(pRecord);
				addLavorazione(pRecord);
				addContenuti(pRecord);
				addAllegati(allegati);

				formEstremi.setFields(itemEstremi);
				formLavorazione.setFields(itemLavorazione);
				formContenuti.setFields(itemContenuti);

				layout.addMember(label);
				layout.addMember(formEstremi);

				layout.addMember(labelLavorazione);
				layout.addMember(formLavorazione);

				layout.addMember(labelContenuti);
				layout.addMember(formContenuti);
			}

			final HTMLFlow htmlFlow = new HTMLFlow();
			htmlFlow.setWidth100();
			htmlFlow.setHeight100();
			if (isBozzaEditabile) {
				htmlFlow.setContents(html);
			} else {
				htmlFlow.setContentsURL(html);
			}

			layout.addMember(htmlFlow);

			if (completa) {
				final HTMLFlow lHtmlFlow = new HTMLFlow();
				htmlFlow.setWidth100();
				htmlFlow.setHeight100();
				lHtmlFlow.setContents("</br></br>");

				layout.addMember(lHtmlFlow);
				layout.addMember(labelAllegati);

				if (allegati.length() > 0) {
					layout.addMember(labelValueAllegati);
				}
				layout.addMember(lHtmlFlow);
			}

			final Object[] obj = new Object[1];
			obj[0] = layout;

			IButton printPreviewButton = new IButton("Stampa");

			final PrintProperties printProperties = new PrintProperties();
			String includes[] = new String[1];
			includes[0] = "com.smartgwt.client.widgets.Canvas";

			printProperties.setIncludeControls(includes);

			printPreviewButton.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {

					Layout.printComponents(obj);
				}
			});
			
			HStack _buttons = new HStack(5);
			_buttons.setHeight(30);
			_buttons.setAlign(Alignment.CENTER);
			_buttons.setPadding(5);
			_buttons.addMember(printPreviewButton);
			
			VLayout portletLayout = new VLayout();
			portletLayout.setHeight100();
			portletLayout.setWidth100();
			portletLayout.setOverflow(Overflow.VISIBLE);

			portletLayout.addMember(layout);
			portletLayout.addMember(_buttons);
			
			if (completa) {
				formEstremi.editRecord(pRecord);
				formLavorazione.editRecord(pRecord);
				formContenuti.editRecord(pRecord);
			}
			
			setBody(portletLayout);

		} catch (Exception e) {
			markForDestroy();
		}
	}

	private void addAllegati(String allegati) {

		labelAllegati = new Label();
		labelAllegati.setStyleName("labelStampaMenu");
		labelAllegati.setContents("Allegati");
		labelAllegati.setHeight(30);
		labelAllegati.setWidth100();

		labelValueAllegati = new Label();
		labelValueAllegati.setAlign(com.smartgwt.client.types.Alignment.LEFT);
		labelValueAllegati.setLayoutAlign(VerticalAlignment.BOTTOM);
		labelValueAllegati.setContents(allegati);
		labelValueAllegati.setHeight(30);
		labelValueAllegati.setWidth100();
	}

	public void addEstremi(Record pRecord) {
		label = new Label();
		label.setStyleName("labelStampaMenu");
		label.setContents("Estremi");
		label.setHeight(30);
		label.setWidth100();

		formEstremi = new DynamicForm();
		formEstremi.setHeight(50);
		formEstremi.setWidth100();

		formEstremi.editRecord(pRecord);
		formEstremi.setWrapItemTitles(false);
		formEstremi.setNumCols(10);
		formEstremi.setColWidths("60", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		boolean uscita = "O".equals(pRecord.getAttribute("flgIO")) ? true : false;

		int x = uscita ? 20 : 14;
		itemEstremi = new FormItem[x];

		SpacerItem spacer1Colon = new SpacerItem();
		spacer1Colon.setColSpan(1);
		spacer1Colon.setHeight(10);
		spacer1Colon.setEndRow(false);
		itemEstremi[0] = spacer1Colon;
		spacer1Colon.setEndRow(true);
		itemEstremi[1] = spacer1Colon;

		addRow("N°", "id", 3, itemEstremi);
		addRow("Tipo Email", "tipoEmail", 5, itemEstremi);
		addRow("Email PEC", "isPec", 7, itemEstremi);
		addRow("Data invio", "tsInvio", 9, itemEstremi);
		addRow("Data", "tsInsRegistrazione", 11, itemEstremi);
		addRow("Casella scarico", "casellaAccount", 13, itemEstremi);

		if (uscita) {
			addRow("Stato Invio", "statoInvio", 15, itemEstremi);
			addRow("Stato Consegna", "statoConsegna", 17, itemEstremi);
			addRow("Stato Accettazione", "statoAccettazione", 19, itemEstremi);
		}
	}

	public void addLavorazione(Record pRecord) {
		labelLavorazione = new Label();
		labelLavorazione.setStyleName("labelStampaMenu");
		labelLavorazione.setContents("Lavorazione");
		labelLavorazione.setHeight(30);
		labelLavorazione.setWidth100();

		formLavorazione = new DynamicForm();
		formLavorazione.setHeight(50);
		formLavorazione.setWidth100();

		formLavorazione.editRecord(pRecord);
		formLavorazione.setWrapItemTitles(false);
		formLavorazione.setNumCols(10);
		formLavorazione.setColWidths("60", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		itemLavorazione = new FormItem[24];

		SpacerItem spacer1Colon = new SpacerItem();
		spacer1Colon.setColSpan(1);
		spacer1Colon.setHeight(10);
		spacer1Colon.setEndRow(false);
		itemLavorazione[0] = spacer1Colon;
		spacer1Colon.setEndRow(true);
		itemLavorazione[1] = spacer1Colon;

		addRow4("Stato Lavorazione", "statoLavorazione", "dataAggStatoLavorazione", "orarioAggStatoLavorazione", 7, itemLavorazione);
		addRow4("U.O competente", "desUOAssegnataria", "dataUltimaAssegnazione", "orarioUltimaAssegnazione", 13, itemLavorazione);
		addRow4("In carico a", "desUtenteLock", "dataLock", "orarioLock", 19, itemLavorazione);

		addRow("Azione da fare", "codAzioneDaFare", 21, itemLavorazione);
		addRow("Dettagli azione", "dettaglioAzioneDaFare", 23, itemLavorazione);
	}

	public void addContenuti(Record pRecord) {
		labelContenuti = new Label();
		labelContenuti.setStyleName("labelStampaMenu");
		labelContenuti.setContents("Contenuti");
		labelContenuti.setHeight(30);
		labelContenuti.setWidth100();

		formContenuti = new DynamicForm();
		formContenuti.setHeight(50);
		formContenuti.setWidth100();
		formContenuti.editRecord(pRecord);
		formContenuti.setWrapItemTitles(false);
		formContenuti.setNumCols(10);
		formContenuti.setColWidths("60", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		itemContenuti = new FormItem[12];

		SpacerItem spacer1Colon = new SpacerItem();
		spacer1Colon.setColSpan(1);
		spacer1Colon.setHeight(10);
		spacer1Colon.setEndRow(false);

		itemContenuti[0] = spacer1Colon;
		spacer1Colon.setEndRow(true);
		itemContenuti[1] = spacer1Colon;

		addRow800("Mittente", "accountMittente", 3, itemContenuti);
		addRow800("Destinatari", "destinatari", 5, itemContenuti);

		itemContenuti[6] = spacer1Colon;
		spacer1Colon.setEndRow(true);
		itemContenuti[7] = spacer1Colon;

		addRow("Oggetto", "subject", 9, itemContenuti);
		addRow("Testo del Messaggio", "", 11, itemContenuti);
	}

	public void addRow4(String titleLabel, String name, String name1, String name2, int i, FormItem[] item) {

		EstremiTitleStaticTextItem labelId = new EstremiTitleStaticTextItem(titleLabel, 80);
		labelId.setHeight(30);
		labelId.setWidth(150);

		StaticTextItem itemValue = new StaticTextItem(name, "");
		itemValue.setShowTitle(false);
		itemValue.setTextBoxStyle(it.eng.utility.Styles.formStampaHTML);
		itemValue.setHeight(30);

		itemValue.setWidth(100);

		EstremiTitleStaticTextItem labelData = new EstremiTitleStaticTextItem("dal", 20);
		labelData.setHeight(30);
		labelData.setWidth(10);

		StaticTextItem itemValue1 = new StaticTextItem(name1, "");
		itemValue1.setShowTitle(false);
		itemValue1.setTextBoxStyle(it.eng.utility.Styles.formStampaHTML);
		itemValue1.setHeight(30);
		itemValue1.setWidth(50);

		EstremiTitleStaticTextItem labelOrario = new EstremiTitleStaticTextItem("alle", 20);
		labelOrario.setHeight(30);
		labelOrario.setWidth(10);

		StaticTextItem itemValue2 = new StaticTextItem(name2, "");
		itemValue2.setShowTitle(false);
		itemValue2.setTextBoxStyle(it.eng.utility.Styles.formStampaHTML);
		itemValue2.setHeight(30);
		itemValue2.setEndRow(true);
		itemValue2.setWidth(50);

		item[i - 5] = labelId;
		item[i - 4] = itemValue;

		item[i - 3] = labelData;
		item[i - 2] = itemValue1;

		item[i - 1] = labelOrario;
		item[i] = itemValue2;
	}

	public void addRow(String titleLabel, String name, int i, FormItem[] item) {

		EstremiTitleStaticTextItem labelId = new EstremiTitleStaticTextItem(titleLabel, 80);
		labelId.setHeight(30);
		labelId.setWidth(150);

		StaticTextItem itemValue = new StaticTextItem(name, "");
		itemValue.setShowTitle(false);
		itemValue.setTextBoxStyle(it.eng.utility.Styles.formStampaHTML);
		itemValue.setHeight(30);
		itemValue.setEndRow(true);
		itemValue.setWidth(400);
		item[i - 1] = labelId;
		item[i] = itemValue;
	}

	public void addRow800(String titleLabel, String name, int i, FormItem[] item) {

		EstremiTitleStaticTextItem labelId = new EstremiTitleStaticTextItem(titleLabel, 80);
		labelId.setHeight(60);
		labelId.setWidth(150);

		StaticTextItem itemValue = new StaticTextItem(name, "");
		itemValue.setShowTitle(false);
		itemValue.setTextBoxStyle(it.eng.utility.Styles.formStampaHTML);
		itemValue.setHeight(60);
		itemValue.setEndRow(true);
		itemValue.setWidth(600);
		item[i - 1] = labelId;
		item[i] = itemValue;
	}

	public static String encodeURL(String str) {
		if (str != null) {
			return URL.encode(str.replaceAll("&", "%26"));
		}
		return null;
	}

	public class EstremiTitleStaticTextItem extends StaticTextItem {

		public EstremiTitleStaticTextItem(String title, int width) {
			setTitle(title);
			setColSpan(1);
			setDefaultValue(title + AurigaLayout.getSuffixFormItemTitle());
			setWidth(width);
			setShowTitle(false);
			setAlign(Alignment.RIGHT);
			setTextBoxStyle(it.eng.utility.Styles.formTitleStampaHTML);
			setWrap(false);
		}

		@Override
		public void setCanEdit(Boolean canEdit) {
			setTextBoxStyle(it.eng.utility.Styles.formTitleStampaHTML);
		}
	}
}
