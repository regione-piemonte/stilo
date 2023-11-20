/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;


/**
 * bean di input per analizeffetuare controlli su un file
 * @author Russo
 *
 */
public class InputFileBean {
	private File inputFile;
	private File timestamp;
	public File getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(File timestamp) {
		this.timestamp = timestamp;
	}
	private String fileName;
	private File temporaryDir;
	
	public File getTemporaryDir() {
		return temporaryDir;
	}
	public void setTemporaryDir(File temporaryDir) {
		this.temporaryDir = temporaryDir;
	}
	public File getInputFile() {
		return inputFile;
	}
	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
