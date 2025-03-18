/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.wordOpener.exception.UnableToRetrieveFileException;
import it.eng.wordOpener.exception.UnableToSaveFileException;

import java.io.File;

import javax.swing.JApplet;

public interface FileProvider {

	public File getFile() throws UnableToRetrieveFileException;
	
	public void saveFileToServer(File pFile) throws UnableToSaveFileException;
	
	public void setApplet(JApplet applet);
}
