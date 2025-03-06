package it.eng.client.applet.response;

import javax.swing.JApplet;

public interface GenericResponse {

	public void saveOutputParameter(JApplet applet) throws Exception;
	public boolean saveOutput(String... params) throws Exception;
	public boolean getAutoClosePostSearch();
	public String getCallBackAskForClose();
	
}
