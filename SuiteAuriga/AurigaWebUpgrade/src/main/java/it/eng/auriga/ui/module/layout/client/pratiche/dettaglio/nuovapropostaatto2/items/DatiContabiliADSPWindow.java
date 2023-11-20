/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.ErroreProposteConcorrentiPopUp;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DatiContabiliADSPWindow extends ModalWindow {
	
	protected DatiContabiliADSPWindow window;
	protected DatiContabiliADSPDetail detail;	
	protected ListaDatiContabiliADSPItem gridItem;
	protected ToolStrip detailToolStrip;
	
	private static final String STATO_SISTEMA_CONTABILE_ALLINEATO = "allineato";
	private static final String STATO_SISTEMA_CONTABILE_NON_ALLINEATO = "non_allineato";
	private static final String STATO_SISTEMA_CONTABILE_DA_ALLINEARE = "da_allineare";
	
	private static final String OPERAZIONE_SISTEMA_CONTABILE_INSERT = "insert";
	private static final String OPERAZIONE_SISTEMA_CONTABILE_UPDATE = "update";
	private static final String OPERAZIONE_SISTEMA_CONTABILE_DELETE = "delete";
	
	private final int ALT_POPUP_ERR_MASS = 375;
	private final int LARG_POPUP_ERR_MASS = 620;
	
	public DatiContabiliADSPWindow(ListaDatiContabiliADSPItem gridItem, String nomeEntita, final Record record, boolean canEdit, 
			final String cdrUOCompetente, final Boolean flgSenzaImpegniCont, final String idUd) {
		
		super(nomeEntita, false);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(540);
		setWidth(1000);
		setOverflow(Overflow.AUTO);    			
				
		window = this;
		this.gridItem = gridItem;
		
		final boolean isNew = (record == null);
						
		if(record != null) {
			if(canEdit) {
				setTitle(I18NUtil.getMessages().editDetail_titlePrefix() + " " + getTipoEstremiRecord(record));				
			} else {
				setTitle(I18NUtil.getMessages().viewDetail_titlePrefix() + " " + getTipoEstremiRecord(record));	
			}			
		} else {
			setTitle("Compila dati contabili");
		}
		
		detail = new DatiContabiliADSPDetail(gridItem, nomeEntita, record, canEdit, cdrUOCompetente);		
		detail.setHeight100();
		detail.setWidth100();
		
		if(canEdit) {
			
			final DetailToolStripButton saveButton = new DetailToolStripButton(I18NUtil.getMessages().formChooser_ok(), "ok.png");
			saveButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					saveButton.focusAfterGroup();
					final Record lRecord = detail.getRecordToSave();								
					if(detail.validate()) {						
						String[] codiciCIG = detail.codiceCIGItem.getValueMapAsArray();
						String[] codiciCUP = detail.codiceCUPItem.getValueMapAsArray();						
						String messaggioCIGCUPMancanti = null;						
						if((checkCodiciValidi(codiciCIG) && (lRecord.getAttribute("codiceCIG")==null || "".equals(lRecord.getAttribute("codiceCIG")))) 
								&&
					       (checkCodiciValidi(codiciCUP) && (lRecord.getAttribute("codiceCUP")==null || "".equals(lRecord.getAttribute("codiceCUP"))))	) {
							messaggioCIGCUPMancanti = "Presenti CIG e CUP nei dati scheda. Sei sicuro di voler lasciare vuoti i campi CIG e CUP sul movimento ?";
						}
						else if(checkCodiciValidi(codiciCIG) && (lRecord.getAttribute("codiceCIG")==null || "".equals(lRecord.getAttribute("codiceCIG")))) {
							messaggioCIGCUPMancanti = "Presenti CIG nei dati scheda. Sei sicuro di voler lasciare vuoto il CIG sul movimento ?";
						}
						else if(checkCodiciValidi(codiciCUP) && (lRecord.getAttribute("codiceCUP")==null || "".equals(lRecord.getAttribute("codiceCUP")))) {
							messaggioCIGCUPMancanti = "Presenti CUP nei dati scheda. Sei sicuro di voler lasciare vuoto il CUP sul movimento ?";
						}						
						if(messaggioCIGCUPMancanti!=null) {
							SC.ask(messaggioCIGCUPMancanti , new BooleanCallback() {					
								@Override
								public void execute(Boolean value) {				
									if(value) {
										manageOnClickOK(record, cdrUOCompetente, isNew, lRecord, flgSenzaImpegniCont, idUd);
									}
								}
							});
						}else {
							manageOnClickOK(record, cdrUOCompetente, isNew, lRecord, flgSenzaImpegniCont, idUd);
						}												
					} else {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
					}
				}
			});
			
			DetailToolStripButton annullaButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
			annullaButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {				
					window.manageOnCloseClick();
				}
			});
			
			ToolStrip detailToolStrip = new ToolStrip();
			detailToolStrip.setWidth100();
			detailToolStrip.setHeight(30);
			detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
			detailToolStrip.addFill(); // push all buttons to the right		
			detailToolStrip.addButton(saveButton);
			detailToolStrip.addButton(annullaButton);
			
			VLayout detailLayout = new VLayout();  
			detailLayout.setOverflow(Overflow.HIDDEN);		
			detailLayout.setHeight100();
			detailLayout.setWidth100();		
			detailLayout.setMembers(detail, detailToolStrip);	
			
			setBody(detailLayout);
		} else {
			setBody(detail);
		}
		
		setIcon("blank.png");		
	}
	
	protected boolean checkCodiciValidi(String[] codici) {
		if(codici!=null) {
			for(String codice : codici) {
				if(codice!=null && !"".equalsIgnoreCase(codice)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param record
	 * @param oldRecord
	 * @return
	 * @throws NumberFormatException
	 */
	public String getImportoDaVerificare(final Record record, Record oldRecord)
			throws NumberFormatException {
		String importoDaVerificare = record.getAttribute("importo");
		
		if(gridItem.isDisattivaIntegrazioneSistemaContabile()) {
			return importoDaVerificare;
		}
		
		if(oldRecord!=null) {
			String ultimoImportoAllineato = oldRecord.getAttribute("ultimoImportoAllineato");
			String importo = record.getAttribute("importo");
			
			String ultimoKeyCapitoloAllineato = oldRecord.getAttribute("ultimoKeyCapitoloAllineato");
			String keyCapitolo = record.getAttribute("keyCapitolo");
			
			if(ultimoImportoAllineato!=null && !"".equalsIgnoreCase(ultimoImportoAllineato)) {
				if(keyCapitolo.equals(ultimoKeyCapitoloAllineato)) {
					/*se è stato cambiato l'importo di un movimento rispetto all utimo allineato sul sistema contabile, verifico la disponibilita solo per la differenza*/
					if(!importo.equals(ultimoImportoAllineato)) {
						Float importoFloat = Float.valueOf(importo.replace(".", "").replace(",", "."));
						Float oldImportoFloat = Float.valueOf(ultimoImportoAllineato.replace(".", "").replace(",", "."));
						
						if(importoFloat>oldImportoFloat) {
							importoDaVerificare = String.valueOf(importoFloat - oldImportoFloat);
						}else {
							importoDaVerificare = null;
						}
					}else {
						importoDaVerificare = null;
					}
				}
			}
			
		}
		return importoDaVerificare;
	}
	
	/*Qui sono nell'azione di modifica, in base agli stati di partenza dei movimenti contabili setto i nuovi stati e l'operazione di update*/
	protected void manageStatoOperazioneSistemaContabile(Record lRecord) {
		String operazioneSistemaContabile = lRecord.getAttributeAsString("operazioneSistemaContabile");
		String statoSistemaContabile = lRecord.getAttributeAsString("statoSistemaContabile");		
		if(statoSistemaContabile != null) {
			if(statoSistemaContabile.equals(STATO_SISTEMA_CONTABILE_ALLINEATO)) {
				if(operazioneSistemaContabile != null) {
					if(operazioneSistemaContabile.equals(OPERAZIONE_SISTEMA_CONTABILE_INSERT) || operazioneSistemaContabile.equals(OPERAZIONE_SISTEMA_CONTABILE_UPDATE)) {
						lRecord.setAttribute("statoSistemaContabile", STATO_SISTEMA_CONTABILE_DA_ALLINEARE);
						lRecord.setAttribute("operazioneSistemaContabile", OPERAZIONE_SISTEMA_CONTABILE_UPDATE);
					}
				}
			} else if(statoSistemaContabile.equals(STATO_SISTEMA_CONTABILE_NON_ALLINEATO)) {
				if(operazioneSistemaContabile != null) {
					if(operazioneSistemaContabile.equals(OPERAZIONE_SISTEMA_CONTABILE_INSERT)) {
						lRecord.setAttribute("statoSistemaContabile", STATO_SISTEMA_CONTABILE_DA_ALLINEARE);
						lRecord.setAttribute("operazioneSistemaContabile", OPERAZIONE_SISTEMA_CONTABILE_INSERT);
					} else if(operazioneSistemaContabile.equals(OPERAZIONE_SISTEMA_CONTABILE_UPDATE)) {
						lRecord.setAttribute("statoSistemaContabile", STATO_SISTEMA_CONTABILE_DA_ALLINEARE);
						lRecord.setAttribute("operazioneSistemaContabile", OPERAZIONE_SISTEMA_CONTABILE_UPDATE);
					}
				}
			}
		}
	}

	public void saveData(Record record) {
		
	}
	
	/**
	 * @param isNew
	 * @param lRecord
	 */
	public void manageSaveData(final boolean isNew, final Record lRecord) {
		saveData(lRecord);		
		if(isNew) {
			AfterSaveDatiSpesaDialogBox afterSaveDatiSpesaDialogBox = new AfterSaveDatiSpesaDialogBox("Vuoi inserire un nuovo dettaglio di spesa?") {

				@Override
				public void onClickButton(String scelta) {
					if(scelta != null && scelta.equals("SI")) {						
						HashMap<String, Object> initialValues = new HashMap<String, Object>();
						initialValues.put("annoEsercizio", DateTimeFormat.getFormat("yyyy").format(new Date()));
						detail.editNewRecord(initialValues);
					} else if(scelta != null && scelta.equals("CC")) {
						// per la copia lascio i dati così come sono invariati a maschera, perchè se richiamo l'editRecord mi perdo il valore di alcuni campi
//						detail.editRecord(new Record(detail.getValuesManager().getValues()));
					} else {
						window.manageOnCloseClick();
					}
				}
			};	
			afterSaveDatiSpesaDialogBox.show();
		} else {
			window.manageOnCloseClick();
		}
	}
	
	public String getTipoEstremiRecord(Record record) {
		String estremi = "";
		if(record.getAttribute("capitolo") != null && !"".equals(record.getAttribute("capitolo"))) {
			estremi += "capitolo " + record.getAttribute("capitolo") + " ";
		}
		if(record.getAttribute("annoEsercizio") != null && !"".equals(record.getAttribute("annoEsercizio"))) {
			estremi += "esercizio " + record.getAttribute("annoEsercizio") + " ";
		}	
		return estremi;
	}

	/**
	 * @param oldRecordList
	 * @param cdrUOCompetente
	 * @param isNew
	 * @param recordToSave
	 * @throws NumberFormatException
	 */
	public void manageOnClickOK(final Record oldRecordList, final String cdrUOCompetente, final boolean isNew,
			final Record recordToSave, final Boolean flgSenzaImpegniCont, final String idUd) throws NumberFormatException {
		Layout.showWaitPopup("Validazione in corso...");
		
		/*Se il capitolo non è stato selezionato dalla lista faccio una ricerca invocando il WS per recuperare keyCapitolo*/
		if((recordToSave.getAttributeAsString("keyCapitolo")==null || "".equalsIgnoreCase(recordToSave.getAttributeAsString("keyCapitolo")))
				&& (recordToSave.getAttributeAsString("capitolo")!=null && !"".equalsIgnoreCase(recordToSave.getAttributeAsString("capitolo")))
				&& (recordToSave.getAttributeAsString("conto")!=null && !"".equalsIgnoreCase(recordToSave.getAttributeAsString("conto")))
				) {
			
			Record recordRicercaCapitolo = new Record();
			recordRicercaCapitolo.setAttribute("capitolo1", recordToSave.getAttributeAsString("capitolo"));
			recordRicercaCapitolo.setAttribute("capitolo2", recordToSave.getAttributeAsString("conto"));
			recordRicercaCapitolo.setAttribute("annoEsercizio", recordToSave.getAttributeAsString("annoEsercizio"));
			recordRicercaCapitolo.setAttribute("flgEntrataUscita", recordToSave.getAttributeAsString("flgEntrataUscita"));
			
			/*Verifico la presenza dei capitoli*/
			final GWTRestDataSource lContabilitaADSPDataSource = new GWTRestDataSource("ContabilitaADSPDataSource");
			lContabilitaADSPDataSource.extraparam.put("cdrUOCompetente", cdrUOCompetente);
			lContabilitaADSPDataSource.performCustomOperation("getCapitoli", recordRicercaCapitolo, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					/*Se il WS getCapitoli non trova nessun capitolo restituisce errore*/
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						Record[] listaCapitoli = record.getAttributeAsRecordArray("listaCapitoliADSP");
						
						/*Inserendo i campi capitolo e conto (dati obbligatori del validate) posso trovare necessariamente un solo capitolo*/
						if(listaCapitoli.length==1) {
							recordToSave.setAttribute("keyCapitolo", listaCapitoli[0].getAttribute("keyCapitolo"));
							
							/**
							 * Quando all'OK su una movimento contabile viene fatta la verifica della disponibilità sul capitolo+conto, se è un movimento che si sta modificando e per cui NON sono stati variati 
							 * capitolo+conto dobbiamo verificare che sia disponibile solo l'aumento di importo (se era 100 e diventa 120 dobbiamo verificare che ci sia 20). Se l'importo si riduce rispetto a 
							 * prima NON dobbiamo fare controllo. Se invece capitolo e/o conto sono cambiati dobbiamo controllare disponibilità dell'importo totale
							 */
							final String importoDaVerificare = getImportoDaVerificare(recordToSave, record);
								
							if(importoDaVerificare!=null){
							
								/*Verifico l'importo*/
								final GWTRestDataSource lContabilitaADSPDataSource = new GWTRestDataSource("ContabilitaADSPDataSource");
								lContabilitaADSPDataSource.extraparam.put("cdrUOCompetente", cdrUOCompetente);
								lContabilitaADSPDataSource.extraparam.put("importoDaVerificare", importoDaVerificare);
								lContabilitaADSPDataSource.extraparam.put("flgSenzaImpegniCont", flgSenzaImpegniCont!=null && flgSenzaImpegniCont==true ? "true" : "false");
								lContabilitaADSPDataSource.extraparam.put("idUd", idUd);
								
								final HashMap<String,String> mappaKeyCapitoliSalvata = gridItem.getMappaKeyCapitoli();
								sottraiImportoDaMappaKeyCapitoli(oldRecordList, importoDaVerificare,
										mappaKeyCapitoliSalvata);
								
								recordToSave.setAttribute("mappaKeyCapitoli", new Record(mappaKeyCapitoliSalvata));
								
								lContabilitaADSPDataSource.performCustomOperation("verificaDisponibilitaImporto", recordToSave, new DSCallback() {							
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
											final Record record = response.getData()[0];
											if(record.getAttributeAsBoolean("inError")) {
												
												recordToSave.setAttribute("disponibilitaImporto", "non_disponibile");
												
												ripristinaImportoInMappaKeyCapitoli(oldRecordList, importoDaVerificare,
														mappaKeyCapitoliSalvata);		
												
												SC.warn(record.getAttributeAsString("defaultMessage"), new BooleanCallback() {
													
													@Override
													public void execute(Boolean value) {
														if(gridItem.isDisattivaIntegrazioneSistemaContabile()) {
															Record datiContabiliADSPXmlBean = record.getAttributeAsRecord("resultBean");														
															if(datiContabiliADSPXmlBean != null &&  datiContabiliADSPXmlBean.getAttributeAsRecordList("listaProposteConcorrenti")!=null &&
																datiContabiliADSPXmlBean.getAttributeAsRecordList("listaProposteConcorrenti").getLength() > 0) {
																	showListaProposteConcorrenti(datiContabiliADSPXmlBean.getAttributeAsRecordList("listaProposteConcorrenti"),
																			isNew, recordToSave, record);
															}else {
																manageInserimentoSenzaDisponibilita(isNew, recordToSave, record);
																}
														}
													}
												});
												
											}else {
												manageStatoOperazioneSistemaContabile(recordToSave);
												
												/*????? forse superfluo, perchè lo fa nel setData*/
												aggiornaMappaKeyCapitoli(record);
												
												/*pulisco la lista dei capitoli*/
												recordToSave.setAttribute("listaCapitoliADSP", "");
												
												if(!flgSenzaImpegniCont) {
													recordToSave.setAttribute("disponibilitaImporto", "disponibile");
												}											
												
												manageSaveData(isNew, recordToSave);																
											}
										} 				
									}
								}, new DSRequest());
							}else {
								manageStatoOperazioneSistemaContabile(recordToSave);
								
								/*pulisco la lista dei capitoli*/
								recordToSave.setAttribute("listaCapitoliADSP", "");
								manageSaveData(isNew, recordToSave);	
							}
						} else {
							// Non dovrebbe mai succedere ma per sicurezza aggiungo messaggi di errore
							if(listaCapitoli.length==0) {
								Layout.addMessage(new MessageBean("Non è stato trovato nessun capitolo relativo ai campi capitolo e conto indicati a maschera", "", MessageType.ERROR));								
							} else {
								Layout.addMessage(new MessageBean("Sono stati trovati più capitoli relativi ai campi capitolo e conto indicati a maschera", "", MessageType.ERROR));								
							}
						}
					} 				
				}

			}, new DSRequest());
			
		}else {
			/*Verifico l'importo*/
			
			/**
			 * Quando all'OK su una movimento contabile viene fatta la verifica della disponibilità sul capitolo+conto, se è un movimento che si sta modificando e per cui NON sono stati variati 
			 * capitolo+conto dobbiamo verificare che sia disponibile solo l'aumento di importo (se era 100 e diventa 120 dobbiamo verificare che ci sia 20). Se l'importo si riduce rispetto a 
			 * prima NON dobbiamo fare controllo. Se invece capitolo e/o conto sono cambiati dobbiamo controllare disponibilità dell'importo totale
			 */
			final String importoDaVerificare = getImportoDaVerificare(recordToSave, oldRecordList);
			
			if(importoDaVerificare!=null){
				final GWTRestDataSource lContabilitaADSPDataSource = new GWTRestDataSource("ContabilitaADSPDataSource");
				lContabilitaADSPDataSource.extraparam.put("cdrUOCompetente", cdrUOCompetente);
				lContabilitaADSPDataSource.extraparam.put("importoDaVerificare", importoDaVerificare);
				lContabilitaADSPDataSource.extraparam.put("flgSenzaImpegniCont", flgSenzaImpegniCont!=null && flgSenzaImpegniCont==true ? "true" : "false");
				lContabilitaADSPDataSource.extraparam.put("idUd", idUd);
				
				final HashMap<String,String> mappaKeyCapitoliSalvata = gridItem.getMappaKeyCapitoli();
				sottraiImportoDaMappaKeyCapitoli(oldRecordList, importoDaVerificare, mappaKeyCapitoliSalvata);
				
				recordToSave.setAttribute("mappaKeyCapitoli", new Record(mappaKeyCapitoliSalvata));
				
				lContabilitaADSPDataSource.performCustomOperation("verificaDisponibilitaImporto", recordToSave, new DSCallback() {							
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
							final Record record = response.getData()[0];
							if(record.getAttributeAsBoolean("inError")) {
								
								recordToSave.setAttribute("disponibilitaImporto", "non_disponibile");
								
								ripristinaImportoInMappaKeyCapitoli(oldRecordList, importoDaVerificare,
										mappaKeyCapitoliSalvata);								
								
								SC.warn(record.getAttributeAsString("defaultMessage"), new BooleanCallback() {
									
									@Override
									public void execute(Boolean value) {
										if(gridItem.isDisattivaIntegrazioneSistemaContabile()) {
											Record datiContabiliADSPXmlBean = record.getAttributeAsRecord("resultBean");														
											if(datiContabiliADSPXmlBean != null &&  datiContabiliADSPXmlBean.getAttributeAsRecordList("listaProposteConcorrenti")!=null &&
												datiContabiliADSPXmlBean.getAttributeAsRecordList("listaProposteConcorrenti").getLength() > 0) {
													showListaProposteConcorrenti(datiContabiliADSPXmlBean.getAttributeAsRecordList("listaProposteConcorrenti"),
															isNew, recordToSave, record);
											}else {
												manageInserimentoSenzaDisponibilita(isNew, recordToSave, record);
											}	
										}
									}
								});
							}else {
								manageStatoOperazioneSistemaContabile(recordToSave);
								
								/*????? forse superfluo, perchè lo fa nel setData*/
								aggiornaMappaKeyCapitoli(record);
								
								/*pulisco la lista dei capitoli*/
								recordToSave.setAttribute("listaCapitoliADSP", "");
								
								if(!flgSenzaImpegniCont) {
									recordToSave.setAttribute("disponibilitaImporto", "disponibile");
								}
															
								manageSaveData(isNew, recordToSave);									
							}
						} 				
					}
				}, new DSRequest());								
			}else {
				manageStatoOperazioneSistemaContabile(recordToSave);
				
				/*pulisco la lista dei capitoli*/
				recordToSave.setAttribute("listaCapitoliADSP", "");
				manageSaveData(isNew, recordToSave);	
			}
		}
		
		Layout.hideWaitPopup();
	}

	/**
	 * @param record
	 * @throws NumberFormatException
	 */
	public void aggiornaMappaKeyCapitoli(Record record) throws NumberFormatException {
		Record datiContabiliADSPXmlBean = record.getAttributeAsRecord("resultBean");
		if(datiContabiliADSPXmlBean!=null) {
			
			HashMap<String, String> mappaKeyCapitoli = gridItem.getMappaKeyCapitoli();
			
			String importo = datiContabiliADSPXmlBean.getAttributeAsString("importo").replace(".", "").replace(",", ".");
			
			if(mappaKeyCapitoli.get(datiContabiliADSPXmlBean.getAttributeAsString("keyCapitolo"))!=null 
					&& !"".equalsIgnoreCase(mappaKeyCapitoli.
							get(datiContabiliADSPXmlBean.getAttributeAsString("keyCapitolo")))) {
				String importoPresente = mappaKeyCapitoli.get(datiContabiliADSPXmlBean.getAttributeAsString("keyCapitolo"));
				
				String importoAggiornato = String.valueOf(Double.valueOf(importoPresente) 
						+ Double.valueOf(importo));
				
				mappaKeyCapitoli.put(datiContabiliADSPXmlBean.getAttributeAsString("keyCapitolo"), importoAggiornato);
				
			}else {
				mappaKeyCapitoli.put(datiContabiliADSPXmlBean.getAttributeAsString("keyCapitolo"), importo);
			}
		}
	}

	/**
	 * @param oldRecordList
	 * @param importoDaVerificare
	 * @param mappaKeyCapitoliSalvata
	 * @throws NumberFormatException
	 */
	public void sottraiImportoDaMappaKeyCapitoli(final Record oldRecordList, String importoDaVerificare,
			HashMap<String, String> mappaKeyCapitoliSalvata) throws NumberFormatException {
		if(oldRecordList!=null) {
			if(mappaKeyCapitoliSalvata.get(oldRecordList.getAttributeAsString("keyCapitolo"))!=null 
					&& !"".equalsIgnoreCase(mappaKeyCapitoliSalvata.
							get(oldRecordList.getAttributeAsString("keyCapitolo")))) {
				String importoPresente = mappaKeyCapitoliSalvata.get(oldRecordList.getAttributeAsString("keyCapitolo"));
				
				String importoAggiornato = String.valueOf(Double.valueOf(importoPresente) 
						- Double.valueOf(oldRecordList.getAttributeAsString("importo").replace(".", "").replace(",", ".")));
				
				mappaKeyCapitoliSalvata.put(oldRecordList.getAttributeAsString("keyCapitolo"), importoAggiornato);
				
			}
		}
	}

	/**
	 * @param oldRecordList
	 * @param importoDaVerificare
	 * @param mappaKeyCapitoliSalvata
	 * @throws NumberFormatException
	 */
	public void ripristinaImportoInMappaKeyCapitoli(final Record oldRecordList, final String importoDaVerificare,
			final HashMap<String, String> mappaKeyCapitoliSalvata) throws NumberFormatException {
		
		if(oldRecordList!=null) {
			if(mappaKeyCapitoliSalvata.get(oldRecordList.getAttributeAsString("keyCapitolo"))!=null 
					&& !"".equalsIgnoreCase(mappaKeyCapitoliSalvata.
							get(oldRecordList.getAttributeAsString("keyCapitolo")))) {
				String importoPresente = mappaKeyCapitoliSalvata.get(oldRecordList.getAttributeAsString("keyCapitolo"));
				
				String importoAggiornato = String.valueOf(Double.valueOf(importoPresente) 
						+ Double.valueOf(importoDaVerificare));
				
				mappaKeyCapitoliSalvata.put(oldRecordList.getAttributeAsString("keyCapitolo"), importoAggiornato);				
			}
		}
	}

	/**
	 * @param record
	 */
	public void showListaProposteConcorrenti(final RecordList listaProposteConcorrenti, final boolean isNew, final Record recordToSave,
			final Record record) {
		RecordList listaErrori = new RecordList();
		for (int i = 0; i < listaProposteConcorrenti.getLength(); i++) {
			Record propostaConcorrente = listaProposteConcorrenti.get(i);
			
			Record recordErrore = new Record();
			recordErrore.setAttribute("estremiProposta", propostaConcorrente.getAttribute("estremiProposta"));
			recordErrore.setAttribute("oggettoProposta", propostaConcorrente.getAttribute("oggettoProposta"));
			recordErrore.setAttribute("importoProposta", propostaConcorrente.getAttribute("importoProposta"));
			recordErrore.setAttribute("capitoloProposta", propostaConcorrente.getAttribute("capitoloProposta"));
			recordErrore.setAttribute("contoProposta", propostaConcorrente.getAttribute("contoProposta"));
			listaErrori.add(recordErrore);

		}
		ErroreProposteConcorrentiPopUp errorePopup = new ErroreProposteConcorrentiPopUp(nomeEntita, listaErrori,
				LARG_POPUP_ERR_MASS, ALT_POPUP_ERR_MASS, "Atti in iter che insistono sullo stesso capitolo/conto") {
			
			@Override
			public void manageOnClick() {
				manageInserimentoSenzaDisponibilita(isNew, recordToSave, record);
			};
		};
		errorePopup.show();
	}

	/**
	 * @param isNew
	 * @param recordToSave
	 * @param record
	 */
	public void manageInserimentoSenzaDisponibilita(final boolean isNew, final Record recordToSave,
			final Record record) {
		
		String modCtrlDispCapitoliRda = AurigaLayout.getParametroDB("MOD_CTRL_DISP_CAPITOLI_IN_RDA");
		if(modCtrlDispCapitoliRda!=null && !"".equalsIgnoreCase(modCtrlDispCapitoliRda) && modCtrlDispCapitoliRda.equalsIgnoreCase("WARNING")) {
			SC.ask("Vuoi comunque procedere all'inserimento/aggiornamento del movimento contabile anche se non risulta esserci sufficiente disponibilità ? " , new BooleanCallback() {					
				@Override
				public void execute(Boolean value) {				
					if(value) {
						manageStatoOperazioneSistemaContabile(recordToSave);																		
						/*????? forse superfluo, perchè lo fa nel setData*/
						aggiornaMappaKeyCapitoli(record);														
						/*pulisco la lista dei capitoli*/
						recordToSave.setAttribute("listaCapitoliADSP", "");
						manageSaveData(isNew, recordToSave);
					}
				}
			});
		}
	}
	
}
