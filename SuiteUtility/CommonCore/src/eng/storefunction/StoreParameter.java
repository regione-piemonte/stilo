package eng.storefunction;

/**
  Classe che permette l'esecuzione di una store procedure semplificando la creazione
  della store.
*/

import java.math.BigDecimal;
import java.sql.*;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

import eng.database.exception.EngSqlNoApplException;


public class StoreParameter {

	public final static int IN =  0x1;
	public final static int OUT = 0x2;
	public final static int IN_OUT = 0x3;

	/**
		Tipo di parametro (IN, OUT, IN_OUT)
	*/
	protected int inOut = IN;

	/**
		Tipo di dati SQL (vedi java.sql.Types)
	*/
	protected int type = Types.VARCHAR;

	/**
		Valore del parametro (in/out)
	*/
	protected Object value = null;

	/**
		Nome del parametro
	*/
	protected String name = null;

	/**
		Indice all'interno della store procedure
	*/
	protected int index = 0;

	public StoreParameter(int index, String name, Object value, int type, int inout )
	{
		this.index = index;
		this.name = name;
		this.value = value;
		this.type = type;
		this.inOut = inout;
	}

        public StoreParameter(int index, String name, int type, int inout )
	{
		this(index, name, null, type, inout );
	}

	/**
		Imposta un parametro di output del tipo desiderato
	*/
	public StoreParameter(int index, int type)
	{
		this(index, null, null, type, OUT );
	}

	/**
		Imposta un parametro di output del tipo desiderato
	*/
	public StoreParameter(int index)
	{
		this(index, null, null, java.sql.Types.VARCHAR, OUT );
	}

	/**
		Imposta un parametro di input (il tipo viene impostato a VARCHAR)
	*/
	public StoreParameter(int index, Object val)
	{
		this(index, null, null, java.sql.Types.VARCHAR, IN );
	}

	/**
		Imposta un parametro (il tipo viene impostato a VARCHAR)
	*/
	public StoreParameter(int index, Object val, int type)
	{
		this(index, null, null, java.sql.Types.VARCHAR, type );
	}

	/**
		Ritorna uno dei seguenti valori IN, OUT, IN_OUT
	*/
	public int getInOut()
	{
		return inOut;
	}

	/**
		Ritorna uno dei seguenti valori IN, OUT, IN_OUT
	*/
	public void setInOut(int inOut)
	{
		this.inOut = inOut;
	}

	/**
		Ritorna il nome del parametro
	*/
	public String getName()
	{
		return name;
	}

	/**
		Imposta il nome del parametro
	*/
	public void setName(String name)
	{
		this.name = name;
	}

	/**
		Ritorna l'indice del parametro
	*/
	public int getIndex()
	{
		return index;
	}

	/**
		Imposta l'indice del parametro
	*/
	public void setIndex(int index)
	{
		this.index = index;
	}

	/**
		Imposta il valore del parametro
	*/
	public void setValue(Object value)
	{
		this.value = value;
	}

	/**
		Recupera il valore del parametro
	*/
	public Object getValue()
	{
		if(type==Types.INTEGER)
		{
			if(value instanceof BigDecimal)
				return new Integer(((BigDecimal)value).intValue());
			else
				return this.value;
		}
		else
			return this.value;
	}

	public void prepareStore(CallableStatement stmt) throws SQLException, EngSqlNoApplException
	{
		if ( (getInOut() & IN) != 0 )
		{
			prepareStore((PreparedStatement)stmt);
		}

		if ( (getInOut() & OUT) != 0 )
		{
			stmt.registerOutParameter( index, type );
		}
	}

	public void prepareStore(PreparedStatement stmt) throws SQLException, EngSqlNoApplException
	{
		if ( ((getInOut() & IN) != 0)  && (value != null) && (!value.equals("")) )
		{

                  // ghf 20/05/2005 : corretto caso di gestione type=numeric
                  switch (type) {
                    case (Types.INTEGER):
                      stmt.setInt(index, Integer.parseInt(value + ""));
                      break;
                    case (Types.NUMERIC):
                      stmt.setLong(index, Long.parseLong(value + ""));
                      break;
                    case (Types.VARCHAR):
                      stmt.setString(index, value + "");
                      break;
                    default:
                      stmt.setNull(index, type);
                  }

                  //if (Types.INTEGER == type)
                  //{
                  //        stmt.setInt(index, Integer.parseInt(value+"") );
                  //}
                  //else
                  //{
                  //        stmt.setString(index,  value+"" );
                  //}
		}
		else if ( (getInOut() & IN) != 0 )
		{
			stmt.setNull( index, type);
		}
	}

	public StoreParameter cloneMe()
	{
		return new StoreParameter(index, name, value, type, inOut );
	}

	public Object getAttributeValue()
	{
		return getValue();
	}

	public int getType() {
		int ret = (new Integer(type)).intValue();
		return ret;
	}
	

}
