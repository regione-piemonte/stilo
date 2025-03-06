package it.eng.wordOpener.fileProvider;

import it.eng.wordOpener.exception.UnableToRetrieveFileException;
import it.eng.wordOpener.exception.UnableToSaveFileException;

import java.io.File;

import javax.swing.JApplet;

public interface FileProvider {

	public File getFile() throws UnableToRetrieveFileException;
	
	public void saveFileToServer(File pFile) throws UnableToSaveFileException;
	
	public void setApplet(JApplet applet);
}
