/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
