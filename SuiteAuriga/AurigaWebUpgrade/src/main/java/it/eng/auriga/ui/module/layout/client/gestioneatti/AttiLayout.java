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
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

/**
 * Visualizza la lista degli atti in lavorazione
 * 
 * @author massimo malvestio
 *
 */
public class AttiLayout extends CustomLayout {
	
	public static final String _RILASCIO_VISTO = "RILASCIO_VISTO";
	public static final String _RIFIUTO_VISTO = "RIFIUTO_VISTO";
	
	public static final String _EVENTO_AMC = "EVENTO_AMC";

	protected MultiToolStripButton smistaPropAttoMultiButton;
	protected MultiToolStripButton presaInCaricoAttoMultiButton;
	protected MultiToolStripButton condividiAttoMultiButton;
	protected MultiToolStripButton rilascioVistoMultiButton;
	protected MultiToolStripButton rifiutoVistoMultiButton;	
	protected MultiToolStripButton mandaAlLibroFirmaAttoMultiButton;
	protected MultiToolStripButton togliDalLibroFirmaAttoMultiButton;
	protected MultiToolStripButton segnaDaRicontrollareAttoMultiButton;
	protected MultiToolStripButton zipVistoContabileButton;
	protected MultiToolStripButton eventoAMCMultiButton;
	
