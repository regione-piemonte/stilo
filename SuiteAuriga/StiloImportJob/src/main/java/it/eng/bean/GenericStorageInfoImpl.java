/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.storageutil.GenericStorageInfo;

public class GenericStorageInfoImpl implements GenericStorageInfo {

	private String utilizzatoreStorageId;

	@Override
	public String getUtilizzatoreStorageId() {
		return utilizzatoreStorageId;
	}

	public void setUtilizzatoreStorageId(String utilizzatoreStorageId) {
		this.utilizzatoreStorageId = utilizzatoreStorageId;
	}

}
