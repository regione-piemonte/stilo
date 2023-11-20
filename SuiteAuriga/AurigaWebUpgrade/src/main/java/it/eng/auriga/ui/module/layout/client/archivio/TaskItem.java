/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

import com.smartgwt.client.widgets.ImgButton;

public abstract class TaskItem extends ReplicableItem{
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		TaskCanvas lTaskCanvas = new TaskCanvas();
		return lTaskCanvas;
	}
	
	@Override
	protected ImgButton[] createAddButtons() {
		return new ImgButton[]{};	
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		
		super.setCanEdit(false);
	}

	public abstract void reloadTask();
	
}