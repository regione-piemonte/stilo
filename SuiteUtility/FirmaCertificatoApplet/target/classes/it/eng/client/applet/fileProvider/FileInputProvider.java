package it.eng.client.applet.fileProvider;


public interface FileInputProvider {

	public FileInputResponse getFileInputResponse() throws Exception;
	public String getPin() throws Exception;
	public String getAlias() throws Exception;
	public String getMetodoFirma() throws Exception;
	
}
