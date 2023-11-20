/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AggiungiMarcaTemporaleException extends Exception {

	private static final long serialVersionUID = -62691510466793557L;

	private ErrorInfo errorInfo;
	private String errorContext;

	public AggiungiMarcaTemporaleException() {
		this((ErrorInfo) null);
	}

	public AggiungiMarcaTemporaleException(Throwable cause) {
		this(cause, null);
	}

	public AggiungiMarcaTemporaleException(String message) {
		this(message, (ErrorInfo) null);
	}

	public AggiungiMarcaTemporaleException(String message, Throwable cause) {
		this(message, cause, null);
	}

	public AggiungiMarcaTemporaleException(ErrorInfo errorInfo) {
		this(errorInfo.getDescription(), errorInfo, null);
	}

	public AggiungiMarcaTemporaleException(String message, Throwable cause, ErrorInfo errorInfo) {
		this(message, cause, errorInfo, null);
	}

	public AggiungiMarcaTemporaleException(String message, ErrorInfo errorInfo) {
		this(message, errorInfo, null);
	}

	public AggiungiMarcaTemporaleException(Throwable cause, ErrorInfo errorInfo) {
		this(errorInfo.getDescription(), cause, errorInfo, null);
	}

	public AggiungiMarcaTemporaleException(String message, String errorContext) {
		this(message, null, errorContext);
	}

	public AggiungiMarcaTemporaleException(ErrorInfo errorInfo, String errorContext) {
		super();
		this.errorInfo = errorInfo;
		this.errorContext = errorContext;
	}

	public AggiungiMarcaTemporaleException(String message, Throwable cause, ErrorInfo errorInfo, String errorContext) {
		super(message, cause);
		this.errorInfo = errorInfo;
		this.errorContext = errorContext;
	}

	public AggiungiMarcaTemporaleException(String message, ErrorInfo errorInfo, String errorContext) {
		super(message);
		this.errorInfo = errorInfo;
		this.errorContext = errorContext;
	}

	public AggiungiMarcaTemporaleException(Throwable cause, ErrorInfo errorInfo, String errorContext) {
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
