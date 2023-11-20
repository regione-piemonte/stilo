/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.data.RecordList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;


public class MonitoraggioOperazioniBatchLayout extends CustomLayout {	

	private final int ALT_POPUP_ERR_MASS = 400;
	private final int LARG_POPUP_ERR_MASS = 800;

	protected MultiToolStripButton mettiDaRiprocessareMultiButton;
	protected MultiToolStripButton togliDaRiprocessareMultiButton;
	
	private ToolStripButton nuovaRichiestaChiusuraButton;
	private ToolStripButton nuovaRichiestaRiassegnazioneButton;
	private ToolStripButton nuovaRichiestaVariazionePermessiButton;
	
	protected Record record;
	
	public MonitoraggioOperazioniBatchLayout() {
		this(null, null);
	}
	
	public MonitoraggioOperazioniBatchLayout(Boolean flgSelezioneSingola) {
		this(flgSelezioneSingola, null);
	}

	public MonitoraggioOperazioniBatchLayout(Boolean flgSelezioneSingola, Boolean showOnlyDetail) {				
		super("monitoraggio_operazioni_batch", 
				new GWTRestDataSource("MonitoraggioOperazioniBatchDataSource", "idRichiesta", FieldType.TEXT),  
				new ConfigurableFilter("monitoraggio_operazioni_batch"), 
				new MonitoraggioOperazioniBatchList("monitoraggio_operazioni_batch") ,
				new CustomDetail("monitoraggio_operazioni_batch"),
				null,
				flgSelezioneSingola,
				showOnlyDetail
		);
		
		setMultiselect(true);
		
		this.setLookup(false);
		
		if (!isAbilToIns()) {
			newButton.hide();
		}
	}	
	
	public static boolean isAbilToIns() {
		return false;
	}
	
	public static boolean isAbilToMod() {
		return false;
	}

	public static boolean isAbilToDel() {
		return false;
	}	
	
	public static boolean isAbilToView() {
		return false;
	}	
	
	public static boolean isRecordAbilToView(boolean flgLocked) {
		return !flgLocked && isAbilToView();
	}

	public static boolean isRecordAbilToMod(boolean flgLocked) {
		return !flgLocked && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgAttivo, boolean flgLocked) {
		return flgAttivo && !flgLocked && isAbilToDel();
	}	

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().monitoraggio_operazioni_batch_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().monitoraggio_operazioni_batch_detail_edit_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().monitoraggio_operazioni_batch_detail_view_title(getTipoEstremiRecord(record));		
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();		
		Record record = new Record(detail.getValuesManager().getValues());
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equals("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("1");
		if(isRecordAbilToDel(flgValido, recProtetto)){
			deleteButton.show();
		} else {
			deleteButton.hide();
		}	
		if(isRecordAbilToMod(recProtetto)) {
			editButton.show();
		} else{
			editButton.hide();
		}		
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	@Override
	protected Record createMultiLookupRecord(Record record) {
		return new Record();
	}
	
	@Override
	public boolean showMultiselectButtonsUnderList() {
		return true;
	}
	
	@Override
	public void setMultiselect(Boolean multiselect) {
		super.setMultiselect(multiselect);
		newButton.hide();
	}
	
	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {
		
		// Bottone per mettere la richiesta nello stato "da riprocessare" 
		if (mettiDaRiprocessareMultiButton == null) {
			mettiDaRiprocessareMultiButton = new MultiToolStripButton("pratiche/task/caricamento.png", this, I18NUtil.getMessages().monitoraggio_operazioni_batch_mettiDaRiprocessareMultiButton_title(), false) {

						public boolean toShow() {
							return true;
						}

						public void doSomething() {
							SC.ask("Sei sicuro  ?", new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if(value) {
										try {
											String nota = "Note da apporre sulle richieste elaborate";
											NotaWindow notaWindow = new NotaWindow(nota, new ServiceCallback<Record>() {
													@Override
													public void execute(Record recordNote) 
													{														
														final List<String> listaIdRichieste = getListaIdRichiesteSelezionate();
														final RecordList   listaRecord      = getListaRecordSelezionati();
														
														String stato = "da_riprocessare";
														String note  = String.valueOf(recordNote.getAttribute("nota"));
															
														if ((listaIdRichieste != null) && (listaIdRichieste.size() > 0)) {
															mettiRichiestaDaRiprocessareMassivo(listaIdRichieste, listaRecord, stato, note);
														} else {
																Layout.hideWaitPopup();
																Layout.addMessage(new MessageBean(I18NUtil.getMessages().monitoraggio_operazioni_batch_listaRecSelezionati_vuota_error(), "", MessageType.WARNING));
														}
												}
											});
											notaWindow.show();						
										}
										catch (Exception e) {
					   						Layout.hideWaitPopup();
					   					}
									}
								}								
							});
						}
					};
				}	
		
