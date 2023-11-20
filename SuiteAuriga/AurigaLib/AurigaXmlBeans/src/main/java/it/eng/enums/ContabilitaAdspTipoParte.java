/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum ContabilitaAdspTipoParte {
	
	ENTRATE("E"),
	SPESA("S");
	
	private String codice;

	ContabilitaAdspTipoParte(String codice) {
		this.codice = codice;
	}
	
	public String getCodice() {
		return this.codice;
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", this.name(), this.codice);
	}
	
}
