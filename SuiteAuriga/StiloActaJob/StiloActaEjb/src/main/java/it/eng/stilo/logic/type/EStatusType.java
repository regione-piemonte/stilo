/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum EStatusType {
	
	OPEN(1),
	DELETED(5),
	CLOSED(9),
	WAITING_CLOSURE(15);
	
	private int code;

	EStatusType(int eCode) {
		this.code = eCode;
	}

	public int getCode() {
		return code;
	}

}
