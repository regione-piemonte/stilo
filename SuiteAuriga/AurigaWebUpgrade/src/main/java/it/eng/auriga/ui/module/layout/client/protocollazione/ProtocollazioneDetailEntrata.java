/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.pgweb.EsibentiItem;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;

/**
 * Maschera di protocollazione in ENTRATA
 * 
 * @author Mattia Zanin
 *
 */

public abstract class ProtocollazioneDetailEntrata extends ProtocollazioneDetailWizard {

	protected ProtocollazioneDetailEntrata instance;
	
	public ProtocollazioneDetailEntrata(final String nomeEntita) {

		super(nomeEntita);

		instance = this;
	}

	@Override
	public String getFlgTipoProv() {
		return "E";
	}

	@Override
	public boolean showIconeNotificheInterop() {
		return true;
	}

	@Override
	public boolean showIconaAnnullata() {
		return true;
	}

	@Override
	public boolean showIconaRichAnnullamento() {
		return true;
	}
	
	public boolean validateDetailSectionEsibenti() {
		if(!isFromCanaleSportello()/* && !isFromCanalePregresso()*/) {
			return true;
		} else {
			return esibentiItem.validate();
		}
	}	
	
	@Override
	protected void createDetailSectionEsibenti() {
		
		if(isModalitaWizard()) {
			
			createEsibentiForm();

			detailSectionEsibenti = new ProtocollazioneDetailSection(I18NUtil.getMessages().protocollazione_detail_esibentiForm_title(),
					true, true, true, esibentiForm) {
				
				@Override
				public boolean validate() {
					return validateDetailSectionEsibenti();
				}
				
				@Override
				public boolean isRequired() {
					return isFromCanaleSportello()/* || isFromCanalePregresso()*/;						
				}
			};
			
		} else {
			super.createDetailSectionEsibenti();
		}
	}
	
	@Override
	public void createEsibentiForm() {
		
		super.createEsibentiForm();
		
		if(isModalitaWizard()) {
			esibentiForm.setTabID("HEADER");
		}
	}	

