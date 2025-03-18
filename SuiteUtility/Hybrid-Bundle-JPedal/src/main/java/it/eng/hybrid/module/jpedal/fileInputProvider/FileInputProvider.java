/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;

public interface FileInputProvider {

	public FileInputResponse getFile( PreferenceManager preferenceManager) throws Exception;
	
}
