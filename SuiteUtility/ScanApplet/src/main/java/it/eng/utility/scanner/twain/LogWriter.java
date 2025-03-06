package it.eng.utility.scanner.twain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.JTextArea;

/**
 * <p>logs all activity. And some low level variables/methods
 * as it is visible to all classes.
 * <p>Provided for debugging and NOT officially part of the API
 */
public class LogWriter {

	/**amount of debugging detail we put in log*/
	public static boolean debug = false;

	/**filename of logfile*/
	static public String log_name = null;
	
	static public ArrayList<String> logArray = null;

	final public static void initLog(){
		if( log_name != null ) {
			File fileLog = new File( log_name );
			if( !fileLog.getParentFile().exists() ){
				fileLog.getParentFile().mkdir();
			}
			if( !fileLog.exists() ){
				try {
					fileLog.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//write message
			PrintWriter log_file;
			try {
				log_file = new PrintWriter( new FileWriter( log_name, true ) );
				log_file.println("System Properties: \n");
				writeSystemProperty(log_file, null);
				log_file.println("\n\n\n");
				log_file.flush();
				log_file.close();
			}
			catch( Exception e ) {
				System.err.println( "Exception " + e + " attempting to write to log file " + log_name );
			}
		}
		if( logArray != null ) {
			logArray.add("System Properties: \n");
			writeSystemProperty(null, logArray);
			logArray.add("\n\n\n");
		}
	}
	
	private static void writeSystemProperty(PrintWriter log_file, ArrayList logArray){
		Properties props=System.getProperties(); 
		Iterator<Object> itr = props.keySet().iterator();
		while(itr.hasNext()){
			String key = (String) itr.next();
			if( logArray!=null)
				logArray.add(key + " - " + props.getProperty(key));
			if( log_file!=null)
				log_file.println(key + " - " + props.getProperty(key));
			
		}
	}
	
	final public static void writeLog( String message, Throwable t ) {
		writeLog( message, true, null, t );
	}
	
	final public static void writeLog( String message, boolean console ) {
		writeLog( message, console, null, null );
	}
	
	final public static void writeLog( String message, boolean console, JTextArea ta, Throwable t ) {
		if( ta!=null){
			ta.append(message + "\n");
			ta.repaint();
		}
		
		if( console == true ){
			System.out.println( message );
			if(t!=null){
				t.printStackTrace();
			}
		}
		
		if( log_name != null ){

			//write message
			PrintWriter log_file;
			try{
				log_file = new PrintWriter( new FileWriter( log_name, true ) );
				
				log_file.println(message);
				
				if(t!=null){
					t.printStackTrace(log_file);
				}
				log_file.flush();
				log_file.close();
			} catch( Exception e ){
				System.err.println( "Exception " + e + " attempting to write to log file " + log_name );
			}

		} 
		if( logArray!=null){
			logArray.add( message );
			if(t!=null){
				logArray.add( ""+ t.getStackTrace() );
			}
		}
	}
	
	final public static void writeLog( String message ) {
		writeLog( message, true );
    }
	
	final public static void writeLog( String message, boolean console, JTextArea ta ) {
		writeLog( message, console, ta, null );
    }
	
	final public static void writeLog( String message, JTextArea ta ) {
		writeLog( message, true, ta, null );
    }
	
}
