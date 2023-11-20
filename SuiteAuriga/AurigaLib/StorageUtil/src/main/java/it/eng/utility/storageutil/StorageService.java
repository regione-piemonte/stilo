/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.storageutil.exception.StorageException;

import java.io.File;
import java.io.InputStream;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;

/**
 * Interfaccia che espone tutti i servizi dello storage da utilizzare all'esterno per il salvataggio, la cancellazione e il recupero del file
 * 
 * @author Mattia Zanin
 *
 */
public interface StorageService {
	
	/**
     * Metodo di salvataggio del file nello storage che ritorna l'id
     * 
     * @param file
	 * @throws StorageException
     */
	public String store(File file, String... params) throws StorageException;
	
	public String writeTo(Message lMessage) throws StorageException;
	
	/**
     * Metodo di salvataggio del file nello storage che ritorna l'id
     * 
     * @param file
	 * @throws StorageException
     */
	public String storeStream(InputStream inputStream, String... params) throws StorageException;
	
	public String storeFileItem(FileItem fileItem) throws StorageException;
	
	/**
     * Metodo di cancellazione del file dallo storage
     * 
     * @param uri
	 * @throws StorageException
     */
	public void delete(String uri) throws StorageException;
	
	/**
     * Metodo di recupero dello stream del file dallo storage
     * 
     * @param uri
	 * @throws StorageException
     */
	public InputStream extract(String uri) throws StorageException;
	
	/**
     * Metodo di recupero dello stream del file dallo storage
     * 
     * @param uri
	 * @throws StorageException
     */
	public File extractFile(String uri) throws StorageException;

	/**
	 * Restituisce un puntamento al file reale
	 * @param uri
	 * @return
	 * @throws StorageException
	 */
	public File getRealFile(String uri) throws StorageException;
	
	public String getTipoStorage() throws StorageException;
	
	public String getConfigurazioniStorage() throws StorageException;
	
}
