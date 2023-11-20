/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class ProponentiItem extends ReplicableItem {
	
	@Override
	public ReplicableCanvas getCanvasToReply() {		
		ProponentiCanvas lProponentiCanvas = new ProponentiCanvas(this);		
		return lProponentiCanvas;
	}
	
	public boolean isFromProponentiDetailInGridItem() {
		return false;
	}
	
	public boolean isAbilSelezioneProponentiEstesa() {
		return false;
	}
		
	public String getIdTipoDocProposta() {
		return null;
	}
	
	public LinkedHashMap<String, String> getProponentiValueMap() {
		return null;
	}
	
	protected DataSource getProponentiDS() {
		DataSource lUfficioProponenteDS = new DataSource();
		lUfficioProponenteDS.setClientOnly(true);		
		if(getProponentiValueMap() != null) {
			Record[] lUfficioProponenteRecords = new Record[getProponentiValueMap().size()];			
			int count = 0;
			for(String key : getProponentiValueMap().keySet()) {
				String value = getProponentiValueMap().get(key);				
				Record lRecord = new Record();
				lRecord.setAttribute("idUo", key);
				lRecord.setAttribute("descrizione", value);
				lUfficioProponenteRecords[count++] = lRecord;				
			}
			lUfficioProponenteDS.setTestData(lUfficioProponenteRecords);	
		}
		return lUfficioProponenteDS;
	}
	
	public LinkedHashMap<String, String> getSelezioneProponentiValueMap() {
		return null;	
	}
	
	public LinkedHashMap<String, String> getFlgUfficioGareProponentiMap() {
		return null;
	}	
		
	public boolean showIdUo() {
		return false;
	}
	
	public String getTitleIdUo() {
		return null;
	}
	
	public boolean isRequiredIdUo() {
		return false;
	}
	
	public String getAltriParamLoadComboIdUo() {
		return null;
	}
	
	public boolean isEditableIdUo() {
		return false;
	}
			
	public boolean showIdScrivaniaRdP() {
		return false;
	}
	
	public String getTitleIdScrivaniaRdP() {
		return null;
	}
	
	public boolean isRequiredIdScrivaniaRdP() {
		return false;
	}
	
	public String getAltriParamLoadComboIdScrivaniaRdP() {
		return null;
	}
	
	public boolean isEditableIdScrivaniaRdP() {
		return false;
	}
	
	public boolean showIdScrivaniaDirigente() {
		return false;
	}
	
	public String getTitleIdScrivaniaDirigente() {
		return null;
	}
	
	public boolean isRequiredIdScrivaniaDirigente() {
		return false;
	}
	
	public String getAltriParamLoadComboIdScrivaniaDirigente() {
		return null;
	}
	
	public boolean isEditableIdScrivaniaDirigente() {
		return false;
	}
	
	public boolean showIdScrivaniaDirettore() {
		return false;
	}
	
	public String getTitleIdScrivaniaDirettore() {
		return null;
	}
	
	public boolean isRequiredIdScrivaniaDirettore() {
		return false;
	}
	
	public String getAltriParamLoadComboIdScrivaniaDirettore() {
		return null;
	}
	
	public boolean isEditableIdScrivaniaDirettore() {
		return false;
	}
	
	public boolean showTipoVistoScrivaniaDirigente() {
		return false;
	}
	
	public boolean isRequiredTipoVistoScrivaniaDirigente() {
		return false;
	}
	
	public boolean showTipoVistoScrivaniaDirettore() {
		return false;
	}
	
	public boolean isRequiredTipoVistoScrivaniaDirettore() {
		return false;
	}
	
	public int getSelectItemOrganigrammaWidth() {
		return 500;
	}
	
	public void manageChangedUoSelezionata() {

	}
	
	public class ProponentiMultiLookupOrganigramma extends LookupOrganigrammaPopup {

		private List<ProponentiCanvas> multiLookupList = new ArrayList<ProponentiCanvas>(); 
		
		public ProponentiMultiLookupOrganigramma(Record record) {
			super(record, false, 0);			
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			ProponentiCanvas lastCanvas = (ProponentiCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecord(record);
				multiLookupList.add(lastCanvas);
			} else {
				ProponentiCanvas canvas = (ProponentiCanvas) onClickNewButton();
				canvas.setFormValuesFromRecord(record);
				multiLookupList.add(canvas);
			}					
		}		
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(ProponentiCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("organigramma").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}	
		
		@Override
		public String getFinalita() {
			return null;
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
			lRecord.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
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
	
	public String getFlgSoloValide() {
		return "true";
	}
	
	// Questo metodo imposta il canEdit solo sui ReplicableCanvas, senza agire sulla visibilità dei bottoni di add e remove
	public void setCanEditCanvas(Boolean canEdit) {
		if (getCanvas() != null) {
			final VLayout lVLayout = getVLayout();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ReplicableCanvas) {
							ReplicableCanvas lReplicableCanvas = (ReplicableCanvas) lHLayoutMember;
							lReplicableCanvas.setCanEdit(canEdit);
						}
					}
				}
			}
		}
	}
	
}
