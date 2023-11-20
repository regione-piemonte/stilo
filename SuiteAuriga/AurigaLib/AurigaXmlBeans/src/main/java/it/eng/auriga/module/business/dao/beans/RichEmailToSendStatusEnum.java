/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Traccia gli stati che una richiesta di gestione pec heineken può assumere
 * @author massimo malvestio
 *
 */
public enum RichEmailToSendStatusEnum {

	TO_PROCESS("TO_PROCESS"),
	PROCESSING("PROCESSING"),
	COMPLETED("COMPLETED"),
	IN_WAIT("IN_WAIT"),
	DELETED("DELETED"),
	ERROR("ERROR"),
	LOCKED("LOCKED");
	
	private String value;
	
	private RichEmailToSendStatusEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
