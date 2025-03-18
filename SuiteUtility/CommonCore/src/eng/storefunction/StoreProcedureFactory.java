/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public interface StoreProcedureFactory
{
	/**
		Questo metodo crea una nuova istanza di StoreProcedure replicando un'istanza
		statica. La StoreProcedure ritornata è thread safe.
	*/
	public StoreProcedure getStoreProcedure(String procedureName);
}
