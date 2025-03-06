package it.eng.wordOpener.exception;

public class InitException extends Exception {

	private static final long serialVersionUID = -8376522192725973072L;

	public InitException(String parameter){
		super("Manca il parametro " + parameter);
	}
}
