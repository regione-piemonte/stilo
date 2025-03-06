package it.eng.core.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Classe che espone alcuni metodi di utilita per i file
 *
 * @author Mattia Zanin
 */
public class FileUtil {
	
	/**
	 * Metodo di scrittura su file, con utilizzo limitato della memoria
	 *
	 * @param is
	 * @param out
	 */
	public static void writeStreamToFile(InputStream is, File out) throws Exception{
		FileOutputStream fos = null;
		fos = new FileOutputStream(out);
		if(fos != null) {
	        try
	        {	        	
	            byte[] buffer = new byte[16384];
	            int byte_count = 0;
	            while ( (byte_count = is.read(buffer)) > 0)
	            {
	                fos.write(buffer,0,byte_count);
	            }	            
	        } catch (Exception ex)
	        {
	            throw ex;
	        }
	        finally {
	        	try { is.close(); } catch (Exception ex1){}
	            try { fos.close(); } catch (Exception ex1){}                      
	        }      
		}
	}
	
}
