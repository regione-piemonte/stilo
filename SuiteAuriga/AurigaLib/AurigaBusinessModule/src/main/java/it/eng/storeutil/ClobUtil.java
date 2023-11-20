/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Writer;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.Connection;

import oracle.sql.CLOB;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.C3P0ProxyConnection;

public class ClobUtil {
	
	private static Logger mLogger = Logger.getLogger(ClobUtil.class);
	
	/**
	 * Recupero la stringa dal clob
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	public String getString(Clob clob) throws Exception{
		if(clob!=null){
			String lString = IOUtils.toString(((CLOB)clob).characterStreamValue());
//			String lString = IOUtils.toString(((CLOB)clob).binaryStreamValue());			
			return lString;			
		}else{
			return null;
		}
	}
	
	/**
	 * Creo un oggetto clob e ne setto la stringa
	 * @param str
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public CLOB setString(String str,Connection con) throws Exception{
		CLOB clob = null;
		try{
			if(con instanceof C3P0ProxyConnection) {
				C3P0ProxyConnection castCon = (C3P0ProxyConnection)con;
				Method m = CLOB.class.getMethod("createTemporary", new Class[]{Connection.class, boolean.class, int.class});
				Object[] args = new Object[] {C3P0ProxyConnection.RAW_CONNECTION, Boolean.valueOf( true ), CLOB.DURATION_SESSION};
				clob = (CLOB) castCon.rawConnectionOperation(m, null, args);	
	        	clob.open(CLOB.MODE_READWRITE); 
	        	Writer witer = clob.setCharacterStream(0);
	        	witer.write(str); 
	        	witer.flush();
	        	witer.close(); 
	        	clob.close();    
			} else {
				clob = CLOB.createTemporary(con, true, CLOB.DURATION_SESSION); 
	        	clob.open(CLOB.MODE_READWRITE); 
	        	Writer witer = clob.setCharacterStream(0);
	        	witer.write(str); 
	        	witer.flush();
	        	witer.close(); 
	        	clob.close();
			}
  	  	} 
        catch(Exception sqlexp) {
        	mLogger.error("Errore clob: " + sqlexp.getMessage(), sqlexp);
        	if(clob!=null){
        		clob.freeTemporary();
        	}
        	throw new Exception("Errore scrittura CLOB!");
  	  	} 
        return clob;
	}
}