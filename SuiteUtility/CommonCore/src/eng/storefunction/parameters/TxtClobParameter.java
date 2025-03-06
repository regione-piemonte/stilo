package eng.storefunction.parameters;

import  eng.storefunction.*;
import java.io.Writer;
import java.io.Reader;
import java.io.OutputStream;
import java.io.InputStream;
import java.sql.*;

import org.apache.commons.io.IOUtils;

import eng.database.exception.*;
import oracle.sql.CLOB;


public class TxtClobParameter extends StoreParameter {


    /**
     * Usato per un clob in input
     */
    public TxtClobParameter(int index, String name, oracle.sql.CLOB value, int inOut)
    {
    	super(index, name, value, Types.CLOB, inOut );
    	//if (this.value == null)
    	//{
    	//	this.value = new Vector();
    	//}
    }

    /**
     * Usato per un clob in output
     */
    public TxtClobParameter(int index, String name)
    {
        this(index,name,null,OUT);
        //this.value = new Vector();
        this.value = null;
    }

    /**
     * Usato per un clob in output
     */
    public TxtClobParameter(int index )
    {
    	//this(index, null, new Vector(), OUT );
        this(index, null, null, OUT );
    }

    /**
     * Usato per un clob in input
     */
    public TxtClobParameter(int index, String name, oracle.sql.CLOB value)
    {
    	this(index, name, value, IN);
    }

    /**
     * Usato per un clob in input
     */
    public TxtClobParameter(int index, oracle.sql.CLOB value)
    {
    	this(index, null, value, IN);
    }

    /**
     * Metodo che imposta il Clob da memorizzare nella classe
     */
    public void setValue(Object val)
    {
        // mi tengo da parte uil vecchio valore
        oracle.sql.CLOB oldValue = null;
        if (this.value !=null) oldValue = (oracle.sql.CLOB) this.value;

        try
        {

            // caso in cui val sia == null
            if (val == null)
            {
                this.value = null;
            }
            // caso in cui val sia un oracle.sql.CLOB
            else if (val instanceof oracle.sql.CLOB)
            {
                this.value = val;
            }
            // qualunque altro oggetto
            else {
                this.value = null;
            }
        }
        catch (Exception e)
        {
            // in caso di errore chiudo il nuovo CLOB
            try { ((oracle.sql.CLOB)val).close(); } catch (Exception e1) {}
            try { ((oracle.sql.CLOB)val).freeTemporary(); } catch (Exception e1) {}
        }
        finally
        {
           // chiudo old value in sicurezza
           if (oldValue != null){
               try { oldValue.close(); } catch (Exception e) {}
               try { oldValue.freeTemporary(); } catch (Exception e) {}
           }
        }
    }

    /**
       Ritorna un oracle.sql.CLOB o null in caso di errore.
    */
    public Object getValue()
    {
    	return this.value;
    }


    public String toString()
    {
        if (this.value != null)
        {    return (this.value.toString() );
        } else {
            return null;
        }

    }

    public void prepareStore(CallableStatement stmt) throws SQLException, EngSqlNoApplException
    {
    	if ( (getInOut() & IN) != 0 )
    	{
    		prepareStore((PreparedStatement)stmt);
    	}

    	if ( (getInOut() & OUT) != 0 )
    	{
    		stmt.registerOutParameter( index, Types.CLOB );
    	}
    }

    public void prepareStore(PreparedStatement stmt) throws SQLException, EngSqlNoApplException
    {
        // caso in cui value e' valorizzato.
    	if ( (getInOut() & IN) != 0  && value != null)
    	{
    		stmt.setClob(index, (oracle.sql.CLOB) value);
    	}
        // caso in cui value e' vuoto
    	else
    	{
    		stmt.setNull( index,  Types.CLOB );
    	}
    }

    /**
     * Imposta un temporary CLOB vuoto e restituisce il writer
     * @param conn Connection
     * @return Writer
     * @throws SQLException
     * @throws Exception
     */
    public Writer setNewTemporaryCLOB(Connection conn) throws  SQLException, Exception
    {
        CLOB tempClob = null;
        // solo se stringa non nulla;
        try
        {
                // ricavo il CLOB temporaneo
                tempClob = getNewTemporaryCLOB(conn);
                // imposto il CLOB
                setValue(tempClob);
                // ricavo il writer
                return tempClob.getCharacterOutputStream();
        }
        catch(Exception e)
        {
            // svuoto il temporary Clob
            try {tempClob.freeTemporary();} catch (Exception e1){}
            // annullo tutto
            setValue(null);
            if (e instanceof SQLException)
            {
                throw (SQLException) e;
            } else {
                throw e;
            }
        }
    }

