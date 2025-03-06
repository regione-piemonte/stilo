package org.jpedal.examples.viewer.fileProvider;

import org.jpedal.examples.viewer.config.PreferenceManager;


public interface FileInputProvider {

	public FileInputResponse getFile( PreferenceManager preferenceManager) throws Exception;
	
}
