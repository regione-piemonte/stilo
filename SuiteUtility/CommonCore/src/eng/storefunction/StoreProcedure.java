package eng.storefunction;


import java.util.*;
import java.sql.*;
import eng.database.modal.*;
import eng.storefunction.parameters.ErrorMessageParameter;

/**
  Classe che permette l'esecuzione di una store procedure semplificando la creazione
  della store.
*/
public class StoreProcedure {

	java.util.Vector parameters;
	String storeName = "";
	java.sql.Connection con = null;

	/**

	*/
	public StoreProcedure(String storeName, java.util.Vector parameters, java.sql.Connection con)
	{
		this.storeName = storeName;
		this.parameters = parameters;
		this.con = con;

		if (parameters == null) parameters = new java.util.Vector();
	}

	public StoreProcedure(java.sql.Connection con)
	{
		this(null,null,con);
	}

	public StoreProcedure(java.util.Vector parameters, java.sql.Connection con)
	{
		this(null,parameters,con);
	}

	public StoreProcedure(String storeName, java.sql.Connection con)
	{
		this(storeName, null, con);
	}

	public StoreProcedure(String storeName)
	{
		this(storeName, null, null);
	}

	public StoreProcedure(String storeName, java.util.Vector parameters)
	{
		this(storeName, parameters, null);
	}

	public StoreProcedure(String storeName, StoreParameter[] parameters)
	{
		this(storeName, null, null);
		if (parameters != null)
		{
			this.parameters = new Vector();
			for (int i=0; i<parameters.length; ++i)
			{
				this.parameters.add(parameters[i]);
			}
		}
	}

	public EngResultSet executeQuery(String sql) throws StoreProcedureException
	{
		java.sql.PreparedStatement stmt = null;
		java.sql.ResultSet rs = null;

		if (sql == null) throw new StoreProcedureException(-100, "SQL non specificato o nullo.");
		if (con == null) throw new StoreProcedureException(-101, "Connessione non specificata o nulla.");

		try {
				stmt = con.prepareStatement(sql);
				prepareStore(stmt);
				rs = stmt.executeQuery();

				return new EngResultSet(rs);

		} catch (StoreProcedureException ex)
		{
			throw ex;

		} catch (Exception ex)
		{
			throw new StoreProcedureException(-103, ex.getMessage());
		}
		finally
		{
			if (stmt != null) try { stmt.close(); }	catch (Exception x){}
			if (rs != null) try { rs.close(); }	catch (Exception x){}
		}

	}

	public void execute(String sql) throws StoreProcedureException
	{
		java.sql.PreparedStatement stmt = null;
		java.sql.ResultSet rs = null;

		if (sql == null) throw new StoreProcedureException(-100, "SQL non specificato o nullo.");
		if (con == null) throw new StoreProcedureException(-101, "Connessione non specificata o nulla.");

		try {
				stmt = con.prepareStatement(sql);
				prepareStore(stmt);
				stmt.execute(sql);

		} catch (StoreProcedureException ex)
		{
			throw ex;

		} catch (Exception ex)
		{
			throw new StoreProcedureException(-103, ex.getMessage());
		}
		finally
		{
			if (stmt != null) try { stmt.close(); }	catch (Exception x){}
			if (rs != null) try { rs.close(); }	catch (Exception x){}
		}

	}

	public void execute() throws StoreProcedureException
	{
		java.sql.CallableStatement stmt = null;
		if (storeName == null) throw new StoreProcedureException(-100, "Nome della store non specificato o nullo.");
		if (con == null) throw new StoreProcedureException(-101, "Connessione non specificata o nulla.");

		int lastIndex = getMaxIndex( parameters );
		int resultVal = 0;
		String sql = "{call ? := " + storeName + "(";
		for (int i=1; i< lastIndex; ++i)
		{
			if (i > 1 )  sql+=",";
			sql += "?";
		}
		sql += ")}";

		try
                {
                    stmt = (CallableStatement)con.prepareCall(sql);
                    prepareStore(stmt);
                    stmt.execute();
                    resultVal = stmt.getInt(1);
                    // GHF 08/03/2005 spostato il controllo dopo il fetch dei parametri
                    //                per consentire il fetch degli OUT PRMs
                    // if (resultVal == 0)
                    // {
                    //        throw new StoreProcedureException(resultVal, "Command SQL = "+ sql +". Exception=" + stmt.getString(lastIndex));
                    // }

                    for (int i=0; i<parameters.size(); ++i)
                    {
                            if (parameters.elementAt(i) instanceof StoreParameter)
                            {
                                    StoreParameter storeParameter = (StoreParameter)parameters.elementAt(i);
                                    if ((storeParameter.getInOut() & storeParameter.OUT) != 0)
                                    {
                                            //eng.util.Logger.getLogger().info("Imposto valore per " + storeParameter.getName());
                                            storeParameter.setValue( stmt.getObject(storeParameter.getIndex()) );
                                    }
                            }
                    }

                    // GHF 08/03/2005
                    if (resultVal == 0)
                    {
                    	String msg = stmt.getString(getErrorMessageIndex());
                    	eng.util.Logger.getLogger().error("Command SQL = "+ sql +". Exception["+resultVal+"]=" + msg);
                    	throw new StoreProcedureException(resultVal, ""+ msg);
                    }
                }
		catch (StoreProcedureException ex)
		{
			eng.util.Logger.getLogger().info(ex.getMessage());
			throw ex;

		}
		catch (Exception ex)
		{
			eng.util.Logger.getLogger().fatal("Errore durante l'esecuzione della store: "+sql,ex);
			throw new StoreProcedureException(-103, ex.getMessage());
		}
		finally
		{
			if (stmt != null) try { stmt.close(); }	catch (Exception x){}
		}
	}

