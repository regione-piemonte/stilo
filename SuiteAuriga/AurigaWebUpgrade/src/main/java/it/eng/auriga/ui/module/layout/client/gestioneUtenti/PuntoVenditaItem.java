/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;


public class PuntoVenditaItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {		
		PuntoVenditaCanvas lPuntoVenditaCanvas = new PuntoVenditaCanvas();
		return lPuntoVenditaCanvas;
	}
	
}
