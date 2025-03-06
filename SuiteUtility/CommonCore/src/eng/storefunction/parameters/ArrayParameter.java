package eng.storefunction.parameters;


import  eng.storefunction.*;
import  java.util.*;

import  java.sql.*;

import  javax.servlet.http.*;

import  eng.database.exception.*;

import  oracle.sql.ARRAY;
import  oracle.sql.ArrayDescriptor;
import  eng.database.modal.EngResultSet;


public class ArrayParameter extends StoreParameter {

    // ATTENZIONE! DEVE essere scritto in MAIUSCOLO! DEVE essere
    // definito come TIPO nel database
    private static final String ARRAY_DESCRIPTOR_NAME   = "CM_SUAP.SUVA_STRING";

    // ATTENZIONE! DEVE essere uguale al numero di elementi di
    // string_varray definito nel database
    private static final int LINES_MAXIMUM           = 1000;

    // ATTENZIONE! DEVE essere uguale a quello definito nelle store
    // procedure e nelle JSP
    public  static final String COLUMN_SEPARATOR        = "|*|";

    //Questo valore viene utilizzato solo per evitare che un eventuale
    //errore di formattazione dati generi un loop infinito.
    private static final int COLUMN_MAXIMUM          = 100;

    /**
     * Usato per un array in input
     */
    public ArrayParameter(int index, String name,Vector lines, int inOut)
    {
	super(index, name, lines, Types.ARRAY, inOut );
	if (this.value == null)
	{
	    this.value = new Vector();
	}
    }

    /**
     * Usato per un array in output
     */
    public ArrayParameter(int index, String name)
    {
	this(index,name,null,OUT);
	this.value = new Vector();
    }

    /**
     * Usato per un array in output
     */
    public ArrayParameter(int index )
    {
	this(index,null,new Vector(), OUT );
    }

    /**
     * Usato per un array in inout
     */
    public ArrayParameter(int index, String name, Vector lines)
    {
	this(index,name,lines,IN);
    }

    /**
     * Usato per un array in inout
     */
    public ArrayParameter(int index, Vector lines)
    {
	this(index,null,lines,IN);
    }

    /**
     * Ci aspettiamo un Vector di stringhe  o una sola stringa.
     */
    public void setValue(Object value)
    {
	if (value != null)
	{
	    eng.util.Logger.getLogger().info("Imposto valore per Array: " + value.getClass().getName());
	}
	else
	{
	    return;
	}
	if (value instanceof Array )
	{
	    try {
		Array ar = (Array)value;
		String[] strings = (String[])ar.getArray();
		Vector v = new Vector();
		for(int i=0; i<strings.length; i++)
		{
		    String s = strings[i];
		    v.add(strings[i]);
		}

		this.value = v;
	    } catch (Exception ex)
	    {
		eng.util.Logger.getLogger().error("Exception in ArrayParameter." + ex + ".");
	    }
	}
	else if (value instanceof Vector)
	{
	    this.value = value;
	}
	else
	{
	    this.value = new Vector();
	    ((Vector)this.value).add("" +value);
	}
    }

    /**
       Ritorna un EngResultSet o null in caso di errore.
    */
    public Object getValue()
    {
	return this.value;
    }

    public EngResultSet getEngResultSet() throws EngSqlNoApplException
    {
	EngResultSet result = new EngResultSet();
	Iterator     lines = ((Vector)(this.value)).iterator();
	String       line;

	int cline = 0;
	while(lines.hasNext())
	{
	    line = (String)lines.next();
	    Vector columns = makeColumns(line);
	    for (int i=0; i<columns.size(); i++)
	    {
		result.addColumn((String)columns.elementAt(i));
	    }
	    result.addRow();
	    cline++;
	}
	return result;
    }

    public String toString()
    {
	Iterator     lines = ((Vector)(this.value)).iterator();
	String       line = "";

	while(lines.hasNext())
	{
	    line += (String)lines.next();
	}
	return line;
    }


    public EngResultSet getEngResultSet(String col_sep) throws EngSqlNoApplException
    {
	EngResultSet result = new EngResultSet();
	Iterator     lines = ((Vector)(this.value)).iterator();
	String       line;

	int cline = 0;
	while(lines.hasNext())
	{
	    line = (String)lines.next();
	    Vector columns = makeColumns(line, col_sep);
	    for (int i=0; i<columns.size(); i++)
	    {
		result.addColumn((String)columns.elementAt(i));
	    }
	    result.addRow();
	    cline++;
	}
	return result;
    }


