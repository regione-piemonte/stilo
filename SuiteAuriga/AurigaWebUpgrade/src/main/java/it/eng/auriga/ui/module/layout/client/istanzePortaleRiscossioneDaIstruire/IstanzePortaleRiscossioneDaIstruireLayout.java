/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.archivio.AssegnaUdAdAltroUfficioPopup;
import it.eng.auriga.ui.module.layout.client.archivio.AssegnazionePopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.istanze.IstanzeWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

public class IstanzePortaleRiscossioneDaIstruireLayout extends CustomLayout {

	protected MultiToolStripButton avviaIstruttoriaMultiButton;
	protected MultiToolStripButton assegnazioneAltroUfficioMultiButton;
	protected MultiToolStripButton restituzioneSmistatoreMultiButton;
	
	protected RecordList recordSelezionati;
	protected ToolStripButton newIstanzaButton;

	public IstanzePortaleRiscossioneDaIstruireLayout() {
		this("istanze_portale_riscossione_da_istruire", null, null, null);
	}

	public IstanzePortaleRiscossioneDaIstruireLayout(String nomeEntita) {
		this(nomeEntita, null, null, null);
	}

	public IstanzePortaleRiscossioneDaIstruireLayout(String nomeEntita,String finalita) {
		this(nomeEntita, finalita, null, null);
	}

	public IstanzePortaleRiscossioneDaIstruireLayout(String nomeEntita,String finalita, Boolean flgSelezioneSingola) {
		this(nomeEntita, finalita, flgSelezioneSingola, null);
	}

	public IstanzePortaleRiscossioneDaIstruireLayout(String nomeEntita,String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super(nomeEntita, new GWTRestDataSource("IstanzePortaleRiscossioneDaIstruireDatasource", "valore",FieldType.TEXT), 
				new IstanzePortaleRiscossioneDaIstruireFilter(nomeEntita),
				new IstanzePortaleRiscossioneDaIstruireList(nomeEntita),
				new CustomDetail(nomeEntita),
				finalita, showOnlyDetail);
		setMultiselect(true);
		this.setLookup(false);
		newButton.hide();
	}

	public static boolean isAbilToIns() {
		return true;
	}

	public static boolean isAbilToMod() {
		return true;
	}

	public static boolean isAbilToDel() {
		return false;
	}

	public static boolean isRecordAbilToMod(boolean flgLocked) {
		return !flgLocked && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgLocked) {
		return !flgLocked && isAbilToDel();
	}

	public static boolean isAbilToViewDetail() {
		return true;
	}

	public static boolean isAbilAltreOpButton() {
		return true;
	}
	
	public static boolean isAbilToViewDoc() {
		return true;
	}
	
