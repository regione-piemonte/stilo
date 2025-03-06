package it.eng.suiteutility.dynamiccodedetector;

public class DynamicCodeDetectorException extends Exception{
	
	public DynamicCodeDetectorException(String e){
		super(e);
	}
	
	public DynamicCodeDetectorException(String msg, Exception e){
		super(msg, e);
	}
	
}
