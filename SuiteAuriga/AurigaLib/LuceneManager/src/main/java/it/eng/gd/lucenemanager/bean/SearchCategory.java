/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public enum SearchCategory implements Serializable {

	REP_DOC("REP_DOC"), REP_DOC_FILE("REP_DOC_FILE"), DEF_CTX_CL("DEF_CTX_CL"), DEF_CTX_SO("DEF_CTX_SO"), EMAIL("EMAIL"), RUBRICA("RUBRICA"), TOPOGRAFICO(
			"TOPOGRAFICO"), ALBO_DOC("ALBO_DOC"), ALBO_DOC_FILE("ALBO_DOC_FILE");

	private String valore;

	private SearchCategory(String value) {
		valore = value;
	}

	public String getValore() {
		return valore;
	}

}
