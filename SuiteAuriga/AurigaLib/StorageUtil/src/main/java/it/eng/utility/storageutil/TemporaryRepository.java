/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;

/**
 * Interfaccia che espone i metodi da implementare per la gestione dei file temporanei utilizzati nello storage
 * 
 * @author Mattia Zanin
 *
 */
public interface TemporaryRepository {
	
	/**
	 * Metodo che ritorna lo stream in lettura del file temporaneo
	 * 
	 * @param relativePath
	 * @throws FileNotFoundException
	 */
	public InputStream retrieveInputStreamFromRelativePath(String relativePath) throws FileNotFoundException;
	
	/**
	 * Metodo di salvataggio del file temporaneo
	 * 
	 * @param is
	 * @param relativePath
	 */
	public void storeInputStreamToRelativeFile(InputStream is, String relativePath);
	
	/**
	 * Metodo che apre uno stream in scrittura sul file temporaneo
	 * 
	 * @param relativePath
	 * @throws IOException
	 */
	public OutputStream openRelativeFileOutputStream(String relativePath)  throws IOException;

	/**
	 * Metodo che cancella il file temporaneo
	 *  
	 * @param relativePath
	 * @throws IOException
	 */
	public void deleteRelativeFile(String relativePath)  throws IOException;
	
	/**
	 * Metodo che ritorna il basePath 
	 * 
	 */
	public String getBasePath();

	public void printMessageToRelativeFile(Message lMessage, String relativePath);

	public void writeFileItemToRelativeFile(FileItem fileItem, String relativePath);

	public File retrieveFileFromRelativePath(String id) throws FileNotFoundException;
	
}