	@Override
	protected void createEsibentiItem() {
		
		if(isModalitaWizard()) {
			
			esibentiItem = new EsibentiItem() {
				
				@Override
				public void manageOnChanged() {
					if(isFromCanaleSportello() || isFromCanalePregresso()) {
						redrawSections(detailSectionEsibenti);
					}
				}
				
				@Override
				public boolean isObbligatorio() {
					return (isFromCanaleSportello()/* || isFromCanalePregresso()*/);
				}
			};
			esibentiItem.setName("listaEsibenti");
			esibentiItem.setShowTitle(false);
			esibentiItem.setNotReplicable(true);
			esibentiItem.setAttribute("obbligatorio", true);
			esibentiItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					if(isFromCanaleSportello()/* || isFromCanalePregresso()*/) {
						detailSectionEsibenti.setRequired(true);
						esibentiItem.setAttribute("obbligatorio", true);
					} else {
						detailSectionEsibenti.setRequired(false);
						esibentiItem.setAttribute("obbligatorio", false);
					}
					return true;
				}
			});
			
		} else {
			super.createEsibentiItem();
		}
	}

	@Override
	public boolean showOpenDetailSectionMittenti() {
		return true;
	}

	@Override
	public boolean isRequiredDetailSectionMittenti() {
		return true;
	}
	
	@Override
	public boolean validateDetailSectionMittenti() {
		
		if(isModalitaWizard()) {			
			if((isFromCanaleSportello() || isFromCanalePregresso()) && esibentiItem.getFlgAncheMittente() != null && esibentiItem.getFlgAncheMittente()) {
				return true;
			} else {
				return mittentiItem.validate();
			}			
		} else {
			return super.validateDetailSectionMittenti();
		}
	}

	@Override
	protected void createMittentiItem() {
		
		if(isModalitaWizard()) {
			
			mittentiItem = new MittenteProtEntrataItem() {
				
				@Override
				public boolean isProtInModalitaWizard() {
					return true;
				}
				
				@Override
				public String getMezzoTrasmissione() {
					return mezzoTrasmissioneItem.getValueAsString();
				}				
				
				@Override
				public boolean isSupportoOriginaleProtValorizzato() {
					return isSupportoOriginaleValorizzato();					
				}
				
				@Override
				public String getSupportoOriginaleProt() {
					return getSupportoOriginale();					
				}
				
				@Override
				public boolean isAttivoAssegnatarioUnicoCartaceoProt() {
					return isAttivoAssegnatarioUnicoCartaceo();
				}	
				
				@Override
				public boolean isAttivaRestrAssCartaceoProt() {
					return isAttivaRestrizioneAssegnazioneCartaceo();
				}
				
				@Override
				public boolean isAttivoAssegnatarioUnicoProt() {
					return isAttivoAssegnatarioUnico();
				}
				
				@Override
				public int getNroAssegnazioniProt() {
					return getNroAssegnazioni();
				}
	
				@Override
				public void manageOnChanged() {
					if(isRequiredDetailSectionMittenti()) {
						redrawSections(detailSectionMittenti);
					}
				}
				
				@Override
				public void manageChangeFlgAssegnaAlMittente(ChangeEvent event) {
					manageChangeFlgAssegnaAlMittDest(event);						
				}
				
				@Override
				public void manageChangedFlgAssegnaAlMittente(Record canvasRecord) {
					manageChangedFlgAssegnaAlMittDestWizard();
				}
	
				@Override
				public boolean showOpenIndirizzo() {
					if (mezzoTrasmissioneItem.getValue() != null && "L".equals(mezzoTrasmissioneItem.getValueAsString())) {
						return true;
					}
					return false;
				}
	
				@Override
				public boolean getShowItemsIndirizzo() {
//					if (mezzoTrasmissioneItem.getValue() != null && ("PEC".equals(mezzoTrasmissioneItem.getValueAsString()) || "PEO".equals(mezzoTrasmissioneItem.getValueAsString()))) {
//						return false;
//					}
					return true;
				}
			};
			mittentiItem.setName("listaMittenti");
			if (AurigaLayout.getIsAttivaAccessibilita()) {
				mittentiItem.setTabIndex(-1);
				mittentiItem.setCanFocus(false);			
			}
			mittentiItem.setShowTitle(false);
			if (isRequiredDetailSectionMittenti()) {
				mittentiItem.setAttribute("obbligatorio", true);
			}
			
		} else {
		
			mittentiItem = new MittenteProtEntrataItem() {
	
				@Override
				public boolean getShowItemsIndirizzo() {
//					if (isFromEmail()) {
//						return false;
//					}
					return false;
				}
				
				@Override
				public boolean isAttivoAssegnatarioUnicoProt() {
					return isAttivoAssegnatarioUnico();
				}
				
				@Override
				public int getNroAssegnazioniProt() {
					return getNroAssegnazioni();
				}
				
				@Override
				public void manageChangedFlgAssegnaAlMittente(Record canvasRecord) {
					manageChangedFlgAssegnaAlMittDest();					
				}
			};
			mittentiItem.setName("listaMittenti");
			mittentiItem.setShowTitle(false);
			if (AurigaLayout.getIsAttivaAccessibilita()) {
				mittentiItem.setTabIndex(-1);
				mittentiItem.setCanFocus(false);			
			}
			if (isRequiredDetailSectionMittenti()) {
				mittentiItem.setAttribute("obbligatorio", true);
			}
		}
		mittentiItem.setNotReplicable(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_MULTI_MITT_E"));
	}
	
	public boolean showDetailSectionDestinatari() {		
		if(AurigaLayout.getParametroDBAsBoolean("HIDE_DEST_IN_PROT_ENTRATA")) {
			return false;
		}
		return true;
	}

	@Override
	public boolean showOpenDetailSectionDestinatari() {
		return true;
	}

	@Override
	public boolean isRequiredDetailSectionDestinatari() {		
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PREIMP_DEST_UO_PROT_IN_PROT_E");
	}
	
	public Record getDestProtEntrataDefault() {
		Record destProtEntrataDefault = null;
		if (uoProtocollanteSelectItem != null) {
			if (uoProtocollanteSelectItem.getValueAsString() != null && !"".equals(uoProtocollanteSelectItem.getValueAsString())) {
				destProtEntrataDefault = AurigaLayout.getDestProtEntrataDefaultMap().get(uoProtocollanteSelectItem.getValueAsString());				
			} else if (getSelezioneUoProtocollanteValueMap().size() == 1) {				
				destProtEntrataDefault = AurigaLayout.getDestProtEntrataDefaultMap().get(getSelezioneUoProtocollanteValueMap().keySet().toArray(new String[1])[0]);				
			}
		} 
		return destProtEntrataDefault;
	}

	@Override
	protected void createDestinatariItem() {

		if(isModalitaWizard()) {
			
			destinatariItem = new DestinatarioProtEntrataItem() {
				
				@Override
				public boolean isProtInModalitaWizard() {
					return true;
				}
				
				@Override
				public boolean isSupportoOriginaleProtValorizzato() {
					return isSupportoOriginaleValorizzato();					
				}
				
				@Override
				public String getSupportoOriginaleProt() {
					return getSupportoOriginale();					
				}
				
				@Override
				public boolean isAttivoAssegnatarioUnicoCartaceoProt() {
					return isAttivoAssegnatarioUnicoCartaceo();
				}	
				
				@Override
				public boolean isAttivaRestrAssCartaceoProt() {
					return isAttivaRestrizioneAssegnazioneCartaceo();
				}
				
				@Override
				public boolean isAttivoAssegnatarioUnicoProt() {
					return isAttivoAssegnatarioUnico();
				}
				
				@Override
				public int getNroAssegnazioniProt() {
					return getNroAssegnazioni();
				}

//				@Override
//				public String getTitleAggiungiDestInAssegnatariButton() {
//
//					if (!hasAllContenutiDigitali()) {
//						return "Aggiungi come assegnatario";
//					} else {
//						return "Aggiungi tra gli assegnatari";
//					}
//				}
//
//				@Override
//				public boolean getShowAggiungiDestInAssegnatariButton() {
//
//					return true;
//				}
//
//				@Override
//				public boolean getShowAggiungiDestInAssegnatariButtonInCanvas(Record lRecordCanvas) {
//
//					return validateAssegnatario(buildRecordAssegnatarioFromDest(lRecordCanvas));
//				}
//
//				@Override
//				public void manageAssegnaAlDestinatario(Record recordDest) {
//
//					final Record recordAssegnatario = buildRecordAssegnatarioFromDest(recordDest);
//					if (recordAssegnatario != null) {
//						RecordList listaAssegnazioni = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioni");
//						boolean trovato = false;
//						if (listaAssegnazioni != null) {
//							for (int i = 0; i < listaAssegnazioni.getLength(); i++) {
//								if (listaAssegnazioni.get(i).getAttribute("organigramma") != null
//										&& listaAssegnazioni.get(i).getAttribute("organigramma").equals(recordAssegnatario.getAttribute("organigramma"))) {
//									trovato = true;
//									break;
//								}
//							}
//						}
//						if (!trovato) {
//							final AssegnazioneCanvas canvas = assegnazioneItem.getAssegnatarioUnicoCanvas();
//							if (canvas != null) {
//								boolean hasValue = false;
//								for (DynamicForm form : canvas.getForm()) {
//									form.markForRedraw();
//									if (form.hasValue()) {
//										hasValue = true;
//										break;
//									}
//								}
//								if (hasValue) {
//									SC.ask("E' già presente un assegnatario. Si vuole sostituire con il nuovo?", new BooleanCallback() {
//
//										@Override
//										public void execute(Boolean value) {
//
//											if (value) {
//												canvas.editRecord(recordAssegnatario);
//												Layout.addMessage(new MessageBean("Il destinatario è stato aggiunto come assegnatario", "", MessageType.INFO));
//											}
//										}
//									});
//								} else {
//									canvas.editRecord(recordAssegnatario);
//									Layout.addMessage(new MessageBean("Il destinatario è stato aggiunto come assegnatario", "", MessageType.INFO));
//								}
//							} else {
//								AssegnazioneCanvas newCanvas = (AssegnazioneCanvas) assegnazioneItem.onClickNewButton();
//								newCanvas.editRecord(recordAssegnatario);
//								Layout.addMessage(new MessageBean("Il destinatario è stato aggiunto tra gli assegnatari", "", MessageType.INFO));
//							}
//							detailSectionAssegnazione.open();
//						}
//					}
//				};
				
				@Override
				public void manageChangeFlgAssegnaAlDestinatario(ChangeEvent event) {
					manageChangeFlgAssegnaAlMittDest(event);						
				}
				
				@Override
				public void manageChangedFlgAssegnaAlDestinatario(Record canvasRecord) {
					manageChangedFlgAssegnaAlMittDestWizard();
				}

				@Override
				public boolean hasDefaultValue() {
					return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PREIMP_DEST_UO_PROT_IN_PROT_E") && getDestProtEntrataDefault() != null;
				}

				@Override
				public Record getDefaultRecord() {
					Record lRecord = new Record();
					Record destProtEntrataDefault = getDestProtEntrataDefault();
					if(destProtEntrataDefault != null) {
						String idUoSoggetto = destProtEntrataDefault.getAttribute("idUo");
						String descrizione = destProtEntrataDefault.getAttribute("descrizione");
						if(AurigaLayout.getParametroDBAsBoolean("DEST_INT_CON_SELECT")) {
							lRecord.setAttribute("tipoDestinatario", "UP_UOI");
						} else {
							lRecord.setAttribute("tipoDestinatario", "UOI");
						}
						if (idUoSoggetto.startsWith("UO")) {
							lRecord.setAttribute("idUoSoggetto", idUoSoggetto.substring(2));
							lRecord.setAttribute("organigrammaDestinatario", idUoSoggetto);
						} else {
							lRecord.setAttribute("idUoSoggetto", idUoSoggetto);
							lRecord.setAttribute("organigrammaDestinatario", "UO" + idUoSoggetto);
						}
						lRecord.setAttribute("codRapidoDestinatario", descrizione.substring(0, descrizione.indexOf(" - ")));
						lRecord.setAttribute("denominazioneDestinatario", descrizione.substring(descrizione.indexOf(" - ") + 3));
						lRecord.setAttribute("flgAssegnaAlDestinatario", getFlgAssegnaAlDestinatarioDefault());
					}
					return lRecord;
				}
				
				@Override
				public boolean getShowItemsIndirizzo() {
					return false;
				}								
			};
			if (AurigaLayout.getIsAttivaAccessibilita()) {
				destinatariItem.setTabIndex(null);
				destinatariItem.setCanFocus(true);		
			}
			destinatariItem.setName("listaDestinatari");
			destinatariItem.setShowTitle(false);
			if (isRequiredDetailSectionDestinatari()) {
				destinatariItem.setAttribute("obbligatorio", true);
			}
			
		} else {
			
			destinatariItem = new DestinatarioProtEntrataItem() {
	
				@Override
				public boolean hasDefaultValue() {					
					return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PREIMP_DEST_UO_PROT_IN_PROT_E") && getDestProtEntrataDefault() != null;
				}

				@Override
				public Record getDefaultRecord() {
					Record lRecord = new Record();
					Record destProtEntrataDefault = getDestProtEntrataDefault();
					if(destProtEntrataDefault != null) {
						String idUoSoggetto = destProtEntrataDefault.getAttribute("idUo");
						String descrizione = destProtEntrataDefault.getAttribute("descrizione");
						if(AurigaLayout.getParametroDBAsBoolean("DEST_INT_CON_SELECT")) {
							lRecord.setAttribute("tipoDestinatario", "UP_UOI");
						} else {
							lRecord.setAttribute("tipoDestinatario", "UOI");
						}
						if (idUoSoggetto.startsWith("UO")) {
							lRecord.setAttribute("idUoSoggetto", idUoSoggetto.substring(2));
							lRecord.setAttribute("organigrammaDestinatario", idUoSoggetto);
						} else {
							lRecord.setAttribute("idUoSoggetto", idUoSoggetto);
							lRecord.setAttribute("organigrammaDestinatario", "UO" + idUoSoggetto);
						}
						lRecord.setAttribute("codRapidoDestinatario", descrizione.substring(0, descrizione.indexOf(" - ")));
						lRecord.setAttribute("denominazioneDestinatario", descrizione.substring(descrizione.indexOf(" - ") + 3));
						lRecord.setAttribute("flgAssegnaAlDestinatario", getFlgAssegnaAlDestinatarioDefault());
					}
					return lRecord;
				}
				
				@Override
				public boolean getShowItemsIndirizzo() {
					return false;
				}
				
				@Override
				public boolean isAttivoAssegnatarioUnicoProt() {
					return isAttivoAssegnatarioUnico();
				}
				
				@Override
				public int getNroAssegnazioniProt() {
					return getNroAssegnazioni();
				}
				
				@Override
				public void manageChangedFlgAssegnaAlDestinatario(Record canvasRecord) {
					manageChangedFlgAssegnaAlMittDest();					
				}
			};
			destinatariItem.setName("listaDestinatari");
			destinatariItem.setShowTitle(false);
			if (isRequiredDetailSectionDestinatari()) {
				destinatariItem.setAttribute("obbligatorio", true);
			}
			
		}
	}

	@Override
	public boolean showDetailSectionDatiRicezione() {
		return true;
	}		
	
	@Override
	public boolean isRequiredMezzoTrasmissioneInDatiRicezione() {
		return AurigaLayout.getParametroDBAsBoolean("OBBL_MEZZO_TRASM_E");
	}

	@Override
	public boolean showDetailSectionRegEmergenza() {
		return true;
	}
	
	@Override
	public boolean showDetailSectionDestinatariAfterAssegnazione() {
		if(isModalitaWizard() && !isRegistroFattureDetail()) {
			return true;						
		}
		return false;
	}

	@Override
	public boolean showConfermaAssegnazioneForm() {
		return true;
	}
	
	@Override
	public boolean isRequiredDetailSectionAssegnazione() {		
		return AurigaLayout.getParametroDBAsBoolean("OBBL_ASSEGNAZIONE_PROT_E");
	}

	@Override
	public boolean isRequiredDetailSectionClassificazioneFascicolazione() {
		return AurigaLayout.getParametroDBAsBoolean("OBBL_CLASSIF_PROT_E");
	}
	
	@Override
	public void editNewRecord(Map initialValues) {

		super.editNewRecord(initialValues);

		if(isModalitaWizard()) {
			showHideSections();
		}
	}
	
	@Override
	public void editRecord(Record record) {
	
		super.editRecord(record);
	
		if(isModalitaWizard()) {
			showHideSections();
		}
		
	}
	
	@Override
	public void showHideSections() {
		
		super.showHideSections();
		
		if (detailSectionEsibenti != null) {
			if(isFromCanaleSportello() || isFromCanalePregresso()) {
				detailSectionEsibenti.show();
			} else {
				detailSectionEsibenti.hide();
			}
		}
		if (detailSectionMittenti != null) {
			if(isFromCanaleSportello() || isFromCanalePregresso()) {
				if(esibentiItem.getFlgAncheMittente() == null || !esibentiItem.getFlgAncheMittente()) {
					detailSectionMittenti.show();
				} else {
					detailSectionMittenti.hide();
				}
			} else {
				detailSectionMittenti.show();
			}
		}
		if (detailSectionDatiRicezione != null) {	
			if (isFromCanaleSportello() || isFromCanalePregresso()) {
				detailSectionDatiRicezione.hide();
			} else {
				detailSectionDatiRicezione.show();
			}
		}

	}
	
	@Override
	public boolean isAltraNumerazione() {
		if(isProtocolloGeneraleConRepertorio()) {
			return true;
		}
		return super.isAltraNumerazione();
	}

	@Override
	public String getCategoriaAltraNumerazione() {
		if(isProtocolloGeneraleConRepertorio()) {
			return "R";
		}
		return super.getCategoriaAltraNumerazione();
	}

	@Override
	public String getIconAltraNumerazione() {
		if(isProtocolloGeneraleConRepertorio()) {
			return "protocollazione/repertorio.png";
		}
		return super.getIconAltraNumerazione();
	}

	@Override
	public String getTitleAltraNumerazione() {
		if(isProtocolloGeneraleConRepertorio()) {
			return "Repertorio";
		}
		return super.getTitleAltraNumerazione();
	}
	
	@Override
	public Map<String, Object> getNuovaProtInitialValues() {
		Map<String, Object> initialValues = super.getNuovaProtInitialValues();
		if(initialValues == null) {
			initialValues = new HashMap<String, Object>();
		}
		if(isProtocolloGeneraleConRepertorio()) {
			initialValues.put("repertorio", (String) vm.getValues().get("siglaNumerazioneSecondaria"));								
		}
		return initialValues.size() > 0 ? initialValues : null;
	}

	@Override
	public void nuovaProtComeCopia(Map values, Map attributiDinamiciDoc, final boolean flgNoEsibente, final boolean flgNoMittenti, final boolean flgNoDestinatari,
			final boolean flgNoAltriAssegnatari, final boolean flgNoOggetto, final boolean flgNoPrimario, final boolean flgNoAllegati,
			final boolean flgNoFileAllegati, final boolean flgNoDocumentiCollegati, final boolean flgNoFascicolazione, final boolean flgNoAttributiCustom, ServiceCallback<Record> callback) {
		if(isProtocolloGeneraleConRepertorio()) {
			values.put("repertorio", (String) values.get("siglaNumerazioneSecondaria"));								
		}
		super.nuovaProtComeCopia(values, attributiDinamiciDoc, flgNoEsibente, flgNoMittenti, flgNoDestinatari, flgNoAltriAssegnatari, flgNoOggetto, flgNoPrimario, flgNoAllegati,
				flgNoFileAllegati, flgNoDocumentiCollegati, flgNoFascicolazione, flgNoAttributiCustom, callback);
	}

}