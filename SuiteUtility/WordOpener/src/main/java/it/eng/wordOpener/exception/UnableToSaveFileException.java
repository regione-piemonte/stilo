package it.eng.wordOpener.exception;

public class UnableToSaveFileException extends Exception {

	public UnableToSaveFileException(String string) {
		super(string);
	}

	public UnableToSaveFileException(String string, Throwable e) {
		super(string, e);
	}

	private static final long serialVersionUID = -1044232644864767264L;

}
