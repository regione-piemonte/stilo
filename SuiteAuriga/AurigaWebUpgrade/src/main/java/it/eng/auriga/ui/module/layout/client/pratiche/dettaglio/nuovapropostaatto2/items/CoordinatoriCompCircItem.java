/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;

public class CoordinatoriCompCircItem extends GroupReplicableItem {

	public CoordinatoriCompCircItem(String title) {
		super(title);
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		CoordinatoriCompCircCanvas lCoordinatoriCompCircCanvas = new CoordinatoriCompCircCanvas(this);
		return lCoordinatoriCompCircCanvas;
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
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
			((CoordinatoriCompCircCanvas)lReplicableCanvas).resetAfterChangedParams();
		}
	}

}
