/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DatiContabiliADSPItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		DatiContabiliADSPCanvas lDatiContabiliADSPCanvas = new DatiContabiliADSPCanvas(this);
		return lDatiContabiliADSPCanvas;
	}
	
	public boolean showEsercizioDatiContabiliADSP() {
		return false;
	}
		
	public String getTitleEsercizioDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredEsercizioDatiContabiliADSP() {
		return false;
	}
	
	public boolean showCapitoloDatiContabiliADSP() {
		return false;
	}
		
	public String getTitleCapitoloDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredCapitoloDatiContabiliADSP() {
		return false;
	}
	
	public boolean showContoDatiContabiliADSP() {
		return false;
	}
		
	public String getTitleContoDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredContoDatiContabiliADSP() {
		return false;
	}
	
	public boolean showDecretoCIGDatiContabiliADSP() {
		return false;
	}
	
	public String getTitleDecretoCIGDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredDecretoCIGDatiContabiliADSP() {
		return false;
	}
	
	public String[] getCIGValueMap() {
		return null;
	}
	
	public RecordList getCIGCUPRecordList() {
		return null;
	}	
	
	public boolean showDecretoCUPDatiContabiliADSP() {
		return false;
	}
	
	public String getTitleDecretoCUPDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredDecretoCUPDatiContabiliADSP() {
		return false;
	}				
	
	public boolean showDecretoImportoDatiContabiliADSP() {
		return false;
	}
	
	public String getTitleDecretoImportoDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredDecretoImportoDatiContabiliADSP() {
		return false;
	}				
	
	public boolean isSkipControlloImportoMaggioreDiZeroValidator() {
		return true;
	}
	
	public boolean showDecretoOperaDatiContabiliADSP() {
		return false;
	}
	
	public String getTitleDecretoOperaDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredDecretoOperaDatiContabiliADSP() {
		return false;
	}
	
	public LinkedHashMap<String, String> getOpereADSPValueMap() {
		return null;
	}
	
	public void reloadOpereADSPValueMap() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			DatiContabiliADSPCanvas lDatiContabiliADSPCanvas = (DatiContabiliADSPCanvas) getReplicableCanvas(lHLayout);
			lDatiContabiliADSPCanvas.reloadOpereADSPValueMap();
		}
	}
	
}
