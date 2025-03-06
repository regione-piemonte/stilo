package it.eng.wordOpener;

import it.eng.wordOpener.exception.UnableToEditFileException;

import java.io.File;

public interface WriteAndSave {

	public void openProcess(File file) throws UnableToEditFileException;
	
	public void saveFile();
}
