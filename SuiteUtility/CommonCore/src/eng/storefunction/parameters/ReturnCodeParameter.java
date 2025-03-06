package eng.storefunction.parameters;
import eng.storefunction.*;

/**
  Classe che permette l'esecuzione di una store procedure semplificando la creazione
  della store.
*/

import java.sql.*;


public class ReturnCodeParameter extends StoreParameter {
	
		
	public ReturnCodeParameter(int index)
	{
		super(index, null, null, java.sql.Types.INTEGER, OUT );
	}
	
	public StoreParameter cloneMe()
	{
		return new ReturnCodeParameter(index);
	}
}
