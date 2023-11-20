/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class CIGItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		CIGCanvas lCIGCanvas = new CIGCanvas(this);
		return lCIGCanvas;
	}
	
	public boolean showCUP() {
		return false;
	}
	
	public boolean showNumGara() {
		return false;
	}
	
	public String getTitleNumGara() {
		return null;
	}
	
	public boolean isRequiredNumGara() {
		return false;
	}
	
	public String getDefaultValueNumGara() {
		return null;
	}

}