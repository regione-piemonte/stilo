/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import com.smartgwt.client.data.Record;

public abstract class UoAssociateUtenteItem extends ReplicableItem {	
	
	private GestioneUtentiDetail detail;
	private Record detailRecord;
	
	public UoAssociateUtenteItem(GestioneUtentiDetail detail) {
		setDetail(detail);
		setDetailRecord(new Record(detail.getValuesManager().getValues()));
	}
	
	public GestioneUtentiDetail getDetail() {
		return detail;
	}
	
	public void setDetail(GestioneUtentiDetail detail) {
		this.detail = detail;
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {		
		UoAssociateUtenteCanvas lUoAssociateUtenteCanvas = new UoAssociateUtenteCanvas();
		return lUoAssociateUtenteCanvas;
	}
	
	@Override
	public void onClickHandlerNewButton() {
		String title = "Nuova associazione"; 
		Record record = new Record(detail.getValuesManager().getValues());
		
		boolean isVersioneNestle = isVersioneNestle();
		
		String mode = "new";
		
		AgganciaUtenteUOPopup agganciaUtenteUOPopup = new AgganciaUtenteUOPopup(record, title, isVersioneNestle, mode) {
			
			@Override
			public void onClickOkButton(Record formRecord, DSCallback callback) {	
				UoAssociateUtenteCanvas lastCanvas = (UoAssociateUtenteCanvas) getLastCanvas();
				if(lastCanvas != null && !lastCanvas.isChanged()) {
					lastCanvas.setFormValuesFromRecordAfterNew(formRecord);
				} else {
					UoAssociateUtenteCanvas canvas = (UoAssociateUtenteCanvas) onClickNewButton();
					canvas.setFormValuesFromRecordAfterNew(formRecord);
				}	
				markForDestroy();
			}
		};
		agganciaUtenteUOPopup.show();
	}
	
	public abstract boolean isViewMode();
	public abstract boolean isEditMode();
	public abstract boolean isNewMode();
	public abstract String getMode();
	public abstract boolean isVersioneNestle();
	
	
	public Record getDetailRecord() {
		return new Record(detail.getValuesManager().getValues());
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}
}