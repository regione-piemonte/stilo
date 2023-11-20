/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ChangeCanEditEvent;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public class DocumentListItem extends ReplicableItem {
		
	DocumentListItem documentListItemInstance = this; 
	protected DynamicForm addButtonsForm;
	protected ImgButtonItem importaFileArchivioButton;
			
	/*INFO DOCUMENTO*/
	protected FileUploadItemWithFirmaAndMimeType uploadFileItem;
	protected HiddenItem uriFileHiddenItem;
	protected HiddenItem flgFileFirmatoHiddenItem;
	protected HiddenItem mimeTypeFileHiddenItem;
	protected HiddenItem improntaFileHiddenItem;
	protected HiddenItem algoritmoImprontaHiddenItem;
	protected HiddenItem encodingImprontaHiddenItem;
		
	@Override
	public DocumentListCanvas getCanvasToReply() {		
		DocumentListCanvas lDocumentListCanvas = new DocumentListCanvas(documentListItemInstance);
		return lDocumentListCanvas;
	}
	
	@Override
	public Record getDefaultRecord() {
		return super.getDefaultRecord();
	}
	
	@Override
	public HLayout createAddButtonsLayout() {
		HLayout addButtonsLayout = super.createAddButtonsLayout();

		uriFileHiddenItem = new HiddenItem("uriFile");
		flgFileFirmatoHiddenItem = new HiddenItem("flgFileFirmato");
		mimeTypeFileHiddenItem = new HiddenItem("mimeTypeFile");
		improntaFileHiddenItem = new HiddenItem("improntaFile");
		encodingImprontaHiddenItem = new HiddenItem("encodingImpronta");
		algoritmoImprontaHiddenItem = new HiddenItem("algoritmoImpronta");
		
		importaFileArchivioButton = new ImgButtonItem("importaFileArchivioButton", "buttons/importaFileDocumenti.png", getImportaFileArchivioButtonTitle());
		importaFileArchivioButton.setEndRow(false);
		importaFileArchivioButton.setColSpan(1);
		importaFileArchivioButton.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {

				ImportaFileLookupArchivio lookupArchivioPopup = new ImportaFileLookupArchivio(null);
				lookupArchivioPopup.show();
			}
			
		});
		importaFileArchivioButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showImportaFileArchivioButtonDocumentList();
			}
		});


		/***********************************************************************************************************************************************************/
		/*				L'UPLOAD VIENE MOSTRATO SOLO PER IL TIPO LINK
		/***********************************************************************************************************************************************************/
		uploadFileItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				addButtonsForm.clearErrors(true);
				addButtonsForm.setValue("displayFile", displayFileName);
				addButtonsForm.setValue("uriFile", uri);				
				changedEventAfterUpload(displayFileName, uri);
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
				uploadFileItem.getCanvas().redraw();
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				flgFileFirmatoHiddenItem.setValue(info.isFirmato());
				mimeTypeFileHiddenItem.setValue(info.getMimetype());
				improntaFileHiddenItem.setValue(info.getImpronta());
			}
		});
		uploadFileItem.setColSpan(1);
		uploadFileItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				uploadFileItem.setAttribute("nascosto", !showUploadFileButtonInDocumentList());	
				return showUploadFileButtonInDocumentList();
			}
		});	
		
		addButtonsForm = new DynamicForm();
		addButtonsForm.setNumCols(10);
		addButtonsForm.setColWidths(1,1,1,1,1,1,1,1,"*");
		addButtonsForm.setMargin(0);		
		addButtonsForm.setFields(importaFileArchivioButton,
								 uploadFileItem,
				                 uriFileHiddenItem,
                                 flgFileFirmatoHiddenItem,
                                 mimeTypeFileHiddenItem,
                                 improntaFileHiddenItem,
                                 encodingImprontaHiddenItem,
                                 algoritmoImprontaHiddenItem
				               );

		addButtonsLayout.addMember(addButtonsForm);

		return addButtonsLayout;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setEditing(canEdit);
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
		if(getForm() != null && getForm().getDetailSection() != null && getForm().getDetailSection().showFirstCanvasWhenEmptyAfterOpen() && getForm().getDetailSection().isOpen()) {
			try {
				if (getEditing() != null && getEditing() && getTotalMembers() == 0) {
					onClickNewButton();
				} else if(getTotalMembers() == 1 && !hasValue()){
					removeEmptyCanvas();
				}
			} catch (Exception e) {
			}
		}
		this.fireEvent(new ChangeCanEditEvent(instance));
	}
	
	@Override
	protected ImgButton[] createAddButtons() {
		if (hideNewButtonDocumentList()) {
			return new ImgButton[0];
		} else {
			return super.createAddButtons();
		}
	}
	
	public boolean showUploadItemInDocumentListItem() {
		return true;
	}
		
	public boolean showPreviewButtonInDocumentListItem() {
		return true;
	}
	
	public boolean showPreviewEditButtonInDocumentListItem() {
		return true;
	}
	
	public boolean canBeEditedByAppletInDocumentListItem() {
		return false;
	}
	
	public boolean showEditButtonInDocumentListItem() {
		return true;
	}			
	
	public boolean showGeneraDaModelloButtonInDocumentListItem() {
		return false;
	}
	
	public boolean showVisualizzaVersioniMenuItemInDocumentListItem() {
		return true;
	}
	
	public boolean showDownloadMenuItemInDocumentListItem() {
		return true;
	}
	
	public boolean showDownloadButtomOutsideAltreOpMenuInDocumentListItem() {
		return false;
	}
	
	public boolean showAcquisisciDaScannerMenuItemInDocumentListItem() {
		return true;
	}
	
	public boolean showFirmaMenuItemInDocumentListItem() {
		return true;
	}
	
	public boolean showTimbraBarcodeMenuItemsInDocumentListItem() {
		return false;
	}			
	
	public boolean hideTimbraMenuItemsInDocumentListItem() {
		return false;
	}
	
	public boolean showEliminaMenuItemInDocumentListItem() {
		return true;
	}
	
	public boolean showFileFirmatoDigButtonInDocumentListItem() {
		return true;
	}
	
	public boolean isAttivaTimbraturaFilePostRegInDocumentListItem() {
		return false;
	}
	
	public boolean showFlgSostituisciVerPrecItemInDocumentListItem() {
		return false;
	}
	
	public boolean showAltreOpInDocumentListItem() {
		return true;
	}
	
	public boolean hideNewButtonDocumentList() {
		return false;
	}
	
	public boolean showImportaFileArchivioButtonDocumentList() {
		return false;
	}
	
	
	public boolean showUploadFileButtonInDocumentList() {
		return false;
	}
	
	
	public String getImportaFileArchivioButtonTitle() {
		return "Seleziona dall'archivio";
	}
	
	public String getFinalitaImportaFileLookupArchivio() {
		return "";
	}
	
	public class ImportaFileLookupArchivio extends LookupArchivioPopup {

		private List<Record> multiLookupList = new ArrayList<Record>();

		public ImportaFileLookupArchivio(Record record) {
			super(record, null, false);
		}
		
		@Override
		public String getWindowTitle() {
			return getImportaFileArchivioButtonTitle();
		}
		
		@Override
		public String getFinalita() {
			return getFinalitaImportaFileLookupArchivio();
		}

		@Override
		public void manageOnCloseClick() {
			super.manageOnCloseClick();
			if ((multiLookupList != null) && (multiLookupList.size() > 0)) {
				Layout.showWaitPopup("Importazione documenti in corso. Attendere...");
				RecordList lRecordListDocumentToImport = new RecordList();
				for (Record record : multiLookupList) {
					Record lRecordToLoad = new Record();
					lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
					lRecordListDocumentToImport.add(lRecordToLoad);
				}	
				Record recordToPass = new Record();
				recordToPass.setAttribute("listaRecord", lRecordListDocumentToImport);
				final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
				lProtocolloDataSource.executecustom("getElencoFileForImportInDocumentItem", recordToPass, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							RecordList listaFile = response.getData()[0].getAttributeAsRecordList("listaRecord");
							for (int i = 0; i < listaFile.getLength(); i++) {
								Record recordFile = listaFile.get(i);
								Record recordToPass = new Record();
								recordToPass.setAttribute("documento", recordFile);
								DocumentListCanvas lastCanvas = (DocumentListCanvas) getLastCanvas();
								if (lastCanvas != null && !lastCanvas.isChangedAndValid()) {
									lastCanvas.setFormValuesFromRecordImportaFileArchivio(recordToPass);
								} else {
									DocumentListCanvas canvas = (DocumentListCanvas) onClickNewButton();
									canvas.setFormValuesFromRecordImportaFileArchivio(recordToPass);										
								}
							}
						}
						Layout.hideWaitPopup();
					}
				});
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

	protected void changedEventAfterUpload(final String displayFileName, final String uri) {
			
		Record record = new Record();
		record.setAttribute("uri", uri);
		record.setAttribute("displayFilnename", displayFileName);

		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ContenutoFoglioImportatoDataSource");
		lGwtRestDataSource.executecustom("calcolaInfoFile", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					InfoFileRecord infoFile = new InfoFileRecord(record);
					
					Record recordFile = new Record();
					recordFile.setAttribute("uriFile", uri);
					recordFile.setAttribute("nomeFile", displayFileName);
					recordFile.setAttribute("infoFile", infoFile);
					
					Record recordToPass = new Record();
					recordToPass.setAttribute("documento", recordFile);
					
					DocumentListCanvas lastCanvas = (DocumentListCanvas) getLastCanvas();
					if (lastCanvas != null && !lastCanvas.isChangedAndValid()) {
						lastCanvas.setFormValuesFromRecordImportaFileArchivio(recordToPass);
					} else {
						DocumentListCanvas canvas = (DocumentListCanvas) onClickNewButton();
						canvas.setFormValuesFromRecordImportaFileArchivio(recordToPass);										
					}
				}
			}
		});
	}
}