    public  static  Vector  makeColumns(String line) throws  EngSqlNoApplException
    {
	if (line !=null && line.startsWith("0x") && line.indexOf("|*|") < 0)
	{
	    String new_line ="";
	    // Stringa in formato binario su stringa...
	    line = line.substring(2);
	    while (line.length() > 0)
	    {
		String cc = line.substring(0,2);
		line = line.substring(2);
		int i = Integer.parseInt(cc, 16);
		new_line = new_line + new String(new char[]{(char)i});

	    }
	    line = new_line;

	}


	Vector  v       = new Vector();
	String  column;
	int     posPrec = 0;
	int     posLast;

	if (line==null)
	{
	    line = "";
	}

	while((posLast = line.indexOf(COLUMN_SEPARATOR, posPrec))!=-1)
	{
	    column = line.substring(posPrec, posLast);
	    v.add(column);
	    posPrec = posLast + COLUMN_SEPARATOR.length();
	    if (v.size() > COLUMN_MAXIMUM)
	    {
		throw new EngSqlNoApplException("Errore interno (131)" + line + ".");
	    }
	}
	return  v;
    }

    public  static  Vector  makeColumns(String line, String col_sep) throws  EngSqlNoApplException
    {
	if (line !=null && line.startsWith("0x") && line.indexOf("|*|") < 0)
	{
	    String new_line ="";
	    // Stringa in formato binario su stringa...
	    line = line.substring(2);
	    while (line.length() > 0)
	    {
		String cc = line.substring(0,2);
		line = line.substring(2);
		int i = Integer.parseInt(cc, 16);
		new_line = new_line + new String(new char[]{(char)i});

	    }
	    line = new_line;

	}


	Vector  v       = new Vector();
	String  column;
	int     posPrec = 0;
	int     posLast;

	if (line==null)
	{
	    line = "";
	}

	while((posLast = line.indexOf(col_sep, posPrec))!=-1)
	{
	    column = line.substring(posPrec, posLast);
	    v.add(column);
	    posPrec = posLast + col_sep.length();
	    if (v.size() > COLUMN_MAXIMUM)
	    {
		throw new EngSqlNoApplException("Errore interno (131)" + line + ".");
	    }
	}
	return  v;
    }


    public void prepareStore(CallableStatement stmt) throws SQLException
    {

	if ( (getInOut() & IN) != 0 )
	{
	    prepareStore((PreparedStatement)stmt);
	}

	if ( (getInOut() & OUT) != 0 )
	{
	    stmt.registerOutParameter( index, Types.ARRAY, ARRAY_DESCRIPTOR_NAME);
	}
    }

    public void prepareStore(PreparedStatement stmt) throws SQLException
    {
	if ( (getInOut() & IN) != 0  && value != null)
	{
	    Array ar = makeARRAY( stmt.getConnection() , (Vector)value);
	    stmt.setArray(index, ar);
	}
	else
	{
	    stmt.setNull( index,  Types.ARRAY, ARRAY_DESCRIPTOR_NAME);
	}
    }


    public  Array   makeARRAY(Connection connection, Vector array) throws  SQLException
    {
	// Ciclo l'array per sostituire dei caratteri MOLTO speciali
	for (int i=0; i<array.size(); i++)
	{
	    array.set(i,eng.util.XMLUtil.verySpecialEscape(array.get(i).toString()));
	}

	String[] sarray = (String[])array.toArray(new String[]{});
	ArrayDescriptor d = ArrayDescriptor.createDescriptor(ARRAY_DESCRIPTOR_NAME, connection);
	ARRAY arrayOracle = new ARRAY(d, connection, sarray);
	return arrayOracle;

    }

    public StoreParameter cloneMe()
    {
	return new ArrayParameter(index, name, (Vector)value, inOut );
    }

    public Object getAttributeValue()
    {
	EngResultSet rs = null;

	try {
	    rs = getEngResultSet();
	} catch (Exception ex)
	{
	    rs = null;
	}

	return rs;
    }
}
