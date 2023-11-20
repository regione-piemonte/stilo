/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.PostaElettronicaWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

public class InvioMailLayout extends CustomDetail {

	protected InvioMailForm form;
	protected PostaElettronicaWindow window;
	protected String tipoRel;
	protected RecordList listaRecord;

	public InvioMailLayout(PostaElettronicaWindow pWindow, String pTipoRel, ValuesManager vm, RecordList pListaRecord) {

		super("invioMailLayout", vm);

		this.window = pWindow;
		
		tipoRel = pTipoRel;
		listaRecord = pListaRecord;

		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);
		
		form = new InvioMailForm(tipoRel);
		form.setMargin(10);
		form.setValuesManager(vm);

		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		layout.addMember(form);

		addMember(layout);

	}

	public void unlock(final RecordList listaMail, final boolean isMassiva, final ServiceCallback<Record> callback) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("UnlockEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = null;

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					Map errorMessages = data.getAttributeAsMap("errorMessages");

					String key = null;

					if (listaMail.get(0) != null)
						key = listaMail.get(0).getAttribute("idEmail");

					if (errorMessages != null && errorMessages.size() > 0) {
						errorMsg = "Fallito rilascio della mail al termine dell'operazione: " + (key != null ? errorMessages.get(key) : "");
					}
				}
				Layout.hideWaitPopup();
				if (errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.WARNING));
				} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean("Rilascio effettuato con successo", "", MessageType.INFO));

				}

				Record record = new Record();
				callback.execute(record);
			}
		});
	}

	private void bloccaemail(final Record pRecordMail, final ServiceCallback<Record> callback) {
		ctrlLockEmail(pRecordMail, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {

				Record esitoCheck = object.getAttributeAsRecord("esito");

				boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");

				if (!isLock) {
					final RecordList listaEmail = new RecordList();
					listaEmail.add(pRecordMail);

					lock(listaEmail, new ServiceCallback<Record>() {

						@Override
						public void execute(Record pRecord) {
							boolean isLock = pRecord.getAttributeAsBoolean("esitoLock");
							final RecordList listaEmail = new RecordList();
							listaEmail.add(pRecordMail);
							callback.execute(pRecordMail);
						}
					});
				}
			}
		});
	}

	private void verificaLockEChiudiMailDiProvenienza(final Record pRecordMail, final DSCallback callback, final DSResponse response, final boolean chiudiMailProvenienza, final String gestisciAzioniDaFare) {
		final RecordList listaEmail = new RecordList();
		listaEmail.add(pRecordMail);
		boolean flgRilascia = pRecordMail.getAttributeAsBoolean("flgRilascia");
		if (flgRilascia) {
			unlock(listaEmail, false, new ServiceCallback<Record>() {

				@Override
				public void execute(Record pRecord) {
					chiudiMailDiProvenienza(pRecordMail, callback, response, null,  new DSRequest(), chiudiMailProvenienza, gestisciAzioniDaFare);
				}
			});
		} else {
			chiudiMailDiProvenienza(pRecordMail, callback, response, null,  new DSRequest(), chiudiMailProvenienza, gestisciAzioniDaFare);
		}
	}

	private void lock(final RecordList listaMail, final ServiceCallback<Record> callback) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LockEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Record rec = new Record();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];

					Map mapErrorMessages = data.getAttributeAsMap("errorMessages");
					if (mapErrorMessages != null && mapErrorMessages.size() > 0) {
						rec.setAttribute("esitoLock", false);
					} else
						rec.setAttribute("esitoLock", true);
				}
				callback.execute(rec);
			}
		});
	}

	private void ctrlLockEmail(Record record, final ServiceCallback<Record> callback) {
		final GWTRestDataSource lockEmailDataSource = new GWTRestDataSource("LockEmailDataSource");
		lockEmailDataSource.executecustom("checkLockEmail", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Record rec = new Record();
				rec.setAttribute("esito", response.getData()[0]);
				callback.execute(rec);
			}
		}); 
	}

	// Passo per questo anche se faccio un invio come copia
	public void inviaMail(final DSCallback callback, final boolean mostraPopUpChiusuraMailProvenienza) {
	
		//Procedo all'invio 
		sendMail(callback, mostraPopUpChiusuraMailProvenienza);		
	}
	
	private void sendMail(final DSCallback callback, final boolean mostraPopUpChiusuraMailProvenienza) {
		if (vm != null && vm.validate()) {
			final Record lRecordMail = new Record(vm.getValues());
			if (lRecordMail != null) {
				if (isMittenteValido(lRecordMail)) {
					if(verificaInvioSeparato(lRecordMail)){
						if (mostraPopUpChiusuraMailProvenienza){
							// Apro la popup per chiedere se chiudere o no le mail di provenienza
							// Verifico se ho azioni da fare sulle mail da chiudere
							Map<String, String> mappaParametriPopupChiusuraMailProvenienza = generaParametriPopupChiusuraMailProvenienza();
							
							InvioMailPopup invioMailPopup = new InvioMailPopup(mappaParametriPopupChiusuraMailProvenienza) {
								@Override
								public void onClickChiudiMailECompletaAzioneButton(Record formRecord) {
									//Il terzo parametro è true perchè in questo caso dobbiamo chiudere la mail
									continuaSendMail(callback, lRecordMail, true, "COMPL");
									markForDestroy();
								}
	
								@Override
								public void onClickChiudiMailEAnnullaAzioneButton(Record formRecord) {
									//Il terzo parametro è true perchè in questo caso dobbiamo chiudere la mail
									continuaSendMail(callback, lRecordMail, true, "ANN");
									markForDestroy();
								}
	
								@Override
								public void onClickChiudiMailButton(Record formRecord) {
									//Il terzo parametro è true perchè in questo caso dobbiamo chiudere la mail
									continuaSendMail(callback, lRecordMail, true, null);
									markForDestroy();
								}
	
								@Override
								public void onClickNonChiudereMailButton(Record formRecord) {
									//Il terzo parametro è false perchè in questo caso NON dobbiamo chiudere la mail
									continuaSendMail(callback, lRecordMail, false, null);
									markForDestroy();
								}
	
							};
							invioMailPopup.show();
						}else{
							continuaSendMail(callback, lRecordMail, false, null);
						}
					} else {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().inviomailform_flgInvioSeparato_errormessages() + "", "", MessageType.WARNING));
					}
				}else{
					Layout.addMessage(new MessageBean("Attenzione, mittente non valorizzato " + "", "", MessageType.WARNING));
				}
			}
		}
	}

	private void continuaSendMail(final DSCallback callback, final Record lRecordMail, final Boolean chiudiMailProvenienza, final String gestisciAzioniDaFare) {
		Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_invio_mail());
		try {
			boolean isOperazioneMassiva = false;
			if (listaRecord != null) {
				if (listaRecord.getLength() > 1) {
					isOperazioneMassiva = true;
					lRecordMail.setAttribute("listaRecord", listaRecord);
				} else if (listaRecord.getLength() == 1) {
					lRecordMail.setAttribute("idEmail", listaRecord.get(0).getAttribute("idEmail"));
				}
			}

			if (!isOperazioneMassiva) {
				// Dovrei controllare il lock della mail precessoria a quella
				// che sto inviando.
				// idEmail contiene l'id della mail precessoria, ma non sempre questa è presente
				// Se non è presente salto il controllo del bloccomail
				if ((lRecordMail.getAttribute("idEmail") != null) && (lRecordMail.getAttribute("idEmail").length() > 0)) {
					// Ho una mail precessoria, la controllo con bloccamail
					bloccaemail(lRecordMail, new ServiceCallback<Record>() {

						@Override
						public void execute(Record lRecordMail) {
							continuaInvioMailNonMassivo(lRecordMail, callback, chiudiMailProvenienza, gestisciAzioniDaFare);
						}
					});
				} else {
					// Non ho mail precessoria, salto il controllo di blocca mail
					continuaInvioMailNonMassivo(lRecordMail, callback, chiudiMailProvenienza, gestisciAzioniDaFare);
				}
			} else {
				// Ho bisogno di una costante final per la successiva chiamata
				final boolean isOperazioneMassivaFinal = isOperazioneMassiva;
				RecordList listaEmail = lRecordMail.getAttributeAsRecordList("listaRecord");
				lockmassivo(listaEmail, new ServiceCallback<Record>() {

					@Override
					public void execute(Record recordlock) {
						final RecordList recLock = recordlock.getAttributeAsRecordList("listaLock");
						boolean esitoInoltro = recordlock.getAttributeAsBoolean("esitoInoltro");
						if (esitoInoltro) {
							final GWTRestService<Record, Record> lGwtRestServiceInvioNotifica = new GWTRestService<Record, Record>(
									"AurigaInvioMailDatasource");
							lGwtRestServiceInvioNotifica.extraparam.put("tipoRel", tipoRel);
							lGwtRestServiceInvioNotifica.extraparam.put("tipoRelCopia", lRecordMail.getAttribute("tipoRelCopia"));
							lGwtRestServiceInvioNotifica.executecustom(isOperazioneMassivaFinal ? "invioMailInoltroMassivo" : "invioMail",
									lRecordMail, new DSCallback() {

										@Override
										public void execute(final DSResponse response, final Object rawData, final DSRequest request) {
											Layout.hideWaitPopup();
											if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												manageResponseInvio(response);
												unlock(recLock, true, new ServiceCallback<Record>() {

													@Override
													public void execute(Record object) {															
														chiudiMailDiProvenienza(lRecordMail, callback, response, rawData, request, chiudiMailProvenienza, gestisciAzioniDaFare);
													}
												});
											} else {
												Layout.addMessage(new MessageBean("Si è verificato un errore durante l'invio dell'e-mail", "",
														MessageType.ERROR));
											}
										}
									});
						}
					}
				});
			}
		} catch (Exception e) {
			Layout.hideWaitPopup();
			Layout.addMessage(new MessageBean("Si è verificato un errore durante l'invio dell'e-mail: " + e.getMessage(), "", MessageType.ERROR));
		}
	}

	private void continuaInvioMailNonMassivo(final Record lRecordMail, final DSCallback callback, final boolean chiudiMailProvenienza, final String gestisciAzioniDaFare) {
		boolean isInoltroMassivo = false;
		if (listaRecord != null) {
			if (listaRecord.getLength() > 1) {
				isInoltroMassivo = true;
				lRecordMail.setAttribute("listaRecord", listaRecord);
			} else if (listaRecord.getLength() == 1) {
				lRecordMail.setAttribute("idEmail", listaRecord.get(0).getAttribute("idEmail"));
			}
		}
		final boolean flgMassivo = isInoltroMassivo;
		final GWTRestService<Record, Record> lGwtRestServiceInvioNotifica = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
		if (!flgMassivo && (tipoRel != null && tipoRel.equals("InvioNuovoMessaggioCopia"))) {
			lGwtRestServiceInvioNotifica.extraparam.put("idEmailUD", lRecordMail.getAttribute("idEmailUD"));			
		}
		lGwtRestServiceInvioNotifica.extraparam.put("tipoRel", tipoRel);
		lGwtRestServiceInvioNotifica.extraparam.put("tipoRelCopia", lRecordMail.getAttribute("tipoRelCopia"));
		lGwtRestServiceInvioNotifica.executecustom(isInoltroMassivo ? "invioMailInoltroMassivo" : "invioMail", lRecordMail, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					manageResponseInvio(response);
					if (!flgMassivo) {
						Record lRecord = new Record();
						lRecord.setAttribute("idEmail", form.getRecord().getAttribute("idEmail"));
						lRecord.setAttribute("mailPredecessoreStatoLav", form.getRecord().getAttribute("mailPredecessoreStatoLav"));
						
						boolean flg = form.getRecord().getAttributeAsBoolean("flgRilascia");
						lRecord.setAttribute("flgRilascia", flg);
						lRecord.setAttribute("listaRecord", listaRecord);

						verificaLockEChiudiMailDiProvenienza(lRecord, callback, response, chiudiMailProvenienza, gestisciAzioniDaFare);
					} else {
						chiudiMailDiProvenienza(lRecordMail, callback, response, rawData, request, chiudiMailProvenienza, gestisciAzioniDaFare);
					}
				} else {
					Layout.addMessage(new MessageBean("Si è verificato un errore durante l'invio dell'e-mail", "", MessageType.ERROR));
				}
			}
		});
	}

	private void lockmassivo(final RecordList listaMail, final ServiceCallback<Record> callback) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LockEmailDataSource");
		lGwtRestDataSource.addParam("isMassivo", "1");

		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				RecordList listalock = new RecordList();
				boolean esitoInoltro = true;
				String errorMsg = "";
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					Map errorMessages = data.getAttributeAsMap("errorMessages");

					for (int i = 0; i < listaMail.getLength(); i++) {
						Record reco = listaMail.get(i);
						if (errorMessages.get(reco.getAttribute("idEmail")) != null) {
							reco.setAttribute("idEmail", reco.getAttribute("idEmail"));
							if (errorMessages.get(reco.getAttribute("idEmail")) != null) {
								esitoInoltro = false;
								errorMsg += reco.getAttribute("id") + " " + (String) errorMessages.get(reco.getAttribute("idEmail")) + "<br/>";
							} else {
								reco.setAttribute("esitoLock", false);
							}
						} else {
							reco.setAttribute("esitoLock", true);
							reco.setAttribute("idEmail", reco.getAttribute("idEmail"));
							listalock.add(reco);
						}
					}
				}
				if (errorMsg.length() > 0)
					Layout.addMessage(new MessageBean("Impossibile fare l'inoltro: <br/>" + errorMsg, "", MessageType.ERROR));

				Record record = new Record();
				record.setAttribute("listaLock", listalock);
				record.setAttribute("esitoInoltro", esitoInoltro);
				callback.execute(record);
			}
		});
	}

	protected void manageResponseInvio(DSResponse response) {
		Layout.addMessage(new MessageBean(I18NUtil.getMessages().invionotificainterop_esitoInvio_OK_value(), "", MessageType.INFO));
		window.markForDestroy();
	}

	public InvioMailForm getForm() {
		return form;
	}

	public void salvaBozza(final DSCallback callback, final String operazione) {
		// In operazione ho il tipo di bozza, ovvero se è una bozza di una mail di risposta, di inoltro,
		// di un nuovo messaggio o di un nuovo invio come copia
		// Se sono nella bozza di un nuovo invio come copia, dovrò anche tener traccia del tipo di messaggio
		// da cui è fatto il nuovo come copia. Questo perchè al momento del salvataggio devo tener traccia del
		// tipo del messaggio originale da cui ho fatto la copia. Questa informazione è presente nel vm nell'attributo tipoRelCopia
		if (vm != null) {
			vm.clearErrors(true);
			if ((vm.getItem("listaItemInLavorazione") == null)
					|| (vm.getItem("listaItemInLavorazione") != null && vm.getItem("listaItemInLavorazione").validate())) {
				final Record record = new Record(vm.getValues());
				if (isMittenteValido(record)) {
					Record massiveRecord = new Record(form.getValues());

					if (operazione != null && "I".equals(operazione)) {
						// Se sono nella bozza di un inoltro massivo, mi salvo l'elenco delle mail inoltrate
						RecordList recordList = new RecordList();
						if (massiveRecord != null && massiveRecord.getAttributeAsRecordList("listaDettagli") != null
								&& massiveRecord.getAttributeAsRecordList("listaDettagli").getLength() > 0) {
							for (int i = 0; i < massiveRecord.getAttributeAsRecordList("listaDettagli").getLength(); i++) {
								Record item = massiveRecord.getAttributeAsRecordList("listaDettagli").get(i);

								Record recordToSave = new Record();
								recordToSave.setAttribute("idEmail", item.getAttributeAsString("idEmail"));

								recordList.add(recordToSave);
							}
						}
						record.setAttribute("listaIdEmailInoltrate", recordList);
					}
					// Se sono nel salvataggio di una bozza di un nuovo come copia, devo salvare il tipo di relazione del
					// messaggio da cui è stata fatta la copia (presente nel vm in tipoRelCopia)
					String tipoRelCopia = record.getAttribute("tipoRelCopia");
					
					salvaMail(callback, operazione, record);
					
				} else {
					Layout.addMessage(new MessageBean("Attenzione, mittente non valorizzato " + "", "", MessageType.WARNING));
				}
			}
		}
	}
	
	private void salvaMail(final DSCallback callback, final String operazione, final Record record) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaSalvaIterBozzaMailDataSource");
		lGwtRestService.extraparam.put("tipoRel", tipoRel);
		lGwtRestService.extraparam.put("operazione", operazione);
		// lGwtRestService.extraparam.put("tipoRelCopia", tipoRelCopia);
		lGwtRestService.performCustomOperation("saveBozza", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean("Bozza salvata con successo", "", MessageType.INFO));
					if (callback != null) {
						callback.execute(response, rawData, request);
					}
				}
			}
		}, new DSRequest());
	}

	public Boolean isMittenteValido(Record record) {
		return record != null && record.getAttributeAsString("mittente") != null && !"".equalsIgnoreCase(record.getAttributeAsString("mittente"))
				&& !" ".equals(record.getAttributeAsString("mittente"));
	}
	
	/**
	 * Viene verificato l'invio separato della mail, se il chek flgInvioSeparato è spuntato e sono presenti
	 * destinatari in CC, l'invio della stessa non è consentito.
	 */
	public Boolean verificaInvioSeparato(Record record) {
		
		boolean verify = true;
		if(record != null){
			if(record.getAttributeAsBoolean("flgInvioSeparato") != null && record.getAttributeAsBoolean("flgInvioSeparato")){
				if(record.getAttributeAsString("destinatariCC") != null && !"".equalsIgnoreCase(record.getAttributeAsString("destinatariCC"))){
					verify = false;
				}
			}
		}
		return verify;
	}
	
	// Se richiesto chiude le mail da cui hanno origine l'inoltro o la risposta
	protected void chiudiMailDiProvenienza(Record recordMailCorrente, final DSCallback callback, final DSResponse response, final Object rawData, final DSRequest request, boolean chiudiMailProvenienza, String gestisciAzioniDaFare){
		boolean isChiusuraMultipla = false;
		RecordList listaMailDaChiudere = new RecordList();
		if (chiudiMailProvenienza){
			// Devo chiudere le mail di provenienza (ovvero quella a cui ho riposto o quelle che ho inoltrato)
			if	(listaRecord != null && listaRecord.getLength() > 0){
				// se sono in un inoltro massivo la lista delle mail da chidere la trovo in listaRecord
				if (listaRecord.getLength() > 1){
					isChiusuraMultipla = true;
				}
				// Devo chiudere solamente le mail aperte
				for (int i = 0; i < listaRecord.getLength(); i++){
					Record record = listaRecord.get(i);
					if ("aperta".equalsIgnoreCase(record.getAttribute("statoLavorazione"))){
						listaMailDaChiudere.add(listaRecord.get(i));
					}
				}
			}else if (recordMailCorrente.getAttribute("idEmail") != null && !"".equalsIgnoreCase(recordMailCorrente.getAttribute("idEmail"))){
				// La mail da chiudere la trovo dentro idEmail
				// Devo verificare se è una mail aperta
				if (("1".equalsIgnoreCase(recordMailCorrente.getAttribute("flgMailStatoLavAperta"))) 
						|| ("aperta".equalsIgnoreCase(recordMailCorrente.getAttribute("mailPredecessoreStatoLav")))){
				
					Record emailDaChiudere = new Record();
					emailDaChiudere.setAttribute("idEmail", recordMailCorrente.getAttribute("idEmail"));	
					listaMailDaChiudere.add(emailDaChiudere);
				}
			}
			
			final boolean isChiusuraMultiplaFinal = isChiusuraMultipla;
			final RecordList listaMailDaChiudereFinal = listaMailDaChiudere;
			
			if (listaMailDaChiudereFinal.getLength() > 0){
			
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchiviazioneEmailDataSource");
				Record record = new Record();
				record.setAttribute("listaRecord", listaMailDaChiudere);
				if (gestisciAzioniDaFare != null){
					record.setAttribute("operazioneRichiesta", gestisciAzioniDaFare);
				}
				lGwtRestDataSource.addData(record, new DSCallback() {
		
					@Override
					public void execute(DSResponse lResponse, Object lRawData, DSRequest lRequest) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {	
							// Verifico se ho avuto errori nella chiusura delle mail di provenienza
							if (lResponse.getData()[0] != null && lResponse.getData()[0].getAttributeAsMap("errorMessages") != null && !lResponse.getData()[0].getAttributeAsMap("errorMessages").isEmpty()){
								String errorMessage = "L'azione \"" + ((AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") != null
										&& !"".equalsIgnoreCase(AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL")))
										? AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") : "Chiudi")
										+ "\" non è andata a buon fine";
								if (isChiusuraMultiplaFinal){
									// Verifico se sono andati in errore tutti i record o solo alcuni
									if (listaMailDaChiudereFinal.getLength() == response.getData()[0].getAttributeAsMap("errorMessages").size()){
										// Sono andati in errore tutti i record
										errorMessage = "L'azione \"" + ((AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") != null
												&& !"".equalsIgnoreCase(AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL")))
														? AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") : "Chiudi")
												+ "\" non è andata a buon fine su tutte le e-mail inoltrate";
									}else{
										// Sono andati in errore solamente alcuni record
										errorMessage = "L'azione \"" + ((AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") != null
												&& !"".equalsIgnoreCase(AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL")))
														? AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL") : "Chiudi")
												+ "\" non è andata a buon fine su alcune delle e-mail inoltrate";
									}
								}
								Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
								
							}
								
						}
						if (callback != null) {
							callback.execute(response, rawData, request);
						}
					}
				});
			}else{
				if (callback != null) {
					callback.execute(response, rawData, request);
				}
			}
		} else {
			if (callback != null) {
				callback.execute(response, rawData, request);
			}
		}
	}
	
	protected Map<String, String> generaParametriPopupChiusuraMailProvenienza(){
		boolean isOperazioneMassiva = false;
		String nomeOperazione = "";
		
		if ((tipoRel != null) && (tipoRel.toLowerCase().indexOf("inoltr") != -1)){
			// Sono in un inoltro (non bozza)
			// Verifico se sono in un inoltro multiplo
			if(listaRecord != null && listaRecord.getLength() > 0){
				// Sono in una inoltro (non bozza) multiplo
				isOperazioneMassiva = true;
				nomeOperazione = "inoltro";
			}else{
				// Sono in una inoltro (non bozza) singolo
				isOperazioneMassiva = false;
				nomeOperazione = "inoltro";
			}
		}else{
			// Sono in una risposta (non bozza)
			isOperazioneMassiva = false;
			nomeOperazione = "risposta";
		}
		Map<String, String> mappaParametri = new HashMap<String, String>();
		mappaParametri.put("isOperazioneMassiva", isOperazioneMassiva + "");
		mappaParametri.put("nomeOperazione", nomeOperazione);

		mappaParametri = settaAzioniDaFareParam(mappaParametri);
		
		return mappaParametri;
	}
	
	protected Map<String, String> settaAzioniDaFareParam(Map<String, String> mappaParametri){
		Record recordPrincipale = window.getRecordPrincipale();
		boolean isAzioniDaFarePresenti = false;
		String nomeAzioneDaFare = null;
		// Verifico se sono in una operazione massiva
		if ((recordPrincipale != null) && (recordPrincipale.getAttributeAsRecordArray("listaRecord") != null) && (recordPrincipale.getAttributeAsRecordArray("listaRecord").length > 0)){
			// Sono in una operazione massiva
			// Verifico se ho un'azione da fare sui record dell'operazione massiva, e se è uguale per tutti
			for (int i = 0; i < recordPrincipale.getAttributeAsRecordArray("listaRecord").length; i++){
				if (checkAzioneDaFareEAperta(recordPrincipale.getAttributeAsRecordArray("listaRecord")[i])){
					isAzioniDaFarePresenti = true;
					// Devo controllare se le eventuali azioni da fare sono tutte uguali
					String nomeAzioneDaFareTmp = estraiNomeAzioneDaFare(recordPrincipale.getAttributeAsRecordArray("listaRecord")[i]);
					if (nomeAzioneDaFare == null){
						nomeAzioneDaFare = nomeAzioneDaFareTmp;
					}else if (!nomeAzioneDaFare.equalsIgnoreCase(nomeAzioneDaFareTmp)){
						nomeAzioneDaFare = "";
					}
				}
			}
		}else{
			// Sono in una riposta o inoltro singolo
			if (checkAzioneDaFareEAperta(recordPrincipale)){
				isAzioniDaFarePresenti = true;
				nomeAzioneDaFare = estraiNomeAzioneDaFare(recordPrincipale);
			}
		}
		mappaParametri.put("isAzioniDaFarePresenti", isAzioniDaFarePresenti + "");
		mappaParametri.put("nomeAzioneDaFare", nomeAzioneDaFare);
		return mappaParametri;
	}
	
	protected static boolean checkAzioneDaFareEAperta(Record recordDaControllare){
		if ((recordDaControllare != null) && (recordDaControllare.getAttributeAsRecord("azioneDaFareBean") != null)
				&& (recordDaControllare.getAttributeAsRecord("azioneDaFareBean").getAttribute("codAzioneDaFare") != null)
				&& (!"".equalsIgnoreCase(recordDaControllare.getAttributeAsRecord("azioneDaFareBean").getAttribute("codAzioneDaFare")))
				&& ("aperta".equalsIgnoreCase(recordDaControllare.getAttribute("statoLavorazione")))) {
			return true;
		}
		return false; 
	}
	
	protected static String estraiNomeAzioneDaFare(Record recordDaControllare){
		if ((recordDaControllare != null) && (recordDaControllare.getAttributeAsRecord("azioneDaFareBean") != null) && (recordDaControllare.getAttributeAsRecord("azioneDaFareBean").getAttribute("codAzioneDaFare") != null) && (!"".equalsIgnoreCase(recordDaControllare.getAttributeAsRecord("azioneDaFareBean").getAttribute("codAzioneDaFare")))){
			return recordDaControllare.getAttributeAsRecord("azioneDaFareBean").getAttribute("azioneDaFare");
		}
		return ""; 
	}

}
