/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
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
import it.eng.auriga.ui.module.layout.client.classifiche.ClassificheWindow;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.registriNumerazione.RegistriNumerazioneWindow;
import it.eng.auriga.ui.module.layout.client.tipoFascicoliAggr.TipologieFascicoliWindows;
import it.eng.auriga.ui.module.layout.client.tipologieDocumentali.TipologieDocumentaliWindow;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomAdvancedTreeLayout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.GenericCallback;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu.NavigationContextMenuFrom;

public class OrganigrammaList extends CustomList {

	private ListGridField idUoSvUt;
	private ListGridField idUo;
	private ListGridField idUser;
	private ListGridField folderUp;
	private ListGridField icona;
	private ListGridField tipo;
	private ListGridField nome;
	private ListGridField denominazioneEstesa;
	private ListGridField tsInizio;
	private ListGridField tsFine;
	private ListGridField codRapidoUo;
	private ListGridField codRapidoUoXOrd;
	private ListGridField tipoRelUtenteUo;
	private ListGridField protocollista;
	private ListGridField flgSelXFinalita;
	private ListGridField flgSelXAssegnazione;
	private ListGridField idUoSup;
	private ListGridField denomUoSup;
	private ListGridField score;
	private ListGridField ruolo;
	private ListGridField username;
	private ListGridField titolo;
	private ListGridField qualifica;
	private ListGridField competenzefunzioni;
	private ListGridField dtPubblicazioneIpa;
	private ListGridField flgPubblicataSuIpa;
	private ListGridField indirizzo;
	private ListGridField telefono;
	private ListGridField email;
	private ListGridField fax;
	private ListGridField profilo;
	private ListGridField subProfili;
	private ListGridField flgPuntoProtocollo;
	private ListGridField flgPuntoRaccoltaEmail;
	private ListGridField nomePostazioneInPrecedenza;
	private ListGridField collegataAPuntoProt;
	private ListGridField flgInibitoInvioCC;
	private ListGridField flgInibitaAssegnazione;
	private ListGridField flgInibitaAssegnazioneEmail;
	private ListGridField abilitaUoProtEntrata;
	private ListGridField abilitaUoProtUscita;
	private ListGridField abilitaUoProtUscitaFull;
	private ListGridField abilitaUoAssRiservatezza;
	private ListGridField abilitaUoGestMulte;
	private ListGridField abilitaUoGestContratti;
	private ListGridField tsStrutturaDaCessareDal; 
	private ListGridField origineCreazione;
	
