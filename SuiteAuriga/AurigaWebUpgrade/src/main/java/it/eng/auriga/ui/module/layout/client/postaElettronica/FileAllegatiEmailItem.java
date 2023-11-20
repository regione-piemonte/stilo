/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

public class FileAllegatiEmailItem extends ReplicableItem {

	private Record detailRecord;

	@Override
	public ReplicableCanvas getCanvasToReply() {
		FileAllegatiEmailCanvas lFileAllegatiEmailCanvas = new FileAllegatiEmailCanvas(this);
		return lFileAllegatiEmailCanvas;
	}

	public boolean showNumeroAllegato() {
		return true;
	}

	public void downloadFile(ServiceCallback<Record> lDsCallback) {

	}

	public boolean showStampaFileButton() {
		return false;
	}

	public Record getDetailRecord() {
		return detailRecord;
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}

}
