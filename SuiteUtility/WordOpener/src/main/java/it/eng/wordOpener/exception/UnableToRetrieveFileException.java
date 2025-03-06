package it.eng.wordOpener.exception;

public class UnableToRetrieveFileException extends Exception {

	public UnableToRetrieveFileException(Exception e) {
		super("Errore " + e.getMessage());
	}
	
	public UnableToRetrieveFileException(String message, Exception e) {
		super(message);
	}

	private static final long serialVersionUID = -4903215172137046458L;

}
