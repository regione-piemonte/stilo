/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class FlussiAssociatiWorkflowItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		FlussiAssociatiWorkflowCanvas flussiAssociatiWorkflowCanvas = new FlussiAssociatiWorkflowCanvas();
		return flussiAssociatiWorkflowCanvas;
	}

}
