/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;

/**
 * Maschera di protocollazione in USCITA
 * 
 * @author Mattia Zanin
 *
 */

public abstract class ProtocollazioneDetailUscita extends ProtocollazioneDetailWizard {

	protected ProtocollazioneDetailUscita instance;

	// Indica se sono state effettuate delle modifiche ai file (primario e allegati) rispetto a quelle già trasmesse via PEC (nel caso di invio mail
	// interoperabile)
	protected boolean flgModificatiFileInvioPEC = false;

	public ProtocollazioneDetailUscita(String nomeEntita) {

		super(nomeEntita);

		instance = this;
	}

	@Override
	public String getFlgTipoProv() {
		return "U";
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

	@Override
	public boolean showOpenDetailSectionMittenti() {
		return true;
	}

	@Override
	public boolean isRequiredDetailSectionMittenti() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_OBBLIG_MITT_IN_USCITA");
	}

	@Override
	protected void createMittentiItem() {

		mittentiItem = new MittenteProtUscitaItem() {
			
			@Override
			public boolean isProtInModalitaWizard() {
				return isModalitaWizard();
			}
			
			@Override
			public boolean isSupportoOriginaleProtValorizzato() {
				if(isModalitaWizard()) {
					return isSupportoOriginaleValorizzato();
				}
				return false;
			}
			
			@Override
			public String getSupportoOriginaleProt() {
				if(isModalitaWizard()) {
					return getSupportoOriginale();
				}
				return null;
			}

			@Override
			public boolean isAttivoAssegnatarioUnicoCartaceoProt() {
				return isModalitaWizard() && isAttivoAssegnatarioUnicoCartaceo();
			}	
			
			@Override
			public boolean isAttivaRestrAssCartaceoProt() {
				return isModalitaWizard() && isAttivaRestrizioneAssegnazioneCartaceo();
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
				if(isModalitaWizard() && isRequiredDetailSectionMittenti()) {
					redrawSections(detailSectionMittenti);
				}
			}
			
			@Override
			public void manageChangeFlgAssegnaAlMittente(ChangeEvent event) {
				if(isModalitaWizard()) {
					manageChangeFlgAssegnaAlMittDest(event);	
				}
			}
			
			@Override
			public void manageChangedFlgAssegnaAlMittente(Record canvasRecord) {
				if(isModalitaWizard()) {
					manageChangedFlgAssegnaAlMittDestWizard();
				} else {
					manageChangedFlgAssegnaAlMittDest();
				}
			}
			
			@Override
			public boolean hasDefaultValue() {
				// se ho una sola UO collegata e il mittente è obbligatorio la setto lo stesso, anche se non è attiva la preimpostazione del mittente
				if(getUoProtocollanteValueMap().size() == 1 && isRequiredDetailSectionMittenti()) {
					return true;
				}
				return AurigaLayout.getParametroDBAsBoolean("PREIMP_UO_COME_MITT_PROT_UI") && (getSelezioneUoProtocollanteValueMap().size() == 1)
						&& (AurigaLayout.getIdUOPuntoProtAttivato() == null || "".equals(AurigaLayout.getIdUOPuntoProtAttivato()) );
			}

			@Override
			public Record getDefaultRecord() {
				Record lRecord = new Record();
				if (getSelezioneUoProtocollanteValueMap().size() == 1) {
					String idUoSoggetto = getSelezioneUoProtocollanteValueMap().keySet().toArray(new String[1])[0];
					String descrizione = getSelezioneUoProtocollanteValueMap().get(idUoSoggetto);
					lRecord.setAttribute("tipoMittente", "UOI");
					if (idUoSoggetto.startsWith("UO")) {
						lRecord.setAttribute("idUoSoggetto", idUoSoggetto.substring(2));
						lRecord.setAttribute("organigrammaMittente", idUoSoggetto);
					} else {
						lRecord.setAttribute("idUoSoggetto", idUoSoggetto);
						lRecord.setAttribute("organigrammaMittente", "UO" + idUoSoggetto);
					}
					lRecord.setAttribute("codRapidoMittente", descrizione.substring(0, descrizione.indexOf(" - ")));
					lRecord.setAttribute("denominazioneMittente", descrizione.substring(descrizione.indexOf(" - ") + 3));
					lRecord.setAttribute("flgAssegnaAlMittente", getFlgAssegnaAlMittenteDefault());
				}
				return lRecord;
			}
			
//			@Override
//			public Record getCanvasDefaultRecord() {
//				// Solo se non è attivo il parametro PREIMP_UO_COME_MITT_PROT_UI (o se non ho un unico ufficio di lavoro selezionato)
//				// quando è attivo DIM_ORGANIGRAMMA_NONSTD preimposto il codice rapido 
//				if(!hasDefaultValue() && AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
//					Record lRecord = super.getDefaultRecord();
//					lRecord.setAttribute("tipoMittente", "UOI");
//					lRecord.setAttribute("codRapidoMittente", AurigaLayout.getCodRapidoOrganigramma());
//					return lRecord;
//				}
//				return null;
//			}

			@Override
			public boolean getShowItemsIndirizzo() {
				return false; // i mittenti sono sempre interni quindi non mostro l'indirizzo
			}
			
			@Override
			public String getUoProtocollante() {
				return getUoProtocollanteSelezionata();
			}			
		};
		mittentiItem.setName("listaMittenti");
		mittentiItem.setShowTitle(false);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			mittentiItem.setTabIndex(-1);
			mittentiItem.setCanFocus(false);		
		}
		mittentiItem.setNotReplicable(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_MULTI_MITT_U"));
		if (isRequiredDetailSectionMittenti()) {
			mittentiItem.setAttribute("obbligatorio", true);
		}
	}

