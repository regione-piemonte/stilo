/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class ImportDocIndexException extends Exception {

	private static final long serialVersionUID = -62691510466793557L;

	private ErrorInfo errorInfo;
	private String errorContext;

	public ImportDocIndexException() {
		this((ErrorInfo) null);
	}

	public ImportDocIndexException(Throwable cause) {
		this(cause, null);
	}

	public ImportDocIndexException(String message) {
		this(message, (ErrorInfo) null);
	}

	public ImportDocIndexException(String message, Throwable cause) {
		this(message, cause, null);
	}

	public ImportDocIndexException(ErrorInfo errorInfo) {
		this(errorInfo.getDescription(), errorInfo, null);
	}

	public ImportDocIndexException(String message, Throwable cause, ErrorInfo errorInfo) {
		this(message, cause, errorInfo, null);
	}

	public ImportDocIndexException(String message, ErrorInfo errorInfo) {
		this(message, errorInfo, null);
	}

	public ImportDocIndexException(Throwable cause, ErrorInfo errorInfo) {
		this(errorInfo.getDescription(), cause, errorInfo, null);
	}

	public ImportDocIndexException(String message, String errorContext) {
		this(message, null, errorContext);
	}

	public ImportDocIndexException(ErrorInfo errorInfo, String errorContext) {
		super();
		this.errorInfo = errorInfo;
		this.errorContext = errorContext;
	}

	public ImportDocIndexException(String message, Throwable cause, ErrorInfo errorInfo, String errorContext) {
		super(message, cause);
		this.errorInfo = errorInfo;
		this.errorContext = errorContext;
	}

	public ImportDocIndexException(String message, ErrorInfo errorInfo, String errorContext) {
		super(message);
		this.errorInfo = errorInfo;
		this.errorContext = errorContext;
	}

	public ImportDocIndexException(Throwable cause, ErrorInfo errorInfo, String errorContext) {
		super(cause);
		this.errorInfo = errorInfo;
		this.errorContext = errorContext;
	}

	public ErrorInfo getErrorInfo() {
		return this.errorInfo;
	}

	public String getErrorContext() {
		return errorContext;
	}
}
