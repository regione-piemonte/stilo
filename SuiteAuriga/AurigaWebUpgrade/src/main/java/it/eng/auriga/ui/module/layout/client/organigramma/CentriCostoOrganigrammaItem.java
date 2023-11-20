/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

/**
 * 
 * @author Antonio Peluso
 *
 */

public abstract class CentriCostoOrganigrammaItem extends ReplicableItem{
		
	@Override
	public ReplicableCanvas getCanvasToReply() {
		CentriCostoOrganigrammaCanvas lCentriCostoOrganigrammaCanvas = new CentriCostoOrganigrammaCanvas(this);
		return lCentriCostoOrganigrammaCanvas;
	}

	public abstract boolean isNewMode();
}