	@Override
	public boolean showOpenDetailSectionDestinatari() {
		return true;
	}

	@Override
	public boolean isRequiredDetailSectionDestinatari() {
		return true;
	}

	@Override
	protected void createDestinatariItem() {

		destinatariItem = new DestinatarioProtUscitaItem() {
			
			@Override
			public boolean isProtInModalitaWizard() {
				return isModalitaWizard();
			}
			
			@Override
			public boolean isSupportoOriginaleProtValorizzato() {
				if(isModalitaWizard()) {
					return isSupportoOriginaleValorizzato();
				}
				return false;
			}
			
			@Override
			public String getSupportoOriginaleProt() {
				if(isModalitaWizard()) {
					return getSupportoOriginale();
				}
				return null;
			}
			
			@Override
			public boolean isAttivoAssegnatarioUnicoCartaceoProt() {
				return isModalitaWizard() && isAttivoAssegnatarioUnicoCartaceo();
			}	
			
			@Override
			public boolean isAttivaRestrAssCartaceoProt() {
				return isModalitaWizard() && isAttivaRestrizioneAssegnazioneCartaceo();
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
				if(isModalitaWizard() && isRequiredDetailSectionDestinatari()) {
					if(detailSectionDestinatari != null) {
						redrawSections(detailSectionDestinatari);
					}
				}
			}
			
			@Override
			public void manageChangeFlgAssegnaAlDestinatario(ChangeEvent event) {
				if(isModalitaWizard()) {
					manageChangeFlgAssegnaAlMittDest(event);	
				}
			}
			
			@Override
			public void manageChangedFlgAssegnaAlDestinatario(Record canvasRecord) {
				if(isModalitaWizard()) {
					manageChangedFlgAssegnaAlMittDestWizard();
				} else {
					manageChangedFlgAssegnaAlMittDest();
				}
			}
			
			@Override
			public boolean showOpenIndirizzo() {
				if(getShowDestinatariEstesi()) {
					return AurigaLayout.getParametroDBAsBoolean("ATTIVA_INDIRIZZO_DEST_ESTESI");
				}
				return false;
			}

			@Override
			public boolean getShowItemsIndirizzo() {
				if(getShowDestinatariEstesi()) {
					return AurigaLayout.getParametroDBAsBoolean("ATTIVA_INDIRIZZO_DEST_ESTESI");
				}	
				// nascondo l'indirizzo solo se il tipo è uno fra UOI, UP, LD e PREF (vedi showHideIndirizzo in DestinatarioProtUscitaCanvas) mentre prima era sempre nascosto
				return isModalitaWizard();					
			}
			
			@Override
			public boolean getSoloMezzoTrasmissionePEC() {
				if(getShowDestinatariEstesi()) {
					Record lRecordUoRegistrazione = null;
					if (uoProtocollanteSelectItem != null) {
						if (uoProtocollanteSelectItem.getValueAsString() != null && !"".equals(uoProtocollanteSelectItem.getValueAsString())) {
							lRecordUoRegistrazione = AurigaLayout.getUoRegistrazioneMap().get(uoProtocollanteSelectItem.getValueAsString());				
						} else if (getSelezioneUoProtocollanteValueMap().size() == 1) {				
							lRecordUoRegistrazione = AurigaLayout.getUoRegistrazioneMap().get(getSelezioneUoProtocollanteValueMap().keySet().toArray(new String[1])[0]);				
						}
					}
					return lRecordUoRegistrazione != null && lRecordUoRegistrazione.getAttribute("flgSoloMezzoTrasmissionePEC") != null && "1".equals(lRecordUoRegistrazione.getAttribute("flgSoloMezzoTrasmissionePEC"));
				}
				return super.getSoloMezzoTrasmissionePEC();
			}
			
			@Override
			public Boolean validate() {	
				boolean valid = super.validate();
				valid = validateDestEsterni() && valid;
				return valid;
			}
			
			@Override
			public Boolean valuesAreValid() {
				boolean valid = super.valuesAreValid();
				valid = valuesAreValidDestEsterni() && valid;
				return valid;
			}
		};
		destinatariItem.setName("listaDestinatari");
		destinatariItem.setShowTitle(false);
		if (isRequiredDetailSectionDestinatari()) {
			destinatariItem.setAttribute("obbligatorio", true);
		}
	}
	
