package it.eng.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.io.FileUtils;

/**
 * Classe di gestione della coda per la cancellazione dei file temporanei
 * @author Mattia Zanin
 *
 */
public class FilesToDeleteQueue{
	
	private static boolean started = false;
	private static BlockingQueue<File> filesToDeleteQueue = new LinkedBlockingQueue<File>();
	
//	public static void main(String[] args) throws Exception {
//		File file = new File("c:/tmp/test-files/file.txt");
//		InputStream is = FileUtils.openInputStream(file);
//		
//		FilesToDeleteQueue.put(file);
//		
//		File file2 = new File("c:/tmp/test-files/file2.txt");		
//		FileUtil.writeStreamToFile(is, file2);				
//	}
	
	/**
	 * Se non è già attivo faccio partire il thread di cancellazione, dopo aver ripristinato la coda dal file di backup
	 * 
	 */
	private static void initialize() throws Exception {		
		//FilesToDeleteConfig config = (FilesToDeleteConfig)AppContext.getSpringBean(AppContext._ATT_NAME_FILES_TO_DELETE_CONFIG_SPRINGBEAN);
		FileInputStream fis = null;
		ObjectInputStream in = null;		
		if(!started) {
//			File file = new File("filesToDeleteQueue.ser");
//			if(file.exists()) {
//				try {				
//					fis = new FileInputStream(file);
//					in = new ObjectInputStream(fis); 
//					//deserializzo
//					filesToDeleteQueue = (BlockingQueue<File>) in.readObject();			
//			    } catch(Exception e) {
//			        e.printStackTrace();
//			    } finally {
//			    	try {in.close();} catch(Exception e) {}
//			    	try {fis.close();} catch(Exception e) {}
//			    	in = null; fis = null;
//			    }
//			} else {
//				//se non esiste lo creo
//				file.createNewFile();
//				filesToDeleteQueue = new LinkedBlockingQueue<File>();
//			}
			ThreadDeleteFiles thread = new ThreadDeleteFiles();
			thread.start();
			started = true;
		} 		
	}	
	
	/**
	 * Faccio il backup della coda su file
	 * 
	 */
//	private synchronized static void backup() throws Exception {	
//		//FilesToDeleteConfig config = (FilesToDeleteConfig)AppContext.getSpringBean(AppContext._ATT_NAME_FILES_TO_DELETE_CONFIG_SPRINGBEAN);
//		FileOutputStream fos = null;
//		ObjectOutputStream out = null;					
//		try {
//			File file = new File("filesToDeleteQueue.ser");
//			fos = new FileOutputStream(file);
//	        out = new ObjectOutputStream(fos);
//	        //serializzo
//	        out.writeObject(filesToDeleteQueue);	        
//	    } catch(Exception e) {
//	        e.printStackTrace();
//	    } finally {
//	    	try {out.close();} catch(Exception e) {}
//	    	try {fos.close();} catch(Exception e) {}
//	    	out = null; fos = null;
//	    }	    
//	}	
	
	/**
	 * Metto in coda il file da cancellare
	 * 
	 */
	public static File take() throws Exception {		
		initialize();		
		return filesToDeleteQueue.take();
	}
	
	/**
	 * Prelevo dalla coda il file da cancellare
	 * 
	 */
	public static void put(File f) throws Exception {
		initialize();
		filesToDeleteQueue.put(f);		
//		backup();		
	}
	
}