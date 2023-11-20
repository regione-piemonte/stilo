/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

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
import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiList;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.items.CustomTaskButton;

/**
 * Definisce la lista dei campi per la vista sugli atti in iter
 * 
 * @author massimo malvestio
 *
 */
public class AttiInLavorazioneList extends AttiList {

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

	private ListGridField dataProposta;

	private ListGridField dataAtto;

	private ListGridField oggetto;

	private ListGridField unOrgProponente;

	private ListGridField avviatoDa;

	private ListGridField prossimeAttivita;

	private ListGridField ruoloSmistamento;

	private ListGridField assegnatario;
	
	private ListGridField assegnatarioSG;

	private ListGridField dataInvioVerificaContabile;
	
	private ListGridField dataInvioFaseAppDirettori;
	
	private ListGridField dirigenteAdottante;
	
	private ListGridField respProcedimento;
	
	private ListGridField rup;
	
	private ListGridField altriRespInConcerto;
	
	private ListGridField responsabilePEG;
	
	private ListGridField uoCompDefSpesa;
	
	private ListGridField codiceCIG;
	
	private ListGridField estensori;
	
	private ListGridField stato;
	
	private ListGridField dataPrimoInoltroRagioneria;
	
	private ListGridField nroInoltriRagioneria;
	
	private ListGridField statoRagioneria;
	
	private ListGridField flgGeneraFileUnionePerLibroFirma;
	
	private ListGridField activityName;
	
	private ListGridField flgPrevistaNumerazioneField;
	
	private ListGridField prossimoTaskAppongoFirmaVistoField;
	
	private ListGridField prossimoTaskRifiutoFirmaVistoField; 

	private ListGridField assegnatarioUffAcquistiField;
	
