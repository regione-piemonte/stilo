/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CopyFiles {
	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	 public long copiaStream (InputStream inputStream ,Path targetFile) throws IOException
	    {
	    	File prova = new File(targetFile.toString());
	    	logger.info("File: "+prova.getAbsolutePath());
	    	long copiedFile =Files.copy(inputStream, targetFile, StandardCopyOption.REPLACE_EXISTING);
	    
	    	
	    	return copiedFile;
	    }

	public CopyFiles() {
		super();
		// TODO Auto-generated constructor stub
	}

}
