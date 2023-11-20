/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DirigentiConcertoItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		DirigentiConcertoCanvas lDirigentiConcertoCanvas = new DirigentiConcertoCanvas(this);
		return lDirigentiConcertoCanvas;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public boolean showFlgFirmatario() {
		return true;
	}

}
