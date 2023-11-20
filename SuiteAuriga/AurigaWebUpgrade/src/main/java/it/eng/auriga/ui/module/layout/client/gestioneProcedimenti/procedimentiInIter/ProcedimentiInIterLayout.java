/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.AssegnazioneProcPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

public class ProcedimentiInIterLayout extends CustomLayout {
	
	protected MultiToolStripButton assegnaMultiButton;
	protected MultiToolStripButton presaInCaricoMultiButton;
	protected MultiToolStripButton mandaAlLibroFirmaAttoMultiButton;
	protected MultiToolStripButton togliDalLibroFirmaAttoMultiButton;

	public ProcedimentiInIterLayout(String nomeEntita) {
		this(nomeEntita, null, null, null);
	}

	public ProcedimentiInIterLayout(String nomeEntita, String finalita) {
		this(nomeEntita, finalita, null, null);
	}

	public ProcedimentiInIterLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola) {
		this(nomeEntita, finalita, flgSelezioneSingola, null);
	}

	public ProcedimentiInIterLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super(nomeEntita, 
				new GWTRestDataSource("ProcedimentiInIterDatasource", "idProcedimento", FieldType.TEXT), 
				new ProcedimentiInIterFilter(nomeEntita), 
				new ProcedimentiInIterList(nomeEntita), 
				new ProcedimentiInIterDetail(nomeEntita), 
				finalita, flgSelezioneSingola, showOnlyDetail);

		setMultiselect(true);
		newButton.hide();
	}
	
	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}
	
	@Override
	public boolean showPaginazioneItems() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PAGINAZIONE_ATTI");
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {

		GWTRestDataSource dataSource = (GWTRestDataSource) getList().getDataSource();
		dataSource.setForceToShowPrompt(false);

		return dataSource;
	}
	
	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}
	
	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {
		
		// Bottone assegna
		if (assegnaMultiButton == null) {
			assegnaMultiButton = new MultiToolStripButton("archivio/assegna.png", this, "Assegna", false) {

				@Override
				public boolean toShow() {
					return Layout.isPrivilegioAttivo("GP/ASS");
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					final AssegnazioneProcPopup assegnazioneProcPopup = new AssegnazioneProcPopup(listaRecord) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							Record record = new Record();
							record.setAttribute("listaRecord", listaRecord);
							record.setAttribute("listaAssegnazione", _form.getValueAsRecordList("listaAssegnazione"));
							Layout.showWaitPopup("Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneProcDataSource");
							try {
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaRecord, "idProcedimento", "numeroProposta",
												"Assegnazione effettuata con successo", "Tutti i record selezionati per l'assegnazione sono andati in errore!",
												"Alcuni dei record selezionati per l'assegnazione sono andati in errore!", callback);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						}
					};
					assegnazioneProcPopup.show();
				}
			};
		}
		
		// Bottone prendi in carico 
		if (presaInCaricoMultiButton == null) {
			presaInCaricoMultiButton = new MultiToolStripButton("archivio/prendiInCarico.png", this, "Prendi in carico", false) {

				@Override
				public boolean toShow() {
					return Layout.isPrivilegioAttivo("IAC/PC");
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					Record record = new Record();
					record.setAttribute("listaRecord", listaRecord);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneProcDataSource");
					try {
						lGwtRestDataSource.executecustom("presaInCarico", record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								massiveOperationWithoutCallback(response, listaRecord, "idProcedimento", "numeroProposta",
									"Presa in carico effettuata con successo", "Tutti i record selezionati per la presa in carico sono andati in errore!",
									"Alcuni dei record selezionati per la presa in carico sono andati in errore!");
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			};
		}
		
		// Bottone manda al libro firma
		if (mandaAlLibroFirmaAttoMultiButton == null) {
			mandaAlLibroFirmaAttoMultiButton = new MultiToolStripButton("attiInLavorazione/mandaLibroFirma.png", this, "Manda al libro firma", false) {
				
				@Override
				public boolean toShow() {
					return isAbilMandaAlLibroFirma();
				}
				
				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					boolean previstaNumerazione = false;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						Record rec = list.getSelectedRecords()[i];
						listaRecord.add(rec);
						if (rec.getAttribute("flgPrevistaNumerazione") != null && "1".equalsIgnoreCase(rec.getAttribute("flgPrevistaNumerazione"))) {
							previstaNumerazione = true;
						}
					}
					
					String paramDBMsgFirmaAttiEntroGiorno = AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO");
					
					if (previstaNumerazione && paramDBMsgFirmaAttiEntroGiorno != null && "WARNING".equalsIgnoreCase(paramDBMsgFirmaAttiEntroGiorno)) {
						SC.warn(I18NUtil.getMessages().atti_list_avviso_Warning_NumerazioneConRegistrazione(), new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								continuaMandaAlLibroFirmaAttoMultiButton(listaRecord);
							}
						});
					} else if (previstaNumerazione && paramDBMsgFirmaAttiEntroGiorno != null && "BLOCCANTE".equalsIgnoreCase(paramDBMsgFirmaAttiEntroGiorno)) {
						SC.warn(I18NUtil.getMessages().atti_list_avviso_Bloccante_NumerazioneConRegistrazione(), new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								continuaMandaAlLibroFirmaAttoMultiButton(listaRecord);
							}
						});
					} else {
						continuaMandaAlLibroFirmaAttoMultiButton(listaRecord);
					}
										
				}	
			};
		}
		
		// Bottone rimuovi dal libro firma
		if (togliDalLibroFirmaAttoMultiButton == null) {
			togliDalLibroFirmaAttoMultiButton = new MultiToolStripButton("attiInLavorazione/togliLibroFirma.png", this, "Togli dal libro firma", false) {
				
				@Override
				public boolean toShow() {
					return isAbiltogliDalLibroFirma();
				}
				
				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					
					Record record = new Record();
					record.setAttribute("listaRecord", listaRecord);
					Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioniLibroFirmaDataSource");
					try {
						lGwtRestDataSource.executecustom("togliDaLibroFirmaDaProcedimentiInIter", record, new DSCallback() {

							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Layout.hideWaitPopup();
									customMassiveOperationCallback(response, listaRecord.getLength(), null);
									doSearch();
								}
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
					
				}
			};
		}
		
		return new MultiToolStripButton[] { assegnaMultiButton, presaInCaricoMultiButton, mandaAlLibroFirmaAttoMultiButton, togliDalLibroFirmaAttoMultiButton};
		
	}
	
	private void continuaMandaAlLibroFirmaAttoMultiButton(final RecordList listaRecord) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaRecord);
		Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioniLibroFirmaDataSource");
		try {
			lGwtRestDataSource.executecustom("mandaALibroFirmaDaProcedimentiInIter", record, new DSCallback() {

				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					Record recordAttributiLibroFirma = response.getData()[0];
					if (recordAttributiLibroFirma != null && recordAttributiLibroFirma.getAttributeAsRecordList("listaRecord") != null) {
						RecordList listaAttiDaLavorare = recordAttributiLibroFirma.getAttributeAsRecordList("listaRecord");
						gestioneInvioLibroFirmaInSequenza(recordAttributiLibroFirma, listaAttiDaLavorare, 0, listaAttiDaLavorare.getLength(), null);
					}
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}	
	
	private void gestioneInvioLibroFirmaInSequenza(final Record recordAttributiInviaALibroFirma, final RecordList listaAttiDaLavorare, final int posAttoDaLavorare, final int numTotaleRecordDaLavorare, Map errorMessages) {
		if (errorMessages == null) {
			errorMessages = new HashMap<String, String>();
		}
		if (listaAttiDaLavorare != null && posAttoDaLavorare < listaAttiDaLavorare.getLength()) {
			Layout.showWaitPopup("Invio al libro firma in corso per il documento N° " + (posAttoDaLavorare + 1) + " di " +  numTotaleRecordDaLavorare + ". Attendere…");
			Record attoDaLavorare = listaAttiDaLavorare.get(posAttoDaLavorare);
			attoDaLavorare.setAttribute("errorMessages", errorMessages);
			final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioniLibroFirmaDataSource");
			lGwtRestDataSource.executecustom("effettuaNumerazionePerInvioALibroFirma", attoDaLavorare, new DSCallback() {
				
				@Override
				public void execute(DSResponse dsResponse1, Object rawData1, DSRequest dsRequest1) {
					if (dsResponse1.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record attoPostNumerazione = dsResponse1.getData()[0];
						if (attoPostNumerazione.getAttribute("esitoNumerazioneOk") != null && attoPostNumerazione.getAttributeAsBoolean("esitoNumerazioneOk")) {
							lGwtRestDataSource.executecustom("generaFileUnioneEAllegatiDaModelloPerInvioALibroFirma", attoPostNumerazione,  new DSCallback() {
								
								@Override
								public void execute(DSResponse dsResponse2, Object rawData2, DSRequest dsRequest2) {
									if (dsResponse2.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record attoPostGenerazione = dsResponse2.getData()[0];
										if (attoPostGenerazione.getAttribute("esitoGenerazioniDaModelloOk") != null && attoPostGenerazione.getAttributeAsBoolean("esitoGenerazioniDaModelloOk")) {
											RecordList listaRecordApposizioneVisto = new RecordList();
											listaRecordApposizioneVisto.add(attoPostGenerazione);
											recordAttributiInviaALibroFirma.setAttribute("listaRecord", listaRecordApposizioneVisto);
											recordAttributiInviaALibroFirma.setAttribute("errorMessages", attoPostGenerazione.getAttributeAsMap("errorMessages"));
											lGwtRestDataSource.executecustom("mandaALibroFirmaCommon", recordAttributiInviaALibroFirma,  new DSCallback() {
						
												@Override
												public void execute(DSResponse dsResponse3, Object rawData3, DSRequest request3) {	
													if (dsResponse3.getStatus() == DSResponse.STATUS_SUCCESS) {
														Record attoPostInvioALibroFirma = dsResponse3.getData()[0];
														Map nuovoErrorMessages = attoPostInvioALibroFirma.getAttributeAsMap("errorMessages");
														gestioneInvioLibroFirmaInSequenza(recordAttributiInviaALibroFirma, listaAttiDaLavorare, posAttoDaLavorare + 1, numTotaleRecordDaLavorare, nuovoErrorMessages);
													} else {
														// Errore nella chiamata al datasource AzioniLibroFirmaDataSource
														Layout.hideWaitPopup();
														Layout.addMessage(new MessageBean("Errore nell'avvio del processo di invio a libro firma", "", MessageType.ERROR));
													}
												}
											});
										} else {
											// La generazione dei file da modello non è andata a buon fine, proseguo con il recordo successivo
											Map nuovoErrorMessages = attoPostGenerazione.getAttributeAsMap("errorMessages");
											gestioneInvioLibroFirmaInSequenza(recordAttributiInviaALibroFirma, listaAttiDaLavorare, posAttoDaLavorare + 1, numTotaleRecordDaLavorare, nuovoErrorMessages);
										}
									} else {
										// Errore nella chiamata al datasource AzioniLibroFirmaDataSource
										Layout.hideWaitPopup();
										Layout.addMessage(new MessageBean("Errore nell'avvio del processo di generazione da modello", "", MessageType.ERROR));
									}
								}
							});
						} else {
							// La numerazione non è andata a buon fine, proseguo con il recordo successivo
							Map nuovoErrorMessages = attoPostNumerazione.getAttributeAsMap("errorMessages");
							gestioneInvioLibroFirmaInSequenza(recordAttributiInviaALibroFirma, listaAttiDaLavorare, posAttoDaLavorare + 1, numTotaleRecordDaLavorare, nuovoErrorMessages);
						}
					} else {
						// Errore nella chiamata al datasource AzioniLibroFirmaDataSource
						Layout.hideWaitPopup();
						Layout.addMessage(new MessageBean("Errore nell'avvio del processo di numerazione", "", MessageType.ERROR));
					}
				}
			});
		} else {
			Layout.hideWaitPopup();
			Record data = new Record();
			data.setAttribute("errorMessages", errorMessages);
			DSResponse response = new DSResponse();
			response.setStatus(DSResponse.STATUS_SUCCESS);
			response.setData(data);
			customMassiveOperationCallback(response, listaAttiDaLavorare.getLength(), "manda_libro_firma");
			doSearch();
		}
	}
	
	public static boolean isAbilMandaAlLibroFirma() {
		return AurigaLayout.isPrivilegioAttivo("IAC/ILF");
	}
	
	public static boolean isAbiltogliDalLibroFirma() {
		return AurigaLayout.isPrivilegioAttivo("IAC/ILF");
	}
	
	public void massiveOperationCallback(DSResponse response, RecordList lista, String pkField, String nameField, String successMessage,
			String completeErrorMessage, String partialErrorMessage, DSCallback callback) {
		
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			int[] recordsToSelect = null;
			RecordList listaErrori = new RecordList();
			if (errorMessages != null && errorMessages.size() > 0) {
				recordsToSelect = new int[errorMessages.size()];
				if (lista.getLength() > errorMessages.size()) {
					errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
				} else {
					errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				}
				int rec = 0;
				for (int i = 0; i < lista.getLength(); i++) {
					Record record = lista.get(i);
					if (errorMessages.get(record.getAttribute(pkField)) != null) {
						Record recordErrore = new Record();
						recordsToSelect[rec++] = list.getRecordIndex(record);
						errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));
						recordErrore.setAttribute("idError", record.getAttribute(nameField));
						recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
						listaErrori.add(recordErrore);
					}
				}
			}
			doSearchAndSelectRecords(recordsToSelect);
			Layout.hideWaitPopup();
			if(listaErrori != null && listaErrori.getLength() > 0) {
				ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Estremi", listaErrori, lista.getLength(), 600, 300);
				errorePopup.show();
			} else if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		} else {
			Layout.hideWaitPopup();
		}
	}
	
	public void customMassiveOperationCallback(DSResponse dsResponse, int recorSelezionati, String operazione) {
		Record data = dsResponse.getData()[0];
		Map errorMessages = data.getAttributeAsMap("errorMessages");

		RecordList listaErrori = new RecordList();
		List<String> listKey = new ArrayList<String>(errorMessages.keySet());

		for(String keyRecordError : listKey) {
			Record recordErrore = new Record();
			recordErrore.setAttribute("idError", keyRecordError);
			recordErrore.setAttribute("descrizione",errorMessages.get(keyRecordError));
			listaErrori.add(recordErrore);
		}

		if (listaErrori != null && listaErrori.getLength() > 0) {
			ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Procedimento",
					listaErrori, recorSelezionati, 600, 300);
			errorePopup.show();
		} else { 
			if(operazione != null && "manda_libro_firma".equalsIgnoreCase(operazione)) {
				Layout.addMessage(new MessageBean("Operazione effettuata con successo: accedere alla funzione \"libro firma\" per firmare i documenti selezionati", "", MessageType.INFO));
			} else {
				Layout.addMessage(new MessageBean("Operazione effettuata con successo", "", MessageType.INFO));
			}
			
		}
	}
	
	public void massiveOperationWithoutCallback(DSResponse response, RecordList lista, String pkField, String nameField, String successMessage,
			String completeErrorMessage, String partialErrorMessage) {
		
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			int[] recordsToSelect = null;
			RecordList listaErrori = new RecordList();
			if (errorMessages != null && errorMessages.size() > 0) {
				recordsToSelect = new int[errorMessages.size()];
				if (lista.getLength() > errorMessages.size()) {
					errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
				} else {
					errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				}
				int rec = 0;
				for (int i = 0; i < lista.getLength(); i++) {
					Record record = lista.get(i);
					Record recordErrore = new Record();
					if (errorMessages.get(record.getAttribute(pkField)) != null) {
						recordsToSelect[rec++] = list.getRecordIndex(record);
						errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));
						recordErrore.setAttribute("idError", record.getAttribute(pkField));
					}
					recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
					listaErrori.add(recordErrore);
				}
			}
			doSearchAndSelectRecords(recordsToSelect);
			Layout.hideWaitPopup();
			if(listaErrori != null && listaErrori.getLength() > 0) {
				ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Estremi", listaErrori, lista.getLength(), 600, 300);
				errorePopup.show();
			} else if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				doSearch();
			}
		} else {
			Layout.hideWaitPopup();
		}
	}

}