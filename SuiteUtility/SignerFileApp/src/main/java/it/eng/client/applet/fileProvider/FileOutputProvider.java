/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.InputStream;

import javax.swing.JApplet;


public interface FileOutputProvider {

	public void saveOutputFile(InputStream in, String fileInputName, String tipoBusta) throws Exception;
	public void saveOutputParameter(JApplet applet) throws Exception;
	public boolean getAutoClosePostSign();
	public String getCallBackAskForClose();
	
}
