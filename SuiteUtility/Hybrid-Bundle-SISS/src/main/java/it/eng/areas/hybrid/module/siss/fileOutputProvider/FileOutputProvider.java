package it.eng.areas.hybrid.module.siss.fileOutputProvider;


import java.io.InputStream;


public interface FileOutputProvider {

	public boolean saveOutputFile(String id, InputStream in, String fileInputName, String tipoBusta, String... params) throws Exception;
	public void saveOutputParameter() throws Exception;
	public boolean getAutoClosePostSign();
	public String getCallBackAskForClose();
	
}
