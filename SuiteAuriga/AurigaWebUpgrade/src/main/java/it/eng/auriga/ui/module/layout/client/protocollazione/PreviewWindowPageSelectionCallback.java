/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

public abstract class PreviewWindowPageSelectionCallback {

	public abstract void executeSalva(Record record);
		
	public abstract void executeSalvaVersConOmissis(Record record);
	
	public abstract void executeOnError();

}