	public boolean validateDestEsterni() {
		boolean valid = true;
		if (mode == null || mode.equals("new") || mode.equals("edit")) {
			if(destinatariItem != null) {
				if(destinatariItem instanceof DestinatarioProtUscitaItem) {
					if (((DestinatarioProtUscitaItem)destinatariItem).isAttivoDestEsternoUnicoCartaceoProt()) {				
						RecordList listaDestinatari = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatari");
						int nroDestinatariEsterni = 0;
						if(destinatariItem.getVisible()) {
							nroDestinatariEsterni = listaDestinatari != null ? listaDestinatari.getLength() : 0;
						}
						if (nroDestinatariEsterni > 1) {
							if(detailSectionDestinatari != null) {							
								if (isOpenableDetailSection(detailSectionDestinatari)) {
									detailSectionDestinatari.open();
									if(destinatariForm != null) {
										destinatariForm.setFieldErrors("listaDestinatari", "Si può mettere un solo destinatario esterno se il supporto selezionato è cartaceo");
									}
								}
								valid = false;
							}
						}
					}
				}
			}
		}
		return valid;
	}
	
	public boolean valuesAreValidDestEsterni() {
		boolean valid = true;
		if (mode == null || mode.equals("new") || mode.equals("edit")) {
			if(destinatariItem != null) {
				if(destinatariItem instanceof DestinatarioProtUscitaItem) {
					if (((DestinatarioProtUscitaItem)destinatariItem).isAttivoDestEsternoUnicoCartaceoProt()) {				
						RecordList listaDestinatari = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatari");
						int nroDestinatariEsterni = 0;
						if(destinatariItem.getVisible()) {
							nroDestinatariEsterni = listaDestinatari != null ? listaDestinatari.getLength() : 0;
						}
						if (nroDestinatariEsterni > 1) {						
							valid = false;					
						}
					}
				}
			}
		}
		return valid;
	}

	public boolean validateAssegnatario(Record recordAssegnatario) {
		
		if (recordAssegnatario == null)
			return false;
		return true;
	}


	@Override
	public void manageChangedFilePrimario() {
		super.manageChangedFilePrimario();
		flgModificatiFileInvioPEC = true;
	}
	
	@Override
	public void manageChangedFilePrimarioOmissis() {
		super.manageChangedFilePrimarioOmissis();
		flgModificatiFileInvioPEC = true;
	}	

	@Override
	public void manageChangedFileInAllegatiItem() {
		super.manageChangedFileInAllegatiItem();
		flgModificatiFileInvioPEC = true;
	}

	@Override
	public boolean showDetailSectionDatiRicezione() {
		return false;
	}

	@Override
	public boolean showDetailSectionRegEmergenza() {
		return true;
	}

	@Override
	public boolean showConfermaAssegnazioneForm() {
		return true;
	}

	@Override
	public boolean isRequiredDetailSectionClassificazioneFascicolazione() {
		return AurigaLayout.getParametroDBAsBoolean("OBBL_CLASSIF_PROT_U");
	}

	@Override
	public void clickRegistra(final String motivoVarDatiReg) {
		Record record = new Record(vm.getValues());
		boolean inviataMailInteroperabile = record.getAttributeAsBoolean("inviataMailInteroperabile") != null
				&& record.getAttributeAsBoolean("inviataMailInteroperabile");
		if (inviataMailInteroperabile && (isModificaDatiReg() || flgModificatiFileInvioPEC)) {
			SC.ask("Attenzione: la registrazione è già stata trasmessa via PEC. Il/i destinatario/i della PEC ha/hanno ricevuto file e/o dati diversi da quelli che si stanno salvando. Continuare?",
					new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ProtocollazioneDetailUscita.super.clickRegistra(motivoVarDatiReg);
							}
						}
					});
		} else {
			super.clickRegistra(motivoVarDatiReg);
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