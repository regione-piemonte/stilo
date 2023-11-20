/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class EstensoreItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		
		EstensoreCanvas lEstensoreCanvas = new EstensoreCanvas(this);
		return lEstensoreCanvas;
	}
	
	public void resetAfterChangedParams() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((EstensoreCanvas)lReplicableCanvas).resetAfterChangedParams();
		}	
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
}