package it.eng.hybrid.module.selectCertificati.response;

import javax.swing.JApplet;

public interface GenericResponse {

	public void saveOutputParameter() throws Exception;
	public boolean saveOutput(String... params) throws Exception;
	public boolean getAutoClosePostSearch();
	public String getCallBackAskForClose();
	
}
