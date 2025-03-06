package it.eng.utility.cryptosigner.exception;

/**
 * Eccezione specializzata per la firma
 * @author Rigo Michele
 * @verison 0.1
 * 14/04/2010
 */
public class CryptoSignerException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CryptoSignerException() {
		super();
	}

	public CryptoSignerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CryptoSignerException(String arg0) {
		super(arg0);
	}

	public CryptoSignerException(Throwable arg0) {
		super(arg0);
	}
}