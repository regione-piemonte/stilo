/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class DynamicCodeDetectorException extends Exception{
	
	public DynamicCodeDetectorException(String e){
		super(e);
	}
	
	public DynamicCodeDetectorException(String msg, Exception e){
		super(msg, e);
	}
	
}
