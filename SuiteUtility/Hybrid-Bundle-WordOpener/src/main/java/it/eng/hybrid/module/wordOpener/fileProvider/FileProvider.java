package it.eng.hybrid.module.wordOpener.fileProvider;

import it.eng.hybrid.module.wordOpener.exception.UnableToRetrieveFileException;
import it.eng.hybrid.module.wordOpener.exception.UnableToSaveFileException;

import java.io.File;

public interface FileProvider {

	public File getFile() throws UnableToRetrieveFileException;
	
	public void saveFileToServer(File pFile) throws UnableToSaveFileException;
	
	public String getArgFunction();
}
