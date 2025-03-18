/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class UnableToSaveFileException extends Exception {

	public UnableToSaveFileException(String string) {
		super(string);
	}

	public UnableToSaveFileException(String string, Throwable e) {
		super(string, e);
	}

	private static final long serialVersionUID = -1044232644864767264L;

}