    /**
     * Imposta il CLOB in output con temporary CLOB creato da un valore striga
     *
     * @param conn Connection
     * @param sVal String
     * @throws SQLException
     * @throws Exception
     */
    public void setNewTemporaryCLOBStringValue (Connection conn, String sVal) throws  SQLException, Exception
    {
        CLOB tempClob = null;
        Writer wr = null;
        // solo se stringa non nulla;
        if (sVal == null) {
            setValue(null);
        } else {

            try {
                // ricavo il CLOB temporaneo
                tempClob = getNewTemporaryCLOB(conn);
                // ricavo il writer
                wr = tempClob.getCharacterOutputStream();
                //scrivo sul writer
                wr.write(sVal);
                // imposto il CLOB
                setValue(tempClob);
                // Flush and close del writer
                try {wr.flush();} catch (Exception e){}
                try {wr.close();} catch (Exception e){}
            }
            catch(Exception e)
            {
                // Flush and close del writer
                try {wr.flush();} catch (Exception e1){}
                try {wr.close();} catch (Exception e1){}
                try {tempClob.freeTemporary();} catch (Exception e1){}
                setValue(null);
                if (e instanceof SQLException)
                {
                    throw (SQLException) e;
                } else {
                    throw e;
                }
            }
        }
    }

    public String getCLOBStringValueAndClose() throws  SQLException, Exception
    {
        //eng.util.Logger.getLogger().info("Dentro makeCLOBFromWriter di TxtClobParameter" );

        // se nullo, restituisco la stringa nulla
        if (this.value == null) return null;

        // recupero un nuovo temporary CLOB
       // CLOB clobOracle = (CLOB)this.value;
//        Reader rdr = null;
//        char[] tmpChr = new char[2048];
//        StringBuffer sb = new StringBuffer();
//        int byte_count = 0;
        // vai con l'estrazione...
        try{
        return IOUtils.toString(((CLOB)this.value).asciiStreamValue());
//        try
//        {
//            // recupero il reader da CLOB
//            rdr = clobOracle.getCharacterStream();
//            while ((byte_count = rdr.read(tmpChr)) > 0)
//            {
//                //leggo a pezzi...
//                sb.append(tmpChr, 0, byte_count);
//            }
//            return sb.toString();
//        }
        // chiudo tutto in sicurezza...
        }finally
        {
            //((try {rdr.close();} catch (Exception e){}
            try {((CLOB)this.value).close();} catch (Exception e){}
        }

    }


    /**
     * Clona il parametro
     *
     * @return StoreParameter
     */
    public StoreParameter cloneMe()
    {
    	return new TxtClobParameter(index, name, (CLOB)value, inOut );
    }


    /**
     * ???
     * @return Object
     */
    public Object getAttributeValue()
    {
        return super.getAttributeValue();
    }


    /**
     * Restituisce un temporary CLOB
     *
     * @param conn Connection
     * @return CLOB
     * @throws SQLException
     */
    private static CLOB getNewTemporaryCLOB(Connection conn) throws SQLException, Exception
    {

    	eng.util.Logger.getLogger().info("Dentro getNewTemporaryCLOB" );
        CLOB tempClob = null;
        try{
  	    // creo il nuovo temporary clob
        tempClob = CLOB.createTemporary(conn.getMetaData().getConnection(), true, CLOB.DURATION_SESSION);

  	    // il clob e' in R/W
  	    tempClob.open(CLOB.MODE_READWRITE);

        // restituisco il nuovo tempClob
        return tempClob;

        }
        catch(Exception e)
        {
            try {tempClob.close();} catch (Exception e1){}
            try {tempClob.freeTemporary();} catch (Exception e1){}
            if (e instanceof SQLException)
            {
                throw (SQLException) e;
            } else {
                throw e;
            }
        }
        finally
        {
            eng.util.Logger.getLogger().info("Fine getNewTemporaryCLOB");
        }
    }
/*
    public static final void main(final String[] args)
   {
       try
       {
           Class.forName("oracle.jdbc.driver.OracleDriver");
           java.sql.Connection conn = java.sql.DriverManager.getConnection("jdbc:oracle:thin:ddd_owner_1/ddd_owner_1@172.28.20.6:1521:ORALAB");

           StoreProcedure sp =
                   new StoreProcedure("DDPK_PROVA.due",
                               new StoreParameter[]
                               {
                                 new ReturnCodeParameter(1),
                                 new StoreParameter(2, "IdUserIn", Types.INTEGER, StoreParameter.IN ),
                                 new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                               });
           sp.setConnection(conn);

           sp.getParameterByName("IdUserIn").setValue("1");
           ((TxtClobParameter)sp.getParameterByName("XMLIn")).setNewTemporaryCLOBStringValue(conn, "aaaaaaaaaaaaaaa bbbbbbbbbbbbbbbbb cccccccccccccccccccc dddddddddddddddddddd eeeeeeeeeeeeeee ffffffffffffffffff gggggggggggggggggggggg hhhhhhhhhhhhhhhhhhhhhh iiiiiiiiiiiiiiiiiii");
           sp.execute();
           conn.commit();

           sp = new StoreProcedure("DDPK_PROVA.uno",
                                          new StoreParameter[]
                                          {
                                            new ReturnCodeParameter(1),
                                            new StoreParameter(2, "IdUserIn", Types.INTEGER, StoreParameter.IN ),
                                            new TxtClobParameter(3, "XMLIn", null, StoreParameter.OUT ),
                                          });
           sp.setConnection(conn);
           sp.getParameterByName("IdUserIn").setValue("1");
           sp.execute();

           TxtClobParameter txtprm = (TxtClobParameter)sp.getParameterByName("XMLIn");
           String stm = txtprm.getCLOBStringValueAndClose();
           conn.close();


       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
   }
*/
}
