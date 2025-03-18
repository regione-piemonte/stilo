/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.jpedal.examples.viewer.config.PreferenceManager;


public interface FileInputProvider {

	public FileInputResponse getFile( PreferenceManager preferenceManager) throws Exception;
	
}
