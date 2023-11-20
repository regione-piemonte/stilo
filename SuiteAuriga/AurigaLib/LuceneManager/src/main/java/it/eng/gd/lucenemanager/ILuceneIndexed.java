/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.util.Map;

/**
 * Ibnterfaccia che espone i metodi di indicizzazione su Lucene
 * @author Administrator
 *
 */
public interface ILuceneIndexed {
	
	/**
	 * Indicizza un documento
	 * @param file --> File da indicizzare (deve essere salvatao nella directory configurata nello springbean)
	 * @param mimetype --> Mimetype obbligatorio per il recupero del parser configurato nello spring bean
	 * @param filename --> Nome dei file
	 * @param id --> Id dell'oggetto univoco da indicizzare
 	 * @param indexmetadata --> HashMap contenete i metadati da indicizzare (supporta oggetti di tipo Date che non vengono comumnque indicizzati ma utilizzati solo nei filtri di ricerca e non nella query, tutti gli altri vengono convertiti a stringa)
	 * @param unindexmetadata --> HashMap contenete i metadati da non indicizzare (supporta oggetti di tipo Date, tutti gli altri vengono convertiti a stringa)
	 * @throws Exception Se esiste gia' un id viene rilanciata un'eccezione
	 */
	public void addDocument(File file,String mimetype,String filename,String id,Map<String,Object> indexmetadata,Map<String,Object> unindexmetadata)throws Exception;
	
	/**
	 * Indicizza un folder
	 * @param foldername --> Nome del folder da indicizzare
	 * @param id --> Id univoco dell'oggetto da indicizzare
	 * @param indexmetadata --> HashMap contenete i metadati da indicizzare (supporta oggetti di tipo Date che non vengono comumnque indicizzati ma utilizzati solo nei filtri di ricerca e non nella query, tutti gli altri vengono convertiti a stringa)
	 * @param unindexmetadata --> HashMap contenete i metadati da non indicizzare (supporta oggetti di tipo Date, tutti gli altri vengono convertiti a stringa)
	 * @throws Exception Se esiste gia' un id viene rilanciata un'eccezione
	 */
	public void addFolder(String foldername,String id,Map<String,Object> indexmetadata,Map<String,Object> unindexmetadata)throws Exception;
	
	/**
	 * Effettua un update del documento, vengono updatati o aggiunti solo i valori passati in ingresso. Se gli attributi vengono passati a null non viene effettuato l'update dello valore.
	 * ES. Se file == null non viene rindicizzato il file.
	 * Nelle Map vengono aggiornati solo i valori presenti.	
	 * @param file --> File da indicizzare (deve essere salvatao nella directory configurata nello springbean)
	 * @param mimetype --> Mimetype obbligatorio per il recupero del parser configurato nello spring bean
	 * @param filename --> Nome dei file
	 * @param id --> Id dell'oggetto univoco indicizzato
	 * @param indexmetadata  --> HashMap contenete i metadati da indicizzare (supporta oggetti di tipo Date che non vengono comumnque indicizzati ma utilizzati solo nei filtri di ricerca e non nella query, tutti gli altri vengono convertiti a stringa)
	 * @param unindexmetadata --> HashMap contenete i metadati da non indicizzare (supporta oggetti di tipo Date, tutti gli altri vengono convertiti a stringa)
	 * @throws Exception
	 */
	public void updateDocument(File file,String mimetype,String filename,String id,Map<String,Object> indexmetadata,Map<String,Object> unindexmetadata)throws Exception;
	
	/**
	 * Effettua un update del Folder, vengono updatati o aggiunti solo i valori passati in ingresso. Se gli attributi vengono passati a null non viene effettuato l'update dello valore.
	 * ES. Se foldername == null non viene rindicizzato il nome del folder
	 * Nelle Map vengono aggiornati solo i valori presenti.	
	 * @param foldername
	 * @param id
	 * @param indexmetadata
	 * @param unindexmetadata
	 * @throws Exception
	 */
	public void updateFolder(String foldername,String id,Map<String,Object> indexmetadata,Map<String,Object> unindexmetadata)throws Exception;
	
	/**
	 * Elimina un folder dall'indicizzazione
	 * @param id --> Id univoco dell'oggetto indicizzato
	 * @throws Exception
	 */
	public void deleteFolder(String id)throws Exception;
	
	/**
	 * Elimina un documento dall'indicizzazione
	 * @param id --> Id univoco dell'oggetto indicizzato
	 * @throws Exception
	 */
	public void deleteDocument(String id)throws Exception;
	
}
