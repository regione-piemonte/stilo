/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.auriga.ui.module.layout.client.AurigaLayout;

public class SoggettiList extends CustomList {

	private ListGridField idSoggettoField;
	private ListGridField idUoUtSvField;
	private ListGridField denominazioneField;
	private ListGridField codiceFiscaleField;
	private ListGridField partitaIvaField;
	private ListGridField dataNascitaIstituzioneField;
	private ListGridField dataCessazioneField;
	private ListGridField flgPersFisicaField;
	private ListGridField cognomeField;
	private ListGridField nomeField;
	private ListGridField titoloField;
	private ListGridField cittaField;
	private ListGridField tipoField;
	private ListGridField sottotipoField;
	private ListGridField condizioneGiuridicaField;
	private ListGridField causaleCessazioneField;
	private ListGridField comuneNascitaIstituzioneField;
	private ListGridField statoNascitaIstituzioneField;
	private ListGridField cittadinanzaField;
	private ListGridField flgAnnField;
	private ListGridField altreDenominazioniField;
	private ListGridField vecchieDenominazioniField;
	private ListGridField indirizzoField;
	private ListGridField indirizzoCompletoField;
	private ListGridField flgCertificatoField;
	private ListGridField estremiCertificazioneField;
	private ListGridField codiceOrigineField;
	private ListGridField codiceIpaField;
	private ListGridField tsInsField;
	private ListGridField uteInsField;
	private ListGridField tsLastUpdField;
	private ListGridField uteLastUpdField;
	private ListGridField flgDiSistemaField;
	private ListGridField flgValidoField;
	private ListGridField codiceRapidoField;
	private ListGridField codiceAmmInIpaField;
	private ListGridField codiceAooInIpaField;
	private ListGridField acronimoField;
	private ListGridField emailField;
	private ListGridField emailPecField;
	private ListGridField emailPeoField;
	private ListGridField flgEmailPecPeoField;
	private ListGridField telField;
	private ListGridField faxField;
	private ListGridField codTipoSoggIntField;
	private ListGridField flgInOrganigrammaField;
	private ListGridField flgSelXFinalitaField;
	private ListGridField scoreField;
	private ListGridField rubricaDiField;
	private ListGridField visibSottoUoField;
	private ListGridField gestibSottoUoField;

	public SoggettiList(String nomeEntita) {

		super(nomeEntita);

		idSoggettoField = new ListGridField("idSoggetto", I18NUtil.getMessages().soggetti_list_idSoggettoField_title());
		idSoggettoField.setHidden(true);

		idUoUtSvField = new ListGridField("idUoUtSv");
		idUoUtSvField.setHidden(true);
		idUoUtSvField.setCanHide(false);

		denominazioneField = new ListGridField("denominazione", I18NUtil.getMessages().soggetti_list_denominazioneField_title());
		denominazioneField.setAttribute("custom", true);
		denominazioneField.setCellAlign(Alignment.LEFT);

		codiceFiscaleField = new ListGridField("codiceFiscale", I18NUtil.getMessages().soggetti_list_codiceFiscaleField_title());

		partitaIvaField = new ListGridField("partitaIva", I18NUtil.getMessages().soggetti_list_partitaIvaField_title());

		dataNascitaIstituzioneField = new ListGridField("dataNascitaIstituzione", I18NUtil.getMessages().soggetti_list_dataNascitaIstituzioneField_title());
		dataNascitaIstituzioneField.setType(ListGridFieldType.DATE);
		dataNascitaIstituzioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);

