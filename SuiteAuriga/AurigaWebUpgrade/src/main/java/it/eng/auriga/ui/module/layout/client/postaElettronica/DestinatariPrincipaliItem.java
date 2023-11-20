/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DestinatariPrincipaliItem extends ReplicableItem{
	
	private Record detailRecord;
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		DestinatariPrincipaliCanvas lDestinatariPrincipaliCanvas = new DestinatariPrincipaliCanvas(this);		
		return lDestinatariPrincipaliCanvas;
	}

	public Record getDetailRecord() {
		return detailRecord;
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}
}
