package eng.database.modal;

import java.io.Serializable;
import java.sql.*;
import java.util.*;
import eng.database.definition.*;
import eng.database.exception.*;
import eng.util.*;

public class EngResultSet implements ResultSelection, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Vector rows=null;
	private Vector column=null;
	private int position=0;
	private int numColumn=0;
        //ghf 05/11/2003
        // aggiunto per limitare il n. di righe
        private boolean tooManyRows=false;

   public EngResultSet()
   {
      rows=new Vector();
      column = new Vector();
   }

   public EngResultSet(boolean isManyRows)
   {
      this();
      tooManyRows = isManyRows;
   }

   public EngResultSet(ResultSet rs, boolean isManyRows) throws EngSqlNoApplException
   {
      this(rs);
      tooManyRows = isManyRows;
   }

   public EngResultSet(ResultSet rs) throws EngSqlNoApplException
   {
      if (rs != null)
      {
            try
            {
                position = 0;
                ResultSetMetaData rsm = rs.getMetaData();
                numColumn = rsm.getColumnCount();

                boolean find = true;
                while (rs.next())
                {
                  if (find)
                  {
                    rows = new Vector();
                    find = false;
                  }
                  column = new Vector();
                  for(int i=1; i<= numColumn; i++)
                  {
                    column.addElement(rs.getString(i));
                  }
                  rows.addElement(column);
                }
	  }
          catch(SQLException e)
          {
	    throw new EngSqlNoApplException("Engineering. "+ e.getMessage());
	  }
      }
      else
      {

      }
   }

	public boolean next(){
	  boolean nextElement = false;
	  if (rows != null && rows.size() >= 0 &&
	      position < rows.size()) {
		 position++;
		 nextElement = true;
	  }
	  else
	    nextElement = false;

	  return nextElement;
	}

	public String getElement(int i)  throws EngSqlNoApplException{
		try	{
	       return (String)((Vector)rows.elementAt(position-1)).elementAt(i-1);
	    }catch(ArrayIndexOutOfBoundsException aioob) {
			throw new EngSqlNoApplException("Engineering. Stack overflow:"+ i +". Column number selected:" + numColumn);
	    }
	}

	public void setElement(String elem, int i)  throws EngSqlNoApplException{
		try	{
	       ((Vector)rows.elementAt(position-1)).setElementAt(elem, i-1);
	    }
		catch(ArrayIndexOutOfBoundsException aioob) {
			throw new EngSqlNoApplException("Engineering. Stack overflow:"+ i +". Column number selected:" + numColumn);
	    }
	}
	
    public String getElementAsNumber(int i, int decimali, boolean usaPunteggiatura)  throws EngSqlNoApplException{
	    try	{
	       String s = (String)((Vector)rows.elementAt(position-1)).elementAt(i-1);
               String pattern = "";
               pattern =  (usaPunteggiatura) ? "#,##0" : "0";
               if (decimali > 0)
               {
                   pattern += ".";
                   for (int k=0; k<decimali; ++k)
                   {
                       pattern += "0";
                   }

               }

               java.text.DecimalFormat df = new java.text.DecimalFormat(pattern, new java.text.DecimalFormatSymbols(java.util.Locale.ITALY) );

               try {
                    return  df.format( Double.parseDouble( s ) );
               } catch (Exception ex)
               {
                   return s;
               }


	    } catch(ArrayIndexOutOfBoundsException aioob) {
			throw new EngSqlNoApplException("Engineering. Stack overflow:"+ i +". Column number selected:" + numColumn);
	    }
	}

    public String getHtmlElement(int i)  throws EngSqlNoApplException{

//        XMLUtil filtro = new XMLUtil();
        String res;

        try	{
	       res = (String)((Vector)rows.elementAt(position-1)).elementAt(i-1);
//           return filtro.xmlEscape(res);
	       return XMLUtil.xmlEscape(res);
	    }catch(ArrayIndexOutOfBoundsException aioob) {
			throw new EngSqlNoApplException("Engineering. Stack overflow:"+ i +". Column number selected:" + numColumn);
	    }
	}

    public void addColumn(String obj) {
    	column.addElement(obj);
    }

    public void addRow() {
    	rows.addElement(column);
		column = new Vector();
    }
    public void addRow(Vector row) {
    	     rows.addElement(row);
    }

    public Vector getRow(int index) {
    	     return (Vector)rows.elementAt(index-1);
    }

    public boolean isEmpty() {
       if (rows.size()==0)
	     return true;
       else
	     return false;
    }

    public int numberColumns() {
    	return column.size();
    }


    public int numberRows() {
    	return rows.size();
    }

    //ghf 05/11/2003
    // aggiunto per limitare il n. di righe
    public void setTooManyRows(boolean tooManyRows)
    {
      this.tooManyRows = tooManyRows;
    }

    //ghf 05/11/2003
    // aggiunto per limitare il n. di righe
    public boolean isTooManyRows()
    {
      return tooManyRows;
    }

    public void setPosition(int pos)
   {
     this.position = pos;
   }


}
