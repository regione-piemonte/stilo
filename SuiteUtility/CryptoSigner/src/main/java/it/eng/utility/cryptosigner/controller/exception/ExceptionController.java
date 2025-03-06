package it.eng.utility.cryptosigner.controller.exception;

/**
 * Eccezione specializzata per il processo di controllo dei file firmati
 * @author Rigo Michele
 *
 */
public class ExceptionController extends Exception{
	 public ExceptionController(String message) {
			super(message);
		    }
	 
	 public ExceptionController(Exception e) {
			super(e);
		    }
}