		dataCessazioneField = new ListGridField("dataCessazione", I18NUtil.getMessages().soggetti_list_dataCessazioneField_title());
		dataCessazioneField.setType(ListGridFieldType.DATE);
		dataCessazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);

		flgPersFisicaField = new ListGridField("flgPersFisica", I18NUtil.getMessages().soggetti_list_flgPersFisicaField_title());
		flgPersFisicaField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgPersFisicaField.setType(ListGridFieldType.ICON);
		flgPersFisicaField.setWidth(30);
		flgPersFisicaField.setIconWidth(16);
		flgPersFisicaField.setIconHeight(16);
		Map<String, String> flgPersFisicaValueIcons = new HashMap<String, String>();
		flgPersFisicaValueIcons.put("1", "anagrafiche/soggetti/flgPersFisica/persona_fisica.png");
		flgPersFisicaValueIcons.put("0", "anagrafiche/soggetti/flgPersFisica/persona_giuridica.png");
		flgPersFisicaValueIcons.put("", "warning.png");
		flgPersFisicaField.setValueIcons(flgPersFisicaValueIcons);
		Map<String, String> flgPersFisicaValueHovers = new HashMap<String, String>();
		flgPersFisicaValueHovers.put("1", I18NUtil.getMessages().soggetti_flgPersFisica_1_value());
		flgPersFisicaValueHovers.put("0", I18NUtil.getMessages().soggetti_flgPersFisica_0_value());
		flgPersFisicaValueHovers.put("", I18NUtil.getMessages().soggetti_flgPersFisica_NULL_value());
		flgPersFisicaField.setAttribute("valueHovers", flgPersFisicaValueHovers);

		cognomeField = new ListGridField("cognome", I18NUtil.getMessages().soggetti_list_cognomeField_title());

		nomeField = new ListGridField("nome", I18NUtil.getMessages().soggetti_list_nomeField_title());

		titoloField = new ListGridField("titolo", I18NUtil.getMessages().soggetti_list_titoloField_title());

		cittaField = new ListGridField("citta", I18NUtil.getMessages().soggetti_list_cittaField_title());

		tipoField = new ListGridField("tipo", I18NUtil.getMessages().soggetti_list_tipoField_title());
		tipoField.setAttribute("custom", true);
		tipoField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if (value != null) {
					if ("#APA".equals(value))
						return I18NUtil.getMessages().soggetti_tipo_APA_value();
					if ("#IAMM".equals(value)) {
						String acronimoEnte = AurigaLayout.getParametroDB("ACRONIMO_ENTE");
						return acronimoEnte != null && !"".equals(acronimoEnte) ? acronimoEnte : I18NUtil.getMessages().soggetti_tipo_IAMM_value();
					}
					if ("UO;UOI".equals(value))
						return I18NUtil.getMessages().soggetti_tipo_UOUOI_value();
					if ("UP".equals(value))
						return I18NUtil.getMessages().soggetti_tipo_UP_value();
					if ("#AF".equals(value))
						return I18NUtil.getMessages().soggetti_tipo_AF_value();
					if ("#AG".equals(value))
						return I18NUtil.getMessages().soggetti_tipo_AG_value();
				}
				return null;
			}
		});

		sottotipoField = new ListGridField("sottotipo", I18NUtil.getMessages().soggetti_list_sottotipoField_title());

		condizioneGiuridicaField = new ListGridField("condizioneGiuridica", I18NUtil.getMessages().soggetti_list_condizioneGiuridicaField_title());

		causaleCessazioneField = new ListGridField("causaleCessazione", I18NUtil.getMessages().soggetti_list_causaleCessazioneField_title());

		comuneNascitaIstituzioneField = new ListGridField("comuneNascitaIstituzione",
				I18NUtil.getMessages().soggetti_list_comuneNascitaIstituzioneField_title());

		statoNascitaIstituzioneField = new ListGridField("statoNascitaIstituzione", I18NUtil.getMessages().soggetti_list_statoNascitaIstituzioneField_title());

		cittadinanzaField = new ListGridField("cittadinanza", I18NUtil.getMessages().soggetti_list_cittadinanzaField_title());

		flgAnnField = new ListGridField("flgAnn", I18NUtil.getMessages().soggetti_list_flgAnnField_title(), 50);
		flgAnnField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgAnnField.setType(ListGridFieldType.ICON);
		flgAnnField.setWidth(30);
		flgAnnField.setIconWidth(16);
		flgAnnField.setIconHeight(16);
		Map<String, String> flgAnnValueIcons = new HashMap<String, String>();
		flgAnnValueIcons.put("1", "ko.png");
		flgAnnValueIcons.put("0", "blank.png");
		flgAnnValueIcons.put("", "blank.png");
		flgAnnField.setValueIcons(flgAnnValueIcons);
		flgAnnField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if ("1".equals(record.getAttribute("flgAnn"))) {
					return I18NUtil.getMessages().soggetti_flgAnn_1_value();
				}
				return null;
			}
		});

		altreDenominazioniField = new ListGridField("altreDenominazioni", I18NUtil.getMessages().soggetti_list_altreDenominazioniField_title());

		vecchieDenominazioniField = new ListGridField("vecchieDenominazioni", I18NUtil.getMessages().soggetti_list_vecchieDenominazioniField_title());

		indirizzoField = new ListGridField("indirizzo", I18NUtil.getMessages().soggetti_list_indirizzoField_title());

		indirizzoCompletoField = new ListGridField("indirizzoCompleto", I18NUtil.getMessages().soggetti_list_indirizzoCompletoField_title());
		indirizzoCompletoField.setAttribute("custom", true);
		indirizzoCompletoField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String indirizzoCompleto = value != null && !"".equals(String.valueOf(value)) ? value.toString() : null;
				if (indirizzoCompleto != null && !"".equals(indirizzoCompleto)) {
					StringSplitterClient st = new StringSplitterClient(indirizzoCompleto, "|*|");
					try {
						String descrViaTopo = st.getTokens()[0] != null ? st.getTokens()[0] : "";// 1) descrizione Via/toponimo
						String cap = st.getTokens()[2] != null ? st.getTokens()[2] : "";// 3) CAP
						String comuneCitta = st.getTokens()[3] != null ? st.getTokens()[3] : "";// 4) Comune / Città
						String targaProvincia = st.getTokens()[4] != null ? st.getTokens()[4] : "";// 5) Targa provincia
						String codViaInToponomastica = st.getTokens()[6] != null ? st.getTokens()[6] : "";// 7) Cod. via se in toponomastica
						String tipoToponimo = st.getTokens()[7] != null ? st.getTokens()[7] : "";// 8) Tipo toponimo
						String codIstat = st.getTokens()[8] != null ? st.getTokens()[8] : "";// 9) Cod. ISTAT comune
						String stato = st.getTokens()[9] != null ? st.getTokens()[9] : "";// 10) Cod. ISTAT stato
						String nomeStato = st.getTokens()[10] != null ? st.getTokens()[10] : "";// 11) Nome stato
						String civico = st.getTokens()[11] != null ? st.getTokens()[11] : "";// 12) Civico prima parte
						String appendice = st.getTokens()[12] != null ? st.getTokens()[12] : "";// 13)Appendice del civico
						String zona = st.getTokens()[13] != null ? st.getTokens()[13] : "";// 14)Zona
						String localitaFrazione = st.getTokens()[14] != null ? st.getTokens()[14] : "";// 15)Località/frazione
						String altriDati = st.getTokens()[15] != null ? st.getTokens()[15] : "";// 16)Complemento/dati aggiuntivi indirizzo
						String res = descrViaTopo + " " + cap + " " + comuneCitta + " " + targaProvincia + " " + codViaInToponomastica + " " + tipoToponimo
								+ " " + " " + codIstat + " " + stato + " " + nomeStato + " " + civico + " " + appendice + " " + zona + " " + localitaFrazione
								+ " " + altriDati;
						return res;
					} catch (Exception e) {
					}
				}

				if (indirizzoCompleto != null) {
					String res = indirizzoCompleto.replaceAll("\\|", " ").replaceAll("\\*", "");
					return res;
				}
				return null;
			}
		});

		flgCertificatoField = new ListGridField("flgCertificato", I18NUtil.getMessages().soggetti_list_flgCertificatoField_title());
		flgCertificatoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgCertificatoField.setType(ListGridFieldType.ICON);
		flgCertificatoField.setWidth(30);
		flgCertificatoField.setIconWidth(16);
		flgCertificatoField.setIconHeight(16);
		Map<String, String> flgCertificatoValueIcons = new HashMap<String, String>();
		flgCertificatoValueIcons.put("1", "coccarda.png");
		flgCertificatoValueIcons.put("0", "blank.png");
		flgCertificatoValueIcons.put("", "blank.png");
		flgCertificatoField.setValueIcons(flgCertificatoValueIcons);
		flgCertificatoField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if ("1".equals(record.getAttribute("flgCertificato"))) {
					return I18NUtil.getMessages().soggetti_flgCertificato_1_value() + ": " + record.getAttributeAsString("estremiCertificazione");
				}
				return null;
			}
		});

		estremiCertificazioneField = new ListGridField("estremiCertificazione", I18NUtil.getMessages().soggetti_list_estremiCertificazioneField_title());
		estremiCertificazioneField.setHidden(true);
		estremiCertificazioneField.setCanHide(false);

		codiceOrigineField = new ListGridField("codiceOrigine", I18NUtil.getMessages().soggetti_list_codiceOrigineField_title());

		codiceIpaField = new ListGridField("codiceIpa", I18NUtil.getMessages().soggetti_list_codiceIpaField_title());

		tsInsField = new ListGridField("tsIns", I18NUtil.getMessages().soggetti_list_tsInsField_title());
		tsInsField.setType(ListGridFieldType.DATE);
		tsInsField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		uteInsField = new ListGridField("uteIns", I18NUtil.getMessages().soggetti_list_uteInsField_title());

		tsLastUpdField = new ListGridField("tsLastUpd", I18NUtil.getMessages().soggetti_list_tsLastUpdField_title());
		tsLastUpdField.setType(ListGridFieldType.DATE);
		tsLastUpdField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		uteLastUpdField = new ListGridField("uteLastUpd", I18NUtil.getMessages().soggetti_list_uteLastUpdField_title());

		flgDiSistemaField = new ListGridField("flgDiSistema", I18NUtil.getMessages().soggetti_list_flgDiSistemaField_title());
		flgDiSistemaField.setType(ListGridFieldType.ICON);
		flgDiSistemaField.setWidth(30);
		flgDiSistemaField.setIconWidth(16);
		flgDiSistemaField.setIconHeight(16);
		Map<String, String> flgDiSistemaValueIcons = new HashMap<String, String>();
		flgDiSistemaValueIcons.put("1", "lock.png");
		flgDiSistemaValueIcons.put("0", "blank.png");
		flgDiSistemaValueIcons.put("", "blank.png");
		flgDiSistemaField.setValueIcons(flgDiSistemaValueIcons);
		flgDiSistemaField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if ("1".equals(record.getAttribute("flgDiSistema"))) {
					return I18NUtil.getMessages().soggetti_flgDiSistema_1_value();
				}
				return null;
			}
		});

		flgValidoField = new ListGridField("flgValido", I18NUtil.getMessages().soggetti_list_flgValidoField_title());
		flgValidoField.setType(ListGridFieldType.ICON);
		flgValidoField.setWidth(30);
		flgValidoField.setIconWidth(16);
		flgValidoField.setIconHeight(16);
		Map<String, String> flgValidoValueIcons = new HashMap<String, String>();
		flgValidoValueIcons.put("1", "ok.png");
		flgValidoValueIcons.put("0", "blank.png");
		flgValidoValueIcons.put("", "blank.png");
		flgValidoField.setValueIcons(flgValidoValueIcons);
		flgValidoField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if ("1".equals(record.getAttribute("flgValido"))) {
					return I18NUtil.getMessages().soggetti_flgValido_1_value();
				}
				return null;
			}
		});

		codiceRapidoField = new ListGridField("codiceRapido", I18NUtil.getMessages().soggetti_list_codiceRapidoField_title());

		codiceAmmInIpaField = new ListGridField("codiceAmmInIpa", I18NUtil.getMessages().soggetti_list_codiceAmmInIpaField_title());

		codiceAooInIpaField = new ListGridField("codiceAooInIpa", I18NUtil.getMessages().soggetti_list_codiceAooInIpaField_title());

		acronimoField = new ListGridField("acronimo", I18NUtil.getMessages().soggetti_list_acronimoField_title());

		emailField = new ListGridField("email", I18NUtil.getMessages().soggetti_list_emailField_title());

		emailPecField = new ListGridField("emailPec", I18NUtil.getMessages().soggetti_list_emailPecField_title());
		
		emailPeoField = new ListGridField("emailPeo", I18NUtil.getMessages().soggetti_list_emailPeoField_title());
		
		flgEmailPecPeoField = new ListGridField("flgEmailPecPeo", I18NUtil.getMessages().soggetti_list_flgEmailPecPeoField_title());
		flgEmailPecPeoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgEmailPecPeoField.setType(ListGridFieldType.ICON);
		flgEmailPecPeoField.setWidth(30);
		flgEmailPecPeoField.setIconWidth(16);
		flgEmailPecPeoField.setIconHeight(16);
		Map<String, String> flgEmailPecPeoValueIcons = new HashMap<String, String>();
		flgEmailPecPeoValueIcons.put("PEC", "anagrafiche/soggetti/flgEmailPecPeo/PEC.png");
		flgEmailPecPeoValueIcons.put("PEO", "anagrafiche/soggetti/flgEmailPecPeo/PEO.png");
		flgEmailPecPeoValueIcons.put("", "blank.png");
		flgEmailPecPeoField.setValueIcons(flgEmailPecPeoValueIcons);
		Map<String, String> flgEmailPecPeoValueHovers = new HashMap<String, String>();
		flgEmailPecPeoValueHovers.put("PEC", I18NUtil.getMessages().soggetti_flgEmailPecPeo_PEC_value());
		flgEmailPecPeoValueHovers.put("PEO", I18NUtil.getMessages().soggetti_flgEmailPecPeo_PEO_value());
		flgEmailPecPeoField.setAttribute("valueHovers", flgEmailPecPeoValueHovers);

		telField = new ListGridField("tel", I18NUtil.getMessages().soggetti_list_telField_title());

		faxField = new ListGridField("fax", I18NUtil.getMessages().soggetti_list_faxField_title());

		codTipoSoggIntField = new ListGridField("codTipoSoggInt", I18NUtil.getMessages().soggetti_list_codTipoSoggIntField_title());
		codTipoSoggIntField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		codTipoSoggIntField.setType(ListGridFieldType.ICON);
		codTipoSoggIntField.setWidth(30);
		codTipoSoggIntField.setIconWidth(16);
		codTipoSoggIntField.setIconHeight(16);
		Map<String, String> codTipoSoggIntValueIcons = new HashMap<String, String>();
		codTipoSoggIntValueIcons.put("UP", "anagrafiche/soggetti/codTipoSoggInt/UP.png");
		codTipoSoggIntValueIcons.put("AOOI", "anagrafiche/soggetti/codTipoSoggInt/AOOI.png");
		codTipoSoggIntValueIcons.put("UOI", "anagrafiche/soggetti/codTipoSoggInt/UOI.png");
		codTipoSoggIntField.setValueIcons(codTipoSoggIntValueIcons);
		Map<String, String> codTipoSoggIntValueHovers = new HashMap<String, String>();
		codTipoSoggIntValueHovers.put("UP", I18NUtil.getMessages().soggetti_codTipoSoggInt_UP_value());
		codTipoSoggIntValueHovers.put("AOOI", I18NUtil.getMessages().soggetti_codTipoSoggInt_AOOI_value());
		codTipoSoggIntValueHovers.put("UOI", I18NUtil.getMessages().soggetti_codTipoSoggInt_UOI_value());
		codTipoSoggIntField.setAttribute("valueHovers", codTipoSoggIntValueHovers);

		flgInOrganigrammaField = new ListGridField("flgInOrganigramma", I18NUtil.getMessages().soggetti_list_flgInOrganigrammaField_title());
		flgInOrganigrammaField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgInOrganigrammaField.setType(ListGridFieldType.ICON);
		flgInOrganigrammaField.setWidth(30);
		flgInOrganigrammaField.setIconWidth(16);
		flgInOrganigrammaField.setIconHeight(16);
		Map<String, String> flgInOrganigrammaValueIcons = new HashMap<String, String>();
		flgInOrganigrammaValueIcons.put("1", "anagrafiche/soggetti/soggInOrg.png");
		flgInOrganigrammaValueIcons.put("0", "blank.png");
		flgInOrganigrammaValueIcons.put("", "blank.png");
		flgInOrganigrammaField.setValueIcons(flgInOrganigrammaValueIcons);
		flgInOrganigrammaField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if ("1".equals(record.getAttribute("flgInOrganigramma"))) {
					return I18NUtil.getMessages().soggetti_list_flgInOrganigrammaField_title();
				}
				return null;
			}
		});

		flgSelXFinalitaField = new ListGridField("flgSelXFinalita");
		flgSelXFinalitaField.setHidden(true);
		flgSelXFinalitaField.setCanHide(false);

		scoreField = new ListGridField("score", I18NUtil.getMessages().soggetti_list_scoreField_title());
		scoreField.setType(ListGridFieldType.INTEGER);
		scoreField.setSortByDisplayField(false);
		scoreField.setCellFormatter(new CellFormatter() {

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

		if (SoggettiLayout.isPartizionamentoRubricaAbilitato()) {
			rubricaDiField = new ListGridField("rubricaDi", I18NUtil.getMessages().soggetti_list_rubricaDiField_title());

			visibSottoUoField = new ListGridField("flgVisibSottoUo", I18NUtil.getMessages().soggetti_list_visibSottoUoField_title());
			visibSottoUoField.setType(ListGridFieldType.ICON);
			visibSottoUoField.setWidth(30);
			visibSottoUoField.setIconWidth(16);
			visibSottoUoField.setIconHeight(16);
			Map<String, String> visibSottoUoFieldIcons = new HashMap<String, String>();
			visibSottoUoFieldIcons.put("1", "ok.png");
			visibSottoUoFieldIcons.put("0", "blank.png");
			visibSottoUoFieldIcons.put("", "blank.png");
			visibSottoUoField.setValueIcons(visibSottoUoFieldIcons);

			gestibSottoUoField = new ListGridField("flgGestibSottoUo", I18NUtil.getMessages().soggetti_list_gestibSottoUoField_title());
			gestibSottoUoField.setType(ListGridFieldType.ICON);
			gestibSottoUoField.setWidth(30);
			gestibSottoUoField.setIconWidth(16);
			gestibSottoUoField.setIconHeight(16);
			Map<String, String> gestibSottoUoFieldIcons = new HashMap<String, String>();
			gestibSottoUoFieldIcons.put("1", "ok.png");
			gestibSottoUoFieldIcons.put("0", "blank.png");
			gestibSottoUoFieldIcons.put("", "blank.png");
			gestibSottoUoField.setValueIcons(gestibSottoUoFieldIcons);

			setFields(new ListGridField[] { idSoggettoField, denominazioneField, codiceFiscaleField, partitaIvaField, dataNascitaIstituzioneField,
					dataCessazioneField, flgPersFisicaField, cognomeField, nomeField, titoloField, tipoField, sottotipoField, condizioneGiuridicaField,
					causaleCessazioneField, comuneNascitaIstituzioneField, statoNascitaIstituzioneField, cittadinanzaField, flgAnnField,
					altreDenominazioniField, vecchieDenominazioniField, indirizzoField, indirizzoCompletoField, flgCertificatoField, estremiCertificazioneField,
					codiceOrigineField, codiceIpaField, tsInsField, uteInsField, tsLastUpdField, uteLastUpdField, flgDiSistemaField, flgValidoField,
					codiceRapidoField, codiceAmmInIpaField, codiceAooInIpaField, acronimoField, emailField, emailPecField, emailPeoField, flgEmailPecPeoField, telField,
					faxField, codTipoSoggIntField, flgInOrganigrammaField, flgSelXFinalitaField, scoreField, cittaField, rubricaDiField, visibSottoUoField,
					gestibSottoUoField });

		} else {

			setFields(new ListGridField[] { idSoggettoField, denominazioneField, codiceFiscaleField, partitaIvaField, dataNascitaIstituzioneField,
					dataCessazioneField, flgPersFisicaField, cognomeField, nomeField, titoloField, tipoField, sottotipoField, condizioneGiuridicaField,
					causaleCessazioneField, comuneNascitaIstituzioneField, statoNascitaIstituzioneField, cittadinanzaField, flgAnnField,
					altreDenominazioniField, vecchieDenominazioniField, indirizzoField, indirizzoCompletoField, flgCertificatoField, estremiCertificazioneField,
					codiceOrigineField, codiceIpaField, tsInsField, uteInsField, tsLastUpdField, uteLastUpdField, flgDiSistemaField, flgValidoField,
					codiceRapidoField, codiceAmmInIpaField, codiceAooInIpaField, acronimoField, emailField, emailPecField, emailPeoField, flgEmailPecPeoField, telField,
					faxField, codTipoSoggIntField, flgInOrganigrammaField, flgSelXFinalitaField, scoreField, cittaField });

		}
		
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			setArrowKeyAction("focus");
			
			addBodyKeyPressHandler(new BodyKeyPressHandler() {
	            
	            @Override
	            public void onBodyKeyPress(BodyKeyPressEvent event) {
	                if (EventHandler.getKey().equalsIgnoreCase("Enter") == true) {
						Integer focusRow2 = getFocusRow();
						ListGridRecord record = getRecord(focusRow2);
	//					manageDetailButtonClick(record);
						if(isRecordSelezionabileForLookup(record)) {
							manageLookupButtonClick(record);
						}
	//                    System.out.println("ENTER PRESSED !!!!" + listGrid.getSelectedRecord());
	                }
	            }
	        });				
		}
	}

	@Override
	protected int getButtonsFieldWidth() {
		return 100;
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
			scoreField.setHidden(false);
		} else {
			scoreField.setHidden(true);
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
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		}, new DSRequest());
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

	// Definisco i bottoni DETTAGLIO/MODIFICA/CANCELLA/SELEZIONA
	@Override
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {

		Canvas lCanvasReturn = null;

		if (fieldName.equals("buttons")) {

			ImgButton detailButton = buildDetailButton(record);
			ImgButton modifyButton = buildModifyButton(record);
			ImgButton deleteButton = buildDeleteButton(record);
			ImgButton lookupButton = buildLookupButton(record);

			if (!isRecordAbilToMod(record)) {
				modifyButton.disable();
			}

			if (!isRecordAbilToDel(record)) {
				deleteButton.disable();
			}

			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);
			recordCanvas.setMembersMargin(7);

			recordCanvas.addMember(detailButton);
			recordCanvas.addMember(modifyButton);
			recordCanvas.addMember(deleteButton);

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
	protected void manageDeleteButtonClick(final ListGridRecord record) {
		
		final boolean flgDiSistema = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		SC.ask(flgDiSistema ? I18NUtil.getMessages().soggetti_annullamentoLogicoAsk_message() : I18NUtil.getMessages().soggetti_eliminazioneFisicaAsk_message(),
				new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						
						if (value) {
							removeData(record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout.getTipoEstremiRecord(record)), "",
												MessageType.INFO));
										layout.hideDetail(true);
									}
								}
							});
						}
					}
				});
	}

	@Override
	protected boolean isRecordSelezionabileForLookup(ListGridRecord record) {
		return record.getAttributeAsBoolean("flgSelXFinalita");
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	}

	@Override
	protected boolean showDetailButtonField() {
		return true;
	}

	@Override
	protected boolean showModifyButtonField() {
		return SoggettiLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return SoggettiLayout.isAbilToDel();
	}

	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean flgDiSistema = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		final String tipo = (record.getAttribute("tipo")!=null ? record.getAttribute("tipo") : "");
		
		// Se il soggetto e' #APA O #APA;PA o #APA;AOO;AOOE o #APA;UO;UOE e ho uno di questi privilegi : GRS/SPA;M o GRS/SPA/E;I o GRS/SPA/E;FC => lo posso modificare
		return (SoggettiLayout.isRecordAbilToMod(flgDiSistema) || (tipo.startsWith("#APA") && ( SoggettiLayout.isRecordAbilToModPA(flgDiSistema) || SoggettiLayout.isRecordAbilToModPAE(flgDiSistema))  ));
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean flgDiSistema = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		final boolean flgValido = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("1");
		final String tipo = (record.getAttribute("tipo")!=null ? record.getAttribute("tipo") : "");

		// Se il soggetto e' #APA O #APA;PA o #APA;AOO;AOOE o #APA;UO;UOE e ho uno di questi privilegi : GRS/SPA;FC => lo posso eliminare 
		return SoggettiLayout.isRecordAbilToDel(flgValido, flgDiSistema) || (tipo.startsWith("#APA") && SoggettiLayout.isRecordAbilToDelPA(flgValido, flgDiSistema) );
	}
	
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
}
