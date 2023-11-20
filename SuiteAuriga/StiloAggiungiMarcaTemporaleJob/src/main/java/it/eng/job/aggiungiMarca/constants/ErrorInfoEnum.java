/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.job.aggiungiMarca.exceptions.ErrorInfo;

public enum ErrorInfoEnum implements ErrorInfo {

	/*
	 * Errori di validazione parametri e configurazioni da 050 a 099
	 */

	VALIDAZIONE_CONFIGURAZIONI_JOB("050", "Configurazioni job errate"),
	
	VALIDAZIONE_CONFIGURAZIONE_TIPO_DOMINIO("055", "Configurazione tipo dominio errata"),

	VALIDAZIONE_CONFIGURAZIONE_STORAGE("056", "Configurazione storage errata"),

	VALIDAZIONE_CONFIGURAZIONE_SCHEMA("057", "Configurazione schema errata"),

	VALIDAZIONE_CONFIGURAZIONE_LINGUA_LOCALE("058", "Configurazione locale errata"),
	
	VALIDAZIONE_CONFIGURAZIONE_FILE_MANAGER("059", "Configurazione File Manager errata"),
	
	VALIDAZIONE_CONFIGURAZIONE_TOKEN("060", "Configurazione token errata"),
	
	VALIDAZIONE_CONFIGURAZIONE_DB("061", "Configurazione db errata"),
	
	/*
	 * Errori di elaborazione
	 */
	ERRORE_ELABORAZIONE_JOB("200", "Errore elaborazione job"),

	ERRORE_RICERCA_FILES("201", "Errore nella ricerca dei files da marcare"),
	
	ERRORE_ELABORAZIONE_FILES("210", "Errore nell'elaborazione del file da marcare"),

	ERRORE_OPERAZIONI_FINALI("218", "Errore nelle operazioni finali "),
	
	ERRORE_GENERICO("999", "Errore generico")

	;

	private final String code;
	private final String description;

	private ErrorInfoEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public static final ErrorInfoEnum fromCode(String code) {
		for (ErrorInfoEnum value : values()) {
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
