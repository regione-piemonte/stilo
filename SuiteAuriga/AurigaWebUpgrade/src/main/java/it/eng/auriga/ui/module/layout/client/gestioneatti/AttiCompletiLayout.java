/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.archivio.SegnaComeVisionatoPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.ConfrontaDocumentiLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

/**
 * Visualizza la lista degli atti in lavorazione
 * 
 * @author massimo malvestio
 *
 */
public class AttiCompletiLayout extends AttiLayout {
	
	public static final String _SOTTOSCRIZIONE_CONSIGLIERE = "SOTTOSCRIZIONE_CONSIGLIERE";
	public static final String _ELIMINAZIONE_SOTTOSCRIZIONE_CONSIGLIERE = "ELIMINAZIONE_SOTTOSCRIZIONE_CONSIGLIERE";
	public static final String _PRESENTAZIONE_FIRMATARIO = "PRESENTAZIONE_FIRMATARIO";
	public static final String _RITIRO_FIRMATARIO = "RITIRO_FIRMATARIO";
	public static final String _ANNULLAMENTO = "ANNULLAMENTO";
	public static final String _APPOSIZIONE_TAG = "APPOSIZIONE_TAG";
	public static final String _ELIMINAZIONE_TAG = "ELIMINAZIONE_TAG";
	public static final String _SBLOCCO_INVIO_TARDIVO_RAG = "SBLOCCO_INVIO_TARDIVO_RAG";
	public static final String _BLOCCO_INVIO_TARDIVO_RAG = "BLOCCO_INVIO_TARDIVO_RAG";
	
	protected MultiToolStripButton sottoscrizioneConsMultiButton;
	protected MultiToolStripButton rimuoviSottoscrizioneConsMultiButton;
	protected MultiToolStripButton presentazionePropAttoMultiButton;
	protected MultiToolStripButton ritiroPropAttoMultiButton;
	protected MultiToolStripButton annullaPropostaAttoMultiButton;
	protected MultiToolStripButton visionatoMultiButton;
	protected MultiToolStripButton apposizioneTagMultiButton;
	protected MultiToolStripButton eliminazioneTagMultiButton;
	protected MultiToolStripButton consentiInvioTardRagMultiButton;
	protected MultiToolStripButton inibisciInvioTardRagMultiButton;
	protected MultiToolStripButton confrontaDocumentiMultiButton;
	
	public AttiCompletiLayout(String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail,
			String pFinalita, Boolean pFlgSelezioneSingola, Boolean pShowOnlyDetail) {
		
		super(nomeEntita, pDatasource, pFilter, pList, pDetail, pFinalita, pFlgSelezioneSingola, pShowOnlyDetail);

	}
	
	public static boolean isAbilSottoscrizioneCons() {
		return AurigaLayout.isPrivilegioAttivo("ATT/ASC");
	}
	
	public static boolean isAbilRimuoviSottoscrizioneCons() {
		return AurigaLayout.isPrivilegioAttivo("ATT/DSC");
	}
	
	public static boolean isAbilPresentazionePropAtto() {
		return AurigaLayout.isPrivilegioAttivo("ATT/PRS");
	}
	
