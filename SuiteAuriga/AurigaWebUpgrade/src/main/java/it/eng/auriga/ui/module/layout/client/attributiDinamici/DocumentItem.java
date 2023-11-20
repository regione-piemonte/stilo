/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.core.DataClass;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.common.items.FileButtonsItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.ApponiTimbroWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.IDocumentItem;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class DocumentItem extends CanvasItem implements IDocumentItem{

	private Record detailRecord;
	private DocumentItem instance;
	private DocumentForm lDynamicForm;

	private HiddenItem idUdItem;
	private HiddenItem idDocItem;
	private HiddenItem uriFileItem;
	private HiddenItem infoFileItem;
	private HiddenItem remoteUriItem;
	private HiddenItem isChangedItem;
	private HiddenItem nomeFileOrigItem;
	private HiddenItem nomeFileTifItem;
	private HiddenItem uriFileTifItem;
	private HiddenItem nomeFileVerPreFirmaItem;
	private HiddenItem uriFileVerPreFirmaItem;
	private HiddenItem improntaVerPreFirmaItem;
	private HiddenItem infoFileVerPreFirmaItem;
	private HiddenItem nroUltimaVersioneItem;
	private HiddenItem nroVersioneItem;

	private ExtendedTextItem nomeFileItem;
//	private FileUploadItemWithFirmaAndMimeType uploadItem;
	private FileButtonsItem fileButtons;
	private TextItem improntaItem; 
	
	protected CheckboxItem flgSostituisciVerPrecItem;
	
	protected HiddenItem flgTimbratoFilePostRegItem;
	protected HiddenItem opzioniTimbroItem;
	protected CheckboxItem flgTimbraFilePostRegItem;
	
	//TODO PROPRIETA' da aggiungere al bean & datasource della maschera, copiare da protocollazioneDetail
	//protected HiddenItem attivaTimbroTipologiaItem; 

	protected Map valuesOrig;
	protected Boolean editing = true;
	
	/**
	 * Serve per istanziare la classe tramite GWT
	 * 
	 * @param jsObj
	 */
	public DocumentItem(JavaScriptObject jsObj) {
		super(jsObj);
	}

	/**
	 * Esegue il build quando l'oggetto non esiste. return AttachmentItem;
	 * 
	 * @param jsObj
	 * @return
	 */
	public DocumentItem buildObject(JavaScriptObject jsObj) {
		
		DocumentItem lItem = getOrCreateRef(jsObj);
		return lItem;
	}

	/**
	 * Crea l'istanza o ne restituisce una a partire dall'oggetto Javascript
	 * 
	 * @param jsObj
	 * @return
	 */
	public static DocumentItem getOrCreateRef(JavaScriptObject jsObj) {
		if (jsObj == null)
			return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if (obj != null) {
			obj.setJsObj(jsObj);
			return (DocumentItem) obj;
		} else {
			return null;
		}
	}

	/**
	 * Crea un AttachmentItem. In fase di init, lo disegna e ne setta lo showHandler per gestire il setValue
	 * 
	 */
	public DocumentItem() {
		instance = this;
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {

			public void onInit(FormItem item) {
				// Inizializza il componente
				buildObject(item.getJsObj()).disegna((Record) item.getValue());
				// Setto lo showValue (Gestiste il setValue
				addShowValueHandler(new ShowValueHandler() {

					@Override
					public void onShowValue(ShowValueEvent event) {
						Record lRecord = event.getDataValueAsRecord();
						if(lRecord != null) {
							valuesOrig = lRecord.toMap();
							setValue(lRecord);
						}
					}
				});
			}
		});
		setShouldSaveValue(true);
	}

	@Override
	public void setValue(Object value) {		
		Record lRecord = (Record) value;
		lDynamicForm.editRecord(lRecord != null ? lRecord : new Record());
		// Memorizzo il record
		storeValue(lRecord);
	}

	@Override
	public Object getValue() {		
		return lDynamicForm != null ? lDynamicForm.getValuesAsRecord() : new Record();
	}

	protected void disegna(Record lRecord) {

		lDynamicForm = new DocumentForm();
		lDynamicForm.setWidth(10);
		lDynamicForm.setNumCols(6);
		lDynamicForm.setOverflow(Overflow.VISIBLE);
		
		idUdItem = new HiddenItem("idUd");
		idDocItem = new HiddenItem("idDoc");
		uriFileItem = new HiddenItem("uriFile");
		infoFileItem = new HiddenItem("infoFile");
		remoteUriItem = new HiddenItem("remoteUri");
		isChangedItem = new HiddenItem("isChanged");
		nomeFileOrigItem = new HiddenItem("nomeFileOrig");
		nomeFileTifItem = new HiddenItem("nomeFileTif");
		uriFileTifItem = new HiddenItem("uriFileTif");		
		nomeFileVerPreFirmaItem = new HiddenItem("nomeFileVerPreFirma");
		uriFileVerPreFirmaItem = new HiddenItem("uriFileVerPreFirma");
		improntaVerPreFirmaItem = new HiddenItem("improntaVerPreFirma");
		infoFileVerPreFirmaItem = new HiddenItem("infoFileVerPreFirma");
		nroUltimaVersioneItem = new HiddenItem("nroUltimaVersione");
		nroVersioneItem = new HiddenItem("nroVersione");

		nomeFileItem = new ExtendedTextItem("nomeFile");
		nomeFileItem.setShowTitle(false);
		try {
			nomeFileItem.setWidth(getWidth());
		} catch (Exception e) {
		}
		nomeFileItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				if (isNotBlank(nomeFileItem.getValueAsString())) {
					if (isNotBlank(lDynamicForm.getValueAsString("nomeFileOrig"))
							&& !nomeFileItem.getValueAsString().equals(lDynamicForm.getValueAsString("nomeFileOrig"))) {
						manageOnChangedFile();						
					}
				} else {
					fileButtons.clickEliminaFile();
				}
				lDynamicForm.markForRedraw();
				fileButtons.markForRedraw();
				manageOnChanged();
			}
		});

