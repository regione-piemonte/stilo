/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class RespVistiConformitaItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		RespVistiConformitaCanvas lRespVistiConformitaCanvas = new RespVistiConformitaCanvas(this);
		return lRespVistiConformitaCanvas;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}

	public boolean showFlgFirmatario() {
		return true;
	}
	
	public boolean showMotivi() {
		return true;
	}
	
	public boolean isRequiredMotivi() {
		return true;
	}
	
}
