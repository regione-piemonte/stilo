package it.eng.core.business.export.utility;

import java.io.File;

public class ImportInfo {

	private File importFile;
	private boolean transactional=true;
	
	public File getImportFile() {
		return importFile;
	}
	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}
	public boolean isTransactional() {
		return transactional;
	}
	public void setTransactional(boolean transactional) {
		this.transactional = transactional;
	}
	
	
	
}
