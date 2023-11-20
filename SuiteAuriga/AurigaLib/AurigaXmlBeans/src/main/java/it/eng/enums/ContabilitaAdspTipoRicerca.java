/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum ContabilitaAdspTipoRicerca {
	
	NO_CONSERVATO_NO_PRELEVATO(0),
	INVIO_CONSERVAZIONE_POSITIVO(1),
	INVIO_CONSERVAZIONE_POSITIVO_WARNING(2),
	NEGATIVO(3),
	PRELEVATO_ESITO_POSITIVO(5),
	PRELEVATO_ESITO_POSITIVO_WARNING(6),
	TUTTI(10);
	
	private Integer codice;
	
	ContabilitaAdspTipoRicerca(Integer codice) {
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
