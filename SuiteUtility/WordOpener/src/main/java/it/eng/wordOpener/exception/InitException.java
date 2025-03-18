/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class InitException extends Exception {

	private static final long serialVersionUID = -8376522192725973072L;

	public InitException(String parameter){
		super("Manca il parametro " + parameter);
	}
}
