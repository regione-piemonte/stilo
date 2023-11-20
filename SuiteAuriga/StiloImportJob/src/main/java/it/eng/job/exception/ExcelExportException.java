/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class ExcelExportException extends Exception {

	private static final long serialVersionUID = -2807557878490032631L;

	public ExcelExportException() {
	}

	public ExcelExportException(String message) {
		super(message);
	}

	public ExcelExportException(Throwable cause) {
		super(cause);
	}

	public ExcelExportException(String message, Throwable cause) {
		super(message, cause);
	}

}