	public OrganigrammaList(final String nomeEntita) {

		super(nomeEntita);

		folderUp = new ControlListGridField("folderUp");
		folderUp.setAttribute("custom", true);
		folderUp.setShowHover(true);
		folderUp.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("idUoSup") != null && !"".equals(record.getAttribute("idUoSup")) && !isUoCorrente(record.getAttribute("idUoSup"))) {
					return buildImgButtonHtml("buttons/folderUp.png");
				}
				return null;
			}
		});
		folderUp.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("idUoSup") != null && !"".equals(record.getAttribute("idUoSup")) && !isUoCorrente(record.getAttribute("idUoSup"))) {
					return I18NUtil.getMessages().organigramma_list_folderUpButton_prompt() + " " + record.getAttribute("denomUoSup");
				}
				return null;
			}
		});
		folderUp.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				Record record = event.getRecord();
				if (record.getAttribute("idUoSup") != null && !"".equals(record.getAttribute("idUoSup")) && !isUoCorrente(record.getAttribute("idUoSup"))) {
					if (layout instanceof OrganigrammaLayout) {
						((OrganigrammaLayout) layout).esploraFromList((record.getAttribute("idUoSup")));
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
				String tipo = record.getAttributeAsString("tipo");
				int pos = tipo.indexOf("_");
				if (!tipo.equals("SV_A") && !tipo.startsWith("UO") && pos != -1) {
					tipo = tipo.substring(0, pos);
				}
				if (tipo.startsWith("UO")) {
					return buildImgButtonHtml(nomeEntita + "/tipo/" + tipo + ".png");
				}
				return buildIconHtml(nomeEntita + "/tipo/" + tipo + ".png");
			}
		});
		icona.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String tipo = record.getAttributeAsString("tipo");
				int pos = tipo.indexOf("_");
				if (!tipo.equals("SV_A") && !tipo.startsWith("UO") && pos != -1) {
					tipo = tipo.substring(0, pos);
				}
				if (tipo.startsWith("UO")) {
					String descrTipo = record.getAttributeAsString("descrTipo");
					if (descrTipo != null && !"".equals(descrTipo)) {
						return I18NUtil.getMessages().organigramma_list_iconaFolderButton_prompt() + " " + descrTipo;
					} else {
						return I18NUtil.getMessages().organigramma_list_iconaFolderButton_prompt();
					}
				}
				if ("SV".equalsIgnoreCase(tipo)) {
					return I18NUtil.getMessages().organigramma_list_iconaSV_prompt();
				} else if ("UT".equalsIgnoreCase(tipo)) {
					return I18NUtil.getMessages().organigramma_list_iconaUT_prompt();
				}
				return null;
			}
		});
		icona.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				try {
					Record record = event.getRecord();
					String tipo = record.getAttributeAsString("tipo");
					int pos = tipo.indexOf("_");
					if (!tipo.equals("SV_A") && !tipo.startsWith("UO") && pos != -1) {
						tipo = tipo.substring(0, pos);
					}
					if (tipo.startsWith("UO")) {
						if (layout instanceof OrganigrammaLayout) {
							((OrganigrammaLayout) layout).esploraFromList((record.getAttribute("idUoSvUt")));
						}
					}
				} catch(Exception e) {}
			}
		});

		idUoSvUt = new ListGridField("idUoSvUt");
		idUoSvUt.setHidden(true);
		idUoSvUt.setCanHide(false);

		idUo = new ListGridField("idUo");
		idUo.setHidden(true);
		idUo.setCanHide(false);

		idUser = new ListGridField("idUser");
		idUser.setHidden(true);
		idUser.setCanHide(false);

		tipo = new ListGridField("tipo");
		tipo.setHidden(true);
		tipo.setCanHide(false);

		nome = new ListGridField("nome", I18NUtil.getMessages().organigramma_list_nomeField_title());

		denominazioneEstesa = new ListGridField("denominazioneEstesa", I18NUtil.getMessages().organigramma_list_denominazioneEstesaField_title());

		tsInizio = new ListGridField("tsInizio", I18NUtil.getMessages().organigramma_list_tsInizioField_title());
		tsInizio.setType(ListGridFieldType.DATE);
		tsInizio.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		tsInizio.setWrap(false);

		tsFine = new ListGridField("tsFine", I18NUtil.getMessages().organigramma_list_tsFineField_title());
		tsFine.setType(ListGridFieldType.DATE);
		tsFine.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		tsFine.setWrap(false);

		codRapidoUo = new ListGridField("codRapidoUo", I18NUtil.getMessages().organigramma_list_codRapidoUoField_title());
		codRapidoUo.setHidden(true);
		codRapidoUo.setCanHide(false);

		codRapidoUoXOrd = new ListGridField("codRapidoUoXOrd", I18NUtil.getMessages().organigramma_list_codRapidoUoField_title());
		codRapidoUoXOrd.setDisplayField("codRapidoUo");
		codRapidoUoXOrd.setSortByDisplayField(false);

		tipoRelUtenteUo = new ListGridField("tipoRelUtenteUo", I18NUtil.getMessages().organigramma_list_tipoRelUtenteUoField_title());
		tipoRelUtenteUo.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		tipoRelUtenteUo.setWrap(false);
		tipoRelUtenteUo.setType(ListGridFieldType.ICON);
		tipoRelUtenteUo.setWidth(30);
		tipoRelUtenteUo.setIconWidth(16);
		tipoRelUtenteUo.setIconHeight(16);
		Map<String, String> tipoRelUtenteUoValueIcons = new HashMap<String, String>();
		tipoRelUtenteUoValueIcons.put("A", "organigramma/tipoRelazioneUtenteUO/A.png");
		tipoRelUtenteUoValueIcons.put("D", "organigramma/tipoRelazioneUtenteUO/D.png");
		tipoRelUtenteUoValueIcons.put("L", "organigramma/tipoRelazioneUtenteUO/L.png");
		tipoRelUtenteUoValueIcons.put("", "blank.png");
		tipoRelUtenteUo.setValueIcons(tipoRelUtenteUoValueIcons);
		Map<String, String> tipoRelUtenteUoValueHovers = new HashMap<String, String>();
		tipoRelUtenteUoValueHovers.put("A", I18NUtil.getMessages().organigramma_list_tipoRelUtenteUoField_A_prompt());
		tipoRelUtenteUoValueHovers.put("D", I18NUtil.getMessages().organigramma_list_tipoRelUtenteUoField_D_prompt());
		tipoRelUtenteUoValueHovers.put("L", I18NUtil.getMessages().organigramma_list_tipoRelUtenteUoField_L_prompt());
		tipoRelUtenteUo.setAttribute("valueHovers", tipoRelUtenteUoValueHovers);
		
		protocollista = new ListGridField("flgProtocollista", "Protocollista");
		protocollista.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		protocollista.setWrap(false);
		protocollista.setType(ListGridFieldType.ICON);
		protocollista.setWidth(30);
		protocollista.setIconWidth(16);
		protocollista.setIconHeight(16);
		Map<String, String> protocollistaIcons = new HashMap<String, String>();
		protocollistaIcons.put("true", "organigramma/protocollista.png");
		protocollistaIcons.put("", "blank.png");
		protocollista.setValueIcons(protocollistaIcons);
		Map<String, String> protocollistaValueHovers = new HashMap<String, String>();
		protocollistaValueHovers.put("true", "Protocollista");
		protocollista.setAttribute("valueHovers", protocollistaValueHovers);
		
		idUoSup = new ListGridField("idUoSup");
		idUoSup.setHidden(true);
		idUoSup.setCanHide(false);

		denomUoSup = new ListGridField("denomUoSup");
		denomUoSup.setHidden(true);
		denomUoSup.setCanHide(false);

		flgSelXFinalita = new ListGridField("flgSelXFinalita");
		flgSelXFinalita.setHidden(true);
		flgSelXFinalita.setCanHide(false);
		
		flgSelXAssegnazione = new ListGridField("flgSelXAssegnazione");
		flgSelXAssegnazione.setHidden(true);
		flgSelXAssegnazione.setCanHide(false);

		score = new ListGridField("score", I18NUtil.getMessages().organigramma_list_scoreField_title());
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

		ruolo = new ListGridField("ruolo", I18NUtil.getMessages().organigramma_list_ruoloField_title());

		username = new ListGridField("username", I18NUtil.getMessages().organigramma_list_usernameField_title());

		titolo = new ListGridField("titolo", I18NUtil.getMessages().organigramma_list_titoloField_title());

		qualifica = new ListGridField("qualifica", I18NUtil.getMessages().organigramma_list_qualificaField_title());

		competenzefunzioni = new ListGridField("competenzefunzioni", I18NUtil.getMessages().organigramma_list_competenzefunzioniField_title());

		indirizzo = new ListGridField("indirizzo", I18NUtil.getMessages().organigramma_list_indirizzoField_title());

		telefono = new ListGridField("telefono", I18NUtil.getMessages().organigramma_list_telefonoField_title());

		email = new ListGridField("email", I18NUtil.getMessages().organigramma_list_emailField_title());

		fax = new ListGridField("fax", I18NUtil.getMessages().organigramma_list_faxField_title());

		profilo = new ListGridField("profilo", I18NUtil.getMessages().organigramma_list_profiloField_title());
		
		subProfili = new ListGridField("subProfili", I18NUtil.getMessages().organigramma_list_subProfiliField_title());
		

		dtPubblicazioneIpa = new ListGridField("dtPubblicazioneIpa");
		dtPubblicazioneIpa.setType(ListGridFieldType.DATE);
		dtPubblicazioneIpa.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dtPubblicazioneIpa.setWrap(false);

		flgPubblicataSuIpa = new ListGridField("flgPubblicataSuIpa", I18NUtil.getMessages().organigramma_list_pubblicataSuIpaField_title());
		flgPubblicataSuIpa.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgPubblicataSuIpa.setWrap(false);
		flgPubblicataSuIpa.setType(ListGridFieldType.ICON);
		flgPubblicataSuIpa.setWidth(30);
		flgPubblicataSuIpa.setIconWidth(16);
		flgPubblicataSuIpa.setIconHeight(16);
		Map<String, String> flgPubblicataSuIpaIcons = new HashMap<String, String>();
		flgPubblicataSuIpaIcons.put("1", "ipa.png");
		flgPubblicataSuIpaIcons.put("", "blank.png");
		flgPubblicataSuIpa.setValueIcons(flgPubblicataSuIpaIcons);
		flgPubblicataSuIpa.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("1".equals(record.getAttribute("flgPubblicataSuIpa"))) {
					String dtPubblicazione = "";
					if (record.getAttribute("dtPubblicazioneIpa") != null && !record.getAttribute("dtPubblicazioneIpa").equalsIgnoreCase("")) {
						dtPubblicazione = record.getAttributeAsString("dtPubblicazioneIpa").substring(0, 10);
						return I18NUtil.getMessages().organigramma_list_flgPubblicataSuIpaAlt_value(dtPubblicazione);
					}
				}
				return null;
			}
		});
		
		collegataAPuntoProt = new ListGridField("collegataAPuntoProt", I18NUtil.getMessages().organigramma_list_collegataAPuntoProt_title());

		flgPuntoProtocollo = new ListGridField("flgPuntoProtocollo", I18NUtil.getMessages().organigramma_list_flgPuntoProtocollo_title());
		flgPuntoProtocollo.setAttribute("custom", true);
		flgPuntoProtocollo.setShowHover(true);
		flgPuntoProtocollo.setAlign(Alignment.CENTER);
		flgPuntoProtocollo.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgPuntoProtocollo.setWidth(30);
		flgPuntoProtocollo.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
					return "<div align=\"center\"><img src=\"images/organigramma/puntoProtocollo.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
				}
				return null;
			}
		});
		flgPuntoProtocollo.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
					return "Punto di Protocollo";
				}
				return null;
			}
		});
		
		flgPuntoRaccoltaEmail = new ListGridField("flgPuntoRaccoltaEmail", I18NUtil.getMessages().organigramma_list_flgPuntoRaccoltaEmail_title());
		flgPuntoRaccoltaEmail.setAttribute("custom", true);
		flgPuntoRaccoltaEmail.setShowHover(true);
		flgPuntoRaccoltaEmail.setAlign(Alignment.CENTER);
		flgPuntoRaccoltaEmail.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgPuntoRaccoltaEmail.setWidth(30);
		flgPuntoRaccoltaEmail.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flgPuntoRaccoltaEmail") != null && record.getAttributeAsBoolean("flgPuntoRaccoltaEmail")) {
					return "<div align=\"center\"><img src=\"images/organigramma/puntoRaccoltaEmail.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
				}
				return null;
			}
		});
		flgPuntoRaccoltaEmail.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flgPuntoRaccoltaEmail") != null && record.getAttributeAsBoolean("flgPuntoRaccoltaEmail")) {
					return I18NUtil.getMessages().organigramma_list_flgPuntoRaccoltaEmail_title();
				}
				return null;
			}
		});

		nomePostazioneInPrecedenza = new ListGridField("nomePostazioneInPrecedenza", I18NUtil.getMessages().organigramma_list_InPrecedenza_title());
		
		flgInibitoInvioCC = new ListGridField("flgInibitoInvioCC", I18NUtil.getMessages().organigramma_uo_detail_flgInibitoInvioCC_title());
		flgInibitoInvioCC.setType(ListGridFieldType.ICON);
		flgInibitoInvioCC.setWidth(30);
		flgInibitoInvioCC.setIconWidth(16);
		flgInibitoInvioCC.setIconHeight(16);
		Map<String, String> flgInibitoInvioCCValueIcons = new HashMap<String, String>();
		flgInibitoInvioCCValueIcons.put("true", "organigramma/inibito_invio_cc_docfasc.png");
		flgInibitoInvioCC.setValueIcons(flgInibitoInvioCCValueIcons);
		flgInibitoInvioCC.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("true".equals(record.getAttribute("flgInibitoInvioCC"))) {
					return I18NUtil.getMessages().organigramma_uo_detail_flgInibitoInvioCC_title();
				}
				return null;
			}
		});

		flgInibitaAssegnazione = new ListGridField("flgInibitaAssegnazione", I18NUtil.getMessages().organigramma_uo_detail_flgInibitaAssegnazione_title());
		flgInibitaAssegnazione.setType(ListGridFieldType.ICON);
		flgInibitaAssegnazione.setWidth(30);
		flgInibitaAssegnazione.setIconWidth(16);
		flgInibitaAssegnazione.setIconHeight(16);
		Map<String, String> flgInibitaAssegnazioneValueIcons = new HashMap<String, String>();
		flgInibitaAssegnazioneValueIcons.put("true", "organigramma/inibita_ass_docfasc.png");
		flgInibitaAssegnazione.setValueIcons(flgInibitaAssegnazioneValueIcons);
		flgInibitaAssegnazione.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("true".equals(record.getAttribute("flgInibitoInvioCC"))) {
					return I18NUtil.getMessages().organigramma_uo_detail_flgInibitaAssegnazione_title();
				}
				return null;
			}
		});
		
		flgInibitaAssegnazioneEmail = new ListGridField("flgInibitaAssegnazioneEmail", I18NUtil.getMessages().organigramma_uo_detail_flgInibitaAssegnazioneEmail_title());
		flgInibitaAssegnazioneEmail.setType(ListGridFieldType.ICON);
		flgInibitaAssegnazioneEmail.setWidth(30);
		flgInibitaAssegnazioneEmail.setIconWidth(16);
		flgInibitaAssegnazioneEmail.setIconHeight(16);
		Map<String, String> flgInibitaAssegnazioneEmailValueIcons = new HashMap<String, String>();
		flgInibitaAssegnazioneEmailValueIcons.put("true", "organigramma/inibita_ass_email.png");
		flgInibitaAssegnazioneEmail.setValueIcons(flgInibitaAssegnazioneEmailValueIcons);
		flgInibitaAssegnazioneEmail.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("true".equals(record.getAttribute("flgInibitoInvioCC"))) {
					return I18NUtil.getMessages().organigramma_uo_detail_flgInibitaAssegnazioneEmail_title();
				}
				return null;
			}
		});

		// abilitata alla protocollazione in entrata
		abilitaUoProtEntrata = new ListGridField("abilitaUoProtEntrata", I18NUtil.getMessages().organigramma_uo_detail_abilitaUoProtEntrata_title());
		abilitaUoProtEntrata.setType(ListGridFieldType.ICON);
		abilitaUoProtEntrata.setWidth(30);
		abilitaUoProtEntrata.setIconWidth(16);
		abilitaUoProtEntrata.setIconHeight(16);
		Map<String, String> abilitaUoProtEntrataValueIcons = new HashMap<String, String>();
		abilitaUoProtEntrataValueIcons.put("true", "menu/protocollazione_entrata.png");
		abilitaUoProtEntrata.setValueIcons(abilitaUoProtEntrataValueIcons);
		abilitaUoProtEntrata.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("true".equals(record.getAttribute("abilitaUoProtEntrata"))) {
					return I18NUtil.getMessages().organigramma_uo_detail_abilitaUoProtEntrata_title();
				}
				return null;
			}
		});
		
		// abilitata alla protocollazione in uscita
		abilitaUoProtUscita = new ListGridField("abilitaUoProtUscita", I18NUtil.getMessages().organigramma_uo_detail_abilitaUoProtUscita_title());
		abilitaUoProtUscita.setType(ListGridFieldType.ICON);
		abilitaUoProtUscita.setWidth(30);
		abilitaUoProtUscita.setIconWidth(16);
		abilitaUoProtUscita.setIconHeight(16);
		Map<String, String> abilitaUoProtUscitaValueIcons = new HashMap<String, String>();
		abilitaUoProtUscitaValueIcons.put("true", "menu/protocollazione_uscita.png");
		abilitaUoProtUscita.setValueIcons(abilitaUoProtUscitaValueIcons);
		abilitaUoProtUscita.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("true".equals(record.getAttribute("abilitaUoProtUscita"))) {
					return I18NUtil.getMessages().organigramma_uo_detail_abilitaUoProtUscita_title();
				}
				return null;
			}
		});
		
		// abilitata alla protocollazione in uscita con qualsiasi mezzo
		abilitaUoProtUscitaFull = new ListGridField("abilitaUoProtUscitaFull", I18NUtil.getMessages().organigramma_uo_detail_abilitaUoProtUscitaFull_title());
		abilitaUoProtUscitaFull.setType(ListGridFieldType.ICON);
		abilitaUoProtUscitaFull.setWidth(30);
		abilitaUoProtUscitaFull.setIconWidth(16);
		abilitaUoProtUscitaFull.setIconHeight(16);
		Map<String, String> abilitaUoProtUscitaFullValueIcons = new HashMap<String, String>();
		abilitaUoProtUscitaFullValueIcons.put("true", "menu/protocollazione_U.png");
		abilitaUoProtUscitaFull.setValueIcons(abilitaUoProtUscitaFullValueIcons);
		abilitaUoProtUscitaFull.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("true".equals(record.getAttribute("abilitaUoProtUscitaFull"))) {
					return I18NUtil.getMessages().organigramma_uo_detail_abilitaUoProtUscitaFull_title();
				}
				return null;
			}
		});
		
		// abilitata ad assegnare riservatezza
		abilitaUoAssRiservatezza = new ListGridField("abilitaUoAssRiservatezza", I18NUtil.getMessages().organigramma_uo_detail_abilitaUoAssRiservatezza_title());
		abilitaUoAssRiservatezza.setType(ListGridFieldType.ICON);
		abilitaUoAssRiservatezza.setWidth(30);
		abilitaUoAssRiservatezza.setIconWidth(16);
		abilitaUoAssRiservatezza.setIconHeight(16);
		Map<String, String> abilitaUoAssRiservatezzaValueIcons = new HashMap<String, String>();
		abilitaUoAssRiservatezzaValueIcons.put("true", "lock.png");
		abilitaUoAssRiservatezza.setValueIcons(abilitaUoAssRiservatezzaValueIcons);
		abilitaUoAssRiservatezza.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("true".equals(record.getAttribute("abilitaUoAssRiservatezza"))) {
					return I18NUtil.getMessages().organigramma_uo_detail_abilitaUoAssRiservatezza_title();
				}
				return null;
			}
		});
		
		// abilitata alla registrazione multe
		abilitaUoGestMulte = new ListGridField("abilitaUoGestMulte", I18NUtil.getMessages().organigramma_uo_detail_abilitaUoGestMulte_title());
		abilitaUoGestMulte.setType(ListGridFieldType.ICON);
		abilitaUoGestMulte.setWidth(30);
		abilitaUoGestMulte.setIconWidth(16);
		abilitaUoGestMulte.setIconHeight(16);
		Map<String, String> abilitaUoGestMulteValueIcons = new HashMap<String, String>();
		abilitaUoGestMulteValueIcons.put("true", "lettere/lettera_M_rossa.png");
		abilitaUoGestMulte.setValueIcons(abilitaUoGestMulteValueIcons);
		abilitaUoGestMulte.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("true".equals(record.getAttribute("abilitaUoGestMulte"))) {
					return I18NUtil.getMessages().organigramma_uo_detail_abilitaUoGestMulte_title();
				}
				return null;
			}
		});
						
		// abilitata alla registrazione contratti
		abilitaUoGestContratti = new ListGridField("abilitaUoGestContratti", I18NUtil.getMessages().organigramma_uo_detail_abilitaUoGestContratti_title());
		abilitaUoGestContratti.setType(ListGridFieldType.ICON);
		abilitaUoGestContratti.setWidth(30);
		abilitaUoGestContratti.setIconWidth(16);
		abilitaUoGestContratti.setIconHeight(16);
		Map<String, String> abilitaUoGestContrattiValueIcons = new HashMap<String, String>();
		abilitaUoGestContrattiValueIcons.put("true", "lettere/lettera_C_verde.png");
		abilitaUoGestContratti.setValueIcons(abilitaUoGestContrattiValueIcons);
		abilitaUoGestContratti.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("true".equals(record.getAttribute("abilitaUoGestContratti"))) {
					return I18NUtil.getMessages().organigramma_uo_detail_abilitaUoGestContratti_title();
				}
				return null;
			}
		});
		
		// struttura da cessare dal <data>
		tsStrutturaDaCessareDal = new ListGridField("tsStrutturaDaCessareDal", I18NUtil.getMessages().organigramma_list_tsStrutturaDaCessareDalField_title());
		tsStrutturaDaCessareDal.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		tsStrutturaDaCessareDal.setAlign(Alignment.CENTER);
		tsStrutturaDaCessareDal.setAttribute("custom", true);
		tsStrutturaDaCessareDal.setShowHover(true);
		tsStrutturaDaCessareDal.setType(ListGridFieldType.ICON);
		tsStrutturaDaCessareDal.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (value != null && !value.toString().equalsIgnoreCase("")) {
					return buildImgButtonHtml("buttons/hideTree.png");
				}
				return null;
			}
		});
		tsStrutturaDaCessareDal.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String realValue = record.getAttribute("tsStrutturaDaCessareDal");
				if (realValue != null && !realValue.equalsIgnoreCase("")) {					
					return "struttura da cessare dal " + realValue;
				}
				return null;
			}
		});
		
		// origine creazione
		origineCreazione = new ListGridField("origineCreazione", I18NUtil.getMessages().organigramma_uo_list_origineCreazione_title());
		origineCreazione.setType(ListGridFieldType.ICON);
		origineCreazione.setWidth(30);
		origineCreazione.setIconWidth(16);
		origineCreazione.setIconHeight(16);
		Map<String, String> origineCreazioneValueIcons = new HashMap<String, String>();
		origineCreazioneValueIcons.put("HR", "lettere/lettera_HR_nera.png");
		origineCreazioneValueIcons.put("M", "lettere/lettera_M_nera.png");
		origineCreazione.setValueIcons(origineCreazioneValueIcons);
		origineCreazione.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("HR".equals(record.getAttribute("origineCreazione"))) {
					return I18NUtil.getMessages().organigramma_list_icona_OrigineCreazione_HR_Button_prompt();
				}
				if ("M".equals(record.getAttribute("origineCreazione"))) {
					return I18NUtil.getMessages().organigramma_list_icona_origineCreazione_M_Button_prompt();
				}				
				return null;
			}
		});
		
		List<ListGridField> fields = new ArrayList<ListGridField>();
		fields.add(folderUp);
		fields.add(icona);
		fields.add(idUoSvUt);
		fields.add(tipo);
		fields.add(nome);
		fields.add(denominazioneEstesa);
		fields.add(tsInizio);
		fields.add(tsFine);
		fields.add(codRapidoUo);
		fields.add(codRapidoUoXOrd);
		fields.add(tipoRelUtenteUo);
		fields.add(protocollista);
		fields.add(idUoSup);
		fields.add(denomUoSup);
		fields.add(flgSelXFinalita);
		fields.add(flgSelXAssegnazione);
		fields.add(score);
		fields.add(ruolo);
		fields.add(username);
		fields.add(titolo);
		fields.add(qualifica);
		fields.add(competenzefunzioni);
		fields.add(flgPubblicataSuIpa);
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT")) {
			fields.add(collegataAPuntoProt);
			fields.add(flgPuntoProtocollo);
		}
		fields.add(flgPuntoRaccoltaEmail);
		fields.add(indirizzo);
		fields.add(telefono);
		fields.add(email);
		fields.add(fax);
		fields.add(profilo);
		fields.add(subProfili);
		fields.add(nomePostazioneInPrecedenza);
		fields.add(flgInibitoInvioCC);
		fields.add(flgInibitaAssegnazione);
		fields.add(flgInibitaAssegnazioneEmail);
		
		if (OrganigrammaLayout.isAbilitaUoProtUscitaFull()){
			fields.add(abilitaUoProtUscitaFull);	
		}
		
		// Solo A2A
		if (AurigaLayout.isAttivoClienteA2A()){
			fields.add(abilitaUoAssRiservatezza);
			fields.add(abilitaUoProtEntrata);
			fields.add(abilitaUoProtUscita);
			fields.add(abilitaUoGestMulte);
			fields.add(abilitaUoGestContratti);
			fields.add(tsStrutturaDaCessareDal);
		}
		
		
		fields.add(origineCreazione);
		
		setFields(fields.toArray(new ListGridField[fields.size()]));

		setAutoFetchData(false);
		setDataFetchMode(FetchMode.BASIC);
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
	
	public boolean isUoCorrente(String idUo) {
		String idUoCorrente = null; 
		try {
			idUoCorrente = ((OrganigrammaLayout) getLayout()).getNavigator().getCurrentNode().getIdFolder();
		} catch(Exception e) {}
		return idUoCorrente != null && idUo != null && idUoCorrente.equals(idUo); 
	}

	@Override
	protected int getButtonsFieldWidth() {
		return 50;
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
	public void manageContextClick(final Record record) {
		if (record != null) {
			String tipo = record.getAttribute("tipo");
			if (tipo != null && tipo.startsWith("UO")) {
				Record lRecord = new Record();
				lRecord.setAttribute("idFolder", record.getAttributeAsString("idUoSvUt"));
				lRecord.setAttribute("idNode", ((CustomAdvancedTreeLayout) layout).getNavigator().getCurrentNode().getIdNode());
				((CustomAdvancedTreeLayout) layout).getTree().getDataSource().performCustomOperation("calcolaPercorsoFromList", lRecord, new DSCallback() {

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
			} else if (tipo != null && tipo.startsWith("SV")) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						showRowContextMenu(getRecord(getRecordIndex(record)), null);
					}
				});
			}
		}
	}

	public void showRowContextMenu(final ListGridRecord record, final Menu navigationContextMenu) {

		final Menu contextMenu = new Menu();

		String tipo = record.getAttribute("tipo");
		if (tipo != null) {
			if (tipo.startsWith("UO")) {
				final Menu altreOpMenu = createUoAltreOpMenu(record);
				for (int i = 0; i < altreOpMenu.getItems().length; i++) {
					contextMenu.addItem(altreOpMenu.getItems()[i], i);
				}
			} else if (tipo.startsWith("SV")) {
				final Menu altreOpMenu = createSvAltreOpMenu(record);
				for (int i = 0; i < altreOpMenu.getItems().length; i++) {
					contextMenu.addItem(altreOpMenu.getItems()[i], i);
				}
			}
		}

		if (navigationContextMenu != null) {
			for (int i = 0; i < navigationContextMenu.getItems().length; i++) {
				contextMenu.addItem(navigationContextMenu.getItems()[i], i);
			}
			MenuItem separatorMenuItem = new MenuItem();
			separatorMenuItem.setIsSeparator(true);
			contextMenu.addItem(separatorMenuItem, navigationContextMenu.getItems().length);
		}

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {

				if (contextMenu.getItems().length > 0) {
					contextMenu.showContextMenu();
				}
			}
		});
	}

	public Menu createSvAltreOpMenu(final ListGridRecord record) {

		Menu altreOpMenu = new Menu();

		MenuItem visualizzaMenuItem = new MenuItem("Visualizza", "buttons/detail.png");
		visualizzaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageDetailButtonClick(record);
			}
		});
		altreOpMenu.addItem(visualizzaMenuItem);

		if (OrganigrammaLayout.isAbilToMod()) {
			MenuItem modificaMenuItem = new MenuItem("Modifica", "buttons/modify.png");
			modificaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					manageModifyButtonClick(record);
				}
			});
			altreOpMenu.addItem(modificaMenuItem);

			MenuItem sostituzioneUtente = getSostituzioneScrivaniaUtenteMenuItem(record);
			altreOpMenu.addItem(sostituzioneUtente);
			
			//Taglia postazione utente UO
			MenuItem tagliaPostazioneMenuItem = getTagliaPostazioneMenuItem(record);
			altreOpMenu.addItem(tagliaPostazioneMenuItem);
			
			// Copia postazione
			MenuItem copiaMenuItem = getCopyMenuItem(record);
			altreOpMenu.addItem(copiaMenuItem);
		}

		// Se la UO/scrivania NON e' nei preferiti (=0) mostro la voce "Aggiungi ai destinatari preferiti"
		if (record.getAttribute("flgDestinatarioNeiPreferiti") != null && !record.getAttributeAsBoolean("flgDestinatarioNeiPreferiti")) {
			MenuItem aggiungiDestinatariPreferitiItem = getAggiungiDestinatariPreferitiItem(record);
			altreOpMenu.addItem(aggiungiDestinatariPreferitiItem);
		}
		
		// Se la UO/scrivania E'nei preferiti (=1) mostro la voce "Togli dai destinatari preferiti"
		if (record.getAttribute("flgDestinatarioNeiPreferiti") != null && record.getAttributeAsBoolean("flgDestinatarioNeiPreferiti")) {
			MenuItem togliDestinatariPreferitiItem = getTogliDestinatariPreferitiItem(record);
			altreOpMenu.addItem(togliDestinatariPreferitiItem);
		}

		altreOpMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));

		return altreOpMenu;
	}

	public Menu createUoAltreOpMenu(final ListGridRecord record) {

		Menu altreOpMenu = new Menu();

		final String idUoSvUtValue = record.getAttributeAsString("idUoSvUt");

		MenuItem visualizzaMenuItem = new MenuItem("Visualizza", "buttons/detail.png");
		visualizzaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageDetailButtonClick(record);
			}
		});
		altreOpMenu.addItem(visualizzaMenuItem);

		if (OrganigrammaLayout.isAbilToMod()) {
			MenuItem modificaMenuItem = new MenuItem("Modifica", "buttons/modify.png");
			modificaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					manageModifyButtonClick(record);
				}
			});
			altreOpMenu.addItem(modificaMenuItem);
		}

		final String nroLivello = record.getAttribute("nroLivello");
		if (((OrganigrammaLayout) getLayout()).isAbilToInsInLivello(nroLivello != null ? new BigDecimal(nroLivello) : null)) {
			MenuItem aggiungiUO = new MenuItem("Nuova U.O.", "buttons/new.png");
			aggiungiUO.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					((OrganigrammaLayout) getLayout()).setIdNodeToOpenByIdFolder(record.getAttribute("idUoSvUt"), new GenericCallback() {
						
						@Override
						public void execute() {
							deselectAllRecords();					
							getLayout().changeDetail(new GWTRestDataSource("OrganigrammaDatasource", "idUoSvUt", FieldType.TEXT),
									new OrganigrammaDetail("organigramma"));
							Map<String, String> mappa = new HashMap<String, String>();
							try {
								mappa.put("nroLivello", "" + (Integer.parseInt(nroLivello) + 1));
							} catch (Exception e) {
								mappa.put("nroLivello", "1");
							}
							mappa.put("livelloCorrente", record.getAttribute("codRapidoUo"));
							getLayout().getDetail().editNewRecord(mappa);
							getLayout().getDetail().getValuesManager().clearErrors(true);
							getLayout().newMode();
						}
					});
				}
			});
			altreOpMenu.addItem(aggiungiUO);
		}

		if(OrganigrammaLayout.isAbilToIns()){
			MenuItem agganciaUtenteUOMenuItem = new MenuItem(I18NUtil.getMessages().organigramma_list_agganciaUtente(), "organigramma/agganciaUtenteUo.png");
			agganciaUtenteUOMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {				
					((OrganigrammaLayout) getLayout()).setIdNodeToOpenByIdFolder(record.getAttribute("idUoSvUt"), new GenericCallback() {
						
						@Override
						public void execute() {
							getLayout().setTitle(I18NUtil.getMessages().organigramma_list_agganciaUtente());
							getLayout().changeDetail(new GWTRestDataSource("PostazioneDatasource", "ciRelUserUo", FieldType.TEXT),
									new PostazioneDetail("organigramma", false));
							Map<String, Object> mappa = new HashMap<String, Object>();
							mappa.put("idUo", record.getAttributeAsString("idUoSvUt"));
							getLayout().getDetail().editNewRecord(mappa);
							getLayout().getDetail().getValuesManager().clearErrors(true);
							getLayout().newMode();
						}
					});
				}
			});
			altreOpMenu.addItem(agganciaUtenteUOMenuItem);
		}

		MenuItem classificheAbilitateMenuItem = new MenuItem("Classifiche abilitate", "lookup/titolario.png");
		classificheAbilitateMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				Record classificheRecord = new Record();
				classificheRecord.setAttribute("flgTpDestAbil", "UO");
				classificheRecord.setAttribute("flgClassificheAttiva", "1");
				classificheRecord.setAttribute("idUo", record.getAttributeAsString("idUoSvUt"));
				classificheRecord.setAttribute("codRapidoUo", record.getAttributeAsString("codRapidoUo"));
				classificheRecord.setAttribute("denominazioneEstesa", record.getAttributeAsString("denominazioneEstesa"));
				
				ClassificheWindow classifiche = new ClassificheWindow("Classifiche abilitate alla U.0.", classificheRecord) {

					@Override
					public void manageOnCloseClick() {
						super.manageOnCloseClick();
						layout.doSearch();
					}
				};
				classifiche.show();
			}
		});
		altreOpMenu.addItem(classificheAbilitateMenuItem);
		
		// Tipologie documentali pubblicabili
		if (OrganigrammaLayout.isAbilTipologieDocPubblicabili()) {
			MenuItem tipologieDocPubblicabiliMenuItem = new MenuItem("Tipologie documentali pubblicabili", "menu/tipologieDocumentaliPubblicabili.png");
			tipologieDocPubblicabiliMenuItem.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String titoloWindow = "Tipologie documentali pubblicabili dagli operatori della UO";
					Record tipiDocRecord = new Record();										
//					Verificare perchè record.getAttributeAsString("viewTipologiaDaUO") ritorna null, al momento impostiamo sempre a true
//					tipiDocRecord.setAttribute("viewTipologiaDaUO", record.getAttributeAsString("viewTipologiaDaUO"));
					tipiDocRecord.setAttribute("viewTipologiaDaUO", "true");
					tipiDocRecord.setAttribute("flgStatoAbilitazioneIn", "1");
					tipiDocRecord.setAttribute("flgTipologiaDestAbilIn", "UO");
					tipiDocRecord.setAttribute("idUo", record.getAttributeAsString("idUoSvUt"));
					tipiDocRecord.setAttribute("codRapidoUo", record.getAttributeAsString("codRapidoUo"));
					tipiDocRecord.setAttribute("denominazioneEstesa", record.getAttributeAsString("descrUoSvUt"));
					tipiDocRecord.setAttribute("icona", record.getAttributeAsString("icona"));					
					tipiDocRecord.setAttribute("opzioniAbil", "P");
					TipologieDocumentaliWindow tipiDoc = new TipologieDocumentaliWindow(titoloWindow, tipiDocRecord) {

						@Override
						public void manageOnCloseClick() {
							super.manageOnCloseClick();
							layout.doSearch();
						}
					};
					tipiDoc.show();
				}
			});
			altreOpMenu.addItem(tipologieDocPubblicabiliMenuItem);
		}
				
		// Tipologie documentali abilitate
		if (OrganigrammaLayout.isAbilToMod()) {
			MenuItem tipologieDocAbilitateMenuItem = new MenuItem("Tipologie documentali abilitate", "menu/tipologieDocumentali.png");
			tipologieDocAbilitateMenuItem.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					Record tipiDocRecord = new Record();					
//					Verificare perchè record.getAttributeAsString("viewTipologiaDaUO") ritorna null, al momento impostiamo sempre a true
//					tipiDocRecord.setAttribute("viewTipologiaDaUO", record.getAttributeAsString("viewTipologiaDaUO"));
					tipiDocRecord.setAttribute("viewTipologiaDaUO", "true");
					tipiDocRecord.setAttribute("flgStatoAbilitazioneIn", "1");
					tipiDocRecord.setAttribute("flgTipologiaDestAbilIn", "UO");
					tipiDocRecord.setAttribute("idUo", record.getAttributeAsString("idUoSvUt"));
					tipiDocRecord.setAttribute("codRapidoUo", record.getAttributeAsString("codRapidoUo"));
					tipiDocRecord.setAttribute("denominazioneEstesa", record.getAttributeAsString("descrUoSvUt"));				
					TipologieDocumentaliWindow tipiDoc = new TipologieDocumentaliWindow("Tipologie documentali abilitate alla U.0.", tipiDocRecord) {

						@Override
						public void manageOnCloseClick() {
							super.manageOnCloseClick();
							layout.doSearch();
						}
					};
					tipiDoc.show();
				}
			});
			altreOpMenu.addItem(tipologieDocAbilitateMenuItem);
		}

		//Tipologie fascicoli e UA abilitate
		if (OrganigrammaLayout.isAbilToMod()) {
			MenuItem tipoFascicoliAbilitateMenuItem = new MenuItem("Tipologie fascicoli e UA abilitate", "menu/tipo_fascicoli_aggr.png");
			tipoFascicoliAbilitateMenuItem.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {					
					Record tipiFolderRecord = new Record();					
					tipiFolderRecord.setAttribute("viewTipologiaDaUO", "true");
					tipiFolderRecord.setAttribute("flgStatoAbilitazioneIn", "1");
					tipiFolderRecord.setAttribute("flgTipologiaDestAbilIn", "UO");
					tipiFolderRecord.setAttribute("idUo", record.getAttributeAsString("idUoSvUt"));
					tipiFolderRecord.setAttribute("codRapidoUo", record.getAttributeAsString("codRapidoUo"));
					tipiFolderRecord.setAttribute("denominazioneEstesa", record.getAttributeAsString("descrUoSvUt"));					
					TipologieFascicoliWindows tipiFolder = new TipologieFascicoliWindows("Tipologie fascicoli e UA abilitate  alla U.O", tipiFolderRecord) {

						@Override
						public void manageOnCloseClick() {
							super.manageOnCloseClick();
							layout.doSearch();
						}
					};
					tipiFolder.show();
				}
			});
			altreOpMenu.addItem(tipoFascicoliAbilitateMenuItem);
		}
		

		if (OrganigrammaLayout.isAbilToMod()) {
			// Incolla utilizzata per le operazioni { Copia & Incolla - Taglia UO - Taglia SV }
			MenuItem incollaMenuItem = new MenuItem("Incolla", "incolla.png");
			incollaMenuItem.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					// se l'albero è chiuso n lo carica
					if (((OrganigrammaLayout) layout).getCopyNode() != null ) {
						((OrganigrammaLayout) layout).copyAndPaste("SV",record,false);
					} else if (((OrganigrammaLayout) layout).getCutNode() != null) {
						((OrganigrammaLayout) layout).paste("UO",record,true);
					} else if (((OrganigrammaLayout) layout).getCutNodePostazione() != null ) {
						((OrganigrammaLayout) layout).paste("SV",record,true);
					}	
				}
			});
			
			if ((((OrganigrammaLayout) layout).getCopyNode() != null && !((OrganigrammaLayout) layout).getCopyNode().getAttribute("idUoSvUt").equals(idUoSvUtValue))
					|| (((OrganigrammaLayout) layout).getCutNode() != null && !((OrganigrammaLayout) layout).getCutNode().getAttribute("idUoSvUt").equals(idUoSvUtValue))
					|| (((OrganigrammaLayout) layout).getCutNodePostazione() != null  && !((OrganigrammaLayout) layout).getCutNodePostazione().getAttribute("idUoSvUt").equals(idUoSvUtValue))) {
				incollaMenuItem.setEnabled(true);
			} else {
				incollaMenuItem.setEnabled(false);
			}
			altreOpMenu.addItem(incollaMenuItem);
		}
		
		// Taglia UO
		MenuItem tagliaUOMenuItem = getTagliaUOMenuItem(record);
		if (Layout.isPrivilegioAttivo("SIC/SO;M")) {
			tagliaUOMenuItem.setEnabled(true);
		} else {
			tagliaUOMenuItem.setEnabled(false);
		}
		altreOpMenu.addItem(tagliaUOMenuItem);
		
		//Elimina UO
		MenuItem eliminaUOMenuItem = getEliminaUOMenuItem(record);
		if (Layout.isPrivilegioAttivo("SIC/SO;FC")) {
			eliminaUOMenuItem.setEnabled(true);
		}else{
			eliminaUOMenuItem.setEnabled(false);
		}
		altreOpMenu.addItem(eliminaUOMenuItem);
		
		// Se la UO/scrivania NON e' nei preferiti (=0) mostro la voce "Aggiungi ai destinatari preferiti"
		if (record.getAttribute("flgDestinatarioNeiPreferiti") != null && !record.getAttributeAsBoolean("flgDestinatarioNeiPreferiti")) {
			MenuItem aggiungiDestinatariPreferitiItem = getAggiungiDestinatariPreferitiItem(record);
			altreOpMenu.addItem(aggiungiDestinatariPreferitiItem);
		}
		
		// Se la UO/scrivania E'nei preferiti (=1) mostro la voce "Togli dai destinatari preferiti"
		if (record.getAttribute("flgDestinatarioNeiPreferiti") != null && record.getAttributeAsBoolean("flgDestinatarioNeiPreferiti")) {
			MenuItem togliDestinatariPreferitiItem = getTogliDestinatariPreferitiItem(record);
			altreOpMenu.addItem(togliDestinatariPreferitiItem);
		}

		
		// Tipi Registri Numerazione abilitati
		if (OrganigrammaLayout.isAbilToMod()) {
			MenuItem tipiRegistriNumrazioneAbilitati = getTipiRegistriNumerazioneAbilitatiMenuItem(record);
			altreOpMenu.addItem(tipiRegistriNumrazioneAbilitati);			
		}
		
		altreOpMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));

		return altreOpMenu;
	}
	
	private MenuItem getCopyMenuItem(final Record record) {
		MenuItem copiaMenuItem = new MenuItem("Copia", "copia.png");
		copiaMenuItem.addClickHandler(new ClickHandler() {
		
			@Override
			public void onClick(MenuItemClickEvent event) {

				if(record.getAttribute("dataInizioValidita") == null ){ 
				     record.setAttribute("dataInizioValidita", record.getAttribute("dataInizio"));
				}

				((OrganigrammaLayout) layout).setCopyNode(record);
				((OrganigrammaLayout) layout).setCutNodePostazione(null);
				((OrganigrammaLayout) layout).setCutNode(null);
			}
		});
		return copiaMenuItem;
	}
	
	private MenuItem getTagliaUOMenuItem(final Record record) {
		MenuItem tagliaUOMenuItem = new MenuItem("Taglia", "buttons/cut.png");
		tagliaUOMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				String parentId = ((CustomAdvancedTreeLayout) layout).getNavigator().getCurrentNode().getIdNode();
				String idUoSup = parentId.substring(parentId.lastIndexOf("/") + 1);
				if(idUoSup == null || "".equals(idUoSup)) {
					idUoSup = "/";
				}	
				Record cutRecord = new Record(record.toMap());
				cutRecord.setAttribute("parentId", parentId);			
				cutRecord.setAttribute("idUoSup", idUoSup);								
				((OrganigrammaLayout) layout).setCutNode(cutRecord);
				((OrganigrammaLayout) layout).setCutNodePostazione(null);
				((OrganigrammaLayout) layout).setCopyNode(null);
			}
		});
		return tagliaUOMenuItem;
	}
	
	//Taglia postazione utente UO
	private MenuItem getTagliaPostazioneMenuItem(final Record record) {
		
		MenuItem tagliaPostazioneMenuItem = new MenuItem("Taglia", "buttons/cut.png");
		tagliaPostazioneMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				String parentId = ((CustomAdvancedTreeLayout) layout).getNavigator().getCurrentNode().getIdNode();
				String idUoSup = parentId.substring(parentId.lastIndexOf("/") + 1);
				if(idUoSup == null || "".equals(idUoSup)) {
					idUoSup = "/";
				}	
				Record cutRecord = new Record(record.toMap());
				cutRecord.setAttribute("parentId", parentId);			
				cutRecord.setAttribute("idUoSup", idUoSup);		
				((OrganigrammaLayout) layout).setCutNodePostazione(cutRecord);
				((OrganigrammaLayout) layout).setCutNode(null);
				((OrganigrammaLayout) layout).setCopyNode(null);
			}
		});
		return tagliaPostazioneMenuItem;
	}
	
	private MenuItem getEliminaUOMenuItem(final Record currentRecord) {
		MenuItem eliminaUOMenuItem = new MenuItem("Elimina UO", "buttons/delete.png");
		eliminaUOMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				((OrganigrammaLayout) layout).delete(currentRecord,true);
			}
		});
		return eliminaUOMenuItem;
	}

	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		
		final String tipo = record.getAttribute("tipo");
		if (tipo != null && tipo.startsWith("UO")) {
			manageLoadDetailUnitaOrganizzativa(record, recordNum, callback);			
		} else if (tipo != null && tipo.startsWith("SV")) {
			manageLoadDetailPostazione(record, recordNum, callback);	
		}		
	}
	
	public void manageLoadDetailUnitaOrganizzativa(final Record record, final int recordNum, final DSCallback callback) {
		getLayout().changeDetail(new GWTRestDataSource("OrganigrammaDatasource", "idUoSvUt", FieldType.TEXT), new OrganigrammaDetail("organigramma"));
		getLayout().getDetail().getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(final DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record detailRecord = response.getData()[0];
					if(((OrganigrammaLayout) getLayout()).getIdNodeToOpen() != null && 
							!"".equals(((OrganigrammaLayout) getLayout()).getIdNodeToOpen())){
						layout.getDetail().editRecord(detailRecord, recordNum);
						layout.getDetail().getValuesManager().clearErrors(true);
						callback.execute(response, null, new DSRequest());
					}else{
						((OrganigrammaLayout) getLayout()).setIdNodeToOpenByIdFolder(detailRecord.getAttribute("idUoSup"), new GenericCallback() {
							
							@Override
							public void execute() {
								layout.getDetail().editRecord(detailRecord, recordNum);
								layout.getDetail().getValuesManager().clearErrors(true);
								callback.execute(response, null, new DSRequest());
							}
						});		
					}
				}
			}
		}, new DSRequest());
	}
	
	public void manageLoadDetailPostazione(final Record record, final int recordNum, final DSCallback callback) {
		getLayout().changeDetail(new GWTRestDataSource("PostazioneDatasource", "ciRelUserUo", FieldType.TEXT), new PostazioneDetail("organigramma", false));
		getLayout().getDetail().getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(final DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record detailRecord = response.getData()[0];
					if(((OrganigrammaLayout) getLayout()).getIdNodeToOpen() != null && 
							!"".equals(((OrganigrammaLayout) getLayout()).getIdNodeToOpen())){
						layout.getDetail().editRecord(detailRecord, recordNum);
						layout.getDetail().getValuesManager().clearErrors(true);
						callback.execute(response, null, new DSRequest());
					}else{
						((OrganigrammaLayout) getLayout()).setIdNodeToOpenByIdFolder(detailRecord.getAttribute("idUo"), new GenericCallback() {
							
							@Override
							public void execute() {
								layout.getDetail().editRecord(detailRecord, recordNum);
								layout.getDetail().getValuesManager().clearErrors(true);
								callback.execute(response, null, new DSRequest());
							}
						});		
					}
				}
			}
		}, new DSRequest());
	}

	public void manageLoadDetailPerSostituzioneUtente(final Record record, final int recordNum, final DSCallback callback) {
		getLayout().changeDetail(new GWTRestDataSource("PostazioneDatasource", "ciRelUserUo", FieldType.TEXT), new PostazioneDetail("organigramma", true));
		getLayout().getDetail().getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(final DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record detailRecord = response.getData()[0];
					((OrganigrammaLayout) getLayout()).setIdNodeToOpenByIdFolder(detailRecord.getAttribute("idUo"), new GenericCallback() {
						
						@Override
						public void execute() {
							detailRecord.setAttribute("abilitaUoProtEntrata", record.getAttributeAsString("abilitaUoProtEntrata"));
							detailRecord.setAttribute("abilitaUoProtUscita", record.getAttributeAsString("abilitaUoProtUscita"));
							layout.getDetail().editRecord(detailRecord, recordNum);
							layout.getDetail().getValuesManager().clearErrors(true);
							callback.execute(response, null, new DSRequest());
						}
					});					
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

	@Override
	protected void manageAltreOpButtonClick(ListGridRecord record) {
		showRowContextMenu(record, null);
	}

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

			recordCanvas.addMember(detailButton);

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

	//Sostituzione scrivania utente
	private MenuItem getSostituzioneScrivaniaUtenteMenuItem(final Record record) {
		MenuItem sostituzioneMenuItem = new MenuItem("Sostituisci utente", "organigramma/sostituisciUtente.png");
		sostituzioneMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageLoadDetailPerSostituzioneUtente(record, -1, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						getLayout().editMode();
					}
				});
			}
		});
		return sostituzioneMenuItem;
	}

	public ListGridField getIdUoSvUt() {
		return idUoSvUt;
	}

	public void setIdUoSvUt(ListGridField idUoSvUt) {
		this.idUoSvUt = idUoSvUt;
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};

	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsFields = super.getButtonsFields();
		if (altreOpButtonField == null) {
			altreOpButtonField = buildAltreOpButtonField();
		}
		buttonsFields.add(0, altreOpButtonField);
		return buttonsFields;
	}

	@Override
	protected boolean showDetailButtonField() {
		return true;
	}
	
	@Override
	protected boolean isRecordAbilToView(ListGridRecord record) {
		if (record != null && record.getAttributeAsString("tipo") != null && "UT".equalsIgnoreCase(record.getAttributeAsString("tipo"))) {
			return false;
		}
		return true;
	}
	
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	
	private MenuItem getAggiungiDestinatariPreferitiItem(final Record record) {
		MenuItem aggiungiDestinatariPreferitiMenuItem = new MenuItem("Aggiungi ai destinatari preferiti", "organigramma/aggiungi_destinatari_preferiti.png");
		aggiungiDestinatariPreferitiMenuItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				record.setAttribute("idNode", ((CustomAdvancedTreeLayout) layout).getNavigator().getCurrentNode().getIdNode());
				((OrganigrammaLayout) layout).aggiungiTogliDestinatariPreferiti(record,"A", "LIST");
			}
		});
		return aggiungiDestinatariPreferitiMenuItem;

	}
	
	private MenuItem getTogliDestinatariPreferitiItem(final Record record) {
		MenuItem aggiungiDestinatariPreferitiMenuItem = new MenuItem("Togli dai destinatari preferiti", "organigramma/togli_destinatari_preferiti.png");
		aggiungiDestinatariPreferitiMenuItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				record.setAttribute("idNode", ((CustomAdvancedTreeLayout) layout).getNavigator().getCurrentNode().getIdNode());
				((OrganigrammaLayout) layout).aggiungiTogliDestinatariPreferiti(record, "D", "LIST");
			}
		});
		return aggiungiDestinatariPreferitiMenuItem;
	}
	
	private MenuItem getTipiRegistriNumerazioneAbilitatiMenuItem(final Record record) {
		
		MenuItem tipiRegistriNumerazioneAbilitatiMenuItem = new MenuItem("Registri abilitati", "menu/registri_numerazione.png");
		tipiRegistriNumerazioneAbilitatiMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				Record registriRecord = new Record();
				registriRecord.setAttribute("callFrom", "organigramma");
				registriRecord.setAttribute("flgStatoAbilitazioneIn", "1");
				registriRecord.setAttribute("flgTipologiaDestAbilIn", "UO");
				registriRecord.setAttribute("idUo", record.getAttributeAsString("idUoSvUt"));
				registriRecord.setAttribute("opzioniAbil", "A");
				registriRecord.setAttribute("codRapidoUo", record.getAttributeAsString("codRapidoUo"));
				registriRecord.setAttribute("denominazioneEstesa", record.getAttributeAsString("descrUoSvUt"));

				RegistriNumerazioneWindow registriNumerazione = new RegistriNumerazioneWindow("Registri Numerazione abilitati alla U.0.", registriRecord) {

					@Override
					public void manageOnCloseClick() {
						super.manageOnCloseClick();
						layout.doSearch();
					}
				};
				registriNumerazione.show();
			}
		});		
		return tipiRegistriNumerazioneAbilitatiMenuItem;
	}
	
}