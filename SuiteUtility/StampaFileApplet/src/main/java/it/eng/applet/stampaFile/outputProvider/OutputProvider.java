package it.eng.applet.stampaFile.outputProvider;

import javax.swing.JApplet;



public interface OutputProvider {

	public void saveOutputParameter(JApplet applet) throws Exception;
	public boolean getAutoClosePostPrint();
	public String getCallBackAskForClose();
}
