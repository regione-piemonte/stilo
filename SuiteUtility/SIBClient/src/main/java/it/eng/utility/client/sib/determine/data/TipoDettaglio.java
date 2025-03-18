/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum TipoDettaglio {

	IMPEGNO("IPG"), ACCERTAMENTO("ACC"), VARIAZIONE_DI_IMPEGNO("VIP"), VARIAZIONE_DI_ACCERTAMENTO("VAC"), SUBIMPEGNO("SIP"), SUBACCERTAMENTO(
			"SAC"), VARIAZIONE_DI_SUBIMPEGNO("VSI"), VARIAZIONE_DI_SUBACCERTAMENTO("VSA"), CRONOPROGRAMMA("COP"), SUBCRONOPROGRAMMA("SCP");

	TipoDettaglio(String codice) {
		this.codice = codice;
	}

	private final String codice;

	public String getCodice() {
		return codice;
	}

}
