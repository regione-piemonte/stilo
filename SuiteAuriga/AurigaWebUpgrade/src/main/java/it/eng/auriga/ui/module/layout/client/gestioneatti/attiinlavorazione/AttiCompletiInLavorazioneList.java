/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiCompletiList;
import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.osservazioniNotifiche.OsservazioniNotificheWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.items.CustomTaskButton;

/**
 * Definisce la lista dei campi per la vista sugli atti in iter completi
 * 
 * @author Dancrist
 *
 */
public class AttiCompletiInLavorazioneList extends AttiCompletiList {

	private ListGridField idProcedimento;
	private ListGridField tipoAtto;
	private ListGridField dataAvvio;
	private ListGridField unitaDocumentariaId;
	private ListGridField idUdFolder;
	private ListGridField numeroProposta;
	private ListGridField numeroAtto;
	private ListGridField inFase;
	private ListGridField ultimaAttivita;
	private ListGridField ultimaAttivitaEsito;
	private ListGridField ultimaAttivitaMessaggio;
	private ListGridField dataAtto;
	private ListGridField oggetto;
	private ListGridField unOrgProponente;
	private ListGridField uoCompetente;
	private ListGridField avviatoDa;
	private ListGridField prossimeAttivita;
	private ListGridField ruoloSmistamento;
	private ListGridField assegnatario;
	private ListGridField assegnatarioSG;
	private ListGridField dataInvioVerificaContabile;
	private ListGridField codiceCIG;
	private ListGridField nominativi;
	private ListGridField parolaChiave;
	private ListGridField iniziativa;
	private ListGridField flgRilevanzaContabile;
	private ListGridField tipoDettaglio;
	private ListGridField codTipo;
	private ListGridField flgImmEseguibile;
	private ListGridField stato;
	private ListGridField dataPrimoInoltroRagioneria;
	private ListGridField nroInoltriRagioneria;
	private ListGridField statoRagioneria;
	private ListGridField centroDiCosto;
	private ListGridField dataScadenza;
	private ListGridField ordinativi;
	private ListGridField flgVisionato;
	private ListGridField tagApposti;
	private ListGridField flgNotifiche;	
	private ListGridField nrAttoStruttura;
	private ListGridField nrLiqContestuale;
	private ListGridField flgPresentiEmendamenti;
	private ListGridField flgGeneraFileUnionePerLibroFirma;
	private ListGridField activityName;
	private ListGridField dataInvioApprovazioneDG;
	private ListGridField flgPrevistaNumerazione;
	private ListGridField prossimoTaskAppongoFirmaVisto;
	private ListGridField prossimoTaskRifiutoFirmaVisto; 
	private ListGridField programmazioneAcquisti; 
	private ListGridField cui;
	private ListGridField assegnatarioUffAcquisti;
	
