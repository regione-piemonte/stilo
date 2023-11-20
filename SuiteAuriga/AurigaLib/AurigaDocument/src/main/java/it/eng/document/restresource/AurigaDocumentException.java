/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

public class AurigaDocumentException extends WebApplicationException {

	private static final long serialVersionUID = 1L;
	
	public AurigaDocumentException() {
		super();
	}

	public AurigaDocumentException(int status) {
		super(status);
	}
	
	public AurigaDocumentException(Response response) {
		super(response);
	}

	public AurigaDocumentException(int status, String message) {
		super( getResponse(status, message) );
	}
	
	public AurigaDocumentException(Throwable cause) {
		super(cause);
	}
	
	public AurigaDocumentException(Throwable cause, int status) {
		super(cause, status);
	}

	private static Response getResponse(int status, String message) {
		final ResponseBuilderImpl builder = new ResponseBuilderImpl();
		builder.status(status);
		builder.entity(message);
		return builder.build();
	}
	
}
