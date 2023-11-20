/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum ContabilitaAdspTipoSaldo {
	
	TUTTI(0),
	SALDO_MAGGIORE_ZERO(1);
	
	private Integer codice;
	
	ContabilitaAdspTipoSaldo(Integer codice) {
		this.codice = codice;
	}
	
	public Integer getCodice() {
		return this.codice;
	}
	
	@Override
	public String toString() {
		return String.format("%s [%s]", this.name(), this.codice);
	}
	
	
}
