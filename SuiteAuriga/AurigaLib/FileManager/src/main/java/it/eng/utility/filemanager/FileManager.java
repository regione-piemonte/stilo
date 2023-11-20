/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import it.eng.bean.ExecutionResultBean;

public interface FileManager {

	public void setConfig(FileManagerConfig config) throws FileManagerException;

	public boolean fileExists(FileBean fileBean) throws FileManagerException;

	public boolean canReadFile(FileBean fileBean) throws FileManagerException;

	public boolean canWriteFile(FileBean fileBean) throws FileManagerException;

	public boolean isDirectory(FileBean fileBean) throws FileManagerException;

	public File getFile(FileBean fileBean, String destDir) throws FileManagerException;

	public ExecutionResultBean<Map<String, Boolean>> deleteFiles(List<FileBean> listaFiles) throws FileManagerException;

	public boolean deleteFile(FileBean fileBean) throws FileManagerException;

	public boolean deleteDirectory(FileBean fileBean) throws FileManagerException;

	/**
	 * Copia del file nella directory di destinazione, con sovrascrittura del file se esiste già
	 * 
	 * @param fileBean
	 * @param destDir
	 * @return
	 * @throws FileManagerException
	 */

	public boolean copyFile(FileBean fileBean, FileBean destDir) throws FileManagerException;

	/**
	 * Spostamento del file nella directory di destinazione, con sovrascrittura del file se esiste già
	 * 
	 * @param fileBean
	 * @param destDir
	 * @return
	 * @throws FileManagerException
	 */

	public boolean moveFile(FileBean fileBean, FileBean destDir) throws FileManagerException;

	/**
	 * Rinomina il file, se il file esiste già solleva un'eccezione
	 * 
	 * @param fileBean
	 * @param newFileBean
	 * @return
	 * @throws FileManagerException
	 */

	public boolean renameFile(FileBean fileBean, FileBean newFileBean) throws FileManagerException;

	/**
	 * Creazione di una directory nel path specificato e restituisce l'esito<br>
	 * Se esiste già non compie alcuna azione e restituisce TRUE
	 * 
	 * @param path
	 * @return
	 * @throws FileManagerException
	 */
	public boolean createDirectory(Path path) throws FileManagerException;

	/**
	 * Creazione di una directory e restituisce l'esito<br>
	 * Se esiste già non compie alcuna azione e restituisce TRUE
	 * 
	 * @param path
	 * @return
	 * @throws FileManagerException
	 */
	public boolean createDirectory(FileBean fileBean) throws FileManagerException;

}
