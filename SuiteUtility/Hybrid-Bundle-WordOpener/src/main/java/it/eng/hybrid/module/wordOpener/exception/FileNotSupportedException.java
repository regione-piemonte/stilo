package it.eng.hybrid.module.wordOpener.exception;

public class FileNotSupportedException extends Exception {

	private static final long serialVersionUID = 8830145611898107521L;

	public FileNotSupportedException(String formato){
		super("Il tipo di file con estensione " + formato + " non è supportato"); 
	}
}
