package it.eng.auriga.opentext.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

	public static void main(String[] args) throws ParseException {
		 String s ="20210615150000";
		 
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
			System.out.println(sdfDate.parse(s));
			
		//System.out.println(Integer.decode("a"));
	//	System.out.println("CharForNumber" +getCharForNumber(3));;
		
		
	}
	
	
}