	public static boolean isAbilToViewAllegati() {
		return true;
	}
	
	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_detail_edit_title(getTipoEstremiRecord(record));
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_detail_view_title(getTipoEstremiRecord(record));
	}

	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}

	@Override
	public boolean showMultiselectButtonsUnderList() {
		return true;
	}

	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {

		if (avviaIstruttoriaMultiButton == null) {
			avviaIstruttoriaMultiButton = new MultiToolStripButton("buttons/gear.png", this, I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_avviaIstruttoriaMultiButton()) {
				
				@Override
				public boolean toShow() {
					return true;
				}

				@Override
				public void doSomething() {					
					final RecordList listaIstanze = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaIstanze.add(list.getSelectedRecords()[i]);
					}
					avviaIstruttoria(listaIstanze);	
				}

				public void avviaIstruttoria(final RecordList listaIstanze) {
					
					final String tipoIstanza = getTipoIstanza();
					AurigaLayout.apriSceltaTipoFlussoIstanzaPopup(tipoIstanza, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							String oggettoProc = "Istruttoria istanze " + tipoIstanza + ":\n";
							for(int i = 0; i < listaIstanze.getLength(); i++) {
								oggettoProc += "- " + listaIstanze.get(i).getAttribute("nrIstanza") + ": " + listaIstanze.get(i).getAttribute("oggetto") + "\n"; 
							}

							Record record = new Record();					
							record.setAttribute("flowTypeId", object.getAttribute("flowTypeId"));				
							record.setAttribute("idProcessType", object.getAttribute("idProcessType"));
							record.setAttribute("nomeProcessType", object.getAttribute("nomeProcessType"));
							record.setAttribute("idDocTypeFinale", object.getAttribute("idDocTypeFinale")); 
							record.setAttribute("nomeDocTypeFinale", object.getAttribute("nomeDocTypeFinale"));						
							record.setAttribute("oggettoProc", oggettoProc);
							record.setAttribute("listaIstanze", listaIstanze);
							
							Layout.showWaitPopup("Avvio procedimento in corso...");
							GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AvvioProcTributiActivitiDatasource");
							lGwtRestService.call(record, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {	
									Layout.hideWaitPopup();
									Layout.addMessage(new MessageBean("Procedimento avviato con successo", "", MessageType.INFO));
									apriDettProcedimentoFromFolder(object.getAttribute("idFolder"));
									doSearch();
								}
							});
						}
					});					
				}
			};					
		}
		
		if (assegnazioneAltroUfficioMultiButton == null) {
			assegnazioneAltroUfficioMultiButton = new MultiToolStripButton("buttons/assegnazione_altro_ufficio.png", this, 
					I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_assegnazioneAltroUfficioMultiButton()) {
				
				@Override
				public boolean toShow() {
					return Layout.isPrivilegioAttivo("IAC/AAF");
				}

				@Override
				public void doSomething() {					
					final RecordList listaIstanze = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaIstanze.add(list.getSelectedRecords()[i]);
					}
					assegnazione(listaIstanze);	
				}

				public void assegnazione(final RecordList listaIstanze) {
					
					final AssegnaUdAdAltroUfficioPopup assegnaUdPopup = new AssegnaUdAdAltroUfficioPopup(null) {

						@Override
						public void onClickOkButton(Record record, final DSCallback callback) {
							
							record.setAttribute("listaRecord", listaIstanze);
							record.setAttribute("flgUdFolder", "U");
							Layout.showWaitPopup("Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
							try {
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaIstanze, "idUdFolder", "segnatura", "Assegnazione effettuata con successo",
												"Tutti i record selezionati per l'assegnazione sono andati in errore!",
												"Alcuni dei record selezionati per l'assegnazione sono andati in errore!", callback);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						}
					};
					assegnaUdPopup.show();		
				}
			};					
		}
		
		if (restituzioneSmistatoreMultiButton == null) {
			restituzioneSmistatoreMultiButton = new MultiToolStripButton("buttons/restituzione_smistatore.png", this, 
					I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_restituzioneSmistatoreMultiButton()) {
				
				@Override
				public boolean toShow() {
					return true;
				}

				@Override
				public void doSomething() {					
					final RecordList listaIstanze = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaIstanze.add(list.getSelectedRecords()[i]);
					}
					restituzione(listaIstanze);	
				}

				public void restituzione(final RecordList listaIstanze) {
					
					final AssegnazionePopup assegnazionePopup = new AssegnazionePopup("U", null) {

						@Override
						public void onClickOkButton(Record record, final DSCallback callback) {
							
							record.setAttribute("flgUdFolder", flgUdFolder);
							record.setAttribute("listaRecord", listaIstanze);
							
							Layout.showWaitPopup("Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
							try {
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaIstanze, "idUdFolder", "segnatura", "Assegnazione effettuata con successo",
												"Tutti i record selezionati per l'assegnazione sono andati in errore!",
												"Alcuni dei record selezionati per l'assegnazione sono andati in errore!", callback);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						}
					};
					assegnazionePopup.show();		
				}
			};					
		}
		
		return new MultiToolStripButton[] { avviaIstruttoriaMultiButton, assegnazioneAltroUfficioMultiButton};
	}
	
	public void apriDettProcedimentoFromFolder(String idFolder) {
		Record lRecordDetail = new Record();
		lRecordDetail.setAttribute("idUdFolder", idFolder);
		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);		
		lArchivioDatasource.getData(lRecordDetail, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record lRecordFolder = response.getData()[0];			
					AurigaLayout.apriDettaglioPratica(lRecordFolder.getAttribute("idProcess"), lRecordFolder.getAttribute("estremiProcess"));					
				} 				
			}
		});	
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();
		deleteButton.hide();
		editButton.hide();
		lookupButton.hide();
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	@Override
	public void setMultiselect(Boolean multiselect) {
		super.setMultiselect(multiselect);
		newButton.hide();
	}

	public RecordList getRecordSelezionati() {
		return recordSelezionati;
	}

	public void setRecordSelezionati(RecordList recordSelezionati) {
		this.recordSelezionati = recordSelezionati;
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
	protected GWTRestDataSource createNroRecordDatasource() {

		GWTRestDataSource referenceDatasource = new GWTRestDataSource("IstanzePortaleRiscossioneDaIstruireDatasource", "valore", FieldType.TEXT);
		referenceDatasource.setForceToShowPrompt(false);

		return referenceDatasource;
	}
	
	@Override
	public List<ToolStripButton> getDetailButtons() {
		List<ToolStripButton> detailButtons = new ArrayList<ToolStripButton>();
		detailButtons.add(altreOpButton);
		return detailButtons;
	}
	
	@Override
	protected ToolStripButton[] getCustomNewButtons() {
		
		if (isAbilToIns()) {
			newIstanzaButton = new ToolStripButton();   
			newIstanzaButton.setIcon("buttons/new.png");   
			newIstanzaButton.setIconSize(16); 
			newIstanzaButton.addHoverHandler(new HoverHandler() {
				
				@Override
				public void onHover(HoverEvent event) {
					newIstanzaButton.setPrompt(I18NUtil.getMessages().newIstanzaButton_prompt(getTipoIstanza()));
				}
			});
			newIstanzaButton.setCanHover(true);
			newIstanzaButton.addClickHandler(new ClickHandler() {	
				
				@Override
				public void onClick(ClickEvent event) {
					
					
					SceltaTipoProtoIstanzaPopup lSceltaTipoProtoIstanzaPopup = new SceltaTipoProtoIstanzaPopup(new ServiceCallback<Record>() {

						@Override
						public void execute(Record lRecord) {
							
							String sceltaProtocollo = lRecord.getAttribute("tipoProtocolloIstanza");
							if(sceltaProtocollo.equalsIgnoreCase("da protocollare")) {
								manageNewIstanzaClick(getTipoIstanza());
							} else {
								Record lRecordToLoad = new Record();
								lRecordToLoad.setAttribute("idUd", lRecord.getAttribute("idUd"));
								final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
								lGwtRestDataSource.performCustomOperation("get", lRecordToLoad, new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
												
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											final Record recordDettaglio = response.getData()[0];
											
											RecordList listaContribuenti = new RecordList();
																					
											RecordList listaEsibenti = recordDettaglio.getAttributeAsRecordList("listaEsibenti") != null
													&& !recordDettaglio.getAttributeAsRecordList("listaEsibenti").isEmpty() ?
														recordDettaglio.getAttributeAsRecordList("listaEsibenti") : null;		
											if(listaEsibenti != null) {
												for(int i=0; i < listaEsibenti.getLength(); i++) {
													Record esibenteRecord = listaEsibenti.get(i);
													if(esibenteRecord.getAttributeAsBoolean("flgAncheMittente") != null &&
														esibenteRecord.getAttributeAsBoolean("flgAncheMittente")) {
														Record contribuenteItem = populateContribuenteFromMittente(esibenteRecord);
														listaContribuenti.add(contribuenteItem);
													}
												}
											}
											if(listaContribuenti.isEmpty()) {
												RecordList listaMittenti = recordDettaglio.getAttributeAsRecordList("listaMittenti") != null
															&& !recordDettaglio.getAttributeAsRecordList("listaMittenti").isEmpty() ?
																recordDettaglio.getAttributeAsRecordList("listaMittenti") : null;		
												if(listaMittenti != null) {
													for(int i=0; i < listaMittenti.getLength(); i++) {
														Record mittenteRecord = listaMittenti.get(i);
														Record contribuenteItem = populateContribuenteFromMittente(mittenteRecord);
														listaContribuenti.add(contribuenteItem);
													}
												}
											}

											recordDettaglio.setAttribute("listaContribuenti", listaContribuenti);
											manageEditIstanzaClick(getTipoIstanza(), recordDettaglio);
										}
									}
								});
							}
						}
					});
					lSceltaTipoProtoIstanzaPopup.show();
				}   
			});  						
			return new ToolStripButton[]{newIstanzaButton};
		} 
		return new ToolStripButton[]{};
	}
	
	public void manageNewIstanzaClick(String tipoIstanza) {
		if(tipoIstanza != null) {			
			if("CED".equalsIgnoreCase(tipoIstanza)) {
				IstanzeWindow lIstanzeWindow = new IstanzeWindow("istanze_ced", null){
					
					@Override
					public void manageAfterCloseWindow() {
						doSearch();
					}
				};				
				lIstanzeWindow.newRecord(tipoIstanza);
				
			} else if("AUTOTUTELA".equalsIgnoreCase(tipoIstanza)) {
				IstanzeWindow lIstanzeWindow = new IstanzeWindow("istanze_autotutela", null){
					
					@Override
					public void manageAfterCloseWindow() {
						doSearch();
					}					
				};				
				lIstanzeWindow.newRecord(tipoIstanza);
			}
		}
	}
	
	public void manageEditIstanzaClick(String tipoIstanza, Record record) {
		if(tipoIstanza != null) {			
			if("CED".equalsIgnoreCase(tipoIstanza)) {
				IstanzeWindow lIstanzeWindow = new IstanzeWindow("istanze_ced", null){
					
					@Override
					public void manageAfterCloseWindow() {
						doSearch();
					}
				};				
				lIstanzeWindow.editRecord(record, tipoIstanza);
				
			} else if("AUTOTUTELA".equalsIgnoreCase(tipoIstanza)) {
				IstanzeWindow lIstanzeWindow = new IstanzeWindow("istanze_autotutela", null){
					
					@Override
					public void manageAfterCloseWindow() {
						doSearch();
					}					
				};				
				lIstanzeWindow.editRecord(record, tipoIstanza);
			}
		}
	}
	
	public String getTipoIstanza() {
		String tipoIstanza = null;
		AdvancedCriteria criteria = filter.getCriteria(true);
		if (criteria != null && criteria.getCriteria() != null){
			for (Criterion lCriterion : criteria.getCriteria()) {	
				if("tipoIstanza".equals(lCriterion.getFieldName())) {     
					tipoIstanza = lCriterion.getValueAsString();
					break;
				}									
			}
		}	
		return tipoIstanza;
	}
	
	private Record populateContribuenteFromMittente(Record mittente) {
		
		Record contribuente = new Record();
		if(mittente.getAttributeAsBoolean("flgAncheMittente") != null &&
				mittente.getAttributeAsBoolean("flgAncheMittente")) {
			contribuente.setAttribute("flgAncheMittente", mittente.getAttributeAsBoolean("flgAncheMittente"));
			contribuente.setAttribute("tipoSoggetto", mittente.getAttributeAsString("tipoSoggetto"));
			contribuente.setAttribute("codRapido", mittente.getAttributeAsString("codRapido"));
			contribuente.setAttribute("cognome", mittente.getAttributeAsString("cognome"));
			contribuente.setAttribute("nome", mittente.getAttributeAsString("nome"));
			contribuente.setAttribute("codFiscale", mittente.getAttributeAsString("codFiscale"));
			contribuente.setAttribute("denominazione", mittente.getAttributeAsString("denominazione"));													
		} else {
			contribuente.setAttribute("tipoSoggetto", mittente.getAttributeAsString("tipoMittente"));
			contribuente.setAttribute("codRapido", mittente.getAttributeAsString("codRapidoMittente"));
			contribuente.setAttribute("cognome", mittente.getAttributeAsString("cognomeMittente"));
			contribuente.setAttribute("nome", mittente.getAttributeAsString("nomeMittente"));
			contribuente.setAttribute("codFiscale", mittente.getAttributeAsString("codfiscaleMittente"));
			contribuente.setAttribute("denominazione", mittente.getAttributeAsString("denominazioneMittente"));													
		}
		
		contribuente.setAttribute("stato", mittente.getAttributeAsString("stato"));
		contribuente.setAttribute("nomeStato", mittente.getAttributeAsString("nomeStato"));
		contribuente.setAttribute("flgFuoriComune", mittente.getAttributeAsBoolean("flgFuoriComune"));
		contribuente.setAttribute("codToponimo", mittente.getAttributeAsString("codToponimo"));
		contribuente.setAttribute("tipoToponimo", mittente.getAttributeAsString("tipoToponimo"));
		contribuente.setAttribute("toponimo", mittente.getAttributeAsString("toponimo"));
		contribuente.setAttribute("indirizzo", mittente.getAttributeAsString("indirizzo"));
		contribuente.setAttribute("civico", mittente.getAttributeAsString("civico"));
		contribuente.setAttribute("interno", mittente.getAttributeAsString("interno"));
		contribuente.setAttribute("comune", mittente.getAttributeAsString("comune"));
		contribuente.setAttribute("nomeComune", mittente.getAttributeAsString("nomeComune"));
		contribuente.setAttribute("citta", mittente.getAttributeAsString("citta"));													
		contribuente.setAttribute("provincia", mittente.getAttributeAsString("provincia"));
		contribuente.setAttribute("frazione", mittente.getAttributeAsString("frazione"));
		contribuente.setAttribute("cap", mittente.getAttributeAsString("cap"));
		contribuente.setAttribute("zona", mittente.getAttributeAsString("zona"));
		contribuente.setAttribute("complementoIndirizzo", mittente.getAttributeAsString("complementoIndirizzo"));
		contribuente.setAttribute("appendici", mittente.getAttributeAsString("appendici"));
		
		return contribuente;
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
						recordsToSelect[rec++] = list.getRecordIndex(record);
						Record recordErrore = new Record();
						if ("U".equals(record.getAttribute("flgUdFolder"))) {
							recordErrore.setAttribute("idError", getEstremiUdFromLayout(record));
							errorMsg += "<br/>" + getEstremiUdFromLayout(record) + ": " + errorMessages.get(record.getAttribute(pkField));
						} else if ("F".equals(record.getAttribute("flgUdFolder"))) {
							recordErrore.setAttribute("idError", getEstremiFolderFromLayout(record));
							errorMsg += "<br/>" + getEstremiFolderFromLayout(record) + ": " + errorMessages.get(record.getAttribute(pkField));
						} else {
							recordErrore.setAttribute("idError", record.getAttribute(nameField));
							errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));							
						}
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
			} else if (errorMsg != null && !"".equals(errorMsg)) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				if (successMessage != null && !"".equalsIgnoreCase(successMessage)) {
					Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				}
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		} else {
			Layout.hideWaitPopup();
		}
	}
	
	/**
	 * METODI PER IL CALCOLO DEL TITOLO PER IL BOTTONE DI OSSERVAZIONI E NOTIFICHE
	 */
	private String getEstremiFolderFromLayout(Record record){
		String estremi = "";
		if(record.getAttribute("percorsoNome") != null && !"".equals(record.getAttribute("percorsoNome"))){
			estremi = record.getAttribute("percorsoNome");
		}else{
			if (record.getAttributeAsString("annoFascicolo") != null && !"".equals(record.getAttributeAsString("annoFascicolo"))) {
				estremi += record.getAttributeAsString("annoFascicolo") + " ";
			}
			if (record.getAttributeAsString("indiceClassifica") != null && !"".equals(record.getAttributeAsString("indiceClassifica"))) {
				estremi += record.getAttributeAsString("indiceClassifica") + " ";
			}
			if (record.getAttributeAsString("nroFascicolo") != null && !"".equals(record.getAttributeAsString("nroFascicolo"))) {
				estremi += "N° " + record.getAttributeAsString("nroFascicolo");
				if (record.getAttributeAsString("nroSottofascicolo") != null && !"".equals(record.getAttributeAsString("nroSottofascicolo"))) {
					estremi += "/" + record.getAttributeAsString("nroSottofascicolo");
				}
				estremi += " ";
			}
			if (record.getAttributeAsString("nome") != null && !"".equals(record.getAttributeAsString("nome"))) {
				estremi += record.getAttributeAsString("nome");
			}
		}
		return estremi;
	}
	
	private String getEstremiUdFromLayout(Record record){
		return record.getAttribute("segnatura");
	}
	
}