	public AttiCompletiInLavorazioneList(String nomeEntita) {

		super(nomeEntita);

		buildIdAttoField();

		buildIdUdFolder();

		buildTipoAtto();

		buildDataAvvio();

		buildNumeroProposta();

		buildNumeroAtto();

		buildInFase();

		buildUltimaAttivita();
		
		buildUltimaAttivitaEsito();

		buildUltimaAttivitaMessaggio();

		buildDataAtto();

		buildOggetto();

		buildUnOrgProponente();

		buildUoCompetente();
		
		buildAvviatoDa();

		buildProssimeAttivita();

		buildUnitaDocumentariaId();

		buildRuoloSmistamento();

		buildAssegnatario();
		
		buildAssegnatarioSG();
		
		buildDataInvioVerificaContabile();
		
		buildCodiceCIG();
		
		buildNominativi();
		
		buildParolaChiave();
		
		buildIniziativa();
		
		buildFlgRilevanzaContabile();
		
		buildTipoDettaglio();
		
		buildCodTipo();
		
		buildFlgImmEseguibile();
		
		buildStato();
		
		buildDataPrimoInoltroRagioneria();
		
		buildNroInoltriRagioneria();
		
		buildStatoRagioneria();
		
		buildCentroDiCosto();
		
		buildDataScadenza();
		
		buildOrdinativi();
		
		buildFlgVisionato();
		
		buildTagApposti();
		
		buildNotifiche();
		
		buildNrAttoStruttura();
		
		buildLiqContestuale();
		
		buildDataInvioApprovazioneDG();
		
		buildFlgPresentiEmendamenti();
		
		buildFlgGeneraFileUnionePerLibroFirmaField();
		
		buildActivityNameField();
		
		buildFlgPrevistaNumerazioneField();
		
		buildProssimoTaskAppongoFirmaVistoField();
		
		buildProssimoTaskRifiutoFirmaVistoField();
		
		buildProgrammazioneAcquistiField();
		
		buildCuiField();
		
		buildAssegnatarioUffAcquistiField();
		
		List<ListGridField> listaFields = new ArrayList<ListGridField>();
		
		listaFields.add(idProcedimento);
		listaFields.add(unitaDocumentariaId);
		listaFields.add(idUdFolder);
		listaFields.add(tipoAtto);
		listaFields.add(numeroProposta);
		listaFields.add(numeroAtto);
		listaFields.add(dataAtto);
		listaFields.add(inFase);
		listaFields.add(ultimaAttivita);
		listaFields.add(ultimaAttivitaEsito);
		listaFields.add(ultimaAttivitaMessaggio);
		listaFields.add(oggetto);
		listaFields.add(unOrgProponente);
		listaFields.add(uoCompetente);
		listaFields.add(dataAvvio);
		listaFields.add(avviatoDa);
		listaFields.add(prossimeAttivita);
		listaFields.add(nominativi);
		listaFields.add(parolaChiave);
		listaFields.add(iniziativa);
		listaFields.add(flgRilevanzaContabile);
		listaFields.add(tipoDettaglio);
		listaFields.add(codTipo);
		listaFields.add(flgImmEseguibile);
		listaFields.add(stato);
		listaFields.add(dataPrimoInoltroRagioneria);
		listaFields.add(nroInoltriRagioneria);
		listaFields.add(statoRagioneria);
		listaFields.add(centroDiCosto);		
		listaFields.add(dataScadenza);
		listaFields.add(ordinativi);
		listaFields.add(flgVisionato);
		listaFields.add(tagApposti);
		listaFields.add(flgNotifiche);
		
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_DOPPIA_NUM_ATTI")) {
			listaFields.add(nrAttoStruttura);
		}
		
		if(AurigaLayout.isAttivoClienteCMTO()) {
			listaFields.add(nrLiqContestuale);
		}

		listaFields.add(dataInvioApprovazioneDG);
		
		if(AttiLayout.isAttivoSmistamentoAtti()) {
			listaFields.add(ruoloSmistamento);
			listaFields.add(assegnatario);
		}
		listaFields.add(assegnatarioSG);
		listaFields.add(dataInvioVerificaContabile);
		
		if(AurigaLayout.isAttivaNuovaPropostaAtto2()) {			
			if(AurigaLayout.isAttivaNuovaPropostaAtto2Completa()) {
				if(AurigaLayout.isAttivoClienteCMMI() || isAttivaCIGInIterAtti()) {
					listaFields.add(codiceCIG);
				}
			} else {
				if(AurigaLayout.isAttivoClienteCMMI() || isAttivaCIGInIterAtti()) {
					listaFields.add(codiceCIG);
				}
			}			
		}
		
		listaFields.add(flgPresentiEmendamenti);
		listaFields.add(flgGeneraFileUnionePerLibroFirma);
		listaFields.add(activityName);
		listaFields.add(flgPrevistaNumerazione);
		listaFields.add(prossimoTaskAppongoFirmaVisto);
		listaFields.add(prossimoTaskRifiutoFirmaVisto);
		
		if(AurigaLayout.isAttivoClienteCOTO()) {
			listaFields.add(programmazioneAcquisti);
		}
		
		if(AurigaLayout.isAttivoClienteCOTO()) {
			listaFields.add(cui);
		}
		
		if(AurigaLayout.isAttivaGestioneUfficioGare()) {
			listaFields.add(assegnatarioUffAcquisti);
		}
		
