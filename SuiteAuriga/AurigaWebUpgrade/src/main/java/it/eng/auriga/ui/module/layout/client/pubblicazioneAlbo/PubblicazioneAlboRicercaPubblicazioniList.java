/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
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
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class PubblicazioneAlboRicercaPubblicazioniList extends CustomList {

	// Colonne visibili	
	private ListGridField segnaturaXOrd;
	private ListGridField nroPubblicazione;
	private ListGridField tsPubblicazione;
	private ListGridField oggetto;
	private ListGridField tipo;
	private ListGridField assegnatari;
	private ListGridField dtEsecutivita;
	private ListGridField score;
	private ListGridField richiedentePubblicazione;
	private ListGridField flgRicevutaViaEmail;
	private ListGridField flgInviataViaEmail;
	private ListGridField flgImmediatamenteEseg;
	private ListGridField dataAtto;
	private ListGridField fascicoliApp;
	private ListGridField livelloRiservatezza;
	private ListGridField utenteProtocollante;
	private ListGridField uoProtocollante;
	private ListGridField uoProponente;
	private ListGridField statoTrasmissioneMail;
	private ListGridField dataInizioPubblicazione;
	private ListGridField dataFinePubblicazione;
	private ListGridField formaPubblicazione;
	private ListGridField statoPubblicazione;
	private ListGridField motivoAnnullamento;

	// Colonne nascoste
	private ListGridField idRichPubbl;
	private ListGridField idDocPrimario;
	private ListGridField idUdFolder;
	private ListGridField flgUdFolder;
	private ListGridField flgSelXFinalita;
	private ListGridField segnatura;
	private ListGridField nroDocConFile;
	private ListGridField nroDocConFileFirmati;
	private ListGridField nroDocConFileDaScanner;
	
	private boolean isActiveModal;

	public PubblicazioneAlboRicercaPubblicazioniList(String nomeEntita) {

		super(nomeEntita);

		isActiveModal = AurigaLayout.getImpostazioniSceltaAccessibilitaAsBoolean("mostraModal");

		setAutoFetchData(false);
		setDataFetchMode(FetchMode.BASIC);
		setSelectionType(SelectionStyle.NONE);

		// **********************************************************************************************************************************
		// Colonne nascoste
		// **********************************************************************************************************************************
		idRichPubbl            = new ListGridField("idRichPubbl"); idRichPubbl.setHidden(true); idRichPubbl.setCanHide(false);
		idDocPrimario          = new ListGridField("idDocPrimario"); idDocPrimario.setHidden(true); idDocPrimario.setCanHide(false);
		idUdFolder             = new ListGridField("idUdFolder"); idUdFolder.setHidden(true); idUdFolder.setCanHide(false);
		flgUdFolder            = new ListGridField("flgUdFolder"); flgUdFolder.setHidden(true); flgUdFolder.setCanHide(false);
		flgSelXFinalita        = new ListGridField("flgSelXFinalita"); flgSelXFinalita.setHidden(true); flgSelXFinalita.setCanHide(false);
		segnatura              = new ListGridField("segnatura"); segnatura.setHidden(true); segnatura.setCanHide(false);
		nroDocConFile          = new ListGridField("nroDocConFile"); nroDocConFile.setHidden(true); nroDocConFile.setCanHide(false);
		nroDocConFileFirmati   = new ListGridField("nroDocConFileFirmati"); nroDocConFileFirmati.setHidden(true); nroDocConFileFirmati.setCanHide(false);
		nroDocConFileDaScanner = new ListGridField("nroDocConFileDaScanner"); nroDocConFileDaScanner.setHidden(true); nroDocConFileDaScanner.setCanHide(false);		
		
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			setCanFocus(true);
			setArrowKeyAction("focus");
			addBodyKeyPressHandler(new BodyKeyPressHandler() {
	            @Override
	            public void onBodyKeyPress(BodyKeyPressEvent event) {
	                if (EventHandler.getKey().equalsIgnoreCase("Space") == true) {
						Integer focusRow2 = getFocusRow();
						ListGridRecord record = getRecord(focusRow2);
						manageDetailButtonClick(record);
	                }
	            }
	        });
		}

		// **********************************************************************************************************************************
		// Colone visibili
		// **********************************************************************************************************************************
		
		segnaturaXOrd = new ListGridField("segnaturaXOrd", I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_list_segnaturaField_title()); 
		segnaturaXOrd.setDisplayField("segnatura"); 
		segnaturaXOrd.setSortByDisplayField(false);
		segnaturaXOrd.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {

				if (AurigaLayout.getIsAttivaAccessibilita()) {
					manageLoadDetailAcc(event.getRecord(), getRecordIndex(event.getRecord()),new DSCallback() {							
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							layout.viewMode();		
						}
					}); 
				} else {
					manageLoadDetailNoAcc(event.getRecord(), getRecordIndex(event.getRecord()), new DSCallback() {							
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							layout.viewMode();		
						}
					}); 
				}
			}
		});
		
		nroPubblicazione = new ListGridField("nroPubblicazione", I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_list_nroPubblicazioneField_title());
		
		tsPubblicazione = new ListGridField("tsPubblicazione", I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_list_tsPubblicazioneField_title()); 
		tsPubblicazione.setType(ListGridFieldType.DATE); 
		tsPubblicazione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME); 
		tsPubblicazione.setWrap(false);
		
		oggetto = new ListGridField("oggetto", I18NUtil.getMessages().archivio_list_oggettoField_title());
		
		tipo = new ListGridField("tipo", I18NUtil.getMessages().archivio_list_tipoField_title());		
		
		assegnatari = new ListGridField("assegnatari", I18NUtil.getMessages().archivio_list_assegnatariField_title());
		
		dtEsecutivita = new ListGridField("dtEsecutivita", I18NUtil.getMessages().archivio_list_dtEsecutivitaField_title()); 
		dtEsecutivita.setType(ListGridFieldType.DATE);
		dtEsecutivita.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE); 
		
		score = new ListGridField("score", I18NUtil.getMessages().archivio_list_scoreField_title());
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
		
		richiedentePubblicazione = new ListGridField("richiedentePubblicazione", I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_list_richiedentePubblicazioneField_title());
		
		flgRicevutaViaEmail = new ListGridField("iconaFlgRicevutaViaEmail", I18NUtil.getMessages().archivio_list_flgRicevutaViaEmailField_title());
		flgRicevutaViaEmail.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgRicevutaViaEmail.setAlign(Alignment.CENTER);
		flgRicevutaViaEmail.setWrap(false);
		flgRicevutaViaEmail.setWidth(30);
		flgRicevutaViaEmail.setAttribute("custom", true);
		flgRicevutaViaEmail.setShowHover(true);
		flgRicevutaViaEmail.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgRicevutaViaEmail = (String) record.getAttribute("flgRicevutaViaEmail");
				if (flgRicevutaViaEmail != null && !"".equals(flgRicevutaViaEmail)) {
					return buildIconHtml("mail/" + flgRicevutaViaEmail + ".png");
				}
				return null;
			}
		});
		flgRicevutaViaEmail.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgRicevutaViaEmail = (String) record.getAttribute("flgRicevutaViaEmail");
				if (flgRicevutaViaEmail != null && !"".equals(flgRicevutaViaEmail)) {
					if (flgRicevutaViaEmail.equals("INTEROP")) {
						return "Ricevuta tramite e-mail interoperabile";
					} else if (flgRicevutaViaEmail.equals("PEC")) {
						return "Ricevuta tramite PEC (non interoperabile)";
					} else if (flgRicevutaViaEmail.equals("PEO")) {
						return "Ricevuta tramite posta elettronica ordinaria";
					}
				}
				return null;
			}
		});
		
		flgInviataViaEmail = new ListGridField("iconaFlgInviataViaEmail", I18NUtil.getMessages().archivio_list_flgInviataViaEmailField_title());
		flgInviataViaEmail.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgInviataViaEmail.setAlign(Alignment.CENTER);
		flgInviataViaEmail.setWrap(false);
		flgInviataViaEmail.setWidth(30);
		flgInviataViaEmail.setAttribute("custom", true);
		flgInviataViaEmail.setShowHover(true);
		flgInviataViaEmail.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flgInviataViaEmail") != null && !"".equals(record.getAttribute("flgInviataViaEmail"))) {
					return buildIconHtml("mail/inviata.png");
				}
				return null;
			}
		});
		flgInviataViaEmail.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgInviataViaEmail = (String) record.getAttribute("flgInviataViaEmail");
				if (flgInviataViaEmail != null && !"".equals(flgInviataViaEmail)) {
					String[] tokens = new StringSplitterClient(flgInviataViaEmail, ";").getTokens();
					if (tokens.length > 0) {
						String res = "Inviata tramite ";
						for (int i = 0; i < tokens.length; i++) {
							if (i > 0) {
								if (i == (tokens.length - 1)) {
									res += " e ";
								} else {
									res += ", ";
								}
							}
							if (tokens[i].equals("INTEROP")) {
								res += "e-mail interoperabile";
							} else if (tokens[i].equals("PEC")) {
								res += "PEC (non interoperabile)";
							} else if (tokens[i].equals("PEO")) {
								res += "posta elettronica ordinaria";
							}
						}
						return res;
					}
				}
				return null;
			}
		});
		
		flgImmediatamenteEseg = new ListGridField("flgImmediatamenteEseg", I18NUtil.getMessages().archivio_list_flgImmediatamenteEsegField());
		flgImmediatamenteEseg.setType(ListGridFieldType.ICON);
		flgImmediatamenteEseg.setIconWidth(16);
		flgImmediatamenteEseg.setIconHeight(16);
		Map<String, String> flgImmediatamenteEsegValueIcons = new HashMap<String, String>();
		flgImmediatamenteEsegValueIcons.put("1", "attiInLavorazione/IE.png");
		flgImmediatamenteEsegValueIcons.put("0", "blank.png");
		flgImmediatamenteEseg.setValueIcons(flgImmediatamenteEsegValueIcons);
		
		dataAtto = new ListGridField("dataAtto", I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_list_dataAttoField_title()); 
		dataAtto.setType(ListGridFieldType.DATE); 
		dataAtto.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE); 		
		
		fascicoliApp = new ListGridField("fascicoliApp", I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_list_fascicoliAppField_title());
		
		livelloRiservatezza = new ListGridField("livelloRiservatezza", I18NUtil.getMessages().archivio_list_livelloRiservatezzaField_title());
		livelloRiservatezza.setType(ListGridFieldType.INTEGER);
		livelloRiservatezza.setAlign(Alignment.CENTER);
		livelloRiservatezza.setWrap(false);
		livelloRiservatezza.setWidth(30);
		livelloRiservatezza.setIconWidth(16);
		livelloRiservatezza.setIconHeight(16);
		livelloRiservatezza.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String valueLivelloRiservatezza = record.getAttribute("livelloRiservatezza");
				Integer livelloRiservatezza = valueLivelloRiservatezza != null && !"".equals(String.valueOf(valueLivelloRiservatezza)) ? new Integer(String
						.valueOf(valueLivelloRiservatezza)) : null;
				if (livelloRiservatezza != null) {
					return buildIconHtml("lock.png");
				}
				return null;
			}
		});
		livelloRiservatezza.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String valueLivelloRiservatezza = record.getAttribute("livelloRiservatezza");
				Integer livelloRiservatezza = valueLivelloRiservatezza != null && !"".equals(String.valueOf(valueLivelloRiservatezza)) ? new Integer(String
						.valueOf(valueLivelloRiservatezza)) : null;
				if (livelloRiservatezza != null) {
					return "Riservato";
				}
				return null;
			}
		});
		
		utenteProtocollante = new ListGridField("utenteProtocollante", I18NUtil.getMessages().archivio_list_utenteProtocollanteField_title());
		
		uoProtocollante = new ListGridField("uoProtocollante", I18NUtil.getMessages().archivio_list_uoProtocollanteField_title());
		
		uoProponente = new ListGridField("uoProponente", I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_list_uoProponenteField_title());
		
		statoTrasmissioneMail = new ListGridField("statoTrasmissioneEmail", I18NUtil.getMessages().stato_trasmissione_mail_title());
		statoTrasmissioneMail.setType(ListGridFieldType.ICON);
		statoTrasmissioneMail.setWidth(30);
		statoTrasmissioneMail.setIconWidth(16);
		statoTrasmissioneMail.setIconHeight(16);
		Map<String, String> statoInvioMailIcons = new HashMap<String, String>();
		statoInvioMailIcons.put("ERROR", "postaElettronica/statoConsolidamento/ko-red.png");
		statoInvioMailIcons.put("WARNING", "postaElettronica/statoConsolidamento/ko-arancione.png");
		statoInvioMailIcons.put("IN_PROGRESS", "postaElettronica/statoConsolidamento/presunto_ok.png");
		statoInvioMailIcons.put("CONSEGNA", "postaElettronica/statoConsolidamento/consegnata.png");
		statoTrasmissioneMail.setValueIcons(statoInvioMailIcons);
		Map<String, String> statoInvioMailValueHovers = new HashMap<String, String>();
		statoInvioMailValueHovers.put("ERROR", I18NUtil.getMessages().stato_trasmissione_mail_error());
		statoInvioMailValueHovers.put("WARNING", I18NUtil.getMessages().stato_trasmissione_mail_warning());
		statoInvioMailValueHovers.put("IN_PROGRESS", I18NUtil.getMessages().stato_trasmissione_mail_inprogress());
		statoInvioMailValueHovers.put("CONSEGNA", I18NUtil.getMessages().stato_trasmissione_mail_consegna());
		statoTrasmissioneMail.setAttribute("valueHovers", statoInvioMailValueHovers);
		
		dataInizioPubblicazione = new ListGridField("dataInizioPubblicazione", I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_list_dataInizioPubblicazioneField_title()); 
		dataInizioPubblicazione.setType(ListGridFieldType.DATE); 
		dataInizioPubblicazione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE); 
		
		dataFinePubblicazione = new ListGridField("dataFinePubblicazione", I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_list_dataFinePubblicazioneField_title());   
		dataFinePubblicazione.setType(ListGridFieldType.DATE);
		dataFinePubblicazione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE); 
		
		formaPubblicazione = new ListGridField("formaPubblicazione", I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_list_formaPubblicazioneField_title());
		
		statoPubblicazione = new ListGridField("statoPubblicazione", I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_list_statoPubblicazioneField_title());
		
		motivoAnnullamento = new ListGridField("motivoAnnullamento", I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_list_motivoAnnullamentoField_title());
		
		setFields( 	
			// Colone visibili
			oggetto, 
			segnaturaXOrd, 
			dataAtto, 
			tipo, 
			fascicoliApp, 
			livelloRiservatezza, 
			assegnatari, 
			flgRicevutaViaEmail, 
			flgInviataViaEmail, 
			utenteProtocollante, 
			uoProtocollante, 
			nroPubblicazione, 
			dataInizioPubblicazione, 
			dataFinePubblicazione, 
			richiedentePubblicazione, 
			statoPubblicazione, 
			tsPubblicazione, 
			formaPubblicazione, 
			motivoAnnullamento, 
			uoProponente, 
			score, 
			statoTrasmissioneMail, 
			dtEsecutivita, 
			flgImmediatamenteEseg, 
			
			// Colonne nascoste	
			idRichPubbl,
			idDocPrimario,
			idUdFolder,
			flgUdFolder,
			flgSelXFinalita,
			segnatura,
			nroDocConFile,
			nroDocConFileFirmati,
			nroDocConFileDaScanner				
		);
			
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

	protected MenuItem[] getHeaderContextMenuItems(Integer fieldNum) {
		return super.getHeaderContextMenuItems(fieldNum);
	}

	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			manageLoadDetailAcc(record, recordNum, callback);
		} else {
			manageLoadDetailNoAcc(record, recordNum, callback);
		}
	}

	// Definisco i bottoni DETTAGLIO/MODIFICA/CANCELLA/SELEZIONA
	@Override
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {
		Canvas lCanvasReturn = null;

		if (fieldName.equals("buttons")) {

			ImgButton detailButton = buildDetailButton(record);

			ImgButton lookupButton = buildLookupButton(record);

			HLayout recordCanvas = new HLayout(4);
			recordCanvas.setHeight(22);
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);
			recordCanvas.setMembersMargin(7);
 
			if (isRecordAbilToView(record)) {
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

	public void addToRecent(String idUd, String idDoc) {
		if (idUd != null && !"".equals(idUd) && idDoc != null && !"".equals(idDoc)) {
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AddToRecentDataSource");
			lGwtRestDataSource.addData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
				}
			});
		}
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
		return PubblicazioneAlboRicercaPubblicazioniLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return PubblicazioneAlboRicercaPubblicazioniLayout.isAbilToDel();
	}
	
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean flgDiSistema = record.getAttribute("flgSistema") != null && record.getAttributeAsString("flgSistema").equals("1") ? true : false;
		return PubblicazioneAlboRicercaPubblicazioniLayout.isRecordAbilToMod(flgDiSistema);
	}
	
	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean flgDiSistema = record.getAttribute("flgSistema") != null && record.getAttributeAsString("flgSistema").equals("1") ? true : false;
		final boolean flgValido = record.getAttribute("valido") != null && record.getAttribute("valido").equals("true") ? true : false;
		return PubblicazioneAlboRicercaPubblicazioniLayout.isRecordAbilToDel(flgValido, flgDiSistema);
	}
	
	protected String buildLinkText(String text) {
		return "<div style=\"cursor:pointer\"><b><u>" + text + "</u></b></div>";		
	}
	
	@Override
	protected void manageDetailButtonClick(ListGridRecord record) {
		if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
			detailClick(record, getRecordIndex(record)); 
		} else {
			super.detailClick(record, getRecordIndex(record));
		}
	}
	
	protected void detailClick(final Record record, final int recordNum) {
		if(layout != null) {
			layout.getDetail().markForRedraw();
			manageLoadDetail(record, recordNum, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					layout.viewMode();	
					List<ToolStripButton> detailButtons = layout.getDetailButtons();
					if (dettaglioWindow != null && dettaglioWindow.isOpen()) {
						dettaglioWindow.updateContentDocumentWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}else {
						dettaglioWindow = new DettaglioWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), detailButtons);
					}
				}
			}); 
		}
	}
	
	private void manageLoadDetailAcc(final Record record, final int recordNum, final DSCallback callback) {
		if (isActiveModal) {
			Layout.showWaitPopup(I18NUtil.getMessages().archivio_detail_caricamento_dettaglio_documento());
		}		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUdFolder", record.getAttribute("idUdFolder"));
		lRecordToLoad.setAttribute("idRichPubbl", record.getAttribute("idRichPubbl"));
		lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		});
	}
	
	private void manageLoadDetailNoAcc(final Record record, final int recordNum, final DSCallback callback) {
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUdFolder", record.getAttribute("idUdFolder"));
		lRecordToLoad.setAttribute("idRichPubbl", record.getAttribute("idRichPubbl"));
		lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		});
	}
	
	@Override
	public void manageContextClick(final Record record) {
		showRowContextMenu(record);
	}
	
	@Override
	protected void manageAltreOpButtonClick(ListGridRecord record) {
		if (record != null) {
			showRowContextMenu(record);
		}
	}
	
	public void showRowContextMenu(final Record record) {
		
		GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource");
		lGwtRestDataSourceProtocollo.addParam("flgSoloAbilAzioni", "1");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUdFolder", record.getAttribute("idUdFolder"));
		lRecordToLoad.setAttribute("idRichPubbl", record.getAttribute("idRichPubbl"));
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					buildMenu(record);
				}
			}
		});
		
	}
	
	protected void buildMenu(final Record record) {
		
		Menu contextMenu = new Menu();	
		
		//Dettaglio					
		MenuItem dettaglioMenuItem = new MenuItem("Visualizza dettaglio", "buttons/detail.png");
		dettaglioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			public void onClick(MenuItemClickEvent event) {
				if (AurigaLayout.getIsAttivaAccessibilita()) {
					manageLoadDetailAcc(record, getRecordIndex(record),new DSCallback() {							
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							layout.viewMode();		
						}
					}); 
				} else {
					manageLoadDetailNoAcc(record, getRecordIndex(record), new DSCallback() {							
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							layout.viewMode();		
						}
					}); 
				}
			}
		});
		contextMenu.addItem(dettaglioMenuItem);	
		
		
		// Annullamento pubblicazione albo				
		if(record != null && record.getAttributeAsBoolean("abilAnnullamentoPubblicazione") != null &&
				record.getAttributeAsBoolean("abilAnnullamentoPubblicazione")) {
			MenuItem annullamentoPubblicazioneMenuItem = new MenuItem(I18NUtil.getMessages().pubblicazione_albo_annulla_pubblicazione_title(), "buttons/annullamento_pubblicazione.png");
			annullamentoPubblicazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				public void onClick(MenuItemClickEvent event) {
					annullaPubblicazione(record);
				}
			});
			contextMenu.addItem(annullamentoPubblicazioneMenuItem);	
		}
		
		// Proroga del termine di pubblicazione
		if(record != null && record.getAttributeAsBoolean("abilProrogaPubblicazione") != null &&
				record.getAttributeAsBoolean("abilProrogaPubblicazione")) {
			MenuItem prorogaTerminePubblicazioneMenuItem = new MenuItem(I18NUtil.getMessages().pubblicazione_albo_proroga_pubblicazione_title(), "buttons/proroga_termine_pubblicazione.png");
			prorogaTerminePubblicazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				public void onClick(MenuItemClickEvent event) {
					prorogaPubblicazione(record);
				}
			});
			contextMenu.addItem(prorogaTerminePubblicazioneMenuItem);	
		}
		
		// Rettifica
		if(record != null && record.getAttributeAsBoolean("abilRettificaPubblicazione") != null &&
				record.getAttributeAsBoolean("abilRettificaPubblicazione")) {
			MenuItem rettificaMenuItem = new MenuItem(I18NUtil.getMessages().pubblicazione_albo_rettifica_pubblicazione_title(), "protocollazione/provvisoria.png");
			rettificaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				public void onClick(MenuItemClickEvent event) {
					rettificaPubblicazione(record);
				}
			});
			contextMenu.addItem(rettificaMenuItem);	
		}		
		contextMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));	
		if(contextMenu.getItems().length > 0) {
			contextMenu.showContextMenu();
		}
	}
	
	public void annullaPubblicazione(final Record detailRecord) {
		MotivazioneAnnullamentoPubblicazionePopup motivazioneAnnullamentoPubblicazionePopup = new MotivazioneAnnullamentoPubblicazionePopup("Motivo annullamento pubblicazione") {
			
			@Override
			public void onClickOkButton(Record object, final DSCallback callback) {
				Record annPubbRecord = new Record();
				annPubbRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUdFolder"));
				annPubbRecord.setAttribute("idRichPubbl", detailRecord.getAttribute("idRichPubbl"));
				annPubbRecord.setAttribute("motivoAnnPubblicazione", object.getAttribute("motivoAnnPubblicazione"));							
				final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource");
				lGwtRestDataSource.executecustom("annullamentoPubblicazione", annPubbRecord, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						Layout.hideWaitPopup();
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {	
							Layout.addMessage(new MessageBean("Annullamento pubblicazione effettuato con successo", "", MessageType.INFO));
							layout.reloadList();
							callback.execute(response, null, new DSRequest());
						}
					}
				});
			}
		};
		motivazioneAnnullamentoPubblicazionePopup.show();	
	}
	
	public void prorogaPubblicazione(final Record detailRecord) {
		Record recordToLoad = new Record();
		recordToLoad.setAttribute("dataInizioPubblicazione", detailRecord.getAttribute("dataInizioPubblicazione"));
		recordToLoad.setAttribute("dataAProrogaPubblicazione", detailRecord.getAttribute("dataFinePubblicazione"));
		recordToLoad.setAttribute("giorniDurataProrogaPubblicazione", detailRecord.getAttribute("giorniPubblicazione"));
		ProrogaTerminePubblicazionePopup prorogaTerminePubblicazionePopup = new ProrogaTerminePubblicazionePopup("Proroga pubblicazione", true, recordToLoad) {
			
			@Override
			public void onClickOkButton(Record object, final DSCallback callback) {
				Record prorogaRecord = new Record();	
				prorogaRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUdFolder"));
				prorogaRecord.setAttribute("idRichPubbl",detailRecord.getAttribute("idRichPubbl"));
				prorogaRecord.setAttribute("dataAProrogaPubblicazione", object.getAttributeAsDate("dataAProrogaPubblicazione"));
				prorogaRecord.setAttribute("giorniDurataProrogaPubblicazione", object.getAttribute("giorniDurataProrogaPubblicazione"));
				prorogaRecord.setAttribute("motivoProrogaPubblicazione", object.getAttribute("motivoProrogaPubblicazione"));
				
				final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource");
				lGwtRestDataSource.executecustom("prorogaTerminePubblicazione", prorogaRecord, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						Layout.hideWaitPopup();
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {	
							Layout.addMessage(new MessageBean("Proroga pubblicazione effettuata con successo", "", MessageType.INFO));
							layout.reloadList();
							callback.execute(response, null, new DSRequest());
						}
					}
				});				
			}
		};
		prorogaTerminePubblicazionePopup.show();
	}
	
	public void rettificaPubblicazione(final Record detailRecord) {
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUdFolder", detailRecord.getAttribute("idUdFolder"));
		lRecordToLoad.setAttribute("idRichPubbl", detailRecord.getAttribute("idRichPubbl"));
		lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					record.setAttribute("isRettifica", true);					
					NuovaRichiestaPubblicazioneWindow lNuovaRichiestaPubblicazioneWindow = new NuovaRichiestaPubblicazioneWindow(record.toMap(), layout) {
						
						@Override
						public void manageOnCloseClick() {
							super.manageOnCloseClick();
							layout.doSearch();
						}
					};					
					lNuovaRichiestaPubblicazioneWindow.show();
				}
			}
		});
	}
	
	@Override
	protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		try {
			if (getFieldName(colNum).equals("segnaturaXOrd")) {
				if (record.getAttribute("segnaturaXOrd") != null &&
						!"".equalsIgnoreCase(record.getAttribute("segnaturaXOrd"))) {
									return it.eng.utility.Styles.cellTextBlueClickable;
				}
			} else {
				return super.getBaseStyle(record, rowNum, colNum);
			}
		} catch (Exception e) {
			return super.getBaseStyle(record, rowNum, colNum);
		}
		return super.getBaseStyle(record, rowNum, colNum);
	}
	
}