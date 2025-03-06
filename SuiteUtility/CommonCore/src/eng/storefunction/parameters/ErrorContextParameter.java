package eng.storefunction.parameters;
import eng.storefunction.*;

/**
  Classe che permette l'esecuzione di una store procedure semplificando la creazione
  della store.
*/

import java.sql.*;


public class ErrorContextParameter extends StoreParameter {
			
	public ErrorContextParameter(int index)
	{
		super(index, "ErrorContext", null, java.sql.Types.VARCHAR, OUT );
	}
	
	public StoreParameter cloneMe()
	{
		return new ErrorContextParameter(index);
	}
}
