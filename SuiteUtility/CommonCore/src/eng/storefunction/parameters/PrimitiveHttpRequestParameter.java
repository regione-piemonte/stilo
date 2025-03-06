package eng.storefunction.parameters;
import eng.storefunction.*;

/**
  Classe che permette l'esecuzione di una store procedure semplificando la creazione
  della store.
*/

import java.sql.*;


public class PrimitiveHttpRequestParameter extends StoreParameter implements HttpRequestParameter {
	
	public PrimitiveHttpRequestParameter(int index, String name)
	{
		super(index, name, null, java.sql.Types.VARCHAR, IN );
	}
	
	public PrimitiveHttpRequestParameter(int index, String name, int inout)
	{
		super(index, name, null, java.sql.Types.VARCHAR, inout );
	}
	
	public void setHttpRequest(javax.servlet.http.HttpServletRequest request)
	{
		// Cerchiamo un parametro con il nome indicato e lo impostiamo come
		// valore del parametro.
		
		if (request == null || name == null) return;
		Object val = request.getParameter(this.name);
		if (val != null)
		{
			setValue( val );
		}	
	}
	
	public StoreParameter cloneMe()
	{
		return new PrimitiveHttpRequestParameter(index, name, inOut);
	}
}

