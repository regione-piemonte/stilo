/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import eng.storefunction.*;

/**
  Classe che permette l'esecuzione di una store procedure semplificando la creazione
  della store.
*/

import java.sql.*;


public class ErrorMessageParameter extends StoreParameter {
			
	public ErrorMessageParameter(int index)
	{
		super(index, "ErrorMessage", null, java.sql.Types.VARCHAR, OUT );
	}
	
	public StoreParameter cloneMe()
	{
		return new ErrorMessageParameter(index);
	}
}
