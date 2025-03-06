package it.eng.suiteutility.mimedetector;

public class MimeDetectorException extends Exception {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MimeDetectorException(String msg)
	  {
	    super(msg);
	  }

	  public MimeDetectorException(String msg, Throwable cause) {
	    super(msg, cause);
	  }
}
