/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;

import it.eng.utility.storageutil.exception.StorageException;

/**
 * Interfaccia che espone i metodi da implementare nello storage per l'inserimento, la cancellazione e il recupero del file
 * 
 * @author Mattia Zanin
 *
 */
public interface Storage {
	
	/**
     * Metodo di configurazione dello storage
     * 
     * @param xmlConfig
	 * @throws StorageException
     */
	public void configure(String xmlConfig) throws StorageException;	
	
	/**
     * Metodo che inserisce un nuovo file nello storage e ritorna l'id
     * 
     * @param file
	 * @throws StorageException
     */
	public String insert(File file, String... params) throws StorageException;	
	
	/**
     * Metodo che inserisce un nuovo file nello storage e ritorna l'id
     * 
     * @param file
	 * @throws StorageException
     */
	public String insertInput(InputStream inputStream, String... params) throws StorageException;	
	
	/**
     * Metodo che inserisce un nuovo file nello storage e ritorna l'id
     * 
     * @param file
	 * @throws StorageException
     */
	public String printMessage(Message lMessage) throws StorageException;	
	
	/**
     * Metodo che cancella il file dallo storage
     * 
     * @param id
	 * @throws StorageException
     */
	public void delete(String id) throws StorageException;
	
	//public void update(String id, File file) throws StorageException;
	
	/**
     * Metodo che recupera lo stream del file dallo storage
     * 
     * @param id 
	 * @throws StorageException
     */
	public InputStream retrieve(String id) throws StorageException;
	
	/**
     * Metodo che recupera lo stream del file dallo storage
     * 
     * @param id 
	 * @throws StorageException
     */
	public File retrieveFile(String id) throws StorageException;
	
	/**
	 * Recupera il puntamento al file
	 * @param id
	 * @return
	 * @throws StorageException
	 */
	public File retrievRealFile(String id) throws StorageException;

	public String writeFileItem(FileItem fileItem) throws StorageException;
	
	public void setIdStorage(String idStorage);
	
	public void setUtilizzatoreStorage(String idUtilizzatore);
	
}
