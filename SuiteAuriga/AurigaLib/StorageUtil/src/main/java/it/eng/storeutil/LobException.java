/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class LobException extends Exception {
	
	private static final long serialVersionUID = 6726386799232480044L;

	public LobException(Exception ex) {
		super(ex);
	}

	public LobException(String message, Exception ex) {
		super(message, ex);
	}

}
