package it.eng.hybrid.module.wordOpener.error;

public interface ErrorManager {

	public void manageError(String message);
	
	public void manageExcepion(Exception pException);

	public void justClose();
}
