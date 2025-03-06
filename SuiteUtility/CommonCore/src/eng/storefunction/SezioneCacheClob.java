package eng.storefunction;

import it.eng.auriga.repository2.xml.sezionecache.SezioneCache;

import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import oracle.sql.CLOB;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eng.database.exception.EngException;
import eng.database.exception.EngSqlNoApplException;
import eng.database.modal.EngResultSet;

public class SezioneCacheClob extends Parameter
{          
    
    public  SezioneCacheClob(int position) throws EngException
    {
    	this(position, null);
    }

    public  SezioneCacheClob(int position, String name) throws EngException
    {
    	super(position, java.sql.Types.CLOB , name);
    }

    public static CLOB makeCLOB(Connection connection, SezioneCache sc)
                            throws  SQLException, EngSqlNoApplException
    {
    	CLOB tempClob = null;
        try{
        	// If the temporary CLOB has not yet been created, create new
        	tempClob = CLOB.createTemporary(connection.getMetaData().getConnection(), true, CLOB.DURATION_SESSION); 

        	// Open the temporary CLOB in readwrite mode to enable writing
        	tempClob.open(CLOB.MODE_READWRITE); 
        	// Get the output stream to write
        	Writer tempClobWriter = tempClob.getCharacterOutputStream(); 
        	// Write the data into the temporary CLOB
        	sc.marshal(tempClobWriter);

        	// Flush and close the stream
        	tempClobWriter.flush();
        	tempClobWriter.close(); 

        	// Close the temporary CLOB 
        	tempClob.close();    
  	  	} 
        catch(SQLException sqlexp)
        {
        	tempClob.freeTemporary(); 
        	sqlexp.printStackTrace();
  	  	} 
        catch(Exception exp)
        {
        	tempClob.freeTemporary(); 
        	exp.printStackTrace();
  	  	}
  	  	return tempClob; 
    }

    
    public void  executeSetValueArray(Vector  lines) throws EngException
    {
    	setValueArray(lines);
    }
    
    public void  makeValue(Object httpRequest) throws EngException
    {
    	SezioneCache  sc;
    	String name = getName();
    	sc   = (SezioneCache)((HttpServletRequest)httpRequest).getAttribute(name);
    	setValueSezioneCache(sc);
    }


    public  String  toString()
    {
    	StringBuffer  sb = new StringBuffer("(HttpClob:");
    	sb.append(super.toString() + ",");
    	sb.append(")");
    	return  sb.toString();
    }
    
    public Parameter cloneMe()  throws  eng.database.exception.EngException
    {
    	SezioneCacheClob p = new SezioneCacheClob(this.getPosition(), this.getName());
    	if (this.isModeIn()) p.setModeIn();
    	else if (this.isModeOut()) p.setModeOut();
    	else if (this.isModeInOut()) p.setModeInOut();
        
        return p;
    }
}
