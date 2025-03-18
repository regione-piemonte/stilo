/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

/**
 * helper per gestire gli attach tramite storageUri
 * 
 * @author mza
 *
 */
public interface IAttachStorage {
	
	public String storeTempFile(File file) throws Exception;
	
	public File extractTempFile(String storageUri) throws Exception;
	
}