	public AttiInLavorazioneList(String nomeEntita) {

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

		buildDataProposta();

		buildDataAtto();

		buildOggetto();

		buildUnOrgProponente();

		buildAvviatoDa();

		buildProssimeAttivita();

		buildUnitaDocumentariaId();

		buildRuoloSmistamento();

		buildAssegnatario();
		
		buildAssegnatarioSG();
		
		buildDataInvioVerificaContabile();
		
		builDataInvioFaseAppDirettori();
		
		buildDirigenteAdottante();
		
		buildRespProcedimento();
		
		buildRUP();
		
		buildAltriRespInConcerto();
		
		buildResponsabilePEG();
		
		buildUoCompDefSpesa();
		
		buildCodiceCIG();
		
		buildEstensori();
		
		buildStato();
		
		buildDataPrimoInoltroRagioneria();
		
		buildNroInoltriRagioneria();
		
		buildStatoRagioneria();
		
		buildFlgGeneraFileUnionePerLibroFirmaField();
		
		buildActivityNameField();
		
		buildFlgPrevistaNumerazioneField();
		
		buildProssimoTaskAppongoFirmaVistoField();
		
		buildProssimoTaskRifiutoFirmaVistoField();
		
		buildAssegnatarioUffAcquistiField();
		
		List<ListGridField> listaFields = new ArrayList<ListGridField>();
		
		listaFields.add(idProcedimento);
		listaFields.add(unitaDocumentariaId);
		listaFields.add(idUdFolder);
		listaFields.add(tipoAtto);
		listaFields.add(numeroProposta);
		listaFields.add(dataProposta);
		listaFields.add(numeroAtto);
		listaFields.add(dataAtto);
		listaFields.add(inFase);
		listaFields.add(ultimaAttivita);
		listaFields.add(ultimaAttivitaEsito);
		listaFields.add(ultimaAttivitaMessaggio);
		listaFields.add(oggetto);
		listaFields.add(unOrgProponente);
		listaFields.add(dataAvvio);
		listaFields.add(avviatoDa);
		listaFields.add(prossimeAttivita);
		listaFields.add(stato);
		listaFields.add(dataPrimoInoltroRagioneria);
		listaFields.add(nroInoltriRagioneria);
		listaFields.add(statoRagioneria);
		
		if(AttiLayout.isAttivoSmistamentoAtti()) {
			listaFields.add(ruoloSmistamento);
			listaFields.add(assegnatario);
		}
		listaFields.add(assegnatarioSG);
		listaFields.add(dataInvioVerificaContabile);
		listaFields.add(dataInvioFaseAppDirettori);
		
		if(AurigaLayout.isAttivaNuovaPropostaAtto2()) {
			if(AurigaLayout.isAttivaNuovaPropostaAtto2Completa()) {
				//TODO da rivedere che colonne mettere per la gestione completa
				listaFields.add(dirigenteAdottante);
				listaFields.add(respProcedimento);
				listaFields.add(rup);
				if(AurigaLayout.isAttivoClienteCMMI() || isAttivaRespConcertoInIterAtti()) {
					listaFields.add(altriRespInConcerto);
				}
				listaFields.add(responsabilePEG);
				listaFields.add(uoCompDefSpesa);
				if(!AurigaLayout.isAttivoClienteCMMI() && isAttivaEstensoriAtti()) {
					listaFields.add(estensori);
				}
				if(AurigaLayout.isAttivoClienteCMMI() || isAttivaCIGInIterAtti()) {
					listaFields.add(codiceCIG);
				}
			} else {
				listaFields.add(dirigenteAdottante);
				listaFields.add(respProcedimento);
				listaFields.add(rup);
				if(AurigaLayout.isAttivoClienteCMMI() || isAttivaRespConcertoInIterAtti()) {
					listaFields.add(altriRespInConcerto);
				}
				listaFields.add(responsabilePEG);
				listaFields.add(uoCompDefSpesa);
				if(!AurigaLayout.isAttivoClienteCMMI() && isAttivaEstensoriAtti()) {
					listaFields.add(estensori);
				}
				if(AurigaLayout.isAttivoClienteCMMI() || isAttivaCIGInIterAtti()) {
					listaFields.add(codiceCIG);
				}
			}
		}
		listaFields.add(flgGeneraFileUnionePerLibroFirma);
		listaFields.add(activityName);
		listaFields.add(flgPrevistaNumerazioneField);
		listaFields.add(prossimoTaskAppongoFirmaVistoField);
		listaFields.add(prossimoTaskRifiutoFirmaVistoField);
		
		if(AurigaLayout.isAttivaGestioneUfficioGare()) {
			listaFields.add(assegnatarioUffAcquistiField);
		}
		
		setFields(listaFields.toArray(new ListGridField[listaFields.size()]));
	}

	private void buildIdAttoField() {
		idProcedimento = new ListGridField("idProcedimento");
		idProcedimento.setHidden(Boolean.TRUE);
		idProcedimento.setCanHide(Boolean.FALSE);
	}

	private void buildUnitaDocumentariaId() {
		unitaDocumentariaId = new ListGridField("unitaDocumentariaId");
		unitaDocumentariaId.setHidden(Boolean.TRUE);
		unitaDocumentariaId.setCanHide(Boolean.FALSE);
	}

	private void buildIdUdFolder() {
		idUdFolder = new ListGridField("idUdFolder");
		idUdFolder.setHidden(Boolean.TRUE);
		idUdFolder.setCanHide(Boolean.FALSE);
	}

	private void buildTipoAtto() {
		tipoAtto = new ListGridField("tipoAtto", I18NUtil.getMessages().atti_inlavorazione_list_tipoAtto());
		tipoAtto.setWidth(100);
		tipoAtto.setAlign(Alignment.LEFT);
	}

	private void buildDataAvvio() {
		dataAvvio = new ListGridField("dataAvvio", I18NUtil.getMessages().atti_inlavorazione_list_dataAvvio());
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
		numeroProposta = new ListGridField("ordinamentoNumeroProposta", I18NUtil.getMessages().atti_inlavorazione_list_ordinamentoNumeroProposta());
		numeroProposta.setDisplayField("numeroProposta");
		numeroProposta.setSortByDisplayField(false);
		numeroProposta.setWidth(100);
		numeroProposta.setAlign(Alignment.LEFT);
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
		numeroAtto = new ListGridField("ordinamentoNumeroAtto", I18NUtil.getMessages().atti_inlavorazione_list_ordinamentoNumeroAtto());
		numeroAtto.setDisplayField("numeroAtto");
		numeroAtto.setSortByDisplayField(false);
		numeroAtto.setWidth(100);
		numeroAtto.setAlign(Alignment.LEFT);
	}

