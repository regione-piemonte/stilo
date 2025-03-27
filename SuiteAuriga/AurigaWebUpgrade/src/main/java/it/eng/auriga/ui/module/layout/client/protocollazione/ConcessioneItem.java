/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.protocollazione;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class ConcessioneItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		ConcessioneCanvas lConcessioneCanvas = new ConcessioneCanvas();		
		return lConcessioneCanvas;
	}
}
