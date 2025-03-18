/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import eng.storefunction.*;

/**
  Classe che permette l'esecuzione di una store procedure semplificando la creazione
  della store.
*/

import java.sql.*;


public class AutoCommitParameter extends StoreParameter {
			
	public AutoCommitParameter(int index, String commit_type)
	{
		super(index, "FlgAutoCommitIn", commit_type, java.sql.Types.VARCHAR, IN );
	}
	
	public StoreParameter cloneMe()
	{
		return new AutoCommitParameter(index, getValue()+"");
	}
}
