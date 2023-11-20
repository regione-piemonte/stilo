/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VStack;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.AvvioPropostaAttoDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.AvvioNuovaPropostaAtto2CompletaDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.AvvioNuovaPropostaAtto2Detail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.AvvioRevocaNuovaPropostaAtto2CompletaDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.AvvioRevocaNuovaPropostaAtto2Detail;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public class AvvioPraticaLayout extends DettaglioPraticaLayout {
	
	protected BooleanCallback afterAvvioIterCallback;
	
	public AvvioPraticaLayout(Record lRecordAvvio, ModalWindow window) {		
		this(lRecordAvvio, window, null);
	}
	
	public AvvioPraticaLayout(Record lRecordAvvio, ModalWindow window, final BooleanCallback afterAvvioIterCallback) {		
		
		super(null, window);
				
		vm.editNewRecord();
		
		this.recordAvvio = lRecordAvvio;
		this.afterAvvioIterCallback = afterAvvioIterCallback;
		
		this.idTipoProc = lRecordAvvio.getAttribute("idTipoProc");
		
		this.intestazioneProgetto = getIntestazioneProgetto();	
		
		mappaMenu = new LinkedHashMap<String, Record>();
		mappaSottomenuGruppi = new LinkedHashMap<String, RecordList>();
		mappaSottomenuAttributiComplessi = new LinkedHashMap<String, RecordList>();
		mappaVStackSottomenu = new HashMap<String, VStack>();
		mappaLabelSottomenu = new HashMap<String, Label>();
		
		creaMenuGestisciIter(new ServiceCallback<Record>() {			
			@Override
			public void execute(Record record) {
				for(String key : mappaMenu.keySet()) {
					String tipoAttivita = mappaMenu.get(key).getAttributeAsString("tipoAttivita");					
					if(tipoAttivita != null && "AS".equals(tipoAttivita)) {
						caricaDettaglioEvento(key);
						return;
					}			
				}		
			}
		});
		
//		creaMenuMonitoraProcedimento();
        
	}
	
	@Override
	public void onClickVoceMenu(Record record) {
		// In avvio dell'atto se si clicca sui passi dell'iter nella palette di destra dobbiamo disabilitare l'azione per evitare che vengano persi eventuali dati immessi a maschera
		AurigaLayout.addMessage(new MessageBean("Azione non consentita fino ad avvio completato", "", MessageType.ERROR));
	}
	
	public boolean isAvvioIterRevocaAtto() {
		return false;
	}
	
	protected String getIntestazioneProgetto() {
		String nomeTipoProc = recordAvvio.getAttribute("nomeTipoProc");
		if(nomeTipoProc != null && !"".equals(nomeTipoProc)) {
			nomeTipoProc = nomeTipoProc.substring(0, 1).toLowerCase() + nomeTipoProc.substring(1);
		}
		return "Avvio proposta di " + nomeTipoProc + " del " + DateUtil.format(new Date());						
	}
	
	@Override
	public boolean showUoProponenteResponsabileInIntestazione() {
		return AurigaLayout.getSelezioneUoProponenteAttiValueMap().size() <= 1;
	}
	
	@Override
	public CustomDetail buildDettaglioEvento(Record record) {
		
		final String idGUIEvento = record.getAttribute("idGUIEvento");
		final String nomeEntita = "evento" + idTipoProc;
		
		if (idGUIEvento != null && idGUIEvento.equalsIgnoreCase("AVVIO_PROPOSTA_ATTO")) {
			if(isAvvioIterRevocaAtto()) {
				if(AurigaLayout.isAttivaNuovaPropostaAtto2()) {
					if(AurigaLayout.isAttivaNuovaPropostaAtto2Completa()) {
						return new AvvioRevocaNuovaPropostaAtto2CompletaDetail("revoca." + nomeEntita, idTipoProc, recordAvvio, record, this);
					} else {
						return new AvvioRevocaNuovaPropostaAtto2Detail("revoca." + nomeEntita, idTipoProc, recordAvvio, record, this);
					}
				}
			} else { 
				if(AurigaLayout.isAttivaNuovaPropostaAtto2()) {
					if(AurigaLayout.isAttivaNuovaPropostaAtto2Completa()) {
						return new AvvioNuovaPropostaAtto2CompletaDetail(nomeEntita, idTipoProc, recordAvvio, record, this);
					} else {
						return new AvvioNuovaPropostaAtto2Detail(nomeEntita, idTipoProc, recordAvvio, record, this);	
					}																
				} else {
					return new AvvioPropostaAttoDetail(nomeEntita, idTipoProc, recordAvvio, record, this);
				}
			}
		} 
		
		return super.buildDettaglioEvento(record);
	}
	
	@Override
	public void manageOnClickButtonSalvaDefinitivo() {
		if(!buttonSalvaDefinitivo.isDisabled()) {
			buttonSalvaDefinitivo.setDisabled(true);
			AurigaLayout.addTemporaryDisabledButtons(buttonSalvaDefinitivo);
			salvaDatiDefinitivo();		
		}		
	}
	
	public void manageOnErrorAvvioIterAtti(String idUdToDelete) {
		if(!AurigaLayout.getParametroDBAsBoolean("SKIP_DELETE_UD_WITH_ERROR_IN_AVVIO_ITER")) {
			if(idUdToDelete != null && !"".equals(idUdToDelete)) {
				final Record lRecord = new Record();
				lRecord.setAttribute("idUd", idUdToDelete);
				final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
				lProtocolloDataSource.executecustom("annullamentoLogico", lRecord, new DSCallback() {
	
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							// l'ud è stata eliminata con successo
						} 
					}
				});
			}
		}
		buttonSalvaDefinitivo.setDisabled(false);
		AurigaLayout.removeTemporaryDisabledButtons(buttonSalvaDefinitivo);		
	}

	public void avvioIterAtti(final String idUd, String idFolder, final String idDocPrimario, String uoProtocollante, String oggetto) {
		
		final String idTipoProc = recordAvvio.getAttribute("idTipoProc");
		final String nomeTipoProc = recordAvvio.getAttribute("nomeTipoProc");
		final String idTipoFlussoActiviti = recordAvvio.getAttribute("idTipoFlussoActiviti");
		final String idTipoDocProposta = recordAvvio.getAttribute("idTipoDocProposta");
		final String nomeTipoDocProposta = recordAvvio.getAttribute("nomeTipoDocProposta");
		final String siglaProposta = recordAvvio.getAttribute("siglaProposta");
		final String idUdRichiesta = recordAvvio.getAttribute("idUdRichiesta");
		// ORGANI COLLEGIALI
		final String idSeduta = recordAvvio.getAttribute("idSeduta");
		final String organoCollegiale = recordAvvio.getAttribute("organoCollegiale");
		final String finalitaOrgColl = recordAvvio.getAttribute("finalitaOrgColl");
		
		final ServiceCallback<Record> errorCallback = new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				// se va in errore l'avvio, dopo che ho creato l'ud della proposta, devo eliminarla
				manageOnErrorAvvioIterAtti(idUd);
			}
		};
		
		final Record lRecordAvvio = new Record();
		lRecordAvvio.setAttribute("idTipoProc", idTipoProc);
		lRecordAvvio.setAttribute("nomeTipoProc", nomeTipoProc);				
		lRecordAvvio.setAttribute("idTipoFlussoActiviti", idTipoFlussoActiviti);
		lRecordAvvio.setAttribute("idTipoDocProposta", idTipoDocProposta);
		lRecordAvvio.setAttribute("nomeTipoDocProposta", nomeTipoDocProposta);
		lRecordAvvio.setAttribute("siglaProposta", siglaProposta);
		lRecordAvvio.setAttribute("idUd", idUd);
		lRecordAvvio.setAttribute("idFolder", idFolder);
		lRecordAvvio.setAttribute("idUoProponente", uoProtocollante);
		lRecordAvvio.setAttribute("oggetto", oggetto);	
		lRecordAvvio.setAttribute("idUdRichiesta", idUdRichiesta); // se è un avvio iter WF di risposta
		// ORGANI COLLEGIALI
		lRecordAvvio.setAttribute("idSeduta", idSeduta);
		lRecordAvvio.setAttribute("organoCollegiale", organoCollegiale);
		lRecordAvvio.setAttribute("finalitaOrgColl", finalitaOrgColl);
		
		Layout.showWaitPopup("Avvio proposta atto in corso...");
		GWTRestService<Record, Record> lAvvioIterAttiActivitiDatasource = new GWTRestService<Record, Record>("AvvioIterAttiActivitiDatasource");
		lAvvioIterAttiActivitiDatasource.call(lRecordAvvio, new ServiceCallback<Record>() {
			
			@Override
			public void manageError() {
				super.manageError();
				if(errorCallback != null) {
					errorCallback.execute(null);
				}
			}
			
			@Override
			public void execute(final Record lRecordAvviato) {
				
				Layout.hideWaitPopup();														
				final String idProcesso = lRecordAvviato.getAttribute("idProcesso");
				final String estremiRegNumUd = lRecordAvviato.getAttribute("estremiRegNumUd");
				final String idUd = lRecordAvviato.getAttribute("idUd");
				Layout.addMessage(new MessageBean("Avviata con successo la proposta atto " + estremiRegNumUd, "", MessageType.INFO));
				generaFilePrimario(idProcesso, idUd, idDocPrimario, idTipoDocProposta, new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record lSetFilePrimarioRecord) {
						
						if(AurigaLayout.getParametroDBAsBoolean("SKIP_TASK_IN_AVVIO_PROC")) {
							// se non eseguo la prima attività quando apro la proposta l'unica attività disponibile è la prima ma se la apro è vuota
							window.markForDestroy();
							AurigaLayout.apriDettaglioPratica(idProcesso, estremiRegNumUd, afterAvvioIterCallback);							
						} else {
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("GetListaAttFlussoProcDatasource");
							lGwtRestDataSource.addParam("idProcess", idProcesso);
							lGwtRestDataSource.fetchData(null, new DSCallback() {
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {															
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										RecordList listaAttivita = response.getDataAsRecordList();
										if(listaAttivita.getLength() > 0) {
											for(int i = 0; i < listaAttivita.getLength(); i++) {
												final Record lRecordTask = listaAttivita.get(i);
												String tipoAttivita = lRecordTask.getAttribute("tipoAttivita");									
												//la prima attività disponibile dovrebbe avere activityName uguale a 'PROPOSTA'
												if(tipoAttivita != null && "AS".equals(tipoAttivita)) {
													Record lRecord = new Record();
													lRecord.setAttribute("idProcess", idProcesso);
													lRecord.setAttribute("idUd", idUd);
													lRecord.setAttribute("idTipoEvento", lRecordTask.getAttribute("idTipoEvento"));
													lRecord.setAttribute("desEvento", getDisplayNameEvento(lRecordTask.getAttribute("nome")));
													GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("CustomEventDatasource");
													lGWTRestService.addParam("skipSuccessMsg", "true");
													lGWTRestService.call(lRecord, new ServiceCallback<Record>() {
														
														@Override
														public void manageError() {															
															Layout.addMessage(new MessageBean("Errore durante la creazione dell'evento nel task di avvio del procedimento", "", MessageType.ERROR));
															if(errorCallback != null) {
																errorCallback.execute(null);
															}
														}
														
														@Override
														public void execute(Record object) {
															Record lRecord = new Record();
															lRecord.setAttribute("instanceId", lRecordTask.getAttribute("instanceId"));
															lRecord.setAttribute("activityName", lRecordTask.getAttribute("activityName"));
															lRecord.setAttribute("idProcess", idProcesso);
															lRecord.setAttribute("idEventType", lRecordTask.getAttribute("idTipoEvento"));
															lRecord.setAttribute("idEvento", object.getAttribute("idEvento"));
															GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
															lAurigaTaskDataSource.executecustom("salvaTask", lRecord, new DSCallback() {
																@Override
																public void execute(DSResponse response, Object rawData, DSRequest request) {
																	if (response.getStatus()==DSResponse.STATUS_SUCCESS) {
																		window.markForDestroy();
																		AurigaLayout.apriDettaglioPratica(idProcesso, estremiRegNumUd, afterAvvioIterCallback); // dobbiamo per forza eseguire la prima attività all'avvio?
																	} else {																		
																		Layout.addMessage(new MessageBean("Errore durante il salvataggio del task di avvio del procedimento", "", MessageType.ERROR));
																		if(errorCallback != null) {
																			errorCallback.execute(null);
																		}
																	}
																}
															});
														}
													});
													break;
												}
											}									
										} else {
											window.markForDestroy();	
											AurigaLayout.apriDettaglioPratica(idProcesso, estremiRegNumUd, afterAvvioIterCallback);											
										}
									} else {										
										Layout.addMessage(new MessageBean("Errore durante il recupero dei task del procedimento", "", MessageType.ERROR));
										if(errorCallback != null) {
											errorCallback.execute(null);
										}
									}
								}
							});
						}
					}
				}, errorCallback);		
			}
		});	
	}
	
	protected void generaFilePrimario(final String idProcess, final String idUd, final String idDocPrimario, String idTipoDocProposta,
			final ServiceCallback<Record> successCallback, final ServiceCallback<Record> errorCallback) {

		if (currentDetail != null && (currentDetail instanceof AvvioPropostaAttoDetail)
				&& ((AvvioPropostaAttoDetail) currentDetail).isGenerazioneAutomaticaFilePrimario()) {
			GWTRestService<Record, Record> lGeneraFilePrimarioAttiDatasource = new GWTRestService<Record, Record>("GeneraFilePrimarioAttiDatasource");
			Record lRecord = new Record();
			lRecord.setAttribute("idProcess", idProcess);
			lRecord.setAttribute("idUd", idUd);
			lRecord.setAttribute("idDocType", idTipoDocProposta);
			lGeneraFilePrimarioAttiDatasource.call(lRecord, new ServiceCallback<Record>() {
				
				@Override
				public void manageError() {
					super.manageError();
					if(errorCallback != null) {
						errorCallback.execute(null);
					}
				}

				@Override
				public void execute(Record object) {					
					if (object.getAttributeAsBoolean("esito") != null && object.getAttributeAsBoolean("esito")) {
						final String uriFilePrimario = object.getAttribute("uriFilePrimario");
						final String nomeFilePrimario = object.getAttribute("nomeFilePrimario");
						((AvvioPropostaAttoDetail) currentDetail).aggiornaFilePrimario(idUd, idDocPrimario, uriFilePrimario, nomeFilePrimario, successCallback);
					} else {
						Layout.addMessage(new MessageBean(object.getAttribute("error"), "", MessageType.ERROR));
						if(errorCallback != null) {
							errorCallback.execute(null);
						}
					}
				}
			});
		} else {
			if (successCallback != null) {
				successCallback.execute(null);
			}
		}
	}

	@Override
	public boolean isFrontOffice() {
		return false;
	}

}