	private void buildInFase() {
		inFase = new ListGridField("inFase", I18NUtil.getMessages().atti_inlavorazione_list_inFase());
		inFase.setWidth(100);
		inFase.setAlign(Alignment.LEFT);
	}

	private void buildUltimaAttivita() {
		ultimaAttivita = new ListGridField("ultimaAttivita", I18NUtil.getMessages().atti_inlavorazione_list_ultimaAttivita());
		ultimaAttivita.setWidth(100);
		ultimaAttivita.setAlign(Alignment.LEFT);
	}
	
	private void buildUltimaAttivitaEsito() {
		ultimaAttivitaEsito = new ListGridField("ultimaAttivitaEsito", I18NUtil.getMessages().atti_inlavorazione_list_ultimaAttivitaEsito());
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
		ultimaAttivitaMessaggio = new ListGridField("ultimaAttivitaMessaggio", I18NUtil.getMessages().atti_inlavorazione_list_ultimaAttivitaMessaggio());
		ultimaAttivitaMessaggio.setWidth(100);
		ultimaAttivitaMessaggio.setAlign(Alignment.LEFT);
	}

	private void buildDataProposta() {
		dataProposta = new ListGridField("dataProposta", I18NUtil.getMessages().atti_inlavorazione_list_dataProposta());
		dataProposta.setType(ListGridFieldType.DATE);
		dataProposta.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataProposta.setWrap(false);
		dataProposta.setWidth(100);
	}

	private void buildDataAtto() {
		dataAtto = new ListGridField("dataAtto", I18NUtil.getMessages().atti_inlavorazione_list_dataAtto());
		dataAtto.setType(ListGridFieldType.DATE);
		dataAtto.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataAtto.setWrap(false);
		dataAtto.setWidth(100);
	}

	private void buildOggetto() {
		oggetto = new ListGridField("oggetto", I18NUtil.getMessages().atti_inlavorazione_list_oggetto());
		oggetto.setWidth(100);
		oggetto.setAlign(Alignment.LEFT);
	}

	private void buildUnOrgProponente() {
		unOrgProponente = new ListGridField("unOrgProponente", I18NUtil.getMessages().atti_inlavorazione_list_unOrgProponente());
		unOrgProponente.setWidth(100);
		unOrgProponente.setAlign(Alignment.LEFT);
	}

	private void buildAvviatoDa() {
		avviatoDa = new ListGridField("avviatoDa", I18NUtil.getMessages().atti_inlavorazione_list_avviatoDa());
		avviatoDa.setWidth(100);
		avviatoDa.setAlign(Alignment.LEFT);
	}
	
	private void buildDataInvioVerificaContabile(){
		dataInvioVerificaContabile = new ListGridField("dataInvioVerificaContabile", I18NUtil.getMessages().atti_inlavorazione_list_dataInvioVerificaContabile());
		dataInvioVerificaContabile.setType(ListGridFieldType.DATE);
		dataInvioVerificaContabile.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		dataInvioVerificaContabile.setWrap(false);
		dataInvioVerificaContabile.setWidth(100);
	}
	
	private void builDataInvioFaseAppDirettori(){
		dataInvioFaseAppDirettori = new ListGridField("dataInvioFaseAppDirettori", I18NUtil.getMessages().atti_inlavorazione_list_dataInvioFaseAppDirettori());
		dataInvioFaseAppDirettori.setType(ListGridFieldType.DATE);
		dataInvioFaseAppDirettori.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		dataInvioFaseAppDirettori.setWrap(false);
		dataInvioFaseAppDirettori.setWidth(100);
		String nomeFaseApprovDirettoriASL = AurigaLayout.getParametroDB("NOME_FASE_APPROV_DIRETTORI_ASL");
		if(nomeFaseApprovDirettoriASL == null || "".equals(nomeFaseApprovDirettoriASL)) {
			dataInvioFaseAppDirettori.setHidden(true);
			dataInvioFaseAppDirettori.setCanHide(false);
		}
	}

