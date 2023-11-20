/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public class FreeMarkerCreateDocumentException extends Exception {

	private static final long serialVersionUID = -4532202906096679697L;

	public FreeMarkerCreateDocumentException(String message) {
		super(message);
	}

	public FreeMarkerCreateDocumentException(String message, Throwable throwable) {
		super(message, throwable);
	}

}