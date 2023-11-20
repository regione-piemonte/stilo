/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum OperazioniAurigaLottiDocFaultCode {

	INVALID_REQUEST(1, "Request formalmente non corretta (non valida rispetto xsd) o non valorizzata"), DUPLICATED_ID(2,
			"ID lotto duplicato (già inviato dal chiamante)"), NOT_FOUND(3, "Lotto non presente"), INVALID_CREDENTIAL(4,
					"Credenziali non valide"), INVALID_DOC_TYPE(5, "Tipologia documentale non valida o non presente"), INVALID_OPERATIONS(6,
							"Operazioni non valide"), GENERIC_ERROR(999, "Errore generico non previsto"),
	INVALID_ATTACH(7,"Impossibile recuperare l'attachment: file corrotto o non presente");

	private final Integer code;
	private final String msg;

	OperazioniAurigaLottiDocFaultCode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}
}