//		uploadItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {
//
//			@Override
//			public void uploadEnd(final String displayFileName, final String uri) {
//				Record lRecordForm = new Record(lDynamicForm.getValues());
//				lRecordForm.setAttribute("nomeFile", displayFileName);
//				lRecordForm.setAttribute("uriFile", uri);
//				lRecordForm.setAttribute("nomeFileTif", "");
//				lRecordForm.setAttribute("uriFileTif", "");
//				lRecordForm.setAttribute("remoteUri", false);
//				setValue(lRecordForm);
//				changedEventAfterUpload(displayFileName, uri);
//			}
//
//			@Override
//			public void manageError(String error) {
//				String errorMessage = "Errore nel caricamento del file";
//				if (error != null && !"".equals(error))
//					errorMessage += ": " + error;
//				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
//				lDynamicForm.markForRedraw();
//				fileButtons.markForRedraw();
//				manageOnChanged();
//				uploadItem.getCanvas().redraw();
//			}
//		}, new ManageInfoCallbackHandler() {
//
//			@Override
//			public void manageInfo(InfoFileRecord info) {
//				InfoFileRecord precInfo = lDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(lDynamicForm.getValue("infoFile")) : null;
//				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
//				Record lRecordForm = new Record(lDynamicForm.getValues());
//				lRecordForm.setAttribute("infoFile", info);
//				String nomeFile = lDynamicForm.getValueAsString("nomeFile");
//				String nomeFileOrig = lDynamicForm.getValueAsString("nomeFileOrig");
//				if (precImpronta == null || !precImpronta.equals(info.getImpronta())
//						|| (nomeFile != null && !"".equals(nomeFile) && nomeFileOrig != null && !"".equals(nomeFileOrig) && !nomeFile.equals(nomeFileOrig))) {
//					manageOnChangedFile()		
//				}
//				setValue(lRecordForm);
//				if (info.isFirmato() && !info.isFirmaValida()) {
//					GWTRestDataSource.printMessage(new MessageBean("Il file presenta una firma non valida alla data odierna", "", MessageType.WARNING));
//				}
//				lDynamicForm.markForRedraw();
//				fileButtons.markForRedraw();
//				manageOnChanged();
//			}
//		});
//		uploadItem.setAttribute("nascosto", !showUploadItem());		
//		uploadItem.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				
//				uploadItem.setAttribute("nascosto", !showUploadItem());				
//				return showUploadItem();
//			}
//		});
//		
//		uploadItem.setColSpan(1);

		fileButtons = new FileButtonsItem("fileButtons", lDynamicForm/*, uploadItem*/) {
			
			@Override
			public String getTipoDoc() {
				return instance.getTipoDocumento();
			}
			
			@Override
			public boolean showAltreOperazioni() {	
				return instance.showAltreOperazioni();
			}
			
			@Override
			public boolean showAltreOperazioniIfEditing() {	
				return instance.showAltreOperazioniIfEditing();
			}
			
			@Override
			public Record getRecordProtocollo() {
				return getRecordProtocolloDoc();
			}
			
			@Override
			public String getFlgTipoProv() {
				return instance.getFlgTipoProvProtocollo();
			}
			
			@Override
			public String getIdProcGeneraDaModello() {
				return instance.getIdProcGeneraDaModello();
			}
			
			@Override
			public boolean showUploadItem() {
				if(instance.showUploadItem()) {
					return super.showUploadItem();
				}
				return false;
			}
			
			@Override
			public String getWarningMessageOnUpload() {
				return instance.getWarningMessageOnUpload();
			}
			
			@Override
			public String getIdUd() {
				return lDynamicForm.getValueAsString("idUd");
			}
			
			@Override
			public boolean enableAcquisisciDaScannerMenuItem() {
				return super.enableAcquisisciDaScannerMenuItem() && enableAcquisisciDaScannerMenu();
			}
			
			@Override
			public boolean showPreviewButton() {
				if(instance.showPreviewButton()) {
					return super.showPreviewButton();
				}
				return false;
			}
			
			@Override
			public boolean showSalvaVersConOmissisInPreview() {
				return instance.showSalvaVersConOmissisInPreview();
			}
			
			@Override
			public void executeSalvaVersConOmissisInPreview(Record record) {
				instance.executeSalvaVersConOmissisInPreview(record);
			}
			
			@Override
			public boolean showPreviewEditButton() {
				if(instance.showPreviewEditButton()) {
					return super.showPreviewEditButton();
				}
				return false;
			}
			
			@Override
			public boolean canBeEditedByApplet() {
				return instance.canBeEditedByApplet();				
			}
			
			@Override
			public boolean showEditButton() {
				if(instance.showEditButton()) {
					return super.showEditButton();
				}
				return false;
			}			
			
			@Override
			public boolean showGeneraDaModelloButton() {
				if(instance.showGeneraDaModelloButton()) {
					return super.showGeneraDaModelloButton();
				}
				return false;
			}
			
			@Override
			public boolean showVisualizzaVersioniMenuItem() {
				return instance.showVisualizzaVersioniMenuItem();
			}
			
			@Override
			public boolean showDownloadMenuItem() {
				return instance.showDownloadMenuItem();
			}
			
			@Override
			public boolean showDownloadButtomOutsideAltreOpMenu() {
				return instance.showDownloadButtomOutsideAltreOpMenu();
			}
			
			@Override
			public boolean showAcquisisciDaScannerMenuItem() {
				return instance.showAcquisisciDaScannerMenuItem();
			}
			
			@Override
			public boolean showFirmaMenuItem() {
				return instance.showFirmaMenuItem();
			}
			
			@Override
			public boolean showTimbraBarcodeMenuItems() {
				return instance.showTimbraBarcodeMenuItems();
			}
			
			@Override
			public boolean hideTimbraMenuItems() {
				return instance.hideTimbraMenuItems();
			}
			
			@Override
			public boolean showEliminaMenuItem() {
				return instance.showEliminaMenuItem();
			}
			
			@Override
			public boolean showFileFirmatoDigButton() {
				if(instance.showFileFirmatoDigButton()) {
					return super.showFileFirmatoDigButton();
				}
				return false;
			}

			@Override
			public void manageOnChanged() {
				instance.manageOnChanged();
			}

			@Override
			public void manageOnChangedFile() {
				instance.manageOnChangedFile();
			}

			@Override
			public boolean isAttivaTimbroTipologia() {
				return instance.isAttivaTimbroTipologia();
			}
			
			@Override
			public Record getRecordCaricaModello(String idModello, String tipoModello) {
				return instance.getRecordCaricaModello(idModello, tipoModello);
			}
			
			@Override
			public GWTRestDataSource getGeneraDaModelloDataSource(String idModello, String tipoModello, String flgConvertiInPdf) {
				return instance.getGeneraDaModelloDataSource(idModello, tipoModello, flgConvertiInPdf);
			}
			
			@Override
			public boolean isFormatoAmmesso(InfoFileRecord info) {	
				return instance.isFormatoAmmesso(info);
			}
			
			@Override
			public boolean showAzioniTimbratura() {
				return instance.showAzioniTimbratura();
			}
		};
		
		improntaItem = new TextItem("impronta", "Impronta") {
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		improntaItem.setWidth(350);
		improntaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				improntaItem.setCanEdit(false);
				if (uriFileItem.getValue() != null && !uriFileItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(lDynamicForm.getValue("infoFile"));
					String impronta = lInfoFileRecord != null && lInfoFileRecord.getImpronta() != null ? lInfoFileRecord.getImpronta() : "";
					improntaItem.setValue(impronta);
					return impronta != null && !"".equals(impronta) && AurigaLayout.getParametroDBAsBoolean("SHOW_IMPRONTA_FILE");
				} else {
					improntaItem.setValue("");
					return false;
				}
			}
		});
		
		flgSostituisciVerPrecItem = new CheckboxItem("flgSostituisciVerPrec", "sostituisci alla ver. prec");
		flgSostituisciVerPrecItem.setWidth("*");
		flgSostituisciVerPrecItem.setDefaultValue(false);
		flgSostituisciVerPrecItem.setVisible(false);
				
		flgTimbratoFilePostRegItem = new HiddenItem("flgTimbratoFilePostReg");		
		opzioniTimbroItem = new HiddenItem("opzioniTimbro");
		
		flgTimbraFilePostRegItem = new CheckboxItem("flgTimbraFilePostReg", I18NUtil.getMessages().protocollazione_flgTimbraFilePostReg());
		flgTimbraFilePostRegItem.setValue(false);
		flgTimbraFilePostRegItem.setColSpan(1);
		flgTimbraFilePostRegItem.setWidth("*");
		flgTimbraFilePostRegItem.setShowTitle(true);
		flgTimbraFilePostRegItem.setWrapTitle(false);
		flgTimbraFilePostRegItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgTimbraFilePostReg();
			}
		});	
		flgTimbraFilePostRegItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if(event.getValue() != null && (Boolean)event.getValue()){
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(lDynamicForm.getValue("infoFile"));
					//Verifico se il file è timbrabile
					if (canBeTimbrato(lInfoFileRecord)) {
						if(!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaApponiTimbro")){
							showOpzioniTimbratura();						
						}
					} else {
						Layout.addMessage(new MessageBean("Il formato del file non ne consente la timbratura", "", MessageType.WARNING));
						flgTimbraFilePostRegItem.setValue(false);
					}
				}	
			}
		});
		
		setFormItems(
			idUdItem, 
			idDocItem, 
			uriFileItem, 
			infoFileItem, 
			remoteUriItem, 
			isChangedItem, 
			nomeFileOrigItem, 
			nomeFileTifItem, 
			uriFileTifItem,
			nomeFileVerPreFirmaItem,
			uriFileVerPreFirmaItem,
			improntaVerPreFirmaItem,
			infoFileVerPreFirmaItem,
			nroUltimaVersioneItem,
			nroVersioneItem,
			nomeFileItem, 
//			uploadItem, 
			fileButtons,
			improntaItem,
			flgSostituisciVerPrecItem,
			flgTimbratoFilePostRegItem,
			opzioniTimbroItem,
			flgTimbraFilePostRegItem
		);

		setCanvas(lDynamicForm);

		setValue(lRecord);
	}

	protected boolean showAzioniTimbratura() {
		return true;
	}

	protected boolean showAltreOperazioni() {
		return true;
	}
		
	protected boolean showAltreOperazioniIfEditing() {
		return false;
	}

	public void setFormItems(FormItem... items) {
		for (FormItem item : items) {
			item.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {					
					Record lRecord = new Record(lDynamicForm.getValues());
					lRecord.setAttribute(event.getItem().getName(), event.getValue());
					storeValue(lRecord);
				}
			});
		}
		lDynamicForm.setItems(items);
	}
	
	@Override
	public Boolean validate() {
		boolean valid = true;
		lDynamicForm.setShowInlineErrors(true);
		Map<String, String> lMap = new HashMap<String, String>();
		if (getRequired() && !isNotBlank((String) uriFileItem.getValue())) {
			lMap.put("nomeFile", "Campo obbligatorio");
			lDynamicForm.setErrors(lMap);
			valid = false;
		}
		valid = validateSenzaObbligatorieta() && valid;
		showTabErrors(valid);
		return valid;
	}
	
	@Override
	public Boolean valuesAreValid() {
		boolean valid = true;
		if (getRequired() && !isNotBlank((String) uriFileItem.getValue())) {
			valid = false;
		}
		valid = valuesAreValidSenzaObbligatorieta() && valid;
		showTabErrors(valid);
		return valid;		
	}	

	public Boolean validateSenzaObbligatorieta() {
		boolean valid = true;
		if (lDynamicForm.getValue("uriFile") != null && !"".equals(lDynamicForm.getValue("uriFile"))) {
			InfoFileRecord infoFile = lDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(lDynamicForm.getValue("infoFile")) : null;
			if (infoFile == null || infoFile.getMimetype() == null || infoFile.getMimetype().equals("")) {
				HashMap errors = new HashMap();
				errors.put("nomeFile", "Formato file non riconosciuto");
				lDynamicForm.setErrors(errors);
				valid = false;
			}
			if (infoFile.getBytes() <= 0) {
				HashMap errors = new HashMap();
				errors.put("nomeFile", "Il file ha dimensione 0");
				lDynamicForm.setErrors(errors);
				valid = false;
			}
		}
		showTabErrors(valid);
		return valid;
	}
	
	public Boolean valuesAreValidSenzaObbligatorieta() {
		boolean valid = true;
		if (lDynamicForm.getValue("uriFile") != null && !"".equals(lDynamicForm.getValue("uriFile"))) {
			InfoFileRecord infoFile = lDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(lDynamicForm.getValue("infoFile")) : null;
			if (infoFile == null || infoFile.getMimetype() == null || infoFile.getMimetype().equals("")) {
				valid = false;
			}
			if (infoFile.getBytes() <= 0) {
				valid = false;
			}
		}
		return valid;
	}

	public void showTabErrors(boolean valid) {
		try {
			TabSet tabSet = getForm() != null ? getForm().getTabSet() : null;
			String tabID = getForm() != null ? getForm().getTabID() : null;
			if (!valid && tabSet != null && tabID != null && !"".equals(tabID)) {
				tabSet.showTabErrors(tabID);
			}
		} catch (Exception e) {
		}
	}

	// Cancellazione del file
	protected void clickEliminaFile() {
		Map values = lDynamicForm.getValues();
		values.remove("infoFile");
		values.remove("infoFileVerPreFirma");
		Record lRecordForm = new Record(values);
		lRecordForm.setAttribute("nomeFile", "");
		lRecordForm.setAttribute("uriFile", "");
		lRecordForm.setAttribute("nomeFileTif", "");
		lRecordForm.setAttribute("uriFileTif", "");
		lRecordForm.setAttribute("remoteUri", false);		
		lRecordForm.setAttribute("nomeFileVerPreFirma", "");
		lRecordForm.setAttribute("uriFileVerPreFirma", "");
		lRecordForm.setAttribute("improntaVerPreFirma", "");
		setValue(lRecordForm);
		lDynamicForm.markForRedraw();
		fileButtons.markForRedraw();
		manageOnChanged();
//		uploadItem.getCanvas().redraw();
	}

	public DynamicForm getForm() {
		return lDynamicForm;
	}

	protected void changedEventAfterUpload(final String displayFileName, final String uri) {
		ChangedEvent lChangedEventDisplay = new ChangedEvent(nomeFileItem.getJsObj()) {

			public DynamicForm getForm() {
				return lDynamicForm;
			};

			@Override
			public FormItem getItem() {
				return nomeFileItem;
			}

			@Override
			public Object getValue() {
				
				return displayFileName;
			}
		};
		ChangedEvent lChangedEventUri = new ChangedEvent(uriFileItem.getJsObj()) {

			public DynamicForm getForm() {
				return lDynamicForm;
			};

			@Override
			public FormItem getItem() {
				return uriFileItem;
			}

			@Override
			public Object getValue() {
				return uri;
			}
		};
		nomeFileItem.fireEvent(lChangedEventDisplay);
		uriFileItem.fireEvent(lChangedEventUri);
	}

	public void editRecord(Record record) {
		
		record.setAttribute("nomeFileOrig", record.getAttribute("nomeFile"));
		lDynamicForm.editRecord(record);
		lDynamicForm.markForRedraw();
	}

	private boolean isNotBlank(String str) {
		return str != null && !"".equals(str.trim());
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {	
		setEditing(canEdit);
		for (FormItem item : lDynamicForm.getFields()) {
			item.setCanEdit(canEdit);
			item.redraw();
		}	
		if (!isNomeFileEditable()) {
			nomeFileItem.setCanEdit(false);
			nomeFileItem.redraw();
		}
		fileButtons.setCanEdit(canEdit);
	}
	
	public Boolean getEditing() {
		return editing;
	}

	public void setEditing(Boolean editing) {
		this.editing = editing;
	}

	public void mostraVariazione() {
		nomeFileItem.setCellStyle(it.eng.utility.Styles.formCellVariazione);
		nomeFileItem.redraw();
	}
	
	/**
	 * Viene verificato se il file da timbrare in fase di post registrazione è convertibile in pdf.
	 */
	private static boolean canBeTimbrato(InfoFileRecord lInfoFileRecord) {
		if (lInfoFileRecord == null)
			return false;
		else {
			String mimetype = lInfoFileRecord.getMimetype() != null ? lInfoFileRecord.getMimetype() : "";
			if (mimetype != null) {
				if (mimetype.equals("application/pdf") || mimetype.startsWith("image") || lInfoFileRecord.isConvertibile()) {
					return true;
				} else
					return false;
			} else
				return false;
		}
	}
	
	private void showOpzioniTimbratura() {

		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro");
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro");
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina");

		Record record = new Record();
		record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
		record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
		record.setAttribute("tipoPaginaPref", tipoPaginaPref);

		record.setAttribute("nomeFile", lDynamicForm.getValueAsString("nomeFile"));
		record.setAttribute("uriFile", lDynamicForm.getValueAsString("uriFile"));
		record.setAttribute("remote", lDynamicForm.getValueAsString("remoteUri"));
		InfoFileRecord infoFile = lDynamicForm.getValue("infoFile") != null
				? new InfoFileRecord(lDynamicForm.getValue("infoFile"))
				: null;
		if (infoFile != null) {
			record.setAttribute("infoFile", infoFile);
		}

		ApponiTimbroWindow apponiTimbroWindow = new ApponiTimbroWindow(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				Record lRecordForm = new Record(lDynamicForm.getValues());
				lRecordForm.setAttribute("opzioniTimbro", object);
				setValue(lRecordForm);
			}
		});
		apponiTimbroWindow.show();
	}
	
	public String getTipoDocumento() {
		return null;
	}
	
	public String getFlgTipoProvProtocollo() {
		return null;
	}
	
	public String getIdProcGeneraDaModello() {
		return null;
	}
	
	public Date getDataRifValiditaFirma() {
		return null;
	}

	private boolean showFlgTimbraFilePostReg() {
		/*
		Il check deve apparire quando c'è il file, NON è firmato digitalmente & se:
	    a) si sta facendo una nuova registrazione
	    b) si sta facendo una nuova registrazione come copia
	    c) si sta facendo una nuova registrazione da modello
	    d) si sta protocollando una bozza
	    e) si sta modificando una registrazione e si è fatto upload/scansione di un nuovo file OR se il file era già presente e la stored non indicava che il file era timbrato (il caso d può ricadere in questo).
	    */ 
		if (isAttivaTimbraturaFilePostReg()) {		
			boolean isEditing = getEditingProtocollo() && showUploadItem();
			String flgTipoProv = getFlgTipoProvProtocollo();
			String uriFile = (String) lDynamicForm.getValue("uriFile");
			InfoFileRecord infoFile = lDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(lDynamicForm.getValue("infoFile")) : null;
//			String mimetype = infoFile != null && infoFile.getMimetype() != null ? infoFile.getMimetype() : "";
			// se sto facendo una nuova registrazione o una variazione di una già esistente, e se il file c'è e non è firmato digitalmente
			if (flgTipoProv != null && !"".equals(flgTipoProv) && isEditing && 
				uriFile != null && !"".equals(uriFile) && infoFile != null && !infoFile.isFirmato() /*&& !mimetype.startsWith("image")*/) {
				String idDoc = lDynamicForm.getValueAsString("idDoc");
				boolean isChanged = lDynamicForm.getValue("isChanged") != null && (Boolean) lDynamicForm.getValue("isChanged");
				boolean flgTimbratoFilePostReg = lDynamicForm.getValue("flgTimbratoFilePostReg") != null && (Boolean) lDynamicForm.getValue("flgTimbratoFilePostReg");
				boolean isNewOrChanged = idDoc == null || "".equals(idDoc) || isChanged;			
				// se il file è nuovo oppure era già presente e la stored non indicava che il file era timbrato 
				if (isNewOrChanged || !flgTimbratoFilePostReg) {  
					return true;
				}				
			}
		}
		// Nel caso in cui il check non è visibile il valore va settato sempre a false
		lDynamicForm.setValue("flgTimbraFilePostReg",false);
		return false;	
	}
	
	public class DocumentForm extends DynamicForm {

		public DocumentForm() {
			setWrapItemTitles(false);
		}

		@Override
		public void setValue(String fieldName, String value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, double value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, boolean value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, int[] value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, Date value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, RelativeDate value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, String[] value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, Map value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, JavaScriptObject value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, DataClass value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, DataClass[] value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, Record value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, Record[] value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValues(Map values) {
			super.setValues(values);
			update();
		}

		@Override
		public void clearValue(String fieldName) {
			super.clearValue(fieldName);
			update();
		}

		@Override
		public void clearValues() {
			super.clearValues();
			update();
		}

		public void update() {
			setCanFocus(false);
			setTabIndex(-1);
			Record lRecord = new Record(lDynamicForm.getValues());
			storeValue(lRecord);			
		}
	}
	
	public boolean showUploadItem() {
		return true;
	}
	
	public String getWarningMessageOnUpload() {
		return null;
	}
		
	public boolean showPreviewButton() {
		return true;
	}
	
	public boolean showSalvaVersConOmissisInPreview() {
		return false;
	}
	
	public void executeSalvaVersConOmissisInPreview(Record record) {
	
	}
	
	public void executeSalvaInPreview(Record record) {
		fileButtons.executeSalvaInPreview(record);
	}
	
	
	public boolean showPreviewEditButton() {
		return true;
	}
	
	public boolean canBeEditedByApplet() {
		return false;
	}
	
	public boolean showEditButton() {
		return true;
	}			
	
	public boolean showGeneraDaModelloButton() {
		return false;
	}
	
	public boolean showVisualizzaVersioniMenuItem() {
		return true;
	}
	
	public boolean showDownloadMenuItem() {
		return true;
	}
	
	public boolean showDownloadButtomOutsideAltreOpMenu() {
		return false;
	}
	
	public boolean showAcquisisciDaScannerMenuItem() {
		return true;
	}
	
	public boolean showFirmaMenuItem() {
		return true;
	}
	
	public boolean showTimbraBarcodeMenuItems() {
		return false;
	}			
	
	public boolean hideTimbraMenuItems() {
		return false;
	}
	
	public boolean showEliminaMenuItem() {
		return true;
	}
	
	public boolean showFileFirmatoDigButton() {
		return true;
	}
	
	public boolean isAttivaTimbraturaFilePostReg() {
		return false;
	}
	
	public boolean showFlgSostituisciVerPrecItem() {
		return false;
	}
	
	public boolean isNomeFileEditable() {
		return true;
	}
	
	public void manageOnChangedDocument() {
		
	}
	
	private void manageOnChanged() {
		manageOnChangedDocument();
	}
	
	public void manageOnChangedDocumentFile() {
		
	}
	
	private void manageOnChangedFile() {		
		Record lRecordForm = new Record(lDynamicForm.getValues());
		lRecordForm.setAttribute("isChanged", true);
		setValue(lRecordForm);
		manageOnChangedDocumentFile();
		nomeFileItem.validate();
		lDynamicForm.redraw();		
		if (showFlgSostituisciVerPrecItem()) {
			Record recordOrig = valuesOrig != null ? new Record(valuesOrig) : null;
			InfoFileRecord precInfo = recordOrig != null && recordOrig.getAttributeAsObject("infoFile") != null
					? new InfoFileRecord(recordOrig.getAttributeAsObject("infoFile")) : null;
			String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
			InfoFileRecord info = lDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(lDynamicForm.getValue("infoFile")) : null;
			String impronta = info != null ? info.getImpronta() : null;
			if(flgSostituisciVerPrecItem != null) {
				if (precImpronta != null && impronta != null && !impronta.equals(precImpronta)) {
					flgSostituisciVerPrecItem.show();
				} else {
					flgSostituisciVerPrecItem.hide();
				}
			}			
		}
		if(isAttivaTimbraturaFilePostReg() && showFlgTimbraFilePostReg()) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(lDynamicForm.getValue("infoFile"));
			if (canBeTimbrato(lInfoFileRecord) && !lInfoFileRecord.isFirmato()) {
				if (AurigaLayout.getParametroDBAsBoolean("FORZA_TIMBRATURA_FILE_POST_REG")) {
					if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaApponiTimbro")) {
						boolean flgTimbraFilePostRegValue = Boolean.parseBoolean(lDynamicForm.getValueAsString("flgTimbraFilePostReg"));
						
	//					if (!(flgTimbraFilePostRegItem.getValue() != null && (Boolean) flgTimbraFilePostRegItem.getValue())) {
						if (!flgTimbraFilePostRegValue) {
							showOpzioniTimbratura();
						} else {
							SC.ask("Vuoi verificare/modificare le opzioni di timbratura per il nuovo file ?",
									new BooleanCallback() {
	
										@Override
										public void execute(Boolean value) {
											if (value) {
												showOpzioniTimbratura();
											}
										}
									});
						}
					}
					flgTimbraFilePostRegItem.setValue(true);
				} else {
					flgTimbraFilePostRegItem.setValue(false);
				}
			} else {
				flgTimbraFilePostRegItem.setValue(false);
			}	
		}
	}

	public boolean isAttivaTimbroTipologia() {
		return false;
	}
	
	public Record getRecordCaricaModello(String idModello, String tipoModello) {
		final Record modelloRecord = new Record();
		modelloRecord.setAttribute("idModello", idModello);
		modelloRecord.setAttribute("tipoModello", tipoModello);
		modelloRecord.setAttribute("idUd", lDynamicForm.getValueAsString("idUd"));
		return modelloRecord;
	}
	
	public GWTRestDataSource getGeneraDaModelloDataSource(String idModello, String tipoModello, String flgConvertiInPdf) {
		final GWTRestDataSource lGeneraDaModelloDataSource = new GWTRestDataSource("GeneraDaModelloWithDatiDocDataSource");
		lGeneraDaModelloDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
		return lGeneraDaModelloDataSource;
	}
	
	public boolean isFormatoAmmesso(InfoFileRecord info) {	
		return true;
	}
	
	public void updateAfterUpload(final String displayFileName, final String uri, Record infoFile) {
		fileButtons.updateAfterUpload(displayFileName, uri, infoFile);
	}

	public boolean isNotNull() {
		return lDynamicForm.getValue("uriFile") != null && !"".equals(lDynamicForm.getValue("uriFile"));
	}
	
	public boolean enableAcquisisciDaScannerMenu() {
		return true;
	}
	
	public boolean getEditingProtocollo() {
		return getEditing();
	}
	
	public void setCanEditProtocollo(Boolean canEdit) {
		nomeFileItem.setCanEdit(canEdit);
		fileButtons.setCanEdit(canEdit);
	}
	
	public Record getRecordProtocolloDoc() {
		return getDetailRecord();
	}
	 
	public Record getDetailRecord() {
		return detailRecord;
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}
	 
	@Override
	public void clearErrors() {
		 if (getForm() != null) { 
			 getForm().clearErrors(true);
		 }
	}

	@Override
	public Map getMapErrors() {
		 Map errors = null;
		 if (getForm() != null) {
			 if (getForm().getErrors() != null && getForm().getErrors().size() > 0) {
				 if (errors == null) {
					 errors = new HashMap();
				 }
				 errors.putAll(getForm().getErrors());
			 }
		 }
		 return errors;
	}
	
	public void manageOnDestroy() {
		
	}

}
