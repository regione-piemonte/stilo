/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DettaglioEmailDestinatarioItem extends ReplicableItem {

	private String idEmail;

	@Override
	public ReplicableCanvas getCanvasToReply() {
		
		return new DettaglioEmailDestinatarioCanvas();
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	
}
