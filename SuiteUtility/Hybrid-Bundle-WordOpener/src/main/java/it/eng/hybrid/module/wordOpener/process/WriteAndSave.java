package it.eng.hybrid.module.wordOpener.process;

import it.eng.hybrid.module.wordOpener.exception.UnableToEditFileException;

import java.io.File;

public interface WriteAndSave {

	public void openProcess(File file) throws UnableToEditFileException;
	
	public void saveFile();
}
