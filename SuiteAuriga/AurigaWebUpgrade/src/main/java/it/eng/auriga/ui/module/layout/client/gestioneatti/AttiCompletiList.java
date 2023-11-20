/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioUD.InvioUDMailWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

public class AttiCompletiList extends AttiList {

	public AttiCompletiList(String nomeEntita) {
		super(nomeEntita);
	}
	
	@Override
	public Menu createUdAltreOpMenu(final ListGridRecord listRecord, final Record detailRecord) {	
		
		final Menu contextMenu = new Menu();
		
		MenuItem visualizzaFileMenuItem = buildVisualizzaFileMenuItem(detailRecord);
		if(visualizzaFileMenuItem != null) {
			contextMenu.addItem(visualizzaFileMenuItem);
		}
		
		MenuItem scaricaFileCompletiXAttiMenuItem = buildScaricaFileCompletiXAttiMenuItem(listRecord, detailRecord);
		if(scaricaFileCompletiXAttiMenuItem != null) {
			contextMenu.addItem(scaricaFileCompletiXAttiMenuItem);
		}
		
		MenuItem smistaPropAttoMenuItem = new MenuItem("Smista", "archivio/assegna.png");
		smistaPropAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				AurigaLayout.smistaAtto(layout, listRecord, null);
			}
		});
		if(isRecordAbilToSmistaPropAtto(detailRecord)) {			
			contextMenu.addItem(smistaPropAttoMenuItem);
		}
		
		MenuItem presaInCaricoPropAttoMenuItem = new MenuItem("Presa in carico", "archivio/prendiInCarico.png");
		presaInCaricoPropAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				presaInCaricoPropAttoButtonClick(listRecord);
			}
		});
		if(isRecordAbilToPresaInCaricoPropAtto(detailRecord)) {			
			contextMenu.addItem(presaInCaricoPropAttoMenuItem);
		}
		
		MenuItem condividiAttoMenuItem = new MenuItem("Condividi", "archivio/condividi.png");
		condividiAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				AurigaLayout.condividiAtto(layout, listRecord, null);
			}
		});
		if(isRecordAbilToCondividiAtto(detailRecord)) {			
			contextMenu.addItem(condividiAttoMenuItem);
		}
		
		MenuItem sottoscrizioneConsMenuItem = new MenuItem("Sottoscrivi", "attiInLavorazione/azioni/sottoscrizioneCons.png");
		sottoscrizioneConsMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				sottoscrizioneConsButtonClick(listRecord);
			}
		});
		if(isRecordAbilToSottoscrizioneCons(detailRecord)) {			
			contextMenu.addItem(sottoscrizioneConsMenuItem);
		}
		
		MenuItem rimuoviSottoscrizioneConsMenuItem = new MenuItem("Rimuovi sottoscrizione", "attiInLavorazione/azioni/rimuoviSottoscrizioneCons.png");
		rimuoviSottoscrizioneConsMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				rimuoviSottoscrizioneConsButtonClick(listRecord);
			}
		});
		if(isRecordAbilToRimuoviSottoscrizioneCons(detailRecord)) {			
			contextMenu.addItem(rimuoviSottoscrizioneConsMenuItem);
		}
		
		MenuItem presentazionePropAttoMenuItem = new MenuItem("Presenta", "attiInLavorazione/azioni/presentazionePropAtto.png");
		presentazionePropAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				presentazionePropAttoButtonClick(listRecord);
			}
		});
		if(isRecordAbilToPresentazionePropAtto(detailRecord)) {			
			contextMenu.addItem(presentazionePropAttoMenuItem);
		}
		
		MenuItem ritiroPropAttoMenuItem = new MenuItem("Ritira", "attiInLavorazione/azioni/ritiroPropAtto.png");
		ritiroPropAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				ritiroPropAttoButtonClick(listRecord);
			}
		});
		if(isRecordAbilToRitiroPropAtto(detailRecord)) {			
			contextMenu.addItem(ritiroPropAttoMenuItem);
		}
		
		MenuItem rilascioVistoMenuItem = new MenuItem("Rilascia visto", "attiInLavorazione/azioni/rilascioVisto.png");
		rilascioVistoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				rilascioVistoButtonClick(listRecord);
			}
		});
		if(isRecordAbilToRilascioVisto(detailRecord)) {			
			contextMenu.addItem(rilascioVistoMenuItem);
		}
		
		MenuItem rifiutoVisto = new MenuItem("Rifiuta visto", "attiInLavorazione/azioni/rifiutoVisto.png");
		rifiutoVisto.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				rifiutoVistoButtonClick(listRecord);
			}
		});
		if(isRecordAbilToRifiutoVisto(detailRecord)) {			
			contextMenu.addItem(rifiutoVisto);
		}
		
		MenuItem mandaLibroFirmaMenuItem = new MenuItem("Manda al libro firma", "attiInLavorazione/mandaLibroFirma.png");
		mandaLibroFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				mandaLibroFirmaMenuItemButtonClick(listRecord);
			}
		});
		if(isRecordAbilInviaALibroFirmaVistoRegCont(detailRecord)) {
			contextMenu.addItem(mandaLibroFirmaMenuItem);	
		}
		
		MenuItem togliLibroFirmaMenuItem = new MenuItem("Togli dal libro firma", "attiInLavorazione/togliLibroFirma.png");
		togliLibroFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				togliLibroFirmaButtonClick(listRecord);
			}
		});
		if(isRecordAbilTogliDaLibroFirmaVistoRegCont(detailRecord)) {
			contextMenu.addItem(togliLibroFirmaMenuItem);	
		}
		
		MenuItem segnaRicontrollareMenuItem = new MenuItem("Segna da ricontrollare", "archivio/flgPresaInCarico/da_fare.png");
		segnaRicontrollareMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				segnaRicontrollareButtonClick(listRecord);
			}
		});
		if(isRecordAbilImpostaStatoAlVistoRegCont(detailRecord)) {
			contextMenu.addItem(segnaRicontrollareMenuItem);	
		}
		
		MenuItem avviaEmendamentoMenuItem = new MenuItem("Aggiungi emendamento", "buttons/modificaDatiReg.png");
		avviaEmendamentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				avviaEmendamentoButtonClick(listRecord);
			}
		});
		if(isRecordAbilToAvviaEmendamento(detailRecord)) {			
			contextMenu.addItem(avviaEmendamentoMenuItem);
		}
		
		MenuItem osservazioniNotificheMenuItem = new MenuItem(I18NUtil.getMessages().osservazioniNotifiche_menu_apri_title(), "osservazioni_notifiche.png");
		osservazioniNotificheMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				osservazioniNotificheButtonClick(listRecord);
			}
		});
		if(isRecordAbilToOsservazioniNotifiche(detailRecord)) {
			contextMenu.addItem(osservazioniNotificheMenuItem);	
		}
		
		MenuItem annullaPropostaAttoMenuItem = new MenuItem("Annulla", "attiInLavorazione/azioni/annullaPropostaAtto.png");
		annullaPropostaAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				annullaPropostaAttoButtonClick(listRecord);
			}
		});
		if(isRecordAbilToAnnullaPropostaAtto(detailRecord)) {			
			contextMenu.addItem(annullaPropostaAttoMenuItem);
		}
		
		String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");				
		MenuItem eventoAMCMenuItem = new MenuItem(lSistAMC != null && !"".equals(lSistAMC) ? "Evento " + lSistAMC : "Evento AMC", "buttons/gear.png");
		eventoAMCMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				eventoAMCButtonClick(listRecord);
			}
		});
		if(isRecordAbilToEventoAMC(detailRecord)) {
			contextMenu.addItem(eventoAMCMenuItem);
		}
		
		MenuItem dettaglioFirmeVistiMenuItem = new MenuItem("Dettaglio firme/visti", "coccarda.png");
		dettaglioFirmeVistiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				dettaglioFirmeVistiButtonClick(listRecord);
			}
		});
		if(isRecordAbilDettaglioFirmeVisti(detailRecord)) {
			contextMenu.addItem(dettaglioFirmeVistiMenuItem);
		}
							
		// Gestione sostituzioni resp. reg. tecnica
		MenuItem modificaSostDirRegTecnicaPropAttoMenuItem = new MenuItem(I18NUtil.getMessages().modificaSostDirRegTecnicaPropAtto_menu_apri_title(), "attiInLavorazione/dirRespRegTecnicaAttoProp.png");		
		modificaSostDirRegTecnicaPropAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
			@Override
			public void onClick(MenuItemClickEvent event) {
				modificaSostDirRegTecnicaPropAttoButtonClick(listRecord, detailRecord);
			}
		});
		if(isRecordAbilToModificaSostDirRegTecnicaPropAtto(detailRecord)) {			
			contextMenu.addItem(modificaSostDirRegTecnicaPropAttoMenuItem);
		}
			
		// Modifica termine raccolta sottoscrizioni
		MenuItem modificaTermineRaccoltaSottoscrizioniPropAttoMenuItem = new MenuItem(I18NUtil.getMessages().modificaTermineRaccoltaSottoscrizioniPropAtto_menu_apri_title(), "attiInLavorazione/termineRaccoltaSottoscrizioniPropAtto.png");
		modificaTermineRaccoltaSottoscrizioniPropAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				modificaTermineRaccoltaSottoscrizioniPropAttoButtonClick(listRecord, detailRecord);
			}
		});
	
		if(isRecordAbilToModificaTermineRaccoltaSottoscrizioniPropAtto(detailRecord)) {			
			contextMenu.addItem(modificaTermineRaccoltaSottoscrizioniPropAttoMenuItem);
		}
		
		// Crea la voce INVIO E-MAIL
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_INVIO_MAIL_PROP_ATTO")) {
			MenuItem invioMailItem = new MenuItem("Invio e-mail", "mail/PEO.png");
			invioMailItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					invioMailButtonClick(listRecord);
				}
			});
			contextMenu.addItem(invioMailItem);
		}
		
		return contextMenu;
	}
	
	protected void invioMailButtonClick(final Record record) {
		
		final Record detailRecord = new Record();
		detailRecord.setAttribute("idProcess", record.getAttribute("idProcedimento"));
		detailRecord.setAttribute("idUd", record.getAttribute("unitaDocumentariaId"));
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaInvioUDMailDatasource");
		lGwtRestService.extraparam.put("tipoMail", "PEO");
		lGwtRestService.extraparam.put("finalita", "INVIO_PROPOSTA_ATTO");
		lGwtRestService.executecustom("preparaEmailAttiDaLista", detailRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if(dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record object = dsResponse.getData()[0];
					boolean isPresentiAllegatiPISep = false;
					RecordList listaAttach = object.getAttributeAsRecordList("attach");
					final RecordList listaAttachWithoutAllegatiPISep = new RecordList();
					if(listaAttach != null) {
						for(int i = 0; i < listaAttach.getLength(); i++) {
							Record attach = listaAttach.get(i);
							if(attach.getAttributeAsBoolean("flgAllegatoPISep")) {
								isPresentiAllegatiPISep = true;
							} else {
								listaAttachWithoutAllegatiPISep.add(attach);
							}
						}
					}
					if(isPresentiAllegatiPISep) {
						SC.ask("Vuoi includere gli allegati parte integrante separati? ", new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								if(value != null && value) {
									InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEO", true, new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {											
											if (layout != null) {
												layout.doSearch();
											}
										}
									});
									lInvioUdMailWindow.loadMail(object);
									lInvioUdMailWindow.show();
								} else {
									object.setAttribute("attach", listaAttachWithoutAllegatiPISep);
									InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEO", true, new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {										
											if (layout != null) {
												layout.doSearch();
											}
										}
									});
									lInvioUdMailWindow.loadMail(object);
									lInvioUdMailWindow.show();
								}							
							}
						});				
					} else {					
						InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEO", true, new DSCallback() {
							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {								
								if (layout != null) {
									layout.doSearch();
								}
							}
						});
						lInvioUdMailWindow.loadMail(object);
						lInvioUdMailWindow.show();
					}
				}	
			}
		}); 
	}
	
	protected boolean isRecordAbilDettaglioFirmeVisti(Record record) {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_DETT_FIRME_ATTO");
	}

	protected boolean isRecordAbilToSottoscrizioneCons(Record record) {
		return AurigaLayout.isAttivaNuovaPropostaAtto2Completa() && record.getAttributeAsBoolean("abilSottoscrizioneCons");
	}
	
	protected boolean isRecordAbilToRimuoviSottoscrizioneCons(Record record) {
		return AurigaLayout.isAttivaNuovaPropostaAtto2Completa() && record.getAttributeAsBoolean("abilRimuoviSottoscrizioneCons");
	}
	
	protected boolean isRecordAbilToPresentazionePropAtto(Record record) {
		return AurigaLayout.isAttivaNuovaPropostaAtto2Completa() && record.getAttributeAsBoolean("abilPresentazionePropAtto");
	}
	
	protected boolean isRecordAbilToRitiroPropAtto(Record record) {
		return AurigaLayout.isAttivaNuovaPropostaAtto2Completa() && record.getAttributeAsBoolean("abilRitiroPropAtto");
	}
	
	protected boolean isRecordAbilToAvviaEmendamento(Record record) {
		return AurigaLayout.isAttivaNuovaPropostaAtto2Completa() && record.getAttributeAsBoolean("abilAvviaEmendamento");
	}
	
	protected boolean isRecordAbilToAnnullaPropostaAtto(Record record) {
		return AurigaLayout.isAttivaNuovaPropostaAtto2Completa() && record.getAttributeAsBoolean("abilAnnullaPropostaAtto");
	}
	
	public void dettaglioFirmeVistiButtonClick(final Record record) {
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("DettaglioFirmeVisteAttoDataSource");
		lGwtRestService.extraparam.put("idUdAtto", record.getAttribute("unitaDocumentariaId"));
		lGwtRestService.call(new Record(), new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				
				if(object != null && object.getAttributeAsRecordList("listaFirmeVisti") != null &&
						!object.getAttributeAsRecordList("listaFirmeVisti").isEmpty()) {
					DettaglioFirmeVistiDetail lDettaglioFirmeVistiDetail = new DettaglioFirmeVistiDetail();
					lDettaglioFirmeVistiDetail.caricaDettaglio(object);
					lDettaglioFirmeVistiDetail.setCanEdit(false);
					String title = "Dettaglio firme/visti "+ object.getAttributeAsString("estremiAtto");
					String icon = "coccarda.png";
					AurigaLayout.addModalWindow("dettaglio_firme_visti", title, icon, lDettaglioFirmeVistiDetail);
				} else {
					Layout.addMessage(new MessageBean("Non sono presenti firme/visti", "", MessageType.INFO));
				}
			}

		});
	}

	public void sottoscrizioneConsButtonClick(final Record record) {
		final RecordList listaRecord = new RecordList();
		listaRecord.add(record);		
		final Record recordToSave = new Record();
		recordToSave.setAttribute("listaRecord", listaRecord);		
		recordToSave.setAttribute("azione", AttiCompletiLayout._SOTTOSCRIZIONE_CONSIGLIERE);
		Layout.showWaitPopup("Sottoscrizione in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
		try {
			lGwtRestDataSource.addData(recordToSave, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {					
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {							
						Record data = response.getData()[0];
						Map errorMessages = data.getAttributeAsMap("errorMessages");
						if (errorMessages != null && errorMessages.size() == 1) {
							String errorMsg = (String) errorMessages.get(record.getAttribute("unitaDocumentariaId"));
							Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
						} else {
							if(layout != null) {
								layout.doSearch();
							}
							Layout.addMessage(new MessageBean("Sottoscrizione effettuata con successo", "", MessageType.INFO));							
						}
					} 
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}	
	
	public void rimuoviSottoscrizioneConsButtonClick(final Record record) {
		final RecordList listaRecord = new RecordList();
		listaRecord.add(record);		
		final Record recordToSave = new Record();
		recordToSave.setAttribute("listaRecord", listaRecord);		
		recordToSave.setAttribute("azione", AttiCompletiLayout._ELIMINAZIONE_SOTTOSCRIZIONE_CONSIGLIERE);
		Layout.showWaitPopup("Rimozione sottoscrizione in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
		try {
			lGwtRestDataSource.addData(recordToSave, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {					
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {							
						Record data = response.getData()[0];
						Map errorMessages = data.getAttributeAsMap("errorMessages");
						if (errorMessages != null && errorMessages.size() == 1) {
							String errorMsg = (String) errorMessages.get(record.getAttribute("unitaDocumentariaId"));
							Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
						} else {
							if(layout != null) {
								layout.doSearch();
							}
							Layout.addMessage(new MessageBean("Rimozione sottoscrizione effettuata con successo", "", MessageType.INFO));							
						}
					} 
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	public void presentazionePropAttoButtonClick(final Record record) {		
		final RecordList listaRecord = new RecordList();
		listaRecord.add(record);		
		final Record recordToSave = new Record();
		recordToSave.setAttribute("listaRecord", listaRecord);	
		recordToSave.setAttribute("azione", AttiCompletiLayout._PRESENTAZIONE_FIRMATARIO);
		Layout.showWaitPopup("Presentazione in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
		try {
			lGwtRestDataSource.addData(recordToSave, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {					
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {							
						Record data = response.getData()[0];
						Map errorMessages = data.getAttributeAsMap("errorMessages");
						if (errorMessages != null && errorMessages.size() == 1) {
							String errorMsg = (String) errorMessages.get(record.getAttribute("unitaDocumentariaId"));
							Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
						} else {
							if(layout != null) {
								layout.doSearch();
							}
							Layout.addMessage(new MessageBean("Presentazione effettuata con successo", "", MessageType.INFO));							
						}
					} 
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	public void ritiroPropAttoButtonClick(final Record record) {		
		final RecordList listaRecord = new RecordList();
		listaRecord.add(record);		
		final Record recordToSave = new Record();
		recordToSave.setAttribute("listaRecord", listaRecord);	
		recordToSave.setAttribute("azione", AttiCompletiLayout._RITIRO_FIRMATARIO);
		MotivoOsservazioniAzioneSuListaAttiPopup lMotivoOsservazioniAzioneSuListaAttiPopup = new MotivoOsservazioniAzioneSuListaAttiPopup("Motivazione ritiro", recordToSave, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				Layout.showWaitPopup("Ritiro in corso: potrebbe richiedere qualche secondo. Attendere…");
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
				try {
					lGwtRestDataSource.addData(object, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {					
							Layout.hideWaitPopup();
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {							
								Record data = response.getData()[0];
								Map errorMessages = data.getAttributeAsMap("errorMessages");
								if (errorMessages != null && errorMessages.size() == 1) {
									String errorMsg = (String) errorMessages.get(record.getAttribute("unitaDocumentariaId"));
									Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
								} else {
									if(layout != null) {
										layout.doSearch();
									}
									Layout.addMessage(new MessageBean("Ritiro effettuato con successo", "", MessageType.INFO));							
								}
							} 
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}		
			}
		});
		lMotivoOsservazioniAzioneSuListaAttiPopup.show();			
	}
	
	public void avviaEmendamentoButtonClick(Record record) {
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", record.getAttribute("unitaDocumentariaId"));
		lNuovaPropostaAtto2DataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {					
					final Record lRecordAtto = response.getData()[0];
					AurigaLayout.avviaEmendamentoSuAtto(lRecordAtto);	
				}
			}
		});
	}
	
	public void annullaPropostaAttoButtonClick(final Record record) {
		final RecordList listaRecord = new RecordList();
		listaRecord.add(record);		
		final Record recordToSave = new Record();
		recordToSave.setAttribute("listaRecord", listaRecord);	
		recordToSave.setAttribute("azione", AttiCompletiLayout._ANNULLAMENTO);
		MotivoOsservazioniAzioneSuListaAttiPopup lMotivoOsservazioniAzioneSuListaAttiPopup = new MotivoOsservazioniAzioneSuListaAttiPopup("Motivazione annullamento", recordToSave, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				Layout.showWaitPopup("Annullamento in corso: potrebbe richiedere qualche secondo. Attendere…");
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
				try {
					lGwtRestDataSource.addData(object, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {					
							Layout.hideWaitPopup();
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {							
								Record data = response.getData()[0];
								Map errorMessages = data.getAttributeAsMap("errorMessages");
								if (errorMessages != null && errorMessages.size() == 1) {
									String errorMsg = (String) errorMessages.get(record.getAttribute("unitaDocumentariaId"));
									Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
								} else {
									if(layout != null) {
										layout.doSearch();
									}
									Layout.addMessage(new MessageBean("Annullamento effettuato con successo", "", MessageType.INFO));							
								}
							} 
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}			
			}
		});
		lMotivoOsservazioniAzioneSuListaAttiPopup.show();		
	}
	
	
	protected boolean isRecordAbilToModificaSostDirRegTecnicaPropAtto(Record record) {
		return record.getAttributeAsBoolean("abilModificaSostDirRegTecnicaPropAtto");
	}
		
	protected boolean isRecordAbilToModificaTermineRaccoltaSottoscrizioniPropAtto(Record record) {
		return record.getAttributeAsBoolean("abilModificaTermineSottoscrizionePropAtto");
	}
	
	public void modificaSostDirRegTecnicaPropAttoButtonClick(final ListGridRecord listRecord, final Record detailRecord) {
		final String idUd      = detailRecord.getAttributeAsString("idUd");
		final String idProcess = detailRecord.getAttributeAsString("idProcess");
		final String activityName = "stesura_testo";
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		lRecord.setAttribute("idProcess", idProcess);
		lRecord.setAttribute("activityName", activityName);
		String numeroProposta = listRecord.getAttribute("numeroProposta");
		String title = I18NUtil.getMessages().modificaSostDirRegTecnicaPropAtto_Popup_Ud_title(numeroProposta);
		new DettaglioDirRespRegTecnicaPropAttoPopup(instance.getLayout(), lRecord, title, true);	
	}	
	
	public void modificaTermineRaccoltaSottoscrizioniPropAttoButtonClick(final ListGridRecord listRecord, final Record detailRecord) {
		
		final String idUd      = detailRecord.getAttributeAsString("idUd");
		final String idProcess = detailRecord.getAttributeAsString("idProcess");
		final String activityName = "stesura_testo";
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		lRecord.setAttribute("idProcess", idProcess);
		lRecord.setAttribute("activityName", activityName);
		String numeroProposta = listRecord.getAttribute("numeroProposta");
		String title = I18NUtil.getMessages().modificaTermineRaccoltaSottoscrizioniPropAtto_Popup_Ud_title(numeroProposta);
		new DettaglioTermineRaccoltaSottoscrizioniPropAttoPopup(lRecord, title, true);
	}
	
	@Override
	protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		try {
			if (getFieldName(colNum).equals("ordinamentoNumeroProposta")) {
				if (record.getAttribute("ordinamentoNumeroProposta") != null &&
						!"".equalsIgnoreCase(record.getAttribute("ordinamentoNumeroProposta"))) {
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