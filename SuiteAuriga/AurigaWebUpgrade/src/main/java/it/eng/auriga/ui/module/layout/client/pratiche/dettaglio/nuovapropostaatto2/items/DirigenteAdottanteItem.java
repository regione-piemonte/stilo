/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DirigenteAdottanteItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		DirigenteAdottanteCanvas lDirigenteAdottanteCanvas = new DirigenteAdottanteCanvas(this);
		return lDirigenteAdottanteCanvas;
	}
	
	public void resetAfterChangedParams() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((DirigenteAdottanteCanvas)lReplicableCanvas).resetAfterChangedParams();
		}	
	}
	
	public void manageOnChangedFlgAdottanteAncheRdP(boolean value) {
		
	}

	public void manageOnChangedFlgAdottanteAncheRUP(boolean value) {
		
	}
		
	public String getIdUdAttoDaAnn() {
		return null;
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}
	
	public boolean showFlgAncheRdP() {
		return true;
	}
	
	public boolean showFlgAncheRUP() {
		return true;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public void clearFlgAdottanteAncheRUP() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			lReplicableCanvas.getForm()[0].setValue("flgAdottanteAncheRUP", false);
		}	
	}

}
