package it.eng.hybrid.module.firmaCertificato.inputFileProvider;


public interface FileInputProvider {

	public FileInputResponse getFileInputResponse() throws Exception;
	public String getPin() throws Exception;
	public String getAlias() throws Exception;
	public String getMetodoFirma() throws Exception;
}
