/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;

import java.io.InputStream;

public interface FileOutputProvider {

	public void saveOutputFile(InputStream in, String fileInputName, PreferenceManager preferenceManager) throws Exception;
	public void saveOutputParameter(PreferenceManager preferenceManager) throws Exception;
	public boolean tryTosaveOutputFile(InputStream in, String fileInputName, PreferenceManager preferenceManager) throws Exception;
}
