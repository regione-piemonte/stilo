/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.io.InputStream;


public interface FileOutputProvider {

	public boolean saveOutputFile(String id, InputStream in, String fileInputName, String tipoBusta, String... params) throws Exception;
	public void saveOutputParameter() throws Exception;
	public boolean getAutoClosePostSign();
	public String getCallBackAskForClose();
	
}
