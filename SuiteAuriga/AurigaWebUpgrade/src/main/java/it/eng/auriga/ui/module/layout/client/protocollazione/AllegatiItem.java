/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.ListaWarningsPopup;
import it.eng.auriga.ui.module.layout.client.archivio.DocPraticaPregressaItem;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.callback.UploadMultipleItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.IDatiSensibiliItem;
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.FileMultipleUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public class AllegatiItem extends ReplicableItem implements IDatiSensibiliItem {

	DynamicForm addButtonsForm;

	private Record detailRecord;
	private Integer nomeFileWidth;
	private boolean showFlgProvEsterna;
	private boolean showFlgParere;
	private boolean showFlgParteDispositivo;
	private boolean showFlgNoPubblAllegato;
	private boolean showFlgPubblicaSeparato;
	private boolean showFlgEscludiPubblicazione;
	private boolean flgPubblicazioneAllegatiUguale;
	private boolean flgSoloPreparazioneVersPubblicazione;
	private boolean flgSoloOmissisModProprietaFile;
	private boolean showFlgSostituisciVerPrec;
	private boolean showImportaFileDaDocumenti = true;
	private boolean showCollegaDocumentiImportati = true;
	private boolean hideVisualizzaVersioniButton;
	private boolean hideAcquisisciDaScannerInAltreOperazioniButton;
	private boolean hideFirmaInAltreOperazioniButton;
	private boolean hideTimbraInAltreOperazioniButton;
	private boolean showDownloadOutsideAltreOperazioniButton;
	private FileMultipleUploadItemWithFirmaAndMimeType uploadButton;
	private ImgButtonItem importaFileDocumentiBtn;
	private CheckboxItem collegaDocumentiImportatiCbx;

	private boolean readOnly;
	private boolean soloPreparazioneVersPubblicazione;
	private boolean soloOmissisModProprietaFile;
	private boolean attivaModificaEsclusionePubblicazione;
	private boolean aggiuntaFile;
	private boolean canEditOnlyOmissis;
	
	protected ReplicableCanvas lastCanvasToReplicate;
	
	private int numFileCaricatiInErrore = 0;
	private int numFileCaricatiInUploadMultiploGlobal = 0;
	
	public AllegatiItem() {
	}
	
	public AllegatiItem(int nomeFileWidth) {
		this();
		setNomeFileWidth(nomeFileWidth);			
	}
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		HashMap<String, String> lMap = new HashMap<String, String>();
		lMap.put("nomeFileWidth", getNomeFileWidth() != null ? String.valueOf(getNomeFileWidth()) : null);
		AllegatoCanvas lAllegatoCanvas = new AllegatoCanvas(this, lMap);
		return lAllegatoCanvas;
	}
	
	@Override
	public void setUpClickRemoveHandler(VLayout lVLayout, HLayout lHLayout) {
		super.setUpClickRemoveHandler(lVLayout, lHLayout);
		manageOnChanged();
	}

	public void manageOnChanged() {

	}
	
	public String getFixedTipoAllegato() {
		return null;
	}
	
	public String getNroDocumentoBarcodeLabel() {
		return "N° documento";
	}
	
	public void manageErroriFile(HashMap<String, String> erroriFile) {
		if(erroriFile != null && !erroriFile.isEmpty()) {
			for(String key : erroriFile.keySet()) {
				if(key != null && !"".equals(key) && !"0".equals(key)) {
					AllegatoCanvas lAllegatoCanvas = getAllegatoCanvasFromNumero(key);
					lAllegatoCanvas.showErrorFile(erroriFile.get(key));
					lAllegatoCanvas.markForRedraw();
				}
			}
		}
	}
	
	public AllegatoCanvas getAllegatoCanvasFromNumero(String numero) {
		VLayout lVLayout = getVLayout();
		int numeroAllegato = 0;
		for (int i = 0; i < getTotalMembers(); i++) {
			numeroAllegato++;
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) getReplicableCanvas(lHLayout);
			if (numero != null && !"".equals(numero) && Integer.parseInt(numero) == numeroAllegato) {
				return lAllegatoCanvas;				
			}
		}
		return null;
	}
	
	public AllegatoCanvas getAllegatoCanvasFromTipo(String tipo) {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) getReplicableCanvas(lHLayout);
			Record lAllegatoRecord = lAllegatoCanvas.getFormValuesAsRecord();
			String tipoAllegato = lAllegatoRecord.getAttribute("listaTipiFileAllegato");
			if (tipoAllegato != null && tipoAllegato.equals(tipo)) {
				if (!readOnly || lAllegatoCanvas.isNewOrSavedInTaskCorrente()) {
					return lAllegatoCanvas;
				}
			}
		}
		return null;
	}
	
	// se restituisce false si può aggiungere un allegato anche senza dover mettere il file, se è presente un tipo o la descrizione
	// se true invece il file è sempre obbligatorio, quando è presente un tipo o la descrizione
	public boolean isObbligatorioFile() {
		return false;
	}

	public boolean validateFormatoFileAllegato(InfoFileRecord infoFileAllegato) {
		return true;
	}
	
	public void preimpostaApponiTimbroFromEmail() {
		if (getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ReplicableCanvas) {
							AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) lHLayoutMember;
							lAllegatoCanvas.preimpostaApponiTimbroFromEmail();
						}
					}
				}
			}
		}
	}
	
	public void preimpostaApponiTimbroXProtBozza() {
		if (getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ReplicableCanvas) {
							AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) lHLayoutMember;
							lAllegatoCanvas.preimpostaApponiTimbroXProtBozza();
						}
					}
				}
			}
		}
	}

	public String getFormatoFileNonValidoErrorMessage() {
		return "Formato file non valido";
	}

	private List<ReplicableCanvas> listaReplicableCanvasCaricatiInUploadMultiplo = new ArrayList<ReplicableCanvas>();
	
	@Override
	public HLayout createAddButtonsLayout() {
		HLayout addButtonsLayout = super.createAddButtonsLayout();

		NestedFormItem addButtons = new NestedFormItem("add");
		addButtons.setWidth(40);
		addButtons.setNumCols(2);
		addButtons.setColWidths(16, 16);

		uploadButton = new FileMultipleUploadItemWithFirmaAndMimeType(new UploadMultipleItemCallBackHandler() {

			@Override
			public void uploadEnd(String displayFileName, String uri, String numFileCaricatiInUploadMultiplo) {
				ReplicableCanvas lReplicableCanvas;
				AllegatoCanvas lastCanvas = (AllegatoCanvas) getLastCanvas();
				if (lastCanvas != null && !lastCanvas.isChangedAndValid()) {
					lReplicableCanvas = lastCanvas;
					lastCanvasToReplicate = lastCanvas;
				} else {					
					lReplicableCanvas = (AllegatoCanvas) onClickNewButton();
					if(lastCanvasToReplicate != null) { 
						getFileForm(lReplicableCanvas).setValues(getFileForm(lastCanvasToReplicate).getValues());						
					}
				}
				getFileForm(lReplicableCanvas).setValue("nomeFileAllegato", displayFileName);
				getFileForm(lReplicableCanvas).setValue("uriFileAllegato", uri);
				getFileForm(lReplicableCanvas).setValue("nomeFileAllegatoTif", "");
				getFileForm(lReplicableCanvas).setValue("uriFileAllegatoTif", "");
				getFileForm(lReplicableCanvas).setValue("remoteUri", false);
				getFileForm(lReplicableCanvas).setValue("nomeFileVerPreFirma", displayFileName);
				getFileForm(lReplicableCanvas).setValue("uriFileVerPreFirma", uri);
				getFileForm(lReplicableCanvas).setValue("isUdSenzaFileImportata", false);
				getFileForm(lReplicableCanvas).setValue("flgTimbraFilePostReg", false);
				getFileForm(lReplicableCanvas).setValue("flgTimbraFilePostRegOmissis", false);
				getFileForm(lReplicableCanvas).setValue("numFileCaricatiInUploadMultiplo", numFileCaricatiInUploadMultiplo);
				((AllegatoCanvas) lReplicableCanvas).changedEventAfterUpload(displayFileName, uri);
//				uploadButton.redrawPrettyFileMultipleUploadInput();
				// mi recupero il numero totale di file selezionati in upload
				int numFileCaricati = numFileCaricatiInUploadMultiplo != null && !"".equals(numFileCaricatiInUploadMultiplo) ? Integer.parseInt(numFileCaricatiInUploadMultiplo) : 0;
				// se il numero è maggiore di 1, e quindi si tratta di un upload multiplo
				if(numFileCaricati > 1) {
					// mi recupero il numero progressivo del file di cui sto facendo l'upload
					int nroProgrFile = listaReplicableCanvasCaricatiInUploadMultiplo.size() + 1;
					if(nroProgrFile == 1) {
						// a partire dal primo upload mostro la waitPopup
						Layout.showWaitPopup("Caricamento file in corso...");
						numFileCaricatiInUploadMultiploGlobal = numFileCaricati;
					}
					// se non ho ancora terminato l'upload di tutti i file
					if(nroProgrFile <= numFileCaricati) {
						// aggiungo il mio lReplicableCanvas a listaReplicableCanvasCaricatiInUploadMultiplo												
						listaReplicableCanvasCaricatiInUploadMultiplo.add(lReplicableCanvas);						
					}
				}
				// se invece è un upload singolo
				else {
					listaReplicableCanvasCaricatiInUploadMultiplo = new ArrayList<ReplicableCanvas>();
				}
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));				
				manageOnChanged();
				uploadButton.getCanvas().redraw();
				
				numFileCaricatiInErrore++;
				int nroProgrFile = listaReplicableCanvasCaricatiInUploadMultiplo.size();
				if(nroProgrFile + numFileCaricatiInErrore == numFileCaricatiInUploadMultiploGlobal) {
					// faccio i controlli su tutta la lista di file che ho aggiunto con l'upload multiplo
					controlAllAfterUploadMultiplo();
					// resetto listaRecordCaricatiInUploadMultiplo
					listaReplicableCanvasCaricatiInUploadMultiplo = new ArrayList<ReplicableCanvas>();
					numFileCaricatiInErrore = 0;
				}
			}
			
			@Override
			public void cancelMultipleUpload() {
//				SC.say("Upload annullato");
				listaReplicableCanvasCaricatiInUploadMultiplo = new ArrayList<ReplicableCanvas>();
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				AllegatoCanvas lLastReplicableCanvas = (AllegatoCanvas) getLastCanvas();
				// mi recupero il numero totale di file selezionati in upload (lo devo fare prima di lLastReplicableCanvas.manageChangedFileAllegato() perchè lì viene resettato a 1)				
				int numFileCaricati = getFileForm(lLastReplicableCanvas).getValue("numFileCaricatiInUploadMultiplo") != null && !"".equals(getFileForm(lLastReplicableCanvas).getValue("numFileCaricatiInUploadMultiplo")) ? Integer.parseInt((String) getFileForm(lLastReplicableCanvas).getValue("numFileCaricatiInUploadMultiplo")) : 0;
				InfoFileRecord precInfo = getFileForm(lLastReplicableCanvas).getValue("infoFile") != null ? new InfoFileRecord(getFileForm(lLastReplicableCanvas).getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				getFileForm(lLastReplicableCanvas).setValue("infoFile", info);
				getFileForm(lLastReplicableCanvas).setValue("improntaVerPreFirma", info.getImpronta());
				getFileForm(lLastReplicableCanvas).setValue("infoFileVerPreFirma", info);				
				String displayName = getFileForm(lLastReplicableCanvas).getValueAsString("nomeFileAllegato");
				String displayNameOrig = getFileForm(lLastReplicableCanvas).getValueAsString("nomeFileAllegatoOrig");
				if (precImpronta == null
						|| !precImpronta.equals(info.getImpronta())
						|| (displayName != null && !"".equals(displayName) && displayNameOrig != null && !"".equals(displayNameOrig) && !displayName
								.equals(displayNameOrig))) {
//					getFileForm(lLastReplicableCanvas).setValue("isChanged", true);
					lLastReplicableCanvas.manageChangedFileAllegato();
				}				
//				controlAfterUpload(info, displayName);
				lLastReplicableCanvas.getAllegatoButtons().redraw();
				manageOnChanged();
				if(listaReplicableCanvasCaricatiInUploadMultiplo.size() > 0) {
					// mi recupero il numero progressivo del file di cui ho appena fatto l'upload
					int nroProgrFile = listaReplicableCanvasCaricatiInUploadMultiplo.size();	
					// se sono arrivato alla fine e ho ancora terminato l'upload di tutti i file
					if(nroProgrFile == numFileCaricati) {
						// nascondo la waitPopup
//						Layout.hideWaitPopup(); // lo faccio in controlAllAfterUploadMultiplo
						// faccio i controlli su tutta la lista di file che ho aggiunto con l'upload multiplo
						controlAllAfterUploadMultiplo();
						// resetto listaRecordCaricatiInUploadMultiplo
						listaReplicableCanvasCaricatiInUploadMultiplo = new ArrayList<ReplicableCanvas>();
					}
				} 
				// se è un upload singolo con updateData() aggiorno direttamente le info del file di cui ho appena fatto l'upload
				else {
					// faccio i controlli sul file di cui ho appena fatto l'upload
					controlAfterUpload(info, displayName);
					// resetto listaRecordCaricatiInUploadMultiplo
					listaReplicableCanvasCaricatiInUploadMultiplo = new ArrayList<ReplicableCanvas>();
				}
			}
		});
		if (!AurigaLayout.getIsAttivaAccessibilita()) {
			uploadButton.setCanFocus(false);
			uploadButton.setTabIndex(-1);
		}

		importaFileDocumentiBtn = new ImgButtonItem("importaFileDocumentiBtn", "buttons/importaFileDocumenti.png", getImportaFileDocumentiBtnTitle());

		importaFileDocumentiBtn.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				ImportaAllegatiMultiLookupArchivio lookupArchivioPopup = new ImportaAllegatiMultiLookupArchivio(null);
				lookupArchivioPopup.show();
			}
		});

		importaFileDocumentiBtn.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_IMP_FILE_ALTRI_DOC") && showImportaFileDaDocumenti;
			}
		});

		collegaDocumentiImportatiCbx = new CheckboxItem("collegaDocumentiImportati", I18NUtil.getMessages()
				.protocollazione_detail_collegaDocumentiFileImportati_title());
		collegaDocumentiImportatiCbx.setValue(true);
		collegaDocumentiImportatiCbx.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_IMP_FILE_ALTRI_DOC") && showImportaFileDaDocumenti && showCollegaDocumentiImportati) {
					ReplicableCanvas[] allCanvas = getAllCanvas();
					boolean fileImportatiPresenti = false;
					for (ReplicableCanvas replicableCanvas : allCanvas) {
						boolean fileImportato = Boolean.parseBoolean(getFileForm(replicableCanvas).getValueAsString("fileImportato"));
						if (fileImportato) {
							fileImportatiPresenti = true;
						}
					}
					// Se non ci sono file importati resetto la check box
					if (!fileImportatiPresenti) {
						collegaDocumentiImportatiCbx.setValue(true);
					}
					return fileImportatiPresenti;
				}
				return false;
			}
		});
		collegaDocumentiImportatiCbx.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null) {
					ReplicableCanvas[] allCanvas = getAllCanvas();
					for (ReplicableCanvas replicableCanvas : allCanvas) {
						boolean fileImportato = Boolean.parseBoolean(getFileForm(replicableCanvas).getValueAsString("fileImportato"));
						if (fileImportato) {
							getFileForm(replicableCanvas).setValue("collegaDocumentoImportato", event.getValue() != null && (Boolean) event.getValue());
						}
					}
				}
			}
		});

		addButtons.setNestedFormItems(uploadButton, importaFileDocumentiBtn);
		
		addButtonsForm = new DynamicForm();
		addButtonsForm.setNumCols(10);
		addButtonsForm.setMargin(0);

		if(isShowFlgPubblicaSeparato() && isFlgPubblicazioneAllegatiUguale()) { 
			CheckboxItem flgPubblicaAllegatiSeparatiItem = new CheckboxItem("flgPubblicaAllegatiSeparati", getTitleFlgPubblicaSeparato());
			flgPubblicaAllegatiSeparatiItem.setValue(getFlgAllegAttoPubblSepDefault());
			flgPubblicaAllegatiSeparatiItem.setColSpan(1);
			flgPubblicaAllegatiSeparatiItem.setWidth("*");
			// flgPubblicaAllegatiSeparatiItem.setLabelAsTitle(true);
			flgPubblicaAllegatiSeparatiItem.setShowTitle(true);
			flgPubblicaAllegatiSeparatiItem.setWrapTitle(false);						
			addButtonsForm.setFields(addButtons, collegaDocumentiImportatiCbx, flgPubblicaAllegatiSeparatiItem);
		} else {
			addButtonsForm.setFields(addButtons, collegaDocumentiImportatiCbx);
		}
		
		addButtonsLayout.addMember(addButtonsForm);

		return addButtonsLayout;

	}
	
	public void controlAllAfterUploadMultiplo() {
		final List<MessageBean> listaWarningMessages = new ArrayList<MessageBean>();
		if (getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ReplicableCanvas) {
							AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) lHLayoutMember;
							String uri = lAllegatoCanvas != null && getFileForm(lAllegatoCanvas).getValue("uriFileAllegato") != null ? getFileForm(lAllegatoCanvas).getValueAsString("uriFileAllegato") : null;
							if(uri != null && !"".equals(uri)) {				
								String displayName = lAllegatoCanvas != null && getFileForm(lAllegatoCanvas).getValue("nomeFileAllegato") != null ? getFileForm(lAllegatoCanvas).getValueAsString("nomeFileAllegato") : null;
								InfoFileRecord info = getFileForm(lAllegatoCanvas).getValue("infoFile") != null ? new InfoFileRecord(getFileForm(lAllegatoCanvas).getValue("infoFile")) : null;
								manageControlAfterUpload(info, lAllegatoCanvas, displayName, listaWarningMessages);
							}
						}
					}
				}
			}
		}		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {
				Layout.hideWaitPopup();
				if(listaWarningMessages != null && listaWarningMessages.size() > 0) {	
					if(listaWarningMessages.size() == 1) {
						GWTRestDataSource.printMessage(listaWarningMessages.get(0));
					} else {
						RecordList listaWarnings = new RecordList();
						for(int i = 0; i < listaWarningMessages.size(); i++) {
							Record warningRecord = new Record();
							warningRecord.setAttribute("warning", listaWarningMessages.get(i).getMessage());
							listaWarnings.add(warningRecord);
						}
						ListaWarningsPopup listaWarningsPopup = new ListaWarningsPopup("Avvertimenti", "Il caricamento dei file è stato eseguito con alcuni warning. Di seguito il dettaglio: ", listaWarnings, 600, 300);
						listaWarningsPopup.show();
					}
				}
			}
		});		
	}
	
	public void controlAfterUpload(InfoFileRecord info, String displayName) {	
		AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) getLastCanvas();		
		manageControlAfterUpload(info, lAllegatoCanvas, displayName, null);
	}
	
	public void manageControlAfterUpload(InfoFileRecord info, AllegatoCanvas lAllegatoCanvas, String displayName, List<MessageBean> listaWarningMessages) {	
		boolean flgParteDispositivo = lAllegatoCanvas != null && getFileForm(lAllegatoCanvas).getValue("flgParteDispositivo") != null && new Boolean(getFileForm(lAllegatoCanvas).getValueAsString("flgParteDispositivo"));
		boolean flgNoPubblAllegato = lAllegatoCanvas != null && getFileForm(lAllegatoCanvas).getValue("flgNoPubblAllegato") != null	&& new Boolean(getFileForm(lAllegatoCanvas).getValueAsString("flgNoPubblAllegato"));
		boolean flgDatiSensibili = lAllegatoCanvas != null && getFileForm(lAllegatoCanvas).getValue("flgDatiSensibili") != null	&& new Boolean(getFileForm(lAllegatoCanvas).getValueAsString("flgDatiSensibili"));
		boolean showFileOmissis = getShowVersioneOmissis() && flgDatiSensibili;
//		boolean flgPubblicaSeparato = false;
//		if(isShowFlgPubblicaSeparato()) {
//			if(isFlgPubblicazioneAllegatiUguale()) {
//				flgPubblicaSeparato = isFlgPubblicaAllegatiSeparati();
//			} else {
//				flgPubblicaSeparato = lAllegatoCanvas != null && getFileForm(lAllegatoCanvas).getValue("flgPubblicaSeparato") != null && new Boolean(getFileForm(lAllegatoCanvas).getValueAsString("flgPubblicaSeparato"));	
//			}
//		}
		if (!isFormatoAmmesso(info)) {			
			MessageBean message = new MessageBean("Il file " + displayName + " risulta in un formato non ammesso", "", MessageType.WARNING);
			if(listaWarningMessages != null) listaWarningMessages.add(message); 
			else GWTRestDataSource.printMessage(message);
		} else if(flgParteDispositivo && !isFormatoAmmessoParteIntegrante(info)) {
			MessageBean message = new MessageBean("Il file " + displayName + " risulta in un formato non ammesso per un allegato parte integrante", "", MessageType.WARNING);
			if(listaWarningMessages != null) listaWarningMessages.add(message); 
			else GWTRestDataSource.printMessage(message);
//			lAllegatoCanvas.clickEliminaFile();
		}
		if(flgParteDispositivo && !PreviewWindow.isPdfConvertibile(info) /*&& !flgPubblicaSeparato*/) {
			if(isFromAllegatoDetailInGridItem()) {
				getFileForm(lAllegatoCanvas).setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
			} 
			if(isShowFlgPubblicaSeparato()) {
				if(isFlgPubblicazioneAllegatiUguale()) {
					setFlgPubblicaAllegatiSeparati(true);
				} else {
					getFileForm(lAllegatoCanvas).setValue("flgPubblicaSeparato", true);
				}
			}
			MessageBean message = new MessageBean("File " + displayName + " non convertibile in formato pdf: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING);
			if(listaWarningMessages != null) listaWarningMessages.add(message); 
			else GWTRestDataSource.printMessage(message);
		}
		if (info.isFirmato()) {
			String rifiutoAllegatiConFirmeNonValide = getRifiutoAllegatiConFirmeNonValide();
			if(!info.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null && !"".equals(rifiutoAllegatiConFirmeNonValide)) {
				if("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
					flgParteDispositivo = false;
					getFileForm(lAllegatoCanvas).setValue("flgParteDispositivo", false);		
					flgNoPubblAllegato = true;
					getFileForm(lAllegatoCanvas).setValue("flgNoPubblAllegato", true);
//					flgPubblicaSeparato = false;
					getFileForm(lAllegatoCanvas).setValue("flgPubblicaSeparato", false);
					if(showFileOmissis) {
						flgDatiSensibili = false;
						showFileOmissis = false;
						getFileForm(lAllegatoCanvas).setValue("flgDatiSensibili", false);	
						lAllegatoCanvas.manageOnChangedFlgDatiSensibili();						
					}
					MessageBean message = new MessageBean("Il file presenta firme non valide alla data odierna e non può essere caricato come allegato parte integrante: è stato automaticamente inserito come allegato NON parte integrante", "", MessageType.WARNING);
					if(listaWarningMessages != null) listaWarningMessages.add(message); 
					else GWTRestDataSource.printMessage(message);
				} else {
					MessageBean message = new MessageBean("Il file presenta firme non valide alla data odierna", "", MessageType.WARNING);
					if(listaWarningMessages != null) listaWarningMessages.add(message); 
					else GWTRestDataSource.printMessage(message);
				}
			} else if(isDisattivaUnioneAllegatiFirmati()) {
				if(flgParteDispositivo && !flgNoPubblAllegato) {
					if(isFromAllegatoDetailInGridItem()) {
						getFileForm(lAllegatoCanvas).setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
					} 
					if(isShowFlgPubblicaSeparato()) {
						if(isFlgPubblicazioneAllegatiUguale()) {
							setFlgPubblicaAllegatiSeparati(true);
						} else {
							getFileForm(lAllegatoCanvas).setValue("flgPubblicaSeparato", true);														
						}
					}							
//					MessageBean message = new MessageBean("Il file " + displayName + " è firmato. Si consiglia pubblicazione separata.", "", MessageType.WARNING);
//					if(listaWarningMessages != null) listaWarningMessages.add(message); 
//					else GWTRestDataSource.printMessage(message);
				}
			}
		}		
		if(flgParteDispositivo && isPubblicazioneSeparataPdfProtetti() && info.isPdfProtetto() /*&& !flgPubblicaSeparato*/) {
			if(isFromAllegatoDetailInGridItem()) {
				getFileForm(lAllegatoCanvas).setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
			} 
			if(isShowFlgPubblicaSeparato()) {
				if(isFlgPubblicazioneAllegatiUguale()) {
					setFlgPubblicaAllegatiSeparati(true);
				} else {
					getFileForm(lAllegatoCanvas).setValue("flgPubblicaSeparato", true);
				}
			}
			MessageBean message = new MessageBean("File " + displayName + " protetto da qualsiasi modifica: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING);
			if(listaWarningMessages != null) listaWarningMessages.add(message); 
			else GWTRestDataSource.printMessage(message);
		}
		final float MEGABYTE = 1024L * 1024L;
		long dimAlertAllegato = getDimAlertAllegato(); // questo è in bytes
		long dimMaxAllegatoXPubblInMB = getDimMaxAllegatoXPubbl(); // questa è in MB
		if(dimMaxAllegatoXPubblInMB > 0 && info != null && info.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE)) {						
			if(flgParteDispositivo) {
				if(isShowFlgNoPubblAllegato()) {
					getFileForm(lAllegatoCanvas).setValue("flgNoPubblAllegato", true);		
				}
				if(isFromAllegatoDetailInGridItem()) {
					getFileForm(lAllegatoCanvas).setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
				} 
				if(isShowFlgPubblicaSeparato()) {
					if(isFlgPubblicazioneAllegatiUguale()) {
						setFlgPubblicaAllegatiSeparati(true);
					} else {
						getFileForm(lAllegatoCanvas).setValue("flgPubblicaSeparato", true);														
					}
				}				
				MessageBean message = new MessageBean("La dimensione del file " + displayName + " è superiore alla soglia di " + dimMaxAllegatoXPubblInMB + " MB consentita per la pubblicazione", "", MessageType.WARNING);
				if(listaWarningMessages != null) listaWarningMessages.add(message); 
				else GWTRestDataSource.printMessage(message);
			}	
		} else if(dimAlertAllegato > 0 && info != null && info.getBytes() > dimAlertAllegato) {
			float dimensioneInMB = info.getBytes() / MEGABYTE;						
			if(flgParteDispositivo && !flgNoPubblAllegato) {
				if(isFromAllegatoDetailInGridItem()) {
					getFileForm(lAllegatoCanvas).setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
				} 
				if(isShowFlgPubblicaSeparato()) {
					if(isFlgPubblicazioneAllegatiUguale()) {
						setFlgPubblicaAllegatiSeparati(true);
					} else {
						getFileForm(lAllegatoCanvas).setValue("flgPubblicaSeparato", true);														
					}
				}
				MessageBean message = new MessageBean("Attenzione: la dimensione del file " + displayName + " è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB. Si consiglia pubblicazione separata.", "", MessageType.WARNING);
				if(listaWarningMessages != null) listaWarningMessages.add(message); 
				else GWTRestDataSource.printMessage(message);
			} else {				
				MessageBean message = new MessageBean("Attenzione: la dimensione del file " + displayName + " è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB", "", MessageType.WARNING);
				if(listaWarningMessages != null) listaWarningMessages.add(message); 
				else GWTRestDataSource.printMessage(message);
			}							
		}
	}

	public GWTRestDataSource getTipiFileAllegatoDataSource() {
		String idProcess = getIdProcess();
		String idProcessType = getIdProcessType();
		if ((idProcess != null && !"".equals(idProcess)) || (idProcessType != null && !"".equals(idProcessType))) {
			GWTRestDataSource lLoadComboTipoDocInProcessDataSource = new GWTRestDataSource("LoadComboTipoDocInProcessDataSource", "idTipoDoc", FieldType.TEXT,
					true);
			lLoadComboTipoDocInProcessDataSource.addParam("idProcess", idProcess);
			lLoadComboTipoDocInProcessDataSource.addParam("idProcessType", idProcessType);
			return lLoadComboTipoDocInProcessDataSource;
		}
		return new GWTRestDataSource("LoadComboTipoFileAllegatoDataSource", "idTipoFileAllegato", FieldType.TEXT, true);
	}

	public boolean showFilterEditorInTipiFileAllegato() {
		String idProcess = getIdProcess();
		if (idProcess != null && !"".equals(idProcess)) {
			return false;
		}
		return true;
	}

	@Override
	public void storeValue(RecordList values) {
		super.storeValue(values);
		if (addButtonsForm != null) {
			addButtonsForm.markForRedraw();
		}
	}

	public boolean canBeEditedByApplet() {
		return false;
	}

	public boolean showNumeroAllegato() {
		return true;
	}

	public boolean showTipoAllegato() {
		return true;
	}

	public boolean showDescrizioneFileAllegato() {
		return true;
	}

	public boolean showNomeFileAllegato() {
		return true;
	}

	public boolean showGeneraDaModello() {
		return false;
	}
	
	public boolean isModelloModificabile(String idModello) {
		return true;
	}
	
	public boolean showVisualizzaFileUdButton() {
		return false;
	}
	
	public boolean isDocumentiInizialiIstanza() {
		return false;
	}
	
	public boolean isDocumentiIstruttoria() {
		return false;
	}
	
	public boolean isDocPraticaVisure() {
		return false;
	}
		
	public boolean isDocCedAutotutela() {
		return false;
	}
	
	public boolean isDocIstruttoriaDettFascicoloGenCompleto() {
		return false;
	}
	
	public String getEmailContribuente() {
		return null;
	}
	
	public String getFlgTipoProvProtocollo() {
		return null;
	}

	public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
		final Record modelloRecord = new Record();
		modelloRecord.setAttribute("idModello", idModello);
		modelloRecord.setAttribute("tipoModello", tipoModello);
		modelloRecord.setAttribute("idUd", detailRecord != null ? detailRecord.getAttribute("idUd") : null);
		return modelloRecord;
	}
	
	public void caricaModelloAllegato(String idModello, String tipoModello, String flgConvertiInPdf, String idUd, final ServiceCallback<Record> callback) {
		caricaModelloAllegato(idModello, tipoModello, flgConvertiInPdf, callback);
	}

	public void caricaModelloAllegato(String idModello, String tipoModello, String flgConvertiInPdf, final ServiceCallback<Record> callback) {

		final GWTRestDataSource lGeneraDaModelloWithDatiDocDataSource = new GWTRestDataSource("GeneraDaModelloWithDatiDocDataSource");
		lGeneraDaModelloWithDatiDocDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
		lGeneraDaModelloWithDatiDocDataSource.executecustom("caricaModello", getRecordCaricaModelloAllegato(idModello, tipoModello), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (callback != null) {
						callback.execute(response.getData()[0]);
					}
				}
			}
		});
	}
	
	public Date getDataRifValiditaFirma() {
		String idProcess = getIdProcess();
		if(idProcess == null || "".equals(idProcess)) {
			Record recordProtocollo = getDetailRecord();
			return recordProtocollo != null ? recordProtocollo.getAttributeAsDate("dataProtocollo") : null;
		}				
		return null;
	}
	
	public String getEstremiProtocollo() {
		String estremiProtocollo = null;
		Record recordProtocollo = getDetailRecord();
		if (recordProtocollo != null) {
			estremiProtocollo = recordProtocollo.getAttribute("siglaProtocollo") + " " + recordProtocollo.getAttribute("nroProtocollo") +
					getSuffixSubProtocollo(recordProtocollo.getAttribute("subProtocollo")) + " " + DateUtil.formatAsShortDatetime(recordProtocollo.getAttributeAsDate("dataProtocollo"));
		} 
		return estremiProtocollo;
	}
	
	protected String getSuffixSubProtocollo(String subProtocollo) {
		return subProtocollo != null && !"".equals(subProtocollo) ? "." + subProtocollo : "";		
	}

	public void visualizzaVersioni(Record allegatoRecord) {
		String nroAllegato = allegatoRecord.getAttributeAsString("numeroProgrAllegato");
		String idDocAllegato = allegatoRecord.getAttributeAsString("idDocAllegato");		
		ProtocollazioneDetail.visualizzaVersioni(idDocAllegato, "A", nroAllegato, getEstremiProtocollo(), getDetailRecord());
	}
	
	public boolean showUpload() {
		return true;
	}

	public boolean showAcquisisciDaScanner() {
		return true;
	}
	
	public boolean isAttivaTimbraturaFilePostRegAllegato() {
		return false; //return AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_FILE_POST_REG");
	}
	
	public boolean sonoInMail() {
		return false;
	}
	
	public boolean isScansioneImgAssoc() {
		return false;
	}
		
	public void clickTrasformaInPrimario(HLayout lHLayout) {
		int number = -1;
		// Recupero il layout
		VLayout lVLayout = (VLayout) getCanvas();
		if (lHLayout == null)
			number = 0;
		else {
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				if (lVLayout.getMember(i).getID().equals(lHLayout.getID())) {
					number = i;
				}
			}
		}
		clickTrasformaInPrimario(number);

	}

	public void clickTrasformaInPrimario(int index) {

	}

	public void mostraVariazione(int nroAllegato, String datoAnnullato) {
		if (getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ReplicableCanvas) {
							AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) lHLayoutMember;
							if (lAllegatoCanvas.getCounter() == nroAllegato) {
								lAllegatoCanvas.mostraVariazione(datoAnnullato);
								getForm().show();
							}
						}
					}
				}
			}
		}
	}
	
	public boolean isFlgOmissisChecked() {
		if (getCanvas() != null) {
			final VLayout lVLayout = getVLayout();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
							if (lHLayoutMember instanceof AllegatoCanvas) {
								AllegatoCanvas allCanvas = (AllegatoCanvas)lHLayoutMember;
								if(allCanvas.isFlgOmissis()) {
									return true;
								}
							}
						
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setReadOnly(false);
		setSoloPreparazioneVersPubblicazione(false);
		setSoloOmissisModProprietaFile(false);
		setAttivaModificaEsclusionePubblicazione(false);
		setAggiuntaFile(false);
		setCanEditOnlyOmissis(false);
		super.setCanEdit(canEdit);
		if (getCanvas() != null) {
			final VLayout lVLayout = getVLayout();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ReplicableCanvas) {
							ReplicableCanvas lReplicableCanvas = (ReplicableCanvas) lHLayoutMember;
							lReplicableCanvas.setCanEdit(canEdit);
						} else {
							if(lHLayoutMember instanceof DynamicForm) {
								FormItem addButtons = ((DynamicForm)lHLayoutMember).getItem("add");
								if(addButtons != null) {									
									if (canEdit) {
										addButtons.show();
									} else {
										addButtons.hide();
									}
								}
							} else {
								if (canEdit) {
									if(lHLayoutMember.getParentElement() != null) {
										lHLayoutMember.show();
									} else {
										lHLayoutMember.hide();
									}
								} else {
									lHLayoutMember.hide();
								}
							} 							
						}
					}
				}
			}
		}
	}
	
	public void readOnlyMode() {
		setCanEdit(true);
		setReadOnly(true);
		if (getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if(lHLayoutMember instanceof DynamicForm) {
							FormItem addButtons = ((DynamicForm)lHLayoutMember).getItem("add");
							if(addButtons != null) {
								if (getShowNewButton()) {
									addButtons.show();
								} else {
									addButtons.hide();
								}
							}
						} else if (lHLayoutMember instanceof ImgButton) {
							if (i == (lVLayout.getMembers().length - 1)) {
								// se è un bottone di add lo mostro
								if (getShowNewButton()) {
									lHLayoutMember.show();
								} else {
									lHLayoutMember.hide();
								}
							}
						} else if (lHLayoutMember instanceof ReplicableCanvas) {
							AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) lHLayoutMember;
							lAllegatoCanvas.readOnlyMode();
						}
					}
				}
			}
		}
	}
	
	public void soloPreparazioneVersPubblicazioneMode() {
		setCanEdit(true);
		setSoloPreparazioneVersPubblicazione(true);
		if (getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if(lHLayoutMember instanceof DynamicForm) {
							FormItem addButtons = ((DynamicForm)lHLayoutMember).getItem("add");
							if(addButtons != null) {
								addButtons.hide();								
							}
						} else if (lHLayoutMember instanceof ImgButton) {
							if (i == (lVLayout.getMembers().length - 1)) {
								// se è un bottone di add lo nascondo
								lHLayoutMember.hide();
							} else if (lHLayoutMember instanceof RemoveButton) {
								// se è un bottone di remove lo disabilito
								((RemoveButton) lHLayoutMember).setAlwaysDisabled(true);
							}
						} else if (lHLayoutMember instanceof ReplicableCanvas) {
							AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) lHLayoutMember;
							lAllegatoCanvas.soloPreparazioneVersPubblicazioneMode();
						}
					}
				}
			}
		}		
	}
	
	public void soloOmissisModProprietaFileMode() {
		setCanEdit(true);
		setSoloOmissisModProprietaFile(true);		
		if (getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if(lHLayoutMember instanceof DynamicForm) {
							FormItem addButtons = ((DynamicForm)lHLayoutMember).getItem("add");
							if(addButtons != null) {
								if (!isAllegatiNonParteIntegranteNonEditabili() && getShowNewButton()) {
									addButtons.show();
								} else {
									addButtons.hide();
								}	
							}
						} else if (lHLayoutMember instanceof ImgButton) {
							if (i == (lVLayout.getMembers().length - 1)) {
								// se è un bottone di add lo mostro
								if (!isAllegatiNonParteIntegranteNonEditabili() && getShowNewButton()) {
									lHLayoutMember.show();
								} else {
									lHLayoutMember.hide();
								}
							} else if (lHLayoutMember instanceof RemoveButton) {
								// se è un bottone di remove lo disabilito
								((RemoveButton) lHLayoutMember).setAlwaysDisabled(true);
							}
						} else if (lHLayoutMember instanceof ReplicableCanvas) {
							AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) lHLayoutMember;
							lAllegatoCanvas.soloOmissisModProprietaFileMode();
						}
					}
				}
			}
		}		
	}
	
	public void attivaModificaEsclusionePubblicazioneMode() {
		setAttivaModificaEsclusionePubblicazione(true);
		if (getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ReplicableCanvas) {
							AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) lHLayoutMember;
							lAllegatoCanvas.attivaModificaEsclusionePubblicazioneMode();
						}
					}
				}
			}
		}
	}

	public void setAggiuntaFileMode() {
		setCanEdit(true);
		setAggiuntaFile(true);
		if (getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if(lHLayoutMember instanceof DynamicForm) {
							FormItem addButtons = ((DynamicForm)lHLayoutMember).getItem("add");
							if(addButtons != null) {
								if (getShowNewButton()) {
									addButtons.show();
								} else {
									addButtons.hide();
								}
							}
						} else if (lHLayoutMember instanceof ImgButton) {
							if (i == (lVLayout.getMembers().length - 1)) {
								// se è un bottone di add lo mostro
								if (getShowNewButton()) {
									lHLayoutMember.show();
								} else {
									lHLayoutMember.hide();
								}
							} else if (lHLayoutMember instanceof RemoveButton) {
								// se è un bottone di remove lo disabilito
								((RemoveButton) lHLayoutMember).setAlwaysDisabled(true);
							}
						} else if (lHLayoutMember instanceof ReplicableCanvas) {
							AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) lHLayoutMember;
							lAllegatoCanvas.aggiuntaFileMode();
						}
					}
				}
			}
		}
	}
	
	public void canEditOnlyOmissisMode() {
		setCanEdit(false);
		setCanEditOnlyOmissis(true);
		if (getCanvas() != null) {
			final VLayout lVLayout = getVLayout();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ReplicableCanvas) {
							ReplicableCanvas lReplicableCanvas = (ReplicableCanvas) lHLayoutMember;
							lReplicableCanvas.setCanEdit(false);
							if (lHLayoutMember instanceof AllegatoCanvas) {
								AllegatoCanvas allCanvas = (AllegatoCanvas)lHLayoutMember;
								allCanvas.canEditOnlyOmissisMode();
							} 
						} else if (lHLayoutMember instanceof ImgButton) {
							if (lHLayoutMember instanceof RemoveButton) {
								// se è un bottone di remove lo disabilito
								((RemoveButton) lHLayoutMember).setAlwaysDisabled(true);
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void updateMode() {
		if(isReadOnly()) {
			readOnlyMode();
		} 
		if(isSoloPreparazioneVersPubblicazione()) {
			soloPreparazioneVersPubblicazioneMode();
		}
		if(isSoloOmissisModProprietaFile()) {
			soloOmissisModProprietaFileMode();
		}
		if(isAttivaModificaEsclusionePubblicazione()) {
			attivaModificaEsclusionePubblicazioneMode();
		}
		if(isAggiuntaFile()) {
			setAggiuntaFileMode();
		}
		if(isCanEditOnlyOmissis()) {
			canEditOnlyOmissisMode();
		}
	}
	
	public boolean isFromAllegatoDetailInGridItem() {
		return false;
	}

	public Record getDetailRecord() {
		return detailRecord;
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}

	public void setNomeFileWidth(int nomeFileWidth) {
		this.nomeFileWidth = nomeFileWidth;
	}

	public Integer getNomeFileWidth() {
		return nomeFileWidth;
	}

	public String getIdTaskCorrenteAllegati() {
		return null;
	}

	public HashSet<String> getTipiModelliAttiAllegati() {
		return null;
	}

	public boolean sonoModificaVisualizza() {
		return false;
	}

	public void manageChangedFileAllegatiParteDispositivo() {

	}

	public void manageChangedFileAllegati() {

	}

	public boolean getFlgAllegAttoParteIntDefault() {
		return AurigaLayout.getParametroDBAsBoolean("FLG_ALLEG_ATTO_PARTE_INT_DEFAULT");
	}

	public String getTitleFlgParteDispositivo() {
		return I18NUtil.getMessages().protocollazione_flg_parte_dispositivo();
	}
	
	public String getTitleFlgNoPubblAllegato() {
		return I18NUtil.getMessages().protocollazione_flg_no_pubbl();
	}
	
	public boolean getFlgAllegAttoPubblSepDefault() {
		return AurigaLayout.getParametroDBAsBoolean("FLG_ALLEG_ATTO_PUBBL_SEPARATA_DEFAULT");
	}
	
	public String getTitleFlgPubblicaSeparato() {
		String labelFlgAllegatoPIAttoSeparato = AurigaLayout.getParametroDB("LABEL_FLG_ALLEGATO_PI_ATTO_SEPARATO");
		if(labelFlgAllegatoPIAttoSeparato != null && !"".equals(labelFlgAllegatoPIAttoSeparato)) {
			return labelFlgAllegatoPIAttoSeparato;
		}		
		return "da non unire al dispositivo";
	}	
	
	public String getTitleFlgDatiSensibili() {
		String labelFlgVerOmissis = AurigaLayout.getParametroDB("LABEL_FLG_VER_OMISSIS");
		if(labelFlgVerOmissis != null && !"".equals(labelFlgVerOmissis)) {
			return labelFlgVerOmissis;
		}		
		return "ver. con omissis per pubblicazione";
	}
	
	public String getTitleTipiFileAllegato() {
		return I18NUtil.getMessages().protocollazione_detail_tipoItem_title();
	}

	public String getTitleDescrizioneFileAllegato() {
		return I18NUtil.getMessages().protocollazione_detail_descrizioneItem_title();
	}

	public Integer getWidthDescrizioneFileAllegato() {
		return null;
	}

	public String getTitleNomeFileAllegato() {
		return I18NUtil.getMessages().protocollazione_detail_nomeFileAllegatoItem_title();
	}
	
	public boolean getShowTitleNomeFileAllegato() {
		return true;
	}
	
	public Integer getWidthNomeFileAllegato() {
		return null;
	}
	
	public String getTitleNomeFileOmissis() {
		return I18NUtil.getMessages().protocollazione_detail_nomeFileOmissisAllegatoItem_title();
	}
	
	public boolean getShowFlgDatiProtettiTipo1() {
		return false;
	}
	
	public String getTitleFlgDatiProtettiTipo1() {
		return "dati protetti tipo 1";
	}
	
	public boolean getShowFlgDatiProtettiTipo2() {
		return false;
	}
	
	public String getTitleFlgDatiProtettiTipo2() {
		return "dati protetti tipo 2";
	}
	
	public boolean getShowFlgDatiProtettiTipo3() {
		return false;
	}
	
	public String getTitleFlgDatiProtettiTipo3() {
		return "dati protetti tipo 3";
	}
	
	public boolean getShowFlgDatiProtettiTipo4() {
		return false;
	}
	
	public String getTitleFlgDatiProtettiTipo4() {
		return "dati protetti tipo 4";
	}
	
	public boolean getShowVersioneOmissis() {
		return AurigaLayout.getParametroDBAsBoolean("SHOW_VERS_CON_OMISSIS") /*&& !isShowFlgNoPubblAllegato()*/;
	}
	
	public boolean isAttivaGestSepAllegatiUD() {
		return false;
	}
	
	public long getDimAlertAllegato() {
		return -1;
	}
	
	public long getDimMaxAllegatoXPubbl() {
		return -1;
	}
	
	public String getIdProcess() {
		return null;
	}
	
	public String getIdFolder() {
		return null;
	}
	
	public String getIdDocFilePrimario(){
		return null;
	}
	
	public String getIdUd() {
		return null;
	}
	
	public String getIdProcessType() {
		return null;
	}
	
	public String getDictionaryEntrySezione() {
		return null;
	}

	public boolean isProtInModalitaWizard() {
		return false;
	}
	
	public boolean isCanaleSupportoDigitale() {
		return false;
	}

	public boolean isCanaleSupportoCartaceo() {
		return false;
	}
	
	public boolean isShowFlgProvEsterna() {
		return showFlgProvEsterna;
	}

	public void setShowFlgProvEsterna(boolean showFlgProvEsterna) {
		this.showFlgProvEsterna = showFlgProvEsterna;
	}
	
	public boolean isShowFlgParere() {
		return showFlgParere;
	}

	public void setShowFlgParere(boolean showFlgParere) {
		this.showFlgParere = showFlgParere;
	}
	
	public boolean isShowFlgParteDispositivo() {
		return showFlgParteDispositivo;
	}

	public void setShowFlgParteDispositivo(boolean showFlgParteDispositivo) {
		this.showFlgParteDispositivo = showFlgParteDispositivo;
	}

	public boolean isShowFlgNoPubblAllegato() {
		return showFlgNoPubblAllegato;
	}

	public void setShowFlgNoPubblAllegato(boolean showFlgNoPubblAllegato) {
		this.showFlgNoPubblAllegato = showFlgNoPubblAllegato;
	}
	
	public boolean isShowFlgPubblicaSeparato() {
		return showFlgPubblicaSeparato;
	}
	
	public boolean isShowFlgEscludiPubblicazione() {
		return showFlgEscludiPubblicazione;
	}
	
	public void setShowFlgPubblicaSeparato(boolean showFlgPubblicaSeparato) {
		this.showFlgPubblicaSeparato = showFlgPubblicaSeparato;
	}
	
	public boolean isFlgPubblicazioneAllegatiUguale() {
		return flgPubblicazioneAllegatiUguale;
	}

	public void setFlgPubblicazioneAllegatiUguale(boolean flgPubblicazioneAllegatiUguale) {
		this.flgPubblicazioneAllegatiUguale = flgPubblicazioneAllegatiUguale;
	}
	
	public boolean isFlgSoloPreparazioneVersPubblicazione() {
		return flgSoloPreparazioneVersPubblicazione;
	}
	
	public void setFlgSoloPreparazioneVersPubblicazione(boolean flgSoloPreparazioneVersPubblicazione) {
		this.flgSoloPreparazioneVersPubblicazione = flgSoloPreparazioneVersPubblicazione;
	}
	
	public boolean isFlgSoloOmissisModProprietaFile() {
		return flgSoloOmissisModProprietaFile;
	}
	
	public void setFlgSoloOmissisModProprietaFile(boolean flgSoloOmissisModProprietaFile) {
		this.flgSoloOmissisModProprietaFile = flgSoloOmissisModProprietaFile;
	}

	public boolean isShowFlgSostituisciVerPrec() {
		return showFlgSostituisciVerPrec;
	}

	public void setShowFlgSostituisciVerPrec(boolean showFlgSostituisciVerPrec) {
		this.showFlgSostituisciVerPrec = showFlgSostituisciVerPrec;
	}

	public boolean isShowImportaFileDaDocumenti() {
		return showImportaFileDaDocumenti;
	}

	public void setShowImportaFileDaDocumenti(boolean showImportaFileDaDocumenti) {
		this.showImportaFileDaDocumenti = showImportaFileDaDocumenti;
	}
	
	public boolean isShowCollegaDocumentiImportati() {
		return showCollegaDocumentiImportati;
	}

	public void setShowCollegaDocumentiImportati(boolean showCollegaDocumentiImportati) {
		this.showCollegaDocumentiImportati = showCollegaDocumentiImportati;
	}
	
	public boolean isHideVisualizzaVersioniButton() {
		return hideVisualizzaVersioniButton;
	}
	
	public void setHideVisualizzaVersioniButton(boolean hideVisualizzaVersioniButton) {
		this.hideVisualizzaVersioniButton = hideVisualizzaVersioniButton;
	}
	
	public boolean isShowDownloadOutsideAltreOperazioniButton() {
		return showDownloadOutsideAltreOperazioniButton;
	}

	public void setShowDownloadOutsideAltreOperazioniButton(boolean showDownloadOutsideAltreOperazioniButton) {
		this.showDownloadOutsideAltreOperazioniButton = showDownloadOutsideAltreOperazioniButton;
	}	
	
	public boolean isHideAcquisisciDaScannerInAltreOperazioniButton() {
		return hideAcquisisciDaScannerInAltreOperazioniButton;
	}

	public void setHideAcquisisciDaScannerInAltreOperazioniButton(boolean hideAcquisisciDaScannerInAltreOperazioniButton) {
		this.hideAcquisisciDaScannerInAltreOperazioniButton = hideAcquisisciDaScannerInAltreOperazioniButton;
	}
	
	public boolean isHideFirmaInAltreOperazioniButton() {
		return hideFirmaInAltreOperazioniButton;
	}

	public void setHideFirmaInAltreOperazioniButton(boolean hideFirmaInAltreOperazioniButton) {
		this.hideFirmaInAltreOperazioniButton = hideFirmaInAltreOperazioniButton;
	}
	
	public boolean isHideTimbraInAltreOperazioniButton() {
		return hideTimbraInAltreOperazioniButton;
	}

	public void setHideTimbraInAltreOperazioniButton(boolean hideTimbraInAltreOperazioniButton) {
		this.hideTimbraInAltreOperazioniButton = hideTimbraInAltreOperazioniButton;
	}
	
	public boolean isAttivaTimbroTipologia() {
		return false;
	}
	
	public boolean isAttivaVociBarcode(){
		return false;
	}
	
	public boolean isFromFolderPraticaPregressa(){
		return false;
	}
	
	public boolean showOperazioniTimbraturaAllegato(Record allegatoRecord) {
		Record detailRecord = getDetailRecord();
		if(detailRecord != null && detailRecord.getAttribute("codCategoriaProtocollo")!= null && ("PP".equals(detailRecord.getAttribute("codCategoriaProtocollo")) || "PG".equals(detailRecord.getAttribute("codCategoriaProtocollo")) || "R".equals(detailRecord.getAttribute("codCategoriaProtocollo")))) {		
			return true;
		}
		return false;
	}
	
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isReadOnly() {
		return readOnly;
	}
	
	public boolean isSoloPreparazioneVersPubblicazione() {
		return soloPreparazioneVersPubblicazione;
	}

	public void setSoloPreparazioneVersPubblicazione(boolean soloPreparazioneVersPubblicazione) {
		this.soloPreparazioneVersPubblicazione = soloPreparazioneVersPubblicazione;
	}
	
	public boolean isSoloOmissisModProprietaFile() {
		return soloOmissisModProprietaFile;
	}

	public void setSoloOmissisModProprietaFile(boolean soloOmissisModProprietaFile) {
		this.soloOmissisModProprietaFile = soloOmissisModProprietaFile;
	}
	
	public boolean isAllegatiNonParteIntegranteNonEditabili() {
		return false;
	}
	
	public boolean isAttivaModificaEsclusionePubblicazione() {
		return attivaModificaEsclusionePubblicazione;
	}

	public void setAttivaModificaEsclusionePubblicazione(boolean attivaModificaEsclusionePubblicazione) {
		this.attivaModificaEsclusionePubblicazione = attivaModificaEsclusionePubblicazione;
	}
	
	public boolean abilitaModificaFlgEsclusionePubblicazione() {
		return false;
	}
	
	public void setAggiuntaFile(boolean aggiuntaFile) {
		this.aggiuntaFile = aggiuntaFile;
	}

	public boolean isAggiuntaFile() {
		return aggiuntaFile;
	}
	
	public void setCanEditOnlyOmissis(boolean canEditOnlyOmissis) {
		this.canEditOnlyOmissis = canEditOnlyOmissis;
	}

	public boolean isCanEditOnlyOmissis() {
		return canEditOnlyOmissis;
	}
	
	public boolean isFormatoAmmesso(InfoFileRecord info) {
		return true;
	}
	
	public boolean isFormatoAmmessoParteIntegrante(InfoFileRecord info) {
		return true;
	}
	
	public String getRifiutoAllegatiConFirmeNonValide() {
		return null;
	}
	
	public boolean isDisattivaUnioneAllegatiFirmati() {
		return false;
	}
	
	public boolean isPubblicazioneSeparataPdfProtetti() {
		return false;
	}
	
	public void manageOnClickFirma(String uri, String display, InfoFileRecord infoFile, FirmaCallback firmaCallback) {
		FirmaUtility.firmaMultipla(uri, display, infoFile, firmaCallback);
	}
	
	public boolean showTimbraBarcodeMenuOmissis() {
		return false;
	}
	
	public String getImportaFileDocumentiBtnTitle() {
		if ((isDocCedAutotutela() && isDocumentiIstruttoria()) || isDocIstruttoriaDettFascicoloGenCompleto()) {
			return I18NUtil.getMessages().protocollazione_detail_importaDocumentiDaAltriDocumenti_title();
		} else {
			return I18NUtil.getMessages().protocollazione_detail_importaFileDaAltriDocumenti_title();
		}
	}
	
	public String getFinalitaImportaAllegatiMultiLookupArchivio() {
		if ((isDocCedAutotutela() && isDocumentiIstruttoria()) || isDocIstruttoriaDettFascicoloGenCompleto()) {
			return "IMPORTA_UD";
		} else {
			return "IMPORTA_FILE";
		}
	}
	
	public void clickProtocolla(Record allegatoRecord, ServiceCallback<Record> callback) {
		
	}
	
	public void afterClickProtocolla(Record allegatoRecord) {
		
	}
	
	@Override
	public boolean hasDatiSensibili() {
		if(isShowFlgNoPubblAllegato() || getShowVersioneOmissis()) {
			VLayout lVLayout = getVLayout();
			for (int i = 0; i < getTotalMembers(); i++) {
				HLayout lHLayout = (HLayout) lVLayout.getMember(i);
				AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) getReplicableCanvas(lHLayout);
				if(lAllegatoCanvas.isParteDispositivo() && (!lAllegatoCanvas.isPubblicazioneSeparata() || hasListaAllegatiParteIntSeparatiXPubbl()) && (lAllegatoCanvas.isEsclusoDaPubbl() || lAllegatoCanvas.hasDatiSensibili())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasListaAllegatiParteIntSeparatiXPubbl() {
		return false;
	}
	
	public void setFlgPubblicaAllegatiSeparati(boolean flgPubblicaAllegatiSeparati) {
		if(isShowFlgPubblicaSeparato() && isFlgPubblicazioneAllegatiUguale()) { 
			addButtonsForm.setValue("flgPubblicaAllegatiSeparati", flgPubblicaAllegatiSeparati);
		}
	}
	
	public boolean isFlgPubblicaAllegatiSeparati() {
		if(isShowFlgPubblicaSeparato() && isFlgPubblicazioneAllegatiUguale()) { 
			Boolean flgPubblicaAllegatiSeparati = addButtonsForm.getValue("flgPubblicaAllegatiSeparati") != null && (Boolean) addButtonsForm.getValue("flgPubblicaAllegatiSeparati");
			return flgPubblicaAllegatiSeparati != null && flgPubblicaAllegatiSeparati;
		}
		return false;
	}
	
	public boolean isAttivaSceltaPosizioneAllegatiUniti() {
		return false;
	}		
	
	public boolean isCollegaDocumentiImportati() {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_IMP_FILE_ALTRI_DOC") && showImportaFileDaDocumenti && showCollegaDocumentiImportati) {
			Boolean collegaDocumentiImportati = addButtonsForm.getValue("collegaDocumentiImportati") != null && (Boolean) addButtonsForm.getValue("collegaDocumentiImportati");
			return collegaDocumentiImportati != null && collegaDocumentiImportati;
		}
		return false;
	}
	
	/**
	 * @author FEBUONO
	 * 
	 * XXX: In attesa di capire se la risposta è sempre il primo canvas, altrimenti bisogna usare il metodo di 
	 * AllegatoCanvas isRispostaCanvas() ciclando sui canvas (TaskDettFascicoloDetail setCanEdit di AllegatiItem)
	 */
	public AllegatoCanvas getRispostaCanvas() {
//		return getFirstCanvas() != null ? ((AllegatoCanvas) getFirstCanvas()) : null;
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			AllegatoCanvas lAllegatoCanvas = (AllegatoCanvas) getReplicableCanvas(lHLayout);
			if (lAllegatoCanvas.isRispostaCanvas()) {
				return lAllegatoCanvas;
			}			
		}
		return null;
	}
	
	public class ImportaAllegatiMultiLookupArchivio extends LookupArchivioPopup {

		private List<Record> multiLookupList = new ArrayList<Record>();

		public ImportaAllegatiMultiLookupArchivio(Record record) {
			super(record, null, false);
		}
		
		@Override
		public String getWindowTitle() {
			return getImportaFileDocumentiBtnTitle();
		}
		
		@Override
		public String getFinalita() {
			return getFinalitaImportaAllegatiMultiLookupArchivio();
		}

		@Override
		public void manageOnCloseClick() {
			super.manageOnCloseClick();
			if ((multiLookupList != null) && (multiLookupList.size() > 0)) {
				RecordList lRecordListDocumentToImport = new RecordList();
				for (Record record : multiLookupList) {
					Record lRecordToLoad = new Record();
					lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
					lRecordListDocumentToImport.add(lRecordToLoad);
				}
				Record recordMassivo = new Record();
				recordMassivo.setAttribute("listaRecord", lRecordListDocumentToImport);
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
				Layout.showWaitPopup(I18NUtil.getMessages().protocollazione_detail_importazioneFileWaitPopup_title());
				// Se cambia la logica nell'import bisogna riportarla anche nella medesima funzione in AllegatiGridItem
				lGwtRestDataSource.performCustomOperation("getProtocolloMassivo", recordMassivo, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							importaAllegatiDaGetProtocolloMassivo(response);
						} else {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().protocollazione_detail_importazioneFile_error(), "", MessageType.ERROR));
						}
						Layout.hideWaitPopup();
					}
					
				}, new DSRequest());
			}

		}

		@Override
		public void manageMultiLookupBack(Record record) {
			multiLookupList.add(record);
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			if (multiLookupList != null) {
				for (int i = 0; i < multiLookupList.size(); i++) {
					Record values = multiLookupList.get(i);
					if (values.getAttribute("idUdFolder").equals(record.getAttribute("id"))) {
						multiLookupList.remove(i);
						break;
					}
				}
			}
		}

		@Override
		public void manageLookupBack(Record record) {
		}

	}
	
	public void importaAllegatiDaGetProtocolloMassivo(final DSResponse response) {
		if ((isDocCedAutotutela() && isDocumentiIstruttoria()) || isDocIstruttoriaDettFascicoloGenCompleto()) {			
			importaUnitaDocumentaleDocIstruttoria(response);
		} else {
			if(isAttivaAllegatoUd()) {
				SC.ask("Vuoi importare un allegato per ogni riga-unità documentale selezionata? In caso contrario verrà importato un allegato per ogni file della/e righe selezionate.", new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value){
							importaUnitaDocumentaleAllegato(response);
						} else {
							importaFileFromUnitaDocumentale(response);
						}
					}
				});									
			} else {								
				importaFileFromUnitaDocumentale(response);
			}				
		}		
	}
	
	public boolean isAttivaAllegatoUd() {
		return false;
	}
	
	public void importaUnitaDocumentaleDocIstruttoria(DSResponse response) {
		RecordList resultRecordList = response.getData()[0].getAttributeAsRecordList("listaRecord");
		if(resultRecordList != null) {
			for (int i = 0; i < resultRecordList.getLength(); i++) {
				Record resultRecord = resultRecordList.get(i);
				// Verifico la presenza di allegati
				boolean haveFile = false;
				if (resultRecord.getAttribute("listaAllegati") != null && resultRecord.getAttributeAsRecordArray("listaAllegati").length > 0) {
					haveFile = true;
				}
				resultRecord.setAttribute("flgIdUdAppartenenzaContieneAllegati", haveFile);				
				if(resultRecord.getAttribute("codCategoriaProtocollo") != null && ("PG".equals(resultRecord.getAttribute("codCategoriaProtocollo")) || "R".equals(resultRecord.getAttribute("codCategoriaProtocollo")))) {
					String estremiUd = "";
					if("PG".equals(resultRecord.getAttribute("codCategoriaProtocollo"))) {
						estremiUd += "Prot. "; 
					} else if(resultRecord.getAttribute("siglaProtocollo") != null && !"".equals(resultRecord.getAttribute("siglaProtocollo"))) {
						estremiUd += resultRecord.getAttribute("siglaProtocollo") + " "; 
					}
					String dataProtocollo = DateUtil.format((Date) resultRecord.getAttributeAsDate("dataProtocollo"));
					if(dataProtocollo == null || "".equals(dataProtocollo)) {
						dataProtocollo = resultRecord.getAttribute("dataProtocollo") != null ? resultRecord.getAttribute("dataProtocollo").substring(0, 10) : "";
					}
					estremiUd += dataProtocollo + "." + resultRecord.getAttribute("nroProtocollo");		
					resultRecord.setAttribute("estremiProtUd", estremiUd);
				}
				// Metto una riga che rappresenta l'unità documentale
				AllegatoCanvas lastCanvas = (AllegatoCanvas) getLastCanvas();									
				if (lastCanvas != null && !lastCanvas.isChangedAndValid()) {
					lastCanvas.setFormValuesFromRecordImportaUnitaDocumentale(resultRecord);
					lastCanvasToReplicate = lastCanvas;
				} else {
					AllegatoCanvas canvas = (AllegatoCanvas) onClickNewButton();
					if(lastCanvasToReplicate != null) { 
						getFileForm(canvas).setValues(getFileForm(lastCanvasToReplicate).getValues());
					}
					canvas.setFormValuesFromRecordImportaUnitaDocumentale(resultRecord);
				}
			}
		}
		manageAfterImporta(response.getData()[0].getAttributeAsMap("errorMessages"));
	}
	
	public void importaUnitaDocumentaleAllegato(DSResponse response) {
		RecordList resultRecordList = response.getData()[0].getAttributeAsRecordList("listaRecord");
		if(resultRecordList != null) {
			for (int i = 0; i < resultRecordList.getLength(); i++) {
				Record resultRecord = resultRecordList.get(i);
				// Metto una riga che rappresenta l'unità documentale
				AllegatoCanvas lastCanvas = (AllegatoCanvas) getLastCanvas();									
				if (lastCanvas != null && !lastCanvas.isChangedAndValid()) {
					lastCanvas.setFormValuesFromRecordImportaUnitaDocumentaleAllegato(resultRecord);
					lastCanvasToReplicate = lastCanvas;
				} else {
					AllegatoCanvas canvas = (AllegatoCanvas) onClickNewButton();
					if(lastCanvasToReplicate != null) { 
						getFileForm(canvas).setValues(getFileForm(lastCanvasToReplicate).getValues());
					}
					canvas.setFormValuesFromRecordImportaUnitaDocumentaleAllegato(resultRecord);
				}
			}
		}
		manageAfterImporta(response.getData()[0].getAttributeAsMap("errorMessages"));
	}
	
	public void importaFileFromUnitaDocumentale(DSResponse response) {
		RecordList resultRecordList = response.getData()[0].getAttributeAsRecordList("listaRecord");
		if(resultRecordList != null) {
			for (int i = 0; i < resultRecordList.getLength(); i++) {
				Record resultRecord = resultRecordList.get(i);
				String idUd = resultRecord.getAttribute("idUd");
				String segnatura = resultRecord.getAttribute("segnatura") != null ? resultRecord.getAttribute("segnatura") : "";
				int numeroAllegato = 0;								
				boolean haveFile = false;
				// Verifico che sia presente il file primario
				if ((resultRecord.getAttribute("uriFilePrimario") != null) && (!"".equalsIgnoreCase(resultRecord.getAttribute("uriFilePrimario")))) {
					haveFile = true;
					AllegatoCanvas lastCanvas = (AllegatoCanvas) getLastCanvas();
					if (lastCanvas != null && !lastCanvas.isChangedAndValid()) {
						lastCanvas.setFormValuesFromRecordImportaFilePrimario(resultRecord);
						lastCanvasToReplicate = lastCanvas;
					} else {
						AllegatoCanvas canvas = (AllegatoCanvas) onClickNewButton();
						if(lastCanvasToReplicate != null) { 
							getFileForm(canvas).setValues(getFileForm(lastCanvasToReplicate).getValues());
						}
						canvas.setFormValuesFromRecordImportaFilePrimario(resultRecord);										
					}
				}
				// Verifico gli allegati
				if ((resultRecord.getAttribute("listaAllegati") != null)) {
					Record[] listaAllegati = resultRecord.getAttributeAsRecordArray("listaAllegati");
					for (Record recordAllegato : listaAllegati) {
						haveFile = true;
						numeroAllegato += 1;
						AllegatoCanvas lastCanvas = (AllegatoCanvas) getLastCanvas();
						if (lastCanvas != null && !lastCanvas.isChangedAndValid()) {
							lastCanvas.setFormValuesFromRecordImportaFileAllegato(recordAllegato, numeroAllegato, idUd, segnatura);
							lastCanvasToReplicate = lastCanvas;
						} else {
							AllegatoCanvas canvas = (AllegatoCanvas) onClickNewButton();
							if(lastCanvasToReplicate != null) { 
								getFileForm(canvas).setValues(getFileForm(lastCanvasToReplicate).getValues());
							}
							canvas.setFormValuesFromRecordImportaFileAllegato(recordAllegato, numeroAllegato, idUd, segnatura);											
						}
					}
				}								
				// Se non ho nè primario nè allegati metto una riga che rappresenta l'unità documentale
				if (!haveFile) {
					AllegatoCanvas lastCanvas = (AllegatoCanvas) getLastCanvas();									
					if (lastCanvas != null && !lastCanvas.isChangedAndValid()) {
						lastCanvas.setFormValuesFromRecordImportaUnitaDocumentale(resultRecord);
						lastCanvasToReplicate = lastCanvas;
					} else {
						AllegatoCanvas canvas = (AllegatoCanvas) onClickNewButton();
						if(lastCanvasToReplicate != null) { 
							getFileForm(canvas).setValues(getFileForm(lastCanvasToReplicate).getValues());
						}
						canvas.setFormValuesFromRecordImportaUnitaDocumentale(resultRecord);
					}
				}
			}			
		}
		manageAfterImporta(response.getData()[0].getAttributeAsMap("errorMessages"));
	}
	
	public void manageAfterImporta(Map mappaErrori) {
		// faccio i controlli su tutta la lista di file che ho importato
		controlAllAfterUploadMultiplo();
		if (mappaErrori != null && mappaErrori.size() > 0 && mappaErrori.get("segnatureInError") != null && !"".equalsIgnoreCase((String) mappaErrori.get("segnatureInError"))) {
			String listaSegnatureInError = (String) mappaErrori.get("segnatureInError");
			String message = I18NUtil.getMessages().protocollazione_detail_elencoSegnatureInError_error() + " " + listaSegnatureInError;
			Layout.addMessage(new MessageBean(message, "", MessageType.WARNING));
		}
	}

	@Override
	public boolean skipValidation() {
		return getForm() == null || !getForm().isFormVisible() || (getForm().isFormVisible() && !getVisible()) || !(getEditing() != null && getEditing());
	}

	public boolean showAltreOp() {
		return true;
	}

	public boolean showAltreOpOmissis() {
		return true;
	}
	
	public DynamicForm getFileForm(ReplicableCanvas lReplicableCanvas) {
		return (this instanceof DocPraticaPregressaItem) ? lReplicableCanvas.getForm()[1] : lReplicableCanvas.getForm()[0];
	}
	
	public boolean isShowModalPreview() {
		return true;
	}
	
}
