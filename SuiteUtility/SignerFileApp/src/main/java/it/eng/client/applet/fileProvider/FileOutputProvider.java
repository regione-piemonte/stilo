package it.eng.client.applet.fileProvider;

import java.io.InputStream;

import javax.swing.JApplet;


public interface FileOutputProvider {

	public void saveOutputFile(InputStream in, String fileInputName, String tipoBusta) throws Exception;
	public void saveOutputParameter(JApplet applet) throws Exception;
	public boolean getAutoClosePostSign();
	public String getCallBackAskForClose();
	
}
