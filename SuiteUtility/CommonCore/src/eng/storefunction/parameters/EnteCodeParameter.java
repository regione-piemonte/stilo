package eng.storefunction.parameters;
import eng.storefunction.*;
import eng.bean.*;
/**
  Classe che permette l'esecuzione di una store procedure semplificando la creazione
  della store.
*/

import java.sql.*;


public class EnteCodeParameter extends StoreParameter implements HttpSessionParameter {


	public EnteCodeParameter(int index)
	{
		super(index, null, null, java.sql.Types.VARCHAR, OUT );
	}

	public void setHttpSession(javax.servlet.http.HttpSession session)
	{
		Object obj = eng.util.GetProperties.getUserProperty(  UserProperties.UP_ID_ENTE, session);
		if (obj != null)
		{
			setValue( obj );
		}
	}

	public StoreParameter cloneMe()
	{
		return new EnteCodeParameter(index);
	}
}
