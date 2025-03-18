/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.InputStream;

import javax.swing.JApplet;

import org.jpedal.examples.viewer.config.PreferenceManager;


public interface FileOutputProvider {

	public void saveOutputFile(InputStream in, String fileInputName, PreferenceManager preferenceManager) throws Exception;
	public void saveOutputParameter(JApplet applet, PreferenceManager preferenceManager) throws Exception;
	public boolean tryTosaveOutputFile(InputStream in, String fileInputName, PreferenceManager preferenceManager) throws Exception;
}
