/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class AreeCommercialiItem extends ReplicableItem {
	
	private String idAreaCommerciali;
	
	
	@Override
	public ReplicableCanvas getCanvasToReply() {		
		AreeCommercialiCanvas lAreeCommercialiCanvas = new AreeCommercialiCanvas();
		return lAreeCommercialiCanvas;
	}


	public String getIdAreaCommerciali() {
		return idAreaCommerciali;
	}


	public void setIdAreaCommerciali(String idAreaCommerciali) {
		this.idAreaCommerciali = idAreaCommerciali;
	}




	
	
}
