/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.storageutil.GenericStorageInfo;

public class GenericStorageInfoImpl implements GenericStorageInfo {
	
	private final String idUtilizzatore;
	
	public GenericStorageInfoImpl(String idUtilizzatore) {
		this.idUtilizzatore = idUtilizzatore;
 	}

	@Override
	public String getUtilizzatoreStorageId() {
		return idUtilizzatore;
	}

}
