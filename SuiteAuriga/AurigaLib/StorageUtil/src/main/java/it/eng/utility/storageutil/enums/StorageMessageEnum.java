/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum StorageMessageEnum {
	
	// Come PLACEHOLDER utilizzare @#@
	
	STORAGE_FOLDER("StorageFolder: @#@"),
	CREO_LA_DIRECTORY("Creo la directory perchè non esiste"),
	FILE_DA_CREARE("Andremo a scrivere sul file: @#@ con storageFolder: @#@"),
	INIZIO_SCRITTURA_FILE("Inizio scrittura file"),
	FINE_SCRITTURA_FILE("Fine scrittura file"),
	CHIUSURA_STREAM("Chiusura stream"),
	CERCO_NEL_REPOSITORY("### cerco il file in @#@ ###"),
	FILE_VALIDO("File valido:"),
	FILE_CIFRATO("File cifrato"),
	
	ERR_ANALISI_FILE("Errore nell'analisi del file"), 
	ERR_INSERT_FILE("Errore nell'operazione di insert del file"), 
	ERR_INSERT_STREAM("Errore nell'operazione di insert dello stream"), 
	ERR_DELETE_FILE("Errore nell'operazione di delete del file"), 
	ERR_RETRIEVE_FILE("Errore nell'operazione di retrieve del file"),
	ERR_RETRIEVE_STREAM("Errore nell'operazione di retrieve dello stream"), 
	ERR_RETRIEVE_ARCHIVIO_COMPRESSO("Errore nell'operazione di retrieve dell'archivio compresso"), 
	ERR_ARCHIVIAZIONE_MAIL_MESSAGE("Errore nell'operazione di archiviazione del mail message"), 
	ERR_ARCHIVIAZIONE_FILE_ITEM("Errore nell'operazione di scrittura del FileItem"),
	ERR_COMPRESSIONE_FILE("Errore nella compressione del file"),
	ERR_COMPRESSIONE_CIFRATURA_FILE("Errore nella compressione e cifratura del file"),
	;
	
	private static final String PLACEHOLDER = "@#@";
	private static final String NULLSTRING = "[NULL]";
	private String message;
	
	private StorageMessageEnum(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return format();
	}
	
	public final String format(Object ...args) {
		int pos = 0;
		int countPos = 0;
		StringBuilder result = new StringBuilder(message);
		
		//sostituzione placeholder
		while ((pos = result.indexOf(PLACEHOLDER)) > -1) {
			
			int offset = pos + PLACEHOLDER.length();
			
			if (args != null && args.length > countPos) {
				result.replace(pos, offset, 
						args[countPos] != null ? String.valueOf(args[countPos]) : NULLSTRING);
			} else {
				result.replace(pos, offset, NULLSTRING);
			}
			
			countPos++;
		}
		
		//scrittura argomenti rimanenti
		while (args != null && countPos < args.length) {
			result.append(" ").append(args[countPos] != null ? String.valueOf(args[countPos]) : NULLSTRING);
			countPos++;
		}
		
		return result.toString();
	}
}
