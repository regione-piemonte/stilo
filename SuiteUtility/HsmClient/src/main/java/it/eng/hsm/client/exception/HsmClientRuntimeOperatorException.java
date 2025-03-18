/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.bouncycastle.operator.RuntimeOperatorException;

public class HsmClientRuntimeOperatorException extends RuntimeOperatorException {

	public HsmClientRuntimeOperatorException(String message) {
		super(message);
	}

	public HsmClientRuntimeOperatorException(String message, Throwable cause) {
		super(message, cause);
	}

}
