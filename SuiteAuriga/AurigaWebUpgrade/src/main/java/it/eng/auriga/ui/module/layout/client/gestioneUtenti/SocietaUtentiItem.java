/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class SocietaUtentiItem extends ReplicableItem {
	
	private String idSocieta;
	
	
	@Override
	public ReplicableCanvas getCanvasToReply() {		
		SocietaUtentiCanvas lSocietaUtentiCanvas = new SocietaUtentiCanvas();
		return lSocietaUtentiCanvas;
	}


	public String getIdSocieta() {
		return idSocieta;
	}


	public void setIdSocieta(String idSocieta) {
		this.idSocieta = idSocieta;
	}

	
}
