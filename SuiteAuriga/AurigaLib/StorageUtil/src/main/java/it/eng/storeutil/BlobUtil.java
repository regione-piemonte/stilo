/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.FileUtil;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;

import oracle.sql.BLOB;
import oracle.sql.CharacterSet;

import org.apache.commons.io.IOUtils;

public class BlobUtil {
	
	/**
	 * Recupero la stringa dal clob
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	public byte[] getByte(Blob blob) throws Exception{
		if(blob!=null){
//			String lString = IOUtils.toString(((BLOB)clob).characterStreamValue());
			byte[] lBytes = IOUtils.toByteArray(((BLOB)blob).binaryStreamValue());
			return lBytes;		
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
	public BLOB setByte(byte[] str,Connection con) throws Exception{
		BLOB blob = null;
		try{
			blob = (BLOB)con.createBlob();
			blob.setBytes(1, str);
//			blob = BLOB.createTemporary(con, false, BLOB.DURATION_SESSION);
//			//blob = BLOB.createTemporary(con, true, BLOB.DURATION_SESSION);
//        	blob.open(BLOB.MODE_READWRITE); 
//        	Writer witer = new OutputStreamWriter(blob.setBinaryStream(0));
//        	witer.write(IOUtils.toString(str, "UTF-8") ); 
//        	witer.flush();
//        	witer.close(); 
//        	blob.close();    
  	  	} 
        catch(Exception sqlexp)
        {
        	if(blob!=null){
        		blob.freeTemporary();
        	}
        	throw new Exception("Errore scrittura BLOB!");
  	  	} 
        return blob;
	}
}