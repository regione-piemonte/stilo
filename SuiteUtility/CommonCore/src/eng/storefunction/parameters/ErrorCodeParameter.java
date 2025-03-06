package eng.storefunction.parameters;
import eng.storefunction.*;

/**
  Classe che permette l'esecuzione di una store procedure semplificando la creazione
  della store.
*/

import java.sql.*;


public class ErrorCodeParameter extends StoreParameter {
	
		
	public ErrorCodeParameter(int index)
	{
		super(index, "ErrorCode", null, java.sql.Types.INTEGER, OUT );
	}
	
	public StoreParameter cloneMe()
	{
		return new ErrorCodeParameter(index);
	}
}
