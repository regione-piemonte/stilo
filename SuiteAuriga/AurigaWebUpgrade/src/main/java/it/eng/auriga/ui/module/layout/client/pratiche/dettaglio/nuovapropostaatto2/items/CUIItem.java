/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class CUIItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		CUICanvas lCUICanvas = new CUICanvas(this);
		return lCUICanvas;
	}
	
	public boolean showAnnoRif() {
		return false;
	}
		
	public String getTitleAnnoRif() {
		return null;
	}
	
	public boolean isRequiredAnnoRif() {
		return false;
	}
	
	public String getDefaultValueAnnoRif() {
		return null;
	}
	
}