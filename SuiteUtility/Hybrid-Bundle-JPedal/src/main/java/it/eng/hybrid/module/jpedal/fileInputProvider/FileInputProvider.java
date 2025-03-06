package it.eng.hybrid.module.jpedal.fileInputProvider;

import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;

public interface FileInputProvider {

	public FileInputResponse getFile( PreferenceManager preferenceManager) throws Exception;
	
}
