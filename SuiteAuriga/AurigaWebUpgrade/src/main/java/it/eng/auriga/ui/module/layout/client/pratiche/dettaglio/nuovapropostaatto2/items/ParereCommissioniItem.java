/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class ParereCommissioniItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		ParereCommissioniCanvas lParereCommissioniCanvas = new ParereCommissioniCanvas(this);
		return lParereCommissioniCanvas;
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
			((ParereCommissioniCanvas)lReplicableCanvas).resetAfterChangedParams();
		}	
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}

}