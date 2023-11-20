/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;

public class ProponenteAttoConsiglioItem extends GroupReplicableItem {

	public ProponenteAttoConsiglioItem(String title) {
		super(title);
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		ProponenteAttoConsiglioCanvas lProponenteAttoConsiglioCanvas = new ProponenteAttoConsiglioCanvas(this);
		return lProponenteAttoConsiglioCanvas;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
}
