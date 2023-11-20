/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;

public class ConsiglieriItem extends GroupReplicableItem {

	public ConsiglieriItem(String title) {
		super(title);
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		ConsiglieriCanvas lConsiglieriCanvas = new ConsiglieriCanvas(this);
		return lConsiglieriCanvas;
	}
	
	public boolean showFlgFirmaInSostSindaco() {
		return false;
	}
	
	public String getTitleFlgFirmaInSostSindaco() {
		return null;
	}
				
	public boolean getDefaultValueAsBooleanFlgFirmaInSostSindaco() {
		return false;
	}
	
	public boolean isEditableFlgFirmaInSostSindaco() {
		return false;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public boolean showFlgFirmatario() {
		return false;
	}

}