		setFields(listaFields.toArray(new ListGridField[listaFields.size()]));
	}

	private void buildUnitaDocumentariaId() {
		unitaDocumentariaId = new ListGridField("unitaDocumentariaId");
		unitaDocumentariaId.setHidden(Boolean.TRUE);
		unitaDocumentariaId.setCanHide(Boolean.FALSE);
	}

	private void buildIdAttoField() {
		idProcedimento = new ListGridField("idProcedimento");
		idProcedimento.setHidden(Boolean.TRUE);
		idProcedimento.setCanHide(Boolean.FALSE);
	}

	private void buildIdUdFolder() {
		idUdFolder = new ListGridField("idUdFolder");
		idUdFolder.setHidden(Boolean.TRUE);
		idUdFolder.setCanHide(Boolean.FALSE);
	}

	private void buildTipoAtto() {
		tipoAtto = new ListGridField("tipoAtto", I18NUtil.getMessages().atti_inlavorazione_completi_list_tipoAtto());
		tipoAtto.setWidth(100);
		tipoAtto.setAlign(Alignment.LEFT);
	}

	private void buildDataAvvio() {
		dataAvvio = new ListGridField("dataAvvio", I18NUtil.getMessages().atti_inlavorazione_completi_list_dataAvvio());
		dataAvvio.setDisplayField("dataDecorrenza");
		dataAvvio.setSortByDisplayField(false);
		dataAvvio.setType(ListGridFieldType.DATE);
		dataAvvio.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataAvvio.setWrap(false);
		dataAvvio.setWidth(100);
		dataAvvio.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute("dataAvvio");
			}
		});
	}

	private void buildNumeroProposta() {
		numeroProposta = new ListGridField("ordinamentoNumeroProposta", I18NUtil.getMessages().atti_inlavorazione_completi_list_ordinamentoNumeroProposta());
		numeroProposta.setDisplayField("numeroProposta");
		numeroProposta.setSortByDisplayField(false);
		numeroProposta.setWidth(100);
		numeroProposta.setAlign(Alignment.LEFT);
		numeroProposta.setCanDragResize(true);
		numeroProposta.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {

				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				
				List<CustomTaskButton> customButtons = buildCustomButtonsDettaglioPratica(record);
				
				AurigaLayout.apriDettaglioPratica(record.getAttribute("idProcedimento"), record.getAttribute("numeroProposta"), customButtons, new BooleanCallback() {

					@Override
					public void execute(Boolean isSaved) {
						if (getLayout() != null) {
							if(isSaved != null && isSaved) {
								getLayout().reloadListAndSetCurrentRecord(record);
							}
						}
					}
				});
			}
		});
	}

	private void buildNumeroAtto() {
		numeroAtto = new ListGridField("ordinamentoNumeroAtto", I18NUtil.getMessages().atti_inlavorazione_completi_list_ordinamentoNumeroAtto());
		numeroAtto.setDisplayField("numeroAtto");
		numeroAtto.setSortByDisplayField(false);
		numeroAtto.setWidth(100);
		numeroAtto.setAlign(Alignment.LEFT);
	}

	private void buildInFase() {
		inFase = new ListGridField("inFase", I18NUtil.getMessages().atti_inlavorazione_completi_list_inFase());
		inFase.setWidth(100);
		inFase.setAlign(Alignment.LEFT);
	}

	private void buildUltimaAttivita() {
		ultimaAttivita = new ListGridField("ultimaAttivita", I18NUtil.getMessages().atti_inlavorazione_completi_list_ultimaAttivita());
		ultimaAttivita.setWidth(100);
		ultimaAttivita.setAlign(Alignment.LEFT);
	}
	
	private void buildUltimaAttivitaEsito() {
		ultimaAttivitaEsito = new ListGridField("ultimaAttivitaEsito", I18NUtil.getMessages().atti_inlavorazione_completi_list_ultimaAttivitaEsito());
		ultimaAttivitaEsito.setType(ListGridFieldType.ICON);
		ultimaAttivitaEsito.setWidth(30);
		ultimaAttivitaEsito.setIconWidth(16);
		ultimaAttivitaEsito.setIconHeight(16);
		ultimaAttivitaEsito.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String ultimaAttivitaEsito = record.getAttribute("ultimaAttivitaEsito") != null ? record.getAttribute("ultimaAttivitaEsito") : "";
				if ("OK".equalsIgnoreCase(ultimaAttivitaEsito)) {
					return buildIconHtml("pratiche/task/icone/svolta_OK.png");
				} else if ("KO".equalsIgnoreCase(ultimaAttivitaEsito)) {
					return buildIconHtml("pratiche/task/icone/svolta_KO.png");
				} else if ("W".equalsIgnoreCase(ultimaAttivitaEsito)) {
					return buildIconHtml("pratiche/task/icone/svolta_W.png");
				} 
				return null;
			}
		});
		ultimaAttivitaEsito.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute("altUltimaAttivitaEsito");
			}
		});
	}
	
	private void buildUltimaAttivitaMessaggio() {
		ultimaAttivitaMessaggio = new ListGridField("ultimaAttivitaMessaggio", I18NUtil.getMessages().atti_inlavorazione_completi_list_ultimaAttivitaMessaggio());
		ultimaAttivitaMessaggio.setWidth(100);
		ultimaAttivitaMessaggio.setAlign(Alignment.LEFT);
	}

	private void buildDataAtto() {
		dataAtto = new ListGridField("dataAtto", I18NUtil.getMessages().atti_inlavorazione_completi_list_dataAtto());
		dataAtto.setType(ListGridFieldType.DATE);
		dataAtto.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataAtto.setWrap(false);
		dataAtto.setWidth(100);
	}

	private void buildOggetto() {
		oggetto = new ListGridField("oggetto", I18NUtil.getMessages().atti_inlavorazione_completi_list_oggetto());
		oggetto.setWidth(100);
		oggetto.setAlign(Alignment.LEFT);
	}

	private void buildUnOrgProponente() {
		unOrgProponente = new ListGridField("unOrgProponente", I18NUtil.getMessages().atti_inlavorazione_completi_list_unOrgProponente());
		unOrgProponente.setWidth(100);
		unOrgProponente.setAlign(Alignment.LEFT);
	}
		
	private void buildUoCompetente() {
		uoCompetente = new ListGridField("uoCompetente", I18NUtil.getMessages().atti_inlavorazione_completi_list_uoCompetente());
		uoCompetente.setWidth(100);
		uoCompetente.setAlign(Alignment.LEFT);
		if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_UO_COMPETENTE_ATTI_GARE")) {
			uoCompetente.setHidden(true);
			uoCompetente.setCanHide(false);
		}
	}

	private void buildAvviatoDa() {
		avviatoDa = new ListGridField("avviatoDa", I18NUtil.getMessages().atti_inlavorazione_completi_list_avviatoDa());
		avviatoDa.setWidth(100);
		avviatoDa.setAlign(Alignment.LEFT);
	}
	
	private void buildDataInvioVerificaContabile(){
		dataInvioVerificaContabile = new ListGridField("dataInvioVerificaContabile", I18NUtil.getMessages().atti_inlavorazione_completi_list_dataInvioVerificaContabile());
		dataInvioVerificaContabile.setType(ListGridFieldType.DATE);
		dataInvioVerificaContabile.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		dataInvioVerificaContabile.setWrap(false);
		dataInvioVerificaContabile.setWidth(100);
	}

	private void buildProssimeAttivita() {
		prossimeAttivita = new ListGridField("prossimeAttivita", I18NUtil.getMessages().atti_inlavorazione_completi_list_prossimeAttivita());
		prossimeAttivita.setWidth(100);
		prossimeAttivita.setAlign(Alignment.LEFT);
	}

	private void buildRuoloSmistamento() {
		ruoloSmistamento = new ListGridField("ruoloSmistamento");
		ruoloSmistamento.setHidden(Boolean.TRUE);
		ruoloSmistamento.setCanHide(Boolean.FALSE);
	}

	private void buildAssegnatario() {
		assegnatario = new ListGridField("assegnatario", I18NUtil.getMessages().atti_inlavorazione_completi_list_assegnatario());
	}
	
	private void buildAssegnatarioSG() {
		assegnatarioSG = new ListGridField("assegnatarioSG", I18NUtil.getMessages().atti_inlavorazione_completi_list_assegnatarioSG());
	}
	
	private void buildCodiceCIG() {
		codiceCIG = new ListGridField("codiceCIG", I18NUtil.getMessages().atti_inlavorazione_completi_list_codiceCIG());
	}
	
	private void buildNominativi() {
		nominativi = new ListGridField("nominativi", I18NUtil.getMessages().atti_inlavorazione_completi_list_nominativi());
	}
	
	private void buildParolaChiave() {
		parolaChiave = new ListGridField("parolaChiave", I18NUtil.getMessages().atti_inlavorazione_completi_list_parolaChiave());
	}
	
	private void buildIniziativa() {
		iniziativa = new ListGridField("iniziativa", I18NUtil.getMessages().atti_inlavorazione_completi_list_iniziativa());
	}
	
	private void buildFlgRilevanzaContabile() {
		flgRilevanzaContabile = new ListGridField("flgRilevanzaContabile", I18NUtil.getMessages().atti_inlavorazione_completi_list_flgRilevanzaContabile());
		flgRilevanzaContabile.setType(ListGridFieldType.ICON);
		flgRilevanzaContabile.setWidth(30);
		flgRilevanzaContabile.setIconWidth(16);
		flgRilevanzaContabile.setIconHeight(16);
		Map<String, String> flgValidoValueIcons = new HashMap<String, String>();
		flgValidoValueIcons.put("1", "attiInLavorazione/rilevanzaContabile.png");
		flgRilevanzaContabile.setValueIcons(flgValidoValueIcons);
		flgRilevanzaContabile.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("1".equals(record.getAttribute("flgRilevanzaContabile"))) {
					return "Con rilevanza contabile";//TODO
				}
				return null;
			}
		});
	}
	
	private void buildTipoDettaglio() {
		tipoDettaglio = new ListGridField("tipoDettaglio", I18NUtil.getMessages().atti_inlavorazione_completi_list_tipoDettaglio());
	}
	
	private void buildCodTipo() {
		codTipo = new ListGridField("codTipo", I18NUtil.getMessages().atti_inlavorazione_completi_list_codTipo());
	}
	
	private void buildFlgImmEseguibile() {
		flgImmEseguibile = new ListGridField("flgImmEseguibile", I18NUtil.getMessages().atti_inlavorazione_completi_list_flgImmEseguibile());
		flgImmEseguibile.setType(ListGridFieldType.ICON);
		flgImmEseguibile.setWidth(30);
		flgImmEseguibile.setIconWidth(16);
		flgImmEseguibile.setIconHeight(16);
		Map<String, String> flgImmEseguibileIcons = new HashMap<String, String>();
		flgImmEseguibileIcons.put("1", "attiInLavorazione/IE.png");
		flgImmEseguibile.setValueIcons(flgImmEseguibileIcons);
		flgImmEseguibile.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("1".equals(record.getAttribute("flgImmEseguibile"))) {
					return "I.E.";
				}
				return null;
			}
		});
	}
	
	private void buildStato() {
		stato = new ListGridField("stato", I18NUtil.getMessages().atti_inlavorazione_completi_list_stato());
	}
	
	private void buildDataPrimoInoltroRagioneria(){
		dataPrimoInoltroRagioneria = new ListGridField("dataPrimoInoltroRagioneria", I18NUtil.getMessages().atti_inlavorazione_completi_list_dataPrimoInoltroRagioneria());
		dataPrimoInoltroRagioneria.setType(ListGridFieldType.DATE);
		dataPrimoInoltroRagioneria.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		dataPrimoInoltroRagioneria.setWrap(false);
		dataPrimoInoltroRagioneria.setWidth(100);
	}
	
	private void buildNroInoltriRagioneria() {
		nroInoltriRagioneria = new ListGridField("nroInoltriRagioneria", I18NUtil.getMessages().atti_inlavorazione_completi_list_nroInoltriRagioneria());
	}
	
	private void buildStatoRagioneria() {
		statoRagioneria = new ListGridField("statoRagioneria", I18NUtil.getMessages().atti_inlavorazione_completi_list_statoRagioneria());
		statoRagioneria.setType(ListGridFieldType.ICON);
		statoRagioneria.setWidth(30);
		statoRagioneria.setIconWidth(16);
		statoRagioneria.setIconHeight(16);
		statoRagioneria.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoRagioneria = record.getAttribute("statoRagioneria") != null ? record.getAttribute("statoRagioneria") : "";
				if ("N".equalsIgnoreCase(statoRagioneria)) {
					return buildIconHtml("attiInLavorazione/statoRagioneria/N.png");
				} else if ("R".equalsIgnoreCase(statoRagioneria)) {
					return buildIconHtml("attiInLavorazione/statoRagioneria/R.png");
				}
				return null;
			}
		});
		statoRagioneria.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoRagioneria = record.getAttribute("statoRagioneria") != null ? record.getAttribute("statoRagioneria") : "";
				if ("N".equalsIgnoreCase(statoRagioneria)) {
					return "Nuovo arrivo";
				} else if ("R".equalsIgnoreCase(statoRagioneria)) {
					return "Ritorno alla Ragioneria";
				}
				return null;
			}
		});
	}
	
	private void buildCentroDiCosto() {
		centroDiCosto = new ListGridField("centroDiCosto", I18NUtil.getMessages().atti_inlavorazione_completi_list_centroDiCosto());
		if(!AurigaLayout.isAttivoClienteCOTO()) {
			centroDiCosto.setHidden(true);
			centroDiCosto.setCanHide(false);
		}
	}
	
	private void buildDataScadenza(){
		dataScadenza = new ListGridField("dataScadenza", I18NUtil.getMessages().atti_inlavorazione_completi_list_dataScadenza());
		dataScadenza.setType(ListGridFieldType.DATE);
		dataScadenza.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataScadenza.setWrap(false);
		dataScadenza.setWidth(100);
		if(!AurigaLayout.getParametroDBAsBoolean("ATTIVO_ITER_LIQUIDAZIONI")) {
			dataScadenza.setHidden(true);
			dataScadenza.setCanHide(false);
		}
	}
	
	private void buildOrdinativi() {
		ordinativi = new ListGridField("ordinativi", I18NUtil.getMessages().atti_inlavorazione_completi_list_ordinativi());		
		if(!AurigaLayout.getParametroDBAsBoolean("ATTIVO_ITER_LIQUIDAZIONI")) {
			ordinativi.setHidden(true);
			ordinativi.setCanHide(false);
		}
	}
	
	private void buildFlgVisionato() {
		flgVisionato = new ListGridField("flgVisionato", I18NUtil.getMessages().atti_inlavorazione_completi_list_flgVisionato());	
		flgVisionato.setType(ListGridFieldType.ICON);
		flgVisionato.setWidth(30);
		flgVisionato.setIconWidth(16);
		flgVisionato.setIconHeight(16);
		Map<String, String> flgVisionatoValueIcons = new HashMap<String, String>();
		flgVisionatoValueIcons.put("1", "postaElettronica/flgRicevutaLettura.png");
		flgVisionato.setValueIcons(flgVisionatoValueIcons);
		flgVisionato.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("1".equals(record.getAttribute("flgVisionato"))) {
					return record.getAttribute("altFlgVisionato");
				}
				return null;
			}
		});
	}
	
	private void buildTagApposti() {
		tagApposti = new ListGridField("tagApposti", I18NUtil.getMessages().atti_inlavorazione_completi_list_tagApposti());		
	}
	
	private void buildNotifiche() {
		flgNotifiche = new ListGridField("flgNotifiche", I18NUtil.getMessages().atti_personali_completi_list_flgNotifiche());
		flgNotifiche.setType(ListGridFieldType.ICON);
		flgNotifiche.setWidth(30);
		flgNotifiche.setIconWidth(16);
		flgNotifiche.setIconHeight(16);
		Map<String, String> flgNotificheValueIcons = new HashMap<String, String>();
		flgNotificheValueIcons.put("1", "attiInLavorazione/atti_notifiche_utente.png");
		flgNotifiche.setValueIcons(flgNotificheValueIcons);
		flgNotifiche.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {

				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				
				if(record.getAttributeAsString("flgNotifiche") != null &&
						"1".equals(record.getAttributeAsString("flgNotifiche"))) {			
					String unitaDocumentariaId = record.getAttribute("unitaDocumentariaId");
					String title = "Osservazioni e notifiche " +  record.getAttribute("numeroProposta");
					OsservazioniNotificheWindow osservazioniNotifiche = new OsservazioniNotificheWindow(unitaDocumentariaId, "U", title);
					osservazioniNotifiche.show();							
				}
				
			}
		});
		flgNotifiche.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("1".equals(record.getAttribute("flgNotifiche"))) {
					return I18NUtil.getMessages().atti_personali_completi_list_flgNotifiche();
				}
				return null;
			}
		});
	}
	
	private void buildNrAttoStruttura() {
		nrAttoStruttura = new ListGridField("nrAttoStruttura", I18NUtil.getMessages().atti_inlavorazione_completi_list_nrAttoStruttura());
	}
	
	private void buildLiqContestuale() {
		nrLiqContestuale = new ListGridField("nrLiqContestuale", I18NUtil.getMessages().atti_inlavorazione_completi_list_nrLiqContestuale());
	}
	
	
	private void buildDataInvioApprovazioneDG(){
		
		dataInvioApprovazioneDG = new ListGridField("dataInvioApprovazioneDG", I18NUtil.getMessages().atti_inlavorazione_completi_list_dataInvioApprovazioneDG());
		dataInvioApprovazioneDG.setType(ListGridFieldType.DATE);
		dataInvioApprovazioneDG.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		dataInvioApprovazioneDG.setWrap(false);
		dataInvioApprovazioneDG.setWidth(100);
		String par_TASK_APPROVAZIONE_DG = AurigaLayout.getParametroDB("TASK_APPROVAZIONE_DG");
		if ((par_TASK_APPROVAZIONE_DG != null && !par_TASK_APPROVAZIONE_DG.equalsIgnoreCase(""))) {
			dataInvioApprovazioneDG.setHidden(false);
			dataInvioApprovazioneDG.setCanHide(true);
		}
		else{
			dataInvioApprovazioneDG.setHidden(true);
			dataInvioApprovazioneDG.setCanHide(false);
		}
	}
	
	private void buildFlgPresentiEmendamenti() {
		flgPresentiEmendamenti = new ListGridField("flgPresentiEmendamenti", I18NUtil.getMessages().atti_inlavorazione_completi_list_flgPresentiEmendamenti());
		flgPresentiEmendamenti.setType(ListGridFieldType.ICON);
		flgPresentiEmendamenti.setWidth(30);
		flgPresentiEmendamenti.setIconWidth(16);
		flgPresentiEmendamenti.setIconHeight(16);
		Map<String, String> flgPresentiEmendamentiValueIcons = new HashMap<String, String>();
		flgPresentiEmendamentiValueIcons.put("1", "attiInLavorazione/EM.png");
		flgPresentiEmendamenti.setValueIcons(flgPresentiEmendamentiValueIcons);
		flgPresentiEmendamenti.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("1".equals(record.getAttribute("flgPresentiEmendamenti"))) {
					return "Presenti emendamenti";
				}
				return null;
			}
		});
	}
	
	private void buildFlgGeneraFileUnionePerLibroFirmaField() {
		flgGeneraFileUnionePerLibroFirma = new ListGridField("flgGeneraFileUnionePerLibroFirma");
		flgGeneraFileUnionePerLibroFirma.setHidden(Boolean.TRUE);
		flgGeneraFileUnionePerLibroFirma.setCanHide(Boolean.FALSE);
	}
	
	private void buildActivityNameField() {
		activityName = new ListGridField("activityName");
		activityName.setHidden(Boolean.TRUE);
		activityName.setCanHide(Boolean.FALSE);
	}
	
	private void buildFlgPrevistaNumerazioneField() {
		flgPrevistaNumerazione = new ListGridField("flgPrevistaNumerazione");
		flgPrevistaNumerazione.setHidden(Boolean.TRUE);
		flgPrevistaNumerazione.setCanHide(Boolean.FALSE);
	}
	
	private void buildProssimoTaskAppongoFirmaVistoField() {
		prossimoTaskAppongoFirmaVisto = new ListGridField("prossimoTaskAppongoFirmaVisto");
		prossimoTaskAppongoFirmaVisto.setHidden(Boolean.TRUE);
		prossimoTaskAppongoFirmaVisto.setCanHide(Boolean.FALSE);
	}
	
	private void buildProssimoTaskRifiutoFirmaVistoField() {
		prossimoTaskRifiutoFirmaVisto = new ListGridField("prossimoTaskRifiutoFirmaVisto");
		prossimoTaskRifiutoFirmaVisto.setHidden(Boolean.TRUE);
		prossimoTaskRifiutoFirmaVisto.setCanHide(Boolean.FALSE);
	}
	
	private void buildProgrammazioneAcquistiField() {
		programmazioneAcquisti = new ListGridField("programmazioneAcquisti", I18NUtil.getMessages().atti_inlavorazione_completi_list_programmazioneAcquisti());
	}
	
	private void buildCuiField() {
		cui = new ListGridField("cui", I18NUtil.getMessages().atti_inlavorazione_completi_list_cui());
	}
	
	private void buildAssegnatarioUffAcquistiField() {
		assegnatarioUffAcquisti = new ListGridField("assegnatarioUffAcquisti", I18NUtil.getMessages().atti_inlavorazione_completi_list_assegnatarioUffAcquisti());
	}
	
}