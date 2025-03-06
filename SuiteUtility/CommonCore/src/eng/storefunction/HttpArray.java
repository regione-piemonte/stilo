package eng.storefunction;

import  java.util.*;

import  java.sql.*;

import  javax.servlet.http.*;

import  eng.database.exception.*;

import  oracle.sql.ARRAY;
import  oracle.sql.ArrayDescriptor;
import java.io.InputStream;

public class HttpArray extends Parameter
{
    //ATTENZIONE!

    //Tutti queste quattro literals DEVONO essere uguali alle loro
    //analoghe nell'ambiente esterno a Java (es. Jsp, Store
    //function,...).

    //ATTENZIONE! DEVE essere scritto IN MAIUSCOLO! DEVE essere definito come tipo nel database
    //private static  final String    ARRAY_DESCRIPTOR_NAME   = "DDD_MASTER.DDVA_STRING"; 

    //ATTENZIONE! DEVE Essere scritto in MAIUSCOLO! DEVE essere
    //definito COME TIPO nel database 
    private static String ARRAY_DESCRIPTOR_NAME = "DDD_MASTER.DDVA_STRING"; 

    //ATTENZIONE! DEVE essere uguale al NUMERO di elementi di
    //STRING_VARRAY definito nel database 
    private static final int LINES_MAXIMUM = 1000;

    //ATTENZIONE! DEVE essere uguale a quello definito nelle STORE PROCEDURE e nelle JSP
    public static  final String COLUMN_SEPARATOR = "|*|";           

    //ATTENZIONE! DEVE essere ugualae a quello DEFINITO NEL DATABASE e nelLE JSP
    public static  final String LINE_SEPARATOR = "|-|";            
    //ATTENZIONE_FINE

    //Questo valore viene utilizzato solo per evitare che un eventuale
    //errore di formattazione dati generi un loop infinito. 
    private static  final int       COLUMN_MAXIMUM          = 100;              
    private static  final boolean   JSP_ARRAY_FOR_COLUMN    = true;

    private final String[]  names;
    private final int       namesStartIndex;
    private final int       namesCounter;

    public  HttpArray(int position) throws EngException
    {
      this(position, null, null, 0, 0);
    }

    public  HttpArray(int position, String name) throws EngException
    {
      this(position, name, null, 0, 0);
    }

    public  HttpArray(int position, String name, String[] names) throws EngException
    {
      this(position, name, names, 0, names.length);
    }

    public  HttpArray(int position, String name, String[] names, int namesStartIndex, int namesCounter) throws EngException
    {
      super(position, Parameter.getTypeArray(), name);
      configure();

      this.names            = names;
      this.namesStartIndex  = namesStartIndex;
      this.namesCounter     = namesCounter;
    }

    public  static  String  getArrayDescriptorName()
    {
    	eng.util.Logger.getLogger().error("ARRAY_DESCRIPTOR_NAME: " + ARRAY_DESCRIPTOR_NAME);
      return ARRAY_DESCRIPTOR_NAME;
    }
    
    public void setArrayDescriptorName(String s)
    {
       ARRAY_DESCRIPTOR_NAME = s;
    }


    public  String[]  getNames()
    {
      return  names;
    }

    public  static  ARRAY   makeARRAY(Connection connection, Vector array)
                            throws  SQLException, EngSqlNoApplException
    {
      try
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
      catch(ArrayStoreException ex)
      {
        throw new EngSqlNoApplException("Errore interno (105)" + ex);
      }
      catch(SQLException ex)
      {
        throw new EngSqlNoApplException("Errore interno (106)");
      }
    }

