/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;

public class AssessoriItem extends GroupReplicableItem {

	public AssessoriItem(String title) {
		super(title);		
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		AssessoriCanvas lAssessoriCanvas = new AssessoriCanvas(this);
		return lAssessoriCanvas;
	}
	
	public boolean getFlgAbilitaAutoFetchDataSelectOrganigramma() {
		return false;
	}
	
	public boolean selectUniqueValueAfterChangedParams() {
		return false;
	}

	public void resetAfterChangedUoProponente() {
		if(getAltriParamLoadCombo() != null && getAltriParamLoadCombo().indexOf("$ID_UO_PROPONENTE$") != -1) {	
			resetAfterChangedParams();
		}
	}
	
	public void resetAfterChangedParams() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((AssessoriCanvas)lReplicableCanvas).resetAfterChangedParams();
		}	
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}	
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public boolean showFlgFirmatario() {
		return false;
	}

}
