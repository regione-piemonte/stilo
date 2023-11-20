/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class ModificaDocumentiFascicoliStrutturaItem extends ReplicableItem {	
	
	private GestioneUtentiDetail detail;
	private Record detailRecord;
	
	public ModificaDocumentiFascicoliStrutturaItem(GestioneUtentiDetail detail) {
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
		return new DocFascAssegnatiStrutturaModCanvas();
	}
	
	@Override
	public void onClickHandlerNewButton() {
		
		String title = "Modifica di documenti e fascicoli assegnati alla U.O"; 
		Record record = new Record(detail.getValuesManager().getValues());
		
		String mode = "new";
		
		AgganciaDocumentiFascicoliStrutturaUOPopup agganciaDocumentiFascicoliStrutturaUOPopup = new AgganciaDocumentiFascicoliStrutturaUOPopup(record, title, mode) {
			
			@Override
			public void onClickOkButton(Record formRecord, DSCallback callback) {	
				DocFascAssegnatiStrutturaModCanvas lastCanvas = (DocFascAssegnatiStrutturaModCanvas) getLastCanvas();
				if(lastCanvas != null && !lastCanvas.isChanged()) {
					lastCanvas.setFormValuesFromRecordAfterNew(formRecord);
				} else {
					DocFascAssegnatiStrutturaModCanvas canvas = (DocFascAssegnatiStrutturaModCanvas) onClickNewButton();
					canvas.setFormValuesFromRecordAfterNew(formRecord);
				}	
				markForDestroy();
			}
		};
		agganciaDocumentiFascicoliStrutturaUOPopup.show();
	}
	
	public abstract boolean isViewMode();
	public abstract boolean isEditMode();
	public abstract boolean isNewMode();
	public abstract String getMode();
	
	public Record getDetailRecord() {
		return new Record(detail.getValuesManager().getValues());
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}
}