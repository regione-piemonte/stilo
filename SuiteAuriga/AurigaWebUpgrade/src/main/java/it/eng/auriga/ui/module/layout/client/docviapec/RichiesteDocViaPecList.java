/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioEmailWindow;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

/**
 * Mostra tutte le richieste di invio email
 * 
 * @author massimo malvestio
 *
 */
public class RichiesteDocViaPecList extends CustomList {

	private ListGridField id;
	private ListGridField idRichiesta;
	private ListGridField codApplRich;
	private ListGridField tipoDocInv;
	private ListGridField xmlRichiesta;
	private ListGridField previewXmlRichiesta;
	private ListGridField statoRichiesta;
	private ListGridField errorMessage;
	private ListGridField dtSottRich;
	private ListGridField dtInvioEmail;
	private ListGridField statoInvioMail;
	private ListGridField statoAccettazioneMail;
	private ListGridField statoConsegnaMail;
	private ListGridField idEmailAss;

	private ControlListGridField listaProcessiButtonField;

	public RichiesteDocViaPecList(String nomeEntita) {
		super(nomeEntita);

		id = new ListGridField("id");
		id.setCanHide(true);
		id.setHidden(true);

		idEmailAss = new ListGridField("idEmailAss");
		idEmailAss.setCanHide(true);
		idEmailAss.setHidden(true);

		idRichiesta = new ListGridField("idRichiesta", I18NUtil.getMessages().invio_documentazione_via_pec_idRichiesta());

		codApplRich = new ListGridField("codApplRich", I18NUtil.getMessages().invio_documentazione_via_pec_applicazione_richiedente());

		tipoDocInv = new ListGridField("tipoDocInv", I18NUtil.getMessages().invio_documentazione_via_pec_docType());

		xmlRichiesta = new ListGridField("xmlRichiesta");
		xmlRichiesta.setCanHide(true);
		xmlRichiesta.setHidden(true);

		previewXmlRichiesta = new ListGridField("previewXmlRichiesta", "Dettaglio xml");
		previewXmlRichiesta.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		previewXmlRichiesta.setAttribute("custom", true);
		previewXmlRichiesta.setType(ListGridFieldType.ICON);
		previewXmlRichiesta.setAlign(Alignment.CENTER);
		previewXmlRichiesta.setWrap(false);
		previewXmlRichiesta.setWidth(30);
		previewXmlRichiesta.setIconWidth(16);
		previewXmlRichiesta.setIconHeight(16);
		previewXmlRichiesta.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return buildIconHtml("buttons/preview_xml.png");
			}
		});
		previewXmlRichiesta.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return "Dettaglio xml di richiesta";
			}
		});
		previewXmlRichiesta.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				String xmlRichiestaToShow = event.getRecord().getAttribute("xmlRichiesta");
				PreviewXmlRichiestaWindow lPreviewXmlRichiestaWindow = new PreviewXmlRichiestaWindow(xmlRichiestaToShow);
				lPreviewXmlRichiestaWindow.show();
			}
		});

		statoRichiesta = new ListGridField("statoRichiesta", I18NUtil.getMessages().invio_documentazione_via_pec_stato_richiesta());

		errorMessage = new ListGridField("errorMessage", I18NUtil.getMessages().invio_documentazione_via_pec_errore());

		dtSottRich = new ListGridField("dtSottRich", I18NUtil.getMessages().invio_documentazione_via_pec_richiesta_del());
		dtSottRich.setType(ListGridFieldType.DATE);
		dtSottRich.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		dtInvioEmail = new ListGridField("dtInvioEmail", I18NUtil.getMessages().invio_documentazione_via_pec_email_inviata_il());
		dtInvioEmail.setType(ListGridFieldType.DATE);
		dtInvioEmail.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		statoInvioMail = new ListGridField("statoInvioMail", I18NUtil.getMessages().posta_elettronica_list_statoInvioField());
		statoInvioMail.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoInvioMail.setType(ListGridFieldType.ICON);
		statoInvioMail.setAlign(Alignment.CENTER);
		statoInvioMail.setWrap(false);
		statoInvioMail.setWidth(30);
		statoInvioMail.setIconWidth(16);
		statoInvioMail.setIconHeight(16);
		statoInvioMail.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoInvio = (String) record.getAttribute("statoInvioMail");
				return getStatoConsolidamento(statoInvio);
			}
		});
		statoInvioMail.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoInvioString = (String) record.getAttribute("statoInvioMail");
				return getHoverValueStatoInvioMail(statoInvioString);
			}
		});

		statoAccettazioneMail = new ListGridField("statoAccettazioneMail", I18NUtil.getMessages().posta_elettronica_list_statoAccettazioneField());
		statoAccettazioneMail.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoAccettazioneMail.setType(ListGridFieldType.ICON);
		statoAccettazioneMail.setAlign(Alignment.CENTER);
		statoAccettazioneMail.setWrap(false);
		statoAccettazioneMail.setWidth(30);
		statoAccettazioneMail.setIconWidth(16);
		statoAccettazioneMail.setIconHeight(16);
		statoAccettazioneMail.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoAccettazione = (String) record.getAttribute("statoAccettazioneMail");
				return getStatoConsolidamento(statoAccettazione);
			}
		});
		statoAccettazioneMail.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoAccettazioneMailString = (String) record.getAttribute("statoAccettazioneMail");
				return getHoverValueStatoAccettazioneMail(statoAccettazioneMailString);
			}
		});

		statoConsegnaMail = new ListGridField("statoConsegnaMail", I18NUtil.getMessages().posta_elettronica_list_statoConsegnaField());
		statoConsegnaMail.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoConsegnaMail.setType(ListGridFieldType.ICON);
		statoConsegnaMail.setAlign(Alignment.CENTER);
		statoConsegnaMail.setWrap(false);
		statoConsegnaMail.setWidth(30);
		statoConsegnaMail.setIconWidth(16);
		statoConsegnaMail.setIconHeight(16);
		statoConsegnaMail.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoConsegna = (String) record.getAttribute("statoConsegnaMail");
				return getStatoConsolidamento(statoConsegna);
			}
		});
		statoConsegnaMail.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoConsegnaMailString = (String) record.getAttribute("statoConsegnaMail");
				return getHoverValueStatoConsegnaMail(statoConsegnaMailString);
			}
		});

		setFields(id, idRichiesta, idEmailAss, codApplRich, tipoDocInv, xmlRichiesta, previewXmlRichiesta, statoRichiesta, errorMessage, dtSottRich,
				dtInvioEmail, statoInvioMail, statoAccettazioneMail, statoConsegnaMail);
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};

	@Override
	protected boolean showDetailButtonField() {
		
		return true;
	}

	@Override
	protected boolean showModifyButtonField() {
		
		return RichiesteDocViaPecLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		
		return RichiesteDocViaPecLayout.isAbilToDel();
	}

	@Override
	protected List<ControlListGridField> getButtonsFields() {

		List<ControlListGridField> buttonsFields = super.getButtonsFields();
		if (listaProcessiButtonField == null) {
			listaProcessiButtonField = buildShowDetailEmail();
		}
		buttonsFields.add(listaProcessiButtonField);
		return buttonsFields;
	}

	private ControlListGridField buildShowDetailEmail() {
		ControlListGridField dettaglioEmailButton = new ControlListGridField("buildShowDetailEmail");
		dettaglioEmailButton.setAttribute("custom", true);
		dettaglioEmailButton.setShowHover(true);
		dettaglioEmailButton.setCanReorder(false);
		dettaglioEmailButton.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record != null && record.getAttributeAsString("idEmailAss") != null && !"".equals(record.getAttributeAsString("idEmailAss"))) {
					return buildImgButtonHtml("buttons/preview_rich_doc_pec.png");
				}
				return null;
			}
		});
		dettaglioEmailButton.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record != null && record.getAttributeAsString("idEmailAss") != null && !"".equals(record.getAttributeAsString("idEmailAss"))) {
					return "Dettaglio email";
				}
				return null;
			}
		});
		dettaglioEmailButton.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				event.cancel();
				final Record record = event.getRecord();
				if (record != null && record.getAttributeAsString("idEmailAss") != null && !"".equals(record.getAttributeAsString("idEmailAss"))) {
					dettaglioEmail(record);
				}
			}
		});
		return dettaglioEmailButton;
	}

	private String getStatoConsolidamento(String stato) {
		if (stato != null && "OK".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/consegnata.png");
		} else if (stato != null && "KO".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/ko-red.png");
		} else if (stato != null && "IP".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/presunto_ok.png");
		} else if (stato != null && "W".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/ko-arancione.png");
		} else if (stato != null && "ND".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/stato_consegna.png");
		} else {
			return buildIconHtml("blank.png");
		}
	}

	private String getHoverValueStatoAccettazioneMail(String stato) {
		if (stato != null && "OK".equals(stato)) {
			return "accettata";
		} else if (stato != null && "KO".equals(stato)) {
			return "non accettata";
		} else if (stato != null && "IP".equals(stato)) {
			return "accettazione in corso";
		} else if (stato != null && "W".equals(stato)) {
			return "presunta mancata accettazione";
		} else if (stato != null && "ND".equals(stato)) {
			return "accettazione non valorizzata";
		} else {
			return "";
		}
	}

	private String getHoverValueStatoInvioMail(String stato) {
		if (stato != null && "OK".equals(stato)) {
			return "inviata";
		} else if (stato != null && "KO".equals(stato)) {
			return "invio fallito";
		} else if (stato != null && "IP".equals(stato)) {
			return "invio in corso";
		} else if (stato != null && "W".equals(stato)) {
			return "avvertimenti in invio";
		} else {
			return "";
		}
	}

	private String getHoverValueStatoConsegnaMail(String stato) {
		if (stato != null && "OK".equals(stato)) {
			return "consegnata";
		} else if (stato != null && "KO".equals(stato)) {
			return "consegna fallita";
		} else if (stato != null && "IP".equals(stato)) {
			return "consegna in corso";
		} else if (stato != null && "W".equals(stato)) {
			return "presunta mancata consegna";
		} else if (stato != null && "ND".equals(stato)) {
			return "consegna non valorizzata";
		} else {
			return "";
		}
	}

	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/

	private void dettaglioEmail(Record lRecord) {
		String idEmail = lRecord.getAttributeAsString("idEmailAss");
		DettaglioEmailWindow lDettaglioEmailWindow = new DettaglioEmailWindow(idEmail, null, getLayout());
	}

}