	public static boolean isAttivoConfrontoAtti() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_CONFRONTO_ATTI");
	}
	
	public static boolean isAbilRitiroPropAtto() {
		return AurigaLayout.isPrivilegioAttivo("ATT/RIT") || AurigaLayout.isPrivilegioAttivo("ATT/FRI");
	}
	
	public static boolean isAbilAvviaEmendamento() {
		return AurigaLayout.isPrivilegioAttivo("ATT/AEM");
	}
	
	public static boolean isAbilAnnullaPropostaAtto() {
		return AurigaLayout.isPrivilegioAttivo("ATT/ANN");
	}
	
	public static boolean isAbilInvioTardivoRagioneria() {
		return AurigaLayout.isPrivilegioAttivo("ATT/ITR");
	}
	
	@Override
	public boolean showMultiselectButtonsUnderList() {
		// qui non posso guardare solo se è attivo lo smistamento atti perchè ho anche i bottoni "Presenta" e "Sottoscrivi"
//		return isAttivoSmistamentoAtti();
		return true;
	}

	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {

		// chiamo il super per creare anche gli altri bottoni da aggiungere alle operazioni massive
		super.getMultiselectButtons();
		
		List<MultiToolStripButton> listaMultiselectButtons = new ArrayList<MultiToolStripButton>();

		if (smistaPropAttoMultiButton != null) {
			listaMultiselectButtons.add(smistaPropAttoMultiButton);		
		}
		if (presaInCaricoAttoMultiButton != null) {
			listaMultiselectButtons.add(presaInCaricoAttoMultiButton);		
		}
		if (condividiAttoMultiButton != null) {
			listaMultiselectButtons.add(condividiAttoMultiButton);		
		}
		if (rilascioVistoMultiButton != null) {
			listaMultiselectButtons.add(rilascioVistoMultiButton);	
		}
		if (rifiutoVistoMultiButton != null) {
			listaMultiselectButtons.add(rifiutoVistoMultiButton);	
		}
		if (mandaAlLibroFirmaAttoMultiButton != null) { 
			listaMultiselectButtons.add(mandaAlLibroFirmaAttoMultiButton);	
		}
		if (togliDalLibroFirmaAttoMultiButton != null) {
			listaMultiselectButtons.add(togliDalLibroFirmaAttoMultiButton);	
		}
		if (segnaDaRicontrollareAttoMultiButton != null) { 
			listaMultiselectButtons.add(segnaDaRicontrollareAttoMultiButton);	
		}
		
		if (sottoscrizioneConsMultiButton == null) {
			sottoscrizioneConsMultiButton = new MultiToolStripButton("attiInLavorazione/azioni/sottoscrizioneCons.png", this, "Sottoscrivi", false) {

				@Override
				public boolean toShow() {
					return isAbilSottoscrizioneCons();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}						
					final Record recordToSave = new Record();
					recordToSave.setAttribute("listaRecord", listaRecord);		
					recordToSave.setAttribute("azione", _SOTTOSCRIZIONE_CONSIGLIERE);
					Layout.showWaitPopup("Sottoscrizione in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
					try {
						lGwtRestDataSource.addData(recordToSave, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {					
								massiveOperationCallback(response, listaRecord, "unitaDocumentariaId", "numeroProposta",
										"Sottoscrizione effettuata con successo",
										"Tutti i record selezionati per la sottoscrizione sono andati in errore!",
										"Alcuni dei record selezionati per la sottoscrizione sono andati in errore!", null);
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			};
		}
		listaMultiselectButtons.add(sottoscrizioneConsMultiButton);	
		
		if (rimuoviSottoscrizioneConsMultiButton == null) {
			rimuoviSottoscrizioneConsMultiButton = new MultiToolStripButton("attiInLavorazione/azioni/rimuoviSottoscrizioneCons.png", this, "Rimuovi sottoscrizione", false) {

				@Override
				public boolean toShow() {
					return isAbilRimuoviSottoscrizioneCons();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}						
					final Record recordToSave = new Record();
					recordToSave.setAttribute("listaRecord", listaRecord);		
					recordToSave.setAttribute("azione", _ELIMINAZIONE_SOTTOSCRIZIONE_CONSIGLIERE);
					Layout.showWaitPopup("Rimozione sottoscrizione in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
					try {
						lGwtRestDataSource.addData(recordToSave, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {					
								massiveOperationCallback(response, listaRecord, "unitaDocumentariaId", "numeroProposta",
										"Rimozione sottoscrizione effettuata con successo",
										"Tutti i record selezionati per la rimozione sottoscrizione sono andati in errore!",
										"Alcuni dei record selezionati per la rimozione sottoscrizione sono andati in errore!", null);
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			};
		}
		listaMultiselectButtons.add(rimuoviSottoscrizioneConsMultiButton);	
		
		if (presentazionePropAttoMultiButton == null) {
			presentazionePropAttoMultiButton = new MultiToolStripButton("attiInLavorazione/azioni/presentazionePropAtto.png", this, "Presenta", false) {

				@Override
				public boolean toShow() {
					return isAbilPresentazionePropAtto();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}						
					final Record recordToSave = new Record();
					recordToSave.setAttribute("listaRecord", listaRecord);		
					recordToSave.setAttribute("azione", _PRESENTAZIONE_FIRMATARIO);
					Layout.showWaitPopup("Presentazione in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
					try {
						lGwtRestDataSource.addData(recordToSave, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {					
								massiveOperationCallback(response, listaRecord, "unitaDocumentariaId", "numeroProposta",
										"Presentazione effettuata con successo",
										"Tutti i record selezionati per la presentazione sono andati in errore!",
										"Alcuni dei record selezionati per la presentazione sono andati in errore!", null);
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			};
		}
		listaMultiselectButtons.add(presentazionePropAttoMultiButton);	
		
		if (ritiroPropAttoMultiButton == null) {
			ritiroPropAttoMultiButton = new MultiToolStripButton("attiInLavorazione/azioni/ritiroPropAtto.png", this, "Ritira", false) {

				@Override
				public boolean toShow() {
					return isAbilRitiroPropAtto();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}						
					final Record recordToSave = new Record();
					recordToSave.setAttribute("listaRecord", listaRecord);		
					recordToSave.setAttribute("azione", _RITIRO_FIRMATARIO);
					MotivoOsservazioniAzioneSuListaAttiPopup lMotivoOsservazioniAzioneSuListaAttiPopup = new MotivoOsservazioniAzioneSuListaAttiPopup("Motivazione ritiro", recordToSave, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							Layout.showWaitPopup("Ritiro in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
							try {
								lGwtRestDataSource.addData(object, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {					
										massiveOperationCallback(response, listaRecord, "unitaDocumentariaId", "numeroProposta",
												"Ritiro effettuato con successo",
												"Tutti i record selezionati per il ritiro sono andati in errore!",
												"Alcuni dei record selezionati per il ritiro sono andati in errore!", null);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}				
						}
					});
					lMotivoOsservazioniAzioneSuListaAttiPopup.show();					
				}
			};
		}
		listaMultiselectButtons.add(ritiroPropAttoMultiButton);	
		
		if (annullaPropostaAttoMultiButton == null) {
			annullaPropostaAttoMultiButton = new MultiToolStripButton("attiInLavorazione/azioni/annullaPropostaAtto.png", this, "Annulla", false) {

				@Override
				public boolean toShow() {
					return isAbilAnnullaPropostaAtto();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}						
					final Record recordToSave = new Record();
					recordToSave.setAttribute("listaRecord", listaRecord);		
					recordToSave.setAttribute("azione", _ANNULLAMENTO);					
					MotivoOsservazioniAzioneSuListaAttiPopup lMotivoOsservazioniAzioneSuListaAttiPopup = new MotivoOsservazioniAzioneSuListaAttiPopup("Motivazione annullamento", recordToSave, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							Layout.showWaitPopup("Annullamento in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
							try {
								lGwtRestDataSource.addData(object, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {					
										massiveOperationCallback(response, listaRecord, "unitaDocumentariaId", "numeroProposta",
												"Annullamento effettuato con successo",
												"Tutti i record selezionati per l'annullamento sono andati in errore!",
												"Alcuni dei record selezionati per l'annullamento sono andati in errore!", null);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}				
						}
					});
					lMotivoOsservazioniAzioneSuListaAttiPopup.show();
				}
			};
		}
		listaMultiselectButtons.add(annullaPropostaAttoMultiButton);	
		
		if (visionatoMultiButton == null) {
			visionatoMultiButton = new MultiToolStripButton("postaElettronica/flgRicevutaLettura.png", this, "Segna come visionato", false) {

				@Override
				public boolean toShow() {
					return true;
				}

				@Override
				public void doSomething() {
					
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					String title = "Indica documentazione come visionata";
					
					SegnaComeVisionatoPopup segnaComeVisionatoPopup = new SegnaComeVisionatoPopup(title, null, null) {

						@Override
						public void onClickOkButton(Record object, final DSCallback callback) {
							
							RecordList listaUdFolderNoteAggiornate = new RecordList();
							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								Record idUd = list.getSelectedRecords()[i];
								String noteInserita = object.getAttribute("note");
								Record idUdNoteAggiornate = new Record();
								idUdNoteAggiornate.setAttribute("unitaDocumentariaId", idUd.getAttribute("unitaDocumentariaId"));
								idUdNoteAggiornate.setAttribute("note", noteInserita);
								
								listaUdFolderNoteAggiornate.add(idUdNoteAggiornate);
							}
							
							Record lRecordSelezionatiNoteAggiornate = new Record();
							lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);
							final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
							lGwtRestDataSource.executecustom("segnaComeVisionato", lRecordSelezionatiNoteAggiornate, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
											"Operazione effettuata con successo",
											"Tutti i record selezionati sono andati in errore!",
											"Alcuni dei record selezionati sono andati in errore!", callback);
								}
							});
						}
					};
					segnaComeVisionatoPopup.show();
				}
			};
		}
		listaMultiselectButtons.add(visionatoMultiButton);
		
		/**
		 * Apposizione Tag
		 */
		if (apposizioneTagMultiButton == null) {
			apposizioneTagMultiButton = new MultiToolStripButton("postaElettronica/apposizione_tag_commenti.png", this,
					"Apposizione tag", false) {

				@Override
				public void doSomething() {
					final RecordList listaAtti = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++){
						listaAtti.add(list.getSelectedRecords()[i]);
					}
					new TagCommentiAttiCompletiWindow("apposizione_tag_atti_completi",_APPOSIZIONE_TAG,
							listaAtti,new DSCallback() {
								
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							doSearch();
						}
					});
				}
			};
		}
		listaMultiselectButtons.add(apposizioneTagMultiButton);
		
		/**
		 * Rimozione Tag 
		 */
		if (eliminazioneTagMultiButton == null) {
			eliminazioneTagMultiButton = new MultiToolStripButton("postaElettronica/eliminazione_tag_commenti.png", 
					this, "Eliminazione tag", false) {

				@Override
				public void doSomething() {
					final RecordList listaAtti = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++){
						listaAtti.add(list.getSelectedRecords()[i]);
					}
					new TagCommentiAttiCompletiWindow("eliminazione_tag_atti_completi",_ELIMINAZIONE_TAG,
							listaAtti,new DSCallback() {
								
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							doSearch();
						}
					});
				}
			};
		}
		listaMultiselectButtons.add(eliminazioneTagMultiButton);
		
		/**
		 * Confronta atti 
		 */
		if (confrontaDocumentiMultiButton == null) {
			confrontaDocumentiMultiButton = new MultiToolStripButton("buttons/confronta_documenti_piccolo.png", this, "Confronta documenti", false) {
				
				@Override
				public boolean toShow() {
					return isAttivoConfrontoAtti();
				}
				
				@Override
				public void doSomething() {
					final RecordList listaAtti = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++){
						Record recordToAdd = new Record();
						recordToAdd.setAttribute("idUd", list.getSelectedRecords()[i].getAttribute("unitaDocumentariaId"));
						listaAtti.add(recordToAdd);
					}
					ConfrontaDocumentiLayout.showConfrontaDocumenti(listaAtti);
				}
			};
		}
		listaMultiselectButtons.add(confrontaDocumentiMultiButton);
		
		/**
		 * Consenti invio tardivo alla Ragioneria
		 */
		if (consentiInvioTardRagMultiButton == null) {
			consentiInvioTardRagMultiButton = new MultiToolStripButton("attiInLavorazione/azioni/consentiInvioTardivoRagioneria.png", 
					this, "Consenti invio tardivo alla Ragioneria", false) {
				
				@Override
				public boolean toShow() {
					return isAbilInvioTardivoRagioneria();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++){
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					final Record recordToSave = new Record();
					recordToSave.setAttribute("listaRecord", listaRecord);		
					recordToSave.setAttribute("azione", _SBLOCCO_INVIO_TARDIVO_RAG);
					Layout.showWaitPopup("Consenti invio tardivo alla Ragioneria in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
					try {
						lGwtRestDataSource.addData(recordToSave, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {					
								massiveOperationCallback(response, listaRecord, "unitaDocumentariaId", "numeroProposta",
										"Consentito invio tardivo alla Ragioneria effettuato con successo",
										"Tutti i record selezionati per l'invio consentito tardivo alla Ragioneria sono andati in errore!",
										"Alcuni dei record selezionati per l'invio consentito tardivo alla Ragioneria sono andati in errore!", null);
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			};
		}
		listaMultiselectButtons.add(consentiInvioTardRagMultiButton);
		
		/**
		 * Inibisci invio tardivo alla Ragioneria
		 */
		if (inibisciInvioTardRagMultiButton == null) {
			inibisciInvioTardRagMultiButton = new MultiToolStripButton("attiInLavorazione/azioni/inibisciInvioTardivoRagioneria.png", 
					this, "Inibisci invio tardivo alla Ragioneria", false) {
				
				@Override
				public boolean toShow() {
					return isAbilInvioTardivoRagioneria();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++){
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					final Record recordToSave = new Record();
					recordToSave.setAttribute("listaRecord", listaRecord);		
					recordToSave.setAttribute("azione", _BLOCCO_INVIO_TARDIVO_RAG);
					Layout.showWaitPopup("Inibisci invio tardivo alla Ragioneria in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
					try {
						lGwtRestDataSource.addData(recordToSave, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {					
								massiveOperationCallback(response, listaRecord, "unitaDocumentariaId", "numeroProposta",
										"Inibito invio tardivo alla Ragioneria effettuato con successo",
										"Tutti i record selezionati per l'invio inibito tardivo alla Ragioneria sono andati in errore!",
										"Alcuni dei record selezionati per l'invio inibito tardivo alla Ragioneria sono andati in errore!", null);
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			};
		}
		listaMultiselectButtons.add(inibisciInvioTardRagMultiButton);
	
		if (eventoAMCMultiButton != null) {
			listaMultiselectButtons.add(eventoAMCMultiButton);	
		}
	
		return listaMultiselectButtons.toArray(new MultiToolStripButton[listaMultiselectButtons.size()]);
	}

}