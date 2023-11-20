/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;

public abstract class EditaIstanzaWindowFromMail extends IstanzeWindow{
	
	public EditaIstanzaWindowFromMail(String pNomeEntita, Record pRecord) {
		super(pNomeEntita, pRecord);		
	}
	
	@Override
	protected void manageOperationOnLayout(Record lRecord) {		
		this.editNewRecordFromEmail(lRecord);
	}
	
	@Override
	public void manageOnCloseClick() {

		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("AurigaProtocollaPostaElettronicaDataSource");
		lGWTRestDataSource.executecustom("sbloccaMail", record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				_window.markForDestroy();
				manageAfterCloseWindow();
			}
		});
	}
	
	public abstract void manageAfterCloseWindow();
}