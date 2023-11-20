/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class IstruttoreItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		
		IstruttoreCanvas lIstruttoreCanvas = new IstruttoreCanvas(this);
		return lIstruttoreCanvas;
	}
	
	public boolean selectUniqueValueAfterChangedParams() {
		return false;
	}
	
	public void resetAfterChangedParams() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((IstruttoreCanvas)lReplicableCanvas).resetAfterChangedParams();
		}	
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
}