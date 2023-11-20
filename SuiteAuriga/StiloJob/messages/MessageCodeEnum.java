/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum MessageCodeEnum {
	/**
	 * Errore alla riga {0} del file indice
	 * 
	 * @param {0} numero riga
	 **/
	ERRORE_ALLA_RIGA,

	/**
	 * Codice cliente mancante o errato
	 **/
	ERRORE_CLIENTE_MANCANTE,

	/**
	 * Errore nella creazione del documento
	 **/
	ERRORE_CREA_DOC,

	/**
	 * Errore nell'invio via PEC del documento
	 **/
	ERRORE_INVIO_PEC,

	/**
	 * Errore nell'invio del documento
	 **/
	ERRORE_INVIO_DOCUMENTO,

	/**
	 * Data fattura mancante o errato
	 **/
	ERRORE_DATA_FATTURA_MANCANTE,

	/**
	 * La data fattura non può essere futura
	 **/
	ERRORE_DATA_FATTURA_FUTURA,

	/**
	 * La {0} non può essere futura
	 * 
	 * @param {0} descrizione della data in oggetto
	 **/
	ERRORE_DATA_FUTURA,

	/**
	 * Errore nell'inserimento in db delle righe del file indice
	 **/
	ERRORE_INS_DB_FILE_INDICE,

	/**
	 * Errore nell'apertura del documento
	 **/
	ERRORE_LETTURA_DOC,

	/**
	 * Errore nella lettura del file indice
	 **/
	ERRORE_LETTURA_FILE_INDICE,

	/**
	 * Nome file pdf fattura mancante o errato
	 **/
	ERRORE_NOME_FILE_DOC_MANCANTE,

	/**
	 * Numero fattura mancante o errato
	 **/
	ERRORE_NUM_FATTURA,

	/**
	 * Numero pagine mancante o errato
	 **/
	ERRORE_NUM_PAGINE_MANCANTE,

	/**
	 * Numero campi non corretto: {0}
	 * 
	 * @param {0} numero campi considerato errato
	 **/
	ERRORE_NUMERO_CAMPI,

	/**
	 * Partita iva cliente mancante o errata
	 **/
	ERRORE_PIVA_MANCANTE,

	/**
	 * Ragione sociale cliente mancante o errata
	 **/
	ERRORE_RAG_SOC_MANCANTE,

	/**
	 * Errore nella gestione dei documenti
	 **/
	ERRORE_GESTIONE_DOCUMENTI,

	/**
	 * Errore nella ricerca di documenti duplicati
	 **/
	ERRORE_RICERCA_DUPLICATI,

	/**
	 * Codice sezionale non trovato per questo numero fattura
	 **/
	ERRORE_SEZIONALE_NON_TROVATO,

	/**
	 * Codice societa mancante
	 **/
	ERRORE_SOCIETA_MANCANTE,

	/**
	 * Codice societa non censito in anagrafica
	 **/
	ERRORE_SOCIETA_NO_ANAG,

	/**
	 * Tipo documento mancante
	 **/
	ERRORE_TIPO_DOC_MANCANTE,

	/**
	 * Tipo documento non censito in anagrafica
	 **/
	ERRORE_TIPO_DOC_NO_ANAG,

	/**
	 * Codice sezionale mancante o errato
	 **/
	ERRORE_CODICE_SEZIONALE_MANCANTE,

	/**
	 * Tipo invio default mancante o errato
	 **/
	ERRORE_TIPO_INVIO_DEFAULT_MANCANTE,

	/**
	 * Invio da applicare mancante o errato
	 **/
	ERRORE_INVIO_DA_APPLICARE_MANCANTE,

	/**
	 * Canale di invio del documento trovato non coerente con il documento attuale
	 **/
	ERRORE_CANALE_INVIO_NON_COERENTE,

	/**
	 * Indirizzo email PEC mancante o errato
	 **/
	ERRORE_INDIRIZZO_EMAIL_PEC_MANCANTE,

	/**
	 * Canale di invio non definito per il cliente {0}
	 * 
	 * @param {0} nome o codice cliente
	 */
	ERRORE_CANALE_INVIO_NON_TROVATO,

	/**
	 * Tipo fattura mancante o errato
	 **/
	ERRORE_TIPO_FATTURA_MANCANTE,

	/**
	 * Tipo fattura non censito in anagrafica
	 **/
	ERRORE_TIPO_FATTURA_NO_ANAG,

	/**
	 * Errore grave
	 **/
	ERRORE_GRAVE_CONTROLLA_LOG,

	/**
	 * Errore nell'invio dell'esito
	 **/
	ERRORE_INVIO_ESITO,

	/**
	 * File indice mancante
	 **/
	ERRORE_FILE_INDICE_MANCANTE,

	/**
	 * Lotto già elaborato
	 **/
	ERRORE_LOTTO_GIA_ELAB,

	/**
	 * Il file lotto {0} non esiste nel filesystem di provenienza
	 * 
	 * @param {0} nome file lotto
	 **/
	ERRORE_FS_FILE_LOTTO_NON_ESISTE,

	/**
	 * Il file lotto {0} non esiste nel server ftp
	 * 
	 * @param {0} nome file lotto
	 **/
	ERRORE_FTP_FILE_LOTTO_NON_ESISTE,

	/**
	 * Impossibile cancellare il file lotto {0} dal filesystem di provenienza
	 * 
	 * @param {0} nome file lotto
	 **/
	ERRORE_FS_CANCELLA_FILE_LOTTO,

	/**
	 * Impossibile eseguire il backup del file lotto {0} nel filesystem di provenienza
	 * 
	 * @param {0} nome file lotto
	 **/
	ERRORE_FS_BACKUP_FILE_LOTTO,

	/**
	 * Impossibile cancellare il file lotto {0} dal server ftp
	 * 
	 * @param {0} nome file lotto
	 **/
	ERRORE_FTP_CANCELLA_FILE_LOTTO,

	/**
	 * Impossibile eseguire il backup del file lotto {0} nel server ftp
	 * 
	 * @param {0} nome file lotto
	 **/
	ERRORE_FTP_BACKUP_FILE_LOTTO,

	/**
	 * Il file indice {0} non esiste nel filesystem di provenienza
	 * 
	 * @param {0} nome file indice
	 **/
	ERRORE_FS_FILE_INDICE_NON_ESISTE,

	/**
	 * Il file indice {0} non esiste nel server ftp
	 * 
	 * @param {0} nome file indice
	 **/
	ERRORE_FTP_FILE_INDICE_NON_ESISTE,

	/**
	 * Impossibile cancellare il file indice {0} dal filesystem di provenienza
	 * 
	 * @param {0} nome file indice
	 **/
	ERRORE_FS_CANCELLA_FILE_INDICE,

	/**
	 * Impossibile eseguire il backup del file indice {0} nel filesystem di provenienza
	 * 
	 * @param {0} nome file indice
	 **/
	ERRORE_FS_BACKUP_FILE_INDICE,

	/**
	 * Impossibile cancellare il file indice {0} dal server ftp
	 * 
	 * @param {0} nome file indice
	 **/
	ERRORE_FTP_CANCELLA_FILE_INDICE,

	/**
	 * Impossibile eseguire il backup del file indice {0} nel server ftp
	 * 
	 * @param {0} nome file indice
	 **/
	ERRORE_FTP_BACKUP_FILE_INDICE,

	/**
	 * Il file {0} non esiste nel filesystem di provenienza
	 * 
	 * @param {0} nome file
	 **/
	ERRORE_FS_FILE_DOC_NON_ESISTE,

	/**
	 * Il file {0} non esiste nel server ftp
	 * 
	 * @param {0} nome file
	 **/
	ERRORE_FTP_FILE_DOC_NON_ESISTE,

	/**
	 * Impossibile cancellare il file {0} dal filesystem di provenienza
	 * 
	 * @param {0} nome file
	 **/
	ERRORE_FS_CANCELLA_FILE_DOC,

	/**
	 * Impossibile eseguire il backup del file {0} nel filesystem di provenienza
	 * 
	 * @param {0} nome file
	 **/
	ERRORE_FS_BACKUP_FILE_DOC,

	/**
	 * Impossibile cancellare il file {0} dal server ftp
	 * 
	 * @param {0} nome file
	 **/
	ERRORE_FTP_CANCELLA_FILE_DOC,

	/**
	 * Impossibile eseguire il backup del file {0} nel server ftp
	 * 
	 * @param {0} nome file
	 **/
	ERRORE_FTP_BACKUP_FILE_DOC,

	/**
	 * Errori in fase di caricamento.
	 **/
	ERRORI_IN_CARICAMENTO,

	/**
	 * Fase di caricamento terminata correttamente.
	 **/
	NESSUN_ERRORE_IN_CARICAMENTO,

	/**
	 * Lotto {0}
	 * 
	 * @param {0} nome lotto
	 **/
	LOTTO,

	/**
	 * Riga {0}
	 * 
	 * @param {0} numero riga
	 **/
	RIGA,

	/**
	 * Colonna {0}
	 * 
	 * @param {0} numero colonna
	 **/
	COLONNA,

	/**
	 * data fattura
	 **/
	DATA_FATTURA,

	/**
	 * Lotto {0} del {1}
	 * 
	 * @param {0} nome lotto
	 * @param {1} data fattura gg/mm/aaaa
	 **/
	LOTTO_DEL,

	/**
	 * Fattura N° {0} del {1}
	 * 
	 * @param {0} numero fattura
	 * @param {1} data fattura gg/mm/aaaa
	 **/
	FATTURA_NUM_DEL,

	/**
	 * {0} Atto N. {1}
	 * 
	 * @param {0} fonte giuridica
	 * @param {1} numero atto
	 **/
	FONTE_ATTO_NUM,

	/**
	 * Il caricamento è andato a buon fine
	 **/
	MAIL_ESITO_OK,

	/**
	 * Il caricamento è andato a buon fine. Sono stati rilevati errori nella post elaborazione dei file.
	 **/
	MAIL_ESITO_WARN,

	/**
	 * Il caricamento è terminato con errori
	 **/
	MAIL_ESITO_KO,

	/**
	 * Il caricamento del lotto {0} è andato a buon fine
	 * 
	 * @param {0} nome lotto
	 **/
	MAIL_ESITO_LOTTO_OK,

	/**
	 * Il caricamento del lotto {0} è terminato con errori
	 * 
	 * @param {0} nome lotto
	 **/
	MAIL_ESITO_LOTTO_KO,

	/**
	 * Nome file acquisito: {0}
	 * 
	 * @param {0}
	 **/
	MAIL_REPORT_FILE_ACQUISITO,

	/**
	 * Data inizio acquisizione: {0}
	 * 
	 * @param {0}
	 **/
	MAIL_REPORT_DATA_INIZIO_ACQUISIZIONE,

	/**
	 * Data fine acquisizione: {0}
	 * 
	 * @param {0}
	 **/
	MAIL_REPORT_DATA_FINE_ACQUISIZIONE,

	/**
	 * Numero documenti presenti: {0}
	 * 
	 * @param {0}
	 **/
	MAIL_REPORT_NUM_DOC_PRESENTI,

	/**
	 * Numero documenti acquisiti: {0}
	 * 
	 * @param {0}
	 **/
	MAIL_REPORT_NUM_DOC_ACQUISITI,

	/**
	 * Numero documenti scartati: {0}
	 * 
	 * @param {0}
	 **/
	MAIL_REPORT_NUM_DOC_SCARTATI,

	/**
	 * Alcuni documenti non sono stati caricati.<br/>
	 * Consultare il file {0} allegato.
	 * 
	 * @param {0} nome file allegato
	 **/
	MAIL_DOCUMENTI_SCARTATI,

	/**
	 * Alcuni file non sono stati cancellati dal server ftp.<br/>
	 * Consultare il file {0} allegato.
	 * 
	 * @param {0} nome file allegato
	 **/
	MAIL_FILE_NON_CANCELLATI,

	/**
	 * In allegato l'elenco degli errori.
	 **/
	MAIL_ALLEGATO_ERRORI_PRESENTE,

	/**
	 * In allegato l''elenco delle segnalazioni.
	 **/
	MAIL_ALLEGATO_SEGNALAZIONI_PRESENTE,

	/**
	 * In allegato l'elenco dei documenti elaborati.
	 **/
	MAIL_ALLEGATO_DOCUMENTI_PRESENTE,

	/**
	 * In allegato l''elenco dei documenti non caricati a causa di errori.
	 **/
	MAIL_ALLEGATO_DOCUMENTI_IN_ERRORE_PRESENTE,

	/**
	 * In allegato il risultato del caricamento lotti.
	 **/
	MAIL_ALLEGATO_RISULTATO_CARICAMENTO,

	/**
	 * In allegato i risultati del caricamento lotti.
	 **/
	MAIL_ALLEGATI_RISULTATO_CARICAMENTO,

	/**
	 * Nessun allegato presente.
	 **/
	MAIL_NESSUN_ALLEGATO,

	/**
	 * Fonte Giuridica mancante o errato
	 **/
	ERRORE_FONTE_GIURIDICA_MANCANTE,

	/**
	 * Atto concessione mancante o errato
	 **/
	ERRORE_ATTO_MANCANTE,

	/**
	 * Data Documento mancante o errato
	 **/
	ERRORE_DATA_DOCUMENTO,

	/**
	 * Numero Pratica mancante o errato
	 **/
	ERRORE_NUM_PRATICA_MANCANTE,

	/**
	 * Scatola mancante o errato
	 **/
	ERRORE_SCATOLA_MANCANTE,

	/**
	 * lotto mancante o errato
	 **/
	ERRORE_LOTTO_MANCANTE,

	/**
	 * Data scansione mancante o errato
	 **/
	ERRORE_DATA_SCANSIONE,

	/**
	 * Utente scansione mancante o errato
	 **/
	ERRORE_UTENTE_SCANSIONE_MANCANTE,

	/**
	 * Errore nell'aggiornamento dello stato del documento
	 **/
	ERRORE_AGGIORNAMENTO_STATO_DOCUMENTO,

	/**
	 * Errore nell'inserimento dell'allegato
	 **/
	ERRORE_INSERIMENTO_ALLEGATO,

	;
}
