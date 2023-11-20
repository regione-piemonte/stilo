/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

public interface CompareCallback {

	public void noChanges(Record lRecord);
	
	public void changed(Record lRecord);
	
}
