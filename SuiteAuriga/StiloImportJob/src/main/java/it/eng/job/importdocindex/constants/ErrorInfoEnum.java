/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.job.importdocindex.exceptions.ErrorInfo;

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

	VALIDAZIONE_CONFIGURAZIONI_SCANSIONE_INVALIDE("051", "Nessuna scansione configurata correttamente"),

	VALIDAZIONE_CONFIGURAZIONE_SCHEMA("053", "Configurazione schema errata"),

	VALIDAZIONE_CONFIGURAZIONE_LINGUA_LOCALE("054", "Configurazione locale errata"),

	VALIDAZIONE_CONFIGURAZIONE_TIPO_DOMINIO("055", "Configurazione tipo dominio errata"),

	VALIDAZIONE_CONFIGURAZIONE_STORAGE("056", "Configurazione storage errata"),

	VALIDAZIONE_CONFIGURAZIONE_RECUPERO("057", "Configurazione servizio di recupero lotto errata"),

	VALIDAZIONE_CONFIGURAZIONE_ELABORAZIONE("058", "Configurazione servizio di elaborazione lotto errata"),

	VALIDAZIONE_CONFIGURAZIONE_FILE_MANAGER("059", "Configurazione File Manager errata"),

	VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_ROOT("060", "Configurazione directory principale errata. Si ricorda che è una directory obbligatoria da configurare"),

	VALIDAZIONE_CONFIGURAZIONE_SCANSIONE_INDICI("061", "Configurazione scansione indici errata"),

	VALIDAZIONE_CONFIGURAZIONE_FORMATO_INDICE("062", "Configurazione formato indici errata"),

	VALIDAZIONE_CONCORRENZA_DIRECTORY("063", "Alcune configurazioni hanno directory che sono figlie fra di loro"),

	VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_BACKUP("064", "Configurazione directory backup non valida"),

	VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_LAVORO("065", "Configurazione directory lavoro non valida"),

	VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_ELABORATI("066", "Configurazione directory elaborati non valida"),

	VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_ERRORI("067",
			"Configurazione directory errori non valida. Si ricorda che è una directory obbligatoria da configurare"),

	VALIDAZIONE_CONFIGURAZIONE_DEFAULT_MIME_TYPE("068", "Configurazione default mime type errata"),

	VALIDAZIONE_CONFIGURAZIONI_CALCOLO_IMPRONTA("069", "Configurazioni calcolo imporonta errate"),

	VALIDAZIONE_CONFIGURAZIONE_TOKEN("070", "Configurazione token errata"),

	VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_DUPLICATA("071",
			"Le directory di lavoro, backup, elaborati e errori indicate nelle varie configurazioni devono essere diverse tra di loro, per evitare problemi di conflitto dei file e cartelle che vi vengono scritti/copiati: conviene specificare directory specifiche per ogni scansione"),

	VALIDAZIONE_TIPOLOGIA_INDICE("072", "Tipologia indice non supportata"),

	/*
	 * Errori di elaborazione
	 */
	ERRORE_ELABORAZIONE_JOB("200", "Errore elaborazione job"),

	ERRORE_RECUPERO_MIME_TYPE("201", "Errore nel recupero del mime type del file"),

	ERRORE_SCANSIONE_FILE_INDICE("202", "Errore nella scansione dei file indice"),

	ERRORE_SPOSTAMENTO_FILE("203", "Errore nello spostamento del file"),

	ERRORE_DECOMPRESSIONE_FILE("204", "Errore nella decompressione del file"),

	ERRORE_BACKUP_FILE("205", "Errore nel backup del file"),

	ERRORE_FILE_NON_TROVATO("206", "File/directory non trovato"),

	ERRORE_FILE_NON_ACCESSIBILE("207", "File non accessibile"),

	ERRORE_ELIMINAZIONE_FILE("208", "Errore nell'eliminazione del file"),

	ERRORE_CREAZIONE_DIRECTORY("209", "Errore nella creazione della directory"),

	ERRORE_ARCHIVIAZIONE_FILE("210", "Errore nell'archiviazione del file"),

	ERRORE_IMPRONTA_FILE("211", "Errore nel calcolo dell'impronta del file"),

	ERRORE_DIMENSIONE_FILE("212", "Errore nel calcolo della dimensione del file"),

	ERRORE_ELABORAZIONE_FILE("213", "Errore nell'elaborazione del file"),

	ERRORE_REGISTRAZIONE_FILE("214", "Errore nella registrazione del file"),

	ERRORE_SERIALIZZAZIONE_CONFIGURAZIONE("215", "Errore nella serializzazione delle configurazioni"),

	ERRORE_LETTURA_COLONNA_URI_FILE("216", "Errore nella lettura della colonna dell'URI del file da caricare"),

	ERRORE_LETTURA_FILE_CSV("217", "Errore nella lettura del file csv"),

	ERRORE_OPERAZIONI_FINALI_FILE("218", "Errore nelle operazioni finali sul file"),

	ERRORE_RINOMINA_FILE("219", "Errore rinomina file"),

	ERRORE_FILE_INDICE_DUPLICATO("220", "Un file indice è referenziato da più scansioni"),

	ERRORE_LETTURA_FILE_XLS("221", "Errore nella lettura del file xls/xlsx"),

	ERRORE_AGGIORNAMENTO_ELABORAZIONE_RIGA_INDICE("222", "Errore nell'aggiornamento dello stato di elaborazione della riga del file indice"),

	ERRORE_GENERICO("999", "Errore generico"),

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
