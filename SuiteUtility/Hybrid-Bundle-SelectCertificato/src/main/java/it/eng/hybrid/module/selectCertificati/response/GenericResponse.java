/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.swing.JApplet;

public interface GenericResponse {

	public void saveOutputParameter() throws Exception;
	public boolean saveOutput(String... params) throws Exception;
	public boolean getAutoClosePostSearch();
	public String getCallBackAskForClose();
	
}
