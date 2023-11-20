/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FolderUtilException extends Exception {

	private static final long serialVersionUID = 694580717819445899L;

	public FolderUtilException() {
	}

	public FolderUtilException(String message) {
		super(message);
	}

	public FolderUtilException(Throwable cause) {
		super(cause);
	}

	public FolderUtilException(String message, Throwable cause) {
		super(message, cause);
	}

}