    public  String  makeStringOutput(CallableStatement statement) throws SQLException
    {
      StringBuffer  sb      = new StringBuffer();
      String[]      strings = (String[]) statement.getArray(getPosition()).getArray(1,LINES_MAXIMUM);

      for(int i=0; i<strings.length; i++)
      {
        sb.append(strings[i] + LINE_SEPARATOR);
      }

      return sb.toString();
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

    public    void  makeValue(Object httpRequest) throws EngException
    {
      Vector  lines;

      if (JSP_ARRAY_FOR_COLUMN)
      {
        lines   = makeLinesFromNamesColumns((HttpServletRequest)httpRequest, names, namesStartIndex, namesCounter);
      }
      else
      {
        lines   = makeLinesFromNamesElements((HttpServletRequest)httpRequest, names, namesStartIndex, namesCounter);
      }

      setValueArray(lines);
    }

    private Vector  makeLinesFromNamesColumns(HttpServletRequest request, String[] names, int namesStartIndex, int namesCounter)
                    throws EngSqlNoApplException
    {
      Vector          lines               = new Vector();
      String[]        columnsValues       = new String[namesCounter];
      int[]           columnsValuesIndex  = new int[namesCounter];
      StringBuffer    lineValue           = new StringBuffer();
      String          columnValue;
      String          name;

      for(int i=0; i<namesCounter; i++)
      {
        name = names[namesStartIndex + i];
        if (isNameAttribute(name))
        {
          Object  valueAttribute = request.getAttribute(name);
          columnsValues[i] = (valueAttribute instanceof String) ? (String)valueAttribute : null;
        }
        else
        {
          columnsValues[i] = request.getParameter(name);
        }

        //Modifica del 03-10-2003 per permettere parametri non passati nella richiesta Http
        if (columnsValues[i] == null)
        {
          columnsValues[i] = "";
        }
        /*
        if (columnsValues == null)
        {
          throw new EngSqlNoApplException("Manca parametro in Jsp definito sul server (" + names[namesStartIndex + i] + ")");
        }
        else
        {
          if (columnsValues.equals(""))
          {
            throw new EngSqlNoApplException("Parametro Jsp senza valori (" + names[namesStartIndex + i] + ")");
          }
        }
        */
      }

      loop: do
      {
        if (lines.size() >= LINES_MAXIMUM)
        {
            throw new EngSqlNoApplException("Parametro Jsp di tipo Array con troppe linee (" + names[namesStartIndex] + ")");
        }
        lineValue.setLength(0);
        for(int i=0; i<namesCounter; i++)
        {
          if (columnsValuesIndex[i] >= columnsValues[i].length())
          {
            if (i==0)
            {
              break loop;
            }
            else
            {
              throw new EngSqlNoApplException("Parametro Jsp di tipo Array con numero linee disallineate (" + names[namesStartIndex + i] + ")," + columnsValues[i] + ".");
            }
          }
          columnValue = HttpArray.makeNextToken(columnsValues[i], columnsValuesIndex[i], COLUMN_SEPARATOR);
          if (columnValue == null)
          {
            throw new EngSqlNoApplException("Parametro Jsp di tipo Array con valore senza terminatore (" + names[namesStartIndex + i] + ")," + columnsValues[i] + ".");
          }
          lineValue.append(columnValue + COLUMN_SEPARATOR);
          columnsValuesIndex[i] += columnValue.length() + COLUMN_SEPARATOR.length();
        }
        lines.add(lineValue.toString());
      } while(true);
      return(lines);
    }

    private Vector  makeLinesFromNamesElements(HttpServletRequest request, String[] names, int namesStartIndex, int namesCounter)
                    throws EngSqlNoApplException
    {
      Vector        lines = new Vector();
      String        nameIndexed;
      String        columnValue;
      int           columnNullCounter;
      int           lineIndex;
      StringBuffer  lineValue;

      lineIndex = 0;
      do
      {
        lineValue = new StringBuffer();
        lineValue.setLength(0);
        columnNullCounter = 0;
        for(int i=0; i<namesCounter; i++)
        {
          nameIndexed = makeNameIndexed(names[namesStartIndex + i], lineIndex);
          if (isNameAttribute(nameIndexed))
          {
            Object  valueAttribute = request.getAttribute(nameIndexed);
            columnValue = (valueAttribute instanceof String) ? (String)valueAttribute : null;
          }
          else
          {
            columnValue = request.getParameter(nameIndexed);
          }

          //Modifica del 03-10-2003 per permettere parametri non passati nella richiesta Http
          if (columnValue == null)
          {
            columnValue = "";
          }
          if (columnValue == null)
          {
            columnNullCounter++;
          }
          else
          {
            if (columnValue.equals(""))
            {
              lineValue.append(COLUMN_SEPARATOR);
              columnNullCounter++;
            }
            else
            {
              lineValue.append(columnValue + COLUMN_SEPARATOR);
            }
          }
        }
        if (columnNullCounter < namesCounter)
        {
          lines.add(lineValue.toString());
          lineIndex++;
        }
        else
        {
          break;
        }
      } while(lineIndex<LINES_MAXIMUM);
      return(lines);
    }

    private String  makeNameIndexed(String name, int index)
    {
      return(name + "_" + (index + 1));
    }

    private static  String  makeNextToken(String string, int startIndex, String token)
    {
      int index = string.indexOf(token, startIndex);

      if (index == -1)
      {
        return null;
      }
      else
      {
        if  (index == 0)
        {
          return "";
        }
        else
        {
          return  string.substring(startIndex, index);
        }
      }
    }

    public  String  toString()
    {
      StringBuffer  sb = new StringBuffer("(HttpArray:");
      sb.append(super.toString() + ",");

      for (int i=0; i<namesCounter; i++)
      {
        sb.append(names[namesStartIndex + i] + ",");
      }
      sb.append(namesStartIndex + "," + namesCounter);

      sb.append(")");
      return  sb.toString();
    }
    
    public Parameter cloneMe()  throws  eng.database.exception.EngException {
        
        HttpArray p = new HttpArray(this.getPosition(), this.getName(), this.getNames(),this.getNamesStartIndex(),this.getNamesCounter());
        if (this.isModeIn()) p.setModeIn();
        else if (this.isModeOut()) p.setModeOut();
        else if (this.isModeInOut()) p.setModeInOut();
        
        return p;
    }
    
    /**
     * Getter for property namesStartIndex.
     * @return Value of property namesStartIndex.
     */
    public int getNamesStartIndex() {
        return namesStartIndex;
    }
    
    /**
     * Getter for property namesCounter.
     * @return Value of property namesCounter.
     */
    public int getNamesCounter() {
        return namesCounter;
    }
    
    public void configure()
    {
        Properties props = new Properties();
        try {
            // Cerchiamo un file chiamato HMDObjectHandler.properties
            InputStream fis = this.getClass().getClassLoader().getResourceAsStream("HttpArray.properties");
            if (fis != null)
            {
                props.load( fis );
                setArrayDescriptorName(props.getProperty("ARRAY_DESCRIPTOR_NAME", "DDD_MASTER.DDVA_STRING"));
            }
            /*else
            {
                eng.util.Logger.getLogger().error("File \"ddd.properties\" non trovato nel classpath!");
            }*/
        } catch (Exception ex)
        {
            eng.util.Logger.getLogger().error("Errore durante la lettura del file di properties: " + ex.getMessage());
        }
    }

    
}