	public AttiLayout(String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail,
			String pFinalita, Boolean pFlgSelezioneSingola, Boolean pShowOnlyDetail) {
		
		super(nomeEntita, pDatasource, pFilter, pList, pDetail, pFinalita, pFlgSelezioneSingola, pShowOnlyDetail);

		if (showMultiselectButtonsUnderList()) {
			setMultiselect(true);
		} else {
			multiselectButton.hide();
		}
		newButton.hide();
	}
	
	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}

	public static boolean isAttivoSmistamentoAtti() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_SMISTAMENTO_ATTI");
	}
	
	public static boolean isAttivaPresaInCaricoPropAtto() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PRESA_IN_CARICO_ATTI");
	}
	
	public static boolean isAttivaCondivisioneAtti() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_CONDIVISIONE_ATTI");
	}
	
	public static boolean isAbilOsservazioniNotifiche() {
		return AurigaLayout.isPrivilegioAttivo("ATT/OSS");
	}
	
	public static boolean isAbilMandaAlLibroFirma() {
		return AurigaLayout.isPrivilegioAttivo("ATT/ILF");
	}
	
	public static boolean isAbiltogliDalLibroFirma() {
		return AurigaLayout.isPrivilegioAttivo("ATT/ILF");
	}
	
	public static boolean isAbilSegnaDaRicontrollare() {
		return AurigaLayout.isPrivilegioAttivo("ATT/ISV");
	}
	
	public static boolean isAbilZipVistoContabile() {
		return AurigaLayout.isPrivilegioAttivo("ATT/FVC") || AurigaLayout.isPrivilegioAttivo("ATT/ILF");
	}	
	
	public static boolean isAbilRilascioVisto() {
		return AurigaLayout.isPrivilegioAttivo("ATT/RLV");
	}
	
	public static boolean isAbilRifiutoVisto() {
		return AurigaLayout.isPrivilegioAttivo("ATT/RFV");
	}
	
	public static boolean isAbilEventoAMC() {
		return AurigaLayout.isPrivilegioAttivo("ATT/AMC");
	}
	
	@Override
	public boolean showFunzGestioneSubordinati() {
		return Layout.isPrivilegioAttivo("#RESPONSABILE");
	}
	
	@Override
	public boolean showMultiselectButtonsUnderList() {
		return isAttivoSmistamentoAtti();
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

		List<MultiToolStripButton> listaMultiselectButtons = new ArrayList<MultiToolStripButton>();
		
		if (smistaPropAttoMultiButton == null) {
			smistaPropAttoMultiButton = new MultiToolStripButton("archivio/assegna.png", this, "Smista", false) {

				@Override
				public boolean toShow() {
					return isAttivoSmistamentoAtti();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					new SmistamentoAttiPopup((listaRecord.getLength() == 1) ? listaRecord.get(0) : null) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							Record record = new Record();
							record.setAttribute("listaRecord", listaRecord);
							
							if(AurigaLayout.isAttivoClienteCOTO()) {
								record.setAttribute("listaUfficioLiquidatore", _form.getValueAsRecordList("listaUfficioLiquidatore"));
							}
							
							if(AurigaLayout.isAttivoSmistamentoAttiGruppi()) {
								record.setAttribute("smistamentoGruppi", _form.getValue("smistamentoGruppi"));
							}
							
							if(AurigaLayout.isAttivoSmistamentoAttiRagioneria()) {
								record.setAttribute("assegnatarioSmistamentoRagioneria", _form.getValue("assegnatarioSmistamentoRagioneria"));
								record.setAttribute("smistamentoRagioneria", _form.getValue("smistamentoRagioneria"));							
							} else {
								record.setAttribute("listaSmistamento", _form.getValueAsRecordList("listaSmistamento"));
							}
	
							Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("SmistamentoAttiDataSource");
							try {
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaRecord, "idProcedimento", "numeroProposta",
												"Smistamento effettuato con successo",
												"Tutti i record selezionati per lo smistamento sono andati in errore!",
												"Alcuni dei record selezionati per lo smistamento sono andati in errore!", callback);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						}
					};
				}
			};
		}
		listaMultiselectButtons.add(smistaPropAttoMultiButton);	
		
		if (presaInCaricoAttoMultiButton == null) {
			presaInCaricoAttoMultiButton = new MultiToolStripButton("archivio/prendiInCarico.png", this, "Presa in carico", false) {

				@Override
				public boolean toShow() {
					return isAttivaPresaInCaricoPropAtto();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					
					Record record = new Record();
					record.setAttribute("listaRecord", listaRecord);
													
					Layout.showWaitPopup("Presa in carico in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("SmistamentoAttiDataSource");
					lGwtRestDataSource.extraparam.put("operazione", "PRESA_IN_CARICO");
					try {
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								massiveOperationCallback(response, listaRecord, "idProcedimento", "numeroProposta",
										"Presa in carico effettuata con successo",
										"Tutti i record selezionati per la presa in carico sono andati in errore!",
										"Alcuni dei record selezionati per la presa in carico sono andati in errore!", null);
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			};
		}
		listaMultiselectButtons.add(presaInCaricoAttoMultiButton);	
				
		if (condividiAttoMultiButton == null) {
			condividiAttoMultiButton = new MultiToolStripButton("archivio/condividi.png", this, "Condividi", false) {

				@Override
				public boolean toShow() {
					return isAttivaCondivisioneAtti();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					new CondivisioneAttiPopup((listaRecord.getLength() == 1) ? listaRecord.get(0) : null) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							Record record = new Record();
							record.setAttribute("listaRecord", listaRecord);							
							record.setAttribute("listaCondivisione", _form.getValueAsRecordList("listaCondivisione"));
							
							Layout.showWaitPopup("Condivisione in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneAttiDataSource");
							try {
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaRecord, "idProcedimento", "numeroProposta",
												"Condivisione effettuata con successo",
												"Tutti i record selezionati per la condivisione sono andati in errore!",
												"Alcuni dei record selezionati per la condivisione sono andati in errore!", callback);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						}
					};
				}
			};
		}
		listaMultiselectButtons.add(condividiAttoMultiButton);	
		
		if (rilascioVistoMultiButton == null) {
			rilascioVistoMultiButton = new MultiToolStripButton("attiInLavorazione/azioni/rilascioVisto.png", this, "Rilascia visto", false) {

				@Override
				public boolean toShow() {
					return isAbilRilascioVisto();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						Record rec = list.getSelectedRecords()[i];
						final Record recordAtto = new Record();
						// questa operazione deve funzionare sia da lista atti completi che in quella vecchia degli atti
						// quindi passo un record con solo le property unitaDocumentariaId e idProcedimento (che si trovano in entrambi i bean)
						recordAtto.setAttribute("unitaDocumentariaId", rec.getAttribute("unitaDocumentariaId"));
						recordAtto.setAttribute("idProcedimento", rec.getAttribute("idProcedimento"));						
						recordAtto.setAttribute("flgPrevistaNumerazione", rec.getAttribute("flgPrevistaNumerazione"));
						recordAtto.setAttribute("flgGeneraFileUnionePerLibroFirma", rec.getAttribute("flgGeneraFileUnionePerLibroFirma"));
						recordAtto.setAttribute("activityName", rec.getAttribute("activityName"));
						recordAtto.setAttribute("numeroProposta", rec.getAttribute("numeroProposta"));
						recordAtto.setAttribute("prossimoTaskAppongoFirmaVisto", rec.getAttribute("prossimoTaskAppongoFirmaVisto"));
						recordAtto.setAttribute("prossimoTaskRifiutoFirmaVisto", rec.getAttribute("prossimoTaskRifiutoFirmaVisto"));
						listaRecord.add(recordAtto);
					}						
					continuaRilascioVistoMultiButton(listaRecord);					
				}
			};
		}
		listaMultiselectButtons.add(rilascioVistoMultiButton);	
		
		if (rifiutoVistoMultiButton == null) {
			rifiutoVistoMultiButton = new MultiToolStripButton("attiInLavorazione/azioni/rifiutoVisto.png", this, "Rifiuta visto", false) {

				@Override
				public boolean toShow() {
					return isAbilRifiutoVisto();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						final Record recordAtto = new Record();
						// questa operazione deve funzionare sia da lista atti completi che in quella vecchia degli atti
						// quindi passo un record con solo le property unitaDocumentariaId e idProcedimento (che si trovano in entrambi i bean)
						recordAtto.setAttribute("unitaDocumentariaId", list.getSelectedRecords()[i].getAttribute("unitaDocumentariaId"));
						recordAtto.setAttribute("idProcedimento", list.getSelectedRecords()[i].getAttribute("idProcedimento"));
						recordAtto.setAttribute("flgPrevistaNumerazione", list.getSelectedRecords()[i].getAttribute("flgPrevistaNumerazione"));
						recordAtto.setAttribute("flgGeneraFileUnionePerLibroFirma", list.getSelectedRecords()[i].getAttribute("flgGeneraFileUnionePerLibroFirma"));
						recordAtto.setAttribute("activityName", list.getSelectedRecords()[i].getAttribute("activityName"));
						recordAtto.setAttribute("numeroProposta", list.getSelectedRecords()[i].getAttribute("numeroProposta"));
						recordAtto.setAttribute("prossimoTaskAppongoFirmaVisto", list.getSelectedRecords()[i].getAttribute("prossimoTaskAppongoFirmaVisto"));
						recordAtto.setAttribute("prossimoTaskRifiutoFirmaVisto", list.getSelectedRecords()[i].getAttribute("prossimoTaskRifiutoFirmaVisto"));
						listaRecord.add(recordAtto);
					}						
					continuaRifiutoVistoMultiButton(listaRecord);					
				}
			};
		}
		listaMultiselectButtons.add(rifiutoVistoMultiButton);
	
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
		listaMultiselectButtons.add(mandaAlLibroFirmaAttoMultiButton);	
		
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
						lGwtRestDataSource.executecustom("togliDaLibroFirma", record, new DSCallback() {

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
		listaMultiselectButtons.add(togliDalLibroFirmaAttoMultiButton);	
		
		if (segnaDaRicontrollareAttoMultiButton == null) {
			segnaDaRicontrollareAttoMultiButton = new MultiToolStripButton("archivio/flgPresaInCarico/da_fare.png", this, "Segna da ricontrollare", false) {
				
				@Override
				public boolean toShow() {
					return isAbilSegnaDaRicontrollare();
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
						lGwtRestDataSource.executecustom("segnaDaRicontrollare", record, new DSCallback() {

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
		listaMultiselectButtons.add(segnaDaRicontrollareAttoMultiButton);	
		
		if (zipVistoContabileButton == null) {
			zipVistoContabileButton = new MultiToolStripButton("file/zip.png", this, "Zip VRC", false) {

				@Override
				public boolean toShow() {
					return isAbilZipVistoContabile();
				}

				@Override
				public void doSomething() {
					final RecordList listaUd = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUd.add(list.getSelectedRecords()[i]);
					}
					if (!listaUd.isEmpty()) {
						downloadZipDocument(listaUd);
					} else {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().alert_archivio_massivo_alldoc_downloadDocsZip(), "", MessageType.WARNING));
					}
				}

				public void downloadZipDocument(final RecordList listaUdFolder) {
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);

					final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
					lGwtRestDataSource.extraparam.put("messageError", I18NUtil.getMessages().alert_archivio_list_downloadDocsZip());
					lGwtRestDataSource.extraparam.put("limiteMaxZipError", I18NUtil.getMessages().alert_archivio_list_limiteMaxZipError());
					lGwtRestDataSource.executecustom("generaVRCzip", record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record recordVRC = response.getData()[0];
								String message = recordVRC.getAttribute("message");
								if (message != null && !"".equals(message)) {
									Layout.addMessage(new MessageBean(message, "", MessageType.ERROR));
								} else {
									String uri = recordVRC.getAttribute("storageZipRemoteUri");
									String nomeFile = recordVRC.getAttribute("zipName");
									Record infoFileRecord = recordVRC.getAttributeAsRecord("infoFile");
									Record lRecord = new Record();
									lRecord.setAttribute("displayFilename", nomeFile);
									lRecord.setAttribute("uri", uri);
									lRecord.setAttribute("sbustato", "false");
									lRecord.setAttribute("remoteUri", true);
									lRecord.setAttribute("infoFile", infoFileRecord);
									DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
								}
							}

						}
					});
				}
			};
		}
		listaMultiselectButtons.add(zipVistoContabileButton);
					
		if (eventoAMCMultiButton == null) {
			
			String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");
			eventoAMCMultiButton = new MultiToolStripButton("buttons/gear.png", this, lSistAMC != null && !"".equals(lSistAMC) ? "Evento " + lSistAMC : "Evento AMC", false) {

				@Override
				public boolean toShow() {
					String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");
					return lSistAMC != null && !"".equals(lSistAMC) && isAbilEventoAMC();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						final Record recordAtto = new Record();
						// questa operazione deve funzionare sia da lista atti completi che in quella vecchia degli atti
						// quindi passo un record con solo la property unitaDocumentariaId (che si trova in entrambi i bean)
						recordAtto.setAttribute("unitaDocumentariaId", list.getSelectedRecords()[i].getAttribute("unitaDocumentariaId"));
						recordAtto.setAttribute("flgRilevanzaContabile", "1"); // lo setto sempre a 1 altrimenti poi non mi esegue l'evento (tanto poi carica il dettaglio e riverifica se ha rilevanza contabile oppure no)															
						listaRecord.add(recordAtto);
					}
					final Record recordToSave = new Record();
					recordToSave.setAttribute("listaRecord", listaRecord);
					recordToSave.setAttribute("azione", _EVENTO_AMC);	
					final String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");
					EventoAMCPopup lEventoAMCPopup = new EventoAMCPopup("Evento " + lSistAMC, recordToSave, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							Layout.showWaitPopup("Evento " + lSistAMC + " in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "AzioneSuListaDocAttiCompletiDataSource" : "AzioneSuListaDocAttiDataSource");
							try {
								lGwtRestDataSource.addData(object, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {					
										massiveOperationCallback(response, listaRecord, "unitaDocumentariaId", "numeroProposta",
												"Evento " + lSistAMC + " effettuato con successo",
												"Tutti i record selezionati per l'evento " + lSistAMC + " sono andati in errore!",
												"Alcuni dei record selezionati per l'evento " + lSistAMC + " sono andati in errore!", null);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}			
						}
					});
					lEventoAMCPopup.show();					
				}
			};
		}
		listaMultiselectButtons.add(eventoAMCMultiButton);
		
		return listaMultiselectButtons.toArray(new MultiToolStripButton[listaMultiselectButtons.size()]);
	}
	
	private void continuaRilascioVistoMultiButton(final RecordList listaRecord) {
		final Record recordToSave = new Record();
		recordToSave.setAttribute("listaRecord", listaRecord);		
		recordToSave.setAttribute("azione", _RILASCIO_VISTO);
		MotivoOsservazioniAzioneSuListaAttiPopup lMotivoOsservazioniAzioneSuListaAttiPopup = new MotivoOsservazioniAzioneSuListaAttiPopup("Osservazioni/note a corredo del visto", recordToSave, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				if (AurigaLayout.isAttivaNuovaPropostaAtto2Completa()) {
					// Effettuo in sequenza le operazioni di numerazione, generazione e rilascio visto su un singolo atto alla volta
					RecordList listaAttiDaLavorare = object.getAttributeAsRecordList("listaRecord");
					if (listaAttiDaLavorare != null && listaAttiDaLavorare.getLength() > 0) {
						gestioneVistiInSequenza(object, listaAttiDaLavorare, 0, listaAttiDaLavorare.getLength(), null);	
					}
				} else {
					Layout.showWaitPopup("Rilascio visto in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiDataSource");
					try {
						lGwtRestDataSource.addData(object, new DSCallback() {
	
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {					
								massiveOperationCallback(response, listaRecord, "unitaDocumentariaId", "numeroProposta",
										"Rilascio visto effettuato con successo",
										"Tutti i record selezionati per il rilascio visto sono andati in errore!",
										"Alcuni dei record selezionati per il rilascio visto sono andati in errore!", null);
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			}
		});
		
		lMotivoOsservazioniAzioneSuListaAttiPopup.show();
	}
	
	private void continuaRifiutoVistoMultiButton(final RecordList listaRecord) {
		final Record recordToSave = new Record();
		recordToSave.setAttribute("listaRecord", listaRecord);		
		recordToSave.setAttribute("azione", _RIFIUTO_VISTO);
		MotivoOsservazioniAzioneSuListaAttiPopup lMotivoOsservazioniAzioneSuListaAttiPopup = new MotivoOsservazioniAzioneSuListaAttiPopup("Compilazione motivo rifiuto", true, recordToSave, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				if (AurigaLayout.isAttivaNuovaPropostaAtto2Completa()) {
					// Effettuo in sequenza le operazioni di numerazione, generazione e rifiuto visto su un singolo atto alla volta
					RecordList listaAttiDaLavorare = object.getAttributeAsRecordList("listaRecord");
					if (listaAttiDaLavorare != null && listaAttiDaLavorare.getLength() > 0) {
						gestioneVistiInSequenza(object, listaAttiDaLavorare, 0, listaAttiDaLavorare.getLength(), null);	
					}
				} else {
					Layout.showWaitPopup("Rifiuto visto in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiDataSource");
					try {
						lGwtRestDataSource.addData(object, new DSCallback() {
	
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {					
								massiveOperationCallback(response, listaRecord, "unitaDocumentariaId", "numeroProposta",
										"Rifiuto visto effettuato con successo",
										"Tutti i record selezionati per il rifiuto visto sono andati in errore!",
										"Alcuni dei record selezionati per il rifiuto visto sono andati in errore!", null);
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			}
		});
		lMotivoOsservazioniAzioneSuListaAttiPopup.show();
	}
	
	private void gestioneVistiInSequenza(final Record recordAttributiVisto, final RecordList listaAttiDaLavorare, final int posAttoDaLavorare, final int numTotaleRecordDaLavorare, Map errorMessages) {
		if (errorMessages == null) {
			errorMessages = new HashMap<String, String>();
		}
		final boolean rilascioVisto = (recordAttributiVisto.getAttribute("azione") != null && _RILASCIO_VISTO.equalsIgnoreCase(recordAttributiVisto.getAttribute("azione")));
		if (listaAttiDaLavorare != null && posAttoDaLavorare < listaAttiDaLavorare.getLength()) {
			if (rilascioVisto) {
				Layout.showWaitPopup("Rilascio visto in corso per il documento N° " + (posAttoDaLavorare + 1) + " di " +  numTotaleRecordDaLavorare + ". Attendere…");
			} else  {
				Layout.showWaitPopup("Rifiuto visto in corso per il documento N° " + (posAttoDaLavorare + 1) + " di " +  numTotaleRecordDaLavorare + ". Attendere…");
			}
			Record attoDaLavorare = listaAttiDaLavorare.get(posAttoDaLavorare);
			attoDaLavorare.setAttribute("rilascioVisto", rilascioVisto);
			attoDaLavorare.setAttribute("errorMessages", errorMessages);
			final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneSuListaDocAttiCompletiDataSource");
			lGwtRestDataSource.executecustom("effettuaNumerazionePerRilascioRifiutoVisto", attoDaLavorare, new DSCallback() {
				
				@Override
				public void execute(DSResponse dsResponse1, Object rawData1, DSRequest dsRequest1) {
					if (dsResponse1.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record attoPostNumerazione = dsResponse1.getData()[0];
						if (attoPostNumerazione.getAttribute("esitoNumerazioneOk") != null && attoPostNumerazione.getAttributeAsBoolean("esitoNumerazioneOk")) {
							lGwtRestDataSource.executecustom("generaFileUnioneEAllegatiDaModelloPerRilascioRifiutoVisto", attoPostNumerazione,  new DSCallback() {
								
								@Override
								public void execute(DSResponse dsResponse2, Object rawData2, DSRequest dsRequest2) {
									if (dsResponse2.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record attoPostGenerazione = dsResponse2.getData()[0];
										if (attoPostGenerazione.getAttribute("esitoGenerazioniDaModelloOk") != null && attoPostGenerazione.getAttributeAsBoolean("esitoGenerazioniDaModelloOk")) {
											RecordList listaRecordApposizioneVisto = new RecordList();
											listaRecordApposizioneVisto.add(attoPostGenerazione);
											recordAttributiVisto.setAttribute("listaRecord", listaRecordApposizioneVisto);
											recordAttributiVisto.setAttribute("errorMessages", attoPostGenerazione.getAttributeAsMap("errorMessages"));
											lGwtRestDataSource.addData(recordAttributiVisto, new DSCallback() {
						
												@Override
												public void execute(DSResponse dsResponse3, Object rawData3, DSRequest request3) {	
													if (dsResponse3.getStatus() == DSResponse.STATUS_SUCCESS) {
														Record attoPostVisto = dsResponse3.getData()[0];
														Map nuovoErrorMessages = attoPostVisto.getAttributeAsMap("errorMessages");
														gestioneVistiInSequenza(recordAttributiVisto, listaAttiDaLavorare, posAttoDaLavorare + 1, numTotaleRecordDaLavorare, nuovoErrorMessages);
													} else {
														// Errore nella chiamata al datasource AzioneSuListaDocAttiCompletiDataSource
														Layout.hideWaitPopup();
														if (rilascioVisto) {
															Layout.addMessage(new MessageBean("Errore nell'avvio del processo di rilascio visto", "", MessageType.ERROR));
														} else {
															Layout.addMessage(new MessageBean("Errore nell'avvio del processo di rifiuto visto", "", MessageType.ERROR));
														}
													}
												}
											});
										} else {
											// La generazione dei file da modello non è andata a buon fine, proseguo con il recordo successivo
											Map nuovoErrorMessages = attoPostGenerazione.getAttributeAsMap("errorMessages");
											gestioneVistiInSequenza(recordAttributiVisto, listaAttiDaLavorare, posAttoDaLavorare + 1, numTotaleRecordDaLavorare, nuovoErrorMessages);
										}
									} else {
										// Errore nella chiamata al datasource AzioneSuListaDocAttiCompletiDataSource
										Layout.hideWaitPopup();
										Layout.addMessage(new MessageBean("Errore nell'avvio del processo di generazione da modello", "", MessageType.ERROR));
									}
								}
							});
						} else {
							// La numerazione non è andata a buon fine, proseguo con il recordo successivo
							Map nuovoErrorMessages = attoPostNumerazione.getAttributeAsMap("errorMessages");
							gestioneVistiInSequenza(recordAttributiVisto, listaAttiDaLavorare, posAttoDaLavorare + 1, numTotaleRecordDaLavorare, nuovoErrorMessages);
						}
					} else {
						// Errore nella chiamata al datasource AzioneSuListaDocAttiCompletiDataSource
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
			if (rilascioVisto) {
				massiveOperationCallback(response, listaAttiDaLavorare, "unitaDocumentariaId", "numeroProposta",
						"Rilascio visto effettuato con successo",
						"Tutti i record selezionati per il rilascio visto sono andati in errore!",
						"Alcuni dei record selezionati per il rilascio visto sono andati in errore!", null);
			} else {
				massiveOperationCallback(response, listaAttiDaLavorare, "unitaDocumentariaId", "numeroProposta",
						"Rifiuto visto effettuato con successo",
						"Tutti i record selezionati per il rifiuto visto sono andati in errore!",
						"Alcuni dei record selezionati per il rifiuto visto sono andati in errore!", null);
			}
		}
	}
	
	private void continuaMandaAlLibroFirmaAttoMultiButton(final RecordList listaRecord) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaRecord);
		Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioniLibroFirmaDataSource");
		try {
			lGwtRestDataSource.executecustom("mandaALibroFirma", record, new DSCallback() {

				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Layout.hideWaitPopup();
						Record recordAttributiLibroFirma = response.getData()[0];
						if (recordAttributiLibroFirma != null && recordAttributiLibroFirma.getAttributeAsRecordList("listaRecord") != null) {
							RecordList listaAttiDaLavorare = recordAttributiLibroFirma.getAttributeAsRecordList("listaRecord");
							gestioneInvioLibroFirmaInSequenza(recordAttributiLibroFirma, listaAttiDaLavorare, 0, listaAttiDaLavorare.getLength(), null);
						}
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
			ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Atto", listaErrori, recorSelezionati, 600, 300);
			errorePopup.show();
		} else { 
			if(operazione != null && "manda_libro_firma".equalsIgnoreCase(operazione)) {
				Layout.addMessage(new MessageBean("Operazione effettuata con successo: accedere alla funzione \"libro firma\" per firmare i documenti selezionati", "", MessageType.INFO));
			} else {
				Layout.addMessage(new MessageBean("Operazione effettuata con successo", "", MessageType.INFO));
			}
		}
	}
	
}