	protected void prepareStore(java.sql.CallableStatement stmt)  throws StoreProcedureException
	{
		StoreParameter p = null;
		try {
			for (int i=0; i<parameters.size(); ++i)
			{
				p = (StoreParameter)parameters.elementAt(i);
				((StoreParameter)parameters.elementAt(i)).prepareStore(stmt);
			}
		} catch (Exception ex)
		{

			throw new StoreProcedureException(-102, ex.getMessage());
		}
	}

	protected void prepareStore(java.sql.PreparedStatement stmt)  throws StoreProcedureException
	{
		try {
			for (int i=0; i<parameters.size(); ++i)
			{
				((StoreParameter)parameters.elementAt(i)).prepareStore(stmt);
			}
		} catch (Exception ex)
		{
			throw new StoreProcedureException(-102, ex.getMessage());
		}
	}

	protected int getMaxIndex(Vector params)
	{
		int k = 0;
		for (int i=0; i<parameters.size(); ++i)
		{
			if (parameters.elementAt(i) instanceof StoreParameter)
			{
				k = ( k < ((StoreParameter)parameters.elementAt(i)).getIndex()) ? ((StoreParameter)parameters.elementAt(i)).getIndex() : k;
			}
		}
		return k;
	}

	public void setConnection(java.sql.Connection con)
	{
		this.con = con;
	}

	public void fillWebParameters(javax.servlet.http.HttpServletRequest request)
	{
		fillWebParameters(request,null);
	}

	public void fillWebParameters(javax.servlet.http.HttpSession session)
	{
		fillWebParameters(null, session);
	}

	public void fillWebParameters(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpSession session)
	{
		for (int i=0; i<parameters.size(); ++i)
		{
			if (request != null && parameters.elementAt(i) instanceof HttpRequestParameter)
			{
				((HttpRequestParameter)parameters.elementAt(i)).setHttpRequest(request);
			}
			if (session != null && parameters.elementAt(i) instanceof HttpSessionParameter)
			{
				((HttpSessionParameter)parameters.elementAt(i)).setHttpSession(session);
			}
		}
	}

	public void fillWebAttributes(javax.servlet.http.HttpServletRequest request)
	{
		for (int i=0; i<parameters.size(); ++i)
		{
			StoreParameter sp = (StoreParameter)parameters.elementAt(i);

			if (sp.getName() != null)
			{
				Object atr = sp.getAttributeValue();
				if (atr != null)
				{
					request.setAttribute(sp.getName(), atr);
				}
			}
		}
	}

	public String getStoreName(){
		return storeName;
	}
	

	public StoreProcedure cloneMe()
	{
		Vector v = new Vector();
		for (int i=0; i<parameters.size(); ++i)
		{
			v.add( 	((StoreParameter)parameters.elementAt(i)).cloneMe() );
		}
		return new StoreProcedure(storeName,  v);
	}

	public StoreParameter getParameterByName(String name)
	{
		for (int i=0; i<parameters.size(); ++i)
		{
			if ( ((StoreParameter)parameters.elementAt(i)).getName() != null &&
			     ((StoreParameter)parameters.elementAt(i)).getName().equals(name) )
			{
				return (StoreParameter)parameters.elementAt(i);
			}
		}
		return null;
	}

        /** Recupera il parametro i-esimo. I parametri partono da 1.
         *
         */
        public StoreParameter getParameter(int  i)
	{
                if (i<=0) return null;
		if ( i-1<parameters.size())
		{
			return (StoreParameter)parameters.elementAt(i-1);
		}
		return null;
	}

        public int size()
        {
            if (parameters == null)
            {
                return -1;
            }
            else
            {
                return parameters.size();
            }
        }
        
        protected int getErrorMessageIndex() {
    		int k = 0;
        	for (int i=0; i<parameters.size(); ++i)
        	{
        		if (parameters.elementAt(i) instanceof ErrorMessageParameter) {
        			k = ((ErrorMessageParameter)parameters.elementAt(i)).getIndex();
        			break;
        		}
        	}
        	if (k == 0)
        		return getMaxIndex(parameters);
        	else return k;
        }
}
