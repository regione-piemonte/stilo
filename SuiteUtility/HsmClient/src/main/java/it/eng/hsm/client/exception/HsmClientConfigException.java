/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class HsmClientConfigException extends Exception {

	public HsmClientConfigException() {
	}

	public HsmClientConfigException(String message) {
		super(message);
	}

	public HsmClientConfigException(String message, Throwable cause) {
		super(message, cause);
	}

}
