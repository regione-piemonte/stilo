/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class VersionHandlerWarning extends VersionHandlerException {

	public VersionHandlerWarning(String message) {
		super(message);
	}

	public VersionHandlerWarning(int innerCode, String message) {
		super(innerCode, message);
	}

}
