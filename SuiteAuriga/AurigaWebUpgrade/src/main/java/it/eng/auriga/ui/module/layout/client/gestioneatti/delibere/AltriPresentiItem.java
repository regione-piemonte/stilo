/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class AltriPresentiItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		AltriPresentiCanvas lAltriPresentiCanvas = new AltriPresentiCanvas(this);
		return lAltriPresentiCanvas;
	}
	
	public String getTipologiaSessione() {
		return null;
	}

}