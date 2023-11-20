/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashSet;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class AllegatoDetail extends CustomDetail {
	
	protected AllegatiGridItem gridItem;
	
	protected DynamicForm mDynamicForm;
	
	protected AllegatiItem listaAllegatiItem;
	
	public AllegatoDetail(String nomeEntita, final AllegatiGridItem gridItem, Record recordAllegato, boolean canEdit) {
		
		super(nomeEntita);
		
		this.gridItem = gridItem;
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(18);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWrapItemTitles(false);
//		mDynamicForm.setCellBorder(1);
		
		listaAllegatiItem = new AllegatiItem() {
			
			@Override
			public GWTRestDataSource getTipiFileAllegatoDataSource() {
				if(gridItem.getTipiFileAllegatoDataSource() != null) {
					return gridItem.getTipiFileAllegatoDataSource();
				}
				return super.getTipiFileAllegatoDataSource();
			}
			
			@Override
			public boolean showFilterEditorInTipiFileAllegato() {
				return gridItem.showFilterEditorInTipiFileAllegato();
			}
			
			/*
			 * se un allegato è parte integrante e uno dei 4 flag di dati protetti è spuntato e se non hanno selezionato uno tra "escludi pubblicazione" e 
			 * il flag per attivare la versione con omissis diamo errore "In caso di dati protetti, trattandosi di allegato parte integrante, devi selezionare 
			 * l'esclusione dalla pubblicazione o predisporre una versione con omissis".
			 */
			
			@Override
			public Boolean validate() {
				boolean valid = super.validate();
				if(isShowFlgParteDispositivo() && (isShowFlgNoPubblAllegato() || getShowVersioneOmissis())) {
					RecordList listaAllegati = new Record(vm.getValues()).getAttributeAsRecordList("listaAllegati");
					if (listaAllegati != null) {
						for (int i = 0; i < listaAllegati.getLength(); i++) {
							Record allegato = listaAllegati.get(i);
							boolean flgParteDispositivo = allegato.getAttributeAsBoolean("flgParteDispositivo");									
							boolean flgDatiProtettiTipo1 = getShowFlgDatiProtettiTipo1() ? allegato.getAttributeAsBoolean("flgDatiProtettiTipo1") : false;									
							boolean flgDatiProtettiTipo2 = getShowFlgDatiProtettiTipo2() ? allegato.getAttributeAsBoolean("flgDatiProtettiTipo2") : false;										
							boolean flgDatiProtettiTipo3 = getShowFlgDatiProtettiTipo3() ? allegato.getAttributeAsBoolean("flgDatiProtettiTipo3") : false;							
							boolean flgDatiProtettiTipo4 = getShowFlgDatiProtettiTipo4() ? allegato.getAttributeAsBoolean("flgDatiProtettiTipo4") : false;		
							boolean flgNoPubblAllegato = allegato.getAttributeAsBoolean("flgNoPubblAllegato");
							boolean flgDatiSensibili = allegato.getAttributeAsBoolean("flgDatiSensibili");
							if(flgParteDispositivo && (flgDatiProtettiTipo1 || flgDatiProtettiTipo2 || flgDatiProtettiTipo3 || flgDatiProtettiTipo4) && !(flgNoPubblAllegato || flgDatiSensibili)) {
								if(isShowFlgNoPubblAllegato() && getShowVersioneOmissis()) {
									mDynamicForm.setFieldErrors("listaAllegati", "In caso di dati protetti, trattandosi di allegato parte integrante, devi selezionare l'esclusione dalla pubblicazione o predisporre una versione con omissis");									
								} else if(isShowFlgNoPubblAllegato()) {
									mDynamicForm.setFieldErrors("listaAllegati", "In caso di dati protetti, trattandosi di allegato parte integrante, devi selezionare l'esclusione dalla pubblicazione");									
								} else if(getShowVersioneOmissis()) {
									mDynamicForm.setFieldErrors("listaAllegati", "In caso di dati protetti, trattandosi di allegato parte integrante, devi predisporre una versione con omissis");										
								}
								valid = false;															
							}							
						}
					}					
				}
				return valid;
			}
			
			@Override
			public Boolean valuesAreValid() {					
				boolean valid = super.valuesAreValid();
				if(isShowFlgParteDispositivo() && (isShowFlgNoPubblAllegato() || getShowVersioneOmissis())) {
					RecordList listaAllegati = new Record(vm.getValues()).getAttributeAsRecordList("listaAllegati");
					if (listaAllegati != null) {
						for (int i = 0; i < listaAllegati.getLength(); i++) {
							Record allegato = listaAllegati.get(i);
							boolean flgParteDispositivo = allegato.getAttributeAsBoolean("flgParteDispositivo");									
							boolean flgDatiProtettiTipo1 = getShowFlgDatiProtettiTipo1() ? allegato.getAttributeAsBoolean("flgDatiProtettiTipo1") : false;									
							boolean flgDatiProtettiTipo2 = getShowFlgDatiProtettiTipo2() ? allegato.getAttributeAsBoolean("flgDatiProtettiTipo2") : false;										
							boolean flgDatiProtettiTipo3 = getShowFlgDatiProtettiTipo3() ? allegato.getAttributeAsBoolean("flgDatiProtettiTipo3") : false;							
							boolean flgDatiProtettiTipo4 = getShowFlgDatiProtettiTipo4() ? allegato.getAttributeAsBoolean("flgDatiProtettiTipo4") : false;		
							boolean flgNoPubblAllegato = allegato.getAttributeAsBoolean("flgNoPubblAllegato");
							boolean flgDatiSensibili = allegato.getAttributeAsBoolean("flgDatiSensibili");									
							if(flgParteDispositivo && (flgDatiProtettiTipo1 || flgDatiProtettiTipo2 || flgDatiProtettiTipo3 || flgDatiProtettiTipo4) && !(flgNoPubblAllegato || flgDatiSensibili)) {
								valid = false;																
							}							
						}
					}
				}
				return valid;
			}

			
			public boolean isFromAllegatoDetailInGridItem() {
				return true;
			}
			
			@Override
			public Record getDetailRecord() {
				return gridItem.getDetailRecord();
			}
			
			@Override
			public String getIdProcess() {
				return gridItem.getIdProcess();
			}
			
			@Override
			public String getIdFolder() {
				return gridItem.getIdFolder();
			}
			
			@Override
			public String getIdDocFilePrimario(){
				return gridItem.getIdDocFilePrimario();
			}
			
			@Override
			public String getIdUd() {
				return gridItem.getIdUd();
			}

			@Override
			public String getIdProcessType() {
				return gridItem.getIdProcessType();
			}

			@Override
			public String getIdTaskCorrenteAllegati() {
				return gridItem.getIdTaskCorrenteAllegati();
			}

			@Override
			public HashSet<String> getTipiModelliAttiAllegati() {
				return gridItem.getTipiModelliAttiAllegati();
			}
			
			@Override
			public boolean isObbligatorioFile() {
				return gridItem.isObbligatorioFile();
			}
			
			@Override
			public boolean validateFormatoFileAllegato(InfoFileRecord info) {
				return gridItem.validateFormatoFileAllegato(info);
			}
			
			@Override
			public String getFormatoFileNonValidoErrorMessage() {
				return gridItem.getFormatoFileNonValidoErrorMessage();
			}
			
			@Override
			public String getTitleNomeFileAllegato() {
				return gridItem.getTitleNomeFileAllegato();
			}
			
			@Override
			public boolean isProtInModalitaWizard() {
				return gridItem.isProtInModalitaWizard();
			}

			@Override
			public boolean isCanaleSupportoDigitale() {
				return gridItem.isCanaleSupportoDigitale();
			}

			@Override
			public boolean isCanaleSupportoCartaceo() {
				return gridItem.isCanaleSupportoCartaceo();
			}

			@Override
			public boolean isFormatoAmmesso(InfoFileRecord info) {
				return gridItem.isFormatoAmmesso(info);
			}
				
			@Override
			public boolean isFormatoAmmessoParteIntegrante(InfoFileRecord info) {
				return gridItem.isFormatoAmmessoParteIntegrante(info);
			}	
			
			@Override
			public String getRifiutoAllegatiConFirmeNonValide() {
				return gridItem.getRifiutoAllegatiConFirmeNonValide();
			}
			
			@Override
			public boolean isDisattivaUnioneAllegatiFirmati() {
				return gridItem.isDisattivaUnioneAllegatiFirmati();
			}
			
			@Override
			public boolean isPubblicazioneSeparataPdfProtetti() {
				return gridItem.isPubblicazioneSeparataPdfProtetti();
			}
			
			@Override
			public String getFixedTipoAllegato() {
				return gridItem.getFixedTipoAllegato();
			}
			
			@Override
			public boolean showTipoAllegato() {
				return gridItem.getShowTipoAllegato();
			}
			
			@Override
			public boolean showGeneraDaModello() {
				return gridItem.getShowGeneraDaModello();
			}
		
			@Override
			public boolean canBeEditedByApplet() {
				return gridItem.canBeEditedByApplet();
			}
			
			@Override
			public boolean isAttivaTimbraturaFilePostRegAllegato() {
				return gridItem.isAttivaTimbraturaFilePostRegAllegato();				
			}
			
			@Override
			public boolean sonoInMail() {
				return gridItem.sonoInMail();
			}
			
			@Override
			public boolean sonoModificaVisualizza() {
				return gridItem.sonoModificaVisualizza();
			}
			
			@Override
			public void clickTrasformaInPrimario(int index) {
				gridItem.clickTrasformaInPrimario(index);
			}
			
			@Override
			public void caricaModelloAllegato(String idModello, String tipoModello, String flgConvertiInPdf,
					ServiceCallback<Record> callback) {
				gridItem.caricaModelloAllegato(idModello, tipoModello, flgConvertiInPdf, callback);
			}
			
			@Override
			public void visualizzaVersioni(Record allegatoRecord) {
				gridItem.clickVisualizzaVersioni(allegatoRecord);
			}
			
			@Override
			public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
				return gridItem.getRecordCaricaModelloAllegato(idModello, tipoModello);
			}

			@Override
			public boolean showNumeroAllegato() {
				return false;
			}
			
			@Override
			public boolean getFlgAllegAttoParteIntDefault() {
				return gridItem.getFlgAllegAttoParteIntDefault();
			}
			
			@Override
			public String getTitleFlgParteDispositivo() {
				return gridItem.getTitleFlgParteDispositivo().toLowerCase();
			}
			
			@Override
			public String getTitleFlgNoPubblAllegato() {
				return gridItem.getTitleFlgNoPubblAllegato().toLowerCase();
			}
			
			@Override
			public boolean getFlgAllegAttoPubblSepDefault() {
				return gridItem.getFlgAllegAttoPubblSepDefault();
			}
			
			// Questo metodo deve rimanere commentato: il titolo della colonna della grid è sempre "Da unire al dispositivo", mentre il check in dettaglio ha come label 
			// il valore del parametro DB LABEL_FLG_ALLEGATO_PI_ATTO_SEPARATO (se il parametro è vuoto o non esiste il valore di default è "da non unire al dispositivo")
//			@Override
//			public String getTitleFlgPubblicaSeparato() {
//				return gridItem.getTitleFlgPubblicaSeparato().toLowerCase();
//			}	
			
			@Override
			public String getTitleFlgDatiSensibili() {
				return gridItem.getTitleFlgDatiSensibili().toLowerCase();
			}
			
			@Override
			public Integer getWidthDescrizioneFileAllegato() {
				return gridItem.getWidthDescrizioneFileAllegato();
			}
			
			@Override
			public Integer getWidthNomeFileAllegato() {
				return gridItem.getWidthNomeFileAllegato();
			}
			
			@Override
			public boolean getShowFlgDatiProtettiTipo1() {
				return gridItem.getShowFlgDatiProtettiTipo1();
			}
			
			@Override
			public String getTitleFlgDatiProtettiTipo1() {
				return gridItem.getTitleFlgDatiProtettiTipo1();
			}
			
			@Override
			public boolean getShowFlgDatiProtettiTipo2() {
				return gridItem.getShowFlgDatiProtettiTipo2();
			}
			
			@Override
			public String getTitleFlgDatiProtettiTipo2() {
				return gridItem.getTitleFlgDatiProtettiTipo2();
			}
			
			@Override
			public boolean getShowFlgDatiProtettiTipo3() {
				return gridItem.getShowFlgDatiProtettiTipo3();
			}
			
			@Override
			public String getTitleFlgDatiProtettiTipo3() {
				return gridItem.getTitleFlgDatiProtettiTipo3();
			}
			
			@Override
			public boolean getShowFlgDatiProtettiTipo4() {
				return gridItem.getShowFlgDatiProtettiTipo4();
			}
			
			@Override
			public String getTitleFlgDatiProtettiTipo4() {
				return gridItem.getTitleFlgDatiProtettiTipo4();
			}
			
			@Override
			public boolean getShowVersioneOmissis() {
				return gridItem.getShowVersioneOmissis();
			}
			
			@Override
			public long getDimAlertAllegato() {
				return gridItem.getDimAlertAllegato();
			}
			
			@Override
			public long getDimMaxAllegatoXPubbl() {
				return gridItem.getDimMaxAllegatoXPubbl();
			}
			
			@Override
			public boolean isHideVisualizzaVersioniButton() {
				return gridItem.isHideVisualizzaVersioniButton();
			}
			
			@Override
			public boolean isHideAcquisisciDaScannerInAltreOperazioniButton() {
				return gridItem.isHideAcquisisciDaScannerInAltreOperazioniButton();
			}
			
			@Override
			public boolean isHideFirmaInAltreOperazioniButton() {
				return gridItem.isHideFirmaInAltreOperazioniButton();
			}

			@Override
			public boolean isHideTimbraInAltreOperazioniButton() {
				return gridItem.isHideTimbraInAltreOperazioniButton();
			}
			
			@Override
			public boolean isAttivaTimbroTipologia() {
				return gridItem.isAttivaTimbroTipologia();
			}
			
			@Override
			public boolean isAttivaVociBarcode(){
				return gridItem.isAttivaVociBarcode();
			}
			
			@Override
			public boolean isFromFolderPraticaPregressa(){
				return gridItem.isFromFolderPraticaPregressa();
			}
			
			@Override
			public String getNroDocumentoBarcodeLabel() {
				return gridItem.getNroDocumentoBarcodeLabel();
			}
			
			@Override
			public boolean isDocumentiInizialiIstanza() {
				return gridItem.isDocumentiInizialiIstanza();
			}
			
			@Override
			public boolean isDocumentiIstruttoria() {
				return gridItem.isDocumentiIstruttoria();
			}
			
			@Override
			public boolean isDocPraticaVisure() {
				return gridItem.isDocPraticaVisure();
			}
			
			@Override
			public boolean isDocCedAutotutela() {
				return gridItem.isDocCedAutotutela();
			}
			
			@Override
			public boolean isDocIstruttoriaDettFascicoloGenCompleto() {
				return gridItem.isDocIstruttoriaDettFascicoloGenCompleto();
			}			
			
			@Override
			public String getEmailContribuente() {
				return gridItem.getEmailContribuente();
			}
			
			@Override
			public String getFlgTipoProvProtocollo() {
				return gridItem.getFlgTipoProvProtocollo();
			}
			
			@Override
			public boolean showTimbraBarcodeMenuOmissis() {
				return gridItem.getShowTimbraBarcodeMenuOmissis();
			}
			
			@Override
			public boolean isReadOnly() {
				return gridItem.isReadOnly();
			}
			
			@Override
			public boolean isSoloPreparazioneVersPubblicazione() {
				return gridItem.isSoloPreparazioneVersPubblicazione();
			}
			
			@Override
			public boolean isSoloOmissisModProprietaFile() {
				return gridItem.isSoloOmissisModProprietaFile();
			}
			
			@Override
			public boolean isAllegatiNonParteIntegranteNonEditabili() {
				return gridItem.isAllegatiNonParteIntegranteNonEditabili();
			}
			
			@Override
			public boolean isAttivaModificaEsclusionePubblicazione() {
				return gridItem.isAttivaModificaEsclusionePubblicazione();
			}
			
			@Override
			public boolean isAggiuntaFile() {
				return gridItem.isAggiuntaFile();
			}
			
			@Override
			public boolean isCanEditOnlyOmissis() {
				return gridItem.isCanEditOnlyOmissis();
			}
			
			@Override
			public void manageChangedFileAllegati() {
				gridItem.manageChangedFileAllegati();
			}
			
			@Override
			public void manageChangedFileAllegatiParteDispositivo() {
				gridItem.manageChangedFileAllegatiParteDispositivo();
			}
			
			@Override
			public boolean isFlgPubblicaAllegatiSeparati() {
				if(gridItem.getShowFlgPubblicaSeparato() && gridItem.getFlgPubblicazioneAllegatiUguale()) {
					return gridItem.isFlgPubblicaAllegatiSeparati();
				}
				return false;
			}
			
			@Override
			public boolean isAttivaAllegatoUd() {
				return gridItem.isAttivaAllegatoUd();
			}
			
			@Override
			public boolean isAttivaSceltaPosizioneAllegatiUniti() {
				return gridItem.isAttivaSceltaPosizioneAllegatiUniti();
			}
			
			@Override
			public boolean isShowModalPreview() {
				return isEnablePreviewModal();
			}
		};
		listaAllegatiItem.setName("listaAllegati");
		listaAllegatiItem.setShowTitle(false);
		listaAllegatiItem.setShowFlgSostituisciVerPrec(gridItem.getShowFlgSostituisciVerPrec());
		listaAllegatiItem.setShowFlgProvEsterna(gridItem.getShowFlgProvEsterna());
		listaAllegatiItem.setShowFlgParere(gridItem.getShowFlgParere());
		listaAllegatiItem.setShowFlgParteDispositivo(gridItem.getShowFlgParteDispositivo());
		listaAllegatiItem.setShowFlgNoPubblAllegato(gridItem.getShowFlgNoPubblAllegato());			
		listaAllegatiItem.setShowFlgPubblicaSeparato(gridItem.getShowFlgPubblicaSeparato() && !gridItem.getFlgPubblicazioneAllegatiUguale());
		listaAllegatiItem.setFlgPubblicazioneAllegatiUguale(false); // questo va sempre passato a false
		if(recordAllegato != null) {
			listaAllegatiItem.setAttribute("obbligatorio", true);
			listaAllegatiItem.setNotReplicable(true);
		}
		
		mDynamicForm.setFields(
			listaAllegatiItem
		);
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(mDynamicForm);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
		
		if(recordAllegato != null) {
			Record record = new Record();
			RecordList listaAllegati = new RecordList();
			listaAllegati.add(recordAllegato);
			record.setAttribute("listaAllegati", listaAllegati);
			editRecord(record);		
		} else {
			editNewRecord();
			listaAllegatiItem.onClickNewButton();
		}
		
		listaAllegatiItem.setCanEdit(canEdit);
		if(canEdit) {
			listaAllegatiItem.setReadOnly(gridItem.isReadOnly());
			listaAllegatiItem.setSoloPreparazioneVersPubblicazione(gridItem.isSoloPreparazioneVersPubblicazione());		
			listaAllegatiItem.setSoloOmissisModProprietaFile(gridItem.isSoloOmissisModProprietaFile());
			listaAllegatiItem.setAttivaModificaEsclusionePubblicazione(gridItem.isAttivaModificaEsclusionePubblicazione());
			listaAllegatiItem.setAggiuntaFile(gridItem.isAggiuntaFile());
			listaAllegatiItem.setCanEditOnlyOmissis(gridItem.isCanEditOnlyOmissis());			
		}
		listaAllegatiItem.updateMode(); // per impostare il mode corretto anche in dettaglio		
	}
	
	public Record getRecordToSave() {
		Record lRecordToSave = new Record();
		lRecordToSave.setAttribute("listaAllegati", mDynamicForm.getValueAsRecordList("listaAllegati"));
		return lRecordToSave;
	}
	
	@Override
	public boolean customValidate() {
		if(!listaAllegatiItem.isObbligatorio()) {
			listaAllegatiItem.removeEmptyCanvas();
		}
		return super.customValidate();
	}
	
	public boolean isEnablePreviewModal () {
		return true;
	}
		
}