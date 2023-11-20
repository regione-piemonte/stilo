/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.fields.FormItem;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class SelezionaScrivaniaItem extends ReplicableItem {
	
	@Override
	public ReplicableCanvas getCanvasToReply() {		
		SelezionaScrivaniaCanvas lSelezionaScrivaniaCanvas = new SelezionaScrivaniaCanvas(this);		
		return lSelezionaScrivaniaCanvas;
	}
	
	public int getSelectItemOrganigrammaWidth() {
		return 500;
	}
	
	public List<FormItem> getCustomItems() {
		return new ArrayList<FormItem>();
	}

	public void manageChangedScrivaniaSelezionata() {

	}
	
	public class SelezionaScrivaniaMultiLookupOrganigramma extends LookupOrganigrammaPopup {

		private List<SelezionaScrivaniaCanvas> multiLookupList = new ArrayList<SelezionaScrivaniaCanvas>(); 
		
		public SelezionaScrivaniaMultiLookupOrganigramma(Record record) {
			super(record, false, 2);			
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			SelezionaScrivaniaCanvas lastCanvas = (SelezionaScrivaniaCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecord(record);
				multiLookupList.add(lastCanvas);
			} else {
				SelezionaScrivaniaCanvas canvas = (SelezionaScrivaniaCanvas) onClickNewButton();
				canvas.setFormValuesFromRecord(record);
				multiLookupList.add(canvas);
			}					
		}		
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(SelezionaScrivaniaCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("organigramma").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}	
		
		@Override
		public String getFinalita() {
			return getFinalitaLookup();
		}
		
	}
	
	@Override
	public boolean hasDefaultValue() {
		return true;
	}
	
	@Override
	public Record getCanvasDefaultRecord() {
		if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			Record lRecord = new Record();	
			lRecord.setAttribute("codUoScrivania", AurigaLayout.getCodRapidoOrganigramma());
			return lRecord;				
		}
		return null;
	}
	
	public RecordList getValueAsRecordList() {
		RecordList value = new RecordList();
		for(ReplicableCanvas canvas : getAllCanvas()) {			
			value.add(canvas.getFormValuesAsRecord());
		}
		return value;
	}	
	
	public boolean showLookupOrganigrammaButton() {
		return true;
	}
	
	public String getFinalitaSelect() {
		return null;
	}
	
	public String getFinalitaLookup() {
		return "SEL_SV";
	}
	
	public String getFlgSoloValide() {
		return "true";
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public void resetAfterChangedUoProponente() {
		if(getAltriParamLoadCombo() != null && getAltriParamLoadCombo().indexOf("$ID_UO_PROPONENTE$") != -1) {	
			resetAfterChangedParams();
		}		
	}
	
	public void resetAfterChangedParams() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((SelezionaScrivaniaCanvas)lReplicableCanvas).resetAfterChangedParams();
		}	
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}	
	
}
