/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;

/**
 * Maschera di una BOZZA
 * 
 * @author Mattia Zanin
 *
 */

public abstract class ProtocollazioneDetailBozze extends ProtocollazioneDetailWizard {

	protected ProtocollazioneDetailBozze instance;

	public ProtocollazioneDetailBozze(String nomeEntita) {
		
		super(nomeEntita);		
		
		instance = this;
	}

	@Override
	public String getTitleUoProtocollanteSelectItem() {
		return "<b>U.O. registrazione</b>";
	}
	
	@Override
	public boolean isRequiredDetailSectionMittenti() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_OBBL_MITT_IN_BOZZE");
	}
	
	@Override
	protected void createMittentiItem() {

		mittentiItem = new MittenteProtInternaItem() {
			
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
			public boolean getFlgAssegnaAlMittenteDefault() {
				return false;
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
				return AurigaLayout.getParametroDBAsBoolean("PREIMP_UO_COME_MITT_BOZZE") && (getSelezioneUoProtocollanteValueMap().size() == 1)
						&& (AurigaLayout.getIdUOPuntoProtAttivato() == null || "".equals(AurigaLayout.getIdUOPuntoProtAttivato()));
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
				}
				return lRecord;
			}
			
//			@Override
//			public Record getCanvasDefaultRecord() {
//				// Solo se non è attivo il parametro PREIMP_UO_COME_MITT_BOZZE (o se non ho un unico ufficio di lavoro selezionato)
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
				return false;
			}
			
			@Override
			public String getUoProtocollante() {
				return getUoProtocollanteSelezionata();
			}	
			
			@Override
			public boolean isInBozza() {
				return true;
			}
		};
		mittentiItem.setName("listaMittenti");
		mittentiItem.setShowTitle(false);
		mittentiItem.setShowNewButton(true);
		mittentiItem.setNotReplicable(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_MULTI_MITT_I"));
		if (isRequiredDetailSectionMittenti()) {
			mittentiItem.setAttribute("obbligatorio", true);
		}
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

			 // il parametro ASSEGNAZIONE_DEST_DEFAULT va considerato anche sulle bozze se true dato che ora i check di assegnazione e cc sulle bozze sono solo indicazioni che non scatenano assegnazioni/invii cc (solo quando si protocolla)
//			@Override
//			public boolean getFlgAssegnaAlDestinatarioDefault() {
//				return false;
//			}
			
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
				return isModalitaWizard();
			}
			
			@Override
			public boolean getSoloMezzoTrasmissionePEC() {
				if(getShowDestinatariEstesi()) {
//					Record lRecordUoRegistrazione = null;
//					if (uoProtocollanteSelectItem != null) {
//						if (uoProtocollanteSelectItem.getValueAsString() != null && !"".equals(uoProtocollanteSelectItem.getValueAsString())) {
//							lRecordUoRegistrazione = AurigaLayout.getUoRegistrazioneMap().get(uoProtocollanteSelectItem.getValueAsString());				
//						} else if (getSelezioneUoProtocollanteValueMap().size() == 1) {				
//							lRecordUoRegistrazione = AurigaLayout.getUoRegistrazioneMap().get(getSelezioneUoProtocollanteValueMap().keySet().toArray(new String[1])[0]);				
//						}
//					}
//					return lRecordUoRegistrazione != null && lRecordUoRegistrazione.getAttribute("flgSoloMezzoTrasmissionePEC") != null && "1".equals(lRecordUoRegistrazione.getAttribute("flgSoloMezzoTrasmissionePEC"));
					return false;
				}
				return super.getSoloMezzoTrasmissionePEC();
			}
			
			@Override
			public boolean isInBozza() {
				return true;
			}
		};
		destinatariItem.setName("listaDestinatari");
		destinatariItem.setShowTitle(false);
		destinatariItem.setShowNewButton(true);
	}
	
	@Override
	public boolean showDetailSectionAltreVieAfterHeader() {
		Boolean flgTipoDocConVie = getFlgTipoDocConVie();
		if(isModalitaWizard() && showAttributiDinamiciDoc() && flgTipoDocConVie != null && flgTipoDocConVie) {
			return true;
		}
		return super.showDetailSectionAltreVieAfterHeader();
	}
	
	@Override
	public boolean showOpenDetailSectionAltreVie() {
		Boolean flgTipoDocConVie = getFlgTipoDocConVie();
		if(isModalitaWizard() && showAttributiDinamiciDoc() && flgTipoDocConVie != null && flgTipoDocConVie) {
			return true;
		} 
		return super.showOpenDetailSectionAltreVie();
	}
	
	@Override
	public boolean isRequiredDetailSectionAltreVie() {
		Boolean flgTipoDocConVie = getFlgTipoDocConVie();
		if(isModalitaWizard() && showAttributiDinamiciDoc() && flgTipoDocConVie != null && flgTipoDocConVie) {
			return true;
		} 
		return super.isRequiredDetailSectionAltreVie();
	}
	
	@Override
	public String getTitleDetailSectionAltreVie() {
		Boolean flgTipoDocConVie = getFlgTipoDocConVie();
		if(isModalitaWizard() && showAttributiDinamiciDoc() && flgTipoDocConVie != null && flgTipoDocConVie) {
			return "Indirizzo collegato";
		}
		return super.getTitleDetailSectionAltreVie();
	}
	
	public boolean showFlgFuoriComuneInAltreVie() {
		Boolean flgTipoDocConVie = getFlgTipoDocConVie();
		if(isModalitaWizard() && showAttributiDinamiciDoc() && flgTipoDocConVie != null && flgTipoDocConVie) {
			return false;
		}
		return super.showFlgFuoriComuneInAltreVie();
	}
	
	public boolean getFlgFuoriComuneInAltreVie() {
		Boolean flgTipoDocConVie = getFlgTipoDocConVie();
		if(isModalitaWizard() && showAttributiDinamiciDoc() && flgTipoDocConVie != null && flgTipoDocConVie) {
			return false;
		}
		return super.getFlgFuoriComuneInAltreVie();
	}
	
	public boolean isNotReplicableAltreVieItem() {
		Boolean flgTipoDocConVie = getFlgTipoDocConVie();
		if(isModalitaWizard() && showAttributiDinamiciDoc() && flgTipoDocConVie != null && flgTipoDocConVie) {
			return true;
		}
		return super.isNotReplicableAltreVieItem();
	}	
	
	@Override
	public boolean isRequiredDetailSectionContenuti() {
		return isModalitaWizard() && isRequiredOggetto();
	}
	
	@Override
	public boolean isRequiredOggetto() {
		Boolean flgOggettoNonObblig = getFlgOggettoNonObblig();
		if(isModalitaWizard() && showAttributiDinamiciDoc() && flgOggettoNonObblig != null && flgOggettoNonObblig) {
			return false;
		}
		return super.isRequiredOggetto();
	}
	
	public Boolean getFlgTipoDocConVie() {
		return null;
	}
	
	public Boolean getFlgOggettoNonObblig() {
		return null;
	}

	@Override
	public boolean showFlgSostituisciVerPrecItem() {
		return true;
	}
	
	@Override
	public boolean isAttivaStampaEtichettaAutoReg(Record record) {
		return false;
	}
	
	@Override
	public boolean showStampaEtichettaButton(Record record) {
		return false;		
	}	
	
	@Override
	public void modificaDatiMode(Boolean abilAggiuntaFile) {
		
		this.editMode = "modificaDati";		
		setModificaDatiReg(false);
		editMode();
		Record record = new Record(getValuesManager().getValues());
		if (abilAggiuntaFile == null) {
			abilAggiuntaFile = record.getAttributeAsBoolean("abilAggiuntaFile");
		}
		if (abilAggiuntaFile) {
			nomeFilePrimarioItem.setCanEdit(true);
			filePrimarioButtons.setCanEdit(true);
			uploadFilePrimarioItem.setCanEdit(true);
			if (generaDaModelloButton != null) {
				generaDaModelloButton.setCanEdit(true);
			}
		} else {
			nomeFilePrimarioItem.setCanEdit(false);
			filePrimarioButtons.setCanEdit(false);
			uploadFilePrimarioItem.setCanEdit(false);
			if (generaDaModelloButton != null) {
				generaDaModelloButton.setCanEdit(false);
			}
		}
		if (abilAggiuntaFile) {
			if(fileAllegatiItem != null) {
				if(fileAllegatiItem instanceof AllegatiGridItem) {
					((AllegatiGridItem)fileAllegatiItem).setAggiuntaFileMode();
				} else if(fileAllegatiItem instanceof AllegatiItem) {
					((AllegatiItem)fileAllegatiItem).setAggiuntaFileMode();
				}				
			}
		} else {
			fileAllegatiItem.setCanEdit(false);
		}
	}

}