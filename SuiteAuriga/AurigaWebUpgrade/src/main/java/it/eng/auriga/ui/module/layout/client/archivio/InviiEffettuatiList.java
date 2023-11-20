/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility.StampaEtichettaCallback;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.StampaEtichettaPopup;
import it.eng.auriga.ui.module.layout.shared.util.AzioniRapide;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class InviiEffettuatiList extends CustomList {

	private ListGridField idInvioNotifica;
	private ListGridField flgInvioNotifica;
	private ListGridField desMittente;
	private ListGridField desUteInvioNotifica;
	private ListGridField tsInvioNotifica;
	private ListGridField desDestinatario;
	private ListGridField desMotivo;
	private ListGridField livPriorita;
	private ListGridField messaggio;
	private ListGridField iconaFlgAnnullabile;
	
	private Record archivioRecord;
	
	public InviiEffettuatiList(String nomeEntita, Record record) {
		
		super(nomeEntita);
		
		this.archivioRecord = record;
		
		idInvioNotifica = new ListGridField("idInvioNotifica");
		idInvioNotifica.setHidden(true);
		idInvioNotifica.setCanHide(false);		
		
		flgInvioNotifica = new ListGridField("flgInvioNotifica", I18NUtil.getMessages().invii_effettuati_list_flgInvioNotifica());
		flgInvioNotifica.setType(ListGridFieldType.ICON);
		flgInvioNotifica.setWidth(30);
		flgInvioNotifica.setIconWidth(16);
		flgInvioNotifica.setIconHeight(16);
		Map<String, String> flgInvioNotificaValueIcons = new HashMap<String, String>();
		flgInvioNotificaValueIcons.put("I", "archivio/assegna.png");
		flgInvioNotificaValueIcons.put("N", "archivio/condividi.png");
		flgInvioNotifica.setValueIcons(flgInvioNotificaValueIcons);
		Map<String, String> flgInvioNotificaValueHovers = new HashMap<String, String>();
		flgInvioNotificaValueHovers.put("I", "Invio per competenza");
		flgInvioNotificaValueHovers.put("N", "Invio per conoscenza");
		flgInvioNotifica.setAttribute("valueHovers", flgInvioNotificaValueHovers);
		
		desUteInvioNotifica = new ListGridField("desUteInvioNotifica", I18NUtil.getMessages().invii_effettuati_list_desUteInvioNotifica());	
		
		tsInvioNotifica = new ListGridField("tsInvioNotifica", I18NUtil.getMessages().invii_effettuati_list_tsInvioNotifica());	
		tsInvioNotifica.setType(ListGridFieldType.DATE);
		tsInvioNotifica.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);		
		tsInvioNotifica.setWrap(false);	
		
		desMittente = new ListGridField("desMittente", I18NUtil.getMessages().invii_effettuati_list_desMittente());	
		
		desDestinatario = new ListGridField("desDestinatario", I18NUtil.getMessages().invii_effettuati_list_desDestinatario());
		
		desMotivo = new ListGridField("desMotivo", I18NUtil.getMessages().invii_effettuati_list_desMotivo());	
		
		messaggio = new ListGridField("messaggio", I18NUtil.getMessages().invii_effettuati_list_messaggio());	
		
		livPriorita = new ListGridField("livPriorita", I18NUtil.getMessages().invii_effettuati_list_livPriorita());		
		livPriorita.setType(ListGridFieldType.ICON);
		livPriorita.setWidth(30);
		livPriorita.setIconWidth(16);
		livPriorita.setIconHeight(16);
		Map<String, String> livPrioritaValueIcons = new HashMap<String, String>();			
		livPrioritaValueIcons.put("-1", "prioritaBassa.png");
		livPrioritaValueIcons.put("0", "prioritaMedia.png");
		livPrioritaValueIcons.put("1", "prioritaAlta.png");
		livPrioritaValueIcons.put("2", "prioritaAltissima.png");		
		livPrioritaValueIcons.put("",  "blank.png");
		livPriorita.setValueIcons(livPrioritaValueIcons);		
		livPriorita.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				String valuePriorita = record.getAttribute("livPriorita");
				Integer priorita = valuePriorita != null && !"".equals(String.valueOf(valuePriorita)) ? new Integer(String.valueOf(valuePriorita)) : null;
				if(priorita != null) {
					String res = "";
					switch(priorita){				
						case -1:		// priorita' bassa
							res = I18NUtil.getMessages().prioritaBassa_Alt_value();
							break;
						case 0:		// priorita' normale
							res = I18NUtil.getMessages().prioritaNormale_Alt_value();
							break;
						case 1:		// priorita' alta
							res = I18NUtil.getMessages().prioritaAlta_Alt_value();
							break;
						case 2:		// priorita' altissima
							res = I18NUtil.getMessages().prioritaAltissima_Alt_value();
							break;
					} 
					return res;
				}
				return null;	
			}
		});				
		
		iconaFlgAnnullabile = new ControlListGridField("iconaFlgAnnullabile", I18NUtil.getMessages().invii_effettuati_list_iconaFlgAnnullabile());   
		iconaFlgAnnullabile.setAttribute("custom", true);			
		iconaFlgAnnullabile.setShowHover(true);			
		iconaFlgAnnullabile.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				final String flgAnnullabile  = record.getAttributeAsString("flgAnnullabile");				
				if("0".equals(flgAnnullabile)) {
					return buildIconHtml("ko.png");												
				} 				
				return null;					
			}
		});
		iconaFlgAnnullabile.setHoverCustomizer(new HoverCustomizer() {	
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				final String flgAnnullabile  = record.getAttributeAsString("flgAnnullabile");
				final String motivoNonAnnullabile  = record.getAttributeAsString("motivoNonAnnullabile");	
				if("0".equals(flgAnnullabile)) {
					if(motivoNonAnnullabile != null && !"".equals(motivoNonAnnullabile)) {
						return "Invio non più annullabile/modificabile per il seguente motivo: " + motivoNonAnnullabile;
					} else return "Invio non più annullabile/modificabile";						
				} 				
				return null;
			}
		});		
		
		setFields(new ListGridField[] {
			idInvioNotifica,
			flgInvioNotifica,
			desMittente,
			desUteInvioNotifica,
			tsInvioNotifica,
			desDestinatario,
			desMotivo,
			livPriorita,
			messaggio,
			iconaFlgAnnullabile
		});  
		
	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 50;
	}
	
	// Definisco i bottoni DETTAGLIO/MODIFICA/CANCELLA/SELEZIONA
	@Override  
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {  
		
		Canvas lCanvasReturn = null;
		
		if (fieldName.equals("buttons")) {	
			
			ImgButton modifyButton = buildModifyButton(record);
			ImgButton deleteButton = buildDeleteButton(record);    			
				
			if(!isRecordAbilToMod(record)) {
				modifyButton.disable();				
			}
			
			if(!isRecordAbilToDel(record)) {
				deleteButton.disable();
			}
			
			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);   
			recordCanvas.setMembersMargin(7);
			
			recordCanvas.addMember(modifyButton);
			recordCanvas.addMember(deleteButton);
						
			lCanvasReturn = recordCanvas;	
		}	
	
		return lCanvasReturn;
	}
	
	public void getDestinatariPreferitiUtenteAssegnazione(String flgUdFolder, String idUdFolder, final ServiceCallback<Record> callback) {
		Record recordDestPref = new Record();						
		RecordList listaAzioniRapide = new RecordList();
		Record recordAzioneRapida = new Record();
		if(flgUdFolder.equals("U")){
			recordDestPref.setAttribute("idUd", idUdFolder);
			recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue());
		} else {
			recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_FOLDER.getValue()); 
		}
		listaAzioniRapide.add(recordAzioneRapida);
		recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
		lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {					
					Record destinatariPreferiti = response.getData()[0];
					if(callback != null) {
						callback.execute(destinatariPreferiti);
					}
				}
			}
		}, new DSRequest());		
	}
				
	public void getDestinatariPreferitiUtenteCondivisione(String flgUdFolder, String idUdFolder, final ServiceCallback<Record> callback) { 
		Record recordDestPref = new Record();						
		RecordList listaAzioniRapide = new RecordList();
		Record recordAzioneRapida = new Record();
		if(flgUdFolder.equals("U")){
			recordDestPref.setAttribute("idUd", idUdFolder);
			recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue());
		} else {
			recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_FOLDER.getValue()); 
		}
		listaAzioniRapide.add(recordAzioneRapida);
		recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
		lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {					
					Record destinatariPreferiti = response.getData()[0];
					if(callback != null) {
						callback.execute(destinatariPreferiti);
					}
				}
			}
		}, new DSRequest());	
	}
	
	@Override
	protected void manageModifyButtonClick(final ListGridRecord record) {

		String flgInvioNotifica = record.getAttribute("flgInvioNotifica") != null ? record.getAttribute("flgInvioNotifica") : "";					
		final String flgUdFolder = archivioRecord != null ? archivioRecord.getAttribute("flgUdFolder") : null;
		final String idUdFolder = archivioRecord != null ? archivioRecord.getAttribute("idUdFolder") : null;
		final String estremiUdFolder = archivioRecord != null ? archivioRecord.getAttribute("estremiUdFolder") : null;
		final String flgTipoProv = archivioRecord != null ? archivioRecord.getAttribute("flgTipoProv") : null;
		
		if("I".equals(flgInvioNotifica)) {
			
			getDestinatariPreferitiUtenteAssegnazione(flgUdFolder, idUdFolder, new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record destinatariPreferiti) {
					
					String title = "Modifica dati assegnazione";
					if (estremiUdFolder != null && !"".equals(estremiUdFolder)) {
						if(flgUdFolder != null && "U".equals(flgUdFolder)) {
							title += " del documento " + estremiUdFolder; 
						} else if(flgUdFolder != null && "F".equals(flgUdFolder)) {
							title += " del fascicolo " + estremiUdFolder;
						}
					}
					String desDestinatario = record != null ? record.getAttribute("desDestinatario") : null;
					if (desDestinatario != null && !"".equals(desDestinatario)) {
						title += " a " + desDestinatario;
					}
					
					RecordList listaUOPreferiti = null;
					RecordList listaUtentiPreferiti = null;
					if(flgUdFolder != null && flgUdFolder.equals("U")){
//						listaUOPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUOPreferiteDoc");
//						listaUtentiPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUtentiPreferitiDoc");
						listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_DOC.getValue()));
						listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.ASSEGNA_DOC.getValue()));
	
					} else if(flgUdFolder != null && flgUdFolder.equals("F")){
//						listaUOPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUOPreferiteFolder");
//						listaUtentiPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUtentiPreferitiFolder");
						listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_FOLDER.getValue()));
						listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.ASSEGNA_FOLDER.getValue()));					
					}
					
					final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare
					
					boolean flgSoloUo = false;
					final String codSupportoOrig = archivioRecord != null ? archivioRecord.getAttribute("codSupportoOrig") : null;
					
					//nel caso di folder non sarà valorizzato
					if(codSupportoOrig != null) {
						if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
						   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
						   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
							flgSoloUo = true;
						}
					}
					
					if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
						listaPreferiti.addList(listaUOPreferiti.toArray());				
					}
					
					if(!flgSoloUo) {
						if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
							listaPreferiti.addList(listaUtentiPreferiti.toArray());					
						}
					}
					
					final RecordList listaAssPreselMitt = archivioRecord != null ? archivioRecord.getAttributeAsRecordList("listaAssPreselMitt") : null;
					
					AssegnazionePopup assegnazionePopup = new AssegnazionePopup(record.getAttribute("flgUdFolder"), record, title, "InviiEffettuatiList") {							
						
						@Override
						public String getFlgTipoProvDoc() {
							if(flgUdFolder.equals("U")) {
								return flgTipoProv;														
							}
							return null;
						}
						
						@Override
						public String getCodSupportoOrig() {
							return codSupportoOrig;
						}
						
						@Override
						public RecordList getListaPreferiti() {
							return listaPreferiti;
						}
						
						@Override
						public RecordList getListaAssegnatariMitt() {
							return listaAssPreselMitt;
						}
						
						@Override
						public void onClickOkButton(Record lRecord, final DSCallback callback) {

							final RecordList listaUdFolder = new RecordList();
							listaUdFolder.add(record);						
							
							lRecord.setAttribute("flgUdFolder", record.getAttribute("flgUdFolder"));
							lRecord.setAttribute("listaRecord", listaUdFolder);
							lRecord.setAttribute("idInvio", record.getAttribute("idInvioNotifica"));	
							
							Layout.showWaitPopup("Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");	
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
							try {
								lGwtRestDataSource.addData(lRecord, new DSCallback() {
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										operationCallback(
												response, record, "idUdFolder", 
												"Assegnazione effettuata con successo", 
												"Si è verificato un errore durante l'assegnazione!", 
												new DSCallback() {
													
													@Override
													public void execute(DSResponse response, Object rawData, DSRequest request) {														
														if(callback != null){
															callback.execute(response, rawData, request);
														}
														
														String idUdFolder = archivioRecord != null ? archivioRecord.getAttribute("idUdFolder") : null;
														String flgUdFolder = archivioRecord != null ? archivioRecord.getAttribute("flgUdFolder") : null;
														if(flgUdFolder != null && "U".equals(flgUdFolder)) {
															final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
															Record lRecordToLoad = new Record();
															lRecordToLoad.setAttribute("idUd", idUdFolder);
															lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

																@Override
																public void execute(DSResponse response, Object rawData, DSRequest request) {
																	if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
																		manageStampaEtichettaPostAssegnazione(archivioRecord, response.getData()[0]);
																	}
																}
															});
														}
													}
												});
									}
								});
							} catch(Exception e) {
								Layout.hideWaitPopup();
							}								
						}
					};
					assegnazionePopup.editRecordPerModificaInvio(prepareRecordInvio(record));
					assegnazionePopup.show();
				}
			});
			
		} else if("N".equals(flgInvioNotifica)) {
			
			getDestinatariPreferitiUtenteCondivisione(flgUdFolder, idUdFolder, new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record destinatariPreferiti) {
					
					String title = "Modifica dati invio per conoscenza";
					if (estremiUdFolder != null && !"".equals(estremiUdFolder)) {
						if(flgUdFolder != null && "U".equals(flgUdFolder)) {
							title += " del documento " + estremiUdFolder; 
						} else if(flgUdFolder != null && "F".equals(flgUdFolder)) {
							title += " del fascicolo " + estremiUdFolder;
						}
					}
					String desDestinatario = record != null ? record.getAttribute("desDestinatario") : null;
					if (desDestinatario != null && !"".equals(desDestinatario)) {
						title += " a " + desDestinatario;
					}
					
					RecordList listaUOPreferiti = null;
					RecordList listaUtentiPreferiti = null;
					if(flgUdFolder != null && flgUdFolder.equals("U")){
//						listaUOPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUOPreferiteDoc");
//						listaUtentiPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUtentiPreferitiDoc");
						listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.INVIO_CC_DOC.getValue()));
						listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.INVIO_CC_DOC.getValue()));
	
					} else if(flgUdFolder != null && flgUdFolder.equals("F")){
//						listaUOPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUOPreferiteFolder");
//						listaUtentiPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUtentiPreferitiFolder");
						listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.INVIO_CC_FOLDER.getValue()));
						listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.INVIO_CC_FOLDER.getValue()));					
					}
					
					final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare
					
					if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
						listaPreferiti.addList(listaUOPreferiti.toArray());				
					}
					
					if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
						listaPreferiti.addList(listaUtentiPreferiti.toArray());
					}	
					
					CondivisionePopup condivisionePopup = new CondivisionePopup(record.getAttribute("flgUdFolder"), record, title) {
						
						@Override
						public String getFlgTipoProvDoc() {
							if(flgUdFolder.equals("U")) {
								return flgTipoProv;														
							}
							return null;
						}												
						
						@Override
						public RecordList getListaPreferiti() {
							return listaPreferiti;
						}
						
						@Override
						public void onClickOkButton(Record lRecord, final DSCallback callback) {

							final RecordList listaUdFolder = new RecordList();
							listaUdFolder.add(record);						
							
							lRecord.setAttribute("flgUdFolder", record.getAttribute("flgUdFolder"));
							lRecord.setAttribute("listaRecord", listaUdFolder);
							lRecord.setAttribute("idNotifica", record.getAttribute("idInvioNotifica"));		
							
							Layout.showWaitPopup("Invio per conoscenza in corso: potrebbe richiedere qualche secondo. Attendere…");										
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
							try {
								lGwtRestDataSource.addData(lRecord, new DSCallback() {
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										operationCallback(
												response, 
												record, 
												"idUdFolder", 
												"Invio per conoscenza effettuato con successo", 
												"Si è verificato un errore durante l'invio per conoscenza!", 
												callback
										);	
									}
								});
							} catch(Exception e) {
								Layout.hideWaitPopup();
							}								
						}
					};
					condivisionePopup.editRecordPerModificaNotifica(prepareRecordNotifica(record));
					condivisionePopup.show();
				}
			});
				
		}	  
	}
	
	@Override
	protected void manageDeleteButtonClick(final ListGridRecord record) {

		final Record lRecord = new Record();
		lRecord.setAttribute("flgUdFolder", record.getAttribute("flgUdFolder"));
		lRecord.setAttribute("idUdFolder", record.getAttribute("idUdFolder"));
		lRecord.setAttribute("flgInvioNotifica", record.getAttribute("flgInvioNotifica"));
		lRecord.setAttribute("idInvioNotifica", record.getAttribute("idInvioNotifica"));
		lRecord.setAttribute("desDestinatario", record.getAttribute("desDestinatario"));
		
		String title = "Annullamento assegnazione";
		String flgUdFolder = archivioRecord != null ? archivioRecord.getAttribute("flgUdFolder") : null;
		String estremiUdFolder = archivioRecord != null ? archivioRecord.getAttribute("estremiUdFolder") : null;
		if (estremiUdFolder != null && !"".equals(estremiUdFolder)) {
			if(flgUdFolder != null && "U".equals(flgUdFolder)) {
				title += " del documento " + estremiUdFolder; 
			} else if(flgUdFolder != null && "F".equals(flgUdFolder)) {
				title += " del fascicolo " + estremiUdFolder;
			}
		}
		String desDestinatario = record != null ? record.getAttribute("desDestinatario") : null;
		if (desDestinatario != null && !"".equals(desDestinatario)) {
			title += " a " + desDestinatario;
		}
		
		AnnullamentoInvioNotificaPopup annullamentoInvioNotificaPopup = new AnnullamentoInvioNotificaPopup(lRecord, title) {						
			@Override
			public void onClickOkButton(final DSCallback callback) {

				Record lRecordInput =  new Record(_form.getValues());
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AnnullamentoInvioNotificaDataSource");
				lGwtRestDataSource.addData(lRecordInput, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Layout.addMessage(new MessageBean("Invio annullato", "", MessageType.INFO));
						layout.doSearch();
						callback.execute(response, null, new DSRequest());
					}
				});		
			}
		};	
		annullamentoInvioNotificaPopup.show();         
	}
	
	private Record prepareRecordInvio(Record record) {
		
		Record lRecordInvio = new Record();
		lRecordInvio.setAttribute("flgUdFolder", record.getAttribute(""));		
		RecordList lRecordListAssegnatari = new RecordList();
		Record lRecordAssegnatario = new Record();
		lRecordAssegnatario.setAttribute("codRapido", record.getAttribute("codiceRapido"));
		lRecordAssegnatario.setAttribute("typeNodo", record.getAttribute("tipoDestinatario"));
		if (record.getAttribute("tipoDestinatario") != null && "G".equalsIgnoreCase(record.getAttribute("tipoDestinatario"))){
			lRecordAssegnatario.setAttribute("tipo", "LD");
			lRecordAssegnatario.setAttribute("gruppo", record.getAttribute("idDestinatario"));
		}else {
			lRecordAssegnatario.setAttribute("tipo", "SV;UO");
			lRecordAssegnatario.setAttribute("organigramma", record.getAttribute("tipoDestinatario") + record.getAttribute("idDestinatario"));
			lRecordAssegnatario.setAttribute("idUo", record.getAttribute("idDestinatario"));
		}
		lRecordAssegnatario.setAttribute("descrizione", record.getAttribute("desDestinatario"));
		lRecordListAssegnatari.add(lRecordAssegnatario);
		lRecordInvio.setAttribute("listaAssegnazioni", lRecordListAssegnatari);		
		lRecordInvio.setAttribute("motivoInvio", record.getAttribute("codMotivo"));
		lRecordInvio.setAttribute("messaggioInvio", record.getAttribute("messaggio"));
		lRecordInvio.setAttribute("livelloPriorita", record.getAttribute("livPriorita"));
//		lRecordInvio.setAttribute("tsDecorrenzaAssegnaz", record.getAttribute("tsDecorrenzaInvio"));		
		return lRecordInvio;
	}
	
	private Record prepareRecordNotifica(Record record) {
		
		Record lRecordNotifica = new Record();
		lRecordNotifica.setAttribute("flgUdFolder", record.getAttribute(""));		
		RecordList lRecordListDestInvioCC = new RecordList();
		Record lRecordDestInvioCC = new Record();
		String tipoDestinatario = record.getAttribute("tipoDestinatario");
		if("G".equals(tipoDestinatario)) {
			lRecordDestInvioCC.setAttribute("tipo", "LD");
			lRecordDestInvioCC.setAttribute("typeNodo", tipoDestinatario);			
			lRecordDestInvioCC.setAttribute("codRapido", record.getAttribute("codiceRapido"));
			lRecordDestInvioCC.setAttribute("gruppo", record.getAttribute("idDestinatario"));
		} else {
			lRecordDestInvioCC.setAttribute("tipo", "SV;UO");			
			lRecordDestInvioCC.setAttribute("typeNodo", tipoDestinatario);
			lRecordDestInvioCC.setAttribute("codRapido", record.getAttribute("codiceRapido"));
			lRecordDestInvioCC.setAttribute("idUo", record.getAttribute("idDestinatario"));		
			lRecordDestInvioCC.setAttribute("organigramma", tipoDestinatario + record.getAttribute("idDestinatario"));
		}
		lRecordListDestInvioCC.add(lRecordDestInvioCC);
		lRecordNotifica.setAttribute("listaDestInvioCC", lRecordListDestInvioCC);		
		lRecordNotifica.setAttribute("motivoInvio", record.getAttribute("codMotivo"));
		lRecordNotifica.setAttribute("messaggioInvio", record.getAttribute("messaggio"));
		lRecordNotifica.setAttribute("livelloPriorita", record.getAttribute("livPriorita"));
//		lRecordNotifica.setAttribute("tsDecorrenzaAssegnaz", record.getAttribute("tsDecorrenzaInvio"));
		return lRecordNotifica;
	}
		
	public void operationCallback(DSResponse response, Record record, String pkField, String successMessage, String errorMessage, DSCallback callback) {
		if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];	
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;	
			if(errorMessages != null) { 
				if (errorMessages.get(record.getAttribute(pkField)) != null) {
					errorMsg = (String) errorMessages.get(record.getAttribute(pkField));
				} else {
					errorMsg = errorMessage != null ? errorMessage : "Si è verificato un errore durante l'operazione!";
				}
			}
			Layout.hideWaitPopup();
			if(errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));										
			} else if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				layout.doSearch();
				if(callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		}
	}
	
	
	/**
	 * Metodo per la stampa delle etichette in fase di post-assegnazione UD
	 */
	private void manageStampaEtichettaPostAssegnazione(Record listRecord, Record detailRecord) {
		
		String codSupportoOrig = detailRecord != null && detailRecord.getAttributeAsString("codSupportoOrig") != null ? 
				detailRecord.getAttributeAsString("codSupportoOrig") : null;
		String segnaturaXOrd = listRecord != null && listRecord.getAttributeAsString("segnaturaXOrd") != null ? 
				listRecord.getAttributeAsString("segnaturaXOrd") : null;
		
		if( (codSupportoOrig != null && "C".equals(codSupportoOrig)) &&
			(segnaturaXOrd != null && (segnaturaXOrd.startsWith("1-") || segnaturaXOrd.startsWith("2-"))) &&
			AurigaLayout.getParametroDBAsBoolean("ATTIVA_STAMPA_AUTO_ETICH_POST_ASS") &&
			AurigaLayout.getImpostazioneStampaAsBoolean("stampaEtichettaAutoReg") ){
				
			final Record recordToPrint = new Record();
			recordToPrint.setAttribute("idUd", detailRecord.getAttribute("idUd"));
			recordToPrint.setAttribute("listaAllegati", detailRecord.getAttributeAsRecordList("listaAllegati"));
			recordToPrint.setAttribute("idDoc", detailRecord.getAttribute("idDocPrimario"));
			if(AurigaLayout.getImpostazioneStampaAsBoolean("skipSceltaOpzStampa")){
				
				/**
				 * Viene verificato che sia stata selezionata una stampante in precedenza
				 */
				if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null && 
						!"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
					buildStampaEtichettaAutoPostAss(recordToPrint, null);
				} else {
					PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {

						@Override
						public void execute(String nomeStampante) {
							recordToPrint.setAttribute("nomeStampante", nomeStampante);
							buildStampaEtichettaAutoPostAss(recordToPrint,nomeStampante);
						}
					}, new PrinterScannerCallback() {
						
						@Override
						public void execute(String nomeStampante) {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().protocollazione_detail_stampaEtichettaPostRegSenzaOpzStampa_errorMessage(),
									"", MessageType.ERROR));
						}
					});
				}	
			} else {
				recordToPrint.setAttribute("flgHideBarcode", true);
				StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(recordToPrint);
				stampaEtichettaPopup.show();
			}
		}
	}
	
	/**
	 * Stampa dell'etichetta post-registrazione
	 */
	private void buildStampaEtichettaAutoPostAss(Record record, String nomeStampante) {
		
		Layout.showWaitPopup("Stampa etichetta in corso...");
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttribute("idUd"));
		lRecord.setAttribute("listaAllegati", record.getAttributeAsRecordList("listaAllegati"));
		if(nomeStampante == null || "".equals(nomeStampante)) {
			nomeStampante = AurigaLayout.getImpostazioneStampa("stampanteEtichette");
		}
		lRecord.setAttribute("nomeStampante", nomeStampante);
		lRecord.setAttribute("nroEtichette",  "1");		
		lRecord.setAttribute("flgPrimario", AurigaLayout.getImpostazioneStampaAsBoolean("flgPrimario"));
		lRecord.setAttribute("flgAllegati", AurigaLayout.getImpostazioneStampaAsBoolean("flgAllegati"));
		lRecord.setAttribute("flgRicevutaXMittente", AurigaLayout.getImpostazioneStampaAsBoolean("flgRicevutaXMittente"));
		lRecord.setAttribute("flgHideBarcode", true);
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ETICHETTA_ORIG_COPIA")){			
			lRecord.setAttribute("notazioneCopiaOriginale",  AurigaLayout.getImpostazioneStampa("notazioneCopiaOriginale"));
		}
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PreparaValoriEtichettaDatasource");
		lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				Layout.hideWaitPopup();
				final String nomeStampante = object.getAttribute("nomeStampante");
				final Record[] etichette = object.getAttributeAsRecordArray("etichette");
				final String numCopie = object.getAttribute("nroEtichette");
				StampaEtichettaUtility.stampaEtichetta("", "", nomeStampante, "", etichette, numCopie, new StampaEtichettaCallback() {

					@Override
					public void execute() {
					
					}
				});
			}

			@Override
			public void manageError() {
				Layout.hideWaitPopup();
				Layout.addMessage(new MessageBean("Impossibile stampare l'etichetta", "", MessageType.ERROR));
			}
		});
	}
	
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
	@Override
	protected boolean showModifyButtonField() {
		return true;
	}
    @Override
	protected boolean showDeleteButtonField() {
		return true;
	}
    @Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
    	final String flgAnnullabile  = record.getAttributeAsString("flgAnnullabile");
		return !"0".equals(flgAnnullabile);
	}
    @Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
    	final String flgAnnullabile  = record.getAttributeAsString("flgAnnullabile");
		return !"0".equals(flgAnnullabile);
	}
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/			
}