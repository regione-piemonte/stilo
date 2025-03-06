package it.eng.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

/**
 * Classe di utility per la gestione dei file
 * @author Mattia Zanin
 *
 */
public class FileUtil {
	
	/**
	 * Cancella il file in ingresso; se il file è utilizzato da un altro processo viene inserito
	 * in una coda e verrà cancellato successivamente dal thread
	 *
	 */
	public static boolean deleteFile(File file) {
		boolean deleted = false;
		if(file != null && file.exists()) {
			try { 
				FileUtils.forceDelete(file);
				deleted = true;
			}  catch (Throwable e) {
				try { FilesToDeleteQueue.put(file); } catch(Throwable e1) {}
			}						
		}
		return deleted;		
	}
	
	/**
	 * Scrive uno stream di byte su file (ottimizzato per file di grandi dimensioni)
	 *
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
