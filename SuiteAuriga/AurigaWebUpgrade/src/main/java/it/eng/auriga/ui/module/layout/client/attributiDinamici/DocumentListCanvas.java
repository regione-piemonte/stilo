/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DocumentListCanvas extends ReplicableCanvas {
	
	private DocumentItem documentItem;

	private ReplicableCanvasForm mDynamicForm;

	public DocumentListCanvas(ReplicableItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		documentItem = new DocumentItem() {
			
			@Override
			public int getWidth() {
				return 450;
			}
			
			@Override
			protected boolean showAltreOperazioni() {
				return ((DocumentListItem) getItem()).showAltreOpInDocumentListItem();
			}
			
			@Override
			public boolean showUploadItem() {
				return ((DocumentListItem) getItem()).showUploadItemInDocumentListItem();
			}
				
			@Override
			public boolean showPreviewButton() {
				return ((DocumentListItem) getItem()).showPreviewButtonInDocumentListItem();
			}
			
			@Override
			public boolean showPreviewEditButton() {
				return ((DocumentListItem) getItem()).showPreviewEditButtonInDocumentListItem();
			}
			
			@Override
			public boolean canBeEditedByApplet() {
				return ((DocumentListItem) getItem()).canBeEditedByAppletInDocumentListItem();
			}
			
			@Override
			public boolean showEditButton() {
				return ((DocumentListItem) getItem()).showEditButtonInDocumentListItem();
			}			
			
			@Override
			public boolean showGeneraDaModelloButton() {
				return ((DocumentListItem) getItem()).showGeneraDaModelloButtonInDocumentListItem();
			}
			
			public boolean showVisualizzaVersioniMenuItem() {
				return ((DocumentListItem) getItem()).showVisualizzaVersioniMenuItemInDocumentListItem();
			}
			
			public boolean showDownloadMenuItem() {
				return ((DocumentListItem) getItem()).showDownloadMenuItemInDocumentListItem();
			}
			
			@Override
			public boolean showDownloadButtomOutsideAltreOpMenu() {
				return ((DocumentListItem) getItem()).showDownloadButtomOutsideAltreOpMenuInDocumentListItem();
			}
			
			@Override
			public boolean showAcquisisciDaScannerMenuItem() {
				return ((DocumentListItem) getItem()).showAcquisisciDaScannerMenuItemInDocumentListItem();
			}
			
			@Override
			public boolean showFirmaMenuItem() {
				return ((DocumentListItem) getItem()).showFirmaMenuItemInDocumentListItem();
			}
			
			@Override
			public boolean showTimbraBarcodeMenuItems() {
				return ((DocumentListItem) getItem()).showTimbraBarcodeMenuItemsInDocumentListItem();
			}			
			
			@Override
			public boolean hideTimbraMenuItems() {
				return ((DocumentListItem) getItem()).hideTimbraMenuItemsInDocumentListItem();
			}
			
			@Override
			public boolean showEliminaMenuItem() {
				return ((DocumentListItem) getItem()).showEliminaMenuItemInDocumentListItem();
			}
			
			@Override
			public boolean showFileFirmatoDigButton() {
				return ((DocumentListItem) getItem()).showFileFirmatoDigButtonInDocumentListItem();
			}
			
			@Override
			public boolean isAttivaTimbraturaFilePostReg() {
				return ((DocumentListItem) getItem()).isAttivaTimbraturaFilePostRegInDocumentListItem();
			}
			
			@Override
			public boolean showFlgSostituisciVerPrecItem() {
				return ((DocumentListItem) getItem()).showFlgSostituisciVerPrecItemInDocumentListItem();
			}
			
		};
	
		documentItem.setStartRow(true);
		documentItem.setName("documento");
		documentItem.setShowTitle(false);
		
		mDynamicForm.setFields(documentItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);					
	}	
	
	public boolean isChangedAndValid() {
		boolean valid = true;
		Record documentItemValue = (mDynamicForm.getValuesAsRecord() != null) ? mDynamicForm.getValuesAsRecord().getAttributeAsRecord("documento") : null;
		if (documentItemValue == null || 
				documentItemValue.getAttribute("uriFile") == null || "".equalsIgnoreCase( documentItemValue.getAttribute("uriFile")) ||
				documentItemValue.getAttribute("nomeFile") == null || "".equalsIgnoreCase( documentItemValue.getAttribute("nomeFile"))) {
			valid = false;
		}
		return isChanged() && valid;
	}
	
	public void setFormValuesFromRecordImportaFileArchivio(Record record) {	
		editRecord(record);
		Record recordDoumento = record.getAttributeAsRecord("documento");
		documentItem.updateAfterUpload(recordDoumento.getAttribute("nomeFile"), recordDoumento.getAttribute("uriFile"), recordDoumento.getAttributeAsRecord("infoFile"));
	}
	
	public boolean showUploadItem() {
		return true;
	}
		
	public boolean showPreviewButton() {
		return true;
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
	
}
