/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.job.codaEXport.exceptions.ErrorInfo;

public enum EsitiRestServiceEnum implements ErrorInfo {

	ACK("ACK", "ACK"),
	NACK("NACK", "NACK");

	private final String code;
	private final String description;

	private EsitiRestServiceEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public static final EsitiRestServiceEnum fromCode(String code) {
		for (EsitiRestServiceEnum value : values()) {
			if (value.getCode().equals(code))
				return value;
		}
		return null;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return String.format("[%s] %s", code, description);
	}

}
