/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class AvvioPropostaOrganigrammaWindow extends ModalWindow {
	protected AvvioPropostaOrganigrammaWindow _window;

	private AvvioPropostaOrganigrammaDetail detail;

	private ToolStrip detailToolStrip;
	private DetailToolStripButton saveButton;
	
	public AvvioPropostaOrganigrammaWindow() {
		
		super("avvio_proposta_organigramma", true);

		_window = this;
		
		setTitle("Avvio proposta revisione organigramma");
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		detail = new AvvioPropostaOrganigrammaDetail("avvio_proposta_organigramma");
		detail.setHeight100();

		saveButton = new DetailToolStripButton(I18NUtil.getMessages().saveButton_prompt(), "buttons/save.png");
		saveButton.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) { 
				onSaveButtonClick();				
			}   
		}); 		

		detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); //push all buttons to the right 
		detailToolStrip.addButton(saveButton);
		
		VLayout detailLayout = new VLayout();  
		detailLayout.setOverflow(Overflow.HIDDEN);		
		setOverflow(Overflow.AUTO);    			
		
		detailLayout.setMembers(detail, detailToolStrip);		
		
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		setBody(detailLayout);
		
		detail.setCanEdit(true);				
		setHeight(300);
		setWidth(1000);

        setIcon("menu/avvio_procedimento.png");
        show();
	}
	
	public void onSaveButtonClick() {
		if(detail.validate()) {
			final Record lRecordAvvio = detail.getRecordToSave();
			final String idTipoProc = lRecordAvvio.getAttribute("idProcessType");
			final String nomeTipoProc = lRecordAvvio.getAttribute("nomeProcessType");
			final String idTipoDocProposta = lRecordAvvio.getAttribute("idDocType");
			final String nomeTipoDocProposta = lRecordAvvio.getAttribute("nomeDocType");
			final String idTipoFlussoActiviti = lRecordAvvio.getAttribute("flowTypeId");
			final String idUoRevisioneOrganigramma = lRecordAvvio.getAttribute("idUoRevisioneOrganigramma");
			final String idUoPadreRevisioneOrganigramma = lRecordAvvio.getAttribute("idUoPadreRevisioneOrganigramma");			
			final String codRapidoUoRevisioneOrganigramma = lRecordAvvio.getAttribute("codRapidoUoRevisioneOrganigramma");
			final String nomeUoRevisioneOrganigramma = lRecordAvvio.getAttribute("nomeUoRevisioneOrganigramma");
			final String oggetto = "Proposta revisione organigramma " + codRapidoUoRevisioneOrganigramma + " - " + nomeUoRevisioneOrganigramma;
			Record lRecordToSave = new Record();
			lRecordToSave.setAttribute("tipoDocumento", idTipoDocProposta);
			lRecordToSave.setAttribute("oggetto", oggetto);
			lRecordToSave.setAttribute("idUoRevisioneOrganigramma", idUoRevisioneOrganigramma);	
			lRecordToSave.setAttribute("idUoPadreRevisioneOrganigramma", idUoPadreRevisioneOrganigramma);				
			lRecordToSave.setAttribute("codRapidoUoRevisioneOrganigramma", codRapidoUoRevisioneOrganigramma);		
			lRecordToSave.setAttribute("nomeUoRevisioneOrganigramma", nomeUoRevisioneOrganigramma);				
			final GWTRestDataSource lPropostaOrganigrammaDataSource = new GWTRestDataSource("PropostaOrganigrammaDataSource");
			Layout.showWaitPopup("Avvio proposta revisione organigramma in corso...");
			lPropostaOrganigrammaDataSource.addData(lRecordToSave, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record lRecordSaved = response.getData()[0];
						final String idUd = lRecordSaved.getAttribute("idUd");
						final String rowidDoc = lRecordSaved.getAttribute("rowidDoc");
						final String idDocPrimario = lRecordSaved.getAttribute("idDocPrimario");
//						lProtocolloDataSource.getData(lRecordSaved, new DSCallback() {
//
//							@Override
//							public void execute(DSResponse response, Object rawData, DSRequest request) {
//								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {									
									final Record lRecordAvvio = new Record();
									lRecordAvvio.setAttribute("idTipoProc", idTipoProc);
									lRecordAvvio.setAttribute("nomeTipoProc", nomeTipoProc);				
									lRecordAvvio.setAttribute("idTipoFlussoActiviti", idTipoFlussoActiviti);
									lRecordAvvio.setAttribute("idTipoDocProposta", idTipoDocProposta);
									lRecordAvvio.setAttribute("nomeTipoDocProposta", nomeTipoDocProposta);
									lRecordAvvio.setAttribute("idUd", idUd);
									lRecordAvvio.setAttribute("idUoProponente", AurigaLayout.getUoLavoro());
									lRecordAvvio.setAttribute("oggetto", oggetto);	
									lRecordAvvio.setAttribute("siglaProposta", "");	
									Layout.showWaitPopup("Avvio proposta revisione organigramma in corso...");
									GWTRestService<Record, Record> lAvvioIterAttiActivitiDatasource = new GWTRestService<Record, Record>("AvvioIterAttiActivitiDatasource");
									lAvvioIterAttiActivitiDatasource.call(lRecordAvvio, new ServiceCallback<Record>() {
										
										@Override
										public void execute(final Record lRecordAvviato) {
											
											Layout.hideWaitPopup();														
											final String idProcesso = lRecordAvviato.getAttribute("idProcesso");
											final String estremiRegNumUd = lRecordAvviato.getAttribute("estremiRegNumUd");
											final String idUd = lRecordAvviato.getAttribute("idUd");
											Layout.addMessage(new MessageBean("Avviata con successo la proposta di revisione organigramma " + estremiRegNumUd, "", MessageType.INFO));
											if(AurigaLayout.getParametroDBAsBoolean("SKIP_TASK_IN_AVVIO_PROC")) {
												//TODO se non eseguo la prima attività quando apro la proposta l'unica attività disponibile è la prima ma se la apro è vuota
												_window.markForDestroy();
												AurigaLayout.apriDettaglioPratica(idProcesso, estremiRegNumUd);							
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
																							_window.markForDestroy();
																							AurigaLayout.apriDettaglioPratica(idProcesso, estremiRegNumUd); //TODO dobbiamo per forza eseguire la prima attività all'avvio?
																						} else {																		
																							Layout.addMessage(new MessageBean("Errore durante il salvataggio del task di avvio del procedimento", "", MessageType.ERROR));																									
																						}
																					}
																				});
																			}
																		});
																		break;
																	}
																}									
															} else {
																_window.markForDestroy();	
																AurigaLayout.apriDettaglioPratica(idProcesso, estremiRegNumUd);											
															}
														} else {										
															Layout.addMessage(new MessageBean("Errore durante il recupero dei task del procedimento", "", MessageType.ERROR));																	
														}
													}
												});
											}										
										}
									});	
//								}
//							}
//						});
					}
				}
			});
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	public String getDisplayNameEvento(String nome) {
		String displayName = nome;
		int pos = displayName.indexOf("|*|");
		if (pos != -1) {
			displayName = displayName.substring(pos + 3);
		}
		return displayName;
	}
	
	@Override
	public void manageOnCloseClick() {	
		markForDestroy();		
	}

}
