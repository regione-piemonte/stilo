/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public enum FlagArchivio implements Serializable, DBenum{
	ARCHIVIO("C"), ARCHIVIO_DEPOSITO("D"),  ARCHIVIO_STORICO("S"),  NOT_SETTED("");

	private String realValue;
	
	private FlagArchivio(String value){
		realValue = value;
	}
	@Override
	public String getDbValue() {
		return realValue+"";
	}

}
