/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.archivio;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

/**
 * 
 * @author dbe4235
 *
 */

public class EsibentePraticaPregressaItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		EsibentePraticaPregressaCanvas lEsibentePraticaPregressaCanvas = new EsibentePraticaPregressaCanvas();
		return lEsibentePraticaPregressaCanvas;
	}

}
