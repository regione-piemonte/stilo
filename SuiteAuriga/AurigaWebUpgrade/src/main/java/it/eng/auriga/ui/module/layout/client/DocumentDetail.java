/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaNonEseguitaCallback;
import it.eng.auriga.ui.module.layout.client.archivio.CondivisionePopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.ApponiTimbroWindow;
import it.eng.auriga.ui.module.layout.client.scrivania.ScrivaniaLayout;
import it.eng.auriga.ui.module.layout.shared.util.AzioniRapide;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

public abstract class DocumentDetail extends CustomDetail{
	
	public DocumentDetail(String nomeEntita) {
		super(nomeEntita);
	}

	public DocumentDetail(String pNomeEntita, ValuesManager pValuesManager) {
		super(pNomeEntita, pValuesManager);
	}
	
	public String getFlgTipoProv() {
		return null;
	}
	
	/**
	 * Metodo che ricarica i dati di dettaglio del documento (se si è in
	 * inserimento i campi vengono ripuliti e riportati alla situazione
	 * iniziale)
	 * 
	 */
	public void reload(final DSCallback callback) {

		if (mode != null && mode.equals("new")) {
			vm.clearErrors(true);
			editNewRecord();
		} else {
			Record lRecordToLoad = new Record(vm.getValues());
			final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
			if(layout != null && layout instanceof ScrivaniaLayout) {
				lGwtRestDataSource.addParam("idNode", ((ScrivaniaLayout)layout).getIdNode());
			}
			lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						editRecord(record);
						if(callback != null){
							callback.execute(response, null, new DSRequest());
						}
						
					} else {
						Layout.addMessage(
								new MessageBean("Si è verificato un errore durante il caricamento del dettaglio", "",
										MessageType.ERROR));
					}
				}
			});
		}
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Invio per conoscenza"
	 */
	public void clickCondivisione(Record destinatariPreferiti) {
		
		final Record detailRecord = new Record(vm.getValues());
		
		RecordList listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.INVIO_CC_DOC.getValue()));
		RecordList listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.INVIO_CC_DOC.getValue()));

		final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare per la condivisione
		
		if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
			listaPreferiti.addList(listaUOPreferiti.toArray());
		}
		
		if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
			listaPreferiti.addList(listaUtentiPreferiti.toArray());
		}
		
		clickCondivisioneStandard(detailRecord, listaPreferiti);
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Freccia invio per conoscenza"
	 */
	public void clickFrecciaCondivisione(Record destinatariPreferiti) {
		
		final Record detailRecord = new Record(vm.getValues());
		
		final Menu creaCondivisione = new Menu(); 
		
		RecordList listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.INVIO_CC_DOC.getValue()));
		RecordList listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.INVIO_CC_DOC.getValue()));

		boolean noMenuRapido = true; // identifica la presenza o meno di valori da visualizzare nel menu rapido di condivisione
		final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare per la condivisione
		
		if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
			listaPreferiti.addList(listaUOPreferiti.toArray());
			noMenuRapido = false;
		}
		
		if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
			listaPreferiti.addList(listaUtentiPreferiti.toArray());
			noMenuRapido = false;
		}
		
		// Invio per conoscenza Standard
		MenuItem condivisioneMenuStandardItem = new MenuItem("Standard");
		condivisioneMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {				
				clickCondivisioneStandard(detailRecord, listaPreferiti);
			}
		});
		creaCondivisione.addItem(condivisioneMenuStandardItem);
		
		// Invio per conoscenza Rapida
		MenuItem condivisioneMenuRapidoItem = new MenuItem("Rapida");
		
		Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
		
		if(success != null && success == true) {
			
			Menu scelteRapide = new Menu();					
			
			if(noMenuRapido){
				condivisioneMenuRapidoItem.setEnabled(false);
			} else {
				buildMenuRapidoCondivisione(detailRecord, "U", listaPreferiti, scelteRapide);
				condivisioneMenuRapidoItem.setSubmenu(scelteRapide);
			}
			
		} else {
			condivisioneMenuRapidoItem.setEnabled(false);
		}
		creaCondivisione.addItem(condivisioneMenuRapidoItem);
		creaCondivisione.showContextMenu();
	}	
	
	protected void buildMenuRapidoCondivisione(final Record detailRecord, final String flgUdFolder, RecordList listaPreferiti, Menu scelteRapide) {
		
		for(int i=0; i < listaPreferiti.getLength();i++){
				
			Record currentRecord = listaPreferiti.get(i);
			final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
			final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
			final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
			
			MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
			currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
				@Override
				public void onClick(MenuItemClickEvent event) {
					
					final RecordList listaUdFolder = new RecordList();
					if(detailRecord.getAttributeAsString("idUdFolder") == null ||
							"".equals(detailRecord.getAttributeAsString("idUdFolder"))){
						detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
						detailRecord.setAttribute("flgUdFolder", flgUdFolder);
					}
					listaUdFolder.add(detailRecord);
					RecordList listaDestInvioCC = new RecordList();
					Record recordAssegnazioni = new Record();
					recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
					recordAssegnazioni.setAttribute("typeNodo", tipoDestinatarioPreferito);
					listaDestInvioCC.add(recordAssegnazioni);
					
					Record record = new Record();
					record.setAttribute("flgUdFolder", flgUdFolder);
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("listaDestInvioCC", listaDestInvioCC);
					
					Layout.showWaitPopup("Invio per conoscenza in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
					try {
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								operationCallback(response, detailRecord, "idUdFolder", "Invio per conoscenza effettuato con successo",
										"Si è verificato un errore durante l'invio per conoscenza!", new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {	
										reload(null);
									}
								});
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			});
			scelteRapide.addItem(currentRapidoItem);			
		}
	}
	
	public void clickCondivisioneStandard(final Record detailRecord, final RecordList listaPreferiti) {		
		String title = I18NUtil.getMessages().condivisioneWindow_title() + " del documento " + detailRecord.getAttribute("segnatura");
		
		final CondivisionePopup condivisionePopup = new CondivisionePopup("U", detailRecord, title) {
			
			@Override
			public String getFlgTipoProvDoc() {
				return getFlgTipoProv();																		
			}
			
			@Override
			public RecordList getListaPreferiti() {
				return listaPreferiti;
			}
	
			@Override
			public void onClickOkButton(Record record, final DSCallback callback) {
				
				clickOkCondivisioneButton(detailRecord, record, callback);
			}
		};
		condivisionePopup.show();
	}	
	
	private void clickOkCondivisioneButton(final Record detailRecord, Record record, final DSCallback callback) {
		
		final RecordList listaUdFolder = new RecordList();
		if(detailRecord.getAttributeAsString("idUdFolder") == null ||
				"".equals(detailRecord.getAttributeAsString("idUdFolder"))){
			detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
			detailRecord.setAttribute("flgUdFolder", "U");
		}
		listaUdFolder.add(detailRecord);
	
		record.setAttribute("flgUdFolder", "U");
		record.setAttribute("listaRecord", listaUdFolder);
		
		Layout.showWaitPopup("Invio per conoscenza in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
		try {
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					operationCallback(response, detailRecord, "idUdFolder", "Invio per conoscenza effettuato con successo",
							"Si è verificato un errore durante l'invio per conoscenza!", new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {														
							if(callback != null){
								callback.execute(response, rawData, request);
							}
							reload(null);
						}
					});
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	public void operationCallback(DSResponse response, Record record, String pkField, String successMessage, String errorMessage, DSCallback callback) {
		operationCallback(response, record, pkField, successMessage, errorMessage, callback, null);
	}
	
	public void operationCallback(DSResponse response, Record record, String pkField, String successMessage, String errorMessage, DSCallback callback, DSCallback errorCallback) {
		
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			if (errorMessages != null && !errorMessages.isEmpty()) {
				if (errorMessages.get(record.getAttribute(pkField)) != null) {
					errorMsg = (String) errorMessages.get(record.getAttribute(pkField));
				} else {
					errorMsg = errorMessage != null ? errorMessage : "Si è verificato un errore durante l'operazione!";
				}
			}
			Layout.hideWaitPopup();
			if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
				if (errorCallback != null) {
					errorCallback.execute(new DSResponse(), null, new DSRequest());
				}
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		}
	}
	
	public static void avviaProtocollaTimbraEFirma(final DocumentDetail detail, final Record recordDaProtocollare, final Map<String, String> mappaErrori, final ServiceCallback<DSResponse> callbackManageFirmaEProtocolla) {
		
		// Raccolgo le informzioni per la timbratura
		boolean skipSceltaTimbratura = AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniTimbroSegnatura");
		if (skipSceltaTimbratura) {
			protocollaBeforeTimbraEFirma(detail, recordDaProtocollare, null, mappaErrori, callbackManageFirmaEProtocolla);
		} else {
			String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro");
			String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro");
			String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina");

			Record record = new Record();
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("tipoPaginaPref", tipoPaginaPref);
			record.setAttribute("skipPreview", true);

			ApponiTimbroWindow apponiTimbroWindow = new ApponiTimbroWindow(record, new ServiceCallback<Record>() {

				@Override
				public void execute(Record impostazioniTimbro) {
					protocollaBeforeTimbraEFirma(detail, recordDaProtocollare, impostazioniTimbro, mappaErrori, callbackManageFirmaEProtocolla);
				}
			});
			apponiTimbroWindow.show();
		}		
	}
	
	private static void protocollaBeforeTimbraEFirma(final DocumentDetail detail, final Record recordDaProtocollare, final Record impostazioniTimbro, final Map<String, String> mappaErrori, final ServiceCallback<DSResponse> callbackManageFirmaEProtocolla) {
		
		Layout.showWaitPopup(I18NUtil.getMessages().archivio_list_protocollazioneDocumenti_title());
		// Eseguo a protocollazione
		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lArchivioDatasource.performCustomOperation("protocollazioneBeforeTimbraEFirmaMassiva", recordDaProtocollare, new DSCallback() {

			@Override
			public void execute(final DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if(detail != null) {
						detail.reload(new DSCallback() {
							
							@Override
							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
								// bisogna ricaricare i dati a maschera dopo la protocollazione, in caso le operazioni successive vengano annullate
								detail.setSaved(true);
								if(detail.getLayout() != null) {
									detail.getLayout().viewMode();
								} else {
									detail.viewMode();
								}								
								afterProtocollaBeforeTimbraEFirma(response, recordDaProtocollare, impostazioniTimbro, mappaErrori, callbackManageFirmaEProtocolla);
							}
						});
					} else {
						afterProtocollaBeforeTimbraEFirma(response, recordDaProtocollare, impostazioniTimbro, mappaErrori, callbackManageFirmaEProtocolla);
					}					
				}
			}
		}, new DSRequest());
	}
	
	private static void afterProtocollaBeforeTimbraEFirma(DSResponse response, Record recordDaProtocollare, final Record impostazioniTimbro, final Map<String, String> mappaErrori, final ServiceCallback<DSResponse> callbackManageFirmaEProtocolla) {
		Record bozzeProtocollate = response.getData()[0]; 
		// Aggiorno la mappa degli errori
		Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages") != null ? response.getData()[0].getAttributeAsMap("errorMessages") : new HashMap<String, String>();
		mappaErrori.putAll(errorMessages);
		response.getData()[0].setAttribute("errorMessages", mappaErrori);
		// Recupero i file da timbrare e firmare
		Layout.showWaitPopup(I18NUtil.getMessages().archivio_list_recuperoFileDaTimbrareEFirmare_title());
		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lArchivioDatasource.performCustomOperation("getListaFileDaTimbrareEFirmareAfterProtocolla", bozzeProtocollate, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					// Aggiorno la mappa degli errori
					Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages") != null ? response.getData()[0].getAttributeAsMap("errorMessages") : new HashMap<String, String>();
					mappaErrori.putAll(errorMessages);
					response.getData()[0].setAttribute("errorMessages", mappaErrori);
					// Sono stati ritornati i file che devono essere timbrati e firmati firmati
					if(response.getData()[0].getAttributeAsRecordList("files") != null && !response.getData()[0].getAttributeAsRecordList("files").isEmpty()) {
						timbraBeforeFirma(response.getData()[0], impostazioniTimbro, mappaErrori, callbackManageFirmaEProtocolla);
					} else {
						callbackManageFirmaEProtocolla.execute(response);
					}
				}
			}
		}, new DSRequest());
	}
	
	private static void timbraBeforeFirma (Record fileDaTimbrare, Record impostazioniTimbro, final Map<String, String> mappaErrori, final ServiceCallback<DSResponse> callbackManageFirmaEProtocolla) {
		Layout.showWaitPopup(I18NUtil.getMessages().archivio_list_apposizioneTimbri_title());
		
		fileDaTimbrare.setAttribute("opzioniTimbro", impostazioniTimbro);
		
		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lArchivioDatasource.performCustomOperation("timbraFilePerProtocollaTimbraEFirma", fileDaTimbrare, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					// Aggiorno la mappa degli errori
					Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages") != null ? response.getData()[0].getAttributeAsMap("errorMessages") : new HashMap<String, String>();
					mappaErrori.putAll(errorMessages);
					response.getData()[0].setAttribute("errorMessages", mappaErrori);
					// Sono stati ritornati i file che devono essere firmati
					if(response.getData()[0].getAttributeAsRecordList("files") != null && !response.getData()[0].getAttributeAsRecordList("files").isEmpty()) {
						firmaAfterProtocolla(response.getData()[0], mappaErrori, callbackManageFirmaEProtocolla);
					} else {
						callbackManageFirmaEProtocolla.execute(response);
					}
				}
			}
		}, new DSRequest());
	}
	
	private static void firmaAfterProtocolla(final Record fileDaFirmare, final Map<String, String> mappaErrori, final ServiceCallback<DSResponse> callbackManageFirmaEProtocolla) {
		FirmaUtility.firmaMultipla(fileDaFirmare.getAttributeAsRecordArray("files"), 
				new FirmaMultiplaCallback() {

					@Override
					public void execute(Map<String, Record> files, Record[] filesAndUd) {
						// Arrivo qua solo se la firma ha avuto esito positivo, quindi ho sicuramente dei file firmati
						salvaAfterProtocollaTimbraEFirma(files, filesAndUd, mappaErrori, callbackManageFirmaEProtocolla);
					}
				},
				new FirmaMultiplaNonEseguitaCallback() {
					
					@Override
					public void execute(String errorMessage) {
						// Tutte le idUd sono andate in errore perchè la firma è fallita
						Record[] files = fileDaFirmare.getAttributeAsRecordArray("files");
						if (files != null) {
							for (int i = 0; i < files.length; i++) {
								mappaErrori.put(files[i].getAttribute("idUd"), "Il documento è stato protocollato. Non è stata tuttavia portata a termine l'operazione di firma.");
							}
						}
						// Creo un DSREsponse per la compatibilità delle callback
						DSResponse response = new DSResponse();
						response.setStatus(DSResponse.STATUS_SUCCESS);
						Record[] data = new Record[1];
						Record errori = new Record();
						errori.setAttribute("errorMessages", mappaErrori);
						data[0] = errori;
						response.setData(data);
						callbackManageFirmaEProtocolla.execute(response);
					}
				});
	}
	
	private static void salvaAfterProtocollaTimbraEFirma(Map<String, Record> files, Record[] filesAndUd, final Map<String, String> mappaErrori, final ServiceCallback<DSResponse> callbackManageFirmaEProtocolla) {		
		// Inserisco all'interno di un record i file che devono essere elaborati
		final Record lRecord = new Record();
		Record[] lRecords = new Record[files.size()];
		int i = 0;
		for (String lString : files.keySet()) {
			Record recordToInsert = files.get(lString);
			for (Record recordConUd : filesAndUd) {
				if (recordConUd.getAttribute("idFile").equals(lString)) {
					recordToInsert.setAttribute("idUd", recordConUd.getAttribute("idUd"));
				}
			}
			lRecords[i] = recordToInsert;
			i++;
		}
		lRecord.setAttribute("files", lRecords);
				
		Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lArchivioDatasource.performCustomOperation("aggiornaDocumentoAfterProtocollazioneTimbraEFirma", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					// Aggiorno la mappa degli errori
					Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages") != null ? response.getData()[0].getAttributeAsMap("errorMessages") : new HashMap<String, String>();
					mappaErrori.putAll(errorMessages);
					response.getData()[0].setAttribute("errorMessages", mappaErrori);
					callbackManageFirmaEProtocolla.execute(response);
				}
			}
		}, new DSRequest());
	}

}