		// Bottone per togliere la richiesta nello stato "da riprocessare" 
		if (togliDaRiprocessareMultiButton == null) {
			togliDaRiprocessareMultiButton = new MultiToolStripButton("pratiche/task/buttons/annulla.png", this, I18NUtil.getMessages().monitoraggio_operazioni_batch_togliDaRiprocessareMultiButton_title(), false) {

								public boolean toShow() {
									return true;
								}

								public void doSomething() {
									SC.ask("Sei sicuro  ?", new BooleanCallback() {
										@Override
										public void execute(Boolean value) {
											if(value) {
												try {
													    String nota = "Note da apporre sulle richieste elaborate";
														NotaWindow notaWindow = new NotaWindow(nota, new ServiceCallback<Record>() {
															@Override
															public void execute(Record recordNote) {														
																final List<String> listaIdRichieste = getListaIdRichiesteSelezionate();
																final RecordList   listaRecord      = getListaRecordSelezionati();
																
																String stato = "eliminata";
																String note  = String.valueOf(recordNote.getAttribute("nota"));
																	
																if (listaIdRichieste != null && !listaIdRichieste.isEmpty()) {
																	togliRichiestaDaRiprocessareMassivo(listaIdRichieste, listaRecord, stato, note);
																} else {
																		Layout.hideWaitPopup();
																		Layout.addMessage(new MessageBean(I18NUtil.getMessages().monitoraggio_operazioni_batch_listaRecSelezionati_vuota_error(), "", MessageType.WARNING));
																}
														}
													});
													notaWindow.show();						
												}
												catch (Exception e) {
							   						Layout.hideWaitPopup();
							   					}
											}
										}								
									});
								}
							};
						}			
		return new MultiToolStripButton[] { mettiDaRiprocessareMultiButton, togliDaRiprocessareMultiButton};
	}
	
	private List<String> getListaIdRichiesteSelezionate() {
		final List<String> listaIdRichieste = new ArrayList<String>();
		if ((list != null) && (list.getSelectedRecord() != null) && (list.getSelectedRecords().length > 0)) {
			for (int i = 0; i < list.getSelectedRecords().length; i++) {
				listaIdRichieste.add(list.getSelectedRecords()[i].getAttributeAsString("idRichiesta"));
			}
		}
		return listaIdRichieste;
	}
	
	private RecordList getListaRecordSelezionati() {
		final RecordList listaRecord = new RecordList();
		for (int i = 0; i < list.getSelectedRecords().length; i++) {
			listaRecord.add(list.getSelectedRecords()[i]);
		}
		return listaRecord;
	}
	
	private void mettiRichiestaDaRiprocessareMassivo(final List<String> listaId, final RecordList listaRecord, String stato, String note) {
		Record input = new Record();
		input.setAttribute("idRichiesteList", listaId.toArray(new String[0]));
		input.setAttribute("stato", stato);
		input.setAttribute("note", note);
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("MonitoraggioOperazioniBatchDataSource");
		lGwtRestDataSource.executecustom("mettiTogliRichiestaDaRiprocessare", input, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				MessageBean messageBean = ErrorMessagePopup(response,                                         
															listaRecord, 
						                                    "idRichiesta", 
						                                    "idRichiesta", 
						                                    I18NUtil.getMessages().monitoraggio_operazioni_batch_metti_da_riprocessare_successo(), 
						                                    I18NUtil.getMessages().monitoraggio_operazioni_batch_metti_da_riprocessare_errore_parziale(), 
						                                    I18NUtil.getMessages().monitoraggio_operazioni_batch_metti_da_riprocessare_errore_totale(), 
						                                    I18NUtil.getMessages().monitoraggio_operazioni_batch_metti_da_riprocessare_errore()
						                                   );				
				Layout.addMessage(messageBean);
				Layout.hideWaitPopup();
				
				Record data = response.getData()[0];
				boolean storeInError = data.getAttributeAsBoolean("storeInError") != null ? data.getAttributeAsBoolean("storeInError") : false;
				if (!storeInError) {
					reloadList();
				}
			}
		});
	}
	
	
	private void togliRichiestaDaRiprocessareMassivo(final List<String> listaId, final RecordList listaRecord, String stato, String note) {
		Record input = new Record();
		input.setAttribute("idRichiesteList", listaId.toArray(new String[0]));
		input.setAttribute("stato", stato);
		input.setAttribute("note", note);
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("MonitoraggioOperazioniBatchDataSource");
		lGwtRestDataSource.executecustom("mettiTogliRichiestaDaRiprocessare", input, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				MessageBean messageBean = ErrorMessagePopup(response,                                         
															listaRecord, 
						                                    "idRichiesta", 
						                                    "idRichiesta", 
						                                    I18NUtil.getMessages().monitoraggio_operazioni_batch_togli_da_riprocessare_successo(), 
						                                    I18NUtil.getMessages().monitoraggio_operazioni_batch_togli_da_riprocessare_errore_parziale(), 
						                                    I18NUtil.getMessages().monitoraggio_operazioni_batch_togli_da_riprocessare_errore_totale(), 
						                                    I18NUtil.getMessages().monitoraggio_operazioni_batch_togli_da_riprocessare_errore()
						                                   );				
				Layout.addMessage(messageBean);
				Layout.hideWaitPopup();

				Record data = response.getData()[0];
				boolean storeInError = data.getAttributeAsBoolean("storeInError") != null ? data.getAttributeAsBoolean("storeInError") : false;
				if (!storeInError) {
					reloadList();
				}

			}
		});
	}
	
	private MessageBean ErrorMessagePopup(DSResponse response, RecordList listaRecord, String pkField, String nameField, String successMessage, String partialErrorMessage, String completeErrorMessage, String genericErrorMessage) {
		MessageBean messageBean = null;
		String strMessageBean = "";
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			boolean storeInError = data.getAttributeAsBoolean("storeInError") != null ? data.getAttributeAsBoolean("storeInError") : false;
			if (storeInError) {
				String storeInErrorMessage = data.getAttribute("storeErrorMessage") != null && !"".equalsIgnoreCase(data.getAttribute("storeErrorMessage")) ? data.getAttribute("storeErrorMessage") : genericErrorMessage;
				return new MessageBean(storeInErrorMessage, "", MessageType.ERROR);
			} else {
				Map errorMessages = data.getAttributeAsMap("errorMessages");
				if ((errorMessages != null) && (errorMessages.size() > 0)) {
					RecordList listaErrori = new RecordList();
					Record recordErrore = null;
					if ((listaRecord.getLength() > errorMessages.size()) && (storeInError == false)) {
						strMessageBean = partialErrorMessage != null ? partialErrorMessage : "";
						messageBean = new MessageBean(strMessageBean, "", MessageType.WARNING);
					} else {
						strMessageBean = completeErrorMessage != null ? completeErrorMessage : "";
						messageBean = new MessageBean(strMessageBean, "", MessageType.ERROR);
					}
					for (int i = 0; i < listaRecord.getLength(); i++) {
						Record record = listaRecord.get(i);
						String idRichiesta = record.getAttribute("idRichiesta");
						if (errorMessages.get(idRichiesta) != null) {
							recordErrore = new Record();
							
							// Leggo l'errore composto da col 2 (descrizione richiesta) + col 4 (descrizione errore) 
							String errMessage = (String) errorMessages.get(record.getAttribute("idRichiesta")).toString();
							
							String idError = "";
							String descErrore = "";
							
							// Splitto 
							String[] split = errMessage.split("#");
							
							// Estraggo la descrizione della richiesta
							if (split.length >= 1){
								idError = split[0];
							}
							
							// Estraggo la descrizione dell'errore
							if (split.length >= 2){
								descErrore = split[1];
							}

							recordErrore.setAttribute("idError", idError);
							recordErrore.setAttribute("descrizione", descErrore);
							listaErrori.add(recordErrore);
						}
					}
					ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Richieste", listaErrori, listaRecord.getLength(), LARG_POPUP_ERR_MASS,ALT_POPUP_ERR_MASS);
					errorePopup.show();
				} else {
					strMessageBean = successMessage != null ? successMessage : "";
					messageBean = new MessageBean(strMessageBean, "", MessageType.INFO);
				}

			}
		}
		return messageBean;
	}
	
	public void reloadList() {
		doSearch();
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		GWTRestDataSource datasource = new GWTRestDataSource("MonitoraggioOperazioniBatchDataSource", "idRichiesta", FieldType.TEXT);
		return datasource;
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
	protected ToolStripButton[] getCustomNewButtons() {
		
		if (nuovaRichiestaChiusuraButton == null) {
			nuovaRichiestaChiusuraButton = new ToolStripButton();
			nuovaRichiestaChiusuraButton.setIcon("lookup/nuovaRichiestaChiusura.png");
			nuovaRichiestaChiusuraButton.setIconSize(16);
			nuovaRichiestaChiusuraButton.setPrompt("Nuova rich. chiusura");
			nuovaRichiestaChiusuraButton.setVisible(isNuovaRichiestaChiusuraButtonVisible());				
			nuovaRichiestaChiusuraButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					final Record recordIn = getRecord();
					final NuovaRichiestaChiusuraPopup nuovaRichiestaChiusuraPopup = new NuovaRichiestaChiusuraPopup(recordIn) {
						@Override
						public void onClickOkButton(Record object, final DSCallback callback) {
							
							Record record = new Record();
							record.setAttribute("tipoOggettiDaChiudere",      object.getAttribute("tipoOggettiDaChiudere"));
							record.setAttribute("caselle",                    object.getAttribute("caselle"));
							record.setAttribute("dtOperazione",               object.getAttributeAsDate("dtOperazione"));
							record.setAttribute("dataInvioDa",                object.getAttributeAsDate("dataInvioDa"));
							record.setAttribute("dataInvioA",                 object.getAttributeAsDate("dataInvioA"));
							record.setAttribute("periodoApertura",            object.getAttribute("periodoApertura"));
							record.setAttribute("tipoPeriodoApertura",        object.getAttribute("tipoPeriodoApertura"));
							record.setAttribute("periodoSenzaOperazioni",     object.getAttribute("periodoSenzaOperazioni"));
							record.setAttribute("tipoPeriodoSenzaOperazioni", object.getAttribute("tipoPeriodoSenzaOperazioni"));
							record.setAttribute("motivazioneRichiesta",       object.getAttribute("motivazioneRichiesta"));
							record.setAttribute("noteRichiesta",              object.getAttribute("noteRichiesta"));									
                            if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
                            	record.setAttribute("struttureNONSTD", object.getAttributeAsJavaScriptObject("struttureNONSTD"));
							}
							else{
								record.setAttribute("struttureSTD", object.getAttribute("struttureSTD"));
							}								
							final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("MonitoraggioOperazioniBatchDataSource");								
							lGwtRestDataSource.executecustom("nuovaRichiestaChiusura", record, new DSCallback() {
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									Layout.hideWaitPopup();
									if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record data = response.getData()[0];	
										String idJob = data.getAttributeAsString("idJob");									
										if(idJob != null) 
											Layout.addMessage(new MessageBean("Richiesta acquisita. Assegnato alla richiesta il N° " + idJob, "", MessageType.INFO));
										else
											Layout.addMessage(new MessageBean("Richiesta fallita.", "", MessageType.INFO));
									}
								}
							});
						}
						
					};
					nuovaRichiestaChiusuraPopup.show();						
				}
			});
		}
		
		if (nuovaRichiestaRiassegnazioneButton == null) {
			nuovaRichiestaRiassegnazioneButton = new ToolStripButton();
			nuovaRichiestaRiassegnazioneButton.setIcon("lookup/nuovaRichiestaRiassegnazione.png");
			nuovaRichiestaRiassegnazioneButton.setIconSize(16);
			nuovaRichiestaRiassegnazioneButton.setPrompt("Nuova rich. riassegnazione");
			nuovaRichiestaRiassegnazioneButton.setVisible(isNuovaRichiestaRiassegnazioneButtonVisible());
			nuovaRichiestaRiassegnazioneButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					final NuovaRichiestaRiassegnazionePopup nuovaRichiestaRiassegnazionePopup = new NuovaRichiestaRiassegnazionePopup() {
						
						@Override
						public void onClickOkButton(Record object, final DSCallback callback) {
							
							Record recordToSend = new Record();
							recordToSend.setAttribute("tipoOggettoRiassegnare", object.getAttribute("tipoOggettoRiassegnare")!=null ? object.getAttribute("tipoOggettoRiassegnare") : null);
							recordToSend.setAttribute("dataeOraOperazione", object.getAttributeAsDate("dataeOraOperazione")!=null ? object.getAttributeAsDate("dataeOraOperazione") : null);
							recordToSend.setAttribute("organigrammaUoPostazioneDa", object.getAttribute("organigrammaUoPostazioneDa")!=null ? object.getAttribute("organigrammaUoPostazioneDa") : null);
							recordToSend.setAttribute("organigrammaUoPostazioneVs", object.getAttribute("organigrammaUoPostazioneVs")!=null ? object.getAttribute("organigrammaUoPostazioneVs") : null);
							recordToSend.setAttribute("motivazioneRichiesta", object.getAttribute("motivazioneRichiesta")!=null ? object.getAttribute("motivazioneRichiesta") : null);
							recordToSend.setAttribute("noteRichiesta", object.getAttribute("noteRichiesta")!=null ? object.getAttribute("noteRichiesta") : null);
							
							final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("MonitoraggioOperazioniBatchDataSource");
							lGwtRestDataSource.extraparam.put("tipoOperazione", "spostamento");
							lGwtRestDataSource.executecustom("nuovaRichiestaRiassegnazioneVariazione", recordToSend, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData,DSRequest request) {
									Layout.hideWaitPopup();
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record data = response.getData()[0];
										if (data.getAttributeAsString("storeErrorMessage") != null) {
											Layout.addMessage(new MessageBean(data.getAttributeAsString("storeErrorMessage"), "",MessageType.ERROR));
										} else {
											Layout.addMessage(new MessageBean("Operazione effettuata con successo","", MessageType.INFO));
										}
									}
									markForDestroy();
									reloadList();
								}
							});
						}
					};
					nuovaRichiestaRiassegnazionePopup.show();	
				}
			});
		}
		
		if (nuovaRichiestaVariazionePermessiButton == null) {
			nuovaRichiestaVariazionePermessiButton = new ToolStripButton();
			nuovaRichiestaVariazionePermessiButton.setIcon("lookup/nuovaRichiestaVariazionePermessi.png");
			nuovaRichiestaVariazionePermessiButton.setIconSize(16);
			nuovaRichiestaVariazionePermessiButton.setPrompt("Nuova rich. variazione permessi");
			nuovaRichiestaVariazionePermessiButton.setVisible(isNuovaRichiestavariazionePermessiButtonVisible());
			nuovaRichiestaVariazionePermessiButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					final NuovaRichiestaVariazionePermessiPopup nuovaRichiestaVariazionePermessiPopup = new NuovaRichiestaVariazionePermessiPopup() {
						
						@Override
						public void onClickOkButton(Record object, final DSCallback callback) {
							
							Record recordToSend = new Record();
							recordToSend.setAttribute("tipoOggettoRiassegnare", object.getAttribute("tipoOggettoRiassegnare")!=null ? object.getAttribute("tipoOggettoRiassegnare") : null);
							recordToSend.setAttribute("dataeOraOperazione", object.getAttributeAsDate("dataeOraOperazione")!=null ? object.getAttributeAsDate("dataeOraOperazione") : null);
							recordToSend.setAttribute("organigrammaUoPostazioneDa", object.getAttribute("organigrammaUoPostazioneDa")!=null ? object.getAttribute("organigrammaUoPostazioneDa") : null);
							recordToSend.setAttribute("organigrammaUoPostazioneVs", object.getAttribute("organigrammaUoPostazioneVs")!=null ? object.getAttribute("organigrammaUoPostazioneVs") : null);
							recordToSend.setAttribute("motivazioneRichiesta", object.getAttribute("motivazioneRichiesta")!=null ? object.getAttribute("motivazioneRichiesta") : null);
							recordToSend.setAttribute("noteRichiesta", object.getAttribute("noteRichiesta")!=null ? object.getAttribute("noteRichiesta") : null);
							
							final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("MonitoraggioOperazioniBatchDataSource");
							lGwtRestDataSource.extraparam.put("tipoOperazione", "modifica_permessi");
							lGwtRestDataSource.executecustom("nuovaRichiestaRiassegnazioneVariazione", recordToSend, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData,DSRequest request) {
									Layout.hideWaitPopup();
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record data = response.getData()[0];
										if (data.getAttributeAsString("storeErrorMessage") != null) {
											Layout.addMessage(new MessageBean(data.getAttributeAsString("storeErrorMessage"), "",MessageType.ERROR));
										} else {
											Layout.addMessage(new MessageBean("Operazione effettuata con successo","", MessageType.INFO));
										}
									}
									markForDestroy();
									reloadList();
								}
							});
						}
					};
					nuovaRichiestaVariazionePermessiPopup.show();	
				
				}
			});
		}
		
		return new ToolStripButton[] { nuovaRichiestaChiusuraButton , nuovaRichiestaRiassegnazioneButton, nuovaRichiestaVariazionePermessiButton };
	}
	
	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}
	
	public boolean isNuovaRichiestaChiusuraButtonVisible(){
		return (Layout.isPrivilegioAttivo("ROM/M/CHM") || Layout.isPrivilegioAttivo("ROM/M/CHF"));		
	}
	
	public boolean isNuovaRichiestaRiassegnazioneButtonVisible(){
		return (Layout.isPrivilegioAttivo("ROM/M/SPM") || Layout.isPrivilegioAttivo("ROM/M/SPD") || Layout.isPrivilegioAttivo("ROM/M/SPF"));
	}
	
	public boolean isNuovaRichiestavariazionePermessiButtonVisible(){
		return (Layout.isPrivilegioAttivo("ROM/M/VPD") || Layout.isPrivilegioAttivo("ROM/M/VPF"));
	}
	
}