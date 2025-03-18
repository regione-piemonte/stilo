/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.wordOpener.exception.UnableToEditFileException;

import java.io.File;

public interface WriteAndSave {

	public void openProcess(File file) throws UnableToEditFileException;
	
	public void saveFile();
}