	private void buildProssimeAttivita() {
		prossimeAttivita = new ListGridField("prossimeAttivita", I18NUtil.getMessages().atti_inlavorazione_list_prossimeAttivita());
		prossimeAttivita.setWidth(100);
		prossimeAttivita.setAlign(Alignment.LEFT);
	}

	private void buildRuoloSmistamento() {
		ruoloSmistamento = new ListGridField("ruoloSmistamento");
		ruoloSmistamento.setHidden(Boolean.TRUE);
		ruoloSmistamento.setCanHide(Boolean.FALSE);
	}

	private void buildAssegnatario() {
		assegnatario = new ListGridField("assegnatario", I18NUtil.getMessages().atti_inlavorazione_list_assegnatario());
	}
	
	private void buildAssegnatarioSG() {
		assegnatarioSG = new ListGridField("assegnatarioSG", I18NUtil.getMessages().atti_inlavorazione_list_assegnatarioSG());
	}
		
	private void buildDirigenteAdottante() {
		dirigenteAdottante = new ListGridField("dirigenteAdottante", I18NUtil.getMessages().atti_inlavorazione_list_dirigenteAdottante());
	}
	
	private void buildRespProcedimento() {
		respProcedimento = new ListGridField("respProcedimento", I18NUtil.getMessages().atti_inlavorazione_list_respProcedimento());
	}
	
	private void buildRUP() {
		rup = new ListGridField("rup", I18NUtil.getMessages().atti_inlavorazione_list_rup());
	}
	
	private void buildAltriRespInConcerto() {
		altriRespInConcerto = new ListGridField("altriRespInConcerto", I18NUtil.getMessages().atti_inlavorazione_list_altriRespInConcerto());
	}
	
	private void buildResponsabilePEG() {
		responsabilePEG = new ListGridField("responsabilePEG", I18NUtil.getMessages().atti_inlavorazione_list_responsabilePEG());
	}
	
	private void buildUoCompDefSpesa() {
		uoCompDefSpesa = new ListGridField("uoCompDefSpesa", I18NUtil.getMessages().atti_inlavorazione_list_uoCompDefSpesa());
	}
	
	private void buildCodiceCIG() {
		codiceCIG = new ListGridField("codiceCIG", I18NUtil.getMessages().atti_inlavorazione_list_codiceCIG());
	}
	
	private void buildEstensori() {
		estensori = new ListGridField("estensori", I18NUtil.getMessages().atti_inlavorazione_list_estensori());
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
		flgPrevistaNumerazioneField = new ListGridField("flgPrevistaNumerazione");
		flgPrevistaNumerazioneField.setHidden(Boolean.TRUE);
		flgPrevistaNumerazioneField.setCanHide(Boolean.FALSE);
	}
	
	private void buildProssimoTaskAppongoFirmaVistoField() {
		prossimoTaskAppongoFirmaVistoField = new ListGridField("prossimoTaskAppongoFirmaVisto");
		prossimoTaskAppongoFirmaVistoField.setHidden(Boolean.TRUE);
		prossimoTaskAppongoFirmaVistoField.setCanHide(Boolean.FALSE);
	}
	
	private void buildProssimoTaskRifiutoFirmaVistoField() {
		prossimoTaskRifiutoFirmaVistoField = new ListGridField("prossimoTaskRifiutoFirmaVisto");
		prossimoTaskRifiutoFirmaVistoField.setHidden(Boolean.TRUE);
		prossimoTaskRifiutoFirmaVistoField.setCanHide(Boolean.FALSE);
	}
	
	private void buildAssegnatarioUffAcquistiField() {
		assegnatarioUffAcquistiField = new ListGridField("assegnatarioUffAcquisti", I18NUtil.getMessages().atti_inlavorazione_list_assegnatarioUffAcquisti());
	}
	
}