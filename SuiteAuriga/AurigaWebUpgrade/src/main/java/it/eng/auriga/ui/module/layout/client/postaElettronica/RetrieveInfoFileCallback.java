/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

public abstract class RetrieveInfoFileCallback {

	public abstract void manageInfoFile(Record pRecord, RecordList lRecordList);
}
