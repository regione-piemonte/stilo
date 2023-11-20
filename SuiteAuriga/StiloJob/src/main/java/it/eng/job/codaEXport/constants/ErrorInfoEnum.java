/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.job.codaEXport.exceptions.ErrorInfo;

public enum ErrorInfoEnum implements ErrorInfo {

	/*
	 * Errori sul database da 000 a 003
	 */

	DB_CONN_INTERROTTA("000", "Impossibile connettersi al database"),

	DB_RECUPERO_DATI("001", "Impossibile recuperare i dati dal database"),

	DB_INSERIMENTO_DATI("002", "Impossibile salvare i dati nel database"),

	DB_AGGIORNAMENTO_DATI("003", "Impossibile aggiornare i dati nel database"),

	/*
	 * Errori di validazione parametri e configurazioni da 050 a 099
	 */

	VALIDAZIONE_CONFIGURAZIONI_JOB("050", "Configurazioni job errate"),

	//VALIDAZIONE_CONFIGURAZIONE_SCHEMA("053", "Configurazione schema errata"),

	VALIDAZIONE_CONFIGURAZIONE_LINGUA_LOCALE("054", "Configurazione locale errata"),

	//VALIDAZIONE_CONFIGURAZIONE_TIPO_DOMINIO("055", "Configurazione tipo dominio errata"),

	VALIDAZIONE_CONFIGURAZIONE_STORAGE("056", "Configurazione storage errata"),

	//VALIDAZIONE_CONFIGURAZIONE_RECUPERO("057", "Configurazione servizio di recupero lotto errata"),

	VALIDAZIONE_CONFIGURAZIONE_FILE_MANAGER("059", "Configurazione File Manager errata"),
	
	VALIDAZIONE_CONFIGURAZIONE_ACQUISIZIONE_DATI("060", "Configurazioni relative all'acquisizione dei dati errata"),
	
	VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_INPUT("063", "Configurazione directory di input non valida"),

	VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_BACKUP("064", "Configurazione directory backup non valida"),
	
	VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_SCARTI("065", "Configurazione directory scarti non valida"),

	VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_ELABORATI("066", "Configurazione directory elaborati non valida"),

	/*
	 * Errori di elaborazione
	 */
	ERRORE_ELABORAZIONE_JOB("200", "Errore elaborazione job"),

	ERRORE_BACKUP_FILE("205", "Errore nel backup del file"),
	
	ERRORE_SPOSTAMENTO_FILE("203", "Errore nello spostamento del file"),

	ERRORE_FILE_NON_TROVATO("206", "File/directory non trovato"),

	ERRORE_FILE_NON_ACCESSIBILE("207", "File non accessibile"),

	ERRORE_ELABORAZIONE_FILE("210", "Errore nell'elaborazione del file"),

	ERRORE_ELIMINAZIONE_FILE("208", "Errore nell'eliminazione del file"),

	ERRORE_CREAZIONE_DIRECTORY("209", "Errore nella creazione della directory"),

	ERRORE_OPERAZIONI_FINALI_FILE("218", "Errore nelle operazioni finali sul file"),
	
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
