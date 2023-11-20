/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * 
 * @author DANCRIST
 *
 */

public class GWTDebug {
	
	 private static boolean enabled = false;
	    
	 public static void enable() { 
	  enabled = true; 
	 }
	 
	 public static void disable() { 
	  enabled = false; 
	 }
	 
	 public static void printLog( final String s ) { 
		 try{
			 if( enabled ) nativeConsoleLog( s ); 
		 }catch (Exception e) {
		   
		 }
	 }

	 public static native void nativeConsoleLog(String text)
	 /*-{
	     console.log(text);
	 }-*/;
	 
}
