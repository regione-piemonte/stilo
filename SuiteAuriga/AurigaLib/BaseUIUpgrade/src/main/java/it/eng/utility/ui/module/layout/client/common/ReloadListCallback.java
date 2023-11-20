/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

public class ReloadListCallback {
	
	private CustomLayout layout;
	private Record record;
	
	public CustomLayout getLayout() {
		return layout;
	}

	public void setLayout(CustomLayout layout) {
		this.layout = layout;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public void executeCallback(){
		if(layout != null) {
			layout.reloadListAndSetCurrentRecord(record);
		}
	}

}
