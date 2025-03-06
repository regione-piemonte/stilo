package it.eng.hybrid.module.stampaFiles.outputProvider;

public interface OutputProvider {

	public void saveOutputParameter() throws Exception;
	public boolean getAutoClosePostPrint();
	public String getCallBackAskForClose();
}
