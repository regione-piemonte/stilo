/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;

public class UtenteRifAttoConsiglioItem extends GroupReplicableItem {

	public UtenteRifAttoConsiglioItem(String title) {
		super(title);
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		UtenteRifAttoConsiglioCanvas lUtenteRifAttoConsiglioCanvas = new UtenteRifAttoConsiglioCanvas(this);
		return lUtenteRifAttoConsiglioCanvas;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
}
