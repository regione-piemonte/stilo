/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.Portlet;

/**
 * Maschera di protocollazione INTERNA
 * 
 * @author Mattia Zanin
 *
 */

public abstract class ProtocollazioneDetailInterna extends ProtocollazioneDetailWizard {

	protected ProtocollazioneDetailInterna instance;

	public ProtocollazioneDetailInterna(String nomeEntita) {

		super(nomeEntita);

		instance = this;
	}

	@Override
	public String getFlgTipoProv() {
		return "I";
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
		return true;
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
				return false;
			}
			
			@Override
			public String getUoProtocollante() {
				return getUoProtocollanteSelezionata();
			}	
		};
		mittentiItem.setName("listaMittenti");
		mittentiItem.setShowTitle(false);
		mittentiItem.setNotReplicable(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_MULTI_MITT_I"));
		if (isRequiredDetailSectionMittenti()) {
			mittentiItem.setAttribute("obbligatorio", true);
		}
	}
	
	@Override
	public boolean showDetailSectionDestinatari() {		
		if(AurigaLayout.getParametroDBAsBoolean("HIDE_DEST_IN_PROT_INTERNO")) {
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
		if(isModalitaWizard() && !isRepertorioDetailEntrata() && !isRepertorioDetailInterno() && !isRepertorioDetailUscita()) {
			return false;
		}
		return true;
	}

	@Override
	protected void createDestinatariItem() {
		
		destinatariItem = new DestinatarioProtInternaItem() {
			
			@Override
			public boolean isAbilitatiDestEsterniInRegInt() {
				boolean isNewMode = mode == null || "new".equals(mode);
				return AurigaLayout.getParametroDBAsBoolean("CONSENTI_DEST_EST_IN_REG_INT") && !isNewMode;
			}
						
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
			public boolean getShowItemsIndirizzo() {
				return false;
			}
		};
		destinatariItem.setName("listaDestinatari");
		destinatariItem.setShowTitle(false);
		if (isRequiredDetailSectionDestinatari()) {
			destinatariItem.setAttribute("obbligatorio", true);
		}
		
	}
	
	@Override
	public boolean showDetailSectionAssegnazioneBeforeDestinatari() {
		if(isModalitaWizard() && !isRepertorioDetailEntrata() && !isRepertorioDetailInterno() && !isRepertorioDetailUscita()) {
			return true;			
		}
		return false;
	}
	
	@Override
	public boolean isRequiredDetailSectionAssegnazione() {
		if(isModalitaWizard() && !isRepertorioDetailEntrata() && !isRepertorioDetailInterno() && !isRepertorioDetailUscita()) {
			return true;
		}
		return AurigaLayout.getParametroDBAsBoolean("OBBL_ASSEGNAZIONE_PROT_I");		
	}
	
	@Override
	protected void createAssegnazioneItem() {
		super.createAssegnazioneItem();
		assegnazioneItem.setFlgEscludiGruppiMisti(true);
	}

	@Override
	public boolean showDetailSectionDatiRicezione() {
		return false;
	}

	@Override
	public boolean showDetailSectionRegEmergenza() {
		return false;
	}

	@Override
	public boolean showConfermaAssegnazioneForm() {
		return true;
	}

	@Override
	public boolean isRequiredDetailSectionClassificazioneFascicolazione() {
		return AurigaLayout.getParametroDBAsBoolean("OBBL_CLASSIF_PROT_I");
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
			final boolean flgNoAltriAssegnatari, final boolean flgNoOggetto, final boolean flgNoPrimario, final boolean flgNoAllegati, final boolean flgNoFileAllegati, 
			final boolean flgNoDocumentiCollegati, final boolean flgNoFascicolazione, final boolean flgNoAttributiCustom, ServiceCallback<Record> callback) {
		if(isProtocolloGeneraleConRepertorio()) {
			values.put("repertorio", (String) values.get("siglaNumerazioneSecondaria"));								
		}
		super.nuovaProtComeCopia(values, attributiDinamiciDoc, flgNoEsibente, flgNoMittenti, flgNoDestinatari, flgNoAltriAssegnatari, flgNoOggetto, flgNoPrimario, flgNoAllegati,
				flgNoFileAllegati, flgNoDocumentiCollegati, flgNoFascicolazione, flgNoAttributiCustom, callback);
	}
	
	@Override
	protected void afterUpdate(final Record recordDettaglio) {

		String flgTipoProv = recordDettaglio.getAttribute("flgTipoProv") != null ? recordDettaglio.getAttribute("flgTipoProv") : "";
		if("U".equals(flgTipoProv)) {
			Layout.removePortlet("protocollazione_interna");
			if (!Layout.isOpenedPortlet("protocollazione_uscita")) {
				ProtocollazioneDetailUscita protocollazioneDetailUscita = ProtocollazioneUtil
						.buildProtocollazioneDetailUscita(recordDettaglio);				
				protocollazioneDetailUscita.caricaDettaglio(null, recordDettaglio);
				protocollazioneDetailUscita.viewMode();
				Layout.addPortlet("protocollazione_uscita", protocollazioneDetailUscita);
			} else {
				final Portlet portlet = Layout.getOpenedPortlet("protocollazione_uscita");
				SC.ask("La finestra di \"" + portlet.getTitle()
						+ "\" risulta già aperta. Tutti i dati andranno persi. Continuare comunque l'operazione?",
						new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									Layout.selectPortlet("protocollazione_uscita");
									ProtocollazioneDetailUscita protocollazioneDetailUscita = ProtocollazioneUtil
											.buildProtocollazioneDetailUscita(recordDettaglio);
									protocollazioneDetailUscita.caricaDettaglio(null, recordDettaglio);
									protocollazioneDetailUscita.viewMode();
									portlet.setBody(protocollazioneDetailUscita);
								}
							}
						});
			}
		}	
	}

}